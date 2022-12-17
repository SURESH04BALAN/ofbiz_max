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
public class ShipmentPackage implements GenericValueObjectInterface {
public static final String module =ShipmentPackage.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentPackage(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentPackage () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"weightUomId","Weight Uom Id"},
{"weight","Weight"},
{"insuredValue","Insured Value"},
{"boxHeight","Box Height"},
{"boxWidth","Box Width"},
{"createdTxStamp","Created Tx Stamp"},
{"dimensionUomId","Dimension Uom Id"},
{"boxLength","Box Length"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"dateCreated","Date Created"},
{"shipmentBoxTypeId","Shipment Box Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.shipmentPackageSeqId = "";
this.weightUomId = "";
this.weight = "";
this.insuredValue = "";
this.boxHeight = "";
this.boxWidth = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.dimensionUomId = "";
this.boxLength = "";
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.dateCreated = UtilDateTime.nowTimestamp();
this.shipmentBoxTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.weightUomId = (java.lang.String) genVal.get("weightUomId");
this.weight = (java.lang.String) genVal.get("weight");
this.insuredValue = (java.lang.String) genVal.get("insuredValue");
this.boxHeight = (java.lang.String) genVal.get("boxHeight");
this.boxWidth = (java.lang.String) genVal.get("boxWidth");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.dimensionUomId = (java.lang.String) genVal.get("dimensionUomId");
this.boxLength = (java.lang.String) genVal.get("boxLength");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.dateCreated = (java.sql.Timestamp) genVal.get("dateCreated");
this.shipmentBoxTypeId = (java.lang.String) genVal.get("shipmentBoxTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("weightUomId",OrderMaxUtility.getValidEntityString(this.weightUomId));
val.set("weight",OrderMaxUtility.getValidEntityString(this.weight));
val.set("insuredValue",OrderMaxUtility.getValidEntityString(this.insuredValue));
val.set("boxHeight",OrderMaxUtility.getValidEntityString(this.boxHeight));
val.set("boxWidth",OrderMaxUtility.getValidEntityString(this.boxWidth));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("dimensionUomId",OrderMaxUtility.getValidEntityString(this.dimensionUomId));
val.set("boxLength",OrderMaxUtility.getValidEntityString(this.boxLength));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("dateCreated",OrderMaxUtility.getValidEntityTimestamp(this.dateCreated));
val.set("shipmentBoxTypeId",OrderMaxUtility.getValidEntityString(this.shipmentBoxTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("weightUomId",this.weightUomId);
valueMap.put("weight",this.weight);
valueMap.put("insuredValue",this.insuredValue);
valueMap.put("boxHeight",this.boxHeight);
valueMap.put("boxWidth",this.boxWidth);
valueMap.put("dimensionUomId",this.dimensionUomId);
valueMap.put("boxLength",this.boxLength);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("dateCreated",this.dateCreated);
valueMap.put("shipmentBoxTypeId",this.shipmentBoxTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentPackage");
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
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.lang.String weightUomId;
public java.lang.String getweightUomId() {
return weightUomId;
}
public void setweightUomId( java.lang.String weightUomId ) {
this.weightUomId = weightUomId;
}
private java.lang.String weight;
public java.lang.String getweight() {
return weight;
}
public void setweight( java.lang.String weight ) {
this.weight = weight;
}
private java.lang.String insuredValue;
public java.lang.String getinsuredValue() {
return insuredValue;
}
public void setinsuredValue( java.lang.String insuredValue ) {
this.insuredValue = insuredValue;
}
private java.lang.String boxHeight;
public java.lang.String getboxHeight() {
return boxHeight;
}
public void setboxHeight( java.lang.String boxHeight ) {
this.boxHeight = boxHeight;
}
private java.lang.String boxWidth;
public java.lang.String getboxWidth() {
return boxWidth;
}
public void setboxWidth( java.lang.String boxWidth ) {
this.boxWidth = boxWidth;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String dimensionUomId;
public java.lang.String getdimensionUomId() {
return dimensionUomId;
}
public void setdimensionUomId( java.lang.String dimensionUomId ) {
this.dimensionUomId = dimensionUomId;
}
private java.lang.String boxLength;
public java.lang.String getboxLength() {
return boxLength;
}
public void setboxLength( java.lang.String boxLength ) {
this.boxLength = boxLength;
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
private java.sql.Timestamp dateCreated;
public java.sql.Timestamp getdateCreated() {
return dateCreated;
}
public void setdateCreated( java.sql.Timestamp dateCreated ) {
this.dateCreated = dateCreated;
}
private java.lang.String shipmentBoxTypeId;
public java.lang.String getshipmentBoxTypeId() {
return shipmentBoxTypeId;
}
public void setshipmentBoxTypeId( java.lang.String shipmentBoxTypeId ) {
this.shipmentBoxTypeId = shipmentBoxTypeId;
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
        Collection<ShipmentPackage> objectList = new ArrayList<ShipmentPackage>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentPackage(genVal));
        }
        return objectList;
    }    
}
