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
public class ProjectPhaseTaskAssignmentView implements GenericValueObjectInterface {
public static final String module =ProjectPhaseTaskAssignmentView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProjectPhaseTaskAssignmentView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProjectPhaseTaskAssignmentView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"taskId","Task Id"},
{"resourceCount","Resource Count"},
{"entriesCount","Entries Count"},
{"projectId","Project Id"},
{"phaseId","Phase Id"},
{"taskStatusId","Task Status Id"},
};
protected void initObject(){
this.taskId = "";
this.resourceCount = new Long(0);
this.entriesCount = new Long(0);
this.projectId = "";
this.phaseId = "";
this.taskStatusId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.taskId = (java.lang.String) genVal.get("taskId");
this.resourceCount = (java.lang.Long) genVal.get("resourceCount");
this.entriesCount = (java.lang.Long) genVal.get("entriesCount");
this.projectId = (java.lang.String) genVal.get("projectId");
this.phaseId = (java.lang.String) genVal.get("phaseId");
this.taskStatusId = (java.lang.String) genVal.get("taskStatusId");
}
protected void getGenericValue(GenericValue val) {
val.set("taskId",OrderMaxUtility.getValidEntityString(this.taskId));
val.set("resourceCount",this.resourceCount);
val.set("entriesCount",this.entriesCount);
val.set("projectId",OrderMaxUtility.getValidEntityString(this.projectId));
val.set("phaseId",OrderMaxUtility.getValidEntityString(this.phaseId));
val.set("taskStatusId",OrderMaxUtility.getValidEntityString(this.taskStatusId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("taskId",this.taskId);
valueMap.put("resourceCount",this.resourceCount);
valueMap.put("entriesCount",this.entriesCount);
valueMap.put("projectId",this.projectId);
valueMap.put("phaseId",this.phaseId);
valueMap.put("taskStatusId",this.taskStatusId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProjectPhaseTaskAssignmentView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String taskId;
public java.lang.String gettaskId() {
return taskId;
}
public void settaskId( java.lang.String taskId ) {
this.taskId = taskId;
}
private java.lang.Long resourceCount;
public java.lang.Long getresourceCount() {
return resourceCount;
}
public void setresourceCount( java.lang.Long resourceCount ) {
this.resourceCount = resourceCount;
}
private java.lang.Long entriesCount;
public java.lang.Long getentriesCount() {
return entriesCount;
}
public void setentriesCount( java.lang.Long entriesCount ) {
this.entriesCount = entriesCount;
}
private java.lang.String projectId;
public java.lang.String getprojectId() {
return projectId;
}
public void setprojectId( java.lang.String projectId ) {
this.projectId = projectId;
}
private java.lang.String phaseId;
public java.lang.String getphaseId() {
return phaseId;
}
public void setphaseId( java.lang.String phaseId ) {
this.phaseId = phaseId;
}
private java.lang.String taskStatusId;
public java.lang.String gettaskStatusId() {
return taskStatusId;
}
public void settaskStatusId( java.lang.String taskStatusId ) {
this.taskStatusId = taskStatusId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProjectPhaseTaskAssignmentView> objectList = new ArrayList<ProjectPhaseTaskAssignmentView>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProjectPhaseTaskAssignmentView(genVal));
        }
        return objectList;
    }    
}
