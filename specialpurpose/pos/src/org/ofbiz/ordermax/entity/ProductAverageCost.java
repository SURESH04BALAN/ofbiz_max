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
public class ProductAverageCost implements GenericValueObjectInterface {
public static final String module =ProductAverageCost.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductAverageCost(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductAverageCost () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"organizationPartyId","Organization Party Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"thruDate","Thru Date"},
{"fromDate","From Date"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"averageCost","Average Cost"},
{"productAverageCostTypeId","Product Average Cost Type Id"},
{"facilityId","Facility Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"productId","Product Id"},
};
protected void initObject(){
this.organizationPartyId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.thruDate = UtilDateTime.nowTimestamp();
this.fromDate = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.averageCost = java.math.BigDecimal.ZERO;
this.productAverageCostTypeId = "";
this.facilityId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.averageCost = (java.math.BigDecimal) genVal.get("averageCost");
this.productAverageCostTypeId = (java.lang.String) genVal.get("productAverageCostTypeId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("averageCost",OrderMaxUtility.getValidEntityBigDecimal(this.averageCost));
val.set("productAverageCostTypeId",OrderMaxUtility.getValidEntityString(this.productAverageCostTypeId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("fromDate",this.fromDate);
valueMap.put("averageCost",this.averageCost);
valueMap.put("productAverageCostTypeId",this.productAverageCostTypeId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductAverageCost");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
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
private java.math.BigDecimal averageCost;
public java.math.BigDecimal getaverageCost() {
return averageCost;
}
public void setaverageCost( java.math.BigDecimal averageCost ) {
this.averageCost = averageCost;
}
private java.lang.String productAverageCostTypeId;
public java.lang.String getproductAverageCostTypeId() {
return productAverageCostTypeId;
}
public void setproductAverageCostTypeId( java.lang.String productAverageCostTypeId ) {
this.productAverageCostTypeId = productAverageCostTypeId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductAverageCost> objectList = new ArrayList<ProductAverageCost>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductAverageCost(genVal));
        }
        return objectList;
    }    
}
