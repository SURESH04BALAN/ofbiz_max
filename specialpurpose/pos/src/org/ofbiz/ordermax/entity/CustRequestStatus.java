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
public class CustRequestStatus implements GenericValueObjectInterface {
public static final String module =CustRequestStatus.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CustRequestStatus(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CustRequestStatus () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"custRequestId","Cust Request Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"custRequestStatusId","Cust Request Status Id"},
{"statusDatetime","Status Datetime"},
{"custRequestItemSeqId","Cust Request Item Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.statusId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.custRequestId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.custRequestStatusId = "";
this.statusDatetime = UtilDateTime.nowTimestamp();
this.custRequestItemSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.custRequestId = (java.lang.String) genVal.get("custRequestId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.custRequestStatusId = (java.lang.String) genVal.get("custRequestStatusId");
this.statusDatetime = (java.sql.Timestamp) genVal.get("statusDatetime");
this.custRequestItemSeqId = (java.lang.String) genVal.get("custRequestItemSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("custRequestId",OrderMaxUtility.getValidEntityString(this.custRequestId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("custRequestStatusId",OrderMaxUtility.getValidEntityString(this.custRequestStatusId));
val.set("statusDatetime",OrderMaxUtility.getValidEntityTimestamp(this.statusDatetime));
val.set("custRequestItemSeqId",OrderMaxUtility.getValidEntityString(this.custRequestItemSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("custRequestId",this.custRequestId);
valueMap.put("custRequestStatusId",this.custRequestStatusId);
valueMap.put("statusDatetime",this.statusDatetime);
valueMap.put("custRequestItemSeqId",this.custRequestItemSeqId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CustRequestStatus");
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
private java.lang.String custRequestId;
public java.lang.String getcustRequestId() {
return custRequestId;
}
public void setcustRequestId( java.lang.String custRequestId ) {
this.custRequestId = custRequestId;
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
private java.lang.String custRequestStatusId;
public java.lang.String getcustRequestStatusId() {
return custRequestStatusId;
}
public void setcustRequestStatusId( java.lang.String custRequestStatusId ) {
this.custRequestStatusId = custRequestStatusId;
}
private java.sql.Timestamp statusDatetime;
public java.sql.Timestamp getstatusDatetime() {
return statusDatetime;
}
public void setstatusDatetime( java.sql.Timestamp statusDatetime ) {
this.statusDatetime = statusDatetime;
}
private java.lang.String custRequestItemSeqId;
public java.lang.String getcustRequestItemSeqId() {
return custRequestItemSeqId;
}
public void setcustRequestItemSeqId( java.lang.String custRequestItemSeqId ) {
this.custRequestItemSeqId = custRequestItemSeqId;
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
        Collection<CustRequestStatus> objectList = new ArrayList<CustRequestStatus>();
        for (GenericValue genVal : genList) {
            objectList.add(new CustRequestStatus(genVal));
        }
        return objectList;
    }    
}
