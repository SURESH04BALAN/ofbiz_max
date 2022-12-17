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

public class PaymentGroupMember implements GenericValueObjectInterface {

    public static final String module = PaymentGroupMember.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PaymentGroupMember(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PaymentGroupMember() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"paymentGroupId", "Payment Group Id"},
        {"fromDate", "From Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"sequenceNum", "Sequence Num"},
        {"paymentId", "Payment Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"thruDate", "Thru Date"},};

    protected void initObject() {
        this.paymentGroupId = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.sequenceNum = "";
        this.paymentId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.thruDate = null;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.paymentGroupId = (java.lang.String) genVal.get("paymentGroupId");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
        this.paymentId = (java.lang.String) genVal.get("paymentId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("paymentGroupId", OrderMaxUtility.getValidEntityString(this.paymentGroupId));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("sequenceNum", OrderMaxUtility.getValidEntityString(this.sequenceNum));
        val.set("paymentId", OrderMaxUtility.getValidEntityString(this.paymentId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("paymentGroupId", this.paymentGroupId);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("paymentId", this.paymentId);
        valueMap.put("thruDate", this.thruDate);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PaymentGroupMember");
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
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String sequenceNum;

    public java.lang.String getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.lang.String sequenceNum) {
        this.sequenceNum = sequenceNum;
    }
    private java.lang.String paymentId;

    public java.lang.String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(java.lang.String paymentId) {
        this.paymentId = paymentId;
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
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentGroupMember> objectList = new ArrayList<PaymentGroupMember>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentGroupMember(genVal));
        }
        return objectList;
    }
}
