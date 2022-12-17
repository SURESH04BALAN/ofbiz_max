/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ProductInventoryPojo;

public class InteractiveTableModel extends AbstractTableModel {

    public static final int PROD_ID_INDEX = 0;
    public static final int PROD_INTERNALNAME_INDEX = 1;
    public static final int PROD_SCANCODE_INDEX = 2;    
    public static final int PROD_DEFAULTPRICE_INDEX = 3;
    public static final int PROD_LISTPRICE_INDEX = 4;
    public static final int PROD_AVGCOST_INDEX = 5;    
    public static final int PROD_QUANTITY_INDEX = 6;        
    public static final int HIDDEN_INDEX = 7;
    protected String[] columnNames;
    protected Vector dataVector;

    public InteractiveTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new Vector();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        if (column == HIDDEN_INDEX) {
            return false;
        } else {
            return true;
        }
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case PROD_ID_INDEX:
            case PROD_INTERNALNAME_INDEX:
            case PROD_SCANCODE_INDEX:
                return String.class;
                
            case PROD_DEFAULTPRICE_INDEX:
            case PROD_LISTPRICE_INDEX:
            case PROD_AVGCOST_INDEX:
            case PROD_QUANTITY_INDEX:
                return String.class;
            default:
                return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
        if(row >=  dataVector.size() || row < 0 ) {
            return null;
        }
        
        ProductInventoryPojo record = (ProductInventoryPojo) dataVector.get(row);
        switch (column) {
            case PROD_ID_INDEX:
                return record.productId;
            case PROD_INTERNALNAME_INDEX:
                return record.internalName;
            case PROD_SCANCODE_INDEX:    
                return record.eanValue;
                
            case PROD_DEFAULTPRICE_INDEX:
                return record.defaultPrice;
            case PROD_LISTPRICE_INDEX:
                return record.price;
            case PROD_AVGCOST_INDEX:
                return record.purchasePrice;
            case PROD_QUANTITY_INDEX:
                return record.quantityOnHand;                
            default:
                return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
        ProductInventoryPojo record = (ProductInventoryPojo) dataVector.get(row);
        switch (column) {
            case PROD_ID_INDEX:
                record.productId = (String) value;
                break;
            case PROD_INTERNALNAME_INDEX:
                record.internalName = (String) value;
                record.isProductModified = true;                
                break;
            case PROD_SCANCODE_INDEX:    
                record.eanValue = (String) value;
                record.isEanValueModified = true;
                break;
            case PROD_DEFAULTPRICE_INDEX:
                try {
                    record.defaultPrice = new BigDecimal((String) value);
                    record.isDefaultPriceModified = true;
                } catch (Exception e) {
                    record.defaultPrice = BigDecimal.ZERO;
                }
                break;
            case PROD_LISTPRICE_INDEX:
                try {
                    record.price = new BigDecimal((String) value);
                    record.isPriceModiefied = true;                    
                } catch (Exception e) {
                    record.price = BigDecimal.ZERO;
                }
                break;                
            case PROD_AVGCOST_INDEX:
                try {
                    record.purchasePrice = new BigDecimal((String) value);
                    record.isPurchasePriceModified = true;                    
                } catch (Exception e) {
                    record.purchasePrice = BigDecimal.ZERO;
                }
                break;
            case PROD_QUANTITY_INDEX:
                try{
//                    record.quantity = new BigDecimal((String) value);
                    record.quantityOnHand = new BigDecimal((String) value);
                    record.isQuantityOnHandModified = true;
                } catch (Exception e) {
                    record.quantityOnHand = BigDecimal.ZERO;
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
        ProductInventoryPojo prodRecord = (ProductInventoryPojo) dataVector.get(dataVector.size() - 1);
        if (prodRecord.productId.trim().equals("")
                && prodRecord.internalName.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void addEmptyRow() {
        dataVector.add(new ProductInventoryPojo());
        fireTableRowsInserted(
                dataVector.size() - 1,
                dataVector.size() - 1);
    }

    public void addRows(List<ProductInventoryPojo> rows) {
//        dataVector.addAll(rows);
  //          fireTableRowsInserted(
 //                   dataVector.size() - 1,
  //                  dataVector.size() - 1);
        
        for (ProductInventoryPojo entry : rows) {
            dataVector.add(entry);
            Debug.logInfo(entry.internalName + "[" + entry.productId+"]", "InteractiveTableModel");
            fireTableRowsInserted(
                    dataVector.size() - 1,
                    dataVector.size() - 1);

        }

    }
    
    public int getRowNumber(String id)
    {
            
//        dataVector.addAll(rows);
  //          fireTableRowsInserted(
 //                   dataVector.size() - 1,
  //                  dataVector.size() - 1);
        int row = 0;
        for (Object entry : dataVector) {
            ProductInventoryPojo val = (ProductInventoryPojo)entry;
            if(val.productId.equals(id)){
                return row;
            }
            row++;
        }
        
        return 0;
    }

    
}