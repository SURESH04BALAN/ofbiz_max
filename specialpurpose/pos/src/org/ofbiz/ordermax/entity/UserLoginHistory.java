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
public class UserLoginHistory implements GenericValueObjectInterface {
public static final String module =UserLoginHistory.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public UserLoginHistory(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public UserLoginHistory () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"successfulLogin","Successful Login"},
{"thruDate","Thru Date"},
{"fromDate","From Date"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"partyId","Party Id"},
{"passwordUsed","Password Used"},
{"userLoginId","User Login Id"},
{"visitId","Visit Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.successfulLogin = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.fromDate = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.partyId = "";
this.passwordUsed = "";
this.userLoginId = "";
this.visitId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.successfulLogin = (java.lang.String) genVal.get("successfulLogin");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.partyId = (java.lang.String) genVal.get("partyId");
this.passwordUsed = (java.lang.String) genVal.get("passwordUsed");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.visitId = (java.lang.String) genVal.get("visitId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("successfulLogin",OrderMaxUtility.getValidEntityString(this.successfulLogin));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("passwordUsed",OrderMaxUtility.getValidEntityString(this.passwordUsed));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("successfulLogin",this.successfulLogin);
valueMap.put("thruDate",this.thruDate);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("passwordUsed",this.passwordUsed);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("visitId",this.visitId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("UserLoginHistory");
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
private java.lang.String successfulLogin;
public java.lang.String getsuccessfulLogin() {
return successfulLogin;
}
public void setsuccessfulLogin( java.lang.String successfulLogin ) {
this.successfulLogin = successfulLogin;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
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
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String passwordUsed;
public java.lang.String getpasswordUsed() {
return passwordUsed;
}
public void setpasswordUsed( java.lang.String passwordUsed ) {
this.passwordUsed = passwordUsed;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
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
        Collection<UserLoginHistory> objectList = new ArrayList<UserLoginHistory>();
        for (GenericValue genVal : genList) {
            objectList.add(new UserLoginHistory(genVal));
        }
        return objectList;
    }    
}
