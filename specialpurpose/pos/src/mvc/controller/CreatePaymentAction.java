package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class CreatePaymentAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    final private ListAdapterListModel<PaymentComposite> paymentCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Save Payment";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private XuiSession session;

    public CreatePaymentAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<PaymentComposite> paymentCompListModel) {
        super(ActionType.CREATE_NEW_PAYMENT_ACTION, name, session, desktopPane);
        this.paymentCompListModel = paymentCompListModel;
        this.session = session;
    }

    public CreatePaymentAction(ListAdapterListModel<PaymentComposite> paymentCompListModel, XuiSession session) {
        super(ActionType.CREATE_NEW_PAYMENT_ACTION, session);
        this.paymentCompListModel = paymentCompListModel;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return name;
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    public void actionPerformed(ActionEvent e) {

        if (paymentCompListModel.getSize() > 0) {

            for (final PaymentComposite paymentComposite : paymentCompListModel.getList()) {

                try {

                    if (paymentComposite.getPayment().getpaymentId() == null || paymentComposite.getPayment().getpaymentId().isEmpty()) {
                        LoadPaymentWorker.savePayment(paymentComposite, session);
                    }

                    ActionEvent event = new ActionEvent(this, 1, "Create Payment");
                    for (ActionListener listener : listeners) {
                        listener.actionPerformed(event); // broadcast to all
                    }

                } //          });
                catch (Exception ex) {
                    Debug.logError(ex, module);
                } finally {
                }
            }
        } else {
            int selection = OrderMaxOptionPane.showConfirmDialog(
                    null, "Invoice is not generated yet. Please approve the paymentComposite", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
