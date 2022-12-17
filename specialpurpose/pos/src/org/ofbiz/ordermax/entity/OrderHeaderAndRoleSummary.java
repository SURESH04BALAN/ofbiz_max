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

public class OrderHeaderAndRoleSummary implements GenericValueObjectInterface {

    public static final String module = OrderHeaderAndRoleSummary.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderHeaderAndRoleSummary(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderHeaderAndRoleSummary() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"statusId", "Status Id"},
        {"roleTypeId", "Role Type Id"},
        {"partyId", "Party Id"},
        {"orderTypeId", "Order Type Id"},
        {"orderDate", "Order Date"},
        {"totalOrders", "Total Orders"},
        {"totalGrandAmount", "Total Grand Amount"},
        {"orderId", "Order Id"},
        {"totalSubRemainingAmount", "Total Sub Remaining Amount"},};

    protected void initObject() {
        this.statusId = "";
        this.roleTypeId = "";
        this.partyId = "";
        this.orderTypeId = "";
        this.orderDate = UtilDateTime.nowTimestamp();
        this.totalOrders = new Long(0);
        this.totalGrandAmount = java.math.BigDecimal.ZERO;
        this.orderId = "";
        this.totalSubRemainingAmount = java.math.BigDecimal.ZERO;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.orderId = (java.lang.String) genVal.get("orderId");        
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
        this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
        
        this.orderName = (java.lang.String) genVal.get("orderName");
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");        
//        this.totalOrders = (java.lang.Long) genVal.get("totalOrders");
        this.totalGrandAmount = (java.math.BigDecimal) genVal.get("totalGrandAmount");

        this.totalSubRemainingAmount = (java.math.BigDecimal) genVal.get("totalSubRemainingAmount");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("roleTypeId", OrderMaxUtility.getValidEntityString(this.roleTypeId));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("orderTypeId", OrderMaxUtility.getValidEntityString(this.orderTypeId));
        val.set("orderDate", OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
        val.set("totalOrders", this.totalOrders);
        val.set("totalGrandAmount", OrderMaxUtility.getValidEntityBigDecimal(this.totalGrandAmount));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("totalSubRemainingAmount", OrderMaxUtility.getValidEntityBigDecimal(this.totalSubRemainingAmount));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("statusId", this.statusId);
        valueMap.put("roleTypeId", this.roleTypeId);
        valueMap.put("partyId", this.partyId);
        valueMap.put("orderTypeId", this.orderTypeId);
        valueMap.put("orderDate", this.orderDate);
        valueMap.put("totalOrders", this.totalOrders);
        valueMap.put("totalGrandAmount", this.totalGrandAmount);
        valueMap.put("orderId", this.orderId);
        valueMap.put("totalSubRemainingAmount", this.totalSubRemainingAmount);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderHeaderAndRoleSummary");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String orderName;
    private java.lang.String billTo;
    private java.lang.String billFrom;    

    public String getBillFrom() {
        return billFrom;
    }

    public void setBillFrom(String billFrom) {
        this.billFrom = billFrom;
    }
    private java.lang.String trackingCode;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getBillTo() {
        return billTo;
    }

    public void setBillTo(String billTo) {
        this.billTo = billTo;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }
    private java.lang.String productStoreId;

    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String roleTypeId;

    public java.lang.String getroleTypeId() {
        return roleTypeId;
    }

    public void setroleTypeId(java.lang.String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String orderTypeId;

    public java.lang.String getorderTypeId() {
        return orderTypeId;
    }

    public void setorderTypeId(java.lang.String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
    private java.sql.Timestamp orderDate;

    public java.sql.Timestamp getorderDate() {
        return orderDate;
    }

    public void setorderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    private java.lang.Long totalOrders;

    public java.lang.Long gettotalOrders() {
        return totalOrders;
    }

    public void settotalOrders(java.lang.Long totalOrders) {
        this.totalOrders = totalOrders;
    }
    private java.math.BigDecimal totalGrandAmount;

    public java.math.BigDecimal gettotalGrandAmount() {
        return totalGrandAmount;
    }

    public void settotalGrandAmount(java.math.BigDecimal totalGrandAmount) {
        this.totalGrandAmount = totalGrandAmount;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }
    private java.math.BigDecimal totalSubRemainingAmount;

    public java.math.BigDecimal gettotalSubRemainingAmount() {
        return totalSubRemainingAmount;
    }

    public void settotalSubRemainingAmount(java.math.BigDecimal totalSubRemainingAmount) {
        this.totalSubRemainingAmount = totalSubRemainingAmount;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderAndRoleSummary> objectList = new ArrayList<OrderHeaderAndRoleSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderAndRoleSummary(genVal));
        }
        return objectList;
    }
}
