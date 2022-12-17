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
public class PicklistAndBinAndItem implements GenericValueObjectInterface {
public static final String module =PicklistAndBinAndItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PicklistAndBinAndItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PicklistAndBinAndItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"primaryShipGroupSeqId","Primary Ship Group Seq Id"},
{"picklistBinId","Picklist Bin Id"},
{"binLocationNumber","Bin Location Number"},
{"primaryOrderId","Primary Order Id"},
{"facilityId","Facility Id"},
{"statusId","Status Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"picklistDate","Picklist Date"},
{"description","Description"},
{"picklistId","Picklist Id"},
{"itemStatusId","Item Status Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"orderId","Order Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.primaryShipGroupSeqId = "";
this.picklistBinId = "";
this.binLocationNumber = new Long(0);
this.primaryOrderId = "";
this.facilityId = "";
this.statusId = "";
this.orderItemSeqId = "";
this.lastModifiedByUserLogin = "";
this.picklistDate = UtilDateTime.nowTimestamp();
this.description = "";
this.picklistId = "";
this.itemStatusId = "";
this.shipGroupSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.shipmentMethodTypeId = "";
this.orderId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.primaryShipGroupSeqId = (java.lang.String) genVal.get("primaryShipGroupSeqId");
this.picklistBinId = (java.lang.String) genVal.get("picklistBinId");
this.binLocationNumber = (java.lang.Long) genVal.get("binLocationNumber");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.picklistDate = (java.sql.Timestamp) genVal.get("picklistDate");
this.description = (java.lang.String) genVal.get("description");
this.picklistId = (java.lang.String) genVal.get("picklistId");
this.itemStatusId = (java.lang.String) genVal.get("itemStatusId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("primaryShipGroupSeqId",OrderMaxUtility.getValidEntityString(this.primaryShipGroupSeqId));
val.set("picklistBinId",OrderMaxUtility.getValidEntityString(this.picklistBinId));
val.set("binLocationNumber",this.binLocationNumber);
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("picklistDate",OrderMaxUtility.getValidEntityTimestamp(this.picklistDate));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("picklistId",OrderMaxUtility.getValidEntityString(this.picklistId));
val.set("itemStatusId",OrderMaxUtility.getValidEntityString(this.itemStatusId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("primaryShipGroupSeqId",this.primaryShipGroupSeqId);
valueMap.put("picklistBinId",this.picklistBinId);
valueMap.put("binLocationNumber",this.binLocationNumber);
valueMap.put("primaryOrderId",this.primaryOrderId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("statusId",this.statusId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("picklistDate",this.picklistDate);
valueMap.put("description",this.description);
valueMap.put("picklistId",this.picklistId);
valueMap.put("itemStatusId",this.itemStatusId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("orderId",this.orderId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PicklistAndBinAndItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String primaryShipGroupSeqId;
public java.lang.String getprimaryShipGroupSeqId() {
return primaryShipGroupSeqId;
}
public void setprimaryShipGroupSeqId( java.lang.String primaryShipGroupSeqId ) {
this.primaryShipGroupSeqId = primaryShipGroupSeqId;
}
private java.lang.String picklistBinId;
public java.lang.String getpicklistBinId() {
return picklistBinId;
}
public void setpicklistBinId( java.lang.String picklistBinId ) {
this.picklistBinId = picklistBinId;
}
private java.lang.Long binLocationNumber;
public java.lang.Long getbinLocationNumber() {
return binLocationNumber;
}
public void setbinLocationNumber( java.lang.Long binLocationNumber ) {
this.binLocationNumber = binLocationNumber;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp picklistDate;
public java.sql.Timestamp getpicklistDate() {
return picklistDate;
}
public void setpicklistDate( java.sql.Timestamp picklistDate ) {
this.picklistDate = picklistDate;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String picklistId;
public java.lang.String getpicklistId() {
return picklistId;
}
public void setpicklistId( java.lang.String picklistId ) {
this.picklistId = picklistId;
}
private java.lang.String itemStatusId;
public java.lang.String getitemStatusId() {
return itemStatusId;
}
public void setitemStatusId( java.lang.String itemStatusId ) {
this.itemStatusId = itemStatusId;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
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
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
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
        Collection<PicklistAndBinAndItem> objectList = new ArrayList<PicklistAndBinAndItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new PicklistAndBinAndItem(genVal));
        }
        return objectList;
    }    
}
