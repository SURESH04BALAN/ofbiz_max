package org.ofbiz.ordermax.entity;

import java.text.SimpleDateFormat;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class OrderHeader implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = OrderHeader.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderHeader(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderHeader() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"orderId", "Order Id"},
        {"orderTypeId", "Order Type Id"},
        {"orderName", "Order Name"},
        {"externalId", "External Id"},
        {"salesChannelEnumId", "Sales Channel Enum Id"},
        {"orderDate", "Order Date"},
        {"priority", "Priority"},
        {"entryDate", "Entry Date"},
        {"pickSheetPrintedDate", "Pick Sheet Printed Date"},
        {"visitId", "Visit Id"},
        {"statusId", "Status Id"},
        {"createdBy", "Created By"},
        {"firstAttemptOrderId", "First Attempt Order Id"},
        {"currencyUom", "Currency Uom"},
        {"syncStatusId", "Sync Status Id"},
        {"billingAccountId", "Billing Account Id"},
        {"originFacilityId", "Origin Facility Id"},
        {"webSiteId", "Web Site Id"},
        {"productStoreId", "Product Store Id"},
        {"terminalId", "Terminal Id"},
        {"transactionId", "Transaction Id"},
        {"autoOrderShoppingListId", "Auto Order Shopping List Id"},
        {"needsInventoryIssuance", "Needs Inventory Issuance"},
        {"isRushOrder", "Is Rush Order"},
        {"internalCode", "Internal Code"},
        {"remainingSubTotal", "Remaining Sub Total"},
        {"grandTotal", "Grand Total"},
        {"isViewed", "Is Viewed"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.setOrderId("");
        this.setOrderTypeId("");
        this.setOrderName("");
        this.setExternalId("");
        this.setSalesChannelEnumId("");
        this.setOrderDate(UtilDateTime.nowTimestamp());
        this.setPriority("");
        this.setEntryDate(UtilDateTime.nowTimestamp());
        this.setPickSheetPrintedDate(UtilDateTime.nowTimestamp());
        this.setVisitId("");
        this.setStatusId("");
        this.setCreatedBy("");
        this.setFirstAttemptOrderId("");
        this.setCurrencyUom("");
        this.setSyncStatusId("");
        this.setBillingAccountId("");
        this.setOriginFacilityId("");
        this.setWebSiteId("");
        this.setProductStoreId("");
        this.setTerminalId("");
        this.setTransactionId("");
        this.setAutoOrderShoppingListId("");
        this.setNeedsInventoryIssuance("");
        this.setIsRushOrder("");
        this.setInternalCode("");
        this.setRemainingSubTotal(java.math.BigDecimal.ZERO);
        this.setGrandTotal(java.math.BigDecimal.ZERO);
        this.setIsViewed("");
        this.setLastUpdatedStamp(UtilDateTime.nowTimestamp());
        this.setLastUpdatedTxStamp(UtilDateTime.nowTimestamp());
        this.setCreatedStamp(UtilDateTime.nowTimestamp());
        this.setCreatedTxStamp(UtilDateTime.nowTimestamp());
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.setOrderId((java.lang.String) genVal.get("orderId"));
        this.setOrderTypeId((java.lang.String) genVal.get("orderTypeId"));
        this.setOrderName((java.lang.String) genVal.get("orderName"));
        this.setExternalId((java.lang.String) genVal.get("externalId"));
        this.setSalesChannelEnumId((java.lang.String) genVal.get("salesChannelEnumId"));
        this.setOrderDate((java.sql.Timestamp) genVal.get("orderDate"));
        this.setPriority((java.lang.String) genVal.get("priority"));
        this.setEntryDate((java.sql.Timestamp) genVal.get("entryDate"));
        this.setPickSheetPrintedDate((java.sql.Timestamp) genVal.get("pickSheetPrintedDate"));
        this.setVisitId((java.lang.String) genVal.get("visitId"));
        this.setStatusId((java.lang.String) genVal.get("statusId"));
        this.setCreatedBy((java.lang.String) genVal.get("createdBy"));
        this.setFirstAttemptOrderId((java.lang.String) genVal.get("firstAttemptOrderId"));
        this.setCurrencyUom((java.lang.String) genVal.get("currencyUom"));
        this.setSyncStatusId((java.lang.String) genVal.get("syncStatusId"));
        this.setBillingAccountId((java.lang.String) genVal.get("billingAccountId"));
        this.setOriginFacilityId((java.lang.String) genVal.get("originFacilityId"));
        this.setWebSiteId((java.lang.String) genVal.get("webSiteId"));
        this.setProductStoreId((java.lang.String) genVal.get("productStoreId"));
        this.setTerminalId((java.lang.String) genVal.get("terminalId"));
        this.setTransactionId((java.lang.String) genVal.get("transactionId"));
        this.setAutoOrderShoppingListId((java.lang.String) genVal.get("autoOrderShoppingListId"));
        this.setNeedsInventoryIssuance((java.lang.String) genVal.get("needsInventoryIssuance"));
        this.setIsRushOrder((java.lang.String) genVal.get("isRushOrder"));
        this.setInternalCode((java.lang.String) genVal.get("internalCode"));
        this.setRemainingSubTotal((java.math.BigDecimal) genVal.get("remainingSubTotal"));
        this.setGrandTotal((java.math.BigDecimal) genVal.get("grandTotal"));
        this.setIsViewed((java.lang.String) genVal.get("isViewed"));
        this.setLastUpdatedStamp((java.sql.Timestamp) genVal.get("lastUpdatedStamp"));
        this.setLastUpdatedTxStamp((java.sql.Timestamp) genVal.get("lastUpdatedTxStamp"));
        this.setCreatedStamp((java.sql.Timestamp) genVal.get("createdStamp"));
        this.setCreatedTxStamp((java.sql.Timestamp) genVal.get("createdTxStamp"));
    }

    protected void getGenericValue(GenericValue val) {
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.getOrderId()));
        val.set("orderTypeId", OrderMaxUtility.getValidEntityString(this.getOrderTypeId()));
        val.set("orderName", OrderMaxUtility.getValidEntityString(this.getOrderName()));
        val.set("externalId", OrderMaxUtility.getValidEntityString(this.getExternalId()));
        val.set("salesChannelEnumId", OrderMaxUtility.getValidEntityString(this.getSalesChannelEnumId()));
        val.set("orderDate", OrderMaxUtility.getValidEntityTimestamp(this.getOrderDate()));
        val.set("priority", OrderMaxUtility.getValidEntityString(this.getPriority()));
        val.set("entryDate", OrderMaxUtility.getValidEntityTimestamp(this.getEntryDate()));
        val.set("pickSheetPrintedDate", OrderMaxUtility.getValidEntityTimestamp(this.getPickSheetPrintedDate()));
        val.set("visitId", OrderMaxUtility.getValidEntityString(this.getVisitId()));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.getStatusId()));
        val.set("createdBy", OrderMaxUtility.getValidEntityString(this.getCreatedBy()));
        val.set("firstAttemptOrderId", OrderMaxUtility.getValidEntityString(this.getFirstAttemptOrderId()));
        val.set("currencyUom", OrderMaxUtility.getValidEntityString(this.getCurrencyUom()));
        val.set("syncStatusId", OrderMaxUtility.getValidEntityString(this.getSyncStatusId()));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.getBillingAccountId()));
        val.set("originFacilityId", OrderMaxUtility.getValidEntityString(this.getOriginFacilityId()));
        val.set("webSiteId", OrderMaxUtility.getValidEntityString(this.getWebSiteId()));
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.getProductStoreId()));
        val.set("terminalId", OrderMaxUtility.getValidEntityString(this.getTerminalId()));
        val.set("transactionId", OrderMaxUtility.getValidEntityString(this.getTransactionId()));
        val.set("autoOrderShoppingListId", OrderMaxUtility.getValidEntityString(this.getAutoOrderShoppingListId()));
        val.set("needsInventoryIssuance", OrderMaxUtility.getValidEntityString(this.getNeedsInventoryIssuance()));
        val.set("isRushOrder", OrderMaxUtility.getValidEntityString(this.getIsRushOrder()));
        val.set("internalCode", OrderMaxUtility.getValidEntityString(this.getInternalCode()));
        val.set("remainingSubTotal", this.getRemainingSubTotal());
        val.set("grandTotal", this.getGrandTotal());
        val.set("isViewed", OrderMaxUtility.getValidEntityString(this.getIsViewed()));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.getLastUpdatedStamp()));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.getLastUpdatedTxStamp()));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.getCreatedStamp()));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.getCreatedTxStamp()));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public GenericValue createNewGenericValueObj(Delegator delegator) {
        genVal = delegator.makeValue("OrderHeader");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }

    protected java.lang.String orderId;

    protected java.lang.String orderTypeId;

    protected java.lang.String orderName;

    protected java.lang.String externalId;

    protected java.lang.String salesChannelEnumId;

    protected java.sql.Timestamp orderDate;

    protected java.lang.String priority;

    protected java.sql.Timestamp entryDate;

    protected java.sql.Timestamp pickSheetPrintedDate;

    protected java.lang.String visitId;

    protected java.lang.String statusId;

    protected java.lang.String createdBy;

    protected java.lang.String firstAttemptOrderId;

    protected java.lang.String currencyUom;

    protected java.lang.String syncStatusId;

    protected java.lang.String billingAccountId;

    protected java.lang.String originFacilityId;

    protected java.lang.String webSiteId;

    protected java.lang.String productStoreId;

    protected java.lang.String terminalId;

    protected java.lang.String transactionId;

    protected java.lang.String autoOrderShoppingListId;

    protected java.lang.String needsInventoryIssuance;

    protected java.lang.String isRushOrder;

    protected java.lang.String internalCode;

    protected java.math.BigDecimal remainingSubTotal;

    protected java.math.BigDecimal grandTotal;

    protected java.lang.String isViewed;

    protected java.sql.Timestamp lastUpdatedStamp;

    protected java.sql.Timestamp lastUpdatedTxStamp;

    protected java.sql.Timestamp createdStamp;

    protected java.sql.Timestamp createdTxStamp;

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    /**
     * @return the orderId
     */
    public java.lang.String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderTypeId
     */
    public java.lang.String getOrderTypeId() {
        return orderTypeId;
    }

    /**
     * @param orderTypeId the orderTypeId to set
     */
    public void setOrderTypeId(java.lang.String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    /**
     * @return the orderName
     */
    public java.lang.String getOrderName() {
        return orderName;
    }

    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(java.lang.String orderName) {
        this.orderName = orderName;
    }

    /**
     * @return the externalId
     */
    public java.lang.String getExternalId() {
        return externalId;
    }

    /**
     * @param externalId the externalId to set
     */
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }

    /**
     * @return the salesChannelEnumId
     */
    public java.lang.String getSalesChannelEnumId() {
        return salesChannelEnumId;
    }

    /**
     * @param salesChannelEnumId the salesChannelEnumId to set
     */
    public void setSalesChannelEnumId(java.lang.String salesChannelEnumId) {
        this.salesChannelEnumId = salesChannelEnumId;
    }

    /**
     * @return the orderDate
     */
    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the priority
     */
    public java.lang.String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }

    /**
     * @return the entryDate
     */
    public java.sql.Timestamp getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(java.sql.Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the pickSheetPrintedDate
     */
    public java.sql.Timestamp getPickSheetPrintedDate() {
        return pickSheetPrintedDate;
    }

    /**
     * @param pickSheetPrintedDate the pickSheetPrintedDate to set
     */
    public void setPickSheetPrintedDate(java.sql.Timestamp pickSheetPrintedDate) {
        this.pickSheetPrintedDate = pickSheetPrintedDate;
    }

    /**
     * @return the visitId
     */
    public java.lang.String getVisitId() {
        return visitId;
    }

    /**
     * @param visitId the visitId to set
     */
    public void setVisitId(java.lang.String visitId) {
        this.visitId = visitId;
    }

    /**
     * @return the statusId
     */
    public java.lang.String getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the createdBy
     */
    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the firstAttemptOrderId
     */
    public java.lang.String getFirstAttemptOrderId() {
        return firstAttemptOrderId;
    }

    /**
     * @param firstAttemptOrderId the firstAttemptOrderId to set
     */
    public void setFirstAttemptOrderId(java.lang.String firstAttemptOrderId) {
        this.firstAttemptOrderId = firstAttemptOrderId;
    }

    /**
     * @return the currencyUom
     */
    public java.lang.String getCurrencyUom() {
        return currencyUom;
    }

    /**
     * @param currencyUom the currencyUom to set
     */
    public void setCurrencyUom(java.lang.String currencyUom) {
        this.currencyUom = currencyUom;
    }

    /**
     * @return the syncStatusId
     */
    public java.lang.String getSyncStatusId() {
        return syncStatusId;
    }

    /**
     * @param syncStatusId the syncStatusId to set
     */
    public void setSyncStatusId(java.lang.String syncStatusId) {
        this.syncStatusId = syncStatusId;
    }

    /**
     * @return the billingAccountId
     */
    public java.lang.String getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * @param billingAccountId the billingAccountId to set
     */
    public void setBillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * @return the originFacilityId
     */
    public java.lang.String getOriginFacilityId() {
        return originFacilityId;
    }

    /**
     * @param originFacilityId the originFacilityId to set
     */
    public void setOriginFacilityId(java.lang.String originFacilityId) {
        this.originFacilityId = originFacilityId;
    }

    /**
     * @return the webSiteId
     */
    public java.lang.String getWebSiteId() {
        return webSiteId;
    }

    /**
     * @param webSiteId the webSiteId to set
     */
    public void setWebSiteId(java.lang.String webSiteId) {
        this.webSiteId = webSiteId;
    }

    /**
     * @return the productStoreId
     */
    public java.lang.String getProductStoreId() {
        return productStoreId;
    }

    /**
     * @param productStoreId the productStoreId to set
     */
    public void setProductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
    }

    /**
     * @return the terminalId
     */
    public java.lang.String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalId the terminalId to set
     */
    public void setTerminalId(java.lang.String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * @return the transactionId
     */
    public java.lang.String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(java.lang.String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the autoOrderShoppingListId
     */
    public java.lang.String getAutoOrderShoppingListId() {
        return autoOrderShoppingListId;
    }

    /**
     * @param autoOrderShoppingListId the autoOrderShoppingListId to set
     */
    public void setAutoOrderShoppingListId(java.lang.String autoOrderShoppingListId) {
        this.autoOrderShoppingListId = autoOrderShoppingListId;
    }

    /**
     * @return the needsInventoryIssuance
     */
    public java.lang.String getNeedsInventoryIssuance() {
        return needsInventoryIssuance;
    }

    /**
     * @param needsInventoryIssuance the needsInventoryIssuance to set
     */
    public void setNeedsInventoryIssuance(java.lang.String needsInventoryIssuance) {
        this.needsInventoryIssuance = needsInventoryIssuance;
    }

    /**
     * @return the isRushOrder
     */
    public java.lang.String getIsRushOrder() {
        return isRushOrder;
    }

    /**
     * @param isRushOrder the isRushOrder to set
     */
    public void setIsRushOrder(java.lang.String isRushOrder) {
        this.isRushOrder = isRushOrder;
    }

    /**
     * @return the internalCode
     */
    public java.lang.String getInternalCode() {
        return internalCode;
    }

    /**
     * @param internalCode the internalCode to set
     */
    public void setInternalCode(java.lang.String internalCode) {
        this.internalCode = internalCode;
    }

    /**
     * @return the remainingSubTotal
     */
    public java.math.BigDecimal getRemainingSubTotal() {
        return remainingSubTotal;
    }

    /**
     * @param remainingSubTotal the remainingSubTotal to set
     */
    public void setRemainingSubTotal(java.math.BigDecimal remainingSubTotal) {
        this.remainingSubTotal = remainingSubTotal;
    }

    /**
     * @return the grandTotal
     */
    public java.math.BigDecimal getGrandTotal() {
        return grandTotal;
    }

    /**
     * @param grandTotal the grandTotal to set
     */
    public void setGrandTotal(java.math.BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * @return the isViewed
     */
    public java.lang.String getIsViewed() {
        return isViewed;
    }

    /**
     * @param isViewed the isViewed to set
     */
    public void setIsViewed(java.lang.String isViewed) {
        this.isViewed = isViewed;
    }

    /**
     * @return the lastUpdatedStamp
     */
    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    /**
     * @param lastUpdatedStamp the lastUpdatedStamp to set
     */
    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    /**
     * @return the lastUpdatedTxStamp
     */
    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    /**
     * @param lastUpdatedTxStamp the lastUpdatedTxStamp to set
     */
    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    /**
     * @return the createdStamp
     */
    public java.sql.Timestamp getCreatedStamp() {
        return createdStamp;
    }

    /**
     * @param createdStamp the createdStamp to set
     */
    public void setCreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }

    /**
     * @return the createdTxStamp
     */
    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    /**
     * @param createdTxStamp the createdTxStamp to set
     */
    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getOrderName());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getOrderId());
                orderToStringBuilder.append(" ]");
            }
        } else if (DisplayTypes.SHOW_CODE_AND_TIME.equals(showKey)) {
            orderToStringBuilder.append(getOrderId() + " - " + new SimpleDateFormat("dd/MM/yyyy : HH:MM:SS").format(new java.util.Date(this.getOrderDate().getTime())));
        } else {
            orderToStringBuilder.append(getOrderId());
        }

        return orderToStringBuilder.toString();
    }
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
