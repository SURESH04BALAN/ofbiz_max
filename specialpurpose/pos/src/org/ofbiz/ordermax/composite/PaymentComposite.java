/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.math.BigDecimal;
import java.util.List;
import mvc.controller.LoadPaymentWorker;
import org.ofbiz.accounting.payment.PaymentWorker;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.Payment;
import org.ofbiz.ordermax.entity.PaymentApplication;

/**
 *
 * @author siranjeev
 */
public class PaymentComposite {

    /*public Account getPartyPaymentFrom() {
        return partyPaymentFrom;
    }

    public void setPartyPaymentFrom(Account partyPaymentFrom) {
        this.partyPaymentFrom = partyPaymentFrom;
    }

    public Account getPartyPaymentTo() {
        return partyPaymentTo;
    }

    public void setPartyPaymentTo(Account partyPaymentTo) {
        this.partyPaymentTo = partyPaymentTo;
    }

    private Account partyPaymentFrom;
    private Account partyPaymentTo;*/
    private Payment payment;
    private BigDecimal appliedAmount;
    private BigDecimal outstandingAmount;
    private BigDecimal uncapturedAmount;

    public BigDecimal getUncapturedAmount() {
        return uncapturedAmount;
    }

    public void setUncapturedAmount(BigDecimal uncapturedAmount) {
        this.uncapturedAmount = uncapturedAmount;
    }
    private PaymentApplicationCompositeList paymentApplicationCompositeList = null;
    private OrderPaymentPreference orderPaymentPreference = null;
    private BillingAccount billingAccount = null;

    public BillingAccount getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
    }
    public OrderPaymentPreference getOrderPaymentPreference() {
        return orderPaymentPreference;
    }

    public void setOrderPaymentPreferences(OrderPaymentPreference orderPaymentPreference) {
        this.orderPaymentPreference = orderPaymentPreference;
    }
    
    public PaymentApplicationCompositeList getPaymentApplicationCompositeList() {
        return paymentApplicationCompositeList;
    }

    public void setPaymentApplicationCompositeList(PaymentApplicationCompositeList paymentApplicationCompositeList) {
        this.paymentApplicationCompositeList = paymentApplicationCompositeList;
    }

    public BigDecimal getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(BigDecimal appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
