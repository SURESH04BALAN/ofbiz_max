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
import org.ofbiz.ordermax.base.InvoiceRolePartyPayment;

import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class PaymentEnteryTableModel extends ActionTableModel<InvoiceRolePartyPayment>  {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {
        DATE_COL(0, 100, java.sql.Timestamp.class, "Date", false),
        INVOICE_COL(1, 100, String.class, "Invoice", false),
        REFERANCE_COL(2, 100, String.class, "Reference", false),
        INVOICE_AMT_COL(3, 200, BigDecimal.class, "Invoice Amt", false),
        PAID_AMT_COL(4, 100, BigDecimal.class, "Paid Amt", false),
        BALANCE_AMT_COL(5, 100, BigDecimal.class, "Balance Amt", false),
        ALLOCATION_AMT_COL(6, 100, BigDecimal.class, "Allocation", true),
        BRANCH_COL(7, 100, String.class, "Branch", false);

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

    public PaymentEnteryTableModel() {
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
        InvoiceRolePartyPayment paymentComposite = (InvoiceRolePartyPayment) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
                
        switch (column) {
            case DATE_COL:
                columnValue = paymentComposite.getInvoice().getTimestamp("invoiceDate");
                break;
            case INVOICE_COL:
        
                    columnValue =paymentComposite.getInvoice().getString("invoiceId");
                     
                break;
            case REFERANCE_COL:

                    columnValue = new String();
                
                break;
            case INVOICE_AMT_COL:

                    columnValue = paymentComposite.getInvoiceAmount();
  
                break;
            case PAID_AMT_COL:
                columnValue = paymentComposite.getPaidAmount();
                break;
            case BALANCE_AMT_COL:
                columnValue = paymentComposite.getOutstandingAmount();
                break;
            case ALLOCATION_AMT_COL:
                columnValue = paymentComposite.getAllocationAmount();
                break;

            case BRANCH_COL:
                columnValue = new String();
                break;
  
            default:
                columnValue = new String();                
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

}
