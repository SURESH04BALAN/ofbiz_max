/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class GenericValueTableDataModel extends BaseDynamicTableModel implements DynamicTableModelInterface {

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
    ArrayList<GenericValue> dataList = new ArrayList<GenericValue>();

    public GenericValueTableDataModel(String[][] IdColumnName) {
        super(IdColumnName);
    }

    public GenericValueTableDataModel(String[][] IdColumnName, boolean isEditable) {
        super(IdColumnName,isEditable);
    }
        
    public int getColumnCount() {
        return columnDetailsMap.size();
    }

    public int getRowCount() {
        return dataList.size();
    }
    /*
     @Override
     public String getColumnName(int col) {
     return columnDetailsMap.get(col).Name;
     }
     */

    public int getColumnWidth(int col) {
        return columnDetailsMap.get(col).ColumnWidth.intValue();
    }

    public String getColumnRendererName(int col) {
        return columnDetailsMap.get(col).RendererName;
    }

    public String getColumnClassName(int col) {
        return columnDetailsMap.get(col).ClassName;
    }

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


    @Override
    public void setValueAt(Object value, int row, int col) {

//            data[row][col] = value;
        String id = columnDetailsMap.get(col).Id;//columnNumberId.get(col);
        GenericValue data = dataList.get(row);
        data.set(id, value);
        fireTableCellUpdated(row, col);
    }

    private void printDebugData() {
        int numRows = getRowCount();
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
    }

    public void addRows(List<GenericValue> datalist) {
//        dataVector.addAll(rows);
        //          fireTableRowsInserted(
        //                   dataVector.size() - 1,
        //                  dataVector.size() - 1);

        for (GenericValue entry : datalist) {
            dataList.add(entry);
//            Debug.logInfo(entry.internalName + "[" + entry.productId+"]", "InteractiveTableModel");
            fireTableRowsInserted(
                    dataList.size() - 1,
                    dataList.size() - 1);

        }

    }

    //do nothing
    public void addMapRows(List<Map<String, Object>> datalist) {
    }

    public Object getValueAtUsingColId(int rowid, String id) throws Exception {
        int col = getColumnIndexFromId(id);
        return getValueAt(rowid, col);
    }

    public GenericValue getRowGenericData(int row) {
        return dataList.get(row);
    }
    
//    public int getRowCount(){
//        return dataList.size();
//    }

    public Map<String, Object> getRowMapData(int row) {
        return null;

    }
}
