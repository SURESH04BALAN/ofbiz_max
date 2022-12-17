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
public class PaymentGatewayWorldPay implements GenericValueObjectInterface {
public static final String module =PaymentGatewayWorldPay.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayWorldPay(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayWorldPay () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"instId","Inst Id"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"noLanguageMenu","No Language Menu"},
{"withDelivery","With Delivery"},
{"hideContact","Hide Contact"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"hideCurrency","Hide Currency"},
{"testMode","Test Mode"},
{"langId","Lang Id"},
{"fixContact","Fix Contact"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"authMode","Auth Mode"},
{"redirectUrl","Redirect Url"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.instId = "";
this.paymentGatewayConfigId = "";
this.noLanguageMenu = "";
this.withDelivery = "";
this.hideContact = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.hideCurrency = "";
this.testMode = new Long(0);
this.langId = "";
this.fixContact = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.authMode = "";
this.redirectUrl = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.instId = (java.lang.String) genVal.get("instId");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.noLanguageMenu = (java.lang.String) genVal.get("noLanguageMenu");
this.withDelivery = (java.lang.String) genVal.get("withDelivery");
this.hideContact = (java.lang.String) genVal.get("hideContact");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.hideCurrency = (java.lang.String) genVal.get("hideCurrency");
this.testMode = (java.lang.Long) genVal.get("testMode");
this.langId = (java.lang.String) genVal.get("langId");
this.fixContact = (java.lang.String) genVal.get("fixContact");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.authMode = (java.lang.String) genVal.get("authMode");
this.redirectUrl = (java.lang.String) genVal.get("redirectUrl");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("instId",OrderMaxUtility.getValidEntityString(this.instId));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("noLanguageMenu",OrderMaxUtility.getValidEntityString(this.noLanguageMenu));
val.set("withDelivery",OrderMaxUtility.getValidEntityString(this.withDelivery));
val.set("hideContact",OrderMaxUtility.getValidEntityString(this.hideContact));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("hideCurrency",OrderMaxUtility.getValidEntityString(this.hideCurrency));
val.set("testMode",this.testMode);
val.set("langId",OrderMaxUtility.getValidEntityString(this.langId));
val.set("fixContact",OrderMaxUtility.getValidEntityString(this.fixContact));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("authMode",OrderMaxUtility.getValidEntityString(this.authMode));
val.set("redirectUrl",OrderMaxUtility.getValidEntityString(this.redirectUrl));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("instId",this.instId);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("noLanguageMenu",this.noLanguageMenu);
valueMap.put("withDelivery",this.withDelivery);
valueMap.put("hideContact",this.hideContact);
valueMap.put("hideCurrency",this.hideCurrency);
valueMap.put("testMode",this.testMode);
valueMap.put("langId",this.langId);
valueMap.put("fixContact",this.fixContact);
valueMap.put("authMode",this.authMode);
valueMap.put("redirectUrl",this.redirectUrl);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayWorldPay");
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
private java.lang.String instId;
public java.lang.String getinstId() {
return instId;
}
public void setinstId( java.lang.String instId ) {
this.instId = instId;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String noLanguageMenu;
public java.lang.String getnoLanguageMenu() {
return noLanguageMenu;
}
public void setnoLanguageMenu( java.lang.String noLanguageMenu ) {
this.noLanguageMenu = noLanguageMenu;
}
private java.lang.String withDelivery;
public java.lang.String getwithDelivery() {
return withDelivery;
}
public void setwithDelivery( java.lang.String withDelivery ) {
this.withDelivery = withDelivery;
}
private java.lang.String hideContact;
public java.lang.String gethideContact() {
return hideContact;
}
public void sethideContact( java.lang.String hideContact ) {
this.hideContact = hideContact;
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
private java.lang.String hideCurrency;
public java.lang.String gethideCurrency() {
return hideCurrency;
}
public void sethideCurrency( java.lang.String hideCurrency ) {
this.hideCurrency = hideCurrency;
}
private java.lang.Long testMode;
public java.lang.Long gettestMode() {
return testMode;
}
public void settestMode( java.lang.Long testMode ) {
this.testMode = testMode;
}
private java.lang.String langId;
public java.lang.String getlangId() {
return langId;
}
public void setlangId( java.lang.String langId ) {
this.langId = langId;
}
private java.lang.String fixContact;
public java.lang.String getfixContact() {
return fixContact;
}
public void setfixContact( java.lang.String fixContact ) {
this.fixContact = fixContact;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String authMode;
public java.lang.String getauthMode() {
return authMode;
}
public void setauthMode( java.lang.String authMode ) {
this.authMode = authMode;
}
private java.lang.String redirectUrl;
public java.lang.String getredirectUrl() {
return redirectUrl;
}
public void setredirectUrl( java.lang.String redirectUrl ) {
this.redirectUrl = redirectUrl;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGatewayWorldPay> objectList = new ArrayList<PaymentGatewayWorldPay>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayWorldPay(genVal));
        }
        return objectList;
    }    
}
