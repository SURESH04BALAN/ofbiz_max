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
public class PartyRoleNameDetail implements GenericValueObjectInterface {
public static final String module =PartyRoleNameDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyRoleNameDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyRoleNameDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"monthsWithEmployer","Months With Employer"},
{"dataSourceId","Data Source Id"},
{"mothersMaidenName","Mothers Maiden Name"},
{"residenceStatusEnumId","Residence Status Enum Id"},
{"height","Height"},
{"yearsWithEmployer","Years With Employer"},
{"officeSiteName","Office Site Name"},
{"description","Description"},
{"middleNameLocal","Middle Name Local"},
{"gender","Gender"},
{"birthDate","Birth Date"},
{"createdDate","Created Date"},
{"middleName","Middle Name"},
{"lastName","Last Name"},
{"occupation","Occupation"},
{"nickname","Nickname"},
{"firstNameLocal","First Name Local"},
{"existingCustomer","Existing Customer"},
{"socialSecurityNumber","Social Security Number"},
{"personalTitle","Personal Title"},
{"partyTypeId","Party Type Id"},
{"tickerSymbol","Ticker Symbol"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"memberId","Member Id"},
{"deceasedDate","Deceased Date"},
{"numEmployees","Num Employees"},
{"groupName","Group Name"},
{"weight","Weight"},
{"otherLocal","Other Local"},
{"passportExpireDate","Passport Expire Date"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"passportNumber","Passport Number"},
{"maritalStatus","Marital Status"},
{"externalId","External Id"},
{"isUnread","Is Unread"},
{"employmentStatusEnumId","Employment Status Enum Id"},
{"logoImageUrl","Logo Image Url"},
{"statusId","Status Id"},
{"groupNameLocal","Group Name Local"},
{"lastNameLocal","Last Name Local"},
{"totalYearsWorkExperience","Total Years Work Experience"},
{"firstName","First Name"},
{"cardId","Card Id"},
{"suffix","Suffix"},
{"roleTypeId","Role Type Id"},
{"annualRevenue","Annual Revenue"},
{"lastModifiedDate","Last Modified Date"},
{"salutation","Salutation"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.monthsWithEmployer = "";
this.dataSourceId = "";
this.mothersMaidenName = "";
this.residenceStatusEnumId = "";
this.height = "";
this.yearsWithEmployer = "";
this.officeSiteName = "";
this.description = "";
this.middleNameLocal = "";
this.gender = "";
this.birthDate = UtilDateTime.nowTimestamp();
this.createdDate = UtilDateTime.nowTimestamp();
this.middleName = "";
this.lastName = "";
this.occupation = "";
this.nickname = "";
this.firstNameLocal = "";
this.existingCustomer = "";
this.socialSecurityNumber = "";
this.personalTitle = "";
this.partyTypeId = "";
this.tickerSymbol = "";
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.memberId = "";
this.deceasedDate = UtilDateTime.nowTimestamp();
this.numEmployees = "";
this.groupName = "";
this.weight = "";
this.otherLocal = "";
this.passportExpireDate = UtilDateTime.nowTimestamp();
this.preferredCurrencyUomId = "";
this.passportNumber = "";
this.maritalStatus = "";
this.externalId = "";
this.isUnread = "";
this.employmentStatusEnumId = "";
this.logoImageUrl = "";
this.statusId = "";
this.groupNameLocal = "";
this.lastNameLocal = "";
this.totalYearsWorkExperience = "";
this.firstName = "";
this.cardId = "";
this.suffix = "";
this.roleTypeId = "";
this.annualRevenue = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.salutation = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.monthsWithEmployer = (java.lang.String) genVal.get("monthsWithEmployer");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.mothersMaidenName = (java.lang.String) genVal.get("mothersMaidenName");
this.residenceStatusEnumId = (java.lang.String) genVal.get("residenceStatusEnumId");
this.height = (java.lang.String) genVal.get("height");
this.yearsWithEmployer = (java.lang.String) genVal.get("yearsWithEmployer");
this.officeSiteName = (java.lang.String) genVal.get("officeSiteName");
this.description = (java.lang.String) genVal.get("description");
this.middleNameLocal = (java.lang.String) genVal.get("middleNameLocal");
this.gender = (java.lang.String) genVal.get("gender");
this.birthDate = (java.sql.Timestamp) genVal.get("birthDate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.middleName = (java.lang.String) genVal.get("middleName");
this.lastName = (java.lang.String) genVal.get("lastName");
this.occupation = (java.lang.String) genVal.get("occupation");
this.nickname = (java.lang.String) genVal.get("nickname");
this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
this.existingCustomer = (java.lang.String) genVal.get("existingCustomer");
this.socialSecurityNumber = (java.lang.String) genVal.get("socialSecurityNumber");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.tickerSymbol = (java.lang.String) genVal.get("tickerSymbol");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.memberId = (java.lang.String) genVal.get("memberId");
this.deceasedDate = (java.sql.Timestamp) genVal.get("deceasedDate");
this.numEmployees = (java.lang.String) genVal.get("numEmployees");
this.groupName = (java.lang.String) genVal.get("groupName");
this.weight = (java.lang.String) genVal.get("weight");
this.otherLocal = (java.lang.String) genVal.get("otherLocal");
this.passportExpireDate = (java.sql.Timestamp) genVal.get("passportExpireDate");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.passportNumber = (java.lang.String) genVal.get("passportNumber");
this.maritalStatus = (java.lang.String) genVal.get("maritalStatus");
this.externalId = (java.lang.String) genVal.get("externalId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.employmentStatusEnumId = (java.lang.String) genVal.get("employmentStatusEnumId");
this.logoImageUrl = (java.lang.String) genVal.get("logoImageUrl");
this.statusId = (java.lang.String) genVal.get("statusId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
this.totalYearsWorkExperience = (java.lang.String) genVal.get("totalYearsWorkExperience");
this.firstName = (java.lang.String) genVal.get("firstName");
this.cardId = (java.lang.String) genVal.get("cardId");
this.suffix = (java.lang.String) genVal.get("suffix");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.annualRevenue = (java.lang.String) genVal.get("annualRevenue");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.salutation = (java.lang.String) genVal.get("salutation");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("monthsWithEmployer",OrderMaxUtility.getValidEntityString(this.monthsWithEmployer));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("mothersMaidenName",OrderMaxUtility.getValidEntityString(this.mothersMaidenName));
val.set("residenceStatusEnumId",OrderMaxUtility.getValidEntityString(this.residenceStatusEnumId));
val.set("height",OrderMaxUtility.getValidEntityString(this.height));
val.set("yearsWithEmployer",OrderMaxUtility.getValidEntityString(this.yearsWithEmployer));
val.set("officeSiteName",OrderMaxUtility.getValidEntityString(this.officeSiteName));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("middleNameLocal",OrderMaxUtility.getValidEntityString(this.middleNameLocal));
val.set("gender",OrderMaxUtility.getValidEntityString(this.gender));
val.set("birthDate",OrderMaxUtility.getValidEntityTimestamp(this.birthDate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("occupation",OrderMaxUtility.getValidEntityString(this.occupation));
val.set("nickname",OrderMaxUtility.getValidEntityString(this.nickname));
val.set("firstNameLocal",OrderMaxUtility.getValidEntityString(this.firstNameLocal));
val.set("existingCustomer",OrderMaxUtility.getValidEntityString(this.existingCustomer));
val.set("socialSecurityNumber",OrderMaxUtility.getValidEntityString(this.socialSecurityNumber));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("tickerSymbol",OrderMaxUtility.getValidEntityString(this.tickerSymbol));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("memberId",OrderMaxUtility.getValidEntityString(this.memberId));
val.set("deceasedDate",OrderMaxUtility.getValidEntityTimestamp(this.deceasedDate));
val.set("numEmployees",OrderMaxUtility.getValidEntityString(this.numEmployees));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("otherLocal",OrderMaxUtility.getValidEntityString(this.otherLocal));
val.set("passportExpireDate",OrderMaxUtility.getValidEntityTimestamp(this.passportExpireDate));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("passportNumber",OrderMaxUtility.getValidEntityString(this.passportNumber));
val.set("maritalStatus",OrderMaxUtility.getValidEntityString(this.maritalStatus));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("employmentStatusEnumId",OrderMaxUtility.getValidEntityString(this.employmentStatusEnumId));
val.set("logoImageUrl",OrderMaxUtility.getValidEntityString(this.logoImageUrl));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("lastNameLocal",OrderMaxUtility.getValidEntityString(this.lastNameLocal));
val.set("totalYearsWorkExperience",OrderMaxUtility.getValidEntityString(this.totalYearsWorkExperience));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("cardId",OrderMaxUtility.getValidEntityString(this.cardId));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("annualRevenue",OrderMaxUtility.getValidEntityString(this.annualRevenue));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("salutation",OrderMaxUtility.getValidEntityString(this.salutation));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("monthsWithEmployer",this.monthsWithEmployer);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("mothersMaidenName",this.mothersMaidenName);
valueMap.put("residenceStatusEnumId",this.residenceStatusEnumId);
valueMap.put("height",this.height);
valueMap.put("yearsWithEmployer",this.yearsWithEmployer);
valueMap.put("officeSiteName",this.officeSiteName);
valueMap.put("description",this.description);
valueMap.put("middleNameLocal",this.middleNameLocal);
valueMap.put("gender",this.gender);
valueMap.put("birthDate",this.birthDate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("middleName",this.middleName);
valueMap.put("lastName",this.lastName);
valueMap.put("occupation",this.occupation);
valueMap.put("nickname",this.nickname);
valueMap.put("firstNameLocal",this.firstNameLocal);
valueMap.put("existingCustomer",this.existingCustomer);
valueMap.put("socialSecurityNumber",this.socialSecurityNumber);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("tickerSymbol",this.tickerSymbol);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("memberId",this.memberId);
valueMap.put("deceasedDate",this.deceasedDate);
valueMap.put("numEmployees",this.numEmployees);
valueMap.put("groupName",this.groupName);
valueMap.put("weight",this.weight);
valueMap.put("otherLocal",this.otherLocal);
valueMap.put("passportExpireDate",this.passportExpireDate);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("passportNumber",this.passportNumber);
valueMap.put("maritalStatus",this.maritalStatus);
valueMap.put("externalId",this.externalId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("employmentStatusEnumId",this.employmentStatusEnumId);
valueMap.put("logoImageUrl",this.logoImageUrl);
valueMap.put("statusId",this.statusId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("lastNameLocal",this.lastNameLocal);
valueMap.put("totalYearsWorkExperience",this.totalYearsWorkExperience);
valueMap.put("firstName",this.firstName);
valueMap.put("cardId",this.cardId);
valueMap.put("suffix",this.suffix);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("annualRevenue",this.annualRevenue);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("salutation",this.salutation);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyRoleNameDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String monthsWithEmployer;
public java.lang.String getmonthsWithEmployer() {
return monthsWithEmployer;
}
public void setmonthsWithEmployer( java.lang.String monthsWithEmployer ) {
this.monthsWithEmployer = monthsWithEmployer;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String mothersMaidenName;
public java.lang.String getmothersMaidenName() {
return mothersMaidenName;
}
public void setmothersMaidenName( java.lang.String mothersMaidenName ) {
this.mothersMaidenName = mothersMaidenName;
}
private java.lang.String residenceStatusEnumId;
public java.lang.String getresidenceStatusEnumId() {
return residenceStatusEnumId;
}
public void setresidenceStatusEnumId( java.lang.String residenceStatusEnumId ) {
this.residenceStatusEnumId = residenceStatusEnumId;
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
private java.lang.String middleName;
public java.lang.String getmiddleName() {
return middleName;
}
public void setmiddleName( java.lang.String middleName ) {
this.middleName = middleName;
}
private java.lang.String lastName;
public java.lang.String getlastName() {
return lastName;
}
public void setlastName( java.lang.String lastName ) {
this.lastName = lastName;
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
private java.lang.String firstNameLocal;
public java.lang.String getfirstNameLocal() {
return firstNameLocal;
}
public void setfirstNameLocal( java.lang.String firstNameLocal ) {
this.firstNameLocal = firstNameLocal;
}
private java.lang.String existingCustomer;
public java.lang.String getexistingCustomer() {
return existingCustomer;
}
public void setexistingCustomer( java.lang.String existingCustomer ) {
this.existingCustomer = existingCustomer;
}
private java.lang.String socialSecurityNumber;
public java.lang.String getsocialSecurityNumber() {
return socialSecurityNumber;
}
public void setsocialSecurityNumber( java.lang.String socialSecurityNumber ) {
this.socialSecurityNumber = socialSecurityNumber;
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
private java.lang.String tickerSymbol;
public java.lang.String gettickerSymbol() {
return tickerSymbol;
}
public void settickerSymbol( java.lang.String tickerSymbol ) {
this.tickerSymbol = tickerSymbol;
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
private java.lang.String otherLocal;
public java.lang.String getotherLocal() {
return otherLocal;
}
public void setotherLocal( java.lang.String otherLocal ) {
this.otherLocal = otherLocal;
}
private java.sql.Timestamp passportExpireDate;
public java.sql.Timestamp getpassportExpireDate() {
return passportExpireDate;
}
public void setpassportExpireDate( java.sql.Timestamp passportExpireDate ) {
this.passportExpireDate = passportExpireDate;
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
private java.lang.String employmentStatusEnumId;
public java.lang.String getemploymentStatusEnumId() {
return employmentStatusEnumId;
}
public void setemploymentStatusEnumId( java.lang.String employmentStatusEnumId ) {
this.employmentStatusEnumId = employmentStatusEnumId;
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
private java.lang.String lastNameLocal;
public java.lang.String getlastNameLocal() {
return lastNameLocal;
}
public void setlastNameLocal( java.lang.String lastNameLocal ) {
this.lastNameLocal = lastNameLocal;
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
private java.lang.String cardId;
public java.lang.String getcardId() {
return cardId;
}
public void setcardId( java.lang.String cardId ) {
this.cardId = cardId;
}
private java.lang.String suffix;
public java.lang.String getsuffix() {
return suffix;
}
public void setsuffix( java.lang.String suffix ) {
this.suffix = suffix;
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
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String salutation;
public java.lang.String getsalutation() {
return salutation;
}
public void setsalutation( java.lang.String salutation ) {
this.salutation = salutation;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyRoleNameDetail> objectList = new ArrayList<PartyRoleNameDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyRoleNameDetail(genVal));
        }
        return objectList;
    }    
}
