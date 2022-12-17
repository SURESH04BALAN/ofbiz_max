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

public class ProductFeatureAppl implements GenericValueObjectInterface {

    public static final String module = ProductFeatureAppl.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductFeatureAppl(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public ProductFeatureAppl() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"amount", "Amount"},
        {"productId", "Product Id"},
        {"sequenceNum", "Sequence Num"},
        {"recurringAmount", "Recurring Amount"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"productFeatureId", "Product Feature Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
        {"productFeatureApplTypeId", "Product Feature Appl Type Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.amount = null;
        this.productId = null;
        this.sequenceNum = new Long(0);
        this.recurringAmount = null;
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.productFeatureId = null;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.thruDate = null;
        this.fromDate = null;
        this.productFeatureApplTypeId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.amount = (BigDecimal) genVal.get("amount");
        this.productId = (java.lang.String) genVal.get("productId");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.recurringAmount = (BigDecimal) genVal.get("recurringAmount");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.productFeatureApplTypeId = (java.lang.String) genVal.get("productFeatureApplTypeId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("amount", OrderMaxUtility.getValidBigDecimal(this.amount));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("sequenceNum", this.sequenceNum);
        val.set("recurringAmount", OrderMaxUtility.getValidBigDecimal(this.recurringAmount));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("productFeatureApplTypeId", OrderMaxUtility.getValidEntityString(this.productFeatureApplTypeId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("amount", this.amount);
        valueMap.put("productId", this.productId);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("recurringAmount", this.recurringAmount);
        valueMap.put("productFeatureId", this.productFeatureId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("productFeatureApplTypeId", this.productFeatureApplTypeId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductFeatureAppl");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
    private BigDecimal recurringAmount;

    public BigDecimal getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(BigDecimal recurringAmount) {
        this.recurringAmount = recurringAmount;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getProductFeatureId() {
        return productFeatureId;
    }

    public void setProductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
    private java.lang.String productFeatureApplTypeId;

    public java.lang.String getProductFeatureApplTypeId() {
        return productFeatureApplTypeId;
    }

    public void setProductFeatureApplTypeId(java.lang.String productFeatureApplTypeId) {
        this.productFeatureApplTypeId = productFeatureApplTypeId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductFeatureAppl> objectList = new ArrayList<ProductFeatureAppl>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductFeatureAppl(genVal));
        }
        return objectList;
    }
}
