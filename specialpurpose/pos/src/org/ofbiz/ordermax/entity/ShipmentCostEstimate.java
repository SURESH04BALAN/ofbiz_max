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
public class ShipmentCostEstimate implements GenericValueObjectInterface {
public static final String module =ShipmentCostEstimate.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentCostEstimate(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentCostEstimate () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"priceUomId","Price Uom Id"},
{"quantityUomId","Quantity Uom Id"},
{"orderItemFlatPrice","Order Item Flat Price"},
{"productStoreId","Product Store Id"},
{"weightBreakId","Weight Break Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentCostEstimateId","Shipment Cost Estimate Id"},
{"oversizePrice","Oversize Price"},
{"createdStamp","Created Stamp"},
{"quantityBreakId","Quantity Break Id"},
{"shippingPricePercent","Shipping Price Percent"},
{"geoIdFrom","Geo Id From"},
{"priceBreakId","Price Break Id"},
{"featurePrice","Feature Price"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"priceUnitPrice","Price Unit Price"},
{"weightUnitPrice","Weight Unit Price"},
{"carrierPartyId","Carrier Party Id"},
{"weightUomId","Weight Uom Id"},
{"oversizeUnit","Oversize Unit"},
{"featurePercent","Feature Percent"},
{"carrierRoleTypeId","Carrier Role Type Id"},
{"productStoreShipMethId","Product Store Ship Meth Id"},
{"geoIdTo","Geo Id To"},
{"productFeatureGroupId","Product Feature Group Id"},
{"orderFlatPrice","Order Flat Price"},
{"roleTypeId","Role Type Id"},
{"partyId","Party Id"},
{"orderPricePercent","Order Price Percent"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"quantityUnitPrice","Quantity Unit Price"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.priceUomId = "";
this.quantityUomId = "";
this.orderItemFlatPrice = java.math.BigDecimal.ZERO;
this.productStoreId = "";
this.weightBreakId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentCostEstimateId = "";
this.oversizePrice = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.quantityBreakId = "";
this.shippingPricePercent = "";
this.geoIdFrom = "";
this.priceBreakId = "";
this.featurePrice = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.priceUnitPrice = "";
this.weightUnitPrice = "";
this.carrierPartyId = "";
this.weightUomId = "";
this.oversizeUnit = "";
this.featurePercent = "";
this.carrierRoleTypeId = "";
this.productStoreShipMethId = "";
this.geoIdTo = "";
this.productFeatureGroupId = "";
this.orderFlatPrice = java.math.BigDecimal.ZERO;
this.roleTypeId = "";
this.partyId = "";
this.orderPricePercent = java.math.BigDecimal.ZERO;
this.shipmentMethodTypeId = "";
this.quantityUnitPrice = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.priceUomId = (java.lang.String) genVal.get("priceUomId");
this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
this.orderItemFlatPrice = (java.math.BigDecimal) genVal.get("orderItemFlatPrice");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.weightBreakId = (java.lang.String) genVal.get("weightBreakId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentCostEstimateId = (java.lang.String) genVal.get("shipmentCostEstimateId");
this.oversizePrice = (java.lang.String) genVal.get("oversizePrice");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.quantityBreakId = (java.lang.String) genVal.get("quantityBreakId");
this.shippingPricePercent = (java.lang.String) genVal.get("shippingPricePercent");
this.geoIdFrom = (java.lang.String) genVal.get("geoIdFrom");
this.priceBreakId = (java.lang.String) genVal.get("priceBreakId");
this.featurePrice = (java.lang.String) genVal.get("featurePrice");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.priceUnitPrice = (java.lang.String) genVal.get("priceUnitPrice");
this.weightUnitPrice = (java.lang.String) genVal.get("weightUnitPrice");
this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
this.weightUomId = (java.lang.String) genVal.get("weightUomId");
this.oversizeUnit = (java.lang.String) genVal.get("oversizeUnit");
this.featurePercent = (java.lang.String) genVal.get("featurePercent");
this.carrierRoleTypeId = (java.lang.String) genVal.get("carrierRoleTypeId");
this.productStoreShipMethId = (java.lang.String) genVal.get("productStoreShipMethId");
this.geoIdTo = (java.lang.String) genVal.get("geoIdTo");
this.productFeatureGroupId = (java.lang.String) genVal.get("productFeatureGroupId");
this.orderFlatPrice = (java.math.BigDecimal) genVal.get("orderFlatPrice");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.orderPricePercent = (java.math.BigDecimal) genVal.get("orderPricePercent");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.quantityUnitPrice = (java.lang.String) genVal.get("quantityUnitPrice");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("priceUomId",OrderMaxUtility.getValidEntityString(this.priceUomId));
val.set("quantityUomId",OrderMaxUtility.getValidEntityString(this.quantityUomId));
val.set("orderItemFlatPrice",OrderMaxUtility.getValidEntityBigDecimal(this.orderItemFlatPrice));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("weightBreakId",OrderMaxUtility.getValidEntityString(this.weightBreakId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentCostEstimateId",OrderMaxUtility.getValidEntityString(this.shipmentCostEstimateId));
val.set("oversizePrice",OrderMaxUtility.getValidEntityString(this.oversizePrice));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("quantityBreakId",OrderMaxUtility.getValidEntityString(this.quantityBreakId));
val.set("shippingPricePercent",OrderMaxUtility.getValidEntityString(this.shippingPricePercent));
val.set("geoIdFrom",OrderMaxUtility.getValidEntityString(this.geoIdFrom));
val.set("priceBreakId",OrderMaxUtility.getValidEntityString(this.priceBreakId));
val.set("featurePrice",OrderMaxUtility.getValidEntityString(this.featurePrice));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("priceUnitPrice",OrderMaxUtility.getValidEntityString(this.priceUnitPrice));
val.set("weightUnitPrice",OrderMaxUtility.getValidEntityString(this.weightUnitPrice));
val.set("carrierPartyId",OrderMaxUtility.getValidEntityString(this.carrierPartyId));
val.set("weightUomId",OrderMaxUtility.getValidEntityString(this.weightUomId));
val.set("oversizeUnit",OrderMaxUtility.getValidEntityString(this.oversizeUnit));
val.set("featurePercent",OrderMaxUtility.getValidEntityString(this.featurePercent));
val.set("carrierRoleTypeId",OrderMaxUtility.getValidEntityString(this.carrierRoleTypeId));
val.set("productStoreShipMethId",OrderMaxUtility.getValidEntityString(this.productStoreShipMethId));
val.set("geoIdTo",OrderMaxUtility.getValidEntityString(this.geoIdTo));
val.set("productFeatureGroupId",OrderMaxUtility.getValidEntityString(this.productFeatureGroupId));
val.set("orderFlatPrice",OrderMaxUtility.getValidEntityBigDecimal(this.orderFlatPrice));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("orderPricePercent",OrderMaxUtility.getValidEntityBigDecimal(this.orderPricePercent));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("quantityUnitPrice",OrderMaxUtility.getValidEntityString(this.quantityUnitPrice));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("priceUomId",this.priceUomId);
valueMap.put("quantityUomId",this.quantityUomId);
valueMap.put("orderItemFlatPrice",this.orderItemFlatPrice);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("weightBreakId",this.weightBreakId);
valueMap.put("shipmentCostEstimateId",this.shipmentCostEstimateId);
valueMap.put("oversizePrice",this.oversizePrice);
valueMap.put("quantityBreakId",this.quantityBreakId);
valueMap.put("shippingPricePercent",this.shippingPricePercent);
valueMap.put("geoIdFrom",this.geoIdFrom);
valueMap.put("priceBreakId",this.priceBreakId);
valueMap.put("featurePrice",this.featurePrice);
valueMap.put("priceUnitPrice",this.priceUnitPrice);
valueMap.put("weightUnitPrice",this.weightUnitPrice);
valueMap.put("carrierPartyId",this.carrierPartyId);
valueMap.put("weightUomId",this.weightUomId);
valueMap.put("oversizeUnit",this.oversizeUnit);
valueMap.put("featurePercent",this.featurePercent);
valueMap.put("carrierRoleTypeId",this.carrierRoleTypeId);
valueMap.put("productStoreShipMethId",this.productStoreShipMethId);
valueMap.put("geoIdTo",this.geoIdTo);
valueMap.put("productFeatureGroupId",this.productFeatureGroupId);
valueMap.put("orderFlatPrice",this.orderFlatPrice);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("partyId",this.partyId);
valueMap.put("orderPricePercent",this.orderPricePercent);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("quantityUnitPrice",this.quantityUnitPrice);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentCostEstimate");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String priceUomId;
public java.lang.String getpriceUomId() {
return priceUomId;
}
public void setpriceUomId( java.lang.String priceUomId ) {
this.priceUomId = priceUomId;
}
private java.lang.String quantityUomId;
public java.lang.String getquantityUomId() {
return quantityUomId;
}
public void setquantityUomId( java.lang.String quantityUomId ) {
this.quantityUomId = quantityUomId;
}
private java.math.BigDecimal orderItemFlatPrice;
public java.math.BigDecimal getorderItemFlatPrice() {
return orderItemFlatPrice;
}
public void setorderItemFlatPrice( java.math.BigDecimal orderItemFlatPrice ) {
this.orderItemFlatPrice = orderItemFlatPrice;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String weightBreakId;
public java.lang.String getweightBreakId() {
return weightBreakId;
}
public void setweightBreakId( java.lang.String weightBreakId ) {
this.weightBreakId = weightBreakId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String shipmentCostEstimateId;
public java.lang.String getshipmentCostEstimateId() {
return shipmentCostEstimateId;
}
public void setshipmentCostEstimateId( java.lang.String shipmentCostEstimateId ) {
this.shipmentCostEstimateId = shipmentCostEstimateId;
}
private java.lang.String oversizePrice;
public java.lang.String getoversizePrice() {
return oversizePrice;
}
public void setoversizePrice( java.lang.String oversizePrice ) {
this.oversizePrice = oversizePrice;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String quantityBreakId;
public java.lang.String getquantityBreakId() {
return quantityBreakId;
}
public void setquantityBreakId( java.lang.String quantityBreakId ) {
this.quantityBreakId = quantityBreakId;
}
private java.lang.String shippingPricePercent;
public java.lang.String getshippingPricePercent() {
return shippingPricePercent;
}
public void setshippingPricePercent( java.lang.String shippingPricePercent ) {
this.shippingPricePercent = shippingPricePercent;
}
private java.lang.String geoIdFrom;
public java.lang.String getgeoIdFrom() {
return geoIdFrom;
}
public void setgeoIdFrom( java.lang.String geoIdFrom ) {
this.geoIdFrom = geoIdFrom;
}
private java.lang.String priceBreakId;
public java.lang.String getpriceBreakId() {
return priceBreakId;
}
public void setpriceBreakId( java.lang.String priceBreakId ) {
this.priceBreakId = priceBreakId;
}
private java.lang.String featurePrice;
public java.lang.String getfeaturePrice() {
return featurePrice;
}
public void setfeaturePrice( java.lang.String featurePrice ) {
this.featurePrice = featurePrice;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String priceUnitPrice;
public java.lang.String getpriceUnitPrice() {
return priceUnitPrice;
}
public void setpriceUnitPrice( java.lang.String priceUnitPrice ) {
this.priceUnitPrice = priceUnitPrice;
}
private java.lang.String weightUnitPrice;
public java.lang.String getweightUnitPrice() {
return weightUnitPrice;
}
public void setweightUnitPrice( java.lang.String weightUnitPrice ) {
this.weightUnitPrice = weightUnitPrice;
}
private java.lang.String carrierPartyId;
public java.lang.String getcarrierPartyId() {
return carrierPartyId;
}
public void setcarrierPartyId( java.lang.String carrierPartyId ) {
this.carrierPartyId = carrierPartyId;
}
private java.lang.String weightUomId;
public java.lang.String getweightUomId() {
return weightUomId;
}
public void setweightUomId( java.lang.String weightUomId ) {
this.weightUomId = weightUomId;
}
private java.lang.String oversizeUnit;
public java.lang.String getoversizeUnit() {
return oversizeUnit;
}
public void setoversizeUnit( java.lang.String oversizeUnit ) {
this.oversizeUnit = oversizeUnit;
}
private java.lang.String featurePercent;
public java.lang.String getfeaturePercent() {
return featurePercent;
}
public void setfeaturePercent( java.lang.String featurePercent ) {
this.featurePercent = featurePercent;
}
private java.lang.String carrierRoleTypeId;
public java.lang.String getcarrierRoleTypeId() {
return carrierRoleTypeId;
}
public void setcarrierRoleTypeId( java.lang.String carrierRoleTypeId ) {
this.carrierRoleTypeId = carrierRoleTypeId;
}
private java.lang.String productStoreShipMethId;
public java.lang.String getproductStoreShipMethId() {
return productStoreShipMethId;
}
public void setproductStoreShipMethId( java.lang.String productStoreShipMethId ) {
this.productStoreShipMethId = productStoreShipMethId;
}
private java.lang.String geoIdTo;
public java.lang.String getgeoIdTo() {
return geoIdTo;
}
public void setgeoIdTo( java.lang.String geoIdTo ) {
this.geoIdTo = geoIdTo;
}
private java.lang.String productFeatureGroupId;
public java.lang.String getproductFeatureGroupId() {
return productFeatureGroupId;
}
public void setproductFeatureGroupId( java.lang.String productFeatureGroupId ) {
this.productFeatureGroupId = productFeatureGroupId;
}
private java.math.BigDecimal orderFlatPrice;
public java.math.BigDecimal getorderFlatPrice() {
return orderFlatPrice;
}
public void setorderFlatPrice( java.math.BigDecimal orderFlatPrice ) {
this.orderFlatPrice = orderFlatPrice;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.math.BigDecimal orderPricePercent;
public java.math.BigDecimal getorderPricePercent() {
return orderPricePercent;
}
public void setorderPricePercent( java.math.BigDecimal orderPricePercent ) {
this.orderPricePercent = orderPricePercent;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String quantityUnitPrice;
public java.lang.String getquantityUnitPrice() {
return quantityUnitPrice;
}
public void setquantityUnitPrice( java.lang.String quantityUnitPrice ) {
this.quantityUnitPrice = quantityUnitPrice;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentCostEstimate> objectList = new ArrayList<ShipmentCostEstimate>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentCostEstimate(genVal));
        }
        return objectList;
    }    
}
