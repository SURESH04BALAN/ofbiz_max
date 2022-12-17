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

public class Invoice implements GenericValueObjectInterface {

    public static final String module = Invoice.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Invoice(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Invoice() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"invoiceDate", "Invoice Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"invoiceTypeId", "Invoice Type Id"},
        {"billingAccountId", "Billing Account Id"},
        {"invoiceId", "Invoice Id"},
        {"contactMechId", "Contact Mech Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"referenceNumber", "Reference Number"},
        {"partyIdFrom", "Party Id From"},
        {"statusId", "Status Id"},
        {"roleTypeId", "Role Type Id"},
        {"invoiceMessage", "Invoice Message"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"partyId", "Party Id"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"paidDate", "Paid Date"},
        {"dueDate", "Due Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"recurrenceInfoId", "Recurrence Info Id"},};

    protected void initObject() {
        this.invoiceDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.invoiceTypeId = "";
        this.billingAccountId = "";
        this.invoiceId = "";
        this.contactMechId = "";
        this.currencyUomId = "";
        this.referenceNumber = "";
        this.partyIdFrom = "";
        this.statusId = "";
        this.roleTypeId = "";
        this.invoiceMessage = "";
        this.createdTxStamp = null;
        this.partyId = "";
        this.createdStamp = null;
        this.description = "";
        this.paidDate = null;
        this.dueDate = null;
        this.lastUpdatedTxStamp = null;
        this.recurrenceInfoId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.invoiceDate = (java.sql.Timestamp) genVal.get("invoiceDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.invoiceId = (java.lang.String) genVal.get("invoiceId");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.referenceNumber = (java.lang.String) genVal.get("referenceNumber");
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
        this.invoiceMessage = (java.lang.String) genVal.get("invoiceMessage");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.paidDate = (java.sql.Timestamp) genVal.get("paidDate");
        this.dueDate = (java.sql.Timestamp) genVal.get("dueDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("invoiceDate", OrderMaxUtility.getValidEntityTimestamp(this.invoiceDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("invoiceTypeId", OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("invoiceId", OrderMaxUtility.getValidEntityString(this.invoiceId));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("referenceNumber", OrderMaxUtility.getValidEntityString(this.referenceNumber));
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("roleTypeId", OrderMaxUtility.getValidEntityString(this.roleTypeId));
        val.set("invoiceMessage", OrderMaxUtility.getValidEntityString(this.invoiceMessage));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("paidDate", OrderMaxUtility.getValidEntityTimestamp(this.paidDate));
        val.set("dueDate", OrderMaxUtility.getValidEntityTimestamp(this.dueDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("recurrenceInfoId", OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("invoiceDate", this.invoiceDate);
        valueMap.put("invoiceTypeId", this.invoiceTypeId);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("invoiceId", this.invoiceId);
        valueMap.put("contactMechId", this.contactMechId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("referenceNumber", this.referenceNumber);
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("statusId", this.statusId);
        valueMap.put("roleTypeId", this.roleTypeId);
        valueMap.put("invoiceMessage", this.invoiceMessage);
        valueMap.put("partyId", this.partyId);
        valueMap.put("description", this.description);
        valueMap.put("paidDate", this.paidDate);
        valueMap.put("dueDate", this.dueDate);
        valueMap.put("recurrenceInfoId", this.recurrenceInfoId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Invoice");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.sql.Timestamp invoiceDate;

    public java.sql.Timestamp getinvoiceDate() {
        return invoiceDate;
    }

    public void setinvoiceDate(java.sql.Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String invoiceTypeId;

    public java.lang.String getinvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setinvoiceTypeId(java.lang.String invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getbillingAccountId() {
        return billingAccountId;
    }

    public void setbillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
    private java.lang.String invoiceId;

    public java.lang.String getinvoiceId() {
        return invoiceId;
    }

    public void setinvoiceId(java.lang.String invoiceId) {
        this.invoiceId = invoiceId;
    }
    private java.lang.String contactMechId;

    public java.lang.String getcontactMechId() {
        return contactMechId;
    }

    public void setcontactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String referenceNumber;

    public java.lang.String getreferenceNumber() {
        return referenceNumber;
    }

    public void setreferenceNumber(java.lang.String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getpartyIdFrom() {
        return partyIdFrom;
    }

    public void setpartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String roleTypeId;

    public java.lang.String getroleTypeId() {
        return roleTypeId;
    }

    public void setroleTypeId(java.lang.String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    private java.lang.String invoiceMessage;

    public java.lang.String getinvoiceMessage() {
        return invoiceMessage;
    }

    public void setinvoiceMessage(java.lang.String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.sql.Timestamp paidDate;

    public java.sql.Timestamp getpaidDate() {
        return paidDate;
    }

    public void setpaidDate(java.sql.Timestamp paidDate) {
        this.paidDate = paidDate;
    }
    private java.sql.Timestamp dueDate;

    public java.sql.Timestamp getdueDate() {
        return dueDate;
    }

    public void setdueDate(java.sql.Timestamp dueDate) {
        this.dueDate = dueDate;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String recurrenceInfoId;

    public java.lang.String getrecurrenceInfoId() {
        return recurrenceInfoId;
    }

    public void setrecurrenceInfoId(java.lang.String recurrenceInfoId) {
        this.recurrenceInfoId = recurrenceInfoId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Invoice> objectList = new ArrayList<Invoice>();
        for (GenericValue genVal : genList) {
            objectList.add(new Invoice(genVal));
        }
        return objectList;
    }
}
