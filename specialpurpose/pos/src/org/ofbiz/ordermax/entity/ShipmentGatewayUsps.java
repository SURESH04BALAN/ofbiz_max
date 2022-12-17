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
public class ShipmentGatewayUsps implements GenericValueObjectInterface {
public static final String module =ShipmentGatewayUsps.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentGatewayUsps(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentGatewayUsps () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"test","Test"},
{"connectUrl","Connect Url"},
{"shipmentGatewayConfigId","Shipment Gateway Config Id"},
{"connectTimeout","Connect Timeout"},
{"accessPassword","Access Password"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"connectUrlLabels","Connect Url Labels"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"accessUserId","Access User Id"},
{"maxEstimateWeight","Max Estimate Weight"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.test = "";
this.connectUrl = "";
this.shipmentGatewayConfigId = "";
this.connectTimeout = new Long(0);
this.accessPassword = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.connectUrlLabels = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.accessUserId = "";
this.maxEstimateWeight = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.test = (java.lang.String) genVal.get("test");
this.connectUrl = (java.lang.String) genVal.get("connectUrl");
this.shipmentGatewayConfigId = (java.lang.String) genVal.get("shipmentGatewayConfigId");
this.connectTimeout = (java.lang.Long) genVal.get("connectTimeout");
this.accessPassword = (java.lang.String) genVal.get("accessPassword");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.connectUrlLabels = (java.lang.String) genVal.get("connectUrlLabels");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.accessUserId = (java.lang.String) genVal.get("accessUserId");
this.maxEstimateWeight = (java.lang.Long) genVal.get("maxEstimateWeight");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("test",OrderMaxUtility.getValidEntityString(this.test));
val.set("connectUrl",OrderMaxUtility.getValidEntityString(this.connectUrl));
val.set("shipmentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.shipmentGatewayConfigId));
val.set("connectTimeout",this.connectTimeout);
val.set("accessPassword",OrderMaxUtility.getValidEntityString(this.accessPassword));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("connectUrlLabels",OrderMaxUtility.getValidEntityString(this.connectUrlLabels));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("accessUserId",OrderMaxUtility.getValidEntityString(this.accessUserId));
val.set("maxEstimateWeight",this.maxEstimateWeight);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("test",this.test);
valueMap.put("connectUrl",this.connectUrl);
valueMap.put("shipmentGatewayConfigId",this.shipmentGatewayConfigId);
valueMap.put("connectTimeout",this.connectTimeout);
valueMap.put("accessPassword",this.accessPassword);
valueMap.put("connectUrlLabels",this.connectUrlLabels);
valueMap.put("accessUserId",this.accessUserId);
valueMap.put("maxEstimateWeight",this.maxEstimateWeight);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentGatewayUsps");
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
private java.lang.String test;
public java.lang.String gettest() {
return test;
}
public void settest( java.lang.String test ) {
this.test = test;
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
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String connectUrlLabels;
public java.lang.String getconnectUrlLabels() {
return connectUrlLabels;
}
public void setconnectUrlLabels( java.lang.String connectUrlLabels ) {
this.connectUrlLabels = connectUrlLabels;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String accessUserId;
public java.lang.String getaccessUserId() {
return accessUserId;
}
public void setaccessUserId( java.lang.String accessUserId ) {
this.accessUserId = accessUserId;
}
private java.lang.Long maxEstimateWeight;
public java.lang.Long getmaxEstimateWeight() {
return maxEstimateWeight;
}
public void setmaxEstimateWeight( java.lang.Long maxEstimateWeight ) {
this.maxEstimateWeight = maxEstimateWeight;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentGatewayUsps> objectList = new ArrayList<ShipmentGatewayUsps>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentGatewayUsps(genVal));
        }
        return objectList;
    }    
}
