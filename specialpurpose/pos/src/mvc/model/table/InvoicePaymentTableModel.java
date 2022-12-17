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

import org.ofbiz.ordermax.composite.PaymentComposite;
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
public class InvoicePaymentTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        DATE(0, 20, java.sql.Timestamp.class, "Date", false),
        INVOICEID(1, 25, String.class, "Order Invoice Id", true),
        REFERENCE(2, 50, String.class, "Reference", false),
        INVOICEAMTOUNT(3, 50, BigDecimal.class, "Invoice Amount", false),
        PAIDAMOUNT(4, 100, BigDecimal.class, "Paid Amount", true),
        BALANCEAMOUNT(5, 100, BigDecimal.class, "Balance Amount", false),
        ALLOCATION(6, 100, BigDecimal.class, "Allocation", true),
        BRANCH(7, 100, String.class, "Branch", false);

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

//"Date 	PaymentComposite Nbr 	PaymentComposite Name 	PaymentComposite Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	Status";
    private ListModel<PaymentComposite> listModel = new DefaultListModel<PaymentComposite>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public InvoicePaymentTableModel() {
    }

    public final void setListModel(ListModel<PaymentComposite> listModel) {
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
        PaymentComposite paymentComposite = (PaymentComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
//DATE, INVOICEID,	REFERENCE, INVOICEAMTOUNT, PAIDAMOUNT, BALANCEAMOUNT, ALLOCATION,	ALLOCATION, TRACKINGCODE, 	STATUS
        switch (column) {
            case DATE:
                columnValue = paymentComposite.getPayment().geteffectiveDate();
                break;
            case INVOICEID:
                columnValue = paymentComposite.getPayment().getpaymentId();
                break;
            case REFERENCE:
                columnValue = paymentComposite.getPayment().getpaymentId();
                break;
            case INVOICEAMTOUNT:
                columnValue = paymentComposite.getPayment().getamount();                
                break;
            case PAIDAMOUNT:
                columnValue = paymentComposite.getPayment().getamount();
                break;
            case BALANCEAMOUNT:
                columnValue = paymentComposite.getPayment().getamount();
                break;
            case ALLOCATION:
                columnValue = "";
                break;

            case BRANCH:
                columnValue = paymentComposite.getPayment().getpaymentId();
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
