/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author siranjeev
 */
public class InteractiveTableModel extends AbstractTableModel {

    public static final int ORDER_ID = 0;
    public static final int RETURN_NEXT_ROW = 1;
    public static final int PARTY_NAME = 2;
    public static final int ORDER_STATUS = 3;

    public static final int INVOICE_IDS = 4;
    public static final int INVOICE_STATUS = 5;
    public static final int ORDER_AMOUNT = 6;
    public static final int INVOICE_AMOUNT = 7;

    public static final int ORDER_BUTTON_INDEX = 8;

    public static final int HIDDEN_INDEX = 1;

    protected String[] columnNames;
    protected Vector<OrderInvoiceGenerateRecord> dataVector;

    public InteractiveTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new Vector<OrderInvoiceGenerateRecord>();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case ORDER_ID:
            case ORDER_BUTTON_INDEX:
                return true;
            case PARTY_NAME:
            case HIDDEN_INDEX:
            case ORDER_STATUS:
            case INVOICE_IDS:
            case INVOICE_STATUS:
            case ORDER_AMOUNT:
            case INVOICE_AMOUNT:

                return false;
            default:
                return true;
        }
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case ORDER_ID:
            case PARTY_NAME:
            case ORDER_STATUS:
            case INVOICE_IDS:
            case INVOICE_STATUS:
                return String.class;
            case ORDER_BUTTON_INDEX:
                return JButton.class;
            case ORDER_AMOUNT:
            case INVOICE_AMOUNT:
                return BigDecimal.class;
            default:
                return Object.class;
        }
    }

    public Object getValueAt(final int row, int column) {
        OrderInvoiceGenerateRecord record = dataVector.get(row);
        switch (column) {
            case ORDER_ID:
                return record.getOrderId();
            case PARTY_NAME:
                return record.getPartyName();
            case ORDER_STATUS:
                return record.getOrderStatus();
            case ORDER_BUTTON_INDEX:
                JButton button = null;
                if (record.getOrderButton() == null) {
                    button = new JButton("Default");
//                    record.setOrderButton(button);
                } else {
                    button = record.getOrderButton();
                }
//        PurchaseOrderAction purchaserAction = new PurchaseOrderAction("Purchaser Order", session, desktoppane); 
//        button.setAction(purchaserAction);
/*                    button.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent arg0) {
                 OrderMaxOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),
                 "Button clicked for row " + row);
                 }
                 });
                 */
                return button;
            case INVOICE_IDS:
                return record.getInvoiceIds();
            case INVOICE_STATUS:
                return record.getInvoiceStatus();
            case ORDER_AMOUNT:
                return record.getOrderAmount();
            case INVOICE_AMOUNT:
                return record.getInvoiceAmount();

            default:
                return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
        OrderInvoiceGenerateRecord record = (OrderInvoiceGenerateRecord) dataVector.get(row);
        switch (column) {
            case ORDER_ID:
                record.setOrderId((String) value);
                break;
            case PARTY_NAME:
                record.setPartyName((String) value);
                break;
            case ORDER_STATUS:
                record.setOrderStatus((String) value);
                break;
            default:
                System.out.println("invalid index");
        }
        if (column != ORDER_BUTTON_INDEX) {
            fireTableCellUpdated(row, column);
        }
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
        OrderInvoiceGenerateRecord audioRecord = (OrderInvoiceGenerateRecord) dataVector.get(dataVector.size() - 1);
        if (audioRecord.getOrderId().trim().equals("")/* &&
                 audioRecord.getArtist().trim().equals("") &&
                 audioRecord.getAlbum().trim().equals("")*/) {
            return true;
        } else {
            return false;
        }
    }

    public OrderInvoiceGenerateRecord addEmptyRow() {
        OrderInvoiceGenerateRecord ordRec = new OrderInvoiceGenerateRecord();
        dataVector.add(ordRec);
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
        return ordRec;
    }

    public OrderInvoiceGenerateRecord getRowData(int rowIndex) {
        if (dataVector.size() > rowIndex) {
            return (OrderInvoiceGenerateRecord) dataVector.get(rowIndex);
        }

        return null;
    }

    public List<OrderInvoiceGenerateRecord> getDataList() {
        ArrayList<OrderInvoiceGenerateRecord> list = new ArrayList<OrderInvoiceGenerateRecord>();
        for (OrderInvoiceGenerateRecord record : dataVector) {
            if (record.getOrder() != null) {
                list.add(record);
            }
        }
        return list;
    }

}
