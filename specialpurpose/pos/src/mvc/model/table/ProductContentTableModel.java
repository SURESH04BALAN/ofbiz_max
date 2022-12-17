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
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.ProductContent;
import org.ofbiz.ordermax.entity.ProductContentType;
import org.ofbiz.ordermax.product.productContent.ProductContentTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ProductContentTableModel extends ActionTableModel<ProductContentComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 150;

    static public enum Columns {

        CONTENTID(0, defaultWidth, String.class, "Content", false),
        CONTENTTYPEID(1, 300, String.class, "Type", false),
        PICTURE(2, defaultWidth, ImageIcon.class, "Picture", false),
        Data(3, 300, String.class, "Value", true),
        FROMDATETIME(4, defaultWidth, java.sql.Timestamp.class, "From Date", false),
        THRUDATE(5, defaultWidth, java.sql.Timestamp.class, "Thru Date", false),
        PurchaseFromDate(6, defaultWidth, java.sql.Timestamp.class, "Purchase From Date", false),
        PurchaseThruDate(7, defaultWidth, java.sql.Timestamp.class, "Purchase Thru Date", false),
        UseCountLimit(8, defaultWidth, java.math.BigDecimal.class, "Use Count Limit", false),
        UseTime(9, defaultWidth, java.sql.Timestamp.class, "Use Time", false),
        UseTimeUomId(10, defaultWidth, java.sql.Timestamp.class, "Use Time Uom Id", false),
        UseRoleTypeId(11, defaultWidth, java.math.BigDecimal.class, "Use Role Type Id", false),
        SequenceNum(12, defaultWidth, java.math.BigDecimal.class, "Sequence Num", false);

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

    public ProductContentTableModel() {
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
        ProductContentComposite productComposite = (ProductContentComposite) listModel.getElementAt(rowIndex);
        ProductContent productContent = productComposite.getProductContent();

        switch (column) {
            case CONTENTID:
                columnValue = productContent.getcontentId();
                break;
            case CONTENTTYPEID: {
                try {
                    Debug.logInfo(productContent.getproductContentTypeId() + " - " + productComposite.getContent().getcontentName(), "");
                    ProductContentType product = ProductContentTypeSingleton.getProductContentType(productContent.getproductContentTypeId());
                    columnValue = product.getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(SupplierProductCompositeDetailTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case FROMDATETIME:
                columnValue = productContent.getfromDate();
                break;

            case THRUDATE:
                columnValue = productContent.getthruDate();
                break;
            case PurchaseFromDate:
                columnValue = productContent.getpurchaseFromDate();
                break;

            case PurchaseThruDate:
                columnValue = productContent.getpurchaseThruDate();
                break;
            case UseCountLimit:
                columnValue = productContent.getuseCountLimit();
                break;

            case PICTURE:
                //columnValue = productComposite.getIcon();
                String fileName = ProductDataTreeLoader.BaseImagePath;
                if (UtilValidate.isNotEmpty(productComposite.getDataResource()) && UtilValidate.isNotEmpty(productComposite.getDataResource().getobjectInfo())) {
                    String filePath = fileName.concat(productComposite.getDataResource().getobjectInfo());
                    ImageIcon icon1 = BaseHelper.getImage(filePath);
                    columnValue = icon1;
                }
                break;

            case Data:
                //columnValue = productComposite.getIcon();
                columnValue = productComposite.getDataResource().getobjectInfo();
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
