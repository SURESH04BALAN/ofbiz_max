package org.ofbiz.ordermax.entity;

import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class PartyRelationship implements GenericValueObjectInterface {

    public static final String module = PartyRelationship.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PartyRelationship(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public PartyRelationship() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"partyIdFrom", "Party Id From"},
        {"partyIdTo", "Party Id To"},
        {"roleTypeIdFrom", "Role Type Id From"},
        {"roleTypeIdTo", "Role Type Id To"},
        {"fromDate", "From Date"},
        {"thruDate", "Thru Date"},
        {"statusId", "Status Id"},
        {"relationshipName", "Relationship Name"},
        {"securityGroupId", "Security Group Id"},
        {"priorityTypeId", "Priority Type Id"},
        {"partyRelationshipTypeId", "Party Relationship Type Id"},
        {"permissionsEnumId", "Permissions Enum Id"},
        {"positionTitle", "Position Title"},
        {"comments", "Comments"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.partyIdFrom = "";
        this.partyIdTo = "";
        this.roleTypeIdFrom = "";
        this.roleTypeIdTo = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.thruDate = UtilDateTime.nowTimestamp();
        this.statusId = "";
        this.relationshipName = "";
        this.securityGroupId = "";
        this.priorityTypeId = "";
        this.partyRelationshipTypeId = "";
        this.permissionsEnumId = "";
        this.positionTitle = "";
        this.comments = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.partyIdTo = (java.lang.String) genVal.get("partyIdTo");
        this.roleTypeIdFrom = (java.lang.String) genVal.get("roleTypeIdFrom");
        this.roleTypeIdTo = (java.lang.String) genVal.get("roleTypeIdTo");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.relationshipName = (java.lang.String) genVal.get("relationshipName");
        this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
        this.priorityTypeId = (java.lang.String) genVal.get("priorityTypeId");
        this.partyRelationshipTypeId = (java.lang.String) genVal.get("partyRelationshipTypeId");
        this.permissionsEnumId = (java.lang.String) genVal.get("permissionsEnumId");
        this.positionTitle = (java.lang.String) genVal.get("positionTitle");
        this.comments = (java.lang.String) genVal.get("comments");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("partyIdTo", OrderMaxUtility.getValidEntityString(this.partyIdTo));
        val.set("roleTypeIdFrom", OrderMaxUtility.getValidEntityString(this.roleTypeIdFrom));
        val.set("roleTypeIdTo", OrderMaxUtility.getValidEntityString(this.roleTypeIdTo));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("relationshipName", OrderMaxUtility.getValidEntityString(this.relationshipName));
        val.set("securityGroupId", OrderMaxUtility.getValidEntityString(this.securityGroupId));
        val.set("priorityTypeId", OrderMaxUtility.getValidEntityString(this.priorityTypeId));
        val.set("partyRelationshipTypeId", OrderMaxUtility.getValidEntityString(this.partyRelationshipTypeId));
        val.set("permissionsEnumId", OrderMaxUtility.getValidEntityString(this.permissionsEnumId));
        val.set("positionTitle", OrderMaxUtility.getValidEntityString(this.positionTitle));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public GenericValue createNewGenericValueObj(Delegator delegator) {
        genVal = delegator.makeValue("PartyRelationship");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getpartyIdFrom() {
        return partyIdFrom;
    }

    public void setpartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    private java.lang.String partyIdTo;

    public java.lang.String getpartyIdTo() {
        return partyIdTo;
    }

    public void setpartyIdTo(java.lang.String partyIdTo) {
        this.partyIdTo = partyIdTo;
    }
    private java.lang.String roleTypeIdFrom;

    public java.lang.String getroleTypeIdFrom() {
        return roleTypeIdFrom;
    }

    public void setroleTypeIdFrom(java.lang.String roleTypeIdFrom) {
        this.roleTypeIdFrom = roleTypeIdFrom;
    }
    private java.lang.String roleTypeIdTo;

    public java.lang.String getroleTypeIdTo() {
        return roleTypeIdTo;
    }

    public void setroleTypeIdTo(java.lang.String roleTypeIdTo) {
        this.roleTypeIdTo = roleTypeIdTo;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String relationshipName;

    public java.lang.String getrelationshipName() {
        return relationshipName;
    }

    public void setrelationshipName(java.lang.String relationshipName) {
        this.relationshipName = relationshipName;
    }
    private java.lang.String securityGroupId;

    public java.lang.String getsecurityGroupId() {
        return securityGroupId;
    }

    public void setsecurityGroupId(java.lang.String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }
    private java.lang.String priorityTypeId;

    public java.lang.String getpriorityTypeId() {
        return priorityTypeId;
    }

    public void setpriorityTypeId(java.lang.String priorityTypeId) {
        this.priorityTypeId = priorityTypeId;
    }
    private java.lang.String partyRelationshipTypeId;

    public java.lang.String getpartyRelationshipTypeId() {
        return partyRelationshipTypeId;
    }

    public void setpartyRelationshipTypeId(java.lang.String partyRelationshipTypeId) {
        this.partyRelationshipTypeId = partyRelationshipTypeId;
    }
    private java.lang.String permissionsEnumId;

    public java.lang.String getpermissionsEnumId() {
        return permissionsEnumId;
    }

    public void setpermissionsEnumId(java.lang.String permissionsEnumId) {
        this.permissionsEnumId = permissionsEnumId;
    }
    private java.lang.String positionTitle;

    public java.lang.String getpositionTitle() {
        return positionTitle;
    }

    public void setpositionTitle(java.lang.String positionTitle) {
        this.positionTitle = positionTitle;
    }
    private java.lang.String comments;

    public java.lang.String getcomments() {
        return comments;
    }

    public void setcomments(java.lang.String comments) {
        this.comments = comments;
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
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
