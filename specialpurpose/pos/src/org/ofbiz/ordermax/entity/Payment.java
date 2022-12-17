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

public class Payment implements GenericValueObjectInterface {

    public static final String module = Payment.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Payment(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Payment() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"actualCurrencyUomId", "Actual Currency Uom Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"partyIdTo", "Party Id To"},
        {"paymentPreferenceId", "Payment Preference Id"},
        {"paymentTypeId", "Payment Type Id"},
        {"paymentId", "Payment Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"paymentMethodTypeId", "Payment Method Type Id"},
        {"partyIdFrom", "Party Id From"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"statusId", "Status Id"},
        {"amount", "Amount"},
        {"actualCurrencyAmount", "Actual Currency Amount"},
        {"roleTypeIdTo", "Role Type Id To"},
        {"paymentGatewayResponseId", "Payment Gateway Response Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"paymentRefNum", "Payment Ref Num"},
        {"finAccountTransId", "Fin Account Trans Id"},
        {"paymentMethodId", "Payment Method Id"},
        {"effectiveDate", "Effective Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"comments", "Comments"},};

    protected void initObject() {
        this.actualCurrencyUomId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.partyIdTo = "";
        this.paymentPreferenceId = "";
        this.paymentTypeId = "";
        this.paymentId = "";
        this.currencyUomId = "";
        this.paymentMethodTypeId = "";
        this.partyIdFrom = "";
        this.overrideGlAccountId = "";
        this.statusId = "";
        this.amount = java.math.BigDecimal.ZERO;
        this.actualCurrencyAmount = java.math.BigDecimal.ZERO;
        this.roleTypeIdTo = "";
        this.paymentGatewayResponseId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.paymentRefNum = "";
        this.finAccountTransId = "";
        this.paymentMethodId = "";
        this.effectiveDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.comments = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.actualCurrencyUomId = (java.lang.String) genVal.get("actualCurrencyUomId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
        this.paymentPreferenceId = (java.lang.String) genVal.get("paymentPreferenceId");
        this.paymentTypeId = (java.lang.String) genVal.get("paymentTypeId");
        this.paymentId = (java.lang.String) genVal.get("paymentId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.amount = (java.math.BigDecimal) genVal.get("amount");
        this.actualCurrencyAmount = (java.math.BigDecimal) genVal.get("actualCurrencyAmount");
        this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
        this.paymentGatewayResponseId = (java.lang.String) genVal.get("paymentGatewayResponseId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.paymentRefNum = (java.lang.String) genVal.get("paymentRefNum");
        this.finAccountTransId = (java.lang.String) genVal.get("finAccountTransId");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.comments = (java.lang.String) genVal.get("comments");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("actualCurrencyUomId", OrderMaxUtility.getValidEntityString(this.actualCurrencyUomId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("partyIdTo", OrderMaxUtility.getValidEntityString(this.partyIdTo));
        val.set("paymentPreferenceId", OrderMaxUtility.getValidEntityString(this.paymentPreferenceId));
        val.set("paymentTypeId", OrderMaxUtility.getValidEntityString(this.paymentTypeId));
        val.set("paymentId", OrderMaxUtility.getValidEntityString(this.paymentId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("paymentMethodTypeId", OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("amount", OrderMaxUtility.getValidEntityBigDecimal(this.amount));
        val.set("actualCurrencyAmount", OrderMaxUtility.getValidEntityBigDecimal(this.actualCurrencyAmount));
        val.set("roleTypeIdTo", OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
        val.set("paymentGatewayResponseId", OrderMaxUtility.getValidEntityString(this.paymentGatewayResponseId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("paymentRefNum", OrderMaxUtility.getValidEntityString(this.paymentRefNum));
        val.set("finAccountTransId", OrderMaxUtility.getValidEntityString(this.finAccountTransId));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("effectiveDate", OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("actualCurrencyUomId", this.actualCurrencyUomId);
        valueMap.put("partyIdTo", this.partyIdTo);
        valueMap.put("paymentPreferenceId", this.paymentPreferenceId);
        valueMap.put("paymentTypeId", this.paymentTypeId);
        valueMap.put("paymentId", this.paymentId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("paymentMethodTypeId", this.paymentMethodTypeId);
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("overrideGlAccountId", this.overrideGlAccountId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("amount", this.amount);
        valueMap.put("actualCurrencyAmount", this.actualCurrencyAmount);
        valueMap.put("roleTypeIdTo", this.roleTypeIdTo);
        valueMap.put("paymentGatewayResponseId", this.paymentGatewayResponseId);
        valueMap.put("paymentRefNum", this.paymentRefNum);
        valueMap.put("finAccountTransId", this.finAccountTransId);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("effectiveDate", this.effectiveDate);
        valueMap.put("comments", this.comments);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Payment");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String actualCurrencyUomId;

    public java.lang.String getactualCurrencyUomId() {
        return actualCurrencyUomId;
    }

    public void setactualCurrencyUomId(java.lang.String actualCurrencyUomId) {
        this.actualCurrencyUomId = actualCurrencyUomId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String partyIdTo;

    public java.lang.String getpartyIdTo() {
        return partyIdTo;
    }

    public void setpartyIdTo(java.lang.String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    private java.lang.String paymentPreferenceId;

    public java.lang.String getpaymentPreferenceId() {
        return paymentPreferenceId;
    }

    public void setpaymentPreferenceId(java.lang.String paymentPreferenceId) {
        this.paymentPreferenceId = paymentPreferenceId;
    }
    private java.lang.String paymentTypeId;

    public java.lang.String getpaymentTypeId() {
        return paymentTypeId;
    }

    public void setpaymentTypeId(java.lang.String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    private java.lang.String paymentId;

    public java.lang.String getpaymentId() {
        return paymentId;
    }

    public void setpaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String paymentMethodTypeId;

    public java.lang.String getpaymentMethodTypeId() {
        return paymentMethodTypeId;
    }

    public void setpaymentMethodTypeId(java.lang.String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getpartyIdFrom() {
        return partyIdFrom;
    }

    public void setpartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
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
    private java.math.BigDecimal amount;

    public java.math.BigDecimal getamount() {
        return amount;
    }

    public void setamount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    private java.math.BigDecimal actualCurrencyAmount;

    public java.math.BigDecimal getactualCurrencyAmount() {
        return actualCurrencyAmount;
    }

    public void setactualCurrencyAmount(java.math.BigDecimal actualCurrencyAmount) {
        this.actualCurrencyAmount = actualCurrencyAmount;
    }
    private java.lang.String roleTypeIdTo;

    public java.lang.String getroleTypeIdTo() {
        return roleTypeIdTo;
    }

    public void setroleTypeIdTo(java.lang.String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }
    private java.lang.String paymentGatewayResponseId;

    public java.lang.String getpaymentGatewayResponseId() {
        return paymentGatewayResponseId;
    }

    public void setpaymentGatewayResponseId(java.lang.String paymentGatewayResponseId) {
        this.paymentGatewayResponseId = paymentGatewayResponseId;
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
    private java.lang.String paymentRefNum;

    public java.lang.String getpaymentRefNum() {
        return paymentRefNum;
    }

    public void setpaymentRefNum(java.lang.String paymentRefNum) {
        this.paymentRefNum = paymentRefNum;
    }
    private java.lang.String finAccountTransId;

    public java.lang.String getfinAccountTransId() {
        return finAccountTransId;
    }

    public void setfinAccountTransId(java.lang.String finAccountTransId) {
        this.finAccountTransId = finAccountTransId;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getpaymentMethodId() {
        return paymentMethodId;
    }

    public void setpaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    private java.sql.Timestamp effectiveDate;

    public java.sql.Timestamp geteffectiveDate() {
        return effectiveDate;
    }

    public void seteffectiveDate(java.sql.Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String comments;

    public java.lang.String getcomments() {
        return comments;
    }

    public void setcomments(java.lang.String comments) {
        this.comments = comments;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Payment> objectList = new ArrayList<Payment>();
        for (GenericValue genVal : genList) {
            objectList.add(new Payment(genVal));
        }
        return objectList;
    }
}
