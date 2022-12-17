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
public class ShipmentItem implements GenericValueObjectInterface {
public static final String module =ShipmentItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentContentDescription","Shipment Content Description"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"quantity","Quantity"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"productId","Product Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.shipmentItemSeqId = "";
this.shipmentContentDescription = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentContentDescription = (java.lang.String) genVal.get("shipmentContentDescription");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentContentDescription",OrderMaxUtility.getValidEntityString(this.shipmentContentDescription));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentContentDescription",this.shipmentContentDescription);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("quantity",this.quantity);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentItem");
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
private java.lang.String shipmentItemSeqId;
public java.lang.String getshipmentItemSeqId() {
return shipmentItemSeqId;
}
public void setshipmentItemSeqId( java.lang.String shipmentItemSeqId ) {
this.shipmentItemSeqId = shipmentItemSeqId;
}
private java.lang.String shipmentContentDescription;
public java.lang.String getshipmentContentDescription() {
return shipmentContentDescription;
}
public void setshipmentContentDescription( java.lang.String shipmentContentDescription ) {
this.shipmentContentDescription = shipmentContentDescription;
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
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentItem> objectList = new ArrayList<ShipmentItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentItem(genVal));
        }
        return objectList;
    }    
}
