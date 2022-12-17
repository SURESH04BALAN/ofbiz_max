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
public class PaymentGatewayClearCommerce implements GenericValueObjectInterface {
public static final String module =PaymentGatewayClearCommerce.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentGatewayClearCommerce(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentGatewayClearCommerce () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"serverURL","Server Url"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"userAlias","User Alias"},
{"processMode","Process Mode"},
{"effectiveAlias","Effective Alias"},
{"clientId","Client Id"},
{"sourceId","Source Id"},
{"groupId","Group Id"},
{"pwd","Pwd"},
{"username","Username"},
{"enableCVM","Enable Cvm"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.serverURL = "";
this.paymentGatewayConfigId = "";
this.userAlias = "";
this.processMode = "";
this.effectiveAlias = "";
this.clientId = "";
this.sourceId = "";
this.groupId = "";
this.pwd = "";
this.username = "";
this.enableCVM = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.serverURL = (java.lang.String) genVal.get("serverURL");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.userAlias = (java.lang.String) genVal.get("userAlias");
this.processMode = (java.lang.String) genVal.get("processMode");
this.effectiveAlias = (java.lang.String) genVal.get("effectiveAlias");
this.clientId = (java.lang.String) genVal.get("clientId");
this.sourceId = (java.lang.String) genVal.get("sourceId");
this.groupId = (java.lang.String) genVal.get("groupId");
this.pwd = (java.lang.String) genVal.get("pwd");
this.username = (java.lang.String) genVal.get("username");
this.enableCVM = (java.lang.String) genVal.get("enableCVM");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("serverURL",OrderMaxUtility.getValidEntityString(this.serverURL));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("userAlias",OrderMaxUtility.getValidEntityString(this.userAlias));
val.set("processMode",OrderMaxUtility.getValidEntityString(this.processMode));
val.set("effectiveAlias",OrderMaxUtility.getValidEntityString(this.effectiveAlias));
val.set("clientId",OrderMaxUtility.getValidEntityString(this.clientId));
val.set("sourceId",OrderMaxUtility.getValidEntityString(this.sourceId));
val.set("groupId",OrderMaxUtility.getValidEntityString(this.groupId));
val.set("pwd",OrderMaxUtility.getValidEntityString(this.pwd));
val.set("username",OrderMaxUtility.getValidEntityString(this.username));
val.set("enableCVM",OrderMaxUtility.getValidEntityString(this.enableCVM));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("serverURL",this.serverURL);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("userAlias",this.userAlias);
valueMap.put("processMode",this.processMode);
valueMap.put("effectiveAlias",this.effectiveAlias);
valueMap.put("clientId",this.clientId);
valueMap.put("sourceId",this.sourceId);
valueMap.put("groupId",this.groupId);
valueMap.put("pwd",this.pwd);
valueMap.put("username",this.username);
valueMap.put("enableCVM",this.enableCVM);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentGatewayClearCommerce");
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
private java.lang.String userAlias;
public java.lang.String getuserAlias() {
return userAlias;
}
public void setuserAlias( java.lang.String userAlias ) {
this.userAlias = userAlias;
}
private java.lang.String processMode;
public java.lang.String getprocessMode() {
return processMode;
}
public void setprocessMode( java.lang.String processMode ) {
this.processMode = processMode;
}
private java.lang.String effectiveAlias;
public java.lang.String geteffectiveAlias() {
return effectiveAlias;
}
public void seteffectiveAlias( java.lang.String effectiveAlias ) {
this.effectiveAlias = effectiveAlias;
}
private java.lang.String clientId;
public java.lang.String getclientId() {
return clientId;
}
public void setclientId( java.lang.String clientId ) {
this.clientId = clientId;
}
private java.lang.String sourceId;
public java.lang.String getsourceId() {
return sourceId;
}
public void setsourceId( java.lang.String sourceId ) {
this.sourceId = sourceId;
}
private java.lang.String groupId;
public java.lang.String getgroupId() {
return groupId;
}
public void setgroupId( java.lang.String groupId ) {
this.groupId = groupId;
}
private java.lang.String pwd;
public java.lang.String getpwd() {
return pwd;
}
public void setpwd( java.lang.String pwd ) {
this.pwd = pwd;
}
private java.lang.String username;
public java.lang.String getusername() {
return username;
}
public void setusername( java.lang.String username ) {
this.username = username;
}
private java.lang.String enableCVM;
public java.lang.String getenableCVM() {
return enableCVM;
}
public void setenableCVM( java.lang.String enableCVM ) {
this.enableCVM = enableCVM;
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
        Collection<PaymentGatewayClearCommerce> objectList = new ArrayList<PaymentGatewayClearCommerce>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGatewayClearCommerce(genVal));
        }
        return objectList;
    }    
}
