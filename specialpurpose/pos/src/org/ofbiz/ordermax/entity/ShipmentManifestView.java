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
public class ShipmentManifestView implements GenericValueObjectInterface {
public static final String module =ShipmentManifestView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentManifestView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentManifestView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"boxNumber","Box Number"},
{"destToName","Dest To Name"},
{"destContactNumber","Dest Contact Number"},
{"destStateProvinceGeoId","Dest State Province Geo Id"},
{"originContactMechId","Origin Contact Mech Id"},
{"originCountryCode","Origin Country Code"},
{"originAddress1","Origin Address 1"},
{"destAreaCode","Dest Area Code"},
{"destTelecomNumberId","Dest Telecom Number Id"},
{"originAttnName","Origin Attn Name"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentId","Shipment Id"},
{"originAddress2","Origin Address 2"},
{"originStateProvinceGeoId","Origin State Province Geo Id"},
{"originContactNumber","Origin Contact Number"},
{"carrierLastName","Carrier Last Name"},
{"originToName","Origin To Name"},
{"actualStartDate","Actual Start Date"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"destAttnName","Dest Attn Name"},
{"destAddress1","Dest Address 1"},
{"weightUomDescription","Weight Uom Description"},
{"weightUomAbbreviation","Weight Uom Abbreviation"},
{"packageDateCreated","Package Date Created"},
{"actualCost","Actual Cost"},
{"destPostalCodeGeoId","Dest Postal Code Geo Id"},
{"destCountryCode","Dest Country Code"},
{"originDirections","Origin Directions"},
{"orderItemSeqId","Order Item Seq Id"},
{"destCountryGeoId","Dest Country Geo Id"},
{"destContactMechId","Dest Contact Mech Id"},
{"destFacilityId","Dest Facility Id"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"issuedByUserLoginId","Issued By User Login Id"},
{"destCity","Dest City"},
{"actualArrivalDate","Actual Arrival Date"},
{"weight","Weight"},
{"originFacilityId","Origin Facility Id"},
{"carrierFirstName","Carrier First Name"},
{"shipmentContentDescription","Shipment Content Description"},
{"estimatedArrivalDate","Estimated Arrival Date"},
{"originAreaCode","Origin Area Code"},
{"originTelecomNumberId","Origin Telecom Number Id"},
{"trackingCode","Tracking Code"},
{"shipmentMethodDescription","Shipment Method Description"},
{"estimatedStartDate","Estimated Start Date"},
{"destFacilityName","Dest Facility Name"},
{"originPostalCodeGeoId","Origin Postal Code Geo Id"},
{"issuedDateTime","Issued Date Time"},
{"issuedQuantity","Issued Quantity"},
{"orderId","Order Id"},
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"carrierPartyId","Carrier Party Id"},
{"originFacilityName","Origin Facility Name"},
{"originCountryGeoId","Origin Country Geo Id"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"originPostalCode","Origin Postal Code"},
{"destDirections","Dest Directions"},
{"packageQuantity","Package Quantity"},
{"productId","Product Id"},
{"destAddress2","Dest Address 2"},
{"deliveryId","Delivery Id"},
{"destPostalCode","Dest Postal Code"},
{"itemIssuanceId","Item Issuance Id"},
{"carrierGroupName","Carrier Group Name"},
{"originCity","Origin City"},
};
protected void initObject(){
this.internalName = "";
this.boxNumber = "";
this.destToName = "";
this.destContactNumber = "";
this.destStateProvinceGeoId = "";
this.originContactMechId = "";
this.originCountryCode = "";
this.originAddress1 = "";
this.destAreaCode = "";
this.destTelecomNumberId = "";
this.originAttnName = "";
this.shipmentItemSeqId = "";
this.shipmentId = "";
this.originAddress2 = "";
this.originStateProvinceGeoId = "";
this.originContactNumber = "";
this.carrierLastName = "";
this.originToName = "";
this.actualStartDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.destAttnName = "";
this.destAddress1 = "";
this.weightUomDescription = "";
this.weightUomAbbreviation = "";
this.packageDateCreated = UtilDateTime.nowTimestamp();
this.actualCost = "";
this.destPostalCodeGeoId = "";
this.destCountryCode = "";
this.originDirections = "";
this.orderItemSeqId = "";
this.destCountryGeoId = "";
this.destContactMechId = "";
this.destFacilityId = "";
this.shipmentMethodTypeId = "";
this.issuedByUserLoginId = "";
this.destCity = "";
this.actualArrivalDate = UtilDateTime.nowTimestamp();
this.weight = "";
this.originFacilityId = "";
this.carrierFirstName = "";
this.shipmentContentDescription = "";
this.estimatedArrivalDate = UtilDateTime.nowTimestamp();
this.originAreaCode = "";
this.originTelecomNumberId = "";
this.trackingCode = "";
this.shipmentMethodDescription = "";
this.estimatedStartDate = UtilDateTime.nowTimestamp();
this.destFacilityName = "";
this.originPostalCodeGeoId = "";
this.issuedDateTime = UtilDateTime.nowTimestamp();
this.issuedQuantity = java.math.BigDecimal.ZERO;
this.orderId = "";
this.shipmentPackageSeqId = "";
this.carrierPartyId = "";
this.originFacilityName = "";
this.originCountryGeoId = "";
this.shipmentRouteSegmentId = "";
this.originPostalCode = "";
this.destDirections = "";
this.packageQuantity = java.math.BigDecimal.ZERO;
this.productId = "";
this.destAddress2 = "";
this.deliveryId = "";
this.destPostalCode = "";
this.itemIssuanceId = "";
this.carrierGroupName = "";
this.originCity = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.boxNumber = (java.lang.String) genVal.get("boxNumber");
this.destToName = (java.lang.String) genVal.get("destToName");
this.destContactNumber = (java.lang.String) genVal.get("destContactNumber");
this.destStateProvinceGeoId = (java.lang.String) genVal.get("destStateProvinceGeoId");
this.originContactMechId = (java.lang.String) genVal.get("originContactMechId");
this.originCountryCode = (java.lang.String) genVal.get("originCountryCode");
this.originAddress1 = (java.lang.String) genVal.get("originAddress1");
this.destAreaCode = (java.lang.String) genVal.get("destAreaCode");
this.destTelecomNumberId = (java.lang.String) genVal.get("destTelecomNumberId");
this.originAttnName = (java.lang.String) genVal.get("originAttnName");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.originAddress2 = (java.lang.String) genVal.get("originAddress2");
this.originStateProvinceGeoId = (java.lang.String) genVal.get("originStateProvinceGeoId");
this.originContactNumber = (java.lang.String) genVal.get("originContactNumber");
this.carrierLastName = (java.lang.String) genVal.get("carrierLastName");
this.originToName = (java.lang.String) genVal.get("originToName");
this.actualStartDate = (java.sql.Timestamp) genVal.get("actualStartDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.destAttnName = (java.lang.String) genVal.get("destAttnName");
this.destAddress1 = (java.lang.String) genVal.get("destAddress1");
this.weightUomDescription = (java.lang.String) genVal.get("weightUomDescription");
this.weightUomAbbreviation = (java.lang.String) genVal.get("weightUomAbbreviation");
this.packageDateCreated = (java.sql.Timestamp) genVal.get("packageDateCreated");
this.actualCost = (java.lang.String) genVal.get("actualCost");
this.destPostalCodeGeoId = (java.lang.String) genVal.get("destPostalCodeGeoId");
this.destCountryCode = (java.lang.String) genVal.get("destCountryCode");
this.originDirections = (java.lang.String) genVal.get("originDirections");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.destCountryGeoId = (java.lang.String) genVal.get("destCountryGeoId");
this.destContactMechId = (java.lang.String) genVal.get("destContactMechId");
this.destFacilityId = (java.lang.String) genVal.get("destFacilityId");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.issuedByUserLoginId = (java.lang.String) genVal.get("issuedByUserLoginId");
this.destCity = (java.lang.String) genVal.get("destCity");
this.actualArrivalDate = (java.sql.Timestamp) genVal.get("actualArrivalDate");
this.weight = (java.lang.String) genVal.get("weight");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.carrierFirstName = (java.lang.String) genVal.get("carrierFirstName");
this.shipmentContentDescription = (java.lang.String) genVal.get("shipmentContentDescription");
this.estimatedArrivalDate = (java.sql.Timestamp) genVal.get("estimatedArrivalDate");
this.originAreaCode = (java.lang.String) genVal.get("originAreaCode");
this.originTelecomNumberId = (java.lang.String) genVal.get("originTelecomNumberId");
this.trackingCode = (java.lang.String) genVal.get("trackingCode");
this.shipmentMethodDescription = (java.lang.String) genVal.get("shipmentMethodDescription");
this.estimatedStartDate = (java.sql.Timestamp) genVal.get("estimatedStartDate");
this.destFacilityName = (java.lang.String) genVal.get("destFacilityName");
this.originPostalCodeGeoId = (java.lang.String) genVal.get("originPostalCodeGeoId");
this.issuedDateTime = (java.sql.Timestamp) genVal.get("issuedDateTime");
this.issuedQuantity = (java.math.BigDecimal) genVal.get("issuedQuantity");
this.orderId = (java.lang.String) genVal.get("orderId");
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.originFacilityName = (java.lang.String) genVal.get("originFacilityName");
this.originCountryGeoId = (java.lang.String) genVal.get("originCountryGeoId");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.originPostalCode = (java.lang.String) genVal.get("originPostalCode");
this.destDirections = (java.lang.String) genVal.get("destDirections");
this.packageQuantity = (java.math.BigDecimal) genVal.get("packageQuantity");
this.productId = (java.lang.String) genVal.get("productId");
this.destAddress2 = (java.lang.String) genVal.get("destAddress2");
this.deliveryId = (java.lang.String) genVal.get("deliveryId");
this.destPostalCode = (java.lang.String) genVal.get("destPostalCode");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.carrierGroupName = (java.lang.String) genVal.get("carrierGroupName");
this.originCity = (java.lang.String) genVal.get("originCity");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("boxNumber",OrderMaxUtility.getValidEntityString(this.boxNumber));
val.set("destToName",OrderMaxUtility.getValidEntityString(this.destToName));
val.set("destContactNumber",OrderMaxUtility.getValidEntityString(this.destContactNumber));
val.set("destStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.destStateProvinceGeoId));
val.set("originContactMechId",OrderMaxUtility.getValidEntityString(this.originContactMechId));
val.set("originCountryCode",OrderMaxUtility.getValidEntityString(this.originCountryCode));
val.set("originAddress1",OrderMaxUtility.getValidEntityString(this.originAddress1));
val.set("destAreaCode",OrderMaxUtility.getValidEntityString(this.destAreaCode));
val.set("destTelecomNumberId",OrderMaxUtility.getValidEntityString(this.destTelecomNumberId));
val.set("originAttnName",OrderMaxUtility.getValidEntityString(this.originAttnName));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("originAddress2",OrderMaxUtility.getValidEntityString(this.originAddress2));
val.set("originStateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.originStateProvinceGeoId));
val.set("originContactNumber",OrderMaxUtility.getValidEntityString(this.originContactNumber));
val.set("carrierLastName",OrderMaxUtility.getValidEntityString(this.carrierLastName));
val.set("originToName",OrderMaxUtility.getValidEntityString(this.originToName));
val.set("actualStartDate",OrderMaxUtility.getValidEntityTimestamp(this.actualStartDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("destAttnName",OrderMaxUtility.getValidEntityString(this.destAttnName));
val.set("destAddress1",OrderMaxUtility.getValidEntityString(this.destAddress1));
val.set("weightUomDescription",OrderMaxUtility.getValidEntityString(this.weightUomDescription));
val.set("weightUomAbbreviation",OrderMaxUtility.getValidEntityString(this.weightUomAbbreviation));
val.set("packageDateCreated",OrderMaxUtility.getValidEntityTimestamp(this.packageDateCreated));
val.set("actualCost",OrderMaxUtility.getValidEntityString(this.actualCost));
val.set("destPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.destPostalCodeGeoId));
val.set("destCountryCode",OrderMaxUtility.getValidEntityString(this.destCountryCode));
val.set("originDirections",OrderMaxUtility.getValidEntityString(this.originDirections));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("destCountryGeoId",OrderMaxUtility.getValidEntityString(this.destCountryGeoId));
val.set("destContactMechId",OrderMaxUtility.getValidEntityString(this.destContactMechId));
val.set("destFacilityId",OrderMaxUtility.getValidEntityString(this.destFacilityId));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("issuedByUserLoginId",OrderMaxUtility.getValidEntityString(this.issuedByUserLoginId));
val.set("destCity",OrderMaxUtility.getValidEntityString(this.destCity));
val.set("actualArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.actualArrivalDate));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("carrierFirstName",OrderMaxUtility.getValidEntityString(this.carrierFirstName));
val.set("shipmentContentDescription",OrderMaxUtility.getValidEntityString(this.shipmentContentDescription));
val.set("estimatedArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedArrivalDate));
val.set("originAreaCode",OrderMaxUtility.getValidEntityString(this.originAreaCode));
val.set("originTelecomNumberId",OrderMaxUtility.getValidEntityString(this.originTelecomNumberId));
val.set("trackingCode",OrderMaxUtility.getValidEntityString(this.trackingCode));
val.set("shipmentMethodDescription",OrderMaxUtility.getValidEntityString(this.shipmentMethodDescription));
val.set("estimatedStartDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedStartDate));
val.set("destFacilityName",OrderMaxUtility.getValidEntityString(this.destFacilityName));
val.set("originPostalCodeGeoId",OrderMaxUtility.getValidEntityString(this.originPostalCodeGeoId));
val.set("issuedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.issuedDateTime));
val.set("issuedQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.issuedQuantity));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("originFacilityName",OrderMaxUtility.getValidEntityString(this.originFacilityName));
val.set("originCountryGeoId",OrderMaxUtility.getValidEntityString(this.originCountryGeoId));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("originPostalCode",OrderMaxUtility.getValidEntityString(this.originPostalCode));
val.set("destDirections",OrderMaxUtility.getValidEntityString(this.destDirections));
val.set("packageQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.packageQuantity));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("destAddress2",OrderMaxUtility.getValidEntityString(this.destAddress2));
val.set("deliveryId",OrderMaxUtility.getValidEntityString(this.deliveryId));
val.set("destPostalCode",OrderMaxUtility.getValidEntityString(this.destPostalCode));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("carrierGroupName",OrderMaxUtility.getValidEntityString(this.carrierGroupName));
val.set("originCity",OrderMaxUtility.getValidEntityString(this.originCity));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("boxNumber",this.boxNumber);
valueMap.put("destToName",this.destToName);
valueMap.put("destContactNumber",this.destContactNumber);
valueMap.put("destStateProvinceGeoId",this.destStateProvinceGeoId);
valueMap.put("originContactMechId",this.originContactMechId);
valueMap.put("originCountryCode",this.originCountryCode);
valueMap.put("originAddress1",this.originAddress1);
valueMap.put("destAreaCode",this.destAreaCode);
valueMap.put("destTelecomNumberId",this.destTelecomNumberId);
valueMap.put("originAttnName",this.originAttnName);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("originAddress2",this.originAddress2);
valueMap.put("originStateProvinceGeoId",this.originStateProvinceGeoId);
valueMap.put("originContactNumber",this.originContactNumber);
valueMap.put("carrierLastName",this.carrierLastName);
valueMap.put("originToName",this.originToName);
valueMap.put("actualStartDate",this.actualStartDate);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("destAttnName",this.destAttnName);
valueMap.put("destAddress1",this.destAddress1);
valueMap.put("weightUomDescription",this.weightUomDescription);
valueMap.put("weightUomAbbreviation",this.weightUomAbbreviation);
valueMap.put("packageDateCreated",this.packageDateCreated);
valueMap.put("actualCost",this.actualCost);
valueMap.put("destPostalCodeGeoId",this.destPostalCodeGeoId);
valueMap.put("destCountryCode",this.destCountryCode);
valueMap.put("originDirections",this.originDirections);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("destCountryGeoId",this.destCountryGeoId);
valueMap.put("destContactMechId",this.destContactMechId);
valueMap.put("destFacilityId",this.destFacilityId);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("issuedByUserLoginId",this.issuedByUserLoginId);
valueMap.put("destCity",this.destCity);
valueMap.put("actualArrivalDate",this.actualArrivalDate);
valueMap.put("weight",this.weight);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("carrierFirstName",this.carrierFirstName);
valueMap.put("shipmentContentDescription",this.shipmentContentDescription);
valueMap.put("estimatedArrivalDate",this.estimatedArrivalDate);
valueMap.put("originAreaCode",this.originAreaCode);
valueMap.put("originTelecomNumberId",this.originTelecomNumberId);
valueMap.put("trackingCode",this.trackingCode);
valueMap.put("shipmentMethodDescription",this.shipmentMethodDescription);
valueMap.put("estimatedStartDate",this.estimatedStartDate);
valueMap.put("destFacilityName",this.destFacilityName);
valueMap.put("originPostalCodeGeoId",this.originPostalCodeGeoId);
valueMap.put("issuedDateTime",this.issuedDateTime);
valueMap.put("issuedQuantity",this.issuedQuantity);
valueMap.put("orderId",this.orderId);
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("originFacilityName",this.originFacilityName);
valueMap.put("originCountryGeoId",this.originCountryGeoId);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("originPostalCode",this.originPostalCode);
valueMap.put("destDirections",this.destDirections);
valueMap.put("packageQuantity",this.packageQuantity);
valueMap.put("productId",this.productId);
valueMap.put("destAddress2",this.destAddress2);
valueMap.put("deliveryId",this.deliveryId);
valueMap.put("destPostalCode",this.destPostalCode);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("carrierGroupName",this.carrierGroupName);
valueMap.put("originCity",this.originCity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentManifestView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String internalName;
public java.lang.String getinternalName() {
return internalName;
}
public void setinternalName( java.lang.String internalName ) {
this.internalName = internalName;
}
private java.lang.String boxNumber;
public java.lang.String getboxNumber() {
return boxNumber;
}
public void setboxNumber( java.lang.String boxNumber ) {
this.boxNumber = boxNumber;
}
private java.lang.String destToName;
public java.lang.String getdestToName() {
return destToName;
}
public void setdestToName( java.lang.String destToName ) {
this.destToName = destToName;
}
private java.lang.String destContactNumber;
public java.lang.String getdestContactNumber() {
return destContactNumber;
}
public void setdestContactNumber( java.lang.String destContactNumber ) {
this.destContactNumber = destContactNumber;
}
private java.lang.String destStateProvinceGeoId;
public java.lang.String getdestStateProvinceGeoId() {
return destStateProvinceGeoId;
}
public void setdestStateProvinceGeoId( java.lang.String destStateProvinceGeoId ) {
this.destStateProvinceGeoId = destStateProvinceGeoId;
}
private java.lang.String originContactMechId;
public java.lang.String getoriginContactMechId() {
return originContactMechId;
}
public void setoriginContactMechId( java.lang.String originContactMechId ) {
this.originContactMechId = originContactMechId;
}
private java.lang.String originCountryCode;
public java.lang.String getoriginCountryCode() {
return originCountryCode;
}
public void setoriginCountryCode( java.lang.String originCountryCode ) {
this.originCountryCode = originCountryCode;
}
private java.lang.String originAddress1;
public java.lang.String getoriginAddress1() {
return originAddress1;
}
public void setoriginAddress1( java.lang.String originAddress1 ) {
this.originAddress1 = originAddress1;
}
private java.lang.String destAreaCode;
public java.lang.String getdestAreaCode() {
return destAreaCode;
}
public void setdestAreaCode( java.lang.String destAreaCode ) {
this.destAreaCode = destAreaCode;
}
private java.lang.String destTelecomNumberId;
public java.lang.String getdestTelecomNumberId() {
return destTelecomNumberId;
}
public void setdestTelecomNumberId( java.lang.String destTelecomNumberId ) {
this.destTelecomNumberId = destTelecomNumberId;
}
private java.lang.String originAttnName;
public java.lang.String getoriginAttnName() {
return originAttnName;
}
public void setoriginAttnName( java.lang.String originAttnName ) {
this.originAttnName = originAttnName;
}
private java.lang.String shipmentItemSeqId;
public java.lang.String getshipmentItemSeqId() {
return shipmentItemSeqId;
}
public void setshipmentItemSeqId( java.lang.String shipmentItemSeqId ) {
this.shipmentItemSeqId = shipmentItemSeqId;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.lang.String originAddress2;
public java.lang.String getoriginAddress2() {
return originAddress2;
}
public void setoriginAddress2( java.lang.String originAddress2 ) {
this.originAddress2 = originAddress2;
}
private java.lang.String originStateProvinceGeoId;
public java.lang.String getoriginStateProvinceGeoId() {
return originStateProvinceGeoId;
}
public void setoriginStateProvinceGeoId( java.lang.String originStateProvinceGeoId ) {
this.originStateProvinceGeoId = originStateProvinceGeoId;
}
private java.lang.String originContactNumber;
public java.lang.String getoriginContactNumber() {
return originContactNumber;
}
public void setoriginContactNumber( java.lang.String originContactNumber ) {
this.originContactNumber = originContactNumber;
}
private java.lang.String carrierLastName;
public java.lang.String getcarrierLastName() {
return carrierLastName;
}
public void setcarrierLastName( java.lang.String carrierLastName ) {
this.carrierLastName = carrierLastName;
}
private java.lang.String originToName;
public java.lang.String getoriginToName() {
return originToName;
}
public void setoriginToName( java.lang.String originToName ) {
this.originToName = originToName;
}
private java.sql.Timestamp actualStartDate;
public java.sql.Timestamp getactualStartDate() {
return actualStartDate;
}
public void setactualStartDate( java.sql.Timestamp actualStartDate ) {
this.actualStartDate = actualStartDate;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.lang.String destAttnName;
public java.lang.String getdestAttnName() {
return destAttnName;
}
public void setdestAttnName( java.lang.String destAttnName ) {
this.destAttnName = destAttnName;
}
private java.lang.String destAddress1;
public java.lang.String getdestAddress1() {
return destAddress1;
}
public void setdestAddress1( java.lang.String destAddress1 ) {
this.destAddress1 = destAddress1;
}
private java.lang.String weightUomDescription;
public java.lang.String getweightUomDescription() {
return weightUomDescription;
}
public void setweightUomDescription( java.lang.String weightUomDescription ) {
this.weightUomDescription = weightUomDescription;
}
private java.lang.String weightUomAbbreviation;
public java.lang.String getweightUomAbbreviation() {
return weightUomAbbreviation;
}
public void setweightUomAbbreviation( java.lang.String weightUomAbbreviation ) {
this.weightUomAbbreviation = weightUomAbbreviation;
}
private java.sql.Timestamp packageDateCreated;
public java.sql.Timestamp getpackageDateCreated() {
return packageDateCreated;
}
public void setpackageDateCreated( java.sql.Timestamp packageDateCreated ) {
this.packageDateCreated = packageDateCreated;
}
private java.lang.String actualCost;
public java.lang.String getactualCost() {
return actualCost;
}
public void setactualCost( java.lang.String actualCost ) {
this.actualCost = actualCost;
}
private java.lang.String destPostalCodeGeoId;
public java.lang.String getdestPostalCodeGeoId() {
return destPostalCodeGeoId;
}
public void setdestPostalCodeGeoId( java.lang.String destPostalCodeGeoId ) {
this.destPostalCodeGeoId = destPostalCodeGeoId;
}
private java.lang.String destCountryCode;
public java.lang.String getdestCountryCode() {
return destCountryCode;
}
public void setdestCountryCode( java.lang.String destCountryCode ) {
this.destCountryCode = destCountryCode;
}
private java.lang.String originDirections;
public java.lang.String getoriginDirections() {
return originDirections;
}
public void setoriginDirections( java.lang.String originDirections ) {
this.originDirections = originDirections;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String destCountryGeoId;
public java.lang.String getdestCountryGeoId() {
return destCountryGeoId;
}
public void setdestCountryGeoId( java.lang.String destCountryGeoId ) {
this.destCountryGeoId = destCountryGeoId;
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
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String issuedByUserLoginId;
public java.lang.String getissuedByUserLoginId() {
return issuedByUserLoginId;
}
public void setissuedByUserLoginId( java.lang.String issuedByUserLoginId ) {
this.issuedByUserLoginId = issuedByUserLoginId;
}
private java.lang.String destCity;
public java.lang.String getdestCity() {
return destCity;
}
public void setdestCity( java.lang.String destCity ) {
this.destCity = destCity;
}
private java.sql.Timestamp actualArrivalDate;
public java.sql.Timestamp getactualArrivalDate() {
return actualArrivalDate;
}
public void setactualArrivalDate( java.sql.Timestamp actualArrivalDate ) {
this.actualArrivalDate = actualArrivalDate;
}
private java.lang.String weight;
public java.lang.String getweight() {
return weight;
}
public void setweight( java.lang.String weight ) {
this.weight = weight;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String carrierFirstName;
public java.lang.String getcarrierFirstName() {
return carrierFirstName;
}
public void setcarrierFirstName( java.lang.String carrierFirstName ) {
this.carrierFirstName = carrierFirstName;
}
private java.lang.String shipmentContentDescription;
public java.lang.String getshipmentContentDescription() {
return shipmentContentDescription;
}
public void setshipmentContentDescription( java.lang.String shipmentContentDescription ) {
this.shipmentContentDescription = shipmentContentDescription;
}
private java.sql.Timestamp estimatedArrivalDate;
public java.sql.Timestamp getestimatedArrivalDate() {
return estimatedArrivalDate;
}
public void setestimatedArrivalDate( java.sql.Timestamp estimatedArrivalDate ) {
this.estimatedArrivalDate = estimatedArrivalDate;
}
private java.lang.String originAreaCode;
public java.lang.String getoriginAreaCode() {
return originAreaCode;
}
public void setoriginAreaCode( java.lang.String originAreaCode ) {
this.originAreaCode = originAreaCode;
}
private java.lang.String originTelecomNumberId;
public java.lang.String getoriginTelecomNumberId() {
return originTelecomNumberId;
}
public void setoriginTelecomNumberId( java.lang.String originTelecomNumberId ) {
this.originTelecomNumberId = originTelecomNumberId;
}
private java.lang.String trackingCode;
public java.lang.String gettrackingCode() {
return trackingCode;
}
public void settrackingCode( java.lang.String trackingCode ) {
this.trackingCode = trackingCode;
}
private java.lang.String shipmentMethodDescription;
public java.lang.String getshipmentMethodDescription() {
return shipmentMethodDescription;
}
public void setshipmentMethodDescription( java.lang.String shipmentMethodDescription ) {
this.shipmentMethodDescription = shipmentMethodDescription;
}
private java.sql.Timestamp estimatedStartDate;
public java.sql.Timestamp getestimatedStartDate() {
return estimatedStartDate;
}
public void setestimatedStartDate( java.sql.Timestamp estimatedStartDate ) {
this.estimatedStartDate = estimatedStartDate;
}
private java.lang.String destFacilityName;
public java.lang.String getdestFacilityName() {
return destFacilityName;
}
public void setdestFacilityName( java.lang.String destFacilityName ) {
this.destFacilityName = destFacilityName;
}
private java.lang.String originPostalCodeGeoId;
public java.lang.String getoriginPostalCodeGeoId() {
return originPostalCodeGeoId;
}
public void setoriginPostalCodeGeoId( java.lang.String originPostalCodeGeoId ) {
this.originPostalCodeGeoId = originPostalCodeGeoId;
}
private java.sql.Timestamp issuedDateTime;
public java.sql.Timestamp getissuedDateTime() {
return issuedDateTime;
}
public void setissuedDateTime( java.sql.Timestamp issuedDateTime ) {
this.issuedDateTime = issuedDateTime;
}
private java.math.BigDecimal issuedQuantity;
public java.math.BigDecimal getissuedQuantity() {
return issuedQuantity;
}
public void setissuedQuantity( java.math.BigDecimal issuedQuantity ) {
this.issuedQuantity = issuedQuantity;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String originFacilityName;
public java.lang.String getoriginFacilityName() {
return originFacilityName;
}
public void setoriginFacilityName( java.lang.String originFacilityName ) {
this.originFacilityName = originFacilityName;
}
private java.lang.String originCountryGeoId;
public java.lang.String getoriginCountryGeoId() {
return originCountryGeoId;
}
public void setoriginCountryGeoId( java.lang.String originCountryGeoId ) {
this.originCountryGeoId = originCountryGeoId;
}
private java.lang.String shipmentRouteSegmentId;
public java.lang.String getshipmentRouteSegmentId() {
return shipmentRouteSegmentId;
}
public void setshipmentRouteSegmentId( java.lang.String shipmentRouteSegmentId ) {
this.shipmentRouteSegmentId = shipmentRouteSegmentId;
}
private java.lang.String originPostalCode;
public java.lang.String getoriginPostalCode() {
return originPostalCode;
}
public void setoriginPostalCode( java.lang.String originPostalCode ) {
this.originPostalCode = originPostalCode;
}
private java.lang.String destDirections;
public java.lang.String getdestDirections() {
return destDirections;
}
public void setdestDirections( java.lang.String destDirections ) {
this.destDirections = destDirections;
}
private java.math.BigDecimal packageQuantity;
public java.math.BigDecimal getpackageQuantity() {
return packageQuantity;
}
public void setpackageQuantity( java.math.BigDecimal packageQuantity ) {
this.packageQuantity = packageQuantity;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String destAddress2;
public java.lang.String getdestAddress2() {
return destAddress2;
}
public void setdestAddress2( java.lang.String destAddress2 ) {
this.destAddress2 = destAddress2;
}
private java.lang.String deliveryId;
public java.lang.String getdeliveryId() {
return deliveryId;
}
public void setdeliveryId( java.lang.String deliveryId ) {
this.deliveryId = deliveryId;
}
private java.lang.String destPostalCode;
public java.lang.String getdestPostalCode() {
return destPostalCode;
}
public void setdestPostalCode( java.lang.String destPostalCode ) {
this.destPostalCode = destPostalCode;
}
private java.lang.String itemIssuanceId;
public java.lang.String getitemIssuanceId() {
return itemIssuanceId;
}
public void setitemIssuanceId( java.lang.String itemIssuanceId ) {
this.itemIssuanceId = itemIssuanceId;
}
private java.lang.String carrierGroupName;
public java.lang.String getcarrierGroupName() {
return carrierGroupName;
}
public void setcarrierGroupName( java.lang.String carrierGroupName ) {
this.carrierGroupName = carrierGroupName;
}
private java.lang.String originCity;
public java.lang.String getoriginCity() {
return originCity;
}
public void setoriginCity( java.lang.String originCity ) {
this.originCity = originCity;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentManifestView> objectList = new ArrayList<ShipmentManifestView>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentManifestView(genVal));
        }
        return objectList;
    }    
}
