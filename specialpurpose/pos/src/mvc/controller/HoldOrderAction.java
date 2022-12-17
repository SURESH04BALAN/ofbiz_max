package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.Action;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;

import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class HoldOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "HOLD ORDER";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public HoldOrderAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION,
                HoldOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public HoldOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.APPROVE_PURCHASE_ORDER_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public HoldOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
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

    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {

            final Order order = orderActionInterface.getOrder();
            orderStatusChanges(order.getOrderId());
        } else {
            for (final Order order : orderCompListModel.getList()) {
                orderStatusChanges(order.getOrderId());
            }
        }
    }

    public void orderStatusChanges(String orderId) {
        Debug.logInfo("orderStatusChanges : ", module);
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                resultMap = dispatcher.runSync("changeOrderStatus", UtilMisc.toMap("orderId", orderId, "statusId", "ORDER_HOLD", "userLogin", userLogin));
                OrderMaxUtility.handleServiceReturn("Quick Ship Entire Order: ", resultMap);
                /*if (resultMap.containsKey("responseMessage")) {
                 if (resultMap.get("responseMessage").equals("error")) {
                 int selection = OrderMaxOptionPane.showConfirmDialog(
                 null, resultMap.get("errorMessageList").toString(), "Quick Ship Entire Order: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                 }
                 }*/

                //Debug.logInfo("Test sales order with id [" + orderId + "] has been shipped", module);
            } catch (Exception exc) {
                Debug.logError("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
            }
        }

        ActionEvent event = new ActionEvent(this, 1, orderId);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }
    }

    protected ClassLoader getClassLoader() {
        //Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            //Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    //Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                    Debug.logError("class loader 5", module);
                }

                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }
}
