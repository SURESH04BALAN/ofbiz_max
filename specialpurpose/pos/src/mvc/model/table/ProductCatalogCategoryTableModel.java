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
import org.ofbiz.ordermax.entity.ProdCatalogCategory;
import org.ofbiz.ordermax.entity.ProdCatalogCategoryType;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.product.catalog.ProdCatalogCategoryTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductCatalogCategoryTableModel extends ActionTableModel<ProdCatalogCategory> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 150;

    static public enum Columns {

        CATEGORYID(0, defaultWidth, String.class, "Category Id", true),
        CATEGORYNAME(1, 300, String.class, "Category Name", false),
        CATALOGCATEGORYNAME(2, 300, String.class, "Catalog Category Name", false),
        FROMDATETIME(3, defaultWidth, java.sql.Timestamp.class, "From Date Time", false),
        THRUDATE(4, defaultWidth, java.sql.Timestamp.class, "Thru Date", false),
        SEQ(5, defaultWidth, java.math.BigDecimal.class, "SEQ", false);

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

    public ProductCatalogCategoryTableModel() {
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
        ProdCatalogCategory prodCatalogCategory = (ProdCatalogCategory) listModel.getElementAt(rowIndex);

        switch (column) {
            case CATEGORYID:
                columnValue = prodCatalogCategory.getproductCategoryId();
                break;
            case CATEGORYNAME: {
                try {
                    ProductCategory product = ProductCategorySingleton.getProductCategory(prodCatalogCategory.getproductCategoryId());
                    columnValue = product.getCategoryName();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeDetailTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case CATALOGCATEGORYNAME: {
                try {
                    ProdCatalogCategoryType product = ProdCatalogCategoryTypeSingleton.getProdCatalogCategoryType(prodCatalogCategory.getprodCatalogCategoryTypeId());
                    columnValue = product.getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeDetailTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case FROMDATETIME:

                columnValue = prodCatalogCategory.getfromDate();

                break;

            case THRUDATE:
                columnValue = prodCatalogCategory.getthruDate();
                break;
            case SEQ:
                columnValue = prodCatalogCategory.getsequenceNum();
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
