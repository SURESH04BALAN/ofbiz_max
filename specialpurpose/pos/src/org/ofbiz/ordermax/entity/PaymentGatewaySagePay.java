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
public class PaymentGatewaySagePay implements GenericValueObjectInterface {
public static final String module =PaymentGatewaySagePay.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewaySagePay(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewaySagePay () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"productionHost","Production Host"},
{"authenticationUrl","Authentication Url"},
{"refundUrl","Refund Url"},
{"vendor","Vendor"},
{"testingHost","Testing Host"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"authoriseUrl","Authorise Url"},
{"sagePayMode","Sage Pay Mode"},
{"voidUrl","Void Url"},
{"protocolVersion","Protocol Version"},
{"authoriseTransType","Authorise Trans Type"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"authenticationTransType","Authentication Trans Type"},
{"releaseTransType","Release Trans Type"},
{"releaseUrl","Release Url"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productionHost = "";
this.authenticationUrl = "";
this.refundUrl = "";
this.vendor = "";
this.testingHost = "";
this.paymentGatewayConfigId = "";
this.authoriseUrl = "";
this.sagePayMode = "";
this.voidUrl = "";
this.protocolVersion = "";
this.authoriseTransType = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.authenticationTransType = "";
this.releaseTransType = "";
this.releaseUrl = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productionHost = (java.lang.String) genVal.get("productionHost");
this.authenticationUrl = (java.lang.String) genVal.get("authenticationUrl");
this.refundUrl = (java.lang.String) genVal.get("refundUrl");
this.vendor = (java.lang.String) genVal.get("vendor");
this.testingHost = (java.lang.String) genVal.get("testingHost");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.authoriseUrl = (java.lang.String) genVal.get("authoriseUrl");
this.sagePayMode = (java.lang.String) genVal.get("sagePayMode");
this.voidUrl = (java.lang.String) genVal.get("voidUrl");
this.protocolVersion = (java.lang.String) genVal.get("protocolVersion");
this.authoriseTransType = (java.lang.String) genVal.get("authoriseTransType");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.authenticationTransType = (java.lang.String) genVal.get("authenticationTransType");
this.releaseTransType = (java.lang.String) genVal.get("releaseTransType");
this.releaseUrl = (java.lang.String) genVal.get("releaseUrl");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productionHost",OrderMaxUtility.getValidEntityString(this.productionHost));
val.set("authenticationUrl",OrderMaxUtility.getValidEntityString(this.authenticationUrl));
val.set("refundUrl",OrderMaxUtility.getValidEntityString(this.refundUrl));
val.set("vendor",OrderMaxUtility.getValidEntityString(this.vendor));
val.set("testingHost",OrderMaxUtility.getValidEntityString(this.testingHost));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("authoriseUrl",OrderMaxUtility.getValidEntityString(this.authoriseUrl));
val.set("sagePayMode",OrderMaxUtility.getValidEntityString(this.sagePayMode));
val.set("voidUrl",OrderMaxUtility.getValidEntityString(this.voidUrl));
val.set("protocolVersion",OrderMaxUtility.getValidEntityString(this.protocolVersion));
val.set("authoriseTransType",OrderMaxUtility.getValidEntityString(this.authoriseTransType));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("authenticationTransType",OrderMaxUtility.getValidEntityString(this.authenticationTransType));
val.set("releaseTransType",OrderMaxUtility.getValidEntityString(this.releaseTransType));
val.set("releaseUrl",OrderMaxUtility.getValidEntityString(this.releaseUrl));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productionHost",this.productionHost);
valueMap.put("authenticationUrl",this.authenticationUrl);
valueMap.put("refundUrl",this.refundUrl);
valueMap.put("vendor",this.vendor);
valueMap.put("testingHost",this.testingHost);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("authoriseUrl",this.authoriseUrl);
valueMap.put("sagePayMode",this.sagePayMode);
valueMap.put("voidUrl",this.voidUrl);
valueMap.put("protocolVersion",this.protocolVersion);
valueMap.put("authoriseTransType",this.authoriseTransType);
valueMap.put("authenticationTransType",this.authenticationTransType);
valueMap.put("releaseTransType",this.releaseTransType);
valueMap.put("releaseUrl",this.releaseUrl);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewaySagePay");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String productionHost;
public java.lang.String getproductionHost() {
return productionHost;
}
public void setproductionHost( java.lang.String productionHost ) {
this.productionHost = productionHost;
}
private java.lang.String authenticationUrl;
public java.lang.String getauthenticationUrl() {
return authenticationUrl;
}
public void setauthenticationUrl( java.lang.String authenticationUrl ) {
this.authenticationUrl = authenticationUrl;
}
private java.lang.String refundUrl;
public java.lang.String getrefundUrl() {
return refundUrl;
}
public void setrefundUrl( java.lang.String refundUrl ) {
this.refundUrl = refundUrl;
}
private java.lang.String vendor;
public java.lang.String getvendor() {
return vendor;
}
public void setvendor( java.lang.String vendor ) {
this.vendor = vendor;
}
private java.lang.String testingHost;
public java.lang.String gettestingHost() {
return testingHost;
}
public void settestingHost( java.lang.String testingHost ) {
this.testingHost = testingHost;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String authoriseUrl;
public java.lang.String getauthoriseUrl() {
return authoriseUrl;
}
public void setauthoriseUrl( java.lang.String authoriseUrl ) {
this.authoriseUrl = authoriseUrl;
}
private java.lang.String sagePayMode;
public java.lang.String getsagePayMode() {
return sagePayMode;
}
public void setsagePayMode( java.lang.String sagePayMode ) {
this.sagePayMode = sagePayMode;
}
private java.lang.String voidUrl;
public java.lang.String getvoidUrl() {
return voidUrl;
}
public void setvoidUrl( java.lang.String voidUrl ) {
this.voidUrl = voidUrl;
}
private java.lang.String protocolVersion;
public java.lang.String getprotocolVersion() {
return protocolVersion;
}
public void setprotocolVersion( java.lang.String protocolVersion ) {
this.protocolVersion = protocolVersion;
}
private java.lang.String authoriseTransType;
public java.lang.String getauthoriseTransType() {
return authoriseTransType;
}
public void setauthoriseTransType( java.lang.String authoriseTransType ) {
this.authoriseTransType = authoriseTransType;
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
private java.lang.String authenticationTransType;
public java.lang.String getauthenticationTransType() {
return authenticationTransType;
}
public void setauthenticationTransType( java.lang.String authenticationTransType ) {
this.authenticationTransType = authenticationTransType;
}
private java.lang.String releaseTransType;
public java.lang.String getreleaseTransType() {
return releaseTransType;
}
public void setreleaseTransType( java.lang.String releaseTransType ) {
this.releaseTransType = releaseTransType;
}
private java.lang.String releaseUrl;
public java.lang.String getreleaseUrl() {
return releaseUrl;
}
public void setreleaseUrl( java.lang.String releaseUrl ) {
this.releaseUrl = releaseUrl;
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
        Collection<PaymentGatewaySagePay> objectList = new ArrayList<PaymentGatewaySagePay>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewaySagePay(genVal));
        }
        return objectList;
    }    
}
