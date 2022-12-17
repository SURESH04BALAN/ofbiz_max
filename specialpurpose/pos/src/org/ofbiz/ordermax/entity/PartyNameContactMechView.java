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
public class PartyNameContactMechView implements GenericValueObjectInterface {
public static final String module =PartyNameContactMechView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyNameContactMechView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyNameContactMechView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"middleName","Middle Name"},
{"lastName","Last Name"},
{"groupName","Group Name"},
{"thruDate","Thru Date"},
{"firstNameLocal","First Name Local"},
{"contactMechId","Contact Mech Id"},
{"contactMechTypeId","Contact Mech Type Id"},
{"suffix","Suffix"},
{"personalTitle","Personal Title"},
{"partyTypeId","Party Type Id"},
{"groupNameLocal","Group Name Local"},
{"statusId","Status Id"},
{"lastNameLocal","Last Name Local"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"firstName","First Name"},
{"infoString","Info String"},
};
protected void initObject(){
this.middleName = "";
this.lastName = "";
this.groupName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.firstNameLocal = "";
this.contactMechId = "";
this.contactMechTypeId = "";
this.suffix = "";
this.personalTitle = "";
this.partyTypeId = "";
this.groupNameLocal = "";
this.statusId = "";
this.lastNameLocal = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.firstName = "";
this.infoString = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.middleName = (java.lang.String) genVal.get("middleName");
this.lastName = (java.lang.String) genVal.get("lastName");
this.groupName = (java.lang.String) genVal.get("groupName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.suffix = (java.lang.String) genVal.get("suffix");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.statusId = (java.lang.String) genVal.get("statusId");
this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.firstName = (java.lang.String) genVal.get("firstName");
this.infoString = (java.lang.String) genVal.get("infoString");
}
protected void getGenericValue(GenericValue val) {
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("firstNameLocal",OrderMaxUtility.getValidEntityString(this.firstNameLocal));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("lastNameLocal",OrderMaxUtility.getValidEntityString(this.lastNameLocal));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("middleName",this.middleName);
valueMap.put("lastName",this.lastName);
valueMap.put("groupName",this.groupName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("firstNameLocal",this.firstNameLocal);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("suffix",this.suffix);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("statusId",this.statusId);
valueMap.put("lastNameLocal",this.lastNameLocal);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("firstName",this.firstName);
valueMap.put("infoString",this.infoString);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyNameContactMechView");
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
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String firstNameLocal;
public java.lang.String getfirstNameLocal() {
return firstNameLocal;
}
public void setfirstNameLocal( java.lang.String firstNameLocal ) {
this.firstNameLocal = firstNameLocal;
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
private java.lang.String suffix;
public java.lang.String getsuffix() {
return suffix;
}
public void setsuffix( java.lang.String suffix ) {
this.suffix = suffix;
}
private java.lang.String personalTitle;
public java.lang.String getpersonalTitle() {
return personalTitle;
}
public void setpersonalTitle( java.lang.String personalTitle ) {
this.personalTitle = personalTitle;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String groupNameLocal;
public java.lang.String getgroupNameLocal() {
return groupNameLocal;
}
public void setgroupNameLocal( java.lang.String groupNameLocal ) {
this.groupNameLocal = groupNameLocal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String lastNameLocal;
public java.lang.String getlastNameLocal() {
return lastNameLocal;
}
public void setlastNameLocal( java.lang.String lastNameLocal ) {
this.lastNameLocal = lastNameLocal;
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
private java.lang.String firstName;
public java.lang.String getfirstName() {
return firstName;
}
public void setfirstName( java.lang.String firstName ) {
this.firstName = firstName;
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
        Collection<PartyNameContactMechView> objectList = new ArrayList<PartyNameContactMechView>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyNameContactMechView(genVal));
        }
        return objectList;
    }    
}
