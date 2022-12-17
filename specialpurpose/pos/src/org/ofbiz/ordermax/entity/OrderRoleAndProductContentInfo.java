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
public class OrderRoleAndProductContentInfo implements GenericValueObjectInterface {
public static final String module =OrderRoleAndProductContentInfo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderRoleAndProductContentInfo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderRoleAndProductContentInfo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"purchaseFromDate","Purchase From Date"},
{"childLeafCount","Child Leaf Count"},
{"isItemGroupPrimary","Is Item Group Primary"},
{"ownerContentId","Owner Content Id"},
{"unitPrice","Unit Price"},
{"dataResourceId","Data Resource Id"},
{"changeByUserLoginId","Change By User Login Id"},
{"overrideGlAccountId","Override Gl Account Id"},
{"dataSourceId","Data Source Id"},
{"dontCancelSetUserLogin","Dont Cancel Set User Login"},
{"privilegeEnumId","Privilege Enum Id"},
{"templateDataResourceId","Template Data Resource Id"},
{"useCountLimit","Use Count Limit"},
{"description","Description"},
{"isModifiedPrice","Is Modified Price"},
{"quantity","Quantity"},
{"createdDate","Created Date"},
{"useTime","Use Time"},
{"shoppingListItemSeqId","Shopping List Item Seq Id"},
{"unitListPrice","Unit List Price"},
{"quoteId","Quote Id"},
{"deploymentId","Deployment Id"},
{"recurringFreqUomId","Recurring Freq Uom Id"},
{"contentName","Content Name"},
{"useRoleTypeId","Use Role Type Id"},
{"shipBeforeDate","Ship Before Date"},
{"characterSetId","Character Set Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"productFeatureId","Product Feature Id"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"prodCatalogId","Prod Catalog Id"},
{"shoppingListId","Shopping List Id"},
{"orderItemGroupSeqId","Order Item Group Seq Id"},
{"productCategoryId","Product Category Id"},
{"supplierProductId","Supplier Product Id"},
{"unitAverageCost","Unit Average Cost"},
{"budgetId","Budget Id"},
{"purchaseThruDate","Purchase Thru Date"},
{"useTimeUomId","Use Time Uom Id"},
{"thruDate","Thru Date"},
{"instanceOfContentId","Instance Of Content Id"},
{"correspondingPoId","Corresponding Po Id"},
{"contentStatusId","Content Status Id"},
{"externalId","External Id"},
{"salesOpportunityId","Sales Opportunity Id"},
{"subscriptionId","Subscription Id"},
{"contentId","Content Id"},
{"serviceName","Service Name"},
{"statusId","Status Id"},
{"childBranchCount","Child Branch Count"},
{"budgetItemSeqId","Budget Item Seq Id"},
{"quoteItemSeqId","Quote Item Seq Id"},
{"shipAfterDate","Ship After Date"},
{"decoratorContentId","Decorator Content Id"},
{"orderId","Order Id"},
{"fromInventoryItemId","From Inventory Item Id"},
{"orderItemTypeId","Order Item Type Id"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"mimeTypeId","Mime Type Id"},
{"dontCancelSetDate","Dont Cancel Set Date"},
{"productId","Product Id"},
{"isPromo","Is Promo"},
{"sequenceNum","Sequence Num"},
{"syncStatusId","Sync Status Id"},
{"itemDescription","Item Description"},
{"roleTypeId","Role Type Id"},
{"unitRecurringPrice","Unit Recurring Price"},
{"localeString","Locale String"},
{"selectedAmount","Selected Amount"},
{"contentTypeId","Content Type Id"},
{"cancelBackOrderDate","Cancel Back Order Date"},
{"lastModifiedDate","Last Modified Date"},
{"productContentTypeId","Product Content Type Id"},
{"autoCancelDate","Auto Cancel Date"},
{"estimatedShipDate","Estimated Ship Date"},
{"productName","Product Name"},
{"comments","Comments"},
{"cancelQuantity","Cancel Quantity"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.purchaseFromDate = UtilDateTime.nowTimestamp();
this.childLeafCount = "";
this.isItemGroupPrimary = "";
this.ownerContentId = "";
this.unitPrice = java.math.BigDecimal.ZERO;
this.dataResourceId = "";
this.changeByUserLoginId = "";
this.overrideGlAccountId = "";
this.dataSourceId = "";
this.dontCancelSetUserLogin = "";
this.privilegeEnumId = "";
this.templateDataResourceId = "";
this.useCountLimit = "";
this.description = "";
this.isModifiedPrice = "";
this.quantity = java.math.BigDecimal.ZERO;
this.createdDate = UtilDateTime.nowTimestamp();
this.useTime = "";
this.shoppingListItemSeqId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.quoteId = "";
this.deploymentId = "";
this.recurringFreqUomId = "";
this.contentName = "";
this.useRoleTypeId = "";
this.shipBeforeDate = UtilDateTime.nowTimestamp();
this.characterSetId = "";
this.orderItemSeqId = "";
this.productFeatureId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.prodCatalogId = "";
this.shoppingListId = "";
this.orderItemGroupSeqId = "";
this.productCategoryId = "";
this.supplierProductId = "";
this.unitAverageCost = "";
this.budgetId = "";
this.purchaseThruDate = UtilDateTime.nowTimestamp();
this.useTimeUomId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.instanceOfContentId = "";
this.correspondingPoId = "";
this.contentStatusId = "";
this.externalId = "";
this.salesOpportunityId = "";
this.subscriptionId = "";
this.contentId = "";
this.serviceName = "";
this.statusId = "";
this.childBranchCount = "";
this.budgetItemSeqId = "";
this.quoteItemSeqId = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.decoratorContentId = "";
this.orderId = "";
this.fromInventoryItemId = "";
this.orderItemTypeId = "";
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.mimeTypeId = "";
this.dontCancelSetDate = UtilDateTime.nowTimestamp();
this.productId = "";
this.isPromo = "";
this.sequenceNum = "";
this.syncStatusId = "";
this.itemDescription = "";
this.roleTypeId = "";
this.unitRecurringPrice = "";
this.localeString = "";
this.selectedAmount = java.math.BigDecimal.ZERO;
this.contentTypeId = "";
this.cancelBackOrderDate = UtilDateTime.nowTimestamp();
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.productContentTypeId = "";
this.autoCancelDate = UtilDateTime.nowTimestamp();
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.productName = "";
this.comments = "";
this.cancelQuantity = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.purchaseFromDate = (java.sql.Timestamp) genVal.get("purchaseFromDate");
this.childLeafCount = (java.lang.String) genVal.get("childLeafCount");
this.isItemGroupPrimary = (java.lang.String) genVal.get("isItemGroupPrimary");
this.ownerContentId = (java.lang.String) genVal.get("ownerContentId");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
this.changeByUserLoginId = (java.lang.String) genVal.get("changeByUserLoginId");
this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.dontCancelSetUserLogin = (java.lang.String) genVal.get("dontCancelSetUserLogin");
this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
this.templateDataResourceId = (java.lang.String) genVal.get("templateDataResourceId");
this.useCountLimit = (java.lang.String) genVal.get("useCountLimit");
this.description = (java.lang.String) genVal.get("description");
this.isModifiedPrice = (java.lang.String) genVal.get("isModifiedPrice");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.useTime = (java.lang.String) genVal.get("useTime");
this.shoppingListItemSeqId = (java.lang.String) genVal.get("shoppingListItemSeqId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.quoteId = (java.lang.String) genVal.get("quoteId");
this.deploymentId = (java.lang.String) genVal.get("deploymentId");
this.recurringFreqUomId = (java.lang.String) genVal.get("recurringFreqUomId");
this.contentName = (java.lang.String) genVal.get("contentName");
this.useRoleTypeId = (java.lang.String) genVal.get("useRoleTypeId");
this.shipBeforeDate = (java.sql.Timestamp) genVal.get("shipBeforeDate");
this.characterSetId = (java.lang.String) genVal.get("characterSetId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
this.orderItemGroupSeqId = (java.lang.String) genVal.get("orderItemGroupSeqId");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
this.unitAverageCost = (java.lang.String) genVal.get("unitAverageCost");
this.budgetId = (java.lang.String) genVal.get("budgetId");
this.purchaseThruDate = (java.sql.Timestamp) genVal.get("purchaseThruDate");
this.useTimeUomId = (java.lang.String) genVal.get("useTimeUomId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.instanceOfContentId = (java.lang.String) genVal.get("instanceOfContentId");
this.correspondingPoId = (java.lang.String) genVal.get("correspondingPoId");
this.contentStatusId = (java.lang.String) genVal.get("contentStatusId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.salesOpportunityId = (java.lang.String) genVal.get("salesOpportunityId");
this.subscriptionId = (java.lang.String) genVal.get("subscriptionId");
this.contentId = (java.lang.String) genVal.get("contentId");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.statusId = (java.lang.String) genVal.get("statusId");
this.childBranchCount = (java.lang.String) genVal.get("childBranchCount");
this.budgetItemSeqId = (java.lang.String) genVal.get("budgetItemSeqId");
this.quoteItemSeqId = (java.lang.String) genVal.get("quoteItemSeqId");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.decoratorContentId = (java.lang.String) genVal.get("decoratorContentId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.fromInventoryItemId = (java.lang.String) genVal.get("fromInventoryItemId");
this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
this.dontCancelSetDate = (java.sql.Timestamp) genVal.get("dontCancelSetDate");
this.productId = (java.lang.String) genVal.get("productId");
this.isPromo = (java.lang.String) genVal.get("isPromo");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.unitRecurringPrice = (java.lang.String) genVal.get("unitRecurringPrice");
this.localeString = (java.lang.String) genVal.get("localeString");
this.selectedAmount = (java.math.BigDecimal) genVal.get("selectedAmount");
this.contentTypeId = (java.lang.String) genVal.get("contentTypeId");
this.cancelBackOrderDate = (java.sql.Timestamp) genVal.get("cancelBackOrderDate");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.productContentTypeId = (java.lang.String) genVal.get("productContentTypeId");
this.autoCancelDate = (java.sql.Timestamp) genVal.get("autoCancelDate");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.productName = (java.lang.String) genVal.get("productName");
this.comments = (java.lang.String) genVal.get("comments");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("purchaseFromDate",OrderMaxUtility.getValidEntityTimestamp(this.purchaseFromDate));
val.set("childLeafCount",OrderMaxUtility.getValidEntityString(this.childLeafCount));
val.set("isItemGroupPrimary",OrderMaxUtility.getValidEntityString(this.isItemGroupPrimary));
val.set("ownerContentId",OrderMaxUtility.getValidEntityString(this.ownerContentId));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("dataResourceId",OrderMaxUtility.getValidEntityString(this.dataResourceId));
val.set("changeByUserLoginId",OrderMaxUtility.getValidEntityString(this.changeByUserLoginId));
val.set("overrideGlAccountId",OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("dontCancelSetUserLogin",OrderMaxUtility.getValidEntityString(this.dontCancelSetUserLogin));
val.set("privilegeEnumId",OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
val.set("templateDataResourceId",OrderMaxUtility.getValidEntityString(this.templateDataResourceId));
val.set("useCountLimit",OrderMaxUtility.getValidEntityString(this.useCountLimit));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("isModifiedPrice",OrderMaxUtility.getValidEntityString(this.isModifiedPrice));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("useTime",OrderMaxUtility.getValidEntityString(this.useTime));
val.set("shoppingListItemSeqId",OrderMaxUtility.getValidEntityString(this.shoppingListItemSeqId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("quoteId",OrderMaxUtility.getValidEntityString(this.quoteId));
val.set("deploymentId",OrderMaxUtility.getValidEntityString(this.deploymentId));
val.set("recurringFreqUomId",OrderMaxUtility.getValidEntityString(this.recurringFreqUomId));
val.set("contentName",OrderMaxUtility.getValidEntityString(this.contentName));
val.set("useRoleTypeId",OrderMaxUtility.getValidEntityString(this.useRoleTypeId));
val.set("shipBeforeDate",OrderMaxUtility.getValidEntityTimestamp(this.shipBeforeDate));
val.set("characterSetId",OrderMaxUtility.getValidEntityString(this.characterSetId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("productFeatureId",OrderMaxUtility.getValidEntityString(this.productFeatureId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("prodCatalogId",OrderMaxUtility.getValidEntityString(this.prodCatalogId));
val.set("shoppingListId",OrderMaxUtility.getValidEntityString(this.shoppingListId));
val.set("orderItemGroupSeqId",OrderMaxUtility.getValidEntityString(this.orderItemGroupSeqId));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("supplierProductId",OrderMaxUtility.getValidEntityString(this.supplierProductId));
val.set("unitAverageCost",OrderMaxUtility.getValidEntityString(this.unitAverageCost));
val.set("budgetId",OrderMaxUtility.getValidEntityString(this.budgetId));
val.set("purchaseThruDate",OrderMaxUtility.getValidEntityTimestamp(this.purchaseThruDate));
val.set("useTimeUomId",OrderMaxUtility.getValidEntityString(this.useTimeUomId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("instanceOfContentId",OrderMaxUtility.getValidEntityString(this.instanceOfContentId));
val.set("correspondingPoId",OrderMaxUtility.getValidEntityString(this.correspondingPoId));
val.set("contentStatusId",OrderMaxUtility.getValidEntityString(this.contentStatusId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("salesOpportunityId",OrderMaxUtility.getValidEntityString(this.salesOpportunityId));
val.set("subscriptionId",OrderMaxUtility.getValidEntityString(this.subscriptionId));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("childBranchCount",OrderMaxUtility.getValidEntityString(this.childBranchCount));
val.set("budgetItemSeqId",OrderMaxUtility.getValidEntityString(this.budgetItemSeqId));
val.set("quoteItemSeqId",OrderMaxUtility.getValidEntityString(this.quoteItemSeqId));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("decoratorContentId",OrderMaxUtility.getValidEntityString(this.decoratorContentId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("fromInventoryItemId",OrderMaxUtility.getValidEntityString(this.fromInventoryItemId));
val.set("orderItemTypeId",OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("mimeTypeId",OrderMaxUtility.getValidEntityString(this.mimeTypeId));
val.set("dontCancelSetDate",OrderMaxUtility.getValidEntityTimestamp(this.dontCancelSetDate));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("isPromo",OrderMaxUtility.getValidEntityString(this.isPromo));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
val.set("syncStatusId",OrderMaxUtility.getValidEntityString(this.syncStatusId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("unitRecurringPrice",OrderMaxUtility.getValidEntityString(this.unitRecurringPrice));
val.set("localeString",OrderMaxUtility.getValidEntityString(this.localeString));
val.set("selectedAmount",OrderMaxUtility.getValidEntityBigDecimal(this.selectedAmount));
val.set("contentTypeId",OrderMaxUtility.getValidEntityString(this.contentTypeId));
val.set("cancelBackOrderDate",OrderMaxUtility.getValidEntityTimestamp(this.cancelBackOrderDate));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("productContentTypeId",OrderMaxUtility.getValidEntityString(this.productContentTypeId));
val.set("autoCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.autoCancelDate));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("purchaseFromDate",this.purchaseFromDate);
valueMap.put("childLeafCount",this.childLeafCount);
valueMap.put("isItemGroupPrimary",this.isItemGroupPrimary);
valueMap.put("ownerContentId",this.ownerContentId);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("dataResourceId",this.dataResourceId);
valueMap.put("changeByUserLoginId",this.changeByUserLoginId);
valueMap.put("overrideGlAccountId",this.overrideGlAccountId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("dontCancelSetUserLogin",this.dontCancelSetUserLogin);
valueMap.put("privilegeEnumId",this.privilegeEnumId);
valueMap.put("templateDataResourceId",this.templateDataResourceId);
valueMap.put("useCountLimit",this.useCountLimit);
valueMap.put("description",this.description);
valueMap.put("isModifiedPrice",this.isModifiedPrice);
valueMap.put("quantity",this.quantity);
valueMap.put("createdDate",this.createdDate);
valueMap.put("useTime",this.useTime);
valueMap.put("shoppingListItemSeqId",this.shoppingListItemSeqId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("quoteId",this.quoteId);
valueMap.put("deploymentId",this.deploymentId);
valueMap.put("recurringFreqUomId",this.recurringFreqUomId);
valueMap.put("contentName",this.contentName);
valueMap.put("useRoleTypeId",this.useRoleTypeId);
valueMap.put("shipBeforeDate",this.shipBeforeDate);
valueMap.put("characterSetId",this.characterSetId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("productFeatureId",this.productFeatureId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("prodCatalogId",this.prodCatalogId);
valueMap.put("shoppingListId",this.shoppingListId);
valueMap.put("orderItemGroupSeqId",this.orderItemGroupSeqId);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("supplierProductId",this.supplierProductId);
valueMap.put("unitAverageCost",this.unitAverageCost);
valueMap.put("budgetId",this.budgetId);
valueMap.put("purchaseThruDate",this.purchaseThruDate);
valueMap.put("useTimeUomId",this.useTimeUomId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("instanceOfContentId",this.instanceOfContentId);
valueMap.put("correspondingPoId",this.correspondingPoId);
valueMap.put("contentStatusId",this.contentStatusId);
valueMap.put("externalId",this.externalId);
valueMap.put("salesOpportunityId",this.salesOpportunityId);
valueMap.put("subscriptionId",this.subscriptionId);
valueMap.put("contentId",this.contentId);
valueMap.put("serviceName",this.serviceName);
valueMap.put("statusId",this.statusId);
valueMap.put("childBranchCount",this.childBranchCount);
valueMap.put("budgetItemSeqId",this.budgetItemSeqId);
valueMap.put("quoteItemSeqId",this.quoteItemSeqId);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("decoratorContentId",this.decoratorContentId);
valueMap.put("orderId",this.orderId);
valueMap.put("fromInventoryItemId",this.fromInventoryItemId);
valueMap.put("orderItemTypeId",this.orderItemTypeId);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("mimeTypeId",this.mimeTypeId);
valueMap.put("dontCancelSetDate",this.dontCancelSetDate);
valueMap.put("productId",this.productId);
valueMap.put("isPromo",this.isPromo);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("syncStatusId",this.syncStatusId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("unitRecurringPrice",this.unitRecurringPrice);
valueMap.put("localeString",this.localeString);
valueMap.put("selectedAmount",this.selectedAmount);
valueMap.put("contentTypeId",this.contentTypeId);
valueMap.put("cancelBackOrderDate",this.cancelBackOrderDate);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("productContentTypeId",this.productContentTypeId);
valueMap.put("autoCancelDate",this.autoCancelDate);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("productName",this.productName);
valueMap.put("comments",this.comments);
valueMap.put("cancelQuantity",this.cancelQuantity);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderRoleAndProductContentInfo");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp purchaseFromDate;
public java.sql.Timestamp getpurchaseFromDate() {
return purchaseFromDate;
}
public void setpurchaseFromDate( java.sql.Timestamp purchaseFromDate ) {
this.purchaseFromDate = purchaseFromDate;
}
private java.lang.String childLeafCount;
public java.lang.String getchildLeafCount() {
return childLeafCount;
}
public void setchildLeafCount( java.lang.String childLeafCount ) {
this.childLeafCount = childLeafCount;
}
private java.lang.String isItemGroupPrimary;
public java.lang.String getisItemGroupPrimary() {
return isItemGroupPrimary;
}
public void setisItemGroupPrimary( java.lang.String isItemGroupPrimary ) {
this.isItemGroupPrimary = isItemGroupPrimary;
}
private java.lang.String ownerContentId;
public java.lang.String getownerContentId() {
return ownerContentId;
}
public void setownerContentId( java.lang.String ownerContentId ) {
this.ownerContentId = ownerContentId;
}
private java.math.BigDecimal unitPrice;
public java.math.BigDecimal getunitPrice() {
return unitPrice;
}
public void setunitPrice( java.math.BigDecimal unitPrice ) {
this.unitPrice = unitPrice;
}
private java.lang.String dataResourceId;
public java.lang.String getdataResourceId() {
return dataResourceId;
}
public void setdataResourceId( java.lang.String dataResourceId ) {
this.dataResourceId = dataResourceId;
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
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String dontCancelSetUserLogin;
public java.lang.String getdontCancelSetUserLogin() {
return dontCancelSetUserLogin;
}
public void setdontCancelSetUserLogin( java.lang.String dontCancelSetUserLogin ) {
this.dontCancelSetUserLogin = dontCancelSetUserLogin;
}
private java.lang.String privilegeEnumId;
public java.lang.String getprivilegeEnumId() {
return privilegeEnumId;
}
public void setprivilegeEnumId( java.lang.String privilegeEnumId ) {
this.privilegeEnumId = privilegeEnumId;
}
private java.lang.String templateDataResourceId;
public java.lang.String gettemplateDataResourceId() {
return templateDataResourceId;
}
public void settemplateDataResourceId( java.lang.String templateDataResourceId ) {
this.templateDataResourceId = templateDataResourceId;
}
private java.lang.String useCountLimit;
public java.lang.String getuseCountLimit() {
return useCountLimit;
}
public void setuseCountLimit( java.lang.String useCountLimit ) {
this.useCountLimit = useCountLimit;
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
private java.lang.String useTime;
public java.lang.String getuseTime() {
return useTime;
}
public void setuseTime( java.lang.String useTime ) {
this.useTime = useTime;
}
private java.lang.String shoppingListItemSeqId;
public java.lang.String getshoppingListItemSeqId() {
return shoppingListItemSeqId;
}
public void setshoppingListItemSeqId( java.lang.String shoppingListItemSeqId ) {
this.shoppingListItemSeqId = shoppingListItemSeqId;
}
private java.math.BigDecimal unitListPrice;
public java.math.BigDecimal getunitListPrice() {
return unitListPrice;
}
public void setunitListPrice( java.math.BigDecimal unitListPrice ) {
this.unitListPrice = unitListPrice;
}
private java.lang.String quoteId;
public java.lang.String getquoteId() {
return quoteId;
}
public void setquoteId( java.lang.String quoteId ) {
this.quoteId = quoteId;
}
private java.lang.String deploymentId;
public java.lang.String getdeploymentId() {
return deploymentId;
}
public void setdeploymentId( java.lang.String deploymentId ) {
this.deploymentId = deploymentId;
}
private java.lang.String recurringFreqUomId;
public java.lang.String getrecurringFreqUomId() {
return recurringFreqUomId;
}
public void setrecurringFreqUomId( java.lang.String recurringFreqUomId ) {
this.recurringFreqUomId = recurringFreqUomId;
}
private java.lang.String contentName;
public java.lang.String getcontentName() {
return contentName;
}
public void setcontentName( java.lang.String contentName ) {
this.contentName = contentName;
}
private java.lang.String useRoleTypeId;
public java.lang.String getuseRoleTypeId() {
return useRoleTypeId;
}
public void setuseRoleTypeId( java.lang.String useRoleTypeId ) {
this.useRoleTypeId = useRoleTypeId;
}
private java.sql.Timestamp shipBeforeDate;
public java.sql.Timestamp getshipBeforeDate() {
return shipBeforeDate;
}
public void setshipBeforeDate( java.sql.Timestamp shipBeforeDate ) {
this.shipBeforeDate = shipBeforeDate;
}
private java.lang.String characterSetId;
public java.lang.String getcharacterSetId() {
return characterSetId;
}
public void setcharacterSetId( java.lang.String characterSetId ) {
this.characterSetId = characterSetId;
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
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
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
private java.lang.String unitAverageCost;
public java.lang.String getunitAverageCost() {
return unitAverageCost;
}
public void setunitAverageCost( java.lang.String unitAverageCost ) {
this.unitAverageCost = unitAverageCost;
}
private java.lang.String budgetId;
public java.lang.String getbudgetId() {
return budgetId;
}
public void setbudgetId( java.lang.String budgetId ) {
this.budgetId = budgetId;
}
private java.sql.Timestamp purchaseThruDate;
public java.sql.Timestamp getpurchaseThruDate() {
return purchaseThruDate;
}
public void setpurchaseThruDate( java.sql.Timestamp purchaseThruDate ) {
this.purchaseThruDate = purchaseThruDate;
}
private java.lang.String useTimeUomId;
public java.lang.String getuseTimeUomId() {
return useTimeUomId;
}
public void setuseTimeUomId( java.lang.String useTimeUomId ) {
this.useTimeUomId = useTimeUomId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String instanceOfContentId;
public java.lang.String getinstanceOfContentId() {
return instanceOfContentId;
}
public void setinstanceOfContentId( java.lang.String instanceOfContentId ) {
this.instanceOfContentId = instanceOfContentId;
}
private java.lang.String correspondingPoId;
public java.lang.String getcorrespondingPoId() {
return correspondingPoId;
}
public void setcorrespondingPoId( java.lang.String correspondingPoId ) {
this.correspondingPoId = correspondingPoId;
}
private java.lang.String contentStatusId;
public java.lang.String getcontentStatusId() {
return contentStatusId;
}
public void setcontentStatusId( java.lang.String contentStatusId ) {
this.contentStatusId = contentStatusId;
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
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
}
private java.lang.String serviceName;
public java.lang.String getserviceName() {
return serviceName;
}
public void setserviceName( java.lang.String serviceName ) {
this.serviceName = serviceName;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String childBranchCount;
public java.lang.String getchildBranchCount() {
return childBranchCount;
}
public void setchildBranchCount( java.lang.String childBranchCount ) {
this.childBranchCount = childBranchCount;
}
private java.lang.String budgetItemSeqId;
public java.lang.String getbudgetItemSeqId() {
return budgetItemSeqId;
}
public void setbudgetItemSeqId( java.lang.String budgetItemSeqId ) {
this.budgetItemSeqId = budgetItemSeqId;
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
private java.lang.String decoratorContentId;
public java.lang.String getdecoratorContentId() {
return decoratorContentId;
}
public void setdecoratorContentId( java.lang.String decoratorContentId ) {
this.decoratorContentId = decoratorContentId;
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
private java.lang.String mimeTypeId;
public java.lang.String getmimeTypeId() {
return mimeTypeId;
}
public void setmimeTypeId( java.lang.String mimeTypeId ) {
this.mimeTypeId = mimeTypeId;
}
private java.sql.Timestamp dontCancelSetDate;
public java.sql.Timestamp getdontCancelSetDate() {
return dontCancelSetDate;
}
public void setdontCancelSetDate( java.sql.Timestamp dontCancelSetDate ) {
this.dontCancelSetDate = dontCancelSetDate;
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
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String syncStatusId;
public java.lang.String getsyncStatusId() {
return syncStatusId;
}
public void setsyncStatusId( java.lang.String syncStatusId ) {
this.syncStatusId = syncStatusId;
}
private java.lang.String itemDescription;
public java.lang.String getitemDescription() {
return itemDescription;
}
public void setitemDescription( java.lang.String itemDescription ) {
this.itemDescription = itemDescription;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String unitRecurringPrice;
public java.lang.String getunitRecurringPrice() {
return unitRecurringPrice;
}
public void setunitRecurringPrice( java.lang.String unitRecurringPrice ) {
this.unitRecurringPrice = unitRecurringPrice;
}
private java.lang.String localeString;
public java.lang.String getlocaleString() {
return localeString;
}
public void setlocaleString( java.lang.String localeString ) {
this.localeString = localeString;
}
private java.math.BigDecimal selectedAmount;
public java.math.BigDecimal getselectedAmount() {
return selectedAmount;
}
public void setselectedAmount( java.math.BigDecimal selectedAmount ) {
this.selectedAmount = selectedAmount;
}
private java.lang.String contentTypeId;
public java.lang.String getcontentTypeId() {
return contentTypeId;
}
public void setcontentTypeId( java.lang.String contentTypeId ) {
this.contentTypeId = contentTypeId;
}
private java.sql.Timestamp cancelBackOrderDate;
public java.sql.Timestamp getcancelBackOrderDate() {
return cancelBackOrderDate;
}
public void setcancelBackOrderDate( java.sql.Timestamp cancelBackOrderDate ) {
this.cancelBackOrderDate = cancelBackOrderDate;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String productContentTypeId;
public java.lang.String getproductContentTypeId() {
return productContentTypeId;
}
public void setproductContentTypeId( java.lang.String productContentTypeId ) {
this.productContentTypeId = productContentTypeId;
}
private java.sql.Timestamp autoCancelDate;
public java.sql.Timestamp getautoCancelDate() {
return autoCancelDate;
}
public void setautoCancelDate( java.sql.Timestamp autoCancelDate ) {
this.autoCancelDate = autoCancelDate;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
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
        Collection<OrderRoleAndProductContentInfo> objectList = new ArrayList<OrderRoleAndProductContentInfo>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderRoleAndProductContentInfo(genVal));
        }
        return objectList;
    }    
}
