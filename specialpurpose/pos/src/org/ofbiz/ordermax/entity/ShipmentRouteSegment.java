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
public class ShipmentRouteSegment implements GenericValueObjectInterface {
public static final String module =ShipmentRouteSegment.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentRouteSegment(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentRouteSegment () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"actualArrivalDate","Actual Arrival Date"},
{"upsHighValueReport","Ups High Value Report"},
{"originFacilityId","Origin Facility Id"},
{"trackingDigest","Tracking Digest"},
{"homeDeliveryType","Home Delivery Type"},
{"originContactMechId","Origin Contact Mech Id"},
{"estimatedArrivalDate","Estimated Arrival Date"},
{"carrierRestrictionCodes","Carrier Restriction Codes"},
{"destTelecomNumberId","Dest Telecom Number Id"},
{"updatedByUserLoginId","Updated By User Login Id"},
{"originTelecomNumberId","Origin Telecom Number Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"estimatedStartDate","Estimated Start Date"},
{"actualStartDate","Actual Start Date"},
{"thirdPartyAccountNumber","Third Party Account Number"},
{"trackingIdNumber","Tracking Id Number"},
{"thirdPartyPostalCode","Third Party Postal Code"},
{"actualServiceCost","Actual Service Cost"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"carrierPartyId","Carrier Party Id"},
{"currencyUomId","Currency Uom Id"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"actualCost","Actual Cost"},
{"carrierRestrictionDesc","Carrier Restriction Desc"},
{"homeDeliveryDate","Home Delivery Date"},
{"deliveryId","Delivery Id"},
{"carrierServiceStatusId","Carrier Service Status Id"},
{"actualOtherCost","Actual Other Cost"},
{"billingWeight","Billing Weight"},
{"actualTransportCost","Actual Transport Cost"},
{"destContactMechId","Dest Contact Mech Id"},
{"destFacilityId","Dest Facility Id"},
{"lastUpdatedDate","Last Updated Date"},
{"billingWeightUomId","Billing Weight Uom Id"},
{"thirdPartyCountryGeoCode","Third Party Country Geo Code"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"carrierDeliveryZone","Carrier Delivery Zone"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.actualArrivalDate = UtilDateTime.nowTimestamp();
this.upsHighValueReport = "";
this.originFacilityId = "";
this.trackingDigest = "";
this.homeDeliveryType = "";
this.originContactMechId = "";
this.estimatedArrivalDate = UtilDateTime.nowTimestamp();
this.carrierRestrictionCodes = "";
this.destTelecomNumberId = "";
this.updatedByUserLoginId = "";
this.originTelecomNumberId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.estimatedStartDate = UtilDateTime.nowTimestamp();
this.actualStartDate = UtilDateTime.nowTimestamp();
this.thirdPartyAccountNumber = "";
this.trackingIdNumber = "";
this.thirdPartyPostalCode = "";
this.actualServiceCost = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.carrierPartyId = "";
this.currencyUomId = "";
this.shipmentRouteSegmentId = "";
this.actualCost = "";
this.carrierRestrictionDesc = "";
this.homeDeliveryDate = UtilDateTime.nowTimestamp();
this.deliveryId = "";
this.carrierServiceStatusId = "";
this.actualOtherCost = "";
this.billingWeight = "";
this.actualTransportCost = "";
this.destContactMechId = "";
this.destFacilityId = "";
this.lastUpdatedDate = UtilDateTime.nowTimestamp();
this.billingWeightUomId = "";
this.thirdPartyCountryGeoCode = "";
this.shipmentMethodTypeId = "";
this.carrierDeliveryZone = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.actualArrivalDate = (java.sql.Timestamp) genVal.get("actualArrivalDate");
this.upsHighValueReport = (java.lang.String) genVal.get("upsHighValueReport");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.trackingDigest = (java.lang.String) genVal.get("trackingDigest");
this.homeDeliveryType = (java.lang.String) genVal.get("homeDeliveryType");
this.originContactMechId = (java.lang.String) genVal.get("originContactMechId");
this.estimatedArrivalDate = (java.sql.Timestamp) genVal.get("estimatedArrivalDate");
this.carrierRestrictionCodes = (java.lang.String) genVal.get("carrierRestrictionCodes");
this.destTelecomNumberId = (java.lang.String) genVal.get("destTelecomNumberId");
this.updatedByUserLoginId = (java.lang.String) genVal.get("updatedByUserLoginId");
this.originTelecomNumberId = (java.lang.String) genVal.get("originTelecomNumberId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.estimatedStartDate = (java.sql.Timestamp) genVal.get("estimatedStartDate");
this.actualStartDate = (java.sql.Timestamp) genVal.get("actualStartDate");
this.thirdPartyAccountNumber = (java.lang.String) genVal.get("thirdPartyAccountNumber");
this.trackingIdNumber = (java.lang.String) genVal.get("trackingIdNumber");
this.thirdPartyPostalCode = (java.lang.String) genVal.get("thirdPartyPostalCode");
this.actualServiceCost = (java.lang.String) genVal.get("actualServiceCost");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.actualCost = (java.lang.String) genVal.get("actualCost");
this.carrierRestrictionDesc = (java.lang.String) genVal.get("carrierRestrictionDesc");
this.homeDeliveryDate = (java.sql.Timestamp) genVal.get("homeDeliveryDate");
this.deliveryId = (java.lang.String) genVal.get("deliveryId");
this.carrierServiceStatusId = (java.lang.String) genVal.get("carrierServiceStatusId");
this.actualOtherCost = (java.lang.String) genVal.get("actualOtherCost");
this.billingWeight = (java.lang.String) genVal.get("billingWeight");
this.actualTransportCost = (java.lang.String) genVal.get("actualTransportCost");
this.destContactMechId = (java.lang.String) genVal.get("destContactMechId");
this.destFacilityId = (java.lang.String) genVal.get("destFacilityId");
this.lastUpdatedDate = (java.sql.Timestamp) genVal.get("lastUpdatedDate");
this.billingWeightUomId = (java.lang.String) genVal.get("billingWeightUomId");
this.thirdPartyCountryGeoCode = (java.lang.String) genVal.get("thirdPartyCountryGeoCode");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.carrierDeliveryZone = (java.lang.String) genVal.get("carrierDeliveryZone");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("actualArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.actualArrivalDate));
val.set("upsHighValueReport",OrderMaxUtility.getValidEntityString(this.upsHighValueReport));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("trackingDigest",OrderMaxUtility.getValidEntityString(this.trackingDigest));
val.set("homeDeliveryType",OrderMaxUtility.getValidEntityString(this.homeDeliveryType));
val.set("originContactMechId",OrderMaxUtility.getValidEntityString(this.originContactMechId));
val.set("estimatedArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedArrivalDate));
val.set("carrierRestrictionCodes",OrderMaxUtility.getValidEntityString(this.carrierRestrictionCodes));
val.set("destTelecomNumberId",OrderMaxUtility.getValidEntityString(this.destTelecomNumberId));
val.set("updatedByUserLoginId",OrderMaxUtility.getValidEntityString(this.updatedByUserLoginId));
val.set("originTelecomNumberId",OrderMaxUtility.getValidEntityString(this.originTelecomNumberId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("estimatedStartDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedStartDate));
val.set("actualStartDate",OrderMaxUtility.getValidEntityTimestamp(this.actualStartDate));
val.set("thirdPartyAccountNumber",OrderMaxUtility.getValidEntityString(this.thirdPartyAccountNumber));
val.set("trackingIdNumber",OrderMaxUtility.getValidEntityString(this.trackingIdNumber));
val.set("thirdPartyPostalCode",OrderMaxUtility.getValidEntityString(this.thirdPartyPostalCode));
val.set("actualServiceCost",OrderMaxUtility.getValidEntityString(this.actualServiceCost));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("actualCost",OrderMaxUtility.getValidEntityString(this.actualCost));
val.set("carrierRestrictionDesc",OrderMaxUtility.getValidEntityString(this.carrierRestrictionDesc));
val.set("homeDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.homeDeliveryDate));
val.set("deliveryId",OrderMaxUtility.getValidEntityString(this.deliveryId));
val.set("carrierServiceStatusId",OrderMaxUtility.getValidEntityString(this.carrierServiceStatusId));
val.set("actualOtherCost",OrderMaxUtility.getValidEntityString(this.actualOtherCost));
val.set("billingWeight",OrderMaxUtility.getValidEntityString(this.billingWeight));
val.set("actualTransportCost",OrderMaxUtility.getValidEntityString(this.actualTransportCost));
val.set("destContactMechId",OrderMaxUtility.getValidEntityString(this.destContactMechId));
val.set("destFacilityId",OrderMaxUtility.getValidEntityString(this.destFacilityId));
val.set("lastUpdatedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedDate));
val.set("billingWeightUomId",OrderMaxUtility.getValidEntityString(this.billingWeightUomId));
val.set("thirdPartyCountryGeoCode",OrderMaxUtility.getValidEntityString(this.thirdPartyCountryGeoCode));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("carrierDeliveryZone",OrderMaxUtility.getValidEntityString(this.carrierDeliveryZone));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("actualArrivalDate",this.actualArrivalDate);
valueMap.put("upsHighValueReport",this.upsHighValueReport);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("trackingDigest",this.trackingDigest);
valueMap.put("homeDeliveryType",this.homeDeliveryType);
valueMap.put("originContactMechId",this.originContactMechId);
valueMap.put("estimatedArrivalDate",this.estimatedArrivalDate);
valueMap.put("carrierRestrictionCodes",this.carrierRestrictionCodes);
valueMap.put("destTelecomNumberId",this.destTelecomNumberId);
valueMap.put("updatedByUserLoginId",this.updatedByUserLoginId);
valueMap.put("originTelecomNumberId",this.originTelecomNumberId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("estimatedStartDate",this.estimatedStartDate);
valueMap.put("actualStartDate",this.actualStartDate);
valueMap.put("thirdPartyAccountNumber",this.thirdPartyAccountNumber);
valueMap.put("trackingIdNumber",this.trackingIdNumber);
valueMap.put("thirdPartyPostalCode",this.thirdPartyPostalCode);
valueMap.put("actualServiceCost",this.actualServiceCost);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("actualCost",this.actualCost);
valueMap.put("carrierRestrictionDesc",this.carrierRestrictionDesc);
valueMap.put("homeDeliveryDate",this.homeDeliveryDate);
valueMap.put("deliveryId",this.deliveryId);
valueMap.put("carrierServiceStatusId",this.carrierServiceStatusId);
valueMap.put("actualOtherCost",this.actualOtherCost);
valueMap.put("billingWeight",this.billingWeight);
valueMap.put("actualTransportCost",this.actualTransportCost);
valueMap.put("destContactMechId",this.destContactMechId);
valueMap.put("destFacilityId",this.destFacilityId);
valueMap.put("lastUpdatedDate",this.lastUpdatedDate);
valueMap.put("billingWeightUomId",this.billingWeightUomId);
valueMap.put("thirdPartyCountryGeoCode",this.thirdPartyCountryGeoCode);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("carrierDeliveryZone",this.carrierDeliveryZone);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentRouteSegment");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp actualArrivalDate;
public java.sql.Timestamp getactualArrivalDate() {
return actualArrivalDate;
}
public void setactualArrivalDate( java.sql.Timestamp actualArrivalDate ) {
this.actualArrivalDate = actualArrivalDate;
}
private java.lang.String upsHighValueReport;
public java.lang.String getupsHighValueReport() {
return upsHighValueReport;
}
public void setupsHighValueReport( java.lang.String upsHighValueReport ) {
this.upsHighValueReport = upsHighValueReport;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String trackingDigest;
public java.lang.String gettrackingDigest() {
return trackingDigest;
}
public void settrackingDigest( java.lang.String trackingDigest ) {
this.trackingDigest = trackingDigest;
}
private java.lang.String homeDeliveryType;
public java.lang.String gethomeDeliveryType() {
return homeDeliveryType;
}
public void sethomeDeliveryType( java.lang.String homeDeliveryType ) {
this.homeDeliveryType = homeDeliveryType;
}
private java.lang.String originContactMechId;
public java.lang.String getoriginContactMechId() {
return originContactMechId;
}
public void setoriginContactMechId( java.lang.String originContactMechId ) {
this.originContactMechId = originContactMechId;
}
private java.sql.Timestamp estimatedArrivalDate;
public java.sql.Timestamp getestimatedArrivalDate() {
return estimatedArrivalDate;
}
public void setestimatedArrivalDate( java.sql.Timestamp estimatedArrivalDate ) {
this.estimatedArrivalDate = estimatedArrivalDate;
}
private java.lang.String carrierRestrictionCodes;
public java.lang.String getcarrierRestrictionCodes() {
return carrierRestrictionCodes;
}
public void setcarrierRestrictionCodes( java.lang.String carrierRestrictionCodes ) {
this.carrierRestrictionCodes = carrierRestrictionCodes;
}
private java.lang.String destTelecomNumberId;
public java.lang.String getdestTelecomNumberId() {
return destTelecomNumberId;
}
public void setdestTelecomNumberId( java.lang.String destTelecomNumberId ) {
this.destTelecomNumberId = destTelecomNumberId;
}
private java.lang.String updatedByUserLoginId;
public java.lang.String getupdatedByUserLoginId() {
return updatedByUserLoginId;
}
public void setupdatedByUserLoginId( java.lang.String updatedByUserLoginId ) {
this.updatedByUserLoginId = updatedByUserLoginId;
}
private java.lang.String originTelecomNumberId;
public java.lang.String getoriginTelecomNumberId() {
return originTelecomNumberId;
}
public void setoriginTelecomNumberId( java.lang.String originTelecomNumberId ) {
this.originTelecomNumberId = originTelecomNumberId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.sql.Timestamp estimatedStartDate;
public java.sql.Timestamp getestimatedStartDate() {
return estimatedStartDate;
}
public void setestimatedStartDate( java.sql.Timestamp estimatedStartDate ) {
this.estimatedStartDate = estimatedStartDate;
}
private java.sql.Timestamp actualStartDate;
public java.sql.Timestamp getactualStartDate() {
return actualStartDate;
}
public void setactualStartDate( java.sql.Timestamp actualStartDate ) {
this.actualStartDate = actualStartDate;
}
private java.lang.String thirdPartyAccountNumber;
public java.lang.String getthirdPartyAccountNumber() {
return thirdPartyAccountNumber;
}
public void setthirdPartyAccountNumber( java.lang.String thirdPartyAccountNumber ) {
this.thirdPartyAccountNumber = thirdPartyAccountNumber;
}
private java.lang.String trackingIdNumber;
public java.lang.String gettrackingIdNumber() {
return trackingIdNumber;
}
public void settrackingIdNumber( java.lang.String trackingIdNumber ) {
this.trackingIdNumber = trackingIdNumber;
}
private java.lang.String thirdPartyPostalCode;
public java.lang.String getthirdPartyPostalCode() {
return thirdPartyPostalCode;
}
public void setthirdPartyPostalCode( java.lang.String thirdPartyPostalCode ) {
this.thirdPartyPostalCode = thirdPartyPostalCode;
}
private java.lang.String actualServiceCost;
public java.lang.String getactualServiceCost() {
return actualServiceCost;
}
public void setactualServiceCost( java.lang.String actualServiceCost ) {
this.actualServiceCost = actualServiceCost;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String shipmentRouteSegmentId;
public java.lang.String getshipmentRouteSegmentId() {
return shipmentRouteSegmentId;
}
public void setshipmentRouteSegmentId( java.lang.String shipmentRouteSegmentId ) {
this.shipmentRouteSegmentId = shipmentRouteSegmentId;
}
private java.lang.String actualCost;
public java.lang.String getactualCost() {
return actualCost;
}
public void setactualCost( java.lang.String actualCost ) {
this.actualCost = actualCost;
}
private java.lang.String carrierRestrictionDesc;
public java.lang.String getcarrierRestrictionDesc() {
return carrierRestrictionDesc;
}
public void setcarrierRestrictionDesc( java.lang.String carrierRestrictionDesc ) {
this.carrierRestrictionDesc = carrierRestrictionDesc;
}
private java.sql.Timestamp homeDeliveryDate;
public java.sql.Timestamp gethomeDeliveryDate() {
return homeDeliveryDate;
}
public void sethomeDeliveryDate( java.sql.Timestamp homeDeliveryDate ) {
this.homeDeliveryDate = homeDeliveryDate;
}
private java.lang.String deliveryId;
public java.lang.String getdeliveryId() {
return deliveryId;
}
public void setdeliveryId( java.lang.String deliveryId ) {
this.deliveryId = deliveryId;
}
private java.lang.String carrierServiceStatusId;
public java.lang.String getcarrierServiceStatusId() {
return carrierServiceStatusId;
}
public void setcarrierServiceStatusId( java.lang.String carrierServiceStatusId ) {
this.carrierServiceStatusId = carrierServiceStatusId;
}
private java.lang.String actualOtherCost;
public java.lang.String getactualOtherCost() {
return actualOtherCost;
}
public void setactualOtherCost( java.lang.String actualOtherCost ) {
this.actualOtherCost = actualOtherCost;
}
private java.lang.String billingWeight;
public java.lang.String getbillingWeight() {
return billingWeight;
}
public void setbillingWeight( java.lang.String billingWeight ) {
this.billingWeight = billingWeight;
}
private java.lang.String actualTransportCost;
public java.lang.String getactualTransportCost() {
return actualTransportCost;
}
public void setactualTransportCost( java.lang.String actualTransportCost ) {
this.actualTransportCost = actualTransportCost;
}
private java.lang.String destContactMechId;
public java.lang.String getdestContactMechId() {
return destContactMechId;
}
public void setdestContactMechId( java.lang.String destContactMechId ) {
this.destContactMechId = destContactMechId;
}
private java.lang.String destFacilityId;
public java.lang.String getdestFacilityId() {
return destFacilityId;
}
public void setdestFacilityId( java.lang.String destFacilityId ) {
this.destFacilityId = destFacilityId;
}
private java.sql.Timestamp lastUpdatedDate;
public java.sql.Timestamp getlastUpdatedDate() {
return lastUpdatedDate;
}
public void setlastUpdatedDate( java.sql.Timestamp lastUpdatedDate ) {
this.lastUpdatedDate = lastUpdatedDate;
}
private java.lang.String billingWeightUomId;
public java.lang.String getbillingWeightUomId() {
return billingWeightUomId;
}
public void setbillingWeightUomId( java.lang.String billingWeightUomId ) {
this.billingWeightUomId = billingWeightUomId;
}
private java.lang.String thirdPartyCountryGeoCode;
public java.lang.String getthirdPartyCountryGeoCode() {
return thirdPartyCountryGeoCode;
}
public void setthirdPartyCountryGeoCode( java.lang.String thirdPartyCountryGeoCode ) {
this.thirdPartyCountryGeoCode = thirdPartyCountryGeoCode;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String carrierDeliveryZone;
public java.lang.String getcarrierDeliveryZone() {
return carrierDeliveryZone;
}
public void setcarrierDeliveryZone( java.lang.String carrierDeliveryZone ) {
this.carrierDeliveryZone = carrierDeliveryZone;
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
        Collection<ShipmentRouteSegment> objectList = new ArrayList<ShipmentRouteSegment>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentRouteSegment(genVal));
        }
        return objectList;
    }    
}
