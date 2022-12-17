/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import org.ofbiz.ordermax.orderbase.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import mvc.controller.PayOrderAction;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction;

/**
 *
 * @author BBS Auctions
 */
public class OrderReturnCopyToPopupButton extends ReturnPopupButtonMenu {

    public static final String module = OrderReturnCopyToPopupButton.class.getName();

//    public PrintInvoiceAction printSalesInvoiceAction = null;
    public EditSalesInvoiceAction editSalesInvoiceAction = null;
//    public PrintPickSlipAction printPurchasePickSlipAction = null;

//    public ViewOrderHistoryAction viewOrderHistoryAction = null;
//    public CreateNewShipGroupAction createNewShipGroupAction = null;//
    //  public CreateAsNewOrderAction createAsNewOrderAction = null;
    public PayOrderAction payOrderAction = null;
    //  public CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    //  public CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    //  public QuickRefundEntireOrderAction quickRefundEntireOrderAction = null;
//    public QuickShipEntireOrderAction quickShipEntireOrderAction = null;
//    public ApproveOrderAction approvedOrderAction = null;
//    public PrintInventoryReceiptStickerAction printInventoryReceiptStickerAction= null;

    public OrderReturnCopyToPopupButton(JToggleButton button, OrderReturnActionInterface orderReturnActionInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderReturnActionInterface, desktopPane);

//        printSalesInvoiceAction = new PrintInvoiceAction(desktopPane, parentFrame, orderActInterface);
        editSalesInvoiceAction = new EditSalesInvoiceAction(desktopPane, orderReturnActionInterface);
//        generateSalesInvoiceAction = new GenerateSalesInvoiceAction(GenerateSalesInvoiceAction.nameStr, session, desktopPane, parentFrame, orderListModel);

//        printPurchasePickSlipAction = new PrintPickSlipAction(desktopPane, parentFrame, orderActInterface);
//        viewOrderHistoryAction = new ViewOrderHistoryAction(ViewOrderHistoryAction.nameStr, session, desktopPane, parentFrame, orderListModel);
//        createNewShipGroupAction = new CreateNewShipGroupAction(CreateNewShipGroupAction.nameStr, session, desktopPane, parentFrame, orderListModel);
//        createAsNewOrderAction = new CreateAsNewOrderAction(desktopPane, parentFrame, orderActInterface);
        payOrderAction = new PayOrderAction(desktopPane, orderReturnActionInterface);
//        createReplacementPurchaseOrderAction = new CreateReplacementOrderAction(desktopPane, parentFrame, orderActInterface);
//        createReturnPurchaseOrderAction = new CreateReturnOrderAction(desktopPane, parentFrame, orderActInterface);
//        quickRefundEntireOrderAction = new QuickRefundEntireOrderAction(desktopPane, parentFrame, orderActInterface);
        //      quickShipEntireOrderAction = new QuickShipEntireOrderAction(desktopPane, parentFrame, orderActInterface);
//        approvedOrderAction = new ApproveOrderAction(desktopPane, parentFrame,  orderActInterface);        
//        printInventoryReceiptStickerAction  = new PrintInventoryReceiptStickerAction( desktopPane, parentFrame, orderActInterface);        
    }

    public String getName() {
        return "Order Copy To Process";
    }

    @Override
    public void loadPopMenuAction(ReturnHeaderComposite orderReturn) {
        popupPanel.removeAll();

        String statusId = popuporderReturn.getReturnHeader().getstatusId();
        if ("RETURN_REQUESTED".equals(statusId) || "RETURN_MAN_REFUND".equals(statusId)) {
        }
        //          JToggleButton btnVal = quickShipEntireOrderAction.createActionButtonItemToggle();
        //           popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);
        if ("RETURN_ACCEPTED".equals(statusId)) {

        }

        if ("RETURN_RECEIVED".equals(statusId) || "RETURN_COMPLETED".equals(statusId)) {

            JToggleButton btnInvoiceVal = editSalesInvoiceAction.createActionButtonItemToggle();
            popupPanel.add(btnInvoiceVal, BorderLayout.LINE_START);

//                JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
//                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);
            JToggleButton btnViewEditDeliver = payOrderAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
        }

    }
}
