/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javolution.util.FastList;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductPriceBulkEditTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.celleditors.PositiveDecimalCellEditor;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.utility.GenericValueComboBox;

/**
 *
 * @author administrator
 */
public class ProductPriceEditTablePanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    static final String module = ProductPriceEditTablePanel.class.getName();
    /**
     * Creates new form ProductPriceEditTablePanel
     */
    static final public int PROD_ID_INDEX = 0;
    static final public int PROD_NAME = 1;
    static final public int PROD_DEFAULT_PRICE = 2;
    static final public int PROD_LIST_PRICE = 3;
    static final public int PROD_AVG_COST = 4;
    static final public int PROD_SUPPLIER_ID = 5;
    static final public int PROD_SUPPLIER_PROD_ID = 6;
    static final public int PROD_SUPPLIER_LAST_PRICE = 7;
    static final public int PROD_SUPPLIER_SCAN_CODE = 8;
    final public int HIDDEN_INDEX = 9;
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
    List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
    //RXTable jTable1 = null;
    JScrollPane scrollPane = null;
    public GenericTableModelPanel<ProductPriceBulkUpdate, ProductPriceBulkEditTableModel> tablePanel = null;

    public ProductPriceEditTablePanel(XuiSession session) {
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
        tablePanel = new GenericTableModelPanel<ProductPriceBulkUpdate, ProductPriceBulkEditTableModel>(new ProductPriceBulkEditTableModel());
        panelTable.setLayout(new BorderLayout());
        panelTable.add(BorderLayout.CENTER, tablePanel);

        setupEditOrderTable();

        this.session = session;
        List<ProductStoreGroup> genFacilityList = ProductStoreGroupSingleton.getValueList();//PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());
        productStoreGroupModel = new JGenericComboBoxSelectionModel<ProductStoreGroup>(panelProductStoreGroup, genFacilityList);

        /*        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());
         facilityIdCombo = new GenericValueComboBox(cbFacilityName, genFacilityList, "Facility", "facilityId", "facilityName");
         Map<String, String> suppliers = PosProductHelper.getSuppliers(session.getDelegator());
         */
        //    JComboBox comboBox1 = new JComboBox();
//        supplierIdCombo = new GenericValueMapComboBox(comboBox1, suppliers);
        //      supplierIdCombo.loadCombo();
        //    DefaultCellEditor dce1 = new DefaultCellEditor(comboBox1);
        //      editors.add(dce1);
        final JTextField textField = new JTextField();
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
        editors.add(editor);
    }

//    public void setBrandMap(Map<String, String> brandValMap) {
//        brandCombo = new GenericValueMapComboBox(cdBrandName, brandValMap);
//        brandCombo.loadCombo();
//    }
    ListAdapterListModel<Product> productListModel = new ListAdapterListModel<Product>();

    public void setProductList(ListAdapterListModel<Product> products) {
        productListModel = products;

    }

    ListAdapterListModel<ProductPriceBulkUpdate> priceList = new ListAdapterListModel<ProductPriceBulkUpdate>();

    public void setProductPriceList(ListAdapterListModel<ProductPriceBulkUpdate> productPriceList) {
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
        jLabel4 = new javax.swing.JLabel();
        cbProductName = new javax.swing.JComboBox();
        btnLoadPrices = new javax.swing.JButton();
        panelProductStoreGroup = new javax.swing.JPanel();
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
            .addGap(0, 877, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        add(panelTable, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(900, 60));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Product Store Group:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Product Name:");

        btnLoadPrices.setText("Load");

        javax.swing.GroupLayout panelProductStoreGroupLayout = new javax.swing.GroupLayout(panelProductStoreGroup);
        panelProductStoreGroup.setLayout(panelProductStoreGroupLayout);
        panelProductStoreGroupLayout.setHorizontalGroup(
            panelProductStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        panelProductStoreGroupLayout.setVerticalGroup(
            panelProductStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        btnLoadNew.setText("New Price");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelProductStoreGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadPrices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadNew)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLoadPrices)
                        .addComponent(btnLoadNew))
                    .addComponent(panelProductStoreGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel4)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnLoadNew;
    public javax.swing.JButton btnLoadPrices;
    private javax.swing.JComboBox cbProductName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelProductStoreGroup;
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
        Debug.logWarning("saave item.", module);
        String productStoreGroupId = "_NA_";
        if (productStoreGroupModel.getSelectedItem() != null) {
            productStoreGroupId = productStoreGroupModel.getSelectedItem().getproductStoreGroupId();
        }
        for (ProductPriceBulkUpdate paymentComposite : priceList.getList()) {
            ProductPrice prodDefPrice = paymentComposite.getDefaultPrice().get(0);
            ProductPrice prodListPrice = paymentComposite.getListPrice().get(0);
            ProductPrice prodPrice = paymentComposite.getAvgCost().get(0);
            
            List<GenericValue> toStore = new ArrayList<GenericValue>();
            prodDefPrice.setproductStoreGroupId(productStoreGroupId);
            if (prodDefPrice.isGenericValueSet()) {
                prodDefPrice.getGenericValue();
                toStore.add(prodDefPrice.getGenericValueObj());
            } else if (prodDefPrice.getprice().equals(BigDecimal.ZERO) == false) {
                toStore.add(prodDefPrice.createNewGenericValueObj(session.getDelegator()));
            }
            
            prodListPrice.setproductStoreGroupId(productStoreGroupId);
            if (prodListPrice.isGenericValueSet()) {
                prodListPrice.getGenericValue();
                toStore.add(prodListPrice.getGenericValueObj());
            } else if (prodListPrice.getprice().equals(BigDecimal.ZERO) == false) {
                toStore.add(prodListPrice.createNewGenericValueObj(session.getDelegator()));
            }
            
            prodPrice.setproductStoreGroupId(productStoreGroupId);
            if (prodPrice.isGenericValueSet()) {
                prodPrice.getGenericValue();
                toStore.add(prodPrice.getGenericValueObj());
            } else if (prodPrice.getprice().equals(BigDecimal.ZERO) == false) {
                toStore.add(prodPrice.createNewGenericValueObj(session.getDelegator()));
            }
            /*              Map<String, GenericValue> dataList = (Map<String, GenericValue>) node.getData();
             for (Map.Entry<String, GenericValue> entryDept : dataList.entrySet()) {
             if (entryDept.getEntityId().equals("Product") == false) {
             toStore.add(entryDept.getEntityValue());
             }
             }
             */
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

        }

    }
    static int NUM_COLS = 5;

    public void loadItem() {
        /*Vector dummyMacData = new Vector(10, 10);
         //        dummyMacData.addElement(new SupplierProductAndListPriceData(new Integer(100), "A", "1", "C", "E"));
         //        dummyMacData.addElement(new SupplierProductAndListPriceData(new Integer(105), "R", "2", "S", "E"));
         List<TreeNode> prodList = priceTreeMap.getAllProductsTreeNodes();
         for (TreeNode node : prodList) {
         if (node.getData() != null) {
         Map<String, GenericValue> dataList = (Map<String, GenericValue>) node.getData();
         dummyMacData.addElement(new SupplierProductAndListPriceData(dataList));
         }
         }
         m_simpleTableModel = new SimpleTableModel(dummyMacData);
         m_simpleTableModel.addTableModelListener(new InteractiveTableModelListener());
         itemJTable.setModel(m_simpleTableModel);
         */
        setupEditOrderTable();
        // Set up table attributes....
        //   setTabMapping(jTable1, 0, dummyMacData.size(), 1, (NUM_COLS - 1));
    }

    final void setupEditOrderTable() {

//}
//        m_simpleTableModel = new SimpleTableModel(dummyMacData);
        tablePanel.setListModel(priceList);

//        tablePanel.tableModel.addTableModelListener(new InteractiveTableModelListener());
//        tablePanel.jTable.setModel(m_simpleTableModel);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
//        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //      txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        //       DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        //       editor.setClickCountToStart(0);
        //       productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        //     tablePanel.jTable.getColumn("Payment Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < ProductPriceBulkEditTableModel.Columns.values().length; i++) {

            ProductPriceBulkEditTableModel.Columns[] columns = ProductPriceBulkEditTableModel.Columns.values();
            ProductPriceBulkEditTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer());
            }
        }

        /*      int colCount = m_simpleTableModel.getColumnCount();
         for (int i = 0; i < colCount; i++) {
         TableColumn column = tablePanel.jTable.getColumnModel().getColumn(i);
         //            column.setPreferredWidth(columnWidth[i]); //third column is bigger            

         switch (i) {
         case PROD_ID_INDEX: {
         //                    itemJTable.getColumn(itemJTable.getColumnName(ORDER_PROD_ID_INDEX)).setCellEditor(new ProductTreeActionTableCellEditor(editor, productListArray, parentFrame, true, session));
         break;
         }
         case PROD_NAME: {
         //                    itemJTable.getColumn(itemJTable.getColumnName(ORDER_PROD_INTERNALNAME_INDEX)).setCellEditor(editor);
         break;
         }
         case PROD_DEFAULT_PRICE: {
         //                    itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(new PositiveDecimalCellEditor(textField));
         break;
         }
         case PROD_LIST_PRICE: {
         //                  itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(new PositiveDecimalCellEditor(textField));
         break;
         }
         case PROD_AVG_COST: {
         //                    itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(new PositiveDecimalCellEditor(textField));
         break;
         }

         case PROD_SUPPLIER_PROD_ID: {
         //                  itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(editor);
         break;
         }

         case PROD_SUPPLIER_ID: {
         break;
         }
         case PROD_SUPPLIER_LAST_PRICE: {
         //                  itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(new PositiveDecimalCellEditor(textField));
         break;
         }
         case PROD_SUPPLIER_SCAN_CODE: {
         //                  itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(editor);
         break;
         }
         case HIDDEN_INDEX: {
         //                itemJTable.getColumn(itemJTable.getColumnName(i)).setCellEditor(editor);
         column.setPreferredWidth(2); //third column is bigger            
         break;
         }
         default: {
         break;
         }
         }

         }
         */
        /*        TableColumn hidden = tablePanel.jTable.getColumnModel().getColumn(HIDDEN_INDEX);
         hidden.setMinWidth(2);
         hidden.setPreferredWidth(2);
         hidden.setMaxWidth(2);
         hidden.setCellRenderer(new InteractiveRenderer(HIDDEN_INDEX));

         tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         */
        tablePanel.jTable.invalidate();
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
        isModified = isModified;

    }

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
}
