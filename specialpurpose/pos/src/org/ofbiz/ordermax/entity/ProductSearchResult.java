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
public class ProductSearchResult implements GenericValueObjectInterface {
public static final String module =ProductSearchResult.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductSearchResult(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductSearchResult () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productSearchResultId","Product Search Result Id"},
{"secondsTotal","Seconds Total"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"orderByName","Order By Name"},
{"numResults","Num Results"},
{"visitId","Visit Id"},
{"searchDate","Search Date"},
{"isAscending","Is Ascending"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.productSearchResultId = "";
this.secondsTotal = new Double(0);
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.orderByName = "";
this.numResults = new Long(0);
this.visitId = "";
this.searchDate = UtilDateTime.nowTimestamp();
this.isAscending = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productSearchResultId = (java.lang.String) genVal.get("productSearchResultId");
this.secondsTotal = (java.lang.Double) genVal.get("secondsTotal");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.orderByName = (java.lang.String) genVal.get("orderByName");
this.numResults = (java.lang.Long) genVal.get("numResults");
this.visitId = (java.lang.String) genVal.get("visitId");
this.searchDate = (java.sql.Timestamp) genVal.get("searchDate");
this.isAscending = (java.lang.String) genVal.get("isAscending");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("productSearchResultId",OrderMaxUtility.getValidEntityString(this.productSearchResultId));
val.set("secondsTotal",OrderMaxUtility.getValidEntityDouble(this.secondsTotal));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("orderByName",OrderMaxUtility.getValidEntityString(this.orderByName));
val.set("numResults",this.numResults);
val.set("visitId",OrderMaxUtility.getValidEntityString(this.visitId));
val.set("searchDate",OrderMaxUtility.getValidEntityTimestamp(this.searchDate));
val.set("isAscending",OrderMaxUtility.getValidEntityString(this.isAscending));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productSearchResultId",this.productSearchResultId);
valueMap.put("secondsTotal",this.secondsTotal);
valueMap.put("orderByName",this.orderByName);
valueMap.put("numResults",this.numResults);
valueMap.put("visitId",this.visitId);
valueMap.put("searchDate",this.searchDate);
valueMap.put("isAscending",this.isAscending);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductSearchResult");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String productSearchResultId;
public java.lang.String getproductSearchResultId() {
return productSearchResultId;
}
public void setproductSearchResultId( java.lang.String productSearchResultId ) {
this.productSearchResultId = productSearchResultId;
}
private java.lang.Double secondsTotal;
public java.lang.Double getsecondsTotal() {
return secondsTotal;
}
public void setsecondsTotal( java.lang.Double secondsTotal ) {
this.secondsTotal = secondsTotal;
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
private java.lang.String orderByName;
public java.lang.String getorderByName() {
return orderByName;
}
public void setorderByName( java.lang.String orderByName ) {
this.orderByName = orderByName;
}
private java.lang.Long numResults;
public java.lang.Long getnumResults() {
return numResults;
}
public void setnumResults( java.lang.Long numResults ) {
this.numResults = numResults;
}
private java.lang.String visitId;
public java.lang.String getvisitId() {
return visitId;
}
public void setvisitId( java.lang.String visitId ) {
this.visitId = visitId;
}
private java.sql.Timestamp searchDate;
public java.sql.Timestamp getsearchDate() {
return searchDate;
}
public void setsearchDate( java.sql.Timestamp searchDate ) {
this.searchDate = searchDate;
}
private java.lang.String isAscending;
public java.lang.String getisAscending() {
return isAscending;
}
public void setisAscending( java.lang.String isAscending ) {
this.isAscending = isAscending;
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
        Collection<ProductSearchResult> objectList = new ArrayList<ProductSearchResult>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductSearchResult(genVal));
        }
        return objectList;
    }    
}
