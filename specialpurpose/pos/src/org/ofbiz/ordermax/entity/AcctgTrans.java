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
public class AcctgTrans implements GenericValueObjectInterface {
public static final String module =AcctgTrans.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public AcctgTrans(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public AcctgTrans () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"glFiscalTypeId","Gl Fiscal Type Id"},
{"acctgTransId","Acctg Trans Id"},
{"paymentId","Payment Id"},
{"voucherDate","Voucher Date"},
{"acctgTransTypeId","Acctg Trans Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"shipmentId","Shipment Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"inventoryItemId","Inventory Item Id"},
{"fixedAssetId","Fixed Asset Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"glJournalId","Gl Journal Id"},
{"invoiceId","Invoice Id"},
{"transactionDate","Transaction Date"},
{"workEffortId","Work Effort Id"},
{"roleTypeId","Role Type Id"},
{"theirAcctgTransId","Their Acctg Trans Id"},
{"physicalInventoryId","Physical Inventory Id"},
{"postedDate","Posted Date"},
{"voucherRef","Voucher Ref"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"partyId","Party Id"},
{"finAccountTransId","Fin Account Trans Id"},
{"scheduledPostingDate","Scheduled Posting Date"},
{"groupStatusId","Group Status Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"isPosted","Is Posted"},
{"receiptId","Receipt Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.glFiscalTypeId = "";
this.acctgTransId = "";
this.paymentId = "";
this.voucherDate = UtilDateTime.nowTimestamp();
this.acctgTransTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.shipmentId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.inventoryItemId = "";
this.fixedAssetId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.glJournalId = "";
this.invoiceId = "";
this.transactionDate = UtilDateTime.nowTimestamp();
this.workEffortId = "";
this.roleTypeId = "";
this.theirAcctgTransId = "";
this.physicalInventoryId = "";
this.postedDate = UtilDateTime.nowTimestamp();
this.voucherRef = "";
this.lastModifiedByUserLogin = "";
this.partyId = "";
this.finAccountTransId = "";
this.scheduledPostingDate = UtilDateTime.nowTimestamp();
this.groupStatusId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.isPosted = "";
this.receiptId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.glFiscalTypeId = (java.lang.String) genVal.get("glFiscalTypeId");
this.acctgTransId = (java.lang.String) genVal.get("acctgTransId");
this.paymentId = (java.lang.String) genVal.get("paymentId");
this.voucherDate = (java.sql.Timestamp) genVal.get("voucherDate");
this.acctgTransTypeId = (java.lang.String) genVal.get("acctgTransTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.shipmentId = (java.lang.String) genVal.get("shipmentId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.glJournalId = (java.lang.String) genVal.get("glJournalId");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.transactionDate = (java.sql.Timestamp) genVal.get("transactionDate");
this.workEffortId = (java.lang.String) genVal.get("workEffortId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.theirAcctgTransId = (java.lang.String) genVal.get("theirAcctgTransId");
this.physicalInventoryId = (java.lang.String) genVal.get("physicalInventoryId");
this.postedDate = (java.sql.Timestamp) genVal.get("postedDate");
this.voucherRef = (java.lang.String) genVal.get("voucherRef");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.partyId = (java.lang.String) genVal.get("partyId");
this.finAccountTransId = (java.lang.String) genVal.get("finAccountTransId");
this.scheduledPostingDate = (java.sql.Timestamp) genVal.get("scheduledPostingDate");
this.groupStatusId = (java.lang.String) genVal.get("groupStatusId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.isPosted = (java.lang.String) genVal.get("isPosted");
this.receiptId = (java.lang.String) genVal.get("receiptId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("glFiscalTypeId",OrderMaxUtility.getValidEntityString(this.glFiscalTypeId));
val.set("acctgTransId",OrderMaxUtility.getValidEntityString(this.acctgTransId));
val.set("paymentId",OrderMaxUtility.getValidEntityString(this.paymentId));
val.set("voucherDate",OrderMaxUtility.getValidEntityTimestamp(this.voucherDate));
val.set("acctgTransTypeId",OrderMaxUtility.getValidEntityString(this.acctgTransTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("shipmentId",OrderMaxUtility.getValidEntityString(this.shipmentId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("inventoryItemId",OrderMaxUtility.getValidEntityString(this.inventoryItemId));
val.set("fixedAssetId",OrderMaxUtility.getValidEntityString(this.fixedAssetId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("glJournalId",OrderMaxUtility.getValidEntityString(this.glJournalId));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("transactionDate",OrderMaxUtility.getValidEntityTimestamp(this.transactionDate));
val.set("workEffortId",OrderMaxUtility.getValidEntityString(this.workEffortId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("theirAcctgTransId",OrderMaxUtility.getValidEntityString(this.theirAcctgTransId));
val.set("physicalInventoryId",OrderMaxUtility.getValidEntityString(this.physicalInventoryId));
val.set("postedDate",OrderMaxUtility.getValidEntityTimestamp(this.postedDate));
val.set("voucherRef",OrderMaxUtility.getValidEntityString(this.voucherRef));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("finAccountTransId",OrderMaxUtility.getValidEntityString(this.finAccountTransId));
val.set("scheduledPostingDate",OrderMaxUtility.getValidEntityTimestamp(this.scheduledPostingDate));
val.set("groupStatusId",OrderMaxUtility.getValidEntityString(this.groupStatusId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("isPosted",OrderMaxUtility.getValidEntityString(this.isPosted));
val.set("receiptId",OrderMaxUtility.getValidEntityString(this.receiptId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glFiscalTypeId",this.glFiscalTypeId);
valueMap.put("acctgTransId",this.acctgTransId);
valueMap.put("paymentId",this.paymentId);
valueMap.put("voucherDate",this.voucherDate);
valueMap.put("acctgTransTypeId",this.acctgTransTypeId);
valueMap.put("shipmentId",this.shipmentId);
valueMap.put("description",this.description);
valueMap.put("inventoryItemId",this.inventoryItemId);
valueMap.put("fixedAssetId",this.fixedAssetId);
valueMap.put("glJournalId",this.glJournalId);
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("transactionDate",this.transactionDate);
valueMap.put("workEffortId",this.workEffortId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("theirAcctgTransId",this.theirAcctgTransId);
valueMap.put("physicalInventoryId",this.physicalInventoryId);
valueMap.put("postedDate",this.postedDate);
valueMap.put("voucherRef",this.voucherRef);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("partyId",this.partyId);
valueMap.put("finAccountTransId",this.finAccountTransId);
valueMap.put("scheduledPostingDate",this.scheduledPostingDate);
valueMap.put("groupStatusId",this.groupStatusId);
valueMap.put("isPosted",this.isPosted);
valueMap.put("receiptId",this.receiptId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("AcctgTrans");
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
private java.sql.Timestamp voucherDate;
public java.sql.Timestamp getvoucherDate() {
return voucherDate;
}
public void setvoucherDate( java.sql.Timestamp voucherDate ) {
this.voucherDate = voucherDate;
}
private java.lang.String acctgTransTypeId;
public java.lang.String getacctgTransTypeId() {
return acctgTransTypeId;
}
public void setacctgTransTypeId( java.lang.String acctgTransTypeId ) {
this.acctgTransTypeId = acctgTransTypeId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String shipmentId;
public java.lang.String getshipmentId() {
return shipmentId;
}
public void setshipmentId( java.lang.String shipmentId ) {
this.shipmentId = shipmentId;
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
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String glJournalId;
public java.lang.String getglJournalId() {
return glJournalId;
}
public void setglJournalId( java.lang.String glJournalId ) {
this.glJournalId = glJournalId;
}
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.sql.Timestamp transactionDate;
public java.sql.Timestamp gettransactionDate() {
return transactionDate;
}
public void settransactionDate( java.sql.Timestamp transactionDate ) {
this.transactionDate = transactionDate;
}
private java.lang.String workEffortId;
public java.lang.String getworkEffortId() {
return workEffortId;
}
public void setworkEffortId( java.lang.String workEffortId ) {
this.workEffortId = workEffortId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String theirAcctgTransId;
public java.lang.String gettheirAcctgTransId() {
return theirAcctgTransId;
}
public void settheirAcctgTransId( java.lang.String theirAcctgTransId ) {
this.theirAcctgTransId = theirAcctgTransId;
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
private java.lang.String voucherRef;
public java.lang.String getvoucherRef() {
return voucherRef;
}
public void setvoucherRef( java.lang.String voucherRef ) {
this.voucherRef = voucherRef;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String finAccountTransId;
public java.lang.String getfinAccountTransId() {
return finAccountTransId;
}
public void setfinAccountTransId( java.lang.String finAccountTransId ) {
this.finAccountTransId = finAccountTransId;
}
private java.sql.Timestamp scheduledPostingDate;
public java.sql.Timestamp getscheduledPostingDate() {
return scheduledPostingDate;
}
public void setscheduledPostingDate( java.sql.Timestamp scheduledPostingDate ) {
this.scheduledPostingDate = scheduledPostingDate;
}
private java.lang.String groupStatusId;
public java.lang.String getgroupStatusId() {
return groupStatusId;
}
public void setgroupStatusId( java.lang.String groupStatusId ) {
this.groupStatusId = groupStatusId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<AcctgTrans> objectList = new ArrayList<AcctgTrans>();
        for (GenericValue genVal : genList) {
            objectList.add(new AcctgTrans(genVal));
        }
        return objectList;
    }    
}
