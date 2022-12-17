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
public class JobSandbox implements GenericValueObjectInterface {
public static final String module =JobSandbox.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public JobSandbox(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public JobSandbox () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"jobId","Job Id"},
{"startDateTime","Start Date Time"},
{"runTime","Run Time"},
{"jobName","Job Name"},
{"serviceName","Service Name"},
{"statusId","Status Id"},
{"runtimeDataId","Runtime Data Id"},
{"createdTxStamp","Created Tx Stamp"},
{"finishDateTime","Finish Date Time"},
{"createdStamp","Created Stamp"},
{"maxRetry","Max Retry"},
{"runByInstanceId","Run By Instance Id"},
{"cancelDateTime","Cancel Date Time"},
{"loaderName","Loader Name"},
{"poolId","Pool Id"},
{"previousJobId","Previous Job Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"tempExprId","Temp Expr Id"},
{"authUserLoginId","Auth User Login Id"},
{"parentJobId","Parent Job Id"},
{"currentRecurrenceCount","Current Recurrence Count"},
{"runAsUser","Run As User"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"recurrenceInfoId","Recurrence Info Id"},
{"maxRecurrenceCount","Max Recurrence Count"},
};
protected void initObject(){
this.jobId = "";
this.startDateTime = UtilDateTime.nowTimestamp();
this.runTime = UtilDateTime.nowTimestamp();
this.jobName = "";
this.serviceName = "";
this.statusId = "";
this.runtimeDataId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.finishDateTime = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.maxRetry = "";
this.runByInstanceId = "";
this.cancelDateTime = UtilDateTime.nowTimestamp();
this.loaderName = "";
this.poolId = "";
this.previousJobId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.tempExprId = "";
this.authUserLoginId = "";
this.parentJobId = "";
this.currentRecurrenceCount = "";
this.runAsUser = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.recurrenceInfoId = "";
this.maxRecurrenceCount = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.jobId = (java.lang.String) genVal.get("jobId");
this.startDateTime = (java.sql.Timestamp) genVal.get("startDateTime");
this.runTime = (java.sql.Timestamp) genVal.get("runTime");
this.jobName = (java.lang.String) genVal.get("jobName");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.statusId = (java.lang.String) genVal.get("statusId");
this.runtimeDataId = (java.lang.String) genVal.get("runtimeDataId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.finishDateTime = (java.sql.Timestamp) genVal.get("finishDateTime");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.maxRetry = (java.lang.String) genVal.get("maxRetry");
this.runByInstanceId = (java.lang.String) genVal.get("runByInstanceId");
this.cancelDateTime = (java.sql.Timestamp) genVal.get("cancelDateTime");
this.loaderName = (java.lang.String) genVal.get("loaderName");
this.poolId = (java.lang.String) genVal.get("poolId");
this.previousJobId = (java.lang.String) genVal.get("previousJobId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.tempExprId = (java.lang.String) genVal.get("tempExprId");
this.authUserLoginId = (java.lang.String) genVal.get("authUserLoginId");
this.parentJobId = (java.lang.String) genVal.get("parentJobId");
this.currentRecurrenceCount = (java.lang.String) genVal.get("currentRecurrenceCount");
this.runAsUser = (java.lang.String) genVal.get("runAsUser");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
this.maxRecurrenceCount = (java.lang.String) genVal.get("maxRecurrenceCount");
}
protected void getGenericValue(GenericValue val) {
val.set("jobId",OrderMaxUtility.getValidEntityString(this.jobId));
val.set("startDateTime",OrderMaxUtility.getValidEntityTimestamp(this.startDateTime));
val.set("runTime",OrderMaxUtility.getValidEntityTimestamp(this.runTime));
val.set("jobName",OrderMaxUtility.getValidEntityString(this.jobName));
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("runtimeDataId",OrderMaxUtility.getValidEntityString(this.runtimeDataId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("finishDateTime",OrderMaxUtility.getValidEntityTimestamp(this.finishDateTime));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("maxRetry",OrderMaxUtility.getValidEntityString(this.maxRetry));
val.set("runByInstanceId",OrderMaxUtility.getValidEntityString(this.runByInstanceId));
val.set("cancelDateTime",OrderMaxUtility.getValidEntityTimestamp(this.cancelDateTime));
val.set("loaderName",OrderMaxUtility.getValidEntityString(this.loaderName));
val.set("poolId",OrderMaxUtility.getValidEntityString(this.poolId));
val.set("previousJobId",OrderMaxUtility.getValidEntityString(this.previousJobId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("tempExprId",OrderMaxUtility.getValidEntityString(this.tempExprId));
val.set("authUserLoginId",OrderMaxUtility.getValidEntityString(this.authUserLoginId));
val.set("parentJobId",OrderMaxUtility.getValidEntityString(this.parentJobId));
val.set("currentRecurrenceCount",OrderMaxUtility.getValidEntityString(this.currentRecurrenceCount));
val.set("runAsUser",OrderMaxUtility.getValidEntityString(this.runAsUser));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("recurrenceInfoId",OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
val.set("maxRecurrenceCount",OrderMaxUtility.getValidEntityString(this.maxRecurrenceCount));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("jobId",this.jobId);
valueMap.put("startDateTime",this.startDateTime);
valueMap.put("runTime",this.runTime);
valueMap.put("jobName",this.jobName);
valueMap.put("serviceName",this.serviceName);
valueMap.put("statusId",this.statusId);
valueMap.put("runtimeDataId",this.runtimeDataId);
valueMap.put("finishDateTime",this.finishDateTime);
valueMap.put("maxRetry",this.maxRetry);
valueMap.put("runByInstanceId",this.runByInstanceId);
valueMap.put("cancelDateTime",this.cancelDateTime);
valueMap.put("loaderName",this.loaderName);
valueMap.put("poolId",this.poolId);
valueMap.put("previousJobId",this.previousJobId);
valueMap.put("tempExprId",this.tempExprId);
valueMap.put("authUserLoginId",this.authUserLoginId);
valueMap.put("parentJobId",this.parentJobId);
valueMap.put("currentRecurrenceCount",this.currentRecurrenceCount);
valueMap.put("runAsUser",this.runAsUser);
valueMap.put("recurrenceInfoId",this.recurrenceInfoId);
valueMap.put("maxRecurrenceCount",this.maxRecurrenceCount);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("JobSandbox");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String jobId;
public java.lang.String getjobId() {
return jobId;
}
public void setjobId( java.lang.String jobId ) {
this.jobId = jobId;
}
private java.sql.Timestamp startDateTime;
public java.sql.Timestamp getstartDateTime() {
return startDateTime;
}
public void setstartDateTime( java.sql.Timestamp startDateTime ) {
this.startDateTime = startDateTime;
}
private java.sql.Timestamp runTime;
public java.sql.Timestamp getrunTime() {
return runTime;
}
public void setrunTime( java.sql.Timestamp runTime ) {
this.runTime = runTime;
}
private java.lang.String jobName;
public java.lang.String getjobName() {
return jobName;
}
public void setjobName( java.lang.String jobName ) {
this.jobName = jobName;
}
private java.lang.String serviceName;
public java.lang.String getserviceName() {
return serviceName;
}
public void setserviceName( java.lang.String serviceName ) {
this.serviceName = serviceName;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String runtimeDataId;
public java.lang.String getruntimeDataId() {
return runtimeDataId;
}
public void setruntimeDataId( java.lang.String runtimeDataId ) {
this.runtimeDataId = runtimeDataId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp finishDateTime;
public java.sql.Timestamp getfinishDateTime() {
return finishDateTime;
}
public void setfinishDateTime( java.sql.Timestamp finishDateTime ) {
this.finishDateTime = finishDateTime;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String maxRetry;
public java.lang.String getmaxRetry() {
return maxRetry;
}
public void setmaxRetry( java.lang.String maxRetry ) {
this.maxRetry = maxRetry;
}
private java.lang.String runByInstanceId;
public java.lang.String getrunByInstanceId() {
return runByInstanceId;
}
public void setrunByInstanceId( java.lang.String runByInstanceId ) {
this.runByInstanceId = runByInstanceId;
}
private java.sql.Timestamp cancelDateTime;
public java.sql.Timestamp getcancelDateTime() {
return cancelDateTime;
}
public void setcancelDateTime( java.sql.Timestamp cancelDateTime ) {
this.cancelDateTime = cancelDateTime;
}
private java.lang.String loaderName;
public java.lang.String getloaderName() {
return loaderName;
}
public void setloaderName( java.lang.String loaderName ) {
this.loaderName = loaderName;
}
private java.lang.String poolId;
public java.lang.String getpoolId() {
return poolId;
}
public void setpoolId( java.lang.String poolId ) {
this.poolId = poolId;
}
private java.lang.String previousJobId;
public java.lang.String getpreviousJobId() {
return previousJobId;
}
public void setpreviousJobId( java.lang.String previousJobId ) {
this.previousJobId = previousJobId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String tempExprId;
public java.lang.String gettempExprId() {
return tempExprId;
}
public void settempExprId( java.lang.String tempExprId ) {
this.tempExprId = tempExprId;
}
private java.lang.String authUserLoginId;
public java.lang.String getauthUserLoginId() {
return authUserLoginId;
}
public void setauthUserLoginId( java.lang.String authUserLoginId ) {
this.authUserLoginId = authUserLoginId;
}
private java.lang.String parentJobId;
public java.lang.String getparentJobId() {
return parentJobId;
}
public void setparentJobId( java.lang.String parentJobId ) {
this.parentJobId = parentJobId;
}
private java.lang.String currentRecurrenceCount;
public java.lang.String getcurrentRecurrenceCount() {
return currentRecurrenceCount;
}
public void setcurrentRecurrenceCount( java.lang.String currentRecurrenceCount ) {
this.currentRecurrenceCount = currentRecurrenceCount;
}
private java.lang.String runAsUser;
public java.lang.String getrunAsUser() {
return runAsUser;
}
public void setrunAsUser( java.lang.String runAsUser ) {
this.runAsUser = runAsUser;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String recurrenceInfoId;
public java.lang.String getrecurrenceInfoId() {
return recurrenceInfoId;
}
public void setrecurrenceInfoId( java.lang.String recurrenceInfoId ) {
this.recurrenceInfoId = recurrenceInfoId;
}
private java.lang.String maxRecurrenceCount;
public java.lang.String getmaxRecurrenceCount() {
return maxRecurrenceCount;
}
public void setmaxRecurrenceCount( java.lang.String maxRecurrenceCount ) {
this.maxRecurrenceCount = maxRecurrenceCount;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<JobSandbox> objectList = new ArrayList<JobSandbox>();
        for (GenericValue genVal : genList) {
            objectList.add(new JobSandbox(genVal));
        }
        return objectList;
    }    
}
