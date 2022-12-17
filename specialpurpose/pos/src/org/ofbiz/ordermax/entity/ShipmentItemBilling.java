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
public class ShipmentItemBilling implements GenericValueObjectInterface {
public static final String module =ShipmentItemBilling.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentItemBilling(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentItemBilling () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"shipmentItemSeqId","Shipment Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"invoiceId","Invoice Id"},
{"createdStamp","Created Stamp"},
{"invoiceItemSeqId","Invoice Item Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.shipmentItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.invoiceId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.invoiceItemSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.shipmentItemSeqId = (java.lang.String) genVal.get("shipmentItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("shipmentItemSeqId",OrderMaxUtility.getValidEntityString(this.shipmentItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("invoiceItemSeqId",OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("shipmentItemSeqId",this.shipmentItemSeqId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("invoiceItemSeqId",this.invoiceItemSeqId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentItemBilling");
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
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String invoiceItemSeqId;
public java.lang.String getinvoiceItemSeqId() {
return invoiceItemSeqId;
}
public void setinvoiceItemSeqId( java.lang.String invoiceItemSeqId ) {
this.invoiceItemSeqId = invoiceItemSeqId;
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
        Collection<ShipmentItemBilling> objectList = new ArrayList<ShipmentItemBilling>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentItemBilling(genVal));
        }
        return objectList;
    }    
}
