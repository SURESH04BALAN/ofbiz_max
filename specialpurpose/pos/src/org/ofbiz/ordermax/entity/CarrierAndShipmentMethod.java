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
public class CarrierAndShipmentMethod implements GenericValueObjectInterface {
public static final String module =CarrierAndShipmentMethod.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CarrierAndShipmentMethod(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CarrierAndShipmentMethod () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"roleTypeId","Role Type Id"},
{"partyId","Party Id"},
{"description","Description"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"sequenceNumber","Sequence Number"},
};
protected void initObject(){
this.roleTypeId = "";
this.partyId = "";
this.description = "";
this.shipmentMethodTypeId = "";
this.sequenceNumber = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.description = (java.lang.String) genVal.get("description");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.sequenceNumber = (java.lang.Long) genVal.get("sequenceNumber");
}
protected void getGenericValue(GenericValue val) {
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("sequenceNumber",this.sequenceNumber);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("partyId",this.partyId);
valueMap.put("description",this.description);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("sequenceNumber",this.sequenceNumber);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CarrierAndShipmentMethod");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
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
        Collection<CarrierAndShipmentMethod> objectList = new ArrayList<CarrierAndShipmentMethod>();
        for (GenericValue genVal : genList) {
            objectList.add(new CarrierAndShipmentMethod(genVal));
        }
        return objectList;
    }    
}
