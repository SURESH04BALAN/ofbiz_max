/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import org.ofbiz.pos.generic.OrderMaxViewEntity.ColumnDetails;

/**
 *
 * @author siranjeev
 */
public abstract class BaseDynamicTableModel extends AbstractTableModel {

    private boolean DEBUG = true;

    protected String[][] IdColumnName = null;
    HashMap<Integer, OrderMaxViewEntity.ColumnDetails> columnDetailsMap = new HashMap<Integer, OrderMaxViewEntity.ColumnDetails>();

    BaseDynamicTableModel(String[][] IdColumnName) {

        this.IdColumnName = IdColumnName;

        for (int i = 0; i < IdColumnName.length; i++) {
            try {
                OrderMaxViewEntity.ColumnDetails columnDetails = OrderMaxViewEntity.ColumnDetails.getColumnDetails(IdColumnName[i][0]);
                columnDetailsMap.put(i, columnDetails);

            } catch (Exception ex) {
                Logger.getLogger(OrderMaxViewEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getColumnCount() {
        return columnDetailsMap.size();
    }
    /*
     public int getRowCount() {
     return dataList.size();
     }
     */

    @Override
    public String getColumnName(int col) {
        return columnDetailsMap.get(col).Name;
    }

    public int getColumnWidth(int col) {
        return columnDetailsMap.get(col).ColumnWidth.intValue();
    }

    public String getColumnRendererName(int col) {
        return columnDetailsMap.get(col).RendererName;
    }

    public String getColumnClassName(int col) {
        return columnDetailsMap.get(col).ClassName;
    }

    /*  
     @Override
     public Object getValueAt(int row, int col) {
     String id = columnDetailsMap.get(col).Id; // columnNumberId.get(col);
     GenericValue data = dataList.get(row);
     if (data != null) {
     return data.get(id);//data[row][col];                
     } else {
     return new Object();//data[row][col];
     }

     }
     */
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        /*            Object obj = getValueAt(0, c);
         if(obj!=null){
         String id =  columnNumberId.get(c);                
         return obj.getClass();
         }
         else{
         return new String("t").getClass();
         }
         */ Class cl = null;
        try {
            cl = Class.forName(columnDetailsMap.get(c).ClassName);
        } catch (ClassNotFoundException ex) {
//            Debug.logError(ex, "module");
        }
        return cl;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        /*        if (DEBUG) {
         System.out.println("Setting value at " + row + "," + col
         + " to " + value
         + " (an instance of "
         + value.getClass() + ")");
         }

         //            data[row][col] = value;
         String id = columnDetailsMap.get(col).Id;//columnNumberId.get(col);
         GenericValue data = dataList.get(row);
         data.set(id, value);
         fireTableCellUpdated(row, col);

         if (DEBUG) {
         System.out.println("New value of data:");
         printDebugData();
         }
         */
    }

    private void printDebugData() {
        /*        int numRows = getRowCount();
         int numCols = getColumnCount();

         for (int i = 0; i < numRows; i++) {
         System.out.print("    row " + i + ":");
         for (int j = 0; j < columnDetailsMap.size(); j++) {
         //                String id = columnNumberId.get(j);
         GenericValue data = dataList.get(i);

         System.out.print("  " + data.get(columnDetailsMap.get(j).Id));
         }
         System.out.println();
         }
         System.out.println("--------------------------");
         * */
    }

    int getColumnIndexFromId(String colId) throws Exception {
        for (Entry<Integer, ColumnDetails> entryBrand : columnDetailsMap.entrySet()) {
            if (entryBrand.getValue().Id.equals(colId)) {
                return entryBrand.getKey().intValue();
            }
        }
        throw new Exception("Column not found: " + colId);
    }

}
