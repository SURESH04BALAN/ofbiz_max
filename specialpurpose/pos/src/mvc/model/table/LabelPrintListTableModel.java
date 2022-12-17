
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
public class LabelPrintListTableModel extends ActionTableModel<ProductCompositeLabelPrint> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        PRODUCTID(0, 30, String.class, "Id", false),
        PRODUCTNAME(1, 150, String.class, "Name", false),
        WEIGHT(2, 30, Double.class, "Weight", false),
        SCANCODE(3, 50, String.class, "Scan Code", false),
        PACKINGDATE(4, 30, java.util.Date.class, "Packing Date", false),
        EXPIREDATE(5, 30, java.util.Date.class, "Expire Date", false),
        PRICE(6, 30, Double.class, "Price", true),      
        NUMBEROFLABELS(7, 30, Integer.class, "Number OF Labels to Print", true);        
        



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

    public LabelPrintListTableModel() {
    }

    @Override
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
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        ProductCompositeLabelPrint productComposite = (ProductCompositeLabelPrint) listModel.getElementAt(rowIndex);

        switch (column) {
            case NUMBEROFLABELS:
                productComposite.setNumberOfLabels((Integer) value);
                break;

            case PRICE:
                productComposite.setPrice((Double) value);
                break;
            case PRODUCTID:
                 productComposite.setProductId((String) value);

            case PRODUCTNAME:
                productComposite.setProductName((String) value);
            case WEIGHT:
                 productComposite.setWeight((Double) value);

            case SCANCODE:
                 productComposite.setScanCode((String) value);

            case PACKINGDATE:
                 productComposite.setPackingDate((java.util.Date)value);

            case EXPIREDATE:
                 productComposite.setExpireDate((java.util.Date)value);
            

                
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
            case PRODUCTID:
                return productComposite.getProductId();

            case PRODUCTNAME:
                return productComposite.getProductName();
            case WEIGHT:
                return productComposite.getWeight();

            case SCANCODE:
                return productComposite.getScanCode();

            case PACKINGDATE:
                return productComposite.getPackingDate();

            case EXPIREDATE:
                return productComposite.getExpireDate();
            
            case NUMBEROFLABELS:
                return productComposite.getNumberOfLabels();
            case PRICE:
                return productComposite.getPrice();
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
