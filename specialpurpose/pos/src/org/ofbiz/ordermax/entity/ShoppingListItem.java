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

public class ShoppingListItem implements GenericValueObjectInterface {

    public static final String module = ShoppingListItem.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ShoppingListItem(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ShoppingListItem() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"modifiedPrice", "Modified Price"},
        {"shoppingListItemSeqId", "Shopping List Item Seq Id"},
        {"reservPersons", "Reserv Persons"},
        {"configId", "Config Id"},
        {"productId", "Product Id"},
        {"reservLength", "Reserv Length"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"quantityPurchased", "Quantity Purchased"},
        {"reservStart", "Reserv Start"},
        {"shoppingListId", "Shopping List Id"},
        {"quantity", "Quantity"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.modifiedPrice = null;
        this.shoppingListItemSeqId = "";
        this.reservPersons = null;
        this.configId = "";
        this.productId = "";
        this.reservLength = null;
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.quantityPurchased = java.math.BigDecimal.ZERO;
        this.reservStart = null;
        this.shoppingListId = "";
        this.quantity = java.math.BigDecimal.ZERO;
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.modifiedPrice = (java.math.BigDecimal) genVal.get("modifiedPrice");
        this.shoppingListItemSeqId = (java.lang.String) genVal.get("shoppingListItemSeqId");
        this.reservPersons = (java.math.BigDecimal) genVal.get("reservPersons");
        this.configId = (java.lang.String) genVal.get("configId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.reservLength = (java.math.BigDecimal) genVal.get("reservLength");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.quantityPurchased = (java.math.BigDecimal) genVal.get("quantityPurchased");
        this.reservStart = (java.sql.Timestamp) genVal.get("reservStart");
        this.shoppingListId = (java.lang.String) genVal.get("shoppingListId");
        this.quantity = (java.math.BigDecimal) genVal.get("quantity");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("modifiedPrice", OrderMaxUtility.getValidBigDecimal(this.modifiedPrice));
        val.set("shoppingListItemSeqId", OrderMaxUtility.getValidEntityString(this.shoppingListItemSeqId));
        val.set("reservPersons", OrderMaxUtility.getValidBigDecimal(this.reservPersons));
        val.set("configId", OrderMaxUtility.getValidEntityString(this.configId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("reservLength", OrderMaxUtility.getValidBigDecimal(this.reservLength));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("quantityPurchased", OrderMaxUtility.getValidBigDecimal(this.quantityPurchased));
        val.set("reservStart", OrderMaxUtility.getValidEntityTimestamp(this.reservStart));
        val.set("shoppingListId", OrderMaxUtility.getValidEntityString(this.shoppingListId));
        val.set("quantity", OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("modifiedPrice", this.modifiedPrice);
        valueMap.put("shoppingListItemSeqId", this.shoppingListItemSeqId);
        valueMap.put("reservPersons", this.reservPersons);
        valueMap.put("configId", this.configId);
        valueMap.put("productId", this.productId);
        valueMap.put("reservLength", this.reservLength);
        valueMap.put("quantityPurchased", this.quantityPurchased);
        valueMap.put("reservStart", this.reservStart);
        valueMap.put("shoppingListId", this.shoppingListId);
        valueMap.put("quantity", this.quantity);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ShoppingListItem");
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
    private java.math.BigDecimal modifiedPrice;

    public java.math.BigDecimal getmodifiedPrice() {
        return modifiedPrice;
    }

    public void setmodifiedPrice(java.math.BigDecimal modifiedPrice) {
        this.modifiedPrice = modifiedPrice;
    }
    private java.lang.String shoppingListItemSeqId;

    public java.lang.String getshoppingListItemSeqId() {
        return shoppingListItemSeqId;
    }

    public void setshoppingListItemSeqId(java.lang.String shoppingListItemSeqId) {
        this.shoppingListItemSeqId = shoppingListItemSeqId;
    }
    private java.math.BigDecimal reservPersons;

    public java.math.BigDecimal getreservPersons() {
        return reservPersons;
    }

    public void setreservPersons(java.math.BigDecimal reservPersons) {
        this.reservPersons = reservPersons;
    }
    private java.lang.String configId;

    public java.lang.String getconfigId() {
        return configId;
    }

    public void setconfigId(java.lang.String configId) {
        this.configId = configId;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.math.BigDecimal reservLength;

    public java.math.BigDecimal getreservLength() {
        return reservLength;
    }

    public void setreservLength(java.math.BigDecimal reservLength) {
        this.reservLength = reservLength;
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
    private java.math.BigDecimal quantityPurchased;

    public java.math.BigDecimal getquantityPurchased() {
        return quantityPurchased;
    }

    public void setquantityPurchased(java.math.BigDecimal quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }
    private java.sql.Timestamp reservStart;

    public java.sql.Timestamp getreservStart() {
        return reservStart;
    }

    public void setreservStart(java.sql.Timestamp reservStart) {
        this.reservStart = reservStart;
    }
    private java.lang.String shoppingListId;

    public java.lang.String getshoppingListId() {
        return shoppingListId;
    }

    public void setshoppingListId(java.lang.String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getquantity() {
        return quantity;
    }

    public void setquantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
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
        Collection<ShoppingListItem> objectList = new ArrayList<ShoppingListItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShoppingListItem(genVal));
        }
        return objectList;
    }
}
