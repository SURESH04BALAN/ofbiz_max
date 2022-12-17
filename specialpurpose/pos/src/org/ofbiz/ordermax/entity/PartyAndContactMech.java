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
public class PartyAndContactMech implements GenericValueObjectInterface {
public static final String module =PartyAndContactMech.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAndContactMech(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAndContactMech () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"paStateProvinceGeoId","Pa State Province Geo Id"},
{"thruDate","Thru Date"},
{"yearsWithContactMech","Years With Contact Mech"},
{"tnAreaCode","Tn Area Code"},
{"allowSolicitation","Allow Solicitation"},
{"tnAskForName","Tn Ask For Name"},
{"tnContactMechId","Tn Contact Mech Id"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"tnCountryCode","Tn Country Code"},
{"externalId","External Id"},
{"paContactMechId","Pa Contact Mech Id"},
{"dataSourceId","Data Source Id"},
{"isUnread","Is Unread"},
{"statusId","Status Id"},
{"verified","Verified"},
{"description","Description"},
{"paPostalCode","Pa Postal Code"},
{"createdDate","Created Date"},
{"paPostalCodeGeoId","Pa Postal Code Geo Id"},
{"infoString","Info String"},
{"paAddress2","Pa Address 2"},
{"paAddress1","Pa Address 1"},
{"contactMechId","Contact Mech Id"},
{"paAttnName","Pa Attn Name"},
{"paPostalCodeExt","Pa Postal Code Ext"},
{"contactMechTypeId","Contact Mech Type Id"},
{"partyTypeId","Party Type Id"},
{"paDirections","Pa Directions"},
{"paCity","Pa City"},
{"extension","Extension"},
{"roleTypeId","Role Type Id"},
{"paCountryGeoId","Pa Country Geo Id"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"monthsWithContactMech","Months With Contact Mech"},
{"lastModifiedDate","Last Modified Date"},
{"paToName","Pa To Name"},
{"tnContactNumber","Tn Contact Number"},
{"paCountyGeoId","Pa County Geo Id"},
{"comments","Comments"},
{"createdByUserLogin","Created By User Login"},
{"paGeoPointId","Pa Geo Point Id"},
};
protected void initObject(){
this.paStateProvinceGeoId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.yearsWithContactMech = "";
this.tnAreaCode = "";
this.allowSolicitation = "";
this.tnAskForName = "";
this.tnContactMechId = "";
this.preferredCurrencyUomId = "";
this.tnCountryCode = "";
this.externalId = "";
this.paContactMechId = "";
this.dataSourceId = "";
this.isUnread = "";
this.statusId = "";
this.verified = "";
this.description = "";
this.paPostalCode = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.paPostalCodeGeoId = "";
this.infoString = "";
this.paAddress2 = "";
this.paAddress1 = "";
this.contactMechId = "";
this.paAttnName = "";
this.paPostalCodeExt = "";
this.contactMechTypeId = "";
this.partyTypeId = "";
this.paDirections = "";
this.paCity = "";
this.extension = "";
this.roleTypeId = "";
this.paCountryGeoId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.monthsWithContactMech = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.paToName = "";
this.tnContactNumber = "";
this.paCountyGeoId = "";
this.comments = "";
this.createdByUserLogin = "";
this.paGeoPointId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.paStateProvinceGeoId = (java.lang.String) genVal.get("paStateProvinceGeoId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.yearsWithContactMech = (java.lang.String) genVal.get("yearsWithContactMech");
this.tnAreaCode = (java.lang.String) genVal.get("tnAreaCode");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.tnAskForName = (java.lang.String) genVal.get("tnAskForName");
this.tnContactMechId = (java.lang.String) genVal.get("tnContactMechId");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.tnCountryCode = (java.lang.String) genVal.get("tnCountryCode");
this.externalId = (java.lang.String) genVal.get("externalId");
this.paContactMechId = (java.lang.String) genVal.get("paContactMechId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.statusId = (java.lang.String) genVal.get("statusId");
this.verified = (java.lang.String) genVal.get("verified");
this.description = (java.lang.String) genVal.get("description");
this.paPostalCode = (java.lang.String) genVal.get("paPostalCode");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.paPostalCodeGeoId = (java.lang.String) genVal.get("paPostalCodeGeoId");
this.infoString = (java.lang.String) genVal.get("infoString");
this.paAddress2 = (java.lang.String) genVal.get("paAddress2");
this.paAddress1 = (java.lang.String) genVal.get("paAddress1");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.paAttnName = (java.lang.String) genVal.get("paAttnName");
this.paPostalCodeExt = (java.lang.String) genVal.get("paPostalCodeExt");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.paDirections = (java.lang.String) genVal.get("paDirections");
this.paCity = (java.lang.String) genVal.get("paCity");
this.extension = (java.lang.String) genVal.get("extension");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.paCountryGeoId = (java.lang.String) genVal.get("paCountryGeoId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.monthsWithContactMech = (java.lang.String) genVal.get("monthsWithContactMech");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.paToName = (java.lang.String) genVal.get("paToName");
this.tnContactNumber = (java.lang.String) genVal.get("tnContactNumber");
this.paCountyGeoId = (java.lang.String) genVal.get("paCountyGeoId");
this.comments = (java.lang.String) genVal.get("comments");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
this.paGeoPointId = (java.lang.String) genVal.get("paGeoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("paStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.paStateProvinceGeoId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("yearsWithContactMech",OrderMaxUtility.getValidEntityString(this.yearsWithContactMech));
val.set("tnAreaCode",OrderMaxUtility.getValidEntityString(this.tnAreaCode));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("tnAskForName",OrderMaxUtility.getValidEntityString(this.tnAskForName));
val.set("tnContactMechId",OrderMaxUtility.getValidEntityString(this.tnContactMechId));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("tnCountryCode",OrderMaxUtility.getValidEntityString(this.tnCountryCode));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("paContactMechId",OrderMaxUtility.getValidEntityString(this.paContactMechId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("verified",OrderMaxUtility.getValidEntityString(this.verified));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("paPostalCode",OrderMaxUtility.getValidEntityString(this.paPostalCode));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("paPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.paPostalCodeGeoId));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("paAddress2",OrderMaxUtility.getValidEntityString(this.paAddress2));
val.set("paAddress1",OrderMaxUtility.getValidEntityString(this.paAddress1));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("paAttnName",OrderMaxUtility.getValidEntityString(this.paAttnName));
val.set("paPostalCodeExt",OrderMaxUtility.getValidEntityString(this.paPostalCodeExt));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("paDirections",OrderMaxUtility.getValidEntityString(this.paDirections));
val.set("paCity",OrderMaxUtility.getValidEntityString(this.paCity));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("paCountryGeoId",OrderMaxUtility.getValidEntityString(this.paCountryGeoId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("monthsWithContactMech",OrderMaxUtility.getValidEntityString(this.monthsWithContactMech));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("paToName",OrderMaxUtility.getValidEntityString(this.paToName));
val.set("tnContactNumber",OrderMaxUtility.getValidEntityString(this.tnContactNumber));
val.set("paCountyGeoId",OrderMaxUtility.getValidEntityString(this.paCountyGeoId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
val.set("paGeoPointId",OrderMaxUtility.getValidEntityString(this.paGeoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("paStateProvinceGeoId",this.paStateProvinceGeoId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("yearsWithContactMech",this.yearsWithContactMech);
valueMap.put("tnAreaCode",this.tnAreaCode);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("tnAskForName",this.tnAskForName);
valueMap.put("tnContactMechId",this.tnContactMechId);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("tnCountryCode",this.tnCountryCode);
valueMap.put("externalId",this.externalId);
valueMap.put("paContactMechId",this.paContactMechId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("statusId",this.statusId);
valueMap.put("verified",this.verified);
valueMap.put("description",this.description);
valueMap.put("paPostalCode",this.paPostalCode);
valueMap.put("createdDate",this.createdDate);
valueMap.put("paPostalCodeGeoId",this.paPostalCodeGeoId);
valueMap.put("infoString",this.infoString);
valueMap.put("paAddress2",this.paAddress2);
valueMap.put("paAddress1",this.paAddress1);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("paAttnName",this.paAttnName);
valueMap.put("paPostalCodeExt",this.paPostalCodeExt);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("paDirections",this.paDirections);
valueMap.put("paCity",this.paCity);
valueMap.put("extension",this.extension);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("paCountryGeoId",this.paCountryGeoId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("monthsWithContactMech",this.monthsWithContactMech);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("paToName",this.paToName);
valueMap.put("tnContactNumber",this.tnContactNumber);
valueMap.put("paCountyGeoId",this.paCountyGeoId);
valueMap.put("comments",this.comments);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
valueMap.put("paGeoPointId",this.paGeoPointId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAndContactMech");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String paStateProvinceGeoId;
public java.lang.String getpaStateProvinceGeoId() {
return paStateProvinceGeoId;
}
public void setpaStateProvinceGeoId( java.lang.String paStateProvinceGeoId ) {
this.paStateProvinceGeoId = paStateProvinceGeoId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String yearsWithContactMech;
public java.lang.String getyearsWithContactMech() {
return yearsWithContactMech;
}
public void setyearsWithContactMech( java.lang.String yearsWithContactMech ) {
this.yearsWithContactMech = yearsWithContactMech;
}
private java.lang.String tnAreaCode;
public java.lang.String gettnAreaCode() {
return tnAreaCode;
}
public void settnAreaCode( java.lang.String tnAreaCode ) {
this.tnAreaCode = tnAreaCode;
}
private java.lang.String allowSolicitation;
public java.lang.String getallowSolicitation() {
return allowSolicitation;
}
public void setallowSolicitation( java.lang.String allowSolicitation ) {
this.allowSolicitation = allowSolicitation;
}
private java.lang.String tnAskForName;
public java.lang.String gettnAskForName() {
return tnAskForName;
}
public void settnAskForName( java.lang.String tnAskForName ) {
this.tnAskForName = tnAskForName;
}
private java.lang.String tnContactMechId;
public java.lang.String gettnContactMechId() {
return tnContactMechId;
}
public void settnContactMechId( java.lang.String tnContactMechId ) {
this.tnContactMechId = tnContactMechId;
}
private java.lang.String preferredCurrencyUomId;
public java.lang.String getpreferredCurrencyUomId() {
return preferredCurrencyUomId;
}
public void setpreferredCurrencyUomId( java.lang.String preferredCurrencyUomId ) {
this.preferredCurrencyUomId = preferredCurrencyUomId;
}
private java.lang.String tnCountryCode;
public java.lang.String gettnCountryCode() {
return tnCountryCode;
}
public void settnCountryCode( java.lang.String tnCountryCode ) {
this.tnCountryCode = tnCountryCode;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
}
private java.lang.String paContactMechId;
public java.lang.String getpaContactMechId() {
return paContactMechId;
}
public void setpaContactMechId( java.lang.String paContactMechId ) {
this.paContactMechId = paContactMechId;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String isUnread;
public java.lang.String getisUnread() {
return isUnread;
}
public void setisUnread( java.lang.String isUnread ) {
this.isUnread = isUnread;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String verified;
public java.lang.String getverified() {
return verified;
}
public void setverified( java.lang.String verified ) {
this.verified = verified;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String paPostalCode;
public java.lang.String getpaPostalCode() {
return paPostalCode;
}
public void setpaPostalCode( java.lang.String paPostalCode ) {
this.paPostalCode = paPostalCode;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String paPostalCodeGeoId;
public java.lang.String getpaPostalCodeGeoId() {
return paPostalCodeGeoId;
}
public void setpaPostalCodeGeoId( java.lang.String paPostalCodeGeoId ) {
this.paPostalCodeGeoId = paPostalCodeGeoId;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
private java.lang.String paAddress2;
public java.lang.String getpaAddress2() {
return paAddress2;
}
public void setpaAddress2( java.lang.String paAddress2 ) {
this.paAddress2 = paAddress2;
}
private java.lang.String paAddress1;
public java.lang.String getpaAddress1() {
return paAddress1;
}
public void setpaAddress1( java.lang.String paAddress1 ) {
this.paAddress1 = paAddress1;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String paAttnName;
public java.lang.String getpaAttnName() {
return paAttnName;
}
public void setpaAttnName( java.lang.String paAttnName ) {
this.paAttnName = paAttnName;
}
private java.lang.String paPostalCodeExt;
public java.lang.String getpaPostalCodeExt() {
return paPostalCodeExt;
}
public void setpaPostalCodeExt( java.lang.String paPostalCodeExt ) {
this.paPostalCodeExt = paPostalCodeExt;
}
private java.lang.String contactMechTypeId;
public java.lang.String getcontactMechTypeId() {
return contactMechTypeId;
}
public void setcontactMechTypeId( java.lang.String contactMechTypeId ) {
this.contactMechTypeId = contactMechTypeId;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String paDirections;
public java.lang.String getpaDirections() {
return paDirections;
}
public void setpaDirections( java.lang.String paDirections ) {
this.paDirections = paDirections;
}
private java.lang.String paCity;
public java.lang.String getpaCity() {
return paCity;
}
public void setpaCity( java.lang.String paCity ) {
this.paCity = paCity;
}
private java.lang.String extension;
public java.lang.String getextension() {
return extension;
}
public void setextension( java.lang.String extension ) {
this.extension = extension;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String paCountryGeoId;
public java.lang.String getpaCountryGeoId() {
return paCountryGeoId;
}
public void setpaCountryGeoId( java.lang.String paCountryGeoId ) {
this.paCountryGeoId = paCountryGeoId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String monthsWithContactMech;
public java.lang.String getmonthsWithContactMech() {
return monthsWithContactMech;
}
public void setmonthsWithContactMech( java.lang.String monthsWithContactMech ) {
this.monthsWithContactMech = monthsWithContactMech;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String paToName;
public java.lang.String getpaToName() {
return paToName;
}
public void setpaToName( java.lang.String paToName ) {
this.paToName = paToName;
}
private java.lang.String tnContactNumber;
public java.lang.String gettnContactNumber() {
return tnContactNumber;
}
public void settnContactNumber( java.lang.String tnContactNumber ) {
this.tnContactNumber = tnContactNumber;
}
private java.lang.String paCountyGeoId;
public java.lang.String getpaCountyGeoId() {
return paCountyGeoId;
}
public void setpaCountyGeoId( java.lang.String paCountyGeoId ) {
this.paCountyGeoId = paCountyGeoId;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
private java.lang.String paGeoPointId;
public java.lang.String getpaGeoPointId() {
return paGeoPointId;
}
public void setpaGeoPointId( java.lang.String paGeoPointId ) {
this.paGeoPointId = paGeoPointId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAndContactMech> objectList = new ArrayList<PartyAndContactMech>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAndContactMech(genVal));
        }
        return objectList;
    }    
}
