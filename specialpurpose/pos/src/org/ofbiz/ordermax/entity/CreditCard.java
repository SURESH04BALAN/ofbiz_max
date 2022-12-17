package org.ofbiz.ordermax.entity;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreditCard implements GenericValueObjectInterface {

    public static final String module = CreditCard.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public CreditCard(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public CreditCard() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"firstNameOnCard", "First Name On Card"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"issueNumber", "Issue Number"},
        {"createdStamp", "Created Stamp"},
        {"cardType", "Card Type"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"lastFailedAuthDate", "Last Failed Auth Date"},
        {"consecutiveFailedNsf", "Consecutive Failed Nsf"},
        {"contactMechId", "Contact Mech Id"},
        {"companyNameOnCard", "Company Name On Card"},
        {"titleOnCard", "Title On Card"},
        {"lastNameOnCard", "Last Name On Card"},
        {"paymentMethodId", "Payment Method Id"},
        {"middleNameOnCard", "Middle Name On Card"},
        {"validFromDate", "Valid From Date"},
        {"expireDate", "Expire Date"},
        {"consecutiveFailedAuths", "Consecutive Failed Auths"},
        {"lastFailedNsfDate", "Last Failed Nsf Date"},
        {"cardNumber", "Card Number"},
        {"suffixOnCard", "Suffix On Card"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.firstNameOnCard = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.issueNumber = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.cardType = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.lastFailedAuthDate = UtilDateTime.nowTimestamp();
        this.consecutiveFailedNsf = "";
        this.contactMechId = "";
        this.companyNameOnCard = "";
        this.titleOnCard = "";
        this.lastNameOnCard = "";
        this.paymentMethodId = "";
        this.middleNameOnCard = "";
        this.validFromDate = UtilDateTime.nowTimestamp();
        this.expireDate = "";
        this.consecutiveFailedAuths = "";
        this.lastFailedNsfDate = UtilDateTime.nowTimestamp();
        this.cardNumber = "";
        this.suffixOnCard = "";
    }

    @Override
    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.firstNameOnCard = (java.lang.String) genVal.get("firstNameOnCard");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.issueNumber = (java.lang.String) genVal.get("issueNumber");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.cardType = (java.lang.String) genVal.get("cardType");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.lastFailedAuthDate = (java.sql.Timestamp) genVal.get("lastFailedAuthDate");
        this.consecutiveFailedNsf = (java.lang.String) genVal.get("consecutiveFailedNsf");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.companyNameOnCard = (java.lang.String) genVal.get("companyNameOnCard");
        this.titleOnCard = (java.lang.String) genVal.get("titleOnCard");
        this.lastNameOnCard = (java.lang.String) genVal.get("lastNameOnCard");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.middleNameOnCard = (java.lang.String) genVal.get("middleNameOnCard");
        this.validFromDate = (java.sql.Timestamp) genVal.get("validFromDate");
        this.expireDate = (java.lang.String) genVal.get("expireDate");
        this.consecutiveFailedAuths = (java.lang.String) genVal.get("consecutiveFailedAuths");
        this.lastFailedNsfDate = (java.sql.Timestamp) genVal.get("lastFailedNsfDate");
        this.cardNumber = (java.lang.String) genVal.get("cardNumber");
        this.suffixOnCard = (java.lang.String) genVal.get("suffixOnCard");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("firstNameOnCard", OrderMaxUtility.getValidEntityString(this.firstNameOnCard));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("issueNumber", OrderMaxUtility.getValidEntityString(this.issueNumber));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("cardType", OrderMaxUtility.getValidEntityString(this.cardType));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("lastFailedAuthDate", OrderMaxUtility.getValidEntityTimestamp(this.lastFailedAuthDate));
        val.set("consecutiveFailedNsf", OrderMaxUtility.getValidEntityString(this.consecutiveFailedNsf));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("companyNameOnCard", OrderMaxUtility.getValidEntityString(this.companyNameOnCard));
        val.set("titleOnCard", OrderMaxUtility.getValidEntityString(this.titleOnCard));
        val.set("lastNameOnCard", OrderMaxUtility.getValidEntityString(this.lastNameOnCard));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("middleNameOnCard", OrderMaxUtility.getValidEntityString(this.middleNameOnCard));
        val.set("validFromDate", OrderMaxUtility.getValidEntityTimestamp(this.validFromDate));
        val.set("expireDate", OrderMaxUtility.getValidEntityString(this.expireDate));
        val.set("consecutiveFailedAuths", OrderMaxUtility.getValidEntityString(this.consecutiveFailedAuths));
        val.set("lastFailedNsfDate", OrderMaxUtility.getValidEntityTimestamp(this.lastFailedNsfDate));
        val.set("cardNumber", OrderMaxUtility.getValidEntityString(this.cardNumber));
        val.set("suffixOnCard", OrderMaxUtility.getValidEntityString(this.suffixOnCard));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("firstNameOnCard", this.firstNameOnCard);
        valueMap.put("issueNumber", this.issueNumber);
        valueMap.put("cardType", this.cardType);
        valueMap.put("lastFailedAuthDate", this.lastFailedAuthDate);
        valueMap.put("consecutiveFailedNsf", this.consecutiveFailedNsf);
        valueMap.put("contactMechId", this.contactMechId);
        valueMap.put("companyNameOnCard", this.companyNameOnCard);
        valueMap.put("titleOnCard", this.titleOnCard);
        valueMap.put("lastNameOnCard", this.lastNameOnCard);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("middleNameOnCard", this.middleNameOnCard);
        valueMap.put("validFromDate", this.validFromDate);
        valueMap.put("expireDate", this.expireDate);
        valueMap.put("consecutiveFailedAuths", this.consecutiveFailedAuths);
        valueMap.put("lastFailedNsfDate", this.lastFailedNsfDate);
        valueMap.put("cardNumber", this.cardNumber);
        valueMap.put("suffixOnCard", this.suffixOnCard);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("CreditCard");
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

    public java.sql.Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String firstNameOnCard;

    public java.lang.String getFirstNameOnCard() {
        return firstNameOnCard;
    }

    public void setFirstNameOnCard(java.lang.String firstNameOnCard) {
        this.firstNameOnCard = firstNameOnCard;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String issueNumber;

    public java.lang.String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(java.lang.String issueNumber) {
        this.issueNumber = issueNumber;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String cardType;

    public java.lang.String getCardType() {
        return cardType;
    }

    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.sql.Timestamp lastFailedAuthDate;

    public java.sql.Timestamp getLastFailedAuthDate() {
        return lastFailedAuthDate;
    }

    public void setLastFailedAuthDate(java.sql.Timestamp lastFailedAuthDate) {
        this.lastFailedAuthDate = lastFailedAuthDate;
    }
    private java.lang.String consecutiveFailedNsf;

    public java.lang.String getConsecutiveFailedNsf() {
        return consecutiveFailedNsf;
    }

    public void setConsecutiveFailedNsf(java.lang.String consecutiveFailedNsf) {
        this.consecutiveFailedNsf = consecutiveFailedNsf;
    }
    private java.lang.String contactMechId;

    public java.lang.String getContactMechId() {
        return contactMechId;
    }

    public void setContactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.lang.String companyNameOnCard;

    public java.lang.String getCompanyNameOnCard() {
        return companyNameOnCard;
    }

    public void setCompanyNameOnCard(java.lang.String companyNameOnCard) {
        this.companyNameOnCard = companyNameOnCard;
    }
    private java.lang.String titleOnCard;

    public java.lang.String getTitleOnCard() {
        return titleOnCard;
    }

    public void setTitleOnCard(java.lang.String titleOnCard) {
        this.titleOnCard = titleOnCard;
    }
    private java.lang.String lastNameOnCard;

    public java.lang.String getLastNameOnCard() {
        return lastNameOnCard;
    }

    public void setLastNameOnCard(java.lang.String lastNameOnCard) {
        this.lastNameOnCard = lastNameOnCard;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    private java.lang.String middleNameOnCard;

    public java.lang.String getMiddleNameOnCard() {
        return middleNameOnCard;
    }

    public void setMiddleNameOnCard(java.lang.String middleNameOnCard) {
        this.middleNameOnCard = middleNameOnCard;
    }
    private java.sql.Timestamp validFromDate;

    public java.sql.Timestamp getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(java.sql.Timestamp validFromDate) {
        this.validFromDate = validFromDate;
    }
    private java.lang.String expireDate;

    public java.lang.String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(java.lang.String expireDate) {
        this.expireDate = expireDate;
    }
    private java.lang.String consecutiveFailedAuths;

    public java.lang.String getConsecutiveFailedAuths() {
        return consecutiveFailedAuths;
    }

    public void setConsecutiveFailedAuths(java.lang.String consecutiveFailedAuths) {
        this.consecutiveFailedAuths = consecutiveFailedAuths;
    }
    private java.sql.Timestamp lastFailedNsfDate;

    public java.sql.Timestamp getLastFailedNsfDate() {
        return lastFailedNsfDate;
    }

    public void setLastFailedNsfDate(java.sql.Timestamp lastFailedNsfDate) {
        this.lastFailedNsfDate = lastFailedNsfDate;
    }
    private java.lang.String cardNumber;

    public java.lang.String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }
    private java.lang.String suffixOnCard;

    public java.lang.String getSuffixOnCard() {
        return suffixOnCard;
    }

    public void setSuffixOnCard(java.lang.String suffixOnCard) {
        this.suffixOnCard = suffixOnCard;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CreditCard> objectList = new ArrayList<CreditCard>();
        for (GenericValue genVal : genList) {
            objectList.add(new CreditCard(genVal));
        }
        return objectList;
    }
}
