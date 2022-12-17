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

public class InvoiceAndApplAndPayment implements GenericValueObjectInterface {

    public static final String module = InvoiceAndApplAndPayment.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InvoiceAndApplAndPayment(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public InvoiceAndApplAndPayment() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"invoiceDate", "Invoice Date"},
        {"pmRoleTypeIdTo", "Pm Role Type Id To"},
        {"invoiceTypeId", "Invoice Type Id"},
        {"pmEffectiveDate", "Pm Effective Date"},
        {"paymentId", "Payment Id"},
        {"referenceNumber", "Reference Number"},
        {"pmPaymentId", "Pm Payment Id"},
        {"invoiceItemSeqId", "Invoice Item Seq Id"},
        {"pmAmount", "Pm Amount"},
        {"partyIdFrom", "Party Id From"},
        {"pmPaymentTypeId", "Pm Payment Type Id"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"statusId", "Status Id"},
        {"pmPaymentGatewayResponseId", "Pm Payment Gateway Response Id"},
        {"pmOverrideGlAccountId", "Pm Override Gl Account Id"},
        {"invoiceMessage", "Invoice Message"},
        {"pmPaymentMethodId", "Pm Payment Method Id"},
        {"description", "Description"},
        {"pmFinAccountTransId", "Pm Fin Account Trans Id"},
        {"dueDate", "Due Date"},
        {"toPaymentId", "To Payment Id"},
        {"pmActualCurrencyUomId", "Pm Actual Currency Uom Id"},
        {"amountApplied", "Amount Applied"},
        {"pmPaymentPreferenceId", "Pm Payment Preference Id"},
        {"pmStatusId", "Pm Status Id"},
        {"invoiceId", "Invoice Id"},
        {"billingAccountId", "Billing Account Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"contactMechId", "Contact Mech Id"},
        {"pmActualCurrencyAmount", "Pm Actual Currency Amount"},
        {"pmComments", "Pm Comments"},
        {"pmPaymentMethodTypeId", "Pm Payment Method Type Id"},
        {"paymentApplicationId", "Payment Application Id"},
        {"pmPartyIdFrom", "Pm Party Id From"},
        {"taxAuthGeoId", "Tax Auth Geo Id"},
        {"roleTypeId", "Role Type Id"},
        {"pmPartyIdTo", "Pm Party Id To"},
        {"partyId", "Party Id"},
        {"pmCurrencyUomId", "Pm Currency Uom Id"},
        {"pmPaymentRefNum", "Pm Payment Ref Num"},
        {"paidDate", "Paid Date"},
        {"recurrenceInfoId", "Recurrence Info Id"},};

    protected void initObject() {
        this.invoiceDate = UtilDateTime.nowTimestamp();
        this.pmRoleTypeIdTo = "";
        this.invoiceTypeId = "";
        this.pmEffectiveDate = UtilDateTime.nowTimestamp();
        this.paymentId = "";
        this.referenceNumber = "";
        this.pmPaymentId = "";
        this.invoiceItemSeqId = "";
        this.pmAmount = java.math.BigDecimal.ZERO;
        this.partyIdFrom = "";
        this.pmPaymentTypeId = "";
        this.overrideGlAccountId = "";
        this.statusId = "";
        this.pmPaymentGatewayResponseId = "";
        this.pmOverrideGlAccountId = "";
        this.invoiceMessage = "";
        this.pmPaymentMethodId = "";
        this.description = "";
        this.pmFinAccountTransId = "";
        this.dueDate = UtilDateTime.nowTimestamp();
        this.toPaymentId = "";
        this.pmActualCurrencyUomId = "";
        this.amountApplied = java.math.BigDecimal.ZERO;
        this.pmPaymentPreferenceId = "";
        this.pmStatusId = "";
        this.invoiceId = "";
        this.billingAccountId = "";
        this.currencyUomId = "";
        this.contactMechId = "";
        this.pmActualCurrencyAmount = "";
        this.pmComments = "";
        this.pmPaymentMethodTypeId = "";
        this.paymentApplicationId = "";
        this.pmPartyIdFrom = "";
        this.taxAuthGeoId = "";
        this.roleTypeId = "";
        this.pmPartyIdTo = "";
        this.partyId = "";
        this.pmCurrencyUomId = "";
        this.pmPaymentRefNum = "";
        this.paidDate = UtilDateTime.nowTimestamp();
        this.recurrenceInfoId = "";
    }

    @Override
    public void setGenericValue() throws Exception {
        
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        
        this.invoiceDate = (java.sql.Timestamp) genVal.get("invoiceDate");
        this.pmRoleTypeIdTo = (java.lang.String) genVal.get("pmRoleTypeIdTo");
        this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
        this.pmEffectiveDate = (java.sql.Timestamp) genVal.get("pmEffectiveDate");
        this.paymentId = (java.lang.String) genVal.get("paymentId");
        this.referenceNumber = (java.lang.String) genVal.get("referenceNumber");
        this.pmPaymentId = (java.lang.String) genVal.get("pmPaymentId");
        this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
        this.pmAmount = (java.math.BigDecimal) genVal.get("pmAmount");
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.pmPaymentTypeId = (java.lang.String) genVal.get("pmPaymentTypeId");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.pmPaymentGatewayResponseId = (java.lang.String) genVal.get("pmPaymentGatewayResponseId");
        this.pmOverrideGlAccountId = (java.lang.String) genVal.get("pmOverrideGlAccountId");
        this.invoiceMessage = (java.lang.String) genVal.get("invoiceMessage");
        this.pmPaymentMethodId = (java.lang.String) genVal.get("pmPaymentMethodId");
        this.description = (java.lang.String) genVal.get("description");
        this.pmFinAccountTransId = (java.lang.String) genVal.get("pmFinAccountTransId");
        this.dueDate = (java.sql.Timestamp) genVal.get("dueDate");
        this.toPaymentId = (java.lang.String) genVal.get("toPaymentId");
        this.pmActualCurrencyUomId = (java.lang.String) genVal.get("pmActualCurrencyUomId");
        this.amountApplied = (java.math.BigDecimal) genVal.get("amountApplied");
        this.pmPaymentPreferenceId = (java.lang.String) genVal.get("pmPaymentPreferenceId");
        this.pmStatusId = (java.lang.String) genVal.get("pmStatusId");
        this.invoiceId = (java.lang.String) genVal.get("invoiceId");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.pmActualCurrencyAmount = (java.lang.String) genVal.get("pmActualCurrencyAmount");
        this.pmComments = (java.lang.String) genVal.get("pmComments");
        this.pmPaymentMethodTypeId = (java.lang.String) genVal.get("pmPaymentMethodTypeId");
        this.paymentApplicationId = (java.lang.String) genVal.get("paymentApplicationId");
        this.pmPartyIdFrom = (java.lang.String) genVal.get("pmPartyIdFrom");
        this.taxAuthGeoId = (java.lang.String) genVal.get("taxAuthGeoId");
        this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
        this.pmPartyIdTo = (java.lang.String) genVal.get("pmPartyIdTo");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.pmCurrencyUomId = (java.lang.String) genVal.get("pmCurrencyUomId");
        this.pmPaymentRefNum = (java.lang.String) genVal.get("pmPaymentRefNum");
        this.paidDate = (java.sql.Timestamp) genVal.get("paidDate");
        this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("invoiceDate", OrderMaxUtility.getValidEntityTimestamp(this.invoiceDate));
        val.set("pmRoleTypeIdTo", OrderMaxUtility.getValidEntityString(this.pmRoleTypeIdTo));
        val.set("invoiceTypeId", OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
        val.set("pmEffectiveDate", OrderMaxUtility.getValidEntityTimestamp(this.pmEffectiveDate));
        val.set("paymentId", OrderMaxUtility.getValidEntityString(this.paymentId));
        val.set("referenceNumber", OrderMaxUtility.getValidEntityString(this.referenceNumber));
        val.set("pmPaymentId", OrderMaxUtility.getValidEntityString(this.pmPaymentId));
        val.set("invoiceItemSeqId", OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
        val.set("pmAmount", OrderMaxUtility.getValidEntityBigDecimal(this.pmAmount));
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("pmPaymentTypeId", OrderMaxUtility.getValidEntityString(this.pmPaymentTypeId));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("pmPaymentGatewayResponseId", OrderMaxUtility.getValidEntityString(this.pmPaymentGatewayResponseId));
        val.set("pmOverrideGlAccountId", OrderMaxUtility.getValidEntityString(this.pmOverrideGlAccountId));
        val.set("invoiceMessage", OrderMaxUtility.getValidEntityString(this.invoiceMessage));
        val.set("pmPaymentMethodId", OrderMaxUtility.getValidEntityString(this.pmPaymentMethodId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("pmFinAccountTransId", OrderMaxUtility.getValidEntityString(this.pmFinAccountTransId));
        val.set("dueDate", OrderMaxUtility.getValidEntityTimestamp(this.dueDate));
        val.set("toPaymentId", OrderMaxUtility.getValidEntityString(this.toPaymentId));
        val.set("pmActualCurrencyUomId", OrderMaxUtility.getValidEntityString(this.pmActualCurrencyUomId));
        val.set("amountApplied", OrderMaxUtility.getValidEntityBigDecimal(this.amountApplied));
        val.set("pmPaymentPreferenceId", OrderMaxUtility.getValidEntityString(this.pmPaymentPreferenceId));
        val.set("pmStatusId", OrderMaxUtility.getValidEntityString(this.pmStatusId));
        val.set("invoiceId", OrderMaxUtility.getValidEntityString(this.invoiceId));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("pmActualCurrencyAmount", OrderMaxUtility.getValidEntityString(this.pmActualCurrencyAmount));
        val.set("pmComments", OrderMaxUtility.getValidEntityString(this.pmComments));
        val.set("pmPaymentMethodTypeId", OrderMaxUtility.getValidEntityString(this.pmPaymentMethodTypeId));
        val.set("paymentApplicationId", OrderMaxUtility.getValidEntityString(this.paymentApplicationId));
        val.set("pmPartyIdFrom", OrderMaxUtility.getValidEntityString(this.pmPartyIdFrom));
        val.set("taxAuthGeoId", OrderMaxUtility.getValidEntityString(this.taxAuthGeoId));
        val.set("roleTypeId", OrderMaxUtility.getValidEntityString(this.roleTypeId));
        val.set("pmPartyIdTo", OrderMaxUtility.getValidEntityString(this.pmPartyIdTo));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("pmCurrencyUomId", OrderMaxUtility.getValidEntityString(this.pmCurrencyUomId));
        val.set("pmPaymentRefNum", OrderMaxUtility.getValidEntityString(this.pmPaymentRefNum));
        val.set("paidDate", OrderMaxUtility.getValidEntityTimestamp(this.paidDate));
        val.set("recurrenceInfoId", OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("invoiceDate", this.invoiceDate);
        valueMap.put("pmRoleTypeIdTo", this.pmRoleTypeIdTo);
        valueMap.put("invoiceTypeId", this.invoiceTypeId);
        valueMap.put("pmEffectiveDate", this.pmEffectiveDate);
        valueMap.put("paymentId", this.paymentId);
        valueMap.put("referenceNumber", this.referenceNumber);
        valueMap.put("pmPaymentId", this.pmPaymentId);
        valueMap.put("invoiceItemSeqId", this.invoiceItemSeqId);
        valueMap.put("pmAmount", this.pmAmount);
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("pmPaymentTypeId", this.pmPaymentTypeId);
        valueMap.put("overrideGlAccountId", this.overrideGlAccountId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("pmPaymentGatewayResponseId", this.pmPaymentGatewayResponseId);
        valueMap.put("pmOverrideGlAccountId", this.pmOverrideGlAccountId);
        valueMap.put("invoiceMessage", this.invoiceMessage);
        valueMap.put("pmPaymentMethodId", this.pmPaymentMethodId);
        valueMap.put("description", this.description);
        valueMap.put("pmFinAccountTransId", this.pmFinAccountTransId);
        valueMap.put("dueDate", this.dueDate);
        valueMap.put("toPaymentId", this.toPaymentId);
        valueMap.put("pmActualCurrencyUomId", this.pmActualCurrencyUomId);
        valueMap.put("amountApplied", this.amountApplied);
        valueMap.put("pmPaymentPreferenceId", this.pmPaymentPreferenceId);
        valueMap.put("pmStatusId", this.pmStatusId);
        valueMap.put("invoiceId", this.invoiceId);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("contactMechId", this.contactMechId);
        valueMap.put("pmActualCurrencyAmount", this.pmActualCurrencyAmount);
        valueMap.put("pmComments", this.pmComments);
        valueMap.put("pmPaymentMethodTypeId", this.pmPaymentMethodTypeId);
        valueMap.put("paymentApplicationId", this.paymentApplicationId);
        valueMap.put("pmPartyIdFrom", this.pmPartyIdFrom);
        valueMap.put("taxAuthGeoId", this.taxAuthGeoId);
        valueMap.put("roleTypeId", this.roleTypeId);
        valueMap.put("pmPartyIdTo", this.pmPartyIdTo);
        valueMap.put("partyId", this.partyId);
        valueMap.put("pmCurrencyUomId", this.pmCurrencyUomId);
        valueMap.put("pmPaymentRefNum", this.pmPaymentRefNum);
        valueMap.put("paidDate", this.paidDate);
        valueMap.put("recurrenceInfoId", this.recurrenceInfoId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InvoiceAndApplAndPayment");
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
    private java.lang.String pmRoleTypeIdTo;

    public java.lang.String getpmRoleTypeIdTo() {
        return pmRoleTypeIdTo;
    }

    public void setpmRoleTypeIdTo(java.lang.String pmRoleTypeIdTo) {
        this.pmRoleTypeIdTo = pmRoleTypeIdTo;
    }
    private java.lang.String invoiceTypeId;

    public java.lang.String getinvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setinvoiceTypeId(java.lang.String invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }
    private java.sql.Timestamp pmEffectiveDate;

    public java.sql.Timestamp getpmEffectiveDate() {
        return pmEffectiveDate;
    }

    public void setpmEffectiveDate(java.sql.Timestamp pmEffectiveDate) {
        this.pmEffectiveDate = pmEffectiveDate;
    }
    private java.lang.String paymentId;

    public java.lang.String getpaymentId() {
        return paymentId;
    }

    public void setpaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
    }
    private java.lang.String referenceNumber;

    public java.lang.String getreferenceNumber() {
        return referenceNumber;
    }

    public void setreferenceNumber(java.lang.String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    private java.lang.String pmPaymentId;

    public java.lang.String getpmPaymentId() {
        return pmPaymentId;
    }

    public void setpmPaymentId(java.lang.String pmPaymentId) {
        this.pmPaymentId = pmPaymentId;
    }
    private java.lang.String invoiceItemSeqId;

    public java.lang.String getinvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setinvoiceItemSeqId(java.lang.String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    private java.math.BigDecimal pmAmount;

    public java.math.BigDecimal getpmAmount() {
        return pmAmount;
    }

    public void setpmAmount(java.math.BigDecimal pmAmount) {
        this.pmAmount = pmAmount;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getpartyIdFrom() {
        return partyIdFrom;
    }

    public void setpartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    private java.lang.String pmPaymentTypeId;

    public java.lang.String getpmPaymentTypeId() {
        return pmPaymentTypeId;
    }

    public void setpmPaymentTypeId(java.lang.String pmPaymentTypeId) {
        this.pmPaymentTypeId = pmPaymentTypeId;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getoverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setoverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String pmPaymentGatewayResponseId;

    public java.lang.String getpmPaymentGatewayResponseId() {
        return pmPaymentGatewayResponseId;
    }

    public void setpmPaymentGatewayResponseId(java.lang.String pmPaymentGatewayResponseId) {
        this.pmPaymentGatewayResponseId = pmPaymentGatewayResponseId;
    }
    private java.lang.String pmOverrideGlAccountId;

    public java.lang.String getpmOverrideGlAccountId() {
        return pmOverrideGlAccountId;
    }

    public void setpmOverrideGlAccountId(java.lang.String pmOverrideGlAccountId) {
        this.pmOverrideGlAccountId = pmOverrideGlAccountId;
    }
    private java.lang.String invoiceMessage;

    public java.lang.String getinvoiceMessage() {
        return invoiceMessage;
    }

    public void setinvoiceMessage(java.lang.String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }
    private java.lang.String pmPaymentMethodId;

    public java.lang.String getpmPaymentMethodId() {
        return pmPaymentMethodId;
    }

    public void setpmPaymentMethodId(java.lang.String pmPaymentMethodId) {
        this.pmPaymentMethodId = pmPaymentMethodId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String pmFinAccountTransId;

    public java.lang.String getpmFinAccountTransId() {
        return pmFinAccountTransId;
    }

    public void setpmFinAccountTransId(java.lang.String pmFinAccountTransId) {
        this.pmFinAccountTransId = pmFinAccountTransId;
    }
    private java.sql.Timestamp dueDate;

    public java.sql.Timestamp getdueDate() {
        return dueDate;
    }

    public void setdueDate(java.sql.Timestamp dueDate) {
        this.dueDate = dueDate;
    }
    private java.lang.String toPaymentId;

    public java.lang.String gettoPaymentId() {
        return toPaymentId;
    }

    public void settoPaymentId(java.lang.String toPaymentId) {
        this.toPaymentId = toPaymentId;
    }
    private java.lang.String pmActualCurrencyUomId;

    public java.lang.String getpmActualCurrencyUomId() {
        return pmActualCurrencyUomId;
    }

    public void setpmActualCurrencyUomId(java.lang.String pmActualCurrencyUomId) {
        this.pmActualCurrencyUomId = pmActualCurrencyUomId;
    }
    private java.math.BigDecimal amountApplied;

    public java.math.BigDecimal getamountApplied() {
        return amountApplied;
    }

    public void setamountApplied(java.math.BigDecimal amountApplied) {
        this.amountApplied = amountApplied;
    }
    private java.lang.String pmPaymentPreferenceId;

    public java.lang.String getpmPaymentPreferenceId() {
        return pmPaymentPreferenceId;
    }

    public void setpmPaymentPreferenceId(java.lang.String pmPaymentPreferenceId) {
        this.pmPaymentPreferenceId = pmPaymentPreferenceId;
    }
    private java.lang.String pmStatusId;

    public java.lang.String getpmStatusId() {
        return pmStatusId;
    }

    public void setpmStatusId(java.lang.String pmStatusId) {
        this.pmStatusId = pmStatusId;
    }
    private java.lang.String invoiceId;

    public java.lang.String getinvoiceId() {
        return invoiceId;
    }

    public void setinvoiceId(java.lang.String invoiceId) {
        this.invoiceId = invoiceId;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getbillingAccountId() {
        return billingAccountId;
    }

    public void setbillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String contactMechId;

    public java.lang.String getcontactMechId() {
        return contactMechId;
    }

    public void setcontactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.lang.String pmActualCurrencyAmount;

    public java.lang.String getpmActualCurrencyAmount() {
        return pmActualCurrencyAmount;
    }

    public void setpmActualCurrencyAmount(java.lang.String pmActualCurrencyAmount) {
        this.pmActualCurrencyAmount = pmActualCurrencyAmount;
    }
    private java.lang.String pmComments;

    public java.lang.String getpmComments() {
        return pmComments;
    }

    public void setpmComments(java.lang.String pmComments) {
        this.pmComments = pmComments;
    }
    private java.lang.String pmPaymentMethodTypeId;

    public java.lang.String getpmPaymentMethodTypeId() {
        return pmPaymentMethodTypeId;
    }

    public void setpmPaymentMethodTypeId(java.lang.String pmPaymentMethodTypeId) {
        this.pmPaymentMethodTypeId = pmPaymentMethodTypeId;
    }
    private java.lang.String paymentApplicationId;

    public java.lang.String getpaymentApplicationId() {
        return paymentApplicationId;
    }

    public void setpaymentApplicationId(java.lang.String paymentApplicationId) {
        this.paymentApplicationId = paymentApplicationId;
    }
    private java.lang.String pmPartyIdFrom;

    public java.lang.String getpmPartyIdFrom() {
        return pmPartyIdFrom;
    }

    public void setpmPartyIdFrom(java.lang.String pmPartyIdFrom) {
        this.pmPartyIdFrom = pmPartyIdFrom;
    }
    private java.lang.String taxAuthGeoId;

    public java.lang.String gettaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void settaxAuthGeoId(java.lang.String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }
    private java.lang.String roleTypeId;

    public java.lang.String getroleTypeId() {
        return roleTypeId;
    }

    public void setroleTypeId(java.lang.String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    private java.lang.String pmPartyIdTo;

    public java.lang.String getpmPartyIdTo() {
        return pmPartyIdTo;
    }

    public void setpmPartyIdTo(java.lang.String pmPartyIdTo) {
        this.pmPartyIdTo = pmPartyIdTo;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String pmCurrencyUomId;

    public java.lang.String getpmCurrencyUomId() {
        return pmCurrencyUomId;
    }

    public void setpmCurrencyUomId(java.lang.String pmCurrencyUomId) {
        this.pmCurrencyUomId = pmCurrencyUomId;
    }
    private java.lang.String pmPaymentRefNum;

    public java.lang.String getpmPaymentRefNum() {
        return pmPaymentRefNum;
    }

    public void setpmPaymentRefNum(java.lang.String pmPaymentRefNum) {
        this.pmPaymentRefNum = pmPaymentRefNum;
    }
    private java.sql.Timestamp paidDate;

    public java.sql.Timestamp getpaidDate() {
        return paidDate;
    }

    public void setpaidDate(java.sql.Timestamp paidDate) {
        this.paidDate = paidDate;
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
        Collection<InvoiceAndApplAndPayment> objectList = new ArrayList<InvoiceAndApplAndPayment>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceAndApplAndPayment(genVal));
        }
        return objectList;
    }
}
