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
public class RecurrenceInfo implements GenericValueObjectInterface {
public static final String module =RecurrenceInfo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RecurrenceInfo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RecurrenceInfo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"recurrenceDateTimes","Recurrence Date Times"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"exceptionRuleId","Exception Rule Id"},
{"startDateTime","Start Date Time"},
{"exceptionDateTimes","Exception Date Times"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"recurrenceRuleId","Recurrence Rule Id"},
{"recurrenceCount","Recurrence Count"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"recurrenceInfoId","Recurrence Info Id"},
};
protected void initObject(){
this.recurrenceDateTimes = UtilDateTime.nowTimestamp();
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.exceptionRuleId = "";
this.startDateTime = UtilDateTime.nowTimestamp();
this.exceptionDateTimes = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.recurrenceRuleId = "";
this.recurrenceCount = new Long(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.recurrenceInfoId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.recurrenceDateTimes = (java.sql.Timestamp) genVal.get("recurrenceDateTimes");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.exceptionRuleId = (java.lang.String) genVal.get("exceptionRuleId");
this.startDateTime = (java.sql.Timestamp) genVal.get("startDateTime");
this.exceptionDateTimes = (java.sql.Timestamp) genVal.get("exceptionDateTimes");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.recurrenceRuleId = (java.lang.String) genVal.get("recurrenceRuleId");
this.recurrenceCount = (java.lang.Long) genVal.get("recurrenceCount");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
}
protected void getGenericValue(GenericValue val) {
val.set("recurrenceDateTimes",OrderMaxUtility.getValidEntityTimestamp(this.recurrenceDateTimes));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("exceptionRuleId",OrderMaxUtility.getValidEntityString(this.exceptionRuleId));
val.set("startDateTime",OrderMaxUtility.getValidEntityTimestamp(this.startDateTime));
val.set("exceptionDateTimes",OrderMaxUtility.getValidEntityTimestamp(this.exceptionDateTimes));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("recurrenceRuleId",OrderMaxUtility.getValidEntityString(this.recurrenceRuleId));
val.set("recurrenceCount",this.recurrenceCount);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("recurrenceInfoId",OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("recurrenceDateTimes",this.recurrenceDateTimes);
valueMap.put("exceptionRuleId",this.exceptionRuleId);
valueMap.put("startDateTime",this.startDateTime);
valueMap.put("exceptionDateTimes",this.exceptionDateTimes);
valueMap.put("recurrenceRuleId",this.recurrenceRuleId);
valueMap.put("recurrenceCount",this.recurrenceCount);
valueMap.put("recurrenceInfoId",this.recurrenceInfoId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RecurrenceInfo");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp recurrenceDateTimes;
public java.sql.Timestamp getrecurrenceDateTimes() {
return recurrenceDateTimes;
}
public void setrecurrenceDateTimes( java.sql.Timestamp recurrenceDateTimes ) {
this.recurrenceDateTimes = recurrenceDateTimes;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String exceptionRuleId;
public java.lang.String getexceptionRuleId() {
return exceptionRuleId;
}
public void setexceptionRuleId( java.lang.String exceptionRuleId ) {
this.exceptionRuleId = exceptionRuleId;
}
private java.sql.Timestamp startDateTime;
public java.sql.Timestamp getstartDateTime() {
return startDateTime;
}
public void setstartDateTime( java.sql.Timestamp startDateTime ) {
this.startDateTime = startDateTime;
}
private java.sql.Timestamp exceptionDateTimes;
public java.sql.Timestamp getexceptionDateTimes() {
return exceptionDateTimes;
}
public void setexceptionDateTimes( java.sql.Timestamp exceptionDateTimes ) {
this.exceptionDateTimes = exceptionDateTimes;
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
private java.lang.String recurrenceRuleId;
public java.lang.String getrecurrenceRuleId() {
return recurrenceRuleId;
}
public void setrecurrenceRuleId( java.lang.String recurrenceRuleId ) {
this.recurrenceRuleId = recurrenceRuleId;
}
private java.lang.Long recurrenceCount;
public java.lang.Long getrecurrenceCount() {
return recurrenceCount;
}
public void setrecurrenceCount( java.lang.Long recurrenceCount ) {
this.recurrenceCount = recurrenceCount;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RecurrenceInfo> objectList = new ArrayList<RecurrenceInfo>();
        for (GenericValue genVal : genList) {
            objectList.add(new RecurrenceInfo(genVal));
        }
        return objectList;
    }    
}
