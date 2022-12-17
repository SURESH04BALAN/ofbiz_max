/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.OrderEntryTableModel;
import static org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor.module;

/**
 *
 * @author siranjeev
 */
public class InteractiveRenderer extends DefaultTableCellRenderer {

    protected int interactiveColumn;

    public int getInteractiveColumn() {
        return interactiveColumn;
    }

    public void setInteractiveColumn(int interactiveColumn) {
        this.interactiveColumn = interactiveColumn;
    }

    public InteractiveRenderer(int interactiveColumn) {
        this.interactiveColumn = interactiveColumn;
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table,
            Object value, boolean isSelected, boolean hasFocus, final int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (column == interactiveColumn && hasFocus) {
            ActionEvent event = new RowColumnActionEvent(this, 1, "productId", row, column, table);
            for (ActionListener listener : listeners) {
                Debug.logInfo("editCell Action listner", module);
                listener.actionPerformed(event); // broadcast to all
            }
        }

        return c;
    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
}
