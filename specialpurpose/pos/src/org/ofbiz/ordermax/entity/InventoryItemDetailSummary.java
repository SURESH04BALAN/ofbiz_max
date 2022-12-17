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
public class InventoryItemDetailSummary implements GenericValueObjectInterface {
public static final String module =InventoryItemDetailSummary.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InventoryItemDetailSummary(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InventoryItemDetailSummary () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"accountingQuantityTotal","Accounting Quantity Total"},
{"quantityOnHandTotal","Quantity On Hand Total"},
{"availableToPromiseTotal","Available To Promise Total"},
{"inventoryItemId","Inventory Item Id"},
};
protected void initObject(){
this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
this.inventoryItemId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
}
protected void getGenericValue(GenericValue val) {
val.set("accountingQuantityTotal",OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
val.set("quantityOnHandTotal",OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
val.set("availableToPromiseTotal",OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("accountingQuantityTotal",this.accountingQuantityTotal);
valueMap.put("quantityOnHandTotal",this.quantityOnHandTotal);
valueMap.put("availableToPromiseTotal",this.availableToPromiseTotal);
valueMap.put("inventoryItemId",this.inventoryItemId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InventoryItemDetailSummary");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal accountingQuantityTotal;
public java.math.BigDecimal getaccountingQuantityTotal() {
return accountingQuantityTotal;
}
public void setaccountingQuantityTotal( java.math.BigDecimal accountingQuantityTotal ) {
this.accountingQuantityTotal = accountingQuantityTotal;
}
private java.math.BigDecimal quantityOnHandTotal;
public java.math.BigDecimal getquantityOnHandTotal() {
return quantityOnHandTotal;
}
public void setquantityOnHandTotal( java.math.BigDecimal quantityOnHandTotal ) {
this.quantityOnHandTotal = quantityOnHandTotal;
}
private java.math.BigDecimal availableToPromiseTotal;
public java.math.BigDecimal getavailableToPromiseTotal() {
return availableToPromiseTotal;
}
public void setavailableToPromiseTotal( java.math.BigDecimal availableToPromiseTotal ) {
this.availableToPromiseTotal = availableToPromiseTotal;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryItemDetailSummary> objectList = new ArrayList<InventoryItemDetailSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItemDetailSummary(genVal));
        }
        return objectList;
    }    
}
