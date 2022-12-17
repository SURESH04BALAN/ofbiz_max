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
public class OrderItemQuantityReportGroupByItem implements GenericValueObjectInterface {
public static final String module =OrderItemQuantityReportGroupByItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemQuantityReportGroupByItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemQuantityReportGroupByItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productStoreId","Product Store Id"},
{"quantityOpen","Quantity Open"},
{"orderDate","Order Date"},
{"orderStatusId","Order Status Id"},
{"quantityOrdered","Quantity Ordered"},
{"orderItemStatusId","Order Item Status Id"},
{"productId","Product Id"},
{"shipBeforeDate","Ship Before Date"},
{"itemDescription","Item Description"},
{"orderItemSeqId","Order Item Seq Id"},
{"orderTypeId","Order Type Id"},
{"quantityIssued","Quantity Issued"},
{"shipAfterDate","Ship After Date"},
{"orderId","Order Id"},
};
protected void initObject(){
this.productStoreId = "";
this.quantityOpen = java.math.BigDecimal.ZERO;
this.orderDate = UtilDateTime.nowTimestamp();
this.orderStatusId = "";
this.quantityOrdered = java.math.BigDecimal.ZERO;
this.orderItemStatusId = "";
this.productId = "";
this.shipBeforeDate = UtilDateTime.nowTimestamp();
this.itemDescription = "";
this.orderItemSeqId = "";
this.orderTypeId = "";
this.quantityIssued = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.quantityOpen = (java.math.BigDecimal) genVal.get("quantityOpen");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.quantityOrdered = (java.math.BigDecimal) genVal.get("quantityOrdered");
this.orderItemStatusId = (java.lang.String) genVal.get("orderItemStatusId");
this.productId = (java.lang.String) genVal.get("productId");
this.shipBeforeDate = (java.sql.Timestamp) genVal.get("shipBeforeDate");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.quantityIssued = (java.lang.String) genVal.get("quantityIssued");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("quantityOpen",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOpen));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("quantityOrdered",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOrdered));
val.set("orderItemStatusId",OrderMaxUtility.getValidEntityString(this.orderItemStatusId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("shipBeforeDate",OrderMaxUtility.getValidEntityTimestamp(this.shipBeforeDate));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("quantityIssued",OrderMaxUtility.getValidEntityString(this.quantityIssued));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("quantityOpen",this.quantityOpen);
valueMap.put("orderDate",this.orderDate);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("quantityOrdered",this.quantityOrdered);
valueMap.put("orderItemStatusId",this.orderItemStatusId);
valueMap.put("productId",this.productId);
valueMap.put("shipBeforeDate",this.shipBeforeDate);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("quantityIssued",this.quantityIssued);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemQuantityReportGroupByItem");
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
private java.lang.String orderStatusId;
public java.lang.String getorderStatusId() {
return orderStatusId;
}
public void setorderStatusId( java.lang.String orderStatusId ) {
this.orderStatusId = orderStatusId;
}
private java.math.BigDecimal quantityOrdered;
public java.math.BigDecimal getquantityOrdered() {
return quantityOrdered;
}
public void setquantityOrdered( java.math.BigDecimal quantityOrdered ) {
this.quantityOrdered = quantityOrdered;
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
private java.sql.Timestamp shipBeforeDate;
public java.sql.Timestamp getshipBeforeDate() {
return shipBeforeDate;
}
public void setshipBeforeDate( java.sql.Timestamp shipBeforeDate ) {
this.shipBeforeDate = shipBeforeDate;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItemQuantityReportGroupByItem> objectList = new ArrayList<OrderItemQuantityReportGroupByItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemQuantityReportGroupByItem(genVal));
        }
        return objectList;
    }    
}
