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

public class PaymentAndTypePartyNameView implements GenericValueObjectInterface {

    public static final String module = PaymentAndTypePartyNameView.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PaymentAndTypePartyNameView(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public PaymentAndTypePartyNameView() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"partyIdFrom", "Party Id From"},
        {"paymentPreferenceId", "Payment Preference Id"},
        {"partyToGroupName", "Party To Group Name"},
        {"paymentTypeId", "Payment Type Id"},
        {"partyFromGroupName", "Party From Group Name"},
        {"paymentId", "Payment Id"},
        {"paymentMethodId", "Payment Method Id"},
        {"paymentGatewayResponseId", "Payment Gateway Response Id"},
        {"partyToFirstName", "Party To First Name"},
        {"paymentRefNum", "Payment Ref Num"},
        {"parentPaymentTypeId", "Parent Payment Type Id"},
        {"roleTypeIdTo", "Role Type Id To"},
        {"finAccountTransId", "Fin Account Trans Id"},
        {"amount", "Amount"},
        {"statusDesc", "Status Desc"},
        {"comments", "Comments"},
        {"actualCurrencyAmount", "Actual Currency Amount"},
        {"partyFromLastName", "Party From Last Name"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"partyFromFirstName", "Party From First Name"},
        {"paymentMethodTypeId", "Payment Method Type Id"},
        {"actualCurrencyUomId", "Actual Currency Uom Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"paymentMethodTypeDesc", "Payment Method Type Desc"},
        {"statusId", "Status Id"},
        {"paymentTypeDesc", "Payment Type Desc"},
        {"partyToLastName", "Party To Last Name"},
        {"partyIdTo", "Party Id To"},
        {"effectiveDate", "Effective Date"},};

    protected void initObject() {
        this.partyIdFrom = "";
        this.paymentPreferenceId = "";
        this.partyToGroupName = "";
        this.paymentTypeId = "";
        this.partyFromGroupName = "";
        this.paymentId = "";
        this.paymentMethodId = "";
        this.paymentGatewayResponseId = "";
        this.partyToFirstName = "";
        this.paymentRefNum = "";
        this.parentPaymentTypeId = "";
        this.roleTypeIdTo = "";
        this.finAccountTransId = "";
        this.amount = java.math.BigDecimal.ZERO;
        this.statusDesc = "";
        this.comments = "";
        this.actualCurrencyAmount = "";
        this.partyFromLastName = "";
        this.overrideGlAccountId = "";
        this.partyFromFirstName = "";
        this.paymentMethodTypeId = "";
        this.actualCurrencyUomId = "";
        this.currencyUomId = "";
        this.paymentMethodTypeDesc = "";
        this.statusId = "";
        this.paymentTypeDesc = "";
        this.partyToLastName = "";
        this.partyIdTo = "";
        this.effectiveDate = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.paymentPreferenceId = (java.lang.String) genVal.get("paymentPreferenceId");
        this.partyToGroupName = (java.lang.String) genVal.get("partyToGroupName");
        this.paymentTypeId = (java.lang.String) genVal.get("paymentTypeId");
        this.partyFromGroupName = (java.lang.String) genVal.get("partyFromGroupName");
        this.paymentId = (java.lang.String) genVal.get("paymentId");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.paymentGatewayResponseId = (java.lang.String) genVal.get("paymentGatewayResponseId");
        this.partyToFirstName = (java.lang.String) genVal.get("partyToFirstName");
        this.paymentRefNum = (java.lang.String) genVal.get("paymentRefNum");
        this.parentPaymentTypeId = (java.lang.String) genVal.get("parentPaymentTypeId");
        this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
        this.finAccountTransId = (java.lang.String) genVal.get("finAccountTransId");
        this.amount = (java.math.BigDecimal) genVal.get("amount");
        this.statusDesc = (java.lang.String) genVal.get("statusDesc");
        this.comments = (java.lang.String) genVal.get("comments");
        this.actualCurrencyAmount = (java.lang.String) genVal.get("actualCurrencyAmount");
        this.partyFromLastName = (java.lang.String) genVal.get("partyFromLastName");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.partyFromFirstName = (java.lang.String) genVal.get("partyFromFirstName");
        this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
        this.actualCurrencyUomId = (java.lang.String) genVal.get("actualCurrencyUomId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.paymentMethodTypeDesc = (java.lang.String) genVal.get("paymentMethodTypeDesc");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.paymentTypeDesc = (java.lang.String) genVal.get("paymentTypeDesc");
        this.partyToLastName = (java.lang.String) genVal.get("partyToLastName");
        this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
        this.effectiveDate = (java.sql.Timestamp) genVal.get("effectiveDate");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("paymentPreferenceId", OrderMaxUtility.getValidEntityString(this.paymentPreferenceId));
        val.set("partyToGroupName", OrderMaxUtility.getValidEntityString(this.partyToGroupName));
        val.set("paymentTypeId", OrderMaxUtility.getValidEntityString(this.paymentTypeId));
        val.set("partyFromGroupName", OrderMaxUtility.getValidEntityString(this.partyFromGroupName));
        val.set("paymentId", OrderMaxUtility.getValidEntityString(this.paymentId));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("paymentGatewayResponseId", OrderMaxUtility.getValidEntityString(this.paymentGatewayResponseId));
        val.set("partyToFirstName", OrderMaxUtility.getValidEntityString(this.partyToFirstName));
        val.set("paymentRefNum", OrderMaxUtility.getValidEntityString(this.paymentRefNum));
        val.set("parentPaymentTypeId", OrderMaxUtility.getValidEntityString(this.parentPaymentTypeId));
        val.set("roleTypeIdTo", OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
        val.set("finAccountTransId", OrderMaxUtility.getValidEntityString(this.finAccountTransId));
        val.set("amount", OrderMaxUtility.getValidEntityBigDecimal(this.amount));
        val.set("statusDesc", OrderMaxUtility.getValidEntityString(this.statusDesc));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
        val.set("actualCurrencyAmount", OrderMaxUtility.getValidEntityString(this.actualCurrencyAmount));
        val.set("partyFromLastName", OrderMaxUtility.getValidEntityString(this.partyFromLastName));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("partyFromFirstName", OrderMaxUtility.getValidEntityString(this.partyFromFirstName));
        val.set("paymentMethodTypeId", OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
        val.set("actualCurrencyUomId", OrderMaxUtility.getValidEntityString(this.actualCurrencyUomId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("paymentMethodTypeDesc", OrderMaxUtility.getValidEntityString(this.paymentMethodTypeDesc));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("paymentTypeDesc", OrderMaxUtility.getValidEntityString(this.paymentTypeDesc));
        val.set("partyToLastName", OrderMaxUtility.getValidEntityString(this.partyToLastName));
        val.set("partyIdTo", OrderMaxUtility.getValidEntityString(this.partyIdTo));
        val.set("effectiveDate", OrderMaxUtility.getValidEntityTimestamp(this.effectiveDate));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("paymentPreferenceId", this.paymentPreferenceId);
        valueMap.put("partyToGroupName", this.partyToGroupName);
        valueMap.put("paymentTypeId", this.paymentTypeId);
        valueMap.put("partyFromGroupName", this.partyFromGroupName);
        valueMap.put("paymentId", this.paymentId);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("paymentGatewayResponseId", this.paymentGatewayResponseId);
        valueMap.put("partyToFirstName", this.partyToFirstName);
        valueMap.put("paymentRefNum", this.paymentRefNum);
        valueMap.put("parentPaymentTypeId", this.parentPaymentTypeId);
        valueMap.put("roleTypeIdTo", this.roleTypeIdTo);
        valueMap.put("finAccountTransId", this.finAccountTransId);
        valueMap.put("amount", this.amount);
        valueMap.put("statusDesc", this.statusDesc);
        valueMap.put("comments", this.comments);
        valueMap.put("actualCurrencyAmount", this.actualCurrencyAmount);
        valueMap.put("partyFromLastName", this.partyFromLastName);
        valueMap.put("overrideGlAccountId", this.overrideGlAccountId);
        valueMap.put("partyFromFirstName", this.partyFromFirstName);
        valueMap.put("paymentMethodTypeId", this.paymentMethodTypeId);
        valueMap.put("actualCurrencyUomId", this.actualCurrencyUomId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("paymentMethodTypeDesc", this.paymentMethodTypeDesc);
        valueMap.put("statusId", this.statusId);
        valueMap.put("paymentTypeDesc", this.paymentTypeDesc);
        valueMap.put("partyToLastName", this.partyToLastName);
        valueMap.put("partyIdTo", this.partyIdTo);
        valueMap.put("effectiveDate", this.effectiveDate);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PaymentAndTypePartyNameView");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getPartyIdFrom() {
        return partyIdFrom;
    }

    public void setPartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    private java.lang.String paymentPreferenceId;

    public java.lang.String getPaymentPreferenceId() {
        return paymentPreferenceId;
    }

    public void setPaymentPreferenceId(java.lang.String paymentPreferenceId) {
        this.paymentPreferenceId = paymentPreferenceId;
    }
    private java.lang.String partyToGroupName;

    public java.lang.String getPartyToGroupName() {
        return partyToGroupName;
    }

    public void setPartyToGroupName(java.lang.String partyToGroupName) {
        this.partyToGroupName = partyToGroupName;
    }
    private java.lang.String paymentTypeId;

    public java.lang.String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(java.lang.String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    private java.lang.String partyFromGroupName;

    public java.lang.String getPartyFromGroupName() {
        return partyFromGroupName;
    }

    public void setPartyFromGroupName(java.lang.String partyFromGroupName) {
        this.partyFromGroupName = partyFromGroupName;
    }
    private java.lang.String paymentId;

    public java.lang.String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    private java.lang.String paymentGatewayResponseId;

    public java.lang.String getPaymentGatewayResponseId() {
        return paymentGatewayResponseId;
    }

    public void setPaymentGatewayResponseId(java.lang.String paymentGatewayResponseId) {
        this.paymentGatewayResponseId = paymentGatewayResponseId;
    }
    private java.lang.String partyToFirstName;

    public java.lang.String getPartyToFirstName() {
        return partyToFirstName;
    }

    public void setPartyToFirstName(java.lang.String partyToFirstName) {
        this.partyToFirstName = partyToFirstName;
    }
    private java.lang.String paymentRefNum;

    public java.lang.String getPaymentRefNum() {
        return paymentRefNum;
    }

    public void setPaymentRefNum(java.lang.String paymentRefNum) {
        this.paymentRefNum = paymentRefNum;
    }
    private java.lang.String parentPaymentTypeId;

    public java.lang.String getParentPaymentTypeId() {
        return parentPaymentTypeId;
    }

    public void setParentPaymentTypeId(java.lang.String parentPaymentTypeId) {
        this.parentPaymentTypeId = parentPaymentTypeId;
    }
    private java.lang.String roleTypeIdTo;

    public java.lang.String getRoleTypeIdTo() {
        return roleTypeIdTo;
    }

    public void setRoleTypeIdTo(java.lang.String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }
    private java.lang.String finAccountTransId;

    public java.lang.String getFinAccountTransId() {
        return finAccountTransId;
    }

    public void setFinAccountTransId(java.lang.String finAccountTransId) {
        this.finAccountTransId = finAccountTransId;
    }
    private java.math.BigDecimal amount;

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    private java.lang.String statusDesc;

    public java.lang.String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }
    private java.lang.String comments;

    public java.lang.String getComments() {
        return comments;
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }
    private java.lang.String actualCurrencyAmount;

    public java.lang.String getActualCurrencyAmount() {
        return actualCurrencyAmount;
    }

    public void setActualCurrencyAmount(java.lang.String actualCurrencyAmount) {
        this.actualCurrencyAmount = actualCurrencyAmount;
    }
    private java.lang.String partyFromLastName;

    public java.lang.String getPartyFromLastName() {
        return partyFromLastName;
    }

    public void setPartyFromLastName(java.lang.String partyFromLastName) {
        this.partyFromLastName = partyFromLastName;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getOverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setOverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String partyFromFirstName;

    public java.lang.String getPartyFromFirstName() {
        return partyFromFirstName;
    }

    public void setPartyFromFirstName(java.lang.String partyFromFirstName) {
        this.partyFromFirstName = partyFromFirstName;
    }
    private java.lang.String paymentMethodTypeId;

    public java.lang.String getPaymentMethodTypeId() {
        return paymentMethodTypeId;
    }

    public void setPaymentMethodTypeId(java.lang.String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }
    private java.lang.String actualCurrencyUomId;

    public java.lang.String getActualCurrencyUomId() {
        return actualCurrencyUomId;
    }

    public void setActualCurrencyUomId(java.lang.String actualCurrencyUomId) {
        this.actualCurrencyUomId = actualCurrencyUomId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String paymentMethodTypeDesc;

    public java.lang.String getPaymentMethodTypeDesc() {
        return paymentMethodTypeDesc;
    }

    public void setPaymentMethodTypeDesc(java.lang.String paymentMethodTypeDesc) {
        this.paymentMethodTypeDesc = paymentMethodTypeDesc;
    }
    private java.lang.String statusId;

    public java.lang.String getStatusId() {
        return statusId;
    }

    public void setStatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String paymentTypeDesc;

    public java.lang.String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(java.lang.String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }
    private java.lang.String partyToLastName;

    public java.lang.String getPartyToLastName() {
        return partyToLastName;
    }

    public void setPartyToLastName(java.lang.String partyToLastName) {
        this.partyToLastName = partyToLastName;
    }
    private java.lang.String partyIdTo;

    public java.lang.String getPartyIdTo() {
        return partyIdTo;
    }

    public void setPartyIdTo(java.lang.String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    private java.sql.Timestamp effectiveDate;

    public java.sql.Timestamp getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(java.sql.Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentAndTypePartyNameView> objectList = new ArrayList<PaymentAndTypePartyNameView>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentAndTypePartyNameView(genVal));
        }
        return objectList;
    }
}
