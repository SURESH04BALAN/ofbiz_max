/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.feature;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastList;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductFeature;
import org.ofbiz.ordermax.entity.ProductFeatureApplType;
import org.ofbiz.ordermax.entity.ProductFeatureCategory;
import org.ofbiz.ordermax.entity.ProductFeatureType;
import org.ofbiz.ordermax.entity.ProductStoreGroup;

/**
 *
 * @author administrator
 */
public class ProductFeatureEditTablePanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    static final String module = ProductFeatureEditTablePanel.class.getName();
    /**
     * Creates new form ProductPriceEditTablePanel
     */

    public JGenericComboBoxSelectionModel<ProductStoreGroup> productStoreGroupModel = null;
//    protected Map<String, String> departmentValMap = new TreeMap<String, String>();
    protected List<String> departmentListBidingCombo = FastList.newInstance();
    //protected GenericValueMapComboBox departmentCombo = null;
    //protected GenericValueMapComboBox brandCombo = null;
    //protected GenericValueMapComboBox productCombo = null;
    //protected GenericValueMapComboBox supplierIdCombo = null;
    //private SimpleTableModel m_simpleTableModel;
    //protected PriceTreeMap priceTreeMap = null;
    protected XuiSession session = null;
    public JGenericComboBoxSelectionModel<ProductFeatureType> productFeatureTypeModel = null;
    public JGenericComboBoxSelectionModel<ProductFeatureCategory> productFeatureCategoryModel = null;

    // List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
    //RXTable jTable1 = null;
    JScrollPane scrollPane = null;
    //ProductFeatureAppl;
    public GenericTableModelPanel<ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate, ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel> tablePanel = null;

    public ProductFeatureEditTablePanel(XuiSession session) {
        initComponents();
//        this.priceTreeMap = new PriceTreeMap(session);
        //chnage our table
  /*      jTable1 = new RXTable() {
         public boolean getScrollableTracksViewportWidth() {
         return getPreferredSize().width < getParent().getWidth();
         }
         };

         jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         jTable1.setSelectAllForEdit(true);
         jTable1.setSurrendersFocusOnKeystroke(true);
         scrollPane = new JScrollPane(jTable1);
         jTable1.setFillsViewportHeight(true);
         panelTable.setLayout(new GridLayout(0, 1));
         panelTable.add(scrollPane);
         */

        productFeatureTypeModel = new JGenericComboBoxSelectionModel<ProductFeatureType>(panelProductFeatureType, ProductFeatureTypeSingleton.getValueList());
        productFeatureCategoryModel = new JGenericComboBoxSelectionModel<ProductFeatureCategory>(panelProductFeatureCategoryType, ProductFeatureCategorySingleton.getValueList());

        setupEditOrderTable();

        this.session = session;
       // List<ProductStoreGroup> genFacilityList = ProductStoreGroupSingleton.getValueList();//PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());
        // productStoreGroupModel = new JGenericComboBoxSelectionModel<ProductStoreGroup>(panelProductFeatureCategoryType, genFacilityList);

        /*        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());
         facilityIdCombo = new GenericValueComboBox(cbFacilityName, genFacilityList, "Facility", "facilityId", "facilityName");
         Map<String, String> suppliers = PosProductHelper.getSuppliers(session.getDelegator());
         */
        //    JComboBox comboBox1 = new JComboBox();
//        supplierIdCombo = new GenericValueMapComboBox(comboBox1, suppliers);
        //      supplierIdCombo.loadCombo();
        //    DefaultCellEditor dce1 = new DefaultCellEditor(comboBox1);
        //      editors.add(dce1);
        /*final JTextField textField = new JTextField();
         textField.setBorder(BorderFactory.createEmptyBorder());

         DefaultCellEditor editor = new PositiveDecimalCellEditor(textField);
         editor.setClickCountToStart(1);
         textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
         textField.getActionMap().put("check", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         tableCellAction(textField, tablePanel.jTable.getSelectedRow(), tablePanel.jTable.getSelectedColumn());
         }
         });

         textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "checkTab");
         textField.getActionMap().put("checkTab", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         tableCellAction(textField, tablePanel.jTable.getSelectedRow(), tablePanel.jTable.getSelectedColumn());
         }
         });

         textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "checkShiftTab");
         textField.getActionMap().put("checkShiftTab", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         tableCellAction(textField, tablePanel.jTable.getSelectedRow(), tablePanel.jTable.getSelectedColumn());
         }
         });
         editors.add(editor);*/
    }

//    public void setBrandMap(Map<String, String> brandValMap) {
//        brandCombo = new GenericValueMapComboBox(cdBrandName, brandValMap);
//        brandCombo.loadCombo();
//    }
    ListAdapterListModel<Product> productListModel = new ListAdapterListModel<Product>();

    public void setProductList(ListAdapterListModel<Product> products) {
        productListModel = products;

    }

    ListAdapterListModel<ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate> priceList = new ListAdapterListModel<ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate>();

    public void setProductPriceList(ListAdapterListModel<ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate> productPriceList) {
        priceList = productPriceList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTable = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbProductName = new javax.swing.JComboBox();
        btnLoadPrices = new javax.swing.JButton();
        panelProductFeatureCategoryType = new javax.swing.JPanel();
        panelProductFeatureType = new javax.swing.JPanel();
        btnLoadNew = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(800, 700));
        setPreferredSize(new java.awt.Dimension(800, 700));
        setLayout(new java.awt.BorderLayout());

        panelTable.setPreferredSize(new java.awt.Dimension(900, 490));

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 967, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        add(panelTable, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(900, 60));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Product Feature Type:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Product Feature Category Type:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Product Name:");

        btnLoadPrices.setText("Load");

        javax.swing.GroupLayout panelProductFeatureCategoryTypeLayout = new javax.swing.GroupLayout(panelProductFeatureCategoryType);
        panelProductFeatureCategoryType.setLayout(panelProductFeatureCategoryTypeLayout);
        panelProductFeatureCategoryTypeLayout.setHorizontalGroup(
            panelProductFeatureCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        panelProductFeatureCategoryTypeLayout.setVerticalGroup(
            panelProductFeatureCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelProductFeatureTypeLayout = new javax.swing.GroupLayout(panelProductFeatureType);
        panelProductFeatureType.setLayout(panelProductFeatureTypeLayout);
        panelProductFeatureTypeLayout.setHorizontalGroup(
            panelProductFeatureTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        panelProductFeatureTypeLayout.setVerticalGroup(
            panelProductFeatureTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        btnLoadNew.setText("New Feature");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(panelProductFeatureType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductFeatureCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadPrices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadNew))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLoadNew, btnLoadPrices});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductFeatureCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLoadPrices)
                        .addComponent(btnLoadNew))
                    .addComponent(panelProductFeatureType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnLoadNew;
    public javax.swing.JButton btnLoadPrices;
    private javax.swing.JComboBox cbProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelProductFeatureCategoryType;
    private javax.swing.JPanel panelProductFeatureType;
    private javax.swing.JPanel panelTable;
    // End of variables declaration//GEN-END:variables

    public void refreshScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
        /* Debug.logWarning("saave item.", module);
         for (ProductPriceBulkUpdate paymentComposite : priceList.getList()) {
         ProductPrice prodDefPrice = paymentComposite.getDefaultPrice().get(0);
         ProductPrice prodListPrice = paymentComposite.getListPrice().get(0);
         ProductPrice prodPrice = paymentComposite.getAvgCost().get(0);
         List<GenericValue> toStore = new ArrayList<GenericValue>();
         if (prodDefPrice.isGenericValueSet()) {
         prodDefPrice.getGenericValue();
         toStore.add(prodDefPrice.getGenericValueObj());
         } else if (prodDefPrice.getprice().equals(BigDecimal.ZERO) == false) {
         toStore.add(prodDefPrice.createNewGenericValueObj(session.getDelegator()));
         }

         if (prodListPrice.isGenericValueSet()) {
         prodListPrice.getGenericValue();
         toStore.add(prodListPrice.getGenericValueObj());
         } else if (prodListPrice.getprice().equals(BigDecimal.ZERO) == false) {
         toStore.add(prodListPrice.createNewGenericValueObj(session.getDelegator()));
         }

         if (prodPrice.isGenericValueSet()) {
         prodPrice.getGenericValue();
         toStore.add(prodPrice.getGenericValueObj());
         } else if (prodPrice.getprice().equals(BigDecimal.ZERO) == false) {
         toStore.add(prodPrice.createNewGenericValueObj(session.getDelegator()));
         }

         try {

         TransactionUtil.begin();

         session.getDelegator().storeAll(toStore);
         Debug.logWarning("store was successful.", module);
         TransactionUtil.commit();

         } catch (GenericEntityException e) {
         TransactionUtil.rollback();
         Debug.logError(e, "Failed to import product inventory [" + "]. Error stack follows.", module);
         } catch (Exception e) {
         TransactionUtil.rollback();
         Debug.logWarning(e, "Import of product inventory [" + "] was unsuccessful.", module);
         }

         }*/

    }
    static int NUM_COLS = 5;

    public void loadItem() {
        setupEditOrderTable();
    }

    final void setupEditOrderTable() {
        panelTable.removeAll();
        tablePanel = new GenericTableModelPanel<ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate, ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel>(new ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel(featureList));
        panelTable.setLayout(new BorderLayout());
        panelTable.add(BorderLayout.CENTER, tablePanel);

        tablePanel.setListModel(priceList);

        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);

        for (int i = 0; i < ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns.values().length; i++) {

            ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns[] columns = ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns.values();
            ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            Debug.logError("setProductFeaturesType " + i + " column index: " + ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns.ProductFeatureId.getColumnIndex(), "module");
            if (i == ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns.ProductFeatureId.getColumnIndex()) {
                Debug.logError("setProductFeaturesType ", "module");
                setProductFeaturesType(tablePanel.jTable, col);
            } else if (i == ProductFeatureMaintainTreeController.ProductPriceBulkEditTableModel.Columns.ProductFeatureApplTypeId.getColumnIndex()) {
                Debug.logError("setProductFeaturesType ", "module");
                setProductFeaturesApplType(tablePanel.jTable, col);
            } else if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer());
            }
        }

        tablePanel.jTable.repaint();
    }

    Map<String, ProductFeature> featureList = null;

    public void setProductFeaturesType(JTable table, TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        if (featureList != null) {
            List<String> features = new ArrayList<String>();
            features.addAll(featureList.keySet());
            Collections.sort(features, Collator.getInstance(Locale.getDefault()));

            for (String val : features) {
                comboBox.addItem(val);
            }
        }
        
        
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public void setProductFeaturesApplType(JTable table, TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        List<ProductFeatureApplType> list = ProductFeatureApplTypeSingleton.getValueList();
       if (list != null) {
            List<String> features = new ArrayList<String>();
            for (ProductFeatureApplType val : list) {
                features.add(val.getdescription());
            }      
            
            Collections.sort(features, Collator.getInstance(Locale.getDefault()));

            for (String val : features) {
                comboBox.addItem(val);
            }
        }
       
       /* if (list != null) {
            for (ProductFeatureApplType val : list) {
                comboBox.addItem(val.getdescription());
            }
        }*/
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    void tableCellAction(final JTextField textField, int row, int col) {
        Debug.logInfo("tableCellAction product: " + textField.getText(), module);
        int selectedRowIndex = tablePanel.jTable.getSelectedRow();
        int selectedColumnIndex = tablePanel.jTable.getSelectedColumn();
        Object selectedObject = (Object) tablePanel.jTable.getModel().getValueAt(selectedRowIndex, selectedColumnIndex).toString();
        Debug.logInfo("tableCellAction selectedObject VALUE: " + selectedObject, module);
        /*        if (itemJTable.getSelectedColumn() == OrderEntryKeyTableModel.ORDER_PROD_ID_INDEX && textField.getText().isEmpty() == false) {
         processProductIdTextField(textField, row);
         } else if (itemJTable.getSelectedColumn() == OrderEntryKeyTableModel.ORDER_QTY_INDEX && textField.getText().isEmpty() == false) {
         processQtyTextField(textField, itemJTable.getSelectedRow());
         } else if (itemJTable.getSelectedColumn() == OrderEntryKeyTableModel.ORDER_PRICE_INDEX && textField.getText().isEmpty() == false) {
         processPriceTextField(textField, itemJTable.getSelectedRow());
         } else { */
        textField.postActionEvent(); //inform the editor
        //}
        Debug.logInfo(" tableModel.hasEmptyRow() 2", module);
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
     class InteractiveRenderer extends DefaultTableCellRenderer {

     protected int interactiveColumn;

     public InteractiveRenderer(int interactiveColumn) {
     this.interactiveColumn = interactiveColumn;
     }

     @Override
     public Component getTableCellRendererComponent(JTable table,
     Object value, boolean isSelected, boolean hasFocus, int row,
     int column) {
     Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
     if (column == interactiveColumn && hasFocus) {
     if ((tablePanel.tableModel.getRowCount() - 1) == row
     && !tablePanel.tableModel.hasEmptyRow()) {
     //                    m_simpleTableModel.addEmptyRow(EmptyRow, "");
     //                   table.setPreferredSize(new java.awt.Dimension(500, OrderDetailTableForm.this.m_simpleTableModel.getRowCount() * itemJTable.getRowHeight()));                   
     }
     scrollToVisible(table, row, PROD_ID_INDEX);
     //                highlightLastRow(row);
     //        rowChanged(row);
     }
     return c;
     }
     }
     */

    public void setParentItem(java.lang.Object parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    protected boolean isModified = false;

    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;

    }
    /*
     class SimpleTableModel extends AbstractTableModel {

     public String[] m_colNames = {"Product Id", "Name", "Def Price", "List Price", "Avg Cost", "Supplier Id", "Supp Prod Id", "Last Price", "Scan Code", ""};
     public Class[] m_colTypes = {String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class, String.class, BigDecimal.class, String.class, String.class};
     List<SupplierProductAndListPriceData> m_macDataVector = new ArrayList<SupplierProductAndListPriceData>();

     public SimpleTableModel(List<SupplierProductAndListPriceData> data) {
     super();
     m_macDataVector.add(new SupplierProductAndListPriceData());
     m_macDataVector.addAll(data);

     }

     public int getColumnCount() {
     return m_colNames.length;
     }

     public int getRowCount() {
     return m_macDataVector.size();
     }

     public void setAllRows(Object value, int col) {

     for (int i = 1; i < m_macDataVector.size(); ++i) {
     SupplierProductAndListPriceData macData = m_macDataVector.get(i);
     switch (col) {
     case PROD_ID_INDEX:
     macData.setProductId((String) value);
     break;
     case PROD_NAME:
     macData.setProductInternalName((String) value);
     break;
     case PROD_DEFAULT_PRICE:
     macData.setListPrice((BigDecimal) value);
     break;
     case PROD_LIST_PRICE:
     macData.setDefaultPrice((BigDecimal) value);
     break;
     case PROD_AVG_COST:
     macData.setAvgCost((BigDecimal) value);
     break;
     case PROD_SUPPLIER_ID:
     macData.setSupplierId((String) value);
     break;
     case PROD_SUPPLIER_PROD_ID:
     macData.setProductId((String) value);
     break;
     case PROD_SUPPLIER_LAST_PRICE:
     macData.setLastPrice((BigDecimal) value);
     break;
     case PROD_SUPPLIER_SCAN_CODE:
     macData.setScanCode((String) value);
     break;
     }
     fireTableCellUpdated(i, col);
     }
     }

     @Override
     public void setValueAt(Object value, int row, int col) {
     if (row == 0) {
     setAllRows(value, col);
     return;
     }

     SupplierProductAndListPriceData macData = m_macDataVector.get(row);

     switch (col) {
     case PROD_ID_INDEX:
     macData.setProductId((String) value);
     break;
     case PROD_NAME:
     macData.setProductInternalName((String) value);
     break;
     case PROD_DEFAULT_PRICE:
     macData.setListPrice((BigDecimal) value);
     break;
     case PROD_LIST_PRICE:
     macData.setDefaultPrice((BigDecimal) value);
     break;
     case PROD_AVG_COST:
     macData.setAvgCost((BigDecimal) value);
     break;
     case PROD_SUPPLIER_ID:
     macData.setSupplierId((String) value);
     break;
     case PROD_SUPPLIER_PROD_ID:
     macData.setProductId((String) value);
     break;
     case PROD_SUPPLIER_LAST_PRICE:
     macData.setLastPrice((BigDecimal) value);
     break;
     case PROD_SUPPLIER_SCAN_CODE:
     macData.setScanCode((String) value);
     break;

     }
     fireTableCellUpdated(row, col);
     }

     @Override
     public String getColumnName(int col) {
     return m_colNames[col];
     }

     @Override
     public Class getColumnClass(int col) {
     return m_colTypes[col];
     }

     public Object getValueAt(int row, int col) {
     Debug.logInfo("getValueAt row: " + row + " column: " + col, module);
     if (row == 0) {
     if (col == PROD_ID_INDEX) {
     return "DEFAULT";
     }

     return new String();
     }

     SupplierProductAndListPriceData macData = m_macDataVector.get(row);

     switch (col) {
     case PROD_ID_INDEX:
     return macData.getProductId();
     case PROD_NAME:
     return macData.getProductInternalName();
     case PROD_DEFAULT_PRICE:
     return macData.getListPrice();
     case PROD_LIST_PRICE:
     return macData.getDefaultPrice();
     case PROD_AVG_COST:
     return macData.getAvgCost();
     case PROD_SUPPLIER_ID:
     return macData.getSupplierId();
     case PROD_SUPPLIER_PROD_ID:
     return macData.getProductId();
     case PROD_SUPPLIER_LAST_PRICE:
     return macData.getLastPrice();
     case PROD_SUPPLIER_SCAN_CODE:
     return macData.getScanCode();

     }

     return new String();
     }

     @Override
     public boolean isCellEditable(int rowIndex, int mColIndex) {
     return true;
     }

     public boolean hasEmptyRow() {
     if (m_macDataVector.isEmpty()) {
     return false;
     }
     return true;
     }
     }
     */

    public Map<String, ProductFeature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(Map<String, ProductFeature> featureList) {
        this.featureList = featureList;
    }
}
