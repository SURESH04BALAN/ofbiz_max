/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

/**
 *
 * @author administrator
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;

import static org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor.module;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;

public class InvoiceEntryTableModel extends AbstractTableModel {

    public enum Columns {

        SEL(0, 10, Boolean.class, "SEL", true),
        ITEMNO(1, 25, String.class, "Item No", false),
        PRODUCT_ID(2, 50, String.class, "Product Id", true),
        DESCRIPTION(3, 250, String.class, "Description", true),
        QUANTITY(4, 25, BigDecimal.class, "Quantity", true),
        UNIT_PRICE(5, 25, BigDecimal.class, "Unit Price", true),
        INVOICE_ITEM_TYPE(6, 75, String.class, "Invoice Item Type", true),
        OVERRIDE_GL_ACCOUNT_ID(7, 75, String.class, "Override Gl Account Id", true),
        HIDDEN_INDEX(8, 1, String.class, "", false),
        TOTAL(9, 50, BigDecimal.class, "Total", false);

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
    /*
     public static final int SEL = 0;
     public static final int ITEMNO = 1;
     public static final int QUANTITY = 2;
     public static final int INVOICE_ITEM_TYPE = 3;
     public static final int PRODUCT_ID = 4;
     public static final int DESCRIPTION = 5;
     public static final int OVERRIDE_GL_ACCOUNT_ID = 6;
     public static final int UNIT_PRICE = 7;
     public static final int HIDDEN_INDEX = 8;
     public static final int TOTAL = 9;
     */
    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public static final String EmptyRow = "NO_ID";

    protected Vector<InvoiceItemComposite> dataVector;

    public InvoiceEntryTableModel() {
        dataVector = new Vector<InvoiceItemComposite>();
    }

    public String getColumnName(int columnIndex) {

        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.headerString;
        
/*        switch (column) {
            case SEL:
                return "Sel";
            case ITEMNO:
                return "Item No";
            case QUANTITY:
                return "Quantity";

            case PRODUCT_ID:
                return "Product Id";
            case DESCRIPTION:
                return "Description";

            case INVOICE_ITEM_TYPE:
                return "Invoice Item Type";

            case OVERRIDE_GL_ACCOUNT_ID:
                return "Override Gl Account Id";
            case UNIT_PRICE:
                return "Unit Price";

            case TOTAL:
                return "Total";

            case HIDDEN_INDEX:
                return "";
            default:
                return "";
        }
*/
    }

    public boolean isCellEditable(int row, int columnIndex) {

        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.isEditable;
/*
        switch (column) {

            case SEL:
            case ITEMNO:
            case QUANTITY:
            case PRODUCT_ID:
            case DESCRIPTION:
                return true;

            case INVOICE_ITEM_TYPE:
            case OVERRIDE_GL_ACCOUNT_ID:
            case UNIT_PRICE:

            case TOTAL:
            case HIDDEN_INDEX:
                return false;
            default:
                return false;
        }
        */
    }

    public Class getColumnClass(int columnIndex) {

        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.className;
/*
        switch (column) {
            case SEL:
                return Boolean.class;
            case INVOICE_ITEM_TYPE:
            case ITEMNO:
            case OVERRIDE_GL_ACCOUNT_ID:
            case PRODUCT_ID:
            case DESCRIPTION:

                return String.class;
            case UNIT_PRICE:
            case QUANTITY:
            case TOTAL:
                return BigDecimal.class;
            default:
                return Object.class;
        }
*/
    }

    @Override
    public Object getValueAt(int row, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        InvoiceItemComposite record = (InvoiceItemComposite) dataVector.get(row);

        switch (column) {
            case SEL:
                columnValue = new Boolean(record.isSelected());
                break;
            case ITEMNO: {
                columnValue = record.getOrderItem().getInvoiceItemSeqId();
                break;
            }

            case QUANTITY: {
                columnValue = record.getOrderItem().getquantity();
                break;
            }
            case INVOICE_ITEM_TYPE: {
                columnValue = record.getOrderItem().getinvoiceItemTypeId();
                break;
            }
            case OVERRIDE_GL_ACCOUNT_ID:
                columnValue = record.getOrderItem().getoverrideGlAccountId();
                break;
            case PRODUCT_ID:
                columnValue = record.getOrderItem().getproductId();
                break;
            case DESCRIPTION: {
                columnValue = record.getOrderItem().getdescription();
                break;
            }
            case UNIT_PRICE: {
                columnValue = record.getOrderItem().getamount();
                break;
            }
            case TOTAL: {
                BigDecimal val = record.getOrderItem().getamount().multiply(record.getOrderItem().getquantity());
                columnValue = val;
                break;
            }
            default:
                columnValue = new Object();
        }

        return columnValue;
    }

    @Override
    public void setValueAt(Object value, int row, int columnIndex) {
        InvoiceItemComposite record = (InvoiceItemComposite) dataVector.get(row);

        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {

//            case SEL:
//                record.setSelected((Boolean) value);
//                break;
            case QUANTITY:
                record.getOrderItem().setQuantity((BigDecimal) value);
                break;
            case ITEMNO:
                record.getOrderItem().setInvoiceItemSeqId((String) value);
                break;

            case INVOICE_ITEM_TYPE:
                record.getOrderItem().setInvoiceItemTypeId((String) value);
                break;
            case OVERRIDE_GL_ACCOUNT_ID:
                record.getOrderItem().setOverrideGlAccountId((String) value);
                break;
            case PRODUCT_ID:
                try {
                    record.getOrderItem().setproductId((String) value);
                    notifyColumnDataChange(row, columnIndex);
                } catch (Exception e) {
                    record.getOrderItem().setquantity(BigDecimal.ZERO);
                    Debug.logError(e, "module");
                }

                break;
            case DESCRIPTION:
                try {
                    record.getOrderItem().setdescription((String) value);
                } catch (Exception e) {
                    record.getOrderItem().setAmount(BigDecimal.ZERO);
                    Debug.logError(e, "module");
                }

                break;
            case TOTAL:
                record.getOrderItem().setTotalItemAmount((BigDecimal) value);
                break;
            default:
                System.out.println("invalid index");
        }
//         record.updateOrderMax();
        fireTableCellUpdated(row, columnIndex);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return Columns.values().length;
    }

    public boolean hasEmptyRow() {
        if (dataVector.size() <= 0) {
            return false;
        }

        InvoiceItemComposite invoiceComposite = (InvoiceItemComposite) dataVector.get(dataVector.size() - 1);

        if (invoiceComposite.getOrderItem().getproductId() == null || invoiceComposite.getOrderItem().getproductId().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void addEmptyRow(InvoiceItemComposite composite) {
        dataVector.add(composite);
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }

    public InvoiceItemComposite getRowData(int row) throws Exception {
        if (row < dataVector.size()) {
            return dataVector.get(row);
        }

        throw new Exception("getRowData()[ rowindex: " + row + " greater than size: " + dataVector.size() + "]");
    }

    public List<InvoiceItemComposite> getSelectedRows() {

        List<InvoiceItemComposite> list = new ArrayList<InvoiceItemComposite>();
        for (InvoiceItemComposite ar : dataVector) {
            if (ar.isSelected()) {
                list.add(ar);
            }
        }

        return list;
    }

    public void addRows(List<InvoiceItemComposite> rows) {
//        dataVector.addAll(rowsOrderItemMax
        //          fireTableRowsInserted(
        //                   dataVector.size() - 1,
        //                  dataVector.size() - 1);
        dataVector.clear();
        for (InvoiceItemComposite auRecord : rows) {
//            InvoiceItemComposite auRecord = InvoiceItemComposite.createRecord(record);

            dataVector.add(auRecord);
            Debug.logInfo(auRecord.getOrderItem().getDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);
        }

    }

    public void setDataList(List<InvoiceItemComposite> rows) {
        dataVector.clear();
        for (InvoiceItemComposite auRecord : rows) {
            dataVector.add(auRecord);
            Debug.logInfo(auRecord.getOrderItem().getDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }

    boolean calculateLineTotalAndGst(InvoiceItemComposite record) {
        //sur     record.setTotalAmount( record.getOrderItem().getquantity().multiply(record.getOrderItem().getunitPrice()));
        return true;
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    protected void notifyColumnDataChange(int row, int column) {

        ActionEvent event = new RowColumnActionEvent(this, 1, "productId", row, column, null);
        for (ActionListener listener : listeners) {
            Debug.logInfo("table Model change Action listner", module);
            listener.actionPerformed(event); // broadcast to all
        }

        fireTableCellUpdated(row, Columns.TOTAL.getColumnIndex());
    }
}
