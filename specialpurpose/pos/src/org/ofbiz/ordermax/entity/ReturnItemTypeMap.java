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
public class ReturnItemTypeMap implements GenericValueObjectInterface {
public static final String module =ReturnItemTypeMap.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ReturnItemTypeMap(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ReturnItemTypeMap () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"returnItemTypeId","Return Item Type Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"returnItemMapKey","Return Item Map Key"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"returnHeaderTypeId","Return Header Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.returnItemTypeId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.returnItemMapKey = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.returnHeaderTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.returnItemTypeId = (java.lang.String) genVal.get("returnItemTypeId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.returnItemMapKey = (java.lang.String) genVal.get("returnItemMapKey");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.returnHeaderTypeId = (java.lang.String) genVal.get("returnHeaderTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("returnItemTypeId",OrderMaxUtility.getValidEntityString(this.returnItemTypeId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("returnItemMapKey",OrderMaxUtility.getValidEntityString(this.returnItemMapKey));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("returnHeaderTypeId",OrderMaxUtility.getValidEntityString(this.returnHeaderTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("returnItemTypeId",this.returnItemTypeId);
valueMap.put("returnItemMapKey",this.returnItemMapKey);
valueMap.put("returnHeaderTypeId",this.returnHeaderTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ReturnItemTypeMap");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String returnItemTypeId;
public java.lang.String getreturnItemTypeId() {
return returnItemTypeId;
}
public void setreturnItemTypeId( java.lang.String returnItemTypeId ) {
this.returnItemTypeId = returnItemTypeId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String returnItemMapKey;
public java.lang.String getreturnItemMapKey() {
return returnItemMapKey;
}
public void setreturnItemMapKey( java.lang.String returnItemMapKey ) {
this.returnItemMapKey = returnItemMapKey;
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
private java.lang.String returnHeaderTypeId;
public java.lang.String getreturnHeaderTypeId() {
return returnHeaderTypeId;
}
public void setreturnHeaderTypeId( java.lang.String returnHeaderTypeId ) {
this.returnHeaderTypeId = returnHeaderTypeId;
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
        Collection<ReturnItemTypeMap> objectList = new ArrayList<ReturnItemTypeMap>();
        for (GenericValue genVal : genList) {
            objectList.add(new ReturnItemTypeMap(genVal));
        }
        return objectList;
    }    
}
