/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ReturnTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ReturnHeaderType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindOrderReturnListPanel extends javax.swing.JPanel {
   
    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

//    private OrderReturnTableModel returnTableModel = new OrderReturnTableModel();
    public GenericTableModelPanel<ReturnHeaderComposite, ReturnTableModel> tablePanel = null;
    public JGenericComboBoxSelectionModel<ReturnHeaderType> returnHeaderTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> StatusItemComboModel = null;
    
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    
    public JComboBoxSelectionModel<ProductPriceType> productPriceTypeComboModel = null;
    
    private ListAdapterListModel<PaymentComposite> invoiceCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    public JComboBoxSelectionModel<PaymentType> paymentTypeComboModel = null;
    public JComboBoxSelectionModel<StatusItem> paymentStatusItemComboModel = null;
    PartyPickerEditPanel partyPickerEditPanel = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    ControllerOptions options = null;
    public FindOrderReturnListPanel(ControllerOptions options) {
        initComponents();
        this.options = options.getCopy();
        tablePanel = new GenericTableModelPanel<ReturnHeaderComposite, ReturnTableModel>(new ReturnTableModel());
        panelReturnList.setLayout(new BorderLayout());
        panelReturnList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();
        

        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "returnHeaderTypeId", ReturnHeaderTypeSingleton.getValueList(), null, options.getReturnHeaderType());
        panelReturnHeaderType.setLayout(new BorderLayout());
        panelReturnHeaderType.add(BorderLayout.CENTER, panel);
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "returnId");
        panelReturnId.setLayout(new BorderLayout());
        panelReturnId.add(BorderLayout.CENTER, panel);
        if(options.isSalesReturn()){
            options.put("keyId", "fromPartyId");
        }
        else{
            options.put("keyId", "toPartyId");
        }        
        
        partyPickerEditPanel = (PartyPickerEditPanel) ReportBaseMain.AddPartyIdSelection(filterList, options, null);
        panelReturnFromPartyId.setLayout(new BorderLayout());
        panelReturnFromPartyId.add(partyPickerEditPanel, BorderLayout.CENTER);
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "billingAccountId");
        panelBillingAccount.setLayout(new BorderLayout());
        panelBillingAccount.add(BorderLayout.CENTER, panel);        

       panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", StatusSingleton.getValueList("ORDER_RETURN_STTS"), null, null);
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, panel);
        
       panel = ReportBaseMain.AddFindDateSelection(filterList, "entryDate", options ,  null);
        panelDateSelection.setLayout(new BorderLayout());
        panelDateSelection.add(BorderLayout.CENTER, panel);
                
/*        
        List<PaymentType> findProductList = PaymentTypeSingleton.getValueList();
        paymentTypeComboModel = new JComboBoxSelectionModel<PaymentType>(findProductList, new PaymentTypeListCellRenderer());
        panelReturnHeaderType.setLayout(new BorderLayout());        
        panelReturnHeaderType.add(BorderLayout.CENTER, paymentTypeComboModel.jComboBox);
        
        List<StatusItem> findStatusPaymentList = StatusSingleton.getValueList("PMNT_STATUS");     
        paymentStatusItemComboModel = new JComboBoxSelectionModel<StatusItem>(findStatusPaymentList,new StatusItemTypeListCellRenderer());
*/
//        panelStatusId.setLayout(new BorderLayout());
 //       panelStatusId.add(BorderLayout.CENTER, paymentStatusItemComboModel.jComboBox);                
                
//        tableReceiveInv.setModel(paymentTableModel);
//        tableReceiveInv.setFillsViewportHeight(true);

//        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Return Search Option");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelReturnList, "Return List");    
//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        
        
    }

    public void setReturnListModel(ListAdapterListModel<ReturnHeaderComposite> orderListModel) {

        tablePanel.setListModel(orderListModel);
    }
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }
    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }
    public List<EntityCondition> getWhereClauseCond() {
        return ReportBaseMain.getWhereClauseCond(filterList);
    }    
    public Map<String, Object> getFindOptionList(){
        
  boolean findVal = false;
              
        Map<String, Object> findOptionList = FastMap.newInstance();   
        
        if(paymentTypeComboModel.comboBoxModel.getSelectedItem()!=null){
            PaymentType invoiceType = (PaymentType) paymentTypeComboModel.comboBoxModel.getSelectedItem();
            findOptionList.put("paymentTypeId", invoiceType.getpaymentTypeId());            
            findVal = true;            
        }

        if(paymentStatusItemComboModel.comboBoxModel.getSelectedItem()!=null){
            StatusItem statusType = (StatusItem) paymentStatusItemComboModel.comboBoxModel.getSelectedItem();
            findOptionList.put("statusId", statusType.getstatusId());            
            findVal = true;            
        }
        
  /*     if(UtilValidate.isNotEmpty(txtPaymentId.getText())){
            findOptionList.put("paymentId", txtPaymentId.getText());            
                        findVal = true;
        }        
       if(UtilValidate.isNotEmpty(txtFromPartyIdTextField.getText())){
            findOptionList.put("partyIdFrom", txtFromPartyIdTextField.getText());            
                        findVal = true;
        }        
       */
       if(findVal==false){
           findOptionList.put("noConditionFind","Y");
       }

//       if(UtilValidate.isNotEmpty(txtToPartyId.getText())){
//            findOptionList.put("partyIdTo", txtToPartyId.getText());            
//        }        
        
//       if(UtilValidate.isNotEmpty(txtAmount.getText())){
//            findOptionList.put("amount", txtAmount.getText());            
//        }        
       
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
        tablePanel.jTable.getColumn("Return ID").setCellEditor(productTreeActionTableCellEditor);
    
        for (int i = 0; i < ReturnTableModel.Columns.values().length; i++) {
            
            ReturnTableModel.Columns[] columns = ReturnTableModel.Columns.values();
            ReturnTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
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
    
    public ReturnHeaderComposite getSelectedReturn() {
        return tablePanel.listModelSelection.getSelection();
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

        jPanel2 = new javax.swing.JPanel();
        panelLookupInvoice = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelReturnHeaderType = new javax.swing.JPanel();
        panelReturnId = new javax.swing.JPanel();
        panelReturnFromPartyId = new javax.swing.JPanel();
        panelBillingAccount = new javax.swing.JPanel();
        panelStatus = new javax.swing.JPanel();
        panelDateSelection = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelReturnList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Return Search Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Return Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Return HeaderType Id:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Return From Party Id:");

        btnFind.setText("Find");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Billing Account:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Return Date:");

        panelReturnHeaderType.setMaximumSize(new java.awt.Dimension(400, 0));
        panelReturnHeaderType.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelReturnHeaderTypeLayout = new javax.swing.GroupLayout(panelReturnHeaderType);
        panelReturnHeaderType.setLayout(panelReturnHeaderTypeLayout);
        panelReturnHeaderTypeLayout.setHorizontalGroup(
            panelReturnHeaderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelReturnHeaderTypeLayout.setVerticalGroup(
            panelReturnHeaderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelReturnId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelReturnId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelReturnIdLayout = new javax.swing.GroupLayout(panelReturnId);
        panelReturnId.setLayout(panelReturnIdLayout);
        panelReturnIdLayout.setHorizontalGroup(
            panelReturnIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelReturnIdLayout.setVerticalGroup(
            panelReturnIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelReturnFromPartyId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelReturnFromPartyId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelReturnFromPartyIdLayout = new javax.swing.GroupLayout(panelReturnFromPartyId);
        panelReturnFromPartyId.setLayout(panelReturnFromPartyIdLayout);
        panelReturnFromPartyIdLayout.setHorizontalGroup(
            panelReturnFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelReturnFromPartyIdLayout.setVerticalGroup(
            panelReturnFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelBillingAccount.setMaximumSize(new java.awt.Dimension(400, 0));
        panelBillingAccount.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelBillingAccountLayout = new javax.swing.GroupLayout(panelBillingAccount);
        panelBillingAccount.setLayout(panelBillingAccountLayout);
        panelBillingAccountLayout.setHorizontalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelBillingAccountLayout.setVerticalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelStatus.setMaximumSize(new java.awt.Dimension(400, 0));
        panelStatus.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelDateSelection.setMaximumSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelDateSelectionLayout = new javax.swing.GroupLayout(panelDateSelection);
        panelDateSelection.setLayout(panelDateSelectionLayout);
        panelDateSelectionLayout.setHorizontalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
        );
        panelDateSelectionLayout.setVerticalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelReturnHeaderType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReturnId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReturnFromPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBillingAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDateSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(162, 162, 162))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelBillingAccount, panelReturnFromPartyId, panelReturnHeaderType, panelReturnId, panelStatus});

        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelReturnHeaderType, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReturnId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReturnFromPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelBillingAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelDateSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(btnFind))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelBillingAccount, panelDateSelection, panelReturnFromPartyId, panelReturnHeaderType, panelReturnId, panelStatus});

        jPanel2.add(panelLookupInvoice, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelReturnList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Return List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelReturnList.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelReturnList, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelReturnList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelBillingAccount;
    private javax.swing.JPanel panelDateSelection;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelReturnFromPartyId;
    private javax.swing.JPanel panelReturnHeaderType;
    private javax.swing.JPanel panelReturnId;
    private javax.swing.JPanel panelReturnList;
    private javax.swing.JPanel panelStatus;
    // End of variables declaration//GEN-END:variables
}
