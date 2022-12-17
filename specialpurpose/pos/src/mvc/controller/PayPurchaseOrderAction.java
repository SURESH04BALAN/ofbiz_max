package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.orderbase.PaymentAmountInterface;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.payment.FindPaymentListController;
import org.ofbiz.ordermax.payment.purchase.SupplierPaymentInvoiceController;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class PayPurchaseOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Pay Invoice";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private XuiSession session;
    private PartyIdInterface partyIdInterface;
    private OrderIdInterface orderIdInterface;
    PaymentAmountInterface paymentAmountInterface;
    /*public PayPurchaseOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, JFrame parentFrame, ListAdapterListModel<Order> orderCompListModel) {
     super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane, parentFrame);
     this.orderCompListModel = orderCompListModel;
     this.session = session;
     }*/
    public PayPurchaseOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,  PartyIdInterface partyIdInterface,
            OrderIdInterface orderIdInterface, PaymentAmountInterface paymentAmountInterface) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = null;
        this.partyIdInterface = partyIdInterface;
        this.orderIdInterface = orderIdInterface;
        this.paymentAmountInterface = paymentAmountInterface;
        this.session = session;
    }

    public PayPurchaseOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION, session);
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
            return name;
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    public void actionPerformed(ActionEvent e) {

        /* if (orderCompListModel.getSize() > 0) {

         for (final Order order : orderCompListModel.getList()) {

         try {
         if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {
         SupplierPaymentInvoiceController paymentInvoiceController = new SupplierPaymentInvoiceController(order.getPartyId(), null, true, session);
         paymentInvoiceController.loadTwoPanelInternalFrame(FindPaymentListController.module, desktopPane, frame);
         }
         } //          });
         catch (Exception ex) {
         Debug.logError(ex, module);
         } finally {
         }
         }
         } else {
         int selection = OrderMaxOptionPane.showConfirmDialog(
         null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
         }*/
        try {
            if (partyIdInterface != null &&
                    orderIdInterface != null ) {
                   ControllerOptions options = new ControllerOptions();
                options.addInvoiceType("PURCHASE_INVOICE");
                options.addPartyId(partyIdInterface.getPartyId());
                options.put("Amount", paymentAmountInterface.getPaymentAmount());
                options.addOrderId(orderIdInterface.getOrderId());
                SupplierPaymentInvoiceController.runController(options);                
            }
        } //          });
        catch (Exception ex) {
            Debug.logError(ex, module);
        } finally {
        }

    }
}
