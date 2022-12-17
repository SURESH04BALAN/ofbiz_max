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
public class PaymentGatewayiDEAL implements GenericValueObjectInterface {
public static final String module =PaymentGatewayiDEAL.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayiDEAL(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayiDEAL () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"expirationPeriod","Expiration Period"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"privateCert","Private Cert"},
{"acquirerKeyStorePassword","Acquirer Key Store Password"},
{"merchantKeyStoreFilename","Merchant Key Store Filename"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"acquirerTimeout","Acquirer Timeout"},
{"acquirerKeyStoreFilename","Acquirer Key Store Filename"},
{"acquirerURL","Acquirer Url"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"merchantSubId","Merchant Sub Id"},
{"merchantId","Merchant Id"},
{"merchantReturnURL","Merchant Return Url"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"merchantKeyStorePassword","Merchant Key Store Password"},
};
protected void initObject(){
this.expirationPeriod = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.privateCert = "";
this.acquirerKeyStorePassword = "";
this.merchantKeyStoreFilename = "";
this.paymentGatewayConfigId = "";
this.acquirerTimeout = "";
this.acquirerKeyStoreFilename = "";
this.acquirerURL = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.merchantSubId = "";
this.merchantId = "";
this.merchantReturnURL = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.merchantKeyStorePassword = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.expirationPeriod = (java.lang.String) genVal.get("expirationPeriod");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.privateCert = (java.lang.String) genVal.get("privateCert");
this.acquirerKeyStorePassword = (java.lang.String) genVal.get("acquirerKeyStorePassword");
this.merchantKeyStoreFilename = (java.lang.String) genVal.get("merchantKeyStoreFilename");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.acquirerTimeout = (java.lang.String) genVal.get("acquirerTimeout");
this.acquirerKeyStoreFilename = (java.lang.String) genVal.get("acquirerKeyStoreFilename");
this.acquirerURL = (java.lang.String) genVal.get("acquirerURL");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.merchantSubId = (java.lang.String) genVal.get("merchantSubId");
this.merchantId = (java.lang.String) genVal.get("merchantId");
this.merchantReturnURL = (java.lang.String) genVal.get("merchantReturnURL");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.merchantKeyStorePassword = (java.lang.String) genVal.get("merchantKeyStorePassword");
}
protected void getGenericValue(GenericValue val) {
val.set("expirationPeriod",OrderMaxUtility.getValidEntityString(this.expirationPeriod));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("privateCert",OrderMaxUtility.getValidEntityString(this.privateCert));
val.set("acquirerKeyStorePassword",OrderMaxUtility.getValidEntityString(this.acquirerKeyStorePassword));
val.set("merchantKeyStoreFilename",OrderMaxUtility.getValidEntityString(this.merchantKeyStoreFilename));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("acquirerTimeout",OrderMaxUtility.getValidEntityString(this.acquirerTimeout));
val.set("acquirerKeyStoreFilename",OrderMaxUtility.getValidEntityString(this.acquirerKeyStoreFilename));
val.set("acquirerURL",OrderMaxUtility.getValidEntityString(this.acquirerURL));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("merchantSubId",OrderMaxUtility.getValidEntityString(this.merchantSubId));
val.set("merchantId",OrderMaxUtility.getValidEntityString(this.merchantId));
val.set("merchantReturnURL",OrderMaxUtility.getValidEntityString(this.merchantReturnURL));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("merchantKeyStorePassword",OrderMaxUtility.getValidEntityString(this.merchantKeyStorePassword));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("expirationPeriod",this.expirationPeriod);
valueMap.put("privateCert",this.privateCert);
valueMap.put("acquirerKeyStorePassword",this.acquirerKeyStorePassword);
valueMap.put("merchantKeyStoreFilename",this.merchantKeyStoreFilename);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("acquirerTimeout",this.acquirerTimeout);
valueMap.put("acquirerKeyStoreFilename",this.acquirerKeyStoreFilename);
valueMap.put("acquirerURL",this.acquirerURL);
valueMap.put("merchantSubId",this.merchantSubId);
valueMap.put("merchantId",this.merchantId);
valueMap.put("merchantReturnURL",this.merchantReturnURL);
valueMap.put("merchantKeyStorePassword",this.merchantKeyStorePassword);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayiDEAL");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String expirationPeriod;
public java.lang.String getexpirationPeriod() {
return expirationPeriod;
}
public void setexpirationPeriod( java.lang.String expirationPeriod ) {
this.expirationPeriod = expirationPeriod;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String privateCert;
public java.lang.String getprivateCert() {
return privateCert;
}
public void setprivateCert( java.lang.String privateCert ) {
this.privateCert = privateCert;
}
private java.lang.String acquirerKeyStorePassword;
public java.lang.String getacquirerKeyStorePassword() {
return acquirerKeyStorePassword;
}
public void setacquirerKeyStorePassword( java.lang.String acquirerKeyStorePassword ) {
this.acquirerKeyStorePassword = acquirerKeyStorePassword;
}
private java.lang.String merchantKeyStoreFilename;
public java.lang.String getmerchantKeyStoreFilename() {
return merchantKeyStoreFilename;
}
public void setmerchantKeyStoreFilename( java.lang.String merchantKeyStoreFilename ) {
this.merchantKeyStoreFilename = merchantKeyStoreFilename;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String acquirerTimeout;
public java.lang.String getacquirerTimeout() {
return acquirerTimeout;
}
public void setacquirerTimeout( java.lang.String acquirerTimeout ) {
this.acquirerTimeout = acquirerTimeout;
}
private java.lang.String acquirerKeyStoreFilename;
public java.lang.String getacquirerKeyStoreFilename() {
return acquirerKeyStoreFilename;
}
public void setacquirerKeyStoreFilename( java.lang.String acquirerKeyStoreFilename ) {
this.acquirerKeyStoreFilename = acquirerKeyStoreFilename;
}
private java.lang.String acquirerURL;
public java.lang.String getacquirerURL() {
return acquirerURL;
}
public void setacquirerURL( java.lang.String acquirerURL ) {
this.acquirerURL = acquirerURL;
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
private java.lang.String merchantSubId;
public java.lang.String getmerchantSubId() {
return merchantSubId;
}
public void setmerchantSubId( java.lang.String merchantSubId ) {
this.merchantSubId = merchantSubId;
}
private java.lang.String merchantId;
public java.lang.String getmerchantId() {
return merchantId;
}
public void setmerchantId( java.lang.String merchantId ) {
this.merchantId = merchantId;
}
private java.lang.String merchantReturnURL;
public java.lang.String getmerchantReturnURL() {
return merchantReturnURL;
}
public void setmerchantReturnURL( java.lang.String merchantReturnURL ) {
this.merchantReturnURL = merchantReturnURL;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String merchantKeyStorePassword;
public java.lang.String getmerchantKeyStorePassword() {
return merchantKeyStorePassword;
}
public void setmerchantKeyStorePassword( java.lang.String merchantKeyStorePassword ) {
this.merchantKeyStorePassword = merchantKeyStorePassword;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayiDEAL> objectList = new ArrayList<PaymentGatewayiDEAL>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayiDEAL(genVal));
        }
        return objectList;
    }    
}
