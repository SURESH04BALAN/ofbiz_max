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
public class ProductInventoryItemAndItemIssuance implements GenericValueObjectInterface {
public static final String module =ProductInventoryItemAndItemIssuance.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductInventoryItemAndItemIssuance(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductInventoryItemAndItemIssuance () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internalName","Internal Name"},
{"serialNumber","Serial Number"},
{"availableToPromiseTotal","Available To Promise Total"},
{"locationSeqId","Location Seq Id"},
{"activationValidThru","Activation Valid Thru"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentId","Shipment Id"},
{"description","Description"},
{"binNumber","Bin Number"},
{"datetimeManufactured","Datetime Manufactured"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"createdDate","Created Date"},
{"oldAvailableToPromise","Old Available To Promise"},
{"taxable","Taxable"},
{"currencyUomId","Currency Uom Id"},
{"uomId","Uom Id"},
{"facilityId","Facility Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"partyId","Party Id"},
{"expireDate","Expire Date"},
{"softIdentifier","Soft Identifier"},
{"activationNumber","Activation Number"},
{"datetimeReceived","Datetime Received"},
{"containerId","Container Id"},
{"issuedByUserLoginId","Issued By User Login Id"},
{"primaryProductCategoryId","Primary Product Category Id"},
{"productTypeId","Product Type Id"},
{"chargeShipping","Charge Shipping"},
{"inventoryItemFixedAssetId","Inventory Item Fixed Asset Id"},
{"statusId","Status Id"},
{"oldQuantityOnHand","Old Quantity On Hand"},
{"introductionDate","Introduction Date"},
{"inventoryItemTypeId","Inventory Item Type Id"},
{"unitCost","Unit Cost"},
{"lotId","Lot Id"},
{"issuedDateTime","Issued Date Time"},
{"fixedAssetId","Fixed Asset Id"},
{"requireInventory","Require Inventory"},
{"orderId","Order Id"},
{"ownerPartyId","Owner Party Id"},
{"manufacturerPartyId","Manufacturer Party Id"},
{"accountingQuantityTotal","Accounting Quantity Total"},
{"quantityOnHandTotal","Quantity On Hand Total"},
{"isVirtual","Is Virtual"},
{"productId","Product Id"},
{"maintHistSeqId","Maint Hist Seq Id"},
{"itemIssuanceId","Item Issuance Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"comments","Comments"},
{"cancelQuantity","Cancel Quantity"},
{"isVariant","Is Variant"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.internalName = "";
this.serialNumber = "";
this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
this.locationSeqId = "";
this.activationValidThru = "";
this.shipmentItemSeqId = "";
this.shipmentId = "";
this.description = "";
this.binNumber = "";
this.datetimeManufactured = "";
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.oldAvailableToPromise = "";
this.taxable = "";
this.currencyUomId = "";
this.uomId = "";
this.facilityId = "";
this.orderItemSeqId = "";
this.partyId = "";
this.expireDate = UtilDateTime.nowTimestamp();
this.softIdentifier = "";
this.activationNumber = "";
this.datetimeReceived = UtilDateTime.nowTimestamp();
this.containerId = "";
this.issuedByUserLoginId = "";
this.primaryProductCategoryId = "";
this.productTypeId = "";
this.chargeShipping = "";
this.inventoryItemFixedAssetId = "";
this.statusId = "";
this.oldQuantityOnHand = "";
this.introductionDate = UtilDateTime.nowTimestamp();
this.inventoryItemTypeId = "";
this.unitCost = java.math.BigDecimal.ZERO;
this.lotId = "";
this.issuedDateTime = UtilDateTime.nowTimestamp();
this.fixedAssetId = "";
this.requireInventory = "";
this.orderId = "";
this.ownerPartyId = "";
this.manufacturerPartyId = "";
this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
this.isVirtual = "";
this.productId = "";
this.maintHistSeqId = "";
this.itemIssuanceId = "";
this.shipGroupSeqId = "";
this.comments = "";
this.cancelQuantity = "";
this.isVariant = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internalName = (java.lang.String) genVal.get("internalName");
this.serialNumber = (java.lang.String) genVal.get("serialNumber");
this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
this.activationValidThru = (java.lang.String) genVal.get("activationValidThru");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.description = (java.lang.String) genVal.get("description");
this.binNumber = (java.lang.String) genVal.get("binNumber");
this.datetimeManufactured = (java.lang.String) genVal.get("datetimeManufactured");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.oldAvailableToPromise = (java.lang.String) genVal.get("oldAvailableToPromise");
this.taxable = (java.lang.String) genVal.get("taxable");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.uomId = (java.lang.String) genVal.get("uomId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
this.softIdentifier = (java.lang.String) genVal.get("softIdentifier");
this.activationNumber = (java.lang.String) genVal.get("activationNumber");
this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
this.containerId = (java.lang.String) genVal.get("containerId");
this.issuedByUserLoginId = (java.lang.String) genVal.get("issuedByUserLoginId");
this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
this.productTypeId = (java.lang.String) genVal.get("productTypeId");
this.chargeShipping = (java.lang.String) genVal.get("chargeShipping");
this.inventoryItemFixedAssetId = (java.lang.String) genVal.get("inventoryItemFixedAssetId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.oldQuantityOnHand = (java.lang.String) genVal.get("oldQuantityOnHand");
this.introductionDate = (java.sql.Timestamp) genVal.get("introductionDate");
this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.lotId = (java.lang.String) genVal.get("lotId");
this.issuedDateTime = (java.sql.Timestamp) genVal.get("issuedDateTime");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.requireInventory = (java.lang.String) genVal.get("requireInventory");
this.orderId = (java.lang.String) genVal.get("orderId");
this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
this.manufacturerPartyId = (java.lang.String) genVal.get("manufacturerPartyId");
this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
this.isVirtual = (java.lang.String) genVal.get("isVirtual");
this.productId = (java.lang.String) genVal.get("productId");
this.maintHistSeqId = (java.lang.String) genVal.get("maintHistSeqId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.comments = (java.lang.String) genVal.get("comments");
this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
this.isVariant = (java.lang.String) genVal.get("isVariant");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("serialNumber",OrderMaxUtility.getValidEntityString(this.serialNumber));
val.set("availableToPromiseTotal",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
val.set("locationSeqId",OrderMaxUtility.getValidEntityString(this.locationSeqId));
val.set("activationValidThru",OrderMaxUtility.getValidEntityString(this.activationValidThru));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("binNumber",OrderMaxUtility.getValidEntityString(this.binNumber));
val.set("datetimeManufactured",OrderMaxUtility.getValidEntityString(this.datetimeManufactured));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("oldAvailableToPromise",OrderMaxUtility.getValidEntityString(this.oldAvailableToPromise));
val.set("taxable",OrderMaxUtility.getValidEntityString(this.taxable));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("expireDate",OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
val.set("softIdentifier",OrderMaxUtility.getValidEntityString(this.softIdentifier));
val.set("activationNumber",OrderMaxUtility.getValidEntityString(this.activationNumber));
val.set("datetimeReceived",OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
val.set("containerId",OrderMaxUtility.getValidEntityString(this.containerId));
val.set("issuedByUserLoginId",OrderMaxUtility.getValidEntityString(this.issuedByUserLoginId));
val.set("primaryProductCategoryId",OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
val.set("productTypeId",OrderMaxUtility.getValidEntityString(this.productTypeId));
val.set("chargeShipping",OrderMaxUtility.getValidEntityString(this.chargeShipping));
val.set("inventoryItemFixedAssetId",OrderMaxUtility.getValidEntityString(this.inventoryItemFixedAssetId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("oldQuantityOnHand",OrderMaxUtility.getValidEntityString(this.oldQuantityOnHand));
val.set("introductionDate",OrderMaxUtility.getValidEntityTimestamp(this.introductionDate));
val.set("inventoryItemTypeId",OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("lotId",OrderMaxUtility.getValidEntityString(this.lotId));
val.set("issuedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.issuedDateTime));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("requireInventory",OrderMaxUtility.getValidEntityString(this.requireInventory));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("ownerPartyId",OrderMaxUtility.getValidEntityString(this.ownerPartyId));
val.set("manufacturerPartyId",OrderMaxUtility.getValidEntityString(this.manufacturerPartyId));
val.set("accountingQuantityTotal",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
val.set("quantityOnHandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
val.set("isVirtual",OrderMaxUtility.getValidEntityString(this.isVirtual));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("maintHistSeqId",OrderMaxUtility.getValidEntityString(this.maintHistSeqId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("cancelQuantity",OrderMaxUtility.getValidEntityString(this.cancelQuantity));
val.set("isVariant",OrderMaxUtility.getValidEntityString(this.isVariant));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internalName",this.internalName);
valueMap.put("serialNumber",this.serialNumber);
valueMap.put("availableToPromiseTotal",this.availableToPromiseTotal);
valueMap.put("locationSeqId",this.locationSeqId);
valueMap.put("activationValidThru",this.activationValidThru);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("description",this.description);
valueMap.put("binNumber",this.binNumber);
valueMap.put("datetimeManufactured",this.datetimeManufactured);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("oldAvailableToPromise",this.oldAvailableToPromise);
valueMap.put("taxable",this.taxable);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("uomId",this.uomId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("partyId",this.partyId);
valueMap.put("expireDate",this.expireDate);
valueMap.put("softIdentifier",this.softIdentifier);
valueMap.put("activationNumber",this.activationNumber);
valueMap.put("datetimeReceived",this.datetimeReceived);
valueMap.put("containerId",this.containerId);
valueMap.put("issuedByUserLoginId",this.issuedByUserLoginId);
valueMap.put("primaryProductCategoryId",this.primaryProductCategoryId);
valueMap.put("productTypeId",this.productTypeId);
valueMap.put("chargeShipping",this.chargeShipping);
valueMap.put("inventoryItemFixedAssetId",this.inventoryItemFixedAssetId);
valueMap.put("statusId",this.statusId);
valueMap.put("oldQuantityOnHand",this.oldQuantityOnHand);
valueMap.put("introductionDate",this.introductionDate);
valueMap.put("inventoryItemTypeId",this.inventoryItemTypeId);
valueMap.put("unitCost",this.unitCost);
valueMap.put("lotId",this.lotId);
valueMap.put("issuedDateTime",this.issuedDateTime);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("requireInventory",this.requireInventory);
valueMap.put("orderId",this.orderId);
valueMap.put("ownerPartyId",this.ownerPartyId);
valueMap.put("manufacturerPartyId",this.manufacturerPartyId);
valueMap.put("accountingQuantityTotal",this.accountingQuantityTotal);
valueMap.put("quantityOnHandTotal",this.quantityOnHandTotal);
valueMap.put("isVirtual",this.isVirtual);
valueMap.put("productId",this.productId);
valueMap.put("maintHistSeqId",this.maintHistSeqId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("comments",this.comments);
valueMap.put("cancelQuantity",this.cancelQuantity);
valueMap.put("isVariant",this.isVariant);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductInventoryItemAndItemIssuance");
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
private java.lang.String datetimeManufactured;
public java.lang.String getdatetimeManufactured() {
return datetimeManufactured;
}
public void setdatetimeManufactured( java.lang.String datetimeManufactured ) {
this.datetimeManufactured = datetimeManufactured;
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
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String oldAvailableToPromise;
public java.lang.String getoldAvailableToPromise() {
return oldAvailableToPromise;
}
public void setoldAvailableToPromise( java.lang.String oldAvailableToPromise ) {
this.oldAvailableToPromise = oldAvailableToPromise;
}
private java.lang.String taxable;
public java.lang.String gettaxable() {
return taxable;
}
public void settaxable( java.lang.String taxable ) {
this.taxable = taxable;
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
private java.lang.String issuedByUserLoginId;
public java.lang.String getissuedByUserLoginId() {
return issuedByUserLoginId;
}
public void setissuedByUserLoginId( java.lang.String issuedByUserLoginId ) {
this.issuedByUserLoginId = issuedByUserLoginId;
}
private java.lang.String primaryProductCategoryId;
public java.lang.String getprimaryProductCategoryId() {
return primaryProductCategoryId;
}
public void setprimaryProductCategoryId( java.lang.String primaryProductCategoryId ) {
this.primaryProductCategoryId = primaryProductCategoryId;
}
private java.lang.String productTypeId;
public java.lang.String getproductTypeId() {
return productTypeId;
}
public void setproductTypeId( java.lang.String productTypeId ) {
this.productTypeId = productTypeId;
}
private java.lang.String chargeShipping;
public java.lang.String getchargeShipping() {
return chargeShipping;
}
public void setchargeShipping( java.lang.String chargeShipping ) {
this.chargeShipping = chargeShipping;
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
private java.sql.Timestamp introductionDate;
public java.sql.Timestamp getintroductionDate() {
return introductionDate;
}
public void setintroductionDate( java.sql.Timestamp introductionDate ) {
this.introductionDate = introductionDate;
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
private java.sql.Timestamp issuedDateTime;
public java.sql.Timestamp getissuedDateTime() {
return issuedDateTime;
}
public void setissuedDateTime( java.sql.Timestamp issuedDateTime ) {
this.issuedDateTime = issuedDateTime;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.lang.String requireInventory;
public java.lang.String getrequireInventory() {
return requireInventory;
}
public void setrequireInventory( java.lang.String requireInventory ) {
this.requireInventory = requireInventory;
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
private java.lang.String manufacturerPartyId;
public java.lang.String getmanufacturerPartyId() {
return manufacturerPartyId;
}
public void setmanufacturerPartyId( java.lang.String manufacturerPartyId ) {
this.manufacturerPartyId = manufacturerPartyId;
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
private java.lang.String isVirtual;
public java.lang.String getisVirtual() {
return isVirtual;
}
public void setisVirtual( java.lang.String isVirtual ) {
this.isVirtual = isVirtual;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
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
private java.lang.String cancelQuantity;
public java.lang.String getcancelQuantity() {
return cancelQuantity;
}
public void setcancelQuantity( java.lang.String cancelQuantity ) {
this.cancelQuantity = cancelQuantity;
}
private java.lang.String isVariant;
public java.lang.String getisVariant() {
return isVariant;
}
public void setisVariant( java.lang.String isVariant ) {
this.isVariant = isVariant;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductInventoryItemAndItemIssuance> objectList = new ArrayList<ProductInventoryItemAndItemIssuance>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductInventoryItemAndItemIssuance(genVal));
        }
        return objectList;
    }    
}
