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

public class ShoppingList implements GenericValueObjectInterface , DisplayNameInterface{

    public static final String module = ShoppingList.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ShoppingList(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ShoppingList() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"listName", "List Name"},
        {"productStoreId", "Product Store Id"},
        {"currencyUom", "Currency Uom"},
        {"lastOrderedDate", "Last Ordered Date"},
        {"shoppingListTypeId", "Shopping List Type Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"parentShoppingListId", "Parent Shopping List Id"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"paymentMethodId", "Payment Method Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"carrierPartyId", "Carrier Party Id"},
        {"contactMechId", "Contact Mech Id"},
        {"productPromoCodeId", "Product Promo Code Id"},
        {"carrierRoleTypeId", "Carrier Role Type Id"},
        {"isPublic", "Is Public"},
        {"lastAdminModified", "Last Admin Modified"},
        {"isActive", "Is Active"},
        {"partyId", "Party Id"},
        {"visitorId", "Visitor Id"},
        {"shoppingListId", "Shopping List Id"},
        {"shipmentMethodTypeId", "Shipment Method Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"recurrenceInfoId", "Recurrence Info Id"},};

    protected void initObject() {
        this.listName = "";
        this.productStoreId = "";
        this.currencyUom = "";
        this.lastOrderedDate = UtilDateTime.nowTimestamp();
        this.shoppingListTypeId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.parentShoppingListId = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = "";
        this.paymentMethodId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.carrierPartyId = "";
        this.contactMechId = "";
        this.productPromoCodeId = "";
        this.carrierRoleTypeId = "";
        this.isPublic = "";
        this.lastAdminModified = null;
        this.isActive = "";
        this.partyId = "";
        this.visitorId = "";
        this.shoppingListId = "";
        this.shipmentMethodTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.recurrenceInfoId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.listName = (java.lang.String) genVal.get("listName");
        this.productStoreId = (java.lang.String) genVal.get("productStoreId");
        this.currencyUom = (java.lang.String) genVal.get("currencyUom");
        this.lastOrderedDate = (java.sql.Timestamp) genVal.get("lastOrderedDate");
        this.shoppingListTypeId = (java.lang.String) genVal.get("shoppingListTypeId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.parentShoppingListId = (java.lang.String) genVal.get("parentShoppingListId");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.paymentMethodId = (java.lang.String) genVal.get("paymentMethodId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.carrierPartyId = (java.lang.String) genVal.get("carrierPartyId");
        this.contactMechId = (java.lang.String) genVal.get("contactMechId");
        this.productPromoCodeId = (java.lang.String) genVal.get("productPromoCodeId");
        this.carrierRoleTypeId = (java.lang.String) genVal.get("carrierRoleTypeId");
        this.isPublic = (java.lang.String) genVal.get("isPublic");
        this.lastAdminModified = (java.sql.Timestamp) genVal.get("lastAdminModified");
        this.isActive = (java.lang.String) genVal.get("isActive");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.visitorId = (java.lang.String) genVal.get("visitorId");
        this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
        this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.recurrenceInfoId = (java.lang.String) genVal.get("recurrenceInfoId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("listName", OrderMaxUtility.getValidEntityString(this.listName));
        val.set("productStoreId", OrderMaxUtility.getValidEntityString(this.productStoreId));
        val.set("currencyUom", OrderMaxUtility.getValidEntityString(this.currencyUom));
        val.set("lastOrderedDate", OrderMaxUtility.getValidEntityTimestamp(this.lastOrderedDate));
        val.set("shoppingListTypeId", OrderMaxUtility.getValidEntityString(this.shoppingListTypeId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("parentShoppingListId", OrderMaxUtility.getValidEntityString(this.parentShoppingListId));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("paymentMethodId", OrderMaxUtility.getValidEntityString(this.paymentMethodId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("carrierPartyId", OrderMaxUtility.getValidEntityString(this.carrierPartyId));
        val.set("contactMechId", OrderMaxUtility.getValidEntityString(this.contactMechId));
        val.set("productPromoCodeId", OrderMaxUtility.getValidEntityString(this.productPromoCodeId));
        val.set("carrierRoleTypeId", OrderMaxUtility.getValidEntityString(this.carrierRoleTypeId));
        val.set("isPublic", OrderMaxUtility.getValidEntityString(this.isPublic));
        val.set("lastAdminModified", OrderMaxUtility.getValidEntityTimestamp(this.lastAdminModified));
        val.set("isActive", OrderMaxUtility.getValidEntityString(this.isActive));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("visitorId", OrderMaxUtility.getValidEntityString(this.visitorId));
        val.set("shoppingListId", OrderMaxUtility.getValidEntityString(this.shoppingListId));
        val.set("shipmentMethodTypeId", OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("recurrenceInfoId", OrderMaxUtility.getValidEntityString(this.recurrenceInfoId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("listName", this.listName);
        valueMap.put("productStoreId", this.productStoreId);
        valueMap.put("currencyUom", this.currencyUom);
        valueMap.put("lastOrderedDate", this.lastOrderedDate);
        valueMap.put("shoppingListTypeId", this.shoppingListTypeId);
        valueMap.put("parentShoppingListId", this.parentShoppingListId);
        valueMap.put("description", this.description);
        valueMap.put("paymentMethodId", this.paymentMethodId);
        valueMap.put("carrierPartyId", this.carrierPartyId);
        valueMap.put("contactMechId", this.contactMechId);
        valueMap.put("productPromoCodeId", this.productPromoCodeId);
        valueMap.put("carrierRoleTypeId", this.carrierRoleTypeId);
        valueMap.put("isPublic", this.isPublic);
        valueMap.put("lastAdminModified", this.lastAdminModified);
        valueMap.put("isActive", this.isActive);
        valueMap.put("partyId", this.partyId);
        valueMap.put("visitorId", this.visitorId);
        valueMap.put("shoppingListId", this.shoppingListId);
        valueMap.put("shipmentMethodTypeId", this.shipmentMethodTypeId);
        valueMap.put("recurrenceInfoId", this.recurrenceInfoId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ShoppingList");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String listName;

    public java.lang.String getlistName() {
        return listName;
    }

    public void setlistName(java.lang.String listName) {
        this.listName = listName;
    }
    private java.lang.String productStoreId;

    public java.lang.String getproductStoreId() {
        return productStoreId;
    }

    public void setproductStoreId(java.lang.String productStoreId) {
        this.productStoreId = productStoreId;
    }
    private java.lang.String currencyUom;

    public java.lang.String getcurrencyUom() {
        return currencyUom;
    }

    public void setcurrencyUom(java.lang.String currencyUom) {
        this.currencyUom = currencyUom;
    }
    private java.sql.Timestamp lastOrderedDate;

    public java.sql.Timestamp getlastOrderedDate() {
        return lastOrderedDate;
    }

    public void setlastOrderedDate(java.sql.Timestamp lastOrderedDate) {
        this.lastOrderedDate = lastOrderedDate;
    }
    private java.lang.String shoppingListTypeId;

    public java.lang.String getshoppingListTypeId() {
        return shoppingListTypeId;
    }

    public void setshoppingListTypeId(java.lang.String shoppingListTypeId) {
        this.shoppingListTypeId = shoppingListTypeId;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String parentShoppingListId;

    public java.lang.String getparentShoppingListId() {
        return parentShoppingListId;
    }

    public void setparentShoppingListId(java.lang.String parentShoppingListId) {
        this.parentShoppingListId = parentShoppingListId;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String paymentMethodId;

    public java.lang.String getpaymentMethodId() {
        return paymentMethodId;
    }

    public void setpaymentMethodId(java.lang.String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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
    private java.lang.String contactMechId;

    public java.lang.String getcontactMechId() {
        return contactMechId;
    }

    public void setcontactMechId(java.lang.String contactMechId) {
        this.contactMechId = contactMechId;
    }
    private java.lang.String productPromoCodeId;

    public java.lang.String getproductPromoCodeId() {
        return productPromoCodeId;
    }

    public void setproductPromoCodeId(java.lang.String productPromoCodeId) {
        this.productPromoCodeId = productPromoCodeId;
    }
    private java.lang.String carrierRoleTypeId;

    public java.lang.String getcarrierRoleTypeId() {
        return carrierRoleTypeId;
    }

    public void setcarrierRoleTypeId(java.lang.String carrierRoleTypeId) {
        this.carrierRoleTypeId = carrierRoleTypeId;
    }
    private java.lang.String isPublic;

    public java.lang.String getisPublic() {
        return isPublic;
    }

    public void setisPublic(java.lang.String isPublic) {
        this.isPublic = isPublic;
    }
    private java.sql.Timestamp lastAdminModified;

    public java.sql.Timestamp getlastAdminModified() {
        return lastAdminModified;
    }

    public void setlastAdminModified(java.sql.Timestamp lastAdminModified) {
        this.lastAdminModified = lastAdminModified;
    }
    private java.lang.String isActive;

    public java.lang.String getisActive() {
        return isActive;
    }

    public void setisActive(java.lang.String isActive) {
        this.isActive = isActive;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String visitorId;

    public java.lang.String getvisitorId() {
        return visitorId;
    }

    public void setvisitorId(java.lang.String visitorId) {
        this.visitorId = visitorId;
    }
    private java.lang.String shoppingListId;

    public java.lang.String getshoppingListId() {
        return shoppingListId;
    }

    public void setshoppingListId(java.lang.String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
    private java.lang.String shipmentMethodTypeId;

    public java.lang.String getshipmentMethodTypeId() {
        return shipmentMethodTypeId;
    }

    public void setshipmentMethodTypeId(java.lang.String shipmentMethodTypeId) {
        this.shipmentMethodTypeId = shipmentMethodTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String recurrenceInfoId;

    public java.lang.String getrecurrenceInfoId() {
        return recurrenceInfoId;
    }

    public void setrecurrenceInfoId(java.lang.String recurrenceInfoId) {
        this.recurrenceInfoId = recurrenceInfoId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShoppingList> objectList = new ArrayList<ShoppingList>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShoppingList(genVal));
        }
        return objectList;
    }
    
    @Override
    public String getdisplayName(DisplayTypes showKey) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey) || DisplayTypes.SHOW_NAME_ONLY.equals(showKey)) {

            orderToStringBuilder.append(getlistName());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showKey)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getshoppingListId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getshoppingListId());
        }

        return orderToStringBuilder.toString();
    }
}
