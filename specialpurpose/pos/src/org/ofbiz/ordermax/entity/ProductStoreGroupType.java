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

public class ProductStoreGroupType implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ProductStoreGroupType.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductStoreGroupType(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public ProductStoreGroupType() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"productStoreGroupTypeId", "Product Store Group Type Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.productStoreGroupTypeId = "";
        this.lastUpdatedStamp = "";
        this.createdTxStamp = "";
        this.createdStamp = "";
        this.description = "";
        this.lastUpdatedTxStamp = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productStoreGroupTypeId = (java.lang.String) genVal.get("productStoreGroupTypeId");
        this.lastUpdatedStamp = (java.lang.String) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.lang.String) genVal.get("createdTxStamp");
        this.createdStamp = (java.lang.String) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.lastUpdatedTxStamp = (java.lang.String) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productStoreGroupTypeId", OrderMaxUtility.getValidEntityString(this.productStoreGroupTypeId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityString(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityString(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityString(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityString(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productStoreGroupTypeId", this.productStoreGroupTypeId);
        valueMap.put("description", this.description);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductStoreGroupType");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productStoreGroupTypeId;

    public java.lang.String getProductStoreGroupTypeId() {
        return productStoreGroupTypeId;
    }

    public void setProductStoreGroupTypeId(java.lang.String productStoreGroupTypeId) {
        this.productStoreGroupTypeId = productStoreGroupTypeId;
    }
    private java.lang.String lastUpdatedStamp;

    public java.lang.String getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.lang.String lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String createdTxStamp;

    public java.lang.String getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.lang.String createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String createdStamp;

    public java.lang.String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.lang.String createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String lastUpdatedTxStamp;

    public java.lang.String getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.lang.String lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStoreGroupType> objectList = new ArrayList<ProductStoreGroupType>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStoreGroupType(genVal));
        }
        return objectList;
    }
    
     @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getDescription());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getProductStoreGroupTypeId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getProductStoreGroupTypeId());
        }

        return orderToStringBuilder.toString();
    }    
}
