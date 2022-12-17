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
public class ProductVirtualAndAssocPrices implements GenericValueObjectInterface {
public static final String module =ProductVirtualAndAssocPrices.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductVirtualAndAssocPrices(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductVirtualAndAssocPrices () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productAssocTypeId","Product Assoc Type Id"},
{"internalName","Internal Name"},
{"thruDate","Thru Date"},
{"assocMaxPrice","Assoc Max Price"},
{"assocCurrencyUomId","Assoc Currency Uom Id"},
{"assocProductStoreGroupId","Assoc Product Store Group Id"},
{"assocProductId","Assoc Product Id"},
{"productId","Product Id"},
{"assocPriceFromDate","Assoc Price From Date"},
{"fromDate","From Date"},
{"assocProductCount","Assoc Product Count"},
{"assocPriceThruDate","Assoc Price Thru Date"},
{"assocMinPrice","Assoc Min Price"},
{"productName","Product Name"},
{"assocPriceTypeId","Assoc Price Type Id"},
};
protected void initObject(){
this.productAssocTypeId = "";
this.internalName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.assocMaxPrice = java.math.BigDecimal.ZERO;
this.assocCurrencyUomId = "";
this.assocProductStoreGroupId = "";
this.assocProductId = "";
this.productId = "";
this.assocPriceFromDate = UtilDateTime.nowTimestamp();
this.fromDate = UtilDateTime.nowTimestamp();
this.assocProductCount = new Long(0);
this.assocPriceThruDate = UtilDateTime.nowTimestamp();
this.assocMinPrice = java.math.BigDecimal.ZERO;
this.productName = "";
this.assocPriceTypeId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productAssocTypeId = (java.lang.String) genVal.get("productAssocTypeId");
this.internalName = (java.lang.String) genVal.get("internalName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.assocMaxPrice = (java.math.BigDecimal) genVal.get("assocMaxPrice");
this.assocCurrencyUomId = (java.lang.String) genVal.get("assocCurrencyUomId");
this.assocProductStoreGroupId = (java.lang.String) genVal.get("assocProductStoreGroupId");
this.assocProductId = (java.lang.String) genVal.get("assocProductId");
this.productId = (java.lang.String) genVal.get("productId");
this.assocPriceFromDate = (java.sql.Timestamp) genVal.get("assocPriceFromDate");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.assocProductCount = (java.lang.Long) genVal.get("assocProductCount");
this.assocPriceThruDate = (java.sql.Timestamp) genVal.get("assocPriceThruDate");
this.assocMinPrice = (java.math.BigDecimal) genVal.get("assocMinPrice");
this.productName = (java.lang.String) genVal.get("productName");
this.assocPriceTypeId = (java.lang.String) genVal.get("assocPriceTypeId");
}
protected void getGenericValue(GenericValue val) {
val.set("productAssocTypeId",OrderMaxUtility.getValidEntityString(this.productAssocTypeId));
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("assocMaxPrice",OrderMaxUtility.getValidEntityBigDecimal(this.assocMaxPrice));
val.set("assocCurrencyUomId",OrderMaxUtility.getValidEntityString(this.assocCurrencyUomId));
val.set("assocProductStoreGroupId",OrderMaxUtility.getValidEntityString(this.assocProductStoreGroupId));
val.set("assocProductId",OrderMaxUtility.getValidEntityString(this.assocProductId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("assocPriceFromDate",OrderMaxUtility.getValidEntityTimestamp(this.assocPriceFromDate));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("assocProductCount",this.assocProductCount);
val.set("assocPriceThruDate",OrderMaxUtility.getValidEntityTimestamp(this.assocPriceThruDate));
val.set("assocMinPrice",OrderMaxUtility.getValidEntityBigDecimal(this.assocMinPrice));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("assocPriceTypeId",OrderMaxUtility.getValidEntityString(this.assocPriceTypeId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productAssocTypeId",this.productAssocTypeId);
valueMap.put("internalName",this.internalName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("assocMaxPrice",this.assocMaxPrice);
valueMap.put("assocCurrencyUomId",this.assocCurrencyUomId);
valueMap.put("assocProductStoreGroupId",this.assocProductStoreGroupId);
valueMap.put("assocProductId",this.assocProductId);
valueMap.put("productId",this.productId);
valueMap.put("assocPriceFromDate",this.assocPriceFromDate);
valueMap.put("fromDate",this.fromDate);
valueMap.put("assocProductCount",this.assocProductCount);
valueMap.put("assocPriceThruDate",this.assocPriceThruDate);
valueMap.put("assocMinPrice",this.assocMinPrice);
valueMap.put("productName",this.productName);
valueMap.put("assocPriceTypeId",this.assocPriceTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductVirtualAndAssocPrices");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String productAssocTypeId;
public java.lang.String getproductAssocTypeId() {
return productAssocTypeId;
}
public void setproductAssocTypeId( java.lang.String productAssocTypeId ) {
this.productAssocTypeId = productAssocTypeId;
}
private java.lang.String internalName;
public java.lang.String getinternalName() {
return internalName;
}
public void setinternalName( java.lang.String internalName ) {
this.internalName = internalName;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.math.BigDecimal assocMaxPrice;
public java.math.BigDecimal getassocMaxPrice() {
return assocMaxPrice;
}
public void setassocMaxPrice( java.math.BigDecimal assocMaxPrice ) {
this.assocMaxPrice = assocMaxPrice;
}
private java.lang.String assocCurrencyUomId;
public java.lang.String getassocCurrencyUomId() {
return assocCurrencyUomId;
}
public void setassocCurrencyUomId( java.lang.String assocCurrencyUomId ) {
this.assocCurrencyUomId = assocCurrencyUomId;
}
private java.lang.String assocProductStoreGroupId;
public java.lang.String getassocProductStoreGroupId() {
return assocProductStoreGroupId;
}
public void setassocProductStoreGroupId( java.lang.String assocProductStoreGroupId ) {
this.assocProductStoreGroupId = assocProductStoreGroupId;
}
private java.lang.String assocProductId;
public java.lang.String getassocProductId() {
return assocProductId;
}
public void setassocProductId( java.lang.String assocProductId ) {
this.assocProductId = assocProductId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.sql.Timestamp assocPriceFromDate;
public java.sql.Timestamp getassocPriceFromDate() {
return assocPriceFromDate;
}
public void setassocPriceFromDate( java.sql.Timestamp assocPriceFromDate ) {
this.assocPriceFromDate = assocPriceFromDate;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.Long assocProductCount;
public java.lang.Long getassocProductCount() {
return assocProductCount;
}
public void setassocProductCount( java.lang.Long assocProductCount ) {
this.assocProductCount = assocProductCount;
}
private java.sql.Timestamp assocPriceThruDate;
public java.sql.Timestamp getassocPriceThruDate() {
return assocPriceThruDate;
}
public void setassocPriceThruDate( java.sql.Timestamp assocPriceThruDate ) {
this.assocPriceThruDate = assocPriceThruDate;
}
private java.math.BigDecimal assocMinPrice;
public java.math.BigDecimal getassocMinPrice() {
return assocMinPrice;
}
public void setassocMinPrice( java.math.BigDecimal assocMinPrice ) {
this.assocMinPrice = assocMinPrice;
}
private java.lang.String productName;
public java.lang.String getproductName() {
return productName;
}
public void setproductName( java.lang.String productName ) {
this.productName = productName;
}
private java.lang.String assocPriceTypeId;
public java.lang.String getassocPriceTypeId() {
return assocPriceTypeId;
}
public void setassocPriceTypeId( java.lang.String assocPriceTypeId ) {
this.assocPriceTypeId = assocPriceTypeId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductVirtualAndAssocPrices> objectList = new ArrayList<ProductVirtualAndAssocPrices>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductVirtualAndAssocPrices(genVal));
        }
        return objectList;
    }    
}
