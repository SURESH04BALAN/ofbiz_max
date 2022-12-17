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
public class PackedQtyVsOrderItemQuantity implements GenericValueObjectInterface {
public static final String module =PackedQtyVsOrderItemQuantity.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PackedQtyVsOrderItemQuantity(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PackedQtyVsOrderItemQuantity () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"packedQuantity","Packed Quantity"},
{"orderedQuantity","Ordered Quantity"},
{"shipmentId","Shipment Id"},
{"issuedQuantity","Issued Quantity"},
{"orderId","Order Id"},
};
protected void initObject(){
this.shipmentPackageSeqId = "";
this.orderItemSeqId = "";
this.packedQuantity = java.math.BigDecimal.ZERO;
this.orderedQuantity = java.math.BigDecimal.ZERO;
this.shipmentId = "";
this.issuedQuantity = java.math.BigDecimal.ZERO;
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.packedQuantity = (java.math.BigDecimal) genVal.get("packedQuantity");
this.orderedQuantity = (java.math.BigDecimal) genVal.get("orderedQuantity");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.issuedQuantity = (java.math.BigDecimal) genVal.get("issuedQuantity");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("packedQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.packedQuantity));
val.set("orderedQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.orderedQuantity));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("issuedQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.issuedQuantity));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("packedQuantity",this.packedQuantity);
valueMap.put("orderedQuantity",this.orderedQuantity);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("issuedQuantity",this.issuedQuantity);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PackedQtyVsOrderItemQuantity");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.math.BigDecimal packedQuantity;
public java.math.BigDecimal getpackedQuantity() {
return packedQuantity;
}
public void setpackedQuantity( java.math.BigDecimal packedQuantity ) {
this.packedQuantity = packedQuantity;
}
private java.math.BigDecimal orderedQuantity;
public java.math.BigDecimal getorderedQuantity() {
return orderedQuantity;
}
public void setorderedQuantity( java.math.BigDecimal orderedQuantity ) {
this.orderedQuantity = orderedQuantity;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PackedQtyVsOrderItemQuantity> objectList = new ArrayList<PackedQtyVsOrderItemQuantity>();
        for (GenericValue genVal : genList) {
            objectList.add(new PackedQtyVsOrderItemQuantity(genVal));
        }
        return objectList;
    }    
}
