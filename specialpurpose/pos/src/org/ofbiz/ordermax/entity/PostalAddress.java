package org.ofbiz.ordermax.entity;

import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class PostalAddress implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = PostalAddress.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PostalAddress(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PostalAddress() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"contactMechId", "Contact Mech Id"},
        {"toName", "To Name"},
        {"attnName", "Attn Name"},
        {"address1", "Address 1"},
        {"address2", "Address 2"},
        {"directions", "Directions"},
        {"city", "City"},
        {"postalCode", "Postal Code"},
        {"postalCodeExt", "Postal Code Ext"},
        {"countryGeoId", "Country Geo Id"},
        {"stateProvinceGeoId", "State Province Geo Id"},
        {"countyGeoId", "County Geo Id"},
        {"postalCodeGeoId", "Postal Code Geo Id"},
        {"geoPointId", "Geo Point Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.contactMechId = "";
        this.toName = "";
        this.attnName = "";
        this.address1 = "";
        this.address2 = "";
        this.directions = "";
        this.city = "";
        this.postalCode = "";
        this.postalCodeExt = "";
        this.countryGeoId = "";
        this.stateProvinceGeoId = "";
        this.countyGeoId = "";
        this.postalCodeGeoId = "";
        this.geoPointId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.toName = (java.lang.String) genVal.get("toName");
        this.attnName = (java.lang.String) genVal.get("attnName");
        this.address1 = (java.lang.String) genVal.get("address1");
        this.address2 = (java.lang.String) genVal.get("address2");
        this.directions = (java.lang.String) genVal.get("directions");
        this.city = (java.lang.String) genVal.get("city");
        this.postalCode = (java.lang.String) genVal.get("postalCode");
        this.postalCodeExt = (java.lang.String) genVal.get("postalCodeExt");
        this.countryGeoId = (java.lang.String) genVal.get("countryGeoId");
        this.stateProvinceGeoId = (java.lang.String) genVal.get("stateProvinceGeoId");
        this.countyGeoId = (java.lang.String) genVal.get("countyGeoId");
        this.postalCodeGeoId = (java.lang.String) genVal.get("postalCodeGeoId");
        this.geoPointId = (java.lang.String) genVal.get("geoPointId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("toName", OrderMaxUtility.getValidEntityString(this.toName));
        val.set("attnName", OrderMaxUtility.getValidEntityString(this.attnName));
        val.set("address1", OrderMaxUtility.getValidEntityString(this.address1));
        val.set("address2", OrderMaxUtility.getValidEntityString(this.address2));
        val.set("directions", OrderMaxUtility.getValidEntityString(this.directions));
        val.set("city", OrderMaxUtility.getValidEntityString(this.city));
        val.set("postalCode", OrderMaxUtility.getValidEntityString(this.postalCode));
        val.set("postalCodeExt", OrderMaxUtility.getValidEntityString(this.postalCodeExt));
        val.set("countryGeoId", OrderMaxUtility.getValidEntityString(this.countryGeoId));
        val.set("stateProvinceGeoId", OrderMaxUtility.getValidEntityString(this.stateProvinceGeoId));
        val.set("countyGeoId", OrderMaxUtility.getValidEntityString(this.countyGeoId));
        val.set("postalCodeGeoId", OrderMaxUtility.getValidEntityString(this.postalCodeGeoId));
        val.set("geoPointId", OrderMaxUtility.getValidEntityString(this.geoPointId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public GenericValue createNewGenericValueObj(Delegator delegator) {
        genVal = delegator.makeValue("PostalAddress");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String contactMechId;

    public java.lang.String getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.lang.String toName;

    public java.lang.String getToName() {
        return toName;
    }

    public void setToName(java.lang.String toName) {
        this.toName = toName;
    }
    private java.lang.String attnName;

    public java.lang.String getAttnName() {
        return attnName;
    }

    public void setAttnName(java.lang.String attnName) {
        this.attnName = attnName;
    }
    private java.lang.String address1;

    public java.lang.String getAddress1() {
        return address1;
    }

    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }
    private java.lang.String address2;

    public java.lang.String getAddress2() {
        return address2;
    }

    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }
    private java.lang.String directions;

    public java.lang.String getDirections() {
        return directions;
    }

    public void setDirections(java.lang.String directions) {
        this.directions = directions;
    }
    private java.lang.String city;

    public java.lang.String getCity() {
        return city;
    }

    public void setCity(java.lang.String city) {
        this.city = city;
    }
    private java.lang.String postalCode;

    public java.lang.String getPostalCode() {
        return postalCode;
    }

    public void setpostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }
    private java.lang.String postalCodeExt;

    public java.lang.String getpostalCodeExt() {
        return postalCodeExt;
    }

    public void setpostalCodeExt(java.lang.String postalCodeExt) {
        this.postalCodeExt = postalCodeExt;
    }
    private java.lang.String countryGeoId;

    public java.lang.String getcountryGeoId() {
        return countryGeoId;
    }

    public void setcountryGeoId(java.lang.String countryGeoId) {
        this.countryGeoId = countryGeoId;
    }
    private java.lang.String stateProvinceGeoId;

    public java.lang.String getstateProvinceGeoId() {
        return stateProvinceGeoId;
    }

    public void setstateProvinceGeoId(java.lang.String stateProvinceGeoId) {
        this.stateProvinceGeoId = stateProvinceGeoId;
    }
    private java.lang.String countyGeoId;

    public java.lang.String getcountyGeoId() {
        return countyGeoId;
    }

    public void setcountyGeoId(java.lang.String countyGeoId) {
        this.countyGeoId = countyGeoId;
    }
    private java.lang.String postalCodeGeoId;

    public java.lang.String getpostalCodeGeoId() {
        return postalCodeGeoId;
    }

    public void setpostalCodeGeoId(java.lang.String postalCodeGeoId) {
        this.postalCodeGeoId = postalCodeGeoId;
    }
    private java.lang.String geoPointId;

    public java.lang.String getgeoPointId() {
        return geoPointId;
    }

    public void setgeoPointId(java.lang.String geoPointId) {
        this.geoPointId = geoPointId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    @Override
    public String getdisplayName(DisplayTypes showId) {
        GenericValue postalMech = getGenericValueObj();
        if (postalMech != null) {
            return OrderMaxUtility.getFormatedAddress1(postalMech) + OrderMaxUtility.getFormatedAddress2(postalMech);

        } else {
            return "Null";
        }
    }

    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
