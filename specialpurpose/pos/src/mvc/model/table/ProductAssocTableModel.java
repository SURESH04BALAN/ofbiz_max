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
import javax.swing.ImageIcon;

import javax.swing.JTable;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.ProductContentType;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.product.panel.ProductAssociationFromPanel;
import org.ofbiz.ordermax.product.panel.ProductAssociationTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductAssocTableModel extends ActionTableModel<ProductAssoc> {

    /**
     * 
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 50;

    static public enum Columns {

        PRODUCTID(0, 60, String.class, "Product ID", false),
        PRODUCTNAME(1, 150, String.class, "Name", false),
        FROMDATETIME(2, 60, java.sql.Timestamp.class, "From Date", false),
        THRUDATE(3, 60, java.sql.Timestamp.class, "Thru Date", false),
        SEQNUM(4, 60, java.math.BigDecimal.class, "Seq Num", false),
        
        ASSOCIATIONTYPE(5, 120, java.math.BigDecimal.class, "Association Type", false),
        QUANTITY(6, 60, java.math.BigDecimal.class, "Quantity", false);

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

    public ProductAssocTableModel() {
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
    int i = 0;
    javax.swing.ImageIcon icon = new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\person_small.png");
    public static final String IMAGE_SHEET_PATH = "http://speckycdn.sdm.netdna-cdn.com/"
            + "wp-content/uploads/2010/08/flag_icons_04.jpg";

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        ProductAssoc productAssoc = (ProductAssoc) listModel.getElementAt(rowIndex);

        switch (column) {
            case PRODUCTID:
                columnValue = productAssoc.getproductIdTo();
                break;
            case PRODUCTNAME: {
                try {
//                    Debug.logInfo(productAssoc.getproductContentTypeId() + " - " + productComposite.getContent().getcontentName(), "");
                    Product product = ProductSingleton.getProduct(productAssoc.getproductIdTo());
                    columnValue = product.getproductName();
                } catch (Exception ex) {
                    Logger.getLogger(ProductAssocTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case FROMDATETIME:
                columnValue = productAssoc.getfromDate();
                break;

            case THRUDATE:
                columnValue = productAssoc.getthruDate();
                break;

            case SEQNUM:
                columnValue = productAssoc.getsequenceNum();
                break;

            case QUANTITY:
                columnValue = productAssoc.getquantity();
                break;
            case ASSOCIATIONTYPE:

                try {
                    columnValue =ProductAssociationTypeSingleton.getProductAssocType(productAssoc.getproductAssocTypeId()).getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(ProductAssociationFromPanel.class.getName()).log(Level.SEVERE, null, ex);
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
}
