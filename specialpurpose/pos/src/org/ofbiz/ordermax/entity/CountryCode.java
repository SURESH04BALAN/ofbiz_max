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
public class CountryCode implements GenericValueObjectInterface {
public static final String module =CountryCode.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CountryCode(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CountryCode () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"countryName","Country Name"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"countryCode","Country Code"},
{"countryAbbr","Country Abbr"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"countryNumber","Country Number"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.countryName = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.countryCode = "";
this.countryAbbr = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.countryNumber = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.countryName = (java.lang.String) genVal.get("countryName");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.countryCode = (java.lang.String) genVal.get("countryCode");
this.countryAbbr = (java.lang.String) genVal.get("countryAbbr");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.countryNumber = (java.lang.String) genVal.get("countryNumber");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("countryName",OrderMaxUtility.getValidEntityString(this.countryName));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("countryCode",OrderMaxUtility.getValidEntityString(this.countryCode));
val.set("countryAbbr",OrderMaxUtility.getValidEntityString(this.countryAbbr));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("countryNumber",OrderMaxUtility.getValidEntityString(this.countryNumber));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("countryName",this.countryName);
valueMap.put("countryCode",this.countryCode);
valueMap.put("countryAbbr",this.countryAbbr);
valueMap.put("countryNumber",this.countryNumber);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CountryCode");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String countryName;
public java.lang.String getcountryName() {
return countryName;
}
public void setcountryName( java.lang.String countryName ) {
this.countryName = countryName;
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
private java.lang.String countryCode;
public java.lang.String getcountryCode() {
return countryCode;
}
public void setcountryCode( java.lang.String countryCode ) {
this.countryCode = countryCode;
}
private java.lang.String countryAbbr;
public java.lang.String getcountryAbbr() {
return countryAbbr;
}
public void setcountryAbbr( java.lang.String countryAbbr ) {
this.countryAbbr = countryAbbr;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String countryNumber;
public java.lang.String getcountryNumber() {
return countryNumber;
}
public void setcountryNumber( java.lang.String countryNumber ) {
this.countryNumber = countryNumber;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CountryCode> objectList = new ArrayList<CountryCode>();
        for (GenericValue genVal : genList) {
            objectList.add(new CountryCode(genVal));
        }
        return objectList;
    }    
}
