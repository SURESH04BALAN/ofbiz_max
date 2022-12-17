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
public class CustRequestItem implements GenericValueObjectInterface {
public static final String module =CustRequestItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CustRequestItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CustRequestItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"custRequestId","Cust Request Id"},
{"maximumAmount","Maximum Amount"},
{"reservPersons","Reserv Persons"},
{"productId","Product Id"},
{"sequenceNum","Sequence Num"},
{"configId","Config Id"},
{"statusId","Status Id"},
{"story","Story"},
{"reservLength","Reserv Length"},
{"createdTxStamp","Created Tx Stamp"},
{"selectedAmount","Selected Amount"},
{"createdStamp","Created Stamp"},
{"priority","Priority"},
{"custRequestResolutionId","Cust Request Resolution Id"},
{"description","Description"},
{"reservStart","Reserv Start"},
{"requiredByDate","Required By Date"},
{"custRequestItemSeqId","Cust Request Item Seq Id"},
{"quantity","Quantity"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.custRequestId = "";
this.maximumAmount = "";
this.reservPersons = java.math.BigDecimal.ZERO;
this.productId = "";
this.sequenceNum = "";
this.configId = "";
this.statusId = "";
this.story = "";
this.reservLength = java.math.BigDecimal.ZERO;
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.selectedAmount = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.priority = "";
this.custRequestResolutionId = "";
this.description = "";
this.reservStart = "";
this.requiredByDate = UtilDateTime.nowTimestamp();
this.custRequestItemSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.custRequestId = (java.lang.String) genVal.get("custRequestId");
this.maximumAmount = (java.lang.String) genVal.get("maximumAmount");
this.reservPersons = (java.math.BigDecimal) genVal.get("reservPersons");
this.productId = (java.lang.String) genVal.get("productId");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
this.configId = (java.lang.String) genVal.get("configId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.story = (java.lang.String) genVal.get("story");
this.reservLength = (java.math.BigDecimal) genVal.get("reservLength");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.selectedAmount = (java.lang.String) genVal.get("selectedAmount");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.priority = (java.lang.String) genVal.get("priority");
this.custRequestResolutionId = (java.lang.String) genVal.get("custRequestResolutionId");
this.description = (java.lang.String) genVal.get("description");
this.reservStart = (java.lang.String) genVal.get("reservStart");
this.requiredByDate = (java.sql.Timestamp) genVal.get("requiredByDate");
this.custRequestItemSeqId = (java.lang.String) genVal.get("custRequestItemSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("custRequestId",OrderMaxUtility.getValidEntityString(this.custRequestId));
val.set("maximumAmount",OrderMaxUtility.getValidEntityString(this.maximumAmount));
val.set("reservPersons",OrderMaxUtility.getValidEntityBigDecimal(this.reservPersons));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
val.set("configId",OrderMaxUtility.getValidEntityString(this.configId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("story",OrderMaxUtility.getValidEntityString(this.story));
val.set("reservLength",OrderMaxUtility.getValidEntityBigDecimal(this.reservLength));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("selectedAmount",OrderMaxUtility.getValidEntityString(this.selectedAmount));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("priority",OrderMaxUtility.getValidEntityString(this.priority));
val.set("custRequestResolutionId",OrderMaxUtility.getValidEntityString(this.custRequestResolutionId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("reservStart",OrderMaxUtility.getValidEntityString(this.reservStart));
val.set("requiredByDate",OrderMaxUtility.getValidEntityTimestamp(this.requiredByDate));
val.set("custRequestItemSeqId",OrderMaxUtility.getValidEntityString(this.custRequestItemSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("custRequestId",this.custRequestId);
valueMap.put("maximumAmount",this.maximumAmount);
valueMap.put("reservPersons",this.reservPersons);
valueMap.put("productId",this.productId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("configId",this.configId);
valueMap.put("statusId",this.statusId);
valueMap.put("story",this.story);
valueMap.put("reservLength",this.reservLength);
valueMap.put("selectedAmount",this.selectedAmount);
valueMap.put("priority",this.priority);
valueMap.put("custRequestResolutionId",this.custRequestResolutionId);
valueMap.put("description",this.description);
valueMap.put("reservStart",this.reservStart);
valueMap.put("requiredByDate",this.requiredByDate);
valueMap.put("custRequestItemSeqId",this.custRequestItemSeqId);
valueMap.put("quantity",this.quantity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CustRequestItem");
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
private java.lang.String custRequestId;
public java.lang.String getcustRequestId() {
return custRequestId;
}
public void setcustRequestId( java.lang.String custRequestId ) {
this.custRequestId = custRequestId;
}
private java.lang.String maximumAmount;
public java.lang.String getmaximumAmount() {
return maximumAmount;
}
public void setmaximumAmount( java.lang.String maximumAmount ) {
this.maximumAmount = maximumAmount;
}
private java.math.BigDecimal reservPersons;
public java.math.BigDecimal getreservPersons() {
return reservPersons;
}
public void setreservPersons( java.math.BigDecimal reservPersons ) {
this.reservPersons = reservPersons;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String configId;
public java.lang.String getconfigId() {
return configId;
}
public void setconfigId( java.lang.String configId ) {
this.configId = configId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String story;
public java.lang.String getstory() {
return story;
}
public void setstory( java.lang.String story ) {
this.story = story;
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
private java.lang.String selectedAmount;
public java.lang.String getselectedAmount() {
return selectedAmount;
}
public void setselectedAmount( java.lang.String selectedAmount ) {
this.selectedAmount = selectedAmount;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String priority;
public java.lang.String getpriority() {
return priority;
}
public void setpriority( java.lang.String priority ) {
this.priority = priority;
}
private java.lang.String custRequestResolutionId;
public java.lang.String getcustRequestResolutionId() {
return custRequestResolutionId;
}
public void setcustRequestResolutionId( java.lang.String custRequestResolutionId ) {
this.custRequestResolutionId = custRequestResolutionId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String reservStart;
public java.lang.String getreservStart() {
return reservStart;
}
public void setreservStart( java.lang.String reservStart ) {
this.reservStart = reservStart;
}
private java.sql.Timestamp requiredByDate;
public java.sql.Timestamp getrequiredByDate() {
return requiredByDate;
}
public void setrequiredByDate( java.sql.Timestamp requiredByDate ) {
this.requiredByDate = requiredByDate;
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
        Collection<CustRequestItem> objectList = new ArrayList<CustRequestItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new CustRequestItem(genVal));
        }
        return objectList;
    }    
}
