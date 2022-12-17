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

public class UserLogin implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = UserLogin.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public UserLogin(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public UserLogin() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"successiveFailedLogins", "Successive Failed Logins"},
        {"enabled", "Enabled"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastCurrencyUom", "Last Currency Uom"},
        {"passwordHint", "Password Hint"},
        {"lastTimeZone", "Last Time Zone"},
        {"userLoginId", "User Login Id"},
        {"externalAuthId", "External Auth Id"},
        {"hasLoggedOut", "Has Logged Out"},
        {"isSystem", "Is System"},
        {"currentPassword", "Current Password"},
        {"requirePasswordChange", "Require Password Change"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"partyId", "Party Id"},
        {"userLdapDn", "User Ldap Dn"},
        {"lastLocale", "Last Locale"},
        {"disabledDateTime", "Disabled Date Time"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.successiveFailedLogins = new Long(0);
        this.enabled = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastCurrencyUom = "";
        this.passwordHint = "";
        this.lastTimeZone = "";
        this.userLoginId = "";
        this.externalAuthId = "";
        this.hasLoggedOut = "";
        this.isSystem = "";
        this.currentPassword = "";
        this.requirePasswordChange = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.partyId = "";
        this.userLdapDn = "";
        this.lastLocale = "";
        this.disabledDateTime = null;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.successiveFailedLogins = (java.lang.Long) genVal.get("successiveFailedLogins");
        this.enabled = (java.lang.String) genVal.get("enabled");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastCurrencyUom = (java.lang.String) genVal.get("lastCurrencyUom");
        this.passwordHint = (java.lang.String) genVal.get("passwordHint");
        this.lastTimeZone = (java.lang.String) genVal.get("lastTimeZone");
        this.userLoginId = (java.lang.String) genVal.get("userLoginId");
        this.externalAuthId = (java.lang.String) genVal.get("externalAuthId");
        this.hasLoggedOut = (java.lang.String) genVal.get("hasLoggedOut");
        this.isSystem = (java.lang.String) genVal.get("isSystem");
        this.currentPassword = (java.lang.String) genVal.get("currentPassword");
        this.requirePasswordChange = (java.lang.String) genVal.get("requirePasswordChange");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.userLdapDn = (java.lang.String) genVal.get("userLdapDn");
        this.lastLocale = (java.lang.String) genVal.get("lastLocale");
        this.disabledDateTime = (java.sql.Timestamp) genVal.get("disabledDateTime");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("successiveFailedLogins", this.successiveFailedLogins);
        val.set("enabled", OrderMaxUtility.getValidEntityString(this.enabled));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastCurrencyUom", OrderMaxUtility.getValidEntityString(this.lastCurrencyUom));
        val.set("passwordHint", OrderMaxUtility.getValidEntityString(this.passwordHint));
        val.set("lastTimeZone", OrderMaxUtility.getValidEntityString(this.lastTimeZone));
        val.set("userLoginId", OrderMaxUtility.getValidEntityString(this.userLoginId));
        val.set("externalAuthId", OrderMaxUtility.getValidEntityString(this.externalAuthId));
        val.set("hasLoggedOut", OrderMaxUtility.getValidEntityString(this.hasLoggedOut));
        val.set("isSystem", OrderMaxUtility.getValidEntityString(this.isSystem));
        val.set("currentPassword", OrderMaxUtility.getValidEntityString(this.currentPassword));
        val.set("requirePasswordChange", OrderMaxUtility.getValidEntityString(this.requirePasswordChange));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("userLdapDn", OrderMaxUtility.getValidEntityString(this.userLdapDn));
        val.set("lastLocale", OrderMaxUtility.getValidEntityString(this.lastLocale));
        val.set("disabledDateTime", OrderMaxUtility.getValidEntityTimestamp(this.disabledDateTime));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("successiveFailedLogins", this.successiveFailedLogins);
        valueMap.put("enabled", this.enabled);
        valueMap.put("lastCurrencyUom", this.lastCurrencyUom);
        valueMap.put("passwordHint", this.passwordHint);
        valueMap.put("lastTimeZone", this.lastTimeZone);
        valueMap.put("userLoginId", this.userLoginId);
        valueMap.put("externalAuthId", this.externalAuthId);
        valueMap.put("hasLoggedOut", this.hasLoggedOut);
        valueMap.put("isSystem", this.isSystem);
        valueMap.put("currentPassword", this.currentPassword);
        valueMap.put("requirePasswordChange", this.requirePasswordChange);
        valueMap.put("partyId", this.partyId);
        valueMap.put("userLdapDn", this.userLdapDn);
        valueMap.put("lastLocale", this.lastLocale);
        valueMap.put("disabledDateTime", this.disabledDateTime);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("UserLogin");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.Long successiveFailedLogins;

    public java.lang.Long getsuccessiveFailedLogins() {
        return successiveFailedLogins;
    }

    public void setsuccessiveFailedLogins(java.lang.Long successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }
    private java.lang.String enabled;

    public java.lang.String getenabled() {
        return enabled;
    }

    public void setenabled(java.lang.String enabled) {
        this.enabled = enabled;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String lastCurrencyUom;

    public java.lang.String getlastCurrencyUom() {
        return lastCurrencyUom;
    }

    public void setlastCurrencyUom(java.lang.String lastCurrencyUom) {
        this.lastCurrencyUom = lastCurrencyUom;
    }
    private java.lang.String passwordHint;

    public java.lang.String getpasswordHint() {
        return passwordHint;
    }

    public void setpasswordHint(java.lang.String passwordHint) {
        this.passwordHint = passwordHint;
    }
    private java.lang.String lastTimeZone;

    public java.lang.String getlastTimeZone() {
        return lastTimeZone;
    }

    public void setlastTimeZone(java.lang.String lastTimeZone) {
        this.lastTimeZone = lastTimeZone;
    }
    private java.lang.String userLoginId;

    public java.lang.String getuserLoginId() {
        return userLoginId;
    }

    public void setuserLoginId(java.lang.String userLoginId) {
        this.userLoginId = userLoginId;
    }
    private java.lang.String externalAuthId;

    public java.lang.String getexternalAuthId() {
        return externalAuthId;
    }

    public void setexternalAuthId(java.lang.String externalAuthId) {
        this.externalAuthId = externalAuthId;
    }
    private java.lang.String hasLoggedOut;

    public java.lang.String gethasLoggedOut() {
        return hasLoggedOut;
    }

    public void sethasLoggedOut(java.lang.String hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }
    private java.lang.String isSystem;

    public java.lang.String getisSystem() {
        return isSystem;
    }

    public void setisSystem(java.lang.String isSystem) {
        this.isSystem = isSystem;
    }
    private java.lang.String currentPassword;

    public java.lang.String getcurrentPassword() {
        return currentPassword;
    }

    public void setcurrentPassword(java.lang.String currentPassword) {
        this.currentPassword = currentPassword;
    }
    private java.lang.String requirePasswordChange;

    public java.lang.String getrequirePasswordChange() {
        return requirePasswordChange;
    }

    public void setrequirePasswordChange(java.lang.String requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
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
    private java.lang.String userLdapDn;

    public java.lang.String getuserLdapDn() {
        return userLdapDn;
    }

    public void setuserLdapDn(java.lang.String userLdapDn) {
        this.userLdapDn = userLdapDn;
    }
    private java.lang.String lastLocale;

    public java.lang.String getlastLocale() {
        return lastLocale;
    }

    public void setlastLocale(java.lang.String lastLocale) {
        this.lastLocale = lastLocale;
    }
    private java.sql.Timestamp disabledDateTime;

    public java.sql.Timestamp getdisabledDateTime() {
        return disabledDateTime;
    }

    public void setdisabledDateTime(java.sql.Timestamp disabledDateTime) {
        this.disabledDateTime = disabledDateTime;
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
        Collection<UserLogin> objectList = new ArrayList<UserLogin>();
        for (GenericValue genVal : genList) {
            objectList.add(new UserLogin(genVal));
        }
        return objectList;
    }
    
    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
    
            orderToStringBuilder.append(getuserLoginId());
    

        return orderToStringBuilder.toString();
    }
}
