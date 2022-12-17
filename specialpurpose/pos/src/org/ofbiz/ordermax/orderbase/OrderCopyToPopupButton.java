/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.BorderLayout;
import javax.swing.JToggleButton;
import mvc.controller.PayOrderAction;
import mvc.controller.PaymentGroupOrderAction;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction;

/**
 *
 * @author BBS Auctions
 */
public class OrderCopyToPopupButton extends OrderPopupButtonMenu {

    public static final String module = OrderCopyToPopupButton.class.getName();

//    public PrintInvoiceAction printSalesInvoiceAction = null;
    public EditSalesInvoiceAction editSalesInvoiceAction = null;
//    public PrintPickSlipAction printPurchasePickSlipAction = null;

//    public ViewOrderHistoryAction viewOrderHistoryAction = null;
//    public CreateNewShipGroupAction createNewShipGroupAction = null;//
    //  public CreateAsNewOrderAction createAsNewOrderAction = null;
    public PayOrderAction payOrderAction = null;
    public PaymentGroupOrderAction paymentGroupOrderAction = null;    
    
    //  public CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    //  public CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    //  public QuickRefundEntireOrderAction quickRefundEntireOrderAction = null;
//    public QuickShipEntireOrderAction quickShipEntireOrderAction = null;
//    public ApproveOrderAction approvedOrderAction = null;
//    public PrintInventoryReceiptStickerAction printInventoryReceiptStickerAction= null;

    public OrderCopyToPopupButton(JToggleButton button, OrderActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderActInterface, desktopPane);

//        printSalesInvoiceAction = new PrintInvoiceAction(desktopPane, parentFrame, orderActInterface);
        editSalesInvoiceAction = new EditSalesInvoiceAction(desktopPane, orderActInterface);
//        generateSalesInvoiceAction = new GenerateSalesInvoiceAction(GenerateSalesInvoiceAction.nameStr, session, desktopPane, parentFrame, orderListModel);

//        printPurchasePickSlipAction = new PrintPickSlipAction(desktopPane, parentFrame, orderActInterface);
//        viewOrderHistoryAction = new ViewOrderHistoryAction(ViewOrderHistoryAction.nameStr, session, desktopPane, parentFrame, orderListModel);
//        createNewShipGroupAction = new CreateNewShipGroupAction(CreateNewShipGroupAction.nameStr, session, desktopPane, parentFrame, orderListModel);
//        createAsNewOrderAction = new CreateAsNewOrderAction(desktopPane, parentFrame, orderActInterface);
        payOrderAction = new PayOrderAction(desktopPane, orderActInterface);
        paymentGroupOrderAction = new PaymentGroupOrderAction(desktopPane, orderActInterface);
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
    final public void loadPopMenuAction(final Order pOrder) {

        if (popupOrder != null
                && popupOrder.getOrderId() != null
                && popupOrder.getOrderId().isEmpty() == false) {
            if ("ORDER_CREATED".equals(popupOrder.getOrderStatusId())) {

//                JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
//                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);
//                    JToggleButton btnInvVal = purchaseInvoiceAction.createActionButtonItemToggle();
//                    popupPanel.add(btnInvVal/*, BorderLayout.LINE_START*/);
            }
  //          JToggleButton btnVal = quickShipEntireOrderAction.createActionButtonItemToggle();
            //          popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);

            if ("ORDER_APPROVED".equals(popupOrder.getOrderStatusId())) {

            //JToggleButton btnViewEditDeliver = payOrderAction.createActionButtonItemToggle();
                //popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
               // JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
                // popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);
            }

            if ("ORDER_COMPLETED".equals(popupOrder.getOrderStatusId())) {
//                JToggleButton btnViewEditDeliver = quickRefundEntireOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

//                btnViewEditDeliver = createReturnPurchaseOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = createReplacementPurchaseOrderAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
//                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
//                JToggleButton btnPrintVal = printSalesInvoiceAction.createActionButtonItemToggle();
//                popupPanel.add(btnPrintVal, BorderLayout.LINE_START);
                JToggleButton btnInvoiceVal = editSalesInvoiceAction.createActionButtonItemToggle();
                popupPanel.add(btnInvoiceVal, BorderLayout.LINE_START);

//                JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
//                popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);
                JToggleButton btnViewEditDeliver = payOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
                
                JToggleButton btnGroupPayment = paymentGroupOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnGroupPayment/*, BorderLayout.LINE_START*/);                

            }
        }
    }

}
