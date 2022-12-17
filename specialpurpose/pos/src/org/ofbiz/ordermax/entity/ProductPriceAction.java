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
public class ProductPriceAction implements GenericValueObjectInterface {
public static final String module =ProductPriceAction.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductPriceAction(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductPriceAction () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"amount","Amount"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"productPriceRuleId","Product Price Rule Id"},
{"productPriceActionTypeId","Product Price Action Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"productPriceActionSeqId","Product Price Action Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"rateCode","Rate Code"},
};
protected void initObject(){
this.amount = java.math.BigDecimal.ZERO;
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productPriceRuleId = "";
this.productPriceActionTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.productPriceActionSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.rateCode = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productPriceRuleId = (java.lang.String) genVal.get("productPriceRuleId");
this.productPriceActionTypeId = (java.lang.String) genVal.get("productPriceActionTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.productPriceActionSeqId = (java.lang.String) genVal.get("productPriceActionSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.rateCode = (java.lang.String) genVal.get("rateCode");
}
protected void getGenericValue(GenericValue val) {
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productPriceRuleId",OrderMaxUtility.getValidEntityString(this.productPriceRuleId));
val.set("productPriceActionTypeId",OrderMaxUtility.getValidEntityString(this.productPriceActionTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("productPriceActionSeqId",OrderMaxUtility.getValidEntityString(this.productPriceActionSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("rateCode",OrderMaxUtility.getValidEntityString(this.rateCode));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("amount",this.amount);
valueMap.put("productPriceRuleId",this.productPriceRuleId);
valueMap.put("productPriceActionTypeId",this.productPriceActionTypeId);
valueMap.put("productPriceActionSeqId",this.productPriceActionSeqId);
valueMap.put("rateCode",this.rateCode);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductPriceAction");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String productPriceRuleId;
public java.lang.String getproductPriceRuleId() {
return productPriceRuleId;
}
public void setproductPriceRuleId( java.lang.String productPriceRuleId ) {
this.productPriceRuleId = productPriceRuleId;
}
private java.lang.String productPriceActionTypeId;
public java.lang.String getproductPriceActionTypeId() {
return productPriceActionTypeId;
}
public void setproductPriceActionTypeId( java.lang.String productPriceActionTypeId ) {
this.productPriceActionTypeId = productPriceActionTypeId;
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
private java.lang.String productPriceActionSeqId;
public java.lang.String getproductPriceActionSeqId() {
return productPriceActionSeqId;
}
public void setproductPriceActionSeqId( java.lang.String productPriceActionSeqId ) {
this.productPriceActionSeqId = productPriceActionSeqId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String rateCode;
public java.lang.String getrateCode() {
return rateCode;
}
public void setrateCode( java.lang.String rateCode ) {
this.rateCode = rateCode;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductPriceAction> objectList = new ArrayList<ProductPriceAction>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductPriceAction(genVal));
        }
        return objectList;
    }    
}
