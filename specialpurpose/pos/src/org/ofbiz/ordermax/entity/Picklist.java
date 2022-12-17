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
public class Picklist implements GenericValueObjectInterface {
public static final String module =Picklist.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public Picklist(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public Picklist () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"facilityId","Facility Id"},
{"statusId","Status Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"createdTxStamp","Created Tx Stamp"},
{"picklistDate","Picklist Date"},
{"createdStamp","Created Stamp"},
{"picklistId","Picklist Id"},
{"description","Description"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.facilityId = "";
this.statusId = "";
this.lastModifiedByUserLogin = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.picklistDate = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.picklistId = "";
this.description = "";
this.shipmentMethodTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.picklistDate = (java.sql.Timestamp) genVal.get("picklistDate");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.picklistId = (java.lang.String) genVal.get("picklistId");
this.description = (java.lang.String) genVal.get("description");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("picklistDate",OrderMaxUtility.getValidEntityTimestamp(this.picklistDate));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("picklistId",OrderMaxUtility.getValidEntityString(this.picklistId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("facilityId",this.facilityId);
valueMap.put("statusId",this.statusId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("picklistDate",this.picklistDate);
valueMap.put("picklistId",this.picklistId);
valueMap.put("description",this.description);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("Picklist");
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
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp picklistDate;
public java.sql.Timestamp getpicklistDate() {
return picklistDate;
}
public void setpicklistDate( java.sql.Timestamp picklistDate ) {
this.picklistDate = picklistDate;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String picklistId;
public java.lang.String getpicklistId() {
return picklistId;
}
public void setpicklistId( java.lang.String picklistId ) {
this.picklistId = picklistId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<Picklist> objectList = new ArrayList<Picklist>();
        for (GenericValue genVal : genList) {
            objectList.add(new Picklist(genVal));
        }
        return objectList;
    }    
}
