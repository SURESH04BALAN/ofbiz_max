/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.entity.ShipmentItem;
import org.ofbiz.ordermax.entity.ShipmentReceipt;
/**
 *
 * @author siranjeev
 */
public class LoadReceiveInventoryCompositeWorker extends SwingWorker<List<ReceiveInventoryComposite>, ReceiveInventoryComposite> {
    public static final String module = LoadReceiveInventoryCompositeWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<ReceiveInventoryComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;

    public LoadReceiveInventoryCompositeWorker(ListAdapterListModel<ReceiveInventoryComposite> personListModel, XuiSession session) {
        this.personListModel = personListModel;
        this.session = session;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    @Override
    protected List<ReceiveInventoryComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<ReceiveInventoryComposite> accounts = new ArrayList<ReceiveInventoryComposite>();
        return accounts;
    }

    static public ReceiveInventoryComposite getShipmentReceiptComposite(final String orderId, XuiSession session) {
        Order order = LoadPurchaseOrderWorker.loadOrder(orderId, session);
        ReceiveInventoryComposite ric = getShipmentReceiptComposite(order, session);
        /*
         ReceiveInventoryComposite ric = new ReceiveInventoryComposite();
         ric.setOrder(order);

         Debug.logInfo("products " + order.getOrderItemsList().getList().size(), module);
         for (OrderItemComposite item : order.getOrderItemsList().getList()) {
         try {
         if (item.getOrderItem().getproductId() == null || item.getOrderItem().getproductId().isEmpty()) {
         Debug.logInfo("null item ", module);
         continue;
         }
         // check for previous order payments
         List<EntityExpr> shipmentReceiptConds = UtilMisc.toList(
         EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, item.getOrderItem().getorderId()),
         EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, item.getOrderItem().getorderItemSeqId()));
         List<GenericValue> shipmentReceipts = session.getDelegator().findList("ShipmentReceipt", EntityCondition.makeCondition(shipmentReceiptConds, EntityOperator.AND), null, null, null, false);

         if (shipmentReceipts.size() > 0) {
         for (GenericValue srIter : shipmentReceipts) {
         ShipmentReceiptComposite src = new ShipmentReceiptComposite();
         ShipmentReceipt sr = new ShipmentReceipt(srIter);
         src.setShipmentReceipt(sr);
         src.setOrderItemComposite(item);
         List<GenericValue> iiGVs = srIter.getRelated("InventoryItem");
         if (iiGVs.size() > 0) {
         InventoryItem iItem = new InventoryItem(iiGVs.get(0));
         src.setInventoryItem(iItem);
         } else {
         InventoryItem iItem = new InventoryItem();
         src.setInventoryItem(iItem);
         }
         ric.getShipmentReceiptCompositeList().add(src);
         }
         } else {
         ShipmentReceiptComposite src = new ShipmentReceiptComposite();
         ShipmentReceipt sr = new ShipmentReceipt();
         src.setShipmentReceipt(sr);
         src.setOrderItemComposite(item);
         InventoryItem iItem = new InventoryItem();
         src.setInventoryItem(iItem);
         ric.getShipmentReceiptCompositeList().add(src);                    
         }

         Debug.logInfo("item " + item.getOrderItem().getproductId(), module);
         } catch (GenericEntityException ex) {
         Logger.getLogger(LoadReceiveInventoryCompositeWorker.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         */
        return ric;
    }

    static public ReceiveInventoryComposite getShipmentReceiptComposite(final Order order, XuiSession session) {

        ReceiveInventoryComposite ric = new ReceiveInventoryComposite();
        //    try {
        //set order header
        ric.setOrder(order);

        //get shipment
        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
        whereClauseMap.put("primaryOrderId", order.getOrderId());
//                    whereClauseMap.put("roleTypeId", "BILL_FROM_VENDOR");

        List<GenericValue> sGVs = PosProductHelper.getGenericValueListsWithSelection("Shipment", whereClauseMap, null, session.getDelegator());

//            GenericValue ohGV = order.getGenericValueObj();
//            List<GenericValue> sGVs = ohGV.getRelated("Shipment");
        if (sGVs.size() > 0) {
//                Shipment iItem = new Shipment(sGVs.get(0));
            ric.setShipment(new Shipment(sGVs.get(0)));
            order.shipment = ric.getShipment();
        } else {
                //GenericValue shpGV = SavePurchaseOrderWorker.createShipment(order, session);
                //order.shipment = new Shipment(shpGV);                            
                Map<String, Object> resultMap = SavePurchaseOrderWorker.quickShipPurchaseOrder(order, session);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                ric.setShipment(order.shipment);                
//            }

        }

        Debug.logInfo("products " + order.getOrderItemsList().getList().size(), module);
        for (OrderItemComposite item : order.getOrderItemsList().getList()) {
            try {
                if (item.getOrderItem().getproductId() == null || item.getOrderItem().getproductId().isEmpty()) {
                    Debug.logInfo("null item ", module);
                    continue;
                }

                //get shipment item
                whereClauseMap = new HashMap<String, Object>();
                whereClauseMap.put("shipmentId", ric.getShipment().getshipmentId());
                whereClauseMap.put("productId", item.getOrderItem().getproductId());
//                    whereClauseMap.put("roleTypeId", "BILL_FROM_VENDOR");

                List<GenericValue> siGVs = PosProductHelper.getGenericValueListsWithSelection("ShipmentItem", whereClauseMap, null, session.getDelegator());
                if (siGVs.size() > 0) {
                    //                Shipment iItem = new Shipment(sGVs.get(0));
                    item.setShipmentItem(new ShipmentItem(siGVs.get(0)));
                } else {
                    item.setShipmentItem(new ShipmentItem());
//                      item.getShipmentItem().
                }

                // check for previous order payments
                List<EntityExpr> shipmentReceiptConds = UtilMisc.toList(
                        EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, item.getOrderItem().getorderId()),
                        EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, item.getOrderItem().getorderItemSeqId()));
                List<GenericValue> shipmentReceipts = session.getDelegator().findList("ShipmentReceipt", EntityCondition.makeCondition(shipmentReceiptConds, EntityOperator.AND), null, null, null, false);

                if (shipmentReceipts.size() > 0) {
                    for (GenericValue srIter : shipmentReceipts) {
                        ShipmentReceiptComposite src = new ShipmentReceiptComposite();
                        ShipmentReceipt sr = new ShipmentReceipt(srIter);
                        src.setShipmentReceipt(sr);
                        src.setOrderItemComposite(item);
                        List<GenericValue> iiGVs = srIter.getRelated("InventoryItem");
                        if (iiGVs.size() > 0) {
                            InventoryItem iItem = new InventoryItem(iiGVs.get(0));
                            if (iItem.getinventoryItemTypeId() == null || iItem.getinventoryItemTypeId().isEmpty()) {
                                iItem.setinventoryItemTypeId("NON_SERIAL_INV_ITEM");
                            }
                            src.setInventoryItem(iItem);
                        } else {
                            InventoryItem iItem = new InventoryItem();
                            iItem.setinventoryItemTypeId("NON_SERIAL_INV_ITEM");
                            src.setInventoryItem(iItem);

                        }
                        ric.getShipmentReceiptCompositeList().add(src);
                    }
                } else {
                    ShipmentReceiptComposite src = new ShipmentReceiptComposite();
                    ShipmentReceipt sr = new ShipmentReceipt();
                    src.setShipmentReceipt(sr);
                    src.setOrderItemComposite(item);
                    InventoryItem iItem = new InventoryItem();
                    src.setInventoryItem(iItem);
                    ric.getShipmentReceiptCompositeList().add(src);
                }

                Debug.logInfo("item " + item.getOrderItem().getproductId(), module);
            } catch (GenericEntityException ex) {
                Debug.logError(ex, module);//LoadReceiveInventoryCompositeWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ric;
    }
        
}
