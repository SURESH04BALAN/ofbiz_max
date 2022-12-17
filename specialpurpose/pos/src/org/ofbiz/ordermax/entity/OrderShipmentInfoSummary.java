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
public class OrderShipmentInfoSummary implements GenericValueObjectInterface {
public static final String module =OrderShipmentInfoSummary.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderShipmentInfoSummary(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderShipmentInfoSummary () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"orderItemSeqId","Order Item Seq Id"},
{"carrierPartyId","Carrier Party Id"},
{"boxNumber","Box Number"},
{"shipmentId","Shipment Id"},
{"trackingCode","Tracking Code"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"shipGroupSeqId","Ship Group Seq Id"},
{"actualStartDate","Actual Start Date"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"orderId","Order Id"},
};
protected void initObject(){
this.shipmentPackageSeqId = "";
this.orderItemSeqId = "";
this.carrierPartyId = "";
this.boxNumber = "";
this.shipmentId = "";
this.trackingCode = "";
this.shipmentRouteSegmentId = "";
this.shipGroupSeqId = "";
this.actualStartDate = UtilDateTime.nowTimestamp();
this.shipmentMethodTypeId = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.boxNumber = (java.lang.String) genVal.get("boxNumber");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.trackingCode = (java.lang.String) genVal.get("trackingCode");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
this.actualStartDate = (java.sql.Timestamp) genVal.get("actualStartDate");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("boxNumber",OrderMaxUtility.getValidEntityString(this.boxNumber));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("trackingCode",OrderMaxUtility.getValidEntityString(this.trackingCode));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("shipGroupSeqId",OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
val.set("actualStartDate",OrderMaxUtility.getValidEntityTimestamp(this.actualStartDate));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("boxNumber",this.boxNumber);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("trackingCode",this.trackingCode);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("shipGroupSeqId",this.shipGroupSeqId);
valueMap.put("actualStartDate",this.actualStartDate);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderShipmentInfoSummary");
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
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String boxNumber;
public java.lang.String getboxNumber() {
return boxNumber;
}
public void setboxNumber( java.lang.String boxNumber ) {
this.boxNumber = boxNumber;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.lang.String trackingCode;
public java.lang.String gettrackingCode() {
return trackingCode;
}
public void settrackingCode( java.lang.String trackingCode ) {
this.trackingCode = trackingCode;
}
private java.lang.String shipmentRouteSegmentId;
public java.lang.String getshipmentRouteSegmentId() {
return shipmentRouteSegmentId;
}
public void setshipmentRouteSegmentId( java.lang.String shipmentRouteSegmentId ) {
this.shipmentRouteSegmentId = shipmentRouteSegmentId;
}
private java.lang.String shipGroupSeqId;
public java.lang.String getshipGroupSeqId() {
return shipGroupSeqId;
}
public void setshipGroupSeqId( java.lang.String shipGroupSeqId ) {
this.shipGroupSeqId = shipGroupSeqId;
}
private java.sql.Timestamp actualStartDate;
public java.sql.Timestamp getactualStartDate() {
return actualStartDate;
}
public void setactualStartDate( java.sql.Timestamp actualStartDate ) {
this.actualStartDate = actualStartDate;
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
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderShipmentInfoSummary> objectList = new ArrayList<OrderShipmentInfoSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderShipmentInfoSummary(genVal));
        }
        return objectList;
    }    
}
