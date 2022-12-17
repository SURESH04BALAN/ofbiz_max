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
public class ProductAndAssoc implements GenericValueObjectInterface {
public static final String module =ProductAndAssoc.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductAndAssoc(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductAndAssoc () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"productAssocTypeId","Product Assoc Type Id"},
{"internalName","Internal Name"},
{"productIdTo","Product Id To"},
{"fromDate","From Date"},
{"quantity","Quantity"},
{"productId","Product Id"},
};
protected void initObject(){
this.productAssocTypeId = "";
this.internalName = "";
this.productIdTo = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.quantity = java.math.BigDecimal.ZERO;
this.productId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.productAssocTypeId = (java.lang.String) genVal.get("productAssocTypeId");
this.internalName = (java.lang.String) genVal.get("internalName");
this.productIdTo = (java.lang.String) genVal.get("productIdTo");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.productId = (java.lang.String) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("productAssocTypeId",OrderMaxUtility.getValidEntityString(this.productAssocTypeId));
val.set("internalName",OrderMaxUtility.getValidEntityString(this.internalName));
val.set("productIdTo",OrderMaxUtility.getValidEntityString(this.productIdTo));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productAssocTypeId",this.productAssocTypeId);
valueMap.put("internalName",this.internalName);
valueMap.put("productIdTo",this.productIdTo);
valueMap.put("fromDate",this.fromDate);
valueMap.put("quantity",this.quantity);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductAndAssoc");
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
private java.lang.String productIdTo;
public java.lang.String getproductIdTo() {
return productIdTo;
}
public void setproductIdTo( java.lang.String productIdTo ) {
this.productIdTo = productIdTo;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
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
        Collection<ProductAndAssoc> objectList = new ArrayList<ProductAndAssoc>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductAndAssoc(genVal));
        }
        return objectList;
    }    
}
