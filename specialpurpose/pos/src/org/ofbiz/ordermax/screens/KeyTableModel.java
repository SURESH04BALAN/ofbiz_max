/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.Key;

/**
 *
 * @author siranjeev
 */
public class KeyTableModel extends AbstractTableModel{
    public static final int KEY_ID = 0;
    public static final int KEY_VALUE = 1;
    public static final int HIDDEN_INDEX = 2;    
    protected String[] columnNames;
    protected ArrayList<Key> dataVector;
    static int EmptyRow = 0;
    public KeyTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new ArrayList<Key>();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        if (column == HIDDEN_INDEX) {
            return false;
        } else {
            return true;
        }
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case KEY_ID:
            case KEY_VALUE:
                return String.class;
                
            default:
                return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
        if(row >=  dataVector.size() || row < 0 ) {
            return null;
        }
        
        Key record = (Key) dataVector.get(row);
        switch (column) {
            case KEY_ID:
                return record._id;
            case KEY_VALUE:
                return record._name;
            default:
                return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
        Key record =  dataVector.get(row);
        switch (column) {
            case KEY_ID:
                record._id = (String) value;
                break;
            case KEY_VALUE:
                record._name  = (String) value;
                break;
                
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public boolean hasEmptyRow() {
        if (dataVector.isEmpty()) {
            return false;
        }
        Key prodRecord =  dataVector.get(dataVector.size() - 1);
        if (prodRecord._id.trim().equals("")
                || prodRecord._name.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void addEmptyRow() {
        Key val = new Key("", "");
        dataVector.add(val);
        EmptyRow++;
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }

    public void addRows(List<Key> rows) {
//        dataVector.addAll(rows);
  //          fireTableRowsInserted(
 //                   dataVector.size() - 1,
  //                  dataVector.size() - 1);
        
        for (Key entry : rows) {
            dataVector.add(entry);
//            Debug.logInfo(entry._id + "[" + entry._name+"]", "KeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }    
    
    public  ArrayList<Key> getDataList(){ return dataVector; }
    
    public int getRowNumber(String id){
        int index = -1;
        int i=0;
//        Debug.logInfo("id: "+id, "module");
        for( Key key : dataVector){
//            Debug.logInfo("key._id: "+key._id, "module");
            if(key._id.equals(id)){
                index=i;
                break;
            }
            ++i;
        }
        return index;
    }
}
