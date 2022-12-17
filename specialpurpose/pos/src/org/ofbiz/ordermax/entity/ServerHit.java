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
public class ServerHit implements GenericValueObjectInterface {
public static final String module =ServerHit.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ServerHit(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ServerHit () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"numOfBytes","Num Of Bytes"},
{"hitStartDateTime","Hit Start Date Time"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"serverIpAddress","Server Ip Address"},
{"hitTypeId","Hit Type Id"},
{"internalContentId","Internal Content Id"},
{"idByIpContactMechId","Id By Ip Contact Mech Id"},
{"userLoginId","User Login Id"},
{"visitId","Visit Id"},
{"requestUrl","Request Url"},
{"contentId","Content Id"},
{"referrerUrl","Referrer Url"},
{"statusId","Status Id"},
{"serverHostName","Server Host Name"},
{"refByWebContactMechId","Ref By Web Contact Mech Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"partyId","Party Id"},
{"runningTimeMillis","Running Time Millis"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.numOfBytes = "";
this.hitStartDateTime = UtilDateTime.nowTimestamp();
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.serverIpAddress = "";
this.hitTypeId = "";
this.internalContentId = "";
this.idByIpContactMechId = "";
this.userLoginId = "";
this.visitId = "";
this.requestUrl = "";
this.contentId = "";
this.referrerUrl = "";
this.statusId = "";
this.serverHostName = "";
this.refByWebContactMechId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.partyId = "";
this.runningTimeMillis = new Long(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.numOfBytes = (java.lang.String) genVal.get("numOfBytes");
this.hitStartDateTime = (java.sql.Timestamp) genVal.get("hitStartDateTime");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.serverIpAddress = (java.lang.String) genVal.get("serverIpAddress");
this.hitTypeId = (java.lang.String) genVal.get("hitTypeId");
this.internalContentId = (java.lang.String) genVal.get("internalContentId");
this.idByIpContactMechId = (java.lang.String) genVal.get("idByIpContactMechId");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.visitId = (java.lang.String) genVal.get("visitId");
this.requestUrl = (java.lang.String) genVal.get("requestUrl");
this.contentId = (java.lang.String) genVal.get("contentId");
this.referrerUrl = (java.lang.String) genVal.get("referrerUrl");
this.statusId = (java.lang.String) genVal.get("statusId");
this.serverHostName = (java.lang.String) genVal.get("serverHostName");
this.refByWebContactMechId = (java.lang.String) genVal.get("refByWebContactMechId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.partyId = (java.lang.String) genVal.get("partyId");
this.runningTimeMillis = (java.lang.Long) genVal.get("runningTimeMillis");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("numOfBytes",OrderMaxUtility.getValidEntityString(this.numOfBytes));
val.set("hitStartDateTime",OrderMaxUtility.getValidEntityTimestamp(this.hitStartDateTime));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("serverIpAddress",OrderMaxUtility.getValidEntityString(this.serverIpAddress));
val.set("hitTypeId",OrderMaxUtility.getValidEntityString(this.hitTypeId));
val.set("internalContentId",OrderMaxUtility.getValidEntityString(this.internalContentId));
val.set("idByIpContactMechId",OrderMaxUtility.getValidEntityString(this.idByIpContactMechId));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("requestUrl",OrderMaxUtility.getValidEntityString(this.requestUrl));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("referrerUrl",OrderMaxUtility.getValidEntityString(this.referrerUrl));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("serverHostName",OrderMaxUtility.getValidEntityString(this.serverHostName));
val.set("refByWebContactMechId",OrderMaxUtility.getValidEntityString(this.refByWebContactMechId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("runningTimeMillis",this.runningTimeMillis);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("numOfBytes",this.numOfBytes);
valueMap.put("hitStartDateTime",this.hitStartDateTime);
valueMap.put("serverIpAddress",this.serverIpAddress);
valueMap.put("hitTypeId",this.hitTypeId);
valueMap.put("internalContentId",this.internalContentId);
valueMap.put("idByIpContactMechId",this.idByIpContactMechId);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("visitId",this.visitId);
valueMap.put("requestUrl",this.requestUrl);
valueMap.put("contentId",this.contentId);
valueMap.put("referrerUrl",this.referrerUrl);
valueMap.put("statusId",this.statusId);
valueMap.put("serverHostName",this.serverHostName);
valueMap.put("refByWebContactMechId",this.refByWebContactMechId);
valueMap.put("partyId",this.partyId);
valueMap.put("runningTimeMillis",this.runningTimeMillis);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ServerHit");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String numOfBytes;
public java.lang.String getnumOfBytes() {
return numOfBytes;
}
public void setnumOfBytes( java.lang.String numOfBytes ) {
this.numOfBytes = numOfBytes;
}
private java.sql.Timestamp hitStartDateTime;
public java.sql.Timestamp gethitStartDateTime() {
return hitStartDateTime;
}
public void sethitStartDateTime( java.sql.Timestamp hitStartDateTime ) {
this.hitStartDateTime = hitStartDateTime;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
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
private java.lang.String idByIpContactMechId;
public java.lang.String getidByIpContactMechId() {
return idByIpContactMechId;
}
public void setidByIpContactMechId( java.lang.String idByIpContactMechId ) {
this.idByIpContactMechId = idByIpContactMechId;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
}
private java.lang.String requestUrl;
public java.lang.String getrequestUrl() {
return requestUrl;
}
public void setrequestUrl( java.lang.String requestUrl ) {
this.requestUrl = requestUrl;
}
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
}
private java.lang.String referrerUrl;
public java.lang.String getreferrerUrl() {
return referrerUrl;
}
public void setreferrerUrl( java.lang.String referrerUrl ) {
this.referrerUrl = referrerUrl;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String serverHostName;
public java.lang.String getserverHostName() {
return serverHostName;
}
public void setserverHostName( java.lang.String serverHostName ) {
this.serverHostName = serverHostName;
}
private java.lang.String refByWebContactMechId;
public java.lang.String getrefByWebContactMechId() {
return refByWebContactMechId;
}
public void setrefByWebContactMechId( java.lang.String refByWebContactMechId ) {
this.refByWebContactMechId = refByWebContactMechId;
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
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.Long runningTimeMillis;
public java.lang.Long getrunningTimeMillis() {
return runningTimeMillis;
}
public void setrunningTimeMillis( java.lang.Long runningTimeMillis ) {
this.runningTimeMillis = runningTimeMillis;
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
        Collection<ServerHit> objectList = new ArrayList<ServerHit>();
        for (GenericValue genVal : genList) {
            objectList.add(new ServerHit(genVal));
        }
        return objectList;
    }    
}
