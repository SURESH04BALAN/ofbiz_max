
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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.ordermax.composite.ProductCompositeLabelPrint;


/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductCompositePrintLabelTableModel extends ActionTableModel<ProductCompositeLabelPrint> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        SELECTED(0, 30, Boolean.class, "Print", true),
        PRODUCTID(1, 30, String.class, "Id", false),
        PRODUCTNAME(2, 150, String.class, "Name", true),
        WEIGHT(3, 30, Double.class, "Weight", true),
        WEIGHTSTR(4, 30, String.class, "Weight", true),
        SCANCODE(5, 50, String.class, "Scan Code", false),
        SHELFLIFE(6, 30, Integer.class, "Shelf Life", true),
        PACKINGDATE(7, 30, java.util.Date.class, "Packing Date", false),
        EXPIREDATE(8, 30, java.util.Date.class, "Expire Date", false),
        INGREDIENTLIST(9, 30, String.class, "Ingredient List", true),
        COUNTRYOFORIGIN(10, 30, String.class, "Origin", true),
        PRODUCTCATEGORY(11, 30, String.class, "Product Cat.", true),
        PRICE(12, 30, Double.class, "Price", true);        



        private int columnIndex;
        private int columnWidth;
        private String headerString;
        private Class className;
        private boolean editable;

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

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean isEditable) {
            this.editable = isEditable;
        }

        Columns(int index, int width, Class className, String header, boolean edit) {
            columnIndex = index;
            columnWidth = width;
            headerString = header;
            this.className = className;
            editable = edit;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public int getColumnWidth() {
            return columnWidth;
        }
    }

    public ProductCompositePrintLabelTableModel() {
    }

    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.editable;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        ProductCompositeLabelPrint productComposite = (ProductCompositeLabelPrint) listModel.getElementAt(rowIndex);

        switch (column) {
            case SELECTED:
                productComposite.setSelected((Boolean) value);
                break;

            case PRODUCTID:
                productComposite.setProductId((String) value);
                break;

            case PRODUCTNAME:
                productComposite.setProductName((String) value);
                break;

            case WEIGHT:
                productComposite.setWeight((Double) value);
                break;

            case WEIGHTSTR:
                productComposite.setWeightStr((String) value);
                break;

            case SCANCODE:
                productComposite.setScanCode((String) value);
                break;

            case SHELFLIFE:
                productComposite.setSelfLife((Integer) value);
                break;

            case PACKINGDATE:
                productComposite.setPackingDate((java.util.Date) value);
                break;

            case EXPIREDATE:
                productComposite.setExpireDate((java.util.Date) value);
                break;

            case INGREDIENTLIST:
                productComposite.setIngredientList((String) value);
                break;

            case COUNTRYOFORIGIN:
                productComposite.setCountryOfOrigin((String) value);
                break;
/*
            case SELECTEDROOTYHILL:
                productComposite.setSelectedRootyHill((Boolean) value);
                break;

            case PRICEROOTYHILL:
                productComposite.setPriceRootyHill((Double) value);
                break;

            case NUMBEROFLABELSROOTYHILL:
                productComposite.setNumberOfLabelsRootyHill((Integer) value);
                break;

            case SELECTEDLIVERPOOL:
                productComposite.setSelectedLiverpool((Boolean) value);
                break;

            case PRICELIVERPOOL:
                productComposite.setPriceLiverpool((Double) value);
                break;

            case NUMBEROFLABELSLIVERPOOL:
                productComposite.setNumberOfLabelsLiverpool((Integer) value);
                break;

            case SELECTEDTOONGABBIE:
                productComposite.setSelectedToongabbie((Boolean) value);
                break;


            case NUMBEROFLABELSTOONGABBIE:
                productComposite.setNumberOfLabels((Integer) value);
                break;*/
            case PRICE:
                productComposite.setPrice((Double) value);
                break;

            case PRODUCTCATEGORY:
                productComposite.setProductCategory((String) value);
                break;                

            default:
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        ProductCompositeLabelPrint productComposite = (ProductCompositeLabelPrint) listModel.getElementAt(rowIndex);
        switch (column) {
            case SELECTED:
                return productComposite.getSelected();

            case PRODUCTID:
                return productComposite.getProductId();

            case PRODUCTNAME:
                return productComposite.getProductName();
            case WEIGHT:
                return productComposite.getWeight();

            case WEIGHTSTR:
                return productComposite.getWeightStr();

            case SCANCODE:
                return productComposite.getScanCode();

            case SHELFLIFE:
                return productComposite.getSelfLife();

            case PACKINGDATE:
                return productComposite.getPackingDate();

            case EXPIREDATE:
                return productComposite.getExpireDate();

            case INGREDIENTLIST:
                return productComposite.getIngredientList();

            case COUNTRYOFORIGIN:
                return productComposite.getCountryOfOrigin();
/*
            case SELECTEDROOTYHILL:
                return productComposite.getSelectedRootyHill();

            case PRICEROOTYHILL:
                return productComposite.getPriceRootyHill();

            case NUMBEROFLABELSROOTYHILL:
                return productComposite.getNumberOfLabelsRootyHill();

            case SELECTEDLIVERPOOL:
                return productComposite.getSelectedLiverpool();

            case PRICELIVERPOOL:
                return productComposite.getPriceLiverpool();

            case NUMBEROFLABELSLIVERPOOL:
                return productComposite.getNumberOfLabelsLiverpool();

            case SELECTEDTOONGABBIE:
                return productComposite.getSelectedToongabbie();


            case NUMBEROFLABELSTOONGABBIE:
                return productComposite.getNumberOfLabels();
*/                
            case PRICE:
                return productComposite.getPrice();

            case PRODUCTCATEGORY                :
                return productComposite.getProductCategory();

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
