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
public class ReturnStatus implements GenericValueObjectInterface {
public static final String module =ReturnStatus.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ReturnStatus(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
//Debug.logInfo(ex, module);
}
}
public ReturnStatus () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"statusId","Status Id"},
{"createdTxStamp","Created Tx Stamp"},
{"returnStatusId","Return Status Id"},
{"createdStamp","Created Stamp"},
{"returnId","Return Id"},
{"returnItemSeqId","Return Item Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"changeByUserLoginId","Change By User Login Id"},
{"statusDatetime","Status Datetime"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.statusId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.returnStatusId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.returnId = "";
this.returnItemSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.changeByUserLoginId = "";
this.statusDatetime = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.statusId = (java.lang.String) genVal.get("statusId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.returnStatusId = (java.lang.String) genVal.get("returnStatusId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.returnId = (java.lang.String) genVal.get("returnId");
this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.changeByUserLoginId = (java.lang.String) genVal.get("changeByUserLoginId");
this.statusDatetime = (java.sql.Timestamp) genVal.get("statusDatetime");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("returnStatusId",OrderMaxUtility.getValidEntityString(this.returnStatusId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("returnId",OrderMaxUtility.getValidEntityString(this.returnId));
val.set("returnItemSeqId",OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("changeByUserLoginId",OrderMaxUtility.getValidEntityString(this.changeByUserLoginId));
val.set("statusDatetime",OrderMaxUtility.getValidEntityTimestamp(this.statusDatetime));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("returnStatusId",this.returnStatusId);
valueMap.put("returnId",this.returnId);
valueMap.put("returnItemSeqId",this.returnItemSeqId);
valueMap.put("changeByUserLoginId",this.changeByUserLoginId);
valueMap.put("statusDatetime",this.statusDatetime);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ReturnStatus");
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
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String returnStatusId;
public java.lang.String getreturnStatusId() {
return returnStatusId;
}
public void setreturnStatusId( java.lang.String returnStatusId ) {
this.returnStatusId = returnStatusId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String returnId;
public java.lang.String getreturnId() {
return returnId;
}
public void setreturnId( java.lang.String returnId ) {
this.returnId = returnId;
}
private java.lang.String returnItemSeqId;
public java.lang.String getreturnItemSeqId() {
return returnItemSeqId;
}
public void setreturnItemSeqId( java.lang.String returnItemSeqId ) {
this.returnItemSeqId = returnItemSeqId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String changeByUserLoginId;
public java.lang.String getchangeByUserLoginId() {
return changeByUserLoginId;
}
public void setchangeByUserLoginId( java.lang.String changeByUserLoginId ) {
this.changeByUserLoginId = changeByUserLoginId;
}
private java.sql.Timestamp statusDatetime;
public java.sql.Timestamp getstatusDatetime() {
return statusDatetime;
}
public void setstatusDatetime( java.sql.Timestamp statusDatetime ) {
this.statusDatetime = statusDatetime;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ReturnStatus> objectList = new ArrayList<ReturnStatus>();
        for (GenericValue genVal : genList) {
            objectList.add(new ReturnStatus(genVal));
        }
        return objectList;
    }    
}
