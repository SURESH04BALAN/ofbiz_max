package org.ofbiz.ordermax.base;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import mvc.controller.LoadProductPriceWorker;

//import net.xoetrope.xui.data.XModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.product.ProductSingleton;

import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.entity.ProductFeatureAndAppl;

public class PosProductHelper extends ImportProductHelper {

    static String module = PosProductHelper.class.getName();
    private static final int DECIMALS = UtilNumber.getBigDecimalScale("order.decimals");
    private static final int ROUNDING = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(DECIMALS, ROUNDING);
    public static final String[] AusState = {"AU-NSW", "AU-QLD", "AU-TAS", "AU-VIC", "AU-SA", "AU-WA", "AU-NT", "AU-ACT"};
    public static int acctgTransSeqNum = 1;
//    public static final String OwnerPartyId = "COMPANY";
//    public static final String OrganizationPartyId = "COMPANY";  
    public static final String InventoryGlAccountId = "140000";
    public static final String OffsettingGlAccountId = "210000";
    public static final String InventoryItemTypeId = "NON_SERIAL_INV_ITEM";
    public static final String GoodIdentificationTypeIdEAN = "EAN";
    public static final String GoodIdentificationTypeIdSKU = "SKU";
    public static final String ProductPriceTypeId_LISTPRICE = "LIST_PRICE";
    public static final String ProductPriceTypeId_DEFAULTPRICE = "DEFAULT_PRICE";
    public static final String ProductPriceTypeId_AVERAGECOST = "AVERAGE_COST";
    public static final String productPriceTypeId = "SIMPLE_AVG_COST";
//    public static final String organizationPartyId = "COMPANY";
    public static final String productPricePurposeId_purchase = "PURCHASE";
    public static final String productStoreGroupId_na = "_NA_";
    public static final String SUPPLIERPRODUCT = "SupplierProduct";

    // prepare the inventoryItem map
    public static Map<String, Object> prepareGoodIdentification(String productId,
            String goodIdentificationTypeId, String idValue) {

        Map<String, Object> fields = FastMap.newInstance();
        fields.put("goodIdentificationTypeId", goodIdentificationTypeId);
        fields.put("productId", productId);
        fields.put("idValue", idValue);

        return fields;
    }

    public static Map<String, Object> prepareSupplierProduct(String productId,
            String supplierPartyId, BigDecimal lastPrice, String currency,
            String supplierProductId) {

        Map<String, Object> fields = FastMap.newInstance();
        fields.put("partyId", supplierPartyId);
        fields.put("productId", productId);
        fields.put("lastPrice", lastPrice);
        fields.put("currencyUomId", currency);
        fields.put("supplierProductId", supplierProductId);
        fields.put("availableFromDate", UtilDateTime.nowTimestamp());
        fields.put("minimumOrderQuantity", BigDecimal.ONE);

        return fields;
    }

    // prepare the product map
    public static Map<String, Object> prepareProduct(String productId, String internalName, String productName) {
        Map<String, Object> fields = FastMap.newInstance();
        fields.put("productId", productId);
        fields.put("productTypeId", "FINISHED_GOOD");
        fields.put("internalName", internalName);
        fields.put("productName", productName);
        fields.put("requirementMethodId", "PRODRQM_AUTO");
        fields.put("isVirtual", "N");
        fields.put("isVariant", "N");

        return fields;
    }

    public static ProductInventoryPojo prepareProduct(String productId, String internalName, String productName, String brandName,
            String supplierId, String supplierProductId, String scanCode, String productPriceTypeId, String organizationPartyId, String facilityId, BigDecimal averageCost) {

        ProductInventoryPojo data = new ProductInventoryPojo();
        data.productId = productId;
        data.productTypeId = "FINISHED_GOOD";
        data.internalName = internalName;
        data.description = productName;
        data.longDescription = productName;
        data.productName = productName;
        data.requirementMethodEnumId = "PRODRQM_AUTO";
        data.brandName = brandName;
        data.requireInventory = "Y";

//        data.smallImageUrl = "/images/products/" + data.productId + "/small.jpg";
//        data.mediumImageUrl = "/images/products/" + data.productId + "/medium.jpg";
//        data.largeImageUrl = "/images/products/" + data.productId + "/large.jpg";
//        data.detailImageUrl = "/images/products/" + data.productId + "/detail.jpg";
//        data.originalImageUrl = "/images/products/" + data.productId + "/original.jpg";
//        data.comments ;
//        data.smallImageUrl ;
//        data.mediumImageUrl ;
//        data.largeImageUrl ;
//        data.height ;
//        data.heightUomId ;
//        data.productLength ;
//        data.productLengthUomId ;
//        data.width ;
//        data.widthUomId ;
//        data.taxable ;
//        data.weight ;
//        data.weightUomId ;
        data.isVirtual = "N";
        data.isVariant = "N";
        data.isInactive = "N";
        data.createdDate = UtilDateTime.nowTimestamp();
//        data.priceCurrencyUomId;
//        data.price;
        data.eanValue = scanCode;
        data.skuValue = scanCode;
//        data.productFeature1;
        data.supplierPartyId = supplierId;
        data.goodIdentificationTypeId1 = "EAN";
        data.goodIdentificationTypeId2 = "SKU";
        data.supplierProductId = supplierProductId;
        data.minimumOrderQuantity = BigDecimal.ONE;
        data.isProductExists = false;
        data.facilityId = facilityId;
        data.organizationPartyId = organizationPartyId;
        data.purchasePrice = averageCost;

        return data;
    }

    public static ProductInventoryPojo prepareProduct(ProductInventoryPojo data, String facilityId) {

        data.productTypeId = "FINISHED_GOOD";
        data.requirementMethodEnumId = "PRODRQM_AUTO";
        data.requireInventory = "Y";
//        data.smallImageUrl = "/images/products/" + data.productId + "/small.jpg";
//        data.mediumImageUrl = "/images/products/" + data.productId + "/medium.jpg";
//        data.largeImageUrl = "/images/products/" + data.productId + "/large.jpg";
//        data.detailImageUrl = "/images/products/" + data.productId + "/detail.jpg";
//        data.originalImageUrl = "/images/products/" + data.productId + "/original.jpg";

//    data.comments ;
//    data.smallImageUrl ;
//    data.mediumImageUrl ;
//    data.largeImageUrl ;
//    data.height ;
//    data.heightUomId ;
//    data.productLength ;
//    data.productLengthUomId ;
//    data.width ;
//    data.widthUomId ;
//    data.taxable ;
//    data.weight ;
//    data.weightUomId ;
        data.isVirtual = "N";
        data.isVariant = "N";
        data.isInactive = "N";
        data.createdDate = UtilDateTime.nowTimestamp();
//    data.priceCurrencyUomId;
//    data.price;
//    data.productFeature1;

        data.goodIdentificationTypeId1 = "EAN";
        data.goodIdentificationTypeId2 = "SKU";
        data.minimumOrderQuantity = BigDecimal.ONE;
        data.isProductExists = false;
        data.facilityId = facilityId;
        data.organizationPartyId = XuiContainer.getSession().getCompanyPartyId();

        return data;
    }

    public static ProductInventoryPojo prepareProductPrice(ProductInventoryPojo dataPojo, String facilityId) {

        dataPojo.productPriceTypeId = productPriceTypeId;
        dataPojo.productPricePurposeId = "PURCHASE";

        dataPojo.priceFromDate = UtilDateTime.nowTimestamp();
        dataPojo.productStoreGroupId = "_NA_";

        dataPojo.productAverageCostTypeId = "SIMPLE_AVG_COST";
        dataPojo.organizationPartyId = XuiContainer.getSession().getCompanyPartyId();
//  	   dataPojo.productId="TM1500"; 
        dataPojo.facilityId = facilityId;
        dataPojo.fromDate = UtilDateTime.nowTimestamp();

        return dataPojo;
    }

    public static ProductInventoryPojo prepareProductPrice(ProductInventoryPojo dataPojo,
            String currency, BigDecimal price, BigDecimal purchasePrice, String productPriceTypeId, String organizationPartyId, String facilityId) {

        if (dataPojo == null) {
            dataPojo = new ProductInventoryPojo();
        }

        dataPojo.productPriceTypeId = productPriceTypeId;
        dataPojo.productPricePurposeId = "PURCHASE";

        dataPojo.currencyUomId = currency;
        dataPojo.priceFromDate = UtilDateTime.nowTimestamp();
        dataPojo.price = price;
        dataPojo.productStoreGroupId = "_NA_";
        dataPojo.purchasePrice = purchasePrice;
        dataPojo.isPriceExists = false;

        dataPojo.productAverageCostTypeId = "SIMPLE_AVG_COST";
        dataPojo.organizationPartyId = organizationPartyId;
//	   dataPojo.productId="TM1500"; 
        dataPojo.facilityId = facilityId;
        dataPojo.fromDate = UtilDateTime.nowTimestamp();

        return dataPojo;
    }

    // prepare the inventoryItem map
    public static ProductInventoryPojo prepareInventoryItem(ProductInventoryPojo dataPojo,
            BigDecimal quantityOnHand, String inventoryItemId, String facilityId, String ownerPartyId) {

        if (dataPojo == null) {
            dataPojo = new ProductInventoryPojo();
        }

        /*    	public Double onHand;
         public Double availableToPromise;
         public Double inventoryValue;
         public Double minimumStock;
         public Double reorderQuantity;
         public Double daysToShip;
         public java.sql.Timestamp priceFromDate;
         public String productStoreGroupId;
         */
        //Debug.logInfo("inv id [" + inventoryItemId, module);
        dataPojo.inventoryItemId = inventoryItemId;
        dataPojo.inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
        dataPojo.ownerPartyId = ownerPartyId;
        dataPojo.organizationPartyId = ownerPartyId;
        dataPojo.facilityId = facilityId;
        dataPojo.expireDate = UtilDateTime.nowTimestamp();
//        dataPojo.expireDate.set(Calendar.YEAR, year + 1900);
//    	dataPojo.quantityOnHand =  quantityOnHand;

        //inventory
        if (quantityOnHand.compareTo(BigDecimal.ZERO) >= 0) {
            dataPojo.quantityOnHand = quantityOnHand;
        } else {
            dataPojo.quantityOnHand = BigDecimal.ZERO;
        }

        dataPojo.availableToPromise = dataPojo.quantityOnHand;
        dataPojo.inventoryGlAccountId = "140000";
        dataPojo.offsettingGlAccountId = "210000";
        dataPojo.minimumStock = new BigDecimal(5);
        dataPojo.reorderQuantity = new BigDecimal(5);
        dataPojo.daysToShip = new Long(2);
        dataPojo.isInventoryExists = false;

        return dataPojo;
    }

    public static Map<String, Object> prepareProductPrice(String productId,
            String currency, BigDecimal price, String productPriceTypeId) {

        Map<String, Object> fields = FastMap.newInstance();
        fields.put("productPriceTypeId", productPriceTypeId);
        fields.put("productPricePurposeId", "PURCHASE");
        fields.put("productId", productId);
        fields.put("currencyUomId", currency);
        fields.put("fromDate", UtilDateTime.nowTimestamp());
        fields.put("price", price);
        fields.put("productStoreGroupId", "_NA_");
        return fields;
    }

    // prepare the inventoryItem map
    public static Map<String, Object> prepareInventoryItem(String productId,
            BigDecimal quantityOnHand, String inventoryItemId, String facilityId) {
        Map<String, Object> fields = FastMap.newInstance();
        fields.put("inventoryItemId", inventoryItemId);
        fields.put("inventoryItemTypeId", "NON_SERIAL_INV_ITEM");
        fields.put("productId", productId);
        fields.put("ownerPartyId", XuiContainer.getSession().getCompanyPartyId());
        fields.put("facilityId", facilityId);
        fields.put("quantityOnHandTotal", quantityOnHand);
        fields.put("availableToPromiseTotal", quantityOnHand);
        return fields;
    }

    /**
     * Helper method to decode a <code>DataImportProduct</code> into a List of
     * <code>GenericValue</code> modeling that product in the OFBiz schema. If
     * for some reason obtaining data via the delegator fails, this service
     * throws that exception. Note that everything is done with the delegator
     * for maximum efficiency.
     *
     * @param data a <code>GenericValue</code> value
     * @param now a <code>Timestamp</code> value
     * @param goodIdentificationTypeId1 a <code>String</code> value
     * @param goodIdentificationTypeId2 a <code>String</code> value
     * @param delegator a <code>Delegator</code> value
     * @return a <code>List</code> value
     * @exception GenericEntityException if an error occurs
     * @exception Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    public static List<GenericValue> decodeProduct(ProductPojo data, Timestamp now, Delegator delegator) throws GenericEntityException, Exception {

        List toStore = FastList.newInstance();
        //Debug.logInfo("Now processing  data [" + data.productId + "] description [" + data.description + "]", module);

        // product
        Map input = FastMap.newInstance();
        // check if we should import the product as inactive
        String isInactive = data.isInactive;
        if ("Y".equalsIgnoreCase(isInactive)) {
            input.put("salesDiscontinuationDate", now);
        }

        input.put("productId", data.productId);
        input.put("productTypeId", data.productTypeId);
        input.put("internalName", data.internalName);
        input.put("description", data.description);
        input.put("longDescription", data.longDescription);
        input.put("productName", data.productName);
        input.put("brandName", data.brandName);
        input.put("comments", data.comments);
        input.put("smallImageUrl", data.smallImageUrl);
        input.put("mediumImageUrl", data.mediumImageUrl);
        input.put("largeImageUrl", data.largeImageUrl);
        input.put("originalImageUrl", data.originalImageUrl);
        input.put("detailImageUrl", data.detailImageUrl);

        input.put("productHeight", data.height);
        input.put("heightUomId", data.heightUomId);
        input.put("productDepth", data.productLength);
        input.put("depthUomId", data.productLengthUomId);
        input.put("productWidth", data.width);
        input.put("widthUomId", data.widthUomId);
        input.put("taxable", data.taxable);
        input.put("weight", data.weight);
        input.put("weightUomId", data.weightUomId);
        input.put("isVirtual", "N");
        input.put("isVariant", "N");
        input.put("requirementMethodEnumId", data.requirementMethodEnumId);
        input.put("requireInventory", data.requireInventory);

        if (data.createdDate != null) {
            input.put("createdDate", data.createdDate);
        } else {
            input.put("createdDate", now);
        }
        GenericValue product = delegator.makeValue("Product", input);
        toStore.add(product);
        Debug.logWarning("Product DONE", module);

        // product price
        input = FastMap.newInstance();
        input.put("productId", product.get("productId"));
        input.put("productPriceTypeId", "DEFAULT_PRICE");
        input.put("productPricePurposeId", "PURCHASE");
        input.put("productStoreGroupId", "_NA_");
        if (UtilValidate.isNotEmpty(data.currencyUomId)) {
            input.put("currencyUomId", data.currencyUomId);
        } else {
            Debug.logWarning("Product [" + data.productId + "] did not have a price currency, setting to default of [" + UtilProperties.getPropertyValue("general", "currency.uom.id.default") + "]", module);
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
        }
        input.put("fromDate", now);
        input.put("price", data.price);
        input.put("createdDate", now);
        GenericValue productPrice = delegator.makeValue("ProductPrice", input);
        toStore.add(productPrice);
        Debug.logWarning("ProductPrice DONE", module);

        // make a list price as well, so that price rules and other features can work
        GenericValue listPrice = delegator.makeValue("ProductPrice", productPrice.getAllFields());
        listPrice.put("productPriceTypeId", "LIST_PRICE");
        toStore.add(listPrice);
        Debug.logWarning("LIST_PRICE DONE", module);

        // good identification (this is per customIdN)
        if (!UtilValidate.isEmpty(data.eanValue) && !UtilValidate.isEmpty(data.goodIdentificationTypeId1)) {
            input = FastMap.newInstance();
            input.put("goodIdentificationTypeId", data.goodIdentificationTypeId1);
            input.put("productId", product.get("productId"));
            input.put("idValue", data.eanValue);
            GenericValue goodIdentification = delegator.makeValue("GoodIdentification", input);
            toStore.add(goodIdentification);
        }

        if (!UtilValidate.isEmpty(data.eanValue) && !UtilValidate.isEmpty(data.goodIdentificationTypeId2)) {
            input = FastMap.newInstance();
            input.put("goodIdentificationTypeId", data.goodIdentificationTypeId2);
            input.put("productId", product.get("productId"));
            input.put("idValue", data.skuValue);
            GenericValue goodIdentification = delegator.makeValue("GoodIdentification", input);
            toStore.add(goodIdentification);
        }

        Debug.logWarning("goodIdentification DONE", module);
        // product features (this is per productFeatureN) all of these have type OTHER_FEATURE
        if (!UtilValidate.isEmpty(data.productFeature1)) {
            String productFeatureId = data.productFeature1;
            productFeatureId = productFeatureId.toUpperCase().replaceAll("\\s", "_");
            GenericValue productFeature = delegator.findByPrimaryKey("ProductFeature", UtilMisc.toMap("productFeatureId", productFeatureId));
            if (productFeature == null) {
                input = FastMap.newInstance();
                input.put("productFeatureId", productFeatureId);
                input.put("description", data.productFeature1);
                input.put("productFeatureTypeId", "OTHER_FEATURE");
                productFeature = delegator.makeValue("ProductFeature", input);
                toStore.add(productFeature);
            }

            input = FastMap.newInstance();
            input.put("productId", product.get("productId"));
            input.put("productFeatureId", productFeatureId);
            input.put("fromDate", now);
            GenericValue productFeatureAppl = delegator.makeValue("ProductFeatureAppl", input);
            toStore.add(productFeatureAppl);
        }

        // insert a purchase record in SupplierProduct
        if (UtilValidate.isNotEmpty(data.supplierPartyId)) {
            String supplierPartyId = data.supplierPartyId;
            input = FastMap.newInstance();
            input.put("productId", product.get("productId"));
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
            // try to find the supplier
            GenericValue supplier = delegator.findByPrimaryKeyCache("Party", UtilMisc.toMap("partyId", supplierPartyId));
            if (UtilValidate.isEmpty(supplier)) {
                //Debug.logInfo("Supplier with ID [" + supplierPartyId + "] not found, will be creating it", module);

                TransactionUtil.begin();
                delegator.create("Party", UtilMisc.toMap("partyId", supplierPartyId, "partyTypeId", "PARTY_GROUP"));
                delegator.create("PartyGroup", UtilMisc.toMap("partyId", supplierPartyId, "groupName", supplierPartyId));
                delegator.create("PartyRole", UtilMisc.toMap("partyId", supplierPartyId, "roleTypeId", "SUPPLIER"));
                TransactionUtil.commit();
            }
            input.put("partyId", supplierPartyId);
            input.put("availableFromDate", now);
            input.put("minimumOrderQuantity", data.minimumOrderQuantity);
            input.put("supplierPrefOrderId", "10_MAIN_SUPPL");
            input.put("lastPrice", data.purchasePrice);
            input.put("supplierProductId", data.supplierProductId);   // vendor part number -- default to our productID for now
            GenericValue supplierProduct = delegator.makeValue("SupplierProduct", input);
            toStore.add(supplierProduct);
            Debug.logWarning("SupplierProduct DONE", module);
        }

        return toStore;
    }

    static public Map<String, GenericValue> decodeProductMap(ProductPojo data, Timestamp now, Delegator delegator) throws GenericEntityException, Exception {

        Map<String, GenericValue> toStore = FastMap.newInstance();
        //Debug.logInfo("Now processing  data [" + data.productId + "] description [" + data.description + "]", module);

        // product
        Map<String, Object> input = FastMap.newInstance();
        // check if we should import the product as inactive
        String isInactive = data.isInactive;
        if ("Y".equalsIgnoreCase(isInactive)) {
            input.put("salesDiscontinuationDate", now);
        }

        input.put("productId", data.productId);
        input.put("productTypeId", data.productTypeId);
        input.put("internalName", data.internalName);
        input.put("description", data.description);
        input.put("longDescription", data.longDescription);
        input.put("productName", data.productName);
        input.put("brandName", data.brandName);
        input.put("comments", data.comments);
        input.put("smallImageUrl", data.smallImageUrl);
        input.put("mediumImageUrl", data.mediumImageUrl);
        input.put("largeImageUrl", data.largeImageUrl);
        input.put("originalImageUrl", data.originalImageUrl);
        input.put("detailImageUrl", data.detailImageUrl);

        input.put("productHeight", data.height);
        input.put("heightUomId", data.heightUomId);
        input.put("productDepth", data.productLength);
        input.put("depthUomId", data.productLengthUomId);
        input.put("productWidth", data.width);
        input.put("widthUomId", data.widthUomId);
        input.put("taxable", data.taxable);
        input.put("weight", data.weight);
        input.put("weightUomId", data.weightUomId);
        input.put("taxable", data.taxable);
        input.put("isVirtual", data.isVirtual);
        input.put("isVariant", data.isVariant);
        input.put("inventoryMessage", data.inventoryMessage);
        input.put("returnable", data.returnable);
        input.put("chargeShipping", data.chargeShipping);
        input.put("autoCreateKeywords", data.autoCreateKeywords);
        input.put("includeInPromotions", data.includeInPromotions);
        input.put("requirementMethodEnumId", data.requirementMethodEnumId);
        input.put("requireInventory", data.requireInventory);

        if (data.createdDate != null) {
            input.put("createdDate", data.createdDate);
        } else {
            input.put("createdDate", now);
        }
        GenericValue product = delegator.makeValue("Product", input);
        toStore.put("Product", product);
        Debug.logWarning("Product DONE", module);

        // product price
        input = FastMap.newInstance();
        input.put("productId", product.get("productId"));
        input.put("productPriceTypeId", "DEFAULT_PRICE");
        input.put("productPricePurposeId", "PURCHASE");
        input.put("productStoreGroupId", "_NA_");
        if (UtilValidate.isNotEmpty(data.currencyUomId)) {
            input.put("currencyUomId", data.currencyUomId);
        } else {
            Debug.logWarning("Product [" + data.productId + "] did not have a price currency, setting to default of [" + UtilProperties.getPropertyValue("general", "currency.uom.id.default") + "]", module);
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
        }
        input.put("fromDate", now);
        input.put("price", data.price);
        input.put("createdDate", now);
        GenericValue productPrice = delegator.makeValue("ProductPrice", input);

        toStore.put("ProductPrice", productPrice);
        Debug.logWarning("ProductPrice DONE", module);

        // make a list price as well, so that price rules and other features can work
        GenericValue listPrice = delegator.makeValue("ProductPrice", productPrice.getAllFields());
        listPrice.put("productPriceTypeId", "LIST_PRICE");

//        toStore.add(listPrice);
        toStore.put("ListPrice", listPrice);

        // make a list price as well, so that price rules and other features can work
        GenericValue averageCostPrice = delegator.makeValue("ProductPrice", productPrice.getAllFields());
        averageCostPrice.put("price", data.purchasePrice);
        averageCostPrice.put("productPriceTypeId", "AVERAGE_COST");
        toStore.put("AverageCost", averageCostPrice);

        Debug.logWarning("LIST_PRICE DONE", module);

        // good identification (this is per customIdN)
        if (!UtilValidate.isEmpty(data.eanValue) && !UtilValidate.isEmpty(data.goodIdentificationTypeId1)) {
            input = FastMap.newInstance();
            input.put("goodIdentificationTypeId", data.goodIdentificationTypeId1);
            input.put("productId", product.get("productId"));
            input.put("idValue", data.eanValue);
            GenericValue goodIdentification = delegator.makeValue("GoodIdentification", input);
//            toStore.add(goodIdentification);
            toStore.put("GoodIdentification", goodIdentification);
        }

        if (!UtilValidate.isEmpty(data.eanValue) && !UtilValidate.isEmpty(data.goodIdentificationTypeId2)) {
            input = FastMap.newInstance();

            input.put("goodIdentificationTypeId", data.goodIdentificationTypeId2);
            input.put("productId", product.get("productId"));
            input.put("idValue", data.skuValue);
            GenericValue goodIdentification = delegator.makeValue("GoodIdentification", input);
//            toStore.add(goodIdentification);
            toStore.put("GoodIdentification1", goodIdentification);
        }

        Debug.logWarning("goodIdentification DONE", module);
        // product features (this is per productFeatureN) all of these have type OTHER_FEATURE
        if (!UtilValidate.isEmpty(data.productFeature1)) {
            String productFeatureId = data.productFeature1;
            productFeatureId = productFeatureId.toUpperCase().replaceAll("\\s", "_");
            GenericValue productFeature = delegator.findByPrimaryKey("ProductFeature", UtilMisc.toMap("productFeatureId", productFeatureId));
            if (productFeature == null) {
                input = FastMap.newInstance();
                input.put("productFeatureId", productFeatureId);
                input.put("description", data.productFeature1);
                input.put("productFeatureTypeId", "OTHER_FEATURE");
                productFeature = delegator.makeValue("ProductFeature", input);
//                toStore.add(productFeature);
                toStore.put("ProductFeature", productFeature);
            }

            input = FastMap.newInstance();
            input.put("productId", product.get("productId"));
            input.put("productFeatureId", productFeatureId);
            input.put("fromDate", now);
            GenericValue productFeatureAppl = delegator.makeValue("ProductFeatureAppl", input);
//            toStore.add(productFeatureAppl);
            toStore.put("ProductFeatureAppl", productFeatureAppl);
        }

        // insert a purchase record in SupplierProduct
        if (UtilValidate.isNotEmpty(data.supplierPartyId)) {
            String supplierPartyId = data.supplierPartyId;
            input = FastMap.newInstance();
            input.put("productId", product.get("productId"));
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
            // try to find the supplier
            GenericValue supplier = delegator.findByPrimaryKeyCache("Party", UtilMisc.toMap("partyId", supplierPartyId));
            if (UtilValidate.isEmpty(supplier)) {
                //Debug.logInfo("Supplier with ID [" + supplierPartyId + "] not found, will be creating it", module);

                TransactionUtil.begin();
                delegator.create("Party", UtilMisc.toMap("partyId", supplierPartyId, "partyTypeId", "PARTY_GROUP"));
                delegator.create("PartyGroup", UtilMisc.toMap("partyId", supplierPartyId, "groupName", supplierPartyId));
                delegator.create("PartyRole", UtilMisc.toMap("partyId", supplierPartyId, "roleTypeId", "SUPPLIER"));
                TransactionUtil.commit();
            }
            input.put("partyId", supplierPartyId);
            input.put("availableFromDate", now);
            input.put("minimumOrderQuantity", data.minimumOrderQuantity);
            input.put("supplierPrefOrderId", "10_MAIN_SUPPL");
            input.put("lastPrice", data.purchasePrice);
            input.put("supplierProductId", data.supplierProductId);   // vendor part number -- default to our productID for now
            GenericValue supplierProduct = delegator.makeValue("SupplierProduct", input);
//            toStore.add(supplierProduct);
            toStore.put("SupplierProduct", supplierProduct);
            Debug.logWarning("SupplierProduct DONE", module);
        }

        // Create the product average cost
        input = FastMap.newInstance();
        input.put("organizationPartyId", data.organizationPartyId);
        input.put("fromDate", now);
        input.put("averageCost", data.purchasePrice);
        input.put("productId", data.productId);
        input.put("productAverageCostTypeId", data.productAverageCostTypeId);
        input.put("facilityId", data.facilityId);
        GenericValue productAverageCost = delegator.makeValue("ProductAverageCost", input);
        toStore.put("ProductAverageCost", productAverageCost);

        return toStore;
    }

    /**
     * Helper method to decode a DataImportInventory into a List of
     * GenericValues modeling that product in the OFBiz schema. If for some
     * reason obtaining data via the delegator fails, this service throws that
     * exception. Note that everything is done with the delegator for maximum
     * efficiency.
     *
     * @param productInventory a <code>GenericValue</code> value
     * @param organizationPartyId a <code>String</code> value
     * @param facilityId a <code>String</code> value
     * @param inventoryGlAccountId a <code>String</code> value
     * @param offsettingGlAccountId a <code>String</code> value
     * @param acctgTransId a <code>String</code> value
     * @param currencyUomId a <code>String</code> value
     * @param now a <code>Timestamp</code> value
     * @param delegator a <code>Delegator</code> value
     * @return a <code>List</code> value
     * @exception GenericEntityException if an error occurs
     * @exception Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    private static List<GenericValue> decodeInventory(ProductInventoryPojo productInventory, String acctgTransId, Timestamp now, Delegator delegator) throws GenericEntityException, Exception {

        List toStore = FastList.newInstance();

        /*        //Debug.logInfo("Now processing  data [" + productInventory.productId + "]", module);

         String productId = productInventory.productId;
         BigDecimal quantityOnHand = productInventory.quantityOnHand;
         if (UtilValidate.isEmpty(quantityOnHand)) {
         quantityOnHand = new BigDecimal(0);
         }

         BigDecimal availableToPromise = productInventory.availableToPromise;
         if (UtilValidate.isEmpty(availableToPromise)) {
         availableToPromise = new BigDecimal(productInventory.quantityOnHand.intValue());
         }

         BigDecimal inventoryValue = productInventory.inventoryValue;
         if (UtilValidate.isEmpty(inventoryValue)) {
         inventoryValue = new BigDecimal(productInventory.quantityOnHand.intValue()*productInventory.purchasePrice.doubleValue());
         }

         BigDecimal averageCost = ZERO;
         if (quantityOnHand.doubleValue() > 0.0) {
         averageCost = new BigDecimal(inventoryValue.doubleValue() / quantityOnHand.doubleValue()).setScale(DECIMALS, ROUNDING);
         }

         // Verify that productId exists
         GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
         if (product == null) {
         //Debug.logInfo("Could not find product [" + productId + "], not importing.", module);
         return toStore;
         }

         productInventory.inventoryItemId = delegator.getNextSeqId("InventoryItem");
         //        inventoryItemId
         // Create the inventory item
         FastMap<Object, Object> input = FastMap.newInstance();
         input.put("inventoryItemId", productInventory.inventoryItemId);
         input.put("unitCost", averageCost);
         input.put("productId", productInventory.productId);
         input.put("ownerPartyId", productInventory.ownerPartyId);
         input.put("datetimeReceived", now);
         input.put("facilityId", productInventory.facilityId);
         input.put("comments", "Auto-generated from Product Inventory Import.");
         input.put("inventoryItemTypeId", productInventory.inventoryItemTypeId);
         input.put("currencyUomId", productInventory.currencyUomId);
         input.put("expireDate", productInventory.expireDate);
         input.put("quantityOnHandTotal", quantityOnHand);        
         input.put("availableToPromiseTotal", quantityOnHand);        
         input.put("accountingQuantityTotal", quantityOnHand);     
        
         //        input.put("availableToPromise", quantityOnHand);        
         //        input.put("quantityOnHand", quantityOnHand);        
         GenericValue inventoryItem = delegator.makeValue("InventoryItem", input);
         toStore.add(inventoryItem);

         // Create the inventory item detail
         input = FastMap.newInstance();
         input.put("inventoryItemId", productInventory.inventoryItemId);
         input.put("inventoryItemDetailSeqId", UtilFormatOut.formatPaddedNumber(1, 4));
         input.put("quantityOnHandDiff", quantityOnHand);
         if (UtilValidate.isNotEmpty(availableToPromise)) {
         input.put("availableToPromiseDiff", availableToPromise);
         } else {
         input.put("availableToPromiseDiff", quantityOnHand);
         }
         GenericValue inventoryItemDetail = delegator.makeValue("InventoryItemDetail", input);
         toStore.add(inventoryItemDetail);

         // Create the product average cost
         input = FastMap.newInstance();
         input.put("organizationPartyId",  productInventory.organizationPartyId);
         input.put("fromDate", now);
         input.put("averageCost", averageCost);
         input.put("productId", productInventory.productId);
         input.put("productAverageCostTypeId", "SIMPLE_AVG_COST");
         input.put("facilityId", productInventory.facilityId);        
         GenericValue productAverageCost = delegator.makeValue("ProductAverageCost", input);
         toStore.add(productAverageCost);

         // Create the two AcctgTransEntries for this item
         input = FastMap.newInstance();
         input.put("acctgTransId", acctgTransId);
         input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
         input.put("acctgTransEntryTypeId", "_NA_");
         input.put("productId", productInventory.productId);
         input.put("organizationPartyId", productInventory.organizationPartyId);
         input.put("currencyUomId", productInventory.currencyUomId);
         input.put("reconcileStatusId", "AES_NOT_RECONCILED");
         input.put("glAccountId", productInventory.inventoryGlAccountId);
         input.put("debitCreditFlag", "D");
         input.put("amount", productInventory.inventoryValue);
         GenericValue debitAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
         toStore.add(debitAcctgTransEntry);
         acctgTransSeqNum++;

         input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
         input.put("glAccountId", productInventory.offsettingGlAccountId);
         input.put("debitCreditFlag", "C");
         GenericValue creditAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
         toStore.add(creditAcctgTransEntry);
         acctgTransSeqNum++;

         // Create the ProductFacility record which is now required for viewing inventory reports
         input = FastMap.newInstance();
         input.put("facilityId", productInventory.facilityId);
         input.put("productId", productInventory.productId);
         if (UtilValidate.isEmpty(productInventory.minimumStock)) {
         input.put("minimumStock", new Double(0.0));
         } else {
         input.put("minimumStock", productInventory.minimumStock);
         }
         input.put("reorderQuantity", productInventory.reorderQuantity);
         input.put("daysToShip", productInventory.daysToShip);
         toStore.add(delegator.makeValue("ProductFacility", input));
         */
        return toStore;
    }

//    @SuppressWarnings("unchecked")
    public static Map<String, GenericValue> decodeInventoryMap(ProductInventoryPojo productInventory, String acctgTransId, Timestamp now, Delegator delegator) throws GenericEntityException, Exception {

        Map<String, GenericValue> toStore = FastMap.newInstance();

        //Debug.logInfo("Now processing  productInventory.inventoryItemId [" + productInventory.inventoryItemId + "]", module);
        String productId = productInventory.productId;
        BigDecimal quantityOnHand = productInventory.quantityOnHand;
        if (UtilValidate.isEmpty(quantityOnHand)) {
            quantityOnHand = new BigDecimal(0);
        }

        BigDecimal availableToPromise = productInventory.availableToPromise;
        if (UtilValidate.isEmpty(availableToPromise)) {
            availableToPromise = quantityOnHand;
        }

        BigDecimal inventoryValue = productInventory.inventoryValue;
        if (UtilValidate.isEmpty(inventoryValue)) {
            inventoryValue = new BigDecimal(quantityOnHand.intValue() * productInventory.purchasePrice.doubleValue());
        }

        BigDecimal averageCost = BigDecimal.ONE;
        if (quantityOnHand.doubleValue() > 0.0) {
            averageCost = new BigDecimal(inventoryValue.doubleValue() / quantityOnHand.doubleValue()).setScale(DECIMALS, ROUNDING);
        }

        // Verify that productId exists
/*        GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
         if (product == null) {
         //Debug.logInfo("Could not find product [" + productId + "], not importing.", module);
         return toStore;
         }
         */
//        productInventory.inventoryItemId = delegator.getNextSeqId("InventoryItem");
//        inventoryItemId
        // Create the inventory item
        FastMap<Object, Object> input = FastMap.newInstance();
        input.put("inventoryItemId", productInventory.inventoryItemId);
        input.put("unitCost", averageCost);
        input.put("productId", productInventory.productId);
        input.put("ownerPartyId", productInventory.ownerPartyId);
        input.put("datetimeReceived", now);
        input.put("facilityId", productInventory.facilityId);
        input.put("comments", "Auto-generated from Product Inventory Import.");
        input.put("inventoryItemTypeId", productInventory.inventoryItemTypeId);
        input.put("currencyUomId", productInventory.currencyUomId);
        input.put("expireDate", productInventory.expireDate);
        input.put("quantityOnHandTotal", quantityOnHand);
        input.put("availableToPromiseTotal", quantityOnHand);
        input.put("accountingQuantityTotal", quantityOnHand);

        GenericValue inventoryItem = delegator.makeValue("InventoryItem", input);
        inventoryItem.set("inventoryItemId", productInventory.inventoryItemId);
        //Debug.logInfo("Now processwd productInventory.inventoryItemId [" + productInventory.inventoryItemId + "]", module);
        toStore.put("InventoryItem", inventoryItem);

        // Create the inventory item detail
        input = FastMap.newInstance();
        input.put("inventoryItemId", productInventory.inventoryItemId);
        input.put("inventoryItemDetailSeqId", UtilFormatOut.formatPaddedNumber(1, 4));
        input.put("effectiveDate", UtilDateTime.nowTimestamp());
        input.put("quantityOnHandDiff", quantityOnHand);
        if (UtilValidate.isNotEmpty(availableToPromise)) {
            input.put("availableToPromiseDiff", availableToPromise);
        } else {
            input.put("availableToPromiseDiff", quantityOnHand);
        }
        input.put("accountingQuantityDiff", BigDecimal.ZERO);
        input.put("unitCost", averageCost);
        GenericValue inventoryItemDetail = delegator.makeValue("InventoryItemDetail", input);
//        toStore.add(inventoryItemDetail);
        toStore.put("InventoryItemDetail_one", inventoryItemDetail);

        // Create the inventory item detail
        input = FastMap.newInstance();
        input.put("inventoryItemId", productInventory.inventoryItemId);
        input.put("inventoryItemDetailSeqId", UtilFormatOut.formatPaddedNumber(2, 4));
        input.put("effectiveDate", UtilDateTime.nowTimestamp());
        input.put("quantityOnHandDiff", BigDecimal.ZERO);
        input.put("availableToPromiseDiff", BigDecimal.ZERO);
        input.put("accountingQuantityDiff", quantityOnHand);
        input.put("unitCost", averageCost);
        GenericValue inventoryItemDetail_two = delegator.makeValue("InventoryItemDetail", input);

        //        toStore.add(inventoryItemDetail);
        toStore.put("InventoryItemDetail_two", inventoryItemDetail_two);

        //Debug.logInfo("Now processing ProductAverageCost", module);
        // Create the product average cost
        input = FastMap.newInstance();
        //PRODUCT_AVERAGE_COST_TYPE_ID		   SIMPLE_AVG_COST
        input.put("productAverageCostTypeId", "SIMPLE_AVG_COST");
        input.put("organizationPartyId", productInventory.organizationPartyId);
        input.put("fromDate", now);
        input.put("averageCost", averageCost);
        input.put("productId", productInventory.productId);
        input.put("facilityId", productInventory.facilityId);
        GenericValue productAverageCost = delegator.makeValue("ProductAverageCost", input);
//        toStore.add(productAverageCost);
        toStore.put("ProductAverageCost", productAverageCost);
        //Debug.logInfo("Now processing ProductAverageCost typeid [" + productAverageCost.getString("productAverageCostTypeId") + "]", module);

        // Create the two AcctgTransEntries for this item
        input = FastMap.newInstance();
        input.put("acctgTransId", acctgTransId);
        input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
        input.put("acctgTransEntryTypeId", "_NA_");
        input.put("productId", productInventory.productId);
        input.put("organizationPartyId", productInventory.organizationPartyId);
        input.put("currencyUomId", productInventory.currencyUomId);
        input.put("reconcileStatusId", "AES_NOT_RECONCILED");
        input.put("glAccountId", productInventory.inventoryGlAccountId);
        input.put("debitCreditFlag", "D");
        input.put("amount", productInventory.inventoryValue);
        GenericValue debitAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
//        toStore.add(debitAcctgTransEntry);
        toStore.put("DebitAcctgTransEntry", debitAcctgTransEntry);
        acctgTransSeqNum++;

        input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
        input.put("glAccountId", productInventory.offsettingGlAccountId);
        input.put("debitCreditFlag", "C");
        GenericValue creditAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
//        toStore.add(creditAcctgTransEntry);
        toStore.put("CreditAcctgTransEntry", creditAcctgTransEntry);
        acctgTransSeqNum++;

        // Create the ProductFacility record which is now required for viewing inventory reports
        input = FastMap.newInstance();
        input.put("facilityId", productInventory.facilityId);
        input.put("productId", productInventory.productId);
        if (UtilValidate.isEmpty(productInventory.minimumStock)) {
            input.put("minimumStock", new Double(0.0));
        } else {
            input.put("minimumStock", productInventory.minimumStock);
        }
        input.put("reorderQuantity", productInventory.reorderQuantity);
        input.put("daysToShip", productInventory.daysToShip);
        input.put("lastInventoryCount", productInventory.quantityOnHand);
        GenericValue productFacility = (delegator.makeValue("ProductFacility", input));
        toStore.put("ProductFacility", productFacility);
        return toStore;
    }

    /**
     * Import products using <code>DataImportProduct</code>. Note that this
     * service is not wrapped in a transaction. Each product record imported is
     * in its own transaction, so it can store as many good records as possible.
     * The goodIdentificationTypeIdN parameters correspond to the type of the
     * customIdN fields in <code>DataImportProduct</code>.
     *
     * @param dctx a <code>DispatchContext</code> value
     * @param context a <code>Map</code> value
     * @return a <code>Map</code> value
     */
    public static Map<String, Object> importProducts(ProductInventoryPojo product, Delegator delegator) {

        //        String goodIdentificationTypeId1 = (String) context.get("goodIdentificationTypeId1");
        //       String goodIdentificationTypeId2 = (String) context.get("goodIdentificationTypeId2");
        Timestamp now = UtilDateTime.nowTimestamp();

        int imported = 0;

        // main try/catch block that traps errors related to obtaining data from delegator
        try {

            // make sure the supplied goodIdentificationTypes exist
            GenericValue goodIdentificationType1 = null;
            if (UtilValidate.isNotEmpty(product.goodIdentificationTypeId1)) {
                goodIdentificationType1 = delegator.findByPrimaryKey("GoodIdentificationType", UtilMisc.toMap("goodIdentificationTypeId", product.goodIdentificationTypeId1));
                if (goodIdentificationType1 == null) {
                    return ServiceUtil.returnError("Cannot import products: goodIdentificationType [" + product.goodIdentificationTypeId1 + "] does not exist.");
                }
            }

            GenericValue goodIdentificationType2 = null;
            if (UtilValidate.isNotEmpty(product.goodIdentificationTypeId2)) {
                goodIdentificationType2 = delegator.findByPrimaryKey("GoodIdentificationType", UtilMisc.toMap("goodIdentificationTypeId", product.goodIdentificationTypeId1));
                if (goodIdentificationType2 == null) {
                    return ServiceUtil.returnError("Cannot import products: goodIdentificationType [" + product.goodIdentificationTypeId2 + "] does not exist.");
                }
            }

            try {
                // use the helper method to decode the product into a List of GenericValues
                // todo this will never be null
                List<GenericValue> toStore = decodeProduct(product, now, delegator);
                if (toStore == null) {
                    Debug.logWarning("Faild to import product[" + product.productId + "] because data was bad.  Check preceding warnings for reason.", module);
                }

                // next we're going to store all each product's data in its own transaction, so if one product's data is bad, the others will still get stored
                TransactionUtil.begin();

                // store the results and mark this product as processed
                delegator.storeAll(toStore);

                // log the import
                //Debug.logInfo("Successfully imported product [" + product.productId + "].", module);
                imported += 1;

                TransactionUtil.commit();
                //Debug.logInfo("Successfully commited product [" + product.productId + "].", module);

            } catch (GenericEntityException e) {
                // if there was an error, we'll just skip this product
                TransactionUtil.rollback();
                Debug.logError(e, "Failed to import product[" + product.productId + "]. Error stack follows.", module);

                // store the import error
                String message = "Failed to import product[" + product.productId + "], Error message : " + e.getMessage();
                //                    storeImportProductError(product, message, delegator);
            } catch (Exception e) {
                TransactionUtil.rollback();
                Debug.logError(e, "Failed to import product[" + product.productId + "]. Error stack follows.", module);

                // store the import error
                String message = "Failed to import product[" + product.productId + "], Error message : " + e.getMessage();
                //                    storeImportProductError(product, message, delegator);
            }
            //            }
            //            importProducts.close();

        } catch (GenericEntityException e) {
            String message = "Cannot import products: Unable to use delegator to retrieve data from the database.  Error is: " + e.getMessage();
            Debug.logError(e, message, module);
            return ServiceUtil.returnError(message);
        }

        Map<String, Object> results = ServiceUtil.returnSuccess();
        results.put("productsImported", new Integer(imported));
        return results;
    }

    /**
     * Import product inventory using DataImportInventory. The
     * organizationPartyId is the ownerPartyId of the facilityId, and all
     * inventory values in DataImportInventory must already be in the right
     * currency. One AcctgTrans plus many entries are created for each
     * facility/ownerPartyId combination. Both gl accounts must be configured
     * for each owner of the inventory in the warehouse facilities. Note that
     * this service is not wrapped in a transaction. Each inventory record set
     * imported is in its own transaction, so it can store as many good records
     * as possible. Will set a ProductFacility with minimumStock of 0 for the
     * facilityId if no minimumStock is supplied and fill in reorderQuantity and
     * daysToShip as well.
     *
     * @param dctx a <code>DispatchContext</code> value
     * @param context a <code>Map</code> value
     * @return a <code>Map</code> value
     */
    public static Map<String, Object> importProductInventory(LocalDispatcher dispatcher, Delegator delegator, Map context, ProductInventoryPojo product) {

        GenericValue userLogin = (GenericValue) context.get("userLogin");
        //Debug.logInfo("importProductInventory in ", module);
        //      String inventoryGlAccountId = (String) context.get("inventoryGlAccountId");
        //    String offsettingGlAccountId = (String) context.get("offsettingGlAccountId");

        int totalImported = 0;

        // run the facility-specific data importing routine for each unique facility
        try {

            int importedFromFacility = importInventoryToFacility(product, product.facilityId, product.inventoryGlAccountId, product.offsettingGlAccountId, dispatcher, delegator, userLogin);
            totalImported += importedFromFacility;

        } catch (GenericServiceException se) {
            String errMsg = "Error in importProductInventory service: " + se.getMessage();
            Debug.logError(se, errMsg, module);
            return ServiceUtil.returnError(errMsg);
        } catch (GenericEntityException ee) {
            String errMsg = "Error in importProductInventory service: " + ee.getMessage();
            Debug.logError(ee, errMsg, module);
            return ServiceUtil.returnError(errMsg);
        }

        Map<String, Object> results = ServiceUtil.returnSuccess();
        results.put("importedRecords", new Integer(totalImported));
        //Debug.logInfo("Could not find product [importProductInventory]2", module);
        return results;
    }

    /**
     * A helper method to import all the inventory items into a particular
     * facility, using its ownerPartyId and currency.
     *
     * @param facilityId a <code>String</code> value
     * @param inventoryGlAccountId a <code>String</code> value
     * @param offsettingGlAccountId a <code>String</code> value
     * @param userLogin a <code>GenericValue</code> value
     * @param dispatcher a <code>LocalDispatcher</code> value
     * @return an <code>int</code> value
     * @exception GenericEntityException if an error occurs
     * @exception GenericServiceException if an error occurs
     */
    @SuppressWarnings("unchecked")
    private static int importInventoryToFacility(ProductInventoryPojo productInventory, String facilityId, String inventoryGlAccountId, String offsettingGlAccountId, LocalDispatcher dispatcher, Delegator delegator, GenericValue userLogin) throws GenericEntityException, GenericServiceException {

        GenericValue facility = delegator.findByPrimaryKeyCache("Facility", UtilMisc.toMap("facilityId", facilityId));

        if (UtilValidate.isEmpty(facility)) {
            String errMsg = "Error in importProductInventory service: facility [" + facilityId + "] does not exist";
            Debug.logError(errMsg, module);
            return 0;
        }

        // Make sure the organizationParty exists
        GenericValue organizationParty = facility.getRelatedOneCache("OwnerParty");
        if (UtilValidate.isEmpty(organizationParty)) {
            String errMsg = "Error in importProductInventory service: owner party for facility [" + facilityId + "] does not exist";
            Debug.logError(errMsg, module);
            return 0;
        }
        String organizationPartyId = organizationParty.getString("partyId");

        // Make sure the supplied GL accounts exist for the organization
        GenericValue glAccountOrganization = null;
        if (UtilValidate.isNotEmpty(inventoryGlAccountId)) {
            glAccountOrganization = delegator.findByPrimaryKeyCache("GlAccountOrganization", UtilMisc.toMap("glAccountId", inventoryGlAccountId, "organizationPartyId", organizationPartyId));
            if (glAccountOrganization == null) {
                Debug.logError("Cannot import inventory: organization [" + organizationPartyId + "] does not have inventory General Ledger account [" + inventoryGlAccountId + "] defined in GlAccountOrganization.", module);
                return 0;
            }
        }
        if (UtilValidate.isNotEmpty(offsettingGlAccountId)) {
            glAccountOrganization = delegator.findByPrimaryKeyCache("GlAccountOrganization", UtilMisc.toMap("glAccountId", offsettingGlAccountId, "organizationPartyId", organizationPartyId));
            if (glAccountOrganization == null) {
                Debug.logError("Cannot import inventory: organization [" + organizationPartyId + "] does not have offsetting General Ledger account [" + offsettingGlAccountId + "] defined in GlAccountOrganization.", module);
                return 0;
            }
        }

        /*        String currencyUomId = UtilCommon.getOrgBaseCurrency(organizationPartyId, delegator);
         if (UtilValidate.isEmpty(currencyUomId)) {
         String errMsg = "Error in importProductInventory service: organization [" + organizationPartyId + "] does not have a baseCurrencyUomId defined in PartyAcctgPref";
         Debug.logError(errMsg, module);
         return 0;
         }
         */
        // All transaction entries have same timestamp
        Timestamp now = UtilDateTime.nowTimestamp();

        Debug.logWarning("createAcctgTrans [ start" + "] was successful.", module);
        // Create the header of an AcctgTrans record for all the subsequent transaction entries
        Map createAcctgTransResults = dispatcher.runSync("createAcctgTrans", UtilMisc.toMap("acctgTransTypeId", "INVENTORY", "glFiscalTypeId", "ACTUAL", "transactionDate", now, "userLogin", userLogin));
        if (ServiceUtil.isError(createAcctgTransResults)) {
            Debug.logWarning("createAcctgTrans [" + "] was unsuccessful.", module);
            return 0;
        }
        Debug.logWarning("createAcctgTrans [" + "] was successful.", module);

        String acctgTransId = (String) createAcctgTransResults.get("acctgTransId");

        int imported = 0;
        //      for (GenericValue productInventory : productInventoryList) {

        try {
            List toStore = decodeInventory(productInventory, acctgTransId, now, delegator);
            if (toStore == null) {
            }

            TransactionUtil.begin();

            delegator.storeAll(toStore);
            Debug.logWarning("Import of product inventory [" + "] was successful.", module);
            TransactionUtil.commit();

        } catch (GenericEntityException e) {
            TransactionUtil.rollback();
            Debug.logError(e, "Failed to import product inventory [" + "]. Error stack follows.", module);
            // store the import error
//                String message = "Failed to import product inventory [" + productInventory.get("itemId") + "]. Error message : " + e.getMessage();
            //              storeImportInventoryError(productInventory, message, delegator);
        } catch (Exception e) {
            TransactionUtil.rollback();
            Debug.logWarning(e, "Import of product inventory [" + "] was unsuccessful.", module);
            // store the import error
//                String message = "Failed to import product inventory [" + productInventory.get("itemId") + "]. Error message : " + e.getMessage();
//                storeImportInventoryError(productInventory, message, delegator);
        }

//        }
        //Debug.logInfo("Could not find product [importProductInventory] 8", module);
        return imported;

    }

    public static GenericValue getInventory(String productId, String facilityId, Delegator delegator) {

        GenericValue tmpInventoryGV = null;
        GenericValue inventoryGV = null;
        /*
         try {
         tmpInventoryGV = delegator.findOne("InventoryItem", true, UtilMisc
         .toMap("productId", productId, "facilityId", facilityId));
            
         if (tmpInventoryGV != null
         && productId.equals(tmpInventoryGV.getString("productId")) && facilityId.equals(tmpInventoryGV.getString("facilityId")) )
         inventoryGV = tmpInventoryGV;
         } catch (GenericEntityException e) {
         Debug.logError("Problem in reading data of inventory Item", module);
         }
         */
        try {
            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("facilityId", EntityOperator.EQUALS, facilityId));
            List<GenericValue> invList = delegator.findList("InventoryItem", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (invList != null) {
//        	invList = EntityUtil.filterByDate(invList);
                Iterator<GenericValue> it = invList.iterator();
                while (it.hasNext()) {
                    tmpInventoryGV = it.next();
                    break;
                }

                if (tmpInventoryGV != null
                        && productId.equals(tmpInventoryGV.getString("productId")) && facilityId.equals(tmpInventoryGV.getString("facilityId"))) {
                    inventoryGV = tmpInventoryGV;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return inventoryGV;
    }

    public static GenericValue getProductPrice(String productId, String productPriceTypeId, String productPricePurposeId,
            String productStoreGroupId, Delegator delegator) {
        GenericValue tmpPriceGV = null;
        GenericValue priceGV = null;

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, productPriceTypeId));
            exprs.add(EntityCondition.makeCondition("productPricePurposeId", EntityOperator.EQUALS, productPricePurposeId));
            exprs.add(EntityCondition.makeCondition("productStoreGroupId", EntityOperator.EQUALS, productStoreGroupId));

            List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (priceList != null) {
                priceList = EntityUtil.filterByDate(priceList);
                Iterator<GenericValue> it = priceList.iterator();
                while (it.hasNext()) {
                    priceGV = it.next();
                    break;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return priceGV;
    }

    public static boolean isProductExists(String productId, Delegator delegator) {

        // check productId if null then skip creating inventory item
        // too.
        return ImportProductHelper.checkProductExists(productId, delegator);
    }

    public static List<GenericValue> createProductPrice(String productId, String currencyUomId,
            Timestamp now, BigDecimal price, BigDecimal averageCostPrice, BigDecimal listPriceVal, Delegator delegator) {

        List<GenericValue> toStore = FastList.newInstance();

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productId", productId);
        input.put("productPriceTypeId", "DEFAULT_PRICE");
        input.put("productPricePurposeId", "PURCHASE");
        input.put("productStoreGroupId", "_NA_");
        if (UtilValidate.isNotEmpty(currencyUomId)) {
            input.put("currencyUomId", currencyUomId);
        } else {
            Debug.logWarning("Product [" + productId + "] did not have a price currency, setting to default of [" + UtilProperties.getPropertyValue("general", "currency.uom.id.default") + "]", module);
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
        }
        input.put("fromDate", now);
        input.put("price", price);
        input.put("createdDate", now);
        GenericValue productPrice = delegator.makeValue("ProductPrice", input);
        toStore.add(productPrice);

        // make a list price as well, so that price rules and other features can work
        GenericValue listPrice = delegator.makeValue("ProductPrice", productPrice.getAllFields());
        listPrice.put("price", listPriceVal);
        listPrice.put("productPriceTypeId", "LIST_PRICE");
        toStore.add(listPrice);

        // make a list price as well, so that price rules and other features can work
        GenericValue avgPrice = delegator.makeValue("ProductPrice", productPrice.getAllFields());
        avgPrice.put("productPriceTypeId", "AVERAGE_COST");
        avgPrice.put("price", averageCostPrice);

        toStore.add(avgPrice);

        return toStore;
    }

    public static GenericValue createProductPrice(
            String productId, String productPriceTypeId, String productPricePurposeId,
            String productStoreGroupId, String currencyUomId,
            Timestamp now, BigDecimal price, Delegator delegator) {

        Map<String, Object> input = FastMap.newInstance();
        input.put("productId", productId);
        input.put("productPriceTypeId", productPriceTypeId);
        input.put("productPricePurposeId", productPricePurposeId);
        input.put("productStoreGroupId", productStoreGroupId);
        if (UtilValidate.isNotEmpty(currencyUomId)) {
            input.put("currencyUomId", currencyUomId);
        } else {
            Debug.logWarning("Product [" + productId + "] did not have a price currency, setting to default of [" + UtilProperties.getPropertyValue("general", "currency.uom.id.default") + "]", module);
            input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
        }
        input.put("fromDate", now);

        GenericValue value = getGenericValueByKey("ProductPrice", input, delegator);
        if (value == null) {
            input.put("price", price);
            input.put("createdDate", now);
            value = delegator.makeValue("ProductPrice", input);
        }
        return value;
    }

    public static void closeProductPrice(String productId, String productPriceTypeId, String productPricePurposeId,
            String productStoreGroupId, Timestamp now, Delegator delegator) {

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, productPriceTypeId));
            exprs.add(EntityCondition.makeCondition("productPricePurposeId", EntityOperator.EQUALS, productPricePurposeId));
            exprs.add(EntityCondition.makeCondition("productStoreGroupId", EntityOperator.EQUALS, productStoreGroupId));

            List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (priceList != null) {
                priceList = EntityUtil.filterByDate(priceList);
                Iterator<GenericValue> it = priceList.iterator();
                while (it.hasNext()) {
                    GenericValue tmpPriceGV = it.next();
                    tmpPriceGV.set("thruDate", now);

                }
                delegator.storeAll(priceList);
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
    }

    public static GenericValue getProductCategoryRollup(String productCategoryId, String parentProductCategoryId, Delegator delegator) {
        GenericValue tmpVal = null;

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productCategoryId", EntityOperator.EQUALS, productCategoryId));
            exprs.add(EntityCondition.makeCondition("parentProductCategoryId", EntityOperator.EQUALS, parentProductCategoryId));

            List<GenericValue> valueList = delegator.findList("ProductCategoryRollup", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                valueList = EntityUtil.filterByDate(valueList);
                Iterator<GenericValue> it = valueList.iterator();
                while (it.hasNext()) {
                    tmpVal = it.next();
                    break;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of ProductCategoryRollup " + e, module);
        }

        return tmpVal;
    }

    public static GenericValue createProductCategoryRollup(String productCategoryId, String parentProductCategoryId, Delegator delegator) {

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productCategoryId", productCategoryId);
        input.put("parentProductCategoryId", parentProductCategoryId);
        input.put("fromDate", UtilDateTime.nowTimestamp());
        GenericValue productCategoryRollup = delegator.makeValue("ProductCategoryRollup", input);
        return productCategoryRollup;
    }

    public static GenericValue getProductCategory(String productCategoryId, Delegator delegator) {
        GenericValue tmpVal = null;

        try {
            tmpVal = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of ProductCategory " + e, module);
        }

        return tmpVal;
    }

    public static GenericValue createProductCategory(String productCategoryId, String primaryParentCategoryId,
            String productCategoryTypeId, String categoryName, String image1, String image2, String image3, Delegator delegator) {

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productCategoryId", productCategoryId);
        input.put("primaryParentCategoryId", primaryParentCategoryId);
        input.put("productCategoryTypeId", productCategoryTypeId);
        input.put("categoryName", categoryName);
        input.put("categoryImageUrl", image1);
        input.put("linkOneImageUrl", image2);
        input.put("linkTwoImageUrl", image3);

        GenericValue productCategory = delegator.makeValue("ProductCategory", input);
        return productCategory;
    }

    public static GenericValue getProductCategoryMember(String productId, String productCategoryId, Delegator delegator) {
        GenericValue tmpVal = null;

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("productCategoryId", EntityOperator.EQUALS, productCategoryId));

            List<GenericValue> valueList = delegator.findList("ProductCategoryMember", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                valueList = EntityUtil.filterByDate(valueList);
                Iterator<GenericValue> it = valueList.iterator();
                while (it.hasNext()) {
                    tmpVal = it.next();
                    break;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of ProductCategoryMember " + e, module);
        }

        return tmpVal;
    }

    public static GenericValue createProductCategoryMember(String productId, String productCategoryId, Delegator delegator) {

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productId", productId);
        input.put("productCategoryId", productCategoryId);
        input.put("fromDate", UtilDateTime.nowTimestamp());
        GenericValue productCategoryMember = delegator.makeValue("ProductCategoryMember", input);
        return productCategoryMember;
    }

    public static GenericValue createProductContent(String productId, String contentId, Delegator delegator) {

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productId", productId);
        input.put("contentId", contentId);
        input.put("productContentTypeId", "ADDTOCART_IMAGE");
        input.put("fromDate", UtilDateTime.nowTimestamp());
        GenericValue ProductContent = delegator.makeValue("ProductContent", input);
        return ProductContent;
    }

    public static GenericValue createProductDimension(String productId, String dimensionId, String internalName, Delegator delegator) {

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("productId", productId);
        input.put("dimensionId", dimensionId);
        input.put("internalName", internalName);
        input.put("productType", "Finished Good");

        GenericValue productDimension = delegator.makeValue("ProductDimension", input);
        return productDimension;
    }

    public static GenericValue getProductAverageCost(String productId, String organizationPartyId, String productAverageCostTypeId, String facilityId, Delegator delegator) {
        GenericValue tmpPriceGV = null;
        GenericValue priceGV = null;

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("organizationPartyId", EntityOperator.EQUALS, organizationPartyId));
            exprs.add(EntityCondition.makeCondition("productAverageCostTypeId", EntityOperator.EQUALS, productAverageCostTypeId));
            exprs.add(EntityCondition.makeCondition("facilityId", EntityOperator.EQUALS, facilityId));

            List<GenericValue> priceList = delegator.findList("ProductAverageCost", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (priceList != null) {
                priceList = EntityUtil.filterByDate(priceList);
                Iterator<GenericValue> it = priceList.iterator();
                while (it.hasNext()) {
                    tmpPriceGV = it.next();
                    break;
                }

                if (tmpPriceGV != null) {
                    priceGV = tmpPriceGV;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return priceGV;
    }

    public static List<GenericValue> getProductAverageCostList(String productId, String organizationPartyId, String facilityId, Delegator delegator) {
        List<GenericValue> priceList = FastList.newInstance();
        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("organizationPartyId", EntityOperator.EQUALS, organizationPartyId));
//            exprs.add(EntityCondition.makeCondition("productAverageCostTypeId", EntityOperator.EQUALS, productAverageCostTypeId));
            exprs.add(EntityCondition.makeCondition("facilityId", EntityOperator.EQUALS, facilityId));

            priceList = delegator.findList("ProductAverageCost", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (priceList != null) {
                priceList = EntityUtil.filterByDate(priceList);
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return priceList;
    }

    public static List<GenericValue> createProductAverageCost(String productId,
            Timestamp now, BigDecimal averageCost, String organizationPartyId, String productAverageCostTypeId, String facilityId, Delegator delegator) {

        List<GenericValue> toStore = FastList.newInstance();

        // product price
        Map<String, Object> input = FastMap.newInstance();
        input.put("organizationPartyId", organizationPartyId);
        input.put("fromDate", now);
        input.put("averageCost", averageCost);
        input.put("productId", productId);
        input.put("productAverageCostTypeId", productAverageCostTypeId);
        input.put("facilityId", facilityId);

        GenericValue productAverageCost = delegator.makeValue("ProductAverageCost", input);

        toStore.add(productAverageCost);

        return toStore;
    }

    /**
     * Helper method to decode a DataImportInventory into a List of
     * GenericValues modeling that product in the OFBiz schema. If for some
     * reason obtaining data via the delegator fails, this service throws that
     * exception. Note that everything is done with the delegator for maximum
     * efficiency.
     *
     * @param productInventory a <code>GenericValue</code> value
     * @param organizationPartyId a <code>String</code> value
     * @param facilityId a <code>String</code> value
     * @param inventoryGlAccountId a <code>String</code> value
     * @param offsettingGlAccountId a <code>String</code> value
     * @param acctgTransId a <code>String</code> value
     * @param currencyUomId a <code>String</code> value
     * @param now a <code>Timestamp</code> value
     * @param delegator a <code>Delegator</code> value
     * @return a <code>List</code> value
     * @exception GenericEntityException if an error occurs
     * @exception Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    public static List<GenericValue> createInventory(String productId, String ownerPartyId, String facilityId, String inventoryItemTypeId,
            String currencyUomId, String inventoryGlAccountId,
            String offsettingGlAccountId, BigDecimal quantityOnHand, BigDecimal avaToPromise, BigDecimal invValue, BigDecimal purchasePrice,
            BigDecimal minimumStock, BigDecimal reorderQuantity, Long daysToShip,
            Timestamp now, LocalDispatcher dispatcher, Delegator delegator, GenericValue userLogin) throws GenericEntityException, Exception {

        List toStore = FastList.newInstance();

        GenericValue facility = delegator.findByPrimaryKeyCache("Facility", UtilMisc.toMap("facilityId", facilityId));

        if (UtilValidate.isEmpty(facility)) {
            String errMsg = "Error in importProductInventory service: facility [" + facilityId + "] does not exist";
            Debug.logError(errMsg, module);
            return toStore;
        }

        // Make sure the organizationParty exists
        GenericValue organizationParty = facility.getRelatedOneCache("OwnerParty");
        if (UtilValidate.isEmpty(organizationParty)) {
            String errMsg = "Error in importProductInventory service: owner party for facility [" + facilityId + "] does not exist";
            Debug.logError(errMsg, module);
            return toStore;
        }
        String organizationPartyId = organizationParty.getString("partyId");

//        //Debug.logInfo("Could not find product [importProductInventory] 5", module);
        // Make sure the supplied GL accounts exist for the organization
        GenericValue glAccountOrganization = null;
        if (UtilValidate.isNotEmpty(inventoryGlAccountId)) {
            glAccountOrganization = delegator.findByPrimaryKeyCache("GlAccountOrganization", UtilMisc.toMap("glAccountId", inventoryGlAccountId, "organizationPartyId", organizationPartyId));
            if (glAccountOrganization == null) {
                Debug.logError("Cannot import inventory: organization [" + organizationPartyId + "] does not have inventory General Ledger account [" + inventoryGlAccountId + "] defined in GlAccountOrganization.", module);
                return toStore;
            }
        }
        if (UtilValidate.isNotEmpty(offsettingGlAccountId)) {
            glAccountOrganization = delegator.findByPrimaryKeyCache("GlAccountOrganization", UtilMisc.toMap("glAccountId", offsettingGlAccountId, "organizationPartyId", organizationPartyId));
            if (glAccountOrganization == null) {
                Debug.logError("Cannot import inventory: organization [" + organizationPartyId + "] does not have offsetting General Ledger account [" + offsettingGlAccountId + "] defined in GlAccountOrganization.", module);
                return toStore;
            }
        }

        /*        String currencyUomId = UtilCommon.getOrgBaseCurrency(organizationPartyId, delegator);
         if (UtilValidate.isEmpty(currencyUomId)) {
         String errMsg = "Error in importProductInventory service: organization [" + organizationPartyId + "] does not have a baseCurrencyUomId defined in PartyAcctgPref";
         Debug.logError(errMsg, module);
         return 0;
         }
         */
        // All transaction entries have same timestamp
        //      //Debug.logInfo("Could not find product [importProductInventory] 6", module);
        // Create the header of an AcctgTrans record for all the subsequent transaction entries
        Map createAcctgTransResults = dispatcher.runSync("createAcctgTrans", UtilMisc.toMap("acctgTransTypeId", "INVENTORY", "glFiscalTypeId", "ACTUAL", "transactionDate", now, "userLogin", userLogin));
        if (ServiceUtil.isError(createAcctgTransResults)) {
            return toStore;
        }
        String acctgTransId = (String) createAcctgTransResults.get("acctgTransId");

        //Debug.logInfo("Now processing  data [" + productId + "]", module);
        BigDecimal onHand = quantityOnHand;
        if (UtilValidate.isEmpty(onHand)) {
            onHand = new BigDecimal(0);
        }

        BigDecimal availableToPromise = avaToPromise;
        if (UtilValidate.isEmpty(availableToPromise)) {
            availableToPromise = new BigDecimal(quantityOnHand.intValue());
        }

        BigDecimal inventoryValue = invValue;
        if (UtilValidate.isEmpty(inventoryValue)) {
            inventoryValue = new BigDecimal(quantityOnHand.intValue() * purchasePrice.doubleValue());
        }

        BigDecimal averageCost = ZERO;
        if (onHand.doubleValue() > 0.0) {
            averageCost = new BigDecimal(inventoryValue.doubleValue() / onHand.doubleValue()).setScale(DECIMALS, ROUNDING);
        }

        // Verify that productId exists
        GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
        if (product == null) {
            //Debug.logInfo("Could not find product [" + productId + "], not importing.", module);
            return toStore;
        }

//        String inventoryItemId = delegator.getNextSeqId("InventoryItem");
        java.sql.Timestamp expireDate = UtilDateTime.nowTimestamp();
        expireDate.setYear(2015);

        // Create the inventory item
        String inventoryItemId = delegator.getNextSeqId("InventoryItem");
        FastMap<String, Object> input = FastMap.newInstance();
        input.put("inventoryItemId", inventoryItemId);
        input.put("unitCost", averageCost);
        input.put("productId", productId);
        input.put("ownerPartyId", ownerPartyId);
        input.put("datetimeReceived", now);
        input.put("facilityId", facilityId);
        input.put("comments", "Auto-generated from Product Inventory Import.");
        input.put("inventoryItemTypeId", inventoryItemTypeId);
        input.put("currencyUomId", currencyUomId);
        input.put("expireDate", expireDate);
        input.put("quantityOnHandTotal", quantityOnHand);
        input.put("availableToPromiseTotal", quantityOnHand);
        input.put("accountingQuantityTotal", quantityOnHand);
        input.put("availableToPromise", quantityOnHand);
        input.put("quantityOnHand", quantityOnHand);

        GenericValue inventoryItem = delegator.makeValue("InventoryItem", input);
        toStore.add(inventoryItem);
        /*
         // Create the inventory item detail
         input = FastMap.newInstance();
         input.put("inventoryItemId", inventoryItemId);
         input.put("inventoryItemDetailSeqId", UtilFormatOut.formatPaddedNumber(1, 4));
         input.put("quantityOnHandDiff", onHand);
         input.put("unitCost", averageCost);
        
         if (UtilValidate.isNotEmpty(availableToPromise)) {
         input.put("availableToPromiseDiff", availableToPromise);
         } else {
         input.put("availableToPromiseDiff", onHand);
         }
         GenericValue inventoryItemDetail = delegator.makeValue("InventoryItemDetail", input);
         toStore.add(inventoryItemDetail);

         // Create the product average cost
         input = FastMap.newInstance();
         input.put("organizationPartyId",  organizationPartyId);
         input.put("fromDate", now);
         input.put("averageCost", averageCost);
         input.put("productId", productId);
         input.put("productAverageCostTypeId", "SIMPLE_AVG_COST");
         input.put("facilityId", facilityId);        
         GenericValue productAverageCost = delegator.makeValue("ProductAverageCost", input);
         toStore.add(productAverageCost);

         // Create the two AcctgTransEntries for this item
         input = FastMap.newInstance();
         input.put("acctgTransId", acctgTransId);
         input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
         input.put("acctgTransEntryTypeId", "_NA_");
         input.put("productId", productId);
         input.put("organizationPartyId", organizationPartyId);
         input.put("currencyUomId", currencyUomId);
         input.put("reconcileStatusId", "AES_NOT_RECONCILED");
         input.put("glAccountId", inventoryGlAccountId);
         input.put("debitCreditFlag", "D");
         input.put("amount", inventoryValue);
         GenericValue debitAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
         toStore.add(debitAcctgTransEntry);
         acctgTransSeqNum++;

         input.put("acctgTransEntrySeqId", UtilFormatOut.formatPaddedNumber(acctgTransSeqNum, 5));
         input.put("glAccountId", offsettingGlAccountId);
         input.put("debitCreditFlag", "C");
         GenericValue creditAcctgTransEntry = delegator.makeValue("AcctgTransEntry", input);
         toStore.add(creditAcctgTransEntry);
         acctgTransSeqNum++;

         // Create the ProductFacility record which is now required for viewing inventory reports
         input = FastMap.newInstance();
         input.put("facilityId", facilityId);
         input.put("productId", productId);
         if (UtilValidate.isEmpty(minimumStock)) {
         input.put("minimumStock", new Double(0.0));
         } else {
         input.put("minimumStock", minimumStock);
         }
         input.put("reorderQuantity", reorderQuantity);
         input.put("daysToShip", daysToShip);
         toStore.add(delegator.makeValue("ProductFacility", input));
         */
        return toStore;
    }

    /*    public void(){
     // insert a purchase record in SupplierProduct
     if (UtilValidate.isNotEmpty(supplierPartyId )) {
     String supplierPartyId = data.supplierPartyId;
     input = FastMap.newInstance();
     input.put("productId", product.get("productId") );
     input.put("currencyUomId", UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
     // try to find the supplier
     GenericValue supplier = delegator.findByPrimaryKeyCache("Party", UtilMisc.toMap("partyId", supplierPartyId));
     if (UtilValidate.isEmpty(supplier)) {
     //Debug.logInfo("Supplier with ID [" + supplierPartyId + "] not found, will be creating it", module);

     TransactionUtil.begin();
     delegator.create("Party", UtilMisc.toMap("partyId", supplierPartyId, "partyTypeId", "PARTY_GROUP"));
     delegator.create("PartyGroup", UtilMisc.toMap("partyId", supplierPartyId, "groupName", supplierPartyId));
     delegator.create("PartyRole", UtilMisc.toMap("partyId", supplierPartyId, "roleTypeId", "SUPPLIER"));
     TransactionUtil.commit();
     }
     input.put("partyId", supplierPartyId);
     input.put("availableFromDate", now);
     input.put("minimumOrderQuantity", data.minimumOrderQuantity);
     input.put("supplierPrefOrderId", "10_MAIN_SUPPL");
     input.put("lastPrice", data.purchasePrice );
     input.put("supplierProductId", data.supplierProductId );   // vendor part number -- default to our productID for now
     GenericValue supplierProduct = delegator.makeValue("SupplierProduct", input);
     toStore.add(supplierProduct);
     }

     }
     */
    public static GenericValue getSupplierProduct(String productId, String partyId, Delegator delegator) {

        GenericValue tmpSupplierProductGV = null;
        GenericValue supplierProductGV = null;

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
            //order by createdStamp
            orderBy.add("createdStamp DESC");

            List<GenericValue> invList = delegator.findList("SupplierProduct", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, null, false);
            if (invList != null) {
//        	invList = EntityUtil.filterByDate(invList);
                Iterator<GenericValue> it = invList.iterator();
                while (it.hasNext()) {
                    tmpSupplierProductGV = it.next();
                    break;
                }

                if (tmpSupplierProductGV != null
                        && productId.equals(tmpSupplierProductGV.getString("productId")) && partyId.equals(tmpSupplierProductGV.getString("partyId"))) {
                    supplierProductGV = tmpSupplierProductGV;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return supplierProductGV;
    }

    public static GenericValue getProductFromSupplierId(String supplierProductId, String partyId, Delegator delegator) {

        GenericValue tmpSupplierProductGV = null;
        GenericValue supplierProductGV = null;

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("supplierProductId", EntityOperator.EQUALS, supplierProductId));
            exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
            //order by product name
            orderBy.add("createdStamp DESC");

            List<GenericValue> invList = delegator.findList("SupplierProduct", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, null, false);
            if (invList != null) {
//        	invList = EntityUtil.filterByDate(invList);
                Iterator<GenericValue> it = invList.iterator();
                while (it.hasNext()) {
                    tmpSupplierProductGV = it.next();
                    break;
                }

                if (tmpSupplierProductGV != null
                        && supplierProductId.equals(tmpSupplierProductGV.getString("supplierProductId")) && partyId.equals(tmpSupplierProductGV.getString("partyId"))) {
                    supplierProductGV = tmpSupplierProductGV;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return supplierProductGV;
    }

    public static boolean updateSupplierProduct(GenericValue supplierVal, String supplierId, String basePrice, String supplierProductId, Delegator delegator) {

        boolean result = false;

        try {
            supplierVal.set("partyId", supplierId);
            supplierVal.set("lastPrice", new BigDecimal(basePrice));
            supplierVal.set("minimumOrderQuantity", new BigDecimal(1));
            supplierVal.set("supplierProductId", supplierProductId);   // vendor part number -- default to our productID for now                   	
            supplierVal.set("availableFromDate", UtilDateTime.nowTimestamp());
            delegator.store(supplierVal);
            result = true;
        } catch (GenericEntityException e) {
            Debug.logError(e, module);

        }

        return result;
    }

    public static boolean createSupplierProduct(String productId, String supplierPartyId, String basePrice, String supplierProductId, String currencyUomId, Delegator delegator) {

        boolean result = false;

        try {
            if (basePrice.isEmpty() == false) {
                GenericValue supplierVal = delegator.makeValue("SupplierProduct");
                supplierVal.set("productId", productId);
                supplierVal.set("partyId", supplierPartyId);
                supplierVal.set("lastPrice", new BigDecimal(basePrice));
                supplierVal.set("supplierProductId", supplierProductId);   // vendor part number -- default to our productID for now    		   				
                supplierVal.set("minimumOrderQuantity", new BigDecimal(1));
                supplierVal.set("availableFromDate", UtilDateTime.nowTimestamp());
                supplierVal.set("currencyUomId", currencyUomId);

                delegator.create(supplierVal);
            } else {
                Debug.logError("basePrice price is empty: " + basePrice, module);
            }
            result = true;

        } catch (GenericEntityException e) {
            Debug.logError(e, module);

        }

        return result;
    }

    public static GenericValue getGoodIdentification(String productId, String goodIdentificationTypeId, Delegator delegator) {
        GenericValue inventoryGV = null;

        try {
            inventoryGV = delegator.findOne("GoodIdentification", false, UtilMisc
                    .toMap("productId", productId, "goodIdentificationTypeId", goodIdentificationTypeId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of getGoodIdentificationType", module);
        }

        return inventoryGV;
    }

    public static List<GenericValue> getProductByGoodIdentificationId(String idValue, String goodIdentificationTypeId, Delegator delegator) {
        List<GenericValue> inventoryGV = null;

        try {
            inventoryGV = delegator.findByAnd("GoodIdentification", UtilMisc.toMap("idValue", idValue, "goodIdentificationTypeId", goodIdentificationTypeId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of getGoodIdentificationType", module);
        }

        return inventoryGV;
    }

    public static GenericValue getProductFromScanCode(String scanCode, String goodIdentificationTypeId, Delegator delegator) {

        GenericValue tmpInventoryGV = null;
        GenericValue inventoryGV = null;

        try {
            List<String> orderBy = FastList.newInstance();
            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("idValue", EntityOperator.EQUALS, scanCode));
            exprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, goodIdentificationTypeId));

            //order by product name
            orderBy.add("createdStamp DESC");

            List<GenericValue> invList = delegator.findList("GoodIdentification", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, null, false);
            if (invList != null) {
                //            	invList = EntityUtil.filterByDate(invList);
                Iterator<GenericValue> it = invList.iterator();
                while (it.hasNext()) {
                    tmpInventoryGV = it.next();
                    break;
                }

                if (tmpInventoryGV != null
                        && scanCode.equals(tmpInventoryGV.getString("idValue")) && goodIdentificationTypeId.equals(tmpInventoryGV.getString("goodIdentificationTypeId"))) {
                    inventoryGV = tmpInventoryGV;
                }

            }
        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return inventoryGV;
    }

    public static List<GenericValue> getProductsFromIdentificationType(String scanCode, String goodIdentificationTypeId, Delegator delegator) {

        List<GenericValue> invList = null;
        try {
            List<String> orderBy = FastList.newInstance();
            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("idValue", EntityOperator.EQUALS, scanCode));
            exprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, goodIdentificationTypeId));

            //order by product name
            orderBy.add("createdStamp DESC");

            invList = delegator.findList("GoodIdentification", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, null, false);
            if (invList == null) {
                invList = new ArrayList<GenericValue>();
            }
        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return invList;
    }

    public static boolean isDuplicateGoodIdentification(String productId, String goodIdentification, String goodIdentificationTypeId, Delegator delegator) {
        List<GenericValue> invList = getProductsFromIdentificationType(goodIdentification, goodIdentificationTypeId, delegator);
        if (invList != null && invList.size() > 0) {
            for (GenericValue gv : invList) {
                if (productId.equals(gv.getString("productId")) == false) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean updateGoodIdentificationType(GenericValue giftEAN, String scanCode, Delegator delegator) {

        try {

            giftEAN.set("idValue", scanCode);

            delegator.store(giftEAN);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return false;
        }

        return true;
    }

    public static boolean removeGoodIdentificationType(GenericValue giftEAN, Delegator delegator) {

        try {
            delegator.removeValue(giftEAN);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return false;
        }

        return true;
    }

    public static boolean createGoodIdentificationType(String productId, String goodIdentificationTypeId, String scanCode, Delegator delegator) {
        boolean result = false;
        try {
            Map<String, Object> input = FastMap.newInstance();
            input.put("goodIdentificationTypeId", goodIdentificationTypeId);
            input.put("productId", productId);
            input.put("idValue", scanCode);
            GenericValue goodIdentification = delegator.makeValue("GoodIdentification", input);
            delegator.create(goodIdentification);
            result = true;

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        return result;
    }

    static public boolean createAcctgTrans(String acctgTransId, String acctgTransTypeId, String glFiscalTypeId, Timestamp transactionDate, GenericValue userLogin, Delegator delegator) {
        boolean result = false;
        try {
            Map<String, Object> input = FastMap.newInstance();

            input.put("acctgTransId", acctgTransId);
            input.put("acctgTransTypeId", acctgTransTypeId);
            input.put("glFiscalTypeId", glFiscalTypeId);
            input.put("transactionDate", transactionDate);
            input.put("lastModifiedByUserLogin", userLogin.get("userLoginId"));
            input.put("createdByUserLogin", userLogin.get("userLoginId"));

            GenericValue acctgTrans = delegator.makeValue("AcctgTrans", input);
            delegator.create(acctgTrans);
            result = true;

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        return result;
    }

    // check if product already exists in database
    public static GenericValue getProduct(String productId, Delegator delegator) {

        GenericValue tmpProductGV = null;

        try {
            tmpProductGV = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return tmpProductGV;
    }

    public static GenericValue getFacility(String facilityId,
            Delegator delegator) {

        GenericValue tmpProductGV = null;

        try {
            tmpProductGV = delegator.findByPrimaryKey("Facility", UtilMisc
                    .toMap("facilityId", facilityId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of Facility", module);
        }

        return tmpProductGV;
    }
    // check if product already exists in database

    public static GenericValue getProductGroup(String partyId,
            Delegator delegator) {

        GenericValue tmpPartyGroup = null;

        try {
            tmpPartyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc
                    .toMap("partyId", partyId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product group", module);
        }

        return tmpPartyGroup;
    }

    static public List<String> getProductPriceTypeIds() {
        List<String> list = FastList.newInstance();
        list.add(ProductPriceTypeId_LISTPRICE);
        list.add(ProductPriceTypeId_DEFAULTPRICE);
        return list;
    }

    static public Map<String, String> getSuppliers(Delegator delegator) {

        List<GenericValue> partyList = null;
        TreeMap<String, String> resultList = null;

        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("PT", "Party");
        dynamicView.addAlias("PT", "partyId");
        dynamicView.addAlias("PT", "statusId");
        dynamicView.addAlias("PT", "partyTypeId");

        dynamicView.addMemberEntity("PR", "PartyRole");
        dynamicView.addAlias("PR", "roleTypeId");
        dynamicView.addViewLink("PT", "PR", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("partyTypeId");
        fieldsToSelect.add("roleTypeId");

        // NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
        // This allows to get all clients when any informations has been entered
        andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PARTY_DISABLED")));
        andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "SUPPLIER")); // Only persons for now...

        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        orderBy.add("partyId");

        //Debug.logInfo("In searchSupplierParty mainCond=" + mainCond, module);
        //thoushand suppliers enough for me
        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        resultList = new TreeMap<String, String>();//FastMap.newInstance();
        if (partyList != null) {

            for (GenericValue party : partyList) {
                /*                    Map<String, String> partyMap = FastMap.newInstance();
                 partyMap.put("partyId", party.getString("partyId"));
                 partyMap.put("roleTypeId", party.getString("roleTypeId"));                    
                 */

                GenericValue partyVal = null;
                String partyTypeId = party.getString("partyTypeId");
                String supplierName = null;
                if ("PERSON".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("lastName") != null) {
                                supplierName = partyVal.getString("lastName");
                            }

                            if (partyVal.getString("firstName") != null) {
                                supplierName = partyVal.getString("lastName") + " " + partyVal.getString("firstName");
                            }
                        }
                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if ("PARTY_GROUP".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("groupName") != null) {
                                supplierName = partyVal.getString("groupName");
                            }
                        }

                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }

//                //Debug.logInfo("partyid: " + party.getString("partyId") + " name " + supplierName, module);
                if (partyVal != null) {
                    resultList.put(party.getString("partyId"), supplierName);
                }
            }
        }
        return resultList;
    }

    static public List<GenericValue> getSupplierList(Delegator delegator) {

        List<GenericValue> partyList = null;
        List<GenericValue> resultList = FastList.newInstance();

        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("PT", "Party");
        dynamicView.addAlias("PT", "partyId");
        dynamicView.addAlias("PT", "description");
        dynamicView.addAlias("PT", "statusId");
        dynamicView.addAlias("PT", "partyTypeId");

        dynamicView.addMemberEntity("PR", "PartyRole");
        dynamicView.addAlias("PR", "roleTypeId");
        dynamicView.addViewLink("PT", "PR", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("partyTypeId");
        fieldsToSelect.add("roleTypeId");

        // NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
        // This allows to get all clients when any informations has been entered
        andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PARTY_DISABLED")));
        andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "SUPPLIER")); // Only persons for now...

        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        orderBy.add("partyId");

        //Debug.logInfo("In searchSupplierParty mainCond=" + mainCond, module);
        //thoushand suppliers enough for me
        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (partyList != null) {

            for (GenericValue party : partyList) {
                /*                    Map<String, String> partyMap = FastMap.newInstance();
                 partyMap.put("partyId", party.getString("partyId"));
                 partyMap.put("roleTypeId", party.getString("roleTypeId"));                    
                 */

                GenericValue partyVal = null;
                String partyTypeId = party.getString("partyTypeId");
                String supplierName = null;
                if ("PERSON".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("lastName") != null) {
                                supplierName = partyVal.getString("lastName");
                            }

                            if (partyVal.getString("firstName") != null) {
                                supplierName = partyVal.getString("lastName") + " " + partyVal.getString("firstName");
                            }
                        }
                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if ("PARTY_GROUP".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("groupName") != null) {
                                supplierName = partyVal.getString("groupName");
                            }
                        }

                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }

//                //Debug.logInfo("partyid: " + party.getString("partyId") + " name " + supplierName, module);
                if (partyVal != null) {
                    party.setString("description", supplierName);
                    resultList.add(party);
                }
            }
        }
        return resultList;
    }

    static public List<GenericValue> getFacilityLists(Delegator delegator) {
        List<GenericValue> shoppingLists = null;

        try {
            shoppingLists = delegator.findList("Facility", null, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return null;
        }

        return shoppingLists;
    }

    static public List<GenericValue> getInvoiceLists(Delegator delegator) {
        List<GenericValue> shoppingLists = null;

        try {
            shoppingLists = delegator.findList("Invoice", null, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return null;
        }

        return shoppingLists;
    }

    // check if party already exists in database
    public static GenericValue getGenericValueByKey(String objetName, Map valueList, Delegator delegator) {

        GenericValue tmpVal = null;

        try {
            tmpVal = delegator.findByPrimaryKey(objetName, valueList/*UtilMisc.toMap("partyId", partyId)*/);

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data from party", module);
        }

        return tmpVal;
    }

    static public List<GenericValue> getGenericValueLists(String entity, Delegator delegator) {
        /*        List<GenericValue> shoppingLists = null;

         try {
         shoppingLists = delegator.findList(entity, null, null, null, null, false);
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }

         return shoppingLists;
         */
        return getGenericValueLists(entity, delegator, null);
    }

    static public List<GenericValue> getGenericValueLists(String entity, Delegator delegator, String sortBy) {
        List<GenericValue> shoppingLists = null;
        List<String> orderBy = FastList.newInstance();
        //order by createdStamp
        if (sortBy != null) {
            orderBy.add(sortBy);
        }
        try {
            shoppingLists = delegator.findList(entity, null, null, orderBy, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        return shoppingLists;
    }

    static public List<GenericValue> getGenericValueListsWithSelection(String entity, Map<String, Object> whereClauseMap, String sortBy, Delegator delegator) {
        List<GenericValue> shoppingLists = null;

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            Debug.logInfo("getGenericValueListsWithSelection cond size: " + whereClauseMap.size(), module);
            for (Map.Entry<String, Object> anEntry : whereClauseMap.entrySet()) {
                exprs.add(EntityCondition.makeCondition(anEntry.getKey(), EntityOperator.EQUALS, anEntry.getValue()));
                Debug.logInfo("anEntry.getKey(): " + anEntry.getKey() + " anEntry.getValue(): " + anEntry.getValue(), module);
            }
            //order by createdStamp
            if (sortBy != null) {
                orderBy.add(sortBy);
            }
            shoppingLists = delegator.findList(entity, EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, null, false);

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return shoppingLists;
    }

    static public List<GenericValue> getReadOnlyGenericValueListsWithSelection(String entity, Map<String, Object> whereClauseMap, String sortBy, Delegator delegator) {

        List<GenericValue> shoppingLists = null;
        Integer maxRows = new Integer(100000);
        EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            for (Map.Entry<String, Object> anEntry : whereClauseMap.entrySet()) {
                exprs.add(EntityCondition.makeCondition(anEntry.getKey(), EntityOperator.EQUALS, anEntry.getValue()));
                Debug.logInfo("anEntry.getKey(): " + anEntry.getKey() + " anEntry.getValue(): " + anEntry.getValue(), module);
            }
            //order by createdStamp
            if (sortBy != null) {
                orderBy.add(sortBy);
            }
            shoppingLists = delegator.findList(entity, EntityCondition.makeCondition(exprs, EntityOperator.AND), null, orderBy, findOpts, false);

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return shoppingLists;
    }

    static public List<GenericValue> getReadOnlyGenericValueListsWithSelection(String entity, List<EntityCondition> whereClauseMap, String sortBy, Delegator delegator, boolean distinct) {

        List<GenericValue> shoppingLists = null;
        Integer maxRows = new Integer(100000);
        EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
        findOpts.setDistinct(distinct);

        try {
//            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            /*for (Map.Entry<String, Object> anEntry : whereClauseMap.entrySet()) {
             exprs.add(EntityCondition.makeCondition(anEntry.getKey(), EntityOperator.EQUALS, anEntry.getValue()));
             //                //Debug.logInfo("anEntry.getKey(): " + anEntry.getKey() + " anEntry.getValue(): " + anEntry.getValue(), module);
             }*/
            for (EntityCondition anEntry : whereClauseMap) {
                Debug.logInfo("anEntry.getKey " + anEntry.toString(), "Hello");
            }
            //order by createdStamp
            if (sortBy != null) {
                orderBy.add(sortBy);
            }
            shoppingLists = delegator.findList(entity, EntityCondition.makeCondition(whereClauseMap, EntityOperator.AND), null, orderBy, findOpts, false);

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return shoppingLists;
    }

    static public List<GenericValue> getReadOnlyGenericValueListsWithSelection(String entity, List<EntityCondition> whereClauseMap, String sortBy, Delegator delegator) {
        return getReadOnlyGenericValueListsWithSelection(entity, whereClauseMap, sortBy, delegator, false);
    }

    static public List<GenericValue> getGenericValueLists(String entity, String whereCl, String whereVal, Delegator delegator) {
        List<GenericValue> shoppingLists = null;

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            //Debug.logInfo(" whereCl: " + whereCl + " whereVal: " + whereVal, module);
            if (whereCl != null && whereVal != null) {
                exprs.add(EntityCondition.makeCondition(whereCl, EntityOperator.EQUALS, whereVal));
            }

            shoppingLists = delegator.findList(entity, EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return shoppingLists;
    }

    public static void createPartyRoles(String ownerPartyId, String supplierPartyId,
            Delegator delegator) throws GenericEntityException {

        String roleTypeId = "SUPPLIER";
        GenericValue val = createPartyRole(supplierPartyId, roleTypeId,
                delegator);

        roleTypeId = "BILL_TO_CUSTOMER";
        val = createPartyRole(ownerPartyId, roleTypeId,
                delegator);

        roleTypeId = "BILL_FROM_VENDOR";
        val = createPartyRole(supplierPartyId, roleTypeId,
                delegator);

        roleTypeId = "SHIP_FROM_VENDOR";
        val = createPartyRole(supplierPartyId, roleTypeId, delegator);

        roleTypeId = "SUPPLIER_AGENT";
        val = createPartyRole(supplierPartyId, roleTypeId, delegator);

        // orderId="Demo1001" ;
    }

    static public void createOrderRoles(String ownerPartyId, String supplierPartyId,
            String orderId, Delegator delegator) throws GenericEntityException {

        String roleTypeId = "BILL_TO_CUSTOMER";

        GenericValue val = createOrderRole(orderId, ownerPartyId, roleTypeId,
                delegator);
        // orderId="Demo1001";

        roleTypeId = "BILL_FROM_VENDOR";
        val = createOrderRole(orderId, supplierPartyId, roleTypeId, delegator);
        // orderId="Demo1001";

        roleTypeId = "SHIP_FROM_VENDOR";
        val = createOrderRole(orderId, supplierPartyId, roleTypeId, delegator);
        // orderId="Demo1001";

        roleTypeId = "SUPPLIER";
        val = createOrderRole(orderId, supplierPartyId, roleTypeId, delegator);
    }

    public static void orderStatus(String orderId, Delegator delegator) throws GenericEntityException {

        String orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        String statusId = "ORDER_CREATED";
        Timestamp statusDatetime = UtilDateTime.nowTimestamp();
        String statusUserLogin = "admin";

        GenericValue val2 = createOrderStatus(orderStatusId, statusId, orderId,
                null, statusDatetime, statusUserLogin, delegator);

        orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        statusId = "ORDER_APPROVED";
        // orderId="Demo1001";
        statusDatetime = UtilDateTime.nowTimestamp();
        statusUserLogin = "admin";
        val2 = createOrderStatus(orderStatusId, statusId, orderId, null,
                statusDatetime, statusUserLogin, delegator);

        statusId = "ORDER_COMPLETED";
        orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        // orderId="Demo1001";
        statusDatetime = UtilDateTime.nowTimestamp();
        statusUserLogin = "admin";
        val2 = createOrderStatus(orderStatusId, statusId, orderId, null,
                statusDatetime, statusUserLogin, delegator);
    }

    static public void orderItemStatus(String orderId, String orderItemSeqId,
            Delegator delegator) throws GenericEntityException {

        String orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        String statusId = "ITEM_CREATED";
        Timestamp statusDatetime = UtilDateTime.nowTimestamp();
        String statusUserLogin = "admin";
        GenericValue val2 = createOrderStatus(orderStatusId, statusId, orderId,
                orderItemSeqId, statusDatetime, statusUserLogin, delegator);

        orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        statusId = "ITEM_APPROVED";
        statusDatetime = UtilDateTime.nowTimestamp();
        val2 = createOrderStatus(orderStatusId, statusId, orderId,
                orderItemSeqId, statusDatetime, statusUserLogin, delegator);

        orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        statusId = "ITEM_COMPLETED";
        statusDatetime = UtilDateTime.nowTimestamp();
        val2 = createOrderStatus(orderStatusId, statusId, orderId,
                orderItemSeqId, statusDatetime, statusUserLogin, delegator);

    }

    public static String createPurchaseOrder(String supplierPartyId, String ownerPartyId) {
        String orderId = null;
        return orderId;
    }

    public static String createPurchaseOrder(String supplierPartyId, String ownerPartyId,
            String currencyUomId, String origCurrencyUomId, String destinationFacilityId,
            List<ProductInventoryPojo> productList, String referenceNumber, Delegator delegator)
            throws GenericEntityException {

        String orderId = delegator.getNextSeqId("OrderHeader");
        String orderTypeId = "PURCHASE_ORDER";
        String orderName = referenceNumber;
        String salesChannelEnumId = "UNKNWN_SALES_CHANNEL";
        Timestamp orderDate = UtilDateTime.nowTimestamp();
        String priority = "2";
        Timestamp entryDate = UtilDateTime.nowTimestamp();
        String statusId = "ORDER_COMPLETED";

        String webSiteId = "OrderEntry";
        BigDecimal remainingSubTotal = new BigDecimal("48.00");
        BigDecimal grandTotal = new BigDecimal("48.00");

        int orderItemSeqIdval = 1;
        String orderItemSeqId = UtilFormatOut.formatPaddedNumber(
                orderItemSeqIdval, 4);
        String shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(
                orderItemSeqIdval, 4);

        // first create all the party roles
        createPartyRoles(ownerPartyId, supplierPartyId, delegator);

        GenericValue genVal = createOrderHeader(orderId, orderTypeId,
                orderName, salesChannelEnumId, orderDate, priority, entryDate,
                statusId, currencyUomId, webSiteId, remainingSubTotal,
                grandTotal, delegator);

        if (genVal != null) {

            orderId = genVal.getString("orderId");

            // set order status
            orderStatus(orderId, delegator);

            // create all the roles for this order
            createOrderRoles(ownerPartyId, supplierPartyId, orderId, delegator);
            String contactMechPurposeTypeId = "SHIPPING_LOCATION";
            String contactMechId = "9300";
            createOrderContactMech(orderId, contactMechPurposeTypeId,
                    contactMechId, delegator);

            // create ship group
            String shipGroupSeqId = "00001";
            String shipmentMethodTypeId = "NO_SHIPPING";
            String carrierPartyId = "_NA_";
            String carrierRoleTypeId = "CARRIER";
//			contactMechId = "9200";
            String maySplit = "N";
            String isGift = "N";
            Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();

            GenericValue val = createOrderItemShipGroup(orderId,
                    shipGroupSeqId, shipmentMethodTypeId, carrierPartyId,
                    carrierRoleTypeId, contactMechId, maySplit, isGift,
                    estimatedDeliveryDate, delegator);

            shipGroupSeqId = val.getString("orderItemShipGroup");

            // create the shipment
            String shipmentId = delegator.getNextSeqId("Shipment");
            String shipmentTypeId = "PURCHASE_SHIPMENT";
            BigDecimal estimatedShipCost = new BigDecimal("0.00");
            String destinationContactMechId = contactMechId;
            String destinationTelecomNumberId = "9301";

            GenericValue val3 = createShipment(shipmentId, shipmentTypeId,
                    statusId, orderId, shipGroupSeqId, estimatedShipCost,
                    destinationFacilityId, destinationContactMechId,
                    destinationTelecomNumberId, supplierPartyId, delegator);

            String shipmentRouteSegmentId = "00001";
            // String destFacilityId="WebStoreWarehouse";
//			String destContactMechId = "9200";
//			String destTelecomNumberId = "9201";
            carrierPartyId = "_NA_";
            shipmentMethodTypeId = "NO_SHIPPING";
            String carrierServiceStatusId = "SHRSCS_NOT_STARTED";

            GenericValue val6 = createShipmentRouteSegment(shipmentId,
                    shipmentRouteSegmentId, destinationFacilityId,
                    destinationContactMechId, destinationTelecomNumberId, carrierPartyId,
                    shipmentMethodTypeId, carrierServiceStatusId, delegator);
            shipmentRouteSegmentId = val6.getString("shipmentRouteSegmentId");

            String shipmentPackageSeqId = "00001";
            Timestamp dateCreated = UtilDateTime.nowTimestamp();
            GenericValue val7 = createShipmentPackage(shipmentId,
                    shipmentPackageSeqId, dateCreated, delegator);
            shipmentPackageSeqId = val7.getString("shipmentPackageSeqId");

            GenericValue val9 = createShipmentPackageRouteSeg(shipmentId,
                    shipmentPackageSeqId, shipmentRouteSegmentId, delegator);

            GenericValue val10 = createShipmentStatus(shipmentId,
                    "PURCH_SHIP_CREATED", UtilDateTime.nowTimestamp(),
                    delegator);
            GenericValue val11 = createShipmentStatus(shipmentId,
                    "PURCH_SHIP_RECEIVED", UtilDateTime.nowTimestamp(),
                    delegator);
            GenericValue val12 = createShipmentStatus(shipmentId,
                    "PURCH_SHIP_SHIPPED", UtilDateTime.nowTimestamp(),
                    delegator);

            String acctgTransShipmentId = delegator.getNextSeqId("AcctgTrans");// "9000";
            String acctgTransTypeId = "SHIPMENT_RECEIPT";
            Timestamp transactionDate = UtilDateTime.nowTimestamp();
            String isPosted = "Y";
            Timestamp postedDate = UtilDateTime.nowTimestamp();
            String glFiscalTypeId = "ACTUAL";

            // shipmentId="9997";
            GenericValue acctgTrans = createAcctgTrans(acctgTransShipmentId,
                    acctgTransTypeId, transactionDate, isPosted, postedDate,
                    glFiscalTypeId, supplierPartyId, shipmentId, null, null,
                    delegator);

            String invoiceId = delegator.getNextSeqId("Invoice");// "8008";
            String invoiceTypeId = "PURCHASE_INVOICE";
            String description = "Purchase Order Invoice";
            statusId = "INVOICE_READY";
            Timestamp invoiceDate = UtilDateTime.nowTimestamp();

            GenericValue val19 = createInvoice(invoiceId, invoiceTypeId,
                    description, supplierPartyId, ownerPartyId, statusId,
                    invoiceDate, currencyUomId, referenceNumber, delegator);
            int transSq = 1;
            for (ProductInventoryPojo entry : productList) {

                String acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(
                        transSq++, 4);
                shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(
                        orderItemSeqIdval, 4);

                String invoiceItemSeqId = UtilFormatOut.formatPaddedNumber(
                        orderItemSeqIdval, 4);
                orderItemSeqId = UtilFormatOut.formatPaddedNumber(
                        orderItemSeqIdval++, 4);

                String productId = entry.productId;
                String orderItemTypeId = "PRODUCT_ORDER_ITEM";

                //Debug.logInfo(" entry.productId: " + entry.productId, module);
                String prodCatalogId = "DemoCatalog";
                String isPromo = "N";
                BigDecimal quantity = entry.quantity;
                BigDecimal selectedAmount = BigDecimal.ZERO;
                BigDecimal unitPrice = entry.supplierLastPrice;// new BigDecimal("24.00");
                BigDecimal unitListPrice = new BigDecimal("0.00");
                String isModifiedPrice = "N";

                statusId = "ITEM_COMPLETED";
                estimatedDeliveryDate = UtilDateTime.nowTimestamp();

                val = createOrderItem(orderId, orderItemSeqId, orderItemTypeId,
                        productId, prodCatalogId, isPromo, quantity,
                        selectedAmount, unitPrice, unitListPrice,
                        isModifiedPrice, entry.productName, statusId,
                        estimatedDeliveryDate, delegator);
                orderItemSeqId = val.getString("orderItemSeqId");
                // set the item status
                orderItemStatus(orderId, orderItemSeqId, delegator);

                String orderItemPriceInfoId = delegator
                        .getNextSeqId("OrderItemPriceInfo");
                description = entry.productName; // "SupplierProduct [minimumOrderQuantity:0.000000, lastPrice: 24.000]";

                genVal = createOrderItemPriceInfo(orderItemPriceInfoId,
                        orderId, orderItemSeqId, description, delegator);

                GenericValue val1 = createOrderItemShipGroupAssoc(orderId,
                        orderItemSeqId, shipGroupSeqId, quantity, delegator);

                GenericValue val4 = createShipmentItem(shipmentId,
                        shipmentItemSeqId, productId, quantity, delegator);

                GenericValue val8 = createShipmentPackageContent(shipmentId,
                        shipmentPackageSeqId, shipmentItemSeqId, quantity,
                        delegator);

                // acctgTransId="9000";
                // String acctgTransEntrySeqId="00001";
                String acctgTransEntryTypeId = "_NA_";

                String roleTypeId = "BILL_FROM_VENDOR";
                String glAccountTypeId = "UNINVOICED_SHIP_RCPT";
                String glAccountId = "214000";
                BigDecimal amount = new BigDecimal(quantity.intValue())
                        .multiply(unitPrice);
                BigDecimal origAmount = new BigDecimal(quantity.intValue())
                        .multiply(unitPrice);
                ;

                String debitCreditFlag = "C";
                String reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val13 = createAcctgTransEntry(
                        acctgTransShipmentId, acctgTransEntrySeqId,
                        acctgTransEntryTypeId, supplierPartyId, roleTypeId,
                        productId, glAccountTypeId, glAccountId,
                        XuiContainer.getSession().getCompanyPartyId(), amount, currencyUomId, origAmount,
                        origCurrencyUomId, debitCreditFlag, reconcileStatusId,
                        delegator);

                // acctgTransId="9000";
                acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(
                        transSq++, 4);
                acctgTransEntryTypeId = "_NA_";
                roleTypeId = "BILL_FROM_VENDOR";
                glAccountTypeId = "INVENTORY_ACCOUNT";
                glAccountId = "140000";
                debitCreditFlag = "D";
                reconcileStatusId = "AES_NOT_RECONCILED";

                val13 = createAcctgTransEntry(acctgTransShipmentId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, productId,
                        glAccountTypeId, glAccountId, XuiContainer.getSession().getCompanyPartyId(),
                        amount, currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

                String inventoryItemId = delegator
                        .getNextSeqId("InventoryItem");
                ;
                String inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
                Timestamp datetimeReceived = UtilDateTime.nowTimestamp();
                String locationSeqId = "TLTLTLLL01";
                BigDecimal quantityOnHandTotal = quantity;
                BigDecimal availableToPromiseTotal = quantity;
                BigDecimal unitCost = unitPrice;

                GenericValue val15 = createInventoryItem(inventoryItemId,
                        inventoryItemTypeId, productId, ownerPartyId,
                        datetimeReceived, destinationFacilityId, locationSeqId,
                        quantityOnHandTotal, availableToPromiseTotal, unitCost,
                        currencyUomId, delegator);

                String shipmentReceiptId = delegator
                        .getNextSeqId("ShipmentReceipt");// "9000";
                // inventoryItemId="9025";
                // shipmentId="9997";
                // orderId="Demo1001";
                // orderItemSeqId="00001";
                datetimeReceived = UtilDateTime.nowTimestamp();
                BigDecimal quantityAccepted = quantity;
                BigDecimal quantityRejected = new BigDecimal("0.000000");

                GenericValue val16 = createShipmentReceipt(shipmentReceiptId,
                        inventoryItemId, productId, shipmentId, orderId,
                        orderItemSeqId, datetimeReceived, quantityAccepted,
                        quantityRejected, delegator);

                // inventoryItemId="9025";
                String inventoryItemDetailSeqId = "00001";
                Timestamp effectiveDate = UtilDateTime.nowTimestamp();
                BigDecimal quantityOnHandDiff = quantity;
                BigDecimal availableToPromiseDiff = quantity;
                BigDecimal accountingQuantityDiff = quantity;
                unitCost = unitPrice;
                // orderId="Demo1001";
                // orderItemSeqId="00001";
                // shipmentId="9997";
                // receiptId="9000";

                GenericValue val17 = createInventoryItemDetail(inventoryItemId,
                        inventoryItemDetailSeqId, effectiveDate,
                        quantityOnHandDiff, availableToPromiseDiff,
                        accountingQuantityDiff, unitCost, orderId,
                        orderItemSeqId, shipmentId, shipmentReceiptId,
                        delegator);

                String itemIssuanceId = delegator.getNextSeqId("ItemIssuance");
                Timestamp issuedDateTime = UtilDateTime.nowTimestamp();

                GenericValue val18 = createItemIssuance(itemIssuanceId,
                        orderId, orderItemSeqId, shipGroupSeqId, shipmentId,
                        shipmentItemSeqId, issuedDateTime, quantity, delegator);

                String invoiceItemTypeId = "PINV_FPROD_ITEM";

                amount = unitPrice;

                GenericValue val20 = createInvoiceItem(invoiceId,
                        invoiceItemSeqId, invoiceItemTypeId, productId,
                        quantity, amount, description, delegator);

                GenericValue val21 = createOrderItemBilling(orderId,
                        orderItemSeqId, invoiceId, invoiceItemSeqId,
                        shipmentReceiptId, quantity, amount, delegator);

                String acctgTransInvoiceId = delegator
                        .getNextSeqId("AcctgTrans");// 9001";
                acctgTransTypeId = "PURCHASE_INVOICE";
                transactionDate = UtilDateTime.nowTimestamp();
                isPosted = "Y";
                postedDate = UtilDateTime.nowTimestamp();
                glFiscalTypeId = "ACTUAL";
                // partyId="DemoSupplier";
                roleTypeId = "BILL_FROM_VENDOR";
                // invoiceId="8008";

                GenericValue val22 = createAcctgTrans(acctgTransInvoiceId,
                        acctgTransTypeId, transactionDate, isPosted,
                        postedDate, glFiscalTypeId, supplierPartyId, null,
                        roleTypeId, invoiceId, delegator);

                // acctgTransId="9001";
                acctgTransEntrySeqId = "00001";
                acctgTransEntryTypeId = "_NA_";
                // partyId="DemoSupplier";
                roleTypeId = "BILL_FROM_VENDOR";
                glAccountId = "214000";
                // amount= new BigDecimal ("48.00");
                // origAmount= new BigDecimal ("48.00");
                debitCreditFlag = "D";
                reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val23 = createAcctgTransEntry(acctgTransInvoiceId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, productId,
                        glAccountTypeId, glAccountId, XuiContainer.getSession().getCompanyPartyId(),
                        amount, currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

                acctgTransEntrySeqId = "00002";
                acctgTransEntryTypeId = "_NA_";
                roleTypeId = "BILL_FROM_VENDOR";
                glAccountTypeId = "ACCOUNTS_PAYABLE";
                glAccountId = "210000";
                debitCreditFlag = "C";
                reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val24 = createAcctgTransEntry(acctgTransInvoiceId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, null, glAccountTypeId,
                        glAccountId, XuiContainer.getSession().getCompanyPartyId(), amount,
                        currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

            }

        }

        return orderId;
    }

    public static GenericValue createOrderHeader(String orderId, String orderTypeId,
            String orderName, String salesChannelEnumId, Timestamp orderDate,
            String priority, Timestamp entryDate, String statusId,
            String currencyUom, String webSiteId, BigDecimal remainingSubTotal,
            BigDecimal grandTotal, Delegator delegator) {

        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader",
                    UtilMisc.toMap("orderId", orderId));

            if (orderHeader == null) {
                orderHeader = delegator.makeValue("OrderHeader");
            }

            orderHeader.set("orderId", orderId);
            orderHeader.set("orderTypeId", orderTypeId);
            orderHeader.set("orderName", orderName);
            orderHeader.set("salesChannelEnumId", salesChannelEnumId);
            orderHeader.set("orderDate", orderDate);
            orderHeader.set("priority", priority);
            orderHeader.set("entryDate", entryDate);
            orderHeader.set("statusId", statusId);
            orderHeader.set("currencyUom", currencyUom);
            orderHeader.set("webSiteId", webSiteId);
            orderHeader.set("remainingSubTotal", remainingSubTotal);
            orderHeader.set("grandTotal", grandTotal);
            orderHeader.create();
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return orderHeader;
    }

    public static GenericValue createOrderItem(String orderId, String orderItemSeqId,
            String orderItemTypeId, String productId, String prodCatalogId,
            String isPromo, BigDecimal quantity, BigDecimal selectedAmount,
            BigDecimal unitPrice, BigDecimal unitListPrice,
            String isModifiedPrice, String itemDescription, String statusId,
            Timestamp estimatedDeliveryDate, Delegator delegator) throws GenericEntityException {

        GenericValue orderItem = getOrderItem(orderId, orderItemSeqId, delegator);

        if (orderItem == null) {
            orderItem = delegator.makeValue("OrderItem");
            delegator.setNextSubSeqId(orderItem, "orderItemSeqId", 4, 1);

        }

        orderItem.set("orderId", orderId);
        orderItem.set("orderItemTypeId", orderItemTypeId);
        orderItem.set("productId", productId);
        orderItem.set("prodCatalogId", prodCatalogId);
        orderItem.set("isPromo", isPromo);
        orderItem.set("quantity", quantity);
        orderItem.set("selectedAmount", selectedAmount);
        orderItem.set("unitPrice", unitPrice);
        orderItem.set("unitListPrice", unitListPrice);
        orderItem.set("isModifiedPrice", isModifiedPrice);
        orderItem.set("itemDescription", itemDescription);
        orderItem.set("statusId", statusId);
        orderItem.set("estimatedDeliveryDate", estimatedDeliveryDate);
        orderItem.create();

        return orderItem;
    }

    public static GenericValue updateOrderItem(String orderId, String orderItemSeqId,
            String orderItemTypeId, String productId, String prodCatalogId,
            String isPromo, BigDecimal quantity, BigDecimal selectedAmount,
            BigDecimal unitPrice, BigDecimal unitListPrice,
            String isModifiedPrice, String itemDescription, String statusId,
            Timestamp estimatedDeliveryDate, Delegator delegator) throws GenericEntityException {

        GenericValue orderItem = getOrderItem(orderId, orderItemSeqId, delegator);

        orderItem.set("orderItemTypeId", orderItemTypeId);
        orderItem.set("productId", productId);
        orderItem.set("prodCatalogId", prodCatalogId);
        orderItem.set("isPromo", isPromo);
        orderItem.set("quantity", quantity);
        orderItem.set("selectedAmount", selectedAmount);
        orderItem.set("unitPrice", unitPrice);
        orderItem.set("unitListPrice", unitListPrice);
        orderItem.set("isModifiedPrice", isModifiedPrice);
        orderItem.set("itemDescription", itemDescription);
        orderItem.set("statusId", statusId);
        orderItem.set("estimatedDeliveryDate", estimatedDeliveryDate);
        orderItem.store();
        return orderItem;
    }

    public static GenericValue getOrderItem(String orderId, String orderItemSeqId,
            Delegator delegator) throws GenericEntityException {

        GenericValue orderItem = delegator.findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId));

        return orderItem;
    }

    static public GenericValue createOrderItemPriceInfo(String orderItemPriceInfoId,
            String orderId, String orderItemSeqId, String description,
            Delegator delegator) {

        GenericValue orderItemPriceInfo = null;
        try {
            orderItemPriceInfo = delegator.findByPrimaryKey(
                    "OrderItemPriceInfo", UtilMisc.toMap(
                            "orderItemPriceInfoId", orderItemPriceInfoId));
            if (orderItemPriceInfo == null) {
                orderItemPriceInfo = delegator.makeValue("OrderItemPriceInfo");
            }
            orderItemPriceInfo.set("orderId", orderId);
            orderItemPriceInfo
                    .set("orderItemPriceInfoId", orderItemPriceInfoId);
            orderItemPriceInfo.set("orderId", orderId);
            orderItemPriceInfo.set("orderItemSeqId", orderItemSeqId);
            orderItemPriceInfo.set("description", description);
            orderItemPriceInfo.create();
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return orderItemPriceInfo;
    }

    static public GenericValue createParty(String partyId, Delegator delegator) {
        GenericValue party = null;
        try {
            party = delegator.findByPrimaryKey("Party", UtilMisc.toMap(
                    "partyId", partyId));
            if (party == null) {
                party = delegator.makeValue("Party");
                party.set("partyId", partyId);
                party.create();
            }
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return party;
    }

    static public GenericValue createPartyGroup(String partyId, String groupName, Delegator delegator) {
        GenericValue partyGroup = null;
        try {
            partyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap(
                    "partyId", partyId));

            if (partyGroup == null) {
                partyGroup = delegator.makeValue("PartyGroup");
                partyGroup.set("partyId", partyId);
                partyGroup.set("groupName", groupName);
                partyGroup.create();
            }
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return partyGroup;
    }

    static GenericValue PartyGroup(String partyId, String groupName, Delegator delegator) {
        GenericValue partyGroup = null;
        try {
            partyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap(
                    "partyId", partyId));

            if (partyGroup == null) {
                partyGroup = delegator.makeValue("PartyGroup");
                partyGroup.set("partyId", partyId);
                partyGroup.set("groupName", groupName);
                partyGroup.create();
            }
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return partyGroup;
    }

    static public GenericValue createPartyRole(String partyId, String roleTypeId, Delegator delegator) throws GenericEntityException {
        GenericValue partyRole = null;
        partyRole = delegator.findByPrimaryKey("PartyRole", UtilMisc.toMap("partyId", partyId, "roleTypeId", roleTypeId));
        if (partyRole == null) {
            partyRole = delegator.makeValue("PartyRole");
            partyRole.set("partyId", partyId);
            partyRole.set("roleTypeId", roleTypeId);
            partyRole.create();
        }
        return partyRole;
    }

    public static GenericValue createOrderRole(String orderId, String partyId,
            String roleTypeId, Delegator delegator) throws GenericEntityException {

        GenericValue orderRole = null;
//        try {
        orderRole = delegator.findByPrimaryKey("OrderRole", UtilMisc.toMap(
                "orderId", orderId, "partyId", partyId, "roleTypeId",
                roleTypeId));
        if (orderRole == null) {
            orderRole = delegator.makeValue("OrderRole");
            orderRole.set("orderId", orderId);
            orderRole.set("partyId", partyId);
            orderRole.set("roleTypeId", roleTypeId);
            orderRole.create();
        }
        //      } catch (GenericEntityException e) {
        // TODO Auto-generated catch block
        //          Debug.logError(e, module);
        //          e.printStackTrace();
        //      }

        return orderRole;
    }

    static public GenericValue createOrderItemShipGroup(String orderId,
            String shipGroupSeqId, String shipmentMethodTypeId,
            String carrierPartyId, String carrierRoleTypeId,
            String contactMechId, String maySplit, String isGift,
            Timestamp estimatedDeliveryDate, Delegator delegator) {

        GenericValue orderItemShipGroup = null;
        try {
            orderItemShipGroup = findOrderItemShipGroup(orderId,
                    shipGroupSeqId, shipmentMethodTypeId,
                    carrierPartyId, carrierRoleTypeId,
                    contactMechId, maySplit, isGift,
                    estimatedDeliveryDate, delegator);

            if (orderItemShipGroup == null) {
                orderItemShipGroup = delegator.makeValue("OrderItemShipGroup");
                delegator.setNextSubSeqId(orderItemShipGroup, "shipGroupSeqId", 4, 1);
                orderItemShipGroup.set("orderId", orderId);
//            orderItemShipGroup.set("shipGroupSeqId", shipGroupSeqId);
                orderItemShipGroup.set("shipmentMethodTypeId", shipmentMethodTypeId);
                orderItemShipGroup.set("carrierPartyId", carrierPartyId);
                orderItemShipGroup.set("carrierRoleTypeId", carrierRoleTypeId);
                orderItemShipGroup.set("contactMechId", contactMechId);
                orderItemShipGroup.set("maySplit", maySplit);
                orderItemShipGroup.set("isGift", isGift);
                orderItemShipGroup.set("estimatedDeliveryDate",
                        estimatedDeliveryDate);
                orderItemShipGroup.create();
            } else {
                orderItemShipGroup.set("orderId", orderId);
//            orderItemShipGroup.set("shipGroupSeqId", shipGroupSeqId);
                orderItemShipGroup.set("shipmentMethodTypeId", shipmentMethodTypeId);
                orderItemShipGroup.set("carrierPartyId", carrierPartyId);
                orderItemShipGroup.set("carrierRoleTypeId", carrierRoleTypeId);
                orderItemShipGroup.set("contactMechId", contactMechId);
                orderItemShipGroup.set("maySplit", maySplit);
                orderItemShipGroup.set("isGift", isGift);
                orderItemShipGroup.set("estimatedDeliveryDate",
                        estimatedDeliveryDate);
                delegator.store(orderItemShipGroup);
            }

        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);

        }
        return orderItemShipGroup;
    }

    static public GenericValue findOrderItemShipGroup(String orderId,
            String shipGroupSeqId, String shipmentMethodTypeId,
            String carrierPartyId, String carrierRoleTypeId,
            String contactMechId, String maySplit, String isGift,
            Timestamp estimatedDeliveryDate, Delegator delegator) {

        GenericValue orderItemShipGroup = null;
        try {
            orderItemShipGroup = delegator.findByPrimaryKey(
                    "OrderItemShipGroup", UtilMisc.toMap("orderId", orderId, "shipGroupSeqId", shipGroupSeqId));
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
        }
        return orderItemShipGroup;
    }

    static public GenericValue createOrderItemShipGroupAssoc(String orderId,
            String orderItemSeqId, String shipGroupSeqId, BigDecimal quantity,
            Delegator delegator) {

        GenericValue genericVal = null;
        try {
            genericVal = delegator.findByPrimaryKey("OrderItemShipGroupAssoc",
                    UtilMisc.toMap("orderId", orderId, "orderItemSeqId",
                            orderItemSeqId, "shipGroupSeqId", shipGroupSeqId));
            if (genericVal == null) {
                genericVal = delegator.makeValue("OrderItemShipGroupAssoc");
                genericVal.set("orderId", orderId);
                genericVal.set("orderItemSeqId", orderItemSeqId);
                genericVal.set("shipGroupSeqId", shipGroupSeqId);
                genericVal.set("quantity", quantity);
                genericVal.create();

            } else {
                genericVal.set("quantity", quantity);
                genericVal.store();
            }
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return genericVal;
    }

    public static GenericValue createOrderStatus(String orderStatusId,
            String statusId, String orderId, String orderItemSeqId,
            Timestamp statusDatetime, String statusUserLogin,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = null;
//        try {
        genericVal = delegator.findByPrimaryKey("OrderStatus", UtilMisc.toMap("orderStatusId", orderStatusId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("OrderStatus");
        }
        genericVal.set("orderStatusId", orderStatusId);
        genericVal.set("statusId", statusId);
        genericVal.set("orderId", orderId);
        if (orderItemSeqId != null) {
            genericVal.set("orderItemSeqId", orderItemSeqId);
        }
        genericVal.set("statusDatetime", statusDatetime);
        genericVal.set("statusUserLogin", statusUserLogin);
        genericVal.create();
//        } catch (GenericEntityException e) {
//            Debug.logError(e, module);
//        }

        return genericVal;
    }
    /*
     static GenericValue createOrderContactMech(String orderId,
     String contactMechPurposeTypeId, String contactMechId,
     Delegator delegator) {

     GenericValue orderContactMech = null;
     try {
     orderContactMech = delegator.findByPrimaryKey("OrderContactMech",
     UtilMisc.toMap("orderId", orderId,
     "contactMechPurposeTypeId",
     contactMechPurposeTypeId, "contactMechId",
     contactMechId));
     if (orderContactMech == null)
     orderContactMech = delegator.makeValue("OrderContactMech");
     orderContactMech.set("orderId", orderId);
     orderContactMech.set("contactMechPurposeTypeId",
     contactMechPurposeTypeId);
     orderContactMech.set("contactMechId", contactMechId);
     orderContactMech.create();
     } catch (GenericEntityException e) {
     // TODO Auto-generated catch block
     Debug.logError(e, module);
     e.printStackTrace();
     }
     return orderContactMech;
     }
     */

    public static GenericValue createOrderContactMech(String orderId,
            String contactMechPurposeTypeId, String contactMechId,
            Delegator delegator) throws GenericEntityException {

        GenericValue orderContactMech = null;
//        try {
        orderContactMech = findOrderContactMech(orderId,
                contactMechPurposeTypeId, contactMechId,
                delegator);
        if (orderContactMech == null) {
            orderContactMech = delegator.makeValue("OrderContactMech");
        }
        orderContactMech.set("orderId", orderId);
        orderContactMech.set("contactMechPurposeTypeId", contactMechPurposeTypeId);
        orderContactMech.set("contactMechId", contactMechId);
        orderContactMech.create();
//        } catch (GenericEntityException e) {
        // TODO Auto-generated catch block
//            Debug.logError(e, module);
//        }
        return orderContactMech;
    }

    public static GenericValue findOrderContactMech(String orderId,
            String contactMechPurposeTypeId, String contactMechId,
            Delegator delegator) throws GenericEntityException {

        GenericValue orderContactMech = null;
//        try {
        orderContactMech = delegator.findByPrimaryKey("OrderContactMech",
                UtilMisc.toMap("orderId", orderId,
                        "contactMechPurposeTypeId",
                        contactMechPurposeTypeId, "contactMechId",
                        contactMechId));

        return orderContactMech;
    }

    public static GenericValue createShipment(String shipmentId,
            String shipmentTypeId, String statusId, String primaryOrderId,
            String primaryShipGroupSeqId, BigDecimal estimatedShipCost,
            String destinationFacilityId, String destinationContactMechId,
            String destinationTelecomNumberId, String partyIdFrom,
            Delegator delegator) {

        GenericValue genericVal = null;
        try {
            genericVal = delegator.findByPrimaryKey("Shipment",
                    UtilMisc.toMap("shipmentId", shipmentId));
            if (genericVal == null) {
                genericVal = delegator.makeValue("Shipment");
            }
            genericVal.set("shipmentId", shipmentId);
            genericVal.set("shipmentTypeId", shipmentTypeId);
            genericVal.set("statusId", statusId);
            genericVal.set("primaryOrderId", primaryOrderId);
            genericVal.set("primaryShipGroupSeqId", primaryShipGroupSeqId);
            genericVal.set("estimatedShipCost", estimatedShipCost);
            genericVal.set("destinationFacilityId", destinationFacilityId);
            genericVal.set("destinationContactMechId", destinationContactMechId);
            genericVal.set("destinationTelecomNumberId", destinationTelecomNumberId);
            genericVal.set("partyIdFrom", partyIdFrom);
            genericVal.create();
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }
        return genericVal;
    }

    static public GenericValue getShipmentItem(String shipmentId,
            String shipmentItemSeqId, String productId,
            Delegator delegator) {

        GenericValue genericVal = null;
        try {
            genericVal = delegator.findByPrimaryKey("ShipmentItem", UtilMisc
                    .toMap("shipmentId", shipmentId,
                            "shipmentItemSeqId", shipmentItemSeqId,
                            "productId", productId));
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
        }

        return genericVal;
    }

    static public GenericValue createShipmentItem(String shipmentId,
            String shipmentItemSeqId, String productId, BigDecimal quantity,
            Delegator delegator) {

        GenericValue genericVal = null;
        try {
            genericVal = delegator.findByPrimaryKey("ShipmentItem", UtilMisc
                    .toMap("shipmentId", shipmentId, "shipmentItemSeqId",
                            shipmentItemSeqId));
            if (genericVal == null) {
                genericVal = delegator.makeValue("ShipmentItem");
                delegator.setNextSubSeqId(genericVal, "shipmentItemSeqId", 4, 1);

            }
            genericVal.set("shipmentId", shipmentId);
//            genericVal.set("shipmentItemSeqId", shipmentItemSeqId);
            genericVal.set("productId", productId);
            genericVal.set("quantity", quantity);
            genericVal.create();
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }

        return genericVal;
    }

    static public GenericValue createShipmentRouteSegment(String shipmentId,
            String shipmentRouteSegmentId, String destFacilityId,
            String destContactMechId, String destTelecomNumberId,
            String carrierPartyId, String shipmentMethodTypeId,
            String carrierServiceStatusId, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey(
                "ShipmentRouteSegment", UtilMisc.toMap("shipmentId",
                        shipmentId, "shipmentRouteSegmentId",
                        shipmentRouteSegmentId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentRouteSegment");
            delegator.setNextSubSeqId(genericVal, "shipmentRouteSegmentId", 4, 1);

        }
        genericVal.set("shipmentId", shipmentId);
//        genericVal.set("shipmentRouteSegmentId", shipmentRouteSegmentId);
        genericVal.set("destFacilityId", destFacilityId);
        genericVal.set("destContactMechId", destContactMechId);
        genericVal.set("destTelecomNumberId", destTelecomNumberId);

        genericVal.set("carrierPartyId", carrierPartyId);
        genericVal.set("shipmentMethodTypeId", shipmentMethodTypeId);
        genericVal.set("carrierServiceStatusId", carrierServiceStatusId);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createShipmentPackage(String shipmentId,
            String shipmentPackageSeqId, Timestamp dateCreated,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("ShipmentPackage",
                UtilMisc.toMap("shipmentId", shipmentId,
                        "shipmentPackageSeqId", shipmentPackageSeqId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentPackage");
            delegator.setNextSubSeqId(genericVal, "shipmentPackageSeqId", 4, 1);

        }
        genericVal.set("shipmentId", shipmentId);
//        genericVal.set("shipmentPackageSeqId", shipmentPackageSeqId);
        genericVal.set("dateCreated", dateCreated);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createShipmentPackageContent(String shipmentId,
            String shipmentPackageSeqId, String shipmentItemSeqId,
            BigDecimal quantity, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey(
                "ShipmentPackageContent", UtilMisc.toMap("shipmentId",
                        shipmentId, "shipmentPackageSeqId",
                        shipmentPackageSeqId, "shipmentItemSeqId",
                        shipmentItemSeqId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentPackageContent");
        }
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("shipmentPackageSeqId", shipmentPackageSeqId);
        genericVal.set("shipmentItemSeqId", shipmentItemSeqId);
        genericVal.set("quantity", quantity);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createShipmentPackageRouteSeg(String shipmentId,
            String shipmentPackageSeqId, String shipmentRouteSegmentId,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey(
                "ShipmentPackageRouteSeg", UtilMisc.toMap("shipmentId",
                        shipmentId, "shipmentPackageSeqId",
                        shipmentPackageSeqId, "shipmentRouteSegmentId",
                        shipmentRouteSegmentId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentPackageRouteSeg");
        }
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("shipmentPackageSeqId", shipmentPackageSeqId);
        genericVal.set("shipmentRouteSegmentId", shipmentRouteSegmentId);
        genericVal.create();

        return genericVal;
    }

    static public GenericValue createShipmentStatus(String shipmentId,
            String statusId, Timestamp statusDate, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("ShipmentStatus",
                UtilMisc.toMap("shipmentId", shipmentId, "statusId", statusId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentStatus");
        }
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("statusId", statusId);
        genericVal.set("statusDate", statusDate);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createAcctgTrans(String acctgTransId,
            String acctgTransTypeId, Timestamp transactionDate,
            String isPosted, Timestamp postedDate, String glFiscalTypeId,
            String partyId, String shipmentId, String roleTypeId,
            String invoiceId, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("AcctgTrans",
                UtilMisc.toMap("acctgTransId", acctgTransId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("AcctgTrans");
        }
        genericVal.set("acctgTransId", acctgTransId);
        genericVal.set("acctgTransTypeId", acctgTransTypeId);
        genericVal.set("transactionDate", transactionDate);
        genericVal.set("isPosted", isPosted);
        genericVal.set("postedDate", postedDate);

        genericVal.set("glFiscalTypeId", glFiscalTypeId);
        genericVal.set("partyId", partyId);
        genericVal.set("shipmentId", shipmentId);

        genericVal.set("roleTypeId", roleTypeId);
        genericVal.set("invoiceId", invoiceId);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createAcctgTransEntry(String acctgTransId,
            String acctgTransEntrySeqId, String acctgTransEntryTypeId,
            String partyId, String roleTypeId, String productId,
            String glAccountTypeId, String glAccountId,
            String organizationPartyId, BigDecimal amount,
            String currencyUomId, BigDecimal origAmount,
            String origCurrencyUomId, String debitCreditFlag,
            String reconcileStatusId, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("AcctgTransEntry",
                UtilMisc.toMap("acctgTransId", acctgTransId,
                        "acctgTransEntrySeqId", acctgTransEntrySeqId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("AcctgTransEntry");
            delegator.setNextSubSeqId(genericVal, "acctgTransEntrySeqId", 4, 1);

        }
        genericVal.set("acctgTransId", acctgTransId);
//        genericVal.set("acctgTransEntrySeqId", acctgTransEntrySeqId);
        genericVal.set("acctgTransEntryTypeId", acctgTransEntryTypeId);
        genericVal.set("partyId", partyId);
        genericVal.set("roleTypeId", roleTypeId);

        genericVal.set("productId", productId);
        genericVal.set("glAccountTypeId", glAccountTypeId);
        genericVal.set("glAccountId", glAccountId);

        genericVal.set("organizationPartyId", organizationPartyId);
        genericVal.set("amount", amount);
        genericVal.set("currencyUomId", currencyUomId);

        genericVal.set("origAmount", origAmount);
        genericVal.set("origCurrencyUomId", origCurrencyUomId);
        genericVal.set("debitCreditFlag", debitCreditFlag);
        genericVal.set("reconcileStatusId", reconcileStatusId);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createInventoryItem(String inventoryItemId,
            String inventoryItemTypeId, String productId, String ownerPartyId,
            Timestamp datetimeReceived, String facilityId,
            String locationSeqId, BigDecimal quantityOnHandTotal,
            BigDecimal availableToPromiseTotal, BigDecimal unitCost,
            String currencyUomId,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("InventoryItem",
                UtilMisc.toMap("inventoryItemId", inventoryItemId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("InventoryItem");
        }
        genericVal.set("inventoryItemId", inventoryItemId);
        genericVal.set("inventoryItemTypeId", inventoryItemTypeId);
        genericVal.set("productId", productId);
        genericVal.set("ownerPartyId", ownerPartyId);
        genericVal.set("datetimeReceived", datetimeReceived);

        genericVal.set("facilityId", facilityId);
        genericVal.set("locationSeqId", locationSeqId);
        genericVal.set("quantityOnHandTotal", quantityOnHandTotal);

        genericVal.set("availableToPromiseTotal", availableToPromiseTotal);
        genericVal.set("unitCost", unitCost);
        genericVal.set("currencyUomId", currencyUomId);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createShipmentReceipt(String receiptId,
            String inventoryItemId, String productId, String shipmentId,
            String orderId, String orderItemSeqId, Timestamp datetimeReceived,
            BigDecimal quantityAccepted, BigDecimal quantityRejected,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("ShipmentReceipt",
                UtilMisc.toMap("receiptId", receiptId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ShipmentReceipt");
        }
        genericVal.set("receiptId", receiptId);
        genericVal.set("inventoryItemId", inventoryItemId);
        genericVal.set("productId", productId);
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("orderId", orderId);
        genericVal.set("orderItemSeqId", orderItemSeqId);

        genericVal.set("datetimeReceived", datetimeReceived);
        genericVal.set("quantityAccepted", quantityAccepted);
        genericVal.set("quantityRejected", quantityRejected);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createInventoryItemDetail(String inventoryItemId,
            String inventoryItemDetailSeqId, Timestamp effectiveDate,
            BigDecimal quantityOnHandDiff, BigDecimal availableToPromiseDiff,
            BigDecimal accountingQuantityDiff, BigDecimal unitCost,
            String orderId,
            String orderItemSeqId, String shipmentId, String receiptId,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey(
                "InventoryItemDetail", UtilMisc.toMap("inventoryItemId",
                        inventoryItemId, "inventoryItemDetailSeqId",
                        inventoryItemDetailSeqId));

        if (genericVal == null) {

            genericVal = delegator.makeValue("InventoryItemDetail");
            //Debug.logInfo("InventoryItemDetail : inventoryItemId: "
//                    + inventoryItemId, module);
            //Debug.logInfo("InventoryItemDetail : inventoryItemDetailSeqId: "
            //                   + inventoryItemDetailSeqId, module);
            genericVal.set("inventoryItemId", inventoryItemId);
            genericVal.set("inventoryItemDetailSeqId", inventoryItemDetailSeqId);
            genericVal.set("effectiveDate", effectiveDate);
//			genericVal.set("quantityOnHandDiff", quantityOnHandDiff);
//			genericVal.set("availableToPromiseDiff", availableToPromiseDiff);
            genericVal.set("accountingQuantityDiff", accountingQuantityDiff);
            genericVal.set("unitCost", unitCost);
            genericVal.set("orderId", orderId);

            genericVal.set("orderItemSeqId", orderItemSeqId);
            genericVal.set("shipmentId", shipmentId);
            genericVal.set("receiptId", receiptId);
            genericVal.set("description", "Done By Suresh");
            genericVal.create();

        }
        return genericVal;
    }

    static public GenericValue createItemIssuance(String itemIssuanceId,
            String orderId, String orderItemSeqId, String shipGroupSeqId,
            String shipmentId, String shipmentItemSeqId,
            Timestamp issuedDateTime, BigDecimal quantity, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("ItemIssuance",
                UtilMisc.toMap("itemIssuanceId", itemIssuanceId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("ItemIssuance");
        }
        genericVal.set("itemIssuanceId", itemIssuanceId);
        genericVal.set("orderId", orderId);
        genericVal.set("orderItemSeqId", orderItemSeqId);
        genericVal.set("shipGroupSeqId", shipGroupSeqId);
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("shipmentItemSeqId", shipmentItemSeqId);
        genericVal.set("issuedDateTime", issuedDateTime);
        genericVal.set("quantity", quantity);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createInvoice(String invoiceId, String invoiceTypeId,
            String description, String partyIdFrom, String partyId,
            String statusId, Timestamp invoiceDate, String currencyUomId, String referenceNumber,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("Invoice",
                UtilMisc.toMap("invoiceId", invoiceId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("Invoice");
        }
        genericVal.set("invoiceId", invoiceId);
        genericVal.set("invoiceTypeId", invoiceTypeId);
        genericVal.set("description", description);
        genericVal.set("partyIdFrom", partyIdFrom);
        genericVal.set("partyId", partyId);
        genericVal.set("referenceNumber", referenceNumber);

        genericVal.set("statusId", statusId);
        genericVal.set("invoiceDate", invoiceDate);
        genericVal.set("currencyUomId", currencyUomId);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createInvoiceItem(String invoiceId,
            String invoiceItemSeqId, String invoiceItemTypeId,
            String productId, BigDecimal quantity, BigDecimal amount,
            String description, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("InvoiceItem",
                UtilMisc.toMap("invoiceId", invoiceId, "invoiceItemSeqId",
                        invoiceItemSeqId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("InvoiceItem");
        }
        genericVal.set("invoiceId", invoiceId);
        genericVal.set("invoiceItemSeqId", invoiceItemSeqId);
        genericVal.set("invoiceItemTypeId", invoiceItemTypeId);
        genericVal.set("productId", productId);
        genericVal.set("quantity", quantity);

        genericVal.set("amount", amount);
        genericVal.set("description", description);
        genericVal.create();
        return genericVal;
    }

    static public GenericValue createOrderItemBilling(String orderId,
            String orderItemSeqId, String invoiceId, String invoiceItemSeqId,
            String shipmentReceiptId, BigDecimal quantity, BigDecimal amount,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey(
                "OrderItemBilling", UtilMisc.toMap("orderId", orderId,
                        "orderItemSeqId", orderItemSeqId, "invoiceId",
                        invoiceId, "invoiceItemSeqId", invoiceItemSeqId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("OrderItemBilling");
        }
        genericVal.set("orderId", orderId);
        genericVal.set("orderItemSeqId", orderItemSeqId);
        genericVal.set("invoiceId", invoiceId);
        genericVal.set("invoiceItemSeqId", invoiceItemSeqId);
        genericVal.set("shipmentReceiptId", shipmentReceiptId);

        genericVal.set("quantity", quantity);
        genericVal.set("amount", amount);
        genericVal.create();
        return genericVal;
    }

    static GenericValue createAcctgTrans(String acctgTransId,
            String acctgTransTypeId, String transactionDate, String isPosted,
            String postedDate, String glFiscalTypeId, String partyId,
            String roleTypeId, String invoiceId, Delegator delegator)
            throws GenericEntityException {

        GenericValue genericVal = delegator.findByPrimaryKey("AcctgTrans",
                UtilMisc.toMap("acctgTransId", acctgTransId));
        if (genericVal == null) {
            genericVal = delegator.makeValue("AcctgTrans");
        }
        genericVal.set("acctgTransId", acctgTransId);
        genericVal.set("acctgTransTypeId", acctgTransTypeId);
        genericVal.set("transactionDate", transactionDate);
        genericVal.set("isPosted", isPosted);
        genericVal.set("postedDate", postedDate);

        genericVal.set("glFiscalTypeId", glFiscalTypeId);
        genericVal.set("partyId", partyId);
        genericVal.set("roleTypeId", roleTypeId);
        genericVal.set("invoiceId", invoiceId);
        genericVal.create();
        return genericVal;
    }

    static public List<ProductImportPojo> importProductCSVFile(String fileName) {
        BufferedReader CSVFile;

        //clear
//	        modifiedProductList.clear();
        List<ProductImportPojo> importList = null;

        try {
            CSVFile = new BufferedReader(new FileReader(fileName));

            String dataRow;
            try {
                importList = FastList.newInstance();
//					currentIndex = 0;				    
                dataRow = CSVFile.readLine();
                // The while checks to see if the data is null. If it is, we've hit
                //  the end of the file. If not, process the data.
                while (dataRow != null) {
                    String[] dataArray = dataRow.split(",");
                    System.out.println(dataRow); // Print the data line.
                    try {
                        int index = 0;
                        ProductImportPojo pojo = new ProductImportPojo();
                        for (String item : dataArray) {

                            try {
                                pojo.SetItem(index++, item.trim());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
//			            	System.out.print(item + ","); 
                        }

                        for (int ii = index; ii < 18; ii++) {
                            pojo.SetItem(index++, "");
                        }

                        importList.add(pojo);
                    } catch (Exception e) {
                        Debug.logError(e, module);
                    }

                    dataRow = CSVFile.readLine(); // Read next line of data.
                }
                /*					Debug.logInfo("========== importList.size() " + importList.size() , module);
                 if(currentIndex < importList.size()){
                 currPojo= importList.get(currentIndex++);
			        	
                 //get next one if empty
                 if(currPojo.isEmpty() && currentIndex < importList.size())
                 currPojo= importList.get(currentIndex++);
			        	
                 setDialogFields();			        	
                 }
                 */
                // Close the file once all data has been read.
                CSVFile.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Read the first line of data.

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // End the printout with a blank line.
        System.out.println();

        return importList;

    }

    public static boolean updateProduct(GenericValue product, ProductPojo data, Delegator delegator) {

        try {

            String isInactive = data.isInactive;
            if ("Y".equalsIgnoreCase(isInactive)) {
                product.set("salesDiscontinuationDate", UtilDateTime.nowTimestamp());
            }

            product.set("productId", data.productId);
            product.set("productTypeId", data.productTypeId);
            product.set("internalName", data.internalName);
            product.set("description", data.description);
            product.set("longDescription", data.longDescription);
            product.set("productName", data.productName);
            product.set("brandName", data.brandName);
            product.set("comments", data.comments);
            product.set("smallImageUrl", data.smallImageUrl);
            product.set("mediumImageUrl", data.mediumImageUrl);
            product.set("largeImageUrl", data.largeImageUrl);
            product.set("originalImageUrl", data.originalImageUrl);
            product.set("detailImageUrl", data.detailImageUrl);

            product.set("productHeight", data.height);
            product.set("heightUomId", data.heightUomId);
            product.set("productDepth", data.productLength);
            product.set("depthUomId", data.productLengthUomId);
            product.set("productWidth", data.width);
            product.set("widthUomId", data.widthUomId);
            product.set("taxable", data.taxable);
            product.set("weight", data.weight);
            product.set("weightUomId", data.weightUomId);
            product.set("taxable", data.taxable);
            product.set("isVirtual", data.isVirtual);
            product.set("isVariant", data.isVariant);
            product.set("inventoryMessage", data.inventoryMessage);
            product.set("returnable", data.returnable);
            product.set("chargeShipping", data.chargeShipping);
            product.set("autoCreateKeywords", data.autoCreateKeywords);
            product.set("includeInPromotions", data.includeInPromotions);
            product.set("requirementMethodEnumId", data.requirementMethodEnumId);
            product.set("piecesIncluded", data.piecesIncluded);
            product.put("requireInventory", data.requireInventory);

            if (data.createdDate != null) {
                product.set("createdDate", data.createdDate);
            } else {
                product.set("createdDate", UtilDateTime.nowTimestamp());
            }
            Debug.logWarning("Product DONE", module);

            delegator.store(product);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return false;
        }

        return true;
    }

    public static synchronized Map<String, Object> getInventoryAvailableByFacility(String productId, String facilityId, LocalDispatcher dispatcher) {

        Map<String, Object> resultMap = FastMap.newInstance();
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            // Get ATP for the product
            Map<String, Object> getProductInventoryAvailableResult = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", productId, "facilityId", facilityId));
            BigDecimal availableToPromiseTotal = (BigDecimal) getProductInventoryAvailableResult.get("availableToPromiseTotal");
            BigDecimal quantityOnHandTotal = (BigDecimal) getProductInventoryAvailableResult.get("quantityOnHandTotal");
//			String available = (String) getProductInventoryAvailableResult.get("available");    				
            resultMap.put("availableToPromiseTotal", availableToPromiseTotal);
            resultMap.put("quantityOnHandTotal", quantityOnHandTotal);
//			resultMap.put("available",available);			
            if (availableToPromiseTotal == null) {
                Debug.logError("availableToPromise is null ", module);
            }
            //   			Debug.logError("availableToPromise: " + availableToPromiseTotal.toString(), module);

            if (quantityOnHandTotal == null) {
                Debug.logError("quantityOnHandTotal is null ", module);
            }

//    		if(available==null)
//       			Debug.logError("available is null ", module);
//    			Debug.logError("quantityOnHandTotal: " + quantityOnHandTotal.toString(), module);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            //pos.OrderMaxOptionPane.showMessageDialog(null, "dialog/error/exception", e.getMessage());            
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
            }

        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
            }
        }

        return resultMap;
    }

    static public String phoneDialAlphaToNumaric(String val) {
        String num = val;
        String alpha = val.toUpperCase();

        if (alpha.startsWith("A") || alpha.startsWith("B") || alpha.startsWith("C")) {
            num = "2";
        } else if (alpha.startsWith("D") || alpha.startsWith("E") || alpha.startsWith("F")) {
            num = "3";
        } else if (alpha.startsWith("G") || alpha.startsWith("H") || alpha.startsWith("I")) {
            num = "4";
        } else if (alpha.startsWith("J") || alpha.startsWith("K") || alpha.startsWith("L")) {
            num = "5";
        } else if (alpha.startsWith("M") || alpha.startsWith("N") || alpha.startsWith("O")) {
            num = "6";
        } else if (alpha.startsWith("P") || alpha.startsWith("Q") || alpha.startsWith("R") || alpha.startsWith("S")) {
            num = "7";
        } else if (alpha.startsWith("T") || alpha.startsWith("U") || alpha.startsWith("V")) {
            num = "8";
        } else if (alpha.startsWith("W") || alpha.startsWith("X") || alpha.startsWith("Y") || alpha.startsWith("Z")) {
            num = "9";
        }

        return num;
    }

    static public List<GenericValue> getProductCategoryMembers(String productCategoryId, LocalDispatcher dispatcher) {

        List<GenericValue> productsList = FastList.newInstance();

        if (UtilValidate.isNotEmpty(productCategoryId)) {

            Map<String, Object> result;
            try {
                result = dispatcher.runSync("getProductCategoryMembers", UtilMisc.toMap("categoryId", productCategoryId));

                if (result.get("categoryMembers") != null) {
                    List<GenericValue> productCategoryMembers = UtilGenerics.checkList(result.get("categoryMembers"), GenericValue.class);
                    if (productCategoryMembers != null) {
                        for (GenericValue prodCatMemb : productCategoryMembers) {
                            if (prodCatMemb != null) {
                                String productId = prodCatMemb.getString("productId");
                                Debug.logError("getProductCategoryMembers productId: " + productId, module);
                                if (productId != null) {
                                    GenericValue prod = null;
                                    try {

                                        try {
                                            //
                                            prod = ProductSingleton.getProduct(productId).getGenericValueObj();
                                            // do not consider discontinued product                                          
                                        } catch (Exception ex) {
                                            Logger.getLogger(PosProductHelper.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        if (prod == null) {

                                            prod = prodCatMemb.getRelatedOne("Product");
                                        }

                                        Timestamp salesDiscontinuationDate = prod.getTimestamp("salesDiscontinuationDate");
                                        //if (salesDiscontinuationDate == null) {
                                        productsList.add(prod);
//                                        Debug.logError(" from product categoryMembers productId: " + prod.getString("productId"), module);
                                        //}

                                    } catch (GenericEntityException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (GenericServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        productsList = EntityUtil.orderBy(productsList, UtilMisc.toList("internalName ASC"));
        return productsList;
    }

    static public List<GenericValue> getProductCategoryMembersOnly(String productCategoryId, LocalDispatcher dispatcher) {

        List<GenericValue> productsList = FastList.newInstance();

        if (UtilValidate.isNotEmpty(productCategoryId)) {

            Map<String, Object> result;
            try {
                result = dispatcher.runSync("getProductCategoryMembers", UtilMisc.toMap("categoryId", productCategoryId));

                if (result.get("categoryMembers") != null) {
                    return UtilGenerics.checkList(result.get("categoryMembers"), GenericValue.class);
                    /*List<GenericValue> productCategoryMembers = 
                     if (productCategoryMembers != null) {
                     for (GenericValue prodCatMemb : productCategoryMembers) {
                     if (prodCatMemb != null) {
                     String productId = prodCatMemb.getString("productId");
                     Debug.logError("getProductCategoryMembers productId: " + productId, module);
                     if (productId != null) {
                     GenericValue prod = null;
                     try {

                     try {
                     //
                     prod = ProductSingleton.getProduct(productId).getGenericValueObj();
                     // do not consider discontinued product                                          
                     } catch (Exception ex) {
                     Logger.getLogger(PosProductHelper.class.getName()).log(Level.SEVERE, null, ex);
                     }

                     if (prod == null) {

                     prod = prodCatMemb.getRelatedOne("Product");
                     }

                     Timestamp salesDiscontinuationDate = prod.getTimestamp("salesDiscontinuationDate");
                     //if (salesDiscontinuationDate == null) {
                     productsList.add(prod);
                     //                                        Debug.logError(" from product categoryMembers productId: " + prod.getString("productId"), module);
                     //}

                     } catch (GenericEntityException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     }
                     }
                     }
                     }
                     }*/
                }
            } catch (GenericServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        productsList = EntityUtil.orderBy(productsList, UtilMisc.toList("internalName ASC"));
        return productsList;
    }

    public static List<GenericValue> getProductCategories(String parentProductCategoryId, Delegator delegator) {
        GenericValue tmpVal = null;
        List<GenericValue> categoryList = FastList.newInstance();

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("primaryParentCategoryId", EntityOperator.EQUALS, parentProductCategoryId));

            List<GenericValue> valueList = delegator.findList("ProductCategory", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                valueList = EntityUtil.orderBy(valueList, UtilMisc.toList("categoryName ASC"));
//	        	valueList = EntityUtil.filterByDate(valueList);

                Iterator<GenericValue> it = valueList.iterator();
                while (it.hasNext()) {

                    tmpVal = it.next();
//                    //Debug.logInfo("sorted category : " + tmpVal.getString("categoryName"), module);
                    categoryList.add(tmpVal);
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of ProductCategoryRollup " + e, module);
        }

        return categoryList;
    }

    public static List<GenericValue> getParentProductCategories(String productCategoryId, Delegator delegator) {
        GenericValue tmpVal = null;
        List<GenericValue> categoryList = FastList.newInstance();

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productCategoryId", EntityOperator.EQUALS, productCategoryId));

            List<GenericValue> valueList = delegator.findList("ProductCategory", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                valueList = EntityUtil.orderBy(valueList, UtilMisc.toList("categoryName ASC"));
//	        	valueList = EntityUtil.filterByDate(valueList);

                Iterator<GenericValue> it = valueList.iterator();
                while (it.hasNext()) {

                    tmpVal = it.next();
//                    //Debug.logInfo("sorted category : " + tmpVal.getString("categoryName"), module);
                    categoryList.add(tmpVal);
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of ProductCategoryRollup " + e, module);
        }

        return categoryList;
    }

    public static void LoadPriceList(ProductInventoryPojo pojo, Delegator delegator) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, pojo.productId));
        //        exprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_LISTPRICE), 
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE),
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_AVERAGECOST) ));        

        GenericValue defaultPrice = null;
        GenericValue listPrice = null;
        GenericValue avgCost = null;

        List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (priceList != null) {
            priceList = EntityUtil.filterByDate(priceList);
            Iterator<GenericValue> it = priceList.iterator();
            while (it.hasNext()) {
                GenericValue productPrice = it.next();

                if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_LISTPRICE)) {
                    if (listPrice == null) {
                        listPrice = productPrice;
                    }
                } else if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE)) {
                    if (defaultPrice == null) {
                        defaultPrice = productPrice;
                    }
                } else if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_AVERAGECOST)) {
                    if (avgCost == null) {
                        avgCost = productPrice;
                    }
                }
                if (defaultPrice != null && listPrice != null && avgCost != null) {
                    break;
                }
            }

            if (defaultPrice != null) {
                // check from/thru dates
                if (defaultPrice.getBigDecimal("price") != null) {
                    pojo.defaultPrice = defaultPrice.getBigDecimal("price");
                }
            }

            if (listPrice != null) {
                if (listPrice.getBigDecimal("price") != null) {
                    pojo.price = listPrice.getBigDecimal("price");
                }
            }

            if (avgCost != null) {
                if (avgCost.getBigDecimal("price") != null) {
                    pojo.purchasePrice = avgCost.getBigDecimal("price");
                }
            }
        }
    }

    public static List<GenericValue> getSupplierProductList(String partyId, String supItemId, Delegator delegator) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
        exprs.add(EntityCondition.makeCondition("supplierProductId", EntityOperator.EQUALS, supItemId));
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE),
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_AVERAGECOST) ));        

        List<GenericValue> supplierProductList = delegator.findList("SupplierProduct", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
//        if (supplierProductList != null) {
//            supplierProductList = EntityUtil.filterByDate(supplierProductList);
//        }
        return supplierProductList;
    }

    public static Map<String, Object> getPriceListAndProductDetails(String productId, Delegator delegator) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        //        exprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_LISTPRICE), 
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE),
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_AVERAGECOST) ));        

        List<GenericValue> defaultPrice = new ArrayList<GenericValue>();
        List<GenericValue> listPrice = new ArrayList<GenericValue>();;
        List<GenericValue> avgCost = new ArrayList<GenericValue>();;
        Map<String, Object> resultMap = FastMap.newInstance();

        List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (priceList != null) {
//            priceList = EntityUtil.filterByDate(priceList);
            Iterator<GenericValue> it = priceList.iterator();
            while (it.hasNext()) {
                GenericValue productPrice = it.next();

                if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_LISTPRICE)) {
                    listPrice.add(productPrice);
                } else if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE)) {
                    defaultPrice.add(productPrice);
                } else if (productPrice.getString("productPriceTypeId").equals(PosProductHelper.ProductPriceTypeId_AVERAGECOST)) {
                    avgCost.add(productPrice);
                }
            }

            listPrice = EntityUtil.orderBy(listPrice, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
            resultMap.put(ProductPriceTypeId_LISTPRICE, listPrice);

            defaultPrice = EntityUtil.orderBy(defaultPrice, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
            resultMap.put(ProductPriceTypeId_DEFAULTPRICE, defaultPrice);

            avgCost = EntityUtil.orderBy(avgCost, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
            resultMap.put(ProductPriceTypeId_AVERAGECOST, avgCost);

            GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), false);

            if (product != null) {

                resultMap.put("Product", product);

                //get last supplier details
                List<GenericValue> supplierProductList = product.getRelated("SupplierProduct");
                List<GenericValue> orderedSupplierProductList = EntityUtil.orderBy(supplierProductList, UtilMisc.toList("createdStamp DESC"));

                if (orderedSupplierProductList == null) {
                    orderedSupplierProductList = new ArrayList<GenericValue>();
                }

                resultMap.put(PosProductHelper.SUPPLIERPRODUCT, orderedSupplierProductList);
                GenericValue eanVal = PosProductHelper.getGoodIdentification(productId, PosProductHelper.GoodIdentificationTypeIdEAN, delegator);
                if (eanVal != null) {
                    resultMap.put(PosProductHelper.GoodIdentificationTypeIdEAN, eanVal);
                }
            }
        }

        return resultMap;
    }

    public static List<ProductPrice> getPriceList(String productId, String productPriceTypeId, String productStoreGroupId, XuiSession session) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        Delegator delegator = session.getDelegator();
        exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        exprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, productPriceTypeId));
        exprs.add(EntityCondition.makeCondition("productStoreGroupId", EntityOperator.EQUALS, productStoreGroupId));
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE),
        //        		EntityOperator.OR, EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, PosProductHelper.ProductPriceTypeId_AVERAGECOST) ));        
        List<ProductPrice> priceList = FastList.newInstance();

        List<GenericValue> priceListGV = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (priceList != null) {
//            priceList = EntityUtil.filterByDate(priceList);
            priceListGV = EntityUtil.orderBy(priceListGV, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
            Iterator<GenericValue> it = priceListGV.iterator();
            while (it.hasNext()) {
                GenericValue productPriceGV = it.next();
                priceList.add(new ProductPrice(productPriceGV));
            }
        }
        if (priceList.isEmpty()) {
            priceList.add(LoadProductPriceWorker.createProductPrice(productId, productPriceTypeId, session));
        }

        return priceList;
    }

    public static List<ProductFeatureAndAppl> getProductFeatureAppl(String productId, String productFeatureTypeId, String productFeatureCategoryId, XuiSession session) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        Delegator delegator = session.getDelegator();
        exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        exprs.add(EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.EQUALS, productFeatureTypeId));
        exprs.add(EntityCondition.makeCondition("productFeatureCategoryId", EntityOperator.EQUALS, productFeatureCategoryId));

        List<ProductFeatureAndAppl> priceList = FastList.newInstance();

        List<GenericValue> priceListGV = delegator.findList("ProductFeatureAndAppl", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (priceList != null) {
//            priceList = EntityUtil.filterByDate(priceList);
            priceListGV = EntityUtil.orderBy(priceListGV, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
            Iterator<GenericValue> it = priceListGV.iterator();
            while (it.hasNext()) {
                priceList.add(new ProductFeatureAndAppl(it.next()));
            }
        }

        return priceList;
    }

    public static List<GenericValue> getSupplierProduct(String productId, Delegator delegator) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));

        List<GenericValue> supplierProductList = delegator.findList("SupplierProduct", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (supplierProductList != null) {

            supplierProductList = EntityUtil.orderBy(supplierProductList, UtilMisc.toList("availableFromDate DESC"));
        }

        return supplierProductList;
    }

    public static List<GenericValue> getSupplierProductByParty(String partyId, Delegator delegator) throws GenericEntityException {
        //Debug.logInfo("partyId: " + partyId, module);
        List<EntityExpr> exprs = FastList.newInstance();
        if (UtilValidate.isNotEmpty(partyId)) {
            exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
        }
        List<GenericValue> supplierProductList = delegator.findList("SupplierProduct", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (supplierProductList != null) {

            supplierProductList = EntityUtil.orderBy(supplierProductList, UtilMisc.toList("availableFromDate DESC"));
        }

        return supplierProductList;
    }

    public static List<GenericValue> getProductPriceByProductPriceType(String productId, String productPriceType, Delegator delegator) throws GenericEntityException {

        List<EntityExpr> exprs = FastList.newInstance();
        if (productId != null) {
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
        }
        if (productPriceType != null) {
            exprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, productPriceType));
        }
        List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        if (priceList != null) {
            //            priceList = EntityUtil.filterByDate(priceList);            
            priceList = EntityUtil.orderBy(priceList, UtilMisc.toList("fromDate DESC", "thruDate DESC"));
        }
        return priceList;
    }

    public static void productPriceSave(ProductInventoryPojo entry, String currency, Delegator delegator) {

        try {

            if (entry.isPriceModiefied) {

                //close all other list prices
                PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_LISTPRICE,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), delegator);

                //create new one
                GenericValue listToStore = PosProductHelper.createProductPrice(
                        entry.productId, PosProductHelper.ProductPriceTypeId_LISTPRICE,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, currency,
                        UtilDateTime.nowTimestamp(), entry.price, delegator);

                delegator.create(listToStore);

            }

            if (entry.isDefaultPriceModified) {

                //close all other list prices
                PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), delegator);

                //create new one
                GenericValue listToStore = PosProductHelper.createProductPrice(
                        entry.productId, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, currency,
                        UtilDateTime.nowTimestamp(), entry.defaultPrice, delegator);

                delegator.create(listToStore);

            }

            if (entry.isPurchasePriceModified) {

                //close all other list prices
                PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_AVERAGECOST,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), delegator);

                //create new one
                GenericValue listToStore = PosProductHelper.createProductPrice(
                        entry.productId, PosProductHelper.ProductPriceTypeId_AVERAGECOST,
                        PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, currency,
                        UtilDateTime.nowTimestamp(), entry.purchasePrice, delegator);

                delegator.create(listToStore);

            }
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
        }
    }

    public static ProductInventoryPojo getProductAndPriceDetails(String productId, Delegator delegator) {

        //send and empty object
        ProductInventoryPojo curryProductPojo = new ProductInventoryPojo();

        try {

            GenericValue product = delegator.findOne("Product", UtilMisc.toMap("productId", productId), false);

            if (product != null) {

                curryProductPojo.productId = productId;
                curryProductPojo.description = product.getString("description");
                curryProductPojo.productName = product.getString("productName");
                curryProductPojo.internalName = product.getString("internalName");
                curryProductPojo.brandName = product.getString("brandName");

                //get price details
                PosProductHelper.LoadPriceList(curryProductPojo, delegator);

                //get last supplier details
                List<GenericValue> supplierProductList = product.getRelated("SupplierProduct");
                List<GenericValue> orderedSupplierProductList = EntityUtil.orderBy(supplierProductList,
                        UtilMisc.toList("createdStamp DESC"));
                GenericValue supplierProduct = null;
                if (orderedSupplierProductList.size() > 0) {
                    supplierProduct = orderedSupplierProductList.get(0);
                    curryProductPojo.supplierProductId = supplierProduct.getString("supplierProductId");
                    curryProductPojo.supplierPartyId = supplierProduct.getString("partyId");
                    curryProductPojo.supplierLastPrice = supplierProduct.getBigDecimal("lastPrice");
                }

                //get scan code details
                curryProductPojo.goodIdentificationTypeId1 = PosProductHelper.GoodIdentificationTypeIdEAN;

                GenericValue eanVal = PosProductHelper.getGoodIdentification(productId, PosProductHelper.GoodIdentificationTypeIdEAN, delegator);
                if (eanVal != null) {
                    curryProductPojo.eanValue = eanVal.getString("idValue");
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return curryProductPojo;
    }
//--------------------------------------------------------------------------------------------------//
//		payments
//--------------------------------------------------------------------------------------------------//

    public static GenericValue createPayment(String referenceNumber, BigDecimal notApplied, String partyIdFrom, String partyIdTo, Delegator delegator) {

        FastMap<String, Object> input = FastMap.newInstance();
        String paymentId = delegator.getNextSeqId("Payment");
        input.put("paymentId", paymentId);
        input.put("paymentTypeId", "VENDOR_PAYMENT");
        input.put("paymentMethodTypeId", "CASH");
        input.put("partyIdFrom", partyIdFrom);
        input.put("partyIdTo", partyIdTo);
        input.put("roleTypeIdTo", "MANAGER");
        input.put("statusId", "PMNT_CONFIRMED");
        input.put("effectiveDate", UtilDateTime.nowTimestamp());
        input.put("paymentRefNum", referenceNumber);
        input.put("amount", notApplied);
        input.put("currencyUomId", "AUD");
        input.put("actualCurrencyUomId", "AUD");

        GenericValue payment = delegator.makeValue("Payment", input);
        try {
            delegator.create(payment);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return payment;
    }

    public static boolean createPaymentApplication(String paymentId, String invoiceId, String referenceNumber, BigDecimal notApplied, GenericValue userLogin, LocalDispatcher dispatcher) {
        boolean result = true;
        Map<String, Object> appl = FastMap.newInstance();
        appl.put("paymentId", paymentId);
        appl.put("invoiceId", invoiceId);
//     appl.put("billingAccountId", supplierPartyId);
        appl.put("amountApplied", notApplied);
        appl.put("userLogin", userLogin);
        Map<String, Object> createPayApplResult;
        try {
            createPayApplResult = dispatcher.runSync("createPaymentApplication", appl);
            if (ServiceUtil.isError(createPayApplResult)) {
                //Debug.logInfo("create payment app failed", module);
                result = false;
            }
        } catch (GenericServiceException e) {
            result = false;
            Debug.logError(e, module);
        }

        return result;
    }

    static public List<GenericValue> findInvoiceFromExternalInvoiceNumber(String supplierId, String reference, Delegator delegator) {

        EntityListIterator entityListIterator = null;
        boolean beganTx = false;
        String productId = null;
        List<GenericValue> orderList = FastList.newInstance();
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        ArrayList<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
            // begin the transaction
            beganTx = TransactionUtil.begin(7200);
            try {

                List<String> orderBy = FastList.newInstance();
                Set<String> fieldsToSelect = FastSet.newInstance();
                List<String> conditionToSelect = FastList.newInstance();
                // fields we need to select; will be used to set distinct
                fieldsToSelect.add("invoiceId");
                //                 fieldsToSelect.add("orderName");
                fieldsToSelect.add("statusId");

                orderBy.add("invoiceId");
                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("referenceNumber", EntityOperator.EQUALS, reference));
                exprs.add(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, "PURCHASE_INVOICE"));
                exprs.add(EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, supplierId));

                EntityCondition cond = EntityCondition.makeCondition(exprs, EntityOperator.AND);
                EntityFindOptions efo = new EntityFindOptions();
                efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

                entityListIterator = delegator.find("Invoice", cond, null, fieldsToSelect, orderBy, efo);
                /*
                 String supplierId = (String) lpSupplierCombo.getSelectedItem();
                 if (UtilValidate.isNotEmpty(supplierId)) {
                 supplierId = supplierListBidingCombo.get(lpSupplierCombo.getSelectedIndex());	        	
                 }                 
                 */

            } catch (GenericEntityException gee) {
                Debug.logWarning(gee, gee.getMessage(), module);
                Map<String, String> messageMap = UtilMisc.toMap("gee", gee.toString());
                throw gee;
            }

            GenericValue invoice;
            while ((invoice = entityListIterator.next()) != null) {

                if (invoice.getString("invoiceId") != null) {
                    orderList.add(invoice);
                    Debug.logError("Invoice found: " + invoice.getString("invoiceId"), module);
                }
            }

        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTx, e.getMessage(), e);
            } catch (Exception e1) {
                Debug.logError(e1, module);
            }
        } catch (Throwable t) {
            Debug.logError(t, module);
            try {
                TransactionUtil.rollback(beganTx, t.getMessage(), t);
            } catch (Exception e2) {
                Debug.logError(e2, module);
            }

        } finally {
            if (entityListIterator != null) {
                try {
                    entityListIterator.close();
                } catch (GenericEntityException gee) {
                    Debug.logError(gee, "Error closing EntityListIterator when indexing product keywords.", module);
                }
            }

            // commit the transaction
            try {
                TransactionUtil.commit(beganTx);
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        return orderList;
    }

    static public ProductInventoryPojo loadProductPriceAndQty(String prodId, String facilityId, Delegator delegator, LocalDispatcher dispatcher) {

        //              ProductInventoryPojo pojo = new ProductInventoryPojo();
        //              pojo.productId = prodId;
//                pojo.internalName = prodName;
        ProductInventoryPojo pojo = null;
        try {

            //PosProductHelper.LoadPriceList(pojo, session.getDelegator());
            pojo = PosProductHelper.getProductAndPriceDetails(prodId, delegator);

            Map<String, Object> inventoryMap = PosProductHelper.getInventoryAvailableByFacility(prodId, facilityId, dispatcher);
            BigDecimal availableToPromiseTotal = (BigDecimal) inventoryMap.get("availableToPromiseTotal");
            BigDecimal quantityOnHandTotal = (BigDecimal) inventoryMap.get("quantityOnHandTotal");
            if (availableToPromiseTotal != null && quantityOnHandTotal != null) {
                pojo.quantityOnHand = quantityOnHandTotal;
                pojo.minimumStock = quantityOnHandTotal;
                pojo.availableToPromise = quantityOnHandTotal;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return pojo;
    }

    static public GenericValue getPerson(String partyId, Delegator delegator) {
        GenericValue partyVal = null;
        try {
            partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", partyId));

        } catch (GenericEntityException ex) {
            Logger.getLogger(PosProductHelper.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return partyVal;
    }
    // check if product already exists in database

    public static GenericValue getPartyGroup(String partyId,
            Delegator delegator) {

        GenericValue tmpPartyGroup = null;

        try {
            tmpPartyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc
                    .toMap("partyId", partyId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product group", module);
        }

        return tmpPartyGroup;
    }

    // check if party already exists in database
    public static GenericValue getParty(String partyId, Delegator delegator) {

        GenericValue tmpPartyGroup = null;

        try {
            tmpPartyGroup = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data from party", module);
        }

        return tmpPartyGroup;
    }

    static public Map<String, Object> createNewContectMech(String partyId, String contactMechPurposeId, String contactMechTypeId,
            GenericValue detailValue, Delegator delegator) {

        Map<String, Object> partyContactMechValueMap = FastMap.newInstance();
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            //Debug.logInfo("createNewContectMech contactMechPurposeId: " + contactMechPurposeId, "purposeId");
            try {
                String newCmId = null;
                try {
                    newCmId = delegator.getNextSeqId("ContactMech");
                } catch (IllegalArgumentException e) {
                }

                GenericValue contactMechType = delegator.findByPrimaryKey("ContactMechType", UtilMisc.toMap("contactMechTypeId", contactMechTypeId));

                String infoString = null;
                if (detailValue != null && detailValue.getEntityName().equals("ContactMech") == false) {
                    infoString = detailValue.getString("infoString");
                }

                GenericValue contactMech = delegator.create("ContactMech", UtilMisc.toMap("contactMechId", newCmId,
                        "contactMechTypeId", contactMechTypeId,
                        "infoString", infoString));

                // no entry with a valid date range exists, create new with open thruDate
                java.sql.Timestamp fromDate = UtilDateTime.nowTimestamp();
                GenericValue partyContactMech = delegator.makeValue("PartyContactMech", UtilMisc.toMap("partyId", partyId, "contactMechId", newCmId, "fromDate", fromDate));
                delegator.create(partyContactMech);

                // no entry with a valid date range exists, create new with open thruDate
                GenericValue partyContactMechPurpose = delegator.makeValue("PartyContactMechPurpose",
                        UtilMisc.toMap("partyId", partyId, "contactMechId", newCmId, "contactMechPurposeTypeId", contactMechPurposeId,
                                "fromDate", fromDate));

                delegator.create(partyContactMechPurpose);

                List<GenericValue> partyContactMechPurposes = new ArrayList<GenericValue>();
                partyContactMechPurposes.add(partyContactMechPurpose);
                if (detailValue != null && detailValue.getEntityName().equals("ContactMech") == false) {
                    detailValue.set("contactMechId", newCmId);
                    delegator.create(detailValue);
                }
                partyContactMechValueMap.put("contactMech", contactMech);
                partyContactMechValueMap.put("partyContactMech", partyContactMech);
                partyContactMechValueMap.put("contactMechType", contactMechType);
                partyContactMechValueMap.put("partyContactMechPurposes", partyContactMechPurposes);
//                            partyContactMechValueMap.put("telecomNumber", telGenric);

            } catch (GenericEntityException ex) {
                Debug.logError(ex, module);
            }

        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return partyContactMechValueMap;
    }

    public static List<GenericValue> getOrderHeader(String partyId, String orderId, Delegator delegator) {
        List<GenericValue> invList = new ArrayList<GenericValue>();
        try {
            List<EntityExpr> exprs = FastList.newInstance();
            //Debug.logInfo(" partyId: " + partyId + " orderId: " + orderId, module);
            if (partyId != null) {
                exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
            }
            if (orderId != null) {
                exprs.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
            }

            invList = delegator.findList("OrderHeaderAndRoles", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true), false);

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", module);
        }

        return invList;
    }

    static public SupplierProductAndListPriceData getOrderItemDetails(String prodId, String facilityId, XuiSession session) {

        SupplierProductAndListPriceData ppData = null;
        try {
            //		Debug.logError("1", module);
            try {
                Map<String, Object> genValueMap = getPriceListAndProductDetails(prodId, session.getDelegator());
                LocalDispatcher dispatcher = XuiContainer.getSession().getDispatcher();

                Map<String, Object> inventoryMap = PosProductHelper.getInventoryAvailableByFacility(prodId, facilityId, dispatcher);

                ppData = new SupplierProductAndListPriceData(genValueMap);
                ppData.setInventoryMap(inventoryMap);

            } catch (GenericEntityException ex) {
                Logger.getLogger(PosProductHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (HeadlessException e1) {
            // TODO Auto-generated catch block
//                e1.printStackTrace();
            Debug.logError(e1, module);
        }
        return ppData;
    }
}

/*
 <simple-method method-name="updateIssuanceShipmentAndPoOnReceiveInventory" short-description="Update issuance, shipment and order items if quantity received is higher than quantity on purchase order">
 <entity-one value-field="orderItem" entity-name="OrderItem"/>
 <if-not-empty field="parameters.orderCurrencyUnitPrice">
 <if-compare-field field="parameters.orderCurrencyUnitPrice" operator="not-equals" to-field="orderItem.unitPrice" type="BigDecimal">
 <set field="orderItem.unitPrice" from-field="parameters.orderCurrencyUnitPrice" type="BigDecimal"/>
 <store-value value-field="orderItem"/>
 </if-compare-field>
 <else>
 <if-compare-field field="parameters.unitCost" operator="not-equals" to-field="orderItem.unitPrice" type="BigDecimal">
 <set field="orderItem.unitPrice" from-field="parameters.unitCost" type="BigDecimal"/>
 <store-value value-field="orderItem"/>
 </if-compare-field>
 </else>
 </if-not-empty>
 <call-simple-method method-name="getReceivedQuantityForOrderItem"/>
 <if-compare-field field="orderItem.quantity" operator="less" to-field="receivedQuantity" type="BigDecimal">
 <set field="orderItem.quantity" from-field="receivedQuantity"/>
 <store-value value-field="orderItem"/>
 </if-compare-field>
 <if-not-empty field="parameters.shipmentId">
 <if-not-empty field="orderItem.productId">
 <call-simple-method method-name="getTotalIssuedQuantityForOrderItem" xml-resource="component://product/script/org/ofbiz/shipment/issuance/IssuanceServices.xml"/>
 <if-compare-field field="totalIssuedQuantity" operator="less" to-field="receivedQuantity" type="BigDecimal">
 <set field="quantityToAdd" value="${receivedQuantity$bigDecimal - totalIssuedQuantity$bigDecimal}" type="BigDecimal"/>
 <entity-condition entity-name="ShipmentItem" list="shipmentItems">
 <condition-list combine="and">
 <condition-expr field-name="productId" from-field="orderItem.productId"/>
 <condition-expr field-name="shipmentId" from-field="parameters.shipmentId"/>
 <condition-expr field-name="shipmentItemSeqId" from-field="parameters.shipmentItemSeqId" ignore-if-empty="true"/>
 </condition-list>
 <order-by field-name="shipmentItemSeqId"/>
 </entity-condition>
 <first-from-list entry="shipmentItem" list="shipmentItems"/>
 <set field="shipmentItem.quantity" value="${shipmentItem.quantity$bigDecimal + quantityToAdd$bigDecimal}" type="BigDecimal"/>
 <store-value value-field="shipmentItem"/>
                    
 <entity-and list="orderShipments" entity-name="OrderShipment">
 <field-map field-name="orderId" from-field="parameters.orderId"/>
 <field-map field-name="orderItemSeqId" from-field="parameters.orderItemSeqId"/>
 <field-map field-name="shipmentId" from-field="parameters.shipmentId"/>
 <field-map field-name="shipmentItemSeqId" from-field="shipmentItem.shipmentItemSeqId"/>
 </entity-and>
 <first-from-list entry="orderShipment" list="orderShipments"/>
 <set field="orderShipment.quantity" value="${orderShipment.quantity$bigDecimal + quantityToAdd$bigDecimal}" type="BigDecimal"/>
 <store-value value-field="orderShipment"/>
 <!--
 TODO: if we want to record the role of the facility operation we have to re-implement this using ShipmentReceiptRole
 <set field="itemIssuanceId" from-field="itemIssuance.itemIssuanceId"/>
 <call-simple-method method-name="associateIssueRoles" xml-resource="component://product/script/org/ofbiz/shipment/issuance/IssuanceServices.xml"/>
 -->
 </if-compare-field>
 </if-not-empty>
 </if-not-empty>
 </simple-method>
 */
