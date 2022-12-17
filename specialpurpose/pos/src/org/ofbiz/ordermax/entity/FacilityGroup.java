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
public class FacilityGroup implements GenericValueObjectInterface {
public static final String module =FacilityGroup.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public FacilityGroup(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public FacilityGroup () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"facilityGroupTypeId","Facility Group Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"facilityGroupId","Facility Group Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"facilityGroupName","Facility Group Name"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"primaryParentGroupId","Primary Parent Group Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.facilityGroupTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.facilityGroupId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.facilityGroupName = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.primaryParentGroupId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.facilityGroupTypeId = (java.lang.String) genVal.get("facilityGroupTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.facilityGroupId = (java.lang.String) genVal.get("facilityGroupId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.facilityGroupName = (java.lang.String) genVal.get("facilityGroupName");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.primaryParentGroupId = (java.lang.String) genVal.get("primaryParentGroupId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("facilityGroupTypeId",OrderMaxUtility.getValidEntityString(this.facilityGroupTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("facilityGroupId",OrderMaxUtility.getValidEntityString(this.facilityGroupId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("facilityGroupName",OrderMaxUtility.getValidEntityString(this.facilityGroupName));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("primaryParentGroupId",OrderMaxUtility.getValidEntityString(this.primaryParentGroupId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("facilityGroupTypeId",this.facilityGroupTypeId);
valueMap.put("facilityGroupId",this.facilityGroupId);
valueMap.put("description",this.description);
valueMap.put("facilityGroupName",this.facilityGroupName);
valueMap.put("primaryParentGroupId",this.primaryParentGroupId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("FacilityGroup");
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
private java.lang.String facilityGroupTypeId;
public java.lang.String getfacilityGroupTypeId() {
return facilityGroupTypeId;
}
public void setfacilityGroupTypeId( java.lang.String facilityGroupTypeId ) {
this.facilityGroupTypeId = facilityGroupTypeId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String facilityGroupId;
public java.lang.String getfacilityGroupId() {
return facilityGroupId;
}
public void setfacilityGroupId( java.lang.String facilityGroupId ) {
this.facilityGroupId = facilityGroupId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String facilityGroupName;
public java.lang.String getfacilityGroupName() {
return facilityGroupName;
}
public void setfacilityGroupName( java.lang.String facilityGroupName ) {
this.facilityGroupName = facilityGroupName;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String primaryParentGroupId;
public java.lang.String getprimaryParentGroupId() {
return primaryParentGroupId;
}
public void setprimaryParentGroupId( java.lang.String primaryParentGroupId ) {
this.primaryParentGroupId = primaryParentGroupId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<FacilityGroup> objectList = new ArrayList<FacilityGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new FacilityGroup(genVal));
        }
        return objectList;
    }    
}
