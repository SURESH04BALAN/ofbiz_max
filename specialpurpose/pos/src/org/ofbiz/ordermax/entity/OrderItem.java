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

public class OrderItem implements GenericValueObjectInterface {

    public static final String module = OrderItem.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderItem(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderItem() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"isItemGroupPrimary", "Is Item Group Primary"},
        {"unitPrice", "Unit Price"},
        {"changeByUserLoginId", "Change By User Login Id"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"dontCancelSetUserLogin", "Dont Cancel Set User Login"},
        {"createdStamp", "Created Stamp"},
        {"isModifiedPrice", "Is Modified Price"},
        {"quantity", "Quantity"},
        {"shoppingListItemSeqId", "Shopping List Item Seq Id"},
        {"quoteId", "Quote Id"},
        {"unitListPrice", "Unit List Price"},
        {"deploymentId", "Deployment Id"},
        {"recurringFreqUomId", "Recurring Freq Uom Id"},
        {"shipBeforeDate", "Ship Before Date"},
        {"orderItemSeqId", "Order Item Seq Id"},
        {"productFeatureId", "Product Feature Id"},
        {"shoppingListId", "Shopping List Id"},
        {"prodCatalogId", "Prod Catalog Id"},
        {"orderItemGroupSeqId", "Order Item Group Seq Id"},
        {"productCategoryId", "Product Category Id"},
        {"supplierProductId", "Supplier Product Id"},
        {"budgetId", "Budget Id"},
        {"unitAverageCost", "Unit Average Cost"},
        {"correspondingPoId", "Corresponding Po Id"},
        {"externalId", "External Id"},
        {"salesOpportunityId", "Sales Opportunity Id"},
        {"subscriptionId", "Subscription Id"},
        {"statusId", "Status Id"},
        {"budgetItemSeqId", "Budget Item Seq Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"quoteItemSeqId", "Quote Item Seq Id"},
        {"shipAfterDate", "Ship After Date"},
        {"orderId", "Order Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"fromInventoryItemId", "From Inventory Item Id"},
        {"orderItemTypeId", "Order Item Type Id"},
        {"estimatedDeliveryDate", "Estimated Delivery Date"},
        {"isPromo", "Is Promo"},
        {"productId", "Product Id"},
        {"dontCancelSetDate", "Dont Cancel Set Date"},
        {"syncStatusId", "Sync Status Id"},
        {"itemDescription", "Item Description"},
        {"unitRecurringPrice", "Unit Recurring Price"},
        {"selectedAmount", "Selected Amount"},
        {"cancelBackOrderDate", "Cancel Back Order Date"},
        {"estimatedShipDate", "Estimated Ship Date"},
        {"autoCancelDate", "Auto Cancel Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"cancelQuantity", "Cancel Quantity"},
        {"comments", "Comments"},};

    protected void initObject() {
        this.isItemGroupPrimary = "";
        this.unitPrice = java.math.BigDecimal.ZERO;
        this.changeByUserLoginId = "";
        this.overrideGlAccountId = "";
        this.dontCancelSetUserLogin = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.isModifiedPrice = "";
        this.quantity = java.math.BigDecimal.ZERO;
        this.shoppingListItemSeqId = "";
        this.quoteId = "";
        this.unitListPrice = java.math.BigDecimal.ZERO;
        this.deploymentId = "";
        this.recurringFreqUomId = "";
        this.shipBeforeDate = UtilDateTime.nowTimestamp();
        this.orderItemSeqId = "";
        this.productFeatureId = "";
        this.shoppingListId = "";
        this.prodCatalogId = "";
        this.orderItemGroupSeqId = "";
        this.productCategoryId = "";
        this.supplierProductId = "";
        this.budgetId = "";
        this.unitAverageCost = java.math.BigDecimal.ZERO;
        this.correspondingPoId = "";
        this.externalId = "";
        this.salesOpportunityId = "";
        this.subscriptionId = "";
        this.statusId = "";
        this.budgetItemSeqId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.quoteItemSeqId = "";
        this.shipAfterDate = UtilDateTime.nowTimestamp();
        this.orderId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.fromInventoryItemId = "";
        this.orderItemTypeId = "";
        this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
        this.isPromo = "";
        this.productId = "";
        this.dontCancelSetDate = UtilDateTime.nowTimestamp();
        this.syncStatusId = "";
        this.itemDescription = "";
        this.unitRecurringPrice = "";
        this.selectedAmount = java.math.BigDecimal.ZERO;
        this.cancelBackOrderDate = UtilDateTime.nowTimestamp();
        this.estimatedShipDate = UtilDateTime.nowTimestamp();
        this.autoCancelDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.cancelQuantity = "";
        this.comments = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.isItemGroupPrimary = (java.lang.String) genVal.get("isItemGroupPrimary");
        this.unitPrice = (java.math.BigDecimal) genVal.get("unitPrice");
        this.changeByUserLoginId = (java.lang.String) genVal.get("changeByUserLoginId");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.dontCancelSetUserLogin = (java.lang.String) genVal.get("dontCancelSetUserLogin");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.isModifiedPrice = (java.lang.String) genVal.get("isModifiedPrice");
        this.quantity = (java.math.BigDecimal) genVal.get("quantity");
        this.shoppingListItemSeqId = (java.lang.String) genVal.get("shoppingListItemSeqId");
        this.quoteId = (java.lang.String) genVal.get("quoteId");
        this.unitListPrice = (java.math.BigDecimal) genVal.get("unitListPrice");
        this.deploymentId = (java.lang.String) genVal.get("deploymentId");
        this.recurringFreqUomId = (java.lang.String) genVal.get("recurringFreqUomId");
        this.shipBeforeDate = (java.sql.Timestamp) genVal.get("shipBeforeDate");
        this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
        this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
        this.orderItemGroupSeqId = (java.lang.String) genVal.get("orderItemGroupSeqId");
        this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
        this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
        this.budgetId = (java.lang.String) genVal.get("budgetId");
        this.unitAverageCost = (java.math.BigDecimal) genVal.get("unitAverageCost");
        this.correspondingPoId = (java.lang.String) genVal.get("correspondingPoId");
        this.externalId = (java.lang.String) genVal.get("externalId");
        this.salesOpportunityId = (java.lang.String) genVal.get("salesOpportunityId");
        this.subscriptionId = (java.lang.String) genVal.get("subscriptionId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.budgetItemSeqId = (java.lang.String) genVal.get("budgetItemSeqId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.quoteItemSeqId = (java.lang.String) genVal.get("quoteItemSeqId");
        this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.fromInventoryItemId = (java.lang.String) genVal.get("fromInventoryItemId");
        this.orderItemTypeId = (java.lang.String) genVal.get("orderItemTypeId");
        this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
        this.isPromo = (java.lang.String) genVal.get("isPromo");
        this.productId = (java.lang.String) genVal.get("productId");
        this.dontCancelSetDate = (java.sql.Timestamp) genVal.get("dontCancelSetDate");
        this.syncStatusId = (java.lang.String) genVal.get("syncStatusId");
        this.itemDescription = (java.lang.String) genVal.get("itemDescription");
        this.unitRecurringPrice = (java.lang.String) genVal.get("unitRecurringPrice");
        this.selectedAmount = (java.math.BigDecimal) genVal.get("selectedAmount");
        this.cancelBackOrderDate = (java.sql.Timestamp) genVal.get("cancelBackOrderDate");
        this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
        this.autoCancelDate = (java.sql.Timestamp) genVal.get("autoCancelDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.cancelQuantity = (java.lang.String) genVal.get("cancelQuantity");
        this.comments = (java.lang.String) genVal.get("comments");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("isItemGroupPrimary", OrderMaxUtility.getValidEntityString(this.isItemGroupPrimary));
        val.set("unitPrice", OrderMaxUtility.getValidEntityBigDecimal(this.unitPrice));
        val.set("changeByUserLoginId", OrderMaxUtility.getValidEntityString(this.changeByUserLoginId));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("dontCancelSetUserLogin", OrderMaxUtility.getValidEntityString(this.dontCancelSetUserLogin));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("isModifiedPrice", OrderMaxUtility.getValidEntityString(this.isModifiedPrice));
        val.set("quantity", OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
        val.set("shoppingListItemSeqId", OrderMaxUtility.getValidEntityString(this.shoppingListItemSeqId));
        val.set("quoteId", OrderMaxUtility.getValidEntityString(this.quoteId));
        val.set("unitListPrice", OrderMaxUtility.getValidEntityBigDecimal(this.unitListPrice));
        val.set("deploymentId", OrderMaxUtility.getValidEntityString(this.deploymentId));
        val.set("recurringFreqUomId", OrderMaxUtility.getValidEntityString(this.recurringFreqUomId));
        val.set("shipBeforeDate", OrderMaxUtility.getValidEntityTimestamp(this.shipBeforeDate));
        val.set("orderItemSeqId", OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("shoppingListId", OrderMaxUtility.getValidEntityString(this.shoppingListId));
        val.set("prodCatalogId", OrderMaxUtility.getValidEntityString(this.prodCatalogId));
        val.set("orderItemGroupSeqId", OrderMaxUtility.getValidEntityString(this.orderItemGroupSeqId));
        val.set("productCategoryId", OrderMaxUtility.getValidEntityString(this.productCategoryId));
        val.set("supplierProductId", OrderMaxUtility.getValidEntityString(this.supplierProductId));
        val.set("budgetId", OrderMaxUtility.getValidEntityString(this.budgetId));
        val.set("unitAverageCost", OrderMaxUtility.getValidBigDecimal(this.unitAverageCost));
        val.set("correspondingPoId", OrderMaxUtility.getValidEntityString(this.correspondingPoId));
        val.set("externalId", OrderMaxUtility.getValidEntityString(this.externalId));
        val.set("salesOpportunityId", OrderMaxUtility.getValidEntityString(this.salesOpportunityId));
        val.set("subscriptionId", OrderMaxUtility.getValidEntityString(this.subscriptionId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("budgetItemSeqId", OrderMaxUtility.getValidEntityString(this.budgetItemSeqId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("quoteItemSeqId", OrderMaxUtility.getValidEntityString(this.quoteItemSeqId));
        val.set("shipAfterDate", OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("fromInventoryItemId", OrderMaxUtility.getValidEntityString(this.fromInventoryItemId));
        val.set("orderItemTypeId", OrderMaxUtility.getValidEntityString(this.orderItemTypeId));
        val.set("estimatedDeliveryDate", OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
        val.set("isPromo", OrderMaxUtility.getValidEntityString(this.isPromo));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("dontCancelSetDate", OrderMaxUtility.getValidEntityTimestamp(this.dontCancelSetDate));
        val.set("syncStatusId", OrderMaxUtility.getValidEntityString(this.syncStatusId));
        val.set("itemDescription", OrderMaxUtility.getValidEntityString(this.itemDescription));
        val.set("unitRecurringPrice", OrderMaxUtility.getValidEntityString(this.unitRecurringPrice));
        val.set("selectedAmount", OrderMaxUtility.getValidEntityBigDecimal(this.selectedAmount));
        val.set("cancelBackOrderDate", OrderMaxUtility.getValidEntityTimestamp(this.cancelBackOrderDate));
        val.set("estimatedShipDate", OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
        val.set("autoCancelDate", OrderMaxUtility.getValidEntityTimestamp(this.autoCancelDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("cancelQuantity", OrderMaxUtility.getValidEntityString(this.cancelQuantity));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("isItemGroupPrimary", this.isItemGroupPrimary);
        valueMap.put("unitPrice", this.unitPrice);
        valueMap.put("changeByUserLoginId", this.changeByUserLoginId);
        valueMap.put("overrideGlAccountId", this.overrideGlAccountId);
        valueMap.put("dontCancelSetUserLogin", this.dontCancelSetUserLogin);
        valueMap.put("isModifiedPrice", this.isModifiedPrice);
        valueMap.put("quantity", this.quantity);
        valueMap.put("shoppingListItemSeqId", this.shoppingListItemSeqId);
        valueMap.put("quoteId", this.quoteId);
        valueMap.put("unitListPrice", this.unitListPrice);
        valueMap.put("deploymentId", this.deploymentId);
        valueMap.put("recurringFreqUomId", this.recurringFreqUomId);
        valueMap.put("shipBeforeDate", this.shipBeforeDate);
        valueMap.put("orderItemSeqId", this.orderItemSeqId);
        valueMap.put("productFeatureId", this.productFeatureId);
        valueMap.put("shoppingListId", this.shoppingListId);
        valueMap.put("prodCatalogId", this.prodCatalogId);
        valueMap.put("orderItemGroupSeqId", this.orderItemGroupSeqId);
        valueMap.put("productCategoryId", this.productCategoryId);
        valueMap.put("supplierProductId", this.supplierProductId);
        valueMap.put("budgetId", this.budgetId);
        valueMap.put("unitAverageCost", this.unitAverageCost);
        valueMap.put("correspondingPoId", this.correspondingPoId);
        valueMap.put("externalId", this.externalId);
        valueMap.put("salesOpportunityId", this.salesOpportunityId);
        valueMap.put("subscriptionId", this.subscriptionId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("budgetItemSeqId", this.budgetItemSeqId);
        valueMap.put("quoteItemSeqId", this.quoteItemSeqId);
        valueMap.put("shipAfterDate", this.shipAfterDate);
        valueMap.put("orderId", this.orderId);
        valueMap.put("fromInventoryItemId", this.fromInventoryItemId);
        valueMap.put("orderItemTypeId", this.orderItemTypeId);
        valueMap.put("estimatedDeliveryDate", this.estimatedDeliveryDate);
        valueMap.put("isPromo", this.isPromo);
        valueMap.put("productId", this.productId);
        valueMap.put("dontCancelSetDate", this.dontCancelSetDate);
        valueMap.put("syncStatusId", this.syncStatusId);
        valueMap.put("itemDescription", this.itemDescription);
        valueMap.put("unitRecurringPrice", this.unitRecurringPrice);
        valueMap.put("selectedAmount", this.selectedAmount);
        valueMap.put("cancelBackOrderDate", this.cancelBackOrderDate);
        valueMap.put("estimatedShipDate", this.estimatedShipDate);
        valueMap.put("autoCancelDate", this.autoCancelDate);
        valueMap.put("cancelQuantity", this.cancelQuantity);
        valueMap.put("comments", this.comments);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderItem");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String isItemGroupPrimary;

    public java.lang.String getisItemGroupPrimary() {
        return isItemGroupPrimary;
    }

    public void setisItemGroupPrimary(java.lang.String isItemGroupPrimary) {
        this.isItemGroupPrimary = isItemGroupPrimary;
    }
    private java.math.BigDecimal unitPrice;

    public java.math.BigDecimal getunitPrice() {
        return unitPrice;
    }

    public void setunitPrice(java.math.BigDecimal unitPrice) {
        Debug.logInfo("old setunitPrice : " + this.unitPrice, module);
        this.unitPrice = unitPrice;
        Debug.logInfo("new setunitPrice : " + unitPrice, module);
        Debug.logInfo("LookupActionListner 1", module);
        /* try{
         throw new Exception("Lookup Action Listner");
         }
         catch(Exception e){
         Debug.logError(e, module);
         } */
    }
    private java.lang.String changeByUserLoginId;

    public java.lang.String getchangeByUserLoginId() {
        return changeByUserLoginId;
    }

    public void setchangeByUserLoginId(java.lang.String changeByUserLoginId) {
        this.changeByUserLoginId = changeByUserLoginId;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getoverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setoverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String dontCancelSetUserLogin;

    public java.lang.String getdontCancelSetUserLogin() {
        return dontCancelSetUserLogin;
    }

    public void setdontCancelSetUserLogin(java.lang.String dontCancelSetUserLogin) {
        this.dontCancelSetUserLogin = dontCancelSetUserLogin;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String isModifiedPrice;

    public java.lang.String getisModifiedPrice() {
        return isModifiedPrice;
    }

    public void setisModifiedPrice(java.lang.String isModifiedPrice) {
        this.isModifiedPrice = isModifiedPrice;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getquantity() {
        return quantity;
    }

    public void setquantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }
    private java.lang.String shoppingListItemSeqId;

    public java.lang.String getshoppingListItemSeqId() {
        return shoppingListItemSeqId;
    }

    public void setshoppingListItemSeqId(java.lang.String shoppingListItemSeqId) {
        this.shoppingListItemSeqId = shoppingListItemSeqId;
    }
    private java.lang.String quoteId;

    public java.lang.String getquoteId() {
        return quoteId;
    }

    public void setquoteId(java.lang.String quoteId) {
        this.quoteId = quoteId;
    }
    private java.math.BigDecimal unitListPrice;

    public java.math.BigDecimal getunitListPrice() {
        return unitListPrice;
    }

    public void setunitListPrice(java.math.BigDecimal unitListPrice) {
        this.unitListPrice = unitListPrice;
    }
    private java.lang.String deploymentId;

    public java.lang.String getdeploymentId() {
        return deploymentId;
    }

    public void setdeploymentId(java.lang.String deploymentId) {
        this.deploymentId = deploymentId;
    }
    private java.lang.String recurringFreqUomId;

    public java.lang.String getrecurringFreqUomId() {
        return recurringFreqUomId;
    }

    public void setrecurringFreqUomId(java.lang.String recurringFreqUomId) {
        this.recurringFreqUomId = recurringFreqUomId;
    }
    private java.sql.Timestamp shipBeforeDate;

    public java.sql.Timestamp getshipBeforeDate() {
        return shipBeforeDate;
    }

    public void setshipBeforeDate(java.sql.Timestamp shipBeforeDate) {
        this.shipBeforeDate = shipBeforeDate;
    }
    private java.lang.String orderItemSeqId;

    public java.lang.String getorderItemSeqId() {
        return orderItemSeqId;
    }

    public void setorderItemSeqId(java.lang.String orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getproductFeatureId() {
        return productFeatureId;
    }

    public void setproductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.lang.String shoppingListId;

    public java.lang.String getshoppingListId() {
        return shoppingListId;
    }

    public void setshoppingListId(java.lang.String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
    private java.lang.String prodCatalogId;

    public java.lang.String getprodCatalogId() {
        return prodCatalogId;
    }

    public void setprodCatalogId(java.lang.String prodCatalogId) {
        this.prodCatalogId = prodCatalogId;
    }
    private java.lang.String orderItemGroupSeqId;

    public java.lang.String getorderItemGroupSeqId() {
        return orderItemGroupSeqId;
    }

    public void setorderItemGroupSeqId(java.lang.String orderItemGroupSeqId) {
        this.orderItemGroupSeqId = orderItemGroupSeqId;
    }
    private java.lang.String productCategoryId;

    public java.lang.String getproductCategoryId() {
        return productCategoryId;
    }

    public void setproductCategoryId(java.lang.String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    private java.lang.String supplierProductId;

    public java.lang.String getsupplierProductId() {
        return supplierProductId;
    }

    public void setsupplierProductId(java.lang.String supplierProductId) {
        this.supplierProductId = supplierProductId;
    }
    private java.lang.String budgetId;

    public java.lang.String getbudgetId() {
        return budgetId;
    }

    public void setbudgetId(java.lang.String budgetId) {
        this.budgetId = budgetId;
    }
    private java.math.BigDecimal unitAverageCost;

    public java.math.BigDecimal getunitAverageCost() {
        return unitAverageCost;
    }

    public void setunitAverageCost(java.math.BigDecimal unitAverageCost) {
        this.unitAverageCost = unitAverageCost;
    }
    private java.lang.String correspondingPoId;

    public java.lang.String getcorrespondingPoId() {
        return correspondingPoId;
    }

    public void setcorrespondingPoId(java.lang.String correspondingPoId) {
        this.correspondingPoId = correspondingPoId;
    }
    private java.lang.String externalId;

    public java.lang.String getexternalId() {
        return externalId;
    }

    public void setexternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    private java.lang.String salesOpportunityId;

    public java.lang.String getsalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setsalesOpportunityId(java.lang.String salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }
    private java.lang.String subscriptionId;

    public java.lang.String getsubscriptionId() {
        return subscriptionId;
    }

    public void setsubscriptionId(java.lang.String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String budgetItemSeqId;

    public java.lang.String getbudgetItemSeqId() {
        return budgetItemSeqId;
    }

    public void setbudgetItemSeqId(java.lang.String budgetItemSeqId) {
        this.budgetItemSeqId = budgetItemSeqId;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String quoteItemSeqId;

    public java.lang.String getquoteItemSeqId() {
        return quoteItemSeqId;
    }

    public void setquoteItemSeqId(java.lang.String quoteItemSeqId) {
        this.quoteItemSeqId = quoteItemSeqId;
    }
    private java.sql.Timestamp shipAfterDate;

    public java.sql.Timestamp getshipAfterDate() {
        return shipAfterDate;
    }

    public void setshipAfterDate(java.sql.Timestamp shipAfterDate) {
        this.shipAfterDate = shipAfterDate;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String fromInventoryItemId;

    public java.lang.String getfromInventoryItemId() {
        return fromInventoryItemId;
    }

    public void setfromInventoryItemId(java.lang.String fromInventoryItemId) {
        this.fromInventoryItemId = fromInventoryItemId;
    }
    private java.lang.String orderItemTypeId;

    public java.lang.String getorderItemTypeId() {
        return orderItemTypeId;
    }

    public void setorderItemTypeId(java.lang.String orderItemTypeId) {
        this.orderItemTypeId = orderItemTypeId;
    }
    private java.sql.Timestamp estimatedDeliveryDate;

    public java.sql.Timestamp getestimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setestimatedDeliveryDate(java.sql.Timestamp estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    private java.lang.String isPromo;

    public java.lang.String getisPromo() {
        return isPromo;
    }

    public void setisPromo(java.lang.String isPromo) {
        this.isPromo = isPromo;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.sql.Timestamp dontCancelSetDate;

    public java.sql.Timestamp getdontCancelSetDate() {
        return dontCancelSetDate;
    }

    public void setdontCancelSetDate(java.sql.Timestamp dontCancelSetDate) {
        this.dontCancelSetDate = dontCancelSetDate;
    }
    private java.lang.String syncStatusId;

    public java.lang.String getsyncStatusId() {
        return syncStatusId;
    }

    public void setsyncStatusId(java.lang.String syncStatusId) {
        this.syncStatusId = syncStatusId;
    }
    private java.lang.String itemDescription;

    public java.lang.String getitemDescription() {
        return itemDescription;
    }

    public void setitemDescription(java.lang.String itemDescription) {
        this.itemDescription = itemDescription;
    }
    private java.lang.String unitRecurringPrice;

    public java.lang.String getunitRecurringPrice() {
        return unitRecurringPrice;
    }

    public void setunitRecurringPrice(java.lang.String unitRecurringPrice) {
        this.unitRecurringPrice = unitRecurringPrice;
    }
    private java.math.BigDecimal selectedAmount;

    public java.math.BigDecimal getselectedAmount() {
        return selectedAmount;
    }

    public void setselectedAmount(java.math.BigDecimal selectedAmount) {
        this.selectedAmount = selectedAmount;
    }
    private java.sql.Timestamp cancelBackOrderDate;

    public java.sql.Timestamp getcancelBackOrderDate() {
        return cancelBackOrderDate;
    }

    public void setcancelBackOrderDate(java.sql.Timestamp cancelBackOrderDate) {
        this.cancelBackOrderDate = cancelBackOrderDate;
    }
    private java.sql.Timestamp estimatedShipDate;

    public java.sql.Timestamp getestimatedShipDate() {
        return estimatedShipDate;
    }

    public void setestimatedShipDate(java.sql.Timestamp estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }
    private java.sql.Timestamp autoCancelDate;

    public java.sql.Timestamp getautoCancelDate() {
        return autoCancelDate;
    }

    public void setautoCancelDate(java.sql.Timestamp autoCancelDate) {
        this.autoCancelDate = autoCancelDate;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String cancelQuantity;

    public java.lang.String getcancelQuantity() {
        return cancelQuantity;
    }

    public void setcancelQuantity(java.lang.String cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
    }
    private java.lang.String comments;

    public java.lang.String getcomments() {
        return comments;
    }

    public void setcomments(java.lang.String comments) {
        this.comments = comments;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItem> objectList = new ArrayList<OrderItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItem(genVal));
        }
        return objectList;
    }
}
