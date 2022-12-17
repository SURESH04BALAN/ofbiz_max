/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.OrderItemComposite;

/**
 *
 * @author siranjeev
 */
public class OrderEntryKeyTableModel extends AbstractTableModel {

    public static final int ORDER_PROD_SELECT_INDEX = 0;
    public static final int ORDER_PROD_ID_INDEX = 1;
    public static final int ORDER_PROD_INTERNALNAME_INDEX = 2;
    public static final int ORDER_ONHAND_QTY_INDEX = 3;
    public static final int ORDER_QTY_INDEX = 4;
    public static final int ORDER_PRICE_INDEX = 5;
    public static final int ORDER_LIST_PRICE_INDEX = 6;
    public static final int ORDER_LASTPRICE_INDEX = 7;
    public static final int ORDER_GST_PRICE_INDEX = 8;
    public static final int ORDER_LINETOTAL_INDEX = 9;
    public static final int HIDDEN_INDEX = 10;
    public static final String EmptyRow = "NO_ID";
    protected String[] columnNames;
    protected Vector<OrderItemComposite> dataVector;

    public OrderEntryKeyTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new Vector<OrderItemComposite>();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {

        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
            case ORDER_PROD_ID_INDEX:
            case ORDER_QTY_INDEX:
            case ORDER_PRICE_INDEX:
                return true;

            case ORDER_PROD_INTERNALNAME_INDEX:
            case ORDER_ONHAND_QTY_INDEX:
            case ORDER_LIST_PRICE_INDEX:
            case ORDER_LASTPRICE_INDEX:
            case ORDER_LINETOTAL_INDEX:
            case ORDER_GST_PRICE_INDEX:
            case HIDDEN_INDEX:

                return false;
            default:
                return false;
        }
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                return Boolean.class;
            case ORDER_PROD_ID_INDEX:
            case ORDER_PROD_INTERNALNAME_INDEX:
                return String.class;
            case ORDER_ONHAND_QTY_INDEX:
                return Integer.class;
            case ORDER_QTY_INDEX:
            case ORDER_PRICE_INDEX:
            case ORDER_LIST_PRICE_INDEX:
            case ORDER_LASTPRICE_INDEX:
            case ORDER_LINETOTAL_INDEX:
            case ORDER_GST_PRICE_INDEX:
                return BigDecimal.class;
            default:
                return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
        if (row >= dataVector.size() || row < 0) {
            return new String("");
        }

        OrderItemComposite record = (OrderItemComposite) dataVector.get(row);
        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                return record.itemSelected;
            case ORDER_PROD_ID_INDEX:
                if (EmptyRow.equals(record.getOrderItem().getproductId())) {
                    return new String("");
                } else {
                    return record.getOrderItem().getproductId();
                }
            case ORDER_PROD_INTERNALNAME_INDEX:
                if (EmptyRow.equals(record.getOrderItem().getproductId())) {
                    return new String("");
                } else {
                    return record.getOrderItem().getitemDescription();
                }
            case ORDER_ONHAND_QTY_INDEX:
                return record.getQuantyOnHand();
            case ORDER_QTY_INDEX:
                return record.getOrderItem().getquantity();
            case ORDER_PRICE_INDEX:
                return record.getOrderItem().getunitPrice();
            case ORDER_LIST_PRICE_INDEX:
                return record.getOrderItem().getunitListPrice();
            case ORDER_LASTPRICE_INDEX:
                return record.lastSalePrice;
            case ORDER_GST_PRICE_INDEX:
                return record.getTotalAmount();
            case ORDER_LINETOTAL_INDEX:
                return record.getGstAmount();

            default:
                return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
        OrderItemComposite record = (OrderItemComposite) dataVector.get(row);
        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                record.itemSelected = (Boolean) value;
                break;
            case ORDER_PROD_ID_INDEX:
                record.getOrderItem().setproductId((String) value);
                break;
            case ORDER_PROD_INTERNALNAME_INDEX:
                record.getOrderItem().setitemDescription((String) value);
                System.out.println("invalid index" + record.getOrderItem().getitemDescription());
//                record.isProductModified = true;                
                break;
            case ORDER_ONHAND_QTY_INDEX:
                record.setQuantyOnHand((BigDecimal) value);
                break;
            case ORDER_QTY_INDEX:
                try {
                    record.getOrderItem().setquantity(new BigDecimal((String) value));
                    calculateLineTotalAndGst(record);
                    fireTableCellUpdated(row, ORDER_LINETOTAL_INDEX);
                } catch (Exception e) {
                    record.getOrderItem().setquantity(BigDecimal.ZERO);
                }
                break;
            case ORDER_PRICE_INDEX:
                try {
                    record.getOrderItem().setunitPrice(new BigDecimal((String) value));
                    calculateLineTotalAndGst(record);
                    fireTableCellUpdated(row, ORDER_LINETOTAL_INDEX);
                } catch (Exception e) {
                    record.getOrderItem().setunitPrice(BigDecimal.ZERO);
                }
                break;
            case ORDER_LIST_PRICE_INDEX:
                try {
                    record.getOrderItem().setunitListPrice(new BigDecimal((String) value));
                } catch (Exception e) {
                    record.getOrderItem().setunitListPrice(BigDecimal.ZERO);
                }
                break;
            case ORDER_LASTPRICE_INDEX:
                try {
//                    record.quantity = new BigDecimal((String) value);
                    record.lastSalePrice = new BigDecimal((String) value);
                } catch (Exception e) {
                    record.lastSalePrice = BigDecimal.ZERO;
                }
                break;
            case ORDER_GST_PRICE_INDEX:
                try {
//                    record.quantity = new BigDecimal((String) value);
                    record.setGstAmount( new BigDecimal((String) value));
                } catch (Exception e) {
                    record.setGstAmount(  BigDecimal.ZERO );
                }
                break;
            case ORDER_LINETOTAL_INDEX:
                try {
//                    record.quantity = new BigDecimal((String) value);
                    record.setTotalAmount( new BigDecimal((String) value));
                } catch (Exception e) {
                    record.setTotalAmount( BigDecimal.ZERO );
                }
                break;
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public boolean hasEmptyRow() {
        if (dataVector.size() == 0) {
            return false;
        }

        OrderItemComposite prodRecord = (OrderItemComposite) dataVector.get(dataVector.size() - 1);
        Debug.logInfo(" Empty: " + prodRecord.getOrderItem().getitemDescription() + "[" + prodRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
        if (EmptyRow.equals(prodRecord.getOrderItem().getproductId()) || prodRecord.getOrderItem().getproductId().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void addEmptyRow(String id, String name) {
        dataVector.add(new OrderItemComposite(id, name));
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fireTableRowsInserted(
                        dataVector.size() - 1,
                        dataVector.size() - 1);
                int row = 0;
                for (OrderItemComposite entry : dataVector) {
                    Debug.logInfo(entry.getOrderItem().getitemDescription() + "[" + entry.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
                    fireTableRowsUpdated(row, ORDER_PROD_SELECT_INDEX);
                }

            }
        });

    }

    public void addRows(List<OrderItemComposite> rows) {
//        dataVector.addAll(rows);
        //          fireTableRowsInserted(
        //                   dataVector.size() - 1,
        //                  dataVector.size() - 1);
        dataVector.clear();
        for (OrderItemComposite entry : rows) {
            dataVector.add(entry);
            Debug.logInfo(entry.getOrderItem().getitemDescription() + "[" + entry.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }

    public void addRow(OrderItemComposite rowItem) {
        dataVector.add(rowItem);
        Debug.logInfo(rowItem.getOrderItem().getitemDescription() + "[" + rowItem.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
        fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }

    public int getRowNumber(String id) {

//        dataVector.addAll(rows);
        //          fireTableRowsInserted(
        //                   dataVector.size() - 1,
        //                  dataVector.size() - 1);
        int row = 0;
        for (Object entry : dataVector) {
            OrderItemComposite val = (OrderItemComposite) entry;
            if (val.getOrderItem().getproductId().equals(id)) {
                return row;
            }
            row++;
        }

        return 0;
    }

    boolean calculateLineTotalAndGst(OrderItemComposite record) {
        record.setTotalAmount( record.getOrderItem().getquantity().multiply(record.getOrderItem().getunitPrice()));
        return true;
    }

    public OrderItemComposite getRowData(int row) throws Exception {
        if (row > dataVector.size()) {
            throw new Exception("getRowData()[ rowindex: " + row + " greater than size: " + dataVector.size() + "]");
        }

        return dataVector.get(row);
    }
}