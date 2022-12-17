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
public class MapValueTableDataModel extends BaseDynamicTableModel implements DynamicTableModelInterface {

    private boolean DEBUG = true;

    ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

    MapValueTableDataModel(String[][] IdColumnName) {
        super(IdColumnName);
    }

    MapValueTableDataModel(String[][] IdColumnName, boolean isEditable) {
        super(IdColumnName,isEditable);
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

    @Override
    public void setValueAt(Object value, int row, int col) {
        String id = columnDetailsMap.get(col).Id;//columnNumberId.get(col);
        Map<String, Object> data = dataList.get(row);
        data.put(id, value);

        fireTableCellUpdated(row, col);
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
