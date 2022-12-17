/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import com.googlecode.jcsv.reader.CSVEntryParser;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class BigFishProductEntryParser implements CSVEntryParser<BigFishProduct> {

    @Override
    public BigFishProduct parseEntry(String... data) {
        BigFishProduct prod = new BigFishProduct();
        int i = 0;
        int length = data.length;
        prod.Master_Product_ID = i++ < length ? data[i - 1] : "";
        prod.Product_ID = i++ < length ? data[i - 1] : "";
        prod.Product_Category_ID = i++ < length ? data[i - 1] : "";
        //prod.Brand = i++ < length ? data[i - 1] : "";
        //prod.Catrgory = i++ < length ? data[i - 1] : "";
        //prod.virtual = i++ < length ? data[i - 1] : "";
        //prod.Product_CODE = i++ < length ? data[i - 1] : "";
        //prod.BRAND_FULL = i++ < length ? data[i - 1] : "";
        //prod.Catrgory_Full = i++ < length ? data[i - 1] : "";
        prod.Internal_Name = i++ < length ? data[i - 1] : "";
//    Debug.logInfo("prod.Internal_Name : " + prod.Internal_Name, "module");       
        prod.Product_Name = i++ < length ? data[i - 1] : "";

//    Debug.logInfo("prod.Product_Name : " + prod.Product_Name, "module");    
        prod.Sales_Pitch = i++ < length ? data[i - 1] : "";

//    Debug.logInfo("prod.Sales_Pitch: " + prod.Sales_Pitch, "module");
        prod.Long_Description = i++ < length ? data[i - 1] : "";

        prod.Special_Instr = i++ < length ? data[i - 1] : "";
        prod.Delivery_Info = i++ < length ? data[i - 1] : "";
        prod.Directions = i++ < length ? data[i - 1] : "";
        prod.Terms_Cond = i++ < length ? data[i - 1] : "";
        prod.Ingredients = i++ < length ? data[i - 1] : "";
        prod.Warnings = i++ < length ? data[i - 1] : "";
        prod.PLP_Label = i++ < length ? data[i - 1] : "";
        prod.PDP_Label = i++ < length ? data[i - 1] : "";
        prod.List_Price = i++ < length ? data[i - 1] : "";
        prod.Sales_Price = i++ < length ? data[i - 1] : "";
        prod.Selectable_Features_1 = i++ < length ? data[i - 1] : "";
        prod.PLP_Swatch_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Swatch_Image = i++ < length ? data[i - 1] : "";
        prod.Selectable_Features_2 = i++ < length ? data[i - 1] : "";
        prod.Selectable_Features_3 = i++ < length ? data[i - 1] : "";
        prod.Selectable_Features_4 = i++ < length ? data[i - 1] : "";
       prod.PLP_Image = i++ < length ? data[i - 1] : "";
        //Debug.logInfo(" prod.PLP_Image: " + prod.PLP_Image, "module");
        //prod.Selectable_Features_5 = i++ < length ? data[i - 1] : "";

        prod.Descriptive_Features_1 = i++ < length ? data[i - 1] : "";
        prod.Descriptive_Features_2 = i++ < length ? data[i - 1] : "";
        prod.Descriptive_Features_3 = i++ < length ? data[i - 1] : "";
        prod.Descriptive_Features_4 = i++ < length ? data[i - 1] : "";
        prod.Descriptive_Features_5 = i++ < length ? data[i - 1] : "";
        prod.PLP_Image = i++ < length ? data[i - 1] : "";
        Debug.logInfo(" prod.PLP_Image: " + prod.PLP_Image, "module");
        prod.PLP_Image_Alt = i++ < length ? data[i - 1] : "";
        Debug.logInfo(" prod.PLP_Image_Alt: " + prod.PLP_Image_Alt, "module");
        prod.PDP_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        Debug.logInfo(" prod.PDP_Thumbnail_Image: " + prod.PDP_Thumbnail_Image, "module");
        prod.PDP_Regular_Image = i++ < length ? data[i - 1] : "";
        Debug.logInfo(" prod.PDP_Regular_Image: " + prod.PDP_Regular_Image, "module");
        prod.PDP_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_1_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_1_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_1_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_2_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_2_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_2_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_3_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_3_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_3_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_4_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_4_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_4_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_5_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_5_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_5_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_6_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_6_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_6_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_7_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_7_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_7_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_8_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_8_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_8_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_9_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_9_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_9_Large_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_10_Thumbnail_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_10_Regular_Image = i++ < length ? data[i - 1] : "";
        prod.PDP_Alt_10_Large_Image = i++ < length ? data[i - 1] : "";
        prod.Product_Height = i++ < length ? data[i - 1] : "";
        prod.Product_Width = i++ < length ? data[i - 1] : "";
        prod.Product_Depth = i++ < length ? data[i - 1] : "";
        prod.Returnable = i++ < length ? data[i - 1] : "";
        prod.Taxable = i++ < length ? data[i - 1] : "";
        prod.Charge_Shipping = i++ < length ? data[i - 1] : "";
        prod.Introduction_Date = i++ < length ? data[i - 1] : "";
        prod.Discontinued_Date = i++ < length ? data[i - 1] : "";
        prod.Manufacturer_ID = i++ < length ? data[i - 1] : "";
        prod.SKU = i++ < length ? data[i - 1] : "";
        prod.Google_ID = i++ < length ? data[i - 1] : "";
        prod.ISBN = i++ < length ? data[i - 1] : "";
        prod.Manufacturer_Number = i++ < length ? data[i - 1] : "";
        prod.Product_Video = i++ < length ? data[i - 1] : "";
        prod.Product_360_Video = i++ < length ? data[i - 1] : "";
        prod.Sequence_Number = i++ < length ? data[i - 1] : "";
        prod.BF_Inventory_Total = i++ < length ? data[i - 1] : "";
        prod.BF_Inventory_Warehouse = i++ < length ? data[i - 1] : "";
        prod.PDP_Select_Multi_Variant = i++ < length ? data[i - 1] : "";
        prod.Product_Weight = i++ < length ? data[i - 1] : "";
        prod.Check_Out_Gift_Message = i++ < length ? data[i - 1] : "";
        prod.PDP_Min_Quantity = i++ < length ? data[i - 1] : "";
        prod.PDP_Max_Quantity = i++ < length ? data[i - 1] : "";
        prod.PDP_Default_Quantity = i++ < length ? data[i - 1] : "";
        prod.PDP_In_Store_Only = i++ < length ? data[i - 1] : "";

Debug.logInfo(" prod.PDP_Regular_Image: " + prod.outputDebug(), "module");
        /*        org.ofbiz.ordermax.entity.Product prod = new org.ofbiz.ordermax.entity.Product();
         int i = 0;
         int length = data.length;
         Debug.logError("Field Length: " + length, null);
         prod.setproductId(i++ < length ? data[i - 1] : "");
         Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

         prod.setinternalName(i++ < length ? data[i - 1] : "");
         Debug.logError("Field" + i + "[internalName]: " + (i < length ? data[i - 1] : ""), null);

         prod.setdepartmentName(i++ < length ? data[i - 1] : "");
         Debug.logError("Field" + i + "[departmentName]: " + (i < length ? data[i - 1] : ""), null);

         //        String brandName = prod.getinternalName().replaceAll(" .*", "");
         if (i++ < length) {
         String brandName = data[i - 1];
         if (UtilValidate.isEmpty(brandName)) {
         brandName = prod.getinternalName().replaceAll(" .*", "");
         }
         prod.setbrandName(brandName);
         prod.setprimaryProductCategoryId(prod.getbrandName());
         Debug.logError("Field" + i + "[brandName]: " + brandName, null);
         }

         prod.setproductName(i++ < length ? data[i - 1] : prod.getinternalName());
         prod.setdescription(i++ < length ? data[i - 1] : prod.getinternalName());
         prod.setlongDescription(i++ < length ? data[i - 1] : prod.getinternalName());
         prod.setcomments(i++ < length ? data[i - 1] : "");
         prod.setrequireInventory(i++ < length ? data[i - 1] : "Y");
         prod.setquantityUomId(i++ < length ? data[i - 1] : "");
         prod.setrequireAmount(i++ < length ? data[i - 1] : "");
         prod.settaxable(i++ < length ? data[i - 1] : "N");
         prod.setisVirtual(i++ < length ? data[i - 1] : "N");
         prod.setisVariant(i++ < length ? data[i - 1] : "N");
         prod.setproductTypeId(i++ < length ? data[i - 1] : "FINISHED_GOOD");
         //        Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

         prod.setmanufacturerPartyId(i++ < length ? data[i - 1] : "");
         prod.setfacilityId(i++ < length ? data[i - 1] : "");
         try {
         prod.setintroductionDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setreleaseDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setsupportDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setsalesDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setsalesDiscWhenNotAvail(i++ < length ? data[i - 1] : "");
         prod.setpriceDetailText(i++ < length ? data[i - 1] : "");
         prod.setsmallImageUrl(i++ < length ? data[i - 1] : "");
         prod.setmediumImageUrl(i++ < length ? data[i - 1] : "");
         prod.setlargeImageUrl(i++ < length ? data[i - 1] : "");
         prod.setdetailImageUrl(i++ < length ? data[i - 1] : "");
         prod.setoriginalImageUrl(i++ < length ? data[i - 1] : "");
         prod.setdetailScreen(i++ < length ? data[i - 1] : "");
         prod.setinventoryMessage(i++ < length ? data[i - 1] : "");
         prod.setquantityIncluded(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setpiecesIncluded(i++ < length ? OrderMaxUtility.getValidEntityLong(data[i - 1]) : null);
         prod.setfixedAmount(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setamountUomTypeId(i++ < length ? data[i - 1] : "");
         prod.setweightUomId(i++ < length ? data[i - 1] : "");
         prod.setweight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setproductWeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ZERO);

         prod.setheightUomId(i++ < length ? data[i - 1] : "");
         prod.setproductHeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ZERO);
         prod.setshippingHeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setwidthUomId(i++ < length ? data[i - 1] : "");
         prod.setproductWidth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setshippingWidth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setdepthUomId(i++ < length ? data[i - 1] : "");
         prod.setproductDepth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setshippingDepth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setdiameterUomId(i++ < length ? data[i - 1] : "");
         prod.setproductDiameter(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setproductRating(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setratingTypeEnum(i++ < length ? data[i - 1] : "");
         prod.setreturnable(i++ < length ? data[i - 1] : "Y");
         prod.setchargeShipping(i++ < length ? data[i - 1] : "N");
         prod.setautoCreateKeywords(i++ < length ? data[i - 1] : "N");
         prod.setincludeInPromotions(i++ < length ? data[i - 1] : "Y");
         prod.setvirtualVariantMethodEnum(i++ < length ? data[i - 1] : "");
         prod.setoriginGeoId(i++ < length ? data[i - 1] : "");
         prod.setrequirementMethodEnumId(i++ < length ? data[i - 1] : "PRODRQM_AUTO");
         prod.setbillOfMaterialLevel(i++ < length ? OrderMaxUtility.getValidEntityLong(data[i - 1]) : new Long(0));
         prod.setreservMaxPersons(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setreserv2ndPPPerc(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setreservNthPPPerc(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
         prod.setconfigId(i++ < length ? data[i - 1] : "");
         try {
         prod.setcreatedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setcreatedByUserLogin(i++ < length ? data[i - 1] : "");
         try {
         prod.setlastModifiedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setlastModifiedByUserLogin(i++ < length ? data[i - 1] : "");
         prod.setinShippingBox(i++ < length ? data[i - 1] : "");
         prod.setdefaultShipmentBoxTypeId(i++ < length ? data[i - 1] : "");
         prod.setorderDecimalQuantity(i++ < length ? data[i - 1] : "");
         try {
         prod.setlastUpdatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setlastUpdatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setcreatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setcreatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(BigFishProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setlotIdFilledIn(i++ < length ? data[i - 1] : "");
         */
        return prod;
    }
}
