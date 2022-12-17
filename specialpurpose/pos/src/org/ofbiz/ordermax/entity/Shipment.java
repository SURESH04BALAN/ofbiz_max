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
public class Shipment implements GenericValueObjectInterface {
public static final String module =Shipment.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public Shipment(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public Shipment () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"addtlShippingChargeDesc","Addtl Shipping Charge Desc"},
{"originFacilityId","Origin Facility Id"},
{"primaryReturnId","Primary Return Id"},
{"primaryShipGroupSeqId","Primary Ship Group Seq Id"},
{"destinationTelecomNumberId","Destination Telecom Number Id"},
{"originContactMechId","Origin Contact Mech Id"},
{"estimatedArrivalDate","Estimated Arrival Date"},
{"partyIdFrom","Party Id From"},
{"statusId","Status Id"},
{"shipmentTypeId","Shipment Type Id"},
{"originTelecomNumberId","Origin Telecom Number Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"estimatedShipCost","Estimated Ship Cost"},
{"latestCancelDate","Latest Cancel Date"},
{"createdDate","Created Date"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"handlingInstructions","Handling Instructions"},
{"partyIdTo","Party Id To"},
{"currencyUomId","Currency Uom Id"},
{"picklistBinId","Picklist Bin Id"},
{"primaryOrderId","Primary Order Id"},
{"estimatedShipWorkEffId","Estimated Ship Work Eff Id"},
{"additionalShippingCharge","Additional Shipping Charge"},
{"estimatedReadyDate","Estimated Ready Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"estimatedArrivalWorkEffId","Estimated Arrival Work Eff Id"},
{"lastModifiedDate","Last Modified Date"},
{"destinationFacilityId","Destination Facility Id"},
{"estimatedShipDate","Estimated Ship Date"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"destinationContactMechId","Destination Contact Mech Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.addtlShippingChargeDesc = "";
this.originFacilityId = "";
this.primaryReturnId = "";
this.primaryShipGroupSeqId = "";
this.destinationTelecomNumberId = "";
this.originContactMechId = "";
this.estimatedArrivalDate = UtilDateTime.nowTimestamp();
this.partyIdFrom = "";
this.statusId = "";
this.shipmentTypeId = "";
this.originTelecomNumberId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.estimatedShipCost = java.math.BigDecimal.ZERO;
this.latestCancelDate = UtilDateTime.nowTimestamp();
this.createdDate = UtilDateTime.nowTimestamp();
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.handlingInstructions = "";
this.partyIdTo = "";
this.currencyUomId = "";
this.picklistBinId = "";
this.primaryOrderId = "";
this.estimatedShipWorkEffId = "";
this.additionalShippingCharge = "";
this.estimatedReadyDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.estimatedArrivalWorkEffId = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.destinationFacilityId = "";
this.estimatedShipDate = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.destinationContactMechId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.addtlShippingChargeDesc = (java.lang.String) genVal.get("addtlShippingChargeDesc");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.primaryReturnId = (java.lang.String) genVal.get("primaryReturnId");
this.primaryShipGroupSeqId = (java.lang.String) genVal.get("primaryShipGroupSeqId");
this.destinationTelecomNumberId = (java.lang.String) genVal.get("destinationTelecomNumberId");
this.originContactMechId = (java.lang.String) genVal.get("originContactMechId");
this.estimatedArrivalDate = (java.sql.Timestamp) genVal.get("estimatedArrivalDate");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.statusId = (java.lang.String) genVal.get("statusId");
this.shipmentTypeId = (java.lang.String) genVal.get("shipmentTypeId");
this.originTelecomNumberId = (java.lang.String) genVal.get("originTelecomNumberId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.estimatedShipCost = (java.math.BigDecimal) genVal.get("estimatedShipCost");
this.latestCancelDate = (java.sql.Timestamp) genVal.get("latestCancelDate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.handlingInstructions = (java.lang.String) genVal.get("handlingInstructions");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.picklistBinId = (java.lang.String) genVal.get("picklistBinId");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
this.estimatedShipWorkEffId = (java.lang.String) genVal.get("estimatedShipWorkEffId");
this.additionalShippingCharge = (java.lang.String) genVal.get("additionalShippingCharge");
this.estimatedReadyDate = (java.sql.Timestamp) genVal.get("estimatedReadyDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.estimatedArrivalWorkEffId = (java.lang.String) genVal.get("estimatedArrivalWorkEffId");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.destinationFacilityId = (java.lang.String) genVal.get("destinationFacilityId");
this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.destinationContactMechId = (java.lang.String) genVal.get("destinationContactMechId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("addtlShippingChargeDesc",OrderMaxUtility.getValidEntityString(this.addtlShippingChargeDesc));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("primaryReturnId",OrderMaxUtility.getValidEntityString(this.primaryReturnId));
val.set("primaryShipGroupSeqId",OrderMaxUtility.getValidEntityString(this.primaryShipGroupSeqId));
val.set("destinationTelecomNumberId",OrderMaxUtility.getValidEntityString(this.destinationTelecomNumberId));
val.set("originContactMechId",OrderMaxUtility.getValidEntityString(this.originContactMechId));
val.set("estimatedArrivalDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedArrivalDate));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("shipmentTypeId",OrderMaxUtility.getValidEntityString(this.shipmentTypeId));
val.set("originTelecomNumberId",OrderMaxUtility.getValidEntityString(this.originTelecomNumberId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("estimatedShipCost",OrderMaxUtility.getValidEntityBigDecimal(this.estimatedShipCost));
val.set("latestCancelDate",OrderMaxUtility.getValidEntityTimestamp(this.latestCancelDate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("handlingInstructions",OrderMaxUtility.getValidEntityString(this.handlingInstructions));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("picklistBinId",OrderMaxUtility.getValidEntityString(this.picklistBinId));
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
val.set("estimatedShipWorkEffId",OrderMaxUtility.getValidEntityString(this.estimatedShipWorkEffId));
val.set("additionalShippingCharge",OrderMaxUtility.getValidEntityString(this.additionalShippingCharge));
val.set("estimatedReadyDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedReadyDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("estimatedArrivalWorkEffId",OrderMaxUtility.getValidEntityString(this.estimatedArrivalWorkEffId));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("destinationFacilityId",OrderMaxUtility.getValidEntityString(this.destinationFacilityId));
val.set("estimatedShipDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("destinationContactMechId",OrderMaxUtility.getValidEntityString(this.destinationContactMechId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("addtlShippingChargeDesc",this.addtlShippingChargeDesc);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("primaryReturnId",this.primaryReturnId);
valueMap.put("primaryShipGroupSeqId",this.primaryShipGroupSeqId);
valueMap.put("destinationTelecomNumberId",this.destinationTelecomNumberId);
valueMap.put("originContactMechId",this.originContactMechId);
valueMap.put("estimatedArrivalDate",this.estimatedArrivalDate);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("statusId",this.statusId);
valueMap.put("shipmentTypeId",this.shipmentTypeId);
valueMap.put("originTelecomNumberId",this.originTelecomNumberId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("estimatedShipCost",this.estimatedShipCost);
valueMap.put("latestCancelDate",this.latestCancelDate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("handlingInstructions",this.handlingInstructions);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("picklistBinId",this.picklistBinId);
valueMap.put("primaryOrderId",this.primaryOrderId);
valueMap.put("estimatedShipWorkEffId",this.estimatedShipWorkEffId);
valueMap.put("additionalShippingCharge",this.additionalShippingCharge);
valueMap.put("estimatedReadyDate",this.estimatedReadyDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("estimatedArrivalWorkEffId",this.estimatedArrivalWorkEffId);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("destinationFacilityId",this.destinationFacilityId);
valueMap.put("estimatedShipDate",this.estimatedShipDate);
valueMap.put("destinationContactMechId",this.destinationContactMechId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("Shipment");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String addtlShippingChargeDesc;
public java.lang.String getaddtlShippingChargeDesc() {
return addtlShippingChargeDesc;
}
public void setaddtlShippingChargeDesc( java.lang.String addtlShippingChargeDesc ) {
this.addtlShippingChargeDesc = addtlShippingChargeDesc;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String primaryReturnId;
public java.lang.String getprimaryReturnId() {
return primaryReturnId;
}
public void setprimaryReturnId( java.lang.String primaryReturnId ) {
this.primaryReturnId = primaryReturnId;
}
private java.lang.String primaryShipGroupSeqId;
public java.lang.String getprimaryShipGroupSeqId() {
return primaryShipGroupSeqId;
}
public void setprimaryShipGroupSeqId( java.lang.String primaryShipGroupSeqId ) {
this.primaryShipGroupSeqId = primaryShipGroupSeqId;
}
private java.lang.String destinationTelecomNumberId;
public java.lang.String getdestinationTelecomNumberId() {
return destinationTelecomNumberId;
}
public void setdestinationTelecomNumberId( java.lang.String destinationTelecomNumberId ) {
this.destinationTelecomNumberId = destinationTelecomNumberId;
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
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
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
private java.lang.String originTelecomNumberId;
public java.lang.String getoriginTelecomNumberId() {
return originTelecomNumberId;
}
public void setoriginTelecomNumberId( java.lang.String originTelecomNumberId ) {
this.originTelecomNumberId = originTelecomNumberId;
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
private java.math.BigDecimal estimatedShipCost;
public java.math.BigDecimal getestimatedShipCost() {
return estimatedShipCost;
}
public void setestimatedShipCost( java.math.BigDecimal estimatedShipCost ) {
this.estimatedShipCost = estimatedShipCost;
}
private java.sql.Timestamp latestCancelDate;
public java.sql.Timestamp getlatestCancelDate() {
return latestCancelDate;
}
public void setlatestCancelDate( java.sql.Timestamp latestCancelDate ) {
this.latestCancelDate = latestCancelDate;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String handlingInstructions;
public java.lang.String gethandlingInstructions() {
return handlingInstructions;
}
public void sethandlingInstructions( java.lang.String handlingInstructions ) {
this.handlingInstructions = handlingInstructions;
}
private java.lang.String partyIdTo;
public java.lang.String getpartyIdTo() {
return partyIdTo;
}
public void setpartyIdTo( java.lang.String partyIdTo ) {
this.partyIdTo = partyIdTo;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String picklistBinId;
public java.lang.String getpicklistBinId() {
return picklistBinId;
}
public void setpicklistBinId( java.lang.String picklistBinId ) {
this.picklistBinId = picklistBinId;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
}
private java.lang.String estimatedShipWorkEffId;
public java.lang.String getestimatedShipWorkEffId() {
return estimatedShipWorkEffId;
}
public void setestimatedShipWorkEffId( java.lang.String estimatedShipWorkEffId ) {
this.estimatedShipWorkEffId = estimatedShipWorkEffId;
}
private java.lang.String additionalShippingCharge;
public java.lang.String getadditionalShippingCharge() {
return additionalShippingCharge;
}
public void setadditionalShippingCharge( java.lang.String additionalShippingCharge ) {
this.additionalShippingCharge = additionalShippingCharge;
}
private java.sql.Timestamp estimatedReadyDate;
public java.sql.Timestamp getestimatedReadyDate() {
return estimatedReadyDate;
}
public void setestimatedReadyDate( java.sql.Timestamp estimatedReadyDate ) {
this.estimatedReadyDate = estimatedReadyDate;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String estimatedArrivalWorkEffId;
public java.lang.String getestimatedArrivalWorkEffId() {
return estimatedArrivalWorkEffId;
}
public void setestimatedArrivalWorkEffId( java.lang.String estimatedArrivalWorkEffId ) {
this.estimatedArrivalWorkEffId = estimatedArrivalWorkEffId;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String destinationFacilityId;
public java.lang.String getdestinationFacilityId() {
return destinationFacilityId;
}
public void setdestinationFacilityId( java.lang.String destinationFacilityId ) {
this.destinationFacilityId = destinationFacilityId;
}
private java.sql.Timestamp estimatedShipDate;
public java.sql.Timestamp getestimatedShipDate() {
return estimatedShipDate;
}
public void setestimatedShipDate( java.sql.Timestamp estimatedShipDate ) {
this.estimatedShipDate = estimatedShipDate;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String destinationContactMechId;
public java.lang.String getdestinationContactMechId() {
return destinationContactMechId;
}
public void setdestinationContactMechId( java.lang.String destinationContactMechId ) {
this.destinationContactMechId = destinationContactMechId;
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
        Collection<Shipment> objectList = new ArrayList<Shipment>();
        for (GenericValue genVal : genList) {
            objectList.add(new Shipment(genVal));
        }
        return objectList;
    }    
}
