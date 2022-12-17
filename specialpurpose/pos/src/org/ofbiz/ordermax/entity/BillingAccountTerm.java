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

public class BillingAccountTerm implements GenericValueObjectInterface {

    public static final String module = BillingAccountTerm.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public BillingAccountTerm(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public BillingAccountTerm() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"billingAccountTermId", "Billing Account Term Id"},
        {"termDays", "Term Days"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"termTypeId", "Term Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"billingAccountId", "Billing Account Id"},
        {"uomId", "Uom Id"},
        {"termValue", "Term Value"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.billingAccountTermId = "";
        this.termDays = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.termTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.billingAccountId = "";
        this.uomId = "";
        this.termValue = java.math.BigDecimal.ZERO;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.billingAccountTermId = (java.lang.String) genVal.get("billingAccountTermId");
        this.termDays = (java.lang.String) genVal.get("termDays");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.termTypeId = (java.lang.String) genVal.get("termTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.termValue = (java.math.BigDecimal) genVal.get("termValue");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("billingAccountTermId", OrderMaxUtility.getValidEntityString(this.billingAccountTermId));
        val.set("termDays", OrderMaxUtility.getValidEntityString(this.termDays));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("termTypeId", OrderMaxUtility.getValidEntityString(this.termTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("termValue", OrderMaxUtility.getValidEntityBigDecimal(this.termValue));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("billingAccountTermId", this.billingAccountTermId);
        valueMap.put("termDays", this.termDays);
        valueMap.put("termTypeId", this.termTypeId);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("uomId", this.uomId);
        valueMap.put("termValue", this.termValue);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("BillingAccountTerm");
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

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String billingAccountTermId;

    public java.lang.String getBillingAccountTermId() {
        return billingAccountTermId;
    }

    public void setBillingAccountTermId(java.lang.String billingAccountTermId) {
        this.billingAccountTermId = billingAccountTermId;
    }
    private java.lang.String termDays;

    public java.lang.String getTermDays() {
        return termDays;
    }

    public void setTermDays(java.lang.String termDays) {
        this.termDays = termDays;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String termTypeId;

    public java.lang.String getTermTypeId() {
        return termTypeId;
    }

    public void setTermTypeId(java.lang.String termTypeId) {
        this.termTypeId = termTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
    private java.lang.String uomId;

    public java.lang.String getUomId() {
        return uomId;
    }

    public void setUomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.math.BigDecimal termValue;

    public java.math.BigDecimal getTermValue() {
        return termValue;
    }

    public void setTermValue(java.math.BigDecimal termValue) {
        this.termValue = termValue;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<BillingAccountTerm> objectList = new ArrayList<BillingAccountTerm>();
        for (GenericValue genVal : genList) {
            objectList.add(new BillingAccountTerm(genVal));
        }
        return objectList;
    }
}
