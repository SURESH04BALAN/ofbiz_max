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
public class RequirementAndRole implements GenericValueObjectInterface {
public static final String module =RequirementAndRole.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RequirementAndRole(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RequirementAndRole () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruDate","Thru Date"},
{"requirementStartDate","Requirement Start Date"},
{"reason","Reason"},
{"facilityId","Facility Id"},
{"requirementId","Requirement Id"},
{"productId","Product Id"},
{"statusId","Status Id"},
{"roleTypeId","Role Type Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"fromDate","From Date"},
{"estimatedBudget","Estimated Budget"},
{"partyId","Party Id"},
{"description","Description"},
{"requiredByDate","Required By Date"},
{"requirementTypeId","Requirement Type Id"},
{"lastModifiedDate","Last Modified Date"},
{"quantity","Quantity"},
{"deliverableId","Deliverable Id"},
{"fixedAssetId","Fixed Asset Id"},
};
protected void initObject(){
this.thruDate = UtilDateTime.nowTimestamp();
this.requirementStartDate = UtilDateTime.nowTimestamp();
this.reason = "";
this.facilityId = "";
this.requirementId = "";
this.productId = "";
this.statusId = "";
this.roleTypeId = "";
this.lastModifiedByUserLogin = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.estimatedBudget = "";
this.partyId = "";
this.description = "";
this.requiredByDate = UtilDateTime.nowTimestamp();
this.requirementTypeId = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.deliverableId = "";
this.fixedAssetId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.requirementStartDate = (java.sql.Timestamp) genVal.get("requirementStartDate");
this.reason = (java.lang.String) genVal.get("reason");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.requirementId = (java.lang.String) genVal.get("requirementId");
this.productId = (java.lang.String) genVal.get("productId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.estimatedBudget = (java.lang.String) genVal.get("estimatedBudget");
this.partyId = (java.lang.String) genVal.get("partyId");
this.description = (java.lang.String) genVal.get("description");
this.requiredByDate = (java.sql.Timestamp) genVal.get("requiredByDate");
this.requirementTypeId = (java.lang.String) genVal.get("requirementTypeId");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.deliverableId = (java.lang.String) genVal.get("deliverableId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
}
protected void getGenericValue(GenericValue val) {
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("requirementStartDate",OrderMaxUtility.getValidEntityTimestamp(this.requirementStartDate));
val.set("reason",OrderMaxUtility.getValidEntityString(this.reason));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("requirementId",OrderMaxUtility.getValidEntityString(this.requirementId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("estimatedBudget",OrderMaxUtility.getValidEntityString(this.estimatedBudget));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("requiredByDate",OrderMaxUtility.getValidEntityTimestamp(this.requiredByDate));
val.set("requirementTypeId",OrderMaxUtility.getValidEntityString(this.requirementTypeId));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("deliverableId",OrderMaxUtility.getValidEntityString(this.deliverableId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruDate",this.thruDate);
valueMap.put("requirementStartDate",this.requirementStartDate);
valueMap.put("reason",this.reason);
valueMap.put("facilityId",this.facilityId);
valueMap.put("requirementId",this.requirementId);
valueMap.put("productId",this.productId);
valueMap.put("statusId",this.statusId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("fromDate",this.fromDate);
valueMap.put("estimatedBudget",this.estimatedBudget);
valueMap.put("partyId",this.partyId);
valueMap.put("description",this.description);
valueMap.put("requiredByDate",this.requiredByDate);
valueMap.put("requirementTypeId",this.requirementTypeId);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("quantity",this.quantity);
valueMap.put("deliverableId",this.deliverableId);
valueMap.put("fixedAssetId",this.fixedAssetId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RequirementAndRole");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp requirementStartDate;
public java.sql.Timestamp getrequirementStartDate() {
return requirementStartDate;
}
public void setrequirementStartDate( java.sql.Timestamp requirementStartDate ) {
this.requirementStartDate = requirementStartDate;
}
private java.lang.String reason;
public java.lang.String getreason() {
return reason;
}
public void setreason( java.lang.String reason ) {
this.reason = reason;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String requirementId;
public java.lang.String getrequirementId() {
return requirementId;
}
public void setrequirementId( java.lang.String requirementId ) {
this.requirementId = requirementId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String estimatedBudget;
public java.lang.String getestimatedBudget() {
return estimatedBudget;
}
public void setestimatedBudget( java.lang.String estimatedBudget ) {
this.estimatedBudget = estimatedBudget;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Timestamp requiredByDate;
public java.sql.Timestamp getrequiredByDate() {
return requiredByDate;
}
public void setrequiredByDate( java.sql.Timestamp requiredByDate ) {
this.requiredByDate = requiredByDate;
}
private java.lang.String requirementTypeId;
public java.lang.String getrequirementTypeId() {
return requirementTypeId;
}
public void setrequirementTypeId( java.lang.String requirementTypeId ) {
this.requirementTypeId = requirementTypeId;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String deliverableId;
public java.lang.String getdeliverableId() {
return deliverableId;
}
public void setdeliverableId( java.lang.String deliverableId ) {
this.deliverableId = deliverableId;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RequirementAndRole> objectList = new ArrayList<RequirementAndRole>();
        for (GenericValue genVal : genList) {
            objectList.add(new RequirementAndRole(genVal));
        }
        return objectList;
    }    
}
