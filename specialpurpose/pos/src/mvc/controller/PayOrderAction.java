package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.orderbase.PaymentAmountInterface;
import org.ofbiz.ordermax.orderbase.returns.OrderReturnActionInterface;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.payment.purchase.SupplierPaymentInvoiceController;
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
public class PayOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Pay To Order ";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private PartyIdInterface partyIdInterface;
    private OrderIdInterface orderIdInterface;

    private OrderActionInterface orderActionInterface;
    private OrderReturnActionInterface orderReturnActionInterface;

    public PayOrderAction(javax.swing.JDesktopPane desktopPane, OrderReturnActionInterface orderReturnActionInterface) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION,
                PayOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderReturnActionInterface = orderReturnActionInterface;
    }

    public PayOrderAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION,
                PayOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public PayOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
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

    public PayOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, PartyIdInterface partyIdInterface,
            OrderIdInterface orderIdInterface, PaymentAmountInterface payAmountInterface) {
        super(ActionType.PAY_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = null;
        this.partyIdInterface = partyIdInterface;
        this.orderIdInterface = orderIdInterface;
        this.paymentAmountInterface = payAmountInterface;
        this.session = session;
    }

    public PayOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
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
                loadOrderPayment(order);
            } else if (orderReturnActionInterface != null) {
                ReturnHeaderComposite orderReturn = orderReturnActionInterface.getOrderReturn();
                if (orderReturn != null) {
                    if (orderReturn.getReturnInvoice() != null) {
                    } else if (orderReturn.getReturnOrder() != null) {
                        loadOrderPayment(orderReturn.getReturnOrder());
                    } else {
                        Set<String> payments = orderReturn.getPaymentIds();
                        for (String paymentId : payments) {
                            ControllerOptions options = new ControllerOptions();
                            options.addPaymentId(paymentId);
                            SupplierPaymentInvoiceController.runController(options);
                        }
                    }
                }

            } else if (partyIdInterface.getPartyId() != null && orderIdInterface.getOrderId() != null && paymentAmountInterface.getPaymentAmount() != null) {
                ControllerOptions options = new ControllerOptions();
                options.addInvoiceType("SALES_INVOICE");
                options.addPartyId(partyIdInterface.getPartyId());
                options.put("Amount", paymentAmountInterface.getPaymentAmount());
                options.addOrderId(orderIdInterface.getOrderId());
                CustomerPaymentInvoiceController.runController(options);// paymentInvoiceController = new CustomerPaymentInvoiceController(partyIdInterface.getPartyId(), orderIdInterface.getOrderId() , paymentAmountInterface.getPaymentAmount(), session);
                //paymentInvoiceController.loadTwoPanelInternalFrame(FindPaymentListController.module, desktopPane);

            }
        } //          });
        catch (Exception ex) {
            Debug.logError(ex, module);
        } finally {
        }
    }

    void loadOrderPayment(final Order order) {
        ControllerOptions options = new ControllerOptions();
        String invoiceTypeId = "";
        String partyId = "";
 
            Debug.logInfo("order.getOrderReadHelper().getOrderOpenAmount(): " + order.getOrderReadHelper().getOrderGrandTotal().toString(), module);

        if ("SALES_ORDER".equals(order.getOrderType())) {
            invoiceTypeId = "SALES_INVOICE";
            options.addInvoiceType(invoiceTypeId);
            options.addOrderId(order.orderHeader.getOrderId());
            partyId = order.getOrderReadHelper().getBillToParty().getString("partyId");
            options.addPartyId(partyId);
            options.addParentPaymentTypeId("RECEIPT");
            options.put("Amount", order.getOrderReadHelper().getOrderGrandTotal());
           
            
            CustomerPaymentInvoiceController.runController(options);
            //CustomerPaymentInvoiceController paymentInvoiceController = new CustomerPaymentInvoiceController(partyId, order.getOrderId(), order.getOrderReadHelper().getOrderOpenAmount(),  session);
            //paymentInvoiceController.loadTwoPanelInternalFrame(FindPaymentListController.module, desktopPane);

        } else if ("PURCHASE_ORDER".equals(order.getOrderType())) {
            invoiceTypeId = "PURCHASE_INVOICE";
            options.addInvoiceType(invoiceTypeId);
            options.addOrderId(order.orderHeader.getOrderId());
            partyId = order.getOrderReadHelper().getBillFromParty().getString("partyId");
            options.addPartyId(partyId);

            options.put("Amount", order.getOrderReadHelper().getOrderGrandTotal());
            options.addParentPaymentTypeId("DISBURSEMENT");
            SupplierPaymentInvoiceController.runController(options);//paymentInvoiceController = new SupplierPaymentInvoiceController(partyId, order.getOrderId(), order.getOrderReadHelper().getOrderOpenAmount(), true, session);
            //paymentInvoiceController.loadTwoPanelInternalFrame(FindPaymentListController.module, desktopPane);
        } else {
            Debug.logError("Order Type Not Handled: " + order.getOrderType(), module);
        }

    }
}
