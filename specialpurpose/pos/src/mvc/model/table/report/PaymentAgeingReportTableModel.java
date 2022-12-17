/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table.report;

/**
 *
 * @author siranjeev
 */
import mvc.model.table.*;
import java.math.BigDecimal;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class PaymentAgeingReportTableModel extends ActionTableModel<PaymentComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        TOPARTY(0, 300, String.class, "Supplier Name", false),
        CURRENT_DUE(1, 100, BigDecimal.class, "Current", false),
        THIRTYTOSIXTY(2, 100, BigDecimal.class, "30 to 59 days", false),
        SIXTYTONINETY(3, 100, BigDecimal.class, "60 to 89 days", false),
        NINETYTOHUNDAREDEIGHTY(4, 200, BigDecimal.class, "90 to 179", false),
        NINETYTOHUNDAREDEIGHTYPLUS(5, 200, BigDecimal.class, "180+", false),
        TOTALOUTSTANDING(6, 100, BigDecimal.class, "Total Outstanding", false);

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

    public PaymentAgeingReportTableModel() {
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
            case CURRENT_DUE:
                columnValue = paymentComposite.getPayment().getpaymentId();
                break;
            case THIRTYTOSIXTY:
                try {
                    columnValue = PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentAgeingReportTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SIXTYTONINETY:
                try {
                    columnValue = StatusSingleton.getStatusItem(paymentComposite.getPayment().getstatusId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentAgeingReportTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case NINETYTOHUNDAREDEIGHTY:

                columnValue = paymentComposite.getPayment().getcomments();

                break;
            case TOTALOUTSTANDING:

                try {
                    columnValue = PartyListSingleton.getAccount(paymentComposite.getPayment().getpartyIdFrom()).getDisplayName();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case TOPARTY:

                try {
                    columnValue = PartyListSingleton.getAccount(paymentComposite.getPayment().getpartyIdTo()).getDisplayName();
                } catch (Exception ex) {
                    Logger.getLogger(PaymentTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case NINETYTOHUNDAREDEIGHTYPLUS:
                columnValue = paymentComposite.getPayment().geteffectiveDate();
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
