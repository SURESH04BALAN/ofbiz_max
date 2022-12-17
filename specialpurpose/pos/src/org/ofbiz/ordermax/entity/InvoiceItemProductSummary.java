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

public class InvoiceItemProductSummary implements GenericValueObjectInterface {

    public static final String module = InvoiceItemProductSummary.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InvoiceItemProductSummary(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
//Debug.logInfo(ex, module);
        }
    }

    public InvoiceItemProductSummary() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"partyIdFrom", "Party Id From"},
        {"quantityTotal", "Quantity Total"},
        {"currencyUomId", "Currency Uom Id"},
        {"statusId", "Status Id"},
        {"productId", "Product Id"},
        {"invoiceTypeId", "Invoice Type Id"},
        {"amountTotal", "Amount Total"},
        {"invoiceDate", "Invoice Date"},
        {"partyId", "Party Id"},
        {"invoiceItemTypeId", "Invoice Item Type Id"},};

    protected void initObject() {
        this.partyIdFrom = "";
        this.quantityTotal = java.math.BigDecimal.ZERO;
        this.currencyUomId = "";
        this.statusId = "";
        this.productId = "";
        this.invoiceTypeId = "";
        this.amountTotal = java.math.BigDecimal.ZERO;
        this.invoiceDate = UtilDateTime.nowTimestamp();
        this.partyId = "";
        this.invoiceItemTypeId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.partyIdFrom = (java.lang.String) genVal.get("partyIdFrom");
        this.quantityTotal = (java.math.BigDecimal) genVal.get("quantityTotal");
        this.currencyUomId = (java.lang.String) genVal.get("currencyUomId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
        this.amountTotal = (java.math.BigDecimal) genVal.get("amountTotal");
        this.invoiceDate = (java.sql.Timestamp) genVal.get("invoiceDate");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.invoiceItemTypeId = (java.lang.String) genVal.get("invoiceItemTypeId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("partyIdFrom", OrderMaxUtility.getValidEntityString(this.partyIdFrom));
        val.set("quantityTotal", OrderMaxUtility.getValidEntityBigDecimal(this.quantityTotal));
        val.set("currencyUomId", OrderMaxUtility.getValidEntityString(this.currencyUomId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("invoiceTypeId", OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
        val.set("amountTotal", OrderMaxUtility.getValidEntityBigDecimal(this.amountTotal));
        val.set("invoiceDate", OrderMaxUtility.getValidEntityTimestamp(this.invoiceDate));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("invoiceItemTypeId", OrderMaxUtility.getValidEntityString(this.invoiceItemTypeId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("partyIdFrom", this.partyIdFrom);
        valueMap.put("quantityTotal", this.quantityTotal);
        valueMap.put("currencyUomId", this.currencyUomId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("productId", this.productId);
        valueMap.put("invoiceTypeId", this.invoiceTypeId);
        valueMap.put("amountTotal", this.amountTotal);
        valueMap.put("invoiceDate", this.invoiceDate);
        valueMap.put("partyId", this.partyId);
        valueMap.put("invoiceItemTypeId", this.invoiceItemTypeId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InvoiceItemProductSummary");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    
    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
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
    private java.math.BigDecimal amountTotal;

    public java.math.BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(java.math.BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }
    private java.sql.Timestamp invoiceDate;

    public java.sql.Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(java.sql.Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    private java.lang.String partyId;

    public java.lang.String getPartyId() {
        return partyId;
    }

    public void setPartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String invoiceItemTypeId;

    public java.lang.String getInvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setInvoiceItemTypeId(java.lang.String invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InvoiceItemProductSummary> objectList = new ArrayList<InvoiceItemProductSummary>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceItemProductSummary(genVal));
        }
        return objectList;
    }
}
