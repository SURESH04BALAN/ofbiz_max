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
public class PartyAndTelecomNumber implements GenericValueObjectInterface {
public static final String module =PartyAndTelecomNumber.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAndTelecomNumber(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAndTelecomNumber () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"contactNumber","Contact Number"},
{"thruDate","Thru Date"},
{"yearsWithContactMech","Years With Contact Mech"},
{"allowSolicitation","Allow Solicitation"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"externalId","External Id"},
{"dataSourceId","Data Source Id"},
{"isUnread","Is Unread"},
{"statusId","Status Id"},
{"verified","Verified"},
{"description","Description"},
{"createdDate","Created Date"},
{"areaCode","Area Code"},
{"contactMechId","Contact Mech Id"},
{"countryCode","Country Code"},
{"partyTypeId","Party Type Id"},
{"extension","Extension"},
{"roleTypeId","Role Type Id"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"monthsWithContactMech","Months With Contact Mech"},
{"askForName","Ask For Name"},
{"lastModifiedDate","Last Modified Date"},
{"comments","Comments"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.contactNumber = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.yearsWithContactMech = "";
this.allowSolicitation = "";
this.preferredCurrencyUomId = "";
this.externalId = "";
this.dataSourceId = "";
this.isUnread = "";
this.statusId = "";
this.verified = "";
this.description = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.areaCode = "";
this.contactMechId = "";
this.countryCode = "";
this.partyTypeId = "";
this.extension = "";
this.roleTypeId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.monthsWithContactMech = "";
this.askForName = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.comments = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.contactNumber = (java.lang.String) genVal.get("contactNumber");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.yearsWithContactMech = (java.lang.String) genVal.get("yearsWithContactMech");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.statusId = (java.lang.String) genVal.get("statusId");
this.verified = (java.lang.String) genVal.get("verified");
this.description = (java.lang.String) genVal.get("description");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.areaCode = (java.lang.String) genVal.get("areaCode");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.countryCode = (java.lang.String) genVal.get("countryCode");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.extension = (java.lang.String) genVal.get("extension");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.monthsWithContactMech = (java.lang.String) genVal.get("monthsWithContactMech");
this.askForName = (java.lang.String) genVal.get("askForName");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.comments = (java.lang.String) genVal.get("comments");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("contactNumber",OrderMaxUtility.getValidEntityString(this.contactNumber));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("yearsWithContactMech",OrderMaxUtility.getValidEntityString(this.yearsWithContactMech));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("verified",OrderMaxUtility.getValidEntityString(this.verified));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("areaCode",OrderMaxUtility.getValidEntityString(this.areaCode));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("countryCode",OrderMaxUtility.getValidEntityString(this.countryCode));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("monthsWithContactMech",OrderMaxUtility.getValidEntityString(this.monthsWithContactMech));
val.set("askForName",OrderMaxUtility.getValidEntityString(this.askForName));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("contactNumber",this.contactNumber);
valueMap.put("thruDate",this.thruDate);
valueMap.put("yearsWithContactMech",this.yearsWithContactMech);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("externalId",this.externalId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("statusId",this.statusId);
valueMap.put("verified",this.verified);
valueMap.put("description",this.description);
valueMap.put("createdDate",this.createdDate);
valueMap.put("areaCode",this.areaCode);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("countryCode",this.countryCode);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("extension",this.extension);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("monthsWithContactMech",this.monthsWithContactMech);
valueMap.put("askForName",this.askForName);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("comments",this.comments);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAndTelecomNumber");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String contactNumber;
public java.lang.String getcontactNumber() {
return contactNumber;
}
public void setcontactNumber( java.lang.String contactNumber ) {
this.contactNumber = contactNumber;
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
private java.lang.String allowSolicitation;
public java.lang.String getallowSolicitation() {
return allowSolicitation;
}
public void setallowSolicitation( java.lang.String allowSolicitation ) {
this.allowSolicitation = allowSolicitation;
}
private java.lang.String preferredCurrencyUomId;
public java.lang.String getpreferredCurrencyUomId() {
return preferredCurrencyUomId;
}
public void setpreferredCurrencyUomId( java.lang.String preferredCurrencyUomId ) {
this.preferredCurrencyUomId = preferredCurrencyUomId;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
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
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String areaCode;
public java.lang.String getareaCode() {
return areaCode;
}
public void setareaCode( java.lang.String areaCode ) {
this.areaCode = areaCode;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String countryCode;
public java.lang.String getcountryCode() {
return countryCode;
}
public void setcountryCode( java.lang.String countryCode ) {
this.countryCode = countryCode;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
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
private java.lang.String askForName;
public java.lang.String getaskForName() {
return askForName;
}
public void setaskForName( java.lang.String askForName ) {
this.askForName = askForName;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAndTelecomNumber> objectList = new ArrayList<PartyAndTelecomNumber>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAndTelecomNumber(genVal));
        }
        return objectList;
    }    
}
