package org.ofbiz.ordermax.entity;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.ordermax.product.feature.ProductFeatureGroupSingleton;
import org.ofbiz.ordermax.product.feature.ProductFeatureSingleton;

public class ProductFeatureGroupAppl implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ProductFeatureGroupAppl.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductFeatureGroupAppl(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public ProductFeatureGroupAppl() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"fromDate", "From Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"sequenceNum", "Sequence Num"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"productFeatureId", "Product Feature Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"productFeatureGroupId", "Product Feature Group Id"},
        {"thruDate", "Thru Date"},};

    protected void initObject() {
        this.fromDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.sequenceNum = new Long(0);
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.productFeatureId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.productFeatureGroupId = "";
        this.thruDate = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.productFeatureGroupId = (java.lang.String) genVal.get("productFeatureGroupId");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("sequenceNum", this.sequenceNum);
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("productFeatureGroupId", OrderMaxUtility.getValidEntityString(this.productFeatureGroupId));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("sequenceNum", this.sequenceNum);
        valueMap.put("productFeatureId", this.productFeatureId);
        valueMap.put("productFeatureGroupId", this.productFeatureGroupId);
        valueMap.put("thruDate", this.thruDate);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductFeatureGroupAppl");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.Long sequenceNum;

    public java.lang.Long getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(java.lang.Long sequenceNum) {
        this.sequenceNum = sequenceNum;
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
    private java.lang.String productFeatureGroupId;

    public java.lang.String getProductFeatureGroupId() {
        return productFeatureGroupId;
    }

    public void setProductFeatureGroupId(java.lang.String productFeatureGroupId) {
        this.productFeatureGroupId = productFeatureGroupId;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getThruDate() {
        return thruDate;
    }

    public void setThruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductFeatureGroupAppl> objectList = new ArrayList<ProductFeatureGroupAppl>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductFeatureGroupAppl(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showKey) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        try {
            ProductFeatureGroup group = ProductFeatureGroupSingleton.getProductFeatureGroup(productFeatureGroupId);
            orderToStringBuilder.append(group.getdescription());
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureGroupAppl.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ProductFeature val = ProductFeatureSingleton.getProductFeature(productFeatureId);
            orderToStringBuilder.append(" [");
            orderToStringBuilder.append(val.getdescription());
            orderToStringBuilder.append(" ]");
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureGroupAppl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderToStringBuilder.toString();
    }
}
