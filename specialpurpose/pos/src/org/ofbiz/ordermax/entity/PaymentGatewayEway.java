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
public class PaymentGatewayEway implements GenericValueObjectInterface {
public static final String module =PaymentGatewayEway.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayEway(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayEway () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"enableBeagle","Enable Beagle"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"customerId","Customer Id"},
{"createdTxStamp","Created Tx Stamp"},
{"refundPwd","Refund Pwd"},
{"enableCvn","Enable Cvn"},
{"createdStamp","Created Stamp"},
{"testMode","Test Mode"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.enableBeagle = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.customerId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.refundPwd = "";
this.enableCvn = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.testMode = "";
this.paymentGatewayConfigId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.enableBeagle = (java.lang.String) genVal.get("enableBeagle");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.customerId = (java.lang.String) genVal.get("customerId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.refundPwd = (java.lang.String) genVal.get("refundPwd");
this.enableCvn = (java.lang.String) genVal.get("enableCvn");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.testMode = (java.lang.String) genVal.get("testMode");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("enableBeagle",OrderMaxUtility.getValidEntityString(this.enableBeagle));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("customerId",OrderMaxUtility.getValidEntityString(this.customerId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("refundPwd",OrderMaxUtility.getValidEntityString(this.refundPwd));
val.set("enableCvn",OrderMaxUtility.getValidEntityString(this.enableCvn));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("testMode",OrderMaxUtility.getValidEntityString(this.testMode));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("enableBeagle",this.enableBeagle);
valueMap.put("customerId",this.customerId);
valueMap.put("refundPwd",this.refundPwd);
valueMap.put("enableCvn",this.enableCvn);
valueMap.put("testMode",this.testMode);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayEway");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String enableBeagle;
public java.lang.String getenableBeagle() {
return enableBeagle;
}
public void setenableBeagle( java.lang.String enableBeagle ) {
this.enableBeagle = enableBeagle;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String customerId;
public java.lang.String getcustomerId() {
return customerId;
}
public void setcustomerId( java.lang.String customerId ) {
this.customerId = customerId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String refundPwd;
public java.lang.String getrefundPwd() {
return refundPwd;
}
public void setrefundPwd( java.lang.String refundPwd ) {
this.refundPwd = refundPwd;
}
private java.lang.String enableCvn;
public java.lang.String getenableCvn() {
return enableCvn;
}
public void setenableCvn( java.lang.String enableCvn ) {
this.enableCvn = enableCvn;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String testMode;
public java.lang.String gettestMode() {
return testMode;
}
public void settestMode( java.lang.String testMode ) {
this.testMode = testMode;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
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
        Collection<PaymentGatewayEway> objectList = new ArrayList<PaymentGatewayEway>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayEway(genVal));
        }
        return objectList;
    }    
}
