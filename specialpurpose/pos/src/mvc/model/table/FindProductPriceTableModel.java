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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.product.ProductPricePurposeSingleton;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class FindProductPriceTableModel extends ActionTableModel<ProductPriceComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 70;

    static public enum Columns {

        PRODUCTID(0, 80, String.class, "Product Id", true),
        PRODUCTNAME(1, 150, String.class, "Description", false),
        PRICETYPE(0, 100, String.class, "Price Type", true),
        PURPOSE(1, 100, String.class, "Purpose", false),
        CURRENCY(2, 65, String.class, "Currency", false),
        PRODUCTSTOREGROUP(3, 120, String.class, "Product Store Group", false),
        FROMDATETIME(4, defaultWidth, java.sql.Timestamp.class, "From Date Time", false),
        THRUDATE(5, defaultWidth, java.sql.Timestamp.class, "Thru Date", false),
        PRICE(6, defaultWidth, java.math.BigDecimal.class, "Price", false),
        TERMUOMID(7, 100, String.class, "Term Uom Id", false),
        PRICECALCSERVICE(8, defaultWidth, String.class, "Custom Price Calc Service", false),
        TAXPERCENTAGE(9, defaultWidth, java.math.BigDecimal.class, "Tax Percentage", false),
        TAXAUTHORITYPARTY(10, defaultWidth, String.class, "Tax Authority Party", false),
        TAXAUTHGEOID(11, defaultWidth, String.class, "Tax Auth Geo ID", false),
        TAXINPRICE(12, defaultWidth, String.class, "Tax In Price", false),
        //        UPDATE(13, 150, java.math.BigDecimal.class, "Update", false),
        LASTMODIFIEDBY(13, defaultWidth, String.class, "Last Modified By", false);

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

    public FindProductPriceTableModel() {
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
        ProductPriceComposite productPriceComposite = (ProductPriceComposite) listModel.getElementAt(rowIndex);
        ProductPrice productPrice = productPriceComposite.getProductPrice();

        switch (column) {
            case PRODUCTID:
                columnValue = productPrice.getproductId();
                break;
            case PRODUCTNAME: {
                try {
                    Product product = ProductSingleton.getProduct(productPrice.getproductId());
                    columnValue = product.getproductName();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeDetailTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case PRICETYPE:
                try {
                    columnValue = ProductPriceTypeSingleton.getProductPriceType(productPrice.getproductPriceTypeId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(FindProductPriceTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case PURPOSE:
                try {
                    columnValue = ProductPricePurposeSingleton.getProductPricePurposeType(productPrice.getproductPricePurposeId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(FindProductPriceTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case CURRENCY:
                columnValue = productPrice.getcurrencyUomId();
                break;
            case PRODUCTSTOREGROUP:
                try {
                    columnValue = ProductStoreGroupSingleton.getProductStoreGroup(productPrice.getproductStoreGroupId()).getproductStoreGroupName();
                } catch (Exception ex) {
                    Logger.getLogger(FindProductPriceTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case FROMDATETIME:

                columnValue = productPrice.getfromDate();

                break;

            case THRUDATE:
                columnValue = productPrice.getthruDate();
                break;

            case PRICE:
                columnValue = productPrice.getprice();
                break;
            case TERMUOMID:
                try {
                    columnValue = UomQuantitySingleton.getUom(productPrice.gettermUomId()).getdescription();
                } catch (Exception ex) {
                    columnValue = "";
//                    Logger.getLogger(ProductPriceTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case PRICECALCSERVICE:
                columnValue = productPrice.getcustomPriceCalcService();
                break;

            case TAXPERCENTAGE:
                columnValue = productPrice.gettaxPercentage();
                if (columnValue == null) {
                    columnValue = BigDecimal.ZERO;
                }
                break;
            case TAXAUTHORITYPARTY:
                columnValue = productPrice.gettaxAuthPartyId();
                break;
            case TAXAUTHGEOID:
                columnValue = productPrice.gettaxAuthGeoId();
                break;
            case TAXINPRICE:
                columnValue = productPrice.gettaxInPrice();
                break;
            case LASTMODIFIEDBY:
                columnValue = productPrice.getlastModifiedByUserLogin();
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
