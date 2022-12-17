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
public class OrderStatus implements GenericValueObjectInterface {
public static final String module =OrderStatus.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderStatus(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderStatus () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"orderPaymentPreferenceId","Order Payment Preference Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"statusDatetime","Status Datetime"},
{"orderStatusId","Order Status Id"},
{"statusId","Status Id"},
{"statusUserLogin","Status User Login"},
{"changeReason","Change Reason"},
{"orderItemSeqId","Order Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"orderId","Order Id"},
};
protected void initObject(){
this.orderPaymentPreferenceId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.statusDatetime = UtilDateTime.nowTimestamp();
this.orderStatusId = "";
this.statusId = "";
this.statusUserLogin = "";
this.changeReason = "";
this.orderItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.orderPaymentPreferenceId = (java.lang.String) genVal.get("orderPaymentPreferenceId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.statusDatetime = (java.sql.Timestamp) genVal.get("statusDatetime");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.statusUserLogin = (java.lang.String) genVal.get("statusUserLogin");
this.changeReason = (java.lang.String) genVal.get("changeReason");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("orderPaymentPreferenceId",OrderMaxUtility.getValidEntityString(this.orderPaymentPreferenceId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("statusDatetime",OrderMaxUtility.getValidEntityTimestamp(this.statusDatetime));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("statusUserLogin",OrderMaxUtility.getValidEntityString(this.statusUserLogin));
val.set("changeReason",OrderMaxUtility.getValidEntityString(this.changeReason));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderPaymentPreferenceId",this.orderPaymentPreferenceId);
valueMap.put("statusDatetime",this.statusDatetime);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("statusId",this.statusId);
valueMap.put("statusUserLogin",this.statusUserLogin);
valueMap.put("changeReason",this.changeReason);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderStatus");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String orderPaymentPreferenceId;
public java.lang.String getorderPaymentPreferenceId() {
return orderPaymentPreferenceId;
}
public void setorderPaymentPreferenceId( java.lang.String orderPaymentPreferenceId ) {
this.orderPaymentPreferenceId = orderPaymentPreferenceId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.sql.Timestamp statusDatetime;
public java.sql.Timestamp getstatusDatetime() {
return statusDatetime;
}
public void setstatusDatetime( java.sql.Timestamp statusDatetime ) {
this.statusDatetime = statusDatetime;
}
private java.lang.String orderStatusId;
public java.lang.String getorderStatusId() {
return orderStatusId;
}
public void setorderStatusId( java.lang.String orderStatusId ) {
this.orderStatusId = orderStatusId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String statusUserLogin;
public java.lang.String getstatusUserLogin() {
return statusUserLogin;
}
public void setstatusUserLogin( java.lang.String statusUserLogin ) {
this.statusUserLogin = statusUserLogin;
}
private java.lang.String changeReason;
public java.lang.String getchangeReason() {
return changeReason;
}
public void setchangeReason( java.lang.String changeReason ) {
this.changeReason = changeReason;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
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
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderStatus> objectList = new ArrayList<OrderStatus>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderStatus(genVal));
        }
        return objectList;
    }    
}
