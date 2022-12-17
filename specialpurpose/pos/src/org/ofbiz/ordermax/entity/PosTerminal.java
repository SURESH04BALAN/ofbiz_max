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

public class PosTerminal implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = PosTerminal.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PosTerminal(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PosTerminal() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
         {"posTerminalId", "Pos Terminal Id"},
        {"terminalName", "Terminal Name"},
      //  {"lastUpdatedStamp", "Last Updated Stamp"},
        {"facilityId", "Facility Id"},
      //  {"createdTxStamp", "Created Tx Stamp"},
      //  {"createdStamp", "Created Stamp"},
       // {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"pushEntitySyncId", "Push Entity Sync Id"},
       };

    protected void initObject() {
        this.terminalName = "";
        this.lastUpdatedStamp = null;
        this.facilityId = "";
        this.createdTxStamp = null;
        this.createdStamp = null;
        this.lastUpdatedTxStamp = null;
        this.pushEntitySyncId = "";
        this.posTerminalId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.terminalName = (java.lang.String) genVal.get("terminalName");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.pushEntitySyncId = (java.lang.String) genVal.get("pushEntitySyncId");
        this.posTerminalId = (java.lang.String) genVal.get("posTerminalId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("terminalName", OrderMaxUtility.getValidEntityString(this.terminalName));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("pushEntitySyncId", OrderMaxUtility.getValidEntityString(this.pushEntitySyncId));
        val.set("posTerminalId", OrderMaxUtility.getValidEntityString(this.posTerminalId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("terminalName", this.terminalName);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("pushEntitySyncId", this.pushEntitySyncId);
        valueMap.put("posTerminalId", this.posTerminalId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PosTerminal");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String terminalName;

    public java.lang.String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String facilityId;

    public java.lang.String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
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
    private java.lang.String pushEntitySyncId;

    public java.lang.String getPushEntitySyncId() {
        return pushEntitySyncId;
    }

    public void setPushEntitySyncId(java.lang.String pushEntitySyncId) {
        this.pushEntitySyncId = pushEntitySyncId;
    }
    private java.lang.String posTerminalId;

    public java.lang.String getPosTerminalId() {
        return posTerminalId;
    }

    public void setPosTerminalId(java.lang.String posTerminalId) {
        this.posTerminalId = posTerminalId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PosTerminal> objectList = new ArrayList<PosTerminal>();
        for (GenericValue genVal : genList) {
            objectList.add(new PosTerminal(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getTerminalName());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getPosTerminalId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getPosTerminalId());
        }

        return orderToStringBuilder.toString();
    }

}
