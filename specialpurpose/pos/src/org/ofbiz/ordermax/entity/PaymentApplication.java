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

public class PaymentApplication implements GenericValueObjectInterface {

    public static final String module = PaymentApplication.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PaymentApplication(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
        }
    }

    public PaymentApplication() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"toPaymentId", "To Payment Id"},
        {"amountApplied", "Amount Applied"},
        {"billingAccountId", "Billing Account Id"},
        {"invoiceId", "Invoice Id"},
        {"paymentId", "Payment Id"},
        {"invoiceItemSeqId", "Invoice Item Seq Id"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"paymentApplicationId", "Payment Application Id"},
        {"taxAuthGeoId", "Tax Auth Geo Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.toPaymentId = "";
        this.amountApplied = java.math.BigDecimal.ZERO;
        this.billingAccountId = "";
        this.invoiceId = "";
        this.paymentId = "";
        this.invoiceItemSeqId = "";
        this.overrideGlAccountId = "";
        this.paymentApplicationId = "";
        this.taxAuthGeoId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.toPaymentId = (java.lang.String) genVal.get("toPaymentId");
        this.amountApplied = (java.math.BigDecimal) genVal.get("amountApplied");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.invoiceId = (java.lang.String) genVal.get("invoiceId");
        this.paymentId = (java.lang.String) genVal.get("paymentId");
        this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.paymentApplicationId = (java.lang.String) genVal.get("paymentApplicationId");
        this.taxAuthGeoId = (java.lang.String) genVal.get("taxAuthGeoId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("toPaymentId", OrderMaxUtility.getValidEntityString(this.toPaymentId));
        val.set("amountApplied", OrderMaxUtility.getValidEntityBigDecimal(this.amountApplied));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("invoiceId", OrderMaxUtility.getValidEntityString(this.invoiceId));
        val.set("paymentId", OrderMaxUtility.getValidEntityString(this.paymentId));
        val.set("invoiceItemSeqId", OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("paymentApplicationId", OrderMaxUtility.getValidEntityString(this.paymentApplicationId));
        val.set("taxAuthGeoId", OrderMaxUtility.getValidEntityString(this.taxAuthGeoId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("toPaymentId", this.toPaymentId);
        valueMap.put("amountApplied", this.amountApplied);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("invoiceId", this.invoiceId);
        valueMap.put("paymentId", this.paymentId);
        valueMap.put("invoiceItemSeqId", this.invoiceItemSeqId);
        valueMap.put("overrideGlAccountId", this.overrideGlAccountId);
        valueMap.put("paymentApplicationId", this.paymentApplicationId);
        valueMap.put("taxAuthGeoId", this.taxAuthGeoId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PaymentApplication");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String toPaymentId;

    public java.lang.String gettoPaymentId() {
        return toPaymentId;
    }

    public void settoPaymentId(java.lang.String toPaymentId) {
        this.toPaymentId = toPaymentId;
    }
    private java.math.BigDecimal amountApplied;

    public java.math.BigDecimal getamountApplied() {
        return amountApplied;
    }

    public void setamountApplied(java.math.BigDecimal amountApplied) {
        this.amountApplied = amountApplied;
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
    private java.lang.String paymentId;

    public java.lang.String getpaymentId() {
        return paymentId;
    }

    public void setpaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
    }
    private java.lang.String invoiceItemSeqId;

    public java.lang.String getinvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setinvoiceItemSeqId(java.lang.String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getoverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setoverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String paymentApplicationId;

    public java.lang.String getpaymentApplicationId() {
        return paymentApplicationId;
    }

    public void setpaymentApplicationId(java.lang.String paymentApplicationId) {
        this.paymentApplicationId = paymentApplicationId;
    }
    private java.lang.String taxAuthGeoId;

    public java.lang.String gettaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void settaxAuthGeoId(java.lang.String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentApplication> objectList = new ArrayList<PaymentApplication>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentApplication(genVal));
        }
        return objectList;
    }
}
