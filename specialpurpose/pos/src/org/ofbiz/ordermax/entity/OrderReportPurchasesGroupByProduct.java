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
public class OrderReportPurchasesGroupByProduct implements GenericValueObjectInterface {
public static final String module =OrderReportPurchasesGroupByProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderReportPurchasesGroupByProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderReportPurchasesGroupByProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"fromRoleTypeId","From Role Type Id"},
{"internalName","Internal Name"},
{"orderDate","Order Date"},
{"unitPrice","Unit Price"},
{"orderStatusId","Order Status Id"},
{"orderItemStatusId","Order Item Status Id"},
{"productId","Product Id"},
{"toRoleTypeId","To Role Type Id"},
{"fromPartyId","From Party Id"},
{"orderTypeId","Order Type Id"},
{"quantity","Quantity"},
{"toPartyId","To Party Id"},
};
protected void initObject(){
this.fromRoleTypeId = "";
this.internalName = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.unitPrice = java.math.BigDecimal.ZERO;
this.orderStatusId = "";
this.orderItemStatusId = "";
this.productId = "";
this.toRoleTypeId = "";
this.fromPartyId = "";
this.orderTypeId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.toPartyId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.fromRoleTypeId = (java.lang.String) genVal.get("fromRoleTypeId");
this.internalName = (java.lang.String) genVal.get("internalName");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.orderItemStatusId = (java.lang.String) genVal.get("orderItemStatusId");
this.productId = (java.lang.String) genVal.get("productId");
this.toRoleTypeId = (java.lang.String) genVal.get("toRoleTypeId");
this.fromPartyId = (java.lang.String) genVal.get("fromPartyId");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.toPartyId = (java.lang.String) genVal.get("toPartyId");
}
protected void getGenericValue(GenericValue val) {
val.set("fromRoleTypeId",OrderMaxUtility.getValidEntityString(this.fromRoleTypeId));
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("orderItemStatusId",OrderMaxUtility.getValidEntityString(this.orderItemStatusId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("toRoleTypeId",OrderMaxUtility.getValidEntityString(this.toRoleTypeId));
val.set("fromPartyId",OrderMaxUtility.getValidEntityString(this.fromPartyId));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("toPartyId",OrderMaxUtility.getValidEntityString(this.toPartyId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("fromRoleTypeId",this.fromRoleTypeId);
valueMap.put("internalName",this.internalName);
valueMap.put("orderDate",this.orderDate);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("orderItemStatusId",this.orderItemStatusId);
valueMap.put("productId",this.productId);
valueMap.put("toRoleTypeId",this.toRoleTypeId);
valueMap.put("fromPartyId",this.fromPartyId);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("quantity",this.quantity);
valueMap.put("toPartyId",this.toPartyId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderReportPurchasesGroupByProduct");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String fromRoleTypeId;
public java.lang.String getfromRoleTypeId() {
return fromRoleTypeId;
}
public void setfromRoleTypeId( java.lang.String fromRoleTypeId ) {
this.fromRoleTypeId = fromRoleTypeId;
}
private java.lang.String internalName;
public java.lang.String getinternalName() {
return internalName;
}
public void setinternalName( java.lang.String internalName ) {
this.internalName = internalName;
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
private java.lang.String orderStatusId;
public java.lang.String getorderStatusId() {
return orderStatusId;
}
public void setorderStatusId( java.lang.String orderStatusId ) {
this.orderStatusId = orderStatusId;
}
private java.lang.String orderItemStatusId;
public java.lang.String getorderItemStatusId() {
return orderItemStatusId;
}
public void setorderItemStatusId( java.lang.String orderItemStatusId ) {
this.orderItemStatusId = orderItemStatusId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String toRoleTypeId;
public java.lang.String gettoRoleTypeId() {
return toRoleTypeId;
}
public void settoRoleTypeId( java.lang.String toRoleTypeId ) {
this.toRoleTypeId = toRoleTypeId;
}
private java.lang.String fromPartyId;
public java.lang.String getfromPartyId() {
return fromPartyId;
}
public void setfromPartyId( java.lang.String fromPartyId ) {
this.fromPartyId = fromPartyId;
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
private java.lang.String toPartyId;
public java.lang.String gettoPartyId() {
return toPartyId;
}
public void settoPartyId( java.lang.String toPartyId ) {
this.toPartyId = toPartyId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderReportPurchasesGroupByProduct> objectList = new ArrayList<OrderReportPurchasesGroupByProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderReportPurchasesGroupByProduct(genVal));
        }
        return objectList;
    }    
}
