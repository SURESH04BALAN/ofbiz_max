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
public class PicklistBin implements GenericValueObjectInterface {
public static final String module =PicklistBin.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PicklistBin(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PicklistBin () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"primaryShipGroupSeqId","Primary Ship Group Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"picklistBinId","Picklist Bin Id"},
{"picklistId","Picklist Id"},
{"binLocationNumber","Bin Location Number"},
{"primaryOrderId","Primary Order Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.primaryShipGroupSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.picklistBinId = "";
this.picklistId = "";
this.binLocationNumber = new Long(0);
this.primaryOrderId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.primaryShipGroupSeqId = (java.lang.String) genVal.get("primaryShipGroupSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.picklistBinId = (java.lang.String) genVal.get("picklistBinId");
this.picklistId = (java.lang.String) genVal.get("picklistId");
this.binLocationNumber = (java.lang.Long) genVal.get("binLocationNumber");
this.primaryOrderId = (java.lang.String) genVal.get("primaryOrderId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("primaryShipGroupSeqId",OrderMaxUtility.getValidEntityString(this.primaryShipGroupSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("picklistBinId",OrderMaxUtility.getValidEntityString(this.picklistBinId));
val.set("picklistId",OrderMaxUtility.getValidEntityString(this.picklistId));
val.set("binLocationNumber",this.binLocationNumber);
val.set("primaryOrderId",OrderMaxUtility.getValidEntityString(this.primaryOrderId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("primaryShipGroupSeqId",this.primaryShipGroupSeqId);
valueMap.put("picklistBinId",this.picklistBinId);
valueMap.put("picklistId",this.picklistId);
valueMap.put("binLocationNumber",this.binLocationNumber);
valueMap.put("primaryOrderId",this.primaryOrderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PicklistBin");
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
private java.lang.String primaryShipGroupSeqId;
public java.lang.String getprimaryShipGroupSeqId() {
return primaryShipGroupSeqId;
}
public void setprimaryShipGroupSeqId( java.lang.String primaryShipGroupSeqId ) {
this.primaryShipGroupSeqId = primaryShipGroupSeqId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String picklistBinId;
public java.lang.String getpicklistBinId() {
return picklistBinId;
}
public void setpicklistBinId( java.lang.String picklistBinId ) {
this.picklistBinId = picklistBinId;
}
private java.lang.String picklistId;
public java.lang.String getpicklistId() {
return picklistId;
}
public void setpicklistId( java.lang.String picklistId ) {
this.picklistId = picklistId;
}
private java.lang.Long binLocationNumber;
public java.lang.Long getbinLocationNumber() {
return binLocationNumber;
}
public void setbinLocationNumber( java.lang.Long binLocationNumber ) {
this.binLocationNumber = binLocationNumber;
}
private java.lang.String primaryOrderId;
public java.lang.String getprimaryOrderId() {
return primaryOrderId;
}
public void setprimaryOrderId( java.lang.String primaryOrderId ) {
this.primaryOrderId = primaryOrderId;
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
        Collection<PicklistBin> objectList = new ArrayList<PicklistBin>();
        for (GenericValue genVal : genList) {
            objectList.add(new PicklistBin(genVal));
        }
        return objectList;
    }    
}
