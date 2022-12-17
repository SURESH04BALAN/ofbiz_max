package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class QuickRefundEntireSalesOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Quick Refund Entire Order";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public QuickRefundEntireSalesOrderAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.QUICK_REFUND_ENTIRE_ORDER_ACTION,
                QuickRefundEntireSalesOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public QuickRefundEntireSalesOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.QUICK_REFUND_ENTIRE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public QuickRefundEntireSalesOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.QUICK_REFUND_ENTIRE_ORDER_ACTION, session);
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
        if (orderActionInterface != null) {
            final Order order = orderActionInterface.getOrder();
            if (quickRefundEntireOrder(order)) {
                ActionEvent event = new ActionEvent(this, 1, order.getOrderId());
                for (ActionListener listener : listeners) {
                    Debug.logInfo("editCell Action listner", module);
                    listener.actionPerformed(event); // broadcast to all
                }
            } else {
                int result = OrderMaxOptionPane.showConfirmDialog(null, "Unable to Refund Entire Order: " + order.getOrderId(), "Refund Entire Order",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            }
        } else {
            for (final Order order : orderCompListModel.getList()) {

                if (quickRefundEntireOrder(order)) {
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

    public boolean quickRefundEntireOrder(Order order) {
        boolean result = true;
        Timestamp orderDate = order.orderHeader.getGenericValueObj().getTimestamp("orderDate");
        // if (orderDate.after(openDate)) {
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> returnResp = null;
        try {
            returnResp = dispatcher.runSync("quickReturnOrder", UtilMisc.<String, Object>toMap("orderId", order.getOrderId(),
                    "returnHeaderTypeId", "CUSTOMER_RETURN", "userLogin", session.getUserLogin()));
            OrderMaxUtility.ServiceReturnStatus status = OrderMaxUtility.handleServiceReturn("Quick Refund Entire Order: ", returnResp);
            if (OrderMaxUtility.ServiceReturnStatus.ERROR.equals(status)) {
                result = false;
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            result = false;
        } catch (Exception ex) {
            Debug.logError(ex, module);
            result = false;
        }
        return result;
    }
    //}
}
