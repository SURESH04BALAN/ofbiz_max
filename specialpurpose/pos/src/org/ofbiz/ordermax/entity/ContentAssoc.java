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
public class ContentAssoc implements GenericValueObjectInterface {
public static final String module =ContentAssoc.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ContentAssoc(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ContentAssoc () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"leftCoordinate","Left Coordinate"},
{"thruDate","Thru Date"},
{"contentIdTo","Content Id To"},
{"mapKey","Map Key"},
{"upperCoordinate","Upper Coordinate"},
{"contentAssocTypeId","Content Assoc Type Id"},
{"dataSourceId","Data Source Id"},
{"contentId","Content Id"},
{"sequenceNum","Sequence Num"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"lastModifiedDate","Last Modified Date"},
{"createdDate","Created Date"},
{"contentAssocPredicateId","Content Assoc Predicate Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.leftCoordinate = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.contentIdTo = "";
this.mapKey = "";
this.upperCoordinate = "";
this.contentAssocTypeId = "";
this.dataSourceId = "";
this.contentId = "";
this.sequenceNum = new Long(0);
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.createdDate = UtilDateTime.nowTimestamp();
this.contentAssocPredicateId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.leftCoordinate = (java.lang.String) genVal.get("leftCoordinate");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.contentIdTo = (java.lang.String) genVal.get("contentIdTo");
this.mapKey = (java.lang.String) genVal.get("mapKey");
this.upperCoordinate = (java.lang.String) genVal.get("upperCoordinate");
this.contentAssocTypeId = (java.lang.String) genVal.get("contentAssocTypeId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.contentId = (java.lang.String) genVal.get("contentId");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.contentAssocPredicateId = (java.lang.String) genVal.get("contentAssocPredicateId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("leftCoordinate",OrderMaxUtility.getValidEntityString(this.leftCoordinate));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("contentIdTo",OrderMaxUtility.getValidEntityString(this.contentIdTo));
val.set("mapKey",OrderMaxUtility.getValidEntityString(this.mapKey));
val.set("upperCoordinate",OrderMaxUtility.getValidEntityString(this.upperCoordinate));
val.set("contentAssocTypeId",OrderMaxUtility.getValidEntityString(this.contentAssocTypeId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("sequenceNum",this.sequenceNum);
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("contentAssocPredicateId",OrderMaxUtility.getValidEntityString(this.contentAssocPredicateId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("leftCoordinate",this.leftCoordinate);
valueMap.put("thruDate",this.thruDate);
valueMap.put("contentIdTo",this.contentIdTo);
valueMap.put("mapKey",this.mapKey);
valueMap.put("upperCoordinate",this.upperCoordinate);
valueMap.put("contentAssocTypeId",this.contentAssocTypeId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("contentId",this.contentId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("contentAssocPredicateId",this.contentAssocPredicateId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ContentAssoc");
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
private java.lang.String leftCoordinate;
public java.lang.String getleftCoordinate() {
return leftCoordinate;
}
public void setleftCoordinate( java.lang.String leftCoordinate ) {
this.leftCoordinate = leftCoordinate;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String contentIdTo;
public java.lang.String getcontentIdTo() {
return contentIdTo;
}
public void setcontentIdTo( java.lang.String contentIdTo ) {
this.contentIdTo = contentIdTo;
}
private java.lang.String mapKey;
public java.lang.String getmapKey() {
return mapKey;
}
public void setmapKey( java.lang.String mapKey ) {
this.mapKey = mapKey;
}
private java.lang.String upperCoordinate;
public java.lang.String getupperCoordinate() {
return upperCoordinate;
}
public void setupperCoordinate( java.lang.String upperCoordinate ) {
this.upperCoordinate = upperCoordinate;
}
private java.lang.String contentAssocTypeId;
public java.lang.String getcontentAssocTypeId() {
return contentAssocTypeId;
}
public void setcontentAssocTypeId( java.lang.String contentAssocTypeId ) {
this.contentAssocTypeId = contentAssocTypeId;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
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
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String contentAssocPredicateId;
public java.lang.String getcontentAssocPredicateId() {
return contentAssocPredicateId;
}
public void setcontentAssocPredicateId( java.lang.String contentAssocPredicateId ) {
this.contentAssocPredicateId = contentAssocPredicateId;
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
        Collection<ContentAssoc> objectList = new ArrayList<ContentAssoc>();
        for (GenericValue genVal : genList) {
            objectList.add(new ContentAssoc(genVal));
        }
        return objectList;
    }    
}
