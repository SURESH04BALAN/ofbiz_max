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
public class SupplierProductAndProduct implements GenericValueObjectInterface {
public static final String module =SupplierProductAndProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public SupplierProductAndProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public SupplierProductAndProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"supplierRatingTypeId","Supplier Rating Type Id"},
{"reservNthPPPerc","Reserv Nth Pp Perc"},
{"unitsIncluded","Units Included"},
{"brandName","Brand Name"},
{"productHeight","Product Height"},
{"billOfMaterialLevel","Bill Of Material Level"},
{"quantityIncluded","Quantity Included"},
{"description","Description"},
{"longDescription","Long Description"},
{"heightUomId","Height Uom Id"},
{"widthUomId","Width Uom Id"},
{"reservMaxPersons","Reserv Max Persons"},
{"mediumImageUrl","Medium Image Url"},
{"createdDate","Created Date"},
{"autoCreateKeywords","Auto Create Keywords"},
{"shippingWidth","Shipping Width"},
{"taxable","Taxable"},
{"salesDiscWhenNotAvail","Sales Disc When Not Avail"},
{"currencyUomId","Currency Uom Id"},
{"amountUomTypeId","Amount Uom Type Id"},
{"agreementId","Agreement Id"},
{"lastPrice","Last Price"},
{"facilityId","Facility Id"},
{"configId","Config Id"},
{"availableFromDate","Available From Date"},
{"productRating","Product Rating"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"ratingTypeEnum","Rating Type Enum"},
{"fixedAmount","Fixed Amount"},
{"supplierProductId","Supplier Product Id"},
{"primaryProductCategoryId","Primary Product Category Id"},
{"returnable","Returnable"},
{"requirementMethodEnumId","Requirement Method Enum Id"},
{"piecesIncluded","Pieces Included"},
{"depthUomId","Depth Uom Id"},
{"quantityUomId","Quantity Uom Id"},
{"productTypeId","Product Type Id"},
{"weight","Weight"},
{"virtualVariantMethodEnum","Virtual Variant Method Enum"},
{"salesDiscontinuationDate","Sales Discontinuation Date"},
{"chargeShipping","Charge Shipping"},
{"includeInPromotions","Include In Promotions"},
{"supportDiscontinuationDate","Support Discontinuation Date"},
{"supplierPrefOrderId","Supplier Pref Order Id"},
{"detailScreen","Detail Screen"},
{"originalImageUrl","Original Image Url"},
{"introductionDate","Introduction Date"},
{"requireAmount","Require Amount"},
{"releaseDate","Release Date"},
{"shippingPrice","Shipping Price"},
{"productDepth","Product Depth"},
{"shippingDepth","Shipping Depth"},
{"largeImageUrl","Large Image Url"},
{"supplierProductName","Supplier Product Name"},
{"originGeoId","Origin Geo Id"},
{"reserv2ndPPPerc","Reserv 2Nd Pp Perc"},
{"inShippingBox","In Shipping Box"},
{"requireInventory","Require Inventory"},
{"detailImageUrl","Detail Image Url"},
{"productWidth","Product Width"},
{"canDropShip","Can Drop Ship"},
{"manufacturerPartyId","Manufacturer Party Id"},
{"weightUomId","Weight Uom Id"},
{"productDiameter","Product Diameter"},
{"minimumOrderQuantity","Minimum Order Quantity"},
{"orderDecimalQuantity","Order Decimal Quantity"},
{"smallImageUrl","Small Image Url"},
{"agreementItemSeqId","Agreement Item Seq Id"},
{"isVirtual","Is Virtual"},
{"priceDetailText","Price Detail Text"},
{"orderQtyIncrements","Order Qty Increments"},
{"defaultShipmentBoxTypeId","Default Shipment Box Type Id"},
{"productId","Product Id"},
{"lotIdFilledIn","Lot Id Filled In"},
{"productWeight","Product Weight"},
{"standardLeadTimeDays","Standard Lead Time Days"},
{"lastModifiedDate","Last Modified Date"},
{"availableThruDate","Available Thru Date"},
{"diameterUomId","Diameter Uom Id"},
{"inventoryMessage","Inventory Message"},
{"productName","Product Name"},
{"shippingHeight","Shipping Height"},
{"comments","Comments"},
{"isVariant","Is Variant"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.internalName = "";
this.supplierRatingTypeId = "";
this.reservNthPPPerc = "";
this.unitsIncluded = "";
this.brandName = "";
this.productHeight = java.math.BigDecimal.ZERO;
this.billOfMaterialLevel = "";
this.quantityIncluded = "";
this.description = "";
this.longDescription = "";
this.heightUomId = "";
this.widthUomId = "";
this.reservMaxPersons = "";
this.mediumImageUrl = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.autoCreateKeywords = "";
this.shippingWidth = "";
this.taxable = "";
this.salesDiscWhenNotAvail = "";
this.currencyUomId = "";
this.amountUomTypeId = "";
this.agreementId = "";
this.lastPrice = java.math.BigDecimal.ZERO;
this.facilityId = "";
this.configId = "";
this.availableFromDate = UtilDateTime.nowTimestamp();
this.productRating = "";
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.ratingTypeEnum = "";
this.fixedAmount = "";
this.supplierProductId = "";
this.primaryProductCategoryId = "";
this.returnable = "";
this.requirementMethodEnumId = "";
this.piecesIncluded = "";
this.depthUomId = "";
this.quantityUomId = "";
this.productTypeId = "";
this.weight = "";
this.virtualVariantMethodEnum = "";
this.salesDiscontinuationDate = UtilDateTime.nowTimestamp();
this.chargeShipping = "";
this.includeInPromotions = "";
this.supportDiscontinuationDate = UtilDateTime.nowTimestamp();
this.supplierPrefOrderId = "";
this.detailScreen = "";
this.originalImageUrl = "";
this.introductionDate = UtilDateTime.nowTimestamp();
this.requireAmount = "";
this.releaseDate = UtilDateTime.nowTimestamp();
this.shippingPrice = "";
this.productDepth = "";
this.shippingDepth = "";
this.largeImageUrl = "";
this.supplierProductName = "";
this.originGeoId = "";
this.reserv2ndPPPerc = "";
this.inShippingBox = "";
this.requireInventory = "";
this.detailImageUrl = "";
this.productWidth = "";
this.canDropShip = "";
this.manufacturerPartyId = "";
this.weightUomId = "";
this.productDiameter = "";
this.minimumOrderQuantity = java.math.BigDecimal.ZERO;
this.orderDecimalQuantity = "";
this.smallImageUrl = "";
this.agreementItemSeqId = "";
this.isVirtual = "";
this.priceDetailText = "";
this.orderQtyIncrements = java.math.BigDecimal.ZERO;
this.defaultShipmentBoxTypeId = "";
this.productId = "";
this.lotIdFilledIn = "";
this.productWeight = "";
this.standardLeadTimeDays = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.availableThruDate = UtilDateTime.nowTimestamp();
this.diameterUomId = "";
this.inventoryMessage = "";
this.productName = "";
this.shippingHeight = "";
this.comments = "";
this.isVariant = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.supplierRatingTypeId = (java.lang.String) genVal.get("supplierRatingTypeId");
this.reservNthPPPerc = (java.lang.String) genVal.get("reservNthPPPerc");
this.unitsIncluded = (java.lang.String) genVal.get("unitsIncluded");
this.brandName = (java.lang.String) genVal.get("brandName");
this.productHeight = (java.math.BigDecimal) genVal.get("productHeight");
this.billOfMaterialLevel = (java.lang.String) genVal.get("billOfMaterialLevel");
this.quantityIncluded = (java.lang.String) genVal.get("quantityIncluded");
this.description = (java.lang.String) genVal.get("description");
this.longDescription = (java.lang.String) genVal.get("longDescription");
this.heightUomId = (java.lang.String) genVal.get("heightUomId");
this.widthUomId = (java.lang.String) genVal.get("widthUomId");
this.reservMaxPersons = (java.lang.String) genVal.get("reservMaxPersons");
this.mediumImageUrl = (java.lang.String) genVal.get("mediumImageUrl");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.autoCreateKeywords = (java.lang.String) genVal.get("autoCreateKeywords");
this.shippingWidth = (java.lang.String) genVal.get("shippingWidth");
this.taxable = (java.lang.String) genVal.get("taxable");
this.salesDiscWhenNotAvail = (java.lang.String) genVal.get("salesDiscWhenNotAvail");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.amountUomTypeId = (java.lang.String) genVal.get("amountUomTypeId");
this.agreementId = (java.lang.String) genVal.get("agreementId");
this.lastPrice = (java.math.BigDecimal) genVal.get("lastPrice");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.configId = (java.lang.String) genVal.get("configId");
this.availableFromDate = (java.sql.Timestamp) genVal.get("availableFromDate");
this.productRating = (java.lang.String) genVal.get("productRating");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.ratingTypeEnum = (java.lang.String) genVal.get("ratingTypeEnum");
this.fixedAmount = (java.lang.String) genVal.get("fixedAmount");
this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
this.returnable = (java.lang.String) genVal.get("returnable");
this.requirementMethodEnumId = (java.lang.String) genVal.get("requirementMethodEnumId");
this.piecesIncluded = (java.lang.String) genVal.get("piecesIncluded");
this.depthUomId = (java.lang.String) genVal.get("depthUomId");
this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
this.productTypeId = (java.lang.String) genVal.get("productTypeId");
this.weight = (java.lang.String) genVal.get("weight");
this.virtualVariantMethodEnum = (java.lang.String) genVal.get("virtualVariantMethodEnum");
this.salesDiscontinuationDate = (java.sql.Timestamp) genVal.get("salesDiscontinuationDate");
this.chargeShipping = (java.lang.String) genVal.get("chargeShipping");
this.includeInPromotions = (java.lang.String) genVal.get("includeInPromotions");
this.supportDiscontinuationDate = (java.sql.Timestamp) genVal.get("supportDiscontinuationDate");
this.supplierPrefOrderId = (java.lang.String) genVal.get("supplierPrefOrderId");
this.detailScreen = (java.lang.String) genVal.get("detailScreen");
this.originalImageUrl = (java.lang.String) genVal.get("originalImageUrl");
this.introductionDate = (java.sql.Timestamp) genVal.get("introductionDate");
this.requireAmount = (java.lang.String) genVal.get("requireAmount");
this.releaseDate = (java.sql.Timestamp) genVal.get("releaseDate");
this.shippingPrice = (java.lang.String) genVal.get("shippingPrice");
this.productDepth = (java.lang.String) genVal.get("productDepth");
this.shippingDepth = (java.lang.String) genVal.get("shippingDepth");
this.largeImageUrl = (java.lang.String) genVal.get("largeImageUrl");
this.supplierProductName = (java.lang.String) genVal.get("supplierProductName");
this.originGeoId = (java.lang.String) genVal.get("originGeoId");
this.reserv2ndPPPerc = (java.lang.String) genVal.get("reserv2ndPPPerc");
this.inShippingBox = (java.lang.String) genVal.get("inShippingBox");
this.requireInventory = (java.lang.String) genVal.get("requireInventory");
this.detailImageUrl = (java.lang.String) genVal.get("detailImageUrl");
this.productWidth = (java.lang.String) genVal.get("productWidth");
this.canDropShip = (java.lang.String) genVal.get("canDropShip");
this.manufacturerPartyId = (java.lang.String) genVal.get("manufacturerPartyId");
this.weightUomId = (java.lang.String) genVal.get("weightUomId");
this.productDiameter = (java.lang.String) genVal.get("productDiameter");
this.minimumOrderQuantity = (java.math.BigDecimal) genVal.get("minimumOrderQuantity");
this.orderDecimalQuantity = (java.lang.String) genVal.get("orderDecimalQuantity");
this.smallImageUrl = (java.lang.String) genVal.get("smallImageUrl");
this.agreementItemSeqId = (java.lang.String) genVal.get("agreementItemSeqId");
this.isVirtual = (java.lang.String) genVal.get("isVirtual");
this.priceDetailText = (java.lang.String) genVal.get("priceDetailText");
this.orderQtyIncrements = (java.math.BigDecimal) genVal.get("orderQtyIncrements");
this.defaultShipmentBoxTypeId = (java.lang.String) genVal.get("defaultShipmentBoxTypeId");
this.productId = (java.lang.String) genVal.get("productId");
this.lotIdFilledIn = (java.lang.String) genVal.get("lotIdFilledIn");
this.productWeight = (java.lang.String) genVal.get("productWeight");
this.standardLeadTimeDays = (java.lang.String) genVal.get("standardLeadTimeDays");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.availableThruDate = (java.sql.Timestamp) genVal.get("availableThruDate");
this.diameterUomId = (java.lang.String) genVal.get("diameterUomId");
this.inventoryMessage = (java.lang.String) genVal.get("inventoryMessage");
this.productName = (java.lang.String) genVal.get("productName");
this.shippingHeight = (java.lang.String) genVal.get("shippingHeight");
this.comments = (java.lang.String) genVal.get("comments");
this.isVariant = (java.lang.String) genVal.get("isVariant");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("supplierRatingTypeId",OrderMaxUtility.getValidEntityString(this.supplierRatingTypeId));
val.set("reservNthPPPerc",OrderMaxUtility.getValidEntityString(this.reservNthPPPerc));
val.set("unitsIncluded",OrderMaxUtility.getValidEntityString(this.unitsIncluded));
val.set("brandName",OrderMaxUtility.getValidEntityString(this.brandName));
val.set("productHeight",OrderMaxUtility.getValidEntityBigDecimal(this.productHeight));
val.set("billOfMaterialLevel",OrderMaxUtility.getValidEntityString(this.billOfMaterialLevel));
val.set("quantityIncluded",OrderMaxUtility.getValidEntityString(this.quantityIncluded));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("longDescription",OrderMaxUtility.getValidEntityString(this.longDescription));
val.set("heightUomId",OrderMaxUtility.getValidEntityString(this.heightUomId));
val.set("widthUomId",OrderMaxUtility.getValidEntityString(this.widthUomId));
val.set("reservMaxPersons",OrderMaxUtility.getValidEntityString(this.reservMaxPersons));
val.set("mediumImageUrl",OrderMaxUtility.getValidEntityString(this.mediumImageUrl));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("autoCreateKeywords",OrderMaxUtility.getValidEntityString(this.autoCreateKeywords));
val.set("shippingWidth",OrderMaxUtility.getValidEntityString(this.shippingWidth));
val.set("taxable",OrderMaxUtility.getValidEntityString(this.taxable));
val.set("salesDiscWhenNotAvail",OrderMaxUtility.getValidEntityString(this.salesDiscWhenNotAvail));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("amountUomTypeId",OrderMaxUtility.getValidEntityString(this.amountUomTypeId));
val.set("agreementId",OrderMaxUtility.getValidEntityString(this.agreementId));
val.set("lastPrice",OrderMaxUtility.getValidEntityBigDecimal(this.lastPrice));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("configId",OrderMaxUtility.getValidEntityString(this.configId));
val.set("availableFromDate",OrderMaxUtility.getValidEntityTimestamp(this.availableFromDate));
val.set("productRating",OrderMaxUtility.getValidEntityString(this.productRating));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("ratingTypeEnum",OrderMaxUtility.getValidEntityString(this.ratingTypeEnum));
val.set("fixedAmount",OrderMaxUtility.getValidEntityString(this.fixedAmount));
val.set("supplierProductId",OrderMaxUtility.getValidEntityString(this.supplierProductId));
val.set("primaryProductCategoryId",OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
val.set("returnable",OrderMaxUtility.getValidEntityString(this.returnable));
val.set("requirementMethodEnumId",OrderMaxUtility.getValidEntityString(this.requirementMethodEnumId));
val.set("piecesIncluded",OrderMaxUtility.getValidEntityString(this.piecesIncluded));
val.set("depthUomId",OrderMaxUtility.getValidEntityString(this.depthUomId));
val.set("quantityUomId",OrderMaxUtility.getValidEntityString(this.quantityUomId));
val.set("productTypeId",OrderMaxUtility.getValidEntityString(this.productTypeId));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("virtualVariantMethodEnum",OrderMaxUtility.getValidEntityString(this.virtualVariantMethodEnum));
val.set("salesDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.salesDiscontinuationDate));
val.set("chargeShipping",OrderMaxUtility.getValidEntityString(this.chargeShipping));
val.set("includeInPromotions",OrderMaxUtility.getValidEntityString(this.includeInPromotions));
val.set("supportDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.supportDiscontinuationDate));
val.set("supplierPrefOrderId",OrderMaxUtility.getValidEntityString(this.supplierPrefOrderId));
val.set("detailScreen",OrderMaxUtility.getValidEntityString(this.detailScreen));
val.set("originalImageUrl",OrderMaxUtility.getValidEntityString(this.originalImageUrl));
val.set("introductionDate",OrderMaxUtility.getValidEntityTimestamp(this.introductionDate));
val.set("requireAmount",OrderMaxUtility.getValidEntityString(this.requireAmount));
val.set("releaseDate",OrderMaxUtility.getValidEntityTimestamp(this.releaseDate));
val.set("shippingPrice",OrderMaxUtility.getValidEntityString(this.shippingPrice));
val.set("productDepth",OrderMaxUtility.getValidEntityString(this.productDepth));
val.set("shippingDepth",OrderMaxUtility.getValidEntityString(this.shippingDepth));
val.set("largeImageUrl",OrderMaxUtility.getValidEntityString(this.largeImageUrl));
val.set("supplierProductName",OrderMaxUtility.getValidEntityString(this.supplierProductName));
val.set("originGeoId",OrderMaxUtility.getValidEntityString(this.originGeoId));
val.set("reserv2ndPPPerc",OrderMaxUtility.getValidEntityString(this.reserv2ndPPPerc));
val.set("inShippingBox",OrderMaxUtility.getValidEntityString(this.inShippingBox));
val.set("requireInventory",OrderMaxUtility.getValidEntityString(this.requireInventory));
val.set("detailImageUrl",OrderMaxUtility.getValidEntityString(this.detailImageUrl));
val.set("productWidth",OrderMaxUtility.getValidEntityString(this.productWidth));
val.set("canDropShip",OrderMaxUtility.getValidEntityString(this.canDropShip));
val.set("manufacturerPartyId",OrderMaxUtility.getValidEntityString(this.manufacturerPartyId));
val.set("weightUomId",OrderMaxUtility.getValidEntityString(this.weightUomId));
val.set("productDiameter",OrderMaxUtility.getValidEntityString(this.productDiameter));
val.set("minimumOrderQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.minimumOrderQuantity));
val.set("orderDecimalQuantity",OrderMaxUtility.getValidEntityString(this.orderDecimalQuantity));
val.set("smallImageUrl",OrderMaxUtility.getValidEntityString(this.smallImageUrl));
val.set("agreementItemSeqId",OrderMaxUtility.getValidEntityString(this.agreementItemSeqId));
val.set("isVirtual",OrderMaxUtility.getValidEntityString(this.isVirtual));
val.set("priceDetailText",OrderMaxUtility.getValidEntityString(this.priceDetailText));
val.set("orderQtyIncrements",OrderMaxUtility.getValidEntityBigDecimal(this.orderQtyIncrements));
val.set("defaultShipmentBoxTypeId",OrderMaxUtility.getValidEntityString(this.defaultShipmentBoxTypeId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("lotIdFilledIn",OrderMaxUtility.getValidEntityString(this.lotIdFilledIn));
val.set("productWeight",OrderMaxUtility.getValidEntityString(this.productWeight));
val.set("standardLeadTimeDays",OrderMaxUtility.getValidEntityString(this.standardLeadTimeDays));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("availableThruDate",OrderMaxUtility.getValidEntityTimestamp(this.availableThruDate));
val.set("diameterUomId",OrderMaxUtility.getValidEntityString(this.diameterUomId));
val.set("inventoryMessage",OrderMaxUtility.getValidEntityString(this.inventoryMessage));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("shippingHeight",OrderMaxUtility.getValidEntityString(this.shippingHeight));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("isVariant",OrderMaxUtility.getValidEntityString(this.isVariant));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("supplierRatingTypeId",this.supplierRatingTypeId);
valueMap.put("reservNthPPPerc",this.reservNthPPPerc);
valueMap.put("unitsIncluded",this.unitsIncluded);
valueMap.put("brandName",this.brandName);
valueMap.put("productHeight",this.productHeight);
valueMap.put("billOfMaterialLevel",this.billOfMaterialLevel);
valueMap.put("quantityIncluded",this.quantityIncluded);
valueMap.put("description",this.description);
valueMap.put("longDescription",this.longDescription);
valueMap.put("heightUomId",this.heightUomId);
valueMap.put("widthUomId",this.widthUomId);
valueMap.put("reservMaxPersons",this.reservMaxPersons);
valueMap.put("mediumImageUrl",this.mediumImageUrl);
valueMap.put("createdDate",this.createdDate);
valueMap.put("autoCreateKeywords",this.autoCreateKeywords);
valueMap.put("shippingWidth",this.shippingWidth);
valueMap.put("taxable",this.taxable);
valueMap.put("salesDiscWhenNotAvail",this.salesDiscWhenNotAvail);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("amountUomTypeId",this.amountUomTypeId);
valueMap.put("agreementId",this.agreementId);
valueMap.put("lastPrice",this.lastPrice);
valueMap.put("facilityId",this.facilityId);
valueMap.put("configId",this.configId);
valueMap.put("availableFromDate",this.availableFromDate);
valueMap.put("productRating",this.productRating);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("ratingTypeEnum",this.ratingTypeEnum);
valueMap.put("fixedAmount",this.fixedAmount);
valueMap.put("supplierProductId",this.supplierProductId);
valueMap.put("primaryProductCategoryId",this.primaryProductCategoryId);
valueMap.put("returnable",this.returnable);
valueMap.put("requirementMethodEnumId",this.requirementMethodEnumId);
valueMap.put("piecesIncluded",this.piecesIncluded);
valueMap.put("depthUomId",this.depthUomId);
valueMap.put("quantityUomId",this.quantityUomId);
valueMap.put("productTypeId",this.productTypeId);
valueMap.put("weight",this.weight);
valueMap.put("virtualVariantMethodEnum",this.virtualVariantMethodEnum);
valueMap.put("salesDiscontinuationDate",this.salesDiscontinuationDate);
valueMap.put("chargeShipping",this.chargeShipping);
valueMap.put("includeInPromotions",this.includeInPromotions);
valueMap.put("supportDiscontinuationDate",this.supportDiscontinuationDate);
valueMap.put("supplierPrefOrderId",this.supplierPrefOrderId);
valueMap.put("detailScreen",this.detailScreen);
valueMap.put("originalImageUrl",this.originalImageUrl);
valueMap.put("introductionDate",this.introductionDate);
valueMap.put("requireAmount",this.requireAmount);
valueMap.put("releaseDate",this.releaseDate);
valueMap.put("shippingPrice",this.shippingPrice);
valueMap.put("productDepth",this.productDepth);
valueMap.put("shippingDepth",this.shippingDepth);
valueMap.put("largeImageUrl",this.largeImageUrl);
valueMap.put("supplierProductName",this.supplierProductName);
valueMap.put("originGeoId",this.originGeoId);
valueMap.put("reserv2ndPPPerc",this.reserv2ndPPPerc);
valueMap.put("inShippingBox",this.inShippingBox);
valueMap.put("requireInventory",this.requireInventory);
valueMap.put("detailImageUrl",this.detailImageUrl);
valueMap.put("productWidth",this.productWidth);
valueMap.put("canDropShip",this.canDropShip);
valueMap.put("manufacturerPartyId",this.manufacturerPartyId);
valueMap.put("weightUomId",this.weightUomId);
valueMap.put("productDiameter",this.productDiameter);
valueMap.put("minimumOrderQuantity",this.minimumOrderQuantity);
valueMap.put("orderDecimalQuantity",this.orderDecimalQuantity);
valueMap.put("smallImageUrl",this.smallImageUrl);
valueMap.put("agreementItemSeqId",this.agreementItemSeqId);
valueMap.put("isVirtual",this.isVirtual);
valueMap.put("priceDetailText",this.priceDetailText);
valueMap.put("orderQtyIncrements",this.orderQtyIncrements);
valueMap.put("defaultShipmentBoxTypeId",this.defaultShipmentBoxTypeId);
valueMap.put("productId",this.productId);
valueMap.put("lotIdFilledIn",this.lotIdFilledIn);
valueMap.put("productWeight",this.productWeight);
valueMap.put("standardLeadTimeDays",this.standardLeadTimeDays);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("availableThruDate",this.availableThruDate);
valueMap.put("diameterUomId",this.diameterUomId);
valueMap.put("inventoryMessage",this.inventoryMessage);
valueMap.put("productName",this.productName);
valueMap.put("shippingHeight",this.shippingHeight);
valueMap.put("comments",this.comments);
valueMap.put("isVariant",this.isVariant);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("SupplierProductAndProduct");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String internalName;
public java.lang.String getinternalName() {
return internalName;
}
public void setinternalName( java.lang.String internalName ) {
this.internalName = internalName;
}
private java.lang.String supplierRatingTypeId;
public java.lang.String getsupplierRatingTypeId() {
return supplierRatingTypeId;
}
public void setsupplierRatingTypeId( java.lang.String supplierRatingTypeId ) {
this.supplierRatingTypeId = supplierRatingTypeId;
}
private java.lang.String reservNthPPPerc;
public java.lang.String getreservNthPPPerc() {
return reservNthPPPerc;
}
public void setreservNthPPPerc( java.lang.String reservNthPPPerc ) {
this.reservNthPPPerc = reservNthPPPerc;
}
private java.lang.String unitsIncluded;
public java.lang.String getunitsIncluded() {
return unitsIncluded;
}
public void setunitsIncluded( java.lang.String unitsIncluded ) {
this.unitsIncluded = unitsIncluded;
}
private java.lang.String brandName;
public java.lang.String getbrandName() {
return brandName;
}
public void setbrandName( java.lang.String brandName ) {
this.brandName = brandName;
}
private java.math.BigDecimal productHeight;
public java.math.BigDecimal getproductHeight() {
return productHeight;
}
public void setproductHeight( java.math.BigDecimal productHeight ) {
this.productHeight = productHeight;
}
private java.lang.String billOfMaterialLevel;
public java.lang.String getbillOfMaterialLevel() {
return billOfMaterialLevel;
}
public void setbillOfMaterialLevel( java.lang.String billOfMaterialLevel ) {
this.billOfMaterialLevel = billOfMaterialLevel;
}
private java.lang.String quantityIncluded;
public java.lang.String getquantityIncluded() {
return quantityIncluded;
}
public void setquantityIncluded( java.lang.String quantityIncluded ) {
this.quantityIncluded = quantityIncluded;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String longDescription;
public java.lang.String getlongDescription() {
return longDescription;
}
public void setlongDescription( java.lang.String longDescription ) {
this.longDescription = longDescription;
}
private java.lang.String heightUomId;
public java.lang.String getheightUomId() {
return heightUomId;
}
public void setheightUomId( java.lang.String heightUomId ) {
this.heightUomId = heightUomId;
}
private java.lang.String widthUomId;
public java.lang.String getwidthUomId() {
return widthUomId;
}
public void setwidthUomId( java.lang.String widthUomId ) {
this.widthUomId = widthUomId;
}
private java.lang.String reservMaxPersons;
public java.lang.String getreservMaxPersons() {
return reservMaxPersons;
}
public void setreservMaxPersons( java.lang.String reservMaxPersons ) {
this.reservMaxPersons = reservMaxPersons;
}
private java.lang.String mediumImageUrl;
public java.lang.String getmediumImageUrl() {
return mediumImageUrl;
}
public void setmediumImageUrl( java.lang.String mediumImageUrl ) {
this.mediumImageUrl = mediumImageUrl;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String autoCreateKeywords;
public java.lang.String getautoCreateKeywords() {
return autoCreateKeywords;
}
public void setautoCreateKeywords( java.lang.String autoCreateKeywords ) {
this.autoCreateKeywords = autoCreateKeywords;
}
private java.lang.String shippingWidth;
public java.lang.String getshippingWidth() {
return shippingWidth;
}
public void setshippingWidth( java.lang.String shippingWidth ) {
this.shippingWidth = shippingWidth;
}
private java.lang.String taxable;
public java.lang.String gettaxable() {
return taxable;
}
public void settaxable( java.lang.String taxable ) {
this.taxable = taxable;
}
private java.lang.String salesDiscWhenNotAvail;
public java.lang.String getsalesDiscWhenNotAvail() {
return salesDiscWhenNotAvail;
}
public void setsalesDiscWhenNotAvail( java.lang.String salesDiscWhenNotAvail ) {
this.salesDiscWhenNotAvail = salesDiscWhenNotAvail;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String amountUomTypeId;
public java.lang.String getamountUomTypeId() {
return amountUomTypeId;
}
public void setamountUomTypeId( java.lang.String amountUomTypeId ) {
this.amountUomTypeId = amountUomTypeId;
}
private java.lang.String agreementId;
public java.lang.String getagreementId() {
return agreementId;
}
public void setagreementId( java.lang.String agreementId ) {
this.agreementId = agreementId;
}
private java.math.BigDecimal lastPrice;
public java.math.BigDecimal getlastPrice() {
return lastPrice;
}
public void setlastPrice( java.math.BigDecimal lastPrice ) {
this.lastPrice = lastPrice;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String configId;
public java.lang.String getconfigId() {
return configId;
}
public void setconfigId( java.lang.String configId ) {
this.configId = configId;
}
private java.sql.Timestamp availableFromDate;
public java.sql.Timestamp getavailableFromDate() {
return availableFromDate;
}
public void setavailableFromDate( java.sql.Timestamp availableFromDate ) {
this.availableFromDate = availableFromDate;
}
private java.lang.String productRating;
public java.lang.String getproductRating() {
return productRating;
}
public void setproductRating( java.lang.String productRating ) {
this.productRating = productRating;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String ratingTypeEnum;
public java.lang.String getratingTypeEnum() {
return ratingTypeEnum;
}
public void setratingTypeEnum( java.lang.String ratingTypeEnum ) {
this.ratingTypeEnum = ratingTypeEnum;
}
private java.lang.String fixedAmount;
public java.lang.String getfixedAmount() {
return fixedAmount;
}
public void setfixedAmount( java.lang.String fixedAmount ) {
this.fixedAmount = fixedAmount;
}
private java.lang.String supplierProductId;
public java.lang.String getsupplierProductId() {
return supplierProductId;
}
public void setsupplierProductId( java.lang.String supplierProductId ) {
this.supplierProductId = supplierProductId;
}
private java.lang.String primaryProductCategoryId;
public java.lang.String getprimaryProductCategoryId() {
return primaryProductCategoryId;
}
public void setprimaryProductCategoryId( java.lang.String primaryProductCategoryId ) {
this.primaryProductCategoryId = primaryProductCategoryId;
}
private java.lang.String returnable;
public java.lang.String getreturnable() {
return returnable;
}
public void setreturnable( java.lang.String returnable ) {
this.returnable = returnable;
}
private java.lang.String requirementMethodEnumId;
public java.lang.String getrequirementMethodEnumId() {
return requirementMethodEnumId;
}
public void setrequirementMethodEnumId( java.lang.String requirementMethodEnumId ) {
this.requirementMethodEnumId = requirementMethodEnumId;
}
private java.lang.String piecesIncluded;
public java.lang.String getpiecesIncluded() {
return piecesIncluded;
}
public void setpiecesIncluded( java.lang.String piecesIncluded ) {
this.piecesIncluded = piecesIncluded;
}
private java.lang.String depthUomId;
public java.lang.String getdepthUomId() {
return depthUomId;
}
public void setdepthUomId( java.lang.String depthUomId ) {
this.depthUomId = depthUomId;
}
private java.lang.String quantityUomId;
public java.lang.String getquantityUomId() {
return quantityUomId;
}
public void setquantityUomId( java.lang.String quantityUomId ) {
this.quantityUomId = quantityUomId;
}
private java.lang.String productTypeId;
public java.lang.String getproductTypeId() {
return productTypeId;
}
public void setproductTypeId( java.lang.String productTypeId ) {
this.productTypeId = productTypeId;
}
private java.lang.String weight;
public java.lang.String getweight() {
return weight;
}
public void setweight( java.lang.String weight ) {
this.weight = weight;
}
private java.lang.String virtualVariantMethodEnum;
public java.lang.String getvirtualVariantMethodEnum() {
return virtualVariantMethodEnum;
}
public void setvirtualVariantMethodEnum( java.lang.String virtualVariantMethodEnum ) {
this.virtualVariantMethodEnum = virtualVariantMethodEnum;
}
private java.sql.Timestamp salesDiscontinuationDate;
public java.sql.Timestamp getsalesDiscontinuationDate() {
return salesDiscontinuationDate;
}
public void setsalesDiscontinuationDate( java.sql.Timestamp salesDiscontinuationDate ) {
this.salesDiscontinuationDate = salesDiscontinuationDate;
}
private java.lang.String chargeShipping;
public java.lang.String getchargeShipping() {
return chargeShipping;
}
public void setchargeShipping( java.lang.String chargeShipping ) {
this.chargeShipping = chargeShipping;
}
private java.lang.String includeInPromotions;
public java.lang.String getincludeInPromotions() {
return includeInPromotions;
}
public void setincludeInPromotions( java.lang.String includeInPromotions ) {
this.includeInPromotions = includeInPromotions;
}
private java.sql.Timestamp supportDiscontinuationDate;
public java.sql.Timestamp getsupportDiscontinuationDate() {
return supportDiscontinuationDate;
}
public void setsupportDiscontinuationDate( java.sql.Timestamp supportDiscontinuationDate ) {
this.supportDiscontinuationDate = supportDiscontinuationDate;
}
private java.lang.String supplierPrefOrderId;
public java.lang.String getsupplierPrefOrderId() {
return supplierPrefOrderId;
}
public void setsupplierPrefOrderId( java.lang.String supplierPrefOrderId ) {
this.supplierPrefOrderId = supplierPrefOrderId;
}
private java.lang.String detailScreen;
public java.lang.String getdetailScreen() {
return detailScreen;
}
public void setdetailScreen( java.lang.String detailScreen ) {
this.detailScreen = detailScreen;
}
private java.lang.String originalImageUrl;
public java.lang.String getoriginalImageUrl() {
return originalImageUrl;
}
public void setoriginalImageUrl( java.lang.String originalImageUrl ) {
this.originalImageUrl = originalImageUrl;
}
private java.sql.Timestamp introductionDate;
public java.sql.Timestamp getintroductionDate() {
return introductionDate;
}
public void setintroductionDate( java.sql.Timestamp introductionDate ) {
this.introductionDate = introductionDate;
}
private java.lang.String requireAmount;
public java.lang.String getrequireAmount() {
return requireAmount;
}
public void setrequireAmount( java.lang.String requireAmount ) {
this.requireAmount = requireAmount;
}
private java.sql.Timestamp releaseDate;
public java.sql.Timestamp getreleaseDate() {
return releaseDate;
}
public void setreleaseDate( java.sql.Timestamp releaseDate ) {
this.releaseDate = releaseDate;
}
private java.lang.String shippingPrice;
public java.lang.String getshippingPrice() {
return shippingPrice;
}
public void setshippingPrice( java.lang.String shippingPrice ) {
this.shippingPrice = shippingPrice;
}
private java.lang.String productDepth;
public java.lang.String getproductDepth() {
return productDepth;
}
public void setproductDepth( java.lang.String productDepth ) {
this.productDepth = productDepth;
}
private java.lang.String shippingDepth;
public java.lang.String getshippingDepth() {
return shippingDepth;
}
public void setshippingDepth( java.lang.String shippingDepth ) {
this.shippingDepth = shippingDepth;
}
private java.lang.String largeImageUrl;
public java.lang.String getlargeImageUrl() {
return largeImageUrl;
}
public void setlargeImageUrl( java.lang.String largeImageUrl ) {
this.largeImageUrl = largeImageUrl;
}
private java.lang.String supplierProductName;
public java.lang.String getsupplierProductName() {
return supplierProductName;
}
public void setsupplierProductName( java.lang.String supplierProductName ) {
this.supplierProductName = supplierProductName;
}
private java.lang.String originGeoId;
public java.lang.String getoriginGeoId() {
return originGeoId;
}
public void setoriginGeoId( java.lang.String originGeoId ) {
this.originGeoId = originGeoId;
}
private java.lang.String reserv2ndPPPerc;
public java.lang.String getreserv2ndPPPerc() {
return reserv2ndPPPerc;
}
public void setreserv2ndPPPerc( java.lang.String reserv2ndPPPerc ) {
this.reserv2ndPPPerc = reserv2ndPPPerc;
}
private java.lang.String inShippingBox;
public java.lang.String getinShippingBox() {
return inShippingBox;
}
public void setinShippingBox( java.lang.String inShippingBox ) {
this.inShippingBox = inShippingBox;
}
private java.lang.String requireInventory;
public java.lang.String getrequireInventory() {
return requireInventory;
}
public void setrequireInventory( java.lang.String requireInventory ) {
this.requireInventory = requireInventory;
}
private java.lang.String detailImageUrl;
public java.lang.String getdetailImageUrl() {
return detailImageUrl;
}
public void setdetailImageUrl( java.lang.String detailImageUrl ) {
this.detailImageUrl = detailImageUrl;
}
private java.lang.String productWidth;
public java.lang.String getproductWidth() {
return productWidth;
}
public void setproductWidth( java.lang.String productWidth ) {
this.productWidth = productWidth;
}
private java.lang.String canDropShip;
public java.lang.String getcanDropShip() {
return canDropShip;
}
public void setcanDropShip( java.lang.String canDropShip ) {
this.canDropShip = canDropShip;
}
private java.lang.String manufacturerPartyId;
public java.lang.String getmanufacturerPartyId() {
return manufacturerPartyId;
}
public void setmanufacturerPartyId( java.lang.String manufacturerPartyId ) {
this.manufacturerPartyId = manufacturerPartyId;
}
private java.lang.String weightUomId;
public java.lang.String getweightUomId() {
return weightUomId;
}
public void setweightUomId( java.lang.String weightUomId ) {
this.weightUomId = weightUomId;
}
private java.lang.String productDiameter;
public java.lang.String getproductDiameter() {
return productDiameter;
}
public void setproductDiameter( java.lang.String productDiameter ) {
this.productDiameter = productDiameter;
}
private java.math.BigDecimal minimumOrderQuantity;
public java.math.BigDecimal getminimumOrderQuantity() {
return minimumOrderQuantity;
}
public void setminimumOrderQuantity( java.math.BigDecimal minimumOrderQuantity ) {
this.minimumOrderQuantity = minimumOrderQuantity;
}
private java.lang.String orderDecimalQuantity;
public java.lang.String getorderDecimalQuantity() {
return orderDecimalQuantity;
}
public void setorderDecimalQuantity( java.lang.String orderDecimalQuantity ) {
this.orderDecimalQuantity = orderDecimalQuantity;
}
private java.lang.String smallImageUrl;
public java.lang.String getsmallImageUrl() {
return smallImageUrl;
}
public void setsmallImageUrl( java.lang.String smallImageUrl ) {
this.smallImageUrl = smallImageUrl;
}
private java.lang.String agreementItemSeqId;
public java.lang.String getagreementItemSeqId() {
return agreementItemSeqId;
}
public void setagreementItemSeqId( java.lang.String agreementItemSeqId ) {
this.agreementItemSeqId = agreementItemSeqId;
}
private java.lang.String isVirtual;
public java.lang.String getisVirtual() {
return isVirtual;
}
public void setisVirtual( java.lang.String isVirtual ) {
this.isVirtual = isVirtual;
}
private java.lang.String priceDetailText;
public java.lang.String getpriceDetailText() {
return priceDetailText;
}
public void setpriceDetailText( java.lang.String priceDetailText ) {
this.priceDetailText = priceDetailText;
}
private java.math.BigDecimal orderQtyIncrements;
public java.math.BigDecimal getorderQtyIncrements() {
return orderQtyIncrements;
}
public void setorderQtyIncrements( java.math.BigDecimal orderQtyIncrements ) {
this.orderQtyIncrements = orderQtyIncrements;
}
private java.lang.String defaultShipmentBoxTypeId;
public java.lang.String getdefaultShipmentBoxTypeId() {
return defaultShipmentBoxTypeId;
}
public void setdefaultShipmentBoxTypeId( java.lang.String defaultShipmentBoxTypeId ) {
this.defaultShipmentBoxTypeId = defaultShipmentBoxTypeId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String lotIdFilledIn;
public java.lang.String getlotIdFilledIn() {
return lotIdFilledIn;
}
public void setlotIdFilledIn( java.lang.String lotIdFilledIn ) {
this.lotIdFilledIn = lotIdFilledIn;
}
private java.lang.String productWeight;
public java.lang.String getproductWeight() {
return productWeight;
}
public void setproductWeight( java.lang.String productWeight ) {
this.productWeight = productWeight;
}
private java.lang.String standardLeadTimeDays;
public java.lang.String getstandardLeadTimeDays() {
return standardLeadTimeDays;
}
public void setstandardLeadTimeDays( java.lang.String standardLeadTimeDays ) {
this.standardLeadTimeDays = standardLeadTimeDays;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.sql.Timestamp availableThruDate;
public java.sql.Timestamp getavailableThruDate() {
return availableThruDate;
}
public void setavailableThruDate( java.sql.Timestamp availableThruDate ) {
this.availableThruDate = availableThruDate;
}
private java.lang.String diameterUomId;
public java.lang.String getdiameterUomId() {
return diameterUomId;
}
public void setdiameterUomId( java.lang.String diameterUomId ) {
this.diameterUomId = diameterUomId;
}
private java.lang.String inventoryMessage;
public java.lang.String getinventoryMessage() {
return inventoryMessage;
}
public void setinventoryMessage( java.lang.String inventoryMessage ) {
this.inventoryMessage = inventoryMessage;
}
private java.lang.String productName;
public java.lang.String getproductName() {
return productName;
}
public void setproductName( java.lang.String productName ) {
this.productName = productName;
}
private java.lang.String shippingHeight;
public java.lang.String getshippingHeight() {
return shippingHeight;
}
public void setshippingHeight( java.lang.String shippingHeight ) {
this.shippingHeight = shippingHeight;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String isVariant;
public java.lang.String getisVariant() {
return isVariant;
}
public void setisVariant( java.lang.String isVariant ) {
this.isVariant = isVariant;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SupplierProductAndProduct> objectList = new ArrayList<SupplierProductAndProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new SupplierProductAndProduct(genVal));
        }
        return objectList;
    }    
}
