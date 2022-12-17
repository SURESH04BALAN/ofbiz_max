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
public class PaymentGatewayCyberSource implements GenericValueObjectInterface {
public static final String module =PaymentGatewayCyberSource.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayCyberSource(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayCyberSource () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"merchantContact","Merchant Contact"},
{"apiVersion","Api Version"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"keysDir","Keys Dir"},
{"enableDav","Enable Dav"},
{"autoBill","Auto Bill"},
{"avsDeclineCodes","Avs Decline Codes"},
{"logSize","Log Size"},
{"logFile","Log File"},
{"keysFile","Keys File"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"logEnabled","Log Enabled"},
{"production","Production"},
{"disableBillAvs","Disable Bill Avs"},
{"ignoreAvs","Ignore Avs"},
{"logDir","Log Dir"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"merchantDescr","Merchant Descr"},
{"merchantId","Merchant Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"fraudScore","Fraud Score"},
};
protected void initObject(){
this.merchantContact = "";
this.apiVersion = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.keysDir = "";
this.enableDav = "";
this.autoBill = "";
this.avsDeclineCodes = "";
this.logSize = new Long(0);
this.logFile = "";
this.keysFile = "";
this.paymentGatewayConfigId = "";
this.logEnabled = "";
this.production = "";
this.disableBillAvs = "";
this.ignoreAvs = "";
this.logDir = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.merchantDescr = "";
this.merchantId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.fraudScore = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.merchantContact = (java.lang.String) genVal.get("merchantContact");
this.apiVersion = (java.lang.String) genVal.get("apiVersion");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.keysDir = (java.lang.String) genVal.get("keysDir");
this.enableDav = (java.lang.String) genVal.get("enableDav");
this.autoBill = (java.lang.String) genVal.get("autoBill");
this.avsDeclineCodes = (java.lang.String) genVal.get("avsDeclineCodes");
this.logSize = (java.lang.Long) genVal.get("logSize");
this.logFile = (java.lang.String) genVal.get("logFile");
this.keysFile = (java.lang.String) genVal.get("keysFile");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.logEnabled = (java.lang.String) genVal.get("logEnabled");
this.production = (java.lang.String) genVal.get("production");
this.disableBillAvs = (java.lang.String) genVal.get("disableBillAvs");
this.ignoreAvs = (java.lang.String) genVal.get("ignoreAvs");
this.logDir = (java.lang.String) genVal.get("logDir");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.merchantDescr = (java.lang.String) genVal.get("merchantDescr");
this.merchantId = (java.lang.String) genVal.get("merchantId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.fraudScore = (java.lang.String) genVal.get("fraudScore");
}
protected void getGenericValue(GenericValue val) {
val.set("merchantContact",OrderMaxUtility.getValidEntityString(this.merchantContact));
val.set("apiVersion",OrderMaxUtility.getValidEntityString(this.apiVersion));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("keysDir",OrderMaxUtility.getValidEntityString(this.keysDir));
val.set("enableDav",OrderMaxUtility.getValidEntityString(this.enableDav));
val.set("autoBill",OrderMaxUtility.getValidEntityString(this.autoBill));
val.set("avsDeclineCodes",OrderMaxUtility.getValidEntityString(this.avsDeclineCodes));
val.set("logSize",this.logSize);
val.set("logFile",OrderMaxUtility.getValidEntityString(this.logFile));
val.set("keysFile",OrderMaxUtility.getValidEntityString(this.keysFile));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("logEnabled",OrderMaxUtility.getValidEntityString(this.logEnabled));
val.set("production",OrderMaxUtility.getValidEntityString(this.production));
val.set("disableBillAvs",OrderMaxUtility.getValidEntityString(this.disableBillAvs));
val.set("ignoreAvs",OrderMaxUtility.getValidEntityString(this.ignoreAvs));
val.set("logDir",OrderMaxUtility.getValidEntityString(this.logDir));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("merchantDescr",OrderMaxUtility.getValidEntityString(this.merchantDescr));
val.set("merchantId",OrderMaxUtility.getValidEntityString(this.merchantId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("fraudScore",OrderMaxUtility.getValidEntityString(this.fraudScore));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("merchantContact",this.merchantContact);
valueMap.put("apiVersion",this.apiVersion);
valueMap.put("keysDir",this.keysDir);
valueMap.put("enableDav",this.enableDav);
valueMap.put("autoBill",this.autoBill);
valueMap.put("avsDeclineCodes",this.avsDeclineCodes);
valueMap.put("logSize",this.logSize);
valueMap.put("logFile",this.logFile);
valueMap.put("keysFile",this.keysFile);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("logEnabled",this.logEnabled);
valueMap.put("production",this.production);
valueMap.put("disableBillAvs",this.disableBillAvs);
valueMap.put("ignoreAvs",this.ignoreAvs);
valueMap.put("logDir",this.logDir);
valueMap.put("merchantDescr",this.merchantDescr);
valueMap.put("merchantId",this.merchantId);
valueMap.put("fraudScore",this.fraudScore);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayCyberSource");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String merchantContact;
public java.lang.String getmerchantContact() {
return merchantContact;
}
public void setmerchantContact( java.lang.String merchantContact ) {
this.merchantContact = merchantContact;
}
private java.lang.String apiVersion;
public java.lang.String getapiVersion() {
return apiVersion;
}
public void setapiVersion( java.lang.String apiVersion ) {
this.apiVersion = apiVersion;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String keysDir;
public java.lang.String getkeysDir() {
return keysDir;
}
public void setkeysDir( java.lang.String keysDir ) {
this.keysDir = keysDir;
}
private java.lang.String enableDav;
public java.lang.String getenableDav() {
return enableDav;
}
public void setenableDav( java.lang.String enableDav ) {
this.enableDav = enableDav;
}
private java.lang.String autoBill;
public java.lang.String getautoBill() {
return autoBill;
}
public void setautoBill( java.lang.String autoBill ) {
this.autoBill = autoBill;
}
private java.lang.String avsDeclineCodes;
public java.lang.String getavsDeclineCodes() {
return avsDeclineCodes;
}
public void setavsDeclineCodes( java.lang.String avsDeclineCodes ) {
this.avsDeclineCodes = avsDeclineCodes;
}
private java.lang.Long logSize;
public java.lang.Long getlogSize() {
return logSize;
}
public void setlogSize( java.lang.Long logSize ) {
this.logSize = logSize;
}
private java.lang.String logFile;
public java.lang.String getlogFile() {
return logFile;
}
public void setlogFile( java.lang.String logFile ) {
this.logFile = logFile;
}
private java.lang.String keysFile;
public java.lang.String getkeysFile() {
return keysFile;
}
public void setkeysFile( java.lang.String keysFile ) {
this.keysFile = keysFile;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String logEnabled;
public java.lang.String getlogEnabled() {
return logEnabled;
}
public void setlogEnabled( java.lang.String logEnabled ) {
this.logEnabled = logEnabled;
}
private java.lang.String production;
public java.lang.String getproduction() {
return production;
}
public void setproduction( java.lang.String production ) {
this.production = production;
}
private java.lang.String disableBillAvs;
public java.lang.String getdisableBillAvs() {
return disableBillAvs;
}
public void setdisableBillAvs( java.lang.String disableBillAvs ) {
this.disableBillAvs = disableBillAvs;
}
private java.lang.String ignoreAvs;
public java.lang.String getignoreAvs() {
return ignoreAvs;
}
public void setignoreAvs( java.lang.String ignoreAvs ) {
this.ignoreAvs = ignoreAvs;
}
private java.lang.String logDir;
public java.lang.String getlogDir() {
return logDir;
}
public void setlogDir( java.lang.String logDir ) {
this.logDir = logDir;
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
private java.lang.String merchantDescr;
public java.lang.String getmerchantDescr() {
return merchantDescr;
}
public void setmerchantDescr( java.lang.String merchantDescr ) {
this.merchantDescr = merchantDescr;
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
private java.lang.String fraudScore;
public java.lang.String getfraudScore() {
return fraudScore;
}
public void setfraudScore( java.lang.String fraudScore ) {
this.fraudScore = fraudScore;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayCyberSource> objectList = new ArrayList<PaymentGatewayCyberSource>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayCyberSource(genVal));
        }
        return objectList;
    }    
}
