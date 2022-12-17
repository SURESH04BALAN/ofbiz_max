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
public class ProductSearchConstraint implements GenericValueObjectInterface {
public static final String module =ProductSearchConstraint.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductSearchConstraint(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductSearchConstraint () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"constraintName","Constraint Name"},
{"isAnd","Is And"},
{"constraintSeqId","Constraint Seq Id"},
{"lowValue","Low Value"},
{"anySuffix","Any Suffix"},
{"removeStems","Remove Stems"},
{"highValue","High Value"},
{"productSearchResultId","Product Search Result Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"anyPrefix","Any Prefix"},
{"includeSubCategories","Include Sub Categories"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"infoString","Info String"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.constraintName = "";
this.isAnd = "";
this.constraintSeqId = "";
this.lowValue = "";
this.anySuffix = "";
this.removeStems = "";
this.highValue = "";
this.productSearchResultId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.anyPrefix = "";
this.includeSubCategories = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.infoString = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.constraintName = (java.lang.String) genVal.get("constraintName");
this.isAnd = (java.lang.String) genVal.get("isAnd");
this.constraintSeqId = (java.lang.String) genVal.get("constraintSeqId");
this.lowValue = (java.lang.String) genVal.get("lowValue");
this.anySuffix = (java.lang.String) genVal.get("anySuffix");
this.removeStems = (java.lang.String) genVal.get("removeStems");
this.highValue = (java.lang.String) genVal.get("highValue");
this.productSearchResultId = (java.lang.String) genVal.get("productSearchResultId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.anyPrefix = (java.lang.String) genVal.get("anyPrefix");
this.includeSubCategories = (java.lang.String) genVal.get("includeSubCategories");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.infoString = (java.lang.String) genVal.get("infoString");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("constraintName",OrderMaxUtility.getValidEntityString(this.constraintName));
val.set("isAnd",OrderMaxUtility.getValidEntityString(this.isAnd));
val.set("constraintSeqId",OrderMaxUtility.getValidEntityString(this.constraintSeqId));
val.set("lowValue",OrderMaxUtility.getValidEntityString(this.lowValue));
val.set("anySuffix",OrderMaxUtility.getValidEntityString(this.anySuffix));
val.set("removeStems",OrderMaxUtility.getValidEntityString(this.removeStems));
val.set("highValue",OrderMaxUtility.getValidEntityString(this.highValue));
val.set("productSearchResultId",OrderMaxUtility.getValidEntityString(this.productSearchResultId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("anyPrefix",OrderMaxUtility.getValidEntityString(this.anyPrefix));
val.set("includeSubCategories",OrderMaxUtility.getValidEntityString(this.includeSubCategories));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("constraintName",this.constraintName);
valueMap.put("isAnd",this.isAnd);
valueMap.put("constraintSeqId",this.constraintSeqId);
valueMap.put("lowValue",this.lowValue);
valueMap.put("anySuffix",this.anySuffix);
valueMap.put("removeStems",this.removeStems);
valueMap.put("highValue",this.highValue);
valueMap.put("productSearchResultId",this.productSearchResultId);
valueMap.put("anyPrefix",this.anyPrefix);
valueMap.put("includeSubCategories",this.includeSubCategories);
valueMap.put("infoString",this.infoString);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductSearchConstraint");
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
private java.lang.String constraintName;
public java.lang.String getconstraintName() {
return constraintName;
}
public void setconstraintName( java.lang.String constraintName ) {
this.constraintName = constraintName;
}
private java.lang.String isAnd;
public java.lang.String getisAnd() {
return isAnd;
}
public void setisAnd( java.lang.String isAnd ) {
this.isAnd = isAnd;
}
private java.lang.String constraintSeqId;
public java.lang.String getconstraintSeqId() {
return constraintSeqId;
}
public void setconstraintSeqId( java.lang.String constraintSeqId ) {
this.constraintSeqId = constraintSeqId;
}
private java.lang.String lowValue;
public java.lang.String getlowValue() {
return lowValue;
}
public void setlowValue( java.lang.String lowValue ) {
this.lowValue = lowValue;
}
private java.lang.String anySuffix;
public java.lang.String getanySuffix() {
return anySuffix;
}
public void setanySuffix( java.lang.String anySuffix ) {
this.anySuffix = anySuffix;
}
private java.lang.String removeStems;
public java.lang.String getremoveStems() {
return removeStems;
}
public void setremoveStems( java.lang.String removeStems ) {
this.removeStems = removeStems;
}
private java.lang.String highValue;
public java.lang.String gethighValue() {
return highValue;
}
public void sethighValue( java.lang.String highValue ) {
this.highValue = highValue;
}
private java.lang.String productSearchResultId;
public java.lang.String getproductSearchResultId() {
return productSearchResultId;
}
public void setproductSearchResultId( java.lang.String productSearchResultId ) {
this.productSearchResultId = productSearchResultId;
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
private java.lang.String anyPrefix;
public java.lang.String getanyPrefix() {
return anyPrefix;
}
public void setanyPrefix( java.lang.String anyPrefix ) {
this.anyPrefix = anyPrefix;
}
private java.lang.String includeSubCategories;
public java.lang.String getincludeSubCategories() {
return includeSubCategories;
}
public void setincludeSubCategories( java.lang.String includeSubCategories ) {
this.includeSubCategories = includeSubCategories;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductSearchConstraint> objectList = new ArrayList<ProductSearchConstraint>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductSearchConstraint(genVal));
        }
        return objectList;
    }    
}
