/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

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
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ReturnHeaderType;
import org.ofbiz.ordermax.entity.ShoppingList;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindShoppingCartListPanel extends javax.swing.JPanel {
   
    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

//    private OrderReturnTableModel returnTableModel = new OrderReturnTableModel();
    public GenericTableModelPanel<ShoppingList, ShoppingListTableModel> tablePanel = null;
    public JGenericComboBoxSelectionModel<ReturnHeaderType> returnHeaderTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> StatusItemComboModel = null;
    
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    
    public JComboBoxSelectionModel<ProductPriceType> productPriceTypeComboModel = null;
    
    private ListAdapterListModel<PaymentComposite> invoiceCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    public JComboBoxSelectionModel<PaymentType> paymentTypeComboModel = null;
    public JComboBoxSelectionModel<StatusItem> paymentStatusItemComboModel = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindShoppingCartListPanel(ControllerOptions options) {
        initComponents();
        
        tablePanel = new GenericTableModelPanel<ShoppingList, ShoppingListTableModel>(new ShoppingListTableModel());
        panelReturnList.setLayout(new BorderLayout());
        panelReturnList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();
        
        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "shoppingListTypeId", ShoppingListTypeSingleton.getValueList(), null, null);
        panelShoppingListType.setLayout(new BorderLayout());
        panelShoppingListType.add(BorderLayout.CENTER, panel);
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "shoppingListId");
        panelShoppingListId.setLayout(new BorderLayout());
        panelShoppingListId.add(BorderLayout.CENTER, panel);


        panel = ReportBaseMain.AddPartyIdSelection(filterList, options, null);
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(panel, BorderLayout.CENTER);
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "description");
        panelDescription.setLayout(new BorderLayout());
        panelDescription.add(BorderLayout.CENTER, panel);        

       
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

    public void setReturnListModel(ListAdapterListModel<ShoppingList> orderListModel) {

        tablePanel.setListModel(orderListModel);
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
        tablePanel.jTable.getColumn("Shopping List Id").setCellEditor(productTreeActionTableCellEditor);
    
        for (int i = 0; i < ShoppingListTableModel.Columns.values().length; i++) {
            
            ShoppingListTableModel.Columns[] columns = ShoppingListTableModel.Columns.values();
            ShoppingListTableModel.Columns column = columns[i];
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
    
    public ShoppingList getSelectedShoppingList() {
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
        panelShoppingListType = new javax.swing.JPanel();
        panelShoppingListId = new javax.swing.JPanel();
        panelPartyId = new javax.swing.JPanel();
        panelDescription = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelReturnList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Shopping List Search Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Shopping List Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Shopping List Type Id:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Party Id:");

        btnFind.setText("Find");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Description:");

        panelShoppingListType.setMaximumSize(new java.awt.Dimension(400, 0));
        panelShoppingListType.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelShoppingListTypeLayout = new javax.swing.GroupLayout(panelShoppingListType);
        panelShoppingListType.setLayout(panelShoppingListTypeLayout);
        panelShoppingListTypeLayout.setHorizontalGroup(
            panelShoppingListTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelShoppingListTypeLayout.setVerticalGroup(
            panelShoppingListTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelShoppingListId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelShoppingListId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelShoppingListIdLayout = new javax.swing.GroupLayout(panelShoppingListId);
        panelShoppingListId.setLayout(panelShoppingListIdLayout);
        panelShoppingListIdLayout.setHorizontalGroup(
            panelShoppingListIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelShoppingListIdLayout.setVerticalGroup(
            panelShoppingListIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelPartyId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelPartyId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelPartyIdLayout = new javax.swing.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelDescription.setMaximumSize(new java.awt.Dimension(400, 0));
        panelDescription.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelDescriptionLayout = new javax.swing.GroupLayout(panelDescription);
        panelDescription.setLayout(panelDescriptionLayout);
        panelDescriptionLayout.setHorizontalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelDescriptionLayout.setVerticalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelShoppingListType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelShoppingListId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(368, 368, 368))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelDescription, panelPartyId, panelShoppingListId, panelShoppingListType});

        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelShoppingListType, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelShoppingListId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelDescription, panelPartyId, panelShoppingListId, panelShoppingListType});

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
                .addComponent(panelReturnList, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelDescription;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelReturnList;
    private javax.swing.JPanel panelShoppingListId;
    private javax.swing.JPanel panelShoppingListType;
    // End of variables declaration//GEN-END:variables
}
