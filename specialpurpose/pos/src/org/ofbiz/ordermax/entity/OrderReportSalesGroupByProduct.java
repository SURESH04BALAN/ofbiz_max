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
public class OrderReportSalesGroupByProduct implements GenericValueObjectInterface {
public static final String module =OrderReportSalesGroupByProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderReportSalesGroupByProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderReportSalesGroupByProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"productStoreId","Product Store Id"},
{"storeName","Store Name"},
{"orderDate","Order Date"},
{"unitPrice","Unit Price"},
{"orderStatusId","Order Status Id"},
{"orderItemStatusId","Order Item Status Id"},
{"quantityOrdered","Quantity Ordered"},
{"productId","Product Id"},
{"roleTypeId","Role Type Id"},
{"orderTypeId","Order Type Id"},
{"partyId","Party Id"},
};
protected void initObject(){
this.internalName = "";
this.productStoreId = "";
this.storeName = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.unitPrice = java.math.BigDecimal.ZERO;
this.orderStatusId = "";
this.orderItemStatusId = "";
this.quantityOrdered = java.math.BigDecimal.ZERO;
this.productId = "";
this.roleTypeId = "";
this.orderTypeId = "";
this.partyId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.storeName = (java.lang.String) genVal.get("storeName");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.orderItemStatusId = (java.lang.String) genVal.get("orderItemStatusId");
this.quantityOrdered = (java.math.BigDecimal) genVal.get("quantityOrdered");
this.productId = (java.lang.String) genVal.get("productId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.partyId = (java.lang.String) genVal.get("partyId");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("storeName",OrderMaxUtility.getValidEntityString(this.storeName));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("orderItemStatusId",OrderMaxUtility.getValidEntityString(this.orderItemStatusId));
val.set("quantityOrdered",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOrdered));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("storeName",this.storeName);
valueMap.put("orderDate",this.orderDate);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("orderItemStatusId",this.orderItemStatusId);
valueMap.put("quantityOrdered",this.quantityOrdered);
valueMap.put("productId",this.productId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("partyId",this.partyId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderReportSalesGroupByProduct");
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
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String storeName;
public java.lang.String getstoreName() {
return storeName;
}
public void setstoreName( java.lang.String storeName ) {
this.storeName = storeName;
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
private java.math.BigDecimal quantityOrdered;
public java.math.BigDecimal getquantityOrdered() {
return quantityOrdered;
}
public void setquantityOrdered( java.math.BigDecimal quantityOrdered ) {
this.quantityOrdered = quantityOrdered;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String orderTypeId;
public java.lang.String getorderTypeId() {
return orderTypeId;
}
public void setorderTypeId( java.lang.String orderTypeId ) {
this.orderTypeId = orderTypeId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderReportSalesGroupByProduct> objectList = new ArrayList<OrderReportSalesGroupByProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderReportSalesGroupByProduct(genVal));
        }
        return objectList;
    }    
}
