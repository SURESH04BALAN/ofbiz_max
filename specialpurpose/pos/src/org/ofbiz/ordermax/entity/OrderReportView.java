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

public class OrderReportView implements GenericValueObjectInterface {

    public static final String module = OrderReportView.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderReportView(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderReportView() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"groupName", "Group Name"},
        {"originFacilityId", "Origin Facility Id"},
        {"orderDate", "Order Date"},
        {"grandTotal", "Grand Total"},
        {"currencyUom", "Currency Uom"},
        {"itemStatus", "Item Status"},
        {"visitId", "Visit Id"},
        {"unitPrice", "Unit Price"},
        {"orderStatus", "Order Status"},
        {"productId", "Product Id"},
        {"itemDescription", "Item Description"},
        {"orderTypeId", "Order Type Id"},
        {"quantity", "Quantity"},
        {"webSiteId", "Web Site Id"},
        {"paymentMethod", "Payment Method"},
        {"orderId", "Order Id"},};

    protected void initObject() {
        this.groupName = "";
        this.originFacilityId = "";
        this.orderDate = UtilDateTime.nowTimestamp();
        this.grandTotal = java.math.BigDecimal.ZERO;
        this.currencyUom = "";
        this.itemStatus = "";
        this.visitId = "";
        this.unitPrice = java.math.BigDecimal.ZERO;
        this.orderStatus = "";
        this.productId = "";
        this.itemDescription = "";
        this.orderTypeId = "";
        this.quantity = java.math.BigDecimal.ZERO;
        this.webSiteId = "";
        this.paymentMethod = "";
        this.orderId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.groupName = (java.lang.String) genVal.get("groupName");
        this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
        this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
        this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
        this.currencyUom = (java.lang.String) genVal.get("currencyUom");
        this.itemStatus = (java.lang.String) genVal.get("itemStatus");
        this.visitId = (java.lang.String) genVal.get("visitId");
        this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
        this.orderStatus = (java.lang.String) genVal.get("orderStatus");
        this.productId = (java.lang.String) genVal.get("productId");
        this.itemDescription = (java.lang.String) genVal.get("itemDescription");
        this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
        this.quantity = (java.math.BigDecimal) genVal.get("quantity");
        this.webSiteId = (java.lang.String) genVal.get("webSiteId");
        this.paymentMethod = (java.lang.String) genVal.get("paymentMethod");
        this.orderId = (java.lang.String) genVal.get("orderId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("groupName", OrderMaxUtility.getValidEntityString(this.groupName));
        val.set("originFacilityId", OrderMaxUtility.getValidEntityString(this.originFacilityId));
        val.set("orderDate", OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
        val.set("grandTotal", OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
        val.set("currencyUom", OrderMaxUtility.getValidEntityString(this.currencyUom));
        val.set("itemStatus", OrderMaxUtility.getValidEntityString(this.itemStatus));
        val.set("visitId", OrderMaxUtility.getValidEntityString(this.visitId));
        val.set("unitPrice", OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
        val.set("orderStatus", OrderMaxUtility.getValidEntityString(this.orderStatus));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("itemDescription", OrderMaxUtility.getValidEntityString(this.itemDescription));
        val.set("orderTypeId", OrderMaxUtility.getValidEntityString(this.orderTypeId));
        val.set("quantity", OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
        val.set("webSiteId", OrderMaxUtility.getValidEntityString(this.webSiteId));
        val.set("paymentMethod", OrderMaxUtility.getValidEntityString(this.paymentMethod));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("groupName", this.groupName);
        valueMap.put("originFacilityId", this.originFacilityId);
        valueMap.put("orderDate", this.orderDate);
        valueMap.put("grandTotal", this.grandTotal);
        valueMap.put("currencyUom", this.currencyUom);
        valueMap.put("itemStatus", this.itemStatus);
        valueMap.put("visitId", this.visitId);
        valueMap.put("unitPrice", this.unitPrice);
        valueMap.put("orderStatus", this.orderStatus);
        valueMap.put("productId", this.productId);
        valueMap.put("itemDescription", this.itemDescription);
        valueMap.put("orderTypeId", this.orderTypeId);
        valueMap.put("quantity", this.quantity);
        valueMap.put("webSiteId", this.webSiteId);
        valueMap.put("paymentMethod", this.paymentMethod);
        valueMap.put("orderId", this.orderId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderReportView");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String groupName;

    public java.lang.String getgroupName() {
        return groupName;
    }

    public void setgroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }
    private java.lang.String originFacilityId;

    public java.lang.String getoriginFacilityId() {
        return originFacilityId;
    }

    public void setoriginFacilityId(java.lang.String originFacilityId) {
        this.originFacilityId = originFacilityId;
    }
    private java.sql.Timestamp orderDate;

    public java.sql.Timestamp getorderDate() {
        return orderDate;
    }

    public void setorderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    private java.math.BigDecimal grandTotal;

    public java.math.BigDecimal getgrandTotal() {
        return grandTotal;
    }

    public void setgrandTotal(java.math.BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
    private java.lang.String currencyUom;

    public java.lang.String getcurrencyUom() {
        return currencyUom;
    }

    public void setcurrencyUom(java.lang.String currencyUom) {
        this.currencyUom = currencyUom;
    }
    private java.lang.String itemStatus;

    public java.lang.String getitemStatus() {
        return itemStatus;
    }

    public void setitemStatus(java.lang.String itemStatus) {
        this.itemStatus = itemStatus;
    }
    private java.lang.String visitId;

    public java.lang.String getvisitId() {
        return visitId;
    }

    public void setvisitId(java.lang.String visitId) {
        this.visitId = visitId;
    }
    private java.math.BigDecimal unitPrice;

    public java.math.BigDecimal getunitPrice() {
        return unitPrice;
    }

    public void setunitPrice(java.math.BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    private java.lang.String orderStatus;

    public java.lang.String getorderStatus() {
        return orderStatus;
    }

    public void setorderStatus(java.lang.String orderStatus) {
        this.orderStatus = orderStatus;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String itemDescription;

    public java.lang.String getitemDescription() {
        return itemDescription;
    }

    public void setitemDescription(java.lang.String itemDescription) {
        this.itemDescription = itemDescription;
    }
    private java.lang.String orderTypeId;

    public java.lang.String getorderTypeId() {
        return orderTypeId;
    }

    public void setorderTypeId(java.lang.String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getquantity() {
        return quantity;
    }

    public void setquantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }
    private java.lang.String webSiteId;

    public java.lang.String getwebSiteId() {
        return webSiteId;
    }

    public void setwebSiteId(java.lang.String webSiteId) {
        this.webSiteId = webSiteId;
    }
    private java.lang.String paymentMethod;

    public java.lang.String getpaymentMethod() {
        return paymentMethod;
    }

    public void setpaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderReportView> objectList = new ArrayList<OrderReportView>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderReportView(genVal));
        }
        return objectList;
    }
}
