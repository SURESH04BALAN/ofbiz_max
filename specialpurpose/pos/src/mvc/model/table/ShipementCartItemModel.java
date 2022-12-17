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
import java.math.BigDecimal;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;

import org.ofbiz.ordermax.composite.Order;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ShipementCartItemModel extends RowTableModel<ShoppingCartItem> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        PRODUCTCODE(0, 25, String.class, "Product Code", false),
        PRODUCTNAME(1, 50, String.class, "Product Name", false),
        QUANTITY(2, 50, String.class, "Quantity", false),
        DYNAMIC(3, 1, String.class, "", false);
//        BALANCEAMOUNT(8, 75, BigDecimal.class, "Balance Amount", false),        

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

//"Date 	Order Nbr 	Order Name 	Order Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	Status";
//    private ListModel<ShoppingCartItem> listModel = new DefaultListModel<ShoppingCartItem>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();
    private Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ShipementCartItemModel(List<String> columnNames) {
        super(columnNames);
    }
    /*
     public final void setListModel(ListModel<Order> listModel) {
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
     Order order = (Order) listModel.getElementAt(rowIndex);
     Columns[] columns = Columns.values();
     Columns column = columns[columnIndex];
     //DATE, PRODUCTCODE,	PRODUCTNAME, QUANTITY, BILLFROMPARTY, BILLTOPARTY, PRODUCTSTORE,	AMOUNT, TRACKINGCODE, 	STATUS
     switch (column) {

     case PRODUCTCODE:
     columnValue = order.getOrderId();
     break;
     case PRODUCTNAME:
     columnValue = order.getOrderName();
     break;
     case QUANTITY:
                
     try {
     columnValue = OrderTypeSingleton.getOrderType(order.getOrderType()).getdescription();
     } catch (Exception ex) {
     columnValue = "";
     Logger.getLogger(ShipementCartItemModel.class.getName()).log(Level.SEVERE, null, ex);
     }
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
     */
    int dynamicColStart = Columns.values().length-1;

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        ShoppingCartItem shoppingCartItem = (ShoppingCartItem) modelData.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = null;

        if ((columns.length - 1) > columnIndex) {
            column = columns[columnIndex];
        } else {
            column = Columns.DYNAMIC;
        }

        switch (column) {

            case PRODUCTCODE:
                columnValue = shoppingCartItem.getProductId();
                break;
            case PRODUCTNAME:
                columnValue = shoppingCartItem.getDescription();
                break;
            case QUANTITY:

                try {
                    columnValue = shoppingCartItem.getQuantity().toString();
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(ShipementCartItemModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case DYNAMIC:
                Debug.logError("dynamic value: " + columnIndex + " dynamicColStart: " + dynamicColStart, "module");
                if (columnIndex >= dynamicColStart && order!=null) {
                    columnValue = order.getItemShipGroupQty(shoppingCartItem, columnIndex-dynamicColStart);
                }

            default:
                break;
        }

        return columnValue;
    }
    @Override
     public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Object columnValue = null;
        ShoppingCartItem shoppingCartItem = (ShoppingCartItem) modelData.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = null;

        if ((columns.length - 1) > columnIndex) {
            column = columns[columnIndex];
        } else {
            column = Columns.DYNAMIC;
        }
             
         switch (column) {
            case DYNAMIC:
                Debug.logError("dynamic value: " + columnIndex + " dynamicColStart: " + dynamicColStart, "module");
                if (columnIndex >= dynamicColStart && order!=null) {
                    String valStr = (String) value;
                    BigDecimal valDec = new BigDecimal(valStr);
                    order.setItemShipGroupQty(shoppingCartItem, 0, valDec, columnIndex-dynamicColStart);
                }
            default:
                break;

         }
//         record.updateOrderMax();
         fireTableCellUpdated(rowIndex, columnIndex);
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
