/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import com.googlecode.jcsv.reader.CSVEntryParser;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class ProductCompositeEntryParser implements CSVEntryParser<ProductComposite> {

    @Override
    public ProductComposite parseEntry(String... data) {
//        if (data.length != 3) {
//            throw new IllegalArgumentException("data is not a valid person record");
//        }

//        String firstname = data[0];
//        String lastname = data[1];
//        int age = Integer.parseInt(data[2]);
  /*        "PRODUCT_ID"
         ,  "INTERNAL_NAME",  "PRODUCT_NAME", "BRAND_NAME",
         "DESCRIPTION", "LONG_DESCRIPTION", "COMMENTS",
         "REQUIRE_INVENTORY", "QUANTITY_UOM_ID","REQUIRE_AMOUNT","TAXABLE",
         "IS_VIRTUAL", "IS_VARIANT", "PRODUCT_TYPE_ID","PRIMARY_PRODUCT_CATEGORY_ID", "MANUFACTURER_PARTY_ID", "FACILITY_ID",
         "INTRODUCTION_DATE", "RELEASE_DATE", "SUPPORT_DISCONTINUATION_DATE", 
         "SALES_DISCONTINUATION_DATE", "SALES_DISC_WHEN_NOT_AVAIL",
         "PRICE_DETAIL_TEXT", "SMALL_IMAGE_URL", "MEDIUM_IMAGE_URL", "LARGE_IMAGE_URL", 
         "DETAIL_IMAGE_URL", "ORIGINAL_IMAGE_URL", "DETAIL_SCREEN", "INVENTORY_MESSAGE",
         "QUANTITY_INCLUDED", "PIECES_INCLUDED", "FIXED_AMOUNT", "AMOUNT_UOM_TYPE_ID",
         "WEIGHT_UOM_ID", "WEIGHT", "PRODUCT_WEIGHT", "HEIGHT_UOM_ID", "PRODUCT_HEIGHT",
         "SHIPPING_HEIGHT", "WIDTH_UOM_ID", "PRODUCT_WIDTH", "SHIPPING_WIDTH", "DEPTH_UOM_ID", 
         "PRODUCT_DEPTH", "SHIPPING_DEPTH", "DIAMETER_UOM_ID", "PRODUCT_DIAMETER", 
         "PRODUCT_RATING", "RATING_TYPE_ENUM", "RETURNABLE", "CHARGE_SHIPPING", 
         "AUTO_CREATE_KEYWORDS", "INCLUDE_IN_PROMOTIONS", "VIRTUAL_VARIANT_METHOD_ENUM", 
         "ORIGIN_GEO_ID", "REQUIREMENT_METHOD_ENUM_ID", "BILL_OF_MATERIAL_LEVEL", 
         "RESERV_MAX_PERSONS", "RESERV2ND_P_P_PERC", "RESERV_NTH_P_P_PERC", "CONFIG_ID",
         "CREATED_DATE", "CREATED_BY_USER_LOGIN", "LAST_MODIFIED_DATE", 
         "LAST_MODIFIED_BY_USER_LOGIN", "IN_SHIPPING_BOX", "DEFAULT_SHIPMENT_BOX_TYPE_ID", 
         "ORDER_DECIMAL_QUANTITY", "LAST_UPDATED_STAMP", "LAST_UPDATED_TX_STAMP",
         "CREATED_STAMP", "CREATED_TX_STAMP", "LOT_ID_FILLED_IN"
         */
        ProductComposite productComposite = LoadProductWorker.newProduct();
        org.ofbiz.ordermax.entity.Product prod = productComposite.getProduct();
//        productComposite.setProduct(prod);

        int i = 0;
        int length = data.length;
        Debug.logError("Field Length: " + length, null);
        prod.setproductId(i++ < length ? data[i - 1] : "");
        Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

        prod.setinternalName(i++ < length ? data[i - 1] : "");
        Debug.logError("Field" + i + "[internalName]: " + (i < length ? data[i - 1] : ""), null);
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

        prod.setdepartmentName(i++ < length ? data[i - 1] : "");
        Debug.logError("Field" + i + "[departmentName]: " + (i < length ? data[i - 1] : ""), null);

        GoodIdentification goodIdentification = new GoodIdentification();
        goodIdentification.setproductId(productComposite.getProduct().getproductId());
        goodIdentification.setgoodIdentificationTypeId("EAN");
        goodIdentification.setidValue(i++ < length ? data[i - 1] : "");
        productComposite.getGoodIdentificationList().add(goodIdentification);

        prod.setproductName(prod.getinternalName());
        prod.setdescription(prod.getinternalName());
        prod.setlongDescription(prod.getinternalName());
        prod.setquantityUomId(i++ < length ? data[i - 1] : "");
        Debug.logError("Field" + i + "[QuantityUomId]: " + data[i - 1], null);

        Debug.logError("Field" + ++i + "[Req Inv]: " + data[i - 1], null);

        //supplier product
        SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(productComposite.getProduct().getproductId());
        spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
        spComp.getSupplierProduct().setsupplierProductId(productComposite.getProduct().getproductId());
        spComp.getSupplierProduct().setcomments(i++ < length ? data[i - 1] : "_NA_");
//        spComp.getSupplierProduct().setpartyId("BigSupplier");
        Debug.logError("Field" + i + "[SupplierPartyId]: " + spComp.getSupplierProduct().getcomments(), null);

        spComp.getSupplierProduct().setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
        BigDecimal lastPrice = BigDecimal.ONE;

        if (i++ < length) {
            String val = data[i - 1];
            if (UtilValidate.isNotEmpty(val)) {
                lastPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
            }
            spComp.getSupplierProduct().setlastPrice(lastPrice);
            Debug.logError("Field" + i + 1 + "[SupplierPrice]: " + data[i - 1], null);
        }
        productComposite.getSupplierProductList().add(spComp);
        BigDecimal defPrice = BigDecimal.ONE;

        if (i++ < length) {
            String val = data[i - 1];
            Debug.logError("Field" + i + "[DefaultPrice -org ]: " + val, null);            
            if (UtilValidate.isNotEmpty(val)) {
                defPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
            }

            ProductPriceComposite productPriceDefComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
//            productPriceDefComposite.getProductPrice().setprice(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
            productPriceDefComposite.getProductPrice().setprice(defPrice);
            Debug.logError("Field" + i + "[DefaultPrice]: " + defPrice, null);

            ProductPriceComposite productPriceListComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
            productPriceListComposite.getProductPrice().setprice(defPrice);

            //          productPriceListComposite.getProductPrice().setprice(i < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
            //  Debug.logError("Field" + i+1 + "[ListPrice]: " + data[i-1], null);
            productPriceListComposite.getProductPrice().setproductPriceTypeId("LIST_PRICE");
            try {
                productComposite.getProductItemPrice().addProductPrice(productPriceDefComposite);
                productComposite.getProductItemPrice().addProductPrice(productPriceListComposite);
            } catch (Exception ex) {
                Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        BigDecimal avgPrice = BigDecimal.ONE;
        if (i++ < length) {
            String val = data[i - 1];
           Debug.logError("Field" + i  + "[CostPrice-org]: " + val, null);            
            if (UtilValidate.isNotEmpty(val)) {
                avgPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
            }
            else{
                avgPrice = spComp.getSupplierProduct().getlastPrice();
            }

            ProductPriceComposite productPriceCostComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
            productPriceCostComposite.getProductPrice().setprice(avgPrice);
            productPriceCostComposite.getProductPrice().setproductPriceTypeId("AVERAGE_COST");
            Debug.logError("Field" + i  + "[CostPrice]: " + avgPrice, null);

            try {
//                if (productPriceCostComposite.getProductPrice().equals(BigDecimal.ZERO) == false) {
                    productComposite.getProductItemPrice().addProductPrice(productPriceCostComposite);
 //               }
            } catch (Exception ex) {
                Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Debug.logError("Field" + ++i + "[Stock At Hand]: " + data[i - 1], null);


        prod.settaxable(i++ < length ? data[i - 1] : "N");
        Debug.logError("Field" + i + "[Taxable]: " + data[i - 1], null);

        /*prod.setisVirtual(i++ < length ? data[i - 1] : "N");
         prod.setisVariant(i++ < length ? data[i - 1] : "N");
         prod.setproductTypeId(i++ < length ? data[i - 1] : "FINISHED_GOOD");
         //        Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

         prod.setmanufacturerPartyId(i++ < length ? data[i - 1] : "");
         prod.setfacilityId(i++ < length ? data[i - 1] : "");
         try {
         prod.setintroductionDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setreleaseDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setsupportDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         prod.setsalesDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
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
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setcreatedByUserLogin(i++ < length ? data[i - 1] : "");
         try {
         prod.setlastModifiedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setlastModifiedByUserLogin(i++ < length ? data[i - 1] : "");
         prod.setinShippingBox(i++ < length ? data[i - 1] : "");
         prod.setdefaultShipmentBoxTypeId(i++ < length ? data[i - 1] : "");
         prod.setorderDecimalQuantity(i++ < length ? data[i - 1] : "");
         try {
         prod.setlastUpdatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setlastUpdatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setcreatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         prod.setcreatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
         } catch (ParseException ex) {
         Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         prod.setlotIdFilledIn(i++ < length ? data[i - 1] : "");
         */
        return productComposite;
    }
}
