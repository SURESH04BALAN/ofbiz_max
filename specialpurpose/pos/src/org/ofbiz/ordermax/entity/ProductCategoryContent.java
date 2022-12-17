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

public class ProductCategoryContent implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ProductCategoryContent.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductCategoryContent(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public ProductCategoryContent() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"purchaseFromDate", "Purchase From Date"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"contentId", "Content Id"},
        {"useDaysLimit", "Use Days Limit"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"useCountLimit", "Use Count Limit"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
        {"prodCatContentTypeId", "Prod Cat Content Type Id"},
        {"productCategoryId", "Product Category Id"},
        {"purchaseThruDate", "Purchase Thru Date"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.purchaseFromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.contentId = "";
        this.useDaysLimit = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.useCountLimit = "";
        this.thruDate = UtilDateTime.nowTimestamp();
        this.fromDate = UtilDateTime.nowTimestamp();
        this.prodCatContentTypeId = "";
        this.productCategoryId = "";
        this.purchaseThruDate = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.purchaseFromDate = (java.sql.Timestamp) genVal.get("purchaseFromDate");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.contentId = (java.lang.String) genVal.get("contentId");
        this.useDaysLimit = (java.lang.String) genVal.get("useDaysLimit");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.useCountLimit = (java.lang.String) genVal.get("useCountLimit");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.prodCatContentTypeId = (java.lang.String) genVal.get("prodCatContentTypeId");
        this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
        this.purchaseThruDate = (java.sql.Timestamp) genVal.get("purchaseThruDate");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("purchaseFromDate", OrderMaxUtility.getValidEntityTimestamp(this.purchaseFromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("contentId", OrderMaxUtility.getValidEntityString(this.contentId));
        val.set("useDaysLimit", OrderMaxUtility.getValidEntityString(this.useDaysLimit));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("useCountLimit", OrderMaxUtility.getValidEntityString(this.useCountLimit));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("prodCatContentTypeId", OrderMaxUtility.getValidEntityString(this.prodCatContentTypeId));
        val.set("productCategoryId", OrderMaxUtility.getValidEntityString(this.productCategoryId));
        val.set("purchaseThruDate", OrderMaxUtility.getValidEntityTimestamp(this.purchaseThruDate));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("purchaseFromDate", this.purchaseFromDate);
        valueMap.put("contentId", this.contentId);
        valueMap.put("useDaysLimit", this.useDaysLimit);
        valueMap.put("useCountLimit", this.useCountLimit);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("prodCatContentTypeId", this.prodCatContentTypeId);
        valueMap.put("productCategoryId", this.productCategoryId);
        valueMap.put("purchaseThruDate", this.purchaseThruDate);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductCategoryContent");
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

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp purchaseFromDate;

    public java.sql.Timestamp getpurchaseFromDate() {
        return purchaseFromDate;
    }

    public void setpurchaseFromDate(java.sql.Timestamp purchaseFromDate) {
        this.purchaseFromDate = purchaseFromDate;
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
    private java.lang.String contentId;

    public java.lang.String getcontentId() {
        return contentId;
    }

    public void setcontentId(java.lang.String contentId) {
        this.contentId = contentId;
    }
    private java.lang.String useDaysLimit;

    public java.lang.String getuseDaysLimit() {
        return useDaysLimit;
    }

    public void setuseDaysLimit(java.lang.String useDaysLimit) {
        this.useDaysLimit = useDaysLimit;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String useCountLimit;

    public java.lang.String getuseCountLimit() {
        return useCountLimit;
    }

    public void setuseCountLimit(java.lang.String useCountLimit) {
        this.useCountLimit = useCountLimit;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.lang.String prodCatContentTypeId;

    public java.lang.String getprodCatContentTypeId() {
        return prodCatContentTypeId;
    }

    public void setprodCatContentTypeId(java.lang.String prodCatContentTypeId) {
        this.prodCatContentTypeId = prodCatContentTypeId;
    }
    private java.lang.String productCategoryId;

    public java.lang.String getproductCategoryId() {
        return productCategoryId;
    }

    public void setproductCategoryId(java.lang.String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    private java.sql.Timestamp purchaseThruDate;

    public java.sql.Timestamp getpurchaseThruDate() {
        return purchaseThruDate;
    }

    public void setpurchaseThruDate(java.sql.Timestamp purchaseThruDate) {
        this.purchaseThruDate = purchaseThruDate;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductCategoryContent> objectList = new ArrayList<ProductCategoryContent>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductCategoryContent(genVal));
        }
        return objectList;
    }
    
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
