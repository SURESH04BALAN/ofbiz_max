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
public class OrderHeaderItemAndShipGroup implements GenericValueObjectInterface {
public static final String module =OrderHeaderItemAndShipGroup.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderItemAndShipGroup(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderItemAndShipGroup () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"needsInventoryIssuance","Needs Inventory Issuance"},
{"productStoreId","Product Store Id"},
{"isItemGroupPrimary","Is Item Group Primary"},
{"pickSheetPrintedDate","Pick Sheet Printed Date"},
{"orderName","Order Name"},
{"unitPrice","Unit Price"},
{"changeByUserLoginId","Change By User Login Id"},
{"overrideGlAccountId","Override Gl Account Id"},
{"terminalId","Terminal Id"},
{"vendorPartyId","Vendor Party Id"},
{"reservedQuantity","Reserved Quantity"},
{"dontCancelSetUserLogin","Dont Cancel Set User Login"},
{"remainingSubTotal","Remaining Sub Total"},
{"orderTypeId","Order Type Id"},
{"priority","Priority"},
{"isModifiedPrice","Is Modified Price"},
{"quantity","Quantity"},
{"shoppingListItemSeqId","Shopping List Item Seq Id"},
{"quoteId","Quote Id"},
{"unitListPrice","Unit List Price"},
{"billingAccountId","Billing Account Id"},
{"orderDate","Order Date"},
{"shipByDate","Ship By Date"},
{"grandTotal","Grand Total"},
{"oiShipAfterDate","Oi Ship After Date"},
{"carrierRoleTypeId","Carrier Role Type Id"},
{"deploymentId","Deployment Id"},
{"internalCode","Internal Code"},
{"telecomContactMechId","Telecom Contact Mech Id"},
{"recurringFreqUomId","Recurring Freq Uom Id"},
{"entryDate","Entry Date"},
{"facilityId","Facility Id"},
{"isGift","Is Gift"},
{"orderItemSeqId","Order Item Seq Id"},
{"productFeatureId","Product Feature Id"},
{"createdBy","Created By"},
{"trackingNumber","Tracking Number"},
{"oiQuantity","Oi Quantity"},
{"oiShipBeforeDate","Oi Ship Before Date"},
{"prodCatalogId","Prod Catalog Id"},
{"shoppingListId","Shopping List Id"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"webSiteId","Web Site Id"},
{"orderItemGroupSeqId","Order Item Group Seq Id"},
{"productCategoryId","Product Category Id"},
{"supplierProductId","Supplier Product Id"},
{"supplierPartyId","Supplier Party Id"},
{"budgetId","Budget Id"},
{"unitAverageCost","Unit Average Cost"},
{"transactionId","Transaction Id"},
{"originFacilityId","Origin Facility Id"},
{"invoicePerShipment","Invoice Per Shipment"},
{"oiSyncStatusId","Oi Sync Status Id"},
{"correspondingPoId","Corresponding Po Id"},
{"currencyUom","Currency Uom"},
{"oiEstimatedDeliveryDate","Oi Estimated Delivery Date"},
{"visitId","Visit Id"},
{"externalId","External Id"},
{"salesOpportunityId","Sales Opportunity Id"},
{"subscriptionId","Subscription Id"},
{"statusId","Status Id"},
{"budgetItemSeqId","Budget Item Seq Id"},
{"isViewed","Is Viewed"},
{"quoteItemSeqId","Quote Item Seq Id"},
{"shipAfterDate","Ship After Date"},
{"orderId","Order Id"},
{"carrierPartyId","Carrier Party Id"},
{"giftMessage","Gift Message"},
{"fromInventoryItemId","From Inventory Item Id"},
{"orderItemTypeId","Order Item Type Id"},
{"contactMechId","Contact Mech Id"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"oiCancelQuantity","Oi Cancel Quantity"},
{"shippingInstructions","Shipping Instructions"},
{"maySplit","May Split"},
{"productId","Product Id"},
{"isPromo","Is Promo"},
{"dontCancelSetDate","Dont Cancel Set Date"},
{"syncStatusId","Sync Status Id"},
{"oiStatusId","Oi Status Id"},
{"itemDescription","Item Description"},
{"unitRecurringPrice","Unit Recurring Price"},
{"selectedAmount","Selected Amount"},
{"isRushOrder","Is Rush Order"},
{"autoOrderShoppingListId","Auto Order Shopping List Id"},
{"oiExternalId","Oi External Id"},
{"oiEstimatedShipDate","Oi Estimated Ship Date"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"firstAttemptOrderId","First Attempt Order Id"},
{"cancelBackOrderDate","Cancel Back Order Date"},
{"estimatedShipDate","Estimated Ship Date"},
{"autoCancelDate","Auto Cancel Date"},
{"salesChannelEnumId","Sales Channel Enum Id"},
{"cancelQuantity","Cancel Quantity"},
{"comments","Comments"},
};
protected void initObject(){
this.needsInventoryIssuance = "";
this.productStoreId = "";
this.isItemGroupPrimary = "";
this.pickSheetPrintedDate = UtilDateTime.nowTimestamp();
this.orderName = "";
this.unitPrice = java.math.BigDecimal.ZERO;
this.changeByUserLoginId = "";
this.overrideGlAccountId = "";
this.terminalId = "";
this.vendorPartyId = "";
this.reservedQuantity = "";
this.dontCancelSetUserLogin = "";
this.remainingSubTotal = java.math.BigDecimal.ZERO;
this.orderTypeId = "";
this.priority = "";
this.isModifiedPrice = "";
this.quantity = java.math.BigDecimal.ZERO;
this.shoppingListItemSeqId = "";
this.quoteId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.billingAccountId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.shipByDate = UtilDateTime.nowTimestamp();
this.grandTotal = java.math.BigDecimal.ZERO;
this.oiShipAfterDate = UtilDateTime.nowTimestamp();
this.carrierRoleTypeId = "";
this.deploymentId = "";
this.internalCode = "";
this.telecomContactMechId = "";
this.recurringFreqUomId = "";
this.entryDate = UtilDateTime.nowTimestamp();
this.facilityId = "";
this.isGift = "";
this.orderItemSeqId = "";
this.productFeatureId = "";
this.createdBy = "";
this.trackingNumber = "";
this.oiQuantity = java.math.BigDecimal.ZERO;
this.oiShipBeforeDate = UtilDateTime.nowTimestamp();
this.prodCatalogId = "";
this.shoppingListId = "";
this.shipmentMethodTypeId = "";
this.webSiteId = "";
this.orderItemGroupSeqId = "";
this.productCategoryId = "";
this.supplierProductId = "";
this.supplierPartyId = "";
this.budgetId = "";
this.unitAverageCost = "";
this.transactionId = "";
this.originFacilityId = "";
this.invoicePerShipment = "";
this.oiSyncStatusId = "";
this.correspondingPoId = "";
this.currencyUom = "";
this.oiEstimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.visitId = "";
this.externalId = "";
this.salesOpportunityId = "";
this.subscriptionId = "";
this.statusId = "";
this.budgetItemSeqId = "";
this.isViewed = "";
this.quoteItemSeqId = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.orderId = "";
this.carrierPartyId = "";
this.giftMessage = "";
this.fromInventoryItemId = "";
this.orderItemTypeId = "";
this.contactMechId = "";
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.oiCancelQuantity = "";
this.shippingInstructions = "";
this.maySplit = "";
this.productId = "";
this.isPromo = "";
this.dontCancelSetDate = UtilDateTime.nowTimestamp();
this.syncStatusId = "";
this.oiStatusId = "";
this.itemDescription = "";
this.unitRecurringPrice = "";
this.selectedAmount = java.math.BigDecimal.ZERO;
this.isRushOrder = "";
this.autoOrderShoppingListId = "";
this.oiExternalId = "";
this.oiEstimatedShipDate = UtilDateTime.nowTimestamp();
this.shipGroupSeqId = "";
this.firstAttemptOrderId = "";
this.cancelBackOrderDate = UtilDateTime.nowTimestamp();
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.autoCancelDate = UtilDateTime.nowTimestamp();
this.salesChannelEnumId = "";
this.cancelQuantity = "";
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.needsInventoryIssuance = (java.lang.String) genVal.get("needsInventoryIssuance");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.isItemGroupPrimary = (java.lang.String) genVal.get("isItemGroupPrimary");
this.pickSheetPrintedDate = (java.sql.Timestamp) genVal.get("pickSheetPrintedDate");
this.orderName = (java.lang.String) genVal.get("orderName");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.changeByUserLoginId = (java.lang.String) genVal.get("changeByUserLoginId");
this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
this.terminalId = (java.lang.String) genVal.get("terminalId");
this.vendorPartyId = (java.lang.String) genVal.get("vendorPartyId");
this.reservedQuantity = (java.lang.String) genVal.get("reservedQuantity");
this.dontCancelSetUserLogin = (java.lang.String) genVal.get("dontCancelSetUserLogin");
this.remainingSubTotal = (java.math.BigDecimal) genVal.get("remainingSubTotal");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.priority = (java.lang.String) genVal.get("priority");
this.isModifiedPrice = (java.lang.String) genVal.get("isModifiedPrice");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.shoppingListItemSeqId = (java.lang.String) genVal.get("shoppingListItemSeqId");
this.quoteId = (java.lang.String) genVal.get("quoteId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.shipByDate = (java.sql.Timestamp) genVal.get("shipByDate");
this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
this.oiShipAfterDate = (java.sql.Timestamp) genVal.get("oiShipAfterDate");
this.carrierRoleTypeId = (java.lang.String) genVal.get("carrierRoleTypeId");
this.deploymentId = (java.lang.String) genVal.get("deploymentId");
this.internalCode = (java.lang.String) genVal.get("internalCode");
this.telecomContactMechId = (java.lang.String) genVal.get("telecomContactMechId");
this.recurringFreqUomId = (java.lang.String) genVal.get("recurringFreqUomId");
this.entryDate = (java.sql.Timestamp) genVal.get("entryDate");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.isGift = (java.lang.String) genVal.get("isGift");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
this.createdBy = (java.lang.String) genVal.get("createdBy");
this.trackingNumber = (java.lang.String) genVal.get("trackingNumber");
this.oiQuantity = (java.math.BigDecimal) genVal.get("oiQuantity");
this.oiShipBeforeDate = (java.sql.Timestamp) genVal.get("oiShipBeforeDate");
this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.orderItemGroupSeqId = (java.lang.String) genVal.get("orderItemGroupSeqId");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
this.supplierPartyId = (java.lang.String) genVal.get("supplierPartyId");
this.budgetId = (java.lang.String) genVal.get("budgetId");
this.unitAverageCost = (java.lang.String) genVal.get("unitAverageCost");
this.transactionId = (java.lang.String) genVal.get("transactionId");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.invoicePerShipment = (java.lang.String) genVal.get("invoicePerShipment");
this.oiSyncStatusId = (java.lang.String) genVal.get("oiSyncStatusId");
this.correspondingPoId = (java.lang.String) genVal.get("correspondingPoId");
this.currencyUom = (java.lang.String) genVal.get("currencyUom");
this.oiEstimatedDeliveryDate = (java.sql.Timestamp) genVal.get("oiEstimatedDeliveryDate");
this.visitId = (java.lang.String) genVal.get("visitId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.salesOpportunityId = (java.lang.String) genVal.get("salesOpportunityId");
this.subscriptionId = (java.lang.String) genVal.get("subscriptionId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.budgetItemSeqId = (java.lang.String) genVal.get("budgetItemSeqId");
this.isViewed = (java.lang.String) genVal.get("isViewed");
this.quoteItemSeqId = (java.lang.String) genVal.get("quoteItemSeqId");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.orderId = (java.lang.String) genVal.get("orderId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.giftMessage = (java.lang.String) genVal.get("giftMessage");
this.fromInventoryItemId = (java.lang.String) genVal.get("fromInventoryItemId");
this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.oiCancelQuantity = (java.lang.String) genVal.get("oiCancelQuantity");
this.shippingInstructions = (java.lang.String) genVal.get("shippingInstructions");
this.maySplit = (java.lang.String) genVal.get("maySplit");
this.productId = (java.lang.String) genVal.get("productId");
this.isPromo = (java.lang.String) genVal.get("isPromo");
this.dontCancelSetDate = (java.sql.Timestamp) genVal.get("dontCancelSetDate");
this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
this.oiStatusId = (java.lang.String) genVal.get("oiStatusId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.unitRecurringPrice = (java.lang.String) genVal.get("unitRecurringPrice");
this.selectedAmount = (java.math.BigDecimal) genVal.get("selectedAmount");
this.isRushOrder = (java.lang.String) genVal.get("isRushOrder");
this.autoOrderShoppingListId = (java.lang.String) genVal.get("autoOrderShoppingListId");
this.oiExternalId = (java.lang.String) genVal.get("oiExternalId");
this.oiEstimatedShipDate = (java.sql.Timestamp) genVal.get("oiEstimatedShipDate");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.firstAttemptOrderId = (java.lang.String) genVal.get("firstAttemptOrderId");
this.cancelBackOrderDate = (java.sql.Timestamp) genVal.get("cancelBackOrderDate");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.autoCancelDate = (java.sql.Timestamp) genVal.get("autoCancelDate");
this.salesChannelEnumId = (java.lang.String) genVal.get("salesChannelEnumId");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("needsInventoryIssuance",OrderMaxUtility.getValidEntityString(this.needsInventoryIssuance));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("isItemGroupPrimary",OrderMaxUtility.getValidEntityString(this.isItemGroupPrimary));
val.set("pickSheetPrintedDate",OrderMaxUtility.getValidEntityTimestamp(this.pickSheetPrintedDate));
val.set("orderName",OrderMaxUtility.getValidEntityString(this.orderName));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("changeByUserLoginId",OrderMaxUtility.getValidEntityString(this.changeByUserLoginId));
val.set("overrideGlAccountId",OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
val.set("terminalId",OrderMaxUtility.getValidEntityString(this.terminalId));
val.set("vendorPartyId",OrderMaxUtility.getValidEntityString(this.vendorPartyId));
val.set("reservedQuantity",OrderMaxUtility.getValidEntityString(this.reservedQuantity));
val.set("dontCancelSetUserLogin",OrderMaxUtility.getValidEntityString(this.dontCancelSetUserLogin));
val.set("remainingSubTotal",OrderMaxUtility.getValidEntityBigDecimal(this.remainingSubTotal));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("priority",OrderMaxUtility.getValidEntityString(this.priority));
val.set("isModifiedPrice",OrderMaxUtility.getValidEntityString(this.isModifiedPrice));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("shoppingListItemSeqId",OrderMaxUtility.getValidEntityString(this.shoppingListItemSeqId));
val.set("quoteId",OrderMaxUtility.getValidEntityString(this.quoteId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("shipByDate",OrderMaxUtility.getValidEntityTimestamp(this.shipByDate));
val.set("grandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
val.set("oiShipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.oiShipAfterDate));
val.set("carrierRoleTypeId",OrderMaxUtility.getValidEntityString(this.carrierRoleTypeId));
val.set("deploymentId",OrderMaxUtility.getValidEntityString(this.deploymentId));
val.set("internalCode",OrderMaxUtility.getValidEntityString(this.internalCode));
val.set("telecomContactMechId",OrderMaxUtility.getValidEntityString(this.telecomContactMechId));
val.set("recurringFreqUomId",OrderMaxUtility.getValidEntityString(this.recurringFreqUomId));
val.set("entryDate",OrderMaxUtility.getValidEntityTimestamp(this.entryDate));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("isGift",OrderMaxUtility.getValidEntityString(this.isGift));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("productFeatureId",OrderMaxUtility.getValidEntityString(this.productFeatureId));
val.set("createdBy",OrderMaxUtility.getValidEntityString(this.createdBy));
val.set("trackingNumber",OrderMaxUtility.getValidEntityString(this.trackingNumber));
val.set("oiQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.oiQuantity));
val.set("oiShipBeforeDate",OrderMaxUtility.getValidEntityTimestamp(this.oiShipBeforeDate));
val.set("prodCatalogId",OrderMaxUtility.getValidEntityString(this.prodCatalogId));
val.set("shoppingListId",OrderMaxUtility.getValidEntityString(this.shoppingListId));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("orderItemGroupSeqId",OrderMaxUtility.getValidEntityString(this.orderItemGroupSeqId));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("supplierProductId",OrderMaxUtility.getValidEntityString(this.supplierProductId));
val.set("supplierPartyId",OrderMaxUtility.getValidEntityString(this.supplierPartyId));
val.set("budgetId",OrderMaxUtility.getValidEntityString(this.budgetId));
val.set("unitAverageCost",OrderMaxUtility.getValidEntityString(this.unitAverageCost));
val.set("transactionId",OrderMaxUtility.getValidEntityString(this.transactionId));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("invoicePerShipment",OrderMaxUtility.getValidEntityString(this.invoicePerShipment));
val.set("oiSyncStatusId",OrderMaxUtility.getValidEntityString(this.oiSyncStatusId));
val.set("correspondingPoId",OrderMaxUtility.getValidEntityString(this.correspondingPoId));
val.set("currencyUom",OrderMaxUtility.getValidEntityString(this.currencyUom));
val.set("oiEstimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.oiEstimatedDeliveryDate));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("salesOpportunityId",OrderMaxUtility.getValidEntityString(this.salesOpportunityId));
val.set("subscriptionId",OrderMaxUtility.getValidEntityString(this.subscriptionId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("budgetItemSeqId",OrderMaxUtility.getValidEntityString(this.budgetItemSeqId));
val.set("isViewed",OrderMaxUtility.getValidEntityString(this.isViewed));
val.set("quoteItemSeqId",OrderMaxUtility.getValidEntityString(this.quoteItemSeqId));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("giftMessage",OrderMaxUtility.getValidEntityString(this.giftMessage));
val.set("fromInventoryItemId",OrderMaxUtility.getValidEntityString(this.fromInventoryItemId));
val.set("orderItemTypeId",OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("oiCancelQuantity",OrderMaxUtility.getValidEntityString(this.oiCancelQuantity));
val.set("shippingInstructions",OrderMaxUtility.getValidEntityString(this.shippingInstructions));
val.set("maySplit",OrderMaxUtility.getValidEntityString(this.maySplit));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("isPromo",OrderMaxUtility.getValidEntityString(this.isPromo));
val.set("dontCancelSetDate",OrderMaxUtility.getValidEntityTimestamp(this.dontCancelSetDate));
val.set("syncStatusId",OrderMaxUtility.getValidEntityString(this.syncStatusId));
val.set("oiStatusId",OrderMaxUtility.getValidEntityString(this.oiStatusId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("unitRecurringPrice",OrderMaxUtility.getValidEntityString(this.unitRecurringPrice));
val.set("selectedAmount",OrderMaxUtility.getValidEntityBigDecimal(this.selectedAmount));
val.set("isRushOrder",OrderMaxUtility.getValidEntityString(this.isRushOrder));
val.set("autoOrderShoppingListId",OrderMaxUtility.getValidEntityString(this.autoOrderShoppingListId));
val.set("oiExternalId",OrderMaxUtility.getValidEntityString(this.oiExternalId));
val.set("oiEstimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.oiEstimatedShipDate));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("firstAttemptOrderId",OrderMaxUtility.getValidEntityString(this.firstAttemptOrderId));
val.set("cancelBackOrderDate",OrderMaxUtility.getValidEntityTimestamp(this.cancelBackOrderDate));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("autoCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.autoCancelDate));
val.set("salesChannelEnumId",OrderMaxUtility.getValidEntityString(this.salesChannelEnumId));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("needsInventoryIssuance",this.needsInventoryIssuance);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("isItemGroupPrimary",this.isItemGroupPrimary);
valueMap.put("pickSheetPrintedDate",this.pickSheetPrintedDate);
valueMap.put("orderName",this.orderName);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("changeByUserLoginId",this.changeByUserLoginId);
valueMap.put("overrideGlAccountId",this.overrideGlAccountId);
valueMap.put("terminalId",this.terminalId);
valueMap.put("vendorPartyId",this.vendorPartyId);
valueMap.put("reservedQuantity",this.reservedQuantity);
valueMap.put("dontCancelSetUserLogin",this.dontCancelSetUserLogin);
valueMap.put("remainingSubTotal",this.remainingSubTotal);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("priority",this.priority);
valueMap.put("isModifiedPrice",this.isModifiedPrice);
valueMap.put("quantity",this.quantity);
valueMap.put("shoppingListItemSeqId",this.shoppingListItemSeqId);
valueMap.put("quoteId",this.quoteId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("billingAccountId",this.billingAccountId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("shipByDate",this.shipByDate);
valueMap.put("grandTotal",this.grandTotal);
valueMap.put("oiShipAfterDate",this.oiShipAfterDate);
valueMap.put("carrierRoleTypeId",this.carrierRoleTypeId);
valueMap.put("deploymentId",this.deploymentId);
valueMap.put("internalCode",this.internalCode);
valueMap.put("telecomContactMechId",this.telecomContactMechId);
valueMap.put("recurringFreqUomId",this.recurringFreqUomId);
valueMap.put("entryDate",this.entryDate);
valueMap.put("facilityId",this.facilityId);
valueMap.put("isGift",this.isGift);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("productFeatureId",this.productFeatureId);
valueMap.put("createdBy",this.createdBy);
valueMap.put("trackingNumber",this.trackingNumber);
valueMap.put("oiQuantity",this.oiQuantity);
valueMap.put("oiShipBeforeDate",this.oiShipBeforeDate);
valueMap.put("prodCatalogId",this.prodCatalogId);
valueMap.put("shoppingListId",this.shoppingListId);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("orderItemGroupSeqId",this.orderItemGroupSeqId);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("supplierProductId",this.supplierProductId);
valueMap.put("supplierPartyId",this.supplierPartyId);
valueMap.put("budgetId",this.budgetId);
valueMap.put("unitAverageCost",this.unitAverageCost);
valueMap.put("transactionId",this.transactionId);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("invoicePerShipment",this.invoicePerShipment);
valueMap.put("oiSyncStatusId",this.oiSyncStatusId);
valueMap.put("correspondingPoId",this.correspondingPoId);
valueMap.put("currencyUom",this.currencyUom);
valueMap.put("oiEstimatedDeliveryDate",this.oiEstimatedDeliveryDate);
valueMap.put("visitId",this.visitId);
valueMap.put("externalId",this.externalId);
valueMap.put("salesOpportunityId",this.salesOpportunityId);
valueMap.put("subscriptionId",this.subscriptionId);
valueMap.put("statusId",this.statusId);
valueMap.put("budgetItemSeqId",this.budgetItemSeqId);
valueMap.put("isViewed",this.isViewed);
valueMap.put("quoteItemSeqId",this.quoteItemSeqId);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("orderId",this.orderId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("giftMessage",this.giftMessage);
valueMap.put("fromInventoryItemId",this.fromInventoryItemId);
valueMap.put("orderItemTypeId",this.orderItemTypeId);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("oiCancelQuantity",this.oiCancelQuantity);
valueMap.put("shippingInstructions",this.shippingInstructions);
valueMap.put("maySplit",this.maySplit);
valueMap.put("productId",this.productId);
valueMap.put("isPromo",this.isPromo);
valueMap.put("dontCancelSetDate",this.dontCancelSetDate);
valueMap.put("syncStatusId",this.syncStatusId);
valueMap.put("oiStatusId",this.oiStatusId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("unitRecurringPrice",this.unitRecurringPrice);
valueMap.put("selectedAmount",this.selectedAmount);
valueMap.put("isRushOrder",this.isRushOrder);
valueMap.put("autoOrderShoppingListId",this.autoOrderShoppingListId);
valueMap.put("oiExternalId",this.oiExternalId);
valueMap.put("oiEstimatedShipDate",this.oiEstimatedShipDate);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("firstAttemptOrderId",this.firstAttemptOrderId);
valueMap.put("cancelBackOrderDate",this.cancelBackOrderDate);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("autoCancelDate",this.autoCancelDate);
valueMap.put("salesChannelEnumId",this.salesChannelEnumId);
valueMap.put("cancelQuantity",this.cancelQuantity);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderItemAndShipGroup");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String needsInventoryIssuance;
public java.lang.String getneedsInventoryIssuance() {
return needsInventoryIssuance;
}
public void setneedsInventoryIssuance( java.lang.String needsInventoryIssuance ) {
this.needsInventoryIssuance = needsInventoryIssuance;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String isItemGroupPrimary;
public java.lang.String getisItemGroupPrimary() {
return isItemGroupPrimary;
}
public void setisItemGroupPrimary( java.lang.String isItemGroupPrimary ) {
this.isItemGroupPrimary = isItemGroupPrimary;
}
private java.sql.Timestamp pickSheetPrintedDate;
public java.sql.Timestamp getpickSheetPrintedDate() {
return pickSheetPrintedDate;
}
public void setpickSheetPrintedDate( java.sql.Timestamp pickSheetPrintedDate ) {
this.pickSheetPrintedDate = pickSheetPrintedDate;
}
private java.lang.String orderName;
public java.lang.String getorderName() {
return orderName;
}
public void setorderName( java.lang.String orderName ) {
this.orderName = orderName;
}
private java.math.BigDecimal unitPrice;
public java.math.BigDecimal getunitPrice() {
return unitPrice;
}
public void setunitPrice( java.math.BigDecimal unitPrice ) {
this.unitPrice = unitPrice;
}
private java.lang.String changeByUserLoginId;
public java.lang.String getchangeByUserLoginId() {
return changeByUserLoginId;
}
public void setchangeByUserLoginId( java.lang.String changeByUserLoginId ) {
this.changeByUserLoginId = changeByUserLoginId;
}
private java.lang.String overrideGlAccountId;
public java.lang.String getoverrideGlAccountId() {
return overrideGlAccountId;
}
public void setoverrideGlAccountId( java.lang.String overrideGlAccountId ) {
this.overrideGlAccountId = overrideGlAccountId;
}
private java.lang.String terminalId;
public java.lang.String getterminalId() {
return terminalId;
}
public void setterminalId( java.lang.String terminalId ) {
this.terminalId = terminalId;
}
private java.lang.String vendorPartyId;
public java.lang.String getvendorPartyId() {
return vendorPartyId;
}
public void setvendorPartyId( java.lang.String vendorPartyId ) {
this.vendorPartyId = vendorPartyId;
}
private java.lang.String reservedQuantity;
public java.lang.String getreservedQuantity() {
return reservedQuantity;
}
public void setreservedQuantity( java.lang.String reservedQuantity ) {
this.reservedQuantity = reservedQuantity;
}
private java.lang.String dontCancelSetUserLogin;
public java.lang.String getdontCancelSetUserLogin() {
return dontCancelSetUserLogin;
}
public void setdontCancelSetUserLogin( java.lang.String dontCancelSetUserLogin ) {
this.dontCancelSetUserLogin = dontCancelSetUserLogin;
}
private java.math.BigDecimal remainingSubTotal;
public java.math.BigDecimal getremainingSubTotal() {
return remainingSubTotal;
}
public void setremainingSubTotal( java.math.BigDecimal remainingSubTotal ) {
this.remainingSubTotal = remainingSubTotal;
}
private java.lang.String orderTypeId;
public java.lang.String getorderTypeId() {
return orderTypeId;
}
public void setorderTypeId( java.lang.String orderTypeId ) {
this.orderTypeId = orderTypeId;
}
private java.lang.String priority;
public java.lang.String getpriority() {
return priority;
}
public void setpriority( java.lang.String priority ) {
this.priority = priority;
}
private java.lang.String isModifiedPrice;
public java.lang.String getisModifiedPrice() {
return isModifiedPrice;
}
public void setisModifiedPrice( java.lang.String isModifiedPrice ) {
this.isModifiedPrice = isModifiedPrice;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String shoppingListItemSeqId;
public java.lang.String getshoppingListItemSeqId() {
return shoppingListItemSeqId;
}
public void setshoppingListItemSeqId( java.lang.String shoppingListItemSeqId ) {
this.shoppingListItemSeqId = shoppingListItemSeqId;
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
private java.lang.String billingAccountId;
public java.lang.String getbillingAccountId() {
return billingAccountId;
}
public void setbillingAccountId( java.lang.String billingAccountId ) {
this.billingAccountId = billingAccountId;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.sql.Timestamp shipByDate;
public java.sql.Timestamp getshipByDate() {
return shipByDate;
}
public void setshipByDate( java.sql.Timestamp shipByDate ) {
this.shipByDate = shipByDate;
}
private java.math.BigDecimal grandTotal;
public java.math.BigDecimal getgrandTotal() {
return grandTotal;
}
public void setgrandTotal( java.math.BigDecimal grandTotal ) {
this.grandTotal = grandTotal;
}
private java.sql.Timestamp oiShipAfterDate;
public java.sql.Timestamp getoiShipAfterDate() {
return oiShipAfterDate;
}
public void setoiShipAfterDate( java.sql.Timestamp oiShipAfterDate ) {
this.oiShipAfterDate = oiShipAfterDate;
}
private java.lang.String carrierRoleTypeId;
public java.lang.String getcarrierRoleTypeId() {
return carrierRoleTypeId;
}
public void setcarrierRoleTypeId( java.lang.String carrierRoleTypeId ) {
this.carrierRoleTypeId = carrierRoleTypeId;
}
private java.lang.String deploymentId;
public java.lang.String getdeploymentId() {
return deploymentId;
}
public void setdeploymentId( java.lang.String deploymentId ) {
this.deploymentId = deploymentId;
}
private java.lang.String internalCode;
public java.lang.String getinternalCode() {
return internalCode;
}
public void setinternalCode( java.lang.String internalCode ) {
this.internalCode = internalCode;
}
private java.lang.String telecomContactMechId;
public java.lang.String gettelecomContactMechId() {
return telecomContactMechId;
}
public void settelecomContactMechId( java.lang.String telecomContactMechId ) {
this.telecomContactMechId = telecomContactMechId;
}
private java.lang.String recurringFreqUomId;
public java.lang.String getrecurringFreqUomId() {
return recurringFreqUomId;
}
public void setrecurringFreqUomId( java.lang.String recurringFreqUomId ) {
this.recurringFreqUomId = recurringFreqUomId;
}
private java.sql.Timestamp entryDate;
public java.sql.Timestamp getentryDate() {
return entryDate;
}
public void setentryDate( java.sql.Timestamp entryDate ) {
this.entryDate = entryDate;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String isGift;
public java.lang.String getisGift() {
return isGift;
}
public void setisGift( java.lang.String isGift ) {
this.isGift = isGift;
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
private java.lang.String createdBy;
public java.lang.String getcreatedBy() {
return createdBy;
}
public void setcreatedBy( java.lang.String createdBy ) {
this.createdBy = createdBy;
}
private java.lang.String trackingNumber;
public java.lang.String gettrackingNumber() {
return trackingNumber;
}
public void settrackingNumber( java.lang.String trackingNumber ) {
this.trackingNumber = trackingNumber;
}
private java.math.BigDecimal oiQuantity;
public java.math.BigDecimal getoiQuantity() {
return oiQuantity;
}
public void setoiQuantity( java.math.BigDecimal oiQuantity ) {
this.oiQuantity = oiQuantity;
}
private java.sql.Timestamp oiShipBeforeDate;
public java.sql.Timestamp getoiShipBeforeDate() {
return oiShipBeforeDate;
}
public void setoiShipBeforeDate( java.sql.Timestamp oiShipBeforeDate ) {
this.oiShipBeforeDate = oiShipBeforeDate;
}
private java.lang.String prodCatalogId;
public java.lang.String getprodCatalogId() {
return prodCatalogId;
}
public void setprodCatalogId( java.lang.String prodCatalogId ) {
this.prodCatalogId = prodCatalogId;
}
private java.lang.String shoppingListId;
public java.lang.String getshoppingListId() {
return shoppingListId;
}
public void setshoppingListId( java.lang.String shoppingListId ) {
this.shoppingListId = shoppingListId;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
}
private java.lang.String orderItemGroupSeqId;
public java.lang.String getorderItemGroupSeqId() {
return orderItemGroupSeqId;
}
public void setorderItemGroupSeqId( java.lang.String orderItemGroupSeqId ) {
this.orderItemGroupSeqId = orderItemGroupSeqId;
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
private java.lang.String supplierPartyId;
public java.lang.String getsupplierPartyId() {
return supplierPartyId;
}
public void setsupplierPartyId( java.lang.String supplierPartyId ) {
this.supplierPartyId = supplierPartyId;
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
private java.lang.String transactionId;
public java.lang.String gettransactionId() {
return transactionId;
}
public void settransactionId( java.lang.String transactionId ) {
this.transactionId = transactionId;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String invoicePerShipment;
public java.lang.String getinvoicePerShipment() {
return invoicePerShipment;
}
public void setinvoicePerShipment( java.lang.String invoicePerShipment ) {
this.invoicePerShipment = invoicePerShipment;
}
private java.lang.String oiSyncStatusId;
public java.lang.String getoiSyncStatusId() {
return oiSyncStatusId;
}
public void setoiSyncStatusId( java.lang.String oiSyncStatusId ) {
this.oiSyncStatusId = oiSyncStatusId;
}
private java.lang.String correspondingPoId;
public java.lang.String getcorrespondingPoId() {
return correspondingPoId;
}
public void setcorrespondingPoId( java.lang.String correspondingPoId ) {
this.correspondingPoId = correspondingPoId;
}
private java.lang.String currencyUom;
public java.lang.String getcurrencyUom() {
return currencyUom;
}
public void setcurrencyUom( java.lang.String currencyUom ) {
this.currencyUom = currencyUom;
}
private java.sql.Timestamp oiEstimatedDeliveryDate;
public java.sql.Timestamp getoiEstimatedDeliveryDate() {
return oiEstimatedDeliveryDate;
}
public void setoiEstimatedDeliveryDate( java.sql.Timestamp oiEstimatedDeliveryDate ) {
this.oiEstimatedDeliveryDate = oiEstimatedDeliveryDate;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
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
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String budgetItemSeqId;
public java.lang.String getbudgetItemSeqId() {
return budgetItemSeqId;
}
public void setbudgetItemSeqId( java.lang.String budgetItemSeqId ) {
this.budgetItemSeqId = budgetItemSeqId;
}
private java.lang.String isViewed;
public java.lang.String getisViewed() {
return isViewed;
}
public void setisViewed( java.lang.String isViewed ) {
this.isViewed = isViewed;
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
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String giftMessage;
public java.lang.String getgiftMessage() {
return giftMessage;
}
public void setgiftMessage( java.lang.String giftMessage ) {
this.giftMessage = giftMessage;
}
private java.lang.String fromInventoryItemId;
public java.lang.String getfromInventoryItemId() {
return fromInventoryItemId;
}
public void setfromInventoryItemId( java.lang.String fromInventoryItemId ) {
this.fromInventoryItemId = fromInventoryItemId;
}
private java.lang.String orderItemTypeId;
public java.lang.String getorderItemTypeId() {
return orderItemTypeId;
}
public void setorderItemTypeId( java.lang.String orderItemTypeId ) {
this.orderItemTypeId = orderItemTypeId;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.sql.Timestamp estimatedDeliveryDate;
public java.sql.Timestamp getestimatedDeliveryDate() {
return estimatedDeliveryDate;
}
public void setestimatedDeliveryDate( java.sql.Timestamp estimatedDeliveryDate ) {
this.estimatedDeliveryDate = estimatedDeliveryDate;
}
private java.lang.String oiCancelQuantity;
public java.lang.String getoiCancelQuantity() {
return oiCancelQuantity;
}
public void setoiCancelQuantity( java.lang.String oiCancelQuantity ) {
this.oiCancelQuantity = oiCancelQuantity;
}
private java.lang.String shippingInstructions;
public java.lang.String getshippingInstructions() {
return shippingInstructions;
}
public void setshippingInstructions( java.lang.String shippingInstructions ) {
this.shippingInstructions = shippingInstructions;
}
private java.lang.String maySplit;
public java.lang.String getmaySplit() {
return maySplit;
}
public void setmaySplit( java.lang.String maySplit ) {
this.maySplit = maySplit;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String isPromo;
public java.lang.String getisPromo() {
return isPromo;
}
public void setisPromo( java.lang.String isPromo ) {
this.isPromo = isPromo;
}
private java.sql.Timestamp dontCancelSetDate;
public java.sql.Timestamp getdontCancelSetDate() {
return dontCancelSetDate;
}
public void setdontCancelSetDate( java.sql.Timestamp dontCancelSetDate ) {
this.dontCancelSetDate = dontCancelSetDate;
}
private java.lang.String syncStatusId;
public java.lang.String getsyncStatusId() {
return syncStatusId;
}
public void setsyncStatusId( java.lang.String syncStatusId ) {
this.syncStatusId = syncStatusId;
}
private java.lang.String oiStatusId;
public java.lang.String getoiStatusId() {
return oiStatusId;
}
public void setoiStatusId( java.lang.String oiStatusId ) {
this.oiStatusId = oiStatusId;
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
private java.lang.String isRushOrder;
public java.lang.String getisRushOrder() {
return isRushOrder;
}
public void setisRushOrder( java.lang.String isRushOrder ) {
this.isRushOrder = isRushOrder;
}
private java.lang.String autoOrderShoppingListId;
public java.lang.String getautoOrderShoppingListId() {
return autoOrderShoppingListId;
}
public void setautoOrderShoppingListId( java.lang.String autoOrderShoppingListId ) {
this.autoOrderShoppingListId = autoOrderShoppingListId;
}
private java.lang.String oiExternalId;
public java.lang.String getoiExternalId() {
return oiExternalId;
}
public void setoiExternalId( java.lang.String oiExternalId ) {
this.oiExternalId = oiExternalId;
}
private java.sql.Timestamp oiEstimatedShipDate;
public java.sql.Timestamp getoiEstimatedShipDate() {
return oiEstimatedShipDate;
}
public void setoiEstimatedShipDate( java.sql.Timestamp oiEstimatedShipDate ) {
this.oiEstimatedShipDate = oiEstimatedShipDate;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.lang.String firstAttemptOrderId;
public java.lang.String getfirstAttemptOrderId() {
return firstAttemptOrderId;
}
public void setfirstAttemptOrderId( java.lang.String firstAttemptOrderId ) {
this.firstAttemptOrderId = firstAttemptOrderId;
}
private java.sql.Timestamp cancelBackOrderDate;
public java.sql.Timestamp getcancelBackOrderDate() {
return cancelBackOrderDate;
}
public void setcancelBackOrderDate( java.sql.Timestamp cancelBackOrderDate ) {
this.cancelBackOrderDate = cancelBackOrderDate;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.sql.Timestamp autoCancelDate;
public java.sql.Timestamp getautoCancelDate() {
return autoCancelDate;
}
public void setautoCancelDate( java.sql.Timestamp autoCancelDate ) {
this.autoCancelDate = autoCancelDate;
}
private java.lang.String salesChannelEnumId;
public java.lang.String getsalesChannelEnumId() {
return salesChannelEnumId;
}
public void setsalesChannelEnumId( java.lang.String salesChannelEnumId ) {
this.salesChannelEnumId = salesChannelEnumId;
}
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderItemAndShipGroup> objectList = new ArrayList<OrderHeaderItemAndShipGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderItemAndShipGroup(genVal));
        }
        return objectList;
    }    
}
