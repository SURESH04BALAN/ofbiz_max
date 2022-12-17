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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;


import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
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
public class PurchaseOrderTableModel extends AbstractTableModel {

    /**
     *
     */
    public static final String module = PurchaseOrderTableModel.class.getName();    
    private static final long serialVersionUID = 1547542546403627396L;
    final static int decimalWidth = 90;

    public enum Columns {
        ORDER_PROD_SELECT_INDEX(0, 30, Boolean.class, "Sel", true),
        ORDER_PROD_ID_INDEX(1, 150, String.class, "Product Code", true),
        ORDER_PROD_INTERNALNAME_INDEX(2, 300, String.class, "Description", false),
        ORDER_QTY_INDEX(3, decimalWidth, BigDecimal.class, "Pur. Qty", true),
        ORDER_PRICE_INDEX(4, decimalWidth, BigDecimal.class, "Pur. Price", true),
        ORDER_ONHAND_QTY_INDEX(5, decimalWidth, BigDecimal.class, "QOH", false),
        ORDER_ATP_QTY_INDEX(6, decimalWidth, BigDecimal.class, "AVP", false),        
        ORDER_LIST_PRICE_INDEX(7, decimalWidth, BigDecimal.class, "Sell Price", false),
        ORDER_LASTPRICE_INDEX(8, decimalWidth, BigDecimal.class, "Last Price", false),
        ORDER_AVG_COST(9, decimalWidth, BigDecimal.class, "Avg. Cost Price", false),        
        HIDDEN_INDEX(10, 1, String.class, "", false),
        ORDER_GST_PRICE_INDEX(11, decimalWidth, BigDecimal.class, "GST", false),
        ORDER_LINETOTAL_INDEX(12, decimalWidth, BigDecimal.class, "Line Total", false),
        ORDER_SUPPLIER_PROD_ID_INDEX(13, decimalWidth, String.class, "Supplier Code", true);

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

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
    
    public static final String EmptyRow = "NO_ID";
    
     protected String[] columnNames;
//     protected Vector<OrderItemComposite> listModel = new Vector<OrderItemComposite>();
    private ListModel<OrderItemComposite> listModel = new DefaultListModel<OrderItemComposite>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

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
    
    public PurchaseOrderTableModel() {
    }

    public final void setListModel(ListModel<OrderItemComposite> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }

     public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
         OrderItemComposite record = (OrderItemComposite)listModel.getElementAt(rowIndex);        
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

         switch (column) {
             case ORDER_PROD_SELECT_INDEX:
                return record.isSelected();
             case ORDER_SUPPLIER_PROD_ID_INDEX:{
    
             //   return record.getLastPrice();                
                if( record.getSupplierProductList() != null && 
                        record.getSupplierProductList().getCurrentSupplierProduct()!=null &&
                        record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct()!=null){
                    return record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct().getsupplierProductId();
                }
                else{
                    return "";
                }
             
             }
             
             case ORDER_PROD_ID_INDEX:{
//                Debug.logInfo("record.getOrderItem().getproductId(): " + record.getOrderItem().getproductId(), "module"); 
                
                return record.getOrderItem().getproductId();
             }
             case ORDER_PROD_INTERNALNAME_INDEX:{
//                Debug.logInfo("record.getOrderItem().getitemDescription(): " + record.getOrderItem().getitemDescription(), "module");                    
                return record.getOrderItem().getitemDescription();
             }
             case ORDER_ONHAND_QTY_INDEX:
                return record.getQuantyOnHand();    
             case ORDER_ATP_QTY_INDEX:
                return record.getAtp();                                     
            case ORDER_QTY_INDEX:
                return record.getOrderItem().getquantity();                
            case ORDER_PRICE_INDEX:{
                return record.getOrderItem().getunitPrice();                
                /*if(record.getShoppingCartItem()!=null){         
                    return record.getShoppingCartItem().getBasePrice();
                }
                else{
                    return BigDecimal.ZERO;
                }*/
            }
            case ORDER_LIST_PRICE_INDEX:{
//                return record.getOrderItem().getunitListPrice();                
                if( record.getProductItemPrice() != null && 
                        record.getProductItemPrice().getListPrice()!=null &&
                        record.getProductItemPrice().getListPrice().getCurrentPrice()!=null){
                    return record.getProductItemPrice().getListPrice().getCurrentPrice().getProductPrice().getprice();
                }
                else{
                    return BigDecimal.ZERO;
                }
            }
            case ORDER_LASTPRICE_INDEX:{
             //   return record.getLastPrice();                
                /*if( record.getSupplierProductList() != null && 
                        record.getSupplierProductList().getCurrentSupplierProduct()!=null &&
                        record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct()!=null){
                    return record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct().getlastPrice();
                }
                else{
                    return BigDecimal.ZERO;
                }*/
                  return BigDecimal.ZERO;
            }
            case ORDER_AVG_COST:
                return record.averageCost;                
            case ORDER_GST_PRICE_INDEX:
                return record.getGstAmount();
            case ORDER_LINETOTAL_INDEX:      {  
                if(record.getShoppingCartItem()!=null){
                Debug.logInfo(" Order Entry Table Model: Price" + record.getShoppingCartItem().getBasePrice() 
                        + " quantity" +  record.getShoppingCartItem().getQuantity() + 
                        " Rental adj:" + record.getShoppingCartItem().getRentalAdjustment() + 
                        " other adj:" + record.getShoppingCartItem().getOtherAdjustments(), module);  
                return record.getShoppingCartItem().getItemSubTotal();
                }
                else{
                return BigDecimal.ZERO;
                }
            }
             default:
                return new Object();
         }
     }

    @Override
     public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
         OrderItemComposite record = (OrderItemComposite)listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
             
         switch (column) {
             case ORDER_PROD_SELECT_INDEX:
                record.setSelected((Boolean)value);
                break;
             case ORDER_PROD_ID_INDEX:
                record.getOrderItem().setproductId((String)value);
                break;
             case ORDER_SUPPLIER_PROD_ID_INDEX:
                record.setSupplierProductId((String)value);
                break;
                 
             case ORDER_PROD_INTERNALNAME_INDEX:
                record.getOrderItem().setitemDescription((String)value);
                break;
             case ORDER_ONHAND_QTY_INDEX:
             case ORDER_ATP_QTY_INDEX                 :
//                record.setQuantyOnHand((BigDecimal)value);                 
                 break;
            case ORDER_QTY_INDEX:                               
                try {
                    Debug.logInfo( "ORDER_QTY_INDEX: "+ value.toString(), module);                    
                    record.getOrderItem().setquantity((BigDecimal)value); 
                    notifyColumnDataChange(rowIndex,columnIndex);                    
                } catch (Exception e) {
                    record.getOrderItem().setquantity(BigDecimal.ZERO);
                    Debug.logError(e, "module");
                }
                
                break;
            case ORDER_PRICE_INDEX:
                try {
                    //record.setquantity(new BigDecimal((String) value));
                    Debug.logInfo( "set ORDER_PRICE_INDEX: "+ value.toString(), module);                                        
                    record.getOrderItem().setunitPrice((BigDecimal)value); 
                    notifyColumnDataChange(rowIndex,columnIndex);
//                    calculateLineTotalAndGst(record);
//                    fireTableCellUpdated(rowIndex, ORDER_LINETOTAL_INDEX);
                    
                } catch (Exception e) {
                    record.getOrderItem().setunitPrice(BigDecimal.ZERO);
                    Debug.logError(e, "module");                    
                }
                                
                break;
            case ORDER_LIST_PRICE_INDEX:
                record.getOrderItem().setunitListPrice((BigDecimal)value);                                
                break;
            case ORDER_LASTPRICE_INDEX:
                record.setLastPrice((BigDecimal)value);                
                break;
            case ORDER_GST_PRICE_INDEX:
                record.setGstAmount((BigDecimal)value);
                break;
            case ORDER_LINETOTAL_INDEX:               
                record.setTotalAmount((BigDecimal)value);                 
                break;
             default:
                System.out.println("invalid index");
         }
//         record.updateOrderMax();
         fireTableCellUpdated(rowIndex, columnIndex);
     }

     public int getRowCount() {
         return listModel.getSize();
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
     
      public OrderItemComposite getRowData(int row) throws Exception {
        if (row < listModel.getSize()) {
            return listModel.getElementAt(row);
        }
      
        throw new Exception("getRowData()[ rowindex: " + row + " greater than size: " + listModel.getSize()+ "]");
    }

    public List<OrderItemComposite> getSelectedRows(){
       
        List<OrderItemComposite> list = new ArrayList<OrderItemComposite>();
        for(int i=0; i < listModel.getSize(); ++i){
            OrderItemComposite ar = listModel.getElementAt(i);
//        for(OrderItemComposite ar : listModel.){
            if(ar.isSelected()){
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
        record.setTotalAmount( record.getOrderItem().getquantity().multiply(record.getOrderItem().getunitPrice()));
        return true;
    }      
   
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    public void clearActionListener() {
        listeners.clear();
    }
    
    protected void notifyColumnDataChange(int row, int column){
            ActionEvent event = new RowColumnActionEvent(this, 1, "productId", row, column, null);
            for (ActionListener listener : listeners) {

                listener.actionPerformed(event); // broadcast to all
            }
            
//            fireTableCellUpdated(row, Columns.ORDER_LINETOTAL_INDEX.columnIndex);
    }

    
    /*
    public int getRowCount() {
        return listModel.getSize();
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
                columnValue = orderItemComposite.getPayment().getORDER_PROD_SELECT_INDEX();
                break;
            case ORDER_PROD_ID_INDEX:
                try {
                    columnValue = ORDER_PROD_ID_INDEXSingleton.getORDER_PROD_ID_INDEX(orderItemComposite.getPayment().getORDER_PROD_ID_INDEXId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(SalesOrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }                
                break;
            case ORDER_PROD_INTERNALNAME_INDEX:
                try {
                    columnValue = ORDER_PROD_INTERNALNAME_INDEXSingleton.getORDER_PROD_INTERNALNAME_INDEXItem(orderItemComposite.getPayment().getORDER_PROD_INTERNALNAME_INDEXId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(SalesOrderTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case ORDER_QTY_INDEX:

                    columnValue = orderItemComposite.getPayment().getORDER_QTY_INDEX();
  
                break;
            case ORDER_PRICE_INDEX:
                columnValue = orderItemComposite.getPartyPaymentFrom().getDisplayName();
                break;
            case ORDER_ONHAND_QTY_INDEX:
                columnValue = orderItemComposite.getPartyPaymentTo().getDisplayName();
                break;
            case ORDER_LIST_PRICE_INDEX:
                columnValue = orderItemComposite.getPayment().getORDER_LIST_PRICE_INDEX();
                break;

            case ORDER_LASTPRICE_INDEX:
                columnValue = orderItemComposite.getPayment().getamount();
                break;
            case HIDDEN_INDEX:
                columnValue = orderItemComposite.getOutstandingAmount();
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
