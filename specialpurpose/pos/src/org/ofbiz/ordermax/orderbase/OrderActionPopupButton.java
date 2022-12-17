/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import mvc.controller.ApproveOrderAction;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateNewShipGroupAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import mvc.controller.QuickRefundEntirePurchaseOrderAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.QuickShipEntireOrderAction;
import mvc.controller.ShipmentReceiptAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import mvc.controller.ViewOrderHistoryAction;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author BBS Auctions
 */
public class OrderActionPopupButton extends OrderPopupButtonMenu {

    public static final String module = OrderActionPopupButton.class.getName();

    public ViewOrderHistoryAction viewOrderHistoryAction = null;
//    public CreateNewShipGroupAction createNewShipGroupAction = null;
    public CreateAsNewOrderAction createAsNewOrderAction = null;
    public CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    public CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    public QuickRefundEntireSalesOrderAction quickRefundEntireOrderAction = null;
    public QuickRefundEntirePurchaseOrderAction quickRefundEntirePurchaseOrderAction = null;
    public QuickShipEntireOrderAction quickShipEntireOrderAction = null;
    public ShipmentReceiptAction shipmentReceiptAction = null;
    public CreateNewShipGroupAction createNewShipGroupAction = null;
    public ViewEditDeliverScheduleInfoAction viewEditDeliverScheduleInfoAction = null;

    //public ApproveOrderAction statusApprovedAction = null;
    public JToggleButton newOrderButton = createActionButtonItemToggle("New Order");
    public JToggleButton saveOrderButton = createActionButtonItemToggle("Save Order");

    public OrderActionPopupButton(JToggleButton button, OrderActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderActInterface, desktopPane);

        viewOrderHistoryAction = new ViewOrderHistoryAction(desktopPane, orderActInterface);
        createReplacementPurchaseOrderAction = new CreateReplacementOrderAction(desktopPane, orderActInterface);
        createReturnPurchaseOrderAction = new CreateReturnOrderAction(desktopPane, orderActInterface);
        quickRefundEntireOrderAction = new QuickRefundEntireSalesOrderAction(desktopPane, orderActInterface);
        quickRefundEntirePurchaseOrderAction = new QuickRefundEntirePurchaseOrderAction(desktopPane, orderActInterface);
        createAsNewOrderAction = new CreateAsNewOrderAction(desktopPane, orderActInterface);
//        statusApprovedAction = new ApproveOrderAction(desktopPane,  orderActInterface);
        createNewShipGroupAction = new CreateNewShipGroupAction(desktopPane, orderActInterface);
        quickShipEntireOrderAction = new QuickShipEntireOrderAction(desktopPane, orderActInterface);
        shipmentReceiptAction = new ShipmentReceiptAction(desktopPane, orderActInterface);
        viewEditDeliverScheduleInfoAction = new ViewEditDeliverScheduleInfoAction(desktopPane, orderActInterface);
    }

    public String getName() {
        return "Order Process Actions";
    }

    @Override
    final public void loadPopMenuAction(final Order pOrder) {
        popupPanel.removeAll();
        popupPanel.add(newOrderButton/*, BorderLayout.LINE_START*/);
        if (popupOrder != null
                && popupOrder.getOrderId() != null
                && popupOrder.getOrderId().isEmpty() == false) {
            if ("ORDER_CREATED".equals(popupOrder.getOrderStatusId())) {
                JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

//                btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                    JToggleButton btnInvVal = purchaseInvoiceAction.createActionButtonItemToggle();
//                    popupPanel.add(btnInvVal/*, BorderLayout.LINE_START*/);
            }
//            JToggleButton btnVal = quickShipEntireOrderAction.createActionButtonItemToggle();
//            popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);

            if ("ORDER_APPROVED".equals(popupOrder.getOrderStatusId())) {
                if ("SALES_ORDER".equals(popupOrder.getOrderType())) {
                    JToggleButton btnVal = quickShipEntireOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);
                } else {

                    JToggleButton btnVal = shipmentReceiptAction.createActionButtonItemToggle();
                    popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);
                }
                JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

            }

            if ("ORDER_COMPLETED".equals(popupOrder.getOrderStatusId())) {
                //if(popupOrder.getOrderReadHelper().get)
                if (popupOrder.getReturnableItems() != null && !popupOrder.getReturnableItems().isEmpty()) {
                    if ("SALES_ORDER".equals(popupOrder.getOrderType())) {
                        JToggleButton btnViewEditDeliver = quickRefundEntireOrderAction.createActionButtonItemToggle();
                        popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                        btnViewEditDeliver = createReturnPurchaseOrderAction.createActionButtonItemToggle();
                        popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                    } else {

                        JToggleButton btnViewEditDeliver = quickRefundEntirePurchaseOrderAction.createActionButtonItemToggle();
                        popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                        btnViewEditDeliver = createReturnPurchaseOrderAction.createActionButtonItemToggle();
                        popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    }
                }

                JToggleButton btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                btnViewEditDeliver = createReplacementPurchaseOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                JToggleButton viewOrderHistoryBtn = viewOrderHistoryAction.createActionButtonItemToggle();
                popupPanel.add(viewOrderHistoryBtn/*, BorderLayout.LINE_START*/);
            }
        }
    }
}
