package org.ofbiz.ordermax.entity;

import java.math.BigDecimal;
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

public class ProductPrice implements GenericValueObjectInterface {

    public static final String module = ProductPrice.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductPrice(GenericValue val) {
        genVal = val;
        try {
            Debug.logInfo("new ProductPrice", module);
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductPrice() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productPriceTypeId", "Product Price Type Id"},
        {"taxInPrice", "Tax In Price"},
        {"thruDate", "Thru Date"},
        {"priceWithoutTax", "Price Without Tax"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"productPricePurposeId", "Product Price Purpose Id"},
        {"customPriceCalcService", "Custom Price Calc Service"},
        {"createdDate", "Created Date"},
        {"productStoreGroupId", "Product Store Group Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"termUomId", "Term Uom Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"priceWithTax", "Price With Tax"},
        {"productId", "Product Id"},
        {"taxAuthGeoId", "Tax Auth Geo Id"},
        {"taxAmount", "Tax Amount"},
        {"price", "Price"},
        {"lastModifiedByUserLogin", "Last Modified By User Login"},
        {"fromDate", "From Date"},
        {"lastModifiedDate", "Last Modified Date"},
        {"taxAuthPartyId", "Tax Auth Party Id"},
        {"taxPercentage", "Tax Percentage"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdByUserLogin", "Created By User Login"},};

    protected void initObject() {
        this.productPriceTypeId = "";
        this.taxInPrice = "";
        this.thruDate = UtilDateTime.nowTimestamp();
        this.priceWithoutTax = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.productPricePurposeId = "";
        this.customPriceCalcService = "";
        this.createdDate = UtilDateTime.nowTimestamp();
        this.productStoreGroupId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.termUomId = "";
        this.currencyUomId = "";
        this.priceWithTax = "";
        this.productId = "";
        this.taxAuthGeoId = "";
        this.taxAmount = "";
        this.price = java.math.BigDecimal.ZERO;
        this.lastModifiedByUserLogin = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.lastModifiedDate = UtilDateTime.nowTimestamp();
        this.taxAuthPartyId = "";
        this.taxPercentage = null;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdByUserLogin = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productPriceTypeId = (java.lang.String) genVal.get("productPriceTypeId");
        this.taxInPrice = (java.lang.String) genVal.get("taxInPrice");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.priceWithoutTax = (java.lang.String) genVal.get("priceWithoutTax");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.productPricePurposeId = (java.lang.String) genVal.get("productPricePurposeId");
        this.customPriceCalcService = (java.lang.String) genVal.get("customPriceCalcService");
        this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
        this.productStoreGroupId = (java.lang.String) genVal.get("productStoreGroupId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.termUomId = (java.lang.String) genVal.get("termUomId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.priceWithTax = (java.lang.String) genVal.get("priceWithTax");
        this.productId = (java.lang.String) genVal.get("productId");
        this.taxAuthGeoId = (java.lang.String) genVal.get("taxAuthGeoId");
        this.taxAmount = (java.lang.String) genVal.get("taxAmount");
        this.price = (java.math.BigDecimal) genVal.get("price");
        this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
        this.taxAuthPartyId = (java.lang.String) genVal.get("taxAuthPartyId");
        this.taxPercentage = (java.math.BigDecimal) genVal.get("taxPercentage");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productPriceTypeId", OrderMaxUtility.getValidEntityString(this.productPriceTypeId));
        val.set("taxInPrice", OrderMaxUtility.getValidEntityString(this.taxInPrice));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("priceWithoutTax", OrderMaxUtility.getValidEntityString(this.priceWithoutTax));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("productPricePurposeId", OrderMaxUtility.getValidEntityString(this.productPricePurposeId));
        val.set("customPriceCalcService", OrderMaxUtility.getValidEntityString(this.customPriceCalcService));
        val.set("createdDate", OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
        val.set("productStoreGroupId", OrderMaxUtility.getValidEntityString(this.productStoreGroupId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("termUomId", OrderMaxUtility.getValidEntityString(this.termUomId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("priceWithTax", OrderMaxUtility.getValidEntityString(this.priceWithTax));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("taxAuthGeoId", OrderMaxUtility.getValidEntityString(this.taxAuthGeoId));
        val.set("taxAmount", OrderMaxUtility.getValidEntityString(this.taxAmount));
        val.set("price", OrderMaxUtility.getValidEntityBigDecimal(this.price));
        val.set("lastModifiedByUserLogin", OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("lastModifiedDate", OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
        val.set("taxAuthPartyId", OrderMaxUtility.getValidEntityString(this.taxAuthPartyId));
        val.set("taxPercentage", OrderMaxUtility.getValidEntityBigDecimal(this.taxPercentage));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdByUserLogin", OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productPriceTypeId", this.productPriceTypeId);
        valueMap.put("taxInPrice", this.taxInPrice);
//valueMap.put("thruDate",this.thruDate);
//valueMap.put("priceWithoutTax",this.priceWithoutTax);
        valueMap.put("productPricePurposeId", this.productPricePurposeId);
        valueMap.put("customPriceCalcService", this.customPriceCalcService);
//valueMap.put("createdDate",this.createdDate);
        valueMap.put("productStoreGroupId", this.productStoreGroupId);
        valueMap.put("termUomId", this.termUomId);
        valueMap.put("currencyUomId", this.currencyUomId);
//valueMap.put("priceWithTax",this.priceWithTax);
        valueMap.put("productId", this.productId);
        valueMap.put("taxAuthGeoId", this.taxAuthGeoId);
//valueMap.put("taxAmount",this.taxAmount);
        valueMap.put("price", this.price);
//valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
        valueMap.put("fromDate", this.fromDate);
//valueMap.put("lastModifiedDate",this.lastModifiedDate);
        valueMap.put("taxAuthPartyId", this.taxAuthPartyId);
        valueMap.put("taxPercentage", taxPercentage);
//valueMap.put("createdByUserLogin",this.createdByUserLogin);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductPrice");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productPriceTypeId;

    public java.lang.String getproductPriceTypeId() {
        return productPriceTypeId;
    }

    public void setproductPriceTypeId(java.lang.String productPriceTypeId) {
        this.productPriceTypeId = productPriceTypeId;
    }
    private java.lang.String taxInPrice;

    public java.lang.String gettaxInPrice() {
        return taxInPrice;
    }

    public void settaxInPrice(java.lang.String taxInPrice) {
        this.taxInPrice = taxInPrice;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.lang.String priceWithoutTax;

    public java.lang.String getpriceWithoutTax() {
        return priceWithoutTax;
    }

    public void setpriceWithoutTax(java.lang.String priceWithoutTax) {
        this.priceWithoutTax = priceWithoutTax;
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
    private java.lang.String productPricePurposeId;

    public java.lang.String getproductPricePurposeId() {
        return productPricePurposeId;
    }

    public void setproductPricePurposeId(java.lang.String productPricePurposeId) {
        this.productPricePurposeId = productPricePurposeId;
    }
    private java.lang.String customPriceCalcService;

    public java.lang.String getcustomPriceCalcService() {
        return customPriceCalcService;
    }

    public void setcustomPriceCalcService(java.lang.String customPriceCalcService) {
        this.customPriceCalcService = customPriceCalcService;
    }
    private java.sql.Timestamp createdDate;

    public java.sql.Timestamp getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(java.sql.Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    private java.lang.String productStoreGroupId;

    public java.lang.String getproductStoreGroupId() {
        return productStoreGroupId;
    }

    public void setproductStoreGroupId(java.lang.String productStoreGroupId) {
        this.productStoreGroupId = productStoreGroupId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String termUomId;

    public java.lang.String gettermUomId() {
        return termUomId;
    }

    public void settermUomId(java.lang.String termUomId) {
        this.termUomId = termUomId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String priceWithTax;

    public java.lang.String getpriceWithTax() {
        return priceWithTax;
    }

    public void setpriceWithTax(java.lang.String priceWithTax) {
        this.priceWithTax = priceWithTax;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String taxAuthGeoId;

    public java.lang.String gettaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void settaxAuthGeoId(java.lang.String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }
    private java.lang.String taxAmount;

    public java.lang.String gettaxAmount() {
        return taxAmount;
    }

    public void settaxAmount(java.lang.String taxAmount) {
        this.taxAmount = taxAmount;
    }
    private java.math.BigDecimal price;

    public java.math.BigDecimal getprice() {
        return price;
    }

    public void setprice(java.math.BigDecimal price) {
        this.price = price;
    }
    private java.lang.String lastModifiedByUserLogin;

    public java.lang.String getlastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setlastModifiedByUserLogin(java.lang.String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.sql.Timestamp lastModifiedDate;

    public java.sql.Timestamp getlastModifiedDate() {
        return lastModifiedDate;
    }

    public void setlastModifiedDate(java.sql.Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    private java.lang.String taxAuthPartyId;

    public java.lang.String gettaxAuthPartyId() {
        return taxAuthPartyId;
    }

    public void settaxAuthPartyId(java.lang.String taxAuthPartyId) {
        this.taxAuthPartyId = taxAuthPartyId;
    }
    private java.math.BigDecimal taxPercentage;

    public java.math.BigDecimal gettaxPercentage() {
        return taxPercentage;
    }

    public void settaxPercentage(java.math.BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String createdByUserLogin;

    public java.lang.String getcreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setcreatedByUserLogin(java.lang.String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductPrice> objectList = new ArrayList<ProductPrice>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductPrice(genVal));
        }
        return objectList;
    }
}
