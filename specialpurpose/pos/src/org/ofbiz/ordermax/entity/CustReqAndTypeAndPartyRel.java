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
public class CustReqAndTypeAndPartyRel implements GenericValueObjectInterface {
public static final String module =CustReqAndTypeAndPartyRel.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CustReqAndTypeAndPartyRel(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CustReqAndTypeAndPartyRel () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"typeDescription","Type Description"},
{"custRequestCategoryId","Cust Request Category Id"},
{"productStoreId","Product Store Id"},
{"responseRequiredDate","Response Required Date"},
{"thruDate","Thru Date"},
{"openDateTime","Open Date Time"},
{"reason","Reason"},
{"partyRelationshipTypeId","Party Relationship Type Id"},
{"partyIdFrom","Party Id From"},
{"relStatusId","Rel Status Id"},
{"statusId","Status Id"},
{"roleTypeIdTo","Role Type Id To"},
{"description","Description"},
{"priority","Priority"},
{"fulfillContactMechId","Fulfill Contact Mech Id"},
{"createdDate","Created Date"},
{"custRequestDate","Cust Request Date"},
{"maximumAmountUomId","Maximum Amount Uom Id"},
{"custRequestId","Cust Request Id"},
{"partyIdTo","Party Id To"},
{"currencyUomId","Currency Uom Id"},
{"roleTypeIdFrom","Role Type Id From"},
{"closedDateTime","Closed Date Time"},
{"custRequestName","Cust Request Name"},
{"custEstimatedMilliSeconds","Cust Estimated Milli Seconds"},
{"custSequenceNum","Cust Sequence Num"},
{"parentCustRequestId","Parent Cust Request Id"},
{"billed","Billed"},
{"fromPartyId","From Party Id"},
{"fromDate","From Date"},
{"custRequestTypeId","Cust Request Type Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"internalComment","Internal Comment"},
{"lastModifiedDate","Last Modified Date"},
{"salesChannelEnumId","Sales Channel Enum Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.typeDescription = "";
this.custRequestCategoryId = "";
this.productStoreId = "";
this.responseRequiredDate = UtilDateTime.nowTimestamp();
this.thruDate = UtilDateTime.nowTimestamp();
this.openDateTime = UtilDateTime.nowTimestamp();
this.reason = "";
this.partyRelationshipTypeId = "";
this.partyIdFrom = "";
this.relStatusId = "";
this.statusId = "";
this.roleTypeIdTo = "";
this.description = "";
this.priority = "";
this.fulfillContactMechId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.custRequestDate = UtilDateTime.nowTimestamp();
this.maximumAmountUomId = "";
this.custRequestId = "";
this.partyIdTo = "";
this.currencyUomId = "";
this.roleTypeIdFrom = "";
this.closedDateTime = UtilDateTime.nowTimestamp();
this.custRequestName = "";
this.custEstimatedMilliSeconds = "";
this.custSequenceNum = "";
this.parentCustRequestId = "";
this.billed = "";
this.fromPartyId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.custRequestTypeId = "";
this.lastModifiedByUserLogin = "";
this.internalComment = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.salesChannelEnumId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.typeDescription = (java.lang.String) genVal.get("typeDescription");
this.custRequestCategoryId = (java.lang.String) genVal.get("custRequestCategoryId");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.responseRequiredDate = (java.sql.Timestamp) genVal.get("responseRequiredDate");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.openDateTime = (java.sql.Timestamp) genVal.get("openDateTime");
this.reason = (java.lang.String) genVal.get("reason");
this.partyRelationshipTypeId = (java.lang.String) genVal.get("partyRelationshipTypeId");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.relStatusId = (java.lang.String) genVal.get("relStatusId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
this.description = (java.lang.String) genVal.get("description");
this.priority = (java.lang.String) genVal.get("priority");
this.fulfillContactMechId = (java.lang.String) genVal.get("fulfillContactMechId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.custRequestDate = (java.sql.Timestamp) genVal.get("custRequestDate");
this.maximumAmountUomId = (java.lang.String) genVal.get("maximumAmountUomId");
this.custRequestId = (java.lang.String) genVal.get("custRequestId");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.roleTypeIdFrom = (java.lang.String) genVal.get("roleTypeIdFrom");
this.closedDateTime = (java.sql.Timestamp) genVal.get("closedDateTime");
this.custRequestName = (java.lang.String) genVal.get("custRequestName");
this.custEstimatedMilliSeconds = (java.lang.String) genVal.get("custEstimatedMilliSeconds");
this.custSequenceNum = (java.lang.String) genVal.get("custSequenceNum");
this.parentCustRequestId = (java.lang.String) genVal.get("parentCustRequestId");
this.billed = (java.lang.String) genVal.get("billed");
this.fromPartyId = (java.lang.String) genVal.get("fromPartyId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.custRequestTypeId = (java.lang.String) genVal.get("custRequestTypeId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.internalComment = (java.lang.String) genVal.get("internalComment");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.salesChannelEnumId = (java.lang.String) genVal.get("salesChannelEnumId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("typeDescription",OrderMaxUtility.getValidEntityString(this.typeDescription));
val.set("custRequestCategoryId",OrderMaxUtility.getValidEntityString(this.custRequestCategoryId));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("responseRequiredDate",OrderMaxUtility.getValidEntityTimestamp(this.responseRequiredDate));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("openDateTime",OrderMaxUtility.getValidEntityTimestamp(this.openDateTime));
val.set("reason",OrderMaxUtility.getValidEntityString(this.reason));
val.set("partyRelationshipTypeId",OrderMaxUtility.getValidEntityString(this.partyRelationshipTypeId));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("relStatusId",OrderMaxUtility.getValidEntityString(this.relStatusId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("roleTypeIdTo",OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("priority",OrderMaxUtility.getValidEntityString(this.priority));
val.set("fulfillContactMechId",OrderMaxUtility.getValidEntityString(this.fulfillContactMechId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("custRequestDate",OrderMaxUtility.getValidEntityTimestamp(this.custRequestDate));
val.set("maximumAmountUomId",OrderMaxUtility.getValidEntityString(this.maximumAmountUomId));
val.set("custRequestId",OrderMaxUtility.getValidEntityString(this.custRequestId));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("roleTypeIdFrom",OrderMaxUtility.getValidEntityString(this.roleTypeIdFrom));
val.set("closedDateTime",OrderMaxUtility.getValidEntityTimestamp(this.closedDateTime));
val.set("custRequestName",OrderMaxUtility.getValidEntityString(this.custRequestName));
val.set("custEstimatedMilliSeconds",OrderMaxUtility.getValidEntityString(this.custEstimatedMilliSeconds));
val.set("custSequenceNum",OrderMaxUtility.getValidEntityString(this.custSequenceNum));
val.set("parentCustRequestId",OrderMaxUtility.getValidEntityString(this.parentCustRequestId));
val.set("billed",OrderMaxUtility.getValidEntityString(this.billed));
val.set("fromPartyId",OrderMaxUtility.getValidEntityString(this.fromPartyId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("custRequestTypeId",OrderMaxUtility.getValidEntityString(this.custRequestTypeId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("internalComment",OrderMaxUtility.getValidEntityString(this.internalComment));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("salesChannelEnumId",OrderMaxUtility.getValidEntityString(this.salesChannelEnumId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("typeDescription",this.typeDescription);
valueMap.put("custRequestCategoryId",this.custRequestCategoryId);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("responseRequiredDate",this.responseRequiredDate);
valueMap.put("thruDate",this.thruDate);
valueMap.put("openDateTime",this.openDateTime);
valueMap.put("reason",this.reason);
valueMap.put("partyRelationshipTypeId",this.partyRelationshipTypeId);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("relStatusId",this.relStatusId);
valueMap.put("statusId",this.statusId);
valueMap.put("roleTypeIdTo",this.roleTypeIdTo);
valueMap.put("description",this.description);
valueMap.put("priority",this.priority);
valueMap.put("fulfillContactMechId",this.fulfillContactMechId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("custRequestDate",this.custRequestDate);
valueMap.put("maximumAmountUomId",this.maximumAmountUomId);
valueMap.put("custRequestId",this.custRequestId);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("roleTypeIdFrom",this.roleTypeIdFrom);
valueMap.put("closedDateTime",this.closedDateTime);
valueMap.put("custRequestName",this.custRequestName);
valueMap.put("custEstimatedMilliSeconds",this.custEstimatedMilliSeconds);
valueMap.put("custSequenceNum",this.custSequenceNum);
valueMap.put("parentCustRequestId",this.parentCustRequestId);
valueMap.put("billed",this.billed);
valueMap.put("fromPartyId",this.fromPartyId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("custRequestTypeId",this.custRequestTypeId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("internalComment",this.internalComment);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("salesChannelEnumId",this.salesChannelEnumId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CustReqAndTypeAndPartyRel");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String typeDescription;
public java.lang.String gettypeDescription() {
return typeDescription;
}
public void settypeDescription( java.lang.String typeDescription ) {
this.typeDescription = typeDescription;
}
private java.lang.String custRequestCategoryId;
public java.lang.String getcustRequestCategoryId() {
return custRequestCategoryId;
}
public void setcustRequestCategoryId( java.lang.String custRequestCategoryId ) {
this.custRequestCategoryId = custRequestCategoryId;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.sql.Timestamp responseRequiredDate;
public java.sql.Timestamp getresponseRequiredDate() {
return responseRequiredDate;
}
public void setresponseRequiredDate( java.sql.Timestamp responseRequiredDate ) {
this.responseRequiredDate = responseRequiredDate;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp openDateTime;
public java.sql.Timestamp getopenDateTime() {
return openDateTime;
}
public void setopenDateTime( java.sql.Timestamp openDateTime ) {
this.openDateTime = openDateTime;
}
private java.lang.String reason;
public java.lang.String getreason() {
return reason;
}
public void setreason( java.lang.String reason ) {
this.reason = reason;
}
private java.lang.String partyRelationshipTypeId;
public java.lang.String getpartyRelationshipTypeId() {
return partyRelationshipTypeId;
}
public void setpartyRelationshipTypeId( java.lang.String partyRelationshipTypeId ) {
this.partyRelationshipTypeId = partyRelationshipTypeId;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.lang.String relStatusId;
public java.lang.String getrelStatusId() {
return relStatusId;
}
public void setrelStatusId( java.lang.String relStatusId ) {
this.relStatusId = relStatusId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String roleTypeIdTo;
public java.lang.String getroleTypeIdTo() {
return roleTypeIdTo;
}
public void setroleTypeIdTo( java.lang.String roleTypeIdTo ) {
this.roleTypeIdTo = roleTypeIdTo;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String priority;
public java.lang.String getpriority() {
return priority;
}
public void setpriority( java.lang.String priority ) {
this.priority = priority;
}
private java.lang.String fulfillContactMechId;
public java.lang.String getfulfillContactMechId() {
return fulfillContactMechId;
}
public void setfulfillContactMechId( java.lang.String fulfillContactMechId ) {
this.fulfillContactMechId = fulfillContactMechId;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.sql.Timestamp custRequestDate;
public java.sql.Timestamp getcustRequestDate() {
return custRequestDate;
}
public void setcustRequestDate( java.sql.Timestamp custRequestDate ) {
this.custRequestDate = custRequestDate;
}
private java.lang.String maximumAmountUomId;
public java.lang.String getmaximumAmountUomId() {
return maximumAmountUomId;
}
public void setmaximumAmountUomId( java.lang.String maximumAmountUomId ) {
this.maximumAmountUomId = maximumAmountUomId;
}
private java.lang.String custRequestId;
public java.lang.String getcustRequestId() {
return custRequestId;
}
public void setcustRequestId( java.lang.String custRequestId ) {
this.custRequestId = custRequestId;
}
private java.lang.String partyIdTo;
public java.lang.String getpartyIdTo() {
return partyIdTo;
}
public void setpartyIdTo( java.lang.String partyIdTo ) {
this.partyIdTo = partyIdTo;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String roleTypeIdFrom;
public java.lang.String getroleTypeIdFrom() {
return roleTypeIdFrom;
}
public void setroleTypeIdFrom( java.lang.String roleTypeIdFrom ) {
this.roleTypeIdFrom = roleTypeIdFrom;
}
private java.sql.Timestamp closedDateTime;
public java.sql.Timestamp getclosedDateTime() {
return closedDateTime;
}
public void setclosedDateTime( java.sql.Timestamp closedDateTime ) {
this.closedDateTime = closedDateTime;
}
private java.lang.String custRequestName;
public java.lang.String getcustRequestName() {
return custRequestName;
}
public void setcustRequestName( java.lang.String custRequestName ) {
this.custRequestName = custRequestName;
}
private java.lang.String custEstimatedMilliSeconds;
public java.lang.String getcustEstimatedMilliSeconds() {
return custEstimatedMilliSeconds;
}
public void setcustEstimatedMilliSeconds( java.lang.String custEstimatedMilliSeconds ) {
this.custEstimatedMilliSeconds = custEstimatedMilliSeconds;
}
private java.lang.String custSequenceNum;
public java.lang.String getcustSequenceNum() {
return custSequenceNum;
}
public void setcustSequenceNum( java.lang.String custSequenceNum ) {
this.custSequenceNum = custSequenceNum;
}
private java.lang.String parentCustRequestId;
public java.lang.String getparentCustRequestId() {
return parentCustRequestId;
}
public void setparentCustRequestId( java.lang.String parentCustRequestId ) {
this.parentCustRequestId = parentCustRequestId;
}
private java.lang.String billed;
public java.lang.String getbilled() {
return billed;
}
public void setbilled( java.lang.String billed ) {
this.billed = billed;
}
private java.lang.String fromPartyId;
public java.lang.String getfromPartyId() {
return fromPartyId;
}
public void setfromPartyId( java.lang.String fromPartyId ) {
this.fromPartyId = fromPartyId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String custRequestTypeId;
public java.lang.String getcustRequestTypeId() {
return custRequestTypeId;
}
public void setcustRequestTypeId( java.lang.String custRequestTypeId ) {
this.custRequestTypeId = custRequestTypeId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String internalComment;
public java.lang.String getinternalComment() {
return internalComment;
}
public void setinternalComment( java.lang.String internalComment ) {
this.internalComment = internalComment;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String salesChannelEnumId;
public java.lang.String getsalesChannelEnumId() {
return salesChannelEnumId;
}
public void setsalesChannelEnumId( java.lang.String salesChannelEnumId ) {
this.salesChannelEnumId = salesChannelEnumId;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CustReqAndTypeAndPartyRel> objectList = new ArrayList<CustReqAndTypeAndPartyRel>();
        for (GenericValue genVal : genList) {
            objectList.add(new CustReqAndTypeAndPartyRel(genVal));
        }
        return objectList;
    }    
}
