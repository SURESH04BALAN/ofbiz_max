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

public class ProductContent implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ProductContent.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductContent(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductContent() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"purchaseThruDate", "Purchase Thru Date"},
        {"purchaseFromDate", "Purchase From Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"useTimeUomId", "Use Time Uom Id"},
        {"thruDate", "Thru Date"},
        {"useRoleTypeId", "Use Role Type Id"},
        {"contentId", "Content Id"},
        {"sequenceNum", "Sequence Num"},
        {"productId", "Product Id"},
        {"fromDate", "From Date"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"useCountLimit", "Use Count Limit"},
        {"productContentTypeId", "Product Content Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"useTime", "Use Time"},};

    protected void initObject() {
        this.purchaseThruDate = null;
        this.purchaseFromDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.useTimeUomId = "";
        this.thruDate = null;
        this.useRoleTypeId = "";
        this.contentId = "";
        this.sequenceNum = Long.valueOf(0);
        this.productId = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.useCountLimit = null;
        this.productContentTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.useTime = null;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.purchaseThruDate = (java.sql.Timestamp) genVal.get("purchaseThruDate");
        this.purchaseFromDate = (java.sql.Timestamp) genVal.get("purchaseFromDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.useTimeUomId = (java.lang.String) genVal.get("useTimeUomId");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.useRoleTypeId = (java.lang.String) genVal.get("useRoleTypeId");
        this.contentId = (java.lang.String) genVal.get("contentId");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.productId = (java.lang.String) genVal.get("productId");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.useCountLimit = (java.lang.Long) genVal.get("useCountLimit");
        this.productContentTypeId = (java.lang.String) genVal.get("productContentTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.useTime = (java.lang.Long) genVal.get("useTime");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("purchaseThruDate", OrderMaxUtility.getValidEntityTimestamp(this.purchaseThruDate));
        val.set("purchaseFromDate", OrderMaxUtility.getValidEntityTimestamp(this.purchaseFromDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("useTimeUomId", OrderMaxUtility.getValidEntityString(this.useTimeUomId));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("useRoleTypeId", OrderMaxUtility.getValidEntityString(this.useRoleTypeId));
        val.set("contentId", OrderMaxUtility.getValidEntityString(this.contentId));
        val.set("sequenceNum", OrderMaxUtility.getValidEntityLong(this.sequenceNum));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("useCountLimit", OrderMaxUtility.getValidEntityLong(this.useCountLimit));
        val.set("productContentTypeId", OrderMaxUtility.getValidEntityString(this.productContentTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("useTime", OrderMaxUtility.getValidEntityLong(this.useTime));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("purchaseThruDate", this.purchaseThruDate);
        valueMap.put("purchaseFromDate", this.purchaseFromDate);
        valueMap.put("useTimeUomId", this.useTimeUomId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("useRoleTypeId", this.useRoleTypeId);
        valueMap.put("contentId", this.contentId);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("productId", this.productId);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("useCountLimit", this.useCountLimit);
        valueMap.put("productContentTypeId", this.productContentTypeId);
        valueMap.put("useTime", this.useTime);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductContent");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.sql.Timestamp purchaseThruDate;

    public java.sql.Timestamp getpurchaseThruDate() {
        return purchaseThruDate;
    }

    public void setpurchaseThruDate(java.sql.Timestamp purchaseThruDate) {
        this.purchaseThruDate = purchaseThruDate;
    }
    private java.sql.Timestamp purchaseFromDate;

    public java.sql.Timestamp getpurchaseFromDate() {
        return purchaseFromDate;
    }

    public void setpurchaseFromDate(java.sql.Timestamp purchaseFromDate) {
        this.purchaseFromDate = purchaseFromDate;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String useTimeUomId;

    public java.lang.String getuseTimeUomId() {
        return useTimeUomId;
    }

    public void setuseTimeUomId(java.lang.String useTimeUomId) {
        this.useTimeUomId = useTimeUomId;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.lang.String useRoleTypeId;

    public java.lang.String getuseRoleTypeId() {
        return useRoleTypeId;
    }

    public void setuseRoleTypeId(java.lang.String useRoleTypeId) {
        this.useRoleTypeId = useRoleTypeId;
    }
    private java.lang.String contentId;

    public java.lang.String getcontentId() {
        return contentId;
    }

    public void setcontentId(java.lang.String contentId) {
        this.contentId = contentId;
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
    private java.lang.Long useCountLimit;

    public java.lang.Long getuseCountLimit() {
        return useCountLimit;
    }

    public void setuseCountLimit(java.lang.Long useCountLimit) {
        this.useCountLimit = useCountLimit;
    }
    private java.lang.String productContentTypeId;

    public java.lang.String getproductContentTypeId() {
        return productContentTypeId;
    }

    public void setproductContentTypeId(java.lang.String productContentTypeId) {
        this.productContentTypeId = productContentTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.Long useTime;

    public java.lang.Long getuseTime() {
        return useTime;
    }

    public void setuseTime(java.lang.Long useTime) {
        this.useTime = useTime;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductContent> objectList = new ArrayList<ProductContent>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductContent(genVal));
        }
        return objectList;
    }
   @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(this.contentId);

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(contentId);
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(contentId);
        }

        return orderToStringBuilder.toString();
    }    
}
