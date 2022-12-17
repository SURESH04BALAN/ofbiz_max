/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class ReceiveInventoryGenerateInventoryAction extends ScreenAction {

    public static final String module = ReceiveInventoryGenerateInventoryAction.class.getName();
    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;
    private ListAdapterListModel<ShipmentReceiptComposite> listModel;

    /*public ReceiveInventoryGenerateInventoryAction(ListAdapterListModel<ShipmentReceiptComposite> listModel, XuiSession session) {
        this.listModel = listModel;
        this.session = session;
    }*/
    public ReceiveInventoryGenerateInventoryAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<ShipmentReceiptComposite> listModel) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, name, session, desktopPane);
        this.listModel = listModel;
        this.session = session;
    }

    public ReceiveInventoryGenerateInventoryAction(ListAdapterListModel<ShipmentReceiptComposite> listModel, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, session);
        this.listModel = listModel;
        this.session = session;
    }
    
    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Clear";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int index = 0; index < listModel.getSize(); ++index) {
            ShipmentReceiptComposite shipmentReceipt = (ShipmentReceiptComposite) listModel.getElementAt(index);
            Debug.logInfo("after shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity: " + shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity(), "Module");
            generateInventory(shipmentReceipt);
        }

        listModel.fireListDataChanged();
        ActionEvent event = new ActionEvent(this, 1, "invoice generated");
        for (ActionListener listener : listeners) {
            Debug.logInfo("editCell action listner", module);
            listener.actionPerformed(event); // broadcast to all
        }
    }

    XuiSession session;
    //Order order;

    public void generateInventory(ShipmentReceiptComposite shipmentReceipt) {
        
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ start btnGenerateInventoryActionPerformed shipGroupSeqId:", module);
        Order order = shipmentReceipt.getOrder();
        String shipGroupSeqId = null;
        OrderItemShipGroupList list = shipmentReceipt.getOrder().getOrderItemShipGroupList();
        Debug.logInfo("ship group list size: " + list.getSize(), module);
        if (list.getSize() > 0) {
            shipGroupSeqId = list.getElementAt(0).getshipGroupSeqId();
        }

        if (shipGroupSeqId == null || shipGroupSeqId.isEmpty()) {
            Debug.logInfo("ship group is empty or null:" + shipGroupSeqId, module);
            return;
        }

        if (order.shipment == null || order.shipment.getshipmentId().isEmpty()) {
            Debug.logInfo("shipment is empty or null:" + shipGroupSeqId, module);
            return;
        }

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String orderId = shipmentReceipt.getOrderItemComposite().getOrderItem().getorderId();
        String newStatus = "ORDER_APPROVED";

// GenericValue userLogin = session.getUserLogin();
//        LocalDispatcher dispatcher = session.getDispatcher();
//        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
//        String orderId = order.getOrder().getOrderId();
        GenericValue facility = PosProductHelper.getFacility(order.getFacilityId(), session.getDelegator());

//        String shipGroupSeqId = order.shipGroupSeqId;
        String inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
//        String destinationFacilityId = "mainwarehouse";
        newStatus = "PURCH_SHIP_RECEIVED";
        String destinationFacilityId = order.getFacilityId();
        Debug.logInfo("destinationFacilityId: " + destinationFacilityId, module);

        OrderItemComposite item = shipmentReceipt.getOrderItemComposite();

//        List<OrderItemComposite> items = getItemList();
//        for (OrderItemComposite item : items) {
        Debug.logInfo("shipGroupSeqId:" + item.getOrderItem().getitemDescription() + ";  shipGroupSeqId:" + shipGroupSeqId, module);

        Debug.logInfo("shipGroupSeqId: " + shipGroupSeqId + " order.shipment.getshipmentId(): " + order.shipment.getshipmentId(), module);
        Debug.logInfo("orderId: " + orderId + "orderItemSeqId: " + item.getOrderItem().getorderItemSeqId(), module);
        Debug.logInfo("item.getquantity(): " + item.getOrderItem().getquantity().toString(), module);
        Map<String, Object> serviceContext = UtilMisc.<String, Object>toMap(
                "orderId", orderId,
                "statusId", newStatus,
                "facilityId", destinationFacilityId,
                "inventoryItemTypeId", inventoryItemTypeId,
                "orderItemSeqId", item.getOrderItem().getorderItemSeqId(),
                "quantity", item.getOrderItem().getquantity(),
                "unitCost", item.getOrderItem().getunitPrice(),
                "quantityAccepted", item.getOrderItem().getquantity(),
                "quantityRejected", BigDecimal.ZERO,
                "shipGroupSeqId", shipGroupSeqId,
                "shipmentId", order.shipment.getshipmentId(),
                "productId", item.getOrderItem().getproductId(),
                "quantityOrdered", item.getOrderItem().getquantity(),
                "datetimeManufactured", shipmentReceipt.getInventoryItem().getdatetimeManufactured(),
                "datetimeReceived", shipmentReceipt.getInventoryItem().getdatetimeReceived(),
                "expireDate", shipmentReceipt.getInventoryItem().getexpireDate(),
                "lotId", shipmentReceipt.getInventoryItem().getlotId(),
                "userLogin", userLogin);

        Map<String, Object> newSttsResult = null;

        //newSttsResult = dispatcher.runSync("issueOrderItemToShipmentAndReceiveAgainstPO", serviceContext);
//                    newSttsResult = dispatcher.runSync("updateIssuanceShipmentAndPoOnReceiveInventory", serviceContext);
        try {
            newSttsResult = dispatcher.runSync("receiveInventoryProduct", serviceContext);
            for (Map.Entry<String, Object> entryDept : newSttsResult.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
        } catch (Exception exc) {
            Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
        }
//        }

        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ end btnGenerateInventoryActionPerformed shipGroupSeqId:" + shipGroupSeqId, module);
//        loadFromDb(orderId);

    }

    protected ClassLoader getClassLoader() {

        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    @Override
    public Action getAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
