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
import java.text.DecimalFormat;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.product.ProductSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class InventoryItemTableModel extends ActionTableModel<InventoryItem> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    static int columnWidth = 100;

    static public enum Columns {

        InventoryItemId(0, InventoryItemTableModel.columnWidth, String.class, "Inventory Item Id", false),
        InventoryItemTypeID(1, InventoryItemTableModel.columnWidth, String.class, "InventoryItem Type ID", true),
        StatusID(2, InventoryItemTableModel.columnWidth, String.class, "Status ID", false),
        DateTimeReceived(3, InventoryItemTableModel.columnWidth, String.class, "Date Time Received", false),
        ExpireDate(4, InventoryItemTableModel.columnWidth, String.class, "Expire Date", false),
        ProductId(5, InventoryItemTableModel.columnWidth, String.class, "Product Id", false),
        InternalName(6, 150, String.class, "Internal Name", false),
        PartId(7, InventoryItemTableModel.columnWidth, String.class, "Party Id", false),
        LocationSeqId(8, InventoryItemTableModel.columnWidth, String.class, "Location Seq Id", false),
        Type(9, 100, String.class, "Type", false),
        LotId(10, 100, String.class, "Lot Id", false),
        BinNumber(11, 100, String.class, "Bin Number", false),
        SerialNumber(12, 100, String.class, "Serial Number", false),
        SoftIdentifier(13, 100, String.class, "Soft Identifier", false),
        QuantityOnHandTotal(14, 100, String.class, "Quantity On Hand Total", false),
        Transfer(15, 100, String.class, "Transfer", false);

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

    public InventoryItemTableModel() {
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
        InventoryItem invoiceComposite = (InventoryItem) listModel.getElementAt(rowIndex);

        switch (column) {
            case InventoryItemTypeID:
                columnValue = invoiceComposite.getinventoryItemTypeId();
                break;
            case StatusID:
                //              try {
                columnValue = invoiceComposite.getstatusId();// InvoiceTypeSingleton.getInvoiceType(invoiceComposite.getInvoice().getinvoiceTypeId()).getdescription();
//                } catch (Exception ex) {
//                    columnValue = "";
//                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
//                }
                break;
            case InventoryItemId:
                columnValue = invoiceComposite.getinventoryItemId();
                Debug.logInfo("InventoryItemId" + columnValue, "InventorItemId");
                break;
            case DateTimeReceived:
                try {
                    columnValue = invoiceComposite.getdatetimeReceived();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case ExpireDate:
                columnValue = invoiceComposite.getexpireDate();
                break;

            case InternalName: {
                try {
                    columnValue = ProductSingleton.getProduct(invoiceComposite.getProductId()).getproductName();
                } catch (Exception ex) {
                    columnValue = null;
                    Logger.getLogger(InventoryProductTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case ProductId:
                columnValue = invoiceComposite.getProductId();
                break;
            case PartId:
                columnValue = invoiceComposite.getpartyId();
                break;
            case LocationSeqId:
                columnValue = invoiceComposite.getlocationSeqId();
                break;
            case Type:
                columnValue = invoiceComposite.getcomments();
                break;
            case LotId:
                columnValue = invoiceComposite.getlotId();
                break;
            case BinNumber:
                columnValue = invoiceComposite.getbinNumber();
                break;
            case SerialNumber:
                columnValue = invoiceComposite.getserialNumber();
                break;
            case SoftIdentifier:
                columnValue = invoiceComposite.getsoftIdentifier();
                break;
            case QuantityOnHandTotal:
                if (invoiceComposite.getquantityOnHandTotal() != null && invoiceComposite.getavailableToPromiseTotal() != null) {
                    columnValue
                            = new DecimalFormat("#0.##").format(invoiceComposite.getquantityOnHandTotal()) + " / " + new DecimalFormat("#0.##").format(invoiceComposite.getavailableToPromiseTotal());
                }

                break;
            case Transfer:
                columnValue = invoiceComposite.getlocationSeqId();
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
