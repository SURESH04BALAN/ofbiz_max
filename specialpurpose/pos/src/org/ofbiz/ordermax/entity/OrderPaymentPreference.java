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
public class OrderPaymentPreference implements GenericValueObjectInterface {
public static final String module =OrderPaymentPreference.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderPaymentPreference(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderPaymentPreference () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"overflowFlag","Overflow Flag"},
{"needsNsfRetry","Needs Nsf Retry"},
{"paymentMethodTypeId","Payment Method Type Id"},
{"manualRefNum","Manual Ref Num"},
{"statusId","Status Id"},
{"manualAuthCode","Manual Auth Code"},
{"createdTxStamp","Created Tx Stamp"},
{"billingPostalCode","Billing Postal Code"},
{"createdStamp","Created Stamp"},
{"paymentMethodId","Payment Method Id"},
{"productPricePurposeId","Product Price Purpose Id"},
{"createdDate","Created Date"},
{"processAttempt","Process Attempt"},
{"orderId","Order Id"},
{"orderPaymentPreferenceId","Order Payment Preference Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"swipedFlag","Swiped Flag"},
{"maxAmount","Max Amount"},
{"track2","Track 2"},
{"finAccountId","Fin Account Id"},
{"presentFlag","Present Flag"},
{"securityCode","Security Code"},
{"orderItemSeqId","Order Item Seq Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.overflowFlag = "";
this.needsNsfRetry = "";
this.paymentMethodTypeId = "";
this.manualRefNum = "";
this.statusId = "";
this.manualAuthCode = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.billingPostalCode = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.paymentMethodId = "";
this.productPricePurposeId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.processAttempt = "";
this.orderId = "";
this.orderPaymentPreferenceId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.swipedFlag = "";
this.maxAmount = java.math.BigDecimal.ZERO;
this.track2 = "";
this.finAccountId = "";
this.presentFlag = "";
this.securityCode = "";
this.orderItemSeqId = "";
this.shipGroupSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.overflowFlag = (java.lang.String) genVal.get("overflowFlag");
this.needsNsfRetry = (java.lang.String) genVal.get("needsNsfRetry");
this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
this.manualRefNum = (java.lang.String) genVal.get("manualRefNum");
this.statusId = (java.lang.String) genVal.get("statusId");
this.manualAuthCode = (java.lang.String) genVal.get("manualAuthCode");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.billingPostalCode = (java.lang.String) genVal.get("billingPostalCode");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
this.productPricePurposeId = (java.lang.String) genVal.get("productPricePurposeId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.processAttempt = (java.lang.String) genVal.get("processAttempt");
this.orderId = (java.lang.String) genVal.get("orderId");
this.orderPaymentPreferenceId = (java.lang.String) genVal.get("orderPaymentPreferenceId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.swipedFlag = (java.lang.String) genVal.get("swipedFlag");
this.maxAmount = (java.math.BigDecimal) genVal.get("maxAmount");
this.track2 = (java.lang.String) genVal.get("track2");
this.finAccountId = (java.lang.String) genVal.get("finAccountId");
this.presentFlag = (java.lang.String) genVal.get("presentFlag");
this.securityCode = (java.lang.String) genVal.get("securityCode");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("overflowFlag",OrderMaxUtility.getValidEntityString(this.overflowFlag));
val.set("needsNsfRetry",OrderMaxUtility.getValidEntityString(this.needsNsfRetry));
val.set("paymentMethodTypeId",OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
val.set("manualRefNum",OrderMaxUtility.getValidEntityString(this.manualRefNum));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("manualAuthCode",OrderMaxUtility.getValidEntityString(this.manualAuthCode));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("billingPostalCode",OrderMaxUtility.getValidEntityString(this.billingPostalCode));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("paymentMethodId",OrderMaxUtility.getValidEntityString(this.paymentMethodId));
val.set("productPricePurposeId",OrderMaxUtility.getValidEntityString(this.productPricePurposeId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("processAttempt",OrderMaxUtility.getValidEntityString(this.processAttempt));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("orderPaymentPreferenceId",OrderMaxUtility.getValidEntityString(this.orderPaymentPreferenceId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("swipedFlag",OrderMaxUtility.getValidEntityString(this.swipedFlag));
val.set("maxAmount",OrderMaxUtility.getValidEntityBigDecimal(this.maxAmount));
val.set("track2",OrderMaxUtility.getValidEntityString(this.track2));
val.set("finAccountId",OrderMaxUtility.getValidEntityString(this.finAccountId));
val.set("presentFlag",OrderMaxUtility.getValidEntityString(this.presentFlag));
val.set("securityCode",OrderMaxUtility.getValidEntityString(this.securityCode));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("overflowFlag",this.overflowFlag);
valueMap.put("needsNsfRetry",this.needsNsfRetry);
valueMap.put("paymentMethodTypeId",this.paymentMethodTypeId);
valueMap.put("manualRefNum",this.manualRefNum);
valueMap.put("statusId",this.statusId);
valueMap.put("manualAuthCode",this.manualAuthCode);
valueMap.put("billingPostalCode",this.billingPostalCode);
valueMap.put("paymentMethodId",this.paymentMethodId);
valueMap.put("productPricePurposeId",this.productPricePurposeId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("processAttempt",this.processAttempt);
valueMap.put("orderId",this.orderId);
valueMap.put("orderPaymentPreferenceId",this.orderPaymentPreferenceId);
valueMap.put("swipedFlag",this.swipedFlag);
valueMap.put("maxAmount",this.maxAmount);
valueMap.put("track2",this.track2);
valueMap.put("finAccountId",this.finAccountId);
valueMap.put("presentFlag",this.presentFlag);
valueMap.put("securityCode",this.securityCode);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderPaymentPreference");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String overflowFlag;
public java.lang.String getoverflowFlag() {
return overflowFlag;
}
public void setoverflowFlag( java.lang.String overflowFlag ) {
this.overflowFlag = overflowFlag;
}
private java.lang.String needsNsfRetry;
public java.lang.String getneedsNsfRetry() {
return needsNsfRetry;
}
public void setneedsNsfRetry( java.lang.String needsNsfRetry ) {
this.needsNsfRetry = needsNsfRetry;
}
private java.lang.String paymentMethodTypeId;
public java.lang.String getpaymentMethodTypeId() {
return paymentMethodTypeId;
}
public void setpaymentMethodTypeId( java.lang.String paymentMethodTypeId ) {
this.paymentMethodTypeId = paymentMethodTypeId;
}
private java.lang.String manualRefNum;
public java.lang.String getmanualRefNum() {
return manualRefNum;
}
public void setmanualRefNum( java.lang.String manualRefNum ) {
this.manualRefNum = manualRefNum;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String manualAuthCode;
public java.lang.String getmanualAuthCode() {
return manualAuthCode;
}
public void setmanualAuthCode( java.lang.String manualAuthCode ) {
this.manualAuthCode = manualAuthCode;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String billingPostalCode;
public java.lang.String getbillingPostalCode() {
return billingPostalCode;
}
public void setbillingPostalCode( java.lang.String billingPostalCode ) {
this.billingPostalCode = billingPostalCode;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String paymentMethodId;
public java.lang.String getpaymentMethodId() {
return paymentMethodId;
}
public void setpaymentMethodId( java.lang.String paymentMethodId ) {
this.paymentMethodId = paymentMethodId;
}
private java.lang.String productPricePurposeId;
public java.lang.String getproductPricePurposeId() {
return productPricePurposeId;
}
public void setproductPricePurposeId( java.lang.String productPricePurposeId ) {
this.productPricePurposeId = productPricePurposeId;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String processAttempt;
public java.lang.String getprocessAttempt() {
return processAttempt;
}
public void setprocessAttempt( java.lang.String processAttempt ) {
this.processAttempt = processAttempt;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
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
private java.lang.String swipedFlag;
public java.lang.String getswipedFlag() {
return swipedFlag;
}
public void setswipedFlag( java.lang.String swipedFlag ) {
this.swipedFlag = swipedFlag;
}
private java.math.BigDecimal maxAmount;
public java.math.BigDecimal getmaxAmount() {
return maxAmount;
}
public void setmaxAmount( java.math.BigDecimal maxAmount ) {
this.maxAmount = maxAmount;
}
private java.lang.String track2;
public java.lang.String gettrack2() {
return track2;
}
public void settrack2( java.lang.String track2 ) {
this.track2 = track2;
}
private java.lang.String finAccountId;
public java.lang.String getfinAccountId() {
return finAccountId;
}
public void setfinAccountId( java.lang.String finAccountId ) {
this.finAccountId = finAccountId;
}
private java.lang.String presentFlag;
public java.lang.String getpresentFlag() {
return presentFlag;
}
public void setpresentFlag( java.lang.String presentFlag ) {
this.presentFlag = presentFlag;
}
private java.lang.String securityCode;
public java.lang.String getsecurityCode() {
return securityCode;
}
public void setsecurityCode( java.lang.String securityCode ) {
this.securityCode = securityCode;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderPaymentPreference> objectList = new ArrayList<OrderPaymentPreference>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderPaymentPreference(genVal));
        }
        return objectList;
    }    
}
