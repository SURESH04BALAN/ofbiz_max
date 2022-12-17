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
public class EntitySyncInclude implements GenericValueObjectInterface {
public static final String module =EntitySyncInclude.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EntitySyncInclude(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public EntitySyncInclude () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"applEnumId","Appl Enum Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"entitySyncId","Entity Sync Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"entityOrPackage","Entity Or Package"},
};
protected void initObject(){
this.applEnumId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.entitySyncId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.entityOrPackage = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.applEnumId = (java.lang.String) genVal.get("applEnumId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.entitySyncId = (java.lang.String) genVal.get("entitySyncId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.entityOrPackage = (java.lang.String) genVal.get("entityOrPackage");
}
protected void getGenericValue(GenericValue val) {
val.set("applEnumId",OrderMaxUtility.getValidEntityString(this.applEnumId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("entitySyncId",OrderMaxUtility.getValidEntityString(this.entitySyncId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("entityOrPackage",OrderMaxUtility.getValidEntityString(this.entityOrPackage));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("applEnumId",this.applEnumId);
valueMap.put("entitySyncId",this.entitySyncId);
valueMap.put("entityOrPackage",this.entityOrPackage);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EntitySyncInclude");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String applEnumId;
public java.lang.String getapplEnumId() {
return applEnumId;
}
public void setapplEnumId( java.lang.String applEnumId ) {
this.applEnumId = applEnumId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String entitySyncId;
public java.lang.String getentitySyncId() {
return entitySyncId;
}
public void setentitySyncId( java.lang.String entitySyncId ) {
this.entitySyncId = entitySyncId;
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
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String entityOrPackage;
public java.lang.String getentityOrPackage() {
return entityOrPackage;
}
public void setentityOrPackage( java.lang.String entityOrPackage ) {
this.entityOrPackage = entityOrPackage;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<EntitySyncInclude> objectList = new ArrayList<EntitySyncInclude>();
        for (GenericValue genVal : genList) {
            objectList.add(new EntitySyncInclude(genVal));
        }
        return objectList;
    }    
}
