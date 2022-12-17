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
public class PaymentGatewayPayPal implements GenericValueObjectInterface {
public static final String module =PaymentGatewayPayPal.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayPayPal(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayPayPal () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"returnUrl","Return Url"},
{"confirmUrl","Confirm Url"},
{"confirmTemplate","Confirm Template"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"cancelReturnUrl","Cancel Return Url"},
{"imageUrl","Image Url"},
{"apiEnvironment","Api Environment"},
{"apiPassword","Api Password"},
{"requireConfirmedShipping","Require Confirmed Shipping"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"apiUserName","Api User Name"},
{"shippingCallbackUrl","Shipping Callback Url"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"businessEmail","Business Email"},
{"apiSignature","Api Signature"},
{"notifyUrl","Notify Url"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"redirectUrl","Redirect Url"},
};
protected void initObject(){
this.returnUrl = "";
this.confirmUrl = "";
this.confirmTemplate = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.cancelReturnUrl = "";
this.imageUrl = "";
this.apiEnvironment = "";
this.apiPassword = "";
this.requireConfirmedShipping = "";
this.paymentGatewayConfigId = "";
this.apiUserName = "";
this.shippingCallbackUrl = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.businessEmail = "";
this.apiSignature = "";
this.notifyUrl = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.redirectUrl = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.returnUrl = (java.lang.String) genVal.get("returnUrl");
this.confirmUrl = (java.lang.String) genVal.get("confirmUrl");
this.confirmTemplate = (java.lang.String) genVal.get("confirmTemplate");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.cancelReturnUrl = (java.lang.String) genVal.get("cancelReturnUrl");
this.imageUrl = (java.lang.String) genVal.get("imageUrl");
this.apiEnvironment = (java.lang.String) genVal.get("apiEnvironment");
this.apiPassword = (java.lang.String) genVal.get("apiPassword");
this.requireConfirmedShipping = (java.lang.String) genVal.get("requireConfirmedShipping");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.apiUserName = (java.lang.String) genVal.get("apiUserName");
this.shippingCallbackUrl = (java.lang.String) genVal.get("shippingCallbackUrl");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.businessEmail = (java.lang.String) genVal.get("businessEmail");
this.apiSignature = (java.lang.String) genVal.get("apiSignature");
this.notifyUrl = (java.lang.String) genVal.get("notifyUrl");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.redirectUrl = (java.lang.String) genVal.get("redirectUrl");
}
protected void getGenericValue(GenericValue val) {
val.set("returnUrl",OrderMaxUtility.getValidEntityString(this.returnUrl));
val.set("confirmUrl",OrderMaxUtility.getValidEntityString(this.confirmUrl));
val.set("confirmTemplate",OrderMaxUtility.getValidEntityString(this.confirmTemplate));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("cancelReturnUrl",OrderMaxUtility.getValidEntityString(this.cancelReturnUrl));
val.set("imageUrl",OrderMaxUtility.getValidEntityString(this.imageUrl));
val.set("apiEnvironment",OrderMaxUtility.getValidEntityString(this.apiEnvironment));
val.set("apiPassword",OrderMaxUtility.getValidEntityString(this.apiPassword));
val.set("requireConfirmedShipping",OrderMaxUtility.getValidEntityString(this.requireConfirmedShipping));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("apiUserName",OrderMaxUtility.getValidEntityString(this.apiUserName));
val.set("shippingCallbackUrl",OrderMaxUtility.getValidEntityString(this.shippingCallbackUrl));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("businessEmail",OrderMaxUtility.getValidEntityString(this.businessEmail));
val.set("apiSignature",OrderMaxUtility.getValidEntityString(this.apiSignature));
val.set("notifyUrl",OrderMaxUtility.getValidEntityString(this.notifyUrl));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("redirectUrl",OrderMaxUtility.getValidEntityString(this.redirectUrl));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("returnUrl",this.returnUrl);
valueMap.put("confirmUrl",this.confirmUrl);
valueMap.put("confirmTemplate",this.confirmTemplate);
valueMap.put("cancelReturnUrl",this.cancelReturnUrl);
valueMap.put("imageUrl",this.imageUrl);
valueMap.put("apiEnvironment",this.apiEnvironment);
valueMap.put("apiPassword",this.apiPassword);
valueMap.put("requireConfirmedShipping",this.requireConfirmedShipping);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("apiUserName",this.apiUserName);
valueMap.put("shippingCallbackUrl",this.shippingCallbackUrl);
valueMap.put("businessEmail",this.businessEmail);
valueMap.put("apiSignature",this.apiSignature);
valueMap.put("notifyUrl",this.notifyUrl);
valueMap.put("redirectUrl",this.redirectUrl);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayPayPal");
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
private java.lang.String confirmUrl;
public java.lang.String getconfirmUrl() {
return confirmUrl;
}
public void setconfirmUrl( java.lang.String confirmUrl ) {
this.confirmUrl = confirmUrl;
}
private java.lang.String confirmTemplate;
public java.lang.String getconfirmTemplate() {
return confirmTemplate;
}
public void setconfirmTemplate( java.lang.String confirmTemplate ) {
this.confirmTemplate = confirmTemplate;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String cancelReturnUrl;
public java.lang.String getcancelReturnUrl() {
return cancelReturnUrl;
}
public void setcancelReturnUrl( java.lang.String cancelReturnUrl ) {
this.cancelReturnUrl = cancelReturnUrl;
}
private java.lang.String imageUrl;
public java.lang.String getimageUrl() {
return imageUrl;
}
public void setimageUrl( java.lang.String imageUrl ) {
this.imageUrl = imageUrl;
}
private java.lang.String apiEnvironment;
public java.lang.String getapiEnvironment() {
return apiEnvironment;
}
public void setapiEnvironment( java.lang.String apiEnvironment ) {
this.apiEnvironment = apiEnvironment;
}
private java.lang.String apiPassword;
public java.lang.String getapiPassword() {
return apiPassword;
}
public void setapiPassword( java.lang.String apiPassword ) {
this.apiPassword = apiPassword;
}
private java.lang.String requireConfirmedShipping;
public java.lang.String getrequireConfirmedShipping() {
return requireConfirmedShipping;
}
public void setrequireConfirmedShipping( java.lang.String requireConfirmedShipping ) {
this.requireConfirmedShipping = requireConfirmedShipping;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String apiUserName;
public java.lang.String getapiUserName() {
return apiUserName;
}
public void setapiUserName( java.lang.String apiUserName ) {
this.apiUserName = apiUserName;
}
private java.lang.String shippingCallbackUrl;
public java.lang.String getshippingCallbackUrl() {
return shippingCallbackUrl;
}
public void setshippingCallbackUrl( java.lang.String shippingCallbackUrl ) {
this.shippingCallbackUrl = shippingCallbackUrl;
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
private java.lang.String businessEmail;
public java.lang.String getbusinessEmail() {
return businessEmail;
}
public void setbusinessEmail( java.lang.String businessEmail ) {
this.businessEmail = businessEmail;
}
private java.lang.String apiSignature;
public java.lang.String getapiSignature() {
return apiSignature;
}
public void setapiSignature( java.lang.String apiSignature ) {
this.apiSignature = apiSignature;
}
private java.lang.String notifyUrl;
public java.lang.String getnotifyUrl() {
return notifyUrl;
}
public void setnotifyUrl( java.lang.String notifyUrl ) {
this.notifyUrl = notifyUrl;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<PaymentGatewayPayPal> objectList = new ArrayList<PaymentGatewayPayPal>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayPayPal(genVal));
        }
        return objectList;
    }    
}
