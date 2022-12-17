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
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.InvoiceType;
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
public class InvoiceCompositeTableModel extends ActionTableModel<InvoiceComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        INVOICEID(0, 100, String.class, "Invoice Id", true), 
        INVOICETYPE(1, 100, String.class, "Invoice Type", false), 
        INVOICEDATE(2, 100, java.sql.Timestamp.class, "Invoice Date", false), 
        STATUS(3, 150, String.class, "Status", false), 
        DESCRIPTION(4, 75, String.class, "Description", false), 
        FROMPARTY(5, 250, String.class, "Vendor Party", false), 
        TOPARTY(6, 200, String.class, "To party", false),
        TOTAL(7, 100, BigDecimal.class, "Amount", false), 
        PAIDAMOUNT(8, 100, BigDecimal.class, "Paid Amount", false),         
        OUTSTANDINGAMOUNT(9, 100, BigDecimal.class, "Outstanding Amount", false);
        
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

    public InvoiceCompositeTableModel() {
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
            case DESCRIPTION:                    
                columnValue = invoiceComposite.getInvoice().getdescription();
                break;                
                
            case TOPARTY:
                columnValue = invoiceComposite.getPartyTo().getDisplayName();
                break;
            case FROMPARTY:
                columnValue = invoiceComposite.getPartyFrom().getDisplayName();
                break;
            case TOTAL:
                columnValue = invoiceComposite.getTotalAmount();
                break;
                
            case PAIDAMOUNT:
                columnValue = invoiceComposite.getPaidAmount();
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
}
