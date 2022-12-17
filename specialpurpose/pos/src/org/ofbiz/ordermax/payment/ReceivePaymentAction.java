package org.ofbiz.ordermax.payment;

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
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.orderbase.returns.ReceiveOrderReturnAction;

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
public class ReceivePaymentAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "RECEIVE PAYMENT";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private PaymentActionInterface paymentActionInterface;

    public ReceivePaymentAction(javax.swing.JDesktopPane desktopPane,  PaymentActionInterface paymentActionInterface) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION,
                ReceivePaymentAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.paymentActionInterface = paymentActionInterface;
    }

    public ReceivePaymentAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public ReceivePaymentAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION, session);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
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
        if (paymentActionInterface != null) {
            final PaymentComposite payment = paymentActionInterface.getPayment();
            if (receivePayment(payment )) {
                ActionEvent event = new ActionEvent(this, 1, payment.getPayment().getpaymentId()  );
                for (ActionListener listener : listeners) {
                    Debug.logInfo("editCell Action listner", module);
                    listener.actionPerformed(event); // broadcast to all
                }
            } else {
                int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to Receive Payment: " + payment.getPayment().getpaymentId(), "Receive Payment",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            }
        } else {
  
        }
    }

     public boolean receivePayment(PaymentComposite payment ) {

         try {
            return LoadPaymentWorker.setStatusToPaymentReceived(payment, session) == OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } catch (Exception ex) {
            Logger.getLogger(ReceiveOrderReturnAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;

    }
}
