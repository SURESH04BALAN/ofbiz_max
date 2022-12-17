/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.List;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.BillingAccountTerm;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;

/**
 *
 * @author BBS Auctions
 */
public class BillingAccountComposite {

    private BillingAccount billingAccount;
    private List<BillingAccountRole> billingAccountRoleList;
    private List<BillingAccountTerm> billingAccountTermList;
    private ListAdapterListModel<InvoiceComposite> billingAccountInvoices;
    private ListAdapterListModel<PaymentComposite> billingAccountPayments;
    private ListAdapterListModel<OrderHeaderAndRoleSummary> billingAccountOrders;
    //List<BillingAccountTermAttr> billingAccountTermListAttr; 
    private String primaryPartyId = null;

    public String getPrimaryPartyId() {
        return primaryPartyId;
    }

    public void setPrimaryPartyId(String primaryPartyId) {
        this.primaryPartyId = primaryPartyId;
    }
    
    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }

    public List<BillingAccountRole> getBillingAccountRoleList() {
        return billingAccountRoleList;
    }

    public void setBillingAccountRoleList(List<BillingAccountRole> billingAccountRoleList) {
        this.billingAccountRoleList = billingAccountRoleList;
    }

    public List<BillingAccountTerm> getBillingAccountTermList() {
        return billingAccountTermList;
    }

    public void setBillingAccountTermList(List<BillingAccountTerm> billingAccountTermList) {
        this.billingAccountTermList = billingAccountTermList;
    }

    public ListAdapterListModel<InvoiceComposite> getBillingAccountInvoices() {
        return billingAccountInvoices;
    }

    public void setBillingAccountInvoices(ListAdapterListModel<InvoiceComposite> billingAccountInvoices) {
        this.billingAccountInvoices = billingAccountInvoices;
    }

    public ListAdapterListModel<PaymentComposite> getBillingAccountPayments() {
        return billingAccountPayments;
    }

    public void setBillingAccountPayments(ListAdapterListModel<PaymentComposite> billingAccountPayments) {
        this.billingAccountPayments = billingAccountPayments;
    }

    public ListAdapterListModel<OrderHeaderAndRoleSummary> getBillingAccountOrders() {
        return billingAccountOrders;
    }

    public void setBillingAccountOrders(ListAdapterListModel<OrderHeaderAndRoleSummary> billingAccountOrders) {
        this.billingAccountOrders = billingAccountOrders;
    }
}
