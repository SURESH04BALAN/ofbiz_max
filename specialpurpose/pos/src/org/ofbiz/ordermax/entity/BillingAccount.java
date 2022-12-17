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
import org.ofbiz.base.util.UtilValidate;

public class BillingAccount implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = BillingAccount.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public BillingAccount(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public BillingAccount() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"billingAccountId", "Billing Account Id"},
        {"accountLimit", "Account Limit"},
        {"accountCurrencyUomId", "Account Currency Uom Id"},
        {"contactMechId", "Contact Mech Id"},
        {"fromDate", "From Date"},
        {"thruDate", "Thru Date"},
        {"description", "Description"},
        {"externalAccountId", "External Account Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.billingAccountId = "";
        this.accountLimit = java.math.BigDecimal.ZERO;
        this.accountCurrencyUomId = "";
        this.contactMechId = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.thruDate = null;
        this.description = "";
        this.externalAccountId = "";
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
        this.accountLimit = (java.math.BigDecimal) genVal.get("accountLimit");
        this.accountCurrencyUomId = (java.lang.String) genVal.get("accountCurrencyUomId");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.description = (java.lang.String) genVal.get("description");
        this.externalAccountId = (java.lang.String) genVal.get("externalAccountId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("accountLimit", OrderMaxUtility.getValidEntityBigDecimal(this.accountLimit));
        val.set("accountCurrencyUomId", OrderMaxUtility.getValidEntityString(this.accountCurrencyUomId));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("externalAccountId", OrderMaxUtility.getValidEntityString(this.externalAccountId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("BillingAccount");
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
    private java.math.BigDecimal accountLimit;

    public java.math.BigDecimal getaccountLimit() {
        return accountLimit;
    }

    public void setaccountLimit(java.math.BigDecimal accountLimit) {
        this.accountLimit = accountLimit;
    }
    private java.lang.String accountCurrencyUomId;

    public java.lang.String getaccountCurrencyUomId() {
        return accountCurrencyUomId;
    }

    public void setaccountCurrencyUomId(java.lang.String accountCurrencyUomId) {
        this.accountCurrencyUomId = accountCurrencyUomId;
    }
    private java.lang.String contactMechId;

    public java.lang.String getcontactMechId() {
        return contactMechId;
    }

    public void setcontactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
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
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String externalAccountId;

    public java.lang.String getexternalAccountId() {
        return externalAccountId;
    }

    public void setexternalAccountId(java.lang.String externalAccountId) {
        this.externalAccountId = externalAccountId;
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
        Collection<BillingAccount> objectList = new ArrayList<BillingAccount>();
        for (GenericValue genVal : genList) {
            objectList.add(new BillingAccount(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showId) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {
            if (UtilValidate.isNotEmpty(getdescription())) {
                orderToStringBuilder.append(getdescription());
            }
            
            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || UtilValidate.isEmpty(getdescription())) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(billingAccountId);
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(billingAccountId);
        }

        return orderToStringBuilder.toString();
    }
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
