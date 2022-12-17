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
public class EftAccount implements GenericValueObjectInterface {
public static final String module =EftAccount.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EftAccount(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
//Debug.logInfo(ex, module);
}
}
public EftAccount () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"companyNameOnAccount","Company Name On Account"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"nameOnAccount","Name On Account"},
{"createdTxStamp","Created Tx Stamp"},
{"accountType","Account Type"},
{"createdStamp","Created Stamp"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"bankName","Bank Name"},
{"accountNumber","Account Number"},
{"contactMechId","Contact Mech Id"},
{"routingNumber","Routing Number"},
{"paymentMethodId","Payment Method Id"},
{"yearsAtBank","Years At Bank"},
};
protected void initObject(){
this.companyNameOnAccount = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.nameOnAccount = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.accountType = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.bankName = "";
this.accountNumber = "";
this.contactMechId = "";
this.routingNumber = "";
this.paymentMethodId = "";
this.yearsAtBank = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.companyNameOnAccount = (java.lang.String) genVal.get("companyNameOnAccount");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.nameOnAccount = (java.lang.String) genVal.get("nameOnAccount");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.accountType = (java.lang.String) genVal.get("accountType");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.bankName = (java.lang.String) genVal.get("bankName");
this.accountNumber = (java.lang.String) genVal.get("accountNumber");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.routingNumber = (java.lang.String) genVal.get("routingNumber");
this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
this.yearsAtBank = (java.lang.String) genVal.get("yearsAtBank");
}
protected void getGenericValue(GenericValue val) {
val.set("companyNameOnAccount",OrderMaxUtility.getValidEntityString(this.companyNameOnAccount));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("nameOnAccount",OrderMaxUtility.getValidEntityString(this.nameOnAccount));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("accountType",OrderMaxUtility.getValidEntityString(this.accountType));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("bankName",OrderMaxUtility.getValidEntityString(this.bankName));
val.set("accountNumber",OrderMaxUtility.getValidEntityString(this.accountNumber));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("routingNumber",OrderMaxUtility.getValidEntityString(this.routingNumber));
val.set("paymentMethodId",OrderMaxUtility.getValidEntityString(this.paymentMethodId));
val.set("yearsAtBank",OrderMaxUtility.getValidEntityString(this.yearsAtBank));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("companyNameOnAccount",this.companyNameOnAccount);
valueMap.put("nameOnAccount",this.nameOnAccount);
valueMap.put("accountType",this.accountType);
valueMap.put("bankName",this.bankName);
valueMap.put("accountNumber",this.accountNumber);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("routingNumber",this.routingNumber);
valueMap.put("paymentMethodId",this.paymentMethodId);
valueMap.put("yearsAtBank",this.yearsAtBank);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EftAccount");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String companyNameOnAccount;
public java.lang.String getCompanyNameOnAccount() {
return companyNameOnAccount;
}
public void setCompanyNameOnAccount( java.lang.String companyNameOnAccount ) {
this.companyNameOnAccount = companyNameOnAccount;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getLastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setLastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String nameOnAccount;
public java.lang.String getNameOnAccount() {
return nameOnAccount;
}
public void setNameOnAccount( java.lang.String nameOnAccount ) {
this.nameOnAccount = nameOnAccount;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getCreatedTxStamp() {
return createdTxStamp;
}
public void setCreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String accountType;
public java.lang.String getAccountType() {
return accountType;
}
public void setAccountType( java.lang.String accountType ) {
this.accountType = accountType;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getCreatedStamp() {
return createdStamp;
}
public void setCreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getLastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setLastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String bankName;
public java.lang.String getBankName() {
return bankName;
}
public void setBankName( java.lang.String bankName ) {
this.bankName = bankName;
}
private java.lang.String accountNumber;
public java.lang.String getAccountNumber() {
return accountNumber;
}
public void setAccountNumber( java.lang.String accountNumber ) {
this.accountNumber = accountNumber;
}
private java.lang.String contactMechId;
public java.lang.String getContactMechId() {
return contactMechId;
}
public void setContactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String routingNumber;
public java.lang.String getRoutingNumber() {
return routingNumber;
}
public void setRoutingNumber( java.lang.String routingNumber ) {
this.routingNumber = routingNumber;
}
private java.lang.String paymentMethodId;
public java.lang.String getPaymentMethodId() {
return paymentMethodId;
}
public void setPaymentMethodId( java.lang.String paymentMethodId ) {
this.paymentMethodId = paymentMethodId;
}
private java.lang.String yearsAtBank;
public java.lang.String getYearsAtBank() {
return yearsAtBank;
}
public void setYearsAtBank( java.lang.String yearsAtBank ) {
this.yearsAtBank = yearsAtBank;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<EftAccount> objectList = new ArrayList<EftAccount>();
        for (GenericValue genVal : genList) {
            objectList.add(new EftAccount(genVal));
        }
        return objectList;
    }    
}
