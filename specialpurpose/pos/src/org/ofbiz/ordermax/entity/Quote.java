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
public class Quote implements GenericValueObjectInterface {
public static final String module =Quote.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public Quote(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public Quote () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"quoteName","Quote Name"},
{"productStoreId","Product Store Id"},
{"quoteTypeId","Quote Type Id"},
{"quoteId","Quote Id"},
{"currencyUomId","Currency Uom Id"},
{"issueDate","Issue Date"},
{"validFromDate","Valid From Date"},
{"statusId","Status Id"},
{"createdTxStamp","Created Tx Stamp"},
{"partyId","Party Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"validThruDate","Valid Thru Date"},
{"salesChannelEnumId","Sales Channel Enum Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.quoteName = "";
this.productStoreId = "";
this.quoteTypeId = "";
this.quoteId = "";
this.currencyUomId = "";
this.issueDate = UtilDateTime.nowTimestamp();
this.validFromDate = UtilDateTime.nowTimestamp();
this.statusId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.partyId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.validThruDate = UtilDateTime.nowTimestamp();
this.salesChannelEnumId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.quoteName = (java.lang.String) genVal.get("quoteName");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.quoteTypeId = (java.lang.String) genVal.get("quoteTypeId");
this.quoteId = (java.lang.String) genVal.get("quoteId");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.issueDate = (java.sql.Timestamp) genVal.get("issueDate");
this.validFromDate = (java.sql.Timestamp) genVal.get("validFromDate");
this.statusId = (java.lang.String) genVal.get("statusId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.partyId = (java.lang.String) genVal.get("partyId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.validThruDate = (java.sql.Timestamp) genVal.get("validThruDate");
this.salesChannelEnumId = (java.lang.String) genVal.get("salesChannelEnumId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("quoteName",OrderMaxUtility.getValidEntityString(this.quoteName));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("quoteTypeId",OrderMaxUtility.getValidEntityString(this.quoteTypeId));
val.set("quoteId",OrderMaxUtility.getValidEntityString(this.quoteId));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("issueDate",OrderMaxUtility.getValidEntityTimestamp(this.issueDate));
val.set("validFromDate",OrderMaxUtility.getValidEntityTimestamp(this.validFromDate));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("validThruDate",OrderMaxUtility.getValidEntityTimestamp(this.validThruDate));
val.set("salesChannelEnumId",OrderMaxUtility.getValidEntityString(this.salesChannelEnumId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("quoteName",this.quoteName);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("quoteTypeId",this.quoteTypeId);
valueMap.put("quoteId",this.quoteId);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("issueDate",this.issueDate);
valueMap.put("validFromDate",this.validFromDate);
valueMap.put("statusId",this.statusId);
valueMap.put("partyId",this.partyId);
valueMap.put("description",this.description);
valueMap.put("validThruDate",this.validThruDate);
valueMap.put("salesChannelEnumId",this.salesChannelEnumId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("Quote");
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
private java.lang.String quoteName;
public java.lang.String getquoteName() {
return quoteName;
}
public void setquoteName( java.lang.String quoteName ) {
this.quoteName = quoteName;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String quoteTypeId;
public java.lang.String getquoteTypeId() {
return quoteTypeId;
}
public void setquoteTypeId( java.lang.String quoteTypeId ) {
this.quoteTypeId = quoteTypeId;
}
private java.lang.String quoteId;
public java.lang.String getquoteId() {
return quoteId;
}
public void setquoteId( java.lang.String quoteId ) {
this.quoteId = quoteId;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.sql.Timestamp issueDate;
public java.sql.Timestamp getissueDate() {
return issueDate;
}
public void setissueDate( java.sql.Timestamp issueDate ) {
this.issueDate = issueDate;
}
private java.sql.Timestamp validFromDate;
public java.sql.Timestamp getvalidFromDate() {
return validFromDate;
}
public void setvalidFromDate( java.sql.Timestamp validFromDate ) {
this.validFromDate = validFromDate;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Timestamp validThruDate;
public java.sql.Timestamp getvalidThruDate() {
return validThruDate;
}
public void setvalidThruDate( java.sql.Timestamp validThruDate ) {
this.validThruDate = validThruDate;
}
private java.lang.String salesChannelEnumId;
public java.lang.String getsalesChannelEnumId() {
return salesChannelEnumId;
}
public void setsalesChannelEnumId( java.lang.String salesChannelEnumId ) {
this.salesChannelEnumId = salesChannelEnumId;
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
        Collection<Quote> objectList = new ArrayList<Quote>();
        for (GenericValue genVal : genList) {
            objectList.add(new Quote(genVal));
        }
        return objectList;
    }    
}
