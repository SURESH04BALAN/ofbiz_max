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
public class TechDataCalendarWeek implements GenericValueObjectInterface {
public static final String module =TechDataCalendarWeek.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public TechDataCalendarWeek(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public TechDataCalendarWeek () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"tuesdayCapacity","Tuesday Capacity"},
{"mondayStartTime","Monday Start Time"},
{"fridayCapacity","Friday Capacity"},
{"saturdayCapacity","Saturday Capacity"},
{"fridayStartTime","Friday Start Time"},
{"wednesdayCapacity","Wednesday Capacity"},
{"thursdayStartTime","Thursday Start Time"},
{"thursdayCapacity","Thursday Capacity"},
{"wednesdayStartTime","Wednesday Start Time"},
{"calendarWeekId","Calendar Week Id"},
{"mondayCapacity","Monday Capacity"},
{"saturdayStartTime","Saturday Start Time"},
{"sundayStartTime","Sunday Start Time"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"tuesdayStartTime","Tuesday Start Time"},
{"sundayCapacity","Sunday Capacity"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.tuesdayCapacity = new Double(0);
this.mondayStartTime =  new java.sql.Time(System.currentTimeMillis());
this.fridayCapacity = new Double(0);
this.saturdayCapacity = "";
this.fridayStartTime =  new java.sql.Time(System.currentTimeMillis());
this.wednesdayCapacity = new Double(0);
this.thursdayStartTime =  new java.sql.Time(System.currentTimeMillis());
this.thursdayCapacity = new Double(0);
this.wednesdayStartTime =  new java.sql.Time(System.currentTimeMillis());
this.calendarWeekId = "";
this.mondayCapacity = new Double(0);
this.saturdayStartTime = "";
this.sundayStartTime = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.tuesdayStartTime =  new java.sql.Time(System.currentTimeMillis());
this.sundayCapacity = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.tuesdayCapacity = (java.lang.Double) genVal.get("tuesdayCapacity");
this.mondayStartTime = (java.sql.Time) genVal.get("mondayStartTime");
this.fridayCapacity = (java.lang.Double) genVal.get("fridayCapacity");
this.saturdayCapacity = (java.lang.String) genVal.get("saturdayCapacity");
this.fridayStartTime = (java.sql.Time) genVal.get("fridayStartTime");
this.wednesdayCapacity = (java.lang.Double) genVal.get("wednesdayCapacity");
this.thursdayStartTime = (java.sql.Time) genVal.get("thursdayStartTime");
this.thursdayCapacity = (java.lang.Double) genVal.get("thursdayCapacity");
this.wednesdayStartTime = (java.sql.Time) genVal.get("wednesdayStartTime");
this.calendarWeekId = (java.lang.String) genVal.get("calendarWeekId");
this.mondayCapacity = (java.lang.Double) genVal.get("mondayCapacity");
this.saturdayStartTime = (java.lang.String) genVal.get("saturdayStartTime");
this.sundayStartTime = (java.lang.String) genVal.get("sundayStartTime");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.tuesdayStartTime = (java.sql.Time) genVal.get("tuesdayStartTime");
this.sundayCapacity = (java.lang.String) genVal.get("sundayCapacity");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("tuesdayCapacity",OrderMaxUtility.getValidEntityDouble(this.tuesdayCapacity));
val.set("mondayStartTime",this.mondayStartTime);
val.set("fridayCapacity",OrderMaxUtility.getValidEntityDouble(this.fridayCapacity));
val.set("saturdayCapacity",OrderMaxUtility.getValidEntityString(this.saturdayCapacity));
val.set("fridayStartTime",this.fridayStartTime);
val.set("wednesdayCapacity",OrderMaxUtility.getValidEntityDouble(this.wednesdayCapacity));
val.set("thursdayStartTime",this.thursdayStartTime);
val.set("thursdayCapacity",OrderMaxUtility.getValidEntityDouble(this.thursdayCapacity));
val.set("wednesdayStartTime",this.wednesdayStartTime);
val.set("calendarWeekId",OrderMaxUtility.getValidEntityString(this.calendarWeekId));
val.set("mondayCapacity",OrderMaxUtility.getValidEntityDouble(this.mondayCapacity));
val.set("saturdayStartTime",OrderMaxUtility.getValidEntityString(this.saturdayStartTime));
val.set("sundayStartTime",OrderMaxUtility.getValidEntityString(this.sundayStartTime));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("tuesdayStartTime",this.tuesdayStartTime);
val.set("sundayCapacity",OrderMaxUtility.getValidEntityString(this.sundayCapacity));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("tuesdayCapacity",this.tuesdayCapacity);
valueMap.put("mondayStartTime",this.mondayStartTime);
valueMap.put("fridayCapacity",this.fridayCapacity);
valueMap.put("saturdayCapacity",this.saturdayCapacity);
valueMap.put("fridayStartTime",this.fridayStartTime);
valueMap.put("wednesdayCapacity",this.wednesdayCapacity);
valueMap.put("thursdayStartTime",this.thursdayStartTime);
valueMap.put("thursdayCapacity",this.thursdayCapacity);
valueMap.put("wednesdayStartTime",this.wednesdayStartTime);
valueMap.put("calendarWeekId",this.calendarWeekId);
valueMap.put("mondayCapacity",this.mondayCapacity);
valueMap.put("saturdayStartTime",this.saturdayStartTime);
valueMap.put("sundayStartTime",this.sundayStartTime);
valueMap.put("description",this.description);
valueMap.put("tuesdayStartTime",this.tuesdayStartTime);
valueMap.put("sundayCapacity",this.sundayCapacity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("TechDataCalendarWeek");
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
private java.lang.Double tuesdayCapacity;
public java.lang.Double gettuesdayCapacity() {
return tuesdayCapacity;
}
public void settuesdayCapacity( java.lang.Double tuesdayCapacity ) {
this.tuesdayCapacity = tuesdayCapacity;
}
private java.sql.Time mondayStartTime;
public java.sql.Time getmondayStartTime() {
return mondayStartTime;
}
public void setmondayStartTime( java.sql.Time mondayStartTime ) {
this.mondayStartTime = mondayStartTime;
}
private java.lang.Double fridayCapacity;
public java.lang.Double getfridayCapacity() {
return fridayCapacity;
}
public void setfridayCapacity( java.lang.Double fridayCapacity ) {
this.fridayCapacity = fridayCapacity;
}
private java.lang.String saturdayCapacity;
public java.lang.String getsaturdayCapacity() {
return saturdayCapacity;
}
public void setsaturdayCapacity( java.lang.String saturdayCapacity ) {
this.saturdayCapacity = saturdayCapacity;
}
private java.sql.Time fridayStartTime;
public java.sql.Time getfridayStartTime() {
return fridayStartTime;
}
public void setfridayStartTime( java.sql.Time fridayStartTime ) {
this.fridayStartTime = fridayStartTime;
}
private java.lang.Double wednesdayCapacity;
public java.lang.Double getwednesdayCapacity() {
return wednesdayCapacity;
}
public void setwednesdayCapacity( java.lang.Double wednesdayCapacity ) {
this.wednesdayCapacity = wednesdayCapacity;
}
private java.sql.Time thursdayStartTime;
public java.sql.Time getthursdayStartTime() {
return thursdayStartTime;
}
public void setthursdayStartTime( java.sql.Time thursdayStartTime ) {
this.thursdayStartTime = thursdayStartTime;
}
private java.lang.Double thursdayCapacity;
public java.lang.Double getthursdayCapacity() {
return thursdayCapacity;
}
public void setthursdayCapacity( java.lang.Double thursdayCapacity ) {
this.thursdayCapacity = thursdayCapacity;
}
private java.sql.Time wednesdayStartTime;
public java.sql.Time getwednesdayStartTime() {
return wednesdayStartTime;
}
public void setwednesdayStartTime( java.sql.Time wednesdayStartTime ) {
this.wednesdayStartTime = wednesdayStartTime;
}
private java.lang.String calendarWeekId;
public java.lang.String getcalendarWeekId() {
return calendarWeekId;
}
public void setcalendarWeekId( java.lang.String calendarWeekId ) {
this.calendarWeekId = calendarWeekId;
}
private java.lang.Double mondayCapacity;
public java.lang.Double getmondayCapacity() {
return mondayCapacity;
}
public void setmondayCapacity( java.lang.Double mondayCapacity ) {
this.mondayCapacity = mondayCapacity;
}
private java.lang.String saturdayStartTime;
public java.lang.String getsaturdayStartTime() {
return saturdayStartTime;
}
public void setsaturdayStartTime( java.lang.String saturdayStartTime ) {
this.saturdayStartTime = saturdayStartTime;
}
private java.lang.String sundayStartTime;
public java.lang.String getsundayStartTime() {
return sundayStartTime;
}
public void setsundayStartTime( java.lang.String sundayStartTime ) {
this.sundayStartTime = sundayStartTime;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Time tuesdayStartTime;
public java.sql.Time gettuesdayStartTime() {
return tuesdayStartTime;
}
public void settuesdayStartTime( java.sql.Time tuesdayStartTime ) {
this.tuesdayStartTime = tuesdayStartTime;
}
private java.lang.String sundayCapacity;
public java.lang.String getsundayCapacity() {
return sundayCapacity;
}
public void setsundayCapacity( java.lang.String sundayCapacity ) {
this.sundayCapacity = sundayCapacity;
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
        Collection<TechDataCalendarWeek> objectList = new ArrayList<TechDataCalendarWeek>();
        for (GenericValue genVal : genList) {
            objectList.add(new TechDataCalendarWeek(genVal));
        }
        return objectList;
    }    
}
