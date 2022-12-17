/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.math.BigDecimal;
import org.ofbiz.base.util.Debug;
import static org.ofbiz.ordermax.composite.Order.ORDERTYPE_PURCHSEORDER;
import org.ofbiz.ordermax.entity.Invoice;

/**
 *
 * @author siranjeev
 */
public class InvoiceComposite {

    private Account partyFrom;
    private Account partyTo;
    private Invoice invoice;
    private BigDecimal allocatedAmount = BigDecimal.ZERO;    

    public BigDecimal getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(BigDecimal allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }
    
    private BigDecimal outstandingAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    private InvoiceItemCompositeList invoiceItemCompositeList = null;
    private InvoiceContactList orderContactList;
    private InvoiceStatusList invoiceStatusesList;
    private InvoiceRolesList invoiceRolesList;
    Financials financials = new Financials();

    public InvoiceStatusList getInvoiceStatusesList() {
        return invoiceStatusesList;
    }

    public void setInvoiceStatusesList(InvoiceStatusList invoiceStatusesList) {
        this.invoiceStatusesList = invoiceStatusesList;
    }

    public InvoiceRolesList getInvoiceRolesList() {
        return invoiceRolesList;
    }

    public void setInvoiceRolesList(InvoiceRolesList invoiceRolesList) {
        this.invoiceRolesList = invoiceRolesList;
    }

    public InvoiceItemCompositeList getInvoiceItemCompositeList() {
        return invoiceItemCompositeList;
    }

    public void setInvoiceItemCompositeList(InvoiceItemCompositeList invoiceItemCompositeList) {
        this.invoiceItemCompositeList = invoiceItemCompositeList;
    }

    public Account getPartyFrom() {
        return partyFrom;
    }

    public void setPartyFrom(Account partyFrom) {
        this.partyFrom = partyFrom;
    }

    public Account getPartyTo() {
        return partyTo;
    }

    public void setPartyTo(Account partyTo) {
        this.partyTo = partyTo;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceRoleComposite getBillToAccount() {
        return invoiceRolesList.getBillToParty();
    }

    public InvoiceRoleComposite getBillFromAccount() {
        return invoiceRolesList.getBillFromParty();
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    Boolean selected = new Boolean(true);
    public InvoiceContactList getInvoiceContactList() {
        return orderContactList;
    }

    public void setInvoiceContactList(InvoiceContactList orderContactList) {
        this.orderContactList = orderContactList;
    }

    public Financials getFinancials() {
        return financials;
    }

    public void setFinancials(Financials orderHeader) {
        this.financials = orderHeader;
    }
    
    public String getPartyId() {
    Debug.logInfo("invoice.getinvoiceTypeId(): " + invoice.getinvoiceTypeId()," hello");
        if ("PURCHASE_INVOICE".equals(invoice.getinvoiceTypeId())) {
            return getBillFromAccount().getInvoiceRole().getpartyId();
        } else {
            return getBillToAccount().getInvoiceRole().getpartyId();
        }
    }

    public String getPartyName() {

        if ("PURCHASE_INVOICE".equals(invoice.getinvoiceTypeId())) {
            return getBillFromAccount().getParty().getPartyGroup().getgroupName();
        } else {
            return getBillToAccount().getParty().getPartyGroup().getgroupName();
        }

    }
    
}
