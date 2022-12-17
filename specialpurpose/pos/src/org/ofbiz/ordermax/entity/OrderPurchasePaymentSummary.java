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
public class OrderPurchasePaymentSummary implements GenericValueObjectInterface {
public static final String module =OrderPurchasePaymentSummary.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderPurchasePaymentSummary(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderPurchasePaymentSummary () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"originFacilityId","Origin Facility Id"},
{"productStoreId","Product Store Id"},
{"billingAccountId","Billing Account Id"},
{"orderDate","Order Date"},
{"paymentMethodTypeId","Payment Method Type Id"},
{"maxAmount","Max Amount"},
{"statusId","Status Id"},
{"terminalId","Terminal Id"},
{"orderTypeId","Order Type Id"},
{"description","Description"},
{"preferenceStatusId","Preference Status Id"},
{"webSiteId","Web Site Id"},
{"orderId","Order Id"},
};
protected void initObject(){
this.originFacilityId = "";
this.productStoreId = "";
this.billingAccountId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.paymentMethodTypeId = "";
this.maxAmount = java.math.BigDecimal.ZERO;
this.statusId = "";
this.terminalId = "";
this.orderTypeId = "";
this.description = "";
this.preferenceStatusId = "";
this.webSiteId = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
this.maxAmount = (java.math.BigDecimal) genVal.get("maxAmount");
this.statusId = (java.lang.String) genVal.get("statusId");
this.terminalId = (java.lang.String) genVal.get("terminalId");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.description = (java.lang.String) genVal.get("description");
this.preferenceStatusId = (java.lang.String) genVal.get("preferenceStatusId");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("paymentMethodTypeId",OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
val.set("maxAmount",OrderMaxUtility.getValidEntityBigDecimal(this.maxAmount));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("terminalId",OrderMaxUtility.getValidEntityString(this.terminalId));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("preferenceStatusId",OrderMaxUtility.getValidEntityString(this.preferenceStatusId));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("billingAccountId",this.billingAccountId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("paymentMethodTypeId",this.paymentMethodTypeId);
valueMap.put("maxAmount",this.maxAmount);
valueMap.put("statusId",this.statusId);
valueMap.put("terminalId",this.terminalId);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("description",this.description);
valueMap.put("preferenceStatusId",this.preferenceStatusId);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderPurchasePaymentSummary");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
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
private java.lang.String paymentMethodTypeId;
public java.lang.String getpaymentMethodTypeId() {
return paymentMethodTypeId;
}
public void setpaymentMethodTypeId( java.lang.String paymentMethodTypeId ) {
this.paymentMethodTypeId = paymentMethodTypeId;
}
private java.math.BigDecimal maxAmount;
public java.math.BigDecimal getmaxAmount() {
return maxAmount;
}
public void setmaxAmount( java.math.BigDecimal maxAmount ) {
this.maxAmount = maxAmount;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String terminalId;
public java.lang.String getterminalId() {
return terminalId;
}
public void setterminalId( java.lang.String terminalId ) {
this.terminalId = terminalId;
}
private java.lang.String orderTypeId;
public java.lang.String getorderTypeId() {
return orderTypeId;
}
public void setorderTypeId( java.lang.String orderTypeId ) {
this.orderTypeId = orderTypeId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String preferenceStatusId;
public java.lang.String getpreferenceStatusId() {
return preferenceStatusId;
}
public void setpreferenceStatusId( java.lang.String preferenceStatusId ) {
this.preferenceStatusId = preferenceStatusId;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderPurchasePaymentSummary> objectList = new ArrayList<OrderPurchasePaymentSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderPurchasePaymentSummary(genVal));
        }
        return objectList;
    }    
}
