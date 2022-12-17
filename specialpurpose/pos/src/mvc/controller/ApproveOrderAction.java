package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderChangeHelper;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;

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
public class ApproveOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "APPROVE ORDER";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public ApproveOrderAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION,
                ApproveOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public ApproveOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public ApproveOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION, session);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "APPROVE ORDER";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {
            final Order order = orderActionInterface.getOrder();
            if (approveOrder(order.getOrderId())) {
                ActionEvent event = new ActionEvent(this, 1, order.getOrderId());
                for (ActionListener listener : listeners) {
                    Debug.logInfo("editCell Action listner", module);
                    listener.actionPerformed(event); // broadcast to all
                }
            } else {
                int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to APPROVE ORDER: " + order.getOrderId(), "Approve Order",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            }
        } else {
            for (final Order order : orderCompListModel.getList()) {

                if (approveOrder(order.getOrderId())) {
                    ActionEvent event = new ActionEvent(this, 1, order.getOrderId());
                    for (ActionListener listener : listeners) {
                        Debug.logInfo("editCell Action listner", module);
                        listener.actionPerformed(event); // broadcast to all
                    }
                } else {
                    int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to APPROVE ORDER: " + order.getOrderId(), "Approve Order",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                }
            }
        }
    }

    public boolean approveOrder(String orderId) {

        boolean result = OrderChangeHelper.approveOrder(
                session.getDispatcher(),
                session.getUserLogin(),
                orderId);

        return result;

    }
}
