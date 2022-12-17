/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.TermTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class OrderTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        DATE(0, 20, java.sql.Timestamp.class, "Date", false),
        ORDERNBR(1, 75, String.class, "Order Nbr", true),
        ORDERNAME(2, 75, String.class, "Order Name", false),
        ORDERTYPE(3, 50, String.class, "Order Type", false),
        BILLFROMPARTY(4, 100, String.class, "Bill From", false),
        BILLTOPARTY(5, 100, String.class, "Bill To", false),
        STATUS(6, 100, String.class, "Status", false),
        TRACKINGCODE(7, 100, String.class, "Tracking Code", false),
        PRODUCTSTORE(8, 100, String.class, "Product Store", false),
        AMOUNT(9, 75, BigDecimal.class, "Amount", false);
//        BALANCEAMOUNT(8, 75, BigDecimal.class, "Balance Amount", false),        

        private int columnIndex;
        private int columnWidth;

        public String getHeaderString() {
            return headerString;
        }

        public void setHeaderString(String headerString) {
            this.headerString = headerString;
        }

        public Class getClassName() {
            return className;
        }

        public void setClassName(Class className) {
            this.className = className;
        }

        public boolean isIsEditable() {
            return isEditable;
        }

        public void setIsEditable(boolean isEditable) {
            this.isEditable = isEditable;
        }
        private String headerString;
        private Class className;
        private boolean isEditable;

        Columns(int index, int width, Class className, String header, boolean edit) {
            columnIndex = index;
            columnWidth = width;
            headerString = header;
            this.className = className;
            isEditable = edit;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public int getColumnWidth() {
            return columnWidth;
        }
    }

//"Date 	Order Nbr 	Order Name 	Order Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	Status";
    private ListModel<Order> listModel = new DefaultListModel<Order>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public OrderTableModel() {
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
    public boolean isCellEditable(int row, int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.isEditable;

    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Order order = (Order) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
//DATE, ORDERNBR,	ORDERNAME, ORDERTYPE, BILLFROMPARTY, BILLTOPARTY, PRODUCTSTORE,	AMOUNT, TRACKINGCODE, 	STATUS
        switch (column) {
            case DATE:
                columnValue = order.getOrderDate();
                break;
            case ORDERNBR:
                columnValue = order.getOrderId();
                break;
            case ORDERNAME:
                columnValue = order.getOrderName();
                break;
            case ORDERTYPE:
                
                try {
                    columnValue = OrderTypeSingleton.getOrderType(order.getOrderType()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case BILLFROMPARTY:
                columnValue = order.getBillFromAccount().getParty().getDisplayName();
                break;
            case BILLTOPARTY:
                columnValue = order.getBillToAccount().getParty().getDisplayName();
                break;
            case PRODUCTSTORE:
                columnValue = "";
                break;

            case AMOUNT:
                columnValue = order.getGrandTotal();
                break;
            case TRACKINGCODE:
                columnValue = order.getTerminalId();
                break;
            case STATUS:
                try {
                    columnValue = StatusSingleton.getStatusItem(order.getOrderStatusId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            default:
                break;
        }

        return columnValue;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.className;
    }

    @Override
    public String getColumnName(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.headerString;
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
