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

public class OrderDeliverySchedule implements GenericValueObjectInterface {

    public static final String module = OrderDeliverySchedule.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderDeliverySchedule(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderDeliverySchedule() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"orderItemSeqId", "Order Item Seq Id"},
        {"unitsPieces", "Units Pieces"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"orderId", "Order Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"totalCubicSize", "Total Cubic Size"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"estimatedReadyDate", "Estimated Ready Date"},
        {"skidsPallets", "Skids Pallets"},
        {"statusId", "Status Id"},
        {"totalWeight", "Total Weight"},
        {"totalWeightUomId", "Total Weight Uom Id"},
        {"totalCubicUomId", "Total Cubic Uom Id"},
        {"cartons", "Cartons"},};

    protected void initObject() {
        this.orderItemSeqId = "";
        this.unitsPieces = java.math.BigDecimal.ZERO;
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.orderId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.totalCubicSize = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.estimatedReadyDate = UtilDateTime.nowTimestamp();
        this.skidsPallets = new Long(0);
        this.statusId = "";
        this.totalWeight = "";
        this.totalWeightUomId = "";
        this.totalCubicUomId = "";
        this.cartons = new Long(0);
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
        this.unitsPieces = (java.math.BigDecimal) genVal.get("unitsPieces");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.totalCubicSize = (java.lang.String) genVal.get("totalCubicSize");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.estimatedReadyDate = (java.sql.Timestamp) genVal.get("estimatedReadyDate");
        this.skidsPallets = (java.lang.Long) genVal.get("skidsPallets");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.totalWeight = (java.lang.String) genVal.get("totalWeight");
        this.totalWeightUomId = (java.lang.String) genVal.get("totalWeightUomId");
        this.totalCubicUomId = (java.lang.String) genVal.get("totalCubicUomId");
        this.cartons = (java.lang.Long) genVal.get("cartons");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("orderItemSeqId", OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
        val.set("unitsPieces", OrderMaxUtility.getValidEntityBigDecimal(this.unitsPieces));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("totalCubicSize", OrderMaxUtility.getValidEntityString(this.totalCubicSize));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("estimatedReadyDate", OrderMaxUtility.getValidEntityTimestamp(this.estimatedReadyDate));
        val.set("skidsPallets", this.skidsPallets);
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("totalWeight", OrderMaxUtility.getValidEntityString(this.totalWeight));
        val.set("totalWeightUomId", OrderMaxUtility.getValidEntityString(this.totalWeightUomId));
        val.set("totalCubicUomId", OrderMaxUtility.getValidEntityString(this.totalCubicUomId));
        val.set("cartons", this.cartons);
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("orderItemSeqId", this.orderItemSeqId);
        valueMap.put("unitsPieces", this.unitsPieces);
        valueMap.put("orderId", this.orderId);
        valueMap.put("totalCubicSize", this.totalCubicSize);
        valueMap.put("estimatedReadyDate", this.estimatedReadyDate);
        valueMap.put("skidsPallets", this.skidsPallets);
        valueMap.put("statusId", this.statusId);
        valueMap.put("totalWeight", this.totalWeight);
        valueMap.put("totalWeightUomId", this.totalWeightUomId);
        valueMap.put("totalCubicUomId", this.totalCubicUomId);
        valueMap.put("cartons", this.cartons);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderDeliverySchedule");
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
    private java.math.BigDecimal unitsPieces;

    public java.math.BigDecimal getunitsPieces() {
        return unitsPieces;
    }

    public void setunitsPieces(java.math.BigDecimal unitsPieces) {
        this.unitsPieces = unitsPieces;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
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
    private java.lang.String totalCubicSize;

    public java.lang.String gettotalCubicSize() {
        return totalCubicSize;
    }

    public void settotalCubicSize(java.lang.String totalCubicSize) {
        this.totalCubicSize = totalCubicSize;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.sql.Timestamp estimatedReadyDate;

    public java.sql.Timestamp getestimatedReadyDate() {
        return estimatedReadyDate;
    }

    public void setestimatedReadyDate(java.sql.Timestamp estimatedReadyDate) {
        this.estimatedReadyDate = estimatedReadyDate;
    }
    private java.lang.Long skidsPallets;

    public java.lang.Long getskidsPallets() {
        return skidsPallets;
    }

    public void setskidsPallets(java.lang.Long skidsPallets) {
        this.skidsPallets = skidsPallets;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String totalWeight;

    public java.lang.String gettotalWeight() {
        return totalWeight;
    }

    public void settotalWeight(java.lang.String totalWeight) {
        this.totalWeight = totalWeight;
    }
    private java.lang.String totalWeightUomId;

    public java.lang.String gettotalWeightUomId() {
        return totalWeightUomId;
    }

    public void settotalWeightUomId(java.lang.String totalWeightUomId) {
        this.totalWeightUomId = totalWeightUomId;
    }
    private java.lang.String totalCubicUomId;

    public java.lang.String gettotalCubicUomId() {
        return totalCubicUomId;
    }

    public void settotalCubicUomId(java.lang.String totalCubicUomId) {
        this.totalCubicUomId = totalCubicUomId;
    }
    private java.lang.Long cartons;

    public java.lang.Long getcartons() {
        return cartons;
    }

    public void setcartons(java.lang.Long cartons) {
        this.cartons = cartons;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderDeliverySchedule> objectList = new ArrayList<OrderDeliverySchedule>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderDeliverySchedule(genVal));
        }
        return objectList;
    }
}
