/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import org.ofbiz.ordermax.orderbase.returns.*;
import org.ofbiz.ordermax.orderbase.*;
import javax.swing.JToggleButton;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.PaymentComposite;

/**
 *
 * @author BBS Auctions
 */
public abstract class PaymentPopupButtonMenu extends PopupButtonMenu {

    public PaymentComposite popuporderReturn;
    PaymentActionInterface paymentActionInterface;

    public PaymentPopupButtonMenu(JToggleButton button, PaymentActionInterface paymentActionInterface, javax.swing.JDesktopPane desktopPane) {
        this.popupButton = button;
        this.paymentActionInterface = paymentActionInterface;
        this.desktopPane = desktopPane;
//        this.parentFrame = parentFrame;
    }


    public abstract void loadPopMenuAction(final PaymentComposite orderReturn);


    final public void loadPopMenu(final PaymentComposite orderReturn) {
        this.popuporderReturn = orderReturn;
        popupPanel.removeAll();
        decoratePopupMenuPanel(popupPanel);
        if (popuporderReturn != null
                && UtilValidate.isNotEmpty(popuporderReturn.getPayment()/*.getpaymentId()*/)) {

//            popupPanel.add(newOrderButton/*, BorderLayout.LINE_START*/);
            loadPopMenuAction(orderReturn);
            createPopupMenu(popupPanel);
            
        }
    }
}
