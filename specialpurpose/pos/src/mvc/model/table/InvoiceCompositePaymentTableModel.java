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
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class InvoiceCompositePaymentTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {
        INVOICEDATE(0, 30, java.sql.Timestamp.class, "INVOICE DATE", false), 
        INVOICEID(1, 50, String.class, "INVOICE ID", true), 
        INVOICETYPE(2, 50, String.class, "INVOICE TYPE", false), 
        STATUS(3, 75, String.class, "STATUS", false), 
        REFERENCE(4, 75, String.class, "REFERENCE", false), 
        TOTAL(7, 75, BigDecimal.class, "INVOICE TOTAL", false), 
        OUTSTANDINGAMOUNT(8, 85, BigDecimal.class, "OUTSTANDING AMOUNT", false),
        ALLOCATION(5, 75, BigDecimal.class, "ALLOCATION", true);
        
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

    private ListModel<InvoiceComposite> listModel = new DefaultListModel<InvoiceComposite>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public InvoiceCompositePaymentTableModel() {
    }

    public final void setListModel(ListModel<InvoiceComposite> listModel) {
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
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        InvoiceComposite invoiceComposite = (InvoiceComposite) listModel.getElementAt(rowIndex);

        switch (column) {
            case INVOICEID:
                columnValue = invoiceComposite.getInvoice().getinvoiceId();
                break;
            case INVOICETYPE:               
                try {
                    columnValue = InvoiceTypeSingleton.getInvoiceType(invoiceComposite.getInvoice().getinvoiceTypeId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case INVOICEDATE:
                columnValue = invoiceComposite.getInvoice().getinvoiceDate();
                break;
            case STATUS:                    
                try {
                    columnValue = StatusSingleton.getStatusItem(invoiceComposite.getInvoice().getstatusId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            case REFERENCE:                    
                columnValue = invoiceComposite.getInvoice().getdescription();
                break;                
                
            case ALLOCATION:
                columnValue = invoiceComposite.getAllocatedAmount();//.getPartyFrom().getDisplayName();
                break;
            case TOTAL:
                columnValue = invoiceComposite.getTotalAmount();
                break;
            case OUTSTANDINGAMOUNT:
                columnValue = invoiceComposite.getOutstandingAmount();
                break;
            default:
                break;
        }

        return columnValue;
    }
    
    @Override    
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)     {
    
        InvoiceComposite payApp = (InvoiceComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        
//PAYMENTAPPLICATIONID, INVOICEID,	PAYMENTID, AMOUNTAPPLIED, BILLINGACCOUNTID, TAXAUTHOGEOID, ALLOCATION,	ALLOCATION, TRACKINGCODE, 	STATUS
        switch (column) {
            case ALLOCATION:
                
                payApp.setAllocatedAmount((BigDecimal)aValue);
                break;
        }
        Debug.logInfo("payApp.setamountApplied: " + payApp.getAllocatedAmount(), "hello ");
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.className;    }

    @Override
    public String getColumnName(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.headerString;

    }
    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
   public void clearAllListeners() {
        listeners.clear();
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
