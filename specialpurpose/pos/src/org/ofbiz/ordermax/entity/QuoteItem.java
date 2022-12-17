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
public class QuoteItem implements GenericValueObjectInterface {
public static final String module =QuoteItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public QuoteItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public QuoteItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"quoteUnitPrice","Quote Unit Price"},
{"reservPersons","Reserv Persons"},
{"reservLength","Reserv Length"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"quoteItemSeqId","Quote Item Seq Id"},
{"reservStart","Reserv Start"},
{"custRequestItemSeqId","Cust Request Item Seq Id"},
{"quantity","Quantity"},
{"skillTypeId","Skill Type Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"custRequestId","Cust Request Id"},
{"quoteId","Quote Id"},
{"estimatedDeliveryDate","Estimated Delivery Date"},
{"uomId","Uom Id"},
{"productId","Product Id"},
{"configId","Config Id"},
{"isPromo","Is Promo"},
{"workEffortId","Work Effort Id"},
{"productFeatureId","Product Feature Id"},
{"selectedAmount","Selected Amount"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"comments","Comments"},
{"deliverableTypeId","Deliverable Type Id"},
};
protected void initObject(){
this.quoteUnitPrice = java.math.BigDecimal.ZERO;
this.reservPersons = java.math.BigDecimal.ZERO;
this.reservLength = java.math.BigDecimal.ZERO;
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.quoteItemSeqId = "";
this.reservStart = "";
this.custRequestItemSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.skillTypeId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.custRequestId = "";
this.quoteId = "";
this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
this.uomId = "";
this.productId = "";
this.configId = "";
this.isPromo = "";
this.workEffortId = "";
this.productFeatureId = "";
this.selectedAmount = java.math.BigDecimal.ZERO;
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.comments = "";
this.deliverableTypeId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.quoteUnitPrice = (java.math.BigDecimal) genVal.get("quoteUnitPrice");
this.reservPersons = (java.math.BigDecimal) genVal.get("reservPersons");
this.reservLength = (java.math.BigDecimal) genVal.get("reservLength");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.quoteItemSeqId = (java.lang.String) genVal.get("quoteItemSeqId");
this.reservStart = (java.lang.String) genVal.get("reservStart");
this.custRequestItemSeqId = (java.lang.String) genVal.get("custRequestItemSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.skillTypeId = (java.lang.String) genVal.get("skillTypeId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.custRequestId = (java.lang.String) genVal.get("custRequestId");
this.quoteId = (java.lang.String) genVal.get("quoteId");
this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
this.uomId = (java.lang.String) genVal.get("uomId");
this.productId = (java.lang.String) genVal.get("productId");
this.configId = (java.lang.String) genVal.get("configId");
this.isPromo = (java.lang.String) genVal.get("isPromo");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
this.selectedAmount = (java.math.BigDecimal) genVal.get("selectedAmount");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.comments = (java.lang.String) genVal.get("comments");
this.deliverableTypeId = (java.lang.String) genVal.get("deliverableTypeId");
}
protected void getGenericValue(GenericValue val) {
val.set("quoteUnitPrice",OrderMaxUtility.getValidEntityBigDecimal(this.quoteUnitPrice));
val.set("reservPersons",OrderMaxUtility.getValidEntityBigDecimal(this.reservPersons));
val.set("reservLength",OrderMaxUtility.getValidEntityBigDecimal(this.reservLength));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("quoteItemSeqId",OrderMaxUtility.getValidEntityString(this.quoteItemSeqId));
val.set("reservStart",OrderMaxUtility.getValidEntityString(this.reservStart));
val.set("custRequestItemSeqId",OrderMaxUtility.getValidEntityString(this.custRequestItemSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("skillTypeId",OrderMaxUtility.getValidEntityString(this.skillTypeId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("custRequestId",OrderMaxUtility.getValidEntityString(this.custRequestId));
val.set("quoteId",OrderMaxUtility.getValidEntityString(this.quoteId));
val.set("estimatedDeliveryDate",OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("configId",OrderMaxUtility.getValidEntityString(this.configId));
val.set("isPromo",OrderMaxUtility.getValidEntityString(this.isPromo));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("productFeatureId",OrderMaxUtility.getValidEntityString(this.productFeatureId));
val.set("selectedAmount",OrderMaxUtility.getValidEntityBigDecimal(this.selectedAmount));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("deliverableTypeId",OrderMaxUtility.getValidEntityString(this.deliverableTypeId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("quoteUnitPrice",this.quoteUnitPrice);
valueMap.put("reservPersons",this.reservPersons);
valueMap.put("reservLength",this.reservLength);
valueMap.put("quoteItemSeqId",this.quoteItemSeqId);
valueMap.put("reservStart",this.reservStart);
valueMap.put("custRequestItemSeqId",this.custRequestItemSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("skillTypeId",this.skillTypeId);
valueMap.put("custRequestId",this.custRequestId);
valueMap.put("quoteId",this.quoteId);
valueMap.put("estimatedDeliveryDate",this.estimatedDeliveryDate);
valueMap.put("uomId",this.uomId);
valueMap.put("productId",this.productId);
valueMap.put("configId",this.configId);
valueMap.put("isPromo",this.isPromo);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("productFeatureId",this.productFeatureId);
valueMap.put("selectedAmount",this.selectedAmount);
valueMap.put("comments",this.comments);
valueMap.put("deliverableTypeId",this.deliverableTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("QuoteItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal quoteUnitPrice;
public java.math.BigDecimal getquoteUnitPrice() {
return quoteUnitPrice;
}
public void setquoteUnitPrice( java.math.BigDecimal quoteUnitPrice ) {
this.quoteUnitPrice = quoteUnitPrice;
}
private java.math.BigDecimal reservPersons;
public java.math.BigDecimal getreservPersons() {
return reservPersons;
}
public void setreservPersons( java.math.BigDecimal reservPersons ) {
this.reservPersons = reservPersons;
}
private java.math.BigDecimal reservLength;
public java.math.BigDecimal getreservLength() {
return reservLength;
}
public void setreservLength( java.math.BigDecimal reservLength ) {
this.reservLength = reservLength;
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
private java.lang.String quoteItemSeqId;
public java.lang.String getquoteItemSeqId() {
return quoteItemSeqId;
}
public void setquoteItemSeqId( java.lang.String quoteItemSeqId ) {
this.quoteItemSeqId = quoteItemSeqId;
}
private java.lang.String reservStart;
public java.lang.String getreservStart() {
return reservStart;
}
public void setreservStart( java.lang.String reservStart ) {
this.reservStart = reservStart;
}
private java.lang.String custRequestItemSeqId;
public java.lang.String getcustRequestItemSeqId() {
return custRequestItemSeqId;
}
public void setcustRequestItemSeqId( java.lang.String custRequestItemSeqId ) {
this.custRequestItemSeqId = custRequestItemSeqId;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String skillTypeId;
public java.lang.String getskillTypeId() {
return skillTypeId;
}
public void setskillTypeId( java.lang.String skillTypeId ) {
this.skillTypeId = skillTypeId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String custRequestId;
public java.lang.String getcustRequestId() {
return custRequestId;
}
public void setcustRequestId( java.lang.String custRequestId ) {
this.custRequestId = custRequestId;
}
private java.lang.String quoteId;
public java.lang.String getquoteId() {
return quoteId;
}
public void setquoteId( java.lang.String quoteId ) {
this.quoteId = quoteId;
}
private java.sql.Timestamp estimatedDeliveryDate;
public java.sql.Timestamp getestimatedDeliveryDate() {
return estimatedDeliveryDate;
}
public void setestimatedDeliveryDate( java.sql.Timestamp estimatedDeliveryDate ) {
this.estimatedDeliveryDate = estimatedDeliveryDate;
}
private java.lang.String uomId;
public java.lang.String getuomId() {
return uomId;
}
public void setuomId( java.lang.String uomId ) {
this.uomId = uomId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String configId;
public java.lang.String getconfigId() {
return configId;
}
public void setconfigId( java.lang.String configId ) {
this.configId = configId;
}
private java.lang.String isPromo;
public java.lang.String getisPromo() {
return isPromo;
}
public void setisPromo( java.lang.String isPromo ) {
this.isPromo = isPromo;
}
private java.lang.String workEffortId;
public java.lang.String getworkEffortId() {
return workEffortId;
}
public void setworkEffortId( java.lang.String workEffortId ) {
this.workEffortId = workEffortId;
}
private java.lang.String productFeatureId;
public java.lang.String getproductFeatureId() {
return productFeatureId;
}
public void setproductFeatureId( java.lang.String productFeatureId ) {
this.productFeatureId = productFeatureId;
}
private java.math.BigDecimal selectedAmount;
public java.math.BigDecimal getselectedAmount() {
return selectedAmount;
}
public void setselectedAmount( java.math.BigDecimal selectedAmount ) {
this.selectedAmount = selectedAmount;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String deliverableTypeId;
public java.lang.String getdeliverableTypeId() {
return deliverableTypeId;
}
public void setdeliverableTypeId( java.lang.String deliverableTypeId ) {
this.deliverableTypeId = deliverableTypeId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<QuoteItem> objectList = new ArrayList<QuoteItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new QuoteItem(genVal));
        }
        return objectList;
    }    
}
