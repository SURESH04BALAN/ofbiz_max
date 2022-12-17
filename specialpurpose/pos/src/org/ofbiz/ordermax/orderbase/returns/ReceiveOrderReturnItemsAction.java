package org.ofbiz.ordermax.orderbase.returns;

import mvc.controller.*;
import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.orderbase.returns.receivereturn.ReceiveReturnInventoryController;

import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class ReceiveOrderReturnItemsAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ReceiveOrderReturnItemsAction.class.getName();
    public static final String nameStr = "RECEIVE ORDER RETURN";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private OrderReturnActionInterface orderReturnActionInterface;
    ControllerOptions option = new ControllerOptions();
    
    public ReceiveOrderReturnItemsAction(ControllerOptions option,  OrderReturnActionInterface orderActInterface) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION,
                ReceiveOrderReturnItemsAction.nameStr, XuiContainer.getSession(), XuiContainer.getSession().getDesktopPane());
        this.orderReturnActionInterface = orderActInterface;
        this.option = option;
    }


    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "APPROVE ORDER";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderReturnActionInterface != null) {
            final ReturnHeaderComposite orderReturn = orderReturnActionInterface.getOrderReturn();
            receiveOrderReturn(orderReturn);//) {
            /*    ActionEvent event = new ActionEvent(this, 1, orderReturn.getReturnHeader().getreturnId());
                for (ActionListener listener : listeners) {
                    Debug.logInfo("editCell Action listner", module);
                    listener.actionPerformed(event); // broadcast to all
                }
            } else {
                int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to Accept ORDER RETURN: " + orderReturn.getReturnHeader().getreturnId(), "Approve Order",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            }*/
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
    }


    void receiveOrderReturn(ReturnHeaderComposite returnHeaderComposite) {
        ControllerOptions option = new ControllerOptions();
        option.put("ReturnHeaderComposite", returnHeaderComposite);
        ReceiveReturnInventoryController controller = ReceiveReturnInventoryController.runController(option);
        controller.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ActionEvent event = new ActionEvent(this, 1, "Receive Order Return Action");
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        Debug.logInfo("ReceiveOrderReturnAction : end", module);
                    }
                });

        //purchaseOrder = new ReceiveInventoryController(orderListModel,  session);        
    }    
}