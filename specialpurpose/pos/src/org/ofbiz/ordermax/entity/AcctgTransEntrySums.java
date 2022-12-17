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
public class AcctgTransEntrySums implements GenericValueObjectInterface {
public static final String module =AcctgTransEntrySums.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public AcctgTransEntrySums(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public AcctgTransEntrySums () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"glAccountClassId","Gl Account Class Id"},
{"glFiscalTypeId","Gl Fiscal Type Id"},
{"organizationPartyId","Organization Party Id"},
{"glAccountTypeId","Gl Account Type Id"},
{"debitCreditFlag","Debit Credit Flag"},
{"transactionDate","Transaction Date"},
{"acctgTransTypeId","Acctg Trans Type Id"},
{"amount","Amount"},
{"accountCode","Account Code"},
{"accountName","Account Name"},
{"glAccountId","Gl Account Id"},
{"isPosted","Is Posted"},
};
protected void initObject(){
this.glAccountClassId = "";
this.glFiscalTypeId = "";
this.organizationPartyId = "";
this.glAccountTypeId = "";
this.debitCreditFlag = "";
this.transactionDate = UtilDateTime.nowTimestamp();
this.acctgTransTypeId = "";
this.amount = java.math.BigDecimal.ZERO;
this.accountCode = "";
this.accountName = "";
this.glAccountId = "";
this.isPosted = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.glAccountClassId = (java.lang.String) genVal.get("glAccountClassId");
this.glFiscalTypeId = (java.lang.String) genVal.get("glFiscalTypeId");
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.debitCreditFlag = (java.lang.String) genVal.get("debitCreditFlag");
this.transactionDate = (java.sql.Timestamp) genVal.get("transactionDate");
this.acctgTransTypeId = (java.lang.String) genVal.get("acctgTransTypeId");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.accountCode = (java.lang.String) genVal.get("accountCode");
this.accountName = (java.lang.String) genVal.get("accountName");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.isPosted = (java.lang.String) genVal.get("isPosted");
}
protected void getGenericValue(GenericValue val) {
val.set("glAccountClassId",OrderMaxUtility.getValidEntityString(this.glAccountClassId));
val.set("glFiscalTypeId",OrderMaxUtility.getValidEntityString(this.glFiscalTypeId));
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("debitCreditFlag",OrderMaxUtility.getValidEntityString(this.debitCreditFlag));
val.set("transactionDate",OrderMaxUtility.getValidEntityTimestamp(this.transactionDate));
val.set("acctgTransTypeId",OrderMaxUtility.getValidEntityString(this.acctgTransTypeId));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("accountCode",OrderMaxUtility.getValidEntityString(this.accountCode));
val.set("accountName",OrderMaxUtility.getValidEntityString(this.accountName));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("isPosted",OrderMaxUtility.getValidEntityString(this.isPosted));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glAccountClassId",this.glAccountClassId);
valueMap.put("glFiscalTypeId",this.glFiscalTypeId);
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("debitCreditFlag",this.debitCreditFlag);
valueMap.put("transactionDate",this.transactionDate);
valueMap.put("acctgTransTypeId",this.acctgTransTypeId);
valueMap.put("amount",this.amount);
valueMap.put("accountCode",this.accountCode);
valueMap.put("accountName",this.accountName);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("isPosted",this.isPosted);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("AcctgTransEntrySums");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String glAccountClassId;
public java.lang.String getglAccountClassId() {
return glAccountClassId;
}
public void setglAccountClassId( java.lang.String glAccountClassId ) {
this.glAccountClassId = glAccountClassId;
}
private java.lang.String glFiscalTypeId;
public java.lang.String getglFiscalTypeId() {
return glFiscalTypeId;
}
public void setglFiscalTypeId( java.lang.String glFiscalTypeId ) {
this.glFiscalTypeId = glFiscalTypeId;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.lang.String glAccountTypeId;
public java.lang.String getglAccountTypeId() {
return glAccountTypeId;
}
public void setglAccountTypeId( java.lang.String glAccountTypeId ) {
this.glAccountTypeId = glAccountTypeId;
}
private java.lang.String debitCreditFlag;
public java.lang.String getdebitCreditFlag() {
return debitCreditFlag;
}
public void setdebitCreditFlag( java.lang.String debitCreditFlag ) {
this.debitCreditFlag = debitCreditFlag;
}
private java.sql.Timestamp transactionDate;
public java.sql.Timestamp gettransactionDate() {
return transactionDate;
}
public void settransactionDate( java.sql.Timestamp transactionDate ) {
this.transactionDate = transactionDate;
}
private java.lang.String acctgTransTypeId;
public java.lang.String getacctgTransTypeId() {
return acctgTransTypeId;
}
public void setacctgTransTypeId( java.lang.String acctgTransTypeId ) {
this.acctgTransTypeId = acctgTransTypeId;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.lang.String accountCode;
public java.lang.String getaccountCode() {
return accountCode;
}
public void setaccountCode( java.lang.String accountCode ) {
this.accountCode = accountCode;
}
private java.lang.String accountName;
public java.lang.String getaccountName() {
return accountName;
}
public void setaccountName( java.lang.String accountName ) {
this.accountName = accountName;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
}
private java.lang.String isPosted;
public java.lang.String getisPosted() {
return isPosted;
}
public void setisPosted( java.lang.String isPosted ) {
this.isPosted = isPosted;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<AcctgTransEntrySums> objectList = new ArrayList<AcctgTransEntrySums>();
        for (GenericValue genVal : genList) {
            objectList.add(new AcctgTransEntrySums(genVal));
        }
        return objectList;
    }    
}
