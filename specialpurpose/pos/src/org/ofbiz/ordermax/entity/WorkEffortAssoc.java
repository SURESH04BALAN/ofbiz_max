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
public class WorkEffortAssoc implements GenericValueObjectInterface {
public static final String module =WorkEffortAssoc.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public WorkEffortAssoc(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public WorkEffortAssoc () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"workEffortIdFrom","Work Effort Id From"},
{"thruDate","Thru Date"},
{"fromDate","From Date"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"workEffortAssocTypeId","Work Effort Assoc Type Id"},
{"workEffortIdTo","Work Effort Id To"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"sequenceNum","Sequence Num"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.workEffortIdFrom = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.fromDate = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.workEffortAssocTypeId = "";
this.workEffortIdTo = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.sequenceNum = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.workEffortIdFrom = (java.lang.String) genVal.get("workEffortIdFrom");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.workEffortAssocTypeId = (java.lang.String) genVal.get("workEffortAssocTypeId");
this.workEffortIdTo = (java.lang.String) genVal.get("workEffortIdTo");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("workEffortIdFrom",OrderMaxUtility.getValidEntityString(this.workEffortIdFrom));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("workEffortAssocTypeId",OrderMaxUtility.getValidEntityString(this.workEffortAssocTypeId));
val.set("workEffortIdTo",OrderMaxUtility.getValidEntityString(this.workEffortIdTo));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("sequenceNum",this.sequenceNum);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("workEffortIdFrom",this.workEffortIdFrom);
valueMap.put("thruDate",this.thruDate);
valueMap.put("fromDate",this.fromDate);
valueMap.put("workEffortAssocTypeId",this.workEffortAssocTypeId);
valueMap.put("workEffortIdTo",this.workEffortIdTo);
valueMap.put("sequenceNum",this.sequenceNum);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("WorkEffortAssoc");
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
private java.lang.String workEffortIdFrom;
public java.lang.String getworkEffortIdFrom() {
return workEffortIdFrom;
}
public void setworkEffortIdFrom( java.lang.String workEffortIdFrom ) {
this.workEffortIdFrom = workEffortIdFrom;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String workEffortAssocTypeId;
public java.lang.String getworkEffortAssocTypeId() {
return workEffortAssocTypeId;
}
public void setworkEffortAssocTypeId( java.lang.String workEffortAssocTypeId ) {
this.workEffortAssocTypeId = workEffortAssocTypeId;
}
private java.lang.String workEffortIdTo;
public java.lang.String getworkEffortIdTo() {
return workEffortIdTo;
}
public void setworkEffortIdTo( java.lang.String workEffortIdTo ) {
this.workEffortIdTo = workEffortIdTo;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<WorkEffortAssoc> objectList = new ArrayList<WorkEffortAssoc>();
        for (GenericValue genVal : genList) {
            objectList.add(new WorkEffortAssoc(genVal));
        }
        return objectList;
    }    
}
