/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class BillingAccountDetailPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    private ListAdapterListModel<BillingAccount> invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
    public JGenericComboBoxSelectionModel<Uom> currencyComboModel = null;
    public JComboBoxSelectionModel<String> descCondComboModel = null;
    private DatePickerEditPanel fromDate = null;
    private DatePickerEditPanel thruDate = null;
    private PartyPickerEditPanel partyIdPicker = null;
//    public JComboBoxSelectionModel<String> accountIdCondComboModel = null;
//    public JComboBoxSelectionModel<String> descCondComboModel = null;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public BillingAccountDetailPanel(ControllerOptions options) {
        initComponents();
//        List<PaymentType> findProductList = PaymentTypeSingleton.getValueList();
//        accountIdCondComboModel = new JComboBoxSelectionModel<String>(SelectionTypeSingleton.getValueList(), new StringListCellRenderer(false));
        currencyComboModel = new JGenericComboBoxSelectionModel<Uom>(UomCurrencySingleton.getValueList());        
        panelCurrencyUom.setLayout(new BorderLayout());
        panelCurrencyUom.add(BorderLayout.CENTER, currencyComboModel.jComboBox);
/*  
        descCondComboModel = new JComboBoxSelectionModel<String>(SelectionTypeSingleton.getValueList(), new StringListCellRenderer(false));
        panelDescCond.setLayout(new BorderLayout());
        panelDescCond.add(BorderLayout.CENTER, descCondComboModel.jComboBox);
*/
        fromDate = new DatePickerEditPanel();
//        fromDate.setSession(session);
        panelFromDate.setLayout(new BorderLayout());
        panelFromDate.add(BorderLayout.CENTER, fromDate);

        thruDate = new DatePickerEditPanel();
        //      thruDate.setSession(session);
        panelThruDate.setLayout(new BorderLayout());
        panelThruDate.add(BorderLayout.CENTER, thruDate);

        ControllerOptions partyOptions = new ControllerOptions();
        partyIdPicker = new PartyPickerEditPanel(partyOptions);
        panelBillToParty.setLayout(new BorderLayout());
        panelBillToParty.add(BorderLayout.CENTER, partyIdPicker);

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Billing Account");

//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        
    }

    private BillingAccountComposite billingAccountComposite = null;
    private BillingAccount billingAccount = null;

    public BillingAccountComposite getBillingAccountComposite() {
        return billingAccountComposite;
    }

    public void setBillingAccountComposite(BillingAccountComposite billingAccountComposite) {
        this.billingAccountComposite = billingAccountComposite;
        billingAccount = this.billingAccountComposite.getBillingAccount();
    }

    public void clearDialogFields() {
        txtBillingAccountId.setText("");
        txtAccoutLimit.setText("");//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
        textDescription.setText("");//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
        //      availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
//        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
        partyIdPicker.textIdField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
    }

    public void setDialogFields() {
        if (billingAccount != null) {
            txtBillingAccountId.setText(billingAccount.getbillingAccountId());
            txtAccoutLimit.setText(billingAccount.getaccountLimit().toString());//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
            textDescription.setText(billingAccount.getdescription());//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
            //      availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
//        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
            partyIdPicker.textIdField.setText(billingAccount.getexternalAccountId());//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));

            if (UtilValidate.isNotEmpty(billingAccount.getfromDate())) {
                fromDate.txtDate.setText(billingAccount.getfromDate().toString());
            }

            if (UtilValidate.isNotEmpty(billingAccount.getthruDate())) {
                thruDate.txtDate.setText(billingAccount.getthruDate().toString());
            }
        }
    }

    public void getDialogFields() {
        billingAccount.setbillingAccountId(txtBillingAccountId.getText());
        if (UtilValidate.isNotEmpty(currencyComboModel.getSelectedItem())) {
            billingAccount.setaccountCurrencyUomId(currencyComboModel.getSelectedItem().getuomId());//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
        }
        
        if (UtilValidate.isNotEmpty(txtAccoutLimit.getText())) {
            billingAccount.setaccountLimit(new BigDecimal(txtAccoutLimit.getText()));//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
        }
        
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
        billingAccount.setdescription(textDescription.getText());//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
        //      availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
//        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
        billingAccount.setexternalAccountId(partyIdPicker.textIdField.getText());//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
        try {
            billingAccount.setfromDate(fromDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(BillingAccountDetailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            billingAccount.setthruDate(thruDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(BillingAccountDetailPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        panelLookupInvoice = new CollapsiblePanel();
        jLabel1 = new javax.swing.JLabel();
        txtBillingAccountId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textDescription = new javax.swing.JTextField();
        txtAccoutLimit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        panelCurrencyUom = new javax.swing.JPanel();
        panelDescCond = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelFromDate = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAvailableBalance = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        panelBillToParty = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Billing Account", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Billing Account Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Description:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Account Limit:");

        panelCurrencyUom.setPreferredSize(new java.awt.Dimension(100, 24));

        javax.swing.GroupLayout panelCurrencyUomLayout = new javax.swing.GroupLayout(panelCurrencyUom);
        panelCurrencyUom.setLayout(panelCurrencyUomLayout);
        panelCurrencyUomLayout.setHorizontalGroup(
            panelCurrencyUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelCurrencyUomLayout.setVerticalGroup(
            panelCurrencyUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelDescCond.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelDescCondLayout = new javax.swing.GroupLayout(panelDescCond);
        panelDescCond.setLayout(panelDescCondLayout);
        panelDescCondLayout.setHorizontalGroup(
            panelDescCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelDescCondLayout.setVerticalGroup(
            panelDescCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelThruDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Thru Date:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelFromDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("From Date:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setText("Account Currency:");

        jLabel3.setText("Contact Mech Id:");

        jLabel5.setText("Available Balance:");

        btnUpdate.setText("Update");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Party Billed To:");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelBillToParty.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelBillToPartyLayout = new javax.swing.GroupLayout(panelBillToParty);
        panelBillToParty.setLayout(panelBillToPartyLayout);
        panelBillToPartyLayout.setHorizontalGroup(
            panelBillToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelBillToPartyLayout.setVerticalGroup(
            panelBillToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBillingAccountId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAccoutLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCurrencyUom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDescCond, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBillToParty, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAvailableBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate)))
        );
        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtBillingAccountId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtAccoutLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelCurrencyUom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(textDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelDescCond, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(panelBillToParty, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(txtAvailableBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(btnUpdate))
        );

        add(panelLookupInvoice, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel panelBillToParty;
    private javax.swing.JPanel panelCurrencyUom;
    private javax.swing.JPanel panelDescCond;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JTextField textDescription;
    private javax.swing.JTextField txtAccoutLimit;
    private javax.swing.JTextField txtAvailableBalance;
    public javax.swing.JTextField txtBillingAccountId;
    // End of variables declaration//GEN-END:variables
}
