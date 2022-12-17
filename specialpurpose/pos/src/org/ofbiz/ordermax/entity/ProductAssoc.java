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

public class ProductAssoc implements GenericValueObjectInterface {

    public static final String module = ProductAssoc.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductAssoc(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductAssoc() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productAssocTypeId", "Product Assoc Type Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"thruDate", "Thru Date"},
        {"productIdTo", "Product Id To"},
        {"reason", "Reason"},
        {"sequenceNum", "Sequence Num"},
        {"productId", "Product Id"},
        {"estimateCalcMethod", "Estimate Calc Method"},
        {"routingWorkEffortId", "Routing Work Effort Id"},
        {"fromDate", "From Date"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"instruction", "Instruction"},
        {"quantity", "Quantity"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"scrapFactor", "Scrap Factor"},
        {"recurrenceInfoId", "Recurrence Info Id"},};

    
    protected void initObject() {
        this.productAssocTypeId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.thruDate = null;
        this.productIdTo = "";
        this.reason = "";
        this.sequenceNum = null;
        this.productId = "";
        this.estimateCalcMethod = "";
        this.routingWorkEffortId = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.instruction = "";
        this.quantity = java.math.BigDecimal.ZERO;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.scrapFactor = null;
        this.recurrenceInfoId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productAssocTypeId = (java.lang.String) genVal.get("productAssocTypeId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.productIdTo = (java.lang.String) genVal.get("productIdTo");
        this.reason = (java.lang.String) genVal.get("reason");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.productId = (java.lang.String) genVal.get("productId");
        this.estimateCalcMethod = (java.lang.String) genVal.get("estimateCalcMethod");
        this.routingWorkEffortId = (java.lang.String) genVal.get("routingWorkEffortId");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.instruction = (java.lang.String) genVal.get("instruction");
        this.quantity = (java.math.BigDecimal) genVal.get("quantity");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.scrapFactor = (java.lang.Long) genVal.get("scrapFactor");
        this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productAssocTypeId", OrderMaxUtility.getValidEntityString(this.productAssocTypeId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("productIdTo", OrderMaxUtility.getValidEntityString(this.productIdTo));
        val.set("reason", OrderMaxUtility.getValidEntityString(this.reason));
        val.set("sequenceNum", OrderMaxUtility.getValidEntityLong(this.sequenceNum));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("estimateCalcMethod", OrderMaxUtility.getValidEntityString(this.estimateCalcMethod));
        val.set("routingWorkEffortId", OrderMaxUtility.getValidEntityString(this.routingWorkEffortId));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("instruction", OrderMaxUtility.getValidEntityString(this.instruction));
        val.set("quantity", OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("scrapFactor", OrderMaxUtility.getValidEntityLong(this.scrapFactor));
        val.set("recurrenceInfoId", OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productAssocTypeId", this.productAssocTypeId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("productIdTo", this.productIdTo);
        valueMap.put("reason", this.reason);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("productId", this.productId);
        valueMap.put("estimateCalcMethod", this.estimateCalcMethod);
        valueMap.put("routingWorkEffortId", this.routingWorkEffortId);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("instruction", this.instruction);
        valueMap.put("quantity", this.quantity);
        valueMap.put("scrapFactor", this.scrapFactor);
        valueMap.put("recurrenceInfoId", this.recurrenceInfoId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductAssoc");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productAssocTypeId;

    public java.lang.String getproductAssocTypeId() {
        return productAssocTypeId;
    }

    public void setproductAssocTypeId(java.lang.String productAssocTypeId) {
        this.productAssocTypeId = productAssocTypeId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.lang.String productIdTo;

    public java.lang.String getproductIdTo() {
        return productIdTo;
    }

    public void setproductIdTo(java.lang.String productIdTo) {
        this.productIdTo = productIdTo;
    }
    private java.lang.String reason;

    public java.lang.String getreason() {
        return reason;
    }

    public void setreason(java.lang.String reason) {
        this.reason = reason;
    }
    private java.lang.Long sequenceNum;

    public java.lang.Long getsequenceNum() {
        return sequenceNum;
    }

    public void setsequenceNum(java.lang.Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String estimateCalcMethod;

    public java.lang.String getestimateCalcMethod() {
        return estimateCalcMethod;
    }

    public void setestimateCalcMethod(java.lang.String estimateCalcMethod) {
        this.estimateCalcMethod = estimateCalcMethod;
    }
    private java.lang.String routingWorkEffortId;

    public java.lang.String getroutingWorkEffortId() {
        return routingWorkEffortId;
    }

    public void setroutingWorkEffortId(java.lang.String routingWorkEffortId) {
        this.routingWorkEffortId = routingWorkEffortId;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
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
    private java.lang.String instruction;

    public java.lang.String getinstruction() {
        return instruction;
    }

    public void setinstruction(java.lang.String instruction) {
        this.instruction = instruction;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getquantity() {
        return quantity;
    }

    public void setquantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.Long scrapFactor;

    public java.lang.Long getscrapFactor() {
        return scrapFactor;
    }

    public void setscrapFactor(java.lang.Long scrapFactor) {
        this.scrapFactor = scrapFactor;
    }
    private java.lang.String recurrenceInfoId;

    public java.lang.String getrecurrenceInfoId() {
        return recurrenceInfoId;
    }

    public void setrecurrenceInfoId(java.lang.String recurrenceInfoId) {
        this.recurrenceInfoId = recurrenceInfoId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductAssoc> objectList = new ArrayList<ProductAssoc>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductAssoc(genVal));
        }
        return objectList;
    }
}
