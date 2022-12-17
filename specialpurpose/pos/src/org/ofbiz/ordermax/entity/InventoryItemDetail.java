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
public class InventoryItemDetail implements GenericValueObjectInterface {
public static final String module =InventoryItemDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InventoryItemDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InventoryItemDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"unitCost","Unit Cost"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"reasonEnumId","Reason Enum Id"},
{"inventoryItemId","Inventory Item Id"},
{"effectiveDate","Effective Date"},
{"fixedAssetId","Fixed Asset Id"},
{"returnItemSeqId","Return Item Seq Id"},
{"orderId","Order Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"inventoryItemDetailSeqId","Inventory Item Detail Seq Id"},
{"returnId","Return Id"},
{"availableToPromiseDiff","Available To Promise Diff"},
{"accountingQuantityDiff","Accounting Quantity Diff"},
{"workEffortId","Work Effort Id"},
{"quantityOnHandDiff","Quantity On Hand Diff"},
{"orderItemSeqId","Order Item Seq Id"},
{"physicalInventoryId","Physical Inventory Id"},
{"itemIssuanceId","Item Issuance Id"},
{"maintHistSeqId","Maint Hist Seq Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"receiptId","Receipt Id"},
};
protected void initObject(){
this.unitCost = java.math.BigDecimal.ZERO;
this.shipmentItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.reasonEnumId = "";
this.inventoryItemId = "";
this.effectiveDate = UtilDateTime.nowTimestamp();
this.fixedAssetId = "";
this.returnItemSeqId = "";
this.orderId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.inventoryItemDetailSeqId = "";
this.returnId = "";
this.availableToPromiseDiff = java.math.BigDecimal.ZERO;
this.accountingQuantityDiff = java.math.BigDecimal.ZERO;
this.workEffortId = "";
this.quantityOnHandDiff = java.math.BigDecimal.ZERO;
this.orderItemSeqId = "";
this.physicalInventoryId = "";
this.itemIssuanceId = "";
this.maintHistSeqId = "";
this.shipGroupSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.receiptId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.reasonEnumId = (java.lang.String) genVal.get("reasonEnumId");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.inventoryItemDetailSeqId = (java.lang.String) genVal.get("inventoryItemDetailSeqId");
this.returnId = (java.lang.String) genVal.get("returnId");
this.availableToPromiseDiff = (java.math.BigDecimal) genVal.get("availableToPromiseDiff");
this.accountingQuantityDiff = (java.math.BigDecimal) genVal.get("accountingQuantityDiff");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.quantityOnHandDiff = (java.math.BigDecimal) genVal.get("quantityOnHandDiff");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.physicalInventoryId = (java.lang.String) genVal.get("physicalInventoryId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.maintHistSeqId = (java.lang.String) genVal.get("maintHistSeqId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.receiptId = (java.lang.String) genVal.get("receiptId");
}
protected void getGenericValue(GenericValue val) {
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("reasonEnumId",OrderMaxUtility.getValidEntityString(this.reasonEnumId));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("effectiveDate",OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("returnItemSeqId",OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("inventoryItemDetailSeqId",OrderMaxUtility.getValidEntityString(this.inventoryItemDetailSeqId));
val.set("returnId",OrderMaxUtility.getValidEntityString(this.returnId));
val.set("availableToPromiseDiff",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseDiff));
val.set("accountingQuantityDiff",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityDiff));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("quantityOnHandDiff",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandDiff));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("physicalInventoryId",OrderMaxUtility.getValidEntityString(this.physicalInventoryId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("maintHistSeqId",OrderMaxUtility.getValidEntityString(this.maintHistSeqId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("unitCost",this.unitCost);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("description",this.description);
valueMap.put("reasonEnumId",this.reasonEnumId);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("effectiveDate",this.effectiveDate);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("returnItemSeqId",this.returnItemSeqId);
valueMap.put("orderId",this.orderId);
valueMap.put("inventoryItemDetailSeqId",this.inventoryItemDetailSeqId);
valueMap.put("returnId",this.returnId);
valueMap.put("availableToPromiseDiff",this.availableToPromiseDiff);
valueMap.put("accountingQuantityDiff",this.accountingQuantityDiff);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("quantityOnHandDiff",this.quantityOnHandDiff);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("physicalInventoryId",this.physicalInventoryId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("maintHistSeqId",this.maintHistSeqId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("receiptId",this.receiptId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InventoryItemDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String reasonEnumId;
public java.lang.String getreasonEnumId() {
return reasonEnumId;
}
public void setreasonEnumId( java.lang.String reasonEnumId ) {
this.reasonEnumId = reasonEnumId;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.sql.Timestamp effectiveDate;
public java.sql.Timestamp geteffectiveDate() {
return effectiveDate;
}
public void seteffectiveDate( java.sql.Timestamp effectiveDate ) {
this.effectiveDate = effectiveDate;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.lang.String returnItemSeqId;
public java.lang.String getreturnItemSeqId() {
return returnItemSeqId;
}
public void setreturnItemSeqId( java.lang.String returnItemSeqId ) {
this.returnItemSeqId = returnItemSeqId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String inventoryItemDetailSeqId;
public java.lang.String getinventoryItemDetailSeqId() {
return inventoryItemDetailSeqId;
}
public void setinventoryItemDetailSeqId( java.lang.String inventoryItemDetailSeqId ) {
this.inventoryItemDetailSeqId = inventoryItemDetailSeqId;
}
private java.lang.String returnId;
public java.lang.String getreturnId() {
return returnId;
}
public void setreturnId( java.lang.String returnId ) {
this.returnId = returnId;
}
private java.math.BigDecimal availableToPromiseDiff;
public java.math.BigDecimal getavailableToPromiseDiff() {
return availableToPromiseDiff;
}
public void setavailableToPromiseDiff( java.math.BigDecimal availableToPromiseDiff ) {
this.availableToPromiseDiff = availableToPromiseDiff;
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
private java.math.BigDecimal quantityOnHandDiff;
public java.math.BigDecimal getquantityOnHandDiff() {
return quantityOnHandDiff;
}
public void setquantityOnHandDiff( java.math.BigDecimal quantityOnHandDiff ) {
this.quantityOnHandDiff = quantityOnHandDiff;
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
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<InventoryItemDetail> objectList = new ArrayList<InventoryItemDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItemDetail(genVal));
        }
        return objectList;
    }    
}
