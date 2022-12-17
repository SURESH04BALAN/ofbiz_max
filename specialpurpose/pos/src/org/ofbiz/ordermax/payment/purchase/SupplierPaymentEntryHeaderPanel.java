/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.purchase;

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
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import mvc.controller.LoadPaymentWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.composite.InvoiceItemCompositeList;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.FinAccount;
import org.ofbiz.ordermax.entity.PaymentMethod;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.paymentmethod.ChequePaymentPanel;
import org.ofbiz.ordermax.payment.CreditCardDetailForm;
import org.ofbiz.ordermax.payment.FinAccountSingleton;
import org.ofbiz.ordermax.payment.PaymentMethodSingleton;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.UOMSingleton;
import org.ofbiz.ordermax.payment.paymentmethod.EftPaymentPanel;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodBaseFactory;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodInterface;

/**
 *
 * @author administrator
 */
public class SupplierPaymentEntryHeaderPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = SupplierPaymentEntryHeaderPanel.class.getName();
    //private List<Map<String, Object>> partyMechList = null;
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
    protected EftPaymentPanel eftPaymentPanel = null;
    public PartyPickerEditPanel panelSupplierPartyIdPicker;
    public PartyPickerEditPanel panelPartyFromIdPicker;
    public DatePickerEditPanel paymentDatePickerEditPanel;
    protected Map<String, PaymentMethodInterface> paymentMethodPanels = new HashMap<String, PaymentMethodInterface>();
    //private JButton btnHeaderPatryId = null;
    private PaymentComposite paymentComposite = null;

    //payment type
    public JGenericComboBoxSelectionModel<PaymentMethodType> paymentMethodTypeComboBox = null;
    public JGenericComboBoxSelectionModel<PaymentType> paymentTypeComboBox = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboBox = null;
    public JGenericComboBoxSelectionModel<Uom> uomComboBox = null;
    public JGenericComboBoxSelectionModel<RoleType> roleTypeComboBox = null;
    public JGenericComboBoxSelectionModel<FinAccount> finAccountComboBox = null;
    PaymentMethodInterface paymentMethodInterface = null;
    JGenericComboBoxSelectionModel<PaymentMethod> paymentMethodComboBox;
//    private ListComboBoxModel<PaymentType> paymentTypeComboBoxModel = new ListComboBoxModel<PaymentType>();
//    private JComboBox<PaymentType> paymentTypeComboBox = new JComboBox<PaymentType>(paymentTypeComboBoxModel);
    //payment method
    //private ListComboBoxModel<PaymentMethod> paymentMethodTypeComboBoxModel = new ListComboBoxModel<PaymentMethod>();
    //private JComboBox<PaymentMethod> paymentMethodTypeComboBox = new JComboBox<PaymentMethod>(paymentMethodTypeComboBoxModel);
    //role type method
   /* private ListComboBoxModel<RoleType> roleTypeComboBoxModel = new ListComboBoxModel<RoleType>();
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
     */

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

        try {
            /*
             SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
             DateFormatter formatter = new DateFormatter(dateformat);
             dateformat.setLenient(false);
             formatter.setAllowsInvalid(false);
             formatter.setOverwriteMode(true);
            
             txtPaymentDate = new JFormattedTextField(formatter);
             txtPaymentDate.setValue(new Date());
             */
            paymentMethodTypeComboBox.selectionModel.addListSelectionListener(new ListSelectionListener() {
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
                                    changePaymentPanel(paymentMethodTypeComboBox.comboBoxModel.getElementAt(i));
                                    break;
                                }
                            }
                        }
                    }
                }
            });

            paymentMethodComboBox.selectionModel.addListSelectionListener(new ListSelectionListener() {
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
                                        Debug.logInfo("? getpaymentMethodTypeId " + paymentMethod.getpaymentMethodTypeId(), "module");
                                        if (valList.containsKey(paymentMethod.getpaymentMethodTypeId())) {
                                            Debug.logInfo("has getpaymentMethodTypeId " + paymentMethod.getpaymentMethodTypeId(), "module");
                                            GenericValue val = valList.get(paymentMethod.getpaymentMethodTypeId());
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

//            paymentMethodTypeComboBox.setSelectedItem(methodType);
//        initComponent1();
            /*paymentCardPanel = new JPanel(new CardLayout());
             chequeDetailForm = new ChequeDetailForm();
             creditCardDetailForm = new CreditCardDetailForm();
             eftPaymentPanel = new EftPaymentPanel();
             paymentCardPanel.add(chequeDetailForm, CHEQUE_TAB_INDEX);
             paymentCardPanel.add(creditCardDetailForm, CARD_TAB_INDEX);
             paymentCardPanel.add(cashPanel, CASH_TAB_INDEX);
             panelPaymentMethodDetail.setLayout(new BorderLayout());
             panelPaymentMethodDetail.add(paymentCardPanel, BorderLayout.CENTER);
             //        OrderMaxUtility.addAPanelToBorder(paymentCardPanel, panelPaymentMethodDetail);
             changeCard(CHEQUE_TAB_INDEX);  */
            //        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, delegator);
            //        facilityIdCombo = new GenericValueComboBox(comboFacility, genFacilityList, "Facility", "facilityId", "facilityName");
            //prty id lookup
            //        btnHeaderPatryId = new JButton("..");
            //        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
            //        cb.install(partyIdTextFieldTo);
            //        btnHeaderPatryId.addActionListener(new LookupActionListner(partyIdTextFieldTo, "partyIdTextField", "SUPPLIER", null));
            //        JToggleButton bntCompany = new JToggleButton("..");
            //        cb = new ComponentBorder(bntCompany);
            //        cb.install(partyIdTextFieldFrom);
            //        bntCompany.addActionListener(new LookupActionListner(payerPartyIdTextField, "partyIdTextField", "BILL_FROM_VENDOR", null));
            //        setupNewContactMap();
            //        txtPaymentAmount.setInputVerifier(new BigDecimalValidator(txtPaymentAmount));
            ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentHeader, "Payment Header");
            ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentDetails, "Payment Application");
        } catch (Exception ex) {
            Logger.getLogger(SupplierPaymentEntryHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    NumberFormatter numFormatter = null;

    public SupplierPaymentEntryHeaderPanel(XuiSession session, String paymentParentTypeId) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numFormat.setMaximumFractionDigits(2);

        numFormatter = new NumberFormatter(numFormat);
//        numFormatter.setMinimum(5.0);
        numFormatter.setMaximum(10000000.0);
        numFormatter.setAllowsInvalid(true);
        numFormatter.setOverwriteMode(true);

//JGenericComboBoxSelectionModel<PaymentType> 
        paymentMethodTypeComboBox = new JGenericComboBoxSelectionModel<PaymentMethodType>(panelPaymentMethodType, PaymentMethodTypeSingleton.getValueList());
        paymentTypeComboBox = new JGenericComboBoxSelectionModel<PaymentType>(panelPaymentType, PaymentTypeSingleton.getValueList(paymentParentTypeId));
        statusItemComboBox = new JGenericComboBoxSelectionModel<StatusItem>(panelStatus, StatusSingleton.getValueList("PMNT_STATUS"));
        uomComboBox = new JGenericComboBoxSelectionModel<Uom>(panelUom, UOMSingleton.getValueList("CURRENCY_MEASURE"));
        paymentMethodComboBox = new JGenericComboBoxSelectionModel<PaymentMethod>(panelPaymentMethod, PaymentMethodSingleton.getValueList());
//        roleTypeComboBox = new JGenericComboBoxSelectionModel<RoleType>(panelRoleType, RoleTypeSingleton.getValueList());
//        finAccountComboBox = new JGenericComboBoxSelectionModel<FinAccount>(panelFinAccount, FinAccountSingleton.getValueList());

//        paymentTypeComboBoxModel.setListModel(PaymentTypeSingleton.getValueListModal(paymentParentTypeId));
//        panelPaymentType.setLayout(new BorderLayout());
//        panelPaymentType.add(BorderLayout.CENTER, paymentTypeComboBox);
//        ListCellRenderer<PaymentType> paymentTypeRenderer = new PaymentTypeListCellRenderer(session.getComboBoxDisplayFormat());
//        paymentTypeComboBox.setRenderer(paymentTypeRenderer);
//        paymentMethodTypeComboBoxModel.setListModel(PaymentMethodSingleton.getValueListModal());
//        panelPaymentMethod.setLayout(new BorderLayout());
//        panelPaymentMethod.add(BorderLayout.CENTER, paymentMethodTypeComboBox);
//        ListCellRenderer<PaymentMethodType> paymentMethodTypeRenderer = new PaymentMethodListCellRenderer(showComboKeys);
//        paymentMethodTypeComboBox.setRenderer(paymentMethodTypeRenderer);

        /*        roleTypeComboBoxModel.setListModel(RoleTypeSingleton.getValueListModal());
         panelRoleType.setLayout(new BorderLayout());
         panelRoleType.add(BorderLayout.CENTER, roleTypeComboBox);
         RoleTypeListCellRenderer roleTypeRenderer = new RoleTypeListCellRenderer(showComboKeys);
         roleTypeComboBox.setRenderer(roleTypeRenderer);

         statusItemComboBoxModel.setListModel(StatusSingleton.getValueListModal("PMNT_STATUS"));
         panelStatus.setLayout(new BorderLayout());
         panelStatus.add(BorderLayout.CENTER, statusItemComboBox);
         StatusItemTypeListCellRenderer statusItemRenderer = new StatusItemTypeListCellRenderer();
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
         */
        ControllerOptions partyOptions = new ControllerOptions();
        panelSupplierPartyIdPicker = new PartyPickerEditPanel(partyOptions);
        panelSupplierPartyCode.setLayout(new BorderLayout());
        panelSupplierPartyCode.add(BorderLayout.CENTER, panelSupplierPartyIdPicker);

        partyOptions = new ControllerOptions();
        panelPartyFromIdPicker = new PartyPickerEditPanel(partyOptions, panelFromPartyCode);
        panelPartyFromIdPicker.textIdField.setInputVerifier(new PartyIdVerifyValidator(panelPartyFromIdPicker.textIdField));

        paymentDatePickerEditPanel = DatePickerEditPanel.addToPanel(panelPaymentDate);
//        panelSupplierPartyCode.setLayout(new BorderLayout());
//        panelSupplierPartyCode.add(BorderLayout.CENTER, panelSupplierPartyIdPicker);

        customInitComponent();
    }

    Map<String, Map<String, GenericValue>> mapList = null;

    public void changePaymentPanel(PaymentMethodType paymentMethodType) {

        if (paymentMethodType != null) {
            try {
                List<PaymentMethod> list = new ArrayList<PaymentMethod>();
                mapList = LoadPaymentWorker.getPartyPaymentMethodValueMaps(session.getDelegator(), panelPartyFromIdPicker.textIdField.getText(),
                        paymentMethodType.getpaymentMethodTypeId(), false);
                for (Map.Entry<String, Map<String, GenericValue>> mapIter : mapList.entrySet()) {
                    GenericValue val = mapIter.getValue().get("paymentMethod");
                    PaymentMethod payMeth = new PaymentMethod(val);
                    list.add(payMeth);
                }

                paymentMethodInterface = PaymentMethodBaseFactory.getReport(paymentMethodType.getpaymentMethodTypeId());
                panelPaymentMethodDetail.removeAll();
                panelPaymentMethodDetail.setLayout(new BorderLayout());
                panelPaymentMethodDetail.add(paymentMethodInterface.getPanel(), BorderLayout.CENTER);

                paymentMethodComboBox.setDataList(list);
                if (!list.isEmpty()) {
                    paymentMethodComboBox.setSelectedItem(list.get(0));
                }

            } catch (Exception ex) {
                Logger.getLogger(SupplierPaymentEntryHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    final public void getDialogFields() {
        try {

//            paymentComposite.getPartyPaymentTo().getParty().setpartyId(partyIdTextFieldFrom.getText());
            paymentComposite.getPayment().setpaymentId(txtPaymentId.getText());
            paymentComposite.getPayment().seteffectiveDate((java.sql.Timestamp) paymentDatePickerEditPanel.getTimeStamp());
            if (paymentMethodInterface != null) {
                paymentComposite.getPayment().setpaymentRefNum(paymentMethodInterface.getReference());
                paymentComposite.getPayment().setcomments(paymentMethodInterface.getComment());
            }

        } catch (Exception ex) {
            Debug.logError(ex, module);
        }
        try {
            paymentComposite.getPayment().setamount(new BigDecimal(txtPaymentAmount.getText()));
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
            if (paymentMethodTypeComboBox.getSelectedItem() != null) {
                paymentComposite.getPayment().setpaymentMethodTypeId(((PaymentMethodType) paymentMethodTypeComboBox.getSelectedItem()).getpaymentMethodTypeId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }

        try {
            if (uomComboBox.getSelectedItem() != null) {
                paymentComposite.getPayment().setcurrencyUomId(((Uom) uomComboBox.getSelectedItem()).getuomId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }

        /*try {
            if (finAccountComboBox.getSelectedItem() != null) {
                paymentComposite.getPayment().setfinAccountTransId(((FinAccount) finAccountComboBox.getSelectedItem()).getfinAccountId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }*/

    }

    public JTextField getSupplierPartyTextField() {
        return panelSupplierPartyIdPicker.textIdField;
    }

    public JTextField getPayerPartyTextField() {
        return panelPartyFromIdPicker.textIdField;
    }

    final public void clearDialogFields() {
        panelSupplierPartyIdPicker.textIdField.setText("");
        panelPartyFromIdPicker.textIdField.setText("");
        txtOverideGlTypeId.setText("");
        txtPaymentAmount.setText(BigDecimal.ZERO.toString());
        paymentDatePickerEditPanel.setCurrentDate();
        txtPaymentId.setText("");
//        editReference.setText("");
//        editComment.setText("");
    }

    final public void setDialogFields() {

        try {
//            Debug.logInfo("From: " + paymentComposite.getPartyPaymentFrom().getParty().getpartyId(), module);
//            Debug.logInfo("To: " + paymentComposite.getPartyPaymentTo().getParty().getpartyId(), module);

            panelSupplierPartyIdPicker.textIdField.setText(paymentComposite.getPayment().getpartyIdTo());
            panelPartyFromIdPicker.textIdField.setText(paymentComposite.getPayment().getpartyIdFrom());

            txtPaymentId.setText(paymentComposite.getPayment().getpaymentId());
            paymentDatePickerEditPanel.setDate(paymentComposite.getPayment().geteffectiveDate());
            if (paymentMethodInterface != null) {
                paymentMethodInterface.setReference(paymentComposite.getPayment().getpaymentRefNum());
                paymentMethodInterface.setComment(paymentComposite.getPayment().getcomments());
            }

        } catch (Exception ex) {
            Debug.logError(ex, module);
        }
        try {

            txtPaymentAmount.setText(paymentComposite.getPayment().getamount().toString());
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

        try {
            paymentMethodTypeComboBox.setSelectedItem(PaymentMethodTypeSingleton.getPaymentMethodType(paymentComposite.getPayment().getpaymentMethodTypeId()));
            PaymentMethodType methodType = PaymentMethodTypeSingleton.getPaymentMethodType(paymentComposite.getPayment().getpaymentMethodTypeId());
            changePaymentPanel(methodType);

        } catch (Exception ex) {
            Debug.logError(ex, module);
        }

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
        Debug.logError("txtPaymentId.getText(): " + txtPaymentId.getText(), module);
        panelPaymentHeader.updateUI();
        /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);

         try {
         roleTypeComboBoxModel.setSelectedItem(RoleTypeSingleton.getRoleType(paymentComposite.getPayment().getroleTypeIdTo()));
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }            /*        partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        
         try {
         paymentTypeComboBoxModel.setSelectedItem(PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()));
         } catch (Exception ex) {
         Debug.logError(ex, module);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JTextField();
        panelSupplierPartyCode = new javax.swing.JPanel();
        panelFromPartyCode = new javax.swing.JPanel();
        panelPaymentDate = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        panelPaymentType = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        panelUom = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtPaymentId = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtOverideGlTypeId = new javax.swing.JTextField();
        btnGlTypeId = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        panelPaymentMethodType = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        panelPaymentMethod = new javax.swing.JPanel();
        panelPaymentMethodDetail = new javax.swing.JPanel();
        panelPaymentDetails = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panelPaymentHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentHeader.setAlignmentX(0.0F);
        panelPaymentHeader.setFocusable(false);
        panelPaymentHeader.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelPaymentHeader.setMinimumSize(new java.awt.Dimension(770, 205));
        panelPaymentHeader.setPreferredSize(new java.awt.Dimension(770, 205));
        panelPaymentHeader.setLayout(new java.awt.GridLayout(1, 0));

        jPanel13.setPreferredSize(new java.awt.Dimension(1002, 120));
        jPanel13.setLayout(new java.awt.GridLayout(1, 3));

        jPanel18.setMinimumSize(new java.awt.Dimension(0, 205));
        jPanel18.setPreferredSize(new java.awt.Dimension(341, 205));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Payment Date:");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Supplier Code:");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("From Company Code:");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("Payment Amount:");

        javax.swing.GroupLayout panelSupplierPartyCodeLayout = new javax.swing.GroupLayout(panelSupplierPartyCode);
        panelSupplierPartyCode.setLayout(panelSupplierPartyCodeLayout);
        panelSupplierPartyCodeLayout.setHorizontalGroup(
            panelSupplierPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelSupplierPartyCodeLayout.setVerticalGroup(
            panelSupplierPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFromPartyCodeLayout = new javax.swing.GroupLayout(panelFromPartyCode);
        panelFromPartyCode.setLayout(panelFromPartyCodeLayout);
        panelFromPartyCodeLayout.setHorizontalGroup(
            panelFromPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelFromPartyCodeLayout.setVerticalGroup(
            panelFromPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

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

        jLabel28.setText("Payment Type:");

        panelPaymentType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentType.setName(""); // NOI18N
        panelPaymentType.setLayout(new java.awt.GridLayout(1, 0));

        jLabel24.setText("Status:");

        panelStatus.setMinimumSize(new java.awt.Dimension(0, 25));
        panelStatus.setName(""); // NOI18N
        panelStatus.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPaymentAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(panelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelPaymentType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelFromPartyCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelSupplierPartyCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelPaymentDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel18, jLabel20});

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelFromPartyCode, panelPaymentDate, panelPaymentType, panelStatus, panelSupplierPartyCode, txtPaymentAmount});

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(panelSupplierPartyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(panelFromPartyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(panelPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel18);

        panelUom.setMinimumSize(new java.awt.Dimension(0, 25));
        panelUom.setName(""); // NOI18N
        panelUom.setLayout(new java.awt.GridLayout(1, 0));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("Payment Id:");

        txtPaymentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaymentIdFocusLost(evt);
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
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtOverideGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGlTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("Payment Method Type:");

        panelPaymentMethodType.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentMethodType.setName(""); // NOI18N
        panelPaymentMethodType.setLayout(new java.awt.GridLayout(1, 0));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("Payment Method:");

        panelPaymentMethod.setMinimumSize(new java.awt.Dimension(0, 25));
        panelPaymentMethod.setName(""); // NOI18N
        panelPaymentMethod.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPaymentMethodType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelUom, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabel17)
                    .addComponent(panelPaymentMethodType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(panelPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelPaymentMethodType, panelUom});

        jPanel13.add(jPanel16);

        panelPaymentMethodDetail.setPreferredSize(new java.awt.Dimension(201, 193));

        javax.swing.GroupLayout panelPaymentMethodDetailLayout = new javax.swing.GroupLayout(panelPaymentMethodDetail);
        panelPaymentMethodDetail.setLayout(panelPaymentMethodDetailLayout);
        panelPaymentMethodDetailLayout.setHorizontalGroup(
            panelPaymentMethodDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );
        panelPaymentMethodDetailLayout.setVerticalGroup(
            panelPaymentMethodDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        jPanel13.add(panelPaymentMethodDetail);

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
            .addGap(0, 450, Short.MAX_VALUE)
        );

        add(panelPaymentDetails, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPaymentIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentIdFocusLost

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
                paymentMethodTypeComboBox.setDataList(PaymentMethodTypeSingleton.getValueList(/*field.getText()*/));
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
        panelSupplierPartyIdPicker.textIdField.setText(val);
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
        return panelSupplierPartyIdPicker.textIdField.getText();
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel panelFromPartyCode;
    private javax.swing.JPanel panelPaymentDate;
    public javax.swing.JPanel panelPaymentDetails;
    private javax.swing.JPanel panelPaymentHeader;
    private javax.swing.JPanel panelPaymentMethod;
    private javax.swing.JPanel panelPaymentMethodDetail;
    private javax.swing.JPanel panelPaymentMethodType;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelSupplierPartyCode;
    private javax.swing.JPanel panelUom;
    private javax.swing.JTextField txtOverideGlTypeId;
    public javax.swing.JTextField txtPaymentAmount;
    public javax.swing.JTextField txtPaymentId;
    // End of variables declaration//GEN-END:variables
}
