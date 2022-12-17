/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import com.googlecode.jcsv.writer.CSVEntryConverter;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author BBS Auctions
 */
public class BigFishProductEntryConverter implements CSVEntryConverter<BigFishProduct> {

    @Override
    public String[] convertEntry(BigFishProduct prod) {
        
         //Debug.logError("Product_ID : " + prod.Product_ID, null);
        String[] columns = new String[89];
        for(int i = 0; i < columns.length; ++i){
            columns[i]= "";
        }
        
        int i = 0;
       try{
        columns[i++] = prod.Master_Product_ID;
        columns[i++] = prod.Product_ID;
        columns[i++] = prod.Product_Category_ID;
//        columns[i++] = prod.Brand;
//        columns[i++] = prod.Catrgory;
//        columns[i++] = prod.virtual;
//        columns[i++] = prod.Product_CODE;
//        columns[i++] = prod.BRAND_FULL;
//        columns[i++] = prod.Catrgory_Full;
        columns[i++] = prod.Internal_Name;
        columns[i++] = prod.Product_Name;
        columns[i++] = prod.Sales_Pitch;
        columns[i++] = prod.Long_Description;
        columns[i++] = prod.Special_Instr;
        columns[i++] = prod.Delivery_Info;
        columns[i++] = prod.Directions;
        columns[i++] = prod.Terms_Cond;
        columns[i++] = prod.Ingredients;
        columns[i++] = prod.Warnings;
        columns[i++] = prod.PLP_Label;
        columns[i++] = prod.PDP_Label;
        columns[i++] = prod.List_Price;
        columns[i++] = prod.Sales_Price;
        columns[i++] = prod.Selectable_Features_1;
        columns[i++] = prod.PLP_Swatch_Image;
        columns[i++] = prod.PDP_Swatch_Image;
        columns[i++] = prod.Selectable_Features_2;
        columns[i++] = prod.Selectable_Features_3;
        columns[i++] = prod.Selectable_Features_4;
        columns[i++] = prod.Selectable_Features_5;
        columns[i++] = prod.Descriptive_Features_1;
        columns[i++] = prod.Descriptive_Features_2;
        columns[i++] = prod.Descriptive_Features_3;
        columns[i++] = prod.Descriptive_Features_4;
        columns[i++] = prod.Descriptive_Features_5;
        columns[i++] = prod.PLP_Image;
        columns[i++] = prod.PLP_Image_Alt;
        columns[i++] = prod.PDP_Thumbnail_Image;
        columns[i++] = prod.PDP_Regular_Image;
        columns[i++] = prod.PDP_Large_Image;
        columns[i++] = prod.PDP_Alt_1_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_1_Regular_Image;
        columns[i++] = prod.PDP_Alt_1_Large_Image;
        columns[i++] = prod.PDP_Alt_2_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_2_Regular_Image;
        columns[i++] = prod.PDP_Alt_2_Large_Image;
        columns[i++] = prod.PDP_Alt_3_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_3_Regular_Image;
        columns[i++] = prod.PDP_Alt_3_Large_Image;
        columns[i++] = prod.PDP_Alt_4_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_4_Regular_Image;
        columns[i++] = prod.PDP_Alt_4_Large_Image;
        columns[i++] = prod.PDP_Alt_5_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_5_Regular_Image;
        columns[i++] = prod.PDP_Alt_5_Large_Image;
        columns[i++] = prod.PDP_Alt_6_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_6_Regular_Image;
        columns[i++] = prod.PDP_Alt_6_Large_Image;
        columns[i++] = prod.PDP_Alt_7_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_7_Regular_Image;
        columns[i++] = prod.PDP_Alt_7_Large_Image;
        columns[i++] = prod.PDP_Alt_8_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_8_Regular_Image;
        columns[i++] = prod.PDP_Alt_8_Large_Image;
        columns[i++] = prod.PDP_Alt_9_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_9_Regular_Image;
        columns[i++] = prod.PDP_Alt_9_Large_Image;
        columns[i++] = prod.PDP_Alt_10_Thumbnail_Image;
        columns[i++] = prod.PDP_Alt_10_Regular_Image;
        columns[i++] = prod.PDP_Alt_10_Large_Image;
        columns[i++] = prod.Product_Height;
        columns[i++] = prod.Product_Width;
        columns[i++] = prod.Product_Depth;
        columns[i++] = prod.Returnable;
        columns[i++] = prod.Taxable;
        columns[i++] = prod.Charge_Shipping;
        columns[i++] = prod.Introduction_Date;
        columns[i++] = prod.Discontinued_Date;
        columns[i++] = prod.Manufacturer_ID;
        columns[i++] = prod.SKU;
        columns[i++] = prod.Google_ID;
        columns[i++] = prod.ISBN;
        columns[i++] = prod.Manufacturer_Number;
        columns[i++] = prod.Product_Video;
        columns[i++] = prod.Product_360_Video;
        columns[i++] = prod.Sequence_Number;
        columns[i++] = prod.BF_Inventory_Total;
        columns[i++] = prod.BF_Inventory_Warehouse;
        columns[i++] = prod.PDP_Select_Multi_Variant;
        columns[i++] = prod.Product_Weight;
        columns[i++] = prod.Check_Out_Gift_Message;
        columns[i++] = prod.PDP_Min_Quantity;
        columns[i++] = prod.PDP_Max_Quantity;
        columns[i++] = prod.PDP_Default_Quantity;
        columns[i++] = prod.PDP_In_Store_Only;
       }
       catch(Exception e){
           Debug.logError(e, "Error");
       }
        return columns;
    }

}
