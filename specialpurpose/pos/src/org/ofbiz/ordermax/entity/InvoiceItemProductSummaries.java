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

public class InvoiceItemProductSummaries implements GenericValueObjectInterface {

    public static final String module = InvoiceItemProductSummaries.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InvoiceItemProductSummaries(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public InvoiceItemProductSummaries() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"partyIdFrom", "Party Id From"},
        {"quantityTotal", "Quantity Total"},
        {"itemAmount", "Item Amount"},
        {"productId", "Product Id"},
        {"invoiceTypeId", "Invoice Type Id"},
        {"description", "Description"},
        {"invoiceItemSeqId", "Invoice Item Seq Id"},
        {"invoiceDate", "Invoice Date"},
        {"invoiceItemTypeId", "Invoice Item Type Id"},
        {"currencyUomId", "Currency Uom Id"},
        {"statusId", "Status Id"},
        {"invoiceId", "Invoice Id"},
        {"amountTotal", "Amount Total"},
        {"unitAverageCost", "Unit Average Cost"},
        {"partyId", "Party Id"},};

    protected void initObject() {
        this.partyIdFrom = "";
        this.quantityTotal = java.math.BigDecimal.ZERO;
        this.itemAmount = java.math.BigDecimal.ZERO;
        this.productId = "";
        this.invoiceTypeId = "";
        this.description = "";
        this.invoiceItemSeqId = "";
        this.invoiceDate = UtilDateTime.nowTimestamp();
        this.invoiceItemTypeId = "";
        this.currencyUomId = "";
        this.statusId = "";
        this.invoiceId = "";
        this.amountTotal = java.math.BigDecimal.ZERO;
        this.unitAverageCost = java.math.BigDecimal.ZERO;
        this.partyId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.quantityTotal = (java.math.BigDecimal) genVal.get("quantityTotal");
        this.itemAmount = (java.math.BigDecimal) genVal.get("itemAmount");
        this.productId = (java.lang.String) genVal.get("productId");
        this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
        this.description = (java.lang.String) genVal.get("description");
        this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
        this.invoiceDate = (java.sql.Timestamp) genVal.get("invoiceDate");
        this.invoiceItemTypeId = (java.lang.String) genVal.get("invoiceItemTypeId");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.invoiceId = (java.lang.String) genVal.get("invoiceId");
        this.amountTotal = (java.math.BigDecimal) genVal.get("amountTotal");
        this.unitAverageCost = (java.math.BigDecimal) genVal.get("unitAverageCost");
        this.partyId = (java.lang.String) genVal.get("partyId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("quantityTotal", OrderMaxUtility.getValidEntityBigDecimal(this.quantityTotal));
        val.set("itemAmount", OrderMaxUtility.getValidEntityBigDecimal(this.itemAmount));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("invoiceTypeId", OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("invoiceItemSeqId", OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
        val.set("invoiceDate", OrderMaxUtility.getValidEntityTimestamp(this.invoiceDate));
        val.set("invoiceItemTypeId", OrderMaxUtility.getValidEntityString(this.invoiceItemTypeId));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("invoiceId", OrderMaxUtility.getValidEntityString(this.invoiceId));
        val.set("amountTotal", OrderMaxUtility.getValidEntityBigDecimal(this.amountTotal));
        val.set("unitAverageCost", OrderMaxUtility.getValidEntityBigDecimal(this.unitAverageCost));

        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("quantityTotal", this.quantityTotal);
        valueMap.put("itemAmount", this.itemAmount);
        valueMap.put("productId", this.productId);
        valueMap.put("invoiceTypeId", this.invoiceTypeId);
        valueMap.put("description", this.description);
        valueMap.put("invoiceItemSeqId", this.invoiceItemSeqId);
        valueMap.put("invoiceDate", this.invoiceDate);
        valueMap.put("invoiceItemTypeId", this.invoiceItemTypeId);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("invoiceId", this.invoiceId);
        valueMap.put("amountTotal", this.amountTotal);
        valueMap.put("unitAverageCost", this.unitAverageCost);

        valueMap.put("partyId", this.partyId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InvoiceItemProductSummaries");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String partyIdFrom;

    public java.lang.String getPartyIdFrom() {
        return partyIdFrom;
    }

    public void setPartyIdFrom(java.lang.String partyIdFrom) {
        this.partyIdFrom = partyIdFrom;
    }
    private java.math.BigDecimal quantityTotal;

    public java.math.BigDecimal getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(java.math.BigDecimal quantityTotal) {
        this.quantityTotal = quantityTotal;
    }
    private java.math.BigDecimal itemAmount;

    public java.math.BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(java.math.BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    private java.math.BigDecimal unitAverageCost;

    public java.math.BigDecimal getUnitAverageCost() {
        return unitAverageCost;
    }

    public void setUnitAverageCost(java.math.BigDecimal unitAverageCost) {
        this.unitAverageCost = unitAverageCost;
    }
    private java.lang.String productId;

    public java.lang.String getProductId() {
        return productId;
    }

    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String invoiceTypeId;

    public java.lang.String getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(java.lang.String invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    private java.lang.String invoiceTypeDesc;

    public java.lang.String getInvoiceTypeDesc() {
        return invoiceTypeDesc;
    }

    public void setInvoiceTypeDesc(java.lang.String invoiceTypeDesc) {
        this.invoiceTypeDesc = invoiceTypeDesc;
    }

    private java.lang.String partyName;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String invoiceItemSeqId;

    public java.lang.String getInvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setInvoiceItemSeqId(java.lang.String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    private java.sql.Timestamp invoiceDate;

    public java.sql.Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(java.sql.Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    private java.lang.String invoiceItemTypeId;

    public java.lang.String getInvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setInvoiceItemTypeId(java.lang.String invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
    }
    private java.lang.String currencyUomId;

    public java.lang.String getCurrencyUomId() {
        return currencyUomId;
    }

    public void setCurrencyUomId(java.lang.String currencyUomId) {
        this.currencyUomId = currencyUomId;
    }
    private java.lang.String statusId;

    public java.lang.String getStatusId() {
        return statusId;
    }

    public void setStatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String invoiceId;

    public java.lang.String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(java.lang.String invoiceId) {
        this.invoiceId = invoiceId;
    }
    private java.math.BigDecimal amountTotal;

    public java.math.BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(java.math.BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }
    private java.lang.String partyId;

    public java.lang.String getPartyId() {
        return partyId;
    }

    public void setPartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InvoiceItemProductSummaries> objectList = new ArrayList<InvoiceItemProductSummaries>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceItemProductSummaries(genVal));
        }
        return objectList;
    }
}
