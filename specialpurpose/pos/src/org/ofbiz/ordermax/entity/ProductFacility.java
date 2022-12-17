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
import org.ofbiz.base.util.Debug;

public class ProductFacility implements GenericValueObjectInterface {

    public static final String module = ProductFacility.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductFacility(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProductFacility() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastInventoryCount", "Last Inventory Count"},
        {"facilityId", "Facility Id"},
        {"productId", "Product Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"minimumStock", "Minimum Stock"},
        {"reorderQuantity", "Reorder Quantity"},
        {"daysToShip", "Days To Ship"},};

    protected void initObject() {
        this.lastUpdatedStamp = null;
        this.lastInventoryCount = java.math.BigDecimal.ZERO;
        this.facilityId = "";
        this.productId = "";
        this.createdTxStamp = null;
        this.createdStamp = null;
        this.lastUpdatedTxStamp = null;
        this.minimumStock = java.math.BigDecimal.ZERO;
        this.reorderQuantity = java.math.BigDecimal.ZERO;
        this.daysToShip = new Long(0);
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastInventoryCount = (java.math.BigDecimal) genVal.get("lastInventoryCount");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.minimumStock = (java.math.BigDecimal) genVal.get("minimumStock");
        this.reorderQuantity = (java.math.BigDecimal) genVal.get("reorderQuantity");
        this.daysToShip = (java.lang.Long) genVal.get("daysToShip");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastInventoryCount", OrderMaxUtility.getValidEntityBigDecimal(this.lastInventoryCount));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("minimumStock", OrderMaxUtility.getValidEntityBigDecimal(this.minimumStock));
        val.set("reorderQuantity", OrderMaxUtility.getValidEntityBigDecimal(this.reorderQuantity));
        val.set("daysToShip", this.daysToShip);
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("lastInventoryCount", this.lastInventoryCount);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("productId", this.productId);
        valueMap.put("minimumStock", this.minimumStock);
        valueMap.put("reorderQuantity", this.reorderQuantity);
        valueMap.put("daysToShip", this.daysToShip);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductFacility");
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

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.math.BigDecimal lastInventoryCount;

    public java.math.BigDecimal getLastInventoryCount() {
        return lastInventoryCount;
    }

    public void setLastInventoryCount(java.math.BigDecimal lastInventoryCount) {
        this.lastInventoryCount = lastInventoryCount;
    }
    private java.lang.String facilityId;

    public java.lang.String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.lang.String productId;

    public java.lang.String getProductId() {
        return productId;
    }

    public void setProductId(java.lang.String productId) {
        this.productId = productId;
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
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.math.BigDecimal minimumStock;

    public java.math.BigDecimal getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(java.math.BigDecimal minimumStock) {
        this.minimumStock = minimumStock;
    }
    private java.math.BigDecimal reorderQuantity;

    public java.math.BigDecimal getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(java.math.BigDecimal reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }
    private java.lang.Long daysToShip;

    public java.lang.Long getDaysToShip() {
        return daysToShip;
    }

    public void setDaysToShip(java.lang.Long daysToShip) {
        this.daysToShip = daysToShip;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductFacility> objectList = new ArrayList<ProductFacility>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductFacility(genVal));
        }
        return objectList;
    }
}
