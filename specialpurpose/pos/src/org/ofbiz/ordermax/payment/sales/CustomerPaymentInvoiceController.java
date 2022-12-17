/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javolution.util.FastMap;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateNewShipGroupAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.LoadOrderWorker;
import mvc.controller.LoadPaymentWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.PayPurchaseOrderAction;
import mvc.controller.PrintPickSlipAction;
import mvc.controller.PrintPurchaseInvoiceAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.ShipmentReceiptAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import mvc.controller.ViewOrderHistoryAction;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.accounting.payment.BillingAccountWorker;
import org.ofbiz.accounting.payment.PaymentWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderEntryTableModel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.Payment;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceAction;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.payment.PaymentActionInterface;
import org.ofbiz.ordermax.payment.PaymentApplicationPanel;
import org.ofbiz.ordermax.payment.PaymentIdVerifyValidator;
import org.ofbiz.ordermax.payment.PaymentStatusActionPopupButton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.SimplePaymentButtonPanel;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodEntryPanel;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentTypePaymentEntryPanel;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderEntryKeyTableModel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.ProductSelectionPanel;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author siranjeev
 */
public class CustomerPaymentInvoiceController extends BaseMainScreen implements PaymentActionInterface {

    public static final String module = PurchaseInvoiceController.class.getName();
    public CustomerPaymentEntryHeaderPanel paymentEntryHeaderPanel = null;
    public final String caption = "Pay Invoice";
    public PosScreen pos = null;
    protected String paymentId = null;
    protected String customerPartyId = null;
    protected String orderId = null;
    protected String invoiceId = null;
    protected BigDecimal amount = BigDecimal.ZERO;
    protected PaymentStatusActionPopupButton paymentStatusActionPopupButton;

    static public CustomerPaymentInvoiceController runController(ControllerOptions options) {

        CustomerPaymentInvoiceController controller = new CustomerPaymentInvoiceController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(CustomerPaymentInvoiceController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(CustomerPaymentInvoiceController.module, options.getDesktopPane());
        }
        return controller;
    }

    protected ControllerOptions options;

    protected CustomerPaymentInvoiceController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options.getCopy();
        paymentId = options.getPaymentId();
        this.customerPartyId = options.getPartyId();
        this.orderId = options.getOrderId();
        this.invoiceId = options.getInvoiceId();
        this.amount = (BigDecimal) options.get("Amount");
    }

    private SimplePaymentButtonPanel buttonPanel = null;
    protected CustomerPaymentInvoiceApplicationPanel paymentInvoiceApplicationPanel = null;
    public PaymentApplicationPanel paymentAllocatedPanel = null;

    final protected ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final protected ListAdapterListModel<PaymentComposite> actPaymentCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    final protected ListAdapterListModel<PaymentApplication> paymentApplicationListModel = new ListAdapterListModel<PaymentApplication>();

    protected Order toPayOrder;
    protected InvoiceComposite toPayInvoice;
    protected PaymentMethodEntryPanel paymentMethodEntryPanel = null;
    protected Set<String> savePaymentIds = new HashSet<String>();

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }
    boolean isLoading = false;

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {
        try {
            isLoading = true;
            ControllerOptions option = options.getCopy();
            paymentEntryHeaderPanel = new CustomerPaymentEntryHeaderPanel(option);
            paymentEntryHeaderPanel.txtPaymentAmount.setInputVerifier(new BigDecimalValidator(paymentEntryHeaderPanel.txtPaymentAmount));
            try {
                paymentEntryHeaderPanel.paymentTypeComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType("CUSTOMER_PAYMENT"));
            } catch (Exception ex) {
                Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            paymentInvoiceApplicationPanel = new CustomerPaymentInvoiceApplicationPanel();
            paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);

            paymentAllocatedPanel = new PaymentApplicationPanel(ControllerOptions.getSession());

            ActionListener paymentIdTextChangeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() instanceof PaymentIdVerifyValidator) {
                        PaymentIdVerifyValidator validator = (PaymentIdVerifyValidator) e.getSource();
                        String paymentId = validator.getField().getText();
                        Debug.logInfo("paymentId: " + paymentId, module);
                        try {
//                        PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId,  ControllerOptions.getSession());
                            loadPayment(paymentId);
                        } catch (Exception ex) {
                            Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            PaymentIdVerifyValidator paymentIdVerifyValidator = new PaymentIdVerifyValidator(paymentEntryHeaderPanel.txtPaymentId, ControllerOptions.getSession());
            paymentIdVerifyValidator.addActionListener(paymentIdTextChangeAction);
            paymentEntryHeaderPanel.txtPaymentId.setInputVerifier(paymentIdVerifyValidator);

            buttonPanel = new SimplePaymentButtonPanel();
            paymentStatusActionPopupButton = new PaymentStatusActionPopupButton(buttonPanel.btnStatusAction, this, ControllerOptions.getDesktopPane());
            ActionListener paymentStatusChange = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadPayment(e.getActionCommand());
                    //set order actions
                    setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

                    setCaption(contFrame);

                }
            };

            paymentStatusActionPopupButton.setCallBackListner(paymentStatusChange);

//        copyToButton = new OrderReturnCopyToPopupButton(panel.btnCopyTo, this, ControllerOptions.getDesktopPane());
//        copyToButton = new CopyToPopupButton(buttonPanel.btnCopyTo);
            paymentMethodEntryPanel = new PaymentMethodEntryPanel();

            paymentEntryHeaderPanel.panelPaymentMethodDetails.setLayout(new BorderLayout());
            paymentEntryHeaderPanel.panelPaymentMethodDetails.add(BorderLayout.CENTER, paymentMethodEntryPanel);
            OrderMaxUtility.addAPanelGrid(paymentEntryHeaderPanel, contFrame.getPanelDetail());
            ComponentBorder.doubleRaisedLoweredBevelBorder(paymentEntryHeaderPanel.panelPaymentMethodDetails, "Payment Entry");

//            paymentEntryHeaderPanel.panelPaymentDetails.setLayout(new BorderLayout());
//            paymentEntryHeaderPanel.panelPaymentDetails.add(BorderLayout.CENTER, paymentInvoiceApplicationPanel);
//            paymentEntryHeaderPanel.panelPaymentDetails.setLayout(new BorderLayout());
            //paymentEntryHeaderPanel.paymentTabbedPane.add("Payment Application", paymentInvoiceApplicationPanel);
//            paymentEntryHeaderPanel.panelApplicationDetail.setLayout(new BorderLayout());
//            paymentEntryHeaderPanel.panelApplicationDetail.add(BorderLayout.CENTER, paymentAllocatedPanel);
            //paymentEntryHeaderPanel.paymentTabbedPane.add("Payments Applied", paymentAllocatedPanel);            
            //          ComponentBorder.doubleRaisedLoweredBevelBorder(paymentEntryHeaderPanel.panelApplicationDetail, "Payment Application");
            OrderMaxUtility.addAPanelToPanel(buttonPanel, paymentEntryHeaderPanel.panelButton);
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), contFrame.getPanelSelecton());
//            OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

//        paymentEntryHeaderPanel.panelCustomerPartyIdPicker.textIdField.setText(customerPartyId);
//        String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
            //      paymentEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
            /*        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             cancelButtonPressed();
             }
             });
             */
            /*            buttonPanel.btnNewOrder.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
             //                paymentEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
             newPayment(billToPartyId, null);
             setCaption(contFrame);
             }
             });
             */
            //party selection button
            ActionListener loadInvoicesAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.getEntityValue() != null) {
                        loadInvoices((String)paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.getEntityValue());
                    }
                }
            };
            paymentInvoiceApplicationPanel.btnLoadInvoices.addActionListener(loadInvoicesAction);

            //party selection button
            ActionListener newPartyAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                    Debug.logInfo("newPartyAction billFromPartyId: " + validator.getField().getText(), module);

                    if (e.getSource() instanceof PartyIdVerifyValidator && !isLoading) {
                        try {

                            //PartyIdVerifyValidator 
                            //        validator = (PartyIdVerifyValidator) e.getSource();
                            Debug.logInfo("newPartyAction paymentFromPartyId: " + validator.getField().getText(), module);
                            /* if (UtilValidate.isEmpty(paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText())) {
                             paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.setText(ControllerOptions.getSession().getCompanyPartyId());
                             }*/
                            if (orderId != null) {
                                //newPaymentToOrder(orderId);
                                //setInitialFocusField(paymentEntryHeaderPanel.txtPaymentAmount);
                                paymentEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                            } else if (paymentId != null) {
                                paymentEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                                paymentEntryHeaderPanel.txtPaymentId.setEnabled(false);
                            } else if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
                                String paymentFromPartyId = validator.getField().getText();
                                if (paymentEntryHeaderPanel.getPaymentComposite() != null) {
                                    String partyId = paymentEntryHeaderPanel.getPaymentComposite().getPayment().getpartyIdTo();

                                    if (UtilValidate.isEmpty(partyId) || !partyId.equals(paymentFromPartyId)) {

                                        paymentFromPartyId = validator.getField().getText();
                                        String paymentToPartyId = paymentEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText();
                                        Debug.logInfo("newPartyAction paymentToPartyId: " + paymentToPartyId, module);
                                        newPayment(paymentFromPartyId, paymentToPartyId);
//                            loadInvoices(paymentFromPartyId);

                                    }
                                } else {
                                    paymentFromPartyId = validator.getField().getText();
                                    String paymentToPartyId = paymentEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText();
                                    Debug.logInfo("newPartyAction paymentToPartyId: " + paymentToPartyId, module);
                                    newPayment(paymentFromPartyId, paymentToPartyId);
                                }
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(paymentEntryHeaderPanel.geCustomerPartyIdFromTextField(), ControllerOptions.getSession());
            piValidator1.addActionListener(newPartyAction);
            paymentEntryHeaderPanel.geCustomerPartyIdFromTextField().setInputVerifier(piValidator1);
            //order selection button

//        CreatePaymentAction createPaymentAction = new CreatePaymentAction(actPaymentCompositeListModel, ControllerOptions.getSession());
//        buttonPanel.btnSaveOrder.setAction(createPaymentAction);
            buttonPanel.btnSaveOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paymentEntryHeaderPanel.isValidValues()) {
                        paymentEntryHeaderPanel.getDialogFields();

                        PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();
                        //if (paymentEntryHeaderPanel.paymentTabbedPane.getSelectedIndex() == 0) {
//                if (paymentComposite.getPayment().getpaymentId() == null || paymentComposite.getPayment().getpaymentId().isEmpty()) {
                        try {
                            Map<String, PaymentTypePaymentEntryPanel> paymentMap = paymentMethodEntryPanel.getPaymentMethodEntry();
                            BigDecimal amount = new BigDecimal(paymentEntryHeaderPanel.txtPaymentAmount.getText());
                            BigDecimal paymentMethodAmount = BigDecimal.ZERO;
                            for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                                if (UtilValidate.isNotEmpty(entryPanel.getValue().txtPaymentAmount.getText())) {
                                    BigDecimal val = new BigDecimal(entryPanel.getValue().txtPaymentAmount.getText());
                                    paymentMethodAmount = paymentMethodAmount.add(val);
                                }
                            }

                            if (amount.compareTo(paymentMethodAmount) != 0) {
                                int result = OrderMaxOptionPane.showConfirmDialog(null, "Paymount amount and payment enteries do not match", "Save Payment",
                                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                                paymentEntryHeaderPanel.txtPaymentAmount.requestFocus();
                                return;
                            }

                            //else {
                            if (paymentComposite.getBillingAccount() != null) {
                                Map<String, Object> paymentValueMap = FastMap.newInstance();

                                for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                                    if (UtilValidate.isNotEmpty(entryPanel.getValue().txtPaymentAmount.getText())) {
                                        BigDecimal amountVal = new BigDecimal(entryPanel.getValue().txtPaymentAmount.getText());
                                        if (!BigDecimal.ZERO.equals(amountVal)) {
                                            paymentValueMap.put("billingAccountId", paymentComposite.getBillingAccount().getbillingAccountId());
                                            paymentValueMap.put("currencyUomId", paymentComposite.getPayment().getcurrencyUomId());
                                            paymentValueMap.put("partyIdFrom", paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText());
                                            paymentValueMap.put("partyIdTo", paymentEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText());
                                            paymentValueMap.put("statusId", "PMNT_NOT_PAID");
                                            paymentValueMap.put("amount", new BigDecimal(entryPanel.getValue().txtPaymentAmount.getText()));
                                            paymentValueMap.put("paymentTypeId", paymentComposite.getPayment().getpaymentTypeId());
                                            paymentValueMap.put("paymentMethodTypeId", entryPanel.getValue().getPaymentMethodType().getpaymentMethodTypeId());
                                            paymentValueMap.put("paymentRefNum", entryPanel.getValue().txtPaymentReference.getText());

                                            String paymentId = LoadPaymentWorker.createPaymentAndApplication(paymentValueMap, ControllerOptions.getSession());
                                            savePaymentIds.add(paymentId);
                                        }
                                    }
                                }
                                if (savePaymentIds.size() > 0) {
                                    paymentId = savePaymentIds.iterator().next();
                                    loadExistingPayment(paymentId);
                                    setCaption(contFrame);
                                }
                            } else if (orderId != null) {
                                //create payment method type map 
                                //this will create multiple payment record for each 
                                //payment method type id
                                Map<String, Object> paymentValueMap = FastMap.newInstance();
                                paymentValueMap.put("orderId", orderId);

                                for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                                    if (UtilValidate.isNotEmpty(entryPanel.getValue().txtPaymentAmount.getText())) {
                                        paymentValueMap.put(entryPanel.getValue().getPaymentMethodType().getpaymentMethodTypeId() + "_amount", entryPanel.getValue().txtPaymentAmount.getText());
                                        paymentValueMap.put(entryPanel.getValue().getPaymentMethodType().getpaymentMethodTypeId() + "_reference", entryPanel.getValue().txtPaymentReference.getText());
                                    }
                                }

                                Map<String, String> result;
                                try {
                                    result = LoadPaymentWorker.receiveOfflinePayment(paymentValueMap, ControllerOptions.getSession());

                                    if (!result.isEmpty()) {
                                        if (result.size() > 1) {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("Following Payments records have been created: ");
                                            for (Map.Entry<String, String> paymentIds : result.entrySet()) {
                                                stringBuilder.append(paymentIds.getKey()).append(": ").append(paymentIds.getValue());
                                            }
                                            stringBuilder.append("Displaying first payment id ");
                                            OrderMaxOptionPane.showMessageDialog(null, stringBuilder);
                                        }
                                        String paymentId = "";
                                        for (Map.Entry<String, String> paymentIds : result.entrySet()) {
                                            paymentId = paymentIds.getValue();
                                            savePaymentIds.add(paymentId);
                                        }
                                        //loadPayment(paymentId);
                                        //set order actions
                                        //setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
                                        loadExistingPayment(paymentId);
                                        setCaption(contFrame);
//                                            GenericValue val = LoadPaymentWorker.loadPaymentGV(paymentId, ControllerOptions.getSession());
//                                            paymentComposite.setPayment(new Payment(val));
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else {
                                LoadPaymentWorker.savePayment(paymentComposite, ControllerOptions.getSession());
                                loadExistingPayment(paymentComposite.getPayment().getpaymentId());
                                setCaption(contFrame);
                            }
                            //}
                        } catch (Exception ex) {
                            Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        paymentEntryHeaderPanel.setDialogFields();
                        setCaption(contFrame);
                    }
                }
            });

            paymentInvoiceApplicationPanel.btnSaveOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();
                    if (paymentComposite.getBillingAccount() == null) {
                        for (InvoiceComposite val : invoiceCompositeListModel.getList()) {
                            if (val.getAllocatedAmount() != null && val.getAllocatedAmount().compareTo(BigDecimal.ZERO) != 0) {
                                PaymentApplication paymentApplication
                                        = LoadPaymentWorker.createPaymentApplication(
                                                paymentComposite.getPayment().getpaymentId(),
                                                val.getInvoice().getinvoiceId(),
                                                val.getAllocatedAmount());
                                paymentComposite.getPaymentApplicationCompositeList().add(paymentApplication);
                            }
                        }
                    } else {
                        for (InvoiceComposite val : invoiceCompositeListModel.getList()) {
                            if (val.getAllocatedAmount() != null && val.getAllocatedAmount().compareTo(BigDecimal.ZERO) != 0) {
                                try {
                                    Map<String, Object> inMap = FastMap.newInstance();
                                    inMap.put("invoiceId", val.getInvoice().getinvoiceId());
                                    LoadPaymentWorker.capturePaymentsByInvoice(inMap, ControllerOptions.getSession());
                                } catch (Exception ex) {
                                    Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            });

            paymentInvoiceApplicationPanel.cbShowZeroAmount.addChangeListener(new javax.swing.event.ChangeListener() {
                @Override
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    //loadInvoices(paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText());
                }
            });

            paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        List<Map<String, Object>> billingAccountList = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(),
                                paymentEntryHeaderPanel.uomComboBox.getSelectedItem().getuomId(), paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(), ControllerOptions.getSession().getDelegator(), ControllerOptions.getSession().getDispatcher());

//                        if (Integer.parseInt(paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter number bigger than 0", "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                        //                      }
                    } catch (GeneralException ex) {
                        Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

//        List<Map<String, Object>> billingAccountList = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(), 
//                uomComboBox.getSelectedItem().getuomId(), panelCustomerPartyIdFromPicker.textIdField.getText(), delegator, ControllerOptions.getSession().getDispatcher());
            paymentInvoiceApplicationPanel.btnAutoAllocate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    BigDecimal allocatedAmount = paymentInvoiceApplicationPanel.getAllocatedAmount();//new BigDecimal(txtAllocatedAmount.getText());
                    BigDecimal unallocatedAmount = paymentInvoiceApplicationPanel.getUnallocateAmount(); //new BigDecimal(txtBalanceAmt.getText());
                    BigDecimal paymentAmt = paymentEntryHeaderPanel.getPaymentAmount();

                    Debug.logInfo("unallocatedAmount " + unallocatedAmount.toString(), "hi");
                    Debug.logInfo("paymentAmt " + paymentAmt.toString(), "hi");

                    if (allocatedAmount.compareTo(paymentAmt) < 0) {

                        BigDecimal amountToBeAllocated = paymentAmt.subtract(allocatedAmount, MathContext.UNLIMITED); //new BigDecimal(txtBalanceAmt.getText());
//                    allocatedAmount = BigDecimal.ZERO;

                        for (InvoiceComposite val : invoiceCompositeListModel.getList()) {
                            Debug.logInfo("unallocatedAmount " + unallocatedAmount.toString(), "hi");
//                if (val.isSelected()) {
                            BigDecimal outAmt = val.getOutstandingAmount();
                            if (outAmt.compareTo(amountToBeAllocated) <= 0) {
                                val.setAllocatedAmount(outAmt);
                                allocatedAmount = allocatedAmount.add(val.getAllocatedAmount());
                            } else {
                                val.setAllocatedAmount(amountToBeAllocated);
                                allocatedAmount = allocatedAmount.add(val.getAllocatedAmount());
                                break;
                            }

                            amountToBeAllocated = amountToBeAllocated.subtract(val.getAllocatedAmount(), MathContext.UNLIMITED);
//                }

                        }

                        paymentInvoiceApplicationPanel.updateUI();
                    }

                    unallocatedAmount = paymentAmt.subtract(allocatedAmount, MathContext.UNLIMITED); //new BigDecimal(txtBalanceAmt.getText());        
                    paymentInvoiceApplicationPanel.setUnAllocatedAmount(unallocatedAmount);//.setText(unallocatedAmount.toString());
                    paymentInvoiceApplicationPanel.setAllocatedAmount(allocatedAmount);
//                invoiceCompositePaymentTableModel.setListModel(invoiceCompositePaymentListModel);
                    paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);

                    setCaption(contFrame);
                }
            });

            paymentInvoiceApplicationPanel.btnClearAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();

                    paymentInvoiceApplicationPanel.setPaymentAmount(paymentEntryHeaderPanel.getPaymentAmount());
                    paymentInvoiceApplicationPanel.setUnAllocatedAmount(BigDecimal.ZERO);
                    paymentInvoiceApplicationPanel.setAllocatedAmount(BigDecimal.ZERO);

                    for (InvoiceComposite val : invoiceCompositeListModel.getList()) {

                        val.setAllocatedAmount(BigDecimal.ZERO);
                    }
                    //paymentInvoiceApplicationPanel.fireTableDataChanged();
//                paymentInvoiceApplicationPanel.setUnAllocatedAmount(paymentComposite.getPayment().getamount().subtract(paymentComposite.getAppliedAmount()));
//                paymentInvoiceApplicationPanel.setAllocatedAmount(paymentComposite.getAppliedAmount());
//                paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);                
                    setCaption(contFrame);
                }
            }
            );

            if (paymentId != null) {
//            loadExistingPayment(paymentId);
                loadPayment(paymentId);
                //set order actions
                setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
                setCaption(contFrame);
                setInitialFocusField(paymentEntryHeaderPanel.txtPaymentId);
            } else if (orderId != null) {
                paymentEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                newPaymentToOrder(orderId);
                setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
                if (amount != null) {
                    paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
                    paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                }
                setInitialFocusField(paymentEntryHeaderPanel.txtPaymentAmount);
                setCaption(contFrame);
            } else {
                //create new payments
                String billToPartyId = XuiSession.getCompanyPartyId();;
                newPayment(customerPartyId, billToPartyId);
//                paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
//                paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                buttonPanel.btnSaveOrder.setEnabled(true);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                setInitialFocusField(paymentEntryHeaderPanel.getCustomerPartyIdFromTextField());
                if (amount != null) {
                    paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
                    paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                }
//            buttonPanel.btnCancelOrder.setEnabled(false);
            }
//        panel.setProductListArray((ProductListArray) treePanel.getTreeDataList());

        } finally {
            isLoading = false;
        }
    }
    ActionListener tableDataChangeAction = null;
    //update the dialog
    int panelApplicationIndex = -1;
    int panelAllocatedIndex = -1;

    void setDialogFields() {
        paymentEntryHeaderPanel.setDialogFields();
        paymentInvoiceApplicationPanel.setDialogFields();
        paymentMethodEntryPanel.setDialogFields();
        paymentAllocatedPanel.setDialogFields();
        if (UtilValidate.isNotEmpty(paymentEntryHeaderPanel.getPaymentComposite())
                && (UtilValidate.isNotEmpty(paymentEntryHeaderPanel.getPaymentComposite().getOutstandingAmount())
                && BigDecimal.ZERO.compareTo(paymentEntryHeaderPanel.getPaymentComposite().getOutstandingAmount()) == 0)
                && (UtilValidate.isNotEmpty(paymentEntryHeaderPanel.getPaymentComposite().getUncapturedAmount())
                && BigDecimal.ZERO.compareTo(paymentEntryHeaderPanel.getPaymentComposite().getUncapturedAmount()) == 0)) {
            paymentEntryHeaderPanel.paymentTabbedPane.addTab("Payments Applied", paymentAllocatedPanel);
        } else {
            paymentEntryHeaderPanel.paymentTabbedPane.addTab("Payment Application", paymentInvoiceApplicationPanel);
        }
    }

    void clearDialogFields() {
        paymentEntryHeaderPanel.clearDialogFields();
        paymentInvoiceApplicationPanel.clearDialogFields();
        paymentMethodEntryPanel.clearDialogFields();
        paymentAllocatedPanel.clearDialogFields();
        invoiceCompositeListModel.clear();
        actPaymentCompositeListModel.clear();
        paymentApplicationListModel.clear();
//        if (panelApplicationIndex != -1) {
        paymentEntryHeaderPanel.paymentTabbedPane.remove(paymentInvoiceApplicationPanel);
//        }
//        if (panelAllocatedIndex != -1) {
        paymentEntryHeaderPanel.paymentTabbedPane.remove(paymentAllocatedPanel);
//        }

    }

    void setPaymentComposite(PaymentComposite paymentComposite) {
        paymentEntryHeaderPanel.setPaymentComposite(paymentComposite);
        paymentMethodEntryPanel.setPaymentComposite(paymentComposite);
        actPaymentCompositeListModel.add(paymentComposite);
        paymentApplicationListModel.addAll(paymentComposite.getPaymentApplicationCompositeList().getList());
        paymentAllocatedPanel.setPaymentApplicationList(paymentApplicationListModel);
    }

    void loadExistingPayment(String paymentId) {
        try {
            clearDialogFields();

            PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId, ControllerOptions.getSession());

            //set order
            setPaymentComposite(paymentComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

            //loadInvoices(paymentToPartyId);
            //setup table
//            paymentInvoiceApplicationPanel.setupEditOrderTable();
//            paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().addActionListener(tableDataChangeAction);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadBillingAccounts() {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);
            PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();
            List<BillingAccount> billingAccountList = LoadBillingAccountWorker.getPartyBillingAccountList(
                    paymentComposite.getPayment().getcurrencyUomId(),
                    paymentComposite.getPayment().getpartyIdFrom(),
                    ControllerOptions.getSession().getDelegator());

            BillingAccount billingAccount = new BillingAccount();
            billingAccount.setdescription("<None>");
            billingAccount.setbillingAccountId(null);
            billingAccountList.add(billingAccount);
            paymentEntryHeaderPanel.billingAccountComboBox.setDataList(billingAccountList);

            /*List<Map<String, Object>> billingAccountListMap = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(),
             paymentEntryHeaderPanel.uomComboBox.getSelectedItem().getuomId(),
             paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(),
             delegator, ControllerOptions.getSession().getDispatcher());

             for (Map<String, Object> list : billingAccountListMap) {
             for (Map.Entry<String, Object> entryDept : list.entrySet()) {
             if (entryDept.getEntityValue() != null) {
             Debug.logInfo("Key : " + entryDept.getEntityId() + " Value: " + entryDept.getEntityValue().toString(), module);
             }
             }
             }
             */
//                        if (Integer.parseInt(paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText()) <= 0) {
           /* JOptionPane.showMessageDialog(null,
             "Error: Please enter number bigger than 0", "Error Message",
             JOptionPane.ERROR_MESSAGE);*/
        } catch (GeneralException ex) {
            Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newPayment(String paymentFromPartyId, String paymentToPartyId) {
        try {
            clearDialogFields();

            //PaymentComposite paymentComposite = null;
            PaymentComposite paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromPartyId, paymentToPartyId, "CUSTOMER_PAYMENT");
            Debug.logError("paymentFromPartyId: " + paymentFromPartyId + " paymentToPartyId: " + paymentToPartyId, module);

            //set order
            setPaymentComposite(paymentComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
//            loadInvoices(paymentToPartyId);

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PaymentComposite newPaymentToOrder(/*String paymentFromPartyId, String paymentToPartyId,*/String orderId) {
        PaymentComposite paymentComposite = null;
        try {

            clearDialogFields();

            GenericValue orderGenVal = OrderReadHelper.getOrderHeader(ControllerOptions.getSession().getDelegator(), orderId);
            //create order header

            OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);
            String paymentFromPartyId = orderReadHelper.getBillToParty().getString("partyId");
            String paymentToPartyId = orderReadHelper.getBillFromParty().getString("partyId");
            paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromPartyId, paymentToPartyId, "CUSTOMER_PAYMENT", orderGenVal);
            Debug.logError("paymentFromPartyId: " + paymentFromPartyId + " paymentToPartyId: " + paymentToPartyId, module);

            amount = orderReadHelper.getOrderGrandTotal();
            paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
            paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());

            //set order
            setPaymentComposite(paymentComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
            //Set<String> invoiceIds = LoadOrderWorker.getOrderInvoiceIds(orderGenVal);
            //List<InvoiceComposite> invoiceList = LoadInvoiceWorker.loadSalesOrderComposites(invoiceIds, ControllerOptions.getSession());
            //loadInvoices(invoiceList);

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paymentComposite;
    }

    public void loadPayment(String paymentId) {
        try {

            //clear dialog
            clearDialogFields();

            //load data
            Debug.logError("loadPayment: paymentId: " + paymentId, module);
            PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId, ControllerOptions.getSession());
            String paymentFromPartyId = paymentComposite.getPayment().getpartyIdFrom();
            Debug.logError("loadPayment: paymentFromPartyId: " + paymentFromPartyId, module);

            //set order
            setPaymentComposite(paymentComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

            //loadInvoices(paymentFromPartyId);
            BigDecimal paymentApplied = PaymentWorker.getPaymentApplied(paymentComposite.getPayment().getGenericValueObj());
            BigDecimal paymentUnApplied = PaymentWorker.getPaymentNotApplied(paymentComposite.getPayment().getGenericValueObj());
//            BigDecimal paymentUnApplied = PaymentWorker. getPaymentsTotal(paymentComposite.getPayment().getGenericValueObj());
            paymentInvoiceApplicationPanel.setPaymentAmount(paymentComposite.getPayment().getamount());
            paymentInvoiceApplicationPanel.setUnAllocatedAmount(paymentUnApplied);
            paymentInvoiceApplicationPanel.setAllocatedAmount(paymentApplied);
            if (paymentUnApplied.compareTo(BigDecimal.ZERO) > 0) {
                loadInvoices(paymentFromPartyId);
            }

//            if (paymentApplicationListModel.getSize() > 0) {
//                paymentInvoiceApplicationPanel.jTabbedPane1.setSelectedIndex(1);
//            }
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeOrderItem(InvoiceItemComposite orderItem) {
        try {

//            LoadPaymentWorker.removeOrderItem(paymentEntryHeaderPanel.getPaymentComposite(), orderItem, ControllerOptions.getSession());
//            panel.reloadItemDataModel(paymentEntryHeaderPanel.getPaymentComposite().getInvoiceItemCompositeList());  //                  }
            paymentEntryHeaderPanel.repaint();

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //set and handle all dialog product actions
    public void loadInvoices(String partyId) {

        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            invoiceCompositeListModel.clear();
            if (partyId == null || partyId.isEmpty()) {
                return;
            }

            Map<String, Object> findOptionList = FastMap.newInstance();
            if (partyId != null && partyId.isEmpty() == false) {
                findOptionList.put("partyId", partyId);
            }

            List<InvoiceComposite> invoices = LoadInvoiceWorker.loadInvoices(findOptionList, ControllerOptions.getSession(), paymentInvoiceApplicationPanel.cbShowZeroAmount.isSelected());
            loadInvoices(invoices);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadInvoices(List<InvoiceComposite> invoices) {
        invoiceCompositeListModel.addAll(invoices);
        Debug.logWarning("invoiceCompositeListModel size: " + invoiceCompositeListModel.getSize(), module);
        paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().clearAllListeners();
        paymentInvoiceApplicationPanel.setupEditOrderTable();

        paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().addActionListener(tableDataChangeAction);
        paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);
    }

    public void setPaymentToActions(PaymentComposite paymentComposite) {

        paymentStatusActionPopupButton.loadPopMenu(paymentComposite);
//        copyToButton.loadPopMenu(paymentComposite);
        if (paymentComposite != null && paymentComposite.getPayment().getpaymentId() != null
                && paymentComposite.getPayment().getpaymentId().isEmpty() == false) {

            if ("ORDER_CREATED".equals(paymentComposite.getPayment().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
//                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_APPROVED".equals(paymentComposite.getPayment().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(false);
//                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_COMPLETED".equals(paymentComposite.getPayment().getstatusId())
                    || "ORDER_CANCELLED".equals(paymentComposite.getPayment().getstatusId())) {
                boolean isEnabled = false;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
//                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

        }
    }

    String invoiceTypeId = "SALES_INVOICE";

    public void setupProductActions() {

        //order product Id selection click
        //party selection button
        ActionListener productIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {

                        Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                        if (genVal != null) {
                            if (genVal.containsKey("productId")) {

                                if (paymentInvoiceApplicationPanel != null) {

                                    JTextField textField = paymentInvoiceApplicationPanel.getTxtProdIdTableTextField();
                                    if (textField != null) {
                                        textField.setText(genVal.get("productId").toString());
                                        Debug.logWarning("PaymentComposite changed - product id text field: " + genVal.get("productId").toString(), module);
                                        processProductIdTextFieldChange(textField, event.getRow());
                                        event.getTable().setValueAt(genVal.get("productId").toString(), event.getRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                                        event.getTable().changeSelection(event.getRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);

                                    }
                                } else {
//                        table.setValueAt(genVal.get("productId").toString(), row, column);
                                }
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ProductTreeActionTableCellEditor.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        paymentInvoiceApplicationPanel.getProductTreeActionTableCellEditor().addActionListener(productIdChangeAction);

        //order product Id selection click
        //party selection button
        ActionListener interactiveCellRenderAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof InteractiveRenderer
                        && e instanceof RowColumnActionEvent) {

                    InteractiveRenderer render = (InteractiveRenderer) e.getSource();
//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    final RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {
                        if ((paymentInvoiceApplicationPanel.getTableModel().getRowCount() - 1) == event.getRow() /*&& !paymentInvoiceApplicationPanel.getTableModel().hasEmptyRow()*/) {

                            final BigDecimal qty = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(event.getRow(), OrderEntryTableModel.ORDER_QTY_INDEX);
                            final BigDecimal price = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(event.getRow(), OrderEntryTableModel.ORDER_PRICE_INDEX);
                            //                        handleTableRowChange(event.getTable(), event.getRow());
//                            addNewOrderItem();

                        }

//                        paymentInvoiceApplicationPanel.setDialogTotals();
                        paymentEntryHeaderPanel.highlightLastRow(event.getRow());
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }
        };

        paymentInvoiceApplicationPanel.getInteractiveRenderer().addActionListener(interactiveCellRenderAction);

        paymentInvoiceApplicationPanel.getTable().addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                /*                if (!paymentInvoiceApplicationPanel.getTableModel().hasEmptyRow()) {
                 final BigDecimal qty = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(panel.getTable().getSelectedRow(), InvoiceEntryTableModel.Columns.QUANTITY.getColumnIndex());
                 final BigDecimal price = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(panel.getTable().getSelectedRow(), InvoiceEntryTableModel.Columns.UNIT_PRICE.getColumnIndex());
                 if (qty.compareTo(BigDecimal.ZERO) != 0) {
                 //                        addNewOrderItem();
                 }

                 }
                 */
            }

            @Override
            public void focusLost(FocusEvent e) {
                /*try {                
                 if ((panel.getTableModel().getRowCount() - 1) == panel.getTable().getSelectedRow() && !panel.getTableModel().hasEmptyRow()) {
  
                 //handleTableRowChange(panel.getTable(), panel.getTable().getSelectedRow());
                 }
                 } catch (Exception ex) {
                 Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
            }

        }
        );

        tableDataChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof OrderEntryTableModel
                        && e instanceof RowColumnActionEvent) {

                    try {
                        final RowColumnActionEvent event = (RowColumnActionEvent) e;
                        int row = event.getRow();
                        InvoiceItemComposite orderItem = null;//paymentInvoiceApplicationPanel.getTableModel().getRowData(row);

                        Debug.logInfo("Before item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getInvoiceId()
                                + "order Seq Id: " + orderItem.getOrderItem().getinvoiceItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getAmount().toString()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        Debug.logInfo("event.getCol(): " + event.getCol(), module);
                        if (event.getCol() == OrderEntryTableModel.ORDER_PRICE_INDEX) {
//                            orderItem.getShoppingCartItem().setIsModifiedPrice(true);
//                            orderItem.getShoppingCartItem().setBasePrice(orderItem.getOrderItem().getunitPrice());

                            Debug.logInfo("OrderEntryTableModel.ORDER_PRICE_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        } else if (event.getCol() == OrderEntryTableModel.ORDER_QTY_INDEX) {
//                            orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), paymentEntryHeaderPanel.getPaymentComposite().getShopingCart());
                            Debug.logInfo("OrderEntryTableModel.ORDER_QTY_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        }

                        //now sink the values back again..
//                        orderItem.getOrderItem().setAmount(orderItem.getShoppingCartItem().getBasePrice());
                        //                      orderItem.getOrderItem().setquantity(orderItem.getShoppingCartItem().getQuantity());
                        Debug.logInfo("After item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getInvoiceId()
                                + "order Seq Id: " + orderItem.getOrderItem().getinvoiceItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getamount().toString()
                                + "orderItem.getOrderItem().getquantityPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
//                        panel.setDialogTotals();

                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseInvoiceController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };

        paymentInvoiceApplicationPanel.getTableModel().addActionListener(tableDataChangeAction);

        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "COLUMN_ENTERY_KEY");
        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getActionMap().put("COLUMN_ENTERY_KEY", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableCellAction(paymentInvoiceApplicationPanel.getTxtProdIdTableTextField(), paymentInvoiceApplicationPanel.getTable().getSelectedRow(), paymentInvoiceApplicationPanel.getTable().getSelectedColumn());
            }
        });

        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "checkTab");
        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getActionMap().put("checkTab", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableCellAction(paymentInvoiceApplicationPanel.getTxtProdIdTableTextField(), paymentInvoiceApplicationPanel.getTable().getSelectedRow(), paymentInvoiceApplicationPanel.getTable().getSelectedColumn());
            }
        });

        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "checkShiftTab");
        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getActionMap().put("checkShiftTab", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableCellAction(paymentInvoiceApplicationPanel.getTxtProdIdTableTextField(), paymentInvoiceApplicationPanel.getTable().getSelectedRow(), paymentInvoiceApplicationPanel.getTable().getSelectedColumn());
            }
        });

        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "checkF2");
        paymentInvoiceApplicationPanel.getTxtProdIdTableTextField().getActionMap().put("checkF2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                //tableCellAction(textField, table.getSelectedRow(), table.getSelectedColumn());
                try {

                    Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                    if (genVal != null) {
                        if (genVal.containsKey("productId")) {

                            if (paymentInvoiceApplicationPanel != null) {

                                JTextField textField = paymentInvoiceApplicationPanel.getTxtProdIdTableTextField();
                                if (textField != null) {
                                    textField.setText(genVal.get("productId").toString());
                                    Debug.logWarning("PaymentComposite changed - product id text field: " + genVal.get("productId").toString(), module);
                                    processProductIdTextFieldChange(textField, paymentInvoiceApplicationPanel.getTable().getSelectedRow());
                                    paymentInvoiceApplicationPanel.getTable().setValueAt(genVal.get("productId").toString(), paymentInvoiceApplicationPanel.getTable().getSelectedRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                                    paymentInvoiceApplicationPanel.getTable().changeSelection(paymentInvoiceApplicationPanel.getTable().getSelectedRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                                    paymentInvoiceApplicationPanel.getTable().requestFocus();

                                }
                            } else {
//                        table.setValueAt(genVal.get("productId").toString(), row, column);
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ProductTreeActionTableCellEditor.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        paymentInvoiceApplicationPanel.getTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

    }

    public void handleTableRowChange(final JTable table, int row) throws Exception {

        final BigDecimal qty = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_QTY_INDEX);
        final BigDecimal price = (BigDecimal) paymentInvoiceApplicationPanel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_PRICE_INDEX);

        if (qty.compareTo(BigDecimal.ZERO) != 0) {
            String prodId = (String) paymentInvoiceApplicationPanel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_PROD_ID_INDEX);
            updateItem(table, row, prodId, price, qty);
        }
    }

    void setCaption(final ContainerPanelInterface contFrame) {
        contFrame.setCaption(getCaption());
    }

    void tableCellAction(final JTextField textField, int row, int col) {
        Debug.logInfo("tableCellAction product: " + textField.getText(), module);
        if (paymentInvoiceApplicationPanel.getTable().getSelectedColumn() == OrderEntryKeyTableModel.ORDER_PROD_ID_INDEX && textField.getText().isEmpty() == false) {
            processProductIdTextFieldChange(textField, row);
        }
    }

    private static final Border red = new LineBorder(Color.red);

    public void processProductIdTextFieldChange(final JTextField textField, int row) {
        try {
            Debug.logInfo("selected product: " + textField.getText(), module);
            String prodId = textField.getText();
            addProduct(row, prodId);
            paymentInvoiceApplicationPanel.setDialogTotals();
            textField.postActionEvent(); //inform the editor
            paymentInvoiceApplicationPanel.getTableModel().fireTableRowsUpdated(row, row);
        } catch (Exception ex) {
            paymentInvoiceApplicationPanel.setDialogTotals();
            textField.setBorder(red);
            Debug.logError(ex, module);
            //          OrderMaxOptionPane.showMessageDialog(this, ex.getMessage());//.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);            
        }
    }

    public InvoiceItemComposite addProduct(int row, String prodId) throws Exception {
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        PaymentComposite order = paymentEntryHeaderPanel.getPaymentComposite();

        /*        Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
         //        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
         ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(prodId, ControllerOptions.getSession());
         InvoiceItemComposite orderItem = paymentInvoiceApplicationPanel.getTableModel().getRowData(row);
         orderItem.getOrderItem().setproductId(prodId);
         if (orderItem.getShoppingCartItem() == null) {
         int index = order.getShopingCart().addItemToEnd(
         orderItem.getOrderItem().getproductId(),
         orderItem.getOrderItem().getselectedAmount(),
         orderItem.getOrderItem().getquantity(),
         orderItem.getOrderItem().getunitPrice(),
         null, null, null,
         orderItem.getOrderItem().getorderItemTypeId(),
         null,
         ControllerOptions.getSession().getDispatcher(),
         true,
         true);

         ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
         orderItem.setShoppingCartItem(scItem);
  
         }

         Debug.logInfo("prodId: " + prodId, module);

         orderItem.setProductItemPrice(pip);

         SupplierProductList spl = LoadProductPriceWorker.getSupplierProduct(prodId, ControllerOptions.getSession());
         orderItem.setSupplierProductList(spl);
         */
        return null; //orderItem;
//
    }

    public InvoiceItemComposite updateItem(JTable table, int row, String prodId, BigDecimal price, BigDecimal qty) throws Exception {
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        PaymentComposite order = paymentEntryHeaderPanel.getPaymentComposite();

        //Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
//        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
        InvoiceItemComposite orderItem = null;//paymentInvoiceApplicationPanel.getTableModel().getRowData(row);

        orderItem.getOrderItem().setamount(price);
        orderItem.getOrderItem().setquantity(qty);
        Debug.logInfo("updateItem qty : " + qty.toString() + " price : " + price.toString(), module);
        orderItem.getShoppingCartItem().setBasePrice(price);
//        orderItem.getShoppingCartItem().setQuantity(qty, ControllerOptions.getSession().getDispatcher(), order.getShopingCart());
        Debug.logInfo(" saved items reload qty : " + orderItem.getShoppingCartItem().getQuantity().toString()
                + " price : " + orderItem.getShoppingCartItem().getBasePrice().toString(), module);
        //orderItem.saleLineTotal = qty.multiply(price);
//        LoadPurchaseOrderWorker.calculateProductItemValues(orderItem);

        return orderItem;
    }

    public void setVisibleItems() {
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

    public String getCaption() {
        StringBuilder orderToStringBuilder = new StringBuilder();
        orderToStringBuilder.append(caption);

        if (paymentEntryHeaderPanel != null && paymentEntryHeaderPanel.getPaymentComposite() != null
                && paymentEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId() != null
                && paymentEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId().isEmpty() == false) {

            orderToStringBuilder.append(" - Payment Id[ ");
            orderToStringBuilder.append(paymentEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId());
//            if (paymentEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName() != null
//                    && paymentEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName().isEmpty() == false) {
//                orderToStringBuilder.append("(");
//                orderToStringBuilder.append(paymentEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName());
//                orderToStringBuilder.append(") ");
//            }
            orderToStringBuilder.append("]");

            /*            if (panel.orderStatusCombo != null) {
             String str = panel.orderStatusCombo.getSelectedValueDesc();
             orderToStringBuilder.append(" Status");
             orderToStringBuilder.append("[");
             orderToStringBuilder.append(str);
             orderToStringBuilder.append("]");
             }*/
        } else {
            orderToStringBuilder.append("[");
            orderToStringBuilder.append("New");
            orderToStringBuilder.append("] ");
            /*            if (panel!=null && panel.orderStatusCombo != null) {
             String str = panel.orderStatusCombo.getSelectedValueDesc();
             orderToStringBuilder.append(" Status");
             orderToStringBuilder.append("[");
             orderToStringBuilder.append(str);
             orderToStringBuilder.append("]");
             }   
             */
        }

        if (orderId != null) {
            orderToStringBuilder.append("[");
            orderToStringBuilder.append("orderId: " + orderId);
            orderToStringBuilder.append("] ");
        }

        return orderToStringBuilder.toString();
    }

    ShipmentReceiptAction shipmentReceiptAction = null;
    GeneratePurchaseInvoiceAction generatePurchaseInvoiceAction = null;

    @Override
    public PaymentComposite getPayment() {
        paymentEntryHeaderPanel.getDialogFields();
        return paymentEntryHeaderPanel.getPaymentComposite();
    }

    class BigDecimalValidator extends InputVerifier {

//            private JFormattedTextField field;
        private double value;
        private static final String ZERO = "0.0";
        private Border border = new LineBorder(Color.red);

        public BigDecimalValidator(JTextField field) {
//                this.field = field;
            border = field.getBorder();
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {

            if (verify(input)) {
                return true;
            } else {
                JTextField field = (JTextField) input;
                field.selectAll();
                return false;
            }
        }

        @Override
        public boolean verify(JComponent input) {
            BigDecimal val = BigDecimal.ZERO;
            JTextField field = (JTextField) input;
            try {
                JTextField formatText = (JTextField) input;
                val = new BigDecimal(formatText.getText());
                paymentInvoiceApplicationPanel.setPaymentAmount(val);
            } catch (NumberFormatException e) {
                field.setBorder(red);
                return false;
            }
            field.setBorder(border);
            return true;
        }
    }

}
