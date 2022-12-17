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

public class OrderItemShipGroup implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = OrderItemShipGroup.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public OrderItemShipGroup(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public OrderItemShipGroup() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"supplierPartyId", "Supplier Party Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"carrierPartyId", "Carrier Party Id"},
        {"giftMessage", "Gift Message"},
        {"contactMechId", "Contact Mech Id"},
        {"shipByDate", "Ship By Date"},
        {"estimatedDeliveryDate", "Estimated Delivery Date"},
        {"carrierRoleTypeId", "Carrier Role Type Id"},
        {"telecomContactMechId", "Telecom Contact Mech Id"},
        {"shippingInstructions", "Shipping Instructions"},
        {"facilityId", "Facility Id"},
        {"maySplit", "May Split"},
        {"isGift", "Is Gift"},
        {"vendorPartyId", "Vendor Party Id"},
        {"trackingNumber", "Tracking Number"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"shipAfterDate", "Ship After Date"},
        {"shipGroupSeqId", "Ship Group Seq Id"},
        {"shipmentMethodTypeId", "Shipment Method Type Id"},
        {"estimatedShipDate", "Estimated Ship Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"orderId", "Order Id"},};

    protected void initObject() {
        this.supplierPartyId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.carrierPartyId = "";
        this.giftMessage = "";
        this.contactMechId = "";
        this.shipByDate = UtilDateTime.nowTimestamp();
        this.estimatedDeliveryDate = UtilDateTime.nowTimestamp();
        this.carrierRoleTypeId = "";
        this.telecomContactMechId = "";
        this.shippingInstructions = "";
        this.facilityId = "";
        this.maySplit = "";
        this.isGift = "";
        this.vendorPartyId = "";
        this.trackingNumber = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.shipAfterDate = UtilDateTime.nowTimestamp();
        this.shipGroupSeqId = "";
        this.shipmentMethodTypeId = "";
        this.estimatedShipDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.orderId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.supplierPartyId = (java.lang.String) genVal.get("supplierPartyId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
        this.giftMessage = (java.lang.String) genVal.get("giftMessage");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.shipByDate = (java.sql.Timestamp) genVal.get("shipByDate");
        this.estimatedDeliveryDate = (java.sql.Timestamp) genVal.get("estimatedDeliveryDate");
        this.carrierRoleTypeId = (java.lang.String) genVal.get("carrierRoleTypeId");
        this.telecomContactMechId = (java.lang.String) genVal.get("telecomContactMechId");
        this.shippingInstructions = (java.lang.String) genVal.get("shippingInstructions");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.maySplit = (java.lang.String) genVal.get("maySplit");
        this.isGift = (java.lang.String) genVal.get("isGift");
        this.vendorPartyId = (java.lang.String) genVal.get("vendorPartyId");
        this.trackingNumber = (java.lang.String) genVal.get("trackingNumber");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.shipAfterDate = (java.sql.Timestamp) genVal.get("shipAfterDate");
        this.shipGroupSeqId = (java.lang.String) genVal.get("shipGroupSeqId");
        this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
        this.estimatedShipDate = (java.sql.Timestamp) genVal.get("estimatedShipDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.orderId = (java.lang.String) genVal.get("orderId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("supplierPartyId", OrderMaxUtility.getValidEntityString(this.supplierPartyId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("carrierPartyId", OrderMaxUtility.getValidEntityString(this.carrierPartyId));
        val.set("giftMessage", OrderMaxUtility.getValidEntityString(this.giftMessage));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("shipByDate", OrderMaxUtility.getValidEntityTimestamp(this.shipByDate));
        val.set("estimatedDeliveryDate", OrderMaxUtility.getValidEntityTimestamp(this.estimatedDeliveryDate));
        val.set("carrierRoleTypeId", OrderMaxUtility.getValidEntityString(this.carrierRoleTypeId));
        val.set("telecomContactMechId", OrderMaxUtility.getValidEntityString(this.telecomContactMechId));
        val.set("shippingInstructions", OrderMaxUtility.getValidEntityString(this.shippingInstructions));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("maySplit", OrderMaxUtility.getValidEntityString(this.maySplit));
        val.set("isGift", OrderMaxUtility.getValidEntityString(this.isGift));
        val.set("vendorPartyId", OrderMaxUtility.getValidEntityString(this.vendorPartyId));
        val.set("trackingNumber", OrderMaxUtility.getValidEntityString(this.trackingNumber));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("shipAfterDate", OrderMaxUtility.getValidEntityTimestamp(this.shipAfterDate));
        val.set("shipGroupSeqId", OrderMaxUtility.getValidEntityString(this.shipGroupSeqId));
        val.set("shipmentMethodTypeId", OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
        val.set("estimatedShipDate", OrderMaxUtility.getValidEntityTimestamp(this.estimatedShipDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("orderId", OrderMaxUtility.getValidEntityString(this.orderId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("supplierPartyId", this.supplierPartyId);
        valueMap.put("carrierPartyId", this.carrierPartyId);
        valueMap.put("giftMessage", this.giftMessage);
        valueMap.put("contactMechId", this.contactMechId);
        valueMap.put("shipByDate", this.shipByDate);
        valueMap.put("estimatedDeliveryDate", this.estimatedDeliveryDate);
        valueMap.put("carrierRoleTypeId", this.carrierRoleTypeId);
        valueMap.put("telecomContactMechId", this.telecomContactMechId);
        valueMap.put("shippingInstructions", this.shippingInstructions);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("maySplit", this.maySplit);
        valueMap.put("isGift", this.isGift);
        valueMap.put("vendorPartyId", this.vendorPartyId);
        valueMap.put("trackingNumber", this.trackingNumber);
        valueMap.put("shipAfterDate", this.shipAfterDate);
        valueMap.put("shipGroupSeqId", this.shipGroupSeqId);
        valueMap.put("shipmentMethodTypeId", this.shipmentMethodTypeId);
        valueMap.put("estimatedShipDate", this.estimatedShipDate);
        valueMap.put("orderId", this.orderId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("OrderItemShipGroup");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String supplierPartyId;

    public java.lang.String getsupplierPartyId() {
        return supplierPartyId;
    }

    public void setsupplierPartyId(java.lang.String supplierPartyId) {
        this.supplierPartyId = supplierPartyId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String carrierPartyId;

    public java.lang.String getcarrierPartyId() {
        return carrierPartyId;
    }

    public void setcarrierPartyId(java.lang.String carrierPartyId) {
        this.carrierPartyId = carrierPartyId;
    }
    private java.lang.String giftMessage;

    public java.lang.String getgiftMessage() {
        return giftMessage;
    }

    public void setgiftMessage(java.lang.String giftMessage) {
        this.giftMessage = giftMessage;
    }
    private java.lang.String contactMechId;

    public java.lang.String getcontactMechId() {
        return contactMechId;
    }

    public void setcontactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.sql.Timestamp shipByDate;

    public java.sql.Timestamp getshipByDate() {
        return shipByDate;
    }

    public void setshipByDate(java.sql.Timestamp shipByDate) {
        this.shipByDate = shipByDate;
    }
    private java.sql.Timestamp estimatedDeliveryDate;

    public java.sql.Timestamp getestimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setestimatedDeliveryDate(java.sql.Timestamp estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    private java.lang.String carrierRoleTypeId;

    public java.lang.String getcarrierRoleTypeId() {
        return carrierRoleTypeId;
    }

    public void setcarrierRoleTypeId(java.lang.String carrierRoleTypeId) {
        this.carrierRoleTypeId = carrierRoleTypeId;
    }
    private java.lang.String telecomContactMechId;

    public java.lang.String gettelecomContactMechId() {
        return telecomContactMechId;
    }

    public void settelecomContactMechId(java.lang.String telecomContactMechId) {
        this.telecomContactMechId = telecomContactMechId;
    }
    private java.lang.String shippingInstructions;

    public java.lang.String getshippingInstructions() {
        return shippingInstructions;
    }

    public void setshippingInstructions(java.lang.String shippingInstructions) {
        this.shippingInstructions = shippingInstructions;
    }
    private java.lang.String facilityId;

    public java.lang.String getfacilityId() {
        return facilityId;
    }

    public void setfacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.lang.String maySplit;

    public java.lang.String getmaySplit() {
        return maySplit;
    }

    public void setmaySplit(java.lang.String maySplit) {
        this.maySplit = maySplit;
    }
    private java.lang.String isGift;

    public java.lang.String getisGift() {
        return isGift;
    }

    public void setisGift(java.lang.String isGift) {
        this.isGift = isGift;
    }
    private java.lang.String vendorPartyId;

    public java.lang.String getvendorPartyId() {
        return vendorPartyId;
    }

    public void setvendorPartyId(java.lang.String vendorPartyId) {
        this.vendorPartyId = vendorPartyId;
    }
    private java.lang.String trackingNumber;

    public java.lang.String gettrackingNumber() {
        return trackingNumber;
    }

    public void settrackingNumber(java.lang.String trackingNumber) {
        this.trackingNumber = trackingNumber;
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
    private java.sql.Timestamp shipAfterDate;

    public java.sql.Timestamp getshipAfterDate() {
        return shipAfterDate;
    }

    public void setshipAfterDate(java.sql.Timestamp shipAfterDate) {
        this.shipAfterDate = shipAfterDate;
    }
    private java.lang.String shipGroupSeqId;

    public java.lang.String getshipGroupSeqId() {
        return shipGroupSeqId;
    }

    public void setshipGroupSeqId(java.lang.String shipGroupSeqId) {
        this.shipGroupSeqId = shipGroupSeqId;
    }
    private java.lang.String shipmentMethodTypeId;

    public java.lang.String getshipmentMethodTypeId() {
        return shipmentMethodTypeId;
    }

    public void setshipmentMethodTypeId(java.lang.String shipmentMethodTypeId) {
        this.shipmentMethodTypeId = shipmentMethodTypeId;
    }
    private java.sql.Timestamp estimatedShipDate;

    public java.sql.Timestamp getestimatedShipDate() {
        return estimatedShipDate;
    }

    public void setestimatedShipDate(java.sql.Timestamp estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String orderId;

    public java.lang.String getorderId() {
        return orderId;
    }

    public void setorderId(java.lang.String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItemShipGroup> objectList = new ArrayList<OrderItemShipGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemShipGroup(genVal));
        }
        return objectList;
    }
    
@Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append("Ship Group Nbr");

//            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" - ");
                orderToStringBuilder.append(shipGroupSeqId);
                orderToStringBuilder.append(" ");
//            }
        } else {
            orderToStringBuilder.append(shipGroupSeqId);
        }

        return orderToStringBuilder.toString();
    }    
}
