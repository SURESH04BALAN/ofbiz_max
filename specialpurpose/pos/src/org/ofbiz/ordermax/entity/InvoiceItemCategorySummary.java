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
public class InvoiceItemCategorySummary implements GenericValueObjectInterface {
public static final String module =InvoiceItemCategorySummary.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InvoiceItemCategorySummary(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InvoiceItemCategorySummary () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"amountTotal","Amount Total"},
{"invoiceDate","Invoice Date"},
{"invoiceTypeId","Invoice Type Id"},
{"partyId","Party Id"},
{"currencyUomId","Currency Uom Id"},
{"partyIdFrom","Party Id From"},
{"invoiceItemTypeId","Invoice Item Type Id"},
{"quantityTotal","Quantity Total"},
{"productCategoryId","Product Category Id"},
{"productId","Product Id"},
};
protected void initObject(){
this.statusId = "";
this.amountTotal = java.math.BigDecimal.ZERO;
this.invoiceDate = UtilDateTime.nowTimestamp();
this.invoiceTypeId = "";
this.partyId = "";
this.currencyUomId = "";
this.partyIdFrom = "";
this.invoiceItemTypeId = "";
this.quantityTotal = java.math.BigDecimal.ZERO;
this.productCategoryId = "";
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.amountTotal = (java.math.BigDecimal) genVal.get("amountTotal");
this.invoiceDate = (java.sql.Timestamp) genVal.get("invoiceDate");
this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.invoiceItemTypeId = (java.lang.String) genVal.get("invoiceItemTypeId");
this.quantityTotal = (java.math.BigDecimal) genVal.get("quantityTotal");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("amountTotal",OrderMaxUtility.getValidEntityBigDecimal(this.amountTotal));
val.set("invoiceDate",OrderMaxUtility.getValidEntityTimestamp(this.invoiceDate));
val.set("invoiceTypeId",OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("invoiceItemTypeId",OrderMaxUtility.getValidEntityString(this.invoiceItemTypeId));
val.set("quantityTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityTotal));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("amountTotal",this.amountTotal);
valueMap.put("invoiceDate",this.invoiceDate);
valueMap.put("invoiceTypeId",this.invoiceTypeId);
valueMap.put("partyId",this.partyId);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("invoiceItemTypeId",this.invoiceItemTypeId);
valueMap.put("quantityTotal",this.quantityTotal);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InvoiceItemCategorySummary");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.math.BigDecimal amountTotal;
public java.math.BigDecimal getamountTotal() {
return amountTotal;
}
public void setamountTotal( java.math.BigDecimal amountTotal ) {
this.amountTotal = amountTotal;
}
private java.sql.Timestamp invoiceDate;
public java.sql.Timestamp getinvoiceDate() {
return invoiceDate;
}
public void setinvoiceDate( java.sql.Timestamp invoiceDate ) {
this.invoiceDate = invoiceDate;
}
private java.lang.String invoiceTypeId;
public java.lang.String getinvoiceTypeId() {
return invoiceTypeId;
}
public void setinvoiceTypeId( java.lang.String invoiceTypeId ) {
this.invoiceTypeId = invoiceTypeId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String invoiceItemTypeId;
public java.lang.String getinvoiceItemTypeId() {
return invoiceItemTypeId;
}
public void setinvoiceItemTypeId( java.lang.String invoiceItemTypeId ) {
this.invoiceItemTypeId = invoiceItemTypeId;
}
private java.math.BigDecimal quantityTotal;
public java.math.BigDecimal getquantityTotal() {
return quantityTotal;
}
public void setquantityTotal( java.math.BigDecimal quantityTotal ) {
this.quantityTotal = quantityTotal;
}
private java.lang.String productCategoryId;
public java.lang.String getproductCategoryId() {
return productCategoryId;
}
public void setproductCategoryId( java.lang.String productCategoryId ) {
this.productCategoryId = productCategoryId;
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
        Collection<InvoiceItemCategorySummary> objectList = new ArrayList<InvoiceItemCategorySummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceItemCategorySummary(genVal));
        }
        return objectList;
    }    
}
