/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import mvc.controller.ApproveOrderAction;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import mvc.controller.PayOrderAction;
import mvc.controller.PrintInventoryReceiptStickerAction;
import mvc.controller.PrintPickSlipAction;
import mvc.controller.PrintSalesInvoiceAction;
import mvc.controller.PrintPurchaseInvoiceAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.QuickShipEntireOrderAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction;

/**
 *
 * @author BBS Auctions
 */
public class OrderPrintPopupButton extends OrderPopupButtonMenu {

    public static final String module = OrderPrintPopupButton.class.getName();

    public PrintSalesInvoiceAction printSalesInvoiceAction = null;
    public PrintPurchaseInvoiceAction printPurchaseInvoiceAction = null;
    public PrintPickSlipAction printPickSlipAction = null;
    public PrintInventoryReceiptStickerAction printInventoryReceiptStickerAction = null;

    public OrderPrintPopupButton(JToggleButton button, OrderActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderActInterface, desktopPane);

        printSalesInvoiceAction = new PrintSalesInvoiceAction(desktopPane, orderActInterface);
        printPickSlipAction = new PrintPickSlipAction(desktopPane, orderActInterface);
        printInventoryReceiptStickerAction = new PrintInventoryReceiptStickerAction(desktopPane, orderActInterface);
        printPurchaseInvoiceAction = new PrintPurchaseInvoiceAction(desktopPane, orderActInterface);
    }

    public String getName() {
        return "Order Print Actions";
    }

    final public void loadPopMenuAction(final Order pOrder) {
        popupPanel.removeAll();
        if (popupOrder != null
                && popupOrder.getOrderId() != null
                && popupOrder.getOrderId().isEmpty() == false) {
            if ("ORDER_CREATED".equals(popupOrder.getOrderStatusId())) {

//                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                JToggleButton btnPrintPickSlipVal = printPickSlipAction.createActionButtonItemToggle();
                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);

//                    JToggleButton btnInvVal = purchaseInvoiceAction.createActionButtonItemToggle();
//                    popupPanel.add(btnInvVal/*, BorderLayout.LINE_START*/);
            }

            if ("ORDER_APPROVED".equals(popupOrder.getOrderStatusId())) {

                JToggleButton btnPrintPickSlipVal = printPickSlipAction.createActionButtonItemToggle();
                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);

            }

            if ("ORDER_COMPLETED".equals(popupOrder.getOrderStatusId())) {
//                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                if ("SALES_ORDER".equals(popupOrder.getOrderType())) {
                    JToggleButton btnPrintVal = printSalesInvoiceAction.createActionButtonItemToggle();
                    popupPanel.add(btnPrintVal, BorderLayout.LINE_START);
                } else {

                    JToggleButton btnVal = printPurchaseInvoiceAction.createActionButtonItemToggle();
                    popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);

                    JToggleButton printReceiptAct1 = printInventoryReceiptStickerAction.createActionButtonItemToggle();
                    Debug.logInfo("printReceiptAct.getText(): " + printReceiptAct1.getText(), module);
                    popupPanel.add(printReceiptAct1/*, BorderLayout.LINE_START*/);
                }

                JToggleButton btnPrintPickSlipVal = printPickSlipAction.createActionButtonItemToggle();
                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);
            }
        }
    }
}
