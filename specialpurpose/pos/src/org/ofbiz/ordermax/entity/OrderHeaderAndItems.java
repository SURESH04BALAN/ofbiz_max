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
public class OrderHeaderAndItems implements GenericValueObjectInterface {
public static final String module =OrderHeaderAndItems.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderAndItems(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderAndItems () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productStoreId","Product Store Id"},
{"unitListPrice","Unit List Price"},
{"orderItemTypeId","Order Item Type Id"},
{"orderDate","Order Date"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"grandTotal","Grand Total"},
{"unitPrice","Unit Price"},
{"orderStatusId","Order Status Id"},
{"productId","Product Id"},
{"shipBeforeDate","Ship Before Date"},
{"orderItemSeqId","Order Item Seq Id"},
{"itemDescription","Item Description"},
{"orderTypeId","Order Type Id"},
{"itemStatusId","Item Status Id"},
{"shipAfterDate","Ship After Date"},
{"quantity","Quantity"},
{"estimatedShipDate","Estimated Ship Date"},
{"cancelQuantity","Cancel Quantity"},
{"orderId","Order Id"},
};
protected void initObject(){
this.productStoreId = "";
this.unitListPrice = java.math.BigDecimal.ZERO;
this.orderItemTypeId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.grandTotal = java.math.BigDecimal.ZERO;
this.unitPrice = java.math.BigDecimal.ZERO;
this.orderStatusId = "";
this.productId = "";
this.shipBeforeDate = UtilDateTime.nowTimestamp();
this.orderItemSeqId = "";
this.itemDescription = "";
this.orderTypeId = "";
this.itemStatusId = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.cancelQuantity = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.productId = (java.lang.String) genVal.get("productId");
this.shipBeforeDate = (java.sql.Timestamp) genVal.get("shipBeforeDate");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.itemStatusId = (java.lang.String) genVal.get("itemStatusId");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("unitListPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
val.set("orderItemTypeId",OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("grandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
val.set("unitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("shipBeforeDate",OrderMaxUtility.getValidEntityTimestamp(this.shipBeforeDate));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("itemStatusId",OrderMaxUtility.getValidEntityString(this.itemStatusId));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("unitListPrice",this.unitListPrice);
valueMap.put("orderItemTypeId",this.orderItemTypeId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("grandTotal",this.grandTotal);
valueMap.put("unitPrice",this.unitPrice);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("productId",this.productId);
valueMap.put("shipBeforeDate",this.shipBeforeDate);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("itemStatusId",this.itemStatusId);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("quantity",this.quantity);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("cancelQuantity",this.cancelQuantity);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderAndItems");
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
private java.math.BigDecimal unitListPrice;
public java.math.BigDecimal getunitListPrice() {
return unitListPrice;
}
public void setunitListPrice( java.math.BigDecimal unitListPrice ) {
this.unitListPrice = unitListPrice;
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
private java.sql.Timestamp estimatedDeliveryDate;
public java.sql.Timestamp getestimatedDeliveryDate() {
return estimatedDeliveryDate;
}
public void setestimatedDeliveryDate( java.sql.Timestamp estimatedDeliveryDate ) {
this.estimatedDeliveryDate = estimatedDeliveryDate;
}
private java.math.BigDecimal grandTotal;
public java.math.BigDecimal getgrandTotal() {
return grandTotal;
}
public void setgrandTotal( java.math.BigDecimal grandTotal ) {
this.grandTotal = grandTotal;
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
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String itemDescription;
public java.lang.String getitemDescription() {
return itemDescription;
}
public void setitemDescription( java.lang.String itemDescription ) {
this.itemDescription = itemDescription;
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
private java.sql.Timestamp shipAfterDate;
public java.sql.Timestamp getshipAfterDate() {
return shipAfterDate;
}
public void setshipAfterDate( java.sql.Timestamp shipAfterDate ) {
this.shipAfterDate = shipAfterDate;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
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
        Collection<OrderHeaderAndItems> objectList = new ArrayList<OrderHeaderAndItems>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderAndItems(genVal));
        }
        return objectList;
    }    
}
