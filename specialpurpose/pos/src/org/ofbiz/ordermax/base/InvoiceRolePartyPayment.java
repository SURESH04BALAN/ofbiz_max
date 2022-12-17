/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.Delegator;

/**
 *
 * @author siranjeev
 */
public class InvoiceRolePartyPayment {

    public InvoiceRolePartyPayment(Map<String, Object> dataList, Delegator delegator) {
        dataMap = dataList;

        if (dataMap.containsKey("Invoice")) {
            invoice = (GenericValue) dataMap.get("Invoice");
        }
        if (dataMap.containsKey("InvoiceRole")) {
            invoiceRole = (GenericValue) dataMap.get("InvoiceRole");
        }
        if (dataMap.containsKey("PartyTo")) {
            partyTo = (GenericValue) dataMap.get("PartyTo");
        }
        if (dataMap.containsKey("PartyFrom")) {
            partyFrom = (GenericValue) dataMap.get("partyFrom");
        }
        if (dataMap.containsKey("Payments")) {
            payments.addAll((List<GenericValue>) dataMap.get("Payments"));
        }
        if (invoice != null) {
            outstandingAmount = org.ofbiz.accounting.invoice.InvoiceWorker.getInvoiceNotApplied(delegator, invoice.getString("invoiceId"));
            paidAmount = org.ofbiz.accounting.invoice.InvoiceWorker.getInvoiceApplied(delegator, invoice.getString("invoiceId"));            
            invoiceAmount = org.ofbiz.accounting.invoice.InvoiceWorker.getInvoiceTotal(delegator, invoice.getString("invoiceId"));            
        }
    }
    
    private GenericValue invoice = null;
    private GenericValue invoiceRole = null;
    private GenericValue partyTo = null;
    private GenericValue partyFrom = null;
    private List<GenericValue> payments = new ArrayList<GenericValue>();
    private BigDecimal invoiceAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount = BigDecimal.ZERO;
    private BigDecimal outstandingAmount = BigDecimal.ZERO;
    private BigDecimal allocationAmount = BigDecimal.ZERO;
    private int rowIndex = 0;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    private boolean selected = false;
    
    public BigDecimal getAllocationAmount() {
        return allocationAmount;
    }

    public void setAllocationAmount(BigDecimal allocationAmount) {
        this.allocationAmount = allocationAmount;
    }

    private Map<String, Object> dataMap = null;

    public GenericValue getInvoice() {
        return invoice;
    }

    public void setInvoice(GenericValue invoice) {
        this.invoice = invoice;
    }

    public GenericValue getInvoiceRole() {
        return invoiceRole;
    }

    public void setInvoiceRole(GenericValue invoiceRole) {
        this.invoiceRole = invoiceRole;
    }

    public GenericValue getPartyTo() {
        return partyTo;
    }

    public void setPartyTo(GenericValue partyTo) {
        this.partyTo = partyTo;
    }

    public GenericValue getPartyFrom() {
        return partyFrom;
    }

    public void setPartyFrom(GenericValue partyFrom) {
        this.partyFrom = partyFrom;
    }

    public List<GenericValue> getPayments() {
        return payments;
    }

    public void setPayments(List<GenericValue> payments) {
        this.payments = payments;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
