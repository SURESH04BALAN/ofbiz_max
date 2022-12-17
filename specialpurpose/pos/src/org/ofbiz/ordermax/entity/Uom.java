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

public class Uom implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = Uom.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Uom(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Uom() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"uomTypeId", "Uom Type Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"uomId", "Uom Id"},
        {"abbreviation", "Abbreviation"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.uomTypeId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = "";
        this.uomId = "";
        this.abbreviation = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.uomTypeId = (java.lang.String) genVal.get("uomTypeId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.abbreviation = (java.lang.String) genVal.get("abbreviation");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("uomTypeId", OrderMaxUtility.getValidEntityString(this.uomTypeId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("abbreviation", OrderMaxUtility.getValidEntityString(this.abbreviation));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("uomTypeId", this.uomTypeId);
        valueMap.put("description", this.description);
        valueMap.put("uomId", this.uomId);
        valueMap.put("abbreviation", this.abbreviation);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Uom");
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
    private java.lang.String uomTypeId;

    public java.lang.String getuomTypeId() {
        return uomTypeId;
    }

    public void setuomTypeId(java.lang.String uomTypeId) {
        this.uomTypeId = uomTypeId;
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
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
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

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<Uom> objectList = new ArrayList<Uom>();
        for (GenericValue genVal : genList) {
            objectList.add(new Uom(genVal));
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
                orderToStringBuilder.append(getuomId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getuomId());
        }

        return orderToStringBuilder.toString();
    }
    
}
