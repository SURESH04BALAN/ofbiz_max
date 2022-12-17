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
public class OrderReportGroupByProduct implements GenericValueObjectInterface {
public static final String module =OrderReportGroupByProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderReportGroupByProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderReportGroupByProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"orderTypeId","Order Type Id"},
{"orderDate","Order Date"},
{"quantity","Quantity"},
{"unitPrice","Unit Price"},
{"orderStatusId","Order Status Id"},
{"orderItemStatusId","Order Item Status Id"},
{"productId","Product Id"},
};
protected void initObject(){
this.orderTypeId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.unitPrice = java.math.BigDecimal.ZERO;
this.orderStatusId = "";
this.orderItemStatusId = "";
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.orderItemStatusId = (java.lang.String) genVal.get("orderItemStatusId");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("orderItemStatusId",OrderMaxUtility.getValidEntityString(this.orderItemStatusId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("quantity",this.quantity);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("orderItemStatusId",this.orderItemStatusId);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderReportGroupByProduct");
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
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderReportGroupByProduct> objectList = new ArrayList<OrderReportGroupByProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderReportGroupByProduct(genVal));
        }
        return objectList;
    }    
}
