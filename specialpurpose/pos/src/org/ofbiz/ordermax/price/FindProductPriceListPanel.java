/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
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
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.PartyListCellRenderer;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ProductPriceTypeListCellRenderer;
import mvc.model.table.FindProductPriceTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindProductPriceListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;

    private final JTextField txtPartyIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;
    public GenericTableModelPanel<ProductPriceComposite, FindProductPriceTableModel> tablePanel = null;

//    private FindProductPriceTableModel productPriceTableModel = new FindProductPriceTableModel();
//    private ListAdapterListModel<SupplierProductComposite> productPriceListModel = new ListAdapterListModel<SupplierProductComposite>();
//    private FindProductPriceTableModel productPriceTableModel = new FindProductPriceTableModel();
//    private ListAdapterListModel<ProductPriceComposite> productPriceListModel = new ListAdapterListModel<ProductPriceComposite>();
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(descCondComboBoxModel);
      ProductPickerEditPanel panelProductIdPicker;
      ControllerOptions controllerOptions = null;
      JGenericComboBoxSelectionModel<ProductStoreGroup> productStoreGroupComboModel = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindProductPriceListPanel(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;
//        this.removeAll();
//        this.setLayout(new BorderLayout());

//        jPanel4.removeAll();

  //      JPanel test = createDetailsPanel();
  //      this.add(BorderLayout.BEFORE_FIRST_LINE, test);
  //      this.add(BorderLayout.CENTER, panelIResultList);
        panelProductIdPicker = new ProductPickerEditPanel(controllerOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panelProductIdPicker);
        
        List<ProductPriceType> findProductPriceType = ProductPriceTypeSingleton.getValueList();
        ProductPriceType tempProductPriceType = new ProductPriceType();
        tempProductPriceType.setdescription("<All>");
        tempProductPriceType.setproductPriceTypeId(null);
        findProductPriceType.add(0, tempProductPriceType);

        productPriceTypeComboModel = new JComboBoxSelectionModel<ProductPriceType>(findProductPriceType,
                new ProductPriceTypeListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY));
        panelPriceType.setLayout(new BorderLayout());
        panelPriceType.add(BorderLayout.CENTER, productPriceTypeComboModel.jComboBox);        
        
        
        List<ProductStoreGroup> productStoreGroups = ProductStoreGroupSingleton.getValueList();
        ProductStoreGroup productStoreGroup = new ProductStoreGroup();
        productStoreGroup.setdescription("All");
        productStoreGroup.setproductStoreGroupId("ANY");
        productStoreGroups.add(0, productStoreGroup);                
        productStoreGroupComboModel = new JGenericComboBoxSelectionModel<ProductStoreGroup>(productStoreGroups);   
        
        panelStoreGroup.setLayout(new BorderLayout());
        panelStoreGroup.add(BorderLayout.CENTER, productStoreGroupComboModel.jComboBox);        
            
        tablePanel = new GenericTableModelPanel<ProductPriceComposite, FindProductPriceTableModel>(new FindProductPriceTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);

        setupEditOrderTable();

//        ComponentBorder.doubleRaisedLoweredBevelBorder(test, "Price Selection");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Price Detail List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<ProductPriceComposite> orderListModel) {
        tablePanel.setListModel(orderListModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtPartyIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getPartyActionTableCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < FindProductPriceTableModel.Columns.values().length; i++) {
            FindProductPriceTableModel.Columns[] columns = FindProductPriceTableModel.Columns.values();
            FindProductPriceTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (FindProductPriceTableModel.Columns.PRODUCTID == column) {
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
            /*else if(FindProductPriceTableModel.Columns == column){
             tableReceiveInv.setSurrendersFocusOnKeystroke(true);
             txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
             editor.setClickCountToStart(0);
             orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(orderRowColumnClickActionTableCellEditor);                   
             } */
        }
    }

    String getSupplierPartyId() {
        String partyId = null;
        if (accountComboModel.jComboBox.getSelectedIndex() > -1) {
            Account account = accountComboModel.comboBoxModel.getElementAt(accountComboModel.jComboBox.getSelectedIndex());
            partyId = account.getParty().getpartyId();
        }
        return partyId;
    }

    String getProductId() {
        String productId = null;
        if (productComboModel.jComboBox.getSelectedIndex() > -1) {
            Product product = productComboModel.comboBoxModel.getElementAt(productComboModel.jComboBox.getSelectedIndex());
            productId = product.getproductId();
        }
        return productId;
    }

    String getProductPriceTypeId() {
        String productPriceTypeId = null;
        if (productPriceTypeComboModel.jComboBox.getSelectedIndex() > -1) {
            ProductPriceType productPriceType = productPriceTypeComboModel.comboBoxModel.getElementAt(productPriceTypeComboModel.jComboBox.getSelectedIndex());
            productPriceTypeId = productPriceType.getproductPriceTypeId();
        }
        return productPriceTypeId;
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();
        boolean showAll = true;
        if (panelProductIdPicker.textIdField.getText() != null && panelProductIdPicker.textIdField.getText().isEmpty() == false) {
            findOptionList.put("productId", panelProductIdPicker.textIdField.getText());
            showAll = false;
        }

//        if (txtDescription.getText() != null && txtDescription.getText().isEmpty() == false) {
//            findOptionList.put("description", txtDescription.getText());
//            showAll = false;
//        }
        
        if (productPriceTypeComboModel.jComboBox.getSelectedItem() != null) {
            ProductPriceType partyType = (ProductPriceType) productPriceTypeComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getproductPriceTypeId())) {
                findOptionList.put("productPriceTypeId", partyType.getproductPriceTypeId());
                showAll = false;
            }
        }        
        
        if (showAll == true) {
            findOptionList.put("showAll", "Y");
            findOptionList.put("noConditionFind", "Y");
        }

        findOptionList.put("lookupFlag", "Y");
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel4 = new CollapsiblePanel();
        btnFind = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        panelProductId = new javax.swing.JPanel();
        panelStoreGroup = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelPriceType = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(678, 100));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWidths = new int[] {0, 6, 0, 6, 0};
        jPanel4Layout.rowHeights = new int[] {0, 3, 0, 3, 0, 3, 0};
        jPanel4.setLayout(jPanel4Layout);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(btnFind, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel3, gridBagConstraints);

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(panelProductId, gridBagConstraints);

        panelStoreGroup.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout panelStoreGroupLayout = new javax.swing.GroupLayout(panelStoreGroup);
        panelStoreGroup.setLayout(panelStoreGroupLayout);
        panelStoreGroupLayout.setHorizontalGroup(
            panelStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStoreGroupLayout.setVerticalGroup(
            panelStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(panelStoreGroup, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Price Type Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel4, gridBagConstraints);

        panelPriceType.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout panelPriceTypeLayout = new javax.swing.GroupLayout(panelPriceType);
        panelPriceType.setLayout(panelPriceTypeLayout);
        panelPriceTypeLayout.setHorizontalGroup(
            panelPriceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPriceTypeLayout.setVerticalGroup(
            panelPriceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(panelPriceType, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Store Group:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel4.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jPanel4, gridBagConstraints);

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelIResultList.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panelIResultList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    public JComboBoxSelectionModel<Account> accountComboModel = null;
    public JGenericComboBoxSelectionModel<Product> productComboModel = null;
    public JComboBoxSelectionModel<ProductPriceType> productPriceTypeComboModel = null;

    private JPanel createDetailsPanel() {

        JPanel panel = new CollapsiblePanel();

        JLabel productIdLabel = new JLabel("Product Id:");
        JLabel productPriceIdLabel = new JLabel("Price Type:");
        JLabel partyIdLabel = new JLabel("Supplier Name:");

        /*		JLabel anAttributeLabel = jLabel7;//new JLabel("An Attribute");
         JLabel dateFieldLabel = jLabel3;//new JLabel("Date Field");
         JLabel anAttLabel = new JLabel("An Att");
         JLabel anotherAttLabel = new JLabel("Another Att");
         JLabel anotherAtt2Label = new JLabel("Another Att");
         */
        PartyListCellRenderer pccRender = new PartyListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);

        accountComboModel = new JComboBoxSelectionModel<Account>(PartyListSingleton.getSupplierValueList(), pccRender);
        List<Product> findProductList = ProductSingleton.getValueList();
        Product temp = new Product();
        temp.setdescription("<All>");
        temp.setproductId(null);
        findProductList.add(0, temp);
        productComboModel = new JGenericComboBoxSelectionModel<Product>(findProductList);


        JComboBox<Account> jComboBox = accountComboModel.jComboBox;
        JComboBox<ProductPriceType> jProductPriceTypeComboBox = productPriceTypeComboModel.jComboBox;

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        int i = 0;

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.NORTHEAST;

        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(productIdLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(productComboModel.jComboBox, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(productPriceIdLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(jProductPriceTypeComboBox, gbc);

        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(partyIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(jComboBox, gbc);

        i++;

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnFind, gbc);

        return panel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelPriceType;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelStoreGroup;
    // End of variables declaration//GEN-END:variables
}
