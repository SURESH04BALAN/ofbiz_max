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
public class OrderHeaderAndShipGroupsByProduct implements GenericValueObjectInterface {
public static final String module =OrderHeaderAndShipGroupsByProduct.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderAndShipGroupsByProduct(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderAndShipGroupsByProduct () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"productStoreId","Product Store Id"},
{"needsInventoryIssuance","Needs Inventory Issuance"},
{"pickSheetPrintedDate","Pick Sheet Printed Date"},
{"orderName","Order Name"},
{"stateProvinceGeoId","State Province Geo Id"},
{"address1","Address 1"},
{"address2","Address 2"},
{"brandName","Brand Name"},
{"city","City"},
{"directions","Directions"},
{"terminalId","Terminal Id"},
{"vendorPartyId","Vendor Party Id"},
{"postalCode","Postal Code"},
{"remainingSubTotal","Remaining Sub Total"},
{"orderTypeId","Order Type Id"},
{"priority","Priority"},
{"postalCodeGeoId","Postal Code Geo Id"},
{"billingAccountId","Billing Account Id"},
{"orderDate","Order Date"},
{"shipByDate","Ship By Date"},
{"grandTotal","Grand Total"},
{"carrierRoleTypeId","Carrier Role Type Id"},
{"telecomContactMechId","Telecom Contact Mech Id"},
{"internalCode","Internal Code"},
{"entryDate","Entry Date"},
{"facilityId","Facility Id"},
{"isGift","Is Gift"},
{"createdBy","Created By"},
{"trackingNumber","Tracking Number"},
{"partyId","Party Id"},
{"countyGeoId","County Geo Id"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"webSiteId","Web Site Id"},
{"geoPointId","Geo Point Id"},
{"supplierPartyId","Supplier Party Id"},
{"originFacilityId","Origin Facility Id"},
{"transactionId","Transaction Id"},
{"toName","To Name"},
{"invoicePerShipment","Invoice Per Shipment"},
{"currencyUom","Currency Uom"},
{"visitId","Visit Id"},
{"externalId","External Id"},
{"statusId","Status Id"},
{"countryGeoId","Country Geo Id"},
{"isViewed","Is Viewed"},
{"shipAfterDate","Ship After Date"},
{"orderId","Order Id"},
{"carrierPartyId","Carrier Party Id"},
{"giftMessage","Gift Message"},
{"postalCodeExt","Postal Code Ext"},
{"contactMechId","Contact Mech Id"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"shippingInstructions","Shipping Instructions"},
{"productId","Product Id"},
{"maySplit","May Split"},
{"syncStatusId","Sync Status Id"},
{"roleTypeId","Role Type Id"},
{"attnName","Attn Name"},
{"autoOrderShoppingListId","Auto Order Shopping List Id"},
{"isRushOrder","Is Rush Order"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"firstAttemptOrderId","First Attempt Order Id"},
{"estimatedShipDate","Estimated Ship Date"},
{"salesChannelEnumId","Sales Channel Enum Id"},
};
protected void initObject(){
this.internalName = "";
this.productStoreId = "";
this.needsInventoryIssuance = "";
this.pickSheetPrintedDate = UtilDateTime.nowTimestamp();
this.orderName = "";
this.stateProvinceGeoId = "";
this.address1 = "";
this.address2 = "";
this.brandName = "";
this.city = "";
this.directions = "";
this.terminalId = "";
this.vendorPartyId = "";
this.postalCode = "";
this.remainingSubTotal = java.math.BigDecimal.ZERO;
this.orderTypeId = "";
this.priority = "";
this.postalCodeGeoId = "";
this.billingAccountId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.shipByDate = UtilDateTime.nowTimestamp();
this.grandTotal = java.math.BigDecimal.ZERO;
this.carrierRoleTypeId = "";
this.telecomContactMechId = "";
this.internalCode = "";
this.entryDate = UtilDateTime.nowTimestamp();
this.facilityId = "";
this.isGift = "";
this.createdBy = "";
this.trackingNumber = "";
this.partyId = "";
this.countyGeoId = "";
this.shipmentMethodTypeId = "";
this.webSiteId = "";
this.geoPointId = "";
this.supplierPartyId = "";
this.originFacilityId = "";
this.transactionId = "";
this.toName = "";
this.invoicePerShipment = "";
this.currencyUom = "";
this.visitId = "";
this.externalId = "";
this.statusId = "";
this.countryGeoId = "";
this.isViewed = "";
this.shipAfterDate = UtilDateTime.nowTimestamp();
this.orderId = "";
this.carrierPartyId = "";
this.giftMessage = "";
this.postalCodeExt = "";
this.contactMechId = "";
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.shippingInstructions = "";
this.productId = "";
this.maySplit = "";
this.syncStatusId = "";
this.roleTypeId = "";
this.attnName = "";
this.autoOrderShoppingListId = "";
this.isRushOrder = "";
this.shipGroupSeqId = "";
this.firstAttemptOrderId = "";
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.salesChannelEnumId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.needsInventoryIssuance = (java.lang.String) genVal.get("needsInventoryIssuance");
this.pickSheetPrintedDate = (java.sql.Timestamp) genVal.get("pickSheetPrintedDate");
this.orderName = (java.lang.String) genVal.get("orderName");
this.stateProvinceGeoId = (java.lang.String) genVal.get("stateProvinceGeoId");
this.address1 = (java.lang.String) genVal.get("address1");
this.address2 = (java.lang.String) genVal.get("address2");
this.brandName = (java.lang.String) genVal.get("brandName");
this.city = (java.lang.String) genVal.get("city");
this.directions = (java.lang.String) genVal.get("directions");
this.terminalId = (java.lang.String) genVal.get("terminalId");
this.vendorPartyId = (java.lang.String) genVal.get("vendorPartyId");
this.postalCode = (java.lang.String) genVal.get("postalCode");
this.remainingSubTotal = (java.math.BigDecimal) genVal.get("remainingSubTotal");
this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
this.priority = (java.lang.String) genVal.get("priority");
this.postalCodeGeoId = (java.lang.String) genVal.get("postalCodeGeoId");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.shipByDate = (java.sql.Timestamp) genVal.get("shipByDate");
this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
this.carrierRoleTypeId = (java.lang.String) genVal.get("carrierRoleTypeId");
this.telecomContactMechId = (java.lang.String) genVal.get("telecomContactMechId");
this.internalCode = (java.lang.String) genVal.get("internalCode");
this.entryDate = (java.sql.Timestamp) genVal.get("entryDate");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.isGift = (java.lang.String) genVal.get("isGift");
this.createdBy = (java.lang.String) genVal.get("createdBy");
this.trackingNumber = (java.lang.String) genVal.get("trackingNumber");
this.partyId = (java.lang.String) genVal.get("partyId");
this.countyGeoId = (java.lang.String) genVal.get("countyGeoId");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.geoPointId = (java.lang.String) genVal.get("geoPointId");
this.supplierPartyId = (java.lang.String) genVal.get("supplierPartyId");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.transactionId = (java.lang.String) genVal.get("transactionId");
this.toName = (java.lang.String) genVal.get("toName");
this.invoicePerShipment = (java.lang.String) genVal.get("invoicePerShipment");
this.currencyUom = (java.lang.String) genVal.get("currencyUom");
this.visitId = (java.lang.String) genVal.get("visitId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.countryGeoId = (java.lang.String) genVal.get("countryGeoId");
this.isViewed = (java.lang.String) genVal.get("isViewed");
this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
this.orderId = (java.lang.String) genVal.get("orderId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.giftMessage = (java.lang.String) genVal.get("giftMessage");
this.postalCodeExt = (java.lang.String) genVal.get("postalCodeExt");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.shippingInstructions = (java.lang.String) genVal.get("shippingInstructions");
this.productId = (java.lang.String) genVal.get("productId");
this.maySplit = (java.lang.String) genVal.get("maySplit");
this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.attnName = (java.lang.String) genVal.get("attnName");
this.autoOrderShoppingListId = (java.lang.String) genVal.get("autoOrderShoppingListId");
this.isRushOrder = (java.lang.String) genVal.get("isRushOrder");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.firstAttemptOrderId = (java.lang.String) genVal.get("firstAttemptOrderId");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.salesChannelEnumId = (java.lang.String) genVal.get("salesChannelEnumId");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("needsInventoryIssuance",OrderMaxUtility.getValidEntityString(this.needsInventoryIssuance));
val.set("pickSheetPrintedDate",OrderMaxUtility.getValidEntityTimestamp(this.pickSheetPrintedDate));
val.set("orderName",OrderMaxUtility.getValidEntityString(this.orderName));
val.set("stateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.stateProvinceGeoId));
val.set("address1",OrderMaxUtility.getValidEntityString(this.address1));
val.set("address2",OrderMaxUtility.getValidEntityString(this.address2));
val.set("brandName",OrderMaxUtility.getValidEntityString(this.brandName));
val.set("city",OrderMaxUtility.getValidEntityString(this.city));
val.set("directions",OrderMaxUtility.getValidEntityString(this.directions));
val.set("terminalId",OrderMaxUtility.getValidEntityString(this.terminalId));
val.set("vendorPartyId",OrderMaxUtility.getValidEntityString(this.vendorPartyId));
val.set("postalCode",OrderMaxUtility.getValidEntityString(this.postalCode));
val.set("remainingSubTotal",OrderMaxUtility.getValidEntityBigDecimal(this.remainingSubTotal));
val.set("orderTypeId",OrderMaxUtility.getValidEntityString(this.orderTypeId));
val.set("priority",OrderMaxUtility.getValidEntityString(this.priority));
val.set("postalCodeGeoId",OrderMaxUtility.getValidEntityString(this.postalCodeGeoId));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("shipByDate",OrderMaxUtility.getValidEntityTimestamp(this.shipByDate));
val.set("grandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
val.set("carrierRoleTypeId",OrderMaxUtility.getValidEntityString(this.carrierRoleTypeId));
val.set("telecomContactMechId",OrderMaxUtility.getValidEntityString(this.telecomContactMechId));
val.set("internalCode",OrderMaxUtility.getValidEntityString(this.internalCode));
val.set("entryDate",OrderMaxUtility.getValidEntityTimestamp(this.entryDate));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("isGift",OrderMaxUtility.getValidEntityString(this.isGift));
val.set("createdBy",OrderMaxUtility.getValidEntityString(this.createdBy));
val.set("trackingNumber",OrderMaxUtility.getValidEntityString(this.trackingNumber));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("countyGeoId",OrderMaxUtility.getValidEntityString(this.countyGeoId));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("geoPointId",OrderMaxUtility.getValidEntityString(this.geoPointId));
val.set("supplierPartyId",OrderMaxUtility.getValidEntityString(this.supplierPartyId));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("transactionId",OrderMaxUtility.getValidEntityString(this.transactionId));
val.set("toName",OrderMaxUtility.getValidEntityString(this.toName));
val.set("invoicePerShipment",OrderMaxUtility.getValidEntityString(this.invoicePerShipment));
val.set("currencyUom",OrderMaxUtility.getValidEntityString(this.currencyUom));
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("countryGeoId",OrderMaxUtility.getValidEntityString(this.countryGeoId));
val.set("isViewed",OrderMaxUtility.getValidEntityString(this.isViewed));
val.set("shipAfterDate",OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("giftMessage",OrderMaxUtility.getValidEntityString(this.giftMessage));
val.set("postalCodeExt",OrderMaxUtility.getValidEntityString(this.postalCodeExt));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("shippingInstructions",OrderMaxUtility.getValidEntityString(this.shippingInstructions));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("maySplit",OrderMaxUtility.getValidEntityString(this.maySplit));
val.set("syncStatusId",OrderMaxUtility.getValidEntityString(this.syncStatusId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("attnName",OrderMaxUtility.getValidEntityString(this.attnName));
val.set("autoOrderShoppingListId",OrderMaxUtility.getValidEntityString(this.autoOrderShoppingListId));
val.set("isRushOrder",OrderMaxUtility.getValidEntityString(this.isRushOrder));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("firstAttemptOrderId",OrderMaxUtility.getValidEntityString(this.firstAttemptOrderId));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("salesChannelEnumId",OrderMaxUtility.getValidEntityString(this.salesChannelEnumId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("needsInventoryIssuance",this.needsInventoryIssuance);
valueMap.put("pickSheetPrintedDate",this.pickSheetPrintedDate);
valueMap.put("orderName",this.orderName);
valueMap.put("stateProvinceGeoId",this.stateProvinceGeoId);
valueMap.put("address1",this.address1);
valueMap.put("address2",this.address2);
valueMap.put("brandName",this.brandName);
valueMap.put("city",this.city);
valueMap.put("directions",this.directions);
valueMap.put("terminalId",this.terminalId);
valueMap.put("vendorPartyId",this.vendorPartyId);
valueMap.put("postalCode",this.postalCode);
valueMap.put("remainingSubTotal",this.remainingSubTotal);
valueMap.put("orderTypeId",this.orderTypeId);
valueMap.put("priority",this.priority);
valueMap.put("postalCodeGeoId",this.postalCodeGeoId);
valueMap.put("billingAccountId",this.billingAccountId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("shipByDate",this.shipByDate);
valueMap.put("grandTotal",this.grandTotal);
valueMap.put("carrierRoleTypeId",this.carrierRoleTypeId);
valueMap.put("telecomContactMechId",this.telecomContactMechId);
valueMap.put("internalCode",this.internalCode);
valueMap.put("entryDate",this.entryDate);
valueMap.put("facilityId",this.facilityId);
valueMap.put("isGift",this.isGift);
valueMap.put("createdBy",this.createdBy);
valueMap.put("trackingNumber",this.trackingNumber);
valueMap.put("partyId",this.partyId);
valueMap.put("countyGeoId",this.countyGeoId);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("geoPointId",this.geoPointId);
valueMap.put("supplierPartyId",this.supplierPartyId);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("transactionId",this.transactionId);
valueMap.put("toName",this.toName);
valueMap.put("invoicePerShipment",this.invoicePerShipment);
valueMap.put("currencyUom",this.currencyUom);
valueMap.put("visitId",this.visitId);
valueMap.put("externalId",this.externalId);
valueMap.put("statusId",this.statusId);
valueMap.put("countryGeoId",this.countryGeoId);
valueMap.put("isViewed",this.isViewed);
valueMap.put("shipAfterDate",this.shipAfterDate);
valueMap.put("orderId",this.orderId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("giftMessage",this.giftMessage);
valueMap.put("postalCodeExt",this.postalCodeExt);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("shippingInstructions",this.shippingInstructions);
valueMap.put("productId",this.productId);
valueMap.put("maySplit",this.maySplit);
valueMap.put("syncStatusId",this.syncStatusId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("attnName",this.attnName);
valueMap.put("autoOrderShoppingListId",this.autoOrderShoppingListId);
valueMap.put("isRushOrder",this.isRushOrder);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("firstAttemptOrderId",this.firstAttemptOrderId);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("salesChannelEnumId",this.salesChannelEnumId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderAndShipGroupsByProduct");
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
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String needsInventoryIssuance;
public java.lang.String getneedsInventoryIssuance() {
return needsInventoryIssuance;
}
public void setneedsInventoryIssuance( java.lang.String needsInventoryIssuance ) {
this.needsInventoryIssuance = needsInventoryIssuance;
}
private java.sql.Timestamp pickSheetPrintedDate;
public java.sql.Timestamp getpickSheetPrintedDate() {
return pickSheetPrintedDate;
}
public void setpickSheetPrintedDate( java.sql.Timestamp pickSheetPrintedDate ) {
this.pickSheetPrintedDate = pickSheetPrintedDate;
}
private java.lang.String orderName;
public java.lang.String getorderName() {
return orderName;
}
public void setorderName( java.lang.String orderName ) {
this.orderName = orderName;
}
private java.lang.String stateProvinceGeoId;
public java.lang.String getstateProvinceGeoId() {
return stateProvinceGeoId;
}
public void setstateProvinceGeoId( java.lang.String stateProvinceGeoId ) {
this.stateProvinceGeoId = stateProvinceGeoId;
}
private java.lang.String address1;
public java.lang.String getaddress1() {
return address1;
}
public void setaddress1( java.lang.String address1 ) {
this.address1 = address1;
}
private java.lang.String address2;
public java.lang.String getaddress2() {
return address2;
}
public void setaddress2( java.lang.String address2 ) {
this.address2 = address2;
}
private java.lang.String brandName;
public java.lang.String getbrandName() {
return brandName;
}
public void setbrandName( java.lang.String brandName ) {
this.brandName = brandName;
}
private java.lang.String city;
public java.lang.String getcity() {
return city;
}
public void setcity( java.lang.String city ) {
this.city = city;
}
private java.lang.String directions;
public java.lang.String getdirections() {
return directions;
}
public void setdirections( java.lang.String directions ) {
this.directions = directions;
}
private java.lang.String terminalId;
public java.lang.String getterminalId() {
return terminalId;
}
public void setterminalId( java.lang.String terminalId ) {
this.terminalId = terminalId;
}
private java.lang.String vendorPartyId;
public java.lang.String getvendorPartyId() {
return vendorPartyId;
}
public void setvendorPartyId( java.lang.String vendorPartyId ) {
this.vendorPartyId = vendorPartyId;
}
private java.lang.String postalCode;
public java.lang.String getpostalCode() {
return postalCode;
}
public void setpostalCode( java.lang.String postalCode ) {
this.postalCode = postalCode;
}
private java.math.BigDecimal remainingSubTotal;
public java.math.BigDecimal getremainingSubTotal() {
return remainingSubTotal;
}
public void setremainingSubTotal( java.math.BigDecimal remainingSubTotal ) {
this.remainingSubTotal = remainingSubTotal;
}
private java.lang.String orderTypeId;
public java.lang.String getorderTypeId() {
return orderTypeId;
}
public void setorderTypeId( java.lang.String orderTypeId ) {
this.orderTypeId = orderTypeId;
}
private java.lang.String priority;
public java.lang.String getpriority() {
return priority;
}
public void setpriority( java.lang.String priority ) {
this.priority = priority;
}
private java.lang.String postalCodeGeoId;
public java.lang.String getpostalCodeGeoId() {
return postalCodeGeoId;
}
public void setpostalCodeGeoId( java.lang.String postalCodeGeoId ) {
this.postalCodeGeoId = postalCodeGeoId;
}
private java.lang.String billingAccountId;
public java.lang.String getbillingAccountId() {
return billingAccountId;
}
public void setbillingAccountId( java.lang.String billingAccountId ) {
this.billingAccountId = billingAccountId;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.sql.Timestamp shipByDate;
public java.sql.Timestamp getshipByDate() {
return shipByDate;
}
public void setshipByDate( java.sql.Timestamp shipByDate ) {
this.shipByDate = shipByDate;
}
private java.math.BigDecimal grandTotal;
public java.math.BigDecimal getgrandTotal() {
return grandTotal;
}
public void setgrandTotal( java.math.BigDecimal grandTotal ) {
this.grandTotal = grandTotal;
}
private java.lang.String carrierRoleTypeId;
public java.lang.String getcarrierRoleTypeId() {
return carrierRoleTypeId;
}
public void setcarrierRoleTypeId( java.lang.String carrierRoleTypeId ) {
this.carrierRoleTypeId = carrierRoleTypeId;
}
private java.lang.String telecomContactMechId;
public java.lang.String gettelecomContactMechId() {
return telecomContactMechId;
}
public void settelecomContactMechId( java.lang.String telecomContactMechId ) {
this.telecomContactMechId = telecomContactMechId;
}
private java.lang.String internalCode;
public java.lang.String getinternalCode() {
return internalCode;
}
public void setinternalCode( java.lang.String internalCode ) {
this.internalCode = internalCode;
}
private java.sql.Timestamp entryDate;
public java.sql.Timestamp getentryDate() {
return entryDate;
}
public void setentryDate( java.sql.Timestamp entryDate ) {
this.entryDate = entryDate;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String isGift;
public java.lang.String getisGift() {
return isGift;
}
public void setisGift( java.lang.String isGift ) {
this.isGift = isGift;
}
private java.lang.String createdBy;
public java.lang.String getcreatedBy() {
return createdBy;
}
public void setcreatedBy( java.lang.String createdBy ) {
this.createdBy = createdBy;
}
private java.lang.String trackingNumber;
public java.lang.String gettrackingNumber() {
return trackingNumber;
}
public void settrackingNumber( java.lang.String trackingNumber ) {
this.trackingNumber = trackingNumber;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String countyGeoId;
public java.lang.String getcountyGeoId() {
return countyGeoId;
}
public void setcountyGeoId( java.lang.String countyGeoId ) {
this.countyGeoId = countyGeoId;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
}
private java.lang.String geoPointId;
public java.lang.String getgeoPointId() {
return geoPointId;
}
public void setgeoPointId( java.lang.String geoPointId ) {
this.geoPointId = geoPointId;
}
private java.lang.String supplierPartyId;
public java.lang.String getsupplierPartyId() {
return supplierPartyId;
}
public void setsupplierPartyId( java.lang.String supplierPartyId ) {
this.supplierPartyId = supplierPartyId;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String transactionId;
public java.lang.String gettransactionId() {
return transactionId;
}
public void settransactionId( java.lang.String transactionId ) {
this.transactionId = transactionId;
}
private java.lang.String toName;
public java.lang.String gettoName() {
return toName;
}
public void settoName( java.lang.String toName ) {
this.toName = toName;
}
private java.lang.String invoicePerShipment;
public java.lang.String getinvoicePerShipment() {
return invoicePerShipment;
}
public void setinvoicePerShipment( java.lang.String invoicePerShipment ) {
this.invoicePerShipment = invoicePerShipment;
}
private java.lang.String currencyUom;
public java.lang.String getcurrencyUom() {
return currencyUom;
}
public void setcurrencyUom( java.lang.String currencyUom ) {
this.currencyUom = currencyUom;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String countryGeoId;
public java.lang.String getcountryGeoId() {
return countryGeoId;
}
public void setcountryGeoId( java.lang.String countryGeoId ) {
this.countryGeoId = countryGeoId;
}
private java.lang.String isViewed;
public java.lang.String getisViewed() {
return isViewed;
}
public void setisViewed( java.lang.String isViewed ) {
this.isViewed = isViewed;
}
private java.sql.Timestamp shipAfterDate;
public java.sql.Timestamp getshipAfterDate() {
return shipAfterDate;
}
public void setshipAfterDate( java.sql.Timestamp shipAfterDate ) {
this.shipAfterDate = shipAfterDate;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String giftMessage;
public java.lang.String getgiftMessage() {
return giftMessage;
}
public void setgiftMessage( java.lang.String giftMessage ) {
this.giftMessage = giftMessage;
}
private java.lang.String postalCodeExt;
public java.lang.String getpostalCodeExt() {
return postalCodeExt;
}
public void setpostalCodeExt( java.lang.String postalCodeExt ) {
this.postalCodeExt = postalCodeExt;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.sql.Timestamp estimatedDeliveryDate;
public java.sql.Timestamp getestimatedDeliveryDate() {
return estimatedDeliveryDate;
}
public void setestimatedDeliveryDate( java.sql.Timestamp estimatedDeliveryDate ) {
this.estimatedDeliveryDate = estimatedDeliveryDate;
}
private java.lang.String shippingInstructions;
public java.lang.String getshippingInstructions() {
return shippingInstructions;
}
public void setshippingInstructions( java.lang.String shippingInstructions ) {
this.shippingInstructions = shippingInstructions;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String maySplit;
public java.lang.String getmaySplit() {
return maySplit;
}
public void setmaySplit( java.lang.String maySplit ) {
this.maySplit = maySplit;
}
private java.lang.String syncStatusId;
public java.lang.String getsyncStatusId() {
return syncStatusId;
}
public void setsyncStatusId( java.lang.String syncStatusId ) {
this.syncStatusId = syncStatusId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String attnName;
public java.lang.String getattnName() {
return attnName;
}
public void setattnName( java.lang.String attnName ) {
this.attnName = attnName;
}
private java.lang.String autoOrderShoppingListId;
public java.lang.String getautoOrderShoppingListId() {
return autoOrderShoppingListId;
}
public void setautoOrderShoppingListId( java.lang.String autoOrderShoppingListId ) {
this.autoOrderShoppingListId = autoOrderShoppingListId;
}
private java.lang.String isRushOrder;
public java.lang.String getisRushOrder() {
return isRushOrder;
}
public void setisRushOrder( java.lang.String isRushOrder ) {
this.isRushOrder = isRushOrder;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.lang.String firstAttemptOrderId;
public java.lang.String getfirstAttemptOrderId() {
return firstAttemptOrderId;
}
public void setfirstAttemptOrderId( java.lang.String firstAttemptOrderId ) {
this.firstAttemptOrderId = firstAttemptOrderId;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.lang.String salesChannelEnumId;
public java.lang.String getsalesChannelEnumId() {
return salesChannelEnumId;
}
public void setsalesChannelEnumId( java.lang.String salesChannelEnumId ) {
this.salesChannelEnumId = salesChannelEnumId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderAndShipGroupsByProduct> objectList = new ArrayList<OrderHeaderAndShipGroupsByProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderAndShipGroupsByProduct(genVal));
        }
        return objectList;
    }    
}
