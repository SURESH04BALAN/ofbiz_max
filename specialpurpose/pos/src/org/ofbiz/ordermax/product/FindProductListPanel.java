/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.table.ProductCompositeTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductType;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindProductListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    public GenericTableModelPanel<ProductComposite, ProductCompositeTableModel> tablePanel = null;

    public JGenericComboBoxSelectionModel<ProductType> productTypeComboModel = null;
    public JGenericComboBoxSelectionModel<ProductCategory> productCategoryComboModel = null;

    boolean showComboKeys = false;
    protected ControllerOptions controllerOptions;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindProductListPanel(ControllerOptions controllerOptions) {
        initComponents();

        this.controllerOptions = controllerOptions;
//        tableReceiveInv.setModel(productCompositeTableModel);
        //      selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      tableReceiveInv.setSelectionModel(selectionModel);
        tablePanel = new GenericTableModelPanel<ProductComposite, ProductCompositeTableModel>(new ProductCompositeTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        List<ProductType> findProductList = ProductTypeSingleton.getValueList();
        ProductType temp = new ProductType();
        temp.setdescription("All");
        temp.setproductTypeId("All");
        findProductList.add(0, temp);

        productTypeComboModel = new JGenericComboBoxSelectionModel<ProductType>(findProductList);
        panelProductTypeId.setLayout(new BorderLayout());
        panelProductTypeId.add(BorderLayout.CENTER, productTypeComboModel.jComboBox);
        productTypeComboModel.comboBoxModel.setSelectedItem(temp);

        List<ProductCategory> nameProductList = ProductCategorySingleton.getValueList();
        ProductCategory tmp = new ProductCategory();
        tmp.setDescription("ALL");
        tmp.setProductCategoryId("All");
        nameProductList.add(0, tmp);

        productCategoryComboModel = new JGenericComboBoxSelectionModel<ProductCategory>(nameProductList);
        panelPrimaryCategory.setLayout(new BorderLayout());
        panelPrimaryCategory.add(BorderLayout.CENTER, productCategoryComboModel.jComboBox);
        productCategoryComboModel.comboBoxModel.setSelectedItem(tmp);

//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Search Options");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Product List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<ProductComposite> listModel) {

//        productCompositeTableModel.setListModel(orderListModel);
//        listModelSelection.setListModels(orderListModel, selectionModel);
        tablePanel.setListModel(listModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("PRODUCT ID").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < ProductCompositeTableModel.Columns.values().length; i++) {
            ProductCompositeTableModel.Columns[] columns = ProductCompositeTableModel.Columns.values();
            ProductCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();

        boolean showAll = true;
        if (productTypeComboModel.jComboBox.getSelectedItem() != null) {
            ProductType product = (ProductType) productTypeComboModel.jComboBox.getSelectedItem();
            if (product.getproductTypeId() != null && "All".equals(product.getproductTypeId()) == false) {
                findOptionList.put("productTypeId", product.getproductTypeId());
                showAll = false;
            }
        }

        if (productCategoryComboModel.jComboBox.getSelectedItem() != null) {
            ProductCategory productCategory = (ProductCategory) productCategoryComboModel.jComboBox.getSelectedItem();
//            findOptionList.put("productId", product.getproductId());
            if (productCategory.getProductCategoryId() != null && "All".equals(productCategory.getProductCategoryId()) == false) {
                findOptionList.put("primaryProductCategoryId", productCategory.getProductCategoryId());
                showAll = false;
            }
//            findOptionList.put("internalName", product.getinternalName());
        }

        if (UtilValidate.isNotEmpty(txtInternalName.getText())) {
            findOptionList.put("internalName", txtInternalName.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(txtProductId.getText())) {
            findOptionList.put("productId", txtProductId.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(txtBrandName.getText())) {
            findOptionList.put("brandName", txtBrandName.getText());
            showAll = false;
        }

        if (showAll == true) {
            findOptionList.put("showAll", "Y");
            findOptionList.put("noConditionFind", "Y");
        }

        findOptionList.put("lookupFlag", "Y");

        return findOptionList;
    }

//    @Override
    public Map<String, EntityCondition> getFilterByExpr() {

        Map<String, EntityCondition> findOptionList = FastMap.newInstance();

 
        if (productTypeComboModel.jComboBox.getSelectedItem() != null) {
            ProductType product = (ProductType) productTypeComboModel.jComboBox.getSelectedItem();
            if (product.getproductTypeId() != null && "All".equals(product.getproductTypeId()) == false) {
                findOptionList.put("productTypeId", EntityCondition.makeCondition("productTypeId", EntityOperator.EQUALS, product.getproductTypeId()));
            }
        }

        if (productCategoryComboModel.jComboBox.getSelectedItem() != null) {
            ProductCategory productCategory = (ProductCategory) productCategoryComboModel.jComboBox.getSelectedItem();
//            findOptionList.put("productId", product.getproductId());
            if (productCategory.getProductCategoryId() != null && "All".equals(productCategory.getProductCategoryId()) == false) {
                findOptionList.put("primaryProductCategoryId", EntityCondition.makeCondition("primaryProductCategoryId", EntityOperator.EQUALS, productCategory.getProductCategoryId()));

            }
//            findOptionList.put("internalName", product.getinternalName());
        }

        if (UtilValidate.isNotEmpty(txtInternalName.getText())) {
            findOptionList.put("internalName", EntityCondition.makeCondition("internalName", EntityOperator.LIKE, txtInternalName.getText() + "%"));

        }

        if (UtilValidate.isNotEmpty(txtProductId.getText())) {
            findOptionList.put("productId", EntityCondition.makeCondition("productId", EntityOperator.LIKE, txtProductId.getText()+ "%"));

        }

        if (UtilValidate.isNotEmpty(txtBrandName.getText())) {
            findOptionList.put("brandName", EntityCondition.makeCondition("brandName", EntityOperator.LIKE, txtBrandName.getText()+ "%"));

        }
        
        if(controllerOptions.contains("virualOnly")){
            findOptionList.put("isVirtual", EntityCondition.makeCondition("isVirtual", EntityOperator.EQUALS, "Y"));        
        }
        
        if(controllerOptions.contains("variantOnly")){
            findOptionList.put("isVariant", EntityCondition.makeCondition("isVariant", EntityOperator.EQUALS, "Y"));        
        }
        
        return findOptionList;

    }

    public ProductComposite getSelectedProduct() {
        return tablePanel.listModelSelection.getSelection();
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
        jLabel2 = new javax.swing.JLabel();
        cbProcessing1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        panelProductTypeId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        cbCreated1 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        cbCreated3 = new javax.swing.JCheckBox();
        txtProductId = new javax.swing.JTextField();
        txtBrandName = new javax.swing.JTextField();
        txtInternalName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        panelPrimaryCategory = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.GridBagLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));
        panelLookupInvoice.setPreferredSize(new java.awt.Dimension(0, 220));
        java.awt.GridBagLayout panelLookupInvoiceLayout = new java.awt.GridBagLayout();
        panelLookupInvoiceLayout.columnWidths = new int[] {0, 6, 0, 6, 0, 6, 0};
        panelLookupInvoiceLayout.rowHeights = new int[] {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0};
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Product Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel2, gridBagConstraints);

        cbProcessing1.setSelected(true);
        cbProcessing1.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbProcessing1, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Product Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel4, gridBagConstraints);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(btnFind, gridBagConstraints);

        jComboBox3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox3, gridBagConstraints);

        panelProductTypeId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelProductTypeIdLayout = new javax.swing.GroupLayout(panelProductTypeId);
        panelProductTypeId.setLayout(panelProductTypeIdLayout);
        panelProductTypeIdLayout.setHorizontalGroup(
            panelProductTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 77, Short.MAX_VALUE)
        );
        panelProductTypeIdLayout.setVerticalGroup(
            panelProductTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelProductTypeId, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Internal Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel5, gridBagConstraints);

        jComboBox4.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox4, gridBagConstraints);

        cbCreated1.setSelected(true);
        cbCreated1.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbCreated1, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Brand Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel7, gridBagConstraints);

        jComboBox6.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox6, gridBagConstraints);

        cbCreated3.setSelected(true);
        cbCreated3.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbCreated3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtProductId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtBrandName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtInternalName, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Primary Category:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel8, gridBagConstraints);

        panelPrimaryCategory.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPrimaryCategoryLayout = new javax.swing.GroupLayout(panelPrimaryCategory);
        panelPrimaryCategory.setLayout(panelPrimaryCategoryLayout);
        panelPrimaryCategoryLayout.setHorizontalGroup(
            panelPrimaryCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPrimaryCategoryLayout.setVerticalGroup(
            panelPrimaryCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelPrimaryCategory, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(panelLookupInvoice, gridBagConstraints);

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 903, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
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
    public javax.swing.JCheckBox cbCreated1;
    public javax.swing.JCheckBox cbCreated3;
    public javax.swing.JCheckBox cbProcessing1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelPrimaryCategory;
    private javax.swing.JPanel panelProductTypeId;
    private javax.swing.JTextField txtBrandName;
    private javax.swing.JTextField txtInternalName;
    private javax.swing.JTextField txtProductId;
    // End of variables declaration//GEN-END:variables
}
