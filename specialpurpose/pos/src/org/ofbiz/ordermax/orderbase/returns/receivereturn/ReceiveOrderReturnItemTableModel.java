/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns.receivereturn;

/**
 *
 * @author siranjeev
 */
import mvc.model.table.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.base.util.Debug;

import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;
import org.ofbiz.ordermax.orderbase.returns.ReturnReasonSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnTypeSingleton;
//import org.ofbiz.ordermax.orderbase.ORDER_PROD_INTERNALNAME_INDEXSingleton;
//import org.ofbiz.ordermax.payment.ORDER_PROD_ID_INDEXSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ReceiveOrderReturnItemTableModel extends ActionTableModel<ReturnItemComposite> {

    /**
     *
     */
    public static final String module = ReceiveOrderReturnItemTableModel.class.getName();
    private static final long serialVersionUID = 1547542546403627396L;
    final static int decimalWidth = 70;

    public enum Columns {

        ORDER_PROD_SELECT_INDEX(0, 15, Boolean.class, "Sel", false),
        RETURNID(1, decimalWidth, String.class, "Return ID", false),
        RETURNSEQID(2, decimalWidth, String.class, "Seq ID", false),
        ORDER_PROD_ID_INDEX(3, decimalWidth, String.class, "Product Code", false),
        ORDER_PROD_INTERNALNAME_INDEX(4, 200, String.class, "Description", false),
        ORDER_RETURN_QTY_INDEX(5, 90, BigDecimal.class, "Received Qty", true),
        ORDER_RETURNABLE_QTY_INDEX(6, decimalWidth, BigDecimal.class, "Returnable Qty", false),
        ORDER_RETURN_PRICE_INDEX(7, 90, BigDecimal.class, "Return Price", true),
        INVENTORY_LOCATION(8, 150, String.class, "Location", true),
        INVENTORY_SERIALIZED(9, 150, String.class, "Serialized", true),
        INVENTORY_ITEM_STATUS_INDEX(10, 150, String.class, "Intital Inventory Item Status", true);

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

    public static final String EmptyRow = "NO_ID";

    protected String[] columnNames;
//     protected Vector<OrderItemComposite> listModel = new Vector<OrderItemComposite>();
//    private ListModel<ReturnItemComposite> listModel = new DefaultListModel<ReturnItemComposite>();
//    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    @Override
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

//"Date 	OrderItemComposite Nbr 	OrderItemComposite Name 	OrderItemComposite Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	ORDER_PROD_INTERNALNAME_INDEX";
    public ReceiveOrderReturnItemTableModel() {
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
        ReturnItemComposite record = (ReturnItemComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                return record.isSelected();
            case RETURNID: {
//                Debug.logInfo("record.getOrderItem().getproductId(): " + record.getOrderItem().getproductId(), "module"); 

                return record.getReturnItem().getreturnId();
            }
            case RETURNSEQID: {
//                Debug.logInfo("record.getOrderItem().getitemDescription(): " + record.getOrderItem().getitemDescription(), "module");                    
                return record.getReturnItem().getreturnItemSeqId();
            }
            case ORDER_PROD_ID_INDEX: {
//                Debug.logInfo("record.getOrderItem().getproductId(): " + record.getOrderItem().getproductId(), "module"); 

                return record.getReturnItem().getproductId();
            }
            case ORDER_PROD_INTERNALNAME_INDEX: {
//                Debug.logInfo("record.getOrderItem().getitemDescription(): " + record.getOrderItem().getitemDescription(), "module");                    
                return record.getReturnItem().getdescription();
            }

            case ORDER_RETURN_QTY_INDEX: {
                return record.getReturnQty();

            }
            case ORDER_RETURNABLE_QTY_INDEX: {
                return record.getReturnItem().getreturnQuantity();

            }
            case ORDER_RETURN_PRICE_INDEX: {
                return record.getReturnItem().getreturnPrice();
            }

            case INVENTORY_LOCATION: {
                try {
                    org.ofbiz.ordermax.entity.ReturnReason val = ReturnReasonSingleton.getReturnReason(record.getReturnItem().getreturnReasonId());//org.ofbiz.ordermax.entity.ReturnReason) value;
                    return val.getdescription();//.getreturnReasonId();
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "";
            }
            case INVENTORY_SERIALIZED:
                try {
                    org.ofbiz.ordermax.entity.ReturnType val = ReturnTypeSingleton.getReturnReason(record.getReturnItem().getreturnTypeId());//org.ofbiz.ordermax.entity.ReturnReason) value;
                    return val.getdescription();//.getreturnReasonId();
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "";

            case INVENTORY_ITEM_STATUS_INDEX:
                try {
                    org.ofbiz.ordermax.entity.StatusItem val = StatusSingleton.getStatusItem(record.getReturnItem().getstatusId());//org.ofbiz.ordermax.entity.ReturnReason) value;
                    return val.getdescription();//.getreturnReasonId();
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "";

//                return record.getReturnItem().getstatusId();
            default:
                return new Object();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
        ReturnItemComposite record = (ReturnItemComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                record.setSelected((Boolean) value);
                break;
            case ORDER_PROD_ID_INDEX:

                break;

            case ORDER_PROD_INTERNALNAME_INDEX:
                record.getOrderItem().setitemDescription((String) value);
                break;
            case ORDER_RETURN_QTY_INDEX:
                try {
                    //record.setquantity(new BigDecimal((String) value));
                    Debug.logInfo("set ORDER_PRICE_INDEX: " + value.toString(), module);
                    record.setReturnQty((BigDecimal) value);
                    notifyColumnDataChange(rowIndex, columnIndex);
//                    calculateLineTotalAndGst(record);
//                    fireTableCellUpdated(rowIndex, ORDER_LINETOTAL_INDEX);

                } catch (Exception e) {
                    record.getReturnItem().setreturnQuantity(BigDecimal.ZERO);
                    Debug.logError(e, "module");
                }

                break;
            case ORDER_RETURN_PRICE_INDEX:
                record.setReturnPrice((BigDecimal) value);
                break;
            case INVENTORY_LOCATION:
//                org.ofbiz.ordermax.entity.ReturnReason val = (org.ofbiz.ordermax.entity.ReturnReason) value;
//                record.getReturnItem().setreturnReasonId(val.getreturnReasonId());   
                org.ofbiz.ordermax.entity.ReturnReason val;
                try {
                    val = ReturnReasonSingleton.getReturnReasonFromDesc((String) value);
                    record.getReturnItem().setreturnReasonId(val.getreturnReasonId());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                record.getReturnItem().setreturnReasonId("RTN_NOT_WANT");
                break;
            case INVENTORY_ITEM_STATUS_INDEX:

                org.ofbiz.ordermax.entity.StatusItem val1;
                try {
                    val1 = StatusSingleton.getReturnStatusFromDesc("INV_SERIALIZED_STTS", (String) value);
                    record.getReturnItem().setstatusId(val1.getstatusId());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                record.getReturnItem().setstatusId("INV_RETURNED");
                break;
            case INVENTORY_SERIALIZED:

                org.ofbiz.ordermax.entity.ReturnType val2;
                try {
                    val2 = ReturnTypeSingleton.getReturnTypeFromDesc((String) value);
                    record.getReturnItem().setreturnTypeId(val2.getreturnTypeId());
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(ReceiveOrderReturnItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                record.getReturnItem().setreturnTypeId("RTN_REFUND");
                break;
            default:
                System.out.println("invalid index");
        }
//         record.updateOrderMax();
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    /*
     public boolean hasEmptyRow() {
     if (listModel.getSize() <= 0) return false;
         
     OrderItemComposite OrderItemComposite = (OrderItemComposite)listModel.getElementAt(listModel.getSize()- 1);
         
     if (OrderItemComposite.getOrderItem().getproductId()==null || OrderItemComposite.getOrderItem().getproductId().trim().equals("") ){
     return true;
     }
     else{
     return false;
     }
     }
     */
    /*
     public void addEmptyRow(OrderItemComposite composite) {
     listModel.(composite);
     fireTableRowsInserted(
     listModel.size() - 1,
     listModel.size() - 1);
     }
     */
    public List<ReturnItemComposite> getSelectedRows() {

        List<ReturnItemComposite> list = new ArrayList<ReturnItemComposite>();
        for (int i = 0; i < listModel.getSize(); ++i) {
            ReturnItemComposite ar = listModel.getElementAt(i);
//        for(OrderItemComposite ar : listModel.){
            if (ar.isSelected()) {
                list.add(ar);
            }
        }

        return list;
    }
    /* 
     public void addRows(List<OrderItemComposite> rows) {
     //        listModel.addAll(rowsOrderItemMax
     //          fireTableRowsInserted(
     //                   listModel.size() - 1,
     //                  listModel.size() - 1);
     listModel.clear();
     for (OrderItemComposite auRecord : rows) {
     //            OrderItemComposite auRecord = OrderItemComposite.createRecord(record);

     listModel.add(auRecord);
     Debug.logInfo(auRecord.getOrderItem().getitemDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
     fireTableRowsInserted(
     listModel.size() - 1,
     listModel.size() - 1);

     }

     }

     public void setDataList(List<OrderItemComposite> rows) {
     listModel.clear();
     for (OrderItemComposite auRecord : rows) {
     listModel.add(auRecord);
     Debug.logInfo(auRecord.getOrderItem().getitemDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
     fireTableRowsInserted(
     listModel.size() - 1,
     listModel.size() - 1);

     }

     }
     */

    boolean calculateLineTotalAndGst(OrderItemComposite record) {
        record.setTotalAmount(record.getOrderItem().getquantity().multiply(record.getOrderItem().getunitPrice()));
        return true;
    }
}
