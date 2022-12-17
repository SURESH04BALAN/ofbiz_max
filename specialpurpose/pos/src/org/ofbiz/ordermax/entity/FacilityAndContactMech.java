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
public class FacilityAndContactMech implements GenericValueObjectInterface {
public static final String module =FacilityAndContactMech.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public FacilityAndContactMech(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public FacilityAndContactMech () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruDate","Thru Date"},
{"productStoreId","Product Store Id"},
{"closedDate","Closed Date"},
{"facilitySizeUomId","Facility Size Uom Id"},
{"defaultDimensionUomId","Default Dimension Uom Id"},
{"oldSquareFootage","Old Square Footage"},
{"description","Description"},
{"infoString","Info String"},
{"ownerPartyId","Owner Party Id"},
{"defaultWeightUomId","Default Weight Uom Id"},
{"facilityName","Facility Name"},
{"openedDate","Opened Date"},
{"contactMechId","Contact Mech Id"},
{"contactMechTypeId","Contact Mech Type Id"},
{"facilityId","Facility Id"},
{"defaultInventoryItemTypeId","Default Inventory Item Type Id"},
{"extension","Extension"},
{"parentFacilityId","Parent Facility Id"},
{"primaryFacilityGroupId","Primary Facility Group Id"},
{"fromDate","From Date"},
{"facilityTypeId","Facility Type Id"},
{"defaultDaysToShip","Default Days To Ship"},
{"facilitySize","Facility Size"},
{"comments","Comments"},
{"geoPointId","Geo Point Id"},
};
protected void initObject(){
this.thruDate = UtilDateTime.nowTimestamp();
this.productStoreId = "";
this.closedDate = UtilDateTime.nowTimestamp();
this.facilitySizeUomId = "";
this.defaultDimensionUomId = "";
this.oldSquareFootage = "";
this.description = "";
this.infoString = "";
this.ownerPartyId = "";
this.defaultWeightUomId = "";
this.facilityName = "";
this.openedDate = UtilDateTime.nowTimestamp();
this.contactMechId = "";
this.contactMechTypeId = "";
this.facilityId = "";
this.defaultInventoryItemTypeId = "";
this.extension = "";
this.parentFacilityId = "";
this.primaryFacilityGroupId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.facilityTypeId = "";
this.defaultDaysToShip = new Long(0);
this.facilitySize = "";
this.comments = "";
this.geoPointId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.closedDate = (java.sql.Timestamp) genVal.get("closedDate");
this.facilitySizeUomId = (java.lang.String) genVal.get("facilitySizeUomId");
this.defaultDimensionUomId = (java.lang.String) genVal.get("defaultDimensionUomId");
this.oldSquareFootage = (java.lang.String) genVal.get("oldSquareFootage");
this.description = (java.lang.String) genVal.get("description");
this.infoString = (java.lang.String) genVal.get("infoString");
this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
this.defaultWeightUomId = (java.lang.String) genVal.get("defaultWeightUomId");
this.facilityName = (java.lang.String) genVal.get("facilityName");
this.openedDate = (java.sql.Timestamp) genVal.get("openedDate");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.defaultInventoryItemTypeId = (java.lang.String) genVal.get("defaultInventoryItemTypeId");
this.extension = (java.lang.String) genVal.get("extension");
this.parentFacilityId = (java.lang.String) genVal.get("parentFacilityId");
this.primaryFacilityGroupId = (java.lang.String) genVal.get("primaryFacilityGroupId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.facilityTypeId = (java.lang.String) genVal.get("facilityTypeId");
this.defaultDaysToShip = (java.lang.Long) genVal.get("defaultDaysToShip");
this.facilitySize = (java.lang.String) genVal.get("facilitySize");
this.comments = (java.lang.String) genVal.get("comments");
this.geoPointId = (java.lang.String) genVal.get("geoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("closedDate",OrderMaxUtility.getValidEntityTimestamp(this.closedDate));
val.set("facilitySizeUomId",OrderMaxUtility.getValidEntityString(this.facilitySizeUomId));
val.set("defaultDimensionUomId",OrderMaxUtility.getValidEntityString(this.defaultDimensionUomId));
val.set("oldSquareFootage",OrderMaxUtility.getValidEntityString(this.oldSquareFootage));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
val.set("ownerPartyId",OrderMaxUtility.getValidEntityString(this.ownerPartyId));
val.set("defaultWeightUomId",OrderMaxUtility.getValidEntityString(this.defaultWeightUomId));
val.set("facilityName",OrderMaxUtility.getValidEntityString(this.facilityName));
val.set("openedDate",OrderMaxUtility.getValidEntityTimestamp(this.openedDate));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("defaultInventoryItemTypeId",OrderMaxUtility.getValidEntityString(this.defaultInventoryItemTypeId));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("parentFacilityId",OrderMaxUtility.getValidEntityString(this.parentFacilityId));
val.set("primaryFacilityGroupId",OrderMaxUtility.getValidEntityString(this.primaryFacilityGroupId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("facilityTypeId",OrderMaxUtility.getValidEntityString(this.facilityTypeId));
val.set("defaultDaysToShip",this.defaultDaysToShip);
val.set("facilitySize",OrderMaxUtility.getValidEntityString(this.facilitySize));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("geoPointId",OrderMaxUtility.getValidEntityString(this.geoPointId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruDate",this.thruDate);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("closedDate",this.closedDate);
valueMap.put("facilitySizeUomId",this.facilitySizeUomId);
valueMap.put("defaultDimensionUomId",this.defaultDimensionUomId);
valueMap.put("oldSquareFootage",this.oldSquareFootage);
valueMap.put("description",this.description);
valueMap.put("infoString",this.infoString);
valueMap.put("ownerPartyId",this.ownerPartyId);
valueMap.put("defaultWeightUomId",this.defaultWeightUomId);
valueMap.put("facilityName",this.facilityName);
valueMap.put("openedDate",this.openedDate);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("defaultInventoryItemTypeId",this.defaultInventoryItemTypeId);
valueMap.put("extension",this.extension);
valueMap.put("parentFacilityId",this.parentFacilityId);
valueMap.put("primaryFacilityGroupId",this.primaryFacilityGroupId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("facilityTypeId",this.facilityTypeId);
valueMap.put("defaultDaysToShip",this.defaultDaysToShip);
valueMap.put("facilitySize",this.facilitySize);
valueMap.put("comments",this.comments);
valueMap.put("geoPointId",this.geoPointId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("FacilityAndContactMech");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.sql.Timestamp closedDate;
public java.sql.Timestamp getclosedDate() {
return closedDate;
}
public void setclosedDate( java.sql.Timestamp closedDate ) {
this.closedDate = closedDate;
}
private java.lang.String facilitySizeUomId;
public java.lang.String getfacilitySizeUomId() {
return facilitySizeUomId;
}
public void setfacilitySizeUomId( java.lang.String facilitySizeUomId ) {
this.facilitySizeUomId = facilitySizeUomId;
}
private java.lang.String defaultDimensionUomId;
public java.lang.String getdefaultDimensionUomId() {
return defaultDimensionUomId;
}
public void setdefaultDimensionUomId( java.lang.String defaultDimensionUomId ) {
this.defaultDimensionUomId = defaultDimensionUomId;
}
private java.lang.String oldSquareFootage;
public java.lang.String getoldSquareFootage() {
return oldSquareFootage;
}
public void setoldSquareFootage( java.lang.String oldSquareFootage ) {
this.oldSquareFootage = oldSquareFootage;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
private java.lang.String ownerPartyId;
public java.lang.String getownerPartyId() {
return ownerPartyId;
}
public void setownerPartyId( java.lang.String ownerPartyId ) {
this.ownerPartyId = ownerPartyId;
}
private java.lang.String defaultWeightUomId;
public java.lang.String getdefaultWeightUomId() {
return defaultWeightUomId;
}
public void setdefaultWeightUomId( java.lang.String defaultWeightUomId ) {
this.defaultWeightUomId = defaultWeightUomId;
}
private java.lang.String facilityName;
public java.lang.String getfacilityName() {
return facilityName;
}
public void setfacilityName( java.lang.String facilityName ) {
this.facilityName = facilityName;
}
private java.sql.Timestamp openedDate;
public java.sql.Timestamp getopenedDate() {
return openedDate;
}
public void setopenedDate( java.sql.Timestamp openedDate ) {
this.openedDate = openedDate;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String contactMechTypeId;
public java.lang.String getcontactMechTypeId() {
return contactMechTypeId;
}
public void setcontactMechTypeId( java.lang.String contactMechTypeId ) {
this.contactMechTypeId = contactMechTypeId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String defaultInventoryItemTypeId;
public java.lang.String getdefaultInventoryItemTypeId() {
return defaultInventoryItemTypeId;
}
public void setdefaultInventoryItemTypeId( java.lang.String defaultInventoryItemTypeId ) {
this.defaultInventoryItemTypeId = defaultInventoryItemTypeId;
}
private java.lang.String extension;
public java.lang.String getextension() {
return extension;
}
public void setextension( java.lang.String extension ) {
this.extension = extension;
}
private java.lang.String parentFacilityId;
public java.lang.String getparentFacilityId() {
return parentFacilityId;
}
public void setparentFacilityId( java.lang.String parentFacilityId ) {
this.parentFacilityId = parentFacilityId;
}
private java.lang.String primaryFacilityGroupId;
public java.lang.String getprimaryFacilityGroupId() {
return primaryFacilityGroupId;
}
public void setprimaryFacilityGroupId( java.lang.String primaryFacilityGroupId ) {
this.primaryFacilityGroupId = primaryFacilityGroupId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String facilityTypeId;
public java.lang.String getfacilityTypeId() {
return facilityTypeId;
}
public void setfacilityTypeId( java.lang.String facilityTypeId ) {
this.facilityTypeId = facilityTypeId;
}
private java.lang.Long defaultDaysToShip;
public java.lang.Long getdefaultDaysToShip() {
return defaultDaysToShip;
}
public void setdefaultDaysToShip( java.lang.Long defaultDaysToShip ) {
this.defaultDaysToShip = defaultDaysToShip;
}
private java.lang.String facilitySize;
public java.lang.String getfacilitySize() {
return facilitySize;
}
public void setfacilitySize( java.lang.String facilitySize ) {
this.facilitySize = facilitySize;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
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
        Collection<FacilityAndContactMech> objectList = new ArrayList<FacilityAndContactMech>();
        for (GenericValue genVal : genList) {
            objectList.add(new FacilityAndContactMech(genVal));
        }
        return objectList;
    }    
}
