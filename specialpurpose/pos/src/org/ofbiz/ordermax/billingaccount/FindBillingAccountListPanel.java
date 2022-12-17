/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.StringListCellRenderer;
import mvc.model.table.BillingAccountTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.SelectionTypeSingleton;


/**
 *
 * @author siranjeev
 */
public class FindBillingAccountListPanel extends javax.swing.JPanel {
   
    private final JTextField txtBillingAccountIdTableTextField = new JTextField();
//    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

    private BillingAccountTableModel paymentTableModel = new BillingAccountTableModel ();
    ListAdapterListModel  invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
    public JComboBoxSelectionModel<String> accountIdCondComboModel = null;
    public JComboBoxSelectionModel<String> descCondComboModel = null;
    private DatePickerEditPanel fromDate = null;
    private DatePickerEditPanel thruDate = null;
    
//    public JComboBoxSelectionModel<String> accountIdCondComboModel = null;
//    public JComboBoxSelectionModel<String> descCondComboModel = null;
    
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindBillingAccountListPanel() {
        initComponents();
//        List<PaymentType> findProductList = PaymentTypeSingleton.getValueList();
        accountIdCondComboModel = new JComboBoxSelectionModel<String>(SelectionTypeSingleton.getValueList(), new StringListCellRenderer(false));
        panelAccountIdCond.setLayout(new BorderLayout());        
        panelAccountIdCond.add(BorderLayout.CENTER, accountIdCondComboModel.jComboBox);
        
        descCondComboModel = new JComboBoxSelectionModel<String>(SelectionTypeSingleton.getValueList(), new StringListCellRenderer(false));
        panelDescCond.setLayout(new BorderLayout());
        panelDescCond.add(BorderLayout.CENTER, descCondComboModel.jComboBox);                
                
        fromDate = new DatePickerEditPanel();
//        fromDate.setSession(session);
        panelFromDate.setLayout(new BorderLayout());
        panelFromDate.add(BorderLayout.CENTER, fromDate);

        thruDate = new DatePickerEditPanel();
  //      thruDate.setSession(session);
        panelThruDate.setLayout(new BorderLayout());
        panelThruDate.add(BorderLayout.CENTER, thruDate);
        
        tableReceiveInv.setModel(paymentTableModel);
        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Search Option");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Search Result");    
//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        
        
    }

    public void setBillingAccountList(ListAdapterListModel<BillingAccount> orderListModel) {

        paymentTableModel.setListModel(orderListModel);
    }

    public JTextField getTxtBillingAccountIdTableTextField() {
        return txtBillingAccountIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getRowColumnActionCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }
    
    public Map<String, Object> getFindOptionList(){
        boolean findVal = false;
        Map<String, Object> findOptionList = FastMap.newInstance();                   
        if(UtilValidate.isNotEmpty(txtBillingId.getText())){
            findOptionList.put("billingAccountId", txtBillingId.getText());            
            findVal = true;
        }

        if(UtilValidate.isNotEmpty(textDescription.getText())){
//            StatusItem statusType = (StatusItem) descCondComboModel.comboBoxModel.getSelectedItem();
//            findOptionList.put("statusId", statusType.getstatusId());            
            findOptionList.put("description", textDescription.getText());                        
            findVal = true;            
        }
        
       if(UtilValidate.isNotEmpty(txtAccoutLimit.getText())){
            findOptionList.put("accountLimit", txtAccoutLimit.getText());            
            findVal = true;            
        }        

       if(UtilValidate.isNotEmpty(fromDate.txtDate.getText())){
            try {
                findOptionList.put("fromDate", fromDate.getTimeStamp());            
                findVal = true;
            } catch (Exception ex) {
                Logger.getLogger(FindBillingAccountListPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        

       if(UtilValidate.isNotEmpty(thruDate.txtDate.getText())){
            try {
                findOptionList.put("thruDate", thruDate.getTimeStamp());            
                findVal = true;
            } catch (Exception ex) {
                Logger.getLogger(FindBillingAccountListPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
       
       if(findVal==false){
           findOptionList.put("noConditionFind","Y");
       }

        return findOptionList;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);
//        txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
//        DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
//        editor.setClickCountToStart(0);
//        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
//        tableReceiveInv.getColumn("Payment Id").setCellEditor(productTreeActionTableCellEditor);
        for (int i = 0; i < BillingAccountTableModel.Columns.values().length; i++) {
            BillingAccountTableModel.Columns[] columns = BillingAccountTableModel.Columns.values();
            BillingAccountTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            
            
            if (BillingAccountTableModel.Columns.BILLINGACCOUNTID.toString().equals(column.toString())){
                Debug.logError("col name: swwt" + column.toString(), "module");                            
                tableReceiveInv.setSurrendersFocusOnKeystroke(true);
                txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);
                Debug.logError("col name: swwt end" + column.toString(), "module");  
            }    
            else if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
            else if(column.getClassName().equals(java.sql.Timestamp.class)){  
                col.setCellRenderer(new DateFormatCellRenderer());                        
            }
                    
        }        
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        panelLookupInvoice = new CollapsiblePanel();
        jLabel1 = new javax.swing.JLabel();
        cbProcessing = new javax.swing.JCheckBox();
        cbRejectedItems = new javax.swing.JCheckBox();
        btnFind = new javax.swing.JButton();
        txtBillingId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textDescription = new javax.swing.JTextField();
        txtAccoutLimit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        panelAccountIdCond = new javax.swing.JPanel();
        panelDescCond = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelFromDate = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelLookupInvoice.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Billing Account Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel1, gridBagConstraints);

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbProcessing, gridBagConstraints);

        cbRejectedItems.setSelected(true);
        cbRejectedItems.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbRejectedItems, gridBagConstraints);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(btnFind, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 139;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtBillingId, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Description:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(textDescription, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtAccoutLimit, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Account Limit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel10, gridBagConstraints);

        panelAccountIdCond.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelAccountIdCondLayout = new javax.swing.GroupLayout(panelAccountIdCond);
        panelAccountIdCond.setLayout(panelAccountIdCondLayout);
        panelAccountIdCondLayout.setHorizontalGroup(
            panelAccountIdCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelAccountIdCondLayout.setVerticalGroup(
            panelAccountIdCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelLookupInvoice.add(panelAccountIdCond, gridBagConstraints);

        panelDescCond.setPreferredSize(new java.awt.Dimension(100, 10));

        javax.swing.GroupLayout panelDescCondLayout = new javax.swing.GroupLayout(panelDescCond);
        panelDescCond.setLayout(panelDescCondLayout);
        panelDescCondLayout.setHorizontalGroup(
            panelDescCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDescCondLayout.setVerticalGroup(
            panelDescCondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelDescCond, gridBagConstraints);

        panelThruDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelThruDate, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Thru Date:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel16, gridBagConstraints);

        panelFromDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelFromDate, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("From Date:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel17, gridBagConstraints);

        jPanel2.add(panelLookupInvoice, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
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
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbProcessing;
    public javax.swing.JCheckBox cbRejectedItems;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAccountIdCond;
    private javax.swing.JPanel panelDescCond;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JTable tableReceiveInv;
    private javax.swing.JTextField textDescription;
    private javax.swing.JTextField txtAccoutLimit;
    private javax.swing.JTextField txtBillingId;
    // End of variables declaration//GEN-END:variables
}
