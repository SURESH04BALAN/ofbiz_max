/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToggleButton;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.StatusValidChangeToDetail;
import static org.ofbiz.ordermax.orderbase.PopupButtonMenu.getStatusValidChangeToDetail;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/**
 *
 * @author BBS Auctions
 */
public class PaymentStatusActionPopupButton extends PaymentPopupButtonMenu {

    public static final String module = PaymentStatusActionPopupButton.class.getName();
    public ConfirmPaymentAction confirmPaymentAction = null;
    public CancelPaymentAction cancelPaymentAction = null;
    public ReceivePaymentAction receivePaymentAction = null;
    public SentPaymentAction sentPaymentAction = null;
    public VoidPaymentAction voidPaymentAction = null;
    public NotPaidPaymentAction notPaidPaymentAction = null;
    private final Map<String, ScreenAction> paymentStatusActions = new HashMap<String, ScreenAction>();

    public PaymentStatusActionPopupButton(JToggleButton button, PaymentActionInterface paymentActionInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, paymentActionInterface, desktopPane);
        confirmPaymentAction = new ConfirmPaymentAction(desktopPane, paymentActionInterface);
        cancelPaymentAction = new CancelPaymentAction(desktopPane, paymentActionInterface);
        receivePaymentAction = new ReceivePaymentAction(desktopPane, paymentActionInterface);
        sentPaymentAction = new SentPaymentAction(desktopPane, paymentActionInterface);
        voidPaymentAction = new VoidPaymentAction(desktopPane, paymentActionInterface);
        notPaidPaymentAction = new NotPaidPaymentAction(desktopPane, paymentActionInterface);

        paymentStatusActions.put("PMNT_RECEIVED", receivePaymentAction);
        paymentStatusActions.put("PMNT_SENT", sentPaymentAction);
        paymentStatusActions.put("PMNT_CANCELLED", cancelPaymentAction);
        paymentStatusActions.put("PMNT_VOID", voidPaymentAction);
        paymentStatusActions.put("PMNT_CONFIRMED", confirmPaymentAction);
        paymentStatusActions.put("PMNT_NOT_PAID", notPaidPaymentAction);
    }

    public String getName() {
        return "Payment Status Action";
    }

    @Override
    public void loadPopMenuAction(PaymentComposite orderReturn) {
        popupPanel.removeAll();
        String statusId = popuporderReturn.getPayment().getstatusId();
        List<StatusValidChangeToDetail> statusValidChangeToDetails = getStatusValidChangeToDetail(statusId, XuiContainer.getSession());
        for (StatusValidChangeToDetail nextStatusDetail : statusValidChangeToDetails) {
            if (paymentStatusActions.containsKey(nextStatusDetail.getstatusIdTo())) {
                if ("PMNT_SENT".equals(nextStatusDetail.getstatusIdTo())) {
                    PaymentType paymentType;
                    try {
                        paymentType = PaymentTypeSingleton.getPaymentType(orderReturn.getPayment().getpaymentTypeId());
                        if ("CUSTOMER_REFUND".equals(paymentType.getpaymentTypeId()) || "DISBURSEMENT".equals(paymentType.getparentTypeId())) {
                            JToggleButton btnViewEditDeliver = paymentStatusActions.get(nextStatusDetail.getstatusIdTo()).createActionButtonItemToggle();
                            popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PaymentStatusActionPopupButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if ("PMNT_RECEIVED".equals(nextStatusDetail.getstatusIdTo())) {
                    PaymentType paymentType;
                    try {
                        paymentType = PaymentTypeSingleton.getPaymentType(orderReturn.getPayment().getpaymentTypeId());
                        if (!"CUSTOMER_REFUND".equals(paymentType.getpaymentTypeId()) && !"DISBURSEMENT".equals(paymentType.getparentTypeId())) {
                            JToggleButton btnViewEditDeliver = paymentStatusActions.get(nextStatusDetail.getstatusIdTo()).createActionButtonItemToggle();
                            popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PaymentStatusActionPopupButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JToggleButton btnViewEditDeliver = paymentStatusActions.get(nextStatusDetail.getstatusIdTo()).createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                }
            }
        }
    }

    public void setCallBackListner(ActionListener paymentStatusChange) {
        cancelPaymentAction.addActionListener(paymentStatusChange);
        confirmPaymentAction.addActionListener(paymentStatusChange);
        receivePaymentAction.addActionListener(paymentStatusChange);
        sentPaymentAction.addActionListener(paymentStatusChange);
        voidPaymentAction.addActionListener(paymentStatusChange);
        notPaidPaymentAction.addActionListener(paymentStatusChange);
    }

}
