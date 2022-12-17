/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import org.ofbiz.ordermax.payment.paymentmethod.ChequePaymentPanel;
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
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.party.contact.ContactMechWorker;
import java.awt.CardLayout;
import static java.lang.String.format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.PaymentMethodListCellRenderer;
import mvc.model.list.PaymentTypeListCellRenderer;
import mvc.model.list.RoleTypeListCellRenderer;
import mvc.model.list.StatusItemTypeListCellRenderer;
import mvc.model.list.UomListCellRenderer;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.StatusSingleton;

/**
 *
 * @author administrator
 */
public class PaymentEntryPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = PaymentEntryPanel.class.getName();
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

    //role type method
    private ListComboBoxModel<StatusItem> statusItemComboBoxModel = new ListComboBoxModel<StatusItem>();
    private JComboBox<StatusItem> statusItemComboBox = new JComboBox<StatusItem>(statusItemComboBoxModel);

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

        OrderMaxUtility.addAPanelToBorder(paymentCardPanel, panelPaymentDetail);
        changeCard(CASH_TAB_INDEX);

//        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, delegator);
//        facilityIdCombo = new GenericValueComboBox(comboFacility, genFacilityList, "Facility", "facilityId", "facilityName");
        //prty id lookup
        btnHeaderPatryId = new JButton("..");
        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
        cb.install(partyIdTextField);
        //btnHeaderPatryId.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField", "BILL_FROM_VENDOR", null));

        //order header lookup
        JButton button1 = new JButton("..");
//        button1.setPreferredSize(new Dimension(10, partyIdTextField.getBounds().height));
        cb = new ComponentBorder(button1);
        cb.install(txtBankCode);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        JButton btnGlTypeId = new JButton("..");
        cb = new ComponentBorder(btnGlTypeId);
        cb.install(txtOverideGlTypeId);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
//        setupNewContactMap();
//        partyIdTextField.setInputVerifier(new PartyIdVerifyValidator(partyIdTextField));
        txtPaymentAmount.setInputVerifier(new BigDecimalValidator(txtPaymentAmount));

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentHeader, "Payment Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentDetails, "Payment Details");
        Debug.logInfo("PaymentEntryPanel: ", module);
    }
    NumberFormatter numFormatter = null;

    public PaymentEntryPanel(XuiSession session) {
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
        return partyIdTextField;
    }

    final public void setDialogFields() {

        /*        if (facilityIdCombo != null) {
         facilityIdCombo.setSelectedItemId((String) session.getAttribute("facilityId"));
         }
         */
        try {

            partyIdTextField.setText(paymentComposite.getPayment().getpartyIdFrom());
            partyIdToTextField.setText(paymentComposite.getPayment().getpartyIdTo());
            txtPaymentId.setText(paymentComposite.getPayment().getpaymentId());
            txtPaymentDate.setValue(paymentComposite.getPayment().geteffectiveDate());
        } catch (Exception ex) {
            Debug.logError(ex, "module");
        }
        try {

            txtPaymentAmount.setValue(paymentComposite.getPayment().getamount());
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
        }            /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);

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
        txtBankCode = new javax.swing.JTextField();
        partyIdTextField = new javax.swing.JTextField();
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
        partyIdToTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JFormattedTextField(numFormatter);
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
        editCurrOutstanding = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        edit60Days = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        edit90Days = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        edit30Days = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        panelOverideGlAccount = new javax.swing.JPanel();
        txtOverideGlTypeId = new javax.swing.JTextField();
        panelRoleType = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        editTotalBalance = new javax.swing.JFormattedTextField();
        panelPaymentDetails = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtSettlementDiscount = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtChequeAmount = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCashAmount = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtOtherEft = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCardAmount = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        panelPaymentDetail = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtGeneralLedgerSet = new javax.swing.JTextField();
        txtBankAccount = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtReceivablesAccount = new javax.swing.JTextField();
        txtDiscountAccount = new javax.swing.JTextField();

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

        txtBankCode.setPreferredSize(new java.awt.Dimension(6, 25));

        partyIdTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        partyIdTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdTextFieldFocusLost(evt);
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

        partyIdToTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        partyIdToTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdToTextFieldFocusLost(evt);
            }
        });

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Payment Amount:");

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
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(partyIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(partyIdToTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBankCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(txtPaymentDate, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                .addGap(37, 37, 37))
                            .addComponent(txtPaymentAmount))))
                .addGap(30, 30, 30))
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel17, jLabel18, jLabel20});

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(partyIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel20))
                    .addComponent(partyIdToTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBankCode, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jButton1)
                    .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {partyIdTextField, partyIdToTextField, txtBankCode, txtPaymentAmount, txtPaymentDate});

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
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPaymentMethodType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel16);

        jPanel3.setPreferredSize(new java.awt.Dimension(201, 193));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Total:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Current:");

        editCurrOutstanding.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                editCurrOutstandingFocusLost(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("60 Days:");

        edit60Days.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                edit60DaysFocusLost(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("90 Days +:");

        edit90Days.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                edit90DaysFocusLost(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("30 Days:");

        edit30Days.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                edit30DaysFocusLost(evt);
            }
        });

        jLabel29.setText("Overide GL Account:");

        panelOverideGlAccount.setMinimumSize(new java.awt.Dimension(0, 25));
        panelOverideGlAccount.setName(""); // NOI18N
        panelOverideGlAccount.setLayout(new java.awt.GridLayout(1, 0));
        panelOverideGlAccount.add(txtOverideGlTypeId);

        panelRoleType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelRoleType.setName(""); // NOI18N
        panelRoleType.setLayout(new java.awt.GridLayout(1, 0));

        jLabel27.setText("Role Type Id:");

        editTotalBalance.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editCurrOutstanding, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(edit60Days)
                    .addComponent(edit90Days)
                    .addComponent(edit30Days)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(panelOverideGlAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editTotalBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel6, jLabel7, jLabel8});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel29))
                    .addComponent(panelOverideGlAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(editTotalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(editCurrOutstanding, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(edit30Days, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(edit60Days, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(edit90Days, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {edit30Days, edit60Days, edit90Days, editCurrOutstanding});

        jPanel13.add(jPanel3);

        panelPaymentHeader.add(jPanel13);

        add(panelPaymentHeader, java.awt.BorderLayout.PAGE_START);

        panelPaymentDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentDetails.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setPreferredSize(new java.awt.Dimension(192, 80));

        jPanel4.setPreferredSize(new java.awt.Dimension(388, 25));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel1.setPreferredSize(new java.awt.Dimension(388, 25));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("Settlement Disc:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtSettlementDiscount)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtSettlementDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setPreferredSize(new java.awt.Dimension(388, 25));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel16.setText("Cheque Amount:");

        txtChequeAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtChequeAmountFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtChequeAmount)
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtChequeAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setPreferredSize(new java.awt.Dimension(388, 25));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Cash Amount:");

        txtCashAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCashAmountFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtCashAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtCashAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setPreferredSize(new java.awt.Dimension(388, 25));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Other/EFT:");

        txtOtherEft.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOtherEftFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtOtherEft)
                .addGap(27, 27, 27))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtOtherEft, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel17.setPreferredSize(new java.awt.Dimension(388, 25));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("Card Amount:");

        txtCardAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCardAmountFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(txtCardAmount)
                .addGap(27, 27, 27))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtCardAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setPreferredSize(new java.awt.Dimension(368, 100));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel24.setText("Comments:");
        jLabel24.setPreferredSize(new java.awt.Dimension(54, 25));
        jPanel15.add(jLabel24, java.awt.BorderLayout.PAGE_START);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setMargin(new java.awt.Insets(20, 10, 2, 2));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel15.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        panelPaymentDetails.add(jPanel10);

        panelPaymentDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelPaymentDetailLayout = new javax.swing.GroupLayout(panelPaymentDetail);
        panelPaymentDetail.setLayout(panelPaymentDetailLayout);
        panelPaymentDetailLayout.setHorizontalGroup(
            panelPaymentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        panelPaymentDetailLayout.setVerticalGroup(
            panelPaymentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("General Ledger Set");

        jLabel4.setText("Bank Account");

        jLabel9.setText("Receivable Account");

        jLabel12.setText("Discount Account");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtBankAccount, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addComponent(txtGeneralLedgerSet, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtReceivablesAccount, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(txtDiscountAccount))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGeneralLedgerSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReceivablesAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBankAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiscountAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPaymentDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(panelPaymentDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelPaymentDetails.add(jPanel11);

        add(panelPaymentDetails, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void partyIdTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdTextFieldFocusLost
        // TODO add your handling code here:
        setDialogFields();
        notifyListeners(OrderMaxUtility.PARTY_CHANGED, "", partyIdTextField.getText());
        Debug.logWarning("Party changed", module);

    }//GEN-LAST:event_partyIdTextFieldFocusLost

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

    private void txtChequeAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChequeAmountFocusGained
        changeCard(CHEQUE_TAB_INDEX);
    }//GEN-LAST:event_txtChequeAmountFocusGained

    private void txtCashAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashAmountFocusGained
        changeCard(CASH_TAB_INDEX);
    }//GEN-LAST:event_txtCashAmountFocusGained

    private void txtOtherEftFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOtherEftFocusGained
        changeCard(CASH_TAB_INDEX);
    }//GEN-LAST:event_txtOtherEftFocusGained

    private void txtCardAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCardAmountFocusGained
        changeCard(CARD_TAB_INDEX);
    }//GEN-LAST:event_txtCardAmountFocusGained

    private void txtPaymentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentIdFocusLost

    private void partyIdToTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdToTextFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_partyIdToTextFieldFocusLost

    private void edit30DaysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_edit30DaysFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_edit30DaysFocusLost

    private void edit90DaysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_edit90DaysFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_edit90DaysFocusLost

    private void edit60DaysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_edit60DaysFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_edit60DaysFocusLost

    private void editCurrOutstandingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editCurrOutstandingFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_editCurrOutstandingFocusLost
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

    class BigDecimalValidator extends InputVerifier {

        private JTextField field;
        private double value;
        private static final String ZERO = "0.0";
        private Border border = new LineBorder(Color.red);

        public BigDecimalValidator(JTextField field) {
            this.field = field;
            border = field.getBorder();
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            if (verify(input)) {
                return true;
            } else {
                field.selectAll();
                return false;
            }
        }

        @Override
        public boolean verify(JComponent input) {
            BigDecimal val = BigDecimal.ZERO;
            try {
                double v = new BigDecimal(field.getText()).doubleValue();
            } catch (NumberFormatException e) {
                field.setBorder(red);
                return false;
            }
            field.setBorder(border);
            return true;
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
            BigDecimal val = new BigDecimal(txtSettlementDiscount.getText());
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
        partyIdTextField.setText(val);
    }

    public void setBankDetail(String val) {
        txtBankCode.setText(val);
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
        return partyIdTextField.getText();
    }

    public String getBankDetail() {
        return txtBankCode.getText();
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
        return getAmountFromText(txtPaymentAmount.getText());
    }

    public BigDecimal getDiscountTotal() {
        return getAmountFromText(txtSettlementDiscount.getText());
    }

    public BigDecimal getChequeTotal() {
        return getAmountFromText(txtChequeAmount.getText());
    }

    public BigDecimal getCashTotal() {
        return getAmountFromText(txtCashAmount.getText());
    }

    public BigDecimal getCardTotal() {
        return getAmountFromText(txtCardAmount.getText());
    }

    public BigDecimal getEftTotal() {
        return getAmountFromText(txtOtherEft.getText());
    }

    public BigDecimal getTotalDiffPayments() {
        return getChequeTotal().add(getCashTotal().add(getCardTotal().add(getEftTotal())));
    }

    public BigDecimal getTotalPaymentsIncDiscount() {
        return getPaymentTotal().add(getDiscountTotal());
    }
    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField edit30Days;
    public javax.swing.JTextField edit60Days;
    public javax.swing.JTextField edit90Days;
    public javax.swing.JTextField editCurrOutstanding;
    public javax.swing.JFormattedTextField editTotalBalance;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel panelOverideGlAccount;
    private javax.swing.JPanel panelPaymentDetail;
    private javax.swing.JPanel panelPaymentDetails;
    private javax.swing.JPanel panelPaymentHeader;
    private javax.swing.JPanel panelPaymentMethodType;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelRoleType;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelUom;
    public javax.swing.JTextField partyIdTextField;
    public javax.swing.JTextField partyIdToTextField;
    private javax.swing.JTextField txtBankAccount;
    private javax.swing.JTextField txtBankCode;
    public javax.swing.JTextField txtCardAmount;
    public javax.swing.JTextField txtCashAmount;
    public javax.swing.JTextField txtChequeAmount;
    private javax.swing.JTextField txtDiscountAccount;
    private javax.swing.JTextField txtGeneralLedgerSet;
    public javax.swing.JTextField txtOtherEft;
    private javax.swing.JTextField txtOverideGlTypeId;
    private javax.swing.JFormattedTextField txtPaymentAmount;
    private javax.swing.JFormattedTextField txtPaymentDate;
    public javax.swing.JTextField txtPaymentId;
    private javax.swing.JTextField txtReceivablesAccount;
    private javax.swing.JTextField txtSettlementDiscount;
    // End of variables declaration//GEN-END:variables
}
