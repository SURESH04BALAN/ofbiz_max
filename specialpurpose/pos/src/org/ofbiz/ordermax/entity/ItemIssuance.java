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
public class ItemIssuance implements GenericValueObjectInterface {
public static final String module =ItemIssuance.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ItemIssuance(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ItemIssuance () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"orderItemSeqId","Order Item Seq Id"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"maintHistSeqId","Maint Hist Seq Id"},
{"shipmentId","Shipment Id"},
{"itemIssuanceId","Item Issuance Id"},
{"createdStamp","Created Stamp"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"quantity","Quantity"},
{"issuedDateTime","Issued Date Time"},
{"inventoryItemId","Inventory Item Id"},
{"fixedAssetId","Fixed Asset Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"issuedByUserLoginId","Issued By User Login Id"},
{"orderId","Order Id"},
{"cancelQuantity","Cancel Quantity"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.orderItemSeqId = "";
this.shipmentItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.maintHistSeqId = "";
this.shipmentId = "";
this.itemIssuanceId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.shipGroupSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.issuedDateTime = UtilDateTime.nowTimestamp();
this.inventoryItemId = "";
this.fixedAssetId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.issuedByUserLoginId = "";
this.orderId = "";
this.cancelQuantity = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.maintHistSeqId = (java.lang.String) genVal.get("maintHistSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.issuedDateTime = (java.sql.Timestamp) genVal.get("issuedDateTime");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.issuedByUserLoginId = (java.lang.String) genVal.get("issuedByUserLoginId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("maintHistSeqId",OrderMaxUtility.getValidEntityString(this.maintHistSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("issuedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.issuedDateTime));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("issuedByUserLoginId",OrderMaxUtility.getValidEntityString(this.issuedByUserLoginId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("maintHistSeqId",this.maintHistSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("issuedDateTime",this.issuedDateTime);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("issuedByUserLoginId",this.issuedByUserLoginId);
valueMap.put("orderId",this.orderId);
valueMap.put("cancelQuantity",this.cancelQuantity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ItemIssuance");
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
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
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
private java.lang.String maintHistSeqId;
public java.lang.String getmaintHistSeqId() {
return maintHistSeqId;
}
public void setmaintHistSeqId( java.lang.String maintHistSeqId ) {
this.maintHistSeqId = maintHistSeqId;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
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
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.sql.Timestamp issuedDateTime;
public java.sql.Timestamp getissuedDateTime() {
return issuedDateTime;
}
public void setissuedDateTime( java.sql.Timestamp issuedDateTime ) {
this.issuedDateTime = issuedDateTime;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String issuedByUserLoginId;
public java.lang.String getissuedByUserLoginId() {
return issuedByUserLoginId;
}
public void setissuedByUserLoginId( java.lang.String issuedByUserLoginId ) {
this.issuedByUserLoginId = issuedByUserLoginId;
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
        Collection<ItemIssuance> objectList = new ArrayList<ItemIssuance>();
        for (GenericValue genVal : genList) {
            objectList.add(new ItemIssuance(genVal));
        }
        return objectList;
    }    
}
