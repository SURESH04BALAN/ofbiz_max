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
public class GeoPoint implements GenericValueObjectInterface {
public static final String module =GeoPoint.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GeoPoint(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GeoPoint () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"information","Information"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"elevation","Elevation"},
{"longitude","Longitude"},
{"elevationUomId","Elevation Uom Id"},
{"latitude","Latitude"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"dataSourceId","Data Source Id"},
{"geoPointId","Geo Point Id"},
};
protected void initObject(){
this.information = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.elevation = new Double(0);
this.longitude = new Double(0);
this.elevationUomId = "";
this.latitude = new Double(0);
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.dataSourceId = "";
this.geoPointId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.information = (java.lang.String) genVal.get("information");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.elevation = (java.lang.Double) genVal.get("elevation");
this.longitude = (java.lang.Double) genVal.get("longitude");
this.elevationUomId = (java.lang.String) genVal.get("elevationUomId");
this.latitude = (java.lang.Double) genVal.get("latitude");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.geoPointId = (java.lang.String) genVal.get("geoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("information",OrderMaxUtility.getValidEntityString(this.information));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("elevation",OrderMaxUtility.getValidEntityDouble(this.elevation));
val.set("longitude",OrderMaxUtility.getValidEntityDouble(this.longitude));
val.set("elevationUomId",OrderMaxUtility.getValidEntityString(this.elevationUomId));
val.set("latitude",OrderMaxUtility.getValidEntityDouble(this.latitude));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("geoPointId",OrderMaxUtility.getValidEntityString(this.geoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("information",this.information);
valueMap.put("elevation",this.elevation);
valueMap.put("longitude",this.longitude);
valueMap.put("elevationUomId",this.elevationUomId);
valueMap.put("latitude",this.latitude);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("geoPointId",this.geoPointId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GeoPoint");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String information;
public java.lang.String getinformation() {
return information;
}
public void setinformation( java.lang.String information ) {
this.information = information;
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
private java.lang.Double elevation;
public java.lang.Double getelevation() {
return elevation;
}
public void setelevation( java.lang.Double elevation ) {
this.elevation = elevation;
}
private java.lang.Double longitude;
public java.lang.Double getlongitude() {
return longitude;
}
public void setlongitude( java.lang.Double longitude ) {
this.longitude = longitude;
}
private java.lang.String elevationUomId;
public java.lang.String getelevationUomId() {
return elevationUomId;
}
public void setelevationUomId( java.lang.String elevationUomId ) {
this.elevationUomId = elevationUomId;
}
private java.lang.Double latitude;
public java.lang.Double getlatitude() {
return latitude;
}
public void setlatitude( java.lang.Double latitude ) {
this.latitude = latitude;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String geoPointId;
public java.lang.String getgeoPointId() {
return geoPointId;
}
public void setgeoPointId( java.lang.String geoPointId ) {
this.geoPointId = geoPointId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GeoPoint> objectList = new ArrayList<GeoPoint>();
        for (GenericValue genVal : genList) {
            objectList.add(new GeoPoint(genVal));
        }
        return objectList;
    }    
}
