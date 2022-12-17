/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import org.ofbiz.ordermax.orderbase.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.celleditors.ActionTableCellEditor;

/**
 *
 * @author siranjeev
 *
 */
public class DataResourceActionTableCellEditor extends ActionTableCellEditor {

    public static final String module = DataResourceActionTableCellEditor.class.getName();
    
    public DataResourceActionTableCellEditor(TableCellEditor editor) {
        super(editor);
    }

    @Override
    protected void editCell(JTable table, Object partialValue, int row, int column) {
        Debug.logInfo("editCell", module);
            ActionEvent event = new RowColumnActionEvent(this, 1, "productId",  row, column, table);
            for (ActionListener listener : listeners) {
                Debug.logInfo("editCell Action listner", module);
                listener.actionPerformed(event); // broadcast to all
            }
    }

    @Override
    public boolean stopCellEditing() {
        Debug.logInfo("stopCellEditing", module);
        return super.stopCellEditing();
    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
        
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    
}
