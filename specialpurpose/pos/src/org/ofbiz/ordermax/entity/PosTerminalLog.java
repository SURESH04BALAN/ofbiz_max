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

public class PosTerminalLog implements GenericValueObjectInterface {

    public static final String module = PosTerminalLog.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PosTerminalLog(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public PosTerminalLog() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
     //   {"lastUpdatedStamp", "Last Updated Stamp"},
        
       // {"createdTxStamp", "Created Tx Stamp"},
       // {"createdStamp", "Created Stamp"},
       // {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"posTerminalLogId", "Pos Terminal Log Id"},
        {"posTerminalId", "Pos Terminal Id"},
        {"transactionId", "Transaction Id"},
        {"itemCount", "Item Count"},
        {"orderId", "Order Id"},
        {"returnId", "Return Id"},
        {"userLoginId", "User Login Id"},
        {"statusId", "Status Id"},
        {"logStartDateTime", "Log Start Date Time"},
        {"logEndDateTime", "Log End Date Time"},        
        };

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.orderId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.posTerminalLogId = "";
        this.logEndDateTime = UtilDateTime.nowTimestamp();
        this.transactionId = "";
        this.posTerminalId = "";
        this.itemCount = "";
        this.logStartDateTime = UtilDateTime.nowTimestamp();
        this.userLoginId = "";
        this.statusId = "";
        this.returnId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.posTerminalLogId = (java.lang.String) genVal.get("posTerminalLogId");
        this.logEndDateTime = (java.sql.Timestamp) genVal.get("logEndDateTime");
        this.transactionId = (java.lang.String) genVal.get("transactionId");
        this.posTerminalId = (java.lang.String) genVal.get("posTerminalId");
        this.itemCount = (java.lang.String) genVal.get("itemCount");
        this.logStartDateTime = (java.sql.Timestamp) genVal.get("logStartDateTime");
        this.userLoginId = (java.lang.String) genVal.get("userLoginId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.returnId = (java.lang.String) genVal.get("returnId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("posTerminalLogId", OrderMaxUtility.getValidEntityString(this.posTerminalLogId));
        val.set("logEndDateTime", OrderMaxUtility.getValidEntityTimestamp(this.logEndDateTime));
        val.set("transactionId", OrderMaxUtility.getValidEntityString(this.transactionId));
        val.set("posTerminalId", OrderMaxUtility.getValidEntityString(this.posTerminalId));
        val.set("itemCount", OrderMaxUtility.getValidEntityString(this.itemCount));
        val.set("logStartDateTime", OrderMaxUtility.getValidEntityTimestamp(this.logStartDateTime));
        val.set("userLoginId", OrderMaxUtility.getValidEntityString(this.userLoginId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("returnId", OrderMaxUtility.getValidEntityString(this.returnId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("orderId", this.orderId);
        valueMap.put("posTerminalLogId", this.posTerminalLogId);
        valueMap.put("logEndDateTime", this.logEndDateTime);
        valueMap.put("transactionId", this.transactionId);
        valueMap.put("posTerminalId", this.posTerminalId);
        valueMap.put("itemCount", this.itemCount);
        valueMap.put("logStartDateTime", this.logStartDateTime);
        valueMap.put("userLoginId", this.userLoginId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("returnId", this.returnId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PosTerminalLog");
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
    private java.lang.String orderId;

    public java.lang.String getOrderId() {
        return orderId;
    }

    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
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
    private java.lang.String posTerminalLogId;

    public java.lang.String getPosTerminalLogId() {
        return posTerminalLogId;
    }

    public void setPosTerminalLogId(java.lang.String posTerminalLogId) {
        this.posTerminalLogId = posTerminalLogId;
    }
    private java.sql.Timestamp logEndDateTime;

    public java.sql.Timestamp getLogEndDateTime() {
        return logEndDateTime;
    }

    public void setLogEndDateTime(java.sql.Timestamp logEndDateTime) {
        this.logEndDateTime = logEndDateTime;
    }
    private java.lang.String transactionId;

    public java.lang.String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }
    private java.lang.String posTerminalId;

    public java.lang.String getPosTerminalId() {
        return posTerminalId;
    }

    public void setPosTerminalId(java.lang.String posTerminalId) {
        this.posTerminalId = posTerminalId;
    }
    private java.lang.String itemCount;

    public java.lang.String getItemCount() {
        return itemCount;
    }

    public void setItemCount(java.lang.String itemCount) {
        this.itemCount = itemCount;
    }
    private java.sql.Timestamp logStartDateTime;

    public java.sql.Timestamp getLogStartDateTime() {
        return logStartDateTime;
    }

    public void setLogStartDateTime(java.sql.Timestamp logStartDateTime) {
        this.logStartDateTime = logStartDateTime;
    }
    private java.lang.String userLoginId;

    public java.lang.String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(java.lang.String userLoginId) {
        this.userLoginId = userLoginId;
    }
    private java.lang.String statusId;

    public java.lang.String getStatusId() {
        return statusId;
    }

    public void setStatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String returnId;

    public java.lang.String getReturnId() {
        return returnId;
    }

    public void setReturnId(java.lang.String returnId) {
        this.returnId = returnId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PosTerminalLog> objectList = new ArrayList<PosTerminalLog>();
        for (GenericValue genVal : genList) {
            objectList.add(new PosTerminalLog(genVal));
        }
        return objectList;
    }
}
