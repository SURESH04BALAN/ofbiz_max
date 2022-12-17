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
import org.ofbiz.ordermax.entity.InventoryItemAndLocation;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class InventoryItemAndLocationTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        InventoryItemId(0, 20, String.class, "Inventory Item Id", false),
        InventoryItemTypeID(1, 50, String.class, "InventoryItem Type ID", true),
        StatusID(2, 50, String.class, "Status ID", false),
        DateTimeReceived(3, 50, String.class, "Date Time Received", false),
        ExpireDate(4, 75, String.class, "Expire Date", false),
        ProductId(5, 75, String.class, "Product Id", false),
        InternalName(6, 75, String.class, "Internal Name", false),
        PartId(7, 75, String.class, "Party Id", false),
        LocationSeqId(8, 75, String.class, "Location Seq Id", false),
        Type(0, 20, String.class, "Type", false),
        LotId(0, 20, String.class, "Lot Id", false),
        BinNumber(0, 20, String.class, "Bin Number", false),
        SerialNumber(0, 20, String.class, "Serial Number", false),
        SoftIdentifier(0, 20, String.class, "Soft Identifier", false),
        QuantityOnHandTotal(0, 50, String.class, "Quantity On Hand Total", false),
        Transfer(0, 20, String.class, "Transfer", false);

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

    private ListModel<InventoryItemAndLocation> listModel = new DefaultListModel<InventoryItemAndLocation>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public InventoryItemAndLocationTableModel() {
    }

    public final void setListModel(ListModel<InventoryItemAndLocation> listModel) {
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
        InventoryItemAndLocation invoiceComposite = (InventoryItemAndLocation) listModel.getElementAt(rowIndex);

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

            case InternalName:
                columnValue = invoiceComposite.getinternalName();
                break;
            case ProductId:
                columnValue = invoiceComposite.getproductId();
                break;
            case PartId:
                columnValue = invoiceComposite.getpartyId();
                break;
            case LocationSeqId:
                columnValue = invoiceComposite.getlocationSeqId();
                break;
            case Type:
                columnValue = invoiceComposite.getinventoryMessage();
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
                columnValue = 

                new DecimalFormat("#0.##").format(invoiceComposite.getquantityOnHandTotal()) + " / " + new DecimalFormat("#0.##").format(invoiceComposite.getavailableToPromiseTotal());
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
