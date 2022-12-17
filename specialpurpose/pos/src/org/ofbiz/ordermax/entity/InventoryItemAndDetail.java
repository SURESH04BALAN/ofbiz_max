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
public class InventoryItemAndDetail implements GenericValueObjectInterface {
public static final String module =InventoryItemAndDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InventoryItemAndDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InventoryItemAndDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"serialNumber","Serial Number"},
{"availableToPromiseTotal","Available To Promise Total"},
{"locationSeqId","Location Seq Id"},
{"activationValidThru","Activation Valid Thru"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentId","Shipment Id"},
{"description","Description"},
{"binNumber","Bin Number"},
{"datetimeManufactured","Datetime Manufactured"},
{"inventoryItemId","Inventory Item Id"},
{"oldAvailableToPromise","Old Available To Promise"},
{"currencyUomId","Currency Uom Id"},
{"uomId","Uom Id"},
{"returnId","Return Id"},
{"facilityId","Facility Id"},
{"accountingQuantityDiff","Accounting Quantity Diff"},
{"workEffortId","Work Effort Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"physicalInventoryId","Physical Inventory Id"},
{"partyId","Party Id"},
{"expireDate","Expire Date"},
{"softIdentifier","Soft Identifier"},
{"activationNumber","Activation Number"},
{"datetimeReceived","Datetime Received"},
{"containerId","Container Id"},
{"inventoryItemFixedAssetId","Inventory Item Fixed Asset Id"},
{"statusId","Status Id"},
{"oldQuantityOnHand","Old Quantity On Hand"},
{"inventoryItemTypeId","Inventory Item Type Id"},
{"unitCost","Unit Cost"},
{"lotId","Lot Id"},
{"reasonEnumId","Reason Enum Id"},
{"effectiveDate","Effective Date"},
{"returnItemSeqId","Return Item Seq Id"},
{"fixedAssetId","Fixed Asset Id"},
{"orderId","Order Id"},
{"ownerPartyId","Owner Party Id"},
{"accountingQuantityTotal","Accounting Quantity Total"},
{"quantityOnHandTotal","Quantity On Hand Total"},
{"inventoryItemDetailSeqId","Inventory Item Detail Seq Id"},
{"availableToPromiseDiff","Available To Promise Diff"},
{"productId","Product Id"},
{"quantityOnHandDiff","Quantity On Hand Diff"},
{"maintHistSeqId","Maint Hist Seq Id"},
{"itemIssuanceId","Item Issuance Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"comments","Comments"},
{"receiptId","Receipt Id"},
};
protected void initObject(){
this.serialNumber = "";
this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
this.locationSeqId = "";
this.activationValidThru = "";
this.shipmentItemSeqId = "";
this.shipmentId = "";
this.description = "";
this.binNumber = "";
this.datetimeManufactured = null;
this.inventoryItemId = "";
this.oldAvailableToPromise = "";
this.currencyUomId = "";
this.uomId = "";
this.returnId = "";
this.facilityId = "";
this.accountingQuantityDiff = java.math.BigDecimal.ZERO;
this.workEffortId = "";
this.orderItemSeqId = "";
this.physicalInventoryId = "";
this.partyId = "";
this.expireDate = UtilDateTime.nowTimestamp();
this.softIdentifier = "";
this.activationNumber = "";
this.datetimeReceived = UtilDateTime.nowTimestamp();
this.containerId = "";
this.inventoryItemFixedAssetId = "";
this.statusId = "";
this.oldQuantityOnHand = "";
this.inventoryItemTypeId = "";
this.unitCost = java.math.BigDecimal.ZERO;
this.lotId = "";
this.reasonEnumId = "";
this.effectiveDate = UtilDateTime.nowTimestamp();
this.returnItemSeqId = "";
this.fixedAssetId = "";
this.orderId = "";
this.ownerPartyId = "";
this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
this.inventoryItemDetailSeqId = "";
this.availableToPromiseDiff = java.math.BigDecimal.ZERO;
this.productId = "";
this.quantityOnHandDiff = java.math.BigDecimal.ZERO;
this.maintHistSeqId = "";
this.itemIssuanceId = "";
this.shipGroupSeqId = "";
this.comments = "";
this.receiptId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.serialNumber = (java.lang.String) genVal.get("serialNumber");
this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
this.activationValidThru = (java.lang.String) genVal.get("activationValidThru");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.description = (java.lang.String) genVal.get("description");
this.binNumber = (java.lang.String) genVal.get("binNumber");
this.datetimeManufactured = (java.sql.Timestamp) genVal.get("datetimeManufactured");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.oldAvailableToPromise = (java.lang.String) genVal.get("oldAvailableToPromise");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.uomId = (java.lang.String) genVal.get("uomId");
this.returnId = (java.lang.String) genVal.get("returnId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.accountingQuantityDiff = (java.math.BigDecimal) genVal.get("accountingQuantityDiff");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.physicalInventoryId = (java.lang.String) genVal.get("physicalInventoryId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
this.softIdentifier = (java.lang.String) genVal.get("softIdentifier");
this.activationNumber = (java.lang.String) genVal.get("activationNumber");
this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
this.containerId = (java.lang.String) genVal.get("containerId");
this.inventoryItemFixedAssetId = (java.lang.String) genVal.get("inventoryItemFixedAssetId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.oldQuantityOnHand = (java.lang.String) genVal.get("oldQuantityOnHand");
this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.lotId = (java.lang.String) genVal.get("lotId");
this.reasonEnumId = (java.lang.String) genVal.get("reasonEnumId");
this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
this.inventoryItemDetailSeqId = (java.lang.String) genVal.get("inventoryItemDetailSeqId");
this.availableToPromiseDiff = (java.math.BigDecimal) genVal.get("availableToPromiseDiff");
this.productId = (java.lang.String) genVal.get("productId");
this.quantityOnHandDiff = (java.math.BigDecimal) genVal.get("quantityOnHandDiff");
this.maintHistSeqId = (java.lang.String) genVal.get("maintHistSeqId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.comments = (java.lang.String) genVal.get("comments");
this.receiptId = (java.lang.String) genVal.get("receiptId");
}
protected void getGenericValue(GenericValue val) {
val.set("serialNumber",OrderMaxUtility.getValidEntityString(this.serialNumber));
val.set("availableToPromiseTotal",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
val.set("locationSeqId",OrderMaxUtility.getValidEntityString(this.locationSeqId));
val.set("activationValidThru",OrderMaxUtility.getValidEntityString(this.activationValidThru));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("binNumber",OrderMaxUtility.getValidEntityString(this.binNumber));
val.set("datetimeManufactured",OrderMaxUtility.getValidEntityTimestamp(this.datetimeManufactured));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("oldAvailableToPromise",OrderMaxUtility.getValidEntityString(this.oldAvailableToPromise));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("returnId",OrderMaxUtility.getValidEntityString(this.returnId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("accountingQuantityDiff",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityDiff));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("physicalInventoryId",OrderMaxUtility.getValidEntityString(this.physicalInventoryId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("expireDate",OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
val.set("softIdentifier",OrderMaxUtility.getValidEntityString(this.softIdentifier));
val.set("activationNumber",OrderMaxUtility.getValidEntityString(this.activationNumber));
val.set("datetimeReceived",OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
val.set("containerId",OrderMaxUtility.getValidEntityString(this.containerId));
val.set("inventoryItemFixedAssetId",OrderMaxUtility.getValidEntityString(this.inventoryItemFixedAssetId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("oldQuantityOnHand",OrderMaxUtility.getValidEntityString(this.oldQuantityOnHand));
val.set("inventoryItemTypeId",OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("lotId",OrderMaxUtility.getValidEntityString(this.lotId));
val.set("reasonEnumId",OrderMaxUtility.getValidEntityString(this.reasonEnumId));
val.set("effectiveDate",OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
val.set("returnItemSeqId",OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("ownerPartyId",OrderMaxUtility.getValidEntityString(this.ownerPartyId));
val.set("accountingQuantityTotal",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
val.set("quantityOnHandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
val.set("inventoryItemDetailSeqId",OrderMaxUtility.getValidEntityString(this.inventoryItemDetailSeqId));
val.set("availableToPromiseDiff",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseDiff));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("quantityOnHandDiff",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandDiff));
val.set("maintHistSeqId",OrderMaxUtility.getValidEntityString(this.maintHistSeqId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("serialNumber",this.serialNumber);
valueMap.put("availableToPromiseTotal",this.availableToPromiseTotal);
valueMap.put("locationSeqId",this.locationSeqId);
valueMap.put("activationValidThru",this.activationValidThru);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("description",this.description);
valueMap.put("binNumber",this.binNumber);
valueMap.put("datetimeManufactured",this.datetimeManufactured);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("oldAvailableToPromise",this.oldAvailableToPromise);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("uomId",this.uomId);
valueMap.put("returnId",this.returnId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("accountingQuantityDiff",this.accountingQuantityDiff);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("physicalInventoryId",this.physicalInventoryId);
valueMap.put("partyId",this.partyId);
valueMap.put("expireDate",this.expireDate);
valueMap.put("softIdentifier",this.softIdentifier);
valueMap.put("activationNumber",this.activationNumber);
valueMap.put("datetimeReceived",this.datetimeReceived);
valueMap.put("containerId",this.containerId);
valueMap.put("inventoryItemFixedAssetId",this.inventoryItemFixedAssetId);
valueMap.put("statusId",this.statusId);
valueMap.put("oldQuantityOnHand",this.oldQuantityOnHand);
valueMap.put("inventoryItemTypeId",this.inventoryItemTypeId);
valueMap.put("unitCost",this.unitCost);
valueMap.put("lotId",this.lotId);
valueMap.put("reasonEnumId",this.reasonEnumId);
valueMap.put("effectiveDate",this.effectiveDate);
valueMap.put("returnItemSeqId",this.returnItemSeqId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("orderId",this.orderId);
valueMap.put("ownerPartyId",this.ownerPartyId);
valueMap.put("accountingQuantityTotal",this.accountingQuantityTotal);
valueMap.put("quantityOnHandTotal",this.quantityOnHandTotal);
valueMap.put("inventoryItemDetailSeqId",this.inventoryItemDetailSeqId);
valueMap.put("availableToPromiseDiff",this.availableToPromiseDiff);
valueMap.put("productId",this.productId);
valueMap.put("quantityOnHandDiff",this.quantityOnHandDiff);
valueMap.put("maintHistSeqId",this.maintHistSeqId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("comments",this.comments);
valueMap.put("receiptId",this.receiptId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InventoryItemAndDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String serialNumber;
public java.lang.String getserialNumber() {
return serialNumber;
}
public void setserialNumber( java.lang.String serialNumber ) {
this.serialNumber = serialNumber;
}
private java.math.BigDecimal availableToPromiseTotal;
public java.math.BigDecimal getavailableToPromiseTotal() {
return availableToPromiseTotal;
}
public void setavailableToPromiseTotal( java.math.BigDecimal availableToPromiseTotal ) {
this.availableToPromiseTotal = availableToPromiseTotal;
}
private java.lang.String locationSeqId;
public java.lang.String getlocationSeqId() {
return locationSeqId;
}
public void setlocationSeqId( java.lang.String locationSeqId ) {
this.locationSeqId = locationSeqId;
}
private java.lang.String activationValidThru;
public java.lang.String getactivationValidThru() {
return activationValidThru;
}
public void setactivationValidThru( java.lang.String activationValidThru ) {
this.activationValidThru = activationValidThru;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String binNumber;
public java.lang.String getbinNumber() {
return binNumber;
}
public void setbinNumber( java.lang.String binNumber ) {
this.binNumber = binNumber;
}
private java.sql.Timestamp datetimeManufactured;
public java.sql.Timestamp getdatetimeManufactured() {
return datetimeManufactured;
}
public void setdatetimeManufactured( java.sql.Timestamp datetimeManufactured ) {
this.datetimeManufactured = datetimeManufactured;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.lang.String oldAvailableToPromise;
public java.lang.String getoldAvailableToPromise() {
return oldAvailableToPromise;
}
public void setoldAvailableToPromise( java.lang.String oldAvailableToPromise ) {
this.oldAvailableToPromise = oldAvailableToPromise;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String uomId;
public java.lang.String getuomId() {
return uomId;
}
public void setuomId( java.lang.String uomId ) {
this.uomId = uomId;
}
private java.lang.String returnId;
public java.lang.String getreturnId() {
return returnId;
}
public void setreturnId( java.lang.String returnId ) {
this.returnId = returnId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.math.BigDecimal accountingQuantityDiff;
public java.math.BigDecimal getaccountingQuantityDiff() {
return accountingQuantityDiff;
}
public void setaccountingQuantityDiff( java.math.BigDecimal accountingQuantityDiff ) {
this.accountingQuantityDiff = accountingQuantityDiff;
}
private java.lang.String workEffortId;
public java.lang.String getworkEffortId() {
return workEffortId;
}
public void setworkEffortId( java.lang.String workEffortId ) {
this.workEffortId = workEffortId;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String physicalInventoryId;
public java.lang.String getphysicalInventoryId() {
return physicalInventoryId;
}
public void setphysicalInventoryId( java.lang.String physicalInventoryId ) {
this.physicalInventoryId = physicalInventoryId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.sql.Timestamp expireDate;
public java.sql.Timestamp getexpireDate() {
return expireDate;
}
public void setexpireDate( java.sql.Timestamp expireDate ) {
this.expireDate = expireDate;
}
private java.lang.String softIdentifier;
public java.lang.String getsoftIdentifier() {
return softIdentifier;
}
public void setsoftIdentifier( java.lang.String softIdentifier ) {
this.softIdentifier = softIdentifier;
}
private java.lang.String activationNumber;
public java.lang.String getactivationNumber() {
return activationNumber;
}
public void setactivationNumber( java.lang.String activationNumber ) {
this.activationNumber = activationNumber;
}
private java.sql.Timestamp datetimeReceived;
public java.sql.Timestamp getdatetimeReceived() {
return datetimeReceived;
}
public void setdatetimeReceived( java.sql.Timestamp datetimeReceived ) {
this.datetimeReceived = datetimeReceived;
}
private java.lang.String containerId;
public java.lang.String getcontainerId() {
return containerId;
}
public void setcontainerId( java.lang.String containerId ) {
this.containerId = containerId;
}
private java.lang.String inventoryItemFixedAssetId;
public java.lang.String getinventoryItemFixedAssetId() {
return inventoryItemFixedAssetId;
}
public void setinventoryItemFixedAssetId( java.lang.String inventoryItemFixedAssetId ) {
this.inventoryItemFixedAssetId = inventoryItemFixedAssetId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String oldQuantityOnHand;
public java.lang.String getoldQuantityOnHand() {
return oldQuantityOnHand;
}
public void setoldQuantityOnHand( java.lang.String oldQuantityOnHand ) {
this.oldQuantityOnHand = oldQuantityOnHand;
}
private java.lang.String inventoryItemTypeId;
public java.lang.String getinventoryItemTypeId() {
return inventoryItemTypeId;
}
public void setinventoryItemTypeId( java.lang.String inventoryItemTypeId ) {
this.inventoryItemTypeId = inventoryItemTypeId;
}
private java.math.BigDecimal unitCost;
public java.math.BigDecimal getunitCost() {
return unitCost;
}
public void setunitCost( java.math.BigDecimal unitCost ) {
this.unitCost = unitCost;
}
private java.lang.String lotId;
public java.lang.String getlotId() {
return lotId;
}
public void setlotId( java.lang.String lotId ) {
this.lotId = lotId;
}
private java.lang.String reasonEnumId;
public java.lang.String getreasonEnumId() {
return reasonEnumId;
}
public void setreasonEnumId( java.lang.String reasonEnumId ) {
this.reasonEnumId = reasonEnumId;
}
private java.sql.Timestamp effectiveDate;
public java.sql.Timestamp geteffectiveDate() {
return effectiveDate;
}
public void seteffectiveDate( java.sql.Timestamp effectiveDate ) {
this.effectiveDate = effectiveDate;
}
private java.lang.String returnItemSeqId;
public java.lang.String getreturnItemSeqId() {
return returnItemSeqId;
}
public void setreturnItemSeqId( java.lang.String returnItemSeqId ) {
this.returnItemSeqId = returnItemSeqId;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String ownerPartyId;
public java.lang.String getownerPartyId() {
return ownerPartyId;
}
public void setownerPartyId( java.lang.String ownerPartyId ) {
this.ownerPartyId = ownerPartyId;
}
private java.math.BigDecimal accountingQuantityTotal;
public java.math.BigDecimal getaccountingQuantityTotal() {
return accountingQuantityTotal;
}
public void setaccountingQuantityTotal( java.math.BigDecimal accountingQuantityTotal ) {
this.accountingQuantityTotal = accountingQuantityTotal;
}
private java.math.BigDecimal quantityOnHandTotal;
public java.math.BigDecimal getquantityOnHandTotal() {
return quantityOnHandTotal;
}
public void setquantityOnHandTotal( java.math.BigDecimal quantityOnHandTotal ) {
this.quantityOnHandTotal = quantityOnHandTotal;
}
private java.lang.String inventoryItemDetailSeqId;
public java.lang.String getinventoryItemDetailSeqId() {
return inventoryItemDetailSeqId;
}
public void setinventoryItemDetailSeqId( java.lang.String inventoryItemDetailSeqId ) {
this.inventoryItemDetailSeqId = inventoryItemDetailSeqId;
}
private java.math.BigDecimal availableToPromiseDiff;
public java.math.BigDecimal getavailableToPromiseDiff() {
return availableToPromiseDiff;
}
public void setavailableToPromiseDiff( java.math.BigDecimal availableToPromiseDiff ) {
this.availableToPromiseDiff = availableToPromiseDiff;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.math.BigDecimal quantityOnHandDiff;
public java.math.BigDecimal getquantityOnHandDiff() {
return quantityOnHandDiff;
}
public void setquantityOnHandDiff( java.math.BigDecimal quantityOnHandDiff ) {
this.quantityOnHandDiff = quantityOnHandDiff;
}
private java.lang.String maintHistSeqId;
public java.lang.String getmaintHistSeqId() {
return maintHistSeqId;
}
public void setmaintHistSeqId( java.lang.String maintHistSeqId ) {
this.maintHistSeqId = maintHistSeqId;
}
private java.lang.String itemIssuanceId;
public java.lang.String getitemIssuanceId() {
return itemIssuanceId;
}
public void setitemIssuanceId( java.lang.String itemIssuanceId ) {
this.itemIssuanceId = itemIssuanceId;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String receiptId;
public java.lang.String getreceiptId() {
return receiptId;
}
public void setreceiptId( java.lang.String receiptId ) {
this.receiptId = receiptId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryItemAndDetail> objectList = new ArrayList<InventoryItemAndDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItemAndDetail(genVal));
        }
        return objectList;
    }    
}
