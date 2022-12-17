/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.dataload.posdata;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import java.sql.Timestamp;
import mvc.model.table.*;
import java.util.Observer;

import javax.swing.JTable;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class PosSalesDataTableModel extends ActionTableModel<PosSalesData> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        Date(0, 20, Timestamp.class, "Date", true),
        Sales(1, 20, BigDecimal.class, "Sales", true),
        Eftpos(2, 20, BigDecimal.class, "Eftpos", true),
        Cashout(3, 20, BigDecimal.class, "Cashout", true),
        Cash(4, 20, BigDecimal.class, "Cash", true),
        Total(5, 20, BigDecimal.class, "Total", true),
        Source(6, 20, String.class, "Source", true),
        Destination(6, 20, String.class, "Destination", true),        
       ;

        private int columnIndex;
        private int columnWidth;

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
        
        protected  String headerString;
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

    public PosSalesDataTableModel() {
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        PosSalesData productComposite = listModel.getElementAt(rowIndex);

        switch (column) {
            case Date:
                columnValue = productComposite.date;
                break;
                
        case Sales:
                columnValue = productComposite.sales;
                break;
        case Eftpos:
                columnValue = productComposite.eftpos;
                break;
        case Cashout:
                columnValue = productComposite.cashout;
                break;
        case Cash:
                columnValue = productComposite.cash;
                break;
        case Total:
                columnValue = productComposite.total;
                break;           
        case Source:
                columnValue = productComposite.source;
                break;     
        case Destination:
                columnValue = productComposite.destination;
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
