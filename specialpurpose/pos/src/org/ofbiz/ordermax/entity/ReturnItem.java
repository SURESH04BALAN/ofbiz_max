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

public class ReturnItem implements GenericValueObjectInterface {

    public static final String module = ReturnItem.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ReturnItem(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ReturnItem() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"orderItemSeqId", "Order Item Seq Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"returnQuantity", "Return Quantity"},
        {"productId", "Product Id"},
        {"orderId", "Order Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"returnTypeId", "Return Type Id"},
        {"description", "Description"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"returnPrice", "Return Price"},
        {"returnItemResponseId", "Return Item Response Id"},
        {"statusId", "Status Id"},
        {"returnReasonId", "Return Reason Id"},
        {"returnItemTypeId", "Return Item Type Id"},
        {"receivedQuantity", "Received Quantity"},
        {"returnId", "Return Id"},
        {"returnItemSeqId", "Return Item Seq Id"},
        {"expectedItemStatus", "Expected Item Status"},};

    protected void initObject() {
        this.orderItemSeqId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.returnQuantity = java.math.BigDecimal.ZERO;
        this.productId = "";
        this.orderId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.returnTypeId = "";
        this.description = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.returnPrice = java.math.BigDecimal.ZERO;
        this.returnItemResponseId = "";
        this.statusId = "";
        this.returnReasonId = "";
        this.returnItemTypeId = "";
        this.receivedQuantity = java.math.BigDecimal.ZERO;
        this.returnId = "";
        this.returnItemSeqId = "";
        this.expectedItemStatus = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.returnQuantity = (java.math.BigDecimal) genVal.get("returnQuantity");
        this.productId = (java.lang.String) genVal.get("productId");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.returnTypeId = (java.lang.String) genVal.get("returnTypeId");
        this.description = (java.lang.String) genVal.get("description");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.returnPrice = (java.math.BigDecimal) genVal.get("returnPrice");
        this.returnItemResponseId = (java.lang.String) genVal.get("returnItemResponseId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.returnReasonId = (java.lang.String) genVal.get("returnReasonId");
        this.returnItemTypeId = (java.lang.String) genVal.get("returnItemTypeId");
        this.receivedQuantity = (java.math.BigDecimal) genVal.get("receivedQuantity");
        this.returnId = (java.lang.String) genVal.get("returnId");
        this.returnItemSeqId = (java.lang.String) genVal.get("returnItemSeqId");
        this.expectedItemStatus = (java.lang.String) genVal.get("expectedItemStatus");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("orderItemSeqId", OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("returnQuantity", OrderMaxUtility.getValidEntityBigDecimal(this.returnQuantity));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("returnTypeId", OrderMaxUtility.getValidEntityString(this.returnTypeId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("returnPrice", OrderMaxUtility.getValidEntityBigDecimal(this.returnPrice));
        val.set("returnItemResponseId", OrderMaxUtility.getValidEntityString(this.returnItemResponseId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("returnReasonId", OrderMaxUtility.getValidEntityString(this.returnReasonId));
        val.set("returnItemTypeId", OrderMaxUtility.getValidEntityString(this.returnItemTypeId));
        val.set("receivedQuantity", OrderMaxUtility.getValidEntityBigDecimal(this.receivedQuantity));
        val.set("returnId", OrderMaxUtility.getValidEntityString(this.returnId));
        val.set("returnItemSeqId", OrderMaxUtility.getValidEntityString(this.returnItemSeqId));
        val.set("expectedItemStatus", OrderMaxUtility.getValidEntityString(this.expectedItemStatus));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("orderItemSeqId", this.orderItemSeqId);
        valueMap.put("returnQuantity", this.returnQuantity);
        valueMap.put("productId", this.productId);
        valueMap.put("orderId", this.orderId);
        valueMap.put("returnTypeId", this.returnTypeId);
        valueMap.put("description", this.description);
        valueMap.put("returnPrice", this.returnPrice);
        valueMap.put("returnItemResponseId", this.returnItemResponseId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("returnReasonId", this.returnReasonId);
        valueMap.put("returnItemTypeId", this.returnItemTypeId);
        valueMap.put("receivedQuantity", this.receivedQuantity);
        valueMap.put("returnId", this.returnId);
        valueMap.put("returnItemSeqId", this.returnItemSeqId);
        valueMap.put("expectedItemStatus", this.expectedItemStatus);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ReturnItem");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String orderItemSeqId;

    public java.lang.String getorderItemSeqId() {
        return orderItemSeqId;
    }

    public void setorderItemSeqId(java.lang.String orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.math.BigDecimal returnQuantity;

    public java.math.BigDecimal getreturnQuantity() {
        return returnQuantity;
    }

    public void setreturnQuantity(java.math.BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
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
    private java.lang.String returnTypeId;

    public java.lang.String getreturnTypeId() {
        return returnTypeId;
    }

    public void setreturnTypeId(java.lang.String returnTypeId) {
        this.returnTypeId = returnTypeId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.math.BigDecimal returnPrice;

    public java.math.BigDecimal getreturnPrice() {
        return returnPrice;
    }

    public void setreturnPrice(java.math.BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }
    private java.lang.String returnItemResponseId;

    public java.lang.String getreturnItemResponseId() {
        return returnItemResponseId;
    }

    public void setreturnItemResponseId(java.lang.String returnItemResponseId) {
        this.returnItemResponseId = returnItemResponseId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String returnReasonId;

    public java.lang.String getreturnReasonId() {
        return returnReasonId;
    }

    public void setreturnReasonId(java.lang.String returnReasonId) {
        this.returnReasonId = returnReasonId;
    }
    private java.lang.String returnItemTypeId;

    public java.lang.String getreturnItemTypeId() {
        return returnItemTypeId;
    }

    public void setreturnItemTypeId(java.lang.String returnItemTypeId) {
        this.returnItemTypeId = returnItemTypeId;
    }
    private java.math.BigDecimal receivedQuantity;

    public java.math.BigDecimal getreceivedQuantity() {
        return receivedQuantity;
    }

    public void setreceivedQuantity(java.math.BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }
    private java.lang.String returnId;

    public java.lang.String getreturnId() {
        return returnId;
    }

    public void setreturnId(java.lang.String returnId) {
        this.returnId = returnId;
    }
    private java.lang.String returnItemSeqId;

    public java.lang.String getreturnItemSeqId() {
        return returnItemSeqId;
    }

    public void setreturnItemSeqId(java.lang.String returnItemSeqId) {
        this.returnItemSeqId = returnItemSeqId;
    }
    private java.lang.String expectedItemStatus;

    public java.lang.String getexpectedItemStatus() {
        return expectedItemStatus;
    }

    public void setexpectedItemStatus(java.lang.String expectedItemStatus) {
        this.expectedItemStatus = expectedItemStatus;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ReturnItem> objectList = new ArrayList<ReturnItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ReturnItem(genVal));
        }
        return objectList;
    }
}
