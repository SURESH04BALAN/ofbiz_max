/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.party.contact.ContactMechWorker;
import java.awt.CardLayout;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import mvc.model.list.FinAccountListCellRenderer;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.PaymentMethodListCellRenderer;
import mvc.model.list.PaymentTypeListCellRenderer;
import mvc.model.list.RoleTypeListCellRenderer;
import mvc.model.list.StatusItemTypeListCellRenderer;
import mvc.model.list.UomListCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceItemCompositeList;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.FinAccount;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.paymentmethod.ChequePaymentPanel;
import org.ofbiz.ordermax.payment.CreditCardDetailForm;
import org.ofbiz.ordermax.payment.FinAccountSingleton;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.payment.UOMSingleton;

/**
 *
 * @author administrator
 */
public class PaymentEntryHeaderPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = PaymentEntryHeaderPanel.class.getName();
    private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private java.awt.Frame parentFrame = null;
    //private GenericValueComboBox facilityIdCombo;
    protected JPanel paymentCardPanel = null;
    static String CHEQUE_TAB_INDEX = "CHEQUECARD";
    static String CARD_TAB_INDEX = "CREDITCARD";
    static String CASH_TAB_INDEX = "CASHCARD";
    protected String visibleCardName = null;
    protected CreditCardDetailForm creditCardDetailForm = null;
    protected ChequePaymentPanel chequeDetailForm = null;
    protected JPanel cashPanel = new JPanel();
    ;
    private JButton btnHeaderPatryId = null;
    private PaymentComposite paymentComposite = null;

    //payment type
    private ListComboBoxModel<PaymentType> paymentTypeComboBoxModel = new ListComboBoxModel<PaymentType>();
    private JComboBox<PaymentType> paymentTypeComboBox = new JComboBox<PaymentType>(paymentTypeComboBoxModel);

    //payment method
    private ListComboBoxModel<PaymentMethodType> paymentMethodTypeComboBoxModel = new ListComboBoxModel<PaymentMethodType>();
    private JComboBox<PaymentMethodType> paymentMethodTypeComboBox = new JComboBox<PaymentMethodType>(paymentMethodTypeComboBoxModel);

    //role type method
    private ListComboBoxModel<RoleType> roleTypeComboBoxModel = new ListComboBoxModel<RoleType>();
    private JComboBox<RoleType> roleTypeComboBox = new JComboBox<RoleType>(roleTypeComboBoxModel);

    //role type method
    private ListComboBoxModel<Uom> uomComboBoxModel = new ListComboBoxModel<Uom>();
    private JComboBox<Uom> uomComboBox = new JComboBox<Uom>(uomComboBoxModel);

    //status type method
    private ListComboBoxModel<StatusItem> statusItemComboBoxModel = new ListComboBoxModel<StatusItem>();
    private JComboBox<StatusItem> statusItemComboBox = new JComboBox<StatusItem>(statusItemComboBoxModel);

    //status type method
    private ListComboBoxModel<FinAccount> finAccountComboBoxModel = new ListComboBoxModel<FinAccount>();
    private JComboBox<FinAccount> finAccountComboBox = new JComboBox<FinAccount>(finAccountComboBoxModel);

    public PaymentComposite getPaymentComposite() {
        return paymentComposite;
    }

    public void setPaymentComposite(PaymentComposite paymentComposite) {
        this.paymentComposite = paymentComposite;
    }

    /**
     * Creates new form PaymentPanel
     */
    /*    public PaymentEntryPanel() {
     initComponents();
     customInitComponent();
        
     }
     */
    boolean showComboKeys = false;

    final void customInitComponent() {
        paymentTypeComboBoxModel.setListModel(PaymentTypeSingleton.getValueListModal());
        panelPaymentType.setLayout(new BorderLayout());
        panelPaymentType.add(BorderLayout.CENTER, paymentTypeComboBox);
        ListCellRenderer<PaymentType> paymentTypeRenderer = new PaymentTypeListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        paymentTypeComboBox.setRenderer(paymentTypeRenderer);

        paymentMethodTypeComboBoxModel.setListModel(PaymentMethodTypeSingleton.getValueListModal());
        panelPaymentMethodType.setLayout(new BorderLayout());
        panelPaymentMethodType.add(BorderLayout.CENTER, paymentMethodTypeComboBox);
        ListCellRenderer<PaymentMethodType> paymentMethodTypeRenderer = new PaymentMethodListCellRenderer(showComboKeys);
        paymentMethodTypeComboBox.setRenderer(paymentMethodTypeRenderer);

        roleTypeComboBoxModel.setListModel(RoleTypeSingleton.getValueListModal());
        panelRoleType.setLayout(new BorderLayout());
        panelRoleType.add(BorderLayout.CENTER, roleTypeComboBox);
        RoleTypeListCellRenderer roleTypeRenderer = new RoleTypeListCellRenderer(showComboKeys);
        roleTypeComboBox.setRenderer(roleTypeRenderer);

        statusItemComboBoxModel.setListModel(StatusSingleton.getValueListModal("PMNT_STATUS"));
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, statusItemComboBox);
        StatusItemTypeListCellRenderer statusItemRenderer = new StatusItemTypeListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        statusItemComboBox.setRenderer(statusItemRenderer);

//        uomComboBoxModel.setListModel(UOMSingleton.getValueListModal("CURRENCY_MEASURE"));
        uomComboBoxModel.setListModel(UOMSingleton.getValueListModal("CURRENCY_MEASURE"));
        panelUom.setLayout(new BorderLayout());
        panelUom.add(BorderLayout.CENTER, uomComboBox);
        UomListCellRenderer uomRenderer = new UomListCellRenderer();
        uomComboBox.setRenderer(uomRenderer);

        finAccountComboBoxModel.setListModel(FinAccountSingleton.getValueListModal());
        panelFinAccount.setLayout(new BorderLayout());
        panelFinAccount.add(BorderLayout.CENTER, finAccountComboBox);
        FinAccountListCellRenderer finAccountRenderer = new FinAccountListCellRenderer();
        finAccountComboBox.setRenderer(finAccountRenderer);

        /*
         SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
         DateFormatter formatter = new DateFormatter(dateformat);
         dateformat.setLenient(false);
         formatter.setAllowsInvalid(false);
         formatter.setOverwriteMode(true);

         txtPaymentDate = new JFormattedTextField(formatter);
         txtPaymentDate.setValue(new Date());
         */
//        initComponent1();
        paymentCardPanel = new JPanel(new CardLayout());
        chequeDetailForm = new ChequePaymentPanel();
        creditCardDetailForm = new CreditCardDetailForm();
        paymentCardPanel.add(chequeDetailForm, CHEQUE_TAB_INDEX);
        paymentCardPanel.add(creditCardDetailForm, CARD_TAB_INDEX);
        paymentCardPanel.add(cashPanel, CASH_TAB_INDEX);

//        OrderMaxUtility.addAPanelToBorder(paymentCardPanel, panelPaymentDetail);
        changeCard(CASH_TAB_INDEX);

//        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, delegator);
//        facilityIdCombo = new GenericValueComboBox(comboFacility, genFacilityList, "Facility", "facilityId", "facilityName");
        //prty id lookup
//        btnHeaderPatryId = new JButton("..");
//        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
  //      cb.install(partyIdTextFieldFrom);
       // btnCustomerCode.addActionListener(new LookupActionListner(partyIdTextFieldFrom, "partyIdTextField", "BILL_TO_CUSTOMER", null));

//        JToggleButton bntCompany = new JToggleButton("..");
//        cb = new ComponentBorder(bntCompany);
//        cb.install(partyIdTextFieldTo);
       // btnCompanyCode.addActionListener(new LookupActionListner(partyIdTextFieldTo, "partyIdTextField", "BILL_FROM_VENDOR", null));

//        JButton btnGlTypeId = new JButton("..");
//        cb = new ComponentBorder(btnGlTypeId);
//        cb.install(txtOverideGlTypeId);
        btnGlTypeId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
//        setupNewContactMap();
//        partyIdTextField.setInputVerifier(new PartyIdVerifyValidator(partyIdTextField));
//        txtPaymentAmount.setInputVerifier(new BigDecimalValidator(txtPaymentAmount));

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentHeader, "Payment Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentDetails, "Payment Application");

    }
    NumberFormatter numFormatter = null;

    public PaymentEntryHeaderPanel(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numFormat.setMaximumFractionDigits(2);

        numFormatter = new NumberFormatter(numFormat);
//        numFormatter.setMinimum(5.0);
        numFormatter.setMaximum(10000000.0);
        numFormatter.setAllowsInvalid(true);
        numFormatter.setOverwriteMode(true);

        initComponents();
        customInitComponent();
    }

    public void changeCard(String desc) {
        if (paymentCardPanel != null) {
            CardLayout cl = (CardLayout) (paymentCardPanel.getLayout());
            cl.show(paymentCardPanel, desc);
            visibleCardName = desc;
        }
    }

    public JTextField getPartyTextField() {
        return partyIdTextFieldFrom;
    }

    final public void getDialogFields() {
        try {

            paymentComposite.getPayment().setpartyIdTo(partyIdTextFieldTo.getText());
            paymentComposite.getPayment().setpaymentId(txtPaymentId.getText());
            paymentComposite.getPayment().seteffectiveDate((java.sql.Timestamp) txtPaymentDate.getValue());
            paymentComposite.getPayment().setpaymentRefNum(editReference.getText());
            paymentComposite.getPayment().setcomments(editComment.getText());

        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {

            paymentComposite.getPayment().setamount(new BigDecimal(txtPaymentAmount.getText()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        
        try {
            if (statusItemComboBoxModel.getSelectedItem() != null) {
                paymentComposite.getPayment().setstatusId(((StatusItem) statusItemComboBoxModel.getSelectedItem()).getstatusId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {
            if (paymentTypeComboBoxModel.getSelectedItem() != null) {
                paymentComposite.getPayment().setpaymentTypeId(((PaymentType) paymentTypeComboBoxModel.getSelectedItem()).getpaymentTypeId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            if (paymentMethodTypeComboBoxModel.getSelectedItem() != null) {
                paymentComposite.getPayment().setpaymentMethodTypeId(((PaymentMethodType) paymentMethodTypeComboBoxModel.getSelectedItem()).getpaymentMethodTypeId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            if (uomComboBoxModel.getSelectedItem() != null) {
                paymentComposite.getPayment().setcurrencyUomId(((Uom) uomComboBoxModel.getSelectedItem()).getuomId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            if (finAccountComboBoxModel.getSelectedItem() != null) {
                paymentComposite.getPayment().setfinAccountTransId(((FinAccount) finAccountComboBoxModel.getSelectedItem()).getfinAccountId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

    }

    final public void clearDialogFields() {
        partyIdTextFieldFrom.setText("");
        partyIdTextFieldTo.setText("");
        txtOverideGlTypeId.setText("");
        txtPaymentAmount.setText(BigDecimal.ZERO.toString());
        txtPaymentDate.setText("");
        txtPaymentId.setText("");
        editReference.setText("");
        editComment.setText("");
    }

    final public void setDialogFields() {

        /*        if (facilityIdCombo != null) {
         facilityIdCombo.setSelectedItemId((String) session.getAttribute("facilityId"));
         }
         */

        try {

            partyIdTextFieldFrom.setText(paymentComposite.getPayment().getpartyIdFrom());
            partyIdTextFieldTo.setText(paymentComposite.getPayment().getpartyIdTo());
            txtPaymentId.setText(paymentComposite.getPayment().getpaymentId());
            txtPaymentDate.setValue(paymentComposite.getPayment().geteffectiveDate());
            editReference.setText(paymentComposite.getPayment().getpaymentRefNum());
            editComment.setText(paymentComposite.getPayment().getcomments());

        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {

            txtPaymentAmount.setText(paymentComposite.getPayment().getamount().toString());
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {
            statusItemComboBoxModel.setSelectedItem(StatusSingleton.getStatusItem(paymentComposite.getPayment().getstatusId()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {
            paymentTypeComboBoxModel.setSelectedItem(PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            paymentMethodTypeComboBoxModel.setSelectedItem(PaymentMethodTypeSingleton.getPaymentMethodType(paymentComposite.getPayment().getpaymentMethodTypeId()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            uomComboBoxModel.setSelectedItem(UOMSingleton.getUom(paymentComposite.getPayment().getcurrencyUomId()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            uomComboBoxModel.setSelectedItem(UOMSingleton.getUom(paymentComposite.getPayment().getcurrencyUomId()));
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        try {
            if (paymentComposite.getPayment().getfinAccountTransId() != null && (paymentComposite.getPayment().getfinAccountTransId().trim()).isEmpty()==false) {
                finAccountComboBoxModel.setSelectedItem(FinAccountSingleton.getFinAccount(paymentComposite.getPayment().getfinAccountTransId()));
            }
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }

        panelPaymentHeader.updateUI();
        /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);

         try {
         roleTypeComboBoxModel.setSelectedItem(RoleTypeSingleton.getRoleType(paymentComposite.getPayment().getroleTypeIdTo()));
         } catch (Exception ex) {
         Debug.logError(ex, "module");
         }            /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        
         try {
         paymentTypeComboBoxModel.setSelectedItem(PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()));
         } catch (Exception ex) {
         Debug.logError(ex, "module");
         }            /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        
         String purposeId = Order.BILLING_LOCATION;
            
         purposeId = OrderMaxUtility.getValidContactMechIdPurposeTypeId(partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);
         String billingAddressMechId = OrderMaxUtility.getValidContactMechId(purposeId, partyMechList, ContactMechPanelMain.Location, ContactMechPanelMain.POSTALADDRESS);//
         Debug.logInfo(purposeId, module);
         txtContactDetails.setText(OrderMaxUtility.getContachMechDetail(billingAddressMechId, partyMechList));
         /*
         purposeId = Order.DELIVERY_PHONE;
         phoneConactMechId = orderHeader.getContactMechId(purposeId);
         if (phoneConactMechId == null) {
         purposeId = OrderMaxUtility.getValidContactMechIdPurposeTypeId(partyMechList, ContactMechPanelMain.Phone, ContactMechPanelMain.TELECOMNUMBER);
         phoneConactMechId = OrderMaxUtility.getValidContactMechId(purposeId, partyMechList, ContactMechPanelMain.Phone, ContactMechPanelMain.TELECOMNUMBER);//
         orderHeader.addContactMechId(purposeId, phoneConactMechId);
         }
         editPhone.setText(getContachMechDetail(phoneConactMechId, partyMechList));
            
         purposeId = Order.BILLING_EMAIL;
         emailConactMechId = orderHeader.getContactMechId(purposeId);
         editEmail.setText(getContachMechDetail(emailConactMechId, partyMechList));
         */

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPaymentHeader = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        partyIdTextFieldFrom = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter formatter = new DateFormatter(dateformat);
        dateformat.setLenient(false);
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        txtPaymentDate = new javax.swing.JFormattedTextField(formatter);
        jLabel20 = new javax.swing.JLabel();
        partyIdTextFieldTo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JTextField();
        panelFinAccount = new javax.swing.JPanel();
        btnCompanyCode = new javax.swing.JButton();
        btnCustomerCode = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelPaymentType = new javax.swing.JPanel();
        panelPaymentMethodType = new javax.swing.JPanel();
        panelUom = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtPaymentId = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        editComment = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        panelOverideGlAccount = new javax.swing.JPanel();
        txtOverideGlTypeId = new javax.swing.JTextField();
        btnGlTypeId = new javax.swing.JButton();
        panelRoleType = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        editReference = new javax.swing.JFormattedTextField();
        panelPaymentDetails = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panelPaymentHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentHeader.setAlignmentX(0.0F);
        panelPaymentHeader.setFocusable(false);
        panelPaymentHeader.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelPaymentHeader.setMinimumSize(new java.awt.Dimension(770, 200));
        panelPaymentHeader.setPreferredSize(new java.awt.Dimension(770, 200));
        panelPaymentHeader.setLayout(new java.awt.GridLayout(1, 0));

        jPanel13.setPreferredSize(new java.awt.Dimension(1002, 120));
        jPanel13.setLayout(new java.awt.GridLayout(1, 3));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Payment Date:");

        partyIdTextFieldFrom.setPreferredSize(new java.awt.Dimension(6, 25));
        partyIdTextFieldFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdTextFieldFromFocusLost(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("Fin Account Id:");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Customer Code:");

        txtPaymentDate.setValue(new Date());

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("To Company Code:");

        partyIdTextFieldTo.setPreferredSize(new java.awt.Dimension(6, 25));
        partyIdTextFieldTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdTextFieldToFocusLost(evt);
            }
        });

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Payment Amount:");

        panelFinAccount.setMinimumSize(new java.awt.Dimension(0, 25));
        panelFinAccount.setName(""); // NOI18N
        panelFinAccount.setLayout(new java.awt.GridLayout(1, 0));

        btnCompanyCode.setText("jButton1");
        btnCompanyCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompanyCodeActionPerformed(evt);
            }
        });

        btnCustomerCode.setText("jButton1");
        btnCustomerCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerCodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPaymentAmount)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(partyIdTextFieldTo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCompanyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                            .addComponent(partyIdTextFieldFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnCustomerCode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(panelFinAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(30, 30, 30))
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel17, jLabel18, jLabel20});

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {partyIdTextFieldFrom, partyIdTextFieldTo, txtPaymentDate});

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(partyIdTextFieldFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCustomerCode)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(partyIdTextFieldTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompanyCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFinAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jButton1)
                    .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {partyIdTextFieldFrom, partyIdTextFieldTo, txtPaymentDate});

        jPanel13.add(jPanel18);

        jLabel25.setText("Payment Type:");

        jLabel26.setText("Payment Method:");

        panelPaymentType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentType.setName(""); // NOI18N
        panelPaymentType.setLayout(new java.awt.GridLayout(1, 0));

        panelPaymentMethodType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentMethodType.setName(""); // NOI18N
        panelPaymentMethodType.setLayout(new java.awt.GridLayout(1, 0));

        panelUom.setMinimumSize(new java.awt.Dimension(0, 25));
        panelUom.setName(""); // NOI18N
        panelUom.setLayout(new java.awt.GridLayout(1, 0));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("Payment Id:");

        txtPaymentId.setPreferredSize(new java.awt.Dimension(6, 25));
        txtPaymentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaymentIdFocusLost(evt);
            }
        });

        jLabel23.setText("Status:");

        panelStatus.setMinimumSize(new java.awt.Dimension(0, 25));
        panelStatus.setName(""); // NOI18N
        panelStatus.setLayout(new java.awt.GridLayout(1, 0));

        jLabel30.setText("Currency:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPaymentMethodType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(225, 225, 225))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelPaymentMethodType, panelPaymentType, panelStatus, panelUom, txtPaymentId});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPaymentMethodType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel16);

        jPanel3.setPreferredSize(new java.awt.Dimension(201, 193));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Reference:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Comment:");

        editComment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                editCommentFocusLost(evt);
            }
        });

        jLabel29.setText("Overide GL Account:");

        panelOverideGlAccount.setMinimumSize(new java.awt.Dimension(0, 25));
        panelOverideGlAccount.setName(""); // NOI18N

        btnGlTypeId.setText("jButton1");
        btnGlTypeId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGlTypeIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOverideGlAccountLayout = new javax.swing.GroupLayout(panelOverideGlAccount);
        panelOverideGlAccount.setLayout(panelOverideGlAccountLayout);
        panelOverideGlAccountLayout.setHorizontalGroup(
            panelOverideGlAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOverideGlAccountLayout.createSequentialGroup()
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelOverideGlAccountLayout.setVerticalGroup(
            panelOverideGlAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOverideGlAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGlTypeId))
        );

        panelRoleType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelRoleType.setName(""); // NOI18N
        panelRoleType.setLayout(new java.awt.GridLayout(1, 0));

        jLabel27.setText("Role Type Id:");

        editReference.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editComment)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editReference, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelOverideGlAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel29))
                    .addComponent(panelOverideGlAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(editReference, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(editComment, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95))
        );

        jPanel13.add(jPanel3);

        panelPaymentHeader.add(jPanel13);

        add(panelPaymentHeader, java.awt.BorderLayout.PAGE_START);

        panelPaymentDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelPaymentDetailsLayout = new javax.swing.GroupLayout(panelPaymentDetails);
        panelPaymentDetails.setLayout(panelPaymentDetailsLayout);
        panelPaymentDetailsLayout.setHorizontalGroup(
            panelPaymentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
        );
        panelPaymentDetailsLayout.setVerticalGroup(
            panelPaymentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        add(panelPaymentDetails, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void partyIdTextFieldFromFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdTextFieldFromFocusLost
        // TODO add your handling code here:
//        setDialogFields();
//        notifyListeners(OrderMaxUtility.PARTY_CHANGED, "", partyIdTextField.getText());
//        Debug.logWarning("Party changed", module);

    }//GEN-LAST:event_partyIdTextFieldFromFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = getLocale(lang);
//        DatePicker dp = new DatePicker((ObservingTextField) txtPaymentDate, locale);
        //previously Selectd date
        //       java.util.Date selectedDate = dp.parseDate(txtPaymentDate.getText());
        //       dp.setSelectedDate(selectedDate);
        //       dp.start(txtPaymentDate);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPaymentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentIdFocusLost

    private void partyIdTextFieldToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdTextFieldToFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_partyIdTextFieldToFocusLost

    private void editCommentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editCommentFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_editCommentFocusLost

    private void btnCompanyCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompanyCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCompanyCodeActionPerformed

    private void btnCustomerCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomerCodeActionPerformed

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

        public void newItem() {
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
                partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), field.getText(), false);
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

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
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
        partyIdTextFieldFrom.setText(val);
    }

    public void setPaymentDate(String val) {
        txtPaymentDate.setText(val);
    }

    public BigDecimal getUnallocateAmount() {
        return BigDecimal.ZERO;
    }

    public String getContactDetails() {
        return "";
    }

    public String getPartyId() {
        return partyIdTextFieldFrom.getText();
    }

    public String getPaymentDate() {
        return txtPaymentDate.getText();
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

    public JButton getBtnHeaderPatryId() {
        return btnHeaderPatryId;
    }

    public JButton getBtnItemPatryId() {
        return null;//btnItemPatryId;
    }

    public void highlightLastRow(int row) {

    }

    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompanyCode;
    private javax.swing.JButton btnCustomerCode;
    private javax.swing.JButton btnGlTypeId;
    public javax.swing.JTextField editComment;
    public javax.swing.JFormattedTextField editReference;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelFinAccount;
    private javax.swing.JPanel panelOverideGlAccount;
    public javax.swing.JPanel panelPaymentDetails;
    private javax.swing.JPanel panelPaymentHeader;
    private javax.swing.JPanel panelPaymentMethodType;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelRoleType;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelUom;
    public javax.swing.JTextField partyIdTextFieldFrom;
    public javax.swing.JTextField partyIdTextFieldTo;
    private javax.swing.JTextField txtOverideGlTypeId;
    public javax.swing.JTextField txtPaymentAmount;
    private javax.swing.JFormattedTextField txtPaymentDate;
    public javax.swing.JTextField txtPaymentId;
    // End of variables declaration//GEN-END:variables
}
