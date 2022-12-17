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
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductStoreCatalogTableModel extends ActionTableModel<ProductStoreCatalog> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 150;

    static public enum Columns {

        PRODUCTSTOREID(0, defaultWidth, String.class, "Product Store Id", true),
        PRODUCTSTORENAME(1, 300, String.class, "Product Store Name", false),
        FROMDATETIME(2, defaultWidth, java.sql.Timestamp.class, "From Date Time", false),
        THRUDATE(3, defaultWidth, java.sql.Timestamp.class, "Thru Date", false),
        SEQ(4, defaultWidth, java.math.BigDecimal.class, "SEQ", false);

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

    public ProductStoreCatalogTableModel() {
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
        ProductStoreCatalog productStoreCatalog = (ProductStoreCatalog) listModel.getElementAt(rowIndex);
       

        switch (column) {
            case PRODUCTSTOREID:
                columnValue = productStoreCatalog.getproductStoreId();
                break;
            case PRODUCTSTORENAME: {
                try {
                    ProductStore product = ProductStoreSingleton.getProductStore(productStoreCatalog.getproductStoreId());
                    columnValue = product.getstoreName();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeDetailTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case FROMDATETIME:

                columnValue = productStoreCatalog.getfromDate();

                break;

            case THRUDATE:
                columnValue = productStoreCatalog.getthruDate();
                break;

            case SEQ:
                columnValue = productStoreCatalog.getsequenceNum();
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
