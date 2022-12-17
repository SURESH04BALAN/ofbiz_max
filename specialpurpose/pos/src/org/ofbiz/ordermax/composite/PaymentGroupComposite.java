/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.PaymentGroupComposite.PaymentGroupMemberComposite;
import org.ofbiz.ordermax.entity.PaymentGroup;
import org.ofbiz.ordermax.entity.PaymentGroupMember;

/**
 *
 * @author BBS Auctions
 */
public class PaymentGroupComposite extends ListAdapterListModel<PaymentGroupMemberComposite> {

    PaymentGroup paymentGroup = null;

    public PaymentGroup getPaymentGroup() {
        return paymentGroup;
    }

    public void setPaymentGroup(PaymentGroup paymentGroup) {
        this.paymentGroup = paymentGroup;
    }
    //List<PaymentGroupMemberComposite> paymentGroupMembers = new ArrayList<PaymentGroupMemberComposite>();

    public void addPayment(PaymentComposite payment) {
//        this.add(newPaymentGroupMemberComposite(payment));
        if (UtilValidate.isEmpty(payment.getPayment().getpaymentId())) {
            this.add(newPaymentGroupMemberComposite(payment));
        } else {
            PaymentComposite val = getPayment(payment.getPayment().getpaymentId());
            if (val == null) {
                this.add(newPaymentGroupMemberComposite(payment));
            }
        }        
    }

    public void addPayment(PaymentComposite payment, PaymentGroupMember member) {

        if (UtilValidate.isEmpty(payment.getPayment().getpaymentId())) {
            this.add(newPaymentGroupMemberComposite(payment, member));
        } else {
            PaymentComposite val = getPayment(payment.getPayment().getpaymentId());
            if (val == null) {
                this.add(newPaymentGroupMemberComposite(payment, member));
            }
        }
    }

    public PaymentGroupMemberComposite newPaymentGroupMemberComposite(PaymentComposite payment) {
        PaymentGroupMemberComposite comp = new PaymentGroupMemberComposite();
        comp.paymentComposite = payment;
        comp.paymentGroupMember = new PaymentGroupMember();
        comp.paymentGroupMember.setPaymentGroupId(paymentGroup.getPaymentGroupId());
        return comp;
    }

    public PaymentGroupMemberComposite newPaymentGroupMemberComposite(PaymentComposite payment, PaymentGroupMember member) {
        PaymentGroupMemberComposite comp = new PaymentGroupMemberComposite();
        comp.paymentComposite = payment;
        comp.paymentGroupMember = member;
        return comp;
    }

    public PaymentComposite getPayment(String paymentId) {
        PaymentComposite paymentComposite = null;
        for (PaymentGroupMemberComposite paymentMembers : this.list) {
            if (paymentMembers.paymentGroupMember.getPaymentId().equals(paymentId)) {
                return paymentMembers.paymentComposite;
            }
        }
        return paymentComposite;
    }
    public BigDecimal getTotalPayment() {
        BigDecimal result = BigDecimal.ZERO;
        for (PaymentGroupMemberComposite paymentMembers : this.list) {
            result = result.add(paymentMembers.paymentComposite.getPayment().getamount());
     
        }
        return result;
    }
    public PaymentComposite getPaymentByPaymentMethodType(String paymentMethodTypeId) {
        PaymentComposite paymentComposite = null;
        for (PaymentGroupMemberComposite paymentMembers : this.list) {
            if (paymentMembers.paymentComposite.getPayment().getpaymentMethodTypeId().equals(paymentMethodTypeId)) {
                return paymentMembers.paymentComposite;
            }
        }
        return paymentComposite;
    }

    public PaymentComposite getFirstPaymentComposite() {
//        PaymentGroupMemberComposite first = this.getElementAt(0);
        if (this.getSize() > 0) {
            return this.getElementAt(0).paymentComposite;
        }
        return null;
    }

    static public class PaymentGroupMemberComposite {

        public PaymentComposite paymentComposite;
        public PaymentGroupMember paymentGroupMember;
    }

}
