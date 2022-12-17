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
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class SupplierProductCompositeTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 70;

    static public enum Columns {

        SUPPLIERID(0, defaultWidth, String.class, "Supplier", true),
        SUPPLIERNAME(1, 150, String.class, "Supplier Name", true),
        SUPPLIERPRODUCTID(2, defaultWidth, String.class, "Supplier Product Id", false),
        MINIMUMORDERQTY(3, defaultWidth, java.math.BigDecimal.class, "Minimum order quantity", false),
        ORDERQTYINCREMENT(4, defaultWidth, java.math.BigDecimal.class, "Order qty increments", false),
        SUPPLIERPREFORDERID(5, defaultWidth, String.class, "Supplier Pref Order Id", false),
        AVAILABLEFROMDATE(6, defaultWidth, java.sql.Timestamp.class, "Available from date", false),
        AVAILABLETHRUDATE(7, defaultWidth, java.sql.Timestamp.class, "Available thru date", false),
        QUANTITYUOMID(8, defaultWidth, String.class, "Quantity Uom Id", false),
        LASTPRICE(9, defaultWidth, java.math.BigDecimal.class, "Last Price", false),
        SHIPPINGPRICE(10, defaultWidth, java.math.BigDecimal.class, "Shipping Price", false);

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

    private ListModel<SupplierProductComposite> listModel = new DefaultListModel<SupplierProductComposite>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public SupplierProductCompositeTableModel() {
    }

    public final void setListModel(ListModel<SupplierProductComposite> listModel) {
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
        SupplierProductComposite supplierProductComp = (SupplierProductComposite) listModel.getElementAt(rowIndex);
        SupplierProduct supplierProduct = supplierProductComp.getSupplierProduct();

        switch (column) {
            case SUPPLIERID:
                columnValue = supplierProduct.getpartyId();
                break;
            case SUPPLIERNAME:
                try {
                    Account account = PartyListSingleton.getAccount(supplierProduct.getpartyId());
                    columnValue = account.getDisplayName();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SUPPLIERPRODUCTID:
                columnValue = supplierProduct.getsupplierProductId();
                break;
            case MINIMUMORDERQTY:
                columnValue = supplierProduct.getminimumOrderQuantity();
                break;
            case ORDERQTYINCREMENT:

                columnValue = supplierProduct.getorderQtyIncrements();

                break;
            case SUPPLIERPREFORDERID:
                try {
                    columnValue = SupplierPrefOrderSingleton.geSupplierPrefOrder(supplierProduct.getsupplierPrefOrderId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
//                    Logger.getLogger(SupplierProductCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case AVAILABLEFROMDATE:
                columnValue = supplierProduct.getavailableFromDate();
                break;

            case AVAILABLETHRUDATE:
                columnValue = supplierProduct.getavailableThruDate();
                break;
            case QUANTITYUOMID:
                try {
                    columnValue = UomQuantitySingleton.getUom(supplierProduct.getquantityUomId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
//                    Logger.getLogger(SupplierProductCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case LASTPRICE:
                columnValue = supplierProduct.getlastPrice();
                break;

            case SHIPPINGPRICE:
                columnValue = supplierProduct.getshippingPrice();
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
