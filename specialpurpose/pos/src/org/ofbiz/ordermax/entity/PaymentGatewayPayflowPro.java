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
public class PaymentGatewayPayflowPro implements GenericValueObjectInterface {
public static final String module =PaymentGatewayPayflowPro.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayPayflowPro(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayPayflowPro () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"returnUrl","Return Url"},
{"cancelReturnUrl","Cancel Return Url"},
{"loggingLevel","Logging Level"},
{"certsPath","Certs Path"},
{"hostAddress","Host Address"},
{"proxyAddress","Proxy Address"},
{"proxyLogon","Proxy Logon"},
{"stackTraceOn","Stack Trace On"},
{"hostPort","Host Port"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"checkAvs","Check Avs"},
{"userId","User Id"},
{"checkCvv2","Check Cvv 2"},
{"partner","Partner"},
{"redirectUrl","Redirect Url"},
{"proxyPassword","Proxy Password"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"logFileName","Log File Name"},
{"vendor","Vendor"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"proxyPort","Proxy Port"},
{"preAuth","Pre Auth"},
{"pwd","Pwd"},
{"maxLogFileSize","Max Log File Size"},
{"enableTransmit","Enable Transmit"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"timeout","Timeout"},
};
protected void initObject(){
this.returnUrl = "";
this.cancelReturnUrl = "";
this.loggingLevel = new Long(0);
this.certsPath = "";
this.hostAddress = "";
this.proxyAddress = "";
this.proxyLogon = "";
this.stackTraceOn = "";
this.hostPort = new Long(0);
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.checkAvs = "";
this.userId = "";
this.checkCvv2 = "";
this.partner = "";
this.redirectUrl = "";
this.proxyPassword = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.logFileName = "";
this.vendor = "";
this.paymentGatewayConfigId = "";
this.proxyPort = new Long(0);
this.preAuth = "";
this.pwd = "";
this.maxLogFileSize = new Long(0);
this.enableTransmit = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.timeout = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.returnUrl = (java.lang.String) genVal.get("returnUrl");
this.cancelReturnUrl = (java.lang.String) genVal.get("cancelReturnUrl");
this.loggingLevel = (java.lang.Long) genVal.get("loggingLevel");
this.certsPath = (java.lang.String) genVal.get("certsPath");
this.hostAddress = (java.lang.String) genVal.get("hostAddress");
this.proxyAddress = (java.lang.String) genVal.get("proxyAddress");
this.proxyLogon = (java.lang.String) genVal.get("proxyLogon");
this.stackTraceOn = (java.lang.String) genVal.get("stackTraceOn");
this.hostPort = (java.lang.Long) genVal.get("hostPort");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.checkAvs = (java.lang.String) genVal.get("checkAvs");
this.userId = (java.lang.String) genVal.get("userId");
this.checkCvv2 = (java.lang.String) genVal.get("checkCvv2");
this.partner = (java.lang.String) genVal.get("partner");
this.redirectUrl = (java.lang.String) genVal.get("redirectUrl");
this.proxyPassword = (java.lang.String) genVal.get("proxyPassword");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.logFileName = (java.lang.String) genVal.get("logFileName");
this.vendor = (java.lang.String) genVal.get("vendor");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.proxyPort = (java.lang.Long) genVal.get("proxyPort");
this.preAuth = (java.lang.String) genVal.get("preAuth");
this.pwd = (java.lang.String) genVal.get("pwd");
this.maxLogFileSize = (java.lang.Long) genVal.get("maxLogFileSize");
this.enableTransmit = (java.lang.String) genVal.get("enableTransmit");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.timeout = (java.lang.Long) genVal.get("timeout");
}
protected void getGenericValue(GenericValue val) {
val.set("returnUrl",OrderMaxUtility.getValidEntityString(this.returnUrl));
val.set("cancelReturnUrl",OrderMaxUtility.getValidEntityString(this.cancelReturnUrl));
val.set("loggingLevel",this.loggingLevel);
val.set("certsPath",OrderMaxUtility.getValidEntityString(this.certsPath));
val.set("hostAddress",OrderMaxUtility.getValidEntityString(this.hostAddress));
val.set("proxyAddress",OrderMaxUtility.getValidEntityString(this.proxyAddress));
val.set("proxyLogon",OrderMaxUtility.getValidEntityString(this.proxyLogon));
val.set("stackTraceOn",OrderMaxUtility.getValidEntityString(this.stackTraceOn));
val.set("hostPort",this.hostPort);
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("checkAvs",OrderMaxUtility.getValidEntityString(this.checkAvs));
val.set("userId",OrderMaxUtility.getValidEntityString(this.userId));
val.set("checkCvv2",OrderMaxUtility.getValidEntityString(this.checkCvv2));
val.set("partner",OrderMaxUtility.getValidEntityString(this.partner));
val.set("redirectUrl",OrderMaxUtility.getValidEntityString(this.redirectUrl));
val.set("proxyPassword",OrderMaxUtility.getValidEntityString(this.proxyPassword));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("logFileName",OrderMaxUtility.getValidEntityString(this.logFileName));
val.set("vendor",OrderMaxUtility.getValidEntityString(this.vendor));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("proxyPort",this.proxyPort);
val.set("preAuth",OrderMaxUtility.getValidEntityString(this.preAuth));
val.set("pwd",OrderMaxUtility.getValidEntityString(this.pwd));
val.set("maxLogFileSize",this.maxLogFileSize);
val.set("enableTransmit",OrderMaxUtility.getValidEntityString(this.enableTransmit));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("timeout",this.timeout);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("returnUrl",this.returnUrl);
valueMap.put("cancelReturnUrl",this.cancelReturnUrl);
valueMap.put("loggingLevel",this.loggingLevel);
valueMap.put("certsPath",this.certsPath);
valueMap.put("hostAddress",this.hostAddress);
valueMap.put("proxyAddress",this.proxyAddress);
valueMap.put("proxyLogon",this.proxyLogon);
valueMap.put("stackTraceOn",this.stackTraceOn);
valueMap.put("hostPort",this.hostPort);
valueMap.put("checkAvs",this.checkAvs);
valueMap.put("userId",this.userId);
valueMap.put("checkCvv2",this.checkCvv2);
valueMap.put("partner",this.partner);
valueMap.put("redirectUrl",this.redirectUrl);
valueMap.put("proxyPassword",this.proxyPassword);
valueMap.put("logFileName",this.logFileName);
valueMap.put("vendor",this.vendor);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("proxyPort",this.proxyPort);
valueMap.put("preAuth",this.preAuth);
valueMap.put("pwd",this.pwd);
valueMap.put("maxLogFileSize",this.maxLogFileSize);
valueMap.put("enableTransmit",this.enableTransmit);
valueMap.put("timeout",this.timeout);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayPayflowPro");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String returnUrl;
public java.lang.String getreturnUrl() {
return returnUrl;
}
public void setreturnUrl( java.lang.String returnUrl ) {
this.returnUrl = returnUrl;
}
private java.lang.String cancelReturnUrl;
public java.lang.String getcancelReturnUrl() {
return cancelReturnUrl;
}
public void setcancelReturnUrl( java.lang.String cancelReturnUrl ) {
this.cancelReturnUrl = cancelReturnUrl;
}
private java.lang.Long loggingLevel;
public java.lang.Long getloggingLevel() {
return loggingLevel;
}
public void setloggingLevel( java.lang.Long loggingLevel ) {
this.loggingLevel = loggingLevel;
}
private java.lang.String certsPath;
public java.lang.String getcertsPath() {
return certsPath;
}
public void setcertsPath( java.lang.String certsPath ) {
this.certsPath = certsPath;
}
private java.lang.String hostAddress;
public java.lang.String gethostAddress() {
return hostAddress;
}
public void sethostAddress( java.lang.String hostAddress ) {
this.hostAddress = hostAddress;
}
private java.lang.String proxyAddress;
public java.lang.String getproxyAddress() {
return proxyAddress;
}
public void setproxyAddress( java.lang.String proxyAddress ) {
this.proxyAddress = proxyAddress;
}
private java.lang.String proxyLogon;
public java.lang.String getproxyLogon() {
return proxyLogon;
}
public void setproxyLogon( java.lang.String proxyLogon ) {
this.proxyLogon = proxyLogon;
}
private java.lang.String stackTraceOn;
public java.lang.String getstackTraceOn() {
return stackTraceOn;
}
public void setstackTraceOn( java.lang.String stackTraceOn ) {
this.stackTraceOn = stackTraceOn;
}
private java.lang.Long hostPort;
public java.lang.Long gethostPort() {
return hostPort;
}
public void sethostPort( java.lang.Long hostPort ) {
this.hostPort = hostPort;
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
private java.lang.String checkAvs;
public java.lang.String getcheckAvs() {
return checkAvs;
}
public void setcheckAvs( java.lang.String checkAvs ) {
this.checkAvs = checkAvs;
}
private java.lang.String userId;
public java.lang.String getuserId() {
return userId;
}
public void setuserId( java.lang.String userId ) {
this.userId = userId;
}
private java.lang.String checkCvv2;
public java.lang.String getcheckCvv2() {
return checkCvv2;
}
public void setcheckCvv2( java.lang.String checkCvv2 ) {
this.checkCvv2 = checkCvv2;
}
private java.lang.String partner;
public java.lang.String getpartner() {
return partner;
}
public void setpartner( java.lang.String partner ) {
this.partner = partner;
}
private java.lang.String redirectUrl;
public java.lang.String getredirectUrl() {
return redirectUrl;
}
public void setredirectUrl( java.lang.String redirectUrl ) {
this.redirectUrl = redirectUrl;
}
private java.lang.String proxyPassword;
public java.lang.String getproxyPassword() {
return proxyPassword;
}
public void setproxyPassword( java.lang.String proxyPassword ) {
this.proxyPassword = proxyPassword;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String logFileName;
public java.lang.String getlogFileName() {
return logFileName;
}
public void setlogFileName( java.lang.String logFileName ) {
this.logFileName = logFileName;
}
private java.lang.String vendor;
public java.lang.String getvendor() {
return vendor;
}
public void setvendor( java.lang.String vendor ) {
this.vendor = vendor;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.Long proxyPort;
public java.lang.Long getproxyPort() {
return proxyPort;
}
public void setproxyPort( java.lang.Long proxyPort ) {
this.proxyPort = proxyPort;
}
private java.lang.String preAuth;
public java.lang.String getpreAuth() {
return preAuth;
}
public void setpreAuth( java.lang.String preAuth ) {
this.preAuth = preAuth;
}
private java.lang.String pwd;
public java.lang.String getpwd() {
return pwd;
}
public void setpwd( java.lang.String pwd ) {
this.pwd = pwd;
}
private java.lang.Long maxLogFileSize;
public java.lang.Long getmaxLogFileSize() {
return maxLogFileSize;
}
public void setmaxLogFileSize( java.lang.Long maxLogFileSize ) {
this.maxLogFileSize = maxLogFileSize;
}
private java.lang.String enableTransmit;
public java.lang.String getenableTransmit() {
return enableTransmit;
}
public void setenableTransmit( java.lang.String enableTransmit ) {
this.enableTransmit = enableTransmit;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.Long timeout;
public java.lang.Long gettimeout() {
return timeout;
}
public void settimeout( java.lang.Long timeout ) {
this.timeout = timeout;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayPayflowPro> objectList = new ArrayList<PaymentGatewayPayflowPro>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayPayflowPro(genVal));
        }
        return objectList;
    }    
}
