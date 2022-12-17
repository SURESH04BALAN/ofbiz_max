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
public class ProductStoreFacilityByOrder implements GenericValueObjectInterface {
public static final String module =ProductStoreFacilityByOrder.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductStoreFacilityByOrder(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductStoreFacilityByOrder () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"facilityName","Facility Name"},
{"productStoreId","Product Store Id"},
{"thruDate","Thru Date"},
{"storeName","Store Name"},
{"fromDate","From Date"},
{"facilityTypeId","Facility Type Id"},
{"facilityId","Facility Id"},
{"orderId","Order Id"},
{"sequenceNum","Sequence Num"},
};
protected void initObject(){
this.facilityName = "";
this.productStoreId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.storeName = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.facilityTypeId = "";
this.facilityId = "";
this.orderId = "";
this.sequenceNum = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.facilityName = (java.lang.String) genVal.get("facilityName");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.storeName = (java.lang.String) genVal.get("storeName");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.facilityTypeId = (java.lang.String) genVal.get("facilityTypeId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
}
protected void getGenericValue(GenericValue val) {
val.set("facilityName",OrderMaxUtility.getValidEntityString(this.facilityName));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("storeName",OrderMaxUtility.getValidEntityString(this.storeName));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("facilityTypeId",OrderMaxUtility.getValidEntityString(this.facilityTypeId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("facilityName",this.facilityName);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("storeName",this.storeName);
valueMap.put("fromDate",this.fromDate);
valueMap.put("facilityTypeId",this.facilityTypeId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("orderId",this.orderId);
valueMap.put("sequenceNum",this.sequenceNum);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductStoreFacilityByOrder");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String facilityName;
public java.lang.String getfacilityName() {
return facilityName;
}
public void setfacilityName( java.lang.String facilityName ) {
this.facilityName = facilityName;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String storeName;
public java.lang.String getstoreName() {
return storeName;
}
public void setstoreName( java.lang.String storeName ) {
this.storeName = storeName;
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
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStoreFacilityByOrder> objectList = new ArrayList<ProductStoreFacilityByOrder>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStoreFacilityByOrder(genVal));
        }
        return objectList;
    }    
}
