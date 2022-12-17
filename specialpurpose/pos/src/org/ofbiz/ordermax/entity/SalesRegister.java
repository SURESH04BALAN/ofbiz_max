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

public class SalesRegister implements GenericValueObjectInterface {

    public static final String module = SalesRegister.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public SalesRegister(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public SalesRegister() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"amount", "Amount"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"trxTypeId", "Trx Type Id"},
        {"balanceamount", "Balanceamount"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"partyId", "Party Id"},
        {"trxId", "Trx Id"},
        {"trxDate", "Trx Date"},
        {"relatedId", "Related Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = null;
        this.amount = java.math.BigDecimal.ZERO;
        this.createdTxStamp = null;
        this.trxTypeId = "";
        this.balanceamount = java.math.BigDecimal.ZERO;
        this.createdStamp = null;
        this.lastUpdatedTxStamp = null;
        this.partyId = "";
        this.trxId = "";
        this.trxDate = UtilDateTime.nowTimestamp();
        this.relatedId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.amount = (java.math.BigDecimal) genVal.get("amount");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.trxTypeId = (java.lang.String) genVal.get("trxTypeId");
        this.balanceamount = (java.math.BigDecimal) genVal.get("balanceamount");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.trxId = (java.lang.String) genVal.get("trxId");
        this.trxDate = (java.sql.Timestamp) genVal.get("trxDate");
        this.relatedId = (java.lang.String) genVal.get("relatedId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("amount", OrderMaxUtility.getValidEntityBigDecimal(this.amount));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("trxTypeId", OrderMaxUtility.getValidEntityString(this.trxTypeId));
        val.set("balanceamount", OrderMaxUtility.getValidEntityBigDecimal(this.balanceamount));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("trxId", OrderMaxUtility.getValidEntityString(this.trxId));
        val.set("trxDate", OrderMaxUtility.getValidEntityTimestamp(this.trxDate));
        val.set("relatedId", OrderMaxUtility.getValidEntityString(this.relatedId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("amount", this.amount);
        valueMap.put("trxTypeId", this.trxTypeId);
        valueMap.put("balanceamount", this.balanceamount);
        valueMap.put("partyId", this.partyId);
        valueMap.put("trxId", this.trxId);
        valueMap.put("trxDate", this.trxDate);
        valueMap.put("relatedId", this.relatedId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("SalesRegister");
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
    private java.math.BigDecimal amount;

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String trxTypeId;

    public java.lang.String getTrxTypeId() {
        return trxTypeId;
    }

    public void setTrxTypeId(java.lang.String trxTypeId) {
        this.trxTypeId = trxTypeId;
    }
    private java.math.BigDecimal balanceamount;

    public java.math.BigDecimal getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(java.math.BigDecimal balanceamount) {
        this.balanceamount = balanceamount;
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
    private java.lang.String partyId;

    public java.lang.String getPartyId() {
        return partyId;
    }

    public void setPartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String trxId;

    public java.lang.String getTrxId() {
        return trxId;
    }

    public void setTrxId(java.lang.String trxId) {
        this.trxId = trxId;
    }
    private java.sql.Timestamp trxDate;

    public java.sql.Timestamp getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(java.sql.Timestamp trxDate) {
        this.trxDate = trxDate;
    }
    private java.lang.String relatedId;

    public java.lang.String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(java.lang.String relatedId) {
        this.relatedId = relatedId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SalesRegister> objectList = new ArrayList<SalesRegister>();
        for (GenericValue genVal : genList) {
            objectList.add(new SalesRegister(genVal));
        }
        return objectList;
    }
}
