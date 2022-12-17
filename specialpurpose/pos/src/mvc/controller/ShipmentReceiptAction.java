/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JDesktopPane;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.purchaseorder.inventory.ReceiveInventoryController;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class ShipmentReceiptAction extends ScreenAction {

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Receive Stock";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = ""; //salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public ShipmentReceiptAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.SHIPMENT_RECEIPT_ACTION,
                ShipmentReceiptAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    public ShipmentReceiptAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SHIPMENT_RECEIPT_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ShipmentReceiptAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SHIPMENT_RECEIPT_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ShipmentReceiptAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderListModel) {
        super(ActionType.SHIPMENT_RECEIPT_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.orderListModel = orderListModel;
        loadIcons();
    }

    public ShipmentReceiptAction(String name, XuiSession session) {
        super(ActionType.SHIPMENT_RECEIPT_ACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
//    private Order order = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        Debug.logInfo("ShipmentReceiptAction : ", module);
        if (orderActionInterface != null) {
            final Order order = orderActionInterface.getOrder();
            reciveShipmentOrder(order);
            
        } else {
            /*for (final Order order : orderCompListModel.getList()) {

                if (approveOrder(order.getOrderId())) {
                    ActionEvent event = new ActionEvent(this, 1, order.getOrderId());
                    for (ActionListener listener : listeners) {
                        Debug.logInfo("editCell Action listner", module);
                        listener.actionPerformed(event); // broadcast to all
                    }
                } else {
                    int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to APPROVE ORDER: " + order.getOrderId(), "Approve Order",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                }
            }*/
        }
//                    Debug.logInfo("ShipmentReceiptAction order id: " + order.getOrderHeader().getOrderId()  , module);
    }

    @Override
    public Action getAction() {
        return this;
    }

    void reciveShipmentOrder(Order order) {
        ControllerOptions option = new ControllerOptions();
        option.put("Order", order);
        ReceiveInventoryController controller = ReceiveInventoryController.runController(option);
        controller.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ActionEvent event = new ActionEvent(this, 1, "receive inventory");
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        Debug.logInfo("ShipmentReceiptAction : end", module);
                    }
                });

        //purchaseOrder = new ReceiveInventoryController(orderListModel,  session);        
    }

    static class ListPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ShipmentReceiptAction(name, session, desktopPane);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new ShipmentReceiptAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.SHIPMENT_RECEIPT_ACTION, new ListPriceLookupActionFactory());
    }
}
