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
public class RecurrenceRule implements GenericValueObjectInterface {
public static final String module =RecurrenceRule.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RecurrenceRule(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RecurrenceRule () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"bySecondList","By Second List"},
{"intervalNumber","Interval Number"},
{"byMinuteList","By Minute List"},
{"byMonthDayList","By Month Day List"},
{"frequency","Frequency"},
{"bySetPosList","By Set Pos List"},
{"xName","X Name"},
{"byWeekNoList","By Week No List"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"untilDateTime","Until Date Time"},
{"weekStart","Week Start"},
{"countNumber","Count Number"},
{"byHourList","By Hour List"},
{"byYearDayList","By Year Day List"},
{"recurrenceRuleId","Recurrence Rule Id"},
{"byMonthList","By Month List"},
{"byDayList","By Day List"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.bySecondList = "";
this.intervalNumber = new Long(0);
this.byMinuteList = "";
this.byMonthDayList = "";
this.frequency = "";
this.bySetPosList = "";
this.xName = "";
this.byWeekNoList = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.untilDateTime = UtilDateTime.nowTimestamp();
this.weekStart = "";
this.countNumber = new Long(0);
this.byHourList = "";
this.byYearDayList = "";
this.recurrenceRuleId = "";
this.byMonthList = "";
this.byDayList = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.bySecondList = (java.lang.String) genVal.get("bySecondList");
this.intervalNumber = (java.lang.Long) genVal.get("intervalNumber");
this.byMinuteList = (java.lang.String) genVal.get("byMinuteList");
this.byMonthDayList = (java.lang.String) genVal.get("byMonthDayList");
this.frequency = (java.lang.String) genVal.get("frequency");
this.bySetPosList = (java.lang.String) genVal.get("bySetPosList");
this.xName = (java.lang.String) genVal.get("xName");
this.byWeekNoList = (java.lang.String) genVal.get("byWeekNoList");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.untilDateTime = (java.sql.Timestamp) genVal.get("untilDateTime");
this.weekStart = (java.lang.String) genVal.get("weekStart");
this.countNumber = (java.lang.Long) genVal.get("countNumber");
this.byHourList = (java.lang.String) genVal.get("byHourList");
this.byYearDayList = (java.lang.String) genVal.get("byYearDayList");
this.recurrenceRuleId = (java.lang.String) genVal.get("recurrenceRuleId");
this.byMonthList = (java.lang.String) genVal.get("byMonthList");
this.byDayList = (java.lang.String) genVal.get("byDayList");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("bySecondList",OrderMaxUtility.getValidEntityString(this.bySecondList));
val.set("intervalNumber",this.intervalNumber);
val.set("byMinuteList",OrderMaxUtility.getValidEntityString(this.byMinuteList));
val.set("byMonthDayList",OrderMaxUtility.getValidEntityString(this.byMonthDayList));
val.set("frequency",OrderMaxUtility.getValidEntityString(this.frequency));
val.set("bySetPosList",OrderMaxUtility.getValidEntityString(this.bySetPosList));
val.set("xName",OrderMaxUtility.getValidEntityString(this.xName));
val.set("byWeekNoList",OrderMaxUtility.getValidEntityString(this.byWeekNoList));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("untilDateTime",OrderMaxUtility.getValidEntityTimestamp(this.untilDateTime));
val.set("weekStart",OrderMaxUtility.getValidEntityString(this.weekStart));
val.set("countNumber",this.countNumber);
val.set("byHourList",OrderMaxUtility.getValidEntityString(this.byHourList));
val.set("byYearDayList",OrderMaxUtility.getValidEntityString(this.byYearDayList));
val.set("recurrenceRuleId",OrderMaxUtility.getValidEntityString(this.recurrenceRuleId));
val.set("byMonthList",OrderMaxUtility.getValidEntityString(this.byMonthList));
val.set("byDayList",OrderMaxUtility.getValidEntityString(this.byDayList));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("bySecondList",this.bySecondList);
valueMap.put("intervalNumber",this.intervalNumber);
valueMap.put("byMinuteList",this.byMinuteList);
valueMap.put("byMonthDayList",this.byMonthDayList);
valueMap.put("frequency",this.frequency);
valueMap.put("bySetPosList",this.bySetPosList);
valueMap.put("xName",this.xName);
valueMap.put("byWeekNoList",this.byWeekNoList);
valueMap.put("untilDateTime",this.untilDateTime);
valueMap.put("weekStart",this.weekStart);
valueMap.put("countNumber",this.countNumber);
valueMap.put("byHourList",this.byHourList);
valueMap.put("byYearDayList",this.byYearDayList);
valueMap.put("recurrenceRuleId",this.recurrenceRuleId);
valueMap.put("byMonthList",this.byMonthList);
valueMap.put("byDayList",this.byDayList);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RecurrenceRule");
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
private java.lang.String bySecondList;
public java.lang.String getbySecondList() {
return bySecondList;
}
public void setbySecondList( java.lang.String bySecondList ) {
this.bySecondList = bySecondList;
}
private java.lang.Long intervalNumber;
public java.lang.Long getintervalNumber() {
return intervalNumber;
}
public void setintervalNumber( java.lang.Long intervalNumber ) {
this.intervalNumber = intervalNumber;
}
private java.lang.String byMinuteList;
public java.lang.String getbyMinuteList() {
return byMinuteList;
}
public void setbyMinuteList( java.lang.String byMinuteList ) {
this.byMinuteList = byMinuteList;
}
private java.lang.String byMonthDayList;
public java.lang.String getbyMonthDayList() {
return byMonthDayList;
}
public void setbyMonthDayList( java.lang.String byMonthDayList ) {
this.byMonthDayList = byMonthDayList;
}
private java.lang.String frequency;
public java.lang.String getfrequency() {
return frequency;
}
public void setfrequency( java.lang.String frequency ) {
this.frequency = frequency;
}
private java.lang.String bySetPosList;
public java.lang.String getbySetPosList() {
return bySetPosList;
}
public void setbySetPosList( java.lang.String bySetPosList ) {
this.bySetPosList = bySetPosList;
}
private java.lang.String xName;
public java.lang.String getxName() {
return xName;
}
public void setxName( java.lang.String xName ) {
this.xName = xName;
}
private java.lang.String byWeekNoList;
public java.lang.String getbyWeekNoList() {
return byWeekNoList;
}
public void setbyWeekNoList( java.lang.String byWeekNoList ) {
this.byWeekNoList = byWeekNoList;
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
private java.sql.Timestamp untilDateTime;
public java.sql.Timestamp getuntilDateTime() {
return untilDateTime;
}
public void setuntilDateTime( java.sql.Timestamp untilDateTime ) {
this.untilDateTime = untilDateTime;
}
private java.lang.String weekStart;
public java.lang.String getweekStart() {
return weekStart;
}
public void setweekStart( java.lang.String weekStart ) {
this.weekStart = weekStart;
}
private java.lang.Long countNumber;
public java.lang.Long getcountNumber() {
return countNumber;
}
public void setcountNumber( java.lang.Long countNumber ) {
this.countNumber = countNumber;
}
private java.lang.String byHourList;
public java.lang.String getbyHourList() {
return byHourList;
}
public void setbyHourList( java.lang.String byHourList ) {
this.byHourList = byHourList;
}
private java.lang.String byYearDayList;
public java.lang.String getbyYearDayList() {
return byYearDayList;
}
public void setbyYearDayList( java.lang.String byYearDayList ) {
this.byYearDayList = byYearDayList;
}
private java.lang.String recurrenceRuleId;
public java.lang.String getrecurrenceRuleId() {
return recurrenceRuleId;
}
public void setrecurrenceRuleId( java.lang.String recurrenceRuleId ) {
this.recurrenceRuleId = recurrenceRuleId;
}
private java.lang.String byMonthList;
public java.lang.String getbyMonthList() {
return byMonthList;
}
public void setbyMonthList( java.lang.String byMonthList ) {
this.byMonthList = byMonthList;
}
private java.lang.String byDayList;
public java.lang.String getbyDayList() {
return byDayList;
}
public void setbyDayList( java.lang.String byDayList ) {
this.byDayList = byDayList;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RecurrenceRule> objectList = new ArrayList<RecurrenceRule>();
        for (GenericValue genVal : genList) {
            objectList.add(new RecurrenceRule(genVal));
        }
        return objectList;
    }    
}
