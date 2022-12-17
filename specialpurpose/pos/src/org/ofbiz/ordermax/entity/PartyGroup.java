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

public class PartyGroup implements GenericValueObjectInterface {

    public static final String module = PartyGroup.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PartyGroup(GenericValue val){
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PartyGroup() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"groupName", "Group Name"},
        {"logoImageUrl", "Logo Image Url"},
        {"groupNameLocal", "Group Name Local"},
        {"tickerSymbol", "Ticker Symbol"},
        {"annualRevenue", "Annual Revenue"},
        {"officeSiteName", "Office Site Name"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"partyId", "Party Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"comments", "Comments"},
        {"numEmployees", "Num Employees"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.groupName = "";
        this.logoImageUrl = "";
        this.groupNameLocal = "";
        this.tickerSymbol = "";
        this.annualRevenue = "";
        this.officeSiteName = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.partyId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.comments = "";
        this.numEmployees = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.groupName = (java.lang.String) genVal.get("groupName");
        this.logoImageUrl = (java.lang.String) genVal.get("logoImageUrl");
        this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
        this.tickerSymbol = (java.lang.String) genVal.get("tickerSymbol");
        this.annualRevenue = (java.lang.String) genVal.get("annualRevenue");
        this.officeSiteName = (java.lang.String) genVal.get("officeSiteName");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.comments = (java.lang.String) genVal.get("comments");
        this.numEmployees = (java.lang.String) genVal.get("numEmployees");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("groupName", OrderMaxUtility.getValidEntityString(this.groupName));
        val.set("logoImageUrl", OrderMaxUtility.getValidEntityString(this.logoImageUrl));
        val.set("groupNameLocal", OrderMaxUtility.getValidEntityString(this.groupNameLocal));
        val.set("tickerSymbol", OrderMaxUtility.getValidEntityString(this.tickerSymbol));
        val.set("annualRevenue", OrderMaxUtility.getValidEntityString(this.annualRevenue));
        val.set("officeSiteName", OrderMaxUtility.getValidEntityString(this.officeSiteName));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
        val.set("numEmployees", OrderMaxUtility.getValidEntityString(this.numEmployees));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("groupName", this.groupName);
        valueMap.put("logoImageUrl", this.logoImageUrl);
        valueMap.put("groupNameLocal", this.groupNameLocal);
        valueMap.put("tickerSymbol", this.tickerSymbol);
        valueMap.put("annualRevenue", this.annualRevenue);
        valueMap.put("officeSiteName", this.officeSiteName);
        valueMap.put("partyId", this.partyId);
        valueMap.put("comments", this.comments);
        valueMap.put("numEmployees", this.numEmployees);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PartyGroup");
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
    private java.lang.String groupName;

    public java.lang.String getgroupName() {
        return groupName;
    }

    public void setgroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }
    private java.lang.String logoImageUrl;

    public java.lang.String getlogoImageUrl() {
        return logoImageUrl;
    }

    public void setlogoImageUrl(java.lang.String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }
    private java.lang.String groupNameLocal;

    public java.lang.String getgroupNameLocal() {
        return groupNameLocal;
    }

    public void setgroupNameLocal(java.lang.String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
    }
    private java.lang.String tickerSymbol;

    public java.lang.String gettickerSymbol() {
        return tickerSymbol;
    }

    public void settickerSymbol(java.lang.String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }
    private java.lang.String annualRevenue;

    public java.lang.String getannualRevenue() {
        return annualRevenue;
    }

    public void setannualRevenue(java.lang.String annualRevenue) {
        this.annualRevenue = annualRevenue;
    }
    private java.lang.String officeSiteName;

    public java.lang.String getofficeSiteName() {
        return officeSiteName;
    }

    public void setofficeSiteName(java.lang.String officeSiteName) {
        this.officeSiteName = officeSiteName;
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
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String comments;

    public java.lang.String getcomments() {
        return comments;
    }

    public void setcomments(java.lang.String comments) {
        this.comments = comments;
    }
    private java.lang.String numEmployees;

    public java.lang.String getnumEmployees() {
        return numEmployees;
    }

    public void setnumEmployees(java.lang.String numEmployees) {
        this.numEmployees = numEmployees;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyGroup> objectList = new ArrayList<PartyGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyGroup(genVal));
        }
        return objectList;
    }
}
