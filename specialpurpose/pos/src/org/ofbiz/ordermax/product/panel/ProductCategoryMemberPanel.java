/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.table.ProductCategoryMemberTableModel;

import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.ProductComposite;

import org.ofbiz.ordermax.entity.ProductCategoryMember;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryMemberPanel extends javax.swing.JPanel {

    public final JTextField txtCategoryIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;

    private ProductCategoryMemberTableModel productCategoryMemberTableModel = new ProductCategoryMemberTableModel();
    private ListAdapterListModel<ProductCategoryMember> accountListModel = new ListAdapterListModel<ProductCategoryMember>();
    private ListModelSelection<ProductCategoryMember> listModelSelection = new ListModelSelection<ProductCategoryMember>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();

    //private ListAdapterListModel<ProductComposite> invoiceCompositeListModel = new ListAdapterListModel<ProductComposite>();
//    public JComboBoxSelectionModel<Account> productIdComboModel = null;
//    public JComboBoxSelectionModel<Account> productNameComboModel = null;

    boolean showComboKeys = false;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public ProductCategoryMemberPanel() {
        initComponents();
        tableReceiveInv.setModel(productCategoryMemberTableModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableReceiveInv.setSelectionModel(selectionModel);

        setupEditOrderTable();

  /*      List<Account> findPartyList = PartyListSingleton.getCustomerValueList();
        Account temp = new Account();
        temp.newComposite();
        temp.getPartyGroup().setgroupName("All");
        temp.getParty().setpartyId("All");
        findPartyList.add(0, temp);
        productIdComboModel = new JComboBoxSelectionModel<Account>(findPartyList,
                new PartyListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY), DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY);
*/
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Product Category Member List");
    }

    public void setProductCategoryMemberList(ListAdapterListModel<ProductCategoryMember> listModel) {
        //    accountListModel.setModel(listModel);
        productCategoryMemberTableModel.setListModel(listModel);
        listModelSelection.setListModels(listModel, selectionModel);
//        accountCompositeTableModel.setListModel(listModel);
    }

    public ProductCategoryMember getSelectedAccount() {
        return listModelSelection.getSelection();
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtCategoryIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getCategoryActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < ProductCategoryMemberTableModel.Columns.values().length; i++) {
            ProductCategoryMemberTableModel.Columns[] columns = ProductCategoryMemberTableModel.Columns.values();
            ProductCategoryMemberTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (ProductCategoryMemberTableModel.Columns.CATEGORYID == column) {
                tableReceiveInv.setSurrendersFocusOnKeystroke(true);
                txtCategoryIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtCategoryIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
        }
    }

    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void clearDialogFields() {

    }

    public void setDialogField() {
        if (productComposite.getProductCategoryList() != null) {
            setProductCategoryMemberList(productComposite.getProductCategoryList());
        }
    }

    public void getDialogField() {
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
        jPanel3 = new javax.swing.JPanel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Category List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
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
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JTable tableReceiveInv;
    // End of variables declaration//GEN-END:variables
}
