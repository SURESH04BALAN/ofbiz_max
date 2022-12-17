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

public class ProductFeatureAndAppl implements GenericValueObjectInterface {

    public static final String module = ProductFeatureAndAppl.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductFeatureAndAppl(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public ProductFeatureAndAppl() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productFeatureTypeId", "Product Feature Type Id"},
        {"amount", "Amount"},
        {"productId", "Product Id"},
        {"sequenceNum", "Sequence Num"},
        {"recurringAmount", "Recurring Amount"},
        {"idCode", "Id Code"},
        {"productFeatureId", "Product Feature Id"},
        {"numberSpecified", "Number Specified"},
        {"productFeatureCategoryId", "Product Feature Category Id"},
        {"description", "Description"},
        {"uomId", "Uom Id"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
        {"defaultSequenceNum", "Default Sequence Num"},
        {"defaultAmount", "Default Amount"},
        {"productFeatureApplTypeId", "Product Feature Appl Type Id"},
        {"abbrev", "Abbrev"},};

    protected void initObject() {
        this.productFeatureTypeId = "";
        this.amount = "";
        this.productId = "";
        this.sequenceNum = new Long(0);
        this.recurringAmount = "";
        this.idCode = "";
        this.productFeatureId = "";
        this.numberSpecified = "";
        this.productFeatureCategoryId = "";
        this.description = "";
        this.uomId = "";
        this.thruDate = UtilDateTime.nowTimestamp();
        this.fromDate = UtilDateTime.nowTimestamp();
        this.defaultSequenceNum = "";
        this.defaultAmount = "";
        this.productFeatureApplTypeId = "";
        this.abbrev = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productFeatureTypeId = (java.lang.String) genVal.get("productFeatureTypeId");
        this.amount = (java.lang.String) genVal.get("amount");
        this.productId = (java.lang.String) genVal.get("productId");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.recurringAmount = (java.lang.String) genVal.get("recurringAmount");
        this.idCode = (java.lang.String) genVal.get("idCode");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.numberSpecified = (java.lang.String) genVal.get("numberSpecified");
        this.productFeatureCategoryId = (java.lang.String) genVal.get("productFeatureCategoryId");
        this.description = (java.lang.String) genVal.get("description");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.defaultSequenceNum = (java.lang.String) genVal.get("defaultSequenceNum");
        this.defaultAmount = (java.lang.String) genVal.get("defaultAmount");
        this.productFeatureApplTypeId = (java.lang.String) genVal.get("productFeatureApplTypeId");
        this.abbrev = (java.lang.String) genVal.get("abbrev");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productFeatureTypeId", OrderMaxUtility.getValidEntityString(this.productFeatureTypeId));
        val.set("amount", OrderMaxUtility.getValidEntityString(this.amount));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("sequenceNum", this.sequenceNum);
        val.set("recurringAmount", OrderMaxUtility.getValidEntityString(this.recurringAmount));
        val.set("idCode", OrderMaxUtility.getValidEntityString(this.idCode));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("numberSpecified", OrderMaxUtility.getValidEntityString(this.numberSpecified));
        val.set("productFeatureCategoryId", OrderMaxUtility.getValidEntityString(this.productFeatureCategoryId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("defaultSequenceNum", OrderMaxUtility.getValidEntityString(this.defaultSequenceNum));
        val.set("defaultAmount", OrderMaxUtility.getValidEntityString(this.defaultAmount));
        val.set("productFeatureApplTypeId", OrderMaxUtility.getValidEntityString(this.productFeatureApplTypeId));
        val.set("abbrev", OrderMaxUtility.getValidEntityString(this.abbrev));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productFeatureTypeId", this.productFeatureTypeId);
        valueMap.put("amount", this.amount);
        valueMap.put("productId", this.productId);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("recurringAmount", this.recurringAmount);
        valueMap.put("idCode", this.idCode);
        valueMap.put("productFeatureId", this.productFeatureId);
        valueMap.put("numberSpecified", this.numberSpecified);
        valueMap.put("productFeatureCategoryId", this.productFeatureCategoryId);
        valueMap.put("description", this.description);
        valueMap.put("uomId", this.uomId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("defaultSequenceNum", this.defaultSequenceNum);
        valueMap.put("defaultAmount", this.defaultAmount);
        valueMap.put("productFeatureApplTypeId", this.productFeatureApplTypeId);
        valueMap.put("abbrev", this.abbrev);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductFeatureAndAppl");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productFeatureTypeId;

    public java.lang.String getProductFeatureTypeId() {
        return productFeatureTypeId;
    }

    public void setProductFeatureTypeId(java.lang.String productFeatureTypeId) {
        this.productFeatureTypeId = productFeatureTypeId;
    }
    private java.lang.String amount;

    public java.lang.String getAmount() {
        return amount;
    }

    public void setAmount(java.lang.String amount) {
        this.amount = amount;
    }
    private java.lang.String productId;

    public java.lang.String getProductId() {
        return productId;
    }

    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.Long sequenceNum;

    public java.lang.Long getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.lang.Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }
    private java.lang.String recurringAmount;

    public java.lang.String getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(java.lang.String recurringAmount) {
        this.recurringAmount = recurringAmount;
    }
    private java.lang.String idCode;

    public java.lang.String getIdCode() {
        return idCode;
    }

    public void setIdCode(java.lang.String idCode) {
        this.idCode = idCode;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getProductFeatureId() {
        return productFeatureId;
    }

    public void setProductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.lang.String numberSpecified;

    public java.lang.String getNumberSpecified() {
        return numberSpecified;
    }

    public void setNumberSpecified(java.lang.String numberSpecified) {
        this.numberSpecified = numberSpecified;
    }
    private java.lang.String productFeatureCategoryId;

    public java.lang.String getProductFeatureCategoryId() {
        return productFeatureCategoryId;
    }

    public void setProductFeatureCategoryId(java.lang.String productFeatureCategoryId) {
        this.productFeatureCategoryId = productFeatureCategoryId;
    }
    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String uomId;

    public java.lang.String getUomId() {
        return uomId;
    }

    public void setUomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.lang.String defaultSequenceNum;

    public java.lang.String getDefaultSequenceNum() {
        return defaultSequenceNum;
    }

    public void setDefaultSequenceNum(java.lang.String defaultSequenceNum) {
        this.defaultSequenceNum = defaultSequenceNum;
    }
    private java.lang.String defaultAmount;

    public java.lang.String getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(java.lang.String defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
    private java.lang.String productFeatureApplTypeId;

    public java.lang.String getProductFeatureApplTypeId() {
        return productFeatureApplTypeId;
    }

    public void setProductFeatureApplTypeId(java.lang.String productFeatureApplTypeId) {
        this.productFeatureApplTypeId = productFeatureApplTypeId;
    }
    private java.lang.String abbrev;

    public java.lang.String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(java.lang.String abbrev) {
        this.abbrev = abbrev;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductFeatureAndAppl> objectList = new ArrayList<ProductFeatureAndAppl>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductFeatureAndAppl(genVal));
        }
        return objectList;
    }
}
