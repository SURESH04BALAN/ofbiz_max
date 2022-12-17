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
public class OrderHeaderItemAndRoles implements GenericValueObjectInterface {
public static final String module =OrderHeaderItemAndRoles.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderItemAndRoles(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderItemAndRoles () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productStoreId","Product Store Id"},
{"originFacilityId","Origin Facility Id"},
{"correspondingPoId","Corresponding Po Id"},
{"orderName","Order Name"},
{"currencyUom","Currency Uom"},
{"unitPrice","Unit Price"},
{"visitId","Visit Id"},
{"statusId","Status Id"},
{"remainingSubTotal","Remaining Sub Total"},
{"orderTypeId","Order Type Id"},
{"quantity","Quantity"},
{"orderId","Order Id"},
{"unitListPrice","Unit List Price"},
{"billingAccountId","Billing Account Id"},
{"orderItemTypeId","Order Item Type Id"},
{"orderDate","Order Date"},
{"grandTotal","Grand Total"},
{"entryDate","Entry Date"},
{"productId","Product Id"},
{"syncStatusId","Sync Status Id"},
{"roleTypeId","Role Type Id"},
{"itemDescription","Item Description"},
{"orderItemSeqId","Order Item Seq Id"},
{"createdBy","Created By"},
{"partyId","Party Id"},
{"firstAttemptOrderId","First Attempt Order Id"},
{"estimatedShipDate","Estimated Ship Date"},
{"webSiteId","Web Site Id"},
{"autoCancelDate","Auto Cancel Date"},
};
protected void initObject(){
this.productStoreId = "";
this.originFacilityId = "";
this.correspondingPoId = "";
this.orderName = "";
this.currencyUom = "";
this.unitPrice = java.math.BigDecimal.ZERO;
this.visitId = "";
this.statusId = "";
this.remainingSubTotal = java.math.BigDecimal.ZERO;
this.orderTypeId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.orderId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.billingAccountId = "";
this.orderItemTypeId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.grandTotal = java.math.BigDecimal.ZERO;
this.entryDate = UtilDateTime.nowTimestamp();
this.productId = "";
this.syncStatusId = "";
this.roleTypeId = "";
this.itemDescription = "";
this.orderItemSeqId = "";
this.createdBy = "";
this.partyId = "";
this.firstAttemptOrderId = "";
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.webSiteId = "";
this.autoCancelDate = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.correspondingPoId = (java.lang.String) genVal.get("correspondingPoId");
this.orderName = (java.lang.String) genVal.get("orderName");
this.currencyUom = (java.lang.String) genVal.get("currencyUom");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.visitId = (java.lang.String) genVal.get("visitId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.remainingSubTotal = (java.math.BigDecimal) genVal.get("remainingSubTotal");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.orderId = (java.lang.String) genVal.get("orderId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
this.entryDate = (java.sql.Timestamp) genVal.get("entryDate");
this.productId = (java.lang.String) genVal.get("productId");
this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.createdBy = (java.lang.String) genVal.get("createdBy");
this.partyId = (java.lang.String) genVal.get("partyId");
this.firstAttemptOrderId = (java.lang.String) genVal.get("firstAttemptOrderId");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.autoCancelDate = (java.sql.Timestamp) genVal.get("autoCancelDate");
}
protected void getGenericValue(GenericValue val) {
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("correspondingPoId",OrderMaxUtility.getValidEntityString(this.correspondingPoId));
val.set("orderName",OrderMaxUtility.getValidEntityString(this.orderName));
val.set("currencyUom",OrderMaxUtility.getValidEntityString(this.currencyUom));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("remainingSubTotal",OrderMaxUtility.getValidEntityBigDecimal(this.remainingSubTotal));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("orderItemTypeId",OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("grandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
val.set("entryDate",OrderMaxUtility.getValidEntityTimestamp(this.entryDate));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("syncStatusId",OrderMaxUtility.getValidEntityString(this.syncStatusId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("createdBy",OrderMaxUtility.getValidEntityString(this.createdBy));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("firstAttemptOrderId",OrderMaxUtility.getValidEntityString(this.firstAttemptOrderId));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("autoCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.autoCancelDate));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("correspondingPoId",this.correspondingPoId);
valueMap.put("orderName",this.orderName);
valueMap.put("currencyUom",this.currencyUom);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("visitId",this.visitId);
valueMap.put("statusId",this.statusId);
valueMap.put("remainingSubTotal",this.remainingSubTotal);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("quantity",this.quantity);
valueMap.put("orderId",this.orderId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("billingAccountId",this.billingAccountId);
valueMap.put("orderItemTypeId",this.orderItemTypeId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("grandTotal",this.grandTotal);
valueMap.put("entryDate",this.entryDate);
valueMap.put("productId",this.productId);
valueMap.put("syncStatusId",this.syncStatusId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("createdBy",this.createdBy);
valueMap.put("partyId",this.partyId);
valueMap.put("firstAttemptOrderId",this.firstAttemptOrderId);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("autoCancelDate",this.autoCancelDate);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderItemAndRoles");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String correspondingPoId;
public java.lang.String getcorrespondingPoId() {
return correspondingPoId;
}
public void setcorrespondingPoId( java.lang.String correspondingPoId ) {
this.correspondingPoId = correspondingPoId;
}
private java.lang.String orderName;
public java.lang.String getorderName() {
return orderName;
}
public void setorderName( java.lang.String orderName ) {
this.orderName = orderName;
}
private java.lang.String currencyUom;
public java.lang.String getcurrencyUom() {
return currencyUom;
}
public void setcurrencyUom( java.lang.String currencyUom ) {
this.currencyUom = currencyUom;
}
private java.math.BigDecimal unitPrice;
public java.math.BigDecimal getunitPrice() {
return unitPrice;
}
public void setunitPrice( java.math.BigDecimal unitPrice ) {
this.unitPrice = unitPrice;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
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
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
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
private java.lang.String orderItemTypeId;
public java.lang.String getorderItemTypeId() {
return orderItemTypeId;
}
public void setorderItemTypeId( java.lang.String orderItemTypeId ) {
this.orderItemTypeId = orderItemTypeId;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.math.BigDecimal grandTotal;
public java.math.BigDecimal getgrandTotal() {
return grandTotal;
}
public void setgrandTotal( java.math.BigDecimal grandTotal ) {
this.grandTotal = grandTotal;
}
private java.sql.Timestamp entryDate;
public java.sql.Timestamp getentryDate() {
return entryDate;
}
public void setentryDate( java.sql.Timestamp entryDate ) {
this.entryDate = entryDate;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String syncStatusId;
public java.lang.String getsyncStatusId() {
return syncStatusId;
}
public void setsyncStatusId( java.lang.String syncStatusId ) {
this.syncStatusId = syncStatusId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String itemDescription;
public java.lang.String getitemDescription() {
return itemDescription;
}
public void setitemDescription( java.lang.String itemDescription ) {
this.itemDescription = itemDescription;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String createdBy;
public java.lang.String getcreatedBy() {
return createdBy;
}
public void setcreatedBy( java.lang.String createdBy ) {
this.createdBy = createdBy;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String firstAttemptOrderId;
public java.lang.String getfirstAttemptOrderId() {
return firstAttemptOrderId;
}
public void setfirstAttemptOrderId( java.lang.String firstAttemptOrderId ) {
this.firstAttemptOrderId = firstAttemptOrderId;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
}
private java.sql.Timestamp autoCancelDate;
public java.sql.Timestamp getautoCancelDate() {
return autoCancelDate;
}
public void setautoCancelDate( java.sql.Timestamp autoCancelDate ) {
this.autoCancelDate = autoCancelDate;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderItemAndRoles> objectList = new ArrayList<OrderHeaderItemAndRoles>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderItemAndRoles(genVal));
        }
        return objectList;
    }    
}
