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
public class PortalPageColumn implements GenericValueObjectInterface {
public static final String module =PortalPageColumn.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortalPageColumn(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortalPageColumn () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"portalPageId","Portal Page Id"},
{"columnWidthPercentage","Column Width Percentage"},
{"columnSeqId","Column Seq Id"},
{"columnWidthPixels","Column Width Pixels"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.portalPageId = "";
this.columnWidthPercentage = "";
this.columnSeqId = "";
this.columnWidthPixels = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.portalPageId = (java.lang.String) genVal.get("portalPageId");
this.columnWidthPercentage = (java.lang.String) genVal.get("columnWidthPercentage");
this.columnSeqId = (java.lang.String) genVal.get("columnSeqId");
this.columnWidthPixels = (java.lang.String) genVal.get("columnWidthPixels");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("portalPageId",OrderMaxUtility.getValidEntityString(this.portalPageId));
val.set("columnWidthPercentage",OrderMaxUtility.getValidEntityString(this.columnWidthPercentage));
val.set("columnSeqId",OrderMaxUtility.getValidEntityString(this.columnSeqId));
val.set("columnWidthPixels",OrderMaxUtility.getValidEntityString(this.columnWidthPixels));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("portalPageId",this.portalPageId);
valueMap.put("columnWidthPercentage",this.columnWidthPercentage);
valueMap.put("columnSeqId",this.columnSeqId);
valueMap.put("columnWidthPixels",this.columnWidthPixels);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortalPageColumn");
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
private java.lang.String portalPageId;
public java.lang.String getportalPageId() {
return portalPageId;
}
public void setportalPageId( java.lang.String portalPageId ) {
this.portalPageId = portalPageId;
}
private java.lang.String columnWidthPercentage;
public java.lang.String getcolumnWidthPercentage() {
return columnWidthPercentage;
}
public void setcolumnWidthPercentage( java.lang.String columnWidthPercentage ) {
this.columnWidthPercentage = columnWidthPercentage;
}
private java.lang.String columnSeqId;
public java.lang.String getcolumnSeqId() {
return columnSeqId;
}
public void setcolumnSeqId( java.lang.String columnSeqId ) {
this.columnSeqId = columnSeqId;
}
private java.lang.String columnWidthPixels;
public java.lang.String getcolumnWidthPixels() {
return columnWidthPixels;
}
public void setcolumnWidthPixels( java.lang.String columnWidthPixels ) {
this.columnWidthPixels = columnWidthPixels;
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
        Collection<PortalPageColumn> objectList = new ArrayList<PortalPageColumn>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortalPageColumn(genVal));
        }
        return objectList;
    }    
}
