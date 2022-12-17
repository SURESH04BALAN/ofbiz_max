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
public class InventoryTransfer implements GenericValueObjectInterface {
public static final String module =InventoryTransfer.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InventoryTransfer(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InventoryTransfer () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"receiveDate","Receive Date"},
{"facilityIdTo","Facility Id To"},
{"containerIdTo","Container Id To"},
{"locationSeqId","Location Seq Id"},
{"facilityId","Facility Id"},
{"inventoryTransferId","Inventory Transfer Id"},
{"sendDate","Send Date"},
{"statusId","Status Id"},
{"createdTxStamp","Created Tx Stamp"},
{"itemIssuanceId","Item Issuance Id"},
{"createdStamp","Created Stamp"},
{"inventoryItemId","Inventory Item Id"},
{"locationSeqIdTo","Location Seq Id To"},
{"containerId","Container Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"comments","Comments"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.receiveDate = UtilDateTime.nowTimestamp();
this.facilityIdTo = "";
this.containerIdTo = "";
this.locationSeqId = "";
this.facilityId = "";
this.inventoryTransferId = "";
this.sendDate = UtilDateTime.nowTimestamp();
this.statusId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.itemIssuanceId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.inventoryItemId = "";
this.locationSeqIdTo = "";
this.containerId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.receiveDate = (java.sql.Timestamp) genVal.get("receiveDate");
this.facilityIdTo = (java.lang.String) genVal.get("facilityIdTo");
this.containerIdTo = (java.lang.String) genVal.get("containerIdTo");
this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.inventoryTransferId = (java.lang.String) genVal.get("inventoryTransferId");
this.sendDate = (java.sql.Timestamp) genVal.get("sendDate");
this.statusId = (java.lang.String) genVal.get("statusId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.locationSeqIdTo = (java.lang.String) genVal.get("locationSeqIdTo");
this.containerId = (java.lang.String) genVal.get("containerId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("receiveDate",OrderMaxUtility.getValidEntityTimestamp(this.receiveDate));
val.set("facilityIdTo",OrderMaxUtility.getValidEntityString(this.facilityIdTo));
val.set("containerIdTo",OrderMaxUtility.getValidEntityString(this.containerIdTo));
val.set("locationSeqId",OrderMaxUtility.getValidEntityString(this.locationSeqId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("inventoryTransferId",OrderMaxUtility.getValidEntityString(this.inventoryTransferId));
val.set("sendDate",OrderMaxUtility.getValidEntityTimestamp(this.sendDate));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("locationSeqIdTo",OrderMaxUtility.getValidEntityString(this.locationSeqIdTo));
val.set("containerId",OrderMaxUtility.getValidEntityString(this.containerId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("receiveDate",this.receiveDate);
valueMap.put("facilityIdTo",this.facilityIdTo);
valueMap.put("containerIdTo",this.containerIdTo);
valueMap.put("locationSeqId",this.locationSeqId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("inventoryTransferId",this.inventoryTransferId);
valueMap.put("sendDate",this.sendDate);
valueMap.put("statusId",this.statusId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("locationSeqIdTo",this.locationSeqIdTo);
valueMap.put("containerId",this.containerId);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InventoryTransfer");
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
private java.sql.Timestamp receiveDate;
public java.sql.Timestamp getreceiveDate() {
return receiveDate;
}
public void setreceiveDate( java.sql.Timestamp receiveDate ) {
this.receiveDate = receiveDate;
}
private java.lang.String facilityIdTo;
public java.lang.String getfacilityIdTo() {
return facilityIdTo;
}
public void setfacilityIdTo( java.lang.String facilityIdTo ) {
this.facilityIdTo = facilityIdTo;
}
private java.lang.String containerIdTo;
public java.lang.String getcontainerIdTo() {
return containerIdTo;
}
public void setcontainerIdTo( java.lang.String containerIdTo ) {
this.containerIdTo = containerIdTo;
}
private java.lang.String locationSeqId;
public java.lang.String getlocationSeqId() {
return locationSeqId;
}
public void setlocationSeqId( java.lang.String locationSeqId ) {
this.locationSeqId = locationSeqId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String inventoryTransferId;
public java.lang.String getinventoryTransferId() {
return inventoryTransferId;
}
public void setinventoryTransferId( java.lang.String inventoryTransferId ) {
this.inventoryTransferId = inventoryTransferId;
}
private java.sql.Timestamp sendDate;
public java.sql.Timestamp getsendDate() {
return sendDate;
}
public void setsendDate( java.sql.Timestamp sendDate ) {
this.sendDate = sendDate;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
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
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.lang.String locationSeqIdTo;
public java.lang.String getlocationSeqIdTo() {
return locationSeqIdTo;
}
public void setlocationSeqIdTo( java.lang.String locationSeqIdTo ) {
this.locationSeqIdTo = locationSeqIdTo;
}
private java.lang.String containerId;
public java.lang.String getcontainerId() {
return containerId;
}
public void setcontainerId( java.lang.String containerId ) {
this.containerId = containerId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryTransfer> objectList = new ArrayList<InventoryTransfer>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryTransfer(genVal));
        }
        return objectList;
    }    
}
