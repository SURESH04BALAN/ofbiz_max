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

public class ReturnHeader implements GenericValueObjectInterface {

    public static final String module = ReturnHeader.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ReturnHeader(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ReturnHeader() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"fromPartyId", "From Party Id"},
        {"entryDate", "Entry Date"},
        {"originContactMechId", "Origin Contact Mech Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"billingAccountId", "Billing Account Id"},
        {"returnHeaderTypeId", "Return Header Type Id"},
        {"finAccountId", "Fin Account Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"statusId", "Status Id"},
        {"createdBy", "Created By"},
        {"paymentMethodId", "Payment Method Id"},
        {"needsInventoryReceive", "Needs Inventory Receive"},
        {"returnId", "Return Id"},
        {"toPartyId", "To Party Id"},
        {"destinationFacilityId", "Destination Facility Id"},
        {"supplierRmaId", "Supplier Rma Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.fromPartyId = "";
        this.entryDate = UtilDateTime.nowTimestamp();
        this.originContactMechId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.billingAccountId = "";
        this.returnHeaderTypeId = "";
        this.finAccountId = "";
        this.currencyUomId = "";
        this.statusId = "";
        this.createdBy = "";
        this.paymentMethodId = "";
        this.needsInventoryReceive = "";
        this.returnId = "";
        this.toPartyId = "";
        this.destinationFacilityId = "";
        this.supplierRmaId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.fromPartyId = (java.lang.String) genVal.get("fromPartyId");
        this.entryDate = (java.sql.Timestamp) genVal.get("entryDate");
        this.originContactMechId = (java.lang.String) genVal.get("originContactMechId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
        this.returnHeaderTypeId = (java.lang.String) genVal.get("returnHeaderTypeId");
        this.finAccountId = (java.lang.String) genVal.get("finAccountId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.createdBy = (java.lang.String) genVal.get("createdBy");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.needsInventoryReceive = (java.lang.String) genVal.get("needsInventoryReceive");
        this.returnId = (java.lang.String) genVal.get("returnId");
        this.toPartyId = (java.lang.String) genVal.get("toPartyId");
        this.destinationFacilityId = (java.lang.String) genVal.get("destinationFacilityId");
        this.supplierRmaId = (java.lang.String) genVal.get("supplierRmaId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("fromPartyId", OrderMaxUtility.getValidEntityString(this.fromPartyId));
        val.set("entryDate", OrderMaxUtility.getValidEntityTimestamp(this.entryDate));
        val.set("originContactMechId", OrderMaxUtility.getValidEntityString(this.originContactMechId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("billingAccountId", OrderMaxUtility.getValidEntityString(this.billingAccountId));
        val.set("returnHeaderTypeId", OrderMaxUtility.getValidEntityString(this.returnHeaderTypeId));
        val.set("finAccountId", OrderMaxUtility.getValidEntityString(this.finAccountId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("createdBy", OrderMaxUtility.getValidEntityString(this.createdBy));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("needsInventoryReceive", OrderMaxUtility.getValidEntityString(this.needsInventoryReceive));
        val.set("returnId", OrderMaxUtility.getValidEntityString(this.returnId));
        val.set("toPartyId", OrderMaxUtility.getValidEntityString(this.toPartyId));
        val.set("destinationFacilityId", OrderMaxUtility.getValidEntityString(this.destinationFacilityId));
        val.set("supplierRmaId", OrderMaxUtility.getValidEntityString(this.supplierRmaId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("fromPartyId", this.fromPartyId);
        valueMap.put("entryDate", this.entryDate);
        valueMap.put("originContactMechId", this.originContactMechId);
        valueMap.put("billingAccountId", this.billingAccountId);
        valueMap.put("returnHeaderTypeId", this.returnHeaderTypeId);
        valueMap.put("finAccountId", this.finAccountId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("createdBy", this.createdBy);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("needsInventoryReceive", this.needsInventoryReceive);
        valueMap.put("returnId", this.returnId);
        valueMap.put("toPartyId", this.toPartyId);
        valueMap.put("destinationFacilityId", this.destinationFacilityId);
        valueMap.put("supplierRmaId", this.supplierRmaId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ReturnHeader");
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
    private java.lang.String fromPartyId;

    public java.lang.String getfromPartyId() {
        return fromPartyId;
    }

    public void setfromPartyId(java.lang.String fromPartyId) {
        this.fromPartyId = fromPartyId;
    }
    private java.sql.Timestamp entryDate;

    public java.sql.Timestamp getentryDate() {
        return entryDate;
    }

    public void setentryDate(java.sql.Timestamp entryDate) {
        this.entryDate = entryDate;
    }
    private java.lang.String originContactMechId;

    public java.lang.String getoriginContactMechId() {
        return originContactMechId;
    }

    public void setoriginContactMechId(java.lang.String originContactMechId) {
        this.originContactMechId = originContactMechId;
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
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String billingAccountId;

    public java.lang.String getbillingAccountId() {
        return billingAccountId;
    }

    public void setbillingAccountId(java.lang.String billingAccountId) {
        this.billingAccountId = billingAccountId;
    }
    private java.lang.String returnHeaderTypeId;

    public java.lang.String getreturnHeaderTypeId() {
        return returnHeaderTypeId;
    }

    public void setreturnHeaderTypeId(java.lang.String returnHeaderTypeId) {
        this.returnHeaderTypeId = returnHeaderTypeId;
    }
    private java.lang.String finAccountId;

    public java.lang.String getfinAccountId() {
        return finAccountId;
    }

    public void setfinAccountId(java.lang.String finAccountId) {
        this.finAccountId = finAccountId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String createdBy;

    public java.lang.String getcreatedBy() {
        return createdBy;
    }

    public void setcreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getpaymentMethodId() {
        return paymentMethodId;
    }

    public void setpaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    private java.lang.String needsInventoryReceive;

    public java.lang.String getneedsInventoryReceive() {
        return needsInventoryReceive;
    }

    public void setneedsInventoryReceive(java.lang.String needsInventoryReceive) {
        this.needsInventoryReceive = needsInventoryReceive;
    }
    private java.lang.String returnId;

    public java.lang.String getreturnId() {
        return returnId;
    }

    public void setreturnId(java.lang.String returnId) {
        this.returnId = returnId;
    }
    private java.lang.String toPartyId;

    public java.lang.String gettoPartyId() {
        return toPartyId;
    }

    public void settoPartyId(java.lang.String toPartyId) {
        this.toPartyId = toPartyId;
    }
    private java.lang.String destinationFacilityId;

    public java.lang.String getdestinationFacilityId() {
        return destinationFacilityId;
    }

    public void setdestinationFacilityId(java.lang.String destinationFacilityId) {
        this.destinationFacilityId = destinationFacilityId;
    }
    private java.lang.String supplierRmaId;

    public java.lang.String getsupplierRmaId() {
        return supplierRmaId;
    }

    public void setsupplierRmaId(java.lang.String supplierRmaId) {
        this.supplierRmaId = supplierRmaId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ReturnHeader> objectList = new ArrayList<ReturnHeader>();
        for (GenericValue genVal : genList) {
            objectList.add(new ReturnHeader(genVal));
        }
        return objectList;
    }
}
