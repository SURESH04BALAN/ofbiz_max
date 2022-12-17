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
public class PartyRelationshipAndContactMechDetail implements GenericValueObjectInterface {
public static final String module =PartyRelationshipAndContactMechDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyRelationshipAndContactMechDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyRelationshipAndContactMechDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"paStateProvinceGeoId","Pa State Province Geo Id"},
{"tnAreaCode","Tn Area Code"},
{"tnContactMechId","Tn Contact Mech Id"},
{"relationshipName","Relationship Name"},
{"partyIdFrom","Party Id From"},
{"dataSourceId","Data Source Id"},
{"roleTypeIdTo","Role Type Id To"},
{"partyStatusId","Party Status Id"},
{"description","Description"},
{"priorityTypeId","Priority Type Id"},
{"createdDate","Created Date"},
{"infoString","Info String"},
{"paAddress2","Pa Address 2"},
{"middleName","Middle Name"},
{"paAddress1","Pa Address 1"},
{"lastName","Last Name"},
{"partyIdTo","Party Id To"},
{"firstNameLocal","First Name Local"},
{"paAttnName","Pa Attn Name"},
{"paPostalCodeExt","Pa Postal Code Ext"},
{"securityGroupId","Security Group Id"},
{"paDirections","Pa Directions"},
{"personalTitle","Personal Title"},
{"partyTypeId","Party Type Id"},
{"paCountryGeoId","Pa Country Geo Id"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"tnContactNumber","Tn Contact Number"},
{"groupName","Group Name"},
{"thruDate","Thru Date"},
{"tnAskForName","Tn Ask For Name"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"partyRelationshipTypeId","Party Relationship Type Id"},
{"tnCountryCode","Tn Country Code"},
{"paContactMechId","Pa Contact Mech Id"},
{"externalId","External Id"},
{"isUnread","Is Unread"},
{"statusId","Status Id"},
{"groupNameLocal","Group Name Local"},
{"lastNameLocal","Last Name Local"},
{"paPostalCode","Pa Postal Code"},
{"firstName","First Name"},
{"paPostalCodeGeoId","Pa Postal Code Geo Id"},
{"permissionsEnumId","Permissions Enum Id"},
{"contactMechId","Contact Mech Id"},
{"roleTypeIdFrom","Role Type Id From"},
{"contactMechTypeId","Contact Mech Type Id"},
{"suffix","Suffix"},
{"paCity","Pa City"},
{"positionTitle","Position Title"},
{"lastModifiedDate","Last Modified Date"},
{"paToName","Pa To Name"},
{"paCountyGeoId","Pa County Geo Id"},
{"comments","Comments"},
{"createdByUserLogin","Created By User Login"},
{"paGeoPointId","Pa Geo Point Id"},
};
protected void initObject(){
this.paStateProvinceGeoId = "";
this.tnAreaCode = "";
this.tnContactMechId = "";
this.relationshipName = "";
this.partyIdFrom = "";
this.dataSourceId = "";
this.roleTypeIdTo = "";
this.partyStatusId = "";
this.description = "";
this.priorityTypeId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.infoString = "";
this.paAddress2 = "";
this.middleName = "";
this.paAddress1 = "";
this.lastName = "";
this.partyIdTo = "";
this.firstNameLocal = "";
this.paAttnName = "";
this.paPostalCodeExt = "";
this.securityGroupId = "";
this.paDirections = "";
this.personalTitle = "";
this.partyTypeId = "";
this.paCountryGeoId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.tnContactNumber = "";
this.groupName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.tnAskForName = "";
this.preferredCurrencyUomId = "";
this.partyRelationshipTypeId = "";
this.tnCountryCode = "";
this.paContactMechId = "";
this.externalId = "";
this.isUnread = "";
this.statusId = "";
this.groupNameLocal = "";
this.lastNameLocal = "";
this.paPostalCode = "";
this.firstName = "";
this.paPostalCodeGeoId = "";
this.permissionsEnumId = "";
this.contactMechId = "";
this.roleTypeIdFrom = "";
this.contactMechTypeId = "";
this.suffix = "";
this.paCity = "";
this.positionTitle = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.paToName = "";
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
this.tnAreaCode = (java.lang.String) genVal.get("tnAreaCode");
this.tnContactMechId = (java.lang.String) genVal.get("tnContactMechId");
this.relationshipName = (java.lang.String) genVal.get("relationshipName");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
this.partyStatusId = (java.lang.String) genVal.get("partyStatusId");
this.description = (java.lang.String) genVal.get("description");
this.priorityTypeId = (java.lang.String) genVal.get("priorityTypeId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.infoString = (java.lang.String) genVal.get("infoString");
this.paAddress2 = (java.lang.String) genVal.get("paAddress2");
this.middleName = (java.lang.String) genVal.get("middleName");
this.paAddress1 = (java.lang.String) genVal.get("paAddress1");
this.lastName = (java.lang.String) genVal.get("lastName");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
this.paAttnName = (java.lang.String) genVal.get("paAttnName");
this.paPostalCodeExt = (java.lang.String) genVal.get("paPostalCodeExt");
this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
this.paDirections = (java.lang.String) genVal.get("paDirections");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.paCountryGeoId = (java.lang.String) genVal.get("paCountryGeoId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.tnContactNumber = (java.lang.String) genVal.get("tnContactNumber");
this.groupName = (java.lang.String) genVal.get("groupName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.tnAskForName = (java.lang.String) genVal.get("tnAskForName");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.partyRelationshipTypeId = (java.lang.String) genVal.get("partyRelationshipTypeId");
this.tnCountryCode = (java.lang.String) genVal.get("tnCountryCode");
this.paContactMechId = (java.lang.String) genVal.get("paContactMechId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.statusId = (java.lang.String) genVal.get("statusId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
this.paPostalCode = (java.lang.String) genVal.get("paPostalCode");
this.firstName = (java.lang.String) genVal.get("firstName");
this.paPostalCodeGeoId = (java.lang.String) genVal.get("paPostalCodeGeoId");
this.permissionsEnumId = (java.lang.String) genVal.get("permissionsEnumId");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.roleTypeIdFrom = (java.lang.String) genVal.get("roleTypeIdFrom");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.suffix = (java.lang.String) genVal.get("suffix");
this.paCity = (java.lang.String) genVal.get("paCity");
this.positionTitle = (java.lang.String) genVal.get("positionTitle");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.paToName = (java.lang.String) genVal.get("paToName");
this.paCountyGeoId = (java.lang.String) genVal.get("paCountyGeoId");
this.comments = (java.lang.String) genVal.get("comments");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
this.paGeoPointId = (java.lang.String) genVal.get("paGeoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("paStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.paStateProvinceGeoId));
val.set("tnAreaCode",OrderMaxUtility.getValidEntityString(this.tnAreaCode));
val.set("tnContactMechId",OrderMaxUtility.getValidEntityString(this.tnContactMechId));
val.set("relationshipName",OrderMaxUtility.getValidEntityString(this.relationshipName));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("roleTypeIdTo",OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
val.set("partyStatusId",OrderMaxUtility.getValidEntityString(this.partyStatusId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("priorityTypeId",OrderMaxUtility.getValidEntityString(this.priorityTypeId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("paAddress2",OrderMaxUtility.getValidEntityString(this.paAddress2));
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("paAddress1",OrderMaxUtility.getValidEntityString(this.paAddress1));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("firstNameLocal",OrderMaxUtility.getValidEntityString(this.firstNameLocal));
val.set("paAttnName",OrderMaxUtility.getValidEntityString(this.paAttnName));
val.set("paPostalCodeExt",OrderMaxUtility.getValidEntityString(this.paPostalCodeExt));
val.set("securityGroupId",OrderMaxUtility.getValidEntityString(this.securityGroupId));
val.set("paDirections",OrderMaxUtility.getValidEntityString(this.paDirections));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("paCountryGeoId",OrderMaxUtility.getValidEntityString(this.paCountryGeoId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("tnContactNumber",OrderMaxUtility.getValidEntityString(this.tnContactNumber));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("tnAskForName",OrderMaxUtility.getValidEntityString(this.tnAskForName));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("partyRelationshipTypeId",OrderMaxUtility.getValidEntityString(this.partyRelationshipTypeId));
val.set("tnCountryCode",OrderMaxUtility.getValidEntityString(this.tnCountryCode));
val.set("paContactMechId",OrderMaxUtility.getValidEntityString(this.paContactMechId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("lastNameLocal",OrderMaxUtility.getValidEntityString(this.lastNameLocal));
val.set("paPostalCode",OrderMaxUtility.getValidEntityString(this.paPostalCode));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("paPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.paPostalCodeGeoId));
val.set("permissionsEnumId",OrderMaxUtility.getValidEntityString(this.permissionsEnumId));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("roleTypeIdFrom",OrderMaxUtility.getValidEntityString(this.roleTypeIdFrom));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("paCity",OrderMaxUtility.getValidEntityString(this.paCity));
val.set("positionTitle",OrderMaxUtility.getValidEntityString(this.positionTitle));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("paToName",OrderMaxUtility.getValidEntityString(this.paToName));
val.set("paCountyGeoId",OrderMaxUtility.getValidEntityString(this.paCountyGeoId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
val.set("paGeoPointId",OrderMaxUtility.getValidEntityString(this.paGeoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("paStateProvinceGeoId",this.paStateProvinceGeoId);
valueMap.put("tnAreaCode",this.tnAreaCode);
valueMap.put("tnContactMechId",this.tnContactMechId);
valueMap.put("relationshipName",this.relationshipName);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("roleTypeIdTo",this.roleTypeIdTo);
valueMap.put("partyStatusId",this.partyStatusId);
valueMap.put("description",this.description);
valueMap.put("priorityTypeId",this.priorityTypeId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("infoString",this.infoString);
valueMap.put("paAddress2",this.paAddress2);
valueMap.put("middleName",this.middleName);
valueMap.put("paAddress1",this.paAddress1);
valueMap.put("lastName",this.lastName);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("firstNameLocal",this.firstNameLocal);
valueMap.put("paAttnName",this.paAttnName);
valueMap.put("paPostalCodeExt",this.paPostalCodeExt);
valueMap.put("securityGroupId",this.securityGroupId);
valueMap.put("paDirections",this.paDirections);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("paCountryGeoId",this.paCountryGeoId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("tnContactNumber",this.tnContactNumber);
valueMap.put("groupName",this.groupName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("tnAskForName",this.tnAskForName);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("partyRelationshipTypeId",this.partyRelationshipTypeId);
valueMap.put("tnCountryCode",this.tnCountryCode);
valueMap.put("paContactMechId",this.paContactMechId);
valueMap.put("externalId",this.externalId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("statusId",this.statusId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("lastNameLocal",this.lastNameLocal);
valueMap.put("paPostalCode",this.paPostalCode);
valueMap.put("firstName",this.firstName);
valueMap.put("paPostalCodeGeoId",this.paPostalCodeGeoId);
valueMap.put("permissionsEnumId",this.permissionsEnumId);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("roleTypeIdFrom",this.roleTypeIdFrom);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("suffix",this.suffix);
valueMap.put("paCity",this.paCity);
valueMap.put("positionTitle",this.positionTitle);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("paToName",this.paToName);
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
 genVal = delegator.makeValue("PartyRelationshipAndContactMechDetail");
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
private java.lang.String tnAreaCode;
public java.lang.String gettnAreaCode() {
return tnAreaCode;
}
public void settnAreaCode( java.lang.String tnAreaCode ) {
this.tnAreaCode = tnAreaCode;
}
private java.lang.String tnContactMechId;
public java.lang.String gettnContactMechId() {
return tnContactMechId;
}
public void settnContactMechId( java.lang.String tnContactMechId ) {
this.tnContactMechId = tnContactMechId;
}
private java.lang.String relationshipName;
public java.lang.String getrelationshipName() {
return relationshipName;
}
public void setrelationshipName( java.lang.String relationshipName ) {
this.relationshipName = relationshipName;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String roleTypeIdTo;
public java.lang.String getroleTypeIdTo() {
return roleTypeIdTo;
}
public void setroleTypeIdTo( java.lang.String roleTypeIdTo ) {
this.roleTypeIdTo = roleTypeIdTo;
}
private java.lang.String partyStatusId;
public java.lang.String getpartyStatusId() {
return partyStatusId;
}
public void setpartyStatusId( java.lang.String partyStatusId ) {
this.partyStatusId = partyStatusId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String priorityTypeId;
public java.lang.String getpriorityTypeId() {
return priorityTypeId;
}
public void setpriorityTypeId( java.lang.String priorityTypeId ) {
this.priorityTypeId = priorityTypeId;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
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
private java.lang.String middleName;
public java.lang.String getmiddleName() {
return middleName;
}
public void setmiddleName( java.lang.String middleName ) {
this.middleName = middleName;
}
private java.lang.String paAddress1;
public java.lang.String getpaAddress1() {
return paAddress1;
}
public void setpaAddress1( java.lang.String paAddress1 ) {
this.paAddress1 = paAddress1;
}
private java.lang.String lastName;
public java.lang.String getlastName() {
return lastName;
}
public void setlastName( java.lang.String lastName ) {
this.lastName = lastName;
}
private java.lang.String partyIdTo;
public java.lang.String getpartyIdTo() {
return partyIdTo;
}
public void setpartyIdTo( java.lang.String partyIdTo ) {
this.partyIdTo = partyIdTo;
}
private java.lang.String firstNameLocal;
public java.lang.String getfirstNameLocal() {
return firstNameLocal;
}
public void setfirstNameLocal( java.lang.String firstNameLocal ) {
this.firstNameLocal = firstNameLocal;
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
private java.lang.String securityGroupId;
public java.lang.String getsecurityGroupId() {
return securityGroupId;
}
public void setsecurityGroupId( java.lang.String securityGroupId ) {
this.securityGroupId = securityGroupId;
}
private java.lang.String paDirections;
public java.lang.String getpaDirections() {
return paDirections;
}
public void setpaDirections( java.lang.String paDirections ) {
this.paDirections = paDirections;
}
private java.lang.String personalTitle;
public java.lang.String getpersonalTitle() {
return personalTitle;
}
public void setpersonalTitle( java.lang.String personalTitle ) {
this.personalTitle = personalTitle;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
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
private java.lang.String tnContactNumber;
public java.lang.String gettnContactNumber() {
return tnContactNumber;
}
public void settnContactNumber( java.lang.String tnContactNumber ) {
this.tnContactNumber = tnContactNumber;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String tnAskForName;
public java.lang.String gettnAskForName() {
return tnAskForName;
}
public void settnAskForName( java.lang.String tnAskForName ) {
this.tnAskForName = tnAskForName;
}
private java.lang.String preferredCurrencyUomId;
public java.lang.String getpreferredCurrencyUomId() {
return preferredCurrencyUomId;
}
public void setpreferredCurrencyUomId( java.lang.String preferredCurrencyUomId ) {
this.preferredCurrencyUomId = preferredCurrencyUomId;
}
private java.lang.String partyRelationshipTypeId;
public java.lang.String getpartyRelationshipTypeId() {
return partyRelationshipTypeId;
}
public void setpartyRelationshipTypeId( java.lang.String partyRelationshipTypeId ) {
this.partyRelationshipTypeId = partyRelationshipTypeId;
}
private java.lang.String tnCountryCode;
public java.lang.String gettnCountryCode() {
return tnCountryCode;
}
public void settnCountryCode( java.lang.String tnCountryCode ) {
this.tnCountryCode = tnCountryCode;
}
private java.lang.String paContactMechId;
public java.lang.String getpaContactMechId() {
return paContactMechId;
}
public void setpaContactMechId( java.lang.String paContactMechId ) {
this.paContactMechId = paContactMechId;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
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
private java.lang.String groupNameLocal;
public java.lang.String getgroupNameLocal() {
return groupNameLocal;
}
public void setgroupNameLocal( java.lang.String groupNameLocal ) {
this.groupNameLocal = groupNameLocal;
}
private java.lang.String lastNameLocal;
public java.lang.String getlastNameLocal() {
return lastNameLocal;
}
public void setlastNameLocal( java.lang.String lastNameLocal ) {
this.lastNameLocal = lastNameLocal;
}
private java.lang.String paPostalCode;
public java.lang.String getpaPostalCode() {
return paPostalCode;
}
public void setpaPostalCode( java.lang.String paPostalCode ) {
this.paPostalCode = paPostalCode;
}
private java.lang.String firstName;
public java.lang.String getfirstName() {
return firstName;
}
public void setfirstName( java.lang.String firstName ) {
this.firstName = firstName;
}
private java.lang.String paPostalCodeGeoId;
public java.lang.String getpaPostalCodeGeoId() {
return paPostalCodeGeoId;
}
public void setpaPostalCodeGeoId( java.lang.String paPostalCodeGeoId ) {
this.paPostalCodeGeoId = paPostalCodeGeoId;
}
private java.lang.String permissionsEnumId;
public java.lang.String getpermissionsEnumId() {
return permissionsEnumId;
}
public void setpermissionsEnumId( java.lang.String permissionsEnumId ) {
this.permissionsEnumId = permissionsEnumId;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String roleTypeIdFrom;
public java.lang.String getroleTypeIdFrom() {
return roleTypeIdFrom;
}
public void setroleTypeIdFrom( java.lang.String roleTypeIdFrom ) {
this.roleTypeIdFrom = roleTypeIdFrom;
}
private java.lang.String contactMechTypeId;
public java.lang.String getcontactMechTypeId() {
return contactMechTypeId;
}
public void setcontactMechTypeId( java.lang.String contactMechTypeId ) {
this.contactMechTypeId = contactMechTypeId;
}
private java.lang.String suffix;
public java.lang.String getsuffix() {
return suffix;
}
public void setsuffix( java.lang.String suffix ) {
this.suffix = suffix;
}
private java.lang.String paCity;
public java.lang.String getpaCity() {
return paCity;
}
public void setpaCity( java.lang.String paCity ) {
this.paCity = paCity;
}
private java.lang.String positionTitle;
public java.lang.String getpositionTitle() {
return positionTitle;
}
public void setpositionTitle( java.lang.String positionTitle ) {
this.positionTitle = positionTitle;
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
        Collection<PartyRelationshipAndContactMechDetail> objectList = new ArrayList<PartyRelationshipAndContactMechDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyRelationshipAndContactMechDetail(genVal));
        }
        return objectList;
    }    
}
