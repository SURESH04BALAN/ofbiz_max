/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table;

import java.math.BigDecimal;

import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.inventory.InventoryItemTypeSingleton;
import org.ofbiz.ordermax.inventory.RejectionReasonSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ReceiveInventoryTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    /*
     public enum Columns {

     SELECT,
     SHIPMENT_SQ,
     ITEM_NAME,
     QTY_RECEIVED,
     QTY_REJECTED,
     QTY_ORDERED,
     PER_UNIT_PRICE,
     INVENTORY_ITEM_TYPE,
     LOCATION,
     REJECTION_REASON,
     LOT_ID,
     OWNER
     }
     */
    final static int decimalWidth = 100;

    public enum Columns {

        SELECT(0, 15, Boolean.class, "Sel", true),
        SHIPMENT_SQ(1, decimalWidth, String.class, "Shipment Sq", true),
        ITEM_NAME(2, 200, String.class, "Item Name", false),
        QTY_RECEIVED(3, decimalWidth, BigDecimal.class, "Qty Received", true),
        QTY_REJECTED(4, decimalWidth, BigDecimal.class, "Qty Rejected", true),
        QTY_ORDERED(5, decimalWidth, BigDecimal.class, "Qty Ordered", false),
        PER_UNIT_PRICE(6, decimalWidth, BigDecimal.class, "Unit Price", true),
        RECEIVED_DATE(7, decimalWidth, java.sql.Timestamp.class, "Manufacture Date", true),
        MANUFACTUR_DATE(8, decimalWidth, java.sql.Timestamp.class, "Manufacture Date", true),
        EXPIRE_DATE(9, decimalWidth, java.sql.Timestamp.class, "Expire Date", true),
        LOT_ID(10, decimalWidth, String.class, "Lot Id", true),
        INVENTORY_ITEM_TYPE(11, decimalWidth, String.class, "Inventory Item Type", true),
        LOCATION(12, decimalWidth, String.class, "Location", true),
        REJECTION_REASON(13, decimalWidth, String.class, "Rejection Reason", true),
        OWNER(14, decimalWidth, String.class, "Owner", false);

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

    private ListModel<ShipmentReceiptComposite> listModel = new DefaultListModel<ShipmentReceiptComposite>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public ReceiveInventoryTableModel() {
    }

    public final void setListModel(ListModel<ShipmentReceiptComposite> listModel) {
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

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ShipmentReceiptComposite shipmentReceipt = (ShipmentReceiptComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        switch (column) {
            case SELECT:
                shipmentReceipt.setSelected((Boolean) aValue);
                break;
            case QTY_RECEIVED:
                shipmentReceipt.getShipmentReceipt().setquantityAccepted((BigDecimal) aValue);
                break;
            case QTY_REJECTED:
                shipmentReceipt.getShipmentReceipt().setquantityRejected((BigDecimal) aValue);
                break;
            case INVENTORY_ITEM_TYPE:
                org.ofbiz.ordermax.entity.InventoryItemType val;
                try {
                    val = InventoryItemTypeSingleton.getInventoryItemTypeFromDesc((String) aValue);
                    shipmentReceipt.getInventoryItem().setinventoryItemTypeId(val.getinventoryItemTypeId());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(ReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                shipmentReceipt.getInventoryItem().setinventoryItemTypeId("NON_SERIAL_INV_ITEM");
                break;
            case LOCATION:
                shipmentReceipt.getInventoryItem().setlocationSeqId((String) aValue);
                break;
            case REJECTION_REASON:
                org.ofbiz.ordermax.entity.RejectionReason val1;
                try {
                    val1 = RejectionReasonSingleton.getRejectionReasonDesc((String) aValue);
                    shipmentReceipt.getShipmentReceipt().setrejectionId(val1.getrejectionId());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(ReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case LOT_ID:
                shipmentReceipt.getInventoryItem().setlotId((String) aValue);
                break;
            case OWNER:
                shipmentReceipt.getInventoryItem().setownerPartyId((String) aValue);
                break;
            case EXPIRE_DATE: {
                //               try {
                //                   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                   Date parsed = format.parse((String) aValue);
//                    java.sql.Timestamp sqlTime = new java.sql.Timestamp(parsed.getTime());
                shipmentReceipt.getInventoryItem().setexpireDate((java.sql.Timestamp) aValue);

//                } catch (ParseException ex) {
//                    Logger.getLogger(ReceiveInventoryTableModel.class.getName()).log(Level.SEVERE, null, ex);
//                }
                break;
            }

            case MANUFACTUR_DATE: {
                shipmentReceipt.getInventoryItem().setdatetimeManufactured((java.sql.Timestamp) aValue);
                break;
            }

            case RECEIVED_DATE: {
                shipmentReceipt.getInventoryItem().setdatetimeReceived((java.sql.Timestamp) aValue);
                break;
            }
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        ShipmentReceiptComposite shipmentReceipt = (ShipmentReceiptComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case SELECT:
                columnValue = shipmentReceipt.isSelected();
                break;
            case SHIPMENT_SQ:
                columnValue = shipmentReceipt.getOrderItemComposite().getShipmentItem().getshipmentItemSeqId();
                break;
            case ITEM_NAME:
                columnValue = shipmentReceipt.getOrderItemComposite().getOrderItem().getproductId()
                        + " - " + shipmentReceipt.getOrderItemComposite().getOrderItem().getitemDescription();
                break;
            case QTY_RECEIVED:
                columnValue = shipmentReceipt.getShipmentReceipt().getquantityAccepted();
                break;
            case QTY_REJECTED:
                columnValue = shipmentReceipt.getShipmentReceipt().getquantityRejected();
                break;
            case QTY_ORDERED:
                columnValue = shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity();
                break;
            case PER_UNIT_PRICE:
                columnValue = shipmentReceipt.getOrderItemComposite().getOrderItem().getunitPrice();
                break;
            case INVENTORY_ITEM_TYPE:
                try {
                    if (UtilValidate.isNotEmpty(shipmentReceipt.getInventoryItem().getinventoryItemTypeId())) {
                        columnValue = InventoryItemTypeSingleton.getInventoryItemType(shipmentReceipt.getInventoryItem().getinventoryItemTypeId()).getdescription();
                    }
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(ReceiveInventoryTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case LOCATION:
                columnValue = shipmentReceipt.getInventoryItem().getlocationSeqId();
                break;
            case REJECTION_REASON:
                try {
                    if (UtilValidate.isNotEmpty(shipmentReceipt.getShipmentReceipt().getrejectionId())) {
                        columnValue = RejectionReasonSingleton.getRejectionReason(shipmentReceipt.getShipmentReceipt().getrejectionId()).getdescription();
                    }
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(ReceiveInventoryTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case LOT_ID:
                columnValue = shipmentReceipt.getInventoryItem().getlotId();
                break;
            case OWNER:
                columnValue = shipmentReceipt.getInventoryItem().getownerPartyId();
                break;
            case EXPIRE_DATE: {
                columnValue = shipmentReceipt.getInventoryItem().getexpireDate();
                break;
            }
            case MANUFACTUR_DATE: {
                columnValue = shipmentReceipt.getInventoryItem().getdatetimeManufactured();
                break;
            }
            case RECEIVED_DATE: {
                columnValue = shipmentReceipt.getInventoryItem().getdatetimeReceived();
                break;
            }
        }
        /*        switch (column) {
         case FIRSTNAME:
         columnValue = order.getOrder().getOrderHeader().getOrderId();
         break;
         case LASTNAME:
         columnValue = order.getOrderHeader().getOrderName();
         break;
         default:
         columnValue = getAddressObject(order, column);
         break;
         }
         */
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
