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
public class CarrierShipmentMethod implements GenericValueObjectInterface {
public static final String module =CarrierShipmentMethod.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CarrierShipmentMethod(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CarrierShipmentMethod () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"roleTypeId","Role Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"partyId","Party Id"},
{"createdStamp","Created Stamp"},
{"carrierServiceCode","Carrier Service Code"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"sequenceNumber","Sequence Number"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.roleTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.partyId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.carrierServiceCode = "";
this.shipmentMethodTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.sequenceNumber = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.partyId = (java.lang.String) genVal.get("partyId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.carrierServiceCode = (java.lang.String) genVal.get("carrierServiceCode");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.sequenceNumber = (java.lang.Long) genVal.get("sequenceNumber");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("carrierServiceCode",OrderMaxUtility.getValidEntityString(this.carrierServiceCode));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("sequenceNumber",this.sequenceNumber);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("partyId",this.partyId);
valueMap.put("carrierServiceCode",this.carrierServiceCode);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("sequenceNumber",this.sequenceNumber);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CarrierShipmentMethod");
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
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String carrierServiceCode;
public java.lang.String getcarrierServiceCode() {
return carrierServiceCode;
}
public void setcarrierServiceCode( java.lang.String carrierServiceCode ) {
this.carrierServiceCode = carrierServiceCode;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.Long sequenceNumber;
public java.lang.Long getsequenceNumber() {
return sequenceNumber;
}
public void setsequenceNumber( java.lang.Long sequenceNumber ) {
this.sequenceNumber = sequenceNumber;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CarrierShipmentMethod> objectList = new ArrayList<CarrierShipmentMethod>();
        for (GenericValue genVal : genList) {
            objectList.add(new CarrierShipmentMethod(genVal));
        }
        return objectList;
    }    
}
