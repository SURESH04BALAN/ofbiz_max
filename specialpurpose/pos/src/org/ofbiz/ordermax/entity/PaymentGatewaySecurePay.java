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
public class PaymentGatewaySecurePay implements GenericValueObjectInterface {
public static final String module =PaymentGatewaySecurePay.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewaySecurePay(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewaySecurePay () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"processTimeout","Process Timeout"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"pwd","Pwd"},
{"enableAmountRound","Enable Amount Round"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"serverURL","Server Url"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"merchantId","Merchant Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.processTimeout = new Long(0);
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.pwd = "";
this.enableAmountRound = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.serverURL = "";
this.paymentGatewayConfigId = "";
this.merchantId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.processTimeout = (java.lang.Long) genVal.get("processTimeout");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.pwd = (java.lang.String) genVal.get("pwd");
this.enableAmountRound = (java.lang.String) genVal.get("enableAmountRound");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.serverURL = (java.lang.String) genVal.get("serverURL");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.merchantId = (java.lang.String) genVal.get("merchantId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("processTimeout",this.processTimeout);
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("pwd",OrderMaxUtility.getValidEntityString(this.pwd));
val.set("enableAmountRound",OrderMaxUtility.getValidEntityString(this.enableAmountRound));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("serverURL",OrderMaxUtility.getValidEntityString(this.serverURL));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("merchantId",OrderMaxUtility.getValidEntityString(this.merchantId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("processTimeout",this.processTimeout);
valueMap.put("pwd",this.pwd);
valueMap.put("enableAmountRound",this.enableAmountRound);
valueMap.put("serverURL",this.serverURL);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("merchantId",this.merchantId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewaySecurePay");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long processTimeout;
public java.lang.Long getprocessTimeout() {
return processTimeout;
}
public void setprocessTimeout( java.lang.Long processTimeout ) {
this.processTimeout = processTimeout;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String pwd;
public java.lang.String getpwd() {
return pwd;
}
public void setpwd( java.lang.String pwd ) {
this.pwd = pwd;
}
private java.lang.String enableAmountRound;
public java.lang.String getenableAmountRound() {
return enableAmountRound;
}
public void setenableAmountRound( java.lang.String enableAmountRound ) {
this.enableAmountRound = enableAmountRound;
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
private java.lang.String serverURL;
public java.lang.String getserverURL() {
return serverURL;
}
public void setserverURL( java.lang.String serverURL ) {
this.serverURL = serverURL;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String merchantId;
public java.lang.String getmerchantId() {
return merchantId;
}
public void setmerchantId( java.lang.String merchantId ) {
this.merchantId = merchantId;
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
        Collection<PaymentGatewaySecurePay> objectList = new ArrayList<PaymentGatewaySecurePay>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewaySecurePay(genVal));
        }
        return objectList;
    }    
}
