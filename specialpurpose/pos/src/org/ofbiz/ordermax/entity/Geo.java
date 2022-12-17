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

public class Geo implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = Geo.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Geo(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Geo() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"geoId", "Geo Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"wellKnownText", "Well Known Text"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"geoName", "Geo Name"},
        {"geoSecCode", "Geo Sec Code"},
        {"abbreviation", "Abbreviation"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"geoTypeId", "Geo Type Id"},
        {"geoCode", "Geo Code"},};

    protected void initObject() {
        this.geoId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.wellKnownText = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.geoName = "";
        this.geoSecCode = "";
        this.abbreviation = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.geoTypeId = "";
        this.geoCode = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.geoId = (java.lang.String) genVal.get("geoId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.wellKnownText = (java.lang.String) genVal.get("wellKnownText");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.geoName = (java.lang.String) genVal.get("geoName");
        this.geoSecCode = (java.lang.String) genVal.get("geoSecCode");
        this.abbreviation = (java.lang.String) genVal.get("abbreviation");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.geoTypeId = (java.lang.String) genVal.get("geoTypeId");
        this.geoCode = (java.lang.String) genVal.get("geoCode");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("geoId", OrderMaxUtility.getValidEntityString(this.geoId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("wellKnownText", OrderMaxUtility.getValidEntityString(this.wellKnownText));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("geoName", OrderMaxUtility.getValidEntityString(this.geoName));
        val.set("geoSecCode", OrderMaxUtility.getValidEntityString(this.geoSecCode));
        val.set("abbreviation", OrderMaxUtility.getValidEntityString(this.abbreviation));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("geoTypeId", OrderMaxUtility.getValidEntityString(this.geoTypeId));
        val.set("geoCode", OrderMaxUtility.getValidEntityString(this.geoCode));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("geoId", this.geoId);
        valueMap.put("wellKnownText", this.wellKnownText);
        valueMap.put("geoName", this.geoName);
        valueMap.put("geoSecCode", this.geoSecCode);
        valueMap.put("abbreviation", this.abbreviation);
        valueMap.put("geoTypeId", this.geoTypeId);
        valueMap.put("geoCode", this.geoCode);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Geo");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String geoId;

    public java.lang.String getgeoId() {
        return geoId;
    }

    public void setgeoId(java.lang.String geoId) {
        this.geoId = geoId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String wellKnownText;

    public java.lang.String getwellKnownText() {
        return wellKnownText;
    }

    public void setwellKnownText(java.lang.String wellKnownText) {
        this.wellKnownText = wellKnownText;
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
    private java.lang.String geoName;

    public java.lang.String getgeoName() {
        return geoName;
    }

    public void setgeoName(java.lang.String geoName) {
        this.geoName = geoName;
    }
    private java.lang.String geoSecCode;

    public java.lang.String getgeoSecCode() {
        return geoSecCode;
    }

    public void setgeoSecCode(java.lang.String geoSecCode) {
        this.geoSecCode = geoSecCode;
    }
    private java.lang.String abbreviation;

    public java.lang.String getabbreviation() {
        return abbreviation;
    }

    public void setabbreviation(java.lang.String abbreviation) {
        this.abbreviation = abbreviation;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String geoTypeId;

    public java.lang.String getgeoTypeId() {
        return geoTypeId;
    }

    public void setgeoTypeId(java.lang.String geoTypeId) {
        this.geoTypeId = geoTypeId;
    }
    private java.lang.String geoCode;

    public java.lang.String getgeoCode() {
        return geoCode;
    }

    public void setgeoCode(java.lang.String geoCode) {
        this.geoCode = geoCode;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Geo> objectList = new ArrayList<Geo>();
        for (GenericValue genVal : genList) {
            objectList.add(new Geo(genVal));
        }
        return objectList;
    }

    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(geoName);

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(abbreviation);
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(abbreviation);
        }

        return orderToStringBuilder.toString();
    }
}
