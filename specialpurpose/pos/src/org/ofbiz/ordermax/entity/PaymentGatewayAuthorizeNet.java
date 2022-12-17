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
public class PaymentGatewayAuthorizeNet implements GenericValueObjectInterface {
public static final String module =PaymentGatewayAuthorizeNet.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayAuthorizeNet(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayAuthorizeNet () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"apiVersion","Api Version"},
{"transactionUrl","Transaction Url"},
{"duplicateWindow","Duplicate Window"},
{"certificateAlias","Certificate Alias"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"cpVersion","Cp Version"},
{"delimiterChar","Delimiter Char"},
{"cpMarketType","Cp Market Type"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"relayResponse","Relay Response"},
{"emailMerchant","Email Merchant"},
{"delimitedData","Delimited Data"},
{"pwd","Pwd"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"testMode","Test Mode"},
{"userId","User Id"},
{"emailCustomer","Email Customer"},
{"cpDeviceType","Cp Device Type"},
{"method","Method"},
{"tranKey","Tran Key"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"transDescription","Trans Description"},
};
protected void initObject(){
this.apiVersion = "";
this.transactionUrl = "";
this.duplicateWindow = "";
this.certificateAlias = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.cpVersion = "";
this.delimiterChar = "";
this.cpMarketType = "";
this.paymentGatewayConfigId = "";
this.relayResponse = "";
this.emailMerchant = "";
this.delimitedData = "";
this.pwd = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.testMode = "";
this.userId = "";
this.emailCustomer = "";
this.cpDeviceType = "";
this.method = "";
this.tranKey = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.transDescription = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.apiVersion = (java.lang.String) genVal.get("apiVersion");
this.transactionUrl = (java.lang.String) genVal.get("transactionUrl");
this.duplicateWindow = (java.lang.String) genVal.get("duplicateWindow");
this.certificateAlias = (java.lang.String) genVal.get("certificateAlias");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.cpVersion = (java.lang.String) genVal.get("cpVersion");
this.delimiterChar = (java.lang.String) genVal.get("delimiterChar");
this.cpMarketType = (java.lang.String) genVal.get("cpMarketType");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.relayResponse = (java.lang.String) genVal.get("relayResponse");
this.emailMerchant = (java.lang.String) genVal.get("emailMerchant");
this.delimitedData = (java.lang.String) genVal.get("delimitedData");
this.pwd = (java.lang.String) genVal.get("pwd");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.testMode = (java.lang.String) genVal.get("testMode");
this.userId = (java.lang.String) genVal.get("userId");
this.emailCustomer = (java.lang.String) genVal.get("emailCustomer");
this.cpDeviceType = (java.lang.String) genVal.get("cpDeviceType");
this.method = (java.lang.String) genVal.get("method");
this.tranKey = (java.lang.String) genVal.get("tranKey");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.transDescription = (java.lang.String) genVal.get("transDescription");
}
protected void getGenericValue(GenericValue val) {
val.set("apiVersion",OrderMaxUtility.getValidEntityString(this.apiVersion));
val.set("transactionUrl",OrderMaxUtility.getValidEntityString(this.transactionUrl));
val.set("duplicateWindow",OrderMaxUtility.getValidEntityString(this.duplicateWindow));
val.set("certificateAlias",OrderMaxUtility.getValidEntityString(this.certificateAlias));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("cpVersion",OrderMaxUtility.getValidEntityString(this.cpVersion));
val.set("delimiterChar",OrderMaxUtility.getValidEntityString(this.delimiterChar));
val.set("cpMarketType",OrderMaxUtility.getValidEntityString(this.cpMarketType));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("relayResponse",OrderMaxUtility.getValidEntityString(this.relayResponse));
val.set("emailMerchant",OrderMaxUtility.getValidEntityString(this.emailMerchant));
val.set("delimitedData",OrderMaxUtility.getValidEntityString(this.delimitedData));
val.set("pwd",OrderMaxUtility.getValidEntityString(this.pwd));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("testMode",OrderMaxUtility.getValidEntityString(this.testMode));
val.set("userId",OrderMaxUtility.getValidEntityString(this.userId));
val.set("emailCustomer",OrderMaxUtility.getValidEntityString(this.emailCustomer));
val.set("cpDeviceType",OrderMaxUtility.getValidEntityString(this.cpDeviceType));
val.set("method",OrderMaxUtility.getValidEntityString(this.method));
val.set("tranKey",OrderMaxUtility.getValidEntityString(this.tranKey));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("transDescription",OrderMaxUtility.getValidEntityString(this.transDescription));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("apiVersion",this.apiVersion);
valueMap.put("transactionUrl",this.transactionUrl);
valueMap.put("duplicateWindow",this.duplicateWindow);
valueMap.put("certificateAlias",this.certificateAlias);
valueMap.put("cpVersion",this.cpVersion);
valueMap.put("delimiterChar",this.delimiterChar);
valueMap.put("cpMarketType",this.cpMarketType);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("relayResponse",this.relayResponse);
valueMap.put("emailMerchant",this.emailMerchant);
valueMap.put("delimitedData",this.delimitedData);
valueMap.put("pwd",this.pwd);
valueMap.put("testMode",this.testMode);
valueMap.put("userId",this.userId);
valueMap.put("emailCustomer",this.emailCustomer);
valueMap.put("cpDeviceType",this.cpDeviceType);
valueMap.put("method",this.method);
valueMap.put("tranKey",this.tranKey);
valueMap.put("transDescription",this.transDescription);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayAuthorizeNet");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String apiVersion;
public java.lang.String getapiVersion() {
return apiVersion;
}
public void setapiVersion( java.lang.String apiVersion ) {
this.apiVersion = apiVersion;
}
private java.lang.String transactionUrl;
public java.lang.String gettransactionUrl() {
return transactionUrl;
}
public void settransactionUrl( java.lang.String transactionUrl ) {
this.transactionUrl = transactionUrl;
}
private java.lang.String duplicateWindow;
public java.lang.String getduplicateWindow() {
return duplicateWindow;
}
public void setduplicateWindow( java.lang.String duplicateWindow ) {
this.duplicateWindow = duplicateWindow;
}
private java.lang.String certificateAlias;
public java.lang.String getcertificateAlias() {
return certificateAlias;
}
public void setcertificateAlias( java.lang.String certificateAlias ) {
this.certificateAlias = certificateAlias;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String cpVersion;
public java.lang.String getcpVersion() {
return cpVersion;
}
public void setcpVersion( java.lang.String cpVersion ) {
this.cpVersion = cpVersion;
}
private java.lang.String delimiterChar;
public java.lang.String getdelimiterChar() {
return delimiterChar;
}
public void setdelimiterChar( java.lang.String delimiterChar ) {
this.delimiterChar = delimiterChar;
}
private java.lang.String cpMarketType;
public java.lang.String getcpMarketType() {
return cpMarketType;
}
public void setcpMarketType( java.lang.String cpMarketType ) {
this.cpMarketType = cpMarketType;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String relayResponse;
public java.lang.String getrelayResponse() {
return relayResponse;
}
public void setrelayResponse( java.lang.String relayResponse ) {
this.relayResponse = relayResponse;
}
private java.lang.String emailMerchant;
public java.lang.String getemailMerchant() {
return emailMerchant;
}
public void setemailMerchant( java.lang.String emailMerchant ) {
this.emailMerchant = emailMerchant;
}
private java.lang.String delimitedData;
public java.lang.String getdelimitedData() {
return delimitedData;
}
public void setdelimitedData( java.lang.String delimitedData ) {
this.delimitedData = delimitedData;
}
private java.lang.String pwd;
public java.lang.String getpwd() {
return pwd;
}
public void setpwd( java.lang.String pwd ) {
this.pwd = pwd;
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
private java.lang.String testMode;
public java.lang.String gettestMode() {
return testMode;
}
public void settestMode( java.lang.String testMode ) {
this.testMode = testMode;
}
private java.lang.String userId;
public java.lang.String getuserId() {
return userId;
}
public void setuserId( java.lang.String userId ) {
this.userId = userId;
}
private java.lang.String emailCustomer;
public java.lang.String getemailCustomer() {
return emailCustomer;
}
public void setemailCustomer( java.lang.String emailCustomer ) {
this.emailCustomer = emailCustomer;
}
private java.lang.String cpDeviceType;
public java.lang.String getcpDeviceType() {
return cpDeviceType;
}
public void setcpDeviceType( java.lang.String cpDeviceType ) {
this.cpDeviceType = cpDeviceType;
}
private java.lang.String method;
public java.lang.String getmethod() {
return method;
}
public void setmethod( java.lang.String method ) {
this.method = method;
}
private java.lang.String tranKey;
public java.lang.String gettranKey() {
return tranKey;
}
public void settranKey( java.lang.String tranKey ) {
this.tranKey = tranKey;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String transDescription;
public java.lang.String gettransDescription() {
return transDescription;
}
public void settransDescription( java.lang.String transDescription ) {
this.transDescription = transDescription;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayAuthorizeNet> objectList = new ArrayList<PaymentGatewayAuthorizeNet>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayAuthorizeNet(genVal));
        }
        return objectList;
    }    
}
