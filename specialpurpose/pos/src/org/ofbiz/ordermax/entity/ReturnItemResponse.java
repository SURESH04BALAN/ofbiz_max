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
public class ReturnItemResponse implements GenericValueObjectInterface {
public static final String module =ReturnItemResponse.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ReturnItemResponse(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
//Debug.logInfo(ex, module);
}
}
public ReturnItemResponse () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"replacementOrderId","Replacement Order Id"},
{"finAccountTransId","Fin Account Trans Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"billingAccountId","Billing Account Id"},
{"responseDate","Response Date"},
{"returnItemResponseId","Return Item Response Id"},
{"orderPaymentPreferenceId","Order Payment Preference Id"},
{"responseAmount","Response Amount"},
{"paymentId","Payment Id"},
};
protected void initObject(){
this.replacementOrderId = "";
this.finAccountTransId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.billingAccountId = "";
this.responseDate = UtilDateTime.nowTimestamp();
this.returnItemResponseId = "";
this.orderPaymentPreferenceId = "";
this.responseAmount = java.math.BigDecimal.ZERO;
this.paymentId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.replacementOrderId = (java.lang.String) genVal.get("replacementOrderId");
this.finAccountTransId = (java.lang.String) genVal.get("finAccountTransId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.responseDate = (java.sql.Timestamp) genVal.get("responseDate");
this.returnItemResponseId = (java.lang.String) genVal.get("returnItemResponseId");
this.orderPaymentPreferenceId = (java.lang.String) genVal.get("orderPaymentPreferenceId");
this.responseAmount = (java.math.BigDecimal) genVal.get("responseAmount");
this.paymentId = (java.lang.String) genVal.get("paymentId");
}
protected void getGenericValue(GenericValue val) {
val.set("replacementOrderId",OrderMaxUtility.getValidEntityString(this.replacementOrderId));
val.set("finAccountTransId",OrderMaxUtility.getValidEntityString(this.finAccountTransId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("responseDate",OrderMaxUtility.getValidEntityTimestamp(this.responseDate));
val.set("returnItemResponseId",OrderMaxUtility.getValidEntityString(this.returnItemResponseId));
val.set("orderPaymentPreferenceId",OrderMaxUtility.getValidEntityString(this.orderPaymentPreferenceId));
val.set("responseAmount",OrderMaxUtility.getValidEntityBigDecimal(this.responseAmount));
val.set("paymentId",OrderMaxUtility.getValidEntityString(this.paymentId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("replacementOrderId",this.replacementOrderId);
valueMap.put("finAccountTransId",this.finAccountTransId);
valueMap.put("billingAccountId",this.billingAccountId);
valueMap.put("responseDate",this.responseDate);
valueMap.put("returnItemResponseId",this.returnItemResponseId);
valueMap.put("orderPaymentPreferenceId",this.orderPaymentPreferenceId);
valueMap.put("responseAmount",this.responseAmount);
valueMap.put("paymentId",this.paymentId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ReturnItemResponse");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String replacementOrderId;
public java.lang.String getReplacementOrderId() {
return replacementOrderId;
}
public void setReplacementOrderId( java.lang.String replacementOrderId ) {
this.replacementOrderId = replacementOrderId;
}
private java.lang.String finAccountTransId;
public java.lang.String getFinAccountTransId() {
return finAccountTransId;
}
public void setFinAccountTransId( java.lang.String finAccountTransId ) {
this.finAccountTransId = finAccountTransId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getLastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setLastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getCreatedTxStamp() {
return createdTxStamp;
}
public void setCreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getCreatedStamp() {
return createdStamp;
}
public void setCreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getLastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setLastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String billingAccountId;
public java.lang.String getBillingAccountId() {
return billingAccountId;
}
public void setBillingAccountId( java.lang.String billingAccountId ) {
this.billingAccountId = billingAccountId;
}
private java.sql.Timestamp responseDate;
public java.sql.Timestamp getResponseDate() {
return responseDate;
}
public void setResponseDate( java.sql.Timestamp responseDate ) {
this.responseDate = responseDate;
}
private java.lang.String returnItemResponseId;
public java.lang.String getReturnItemResponseId() {
return returnItemResponseId;
}
public void setReturnItemResponseId( java.lang.String returnItemResponseId ) {
this.returnItemResponseId = returnItemResponseId;
}
private java.lang.String orderPaymentPreferenceId;
public java.lang.String getOrderPaymentPreferenceId() {
return orderPaymentPreferenceId;
}
public void setOrderPaymentPreferenceId( java.lang.String orderPaymentPreferenceId ) {
this.orderPaymentPreferenceId = orderPaymentPreferenceId;
}
private java.math.BigDecimal responseAmount;
public java.math.BigDecimal getResponseAmount() {
return responseAmount;
}
public void setResponseAmount( java.math.BigDecimal responseAmount ) {
this.responseAmount = responseAmount;
}
private java.lang.String paymentId;
public java.lang.String getPaymentId() {
return paymentId;
}
public void setPaymentId( java.lang.String paymentId ) {
this.paymentId = paymentId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ReturnItemResponse> objectList = new ArrayList<ReturnItemResponse>();
        for (GenericValue genVal : genList) {
            objectList.add(new ReturnItemResponse(genVal));
        }
        return objectList;
    }    
}
