/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.entity;

import java.math.BigDecimal;

/**
 *
 * @author siranjeev
 */
public class SubReportBean {

    private String city;
    private String street;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    private java.lang.String invoiceId;

    public java.lang.String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(java.lang.String invoiceId) {
        this.invoiceId = invoiceId;
    }
    private java.lang.String invoiceItemSeqId;

    public java.lang.String getinvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setinvoiceItemSeqId(java.lang.String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    private java.lang.String invoiceItemTypeId;

    public java.lang.String getinvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setinvoiceItemTypeId(java.lang.String invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getoverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setoverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String overrideOrgPartyId;

    public java.lang.String getoverrideOrgPartyId() {
        return overrideOrgPartyId;
    }

    public void setoverrideOrgPartyId(java.lang.String overrideOrgPartyId) {
        this.overrideOrgPartyId = overrideOrgPartyId;
    }
    private java.lang.String inventoryItemId;

    public java.lang.String getinventoryItemId() {
        return inventoryItemId;
    }

    public void setinventoryItemId(java.lang.String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }
    private java.lang.String productId;

    public java.lang.String getProductId() {
        return productId;
    }

    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getproductFeatureId() {
        return productFeatureId;
    }

    public void setproductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.lang.String parentInvoiceId;

    public java.lang.String getparentInvoiceId() {
        return parentInvoiceId;
    }

    public void setparentInvoiceId(java.lang.String parentInvoiceId) {
        this.parentInvoiceId = parentInvoiceId;
    }
    private java.lang.String parentInvoiceItemSeqId;

    public java.lang.String getparentInvoiceItemSeqId() {
        return parentInvoiceItemSeqId;
    }

    public void setparentInvoiceItemSeqId(java.lang.String parentInvoiceItemSeqId) {
        this.parentInvoiceItemSeqId = parentInvoiceItemSeqId;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String taxableFlag;

    public java.lang.String gettaxableFlag() {
        return taxableFlag;
    }

    public void settaxableFlag(java.lang.String taxableFlag) {
        this.taxableFlag = taxableFlag;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }
    private java.math.BigDecimal amount;

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    private java.lang.String description;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String taxAuthPartyId;

    public java.lang.String gettaxAuthPartyId() {
        return taxAuthPartyId;
    }

    public void settaxAuthPartyId(java.lang.String taxAuthPartyId) {
        this.taxAuthPartyId = taxAuthPartyId;
    }
    private java.lang.String taxAuthGeoId;

    public java.lang.String gettaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void settaxAuthGeoId(java.lang.String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }
    private java.lang.String taxAuthorityRateSeqId;

    public java.lang.String gettaxAuthorityRateSeqId() {
        return taxAuthorityRateSeqId;
    }

    public void settaxAuthorityRateSeqId(java.lang.String taxAuthorityRateSeqId) {
        this.taxAuthorityRateSeqId = taxAuthorityRateSeqId;
    }
    private java.lang.String salesOpportunityId;

    public java.lang.String getsalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setsalesOpportunityId(java.lang.String salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private BigDecimal itemAmount;

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getItemProfit() {
        return itemProfit;
    }

    public void setItemProfit(BigDecimal itemProfit) {
        this.itemProfit = itemProfit;
    }

    public BigDecimal getTotalItemProfit() {
        return totalItemProfit;
    }

    public void setTotalItemProfit(BigDecimal totalItemProfit) {
        this.totalItemProfit = totalItemProfit;
    }

    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;

    private BigDecimal itemProfit = BigDecimal.ZERO;
    private BigDecimal totalItemProfit = BigDecimal.ZERO;
    private BigDecimal itemTotalGst = BigDecimal.ZERO;
    private BigDecimal itemGSTAmount = BigDecimal.ZERO;

    public BigDecimal getItemGSTAmount() {
        return itemGSTAmount;
    }

    public void setItemGSTAmount(BigDecimal itemGSTAmount) {
        this.itemGSTAmount = itemGSTAmount;
    }
    public BigDecimal getItemTotalGst() {
        return itemTotalGst;
    }

    public void setItemTotalGst(BigDecimal itemTotalGst) {
        this.itemTotalGst = itemTotalGst;
    }

    public BigDecimal getTotalItemExGst() {
        return totalItemExGst;
    }

    public void setTotalItemExGst(BigDecimal totalItemExGst) {
        this.totalItemExGst = totalItemExGst;
    }
    private BigDecimal totalItemExGst = BigDecimal.ZERO;
    
    private String bin = "";

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void calculateProfit() {
        if (getCost() != null && getQuantity() != null && description != null && description.contains("GROCERY") == false) {
            setTotalCost(getQuantity().multiply(getCost()));
            setItemProfit(getAmount().subtract(getCost()));
            setTotalItemProfit(getItemProfit().multiply(getQuantity()));
        }
    }
}
