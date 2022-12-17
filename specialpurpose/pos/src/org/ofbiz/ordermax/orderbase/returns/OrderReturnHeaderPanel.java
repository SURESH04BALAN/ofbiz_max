/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.base.components.ReturnPickerEditPanel;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.ReturnHeader;
import org.ofbiz.ordermax.entity.ReturnHeaderType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.invoice.FindInvoiceListPanel;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class OrderReturnHeaderPanel extends javax.swing.JPanel {

//    private final JTextField txtProdIdTableTextField = new JTextField();
//    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    //  private ListAdapterListModel<BillingAccount> invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
    public JGenericComboBoxSelectionModel<ReturnHeaderType> returnHeaderTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
    public JGenericComboBoxSelectionModel<Facility> facilityComboModel = null;
    public JGenericComboBoxSelectionModel<Uom> currencyComboModel = null;
    public JComboBoxSelectionModel<String> needsInvComboModel = null;
    private JGenericComboBoxSelectionModel<PostalAddress> postalAddressListModel = null;
    public JGenericComboBoxSelectionModel<BillingAccount> billingAccountComboBox = null;
    private DatePickerEditPanel fromDate = null;
    public PartyPickerEditPanel panelFromPartyId = null;
    public PartyPickerEditPanel panelToPartyId = null;
    public ReturnPickerEditPanel returnPickerEditPanel = null;
//    public JComboBoxSelectionModel<String> returnHeaderTypeComboModel = null;
//    public JComboBoxSelectionModel<String> statusItemComboModel = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    ControllerOptions options = null;

    public OrderReturnHeaderPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
//        List<PaymentType> findProductList = PaymentTypeSingleton.getValueList();
        returnHeaderTypeComboModel = new JGenericComboBoxSelectionModel<ReturnHeaderType>(ReturnHeaderTypeSingleton.getValueList());
        panelReturnHeaderType.setLayout(new BorderLayout());
        panelReturnHeaderType.add(BorderLayout.CENTER, returnHeaderTypeComboModel.jComboBox);

        statusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(StatusSingleton.getValueList("ORDER_RETURN_STTS"));
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);

        facilityComboModel = new JGenericComboBoxSelectionModel<Facility>(FacilitySingleton.getValueList());
        panelDestinationFacilityId.setLayout(new BorderLayout());
        panelDestinationFacilityId.add(BorderLayout.CENTER, facilityComboModel.jComboBox);

        currencyComboModel = new JGenericComboBoxSelectionModel<Uom>(UomCurrencySingleton.getValueList());
        panelCurrency.setLayout(new BorderLayout());
        panelCurrency.add(BorderLayout.CENTER, currencyComboModel.jComboBox);

        needsInvComboModel = new JComboBoxSelectionModel<String>(YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        panelNeedsInventoryReserve.setLayout(new BorderLayout());
        panelNeedsInventoryReserve.add(BorderLayout.CENTER, needsInvComboModel.jComboBox);

        try {
            needsInvComboModel.setSelectedItem(YesNoConditionSelectSingleton.getString("Y"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<PostalAddress> listPost = new ArrayList<PostalAddress>();
        postalAddressListModel = new JGenericComboBoxSelectionModel<PostalAddress>(listPost);
        panelOriginContacMechId.setLayout(new BorderLayout());
        panelOriginContacMechId.add(BorderLayout.CENTER, postalAddressListModel.jComboBox);

        fromDate = new DatePickerEditPanel();
//        fromDate.setSession(session);
        panelEntryDate.setLayout(new BorderLayout());
        panelEntryDate.add(BorderLayout.CENTER, fromDate);

        returnPickerEditPanel = new ReturnPickerEditPanel(options);
        panelReturnId.setLayout(new BorderLayout());
        panelReturnId.add(BorderLayout.CENTER, returnPickerEditPanel);

        ControllerOptions fromOptions = new ControllerOptions(options);
        panelFromPartyId = new PartyPickerEditPanel(fromOptions);
        pFromPartyId.setLayout(new BorderLayout());
        pFromPartyId.add(BorderLayout.CENTER, panelFromPartyId);

        ControllerOptions toOptions = new ControllerOptions(options);
        panelToPartyId = new PartyPickerEditPanel(toOptions);
        paneToPartyId.setLayout(new BorderLayout());
        paneToPartyId.add(BorderLayout.CENTER, panelToPartyId);

        billingAccountComboBox = new JGenericComboBoxSelectionModel<BillingAccount>(panelBillingAccount);

        //      thruDate.setSession(session);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Return Header");

//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        
    }

    private ReturnHeaderComposite returnHeaderComposite = null;

    public ReturnHeaderComposite getReturnHeaderComposite() {
        return returnHeaderComposite;
    }
    ReturnHeader orderHeader = null;

    public void setReturnHeaderComposite(ReturnHeaderComposite returnHeaderComposite) {
        this.returnHeaderComposite = returnHeaderComposite;
        if (returnHeaderComposite != null) {
            orderHeader = returnHeaderComposite.getReturnHeader();
        }
    }

    public void clearDialogFields() {
        returnPickerEditPanel.textIdField.setText("");
//        txtFromPartyId.setText("");//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
//        txtToPartyId.setText("");//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
        //      availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
//        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
        txtPaymentMethodId.setText("");//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
    }
//    public JGenericComboBoxSelectionModel<ReturnHeaderType> returnHeaderTypeComboModel = null;
//    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
//    public JGenericComboBoxSelectionModel<Facility> facilityComboModel = null;    
//    public JGenericComboBoxSelectionModel<Uom> currencyComboModel = null;        

    public void setDialogFields() {
        if (UtilValidate.isNotEmpty(orderHeader.getreturnId())) {
            returnPickerEditPanel.textIdField.setText(orderHeader.getreturnId());
            returnPickerEditPanel.textIdField.setEditable(false);
        } else {
            returnPickerEditPanel.textIdField.setText("");
            returnPickerEditPanel.textIdField.setEditable(true);
        }

        try {
            returnHeaderTypeComboModel.setSelectedItem(ReturnHeaderTypeSingleton.getReturnHeaderType(orderHeader.getreturnHeaderTypeId()));
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(orderHeader.getstatusId()));
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (UtilValidate.isNotEmpty(orderHeader.getdestinationFacilityId())) {
                facilityComboModel.setSelectedItem(FacilitySingleton.getFacility(orderHeader.getdestinationFacilityId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            currencyComboModel.setSelectedItem(UomCurrencySingleton.getUom(orderHeader.getcurrencyUomId()));
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(orderHeader.getfromPartyId())) {
            panelFromPartyId.textIdField.setText(orderHeader.getfromPartyId());
        } else {
            panelFromPartyId.textIdField.setText("");
        }

        if (UtilValidate.isNotEmpty(orderHeader.gettoPartyId())) {
            panelToPartyId.textIdField.setText(orderHeader.gettoPartyId());
        } else {
            panelToPartyId.textIdField.setText("");
        }

        try {
            needsInvComboModel.setSelectedItem(orderHeader.getneedsInventoryReceive());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
//        txtToPartyId.setText(billingAccount.getdescription());//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
        //      availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
//        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
        txtPaymentMethodId.setText(orderHeader.getpaymentMethodId());//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
        fromDate.setDate(orderHeader.getentryDate());
        txtCreatedBy.setText(orderHeader.getcreatedBy());

    }

    public void getDialogFields() {
        try {
            orderHeader.setreturnHeaderTypeId(returnHeaderTypeComboModel.getSelectedItem().getreturnHeaderTypeId());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            orderHeader.setstatusId(statusItemComboModel.getSelectedItem().getstatusId());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(facilityComboModel.getSelectedItem())) {
                orderHeader.setdestinationFacilityId(facilityComboModel.getSelectedItem().getfacilityId());
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            orderHeader.setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        orderHeader.setfromPartyId(panelFromPartyId.textIdField.getText());
        orderHeader.settoPartyId(panelToPartyId.textIdField.getText());

        try {
            if (UtilValidate.isNotEmpty(billingAccountComboBox.getSelectedItem()) && UtilValidate.isNotEmpty(billingAccountComboBox.getSelectedItem().getbillingAccountId())) {
                orderHeader.setbillingAccountId(billingAccountComboBox.getSelectedItem().getbillingAccountId());
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }        
        try {
            orderHeader.setneedsInventoryReceive(needsInvComboModel.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        orderHeader.setpaymentMethodId(txtPaymentMethodId.getText());//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
        try {
            orderHeader.setentryDate(fromDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelLookupInvoice = new JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelReturnId = new javax.swing.JPanel();
        panelOriginContacMechId = new javax.swing.JPanel();
        panelCurrency = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelEntryDate = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPaymentMethodId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFinAccountId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelReturnHeaderType = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNewCreditCard = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSupplierRMAId = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        panelDestinationFacilityId = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        panelStatusId = new javax.swing.JPanel();
        paneToPartyId = new javax.swing.JPanel();
        txtCreatedBy = new javax.swing.JTextField();
        pFromPartyId = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        panelNeedsInventoryReserve = new javax.swing.JPanel();
        panelBillingAccount = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        panelReturnItems = new javax.swing.JPanel();
        panelReturnHistory = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        btnNewReturnOrderAction = new javax.swing.JToggleButton();
        btnSaveOrder = new javax.swing.JButton();
        btnOrderReturn = new javax.swing.JToggleButton();
        btnPrintInventorySticker = new javax.swing.JToggleButton();
        btnCopyFrom1 = new javax.swing.JButton();
        btnCopyTo = new javax.swing.JToggleButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Return Header", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("From Party Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Return HeaderType Id:");

        panelReturnId.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelReturnIdLayout = new javax.swing.GroupLayout(panelReturnId);
        panelReturnId.setLayout(panelReturnIdLayout);
        panelReturnIdLayout.setHorizontalGroup(
            panelReturnIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        panelReturnIdLayout.setVerticalGroup(
            panelReturnIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelOriginContacMechId.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelOriginContacMechIdLayout = new javax.swing.GroupLayout(panelOriginContacMechId);
        panelOriginContacMechId.setLayout(panelOriginContacMechIdLayout);
        panelOriginContacMechIdLayout.setHorizontalGroup(
            panelOriginContacMechIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        panelOriginContacMechIdLayout.setVerticalGroup(
            panelOriginContacMechIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        panelCurrency.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelCurrencyLayout = new javax.swing.GroupLayout(panelCurrency);
        panelCurrency.setLayout(panelCurrencyLayout);
        panelCurrencyLayout.setHorizontalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        panelCurrencyLayout.setVerticalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Currency:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelEntryDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelEntryDateLayout = new javax.swing.GroupLayout(panelEntryDate);
        panelEntryDate.setLayout(panelEntryDateLayout);
        panelEntryDateLayout.setHorizontalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelEntryDateLayout.setVerticalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Entry Date:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setText("Return Id:");

        jLabel3.setText("Origin Contact Mech Id:");

        jLabel4.setText("Payment Method Id:");

        jLabel5.setText("Fin Account Id:");

        jLabel6.setText("Created By:");

        panelReturnHeaderType.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelReturnHeaderTypeLayout = new javax.swing.GroupLayout(panelReturnHeaderType);
        panelReturnHeaderType.setLayout(panelReturnHeaderTypeLayout);
        panelReturnHeaderTypeLayout.setHorizontalGroup(
            panelReturnHeaderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelReturnHeaderTypeLayout.setVerticalGroup(
            panelReturnHeaderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel8.setText("To Party Id:");

        jLabel11.setText("New Credit Card:");

        jLabel12.setText("Supplier Rma Id:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Destination Facility Id:");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelDestinationFacilityId.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelDestinationFacilityIdLayout = new javax.swing.GroupLayout(panelDestinationFacilityId);
        panelDestinationFacilityId.setLayout(panelDestinationFacilityIdLayout);
        panelDestinationFacilityIdLayout.setHorizontalGroup(
            panelDestinationFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        panelDestinationFacilityIdLayout.setVerticalGroup(
            panelDestinationFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Status Id:");
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelStatusId.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelStatusIdLayout = new javax.swing.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        paneToPartyId.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout paneToPartyIdLayout = new javax.swing.GroupLayout(paneToPartyId);
        paneToPartyId.setLayout(paneToPartyIdLayout);
        paneToPartyIdLayout.setHorizontalGroup(
            paneToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        paneToPartyIdLayout.setVerticalGroup(
            paneToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        pFromPartyId.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout pFromPartyIdLayout = new javax.swing.GroupLayout(pFromPartyId);
        pFromPartyId.setLayout(pFromPartyIdLayout);
        pFromPartyIdLayout.setHorizontalGroup(
            pFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        pFromPartyIdLayout.setVerticalGroup(
            pFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Needs Inventory Receive:");
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelNeedsInventoryReserve.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelNeedsInventoryReserveLayout = new javax.swing.GroupLayout(panelNeedsInventoryReserve);
        panelNeedsInventoryReserve.setLayout(panelNeedsInventoryReserveLayout);
        panelNeedsInventoryReserveLayout.setHorizontalGroup(
            panelNeedsInventoryReserveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        panelNeedsInventoryReserveLayout.setVerticalGroup(
            panelNeedsInventoryReserveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        panelBillingAccount.setMinimumSize(new java.awt.Dimension(0, 25));
        panelBillingAccount.setName(""); // NOI18N
        panelBillingAccount.setLayout(new java.awt.GridLayout(1, 0));

        jLabel25.setText("Billing Account Id:");

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel25)
                    .addComponent(jLabel19)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFinAccountId)
                    .addComponent(pFromPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(paneToPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(panelReturnHeaderType, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(txtPaymentMethodId, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(panelStatusId, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(panelBillingAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEntryDate, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelReturnId, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCreatedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDestinationFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewCreditCard, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelNeedsInventoryReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSupplierRMAId, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelOriginContacMechId, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelCurrency, panelDestinationFacilityId, panelNeedsInventoryReserve, panelOriginContacMechId, panelReturnId, txtCreatedBy, txtNewCreditCard, txtSupplierRMAId});

        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelReturnHeaderType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(panelReturnId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelStatusId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCreatedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(pFromPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDestinationFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(paneToPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtPaymentMethodId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtNewCreditCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtFinAccountId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelNeedsInventoryReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtSupplierRMAId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(panelBillingAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(panelEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(panelOriginContacMechId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Return Header", panelLookupInvoice);

        javax.swing.GroupLayout panelReturnItemsLayout = new javax.swing.GroupLayout(panelReturnItems);
        panelReturnItems.setLayout(panelReturnItemsLayout);
        panelReturnItemsLayout.setHorizontalGroup(
            panelReturnItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1211, Short.MAX_VALUE)
        );
        panelReturnItemsLayout.setVerticalGroup(
            panelReturnItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Return Items", panelReturnItems);

        javax.swing.GroupLayout panelReturnHistoryLayout = new javax.swing.GroupLayout(panelReturnHistory);
        panelReturnHistory.setLayout(panelReturnHistoryLayout);
        panelReturnHistoryLayout.setHorizontalGroup(
            panelReturnHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1211, Short.MAX_VALUE)
        );
        panelReturnHistoryLayout.setVerticalGroup(
            panelReturnHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Return History", panelReturnHistory);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panelButton.setPreferredSize(new java.awt.Dimension(911, 50));

        btnNewReturnOrderAction.setText("NEW RETURN");
        btnNewReturnOrderAction.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btnSaveOrder.setText("SAVE ORDER");

        btnOrderReturn.setText("STATUS ACTION");
        btnOrderReturn.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btnPrintInventorySticker.setText("PRINT");
        btnPrintInventorySticker.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btnCopyFrom1.setText("COPY FROM");

        btnCopyTo.setText("COPY TO");
        btnCopyTo.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btnOk.setText("OK");
        btnOk.setMinimumSize(new java.awt.Dimension(47, 15));
        btnOk.setPreferredSize(new java.awt.Dimension(107, 20));

        btnCancel.setText("CANCEL");
        btnCancel.setMinimumSize(new java.awt.Dimension(47, 15));
        btnCancel.setPreferredSize(new java.awt.Dimension(107, 20));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewReturnOrderAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOrderReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintInventorySticker, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCopyFrom1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCopyTo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnSaveOrder)
                    .addComponent(btnCopyFrom1)
                    .addComponent(btnCopyTo)
                    .addComponent(btnPrintInventorySticker)
                    .addComponent(btnNewReturnOrderAction)
                    .addComponent(btnOrderReturn)
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCopyFrom1, btnCopyTo, btnNewReturnOrderAction, btnOrderReturn, btnPrintInventorySticker});

        add(panelButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    public Map<String, Object> getOptions() {
        List<String> stausList = FastList.newInstance();
        stausList.add("ORDER_APPROVED");
        stausList.add("ORDER_COMPLETED");
        stausList.add("ORDER_HOLD");
        stausList.add("ORDER_PROCESSING");
        /*        if (panel.cbApproved.isSelected() == true) {
  
         }
         if (panel.cbCanceled.isSelected() == true) {
         stausList.add("ORDER_CANCELLED");
         }
         if (panel.cbCompleted.isSelected() == true) {

         }
         if (panel.cbCreated.isSelected() == true) {
         stausList.add("ORDER_CREATED");
         }
         if (panel.cbHeld.isSelected() == true) {            
         }
         if (panel.cbProcessing.isSelected() == true) {

         }
         if (panel.cbRejected.isSelected() == true) {
         stausList.add("ORDER_REJECTED");
         }
         */
        String partyId = panelToPartyId.textIdField.getText();
        List<String> orderTypeId = FastList.newInstance();
        List<String> roleList = null;
        if (options.isSalesReturn()) {
            orderTypeId.add("SALES_ORDER");
            partyId = panelFromPartyId.textIdField.getText();
            roleList = UtilMisc.toList("BILL_TO_CUSTOMER");
        }

        if (options.isPurchaseReturn()) {
            orderTypeId.add("PURCHASE_ORDER");
            partyId = panelToPartyId.textIdField.getText();
            roleList = UtilMisc.toList("BILL_FROM_VENDOR");
        }
        Map<String, Object> svcCtx = FastMap.newInstance();

//        if (panel.cbPartiallyReceived.isSelected() == true) {
        svcCtx.put("filterPartiallyReceivedPOs", "Y");
//        }

//        if (panel.cbOpenPastTheirETA.isSelected() == true) {
        svcCtx.put("filterPOsOpenPastTheirETA", "Y");
//        }

//        if (panel.cbRejectedItems.isSelected() == true) {
        svcCtx.put("filterPOsWithRejectedItems", "Y");
//        }

        String filterInventoryProblems = "N";
//        if (panel.cbInventoryProblem.isSelected() == true) {
//            filterInventoryProblems = "Y";
//        }

        Locale locale = Locale.getDefault();

        svcCtx.put("userLogin", XuiContainer.getSession().getUserLogin());
        //svcCtx.put("orderId", order.getOrderHeader().getOrderId());
        svcCtx.put("roleTypeId", roleList);
        svcCtx.put("partyId", partyId);
        svcCtx.put("locale", locale);
//                svcCtx.put("showAll", "Y");
        svcCtx.put("viewIndex", new Integer(1));
        svcCtx.put("viewSize", new Integer(100000));
        svcCtx.put("orderStatusId", stausList);
        svcCtx.put("orderTypeId", orderTypeId);
        svcCtx.put("filterInventoryProblems", filterInventoryProblems);
        /*              
         Map<String, Object> svcRes = null;
         try {
         LocalDispatcher dispatcher = session.getDispatcher();
         svcRes = dispatcher.runSync("findOrders", svcCtx);

         if (svcRes.containsKey("orderList")) {
         List<GenericValue> orderList = (List<GenericValue>) svcRes.get("orderList");
         }

         //                    for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
         //                        Debug.logInfo("Key : " + entryDept.getEntityId() + " Value: " + entryDept.getEntityValue().toString(), module);
         //                    }
         } catch (GenericServiceException ex1) {
         Debug.logError(ex1, module);
         try {
         //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());
         throw new GeneralException(ServiceUtil.getErrorMessage(svcRes));
         } catch (GeneralException ex) {
         Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         */
        return svcCtx;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnCopyFrom1;
    public javax.swing.JToggleButton btnCopyTo;
    public javax.swing.JToggleButton btnNewReturnOrderAction;
    public javax.swing.JButton btnOk;
    public javax.swing.JToggleButton btnOrderReturn;
    public javax.swing.JToggleButton btnPrintInventorySticker;
    public javax.swing.JButton btnSaveOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pFromPartyId;
    private javax.swing.JPanel paneToPartyId;
    private javax.swing.JPanel panelBillingAccount;
    public javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelCurrency;
    private javax.swing.JPanel panelDestinationFacilityId;
    private javax.swing.JPanel panelEntryDate;
    private javax.swing.JPanel panelLookupInvoice;
    public javax.swing.JPanel panelNeedsInventoryReserve;
    private javax.swing.JPanel panelOriginContacMechId;
    private javax.swing.JPanel panelReturnHeaderType;
    public javax.swing.JPanel panelReturnHistory;
    private javax.swing.JPanel panelReturnId;
    public javax.swing.JPanel panelReturnItems;
    private javax.swing.JPanel panelStatusId;
    private javax.swing.JTextField txtCreatedBy;
    private javax.swing.JTextField txtFinAccountId;
    private javax.swing.JTextField txtNewCreditCard;
    private javax.swing.JTextField txtPaymentMethodId;
    private javax.swing.JTextField txtSupplierRMAId;
    // End of variables declaration//GEN-END:variables
}
