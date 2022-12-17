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

public class ShipmentMethodType implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ShipmentMethodType.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ShipmentMethodType(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ShipmentMethodType() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"shipmentMethodTypeId", "Shipment Method Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"sequenceNum", "Sequence Num"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = "";
        this.shipmentMethodTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.sequenceNum = new java.lang.Long(0);
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("shipmentMethodTypeId", OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("sequenceNum", OrderMaxUtility.getValidEntityLong(this.sequenceNum));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("description", this.description);
        valueMap.put("shipmentMethodTypeId", this.shipmentMethodTypeId);
        valueMap.put("sequenceNum", this.sequenceNum);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ShipmentMethodType");
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
    private java.lang.String shipmentMethodTypeId;

    public java.lang.String getshipmentMethodTypeId() {
        return shipmentMethodTypeId;
    }

    public void setshipmentMethodTypeId(java.lang.String shipmentMethodTypeId) {
        this.shipmentMethodTypeId = shipmentMethodTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
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
        Collection<ShipmentMethodType> objectList = new ArrayList<ShipmentMethodType>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentMethodType(genVal));
        }
        return objectList;
    }
    
@Override
    public String getdisplayName(DisplayTypes showId) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {
   
            orderToStringBuilder.append(getdescription());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getshipmentMethodTypeId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getshipmentMethodTypeId());
        }

        return orderToStringBuilder.toString();
    }
    
}
