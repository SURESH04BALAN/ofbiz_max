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
public class VisualThemeResource implements GenericValueObjectInterface {
public static final String module =VisualThemeResource.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public VisualThemeResource(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public VisualThemeResource () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"visualThemeId","Visual Theme Id"},
{"resourceValue","Resource Value"},
{"sequenceId","Sequence Id"},
{"resourceTypeEnumId","Resource Type Enum Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.visualThemeId = "";
this.resourceValue = "";
this.sequenceId = "";
this.resourceTypeEnumId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.visualThemeId = (java.lang.String) genVal.get("visualThemeId");
this.resourceValue = (java.lang.String) genVal.get("resourceValue");
this.sequenceId = (java.lang.String) genVal.get("sequenceId");
this.resourceTypeEnumId = (java.lang.String) genVal.get("resourceTypeEnumId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("visualThemeId",OrderMaxUtility.getValidEntityString(this.visualThemeId));
val.set("resourceValue",OrderMaxUtility.getValidEntityString(this.resourceValue));
val.set("sequenceId",OrderMaxUtility.getValidEntityString(this.sequenceId));
val.set("resourceTypeEnumId",OrderMaxUtility.getValidEntityString(this.resourceTypeEnumId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("visualThemeId",this.visualThemeId);
valueMap.put("resourceValue",this.resourceValue);
valueMap.put("sequenceId",this.sequenceId);
valueMap.put("resourceTypeEnumId",this.resourceTypeEnumId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("VisualThemeResource");
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
private java.lang.String visualThemeId;
public java.lang.String getvisualThemeId() {
return visualThemeId;
}
public void setvisualThemeId( java.lang.String visualThemeId ) {
this.visualThemeId = visualThemeId;
}
private java.lang.String resourceValue;
public java.lang.String getresourceValue() {
return resourceValue;
}
public void setresourceValue( java.lang.String resourceValue ) {
this.resourceValue = resourceValue;
}
private java.lang.String sequenceId;
public java.lang.String getsequenceId() {
return sequenceId;
}
public void setsequenceId( java.lang.String sequenceId ) {
this.sequenceId = sequenceId;
}
private java.lang.String resourceTypeEnumId;
public java.lang.String getresourceTypeEnumId() {
return resourceTypeEnumId;
}
public void setresourceTypeEnumId( java.lang.String resourceTypeEnumId ) {
this.resourceTypeEnumId = resourceTypeEnumId;
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
        Collection<VisualThemeResource> objectList = new ArrayList<VisualThemeResource>();
        for (GenericValue genVal : genList) {
            objectList.add(new VisualThemeResource(genVal));
        }
        return objectList;
    }    
}
