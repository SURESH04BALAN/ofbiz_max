/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.table.InvoiceCompositeTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.base.components.GenericDateSelectionPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindInvoiceListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

//    private InvoiceCompositeTableModel paymentTableModel = new InvoiceCompositeTableModel();
//    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    public GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel> tablePanel = null;
    //payment type
    //public JGenericComboBoxSelectionModel<InvoiceType> invoiceTypeComboBoxModel = null;
    //public JGenericComboBoxSelectionModel<StatusItem> statusItemComboBoxModel = null;

    //status type method
    //private ListComboBoxModel<String> invoiceIdCondComboBoxModel = new ListComboBoxModel<String>();
    //private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(invoiceIdCondComboBoxModel);

    //status type method
    //private ListComboBoxModel<String> descCondComboBoxModel = new ListComboBoxModel<String>();
    //private ListComboBoxModel<String> refCondComboBoxModel = new ListComboBoxModel<String>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(descCondComboBoxModel);
    //PartyPickerEditPanel panelFromPartyPickerId;
    //PartyPickerEditPanel panelToPartyPickerId;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();    
    ControllerOptions options = null;
    boolean showComboKeys = true;
    PartyPickerEditPanel partyPickerEditPanel = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindInvoiceListPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        //JPanel panel = ReportBaseMain.AddProductIdSelection(filterList, options, "Product Id:");
        
        JPanel panel = ReportBaseMain.AddFindDateSelection(filterList, "invoiceDate", options ,  null, DateSingleton.PERIOD.NODATE);
        panelInvoiceDate.setLayout(new BorderLayout());
        panelInvoiceDate.add(BorderLayout.CENTER, panel);
        
        panel = ReportBaseMain.AddFindDateSelection(filterList, "dueDate", options ,  null, DateSingleton.PERIOD.NODATE);
        panelInvoiceDueDate.setLayout(new BorderLayout());
        panelInvoiceDueDate.add(BorderLayout.CENTER, panel);
                
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "billingAccountId");
        panelBillingAccount.setLayout(new BorderLayout());
        panelBillingAccount.add(BorderLayout.CENTER, panel);          

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("INVOICE_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", findStatusItemList, null, null);
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, panel);

        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "invoiceId");
        panelInvoiceId.setLayout(new BorderLayout());
        panelInvoiceId.add(BorderLayout.CENTER, panel); 
        
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "referenceNumber");
        panelReference.setLayout(new BorderLayout());
        panelReference.add(BorderLayout.CENTER, panel); 

        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "description");
        panelDescription.setLayout(new BorderLayout());
        panelDescription.add(BorderLayout.CENTER, panel); 
        
        tablePanel = new GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel>(new InvoiceCompositeTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        List<InvoiceType> invoiceTypeList = InvoiceTypeSingleton.getValueList();
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setdescription("All");
        invoiceType.setinvoiceTypeId("ANY");
        invoiceTypeList.add(0, invoiceType);

//        invoiceTypeComboBoxModel = new JGenericComboBoxSelectionModel<InvoiceType>(invoiceTypeList);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "invoiceTypeId", invoiceTypeList, "", options.getInvoiceType());
        panelInvoiceType.setLayout(new BorderLayout());
        panelInvoiceType.add(BorderLayout.CENTER, panel);
   
        
        /*       try {
         invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("PURCHASE_INVOICE"));
         } catch (Exception ex) {
         Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        /*List<StatusItem> findStatusItemList = StatusSingleton.getValueList("INVOICE_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        statusItemComboBoxModel = new JGenericComboBoxSelectionModel<StatusItem>(findStatusItemList);
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, statusItemComboBoxModel.jComboBox);
        statusItemComboBoxModel.setSelectedItem(statusItem);
        */
/*
        invoiceIdCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
        jComboBox1.setModel(invoiceIdCondComboBoxModel);
        StringListCellRenderer stringRenderer = new StringListCellRenderer(showComboKeys);
        jComboBox1.setRenderer(stringRenderer);
        try {
            invoiceIdCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        descCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
        jComboBox2.setModel(descCondComboBoxModel);
        stringRenderer = new StringListCellRenderer(showComboKeys);
        jComboBox2.setRenderer(stringRenderer);

        try {
            descCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        refCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
//        referenceCombo.setModel(refCondComboBoxModel);
//        stringRenderer = new StringListCellRenderer(showComboKeys);
//        referenceCombo.setRenderer(stringRenderer);
        try {
            refCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        ControllerOptions fromOptions = new ControllerOptions(options);
        fromOptions.addDefaultPartyIdField(null);
        fromOptions.put("keyId", "partyIdFrom");       
        panel = ReportBaseMain.AddPartyIdSelection(filterList, fromOptions, null);
        panelFromPartyId.setLayout(new BorderLayout());
        panelFromPartyId.add(BorderLayout.CENTER, panel);
        
        
        ControllerOptions toOptions = new ControllerOptions(options);
        toOptions.put("keyId", "partyId");        
         partyPickerEditPanel = (PartyPickerEditPanel) ReportBaseMain.AddPartyIdSelection(filterList, toOptions, null);        
        panelToPartyId.setLayout(new BorderLayout());
        panelToPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);
        
        if (options.isSalesInvoice()) {
            lblPartyId.setText("Party Id To:");
        } else {
            lblPartyId.setText("Party Id From:");
        }
//        GenericDateSelectionPanel dateFilter = new GenericDateSelectionPanel("Invoice Date:");
//        panelInvoiceDate.setLayout(new BorderLayout());
//        panelInvoiceDate.add(dateFilter, BorderLayout.CENTER);
        //prty id lookup
//        JButton btnHeaderPatryId = new JButton("..");
        //      button.setPreferredSize(new Dimension(10, partyIdTextField.getBounds().height));
//        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
//        cb.install(txtFromPartyId);
//        btnHeaderPatryId.addActionListener(new LookupActionListner(txtFromPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));
//    private ListComboBoxModel<String> invoiceIdCondComboBoxModel = new ListComboBoxModel<String>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(invoiceIdCondComboBoxModel);
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Lookup Invoice(s)");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Invoice Filter");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Invoice List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<InvoiceComposite> orderListModel) {

        tablePanel.setListModel(orderListModel);
    }

    public InvoiceComposite getSelectedInvoice() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }
    
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }
    
    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Invoice Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < InvoiceCompositeTableModel.Columns.values().length; i++) {
            InvoiceCompositeTableModel.Columns[] columns = InvoiceCompositeTableModel.Columns.values();
            InvoiceCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

    }
    
    public List<EntityCondition> getWhereClauseCond() {
        return ReportBaseMain.getWhereClauseCond(filterList);
    }  
    
    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();
    /*    boolean showAll = true;
        InvoiceType invoiceType = (InvoiceType) invoiceTypeComboBoxModel.getSelectedItem();
        if (invoiceType != null && !"ANY".equals(invoiceType.getinvoiceTypeId())) {
            findOptionList.put("invoiceTypeId", invoiceType.getinvoiceTypeId());
            showAll = false;
        }
        StatusItem statusType = (StatusItem) statusItemComboBoxModel.getSelectedItem();
        if (statusType != null && !"ANY".equals(statusType.getstatusId())) {
            findOptionList.put("statusId", statusType.getstatusId());
            showAll = false;
        }

        if (txtInvoiceId.getText() != null && txtInvoiceId.getText().isEmpty() == false) {
            findOptionList.put("invoiceId", txtInvoiceId.getText());
            showAll = false;
        }

        if (txtDescription.getText() != null && txtDescription.getText().isEmpty() == false) {
            findOptionList.put("description", txtDescription.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(panelFromPartyPickerId.textIdField.getText())) {
            if (options.isSalesInvoice()) {
                findOptionList.put("partyId", panelFromPartyPickerId.textIdField.getText());
            } else {
                findOptionList.put("partyIdFrom", panelFromPartyPickerId.textIdField.getText());
            }

            showAll = false;
        }

        if (txtBillingAccountId.getText() != null && txtBillingAccountId.getText().isEmpty() == false) {
            findOptionList.put("billingAccountId", txtBillingAccountId.getText());
            showAll = false;
        }

        if (txtReference.getText() != null && txtReference.getText().isEmpty() == false) {
            findOptionList.put("referenceNumber", txtReference.getText());
            showAll = false;
        }

        if (showAll == true) {
            findOptionList.put("showAll", "Y");
            findOptionList.put("noConditionFind", "Y");
        }

        findOptionList.put("lookupFlag", "Y");
*/
        //status type method
        return findOptionList;
    }

    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = bdValue.setScale(scale, rounding);
            setHorizontalAlignment(JLabel.RIGHT);
// And pass it on to parent class
            return super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
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
        jPanel1 = new javax.swing.JPanel();
        lblPartyId2 = new javax.swing.JLabel();
        panelInvoiceDueDate = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        lblPartyId3 = new javax.swing.JLabel();
        panelInvoiceDate = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelFromPartyId = new javax.swing.JPanel();
        lblPartyId1 = new javax.swing.JLabel();
        panelToPartyId = new javax.swing.JPanel();
        lblPartyId = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelInvoiceType = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelInvoiceId = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelDescription = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        panelBillingAccount = new javax.swing.JPanel();
        panelStatus = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelReference = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));
        panelLookupInvoice.setLayout(new java.awt.BorderLayout());

        lblPartyId2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId2.setText("Invoice Date:");

        panelInvoiceDueDate.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout panelInvoiceDueDateLayout = new javax.swing.GroupLayout(panelInvoiceDueDate);
        panelInvoiceDueDate.setLayout(panelInvoiceDueDateLayout);
        panelInvoiceDueDateLayout.setHorizontalGroup(
            panelInvoiceDueDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInvoiceDueDateLayout.setVerticalGroup(
            panelInvoiceDueDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        btnFind.setText("Find");

        lblPartyId3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId3.setText("Invoice Due Date:");

        panelInvoiceDate.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout panelInvoiceDateLayout = new javax.swing.GroupLayout(panelInvoiceDate);
        panelInvoiceDate.setLayout(panelInvoiceDateLayout);
        panelInvoiceDateLayout.setHorizontalGroup(
            panelInvoiceDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 897, Short.MAX_VALUE)
        );
        panelInvoiceDateLayout.setVerticalGroup(
            panelInvoiceDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPartyId2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPartyId3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelInvoiceDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInvoiceDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(234, 234, 234))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPartyId2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInvoiceDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPartyId3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFind)
                .addContainerGap())
        );

        panelLookupInvoice.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(2636, 120));

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 131));

        panelFromPartyId.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelFromPartyIdLayout = new javax.swing.GroupLayout(panelFromPartyId);
        panelFromPartyId.setLayout(panelFromPartyIdLayout);
        panelFromPartyIdLayout.setHorizontalGroup(
            panelFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        panelFromPartyIdLayout.setVerticalGroup(
            panelFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        lblPartyId1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId1.setText("From Party ID:");

        panelToPartyId.setMinimumSize(new java.awt.Dimension(252, 0));
        panelToPartyId.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelToPartyIdLayout = new javax.swing.GroupLayout(panelToPartyId);
        panelToPartyId.setLayout(panelToPartyIdLayout);
        panelToPartyIdLayout.setHorizontalGroup(
            panelToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        panelToPartyIdLayout.setVerticalGroup(
            panelToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        lblPartyId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId.setText("To Party ID:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Invoice Type:");

        panelInvoiceType.setMinimumSize(new java.awt.Dimension(252, 0));
        panelInvoiceType.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelInvoiceTypeLayout = new javax.swing.GroupLayout(panelInvoiceType);
        panelInvoiceType.setLayout(panelInvoiceTypeLayout);
        panelInvoiceTypeLayout.setHorizontalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        panelInvoiceTypeLayout.setVerticalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Invoice Id:");

        panelInvoiceId.setMinimumSize(new java.awt.Dimension(252, 0));
        panelInvoiceId.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelInvoiceIdLayout = new javax.swing.GroupLayout(panelInvoiceId);
        panelInvoiceId.setLayout(panelInvoiceIdLayout);
        panelInvoiceIdLayout.setHorizontalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        panelInvoiceIdLayout.setVerticalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPartyId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPartyId1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInvoiceType, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(panelToPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(panelFromPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelFromPartyId, panelInvoiceId, panelInvoiceType, panelToPartyId});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPartyId)
                    .addComponent(panelToPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPartyId1)
                    .addComponent(panelFromPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, panelInvoiceId});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, panelInvoiceType});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblPartyId, panelToPartyId});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblPartyId1, panelFromPartyId});

        jPanel3.setPreferredSize(new java.awt.Dimension(600, 135));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Reference Number:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Description:");

        panelDescription.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelDescriptionLayout = new javax.swing.GroupLayout(panelDescription);
        panelDescription.setLayout(panelDescriptionLayout);
        panelDescriptionLayout.setHorizontalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        panelDescriptionLayout.setVerticalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");

        panelBillingAccount.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelBillingAccountLayout = new javax.swing.GroupLayout(panelBillingAccount);
        panelBillingAccount.setLayout(panelBillingAccountLayout);
        panelBillingAccountLayout.setHorizontalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        panelBillingAccountLayout.setVerticalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        panelStatus.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Billing Account ID:");

        panelReference.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelReferenceLayout = new javax.swing.GroupLayout(panelReference);
        panelReference.setLayout(panelReferenceLayout);
        panelReferenceLayout.setHorizontalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        panelReferenceLayout.setVerticalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelBillingAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelReference, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelBillingAccount, panelDescription, panelReference, panelStatus});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(panelBillingAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel3, panelDescription, panelReference});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, panelBillingAccount});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel9, panelStatus});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelLookupInvoice.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        add(panelLookupInvoice, java.awt.BorderLayout.PAGE_START);

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1281, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(panelIResultList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblPartyId;
    private javax.swing.JLabel lblPartyId1;
    private javax.swing.JLabel lblPartyId2;
    private javax.swing.JLabel lblPartyId3;
    private javax.swing.JPanel panelBillingAccount;
    private javax.swing.JPanel panelDescription;
    private javax.swing.JPanel panelFromPartyId;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelInvoiceDate;
    private javax.swing.JPanel panelInvoiceDueDate;
    private javax.swing.JPanel panelInvoiceId;
    private javax.swing.JPanel panelInvoiceType;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelReference;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelToPartyId;
    // End of variables declaration//GEN-END:variables
}
