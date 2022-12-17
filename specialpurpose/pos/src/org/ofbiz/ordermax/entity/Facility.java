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

public class Facility implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = Facility.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Facility(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Facility() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"facilityName", "Facility Name"},
        {"defaultWeightUomId", "Default Weight Uom Id"},
        {"productStoreId", "Product Store Id"},
        {"openedDate", "Opened Date"},
        {"closedDate", "Closed Date"},
        {"facilitySizeUomId", "Facility Size Uom Id"},
        {"defaultInventoryItemTypeId", "Default Inventory Item Type Id"},
        {"facilityId", "Facility Id"},
        {"defaultDimensionUomId", "Default Dimension Uom Id"},
        {"oldSquareFootage", "Old Square Footage"},
        {"parentFacilityId", "Parent Facility Id"},
        {"primaryFacilityGroupId", "Primary Facility Group Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"facilityTypeId", "Facility Type Id"},
        {"description", "Description"},
        {"defaultDaysToShip", "Default Days To Ship"},
        {"facilitySize", "Facility Size"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"ownerPartyId", "Owner Party Id"},
        {"geoPointId", "Geo Point Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.facilityName = "";
        this.defaultWeightUomId = "";
        this.productStoreId = "";
        this.openedDate = UtilDateTime.nowTimestamp();
        this.closedDate = UtilDateTime.nowTimestamp();
        this.facilitySizeUomId = "";
        this.defaultInventoryItemTypeId = "";
        this.facilityId = "";
        this.defaultDimensionUomId = "";
        this.oldSquareFootage = "";
        this.parentFacilityId = "";
        this.primaryFacilityGroupId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.facilityTypeId = "";
        this.description = "";
        this.defaultDaysToShip = new Long(0);
        this.facilitySize = java.math.BigDecimal.ZERO;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.ownerPartyId = "";
        this.geoPointId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.facilityName = (java.lang.String) genVal.get("facilityName");
        this.defaultWeightUomId = (java.lang.String) genVal.get("defaultWeightUomId");
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");
        this.openedDate = (java.sql.Timestamp) genVal.get("openedDate");
        this.closedDate = (java.sql.Timestamp) genVal.get("closedDate");
        this.facilitySizeUomId = (java.lang.String) genVal.get("facilitySizeUomId");
        this.defaultInventoryItemTypeId = (java.lang.String) genVal.get("defaultInventoryItemTypeId");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.defaultDimensionUomId = (java.lang.String) genVal.get("defaultDimensionUomId");
        this.oldSquareFootage = (java.lang.String) genVal.get("oldSquareFootage");
        this.parentFacilityId = (java.lang.String) genVal.get("parentFacilityId");
        this.primaryFacilityGroupId = (java.lang.String) genVal.get("primaryFacilityGroupId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.facilityTypeId = (java.lang.String) genVal.get("facilityTypeId");
        this.description = (java.lang.String) genVal.get("description");
        this.defaultDaysToShip = (java.lang.Long) genVal.get("defaultDaysToShip");
        this.facilitySize = (java.math.BigDecimal) genVal.get("facilitySize");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
        this.geoPointId = (java.lang.String) genVal.get("geoPointId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("facilityName", OrderMaxUtility.getValidEntityString(this.facilityName));
        val.set("defaultWeightUomId", OrderMaxUtility.getValidEntityString(this.defaultWeightUomId));
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.productStoreId));
        val.set("openedDate", OrderMaxUtility.getValidEntityTimestamp(this.openedDate));
        val.set("closedDate", OrderMaxUtility.getValidEntityTimestamp(this.closedDate));
        val.set("facilitySizeUomId", OrderMaxUtility.getValidEntityString(this.facilitySizeUomId));
        val.set("defaultInventoryItemTypeId", OrderMaxUtility.getValidEntityString(this.defaultInventoryItemTypeId));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("defaultDimensionUomId", OrderMaxUtility.getValidEntityString(this.defaultDimensionUomId));
        val.set("oldSquareFootage", OrderMaxUtility.getValidEntityString(this.oldSquareFootage));
        val.set("parentFacilityId", OrderMaxUtility.getValidEntityString(this.parentFacilityId));
        val.set("primaryFacilityGroupId", OrderMaxUtility.getValidEntityString(this.primaryFacilityGroupId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("facilityTypeId", OrderMaxUtility.getValidEntityString(this.facilityTypeId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("defaultDaysToShip", this.defaultDaysToShip);
        val.set("facilitySize", OrderMaxUtility.getValidEntityBigDecimal(this.facilitySize));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("ownerPartyId", OrderMaxUtility.getValidEntityString(this.ownerPartyId));
        val.set("geoPointId", OrderMaxUtility.getValidEntityString(this.geoPointId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("facilityName", this.facilityName);
        valueMap.put("defaultWeightUomId", this.defaultWeightUomId);
        valueMap.put("productStoreId", this.productStoreId);
        valueMap.put("openedDate", this.openedDate);
        valueMap.put("closedDate", this.closedDate);
        valueMap.put("facilitySizeUomId", this.facilitySizeUomId);
        valueMap.put("defaultInventoryItemTypeId", this.defaultInventoryItemTypeId);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("defaultDimensionUomId", this.defaultDimensionUomId);
        valueMap.put("oldSquareFootage", this.oldSquareFootage);
        valueMap.put("parentFacilityId", this.parentFacilityId);
        valueMap.put("primaryFacilityGroupId", this.primaryFacilityGroupId);
        valueMap.put("facilityTypeId", this.facilityTypeId);
        valueMap.put("description", this.description);
        valueMap.put("defaultDaysToShip", this.defaultDaysToShip);
        valueMap.put("facilitySize", this.facilitySize);
        valueMap.put("ownerPartyId", this.ownerPartyId);
        valueMap.put("geoPointId", this.geoPointId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Facility");
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
    private java.lang.String facilityName;

    public java.lang.String getfacilityName() {
        return facilityName;
    }

    public void setfacilityName(java.lang.String facilityName) {
        this.facilityName = facilityName;
    }
    private java.lang.String defaultWeightUomId;

    public java.lang.String getdefaultWeightUomId() {
        return defaultWeightUomId;
    }

    public void setdefaultWeightUomId(java.lang.String defaultWeightUomId) {
        this.defaultWeightUomId = defaultWeightUomId;
    }
    private java.lang.String productStoreId;

    public java.lang.String getproductStoreId() {
        return productStoreId;
    }

    public void setproductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
    }
    private java.sql.Timestamp openedDate;

    public java.sql.Timestamp getopenedDate() {
        return openedDate;
    }

    public void setopenedDate(java.sql.Timestamp openedDate) {
        this.openedDate = openedDate;
    }
    private java.sql.Timestamp closedDate;

    public java.sql.Timestamp getclosedDate() {
        return closedDate;
    }

    public void setclosedDate(java.sql.Timestamp closedDate) {
        this.closedDate = closedDate;
    }
    private java.lang.String facilitySizeUomId;

    public java.lang.String getfacilitySizeUomId() {
        return facilitySizeUomId;
    }

    public void setfacilitySizeUomId(java.lang.String facilitySizeUomId) {
        this.facilitySizeUomId = facilitySizeUomId;
    }
    private java.lang.String defaultInventoryItemTypeId;

    public java.lang.String getdefaultInventoryItemTypeId() {
        return defaultInventoryItemTypeId;
    }

    public void setdefaultInventoryItemTypeId(java.lang.String defaultInventoryItemTypeId) {
        this.defaultInventoryItemTypeId = defaultInventoryItemTypeId;
    }
    private java.lang.String facilityId;

    public java.lang.String getfacilityId() {
        return facilityId;
    }

    public void setfacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.lang.String defaultDimensionUomId;

    public java.lang.String getdefaultDimensionUomId() {
        return defaultDimensionUomId;
    }

    public void setdefaultDimensionUomId(java.lang.String defaultDimensionUomId) {
        this.defaultDimensionUomId = defaultDimensionUomId;
    }
    private java.lang.String oldSquareFootage;

    public java.lang.String getoldSquareFootage() {
        return oldSquareFootage;
    }

    public void setoldSquareFootage(java.lang.String oldSquareFootage) {
        this.oldSquareFootage = oldSquareFootage;
    }
    private java.lang.String parentFacilityId;

    public java.lang.String getparentFacilityId() {
        return parentFacilityId;
    }

    public void setparentFacilityId(java.lang.String parentFacilityId) {
        this.parentFacilityId = parentFacilityId;
    }
    private java.lang.String primaryFacilityGroupId;

    public java.lang.String getprimaryFacilityGroupId() {
        return primaryFacilityGroupId;
    }

    public void setprimaryFacilityGroupId(java.lang.String primaryFacilityGroupId) {
        this.primaryFacilityGroupId = primaryFacilityGroupId;
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
    private java.lang.String facilityTypeId;

    public java.lang.String getfacilityTypeId() {
        return facilityTypeId;
    }

    public void setfacilityTypeId(java.lang.String facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.Long defaultDaysToShip;

    public java.lang.Long getdefaultDaysToShip() {
        return defaultDaysToShip;
    }

    public void setdefaultDaysToShip(java.lang.Long defaultDaysToShip) {
        this.defaultDaysToShip = defaultDaysToShip;
    }
    private java.math.BigDecimal facilitySize;

    public java.math.BigDecimal getfacilitySize() {
        return facilitySize;
    }

    public void setfacilitySize(java.math.BigDecimal facilitySize) {
        this.facilitySize = facilitySize;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String ownerPartyId;

    public java.lang.String getownerPartyId() {
        return ownerPartyId;
    }

    public void setownerPartyId(java.lang.String ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }
    private java.lang.String geoPointId;

    public java.lang.String getgeoPointId() {
        return geoPointId;
    }

    public void setgeoPointId(java.lang.String geoPointId) {
        this.geoPointId = geoPointId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Facility> objectList = new ArrayList<Facility>();
        for (GenericValue genVal : genList) {
            objectList.add(new Facility(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getfacilityName());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getfacilityId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getfacilityId());
        }

        return orderToStringBuilder.toString();
    }

}
