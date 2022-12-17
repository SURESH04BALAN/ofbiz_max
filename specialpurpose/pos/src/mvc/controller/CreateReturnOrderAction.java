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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.returns.MaintainOrderReturnController;
import org.ofbiz.ordermax.payment.PaymentController;
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
public class CreateReturnOrderAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Create Return";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public CreateReturnOrderAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.CREATE_RETURN_ACTION,
                CreateReturnOrderAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public CreateReturnOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.CREATE_RETURN_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public CreateReturnOrderAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.CREATE_RETURN_ACTION, session);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {
            final Order order = orderActionInterface.getOrder();
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addReturnHeaderType("CUSTOMER_RETURN");
            controllerOptions.addRoleType("CUSTOMER");

            if (orderReturn(order, controllerOptions)) {
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
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addReturnHeaderType("CUSTOMER_RETURN");
            controllerOptions.addRoleType("CUSTOMER");
            for (final Order order : orderCompListModel.getList()) {

                if (orderReturn(order, controllerOptions)) {
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

    public boolean orderReturn(Order order, ControllerOptions controllerOptions) {
        controllerOptions.addOrder(order);
        MaintainOrderReturnController.runController(controllerOptions) ;//paymentController = new MaintainOrderReturnController(order, controllerOptions, session);
        //paymentController.loadNonSizeableInternalFrameDialogScreen(PaymentController.module, desktopPane);
        return true;
    }
}
