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

public class PosTerminalInternTx implements GenericValueObjectInterface {

    public static final String module = PosTerminalInternTx.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PosTerminalInternTx(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public PosTerminalInternTx() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        //{"lastUpdatedStamp","Last Updated Stamp"},
        //{"createdTxStamp","Created Tx Stamp"},
        //{"createdStamp","Created Stamp"},
        //{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
        {"posTerminalLogId", "Pos Terminal Log Id"},
        {"paidAmount", "Paid Amount"},
        {"reasonComment", "Reason Comment"},
        {"reasonEnumId", "Reason Enum Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = "";
        this.createdTxStamp = "";
        this.createdStamp = "";
        this.lastUpdatedTxStamp = "";
        this.posTerminalLogId = "";
        this.reasonComment = "";
        this.reasonEnumId = "";
        this.paidAmount = java.math.BigDecimal.ZERO;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.lang.String) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.lang.String) genVal.get("createdTxStamp");
        this.createdStamp = (java.lang.String) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.lang.String) genVal.get("lastUpdatedTxStamp");
        this.posTerminalLogId = (java.lang.String) genVal.get("posTerminalLogId");
        this.reasonComment = (java.lang.String) genVal.get("reasonComment");
        this.reasonEnumId = (java.lang.String) genVal.get("reasonEnumId");
        this.paidAmount = (java.math.BigDecimal) genVal.get("paidAmount");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityString(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityString(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityString(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityString(this.lastUpdatedTxStamp));
        val.set("posTerminalLogId", OrderMaxUtility.getValidEntityString(this.posTerminalLogId));
        val.set("reasonComment", OrderMaxUtility.getValidEntityString(this.reasonComment));
        val.set("reasonEnumId", OrderMaxUtility.getValidEntityString(this.reasonEnumId));
        val.set("paidAmount", OrderMaxUtility.getValidEntityBigDecimal(this.paidAmount));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("posTerminalLogId", this.posTerminalLogId);
        valueMap.put("reasonComment", this.reasonComment);
        valueMap.put("reasonEnumId", this.reasonEnumId);
        valueMap.put("paidAmount", this.paidAmount);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PosTerminalInternTx");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String lastUpdatedStamp;

    public java.lang.String getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.lang.String lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String createdTxStamp;

    public java.lang.String getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.lang.String createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String createdStamp;

    public java.lang.String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.lang.String createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String lastUpdatedTxStamp;

    public java.lang.String getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.lang.String lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String posTerminalLogId;

    public java.lang.String getPosTerminalLogId() {
        return posTerminalLogId;
    }

    public void setPosTerminalLogId(java.lang.String posTerminalLogId) {
        this.posTerminalLogId = posTerminalLogId;
    }
    private java.lang.String reasonComment;

    public java.lang.String getReasonComment() {
        return reasonComment;
    }

    public void setReasonComment(java.lang.String reasonComment) {
        this.reasonComment = reasonComment;
    }
    private java.lang.String reasonEnumId;

    public java.lang.String getReasonEnumId() {
        return reasonEnumId;
    }

    public void setReasonEnumId(java.lang.String reasonEnumId) {
        this.reasonEnumId = reasonEnumId;
    }
    private java.math.BigDecimal paidAmount;

    public java.math.BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(java.math.BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PosTerminalInternTx> objectList = new ArrayList<PosTerminalInternTx>();
        for (GenericValue genVal : genList) {
            objectList.add(new PosTerminalInternTx(genVal));
        }
        return objectList;
    }
}
