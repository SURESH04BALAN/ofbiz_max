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
public class ServerHitBin implements GenericValueObjectInterface {
public static final String module =ServerHitBin.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ServerHitBin(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ServerHitBin () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"totalTimeMillis","Total Time Millis"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"minTimeMillis","Min Time Millis"},
{"binStartDateTime","Bin Start Date Time"},
{"serverIpAddress","Server Ip Address"},
{"hitTypeId","Hit Type Id"},
{"internalContentId","Internal Content Id"},
{"maxTimeMillis","Max Time Millis"},
{"binEndDateTime","Bin End Date Time"},
{"contentId","Content Id"},
{"serverHitBinId","Server Hit Bin Id"},
{"serverHostName","Server Host Name"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"numberHits","Number Hits"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.totalTimeMillis = new Long(0);
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.minTimeMillis = new Long(0);
this.binStartDateTime = UtilDateTime.nowTimestamp();
this.serverIpAddress = "";
this.hitTypeId = "";
this.internalContentId = "";
this.maxTimeMillis = new Long(0);
this.binEndDateTime = UtilDateTime.nowTimestamp();
this.contentId = "";
this.serverHitBinId = "";
this.serverHostName = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.numberHits = new Long(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.totalTimeMillis = (java.lang.Long) genVal.get("totalTimeMillis");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.minTimeMillis = (java.lang.Long) genVal.get("minTimeMillis");
this.binStartDateTime = (java.sql.Timestamp) genVal.get("binStartDateTime");
this.serverIpAddress = (java.lang.String) genVal.get("serverIpAddress");
this.hitTypeId = (java.lang.String) genVal.get("hitTypeId");
this.internalContentId = (java.lang.String) genVal.get("internalContentId");
this.maxTimeMillis = (java.lang.Long) genVal.get("maxTimeMillis");
this.binEndDateTime = (java.sql.Timestamp) genVal.get("binEndDateTime");
this.contentId = (java.lang.String) genVal.get("contentId");
this.serverHitBinId = (java.lang.String) genVal.get("serverHitBinId");
this.serverHostName = (java.lang.String) genVal.get("serverHostName");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.numberHits = (java.lang.Long) genVal.get("numberHits");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("totalTimeMillis",this.totalTimeMillis);
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("minTimeMillis",this.minTimeMillis);
val.set("binStartDateTime",OrderMaxUtility.getValidEntityTimestamp(this.binStartDateTime));
val.set("serverIpAddress",OrderMaxUtility.getValidEntityString(this.serverIpAddress));
val.set("hitTypeId",OrderMaxUtility.getValidEntityString(this.hitTypeId));
val.set("internalContentId",OrderMaxUtility.getValidEntityString(this.internalContentId));
val.set("maxTimeMillis",this.maxTimeMillis);
val.set("binEndDateTime",OrderMaxUtility.getValidEntityTimestamp(this.binEndDateTime));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("serverHitBinId",OrderMaxUtility.getValidEntityString(this.serverHitBinId));
val.set("serverHostName",OrderMaxUtility.getValidEntityString(this.serverHostName));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("numberHits",this.numberHits);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("totalTimeMillis",this.totalTimeMillis);
valueMap.put("minTimeMillis",this.minTimeMillis);
valueMap.put("binStartDateTime",this.binStartDateTime);
valueMap.put("serverIpAddress",this.serverIpAddress);
valueMap.put("hitTypeId",this.hitTypeId);
valueMap.put("internalContentId",this.internalContentId);
valueMap.put("maxTimeMillis",this.maxTimeMillis);
valueMap.put("binEndDateTime",this.binEndDateTime);
valueMap.put("contentId",this.contentId);
valueMap.put("serverHitBinId",this.serverHitBinId);
valueMap.put("serverHostName",this.serverHostName);
valueMap.put("numberHits",this.numberHits);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ServerHitBin");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long totalTimeMillis;
public java.lang.Long gettotalTimeMillis() {
return totalTimeMillis;
}
public void settotalTimeMillis( java.lang.Long totalTimeMillis ) {
this.totalTimeMillis = totalTimeMillis;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.Long minTimeMillis;
public java.lang.Long getminTimeMillis() {
return minTimeMillis;
}
public void setminTimeMillis( java.lang.Long minTimeMillis ) {
this.minTimeMillis = minTimeMillis;
}
private java.sql.Timestamp binStartDateTime;
public java.sql.Timestamp getbinStartDateTime() {
return binStartDateTime;
}
public void setbinStartDateTime( java.sql.Timestamp binStartDateTime ) {
this.binStartDateTime = binStartDateTime;
}
private java.lang.String serverIpAddress;
public java.lang.String getserverIpAddress() {
return serverIpAddress;
}
public void setserverIpAddress( java.lang.String serverIpAddress ) {
this.serverIpAddress = serverIpAddress;
}
private java.lang.String hitTypeId;
public java.lang.String gethitTypeId() {
return hitTypeId;
}
public void sethitTypeId( java.lang.String hitTypeId ) {
this.hitTypeId = hitTypeId;
}
private java.lang.String internalContentId;
public java.lang.String getinternalContentId() {
return internalContentId;
}
public void setinternalContentId( java.lang.String internalContentId ) {
this.internalContentId = internalContentId;
}
private java.lang.Long maxTimeMillis;
public java.lang.Long getmaxTimeMillis() {
return maxTimeMillis;
}
public void setmaxTimeMillis( java.lang.Long maxTimeMillis ) {
this.maxTimeMillis = maxTimeMillis;
}
private java.sql.Timestamp binEndDateTime;
public java.sql.Timestamp getbinEndDateTime() {
return binEndDateTime;
}
public void setbinEndDateTime( java.sql.Timestamp binEndDateTime ) {
this.binEndDateTime = binEndDateTime;
}
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
}
private java.lang.String serverHitBinId;
public java.lang.String getserverHitBinId() {
return serverHitBinId;
}
public void setserverHitBinId( java.lang.String serverHitBinId ) {
this.serverHitBinId = serverHitBinId;
}
private java.lang.String serverHostName;
public java.lang.String getserverHostName() {
return serverHostName;
}
public void setserverHostName( java.lang.String serverHostName ) {
this.serverHostName = serverHostName;
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
private java.lang.Long numberHits;
public java.lang.Long getnumberHits() {
return numberHits;
}
public void setnumberHits( java.lang.Long numberHits ) {
this.numberHits = numberHits;
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
        Collection<ServerHitBin> objectList = new ArrayList<ServerHitBin>();
        for (GenericValue genVal : genList) {
            objectList.add(new ServerHitBin(genVal));
        }
        return objectList;
    }    
}
