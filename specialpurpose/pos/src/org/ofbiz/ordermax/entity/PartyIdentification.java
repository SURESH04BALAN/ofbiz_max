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

public class PartyIdentification implements GenericValueObjectInterface {

    public static final String module = PartyIdentification.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PartyIdentification(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PartyIdentification() {
        initObject();
                              
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"partyIdentificationTypeId", "Party Identification Type Id"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"idValue", "Id Value"},
        {"partyId", "Party Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.partyIdentificationTypeId = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.idValue = "";
        this.partyId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.partyIdentificationTypeId = (java.lang.String) genVal.get("partyIdentificationTypeId");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.idValue = (java.lang.String) genVal.get("idValue");
        this.partyId = (java.lang.String) genVal.get("partyId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidTimestamp(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidTimestamp(this.createdTxStamp));
        val.set("partyIdentificationTypeId", OrderMaxUtility.getValidEntityString(this.partyIdentificationTypeId));
        val.set("createdStamp", OrderMaxUtility.getValidTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidTimestamp(this.lastUpdatedTxStamp));
        val.set("idValue", OrderMaxUtility.getValidEntityString(this.idValue));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("partyIdentificationTypeId", this.partyIdentificationTypeId);
        valueMap.put("idValue", this.idValue);
        valueMap.put("partyId", this.partyId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PartyIdentification");
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
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String partyIdentificationTypeId;

    public java.lang.String getPartyIdentificationTypeId() {
        return partyIdentificationTypeId;
    }

    public void setPartyIdentificationTypeId(java.lang.String partyIdentificationTypeId) {
        this.partyIdentificationTypeId = partyIdentificationTypeId;
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
    private java.lang.String idValue;

    public java.lang.String getIdValue() {
        return idValue;
    }

    public void setIdValue(java.lang.String idValue) {
        this.idValue = idValue;
    }
    private java.lang.String partyId;

    public java.lang.String getPartyId() {
        return partyId;
    }

    public void setPartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyIdentification> objectList = new ArrayList<PartyIdentification>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyIdentification(genVal));
        }
        return objectList;
    }
}
