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

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.PartyRolesList;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryMember;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductCategoryMemberTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        CATEGORYID(0, 75, String.class, "Category Id", true),
        CATEGORYNAME(4, 200, String.class, "Category Name", false),
        FROMDATETIME(1, 150, java.sql.Timestamp.class, "From Date Time", false),
        THRUDATETIME(2, 150, java.sql.Timestamp.class, "Thru Date Time", false),
        COMMENTS(3, 75, String.class, "Comments", false),
        SEQUENCENUMBER(4, 200, String.class, "Sequence Num", false),
        QUANTITY(5, 150, String.class, " Quantity", false),
        UPDATE(6, 150, String.class, "Update", false),
        DELETE(7, 150, String.class, "Delete", false);

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

    private ListModel<ProductCategoryMember> listModel = new DefaultListModel<ProductCategoryMember>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public ProductCategoryMemberTableModel() {
    }

    public final void setListModel(ListModel<ProductCategoryMember> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        return listModel.getSize();
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
        ProductCategoryMember productCategory = (ProductCategoryMember) listModel.getElementAt(rowIndex);
        switch (column) {
            case CATEGORYID:
                columnValue = productCategory.getproductCategoryId();
                break;
            case CATEGORYNAME: {

                ProductCategory prodCat;
                try {
                    prodCat = ProductCategorySingleton.getProductCategory(productCategory.getproductCategoryId());
                    columnValue = prodCat.getCategoryName();
                } catch (Exception ex) {
                    Debug.logError(ex, null);
                }
                break;
            }

            case COMMENTS:
                columnValue = productCategory.getcomments();
                break;
            case SEQUENCENUMBER:
                columnValue = productCategory.getsequenceNum();
                break;

            case QUANTITY:

                columnValue = productCategory.getquantity();

                break;
            case UPDATE:
                columnValue = "";
                break;
            case DELETE:

                columnValue = "";

                break;
            case FROMDATETIME:
                columnValue = productCategory.getfromDate();
                break;
            case THRUDATETIME:
                columnValue = productCategory.getthruDate();
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

    private class ListModelChangeListener implements ListDataListener {

        public void intervalAdded(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void intervalRemoved(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void contentsChanged(ListDataEvent e) {
            fireTableDataChanged();
        }

    }

}
