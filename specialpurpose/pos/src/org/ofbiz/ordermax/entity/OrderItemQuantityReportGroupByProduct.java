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
public class OrderItemQuantityReportGroupByProduct implements GenericValueObjectInterface {
public static final String module =OrderItemQuantityReportGroupByProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemQuantityReportGroupByProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemQuantityReportGroupByProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"orderTypeId","Order Type Id"},
{"quantityIssued","Quantity Issued"},
{"quantityOpen","Quantity Open"},
{"orderDate","Order Date"},
{"issuedDateTime","Issued Date Time"},
{"orderStatusId","Order Status Id"},
{"orderItemStatusId","Order Item Status Id"},
{"quantityOrdered","Quantity Ordered"},
{"productId","Product Id"},
};
protected void initObject(){
this.orderTypeId = "";
this.quantityIssued = "";
this.quantityOpen = java.math.BigDecimal.ZERO;
this.orderDate = UtilDateTime.nowTimestamp();
this.issuedDateTime = UtilDateTime.nowTimestamp();
this.orderStatusId = "";
this.orderItemStatusId = "";
this.quantityOrdered = java.math.BigDecimal.ZERO;
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.quantityIssued = (java.lang.String) genVal.get("quantityIssued");
this.quantityOpen = (java.math.BigDecimal) genVal.get("quantityOpen");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.issuedDateTime = (java.sql.Timestamp) genVal.get("issuedDateTime");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.orderItemStatusId = (java.lang.String) genVal.get("orderItemStatusId");
this.quantityOrdered = (java.math.BigDecimal) genVal.get("quantityOrdered");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("quantityIssued",OrderMaxUtility.getValidEntityString(this.quantityIssued));
val.set("quantityOpen",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOpen));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("issuedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.issuedDateTime));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("orderItemStatusId",OrderMaxUtility.getValidEntityString(this.orderItemStatusId));
val.set("quantityOrdered",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOrdered));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("quantityIssued",this.quantityIssued);
valueMap.put("quantityOpen",this.quantityOpen);
valueMap.put("orderDate",this.orderDate);
valueMap.put("issuedDateTime",this.issuedDateTime);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("orderItemStatusId",this.orderItemStatusId);
valueMap.put("quantityOrdered",this.quantityOrdered);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemQuantityReportGroupByProduct");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String orderTypeId;
public java.lang.String getorderTypeId() {
return orderTypeId;
}
public void setorderTypeId( java.lang.String orderTypeId ) {
this.orderTypeId = orderTypeId;
}
private java.lang.String quantityIssued;
public java.lang.String getquantityIssued() {
return quantityIssued;
}
public void setquantityIssued( java.lang.String quantityIssued ) {
this.quantityIssued = quantityIssued;
}
private java.math.BigDecimal quantityOpen;
public java.math.BigDecimal getquantityOpen() {
return quantityOpen;
}
public void setquantityOpen( java.math.BigDecimal quantityOpen ) {
this.quantityOpen = quantityOpen;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.sql.Timestamp issuedDateTime;
public java.sql.Timestamp getissuedDateTime() {
return issuedDateTime;
}
public void setissuedDateTime( java.sql.Timestamp issuedDateTime ) {
this.issuedDateTime = issuedDateTime;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItemQuantityReportGroupByProduct> objectList = new ArrayList<OrderItemQuantityReportGroupByProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemQuantityReportGroupByProduct(genVal));
        }
        return objectList;
    }    
}
