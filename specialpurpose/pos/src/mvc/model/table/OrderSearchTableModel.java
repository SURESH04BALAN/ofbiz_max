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
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.party.PartyListSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class OrderSearchTableModel extends ActionTableModel<OrderHeaderAndRoleSummary> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {
        ORDERTYPE(3, 100, String.class, "Order Type", false),
        ORDERNBR(1, 70, String.class, "Order Id", false),
        BILLTOPARTY(5, 220, String.class, "Bill To", false),
        DATE(0, 90, java.sql.Timestamp.class, "Date", false),

        ORDERNAME(2, 150, String.class, "Order Name", false),

        BILLFROMPARTY(4, 220, String.class, "Bill From", false),

        STATUS(6, 100, String.class, "Status", false),
        TRACKINGCODE(7, 70, String.class, "Tracking Code", false),
        PRODUCTSTORE(8, 150, String.class, "Product Store", false),
        AMOUNT(9, 100, BigDecimal.class, "Amount", false);
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

    public OrderSearchTableModel() {
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
        OrderHeaderAndRoleSummary order = (OrderHeaderAndRoleSummary) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
//DATE, ORDERNBR,	ORDERNAME, ORDERTYPE, BILLFROMPARTY, BILLTOPARTY, PRODUCTSTORE,	AMOUNT, TRACKINGCODE, 	STATUS
        switch (column) {
            case DATE:
                columnValue = order.getorderDate();
                break;
            case ORDERNBR:
                columnValue = order.getorderId();
                break;
            case ORDERNAME:
                columnValue = order.getOrderName();
                break;
            case ORDERTYPE:

                try {
                    columnValue = OrderTypeSingleton.getOrderType(order.getorderTypeId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderSearchTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case BILLFROMPARTY:
                try {
                    if (UtilValidate.isNotEmpty(order.getBillFrom())) {
                        Account acct = PartyListSingleton.getAccount(order.getBillFrom());
                        columnValue = acct.getDisplayName();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderSearchTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case BILLTOPARTY:
                try {
                    if (UtilValidate.isNotEmpty(order.getBillTo())) {
                        Account acct = PartyListSingleton.getAccount(order.getBillTo());
                        columnValue = acct.getDisplayName();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderSearchTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case PRODUCTSTORE:
                columnValue = order.getProductStoreId();
                break;

            case AMOUNT:
                columnValue = order.gettotalGrandAmount();
                break;
            case TRACKINGCODE:
                columnValue = order.getTrackingCode();// order.getOrderHeader().getTerminalId();
                break;
            case STATUS:
                try {
                    columnValue = StatusSingleton.getStatusItem(order.getstatusId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderSearchTableModel.class.getName()).log(Level.SEVERE, null, ex);
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

}
