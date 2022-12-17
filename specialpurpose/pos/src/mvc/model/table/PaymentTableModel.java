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
import javax.swing.JTable;
import org.ofbiz.base.util.UtilValidate;

import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class PaymentTableModel extends ActionTableModel<PaymentComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        PAYMENTID(0, 70, String.class, "Payment Id", true),
        PAYMENTTYPE(1, 150, String.class, "Payment Type", false),
        STATUS(2, 100, String.class, "Status", false),
        COMMENTS(3, 270, String.class, "Comments", false),
        FROMPARTY(4, 220, String.class, "From Party", false),
        TOPARTY(5, 220, String.class, "To Party", false),
        EFFECTIVEDATE(6, 90, java.sql.Timestamp.class, "Effective Date", true),
        AMOUNT(7, 70, BigDecimal.class, "Amount", false),
        OUTSTANDINGAMOUNT(7, 100, BigDecimal.class, "Outstanding amount", false);

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

    public PaymentTableModel() {
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
        switch (column) {
            case PAYMENTID:
                columnValue = paymentComposite.getPayment().getpaymentId();
                break;
            case PAYMENTTYPE:
                try {
                    columnValue = PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case STATUS:
                try {
                    columnValue = StatusSingleton.getStatusItem(paymentComposite.getPayment().getstatusId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case COMMENTS:

                columnValue = paymentComposite.getPayment().getcomments();

                break;
            case FROMPARTY:
                try {
                      if(UtilValidate.isNotEmpty(paymentComposite.getPayment().getpartyIdFrom())){
                    columnValue = PartyListSingleton.getAccount(paymentComposite.getPayment().getpartyIdFrom()).getDisplayName();
                      }
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case TOPARTY:
                try {
                    if(UtilValidate.isNotEmpty(paymentComposite.getPayment().getpartyIdTo())){
                    columnValue = PartyListSingleton.getAccount(paymentComposite.getPayment().getpartyIdTo()).getDisplayName();
                    }  
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case EFFECTIVEDATE:
                columnValue = paymentComposite.getPayment().geteffectiveDate();
                break;

            case AMOUNT:
                columnValue = paymentComposite.getPayment().getamount();
                break;
            case OUTSTANDINGAMOUNT:
                columnValue = paymentComposite.getOutstandingAmount();
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

}
