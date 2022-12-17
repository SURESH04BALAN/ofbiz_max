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
public class AcctgTransAndEntries implements GenericValueObjectInterface {
public static final String module =AcctgTransAndEntries.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public AcctgTransAndEntries(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public AcctgTransAndEntries () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"glFiscalTypeId","Gl Fiscal Type Id"},
{"organizationPartyId","Organization Party Id"},
{"glAccountTypeId","Gl Account Type Id"},
{"acctgTransId","Acctg Trans Id"},
{"paymentId","Payment Id"},
{"acctgTransEntryTypeId","Acctg Trans Entry Type Id"},
{"amount","Amount"},
{"acctgTransTypeId","Acctg Trans Type Id"},
{"accountCode","Account Code"},
{"reconcileStatusId","Reconcile Status Id"},
{"glAccountId","Gl Account Id"},
{"shipmentId","Shipment Id"},
{"inventoryItemId","Inventory Item Id"},
{"fixedAssetId","Fixed Asset Id"},
{"acctgTransEntrySeqId","Acctg Trans Entry Seq Id"},
{"transDescription","Trans Description"},
{"glAccountClassId","Gl Account Class Id"},
{"glJournalId","Gl Journal Id"},
{"origCurrencyUomId","Orig Currency Uom Id"},
{"invoiceId","Invoice Id"},
{"origAmount","Orig Amount"},
{"currencyUomId","Currency Uom Id"},
{"debitCreditFlag","Debit Credit Flag"},
{"transactionDate","Transaction Date"},
{"productId","Product Id"},
{"transTypeDescription","Trans Type Description"},
{"workEffortId","Work Effort Id"},
{"accountName","Account Name"},
{"physicalInventoryId","Physical Inventory Id"},
{"postedDate","Posted Date"},
{"partyId","Party Id"},
{"isPosted","Is Posted"},
{"receiptId","Receipt Id"},
};
protected void initObject(){
this.glFiscalTypeId = "";
this.organizationPartyId = "";
this.glAccountTypeId = "";
this.acctgTransId = "";
this.paymentId = "";
this.acctgTransEntryTypeId = "";
this.amount = java.math.BigDecimal.ZERO;
this.acctgTransTypeId = "";
this.accountCode = "";
this.reconcileStatusId = "";
this.glAccountId = "";
this.shipmentId = "";
this.inventoryItemId = "";
this.fixedAssetId = "";
this.acctgTransEntrySeqId = "";
this.transDescription = "";
this.glAccountClassId = "";
this.glJournalId = "";
this.origCurrencyUomId = "";
this.invoiceId = "";
this.origAmount = java.math.BigDecimal.ZERO;
this.currencyUomId = "";
this.debitCreditFlag = "";
this.transactionDate = UtilDateTime.nowTimestamp();
this.productId = "";
this.transTypeDescription = "";
this.workEffortId = "";
this.accountName = "";
this.physicalInventoryId = "";
this.postedDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.isPosted = "";
this.receiptId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.glFiscalTypeId = (java.lang.String) genVal.get("glFiscalTypeId");
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.acctgTransId = (java.lang.String) genVal.get("acctgTransId");
this.paymentId = (java.lang.String) genVal.get("paymentId");
this.acctgTransEntryTypeId = (java.lang.String) genVal.get("acctgTransEntryTypeId");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.acctgTransTypeId = (java.lang.String) genVal.get("acctgTransTypeId");
this.accountCode = (java.lang.String) genVal.get("accountCode");
this.reconcileStatusId = (java.lang.String) genVal.get("reconcileStatusId");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.acctgTransEntrySeqId = (java.lang.String) genVal.get("acctgTransEntrySeqId");
this.transDescription = (java.lang.String) genVal.get("transDescription");
this.glAccountClassId = (java.lang.String) genVal.get("glAccountClassId");
this.glJournalId = (java.lang.String) genVal.get("glJournalId");
this.origCurrencyUomId = (java.lang.String) genVal.get("origCurrencyUomId");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.origAmount = (java.math.BigDecimal) genVal.get("origAmount");
this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
this.debitCreditFlag = (java.lang.String) genVal.get("debitCreditFlag");
this.transactionDate = (java.sql.Timestamp) genVal.get("transactionDate");
this.productId = (java.lang.String) genVal.get("productId");
this.transTypeDescription = (java.lang.String) genVal.get("transTypeDescription");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.accountName = (java.lang.String) genVal.get("accountName");
this.physicalInventoryId = (java.lang.String) genVal.get("physicalInventoryId");
this.postedDate = (java.sql.Timestamp) genVal.get("postedDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.isPosted = (java.lang.String) genVal.get("isPosted");
this.receiptId = (java.lang.String) genVal.get("receiptId");
}
protected void getGenericValue(GenericValue val) {
val.set("glFiscalTypeId",OrderMaxUtility.getValidEntityString(this.glFiscalTypeId));
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("acctgTransId",OrderMaxUtility.getValidEntityString(this.acctgTransId));
val.set("paymentId",OrderMaxUtility.getValidEntityString(this.paymentId));
val.set("acctgTransEntryTypeId",OrderMaxUtility.getValidEntityString(this.acctgTransEntryTypeId));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("acctgTransTypeId",OrderMaxUtility.getValidEntityString(this.acctgTransTypeId));
val.set("accountCode",OrderMaxUtility.getValidEntityString(this.accountCode));
val.set("reconcileStatusId",OrderMaxUtility.getValidEntityString(this.reconcileStatusId));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("acctgTransEntrySeqId",OrderMaxUtility.getValidEntityString(this.acctgTransEntrySeqId));
val.set("transDescription",OrderMaxUtility.getValidEntityString(this.transDescription));
val.set("glAccountClassId",OrderMaxUtility.getValidEntityString(this.glAccountClassId));
val.set("glJournalId",OrderMaxUtility.getValidEntityString(this.glJournalId));
val.set("origCurrencyUomId",OrderMaxUtility.getValidEntityString(this.origCurrencyUomId));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("origAmount",OrderMaxUtility.getValidEntityBigDecimal(this.origAmount));
val.set("currencyUomId",OrderMaxUtility.getValidEntityString(this.currencyUomId));
val.set("debitCreditFlag",OrderMaxUtility.getValidEntityString(this.debitCreditFlag));
val.set("transactionDate",OrderMaxUtility.getValidEntityTimestamp(this.transactionDate));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("transTypeDescription",OrderMaxUtility.getValidEntityString(this.transTypeDescription));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("accountName",OrderMaxUtility.getValidEntityString(this.accountName));
val.set("physicalInventoryId",OrderMaxUtility.getValidEntityString(this.physicalInventoryId));
val.set("postedDate",OrderMaxUtility.getValidEntityTimestamp(this.postedDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("isPosted",OrderMaxUtility.getValidEntityString(this.isPosted));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glFiscalTypeId",this.glFiscalTypeId);
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("acctgTransId",this.acctgTransId);
valueMap.put("paymentId",this.paymentId);
valueMap.put("acctgTransEntryTypeId",this.acctgTransEntryTypeId);
valueMap.put("amount",this.amount);
valueMap.put("acctgTransTypeId",this.acctgTransTypeId);
valueMap.put("accountCode",this.accountCode);
valueMap.put("reconcileStatusId",this.reconcileStatusId);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("acctgTransEntrySeqId",this.acctgTransEntrySeqId);
valueMap.put("transDescription",this.transDescription);
valueMap.put("glAccountClassId",this.glAccountClassId);
valueMap.put("glJournalId",this.glJournalId);
valueMap.put("origCurrencyUomId",this.origCurrencyUomId);
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("origAmount",this.origAmount);
valueMap.put("currencyUomId",this.currencyUomId);
valueMap.put("debitCreditFlag",this.debitCreditFlag);
valueMap.put("transactionDate",this.transactionDate);
valueMap.put("productId",this.productId);
valueMap.put("transTypeDescription",this.transTypeDescription);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("accountName",this.accountName);
valueMap.put("physicalInventoryId",this.physicalInventoryId);
valueMap.put("postedDate",this.postedDate);
valueMap.put("partyId",this.partyId);
valueMap.put("isPosted",this.isPosted);
valueMap.put("receiptId",this.receiptId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("AcctgTransAndEntries");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
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
private java.lang.String acctgTransId;
public java.lang.String getacctgTransId() {
return acctgTransId;
}
public void setacctgTransId( java.lang.String acctgTransId ) {
this.acctgTransId = acctgTransId;
}
private java.lang.String paymentId;
public java.lang.String getpaymentId() {
return paymentId;
}
public void setpaymentId( java.lang.String paymentId ) {
this.paymentId = paymentId;
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
private java.lang.String acctgTransTypeId;
public java.lang.String getacctgTransTypeId() {
return acctgTransTypeId;
}
public void setacctgTransTypeId( java.lang.String acctgTransTypeId ) {
this.acctgTransTypeId = acctgTransTypeId;
}
private java.lang.String accountCode;
public java.lang.String getaccountCode() {
return accountCode;
}
public void setaccountCode( java.lang.String accountCode ) {
this.accountCode = accountCode;
}
private java.lang.String reconcileStatusId;
public java.lang.String getreconcileStatusId() {
return reconcileStatusId;
}
public void setreconcileStatusId( java.lang.String reconcileStatusId ) {
this.reconcileStatusId = reconcileStatusId;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
}
private java.lang.String inventoryItemId;
public java.lang.String getinventoryItemId() {
return inventoryItemId;
}
public void setinventoryItemId( java.lang.String inventoryItemId ) {
this.inventoryItemId = inventoryItemId;
}
private java.lang.String fixedAssetId;
public java.lang.String getfixedAssetId() {
return fixedAssetId;
}
public void setfixedAssetId( java.lang.String fixedAssetId ) {
this.fixedAssetId = fixedAssetId;
}
private java.lang.String acctgTransEntrySeqId;
public java.lang.String getacctgTransEntrySeqId() {
return acctgTransEntrySeqId;
}
public void setacctgTransEntrySeqId( java.lang.String acctgTransEntrySeqId ) {
this.acctgTransEntrySeqId = acctgTransEntrySeqId;
}
private java.lang.String transDescription;
public java.lang.String gettransDescription() {
return transDescription;
}
public void settransDescription( java.lang.String transDescription ) {
this.transDescription = transDescription;
}
private java.lang.String glAccountClassId;
public java.lang.String getglAccountClassId() {
return glAccountClassId;
}
public void setglAccountClassId( java.lang.String glAccountClassId ) {
this.glAccountClassId = glAccountClassId;
}
private java.lang.String glJournalId;
public java.lang.String getglJournalId() {
return glJournalId;
}
public void setglJournalId( java.lang.String glJournalId ) {
this.glJournalId = glJournalId;
}
private java.lang.String origCurrencyUomId;
public java.lang.String getorigCurrencyUomId() {
return origCurrencyUomId;
}
public void setorigCurrencyUomId( java.lang.String origCurrencyUomId ) {
this.origCurrencyUomId = origCurrencyUomId;
}
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
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
private java.sql.Timestamp transactionDate;
public java.sql.Timestamp gettransactionDate() {
return transactionDate;
}
public void settransactionDate( java.sql.Timestamp transactionDate ) {
this.transactionDate = transactionDate;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String transTypeDescription;
public java.lang.String gettransTypeDescription() {
return transTypeDescription;
}
public void settransTypeDescription( java.lang.String transTypeDescription ) {
this.transTypeDescription = transTypeDescription;
}
private java.lang.String workEffortId;
public java.lang.String getworkEffortId() {
return workEffortId;
}
public void setworkEffortId( java.lang.String workEffortId ) {
this.workEffortId = workEffortId;
}
private java.lang.String accountName;
public java.lang.String getaccountName() {
return accountName;
}
public void setaccountName( java.lang.String accountName ) {
this.accountName = accountName;
}
private java.lang.String physicalInventoryId;
public java.lang.String getphysicalInventoryId() {
return physicalInventoryId;
}
public void setphysicalInventoryId( java.lang.String physicalInventoryId ) {
this.physicalInventoryId = physicalInventoryId;
}
private java.sql.Timestamp postedDate;
public java.sql.Timestamp getpostedDate() {
return postedDate;
}
public void setpostedDate( java.sql.Timestamp postedDate ) {
this.postedDate = postedDate;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String isPosted;
public java.lang.String getisPosted() {
return isPosted;
}
public void setisPosted( java.lang.String isPosted ) {
this.isPosted = isPosted;
}
private java.lang.String receiptId;
public java.lang.String getreceiptId() {
return receiptId;
}
public void setreceiptId( java.lang.String receiptId ) {
this.receiptId = receiptId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<AcctgTransAndEntries> objectList = new ArrayList<AcctgTransAndEntries>();
        for (GenericValue genVal : genList) {
            objectList.add(new AcctgTransAndEntries(genVal));
        }
        return objectList;
    }    
}
