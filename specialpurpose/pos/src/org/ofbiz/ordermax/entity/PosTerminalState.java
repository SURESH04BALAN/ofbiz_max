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

public class PosTerminalState implements GenericValueObjectInterface {

    public static final String module = PosTerminalState.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PosTerminalState(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public PosTerminalState() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        /*{"lastUpdatedStamp", "Last Updated Stamp"},
        
         {"createdTxStamp", "Created Tx Stamp"},
         {"createdStamp", "Created Stamp"},
         {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},*/
        {"posTerminalId", "Pos Terminal Id"},
        {"openedDate", "Opened Date"},
        {"closedDate", "Closed Date"},
        {"startingTxId", "Starting Tx Id"},
        {"endingTxId", "Ending Tx Id"},
        {"openedByUserLoginId", "Opened By User Login Id"},
        {"closedByUserLoginId", "Closed By User Login Id"},
        {"startingDrawerAmount", "Starting Drawer Amount"},
        {"actualEndingCash", "Actual Ending Cash"},
        {"actualEndingCheck", "Actual Ending Check"},
        {"actualEndingGc", "Actual Ending Gc"},
        {"actualEndingCc", "Actual Ending Cc"},
        {"actualEndingOther", "Actual Ending Other"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.startingTxId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.endingTxId = "";
        this.actualEndingCash = "";
        this.actualEndingOther = "";
        this.posTerminalId = "";
        this.openedDate = UtilDateTime.nowTimestamp();
        this.openedByUserLoginId = "";
        this.actualEndingCheck = "";
        this.closedByUserLoginId = "";
        this.closedDate = UtilDateTime.nowTimestamp();
        this.actualEndingGc = "";
        this.startingDrawerAmount = java.math.BigDecimal.ZERO;
        this.actualEndingCc = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.startingTxId = (java.lang.String) genVal.get("startingTxId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.endingTxId = (java.lang.String) genVal.get("endingTxId");
        this.actualEndingCash = (java.lang.String) genVal.get("actualEndingCash");
        this.actualEndingOther = (java.lang.String) genVal.get("actualEndingOther");
        this.posTerminalId = (java.lang.String) genVal.get("posTerminalId");
        this.openedDate = (java.sql.Timestamp) genVal.get("openedDate");
        this.openedByUserLoginId = (java.lang.String) genVal.get("openedByUserLoginId");
        this.actualEndingCheck = (java.lang.String) genVal.get("actualEndingCheck");
        this.closedByUserLoginId = (java.lang.String) genVal.get("closedByUserLoginId");
        this.closedDate = (java.sql.Timestamp) genVal.get("closedDate");
        this.actualEndingGc = (java.lang.String) genVal.get("actualEndingGc");
        this.startingDrawerAmount = (java.math.BigDecimal) genVal.get("startingDrawerAmount");
        this.actualEndingCc = (java.lang.String) genVal.get("actualEndingCc");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("startingTxId", OrderMaxUtility.getValidEntityString(this.startingTxId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("endingTxId", OrderMaxUtility.getValidEntityString(this.endingTxId));
        val.set("actualEndingCash", OrderMaxUtility.getValidEntityString(this.actualEndingCash));
        val.set("actualEndingOther", OrderMaxUtility.getValidEntityString(this.actualEndingOther));
        val.set("posTerminalId", OrderMaxUtility.getValidEntityString(this.posTerminalId));
        val.set("openedDate", OrderMaxUtility.getValidEntityTimestamp(this.openedDate));
        val.set("openedByUserLoginId", OrderMaxUtility.getValidEntityString(this.openedByUserLoginId));
        val.set("actualEndingCheck", OrderMaxUtility.getValidEntityString(this.actualEndingCheck));
        val.set("closedByUserLoginId", OrderMaxUtility.getValidEntityString(this.closedByUserLoginId));
        val.set("closedDate", OrderMaxUtility.getValidEntityTimestamp(this.closedDate));
        val.set("actualEndingGc", OrderMaxUtility.getValidEntityString(this.actualEndingGc));
        val.set("startingDrawerAmount", OrderMaxUtility.getValidEntityBigDecimal(this.startingDrawerAmount));
        val.set("actualEndingCc", OrderMaxUtility.getValidEntityString(this.actualEndingCc));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("startingTxId", this.startingTxId);
        valueMap.put("endingTxId", this.endingTxId);
        valueMap.put("actualEndingCash", this.actualEndingCash);
        valueMap.put("actualEndingOther", this.actualEndingOther);
        valueMap.put("posTerminalId", this.posTerminalId);
        valueMap.put("openedDate", this.openedDate);
        valueMap.put("openedByUserLoginId", this.openedByUserLoginId);
        valueMap.put("actualEndingCheck", this.actualEndingCheck);
        valueMap.put("closedByUserLoginId", this.closedByUserLoginId);
        valueMap.put("closedDate", this.closedDate);
        valueMap.put("actualEndingGc", this.actualEndingGc);
        valueMap.put("startingDrawerAmount", this.startingDrawerAmount);
        valueMap.put("actualEndingCc", this.actualEndingCc);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PosTerminalState");
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
    private java.lang.String startingTxId;

    public java.lang.String getStartingTxId() {
        return startingTxId;
    }

    public void setStartingTxId(java.lang.String startingTxId) {
        this.startingTxId = startingTxId;
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
    private java.lang.String endingTxId;

    public java.lang.String getEndingTxId() {
        return endingTxId;
    }

    public void setEndingTxId(java.lang.String endingTxId) {
        this.endingTxId = endingTxId;
    }
    private java.lang.String actualEndingCash;

    public java.lang.String getActualEndingCash() {
        return actualEndingCash;
    }

    public void setActualEndingCash(java.lang.String actualEndingCash) {
        this.actualEndingCash = actualEndingCash;
    }
    private java.lang.String actualEndingOther;

    public java.lang.String getActualEndingOther() {
        return actualEndingOther;
    }

    public void setActualEndingOther(java.lang.String actualEndingOther) {
        this.actualEndingOther = actualEndingOther;
    }
    private java.lang.String posTerminalId;

    public java.lang.String getPosTerminalId() {
        return posTerminalId;
    }

    public void setPosTerminalId(java.lang.String posTerminalId) {
        this.posTerminalId = posTerminalId;
    }
    private java.sql.Timestamp openedDate;

    public java.sql.Timestamp getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(java.sql.Timestamp openedDate) {
        this.openedDate = openedDate;
    }
    private java.lang.String openedByUserLoginId;

    public java.lang.String getOpenedByUserLoginId() {
        return openedByUserLoginId;
    }

    public void setOpenedByUserLoginId(java.lang.String openedByUserLoginId) {
        this.openedByUserLoginId = openedByUserLoginId;
    }
    private java.lang.String actualEndingCheck;

    public java.lang.String getActualEndingCheck() {
        return actualEndingCheck;
    }

    public void setActualEndingCheck(java.lang.String actualEndingCheck) {
        this.actualEndingCheck = actualEndingCheck;
    }
    private java.lang.String closedByUserLoginId;

    public java.lang.String getClosedByUserLoginId() {
        return closedByUserLoginId;
    }

    public void setClosedByUserLoginId(java.lang.String closedByUserLoginId) {
        this.closedByUserLoginId = closedByUserLoginId;
    }
    private java.sql.Timestamp closedDate;

    public java.sql.Timestamp getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(java.sql.Timestamp closedDate) {
        this.closedDate = closedDate;
    }
    private java.lang.String actualEndingGc;

    public java.lang.String getActualEndingGc() {
        return actualEndingGc;
    }

    public void setActualEndingGc(java.lang.String actualEndingGc) {
        this.actualEndingGc = actualEndingGc;
    }
    private java.math.BigDecimal startingDrawerAmount;

    public java.math.BigDecimal getStartingDrawerAmount() {
        return startingDrawerAmount;
    }

    public void setStartingDrawerAmount(java.math.BigDecimal startingDrawerAmount) {
        this.startingDrawerAmount = startingDrawerAmount;
    }
    private java.lang.String actualEndingCc;

    public java.lang.String getActualEndingCc() {
        return actualEndingCc;
    }

    public void setActualEndingCc(java.lang.String actualEndingCc) {
        this.actualEndingCc = actualEndingCc;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PosTerminalState> objectList = new ArrayList<PosTerminalState>();
        for (GenericValue genVal : genList) {
            objectList.add(new PosTerminalState(genVal));
        }
        return objectList;
    }

}
