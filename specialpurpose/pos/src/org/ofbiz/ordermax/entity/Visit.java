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
public class Visit implements GenericValueObjectInterface {
public static final String module =Visit.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public Visit(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public Visit () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruDate","Thru Date"},
{"visitId","Visit Id"},
{"userLoginId","User Login Id"},
{"initialLocale","Initial Locale"},
{"clientUser","Client User"},
{"serverHostName","Server Host Name"},
{"clientHostName","Client Host Name"},
{"initialRequest","Initial Request"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"clientIpStateProvGeoId","Client Ip State Prov Geo Id"},
{"initialUserAgent","Initial User Agent"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"serverIpAddress","Server Ip Address"},
{"contactMechId","Contact Mech Id"},
{"cookie","Cookie"},
{"userCreated","User Created"},
{"roleTypeId","Role Type Id"},
{"clientIpIspName","Client Ip Isp Name"},
{"fromDate","From Date"},
{"sessionId","Session Id"},
{"partyId","Party Id"},
{"clientIpCountryGeoId","Client Ip Country Geo Id"},
{"initialReferrer","Initial Referrer"},
{"visitorId","Visitor Id"},
{"webappName","Webapp Name"},
{"clientIpPostalCode","Client Ip Postal Code"},
{"userAgentId","User Agent Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"clientIpAddress","Client Ip Address"},
};
protected void initObject(){
this.thruDate = UtilDateTime.nowTimestamp();
this.visitId = "";
this.userLoginId = "";
this.initialLocale = "";
this.clientUser = "";
this.serverHostName = "";
this.clientHostName = "";
this.initialRequest = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.clientIpStateProvGeoId = "";
this.initialUserAgent = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.serverIpAddress = "";
this.contactMechId = "";
this.cookie = "";
this.userCreated = "";
this.roleTypeId = "";
this.clientIpIspName = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.sessionId = "";
this.partyId = "";
this.clientIpCountryGeoId = "";
this.initialReferrer = "";
this.visitorId = "";
this.webappName = "";
this.clientIpPostalCode = "";
this.userAgentId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.clientIpAddress = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.visitId = (java.lang.String) genVal.get("visitId");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.initialLocale = (java.lang.String) genVal.get("initialLocale");
this.clientUser = (java.lang.String) genVal.get("clientUser");
this.serverHostName = (java.lang.String) genVal.get("serverHostName");
this.clientHostName = (java.lang.String) genVal.get("clientHostName");
this.initialRequest = (java.lang.String) genVal.get("initialRequest");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.clientIpStateProvGeoId = (java.lang.String) genVal.get("clientIpStateProvGeoId");
this.initialUserAgent = (java.lang.String) genVal.get("initialUserAgent");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.serverIpAddress = (java.lang.String) genVal.get("serverIpAddress");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.cookie = (java.lang.String) genVal.get("cookie");
this.userCreated = (java.lang.String) genVal.get("userCreated");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.clientIpIspName = (java.lang.String) genVal.get("clientIpIspName");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.sessionId = (java.lang.String) genVal.get("sessionId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.clientIpCountryGeoId = (java.lang.String) genVal.get("clientIpCountryGeoId");
this.initialReferrer = (java.lang.String) genVal.get("initialReferrer");
this.visitorId = (java.lang.String) genVal.get("visitorId");
this.webappName = (java.lang.String) genVal.get("webappName");
this.clientIpPostalCode = (java.lang.String) genVal.get("clientIpPostalCode");
this.userAgentId = (java.lang.String) genVal.get("userAgentId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.clientIpAddress = (java.lang.String) genVal.get("clientIpAddress");
}
protected void getGenericValue(GenericValue val) {
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("initialLocale",OrderMaxUtility.getValidEntityString(this.initialLocale));
val.set("clientUser",OrderMaxUtility.getValidEntityString(this.clientUser));
val.set("serverHostName",OrderMaxUtility.getValidEntityString(this.serverHostName));
val.set("clientHostName",OrderMaxUtility.getValidEntityString(this.clientHostName));
val.set("initialRequest",OrderMaxUtility.getValidEntityString(this.initialRequest));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("clientIpStateProvGeoId",OrderMaxUtility.getValidEntityString(this.clientIpStateProvGeoId));
val.set("initialUserAgent",OrderMaxUtility.getValidEntityString(this.initialUserAgent));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("serverIpAddress",OrderMaxUtility.getValidEntityString(this.serverIpAddress));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("cookie",OrderMaxUtility.getValidEntityString(this.cookie));
val.set("userCreated",OrderMaxUtility.getValidEntityString(this.userCreated));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("clientIpIspName",OrderMaxUtility.getValidEntityString(this.clientIpIspName));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("sessionId",OrderMaxUtility.getValidEntityString(this.sessionId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("clientIpCountryGeoId",OrderMaxUtility.getValidEntityString(this.clientIpCountryGeoId));
val.set("initialReferrer",OrderMaxUtility.getValidEntityString(this.initialReferrer));
val.set("visitorId",OrderMaxUtility.getValidEntityString(this.visitorId));
val.set("webappName",OrderMaxUtility.getValidEntityString(this.webappName));
val.set("clientIpPostalCode",OrderMaxUtility.getValidEntityString(this.clientIpPostalCode));
val.set("userAgentId",OrderMaxUtility.getValidEntityString(this.userAgentId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("clientIpAddress",OrderMaxUtility.getValidEntityString(this.clientIpAddress));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruDate",this.thruDate);
valueMap.put("visitId",this.visitId);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("initialLocale",this.initialLocale);
valueMap.put("clientUser",this.clientUser);
valueMap.put("serverHostName",this.serverHostName);
valueMap.put("clientHostName",this.clientHostName);
valueMap.put("initialRequest",this.initialRequest);
valueMap.put("clientIpStateProvGeoId",this.clientIpStateProvGeoId);
valueMap.put("initialUserAgent",this.initialUserAgent);
valueMap.put("serverIpAddress",this.serverIpAddress);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("cookie",this.cookie);
valueMap.put("userCreated",this.userCreated);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("clientIpIspName",this.clientIpIspName);
valueMap.put("fromDate",this.fromDate);
valueMap.put("sessionId",this.sessionId);
valueMap.put("partyId",this.partyId);
valueMap.put("clientIpCountryGeoId",this.clientIpCountryGeoId);
valueMap.put("initialReferrer",this.initialReferrer);
valueMap.put("visitorId",this.visitorId);
valueMap.put("webappName",this.webappName);
valueMap.put("clientIpPostalCode",this.clientIpPostalCode);
valueMap.put("userAgentId",this.userAgentId);
valueMap.put("clientIpAddress",this.clientIpAddress);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("Visit");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.lang.String initialLocale;
public java.lang.String getinitialLocale() {
return initialLocale;
}
public void setinitialLocale( java.lang.String initialLocale ) {
this.initialLocale = initialLocale;
}
private java.lang.String clientUser;
public java.lang.String getclientUser() {
return clientUser;
}
public void setclientUser( java.lang.String clientUser ) {
this.clientUser = clientUser;
}
private java.lang.String serverHostName;
public java.lang.String getserverHostName() {
return serverHostName;
}
public void setserverHostName( java.lang.String serverHostName ) {
this.serverHostName = serverHostName;
}
private java.lang.String clientHostName;
public java.lang.String getclientHostName() {
return clientHostName;
}
public void setclientHostName( java.lang.String clientHostName ) {
this.clientHostName = clientHostName;
}
private java.lang.String initialRequest;
public java.lang.String getinitialRequest() {
return initialRequest;
}
public void setinitialRequest( java.lang.String initialRequest ) {
this.initialRequest = initialRequest;
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
private java.lang.String clientIpStateProvGeoId;
public java.lang.String getclientIpStateProvGeoId() {
return clientIpStateProvGeoId;
}
public void setclientIpStateProvGeoId( java.lang.String clientIpStateProvGeoId ) {
this.clientIpStateProvGeoId = clientIpStateProvGeoId;
}
private java.lang.String initialUserAgent;
public java.lang.String getinitialUserAgent() {
return initialUserAgent;
}
public void setinitialUserAgent( java.lang.String initialUserAgent ) {
this.initialUserAgent = initialUserAgent;
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
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String cookie;
public java.lang.String getcookie() {
return cookie;
}
public void setcookie( java.lang.String cookie ) {
this.cookie = cookie;
}
private java.lang.String userCreated;
public java.lang.String getuserCreated() {
return userCreated;
}
public void setuserCreated( java.lang.String userCreated ) {
this.userCreated = userCreated;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String clientIpIspName;
public java.lang.String getclientIpIspName() {
return clientIpIspName;
}
public void setclientIpIspName( java.lang.String clientIpIspName ) {
this.clientIpIspName = clientIpIspName;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String sessionId;
public java.lang.String getsessionId() {
return sessionId;
}
public void setsessionId( java.lang.String sessionId ) {
this.sessionId = sessionId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String clientIpCountryGeoId;
public java.lang.String getclientIpCountryGeoId() {
return clientIpCountryGeoId;
}
public void setclientIpCountryGeoId( java.lang.String clientIpCountryGeoId ) {
this.clientIpCountryGeoId = clientIpCountryGeoId;
}
private java.lang.String initialReferrer;
public java.lang.String getinitialReferrer() {
return initialReferrer;
}
public void setinitialReferrer( java.lang.String initialReferrer ) {
this.initialReferrer = initialReferrer;
}
private java.lang.String visitorId;
public java.lang.String getvisitorId() {
return visitorId;
}
public void setvisitorId( java.lang.String visitorId ) {
this.visitorId = visitorId;
}
private java.lang.String webappName;
public java.lang.String getwebappName() {
return webappName;
}
public void setwebappName( java.lang.String webappName ) {
this.webappName = webappName;
}
private java.lang.String clientIpPostalCode;
public java.lang.String getclientIpPostalCode() {
return clientIpPostalCode;
}
public void setclientIpPostalCode( java.lang.String clientIpPostalCode ) {
this.clientIpPostalCode = clientIpPostalCode;
}
private java.lang.String userAgentId;
public java.lang.String getuserAgentId() {
return userAgentId;
}
public void setuserAgentId( java.lang.String userAgentId ) {
this.userAgentId = userAgentId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String clientIpAddress;
public java.lang.String getclientIpAddress() {
return clientIpAddress;
}
public void setclientIpAddress( java.lang.String clientIpAddress ) {
this.clientIpAddress = clientIpAddress;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Visit> objectList = new ArrayList<Visit>();
        for (GenericValue genVal : genList) {
            objectList.add(new Visit(genVal));
        }
        return objectList;
    }    
}
