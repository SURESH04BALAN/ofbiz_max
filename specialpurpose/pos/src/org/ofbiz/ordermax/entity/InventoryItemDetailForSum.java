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
public class InventoryItemDetailForSum implements GenericValueObjectInterface {
public static final String module =InventoryItemDetailForSum.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InventoryItemDetailForSum(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InventoryItemDetailForSum () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"currencyUomId","Currency Uom Id"},
{"facilityId","Facility Id"},
{"accountingQuantityDiff","Accounting Quantity Diff"},
{"productId","Product Id"},
{"quantityOnHandDiff","Quantity On Hand Diff"},
{"quantityOnHandSum","Quantity On Hand Sum"},
{"inventoryItemTypeId","Inventory Item Type Id"},
{"unitCost","Unit Cost"},
{"accountingQuantitySum","Accounting Quantity Sum"},
{"effectiveDate","Effective Date"},
{"orderId","Order Id"},
{"ownerPartyId","Owner Party Id"},
};
protected void initObject(){
this.currencyUomId = "";
this.facilityId = "";
this.accountingQuantityDiff = java.math.BigDecimal.ZERO;
this.productId = "";
this.quantityOnHandDiff = java.math.BigDecimal.ZERO;
this.quantityOnHandSum = java.math.BigDecimal.ZERO;
this.inventoryItemTypeId = "";
this.unitCost = java.math.BigDecimal.ZERO;
this.accountingQuantitySum = java.math.BigDecimal.ZERO;
this.effectiveDate = UtilDateTime.nowTimestamp();
this.orderId = "";
this.ownerPartyId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.facilityId = (java.lang.String) genVal.get("facilityId");
this.accountingQuantityDiff = (java.math.BigDecimal) genVal.get("accountingQuantityDiff");
this.productId = (java.lang.String) genVal.get("productId");
this.quantityOnHandDiff = (java.math.BigDecimal) genVal.get("quantityOnHandDiff");
this.quantityOnHandSum = (java.math.BigDecimal) genVal.get("quantityOnHandSum");
this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
this.accountingQuantitySum = (java.math.BigDecimal) genVal.get("accountingQuantitySum");
this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
this.orderId = (java.lang.String) genVal.get("orderId");
this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
}
protected void getGenericValue(GenericValue val) {
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("facilityId",OrderMaxUtility.getValidEntityString(this.facilityId));
val.set("accountingQuantityDiff",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityDiff));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("quantityOnHandDiff",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandDiff));
val.set("quantityOnHandSum",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandSum));
val.set("inventoryItemTypeId",OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
val.set("unitCost",OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
val.set("accountingQuantitySum",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantitySum));
val.set("effectiveDate",OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("ownerPartyId",OrderMaxUtility.getValidEntityString(this.ownerPartyId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("facilityId",this.facilityId);
valueMap.put("accountingQuantityDiff",this.accountingQuantityDiff);
valueMap.put("productId",this.productId);
valueMap.put("quantityOnHandDiff",this.quantityOnHandDiff);
valueMap.put("quantityOnHandSum",this.quantityOnHandSum);
valueMap.put("inventoryItemTypeId",this.inventoryItemTypeId);
valueMap.put("unitCost",this.unitCost);
valueMap.put("accountingQuantitySum",this.accountingQuantitySum);
valueMap.put("effectiveDate",this.effectiveDate);
valueMap.put("orderId",this.orderId);
valueMap.put("ownerPartyId",this.ownerPartyId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InventoryItemDetailForSum");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String facilityId;
public java.lang.String getfacilityId() {
return facilityId;
}
public void setfacilityId( java.lang.String facilityId ) {
this.facilityId = facilityId;
}
private java.math.BigDecimal accountingQuantityDiff;
public java.math.BigDecimal getaccountingQuantityDiff() {
return accountingQuantityDiff;
}
public void setaccountingQuantityDiff( java.math.BigDecimal accountingQuantityDiff ) {
this.accountingQuantityDiff = accountingQuantityDiff;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.math.BigDecimal quantityOnHandDiff;
public java.math.BigDecimal getquantityOnHandDiff() {
return quantityOnHandDiff;
}
public void setquantityOnHandDiff( java.math.BigDecimal quantityOnHandDiff ) {
this.quantityOnHandDiff = quantityOnHandDiff;
}
private java.math.BigDecimal quantityOnHandSum;
public java.math.BigDecimal getquantityOnHandSum() {
return quantityOnHandSum;
}
public void setquantityOnHandSum( java.math.BigDecimal quantityOnHandSum ) {
this.quantityOnHandSum = quantityOnHandSum;
}
private java.lang.String inventoryItemTypeId;
public java.lang.String getinventoryItemTypeId() {
return inventoryItemTypeId;
}
public void setinventoryItemTypeId( java.lang.String inventoryItemTypeId ) {
this.inventoryItemTypeId = inventoryItemTypeId;
}
private java.math.BigDecimal unitCost;
public java.math.BigDecimal getunitCost() {
return unitCost;
}
public void setunitCost( java.math.BigDecimal unitCost ) {
this.unitCost = unitCost;
}
private java.math.BigDecimal accountingQuantitySum;
public java.math.BigDecimal getaccountingQuantitySum() {
return accountingQuantitySum;
}
public void setaccountingQuantitySum( java.math.BigDecimal accountingQuantitySum ) {
this.accountingQuantitySum = accountingQuantitySum;
}
private java.sql.Timestamp effectiveDate;
public java.sql.Timestamp geteffectiveDate() {
return effectiveDate;
}
public void seteffectiveDate( java.sql.Timestamp effectiveDate ) {
this.effectiveDate = effectiveDate;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String ownerPartyId;
public java.lang.String getownerPartyId() {
return ownerPartyId;
}
public void setownerPartyId( java.lang.String ownerPartyId ) {
this.ownerPartyId = ownerPartyId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryItemDetailForSum> objectList = new ArrayList<InventoryItemDetailForSum>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItemDetailForSum(genVal));
        }
        return objectList;
    }    
}
