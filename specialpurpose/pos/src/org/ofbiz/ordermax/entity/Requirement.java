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
public class Requirement implements GenericValueObjectInterface {
public static final String module =Requirement.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public Requirement(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public Requirement () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"requirementStartDate","Requirement Start Date"},
{"reason","Reason"},
{"facilityId","Facility Id"},
{"requirementId","Requirement Id"},
{"productId","Product Id"},
{"statusId","Status Id"},
{"useCase","Use Case"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"createdTxStamp","Created Tx Stamp"},
{"estimatedBudget","Estimated Budget"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"requiredByDate","Required By Date"},
{"requirementTypeId","Requirement Type Id"},
{"lastModifiedDate","Last Modified Date"},
{"quantity","Quantity"},
{"deliverableId","Deliverable Id"},
{"fixedAssetId","Fixed Asset Id"},
{"createdDate","Created Date"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.requirementStartDate = UtilDateTime.nowTimestamp();
this.reason = "";
this.facilityId = "";
this.requirementId = "";
this.productId = "";
this.statusId = "";
this.useCase = "";
this.lastModifiedByUserLogin = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.estimatedBudget = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.requiredByDate = UtilDateTime.nowTimestamp();
this.requirementTypeId = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.deliverableId = "";
this.fixedAssetId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.requirementStartDate = (java.sql.Timestamp) genVal.get("requirementStartDate");
this.reason = (java.lang.String) genVal.get("reason");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.requirementId = (java.lang.String) genVal.get("requirementId");
this.productId = (java.lang.String) genVal.get("productId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.useCase = (java.lang.String) genVal.get("useCase");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.estimatedBudget = (java.lang.String) genVal.get("estimatedBudget");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.requiredByDate = (java.sql.Timestamp) genVal.get("requiredByDate");
this.requirementTypeId = (java.lang.String) genVal.get("requirementTypeId");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.deliverableId = (java.lang.String) genVal.get("deliverableId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("requirementStartDate",OrderMaxUtility.getValidEntityTimestamp(this.requirementStartDate));
val.set("reason",OrderMaxUtility.getValidEntityString(this.reason));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("requirementId",OrderMaxUtility.getValidEntityString(this.requirementId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("useCase",OrderMaxUtility.getValidEntityString(this.useCase));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("estimatedBudget",OrderMaxUtility.getValidEntityString(this.estimatedBudget));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("requiredByDate",OrderMaxUtility.getValidEntityTimestamp(this.requiredByDate));
val.set("requirementTypeId",OrderMaxUtility.getValidEntityString(this.requirementTypeId));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("deliverableId",OrderMaxUtility.getValidEntityString(this.deliverableId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("requirementStartDate",this.requirementStartDate);
valueMap.put("reason",this.reason);
valueMap.put("facilityId",this.facilityId);
valueMap.put("requirementId",this.requirementId);
valueMap.put("productId",this.productId);
valueMap.put("statusId",this.statusId);
valueMap.put("useCase",this.useCase);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("estimatedBudget",this.estimatedBudget);
valueMap.put("description",this.description);
valueMap.put("requiredByDate",this.requiredByDate);
valueMap.put("requirementTypeId",this.requirementTypeId);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("quantity",this.quantity);
valueMap.put("deliverableId",this.deliverableId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("Requirement");
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
private java.lang.String useCase;
public java.lang.String getuseCase() {
return useCase;
}
public void setuseCase( java.lang.String useCase ) {
this.useCase = useCase;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String estimatedBudget;
public java.lang.String getestimatedBudget() {
return estimatedBudget;
}
public void setestimatedBudget( java.lang.String estimatedBudget ) {
this.estimatedBudget = estimatedBudget;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
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
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Requirement> objectList = new ArrayList<Requirement>();
        for (GenericValue genVal : genList) {
            objectList.add(new Requirement(genVal));
        }
        return objectList;
    }    
}
