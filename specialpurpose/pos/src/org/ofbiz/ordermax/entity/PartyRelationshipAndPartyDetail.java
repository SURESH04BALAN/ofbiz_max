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
public class PartyRelationshipAndPartyDetail implements GenericValueObjectInterface {
public static final String module =PartyRelationshipAndPartyDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyRelationshipAndPartyDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyRelationshipAndPartyDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"toLastName","To Last Name"},
{"thruDate","Thru Date"},
{"fromGroupNameLocal","From Group Name Local"},
{"relationshipName","Relationship Name"},
{"partyRelationshipTypeId","Party Relationship Type Id"},
{"fromLastName","From Last Name"},
{"partyIdFrom","Party Id From"},
{"fromfirstNameLocal","Fromfirst Name Local"},
{"statusId","Status Id"},
{"roleTypeIdTo","Role Type Id To"},
{"toGroupNameLocal","To Group Name Local"},
{"description","Description"},
{"partyStatusId","Party Status Id"},
{"priorityTypeId","Priority Type Id"},
{"toGroupName","To Group Name"},
{"fromFirstName","From First Name"},
{"fromGroupName","From Group Name"},
{"tofirstNameLocal","Tofirst Name Local"},
{"toMiddleName","To Middle Name"},
{"permissionsEnumId","Permissions Enum Id"},
{"partyIdTo","Party Id To"},
{"toFirstName","To First Name"},
{"roleTypeIdFrom","Role Type Id From"},
{"fromPersonalTitle","From Personal Title"},
{"securityGroupId","Security Group Id"},
{"fromLastNameLocal","From Last Name Local"},
{"partyTypeId","Party Type Id"},
{"fromMiddleName","From Middle Name"},
{"toLastNameLocal","To Last Name Local"},
{"fromSuffix","From Suffix"},
{"toSuffix","To Suffix"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"toPersonalTitle","To Personal Title"},
{"positionTitle","Position Title"},
{"comments","Comments"},
};
protected void initObject(){
this.toLastName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.fromGroupNameLocal = "";
this.relationshipName = "";
this.partyRelationshipTypeId = "";
this.fromLastName = "";
this.partyIdFrom = "";
this.fromfirstNameLocal = "";
this.statusId = "";
this.roleTypeIdTo = "";
this.toGroupNameLocal = "";
this.description = "";
this.partyStatusId = "";
this.priorityTypeId = "";
this.toGroupName = "";
this.fromFirstName = "";
this.fromGroupName = "";
this.tofirstNameLocal = "";
this.toMiddleName = "";
this.permissionsEnumId = "";
this.partyIdTo = "";
this.toFirstName = "";
this.roleTypeIdFrom = "";
this.fromPersonalTitle = "";
this.securityGroupId = "";
this.fromLastNameLocal = "";
this.partyTypeId = "";
this.fromMiddleName = "";
this.toLastNameLocal = "";
this.fromSuffix = "";
this.toSuffix = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.toPersonalTitle = "";
this.positionTitle = "";
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.toLastName = (java.lang.String) genVal.get("toLastName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.fromGroupNameLocal = (java.lang.String) genVal.get("fromGroupNameLocal");
this.relationshipName = (java.lang.String) genVal.get("relationshipName");
this.partyRelationshipTypeId = (java.lang.String) genVal.get("partyRelationshipTypeId");
this.fromLastName = (java.lang.String) genVal.get("fromLastName");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.fromfirstNameLocal = (java.lang.String) genVal.get("fromfirstNameLocal");
this.statusId = (java.lang.String) genVal.get("statusId");
this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
this.toGroupNameLocal = (java.lang.String) genVal.get("toGroupNameLocal");
this.description = (java.lang.String) genVal.get("description");
this.partyStatusId = (java.lang.String) genVal.get("partyStatusId");
this.priorityTypeId = (java.lang.String) genVal.get("priorityTypeId");
this.toGroupName = (java.lang.String) genVal.get("toGroupName");
this.fromFirstName = (java.lang.String) genVal.get("fromFirstName");
this.fromGroupName = (java.lang.String) genVal.get("fromGroupName");
this.tofirstNameLocal = (java.lang.String) genVal.get("tofirstNameLocal");
this.toMiddleName = (java.lang.String) genVal.get("toMiddleName");
this.permissionsEnumId = (java.lang.String) genVal.get("permissionsEnumId");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.toFirstName = (java.lang.String) genVal.get("toFirstName");
this.roleTypeIdFrom = (java.lang.String) genVal.get("roleTypeIdFrom");
this.fromPersonalTitle = (java.lang.String) genVal.get("fromPersonalTitle");
this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
this.fromLastNameLocal = (java.lang.String) genVal.get("fromLastNameLocal");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.fromMiddleName = (java.lang.String) genVal.get("fromMiddleName");
this.toLastNameLocal = (java.lang.String) genVal.get("toLastNameLocal");
this.fromSuffix = (java.lang.String) genVal.get("fromSuffix");
this.toSuffix = (java.lang.String) genVal.get("toSuffix");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.toPersonalTitle = (java.lang.String) genVal.get("toPersonalTitle");
this.positionTitle = (java.lang.String) genVal.get("positionTitle");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("toLastName",OrderMaxUtility.getValidEntityString(this.toLastName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("fromGroupNameLocal",OrderMaxUtility.getValidEntityString(this.fromGroupNameLocal));
val.set("relationshipName",OrderMaxUtility.getValidEntityString(this.relationshipName));
val.set("partyRelationshipTypeId",OrderMaxUtility.getValidEntityString(this.partyRelationshipTypeId));
val.set("fromLastName",OrderMaxUtility.getValidEntityString(this.fromLastName));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("fromfirstNameLocal",OrderMaxUtility.getValidEntityString(this.fromfirstNameLocal));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("roleTypeIdTo",OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
val.set("toGroupNameLocal",OrderMaxUtility.getValidEntityString(this.toGroupNameLocal));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("partyStatusId",OrderMaxUtility.getValidEntityString(this.partyStatusId));
val.set("priorityTypeId",OrderMaxUtility.getValidEntityString(this.priorityTypeId));
val.set("toGroupName",OrderMaxUtility.getValidEntityString(this.toGroupName));
val.set("fromFirstName",OrderMaxUtility.getValidEntityString(this.fromFirstName));
val.set("fromGroupName",OrderMaxUtility.getValidEntityString(this.fromGroupName));
val.set("tofirstNameLocal",OrderMaxUtility.getValidEntityString(this.tofirstNameLocal));
val.set("toMiddleName",OrderMaxUtility.getValidEntityString(this.toMiddleName));
val.set("permissionsEnumId",OrderMaxUtility.getValidEntityString(this.permissionsEnumId));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("toFirstName",OrderMaxUtility.getValidEntityString(this.toFirstName));
val.set("roleTypeIdFrom",OrderMaxUtility.getValidEntityString(this.roleTypeIdFrom));
val.set("fromPersonalTitle",OrderMaxUtility.getValidEntityString(this.fromPersonalTitle));
val.set("securityGroupId",OrderMaxUtility.getValidEntityString(this.securityGroupId));
val.set("fromLastNameLocal",OrderMaxUtility.getValidEntityString(this.fromLastNameLocal));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("fromMiddleName",OrderMaxUtility.getValidEntityString(this.fromMiddleName));
val.set("toLastNameLocal",OrderMaxUtility.getValidEntityString(this.toLastNameLocal));
val.set("fromSuffix",OrderMaxUtility.getValidEntityString(this.fromSuffix));
val.set("toSuffix",OrderMaxUtility.getValidEntityString(this.toSuffix));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("toPersonalTitle",OrderMaxUtility.getValidEntityString(this.toPersonalTitle));
val.set("positionTitle",OrderMaxUtility.getValidEntityString(this.positionTitle));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("toLastName",this.toLastName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("fromGroupNameLocal",this.fromGroupNameLocal);
valueMap.put("relationshipName",this.relationshipName);
valueMap.put("partyRelationshipTypeId",this.partyRelationshipTypeId);
valueMap.put("fromLastName",this.fromLastName);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("fromfirstNameLocal",this.fromfirstNameLocal);
valueMap.put("statusId",this.statusId);
valueMap.put("roleTypeIdTo",this.roleTypeIdTo);
valueMap.put("toGroupNameLocal",this.toGroupNameLocal);
valueMap.put("description",this.description);
valueMap.put("partyStatusId",this.partyStatusId);
valueMap.put("priorityTypeId",this.priorityTypeId);
valueMap.put("toGroupName",this.toGroupName);
valueMap.put("fromFirstName",this.fromFirstName);
valueMap.put("fromGroupName",this.fromGroupName);
valueMap.put("tofirstNameLocal",this.tofirstNameLocal);
valueMap.put("toMiddleName",this.toMiddleName);
valueMap.put("permissionsEnumId",this.permissionsEnumId);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("toFirstName",this.toFirstName);
valueMap.put("roleTypeIdFrom",this.roleTypeIdFrom);
valueMap.put("fromPersonalTitle",this.fromPersonalTitle);
valueMap.put("securityGroupId",this.securityGroupId);
valueMap.put("fromLastNameLocal",this.fromLastNameLocal);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("fromMiddleName",this.fromMiddleName);
valueMap.put("toLastNameLocal",this.toLastNameLocal);
valueMap.put("fromSuffix",this.fromSuffix);
valueMap.put("toSuffix",this.toSuffix);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("toPersonalTitle",this.toPersonalTitle);
valueMap.put("positionTitle",this.positionTitle);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyRelationshipAndPartyDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String toLastName;
public java.lang.String gettoLastName() {
return toLastName;
}
public void settoLastName( java.lang.String toLastName ) {
this.toLastName = toLastName;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String fromGroupNameLocal;
public java.lang.String getfromGroupNameLocal() {
return fromGroupNameLocal;
}
public void setfromGroupNameLocal( java.lang.String fromGroupNameLocal ) {
this.fromGroupNameLocal = fromGroupNameLocal;
}
private java.lang.String relationshipName;
public java.lang.String getrelationshipName() {
return relationshipName;
}
public void setrelationshipName( java.lang.String relationshipName ) {
this.relationshipName = relationshipName;
}
private java.lang.String partyRelationshipTypeId;
public java.lang.String getpartyRelationshipTypeId() {
return partyRelationshipTypeId;
}
public void setpartyRelationshipTypeId( java.lang.String partyRelationshipTypeId ) {
this.partyRelationshipTypeId = partyRelationshipTypeId;
}
private java.lang.String fromLastName;
public java.lang.String getfromLastName() {
return fromLastName;
}
public void setfromLastName( java.lang.String fromLastName ) {
this.fromLastName = fromLastName;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String fromfirstNameLocal;
public java.lang.String getfromfirstNameLocal() {
return fromfirstNameLocal;
}
public void setfromfirstNameLocal( java.lang.String fromfirstNameLocal ) {
this.fromfirstNameLocal = fromfirstNameLocal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String roleTypeIdTo;
public java.lang.String getroleTypeIdTo() {
return roleTypeIdTo;
}
public void setroleTypeIdTo( java.lang.String roleTypeIdTo ) {
this.roleTypeIdTo = roleTypeIdTo;
}
private java.lang.String toGroupNameLocal;
public java.lang.String gettoGroupNameLocal() {
return toGroupNameLocal;
}
public void settoGroupNameLocal( java.lang.String toGroupNameLocal ) {
this.toGroupNameLocal = toGroupNameLocal;
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
private java.lang.String toGroupName;
public java.lang.String gettoGroupName() {
return toGroupName;
}
public void settoGroupName( java.lang.String toGroupName ) {
this.toGroupName = toGroupName;
}
private java.lang.String fromFirstName;
public java.lang.String getfromFirstName() {
return fromFirstName;
}
public void setfromFirstName( java.lang.String fromFirstName ) {
this.fromFirstName = fromFirstName;
}
private java.lang.String fromGroupName;
public java.lang.String getfromGroupName() {
return fromGroupName;
}
public void setfromGroupName( java.lang.String fromGroupName ) {
this.fromGroupName = fromGroupName;
}
private java.lang.String tofirstNameLocal;
public java.lang.String gettofirstNameLocal() {
return tofirstNameLocal;
}
public void settofirstNameLocal( java.lang.String tofirstNameLocal ) {
this.tofirstNameLocal = tofirstNameLocal;
}
private java.lang.String toMiddleName;
public java.lang.String gettoMiddleName() {
return toMiddleName;
}
public void settoMiddleName( java.lang.String toMiddleName ) {
this.toMiddleName = toMiddleName;
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
private java.lang.String toFirstName;
public java.lang.String gettoFirstName() {
return toFirstName;
}
public void settoFirstName( java.lang.String toFirstName ) {
this.toFirstName = toFirstName;
}
private java.lang.String roleTypeIdFrom;
public java.lang.String getroleTypeIdFrom() {
return roleTypeIdFrom;
}
public void setroleTypeIdFrom( java.lang.String roleTypeIdFrom ) {
this.roleTypeIdFrom = roleTypeIdFrom;
}
private java.lang.String fromPersonalTitle;
public java.lang.String getfromPersonalTitle() {
return fromPersonalTitle;
}
public void setfromPersonalTitle( java.lang.String fromPersonalTitle ) {
this.fromPersonalTitle = fromPersonalTitle;
}
private java.lang.String securityGroupId;
public java.lang.String getsecurityGroupId() {
return securityGroupId;
}
public void setsecurityGroupId( java.lang.String securityGroupId ) {
this.securityGroupId = securityGroupId;
}
private java.lang.String fromLastNameLocal;
public java.lang.String getfromLastNameLocal() {
return fromLastNameLocal;
}
public void setfromLastNameLocal( java.lang.String fromLastNameLocal ) {
this.fromLastNameLocal = fromLastNameLocal;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String fromMiddleName;
public java.lang.String getfromMiddleName() {
return fromMiddleName;
}
public void setfromMiddleName( java.lang.String fromMiddleName ) {
this.fromMiddleName = fromMiddleName;
}
private java.lang.String toLastNameLocal;
public java.lang.String gettoLastNameLocal() {
return toLastNameLocal;
}
public void settoLastNameLocal( java.lang.String toLastNameLocal ) {
this.toLastNameLocal = toLastNameLocal;
}
private java.lang.String fromSuffix;
public java.lang.String getfromSuffix() {
return fromSuffix;
}
public void setfromSuffix( java.lang.String fromSuffix ) {
this.fromSuffix = fromSuffix;
}
private java.lang.String toSuffix;
public java.lang.String gettoSuffix() {
return toSuffix;
}
public void settoSuffix( java.lang.String toSuffix ) {
this.toSuffix = toSuffix;
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
private java.lang.String toPersonalTitle;
public java.lang.String gettoPersonalTitle() {
return toPersonalTitle;
}
public void settoPersonalTitle( java.lang.String toPersonalTitle ) {
this.toPersonalTitle = toPersonalTitle;
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
        Collection<PartyRelationshipAndPartyDetail> objectList = new ArrayList<PartyRelationshipAndPartyDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyRelationshipAndPartyDetail(genVal));
        }
        return objectList;
    }    
}
