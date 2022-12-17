/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ActionTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.celleditors.PositiveDecimalCellEditor;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductFacility;
import org.ofbiz.ordermax.product.ProductSingleton;

/**
 *
 * @author administrator
 */
public class ProductFacilityEditTablePanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    static final String module = ProductFacilityEditTablePanel.class.getName();
    /**
     * Creates new form ProductPriceEditTablePanel
     */

    //GenericValueComboBox facilityIdCombo = null;
    protected XuiSession session = null;
    List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
    //RXTable jTable1 = null;
    JScrollPane scrollPane = null;
    public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    public GenericTableModelPanel<ProductFacility, ProductFacilityTableModel> tablePanel = null;

    public ProductFacilityEditTablePanel(XuiSession session) {
        initComponents();

        tablePanel = new GenericTableModelPanel<ProductFacility, ProductFacilityTableModel>(new ProductFacilityTableModel());
        panelTable.setLayout(new BorderLayout());
        panelTable.add(BorderLayout.CENTER, tablePanel);

        setupEditOrderTable();

        this.session = session;
        List<Facility> genFacilityList = FacilitySingleton.getValueList();//PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());

        facilityModel = new JGenericComboBoxSelectionModel<Facility>(panelFacility, genFacilityList);
        String facilityId = ControllerOptions.getSession().getProductStore().getinventoryFacilityId();
        try {
            Facility facility = FacilitySingleton.getFacility(facilityId);
            facilityModel.setSelectedItem(facility);
        } catch (Exception ex) {
            Logger.getLogger(ProductFacilityEditTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //facilityIdCombo = new GenericValueComboBox(cbFacilityName, genFacilityList, "Facility", "facilityId", "facilityName");
        Map<String, String> suppliers = PosProductHelper.getSuppliers(session.getDelegator());

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

    ListAdapterListModel<Product> productListModel = new ListAdapterListModel<Product>();

    public void setProductList(ListAdapterListModel<Product> products) {
        productListModel = products;

    }

    ListAdapterListModel<ProductFacility> productFacilityList = new ListAdapterListModel<ProductFacility>();

    public void setProductPriceList(ListAdapterListModel<ProductFacility> productPriceList) {
        productFacilityList = productPriceList;
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
        btnLoadPrices = new javax.swing.JButton();
        panelFacility = new javax.swing.JPanel();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(800, 700));
        setPreferredSize(new java.awt.Dimension(800, 700));
        setLayout(new java.awt.BorderLayout());

        panelTable.setPreferredSize(new java.awt.Dimension(900, 490));

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        add(panelTable, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(900, 60));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Facility Name:");

        btnLoadPrices.setText("Load");

        javax.swing.GroupLayout panelFacilityLayout = new javax.swing.GroupLayout(panelFacility);
        panelFacility.setLayout(panelFacilityLayout);
        panelFacilityLayout.setHorizontalGroup(
            panelFacilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );
        panelFacilityLayout.setVerticalGroup(
            panelFacilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(panelFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadPrices)
                .addContainerGap(357, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelFacility, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLoadPrices)
                        .addComponent(jLabel1)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnLoadPrices;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFacility;
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
        for (ProductFacility productFacility : productFacilityList.getList()) {

            List<GenericValue> toStore = new ArrayList<GenericValue>();
            if (productFacility.isGenericValueSet()) {
                productFacility.getGenericValue();
                toStore.add(productFacility.getGenericValueObj());
            } else if (productFacility.getMinimumStock().equals(BigDecimal.ZERO) == false
                    || productFacility.getReorderQuantity().equals(BigDecimal.ZERO) == false
                    || productFacility.getDaysToShip().equals(BigDecimal.ZERO) == false
                    || productFacility.getLastInventoryCount().equals(BigDecimal.ZERO) == false) {
                toStore.add(productFacility.createNewGenericValueObj(session.getDelegator()));
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
        }
    }
    static int NUM_COLS = 5;

    public void loadItem() {
        setupEditOrderTable();
    }

    final void setupEditOrderTable() {

        tablePanel.setListModel(productFacilityList);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        for (int i = 0; i < ProductFacilityTableModel.Columns.values().length; i++) {

            ProductFacilityTableModel.Columns[] columns = ProductFacilityTableModel.Columns.values();
            ProductFacilityTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer());
            }
        }

        tablePanel.jTable.invalidate();
    }

    void tableCellAction(final JTextField textField, int row, int col) {
        Debug.logInfo("tableCellAction product: " + textField.getText(), module);
        int selectedRowIndex = tablePanel.jTable.getSelectedRow();
        int selectedColumnIndex = tablePanel.jTable.getSelectedColumn();
        Object selectedObject = (Object) tablePanel.jTable.getModel().getValueAt(selectedRowIndex, selectedColumnIndex).toString();
        Debug.logInfo("tableCellAction selectedObject VALUE: " + selectedObject, module);
        textField.postActionEvent(); //inform the editor
        Debug.logInfo(" tableModel.hasEmptyRow() 2", module);
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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

    static public class ProductFacilityTableModel extends ActionTableModel<ProductFacility> {

        public enum Columns {
//        public String[] m_colNames = {"Product Id", "Name", "Def Price", "List Price", "Avg Cost", "Supplier Id", "Supp Prod Id", "Last Price", "Scan Code", ""};
//        public Class[] m_colTypes = {String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class, String.class, BigDecimal.class, String.class, String.class};

            PROD_ID_INDEX(0, 100, String.class, "Product Id", false),
            PROD_NAME(1, 250, String.class, "Product Name", false),
            FACILITY_ID(1, 250, String.class, "Facility Name", false),
            MINIMUM_STOCK(2, 100, BigDecimal.class, "Minimum Stock Level", true),
            REORDER_QTY(3, 100, BigDecimal.class, "Reorder Quantity", true),
            DAYS_TO_SHIP(4, 100, Long.class, "Days To Ship", true),
            LAST_INVENTORY_COUNT(3, 100, BigDecimal.class, "Last Inventory Count", true);

            private int columnIndex;
            private int columnWidth;

            public String getHeaderString() {
                return headerString;
            }

            public void setHeaderString(String headerString) {
                this.headerString = headerString;
            }

            public Class getClassName() {
                return className;
            }

            public void setClassName(Class className) {
                this.className = className;
            }

            public boolean isIsEditable() {
                return isEditable;
            }

            public void setIsEditable(boolean isEditable) {
                this.isEditable = isEditable;
            }
            private String headerString;
            private Class className;
            private boolean isEditable;

            Columns(int index, int width, Class className, String header, boolean edit) {
                columnIndex = index;
                columnWidth = width;
                headerString = header;
                this.className = className;
                isEditable = edit;
            }

            public int getColumnIndex() {
                return columnIndex;
            }

            public int getColumnWidth() {
                return columnWidth;
            }
        }

        public ProductFacilityTableModel() {
        }

        public int getColumnCount() {
            return Columns.values().length;
        }

        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.isEditable;

        }

        public Object getValueAt(int rowIndex, int columnIndex) {

//            Debug.logInfo("getValueAt row: " + rowIndex + " column: " + col, module);
            if (rowIndex == 0) {
                Columns[] columns = Columns.values();
                Columns column = columns[columnIndex];
                if (column.equals(Columns.PROD_ID_INDEX)) {
                    return "DEFAULT";
                }

                return new String();
            }

            Object columnValue = null;
            ProductFacility productFacility = (ProductFacility) listModel.getElementAt(rowIndex - 1);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            switch (column) {
                case PROD_ID_INDEX:
                    return productFacility.getProductId();
                case PROD_NAME: {
                    try {
                        columnValue = ProductSingleton.getProduct(productFacility.getProductId()).getproductName();
                                
                    } catch (Exception ex) {
                        columnValue = null;
                        Logger.getLogger(ProductFacilityEditTablePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case FACILITY_ID:{
                    try {
                        columnValue = FacilitySingleton.getFacility(productFacility.getFacilityId()).getfacilityName();
                    } catch (Exception ex) {
                        columnValue = null;
                        Logger.getLogger(ProductFacilityEditTablePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                    
                case MINIMUM_STOCK:
                    return productFacility.getMinimumStock();
                case REORDER_QTY:
                    return productFacility.getReorderQuantity();
                case DAYS_TO_SHIP:
                    return productFacility.getDaysToShip();
                case LAST_INVENTORY_COUNT:
                    return productFacility.getLastInventoryCount();
                default:
                    break;
            }

            return columnValue;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {

            if (rowIndex == 0) {
                setAllRows(value, columnIndex);
                return;
            }
            Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
            ProductFacility productFacility = (ProductFacility) listModel.getElementAt(rowIndex - 1);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];

            switch (column) {
                case PROD_ID_INDEX:
                    productFacility.setProductId((String) value);
                    break;
                case PROD_NAME:
//                        macData.setProductInternalName((String) value);
                    break;
                case FACILITY_ID:
//                        macData.setProductInternalName((String) value);
                    break;
                case MINIMUM_STOCK:
                    productFacility.setMinimumStock((BigDecimal) value);
                    break;
                case REORDER_QTY:
                    productFacility.setReorderQuantity((BigDecimal) value);
                    break;
                case DAYS_TO_SHIP:
                    productFacility.setDaysToShip((Long) value);
                    break;
                case LAST_INVENTORY_COUNT:
                    productFacility.setLastInventoryCount((BigDecimal) value);
                    break;

                default:
                    System.out.println("invalid index");
            }
//         record.updateOrderMax();
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void setAllRows(Object value, int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            for (int rowIndex = 0; rowIndex < listModel.getSize(); ++rowIndex) {
                ProductFacility productFacility = (ProductFacility) listModel.getElementAt(rowIndex);
                switch (column) {
                    case PROD_ID_INDEX:
                        productFacility.setProductId((String) value);
                        break;
                    case PROD_NAME:
//                        macData.setProductInternalName((String) value);
                        break;
                    case FACILITY_ID:
//                        macData.setProductInternalName((String) value);
                        break;
                    case MINIMUM_STOCK:
                        productFacility.setMinimumStock((BigDecimal) value);
                        break;
                    case REORDER_QTY:
                        productFacility.setReorderQuantity((BigDecimal) value);
                        break;
                    case DAYS_TO_SHIP:
                        productFacility.setDaysToShip((Long) value);
                        break;
                    case LAST_INVENTORY_COUNT:
                        productFacility.setLastInventoryCount((BigDecimal) value);
                        break;

                }
                fireTableCellUpdated(rowIndex + 1, columnIndex);
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.className;
        }

        @Override
        public String getColumnName(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.headerString;
        }

        @Override
        final public int getRowCount() {
            return listModel.getSize() + 1;
        }
    }
}
