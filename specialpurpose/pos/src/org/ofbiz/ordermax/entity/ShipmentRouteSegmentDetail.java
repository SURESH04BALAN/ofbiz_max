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
public class ShipmentRouteSegmentDetail implements GenericValueObjectInterface {
public static final String module =ShipmentRouteSegmentDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentRouteSegmentDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentRouteSegmentDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"carrierPartyId","Carrier Party Id"},
{"originFacilityId","Origin Facility Id"},
{"carrierServiceStatusId","Carrier Service Status Id"},
{"billingWeight","Billing Weight"},
{"shipmentId","Shipment Id"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"billingWeightUomId","Billing Weight Uom Id"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"primaryOrderId","Primary Order Id"},
};
protected void initObject(){
this.statusId = "";
this.carrierPartyId = "";
this.originFacilityId = "";
this.carrierServiceStatusId = "";
this.billingWeight = "";
this.shipmentId = "";
this.shipmentRouteSegmentId = "";
this.billingWeightUomId = "";
this.shipmentMethodTypeId = "";
this.primaryOrderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.carrierServiceStatusId = (java.lang.String) genVal.get("carrierServiceStatusId");
this.billingWeight = (java.lang.String) genVal.get("billingWeight");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.billingWeightUomId = (java.lang.String) genVal.get("billingWeightUomId");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("carrierServiceStatusId",OrderMaxUtility.getValidEntityString(this.carrierServiceStatusId));
val.set("billingWeight",OrderMaxUtility.getValidEntityString(this.billingWeight));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("billingWeightUomId",OrderMaxUtility.getValidEntityString(this.billingWeightUomId));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("carrierServiceStatusId",this.carrierServiceStatusId);
valueMap.put("billingWeight",this.billingWeight);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("billingWeightUomId",this.billingWeightUomId);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("primaryOrderId",this.primaryOrderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentRouteSegmentDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String carrierServiceStatusId;
public java.lang.String getcarrierServiceStatusId() {
return carrierServiceStatusId;
}
public void setcarrierServiceStatusId( java.lang.String carrierServiceStatusId ) {
this.carrierServiceStatusId = carrierServiceStatusId;
}
private java.lang.String billingWeight;
public java.lang.String getbillingWeight() {
return billingWeight;
}
public void setbillingWeight( java.lang.String billingWeight ) {
this.billingWeight = billingWeight;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.lang.String shipmentRouteSegmentId;
public java.lang.String getshipmentRouteSegmentId() {
return shipmentRouteSegmentId;
}
public void setshipmentRouteSegmentId( java.lang.String shipmentRouteSegmentId ) {
this.shipmentRouteSegmentId = shipmentRouteSegmentId;
}
private java.lang.String billingWeightUomId;
public java.lang.String getbillingWeightUomId() {
return billingWeightUomId;
}
public void setbillingWeightUomId( java.lang.String billingWeightUomId ) {
this.billingWeightUomId = billingWeightUomId;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentRouteSegmentDetail> objectList = new ArrayList<ShipmentRouteSegmentDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentRouteSegmentDetail(genVal));
        }
        return objectList;
    }    
}
