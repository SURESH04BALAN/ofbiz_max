/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.requirement;

import org.ofbiz.ordermax.invoice.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.model.list.InvoiceTypeListCellRenderer;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.StatusItemTypeListCellRenderer;
import mvc.model.list.StringListCellRenderer;
import mvc.model.table.InvoiceCompositeTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class FindInvoiceListPanel extends javax.swing.JPanel {
   
    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    private InvoiceCompositeTableModel paymentTableModel = new InvoiceCompositeTableModel();
    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    
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
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindInvoiceListPanel() {
        initComponents();
        tableReceiveInv.setModel(paymentTableModel);
        setupEditOrderTable();

        invoiceTypeComboBoxModel.setListModel(InvoiceTypeSingleton.getValueListModal());
        panelInvoiceType.setLayout(new BorderLayout());
        panelInvoiceType.add(BorderLayout.CENTER, invoiceTypeComboBox);
        ListCellRenderer<InvoiceType> paymentTypeRenderer = new InvoiceTypeListCellRenderer(showComboKeys);
        invoiceTypeComboBox.setRenderer(paymentTypeRenderer);
        
        try {
            invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("PURCHASE_INVOICE"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        referenceCombo.setModel(refCondComboBoxModel);
        stringRenderer = new StringListCellRenderer(showComboKeys);
        referenceCombo.setRenderer(stringRenderer);        
        try {
            refCondComboBoxModel.setSelectedItem(ConditionSelectSingleton.getString("equals"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //prty id lookup
        JButton btnHeaderPatryId = new JButton("..");
        //      button.setPreferredSize(new Dimension(10, partyIdTextField.getBounds().height));
        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
        cb.install(txtFromPartyId);
       // btnHeaderPatryId.addActionListener(new LookupActionListner(txtFromPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));

        
//    private ListComboBoxModel<String> invoiceIdCondComboBoxModel = new ListComboBoxModel<String>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(invoiceIdCondComboBoxModel);
        
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Lookup Invoice(s)");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Invoice List");        
    }

    public void setReceiveInventoryList(ListAdapterListModel<InvoiceComposite> orderListModel) {

        paymentTableModel.setListModel(orderListModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tableReceiveInv.getColumn("INVOICE ID").setCellEditor(productTreeActionTableCellEditor);
    
        for (int i = 0; i < InvoiceCompositeTableModel.Columns.values().length; i++) {
            InvoiceCompositeTableModel.Columns[] columns = InvoiceCompositeTableModel.Columns.values();
            InvoiceCompositeTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
            else if(column.getClassName().equals(java.sql.Timestamp.class)){  
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());                        
            }
        }
        
    }
    

    
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
    
    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = bdValue.setScale(org.ofbiz.order.shoppingcart.ShoppingCart.scale, org.ofbiz.order.shoppingcart.ShoppingCart.rounding);
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

        jPanel2 = new javax.swing.JPanel();
        panelLookupInvoice = new javax.swing.JPanel();
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
        txtInvoiceId = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        txtFromPartyId = new javax.swing.JTextField();
        txtBillingAccountId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        referenceCombo = new javax.swing.JComboBox();
        txtReference = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelInvoiceType = new javax.swing.JPanel();
        panelStatus = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jComboBox5 = new javax.swing.JComboBox();
        jTextField6 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox();
        jTextField5 = new javax.swing.JTextField();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbShowZeroAmount = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Requirement Id:");

        cbCreated.setSelected(true);
        cbCreated.setText("Ignore Case");

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Description:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Requirement Type:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("From Party ID:");

        cbRejectedItems.setSelected(true);
        cbRejectedItems.setText("Ignore Case");

        btnFind.setText("Find");

        jComboBox1.setEnabled(false);

        jComboBox2.setEnabled(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Billing Account ID:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Reference Number:");

        javax.swing.GroupLayout panelInvoiceTypeLayout = new javax.swing.GroupLayout(panelInvoiceType);
        panelInvoiceType.setLayout(panelInvoiceTypeLayout);
        panelInvoiceTypeLayout.setHorizontalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        panelInvoiceTypeLayout.setVerticalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Invoice Date"));
        jPanel1.setForeground(new java.awt.Color(240, 240, 240));

        jToggleButton2.setText("jToggleButton1");

        jToggleButton1.setText("jToggleButton1");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Date Between:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel7)
                        .addGap(3, 3, 3)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jToggleButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Filter (purchase orders):");

        cbShowZeroAmount.setText("Show zero amounts");

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFromPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBillingAccountId, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addComponent(referenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(txtReference, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbRejectedItems)
                    .addComponent(cbProcessing)
                    .addComponent(cbCreated))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbShowZeroAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel4, jLabel5});

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnFind, cbCreated, cbProcessing, cbRejectedItems});

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox1, jComboBox2, referenceCombo});

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField8, panelInvoiceType, panelStatus, txtBillingAccountId, txtFromPartyId});

        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbProcessing)
                                .addComponent(txtInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbCreated))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtFromPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBillingAccountId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbShowZeroAmount))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(referenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbRejectedItems))))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnFind))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLookupInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLookupInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

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
        jScrollPane1.setViewportView(tableReceiveInv);

        panelInvoiceList.add(jScrollPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbCreated;
    public javax.swing.JCheckBox cbProcessing;
    public javax.swing.JCheckBox cbRejectedItems;
    public javax.swing.JCheckBox cbShowZeroAmount;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JPanel panelInvoiceType;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JComboBox referenceCombo;
    private javax.swing.JTable tableReceiveInv;
    private javax.swing.JTextField txtBillingAccountId;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtFromPartyId;
    private javax.swing.JTextField txtInvoiceId;
    private javax.swing.JTextField txtReference;
    // End of variables declaration//GEN-END:variables
}
