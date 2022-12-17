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
public class PaymentGatewayOrbital implements GenericValueObjectInterface {
public static final String module =PaymentGatewayOrbital.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayOrbital(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayOrbital () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"port","Port"},
{"connectionPassword","Connection Password"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"portFailover","Port Failover"},
{"hostNameFailover","Host Name Failover"},
{"sdkVersion","Sdk Version"},
{"engineClass","Engine Class"},
{"username","Username"},
{"sslSocketFactory","Ssl Socket Factory"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"readTimeoutSeconds","Read Timeout Seconds"},
{"responseType","Response Type"},
{"hostName","Host Name"},
{"merchantId","Merchant Id"},
{"connectionTimeoutSeconds","Connection Timeout Seconds"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"authorizationURI","Authorization Uri"},
};
protected void initObject(){
this.port = new Long(0);
this.connectionPassword = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.paymentGatewayConfigId = "";
this.portFailover = new Long(0);
this.hostNameFailover = "";
this.sdkVersion = "";
this.engineClass = "";
this.username = "";
this.sslSocketFactory = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.readTimeoutSeconds = new Long(0);
this.responseType = "";
this.hostName = "";
this.merchantId = "";
this.connectionTimeoutSeconds = new Long(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.authorizationURI = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.port = (java.lang.Long) genVal.get("port");
this.connectionPassword = (java.lang.String) genVal.get("connectionPassword");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.portFailover = (java.lang.Long) genVal.get("portFailover");
this.hostNameFailover = (java.lang.String) genVal.get("hostNameFailover");
this.sdkVersion = (java.lang.String) genVal.get("sdkVersion");
this.engineClass = (java.lang.String) genVal.get("engineClass");
this.username = (java.lang.String) genVal.get("username");
this.sslSocketFactory = (java.lang.String) genVal.get("sslSocketFactory");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.readTimeoutSeconds = (java.lang.Long) genVal.get("readTimeoutSeconds");
this.responseType = (java.lang.String) genVal.get("responseType");
this.hostName = (java.lang.String) genVal.get("hostName");
this.merchantId = (java.lang.String) genVal.get("merchantId");
this.connectionTimeoutSeconds = (java.lang.Long) genVal.get("connectionTimeoutSeconds");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.authorizationURI = (java.lang.String) genVal.get("authorizationURI");
}
protected void getGenericValue(GenericValue val) {
val.set("port",this.port);
val.set("connectionPassword",OrderMaxUtility.getValidEntityString(this.connectionPassword));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("portFailover",this.portFailover);
val.set("hostNameFailover",OrderMaxUtility.getValidEntityString(this.hostNameFailover));
val.set("sdkVersion",OrderMaxUtility.getValidEntityString(this.sdkVersion));
val.set("engineClass",OrderMaxUtility.getValidEntityString(this.engineClass));
val.set("username",OrderMaxUtility.getValidEntityString(this.username));
val.set("sslSocketFactory",OrderMaxUtility.getValidEntityString(this.sslSocketFactory));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("readTimeoutSeconds",this.readTimeoutSeconds);
val.set("responseType",OrderMaxUtility.getValidEntityString(this.responseType));
val.set("hostName",OrderMaxUtility.getValidEntityString(this.hostName));
val.set("merchantId",OrderMaxUtility.getValidEntityString(this.merchantId));
val.set("connectionTimeoutSeconds",this.connectionTimeoutSeconds);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("authorizationURI",OrderMaxUtility.getValidEntityString(this.authorizationURI));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("port",this.port);
valueMap.put("connectionPassword",this.connectionPassword);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("portFailover",this.portFailover);
valueMap.put("hostNameFailover",this.hostNameFailover);
valueMap.put("sdkVersion",this.sdkVersion);
valueMap.put("engineClass",this.engineClass);
valueMap.put("username",this.username);
valueMap.put("sslSocketFactory",this.sslSocketFactory);
valueMap.put("readTimeoutSeconds",this.readTimeoutSeconds);
valueMap.put("responseType",this.responseType);
valueMap.put("hostName",this.hostName);
valueMap.put("merchantId",this.merchantId);
valueMap.put("connectionTimeoutSeconds",this.connectionTimeoutSeconds);
valueMap.put("authorizationURI",this.authorizationURI);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayOrbital");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long port;
public java.lang.Long getport() {
return port;
}
public void setport( java.lang.Long port ) {
this.port = port;
}
private java.lang.String connectionPassword;
public java.lang.String getconnectionPassword() {
return connectionPassword;
}
public void setconnectionPassword( java.lang.String connectionPassword ) {
this.connectionPassword = connectionPassword;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.Long portFailover;
public java.lang.Long getportFailover() {
return portFailover;
}
public void setportFailover( java.lang.Long portFailover ) {
this.portFailover = portFailover;
}
private java.lang.String hostNameFailover;
public java.lang.String gethostNameFailover() {
return hostNameFailover;
}
public void sethostNameFailover( java.lang.String hostNameFailover ) {
this.hostNameFailover = hostNameFailover;
}
private java.lang.String sdkVersion;
public java.lang.String getsdkVersion() {
return sdkVersion;
}
public void setsdkVersion( java.lang.String sdkVersion ) {
this.sdkVersion = sdkVersion;
}
private java.lang.String engineClass;
public java.lang.String getengineClass() {
return engineClass;
}
public void setengineClass( java.lang.String engineClass ) {
this.engineClass = engineClass;
}
private java.lang.String username;
public java.lang.String getusername() {
return username;
}
public void setusername( java.lang.String username ) {
this.username = username;
}
private java.lang.String sslSocketFactory;
public java.lang.String getsslSocketFactory() {
return sslSocketFactory;
}
public void setsslSocketFactory( java.lang.String sslSocketFactory ) {
this.sslSocketFactory = sslSocketFactory;
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
private java.lang.Long readTimeoutSeconds;
public java.lang.Long getreadTimeoutSeconds() {
return readTimeoutSeconds;
}
public void setreadTimeoutSeconds( java.lang.Long readTimeoutSeconds ) {
this.readTimeoutSeconds = readTimeoutSeconds;
}
private java.lang.String responseType;
public java.lang.String getresponseType() {
return responseType;
}
public void setresponseType( java.lang.String responseType ) {
this.responseType = responseType;
}
private java.lang.String hostName;
public java.lang.String gethostName() {
return hostName;
}
public void sethostName( java.lang.String hostName ) {
this.hostName = hostName;
}
private java.lang.String merchantId;
public java.lang.String getmerchantId() {
return merchantId;
}
public void setmerchantId( java.lang.String merchantId ) {
this.merchantId = merchantId;
}
private java.lang.Long connectionTimeoutSeconds;
public java.lang.Long getconnectionTimeoutSeconds() {
return connectionTimeoutSeconds;
}
public void setconnectionTimeoutSeconds( java.lang.Long connectionTimeoutSeconds ) {
this.connectionTimeoutSeconds = connectionTimeoutSeconds;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String authorizationURI;
public java.lang.String getauthorizationURI() {
return authorizationURI;
}
public void setauthorizationURI( java.lang.String authorizationURI ) {
this.authorizationURI = authorizationURI;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayOrbital> objectList = new ArrayList<PaymentGatewayOrbital>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayOrbital(genVal));
        }
        return objectList;
    }    
}
