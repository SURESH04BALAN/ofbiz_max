/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

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
import org.ofbiz.ordermax.composite.OrderItemComposite;
import java.util.Vector;
 import javax.swing.table.AbstractTableModel;

import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;

 public class OrderEntryTableModel extends AbstractTableModel {
         public static final String module = OrderEntryTableModel.class.getName();
//     public static final int ORDER_PROD_SELECT_INDEX = 0;
//     public static final int ORDER_PROD_ID_INDEX = 1;
//     public static final int ORDER_PROD_INTERNALNAME_INDEX = 2;     
//     public static final int HIDDEN_INDEX = 3;
     
    public static final int ORDER_PROD_SELECT_INDEX = 0;
    public static final int ORDER_PROD_ID_INDEX = 1;    
    public static final int ORDER_PROD_INTERNALNAME_INDEX = 2;
    public static final int ORDER_QTY_INDEX = 3;
    public static final int ORDER_PRICE_INDEX = 4;
    public static final int ORDER_ONHAND_QTY_INDEX = 5;    
    public static final int ORDER_LIST_PRICE_INDEX = 6;
    public static final int ORDER_LASTPRICE_INDEX = 7;
    public static final int HIDDEN_INDEX = 8;
    public static final int ORDER_GST_PRICE_INDEX = 9;
    public static final int ORDER_LINETOTAL_INDEX = 10;
    public static final int ORDER_SUPPLIER_PROD_ID_INDEX = 11;        

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
    
    public static final String EmptyRow = "NO_ID";
    
     protected String[] columnNames;
     protected Vector<OrderItemComposite> dataVector;

     public OrderEntryTableModel(String[] columnNames) {
         this.columnNames = columnNames;
         dataVector = new Vector<OrderItemComposite>();
     }

     public String getColumnName(int column) {
         return columnNames[column];
     }

     public boolean isCellEditable(int row, int column) {
        switch (column) {
            case ORDER_PROD_SELECT_INDEX:
            case ORDER_SUPPLIER_PROD_ID_INDEX:                
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
             case ORDER_PROD_INTERNALNAME_INDEX:                 
             case ORDER_SUPPLIER_PROD_ID_INDEX:                 
             case ORDER_PROD_ID_INDEX:
                return String.class;
            case ORDER_ONHAND_QTY_INDEX:
            case ORDER_QTY_INDEX:
            case ORDER_PRICE_INDEX:
            case ORDER_LIST_PRICE_INDEX:
            case ORDER_LASTPRICE_INDEX:
            case ORDER_GST_PRICE_INDEX:
            case ORDER_LINETOTAL_INDEX:               
                 return BigDecimal.class;                 
             default:
                return Object.class;
         }
     }

 

     public Object getValueAt(int row, int column) {
         OrderItemComposite record = (OrderItemComposite)dataVector.get(row);
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
            case ORDER_QTY_INDEX:
                return record.getOrderItem().getquantity();                
            case ORDER_PRICE_INDEX:{
                //return record.getOrderItem().getunitPrice();                
                if(record.getShoppingCartItem()!=null){         
                    return record.getShoppingCartItem().getBasePrice();
                }
                else{
                    return BigDecimal.ZERO;
                }
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
                if( record.getSupplierProductList() != null && 
                        record.getSupplierProductList().getCurrentSupplierProduct()!=null &&
                        record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct()!=null){
                    return record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct().getlastPrice();
                }
                else{
                    return BigDecimal.ZERO;
                }
                
            }
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
     public void setValueAt(Object value, int row, int column) {
         OrderItemComposite record = (OrderItemComposite)dataVector.get(row);
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
                record.setQuantyOnHand((BigDecimal)value);                 
                 break;
            case ORDER_QTY_INDEX:                               
                try {
                    record.getOrderItem().setquantity((BigDecimal)value); 
                    notifyColumnDataChange(row,column);                    
                } catch (Exception e) {
                    record.getOrderItem().setquantity(BigDecimal.ZERO);
                    Debug.logError(e, "module");
                }
                
                break;
            case ORDER_PRICE_INDEX:
                try {
                    //record.setquantity(new BigDecimal((String) value));
                    record.getOrderItem().setunitPrice((BigDecimal)value); 
                    notifyColumnDataChange(row,column);
//                    calculateLineTotalAndGst(record);
//                    fireTableCellUpdated(row, ORDER_LINETOTAL_INDEX);
                    
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
         fireTableCellUpdated(row, column);
     }

     public int getRowCount() {
         return dataVector.size();
     }

     public int getColumnCount() {
         return columnNames.length;
     }

     public boolean hasEmptyRow() {
         if (dataVector.size() <= 0) return false;
         
         OrderItemComposite OrderItemComposite = (OrderItemComposite)dataVector.get(dataVector.size() - 1);
         
         if (OrderItemComposite.getOrderItem().getproductId()==null || OrderItemComposite.getOrderItem().getproductId().trim().equals("") ){
             return true;
         }
         else{
             return false;
         }
     }

     public void addEmptyRow(OrderItemComposite composite) {
         dataVector.add(composite);
         fireTableRowsInserted(
            dataVector.size() - 1,
            dataVector.size() - 1);
     }

     
      public OrderItemComposite getRowData(int row) throws Exception {
        if (row < dataVector.size()) {
            return dataVector.get(row);
        }
      
        throw new Exception("getRowData()[ rowindex: " + row + " greater than size: " + dataVector.size() + "]");
    }

    public List<OrderItemComposite> getSelectedRows(){
       
        List<OrderItemComposite> list = new ArrayList<OrderItemComposite>();
        for(OrderItemComposite ar : dataVector){
            if(ar.isSelected()){
                list.add(ar);
            }
        }
        
        return list;
    }
      
   public void addRows(List<OrderItemComposite> rows) {
//        dataVector.addAll(rowsOrderItemMax
        //          fireTableRowsInserted(
        //                   dataVector.size() - 1,
        //                  dataVector.size() - 1);
        dataVector.clear();
        for (OrderItemComposite auRecord : rows) {
//            OrderItemComposite auRecord = OrderItemComposite.createRecord(record);

            dataVector.add(auRecord);
            Debug.logInfo(auRecord.getOrderItem().getitemDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }

   public void setDataList(List<OrderItemComposite> rows) {
       dataVector.clear();
        for (OrderItemComposite auRecord : rows) {
            dataVector.add(auRecord);
            Debug.logInfo(auRecord.getOrderItem().getitemDescription() + "[" + auRecord.getOrderItem().getproductId() + "]", "OrderEntryKeyTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }
   
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
    protected void notifyColumnDataChange(int row, int column){
            ActionEvent event = new RowColumnActionEvent(this, 1, "productId", row, column, null);
            for (ActionListener listener : listeners) {

                listener.actionPerformed(event); // broadcast to all
            }
            
            fireTableCellUpdated(row, ORDER_LINETOTAL_INDEX);
    }
 }
