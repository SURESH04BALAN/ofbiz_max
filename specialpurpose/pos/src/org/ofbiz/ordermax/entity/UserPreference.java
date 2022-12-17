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
public class UserPreference implements GenericValueObjectInterface {
public static final String module =UserPreference.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public UserPreference(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public UserPreference () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"userPrefTypeId","User Pref Type Id"},
{"userPrefGroupTypeId","User Pref Group Type Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"userPrefValue","User Pref Value"},
{"userLoginId","User Login Id"},
{"userPrefDataType","User Pref Data Type"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.userPrefTypeId = "";
this.userPrefGroupTypeId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.userPrefValue = "";
this.userLoginId = "";
this.userPrefDataType = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.userPrefTypeId = (java.lang.String) genVal.get("userPrefTypeId");
this.userPrefGroupTypeId = (java.lang.String) genVal.get("userPrefGroupTypeId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.userPrefValue = (java.lang.String) genVal.get("userPrefValue");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.userPrefDataType = (java.lang.String) genVal.get("userPrefDataType");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("userPrefTypeId",OrderMaxUtility.getValidEntityString(this.userPrefTypeId));
val.set("userPrefGroupTypeId",OrderMaxUtility.getValidEntityString(this.userPrefGroupTypeId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("userPrefValue",OrderMaxUtility.getValidEntityString(this.userPrefValue));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("userPrefDataType",OrderMaxUtility.getValidEntityString(this.userPrefDataType));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("userPrefTypeId",this.userPrefTypeId);
valueMap.put("userPrefGroupTypeId",this.userPrefGroupTypeId);
valueMap.put("userPrefValue",this.userPrefValue);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("userPrefDataType",this.userPrefDataType);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("UserPreference");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String userPrefTypeId;
public java.lang.String getuserPrefTypeId() {
return userPrefTypeId;
}
public void setuserPrefTypeId( java.lang.String userPrefTypeId ) {
this.userPrefTypeId = userPrefTypeId;
}
private java.lang.String userPrefGroupTypeId;
public java.lang.String getuserPrefGroupTypeId() {
return userPrefGroupTypeId;
}
public void setuserPrefGroupTypeId( java.lang.String userPrefGroupTypeId ) {
this.userPrefGroupTypeId = userPrefGroupTypeId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
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
private java.lang.String userPrefValue;
public java.lang.String getuserPrefValue() {
return userPrefValue;
}
public void setuserPrefValue( java.lang.String userPrefValue ) {
this.userPrefValue = userPrefValue;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.lang.String userPrefDataType;
public java.lang.String getuserPrefDataType() {
return userPrefDataType;
}
public void setuserPrefDataType( java.lang.String userPrefDataType ) {
this.userPrefDataType = userPrefDataType;
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
        Collection<UserPreference> objectList = new ArrayList<UserPreference>();
        for (GenericValue genVal : genList) {
            objectList.add(new UserPreference(genVal));
        }
        return objectList;
    }    
}
