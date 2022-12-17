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
public class GlAccOrgAndAcctgTransAndEntry implements GenericValueObjectInterface {
public static final String module =GlAccOrgAndAcctgTransAndEntry.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlAccOrgAndAcctgTransAndEntry(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlAccOrgAndAcctgTransAndEntry () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"organizationPartyId","Organization Party Id"},
{"thruDate","Thru Date"},
{"acctgTransId","Acctg Trans Id"},
{"fromDate","From Date"},
{"glAccountId","Gl Account Id"},
{"debitCreditFlag","Debit Credit Flag"},
{"totalAmount","Total Amount"},
{"isPosted","Is Posted"},
{"transactionDate","Transaction Date"},
};
protected void initObject(){
this.organizationPartyId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.acctgTransId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.glAccountId = "";
this.debitCreditFlag = "";
this.totalAmount = java.math.BigDecimal.ZERO;
this.isPosted = "";
this.transactionDate = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.acctgTransId = (java.lang.String) genVal.get("acctgTransId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.debitCreditFlag = (java.lang.String) genVal.get("debitCreditFlag");
this.totalAmount = (java.math.BigDecimal) genVal.get("totalAmount");
this.isPosted = (java.lang.String) genVal.get("isPosted");
this.transactionDate = (java.sql.Timestamp) genVal.get("transactionDate");
}
protected void getGenericValue(GenericValue val) {
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("acctgTransId",OrderMaxUtility.getValidEntityString(this.acctgTransId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("debitCreditFlag",OrderMaxUtility.getValidEntityString(this.debitCreditFlag));
val.set("totalAmount",OrderMaxUtility.getValidEntityBigDecimal(this.totalAmount));
val.set("isPosted",OrderMaxUtility.getValidEntityString(this.isPosted));
val.set("transactionDate",OrderMaxUtility.getValidEntityTimestamp(this.transactionDate));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("acctgTransId",this.acctgTransId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("debitCreditFlag",this.debitCreditFlag);
valueMap.put("totalAmount",this.totalAmount);
valueMap.put("isPosted",this.isPosted);
valueMap.put("transactionDate",this.transactionDate);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlAccOrgAndAcctgTransAndEntry");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String acctgTransId;
public java.lang.String getacctgTransId() {
return acctgTransId;
}
public void setacctgTransId( java.lang.String acctgTransId ) {
this.acctgTransId = acctgTransId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
}
private java.lang.String debitCreditFlag;
public java.lang.String getdebitCreditFlag() {
return debitCreditFlag;
}
public void setdebitCreditFlag( java.lang.String debitCreditFlag ) {
this.debitCreditFlag = debitCreditFlag;
}
private java.math.BigDecimal totalAmount;
public java.math.BigDecimal gettotalAmount() {
return totalAmount;
}
public void settotalAmount( java.math.BigDecimal totalAmount ) {
this.totalAmount = totalAmount;
}
private java.lang.String isPosted;
public java.lang.String getisPosted() {
return isPosted;
}
public void setisPosted( java.lang.String isPosted ) {
this.isPosted = isPosted;
}
private java.sql.Timestamp transactionDate;
public java.sql.Timestamp gettransactionDate() {
return transactionDate;
}
public void settransactionDate( java.sql.Timestamp transactionDate ) {
this.transactionDate = transactionDate;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GlAccOrgAndAcctgTransAndEntry> objectList = new ArrayList<GlAccOrgAndAcctgTransAndEntry>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlAccOrgAndAcctgTransAndEntry(genVal));
        }
        return objectList;
    }    
}
