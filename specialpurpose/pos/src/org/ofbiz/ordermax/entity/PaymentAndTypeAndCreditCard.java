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
public class PaymentAndTypeAndCreditCard implements GenericValueObjectInterface {
public static final String module =PaymentAndTypeAndCreditCard.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PaymentAndTypeAndCreditCard(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PaymentAndTypeAndCreditCard () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"actualCurrencyUomId","Actual Currency Uom Id"},
{"hasTable","Has Table"},
{"paymentId","Payment Id"},
{"paymentTypeId","Payment Type Id"},
{"lastNameOnCard","Last Name On Card"},
{"paymentMethodTypeId","Payment Method Type Id"},
{"consecutiveFailedNsf","Consecutive Failed Nsf"},
{"partyIdFrom","Party Id From"},
{"lastFailedNsfDate","Last Failed Nsf Date"},
{"overrideGlAccountId","Override Gl Account Id"},
{"actualCurrencyAmount","Actual Currency Amount"},
{"amount","Amount"},
{"statusId","Status Id"},
{"titleOnCard","Title On Card"},
{"roleTypeIdTo","Role Type Id To"},
{"consecutiveFailedAuths","Consecutive Failed Auths"},
{"description","Description"},
{"parentTypeId","Parent Type Id"},
{"paymentRefNum","Payment Ref Num"},
{"middleNameOnCard","Middle Name On Card"},
{"paymentMethodId","Payment Method Id"},
{"effectiveDate","Effective Date"},
{"companyNameOnCard","Company Name On Card"},
{"firstNameOnCard","First Name On Card"},
{"partyIdTo","Party Id To"},
{"currencyUomId","Currency Uom Id"},
{"paymentPreferenceId","Payment Preference Id"},
{"contactMechId","Contact Mech Id"},
{"suffixOnCard","Suffix On Card"},
{"cardType","Card Type"},
{"validFromDate","Valid From Date"},
{"issueNumber","Issue Number"},
{"paymentGatewayResponseId","Payment Gateway Response Id"},
{"finAccountTransId","Fin Account Trans Id"},
{"expireDate","Expire Date"},
{"comments","Comments"},
{"lastFailedAuthDate","Last Failed Auth Date"},
{"cardNumber","Card Number"},
};
protected void initObject(){
this.actualCurrencyUomId = "";
this.hasTable = "";
this.paymentId = "";
this.paymentTypeId = "";
this.lastNameOnCard = "";
this.paymentMethodTypeId = "";
this.consecutiveFailedNsf = "";
this.partyIdFrom = "";
this.lastFailedNsfDate = UtilDateTime.nowTimestamp();
this.overrideGlAccountId = "";
this.actualCurrencyAmount = "";
this.amount = java.math.BigDecimal.ZERO;
this.statusId = "";
this.titleOnCard = "";
this.roleTypeIdTo = "";
this.consecutiveFailedAuths = "";
this.description = "";
this.parentTypeId = "";
this.paymentRefNum = "";
this.middleNameOnCard = "";
this.paymentMethodId = "";
this.effectiveDate = UtilDateTime.nowTimestamp();
this.companyNameOnCard = "";
this.firstNameOnCard = "";
this.partyIdTo = "";
this.currencyUomId = "";
this.paymentPreferenceId = "";
this.contactMechId = "";
this.suffixOnCard = "";
this.cardType = "";
this.validFromDate = UtilDateTime.nowTimestamp();
this.issueNumber = "";
this.paymentGatewayResponseId = "";
this.finAccountTransId = "";
this.expireDate = UtilDateTime.nowTimestamp();
this.comments = "";
this.lastFailedAuthDate = UtilDateTime.nowTimestamp();
this.cardNumber = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.actualCurrencyUomId = (java.lang.String) genVal.get("actualCurrencyUomId");
this.hasTable = (java.lang.String) genVal.get("hasTable");
this.paymentId = (java.lang.String) genVal.get("paymentId");
this.paymentTypeId = (java.lang.String) genVal.get("paymentTypeId");
this.lastNameOnCard = (java.lang.String) genVal.get("lastNameOnCard");
this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
this.consecutiveFailedNsf = (java.lang.String) genVal.get("consecutiveFailedNsf");
this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
this.lastFailedNsfDate = (java.sql.Timestamp) genVal.get("lastFailedNsfDate");
this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
this.actualCurrencyAmount = (java.lang.String) genVal.get("actualCurrencyAmount");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.statusId = (java.lang.String) genVal.get("statusId");
this.titleOnCard = (java.lang.String) genVal.get("titleOnCard");
this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
this.consecutiveFailedAuths = (java.lang.String) genVal.get("consecutiveFailedAuths");
this.description = (java.lang.String) genVal.get("description");
this.parentTypeId = (java.lang.String) genVal.get("parentTypeId");
this.paymentRefNum = (java.lang.String) genVal.get("paymentRefNum");
this.middleNameOnCard = (java.lang.String) genVal.get("middleNameOnCard");
this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
this.companyNameOnCard = (java.lang.String) genVal.get("companyNameOnCard");
this.firstNameOnCard = (java.lang.String) genVal.get("firstNameOnCard");
this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.paymentPreferenceId = (java.lang.String) genVal.get("paymentPreferenceId");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.suffixOnCard = (java.lang.String) genVal.get("suffixOnCard");
this.cardType = (java.lang.String) genVal.get("cardType");
this.validFromDate = (java.sql.Timestamp) genVal.get("validFromDate");
this.issueNumber = (java.lang.String) genVal.get("issueNumber");
this.paymentGatewayResponseId = (java.lang.String) genVal.get("paymentGatewayResponseId");
this.finAccountTransId = (java.lang.String) genVal.get("finAccountTransId");
this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
this.comments = (java.lang.String) genVal.get("comments");
this.lastFailedAuthDate = (java.sql.Timestamp) genVal.get("lastFailedAuthDate");
this.cardNumber = (java.lang.String) genVal.get("cardNumber");
}
protected void getGenericValue(GenericValue val) {
val.set("actualCurrencyUomId",OrderMaxUtility.getValidEntityString(this.actualCurrencyUomId));
val.set("hasTable",OrderMaxUtility.getValidEntityString(this.hasTable));
val.set("paymentId",OrderMaxUtility.getValidEntityString(this.paymentId));
val.set("paymentTypeId",OrderMaxUtility.getValidEntityString(this.paymentTypeId));
val.set("lastNameOnCard",OrderMaxUtility.getValidEntityString(this.lastNameOnCard));
val.set("paymentMethodTypeId",OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
val.set("consecutiveFailedNsf",OrderMaxUtility.getValidEntityString(this.consecutiveFailedNsf));
val.set("partyIdFrom",OrderMaxUtility.getValidEntityString(this.partyIdFrom));
val.set("lastFailedNsfDate",OrderMaxUtility.getValidEntityTimestamp(this.lastFailedNsfDate));
val.set("overrideGlAccountId",OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
val.set("actualCurrencyAmount",OrderMaxUtility.getValidEntityString(this.actualCurrencyAmount));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("titleOnCard",OrderMaxUtility.getValidEntityString(this.titleOnCard));
val.set("roleTypeIdTo",OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
val.set("consecutiveFailedAuths",OrderMaxUtility.getValidEntityString(this.consecutiveFailedAuths));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("parentTypeId",OrderMaxUtility.getValidEntityString(this.parentTypeId));
val.set("paymentRefNum",OrderMaxUtility.getValidEntityString(this.paymentRefNum));
val.set("middleNameOnCard",OrderMaxUtility.getValidEntityString(this.middleNameOnCard));
val.set("paymentMethodId",OrderMaxUtility.getValidEntityString(this.paymentMethodId));
val.set("effectiveDate",OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
val.set("companyNameOnCard",OrderMaxUtility.getValidEntityString(this.companyNameOnCard));
val.set("firstNameOnCard",OrderMaxUtility.getValidEntityString(this.firstNameOnCard));
val.set("partyIdTo",OrderMaxUtility.getValidEntityString(this.partyIdTo));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("paymentPreferenceId",OrderMaxUtility.getValidEntityString(this.paymentPreferenceId));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("suffixOnCard",OrderMaxUtility.getValidEntityString(this.suffixOnCard));
val.set("cardType",OrderMaxUtility.getValidEntityString(this.cardType));
val.set("validFromDate",OrderMaxUtility.getValidEntityTimestamp(this.validFromDate));
val.set("issueNumber",OrderMaxUtility.getValidEntityString(this.issueNumber));
val.set("paymentGatewayResponseId",OrderMaxUtility.getValidEntityString(this.paymentGatewayResponseId));
val.set("finAccountTransId",OrderMaxUtility.getValidEntityString(this.finAccountTransId));
val.set("expireDate",OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("lastFailedAuthDate",OrderMaxUtility.getValidEntityTimestamp(this.lastFailedAuthDate));
val.set("cardNumber",OrderMaxUtility.getValidEntityString(this.cardNumber));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("actualCurrencyUomId",this.actualCurrencyUomId);
valueMap.put("hasTable",this.hasTable);
valueMap.put("paymentId",this.paymentId);
valueMap.put("paymentTypeId",this.paymentTypeId);
valueMap.put("lastNameOnCard",this.lastNameOnCard);
valueMap.put("paymentMethodTypeId",this.paymentMethodTypeId);
valueMap.put("consecutiveFailedNsf",this.consecutiveFailedNsf);
valueMap.put("partyIdFrom",this.partyIdFrom);
valueMap.put("lastFailedNsfDate",this.lastFailedNsfDate);
valueMap.put("overrideGlAccountId",this.overrideGlAccountId);
valueMap.put("actualCurrencyAmount",this.actualCurrencyAmount);
valueMap.put("amount",this.amount);
valueMap.put("statusId",this.statusId);
valueMap.put("titleOnCard",this.titleOnCard);
valueMap.put("roleTypeIdTo",this.roleTypeIdTo);
valueMap.put("consecutiveFailedAuths",this.consecutiveFailedAuths);
valueMap.put("description",this.description);
valueMap.put("parentTypeId",this.parentTypeId);
valueMap.put("paymentRefNum",this.paymentRefNum);
valueMap.put("middleNameOnCard",this.middleNameOnCard);
valueMap.put("paymentMethodId",this.paymentMethodId);
valueMap.put("effectiveDate",this.effectiveDate);
valueMap.put("companyNameOnCard",this.companyNameOnCard);
valueMap.put("firstNameOnCard",this.firstNameOnCard);
valueMap.put("partyIdTo",this.partyIdTo);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("paymentPreferenceId",this.paymentPreferenceId);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("suffixOnCard",this.suffixOnCard);
valueMap.put("cardType",this.cardType);
valueMap.put("validFromDate",this.validFromDate);
valueMap.put("issueNumber",this.issueNumber);
valueMap.put("paymentGatewayResponseId",this.paymentGatewayResponseId);
valueMap.put("finAccountTransId",this.finAccountTransId);
valueMap.put("expireDate",this.expireDate);
valueMap.put("comments",this.comments);
valueMap.put("lastFailedAuthDate",this.lastFailedAuthDate);
valueMap.put("cardNumber",this.cardNumber);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PaymentAndTypeAndCreditCard");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String actualCurrencyUomId;
public java.lang.String getactualCurrencyUomId() {
return actualCurrencyUomId;
}
public void setactualCurrencyUomId( java.lang.String actualCurrencyUomId ) {
this.actualCurrencyUomId = actualCurrencyUomId;
}
private java.lang.String hasTable;
public java.lang.String gethasTable() {
return hasTable;
}
public void sethasTable( java.lang.String hasTable ) {
this.hasTable = hasTable;
}
private java.lang.String paymentId;
public java.lang.String getpaymentId() {
return paymentId;
}
public void setpaymentId( java.lang.String paymentId ) {
this.paymentId = paymentId;
}
private java.lang.String paymentTypeId;
public java.lang.String getpaymentTypeId() {
return paymentTypeId;
}
public void setpaymentTypeId( java.lang.String paymentTypeId ) {
this.paymentTypeId = paymentTypeId;
}
private java.lang.String lastNameOnCard;
public java.lang.String getlastNameOnCard() {
return lastNameOnCard;
}
public void setlastNameOnCard( java.lang.String lastNameOnCard ) {
this.lastNameOnCard = lastNameOnCard;
}
private java.lang.String paymentMethodTypeId;
public java.lang.String getpaymentMethodTypeId() {
return paymentMethodTypeId;
}
public void setpaymentMethodTypeId( java.lang.String paymentMethodTypeId ) {
this.paymentMethodTypeId = paymentMethodTypeId;
}
private java.lang.String consecutiveFailedNsf;
public java.lang.String getconsecutiveFailedNsf() {
return consecutiveFailedNsf;
}
public void setconsecutiveFailedNsf( java.lang.String consecutiveFailedNsf ) {
this.consecutiveFailedNsf = consecutiveFailedNsf;
}
private java.lang.String partyIdFrom;
public java.lang.String getpartyIdFrom() {
return partyIdFrom;
}
public void setpartyIdFrom( java.lang.String partyIdFrom ) {
this.partyIdFrom = partyIdFrom;
}
private java.sql.Timestamp lastFailedNsfDate;
public java.sql.Timestamp getlastFailedNsfDate() {
return lastFailedNsfDate;
}
public void setlastFailedNsfDate( java.sql.Timestamp lastFailedNsfDate ) {
this.lastFailedNsfDate = lastFailedNsfDate;
}
private java.lang.String overrideGlAccountId;
public java.lang.String getoverrideGlAccountId() {
return overrideGlAccountId;
}
public void setoverrideGlAccountId( java.lang.String overrideGlAccountId ) {
this.overrideGlAccountId = overrideGlAccountId;
}
private java.lang.String actualCurrencyAmount;
public java.lang.String getactualCurrencyAmount() {
return actualCurrencyAmount;
}
public void setactualCurrencyAmount( java.lang.String actualCurrencyAmount ) {
this.actualCurrencyAmount = actualCurrencyAmount;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String titleOnCard;
public java.lang.String gettitleOnCard() {
return titleOnCard;
}
public void settitleOnCard( java.lang.String titleOnCard ) {
this.titleOnCard = titleOnCard;
}
private java.lang.String roleTypeIdTo;
public java.lang.String getroleTypeIdTo() {
return roleTypeIdTo;
}
public void setroleTypeIdTo( java.lang.String roleTypeIdTo ) {
this.roleTypeIdTo = roleTypeIdTo;
}
private java.lang.String consecutiveFailedAuths;
public java.lang.String getconsecutiveFailedAuths() {
return consecutiveFailedAuths;
}
public void setconsecutiveFailedAuths( java.lang.String consecutiveFailedAuths ) {
this.consecutiveFailedAuths = consecutiveFailedAuths;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String parentTypeId;
public java.lang.String getparentTypeId() {
return parentTypeId;
}
public void setparentTypeId( java.lang.String parentTypeId ) {
this.parentTypeId = parentTypeId;
}
private java.lang.String paymentRefNum;
public java.lang.String getpaymentRefNum() {
return paymentRefNum;
}
public void setpaymentRefNum( java.lang.String paymentRefNum ) {
this.paymentRefNum = paymentRefNum;
}
private java.lang.String middleNameOnCard;
public java.lang.String getmiddleNameOnCard() {
return middleNameOnCard;
}
public void setmiddleNameOnCard( java.lang.String middleNameOnCard ) {
this.middleNameOnCard = middleNameOnCard;
}
private java.lang.String paymentMethodId;
public java.lang.String getpaymentMethodId() {
return paymentMethodId;
}
public void setpaymentMethodId( java.lang.String paymentMethodId ) {
this.paymentMethodId = paymentMethodId;
}
private java.sql.Timestamp effectiveDate;
public java.sql.Timestamp geteffectiveDate() {
return effectiveDate;
}
public void seteffectiveDate( java.sql.Timestamp effectiveDate ) {
this.effectiveDate = effectiveDate;
}
private java.lang.String companyNameOnCard;
public java.lang.String getcompanyNameOnCard() {
return companyNameOnCard;
}
public void setcompanyNameOnCard( java.lang.String companyNameOnCard ) {
this.companyNameOnCard = companyNameOnCard;
}
private java.lang.String firstNameOnCard;
public java.lang.String getfirstNameOnCard() {
return firstNameOnCard;
}
public void setfirstNameOnCard( java.lang.String firstNameOnCard ) {
this.firstNameOnCard = firstNameOnCard;
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
private java.lang.String paymentPreferenceId;
public java.lang.String getpaymentPreferenceId() {
return paymentPreferenceId;
}
public void setpaymentPreferenceId( java.lang.String paymentPreferenceId ) {
this.paymentPreferenceId = paymentPreferenceId;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String suffixOnCard;
public java.lang.String getsuffixOnCard() {
return suffixOnCard;
}
public void setsuffixOnCard( java.lang.String suffixOnCard ) {
this.suffixOnCard = suffixOnCard;
}
private java.lang.String cardType;
public java.lang.String getcardType() {
return cardType;
}
public void setcardType( java.lang.String cardType ) {
this.cardType = cardType;
}
private java.sql.Timestamp validFromDate;
public java.sql.Timestamp getvalidFromDate() {
return validFromDate;
}
public void setvalidFromDate( java.sql.Timestamp validFromDate ) {
this.validFromDate = validFromDate;
}
private java.lang.String issueNumber;
public java.lang.String getissueNumber() {
return issueNumber;
}
public void setissueNumber( java.lang.String issueNumber ) {
this.issueNumber = issueNumber;
}
private java.lang.String paymentGatewayResponseId;
public java.lang.String getpaymentGatewayResponseId() {
return paymentGatewayResponseId;
}
public void setpaymentGatewayResponseId( java.lang.String paymentGatewayResponseId ) {
this.paymentGatewayResponseId = paymentGatewayResponseId;
}
private java.lang.String finAccountTransId;
public java.lang.String getfinAccountTransId() {
return finAccountTransId;
}
public void setfinAccountTransId( java.lang.String finAccountTransId ) {
this.finAccountTransId = finAccountTransId;
}
private java.sql.Timestamp expireDate;
public java.sql.Timestamp getexpireDate() {
return expireDate;
}
public void setexpireDate( java.sql.Timestamp expireDate ) {
this.expireDate = expireDate;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.sql.Timestamp lastFailedAuthDate;
public java.sql.Timestamp getlastFailedAuthDate() {
return lastFailedAuthDate;
}
public void setlastFailedAuthDate( java.sql.Timestamp lastFailedAuthDate ) {
this.lastFailedAuthDate = lastFailedAuthDate;
}
private java.lang.String cardNumber;
public java.lang.String getcardNumber() {
return cardNumber;
}
public void setcardNumber( java.lang.String cardNumber ) {
this.cardNumber = cardNumber;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentAndTypeAndCreditCard> objectList = new ArrayList<PaymentAndTypeAndCreditCard>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentAndTypeAndCreditCard(genVal));
        }
        return objectList;
    }    
}
