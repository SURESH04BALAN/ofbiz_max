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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.OrderSummaryView;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class OrderSummaryTableModel extends ActionTableModel<OrderSummaryView> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        DATE(0, 100, java.sql.Timestamp.class, "Date", false),
        DUEDATE(1, 100, java.sql.Timestamp.class, "Due Date", false),
        TYPE(2, 200, String.class, "Type", false),
        NUMBER(3, 150, String.class, "Number", true),
        REFERENCE(4, 150, String.class, "Reference", false),
        AMOUNT(5, 150, java.math.BigDecimal.class, "Amount", false),
        DETAILS(6, 270, String.class, "Details", false);

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

    public OrderSummaryTableModel() {
    }

    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        if (column == Columns.NUMBER) {
            OrderSummaryView orderSummaryView = (OrderSummaryView) listModel.getElementAt(row);
            if(orderSummaryView!=null && UtilValidate.isNotEmpty(orderSummaryView.getNumber())){
                return column.isEditable;
            }
            else{
                return false;
            }

        }
        return column.isEditable;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        OrderSummaryView orderSummaryView = (OrderSummaryView) listModel.getElementAt(rowIndex);

        switch (column) {
            case DATE:
                columnValue = orderSummaryView.getDate();

                break;
            case DUEDATE:

                columnValue = orderSummaryView.getDueDate();

                break;
            case TYPE:
                columnValue = orderSummaryView.getType();
                break;
            case NUMBER:
                try {
                    columnValue = orderSummaryView.getNumber();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case REFERENCE:
                columnValue = orderSummaryView.getReference();
                break;

            case AMOUNT:
                columnValue = orderSummaryView.getAmount();
                break;
            case DETAILS:
                columnValue = orderSummaryView.getDetails();
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
