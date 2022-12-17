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
public class OrderItemBilling implements GenericValueObjectInterface {
public static final String module =OrderItemBilling.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemBilling(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemBilling () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"invoiceId","Invoice Id"},
{"invoiceItemSeqId","Invoice Item Seq Id"},
{"amount","Amount"},
{"orderItemSeqId","Order Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"itemIssuanceId","Item Issuance Id"},
{"createdStamp","Created Stamp"},
{"quantity","Quantity"},
{"shipmentReceiptId","Shipment Receipt Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"orderId","Order Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.invoiceId = "";
this.invoiceItemSeqId = "";
this.amount = java.math.BigDecimal.ZERO;
this.orderItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.itemIssuanceId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.shipmentReceiptId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.shipmentReceiptId = (java.lang.String) genVal.get("shipmentReceiptId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("invoiceItemSeqId",OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("shipmentReceiptId",OrderMaxUtility.getValidEntityString(this.shipmentReceiptId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("invoiceItemSeqId",this.invoiceItemSeqId);
valueMap.put("amount",this.amount);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("quantity",this.quantity);
valueMap.put("shipmentReceiptId",this.shipmentReceiptId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemBilling");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.lang.String invoiceItemSeqId;
public java.lang.String getinvoiceItemSeqId() {
return invoiceItemSeqId;
}
public void setinvoiceItemSeqId( java.lang.String invoiceItemSeqId ) {
this.invoiceItemSeqId = invoiceItemSeqId;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String itemIssuanceId;
public java.lang.String getitemIssuanceId() {
return itemIssuanceId;
}
public void setitemIssuanceId( java.lang.String itemIssuanceId ) {
this.itemIssuanceId = itemIssuanceId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String shipmentReceiptId;
public java.lang.String getshipmentReceiptId() {
return shipmentReceiptId;
}
public void setshipmentReceiptId( java.lang.String shipmentReceiptId ) {
this.shipmentReceiptId = shipmentReceiptId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<OrderItemBilling> objectList = new ArrayList<OrderItemBilling>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemBilling(genVal));
        }
        return objectList;
    }    
}
