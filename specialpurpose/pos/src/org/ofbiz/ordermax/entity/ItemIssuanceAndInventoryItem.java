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
public class ItemIssuanceAndInventoryItem implements GenericValueObjectInterface {
public static final String module =ItemIssuanceAndInventoryItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ItemIssuanceAndInventoryItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ItemIssuanceAndInventoryItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"inventoryItemFixedAssetId","Inventory Item Fixed Asset Id"},
{"serialNumber","Serial Number"},
{"availableToPromiseTotal","Available To Promise Total"},
{"locationSeqId","Location Seq Id"},
{"activationValidThru","Activation Valid Thru"},
{"statusId","Status Id"},
{"oldQuantityOnHand","Old Quantity On Hand"},
{"inventoryItemTypeId","Inventory Item Type Id"},
{"unitCost","Unit Cost"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentId","Shipment Id"},
{"lotId","Lot Id"},
{"binNumber","Bin Number"},
{"datetimeManufactured","Datetime Manufactured"},
{"issuedDateTime","Issued Date Time"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"fixedAssetId","Fixed Asset Id"},
{"orderId","Order Id"},
{"oldAvailableToPromise","Old Available To Promise"},
{"ownerPartyId","Owner Party Id"},
{"accountingQuantityTotal","Accounting Quantity Total"},
{"quantityOnHandTotal","Quantity On Hand Total"},
{"currencyUomId","Currency Uom Id"},
{"uomId","Uom Id"},
{"facilityId","Facility Id"},
{"productId","Product Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"partyId","Party Id"},
{"itemIssuanceId","Item Issuance Id"},
{"maintHistSeqId","Maint Hist Seq Id"},
{"expireDate","Expire Date"},
{"softIdentifier","Soft Identifier"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"activationNumber","Activation Number"},
{"datetimeReceived","Datetime Received"},
{"containerId","Container Id"},
{"comments","Comments"},
{"issuedByUserLoginId","Issued By User Login Id"},
{"cancelQuantity","Cancel Quantity"},
};
protected void initObject(){
this.inventoryItemFixedAssetId = "";
this.serialNumber = "";
this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
this.locationSeqId = "";
this.activationValidThru = "";
this.statusId = "";
this.oldQuantityOnHand = "";
this.inventoryItemTypeId = "";
this.unitCost = java.math.BigDecimal.ZERO;
this.shipmentItemSeqId = "";
this.shipmentId = "";
this.lotId = "";
this.binNumber = "";
this.datetimeManufactured = "";
this.issuedDateTime = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.fixedAssetId = "";
this.orderId = "";
this.oldAvailableToPromise = "";
this.ownerPartyId = "";
this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
this.currencyUomId = "";
this.uomId = "";
this.facilityId = "";
this.productId = "";
this.orderItemSeqId = "";
this.partyId = "";
this.itemIssuanceId = "";
this.maintHistSeqId = "";
this.expireDate = UtilDateTime.nowTimestamp();
this.softIdentifier = "";
this.shipGroupSeqId = "";
this.activationNumber = "";
this.datetimeReceived = UtilDateTime.nowTimestamp();
this.containerId = "";
this.comments = "";
this.issuedByUserLoginId = "";
this.cancelQuantity = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.inventoryItemFixedAssetId = (java.lang.String) genVal.get("inventoryItemFixedAssetId");
this.serialNumber = (java.lang.String) genVal.get("serialNumber");
this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
this.activationValidThru = (java.lang.String) genVal.get("activationValidThru");
this.statusId = (java.lang.String) genVal.get("statusId");
this.oldQuantityOnHand = (java.lang.String) genVal.get("oldQuantityOnHand");
this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.lotId = (java.lang.String) genVal.get("lotId");
this.binNumber = (java.lang.String) genVal.get("binNumber");
this.datetimeManufactured = (java.lang.String) genVal.get("datetimeManufactured");
this.issuedDateTime = (java.sql.Timestamp) genVal.get("issuedDateTime");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.oldAvailableToPromise = (java.lang.String) genVal.get("oldAvailableToPromise");
this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.uomId = (java.lang.String) genVal.get("uomId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.productId = (java.lang.String) genVal.get("productId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.maintHistSeqId = (java.lang.String) genVal.get("maintHistSeqId");
this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
this.softIdentifier = (java.lang.String) genVal.get("softIdentifier");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.activationNumber = (java.lang.String) genVal.get("activationNumber");
this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
this.containerId = (java.lang.String) genVal.get("containerId");
this.comments = (java.lang.String) genVal.get("comments");
this.issuedByUserLoginId = (java.lang.String) genVal.get("issuedByUserLoginId");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
}
protected void getGenericValue(GenericValue val) {
val.set("inventoryItemFixedAssetId",OrderMaxUtility.getValidEntityString(this.inventoryItemFixedAssetId));
val.set("serialNumber",OrderMaxUtility.getValidEntityString(this.serialNumber));
val.set("availableToPromiseTotal",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
val.set("locationSeqId",OrderMaxUtility.getValidEntityString(this.locationSeqId));
val.set("activationValidThru",OrderMaxUtility.getValidEntityString(this.activationValidThru));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("oldQuantityOnHand",OrderMaxUtility.getValidEntityString(this.oldQuantityOnHand));
val.set("inventoryItemTypeId",OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("lotId",OrderMaxUtility.getValidEntityString(this.lotId));
val.set("binNumber",OrderMaxUtility.getValidEntityString(this.binNumber));
val.set("datetimeManufactured",OrderMaxUtility.getValidEntityString(this.datetimeManufactured));
val.set("issuedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.issuedDateTime));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("oldAvailableToPromise",OrderMaxUtility.getValidEntityString(this.oldAvailableToPromise));
val.set("ownerPartyId",OrderMaxUtility.getValidEntityString(this.ownerPartyId));
val.set("accountingQuantityTotal",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
val.set("quantityOnHandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("maintHistSeqId",OrderMaxUtility.getValidEntityString(this.maintHistSeqId));
val.set("expireDate",OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
val.set("softIdentifier",OrderMaxUtility.getValidEntityString(this.softIdentifier));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("activationNumber",OrderMaxUtility.getValidEntityString(this.activationNumber));
val.set("datetimeReceived",OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
val.set("containerId",OrderMaxUtility.getValidEntityString(this.containerId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("issuedByUserLoginId",OrderMaxUtility.getValidEntityString(this.issuedByUserLoginId));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("inventoryItemFixedAssetId",this.inventoryItemFixedAssetId);
valueMap.put("serialNumber",this.serialNumber);
valueMap.put("availableToPromiseTotal",this.availableToPromiseTotal);
valueMap.put("locationSeqId",this.locationSeqId);
valueMap.put("activationValidThru",this.activationValidThru);
valueMap.put("statusId",this.statusId);
valueMap.put("oldQuantityOnHand",this.oldQuantityOnHand);
valueMap.put("inventoryItemTypeId",this.inventoryItemTypeId);
valueMap.put("unitCost",this.unitCost);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("lotId",this.lotId);
valueMap.put("binNumber",this.binNumber);
valueMap.put("datetimeManufactured",this.datetimeManufactured);
valueMap.put("issuedDateTime",this.issuedDateTime);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("orderId",this.orderId);
valueMap.put("oldAvailableToPromise",this.oldAvailableToPromise);
valueMap.put("ownerPartyId",this.ownerPartyId);
valueMap.put("accountingQuantityTotal",this.accountingQuantityTotal);
valueMap.put("quantityOnHandTotal",this.quantityOnHandTotal);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("uomId",this.uomId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("productId",this.productId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("partyId",this.partyId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("maintHistSeqId",this.maintHistSeqId);
valueMap.put("expireDate",this.expireDate);
valueMap.put("softIdentifier",this.softIdentifier);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("activationNumber",this.activationNumber);
valueMap.put("datetimeReceived",this.datetimeReceived);
valueMap.put("containerId",this.containerId);
valueMap.put("comments",this.comments);
valueMap.put("issuedByUserLoginId",this.issuedByUserLoginId);
valueMap.put("cancelQuantity",this.cancelQuantity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ItemIssuanceAndInventoryItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String inventoryItemFixedAssetId;
public java.lang.String getinventoryItemFixedAssetId() {
return inventoryItemFixedAssetId;
}
public void setinventoryItemFixedAssetId( java.lang.String inventoryItemFixedAssetId ) {
this.inventoryItemFixedAssetId = inventoryItemFixedAssetId;
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
private java.lang.String lotId;
public java.lang.String getlotId() {
return lotId;
}
public void setlotId( java.lang.String lotId ) {
this.lotId = lotId;
}
private java.lang.String binNumber;
public java.lang.String getbinNumber() {
return binNumber;
}
public void setbinNumber( java.lang.String binNumber ) {
this.binNumber = binNumber;
}
private java.lang.String datetimeManufactured;
public java.lang.String getdatetimeManufactured() {
return datetimeManufactured;
}
public void setdatetimeManufactured( java.lang.String datetimeManufactured ) {
this.datetimeManufactured = datetimeManufactured;
}
private java.sql.Timestamp issuedDateTime;
public java.sql.Timestamp getissuedDateTime() {
return issuedDateTime;
}
public void setissuedDateTime( java.sql.Timestamp issuedDateTime ) {
this.issuedDateTime = issuedDateTime;
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
private java.lang.String oldAvailableToPromise;
public java.lang.String getoldAvailableToPromise() {
return oldAvailableToPromise;
}
public void setoldAvailableToPromise( java.lang.String oldAvailableToPromise ) {
this.oldAvailableToPromise = oldAvailableToPromise;
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
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String itemIssuanceId;
public java.lang.String getitemIssuanceId() {
return itemIssuanceId;
}
public void setitemIssuanceId( java.lang.String itemIssuanceId ) {
this.itemIssuanceId = itemIssuanceId;
}
private java.lang.String maintHistSeqId;
public java.lang.String getmaintHistSeqId() {
return maintHistSeqId;
}
public void setmaintHistSeqId( java.lang.String maintHistSeqId ) {
this.maintHistSeqId = maintHistSeqId;
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
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
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
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String issuedByUserLoginId;
public java.lang.String getissuedByUserLoginId() {
return issuedByUserLoginId;
}
public void setissuedByUserLoginId( java.lang.String issuedByUserLoginId ) {
this.issuedByUserLoginId = issuedByUserLoginId;
}
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ItemIssuanceAndInventoryItem> objectList = new ArrayList<ItemIssuanceAndInventoryItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ItemIssuanceAndInventoryItem(genVal));
        }
        return objectList;
    }    
}
