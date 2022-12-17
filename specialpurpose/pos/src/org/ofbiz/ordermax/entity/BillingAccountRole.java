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
import java.util.Map;

public class BillingAccountRole implements GenericValueObjectInterface {

    public static final String module = BillingAccountRole.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public BillingAccountRole(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public BillingAccountRole() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"billingAccountId", "Billing Account Id"},
        {"partyId", "Party Id"},
        {"roleTypeId", "Role Type Id"},
        {"fromDate", "From Date"},
        {"thruDate", "Thru Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.billingAccountId = "";
        this.partyId = "";
        this.roleTypeId = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.thruDate = null;
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("roleTypeId", OrderMaxUtility.getValidEntityString(this.roleTypeId));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("BillingAccountRole");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getbillingAccountId() {
        return billingAccountId;
    }

    public void setbillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String roleTypeId;

    public java.lang.String getroleTypeId() {
        return roleTypeId;
    }

    public void setroleTypeId(java.lang.String roleTypeId) {
        this.roleTypeId = roleTypeId;
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
//@Override

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<BillingAccountRole> objectList = new ArrayList<BillingAccountRole>();
        for (GenericValue genVal : genList) {
            objectList.add(new BillingAccountRole(genVal));
        }
        return objectList;
    }
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
