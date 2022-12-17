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
public class ShipmentPackageRouteSeg implements GenericValueObjectInterface {
public static final String module =ShipmentPackageRouteSeg.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentPackageRouteSeg(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentPackageRouteSeg () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"internationalInvoice","International Invoice"},
{"shipmentPackageSeqId","Shipment Package Seq Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"boxNumber","Box Number"},
{"insuredAmount","Insured Amount"},
{"codAmount","Cod Amount"},
{"labelHtml","Label Html"},
{"shipmentRouteSegmentId","Shipment Route Segment Id"},
{"currencyUomId","Currency Uom Id"},
{"labelImage","Label Image"},
{"labelPrinted","Label Printed"},
{"createdTxStamp","Created Tx Stamp"},
{"trackingCode","Tracking Code"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"packageServiceCost","Package Service Cost"},
{"packageOtherCost","Package Other Cost"},
{"packageTransportCost","Package Transport Cost"},
{"labelIntlSignImage","Label Intl Sign Image"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.internationalInvoice = "";
this.shipmentPackageSeqId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.boxNumber = "";
this.insuredAmount = "";
this.codAmount = "";
this.labelHtml = "";
this.shipmentRouteSegmentId = "";
this.currencyUomId = "";
this.labelImage = "";
this.labelPrinted = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.trackingCode = "";
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.packageServiceCost = "";
this.packageOtherCost = "";
this.packageTransportCost = "";
this.labelIntlSignImage = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.internationalInvoice = (java.lang.String) genVal.get("internationalInvoice");
this.shipmentPackageSeqId = (java.lang.String) genVal.get("shipmentPackageSeqId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.boxNumber = (java.lang.String) genVal.get("boxNumber");
this.insuredAmount = (java.lang.String) genVal.get("insuredAmount");
this.codAmount = (java.lang.String) genVal.get("codAmount");
this.labelHtml = (java.lang.String) genVal.get("labelHtml");
this.shipmentRouteSegmentId = (java.lang.String) genVal.get("shipmentRouteSegmentId");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.labelImage = (java.lang.String) genVal.get("labelImage");
this.labelPrinted = (java.lang.String) genVal.get("labelPrinted");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.trackingCode = (java.lang.String) genVal.get("trackingCode");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.packageServiceCost = (java.lang.String) genVal.get("packageServiceCost");
this.packageOtherCost = (java.lang.String) genVal.get("packageOtherCost");
this.packageTransportCost = (java.lang.String) genVal.get("packageTransportCost");
this.labelIntlSignImage = (java.lang.String) genVal.get("labelIntlSignImage");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("internationalInvoice",OrderMaxUtility.getValidEntityString(this.internationalInvoice));
val.set("shipmentPackageSeqId",OrderMaxUtility.getValidEntityString(this.shipmentPackageSeqId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("boxNumber",OrderMaxUtility.getValidEntityString(this.boxNumber));
val.set("insuredAmount",OrderMaxUtility.getValidEntityString(this.insuredAmount));
val.set("codAmount",OrderMaxUtility.getValidEntityString(this.codAmount));
val.set("labelHtml",OrderMaxUtility.getValidEntityString(this.labelHtml));
val.set("shipmentRouteSegmentId",OrderMaxUtility.getValidEntityString(this.shipmentRouteSegmentId));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("labelImage",OrderMaxUtility.getValidEntityString(this.labelImage));
val.set("labelPrinted",OrderMaxUtility.getValidEntityString(this.labelPrinted));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("trackingCode",OrderMaxUtility.getValidEntityString(this.trackingCode));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("packageServiceCost",OrderMaxUtility.getValidEntityString(this.packageServiceCost));
val.set("packageOtherCost",OrderMaxUtility.getValidEntityString(this.packageOtherCost));
val.set("packageTransportCost",OrderMaxUtility.getValidEntityString(this.packageTransportCost));
val.set("labelIntlSignImage",OrderMaxUtility.getValidEntityString(this.labelIntlSignImage));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("internationalInvoice",this.internationalInvoice);
valueMap.put("shipmentPackageSeqId",this.shipmentPackageSeqId);
valueMap.put("boxNumber",this.boxNumber);
valueMap.put("insuredAmount",this.insuredAmount);
valueMap.put("codAmount",this.codAmount);
valueMap.put("labelHtml",this.labelHtml);
valueMap.put("shipmentRouteSegmentId",this.shipmentRouteSegmentId);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("labelImage",this.labelImage);
valueMap.put("labelPrinted",this.labelPrinted);
valueMap.put("trackingCode",this.trackingCode);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("packageServiceCost",this.packageServiceCost);
valueMap.put("packageOtherCost",this.packageOtherCost);
valueMap.put("packageTransportCost",this.packageTransportCost);
valueMap.put("labelIntlSignImage",this.labelIntlSignImage);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentPackageRouteSeg");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String internationalInvoice;
public java.lang.String getinternationalInvoice() {
return internationalInvoice;
}
public void setinternationalInvoice( java.lang.String internationalInvoice ) {
this.internationalInvoice = internationalInvoice;
}
private java.lang.String shipmentPackageSeqId;
public java.lang.String getshipmentPackageSeqId() {
return shipmentPackageSeqId;
}
public void setshipmentPackageSeqId( java.lang.String shipmentPackageSeqId ) {
this.shipmentPackageSeqId = shipmentPackageSeqId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String boxNumber;
public java.lang.String getboxNumber() {
return boxNumber;
}
public void setboxNumber( java.lang.String boxNumber ) {
this.boxNumber = boxNumber;
}
private java.lang.String insuredAmount;
public java.lang.String getinsuredAmount() {
return insuredAmount;
}
public void setinsuredAmount( java.lang.String insuredAmount ) {
this.insuredAmount = insuredAmount;
}
private java.lang.String codAmount;
public java.lang.String getcodAmount() {
return codAmount;
}
public void setcodAmount( java.lang.String codAmount ) {
this.codAmount = codAmount;
}
private java.lang.String labelHtml;
public java.lang.String getlabelHtml() {
return labelHtml;
}
public void setlabelHtml( java.lang.String labelHtml ) {
this.labelHtml = labelHtml;
}
private java.lang.String shipmentRouteSegmentId;
public java.lang.String getshipmentRouteSegmentId() {
return shipmentRouteSegmentId;
}
public void setshipmentRouteSegmentId( java.lang.String shipmentRouteSegmentId ) {
this.shipmentRouteSegmentId = shipmentRouteSegmentId;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String labelImage;
public java.lang.String getlabelImage() {
return labelImage;
}
public void setlabelImage( java.lang.String labelImage ) {
this.labelImage = labelImage;
}
private java.lang.String labelPrinted;
public java.lang.String getlabelPrinted() {
return labelPrinted;
}
public void setlabelPrinted( java.lang.String labelPrinted ) {
this.labelPrinted = labelPrinted;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String trackingCode;
public java.lang.String gettrackingCode() {
return trackingCode;
}
public void settrackingCode( java.lang.String trackingCode ) {
this.trackingCode = trackingCode;
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
private java.lang.String packageServiceCost;
public java.lang.String getpackageServiceCost() {
return packageServiceCost;
}
public void setpackageServiceCost( java.lang.String packageServiceCost ) {
this.packageServiceCost = packageServiceCost;
}
private java.lang.String packageOtherCost;
public java.lang.String getpackageOtherCost() {
return packageOtherCost;
}
public void setpackageOtherCost( java.lang.String packageOtherCost ) {
this.packageOtherCost = packageOtherCost;
}
private java.lang.String packageTransportCost;
public java.lang.String getpackageTransportCost() {
return packageTransportCost;
}
public void setpackageTransportCost( java.lang.String packageTransportCost ) {
this.packageTransportCost = packageTransportCost;
}
private java.lang.String labelIntlSignImage;
public java.lang.String getlabelIntlSignImage() {
return labelIntlSignImage;
}
public void setlabelIntlSignImage( java.lang.String labelIntlSignImage ) {
this.labelIntlSignImage = labelIntlSignImage;
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
        Collection<ShipmentPackageRouteSeg> objectList = new ArrayList<ShipmentPackageRouteSeg>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentPackageRouteSeg(genVal));
        }
        return objectList;
    }    
}
