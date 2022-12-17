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
public class ShipmentAndItem implements GenericValueObjectInterface {
public static final String module =ShipmentAndItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentAndItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentAndItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"originFacilityId","Origin Facility Id"},
{"handlingInstructions","Handling Instructions"},
{"shipmentContentDescription","Shipment Content Description"},
{"partyIdTo","Party Id To"},
{"originContactMechId","Origin Contact Mech Id"},
{"estimatedArrivalDate","Estimated Arrival Date"},
{"primaryOrderId","Primary Order Id"},
{"partyIdFrom","Party Id From"},
{"productId","Product Id"},
{"statusId","Status Id"},
{"shipmentTypeId","Shipment Type Id"},
{"estimatedReadyDate","Estimated Ready Date"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"shipmentId","Shipment Id"},
{"quantity","Quantity"},
{"destinationFacilityId","Destination Facility Id"},
{"estimatedShipCost","Estimated Ship Cost"},
{"estimatedShipDate","Estimated Ship Date"},
{"latestCancelDate","Latest Cancel Date"},
{"destinationContactMechId","Destination Contact Mech Id"},
};
protected void initObject(){
this.originFacilityId = "";
this.handlingInstructions = "";
this.shipmentContentDescription = "";
this.partyIdTo = "";
this.originContactMechId = "";
this.estimatedArrivalDate = UtilDateTime.nowTimestamp();
this.primaryOrderId = "";
this.partyIdFrom = "";
this.productId = "";
this.statusId = "";
this.shipmentTypeId = "";
this.estimatedReadyDate = UtilDateTime.nowTimestamp();
this.shipmentItemSeqId = "";
this.shipmentId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.destinationFacilityId = "";
this.estimatedShipCost = java.math.BigDecimal.ZERO;
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.latestCancelDate = UtilDateTime.nowTimestamp();
this.destinationContactMechId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.handlingInstructions = (java.lang.String) genVal.get("handlingInstructions");
this.shipmentContentDescription = (java.lang.String) genVal.get("shipmentContentDescription");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.originContactMechId = (java.lang.String) genVal.get("originContactMechId");
this.estimatedArrivalDate = (java.sql.Timestamp) genVal.get("estimatedArrivalDate");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.productId = (java.lang.String) genVal.get("productId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.shipmentTypeId = (java.lang.String) genVal.get("shipmentTypeId");
this.estimatedReadyDate = (java.sql.Timestamp) genVal.get("estimatedReadyDate");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.destinationFacilityId = (java.lang.String) genVal.get("destinationFacilityId");
this.estimatedShipCost = (java.math.BigDecimal) genVal.get("estimatedShipCost");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.latestCancelDate = (java.sql.Timestamp) genVal.get("latestCancelDate");
this.destinationContactMechId = (java.lang.String) genVal.get("destinationContactMechId");
}
protected void getGenericValue(GenericValue val) {
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("handlingInstructions",OrderMaxUtility.getValidEntityString(this.handlingInstructions));
val.set("shipmentContentDescription",OrderMaxUtility.getValidEntityString(this.shipmentContentDescription));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("originContactMechId",OrderMaxUtility.getValidEntityString(this.originContactMechId));
val.set("estimatedArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedArrivalDate));
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("shipmentTypeId",OrderMaxUtility.getValidEntityString(this.shipmentTypeId));
val.set("estimatedReadyDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedReadyDate));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("destinationFacilityId",OrderMaxUtility.getValidEntityString(this.destinationFacilityId));
val.set("estimatedShipCost",OrderMaxUtility.getValidEntityBigDecimal(this.estimatedShipCost));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("latestCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.latestCancelDate));
val.set("destinationContactMechId",OrderMaxUtility.getValidEntityString(this.destinationContactMechId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("handlingInstructions",this.handlingInstructions);
valueMap.put("shipmentContentDescription",this.shipmentContentDescription);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("originContactMechId",this.originContactMechId);
valueMap.put("estimatedArrivalDate",this.estimatedArrivalDate);
valueMap.put("primaryOrderId",this.primaryOrderId);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("productId",this.productId);
valueMap.put("statusId",this.statusId);
valueMap.put("shipmentTypeId",this.shipmentTypeId);
valueMap.put("estimatedReadyDate",this.estimatedReadyDate);
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("quantity",this.quantity);
valueMap.put("destinationFacilityId",this.destinationFacilityId);
valueMap.put("estimatedShipCost",this.estimatedShipCost);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("latestCancelDate",this.latestCancelDate);
valueMap.put("destinationContactMechId",this.destinationContactMechId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentAndItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String handlingInstructions;
public java.lang.String gethandlingInstructions() {
return handlingInstructions;
}
public void sethandlingInstructions( java.lang.String handlingInstructions ) {
this.handlingInstructions = handlingInstructions;
}
private java.lang.String shipmentContentDescription;
public java.lang.String getshipmentContentDescription() {
return shipmentContentDescription;
}
public void setshipmentContentDescription( java.lang.String shipmentContentDescription ) {
this.shipmentContentDescription = shipmentContentDescription;
}
private java.lang.String partyIdTo;
public java.lang.String getpartyIdTo() {
return partyIdTo;
}
public void setpartyIdTo( java.lang.String partyIdTo ) {
this.partyIdTo = partyIdTo;
}
private java.lang.String originContactMechId;
public java.lang.String getoriginContactMechId() {
return originContactMechId;
}
public void setoriginContactMechId( java.lang.String originContactMechId ) {
this.originContactMechId = originContactMechId;
}
private java.sql.Timestamp estimatedArrivalDate;
public java.sql.Timestamp getestimatedArrivalDate() {
return estimatedArrivalDate;
}
public void setestimatedArrivalDate( java.sql.Timestamp estimatedArrivalDate ) {
this.estimatedArrivalDate = estimatedArrivalDate;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String shipmentTypeId;
public java.lang.String getshipmentTypeId() {
return shipmentTypeId;
}
public void setshipmentTypeId( java.lang.String shipmentTypeId ) {
this.shipmentTypeId = shipmentTypeId;
}
private java.sql.Timestamp estimatedReadyDate;
public java.sql.Timestamp getestimatedReadyDate() {
return estimatedReadyDate;
}
public void setestimatedReadyDate( java.sql.Timestamp estimatedReadyDate ) {
this.estimatedReadyDate = estimatedReadyDate;
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
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String destinationFacilityId;
public java.lang.String getdestinationFacilityId() {
return destinationFacilityId;
}
public void setdestinationFacilityId( java.lang.String destinationFacilityId ) {
this.destinationFacilityId = destinationFacilityId;
}
private java.math.BigDecimal estimatedShipCost;
public java.math.BigDecimal getestimatedShipCost() {
return estimatedShipCost;
}
public void setestimatedShipCost( java.math.BigDecimal estimatedShipCost ) {
this.estimatedShipCost = estimatedShipCost;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.sql.Timestamp latestCancelDate;
public java.sql.Timestamp getlatestCancelDate() {
return latestCancelDate;
}
public void setlatestCancelDate( java.sql.Timestamp latestCancelDate ) {
this.latestCancelDate = latestCancelDate;
}
private java.lang.String destinationContactMechId;
public java.lang.String getdestinationContactMechId() {
return destinationContactMechId;
}
public void setdestinationContactMechId( java.lang.String destinationContactMechId ) {
this.destinationContactMechId = destinationContactMechId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentAndItem> objectList = new ArrayList<ShipmentAndItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentAndItem(genVal));
        }
        return objectList;
    }    
}
