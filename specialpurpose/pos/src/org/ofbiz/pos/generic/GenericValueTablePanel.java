/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.entity.Delegator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siranjeev
 */
public class GenericValueTablePanel extends javax.swing.JPanel {

    private String[][] IdColumnName = null;
    /**
     */
    XuiSession session;
    private boolean DEBUG = true;
    protected DynamicTableModelInterface tableModel = null;
    GenericValue selectedGenericVal = null;
    java.awt.Frame parentFrame = null;

    /**
     * Creates new form GenericValueTablePanel
     */
    public GenericValueTablePanel(java.awt.Frame parent, XuiSession sessionVal, String[][] IdColumnName) {
        initComponents();
        session = sessionVal;
        this.IdColumnName = IdColumnName;
        parentFrame = parent;
//        OrderMaxUtility.addAPanelToPanel(jScrollPane1, this);
    }

    public GenericValueTablePanel(XuiSession sessionVal, String classname) {
        initComponents();
        session = sessionVal;
        try {
            //        this.IdColumnName = IdColumnName;
            this.IdColumnName = reflectionCall("org.ofbiz.ordermax.entity." + classname, "getColumnNameId", null, null);
            Delegator delegator = sessionVal.getDelegator();
            List<GenericValue> partyList = PosProductHelper.getGenericValueLists(classname, delegator);        // TODO add your handling code here:
            this.setupOrderTableList(partyList);

        } catch (Exception ex) {
//            Logger.getLogger(GenericValueTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

//        OrderMaxUtility.addAPanelToPanel(jScrollPane1, this);
    }

    public void setGenericValueTableName(String classname) {
        try {
            //        this.IdColumnName = IdColumnName;
            this.IdColumnName = reflectionCall("org.ofbiz.ordermax.entity." + classname, "getColumnNameId", null, null);
            Delegator delegator = session.getDelegator();
            List<GenericValue> partyList = PosProductHelper.getGenericValueLists(classname, delegator);        // TODO add your handling code here:
            this.setupOrderTableList(partyList);

        } catch (Exception ex) {
//            Logger.getLogger(GenericValueTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setGenericValueTableName(String classname, Map<String, Object> whereClauseMap) {
        try {
            //        this.IdColumnName = IdColumnName;
            this.IdColumnName = reflectionCall("org.ofbiz.ordermax.entity." + classname, "getColumnNameId", null, null);
            Delegator delegator = session.getDelegator();
            List<GenericValue> partyList = PosProductHelper.getGenericValueListsWithSelection(classname, whereClauseMap, null, delegator);        // TODO add your handling code here:
            this.setupOrderTableList(partyList);

        } catch (Exception ex) {
//            Logger.getLogger(GenericValueTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GenericValue getSelectedGenericVal() {
        return selectedGenericVal;
    }

    public void setSelectedGenericVal(GenericValue selectedGenericVal) {
        this.selectedGenericVal = selectedGenericVal;
    }

    public void setupOrderTableList(List<GenericValue> dataList) {
        //       CreateDisplayColumnList colList = new CreateDisplayColumnList(IdColumnName);
//        MapValueTableDataModel model = new MapValueTableDataModel(colList.getColumns());        
        GenericValueTableDataModel model = new GenericValueTableDataModel(IdColumnName);
        jtable.setModel(model);
        tableModel = model;
        tableModel.addRows(dataList);
        jtable.setFillsViewportHeight(true);

        jtable.setSurrendersFocusOnKeystroke(true);
        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        ProductListArray productListArray = new ProductListArray(session);
        final JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        int colCount = tableModel.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            TableColumn column = jtable.getColumnModel().getColumn(i);
            column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
        }
        /*
         String colRenderer = tableModel.getColumnRendererName(i);
         if (colRenderer.equals(OrderMaxViewEntity.ProductTreeNode_Cell_Rendere)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new ProductTreeActionTableCellEditor(editor, productListArray, parentFrame, true, session));
         } else if (colRenderer.equals(OrderMaxViewEntity.StringDescription_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new StringActionTableCellEditor(editor));
         } else if (colRenderer.equals(OrderMaxViewEntity.OrderId_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new OrderIdActionTableCellEditor(editor, parentFrame, true, session));
         }else if (colRenderer.equals(OrderMaxViewEntity.PartyId_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new PartyIdActionTableCellEditor(editor, parentFrame, true, session));
         }
         }
         */
    }

    public void setupOrderTableMap(List<Map<String, Object>> dataList) {
//        CreateDisplayColumnList colList = new CreateDisplayColumnList(IdColumnName);
        MapValueTableDataModel model = new MapValueTableDataModel(IdColumnName);
        jtable.setModel(model);
        tableModel = model;
        tableModel.addMapRows(dataList);
        jtable.setFillsViewportHeight(true);

        jtable.setSurrendersFocusOnKeystroke(true);

        final JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        int colCount = tableModel.getColumnCount();
        /*        for (int i = 0; i < colCount; i++) {
         TableColumn column = jtable.getColumnModel().getColumn(i);
         column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
         String colRenderer = tableModel.getColumnRendererName(i);
         if (colRenderer.equals(OrderMaxViewEntity.ProductTreeNode_Cell_Rendere)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new ProductTreeActionTableCellEditorValid(editor, parentFrame, true, session));
         } else if (colRenderer.equals(OrderMaxViewEntity.StringDescription_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new StringActionTableCellEditor(editor));
         } else if (colRenderer.equals(OrderMaxViewEntity.OrderId_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new OrderIdActionTableCellEditor(editor, parentFrame, true, session));
         } else if (colRenderer.equals(OrderMaxViewEntity.PartyId_Cell_Renderer)) {
         jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new PartyIdActionTableCellEditor(editor, parentFrame, true, session));
         }
         }
         * */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();

        jScrollPane1.setAutoscrolls(true);

        jtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jtable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    // End of variables declaration//GEN-END:variables

    public JTable getJtable() {
        return jtable;
    }

    public void setJtable(JTable jtable) {
        this.jtable = jtable;
    }

    public DynamicTableModelInterface getTableModel() {
        return tableModel;
    }

    /**
     * Allow for instance call, avoiding certain class circular dependencies.
     * <br /> Calls even private method if java Security allows it.
     *
     * @param aninstance instance on which method is invoked (if null, static
     * call)
     * @param classname name of the class containing the method (can be null -
     * ignored, actually - if instance if provided, must be provided if static
     * call)
     * @param amethodname name of the method to invoke
     * @param parameterTypes array of Classes
     * @param parameters array of Object
     * @return resulting Object
     * @throws CCException if any problem
     */
    public static String[][] reflectionCall(final String classname, final String amethodname, final Class[] parameterTypes, final Object[] parameters) throws Exception {
        String[][] res = null;
        try {
            Class myTarget = Class.forName(classname);
            Object myinstance = myTarget.newInstance();
            Method myMethod;
            myMethod = myTarget.getDeclaredMethod(amethodname);  // Works!
            res = (String[][]) myMethod.invoke(myinstance);

        } catch (final ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (final SecurityException e) {
            throw new Exception(e.getMessage());
        } catch (final NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (final InvocationTargetException e) {
            throw new Exception(e.getMessage());
        }
        return res;
    }

    public final class CreateDisplayColumnList {

        public String[][] columnNameExcludeListId = {
            {"lastUpdatedStamp", "Last Updated Stamp"},
            {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
            {"createdStamp", "Created Stamp"},
            {"createdTxStamp", "Created Tx Stamp"},};

        public CreateDisplayColumnList(String[][] headerList) {
            int numColumns = 0;
            for (int i = 0; i < headerList.length; i++) {
//                if (isExclude(headerList[i][0]) == false) {
                numColumns++;
//                }
            }

            columnNameListId = new String[numColumns][2];
            int row = 0;
            for (int i = 0; i < headerList.length; i++) {
//                if (isExclude(headerList[i][0]) == false) {
                columnNameListId[row][1] = headerList[i][1];
                columnNameListId[row++][0] = headerList[i][0];
//                }
            }
        }

        public String[][] getColumns() {
            return columnNameListId;
        }
        public String[][] columnNameListId = null;

        protected boolean isExclude(String col) {

            for (int i = 0; i < columnNameExcludeListId.length; i++) {
                if (columnNameExcludeListId[i][0].equalsIgnoreCase(col)) {
                    return true;
                }
            }
            return false;
        }
    }
}
