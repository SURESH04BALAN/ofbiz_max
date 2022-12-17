/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns.receivereturn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.returns.OrderReturnActionInterface;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class ReceiveOrderReturnItemsInventoryAction extends ScreenAction {

    public static final String module = ReceiveOrderReturnItemsInventoryAction.class.getName();
    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;

    public static final String nameStr = "RECEIVE ORDER RETURN";

    private ReturnHeaderComposite returnHeaderComposite;

    ControllerOptions option = new ControllerOptions();

    public ReceiveOrderReturnItemsInventoryAction(ControllerOptions option, ReturnHeaderComposite returnHeaderComposite) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION,
                ReceiveOrderReturnItemsInventoryAction.nameStr, XuiContainer.getSession(), XuiContainer.getSession().getDesktopPane());
        this.returnHeaderComposite = returnHeaderComposite;
        this.option = option;
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

//        ReturnHeaderComposite returnComposite = orderReturnActionInterface.getOrderReturn();
        if (returnHeaderComposite != null) {
            getReturnHeaderItems(returnHeaderComposite);
        }

        ActionEvent event = new ActionEvent(this, 1, "invoice generated");
        for (ActionListener listener : listeners) {
            Debug.logInfo("editCell action listner", module);
            listener.actionPerformed(event); // broadcast to all
        }
    }

    //XuiSession session;
    //Order order;

    public void getReturnHeaderItems(ReturnHeaderComposite returnHeaderComposite) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        String returnId = returnHeaderComposite.getReturnHeader().getreturnId();

        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ start btnGenerateInventoryActionPerformed shipGroupSeqId:", module);

        if (UtilValidate.isEmpty(returnHeaderComposite.getShipment()) || UtilValidate.isEmpty(returnHeaderComposite.getShipment().getshipmentId())) {
            Debug.logInfo("shipment is empty or null:" + returnId, module);
            return;
        }

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        //Map<String, Object> resultMap = ServiceUtil.returnSuccess();

// GenericValue userLogin = session.getUserLogin();
//        LocalDispatcher dispatcher = session.getDispatcher();
//        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
//        String orderId = order.getOrder().getOrderId();
        GenericValue facility = PosProductHelper.getFacility(returnHeaderComposite.getReturnHeader().getdestinationFacilityId(), session.getDelegator());

//        String shipGroupSeqId = order.shipGroupSeqId;
        String inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
        String newStatus = "INV_RETURNED";
//        String destinationFacilityId = "mainwarehouse";
//        newStatus = "PURCH_SHIP_RECEIVED";
        String destinationFacilityId = facility.getString("facilityId");
        Debug.logInfo("destinationFacilityId: " + destinationFacilityId, module);
        List<ReturnItemComposite> returnItemlist = returnHeaderComposite.getOrderReturnItemsList().getList();

        for ( ReturnItemComposite item :  returnItemlist) {


//        List<OrderItemComposite> items = getItemList();
//        for (OrderItemComposite item : items) {
//            Debug.logInfo("shipGroupSeqId:" + item.getOrderItem().getitemDescription() + ";  shipGroupSeqId:" + shipGroupSeqId, module);

//            Debug.logInfo("shipGroupSeqId: " + shipGroupSeqId + " order.shipment.getshipmentId(): " + order.shipment.getshipmentId(), module);
            Debug.logInfo("returnId: " + returnId + "orderItemSeqId: " + item.getOrderItem().getorderItemSeqId(), module);
            Debug.logInfo("item.getquantity(): " + item.getOrderItem().getquantity().toString(), module);
            Map<String, Object> serviceContext = UtilMisc.<String, Object>toMap(
                    "returnId", returnId,
                    "returnItemSeqId", item.getReturnItem().getreturnItemSeqId(),
                    "statusId", newStatus,
                    "facilityId", destinationFacilityId,
                    "inventoryItemTypeId", inventoryItemTypeId,
//                    "orderItemSeqId", item.getOrderItem().getorderItemSeqId(),
                    "quantityAccepted", item.getReturnItem().getreturnQuantity(),
                    "unitCost", item.getReturnItem().getreturnPrice(),
                    "quantityRejected", item.getReturnItem().getreturnQuantity(),
//                    "quantityRejected", "CUST_RTN_INVOICE",
//                    "quantityRejected", BigDecimal.ZERO,
//                    "shipGroupSeqId", shipGroupSeqId,
                    "shipmentId", returnHeaderComposite.getShipment().getshipmentId(),
                    "productId", item.getReturnItem().getproductId(),
///                    "quantityOrdered", item.getOrderItem().getquantity(),
                    "returnHeaderStatus", "RETURN_RECEIVED",
//                    "datetimeManufactured", shipmentReceipt.getInventoryItem().getdatetimeManufactured(),
//                    "datetimeReceived", shipmentReceipt.getInventoryItem().getdatetimeReceived(),
//                    "expireDate", shipmentReceipt.getInventoryItem().getexpireDate(),
//                    "lotId", shipmentReceipt.getInventoryItem().getlotId(),
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
                Debug.logWarning("Unable to quick ship test sales order with id [" + returnId + "] with error: " + exc.getMessage(), module);
            }
        }

//        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ end btnGenerateInventoryActionPerformed shipGroupSeqId:" + shipGroupSeqId, module);
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
