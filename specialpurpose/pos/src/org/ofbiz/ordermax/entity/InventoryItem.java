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

public class InventoryItem implements GenericValueObjectInterface {

    public static final String module = InventoryItem.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InventoryItem(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public InventoryItem() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"serialNumber", "Serial Number"},
        {"availableToPromiseTotal", "Available To Promise Total"},
        {"locationSeqId", "Location Seq Id"},
        {"activationValidThru", "Activation Valid Thru"},
        {"statusId", "Status Id"},
        {"oldQuantityOnHand", "Old Quantity On Hand"},
        {"inventoryItemTypeId", "Inventory Item Type Id"},
        {"unitCost", "Unit Cost"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"lotId", "Lot Id"},
        {"binNumber", "Bin Number"},
        {"datetimeManufactured", "Datetime Manufactured"},
        {"inventoryItemId", "Inventory Item Id"},
        {"fixedAssetId", "Fixed Asset Id"},
        {"oldAvailableToPromise", "Old Available To Promise"},
        {"ownerPartyId", "Owner Party Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"accountingQuantityTotal", "Accounting Quantity Total"},
        {"quantityOnHandTotal", "Quantity On Hand Total"},
        {"currencyUomId", "Currency Uom Id"},
        {"uomId", "Uom Id"},
        {"facilityId", "Facility Id"},
        {"productId", "Product Id"},
        {"partyId", "Party Id"},
        {"expireDate", "Expire Date"},
        {"softIdentifier", "Soft Identifier"},
        {"activationNumber", "Activation Number"},
        {"datetimeReceived", "Datetime Received"},
        {"containerId", "Container Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"comments", "Comments"},};

    protected void initObject() {
        this.serialNumber = "";
        this.availableToPromiseTotal = java.math.BigDecimal.ZERO;
        this.locationSeqId = "";
        this.activationValidThru = "";
        this.statusId = "";
        this.oldQuantityOnHand = "";
        this.inventoryItemTypeId = "";
        this.unitCost = java.math.BigDecimal.ZERO;
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.lotId = "";
        this.binNumber = "";
        this.datetimeManufactured = UtilDateTime.nowTimestamp();
        this.inventoryItemId = "";
        this.fixedAssetId = "";
        this.oldAvailableToPromise = "";
        this.ownerPartyId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.accountingQuantityTotal = java.math.BigDecimal.ZERO;
        this.quantityOnHandTotal = java.math.BigDecimal.ZERO;
        this.currencyUomId = "";
        this.uomId = "";
        this.facilityId = "";
        this.productId = "";
        this.partyId = "";
        this.expireDate = UtilDateTime.nowTimestamp();
        this.softIdentifier = "";
        this.activationNumber = "";
        this.datetimeReceived = UtilDateTime.nowTimestamp();
        this.containerId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.comments = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.serialNumber = (java.lang.String) genVal.get("serialNumber");
        this.availableToPromiseTotal = (java.math.BigDecimal) genVal.get("availableToPromiseTotal");
        this.locationSeqId = (java.lang.String) genVal.get("locationSeqId");
        this.activationValidThru = (java.lang.String) genVal.get("activationValidThru");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.oldQuantityOnHand = (java.lang.String) genVal.get("oldQuantityOnHand");
        this.inventoryItemTypeId = (java.lang.String) genVal.get("inventoryItemTypeId");
        this.unitCost = (java.math.BigDecimal) genVal.get("unitCost");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.lotId = (java.lang.String) genVal.get("lotId");
        this.binNumber = (java.lang.String) genVal.get("binNumber");
        this.datetimeManufactured = (java.sql.Timestamp) genVal.get("datetimeManufactured");
        this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
        this.fixedAssetId = (java.lang.String) genVal.get("fixedAssetId");
        this.oldAvailableToPromise = (java.lang.String) genVal.get("oldAvailableToPromise");
        this.ownerPartyId = (java.lang.String) genVal.get("ownerPartyId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.accountingQuantityTotal = (java.math.BigDecimal) genVal.get("accountingQuantityTotal");
        this.quantityOnHandTotal = (java.math.BigDecimal) genVal.get("quantityOnHandTotal");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.facilityId = (java.lang.String) genVal.get("facilityId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.expireDate = (java.sql.Timestamp) genVal.get("expireDate");
        this.softIdentifier = (java.lang.String) genVal.get("softIdentifier");
        this.activationNumber = (java.lang.String) genVal.get("activationNumber");
        this.datetimeReceived = (java.sql.Timestamp) genVal.get("datetimeReceived");
        this.containerId = (java.lang.String) genVal.get("containerId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.comments = (java.lang.String) genVal.get("comments");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("serialNumber", OrderMaxUtility.getValidEntityString(this.serialNumber));
        val.set("availableToPromiseTotal", OrderMaxUtility.getValidEntityBigDecimal(this.availableToPromiseTotal));
        val.set("locationSeqId", OrderMaxUtility.getValidEntityString(this.locationSeqId));
        val.set("activationValidThru", OrderMaxUtility.getValidEntityString(this.activationValidThru));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("oldQuantityOnHand", OrderMaxUtility.getValidEntityString(this.oldQuantityOnHand));
        val.set("inventoryItemTypeId", OrderMaxUtility.getValidEntityString(this.inventoryItemTypeId));
        val.set("unitCost", OrderMaxUtility.getValidEntityBigDecimal(this.unitCost));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("lotId", OrderMaxUtility.getValidEntityString(this.lotId));
        val.set("binNumber", OrderMaxUtility.getValidEntityString(this.binNumber));
        val.set("datetimeManufactured", OrderMaxUtility.getValidEntityTimestamp(this.datetimeManufactured));
        val.set("inventoryItemId", OrderMaxUtility.getValidEntityString(this.inventoryItemId));
        val.set("fixedAssetId", OrderMaxUtility.getValidEntityString(this.fixedAssetId));
        val.set("oldAvailableToPromise", OrderMaxUtility.getValidEntityString(this.oldAvailableToPromise));
        val.set("ownerPartyId", OrderMaxUtility.getValidEntityString(this.ownerPartyId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("accountingQuantityTotal", OrderMaxUtility.getValidEntityBigDecimal(this.accountingQuantityTotal));
        val.set("quantityOnHandTotal", OrderMaxUtility.getValidEntityBigDecimal(this.quantityOnHandTotal));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("facilityId", OrderMaxUtility.getValidEntityString(this.facilityId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("expireDate", OrderMaxUtility.getValidEntityTimestamp(this.expireDate));
        val.set("softIdentifier", OrderMaxUtility.getValidEntityString(this.softIdentifier));
        val.set("activationNumber", OrderMaxUtility.getValidEntityString(this.activationNumber));
        val.set("datetimeReceived", OrderMaxUtility.getValidEntityTimestamp(this.datetimeReceived));
        val.set("containerId", OrderMaxUtility.getValidEntityString(this.containerId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("serialNumber", this.serialNumber);
        valueMap.put("availableToPromiseTotal", this.availableToPromiseTotal);
        valueMap.put("locationSeqId", this.locationSeqId);
        valueMap.put("activationValidThru", this.activationValidThru);
        valueMap.put("statusId", this.statusId);
        valueMap.put("oldQuantityOnHand", this.oldQuantityOnHand);
        valueMap.put("inventoryItemTypeId", this.inventoryItemTypeId);
        valueMap.put("unitCost", this.unitCost);
        valueMap.put("lotId", this.lotId);
        valueMap.put("binNumber", this.binNumber);
        valueMap.put("datetimeManufactured", this.datetimeManufactured);
        valueMap.put("inventoryItemId", this.inventoryItemId);
        valueMap.put("fixedAssetId", this.fixedAssetId);
        valueMap.put("oldAvailableToPromise", this.oldAvailableToPromise);
        valueMap.put("ownerPartyId", this.ownerPartyId);
        valueMap.put("accountingQuantityTotal", this.accountingQuantityTotal);
        valueMap.put("quantityOnHandTotal", this.quantityOnHandTotal);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("uomId", this.uomId);
        valueMap.put("facilityId", this.facilityId);
        valueMap.put("productId", this.productId);
        valueMap.put("partyId", this.partyId);
        valueMap.put("expireDate", this.expireDate);
        valueMap.put("softIdentifier", this.softIdentifier);
        valueMap.put("activationNumber", this.activationNumber);
        valueMap.put("datetimeReceived", this.datetimeReceived);
        valueMap.put("containerId", this.containerId);
        valueMap.put("comments", this.comments);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InventoryItem");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String serialNumber;

    public java.lang.String getserialNumber() {
        return serialNumber;
    }

    public void setserialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }
    private java.math.BigDecimal availableToPromiseTotal;

    public java.math.BigDecimal getavailableToPromiseTotal() {
        return availableToPromiseTotal;
    }

    public void setavailableToPromiseTotal(java.math.BigDecimal availableToPromiseTotal) {
        this.availableToPromiseTotal = availableToPromiseTotal;
    }
    private java.lang.String locationSeqId;

    public java.lang.String getlocationSeqId() {
        return locationSeqId;
    }

    public void setlocationSeqId(java.lang.String locationSeqId) {
        this.locationSeqId = locationSeqId;
    }
    private java.lang.String activationValidThru;

    public java.lang.String getactivationValidThru() {
        return activationValidThru;
    }

    public void setactivationValidThru(java.lang.String activationValidThru) {
        this.activationValidThru = activationValidThru;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String oldQuantityOnHand;

    public java.lang.String getoldQuantityOnHand() {
        return oldQuantityOnHand;
    }

    public void setoldQuantityOnHand(java.lang.String oldQuantityOnHand) {
        this.oldQuantityOnHand = oldQuantityOnHand;
    }
    private java.lang.String inventoryItemTypeId;

    public java.lang.String getinventoryItemTypeId() {
        return inventoryItemTypeId;
    }

    public void setinventoryItemTypeId(java.lang.String inventoryItemTypeId) {
        this.inventoryItemTypeId = inventoryItemTypeId;
    }
    private java.math.BigDecimal unitCost;

    public java.math.BigDecimal getunitCost() {
        return unitCost;
    }

    public void setunitCost(java.math.BigDecimal unitCost) {
        this.unitCost = unitCost;
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
    private java.lang.String lotId;

    public java.lang.String getlotId() {
        return lotId;
    }

    public void setlotId(java.lang.String lotId) {
        this.lotId = lotId;
    }
    private java.lang.String binNumber;

    public java.lang.String getbinNumber() {
        return binNumber;
    }

    public void setbinNumber(java.lang.String binNumber) {
        this.binNumber = binNumber;
    }
    private java.sql.Timestamp datetimeManufactured;

    public java.sql.Timestamp getdatetimeManufactured() {
        return datetimeManufactured;
    }

    public void setdatetimeManufactured(java.sql.Timestamp datetimeManufactured) {
        this.datetimeManufactured = datetimeManufactured;
    }
    private java.lang.String inventoryItemId;

    public java.lang.String getinventoryItemId() {
        return inventoryItemId;
    }

    public void setinventoryItemId(java.lang.String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }
    private java.lang.String fixedAssetId;

    public java.lang.String getfixedAssetId() {
        return fixedAssetId;
    }

    public void setfixedAssetId(java.lang.String fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }
    private java.lang.String oldAvailableToPromise;

    public java.lang.String getoldAvailableToPromise() {
        return oldAvailableToPromise;
    }

    public void setoldAvailableToPromise(java.lang.String oldAvailableToPromise) {
        this.oldAvailableToPromise = oldAvailableToPromise;
    }
    private java.lang.String ownerPartyId;

    public java.lang.String getownerPartyId() {
        return ownerPartyId;
    }

    public void setownerPartyId(java.lang.String ownerPartyId) {
        this.ownerPartyId = ownerPartyId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.math.BigDecimal accountingQuantityTotal;

    public java.math.BigDecimal getaccountingQuantityTotal() {
        return accountingQuantityTotal;
    }

    public void setaccountingQuantityTotal(java.math.BigDecimal accountingQuantityTotal) {
        this.accountingQuantityTotal = accountingQuantityTotal;
    }
    private java.math.BigDecimal quantityOnHandTotal;

    public java.math.BigDecimal getquantityOnHandTotal() {
        return quantityOnHandTotal;
    }

    public void setquantityOnHandTotal(java.math.BigDecimal quantityOnHandTotal) {
        this.quantityOnHandTotal = quantityOnHandTotal;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String facilityId;

    public java.lang.String getfacilityId() {
        return facilityId;
    }

    public void setfacilityId(java.lang.String facilityId) {
        this.facilityId = facilityId;
    }
    private java.lang.String productId;

    public java.lang.String getProductId() {
        return productId;
    }

    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.sql.Timestamp expireDate;

    public java.sql.Timestamp getexpireDate() {
        return expireDate;
    }

    public void setexpireDate(java.sql.Timestamp expireDate) {
        this.expireDate = expireDate;
    }
    private java.lang.String softIdentifier;

    public java.lang.String getsoftIdentifier() {
        return softIdentifier;
    }

    public void setsoftIdentifier(java.lang.String softIdentifier) {
        this.softIdentifier = softIdentifier;
    }
    private java.lang.String activationNumber;

    public java.lang.String getactivationNumber() {
        return activationNumber;
    }

    public void setactivationNumber(java.lang.String activationNumber) {
        this.activationNumber = activationNumber;
    }
    private java.sql.Timestamp datetimeReceived;

    public java.sql.Timestamp getdatetimeReceived() {
        return datetimeReceived;
    }

    public void setdatetimeReceived(java.sql.Timestamp datetimeReceived) {
        this.datetimeReceived = datetimeReceived;
    }
    private java.lang.String containerId;

    public java.lang.String getcontainerId() {
        return containerId;
    }

    public void setcontainerId(java.lang.String containerId) {
        this.containerId = containerId;
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

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InventoryItem> objectList = new ArrayList<InventoryItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new InventoryItem(genVal));
        }
        return objectList;
    }
}
