/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */

public class GenerateInventoryWorker extends SwingWorker<List<Order>, Order> {
    public static final String module = GenerateInventoryWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<Order> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;

    public GenerateInventoryWorker(ListAdapterListModel<Order> personListModel, XuiSession session) {
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
    protected List<Order> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<Order> accounts = new ArrayList<Order>();
        return accounts;
    }
    
    
    @Override
    protected void process(List<Order> chunks ) {
        /*            try{
         throw new Exception("process(List<Person> chunks)");
         }
         catch(Exception e){
         e.printStackTrace();
         }
         */
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    private long getTimeToSleep() {
        return loadPersonsSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }
    
    static public void generateInventory(Order order, final XuiSession session){
      GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String orderId = order.getOrderId();
        Delegator delegator = session.getDelegator();
        String newStatus = "ORDER_APPROVED";

// GenericValue userLogin = session.getUserLogin();
//        LocalDispatcher dispatcher = session.getDispatcher();
//        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
//        String orderId = order.getOrder().getOrderId();
        String shipGroupSeqId = order.shipGroupSeqId;
        GenericValue facility = PosProductHelper.getFacility(order.getFacilityId(), delegator);
        
        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ start btnGenerateInventoryActionPerformed shipGroupSeqId:" + shipGroupSeqId, module);
        /*        if (UtilValidate.isNotEmpty(orderId)) {
         try {

         dispatcher.runSync("quickShipPurchaseOrder", UtilMisc.toMap(
         //"order", order.getGenericValueObj(), 
         //"facility", facility, 
         "orderId", order.getOrder().getOrderId(),
         "facilityId", order.getDestinationFacilityId(),
         "userLogin", userLogin));
         Debug.logInfo("Test sales order with id [" + orderId + "] has been shipped", module);
         } catch (Exception exc) {
         Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
         }
         }
         */
        //load shipment
        List<GenericValue> shipments = PosProductHelper.getGenericValueLists("Shipment", "primaryOrderId", orderId, delegator);
        for (GenericValue shipment : shipments) {
            order.shipment = new Shipment(shipment);
            break;
        }

//        String shipGroupSeqId = order.shipGroupSeqId;
        String inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
//        String destinationFacilityId = "mainwarehouse";
        newStatus = "ITEM_APPROVED";
        String destinationFacilityId = order.getFacilityId();
        Debug.logInfo("destinationFacilityId: " + destinationFacilityId, module);
        List<OrderItemComposite> items = order.getOrderItemsList().getList();
        for (OrderItemComposite item : items) {
            Debug.logInfo("shipGroupSeqId:" + item.getOrderItem().getitemDescription() + ";  shipGroupSeqId:" + shipGroupSeqId, module);

            //          Debug.logInfo("shipGroupSeqId: " + shipGroupSeqId + " order.shipment.getshipmentId(): " + order.shipment.getshipmentId(), module);
            Debug.logInfo("orderId: " + orderId + "orderItemSeqId: " + item.getOrderItem().getorderItemSeqId(), module);
            Debug.logInfo("item.getquantity(): " + item.getOrderItem().getquantity().toString(), module);
            Map<String, Object> serviceContext = UtilMisc.<String, Object>toMap(
                    "orderId", orderId,
                    "statusId", newStatus,
                    "facilityId", destinationFacilityId,
                    "inventoryItemTypeId", inventoryItemTypeId,
                    "orderItemSeqId", item.getOrderItem().getorderItemSeqId(),
                    "quantity", item.getOrderItem().getquantity(),
                    "quantityAccepted", item.getOrderItem().getquantity(),
                    "quantityRejected", BigDecimal.ZERO,
                    "shipGroupSeqId", shipGroupSeqId,
                    "shipmentId", order.shipment.getshipmentId(),
                    "productId", item.getOrderItem().getproductId(),
                    "quantityOrdered", item.getOrderItem().getquantity(),
                    "userLogin", userLogin);
            Map<String, Object> newSttsResult = null;

            //newSttsResult = dispatcher.runSync("issueOrderItemToShipmentAndReceiveAgainstPO", serviceContext);
//                    newSttsResult = dispatcher.runSync("updateIssuanceShipmentAndPoOnReceiveInventory", serviceContext);
            try {
                newSttsResult = dispatcher.runSync("receiveInventoryProduct", serviceContext);
            } catch (Exception exc) {
                Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
            }
        }
        
        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ end btnGenerateInventoryActionPerformed shipGroupSeqId:" + shipGroupSeqId, module);
    }
}
