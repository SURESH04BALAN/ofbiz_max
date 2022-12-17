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
public class PartyContactDetailByPurpose implements GenericValueObjectInterface {
public static final String module =PartyContactDetailByPurpose.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyContactDetailByPurpose(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyContactDetailByPurpose () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"contactNumber","Contact Number"},
{"allowSolicitation","Allow Solicitation"},
{"stateProvinceGeoId","State Province Geo Id"},
{"address1","Address 1"},
{"address2","Address 2"},
{"stateGeoTypeId","State Geo Type Id"},
{"city","City"},
{"stateGeoSecCode","State Geo Sec Code"},
{"directions","Directions"},
{"stateGeoCode","State Geo Code"},
{"countyGeoTypeId","County Geo Type Id"},
{"postalCode","Postal Code"},
{"verified","Verified"},
{"countryGeoSecCode","Country Geo Sec Code"},
{"postalCodeGeoId","Postal Code Geo Id"},
{"infoString","Info String"},
{"countryCode","Country Code"},
{"contactMechPurposeTypeId","Contact Mech Purpose Type Id"},
{"countyGeoCode","County Geo Code"},
{"countryGeoTypeId","Country Geo Type Id"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"countyGeoId","County Geo Id"},
{"askForName","Ask For Name"},
{"stateGeoName","State Geo Name"},
{"countryGeoName","Country Geo Name"},
{"countyGeoName","County Geo Name"},
{"geoPointId","Geo Point Id"},
{"countryAbbreviation","Country Abbreviation"},
{"thruDate","Thru Date"},
{"purposeThruDate","Purpose Thru Date"},
{"toName","To Name"},
{"yearsWithContactMech","Years With Contact Mech"},
{"countyWellKnownText","County Well Known Text"},
{"countryWellKnownText","Country Well Known Text"},
{"countryGeoId","Country Geo Id"},
{"countyGeoSecCode","County Geo Sec Code"},
{"stateWellKnownText","State Well Known Text"},
{"countryGeoCode","Country Geo Code"},
{"postalCodeExt","Postal Code Ext"},
{"areaCode","Area Code"},
{"purposeFromDate","Purpose From Date"},
{"countyAbbreviation","County Abbreviation"},
{"contactMechId","Contact Mech Id"},
{"contactMechTypeId","Contact Mech Type Id"},
{"extension","Extension"},
{"roleTypeId","Role Type Id"},
{"stateAbbreviation","State Abbreviation"},
{"stateGeoId","State Geo Id"},
{"attnName","Attn Name"},
{"monthsWithContactMech","Months With Contact Mech"},
{"comments","Comments"},
};
protected void initObject(){
this.contactNumber = "";
this.allowSolicitation = "";
this.stateProvinceGeoId = "";
this.address1 = "";
this.address2 = "";
this.stateGeoTypeId = "";
this.city = "";
this.stateGeoSecCode = "";
this.directions = "";
this.stateGeoCode = "";
this.countyGeoTypeId = "";
this.postalCode = "";
this.verified = "";
this.countryGeoSecCode = "";
this.postalCodeGeoId = "";
this.infoString = "";
this.countryCode = "";
this.contactMechPurposeTypeId = "";
this.countyGeoCode = "";
this.countryGeoTypeId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.countyGeoId = "";
this.askForName = "";
this.stateGeoName = "";
this.countryGeoName = "";
this.countyGeoName = "";
this.geoPointId = "";
this.countryAbbreviation = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.purposeThruDate = UtilDateTime.nowTimestamp();
this.toName = "";
this.yearsWithContactMech = "";
this.countyWellKnownText = "";
this.countryWellKnownText = "";
this.countryGeoId = "";
this.countyGeoSecCode = "";
this.stateWellKnownText = "";
this.countryGeoCode = "";
this.postalCodeExt = "";
this.areaCode = "";
this.purposeFromDate = UtilDateTime.nowTimestamp();
this.countyAbbreviation = "";
this.contactMechId = "";
this.contactMechTypeId = "";
this.extension = "";
this.roleTypeId = "";
this.stateAbbreviation = "";
this.stateGeoId = "";
this.attnName = "";
this.monthsWithContactMech = "";
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.contactNumber = (java.lang.String) genVal.get("contactNumber");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.stateProvinceGeoId = (java.lang.String) genVal.get("stateProvinceGeoId");
this.address1 = (java.lang.String) genVal.get("address1");
this.address2 = (java.lang.String) genVal.get("address2");
this.stateGeoTypeId = (java.lang.String) genVal.get("stateGeoTypeId");
this.city = (java.lang.String) genVal.get("city");
this.stateGeoSecCode = (java.lang.String) genVal.get("stateGeoSecCode");
this.directions = (java.lang.String) genVal.get("directions");
this.stateGeoCode = (java.lang.String) genVal.get("stateGeoCode");
this.countyGeoTypeId = (java.lang.String) genVal.get("countyGeoTypeId");
this.postalCode = (java.lang.String) genVal.get("postalCode");
this.verified = (java.lang.String) genVal.get("verified");
this.countryGeoSecCode = (java.lang.String) genVal.get("countryGeoSecCode");
this.postalCodeGeoId = (java.lang.String) genVal.get("postalCodeGeoId");
this.infoString = (java.lang.String) genVal.get("infoString");
this.countryCode = (java.lang.String) genVal.get("countryCode");
this.contactMechPurposeTypeId = (java.lang.String) genVal.get("contactMechPurposeTypeId");
this.countyGeoCode = (java.lang.String) genVal.get("countyGeoCode");
this.countryGeoTypeId = (java.lang.String) genVal.get("countryGeoTypeId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.countyGeoId = (java.lang.String) genVal.get("countyGeoId");
this.askForName = (java.lang.String) genVal.get("askForName");
this.stateGeoName = (java.lang.String) genVal.get("stateGeoName");
this.countryGeoName = (java.lang.String) genVal.get("countryGeoName");
this.countyGeoName = (java.lang.String) genVal.get("countyGeoName");
this.geoPointId = (java.lang.String) genVal.get("geoPointId");
this.countryAbbreviation = (java.lang.String) genVal.get("countryAbbreviation");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.purposeThruDate = (java.sql.Timestamp) genVal.get("purposeThruDate");
this.toName = (java.lang.String) genVal.get("toName");
this.yearsWithContactMech = (java.lang.String) genVal.get("yearsWithContactMech");
this.countyWellKnownText = (java.lang.String) genVal.get("countyWellKnownText");
this.countryWellKnownText = (java.lang.String) genVal.get("countryWellKnownText");
this.countryGeoId = (java.lang.String) genVal.get("countryGeoId");
this.countyGeoSecCode = (java.lang.String) genVal.get("countyGeoSecCode");
this.stateWellKnownText = (java.lang.String) genVal.get("stateWellKnownText");
this.countryGeoCode = (java.lang.String) genVal.get("countryGeoCode");
this.postalCodeExt = (java.lang.String) genVal.get("postalCodeExt");
this.areaCode = (java.lang.String) genVal.get("areaCode");
this.purposeFromDate = (java.sql.Timestamp) genVal.get("purposeFromDate");
this.countyAbbreviation = (java.lang.String) genVal.get("countyAbbreviation");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.extension = (java.lang.String) genVal.get("extension");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.stateAbbreviation = (java.lang.String) genVal.get("stateAbbreviation");
this.stateGeoId = (java.lang.String) genVal.get("stateGeoId");
this.attnName = (java.lang.String) genVal.get("attnName");
this.monthsWithContactMech = (java.lang.String) genVal.get("monthsWithContactMech");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("contactNumber",OrderMaxUtility.getValidEntityString(this.contactNumber));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("stateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.stateProvinceGeoId));
val.set("address1",OrderMaxUtility.getValidEntityString(this.address1));
val.set("address2",OrderMaxUtility.getValidEntityString(this.address2));
val.set("stateGeoTypeId",OrderMaxUtility.getValidEntityString(this.stateGeoTypeId));
val.set("city",OrderMaxUtility.getValidEntityString(this.city));
val.set("stateGeoSecCode",OrderMaxUtility.getValidEntityString(this.stateGeoSecCode));
val.set("directions",OrderMaxUtility.getValidEntityString(this.directions));
val.set("stateGeoCode",OrderMaxUtility.getValidEntityString(this.stateGeoCode));
val.set("countyGeoTypeId",OrderMaxUtility.getValidEntityString(this.countyGeoTypeId));
val.set("postalCode",OrderMaxUtility.getValidEntityString(this.postalCode));
val.set("verified",OrderMaxUtility.getValidEntityString(this.verified));
val.set("countryGeoSecCode",OrderMaxUtility.getValidEntityString(this.countryGeoSecCode));
val.set("postalCodeGeoId",OrderMaxUtility.getValidEntityString(this.postalCodeGeoId));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("countryCode",OrderMaxUtility.getValidEntityString(this.countryCode));
val.set("contactMechPurposeTypeId",OrderMaxUtility.getValidEntityString(this.contactMechPurposeTypeId));
val.set("countyGeoCode",OrderMaxUtility.getValidEntityString(this.countyGeoCode));
val.set("countryGeoTypeId",OrderMaxUtility.getValidEntityString(this.countryGeoTypeId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("countyGeoId",OrderMaxUtility.getValidEntityString(this.countyGeoId));
val.set("askForName",OrderMaxUtility.getValidEntityString(this.askForName));
val.set("stateGeoName",OrderMaxUtility.getValidEntityString(this.stateGeoName));
val.set("countryGeoName",OrderMaxUtility.getValidEntityString(this.countryGeoName));
val.set("countyGeoName",OrderMaxUtility.getValidEntityString(this.countyGeoName));
val.set("geoPointId",OrderMaxUtility.getValidEntityString(this.geoPointId));
val.set("countryAbbreviation",OrderMaxUtility.getValidEntityString(this.countryAbbreviation));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("purposeThruDate",OrderMaxUtility.getValidEntityTimestamp(this.purposeThruDate));
val.set("toName",OrderMaxUtility.getValidEntityString(this.toName));
val.set("yearsWithContactMech",OrderMaxUtility.getValidEntityString(this.yearsWithContactMech));
val.set("countyWellKnownText",OrderMaxUtility.getValidEntityString(this.countyWellKnownText));
val.set("countryWellKnownText",OrderMaxUtility.getValidEntityString(this.countryWellKnownText));
val.set("countryGeoId",OrderMaxUtility.getValidEntityString(this.countryGeoId));
val.set("countyGeoSecCode",OrderMaxUtility.getValidEntityString(this.countyGeoSecCode));
val.set("stateWellKnownText",OrderMaxUtility.getValidEntityString(this.stateWellKnownText));
val.set("countryGeoCode",OrderMaxUtility.getValidEntityString(this.countryGeoCode));
val.set("postalCodeExt",OrderMaxUtility.getValidEntityString(this.postalCodeExt));
val.set("areaCode",OrderMaxUtility.getValidEntityString(this.areaCode));
val.set("purposeFromDate",OrderMaxUtility.getValidEntityTimestamp(this.purposeFromDate));
val.set("countyAbbreviation",OrderMaxUtility.getValidEntityString(this.countyAbbreviation));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("stateAbbreviation",OrderMaxUtility.getValidEntityString(this.stateAbbreviation));
val.set("stateGeoId",OrderMaxUtility.getValidEntityString(this.stateGeoId));
val.set("attnName",OrderMaxUtility.getValidEntityString(this.attnName));
val.set("monthsWithContactMech",OrderMaxUtility.getValidEntityString(this.monthsWithContactMech));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("contactNumber",this.contactNumber);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("stateProvinceGeoId",this.stateProvinceGeoId);
valueMap.put("address1",this.address1);
valueMap.put("address2",this.address2);
valueMap.put("stateGeoTypeId",this.stateGeoTypeId);
valueMap.put("city",this.city);
valueMap.put("stateGeoSecCode",this.stateGeoSecCode);
valueMap.put("directions",this.directions);
valueMap.put("stateGeoCode",this.stateGeoCode);
valueMap.put("countyGeoTypeId",this.countyGeoTypeId);
valueMap.put("postalCode",this.postalCode);
valueMap.put("verified",this.verified);
valueMap.put("countryGeoSecCode",this.countryGeoSecCode);
valueMap.put("postalCodeGeoId",this.postalCodeGeoId);
valueMap.put("infoString",this.infoString);
valueMap.put("countryCode",this.countryCode);
valueMap.put("contactMechPurposeTypeId",this.contactMechPurposeTypeId);
valueMap.put("countyGeoCode",this.countyGeoCode);
valueMap.put("countryGeoTypeId",this.countryGeoTypeId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("countyGeoId",this.countyGeoId);
valueMap.put("askForName",this.askForName);
valueMap.put("stateGeoName",this.stateGeoName);
valueMap.put("countryGeoName",this.countryGeoName);
valueMap.put("countyGeoName",this.countyGeoName);
valueMap.put("geoPointId",this.geoPointId);
valueMap.put("countryAbbreviation",this.countryAbbreviation);
valueMap.put("thruDate",this.thruDate);
valueMap.put("purposeThruDate",this.purposeThruDate);
valueMap.put("toName",this.toName);
valueMap.put("yearsWithContactMech",this.yearsWithContactMech);
valueMap.put("countyWellKnownText",this.countyWellKnownText);
valueMap.put("countryWellKnownText",this.countryWellKnownText);
valueMap.put("countryGeoId",this.countryGeoId);
valueMap.put("countyGeoSecCode",this.countyGeoSecCode);
valueMap.put("stateWellKnownText",this.stateWellKnownText);
valueMap.put("countryGeoCode",this.countryGeoCode);
valueMap.put("postalCodeExt",this.postalCodeExt);
valueMap.put("areaCode",this.areaCode);
valueMap.put("purposeFromDate",this.purposeFromDate);
valueMap.put("countyAbbreviation",this.countyAbbreviation);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("extension",this.extension);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("stateAbbreviation",this.stateAbbreviation);
valueMap.put("stateGeoId",this.stateGeoId);
valueMap.put("attnName",this.attnName);
valueMap.put("monthsWithContactMech",this.monthsWithContactMech);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyContactDetailByPurpose");
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
private java.lang.String allowSolicitation;
public java.lang.String getallowSolicitation() {
return allowSolicitation;
}
public void setallowSolicitation( java.lang.String allowSolicitation ) {
this.allowSolicitation = allowSolicitation;
}
private java.lang.String stateProvinceGeoId;
public java.lang.String getstateProvinceGeoId() {
return stateProvinceGeoId;
}
public void setstateProvinceGeoId( java.lang.String stateProvinceGeoId ) {
this.stateProvinceGeoId = stateProvinceGeoId;
}
private java.lang.String address1;
public java.lang.String getaddress1() {
return address1;
}
public void setaddress1( java.lang.String address1 ) {
this.address1 = address1;
}
private java.lang.String address2;
public java.lang.String getaddress2() {
return address2;
}
public void setaddress2( java.lang.String address2 ) {
this.address2 = address2;
}
private java.lang.String stateGeoTypeId;
public java.lang.String getstateGeoTypeId() {
return stateGeoTypeId;
}
public void setstateGeoTypeId( java.lang.String stateGeoTypeId ) {
this.stateGeoTypeId = stateGeoTypeId;
}
private java.lang.String city;
public java.lang.String getcity() {
return city;
}
public void setcity( java.lang.String city ) {
this.city = city;
}
private java.lang.String stateGeoSecCode;
public java.lang.String getstateGeoSecCode() {
return stateGeoSecCode;
}
public void setstateGeoSecCode( java.lang.String stateGeoSecCode ) {
this.stateGeoSecCode = stateGeoSecCode;
}
private java.lang.String directions;
public java.lang.String getdirections() {
return directions;
}
public void setdirections( java.lang.String directions ) {
this.directions = directions;
}
private java.lang.String stateGeoCode;
public java.lang.String getstateGeoCode() {
return stateGeoCode;
}
public void setstateGeoCode( java.lang.String stateGeoCode ) {
this.stateGeoCode = stateGeoCode;
}
private java.lang.String countyGeoTypeId;
public java.lang.String getcountyGeoTypeId() {
return countyGeoTypeId;
}
public void setcountyGeoTypeId( java.lang.String countyGeoTypeId ) {
this.countyGeoTypeId = countyGeoTypeId;
}
private java.lang.String postalCode;
public java.lang.String getpostalCode() {
return postalCode;
}
public void setpostalCode( java.lang.String postalCode ) {
this.postalCode = postalCode;
}
private java.lang.String verified;
public java.lang.String getverified() {
return verified;
}
public void setverified( java.lang.String verified ) {
this.verified = verified;
}
private java.lang.String countryGeoSecCode;
public java.lang.String getcountryGeoSecCode() {
return countryGeoSecCode;
}
public void setcountryGeoSecCode( java.lang.String countryGeoSecCode ) {
this.countryGeoSecCode = countryGeoSecCode;
}
private java.lang.String postalCodeGeoId;
public java.lang.String getpostalCodeGeoId() {
return postalCodeGeoId;
}
public void setpostalCodeGeoId( java.lang.String postalCodeGeoId ) {
this.postalCodeGeoId = postalCodeGeoId;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
private java.lang.String countryCode;
public java.lang.String getcountryCode() {
return countryCode;
}
public void setcountryCode( java.lang.String countryCode ) {
this.countryCode = countryCode;
}
private java.lang.String contactMechPurposeTypeId;
public java.lang.String getcontactMechPurposeTypeId() {
return contactMechPurposeTypeId;
}
public void setcontactMechPurposeTypeId( java.lang.String contactMechPurposeTypeId ) {
this.contactMechPurposeTypeId = contactMechPurposeTypeId;
}
private java.lang.String countyGeoCode;
public java.lang.String getcountyGeoCode() {
return countyGeoCode;
}
public void setcountyGeoCode( java.lang.String countyGeoCode ) {
this.countyGeoCode = countyGeoCode;
}
private java.lang.String countryGeoTypeId;
public java.lang.String getcountryGeoTypeId() {
return countryGeoTypeId;
}
public void setcountryGeoTypeId( java.lang.String countryGeoTypeId ) {
this.countryGeoTypeId = countryGeoTypeId;
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
private java.lang.String countyGeoId;
public java.lang.String getcountyGeoId() {
return countyGeoId;
}
public void setcountyGeoId( java.lang.String countyGeoId ) {
this.countyGeoId = countyGeoId;
}
private java.lang.String askForName;
public java.lang.String getaskForName() {
return askForName;
}
public void setaskForName( java.lang.String askForName ) {
this.askForName = askForName;
}
private java.lang.String stateGeoName;
public java.lang.String getstateGeoName() {
return stateGeoName;
}
public void setstateGeoName( java.lang.String stateGeoName ) {
this.stateGeoName = stateGeoName;
}
private java.lang.String countryGeoName;
public java.lang.String getcountryGeoName() {
return countryGeoName;
}
public void setcountryGeoName( java.lang.String countryGeoName ) {
this.countryGeoName = countryGeoName;
}
private java.lang.String countyGeoName;
public java.lang.String getcountyGeoName() {
return countyGeoName;
}
public void setcountyGeoName( java.lang.String countyGeoName ) {
this.countyGeoName = countyGeoName;
}
private java.lang.String geoPointId;
public java.lang.String getgeoPointId() {
return geoPointId;
}
public void setgeoPointId( java.lang.String geoPointId ) {
this.geoPointId = geoPointId;
}
private java.lang.String countryAbbreviation;
public java.lang.String getcountryAbbreviation() {
return countryAbbreviation;
}
public void setcountryAbbreviation( java.lang.String countryAbbreviation ) {
this.countryAbbreviation = countryAbbreviation;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp purposeThruDate;
public java.sql.Timestamp getpurposeThruDate() {
return purposeThruDate;
}
public void setpurposeThruDate( java.sql.Timestamp purposeThruDate ) {
this.purposeThruDate = purposeThruDate;
}
private java.lang.String toName;
public java.lang.String gettoName() {
return toName;
}
public void settoName( java.lang.String toName ) {
this.toName = toName;
}
private java.lang.String yearsWithContactMech;
public java.lang.String getyearsWithContactMech() {
return yearsWithContactMech;
}
public void setyearsWithContactMech( java.lang.String yearsWithContactMech ) {
this.yearsWithContactMech = yearsWithContactMech;
}
private java.lang.String countyWellKnownText;
public java.lang.String getcountyWellKnownText() {
return countyWellKnownText;
}
public void setcountyWellKnownText( java.lang.String countyWellKnownText ) {
this.countyWellKnownText = countyWellKnownText;
}
private java.lang.String countryWellKnownText;
public java.lang.String getcountryWellKnownText() {
return countryWellKnownText;
}
public void setcountryWellKnownText( java.lang.String countryWellKnownText ) {
this.countryWellKnownText = countryWellKnownText;
}
private java.lang.String countryGeoId;
public java.lang.String getcountryGeoId() {
return countryGeoId;
}
public void setcountryGeoId( java.lang.String countryGeoId ) {
this.countryGeoId = countryGeoId;
}
private java.lang.String countyGeoSecCode;
public java.lang.String getcountyGeoSecCode() {
return countyGeoSecCode;
}
public void setcountyGeoSecCode( java.lang.String countyGeoSecCode ) {
this.countyGeoSecCode = countyGeoSecCode;
}
private java.lang.String stateWellKnownText;
public java.lang.String getstateWellKnownText() {
return stateWellKnownText;
}
public void setstateWellKnownText( java.lang.String stateWellKnownText ) {
this.stateWellKnownText = stateWellKnownText;
}
private java.lang.String countryGeoCode;
public java.lang.String getcountryGeoCode() {
return countryGeoCode;
}
public void setcountryGeoCode( java.lang.String countryGeoCode ) {
this.countryGeoCode = countryGeoCode;
}
private java.lang.String postalCodeExt;
public java.lang.String getpostalCodeExt() {
return postalCodeExt;
}
public void setpostalCodeExt( java.lang.String postalCodeExt ) {
this.postalCodeExt = postalCodeExt;
}
private java.lang.String areaCode;
public java.lang.String getareaCode() {
return areaCode;
}
public void setareaCode( java.lang.String areaCode ) {
this.areaCode = areaCode;
}
private java.sql.Timestamp purposeFromDate;
public java.sql.Timestamp getpurposeFromDate() {
return purposeFromDate;
}
public void setpurposeFromDate( java.sql.Timestamp purposeFromDate ) {
this.purposeFromDate = purposeFromDate;
}
private java.lang.String countyAbbreviation;
public java.lang.String getcountyAbbreviation() {
return countyAbbreviation;
}
public void setcountyAbbreviation( java.lang.String countyAbbreviation ) {
this.countyAbbreviation = countyAbbreviation;
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
private java.lang.String stateAbbreviation;
public java.lang.String getstateAbbreviation() {
return stateAbbreviation;
}
public void setstateAbbreviation( java.lang.String stateAbbreviation ) {
this.stateAbbreviation = stateAbbreviation;
}
private java.lang.String stateGeoId;
public java.lang.String getstateGeoId() {
return stateGeoId;
}
public void setstateGeoId( java.lang.String stateGeoId ) {
this.stateGeoId = stateGeoId;
}
private java.lang.String attnName;
public java.lang.String getattnName() {
return attnName;
}
public void setattnName( java.lang.String attnName ) {
this.attnName = attnName;
}
private java.lang.String monthsWithContactMech;
public java.lang.String getmonthsWithContactMech() {
return monthsWithContactMech;
}
public void setmonthsWithContactMech( java.lang.String monthsWithContactMech ) {
this.monthsWithContactMech = monthsWithContactMech;
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
        Collection<PartyContactDetailByPurpose> objectList = new ArrayList<PartyContactDetailByPurpose>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyContactDetailByPurpose(genVal));
        }
        return objectList;
    }    
}
