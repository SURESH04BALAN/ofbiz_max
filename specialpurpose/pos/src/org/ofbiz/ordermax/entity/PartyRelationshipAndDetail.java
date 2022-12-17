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
public class PartyRelationshipAndDetail implements GenericValueObjectInterface {
public static final String module =PartyRelationshipAndDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyRelationshipAndDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyRelationshipAndDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"groupName","Group Name"},
{"thruDate","Thru Date"},
{"partyRelationshipTypeId","Party Relationship Type Id"},
{"relationshipName","Relationship Name"},
{"partyIdFrom","Party Id From"},
{"statusId","Status Id"},
{"groupNameLocal","Group Name Local"},
{"roleTypeIdTo","Role Type Id To"},
{"lastNameLocal","Last Name Local"},
{"description","Description"},
{"partyStatusId","Party Status Id"},
{"priorityTypeId","Priority Type Id"},
{"firstName","First Name"},
{"middleName","Middle Name"},
{"lastName","Last Name"},
{"permissionsEnumId","Permissions Enum Id"},
{"partyIdTo","Party Id To"},
{"firstNameLocal","First Name Local"},
{"roleTypeIdFrom","Role Type Id From"},
{"securityGroupId","Security Group Id"},
{"partyTypeId","Party Type Id"},
{"personalTitle","Personal Title"},
{"suffix","Suffix"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"positionTitle","Position Title"},
{"comments","Comments"},
};
protected void initObject(){
this.groupName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.partyRelationshipTypeId = "";
this.relationshipName = "";
this.partyIdFrom = "";
this.statusId = "";
this.groupNameLocal = "";
this.roleTypeIdTo = "";
this.lastNameLocal = "";
this.description = "";
this.partyStatusId = "";
this.priorityTypeId = "";
this.firstName = "";
this.middleName = "";
this.lastName = "";
this.permissionsEnumId = "";
this.partyIdTo = "";
this.firstNameLocal = "";
this.roleTypeIdFrom = "";
this.securityGroupId = "";
this.partyTypeId = "";
this.personalTitle = "";
this.suffix = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.positionTitle = "";
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.groupName = (java.lang.String) genVal.get("groupName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.partyRelationshipTypeId = (java.lang.String) genVal.get("partyRelationshipTypeId");
this.relationshipName = (java.lang.String) genVal.get("relationshipName");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.statusId = (java.lang.String) genVal.get("statusId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
this.description = (java.lang.String) genVal.get("description");
this.partyStatusId = (java.lang.String) genVal.get("partyStatusId");
this.priorityTypeId = (java.lang.String) genVal.get("priorityTypeId");
this.firstName = (java.lang.String) genVal.get("firstName");
this.middleName = (java.lang.String) genVal.get("middleName");
this.lastName = (java.lang.String) genVal.get("lastName");
this.permissionsEnumId = (java.lang.String) genVal.get("permissionsEnumId");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
this.roleTypeIdFrom = (java.lang.String) genVal.get("roleTypeIdFrom");
this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.suffix = (java.lang.String) genVal.get("suffix");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.positionTitle = (java.lang.String) genVal.get("positionTitle");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("partyRelationshipTypeId",OrderMaxUtility.getValidEntityString(this.partyRelationshipTypeId));
val.set("relationshipName",OrderMaxUtility.getValidEntityString(this.relationshipName));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("roleTypeIdTo",OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
val.set("lastNameLocal",OrderMaxUtility.getValidEntityString(this.lastNameLocal));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("partyStatusId",OrderMaxUtility.getValidEntityString(this.partyStatusId));
val.set("priorityTypeId",OrderMaxUtility.getValidEntityString(this.priorityTypeId));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("permissionsEnumId",OrderMaxUtility.getValidEntityString(this.permissionsEnumId));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("firstNameLocal",OrderMaxUtility.getValidEntityString(this.firstNameLocal));
val.set("roleTypeIdFrom",OrderMaxUtility.getValidEntityString(this.roleTypeIdFrom));
val.set("securityGroupId",OrderMaxUtility.getValidEntityString(this.securityGroupId));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("positionTitle",OrderMaxUtility.getValidEntityString(this.positionTitle));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("groupName",this.groupName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("partyRelationshipTypeId",this.partyRelationshipTypeId);
valueMap.put("relationshipName",this.relationshipName);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("statusId",this.statusId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("roleTypeIdTo",this.roleTypeIdTo);
valueMap.put("lastNameLocal",this.lastNameLocal);
valueMap.put("description",this.description);
valueMap.put("partyStatusId",this.partyStatusId);
valueMap.put("priorityTypeId",this.priorityTypeId);
valueMap.put("firstName",this.firstName);
valueMap.put("middleName",this.middleName);
valueMap.put("lastName",this.lastName);
valueMap.put("permissionsEnumId",this.permissionsEnumId);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("firstNameLocal",this.firstNameLocal);
valueMap.put("roleTypeIdFrom",this.roleTypeIdFrom);
valueMap.put("securityGroupId",this.securityGroupId);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("suffix",this.suffix);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("positionTitle",this.positionTitle);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyRelationshipAndDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
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
private java.lang.String partyRelationshipTypeId;
public java.lang.String getpartyRelationshipTypeId() {
return partyRelationshipTypeId;
}
public void setpartyRelationshipTypeId( java.lang.String partyRelationshipTypeId ) {
this.partyRelationshipTypeId = partyRelationshipTypeId;
}
private java.lang.String relationshipName;
public java.lang.String getrelationshipName() {
return relationshipName;
}
public void setrelationshipName( java.lang.String relationshipName ) {
this.relationshipName = relationshipName;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String groupNameLocal;
public java.lang.String getgroupNameLocal() {
return groupNameLocal;
}
public void setgroupNameLocal( java.lang.String groupNameLocal ) {
this.groupNameLocal = groupNameLocal;
}
private java.lang.String roleTypeIdTo;
public java.lang.String getroleTypeIdTo() {
return roleTypeIdTo;
}
public void setroleTypeIdTo( java.lang.String roleTypeIdTo ) {
this.roleTypeIdTo = roleTypeIdTo;
}
private java.lang.String lastNameLocal;
public java.lang.String getlastNameLocal() {
return lastNameLocal;
}
public void setlastNameLocal( java.lang.String lastNameLocal ) {
this.lastNameLocal = lastNameLocal;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String partyStatusId;
public java.lang.String getpartyStatusId() {
return partyStatusId;
}
public void setpartyStatusId( java.lang.String partyStatusId ) {
this.partyStatusId = partyStatusId;
}
private java.lang.String priorityTypeId;
public java.lang.String getpriorityTypeId() {
return priorityTypeId;
}
public void setpriorityTypeId( java.lang.String priorityTypeId ) {
this.priorityTypeId = priorityTypeId;
}
private java.lang.String firstName;
public java.lang.String getfirstName() {
return firstName;
}
public void setfirstName( java.lang.String firstName ) {
this.firstName = firstName;
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
private java.lang.String permissionsEnumId;
public java.lang.String getpermissionsEnumId() {
return permissionsEnumId;
}
public void setpermissionsEnumId( java.lang.String permissionsEnumId ) {
this.permissionsEnumId = permissionsEnumId;
}
private java.lang.String partyIdTo;
public java.lang.String getpartyIdTo() {
return partyIdTo;
}
public void setpartyIdTo( java.lang.String partyIdTo ) {
this.partyIdTo = partyIdTo;
}
private java.lang.String firstNameLocal;
public java.lang.String getfirstNameLocal() {
return firstNameLocal;
}
public void setfirstNameLocal( java.lang.String firstNameLocal ) {
this.firstNameLocal = firstNameLocal;
}
private java.lang.String roleTypeIdFrom;
public java.lang.String getroleTypeIdFrom() {
return roleTypeIdFrom;
}
public void setroleTypeIdFrom( java.lang.String roleTypeIdFrom ) {
this.roleTypeIdFrom = roleTypeIdFrom;
}
private java.lang.String securityGroupId;
public java.lang.String getsecurityGroupId() {
return securityGroupId;
}
public void setsecurityGroupId( java.lang.String securityGroupId ) {
this.securityGroupId = securityGroupId;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String personalTitle;
public java.lang.String getpersonalTitle() {
return personalTitle;
}
public void setpersonalTitle( java.lang.String personalTitle ) {
this.personalTitle = personalTitle;
}
private java.lang.String suffix;
public java.lang.String getsuffix() {
return suffix;
}
public void setsuffix( java.lang.String suffix ) {
this.suffix = suffix;
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
private java.lang.String positionTitle;
public java.lang.String getpositionTitle() {
return positionTitle;
}
public void setpositionTitle( java.lang.String positionTitle ) {
this.positionTitle = positionTitle;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyRelationshipAndDetail> objectList = new ArrayList<PartyRelationshipAndDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyRelationshipAndDetail(genVal));
        }
        return objectList;
    }    
}
