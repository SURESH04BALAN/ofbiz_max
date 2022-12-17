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
public class PartyAndPostalAddress implements GenericValueObjectInterface {
public static final String module =PartyAndPostalAddress.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAndPostalAddress(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAndPostalAddress () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruDate","Thru Date"},
{"toName","To Name"},
{"postalCodeExt","Postal Code Ext"},
{"allowSolicitation","Allow Solicitation"},
{"contactMechId","Contact Mech Id"},
{"address1","Address 1"},
{"stateProvinceGeoId","State Province Geo Id"},
{"address2","Address 2"},
{"contactMechTypeId","Contact Mech Type Id"},
{"partyTypeId","Party Type Id"},
{"city","City"},
{"extension","Extension"},
{"statusId","Status Id"},
{"directions","Directions"},
{"postalCode","Postal Code"},
{"fromDate","From Date"},
{"countryGeoId","Country Geo Id"},
{"partyId","Party Id"},
{"attnName","Attn Name"},
{"postalCodeGeoId","Postal Code Geo Id"},
{"comments","Comments"},
{"infoString","Info String"},
};
protected void initObject(){
this.thruDate = UtilDateTime.nowTimestamp();
this.toName = "";
this.postalCodeExt = "";
this.allowSolicitation = "";
this.contactMechId = "";
this.address1 = "";
this.stateProvinceGeoId = "";
this.address2 = "";
this.contactMechTypeId = "";
this.partyTypeId = "";
this.city = "";
this.extension = "";
this.statusId = "";
this.directions = "";
this.postalCode = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.countryGeoId = "";
this.partyId = "";
this.attnName = "";
this.postalCodeGeoId = "";
this.comments = "";
this.infoString = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.toName = (java.lang.String) genVal.get("toName");
this.postalCodeExt = (java.lang.String) genVal.get("postalCodeExt");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.address1 = (java.lang.String) genVal.get("address1");
this.stateProvinceGeoId = (java.lang.String) genVal.get("stateProvinceGeoId");
this.address2 = (java.lang.String) genVal.get("address2");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.city = (java.lang.String) genVal.get("city");
this.extension = (java.lang.String) genVal.get("extension");
this.statusId = (java.lang.String) genVal.get("statusId");
this.directions = (java.lang.String) genVal.get("directions");
this.postalCode = (java.lang.String) genVal.get("postalCode");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.countryGeoId = (java.lang.String) genVal.get("countryGeoId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.attnName = (java.lang.String) genVal.get("attnName");
this.postalCodeGeoId = (java.lang.String) genVal.get("postalCodeGeoId");
this.comments = (java.lang.String) genVal.get("comments");
this.infoString = (java.lang.String) genVal.get("infoString");
}
protected void getGenericValue(GenericValue val) {
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("toName",OrderMaxUtility.getValidEntityString(this.toName));
val.set("postalCodeExt",OrderMaxUtility.getValidEntityString(this.postalCodeExt));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("address1",OrderMaxUtility.getValidEntityString(this.address1));
val.set("stateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.stateProvinceGeoId));
val.set("address2",OrderMaxUtility.getValidEntityString(this.address2));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("city",OrderMaxUtility.getValidEntityString(this.city));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("directions",OrderMaxUtility.getValidEntityString(this.directions));
val.set("postalCode",OrderMaxUtility.getValidEntityString(this.postalCode));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("countryGeoId",OrderMaxUtility.getValidEntityString(this.countryGeoId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("attnName",OrderMaxUtility.getValidEntityString(this.attnName));
val.set("postalCodeGeoId",OrderMaxUtility.getValidEntityString(this.postalCodeGeoId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruDate",this.thruDate);
valueMap.put("toName",this.toName);
valueMap.put("postalCodeExt",this.postalCodeExt);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("address1",this.address1);
valueMap.put("stateProvinceGeoId",this.stateProvinceGeoId);
valueMap.put("address2",this.address2);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("city",this.city);
valueMap.put("extension",this.extension);
valueMap.put("statusId",this.statusId);
valueMap.put("directions",this.directions);
valueMap.put("postalCode",this.postalCode);
valueMap.put("fromDate",this.fromDate);
valueMap.put("countryGeoId",this.countryGeoId);
valueMap.put("partyId",this.partyId);
valueMap.put("attnName",this.attnName);
valueMap.put("postalCodeGeoId",this.postalCodeGeoId);
valueMap.put("comments",this.comments);
valueMap.put("infoString",this.infoString);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAndPostalAddress");
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
private java.lang.String toName;
public java.lang.String gettoName() {
return toName;
}
public void settoName( java.lang.String toName ) {
this.toName = toName;
}
private java.lang.String postalCodeExt;
public java.lang.String getpostalCodeExt() {
return postalCodeExt;
}
public void setpostalCodeExt( java.lang.String postalCodeExt ) {
this.postalCodeExt = postalCodeExt;
}
private java.lang.String allowSolicitation;
public java.lang.String getallowSolicitation() {
return allowSolicitation;
}
public void setallowSolicitation( java.lang.String allowSolicitation ) {
this.allowSolicitation = allowSolicitation;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String address1;
public java.lang.String getaddress1() {
return address1;
}
public void setaddress1( java.lang.String address1 ) {
this.address1 = address1;
}
private java.lang.String stateProvinceGeoId;
public java.lang.String getstateProvinceGeoId() {
return stateProvinceGeoId;
}
public void setstateProvinceGeoId( java.lang.String stateProvinceGeoId ) {
this.stateProvinceGeoId = stateProvinceGeoId;
}
private java.lang.String address2;
public java.lang.String getaddress2() {
return address2;
}
public void setaddress2( java.lang.String address2 ) {
this.address2 = address2;
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
private java.lang.String city;
public java.lang.String getcity() {
return city;
}
public void setcity( java.lang.String city ) {
this.city = city;
}
private java.lang.String extension;
public java.lang.String getextension() {
return extension;
}
public void setextension( java.lang.String extension ) {
this.extension = extension;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String directions;
public java.lang.String getdirections() {
return directions;
}
public void setdirections( java.lang.String directions ) {
this.directions = directions;
}
private java.lang.String postalCode;
public java.lang.String getpostalCode() {
return postalCode;
}
public void setpostalCode( java.lang.String postalCode ) {
this.postalCode = postalCode;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String countryGeoId;
public java.lang.String getcountryGeoId() {
return countryGeoId;
}
public void setcountryGeoId( java.lang.String countryGeoId ) {
this.countryGeoId = countryGeoId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String attnName;
public java.lang.String getattnName() {
return attnName;
}
public void setattnName( java.lang.String attnName ) {
this.attnName = attnName;
}
private java.lang.String postalCodeGeoId;
public java.lang.String getpostalCodeGeoId() {
return postalCodeGeoId;
}
public void setpostalCodeGeoId( java.lang.String postalCodeGeoId ) {
this.postalCodeGeoId = postalCodeGeoId;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAndPostalAddress> objectList = new ArrayList<PartyAndPostalAddress>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAndPostalAddress(genVal));
        }
        return objectList;
    }    
}
