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
public class CustomTimePeriod implements GenericValueObjectInterface {
public static final String module =CustomTimePeriod.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CustomTimePeriod(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CustomTimePeriod () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"periodNum","Period Num"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"organizationPartyId","Organization Party Id"},
{"thruDate","Thru Date"},
{"parentPeriodId","Parent Period Id"},
{"periodTypeId","Period Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"fromDate","From Date"},
{"createdStamp","Created Stamp"},
{"customTimePeriodId","Custom Time Period Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"isClosed","Is Closed"},
{"periodName","Period Name"},
};
protected void initObject(){
this.periodNum = new Long(0);
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.organizationPartyId = "";
this.thruDate =  new java.sql.Date(System.currentTimeMillis());
this.parentPeriodId = "";
this.periodTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.fromDate =  new java.sql.Date(System.currentTimeMillis());
this.createdStamp = UtilDateTime.nowTimestamp();
this.customTimePeriodId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.isClosed = "";
this.periodName = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.periodNum = (java.lang.Long) genVal.get("periodNum");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.thruDate = (java.sql.Date) genVal.get("thruDate");
this.parentPeriodId = (java.lang.String) genVal.get("parentPeriodId");
this.periodTypeId = (java.lang.String) genVal.get("periodTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.fromDate = (java.sql.Date) genVal.get("fromDate");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.customTimePeriodId = (java.lang.String) genVal.get("customTimePeriodId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.isClosed = (java.lang.String) genVal.get("isClosed");
this.periodName = (java.lang.String) genVal.get("periodName");
}
protected void getGenericValue(GenericValue val) {
val.set("periodNum",this.periodNum);
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("thruDate",OrderMaxUtility.getValidEntityDate(this.thruDate));
val.set("parentPeriodId",OrderMaxUtility.getValidEntityString(this.parentPeriodId));
val.set("periodTypeId",OrderMaxUtility.getValidEntityString(this.periodTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("fromDate",OrderMaxUtility.getValidEntityDate(this.fromDate));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("customTimePeriodId",OrderMaxUtility.getValidEntityString(this.customTimePeriodId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("isClosed",OrderMaxUtility.getValidEntityString(this.isClosed));
val.set("periodName",OrderMaxUtility.getValidEntityString(this.periodName));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("periodNum",this.periodNum);
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("parentPeriodId",this.parentPeriodId);
valueMap.put("periodTypeId",this.periodTypeId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("customTimePeriodId",this.customTimePeriodId);
valueMap.put("isClosed",this.isClosed);
valueMap.put("periodName",this.periodName);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CustomTimePeriod");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long periodNum;
public java.lang.Long getperiodNum() {
return periodNum;
}
public void setperiodNum( java.lang.Long periodNum ) {
this.periodNum = periodNum;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.sql.Date thruDate;
public java.sql.Date getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Date thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String parentPeriodId;
public java.lang.String getparentPeriodId() {
return parentPeriodId;
}
public void setparentPeriodId( java.lang.String parentPeriodId ) {
this.parentPeriodId = parentPeriodId;
}
private java.lang.String periodTypeId;
public java.lang.String getperiodTypeId() {
return periodTypeId;
}
public void setperiodTypeId( java.lang.String periodTypeId ) {
this.periodTypeId = periodTypeId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Date fromDate;
public java.sql.Date getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Date fromDate ) {
this.fromDate = fromDate;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String customTimePeriodId;
public java.lang.String getcustomTimePeriodId() {
return customTimePeriodId;
}
public void setcustomTimePeriodId( java.lang.String customTimePeriodId ) {
this.customTimePeriodId = customTimePeriodId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String isClosed;
public java.lang.String getisClosed() {
return isClosed;
}
public void setisClosed( java.lang.String isClosed ) {
this.isClosed = isClosed;
}
private java.lang.String periodName;
public java.lang.String getperiodName() {
return periodName;
}
public void setperiodName( java.lang.String periodName ) {
this.periodName = periodName;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CustomTimePeriod> objectList = new ArrayList<CustomTimePeriod>();
        for (GenericValue genVal : genList) {
            objectList.add(new CustomTimePeriod(genVal));
        }
        return objectList;
    }    
}
