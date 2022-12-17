/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class MapValueTableDataModel extends BaseDynamicTableModel implements DynamicTableModelInterface {

    private boolean DEBUG = true;

    /*
     static class ColumnDetails {

     protected String Id;
     protected String Name;
     protected String ClassName;
     protected Integer ColumnWidth;
     protected String RendererName;

     ColumnDetails(String Id, String Name, String ClassName, Integer width, String rendererName) {
     this.Id = Id;
     this.Name = Name;
     this.ClassName = ClassName;
     this.ColumnWidth = width;
     this.RendererName = rendererName;
     }

     static OrderMaxViewEntity.ColumnDetails getColumnDetails(final String id) throws Exception {
     for (int i = 0; i < OrderMaxViewEntity.GenericColumnName.length; ++i) {
     if (OrderMaxViewEntity.GenericColumnName[i].Id.equals(id)) {
     return OrderMaxViewEntity.GenericColumnName[i];
     }
     }
     new ColumnDetails(id, id, "java.lang.String", new Integer(OrderMaxViewEntity.STATUSID_COL_SIZE), OrderMaxViewEntity.Default_Cell_Rendere).outputToDebug();                  
     throw new Exception("Row Index not found: " + id);

     }
        
     void outputToDebug(){
     String str = "new ColumnDetails(" + "\"" + Id  + "\"," +  "\"" + Name + "\",";
     str = str.concat("\"" + ClassName + "\"," +  "\"new Integer(STATUSID_COL_SIZE)\"," + "\"Default_Cell_Rendere\"),");
     Debug.logInfo(str,"ColumnDetails");          
     }
        
     static void outputFieldMap(GenericValue genVal){
     Map<String, Object> fieldsMap = genVal.getAllFields();
     for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
     String str = "new ColumnDetails(" + "\"" + entryDept.getKey()  + "\"," +  "\"" + entryDept.getKey() + "\",";
     str = str.concat("\"" + entryDept.getValue().getClass() + "\"," +  "\"new Integer(STATUSID_COL_SIZE)\"," + "\"Default_Cell_Rendere\"),");
     Debug.logInfo(str,"ColumnDetails");                          
     }
     }
     }
     */
    ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

    public MapValueTableDataModel(String[][] IdColumnName) {
        super(IdColumnName);
    }

    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnWidth(int col) {
        return columnDetailsMap.get(col).ColumnWidth.intValue();
    }

    @Override
    public String getColumnRendererName(int col) {
        return columnDetailsMap.get(col).RendererName;
    }

    @Override
    public String getColumnClassName(int col) {
        return columnDetailsMap.get(col).ClassName;
    }

    @Override
    public int getColumnCount() {
        return columnDetailsMap.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        String id = columnDetailsMap.get(col).Id; // columnNumberId.get(col);
        Map<String, Object> data = dataList.get(row);
        if (data != null) {
            return data.get(id);//data[row][col];                
        } else {
            return new Object();//data[row][col];
        }

    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    /*
     @Override
     public Class getColumnClass(int c) {
     Class cl = null;
     try {
     cl = Class.forName(columnDetailsMap.get(c).ClassName);
     } catch (ClassNotFoundException ex) {
     Debug.logInfo(columnDetailsMap.get(c).Name, "module");
     Debug.logInfo(columnDetailsMap.get(c).Id, "module");
     Debug.logInfo(columnDetailsMap.get(c).ClassName, "module");
     Logger.getLogger(OrderMaxFacilityEditForm.class.getName()).log(Level.SEVERE, null, ex);
     }
     return cl;
     }
     */
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
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }

//            data[row][col] = value;
        String id = columnDetailsMap.get(col).Id;//columnNumberId.get(col);
        Map<String, Object> data = dataList.get(row);
        data.put(id, value);

        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < columnDetailsMap.size(); j++) {
//                String id = columnNumberId.get(j);
                Map<String, Object> data = dataList.get(i);

                System.out.print("  " + data.get(columnDetailsMap.get(j).Id));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    public void addRows(List<GenericValue> datalist) {
    }

    //do nothing
    public void addMapRows(List<Map<String, Object>> datalist) {
        for (Map<String, Object> entry : datalist) {
            dataList.add(entry);
//            Debug.logInfo(entry.internalName + "[" + entry.productId+"]", "InteractiveTableModel");
            fireTableRowsInserted(
                    dataList.size() - 1,
                    dataList.size() - 1);

        }

    }

    public Object getValueAtUsingColId(int rowid, String id) throws Exception {
        int col = getColumnIndexFromId(id);
        return getValueAt(rowid, col);
    }

    public GenericValue getRowGenericData(int row) {
        return null;
    }

    public Map<String, Object> getRowMapData(int row) {
        return dataList.get(row);
    }
}
