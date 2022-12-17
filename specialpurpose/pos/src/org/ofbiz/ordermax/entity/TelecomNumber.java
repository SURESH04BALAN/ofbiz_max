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
public class TelecomNumber implements GenericValueObjectInterface {
public static final String module =TelecomNumber.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public TelecomNumber(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public TelecomNumber () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"contactNumber","Contact Number"},
{"createdTxStamp","Created Tx Stamp"},
{"areaCode","Area Code"},
{"createdStamp","Created Stamp"},
{"contactMechId","Contact Mech Id"},
{"askForName","Ask For Name"},
{"countryCode","Country Code"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.contactNumber = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.areaCode = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.contactMechId = "";
this.askForName = "";
this.countryCode = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.contactNumber = (java.lang.String) genVal.get("contactNumber");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.areaCode = (java.lang.String) genVal.get("areaCode");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.askForName = (java.lang.String) genVal.get("askForName");
this.countryCode = (java.lang.String) genVal.get("countryCode");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("contactNumber",OrderMaxUtility.getValidEntityString(this.contactNumber));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("areaCode",OrderMaxUtility.getValidEntityString(this.areaCode));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("askForName",OrderMaxUtility.getValidEntityString(this.askForName));
val.set("countryCode",OrderMaxUtility.getValidEntityString(this.countryCode));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("contactNumber",this.contactNumber);
valueMap.put("areaCode",this.areaCode);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("askForName",this.askForName);
valueMap.put("countryCode",this.countryCode);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("TelecomNumber");
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
private java.lang.String contactNumber;
public java.lang.String getcontactNumber() {
return contactNumber;
}
public void setcontactNumber( java.lang.String contactNumber ) {
this.contactNumber = contactNumber;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String areaCode;
public java.lang.String getareaCode() {
return areaCode;
}
public void setareaCode( java.lang.String areaCode ) {
this.areaCode = areaCode;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String askForName;
public java.lang.String getaskForName() {
return askForName;
}
public void setaskForName( java.lang.String askForName ) {
this.askForName = askForName;
}
private java.lang.String countryCode;
public java.lang.String getcountryCode() {
return countryCode;
}
public void setcountryCode( java.lang.String countryCode ) {
this.countryCode = countryCode;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<TelecomNumber> objectList = new ArrayList<TelecomNumber>();
        for (GenericValue genVal : genList) {
            objectList.add(new TelecomNumber(genVal));
        }
        return objectList;
    }    
}
