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
public class ShipmentGatewayFedex implements GenericValueObjectInterface {
public static final String module =ShipmentGatewayFedex.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentGatewayFedex(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentGatewayFedex () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"connectUrl","Connect Url"},
{"defaultDropoffType","Default Dropoff Type"},
{"accessUserPwd","Access User Pwd"},
{"labelImageType","Label Image Type"},
{"defaultPackagingType","Default Packaging Type"},
{"shipmentGatewayConfigId","Shipment Gateway Config Id"},
{"templateShipment","Template Shipment"},
{"rateEstimateTemplate","Rate Estimate Template"},
{"connectSoapUrl","Connect Soap Url"},
{"accessMeterNumber","Access Meter Number"},
{"connectTimeout","Connect Timeout"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"templateSubscription","Template Subscription"},
{"accessUserKey","Access User Key"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"accessAccountNbr","Access Account Nbr"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.connectUrl = "";
this.defaultDropoffType = "";
this.accessUserPwd = "";
this.labelImageType = "";
this.defaultPackagingType = "";
this.shipmentGatewayConfigId = "";
this.templateShipment = "";
this.rateEstimateTemplate = "";
this.connectSoapUrl = "";
this.accessMeterNumber = "";
this.connectTimeout = new Long(0);
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.templateSubscription = "";
this.accessUserKey = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.accessAccountNbr = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.connectUrl = (java.lang.String) genVal.get("connectUrl");
this.defaultDropoffType = (java.lang.String) genVal.get("defaultDropoffType");
this.accessUserPwd = (java.lang.String) genVal.get("accessUserPwd");
this.labelImageType = (java.lang.String) genVal.get("labelImageType");
this.defaultPackagingType = (java.lang.String) genVal.get("defaultPackagingType");
this.shipmentGatewayConfigId = (java.lang.String) genVal.get("shipmentGatewayConfigId");
this.templateShipment = (java.lang.String) genVal.get("templateShipment");
this.rateEstimateTemplate = (java.lang.String) genVal.get("rateEstimateTemplate");
this.connectSoapUrl = (java.lang.String) genVal.get("connectSoapUrl");
this.accessMeterNumber = (java.lang.String) genVal.get("accessMeterNumber");
this.connectTimeout = (java.lang.Long) genVal.get("connectTimeout");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.templateSubscription = (java.lang.String) genVal.get("templateSubscription");
this.accessUserKey = (java.lang.String) genVal.get("accessUserKey");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.accessAccountNbr = (java.lang.String) genVal.get("accessAccountNbr");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("connectUrl",OrderMaxUtility.getValidEntityString(this.connectUrl));
val.set("defaultDropoffType",OrderMaxUtility.getValidEntityString(this.defaultDropoffType));
val.set("accessUserPwd",OrderMaxUtility.getValidEntityString(this.accessUserPwd));
val.set("labelImageType",OrderMaxUtility.getValidEntityString(this.labelImageType));
val.set("defaultPackagingType",OrderMaxUtility.getValidEntityString(this.defaultPackagingType));
val.set("shipmentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.shipmentGatewayConfigId));
val.set("templateShipment",OrderMaxUtility.getValidEntityString(this.templateShipment));
val.set("rateEstimateTemplate",OrderMaxUtility.getValidEntityString(this.rateEstimateTemplate));
val.set("connectSoapUrl",OrderMaxUtility.getValidEntityString(this.connectSoapUrl));
val.set("accessMeterNumber",OrderMaxUtility.getValidEntityString(this.accessMeterNumber));
val.set("connectTimeout",this.connectTimeout);
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("templateSubscription",OrderMaxUtility.getValidEntityString(this.templateSubscription));
val.set("accessUserKey",OrderMaxUtility.getValidEntityString(this.accessUserKey));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("accessAccountNbr",OrderMaxUtility.getValidEntityString(this.accessAccountNbr));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("connectUrl",this.connectUrl);
valueMap.put("defaultDropoffType",this.defaultDropoffType);
valueMap.put("accessUserPwd",this.accessUserPwd);
valueMap.put("labelImageType",this.labelImageType);
valueMap.put("defaultPackagingType",this.defaultPackagingType);
valueMap.put("shipmentGatewayConfigId",this.shipmentGatewayConfigId);
valueMap.put("templateShipment",this.templateShipment);
valueMap.put("rateEstimateTemplate",this.rateEstimateTemplate);
valueMap.put("connectSoapUrl",this.connectSoapUrl);
valueMap.put("accessMeterNumber",this.accessMeterNumber);
valueMap.put("connectTimeout",this.connectTimeout);
valueMap.put("templateSubscription",this.templateSubscription);
valueMap.put("accessUserKey",this.accessUserKey);
valueMap.put("accessAccountNbr",this.accessAccountNbr);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentGatewayFedex");
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
private java.lang.String connectUrl;
public java.lang.String getconnectUrl() {
return connectUrl;
}
public void setconnectUrl( java.lang.String connectUrl ) {
this.connectUrl = connectUrl;
}
private java.lang.String defaultDropoffType;
public java.lang.String getdefaultDropoffType() {
return defaultDropoffType;
}
public void setdefaultDropoffType( java.lang.String defaultDropoffType ) {
this.defaultDropoffType = defaultDropoffType;
}
private java.lang.String accessUserPwd;
public java.lang.String getaccessUserPwd() {
return accessUserPwd;
}
public void setaccessUserPwd( java.lang.String accessUserPwd ) {
this.accessUserPwd = accessUserPwd;
}
private java.lang.String labelImageType;
public java.lang.String getlabelImageType() {
return labelImageType;
}
public void setlabelImageType( java.lang.String labelImageType ) {
this.labelImageType = labelImageType;
}
private java.lang.String defaultPackagingType;
public java.lang.String getdefaultPackagingType() {
return defaultPackagingType;
}
public void setdefaultPackagingType( java.lang.String defaultPackagingType ) {
this.defaultPackagingType = defaultPackagingType;
}
private java.lang.String shipmentGatewayConfigId;
public java.lang.String getshipmentGatewayConfigId() {
return shipmentGatewayConfigId;
}
public void setshipmentGatewayConfigId( java.lang.String shipmentGatewayConfigId ) {
this.shipmentGatewayConfigId = shipmentGatewayConfigId;
}
private java.lang.String templateShipment;
public java.lang.String gettemplateShipment() {
return templateShipment;
}
public void settemplateShipment( java.lang.String templateShipment ) {
this.templateShipment = templateShipment;
}
private java.lang.String rateEstimateTemplate;
public java.lang.String getrateEstimateTemplate() {
return rateEstimateTemplate;
}
public void setrateEstimateTemplate( java.lang.String rateEstimateTemplate ) {
this.rateEstimateTemplate = rateEstimateTemplate;
}
private java.lang.String connectSoapUrl;
public java.lang.String getconnectSoapUrl() {
return connectSoapUrl;
}
public void setconnectSoapUrl( java.lang.String connectSoapUrl ) {
this.connectSoapUrl = connectSoapUrl;
}
private java.lang.String accessMeterNumber;
public java.lang.String getaccessMeterNumber() {
return accessMeterNumber;
}
public void setaccessMeterNumber( java.lang.String accessMeterNumber ) {
this.accessMeterNumber = accessMeterNumber;
}
private java.lang.Long connectTimeout;
public java.lang.Long getconnectTimeout() {
return connectTimeout;
}
public void setconnectTimeout( java.lang.Long connectTimeout ) {
this.connectTimeout = connectTimeout;
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
private java.lang.String templateSubscription;
public java.lang.String gettemplateSubscription() {
return templateSubscription;
}
public void settemplateSubscription( java.lang.String templateSubscription ) {
this.templateSubscription = templateSubscription;
}
private java.lang.String accessUserKey;
public java.lang.String getaccessUserKey() {
return accessUserKey;
}
public void setaccessUserKey( java.lang.String accessUserKey ) {
this.accessUserKey = accessUserKey;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String accessAccountNbr;
public java.lang.String getaccessAccountNbr() {
return accessAccountNbr;
}
public void setaccessAccountNbr( java.lang.String accessAccountNbr ) {
this.accessAccountNbr = accessAccountNbr;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentGatewayFedex> objectList = new ArrayList<ShipmentGatewayFedex>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentGatewayFedex(genVal));
        }
        return objectList;
    }    
}
