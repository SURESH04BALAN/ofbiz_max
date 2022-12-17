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
public class ProductAndCategoryMember implements GenericValueObjectInterface {
public static final String module =ProductAndCategoryMember.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductAndCategoryMember(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductAndCategoryMember () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"reservNthPPPerc","Reserv Nth Pp Perc"},
{"brandName","Brand Name"},
{"productHeight","Product Height"},
{"billOfMaterialLevel","Bill Of Material Level"},
{"quantityIncluded","Quantity Included"},
{"description","Description"},
{"longDescription","Long Description"},
{"heightUomId","Height Uom Id"},
{"widthUomId","Width Uom Id"},
{"reservMaxPersons","Reserv Max Persons"},
{"quantity","Quantity"},
{"mediumImageUrl","Medium Image Url"},
{"createdDate","Created Date"},
{"autoCreateKeywords","Auto Create Keywords"},
{"shippingWidth","Shipping Width"},
{"taxable","Taxable"},
{"salesDiscWhenNotAvail","Sales Disc When Not Avail"},
{"amountUomTypeId","Amount Uom Type Id"},
{"facilityId","Facility Id"},
{"configId","Config Id"},
{"productRating","Product Rating"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"fromDate","From Date"},
{"ratingTypeEnum","Rating Type Enum"},
{"fixedAmount","Fixed Amount"},
{"productCategoryId","Product Category Id"},
{"primaryProductCategoryId","Primary Product Category Id"},
{"returnable","Returnable"},
{"requirementMethodEnumId","Requirement Method Enum Id"},
{"depthUomId","Depth Uom Id"},
{"piecesIncluded","Pieces Included"},
{"quantityUomId","Quantity Uom Id"},
{"productTypeId","Product Type Id"},
{"weight","Weight"},
{"virtualVariantMethodEnum","Virtual Variant Method Enum"},
{"thruDate","Thru Date"},
{"salesDiscontinuationDate","Sales Discontinuation Date"},
{"chargeShipping","Charge Shipping"},
{"includeInPromotions","Include In Promotions"},
{"supportDiscontinuationDate","Support Discontinuation Date"},
{"originalImageUrl","Original Image Url"},
{"detailScreen","Detail Screen"},
{"introductionDate","Introduction Date"},
{"requireAmount","Require Amount"},
{"releaseDate","Release Date"},
{"productDepth","Product Depth"},
{"shippingDepth","Shipping Depth"},
{"largeImageUrl","Large Image Url"},
{"originGeoId","Origin Geo Id"},
{"reserv2ndPPPerc","Reserv 2Nd Pp Perc"},
{"inShippingBox","In Shipping Box"},
{"requireInventory","Require Inventory"},
{"detailImageUrl","Detail Image Url"},
{"productWidth","Product Width"},
{"weightUomId","Weight Uom Id"},
{"manufacturerPartyId","Manufacturer Party Id"},
{"productDiameter","Product Diameter"},
{"orderDecimalQuantity","Order Decimal Quantity"},
{"smallImageUrl","Small Image Url"},
{"isVirtual","Is Virtual"},
{"priceDetailText","Price Detail Text"},
{"defaultShipmentBoxTypeId","Default Shipment Box Type Id"},
{"productId","Product Id"},
{"sequenceNum","Sequence Num"},
{"lotIdFilledIn","Lot Id Filled In"},
{"productWeight","Product Weight"},
{"memberComments","Member Comments"},
{"lastModifiedDate","Last Modified Date"},
{"diameterUomId","Diameter Uom Id"},
{"shippingHeight","Shipping Height"},
{"inventoryMessage","Inventory Message"},
{"productName","Product Name"},
{"comments","Comments"},
{"isVariant","Is Variant"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.internalName = "";
this.reservNthPPPerc = "";
this.brandName = "";
this.productHeight = java.math.BigDecimal.ZERO;
this.billOfMaterialLevel = "";
this.quantityIncluded = "";
this.description = "";
this.longDescription = "";
this.heightUomId = "";
this.widthUomId = "";
this.reservMaxPersons = "";
this.quantity = "";
this.mediumImageUrl = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.autoCreateKeywords = "";
this.shippingWidth = "";
this.taxable = "";
this.salesDiscWhenNotAvail = "";
this.amountUomTypeId = "";
this.facilityId = "";
this.configId = "";
this.productRating = "";
this.lastModifiedByUserLogin = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.ratingTypeEnum = "";
this.fixedAmount = "";
this.productCategoryId = "";
this.primaryProductCategoryId = "";
this.returnable = "";
this.requirementMethodEnumId = "";
this.depthUomId = "";
this.piecesIncluded = "";
this.quantityUomId = "";
this.productTypeId = "";
this.weight = java.math.BigDecimal.ZERO;
this.virtualVariantMethodEnum = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.salesDiscontinuationDate = UtilDateTime.nowTimestamp();
this.chargeShipping = "";
this.includeInPromotions = "";
this.supportDiscontinuationDate = UtilDateTime.nowTimestamp();
this.originalImageUrl = "";
this.detailScreen = "";
this.introductionDate = UtilDateTime.nowTimestamp();
this.requireAmount = "";
this.releaseDate = UtilDateTime.nowTimestamp();
this.productDepth = "";
this.shippingDepth = "";
this.largeImageUrl = "";
this.originGeoId = "";
this.reserv2ndPPPerc = "";
this.inShippingBox = "";
this.requireInventory = "";
this.detailImageUrl = "";
this.productWidth = "";
this.weightUomId = "";
this.manufacturerPartyId = "";
this.productDiameter = "";
this.orderDecimalQuantity = "";
this.smallImageUrl = "";
this.isVirtual = "";
this.priceDetailText = "";
this.defaultShipmentBoxTypeId = "";
this.productId = "";
this.sequenceNum = "";
this.lotIdFilledIn = "";
this.productWeight = "";
this.memberComments = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.diameterUomId = "";
this.shippingHeight = "";
this.inventoryMessage = "";
this.productName = "";
this.comments = "";
this.isVariant = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.reservNthPPPerc = (java.lang.String) genVal.get("reservNthPPPerc");
this.brandName = (java.lang.String) genVal.get("brandName");
this.productHeight = (java.math.BigDecimal) genVal.get("productHeight");
this.billOfMaterialLevel = (java.lang.String) genVal.get("billOfMaterialLevel");
this.quantityIncluded = (java.lang.String) genVal.get("quantityIncluded");
this.description = (java.lang.String) genVal.get("description");
this.longDescription = (java.lang.String) genVal.get("longDescription");
this.heightUomId = (java.lang.String) genVal.get("heightUomId");
this.widthUomId = (java.lang.String) genVal.get("widthUomId");
this.reservMaxPersons = (java.lang.String) genVal.get("reservMaxPersons");
this.quantity = (java.lang.String) genVal.get("quantity");
this.mediumImageUrl = (java.lang.String) genVal.get("mediumImageUrl");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.autoCreateKeywords = (java.lang.String) genVal.get("autoCreateKeywords");
this.shippingWidth = (java.lang.String) genVal.get("shippingWidth");
this.taxable = (java.lang.String) genVal.get("taxable");
this.salesDiscWhenNotAvail = (java.lang.String) genVal.get("salesDiscWhenNotAvail");
this.amountUomTypeId = (java.lang.String) genVal.get("amountUomTypeId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.configId = (java.lang.String) genVal.get("configId");
this.productRating = (java.lang.String) genVal.get("productRating");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.ratingTypeEnum = (java.lang.String) genVal.get("ratingTypeEnum");
this.fixedAmount = (java.lang.String) genVal.get("fixedAmount");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
this.returnable = (java.lang.String) genVal.get("returnable");
this.requirementMethodEnumId = (java.lang.String) genVal.get("requirementMethodEnumId");
this.depthUomId = (java.lang.String) genVal.get("depthUomId");
this.piecesIncluded = (java.lang.String) genVal.get("piecesIncluded");
this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
this.productTypeId = (java.lang.String) genVal.get("productTypeId");
this.weight = (java.math.BigDecimal) genVal.get("weight");
this.virtualVariantMethodEnum = (java.lang.String) genVal.get("virtualVariantMethodEnum");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.salesDiscontinuationDate = (java.sql.Timestamp) genVal.get("salesDiscontinuationDate");
this.chargeShipping = (java.lang.String) genVal.get("chargeShipping");
this.includeInPromotions = (java.lang.String) genVal.get("includeInPromotions");
this.supportDiscontinuationDate = (java.sql.Timestamp) genVal.get("supportDiscontinuationDate");
this.originalImageUrl = (java.lang.String) genVal.get("originalImageUrl");
this.detailScreen = (java.lang.String) genVal.get("detailScreen");
this.introductionDate = (java.sql.Timestamp) genVal.get("introductionDate");
this.requireAmount = (java.lang.String) genVal.get("requireAmount");
this.releaseDate = (java.sql.Timestamp) genVal.get("releaseDate");
this.productDepth = (java.lang.String) genVal.get("productDepth");
this.shippingDepth = (java.lang.String) genVal.get("shippingDepth");
this.largeImageUrl = (java.lang.String) genVal.get("largeImageUrl");
this.originGeoId = (java.lang.String) genVal.get("originGeoId");
this.reserv2ndPPPerc = (java.lang.String) genVal.get("reserv2ndPPPerc");
this.inShippingBox = (java.lang.String) genVal.get("inShippingBox");
this.requireInventory = (java.lang.String) genVal.get("requireInventory");
this.detailImageUrl = (java.lang.String) genVal.get("detailImageUrl");
this.productWidth = (java.lang.String) genVal.get("productWidth");
this.weightUomId = (java.lang.String) genVal.get("weightUomId");
this.manufacturerPartyId = (java.lang.String) genVal.get("manufacturerPartyId");
this.productDiameter = (java.lang.String) genVal.get("productDiameter");
this.orderDecimalQuantity = (java.lang.String) genVal.get("orderDecimalQuantity");
this.smallImageUrl = (java.lang.String) genVal.get("smallImageUrl");
this.isVirtual = (java.lang.String) genVal.get("isVirtual");
this.priceDetailText = (java.lang.String) genVal.get("priceDetailText");
this.defaultShipmentBoxTypeId = (java.lang.String) genVal.get("defaultShipmentBoxTypeId");
this.productId = (java.lang.String) genVal.get("productId");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
this.lotIdFilledIn = (java.lang.String) genVal.get("lotIdFilledIn");
this.productWeight = (java.lang.String) genVal.get("productWeight");
this.memberComments = (java.lang.String) genVal.get("memberComments");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.diameterUomId = (java.lang.String) genVal.get("diameterUomId");
this.shippingHeight = (java.lang.String) genVal.get("shippingHeight");
this.inventoryMessage = (java.lang.String) genVal.get("inventoryMessage");
this.productName = (java.lang.String) genVal.get("productName");
this.comments = (java.lang.String) genVal.get("comments");
this.isVariant = (java.lang.String) genVal.get("isVariant");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("reservNthPPPerc",OrderMaxUtility.getValidEntityString(this.reservNthPPPerc));
val.set("brandName",OrderMaxUtility.getValidEntityString(this.brandName));
val.set("productHeight",OrderMaxUtility.getValidEntityBigDecimal(this.productHeight));
val.set("billOfMaterialLevel",OrderMaxUtility.getValidEntityString(this.billOfMaterialLevel));
val.set("quantityIncluded",OrderMaxUtility.getValidEntityString(this.quantityIncluded));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("longDescription",OrderMaxUtility.getValidEntityString(this.longDescription));
val.set("heightUomId",OrderMaxUtility.getValidEntityString(this.heightUomId));
val.set("widthUomId",OrderMaxUtility.getValidEntityString(this.widthUomId));
val.set("reservMaxPersons",OrderMaxUtility.getValidEntityString(this.reservMaxPersons));
val.set("quantity",OrderMaxUtility.getValidEntityString(this.quantity));
val.set("mediumImageUrl",OrderMaxUtility.getValidEntityString(this.mediumImageUrl));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("autoCreateKeywords",OrderMaxUtility.getValidEntityString(this.autoCreateKeywords));
val.set("shippingWidth",OrderMaxUtility.getValidEntityString(this.shippingWidth));
val.set("taxable",OrderMaxUtility.getValidEntityString(this.taxable));
val.set("salesDiscWhenNotAvail",OrderMaxUtility.getValidEntityString(this.salesDiscWhenNotAvail));
val.set("amountUomTypeId",OrderMaxUtility.getValidEntityString(this.amountUomTypeId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("configId",OrderMaxUtility.getValidEntityString(this.configId));
val.set("productRating",OrderMaxUtility.getValidEntityString(this.productRating));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("ratingTypeEnum",OrderMaxUtility.getValidEntityString(this.ratingTypeEnum));
val.set("fixedAmount",OrderMaxUtility.getValidEntityString(this.fixedAmount));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("primaryProductCategoryId",OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
val.set("returnable",OrderMaxUtility.getValidEntityString(this.returnable));
val.set("requirementMethodEnumId",OrderMaxUtility.getValidEntityString(this.requirementMethodEnumId));
val.set("depthUomId",OrderMaxUtility.getValidEntityString(this.depthUomId));
val.set("piecesIncluded",OrderMaxUtility.getValidEntityString(this.piecesIncluded));
val.set("quantityUomId",OrderMaxUtility.getValidEntityString(this.quantityUomId));
val.set("productTypeId",OrderMaxUtility.getValidEntityString(this.productTypeId));
val.set("weight",OrderMaxUtility.getValidEntityBigDecimal(this.weight));
val.set("virtualVariantMethodEnum",OrderMaxUtility.getValidEntityString(this.virtualVariantMethodEnum));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("salesDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.salesDiscontinuationDate));
val.set("chargeShipping",OrderMaxUtility.getValidEntityString(this.chargeShipping));
val.set("includeInPromotions",OrderMaxUtility.getValidEntityString(this.includeInPromotions));
val.set("supportDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.supportDiscontinuationDate));
val.set("originalImageUrl",OrderMaxUtility.getValidEntityString(this.originalImageUrl));
val.set("detailScreen",OrderMaxUtility.getValidEntityString(this.detailScreen));
val.set("introductionDate",OrderMaxUtility.getValidEntityTimestamp(this.introductionDate));
val.set("requireAmount",OrderMaxUtility.getValidEntityString(this.requireAmount));
val.set("releaseDate",OrderMaxUtility.getValidEntityTimestamp(this.releaseDate));
val.set("productDepth",OrderMaxUtility.getValidEntityString(this.productDepth));
val.set("shippingDepth",OrderMaxUtility.getValidEntityString(this.shippingDepth));
val.set("largeImageUrl",OrderMaxUtility.getValidEntityString(this.largeImageUrl));
val.set("originGeoId",OrderMaxUtility.getValidEntityString(this.originGeoId));
val.set("reserv2ndPPPerc",OrderMaxUtility.getValidEntityString(this.reserv2ndPPPerc));
val.set("inShippingBox",OrderMaxUtility.getValidEntityString(this.inShippingBox));
val.set("requireInventory",OrderMaxUtility.getValidEntityString(this.requireInventory));
val.set("detailImageUrl",OrderMaxUtility.getValidEntityString(this.detailImageUrl));
val.set("productWidth",OrderMaxUtility.getValidEntityString(this.productWidth));
val.set("weightUomId",OrderMaxUtility.getValidEntityString(this.weightUomId));
val.set("manufacturerPartyId",OrderMaxUtility.getValidEntityString(this.manufacturerPartyId));
val.set("productDiameter",OrderMaxUtility.getValidEntityString(this.productDiameter));
val.set("orderDecimalQuantity",OrderMaxUtility.getValidEntityString(this.orderDecimalQuantity));
val.set("smallImageUrl",OrderMaxUtility.getValidEntityString(this.smallImageUrl));
val.set("isVirtual",OrderMaxUtility.getValidEntityString(this.isVirtual));
val.set("priceDetailText",OrderMaxUtility.getValidEntityString(this.priceDetailText));
val.set("defaultShipmentBoxTypeId",OrderMaxUtility.getValidEntityString(this.defaultShipmentBoxTypeId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
val.set("lotIdFilledIn",OrderMaxUtility.getValidEntityString(this.lotIdFilledIn));
val.set("productWeight",OrderMaxUtility.getValidEntityString(this.productWeight));
val.set("memberComments",OrderMaxUtility.getValidEntityString(this.memberComments));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("diameterUomId",OrderMaxUtility.getValidEntityString(this.diameterUomId));
val.set("shippingHeight",OrderMaxUtility.getValidEntityString(this.shippingHeight));
val.set("inventoryMessage",OrderMaxUtility.getValidEntityString(this.inventoryMessage));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("isVariant",OrderMaxUtility.getValidEntityString(this.isVariant));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("reservNthPPPerc",this.reservNthPPPerc);
valueMap.put("brandName",this.brandName);
valueMap.put("productHeight",this.productHeight);
valueMap.put("billOfMaterialLevel",this.billOfMaterialLevel);
valueMap.put("quantityIncluded",this.quantityIncluded);
valueMap.put("description",this.description);
valueMap.put("longDescription",this.longDescription);
valueMap.put("heightUomId",this.heightUomId);
valueMap.put("widthUomId",this.widthUomId);
valueMap.put("reservMaxPersons",this.reservMaxPersons);
valueMap.put("quantity",this.quantity);
valueMap.put("mediumImageUrl",this.mediumImageUrl);
valueMap.put("createdDate",this.createdDate);
valueMap.put("autoCreateKeywords",this.autoCreateKeywords);
valueMap.put("shippingWidth",this.shippingWidth);
valueMap.put("taxable",this.taxable);
valueMap.put("salesDiscWhenNotAvail",this.salesDiscWhenNotAvail);
valueMap.put("amountUomTypeId",this.amountUomTypeId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("configId",this.configId);
valueMap.put("productRating",this.productRating);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("fromDate",this.fromDate);
valueMap.put("ratingTypeEnum",this.ratingTypeEnum);
valueMap.put("fixedAmount",this.fixedAmount);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("primaryProductCategoryId",this.primaryProductCategoryId);
valueMap.put("returnable",this.returnable);
valueMap.put("requirementMethodEnumId",this.requirementMethodEnumId);
valueMap.put("depthUomId",this.depthUomId);
valueMap.put("piecesIncluded",this.piecesIncluded);
valueMap.put("quantityUomId",this.quantityUomId);
valueMap.put("productTypeId",this.productTypeId);
valueMap.put("weight",this.weight);
valueMap.put("virtualVariantMethodEnum",this.virtualVariantMethodEnum);
valueMap.put("thruDate",this.thruDate);
valueMap.put("salesDiscontinuationDate",this.salesDiscontinuationDate);
valueMap.put("chargeShipping",this.chargeShipping);
valueMap.put("includeInPromotions",this.includeInPromotions);
valueMap.put("supportDiscontinuationDate",this.supportDiscontinuationDate);
valueMap.put("originalImageUrl",this.originalImageUrl);
valueMap.put("detailScreen",this.detailScreen);
valueMap.put("introductionDate",this.introductionDate);
valueMap.put("requireAmount",this.requireAmount);
valueMap.put("releaseDate",this.releaseDate);
valueMap.put("productDepth",this.productDepth);
valueMap.put("shippingDepth",this.shippingDepth);
valueMap.put("largeImageUrl",this.largeImageUrl);
valueMap.put("originGeoId",this.originGeoId);
valueMap.put("reserv2ndPPPerc",this.reserv2ndPPPerc);
valueMap.put("inShippingBox",this.inShippingBox);
valueMap.put("requireInventory",this.requireInventory);
valueMap.put("detailImageUrl",this.detailImageUrl);
valueMap.put("productWidth",this.productWidth);
valueMap.put("weightUomId",this.weightUomId);
valueMap.put("manufacturerPartyId",this.manufacturerPartyId);
valueMap.put("productDiameter",this.productDiameter);
valueMap.put("orderDecimalQuantity",this.orderDecimalQuantity);
valueMap.put("smallImageUrl",this.smallImageUrl);
valueMap.put("isVirtual",this.isVirtual);
valueMap.put("priceDetailText",this.priceDetailText);
valueMap.put("defaultShipmentBoxTypeId",this.defaultShipmentBoxTypeId);
valueMap.put("productId",this.productId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("lotIdFilledIn",this.lotIdFilledIn);
valueMap.put("productWeight",this.productWeight);
valueMap.put("memberComments",this.memberComments);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("diameterUomId",this.diameterUomId);
valueMap.put("shippingHeight",this.shippingHeight);
valueMap.put("inventoryMessage",this.inventoryMessage);
valueMap.put("productName",this.productName);
valueMap.put("comments",this.comments);
valueMap.put("isVariant",this.isVariant);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductAndCategoryMember");
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
private java.lang.String reservNthPPPerc;
public java.lang.String getreservNthPPPerc() {
return reservNthPPPerc;
}
public void setreservNthPPPerc( java.lang.String reservNthPPPerc ) {
this.reservNthPPPerc = reservNthPPPerc;
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
private java.lang.String quantity;
public java.lang.String getquantity() {
return quantity;
}
public void setquantity( java.lang.String quantity ) {
this.quantity = quantity;
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
private java.lang.String amountUomTypeId;
public java.lang.String getamountUomTypeId() {
return amountUomTypeId;
}
public void setamountUomTypeId( java.lang.String amountUomTypeId ) {
this.amountUomTypeId = amountUomTypeId;
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
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
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
private java.lang.String productCategoryId;
public java.lang.String getproductCategoryId() {
return productCategoryId;
}
public void setproductCategoryId( java.lang.String productCategoryId ) {
this.productCategoryId = productCategoryId;
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
private java.lang.String depthUomId;
public java.lang.String getdepthUomId() {
return depthUomId;
}
public void setdepthUomId( java.lang.String depthUomId ) {
this.depthUomId = depthUomId;
}
private java.lang.String piecesIncluded;
public java.lang.String getpiecesIncluded() {
return piecesIncluded;
}
public void setpiecesIncluded( java.lang.String piecesIncluded ) {
this.piecesIncluded = piecesIncluded;
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
private java.math.BigDecimal weight;
public java.math.BigDecimal getweight() {
return weight;
}
public void setweight( java.math.BigDecimal weight ) {
this.weight = weight;
}
private java.lang.String virtualVariantMethodEnum;
public java.lang.String getvirtualVariantMethodEnum() {
return virtualVariantMethodEnum;
}
public void setvirtualVariantMethodEnum( java.lang.String virtualVariantMethodEnum ) {
this.virtualVariantMethodEnum = virtualVariantMethodEnum;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
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
private java.lang.String originalImageUrl;
public java.lang.String getoriginalImageUrl() {
return originalImageUrl;
}
public void setoriginalImageUrl( java.lang.String originalImageUrl ) {
this.originalImageUrl = originalImageUrl;
}
private java.lang.String detailScreen;
public java.lang.String getdetailScreen() {
return detailScreen;
}
public void setdetailScreen( java.lang.String detailScreen ) {
this.detailScreen = detailScreen;
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
private java.lang.String weightUomId;
public java.lang.String getweightUomId() {
return weightUomId;
}
public void setweightUomId( java.lang.String weightUomId ) {
this.weightUomId = weightUomId;
}
private java.lang.String manufacturerPartyId;
public java.lang.String getmanufacturerPartyId() {
return manufacturerPartyId;
}
public void setmanufacturerPartyId( java.lang.String manufacturerPartyId ) {
this.manufacturerPartyId = manufacturerPartyId;
}
private java.lang.String productDiameter;
public java.lang.String getproductDiameter() {
return productDiameter;
}
public void setproductDiameter( java.lang.String productDiameter ) {
this.productDiameter = productDiameter;
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
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
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
private java.lang.String memberComments;
public java.lang.String getmemberComments() {
return memberComments;
}
public void setmemberComments( java.lang.String memberComments ) {
this.memberComments = memberComments;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String diameterUomId;
public java.lang.String getdiameterUomId() {
return diameterUomId;
}
public void setdiameterUomId( java.lang.String diameterUomId ) {
this.diameterUomId = diameterUomId;
}
private java.lang.String shippingHeight;
public java.lang.String getshippingHeight() {
return shippingHeight;
}
public void setshippingHeight( java.lang.String shippingHeight ) {
this.shippingHeight = shippingHeight;
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
        Collection<ProductAndCategoryMember> objectList = new ArrayList<ProductAndCategoryMember>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductAndCategoryMember(genVal));
        }
        return objectList;
    }    
}
