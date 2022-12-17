package org.ofbiz.ordermax.entity;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryItemAndLocation implements GenericValueObjectInterface {

    public static final String module = InventoryItemAndLocation.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InventoryItemAndLocation(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public InventoryItemAndLocation() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productHeight", "Product Height"},
        {"datetimeManufactured", "Datetime Manufactured"},
        {"createdDate", "Created Date"},
        {"shippingWidth", "Shipping Width"},
        {"taxable", "Taxable"},
        {"currencyUomId", "Currency Uom Id"},
        {"salesDiscWhenNotAvail", "Sales Disc When Not Avail"},
        {"amountUomTypeId", "Amount Uom Type Id"},
        {"uomId", "Uom Id"},
        {"facilityId", "Facility Id"},
        {"configId", "Config Id"},
        {"productRating", "Product Rating"},
        {"lastModifiedByUserLogin", "Last Modified By User Login"},
        {"partyId", "Party Id"},
        {"fixedAmount", "Fixed Amount"},
        {"expireDate", "Expire Date"},
        {"softIdentifier", "Soft Identifier"},
        {"activationNumber", "Activation Number"},
        {"returnable", "Returnable"},
        {"primaryProductCategoryId", "Primary Product Category Id"},
        {"depthUomId", "Depth Uom Id"},
        {"piecesIncluded", "Pieces Included"},
        {"locationTypeEnumId", "Location Type Enum Id"},
        {"levelId", "Level Id"},
        {"chargeShipping", "Charge Shipping"},
        {"includeInPromotions", "Include In Promotions"},
        {"originalImageUrl", "Original Image Url"},
        {"detailScreen", "Detail Screen"},
        {"requireAmount", "Require Amount"},
        {"inventoryItemTypeId", "Inventory Item Type Id"},
        {"productDepth", "Product Depth"},
        {"shippingDepth", "Shipping Depth"},
        {"lotId", "Lot Id"},
        {"largeImageUrl", "Large Image Url"},
        {"originGeoId", "Origin Geo Id"},
        {"requireInventory", "Require Inventory"},
        {"ownerPartyId", "Owner Party Id"},
        {"productDiameter", "Product Diameter"},
        {"quantityOnHandTotal", "Quantity On Hand Total"},
        {"orderDecimalQuantity", "Order Decimal Quantity"},
        {"lotIdFilledIn", "Lot Id Filled In"},
        {"sectionId", "Section Id"},
        {"positionId", "Position Id"},
        {"productWeight", "Product Weight"},
        {"diameterUomId", "Diameter Uom Id"},
        {"shippingHeight", "Shipping Height"},
        {"comments", "Comments"},
        {"isVariant", "Is Variant"},
        {"internalName", "Internal Name"},
        {"serialNumber", "Serial Number"},
        {"reservNthPPPerc", "Reserv Nth Pp Perc"},
        {"availableToPromiseTotal", "Available To Promise Total"},
        {"activationValidThru", "Activation Valid Thru"},
        {"locationSeqId", "Location Seq Id"},
        {"brandName", "Brand Name"},
        {"billOfMaterialLevel", "Bill Of Material Level"},
        {"quantityIncluded", "Quantity Included"},
        {"description", "Description"},
        {"longDescription", "Long Description"},
        {"heightUomId", "Height Uom Id"},
        {"reservMaxPersons", "Reserv Max Persons"},
        {"widthUomId", "Width Uom Id"},
        {"binNumber", "Bin Number"},
        {"inventoryItemId", "Inventory Item Id"},
        {"mediumImageUrl", "Medium Image Url"},
        {"autoCreateKeywords", "Auto Create Keywords"},
        {"oldAvailableToPromise", "Old Available To Promise"},
        {"ratingTypeEnum", "Rating Type Enum"},
        {"aisleId", "Aisle Id"},
        {"datetimeReceived", "Datetime Received"},
        {"containerId", "Container Id"},
        {"requirementMethodEnumId", "Requirement Method Enum Id"},
        {"quantityUomId", "Quantity Uom Id"},
        {"productTypeId", "Product Type Id"},
        {"virtualVariantMethodEnum", "Virtual Variant Method Enum"},
        {"weight", "Weight"},
        {"salesDiscontinuationDate", "Sales Discontinuation Date"},
        {"supportDiscontinuationDate", "Support Discontinuation Date"},
        {"areaId", "Area Id"},
        {"statusId", "Status Id"},
        {"oldQuantityOnHand", "Old Quantity On Hand"},
        {"introductionDate", "Introduction Date"},
        {"releaseDate", "Release Date"},
        {"unitCost", "Unit Cost"},
        {"inShippingBox", "In Shipping Box"},
        {"reserv2ndPPPerc", "Reserv 2Nd Pp Perc"},
        {"fixedAssetId", "Fixed Asset Id"},
        {"detailImageUrl", "Detail Image Url"},
        {"productWidth", "Product Width"},
        {"weightUomId", "Weight Uom Id"},
        {"manufacturerPartyId", "Manufacturer Party Id"},
        {"accountingQuantityTotal", "Accounting Quantity Total"},
        {"smallImageUrl", "Small Image Url"},
        {"isVirtual", "Is Virtual"},
        {"priceDetailText", "Price Detail Text"},
        {"defaultShipmentBoxTypeId", "Default Shipment Box Type Id"},
        {"productId", "Product Id"},
        {"lastModifiedDate", "Last Modified Date"},
        {"inventoryMessage", "Inventory Message"},
        {"productName", "Product Name"},
        {"createdByUserLogin", "Created By User Login"},};

    protected void initObject() {
        this.productHeight = java.math.BigDecimal.ZERO;
        this.datetimeManufactured = "";
        this.createdDate = UtilDateTime.nowTimestamp();
        this.shippingWidth = "";
        this.taxable = "";
        this.currencyUomId = "";
        this.salesDiscWhenNotAvail = "";
        this.amountUomTypeId = "";
        this.uomId = "";
        this.facilityId = "";
        this.configId = "";
        this.productRating = "";
        this.lastModifiedByUserLogin = "";
        this.partyId = "";
        this.fixedAmount = "";
        this.expireDate = UtilDateTime.nowTimestamp();
        this.softIdentifier = "";
        this.activationNumber = "";
        this.returnable = "";
        this.primaryProductCategoryId = "";
        this.depthUomId = "";
        this.piecesIncluded = "";
        this.locationTypeEnumId = "";
        this.levelId = "";
        this.chargeShipping = "";
        this.includeInPromotions = "";
        this.originalImageUrl = "";
        this.detailScreen = "";
        this.requireAmount = "";
        this.inventoryItemTypeId = "";
        this.productDepth = "";
        this.shippingDepth = "";
        this.lotId = "";
        this.largeImageUrl = "";
        this.originGeoId = "";
        this.requireInventory = "";
        this.ownerPartyId = "";
        this.productDiameter = "";
        this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
        this.orderDecimalQuantity = "";
        this.lotIdFilledIn = "";
        this.sectionId = "";
        this.positionId = "";
        this.productWeight = null;
        this.diameterUomId = "";
        this.shippingHeight = "";
        this.comments = "";
        this.isVariant = "";
        this.internalName = "";
        this.serialNumber = "";
        this.reservNthPPPerc = "";
        this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
        this.activationValidThru = "";
        this.locationSeqId = "";
        this.brandName = "";
        this.billOfMaterialLevel = null;
        this.quantityIncluded = "";
        this.description = "";
        this.longDescription = "";
        this.heightUomId = "";
        this.reservMaxPersons = "";
        this.widthUomId = "";
        this.binNumber = "";
        this.inventoryItemId = "";
        this.mediumImageUrl = "";
        this.autoCreateKeywords = "";
        this.oldAvailableToPromise = "";
        this.ratingTypeEnum = "";
        this.aisleId = "";
        this.datetimeReceived = UtilDateTime.nowTimestamp();
        this.containerId = "";
        this.requirementMethodEnumId = "";
        this.quantityUomId = "";
        this.productTypeId = "";
        this.virtualVariantMethodEnum = "";
        this.weight = null;
        this.salesDiscontinuationDate = UtilDateTime.nowTimestamp();
        this.supportDiscontinuationDate = UtilDateTime.nowTimestamp();
        this.areaId = "";
        this.statusId = "";
        this.oldQuantityOnHand = "";
        this.introductionDate = UtilDateTime.nowTimestamp();
        this.releaseDate = UtilDateTime.nowTimestamp();
        this.unitCost = java.math.BigDecimal.ZERO;
        this.inShippingBox = "";
        this.reserv2ndPPPerc = "";
        this.fixedAssetId = "";
        this.detailImageUrl = "";
        this.productWidth = "";
        this.weightUomId = "";
        this.manufacturerPartyId = "";
        this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
        this.smallImageUrl = "";
        this.isVirtual = "";
        this.priceDetailText = "";
        this.defaultShipmentBoxTypeId = "";
        this.productId = "";
        this.lastModifiedDate = UtilDateTime.nowTimestamp();
        this.inventoryMessage = "";
        this.productName = "";
        this.createdByUserLogin = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productHeight = (java.math.BigDecimal) genVal.get("productHeight");
        this.datetimeManufactured = (java.lang.String) genVal.get("datetimeManufactured");
        this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
        this.shippingWidth = (java.lang.String) genVal.get("shippingWidth");
        this.taxable = (java.lang.String) genVal.get("taxable");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.salesDiscWhenNotAvail = (java.lang.String) genVal.get("salesDiscWhenNotAvail");
        this.amountUomTypeId = (java.lang.String) genVal.get("amountUomTypeId");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.configId = (java.lang.String) genVal.get("configId");
        this.productRating = (java.lang.String) genVal.get("productRating");
        this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.fixedAmount = (java.lang.String) genVal.get("fixedAmount");
        this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
        this.softIdentifier = (java.lang.String) genVal.get("softIdentifier");
        this.activationNumber = (java.lang.String) genVal.get("activationNumber");
        this.returnable = (java.lang.String) genVal.get("returnable");
        this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
        this.depthUomId = (java.lang.String) genVal.get("depthUomId");
        this.piecesIncluded = (java.lang.String) genVal.get("piecesIncluded");
        this.locationTypeEnumId = (java.lang.String) genVal.get("locationTypeEnumId");
        this.levelId = (java.lang.String) genVal.get("levelId");
        this.chargeShipping = (java.lang.String) genVal.get("chargeShipping");
        this.includeInPromotions = (java.lang.String) genVal.get("includeInPromotions");
        this.originalImageUrl = (java.lang.String) genVal.get("originalImageUrl");
        this.detailScreen = (java.lang.String) genVal.get("detailScreen");
        this.requireAmount = (java.lang.String) genVal.get("requireAmount");
        this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
        this.productDepth = (java.lang.String) genVal.get("productDepth");
        this.shippingDepth = (java.lang.String) genVal.get("shippingDepth");
        this.lotId = (java.lang.String) genVal.get("lotId");
        this.largeImageUrl = (java.lang.String) genVal.get("largeImageUrl");
        this.originGeoId = (java.lang.String) genVal.get("originGeoId");
        this.requireInventory = (java.lang.String) genVal.get("requireInventory");
        this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
        this.productDiameter = (java.lang.String) genVal.get("productDiameter");
        this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
        this.orderDecimalQuantity = (java.lang.String) genVal.get("orderDecimalQuantity");
        this.lotIdFilledIn = (java.lang.String) genVal.get("lotIdFilledIn");
        this.sectionId = (java.lang.String) genVal.get("sectionId");
        this.positionId = (java.lang.String) genVal.get("positionId");
        this.productWeight = (java.math.BigDecimal) genVal.get("productWeight");
        this.diameterUomId = (java.lang.String) genVal.get("diameterUomId");
        this.shippingHeight = (java.lang.String) genVal.get("shippingHeight");
        this.comments = (java.lang.String) genVal.get("comments");
        this.isVariant = (java.lang.String) genVal.get("isVariant");
        this.internalName = (java.lang.String) genVal.get("internalName");
        this.serialNumber = (java.lang.String) genVal.get("serialNumber");
        this.reservNthPPPerc = (java.lang.String) genVal.get("reservNthPPPerc");
        this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
        this.activationValidThru = (java.lang.String) genVal.get("activationValidThru");
        this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
        this.brandName = (java.lang.String) genVal.get("brandName");
        this.billOfMaterialLevel = (java.lang.Long) genVal.get("billOfMaterialLevel");
        this.quantityIncluded = (java.lang.String) genVal.get("quantityIncluded");
        this.description = (java.lang.String) genVal.get("description");
        this.longDescription = (java.lang.String) genVal.get("longDescription");
        this.heightUomId = (java.lang.String) genVal.get("heightUomId");
        this.reservMaxPersons = (java.lang.String) genVal.get("reservMaxPersons");
        this.widthUomId = (java.lang.String) genVal.get("widthUomId");
        this.binNumber = (java.lang.String) genVal.get("binNumber");
        this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
        this.mediumImageUrl = (java.lang.String) genVal.get("mediumImageUrl");
        this.autoCreateKeywords = (java.lang.String) genVal.get("autoCreateKeywords");
        this.oldAvailableToPromise = (java.lang.String) genVal.get("oldAvailableToPromise");
        this.ratingTypeEnum = (java.lang.String) genVal.get("ratingTypeEnum");
        this.aisleId = (java.lang.String) genVal.get("aisleId");
        this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
        this.containerId = (java.lang.String) genVal.get("containerId");
        this.requirementMethodEnumId = (java.lang.String) genVal.get("requirementMethodEnumId");
        this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
        this.productTypeId = (java.lang.String) genVal.get("productTypeId");
        this.virtualVariantMethodEnum = (java.lang.String) genVal.get("virtualVariantMethodEnum");
        this.weight = (java.math.BigDecimal) genVal.get("weight");
        this.salesDiscontinuationDate = (java.sql.Timestamp) genVal.get("salesDiscontinuationDate");
        this.supportDiscontinuationDate = (java.sql.Timestamp) genVal.get("supportDiscontinuationDate");
        this.areaId = (java.lang.String) genVal.get("areaId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.oldQuantityOnHand = (java.lang.String) genVal.get("oldQuantityOnHand");
        this.introductionDate = (java.sql.Timestamp) genVal.get("introductionDate");
        this.releaseDate = (java.sql.Timestamp) genVal.get("releaseDate");
        this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
        this.inShippingBox = (java.lang.String) genVal.get("inShippingBox");
        this.reserv2ndPPPerc = (java.lang.String) genVal.get("reserv2ndPPPerc");
        this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
        this.detailImageUrl = (java.lang.String) genVal.get("detailImageUrl");
        this.productWidth = (java.lang.String) genVal.get("productWidth");
        this.weightUomId = (java.lang.String) genVal.get("weightUomId");
        this.manufacturerPartyId = (java.lang.String) genVal.get("manufacturerPartyId");
        this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
        this.smallImageUrl = (java.lang.String) genVal.get("smallImageUrl");
        this.isVirtual = (java.lang.String) genVal.get("isVirtual");
        this.priceDetailText = (java.lang.String) genVal.get("priceDetailText");
        this.defaultShipmentBoxTypeId = (java.lang.String) genVal.get("defaultShipmentBoxTypeId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
        this.inventoryMessage = (java.lang.String) genVal.get("inventoryMessage");
        this.productName = (java.lang.String) genVal.get("productName");
        this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productHeight", OrderMaxUtility.getValidEntityBigDecimal(this.productHeight));
        val.set("datetimeManufactured", OrderMaxUtility.getValidEntityString(this.datetimeManufactured));
        val.set("createdDate", OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
        val.set("shippingWidth", OrderMaxUtility.getValidEntityString(this.shippingWidth));
        val.set("taxable", OrderMaxUtility.getValidEntityString(this.taxable));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("salesDiscWhenNotAvail", OrderMaxUtility.getValidEntityString(this.salesDiscWhenNotAvail));
        val.set("amountUomTypeId", OrderMaxUtility.getValidEntityString(this.amountUomTypeId));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("configId", OrderMaxUtility.getValidEntityString(this.configId));
        val.set("productRating", OrderMaxUtility.getValidEntityString(this.productRating));
        val.set("lastModifiedByUserLogin", OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("fixedAmount", OrderMaxUtility.getValidEntityString(this.fixedAmount));
        val.set("expireDate", OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
        val.set("softIdentifier", OrderMaxUtility.getValidEntityString(this.softIdentifier));
        val.set("activationNumber", OrderMaxUtility.getValidEntityString(this.activationNumber));
        val.set("returnable", OrderMaxUtility.getValidEntityString(this.returnable));
        val.set("primaryProductCategoryId", OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
        val.set("depthUomId", OrderMaxUtility.getValidEntityString(this.depthUomId));
        val.set("piecesIncluded", OrderMaxUtility.getValidEntityString(this.piecesIncluded));
        val.set("locationTypeEnumId", OrderMaxUtility.getValidEntityString(this.locationTypeEnumId));
        val.set("levelId", OrderMaxUtility.getValidEntityString(this.levelId));
        val.set("chargeShipping", OrderMaxUtility.getValidEntityString(this.chargeShipping));
        val.set("includeInPromotions", OrderMaxUtility.getValidEntityString(this.includeInPromotions));
        val.set("originalImageUrl", OrderMaxUtility.getValidEntityString(this.originalImageUrl));
        val.set("detailScreen", OrderMaxUtility.getValidEntityString(this.detailScreen));
        val.set("requireAmount", OrderMaxUtility.getValidEntityString(this.requireAmount));
        val.set("inventoryItemTypeId", OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
        val.set("productDepth", OrderMaxUtility.getValidEntityString(this.productDepth));
        val.set("shippingDepth", OrderMaxUtility.getValidEntityString(this.shippingDepth));
        val.set("lotId", OrderMaxUtility.getValidEntityString(this.lotId));
        val.set("largeImageUrl", OrderMaxUtility.getValidEntityString(this.largeImageUrl));
        val.set("originGeoId", OrderMaxUtility.getValidEntityString(this.originGeoId));
        val.set("requireInventory", OrderMaxUtility.getValidEntityString(this.requireInventory));
        val.set("ownerPartyId", OrderMaxUtility.getValidEntityString(this.ownerPartyId));
        val.set("productDiameter", OrderMaxUtility.getValidEntityString(this.productDiameter));
        val.set("quantityOnHandTotal", OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
        val.set("orderDecimalQuantity", OrderMaxUtility.getValidEntityString(this.orderDecimalQuantity));
        val.set("lotIdFilledIn", OrderMaxUtility.getValidEntityString(this.lotIdFilledIn));
        val.set("sectionId", OrderMaxUtility.getValidEntityString(this.sectionId));
        val.set("positionId", OrderMaxUtility.getValidEntityString(this.positionId));
        val.set("productWeight", OrderMaxUtility.getValidBigDecimal(this.productWeight));
        val.set("diameterUomId", OrderMaxUtility.getValidEntityString(this.diameterUomId));
        val.set("shippingHeight", OrderMaxUtility.getValidEntityString(this.shippingHeight));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
        val.set("isVariant", OrderMaxUtility.getValidEntityString(this.isVariant));
        val.set("internalName", OrderMaxUtility.getValidEntityString(this.internalName));
        val.set("serialNumber", OrderMaxUtility.getValidEntityString(this.serialNumber));
        val.set("reservNthPPPerc", OrderMaxUtility.getValidEntityString(this.reservNthPPPerc));
        val.set("availableToPromiseTotal", OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
        val.set("activationValidThru", OrderMaxUtility.getValidEntityString(this.activationValidThru));
        val.set("locationSeqId", OrderMaxUtility.getValidEntityString(this.locationSeqId));
        val.set("brandName", OrderMaxUtility.getValidEntityString(this.brandName));
        val.set("billOfMaterialLevel", OrderMaxUtility.getValidEntityLong(this.billOfMaterialLevel));
        val.set("quantityIncluded", OrderMaxUtility.getValidEntityString(this.quantityIncluded));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("longDescription", OrderMaxUtility.getValidEntityString(this.longDescription));
        val.set("heightUomId", OrderMaxUtility.getValidEntityString(this.heightUomId));
        val.set("reservMaxPersons", OrderMaxUtility.getValidEntityString(this.reservMaxPersons));
        val.set("widthUomId", OrderMaxUtility.getValidEntityString(this.widthUomId));
        val.set("binNumber", OrderMaxUtility.getValidEntityString(this.binNumber));
        val.set("inventoryItemId", OrderMaxUtility.getValidEntityString(this.inventoryItemId));
        val.set("mediumImageUrl", OrderMaxUtility.getValidEntityString(this.mediumImageUrl));
        val.set("autoCreateKeywords", OrderMaxUtility.getValidEntityString(this.autoCreateKeywords));
        val.set("oldAvailableToPromise", OrderMaxUtility.getValidEntityString(this.oldAvailableToPromise));
        val.set("ratingTypeEnum", OrderMaxUtility.getValidEntityString(this.ratingTypeEnum));
        val.set("aisleId", OrderMaxUtility.getValidEntityString(this.aisleId));
        val.set("datetimeReceived", OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
        val.set("containerId", OrderMaxUtility.getValidEntityString(this.containerId));
        val.set("requirementMethodEnumId", OrderMaxUtility.getValidEntityString(this.requirementMethodEnumId));
        val.set("quantityUomId", OrderMaxUtility.getValidEntityString(this.quantityUomId));
        val.set("productTypeId", OrderMaxUtility.getValidEntityString(this.productTypeId));
        val.set("virtualVariantMethodEnum", OrderMaxUtility.getValidEntityString(this.virtualVariantMethodEnum));
        val.set("weight", OrderMaxUtility.getValidBigDecimal(this.weight));
        val.set("salesDiscontinuationDate", OrderMaxUtility.getValidEntityTimestamp(this.salesDiscontinuationDate));
        val.set("supportDiscontinuationDate", OrderMaxUtility.getValidEntityTimestamp(this.supportDiscontinuationDate));
        val.set("areaId", OrderMaxUtility.getValidEntityString(this.areaId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("oldQuantityOnHand", OrderMaxUtility.getValidEntityString(this.oldQuantityOnHand));
        val.set("introductionDate", OrderMaxUtility.getValidEntityTimestamp(this.introductionDate));
        val.set("releaseDate", OrderMaxUtility.getValidEntityTimestamp(this.releaseDate));
        val.set("unitCost", OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
        val.set("inShippingBox", OrderMaxUtility.getValidEntityString(this.inShippingBox));
        val.set("reserv2ndPPPerc", OrderMaxUtility.getValidEntityString(this.reserv2ndPPPerc));
        val.set("fixedAssetId", OrderMaxUtility.getValidEntityString(this.fixedAssetId));
        val.set("detailImageUrl", OrderMaxUtility.getValidEntityString(this.detailImageUrl));
        val.set("productWidth", OrderMaxUtility.getValidEntityString(this.productWidth));
        val.set("weightUomId", OrderMaxUtility.getValidEntityString(this.weightUomId));
        val.set("manufacturerPartyId", OrderMaxUtility.getValidEntityString(this.manufacturerPartyId));
        val.set("accountingQuantityTotal", OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
        val.set("smallImageUrl", OrderMaxUtility.getValidEntityString(this.smallImageUrl));
        val.set("isVirtual", OrderMaxUtility.getValidEntityString(this.isVirtual));
        val.set("priceDetailText", OrderMaxUtility.getValidEntityString(this.priceDetailText));
        val.set("defaultShipmentBoxTypeId", OrderMaxUtility.getValidEntityString(this.defaultShipmentBoxTypeId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("lastModifiedDate", OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
        val.set("inventoryMessage", OrderMaxUtility.getValidEntityString(this.inventoryMessage));
        val.set("productName", OrderMaxUtility.getValidEntityString(this.productName));
        val.set("createdByUserLogin", OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productHeight", this.productHeight);
        valueMap.put("datetimeManufactured", this.datetimeManufactured);
        valueMap.put("createdDate", this.createdDate);
        valueMap.put("shippingWidth", this.shippingWidth);
        valueMap.put("taxable", this.taxable);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("salesDiscWhenNotAvail", this.salesDiscWhenNotAvail);
        valueMap.put("amountUomTypeId", this.amountUomTypeId);
        valueMap.put("uomId", this.uomId);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("configId", this.configId);
        valueMap.put("productRating", this.productRating);
        valueMap.put("lastModifiedByUserLogin", this.lastModifiedByUserLogin);
        valueMap.put("partyId", this.partyId);
        valueMap.put("fixedAmount", this.fixedAmount);
        valueMap.put("expireDate", this.expireDate);
        valueMap.put("softIdentifier", this.softIdentifier);
        valueMap.put("activationNumber", this.activationNumber);
        valueMap.put("returnable", this.returnable);
        valueMap.put("primaryProductCategoryId", this.primaryProductCategoryId);
        valueMap.put("depthUomId", this.depthUomId);
        valueMap.put("piecesIncluded", this.piecesIncluded);
        valueMap.put("locationTypeEnumId", this.locationTypeEnumId);
        valueMap.put("levelId", this.levelId);
        valueMap.put("chargeShipping", this.chargeShipping);
        valueMap.put("includeInPromotions", this.includeInPromotions);
        valueMap.put("originalImageUrl", this.originalImageUrl);
        valueMap.put("detailScreen", this.detailScreen);
        valueMap.put("requireAmount", this.requireAmount);
        valueMap.put("inventoryItemTypeId", this.inventoryItemTypeId);
        valueMap.put("productDepth", this.productDepth);
        valueMap.put("shippingDepth", this.shippingDepth);
        valueMap.put("lotId", this.lotId);
        valueMap.put("largeImageUrl", this.largeImageUrl);
        valueMap.put("originGeoId", this.originGeoId);
        valueMap.put("requireInventory", this.requireInventory);
        valueMap.put("ownerPartyId", this.ownerPartyId);
        valueMap.put("productDiameter", this.productDiameter);
        valueMap.put("quantityOnHandTotal", this.quantityOnHandTotal);
        valueMap.put("orderDecimalQuantity", this.orderDecimalQuantity);
        valueMap.put("lotIdFilledIn", this.lotIdFilledIn);
        valueMap.put("sectionId", this.sectionId);
        valueMap.put("positionId", this.positionId);
        valueMap.put("productWeight", this.productWeight);
        valueMap.put("diameterUomId", this.diameterUomId);
        valueMap.put("shippingHeight", this.shippingHeight);
        valueMap.put("comments", this.comments);
        valueMap.put("isVariant", this.isVariant);
        valueMap.put("internalName", this.internalName);
        valueMap.put("serialNumber", this.serialNumber);
        valueMap.put("reservNthPPPerc", this.reservNthPPPerc);
        valueMap.put("availableToPromiseTotal", this.availableToPromiseTotal);
        valueMap.put("activationValidThru", this.activationValidThru);
        valueMap.put("locationSeqId", this.locationSeqId);
        valueMap.put("brandName", this.brandName);
        valueMap.put("billOfMaterialLevel", this.billOfMaterialLevel);
        valueMap.put("quantityIncluded", this.quantityIncluded);
        valueMap.put("description", this.description);
        valueMap.put("longDescription", this.longDescription);
        valueMap.put("heightUomId", this.heightUomId);
        valueMap.put("reservMaxPersons", this.reservMaxPersons);
        valueMap.put("widthUomId", this.widthUomId);
        valueMap.put("binNumber", this.binNumber);
        valueMap.put("inventoryItemId", this.inventoryItemId);
        valueMap.put("mediumImageUrl", this.mediumImageUrl);
        valueMap.put("autoCreateKeywords", this.autoCreateKeywords);
        valueMap.put("oldAvailableToPromise", this.oldAvailableToPromise);
        valueMap.put("ratingTypeEnum", this.ratingTypeEnum);
        valueMap.put("aisleId", this.aisleId);
        valueMap.put("datetimeReceived", this.datetimeReceived);
        valueMap.put("containerId", this.containerId);
        valueMap.put("requirementMethodEnumId", this.requirementMethodEnumId);
        valueMap.put("quantityUomId", this.quantityUomId);
        valueMap.put("productTypeId", this.productTypeId);
        valueMap.put("virtualVariantMethodEnum", this.virtualVariantMethodEnum);
        valueMap.put("weight", this.weight);
        valueMap.put("salesDiscontinuationDate", this.salesDiscontinuationDate);
        valueMap.put("supportDiscontinuationDate", this.supportDiscontinuationDate);
        valueMap.put("areaId", this.areaId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("oldQuantityOnHand", this.oldQuantityOnHand);
        valueMap.put("introductionDate", this.introductionDate);
        valueMap.put("releaseDate", this.releaseDate);
        valueMap.put("unitCost", this.unitCost);
        valueMap.put("inShippingBox", this.inShippingBox);
        valueMap.put("reserv2ndPPPerc", this.reserv2ndPPPerc);
        valueMap.put("fixedAssetId", this.fixedAssetId);
        valueMap.put("detailImageUrl", this.detailImageUrl);
        valueMap.put("productWidth", this.productWidth);
        valueMap.put("weightUomId", this.weightUomId);
        valueMap.put("manufacturerPartyId", this.manufacturerPartyId);
        valueMap.put("accountingQuantityTotal", this.accountingQuantityTotal);
        valueMap.put("smallImageUrl", this.smallImageUrl);
        valueMap.put("isVirtual", this.isVirtual);
        valueMap.put("priceDetailText", this.priceDetailText);
        valueMap.put("defaultShipmentBoxTypeId", this.defaultShipmentBoxTypeId);
        valueMap.put("productId", this.productId);
        valueMap.put("lastModifiedDate", this.lastModifiedDate);
        valueMap.put("inventoryMessage", this.inventoryMessage);
        valueMap.put("productName", this.productName);
        valueMap.put("createdByUserLogin", this.createdByUserLogin);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InventoryItemAndLocation");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.math.BigDecimal productHeight;

    public java.math.BigDecimal getproductHeight() {
        return productHeight;
    }

    public void setproductHeight(java.math.BigDecimal productHeight) {
        this.productHeight = productHeight;
    }
    private java.lang.String datetimeManufactured;

    public java.lang.String getdatetimeManufactured() {
        return datetimeManufactured;
    }

    public void setdatetimeManufactured(java.lang.String datetimeManufactured) {
        this.datetimeManufactured = datetimeManufactured;
    }
    private java.sql.Timestamp createdDate;

    public java.sql.Timestamp getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(java.sql.Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    private java.lang.String shippingWidth;

    public java.lang.String getshippingWidth() {
        return shippingWidth;
    }

    public void setshippingWidth(java.lang.String shippingWidth) {
        this.shippingWidth = shippingWidth;
    }
    private java.lang.String taxable;

    public java.lang.String gettaxable() {
        return taxable;
    }

    public void settaxable(java.lang.String taxable) {
        this.taxable = taxable;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String salesDiscWhenNotAvail;

    public java.lang.String getsalesDiscWhenNotAvail() {
        return salesDiscWhenNotAvail;
    }

    public void setsalesDiscWhenNotAvail(java.lang.String salesDiscWhenNotAvail) {
        this.salesDiscWhenNotAvail = salesDiscWhenNotAvail;
    }
    private java.lang.String amountUomTypeId;

    public java.lang.String getamountUomTypeId() {
        return amountUomTypeId;
    }

    public void setamountUomTypeId(java.lang.String amountUomTypeId) {
        this.amountUomTypeId = amountUomTypeId;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String facilityId;

    public java.lang.String getfacilityId() {
        return facilityId;
    }

    public void setfacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.lang.String configId;

    public java.lang.String getconfigId() {
        return configId;
    }

    public void setconfigId(java.lang.String configId) {
        this.configId = configId;
    }
    private java.lang.String productRating;

    public java.lang.String getproductRating() {
        return productRating;
    }

    public void setproductRating(java.lang.String productRating) {
        this.productRating = productRating;
    }
    private java.lang.String lastModifiedByUserLogin;

    public java.lang.String getlastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setlastModifiedByUserLogin(java.lang.String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String fixedAmount;

    public java.lang.String getfixedAmount() {
        return fixedAmount;
    }

    public void setfixedAmount(java.lang.String fixedAmount) {
        this.fixedAmount = fixedAmount;
    }
    private java.sql.Timestamp expireDate;

    public java.sql.Timestamp getexpireDate() {
        return expireDate;
    }

    public void setexpireDate(java.sql.Timestamp expireDate) {
        this.expireDate = expireDate;
    }
    private java.lang.String softIdentifier;

    public java.lang.String getsoftIdentifier() {
        return softIdentifier;
    }

    public void setsoftIdentifier(java.lang.String softIdentifier) {
        this.softIdentifier = softIdentifier;
    }
    private java.lang.String activationNumber;

    public java.lang.String getactivationNumber() {
        return activationNumber;
    }

    public void setactivationNumber(java.lang.String activationNumber) {
        this.activationNumber = activationNumber;
    }
    private java.lang.String returnable;

    public java.lang.String getreturnable() {
        return returnable;
    }

    public void setreturnable(java.lang.String returnable) {
        this.returnable = returnable;
    }
    private java.lang.String primaryProductCategoryId;

    public java.lang.String getprimaryProductCategoryId() {
        return primaryProductCategoryId;
    }

    public void setprimaryProductCategoryId(java.lang.String primaryProductCategoryId) {
        this.primaryProductCategoryId = primaryProductCategoryId;
    }
    private java.lang.String depthUomId;

    public java.lang.String getdepthUomId() {
        return depthUomId;
    }

    public void setdepthUomId(java.lang.String depthUomId) {
        this.depthUomId = depthUomId;
    }
    private java.lang.String piecesIncluded;

    public java.lang.String getpiecesIncluded() {
        return piecesIncluded;
    }

    public void setpiecesIncluded(java.lang.String piecesIncluded) {
        this.piecesIncluded = piecesIncluded;
    }
    private java.lang.String locationTypeEnumId;

    public java.lang.String getlocationTypeEnumId() {
        return locationTypeEnumId;
    }

    public void setlocationTypeEnumId(java.lang.String locationTypeEnumId) {
        this.locationTypeEnumId = locationTypeEnumId;
    }
    private java.lang.String levelId;

    public java.lang.String getlevelId() {
        return levelId;
    }

    public void setlevelId(java.lang.String levelId) {
        this.levelId = levelId;
    }
    private java.lang.String chargeShipping;

    public java.lang.String getchargeShipping() {
        return chargeShipping;
    }

    public void setchargeShipping(java.lang.String chargeShipping) {
        this.chargeShipping = chargeShipping;
    }
    private java.lang.String includeInPromotions;

    public java.lang.String getincludeInPromotions() {
        return includeInPromotions;
    }

    public void setincludeInPromotions(java.lang.String includeInPromotions) {
        this.includeInPromotions = includeInPromotions;
    }
    private java.lang.String originalImageUrl;

    public java.lang.String getoriginalImageUrl() {
        return originalImageUrl;
    }

    public void setoriginalImageUrl(java.lang.String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }
    private java.lang.String detailScreen;

    public java.lang.String getdetailScreen() {
        return detailScreen;
    }

    public void setdetailScreen(java.lang.String detailScreen) {
        this.detailScreen = detailScreen;
    }
    private java.lang.String requireAmount;

    public java.lang.String getrequireAmount() {
        return requireAmount;
    }

    public void setrequireAmount(java.lang.String requireAmount) {
        this.requireAmount = requireAmount;
    }
    private java.lang.String inventoryItemTypeId;

    public java.lang.String getinventoryItemTypeId() {
        return inventoryItemTypeId;
    }

    public void setinventoryItemTypeId(java.lang.String inventoryItemTypeId) {
        this.inventoryItemTypeId = inventoryItemTypeId;
    }
    private java.lang.String productDepth;

    public java.lang.String getproductDepth() {
        return productDepth;
    }

    public void setproductDepth(java.lang.String productDepth) {
        this.productDepth = productDepth;
    }
    private java.lang.String shippingDepth;

    public java.lang.String getshippingDepth() {
        return shippingDepth;
    }

    public void setshippingDepth(java.lang.String shippingDepth) {
        this.shippingDepth = shippingDepth;
    }
    private java.lang.String lotId;

    public java.lang.String getlotId() {
        return lotId;
    }

    public void setlotId(java.lang.String lotId) {
        this.lotId = lotId;
    }
    private java.lang.String largeImageUrl;

    public java.lang.String getlargeImageUrl() {
        return largeImageUrl;
    }

    public void setlargeImageUrl(java.lang.String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }
    private java.lang.String originGeoId;

    public java.lang.String getoriginGeoId() {
        return originGeoId;
    }

    public void setoriginGeoId(java.lang.String originGeoId) {
        this.originGeoId = originGeoId;
    }
    private java.lang.String requireInventory;

    public java.lang.String getrequireInventory() {
        return requireInventory;
    }

    public void setrequireInventory(java.lang.String requireInventory) {
        this.requireInventory = requireInventory;
    }
    private java.lang.String ownerPartyId;

    public java.lang.String getownerPartyId() {
        return ownerPartyId;
    }

    public void setownerPartyId(java.lang.String ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }
    private java.lang.String productDiameter;

    public java.lang.String getproductDiameter() {
        return productDiameter;
    }

    public void setproductDiameter(java.lang.String productDiameter) {
        this.productDiameter = productDiameter;
    }
    private java.math.BigDecimal quantityOnHandTotal;

    public java.math.BigDecimal getquantityOnHandTotal() {
        return quantityOnHandTotal;
    }

    public void setquantityOnHandTotal(java.math.BigDecimal quantityOnHandTotal) {
        this.quantityOnHandTotal = quantityOnHandTotal;
    }
    private java.lang.String orderDecimalQuantity;

    public java.lang.String getorderDecimalQuantity() {
        return orderDecimalQuantity;
    }

    public void setorderDecimalQuantity(java.lang.String orderDecimalQuantity) {
        this.orderDecimalQuantity = orderDecimalQuantity;
    }
    private java.lang.String lotIdFilledIn;

    public java.lang.String getlotIdFilledIn() {
        return lotIdFilledIn;
    }

    public void setlotIdFilledIn(java.lang.String lotIdFilledIn) {
        this.lotIdFilledIn = lotIdFilledIn;
    }
    private java.lang.String sectionId;

    public java.lang.String getsectionId() {
        return sectionId;
    }

    public void setsectionId(java.lang.String sectionId) {
        this.sectionId = sectionId;
    }
    private java.lang.String positionId;

    public java.lang.String getpositionId() {
        return positionId;
    }

    public void setpositionId(java.lang.String positionId) {
        this.positionId = positionId;
    }
    private java.math.BigDecimal productWeight;

    public java.math.BigDecimal getproductWeight() {
        return productWeight;
    }

    public void setproductWeight(java.math.BigDecimal productWeight) {
        this.productWeight = productWeight;
    }
    private java.lang.String diameterUomId;

    public java.lang.String getdiameterUomId() {
        return diameterUomId;
    }

    public void setdiameterUomId(java.lang.String diameterUomId) {
        this.diameterUomId = diameterUomId;
    }
    private java.lang.String shippingHeight;

    public java.lang.String getshippingHeight() {
        return shippingHeight;
    }

    public void setshippingHeight(java.lang.String shippingHeight) {
        this.shippingHeight = shippingHeight;
    }
    private java.lang.String comments;

    public java.lang.String getcomments() {
        return comments;
    }

    public void setcomments(java.lang.String comments) {
        this.comments = comments;
    }
    private java.lang.String isVariant;

    public java.lang.String getisVariant() {
        return isVariant;
    }

    public void setisVariant(java.lang.String isVariant) {
        this.isVariant = isVariant;
    }
    private java.lang.String internalName;

    public java.lang.String getinternalName() {
        return internalName;
    }

    public void setinternalName(java.lang.String internalName) {
        this.internalName = internalName;
    }
    private java.lang.String serialNumber;

    public java.lang.String getserialNumber() {
        return serialNumber;
    }

    public void setserialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }
    private java.lang.String reservNthPPPerc;

    public java.lang.String getreservNthPPPerc() {
        return reservNthPPPerc;
    }

    public void setreservNthPPPerc(java.lang.String reservNthPPPerc) {
        this.reservNthPPPerc = reservNthPPPerc;
    }
    private java.math.BigDecimal availableToPromiseTotal;

    public java.math.BigDecimal getavailableToPromiseTotal() {
        return availableToPromiseTotal;
    }

    public void setavailableToPromiseTotal(java.math.BigDecimal availableToPromiseTotal) {
        this.availableToPromiseTotal = availableToPromiseTotal;
    }
    private java.lang.String activationValidThru;

    public java.lang.String getactivationValidThru() {
        return activationValidThru;
    }

    public void setactivationValidThru(java.lang.String activationValidThru) {
        this.activationValidThru = activationValidThru;
    }
    private java.lang.String locationSeqId;

    public java.lang.String getlocationSeqId() {
        return locationSeqId;
    }

    public void setlocationSeqId(java.lang.String locationSeqId) {
        this.locationSeqId = locationSeqId;
    }
    private java.lang.String brandName;

    public java.lang.String getbrandName() {
        return brandName;
    }

    public void setbrandName(java.lang.String brandName) {
        this.brandName = brandName;
    }
    private java.lang.Long billOfMaterialLevel;

    public java.lang.Long getbillOfMaterialLevel() {
        return billOfMaterialLevel;
    }

    public void setbillOfMaterialLevel(java.lang.Long billOfMaterialLevel) {
        this.billOfMaterialLevel = billOfMaterialLevel;
    }
    private java.lang.String quantityIncluded;

    public java.lang.String getquantityIncluded() {
        return quantityIncluded;
    }

    public void setquantityIncluded(java.lang.String quantityIncluded) {
        this.quantityIncluded = quantityIncluded;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String longDescription;

    public java.lang.String getlongDescription() {
        return longDescription;
    }

    public void setlongDescription(java.lang.String longDescription) {
        this.longDescription = longDescription;
    }
    private java.lang.String heightUomId;

    public java.lang.String getheightUomId() {
        return heightUomId;
    }

    public void setheightUomId(java.lang.String heightUomId) {
        this.heightUomId = heightUomId;
    }
    private java.lang.String reservMaxPersons;

    public java.lang.String getreservMaxPersons() {
        return reservMaxPersons;
    }

    public void setreservMaxPersons(java.lang.String reservMaxPersons) {
        this.reservMaxPersons = reservMaxPersons;
    }
    private java.lang.String widthUomId;

    public java.lang.String getwidthUomId() {
        return widthUomId;
    }

    public void setwidthUomId(java.lang.String widthUomId) {
        this.widthUomId = widthUomId;
    }
    private java.lang.String binNumber;

    public java.lang.String getbinNumber() {
        return binNumber;
    }

    public void setbinNumber(java.lang.String binNumber) {
        this.binNumber = binNumber;
    }
    private java.lang.String inventoryItemId;

    public java.lang.String getinventoryItemId() {
        return inventoryItemId;
    }

    public void setinventoryItemId(java.lang.String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }
    private java.lang.String mediumImageUrl;

    public java.lang.String getmediumImageUrl() {
        return mediumImageUrl;
    }

    public void setmediumImageUrl(java.lang.String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }
    private java.lang.String autoCreateKeywords;

    public java.lang.String getautoCreateKeywords() {
        return autoCreateKeywords;
    }

    public void setautoCreateKeywords(java.lang.String autoCreateKeywords) {
        this.autoCreateKeywords = autoCreateKeywords;
    }
    private java.lang.String oldAvailableToPromise;

    public java.lang.String getoldAvailableToPromise() {
        return oldAvailableToPromise;
    }

    public void setoldAvailableToPromise(java.lang.String oldAvailableToPromise) {
        this.oldAvailableToPromise = oldAvailableToPromise;
    }
    private java.lang.String ratingTypeEnum;

    public java.lang.String getratingTypeEnum() {
        return ratingTypeEnum;
    }

    public void setratingTypeEnum(java.lang.String ratingTypeEnum) {
        this.ratingTypeEnum = ratingTypeEnum;
    }
    private java.lang.String aisleId;

    public java.lang.String getaisleId() {
        return aisleId;
    }

    public void setaisleId(java.lang.String aisleId) {
        this.aisleId = aisleId;
    }
    private java.sql.Timestamp datetimeReceived;

    public java.sql.Timestamp getdatetimeReceived() {
        return datetimeReceived;
    }

    public void setdatetimeReceived(java.sql.Timestamp datetimeReceived) {
        this.datetimeReceived = datetimeReceived;
    }
    private java.lang.String containerId;

    public java.lang.String getcontainerId() {
        return containerId;
    }

    public void setcontainerId(java.lang.String containerId) {
        this.containerId = containerId;
    }
    private java.lang.String requirementMethodEnumId;

    public java.lang.String getrequirementMethodEnumId() {
        return requirementMethodEnumId;
    }

    public void setrequirementMethodEnumId(java.lang.String requirementMethodEnumId) {
        this.requirementMethodEnumId = requirementMethodEnumId;
    }
    private java.lang.String quantityUomId;

    public java.lang.String getquantityUomId() {
        return quantityUomId;
    }

    public void setquantityUomId(java.lang.String quantityUomId) {
        this.quantityUomId = quantityUomId;
    }
    private java.lang.String productTypeId;

    public java.lang.String getproductTypeId() {
        return productTypeId;
    }

    public void setproductTypeId(java.lang.String productTypeId) {
        this.productTypeId = productTypeId;
    }
    private java.lang.String virtualVariantMethodEnum;

    public java.lang.String getvirtualVariantMethodEnum() {
        return virtualVariantMethodEnum;
    }

    public void setvirtualVariantMethodEnum(java.lang.String virtualVariantMethodEnum) {
        this.virtualVariantMethodEnum = virtualVariantMethodEnum;
    }
    private java.math.BigDecimal weight;

    public java.math.BigDecimal getweight() {
        return weight;
    }

    public void setweight(java.math.BigDecimal weight) {
        this.weight = weight;
    }
    private java.sql.Timestamp salesDiscontinuationDate;

    public java.sql.Timestamp getsalesDiscontinuationDate() {
        return salesDiscontinuationDate;
    }

    public void setsalesDiscontinuationDate(java.sql.Timestamp salesDiscontinuationDate) {
        this.salesDiscontinuationDate = salesDiscontinuationDate;
    }
    private java.sql.Timestamp supportDiscontinuationDate;

    public java.sql.Timestamp getsupportDiscontinuationDate() {
        return supportDiscontinuationDate;
    }

    public void setsupportDiscontinuationDate(java.sql.Timestamp supportDiscontinuationDate) {
        this.supportDiscontinuationDate = supportDiscontinuationDate;
    }
    private java.lang.String areaId;

    public java.lang.String getareaId() {
        return areaId;
    }

    public void setareaId(java.lang.String areaId) {
        this.areaId = areaId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String oldQuantityOnHand;

    public java.lang.String getoldQuantityOnHand() {
        return oldQuantityOnHand;
    }

    public void setoldQuantityOnHand(java.lang.String oldQuantityOnHand) {
        this.oldQuantityOnHand = oldQuantityOnHand;
    }
    private java.sql.Timestamp introductionDate;

    public java.sql.Timestamp getintroductionDate() {
        return introductionDate;
    }

    public void setintroductionDate(java.sql.Timestamp introductionDate) {
        this.introductionDate = introductionDate;
    }
    private java.sql.Timestamp releaseDate;

    public java.sql.Timestamp getreleaseDate() {
        return releaseDate;
    }

    public void setreleaseDate(java.sql.Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }
    private java.math.BigDecimal unitCost;

    public java.math.BigDecimal getunitCost() {
        return unitCost;
    }

    public void setunitCost(java.math.BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
    private java.lang.String inShippingBox;

    public java.lang.String getinShippingBox() {
        return inShippingBox;
    }

    public void setinShippingBox(java.lang.String inShippingBox) {
        this.inShippingBox = inShippingBox;
    }
    private java.lang.String reserv2ndPPPerc;

    public java.lang.String getreserv2ndPPPerc() {
        return reserv2ndPPPerc;
    }

    public void setreserv2ndPPPerc(java.lang.String reserv2ndPPPerc) {
        this.reserv2ndPPPerc = reserv2ndPPPerc;
    }
    private java.lang.String fixedAssetId;

    public java.lang.String getfixedAssetId() {
        return fixedAssetId;
    }

    public void setfixedAssetId(java.lang.String fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }
    private java.lang.String detailImageUrl;

    public java.lang.String getdetailImageUrl() {
        return detailImageUrl;
    }

    public void setdetailImageUrl(java.lang.String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }
    private java.lang.String productWidth;

    public java.lang.String getproductWidth() {
        return productWidth;
    }

    public void setproductWidth(java.lang.String productWidth) {
        this.productWidth = productWidth;
    }
    private java.lang.String weightUomId;

    public java.lang.String getweightUomId() {
        return weightUomId;
    }

    public void setweightUomId(java.lang.String weightUomId) {
        this.weightUomId = weightUomId;
    }
    private java.lang.String manufacturerPartyId;

    public java.lang.String getmanufacturerPartyId() {
        return manufacturerPartyId;
    }

    public void setmanufacturerPartyId(java.lang.String manufacturerPartyId) {
        this.manufacturerPartyId = manufacturerPartyId;
    }
    private java.math.BigDecimal accountingQuantityTotal;

    public java.math.BigDecimal getaccountingQuantityTotal() {
        return accountingQuantityTotal;
    }

    public void setaccountingQuantityTotal(java.math.BigDecimal accountingQuantityTotal) {
        this.accountingQuantityTotal = accountingQuantityTotal;
    }
    private java.lang.String smallImageUrl;

    public java.lang.String getsmallImageUrl() {
        return smallImageUrl;
    }

    public void setsmallImageUrl(java.lang.String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }
    private java.lang.String isVirtual;

    public java.lang.String getisVirtual() {
        return isVirtual;
    }

    public void setisVirtual(java.lang.String isVirtual) {
        this.isVirtual = isVirtual;
    }
    private java.lang.String priceDetailText;

    public java.lang.String getpriceDetailText() {
        return priceDetailText;
    }

    public void setpriceDetailText(java.lang.String priceDetailText) {
        this.priceDetailText = priceDetailText;
    }
    private java.lang.String defaultShipmentBoxTypeId;

    public java.lang.String getdefaultShipmentBoxTypeId() {
        return defaultShipmentBoxTypeId;
    }

    public void setdefaultShipmentBoxTypeId(java.lang.String defaultShipmentBoxTypeId) {
        this.defaultShipmentBoxTypeId = defaultShipmentBoxTypeId;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.sql.Timestamp lastModifiedDate;

    public java.sql.Timestamp getlastModifiedDate() {
        return lastModifiedDate;
    }

    public void setlastModifiedDate(java.sql.Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    private java.lang.String inventoryMessage;

    public java.lang.String getinventoryMessage() {
        return inventoryMessage;
    }

    public void setinventoryMessage(java.lang.String inventoryMessage) {
        this.inventoryMessage = inventoryMessage;
    }
    private java.lang.String productName;

    public java.lang.String getproductName() {
        return productName;
    }

    public void setproductName(java.lang.String productName) {
        this.productName = productName;
    }
    private java.lang.String createdByUserLogin;

    public java.lang.String getcreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setcreatedByUserLogin(java.lang.String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryItemAndLocation> objectList = new ArrayList<InventoryItemAndLocation>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItemAndLocation(genVal));
        }
        return objectList;
    }
}
