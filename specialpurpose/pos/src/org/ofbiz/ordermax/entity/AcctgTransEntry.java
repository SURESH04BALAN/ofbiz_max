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
public class AcctgTransEntry implements GenericValueObjectInterface {
public static final String module =AcctgTransEntry.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public AcctgTransEntry(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public AcctgTransEntry () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"organizationPartyId","Organization Party Id"},
{"glAccountTypeId","Gl Account Type Id"},
{"acctgTransId","Acctg Trans Id"},
{"acctgTransEntryTypeId","Acctg Trans Entry Type Id"},
{"amount","Amount"},
{"reconcileStatusId","Reconcile Status Id"},
{"createdTxStamp","Created Tx Stamp"},
{"glAccountId","Gl Account Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"theirProductId","Their Product Id"},
{"settlementTermId","Settlement Term Id"},
{"inventoryItemId","Inventory Item Id"},
{"dueDate","Due Date"},
{"acctgTransEntrySeqId","Acctg Trans Entry Seq Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"origCurrencyUomId","Orig Currency Uom Id"},
{"isSummary","Is Summary"},
{"origAmount","Orig Amount"},
{"currencyUomId","Currency Uom Id"},
{"debitCreditFlag","Debit Credit Flag"},
{"theirPartyId","Their Party Id"},
{"productId","Product Id"},
{"groupId","Group Id"},
{"roleTypeId","Role Type Id"},
{"voucherRef","Voucher Ref"},
{"taxId","Tax Id"},
{"partyId","Party Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.organizationPartyId = "";
this.glAccountTypeId = "";
this.acctgTransId = "";
this.acctgTransEntryTypeId = "";
this.amount = java.math.BigDecimal.ZERO;
this.reconcileStatusId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.glAccountId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.theirProductId = "";
this.settlementTermId = "";
this.inventoryItemId = "";
this.dueDate = UtilDateTime.nowTimestamp();
this.acctgTransEntrySeqId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.origCurrencyUomId = "";
this.isSummary = "";
this.origAmount = java.math.BigDecimal.ZERO;
this.currencyUomId = "";
this.debitCreditFlag = "";
this.theirPartyId = "";
this.productId = "";
this.groupId = "";
this.roleTypeId = "";
this.voucherRef = "";
this.taxId = "";
this.partyId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.acctgTransId = (java.lang.String) genVal.get("acctgTransId");
this.acctgTransEntryTypeId = (java.lang.String) genVal.get("acctgTransEntryTypeId");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.reconcileStatusId = (java.lang.String) genVal.get("reconcileStatusId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.theirProductId = (java.lang.String) genVal.get("theirProductId");
this.settlementTermId = (java.lang.String) genVal.get("settlementTermId");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.dueDate = (java.sql.Timestamp) genVal.get("dueDate");
this.acctgTransEntrySeqId = (java.lang.String) genVal.get("acctgTransEntrySeqId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.origCurrencyUomId = (java.lang.String) genVal.get("origCurrencyUomId");
this.isSummary = (java.lang.String) genVal.get("isSummary");
this.origAmount = (java.math.BigDecimal) genVal.get("origAmount");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.debitCreditFlag = (java.lang.String) genVal.get("debitCreditFlag");
this.theirPartyId = (java.lang.String) genVal.get("theirPartyId");
this.productId = (java.lang.String) genVal.get("productId");
this.groupId = (java.lang.String) genVal.get("groupId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.voucherRef = (java.lang.String) genVal.get("voucherRef");
this.taxId = (java.lang.String) genVal.get("taxId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("acctgTransId",OrderMaxUtility.getValidEntityString(this.acctgTransId));
val.set("acctgTransEntryTypeId",OrderMaxUtility.getValidEntityString(this.acctgTransEntryTypeId));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("reconcileStatusId",OrderMaxUtility.getValidEntityString(this.reconcileStatusId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("theirProductId",OrderMaxUtility.getValidEntityString(this.theirProductId));
val.set("settlementTermId",OrderMaxUtility.getValidEntityString(this.settlementTermId));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("dueDate",OrderMaxUtility.getValidEntityTimestamp(this.dueDate));
val.set("acctgTransEntrySeqId",OrderMaxUtility.getValidEntityString(this.acctgTransEntrySeqId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("origCurrencyUomId",OrderMaxUtility.getValidEntityString(this.origCurrencyUomId));
val.set("isSummary",OrderMaxUtility.getValidEntityString(this.isSummary));
val.set("origAmount",OrderMaxUtility.getValidEntityBigDecimal(this.origAmount));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("debitCreditFlag",OrderMaxUtility.getValidEntityString(this.debitCreditFlag));
val.set("theirPartyId",OrderMaxUtility.getValidEntityString(this.theirPartyId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("groupId",OrderMaxUtility.getValidEntityString(this.groupId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("voucherRef",OrderMaxUtility.getValidEntityString(this.voucherRef));
val.set("taxId",OrderMaxUtility.getValidEntityString(this.taxId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("acctgTransId",this.acctgTransId);
valueMap.put("acctgTransEntryTypeId",this.acctgTransEntryTypeId);
valueMap.put("amount",this.amount);
valueMap.put("reconcileStatusId",this.reconcileStatusId);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("description",this.description);
valueMap.put("theirProductId",this.theirProductId);
valueMap.put("settlementTermId",this.settlementTermId);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("dueDate",this.dueDate);
valueMap.put("acctgTransEntrySeqId",this.acctgTransEntrySeqId);
valueMap.put("origCurrencyUomId",this.origCurrencyUomId);
valueMap.put("isSummary",this.isSummary);
valueMap.put("origAmount",this.origAmount);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("debitCreditFlag",this.debitCreditFlag);
valueMap.put("theirPartyId",this.theirPartyId);
valueMap.put("productId",this.productId);
valueMap.put("groupId",this.groupId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("voucherRef",this.voucherRef);
valueMap.put("taxId",this.taxId);
valueMap.put("partyId",this.partyId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("AcctgTransEntry");
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
private java.lang.String glAccountTypeId;
public java.lang.String getglAccountTypeId() {
return glAccountTypeId;
}
public void setglAccountTypeId( java.lang.String glAccountTypeId ) {
this.glAccountTypeId = glAccountTypeId;
}
private java.lang.String acctgTransId;
public java.lang.String getacctgTransId() {
return acctgTransId;
}
public void setacctgTransId( java.lang.String acctgTransId ) {
this.acctgTransId = acctgTransId;
}
private java.lang.String acctgTransEntryTypeId;
public java.lang.String getacctgTransEntryTypeId() {
return acctgTransEntryTypeId;
}
public void setacctgTransEntryTypeId( java.lang.String acctgTransEntryTypeId ) {
this.acctgTransEntryTypeId = acctgTransEntryTypeId;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.lang.String reconcileStatusId;
public java.lang.String getreconcileStatusId() {
return reconcileStatusId;
}
public void setreconcileStatusId( java.lang.String reconcileStatusId ) {
this.reconcileStatusId = reconcileStatusId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
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
private java.lang.String theirProductId;
public java.lang.String gettheirProductId() {
return theirProductId;
}
public void settheirProductId( java.lang.String theirProductId ) {
this.theirProductId = theirProductId;
}
private java.lang.String settlementTermId;
public java.lang.String getsettlementTermId() {
return settlementTermId;
}
public void setsettlementTermId( java.lang.String settlementTermId ) {
this.settlementTermId = settlementTermId;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.sql.Timestamp dueDate;
public java.sql.Timestamp getdueDate() {
return dueDate;
}
public void setdueDate( java.sql.Timestamp dueDate ) {
this.dueDate = dueDate;
}
private java.lang.String acctgTransEntrySeqId;
public java.lang.String getacctgTransEntrySeqId() {
return acctgTransEntrySeqId;
}
public void setacctgTransEntrySeqId( java.lang.String acctgTransEntrySeqId ) {
this.acctgTransEntrySeqId = acctgTransEntrySeqId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String origCurrencyUomId;
public java.lang.String getorigCurrencyUomId() {
return origCurrencyUomId;
}
public void setorigCurrencyUomId( java.lang.String origCurrencyUomId ) {
this.origCurrencyUomId = origCurrencyUomId;
}
private java.lang.String isSummary;
public java.lang.String getisSummary() {
return isSummary;
}
public void setisSummary( java.lang.String isSummary ) {
this.isSummary = isSummary;
}
private java.math.BigDecimal origAmount;
public java.math.BigDecimal getorigAmount() {
return origAmount;
}
public void setorigAmount( java.math.BigDecimal origAmount ) {
this.origAmount = origAmount;
}
private java.lang.String currencyUomId;
public java.lang.String getcurrencyUomId() {
return currencyUomId;
}
public void setcurrencyUomId( java.lang.String currencyUomId ) {
this.currencyUomId = currencyUomId;
}
private java.lang.String debitCreditFlag;
public java.lang.String getdebitCreditFlag() {
return debitCreditFlag;
}
public void setdebitCreditFlag( java.lang.String debitCreditFlag ) {
this.debitCreditFlag = debitCreditFlag;
}
private java.lang.String theirPartyId;
public java.lang.String gettheirPartyId() {
return theirPartyId;
}
public void settheirPartyId( java.lang.String theirPartyId ) {
this.theirPartyId = theirPartyId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String groupId;
public java.lang.String getgroupId() {
return groupId;
}
public void setgroupId( java.lang.String groupId ) {
this.groupId = groupId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String voucherRef;
public java.lang.String getvoucherRef() {
return voucherRef;
}
public void setvoucherRef( java.lang.String voucherRef ) {
this.voucherRef = voucherRef;
}
private java.lang.String taxId;
public java.lang.String gettaxId() {
return taxId;
}
public void settaxId( java.lang.String taxId ) {
this.taxId = taxId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
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
        Collection<AcctgTransEntry> objectList = new ArrayList<AcctgTransEntry>();
        for (GenericValue genVal : genList) {
            objectList.add(new AcctgTransEntry(genVal));
        }
        return objectList;
    }    
}
