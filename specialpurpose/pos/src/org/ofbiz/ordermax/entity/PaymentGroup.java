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

public class PaymentGroup implements GenericValueObjectInterface {

    public static final String module = PaymentGroup.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PaymentGroup(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PaymentGroup() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"paymentGroupId", "Payment Group Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"paymentGroupName", "Payment Group Name"},
        {"paymentGroupTypeId", "Payment Group Type Id"},};

    protected void initObject() {
        this.paymentGroupId = "";
        this.lastUpdatedStamp = null;
        this.createdTxStamp = null;
        this.createdStamp = null;
        this.lastUpdatedTxStamp = null;
        this.paymentGroupName = "";
        this.paymentGroupTypeId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.paymentGroupId = (java.lang.String) genVal.get("paymentGroupId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.paymentGroupName = (java.lang.String) genVal.get("paymentGroupName");
        this.paymentGroupTypeId = (java.lang.String) genVal.get("paymentGroupTypeId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("paymentGroupId", OrderMaxUtility.getValidEntityString(this.paymentGroupId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("paymentGroupName", OrderMaxUtility.getValidEntityString(this.paymentGroupName));
        val.set("paymentGroupTypeId", OrderMaxUtility.getValidEntityString(this.paymentGroupTypeId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("paymentGroupId", this.paymentGroupId);
        valueMap.put("paymentGroupName", this.paymentGroupName);
        valueMap.put("paymentGroupTypeId", this.paymentGroupTypeId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PaymentGroup");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String paymentGroupId;

    public java.lang.String getPaymentGroupId() {
        return paymentGroupId;
    }

    public void setPaymentGroupId(java.lang.String paymentGroupId) {
        this.paymentGroupId = paymentGroupId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
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
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String paymentGroupName;

    public java.lang.String getPaymentGroupName() {
        return paymentGroupName;
    }

    public void setPaymentGroupName(java.lang.String paymentGroupName) {
        this.paymentGroupName = paymentGroupName;
    }
    private java.lang.String paymentGroupTypeId;

    public java.lang.String getPaymentGroupTypeId() {
        return paymentGroupTypeId;
    }

    public void setPaymentGroupTypeId(java.lang.String paymentGroupTypeId) {
        this.paymentGroupTypeId = paymentGroupTypeId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGroup> objectList = new ArrayList<PaymentGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGroup(genVal));
        }
        return objectList;
    }
}
