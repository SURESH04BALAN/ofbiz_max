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
public class TemporalExpression implements GenericValueObjectInterface {
public static final String module =TemporalExpression.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public TemporalExpression(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public TemporalExpression () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"integer1","Integer 1"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"string2","String 2"},
{"string1","String 1"},
{"integer2","Integer 2"},
{"tempExprId","Temp Expr Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"tempExprTypeId","Temp Expr Type Id"},
{"description","Description"},
{"date2","Date 2"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"date1","Date 1"},
};
protected void initObject(){
this.integer1 = new Long(0);
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.string2 = "";
this.string1 = "";
this.integer2 = new Long(0);
this.tempExprId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.tempExprTypeId = "";
this.description = "";
this.date2 = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.date1 = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.integer1 = (java.lang.Long) genVal.get("integer1");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.string2 = (java.lang.String) genVal.get("string2");
this.string1 = (java.lang.String) genVal.get("string1");
this.integer2 = (java.lang.Long) genVal.get("integer2");
this.tempExprId = (java.lang.String) genVal.get("tempExprId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.tempExprTypeId = (java.lang.String) genVal.get("tempExprTypeId");
this.description = (java.lang.String) genVal.get("description");
this.date2 = (java.lang.String) genVal.get("date2");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.date1 = (java.lang.String) genVal.get("date1");
}
protected void getGenericValue(GenericValue val) {
val.set("integer1",this.integer1);
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("string2",OrderMaxUtility.getValidEntityString(this.string2));
val.set("string1",OrderMaxUtility.getValidEntityString(this.string1));
val.set("integer2",this.integer2);
val.set("tempExprId",OrderMaxUtility.getValidEntityString(this.tempExprId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("tempExprTypeId",OrderMaxUtility.getValidEntityString(this.tempExprTypeId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("date2",OrderMaxUtility.getValidEntityString(this.date2));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("date1",OrderMaxUtility.getValidEntityString(this.date1));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("integer1",this.integer1);
valueMap.put("string2",this.string2);
valueMap.put("string1",this.string1);
valueMap.put("integer2",this.integer2);
valueMap.put("tempExprId",this.tempExprId);
valueMap.put("tempExprTypeId",this.tempExprTypeId);
valueMap.put("description",this.description);
valueMap.put("date2",this.date2);
valueMap.put("date1",this.date1);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("TemporalExpression");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long integer1;
public java.lang.Long getinteger1() {
return integer1;
}
public void setinteger1( java.lang.Long integer1 ) {
this.integer1 = integer1;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String string2;
public java.lang.String getstring2() {
return string2;
}
public void setstring2( java.lang.String string2 ) {
this.string2 = string2;
}
private java.lang.String string1;
public java.lang.String getstring1() {
return string1;
}
public void setstring1( java.lang.String string1 ) {
this.string1 = string1;
}
private java.lang.Long integer2;
public java.lang.Long getinteger2() {
return integer2;
}
public void setinteger2( java.lang.Long integer2 ) {
this.integer2 = integer2;
}
private java.lang.String tempExprId;
public java.lang.String gettempExprId() {
return tempExprId;
}
public void settempExprId( java.lang.String tempExprId ) {
this.tempExprId = tempExprId;
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
private java.lang.String tempExprTypeId;
public java.lang.String gettempExprTypeId() {
return tempExprTypeId;
}
public void settempExprTypeId( java.lang.String tempExprTypeId ) {
this.tempExprTypeId = tempExprTypeId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String date2;
public java.lang.String getdate2() {
return date2;
}
public void setdate2( java.lang.String date2 ) {
this.date2 = date2;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String date1;
public java.lang.String getdate1() {
return date1;
}
public void setdate1( java.lang.String date1 ) {
this.date1 = date1;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<TemporalExpression> objectList = new ArrayList<TemporalExpression>();
        for (GenericValue genVal : genList) {
            objectList.add(new TemporalExpression(genVal));
        }
        return objectList;
    }    
}
