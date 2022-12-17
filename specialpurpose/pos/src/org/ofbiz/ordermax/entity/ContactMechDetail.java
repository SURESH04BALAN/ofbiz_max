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
public class ContactMechDetail implements GenericValueObjectInterface {
public static final String module =ContactMechDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ContactMechDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ContactMechDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"paAddress2","Pa Address 2"},
{"paStateProvinceGeoId","Pa State Province Geo Id"},
{"paAddress1","Pa Address 1"},
{"tnAreaCode","Tn Area Code"},
{"contactMechId","Contact Mech Id"},
{"tnAskForName","Tn Ask For Name"},
{"paAttnName","Pa Attn Name"},
{"paPostalCodeExt","Pa Postal Code Ext"},
{"contactMechTypeId","Contact Mech Type Id"},
{"tnCountryCode","Tn Country Code"},
{"paDirections","Pa Directions"},
{"paCity","Pa City"},
{"paCountryGeoId","Pa Country Geo Id"},
{"paPostalCode","Pa Postal Code"},
{"paToName","Pa To Name"},
{"tnContactNumber","Tn Contact Number"},
{"paCountyGeoId","Pa County Geo Id"},
{"paPostalCodeGeoId","Pa Postal Code Geo Id"},
{"infoString","Info String"},
{"paGeoPointId","Pa Geo Point Id"},
};
protected void initObject(){
this.paAddress2 = "";
this.paStateProvinceGeoId = "";
this.paAddress1 = "";
this.tnAreaCode = "";
this.contactMechId = "";
this.tnAskForName = "";
this.paAttnName = "";
this.paPostalCodeExt = "";
this.contactMechTypeId = "";
this.tnCountryCode = "";
this.paDirections = "";
this.paCity = "";
this.paCountryGeoId = "";
this.paPostalCode = "";
this.paToName = "";
this.tnContactNumber = "";
this.paCountyGeoId = "";
this.paPostalCodeGeoId = "";
this.infoString = "";
this.paGeoPointId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.paAddress2 = (java.lang.String) genVal.get("paAddress2");
this.paStateProvinceGeoId = (java.lang.String) genVal.get("paStateProvinceGeoId");
this.paAddress1 = (java.lang.String) genVal.get("paAddress1");
this.tnAreaCode = (java.lang.String) genVal.get("tnAreaCode");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.tnAskForName = (java.lang.String) genVal.get("tnAskForName");
this.paAttnName = (java.lang.String) genVal.get("paAttnName");
this.paPostalCodeExt = (java.lang.String) genVal.get("paPostalCodeExt");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.tnCountryCode = (java.lang.String) genVal.get("tnCountryCode");
this.paDirections = (java.lang.String) genVal.get("paDirections");
this.paCity = (java.lang.String) genVal.get("paCity");
this.paCountryGeoId = (java.lang.String) genVal.get("paCountryGeoId");
this.paPostalCode = (java.lang.String) genVal.get("paPostalCode");
this.paToName = (java.lang.String) genVal.get("paToName");
this.tnContactNumber = (java.lang.String) genVal.get("tnContactNumber");
this.paCountyGeoId = (java.lang.String) genVal.get("paCountyGeoId");
this.paPostalCodeGeoId = (java.lang.String) genVal.get("paPostalCodeGeoId");
this.infoString = (java.lang.String) genVal.get("infoString");
this.paGeoPointId = (java.lang.String) genVal.get("paGeoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("paAddress2",OrderMaxUtility.getValidEntityString(this.paAddress2));
val.set("paStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.paStateProvinceGeoId));
val.set("paAddress1",OrderMaxUtility.getValidEntityString(this.paAddress1));
val.set("tnAreaCode",OrderMaxUtility.getValidEntityString(this.tnAreaCode));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("tnAskForName",OrderMaxUtility.getValidEntityString(this.tnAskForName));
val.set("paAttnName",OrderMaxUtility.getValidEntityString(this.paAttnName));
val.set("paPostalCodeExt",OrderMaxUtility.getValidEntityString(this.paPostalCodeExt));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("tnCountryCode",OrderMaxUtility.getValidEntityString(this.tnCountryCode));
val.set("paDirections",OrderMaxUtility.getValidEntityString(this.paDirections));
val.set("paCity",OrderMaxUtility.getValidEntityString(this.paCity));
val.set("paCountryGeoId",OrderMaxUtility.getValidEntityString(this.paCountryGeoId));
val.set("paPostalCode",OrderMaxUtility.getValidEntityString(this.paPostalCode));
val.set("paToName",OrderMaxUtility.getValidEntityString(this.paToName));
val.set("tnContactNumber",OrderMaxUtility.getValidEntityString(this.tnContactNumber));
val.set("paCountyGeoId",OrderMaxUtility.getValidEntityString(this.paCountyGeoId));
val.set("paPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.paPostalCodeGeoId));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("paGeoPointId",OrderMaxUtility.getValidEntityString(this.paGeoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("paAddress2",this.paAddress2);
valueMap.put("paStateProvinceGeoId",this.paStateProvinceGeoId);
valueMap.put("paAddress1",this.paAddress1);
valueMap.put("tnAreaCode",this.tnAreaCode);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("tnAskForName",this.tnAskForName);
valueMap.put("paAttnName",this.paAttnName);
valueMap.put("paPostalCodeExt",this.paPostalCodeExt);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("tnCountryCode",this.tnCountryCode);
valueMap.put("paDirections",this.paDirections);
valueMap.put("paCity",this.paCity);
valueMap.put("paCountryGeoId",this.paCountryGeoId);
valueMap.put("paPostalCode",this.paPostalCode);
valueMap.put("paToName",this.paToName);
valueMap.put("tnContactNumber",this.tnContactNumber);
valueMap.put("paCountyGeoId",this.paCountyGeoId);
valueMap.put("paPostalCodeGeoId",this.paPostalCodeGeoId);
valueMap.put("infoString",this.infoString);
valueMap.put("paGeoPointId",this.paGeoPointId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ContactMechDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String paAddress2;
public java.lang.String getpaAddress2() {
return paAddress2;
}
public void setpaAddress2( java.lang.String paAddress2 ) {
this.paAddress2 = paAddress2;
}
private java.lang.String paStateProvinceGeoId;
public java.lang.String getpaStateProvinceGeoId() {
return paStateProvinceGeoId;
}
public void setpaStateProvinceGeoId( java.lang.String paStateProvinceGeoId ) {
this.paStateProvinceGeoId = paStateProvinceGeoId;
}
private java.lang.String paAddress1;
public java.lang.String getpaAddress1() {
return paAddress1;
}
public void setpaAddress1( java.lang.String paAddress1 ) {
this.paAddress1 = paAddress1;
}
private java.lang.String tnAreaCode;
public java.lang.String gettnAreaCode() {
return tnAreaCode;
}
public void settnAreaCode( java.lang.String tnAreaCode ) {
this.tnAreaCode = tnAreaCode;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String tnAskForName;
public java.lang.String gettnAskForName() {
return tnAskForName;
}
public void settnAskForName( java.lang.String tnAskForName ) {
this.tnAskForName = tnAskForName;
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
private java.lang.String tnCountryCode;
public java.lang.String gettnCountryCode() {
return tnCountryCode;
}
public void settnCountryCode( java.lang.String tnCountryCode ) {
this.tnCountryCode = tnCountryCode;
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
private java.lang.String paCountryGeoId;
public java.lang.String getpaCountryGeoId() {
return paCountryGeoId;
}
public void setpaCountryGeoId( java.lang.String paCountryGeoId ) {
this.paCountryGeoId = paCountryGeoId;
}
private java.lang.String paPostalCode;
public java.lang.String getpaPostalCode() {
return paPostalCode;
}
public void setpaPostalCode( java.lang.String paPostalCode ) {
this.paPostalCode = paPostalCode;
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
        Collection<ContactMechDetail> objectList = new ArrayList<ContactMechDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new ContactMechDetail(genVal));
        }
        return objectList;
    }    
}
