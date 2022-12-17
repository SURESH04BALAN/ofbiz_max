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
public class ProductAndPriceView implements GenericValueObjectInterface {
public static final String module =ProductAndPriceView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductAndPriceView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductAndPriceView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productPriceTypeId","Product Price Type Id"},
{"productTypeId","Product Type Id"},
{"internalName","Internal Name"},
{"thruDate","Thru Date"},
{"currencyUomId","Currency Uom Id"},
{"isVirtual","Is Virtual"},
{"productId","Product Id"},
{"price","Price"},
{"fromDate","From Date"},
{"description","Description"},
{"productPricePurposeId","Product Price Purpose Id"},
{"productName","Product Name"},
{"primaryProductCategoryId","Primary Product Category Id"},
};
protected void initObject(){
this.productPriceTypeId = "";
this.productTypeId = "";
this.internalName = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.currencyUomId = "";
this.isVirtual = "";
this.productId = "";
this.price = java.math.BigDecimal.ZERO;
this.fromDate = UtilDateTime.nowTimestamp();
this.description = "";
this.productPricePurposeId = "";
this.productName = "";
this.primaryProductCategoryId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productPriceTypeId = (java.lang.String) genVal.get("productPriceTypeId");
this.productTypeId = (java.lang.String) genVal.get("productTypeId");
this.internalName = (java.lang.String) genVal.get("internalName");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.isVirtual = (java.lang.String) genVal.get("isVirtual");
this.productId = (java.lang.String) genVal.get("productId");
this.price = (java.math.BigDecimal) genVal.get("price");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.description = (java.lang.String) genVal.get("description");
this.productPricePurposeId = (java.lang.String) genVal.get("productPricePurposeId");
this.productName = (java.lang.String) genVal.get("productName");
this.primaryProductCategoryId = (java.lang.String) genVal.get("primaryProductCategoryId");
}
protected void getGenericValue(GenericValue val) {
val.set("productPriceTypeId",OrderMaxUtility.getValidEntityString(this.productPriceTypeId));
val.set("productTypeId",OrderMaxUtility.getValidEntityString(this.productTypeId));
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("isVirtual",OrderMaxUtility.getValidEntityString(this.isVirtual));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("price",OrderMaxUtility.getValidEntityBigDecimal(this.price));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("productPricePurposeId",OrderMaxUtility.getValidEntityString(this.productPricePurposeId));
val.set("productName",OrderMaxUtility.getValidEntityString(this.productName));
val.set("primaryProductCategoryId",OrderMaxUtility.getValidEntityString(this.primaryProductCategoryId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productPriceTypeId",this.productPriceTypeId);
valueMap.put("productTypeId",this.productTypeId);
valueMap.put("internalName",this.internalName);
valueMap.put("thruDate",this.thruDate);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("isVirtual",this.isVirtual);
valueMap.put("productId",this.productId);
valueMap.put("price",this.price);
valueMap.put("fromDate",this.fromDate);
valueMap.put("description",this.description);
valueMap.put("productPricePurposeId",this.productPricePurposeId);
valueMap.put("productName",this.productName);
valueMap.put("primaryProductCategoryId",this.primaryProductCategoryId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductAndPriceView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String productPriceTypeId;
public java.lang.String getproductPriceTypeId() {
return productPriceTypeId;
}
public void setproductPriceTypeId( java.lang.String productPriceTypeId ) {
this.productPriceTypeId = productPriceTypeId;
}
private java.lang.String productTypeId;
public java.lang.String getproductTypeId() {
return productTypeId;
}
public void setproductTypeId( java.lang.String productTypeId ) {
this.productTypeId = productTypeId;
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
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String isVirtual;
public java.lang.String getisVirtual() {
return isVirtual;
}
public void setisVirtual( java.lang.String isVirtual ) {
this.isVirtual = isVirtual;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.math.BigDecimal price;
public java.math.BigDecimal getprice() {
return price;
}
public void setprice( java.math.BigDecimal price ) {
this.price = price;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String productPricePurposeId;
public java.lang.String getproductPricePurposeId() {
return productPricePurposeId;
}
public void setproductPricePurposeId( java.lang.String productPricePurposeId ) {
this.productPricePurposeId = productPricePurposeId;
}
private java.lang.String productName;
public java.lang.String getproductName() {
return productName;
}
public void setproductName( java.lang.String productName ) {
this.productName = productName;
}
private java.lang.String primaryProductCategoryId;
public java.lang.String getprimaryProductCategoryId() {
return primaryProductCategoryId;
}
public void setprimaryProductCategoryId( java.lang.String primaryProductCategoryId ) {
this.primaryProductCategoryId = primaryProductCategoryId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductAndPriceView> objectList = new ArrayList<ProductAndPriceView>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductAndPriceView(genVal));
        }
        return objectList;
    }    
}
