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

public class PaymentMethod implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = PaymentMethod.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PaymentMethod(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public PaymentMethod() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"glAccountId", "Gl Account Id"},
        {"description", "Description"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"paymentMethodTypeId", "Payment Method Type Id"},
        {"thruDate", "Thru Date"},
        {"fromDate", "From Date"},
        {"finAccountId", "Fin Account Id"},
        {"paymentMethodId", "Payment Method Id"},
        {"partyId", "Party Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.glAccountId = "";
        this.description = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.paymentMethodTypeId = "";
        this.thruDate = UtilDateTime.nowTimestamp();
        this.fromDate = UtilDateTime.nowTimestamp();
        this.finAccountId = "";
        this.paymentMethodId = "";
        this.partyId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.glAccountId = (java.lang.String) genVal.get("glAccountId");
        this.description = (java.lang.String) genVal.get("description");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.finAccountId = (java.lang.String) genVal.get("finAccountId");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.partyId = (java.lang.String) genVal.get("partyId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("glAccountId", OrderMaxUtility.getValidEntityString(this.glAccountId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("paymentMethodTypeId", OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("finAccountId", OrderMaxUtility.getValidEntityString(this.finAccountId));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("glAccountId", this.glAccountId);
        valueMap.put("description", this.description);
        valueMap.put("paymentMethodTypeId", this.paymentMethodTypeId);
        valueMap.put("thruDate", this.thruDate);
        valueMap.put("fromDate", this.fromDate);
        valueMap.put("finAccountId", this.finAccountId);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("partyId", this.partyId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PaymentMethod");
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
    private java.lang.String glAccountId;

    public java.lang.String getglAccountId() {
        return glAccountId;
    }

    public void setglAccountId(java.lang.String glAccountId) {
        this.glAccountId = glAccountId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String paymentMethodTypeId;

    public java.lang.String getpaymentMethodTypeId() {
        return paymentMethodTypeId;
    }

    public void setpaymentMethodTypeId(java.lang.String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }
    private java.sql.Timestamp thruDate;

    public java.sql.Timestamp getthruDate() {
        return thruDate;
    }

    public void setthruDate(java.sql.Timestamp thruDate) {
        this.thruDate = thruDate;
    }
    private java.sql.Timestamp fromDate;

    public java.sql.Timestamp getfromDate() {
        return fromDate;
    }

    public void setfromDate(java.sql.Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    private java.lang.String finAccountId;

    public java.lang.String getfinAccountId() {
        return finAccountId;
    }

    public void setfinAccountId(java.lang.String finAccountId) {
        this.finAccountId = finAccountId;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getpaymentMethodId() {
        return paymentMethodId;
    }

    public void setpaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PaymentMethod> objectList = new ArrayList<PaymentMethod>();
        for (GenericValue genVal : genList) {
            objectList.add(new PaymentMethod(genVal));
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
                orderToStringBuilder.append(getpaymentMethodId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getpaymentMethodId());
        }

        return orderToStringBuilder.toString();
    }
    
}
