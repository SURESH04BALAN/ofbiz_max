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

public class OrderTerm implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = OrderTerm.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderTerm(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderTerm() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"termDays", "Term Days"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"termValue", "Term Value"},
        {"uomId", "Uom Id"},
        {"orderItemSeqId", "Order Item Seq Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"termTypeId", "Term Type Id"},
        {"description", "Description"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"orderId", "Order Id"},
        {"textValue", "Text Value"},};

    protected void initObject() {
        this.termDays = new Long(0);
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.termValue = java.math.BigDecimal.ZERO;
        this.uomId = "";
        this.orderItemSeqId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.termTypeId = "";
        this.description = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.orderId = "";
        this.textValue = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.termDays = (java.lang.Long) genVal.get("termDays");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.termValue = (java.math.BigDecimal) genVal.get("termValue");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.termTypeId = (java.lang.String) genVal.get("termTypeId");
        this.description = (java.lang.String) genVal.get("description");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.orderId = (java.lang.String) genVal.get("orderId");
        this.textValue = (java.lang.String) genVal.get("textValue");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("termDays", this.termDays);
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("termValue", OrderMaxUtility.getValidBigDecimal(this.termValue));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("orderItemSeqId", OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("termTypeId", OrderMaxUtility.getValidEntityString(this.termTypeId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
        val.set("textValue", OrderMaxUtility.getValidEntityString(this.textValue));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("termDays", this.termDays);
        valueMap.put("termValue", this.termValue);
        valueMap.put("uomId", this.uomId);
        valueMap.put("orderItemSeqId", this.orderItemSeqId);
        valueMap.put("termTypeId", this.termTypeId);
        valueMap.put("description", this.description);
        valueMap.put("orderId", this.orderId);
        valueMap.put("textValue", this.textValue);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderTerm");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.Long termDays;

    public java.lang.Long gettermDays() {
        return termDays;
    }

    public void settermDays(java.lang.Long termDays) {
        this.termDays = termDays;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.math.BigDecimal termValue;

    public java.math.BigDecimal gettermValue() {
        return termValue;
    }

    public void settermValue(java.math.BigDecimal termValue) {
        this.termValue = termValue;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String orderItemSeqId;

    public java.lang.String getorderItemSeqId() {
        return orderItemSeqId;
    }

    public void setorderItemSeqId(java.lang.String orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
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
    private java.lang.String termTypeId;

    public java.lang.String gettermTypeId() {
        return termTypeId;
    }

    public void settermTypeId(java.lang.String termTypeId) {
        this.termTypeId = termTypeId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }
    private java.lang.String textValue;

    public java.lang.String gettextValue() {
        return textValue;
    }

    public void settextValue(java.lang.String textValue) {
        this.textValue = textValue;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderTerm> objectList = new ArrayList<OrderTerm>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderTerm(genVal));
        }
        return objectList;
    }
    
    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {
   
            orderToStringBuilder.append(getdescription());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(this.getorderId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getorderId());
        }

        return orderToStringBuilder.toString();
    }        
}
