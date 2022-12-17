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
public class ProductEntryParser implements CSVEntryParser<org.ofbiz.ordermax.entity.Product> {

    @Override
    public org.ofbiz.ordermax.entity.Product parseEntry(String... data) {

        org.ofbiz.ordermax.entity.Product prod = new org.ofbiz.ordermax.entity.Product();
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
            String brandName =  data[i - 1];
            if(UtilValidate.isEmpty(brandName)){
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
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prod.setreleaseDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prod.setsupportDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prod.setsalesDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        prod.setcreatedByUserLogin(i++ < length ? data[i - 1] : "");
        try {
            prod.setlastModifiedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        prod.setlastModifiedByUserLogin(i++ < length ? data[i - 1] : "");
        prod.setinShippingBox(i++ < length ? data[i - 1] : "");
        prod.setdefaultShipmentBoxTypeId(i++ < length ? data[i - 1] : "");
        prod.setorderDecimalQuantity(i++ < length ? data[i - 1] : "");
        try {
            prod.setlastUpdatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            prod.setlastUpdatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            prod.setcreatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            prod.setcreatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
        } catch (ParseException ex) {
            Logger.getLogger(ProductEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        prod.setlotIdFilledIn(i++ < length ? data[i - 1] : "");

        return prod;
    }
}
