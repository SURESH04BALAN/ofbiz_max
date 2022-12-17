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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javolution.util.FastMap;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.LoadPaymentWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.ShipmentReceiptAction;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.accounting.payment.BillingAccountWorker;
import org.ofbiz.accounting.payment.PaymentWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderEntryTableModel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.billingaccount.PrimaryIdInterface;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.PaymentGroupComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceAction;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.payment.PaymentApplicationPanel;
import org.ofbiz.ordermax.payment.PaymentGroupIdVerifyValidator;
import org.ofbiz.ordermax.payment.PaymentStatusActionPopupButton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodEntryPanel;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentTypePaymentEntryPanel;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderEntryKeyTableModel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.ProductSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class CustomerPaymentGroupInvoiceController extends CustomerPaymentInvoiceController {

    public static final String module = PurchaseInvoiceController.class.getName();
    public CustomerPaymentGroupEntryHeaderPanel paymentGroupEntryHeaderPanel = null;
    static public final String caption = "Customer Payment";
    String paymentGroupId = null;
    //PaymentGroup paymentGroup = new PaymentGroup();

    PaymentStatusActionPopupButton paymentStatusActionPopupButton;

    static public CustomerPaymentGroupInvoiceController runController(ControllerOptions options) {

        CustomerPaymentGroupInvoiceController controller = new CustomerPaymentGroupInvoiceController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(CustomerPaymentGroupInvoiceController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(CustomerPaymentGroupInvoiceController.module, options.getDesktopPane());
        }
        return controller;
    }

    protected CustomerPaymentGroupInvoiceController(ControllerOptions options) {
        super(options);
        paymentGroupId = options.getPaymentGroupId();
    }

    SimplePaymentGroupButtonPanel buttonGroupPanel = null;
    final ListAdapterListModel<PaymentGroupComposite> actPaymentGroupCompositeListModel = new ListAdapterListModel<PaymentGroupComposite>();

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {
        try {
            isLoading = true;
            ControllerOptions option = options.getCopy();
            paymentGroupEntryHeaderPanel = new CustomerPaymentGroupEntryHeaderPanel(option);
            paymentGroupEntryHeaderPanel.txtPaymentAmount.setInputVerifier(new BigDecimalValidator(paymentGroupEntryHeaderPanel.txtPaymentAmount));
            try {
                paymentGroupEntryHeaderPanel.paymentTypeComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType("CUSTOMER_PAYMENT"));
            } catch (Exception ex) {
                Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            paymentInvoiceApplicationPanel = new CustomerPaymentInvoiceApplicationPanel();
            paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);

            paymentAllocatedPanel = new PaymentApplicationPanel(ControllerOptions.getSession());

            ActionListener paymentIdTextChangeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() instanceof PaymentGroupIdVerifyValidator) {
                        PaymentGroupIdVerifyValidator validator = (PaymentGroupIdVerifyValidator) e.getSource();
                        String paymentGroupId = validator.getField().getText();
                        Debug.logInfo("paymentId: " + paymentGroupId, module);
                        try {
//                        PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId,  ControllerOptions.getSession());
                            loadPaymentGroup(paymentGroupId);
                        } catch (Exception ex) {
                            Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            PaymentGroupIdVerifyValidator paymentIdVerifyValidator = new PaymentGroupIdVerifyValidator(paymentGroupEntryHeaderPanel.txtPaymentGroupId, ControllerOptions.getSession());
            paymentIdVerifyValidator.addActionListener(paymentIdTextChangeAction);
            paymentGroupEntryHeaderPanel.txtPaymentGroupId.setInputVerifier(paymentIdVerifyValidator);

            buttonGroupPanel = new SimplePaymentGroupButtonPanel();
            paymentStatusActionPopupButton = new PaymentStatusActionPopupButton(buttonGroupPanel.btnStatusAction, this, ControllerOptions.getDesktopPane());
            ActionListener paymentStatusChange = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadPaymentGroup(e.getActionCommand());
                    //set order actions
                    setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());

                    setCaption(contFrame);

                }
            };

            paymentStatusActionPopupButton.setCallBackListner(paymentStatusChange);

//        copyToButton = new OrderReturnCopyToPopupButton(panel.btnCopyTo, this, ControllerOptions.getDesktopPane());
//        copyToButton = new CopyToPopupButton(buttonGroupPanel.btnCopyTo);
            paymentMethodEntryPanel = new PaymentMethodEntryPanel();

            paymentGroupEntryHeaderPanel.panelPaymentMethodDetails.setLayout(new BorderLayout());
            paymentGroupEntryHeaderPanel.panelPaymentMethodDetails.add(BorderLayout.CENTER, paymentMethodEntryPanel);
            OrderMaxUtility.addAPanelGrid(paymentGroupEntryHeaderPanel, contFrame.getPanelDetail());
            ComponentBorder.doubleRaisedLoweredBevelBorder(paymentGroupEntryHeaderPanel.panelPaymentMethodDetails, "Payment Entry");

//            paymentGroupEntryHeaderPanel.panelPaymentDetails.setLayout(new BorderLayout());
//            paymentGroupEntryHeaderPanel.panelPaymentDetails.add(BorderLayout.CENTER, paymentInvoiceApplicationPanel);
//            paymentGroupEntryHeaderPanel.panelPaymentDetails.setLayout(new BorderLayout());
            //paymentGroupEntryHeaderPanel.paymentTabbedPane.add("Payment Application", paymentInvoiceApplicationPanel);
//            paymentGroupEntryHeaderPanel.panelApplicationDetail.setLayout(new BorderLayout());
//            paymentGroupEntryHeaderPanel.panelApplicationDetail.add(BorderLayout.CENTER, paymentAllocatedPanel);
            //paymentGroupEntryHeaderPanel.paymentTabbedPane.add("Payments Applied", paymentAllocatedPanel);            
            //          ComponentBorder.doubleRaisedLoweredBevelBorder(paymentGroupEntryHeaderPanel.panelApplicationDetail, "Payment Application");
            OrderMaxUtility.addAPanelToPanel(buttonGroupPanel, paymentGroupEntryHeaderPanel.panelButton);
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), contFrame.getPanelSelecton());
//            OrderMaxUtility.addAPanelGrid(buttonGroupPanel, contFrame.getPanelButton());

//        paymentGroupEntryHeaderPanel.panelCustomerPartyIdPicker.textIdField.setText(customerPartyId);
//        String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
            //      paymentGroupEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
            /*        buttonGroupPanel.getCancelButton().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             cancelButtonPressed();
             }
             });
             */
            /*            buttonGroupPanel.btnNewOrder.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
             String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
             //                paymentGroupEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
             newPayment(billToPartyId, null);
             setCaption(contFrame);
             }
             });
             */
            //party selection button
            ActionListener loadInvoicesAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.getEntityValue() != null) {
                        loadInvoices((String) paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.getEntityValue());
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
                            /* if (UtilValidate.isEmpty(paymentGroupEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText())) {
                             paymentGroupEntryHeaderPanel.panelPartyFromIdPicker.textIdField.setText(ControllerOptions.getSession().getCompanyPartyId());
                             }*/
                            if (orderId != null) {
                                //newPaymentToOrder(orderId);
                                //setInitialFocusField(paymentGroupEntryHeaderPanel.txtPaymentAmount);
                                paymentGroupEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                            } else if (paymentGroupId != null) {
                                paymentGroupEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                                paymentGroupEntryHeaderPanel.txtPaymentGroupId.setEnabled(false);
                            } else if (UtilValidate.isEmpty(paymentGroupEntryHeaderPanel.txtPaymentGroupId.getText()) && validator.getField() != null && UtilValidate.isNotEmpty(validator.getField().getText())) {
                                String paymentFromPartyId = validator.getField().getText();
                                if (paymentGroupEntryHeaderPanel.getPaymentComposite() != null) {
                                    String partyId = paymentGroupEntryHeaderPanel.getPaymentComposite().getPayment().getpartyIdTo();

                                    if (UtilValidate.isEmpty(partyId) || !partyId.equals(paymentFromPartyId)) {

                                        paymentFromPartyId = validator.getField().getText();
                                        String paymentToPartyId = paymentGroupEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText();
                                        Debug.logInfo("newPartyAction paymentToPartyId: " + paymentToPartyId, module);
                                        newPaymentGroup(paymentFromPartyId, paymentToPartyId);
//                            loadInvoices(paymentFromPartyId);

                                    }
                                } else {
                                    paymentFromPartyId = validator.getField().getText();
                                    String paymentToPartyId = paymentGroupEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText();
                                    Debug.logInfo("newPartyAction paymentToPartyId: " + paymentToPartyId, module);
                                    newPaymentGroup(paymentFromPartyId, paymentToPartyId);
                                }
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(paymentGroupEntryHeaderPanel.geCustomerPartyIdFromTextField(), ControllerOptions.getSession());
            piValidator1.addActionListener(newPartyAction);
            paymentGroupEntryHeaderPanel.geCustomerPartyIdFromTextField().setInputVerifier(piValidator1);
            //order selection button

//        CreatePaymentAction createPaymentAction = new CreatePaymentAction(actPaymentCompositeListModel, ControllerOptions.getSession());
//        buttonGroupPanel.btnSaveOrder.setAction(createPaymentAction);
            buttonGroupPanel.btnSaveOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (paymentGroupEntryHeaderPanel.isValidValues()) {
                        paymentGroupEntryHeaderPanel.getDialogFields();

                        //  PaymentComposite paymentComposite = paymentGroupEntryHeaderPanel.getPaymentComposite();
                        //if (paymentGroupEntryHeaderPanel.paymentTabbedPane.getSelectedIndex() == 0) {
//                if (paymentComposite.getPayment().getpaymentId() == null || paymentComposite.getPayment().getpaymentId().isEmpty()) {
                        try {
                            Map<String, PaymentTypePaymentEntryPanel> paymentMap = paymentMethodEntryPanel.getPaymentMethodEntry();
                            BigDecimal amount = new BigDecimal(paymentGroupEntryHeaderPanel.txtPaymentAmount.getText());
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
                                paymentGroupEntryHeaderPanel.txtPaymentAmount.requestFocus();
                                return;
                            }

                            getPaymentCompositeList(paymentGroupEntryHeaderPanel.getPaymentGroupComposite());
                            LoadPaymentWorker.savePaymentGroup(paymentGroupEntryHeaderPanel.getPaymentGroupComposite());
                            /*
                             //else {
                             if (paymentComposite.getBillingAccount() != null) {
                             Map<String, Object> paymentValueMap = FastMap.newInstance();

                             for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                             if (UtilValidate.isNotEmpty(entryPanel.getEntityValue().txtPaymentAmount.getText())) {
                             BigDecimal amountVal = new BigDecimal(entryPanel.getEntityValue().txtPaymentAmount.getText());
                             if (!BigDecimal.ZERO.equals(amountVal)) {
                             paymentValueMap.put("billingAccountId", paymentComposite.getBillingAccount().getbillingAccountId());
                             paymentValueMap.put("currencyUomId", paymentComposite.getPayment().getcurrencyUomId());
                             paymentValueMap.put("partyIdFrom", paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText());
                             paymentValueMap.put("partyIdTo", paymentGroupEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText());
                             paymentValueMap.put("statusId", "PMNT_NOT_PAID");
                             paymentValueMap.put("amount", new BigDecimal(entryPanel.getEntityValue().txtPaymentAmount.getText()));
                             paymentValueMap.put("paymentTypeId", paymentComposite.getPayment().getpaymentTypeId());
                             paymentValueMap.put("paymentMethodTypeId", entryPanel.getEntityValue().getPaymentMethodType().getpaymentMethodTypeId());
                             paymentValueMap.put("paymentRefNum", entryPanel.getEntityValue().txtPaymentReference.getText());

                             String paymentId = LoadPaymentWorker.createPaymentAndApplication(paymentValueMap, ControllerOptions.getSession());
                             savePaymentIds.add(paymentId);
                             }
                             }
                             }

                             paymentGroupId = LoadPaymentWorker.createPaymentGroupAndMembers(paymentGroup, savePaymentIds);

                             if (paymentGroupId != null) {
                             loadExistingPaymentGroup(paymentGroupId);
                             setCaption(contFrame);
                             }
                             } else if (orderId != null) {
                             //create payment method type map 
                             //this will create multiple payment record for each 
                             //payment method type id
                             Map<String, Object> paymentValueMap = FastMap.newInstance();
                             paymentValueMap.put("orderId", orderId);

                             for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                             if (UtilValidate.isNotEmpty(entryPanel.getEntityValue().txtPaymentAmount.getText())) {
                             paymentValueMap.put(entryPanel.getEntityValue().getPaymentMethodType().getpaymentMethodTypeId() + "_amount", entryPanel.getEntityValue().txtPaymentAmount.getText());
                             paymentValueMap.put(entryPanel.getEntityValue().getPaymentMethodType().getpaymentMethodTypeId() + "_reference", entryPanel.getEntityValue().txtPaymentReference.getText());
                             }
                             }

                             Map<String, String> result;
                             //List<String> paymentIdList = new ArrayList<String>();
                             try {
                             result = LoadPaymentWorker.receiveOfflinePayment(paymentValueMap, ControllerOptions.getSession());

                             if (!result.isEmpty()) {
                             if (result.size() > 1) {
                             StringBuilder stringBuilder = new StringBuilder();
                             stringBuilder.append("Following Payments records have been created: ");
                             for (Map.Entry<String, String> paymentIds : result.entrySet()) {
                             stringBuilder.append(paymentIds.getEntityId()).append(": ").append(paymentIds.getEntityValue());
                             savePaymentIds.add(paymentIds.getEntityValue());
                             }
                             stringBuilder.append("Displaying first payment id ");
                             OrderMaxOptionPane.showMessageDialog(null, stringBuilder);
                             }
                             String paymentId = "";
                             for (Map.Entry<String, String> paymentIds : result.entrySet()) {
                             paymentId = paymentIds.getEntityValue();
                             savePaymentIds.add(paymentId);
                             }
                             //loadPayment(paymentId);
                             //set order actions
                             //setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());
                             String paymentGroupId = LoadPaymentWorker.createPaymentGroup(paymentGroup);
                             paymentGroup.setPaymentGroupId(paymentGroupId);
                             LoadPaymentWorker.createPaymentGroupMembers(paymentGroup, savePaymentIds);
                             loadExistingPaymentGroup(paymentGroupId);
                             setCaption(contFrame);
                             //                                            GenericValue val = LoadPaymentWorker.loadPaymentGV(paymentId, ControllerOptions.getSession());
                             //                                            paymentComposite.setPayment(new Payment(val));
                             }
                             } catch (Exception ex) {
                             Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                             }

                             } else {
                             LoadPaymentWorker.savePayment(paymentComposite, ControllerOptions.getSession());
                             String paymentGroupId = LoadPaymentWorker.createPaymentGroup(paymentGroup);
                             paymentGroup.setPaymentGroupId(paymentGroupId);
                             savePaymentIds.add(paymentComposite.getPayment().getpaymentId());
                             LoadPaymentWorker.createPaymentGroupMembers(paymentGroup, savePaymentIds);
                             loadExistingPaymentGroup(paymentGroupId);
                             setCaption(contFrame);
                             }*/
                            //}
                        } catch (Exception ex) {
                            Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        paymentGroupEntryHeaderPanel.setDialogFields();
                        setCaption(contFrame);
                    }
                }
            });

            paymentInvoiceApplicationPanel.btnSaveOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PaymentComposite paymentComposite = paymentGroupEntryHeaderPanel.getPaymentComposite();
                    if (paymentComposite.getBillingAccount() == null) {
                        for (InvoiceComposite val : invoiceCompositeListModel.getList()) {
                            Debug.logInfo(" no billing val.invoiceId(): " + val.getInvoice().getinvoiceId(), module);
                            Debug.logInfo("val.getAllocatedAmount(): " + val.getAllocatedAmount(), module);
                            if (val.getAllocatedAmount() != null && val.getAllocatedAmount().compareTo(BigDecimal.ZERO) != 0) {
                                PaymentApplication paymentApplication
                                        = LoadPaymentWorker.createPaymentApplication(
                                                paymentComposite.getPayment().getpaymentId(),
                                                val.getInvoice().getinvoiceId(),
                                                val.getAllocatedAmount());
                                try {
                                    LoadPaymentWorker.savePaymentApplication(paymentApplication, ControllerOptions.getSession());
                                    paymentComposite.getPaymentApplicationCompositeList().add(paymentApplication);
                                } catch (Exception ex) {
                                    Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }
                    } else {
                        for (InvoiceComposite val : invoiceCompositeListModel.getList()) {
                            Debug.logInfo("val.invoiceId(): " + val.getInvoice().getinvoiceId(), module);
                            Debug.logInfo("val.getAllocatedAmount(): " + val.getAllocatedAmount(), module);
                            if (val.getAllocatedAmount() != null && val.getAllocatedAmount().compareTo(BigDecimal.ZERO) != 0) {
                                try {
                                    Map<String, Object> inMap = FastMap.newInstance();
                                    inMap.put("invoiceId", val.getInvoice().getinvoiceId());
                                    LoadPaymentWorker.capturePaymentsByInvoice(inMap, ControllerOptions.getSession());
                                } catch (Exception ex) {
                                    Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            });

            paymentInvoiceApplicationPanel.cbShowZeroAmount.addChangeListener(new javax.swing.event.ChangeListener() {
                @Override
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    //loadInvoices(paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText());
                }
            });

            paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        List<Map<String, Object>> billingAccountList = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(),
                                paymentGroupEntryHeaderPanel.uomComboBox.getSelectedItem().getuomId(), paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(), ControllerOptions.getSession().getDelegator(), ControllerOptions.getSession().getDispatcher());

//                        if (Integer.parseInt(paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter number bigger than 0", "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                        //                      }
                    } catch (GeneralException ex) {
                        Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
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
                    BigDecimal paymentAmt = paymentGroupEntryHeaderPanel.getPaymentAmount();

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
                    PaymentComposite paymentComposite = paymentGroupEntryHeaderPanel.getPaymentComposite();

                    paymentInvoiceApplicationPanel.setPaymentAmount(paymentGroupEntryHeaderPanel.getPaymentAmount());
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

            if (paymentGroupId != null) {
//            loadExistingPayment(paymentId);
                loadPaymentGroup(paymentGroupId);
                //set order actions
                setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());
                setCaption(contFrame);
                setInitialFocusField(paymentGroupEntryHeaderPanel.txtPaymentGroupId);
            } else if (orderId != null) {
                paymentGroupEntryHeaderPanel.getCustomerPartyIdFromTextField().setEnabled(false);
                newPaymentGroupToOrder(orderId);
                setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());
                if (amount != null) {
                    paymentGroupEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
                    paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                }
                setInitialFocusField(paymentGroupEntryHeaderPanel.txtPaymentAmount);
                setCaption(contFrame);
            } else {
                //create new payments
                String billToPartyId = XuiSession.getCompanyPartyId();;
                newPaymentGroup(customerPartyId, billToPartyId);
//                paymentGroupEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
//                paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                buttonGroupPanel.btnSaveOrder.setEnabled(true);
//                buttonGroupPanel.btnApproveOrder.setEnabled(false);
                setInitialFocusField(paymentGroupEntryHeaderPanel.getCustomerPartyIdFromTextField());
                if (amount != null) {
                    paymentGroupEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
                    paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
                }
//            buttonGroupPanel.btnCancelOrder.setEnabled(false);
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
        paymentGroupEntryHeaderPanel.setDialogFields();
        paymentInvoiceApplicationPanel.setDialogFields();
        paymentMethodEntryPanel.setDialogFields();
        paymentAllocatedPanel.setDialogFields();
        if (UtilValidate.isNotEmpty(paymentGroupEntryHeaderPanel.getPaymentComposite())
                && (UtilValidate.isNotEmpty(paymentGroupEntryHeaderPanel.getPaymentComposite().getOutstandingAmount())
                && BigDecimal.ZERO.compareTo(paymentGroupEntryHeaderPanel.getPaymentComposite().getOutstandingAmount()) == 0)
                && (UtilValidate.isNotEmpty(paymentGroupEntryHeaderPanel.getPaymentComposite().getUncapturedAmount())
                && BigDecimal.ZERO.compareTo(paymentGroupEntryHeaderPanel.getPaymentComposite().getUncapturedAmount()) == 0)) {
            paymentGroupEntryHeaderPanel.paymentTabbedPane.addTab("Payments Applied", paymentAllocatedPanel);
        } else {
            paymentGroupEntryHeaderPanel.paymentTabbedPane.addTab("Payment Application", paymentInvoiceApplicationPanel);
        }
    }

    void clearDialogFields() {
        paymentGroupEntryHeaderPanel.clearDialogFields();
        paymentInvoiceApplicationPanel.clearDialogFields();
        paymentMethodEntryPanel.clearDialogFields();
        paymentAllocatedPanel.clearDialogFields();
        invoiceCompositeListModel.clear();
        actPaymentGroupCompositeListModel.clear();
        paymentApplicationListModel.clear();
//        if (panelApplicationIndex != -1) {
        paymentGroupEntryHeaderPanel.paymentTabbedPane.remove(paymentInvoiceApplicationPanel);
//        }
//        if (panelAllocatedIndex != -1) {
        paymentGroupEntryHeaderPanel.paymentTabbedPane.remove(paymentAllocatedPanel);
//        }

    }

    void setPaymentGroupComposite(PaymentGroupComposite paymentGroupComposite) {

        paymentMethodEntryPanel.setPaymentGroupComposite(paymentGroupComposite);
        actPaymentGroupCompositeListModel.add(paymentGroupComposite);
        paymentGroupEntryHeaderPanel.setPaymentGroupComposite(paymentGroupComposite);
        paymentAllocatedPanel.setPaymentApplicationList(paymentApplicationListModel);
        for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {
            paymentApplicationListModel.addAll(paymentCompo.paymentComposite.getPaymentApplicationCompositeList().getList());
        }
    }

    void loadExistingPaymentGroup(String paymentGroupId) {
        try {
            clearDialogFields();

            PaymentGroupComposite paymentComposite = LoadPaymentWorker.loadPaymentGroup(paymentGroupId, ControllerOptions.getSession());

            //set order
            setPaymentGroupComposite(paymentComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());

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
            List<BillingAccount> billingAccountList = null;
            PaymentComposite paymentComposite = paymentGroupEntryHeaderPanel.getPaymentComposite();
            if (paymentComposite != null && paymentComposite.getPayment() != null) {
                billingAccountList = LoadBillingAccountWorker.getPartyBillingAccountList(
                        paymentComposite.getPayment().getcurrencyUomId(),
                        paymentComposite.getPayment().getpartyIdFrom(),
                        ControllerOptions.getSession().getDelegator());
            } else {
                billingAccountList = new ArrayList<BillingAccount>();
            }
            BillingAccount billingAccount = new BillingAccount();
            billingAccount.setdescription("<None>");
            billingAccount.setbillingAccountId(null);
            billingAccountList.add(billingAccount);
            paymentGroupEntryHeaderPanel.billingAccountComboBox.setDataList(billingAccountList);

            /*List<Map<String, Object>> billingAccountListMap = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(),
             paymentGroupEntryHeaderPanel.uomComboBox.getSelectedItem().getuomId(),
             paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(),
             delegator, ControllerOptions.getSession().getDispatcher());

             for (Map<String, Object> list : billingAccountListMap) {
             for (Map.Entry<String, Object> entryDept : list.entrySet()) {
             if (entryDept.getEntityValue() != null) {
             Debug.logInfo("Key : " + entryDept.getEntityId() + " Value: " + entryDept.getEntityValue().toString(), module);
             }
             }
             }
             */
//                        if (Integer.parseInt(paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText()) <= 0) {
           /* JOptionPane.showMessageDialog(null,
             "Error: Please enter number bigger than 0", "Error Message",
             JOptionPane.ERROR_MESSAGE);*/
        } catch (GeneralException ex) {
            Logger.getLogger(CustomerPaymentGroupInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newPaymentGroup(String paymentFromPartyId, String paymentToPartyId) {
        try {
            clearDialogFields();

            //PaymentComposite paymentComposite = null;
            PaymentGroupComposite paymentGroupComposite = LoadPaymentWorker.newPaymentGroupComposite("BATCH_PAYMENT", "Erp multiple payments");

// Debug.logError("paymentFromPartyId: " + paymentFromPartyId + " paymentToPartyId: " + paymentToPartyId, module);
            PaymentComposite paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromPartyId, paymentToPartyId, "CUSTOMER_PAYMENT");

            paymentGroupComposite.addPayment(paymentComposite);
            //set order
            setPaymentGroupComposite(paymentGroupComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());
//            loadInvoices(paymentToPartyId);

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PaymentGroupComposite newPaymentGroupToOrder(/*String paymentFromPartyId, String paymentToPartyId,*/String orderId) {
        PaymentGroupComposite paymentGroupComposite = null;
        try {

            paymentGroupComposite = LoadPaymentWorker.newPaymentGroupComposite("BATCH_PAYMENT", "Erp multiple payments");
            clearDialogFields();

            GenericValue orderGenVal = OrderReadHelper.getOrderHeader(ControllerOptions.getSession().getDelegator(), orderId);
            //create order header

            OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);
            String paymentFromPartyId = orderReadHelper.getBillToParty().getString("partyId");
            String paymentToPartyId = orderReadHelper.getBillFromParty().getString("partyId");
            PaymentComposite paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromPartyId, paymentToPartyId, "CUSTOMER_PAYMENT", orderGenVal);
            Debug.logError("paymentFromPartyId: " + paymentFromPartyId + " paymentToPartyId: " + paymentToPartyId, module);

            amount = orderReadHelper.getOrderGrandTotal();
            paymentGroupEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
            paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());

            paymentGroupComposite.addPayment(paymentComposite);

            //set order
            setPaymentGroupComposite(paymentGroupComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentGroupComposite;
    }

    public void loadPaymentGroup(String paymentGroupId) {
        try {

            //clear dialog
            clearDialogFields();

            //load data
            Debug.logError("loadPayment: paymentGroupId: " + paymentGroupId, module);
            PaymentGroupComposite paymentGroupComposite = LoadPaymentWorker.loadPaymentGroup(paymentGroupId, ControllerOptions.getSession());
            String paymentFromPartyId = null;
            if (paymentGroupComposite.getFirstPaymentComposite() != null) {
                paymentFromPartyId = paymentGroupComposite.getFirstPaymentComposite().getPayment().getpartyIdFrom();
            }
            //Debug.logError("loadPayment: paymentFromPartyId: " + paymentFromPartyId, module);

            //set order
            setPaymentGroupComposite(paymentGroupComposite);

            loadBillingAccounts();

            //update the dialog
            setDialogFields();

            setPaymentToActions(paymentGroupEntryHeaderPanel.getPaymentComposite());

            //loadInvoices(paymentFromPartyId);
            BigDecimal paymentApplied = BigDecimal.ZERO;
            BigDecimal paymentUnApplied = BigDecimal.ZERO;
            BigDecimal paymentAmount = BigDecimal.ZERO;
//            BigDecimal paymentUnApplied = PaymentWorker. getPaymentsTotal(paymentComposite.getPayment().getGenericValueObj());
            //paymentInvoiceApplicationPanel.setPaymentAmount(paymentComposite.getPayment().getamount());
            for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {
                paymentApplied = paymentApplied.add(PaymentWorker.getPaymentApplied(paymentCompo.paymentComposite.getPayment().getGenericValueObj()));
                paymentUnApplied = paymentApplied.add(PaymentWorker.getPaymentNotApplied(paymentCompo.paymentComposite.getPayment().getGenericValueObj()));
//            BigDecimal paymentUnApplied = PaymentWorker. getPaymentsTotal(paymentComposite.getPayment().getGenericValueObj());
                paymentAmount = paymentAmount.add(paymentCompo.paymentComposite.getPayment().getamount());
            }

            paymentInvoiceApplicationPanel.setUnAllocatedAmount(paymentUnApplied);
            paymentInvoiceApplicationPanel.setAllocatedAmount(paymentApplied);
            paymentInvoiceApplicationPanel.setPaymentAmount(paymentAmount);
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

//            LoadPaymentWorker.removeOrderItem(paymentGroupEntryHeaderPanel.getPaymentComposite(), orderItem, ControllerOptions.getSession());
//            panel.reloadItemDataModel(paymentGroupEntryHeaderPanel.getPaymentComposite().getInvoiceItemCompositeList());  //                  }
            paymentGroupEntryHeaderPanel.repaint();

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
                buttonGroupPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonGroupPanel.btnApproveOrder.setEnabled(isEnabled);
//                buttonGroupPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_APPROVED".equals(paymentComposite.getPayment().getstatusId())) {
                boolean isEnabled = true;
                buttonGroupPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(false);
//                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_COMPLETED".equals(paymentComposite.getPayment().getstatusId())
                    || "ORDER_CANCELLED".equals(paymentComposite.getPayment().getstatusId())) {
                boolean isEnabled = false;
                buttonGroupPanel.btnSaveOrder.setEnabled(isEnabled);
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
                        paymentGroupEntryHeaderPanel.highlightLastRow(event.getRow());
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
//                            orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), paymentGroupEntryHeaderPanel.getPaymentComposite().getShopingCart());
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

        PaymentComposite order = paymentGroupEntryHeaderPanel.getPaymentComposite();

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

        PaymentComposite order = paymentGroupEntryHeaderPanel.getPaymentComposite();

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

        if (paymentGroupEntryHeaderPanel != null && paymentGroupEntryHeaderPanel.getPaymentComposite() != null
                && paymentGroupEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId() != null
                && paymentGroupEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId().isEmpty() == false) {

            orderToStringBuilder.append(" - Payment Id[ ");
            orderToStringBuilder.append(paymentGroupEntryHeaderPanel.getPaymentComposite().getPayment().getpaymentId());
//            if (paymentGroupEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName() != null
//                    && paymentGroupEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName().isEmpty() == false) {
//                orderToStringBuilder.append("(");
//                orderToStringBuilder.append(paymentGroupEntryHeaderPanel.getPaymentComposite().getInvoice().getOrderName());
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
        paymentGroupEntryHeaderPanel.getDialogFields();
        return paymentGroupEntryHeaderPanel.getPaymentComposite();
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

    protected void getPaymentCompositeList(PaymentGroupComposite paymentGroup) {

        PaymentComposite paymentComposite = paymentGroupEntryHeaderPanel.getPaymentComposite();

        if (paymentComposite != null) {

            Map<String, PaymentTypePaymentEntryPanel> paymentMap = paymentMethodEntryPanel.getPaymentMethodEntry();

            for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : paymentMap.entrySet()) {
                if (UtilValidate.isNotEmpty(entryPanel.getValue().txtPaymentAmount.getText())) {
                    BigDecimal amountVal = new BigDecimal(entryPanel.getValue().txtPaymentAmount.getText());
                    if (!BigDecimal.ZERO.equals(amountVal)) {
                        String paymentMethodTypeId = entryPanel.getValue().getPaymentMethodType().getpaymentMethodTypeId();

                        PaymentComposite comp = paymentGroup.getPaymentByPaymentMethodType(paymentMethodTypeId);
                        if (comp == null) {
                            comp = LoadPaymentWorker.newPayment(ControllerOptions.getSession(),
                                    paymentGroupEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(),
                                    paymentGroupEntryHeaderPanel.panelPartyToIdPicker.textIdField.getText(),
                                    "CUSTOMER_PAYMENT");
                        }
                        if (paymentComposite.getBillingAccount() != null) {
                            comp.setBillingAccount(new BillingAccount());
                            comp.getBillingAccount().setbillingAccountId(paymentComposite.getBillingAccount().getbillingAccountId());
                        }
                        if (orderId != null) {
                            comp.setOrderPaymentPreferences(new OrderPaymentPreference());
                            comp.getOrderPaymentPreference().setorderId(orderId);
                        }
                        comp.getPayment().setactualCurrencyUomId(paymentComposite.getPayment().getcurrencyUomId());
                        comp.getPayment().setstatusId(paymentComposite.getPayment().getstatusId());
                        comp.getPayment().setamount(new BigDecimal(entryPanel.getValue().txtPaymentAmount.getText()));
                        comp.getPayment().setpaymentTypeId(paymentComposite.getPayment().getpaymentTypeId());
                        comp.getPayment().setpaymentMethodTypeId(entryPanel.getValue().getPaymentMethodType().getpaymentMethodTypeId());
                        comp.getPayment().setpaymentRefNum(entryPanel.getValue().txtPaymentReference.getText());
                        paymentGroup.addPayment(comp);
                    }
                }
            }
        }
    }

    static public class CustomerPaymentGroupIdClickAction implements ActionListener {

        private PrimaryIdInterface prodInterface;
        ControllerOptions options = new ControllerOptions();

        public CustomerPaymentGroupIdClickAction(PrimaryIdInterface prodInterface, ControllerOptions options) {
            this.prodInterface = prodInterface;
            this.options = options;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String productId = prodInterface.getPrimaryId();
            if (productId != null) {

                options.put("paymentGroupId", productId);
                CustomerPaymentGroupInvoiceController.runController(options);

            }
        }
    }

    static public class MainMenuAction extends ScreenAction {

        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";
        ControllerOptions controllerOptions = null;

        public MainMenuAction(String name, ControllerOptions controllerOptions) {
            super(name);
            this.controllerOptions = controllerOptions;
            if (name == null) {
                this.setName(caption);
            }

            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerPaymentGroupInvoiceController.runController(controllerOptions);

        }

        @Override
        public Action getAction() {
            return this;
        }
    }

    static public void addToMenu(ControllerOptions options, javax.swing.JMenu parentMenu) {

        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "PaymentGroup");
        controllerOptions.addOrderType("SALES_ORDER");
        controllerOptions.addRoleTypeParent("CUSTOMER");
        controllerOptions.addParentPaymentTypeId("RECEIPT");        
        MainMenuAction mainMenuItem = new MainMenuAction("Customer Payment Group", controllerOptions);
        JMenuItem mnuItem = mainMenuItem.createActionMenuItem();
        parentMenu.add(mnuItem);
    }
}
