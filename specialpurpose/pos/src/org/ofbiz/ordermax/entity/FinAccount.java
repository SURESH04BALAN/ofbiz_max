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

public class FinAccount implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = FinAccount.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public FinAccount(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public FinAccount() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"organizationPartyId", "Organization Party Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"thruDate", "Thru Date"},
        {"isRefundable", "Is Refundable"},
        {"postToGlAccountId", "Post To Gl Account Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"replenishPaymentId", "Replenish Payment Id"},
        {"finAccountTypeId", "Fin Account Type Id"},
        {"finAccountId", "Fin Account Id"},
        {"availableBalance", "Available Balance"},
        {"statusId", "Status Id"},
        {"replenishLevel", "Replenish Level"},
        {"fromDate", "From Date"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"finAccountPin", "Fin Account Pin"},
        {"actualBalance", "Actual Balance"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"ownerPartyId", "Owner Party Id"},
        {"finAccountCode", "Fin Account Code"},
        {"finAccountName", "Fin Account Name"},};

    protected void initObject() {
        this.organizationPartyId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.thruDate = UtilDateTime.nowTimestamp();
        this.isRefundable = "";
        this.postToGlAccountId = "";
        this.currencyUomId = "";
        this.replenishPaymentId = "";
        this.finAccountTypeId = "";
        this.finAccountId = "";
        this.availableBalance = java.math.BigDecimal.ZERO;
        this.statusId = "";
        this.replenishLevel = java.math.BigDecimal.ZERO;
        this.fromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.finAccountPin = "";
        this.actualBalance = java.math.BigDecimal.ZERO;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.ownerPartyId = "";
        this.finAccountCode = "";
        this.finAccountName = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.isRefundable = (java.lang.String) genVal.get("isRefundable");
        this.postToGlAccountId = (java.lang.String) genVal.get("postToGlAccountId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.replenishPaymentId = (java.lang.String) genVal.get("replenishPaymentId");
        this.finAccountTypeId = (java.lang.String) genVal.get("finAccountTypeId");
        this.finAccountId = (java.lang.String) genVal.get("finAccountId");
        this.availableBalance = (java.math.BigDecimal) genVal.get("availableBalance");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.replenishLevel = (java.math.BigDecimal) genVal.get("replenishLevel");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.finAccountPin = (java.lang.String) genVal.get("finAccountPin");
        this.actualBalance = (java.math.BigDecimal) genVal.get("actualBalance");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
        this.finAccountCode = (java.lang.String) genVal.get("finAccountCode");
        this.finAccountName = (java.lang.String) genVal.get("finAccountName");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("organizationPartyId", OrderMaxUtility.getValidEntityString(this.organizationPartyId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("isRefundable", OrderMaxUtility.getValidEntityString(this.isRefundable));
        val.set("postToGlAccountId", OrderMaxUtility.getValidEntityString(this.postToGlAccountId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("replenishPaymentId", OrderMaxUtility.getValidEntityString(this.replenishPaymentId));
        val.set("finAccountTypeId", OrderMaxUtility.getValidEntityString(this.finAccountTypeId));
        val.set("finAccountId", OrderMaxUtility.getValidEntityString(this.finAccountId));
        val.set("availableBalance", OrderMaxUtility.getValidEntityBigDecimal(this.availableBalance));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("replenishLevel", OrderMaxUtility.getValidEntityBigDecimal(this.replenishLevel));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("finAccountPin", OrderMaxUtility.getValidEntityString(this.finAccountPin));
        val.set("actualBalance", OrderMaxUtility.getValidEntityBigDecimal(this.actualBalance));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("ownerPartyId", OrderMaxUtility.getValidEntityString(this.ownerPartyId));
        val.set("finAccountCode", OrderMaxUtility.getValidEntityString(this.finAccountCode));
        val.set("finAccountName", OrderMaxUtility.getValidEntityString(this.finAccountName));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("organizationPartyId", this.organizationPartyId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("isRefundable", this.isRefundable);
        valueMap.put("postToGlAccountId", this.postToGlAccountId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("replenishPaymentId", this.replenishPaymentId);
        valueMap.put("finAccountTypeId", this.finAccountTypeId);
        valueMap.put("finAccountId", this.finAccountId);
        valueMap.put("availableBalance", this.availableBalance);
        valueMap.put("statusId", this.statusId);
        valueMap.put("replenishLevel", this.replenishLevel);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("finAccountPin", this.finAccountPin);
        valueMap.put("actualBalance", this.actualBalance);
        valueMap.put("ownerPartyId", this.ownerPartyId);
        valueMap.put("finAccountCode", this.finAccountCode);
        valueMap.put("finAccountName", this.finAccountName);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("FinAccount");
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

    public void setorganizationPartyId(java.lang.String organizationPartyId) {
        this.organizationPartyId = organizationPartyId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.lang.String isRefundable;

    public java.lang.String getisRefundable() {
        return isRefundable;
    }

    public void setisRefundable(java.lang.String isRefundable) {
        this.isRefundable = isRefundable;
    }
    private java.lang.String postToGlAccountId;

    public java.lang.String getpostToGlAccountId() {
        return postToGlAccountId;
    }

    public void setpostToGlAccountId(java.lang.String postToGlAccountId) {
        this.postToGlAccountId = postToGlAccountId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String replenishPaymentId;

    public java.lang.String getreplenishPaymentId() {
        return replenishPaymentId;
    }

    public void setreplenishPaymentId(java.lang.String replenishPaymentId) {
        this.replenishPaymentId = replenishPaymentId;
    }
    private java.lang.String finAccountTypeId;

    public java.lang.String getfinAccountTypeId() {
        return finAccountTypeId;
    }

    public void setfinAccountTypeId(java.lang.String finAccountTypeId) {
        this.finAccountTypeId = finAccountTypeId;
    }
    private java.lang.String finAccountId;

    public java.lang.String getfinAccountId() {
        return finAccountId;
    }

    public void setfinAccountId(java.lang.String finAccountId) {
        this.finAccountId = finAccountId;
    }
    private java.math.BigDecimal availableBalance;

    public java.math.BigDecimal getavailableBalance() {
        return availableBalance;
    }

    public void setavailableBalance(java.math.BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.math.BigDecimal replenishLevel;

    public java.math.BigDecimal getreplenishLevel() {
        return replenishLevel;
    }

    public void setreplenishLevel(java.math.BigDecimal replenishLevel) {
        this.replenishLevel = replenishLevel;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
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
    private java.lang.String finAccountPin;

    public java.lang.String getfinAccountPin() {
        return finAccountPin;
    }

    public void setfinAccountPin(java.lang.String finAccountPin) {
        this.finAccountPin = finAccountPin;
    }
    private java.math.BigDecimal actualBalance;

    public java.math.BigDecimal getactualBalance() {
        return actualBalance;
    }

    public void setactualBalance(java.math.BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String ownerPartyId;

    public java.lang.String getownerPartyId() {
        return ownerPartyId;
    }

    public void setownerPartyId(java.lang.String ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }
    private java.lang.String finAccountCode;

    public java.lang.String getfinAccountCode() {
        return finAccountCode;
    }

    public void setfinAccountCode(java.lang.String finAccountCode) {
        this.finAccountCode = finAccountCode;
    }
    private java.lang.String finAccountName;

    public java.lang.String getfinAccountName() {
        return finAccountName;
    }

    public void setfinAccountName(java.lang.String finAccountName) {
        this.finAccountName = finAccountName;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<FinAccount> objectList = new ArrayList<FinAccount>();
        for (GenericValue genVal : genList) {
            objectList.add(new FinAccount(genVal));
        }
        return objectList;
    }
    
    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(finAccountName);

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(finAccountCode);
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(finAccountCode);
        }

        return orderToStringBuilder.toString();
    }

}
