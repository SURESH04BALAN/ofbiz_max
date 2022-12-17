/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.PaymentTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindPaymentListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    public GenericTableModelPanel<PaymentComposite, PaymentTableModel> tablePanel = null;
//    private ListAdapterListModel<PaymentComposite> invoiceCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    public JGenericComboBoxSelectionModel<PaymentType> paymentTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> paymentStatusItemComboModel = null;
//    PartyPickerEditPanel partyPickerEditPanel = null;    
    PartyPickerEditPanel panelFromPartyId;
    PartyPickerEditPanel panelToPartyId;    
    ControllerOptions options;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindPaymentListPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<PaymentType> paymentTypeeList = PaymentTypeSingleton.getValueList(options.getParentPaymentTypeId());
        PaymentType paymentType = new PaymentType();
        paymentType.setdescription("All");
        paymentType.setpaymentTypeId("ANY");
        paymentTypeeList.add(0, paymentType);

        paymentTypeComboModel = new JGenericComboBoxSelectionModel<PaymentType>(paymentTypeeList);
        panelPaymentType.setLayout(new BorderLayout());
        panelPaymentType.add(BorderLayout.CENTER, paymentTypeComboModel.jComboBox);
        paymentTypeComboModel.setSelectedItem(paymentType);

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("PMNT_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        paymentStatusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(findStatusItemList);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, paymentStatusItemComboModel.jComboBox);
        paymentStatusItemComboModel.setSelectedItem(statusItem);

        tablePanel = new GenericTableModelPanel<PaymentComposite, PaymentTableModel>(new PaymentTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Payment Search Option");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Payment List");
        panelFromPartyId = new PartyPickerEditPanel(options);
        panelFromParty.setLayout(new BorderLayout());
        panelFromParty.add(BorderLayout.CENTER, panelFromPartyId);

        panelToPartyId = new PartyPickerEditPanel(options);
        panelToParty.setLayout(new BorderLayout());
        panelToParty.add(BorderLayout.CENTER, panelToPartyId);
        
//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));

    }

    public void setReceiveInventoryList(ListAdapterListModel<PaymentComposite> orderListModel) {

        tablePanel.setListModel(orderListModel);
    }
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            panelFromPartyId.textIdField.setText(account.getParty().getpartyId());
        }

    }
    
    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();

        boolean showAll = true;
        PaymentType paymentType = (PaymentType) paymentTypeComboModel.getSelectedItem();
        if (paymentType != null && !"ANY".equals(paymentType.getpaymentTypeId())) {
            findOptionList.put("paymentTypeId", paymentType.getpaymentTypeId());
            showAll = false;
        }
        StatusItem statusType = (StatusItem) paymentStatusItemComboModel.getSelectedItem();
        if (statusType != null && !"ANY".equals(statusType.getstatusId())) {
            findOptionList.put("statusId", statusType.getstatusId());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(txtPaymentId.getText())) {
            findOptionList.put("paymentId", txtPaymentId.getText());
            showAll = false;
        }
        if (UtilValidate.isNotEmpty(panelFromPartyId.textIdField.getText())) {
            findOptionList.put("partyIdFrom", panelFromPartyId.textIdField.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(panelToPartyId.textIdField.getText())) {
            findOptionList.put("partyIdTo", panelToPartyId.textIdField.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(txtAmount.getText())) {
            findOptionList.put("amount", txtAmount.getText());
            showAll = false;
        }

        if (showAll == true) {
            findOptionList.put("showAll", "Y");
            findOptionList.put("noConditionFind", "Y");
        }

        findOptionList.put("lookupFlag", "Y");
        /*        
         if(txtComments.getText()!=null && txtComments.getText().isEmpty()==false){
         findOptionList.put("description", txtDescription.getText());            
         }        

       
         */
        //status type method

        return findOptionList;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Payment Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < PaymentTableModel.Columns.values().length; i++) {

            PaymentTableModel.Columns[] columns = PaymentTableModel.Columns.values();
            PaymentTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer());
            }
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
        java.awt.GridBagConstraints gridBagConstraints;

        panelLookupInvoice = new CollapsiblePanel();
        jLabel1 = new javax.swing.JLabel();
        cbCreated = new javax.swing.JCheckBox();
        cbProcessing = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbRejectedItems = new javax.swing.JCheckBox();
        btnFind = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        txtPaymentId = new javax.swing.JTextField();
        txtComments = new javax.swing.JTextField();
        txtAmount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelPaymentType = new javax.swing.JPanel();
        panelStatusId = new javax.swing.JPanel();
        panelFromParty = new javax.swing.JPanel();
        panelToParty = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.GridBagLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment Search Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Payment Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbCreated.setSelected(true);
        cbCreated.setText("Ignore Case");

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Comments:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Payment Type:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("From Party Id:");

        cbRejectedItems.setSelected(true);
        cbRejectedItems.setText("Ignore Case");

        btnFind.setText("Find");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Amount:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Reference No:");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("To Party Id:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Reference Number:");

        javax.swing.GroupLayout panelPaymentTypeLayout = new javax.swing.GroupLayout(panelPaymentType);
        panelPaymentType.setLayout(panelPaymentTypeLayout);
        panelPaymentTypeLayout.setHorizontalGroup(
            panelPaymentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        panelPaymentTypeLayout.setVerticalGroup(
            panelPaymentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelStatusIdLayout = new javax.swing.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFromPartyLayout = new javax.swing.GroupLayout(panelFromParty);
        panelFromParty.setLayout(panelFromPartyLayout);
        panelFromPartyLayout.setHorizontalGroup(
            panelFromPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        panelFromPartyLayout.setVerticalGroup(
            panelFromPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelToPartyLayout = new javax.swing.GroupLayout(panelToParty);
        panelToParty.setLayout(panelToPartyLayout);
        panelToPartyLayout.setHorizontalGroup(
            panelToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelToPartyLayout.setVerticalGroup(
            panelToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelStatusId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(cbProcessing))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtComments, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(cbCreated))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(panelFromParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jLabel8)
                .addGap(3, 3, 3)
                .addComponent(panelToParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(cbRejectedItems))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel10)
                .addGap(3, 3, 3)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addComponent(panelStatusId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProcessing))
                .addGap(2, 2, 2)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCreated))
                .addGap(2, 2, 2)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(2, 2, 2)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(panelFromParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(panelToParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbRejectedItems))
                .addGap(6, 6, 6)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(btnFind))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(panelLookupInvoice, gridBagConstraints);

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 984, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panelIResultList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbCreated;
    public javax.swing.JCheckBox cbProcessing;
    public javax.swing.JCheckBox cbRejectedItems;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JPanel panelFromParty;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelStatusId;
    private javax.swing.JPanel panelToParty;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtComments;
    private javax.swing.JTextField txtPaymentId;
    // End of variables declaration//GEN-END:variables
}
