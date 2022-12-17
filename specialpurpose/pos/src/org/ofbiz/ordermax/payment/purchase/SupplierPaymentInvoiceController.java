/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.purchase;

import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javolution.util.FastMap;
import mvc.controller.ApproveOrderAction;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateNewShipGroupAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceAction;
import mvc.controller.LoadPaymentWorker;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.PayPurchaseOrderAction;
import mvc.controller.PrintPurchaseInvoiceAction;
import mvc.controller.PrintPickSlipAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.ShipmentReceiptAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import mvc.controller.ViewOrderHistoryAction;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderEntryTableModel;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.payment.PaymentActionInterface;
import org.ofbiz.ordermax.payment.PaymentApplicationPanel;
import org.ofbiz.ordermax.payment.PaymentIdVerifyValidator;
import org.ofbiz.ordermax.payment.PaymentStatusActionPopupButton;
import org.ofbiz.ordermax.payment.SimplePaymentButtonPanel;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderEntryKeyTableModel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.ProductSelectionPanel;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class SupplierPaymentInvoiceController extends BaseMainScreen  implements PaymentActionInterface {

    public static final String module = PurchaseInvoiceController.class.getName();
    public SupplierPaymentEntryHeaderPanel paymentEntryHeaderPanel = null;
    public final String caption = "Pay Invoice";
    public PosScreen pos = null;
    String paymentId = null;
    String invoiceId = null;
    String supplierPartyId = null;
    String orderId = null;
    BigDecimal amount = BigDecimal.ZERO;
    PaymentStatusActionPopupButton paymentStatusActionPopupButton;

    static public SupplierPaymentInvoiceController runController(ControllerOptions options) {

        SupplierPaymentInvoiceController controller = new SupplierPaymentInvoiceController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(SupplierPaymentInvoiceController.module, options.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(SupplierPaymentInvoiceController.module, options.getDesktopPane());
        }
        return controller;
    }

    ControllerOptions options;

    protected SupplierPaymentInvoiceController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options.getCopy();
        paymentId = options.getPaymentId();
        this.supplierPartyId = options.getPartyId();
        this.orderId = options.getOrderId();
        this.invoiceId = options.getInvoiceId();
        this.amount = (BigDecimal) options.get("Amount");
    }

    SimplePaymentButtonPanel buttonPanel = null;
    private SupplierPaymentInvoiceApplicationPanel paymentInvoiceApplicationPanel = null;
    public PaymentApplicationPanel paymentApplicationPanel = null;

    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<PaymentComposite> actPaymentCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    final private ListAdapterListModel<PaymentApplication> paymentApplicationListModel = new ListAdapterListModel<PaymentApplication>();

    //CopyToPopupButton copyToButton = null;
    ApproveOrderAction statusApprovedAction = null;
    PrintPurchaseInvoiceAction printPurchaseInvoiceAction = null;
    PrintPickSlipAction printPurchasePickSlipAction = null;
    ViewEditDeliverScheduleInfoAction viewEditDeliverScheduleInfoAction = null;
    ViewOrderHistoryAction viewOrderHistoryAction = null;
    CreateNewShipGroupAction createNewShipGroupAction = null;
    CreateAsNewOrderAction createAsNewOrderAction = null;
    PayPurchaseOrderAction payPurchaseOrderAction = null;
    CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    QuickRefundEntireSalesOrderAction quickRefundEntireOrderAction = null;
    Order toPayOrder;
    InvoiceComposite toPayInvoice;

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {

        paymentEntryHeaderPanel = new SupplierPaymentEntryHeaderPanel(ControllerOptions.getSession(), "DISBURSEMENT");
        paymentEntryHeaderPanel.txtPaymentAmount.setInputVerifier(new BigDecimalValidator(paymentEntryHeaderPanel.txtPaymentAmount));

        paymentInvoiceApplicationPanel = new SupplierPaymentInvoiceApplicationPanel();
        paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);

        paymentApplicationPanel = new PaymentApplicationPanel(ControllerOptions.getSession());

        ActionListener paymentIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof PaymentIdVerifyValidator) {
                    PaymentIdVerifyValidator validator = (PaymentIdVerifyValidator) e.getSource();
                    String paymentId = validator.getField().getText();
                    Debug.logInfo("paymentId: " + paymentId, module);
                    try {
                        loadPayment(paymentId);
                    } catch (Exception ex) {
                        Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        };
        PaymentIdVerifyValidator paymentIdVerifyValidator = new PaymentIdVerifyValidator(paymentEntryHeaderPanel.txtPaymentId, ControllerOptions.getSession());
        paymentIdVerifyValidator.addActionListener(paymentIdTextChangeAction);
        paymentEntryHeaderPanel.txtPaymentId.setInputVerifier(paymentIdVerifyValidator);

        buttonPanel = new SimplePaymentButtonPanel();
        paymentStatusActionPopupButton = new PaymentStatusActionPopupButton(buttonPanel.btnStatusAction, this, ControllerOptions.getDesktopPane());
//        copyToButton = new CopyToPopupButton(buttonPanel.btnPayment);
        OrderMaxUtility.addAPanelGrid(paymentEntryHeaderPanel, contFrame.getPanelDetail());
        paymentEntryHeaderPanel.panelPaymentDetails.setLayout(new BorderLayout());
        paymentEntryHeaderPanel.panelPaymentDetails.add(BorderLayout.CENTER, paymentInvoiceApplicationPanel);
        ComponentBorder.doubleRaisedLoweredBevelBorder(paymentEntryHeaderPanel.panelPaymentDetails, "Payment Application");
        OrderMaxUtility.addAPanelToPanel(buttonPanel, contFrame.getPanelButton());

        paymentInvoiceApplicationPanel.panelApplicationDetail.setLayout(new BorderLayout());
        paymentInvoiceApplicationPanel.panelApplicationDetail.add(BorderLayout.CENTER, paymentApplicationPanel);
        ComponentBorder.doubleRaisedLoweredBevelBorder(paymentInvoiceApplicationPanel.panelApplicationDetail, "Payment Application");

//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), contFrame.getPanelSelecton());
        OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

        paymentEntryHeaderPanel.panelSupplierPartyIdPicker.textIdField.setText(supplierPartyId);
        if (amount != null) {
            paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
            paymentEntryHeaderPanel.txtPaymentAmount.setText(amount.toString());
            paymentInvoiceApplicationPanel.txtPaymentAmount.setText(amount.toString());
        }
        String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
        paymentEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);

        //create new payments
        newPayment(billToPartyId, supplierPartyId);

        /*        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         cancelButtonPressed();
         }
         });
         */
/*        buttonPanel.btnNewOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
                paymentEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
                newPayment(billToPartyId, null);
                setCaption(contFrame);
            }
        });
        */
        //party selection button
        ActionListener newPartyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                Debug.logInfo("newPartyAction billFromPartyId: " + validator.getField().getText(), module);

                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                        //PartyIdVerifyValidator 
                        //        validator = (PartyIdVerifyValidator) e.getSource();
                        Debug.logInfo("newPartyAction billFromPartyId: " + validator.getField().getText(), module);
                        if (UtilValidate.isEmpty(paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText())) {
                            paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.setText(ControllerOptions.getSession().getCompanyPartyId());
                        }

                        if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
                            String billFromPartyId = validator.getField().getText();
                            if (paymentEntryHeaderPanel.getPaymentComposite() != null) {
                                String partyId = paymentEntryHeaderPanel.getPaymentComposite().getPayment().getpartyIdTo();//.getPartyPaymentTo();//.getParty().getpartyId()
                                if (UtilValidate.isNotEmpty(partyId)
                                        && partyId.equals(billFromPartyId)) {
                                    return;
                                } else {

                                    String paymentToPartyId = validator.getField().getText();
                                    String paymentFromPartyId = paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText();
                                    Debug.logInfo("newPartyAction paymentToPartyId: " + paymentToPartyId, module);
                                    newPayment(paymentFromPartyId, paymentToPartyId);
//                            loadInvoices(paymentFromPartyId);

                                }
                            } else {
                                String paymentToPartyId = validator.getField().getText();
                                String paymentFromPartyId = paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText();
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

        PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(paymentEntryHeaderPanel.getSupplierPartyTextField(), ControllerOptions.getSession());
        piValidator1.addActionListener(newPartyAction);
        paymentEntryHeaderPanel.getSupplierPartyTextField().setInputVerifier(piValidator1);
        //order selection button
        /*
         ActionListener newPartyAction = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

         if (e.getSource() instanceof PartyIdVerifyValidator) {
         try {

         PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
         Debug.logInfo("billFromPartyId: " + validator.getField().getText(), module);
         if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
         String billFromPartyId = validator.getField().getText();
         if (panel.getOrder() != null && UtilValidate.isNotEmpty(panel.getOrder().getPartyId()) && panel.getOrder().getPartyId().equals(billFromPartyId)) {
         return;
         } else {
         String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();;
         newOrder(billFromPartyId, billToPartyId);
         //set order actions
         setOrderToActions(panel.getOrder());

         loadFinancialData(billFromPartyId);
         loadOrderList(billFromPartyId);
         setCaption(contFrame);
         }
         } else {
         //clear dialog
         panel.clearDialogFields();
         orderListModel.clear();
         //                            orderListModel.add(order);
         invoiceCompositeListModel.clear();
         buttonPanel.btnSaveOrder.setEnabled(true);
         buttonPanel.btnApproveOrder.setEnabled(false);
         buttonPanel.btnCancelOrder.setEnabled(false);
         }
         } catch (Exception ex) {
         Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }
         };

         PartyIdVerifyValidator piValidator = new PartyIdVerifyValidator(panel.txtLineItemPartyId, ControllerOptions.getSession());
         piValidator.addActionListener(newPartyAction);*/
        /*
         buttonPanel.btnSaveOrder.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         if (paymentEntryHeaderPanel.partyIdTextField.getText() != null
         && paymentEntryHeaderPanel.partyIdTextField.getText().isEmpty() == false) {
         paymentEntryHeaderPanel.getDialogFields();
         PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();
         //                SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
         Debug.logInfo("before loadPersonsWorker.execute", NAME);
         //                saveOrderWorker.execute();
         //                    SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
         Debug.logInfo("after loadPersonsWorker.execute", NAME);

         //update the dialog
         paymentEntryHeaderPanel.setDialogFields();
         //set order actions    
         setOrderToActions(paymentEntryHeaderPanel.getPaymentComposite());
         setCaption(contFrame);
         } else {
         OrderMaxOptionPane.showMessageDialog(null, "Cannot Save : Supplier id is empty.");
         }
         }
         });
         */

//        CreatePaymentAction createPaymentAction = new CreatePaymentAction(actPaymentCompositeListModel, ControllerOptions.getSession());
//        buttonPanel.btnSaveOrder.setAction(createPaymentAction);
        buttonPanel.btnSaveOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentEntryHeaderPanel.getDialogFields();

                PaymentComposite paymentComposite = paymentEntryHeaderPanel.getPaymentComposite();
                paymentComposite.getPayment().setpartyIdFrom(paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText());
                paymentComposite.getPayment().setpartyIdTo(paymentEntryHeaderPanel.getSupplierPartyTextField().getText());
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
//                if (paymentComposite.getPayment().getpaymentId() == null || paymentComposite.getPayment().getpaymentId().isEmpty()) {
                try {
                    LoadPaymentWorker.savePayment(paymentComposite, ControllerOptions.getSession());
                } catch (Exception ex) {
                    Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
//                }
                paymentEntryHeaderPanel.setDialogFields();
                setCaption(contFrame);
            }
        });

        paymentInvoiceApplicationPanel.cbShowZeroAmount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                loadPartyInvoices(paymentEntryHeaderPanel.panelSupplierPartyIdPicker.textIdField.getText());
            }
        });
        paymentInvoiceApplicationPanel.btnAutoAllocate.addActionListener(new ActionListener() {
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
                setCaption(contFrame);
            }
        }
        );

        /*        statusApprovedAction = new ApprovePurchaseOrderAction(paymentCompositeListModel, ControllerOptions.getSession());
         buttonPanel.btnApproveOrder.setAction(statusApprovedAction);
         statusApprovedAction.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
         loadInvoices(e.getActionCommand());
         //set order actions
         setOrderToActions(paymentEntryHeaderPanel.getPaymentComposite());

         loadFinancialData(paymentEntryHeaderPanel.partyIdTextField.getText());
         setCaption(contFrame);
         }

         });
         */
        if (paymentId != null) {
//            loadExistingPayment(paymentId);
            loadPayment(paymentId);
            //set order actions
            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

            setCaption(contFrame);
        } else {
            buttonPanel.btnSaveOrder.setEnabled(true);
//            buttonPanel.btnApproveOrder.setEnabled(false);
//            buttonPanel.btnCancelOrder.setEnabled(false);

        }
//        panel.setProductListArray((ProductListArray) treePanel.getTreeDataList());

        setInitialFocusField(paymentEntryHeaderPanel.getPayerPartyTextField());
    }

    ActionListener tableDataChangeAction = null;
    /*
     public void newPayment() {
     paymentEntryHeaderPanel.clearDialogFields();
     paymentInvoiceApplicationPanel.clearDialogFields();
     String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
     paymentEntryHeaderPanel.getPayerPartyTextField().setText(billToPartyId);
     try {
     paymentEntryHeaderPanel.paymentMethodComboBox.setSelectedItem(PaymentMethodSingleton.getPaymentMethod("ABN_CHECKING"));
     //    public JGenericComboBoxSelectionModel<PaymentMethod>
     } catch (Exception ex) {
     Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
     }

     try {
     paymentEntryHeaderPanel.paymentTypeComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType("VENDOR_PAYMENT"));
     //public JGenericComboBoxSelectionModel<PaymentType>
     //      paymentEntryHeaderPanel.paymentTypeComboBox = null;    
     } catch (Exception ex) {
     Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
     }
     paymentEntryHeaderPanel.paymentDatePickerEditPanel.setCurrentDate();
     try {
     paymentEntryHeaderPanel.statusItemComboBox.setSelectedItem(StatusSingleton.getStatusItem("PMNT_NOT_PAID"));
     } catch (Exception ex) {
     Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
     }
     try {
     paymentEntryHeaderPanel.uomComboBox.setSelectedItem(UOMSingleton.getUom((String) XuiContainer.getSession().getAttribute("currency")));
     //                paymentEntryHeaderPanel.roleTypeComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType("VENDOR_PAYMENT"));
     //              paymentEntryHeaderPanel.finAccountComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType("VENDOR_PAYMENT"));
     //                newPayment(paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText(), 
     //                        paymentEntryHeaderPanel.panelSupplierPartyIdPicker.textIdField.getText());
     //set order actions
     //                setOrderToActions(paymentEntryHeaderPanel.getPaymentComposite());
     //                invoiceCompositeListModel.clear();
     } catch (Exception ex) {
     Logger.getLogger(SupplierPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
     }

     buttonPanel.btnSaveOrder.setEnabled(true);
     buttonPanel.btnApproveOrder.setEnabled(false);
     buttonPanel.btnCancelOrder.setEnabled(false);
     }
     */

    void loadExistingPayment(String paymentId) {
        try {
            PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId, ControllerOptions.getSession());

            //clear dialog
            paymentEntryHeaderPanel.clearDialogFields();
            paymentInvoiceApplicationPanel.clearDialogFields();
            //set order
            paymentEntryHeaderPanel.setPaymentComposite(paymentComposite);
//      LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, shopingCart);
            //update the dialog
            paymentEntryHeaderPanel.setDialogFields();

            String paymentToPartyId = paymentComposite.getPayment().getpartyIdTo();
//            order.getOrderItemsList().add(orderItem);
            Debug.logWarning("paymentFromPartyId: " + paymentToPartyId, module);
//            loadInvoices(paymentToPartyId);
            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

            loadPartyInvoices(paymentToPartyId);

            //setup table
//            paymentInvoiceApplicationPanel.setupEditOrderTable();
//            paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().addActionListener(tableDataChangeAction);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newPayment(String paymentFromPartyId, String paymentToPartyId) {
        try {

            PaymentComposite paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromPartyId, paymentToPartyId, "CUSTOMER_PAYMENT");
            Debug.logError("paymentFromPartyId: " + paymentFromPartyId + " paymentToPartyId: " + paymentToPartyId, module);
            /*
             Account paymentFromAccount = PartyListSingleton.getAccount(paymentFromPartyId);
             if (UtilValidate.isNotEmpty(paymentToPartyId)) {
             Account paymentToAccount = PartyListSingleton.getAccount(paymentToPartyId);
             paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromAccount, paymentToAccount, "VENDOR_PAYMENT");
             } else {
             paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromAccount, "VENDOR_PAYMENT");
             }
             */
            paymentEntryHeaderPanel.clearDialogFields();
            paymentInvoiceApplicationPanel.clearDialogFields();

            //set order
            paymentEntryHeaderPanel.setPaymentComposite(paymentComposite);
            //update the dialog
            paymentEntryHeaderPanel.setDialogFields();
            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());
            loadPartyInvoices(paymentToPartyId);

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadPayment(String paymentId) {
        try {

//            Account paymentFromAccount = PartyListSingleton.getAccount(paymentFromPartyId);
//            Account paymentToAccount = PartyListSingleton.getAccount(paymentToPartyId);
//            PaymentComposite paymentComposite = LoadPaymentWorker.newPayment(ControllerOptions.getSession(), paymentFromAccount, paymentToAccount);
            PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(paymentId, ControllerOptions.getSession());
            String paymentFromPartyId = paymentComposite.getPayment().getpartyIdTo();
//            order.getOrderItemsList().add(orderItem);
            Debug.logError("loadPayment: paymentFromPartyId: " + paymentFromPartyId, module);

            //clear dialog
            paymentEntryHeaderPanel.clearDialogFields();
            paymentInvoiceApplicationPanel.clearDialogFields();
            //set order
            paymentEntryHeaderPanel.setPaymentComposite(paymentComposite);

            //update the dialog
            paymentEntryHeaderPanel.setDialogFields();

            setPaymentToActions(paymentEntryHeaderPanel.getPaymentComposite());

            loadPartyInvoices(paymentFromPartyId);

            paymentInvoiceApplicationPanel.setPaymentAmount(paymentComposite.getPayment().getamount());
            paymentInvoiceApplicationPanel.setUnAllocatedAmount(paymentComposite.getPayment().getamount().subtract(paymentComposite.getAppliedAmount()));
            paymentInvoiceApplicationPanel.setAllocatedAmount(paymentComposite.getAppliedAmount());

            paymentApplicationListModel.clear();

            paymentApplicationListModel.addAll(paymentComposite.getPaymentApplicationCompositeList().getList());
            paymentApplicationPanel.setPaymentApplicationList(paymentApplicationListModel);

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //set and handle all dialog product actions
    public void loadPartyInvoices(String partyId) {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

//            paymentCompositeListModel.clear();
            invoiceCompositeListModel.clear();
            if (partyId == null || partyId.isEmpty()) {
                return;
            }

            Map<String, Object> findOptionList = FastMap.newInstance();
            if (partyId != null && partyId.isEmpty() == false) {
                PaymentType paymentType = options.getPaymentType();
                if ("CUSTOMER_REFUND".equals(paymentType.getpaymentTypeId())) {
                    findOptionList.put("partyId", partyId);
                } else {
                    findOptionList.put("partyIdFrom", partyId);
                }
            }

            List<InvoiceComposite> invoices = LoadInvoiceWorker.loadInvoices(findOptionList, ControllerOptions.getSession(), paymentInvoiceApplicationPanel.cbShowZeroAmount.isSelected());
            invoiceCompositeListModel.addAll(invoices);
            Debug.logWarning("invoiceCompositeListModel size: " + invoiceCompositeListModel.getSize(), module);

            paymentInvoiceApplicationPanel.setupEditOrderTable();

            paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().addActionListener(tableDataChangeAction);
            paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);
            //load table
//            paymentInvoiceApplicationPanel.reloadItemDataModel(invoiceCompositeListModel);  //                  }

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //set and handle all dialog product actions
    public void loadOrderInvoices(String orderId) {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

//            paymentCompositeListModel.clear();
            invoiceCompositeListModel.clear();
            Map<String, Object> findOptionList = FastMap.newInstance();
            if (orderId == null || orderId.isEmpty()) {
                return;
            } else {
                findOptionList.put("orderId", orderId);
            }

            List<InvoiceComposite> invoices = LoadInvoiceWorker.loadInvoices(findOptionList, ControllerOptions.getSession(), paymentInvoiceApplicationPanel.cbShowZeroAmount.isSelected());
            invoiceCompositeListModel.addAll(invoices);
            Debug.logWarning("invoiceCompositeListModel size: " + invoiceCompositeListModel.getSize(), module);

            paymentInvoiceApplicationPanel.setupEditOrderTable();

            paymentInvoiceApplicationPanel.getInvoiceCompositePaymentTableModel().addActionListener(tableDataChangeAction);
            paymentInvoiceApplicationPanel.setPaymentApplicationList(invoiceCompositeListModel);
            //load table
//            paymentInvoiceApplicationPanel.reloadItemDataModel(invoiceCompositeListModel);  //                  }

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPaymentToActions(PaymentComposite paymentComposite) {

        invoiceCompositeListModel.clear();
        actPaymentCompositeListModel.clear();
        actPaymentCompositeListModel.add(paymentComposite);

        paymentStatusActionPopupButton.loadPopMenu(paymentComposite);
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

    String invoiceTypeId = "PURCHASE_INVOICE";

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
                        Logger.getLogger(ProductTreeActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ProductTreeActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
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
