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
public class OrderItemAndProduct implements GenericValueObjectInterface {
public static final String module =OrderItemAndProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemAndProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemAndProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"unitPrice","Unit Price"},
{"productHeight","Product Height"},
{"dontCancelSetUserLogin","Dont Cancel Set User Login"},
{"quantity","Quantity"},
{"createdDate","Created Date"},
{"shoppingListItemSeqId","Shopping List Item Seq Id"},
{"shippingWidth","Shipping Width"},
{"taxable","Taxable"},
{"salesDiscWhenNotAvail","Sales Disc When Not Avail"},
{"amountUomTypeId","Amount Uom Type Id"},
{"deploymentId","Deployment Id"},
{"facilityId","Facility Id"},
{"configId","Config Id"},
{"shipBeforeDate","Ship Before Date"},
{"productRating","Product Rating"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"fixedAmount","Fixed Amount"},
{"shoppingListId","Shopping List Id"},
{"prodCatalogId","Prod Catalog Id"},
{"orderItemGroupSeqId","Order Item Group Seq Id"},
{"returnable","Returnable"},
{"primaryProductCategoryId","Primary Product Category Id"},
{"depthUomId","Depth Uom Id"},
{"piecesIncluded","Pieces Included"},
{"chargeShipping","Charge Shipping"},
{"correspondingPoId","Corresponding Po Id"},
{"includeInPromotions","Include In Promotions"},
{"externalId","External Id"},
{"salesOpportunityId","Sales Opportunity Id"},
{"subscriptionId","Subscription Id"},
{"detailScreen","Detail Screen"},
{"originalImageUrl","Original Image Url"},
{"requireAmount","Require Amount"},
{"budgetItemSeqId","Budget Item Seq Id"},
{"productDepth","Product Depth"},
{"shippingDepth","Shipping Depth"},
{"largeImageUrl","Large Image Url"},
{"originGeoId","Origin Geo Id"},
{"requireInventory","Require Inventory"},
{"orderId","Order Id"},
{"fromInventoryItemId","From Inventory Item Id"},
{"productDiameter","Product Diameter"},
{"orderDecimalQuantity","Order Decimal Quantity"},
{"syncStatusId","Sync Status Id"},
{"lotIdFilledIn","Lot Id Filled In"},
{"itemDescription","Item Description"},
{"unitRecurringPrice","Unit Recurring Price"},
{"selectedAmount","Selected Amount"},
{"productWeight","Product Weight"},
{"cancelBackOrderDate","Cancel Back Order Date"},
{"diameterUomId","Diameter Uom Id"},
{"autoCancelDate","Auto Cancel Date"},
{"shippingHeight","Shipping Height"},
{"comments","Comments"},
{"isVariant","Is Variant"},
{"internalName","Internal Name"},
{"isItemGroupPrimary","Is Item Group Primary"},
{"reservNthPPPerc","Reserv Nth Pp Perc"},
{"changeByUserLoginId","Change By User Login Id"},
{"brandName","Brand Name"},
{"overrideGlAccountId","Override Gl Account Id"},
{"billOfMaterialLevel","Bill Of Material Level"},
{"quantityIncluded","Quantity Included"},
{"description","Description"},
{"isModifiedPrice","Is Modified Price"},
{"longDescription","Long Description"},
{"heightUomId","Height Uom Id"},
{"widthUomId","Width Uom Id"},
{"reservMaxPersons","Reserv Max Persons"},
{"mediumImageUrl","Medium Image Url"},
{"autoCreateKeywords","Auto Create Keywords"},
{"quoteId","Quote Id"},
{"unitListPrice","Unit List Price"},
{"recurringFreqUomId","Recurring Freq Uom Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"productFeatureId","Product Feature Id"},
{"ratingTypeEnum","Rating Type Enum"},
{"productCategoryId","Product Category Id"},
{"supplierProductId","Supplier Product Id"},
{"requirementMethodEnumId","Requirement Method Enum Id"},
{"budgetId","Budget Id"},
{"unitAverageCost","Unit Average Cost"},
{"quantityUomId","Quantity Uom Id"},
{"productTypeId","Product Type Id"},
{"weight","Weight"},
{"virtualVariantMethodEnum","Virtual Variant Method Enum"},
{"salesDiscontinuationDate","Sales Discontinuation Date"},
{"supportDiscontinuationDate","Support Discontinuation Date"},
{"statusId","Status Id"},
{"introductionDate","Introduction Date"},
{"releaseDate","Release Date"},
{"quoteItemSeqId","Quote Item Seq Id"},
{"shipAfterDate","Ship After Date"},
{"reserv2ndPPPerc","Reserv 2Nd Pp Perc"},
{"inShippingBox","In Shipping Box"},
{"detailImageUrl","Detail Image Url"},
{"productWidth","Product Width"},
{"weightUomId","Weight Uom Id"},
{"manufacturerPartyId","Manufacturer Party Id"},
{"orderItemTypeId","Order Item Type Id"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"smallImageUrl","Small Image Url"},
{"isVirtual","Is Virtual"},
{"priceDetailText","Price Detail Text"},
{"defaultShipmentBoxTypeId","Default Shipment Box Type Id"},
{"isPromo","Is Promo"},
{"productId","Product Id"},
{"dontCancelSetDate","Dont Cancel Set Date"},
{"lastModifiedDate","Last Modified Date"},
{"estimatedShipDate","Estimated Ship Date"},
{"inventoryMessage","Inventory Message"},
{"productName","Product Name"},
{"cancelQuantity","Cancel Quantity"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.unitPrice = java.math.BigDecimal.ZERO;
this.productHeight = java.math.BigDecimal.ZERO;
this.dontCancelSetUserLogin = "";
this.quantity = java.math.BigDecimal.ZERO;
this.createdDate = UtilDateTime.nowTimestamp();
this.shoppingListItemSeqId = "";
this.shippingWidth = "";
this.taxable = "";
this.salesDiscWhenNotAvail = "";
this.amountUomTypeId = "";
this.deploymentId = "";
this.facilityId = "";
this.configId = "";
this.shipBeforeDate = UtilDateTime.nowTimestamp();
this.productRating = "";
this.lastModifiedByUserLogin = "";
this.fixedAmount = "";
this.shoppingListId = "";
this.prodCatalogId = "";
this.orderItemGroupSeqId = "";
this.returnable = "";
this.primaryProductCategoryId = "";
this.depthUomId = "";
this.piecesIncluded = "";
this.chargeShipping = "";
this.correspondingPoId = "";
this.includeInPromotions = "";
this.externalId = "";
this.salesOpportunityId = "";
this.subscriptionId = "";
this.detailScreen = "";
this.originalImageUrl = "";
this.requireAmount = "";
this.budgetItemSeqId = "";
this.productDepth = "";
this.shippingDepth = "";
this.largeImageUrl = "";
this.originGeoId = "";
this.requireInventory = "";
this.orderId = "";
this.fromInventoryItemId = "";
this.productDiameter = "";
this.orderDecimalQuantity = "";
this.syncStatusId = "";
this.lotIdFilledIn = "";
this.itemDescription = "";
this.unitRecurringPrice = "";
this.selectedAmount = java.math.BigDecimal.ZERO;
this.productWeight = "";
this.cancelBackOrderDate = UtilDateTime.nowTimestamp();
this.diameterUomId = "";
this.autoCancelDate = UtilDateTime.nowTimestamp();
this.shippingHeight = "";
this.comments = "";
this.isVariant = "";
this.internalName = "";
this.isItemGroupPrimary = "";
this.reservNthPPPerc = "";
this.changeByUserLoginId = "";
this.brandName = "";
this.overrideGlAccountId = "";
this.billOfMaterialLevel = "";
this.quantityIncluded = "";
this.description = "";
this.isModifiedPrice = "";
this.longDescription = "";
this.heightUomId = "";
this.widthUomId = "";
this.reservMaxPersons = "";
this.mediumImageUrl = "";
this.autoCreateKeywords = "";
this.quoteId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.recurringFreqUomId = "";
this.orderItemSeqId = "";
this.productFeatureId = "";
this.ratingTypeEnum = "";
this.productCategoryId = "";
this.supplierProductId = "";
this.requirementMethodEnumId = "";
this.budgetId = "";
this.unitAverageCost = "";
this.quantityUomId = "";
this.productTypeId = "";
this.weight = "";
this.virtualVariantMethodEnum = "";
this.salesDiscontinuationDate = UtilDateTime.nowTimestamp();
this.supportDiscontinuationDate = UtilDateTime.nowTimestamp();
this.statusId = "";
this.introductionDate = UtilDateTime.nowTimestamp();
this.releaseDate = UtilDateTime.nowTimestamp();
this.quoteItemSeqId = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.reserv2ndPPPerc = "";
this.inShippingBox = "";
this.detailImageUrl = "";
this.productWidth = "";
this.weightUomId = "";
this.manufacturerPartyId = "";
this.orderItemTypeId = "";
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.smallImageUrl = "";
this.isVirtual = "";
this.priceDetailText = "";
this.defaultShipmentBoxTypeId = "";
this.isPromo = "";
this.productId = "";
this.dontCancelSetDate = UtilDateTime.nowTimestamp();
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.inventoryMessage = "";
this.productName = "";
this.cancelQuantity = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.productHeight = (java.math.BigDecimal) genVal.get("productHeight");
this.dontCancelSetUserLogin = (java.lang.String) genVal.get("dontCancelSetUserLogin");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.shoppingListItemSeqId = (java.lang.String) genVal.get("shoppingListItemSeqId");
this.shippingWidth = (java.lang.String) genVal.get("shippingWidth");
this.taxable = (java.lang.String) genVal.get("taxable");
this.salesDiscWhenNotAvail = (java.lang.String) genVal.get("salesDiscWhenNotAvail");
this.amountUomTypeId = (java.lang.String) genVal.get("amountUomTypeId");
this.deploymentId = (java.lang.String) genVal.get("deploymentId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.configId = (java.lang.String) genVal.get("configId");
this.shipBeforeDate = (java.sql.Timestamp) genVal.get("shipBeforeDate");
this.productRating = (java.lang.String) genVal.get("productRating");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.fixedAmount = (java.lang.String) genVal.get("fixedAmount");
this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
this.orderItemGroupSeqId = (java.lang.String) genVal.get("orderItemGroupSeqId");
this.returnable = (java.lang.String) genVal.get("returnable");
this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
this.depthUomId = (java.lang.String) genVal.get("depthUomId");
this.piecesIncluded = (java.lang.String) genVal.get("piecesIncluded");
this.chargeShipping = (java.lang.String) genVal.get("chargeShipping");
this.correspondingPoId = (java.lang.String) genVal.get("correspondingPoId");
this.includeInPromotions = (java.lang.String) genVal.get("includeInPromotions");
this.externalId = (java.lang.String) genVal.get("externalId");
this.salesOpportunityId = (java.lang.String) genVal.get("salesOpportunityId");
this.subscriptionId = (java.lang.String) genVal.get("subscriptionId");
this.detailScreen = (java.lang.String) genVal.get("detailScreen");
this.originalImageUrl = (java.lang.String) genVal.get("originalImageUrl");
this.requireAmount = (java.lang.String) genVal.get("requireAmount");
this.budgetItemSeqId = (java.lang.String) genVal.get("budgetItemSeqId");
this.productDepth = (java.lang.String) genVal.get("productDepth");
this.shippingDepth = (java.lang.String) genVal.get("shippingDepth");
this.largeImageUrl = (java.lang.String) genVal.get("largeImageUrl");
this.originGeoId = (java.lang.String) genVal.get("originGeoId");
this.requireInventory = (java.lang.String) genVal.get("requireInventory");
this.orderId = (java.lang.String) genVal.get("orderId");
this.fromInventoryItemId = (java.lang.String) genVal.get("fromInventoryItemId");
this.productDiameter = (java.lang.String) genVal.get("productDiameter");
this.orderDecimalQuantity = (java.lang.String) genVal.get("orderDecimalQuantity");
this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
this.lotIdFilledIn = (java.lang.String) genVal.get("lotIdFilledIn");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.unitRecurringPrice = (java.lang.String) genVal.get("unitRecurringPrice");
this.selectedAmount = (java.math.BigDecimal) genVal.get("selectedAmount");
this.productWeight = (java.lang.String) genVal.get("productWeight");
this.cancelBackOrderDate = (java.sql.Timestamp) genVal.get("cancelBackOrderDate");
this.diameterUomId = (java.lang.String) genVal.get("diameterUomId");
this.autoCancelDate = (java.sql.Timestamp) genVal.get("autoCancelDate");
this.shippingHeight = (java.lang.String) genVal.get("shippingHeight");
this.comments = (java.lang.String) genVal.get("comments");
this.isVariant = (java.lang.String) genVal.get("isVariant");
this.internalName = (java.lang.String) genVal.get("internalName");
this.isItemGroupPrimary = (java.lang.String) genVal.get("isItemGroupPrimary");
this.reservNthPPPerc = (java.lang.String) genVal.get("reservNthPPPerc");
this.changeByUserLoginId = (java.lang.String) genVal.get("changeByUserLoginId");
this.brandName = (java.lang.String) genVal.get("brandName");
this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
this.billOfMaterialLevel = (java.lang.String) genVal.get("billOfMaterialLevel");
this.quantityIncluded = (java.lang.String) genVal.get("quantityIncluded");
this.description = (java.lang.String) genVal.get("description");
this.isModifiedPrice = (java.lang.String) genVal.get("isModifiedPrice");
this.longDescription = (java.lang.String) genVal.get("longDescription");
this.heightUomId = (java.lang.String) genVal.get("heightUomId");
this.widthUomId = (java.lang.String) genVal.get("widthUomId");
this.reservMaxPersons = (java.lang.String) genVal.get("reservMaxPersons");
this.mediumImageUrl = (java.lang.String) genVal.get("mediumImageUrl");
this.autoCreateKeywords = (java.lang.String) genVal.get("autoCreateKeywords");
this.quoteId = (java.lang.String) genVal.get("quoteId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.recurringFreqUomId = (java.lang.String) genVal.get("recurringFreqUomId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
this.ratingTypeEnum = (java.lang.String) genVal.get("ratingTypeEnum");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
this.requirementMethodEnumId = (java.lang.String) genVal.get("requirementMethodEnumId");
this.budgetId = (java.lang.String) genVal.get("budgetId");
this.unitAverageCost = (java.lang.String) genVal.get("unitAverageCost");
this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
this.productTypeId = (java.lang.String) genVal.get("productTypeId");
this.weight = (java.lang.String) genVal.get("weight");
this.virtualVariantMethodEnum = (java.lang.String) genVal.get("virtualVariantMethodEnum");
this.salesDiscontinuationDate = (java.sql.Timestamp) genVal.get("salesDiscontinuationDate");
this.supportDiscontinuationDate = (java.sql.Timestamp) genVal.get("supportDiscontinuationDate");
this.statusId = (java.lang.String) genVal.get("statusId");
this.introductionDate = (java.sql.Timestamp) genVal.get("introductionDate");
this.releaseDate = (java.sql.Timestamp) genVal.get("releaseDate");
this.quoteItemSeqId = (java.lang.String) genVal.get("quoteItemSeqId");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.reserv2ndPPPerc = (java.lang.String) genVal.get("reserv2ndPPPerc");
this.inShippingBox = (java.lang.String) genVal.get("inShippingBox");
this.detailImageUrl = (java.lang.String) genVal.get("detailImageUrl");
this.productWidth = (java.lang.String) genVal.get("productWidth");
this.weightUomId = (java.lang.String) genVal.get("weightUomId");
this.manufacturerPartyId = (java.lang.String) genVal.get("manufacturerPartyId");
this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.smallImageUrl = (java.lang.String) genVal.get("smallImageUrl");
this.isVirtual = (java.lang.String) genVal.get("isVirtual");
this.priceDetailText = (java.lang.String) genVal.get("priceDetailText");
this.defaultShipmentBoxTypeId = (java.lang.String) genVal.get("defaultShipmentBoxTypeId");
this.isPromo = (java.lang.String) genVal.get("isPromo");
this.productId = (java.lang.String) genVal.get("productId");
this.dontCancelSetDate = (java.sql.Timestamp) genVal.get("dontCancelSetDate");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.inventoryMessage = (java.lang.String) genVal.get("inventoryMessage");
this.productName = (java.lang.String) genVal.get("productName");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("productHeight",OrderMaxUtility.getValidEntityBigDecimal(this.productHeight));
val.set("dontCancelSetUserLogin",OrderMaxUtility.getValidEntityString(this.dontCancelSetUserLogin));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("shoppingListItemSeqId",OrderMaxUtility.getValidEntityString(this.shoppingListItemSeqId));
val.set("shippingWidth",OrderMaxUtility.getValidEntityString(this.shippingWidth));
val.set("taxable",OrderMaxUtility.getValidEntityString(this.taxable));
val.set("salesDiscWhenNotAvail",OrderMaxUtility.getValidEntityString(this.salesDiscWhenNotAvail));
val.set("amountUomTypeId",OrderMaxUtility.getValidEntityString(this.amountUomTypeId));
val.set("deploymentId",OrderMaxUtility.getValidEntityString(this.deploymentId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("configId",OrderMaxUtility.getValidEntityString(this.configId));
val.set("shipBeforeDate",OrderMaxUtility.getValidEntityTimestamp(this.shipBeforeDate));
val.set("productRating",OrderMaxUtility.getValidEntityString(this.productRating));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("fixedAmount",OrderMaxUtility.getValidEntityString(this.fixedAmount));
val.set("shoppingListId",OrderMaxUtility.getValidEntityString(this.shoppingListId));
val.set("prodCatalogId",OrderMaxUtility.getValidEntityString(this.prodCatalogId));
val.set("orderItemGroupSeqId",OrderMaxUtility.getValidEntityString(this.orderItemGroupSeqId));
val.set("returnable",OrderMaxUtility.getValidEntityString(this.returnable));
val.set("primaryProductCategoryId",OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
val.set("depthUomId",OrderMaxUtility.getValidEntityString(this.depthUomId));
val.set("piecesIncluded",OrderMaxUtility.getValidEntityString(this.piecesIncluded));
val.set("chargeShipping",OrderMaxUtility.getValidEntityString(this.chargeShipping));
val.set("correspondingPoId",OrderMaxUtility.getValidEntityString(this.correspondingPoId));
val.set("includeInPromotions",OrderMaxUtility.getValidEntityString(this.includeInPromotions));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("salesOpportunityId",OrderMaxUtility.getValidEntityString(this.salesOpportunityId));
val.set("subscriptionId",OrderMaxUtility.getValidEntityString(this.subscriptionId));
val.set("detailScreen",OrderMaxUtility.getValidEntityString(this.detailScreen));
val.set("originalImageUrl",OrderMaxUtility.getValidEntityString(this.originalImageUrl));
val.set("requireAmount",OrderMaxUtility.getValidEntityString(this.requireAmount));
val.set("budgetItemSeqId",OrderMaxUtility.getValidEntityString(this.budgetItemSeqId));
val.set("productDepth",OrderMaxUtility.getValidEntityString(this.productDepth));
val.set("shippingDepth",OrderMaxUtility.getValidEntityString(this.shippingDepth));
val.set("largeImageUrl",OrderMaxUtility.getValidEntityString(this.largeImageUrl));
val.set("originGeoId",OrderMaxUtility.getValidEntityString(this.originGeoId));
val.set("requireInventory",OrderMaxUtility.getValidEntityString(this.requireInventory));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("fromInventoryItemId",OrderMaxUtility.getValidEntityString(this.fromInventoryItemId));
val.set("productDiameter",OrderMaxUtility.getValidEntityString(this.productDiameter));
val.set("orderDecimalQuantity",OrderMaxUtility.getValidEntityString(this.orderDecimalQuantity));
val.set("syncStatusId",OrderMaxUtility.getValidEntityString(this.syncStatusId));
val.set("lotIdFilledIn",OrderMaxUtility.getValidEntityString(this.lotIdFilledIn));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("unitRecurringPrice",OrderMaxUtility.getValidEntityString(this.unitRecurringPrice));
val.set("selectedAmount",OrderMaxUtility.getValidEntityBigDecimal(this.selectedAmount));
val.set("productWeight",OrderMaxUtility.getValidEntityString(this.productWeight));
val.set("cancelBackOrderDate",OrderMaxUtility.getValidEntityTimestamp(this.cancelBackOrderDate));
val.set("diameterUomId",OrderMaxUtility.getValidEntityString(this.diameterUomId));
val.set("autoCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.autoCancelDate));
val.set("shippingHeight",OrderMaxUtility.getValidEntityString(this.shippingHeight));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("isVariant",OrderMaxUtility.getValidEntityString(this.isVariant));
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("isItemGroupPrimary",OrderMaxUtility.getValidEntityString(this.isItemGroupPrimary));
val.set("reservNthPPPerc",OrderMaxUtility.getValidEntityString(this.reservNthPPPerc));
val.set("changeByUserLoginId",OrderMaxUtility.getValidEntityString(this.changeByUserLoginId));
val.set("brandName",OrderMaxUtility.getValidEntityString(this.brandName));
val.set("overrideGlAccountId",OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
val.set("billOfMaterialLevel",OrderMaxUtility.getValidEntityString(this.billOfMaterialLevel));
val.set("quantityIncluded",OrderMaxUtility.getValidEntityString(this.quantityIncluded));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("isModifiedPrice",OrderMaxUtility.getValidEntityString(this.isModifiedPrice));
val.set("longDescription",OrderMaxUtility.getValidEntityString(this.longDescription));
val.set("heightUomId",OrderMaxUtility.getValidEntityString(this.heightUomId));
val.set("widthUomId",OrderMaxUtility.getValidEntityString(this.widthUomId));
val.set("reservMaxPersons",OrderMaxUtility.getValidEntityString(this.reservMaxPersons));
val.set("mediumImageUrl",OrderMaxUtility.getValidEntityString(this.mediumImageUrl));
val.set("autoCreateKeywords",OrderMaxUtility.getValidEntityString(this.autoCreateKeywords));
val.set("quoteId",OrderMaxUtility.getValidEntityString(this.quoteId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("recurringFreqUomId",OrderMaxUtility.getValidEntityString(this.recurringFreqUomId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("productFeatureId",OrderMaxUtility.getValidEntityString(this.productFeatureId));
val.set("ratingTypeEnum",OrderMaxUtility.getValidEntityString(this.ratingTypeEnum));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("supplierProductId",OrderMaxUtility.getValidEntityString(this.supplierProductId));
val.set("requirementMethodEnumId",OrderMaxUtility.getValidEntityString(this.requirementMethodEnumId));
val.set("budgetId",OrderMaxUtility.getValidEntityString(this.budgetId));
val.set("unitAverageCost",OrderMaxUtility.getValidEntityString(this.unitAverageCost));
val.set("quantityUomId",OrderMaxUtility.getValidEntityString(this.quantityUomId));
val.set("productTypeId",OrderMaxUtility.getValidEntityString(this.productTypeId));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("virtualVariantMethodEnum",OrderMaxUtility.getValidEntityString(this.virtualVariantMethodEnum));
val.set("salesDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.salesDiscontinuationDate));
val.set("supportDiscontinuationDate",OrderMaxUtility.getValidEntityTimestamp(this.supportDiscontinuationDate));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("introductionDate",OrderMaxUtility.getValidEntityTimestamp(this.introductionDate));
val.set("releaseDate",OrderMaxUtility.getValidEntityTimestamp(this.releaseDate));
val.set("quoteItemSeqId",OrderMaxUtility.getValidEntityString(this.quoteItemSeqId));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("reserv2ndPPPerc",OrderMaxUtility.getValidEntityString(this.reserv2ndPPPerc));
val.set("inShippingBox",OrderMaxUtility.getValidEntityString(this.inShippingBox));
val.set("detailImageUrl",OrderMaxUtility.getValidEntityString(this.detailImageUrl));
val.set("productWidth",OrderMaxUtility.getValidEntityString(this.productWidth));
val.set("weightUomId",OrderMaxUtility.getValidEntityString(this.weightUomId));
val.set("manufacturerPartyId",OrderMaxUtility.getValidEntityString(this.manufacturerPartyId));
val.set("orderItemTypeId",OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("smallImageUrl",OrderMaxUtility.getValidEntityString(this.smallImageUrl));
val.set("isVirtual",OrderMaxUtility.getValidEntityString(this.isVirtual));
val.set("priceDetailText",OrderMaxUtility.getValidEntityString(this.priceDetailText));
val.set("defaultShipmentBoxTypeId",OrderMaxUtility.getValidEntityString(this.defaultShipmentBoxTypeId));
val.set("isPromo",OrderMaxUtility.getValidEntityString(this.isPromo));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("dontCancelSetDate",OrderMaxUtility.getValidEntityTimestamp(this.dontCancelSetDate));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("inventoryMessage",OrderMaxUtility.getValidEntityString(this.inventoryMessage));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("productHeight",this.productHeight);
valueMap.put("dontCancelSetUserLogin",this.dontCancelSetUserLogin);
valueMap.put("quantity",this.quantity);
valueMap.put("createdDate",this.createdDate);
valueMap.put("shoppingListItemSeqId",this.shoppingListItemSeqId);
valueMap.put("shippingWidth",this.shippingWidth);
valueMap.put("taxable",this.taxable);
valueMap.put("salesDiscWhenNotAvail",this.salesDiscWhenNotAvail);
valueMap.put("amountUomTypeId",this.amountUomTypeId);
valueMap.put("deploymentId",this.deploymentId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("configId",this.configId);
valueMap.put("shipBeforeDate",this.shipBeforeDate);
valueMap.put("productRating",this.productRating);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("fixedAmount",this.fixedAmount);
valueMap.put("shoppingListId",this.shoppingListId);
valueMap.put("prodCatalogId",this.prodCatalogId);
valueMap.put("orderItemGroupSeqId",this.orderItemGroupSeqId);
valueMap.put("returnable",this.returnable);
valueMap.put("primaryProductCategoryId",this.primaryProductCategoryId);
valueMap.put("depthUomId",this.depthUomId);
valueMap.put("piecesIncluded",this.piecesIncluded);
valueMap.put("chargeShipping",this.chargeShipping);
valueMap.put("correspondingPoId",this.correspondingPoId);
valueMap.put("includeInPromotions",this.includeInPromotions);
valueMap.put("externalId",this.externalId);
valueMap.put("salesOpportunityId",this.salesOpportunityId);
valueMap.put("subscriptionId",this.subscriptionId);
valueMap.put("detailScreen",this.detailScreen);
valueMap.put("originalImageUrl",this.originalImageUrl);
valueMap.put("requireAmount",this.requireAmount);
valueMap.put("budgetItemSeqId",this.budgetItemSeqId);
valueMap.put("productDepth",this.productDepth);
valueMap.put("shippingDepth",this.shippingDepth);
valueMap.put("largeImageUrl",this.largeImageUrl);
valueMap.put("originGeoId",this.originGeoId);
valueMap.put("requireInventory",this.requireInventory);
valueMap.put("orderId",this.orderId);
valueMap.put("fromInventoryItemId",this.fromInventoryItemId);
valueMap.put("productDiameter",this.productDiameter);
valueMap.put("orderDecimalQuantity",this.orderDecimalQuantity);
valueMap.put("syncStatusId",this.syncStatusId);
valueMap.put("lotIdFilledIn",this.lotIdFilledIn);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("unitRecurringPrice",this.unitRecurringPrice);
valueMap.put("selectedAmount",this.selectedAmount);
valueMap.put("productWeight",this.productWeight);
valueMap.put("cancelBackOrderDate",this.cancelBackOrderDate);
valueMap.put("diameterUomId",this.diameterUomId);
valueMap.put("autoCancelDate",this.autoCancelDate);
valueMap.put("shippingHeight",this.shippingHeight);
valueMap.put("comments",this.comments);
valueMap.put("isVariant",this.isVariant);
valueMap.put("internalName",this.internalName);
valueMap.put("isItemGroupPrimary",this.isItemGroupPrimary);
valueMap.put("reservNthPPPerc",this.reservNthPPPerc);
valueMap.put("changeByUserLoginId",this.changeByUserLoginId);
valueMap.put("brandName",this.brandName);
valueMap.put("overrideGlAccountId",this.overrideGlAccountId);
valueMap.put("billOfMaterialLevel",this.billOfMaterialLevel);
valueMap.put("quantityIncluded",this.quantityIncluded);
valueMap.put("description",this.description);
valueMap.put("isModifiedPrice",this.isModifiedPrice);
valueMap.put("longDescription",this.longDescription);
valueMap.put("heightUomId",this.heightUomId);
valueMap.put("widthUomId",this.widthUomId);
valueMap.put("reservMaxPersons",this.reservMaxPersons);
valueMap.put("mediumImageUrl",this.mediumImageUrl);
valueMap.put("autoCreateKeywords",this.autoCreateKeywords);
valueMap.put("quoteId",this.quoteId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("recurringFreqUomId",this.recurringFreqUomId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("productFeatureId",this.productFeatureId);
valueMap.put("ratingTypeEnum",this.ratingTypeEnum);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("supplierProductId",this.supplierProductId);
valueMap.put("requirementMethodEnumId",this.requirementMethodEnumId);
valueMap.put("budgetId",this.budgetId);
valueMap.put("unitAverageCost",this.unitAverageCost);
valueMap.put("quantityUomId",this.quantityUomId);
valueMap.put("productTypeId",this.productTypeId);
valueMap.put("weight",this.weight);
valueMap.put("virtualVariantMethodEnum",this.virtualVariantMethodEnum);
valueMap.put("salesDiscontinuationDate",this.salesDiscontinuationDate);
valueMap.put("supportDiscontinuationDate",this.supportDiscontinuationDate);
valueMap.put("statusId",this.statusId);
valueMap.put("introductionDate",this.introductionDate);
valueMap.put("releaseDate",this.releaseDate);
valueMap.put("quoteItemSeqId",this.quoteItemSeqId);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("reserv2ndPPPerc",this.reserv2ndPPPerc);
valueMap.put("inShippingBox",this.inShippingBox);
valueMap.put("detailImageUrl",this.detailImageUrl);
valueMap.put("productWidth",this.productWidth);
valueMap.put("weightUomId",this.weightUomId);
valueMap.put("manufacturerPartyId",this.manufacturerPartyId);
valueMap.put("orderItemTypeId",this.orderItemTypeId);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("smallImageUrl",this.smallImageUrl);
valueMap.put("isVirtual",this.isVirtual);
valueMap.put("priceDetailText",this.priceDetailText);
valueMap.put("defaultShipmentBoxTypeId",this.defaultShipmentBoxTypeId);
valueMap.put("isPromo",this.isPromo);
valueMap.put("productId",this.productId);
valueMap.put("dontCancelSetDate",this.dontCancelSetDate);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("inventoryMessage",this.inventoryMessage);
valueMap.put("productName",this.productName);
valueMap.put("cancelQuantity",this.cancelQuantity);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemAndProduct");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal unitPrice;
public java.math.BigDecimal getunitPrice() {
return unitPrice;
}
public void setunitPrice( java.math.BigDecimal unitPrice ) {
this.unitPrice = unitPrice;
}
private java.math.BigDecimal productHeight;
public java.math.BigDecimal getproductHeight() {
return productHeight;
}
public void setproductHeight( java.math.BigDecimal productHeight ) {
this.productHeight = productHeight;
}
private java.lang.String dontCancelSetUserLogin;
public java.lang.String getdontCancelSetUserLogin() {
return dontCancelSetUserLogin;
}
public void setdontCancelSetUserLogin( java.lang.String dontCancelSetUserLogin ) {
this.dontCancelSetUserLogin = dontCancelSetUserLogin;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String shoppingListItemSeqId;
public java.lang.String getshoppingListItemSeqId() {
return shoppingListItemSeqId;
}
public void setshoppingListItemSeqId( java.lang.String shoppingListItemSeqId ) {
this.shoppingListItemSeqId = shoppingListItemSeqId;
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
private java.lang.String deploymentId;
public java.lang.String getdeploymentId() {
return deploymentId;
}
public void setdeploymentId( java.lang.String deploymentId ) {
this.deploymentId = deploymentId;
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
private java.sql.Timestamp shipBeforeDate;
public java.sql.Timestamp getshipBeforeDate() {
return shipBeforeDate;
}
public void setshipBeforeDate( java.sql.Timestamp shipBeforeDate ) {
this.shipBeforeDate = shipBeforeDate;
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
private java.lang.String fixedAmount;
public java.lang.String getfixedAmount() {
return fixedAmount;
}
public void setfixedAmount( java.lang.String fixedAmount ) {
this.fixedAmount = fixedAmount;
}
private java.lang.String shoppingListId;
public java.lang.String getshoppingListId() {
return shoppingListId;
}
public void setshoppingListId( java.lang.String shoppingListId ) {
this.shoppingListId = shoppingListId;
}
private java.lang.String prodCatalogId;
public java.lang.String getprodCatalogId() {
return prodCatalogId;
}
public void setprodCatalogId( java.lang.String prodCatalogId ) {
this.prodCatalogId = prodCatalogId;
}
private java.lang.String orderItemGroupSeqId;
public java.lang.String getorderItemGroupSeqId() {
return orderItemGroupSeqId;
}
public void setorderItemGroupSeqId( java.lang.String orderItemGroupSeqId ) {
this.orderItemGroupSeqId = orderItemGroupSeqId;
}
private java.lang.String returnable;
public java.lang.String getreturnable() {
return returnable;
}
public void setreturnable( java.lang.String returnable ) {
this.returnable = returnable;
}
private java.lang.String primaryProductCategoryId;
public java.lang.String getprimaryProductCategoryId() {
return primaryProductCategoryId;
}
public void setprimaryProductCategoryId( java.lang.String primaryProductCategoryId ) {
this.primaryProductCategoryId = primaryProductCategoryId;
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
private java.lang.String chargeShipping;
public java.lang.String getchargeShipping() {
return chargeShipping;
}
public void setchargeShipping( java.lang.String chargeShipping ) {
this.chargeShipping = chargeShipping;
}
private java.lang.String correspondingPoId;
public java.lang.String getcorrespondingPoId() {
return correspondingPoId;
}
public void setcorrespondingPoId( java.lang.String correspondingPoId ) {
this.correspondingPoId = correspondingPoId;
}
private java.lang.String includeInPromotions;
public java.lang.String getincludeInPromotions() {
return includeInPromotions;
}
public void setincludeInPromotions( java.lang.String includeInPromotions ) {
this.includeInPromotions = includeInPromotions;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
}
private java.lang.String salesOpportunityId;
public java.lang.String getsalesOpportunityId() {
return salesOpportunityId;
}
public void setsalesOpportunityId( java.lang.String salesOpportunityId ) {
this.salesOpportunityId = salesOpportunityId;
}
private java.lang.String subscriptionId;
public java.lang.String getsubscriptionId() {
return subscriptionId;
}
public void setsubscriptionId( java.lang.String subscriptionId ) {
this.subscriptionId = subscriptionId;
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
private java.lang.String requireAmount;
public java.lang.String getrequireAmount() {
return requireAmount;
}
public void setrequireAmount( java.lang.String requireAmount ) {
this.requireAmount = requireAmount;
}
private java.lang.String budgetItemSeqId;
public java.lang.String getbudgetItemSeqId() {
return budgetItemSeqId;
}
public void setbudgetItemSeqId( java.lang.String budgetItemSeqId ) {
this.budgetItemSeqId = budgetItemSeqId;
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
private java.lang.String requireInventory;
public java.lang.String getrequireInventory() {
return requireInventory;
}
public void setrequireInventory( java.lang.String requireInventory ) {
this.requireInventory = requireInventory;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String fromInventoryItemId;
public java.lang.String getfromInventoryItemId() {
return fromInventoryItemId;
}
public void setfromInventoryItemId( java.lang.String fromInventoryItemId ) {
this.fromInventoryItemId = fromInventoryItemId;
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
private java.lang.String syncStatusId;
public java.lang.String getsyncStatusId() {
return syncStatusId;
}
public void setsyncStatusId( java.lang.String syncStatusId ) {
this.syncStatusId = syncStatusId;
}
private java.lang.String lotIdFilledIn;
public java.lang.String getlotIdFilledIn() {
return lotIdFilledIn;
}
public void setlotIdFilledIn( java.lang.String lotIdFilledIn ) {
this.lotIdFilledIn = lotIdFilledIn;
}
private java.lang.String itemDescription;
public java.lang.String getitemDescription() {
return itemDescription;
}
public void setitemDescription( java.lang.String itemDescription ) {
this.itemDescription = itemDescription;
}
private java.lang.String unitRecurringPrice;
public java.lang.String getunitRecurringPrice() {
return unitRecurringPrice;
}
public void setunitRecurringPrice( java.lang.String unitRecurringPrice ) {
this.unitRecurringPrice = unitRecurringPrice;
}
private java.math.BigDecimal selectedAmount;
public java.math.BigDecimal getselectedAmount() {
return selectedAmount;
}
public void setselectedAmount( java.math.BigDecimal selectedAmount ) {
this.selectedAmount = selectedAmount;
}
private java.lang.String productWeight;
public java.lang.String getproductWeight() {
return productWeight;
}
public void setproductWeight( java.lang.String productWeight ) {
this.productWeight = productWeight;
}
private java.sql.Timestamp cancelBackOrderDate;
public java.sql.Timestamp getcancelBackOrderDate() {
return cancelBackOrderDate;
}
public void setcancelBackOrderDate( java.sql.Timestamp cancelBackOrderDate ) {
this.cancelBackOrderDate = cancelBackOrderDate;
}
private java.lang.String diameterUomId;
public java.lang.String getdiameterUomId() {
return diameterUomId;
}
public void setdiameterUomId( java.lang.String diameterUomId ) {
this.diameterUomId = diameterUomId;
}
private java.sql.Timestamp autoCancelDate;
public java.sql.Timestamp getautoCancelDate() {
return autoCancelDate;
}
public void setautoCancelDate( java.sql.Timestamp autoCancelDate ) {
this.autoCancelDate = autoCancelDate;
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
private java.lang.String internalName;
public java.lang.String getinternalName() {
return internalName;
}
public void setinternalName( java.lang.String internalName ) {
this.internalName = internalName;
}
private java.lang.String isItemGroupPrimary;
public java.lang.String getisItemGroupPrimary() {
return isItemGroupPrimary;
}
public void setisItemGroupPrimary( java.lang.String isItemGroupPrimary ) {
this.isItemGroupPrimary = isItemGroupPrimary;
}
private java.lang.String reservNthPPPerc;
public java.lang.String getreservNthPPPerc() {
return reservNthPPPerc;
}
public void setreservNthPPPerc( java.lang.String reservNthPPPerc ) {
this.reservNthPPPerc = reservNthPPPerc;
}
private java.lang.String changeByUserLoginId;
public java.lang.String getchangeByUserLoginId() {
return changeByUserLoginId;
}
public void setchangeByUserLoginId( java.lang.String changeByUserLoginId ) {
this.changeByUserLoginId = changeByUserLoginId;
}
private java.lang.String brandName;
public java.lang.String getbrandName() {
return brandName;
}
public void setbrandName( java.lang.String brandName ) {
this.brandName = brandName;
}
private java.lang.String overrideGlAccountId;
public java.lang.String getoverrideGlAccountId() {
return overrideGlAccountId;
}
public void setoverrideGlAccountId( java.lang.String overrideGlAccountId ) {
this.overrideGlAccountId = overrideGlAccountId;
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
private java.lang.String isModifiedPrice;
public java.lang.String getisModifiedPrice() {
return isModifiedPrice;
}
public void setisModifiedPrice( java.lang.String isModifiedPrice ) {
this.isModifiedPrice = isModifiedPrice;
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
private java.lang.String autoCreateKeywords;
public java.lang.String getautoCreateKeywords() {
return autoCreateKeywords;
}
public void setautoCreateKeywords( java.lang.String autoCreateKeywords ) {
this.autoCreateKeywords = autoCreateKeywords;
}
private java.lang.String quoteId;
public java.lang.String getquoteId() {
return quoteId;
}
public void setquoteId( java.lang.String quoteId ) {
this.quoteId = quoteId;
}
private java.math.BigDecimal unitListPrice;
public java.math.BigDecimal getunitListPrice() {
return unitListPrice;
}
public void setunitListPrice( java.math.BigDecimal unitListPrice ) {
this.unitListPrice = unitListPrice;
}
private java.lang.String recurringFreqUomId;
public java.lang.String getrecurringFreqUomId() {
return recurringFreqUomId;
}
public void setrecurringFreqUomId( java.lang.String recurringFreqUomId ) {
this.recurringFreqUomId = recurringFreqUomId;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String productFeatureId;
public java.lang.String getproductFeatureId() {
return productFeatureId;
}
public void setproductFeatureId( java.lang.String productFeatureId ) {
this.productFeatureId = productFeatureId;
}
private java.lang.String ratingTypeEnum;
public java.lang.String getratingTypeEnum() {
return ratingTypeEnum;
}
public void setratingTypeEnum( java.lang.String ratingTypeEnum ) {
this.ratingTypeEnum = ratingTypeEnum;
}
private java.lang.String productCategoryId;
public java.lang.String getproductCategoryId() {
return productCategoryId;
}
public void setproductCategoryId( java.lang.String productCategoryId ) {
this.productCategoryId = productCategoryId;
}
private java.lang.String supplierProductId;
public java.lang.String getsupplierProductId() {
return supplierProductId;
}
public void setsupplierProductId( java.lang.String supplierProductId ) {
this.supplierProductId = supplierProductId;
}
private java.lang.String requirementMethodEnumId;
public java.lang.String getrequirementMethodEnumId() {
return requirementMethodEnumId;
}
public void setrequirementMethodEnumId( java.lang.String requirementMethodEnumId ) {
this.requirementMethodEnumId = requirementMethodEnumId;
}
private java.lang.String budgetId;
public java.lang.String getbudgetId() {
return budgetId;
}
public void setbudgetId( java.lang.String budgetId ) {
this.budgetId = budgetId;
}
private java.lang.String unitAverageCost;
public java.lang.String getunitAverageCost() {
return unitAverageCost;
}
public void setunitAverageCost( java.lang.String unitAverageCost ) {
this.unitAverageCost = unitAverageCost;
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
private java.sql.Timestamp supportDiscontinuationDate;
public java.sql.Timestamp getsupportDiscontinuationDate() {
return supportDiscontinuationDate;
}
public void setsupportDiscontinuationDate( java.sql.Timestamp supportDiscontinuationDate ) {
this.supportDiscontinuationDate = supportDiscontinuationDate;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.sql.Timestamp introductionDate;
public java.sql.Timestamp getintroductionDate() {
return introductionDate;
}
public void setintroductionDate( java.sql.Timestamp introductionDate ) {
this.introductionDate = introductionDate;
}
private java.sql.Timestamp releaseDate;
public java.sql.Timestamp getreleaseDate() {
return releaseDate;
}
public void setreleaseDate( java.sql.Timestamp releaseDate ) {
this.releaseDate = releaseDate;
}
private java.lang.String quoteItemSeqId;
public java.lang.String getquoteItemSeqId() {
return quoteItemSeqId;
}
public void setquoteItemSeqId( java.lang.String quoteItemSeqId ) {
this.quoteItemSeqId = quoteItemSeqId;
}
private java.sql.Timestamp shipAfterDate;
public java.sql.Timestamp getshipAfterDate() {
return shipAfterDate;
}
public void setshipAfterDate( java.sql.Timestamp shipAfterDate ) {
this.shipAfterDate = shipAfterDate;
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
private java.lang.String orderItemTypeId;
public java.lang.String getorderItemTypeId() {
return orderItemTypeId;
}
public void setorderItemTypeId( java.lang.String orderItemTypeId ) {
this.orderItemTypeId = orderItemTypeId;
}
private java.sql.Timestamp estimatedDeliveryDate;
public java.sql.Timestamp getestimatedDeliveryDate() {
return estimatedDeliveryDate;
}
public void setestimatedDeliveryDate( java.sql.Timestamp estimatedDeliveryDate ) {
this.estimatedDeliveryDate = estimatedDeliveryDate;
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
private java.lang.String isPromo;
public java.lang.String getisPromo() {
return isPromo;
}
public void setisPromo( java.lang.String isPromo ) {
this.isPromo = isPromo;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.sql.Timestamp dontCancelSetDate;
public java.sql.Timestamp getdontCancelSetDate() {
return dontCancelSetDate;
}
public void setdontCancelSetDate( java.sql.Timestamp dontCancelSetDate ) {
this.dontCancelSetDate = dontCancelSetDate;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
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
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
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
        Collection<OrderItemAndProduct> objectList = new ArrayList<OrderItemAndProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemAndProduct(genVal));
        }
        return objectList;
    }    
}
