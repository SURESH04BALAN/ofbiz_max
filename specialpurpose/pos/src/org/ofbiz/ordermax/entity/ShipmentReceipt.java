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
public class ShipmentReceipt implements GenericValueObjectInterface {
public static final String module =ShipmentReceipt.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentReceipt(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentReceipt () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"rejectionId","Rejection Id"},
{"returnId","Return Id"},
{"productId","Product Id"},
{"quantityAccepted","Quantity Accepted"},
{"orderItemSeqId","Order Item Seq Id"},
{"itemDescription","Item Description"},
{"receivedByUserLoginId","Received By User Login Id"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"inventoryItemId","Inventory Item Id"},
{"datetimeReceived","Datetime Received"},
{"quantityRejected","Quantity Rejected"},
{"returnItemSeqId","Return Item Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"orderId","Order Id"},
{"receiptId","Receipt Id"},
};
protected void initObject(){
this.shipmentPackageSeqId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.rejectionId = "";
this.returnId = "";
this.productId = "";
this.quantityAccepted = java.math.BigDecimal.ZERO;
this.orderItemSeqId = "";
this.itemDescription = "";
this.receivedByUserLoginId = "";
this.shipmentItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.inventoryItemId = "";
this.datetimeReceived = UtilDateTime.nowTimestamp();
this.quantityRejected = java.math.BigDecimal.ZERO;
this.returnItemSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.orderId = "";
this.receiptId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.rejectionId = (java.lang.String) genVal.get("rejectionId");
this.returnId = (java.lang.String) genVal.get("returnId");
this.productId = (java.lang.String) genVal.get("productId");
this.quantityAccepted = (java.math.BigDecimal) genVal.get("quantityAccepted");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.receivedByUserLoginId = (java.lang.String) genVal.get("receivedByUserLoginId");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
this.quantityRejected = (java.math.BigDecimal) genVal.get("quantityRejected");
this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.orderId = (java.lang.String) genVal.get("orderId");
this.receiptId = (java.lang.String) genVal.get("receiptId");
}
protected void getGenericValue(GenericValue val) {
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("rejectionId",OrderMaxUtility.getValidEntityString(this.rejectionId));
val.set("returnId",OrderMaxUtility.getValidEntityString(this.returnId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("quantityAccepted",OrderMaxUtility.getValidEntityBigDecimal(this.quantityAccepted));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("receivedByUserLoginId",OrderMaxUtility.getValidEntityString(this.receivedByUserLoginId));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("datetimeReceived",OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
val.set("quantityRejected",OrderMaxUtility.getValidEntityBigDecimal(this.quantityRejected));
val.set("returnItemSeqId",OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("rejectionId",this.rejectionId);
valueMap.put("returnId",this.returnId);
valueMap.put("productId",this.productId);
valueMap.put("quantityAccepted",this.quantityAccepted);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("receivedByUserLoginId",this.receivedByUserLoginId);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("datetimeReceived",this.datetimeReceived);
valueMap.put("quantityRejected",this.quantityRejected);
valueMap.put("returnItemSeqId",this.returnItemSeqId);
valueMap.put("orderId",this.orderId);
valueMap.put("receiptId",this.receiptId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentReceipt");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String rejectionId;
public java.lang.String getrejectionId() {
return rejectionId;
}
public void setrejectionId( java.lang.String rejectionId ) {
this.rejectionId = rejectionId;
}
private java.lang.String returnId;
public java.lang.String getreturnId() {
return returnId;
}
public void setreturnId( java.lang.String returnId ) {
this.returnId = returnId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.math.BigDecimal quantityAccepted;
public java.math.BigDecimal getquantityAccepted() {
return quantityAccepted;
}
public void setquantityAccepted( java.math.BigDecimal quantityAccepted ) {
this.quantityAccepted = quantityAccepted;
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
private java.lang.String receivedByUserLoginId;
public java.lang.String getreceivedByUserLoginId() {
return receivedByUserLoginId;
}
public void setreceivedByUserLoginId( java.lang.String receivedByUserLoginId ) {
this.receivedByUserLoginId = receivedByUserLoginId;
}
private java.lang.String shipmentItemSeqId;
public java.lang.String getshipmentItemSeqId() {
return shipmentItemSeqId;
}
public void setshipmentItemSeqId( java.lang.String shipmentItemSeqId ) {
this.shipmentItemSeqId = shipmentItemSeqId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.sql.Timestamp datetimeReceived;
public java.sql.Timestamp getdatetimeReceived() {
return datetimeReceived;
}
public void setdatetimeReceived( java.sql.Timestamp datetimeReceived ) {
this.datetimeReceived = datetimeReceived;
}
private java.math.BigDecimal quantityRejected;
public java.math.BigDecimal getquantityRejected() {
return quantityRejected;
}
public void setquantityRejected( java.math.BigDecimal quantityRejected ) {
this.quantityRejected = quantityRejected;
}
private java.lang.String returnItemSeqId;
public java.lang.String getreturnItemSeqId() {
return returnItemSeqId;
}
public void setreturnItemSeqId( java.lang.String returnItemSeqId ) {
this.returnItemSeqId = returnItemSeqId;
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
private java.lang.String receiptId;
public java.lang.String getreceiptId() {
return receiptId;
}
public void setreceiptId( java.lang.String receiptId ) {
this.receiptId = receiptId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentReceipt> objectList = new ArrayList<ShipmentReceipt>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentReceipt(genVal));
        }
        return objectList;
    }    
}
