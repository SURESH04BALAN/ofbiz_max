/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentmethod;

import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.PaymentMethod;
import org.ofbiz.ordermax.entity.PaymentMethodType;

/**
 *
 * @author BBS Auctions
 */
public interface PaymentMethodInterface {

    String getReference();

    String getComment();

    void setReference(String val);

    void setComment(String val);

    void setPartyId(String partyId);

    void setPaymentMethod(PaymentMethod payMethod);
    void setPaymentMethodDetails(GenericValue payMethod);

    PaymentMethod getPaymentMethod();

    void setPaymentMethodType(PaymentMethodType payMethod);
    void setPaymentMethodDetails(Map<String, GenericValue> mapValues);
    PaymentMethodType getPaymentMethodType();
    JPanel getPanel();

}
