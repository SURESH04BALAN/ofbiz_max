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
public class EntitySync implements GenericValueObjectInterface {
public static final String module =EntitySync.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EntitySync(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public EntitySync () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"keepRemoveInfoHours","Keep Remove Info Hours"},
{"preOfflineSynchTime","Pre Offline Synch Time"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"offlineSyncSplitMillis","Offline Sync Split Millis"},
{"lastSuccessfulSynchTime","Last Successful Synch Time"},
{"entitySyncId","Entity Sync Id"},
{"targetServiceName","Target Service Name"},
{"targetDelegatorName","Target Delegator Name"},
{"syncEndBufferMillis","Sync End Buffer Millis"},
{"lastHistoryStartDate","Last History Start Date"},
{"runStatusId","Run Status Id"},
{"maxRunningNoUpdateMillis","Max Running No Update Millis"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"forPullOnly","For Pull Only"},
{"forPushOnly","For Push Only"},
{"syncSplitMillis","Sync Split Millis"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.keepRemoveInfoHours = new Double(0);
this.preOfflineSynchTime = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.offlineSyncSplitMillis = "";
this.lastSuccessfulSynchTime = "";
this.entitySyncId = "";
this.targetServiceName = "";
this.targetDelegatorName = "";
this.syncEndBufferMillis = "";
this.lastHistoryStartDate = UtilDateTime.nowTimestamp();
this.runStatusId = "";
this.maxRunningNoUpdateMillis = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.forPullOnly = "";
this.forPushOnly = "";
this.syncSplitMillis = new Long(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.keepRemoveInfoHours = (java.lang.Double) genVal.get("keepRemoveInfoHours");
this.preOfflineSynchTime = (java.lang.String) genVal.get("preOfflineSynchTime");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.offlineSyncSplitMillis = (java.lang.String) genVal.get("offlineSyncSplitMillis");
this.lastSuccessfulSynchTime = (java.lang.String) genVal.get("lastSuccessfulSynchTime");
this.entitySyncId = (java.lang.String) genVal.get("entitySyncId");
this.targetServiceName = (java.lang.String) genVal.get("targetServiceName");
this.targetDelegatorName = (java.lang.String) genVal.get("targetDelegatorName");
this.syncEndBufferMillis = (java.lang.String) genVal.get("syncEndBufferMillis");
this.lastHistoryStartDate = (java.sql.Timestamp) genVal.get("lastHistoryStartDate");
this.runStatusId = (java.lang.String) genVal.get("runStatusId");
this.maxRunningNoUpdateMillis = (java.lang.String) genVal.get("maxRunningNoUpdateMillis");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.forPullOnly = (java.lang.String) genVal.get("forPullOnly");
this.forPushOnly = (java.lang.String) genVal.get("forPushOnly");
this.syncSplitMillis = (java.lang.Long) genVal.get("syncSplitMillis");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("keepRemoveInfoHours",OrderMaxUtility.getValidEntityDouble(this.keepRemoveInfoHours));
val.set("preOfflineSynchTime",OrderMaxUtility.getValidEntityString(this.preOfflineSynchTime));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("offlineSyncSplitMillis",OrderMaxUtility.getValidEntityString(this.offlineSyncSplitMillis));
val.set("lastSuccessfulSynchTime",OrderMaxUtility.getValidEntityString(this.lastSuccessfulSynchTime));
val.set("entitySyncId",OrderMaxUtility.getValidEntityString(this.entitySyncId));
val.set("targetServiceName",OrderMaxUtility.getValidEntityString(this.targetServiceName));
val.set("targetDelegatorName",OrderMaxUtility.getValidEntityString(this.targetDelegatorName));
val.set("syncEndBufferMillis",OrderMaxUtility.getValidEntityString(this.syncEndBufferMillis));
val.set("lastHistoryStartDate",OrderMaxUtility.getValidEntityTimestamp(this.lastHistoryStartDate));
val.set("runStatusId",OrderMaxUtility.getValidEntityString(this.runStatusId));
val.set("maxRunningNoUpdateMillis",OrderMaxUtility.getValidEntityString(this.maxRunningNoUpdateMillis));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("forPullOnly",OrderMaxUtility.getValidEntityString(this.forPullOnly));
val.set("forPushOnly",OrderMaxUtility.getValidEntityString(this.forPushOnly));
val.set("syncSplitMillis",this.syncSplitMillis);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("keepRemoveInfoHours",this.keepRemoveInfoHours);
valueMap.put("preOfflineSynchTime",this.preOfflineSynchTime);
valueMap.put("offlineSyncSplitMillis",this.offlineSyncSplitMillis);
valueMap.put("lastSuccessfulSynchTime",this.lastSuccessfulSynchTime);
valueMap.put("entitySyncId",this.entitySyncId);
valueMap.put("targetServiceName",this.targetServiceName);
valueMap.put("targetDelegatorName",this.targetDelegatorName);
valueMap.put("syncEndBufferMillis",this.syncEndBufferMillis);
valueMap.put("lastHistoryStartDate",this.lastHistoryStartDate);
valueMap.put("runStatusId",this.runStatusId);
valueMap.put("maxRunningNoUpdateMillis",this.maxRunningNoUpdateMillis);
valueMap.put("forPullOnly",this.forPullOnly);
valueMap.put("forPushOnly",this.forPushOnly);
valueMap.put("syncSplitMillis",this.syncSplitMillis);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EntitySync");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Double keepRemoveInfoHours;
public java.lang.Double getkeepRemoveInfoHours() {
return keepRemoveInfoHours;
}
public void setkeepRemoveInfoHours( java.lang.Double keepRemoveInfoHours ) {
this.keepRemoveInfoHours = keepRemoveInfoHours;
}
private java.lang.String preOfflineSynchTime;
public java.lang.String getpreOfflineSynchTime() {
return preOfflineSynchTime;
}
public void setpreOfflineSynchTime( java.lang.String preOfflineSynchTime ) {
this.preOfflineSynchTime = preOfflineSynchTime;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String offlineSyncSplitMillis;
public java.lang.String getofflineSyncSplitMillis() {
return offlineSyncSplitMillis;
}
public void setofflineSyncSplitMillis( java.lang.String offlineSyncSplitMillis ) {
this.offlineSyncSplitMillis = offlineSyncSplitMillis;
}
private java.lang.String lastSuccessfulSynchTime;
public java.lang.String getlastSuccessfulSynchTime() {
return lastSuccessfulSynchTime;
}
public void setlastSuccessfulSynchTime( java.lang.String lastSuccessfulSynchTime ) {
this.lastSuccessfulSynchTime = lastSuccessfulSynchTime;
}
private java.lang.String entitySyncId;
public java.lang.String getentitySyncId() {
return entitySyncId;
}
public void setentitySyncId( java.lang.String entitySyncId ) {
this.entitySyncId = entitySyncId;
}
private java.lang.String targetServiceName;
public java.lang.String gettargetServiceName() {
return targetServiceName;
}
public void settargetServiceName( java.lang.String targetServiceName ) {
this.targetServiceName = targetServiceName;
}
private java.lang.String targetDelegatorName;
public java.lang.String gettargetDelegatorName() {
return targetDelegatorName;
}
public void settargetDelegatorName( java.lang.String targetDelegatorName ) {
this.targetDelegatorName = targetDelegatorName;
}
private java.lang.String syncEndBufferMillis;
public java.lang.String getsyncEndBufferMillis() {
return syncEndBufferMillis;
}
public void setsyncEndBufferMillis( java.lang.String syncEndBufferMillis ) {
this.syncEndBufferMillis = syncEndBufferMillis;
}
private java.sql.Timestamp lastHistoryStartDate;
public java.sql.Timestamp getlastHistoryStartDate() {
return lastHistoryStartDate;
}
public void setlastHistoryStartDate( java.sql.Timestamp lastHistoryStartDate ) {
this.lastHistoryStartDate = lastHistoryStartDate;
}
private java.lang.String runStatusId;
public java.lang.String getrunStatusId() {
return runStatusId;
}
public void setrunStatusId( java.lang.String runStatusId ) {
this.runStatusId = runStatusId;
}
private java.lang.String maxRunningNoUpdateMillis;
public java.lang.String getmaxRunningNoUpdateMillis() {
return maxRunningNoUpdateMillis;
}
public void setmaxRunningNoUpdateMillis( java.lang.String maxRunningNoUpdateMillis ) {
this.maxRunningNoUpdateMillis = maxRunningNoUpdateMillis;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String forPullOnly;
public java.lang.String getforPullOnly() {
return forPullOnly;
}
public void setforPullOnly( java.lang.String forPullOnly ) {
this.forPullOnly = forPullOnly;
}
private java.lang.String forPushOnly;
public java.lang.String getforPushOnly() {
return forPushOnly;
}
public void setforPushOnly( java.lang.String forPushOnly ) {
this.forPushOnly = forPushOnly;
}
private java.lang.Long syncSplitMillis;
public java.lang.Long getsyncSplitMillis() {
return syncSplitMillis;
}
public void setsyncSplitMillis( java.lang.Long syncSplitMillis ) {
this.syncSplitMillis = syncSplitMillis;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<EntitySync> objectList = new ArrayList<EntitySync>();
        for (GenericValue genVal : genList) {
            objectList.add(new EntitySync(genVal));
        }
        return objectList;
    }    
}
