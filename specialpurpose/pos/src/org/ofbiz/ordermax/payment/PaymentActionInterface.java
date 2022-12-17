/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.payment;

import org.ofbiz.ordermax.composite.PaymentComposite;

/**
 *
 * @author siranjeev
 */
public interface PaymentActionInterface {
    public PaymentComposite getPayment();
}
