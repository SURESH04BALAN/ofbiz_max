/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import org.ofbiz.ordermax.payment.sales.PaymentEntryHeaderPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import mvc.controller.LoadPaymentWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.InvoiceRolePartyPayment;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.TwoPanelContainerInternalFrame;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.invoice.purchaseinvoice.PurchaseInvoiceController;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.utility.ComponentBorder;

import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author administrator
 */
public class PaymentController extends BaseMainScreen {

    public static final String module = PaymentController.class.getName();
    public PaymentEntryHeaderPanel paymentPanel = null;
    public PaymentApplicationPanel paymentApplicationPanel = null;
    static String PAYMENT_ENTRY_TAB_INDEX = "Payment Entry";
    static String PAYMENT_ALLOCATE_TAB_INDEX = "Payment Allocate";
    boolean isSalesPayment = true;
    String caption = "";
    protected JPanel productCardPanel = null;
    final private ListAdapterListModel<PaymentApplication> paymentApplicationListModel = new ListAdapterListModel<PaymentApplication>();

    String paymentId = null;
    
    public PaymentController(boolean isSalesPayment, XuiSession sess) {
        super(sess);
        this.isSalesPayment = isSalesPayment;
    }

    public PaymentController(String paymentId,  boolean isSalesPayment, XuiSession sess) {
        super(sess);
        this.isSalesPayment = isSalesPayment;
        this.paymentId = paymentId;
    }
        
    protected void setSizeAndLocation(TwoPanelContainerInternalFrame frame){
        int y = 10;
        int x = 200;
        if(ControllerOptions.getDesktopPane()!=null){
        frame.setSize(ControllerOptions.getDesktopPane().getBounds().width-2*x, ControllerOptions.getDesktopPane().getBounds().height-2*y);
        }
        frame.setLocation(x, y);        
    }

    @Override
    public void loadScreen(final ContainerPanelInterface f) {

        setCaption("Payment Detail");

        productCardPanel = new JPanel(new CardLayout());
        paymentPanel = new PaymentEntryHeaderPanel(ControllerOptions.getSession());
        //party selection button
        ActionListener newPartyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                        PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                        Debug.logInfo("billFromPartyId: " + validator.getField().getText(), module);
                        if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
                            String billFromPartyId = validator.getField().getText();

                   
                        } else {
                            //clear dialog
                          /*  paymentPanel.clearDialogFields();
                            orderListModel.clear();
//                            orderListModel.add(order);
                            invoiceCompositeListModel.clear();
                            buttonPanel.btnSaveOrder.setEnabled(true);
                            buttonPanel.btnApproveOrder.setEnabled(false);
                            buttonPanel.btnCancelOrder.setEnabled(false);*/
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(paymentPanel.partyIdTextFieldFrom, ControllerOptions.getSession());
        piValidator1.addActionListener(newPartyAction);
        paymentPanel.partyIdTextFieldFrom.setInputVerifier(piValidator1);

        paymentApplicationPanel = new PaymentApplicationPanel(ControllerOptions.getSession());

        productCardPanel.add(paymentPanel, PAYMENT_ENTRY_TAB_INDEX);
        
//        productCardPanel.add(paymentApplicationPanel, PAYMENT_ALLOCATE_TAB_INDEX);

        comboPostalChanged(PAYMENT_ENTRY_TAB_INDEX);

        baseMainPanelInterface.newItem();
//        OrderHeaderMax orderMax = OrderMaxUtility.loadOrderFromPersistance(orderId, delegator);
//        baseMainPanelInterface.setOrderHeader(orderMax);
//        baseMainPanelInterface.loadItemEditDataModel();

        baseMainPanelInterface.addChangeListener(this);

        PaymentButtonPanel buttonPanel = new PaymentButtonPanel();

//        OrderMaxUtility.addAPanelToPanel(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(productCardPanel, f.getPanelDetail());
        paymentPanel.panelPaymentDetails.setLayout(new BorderLayout());
        paymentPanel.panelPaymentDetails.add(BorderLayout.CENTER, paymentApplicationPanel);   
        ComponentBorder.doubleRaisedLoweredBevelBorder(paymentPanel.panelPaymentDetails, "Payment Application");        
        OrderMaxUtility.addAPanelToPanel(buttonPanel, f.getPanelButton());

        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    baseMainPanelInterface.saveItem();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        buttonPanel.getBtnAllocate().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //        baseMainPanelInterface.newItem();
                if (paymentPanel.isValidAmount()) {
                    if (canAllocatePayment()) {
                        paymentApplicationPanel.setPaymentAmount(paymentPanel.getPaymentAmount());
/*                        paymentApplicationPanel.setBillingContact(paymentPanel.getContactDetails());
                        paymentApplicationPanel.setPartyId(paymentPanel.getPartyId());
                        paymentApplicationPanel.setBankDetail(paymentPanel.getBankDetail());
                        paymentApplicationPanel.setPaymentDate(paymentPanel.getPaymentDate());
                        */
                        paymentApplicationPanel.loadItem();
                        comboPostalChanged(PAYMENT_ALLOCATE_TAB_INDEX);
                    }
                }
            }
        });

        buttonPanel.getBtnAccept().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                baseMainPanelInterface.loadItem();
                final ClassLoader cl = getClassLoader();
                Thread.currentThread().setContextClassLoader(cl);

                if (paymentApplicationPanel.getTotalAllocatedAndUnallocated().compareTo(paymentPanel.getTotalPaymentsIncDiscount()) != 0) {
                    String message = "Payment amount and allocated amount is not equal.";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Allocation", JOptionPane.YES_OPTION);
                    return;
                }

                if (paymentApplicationPanel.getUnallocateAmount().compareTo(BigDecimal.ZERO) != 0) {
                    String message = "There is unallocated amount. Do you want to save?";

                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Allocation", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                savePayment();

                comboPostalChanged(PAYMENT_ENTRY_TAB_INDEX);
            }
        });
//        baseMainPanelInterface.setProductListArray((ProductListArray) treePanel.getTreeDataList());
//        f.setSize(1200, 700);
//        f.setSize(1000, 700);
//        f.setLocationRelativeTo(null);
//        f.textField = paymentPanel.getPartyTextField();
//        f.setVisible(true);

        if (paymentId != null) {
            loadPayment(paymentId);
            //set order actions
//            setOrderToActions(panel.getOrder());

//            loadFinancialData(panel.partyIdTextField.getText());
//            setCaption(contFrame);
        }/* else {
            buttonPanel.btnSaveOrder.setEnabled(true);
            buttonPanel.btnApproveOrder.setEnabled(false);
            buttonPanel.btnCancelOrder.setEnabled(false);

        }*/
        
        setInitialFocusField(paymentPanel.getPartyTextField());
    }

    @Override
    protected void createTreePanel() {
    }

    public void loadPayment(String paymentId) {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);
            
            PaymentComposite payment = LoadPaymentWorker.loadPayment(paymentId, ControllerOptions.getSession());
//            paymentPanel.c() ;
            paymentPanel.setPaymentComposite(payment);
            //update the dialog
            paymentPanel.setDialogFields();

            paymentApplicationListModel.clear();

            paymentApplicationListModel.addAll(payment.getPaymentApplicationCompositeList().getList());
            paymentApplicationPanel.setPaymentApplicationList(paymentApplicationListModel);
//sur        ShoppingCart shopingCart = LoadPurchaseOrderWorker.loadShopingCart(invoiceId, ControllerOptions.getSession());
//sur        order.setShopingCart(shopingCart);
            /*
            
            //set order
            panel.setOrder(order);
            
//            LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, shopingCart);
            
            //load invoice composites
//        loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel);
            invoicesListModel.clear();
//            LoadInvoiceWorker.loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel, ControllerOptions.getSession());
            //update the dialog
            panel.setDialogFields();
            
            //setup table
            panel.setupEditOrderTable();
            
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            
            //load table
            panel.reloadItemDataModel(order.getInvoiceItemCompositeList());  //                  }
            
            //add empty name
            addNewOrderItem();
            */
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void addItem(String id) throws Exception {
        baseMainPanelInterface.addItem(id);
    }

    @Override
    public void refreshScreen() {
        baseMainPanelInterface.refreshScreen();
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        baseMainPanelInterface.addItem(id, price, qty);
    }
    boolean isNew = false;
    boolean isModified = false;
    String orderStatus;
    String orderId;
    String partyId = "";

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(OrderMaxUtility.ITEM_NEW)) {
            isNew = true;
            isModified = false;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_SAVED)) {
            isNew = false;
            isModified = false;
            orderId = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_MODIFIED)) {
            isModified = true;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_STATUS_CHANGED)) {
            orderStatus = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.PARTY_CHANGED)) {
            partyId = (String) event.getNewValue();
        }

        caption = "Payment Entry - " + partyId + "[";
        if (isNew) {
            caption = caption.concat(" New ");
        } else {
            caption = caption.concat("Order Id: " + orderId);
        }

        if (orderStatus != null) {
            caption = caption.concat(", Order Status: " + orderStatus);
        }

        caption = caption.concat(" ]");

        if (isModified) {
            caption = caption.concat(" - Modified ");
        }

        setCaption(caption);
    }
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    String visibleCardName = null;

    public void comboPostalChanged(String desc) {
        if (productCardPanel != null) {
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            visibleCardName = desc;
            if (visibleCardName.equals(PAYMENT_ENTRY_TAB_INDEX)) {
                baseMainPanelInterface = paymentPanel;
            } else if (visibleCardName.equals(PAYMENT_ALLOCATE_TAB_INDEX)) {
                baseMainPanelInterface = paymentApplicationPanel;
            }
        }
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

    void savePayment() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
/*        String paymentId = delegator.getNextSeqId("Payment");
         input.put("paymentId", paymentId);
         input.put("paymentTypeId", "VENDOR_PAYMENT");
         input.put("paymentMethodTypeId", "CASH");
         input.put("partyIdFrom", "COMPANY");
         input.put("partyIdTo", "MAX SPICES");
         input.put("roleTypeIdTo", "MANAGER");
         input.put("statusId", "PMNT_CONFIRMED");
         input.put("effectiveDate", UtilDateTime.nowTimestamp());
         input.put("paymentRefNum", referenceNumber);
         input.put("amount", notApplied);
         input.put("currencyUomId", "USD");
         input.put("actualCurrencyUomId", "USD");
         */
        String paymentTypeId = "VENDOR_PAYMENT";
        String paymentMethodTypeId = "CASH";
        String referenceNumber = null;
        String partyIdFrom = "COMPANY";
        String partyIdTo = paymentPanel.getPartyId();
        BigDecimal notApplied = null;
        String currency = "USD";
        Map<String, BigDecimal> payments = new HashMap<String, BigDecimal>();
        if (paymentPanel.getChequeTotal().compareTo(BigDecimal.ZERO) != 0) {
            paymentMethodTypeId = "COMPANY_CHECK";
            notApplied = paymentPanel.getChequeTotal();
//        String paymentId
            GenericValue genValue = InvoicePaymentHelper.createSupplierPayment(paymentMethodTypeId,
                    referenceNumber,
                    partyIdFrom,
                    partyIdTo,
                    notApplied,
                    currency,
                    ControllerOptions.getSession().getDelegator());
            payments.put(genValue.getString("paymentId"), notApplied);
//.add(genValue);
//            String paymentId = genValue.getString("paymentId");
        }

        if (paymentPanel.getCashTotal().compareTo(BigDecimal.ZERO) != 0) {
            notApplied = paymentPanel.getCashTotal();
            paymentMethodTypeId = "CASH";
//        String paymentId
            GenericValue genValue = InvoicePaymentHelper.createSupplierPayment(paymentMethodTypeId,
                    referenceNumber,
                    partyIdFrom,
                    partyIdTo,
                    notApplied,
                    currency,
                    ControllerOptions.getSession().getDelegator());
            payments.put(genValue.getString("paymentId"), notApplied);
//            payments.add(genValue);
//            String paymentId = genValue.getString("paymentId");
        }
        if (paymentPanel.getCardTotal().compareTo(BigDecimal.ZERO) != 0) {
            notApplied = paymentPanel.getCardTotal();
            paymentMethodTypeId = "CREDIT_CARD";
//        String paymentId
            GenericValue genValue = InvoicePaymentHelper.createSupplierPayment(paymentMethodTypeId,
                    referenceNumber,
                    partyIdFrom,
                    partyIdTo,
                    notApplied,
                    currency,
                    ControllerOptions.getSession().getDelegator());
            payments.put(genValue.getString("paymentId"), notApplied);
//            payments.add(genValue);
//            String paymentId = genValue.getString("paymentId");
        }

        if (paymentPanel.getEftTotal().compareTo(BigDecimal.ZERO) != 0) {
            notApplied = paymentPanel.getEftTotal();
            paymentMethodTypeId = "EFT_ACCOUNT";
//        String paymentId
            GenericValue genValue = InvoicePaymentHelper.createSupplierPayment(paymentMethodTypeId,
                    referenceNumber,
                    partyIdFrom,
                    partyIdTo,
                    notApplied,
                    currency,
                    ControllerOptions.getSession().getDelegator());
            payments.put(genValue.getString("paymentId"), notApplied);
//            payments.add(genValue);
//            String paymentId = genValue.getString("paymentId");
        }

        List<InvoiceRolePartyPayment> resultList = paymentApplicationPanel.getResultlist();
        int currPaymentIndex = 0;
        if (payments.isEmpty() == false) {
            GenericValue userLogin = ControllerOptions.getSession().getUserLogin();
            LocalDispatcher dispatcher = ControllerOptions.getSession().getDispatcher();

//            GenericValue payment = payments.get(currPaymentIndex);
//            BigDecimal amount = payment.getBigDecimal("amount");
//        createPaymentApplication(String paymentId, String invoiceId, BigDecimal notApplied, GenericValue userLogin, LocalDispatcher dispatcher)
            for (InvoiceRolePartyPayment invoicePayment : resultList) {
                if (invoicePayment.getAllocationAmount().compareTo(BigDecimal.ZERO) != 0) {

                    for (Map.Entry<String, BigDecimal> entryDept : payments.entrySet()) {

                        if (entryDept.getValue().compareTo(BigDecimal.ZERO) != 0) {

                            if (invoicePayment.getAllocationAmount().compareTo(entryDept.getValue()) <= 0) {

                                InvoicePaymentHelper.createPaymentApplication(entryDept.getKey(), invoicePayment.getInvoice().getString("invoiceId"),
                                        invoicePayment.getAllocationAmount(), userLogin, dispatcher);
                                BigDecimal amountLeft = entryDept.getValue().subtract(invoicePayment.getAllocationAmount());
                                invoicePayment.setAllocationAmount(BigDecimal.ZERO);
                                payments.put(entryDept.getKey(), amountLeft);
                                break;
                            } else {
                                InvoicePaymentHelper.createPaymentApplication(entryDept.getKey(), invoicePayment.getInvoice().getString("invoiceId"),
                                        entryDept.getValue(), userLogin, dispatcher);
                                BigDecimal amountLeft = invoicePayment.getAllocationAmount().subtract(entryDept.getValue());
                                invoicePayment.setAllocationAmount(amountLeft);
                                payments.put(entryDept.getKey(), BigDecimal.ZERO);
                            }
                        }
                    }
                }
            }
        }
    }

    boolean canAllocatePayment() {
   
        boolean result = true;
        if (paymentPanel.getTotalPaymentsIncDiscount().compareTo(paymentPanel.getTotalDiffPayments()) != 0) {
            OrderMaxOptionPane.showMessageDialog(null, "Invalid Payment: Payment total is not matching..");
            result = false;
        }
//        BigDecimal chqAmount = BigDecimal.ZERO;
//        BigDecimal cardAmount = BigDecimal.ZERO;        

        return result;
    }


}
