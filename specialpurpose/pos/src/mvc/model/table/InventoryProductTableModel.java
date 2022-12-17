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
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.ordermax.product.ProductSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class InventoryProductTableModel extends ActionTableModel<Map<String, Object>> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        ProductId(0, 100, String.class, "Product Id", false),
        InternalName(1, 200, String.class, "Internal Name", false),
        TotalATP(2, 100, String.class, "Total ATP", false),
        TotalQOH(3, 100, String.class, "Total QOH", false),
        OrderedQuantity(4, 100, String.class, "Ordered Quantity", false),
        MinimumStock(5, 100, String.class, "Minimum Stock", false),
        ReorderQuantity(6, 100, String.class, "Reorder Quantity", false),
        DaysToShip(7, 100, String.class, "Days To Ship", false),
        QOHminusMinStock(8, 100, String.class, "QOH minus Min Stock", false),
        ATPminusMinStock(9, 100, String.class, "ATP minus Min Stock", false),
        Usage(10, 75, String.class, "Usage", false),
        DefaultPrice(11, 100, String.class, "Default Price", false),
        ListPrice(12, 100, String.class, "List Price", false),
        WholeSalePrice(13, 100, String.class, "Whole Sale Price", false),
        FromDateSellThrough(14, 100, String.class, "From Date Sell Through", false),
        SellThroughInitialInventory(15, 100, String.class, "Sell Through Initial Inventory", false),
        SellThroughInventorySold(16, 100, String.class, "Sell Through Inventory Sold", false),
        SellThroughPercentage(17, 100, String.class, "Sell Through Percentage", false);

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

    public InventoryProductTableModel() {
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
        Map<String, Object> mapResult = (Map<String, Object>) listModel.getElementAt(rowIndex);
//facilityId=MAX_FAC, wholeSalePrice=null, productId=AA1600, totalQuantityOnHand=1100.000000, offsetQOHQtyAvailable=1090.000000, reorderQuantity=10.000000, usageQuantity=0, checkTime=2015-09-13 15:20:12.481, quantityOnOrder=0, offsetATPQtyAvailable=1090.000000, minimumStock=10.000000, totalAvailableToPromise=1100.000000, defultPrice=66.000, daysToShip=1, listPrice=66.000}]
        switch (column) {
            case ProductId:
                columnValue = mapResult.get("productId");
                break;
            case InternalName: {
                try {
                    columnValue = ProductSingleton.getProduct((String) mapResult.get("productId")).getproductName();
                } catch (Exception ex) {
                    columnValue = null;
                    Logger.getLogger(InventoryProductTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case TotalATP:
                columnValue = new DecimalFormat("#0.##").format(new BigDecimal((String) mapResult.get("totalAvailableToPromise"))); //(BigDecimal)mapResult.get("totalAvailableToPromise"));
                break;
            case TotalQOH:
                columnValue = new DecimalFormat("#0.##").format(new BigDecimal((String) mapResult.get("totalQuantityOnHand")));
                break;
            case OrderedQuantity:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("quantityOnOrder"));
                break;
            case MinimumStock:
                columnValue = new DecimalFormat("#0.##").format(new BigDecimal((String) mapResult.get("minimumStock")));
                break;
            case ReorderQuantity:
                columnValue = new DecimalFormat("#0.##").format(new BigDecimal((String) mapResult.get("reorderQuantity")));
                break;
            case DaysToShip:
                columnValue = new DecimalFormat("#0.##").format(new BigDecimal((String) mapResult.get("daysToShip")));
                break;
            case QOHminusMinStock:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("offsetQOHQtyAvailable"));
                break;
            case ATPminusMinStock:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("offsetATPQtyAvailable"));
                break;
            case Usage:
                columnValue = mapResult.get("usageQuantity");
                break;
            case DefaultPrice:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("defultPrice"));
                break;
            case ListPrice:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("listPrice"));
                break;
            case WholeSalePrice:
                columnValue = new DecimalFormat("#0.##").format(mapResult.get("wholeSalePrice") != null ? mapResult.get("wholeSalePrice") : BigDecimal.ZERO);
                break;
            case FromDateSellThrough:
                columnValue = null;
                break;
            case SellThroughInitialInventory:
                columnValue = null;
                break;
            case SellThroughInventorySold:
                columnValue = null;
                break;
            case SellThroughPercentage:
                columnValue = null;
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

}
