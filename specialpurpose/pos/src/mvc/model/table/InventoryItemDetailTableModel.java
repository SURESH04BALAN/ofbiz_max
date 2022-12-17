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

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.entity.InventoryItemAndDetail;
import org.ofbiz.ordermax.entity.InventoryItemAndDetail;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class InventoryItemDetailTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultColWidth = 70;
    static public enum Columns {                                
        inventoryItemId(0, defaultColWidth, String.class, "Inv. Item Id", false), 
        inventoryItemDetailSeqId(1, defaultColWidth, String.class, "inv. Item Detail SeqId", false),                        
        productId(2, defaultColWidth, String.class, "Product Id", true), 
        effectiveDate(3, 85, String.class, "Effective Date", false),        
        quantityOnHandTotal(4, defaultColWidth, java.math.BigDecimal.class, "QOH Total", false), 
        quantityOnHandDiff(5, defaultColWidth, java.math.BigDecimal.class, "QOH Diff", false),        
        availableToPromiseTotal(6, defaultColWidth, java.math.BigDecimal.class, "ATP Total", false), 
        availableToPromiseDiff(7, defaultColWidth, java.math.BigDecimal.class, "ATP Diff", false),         
        serialNumber(8, defaultColWidth, String.class, "Serial Number", false), 
        reasonEnumId(9, defaultColWidth, String.class, "Reason", true),         
        softIdentifier(10, defaultColWidth, String.class, "SoftI dentifier", false), 
        description(11, 100, String.class, "Description", false), 
        orderId(12, defaultColWidth, String.class, "Order Id", true), 
        orderItemSeqId(13, defaultColWidth, String.class, "Order Item Seq Id", false), 
        shipmentId(14, defaultColWidth, String.class, "Shipment Id", false), 
//        inventoryItemDetailSeqId(6, 150, String.class, "Internal Name", false),                
        shipmentItemSeqId(15, defaultColWidth, String.class, "Shipment Item Seq Id", false), 
        workEffortId(16, defaultColWidth, String.class, "Work Effort Id", false),
        returnItem(17, defaultColWidth, String.class, "Return Item", false),        
        returnItemSeqId(18, defaultColWidth, String.class, "Return Item Seq Id", false);
        
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

    private ListModel<InventoryItemAndDetail> listModel = new DefaultListModel<InventoryItemAndDetail>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public InventoryItemDetailTableModel() {
    }

    public final void setListModel(ListModel<InventoryItemAndDetail> listModel) {
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
        InventoryItemAndDetail inventoryItemAndDetail = (InventoryItemAndDetail) listModel.getElementAt(rowIndex);

        switch (column) {
            case inventoryItemId:
                columnValue = inventoryItemAndDetail.getinventoryItemId();
                break;
            case inventoryItemDetailSeqId:               
  //              try {
                    columnValue = inventoryItemAndDetail.getinventoryItemDetailSeqId();// InvoiceTypeSingleton.getInvoiceType(inventoryItemAndDetail.getInvoice().getinvoiceTypeId()).getdescription();
//                } catch (Exception ex) {
//                    columnValue = "";
//                    Logger.getLogger(OrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
//                }
                break;
        case productId:
                columnValue = inventoryItemAndDetail.getproductId();
            break;
        case effectiveDate:
                columnValue = inventoryItemAndDetail.geteffectiveDate();
            break;
        case quantityOnHandTotal:
                columnValue = inventoryItemAndDetail.getquantityOnHandTotal();
            break;
        case quantityOnHandDiff:
                columnValue = inventoryItemAndDetail.getquantityOnHandDiff();
            break;
        case availableToPromiseTotal:
                columnValue = inventoryItemAndDetail.getavailableToPromiseTotal();
            break;
        case availableToPromiseDiff:
                columnValue = inventoryItemAndDetail.getavailableToPromiseDiff();
            break;
        case serialNumber:
                columnValue = inventoryItemAndDetail.getserialNumber();
            break;
        case reasonEnumId:
                columnValue = inventoryItemAndDetail.getreasonEnumId();
            break;
        case softIdentifier:
                columnValue = inventoryItemAndDetail.getsoftIdentifier();
            break;
        case description:
                columnValue = inventoryItemAndDetail.getdescription();
            break;
        case orderId:
                columnValue = inventoryItemAndDetail.getorderId();
            break;
        case orderItemSeqId:
                columnValue = inventoryItemAndDetail.getorderItemSeqId();
            break;
        case shipmentId:
                columnValue = inventoryItemAndDetail.getshipmentId();
            break;
//        inventoryItemDetailSeqId(6, 150, String.class, "Internal Name", false),                
        case shipmentItemSeqId:
                columnValue = inventoryItemAndDetail.getshipmentItemSeqId();
            break;
        case workEffortId:
                columnValue = inventoryItemAndDetail.getworkEffortId();
            break;
        case returnItem:
                columnValue = inventoryItemAndDetail.getreturnId();
            break;
        case returnItemSeqId:
                columnValue = inventoryItemAndDetail.getreturnItemSeqId();
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
        return column.className;    }

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
