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
public class PartyAcctgPreference implements GenericValueObjectInterface {
public static final String module =PartyAcctgPreference.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAcctgPreference(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAcctgPreference () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastInvoiceNumber","Last Invoice Number"},
{"fiscalYearStartDay","Fiscal Year Start Day"},
{"orderIdPrefix","Order Id Prefix"},
{"cogsMethodId","Cogs Method Id"},
{"quoteIdPrefix","Quote Id Prefix"},
{"fiscalYearStartMonth","Fiscal Year Start Month"},
{"oldOrderSequenceEnumId","Old Order Sequence Enum Id"},
{"errorGlJournalId","Error Gl Journal Id"},
{"lastQuoteNumber","Last Quote Number"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"oldQuoteSequenceEnumId","Old Quote Sequence Enum Id"},
{"oldInvoiceSequenceEnumId","Old Invoice Sequence Enum Id"},
{"taxFormId","Tax Form Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"lastInvoiceRestartDate","Last Invoice Restart Date"},
{"invoiceIdPrefix","Invoice Id Prefix"},
{"baseCurrencyUomId","Base Currency Uom Id"},
{"quoteSeqCustMethId","Quote Seq Cust Meth Id"},
{"invoiceSeqCustMethId","Invoice Seq Cust Meth Id"},
{"useInvoiceIdForReturns","Use Invoice Id For Returns"},
{"partyId","Party Id"},
{"refundPaymentMethodId","Refund Payment Method Id"},
{"orderSeqCustMethId","Order Seq Cust Meth Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"lastOrderNumber","Last Order Number"},
};
protected void initObject(){
this.lastInvoiceNumber = new Long(0);
this.fiscalYearStartDay = "";
this.orderIdPrefix = "";
this.cogsMethodId = "";
this.quoteIdPrefix = "";
this.fiscalYearStartMonth = "";
this.oldOrderSequenceEnumId = "";
this.errorGlJournalId = "";
this.lastQuoteNumber = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.oldQuoteSequenceEnumId = "";
this.oldInvoiceSequenceEnumId = "";
this.taxFormId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.lastInvoiceRestartDate = UtilDateTime.nowTimestamp();
this.invoiceIdPrefix = "";
this.baseCurrencyUomId = "";
this.quoteSeqCustMethId = "";
this.invoiceSeqCustMethId = "";
this.useInvoiceIdForReturns = "";
this.partyId = "";
this.refundPaymentMethodId = "";
this.orderSeqCustMethId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.lastOrderNumber = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastInvoiceNumber = (java.lang.Long) genVal.get("lastInvoiceNumber");
this.fiscalYearStartDay = (java.lang.String) genVal.get("fiscalYearStartDay");
this.orderIdPrefix = (java.lang.String) genVal.get("orderIdPrefix");
this.cogsMethodId = (java.lang.String) genVal.get("cogsMethodId");
this.quoteIdPrefix = (java.lang.String) genVal.get("quoteIdPrefix");
this.fiscalYearStartMonth = (java.lang.String) genVal.get("fiscalYearStartMonth");
this.oldOrderSequenceEnumId = (java.lang.String) genVal.get("oldOrderSequenceEnumId");
this.errorGlJournalId = (java.lang.String) genVal.get("errorGlJournalId");
this.lastQuoteNumber = (java.lang.String) genVal.get("lastQuoteNumber");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.oldQuoteSequenceEnumId = (java.lang.String) genVal.get("oldQuoteSequenceEnumId");
this.oldInvoiceSequenceEnumId = (java.lang.String) genVal.get("oldInvoiceSequenceEnumId");
this.taxFormId = (java.lang.String) genVal.get("taxFormId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.lastInvoiceRestartDate = (java.sql.Timestamp) genVal.get("lastInvoiceRestartDate");
this.invoiceIdPrefix = (java.lang.String) genVal.get("invoiceIdPrefix");
this.baseCurrencyUomId = (java.lang.String) genVal.get("baseCurrencyUomId");
this.quoteSeqCustMethId = (java.lang.String) genVal.get("quoteSeqCustMethId");
this.invoiceSeqCustMethId = (java.lang.String) genVal.get("invoiceSeqCustMethId");
this.useInvoiceIdForReturns = (java.lang.String) genVal.get("useInvoiceIdForReturns");
this.partyId = (java.lang.String) genVal.get("partyId");
this.refundPaymentMethodId = (java.lang.String) genVal.get("refundPaymentMethodId");
this.orderSeqCustMethId = (java.lang.String) genVal.get("orderSeqCustMethId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.lastOrderNumber = (java.lang.String) genVal.get("lastOrderNumber");
}
protected void getGenericValue(GenericValue val) {
val.set("lastInvoiceNumber",this.lastInvoiceNumber);
val.set("fiscalYearStartDay",OrderMaxUtility.getValidEntityString(this.fiscalYearStartDay));
val.set("orderIdPrefix",OrderMaxUtility.getValidEntityString(this.orderIdPrefix));
val.set("cogsMethodId",OrderMaxUtility.getValidEntityString(this.cogsMethodId));
val.set("quoteIdPrefix",OrderMaxUtility.getValidEntityString(this.quoteIdPrefix));
val.set("fiscalYearStartMonth",OrderMaxUtility.getValidEntityString(this.fiscalYearStartMonth));
val.set("oldOrderSequenceEnumId",OrderMaxUtility.getValidEntityString(this.oldOrderSequenceEnumId));
val.set("errorGlJournalId",OrderMaxUtility.getValidEntityString(this.errorGlJournalId));
val.set("lastQuoteNumber",OrderMaxUtility.getValidEntityString(this.lastQuoteNumber));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("oldQuoteSequenceEnumId",OrderMaxUtility.getValidEntityString(this.oldQuoteSequenceEnumId));
val.set("oldInvoiceSequenceEnumId",OrderMaxUtility.getValidEntityString(this.oldInvoiceSequenceEnumId));
val.set("taxFormId",OrderMaxUtility.getValidEntityString(this.taxFormId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("lastInvoiceRestartDate",OrderMaxUtility.getValidEntityTimestamp(this.lastInvoiceRestartDate));
val.set("invoiceIdPrefix",OrderMaxUtility.getValidEntityString(this.invoiceIdPrefix));
val.set("baseCurrencyUomId",OrderMaxUtility.getValidEntityString(this.baseCurrencyUomId));
val.set("quoteSeqCustMethId",OrderMaxUtility.getValidEntityString(this.quoteSeqCustMethId));
val.set("invoiceSeqCustMethId",OrderMaxUtility.getValidEntityString(this.invoiceSeqCustMethId));
val.set("useInvoiceIdForReturns",OrderMaxUtility.getValidEntityString(this.useInvoiceIdForReturns));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("refundPaymentMethodId",OrderMaxUtility.getValidEntityString(this.refundPaymentMethodId));
val.set("orderSeqCustMethId",OrderMaxUtility.getValidEntityString(this.orderSeqCustMethId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("lastOrderNumber",OrderMaxUtility.getValidEntityString(this.lastOrderNumber));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("lastInvoiceNumber",this.lastInvoiceNumber);
valueMap.put("fiscalYearStartDay",this.fiscalYearStartDay);
valueMap.put("orderIdPrefix",this.orderIdPrefix);
valueMap.put("cogsMethodId",this.cogsMethodId);
valueMap.put("quoteIdPrefix",this.quoteIdPrefix);
valueMap.put("fiscalYearStartMonth",this.fiscalYearStartMonth);
valueMap.put("oldOrderSequenceEnumId",this.oldOrderSequenceEnumId);
valueMap.put("errorGlJournalId",this.errorGlJournalId);
valueMap.put("lastQuoteNumber",this.lastQuoteNumber);
valueMap.put("oldQuoteSequenceEnumId",this.oldQuoteSequenceEnumId);
valueMap.put("oldInvoiceSequenceEnumId",this.oldInvoiceSequenceEnumId);
valueMap.put("taxFormId",this.taxFormId);
valueMap.put("lastInvoiceRestartDate",this.lastInvoiceRestartDate);
valueMap.put("invoiceIdPrefix",this.invoiceIdPrefix);
valueMap.put("baseCurrencyUomId",this.baseCurrencyUomId);
valueMap.put("quoteSeqCustMethId",this.quoteSeqCustMethId);
valueMap.put("invoiceSeqCustMethId",this.invoiceSeqCustMethId);
valueMap.put("useInvoiceIdForReturns",this.useInvoiceIdForReturns);
valueMap.put("partyId",this.partyId);
valueMap.put("refundPaymentMethodId",this.refundPaymentMethodId);
valueMap.put("orderSeqCustMethId",this.orderSeqCustMethId);
valueMap.put("lastOrderNumber",this.lastOrderNumber);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAcctgPreference");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long lastInvoiceNumber;
public java.lang.Long getlastInvoiceNumber() {
return lastInvoiceNumber;
}
public void setlastInvoiceNumber( java.lang.Long lastInvoiceNumber ) {
this.lastInvoiceNumber = lastInvoiceNumber;
}
private java.lang.String fiscalYearStartDay;
public java.lang.String getfiscalYearStartDay() {
return fiscalYearStartDay;
}
public void setfiscalYearStartDay( java.lang.String fiscalYearStartDay ) {
this.fiscalYearStartDay = fiscalYearStartDay;
}
private java.lang.String orderIdPrefix;
public java.lang.String getorderIdPrefix() {
return orderIdPrefix;
}
public void setorderIdPrefix( java.lang.String orderIdPrefix ) {
this.orderIdPrefix = orderIdPrefix;
}
private java.lang.String cogsMethodId;
public java.lang.String getcogsMethodId() {
return cogsMethodId;
}
public void setcogsMethodId( java.lang.String cogsMethodId ) {
this.cogsMethodId = cogsMethodId;
}
private java.lang.String quoteIdPrefix;
public java.lang.String getquoteIdPrefix() {
return quoteIdPrefix;
}
public void setquoteIdPrefix( java.lang.String quoteIdPrefix ) {
this.quoteIdPrefix = quoteIdPrefix;
}
private java.lang.String fiscalYearStartMonth;
public java.lang.String getfiscalYearStartMonth() {
return fiscalYearStartMonth;
}
public void setfiscalYearStartMonth( java.lang.String fiscalYearStartMonth ) {
this.fiscalYearStartMonth = fiscalYearStartMonth;
}
private java.lang.String oldOrderSequenceEnumId;
public java.lang.String getoldOrderSequenceEnumId() {
return oldOrderSequenceEnumId;
}
public void setoldOrderSequenceEnumId( java.lang.String oldOrderSequenceEnumId ) {
this.oldOrderSequenceEnumId = oldOrderSequenceEnumId;
}
private java.lang.String errorGlJournalId;
public java.lang.String geterrorGlJournalId() {
return errorGlJournalId;
}
public void seterrorGlJournalId( java.lang.String errorGlJournalId ) {
this.errorGlJournalId = errorGlJournalId;
}
private java.lang.String lastQuoteNumber;
public java.lang.String getlastQuoteNumber() {
return lastQuoteNumber;
}
public void setlastQuoteNumber( java.lang.String lastQuoteNumber ) {
this.lastQuoteNumber = lastQuoteNumber;
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
private java.lang.String oldQuoteSequenceEnumId;
public java.lang.String getoldQuoteSequenceEnumId() {
return oldQuoteSequenceEnumId;
}
public void setoldQuoteSequenceEnumId( java.lang.String oldQuoteSequenceEnumId ) {
this.oldQuoteSequenceEnumId = oldQuoteSequenceEnumId;
}
private java.lang.String oldInvoiceSequenceEnumId;
public java.lang.String getoldInvoiceSequenceEnumId() {
return oldInvoiceSequenceEnumId;
}
public void setoldInvoiceSequenceEnumId( java.lang.String oldInvoiceSequenceEnumId ) {
this.oldInvoiceSequenceEnumId = oldInvoiceSequenceEnumId;
}
private java.lang.String taxFormId;
public java.lang.String gettaxFormId() {
return taxFormId;
}
public void settaxFormId( java.lang.String taxFormId ) {
this.taxFormId = taxFormId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.sql.Timestamp lastInvoiceRestartDate;
public java.sql.Timestamp getlastInvoiceRestartDate() {
return lastInvoiceRestartDate;
}
public void setlastInvoiceRestartDate( java.sql.Timestamp lastInvoiceRestartDate ) {
this.lastInvoiceRestartDate = lastInvoiceRestartDate;
}
private java.lang.String invoiceIdPrefix;
public java.lang.String getinvoiceIdPrefix() {
return invoiceIdPrefix;
}
public void setinvoiceIdPrefix( java.lang.String invoiceIdPrefix ) {
this.invoiceIdPrefix = invoiceIdPrefix;
}
private java.lang.String baseCurrencyUomId;
public java.lang.String getbaseCurrencyUomId() {
return baseCurrencyUomId;
}
public void setbaseCurrencyUomId( java.lang.String baseCurrencyUomId ) {
this.baseCurrencyUomId = baseCurrencyUomId;
}
private java.lang.String quoteSeqCustMethId;
public java.lang.String getquoteSeqCustMethId() {
return quoteSeqCustMethId;
}
public void setquoteSeqCustMethId( java.lang.String quoteSeqCustMethId ) {
this.quoteSeqCustMethId = quoteSeqCustMethId;
}
private java.lang.String invoiceSeqCustMethId;
public java.lang.String getinvoiceSeqCustMethId() {
return invoiceSeqCustMethId;
}
public void setinvoiceSeqCustMethId( java.lang.String invoiceSeqCustMethId ) {
this.invoiceSeqCustMethId = invoiceSeqCustMethId;
}
private java.lang.String useInvoiceIdForReturns;
public java.lang.String getuseInvoiceIdForReturns() {
return useInvoiceIdForReturns;
}
public void setuseInvoiceIdForReturns( java.lang.String useInvoiceIdForReturns ) {
this.useInvoiceIdForReturns = useInvoiceIdForReturns;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String refundPaymentMethodId;
public java.lang.String getrefundPaymentMethodId() {
return refundPaymentMethodId;
}
public void setrefundPaymentMethodId( java.lang.String refundPaymentMethodId ) {
this.refundPaymentMethodId = refundPaymentMethodId;
}
private java.lang.String orderSeqCustMethId;
public java.lang.String getorderSeqCustMethId() {
return orderSeqCustMethId;
}
public void setorderSeqCustMethId( java.lang.String orderSeqCustMethId ) {
this.orderSeqCustMethId = orderSeqCustMethId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String lastOrderNumber;
public java.lang.String getlastOrderNumber() {
return lastOrderNumber;
}
public void setlastOrderNumber( java.lang.String lastOrderNumber ) {
this.lastOrderNumber = lastOrderNumber;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAcctgPreference> objectList = new ArrayList<PartyAcctgPreference>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAcctgPreference(genVal));
        }
        return objectList;
    }    
}
