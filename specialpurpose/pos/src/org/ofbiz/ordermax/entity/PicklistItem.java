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
public class PicklistItem implements GenericValueObjectInterface {
public static final String module =PicklistItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PicklistItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PicklistItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"orderItemSeqId","Order Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"picklistBinId","Picklist Bin Id"},
{"itemStatusId","Item Status Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"quantity","Quantity"},
{"inventoryItemId","Inventory Item Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"orderId","Order Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.orderItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.picklistBinId = "";
this.itemStatusId = "";
this.shipGroupSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.picklistBinId = (java.lang.String) genVal.get("picklistBinId");
this.itemStatusId = (java.lang.String) genVal.get("itemStatusId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("picklistBinId",OrderMaxUtility.getValidEntityString(this.picklistBinId));
val.set("itemStatusId",OrderMaxUtility.getValidEntityString(this.itemStatusId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("picklistBinId",this.picklistBinId);
valueMap.put("itemStatusId",this.itemStatusId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PicklistItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String picklistBinId;
public java.lang.String getpicklistBinId() {
return picklistBinId;
}
public void setpicklistBinId( java.lang.String picklistBinId ) {
this.picklistBinId = picklistBinId;
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
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<PicklistItem> objectList = new ArrayList<PicklistItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new PicklistItem(genVal));
        }
        return objectList;
    }    
}
