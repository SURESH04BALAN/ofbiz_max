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
public class PicklistItemAndBin implements GenericValueObjectInterface {
public static final String module =PicklistItemAndBin.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PicklistItemAndBin(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PicklistItemAndBin () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"orderItemSeqId","Order Item Seq Id"},
{"primaryShipGroupSeqId","Primary Ship Group Seq Id"},
{"picklistBinId","Picklist Bin Id"},
{"picklistId","Picklist Id"},
{"itemStatusId","Item Status Id"},
{"binLocationNumber","Bin Location Number"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"primaryOrderId","Primary Order Id"},
{"orderId","Order Id"},
};
protected void initObject(){
this.orderItemSeqId = "";
this.primaryShipGroupSeqId = "";
this.picklistBinId = "";
this.picklistId = "";
this.itemStatusId = "";
this.binLocationNumber = new Long(0);
this.shipGroupSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.primaryOrderId = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.primaryShipGroupSeqId = (java.lang.String) genVal.get("primaryShipGroupSeqId");
this.picklistBinId = (java.lang.String) genVal.get("picklistBinId");
this.picklistId = (java.lang.String) genVal.get("picklistId");
this.itemStatusId = (java.lang.String) genVal.get("itemStatusId");
this.binLocationNumber = (java.lang.Long) genVal.get("binLocationNumber");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("primaryShipGroupSeqId",OrderMaxUtility.getValidEntityString(this.primaryShipGroupSeqId));
val.set("picklistBinId",OrderMaxUtility.getValidEntityString(this.picklistBinId));
val.set("picklistId",OrderMaxUtility.getValidEntityString(this.picklistId));
val.set("itemStatusId",OrderMaxUtility.getValidEntityString(this.itemStatusId));
val.set("binLocationNumber",this.binLocationNumber);
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("primaryShipGroupSeqId",this.primaryShipGroupSeqId);
valueMap.put("picklistBinId",this.picklistBinId);
valueMap.put("picklistId",this.picklistId);
valueMap.put("itemStatusId",this.itemStatusId);
valueMap.put("binLocationNumber",this.binLocationNumber);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("primaryOrderId",this.primaryOrderId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PicklistItemAndBin");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
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
private java.lang.Long binLocationNumber;
public java.lang.Long getbinLocationNumber() {
return binLocationNumber;
}
public void setbinLocationNumber( java.lang.Long binLocationNumber ) {
this.binLocationNumber = binLocationNumber;
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
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PicklistItemAndBin> objectList = new ArrayList<PicklistItemAndBin>();
        for (GenericValue genVal : genList) {
            objectList.add(new PicklistItemAndBin(genVal));
        }
        return objectList;
    }    
}
