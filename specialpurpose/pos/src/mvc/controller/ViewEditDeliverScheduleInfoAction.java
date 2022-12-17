package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.deliveryschedule.EditDeliveryScheduleController;
import org.ofbiz.ordermax.party.PartyMainScreen;
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
public class ViewEditDeliverScheduleInfoAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "View/Edit Delivery Schedule Info";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public ViewEditDeliverScheduleInfoAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.VIEWEDIT_DELIVER_SCHEDULEINFO_ACTION,
                ViewEditDeliverScheduleInfoAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public ViewEditDeliverScheduleInfoAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,  ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.VIEWEDIT_DELIVER_SCHEDULEINFO_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public ViewEditDeliverScheduleInfoAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.VIEWEDIT_DELIVER_SCHEDULEINFO_ACTION, session);
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

            if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {
                Set<Order> orderIds = new HashSet<Order>();
                orderIds.add(order);
                ControllerOptions controllerOptions = new ControllerOptions();
                controllerOptions.addOrderType(order.getOrderType());
                EditDeliveryScheduleController findOrderListController = new EditDeliveryScheduleController(order, controllerOptions,  session);
                findOrderListController.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane);
            }
        } else if (orderCompListModel.getSize() > 0) {

            for (final Order order : orderCompListModel.getList()) {

                try {

                    if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {
                        ControllerOptions controllerOptions = new ControllerOptions();
                        controllerOptions.addOrderType("SALES_ORDER");
                        EditDeliveryScheduleController findOrderListController = new EditDeliveryScheduleController(controllerOptions, session);
                        findOrderListController.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane);
                    }
                } //          });
                catch (Exception ex) {
                    Debug.logError(ex, module);
                } finally {
                }
            }
        } else {
            int selection = OrderMaxOptionPane.showConfirmDialog(
                    null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
