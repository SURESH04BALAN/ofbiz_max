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
public class ShipmentPackageRouteDetail implements GenericValueObjectInterface {
public static final String module =ShipmentPackageRouteDetail.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentPackageRouteDetail(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentPackageRouteDetail () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"carrierPartyId","Carrier Party Id"},
{"carrierServiceStatusId","Carrier Service Status Id"},
{"shipmentId","Shipment Id"},
{"trackingCode","Tracking Code"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"labelPrinted","Label Printed"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"primaryOrderId","Primary Order Id"},
};
protected void initObject(){
this.statusId = "";
this.shipmentPackageSeqId = "";
this.carrierPartyId = "";
this.carrierServiceStatusId = "";
this.shipmentId = "";
this.trackingCode = "";
this.shipmentRouteSegmentId = "";
this.labelPrinted = "";
this.shipmentMethodTypeId = "";
this.primaryOrderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.carrierServiceStatusId = (java.lang.String) genVal.get("carrierServiceStatusId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.trackingCode = (java.lang.String) genVal.get("trackingCode");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.labelPrinted = (java.lang.String) genVal.get("labelPrinted");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("carrierServiceStatusId",OrderMaxUtility.getValidEntityString(this.carrierServiceStatusId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("trackingCode",OrderMaxUtility.getValidEntityString(this.trackingCode));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("labelPrinted",OrderMaxUtility.getValidEntityString(this.labelPrinted));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("carrierServiceStatusId",this.carrierServiceStatusId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("trackingCode",this.trackingCode);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("labelPrinted",this.labelPrinted);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("primaryOrderId",this.primaryOrderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentPackageRouteDetail");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String carrierServiceStatusId;
public java.lang.String getcarrierServiceStatusId() {
return carrierServiceStatusId;
}
public void setcarrierServiceStatusId( java.lang.String carrierServiceStatusId ) {
this.carrierServiceStatusId = carrierServiceStatusId;
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
private java.lang.String labelPrinted;
public java.lang.String getlabelPrinted() {
return labelPrinted;
}
public void setlabelPrinted( java.lang.String labelPrinted ) {
this.labelPrinted = labelPrinted;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentPackageRouteDetail> objectList = new ArrayList<ShipmentPackageRouteDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentPackageRouteDetail(genVal));
        }
        return objectList;
    }    
}
