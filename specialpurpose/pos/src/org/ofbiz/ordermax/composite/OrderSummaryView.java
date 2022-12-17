/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

/**
 *
 * @author BBS Auctions
 */
public class OrderSummaryView {

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public enum LineItemType {

        EmptyLineItem,
        OrderLineItem,
        InvoiceLineItem,
        OrderReturnLineItem,
        PaymentLineItem
    };

    private java.sql.Timestamp date;
    private java.sql.Timestamp dueDate;
    private String type;
    private String number = null;
    private String reference;
    private java.math.BigDecimal amount;
    private String details;
    private String trxId;
    
    private LineItemType lineItemType = LineItemType.EmptyLineItem;

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getReturnTypeId() {
        return returnTypeId;
    }

    public void setReturnTypeId(String returnTypeId) {
        this.returnTypeId = returnTypeId;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(String invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }
    private String orderTypeId;    
    private String returnTypeId;     
    private String paymentTypeId;     
    private String invoiceTypeId;     
  
    public LineItemType getLineItemType() {
        return lineItemType;
    }

    public void setLineItemType(LineItemType lineItemType) {
        this.lineItemType = lineItemType;
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    public java.sql.Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(java.sql.Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public java.math.BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    String partyId = null;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
