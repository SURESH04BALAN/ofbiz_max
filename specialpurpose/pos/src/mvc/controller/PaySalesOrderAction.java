package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.orderbase.PaymentAmountInterface;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentInvoiceController;
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
public class PaySalesOrderAction extends ScreenAction {

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

    private OrderActionInterface orderActionInterface;

    public PaySalesOrderAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION,
                PrintSalesInvoiceAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public PaySalesOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    PaymentAmountInterface paymentAmountInterface;
    /*public PayPurchaseOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, JFrame parentFrame, ListAdapterListModel<Order> orderCompListModel) {
     super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane, parentFrame);
     this.orderCompListModel = orderCompListModel;
     this.session = session;
     }*/

    public PaySalesOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, PartyIdInterface partyIdInterface,
            OrderIdInterface orderIdInterface, PaymentAmountInterface payAmountInterface) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = null;
        this.partyIdInterface = partyIdInterface;
        this.orderIdInterface = orderIdInterface;
        this.paymentAmountInterface = payAmountInterface;
        this.session = session;
    }

    public PaySalesOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
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

    @Override
    public void actionPerformed(ActionEvent e) {

//        if (orderCompListModel.getSize() > 0) {
//            for (final Order order : orderCompListModel.getList()) {
        try {
            if (orderActionInterface != null) {

                final Order order = orderActionInterface.getOrder();
                String invoiceTypeId = "";
                String partyId = "";
                if ("SALES_ORDER".equals(order.getOrderType())) {
                    invoiceTypeId = "SALES_INVOICE";
                    partyId = order.getOrderReadHelper().getBillToParty().getString("partyId");
                } else if ("PURCHASE_ORDER".equals(order.getOrderType())) {
                    invoiceTypeId = "PURCHASE_INVOICE";
                    partyId = order.getOrderReadHelper().getBillFromParty().getString("partyId");
                } else {
                    Debug.logError("Order Type Not Handled: " + order.getOrderType(), module);
                }

                if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {

                }
            } else if (partyIdInterface.getPartyId() != null && orderIdInterface.getOrderId() != null && paymentAmountInterface.getPaymentAmount() != null) {
                ControllerOptions options = new ControllerOptions();
                options.addInvoiceType("SALES_INVOICE");
                options.addPartyId(partyIdInterface.getPartyId());
                options.put("Amount", paymentAmountInterface.getPaymentAmount());
                options.addOrderId(orderIdInterface.getOrderId());
                CustomerPaymentInvoiceController.runController(options);
            }
        } //          });
        catch (Exception ex) {
            Debug.logError(ex, module);
        } finally {
        }
    }
  //      } else {
    //          int selection = OrderMaxOptionPane.showConfirmDialog(
    //                 null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    //     }
    //}
}
