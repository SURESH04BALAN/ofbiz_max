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
public class PicklistStatusHistory implements GenericValueObjectInterface {
public static final String module =PicklistStatusHistory.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PicklistStatusHistory(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PicklistStatusHistory () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"changeDate","Change Date"},
{"changeUserLoginId","Change User Login Id"},
{"statusIdTo","Status Id To"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"picklistId","Picklist Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.statusId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.changeDate = UtilDateTime.nowTimestamp();
this.changeUserLoginId = "";
this.statusIdTo = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.picklistId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.changeDate = (java.sql.Timestamp) genVal.get("changeDate");
this.changeUserLoginId = (java.lang.String) genVal.get("changeUserLoginId");
this.statusIdTo = (java.lang.String) genVal.get("statusIdTo");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.picklistId = (java.lang.String) genVal.get("picklistId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("changeDate",OrderMaxUtility.getValidEntityTimestamp(this.changeDate));
val.set("changeUserLoginId",OrderMaxUtility.getValidEntityString(this.changeUserLoginId));
val.set("statusIdTo",OrderMaxUtility.getValidEntityString(this.statusIdTo));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("picklistId",OrderMaxUtility.getValidEntityString(this.picklistId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("changeDate",this.changeDate);
valueMap.put("changeUserLoginId",this.changeUserLoginId);
valueMap.put("statusIdTo",this.statusIdTo);
valueMap.put("picklistId",this.picklistId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PicklistStatusHistory");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.sql.Timestamp changeDate;
public java.sql.Timestamp getchangeDate() {
return changeDate;
}
public void setchangeDate( java.sql.Timestamp changeDate ) {
this.changeDate = changeDate;
}
private java.lang.String changeUserLoginId;
public java.lang.String getchangeUserLoginId() {
return changeUserLoginId;
}
public void setchangeUserLoginId( java.lang.String changeUserLoginId ) {
this.changeUserLoginId = changeUserLoginId;
}
private java.lang.String statusIdTo;
public java.lang.String getstatusIdTo() {
return statusIdTo;
}
public void setstatusIdTo( java.lang.String statusIdTo ) {
this.statusIdTo = statusIdTo;
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
private java.lang.String picklistId;
public java.lang.String getpicklistId() {
return picklistId;
}
public void setpicklistId( java.lang.String picklistId ) {
this.picklistId = picklistId;
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
        Collection<PicklistStatusHistory> objectList = new ArrayList<PicklistStatusHistory>();
        for (GenericValue genVal : genList) {
            objectList.add(new PicklistStatusHistory(genVal));
        }
        return objectList;
    }    
}
