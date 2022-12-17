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
public class CountryAddressFormat implements GenericValueObjectInterface {
public static final String module =CountryAddressFormat.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CountryAddressFormat(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CountryAddressFormat () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"postalCodeRegex","Postal Code Regex"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"requirePostalCode","Require Postal Code"},
{"geoId","Geo Id"},
{"requirePostalCodeExt","Require Postal Code Ext"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"hasPostalCodeExt","Has Postal Code Ext"},
{"geoAssocTypeId","Geo Assoc Type Id"},
{"requireStateProvinceId","Require State Province Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"addressFormat","Address Format"},
};
protected void initObject(){
this.postalCodeRegex = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.requirePostalCode = "";
this.geoId = "";
this.requirePostalCodeExt = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.hasPostalCodeExt = "";
this.geoAssocTypeId = "";
this.requireStateProvinceId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.addressFormat = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.postalCodeRegex = (java.lang.String) genVal.get("postalCodeRegex");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.requirePostalCode = (java.lang.String) genVal.get("requirePostalCode");
this.geoId = (java.lang.String) genVal.get("geoId");
this.requirePostalCodeExt = (java.lang.String) genVal.get("requirePostalCodeExt");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.hasPostalCodeExt = (java.lang.String) genVal.get("hasPostalCodeExt");
this.geoAssocTypeId = (java.lang.String) genVal.get("geoAssocTypeId");
this.requireStateProvinceId = (java.lang.String) genVal.get("requireStateProvinceId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.addressFormat = (java.lang.String) genVal.get("addressFormat");
}
protected void getGenericValue(GenericValue val) {
val.set("postalCodeRegex",OrderMaxUtility.getValidEntityString(this.postalCodeRegex));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("requirePostalCode",OrderMaxUtility.getValidEntityString(this.requirePostalCode));
val.set("geoId",OrderMaxUtility.getValidEntityString(this.geoId));
val.set("requirePostalCodeExt",OrderMaxUtility.getValidEntityString(this.requirePostalCodeExt));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("hasPostalCodeExt",OrderMaxUtility.getValidEntityString(this.hasPostalCodeExt));
val.set("geoAssocTypeId",OrderMaxUtility.getValidEntityString(this.geoAssocTypeId));
val.set("requireStateProvinceId",OrderMaxUtility.getValidEntityString(this.requireStateProvinceId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("addressFormat",OrderMaxUtility.getValidEntityString(this.addressFormat));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("postalCodeRegex",this.postalCodeRegex);
valueMap.put("requirePostalCode",this.requirePostalCode);
valueMap.put("geoId",this.geoId);
valueMap.put("requirePostalCodeExt",this.requirePostalCodeExt);
valueMap.put("hasPostalCodeExt",this.hasPostalCodeExt);
valueMap.put("geoAssocTypeId",this.geoAssocTypeId);
valueMap.put("requireStateProvinceId",this.requireStateProvinceId);
valueMap.put("addressFormat",this.addressFormat);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CountryAddressFormat");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String postalCodeRegex;
public java.lang.String getpostalCodeRegex() {
return postalCodeRegex;
}
public void setpostalCodeRegex( java.lang.String postalCodeRegex ) {
this.postalCodeRegex = postalCodeRegex;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String requirePostalCode;
public java.lang.String getrequirePostalCode() {
return requirePostalCode;
}
public void setrequirePostalCode( java.lang.String requirePostalCode ) {
this.requirePostalCode = requirePostalCode;
}
private java.lang.String geoId;
public java.lang.String getgeoId() {
return geoId;
}
public void setgeoId( java.lang.String geoId ) {
this.geoId = geoId;
}
private java.lang.String requirePostalCodeExt;
public java.lang.String getrequirePostalCodeExt() {
return requirePostalCodeExt;
}
public void setrequirePostalCodeExt( java.lang.String requirePostalCodeExt ) {
this.requirePostalCodeExt = requirePostalCodeExt;
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
private java.lang.String hasPostalCodeExt;
public java.lang.String gethasPostalCodeExt() {
return hasPostalCodeExt;
}
public void sethasPostalCodeExt( java.lang.String hasPostalCodeExt ) {
this.hasPostalCodeExt = hasPostalCodeExt;
}
private java.lang.String geoAssocTypeId;
public java.lang.String getgeoAssocTypeId() {
return geoAssocTypeId;
}
public void setgeoAssocTypeId( java.lang.String geoAssocTypeId ) {
this.geoAssocTypeId = geoAssocTypeId;
}
private java.lang.String requireStateProvinceId;
public java.lang.String getrequireStateProvinceId() {
return requireStateProvinceId;
}
public void setrequireStateProvinceId( java.lang.String requireStateProvinceId ) {
this.requireStateProvinceId = requireStateProvinceId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String addressFormat;
public java.lang.String getaddressFormat() {
return addressFormat;
}
public void setaddressFormat( java.lang.String addressFormat ) {
this.addressFormat = addressFormat;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CountryAddressFormat> objectList = new ArrayList<CountryAddressFormat>();
        for (GenericValue genVal : genList) {
            objectList.add(new CountryAddressFormat(genVal));
        }
        return objectList;
    }    
}
