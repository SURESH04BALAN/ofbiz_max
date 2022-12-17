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

import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.price.ProductPriceBulkUpdate;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductPriceBulkEditTableModel extends ActionTableModel<ProductPriceBulkUpdate> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {
//        public String[] m_colNames = {"Product Id", "Name", "Def Price", "List Price", "Avg Cost", "Supplier Id", "Supp Prod Id", "Last Price", "Scan Code", ""};
//        public Class[] m_colTypes = {String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class, String.class, BigDecimal.class, String.class, String.class};

        ProductId(0, 100, String.class, "Product Id", false),
        ProductName(1, 300, String.class, "Product Name", false),
        DefPrice(2, 100, BigDecimal.class, "Def Price", true),
        ListPrice(3, 100, BigDecimal.class, "List Price", true),
        AvgCost(4, 100, BigDecimal.class, "Avg Cost", true);
//        TOPARTY(5, 100, String.class, "To Party", false),
//        EFFECTIVEDATE(6, 100, java.sql.Timestamp.class, "Effective Date", true),
//        AMOUNT(7, 100, BigDecimal.class, "Amount", false),
//        OUTSTANDINGAMOUNT(7, 100, BigDecimal.class, "Outstanding amount", false);

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

    public ProductPriceBulkEditTableModel() {
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

//            Debug.logInfo("getValueAt row: " + rowIndex + " column: " + col, module);
        if (rowIndex == 0) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            if (column.equals(Columns.ProductId)) {
                return "DEFAULT";
            }

            return new String();
        }

        Object columnValue = null;
        ProductPriceBulkUpdate paymentComposite = (ProductPriceBulkUpdate) listModel.getElementAt(rowIndex-1);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        switch (column) {
            case ProductId:
                columnValue = paymentComposite.getProduct().getproductId();
                break;
            case ProductName:

                columnValue = paymentComposite.getProduct().getproductName();

                break;
            case DefPrice:
                columnValue = paymentComposite.getDefaultPrice().get(0).getprice();
                break;
            case ListPrice:
                columnValue = paymentComposite.getListPrice().get(0).getprice();
                break;
            case AvgCost:
                columnValue = paymentComposite.getAvgCost().get(0).getprice();
                break;

            default:
                break;
        }

        return columnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        if (rowIndex == 0) {
            setAllRows(value, columnIndex);
            return;
        }
        Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
        ProductPriceBulkUpdate paymentComposite = (ProductPriceBulkUpdate) listModel.getElementAt(rowIndex-1);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case DefPrice:
                paymentComposite.getDefaultPrice().get(0).setprice((BigDecimal) value);
                break;
            case ListPrice:
                paymentComposite.getListPrice().get(0).setprice((BigDecimal) value);
                break;
            case AvgCost:
                paymentComposite.getAvgCost().get(0).setprice((BigDecimal) value);
                break;

            default:
                System.out.println("invalid index");
        }
//         record.updateOrderMax();
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setAllRows(Object value, int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        for (int rowIndex = 0; rowIndex < listModel.getSize(); ++rowIndex) {
            ProductPriceBulkUpdate paymentComposite = (ProductPriceBulkUpdate) listModel.getElementAt(rowIndex);
            switch (column) {
                case DefPrice:
                    paymentComposite.getDefaultPrice().get(0).setprice((BigDecimal) value);
                    break;
                case ListPrice:
                    paymentComposite.getListPrice().get(0).setprice((BigDecimal) value);
                    break;
                case AvgCost:
                    paymentComposite.getAvgCost().get(0).setprice((BigDecimal) value);
                    break;

            }
            fireTableCellUpdated(rowIndex+1, columnIndex);
        }
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
    @Override
    final public int getRowCount() {
        return listModel.getSize() + 1;
    }
}
