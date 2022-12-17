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

public class ProductStoreGroup implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ProductStoreGroup.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductStoreGroup(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductStoreGroup() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"productStoreGroupName", "Product Store Group Name"},
        {"productStoreGroupId", "Product Store Group Id"},
        {"productStoreGroupTypeId", "Product Store Group Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"primaryParentGroupId", "Primary Parent Group Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = null;
        this.productStoreGroupName = "";
        this.productStoreGroupId = null;
        this.productStoreGroupTypeId = null;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.primaryParentGroupId = null;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.productStoreGroupName = (java.lang.String) genVal.get("productStoreGroupName");
        this.productStoreGroupId = (java.lang.String) genVal.get("productStoreGroupId");
        this.productStoreGroupTypeId = (java.lang.String) genVal.get("productStoreGroupTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.primaryParentGroupId = (java.lang.String) genVal.get("primaryParentGroupId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("productStoreGroupName", OrderMaxUtility.getValidEntityString(this.productStoreGroupName));
        val.set("productStoreGroupId", OrderMaxUtility.getValidEntityString(this.productStoreGroupId));
        val.set("productStoreGroupTypeId", OrderMaxUtility.getValidEntityString(this.productStoreGroupTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("primaryParentGroupId", OrderMaxUtility.getValidEntityString(this.primaryParentGroupId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("description", this.description);
        valueMap.put("productStoreGroupName", this.productStoreGroupName);
        valueMap.put("productStoreGroupId", this.productStoreGroupId);
        valueMap.put("productStoreGroupTypeId", this.productStoreGroupTypeId);
        valueMap.put("primaryParentGroupId", this.primaryParentGroupId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductStoreGroup");
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
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String productStoreGroupName;

    public java.lang.String getproductStoreGroupName() {
        return productStoreGroupName;
    }

    public void setproductStoreGroupName(java.lang.String productStoreGroupName) {
        this.productStoreGroupName = productStoreGroupName;
    }
    private java.lang.String productStoreGroupId;

    public java.lang.String getproductStoreGroupId() {
        return productStoreGroupId;
    }

    public void setproductStoreGroupId(java.lang.String productStoreGroupId) {
        this.productStoreGroupId = productStoreGroupId;
    }
    private java.lang.String productStoreGroupTypeId;

    public java.lang.String getproductStoreGroupTypeId() {
        return productStoreGroupTypeId;
    }

    public void setproductStoreGroupTypeId(java.lang.String productStoreGroupTypeId) {
        this.productStoreGroupTypeId = productStoreGroupTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String primaryParentGroupId;

    public java.lang.String getprimaryParentGroupId() {
        return primaryParentGroupId;
    }

    public void setprimaryParentGroupId(java.lang.String primaryParentGroupId) {
        this.primaryParentGroupId = primaryParentGroupId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStoreGroup> objectList = new ArrayList<ProductStoreGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStoreGroup(genVal));
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
                orderToStringBuilder.append(getproductStoreGroupId());
                orderToStringBuilder.append("]");
            }
        } else {
            orderToStringBuilder.append(getproductStoreGroupId());
        }

        return orderToStringBuilder.toString();
    }
}
