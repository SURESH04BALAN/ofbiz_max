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

public class ProductFeature implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ProductFeature.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductFeature(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductFeature() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"defaultAmount", "Default Amount"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"numberSpecified", "Number Specified"},
        {"productFeatureCategoryId", "Product Feature Category Id"},
        {"uomId", "Uom Id"},
        {"productFeatureId", "Product Feature Id"},
        {"productFeatureTypeId", "Product Feature Type Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"idCode", "Id Code"},
        {"abbrev", "Abbrev"},
        {"defaultSequenceNum", "Default Sequence Num"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.defaultAmount = "";
        this.lastUpdatedStamp = null;
        this.numberSpecified = "";
        this.productFeatureCategoryId = "";
        this.uomId = "";
        this.productFeatureId = "";
        this.productFeatureTypeId = "";
        this.createdTxStamp = null;
        this.createdStamp = null;
        this.description = "";
        this.idCode = "";
        this.abbrev = "";
        this.defaultSequenceNum = null;
        this.lastUpdatedTxStamp = null;
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.defaultAmount = (java.lang.String) genVal.get("defaultAmount");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.numberSpecified = (java.lang.String) genVal.get("numberSpecified");
        this.productFeatureCategoryId = (java.lang.String) genVal.get("productFeatureCategoryId");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.productFeatureTypeId = (java.lang.String) genVal.get("productFeatureTypeId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.idCode = (java.lang.String) genVal.get("idCode");
        this.abbrev = (java.lang.String) genVal.get("abbrev");
        this.defaultSequenceNum = (java.lang.Long) genVal.get("defaultSequenceNum");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("defaultAmount", OrderMaxUtility.getValidEntityString(this.defaultAmount));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("numberSpecified", OrderMaxUtility.getValidEntityString(this.numberSpecified));
        val.set("productFeatureCategoryId", OrderMaxUtility.getValidEntityString(this.productFeatureCategoryId));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("productFeatureTypeId", OrderMaxUtility.getValidEntityString(this.productFeatureTypeId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("idCode", OrderMaxUtility.getValidEntityString(this.idCode));
        val.set("abbrev", OrderMaxUtility.getValidEntityString(this.abbrev));
        val.set("defaultSequenceNum", this.defaultSequenceNum);
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("defaultAmount", this.defaultAmount);
        valueMap.put("numberSpecified", this.numberSpecified);
        valueMap.put("productFeatureCategoryId", this.productFeatureCategoryId);
        valueMap.put("uomId", this.uomId);
        valueMap.put("productFeatureId", this.productFeatureId);
        valueMap.put("productFeatureTypeId", this.productFeatureTypeId);
        valueMap.put("description", this.description);
        valueMap.put("idCode", this.idCode);
        valueMap.put("abbrev", this.abbrev);
        valueMap.put("defaultSequenceNum", this.defaultSequenceNum);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductFeature");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String defaultAmount;

    public java.lang.String getdefaultAmount() {
        return defaultAmount;
    }

    public void setdefaultAmount(java.lang.String defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String numberSpecified;

    public java.lang.String getnumberSpecified() {
        return numberSpecified;
    }

    public void setnumberSpecified(java.lang.String numberSpecified) {
        this.numberSpecified = numberSpecified;
    }
    private java.lang.String productFeatureCategoryId;

    public java.lang.String getproductFeatureCategoryId() {
        return productFeatureCategoryId;
    }

    public void setproductFeatureCategoryId(java.lang.String productFeatureCategoryId) {
        this.productFeatureCategoryId = productFeatureCategoryId;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getproductFeatureId() {
        return productFeatureId;
    }

    public void setproductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.lang.String productFeatureTypeId;

    public java.lang.String getproductFeatureTypeId() {
        return productFeatureTypeId;
    }

    public void setproductFeatureTypeId(java.lang.String productFeatureTypeId) {
        this.productFeatureTypeId = productFeatureTypeId;
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
    private java.lang.String idCode;

    public java.lang.String getidCode() {
        return idCode;
    }

    public void setidCode(java.lang.String idCode) {
        this.idCode = idCode;
    }
    private java.lang.String abbrev;

    public java.lang.String getabbrev() {
        return abbrev;
    }

    public void setabbrev(java.lang.String abbrev) {
        this.abbrev = abbrev;
    }
    private java.lang.Long defaultSequenceNum;

    public java.lang.Long getdefaultSequenceNum() {
        return defaultSequenceNum;
    }

    public void setdefaultSequenceNum(java.lang.Long defaultSequenceNum) {
        this.defaultSequenceNum = defaultSequenceNum;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductFeature> objectList = new ArrayList<ProductFeature>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductFeature(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getdescription());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getproductFeatureId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getproductFeatureId());
        }

        return orderToStringBuilder.toString();
    }
}
