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
import org.ofbiz.ordermax.dataimportexport.loaders.BigFishProduct;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class BigFishProductTableModel extends ActionTableModel<BigFishProduct> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        Master_Product_ID(0, 20, String.class, "Master_Product_ID", true),
        Product_ID(1, 20, String.class, "Product_ID", true),
        Product_Category_ID(2, 20, String.class, "Product_Category_ID", true),
        Brand(3, 20, String.class, "Brand", true),
        Catrgory(4, 20, String.class, "Catrgory", true),
        VIRTUAL(5, 20, String.class, "virtual", true),
        Product_CODE(6, 20, String.class, "Product_CODE", true),
        BRAND_FULL(7, 20, String.class, "BRAND_FULL", true),
        Catrgory_Full(8, 20, String.class, "Catrgory_Full", true),
        Internal_Name(9, 20, String.class, "Internal_Name", true),
        Product_Name(10, 20, String.class, "Product_Name", true),
        Sales_Pitch(11, 20, String.class, "Sales_Pitch", true),
        Long_Description(12, 20, String.class, "Long_Description", true),
        Special_Instr(13, 20, String.class, "Special_Instr", true),
        Delivery_Info(14, 20, String.class, "Delivery_Info", true),
        Directions(15, 20, String.class, "Directions", true),
        Terms_Cond(16, 20, String.class, "Terms_Cond", true),
        Ingredients(0, 20, String.class, "Ingredients", true),
        Warnings(0, 20, String.class, "Warnings", true),
        PLP_Label(0, 20, String.class, "PLP_Label", true),
        PDP_Label(0, 20, String.class, "PDP_Label", true),
        List_Price(0, 20, String.class, "List_Price", true),
        Sales_Price(0, 20, String.class, "Sales_Price", true),
        Selectable_Features_1(0, 20, String.class, "Selectable_Features_1", true),
        PLP_Swatch_Image(0, 20, String.class, "PLP_Swatch_Image", true),
        PDP_Swatch_Image(0, 20, String.class, "PDP_Swatch_Image", true),
        Selectable_Features_2(0, 20, String.class, "Selectable_Features_2", true),
        Selectable_Features_3(0, 20, String.class, "Selectable_Features_3", true),
        Selectable_Features_4(0, 20, String.class, "Selectable_Features_4", true),
        Selectable_Features_5(0, 20, String.class, "Selectable_Features_5", true),
        Descriptive_Features_1(0, 20, String.class, "Descriptive_Features_1", true),
        Descriptive_Features_2(0, 20, String.class, "Descriptive_Features_2", true),
        Descriptive_Features_3(0, 20, String.class, "Descriptive_Features_3", true),
        Descriptive_Features_4(0, 20, String.class, "Descriptive_Features_4", true),
        Descriptive_Features_5(0, 20, String.class, "Descriptive_Features_5", true),
        PLP_Image(0, 20, String.class, "PLP_Image", true),
        PLP_Image_Alt(0, 20, String.class, "PLP_Image_Alt", true),
        PDP_Thumbnail_Image(0, 20, String.class, "PDP_Thumbnail_Image", true),
        PDP_Regular_Image(0, 20, String.class, "PDP_Regular_Image", true),
        PDP_Large_Image(0, 20, String.class, "PDP_Large_Image", true),
        PDP_Alt_1_Thumbnail_Image(0, 20, String.class, "PDP_Alt_1_Thumbnail_Image", true),
        PDP_Alt_1_Regular_Image(0, 20, String.class, "PDP_Alt_1_Regular_Image", true),
        PDP_Alt_1_Large_Image(0, 20, String.class, "PDP_Alt_1_Large_Image", true),
        PDP_Alt_2_Thumbnail_Image(0, 20, String.class, "PDP_Alt_2_Thumbnail_Image", true),
        PDP_Alt_2_Regular_Image(0, 20, String.class, "PDP_Alt_2_Regular_Image", true),
        PDP_Alt_2_Large_Image(0, 20, String.class, "PDP_Alt_2_Large_Image", true),
        PDP_Alt_3_Thumbnail_Image(0, 20, String.class, "PDP_Alt_3_Thumbnail_Image", true),
        PDP_Alt_3_Regular_Image(0, 20, String.class, "PDP_Alt_3_Regular_Image", true),
        PDP_Alt_3_Large_Image(0, 20, String.class, "PDP_Alt_3_Large_Image", true),
        PDP_Alt_4_Thumbnail_Image(0, 20, String.class, "PDP_Alt_4_Thumbnail_Image", true),
        PDP_Alt_4_Regular_Image(0, 20, String.class, "PDP_Alt_4_Regular_Image", true),
        PDP_Alt_4_Large_Image(0, 20, String.class, "PDP_Alt_4_Large_Image", true),
        PDP_Alt_5_Thumbnail_Image(0, 20, String.class, "PDP_Alt_5_Thumbnail_Image", true),
        PDP_Alt_5_Regular_Image(0, 20, String.class, "PDP_Alt_5_Regular_Image", true),
        PDP_Alt_5_Large_Image(0, 20, String.class, "PDP_Alt_5_Large_Image", true),
        PDP_Alt_6_Thumbnail_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_6_Regular_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_6_Large_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_7_Thumbnail_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_7_Regular_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_7_Large_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_8_Thumbnail_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_8_Regular_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_8_Large_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_9_Thumbnail_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_9_Regular_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_9_Large_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_10_Thumbnail_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_10_Regular_Image(0, 20, String.class, "PRODUCT ID", true),
        PDP_Alt_10_Large_Image(0, 20, String.class, "PRODUCT ID", true),
        Product_Height(0, 20, String.class, "PRODUCT ID", true),
        Product_Width(0, 20, String.class, "PRODUCT ID", true),
        Product_Depth(0, 20, String.class, "PRODUCT ID", true),
        Returnable(0, 20, String.class, "PRODUCT ID", true),
        Taxable(0, 20, String.class, "PRODUCT ID", true),
        Charge_Shipping(0, 20, String.class, "PRODUCT ID", true),
        Introduction_Date(0, 20, String.class, "PRODUCT ID", true),
        Discontinued_Date(0, 20, String.class, "PRODUCT ID", true),
        Manufacturer_ID(0, 20, String.class, "PRODUCT ID", true),
        SKU(0, 20, String.class, "PRODUCT ID", true),
        Google_ID(0, 20, String.class, "PRODUCT ID", true),
        ISBN(0, 20, String.class, "PRODUCT ID", true),
        Manufacturer_Number(0, 20, String.class, "PRODUCT ID", true),
        Product_Video(0, 20, String.class, "PRODUCT ID", true),
        Product_360_Video(0, 20, String.class, "PRODUCT ID", true),
        Sequence_Number(0, 20, String.class, "PRODUCT ID", true),
        BF_Inventory_Total(0, 20, String.class, "PRODUCT ID", true),
        BF_Inventory_Warehouse(0, 20, String.class, "PRODUCT ID", true),
        PDP_Select_Multi_Variant(0, 20, String.class, "PDP_Select_Multi_Variant", true),
        Product_Weight(0, 20, String.class, "Product_Weight", true),
        Check_Out_Gift_Message(0, 20, String.class, "Check_Out_Gift_Message", true),
        PDP_Min_Quantity(0, 20, String.class, "PDP_Min_Quantity", true),
        PDP_Max_Quantity(0, 20, String.class, "PDP_Max_Quantity", true),
        PDP_Default_Quantity(0, 20, String.class, "PDP_Default_Quantity", true),
        PDP_In_Store_Only(0, 20, String.class, "PDP_In_Store_Only", true);

        private int columnIndex;
        private int columnWidth;

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
        
        protected  String headerString;
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

    public BigFishProductTableModel() {
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        BigFishProduct productComposite = listModel.getElementAt(rowIndex);
        switch (column) {
            case Master_Product_ID:
                columnValue = productComposite.Master_Product_ID;
                break;
                
        case Product_ID:
                columnValue = productComposite.Product_ID;
                break;
        case Product_Category_ID:
                columnValue = productComposite.Product_Category_ID;
                break;
        case Brand:
                columnValue = productComposite.Brand;
                break;
        case Catrgory:
                columnValue = productComposite.Catrgory;
                break;
        case VIRTUAL:
                columnValue = productComposite.virtual;
                break;
        case Product_CODE:
                columnValue = productComposite.Product_CODE;
                break;
        case BRAND_FULL:
                columnValue = productComposite.BRAND_FULL;
                break;
        case Catrgory_Full:
                columnValue = productComposite.Catrgory_Full;
                break;
        case Internal_Name:
                columnValue = productComposite.Internal_Name;
                break;
        case Product_Name:
                columnValue = productComposite.Product_Name;
                break;
        case Sales_Pitch:
                columnValue = productComposite.Sales_Pitch;
                break;
        case Long_Description:
                columnValue = productComposite.Long_Description;
                break;
        case Special_Instr:
                columnValue = productComposite.Special_Instr;
                break;
        case Delivery_Info:
                columnValue = productComposite.Delivery_Info;
                break;
        case Directions:
                columnValue = productComposite.Directions;
                break;
        case Terms_Cond:
                columnValue = productComposite.Terms_Cond;
                break;
        case Ingredients:
                columnValue = productComposite.Ingredients;
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
