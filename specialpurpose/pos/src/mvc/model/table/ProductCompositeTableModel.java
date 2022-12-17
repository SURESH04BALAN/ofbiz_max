
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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceList;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.product.ProductTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductCompositeTableModel extends ActionTableModel<ProductComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        PROD_SELECT_INDEX(0, 30, Boolean.class, "Sel", true),
        PRODUCTID(1, 100, String.class, "PRODUCT ID", true),
        PRODUCTTYPEID(2, 150, String.class, "PRODUCT TYPE ID", false),
        INTERNALNAME(3, 300, String.class, "PRODUCT NAME", false),
        BRANDNAME(4, 100, String.class, "BRAND NAME", false),
        DEPARTMENTNAME(5, 100, String.class, "DEPARTMENT,", false),
        SCANCODE(6, 75, String.class, "SCAN CODE", false),
        GST(7, 75, String.class, "GST", false),
        ITEMSALETYPE(8, 75, String.class, "ITEM SALE TYPE(EA - EACH)", false),
        SUPPLIERNAME(9, 150, String.class, "SUPPLIER NAME", false),
        SUPPLIERPRICE(10, 75, String.class, "SUPPLIER PRICE", false),
        SELLINGPRICE(11, 75, String.class, "SELLING PRICE", false),
        AVERAGECOST(12, 75, String.class, "AVERAGE COST", false),
        STOCKATHAND(13, 75, String.class, "STOCK AT HAND", false),
        REQUIREINVFORSALE(14, 75, String.class, "REQUIRE INV FOR SALE(N-NO)", false);

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

    public ProductCompositeTableModel() {
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
        ProductComposite productComposite = (ProductComposite) listModel.getElementAt(rowIndex);

        switch (column) {
            case PROD_SELECT_INDEX:
                productComposite.setSelected((Boolean) value);
            default:
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        ProductComposite productComposite = (ProductComposite) listModel.getElementAt(rowIndex);
        switch (column) {
            case PROD_SELECT_INDEX:
                return productComposite.isSelected();
            case PRODUCTID:
                columnValue = productComposite.getProduct().getproductId();
                break;
            case PRODUCTTYPEID:
                try {
                    if (productComposite.getProduct().getproductTypeId() != null) {
                        columnValue = ProductTypeSingleton.getProductType(productComposite.getProduct().getproductTypeId()).getdescription();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(ProductCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case INTERNALNAME:
                columnValue = productComposite.getProduct().getproductName();
                break;
            case BRANDNAME:

                columnValue = productComposite.getProduct().getbrandName();

                break;
            case DEPARTMENTNAME:
                columnValue = productComposite.getProduct().getdepartmentName();
                break;

            case SCANCODE:
                if (UtilValidate.isNotEmpty(productComposite.getGoodIdentificationList())) {
                    for (GoodIdentification goodIdentification : productComposite.getGoodIdentificationList().getList()) {
                        columnValue = goodIdentification.getidValue();
                        break;
                    }
                }
                break;

            case ITEMSALETYPE:
                columnValue = productComposite.getProduct().getquantityUomId();
                break;
            case REQUIREINVFORSALE:
                columnValue = productComposite.getProduct().getrequireInventory();
                break;
            case SUPPLIERNAME:
                if (productComposite.getSupplierProductList() != null) {
                    for (SupplierProductComposite suppProduct : productComposite.getSupplierProductList().getList()) {
                        columnValue = suppProduct.getSupplierProduct().getcomments();
                        break;
                    }
                }
                break;
            case SUPPLIERPRICE:
                if (productComposite.getSupplierProductList() != null) {
                    for (SupplierProductComposite suppProduct : productComposite.getSupplierProductList().getList()) {
                        columnValue = suppProduct.getSupplierProduct().getlastPrice();
                        break;
                    }
                }
                break;
            case SELLINGPRICE:
                if (productComposite.getProductItemPrice() != null) {
                    ProductPriceList plist = productComposite.getProductItemPrice().getProductPriceList("LIST_PRICE");
                    for (ProductPriceComposite productPriceComposite : plist.getList()) {
                        columnValue = productPriceComposite.getProductPrice().getprice();
                        break;
                    }
                }
                break;
            case AVERAGECOST:
                if (productComposite.getProductItemPrice() != null) {
                    ProductPriceList avglist = productComposite.getProductItemPrice().getProductPriceList("AVERAGE_COST");
                    for (ProductPriceComposite productPriceComposite : avglist.getList()) {
                        columnValue = productPriceComposite.getProductPrice().getprice();
                        break;
                    }
                }
                break;
            case STOCKATHAND:
                break;
            case GST:
                columnValue = productComposite.getProduct().gettaxable();
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
