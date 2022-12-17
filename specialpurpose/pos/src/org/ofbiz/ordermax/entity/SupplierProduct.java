package org.ofbiz.ordermax.entity;

import java.math.BigDecimal;
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
import org.ofbiz.base.util.Debug;

public class SupplierProduct implements GenericValueObjectInterface {

    public static final String module = SupplierProduct.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public SupplierProduct(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public SupplierProduct() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"quantityUomId", "Quantity Uom Id"},
        {"supplierRatingTypeId", "Supplier Rating Type Id"},
        {"unitsIncluded", "Units Included"},
        {"supplierPrefOrderId", "Supplier Pref Order Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"shippingPrice", "Shipping Price"},
        {"supplierProductName", "Supplier Product Name"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"canDropShip", "Can Drop Ship"},
        {"currencyUomId", "Currency Uom Id"},
        {"minimumOrderQuantity", "Minimum Order Quantity"},
        {"agreementId", "Agreement Id"},
        {"agreementItemSeqId", "Agreement Item Seq Id"},
        {"lastPrice", "Last Price"},
        {"orderQtyIncrements", "Order Qty Increments"},
        {"productId", "Product Id"},
        {"availableFromDate", "Available From Date"},
        {"partyId", "Party Id"},
        {"standardLeadTimeDays", "Standard Lead Time Days"},
        {"availableThruDate", "Available Thru Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"comments", "Comments"},
        {"supplierProductId", "Supplier Product Id"},};

    protected void initObject() {
        this.quantityUomId = "";
        this.supplierRatingTypeId = "";
        this.unitsIncluded = null;
        this.supplierPrefOrderId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.shippingPrice = null;
        this.supplierProductName = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.canDropShip = "";
        this.currencyUomId = "";
        this.minimumOrderQuantity = java.math.BigDecimal.ZERO;
        this.agreementId = "";
        this.agreementItemSeqId = "";
        this.lastPrice = java.math.BigDecimal.ZERO;
        this.orderQtyIncrements = java.math.BigDecimal.ZERO;
        this.productId = "";
        this.availableFromDate = UtilDateTime.nowTimestamp();
        this.partyId = "";
        this.standardLeadTimeDays = null;
        this.availableThruDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.comments = "";
        this.supplierProductId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.quantityUomId = (java.lang.String) genVal.get("quantityUomId");
        this.supplierRatingTypeId = (java.lang.String) genVal.get("supplierRatingTypeId");
        this.unitsIncluded = (BigDecimal) genVal.get("unitsIncluded");
        this.supplierPrefOrderId = (java.lang.String) genVal.get("supplierPrefOrderId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.shippingPrice = (java.math.BigDecimal) genVal.get("shippingPrice");
        this.supplierProductName = (java.lang.String) genVal.get("supplierProductName");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.canDropShip = (java.lang.String) genVal.get("canDropShip");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.minimumOrderQuantity = (java.math.BigDecimal) genVal.get("minimumOrderQuantity");
        this.agreementId = (java.lang.String) genVal.get("agreementId");
        this.agreementItemSeqId = (java.lang.String) genVal.get("agreementItemSeqId");
        this.lastPrice = (java.math.BigDecimal) genVal.get("lastPrice");
        this.orderQtyIncrements = (java.math.BigDecimal) genVal.get("orderQtyIncrements");
        this.productId = (java.lang.String) genVal.get("productId");
        this.availableFromDate = (java.sql.Timestamp) genVal.get("availableFromDate");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.standardLeadTimeDays = (java.math.BigDecimal) genVal.get("standardLeadTimeDays");
        this.availableThruDate = (java.sql.Timestamp) genVal.get("availableThruDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.comments = (java.lang.String) genVal.get("comments");
        this.supplierProductId = (java.lang.String) genVal.get("supplierProductId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("quantityUomId", OrderMaxUtility.getValidEntityString(this.quantityUomId));
        val.set("supplierRatingTypeId", OrderMaxUtility.getValidEntityString(this.supplierRatingTypeId));
        val.set("unitsIncluded", OrderMaxUtility.getValidEntityBigDecimal(this.unitsIncluded));
        val.set("supplierPrefOrderId", OrderMaxUtility.getValidEntityString(this.supplierPrefOrderId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("shippingPrice", OrderMaxUtility.getValidEntityBigDecimal(this.shippingPrice));
        val.set("supplierProductName", OrderMaxUtility.getValidEntityString(this.supplierProductName));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("canDropShip", OrderMaxUtility.getValidEntityString(this.canDropShip));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("minimumOrderQuantity", OrderMaxUtility.getValidEntityBigDecimal(this.minimumOrderQuantity));
        val.set("agreementId", OrderMaxUtility.getValidEntityString(this.agreementId));
        val.set("agreementItemSeqId", OrderMaxUtility.getValidEntityString(this.agreementItemSeqId));
        val.set("lastPrice", OrderMaxUtility.getValidEntityBigDecimal(this.lastPrice));
        val.set("orderQtyIncrements", OrderMaxUtility.getValidEntityBigDecimal(this.orderQtyIncrements));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("availableFromDate", OrderMaxUtility.getValidEntityTimestamp(this.availableFromDate));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("standardLeadTimeDays", OrderMaxUtility.getValidEntityBigDecimal(this.standardLeadTimeDays));
        val.set("availableThruDate", OrderMaxUtility.getValidEntityTimestamp(this.availableThruDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("comments", OrderMaxUtility.getValidEntityString(this.comments));
        val.set("supplierProductId", OrderMaxUtility.getValidEntityString(this.supplierProductId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("quantityUomId", this.quantityUomId);
        valueMap.put("supplierRatingTypeId", this.supplierRatingTypeId);

        valueMap.put("unitsIncluded", this.unitsIncluded==null ? BigDecimal.ZERO : unitsIncluded);
        valueMap.put("supplierPrefOrderId", this.supplierPrefOrderId);
        valueMap.put("shippingPrice", this.shippingPrice==null ? BigDecimal.ZERO : shippingPrice);
        valueMap.put("supplierProductName", this.supplierProductName);
        valueMap.put("canDropShip", this.canDropShip);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("minimumOrderQuantity", this.minimumOrderQuantity);
        valueMap.put("agreementId", this.agreementId);
        valueMap.put("agreementItemSeqId", this.agreementItemSeqId);
        valueMap.put("lastPrice", this.lastPrice);
        valueMap.put("orderQtyIncrements", this.orderQtyIncrements);
        valueMap.put("productId", this.productId);
        valueMap.put("availableFromDate", this.availableFromDate);
        valueMap.put("partyId", this.partyId);
        valueMap.put("standardLeadTimeDays", this.standardLeadTimeDays==null ? BigDecimal.ZERO: standardLeadTimeDays);
        valueMap.put("availableThruDate", this.availableThruDate);
        valueMap.put("comments", this.comments);
        valueMap.put("supplierProductId", this.supplierProductId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("SupplierProduct");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String quantityUomId;

    public java.lang.String getquantityUomId() {
        return quantityUomId;
    }

    public void setquantityUomId(java.lang.String quantityUomId) {
        this.quantityUomId = quantityUomId;
    }
    private java.lang.String supplierRatingTypeId;

    public java.lang.String getsupplierRatingTypeId() {
        return supplierRatingTypeId;
    }

    public void setsupplierRatingTypeId(java.lang.String supplierRatingTypeId) {
        this.supplierRatingTypeId = supplierRatingTypeId;
    }
    private java.math.BigDecimal unitsIncluded;

    public java.math.BigDecimal getunitsIncluded() {
        return unitsIncluded;
    }

    public void setunitsIncluded(java.math.BigDecimal unitsIncluded) {
        this.unitsIncluded = unitsIncluded;
    }
    private java.lang.String supplierPrefOrderId;

    public java.lang.String getsupplierPrefOrderId() {
        return supplierPrefOrderId;
    }

    public void setsupplierPrefOrderId(java.lang.String supplierPrefOrderId) {
        this.supplierPrefOrderId = supplierPrefOrderId;
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
    private java.math.BigDecimal shippingPrice;

    public java.math.BigDecimal getshippingPrice() {
        return shippingPrice;
    }

    public void setshippingPrice(java.math.BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
    private java.lang.String supplierProductName;

    public java.lang.String getsupplierProductName() {
        return supplierProductName;
    }

    public void setsupplierProductName(java.lang.String supplierProductName) {
        this.supplierProductName = supplierProductName;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String canDropShip;

    public java.lang.String getcanDropShip() {
        return canDropShip;
    }

    public void setcanDropShip(java.lang.String canDropShip) {
        this.canDropShip = canDropShip;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getcurrencyUomId() {
        return currencyUomId;
    }

    public void setcurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.math.BigDecimal minimumOrderQuantity;

    public java.math.BigDecimal getminimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setminimumOrderQuantity(java.math.BigDecimal minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }
    private java.lang.String agreementId;

    public java.lang.String getagreementId() {
        return agreementId;
    }

    public void setagreementId(java.lang.String agreementId) {
        this.agreementId = agreementId;
    }
    private java.lang.String agreementItemSeqId;

    public java.lang.String getagreementItemSeqId() {
        return agreementItemSeqId;
    }

    public void setagreementItemSeqId(java.lang.String agreementItemSeqId) {
        this.agreementItemSeqId = agreementItemSeqId;
    }
    private java.math.BigDecimal lastPrice;

    public java.math.BigDecimal getlastPrice() {
        return lastPrice;
    }

    public void setlastPrice(java.math.BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }
    private java.math.BigDecimal orderQtyIncrements;

    public java.math.BigDecimal getorderQtyIncrements() {
        return orderQtyIncrements;
    }

    public void setorderQtyIncrements(java.math.BigDecimal orderQtyIncrements) {
        this.orderQtyIncrements = orderQtyIncrements;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.sql.Timestamp availableFromDate;

    public java.sql.Timestamp getavailableFromDate() {
        return availableFromDate;
    }

    public void setavailableFromDate(java.sql.Timestamp availableFromDate) {
        this.availableFromDate = availableFromDate;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
        try{
            throw new Exception("setpartyId : " + partyId);
        }
        catch(Exception e){
            //Debug.logError(e,"module");
        }
    }
    private java.math.BigDecimal standardLeadTimeDays;

    public java.math.BigDecimal getstandardLeadTimeDays() {
        return standardLeadTimeDays;
    }

    public void setstandardLeadTimeDays(java.math.BigDecimal standardLeadTimeDays) {
        this.standardLeadTimeDays = standardLeadTimeDays;
    }
    private java.sql.Timestamp availableThruDate;

    public java.sql.Timestamp getavailableThruDate() {
        return availableThruDate;
    }

    public void setavailableThruDate(java.sql.Timestamp availableThruDate) {
        this.availableThruDate = availableThruDate;
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
    private java.lang.String supplierProductId;

    public java.lang.String getsupplierProductId() {
        return supplierProductId;
    }

    public void setsupplierProductId(java.lang.String supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SupplierProduct> objectList = new ArrayList<SupplierProduct>();
        for (GenericValue genVal : genList) {
            objectList.add(new SupplierProduct(genVal));
        }
        return objectList;
    }
}
