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
public class WorkEffortAssocFromView implements GenericValueObjectInterface {
public static final String module =WorkEffortAssocFromView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public WorkEffortAssocFromView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public WorkEffortAssocFromView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"reservNthPPPerc","Reserv Nth Pp Perc"},
{"workEffortIdTo","Work Effort Id To"},
{"runtimeDataId","Runtime Data Id"},
{"priority","Priority"},
{"description","Description"},
{"estimatedCompletionDate","Estimated Completion Date"},
{"actualStartDate","Actual Start Date"},
{"quantityRejected","Quantity Rejected"},
{"estimatedSetupMillis","Estimated Setup Millis"},
{"lastStatusUpdate","Last Status Update"},
{"createdDate","Created Date"},
{"estimatedMilliSeconds","Estimated Milli Seconds"},
{"tempExprId","Temp Expr Id"},
{"totalMoneyAllowed","Total Money Allowed"},
{"showAsEnumId","Show As Enum Id"},
{"timeTransparency","Time Transparency"},
{"noteId","Note Id"},
{"facilityId","Facility Id"},
{"moneyUomId","Money Uom Id"},
{"currentStatusId","Current Status Id"},
{"workEffortId","Work Effort Id"},
{"workEffortParentId","Work Effort Parent Id"},
{"workEffortIdFrom","Work Effort Id From"},
{"specialTerms","Special Terms"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"accommodationMapId","Accommodation Map Id"},
{"thruDate","Thru Date"},
{"workEffortName","Work Effort Name"},
{"reservPersons","Reserv Persons"},
{"quantityProduced","Quantity Produced"},
{"serviceLoaderName","Service Loader Name"},
{"workEffortPurposeTypeId","Work Effort Purpose Type Id"},
{"estimatedStartDate","Estimated Start Date"},
{"accommodationSpotId","Accommodation Spot Id"},
{"scopeEnumId","Scope Enum Id"},
{"reserv2ndPPPerc","Reserv 2Nd Pp Perc"},
{"workEffortAssocTypeId","Work Effort Assoc Type Id"},
{"fixedAssetId","Fixed Asset Id"},
{"quantityToProduce","Quantity To Produce"},
{"universalId","Universal Id"},
{"percentComplete","Percent Complete"},
{"locationDesc","Location Desc"},
{"revisionNumber","Revision Number"},
{"workEffortTypeId","Work Effort Type Id"},
{"actualSetupMillis","Actual Setup Millis"},
{"actualMilliSeconds","Actual Milli Seconds"},
{"sequenceNum","Sequence Num"},
{"estimateCalcMethod","Estimate Calc Method"},
{"sendNotificationEmail","Send Notification Email"},
{"actualCompletionDate","Actual Completion Date"},
{"lastModifiedDate","Last Modified Date"},
{"sourceReferenceId","Source Reference Id"},
{"infoUrl","Info Url"},
{"totalMilliSecondsAllowed","Total Milli Seconds Allowed"},
{"recurrenceInfoId","Recurrence Info Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.reservNthPPPerc = "";
this.workEffortIdTo = "";
this.runtimeDataId = "";
this.priority = "";
this.description = "";
this.estimatedCompletionDate = UtilDateTime.nowTimestamp();
this.actualStartDate = UtilDateTime.nowTimestamp();
this.quantityRejected = "";
this.estimatedSetupMillis = "";
this.lastStatusUpdate = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.estimatedMilliSeconds = "";
this.tempExprId = "";
this.totalMoneyAllowed = "";
this.showAsEnumId = "";
this.timeTransparency = "";
this.noteId = "";
this.facilityId = "";
this.moneyUomId = "";
this.currentStatusId = "";
this.workEffortId = "";
this.workEffortParentId = "";
this.workEffortIdFrom = "";
this.specialTerms = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.accommodationMapId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.workEffortName = "";
this.reservPersons = "";
this.quantityProduced = "";
this.serviceLoaderName = "";
this.workEffortPurposeTypeId = "";
this.estimatedStartDate = UtilDateTime.nowTimestamp();
this.accommodationSpotId = "";
this.scopeEnumId = "";
this.reserv2ndPPPerc = "";
this.workEffortAssocTypeId = "";
this.fixedAssetId = "";
this.quantityToProduce = java.math.BigDecimal.ZERO;
this.universalId = "";
this.percentComplete = "";
this.locationDesc = "";
this.revisionNumber = new Long(0);
this.workEffortTypeId = "";
this.actualSetupMillis = "";
this.actualMilliSeconds = "";
this.sequenceNum = new Long(0);
this.estimateCalcMethod = "";
this.sendNotificationEmail = "";
this.actualCompletionDate = UtilDateTime.nowTimestamp();
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.sourceReferenceId = "";
this.infoUrl = "";
this.totalMilliSecondsAllowed = "";
this.recurrenceInfoId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.reservNthPPPerc = (java.lang.String) genVal.get("reservNthPPPerc");
this.workEffortIdTo = (java.lang.String) genVal.get("workEffortIdTo");
this.runtimeDataId = (java.lang.String) genVal.get("runtimeDataId");
this.priority = (java.lang.String) genVal.get("priority");
this.description = (java.lang.String) genVal.get("description");
this.estimatedCompletionDate = (java.sql.Timestamp) genVal.get("estimatedCompletionDate");
this.actualStartDate = (java.sql.Timestamp) genVal.get("actualStartDate");
this.quantityRejected = (java.lang.String) genVal.get("quantityRejected");
this.estimatedSetupMillis = (java.lang.String) genVal.get("estimatedSetupMillis");
this.lastStatusUpdate = (java.lang.String) genVal.get("lastStatusUpdate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.estimatedMilliSeconds = (java.lang.String) genVal.get("estimatedMilliSeconds");
this.tempExprId = (java.lang.String) genVal.get("tempExprId");
this.totalMoneyAllowed = (java.lang.String) genVal.get("totalMoneyAllowed");
this.showAsEnumId = (java.lang.String) genVal.get("showAsEnumId");
this.timeTransparency = (java.lang.String) genVal.get("timeTransparency");
this.noteId = (java.lang.String) genVal.get("noteId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.moneyUomId = (java.lang.String) genVal.get("moneyUomId");
this.currentStatusId = (java.lang.String) genVal.get("currentStatusId");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.workEffortParentId = (java.lang.String) genVal.get("workEffortParentId");
this.workEffortIdFrom = (java.lang.String) genVal.get("workEffortIdFrom");
this.specialTerms = (java.lang.String) genVal.get("specialTerms");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.accommodationMapId = (java.lang.String) genVal.get("accommodationMapId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.workEffortName = (java.lang.String) genVal.get("workEffortName");
this.reservPersons = (java.lang.String) genVal.get("reservPersons");
this.quantityProduced = (java.lang.String) genVal.get("quantityProduced");
this.serviceLoaderName = (java.lang.String) genVal.get("serviceLoaderName");
this.workEffortPurposeTypeId = (java.lang.String) genVal.get("workEffortPurposeTypeId");
this.estimatedStartDate = (java.sql.Timestamp) genVal.get("estimatedStartDate");
this.accommodationSpotId = (java.lang.String) genVal.get("accommodationSpotId");
this.scopeEnumId = (java.lang.String) genVal.get("scopeEnumId");
this.reserv2ndPPPerc = (java.lang.String) genVal.get("reserv2ndPPPerc");
this.workEffortAssocTypeId = (java.lang.String) genVal.get("workEffortAssocTypeId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.quantityToProduce = (java.math.BigDecimal) genVal.get("quantityToProduce");
this.universalId = (java.lang.String) genVal.get("universalId");
this.percentComplete = (java.lang.String) genVal.get("percentComplete");
this.locationDesc = (java.lang.String) genVal.get("locationDesc");
this.revisionNumber = (java.lang.Long) genVal.get("revisionNumber");
this.workEffortTypeId = (java.lang.String) genVal.get("workEffortTypeId");
this.actualSetupMillis = (java.lang.String) genVal.get("actualSetupMillis");
this.actualMilliSeconds = (java.lang.String) genVal.get("actualMilliSeconds");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
this.estimateCalcMethod = (java.lang.String) genVal.get("estimateCalcMethod");
this.sendNotificationEmail = (java.lang.String) genVal.get("sendNotificationEmail");
this.actualCompletionDate = (java.sql.Timestamp) genVal.get("actualCompletionDate");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.sourceReferenceId = (java.lang.String) genVal.get("sourceReferenceId");
this.infoUrl = (java.lang.String) genVal.get("infoUrl");
this.totalMilliSecondsAllowed = (java.lang.String) genVal.get("totalMilliSecondsAllowed");
this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("reservNthPPPerc",OrderMaxUtility.getValidEntityString(this.reservNthPPPerc));
val.set("workEffortIdTo",OrderMaxUtility.getValidEntityString(this.workEffortIdTo));
val.set("runtimeDataId",OrderMaxUtility.getValidEntityString(this.runtimeDataId));
val.set("priority",OrderMaxUtility.getValidEntityString(this.priority));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("estimatedCompletionDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedCompletionDate));
val.set("actualStartDate",OrderMaxUtility.getValidEntityTimestamp(this.actualStartDate));
val.set("quantityRejected",OrderMaxUtility.getValidEntityString(this.quantityRejected));
val.set("estimatedSetupMillis",OrderMaxUtility.getValidEntityString(this.estimatedSetupMillis));
val.set("lastStatusUpdate",OrderMaxUtility.getValidEntityString(this.lastStatusUpdate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("estimatedMilliSeconds",OrderMaxUtility.getValidEntityString(this.estimatedMilliSeconds));
val.set("tempExprId",OrderMaxUtility.getValidEntityString(this.tempExprId));
val.set("totalMoneyAllowed",OrderMaxUtility.getValidEntityString(this.totalMoneyAllowed));
val.set("showAsEnumId",OrderMaxUtility.getValidEntityString(this.showAsEnumId));
val.set("timeTransparency",OrderMaxUtility.getValidEntityString(this.timeTransparency));
val.set("noteId",OrderMaxUtility.getValidEntityString(this.noteId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("moneyUomId",OrderMaxUtility.getValidEntityString(this.moneyUomId));
val.set("currentStatusId",OrderMaxUtility.getValidEntityString(this.currentStatusId));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("workEffortParentId",OrderMaxUtility.getValidEntityString(this.workEffortParentId));
val.set("workEffortIdFrom",OrderMaxUtility.getValidEntityString(this.workEffortIdFrom));
val.set("specialTerms",OrderMaxUtility.getValidEntityString(this.specialTerms));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("accommodationMapId",OrderMaxUtility.getValidEntityString(this.accommodationMapId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("workEffortName",OrderMaxUtility.getValidEntityString(this.workEffortName));
val.set("reservPersons",OrderMaxUtility.getValidEntityString(this.reservPersons));
val.set("quantityProduced",OrderMaxUtility.getValidEntityString(this.quantityProduced));
val.set("serviceLoaderName",OrderMaxUtility.getValidEntityString(this.serviceLoaderName));
val.set("workEffortPurposeTypeId",OrderMaxUtility.getValidEntityString(this.workEffortPurposeTypeId));
val.set("estimatedStartDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedStartDate));
val.set("accommodationSpotId",OrderMaxUtility.getValidEntityString(this.accommodationSpotId));
val.set("scopeEnumId",OrderMaxUtility.getValidEntityString(this.scopeEnumId));
val.set("reserv2ndPPPerc",OrderMaxUtility.getValidEntityString(this.reserv2ndPPPerc));
val.set("workEffortAssocTypeId",OrderMaxUtility.getValidEntityString(this.workEffortAssocTypeId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("quantityToProduce",OrderMaxUtility.getValidEntityBigDecimal(this.quantityToProduce));
val.set("universalId",OrderMaxUtility.getValidEntityString(this.universalId));
val.set("percentComplete",OrderMaxUtility.getValidEntityString(this.percentComplete));
val.set("locationDesc",OrderMaxUtility.getValidEntityString(this.locationDesc));
val.set("revisionNumber",this.revisionNumber);
val.set("workEffortTypeId",OrderMaxUtility.getValidEntityString(this.workEffortTypeId));
val.set("actualSetupMillis",OrderMaxUtility.getValidEntityString(this.actualSetupMillis));
val.set("actualMilliSeconds",OrderMaxUtility.getValidEntityString(this.actualMilliSeconds));
val.set("sequenceNum",this.sequenceNum);
val.set("estimateCalcMethod",OrderMaxUtility.getValidEntityString(this.estimateCalcMethod));
val.set("sendNotificationEmail",OrderMaxUtility.getValidEntityString(this.sendNotificationEmail));
val.set("actualCompletionDate",OrderMaxUtility.getValidEntityTimestamp(this.actualCompletionDate));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("sourceReferenceId",OrderMaxUtility.getValidEntityString(this.sourceReferenceId));
val.set("infoUrl",OrderMaxUtility.getValidEntityString(this.infoUrl));
val.set("totalMilliSecondsAllowed",OrderMaxUtility.getValidEntityString(this.totalMilliSecondsAllowed));
val.set("recurrenceInfoId",OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("reservNthPPPerc",this.reservNthPPPerc);
valueMap.put("workEffortIdTo",this.workEffortIdTo);
valueMap.put("runtimeDataId",this.runtimeDataId);
valueMap.put("priority",this.priority);
valueMap.put("description",this.description);
valueMap.put("estimatedCompletionDate",this.estimatedCompletionDate);
valueMap.put("actualStartDate",this.actualStartDate);
valueMap.put("quantityRejected",this.quantityRejected);
valueMap.put("estimatedSetupMillis",this.estimatedSetupMillis);
valueMap.put("lastStatusUpdate",this.lastStatusUpdate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("estimatedMilliSeconds",this.estimatedMilliSeconds);
valueMap.put("tempExprId",this.tempExprId);
valueMap.put("totalMoneyAllowed",this.totalMoneyAllowed);
valueMap.put("showAsEnumId",this.showAsEnumId);
valueMap.put("timeTransparency",this.timeTransparency);
valueMap.put("noteId",this.noteId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("moneyUomId",this.moneyUomId);
valueMap.put("currentStatusId",this.currentStatusId);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("workEffortParentId",this.workEffortParentId);
valueMap.put("workEffortIdFrom",this.workEffortIdFrom);
valueMap.put("specialTerms",this.specialTerms);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("accommodationMapId",this.accommodationMapId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("workEffortName",this.workEffortName);
valueMap.put("reservPersons",this.reservPersons);
valueMap.put("quantityProduced",this.quantityProduced);
valueMap.put("serviceLoaderName",this.serviceLoaderName);
valueMap.put("workEffortPurposeTypeId",this.workEffortPurposeTypeId);
valueMap.put("estimatedStartDate",this.estimatedStartDate);
valueMap.put("accommodationSpotId",this.accommodationSpotId);
valueMap.put("scopeEnumId",this.scopeEnumId);
valueMap.put("reserv2ndPPPerc",this.reserv2ndPPPerc);
valueMap.put("workEffortAssocTypeId",this.workEffortAssocTypeId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("quantityToProduce",this.quantityToProduce);
valueMap.put("universalId",this.universalId);
valueMap.put("percentComplete",this.percentComplete);
valueMap.put("locationDesc",this.locationDesc);
valueMap.put("revisionNumber",this.revisionNumber);
valueMap.put("workEffortTypeId",this.workEffortTypeId);
valueMap.put("actualSetupMillis",this.actualSetupMillis);
valueMap.put("actualMilliSeconds",this.actualMilliSeconds);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("estimateCalcMethod",this.estimateCalcMethod);
valueMap.put("sendNotificationEmail",this.sendNotificationEmail);
valueMap.put("actualCompletionDate",this.actualCompletionDate);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("sourceReferenceId",this.sourceReferenceId);
valueMap.put("infoUrl",this.infoUrl);
valueMap.put("totalMilliSecondsAllowed",this.totalMilliSecondsAllowed);
valueMap.put("recurrenceInfoId",this.recurrenceInfoId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("WorkEffortAssocFromView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String reservNthPPPerc;
public java.lang.String getreservNthPPPerc() {
return reservNthPPPerc;
}
public void setreservNthPPPerc( java.lang.String reservNthPPPerc ) {
this.reservNthPPPerc = reservNthPPPerc;
}
private java.lang.String workEffortIdTo;
public java.lang.String getworkEffortIdTo() {
return workEffortIdTo;
}
public void setworkEffortIdTo( java.lang.String workEffortIdTo ) {
this.workEffortIdTo = workEffortIdTo;
}
private java.lang.String runtimeDataId;
public java.lang.String getruntimeDataId() {
return runtimeDataId;
}
public void setruntimeDataId( java.lang.String runtimeDataId ) {
this.runtimeDataId = runtimeDataId;
}
private java.lang.String priority;
public java.lang.String getpriority() {
return priority;
}
public void setpriority( java.lang.String priority ) {
this.priority = priority;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Timestamp estimatedCompletionDate;
public java.sql.Timestamp getestimatedCompletionDate() {
return estimatedCompletionDate;
}
public void setestimatedCompletionDate( java.sql.Timestamp estimatedCompletionDate ) {
this.estimatedCompletionDate = estimatedCompletionDate;
}
private java.sql.Timestamp actualStartDate;
public java.sql.Timestamp getactualStartDate() {
return actualStartDate;
}
public void setactualStartDate( java.sql.Timestamp actualStartDate ) {
this.actualStartDate = actualStartDate;
}
private java.lang.String quantityRejected;
public java.lang.String getquantityRejected() {
return quantityRejected;
}
public void setquantityRejected( java.lang.String quantityRejected ) {
this.quantityRejected = quantityRejected;
}
private java.lang.String estimatedSetupMillis;
public java.lang.String getestimatedSetupMillis() {
return estimatedSetupMillis;
}
public void setestimatedSetupMillis( java.lang.String estimatedSetupMillis ) {
this.estimatedSetupMillis = estimatedSetupMillis;
}
private java.lang.String lastStatusUpdate;
public java.lang.String getlastStatusUpdate() {
return lastStatusUpdate;
}
public void setlastStatusUpdate( java.lang.String lastStatusUpdate ) {
this.lastStatusUpdate = lastStatusUpdate;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String estimatedMilliSeconds;
public java.lang.String getestimatedMilliSeconds() {
return estimatedMilliSeconds;
}
public void setestimatedMilliSeconds( java.lang.String estimatedMilliSeconds ) {
this.estimatedMilliSeconds = estimatedMilliSeconds;
}
private java.lang.String tempExprId;
public java.lang.String gettempExprId() {
return tempExprId;
}
public void settempExprId( java.lang.String tempExprId ) {
this.tempExprId = tempExprId;
}
private java.lang.String totalMoneyAllowed;
public java.lang.String gettotalMoneyAllowed() {
return totalMoneyAllowed;
}
public void settotalMoneyAllowed( java.lang.String totalMoneyAllowed ) {
this.totalMoneyAllowed = totalMoneyAllowed;
}
private java.lang.String showAsEnumId;
public java.lang.String getshowAsEnumId() {
return showAsEnumId;
}
public void setshowAsEnumId( java.lang.String showAsEnumId ) {
this.showAsEnumId = showAsEnumId;
}
private java.lang.String timeTransparency;
public java.lang.String gettimeTransparency() {
return timeTransparency;
}
public void settimeTransparency( java.lang.String timeTransparency ) {
this.timeTransparency = timeTransparency;
}
private java.lang.String noteId;
public java.lang.String getnoteId() {
return noteId;
}
public void setnoteId( java.lang.String noteId ) {
this.noteId = noteId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String moneyUomId;
public java.lang.String getmoneyUomId() {
return moneyUomId;
}
public void setmoneyUomId( java.lang.String moneyUomId ) {
this.moneyUomId = moneyUomId;
}
private java.lang.String currentStatusId;
public java.lang.String getcurrentStatusId() {
return currentStatusId;
}
public void setcurrentStatusId( java.lang.String currentStatusId ) {
this.currentStatusId = currentStatusId;
}
private java.lang.String workEffortId;
public java.lang.String getworkEffortId() {
return workEffortId;
}
public void setworkEffortId( java.lang.String workEffortId ) {
this.workEffortId = workEffortId;
}
private java.lang.String workEffortParentId;
public java.lang.String getworkEffortParentId() {
return workEffortParentId;
}
public void setworkEffortParentId( java.lang.String workEffortParentId ) {
this.workEffortParentId = workEffortParentId;
}
private java.lang.String workEffortIdFrom;
public java.lang.String getworkEffortIdFrom() {
return workEffortIdFrom;
}
public void setworkEffortIdFrom( java.lang.String workEffortIdFrom ) {
this.workEffortIdFrom = workEffortIdFrom;
}
private java.lang.String specialTerms;
public java.lang.String getspecialTerms() {
return specialTerms;
}
public void setspecialTerms( java.lang.String specialTerms ) {
this.specialTerms = specialTerms;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String accommodationMapId;
public java.lang.String getaccommodationMapId() {
return accommodationMapId;
}
public void setaccommodationMapId( java.lang.String accommodationMapId ) {
this.accommodationMapId = accommodationMapId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String workEffortName;
public java.lang.String getworkEffortName() {
return workEffortName;
}
public void setworkEffortName( java.lang.String workEffortName ) {
this.workEffortName = workEffortName;
}
private java.lang.String reservPersons;
public java.lang.String getreservPersons() {
return reservPersons;
}
public void setreservPersons( java.lang.String reservPersons ) {
this.reservPersons = reservPersons;
}
private java.lang.String quantityProduced;
public java.lang.String getquantityProduced() {
return quantityProduced;
}
public void setquantityProduced( java.lang.String quantityProduced ) {
this.quantityProduced = quantityProduced;
}
private java.lang.String serviceLoaderName;
public java.lang.String getserviceLoaderName() {
return serviceLoaderName;
}
public void setserviceLoaderName( java.lang.String serviceLoaderName ) {
this.serviceLoaderName = serviceLoaderName;
}
private java.lang.String workEffortPurposeTypeId;
public java.lang.String getworkEffortPurposeTypeId() {
return workEffortPurposeTypeId;
}
public void setworkEffortPurposeTypeId( java.lang.String workEffortPurposeTypeId ) {
this.workEffortPurposeTypeId = workEffortPurposeTypeId;
}
private java.sql.Timestamp estimatedStartDate;
public java.sql.Timestamp getestimatedStartDate() {
return estimatedStartDate;
}
public void setestimatedStartDate( java.sql.Timestamp estimatedStartDate ) {
this.estimatedStartDate = estimatedStartDate;
}
private java.lang.String accommodationSpotId;
public java.lang.String getaccommodationSpotId() {
return accommodationSpotId;
}
public void setaccommodationSpotId( java.lang.String accommodationSpotId ) {
this.accommodationSpotId = accommodationSpotId;
}
private java.lang.String scopeEnumId;
public java.lang.String getscopeEnumId() {
return scopeEnumId;
}
public void setscopeEnumId( java.lang.String scopeEnumId ) {
this.scopeEnumId = scopeEnumId;
}
private java.lang.String reserv2ndPPPerc;
public java.lang.String getreserv2ndPPPerc() {
return reserv2ndPPPerc;
}
public void setreserv2ndPPPerc( java.lang.String reserv2ndPPPerc ) {
this.reserv2ndPPPerc = reserv2ndPPPerc;
}
private java.lang.String workEffortAssocTypeId;
public java.lang.String getworkEffortAssocTypeId() {
return workEffortAssocTypeId;
}
public void setworkEffortAssocTypeId( java.lang.String workEffortAssocTypeId ) {
this.workEffortAssocTypeId = workEffortAssocTypeId;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.math.BigDecimal quantityToProduce;
public java.math.BigDecimal getquantityToProduce() {
return quantityToProduce;
}
public void setquantityToProduce( java.math.BigDecimal quantityToProduce ) {
this.quantityToProduce = quantityToProduce;
}
private java.lang.String universalId;
public java.lang.String getuniversalId() {
return universalId;
}
public void setuniversalId( java.lang.String universalId ) {
this.universalId = universalId;
}
private java.lang.String percentComplete;
public java.lang.String getpercentComplete() {
return percentComplete;
}
public void setpercentComplete( java.lang.String percentComplete ) {
this.percentComplete = percentComplete;
}
private java.lang.String locationDesc;
public java.lang.String getlocationDesc() {
return locationDesc;
}
public void setlocationDesc( java.lang.String locationDesc ) {
this.locationDesc = locationDesc;
}
private java.lang.Long revisionNumber;
public java.lang.Long getrevisionNumber() {
return revisionNumber;
}
public void setrevisionNumber( java.lang.Long revisionNumber ) {
this.revisionNumber = revisionNumber;
}
private java.lang.String workEffortTypeId;
public java.lang.String getworkEffortTypeId() {
return workEffortTypeId;
}
public void setworkEffortTypeId( java.lang.String workEffortTypeId ) {
this.workEffortTypeId = workEffortTypeId;
}
private java.lang.String actualSetupMillis;
public java.lang.String getactualSetupMillis() {
return actualSetupMillis;
}
public void setactualSetupMillis( java.lang.String actualSetupMillis ) {
this.actualSetupMillis = actualSetupMillis;
}
private java.lang.String actualMilliSeconds;
public java.lang.String getactualMilliSeconds() {
return actualMilliSeconds;
}
public void setactualMilliSeconds( java.lang.String actualMilliSeconds ) {
this.actualMilliSeconds = actualMilliSeconds;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String estimateCalcMethod;
public java.lang.String getestimateCalcMethod() {
return estimateCalcMethod;
}
public void setestimateCalcMethod( java.lang.String estimateCalcMethod ) {
this.estimateCalcMethod = estimateCalcMethod;
}
private java.lang.String sendNotificationEmail;
public java.lang.String getsendNotificationEmail() {
return sendNotificationEmail;
}
public void setsendNotificationEmail( java.lang.String sendNotificationEmail ) {
this.sendNotificationEmail = sendNotificationEmail;
}
private java.sql.Timestamp actualCompletionDate;
public java.sql.Timestamp getactualCompletionDate() {
return actualCompletionDate;
}
public void setactualCompletionDate( java.sql.Timestamp actualCompletionDate ) {
this.actualCompletionDate = actualCompletionDate;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String sourceReferenceId;
public java.lang.String getsourceReferenceId() {
return sourceReferenceId;
}
public void setsourceReferenceId( java.lang.String sourceReferenceId ) {
this.sourceReferenceId = sourceReferenceId;
}
private java.lang.String infoUrl;
public java.lang.String getinfoUrl() {
return infoUrl;
}
public void setinfoUrl( java.lang.String infoUrl ) {
this.infoUrl = infoUrl;
}
private java.lang.String totalMilliSecondsAllowed;
public java.lang.String gettotalMilliSecondsAllowed() {
return totalMilliSecondsAllowed;
}
public void settotalMilliSecondsAllowed( java.lang.String totalMilliSecondsAllowed ) {
this.totalMilliSecondsAllowed = totalMilliSecondsAllowed;
}
private java.lang.String recurrenceInfoId;
public java.lang.String getrecurrenceInfoId() {
return recurrenceInfoId;
}
public void setrecurrenceInfoId( java.lang.String recurrenceInfoId ) {
this.recurrenceInfoId = recurrenceInfoId;
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
        Collection<WorkEffortAssocFromView> objectList = new ArrayList<WorkEffortAssocFromView>();
        for (GenericValue genVal : genList) {
            objectList.add(new WorkEffortAssocFromView(genVal));
        }
        return objectList;
    }    
}
