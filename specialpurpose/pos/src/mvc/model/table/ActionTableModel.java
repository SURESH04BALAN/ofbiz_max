/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;

/**
 *
 * @author BBS Auctions
 */
public abstract class ActionTableModel<E> extends AbstractTableModel {

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public ActionTableModel() {
    }

    protected ListModel<E> listModel = new DefaultListModel<E>();

    public final void setListModel(ListModel<E> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }

    final public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    final public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    final public void clearActionListener() {
        listeners.clear();
    }

    final protected void notifyColumnDataChange(int row, int column) {
        ActionEvent event = new RowColumnActionEvent(this, 1, "productId", row, column, null);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }
        //            fireTableCellUpdated(row, Columns.ORDER_LINETOTAL_INDEX.columnIndex);
    }

    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    protected class ListModelChangeListener implements ListDataListener {

        public void intervalAdded(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void intervalRemoved(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void contentsChanged(ListDataEvent e) {
            fireTableDataChanged();
        }

    }

    final public E getRowData(int row) throws Exception {
        if (row < listModel.getSize()) {
            return listModel.getElementAt(row);
        }

        throw new Exception("getRowData()[ rowindex: " + row + " greater than size: " + listModel.getSize() + "]");
    }

    @Override
    public int getRowCount() {
        return listModel.getSize();
    }

}
