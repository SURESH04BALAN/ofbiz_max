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
public class ShipmentReceiptAndItem implements GenericValueObjectInterface {
public static final String module =ShipmentReceiptAndItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentReceiptAndItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentReceiptAndItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"rejectionId","Rejection Id"},
{"quantityOnHandTotal","Quantity On Hand Total"},
{"availableToPromiseTotal","Available To Promise Total"},
{"locationSeqId","Location Seq Id"},
{"returnId","Return Id"},
{"facilityId","Facility Id"},
{"productId","Product Id"},
{"quantityAccepted","Quantity Accepted"},
{"orderItemSeqId","Order Item Seq Id"},
{"itemDescription","Item Description"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"unitCost","Unit Cost"},
{"receivedByUserLoginId","Received By User Login Id"},
{"shipmentId","Shipment Id"},
{"lotId","Lot Id"},
{"inventoryItemId","Inventory Item Id"},
{"datetimeReceived","Datetime Received"},
{"quantityRejected","Quantity Rejected"},
{"returnItemSeqId","Return Item Seq Id"},
{"orderId","Order Id"},
{"receiptId","Receipt Id"},
};
protected void initObject(){
this.shipmentPackageSeqId = "";
this.rejectionId = "";
this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
this.locationSeqId = "";
this.returnId = "";
this.facilityId = "";
this.productId = "";
this.quantityAccepted = java.math.BigDecimal.ZERO;
this.orderItemSeqId = "";
this.itemDescription = "";
this.shipmentItemSeqId = "";
this.unitCost = java.math.BigDecimal.ZERO;
this.receivedByUserLoginId = "";
this.shipmentId = "";
this.lotId = "";
this.inventoryItemId = "";
this.datetimeReceived = UtilDateTime.nowTimestamp();
this.quantityRejected = java.math.BigDecimal.ZERO;
this.returnItemSeqId = "";
this.orderId = "";
this.receiptId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.rejectionId = (java.lang.String) genVal.get("rejectionId");
this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
this.returnId = (java.lang.String) genVal.get("returnId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.productId = (java.lang.String) genVal.get("productId");
this.quantityAccepted = (java.math.BigDecimal) genVal.get("quantityAccepted");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.itemDescription = (java.lang.String) genVal.get("itemDescription");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.receivedByUserLoginId = (java.lang.String) genVal.get("receivedByUserLoginId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.lotId = (java.lang.String) genVal.get("lotId");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
this.quantityRejected = (java.math.BigDecimal) genVal.get("quantityRejected");
this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.receiptId = (java.lang.String) genVal.get("receiptId");
}
protected void getGenericValue(GenericValue val) {
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("rejectionId",OrderMaxUtility.getValidEntityString(this.rejectionId));
val.set("quantityOnHandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
val.set("availableToPromiseTotal",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
val.set("locationSeqId",OrderMaxUtility.getValidEntityString(this.locationSeqId));
val.set("returnId",OrderMaxUtility.getValidEntityString(this.returnId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("quantityAccepted",OrderMaxUtility.getValidEntityBigDecimal(this.quantityAccepted));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("itemDescription",OrderMaxUtility.getValidEntityString(this.itemDescription));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("receivedByUserLoginId",OrderMaxUtility.getValidEntityString(this.receivedByUserLoginId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("lotId",OrderMaxUtility.getValidEntityString(this.lotId));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("datetimeReceived",OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
val.set("quantityRejected",OrderMaxUtility.getValidEntityBigDecimal(this.quantityRejected));
val.set("returnItemSeqId",OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("rejectionId",this.rejectionId);
valueMap.put("quantityOnHandTotal",this.quantityOnHandTotal);
valueMap.put("availableToPromiseTotal",this.availableToPromiseTotal);
valueMap.put("locationSeqId",this.locationSeqId);
valueMap.put("returnId",this.returnId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("productId",this.productId);
valueMap.put("quantityAccepted",this.quantityAccepted);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("itemDescription",this.itemDescription);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("unitCost",this.unitCost);
valueMap.put("receivedByUserLoginId",this.receivedByUserLoginId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("lotId",this.lotId);
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
 genVal = delegator.makeValue("ShipmentReceiptAndItem");
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
private java.lang.String rejectionId;
public java.lang.String getrejectionId() {
return rejectionId;
}
public void setrejectionId( java.lang.String rejectionId ) {
this.rejectionId = rejectionId;
}
private java.math.BigDecimal quantityOnHandTotal;
public java.math.BigDecimal getquantityOnHandTotal() {
return quantityOnHandTotal;
}
public void setquantityOnHandTotal( java.math.BigDecimal quantityOnHandTotal ) {
this.quantityOnHandTotal = quantityOnHandTotal;
}
private java.math.BigDecimal availableToPromiseTotal;
public java.math.BigDecimal getavailableToPromiseTotal() {
return availableToPromiseTotal;
}
public void setavailableToPromiseTotal( java.math.BigDecimal availableToPromiseTotal ) {
this.availableToPromiseTotal = availableToPromiseTotal;
}
private java.lang.String locationSeqId;
public java.lang.String getlocationSeqId() {
return locationSeqId;
}
public void setlocationSeqId( java.lang.String locationSeqId ) {
this.locationSeqId = locationSeqId;
}
private java.lang.String returnId;
public java.lang.String getreturnId() {
return returnId;
}
public void setreturnId( java.lang.String returnId ) {
this.returnId = returnId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
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
private java.lang.String shipmentItemSeqId;
public java.lang.String getshipmentItemSeqId() {
return shipmentItemSeqId;
}
public void setshipmentItemSeqId( java.lang.String shipmentItemSeqId ) {
this.shipmentItemSeqId = shipmentItemSeqId;
}
private java.math.BigDecimal unitCost;
public java.math.BigDecimal getunitCost() {
return unitCost;
}
public void setunitCost( java.math.BigDecimal unitCost ) {
this.unitCost = unitCost;
}
private java.lang.String receivedByUserLoginId;
public java.lang.String getreceivedByUserLoginId() {
return receivedByUserLoginId;
}
public void setreceivedByUserLoginId( java.lang.String receivedByUserLoginId ) {
this.receivedByUserLoginId = receivedByUserLoginId;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.lang.String lotId;
public java.lang.String getlotId() {
return lotId;
}
public void setlotId( java.lang.String lotId ) {
this.lotId = lotId;
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
        Collection<ShipmentReceiptAndItem> objectList = new ArrayList<ShipmentReceiptAndItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentReceiptAndItem(genVal));
        }
        return objectList;
    }    
}
