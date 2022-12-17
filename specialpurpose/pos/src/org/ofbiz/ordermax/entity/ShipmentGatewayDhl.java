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
public class ShipmentGatewayDhl implements GenericValueObjectInterface {
public static final String module =ShipmentGatewayDhl.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentGatewayDhl(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentGatewayDhl () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"connectUrl","Connect Url"},
{"shipmentGatewayConfigId","Shipment Gateway Config Id"},
{"rateEstimateTemplate","Rate Estimate Template"},
{"connectTimeout","Connect Timeout"},
{"accessPassword","Access Password"},
{"createdTxStamp","Created Tx Stamp"},
{"headAction","Head Action"},
{"createdStamp","Created Stamp"},
{"labelImageFormat","Label Image Format"},
{"accessShippingKey","Access Shipping Key"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"accessAccountNbr","Access Account Nbr"},
{"accessUserId","Access User Id"},
{"headVersion","Head Version"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.connectUrl = "";
this.shipmentGatewayConfigId = "";
this.rateEstimateTemplate = "";
this.connectTimeout = new Long(0);
this.accessPassword = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.headAction = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.labelImageFormat = "";
this.accessShippingKey = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.accessAccountNbr = "";
this.accessUserId = "";
this.headVersion = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.connectUrl = (java.lang.String) genVal.get("connectUrl");
this.shipmentGatewayConfigId = (java.lang.String) genVal.get("shipmentGatewayConfigId");
this.rateEstimateTemplate = (java.lang.String) genVal.get("rateEstimateTemplate");
this.connectTimeout = (java.lang.Long) genVal.get("connectTimeout");
this.accessPassword = (java.lang.String) genVal.get("accessPassword");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.headAction = (java.lang.String) genVal.get("headAction");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.labelImageFormat = (java.lang.String) genVal.get("labelImageFormat");
this.accessShippingKey = (java.lang.String) genVal.get("accessShippingKey");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.accessAccountNbr = (java.lang.String) genVal.get("accessAccountNbr");
this.accessUserId = (java.lang.String) genVal.get("accessUserId");
this.headVersion = (java.lang.String) genVal.get("headVersion");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("connectUrl",OrderMaxUtility.getValidEntityString(this.connectUrl));
val.set("shipmentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.shipmentGatewayConfigId));
val.set("rateEstimateTemplate",OrderMaxUtility.getValidEntityString(this.rateEstimateTemplate));
val.set("connectTimeout",this.connectTimeout);
val.set("accessPassword",OrderMaxUtility.getValidEntityString(this.accessPassword));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("headAction",OrderMaxUtility.getValidEntityString(this.headAction));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("labelImageFormat",OrderMaxUtility.getValidEntityString(this.labelImageFormat));
val.set("accessShippingKey",OrderMaxUtility.getValidEntityString(this.accessShippingKey));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("accessAccountNbr",OrderMaxUtility.getValidEntityString(this.accessAccountNbr));
val.set("accessUserId",OrderMaxUtility.getValidEntityString(this.accessUserId));
val.set("headVersion",OrderMaxUtility.getValidEntityString(this.headVersion));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("connectUrl",this.connectUrl);
valueMap.put("shipmentGatewayConfigId",this.shipmentGatewayConfigId);
valueMap.put("rateEstimateTemplate",this.rateEstimateTemplate);
valueMap.put("connectTimeout",this.connectTimeout);
valueMap.put("accessPassword",this.accessPassword);
valueMap.put("headAction",this.headAction);
valueMap.put("labelImageFormat",this.labelImageFormat);
valueMap.put("accessShippingKey",this.accessShippingKey);
valueMap.put("accessAccountNbr",this.accessAccountNbr);
valueMap.put("accessUserId",this.accessUserId);
valueMap.put("headVersion",this.headVersion);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentGatewayDhl");
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
private java.lang.String shipmentGatewayConfigId;
public java.lang.String getshipmentGatewayConfigId() {
return shipmentGatewayConfigId;
}
public void setshipmentGatewayConfigId( java.lang.String shipmentGatewayConfigId ) {
this.shipmentGatewayConfigId = shipmentGatewayConfigId;
}
private java.lang.String rateEstimateTemplate;
public java.lang.String getrateEstimateTemplate() {
return rateEstimateTemplate;
}
public void setrateEstimateTemplate( java.lang.String rateEstimateTemplate ) {
this.rateEstimateTemplate = rateEstimateTemplate;
}
private java.lang.Long connectTimeout;
public java.lang.Long getconnectTimeout() {
return connectTimeout;
}
public void setconnectTimeout( java.lang.Long connectTimeout ) {
this.connectTimeout = connectTimeout;
}
private java.lang.String accessPassword;
public java.lang.String getaccessPassword() {
return accessPassword;
}
public void setaccessPassword( java.lang.String accessPassword ) {
this.accessPassword = accessPassword;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String headAction;
public java.lang.String getheadAction() {
return headAction;
}
public void setheadAction( java.lang.String headAction ) {
this.headAction = headAction;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String labelImageFormat;
public java.lang.String getlabelImageFormat() {
return labelImageFormat;
}
public void setlabelImageFormat( java.lang.String labelImageFormat ) {
this.labelImageFormat = labelImageFormat;
}
private java.lang.String accessShippingKey;
public java.lang.String getaccessShippingKey() {
return accessShippingKey;
}
public void setaccessShippingKey( java.lang.String accessShippingKey ) {
this.accessShippingKey = accessShippingKey;
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
private java.lang.String accessUserId;
public java.lang.String getaccessUserId() {
return accessUserId;
}
public void setaccessUserId( java.lang.String accessUserId ) {
this.accessUserId = accessUserId;
}
private java.lang.String headVersion;
public java.lang.String getheadVersion() {
return headVersion;
}
public void setheadVersion( java.lang.String headVersion ) {
this.headVersion = headVersion;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentGatewayDhl> objectList = new ArrayList<ShipmentGatewayDhl>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentGatewayDhl(genVal));
        }
        return objectList;
    }    
}
