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
public class OrderItemPriceInfo implements GenericValueObjectInterface {
public static final String module =OrderItemPriceInfo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemPriceInfo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemPriceInfo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"productPriceRuleId","Product Price Rule Id"},
{"orderItemPriceInfoId","Order Item Price Info Id"},
{"productPriceActionSeqId","Product Price Action Seq Id"},
{"rateCode","Rate Code"},
{"orderItemSeqId","Order Item Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"modifyAmount","Modify Amount"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"orderId","Order Id"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productPriceRuleId = "";
this.orderItemPriceInfoId = "";
this.productPriceActionSeqId = "";
this.rateCode = "";
this.orderItemSeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.modifyAmount = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productPriceRuleId = (java.lang.String) genVal.get("productPriceRuleId");
this.orderItemPriceInfoId = (java.lang.String) genVal.get("orderItemPriceInfoId");
this.productPriceActionSeqId = (java.lang.String) genVal.get("productPriceActionSeqId");
this.rateCode = (java.lang.String) genVal.get("rateCode");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.modifyAmount = (java.lang.String) genVal.get("modifyAmount");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productPriceRuleId",OrderMaxUtility.getValidEntityString(this.productPriceRuleId));
val.set("orderItemPriceInfoId",OrderMaxUtility.getValidEntityString(this.orderItemPriceInfoId));
val.set("productPriceActionSeqId",OrderMaxUtility.getValidEntityString(this.productPriceActionSeqId));
val.set("rateCode",OrderMaxUtility.getValidEntityString(this.rateCode));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("modifyAmount",OrderMaxUtility.getValidEntityString(this.modifyAmount));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productPriceRuleId",this.productPriceRuleId);
valueMap.put("orderItemPriceInfoId",this.orderItemPriceInfoId);
valueMap.put("productPriceActionSeqId",this.productPriceActionSeqId);
valueMap.put("rateCode",this.rateCode);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("modifyAmount",this.modifyAmount);
valueMap.put("description",this.description);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemPriceInfo");
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
private java.lang.String orderItemPriceInfoId;
public java.lang.String getorderItemPriceInfoId() {
return orderItemPriceInfoId;
}
public void setorderItemPriceInfoId( java.lang.String orderItemPriceInfoId ) {
this.orderItemPriceInfoId = orderItemPriceInfoId;
}
private java.lang.String productPriceActionSeqId;
public java.lang.String getproductPriceActionSeqId() {
return productPriceActionSeqId;
}
public void setproductPriceActionSeqId( java.lang.String productPriceActionSeqId ) {
this.productPriceActionSeqId = productPriceActionSeqId;
}
private java.lang.String rateCode;
public java.lang.String getrateCode() {
return rateCode;
}
public void setrateCode( java.lang.String rateCode ) {
this.rateCode = rateCode;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String modifyAmount;
public java.lang.String getmodifyAmount() {
return modifyAmount;
}
public void setmodifyAmount( java.lang.String modifyAmount ) {
this.modifyAmount = modifyAmount;
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
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItemPriceInfo> objectList = new ArrayList<OrderItemPriceInfo>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemPriceInfo(genVal));
        }
        return objectList;
    }    
}
