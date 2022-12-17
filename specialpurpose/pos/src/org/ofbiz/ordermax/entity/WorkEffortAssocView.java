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
public class WorkEffortAssocView implements GenericValueObjectInterface {
public static final String module =WorkEffortAssocView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public WorkEffortAssocView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public WorkEffortAssocView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruDate","Thru Date"},
{"workEffortToRun","Work Effort To Run"},
{"workEffortIdTo","Work Effort Id To"},
{"workEffortToActualCompletionDate","Work Effort To Actual Completion Date"},
{"sequenceNum","Sequence Num"},
{"workEffortToName","Work Effort To Name"},
{"workEffortIdFrom","Work Effort Id From"},
{"workEffortToActualStartDate","Work Effort To Actual Start Date"},
{"workEffortToCurrentStatusId","Work Effort To Current Status Id"},
{"fromDate","From Date"},
{"workEffortToEstimatedStartDate","Work Effort To Estimated Start Date"},
{"workEffortToEstimatedCompletionDate","Work Effort To Estimated Completion Date"},
{"workEffortToSetup","Work Effort To Setup"},
{"workEffortAssocTypeId","Work Effort Assoc Type Id"},
{"workEffortToWorkEffortPurposeTypeId","Work Effort To Work Effort Purpose Type Id"},
{"workEffortToParentId","Work Effort To Parent Id"},
};
protected void initObject(){
this.thruDate = UtilDateTime.nowTimestamp();
this.workEffortToRun = new Double(0);
this.workEffortIdTo = "";
this.workEffortToActualCompletionDate = UtilDateTime.nowTimestamp();
this.sequenceNum = new Long(0);
this.workEffortToName = "";
this.workEffortIdFrom = "";
this.workEffortToActualStartDate = UtilDateTime.nowTimestamp();
this.workEffortToCurrentStatusId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.workEffortToEstimatedStartDate = UtilDateTime.nowTimestamp();
this.workEffortToEstimatedCompletionDate = UtilDateTime.nowTimestamp();
this.workEffortToSetup = new Double(0);
this.workEffortAssocTypeId = "";
this.workEffortToWorkEffortPurposeTypeId = "";
this.workEffortToParentId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.workEffortToRun = (java.lang.Double) genVal.get("workEffortToRun");
this.workEffortIdTo = (java.lang.String) genVal.get("workEffortIdTo");
this.workEffortToActualCompletionDate = (java.sql.Timestamp) genVal.get("workEffortToActualCompletionDate");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
this.workEffortToName = (java.lang.String) genVal.get("workEffortToName");
this.workEffortIdFrom = (java.lang.String) genVal.get("workEffortIdFrom");
this.workEffortToActualStartDate = (java.sql.Timestamp) genVal.get("workEffortToActualStartDate");
this.workEffortToCurrentStatusId = (java.lang.String) genVal.get("workEffortToCurrentStatusId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.workEffortToEstimatedStartDate = (java.sql.Timestamp) genVal.get("workEffortToEstimatedStartDate");
this.workEffortToEstimatedCompletionDate = (java.sql.Timestamp) genVal.get("workEffortToEstimatedCompletionDate");
this.workEffortToSetup = (java.lang.Double) genVal.get("workEffortToSetup");
this.workEffortAssocTypeId = (java.lang.String) genVal.get("workEffortAssocTypeId");
this.workEffortToWorkEffortPurposeTypeId = (java.lang.String) genVal.get("workEffortToWorkEffortPurposeTypeId");
this.workEffortToParentId = (java.lang.String) genVal.get("workEffortToParentId");
}
protected void getGenericValue(GenericValue val) {
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("workEffortToRun",OrderMaxUtility.getValidEntityDouble(this.workEffortToRun));
val.set("workEffortIdTo",OrderMaxUtility.getValidEntityString(this.workEffortIdTo));
val.set("workEffortToActualCompletionDate",OrderMaxUtility.getValidEntityTimestamp(this.workEffortToActualCompletionDate));
val.set("sequenceNum",this.sequenceNum);
val.set("workEffortToName",OrderMaxUtility.getValidEntityString(this.workEffortToName));
val.set("workEffortIdFrom",OrderMaxUtility.getValidEntityString(this.workEffortIdFrom));
val.set("workEffortToActualStartDate",OrderMaxUtility.getValidEntityTimestamp(this.workEffortToActualStartDate));
val.set("workEffortToCurrentStatusId",OrderMaxUtility.getValidEntityString(this.workEffortToCurrentStatusId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("workEffortToEstimatedStartDate",OrderMaxUtility.getValidEntityTimestamp(this.workEffortToEstimatedStartDate));
val.set("workEffortToEstimatedCompletionDate",OrderMaxUtility.getValidEntityTimestamp(this.workEffortToEstimatedCompletionDate));
val.set("workEffortToSetup",OrderMaxUtility.getValidEntityDouble(this.workEffortToSetup));
val.set("workEffortAssocTypeId",OrderMaxUtility.getValidEntityString(this.workEffortAssocTypeId));
val.set("workEffortToWorkEffortPurposeTypeId",OrderMaxUtility.getValidEntityString(this.workEffortToWorkEffortPurposeTypeId));
val.set("workEffortToParentId",OrderMaxUtility.getValidEntityString(this.workEffortToParentId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruDate",this.thruDate);
valueMap.put("workEffortToRun",this.workEffortToRun);
valueMap.put("workEffortIdTo",this.workEffortIdTo);
valueMap.put("workEffortToActualCompletionDate",this.workEffortToActualCompletionDate);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("workEffortToName",this.workEffortToName);
valueMap.put("workEffortIdFrom",this.workEffortIdFrom);
valueMap.put("workEffortToActualStartDate",this.workEffortToActualStartDate);
valueMap.put("workEffortToCurrentStatusId",this.workEffortToCurrentStatusId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("workEffortToEstimatedStartDate",this.workEffortToEstimatedStartDate);
valueMap.put("workEffortToEstimatedCompletionDate",this.workEffortToEstimatedCompletionDate);
valueMap.put("workEffortToSetup",this.workEffortToSetup);
valueMap.put("workEffortAssocTypeId",this.workEffortAssocTypeId);
valueMap.put("workEffortToWorkEffortPurposeTypeId",this.workEffortToWorkEffortPurposeTypeId);
valueMap.put("workEffortToParentId",this.workEffortToParentId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("WorkEffortAssocView");
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
private java.lang.Double workEffortToRun;
public java.lang.Double getworkEffortToRun() {
return workEffortToRun;
}
public void setworkEffortToRun( java.lang.Double workEffortToRun ) {
this.workEffortToRun = workEffortToRun;
}
private java.lang.String workEffortIdTo;
public java.lang.String getworkEffortIdTo() {
return workEffortIdTo;
}
public void setworkEffortIdTo( java.lang.String workEffortIdTo ) {
this.workEffortIdTo = workEffortIdTo;
}
private java.sql.Timestamp workEffortToActualCompletionDate;
public java.sql.Timestamp getworkEffortToActualCompletionDate() {
return workEffortToActualCompletionDate;
}
public void setworkEffortToActualCompletionDate( java.sql.Timestamp workEffortToActualCompletionDate ) {
this.workEffortToActualCompletionDate = workEffortToActualCompletionDate;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String workEffortToName;
public java.lang.String getworkEffortToName() {
return workEffortToName;
}
public void setworkEffortToName( java.lang.String workEffortToName ) {
this.workEffortToName = workEffortToName;
}
private java.lang.String workEffortIdFrom;
public java.lang.String getworkEffortIdFrom() {
return workEffortIdFrom;
}
public void setworkEffortIdFrom( java.lang.String workEffortIdFrom ) {
this.workEffortIdFrom = workEffortIdFrom;
}
private java.sql.Timestamp workEffortToActualStartDate;
public java.sql.Timestamp getworkEffortToActualStartDate() {
return workEffortToActualStartDate;
}
public void setworkEffortToActualStartDate( java.sql.Timestamp workEffortToActualStartDate ) {
this.workEffortToActualStartDate = workEffortToActualStartDate;
}
private java.lang.String workEffortToCurrentStatusId;
public java.lang.String getworkEffortToCurrentStatusId() {
return workEffortToCurrentStatusId;
}
public void setworkEffortToCurrentStatusId( java.lang.String workEffortToCurrentStatusId ) {
this.workEffortToCurrentStatusId = workEffortToCurrentStatusId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.sql.Timestamp workEffortToEstimatedStartDate;
public java.sql.Timestamp getworkEffortToEstimatedStartDate() {
return workEffortToEstimatedStartDate;
}
public void setworkEffortToEstimatedStartDate( java.sql.Timestamp workEffortToEstimatedStartDate ) {
this.workEffortToEstimatedStartDate = workEffortToEstimatedStartDate;
}
private java.sql.Timestamp workEffortToEstimatedCompletionDate;
public java.sql.Timestamp getworkEffortToEstimatedCompletionDate() {
return workEffortToEstimatedCompletionDate;
}
public void setworkEffortToEstimatedCompletionDate( java.sql.Timestamp workEffortToEstimatedCompletionDate ) {
this.workEffortToEstimatedCompletionDate = workEffortToEstimatedCompletionDate;
}
private java.lang.Double workEffortToSetup;
public java.lang.Double getworkEffortToSetup() {
return workEffortToSetup;
}
public void setworkEffortToSetup( java.lang.Double workEffortToSetup ) {
this.workEffortToSetup = workEffortToSetup;
}
private java.lang.String workEffortAssocTypeId;
public java.lang.String getworkEffortAssocTypeId() {
return workEffortAssocTypeId;
}
public void setworkEffortAssocTypeId( java.lang.String workEffortAssocTypeId ) {
this.workEffortAssocTypeId = workEffortAssocTypeId;
}
private java.lang.String workEffortToWorkEffortPurposeTypeId;
public java.lang.String getworkEffortToWorkEffortPurposeTypeId() {
return workEffortToWorkEffortPurposeTypeId;
}
public void setworkEffortToWorkEffortPurposeTypeId( java.lang.String workEffortToWorkEffortPurposeTypeId ) {
this.workEffortToWorkEffortPurposeTypeId = workEffortToWorkEffortPurposeTypeId;
}
private java.lang.String workEffortToParentId;
public java.lang.String getworkEffortToParentId() {
return workEffortToParentId;
}
public void setworkEffortToParentId( java.lang.String workEffortToParentId ) {
this.workEffortToParentId = workEffortToParentId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<WorkEffortAssocView> objectList = new ArrayList<WorkEffortAssocView>();
        for (GenericValue genVal : genList) {
            objectList.add(new WorkEffortAssocView(genVal));
        }
        return objectList;
    }    
}
