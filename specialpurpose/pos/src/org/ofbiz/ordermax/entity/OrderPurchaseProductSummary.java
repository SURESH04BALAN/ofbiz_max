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
public class OrderPurchaseProductSummary implements GenericValueObjectInterface {
public static final String module =OrderPurchaseProductSummary.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderPurchaseProductSummary(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderPurchaseProductSummary () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"originFacilityId","Origin Facility Id"},
{"productStoreId","Product Store Id"},
{"unitListPrice","Unit List Price"},
{"orderDate","Order Date"},
{"unitPrice","Unit Price"},
{"productId","Product Id"},
{"statusId","Status Id"},
{"terminalId","Terminal Id"},
{"orderTypeId","Order Type Id"},
{"itemStatusId","Item Status Id"},
{"quantity","Quantity"},
{"webSiteId","Web Site Id"},
{"orderId","Order Id"},
{"cancelQuantity","Cancel Quantity"},
};
protected void initObject(){
this.internalName = "";
this.originFacilityId = "";
this.productStoreId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.orderDate = UtilDateTime.nowTimestamp();
this.unitPrice = java.math.BigDecimal.ZERO;
this.productId = "";
this.statusId = "";
this.terminalId = "";
this.orderTypeId = "";
this.itemStatusId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.webSiteId = "";
this.orderId = "";
this.cancelQuantity = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.productId = (java.lang.String) genVal.get("productId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.terminalId = (java.lang.String) genVal.get("terminalId");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.itemStatusId = (java.lang.String) genVal.get("itemStatusId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("terminalId",OrderMaxUtility.getValidEntityString(this.terminalId));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("itemStatusId",OrderMaxUtility.getValidEntityString(this.itemStatusId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("orderDate",this.orderDate);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("productId",this.productId);
valueMap.put("statusId",this.statusId);
valueMap.put("terminalId",this.terminalId);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("itemStatusId",this.itemStatusId);
valueMap.put("quantity",this.quantity);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("orderId",this.orderId);
valueMap.put("cancelQuantity",this.cancelQuantity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderPurchaseProductSummary");
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
private java.math.BigDecimal unitListPrice;
public java.math.BigDecimal getunitListPrice() {
return unitListPrice;
}
public void setunitListPrice( java.math.BigDecimal unitListPrice ) {
this.unitListPrice = unitListPrice;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.math.BigDecimal unitPrice;
public java.math.BigDecimal getunitPrice() {
return unitPrice;
}
public void setunitPrice( java.math.BigDecimal unitPrice ) {
this.unitPrice = unitPrice;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
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
private java.lang.String itemStatusId;
public java.lang.String getitemStatusId() {
return itemStatusId;
}
public void setitemStatusId( java.lang.String itemStatusId ) {
this.itemStatusId = itemStatusId;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
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
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderPurchaseProductSummary> objectList = new ArrayList<OrderPurchaseProductSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderPurchaseProductSummary(genVal));
        }
        return objectList;
    }    
}
