package org.ofbiz.ordermax.entity;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductCategory implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ProductCategory.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductCategory(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
          Debug.logInfo(ex, module);
        }
    }

    public ProductCategory() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productCategoryId", "Product Category Id"},
        {"categoryName", "Category Name"},        
        {"longDescription", "Long Description"},
        {"productCategoryTypeId", "Product Category Type Id"},
        {"detailScreen", "Detail Screen"},
//        {"categoryData", "Category Data"},
        {"description", "Description"},
        {"linkTwoImageUrl", "Link Two Image Url"},
        {"linkOneImageUrl", "Link One Image Url"},
        {"primaryParentCategoryId", "Primary Parent Category Id"},
        {"categoryImageUrl", "Category Image Url"},
        {"showInSelect", "Show In Select"},};

    protected void initObject() {
        this.longDescription = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.productCategoryTypeId = "";
        this.detailScreen = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.categoryData = "";
        this.description = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.linkTwoImageUrl = "";
        this.categoryName = "";
        this.productCategoryId = "";
        this.linkOneImageUrl = "";
        this.primaryParentCategoryId = "";
        this.categoryImageUrl = "";
        this.showInSelect = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.longDescription = (java.lang.String) genVal.get("longDescription");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.productCategoryTypeId = (java.lang.String) genVal.get("productCategoryTypeId");
        this.detailScreen = (java.lang.String) genVal.get("detailScreen");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.categoryData = (java.lang.String) genVal.get("categoryData");
        this.description = (java.lang.String) genVal.get("description");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.linkTwoImageUrl = (java.lang.String) genVal.get("linkTwoImageUrl");
        this.categoryName = (java.lang.String) genVal.get("categoryName");
        this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
        this.linkOneImageUrl = (java.lang.String) genVal.get("linkOneImageUrl");
        this.primaryParentCategoryId = (java.lang.String) genVal.get("primaryParentCategoryId");
        this.categoryImageUrl = (java.lang.String) genVal.get("categoryImageUrl");
        this.showInSelect = (java.lang.String) genVal.get("showInSelect");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("longDescription", OrderMaxUtility.getValidEntityString(this.longDescription));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("productCategoryTypeId", OrderMaxUtility.getValidEntityString(this.productCategoryTypeId));
        val.set("detailScreen", OrderMaxUtility.getValidEntityString(this.detailScreen));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("categoryData", OrderMaxUtility.getValidEntityString(this.categoryData));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("linkTwoImageUrl", OrderMaxUtility.getValidEntityString(this.linkTwoImageUrl));
        val.set("categoryName", OrderMaxUtility.getValidEntityString(this.categoryName));
        val.set("productCategoryId", OrderMaxUtility.getValidEntityString(this.productCategoryId));
        val.set("linkOneImageUrl", OrderMaxUtility.getValidEntityString(this.linkOneImageUrl));
        val.set("primaryParentCategoryId", OrderMaxUtility.getValidEntityString(this.primaryParentCategoryId));
        val.set("categoryImageUrl", OrderMaxUtility.getValidEntityString(this.categoryImageUrl));
        val.set("showInSelect", OrderMaxUtility.getValidEntityString(this.showInSelect));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("longDescription", this.longDescription);
        valueMap.put("productCategoryTypeId", this.productCategoryTypeId);
        valueMap.put("detailScreen", this.detailScreen);
        valueMap.put("categoryData", this.categoryData);
        valueMap.put("description", this.description);
        valueMap.put("linkTwoImageUrl", this.linkTwoImageUrl);
        valueMap.put("categoryName", this.categoryName);
        valueMap.put("productCategoryId", this.productCategoryId);
        valueMap.put("linkOneImageUrl", this.linkOneImageUrl);
        valueMap.put("primaryParentCategoryId", this.primaryParentCategoryId);
        valueMap.put("categoryImageUrl", this.categoryImageUrl);
        valueMap.put("showInSelect", this.showInSelect);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductCategory");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String longDescription;

    public java.lang.String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(java.lang.String longDescription) {
        this.longDescription = longDescription;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String productCategoryTypeId;

    public java.lang.String getProductCategoryTypeId() {
        return productCategoryTypeId;
    }

    public void setProductCategoryTypeId(java.lang.String productCategoryTypeId) {
        this.productCategoryTypeId = productCategoryTypeId;
    }
    private java.lang.String detailScreen;

    public java.lang.String getDetailScreen() {
        return detailScreen;
    }

    public void setDetailScreen(java.lang.String detailScreen) {
        this.detailScreen = detailScreen;
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
    private java.lang.String categoryData;

    public java.lang.String getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(java.lang.String categoryData) {
        this.categoryData = categoryData;
    }
    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String linkTwoImageUrl;

    public java.lang.String getLinkTwoImageUrl() {
        return linkTwoImageUrl;
    }

    public void setLinkTwoImageUrl(java.lang.String linkTwoImageUrl) {
        this.linkTwoImageUrl = linkTwoImageUrl;
    }
    private java.lang.String categoryName;

    public java.lang.String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(java.lang.String categoryName) {
        this.categoryName = categoryName;
    }
    private java.lang.String productCategoryId;

    public java.lang.String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(java.lang.String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    private java.lang.String linkOneImageUrl;

    public java.lang.String getLinkOneImageUrl() {
        return linkOneImageUrl;
    }

    public void setLinkOneImageUrl(java.lang.String linkOneImageUrl) {
        this.linkOneImageUrl = linkOneImageUrl;
    }
    private java.lang.String primaryParentCategoryId;

    public java.lang.String getPrimaryParentCategoryId() {
        return primaryParentCategoryId;
    }

    public void setPrimaryParentCategoryId(java.lang.String primaryParentCategoryId) {
        this.primaryParentCategoryId = primaryParentCategoryId;
    }
    private java.lang.String categoryImageUrl;

    public java.lang.String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(java.lang.String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
    private java.lang.String showInSelect;

    public java.lang.String getShowInSelect() {
        return showInSelect;
    }

    public void setShowInSelect(java.lang.String showInSelect) {
        this.showInSelect = showInSelect;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductCategory> objectList = new ArrayList<ProductCategory>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductCategory(genVal));
        }
        return objectList;
    }
    
        @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(getCategoryName());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getProductCategoryId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getProductCategoryId());
        }

        return orderToStringBuilder.toString();
    }
}
