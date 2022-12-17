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
public class PartyRoleAndContactMechDetail implements GenericValueObjectInterface {
public static final String module =PartyRoleAndContactMechDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyRoleAndContactMechDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyRoleAndContactMechDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"paStateProvinceGeoId","Pa State Province Geo Id"},
{"hasTable","Has Table"},
{"tnAreaCode","Tn Area Code"},
{"allowSolicitation","Allow Solicitation"},
{"tnContactMechId","Tn Contact Mech Id"},
{"monthsWithEmployer","Months With Employer"},
{"residenceStatusEnumId","Residence Status Enum Id"},
{"mothersMaidenName","Mothers Maiden Name"},
{"dataSourceId","Data Source Id"},
{"personComments","Person Comments"},
{"height","Height"},
{"yearsWithEmployer","Years With Employer"},
{"officeSiteName","Office Site Name"},
{"description","Description"},
{"verified","Verified"},
{"middleNameLocal","Middle Name Local"},
{"gender","Gender"},
{"birthDate","Birth Date"},
{"createdDate","Created Date"},
{"infoString","Info String"},
{"middleName","Middle Name"},
{"paAddress2","Pa Address 2"},
{"lastName","Last Name"},
{"paAddress1","Pa Address 1"},
{"occupation","Occupation"},
{"nickname","Nickname"},
{"existingCustomer","Existing Customer"},
{"firstNameLocal","First Name Local"},
{"socialSecurityNumber","Social Security Number"},
{"paAttnName","Pa Attn Name"},
{"paPostalCodeExt","Pa Postal Code Ext"},
{"partyTypeId","Party Type Id"},
{"personalTitle","Personal Title"},
{"paDirections","Pa Directions"},
{"tickerSymbol","Ticker Symbol"},
{"paCountryGeoId","Pa Country Geo Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"memberId","Member Id"},
{"deceasedDate","Deceased Date"},
{"tnContactNumber","Tn Contact Number"},
{"numEmployees","Num Employees"},
{"groupName","Group Name"},
{"weight","Weight"},
{"thruDate","Thru Date"},
{"otherLocal","Other Local"},
{"yearsWithContactMech","Years With Contact Mech"},
{"passportExpireDate","Passport Expire Date"},
{"tnAskForName","Tn Ask For Name"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"passportNumber","Passport Number"},
{"maritalStatus","Marital Status"},
{"tnCountryCode","Tn Country Code"},
{"externalId","External Id"},
{"paContactMechId","Pa Contact Mech Id"},
{"employmentStatusEnumId","Employment Status Enum Id"},
{"isUnread","Is Unread"},
{"logoImageUrl","Logo Image Url"},
{"statusId","Status Id"},
{"groupNameLocal","Group Name Local"},
{"partyGroupComments","Party Group Comments"},
{"lastNameLocal","Last Name Local"},
{"parentTypeId","Parent Type Id"},
{"paPostalCode","Pa Postal Code"},
{"totalYearsWorkExperience","Total Years Work Experience"},
{"firstName","First Name"},
{"paPostalCodeGeoId","Pa Postal Code Geo Id"},
{"cardId","Card Id"},
{"contactMechId","Contact Mech Id"},
{"contactMechTypeId","Contact Mech Type Id"},
{"suffix","Suffix"},
{"paCity","Pa City"},
{"extension","Extension"},
{"roleTypeId","Role Type Id"},
{"annualRevenue","Annual Revenue"},
{"monthsWithContactMech","Months With Contact Mech"},
{"lastModifiedDate","Last Modified Date"},
{"paToName","Pa To Name"},
{"salutation","Salutation"},
{"paCountyGeoId","Pa County Geo Id"},
{"comments","Comments"},
{"createdByUserLogin","Created By User Login"},
{"paGeoPointId","Pa Geo Point Id"},
};
protected void initObject(){
this.paStateProvinceGeoId = "";
this.hasTable = "";
this.tnAreaCode = "";
this.allowSolicitation = "";
this.tnContactMechId = "";
this.monthsWithEmployer = "";
this.residenceStatusEnumId = "";
this.mothersMaidenName = "";
this.dataSourceId = "";
this.personComments = "";
this.height = "";
this.yearsWithEmployer = "";
this.officeSiteName = "";
this.description = "";
this.verified = "";
this.middleNameLocal = "";
this.gender = "";
this.birthDate = UtilDateTime.nowTimestamp();
this.createdDate = UtilDateTime.nowTimestamp();
this.infoString = "";
this.middleName = "";
this.paAddress2 = "";
this.lastName = "";
this.paAddress1 = "";
this.occupation = "";
this.nickname = "";
this.existingCustomer = "";
this.firstNameLocal = "";
this.socialSecurityNumber = "";
this.paAttnName = "";
this.paPostalCodeExt = "";
this.partyTypeId = "";
this.personalTitle = "";
this.paDirections = "";
this.tickerSymbol = "";
this.paCountryGeoId = "";
this.lastModifiedByUserLogin = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.memberId = "";
this.deceasedDate = UtilDateTime.nowTimestamp();
this.tnContactNumber = "";
this.numEmployees = "";
this.groupName = "";
this.weight = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.otherLocal = "";
this.yearsWithContactMech = "";
this.passportExpireDate = UtilDateTime.nowTimestamp();
this.tnAskForName = "";
this.preferredCurrencyUomId = "";
this.passportNumber = "";
this.maritalStatus = "";
this.tnCountryCode = "";
this.externalId = "";
this.paContactMechId = "";
this.employmentStatusEnumId = "";
this.isUnread = "";
this.logoImageUrl = "";
this.statusId = "";
this.groupNameLocal = "";
this.partyGroupComments = "";
this.lastNameLocal = "";
this.parentTypeId = "";
this.paPostalCode = "";
this.totalYearsWorkExperience = "";
this.firstName = "";
this.paPostalCodeGeoId = "";
this.cardId = "";
this.contactMechId = "";
this.contactMechTypeId = "";
this.suffix = "";
this.paCity = "";
this.extension = "";
this.roleTypeId = "";
this.annualRevenue = "";
this.monthsWithContactMech = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.paToName = "";
this.salutation = "";
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
this.hasTable = (java.lang.String) genVal.get("hasTable");
this.tnAreaCode = (java.lang.String) genVal.get("tnAreaCode");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.tnContactMechId = (java.lang.String) genVal.get("tnContactMechId");
this.monthsWithEmployer = (java.lang.String) genVal.get("monthsWithEmployer");
this.residenceStatusEnumId = (java.lang.String) genVal.get("residenceStatusEnumId");
this.mothersMaidenName = (java.lang.String) genVal.get("mothersMaidenName");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.personComments = (java.lang.String) genVal.get("personComments");
this.height = (java.lang.String) genVal.get("height");
this.yearsWithEmployer = (java.lang.String) genVal.get("yearsWithEmployer");
this.officeSiteName = (java.lang.String) genVal.get("officeSiteName");
this.description = (java.lang.String) genVal.get("description");
this.verified = (java.lang.String) genVal.get("verified");
this.middleNameLocal = (java.lang.String) genVal.get("middleNameLocal");
this.gender = (java.lang.String) genVal.get("gender");
this.birthDate = (java.sql.Timestamp) genVal.get("birthDate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.infoString = (java.lang.String) genVal.get("infoString");
this.middleName = (java.lang.String) genVal.get("middleName");
this.paAddress2 = (java.lang.String) genVal.get("paAddress2");
this.lastName = (java.lang.String) genVal.get("lastName");
this.paAddress1 = (java.lang.String) genVal.get("paAddress1");
this.occupation = (java.lang.String) genVal.get("occupation");
this.nickname = (java.lang.String) genVal.get("nickname");
this.existingCustomer = (java.lang.String) genVal.get("existingCustomer");
this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
this.socialSecurityNumber = (java.lang.String) genVal.get("socialSecurityNumber");
this.paAttnName = (java.lang.String) genVal.get("paAttnName");
this.paPostalCodeExt = (java.lang.String) genVal.get("paPostalCodeExt");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.paDirections = (java.lang.String) genVal.get("paDirections");
this.tickerSymbol = (java.lang.String) genVal.get("tickerSymbol");
this.paCountryGeoId = (java.lang.String) genVal.get("paCountryGeoId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.memberId = (java.lang.String) genVal.get("memberId");
this.deceasedDate = (java.sql.Timestamp) genVal.get("deceasedDate");
this.tnContactNumber = (java.lang.String) genVal.get("tnContactNumber");
this.numEmployees = (java.lang.String) genVal.get("numEmployees");
this.groupName = (java.lang.String) genVal.get("groupName");
this.weight = (java.lang.String) genVal.get("weight");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.otherLocal = (java.lang.String) genVal.get("otherLocal");
this.yearsWithContactMech = (java.lang.String) genVal.get("yearsWithContactMech");
this.passportExpireDate = (java.sql.Timestamp) genVal.get("passportExpireDate");
this.tnAskForName = (java.lang.String) genVal.get("tnAskForName");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.passportNumber = (java.lang.String) genVal.get("passportNumber");
this.maritalStatus = (java.lang.String) genVal.get("maritalStatus");
this.tnCountryCode = (java.lang.String) genVal.get("tnCountryCode");
this.externalId = (java.lang.String) genVal.get("externalId");
this.paContactMechId = (java.lang.String) genVal.get("paContactMechId");
this.employmentStatusEnumId = (java.lang.String) genVal.get("employmentStatusEnumId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.logoImageUrl = (java.lang.String) genVal.get("logoImageUrl");
this.statusId = (java.lang.String) genVal.get("statusId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.partyGroupComments = (java.lang.String) genVal.get("partyGroupComments");
this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
this.parentTypeId = (java.lang.String) genVal.get("parentTypeId");
this.paPostalCode = (java.lang.String) genVal.get("paPostalCode");
this.totalYearsWorkExperience = (java.lang.String) genVal.get("totalYearsWorkExperience");
this.firstName = (java.lang.String) genVal.get("firstName");
this.paPostalCodeGeoId = (java.lang.String) genVal.get("paPostalCodeGeoId");
this.cardId = (java.lang.String) genVal.get("cardId");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.suffix = (java.lang.String) genVal.get("suffix");
this.paCity = (java.lang.String) genVal.get("paCity");
this.extension = (java.lang.String) genVal.get("extension");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.annualRevenue = (java.lang.String) genVal.get("annualRevenue");
this.monthsWithContactMech = (java.lang.String) genVal.get("monthsWithContactMech");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.paToName = (java.lang.String) genVal.get("paToName");
this.salutation = (java.lang.String) genVal.get("salutation");
this.paCountyGeoId = (java.lang.String) genVal.get("paCountyGeoId");
this.comments = (java.lang.String) genVal.get("comments");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
this.paGeoPointId = (java.lang.String) genVal.get("paGeoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("paStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.paStateProvinceGeoId));
val.set("hasTable",OrderMaxUtility.getValidEntityString(this.hasTable));
val.set("tnAreaCode",OrderMaxUtility.getValidEntityString(this.tnAreaCode));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("tnContactMechId",OrderMaxUtility.getValidEntityString(this.tnContactMechId));
val.set("monthsWithEmployer",OrderMaxUtility.getValidEntityString(this.monthsWithEmployer));
val.set("residenceStatusEnumId",OrderMaxUtility.getValidEntityString(this.residenceStatusEnumId));
val.set("mothersMaidenName",OrderMaxUtility.getValidEntityString(this.mothersMaidenName));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("personComments",OrderMaxUtility.getValidEntityString(this.personComments));
val.set("height",OrderMaxUtility.getValidEntityString(this.height));
val.set("yearsWithEmployer",OrderMaxUtility.getValidEntityString(this.yearsWithEmployer));
val.set("officeSiteName",OrderMaxUtility.getValidEntityString(this.officeSiteName));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("verified",OrderMaxUtility.getValidEntityString(this.verified));
val.set("middleNameLocal",OrderMaxUtility.getValidEntityString(this.middleNameLocal));
val.set("gender",OrderMaxUtility.getValidEntityString(this.gender));
val.set("birthDate",OrderMaxUtility.getValidEntityTimestamp(this.birthDate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("paAddress2",OrderMaxUtility.getValidEntityString(this.paAddress2));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("paAddress1",OrderMaxUtility.getValidEntityString(this.paAddress1));
val.set("occupation",OrderMaxUtility.getValidEntityString(this.occupation));
val.set("nickname",OrderMaxUtility.getValidEntityString(this.nickname));
val.set("existingCustomer",OrderMaxUtility.getValidEntityString(this.existingCustomer));
val.set("firstNameLocal",OrderMaxUtility.getValidEntityString(this.firstNameLocal));
val.set("socialSecurityNumber",OrderMaxUtility.getValidEntityString(this.socialSecurityNumber));
val.set("paAttnName",OrderMaxUtility.getValidEntityString(this.paAttnName));
val.set("paPostalCodeExt",OrderMaxUtility.getValidEntityString(this.paPostalCodeExt));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("paDirections",OrderMaxUtility.getValidEntityString(this.paDirections));
val.set("tickerSymbol",OrderMaxUtility.getValidEntityString(this.tickerSymbol));
val.set("paCountryGeoId",OrderMaxUtility.getValidEntityString(this.paCountryGeoId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("memberId",OrderMaxUtility.getValidEntityString(this.memberId));
val.set("deceasedDate",OrderMaxUtility.getValidEntityTimestamp(this.deceasedDate));
val.set("tnContactNumber",OrderMaxUtility.getValidEntityString(this.tnContactNumber));
val.set("numEmployees",OrderMaxUtility.getValidEntityString(this.numEmployees));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("otherLocal",OrderMaxUtility.getValidEntityString(this.otherLocal));
val.set("yearsWithContactMech",OrderMaxUtility.getValidEntityString(this.yearsWithContactMech));
val.set("passportExpireDate",OrderMaxUtility.getValidEntityTimestamp(this.passportExpireDate));
val.set("tnAskForName",OrderMaxUtility.getValidEntityString(this.tnAskForName));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("passportNumber",OrderMaxUtility.getValidEntityString(this.passportNumber));
val.set("maritalStatus",OrderMaxUtility.getValidEntityString(this.maritalStatus));
val.set("tnCountryCode",OrderMaxUtility.getValidEntityString(this.tnCountryCode));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("paContactMechId",OrderMaxUtility.getValidEntityString(this.paContactMechId));
val.set("employmentStatusEnumId",OrderMaxUtility.getValidEntityString(this.employmentStatusEnumId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("logoImageUrl",OrderMaxUtility.getValidEntityString(this.logoImageUrl));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("partyGroupComments",OrderMaxUtility.getValidEntityString(this.partyGroupComments));
val.set("lastNameLocal",OrderMaxUtility.getValidEntityString(this.lastNameLocal));
val.set("parentTypeId",OrderMaxUtility.getValidEntityString(this.parentTypeId));
val.set("paPostalCode",OrderMaxUtility.getValidEntityString(this.paPostalCode));
val.set("totalYearsWorkExperience",OrderMaxUtility.getValidEntityString(this.totalYearsWorkExperience));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("paPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.paPostalCodeGeoId));
val.set("cardId",OrderMaxUtility.getValidEntityString(this.cardId));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("paCity",OrderMaxUtility.getValidEntityString(this.paCity));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("annualRevenue",OrderMaxUtility.getValidEntityString(this.annualRevenue));
val.set("monthsWithContactMech",OrderMaxUtility.getValidEntityString(this.monthsWithContactMech));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("paToName",OrderMaxUtility.getValidEntityString(this.paToName));
val.set("salutation",OrderMaxUtility.getValidEntityString(this.salutation));
val.set("paCountyGeoId",OrderMaxUtility.getValidEntityString(this.paCountyGeoId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
val.set("paGeoPointId",OrderMaxUtility.getValidEntityString(this.paGeoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("paStateProvinceGeoId",this.paStateProvinceGeoId);
valueMap.put("hasTable",this.hasTable);
valueMap.put("tnAreaCode",this.tnAreaCode);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("tnContactMechId",this.tnContactMechId);
valueMap.put("monthsWithEmployer",this.monthsWithEmployer);
valueMap.put("residenceStatusEnumId",this.residenceStatusEnumId);
valueMap.put("mothersMaidenName",this.mothersMaidenName);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("personComments",this.personComments);
valueMap.put("height",this.height);
valueMap.put("yearsWithEmployer",this.yearsWithEmployer);
valueMap.put("officeSiteName",this.officeSiteName);
valueMap.put("description",this.description);
valueMap.put("verified",this.verified);
valueMap.put("middleNameLocal",this.middleNameLocal);
valueMap.put("gender",this.gender);
valueMap.put("birthDate",this.birthDate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("infoString",this.infoString);
valueMap.put("middleName",this.middleName);
valueMap.put("paAddress2",this.paAddress2);
valueMap.put("lastName",this.lastName);
valueMap.put("paAddress1",this.paAddress1);
valueMap.put("occupation",this.occupation);
valueMap.put("nickname",this.nickname);
valueMap.put("existingCustomer",this.existingCustomer);
valueMap.put("firstNameLocal",this.firstNameLocal);
valueMap.put("socialSecurityNumber",this.socialSecurityNumber);
valueMap.put("paAttnName",this.paAttnName);
valueMap.put("paPostalCodeExt",this.paPostalCodeExt);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("paDirections",this.paDirections);
valueMap.put("tickerSymbol",this.tickerSymbol);
valueMap.put("paCountryGeoId",this.paCountryGeoId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("memberId",this.memberId);
valueMap.put("deceasedDate",this.deceasedDate);
valueMap.put("tnContactNumber",this.tnContactNumber);
valueMap.put("numEmployees",this.numEmployees);
valueMap.put("groupName",this.groupName);
valueMap.put("weight",this.weight);
valueMap.put("thruDate",this.thruDate);
valueMap.put("otherLocal",this.otherLocal);
valueMap.put("yearsWithContactMech",this.yearsWithContactMech);
valueMap.put("passportExpireDate",this.passportExpireDate);
valueMap.put("tnAskForName",this.tnAskForName);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("passportNumber",this.passportNumber);
valueMap.put("maritalStatus",this.maritalStatus);
valueMap.put("tnCountryCode",this.tnCountryCode);
valueMap.put("externalId",this.externalId);
valueMap.put("paContactMechId",this.paContactMechId);
valueMap.put("employmentStatusEnumId",this.employmentStatusEnumId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("logoImageUrl",this.logoImageUrl);
valueMap.put("statusId",this.statusId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("partyGroupComments",this.partyGroupComments);
valueMap.put("lastNameLocal",this.lastNameLocal);
valueMap.put("parentTypeId",this.parentTypeId);
valueMap.put("paPostalCode",this.paPostalCode);
valueMap.put("totalYearsWorkExperience",this.totalYearsWorkExperience);
valueMap.put("firstName",this.firstName);
valueMap.put("paPostalCodeGeoId",this.paPostalCodeGeoId);
valueMap.put("cardId",this.cardId);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("suffix",this.suffix);
valueMap.put("paCity",this.paCity);
valueMap.put("extension",this.extension);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("annualRevenue",this.annualRevenue);
valueMap.put("monthsWithContactMech",this.monthsWithContactMech);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("paToName",this.paToName);
valueMap.put("salutation",this.salutation);
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
 genVal = delegator.makeValue("PartyRoleAndContactMechDetail");
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
private java.lang.String hasTable;
public java.lang.String gethasTable() {
return hasTable;
}
public void sethasTable( java.lang.String hasTable ) {
this.hasTable = hasTable;
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
private java.lang.String tnContactMechId;
public java.lang.String gettnContactMechId() {
return tnContactMechId;
}
public void settnContactMechId( java.lang.String tnContactMechId ) {
this.tnContactMechId = tnContactMechId;
}
private java.lang.String monthsWithEmployer;
public java.lang.String getmonthsWithEmployer() {
return monthsWithEmployer;
}
public void setmonthsWithEmployer( java.lang.String monthsWithEmployer ) {
this.monthsWithEmployer = monthsWithEmployer;
}
private java.lang.String residenceStatusEnumId;
public java.lang.String getresidenceStatusEnumId() {
return residenceStatusEnumId;
}
public void setresidenceStatusEnumId( java.lang.String residenceStatusEnumId ) {
this.residenceStatusEnumId = residenceStatusEnumId;
}
private java.lang.String mothersMaidenName;
public java.lang.String getmothersMaidenName() {
return mothersMaidenName;
}
public void setmothersMaidenName( java.lang.String mothersMaidenName ) {
this.mothersMaidenName = mothersMaidenName;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String personComments;
public java.lang.String getpersonComments() {
return personComments;
}
public void setpersonComments( java.lang.String personComments ) {
this.personComments = personComments;
}
private java.lang.String height;
public java.lang.String getheight() {
return height;
}
public void setheight( java.lang.String height ) {
this.height = height;
}
private java.lang.String yearsWithEmployer;
public java.lang.String getyearsWithEmployer() {
return yearsWithEmployer;
}
public void setyearsWithEmployer( java.lang.String yearsWithEmployer ) {
this.yearsWithEmployer = yearsWithEmployer;
}
private java.lang.String officeSiteName;
public java.lang.String getofficeSiteName() {
return officeSiteName;
}
public void setofficeSiteName( java.lang.String officeSiteName ) {
this.officeSiteName = officeSiteName;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String verified;
public java.lang.String getverified() {
return verified;
}
public void setverified( java.lang.String verified ) {
this.verified = verified;
}
private java.lang.String middleNameLocal;
public java.lang.String getmiddleNameLocal() {
return middleNameLocal;
}
public void setmiddleNameLocal( java.lang.String middleNameLocal ) {
this.middleNameLocal = middleNameLocal;
}
private java.lang.String gender;
public java.lang.String getgender() {
return gender;
}
public void setgender( java.lang.String gender ) {
this.gender = gender;
}
private java.sql.Timestamp birthDate;
public java.sql.Timestamp getbirthDate() {
return birthDate;
}
public void setbirthDate( java.sql.Timestamp birthDate ) {
this.birthDate = birthDate;
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
private java.lang.String middleName;
public java.lang.String getmiddleName() {
return middleName;
}
public void setmiddleName( java.lang.String middleName ) {
this.middleName = middleName;
}
private java.lang.String paAddress2;
public java.lang.String getpaAddress2() {
return paAddress2;
}
public void setpaAddress2( java.lang.String paAddress2 ) {
this.paAddress2 = paAddress2;
}
private java.lang.String lastName;
public java.lang.String getlastName() {
return lastName;
}
public void setlastName( java.lang.String lastName ) {
this.lastName = lastName;
}
private java.lang.String paAddress1;
public java.lang.String getpaAddress1() {
return paAddress1;
}
public void setpaAddress1( java.lang.String paAddress1 ) {
this.paAddress1 = paAddress1;
}
private java.lang.String occupation;
public java.lang.String getoccupation() {
return occupation;
}
public void setoccupation( java.lang.String occupation ) {
this.occupation = occupation;
}
private java.lang.String nickname;
public java.lang.String getnickname() {
return nickname;
}
public void setnickname( java.lang.String nickname ) {
this.nickname = nickname;
}
private java.lang.String existingCustomer;
public java.lang.String getexistingCustomer() {
return existingCustomer;
}
public void setexistingCustomer( java.lang.String existingCustomer ) {
this.existingCustomer = existingCustomer;
}
private java.lang.String firstNameLocal;
public java.lang.String getfirstNameLocal() {
return firstNameLocal;
}
public void setfirstNameLocal( java.lang.String firstNameLocal ) {
this.firstNameLocal = firstNameLocal;
}
private java.lang.String socialSecurityNumber;
public java.lang.String getsocialSecurityNumber() {
return socialSecurityNumber;
}
public void setsocialSecurityNumber( java.lang.String socialSecurityNumber ) {
this.socialSecurityNumber = socialSecurityNumber;
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
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String personalTitle;
public java.lang.String getpersonalTitle() {
return personalTitle;
}
public void setpersonalTitle( java.lang.String personalTitle ) {
this.personalTitle = personalTitle;
}
private java.lang.String paDirections;
public java.lang.String getpaDirections() {
return paDirections;
}
public void setpaDirections( java.lang.String paDirections ) {
this.paDirections = paDirections;
}
private java.lang.String tickerSymbol;
public java.lang.String gettickerSymbol() {
return tickerSymbol;
}
public void settickerSymbol( java.lang.String tickerSymbol ) {
this.tickerSymbol = tickerSymbol;
}
private java.lang.String paCountryGeoId;
public java.lang.String getpaCountryGeoId() {
return paCountryGeoId;
}
public void setpaCountryGeoId( java.lang.String paCountryGeoId ) {
this.paCountryGeoId = paCountryGeoId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String memberId;
public java.lang.String getmemberId() {
return memberId;
}
public void setmemberId( java.lang.String memberId ) {
this.memberId = memberId;
}
private java.sql.Timestamp deceasedDate;
public java.sql.Timestamp getdeceasedDate() {
return deceasedDate;
}
public void setdeceasedDate( java.sql.Timestamp deceasedDate ) {
this.deceasedDate = deceasedDate;
}
private java.lang.String tnContactNumber;
public java.lang.String gettnContactNumber() {
return tnContactNumber;
}
public void settnContactNumber( java.lang.String tnContactNumber ) {
this.tnContactNumber = tnContactNumber;
}
private java.lang.String numEmployees;
public java.lang.String getnumEmployees() {
return numEmployees;
}
public void setnumEmployees( java.lang.String numEmployees ) {
this.numEmployees = numEmployees;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.lang.String weight;
public java.lang.String getweight() {
return weight;
}
public void setweight( java.lang.String weight ) {
this.weight = weight;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String otherLocal;
public java.lang.String getotherLocal() {
return otherLocal;
}
public void setotherLocal( java.lang.String otherLocal ) {
this.otherLocal = otherLocal;
}
private java.lang.String yearsWithContactMech;
public java.lang.String getyearsWithContactMech() {
return yearsWithContactMech;
}
public void setyearsWithContactMech( java.lang.String yearsWithContactMech ) {
this.yearsWithContactMech = yearsWithContactMech;
}
private java.sql.Timestamp passportExpireDate;
public java.sql.Timestamp getpassportExpireDate() {
return passportExpireDate;
}
public void setpassportExpireDate( java.sql.Timestamp passportExpireDate ) {
this.passportExpireDate = passportExpireDate;
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
private java.lang.String passportNumber;
public java.lang.String getpassportNumber() {
return passportNumber;
}
public void setpassportNumber( java.lang.String passportNumber ) {
this.passportNumber = passportNumber;
}
private java.lang.String maritalStatus;
public java.lang.String getmaritalStatus() {
return maritalStatus;
}
public void setmaritalStatus( java.lang.String maritalStatus ) {
this.maritalStatus = maritalStatus;
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
private java.lang.String employmentStatusEnumId;
public java.lang.String getemploymentStatusEnumId() {
return employmentStatusEnumId;
}
public void setemploymentStatusEnumId( java.lang.String employmentStatusEnumId ) {
this.employmentStatusEnumId = employmentStatusEnumId;
}
private java.lang.String isUnread;
public java.lang.String getisUnread() {
return isUnread;
}
public void setisUnread( java.lang.String isUnread ) {
this.isUnread = isUnread;
}
private java.lang.String logoImageUrl;
public java.lang.String getlogoImageUrl() {
return logoImageUrl;
}
public void setlogoImageUrl( java.lang.String logoImageUrl ) {
this.logoImageUrl = logoImageUrl;
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
private java.lang.String partyGroupComments;
public java.lang.String getpartyGroupComments() {
return partyGroupComments;
}
public void setpartyGroupComments( java.lang.String partyGroupComments ) {
this.partyGroupComments = partyGroupComments;
}
private java.lang.String lastNameLocal;
public java.lang.String getlastNameLocal() {
return lastNameLocal;
}
public void setlastNameLocal( java.lang.String lastNameLocal ) {
this.lastNameLocal = lastNameLocal;
}
private java.lang.String parentTypeId;
public java.lang.String getparentTypeId() {
return parentTypeId;
}
public void setparentTypeId( java.lang.String parentTypeId ) {
this.parentTypeId = parentTypeId;
}
private java.lang.String paPostalCode;
public java.lang.String getpaPostalCode() {
return paPostalCode;
}
public void setpaPostalCode( java.lang.String paPostalCode ) {
this.paPostalCode = paPostalCode;
}
private java.lang.String totalYearsWorkExperience;
public java.lang.String gettotalYearsWorkExperience() {
return totalYearsWorkExperience;
}
public void settotalYearsWorkExperience( java.lang.String totalYearsWorkExperience ) {
this.totalYearsWorkExperience = totalYearsWorkExperience;
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
private java.lang.String cardId;
public java.lang.String getcardId() {
return cardId;
}
public void setcardId( java.lang.String cardId ) {
this.cardId = cardId;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
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
private java.lang.String annualRevenue;
public java.lang.String getannualRevenue() {
return annualRevenue;
}
public void setannualRevenue( java.lang.String annualRevenue ) {
this.annualRevenue = annualRevenue;
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
private java.lang.String salutation;
public java.lang.String getsalutation() {
return salutation;
}
public void setsalutation( java.lang.String salutation ) {
this.salutation = salutation;
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
        Collection<PartyRoleAndContactMechDetail> objectList = new ArrayList<PartyRoleAndContactMechDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyRoleAndContactMechDetail(genVal));
        }
        return objectList;
    }    
}
