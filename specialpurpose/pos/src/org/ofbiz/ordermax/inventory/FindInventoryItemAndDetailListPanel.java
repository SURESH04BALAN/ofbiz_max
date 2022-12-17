/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.invoice.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.FacilityListCellRenderer;
import mvc.model.list.InvoiceTypeListCellRenderer;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StatusItemTypeListCellRenderer;
import mvc.model.list.StringListCellRenderer;
import mvc.model.table.InventoryItemDetailTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.InventoryItemAndDetail;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class FindInventoryItemAndDetailListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;

    private final JTextField txtOrderIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

    private InventoryItemDetailTableModel paymentTableModel = new InventoryItemDetailTableModel();
    private ListAdapterListModel<InventoryItemAndDetail> invoiceCompositeListModel = new ListAdapterListModel<InventoryItemAndDetail>();

    //payment type
    public ListComboBoxModel<InvoiceType> invoiceTypeComboBoxModel = new ListComboBoxModel<InvoiceType>();
    private JComboBox<InvoiceType> invoiceTypeComboBox = new JComboBox<InvoiceType>(invoiceTypeComboBoxModel);

    //status type method
    private ListComboBoxModel<StatusItem> statusItemComboBoxModel = new ListComboBoxModel<StatusItem>();
    private JComboBox<StatusItem> statusItemComboBox = new JComboBox<StatusItem>(statusItemComboBoxModel);

    //status type method
    private ListComboBoxModel<String> invoiceIdCondComboBoxModel = new ListComboBoxModel<String>();
    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(invoiceIdCondComboBoxModel);

    //status type method
    private ListComboBoxModel<String> descCondComboBoxModel = new ListComboBoxModel<String>();
    private ListComboBoxModel<String> refCondComboBoxModel = new ListComboBoxModel<String>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(descCondComboBoxModel);

    boolean showComboKeys = false;
    public JComboBoxSelectionModel<Facility> facilityComboModel = null;
    public JGenericComboBoxSelectionModel<Product> productComboModel = null;
public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindInventoryItemAndDetailListPanel(ControllerOptions options) {
        initComponents();
        /*        this.removeAll();
         this.setLayout(new BorderLayout());

         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(2, 1, 2, 1);
         gbc.weightx = 1.0;
         gbc.weighty = 1.0;

         CollapsiblePanel p1 = new CollapsiblePanel();
         p1.setTitle("Text Collapse Panel");
         //        JPanel p = new JPanel();  
         p1.setLayout(new GridBagLayout());
         gbc.gridwidth = gbc.RELATIVE;
         p1.add(new JButton("button 1"), gbc);
         gbc.gridwidth = gbc.REMAINDER;
         p1.add(new JButton("button 2"), gbc);
         gbc.gridwidth = gbc.RELATIVE;
         p1.add(new JButton("button 3"), gbc);
         gbc.gridwidth = gbc.REMAINDER;
         p1.add(new JButton("button 4"), gbc);
         //        this.add(BorderLayout.BEFORE_FIRST_LINE, p1);
         //        this.removeAll();
         //        JPanel test = initComponents1();//createDetailsPanel();
         //        this.add(BorderLayout.BEFORE_FIRST_LINE, test);
         //        this.add(BorderLayout.CENTER, panelInvoiceList);
         */
        JPanel panel = ReportBaseMain.AddProductIdSelection(filterList, options, null);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(panel, BorderLayout.CENTER);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "facilityId", FacilitySingleton.getValueList(), null, null);
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, panel);

       
        tableReceiveInv.setModel(paymentTableModel);
        setupEditOrderTable();

   /*     invoiceTypeComboBoxModel.setListModel(InvoiceTypeSingleton.getValueListModal());
//        panelInvoiceType.setLayout(new BorderLayout());
//        panelInvoiceType.add(BorderLayout.CENTER, invoiceTypeComboBox);
        ListCellRenderer<InvoiceType> paymentTypeRenderer = new InvoiceTypeListCellRenderer(showComboKeys);
        invoiceTypeComboBox.setRenderer(paymentTypeRenderer);

        try {
            invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("PURCHASE_INVOICE"));
        } catch (Exception ex) {
            Logger.getLogger(FindInventoryItemAndDetailListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        statusItemComboBoxModel.setListModel(StatusSingleton.getValueListModal("INVOICE_STATUS"));
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, statusItemComboBox);
        StatusItemTypeListCellRenderer statusItemRenderer = new StatusItemTypeListCellRenderer();
        statusItemComboBox.setRenderer(statusItemRenderer);

        invoiceIdCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
        jComboBox1.setModel(invoiceIdCondComboBoxModel);
        StringListCellRenderer stringRenderer = new StringListCellRenderer(showComboKeys);
        jComboBox1.setRenderer(stringRenderer);
        try {
            invoiceIdCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInventoryItemAndDetailListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        descCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
        jComboBox2.setModel(descCondComboBoxModel);
        stringRenderer = new StringListCellRenderer(showComboKeys);
        jComboBox2.setRenderer(stringRenderer);

        try {
            descCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInventoryItemAndDetailListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        refCondComboBoxModel.setListModel(ConditionSelectSingleton.getValueListModal());
        referenceCombo.setModel(refCondComboBoxModel);
        stringRenderer = new StringListCellRenderer(showComboKeys);
        referenceCombo.setRenderer(stringRenderer);
        try {
            refCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInventoryItemAndDetailListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        //prty id lookup
//        JButton btnHeaderPatryId = new JButton("..");
        //      button.setPreferredSize(new Dimension(10, partyIdTextField.getBounds().height));
//        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
//        cb.install(txtFromPartyId);
//        btnHeaderPatryId.addActionListener(new LookupActionListner(txtFromPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));
//    private ListComboBoxModel<String> invoiceIdCondComboBoxModel = new ListComboBoxModel<String>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(invoiceIdCondComboBoxModel);
//        ComponentBorder.doubleRaisedLoweredBevelBorder(test, "Find Inventory Detail Item");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Inventory Detail List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<InventoryItemAndDetail> orderListModel) {

        paymentTableModel.setListModel(orderListModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    public JTextField getTxtOrderIdTableTextField() {
        return txtOrderIdTableTextField;
    }
    
    public List<EntityCondition> getWhereClauseCond() {
        return ReportBaseMain.getWhereClauseCond(filterList);
    }
    
    public RowColumnClickActionTableCellEditor getOrderActionTableCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < InventoryItemDetailTableModel.Columns.values().length; i++) {
            InventoryItemDetailTableModel.Columns[] columns = InventoryItemDetailTableModel.Columns.values();
            InventoryItemDetailTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (InventoryItemDetailTableModel.Columns.productId == column) {
                tableReceiveInv.setSurrendersFocusOnKeystroke(true);
                txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            } else if (InventoryItemDetailTableModel.Columns.orderId == column) {
                tableReceiveInv.setSurrendersFocusOnKeystroke(true);
                txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);
            }
        }
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();

        if (facilityComboModel.jComboBox.getSelectedItem() != null) {
            Facility facility = (Facility) facilityComboModel.jComboBox.getSelectedItem();
            findOptionList.put("facilityId", facility.getfacilityId());
        }

        if (productComboModel.jComboBox.getSelectedItem() != null) {
            Product product = (Product) productComboModel.jComboBox.getSelectedItem();
            findOptionList.put("productId", product.getproductId());
        }

        if (invoiceTypeComboBoxModel.getSelectedItem() != null) {
            InvoiceType invoiceType = (InvoiceType) invoiceTypeComboBoxModel.getSelectedItem();
            findOptionList.put("invoiceTypeId", invoiceType.getinvoiceTypeId());

        }

        if (statusItemComboBoxModel.getSelectedItem() != null) {
            StatusItem statusType = (StatusItem) statusItemComboBoxModel.getSelectedItem();
            findOptionList.put("statusId", statusType.getstatusId());
        }

        if (txtDescription.getText() != null && txtDescription.getText().isEmpty() == false) {
            findOptionList.put("description", txtDescription.getText());
        }

        if (txtFromPartyId.getText() != null && txtFromPartyId.getText().isEmpty() == false) {
            findOptionList.put("partyIdFrom", txtFromPartyId.getText());
        }

        if (txtBillingAccountId.getText() != null && txtBillingAccountId.getText().isEmpty() == false) {
            findOptionList.put("billingAccountId", txtBillingAccountId.getText());
        }

        if (txtReference.getText() != null && txtReference.getText().isEmpty() == false) {
            findOptionList.put("referenceNumber", txtReference.getText());
        }

        //status type method
        return findOptionList;
    }

    /*  
     public Map<String, Object> getFindOptionList(){
     Map<String, Object> findOptionList = FastMap.newInstance();   
        
     if(invoiceTypeComboBoxModel.getSelectedItem()!=null){
     InvoiceType invoiceType = (InvoiceType) invoiceTypeComboBoxModel.getSelectedItem();
     findOptionList.put("invoiceTypeId", invoiceType.getinvoiceTypeId());
            
     }

     if(statusItemComboBoxModel.getSelectedItem()!=null){
     StatusItem statusType = (StatusItem) statusItemComboBoxModel.getSelectedItem();
     findOptionList.put("statusId", statusType.getstatusId());            
     }
        
     if(txtInvoiceId.getText()!=null && txtInvoiceId.getText().isEmpty()==false){
     findOptionList.put("invoiceId", txtInvoiceId.getText());            
     }        
        
     if(txtDescription.getText()!=null && txtDescription.getText().isEmpty()==false){
     findOptionList.put("description", txtDescription.getText());            
     }        

     if(txtFromPartyId.getText()!=null && txtFromPartyId.getText().isEmpty()==false){
     findOptionList.put("partyIdFrom", txtFromPartyId.getText());            
     }        

     if(txtBillingAccountId.getText()!=null && txtBillingAccountId.getText().isEmpty()==false){
     findOptionList.put("billingAccountId", txtBillingAccountId.getText());            
     }        
        
     if(txtReference.getText()!=null && txtReference.getText().isEmpty()==false){
     findOptionList.put("referenceNumber", txtReference.getText());            
     }        
       
       
     //status type method
    
     return findOptionList;
     }
     */
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
        java.awt.GridBagConstraints gridBagConstraints;

        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtFromPartyId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtReference = new javax.swing.JTextField();
        cbRejectedItems = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        txtBillingAccountId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        referenceCombo = new javax.swing.JComboBox();
        jTextField8 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        txtDescription = new javax.swing.JTextField();
        cbCreated1 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        txtDescription2 = new javax.swing.JTextField();
        cbCreated2 = new javax.swing.JCheckBox();
        panelFacilityId = new javax.swing.JPanel();
        panelProductId = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelInvoiceList.setLayout(new java.awt.GridLayout(1, 0));

        tableReceiveInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableReceiveInv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableReceiveInv);

        panelInvoiceList.add(jScrollPane1);

        add(panelInvoiceList, java.awt.BorderLayout.CENTER);

        jPanel4.setMinimumSize(new java.awt.Dimension(539, 270));
        jPanel4.setPreferredSize(new java.awt.Dimension(678, 270));
        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(txtFromPartyId, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Soft Identifier:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel6, gridBagConstraints);

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(panelStatus, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Lot Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel10, gridBagConstraints);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(btnFind, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Manufacturer Party Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(txtReference, gridBagConstraints);

        cbRejectedItems.setSelected(true);
        cbRejectedItems.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(cbRejectedItems, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serial Number:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(txtBillingAccountId, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(referenceCombo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jTextField8, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Facility Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel1, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Internal Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel11, gridBagConstraints);

        jComboBox3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jComboBox3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(txtDescription, gridBagConstraints);

        cbCreated1.setSelected(true);
        cbCreated1.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(cbCreated1, gridBagConstraints);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Inventory Item Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel12, gridBagConstraints);

        jComboBox6.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jComboBox6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(txtDescription2, gridBagConstraints);

        cbCreated2.setSelected(true);
        cbCreated2.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(cbCreated2, gridBagConstraints);

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(panelFacilityId, gridBagConstraints);

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(panelProductId, gridBagConstraints);

        add(jPanel4, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private JPanel createDetailsPanel() {

        FacilityListCellRenderer pccRender = new FacilityListCellRenderer();
        List<Facility> list = FacilitySingleton.getValueList();
        facilityComboModel = new JComboBoxSelectionModel<Facility>(list, pccRender);
        JComboBox<Facility> jComboBox = facilityComboModel.jComboBox;
//        panelFacilityId.setLayout(new BorderLayout());
//        panelFacilityId.add(BorderLayout.CENTER, jComboBox);

        List<Product> findProductList = ProductSingleton.getValueList();
        Product temp = new Product();
        temp.setdescription("<All>");
        temp.setproductId(null);
        findProductList.add(0, temp);
        productComboModel = new JGenericComboBoxSelectionModel<Product>(findProductList);
//        panelProduct.setLayout(new BorderLayout());
//        panelProduct.add(BorderLayout.CENTER, productComboModel.jComboBox);

        JPanel panel = new CollapsiblePanel();

        JLabel thingNameLabel = jLabel1; //new JLabel("Thing Name");
        JLabel anAttributeLabel = jLabel3;//new JLabel("An Attribute");
        JLabel dateFieldLabel = jLabel3;//new JLabel("Date Field");
        JLabel anAttLabel = new JLabel("An Att");
        JLabel anotherAttLabel = new JLabel("Another Att");
        JLabel anotherAtt2Label = new JLabel("Another Att");

        JTextField thingNameField = new JTextField("");
        JTextField anAttributeField = new JTextField("");
        JTextField dateFieldField = new JTextField("");
        JTextField anAttField = new JTextField("");
        JTextArea anotherAttField = new JTextArea(3, 1);
        JTextField anotherAtt2Field = new JTextField("", 10);

        anotherAtt2Field.setMinimumSize(anotherAtt2Field.getPreferredSize());

        JCheckBox checkbox1 = new JCheckBox("A Checkbox");
        JCheckBox checkbox2 = new JCheckBox("A Checkbox");

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        int i = 0;

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.NORTHEAST;

        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(thingNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(facilityComboModel.jComboBox, gbc);

        i++;

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        panel.add(checkbox1, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(anAttributeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(productComboModel.jComboBox, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(dateFieldLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(dateFieldField, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(anAttLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(anAttField, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(anotherAttLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.weightx = 1.0;
//		gbc.weighty = 1.0;
        panel.add(new JScrollPane(anotherAttField), gbc);

        i++;
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(anotherAtt2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(anotherAtt2Field, gbc);

        gbc.gridx = 2;
        gbc.gridy = i;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnFind, gbc);

        return panel;
    }

   /* private JPanel initComponents1() {
        java.awt.GridBagConstraints gridBagConstraints;
        FacilityListCellRenderer pccRender = new FacilityListCellRenderer();
        List<Facility> list = FacilitySingleton.getValueList();
        facilityComboModel = new JComboBoxSelectionModel<Facility>(list, pccRender);
        JComboBox<Facility> comboBoxFacilityId = facilityComboModel.jComboBox;
//        panelFacilityId.setLayout(new BorderLayout());
//        panelFacilityId.add(BorderLayout.CENTER, jComboBox);

        List<Product> findProductList = ProductSingleton.getValueList();
        Product temp = new Product();
        temp.setdescription("<All>");
        temp.setproductId(null);
        findProductList.add(0, temp);
        productComboModel = new JGenericComboBoxSelectionModel<Product>(findProductList);
        JComboBox<Product> comboBoxProductId = productComboModel.jComboBox;

        jPanel4 = new CollapsiblePanel();
        txtFromPartyId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtReference = new javax.swing.JTextField();
        cbRejectedItems = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        txtBillingAccountId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        referenceCombo = new javax.swing.JComboBox();
        jTextField8 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbProcessing = new javax.swing.JCheckBox();
        txtInvoiceId = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        cbCreated = new javax.swing.JCheckBox();
        txtDescription = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        /*jLabel7 = new javax.swing.JLabel();
         jTextField5 = new javax.swing.JTextField();
         jToggleButton1 = new javax.swing.JToggleButton();
         jComboBox4 = new javax.swing.JComboBox();
         jToggleButton2 = new javax.swing.JToggleButton();
         jComboBox5 = new javax.swing.JComboBox();
         jTextField6 = new javax.swing.JTextField();*/
        /*jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        txtDescription1 = new javax.swing.JTextField();
        cbCreated1 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        txtDescription2 = new javax.swing.JTextField();
        cbCreated2 = new javax.swing.JCheckBox();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

//        jPanel4.setPreferredSize(new java.awt.Dimension(678, 315));
        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(txtFromPartyId, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Soft Identifier:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 50, 0, 0);
        jPanel4.add(jLabel6, gridBagConstraints);

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
                panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 124, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
                panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 124;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(panelStatus, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Lot Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 89, 0, 0);
        jPanel4.add(jLabel10, gridBagConstraints);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 26, 0);
        jPanel4.add(btnFind, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Manufacturer Party Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel4.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 10, 0, 0);
        jPanel4.add(txtReference, gridBagConstraints);

        cbRejectedItems.setSelected(true);
        cbRejectedItems.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        jPanel4.add(cbRejectedItems, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serial Number:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 31, 0, 0);
        jPanel4.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(txtBillingAccountId, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 73, 0, 0);
        jPanel4.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        jPanel4.add(referenceCombo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 0);
        jPanel4.add(jTextField8, gridBagConstraints);

        jComboBox1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        jPanel4.add(jComboBox1, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Facility Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 31, 0, 0);
        jPanel4.add(jLabel1, gridBagConstraints);

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        jPanel4.add(cbProcessing, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel4.add(comboBoxFacilityId, gridBagConstraints);

        jComboBox2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 6, 0, 0);
        jPanel4.add(jComboBox2, gridBagConstraints);

        cbCreated.setSelected(true);
        cbCreated.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(cbCreated, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 0, 0);
        jPanel4.add(comboBoxProductId, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 31, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        /* jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         jLabel7.setText("Date Time Received:");
         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 0;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(4, 22, 0, 0);
         jPanel4.add(jLabel7, gridBagConstraints);
         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 1;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridwidth = 2;
         gridBagConstraints.gridheight = 3;
         gridBagConstraints.ipadx = 118;
         gridBagConstraints.ipady = 1;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
         jPanel4.add(jTextField5, gridBagConstraints);

         jToggleButton1.setText("jToggleButton1");
         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 3;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridheight = 3;
         gridBagConstraints.ipadx = -80;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
         jPanel4.add(jToggleButton1, gridBagConstraints);

         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 4;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridheight = 2;
         gridBagConstraints.ipadx = 86;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
         jPanel4.add(jComboBox4, gridBagConstraints);

         jToggleButton2.setText("jToggleButton1");
         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 8;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridheight = 3;
         gridBagConstraints.ipadx = -80;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
         jPanel4.add(jToggleButton2, gridBagConstraints);

         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 9;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridheight = 2;
         gridBagConstraints.ipadx = 66;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 243);
         jPanel4.add(jComboBox5, gridBagConstraints);
         gridBagConstraints = new java.awt.GridBagConstraints();
         gridBagConstraints.gridx = 5;
         gridBagConstraints.gridy = 3;
         gridBagConstraints.gridwidth = 3;
         gridBagConstraints.gridheight = 2;
         gridBagConstraints.ipadx = 119;
         gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
         gridBagConstraints.insets = new java.awt.Insets(1, 6, 0, 0);
         jPanel4.add(jTextField6, gridBagConstraints);
         */
       /* jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Internal Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 31, 0, 0);
        jPanel4.add(jLabel11, gridBagConstraints);

        jComboBox3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 6, 0, 0);
        jPanel4.add(jComboBox3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 0, 0);
        jPanel4.add(txtDescription1, gridBagConstraints);

        cbCreated1.setSelected(true);
        cbCreated1.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(cbCreated1, gridBagConstraints);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Inventory Item Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 31, 0, 0);
        jPanel4.add(jLabel12, gridBagConstraints);

        jComboBox6.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 6, 0, 0);
        jPanel4.add(jComboBox6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 0, 0);
        jPanel4.add(txtDescription2, gridBagConstraints);

        cbCreated2.setSelected(true);
        cbCreated2.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel4.add(cbCreated2, gridBagConstraints);

//        add(jPanel4, java.awt.BorderLayout.NORTH);
        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelInvoiceList.setLayout(new java.awt.GridLayout(1, 0));

        tableReceiveInv.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tableReceiveInv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableReceiveInv);

        panelInvoiceList.add(jScrollPane1);
        this.add(BorderLayout.BEFORE_FIRST_LINE, jPanel4);
        this.add(BorderLayout.CENTER, panelInvoiceList);

//        add(panelInvoiceList, java.awt.BorderLayout.CENTER);
        return jPanel4;
    }// </editor-fold>           */             

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbCreated1;
    public javax.swing.JCheckBox cbCreated2;
    public javax.swing.JCheckBox cbRejectedItems;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JComboBox referenceCombo;
    private javax.swing.JTable tableReceiveInv;
    private javax.swing.JTextField txtBillingAccountId;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDescription2;
    private javax.swing.JTextField txtFromPartyId;
    private javax.swing.JTextField txtReference;
    // End of variables declaration//GEN-END:variables
}
