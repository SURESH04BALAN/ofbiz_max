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
public class SystemProperty implements GenericValueObjectInterface {
public static final String module =SystemProperty.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public SystemProperty(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public SystemProperty () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"systemPropertyValue","System Property Value"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"systemResourceId","System Resource Id"},
{"description","Description"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"systemPropertyId","System Property Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.systemPropertyValue = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.systemResourceId = "";
this.description = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.systemPropertyId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.systemPropertyValue = (java.lang.String) genVal.get("systemPropertyValue");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.systemResourceId = (java.lang.String) genVal.get("systemResourceId");
this.description = (java.lang.String) genVal.get("description");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.systemPropertyId = (java.lang.String) genVal.get("systemPropertyId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("systemPropertyValue",OrderMaxUtility.getValidEntityString(this.systemPropertyValue));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("systemResourceId",OrderMaxUtility.getValidEntityString(this.systemResourceId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("systemPropertyId",OrderMaxUtility.getValidEntityString(this.systemPropertyId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("systemPropertyValue",this.systemPropertyValue);
valueMap.put("systemResourceId",this.systemResourceId);
valueMap.put("description",this.description);
valueMap.put("systemPropertyId",this.systemPropertyId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("SystemProperty");
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
private java.lang.String systemPropertyValue;
public java.lang.String getsystemPropertyValue() {
return systemPropertyValue;
}
public void setsystemPropertyValue( java.lang.String systemPropertyValue ) {
this.systemPropertyValue = systemPropertyValue;
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
private java.lang.String systemResourceId;
public java.lang.String getsystemResourceId() {
return systemResourceId;
}
public void setsystemResourceId( java.lang.String systemResourceId ) {
this.systemResourceId = systemResourceId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String systemPropertyId;
public java.lang.String getsystemPropertyId() {
return systemPropertyId;
}
public void setsystemPropertyId( java.lang.String systemPropertyId ) {
this.systemPropertyId = systemPropertyId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SystemProperty> objectList = new ArrayList<SystemProperty>();
        for (GenericValue genVal : genList) {
            objectList.add(new SystemProperty(genVal));
        }
        return objectList;
    }    
}
