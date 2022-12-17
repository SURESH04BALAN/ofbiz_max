/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface DynamicTableModelInterface {

    void addRows(List<GenericValue> datalist);

    void addMapRows(List<Map<String, Object>> datalist);

    Object getValueAt(int row, int col);

    boolean isCellEditable(int row, int col);

    void setValueAt(Object value, int row, int col);

    public int getColumnWidth(int col);

    public String getColumnRendererName(int col);

    public String getColumnClassName(int col);

    public int getColumnCount();

    public int getRowCount();

    Object getValueAtUsingColId(int rowid, String id) throws Exception;

    GenericValue getRowGenericData(int row);

    public Map<String, Object> getRowMapData(int row);

}
