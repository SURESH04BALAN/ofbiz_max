/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.party.PartyListSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class GenerateInvoiceTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        SELECT,
        ORDERID,
        PARTYNAME,
        ORDERAMOUNT,
        ORDERBUTTON
    }

    private ListModel<Order> listModel = new DefaultListModel<Order>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public GenerateInvoiceTableModel() {
    }

    public final void setListModel(ListModel<Order> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        return listModel.getSize();
    }

    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Order order = (Order) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        switch (column) {
            case SELECT:
                order.setSelected((Boolean) aValue);
                break;
            case ORDERID:
                order.setOrderId((String) aValue);
                break;
        }
    }

    public Object getValueAt(final int rowIndex, int columnIndex) {
        Object columnValue = null;
        Order order = (Order) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case SELECT:
                columnValue = order.isSelected();
                break;
            case ORDERID:
                columnValue = order.getOrderId() == null ? "" : order.getOrderId();
                break;

            case PARTYNAME:
                Account acct;
                try {
                    acct = PartyListSingleton.getAccount(order.getPartyId());
                    columnValue = acct.getDisplayName() == null ? "" : acct.getDisplayName();                    
                } catch (Exception ex) {
                    Logger.getLogger(GenerateInvoiceTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
       
                break;
            case ORDERAMOUNT:
                columnValue = order.getGrandTotal() == null ? BigDecimal.ZERO : order.getGrandTotal();
                break;
            case ORDERBUTTON:

                final JButton button = new JButton("Load Order");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        OrderMaxOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),
                                "Button clicked for row " + rowIndex);
                    }
                });
                return button;

        }
        /*        switch (column) {
         case FIRSTNAME:
         columnValue = order.getOrder().getOrderId();
         break;
         case LASTNAME:
         columnValue = order.getOrderName();
         break;
         default:
         columnValue = getAddressObject(order, column);
         break;
         }
         */
        return columnValue;
    }

    public int getColumnWidth(int columnIndex) {

        Columns[] columns = Columns.values();
        Columns columnsObj = columns[columnIndex];
        int width = 0;
        switch (columnsObj) {
            case SELECT:
                width = 75;
                break;

            case ORDERID:
                width = 100;
                break;
            case PARTYNAME:
                width = 400;
                break;

            case ORDERAMOUNT:
            case ORDERBUTTON:
                width = 100;
                break;
        }
        return width;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns columnsObj = columns[columnIndex];

        switch (columnsObj) {
            case SELECT:
                return Boolean.class;

            case ORDERID:
            case PARTYNAME:
                return String.class;
            case ORDERAMOUNT:
                return BigDecimal.class;
            case ORDERBUTTON:
                return JButton.class;
            default:
                return String.class;
        }

    }

    @Override
    public String getColumnName(int column) {
        Columns[] columns = Columns.values();
        Columns columnsObj = columns[column];

        String columnName = null;
        switch (columnsObj) {
            case SELECT:
                columnName = "Select";
                break;
            case ORDERID:
                columnName = "ORDER ID";
                break;

            case PARTYNAME:
                columnName = "PARTY NAME";
                break;

            case ORDERAMOUNT:
                columnName = "ORDER AMOUNT";
                break;

            case ORDERBUTTON:
                columnName = "Show Order";
                break;
            default:
                break;
        }

        return columnName;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        Columns[] columns = Columns.values();
        Columns columnsObj = columns[column];
        switch (columnsObj) {
            case PARTYNAME:
            case ORDERAMOUNT:
                return false;

            case SELECT:
            case ORDERID:
            case ORDERBUTTON:
                return true;
            default:
                return true;
        }
    }

    private class ListModelChangeListener implements ListDataListener {

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

}
