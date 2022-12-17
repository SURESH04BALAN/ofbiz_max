/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import mvc.model.list.ListAdapterListModel;
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
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class QuickShipEntireOrderAction extends ScreenAction {

    public static final String module = QuickShipEntireOrderAction.class.getName();
    public static final String nameStr = "Quick Ship Entire Order";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = ""; //salesorder_small.png";

    ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private OrderActionInterface orderActionInterface;

    public QuickShipEntireOrderAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION,
                QuickShipEntireOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public QuickShipEntireOrderAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public QuickShipEntireOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public QuickShipEntireOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderListModel) {
        super(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.orderListModel = orderListModel;
        loadIcons();
    }

    public QuickShipEntireOrderAction(String name, XuiSession session) {
        super(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
//    private Order order = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {

            final Order order = orderActionInterface.getOrder();
            quickShipEntireOrder(order.getOrderId());
        } else if (orderListModel != null) {
            for (Order order : orderListModel.getList()) {

                quickShipEntireOrder(order.getOrderId());
            }
        }
        Debug.logInfo("ShipmentReceiptAction : end", module);
    }

    public void quickShipEntireOrder(String orderId) {
        Debug.logInfo("ShipmentReceiptAction : ", module);
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                resultMap = dispatcher.runSync("quickShipEntireOrder", UtilMisc.toMap("orderId", orderId, "userLogin", userLogin));
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

        ActionEvent event = new ActionEvent(this, 1, "receive inventory");
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

    @Override
    public Action getAction() {
        return this;
    }

    static class ListPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new QuickShipEntireOrderAction(name, session, desktopPane);
        }



        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new QuickShipEntireOrderAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.QUICKSHIP_ENTIRE_ORDER_ACTION, new ListPriceLookupActionFactory());
    }
}
