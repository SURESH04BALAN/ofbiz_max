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

public class OrderHeaderAndRoles implements GenericValueObjectInterface {

    public static final String module = OrderHeaderAndRoles.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderHeaderAndRoles(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderHeaderAndRoles() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"transactionId", "Transaction Id"},
        {"productStoreId", "Product Store Id"},
        {"originFacilityId", "Origin Facility Id"},
        {"needsInventoryIssuance", "Needs Inventory Issuance"},
        //{"invoicePerShipment","Invoice Per Shipment"},
        {"pickSheetPrintedDate", "Pick Sheet Printed Date"},
        {"orderName", "Order Name"},
        {"currencyUom", "Currency Uom"},
        {"visitId", "Visit Id"},
        {"externalId", "External Id"},
        {"terminalId", "Terminal Id"},
        {"statusId", "Status Id"},
        {"remainingSubTotal", "Remaining Sub Total"},
        {"orderTypeId", "Order Type Id"},
        {"priority", "Priority"},
        {"isViewed", "Is Viewed"},
        {"orderId", "Order Id"},
        {"billingAccountId", "Billing Account Id"},
        {"orderDate", "Order Date"},
        {"grandTotal", "Grand Total"},
        {"internalCode", "Internal Code"},
        {"entryDate", "Entry Date"},
        {"syncStatusId", "Sync Status Id"},
        {"roleTypeId", "Role Type Id"},
        {"createdBy", "Created By"},
        {"partyId", "Party Id"},
        {"autoOrderShoppingListId", "Auto Order Shopping List Id"},
        {"isRushOrder", "Is Rush Order"},
        {"firstAttemptOrderId", "First Attempt Order Id"},
        {"webSiteId", "Web Site Id"},
        {"salesChannelEnumId", "Sales Channel Enum Id"},};

    protected void initObject() {
        this.transactionId = "";
        this.productStoreId = "";
        this.originFacilityId = "";
        this.needsInventoryIssuance = "";
        this.invoicePerShipment = "";
        this.pickSheetPrintedDate = UtilDateTime.nowTimestamp();
        this.orderName = "";
        this.currencyUom = "";
        this.visitId = "";
        this.externalId = "";
        this.terminalId = "";
        this.statusId = "";
        this.remainingSubTotal = java.math.BigDecimal.ZERO;
        this.orderTypeId = "";
        this.priority = "";
        this.isViewed = "";
        this.orderId = "";
        this.billingAccountId = "";
        this.orderDate = UtilDateTime.nowTimestamp();
        this.grandTotal = java.math.BigDecimal.ZERO;
        this.internalCode = "";
        this.entryDate = UtilDateTime.nowTimestamp();
        this.syncStatusId = "";
        this.roleTypeId = "";
        this.createdBy = "";
        this.partyId = "";
        this.autoOrderShoppingListId = "";
        this.isRushOrder = "";
        this.firstAttemptOrderId = "";
        this.webSiteId = "";
        this.salesChannelEnumId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.transactionId = (java.lang.String) genVal.get("transactionId");
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");
        this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
        this.needsInventoryIssuance = (java.lang.String) genVal.get("needsInventoryIssuance");
        this.invoicePerShipment = (java.lang.String) genVal.get("invoicePerShipment");
        this.pickSheetPrintedDate = (java.sql.Timestamp) genVal.get("pickSheetPrintedDate");
        this.orderName = (java.lang.String) genVal.get("orderName");
        this.currencyUom = (java.lang.String) genVal.get("currencyUom");
        this.visitId = (java.lang.String) genVal.get("visitId");
        this.externalId = (java.lang.String) genVal.get("externalId");
        this.terminalId = (java.lang.String) genVal.get("terminalId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.remainingSubTotal = (java.math.BigDecimal) genVal.get("remainingSubTotal");
        this.orderTypeId = (java.lang.String) genVal.get("orderTypeId");
        this.priority = (java.lang.String) genVal.get("priority");
        this.isViewed = (java.lang.String) genVal.get("isViewed");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
        this.grandTotal = (java.math.BigDecimal) genVal.get("grandTotal");
        this.internalCode = (java.lang.String) genVal.get("internalCode");
        this.entryDate = (java.sql.Timestamp) genVal.get("entryDate");
        this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
        this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
        this.createdBy = (java.lang.String) genVal.get("createdBy");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.autoOrderShoppingListId = (java.lang.String) genVal.get("autoOrderShoppingListId");
        this.isRushOrder = (java.lang.String) genVal.get("isRushOrder");
        this.firstAttemptOrderId = (java.lang.String) genVal.get("firstAttemptOrderId");
        this.webSiteId = (java.lang.String) genVal.get("webSiteId");
        this.salesChannelEnumId = (java.lang.String) genVal.get("salesChannelEnumId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("transactionId", OrderMaxUtility.getValidEntityString(this.transactionId));
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.productStoreId));
        val.set("originFacilityId", OrderMaxUtility.getValidEntityString(this.originFacilityId));
        val.set("needsInventoryIssuance", OrderMaxUtility.getValidEntityString(this.needsInventoryIssuance));
        val.set("invoicePerShipment", OrderMaxUtility.getValidEntityString(this.invoicePerShipment));
        val.set("pickSheetPrintedDate", OrderMaxUtility.getValidEntityTimestamp(this.pickSheetPrintedDate));
        val.set("orderName", OrderMaxUtility.getValidEntityString(this.orderName));
        val.set("currencyUom", OrderMaxUtility.getValidEntityString(this.currencyUom));
        val.set("visitId", OrderMaxUtility.getValidEntityString(this.visitId));
        val.set("externalId", OrderMaxUtility.getValidEntityString(this.externalId));
        val.set("terminalId", OrderMaxUtility.getValidEntityString(this.terminalId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("remainingSubTotal", OrderMaxUtility.getValidEntityBigDecimal(this.remainingSubTotal));
        val.set("orderTypeId", OrderMaxUtility.getValidEntityString(this.orderTypeId));
        val.set("priority", OrderMaxUtility.getValidEntityString(this.priority));
        val.set("isViewed", OrderMaxUtility.getValidEntityString(this.isViewed));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("orderDate", OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
        val.set("grandTotal", OrderMaxUtility.getValidEntityBigDecimal(this.grandTotal));
        val.set("internalCode", OrderMaxUtility.getValidEntityString(this.internalCode));
        val.set("entryDate", OrderMaxUtility.getValidEntityTimestamp(this.entryDate));
        val.set("syncStatusId", OrderMaxUtility.getValidEntityString(this.syncStatusId));
        val.set("roleTypeId", OrderMaxUtility.getValidEntityString(this.roleTypeId));
        val.set("createdBy", OrderMaxUtility.getValidEntityString(this.createdBy));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("autoOrderShoppingListId", OrderMaxUtility.getValidEntityString(this.autoOrderShoppingListId));
        val.set("isRushOrder", OrderMaxUtility.getValidEntityString(this.isRushOrder));
        val.set("firstAttemptOrderId", OrderMaxUtility.getValidEntityString(this.firstAttemptOrderId));
        val.set("webSiteId", OrderMaxUtility.getValidEntityString(this.webSiteId));
        val.set("salesChannelEnumId", OrderMaxUtility.getValidEntityString(this.salesChannelEnumId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("transactionId", this.transactionId);
        valueMap.put("productStoreId", this.productStoreId);
        valueMap.put("originFacilityId", this.originFacilityId);
        valueMap.put("needsInventoryIssuance", this.needsInventoryIssuance);
        valueMap.put("invoicePerShipment", this.invoicePerShipment);
        valueMap.put("pickSheetPrintedDate", this.pickSheetPrintedDate);
        valueMap.put("orderName", this.orderName);
        valueMap.put("currencyUom", this.currencyUom);
        valueMap.put("visitId", this.visitId);
        valueMap.put("externalId", this.externalId);
        valueMap.put("terminalId", this.terminalId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("remainingSubTotal", this.remainingSubTotal);
        valueMap.put("orderTypeId", this.orderTypeId);
        valueMap.put("priority", this.priority);
        valueMap.put("isViewed", this.isViewed);
        valueMap.put("orderId", this.orderId);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("orderDate", this.orderDate);
        valueMap.put("grandTotal", this.grandTotal);
        valueMap.put("internalCode", this.internalCode);
        valueMap.put("entryDate", this.entryDate);
        valueMap.put("syncStatusId", this.syncStatusId);
        valueMap.put("roleTypeId", this.roleTypeId);
        valueMap.put("createdBy", this.createdBy);
        valueMap.put("partyId", this.partyId);
        valueMap.put("autoOrderShoppingListId", this.autoOrderShoppingListId);
        valueMap.put("isRushOrder", this.isRushOrder);
        valueMap.put("firstAttemptOrderId", this.firstAttemptOrderId);
        valueMap.put("webSiteId", this.webSiteId);
        valueMap.put("salesChannelEnumId", this.salesChannelEnumId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderHeaderAndRoles");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String transactionId;

    public java.lang.String gettransactionId() {
        return transactionId;
    }

    public void settransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }
    private java.lang.String productStoreId;

    public java.lang.String getproductStoreId() {
        return productStoreId;
    }

    public void setproductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
    }
    private java.lang.String originFacilityId;

    public java.lang.String getoriginFacilityId() {
        return originFacilityId;
    }

    public void setoriginFacilityId(java.lang.String originFacilityId) {
        this.originFacilityId = originFacilityId;
    }
    private java.lang.String needsInventoryIssuance;

    public java.lang.String getneedsInventoryIssuance() {
        return needsInventoryIssuance;
    }

    public void setneedsInventoryIssuance(java.lang.String needsInventoryIssuance) {
        this.needsInventoryIssuance = needsInventoryIssuance;
    }
    private java.lang.String invoicePerShipment;

    public java.lang.String getinvoicePerShipment() {
        return invoicePerShipment;
    }

    public void setinvoicePerShipment(java.lang.String invoicePerShipment) {
        this.invoicePerShipment = invoicePerShipment;
    }
    private java.sql.Timestamp pickSheetPrintedDate;

    public java.sql.Timestamp getpickSheetPrintedDate() {
        return pickSheetPrintedDate;
    }

    public void setpickSheetPrintedDate(java.sql.Timestamp pickSheetPrintedDate) {
        this.pickSheetPrintedDate = pickSheetPrintedDate;
    }
    private java.lang.String orderName;

    public java.lang.String getorderName() {
        return orderName;
    }

    public void setorderName(java.lang.String orderName) {
        this.orderName = orderName;
    }
    private java.lang.String currencyUom;

    public java.lang.String getcurrencyUom() {
        return currencyUom;
    }

    public void setcurrencyUom(java.lang.String currencyUom) {
        this.currencyUom = currencyUom;
    }
    private java.lang.String visitId;

    public java.lang.String getvisitId() {
        return visitId;
    }

    public void setvisitId(java.lang.String visitId) {
        this.visitId = visitId;
    }
    private java.lang.String externalId;

    public java.lang.String getexternalId() {
        return externalId;
    }

    public void setexternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    private java.lang.String terminalId;

    public java.lang.String getterminalId() {
        return terminalId;
    }

    public void setterminalId(java.lang.String terminalId) {
        this.terminalId = terminalId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.math.BigDecimal remainingSubTotal;

    public java.math.BigDecimal getremainingSubTotal() {
        return remainingSubTotal;
    }

    public void setremainingSubTotal(java.math.BigDecimal remainingSubTotal) {
        this.remainingSubTotal = remainingSubTotal;
    }
    private java.lang.String orderTypeId;

    public java.lang.String getorderTypeId() {
        return orderTypeId;
    }

    public void setorderTypeId(java.lang.String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
    private java.lang.String priority;

    public java.lang.String getpriority() {
        return priority;
    }

    public void setpriority(java.lang.String priority) {
        this.priority = priority;
    }
    private java.lang.String isViewed;

    public java.lang.String getisViewed() {
        return isViewed;
    }

    public void setisViewed(java.lang.String isViewed) {
        this.isViewed = isViewed;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getbillingAccountId() {
        return billingAccountId;
    }

    public void setbillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
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
    private java.lang.String internalCode;

    public java.lang.String getinternalCode() {
        return internalCode;
    }

    public void setinternalCode(java.lang.String internalCode) {
        this.internalCode = internalCode;
    }
    private java.sql.Timestamp entryDate;

    public java.sql.Timestamp getentryDate() {
        return entryDate;
    }

    public void setentryDate(java.sql.Timestamp entryDate) {
        this.entryDate = entryDate;
    }
    private java.lang.String syncStatusId;

    public java.lang.String getsyncStatusId() {
        return syncStatusId;
    }

    public void setsyncStatusId(java.lang.String syncStatusId) {
        this.syncStatusId = syncStatusId;
    }
    private java.lang.String roleTypeId;

    public java.lang.String getroleTypeId() {
        return roleTypeId;
    }

    public void setroleTypeId(java.lang.String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    private java.lang.String createdBy;

    public java.lang.String getcreatedBy() {
        return createdBy;
    }

    public void setcreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String autoOrderShoppingListId;

    public java.lang.String getautoOrderShoppingListId() {
        return autoOrderShoppingListId;
    }

    public void setautoOrderShoppingListId(java.lang.String autoOrderShoppingListId) {
        this.autoOrderShoppingListId = autoOrderShoppingListId;
    }
    private java.lang.String isRushOrder;

    public java.lang.String getisRushOrder() {
        return isRushOrder;
    }

    public void setisRushOrder(java.lang.String isRushOrder) {
        this.isRushOrder = isRushOrder;
    }
    private java.lang.String firstAttemptOrderId;

    public java.lang.String getfirstAttemptOrderId() {
        return firstAttemptOrderId;
    }

    public void setfirstAttemptOrderId(java.lang.String firstAttemptOrderId) {
        this.firstAttemptOrderId = firstAttemptOrderId;
    }
    private java.lang.String webSiteId;

    public java.lang.String getwebSiteId() {
        return webSiteId;
    }

    public void setwebSiteId(java.lang.String webSiteId) {
        this.webSiteId = webSiteId;
    }
    private java.lang.String salesChannelEnumId;

    public java.lang.String getsalesChannelEnumId() {
        return salesChannelEnumId;
    }

    public void setsalesChannelEnumId(java.lang.String salesChannelEnumId) {
        this.salesChannelEnumId = salesChannelEnumId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderAndRoles> objectList = new ArrayList<OrderHeaderAndRoles>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderAndRoles(genVal));
        }
        return objectList;
    }
}


       