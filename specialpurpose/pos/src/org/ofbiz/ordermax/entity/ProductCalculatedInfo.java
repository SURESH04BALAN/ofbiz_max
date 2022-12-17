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
public class ProductCalculatedInfo implements GenericValueObjectInterface {
public static final String module =ProductCalculatedInfo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductCalculatedInfo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductCalculatedInfo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"totalTimesViewed","Total Times Viewed"},
{"totalQuantityOrdered","Total Quantity Ordered"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"averageCustomerRating","Average Customer Rating"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"productId","Product Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.totalTimesViewed = new Long(0);
this.totalQuantityOrdered = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.averageCustomerRating = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.totalTimesViewed = (java.lang.Long) genVal.get("totalTimesViewed");
this.totalQuantityOrdered = (java.lang.String) genVal.get("totalQuantityOrdered");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.averageCustomerRating = (java.lang.String) genVal.get("averageCustomerRating");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("totalTimesViewed",this.totalTimesViewed);
val.set("totalQuantityOrdered",OrderMaxUtility.getValidEntityString(this.totalQuantityOrdered));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("averageCustomerRating",OrderMaxUtility.getValidEntityString(this.averageCustomerRating));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("totalTimesViewed",this.totalTimesViewed);
valueMap.put("totalQuantityOrdered",this.totalQuantityOrdered);
valueMap.put("averageCustomerRating",this.averageCustomerRating);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductCalculatedInfo");
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
private java.lang.Long totalTimesViewed;
public java.lang.Long gettotalTimesViewed() {
return totalTimesViewed;
}
public void settotalTimesViewed( java.lang.Long totalTimesViewed ) {
this.totalTimesViewed = totalTimesViewed;
}
private java.lang.String totalQuantityOrdered;
public java.lang.String gettotalQuantityOrdered() {
return totalQuantityOrdered;
}
public void settotalQuantityOrdered( java.lang.String totalQuantityOrdered ) {
this.totalQuantityOrdered = totalQuantityOrdered;
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
private java.lang.String averageCustomerRating;
public java.lang.String getaverageCustomerRating() {
return averageCustomerRating;
}
public void setaverageCustomerRating( java.lang.String averageCustomerRating ) {
this.averageCustomerRating = averageCustomerRating;
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
        Collection<ProductCalculatedInfo> objectList = new ArrayList<ProductCalculatedInfo>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductCalculatedInfo(genVal));
        }
        return objectList;
    }    
}
