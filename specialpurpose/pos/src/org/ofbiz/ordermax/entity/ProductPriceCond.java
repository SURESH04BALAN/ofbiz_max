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
public class ProductPriceCond implements GenericValueObjectInterface {
public static final String module =ProductPriceCond.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductPriceCond(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductPriceCond () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"productPriceRuleId","Product Price Rule Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"operatorEnumId","Operator Enum Id"},
{"inputParamEnumId","Input Param Enum Id"},
{"condValue","Cond Value"},
{"productPriceCondSeqId","Product Price Cond Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productPriceRuleId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.operatorEnumId = "";
this.inputParamEnumId = "";
this.condValue = "";
this.productPriceCondSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productPriceRuleId = (java.lang.String) genVal.get("productPriceRuleId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.operatorEnumId = (java.lang.String) genVal.get("operatorEnumId");
this.inputParamEnumId = (java.lang.String) genVal.get("inputParamEnumId");
this.condValue = (java.lang.String) genVal.get("condValue");
this.productPriceCondSeqId = (java.lang.String) genVal.get("productPriceCondSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productPriceRuleId",OrderMaxUtility.getValidEntityString(this.productPriceRuleId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("operatorEnumId",OrderMaxUtility.getValidEntityString(this.operatorEnumId));
val.set("inputParamEnumId",OrderMaxUtility.getValidEntityString(this.inputParamEnumId));
val.set("condValue",OrderMaxUtility.getValidEntityString(this.condValue));
val.set("productPriceCondSeqId",OrderMaxUtility.getValidEntityString(this.productPriceCondSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productPriceRuleId",this.productPriceRuleId);
valueMap.put("operatorEnumId",this.operatorEnumId);
valueMap.put("inputParamEnumId",this.inputParamEnumId);
valueMap.put("condValue",this.condValue);
valueMap.put("productPriceCondSeqId",this.productPriceCondSeqId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductPriceCond");
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
private java.lang.String productPriceRuleId;
public java.lang.String getproductPriceRuleId() {
return productPriceRuleId;
}
public void setproductPriceRuleId( java.lang.String productPriceRuleId ) {
this.productPriceRuleId = productPriceRuleId;
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
private java.lang.String operatorEnumId;
public java.lang.String getoperatorEnumId() {
return operatorEnumId;
}
public void setoperatorEnumId( java.lang.String operatorEnumId ) {
this.operatorEnumId = operatorEnumId;
}
private java.lang.String inputParamEnumId;
public java.lang.String getinputParamEnumId() {
return inputParamEnumId;
}
public void setinputParamEnumId( java.lang.String inputParamEnumId ) {
this.inputParamEnumId = inputParamEnumId;
}
private java.lang.String condValue;
public java.lang.String getcondValue() {
return condValue;
}
public void setcondValue( java.lang.String condValue ) {
this.condValue = condValue;
}
private java.lang.String productPriceCondSeqId;
public java.lang.String getproductPriceCondSeqId() {
return productPriceCondSeqId;
}
public void setproductPriceCondSeqId( java.lang.String productPriceCondSeqId ) {
this.productPriceCondSeqId = productPriceCondSeqId;
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
        Collection<ProductPriceCond> objectList = new ArrayList<ProductPriceCond>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductPriceCond(genVal));
        }
        return objectList;
    }    
}
