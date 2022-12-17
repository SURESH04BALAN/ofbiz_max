/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JToggleButton;
import mvc.controller.ApproveOrderAction;
import mvc.controller.CancelOrderAction;
import mvc.controller.CompleteOrderAction;
import mvc.controller.HoldOrderAction;
import mvc.controller.RejectedOrderAction;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.StatusValidChangeToDetail;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/**
 *
 * @author BBS Auctions
 */
public class OrderStatusActionPopupButton extends OrderPopupButtonMenu {

    public static final String module = OrderStatusActionPopupButton.class.getName();
    public ApproveOrderAction orderApproveAction = null;
    public CancelOrderAction cancelOrderAction = null;
    public HoldOrderAction holdOrderAction = null;
    public RejectedOrderAction rejectedOrderAction = null;
    public CompleteOrderAction completeOrderAction = null;

    private final Map<String, ScreenAction> orderStatusActions = new HashMap<String, ScreenAction>();

    public OrderStatusActionPopupButton(JToggleButton button, OrderActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderActInterface, desktopPane);
        orderApproveAction = new ApproveOrderAction(desktopPane, orderActInterface);
        cancelOrderAction = new CancelOrderAction(desktopPane, orderActInterface);
        holdOrderAction = new HoldOrderAction(desktopPane, orderActInterface);
        rejectedOrderAction = new RejectedOrderAction(desktopPane, orderActInterface);
        completeOrderAction = new CompleteOrderAction(desktopPane, orderActInterface);

        orderStatusActions.put("ORDER_HOLD", holdOrderAction);
        orderStatusActions.put("ORDER_CANCELLED", cancelOrderAction);
        orderStatusActions.put("ORDER_APPROVED", orderApproveAction);
        orderStatusActions.put("ORDER_REJECTED", rejectedOrderAction);
        orderStatusActions.put("ORDER_COMPLETED", completeOrderAction);

    }

    @Override
    public String getName() {
        return "Order Status Action";
    }

    @Override
    final public void loadPopMenuAction(final Order pOrder) {
        popupPanel.removeAll();
        if (popupOrder != null
                && popupOrder.getOrderId() != null
                && popupOrder.getOrderId().isEmpty() == false) {
            List<StatusValidChangeToDetail> statusValidChangeToDetails = getStatusValidChangeToDetail(popupOrder.getOrderStatusId(), XuiContainer.getSession());
            for (StatusValidChangeToDetail nextStatusDetail : statusValidChangeToDetails) {
                if (orderStatusActions.containsKey(nextStatusDetail.getstatusIdTo())) {
                    JToggleButton btnViewEditDeliver = orderStatusActions.get(nextStatusDetail.getstatusIdTo()).createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                }

            }
        }
    }

}
