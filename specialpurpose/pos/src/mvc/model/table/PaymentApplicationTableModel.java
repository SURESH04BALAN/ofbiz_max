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

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;

import org.ofbiz.ordermax.entity.PaymentApplication;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class PaymentApplicationTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        PAYMENTAPPLICATIONID(0, 20, String.class, "Payment Appication Id", false),
        PAYMENTID(1, 25, String.class, "Payment Id", true),
        INVOICEID(2, 50, String.class, "Invoice Id", false),
        AMOUNTAPPLIED(3, 50, BigDecimal.class, "Amount Applied", false),        
        BILLINGACCOUNTID(4, 100, String.class, "Billing Account Id", true),
        TAXAUTHOGEOID(5, 100, String.class, "Tax Autho Geo Id", false),
        OVERIDEGLID(6, 100, String.class, "Overid Gl Id", true);

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

//"Date 	PaymentApplication Nbr 	PaymentApplication Name 	PaymentApplication Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	Status";
    private ListModel<PaymentApplication> listModel = new DefaultListModel<PaymentApplication>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public PaymentApplicationTableModel() {
    }

    public final void setListModel(ListModel<PaymentApplication> listModel) {
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
        PaymentApplication payApp = (PaymentApplication) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
//PAYMENTAPPLICATIONID, INVOICEID,	PAYMENTID, AMOUNTAPPLIED, BILLINGACCOUNTID, TAXAUTHOGEOID, ALLOCATION,	ALLOCATION, TRACKINGCODE, 	STATUS
        switch (column) {
            case PAYMENTAPPLICATIONID:
                columnValue = payApp.getpaymentApplicationId();
                break;
            case INVOICEID:
                columnValue = payApp.getinvoiceId();
                break;
            case PAYMENTID:
                columnValue = payApp.getpaymentId();
                break;
            case AMOUNTAPPLIED:
                columnValue = payApp.getamountApplied();                
                break;
            case BILLINGACCOUNTID:
                columnValue = payApp.getbillingAccountId();
                break;
            case TAXAUTHOGEOID:
                columnValue = payApp.gettaxAuthGeoId();
                break;
            case OVERIDEGLID:
                columnValue = payApp.getoverrideGlAccountId();
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
