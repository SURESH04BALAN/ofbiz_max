/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.composite.InvoiceItemCompositeList;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.PaymentGroupComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.FinAccount;
import org.ofbiz.ordermax.entity.PaymentGroupType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.FinAccountSingleton;
import org.ofbiz.ordermax.payment.PaymentGroupTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.UOMSingleton;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodInterface;

/**
 *
 * @author administrator
 */
public class CustomerPaymentGroupEntryHeaderPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = CustomerPaymentGroupEntryHeaderPanel.class.getName();
    //private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    public PartyPickerEditPanel panelCustomerPartyIdFromPicker;
    public PartyPickerEditPanel panelPartyToIdPicker;
    public DatePickerEditPanel paymentDatePickerEditPanel;
    protected Map<String, PaymentMethodInterface> paymentMethodPanels = new HashMap<String, PaymentMethodInterface>();
    //private JButton btnHeaderPatryId = null;
    //private PaymentComposite paymentComposite = null;
    private PaymentGroupComposite paymentGroupComposite = null;
    ControllerOptions controllerOptions = new ControllerOptions();
    //payment type
    //public JGenericComboBoxSelectionModel<PaymentMethodType> paymentMethodTypeComboBox = null;
    public JGenericComboBoxSelectionModel<PaymentType> paymentTypeComboBox = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboBox = null;
    public JGenericComboBoxSelectionModel<Uom> uomComboBox = null;
    public JGenericComboBoxSelectionModel<RoleType> roleTypeComboBox = null;
    public JGenericComboBoxSelectionModel<FinAccount> finAccountComboBox = null;
    public JGenericComboBoxSelectionModel<BillingAccount> billingAccountComboBox = null;
    PaymentMethodInterface paymentMethodInterface = null;
    JGenericComboBoxSelectionModel<PaymentGroupType> paymentGroupTypeIdComboBox;

    public PaymentGroupComposite getPaymentGroupComposite() {
        return paymentGroupComposite;
    }
    public PaymentComposite getPaymentComposite() {
        return paymentGroupComposite.getFirstPaymentComposite();
    }
    public void setPaymentGroupComposite(PaymentGroupComposite paymentGroupComposite) {
        this.paymentGroupComposite = paymentGroupComposite;
    }

    boolean showComboKeys = false;

    final void customInitComponent() {

        try {
            /*          paymentMethodComboBox.selectionModel.addListSelectionListener(new ListSelectionListener() {
             @Override
             public void valueChanged(ListSelectionEvent e) {

             if (!e.getValueIsAdjusting()) {
             ListSelectionModel lsm = (ListSelectionModel) e.getSource();
             Debug.logInfo("valueChanged ", "module");
             if (lsm.isSelectionEmpty() == false) {

             int minIndex = lsm.getMinSelectionIndex();
             int maxIndex = lsm.getMaxSelectionIndex();
             for (int i = minIndex; i <= maxIndex; i++) {
             if (lsm.isSelectedIndex(i)) {
             PaymentMethod paymentMethod = paymentMethodComboBox.comboBoxModel.getElementAt(i);
             paymentMethodInterface.setPaymentMethod(paymentMethod);
             Debug.logInfo("methhoid " + paymentMethod.getpaymentMethodId(), "module");
             if (mapList.containsKey(paymentMethod.getpaymentMethodId())) {
             Debug.logInfo("has methhoid " + paymentMethod.getpaymentMethodId(), "module");
             Map<String, GenericValue> valList = mapList.get(paymentMethod.getpaymentMethodId());
             Debug.logInfo("? getpaymentMethodTypeId " + paymentMethod.getpaymentMethodId(), "module");
             for (Map.Entry<String, GenericValue> mapIter : valList.entrySet()) {
             Debug.logInfo("mapIter key  " + mapIter.getEntityId(), module);
             }
             if (valList.containsKey(paymentMethod.getpaymentMethodId())) {
             Debug.logInfo("has getpaymentMethodId " + paymentMethod.getpaymentMethodId(), "module");
             GenericValue val = valList.get(paymentMethod.getpaymentMethodId());
             paymentMethodInterface.setPaymentMethodDetails(val);
             }
             }
             break;
             }
             }
             }
             }
             }
             });
             */
            ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentHeader, "Payment Group Header");
            ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentMethodDetails, "Payment Application");
        } catch (Exception ex) {
            Logger.getLogger(CustomerPaymentGroupEntryHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    NumberFormatter numFormatter = null;

    public CustomerPaymentGroupEntryHeaderPanel(ControllerOptions options)//  XuiSession session, String paymentParentTypeId) {
    {
        controllerOptions = options.getCopy();
        this.session = ControllerOptions.getSession();
        delegator = session.getDelegator();

        initComponents();
        NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numFormat.setMaximumFractionDigits(2);

        numFormatter = new NumberFormatter(numFormat);
//        numFormatter.setMinimum(5.0);
        numFormatter.setMaximum(10000000.0);
        numFormatter.setAllowsInvalid(true);
        numFormatter.setOverwriteMode(true);

//JGenericComboBoxSelectionModel<PaymentType> 
//        paymentMethodTypeComboBox = new JGenericComboBoxSelectionModel<PaymentMethodType>(panelPaymentMethodType, PaymentMethodTypeSingleton.getValueList());
        paymentTypeComboBox = new JGenericComboBoxSelectionModel<PaymentType>(panelPaymentType, PaymentTypeSingleton.getValueList(controllerOptions.getParentPaymentTypeId()));
        statusItemComboBox = new JGenericComboBoxSelectionModel<StatusItem>(panelStatus, StatusSingleton.getValueList("PMNT_STATUS"));
        uomComboBox = new JGenericComboBoxSelectionModel<Uom>(panelUom, UOMSingleton.getValueList("CURRENCY_MEASURE"));
        paymentGroupTypeIdComboBox = new JGenericComboBoxSelectionModel<PaymentGroupType>(panelPaymentMethod, PaymentGroupTypeSingleton.getValueList());
        billingAccountComboBox = new JGenericComboBoxSelectionModel<BillingAccount>(panelBillingAccount);

        ControllerOptions partyOptions = options.getCopy();
        panelCustomerPartyIdFromPicker = new PartyPickerEditPanel(partyOptions);
        panelCustomerPartyIdFromCode.setLayout(new BorderLayout());
        panelCustomerPartyIdFromCode.add(BorderLayout.CENTER, panelCustomerPartyIdFromPicker);
        panelCustomerPartyIdFromPicker.textIdField.setInputVerifier(new PartyIdVerifyValidator(panelCustomerPartyIdFromPicker.textIdField));

        partyOptions = options.getCopy();
        panelPartyToIdPicker = new PartyPickerEditPanel(partyOptions, panelPartyIdToCode);

        paymentDatePickerEditPanel = DatePickerEditPanel.addToPanel(panelPaymentDate);
//        panelSupplierPartyCode.setLayout(new BorderLayout());
//        panelSupplierPartyCode.add(BorderLayout.CENTER, panelCustomerPartyIdFromPicker);

        customInitComponent();
    }

    Map<String, Map<String, GenericValue>> mapList = null;

    final public void getDialogFields() {
        PaymentComposite paymentComposite = paymentGroupComposite.getFirstPaymentComposite();
        if (paymentComposite != null) {
            try {
                paymentComposite.getPayment().setpartyIdFrom(panelCustomerPartyIdFromPicker.textIdField.getText());
                paymentComposite.getPayment().setpartyIdTo(panelPartyToIdPicker.textIdField.getText());
//            paymentComposite.getPartyPaymentTo().getParty().setpartyId(partyIdTextFieldFrom.getText());
                paymentComposite.getPayment().setpaymentId(txtPaymentGroupId.getText());
                paymentComposite.getPayment().seteffectiveDate((java.sql.Timestamp) paymentDatePickerEditPanel.getTimeStamp());
                if (paymentMethodInterface != null) {
                    paymentComposite.getPayment().setpaymentRefNum(paymentMethodInterface.getReference());
                    paymentComposite.getPayment().setcomments(paymentMethodInterface.getComment());
                }

            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            try {
               // paymentComposite.getPayment().setamount(new BigDecimal(txtPaymentAmount.getText()));
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            try {
                if (statusItemComboBox.getSelectedItem() != null) {
                    paymentComposite.getPayment().setstatusId(((StatusItem) statusItemComboBox.getSelectedItem()).getstatusId());
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            try {
                if (paymentTypeComboBox.getSelectedItem() != null) {
                    paymentComposite.getPayment().setpaymentTypeId(((PaymentType) paymentTypeComboBox.getSelectedItem()).getpaymentTypeId());
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            try {
                if (billingAccountComboBox.getSelectedItem() != null && billingAccountComboBox.getSelectedItem().getbillingAccountId() != null) {
                    paymentComposite.setBillingAccount(billingAccountComboBox.getSelectedItem());
                } else {
                    paymentComposite.setBillingAccount(null);
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            /*        try {
             if (paymentMethodTypeComboBox.getSelectedItem() != null) {
             paymentComposite.getPayment().setpaymentMethodTypeId(((PaymentMethodType) paymentMethodTypeComboBox.getSelectedItem()).getpaymentMethodTypeId());
             }
             } catch (Exception ex) {
             Debug.logError(ex, module);
             }
             */
            try {
                Debug.logInfo("uomComboBox.getSelectedItem()", module);
                if (uomComboBox.getSelectedItem() != null) {
                    Debug.logInfo("uomComboBox.getSelectedItem() currency: " + ((Uom) uomComboBox.getSelectedItem()).getuomId(), module);

                    paymentComposite.getPayment().setcurrencyUomId(((Uom) uomComboBox.getSelectedItem()).getuomId());
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            /*   try {
             if (finAccountComboBox.getSelectedItem() != null) {
             paymentComposite.getPayment().setfinAccountTransId(((FinAccount) finAccountComboBox.getSelectedItem()).getfinAccountId());
             }
             } catch (Exception ex) {
             Debug.logError(ex, module);
             }
             */
        }
    }

    public JTextField geCustomerPartyIdFromTextField() {
        return panelCustomerPartyIdFromPicker.textIdField;
    }

    public JTextField getCustomerPartyIdFromTextField() {
        return panelCustomerPartyIdFromPicker.textIdField;
    }

    final public void clearDialogFields() {
        panelCustomerPartyIdFromPicker.textIdField.setText("");
        panelPartyToIdPicker.textIdField.setText("");
        txtOverideGlTypeId.setText("");
        txtPaymentAmount.setText(BigDecimal.ZERO.toString());
        paymentDatePickerEditPanel.setCurrentDate();
        txtPaymentGroupId.setText("");
//        editReference.setText("");
//        editComment.setText("");
    }

    final public void setDialogFields() {
        PaymentComposite paymentComposite = paymentGroupComposite.getFirstPaymentComposite();
        if (paymentComposite != null) {
            try {

                Debug.logInfo("From: " + paymentComposite.getPayment().getpartyIdFrom(), module);
                Debug.logInfo("To: " + paymentComposite.getPayment().getpartyIdTo(), module);

                panelCustomerPartyIdFromPicker.textIdField.setText(paymentComposite.getPayment().getpartyIdFrom());
                panelPartyToIdPicker.textIdField.setText(paymentComposite.getPayment().getpartyIdTo());

                txtPaymentGroupId.setText(paymentGroupComposite.getPaymentGroup().getPaymentGroupId());
                paymentDatePickerEditPanel.setDate(paymentComposite.getPayment().geteffectiveDate());
                if (paymentMethodInterface != null) {
                    paymentMethodInterface.setReference(paymentComposite.getPayment().getpaymentRefNum());
                    paymentMethodInterface.setComment(paymentComposite.getPayment().getcomments());
                }

            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            try {
               //if(UtilValidate.isNotEmpty(paymentGroupComposite.getPaymentGroup().getPaymentGroupId()!=null)) {
                    txtPaymentAmount.setText(paymentGroupComposite.getTotalPayment().toString());
              // }
              // else{
              //  txtPaymentAmount.setText(paymentComposite.getPayment().getamount().toString());
              // }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            try {
                statusItemComboBox.setSelectedItem(StatusSingleton.getStatusItem(paymentComposite.getPayment().getstatusId()));
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            try {
                paymentTypeComboBox.setSelectedItem(PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()));
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
            /*
             try {
             paymentMethodTypeComboBox.setSelectedItem(PaymentMethodTypeSingleton.getPaymentMethodType(paymentComposite.getPayment().getpaymentMethodTypeId()));
             } catch (Exception ex) {
             Debug.logError(ex, module);
             }
             */
            try {
                uomComboBox.setSelectedItem(UOMSingleton.getUom(paymentComposite.getPayment().getcurrencyUomId()));
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            try {
                if (paymentComposite.getPayment().getfinAccountTransId() != null && (paymentComposite.getPayment().getfinAccountTransId().trim()).isEmpty() == false) {
                    finAccountComboBox.setSelectedItem(FinAccountSingleton.getFinAccount(paymentComposite.getPayment().getfinAccountTransId()));
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            try {
                if (paymentComposite.getBillingAccount() != null) {
                    for (int i = 0; i < billingAccountComboBox.comboBoxModel.getSize(); ++i) {
                        BillingAccount ba = billingAccountComboBox.comboBoxModel.getElementAt(i);
                        if (ba.getbillingAccountId() != null && ba.getbillingAccountId().equals(paymentComposite.getBillingAccount().getbillingAccountId())) {
                            billingAccountComboBox.setSelectedItem(ba);
                        }
                    }
                }

            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

            Debug.logError("txtPaymentId.getText(): " + txtPaymentGroupId.getText(), module);
            panelPaymentHeader.updateUI();
        }
    }

    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(panelCustomerPartyIdFromPicker.textIdField.getText())) {
            OrderMaxOptionPane.showConfirmDialog(null, "Customer id is empty", "Payment Header",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
            panelCustomerPartyIdFromPicker.textIdField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(panelPartyToIdPicker.textIdField.getText())) {
            OrderMaxOptionPane.showConfirmDialog(null, "Payment to party id is empty", "Payment Header",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

            panelPartyToIdPicker.textIdField.requestFocus();
            result = false;
        } else if (getPaymentTotal().equals(BigDecimal.ZERO)) {
            OrderMaxOptionPane.showConfirmDialog(null, "Payment amount is empty", "Payment Header",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

            txtPaymentAmount.requestFocus();
            result = false;
        }

        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        panelPaymentMethodDetails = new javax.swing.JPanel();
        panelPaymentHeader = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JTextField();
        panelCustomerPartyIdFromCode = new javax.swing.JPanel();
        panelPartyIdToCode = new javax.swing.JPanel();
        panelPaymentDate = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        panelPaymentType = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        panelBillingAccount = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        panelUom = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtPaymentGroupId = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtOverideGlTypeId = new javax.swing.JTextField();
        btnGlTypeId = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        panelPaymentMethod = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelPaymentMethodDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelPaymentMethodDetailsLayout = new javax.swing.GroupLayout(panelPaymentMethodDetails);
        panelPaymentMethodDetails.setLayout(panelPaymentMethodDetailsLayout);
        panelPaymentMethodDetailsLayout.setHorizontalGroup(
            panelPaymentMethodDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );
        panelPaymentMethodDetailsLayout.setVerticalGroup(
            panelPaymentMethodDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        jPanel2.add(panelPaymentMethodDetails, java.awt.BorderLayout.CENTER);

        panelPaymentHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentHeader.setAlignmentX(0.0F);
        panelPaymentHeader.setFocusable(false);
        panelPaymentHeader.setMaximumSize(new java.awt.Dimension(770, 225));
        panelPaymentHeader.setMinimumSize(new java.awt.Dimension(770, 205));
        panelPaymentHeader.setPreferredSize(new java.awt.Dimension(770, 200));
        panelPaymentHeader.setLayout(new java.awt.BorderLayout());

        jPanel13.setPreferredSize(new java.awt.Dimension(1002, 120));
        jPanel13.setLayout(new java.awt.GridLayout(1, 3));

        jPanel18.setMinimumSize(new java.awt.Dimension(0, 205));
        jPanel18.setPreferredSize(new java.awt.Dimension(341, 205));
        jPanel18.setLayout(null);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Payment Date:");
        jPanel18.add(jLabel18);
        jLabel18.setBounds(113, 117, 110, 16);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Customer Code:");
        jPanel18.add(jLabel1);
        jLabel1.setBounds(113, 1, 110, 16);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("To Company Code:");
        jPanel18.add(jLabel20);
        jLabel20.setBounds(113, 30, 110, 16);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Payment Amount:");
        jPanel18.add(jLabel21);
        jLabel21.setBounds(121, 149, 102, 16);
        jPanel18.add(txtPaymentAmount);
        txtPaymentAmount.setBounds(235, 146, 180, 22);

        javax.swing.GroupLayout panelCustomerPartyIdFromCodeLayout = new javax.swing.GroupLayout(panelCustomerPartyIdFromCode);
        panelCustomerPartyIdFromCode.setLayout(panelCustomerPartyIdFromCodeLayout);
        panelCustomerPartyIdFromCodeLayout.setHorizontalGroup(
            panelCustomerPartyIdFromCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelCustomerPartyIdFromCodeLayout.setVerticalGroup(
            panelCustomerPartyIdFromCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel18.add(panelCustomerPartyIdFromCode);
        panelCustomerPartyIdFromCode.setBounds(235, 1, 180, 22);

        javax.swing.GroupLayout panelPartyIdToCodeLayout = new javax.swing.GroupLayout(panelPartyIdToCode);
        panelPartyIdToCode.setLayout(panelPartyIdToCodeLayout);
        panelPartyIdToCodeLayout.setHorizontalGroup(
            panelPartyIdToCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelPartyIdToCodeLayout.setVerticalGroup(
            panelPartyIdToCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel18.add(panelPartyIdToCode);
        panelPartyIdToCode.setBounds(235, 30, 180, 22);

        javax.swing.GroupLayout panelPaymentDateLayout = new javax.swing.GroupLayout(panelPaymentDate);
        panelPaymentDate.setLayout(panelPaymentDateLayout);
        panelPaymentDateLayout.setHorizontalGroup(
            panelPaymentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelPaymentDateLayout.setVerticalGroup(
            panelPaymentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel18.add(panelPaymentDate);
        panelPaymentDate.setBounds(235, 117, 180, 22);

        jLabel28.setText("Payment Type:");
        jPanel18.add(jLabel28);
        jLabel28.setBounds(137, 59, 86, 16);

        panelPaymentType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentType.setName(""); // NOI18N
        panelPaymentType.setLayout(new java.awt.GridLayout(1, 0));
        jPanel18.add(panelPaymentType);
        panelPaymentType.setBounds(235, 59, 180, 22);

        jLabel25.setText("Billing Account Id:");
        jPanel18.add(jLabel25);
        jLabel25.setBounds(121, 88, 102, 16);

        panelBillingAccount.setMinimumSize(new java.awt.Dimension(0, 25));
        panelBillingAccount.setName(""); // NOI18N
        panelBillingAccount.setLayout(new java.awt.GridLayout(1, 0));
        jPanel18.add(panelBillingAccount);
        panelBillingAccount.setBounds(235, 88, 180, 22);

        jPanel13.add(jPanel18);

        panelUom.setMinimumSize(new java.awt.Dimension(0, 25));
        panelUom.setName(""); // NOI18N
        panelUom.setLayout(new java.awt.GridLayout(1, 0));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("Payment Group Id:");

        txtPaymentGroupId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaymentGroupIdFocusLost(evt);
            }
        });

        jLabel30.setText("Currency:");

        jLabel29.setText("Overide GL Account:");

        btnGlTypeId.setText("jButton1");
        btnGlTypeId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGlTypeIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("Payment Group Type:");

        panelPaymentMethod.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentMethod.setName(""); // NOI18N
        panelPaymentMethod.setLayout(new java.awt.GridLayout(1, 0));

        jLabel24.setText("Status:");

        panelStatus.setMinimumSize(new java.awt.Dimension(0, 25));
        panelStatus.setName(""); // NOI18N
        panelStatus.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentGroupId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txtPaymentGroupId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(panelPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)))
        );

        jPanel13.add(jPanel16);

        panelPaymentHeader.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel2.add(panelPaymentHeader, java.awt.BorderLayout.PAGE_START);

        panelButton.setPreferredSize(new java.awt.Dimension(1003, 50));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1003, Short.MAX_VALUE)
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel2.add(panelButton, java.awt.BorderLayout.PAGE_END);

        paymentTabbedPane.addTab("Payment Header", jPanel2);

        add(paymentTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPaymentGroupIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentGroupIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentGroupIdFocusLost

    private void btnGlTypeIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGlTypeIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGlTypeIdActionPerformed
    private Locale getLocale(String loc) {
        if (loc != null && loc.length() > 0) {
            return new Locale(loc);
        } else {
            return Locale.getDefault();
        }

    }
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsModified(boolean isModified) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class PartyIdVerifyValidator extends InputVerifier {

        private JTextField field;
        private double value;
        private static final String ZERO = "0.0";
        private GenericValue party = null;

        public PartyIdVerifyValidator(JTextField field) {
            this.field = field;
        }

        public void setPaymentMethod() {
            /*
             orderHeader = new Order(Order.ORDERTYPE_PURCHSEORDER);
             orderHeader.setstatusId("ORDER_CREATED");
             orderHeader.setorderTypeId("PURCHASE_ORDER");
             orderHeader.setsalesChannelEnumId("PHONE_SALES_CHANNEL");
             orderHeader.setorderDate(UtilDateTime.nowTimestamp());
             orderHeader.setpriority("2");
             orderHeader.setentryDate(UtilDateTime.nowTimestamp());
             orderHeader.setcreatedBy(session.getUserPartyId());
             //    orderHeader.setorderId(delegator.getNextSeqId("OrderHeader"));
             orderHeader.setremainingSubTotal(BigDecimal.ZERO);
             orderHeader.setgrandTotal(BigDecimal.ZERO);
             orderHeader.setcurrencyUom(UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
             orderHeader.setPartyId(partyIdTextField.getText());
             orderHeader.setOwnerPartyId(PosProductHelper.organizationPartyId);
             orderHeader.setDestinationFacilityId((String) session.getAttribute("facilityId"));

             if (facilityIdCombo != null) {
             facilityIdCombo.setSelectedItemId(orderHeader.getDestinationFacilityId());
             }

             //        String currencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
             //        String origCurrencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
             //        String destinationFacilityId = "mainwarehouse"; //facilityListBidingCombo.get(comboFacility.getSelectedIndex());

             clearDialogFields();
             loadItemEditDataModel();
             //    ContactMechPanelMain.Phone, ContactMechPanelMain.TELECOMNUMBER        
             String purposeId = Order.DELIVERY_LOCATION;
             shippConactMechId = orderHeader.getContactMechId(purposeId);
             if (shippConactMechId == null) {
             purposeId = OrderMaxUtility.getValidContactMechIdPurposeTypeId(partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);
             shippConactMechId = OrderMaxUtility.getValidContactMechId(purposeId, partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);//
             orderHeader.addContactMechId(purposeId, shippConactMechId);
             }

             Debug.logInfo("shippConactMechId: " + shippConactMechId + " purposeId: " + purposeId, module);
             editDeliveryAddress.setText(getContachMechDetail(shippConactMechId, partyMechList));
             GenericValueComboBox.setSelectedItemId(comboDeliveryAddress, purposeId);

             purposeId = Order.BILLING_LOCATION;
             billingAddressMechId = orderHeader.getContactMechId(purposeId);
             if (billingAddressMechId == null) {
             purposeId = OrderMaxUtility.getValidContactMechIdPurposeTypeId(partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);
             billingAddressMechId = OrderMaxUtility.getValidContactMechId(purposeId, partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);//
             orderHeader.addContactMechId(purposeId, billingAddressMechId);
             }
             Debug.logInfo("billingAddressMechId: " + billingAddressMechId + " purposeId: " + purposeId, module);
             editBillingAddress.setText(getContachMechDetail(billingAddressMechId, partyMechList));
             GenericValueComboBox.setSelectedItemId(comboBillingAddress, purposeId);

             purposeId = Order.DELIVERY_PHONE;
             phoneConactMechId = orderHeader.getContactMechId(purposeId);
             if (phoneConactMechId == null) {
             purposeId = OrderMaxUtility.getValidContactMechIdPurposeTypeId(partyMechList, ContactMechPanelMain.Phone, ContactMechPanelMain.TELECOMNUMBER);
             phoneConactMechId = OrderMaxUtility.getValidContactMechId(purposeId, partyMechList, ContactMechPanelMain.Phone, ContactMechPanelMain.TELECOMNUMBER);//
             orderHeader.addContactMechId(purposeId, phoneConactMechId);
             }
             Debug.logInfo("phoneConactMechId: " + phoneConactMechId + " purposeId: " + purposeId, module);
             editPhone.setText(getContachMechDetail(phoneConactMechId, partyMechList));
             GenericValueComboBox.setSelectedItemId(comboPhone, purposeId);

             GenericValueComboBox.setSelectedItemId(comboEmail, Order.BILLING_EMAIL);

             notifyListeners(OrderMaxUtility.ITEM_NEW, orderHeader.getorderId(), orderHeader.getorderId());
             // Set up table attributes....
             //setTabMapping(table, 0, 1, (tableModel.getColumnCount() - 1));
             * */
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            if (verify(input)) {
//                partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), field.getText(), false);
                //   paymentMethodTypeComboBox.setDataList(PaymentMethodTypeSingleton.getValueList(/*field.getText()*/));
                newItem();
                return true;
            } else {
                field.selectAll();
                return false;
            }
        }

        @Override
        public boolean verify(JComponent input) {
            try {
                party = PosProductHelper.getParty(field.getText(), delegator);
                if (party != null) {
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public BigDecimal getPaymentAmount() {
        BigDecimal value = BigDecimal.ZERO;
        try {
            BigDecimal val = new BigDecimal(txtPaymentAmount.getText());
            value = val;
        } catch (Exception e) {
            value = null;
        }

        return value;
    }

    public BigDecimal getPaymentDiscountAmount() {
        BigDecimal value = BigDecimal.ZERO;
        try {
            BigDecimal val = BigDecimal.ZERO;//new BigDecimal(txtSettlementDiscount.getText());
            value = val;
        } catch (Exception e) {
            value = null;
        }

        return value;
    }

    public boolean isValidAmount() {
        BigDecimal amt = getPaymentAmount();
        if (amt == null) {
            txtPaymentAmount.requestFocus();
            return false;
        }
        return true;
    }

    public void setPaymentAmount(BigDecimal val) {
        txtPaymentAmount.setText(val.toString());
    }

    public void setContactDetails(String val) {

    }

    public void setPartyId(String val) {
        panelCustomerPartyIdFromPicker.textIdField.setText(val);
    }

    public void setPaymentDate(String val) {
        paymentDatePickerEditPanel.txtDate.setText(val);
    }

    public BigDecimal getUnallocateAmount() {
        return BigDecimal.ZERO;
    }

    public String getContactDetails() {
        return "";
    }

    public String getPartyId() {
        return panelCustomerPartyIdFromPicker.textIdField.getText();
    }

    public String getPaymentDate() {
        return paymentDatePickerEditPanel.txtDate.getText();
    }

    public BigDecimal getAmountFromText(String text) {
        BigDecimal amount = BigDecimal.ZERO;
        if (text != null && text.isEmpty() == false) {
            try {
                amount = new BigDecimal(text);
            } catch (Exception e) {
                amount = BigDecimal.ZERO;
            }
        }
        return amount;
    }

    public BigDecimal getPaymentTotal() {
        return new BigDecimal(txtPaymentAmount.getText());
    }

    public BigDecimal getDiscountTotal() {
        return BigDecimal.ZERO; //getAmountFromText(txtSettlementDiscount.getText());
    }

    public BigDecimal getChequeTotal() {
        return BigDecimal.ZERO; //getAmountFromText(txtChequeAmount.getText());
    }

    public BigDecimal getCashTotal() {
        return BigDecimal.ZERO; //getAmountFromText(txtCashAmount.getText());
    }

    public BigDecimal getCardTotal() {
        return BigDecimal.ZERO;//getAmountFromText(txtCardAmount.getText());
    }

    public BigDecimal getEftTotal() {
        return BigDecimal.ZERO;//getAmountFromText(txtOtherEft.getText());
    }

    public BigDecimal getTotalDiffPayments() {
        return getChequeTotal().add(getCashTotal().add(getCardTotal().add(getEftTotal())));
    }

    public void reloadItemDataModel(InvoiceItemCompositeList cutdownList) {

    }

    public BigDecimal getTotalPaymentsIncDiscount() {
        return getPaymentTotal().add(getDiscountTotal());
    }

    public JButton getBtnItemPatryId() {
        return null;//btnItemPatryId;
    }

    public void highlightLastRow(int row) {

    }

    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGlTypeId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelBillingAccount;
    public javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelCustomerPartyIdFromCode;
    private javax.swing.JPanel panelPartyIdToCode;
    private javax.swing.JPanel panelPaymentDate;
    private javax.swing.JPanel panelPaymentHeader;
    private javax.swing.JPanel panelPaymentMethod;
    public javax.swing.JPanel panelPaymentMethodDetails;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelUom;
    public javax.swing.JTabbedPane paymentTabbedPane;
    private javax.swing.JTextField txtOverideGlTypeId;
    public javax.swing.JTextField txtPaymentAmount;
    public javax.swing.JTextField txtPaymentGroupId;
    // End of variables declaration//GEN-END:variables
}
