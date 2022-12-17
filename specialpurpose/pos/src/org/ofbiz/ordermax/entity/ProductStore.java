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

public class ProductStore implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ProductStore.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductStore(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductStore() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productStoreId", "Product Store Id"},
        {"defaultSalesChannelEnumId", "Default Sales Channel Enum Id"},
       // {"managedByLot", "Managed By Lot"},
        {"vatTaxAuthPartyId", "Vat Tax Auth Party Id"},
        {"enableAutoSuggestionList", "Enable Auto Suggestion List"},
        {"viewCartOnAdd", "View Cart On Add"},
        {"checkInventory", "Check Inventory"},
        {"isDemoStore", "Is Demo Store"},
        {"authErrorMessage", "Auth Error Message"},
        {"storeCreditValidDays", "Store Credit Valid Days"},
        {"headerCancelStatus", "Header Cancel Status"},
        {"createdStamp", "Created Stamp"},
        {"showTaxIsExempt", "Show Tax Is Exempt"},
        {"authDeclinedMessage", "Auth Declined Message"},
        {"retryFailedAuths", "Retry Failed Auths"},
        {"autoOrderCcTryExp", "Auto Order Cc Try Exp"},
        {"daysToCancelNonPay", "Days To Cancel Non Pay"},
        {"defaultPassword", "Default Password"},
        {"inventoryFacilityId", "Inventory Facility Id"},
        {"balanceResOnOrderCreation", "Balance Res On Order Creation"},
        {"showCheckoutGiftOptions", "Show Checkout Gift Options"},
        {"autoInvoiceDigitalItems", "Auto Invoice Digital Items"},
        {"companyName", "Company Name"},
        {"autoOrderCcTryLaterMax", "Auto Order Cc Try Later Max"},
        {"showOutOfStockProducts", "Show Out Of Stock Products"},
        {"itemApprovedStatus", "Item Approved Status"},
        {"autoApproveInvoice", "Auto Approve Invoice"},
        {"defaultLocaleString", "Default Locale String"},
        {"orderNumberPrefix", "Order Number Prefix"},
        {"addToCartRemoveIncompat", "Add To Cart Remove Incompat"},
        {"digProdUploadCategoryId", "Dig Prod Upload Category Id"},
        {"primaryStoreGroupId", "Primary Store Group Id"},
        {"requirementMethodEnumId", "Requirement Method Enum Id"},
        {"selectPaymentTypePerItem", "Select Payment Type Per Item"},
        {"digitalItemApprovedStatus", "Digital Item Approved Status"},
        {"manualAuthIsCapture", "Manual Auth Is Capture"},
        {"defaultCurrencyUomId", "Default Currency Uom Id"},
        {"autoOrderCcTryOtherCards", "Auto Order Cc Try Other Cards"},
        {"requireCustomerRole", "Require Customer Role"},
        {"prorateShipping", "Prorate Shipping"},
        {"addToCartReplaceUpsell", "Add To Cart Replace Upsell"},
        {"reqReturnInventoryReceive", "Req Return Inventory Receive"},
        {"splitPayPrefPerShpGrp", "Split Pay Pref Per Shp Grp"},
        {"reqShipAddrForDigItems", "Req Ship Addr For Dig Items"},
        {"itemDeclinedStatus", "Item Declined Status"},
        {"explodeOrderItems", "Explode Order Items"},
        {"headerApprovedStatus", "Header Approved Status"},
        {"autoApproveOrder", "Auto Approve Order"},
        {"title", "Title"},
        {"authFraudMessage", "Auth Fraud Message"},
        {"shipIfCaptureFails", "Ship If Capture Fails"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"checkGcBalance", "Check Gc Balance"},
        {"autoOrderCcTryLaterNsf", "Auto Order Cc Try Later Nsf"},
        {"isImmediatelyFulfilled", "Is Immediately Fulfilled"},
        {"payToPartyId", "Pay To Party Id"},
        {"oldStyleSheet", "Old Style Sheet"},
        {"headerDeclinedStatus", "Header Declined Status"},
        {"autoSaveCart", "Auto Save Cart"},
        {"usePrimaryEmailUsername", "Use Primary Email Username"},
        {"requireInventory", "Require Inventory"},
        {"oneInventoryFacility", "One Inventory Facility"},
        {"oldHeaderLogo", "Old Header Logo"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"storeName", "Store Name"},
        {"orderDecimalQuantity", "Order Decimal Quantity"},
        {"reserveOrderEnumId", "Reserve Order Enum Id"},
        {"storeCreditAccountEnumId", "Store Credit Account Enum Id"},
        {"vatTaxAuthGeoId", "Vat Tax Auth Geo Id"},
        {"allowPassword", "Allow Password"},
        {"itemCancelStatus", "Item Cancel Status"},
        {"reserveInventory", "Reserve Inventory"},
        {"oldHeaderMiddleBackground", "Old Header Middle Background"},
        {"prodSearchExcludeVariants", "Prod Search Exclude Variants"},
        {"visualThemeId", "Visual Theme Id"},
        {"subtitle", "Subtitle"},
        {"enableDigProdUpload", "Enable Dig Prod Upload"},
        {"oldHeaderRightBackground", "Old Header Right Background"},
        {"autoApproveReviews", "Auto Approve Reviews"},
        {"showPricesWithVatTax", "Show Prices With Vat Tax"},
        {"prorateTaxes", "Prorate Taxes"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"setOwnerUponIssuance", "Set Owner Upon Issuance"},};

    protected void initObject() {
        this.productStoreId = "";
        this.defaultSalesChannelEnumId = "";
        this.managedByLot = "";
        this.vatTaxAuthPartyId = "";
        this.enableAutoSuggestionList = "";
        this.viewCartOnAdd = "";
        this.checkInventory = "";
        this.isDemoStore = "";
        this.authErrorMessage = "";
        this.storeCreditValidDays = new java.lang.Long(0);
        this.headerCancelStatus = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.showTaxIsExempt = "";
        this.authDeclinedMessage = "";
        this.retryFailedAuths = "";
        this.autoOrderCcTryExp = "";
        this.daysToCancelNonPay = new java.lang.Long(0);
        this.defaultPassword = "";
        this.inventoryFacilityId = "";
        this.balanceResOnOrderCreation = "";
        this.showCheckoutGiftOptions = "";
        this.autoInvoiceDigitalItems = "";
        this.companyName = "";
        this.autoOrderCcTryLaterMax = new java.lang.Long(0);
        this.showOutOfStockProducts = "";
        this.itemApprovedStatus = "";
        this.autoApproveInvoice = "";
        this.defaultLocaleString = "";
        this.orderNumberPrefix = "";
        this.addToCartRemoveIncompat = "";
        this.digProdUploadCategoryId = "";
        this.primaryStoreGroupId = "";
        this.requirementMethodEnumId = "";
        this.selectPaymentTypePerItem = "";
        this.digitalItemApprovedStatus = "";
        this.manualAuthIsCapture = "";
        this.defaultCurrencyUomId = "";
        this.autoOrderCcTryOtherCards = "";
        this.requireCustomerRole = "";
        this.prorateShipping = "";
        this.addToCartReplaceUpsell = "";
        this.reqReturnInventoryReceive = "";
        this.splitPayPrefPerShpGrp = "";
        this.reqShipAddrForDigItems = "";
        this.itemDeclinedStatus = "";
        this.explodeOrderItems = "";
        this.headerApprovedStatus = "";
        this.autoApproveOrder = "";
        this.title = "";
        this.authFraudMessage = "";
        this.shipIfCaptureFails = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.checkGcBalance = "";
        this.autoOrderCcTryLaterNsf = "";
        this.isImmediatelyFulfilled = "";
        this.payToPartyId = "";
        this.oldStyleSheet = "";
        this.headerDeclinedStatus = "";
        this.autoSaveCart = "";
        this.usePrimaryEmailUsername = "";
        this.requireInventory = "";
        this.oneInventoryFacility = "";
        this.oldHeaderLogo = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.storeName = "";
        this.orderDecimalQuantity = "";
        this.reserveOrderEnumId = "";
        this.storeCreditAccountEnumId = "";
        this.vatTaxAuthGeoId = "";
        this.allowPassword = "";
        this.itemCancelStatus = "";
        this.reserveInventory = "";
        this.oldHeaderMiddleBackground = "";
        this.prodSearchExcludeVariants = "";
        this.visualThemeId = "";
        this.subtitle = "";
        this.enableDigProdUpload = "";
        this.oldHeaderRightBackground = "";
        this.autoApproveReviews = "";
        this.showPricesWithVatTax = "";
        this.prorateTaxes = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.setOwnerUponIssuance = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");
        this.defaultSalesChannelEnumId = (java.lang.String) genVal.get("defaultSalesChannelEnumId");
        this.managedByLot = (java.lang.String) genVal.get("managedByLot");
        this.vatTaxAuthPartyId = (java.lang.String) genVal.get("vatTaxAuthPartyId");
        this.enableAutoSuggestionList = (java.lang.String) genVal.get("enableAutoSuggestionList");
        this.viewCartOnAdd = (java.lang.String) genVal.get("viewCartOnAdd");
        this.checkInventory = (java.lang.String) genVal.get("checkInventory");
        this.isDemoStore = (java.lang.String) genVal.get("isDemoStore");
        this.authErrorMessage = (java.lang.String) genVal.get("authErrorMessage");
        this.storeCreditValidDays = (java.lang.Long) genVal.get("storeCreditValidDays");
        this.headerCancelStatus = (java.lang.String) genVal.get("headerCancelStatus");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.showTaxIsExempt = (java.lang.String) genVal.get("showTaxIsExempt");
        this.authDeclinedMessage = (java.lang.String) genVal.get("authDeclinedMessage");
        this.retryFailedAuths = (java.lang.String) genVal.get("retryFailedAuths");
        this.autoOrderCcTryExp = (java.lang.String) genVal.get("autoOrderCcTryExp");
        this.daysToCancelNonPay = (java.lang.Long) genVal.get("daysToCancelNonPay");
        this.defaultPassword = (java.lang.String) genVal.get("defaultPassword");
        this.inventoryFacilityId = (java.lang.String) genVal.get("inventoryFacilityId");
        this.balanceResOnOrderCreation = (java.lang.String) genVal.get("balanceResOnOrderCreation");
        this.showCheckoutGiftOptions = (java.lang.String) genVal.get("showCheckoutGiftOptions");
        this.autoInvoiceDigitalItems = (java.lang.String) genVal.get("autoInvoiceDigitalItems");
        this.companyName = (java.lang.String) genVal.get("companyName");
        this.autoOrderCcTryLaterMax = (java.lang.Long) genVal.get("autoOrderCcTryLaterMax");
        this.showOutOfStockProducts = (java.lang.String) genVal.get("showOutOfStockProducts");
        this.itemApprovedStatus = (java.lang.String) genVal.get("itemApprovedStatus");
        this.autoApproveInvoice = (java.lang.String) genVal.get("autoApproveInvoice");
        this.defaultLocaleString = (java.lang.String) genVal.get("defaultLocaleString");
        this.orderNumberPrefix = (java.lang.String) genVal.get("orderNumberPrefix");
        this.addToCartRemoveIncompat = (java.lang.String) genVal.get("addToCartRemoveIncompat");
        this.digProdUploadCategoryId = (java.lang.String) genVal.get("digProdUploadCategoryId");
        this.primaryStoreGroupId = (java.lang.String) genVal.get("primaryStoreGroupId");
        this.requirementMethodEnumId = (java.lang.String) genVal.get("requirementMethodEnumId");
        this.selectPaymentTypePerItem = (java.lang.String) genVal.get("selectPaymentTypePerItem");
        this.digitalItemApprovedStatus = (java.lang.String) genVal.get("digitalItemApprovedStatus");
        this.manualAuthIsCapture = (java.lang.String) genVal.get("manualAuthIsCapture");
        this.defaultCurrencyUomId = (java.lang.String) genVal.get("defaultCurrencyUomId");
        this.autoOrderCcTryOtherCards = (java.lang.String) genVal.get("autoOrderCcTryOtherCards");
        this.requireCustomerRole = (java.lang.String) genVal.get("requireCustomerRole");
        this.prorateShipping = (java.lang.String) genVal.get("prorateShipping");
        this.addToCartReplaceUpsell = (java.lang.String) genVal.get("addToCartReplaceUpsell");
        this.reqReturnInventoryReceive = (java.lang.String) genVal.get("reqReturnInventoryReceive");
        this.splitPayPrefPerShpGrp = (java.lang.String) genVal.get("splitPayPrefPerShpGrp");
        this.reqShipAddrForDigItems = (java.lang.String) genVal.get("reqShipAddrForDigItems");
        this.itemDeclinedStatus = (java.lang.String) genVal.get("itemDeclinedStatus");
        this.explodeOrderItems = (java.lang.String) genVal.get("explodeOrderItems");
        this.headerApprovedStatus = (java.lang.String) genVal.get("headerApprovedStatus");
        this.autoApproveOrder = (java.lang.String) genVal.get("autoApproveOrder");
        this.title = (java.lang.String) genVal.get("title");
        this.authFraudMessage = (java.lang.String) genVal.get("authFraudMessage");
        this.shipIfCaptureFails = (java.lang.String) genVal.get("shipIfCaptureFails");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.checkGcBalance = (java.lang.String) genVal.get("checkGcBalance");
        this.autoOrderCcTryLaterNsf = (java.lang.String) genVal.get("autoOrderCcTryLaterNsf");
        this.isImmediatelyFulfilled = (java.lang.String) genVal.get("isImmediatelyFulfilled");
        this.payToPartyId = (java.lang.String) genVal.get("payToPartyId");
        this.oldStyleSheet = (java.lang.String) genVal.get("oldStyleSheet");
        this.headerDeclinedStatus = (java.lang.String) genVal.get("headerDeclinedStatus");
        this.autoSaveCart = (java.lang.String) genVal.get("autoSaveCart");
        this.usePrimaryEmailUsername = (java.lang.String) genVal.get("usePrimaryEmailUsername");
        this.requireInventory = (java.lang.String) genVal.get("requireInventory");
        this.oneInventoryFacility = (java.lang.String) genVal.get("oneInventoryFacility");
        this.oldHeaderLogo = (java.lang.String) genVal.get("oldHeaderLogo");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.storeName = (java.lang.String) genVal.get("storeName");
        this.orderDecimalQuantity = (java.lang.String) genVal.get("orderDecimalQuantity");
        this.reserveOrderEnumId = (java.lang.String) genVal.get("reserveOrderEnumId");
        this.storeCreditAccountEnumId = (java.lang.String) genVal.get("storeCreditAccountEnumId");
        this.vatTaxAuthGeoId = (java.lang.String) genVal.get("vatTaxAuthGeoId");
        this.allowPassword = (java.lang.String) genVal.get("allowPassword");
        this.itemCancelStatus = (java.lang.String) genVal.get("itemCancelStatus");
        this.reserveInventory = (java.lang.String) genVal.get("reserveInventory");
        this.oldHeaderMiddleBackground = (java.lang.String) genVal.get("oldHeaderMiddleBackground");
        this.prodSearchExcludeVariants = (java.lang.String) genVal.get("prodSearchExcludeVariants");
        this.visualThemeId = (java.lang.String) genVal.get("visualThemeId");
        this.subtitle = (java.lang.String) genVal.get("subtitle");
        this.enableDigProdUpload = (java.lang.String) genVal.get("enableDigProdUpload");
        this.oldHeaderRightBackground = (java.lang.String) genVal.get("oldHeaderRightBackground");
        this.autoApproveReviews = (java.lang.String) genVal.get("autoApproveReviews");
        this.showPricesWithVatTax = (java.lang.String) genVal.get("showPricesWithVatTax");
        this.prorateTaxes = (java.lang.String) genVal.get("prorateTaxes");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.setOwnerUponIssuance = (java.lang.String) genVal.get("setOwnerUponIssuance");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.productStoreId));
        val.set("defaultSalesChannelEnumId", OrderMaxUtility.getValidEntityString(this.defaultSalesChannelEnumId));
        val.set("managedByLot", OrderMaxUtility.getValidEntityString(this.managedByLot));
        val.set("vatTaxAuthPartyId", OrderMaxUtility.getValidEntityString(this.vatTaxAuthPartyId));
        val.set("enableAutoSuggestionList", OrderMaxUtility.getValidEntityString(this.enableAutoSuggestionList));
        val.set("viewCartOnAdd", OrderMaxUtility.getValidEntityString(this.viewCartOnAdd));
        val.set("checkInventory", OrderMaxUtility.getValidEntityString(this.checkInventory));
        val.set("isDemoStore", OrderMaxUtility.getValidEntityString(this.isDemoStore));
        val.set("authErrorMessage", OrderMaxUtility.getValidEntityString(this.authErrorMessage));
        val.set("storeCreditValidDays", OrderMaxUtility.getValidEntityLong(this.storeCreditValidDays));
        val.set("headerCancelStatus", OrderMaxUtility.getValidEntityString(this.headerCancelStatus));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("showTaxIsExempt", OrderMaxUtility.getValidEntityString(this.showTaxIsExempt));
        val.set("authDeclinedMessage", OrderMaxUtility.getValidEntityString(this.authDeclinedMessage));
        val.set("retryFailedAuths", OrderMaxUtility.getValidEntityString(this.retryFailedAuths));
        val.set("autoOrderCcTryExp", OrderMaxUtility.getValidEntityString(this.autoOrderCcTryExp));
        val.set("daysToCancelNonPay", OrderMaxUtility.getValidEntityLong(this.daysToCancelNonPay));
        val.set("defaultPassword", OrderMaxUtility.getValidEntityString(this.defaultPassword));
        val.set("inventoryFacilityId", OrderMaxUtility.getValidEntityString(this.inventoryFacilityId));
        val.set("balanceResOnOrderCreation", OrderMaxUtility.getValidEntityString(this.balanceResOnOrderCreation));
        val.set("showCheckoutGiftOptions", OrderMaxUtility.getValidEntityString(this.showCheckoutGiftOptions));
        val.set("autoInvoiceDigitalItems", OrderMaxUtility.getValidEntityString(this.autoInvoiceDigitalItems));
        val.set("companyName", OrderMaxUtility.getValidEntityString(this.companyName));
        val.set("autoOrderCcTryLaterMax", OrderMaxUtility.getValidEntityLong(this.autoOrderCcTryLaterMax));
        val.set("showOutOfStockProducts", OrderMaxUtility.getValidEntityString(this.showOutOfStockProducts));
        val.set("itemApprovedStatus", OrderMaxUtility.getValidEntityString(this.itemApprovedStatus));
        val.set("autoApproveInvoice", OrderMaxUtility.getValidEntityString(this.autoApproveInvoice));
        val.set("defaultLocaleString", OrderMaxUtility.getValidEntityString(this.defaultLocaleString));
        val.set("orderNumberPrefix", OrderMaxUtility.getValidEntityString(this.orderNumberPrefix));
        val.set("addToCartRemoveIncompat", OrderMaxUtility.getValidEntityString(this.addToCartRemoveIncompat));
        val.set("digProdUploadCategoryId", OrderMaxUtility.getValidEntityString(this.digProdUploadCategoryId));
        val.set("primaryStoreGroupId", OrderMaxUtility.getValidEntityString(this.primaryStoreGroupId));
        val.set("requirementMethodEnumId", OrderMaxUtility.getValidEntityString(this.requirementMethodEnumId));
        val.set("selectPaymentTypePerItem", OrderMaxUtility.getValidEntityString(this.selectPaymentTypePerItem));
        val.set("digitalItemApprovedStatus", OrderMaxUtility.getValidEntityString(this.digitalItemApprovedStatus));
        val.set("manualAuthIsCapture", OrderMaxUtility.getValidEntityString(this.manualAuthIsCapture));
        val.set("defaultCurrencyUomId", OrderMaxUtility.getValidEntityString(this.defaultCurrencyUomId));
        val.set("autoOrderCcTryOtherCards", OrderMaxUtility.getValidEntityString(this.autoOrderCcTryOtherCards));
        val.set("requireCustomerRole", OrderMaxUtility.getValidEntityString(this.requireCustomerRole));
        val.set("prorateShipping", OrderMaxUtility.getValidEntityString(this.prorateShipping));
        val.set("addToCartReplaceUpsell", OrderMaxUtility.getValidEntityString(this.addToCartReplaceUpsell));
        val.set("reqReturnInventoryReceive", OrderMaxUtility.getValidEntityString(this.reqReturnInventoryReceive));
        val.set("splitPayPrefPerShpGrp", OrderMaxUtility.getValidEntityString(this.splitPayPrefPerShpGrp));
        val.set("reqShipAddrForDigItems", OrderMaxUtility.getValidEntityString(this.reqShipAddrForDigItems));
        val.set("itemDeclinedStatus", OrderMaxUtility.getValidEntityString(this.itemDeclinedStatus));
        val.set("explodeOrderItems", OrderMaxUtility.getValidEntityString(this.explodeOrderItems));
        val.set("headerApprovedStatus", OrderMaxUtility.getValidEntityString(this.headerApprovedStatus));
        val.set("autoApproveOrder", OrderMaxUtility.getValidEntityString(this.autoApproveOrder));
        val.set("title", OrderMaxUtility.getValidEntityString(this.title));
        val.set("authFraudMessage", OrderMaxUtility.getValidEntityString(this.authFraudMessage));
        val.set("shipIfCaptureFails", OrderMaxUtility.getValidEntityString(this.shipIfCaptureFails));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("checkGcBalance", OrderMaxUtility.getValidEntityString(this.checkGcBalance));
        val.set("autoOrderCcTryLaterNsf", OrderMaxUtility.getValidEntityString(this.autoOrderCcTryLaterNsf));
        val.set("isImmediatelyFulfilled", OrderMaxUtility.getValidEntityString(this.isImmediatelyFulfilled));
        val.set("payToPartyId", OrderMaxUtility.getValidEntityString(this.payToPartyId));
        val.set("oldStyleSheet", OrderMaxUtility.getValidEntityString(this.oldStyleSheet));
        val.set("headerDeclinedStatus", OrderMaxUtility.getValidEntityString(this.headerDeclinedStatus));
        val.set("autoSaveCart", OrderMaxUtility.getValidEntityString(this.autoSaveCart));
        val.set("usePrimaryEmailUsername", OrderMaxUtility.getValidEntityString(this.usePrimaryEmailUsername));
        val.set("requireInventory", OrderMaxUtility.getValidEntityString(this.requireInventory));
        val.set("oneInventoryFacility", OrderMaxUtility.getValidEntityString(this.oneInventoryFacility));
        val.set("oldHeaderLogo", OrderMaxUtility.getValidEntityString(this.oldHeaderLogo));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("storeName", OrderMaxUtility.getValidEntityString(this.storeName));
        val.set("orderDecimalQuantity", OrderMaxUtility.getValidEntityString(this.orderDecimalQuantity));
        val.set("reserveOrderEnumId", OrderMaxUtility.getValidEntityString(this.reserveOrderEnumId));
        val.set("storeCreditAccountEnumId", OrderMaxUtility.getValidEntityString(this.storeCreditAccountEnumId));
        val.set("vatTaxAuthGeoId", OrderMaxUtility.getValidEntityString(this.vatTaxAuthGeoId));
        val.set("allowPassword", OrderMaxUtility.getValidEntityString(this.allowPassword));
        val.set("itemCancelStatus", OrderMaxUtility.getValidEntityString(this.itemCancelStatus));
        val.set("reserveInventory", OrderMaxUtility.getValidEntityString(this.reserveInventory));
        val.set("oldHeaderMiddleBackground", OrderMaxUtility.getValidEntityString(this.oldHeaderMiddleBackground));
        val.set("prodSearchExcludeVariants", OrderMaxUtility.getValidEntityString(this.prodSearchExcludeVariants));
        val.set("visualThemeId", OrderMaxUtility.getValidEntityString(this.visualThemeId));
        val.set("subtitle", OrderMaxUtility.getValidEntityString(this.subtitle));
        val.set("enableDigProdUpload", OrderMaxUtility.getValidEntityString(this.enableDigProdUpload));
        val.set("oldHeaderRightBackground", OrderMaxUtility.getValidEntityString(this.oldHeaderRightBackground));
        val.set("autoApproveReviews", OrderMaxUtility.getValidEntityString(this.autoApproveReviews));
        val.set("showPricesWithVatTax", OrderMaxUtility.getValidEntityString(this.showPricesWithVatTax));
        val.set("prorateTaxes", OrderMaxUtility.getValidEntityString(this.prorateTaxes));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("setOwnerUponIssuance", OrderMaxUtility.getValidEntityString(this.setOwnerUponIssuance));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productStoreId", this.productStoreId);
        valueMap.put("defaultSalesChannelEnumId", this.defaultSalesChannelEnumId);
        valueMap.put("managedByLot", this.managedByLot);
        valueMap.put("vatTaxAuthPartyId", this.vatTaxAuthPartyId);
        valueMap.put("enableAutoSuggestionList", this.enableAutoSuggestionList);
        valueMap.put("viewCartOnAdd", this.viewCartOnAdd);
        valueMap.put("checkInventory", this.checkInventory);
        valueMap.put("isDemoStore", this.isDemoStore);
        valueMap.put("authErrorMessage", this.authErrorMessage);
        valueMap.put("storeCreditValidDays", this.storeCreditValidDays);
        valueMap.put("headerCancelStatus", this.headerCancelStatus);
        valueMap.put("showTaxIsExempt", this.showTaxIsExempt);
        valueMap.put("authDeclinedMessage", this.authDeclinedMessage);
        valueMap.put("retryFailedAuths", this.retryFailedAuths);
        valueMap.put("autoOrderCcTryExp", this.autoOrderCcTryExp);
        valueMap.put("daysToCancelNonPay", this.daysToCancelNonPay);
        valueMap.put("defaultPassword", this.defaultPassword);
        valueMap.put("inventoryFacilityId", this.inventoryFacilityId);
        valueMap.put("balanceResOnOrderCreation", this.balanceResOnOrderCreation);
        valueMap.put("showCheckoutGiftOptions", this.showCheckoutGiftOptions);
        valueMap.put("autoInvoiceDigitalItems", this.autoInvoiceDigitalItems);
        valueMap.put("companyName", this.companyName);
        valueMap.put("autoOrderCcTryLaterMax", this.autoOrderCcTryLaterMax);
        valueMap.put("showOutOfStockProducts", this.showOutOfStockProducts);
        valueMap.put("itemApprovedStatus", this.itemApprovedStatus);
        valueMap.put("autoApproveInvoice", this.autoApproveInvoice);
        valueMap.put("defaultLocaleString", this.defaultLocaleString);
        valueMap.put("orderNumberPrefix", this.orderNumberPrefix);
        valueMap.put("addToCartRemoveIncompat", this.addToCartRemoveIncompat);
        valueMap.put("digProdUploadCategoryId", this.digProdUploadCategoryId);
        valueMap.put("primaryStoreGroupId", this.primaryStoreGroupId);
        valueMap.put("requirementMethodEnumId", this.requirementMethodEnumId);
        valueMap.put("selectPaymentTypePerItem", this.selectPaymentTypePerItem);
        valueMap.put("digitalItemApprovedStatus", this.digitalItemApprovedStatus);
        valueMap.put("manualAuthIsCapture", this.manualAuthIsCapture);
        valueMap.put("defaultCurrencyUomId", this.defaultCurrencyUomId);
        valueMap.put("autoOrderCcTryOtherCards", this.autoOrderCcTryOtherCards);
        valueMap.put("requireCustomerRole", this.requireCustomerRole);
        valueMap.put("prorateShipping", this.prorateShipping);
        valueMap.put("addToCartReplaceUpsell", this.addToCartReplaceUpsell);
        valueMap.put("reqReturnInventoryReceive", this.reqReturnInventoryReceive);
        valueMap.put("splitPayPrefPerShpGrp", this.splitPayPrefPerShpGrp);
        valueMap.put("reqShipAddrForDigItems", this.reqShipAddrForDigItems);
        valueMap.put("itemDeclinedStatus", this.itemDeclinedStatus);
        valueMap.put("explodeOrderItems", this.explodeOrderItems);
        valueMap.put("headerApprovedStatus", this.headerApprovedStatus);
        valueMap.put("autoApproveOrder", this.autoApproveOrder);
        valueMap.put("title", this.title);
        valueMap.put("authFraudMessage", this.authFraudMessage);
        valueMap.put("shipIfCaptureFails", this.shipIfCaptureFails);
        valueMap.put("checkGcBalance", this.checkGcBalance);
        valueMap.put("autoOrderCcTryLaterNsf", this.autoOrderCcTryLaterNsf);
        valueMap.put("isImmediatelyFulfilled", this.isImmediatelyFulfilled);
        valueMap.put("payToPartyId", this.payToPartyId);
        valueMap.put("oldStyleSheet", this.oldStyleSheet);
        valueMap.put("headerDeclinedStatus", this.headerDeclinedStatus);
        valueMap.put("autoSaveCart", this.autoSaveCart);
        valueMap.put("usePrimaryEmailUsername", this.usePrimaryEmailUsername);
        valueMap.put("requireInventory", this.requireInventory);
        valueMap.put("oneInventoryFacility", this.oneInventoryFacility);
        valueMap.put("oldHeaderLogo", this.oldHeaderLogo);
        valueMap.put("storeName", this.storeName);
        valueMap.put("orderDecimalQuantity", this.orderDecimalQuantity);
        valueMap.put("reserveOrderEnumId", this.reserveOrderEnumId);
        valueMap.put("storeCreditAccountEnumId", this.storeCreditAccountEnumId);
        valueMap.put("vatTaxAuthGeoId", this.vatTaxAuthGeoId);
        valueMap.put("allowPassword", this.allowPassword);
        valueMap.put("itemCancelStatus", this.itemCancelStatus);
        valueMap.put("reserveInventory", this.reserveInventory);
        valueMap.put("oldHeaderMiddleBackground", this.oldHeaderMiddleBackground);
        valueMap.put("prodSearchExcludeVariants", this.prodSearchExcludeVariants);
        valueMap.put("visualThemeId", this.visualThemeId);
        valueMap.put("subtitle", this.subtitle);
        valueMap.put("enableDigProdUpload", this.enableDigProdUpload);
        valueMap.put("oldHeaderRightBackground", this.oldHeaderRightBackground);
        valueMap.put("autoApproveReviews", this.autoApproveReviews);
        valueMap.put("showPricesWithVatTax", this.showPricesWithVatTax);
        valueMap.put("prorateTaxes", this.prorateTaxes);
        valueMap.put("setOwnerUponIssuance", this.setOwnerUponIssuance);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductStore");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productStoreId;

    public java.lang.String getproductStoreId() {
        return productStoreId;
    }

    public void setproductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
    }
    private java.lang.String defaultSalesChannelEnumId;

    public java.lang.String getdefaultSalesChannelEnumId() {
        return defaultSalesChannelEnumId;
    }

    public void setdefaultSalesChannelEnumId(java.lang.String defaultSalesChannelEnumId) {
        this.defaultSalesChannelEnumId = defaultSalesChannelEnumId;
    }
    private java.lang.String managedByLot;

    public java.lang.String getmanagedByLot() {
        return managedByLot;
    }

    public void setmanagedByLot(java.lang.String managedByLot) {
        this.managedByLot = managedByLot;
    }
    private java.lang.String vatTaxAuthPartyId;

    public java.lang.String getvatTaxAuthPartyId() {
        return vatTaxAuthPartyId;
    }

    public void setvatTaxAuthPartyId(java.lang.String vatTaxAuthPartyId) {
        this.vatTaxAuthPartyId = vatTaxAuthPartyId;
    }
    private java.lang.String enableAutoSuggestionList;

    public java.lang.String getenableAutoSuggestionList() {
        return enableAutoSuggestionList;
    }

    public void setenableAutoSuggestionList(java.lang.String enableAutoSuggestionList) {
        this.enableAutoSuggestionList = enableAutoSuggestionList;
    }
    private java.lang.String viewCartOnAdd;

    public java.lang.String getviewCartOnAdd() {
        return viewCartOnAdd;
    }

    public void setviewCartOnAdd(java.lang.String viewCartOnAdd) {
        this.viewCartOnAdd = viewCartOnAdd;
    }
    private java.lang.String checkInventory;

    public java.lang.String getcheckInventory() {
        return checkInventory;
    }

    public void setcheckInventory(java.lang.String checkInventory) {
        this.checkInventory = checkInventory;
    }
    private java.lang.String isDemoStore;

    public java.lang.String getisDemoStore() {
        return isDemoStore;
    }

    public void setisDemoStore(java.lang.String isDemoStore) {
        this.isDemoStore = isDemoStore;
    }
    private java.lang.String authErrorMessage;

    public java.lang.String getauthErrorMessage() {
        return authErrorMessage;
    }

    public void setauthErrorMessage(java.lang.String authErrorMessage) {
        this.authErrorMessage = authErrorMessage;
    }
    private java.lang.Long storeCreditValidDays;

    public java.lang.Long getstoreCreditValidDays() {
        return storeCreditValidDays;
    }

    public void setstoreCreditValidDays(java.lang.Long storeCreditValidDays) {
        this.storeCreditValidDays = storeCreditValidDays;
    }
    private java.lang.String headerCancelStatus;

    public java.lang.String getheaderCancelStatus() {
        return headerCancelStatus;
    }

    public void setheaderCancelStatus(java.lang.String headerCancelStatus) {
        this.headerCancelStatus = headerCancelStatus;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String showTaxIsExempt;

    public java.lang.String getshowTaxIsExempt() {
        return showTaxIsExempt;
    }

    public void setshowTaxIsExempt(java.lang.String showTaxIsExempt) {
        this.showTaxIsExempt = showTaxIsExempt;
    }
    private java.lang.String authDeclinedMessage;

    public java.lang.String getauthDeclinedMessage() {
        return authDeclinedMessage;
    }

    public void setauthDeclinedMessage(java.lang.String authDeclinedMessage) {
        this.authDeclinedMessage = authDeclinedMessage;
    }
    private java.lang.String retryFailedAuths;

    public java.lang.String getretryFailedAuths() {
        return retryFailedAuths;
    }

    public void setretryFailedAuths(java.lang.String retryFailedAuths) {
        this.retryFailedAuths = retryFailedAuths;
    }
    private java.lang.String autoOrderCcTryExp;

    public java.lang.String getautoOrderCcTryExp() {
        return autoOrderCcTryExp;
    }

    public void setautoOrderCcTryExp(java.lang.String autoOrderCcTryExp) {
        this.autoOrderCcTryExp = autoOrderCcTryExp;
    }
    private java.lang.Long daysToCancelNonPay;

    public java.lang.Long getdaysToCancelNonPay() {
        return daysToCancelNonPay;
    }

    public void setdaysToCancelNonPay(java.lang.Long daysToCancelNonPay) {
        this.daysToCancelNonPay = daysToCancelNonPay;
    }
    private java.lang.String defaultPassword;

    public java.lang.String getdefaultPassword() {
        return defaultPassword;
    }

    public void setdefaultPassword(java.lang.String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
    private java.lang.String inventoryFacilityId;

    public java.lang.String getinventoryFacilityId() {
        return inventoryFacilityId;
    }

    public void setinventoryFacilityId(java.lang.String inventoryFacilityId) {
        this.inventoryFacilityId = inventoryFacilityId;
    }
    private java.lang.String balanceResOnOrderCreation;

    public java.lang.String getbalanceResOnOrderCreation() {
        return balanceResOnOrderCreation;
    }

    public void setbalanceResOnOrderCreation(java.lang.String balanceResOnOrderCreation) {
        this.balanceResOnOrderCreation = balanceResOnOrderCreation;
    }
    private java.lang.String showCheckoutGiftOptions;

    public java.lang.String getshowCheckoutGiftOptions() {
        return showCheckoutGiftOptions;
    }

    public void setshowCheckoutGiftOptions(java.lang.String showCheckoutGiftOptions) {
        this.showCheckoutGiftOptions = showCheckoutGiftOptions;
    }
    private java.lang.String autoInvoiceDigitalItems;

    public java.lang.String getautoInvoiceDigitalItems() {
        return autoInvoiceDigitalItems;
    }

    public void setautoInvoiceDigitalItems(java.lang.String autoInvoiceDigitalItems) {
        this.autoInvoiceDigitalItems = autoInvoiceDigitalItems;
    }
    private java.lang.String companyName;

    public java.lang.String getcompanyName() {
        return companyName;
    }

    public void setcompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }
    private java.lang.Long autoOrderCcTryLaterMax;

    public java.lang.Long getautoOrderCcTryLaterMax() {
        return autoOrderCcTryLaterMax;
    }

    public void setautoOrderCcTryLaterMax(java.lang.Long autoOrderCcTryLaterMax) {
        this.autoOrderCcTryLaterMax = autoOrderCcTryLaterMax;
    }
    private java.lang.String showOutOfStockProducts;

    public java.lang.String getshowOutOfStockProducts() {
        return showOutOfStockProducts;
    }

    public void setshowOutOfStockProducts(java.lang.String showOutOfStockProducts) {
        this.showOutOfStockProducts = showOutOfStockProducts;
    }
    private java.lang.String itemApprovedStatus;

    public java.lang.String getitemApprovedStatus() {
        return itemApprovedStatus;
    }

    public void setitemApprovedStatus(java.lang.String itemApprovedStatus) {
        this.itemApprovedStatus = itemApprovedStatus;
    }
    private java.lang.String autoApproveInvoice;

    public java.lang.String getautoApproveInvoice() {
        return autoApproveInvoice;
    }

    public void setautoApproveInvoice(java.lang.String autoApproveInvoice) {
        this.autoApproveInvoice = autoApproveInvoice;
    }
    private java.lang.String defaultLocaleString;

    public java.lang.String getdefaultLocaleString() {
        return defaultLocaleString;
    }

    public void setdefaultLocaleString(java.lang.String defaultLocaleString) {
        this.defaultLocaleString = defaultLocaleString;
    }
    private java.lang.String orderNumberPrefix;

    public java.lang.String getorderNumberPrefix() {
        return orderNumberPrefix;
    }

    public void setorderNumberPrefix(java.lang.String orderNumberPrefix) {
        this.orderNumberPrefix = orderNumberPrefix;
    }
    private java.lang.String addToCartRemoveIncompat;

    public java.lang.String getaddToCartRemoveIncompat() {
        return addToCartRemoveIncompat;
    }

    public void setaddToCartRemoveIncompat(java.lang.String addToCartRemoveIncompat) {
        this.addToCartRemoveIncompat = addToCartRemoveIncompat;
    }
    private java.lang.String digProdUploadCategoryId;

    public java.lang.String getdigProdUploadCategoryId() {
        return digProdUploadCategoryId;
    }

    public void setdigProdUploadCategoryId(java.lang.String digProdUploadCategoryId) {
        this.digProdUploadCategoryId = digProdUploadCategoryId;
    }
    private java.lang.String primaryStoreGroupId;

    public java.lang.String getprimaryStoreGroupId() {
        return primaryStoreGroupId;
    }

    public void setprimaryStoreGroupId(java.lang.String primaryStoreGroupId) {
        this.primaryStoreGroupId = primaryStoreGroupId;
    }
    private java.lang.String requirementMethodEnumId;

    public java.lang.String getrequirementMethodEnumId() {
        return requirementMethodEnumId;
    }

    public void setrequirementMethodEnumId(java.lang.String requirementMethodEnumId) {
        this.requirementMethodEnumId = requirementMethodEnumId;
    }
    private java.lang.String selectPaymentTypePerItem;

    public java.lang.String getselectPaymentTypePerItem() {
        return selectPaymentTypePerItem;
    }

    public void setselectPaymentTypePerItem(java.lang.String selectPaymentTypePerItem) {
        this.selectPaymentTypePerItem = selectPaymentTypePerItem;
    }
    private java.lang.String digitalItemApprovedStatus;

    public java.lang.String getdigitalItemApprovedStatus() {
        return digitalItemApprovedStatus;
    }

    public void setdigitalItemApprovedStatus(java.lang.String digitalItemApprovedStatus) {
        this.digitalItemApprovedStatus = digitalItemApprovedStatus;
    }
    private java.lang.String manualAuthIsCapture;

    public java.lang.String getmanualAuthIsCapture() {
        return manualAuthIsCapture;
    }

    public void setmanualAuthIsCapture(java.lang.String manualAuthIsCapture) {
        this.manualAuthIsCapture = manualAuthIsCapture;
    }
    private java.lang.String defaultCurrencyUomId;

    public java.lang.String getdefaultCurrencyUomId() {
        return defaultCurrencyUomId;
    }

    public void setdefaultCurrencyUomId(java.lang.String defaultCurrencyUomId) {
        this.defaultCurrencyUomId = defaultCurrencyUomId;
    }
    private java.lang.String autoOrderCcTryOtherCards;

    public java.lang.String getautoOrderCcTryOtherCards() {
        return autoOrderCcTryOtherCards;
    }

    public void setautoOrderCcTryOtherCards(java.lang.String autoOrderCcTryOtherCards) {
        this.autoOrderCcTryOtherCards = autoOrderCcTryOtherCards;
    }
    private java.lang.String requireCustomerRole;

    public java.lang.String getrequireCustomerRole() {
        return requireCustomerRole;
    }

    public void setrequireCustomerRole(java.lang.String requireCustomerRole) {
        this.requireCustomerRole = requireCustomerRole;
    }
    private java.lang.String prorateShipping;

    public java.lang.String getprorateShipping() {
        return prorateShipping;
    }

    public void setprorateShipping(java.lang.String prorateShipping) {
        this.prorateShipping = prorateShipping;
    }
    private java.lang.String addToCartReplaceUpsell;

    public java.lang.String getaddToCartReplaceUpsell() {
        return addToCartReplaceUpsell;
    }

    public void setaddToCartReplaceUpsell(java.lang.String addToCartReplaceUpsell) {
        this.addToCartReplaceUpsell = addToCartReplaceUpsell;
    }
    private java.lang.String reqReturnInventoryReceive;

    public java.lang.String getreqReturnInventoryReceive() {
        return reqReturnInventoryReceive;
    }

    public void setreqReturnInventoryReceive(java.lang.String reqReturnInventoryReceive) {
        this.reqReturnInventoryReceive = reqReturnInventoryReceive;
    }
    private java.lang.String splitPayPrefPerShpGrp;

    public java.lang.String getsplitPayPrefPerShpGrp() {
        return splitPayPrefPerShpGrp;
    }

    public void setsplitPayPrefPerShpGrp(java.lang.String splitPayPrefPerShpGrp) {
        this.splitPayPrefPerShpGrp = splitPayPrefPerShpGrp;
    }
    private java.lang.String reqShipAddrForDigItems;

    public java.lang.String getreqShipAddrForDigItems() {
        return reqShipAddrForDigItems;
    }

    public void setreqShipAddrForDigItems(java.lang.String reqShipAddrForDigItems) {
        this.reqShipAddrForDigItems = reqShipAddrForDigItems;
    }
    private java.lang.String itemDeclinedStatus;

    public java.lang.String getitemDeclinedStatus() {
        return itemDeclinedStatus;
    }

    public void setitemDeclinedStatus(java.lang.String itemDeclinedStatus) {
        this.itemDeclinedStatus = itemDeclinedStatus;
    }
    private java.lang.String explodeOrderItems;

    public java.lang.String getexplodeOrderItems() {
        return explodeOrderItems;
    }

    public void setexplodeOrderItems(java.lang.String explodeOrderItems) {
        this.explodeOrderItems = explodeOrderItems;
    }
    private java.lang.String headerApprovedStatus;

    public java.lang.String getheaderApprovedStatus() {
        return headerApprovedStatus;
    }

    public void setheaderApprovedStatus(java.lang.String headerApprovedStatus) {
        this.headerApprovedStatus = headerApprovedStatus;
    }
    private java.lang.String autoApproveOrder;

    public java.lang.String getautoApproveOrder() {
        return autoApproveOrder;
    }

    public void setautoApproveOrder(java.lang.String autoApproveOrder) {
        this.autoApproveOrder = autoApproveOrder;
    }
    private java.lang.String title;

    public java.lang.String gettitle() {
        return title;
    }

    public void settitle(java.lang.String title) {
        this.title = title;
    }
    private java.lang.String authFraudMessage;

    public java.lang.String getauthFraudMessage() {
        return authFraudMessage;
    }

    public void setauthFraudMessage(java.lang.String authFraudMessage) {
        this.authFraudMessage = authFraudMessage;
    }
    private java.lang.String shipIfCaptureFails;

    public java.lang.String getshipIfCaptureFails() {
        return shipIfCaptureFails;
    }

    public void setshipIfCaptureFails(java.lang.String shipIfCaptureFails) {
        this.shipIfCaptureFails = shipIfCaptureFails;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String checkGcBalance;

    public java.lang.String getcheckGcBalance() {
        return checkGcBalance;
    }

    public void setcheckGcBalance(java.lang.String checkGcBalance) {
        this.checkGcBalance = checkGcBalance;
    }
    private java.lang.String autoOrderCcTryLaterNsf;

    public java.lang.String getautoOrderCcTryLaterNsf() {
        return autoOrderCcTryLaterNsf;
    }

    public void setautoOrderCcTryLaterNsf(java.lang.String autoOrderCcTryLaterNsf) {
        this.autoOrderCcTryLaterNsf = autoOrderCcTryLaterNsf;
    }
    private java.lang.String isImmediatelyFulfilled;

    public java.lang.String getisImmediatelyFulfilled() {
        return isImmediatelyFulfilled;
    }

    public void setisImmediatelyFulfilled(java.lang.String isImmediatelyFulfilled) {
        this.isImmediatelyFulfilled = isImmediatelyFulfilled;
    }
    private java.lang.String payToPartyId;

    public java.lang.String getpayToPartyId() {
        return payToPartyId;
    }

    public void setpayToPartyId(java.lang.String payToPartyId) {
        this.payToPartyId = payToPartyId;
    }
    private java.lang.String oldStyleSheet;

    public java.lang.String getoldStyleSheet() {
        return oldStyleSheet;
    }

    public void setoldStyleSheet(java.lang.String oldStyleSheet) {
        this.oldStyleSheet = oldStyleSheet;
    }
    private java.lang.String headerDeclinedStatus;

    public java.lang.String getheaderDeclinedStatus() {
        return headerDeclinedStatus;
    }

    public void setheaderDeclinedStatus(java.lang.String headerDeclinedStatus) {
        this.headerDeclinedStatus = headerDeclinedStatus;
    }
    private java.lang.String autoSaveCart;

    public java.lang.String getautoSaveCart() {
        return autoSaveCart;
    }

    public void setautoSaveCart(java.lang.String autoSaveCart) {
        this.autoSaveCart = autoSaveCart;
    }
    private java.lang.String usePrimaryEmailUsername;

    public java.lang.String getusePrimaryEmailUsername() {
        return usePrimaryEmailUsername;
    }

    public void setusePrimaryEmailUsername(java.lang.String usePrimaryEmailUsername) {
        this.usePrimaryEmailUsername = usePrimaryEmailUsername;
    }
    private java.lang.String requireInventory;

    public java.lang.String getrequireInventory() {
        return requireInventory;
    }

    public void setrequireInventory(java.lang.String requireInventory) {
        this.requireInventory = requireInventory;
    }
    private java.lang.String oneInventoryFacility;

    public java.lang.String getoneInventoryFacility() {
        return oneInventoryFacility;
    }

    public void setoneInventoryFacility(java.lang.String oneInventoryFacility) {
        this.oneInventoryFacility = oneInventoryFacility;
    }
    private java.lang.String oldHeaderLogo;

    public java.lang.String getoldHeaderLogo() {
        return oldHeaderLogo;
    }

    public void setoldHeaderLogo(java.lang.String oldHeaderLogo) {
        this.oldHeaderLogo = oldHeaderLogo;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String storeName;

    public java.lang.String getstoreName() {
        return storeName;
    }

    public void setstoreName(java.lang.String storeName) {
        this.storeName = storeName;
    }
    private java.lang.String orderDecimalQuantity;

    public java.lang.String getorderDecimalQuantity() {
        return orderDecimalQuantity;
    }

    public void setorderDecimalQuantity(java.lang.String orderDecimalQuantity) {
        this.orderDecimalQuantity = orderDecimalQuantity;
    }
    private java.lang.String reserveOrderEnumId;

    public java.lang.String getreserveOrderEnumId() {
        return reserveOrderEnumId;
    }

    public void setreserveOrderEnumId(java.lang.String reserveOrderEnumId) {
        this.reserveOrderEnumId = reserveOrderEnumId;
    }
    private java.lang.String storeCreditAccountEnumId;

    public java.lang.String getstoreCreditAccountEnumId() {
        return storeCreditAccountEnumId;
    }

    public void setstoreCreditAccountEnumId(java.lang.String storeCreditAccountEnumId) {
        this.storeCreditAccountEnumId = storeCreditAccountEnumId;
    }
    private java.lang.String vatTaxAuthGeoId;

    public java.lang.String getvatTaxAuthGeoId() {
        return vatTaxAuthGeoId;
    }

    public void setvatTaxAuthGeoId(java.lang.String vatTaxAuthGeoId) {
        this.vatTaxAuthGeoId = vatTaxAuthGeoId;
    }
    private java.lang.String allowPassword;

    public java.lang.String getallowPassword() {
        return allowPassword;
    }

    public void setallowPassword(java.lang.String allowPassword) {
        this.allowPassword = allowPassword;
    }
    private java.lang.String itemCancelStatus;

    public java.lang.String getitemCancelStatus() {
        return itemCancelStatus;
    }

    public void setitemCancelStatus(java.lang.String itemCancelStatus) {
        this.itemCancelStatus = itemCancelStatus;
    }
    private java.lang.String reserveInventory;

    public java.lang.String getreserveInventory() {
        return reserveInventory;
    }

    public void setreserveInventory(java.lang.String reserveInventory) {
        this.reserveInventory = reserveInventory;
    }
    private java.lang.String oldHeaderMiddleBackground;

    public java.lang.String getoldHeaderMiddleBackground() {
        return oldHeaderMiddleBackground;
    }

    public void setoldHeaderMiddleBackground(java.lang.String oldHeaderMiddleBackground) {
        this.oldHeaderMiddleBackground = oldHeaderMiddleBackground;
    }
    private java.lang.String prodSearchExcludeVariants;

    public java.lang.String getprodSearchExcludeVariants() {
        return prodSearchExcludeVariants;
    }

    public void setprodSearchExcludeVariants(java.lang.String prodSearchExcludeVariants) {
        this.prodSearchExcludeVariants = prodSearchExcludeVariants;
    }
    private java.lang.String visualThemeId;

    public java.lang.String getvisualThemeId() {
        return visualThemeId;
    }

    public void setvisualThemeId(java.lang.String visualThemeId) {
        this.visualThemeId = visualThemeId;
    }
    private java.lang.String subtitle;

    public java.lang.String getsubtitle() {
        return subtitle;
    }

    public void setsubtitle(java.lang.String subtitle) {
        this.subtitle = subtitle;
    }
    private java.lang.String enableDigProdUpload;

    public java.lang.String getenableDigProdUpload() {
        return enableDigProdUpload;
    }

    public void setenableDigProdUpload(java.lang.String enableDigProdUpload) {
        this.enableDigProdUpload = enableDigProdUpload;
    }
    private java.lang.String oldHeaderRightBackground;

    public java.lang.String getoldHeaderRightBackground() {
        return oldHeaderRightBackground;
    }

    public void setoldHeaderRightBackground(java.lang.String oldHeaderRightBackground) {
        this.oldHeaderRightBackground = oldHeaderRightBackground;
    }
    private java.lang.String autoApproveReviews;

    public java.lang.String getautoApproveReviews() {
        return autoApproveReviews;
    }

    public void setautoApproveReviews(java.lang.String autoApproveReviews) {
        this.autoApproveReviews = autoApproveReviews;
    }
    private java.lang.String showPricesWithVatTax;

    public java.lang.String getshowPricesWithVatTax() {
        return showPricesWithVatTax;
    }

    public void setshowPricesWithVatTax(java.lang.String showPricesWithVatTax) {
        this.showPricesWithVatTax = showPricesWithVatTax;
    }
    private java.lang.String prorateTaxes;

    public java.lang.String getprorateTaxes() {
        return prorateTaxes;
    }

    public void setprorateTaxes(java.lang.String prorateTaxes) {
        this.prorateTaxes = prorateTaxes;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String setOwnerUponIssuance;

    public java.lang.String getsetOwnerUponIssuance() {
        return setOwnerUponIssuance;
    }

    public void setsetOwnerUponIssuance(java.lang.String setOwnerUponIssuance) {
        this.setOwnerUponIssuance = setOwnerUponIssuance;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStore> objectList = new ArrayList<ProductStore>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStore(genVal));
        }
        return objectList;
    }
    
     @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getstoreName());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getproductStoreId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getproductStoreId());
        }

        return orderToStringBuilder.toString();
    }

}
