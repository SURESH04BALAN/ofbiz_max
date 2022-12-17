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
public class PartyAndUserLogin implements GenericValueObjectInterface {
public static final String module =PartyAndUserLogin.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAndUserLogin(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAndUserLogin () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"enabled","Enabled"},
{"successiveFailedLogins","Successive Failed Logins"},
{"passwordHint","Password Hint"},
{"partyId","Party Id"},
{"userLoginId","User Login Id"},
{"disabledDateTime","Disabled Date Time"},
{"partyTypeId","Party Type Id"},
{"currentPassword","Current Password"},
};
protected void initObject(){
this.enabled = "";
this.successiveFailedLogins = new Long(0);
this.passwordHint = "";
this.partyId = "";
this.userLoginId = "";
this.disabledDateTime = UtilDateTime.nowTimestamp();
this.partyTypeId = "";
this.currentPassword = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.enabled = (java.lang.String) genVal.get("enabled");
this.successiveFailedLogins = (java.lang.Long) genVal.get("successiveFailedLogins");
this.passwordHint = (java.lang.String) genVal.get("passwordHint");
this.partyId = (java.lang.String) genVal.get("partyId");
this.userLoginId = (java.lang.String) genVal.get("userLoginId");
this.disabledDateTime = (java.sql.Timestamp) genVal.get("disabledDateTime");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.currentPassword = (java.lang.String) genVal.get("currentPassword");
}
protected void getGenericValue(GenericValue val) {
val.set("enabled",OrderMaxUtility.getValidEntityString(this.enabled));
val.set("successiveFailedLogins",this.successiveFailedLogins);
val.set("passwordHint",OrderMaxUtility.getValidEntityString(this.passwordHint));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("userLoginId",OrderMaxUtility.getValidEntityString(this.userLoginId));
val.set("disabledDateTime",OrderMaxUtility.getValidEntityTimestamp(this.disabledDateTime));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("currentPassword",OrderMaxUtility.getValidEntityString(this.currentPassword));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("enabled",this.enabled);
valueMap.put("successiveFailedLogins",this.successiveFailedLogins);
valueMap.put("passwordHint",this.passwordHint);
valueMap.put("partyId",this.partyId);
valueMap.put("userLoginId",this.userLoginId);
valueMap.put("disabledDateTime",this.disabledDateTime);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("currentPassword",this.currentPassword);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAndUserLogin");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String enabled;
public java.lang.String getenabled() {
return enabled;
}
public void setenabled( java.lang.String enabled ) {
this.enabled = enabled;
}
private java.lang.Long successiveFailedLogins;
public java.lang.Long getsuccessiveFailedLogins() {
return successiveFailedLogins;
}
public void setsuccessiveFailedLogins( java.lang.Long successiveFailedLogins ) {
this.successiveFailedLogins = successiveFailedLogins;
}
private java.lang.String passwordHint;
public java.lang.String getpasswordHint() {
return passwordHint;
}
public void setpasswordHint( java.lang.String passwordHint ) {
this.passwordHint = passwordHint;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String userLoginId;
public java.lang.String getuserLoginId() {
return userLoginId;
}
public void setuserLoginId( java.lang.String userLoginId ) {
this.userLoginId = userLoginId;
}
private java.sql.Timestamp disabledDateTime;
public java.sql.Timestamp getdisabledDateTime() {
return disabledDateTime;
}
public void setdisabledDateTime( java.sql.Timestamp disabledDateTime ) {
this.disabledDateTime = disabledDateTime;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAndUserLogin> objectList = new ArrayList<PartyAndUserLogin>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAndUserLogin(genVal));
        }
        return objectList;
    }    
}
