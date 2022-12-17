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

public class ProdCatalogCategory implements GenericValueObjectInterface , DisplayNameInterface {

    public static final String module = ProdCatalogCategory.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProdCatalogCategory(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public ProdCatalogCategory() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
//        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
//        {"createdTxStamp", "Created Tx Stamp"},
//        {"createdStamp", "Created Stamp"},
        {"prodCatalogId", "Prod Catalog Id"},
        {"prodCatalogCategoryTypeId", "Prod Catalog Category Type Id"},
//        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"productCategoryId", "Product Category Id"},
        {"sequenceNum", "Sequence Num"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.thruDate = UtilDateTime.nowTimestamp();
        this.fromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.prodCatalogId = "";
        this.prodCatalogCategoryTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.productCategoryId = "";
        this.sequenceNum = new Long(0);
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
        this.prodCatalogCategoryTypeId = (java.lang.String) genVal.get("prodCatalogCategoryTypeId");
        this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
  
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("prodCatalogId", OrderMaxUtility.getValidEntityString(this.prodCatalogId));
        val.set("prodCatalogCategoryTypeId", OrderMaxUtility.getValidEntityString(this.prodCatalogCategoryTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("productCategoryId", OrderMaxUtility.getValidEntityString(this.productCategoryId));
        val.set("sequenceNum", this.sequenceNum);
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("prodCatalogId", this.prodCatalogId);
        valueMap.put("prodCatalogCategoryTypeId", this.prodCatalogCategoryTypeId);
        valueMap.put("productCategoryId", this.productCategoryId);
        valueMap.put("sequenceNum", this.sequenceNum);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProdCatalogCategory");
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
    private java.lang.String prodCatalogId;

    public java.lang.String getprodCatalogId() {
        return prodCatalogId;
    }

    public void setprodCatalogId(java.lang.String prodCatalogId) {
        this.prodCatalogId = prodCatalogId;
    }
    private java.lang.String prodCatalogCategoryTypeId;

    public java.lang.String getprodCatalogCategoryTypeId() {
        return prodCatalogCategoryTypeId;
    }

    public void setprodCatalogCategoryTypeId(java.lang.String prodCatalogCategoryTypeId) {
        this.prodCatalogCategoryTypeId = prodCatalogCategoryTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String productCategoryId;

    public java.lang.String getproductCategoryId() {
        return productCategoryId;
    }

    public void setproductCategoryId(java.lang.String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    private java.lang.Long sequenceNum;

    public java.lang.Long getsequenceNum() {
        return sequenceNum;
    }

    public void setsequenceNum(java.lang.Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProdCatalogCategory> objectList = new ArrayList<ProdCatalogCategory>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProdCatalogCategory(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showId) {
            StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {
   
            orderToStringBuilder.append(getproductCategoryId());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getprodCatalogId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getproductCategoryId());
        }

        return orderToStringBuilder.toString();
    }
}
