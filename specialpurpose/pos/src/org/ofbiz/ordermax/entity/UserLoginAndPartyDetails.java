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
public class UserLoginAndPartyDetails implements GenericValueObjectInterface {
public static final String module =UserLoginAndPartyDetails.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public UserLoginAndPartyDetails(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public UserLoginAndPartyDetails () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"middleName","Middle Name"},
{"lastName","Last Name"},
{"successiveFailedLogins","Successive Failed Logins"},
{"enabled","Enabled"},
{"groupName","Group Name"},
{"passwordHint","Password Hint"},
{"userLoginId","User Login Id"},
{"partyTypeId","Party Type Id"},
{"currentPassword","Current Password"},
{"statusId","Status Id"},
{"partyId","Party Id"},
{"disabledDateTime","Disabled Date Time"},
{"firstName","First Name"},
{"createdDate","Created Date"},
};
protected void initObject(){
this.middleName = "";
this.lastName = "";
this.successiveFailedLogins = new Long(0);
this.enabled = "";
this.groupName = "";
this.passwordHint = "";
this.userLoginId = "";
this.partyTypeId = "";
this.currentPassword = "";
this.statusId = "";
this.partyId = "";
this.disabledDateTime = UtilDateTime.nowTimestamp();
this.firstName = "";
this.createdDate = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.middleName = (java.lang.String) genVal.get("middleName");
this.lastName = (java.lang.String) genVal.get("lastName");
this.successiveFailedLogins = (java.lang.Long) genVal.get("successiveFailedLogins");
this.enabled = (java.lang.String) genVal.get("enabled");
this.groupName = (java.lang.String) genVal.get("groupName");
this.passwordHint = (java.lang.String) genVal.get("passwordHint");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.currentPassword = (java.lang.String) genVal.get("currentPassword");
this.statusId = (java.lang.String) genVal.get("statusId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.disabledDateTime = (java.sql.Timestamp) genVal.get("disabledDateTime");
this.firstName = (java.lang.String) genVal.get("firstName");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
}
protected void getGenericValue(GenericValue val) {
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("successiveFailedLogins",this.successiveFailedLogins);
val.set("enabled",OrderMaxUtility.getValidEntityString(this.enabled));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("passwordHint",OrderMaxUtility.getValidEntityString(this.passwordHint));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("currentPassword",OrderMaxUtility.getValidEntityString(this.currentPassword));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("disabledDateTime",OrderMaxUtility.getValidEntityTimestamp(this.disabledDateTime));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("middleName",this.middleName);
valueMap.put("lastName",this.lastName);
valueMap.put("successiveFailedLogins",this.successiveFailedLogins);
valueMap.put("enabled",this.enabled);
valueMap.put("groupName",this.groupName);
valueMap.put("passwordHint",this.passwordHint);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("currentPassword",this.currentPassword);
valueMap.put("statusId",this.statusId);
valueMap.put("partyId",this.partyId);
valueMap.put("disabledDateTime",this.disabledDateTime);
valueMap.put("firstName",this.firstName);
valueMap.put("createdDate",this.createdDate);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("UserLoginAndPartyDetails");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
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
private java.lang.Long successiveFailedLogins;
public java.lang.Long getsuccessiveFailedLogins() {
return successiveFailedLogins;
}
public void setsuccessiveFailedLogins( java.lang.Long successiveFailedLogins ) {
this.successiveFailedLogins = successiveFailedLogins;
}
private java.lang.String enabled;
public java.lang.String getenabled() {
return enabled;
}
public void setenabled( java.lang.String enabled ) {
this.enabled = enabled;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.lang.String passwordHint;
public java.lang.String getpasswordHint() {
return passwordHint;
}
public void setpasswordHint( java.lang.String passwordHint ) {
this.passwordHint = passwordHint;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String currentPassword;
public java.lang.String getcurrentPassword() {
return currentPassword;
}
public void setcurrentPassword( java.lang.String currentPassword ) {
this.currentPassword = currentPassword;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.sql.Timestamp disabledDateTime;
public java.sql.Timestamp getdisabledDateTime() {
return disabledDateTime;
}
public void setdisabledDateTime( java.sql.Timestamp disabledDateTime ) {
this.disabledDateTime = disabledDateTime;
}
private java.lang.String firstName;
public java.lang.String getfirstName() {
return firstName;
}
public void setfirstName( java.lang.String firstName ) {
this.firstName = firstName;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<UserLoginAndPartyDetails> objectList = new ArrayList<UserLoginAndPartyDetails>();
        for (GenericValue genVal : genList) {
            objectList.add(new UserLoginAndPartyDetails(genVal));
        }
        return objectList;
    }    
}
