/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.pospanel;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import javax.swing.*;
import javax.swing.table.*;
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.celleditors.ButtonColumnCellEditor;

/**
 *
 * @author administrator
 */
public class PosProductPanels extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = PosProductPanels.class.getName();
    private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private java.awt.Frame parentFrame = null;
    
    ImageIcon errorIcon = (ImageIcon) UIManager.getIcon("OptionPane.errorIcon");
    ImageIcon infoIcon = (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");
    ImageIcon warnIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon");
    String[] columnNames = {"Picture", "Description","Picture", "Description","Picture", "Description"};
    Object[][] data = {{errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
        {errorIcon, "Nivi", infoIcon, "Jeev", warnIcon, "How are you?"},
    };
    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        private static final long serialVersionUID = 1L;
        //  Returning the Class of each column will allow different
        //  renderers to be used based on Class

        @Override
        public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }
    };

    /**
     * Creates new form PaymentPanel
     */
    public PosProductPanels() {
        initComponents();
    }

    public PosProductPanels(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        jTable.setTableHeader(null);
        jTable.setModel(model);
        
        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
//                int modelRow = Integer.valueOf(e.getActionCommand());
                int commaPos = e.getActionCommand().indexOf(",");
                int modelCol = Integer.valueOf(e.getActionCommand().substring(0, commaPos));
                int modelRow = Integer.valueOf(e.getActionCommand().substring(commaPos+1, e.getActionCommand().length()));
                Debug.logInfo("Col: " + modelCol + " Row: " + modelRow, "module");
                //((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };

        for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {
            TableColumn column = jTable.getColumnModel().getColumn(i);

            column.setCellEditor(new ButtonColumnCellEditor(jTable, delete, i));

        }

//        ImageIcon icon = new ImageIcon(ButtonColumnCellEditor.getImage("preyear.gif"), "<<");
//        jButton1.setIcon(icon);

//        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("C:/AuthLog/server/finalpos/specialpurpose/pos/src/org/ofbiz/ordermax/images/larich/coconut--sambol.jpg"))); // NOI18N        

//        initComponent1();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();

        jTable.setBackground(new java.awt.Color(128, 128, 128));
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2", "5", "9", "13", null, null},
                {"1", "2", "6", "10", "14", "3", null},
                {"1", "2", "7", "11", "15", "3", null},
                {"1", "2", "8", "12", "3", "3", null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jTable.setRowHeight(55);
        jTable.setRowMargin(0);
        jTable.setRowSelectionAllowed(false);
        jTable.setShowHorizontalLines(false);
        jTable.setShowVerticalLines(false);
        jTable.getTableHeader().setResizingAllowed(false);
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable);

        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsModified(boolean isModified) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            /*
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
             macData.setSupplierId((ComboKey) value);
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
                
             fireTableCellUpdated(i, col);
             }*/
        }

        public void setValueAt(Object value, int row, int col) {
            if (row == 0) {
                setAllRows(value, col);
                return;
            }

            SupplierProductAndListPriceData macData = m_macDataVector.get(row);
            /*
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
             macData.setSupplierId((ComboKey) value);
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
             * */
        }

        public String getColumnName(int col) {
            return m_colNames[col];
        }

        @Override
        public Class getColumnClass(int col) {
            return m_colTypes[col];
        }

        public Object getValueAt(int row, int col) {
            /*            if (row == 0) {
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
             */
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
    
    static public byte[] getImage(String fileName) {
            InputStream is = null;

            try {
                is = new BufferedInputStream(PosProductPanels.class.getClassLoader().getResourceAsStream(fileName));
                byte[] b = new byte[is.available()];
                is.read(b);
                return b;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
