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

public class ShipmentBoxType implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ShipmentBoxType.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ShipmentBoxType(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ShipmentBoxType() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"weightUomId", "Weight Uom Id"},
        {"boxHeight", "Box Height"},
        {"boxWeight", "Box Weight"},
        {"boxWidth", "Box Width"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"dimensionUomId", "Dimension Uom Id"},
        {"boxLength", "Box Length"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"shipmentBoxTypeId", "Shipment Box Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.weightUomId = "";
        this.boxHeight = java.math.BigDecimal.ZERO;
        this.boxWeight = "";
        this.boxWidth = java.math.BigDecimal.ZERO;
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.dimensionUomId = "";
        this.boxLength = java.math.BigDecimal.ZERO;
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = "";
        this.shipmentBoxTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.weightUomId = (java.lang.String) genVal.get("weightUomId");
        this.boxHeight = (java.math.BigDecimal) genVal.get("boxHeight");
        this.boxWeight = (java.lang.String) genVal.get("boxWeight");
        this.boxWidth = (java.math.BigDecimal) genVal.get("boxWidth");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.dimensionUomId = (java.lang.String) genVal.get("dimensionUomId");
        this.boxLength = (java.math.BigDecimal) genVal.get("boxLength");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.shipmentBoxTypeId = (java.lang.String) genVal.get("shipmentBoxTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("weightUomId", OrderMaxUtility.getValidEntityString(this.weightUomId));
        val.set("boxHeight", OrderMaxUtility.getValidEntityBigDecimal(this.boxHeight));
        val.set("boxWeight", OrderMaxUtility.getValidEntityString(this.boxWeight));
        val.set("boxWidth", OrderMaxUtility.getValidEntityBigDecimal(this.boxWidth));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("dimensionUomId", OrderMaxUtility.getValidEntityString(this.dimensionUomId));
        val.set("boxLength", OrderMaxUtility.getValidEntityBigDecimal(this.boxLength));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("shipmentBoxTypeId", OrderMaxUtility.getValidEntityString(this.shipmentBoxTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("weightUomId", this.weightUomId);
        valueMap.put("boxHeight", this.boxHeight);
        valueMap.put("boxWeight", this.boxWeight);
        valueMap.put("boxWidth", this.boxWidth);
        valueMap.put("dimensionUomId", this.dimensionUomId);
        valueMap.put("boxLength", this.boxLength);
        valueMap.put("description", this.description);
        valueMap.put("shipmentBoxTypeId", this.shipmentBoxTypeId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ShipmentBoxType");
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
    private java.lang.String weightUomId;

    public java.lang.String getweightUomId() {
        return weightUomId;
    }

    public void setweightUomId(java.lang.String weightUomId) {
        this.weightUomId = weightUomId;
    }
    private java.math.BigDecimal boxHeight;

    public java.math.BigDecimal getboxHeight() {
        return boxHeight;
    }

    public void setboxHeight(java.math.BigDecimal boxHeight) {
        this.boxHeight = boxHeight;
    }
    private java.lang.String boxWeight;

    public java.lang.String getboxWeight() {
        return boxWeight;
    }

    public void setboxWeight(java.lang.String boxWeight) {
        this.boxWeight = boxWeight;
    }
    private java.math.BigDecimal boxWidth;

    public java.math.BigDecimal getboxWidth() {
        return boxWidth;
    }

    public void setboxWidth(java.math.BigDecimal boxWidth) {
        this.boxWidth = boxWidth;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String dimensionUomId;

    public java.lang.String getdimensionUomId() {
        return dimensionUomId;
    }

    public void setdimensionUomId(java.lang.String dimensionUomId) {
        this.dimensionUomId = dimensionUomId;
    }
    private java.math.BigDecimal boxLength;

    public java.math.BigDecimal getboxLength() {
        return boxLength;
    }

    public void setboxLength(java.math.BigDecimal boxLength) {
        this.boxLength = boxLength;
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
    private java.lang.String shipmentBoxTypeId;

    public java.lang.String getshipmentBoxTypeId() {
        return shipmentBoxTypeId;
    }

    public void setshipmentBoxTypeId(java.lang.String shipmentBoxTypeId) {
        this.shipmentBoxTypeId = shipmentBoxTypeId;
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
        Collection<ShipmentBoxType> objectList = new ArrayList<ShipmentBoxType>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentBoxType(genVal));
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
                orderToStringBuilder.append(getshipmentBoxTypeId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getshipmentBoxTypeId());
        }

        return orderToStringBuilder.toString();
    }
    
}
