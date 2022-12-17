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

public class ProductStoreFacility implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ProductStoreFacility.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductStoreFacility(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductStoreFacility() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"productStoreId", "Product Store Id"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"facilityId", "Facility Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"sequenceNum", "Sequence Num"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.productStoreId = "";
        this.thruDate = UtilDateTime.nowTimestamp();
        this.fromDate = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.facilityId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.sequenceNum = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.productStoreId));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("sequenceNum", OrderMaxUtility.getValidEntityString(this.sequenceNum));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productStoreId", this.productStoreId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("sequenceNum", this.sequenceNum);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductStoreFacility");
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
    private java.lang.String productStoreId;

    public java.lang.String getproductStoreId() {
        return productStoreId;
    }

    public void setproductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
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
    private java.lang.String facilityId;

    public java.lang.String getfacilityId() {
        return facilityId;
    }

    public void setfacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String sequenceNum;

    public java.lang.String getsequenceNum() {
        return sequenceNum;
    }

    public void setsequenceNum(java.lang.String sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    static public List getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        List<ProductStoreFacility> objectList = new ArrayList<ProductStoreFacility>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStoreFacility(genVal));
        }
        return objectList;
    }
    
        @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getproductStoreId());

            //          if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
            orderToStringBuilder.append(" [");
            orderToStringBuilder.append(getfacilityId());
            orderToStringBuilder.append(" ]");
            //           }
        } else {
            orderToStringBuilder.append(getproductStoreId());
            orderToStringBuilder.append(" [");
            orderToStringBuilder.append(getfacilityId());
            orderToStringBuilder.append(" ]");
        }

        return orderToStringBuilder.toString();
    }

}
