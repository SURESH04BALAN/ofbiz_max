/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import org.ofbiz.ordermax.orderbase.*;
import javax.swing.JToggleButton;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateNewShipGroupAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.QuickShipEntireOrderAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import mvc.controller.ViewOrderHistoryAction;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;

/**
 *
 * @author BBS Auctions
 */
public class OrderReturnActionPopupButton extends ReturnPopupButtonMenu {

    public static final String module = OrderReturnActionPopupButton.class.getName();

    public ViewOrderHistoryAction viewOrderHistoryAction = null;
//    public CreateNewShipGroupAction createNewShipGroupAction = null;
    public CreateAsNewOrderAction createAsNewOrderAction = null;
    public CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    public CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    public QuickRefundEntireSalesOrderAction quickRefundEntireOrderAction = null;

    public QuickShipEntireOrderAction quickShipEntireOrderAction = null;
    public CreateNewShipGroupAction createNewShipGroupAction = null;
    public ViewEditDeliverScheduleInfoAction viewEditDeliverScheduleInfoAction = null;
    public ReceiveOrderReturnItemsAction receiveOrderReturnItemsAction = null;
    //public ApproveOrderAction statusApprovedAction = null;
    public JToggleButton newOrderReturnButton = createActionButtonItemToggle("New Order");
    public JToggleButton saveOrderButton = createActionButtonItemToggle("Save Order");
    ControllerOptions option = null;
    public OrderReturnActionPopupButton(JToggleButton button, OrderReturnActionInterface orderReturnActionInterface, ControllerOptions option) {
        super(button, orderReturnActionInterface, ControllerOptions.getDesktopPane());
        
        this.option = option;
        
        receiveOrderReturnItemsAction = new ReceiveOrderReturnItemsAction(option, orderReturnActionInterface);
        /*viewOrderHistoryAction = new ViewOrderHistoryAction(desktopPane, orderReturnActionInterface);
        createReplacementPurchaseOrderAction = new CreateReplacementOrderAction(desktopPane, orderReturnActionInterface);
        createReturnPurchaseOrderAction = new CreateReturnOrderAction(desktopPane, orderReturnActionInterface);
        quickRefundEntireOrderAction = new QuickRefundEntireSalesOrderAction(desktopPane, orderReturnActionInterface);
        createAsNewOrderAction = new CreateAsNewOrderAction(desktopPane, orderReturnActionInterface);
//        statusApprovedAction = new ApproveOrderAction(desktopPane,  orderReturnActionInterface);
        createNewShipGroupAction = new CreateNewShipGroupAction(desktopPane, orderReturnActionInterface);
        quickShipEntireOrderAction = new QuickShipEntireOrderAction(desktopPane, orderReturnActionInterface);
        viewEditDeliverScheduleInfoAction = new ViewEditDeliverScheduleInfoAction(desktopPane, orderReturnActionInterface);
        */
    }

    public String getName() {
        return "Order Process Actions";
    }

    @Override
    final public void loadPopMenuAction(final ReturnHeaderComposite orderReturn) {
        popupPanel.removeAll();
        popupPanel.add(newOrderReturnButton);
        if ("RETURN_REQUESTED".equals(popuporderReturn.getReturnHeader().getstatusId())) {
  /*          JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);
*/

        }
//            JToggleButton btnVal = quickShipEntireOrderAction.createActionButtonItemToggle();
//            popupPanel.add(btnVal);

        if ("RETURN_ACCEPTED".equals(popuporderReturn.getReturnHeader().getstatusId())) {
            JToggleButton btnVal = receiveOrderReturnItemsAction.createActionButtonItemToggle();
            popupPanel.add(btnVal);
/*
            JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);
*/
        }

        if ("RETURN_COMPLETED".equals(popuporderReturn.getReturnHeader().getstatusId())) {
            /*
//if(popuporderReturn.getOrderReadHelper().get)
            if (popuporderReturn.getReturnableItems() != null && !popuporderReturn.getReturnableItems().isEmpty()) {
                JToggleButton btnViewEditDeliver = quickRefundEntireOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver);

                btnViewEditDeliver = createReturnPurchaseOrderAction.createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver);
            }
            
            JToggleButton btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            btnViewEditDeliver = createReplacementPurchaseOrderAction.createActionButtonItemToggle();
            popupPanel.add(btnViewEditDeliver);

            JToggleButton viewOrderHistoryBtn = viewOrderHistoryAction.createActionButtonItemToggle();
            popupPanel.add(viewOrderHistoryBtn);
            **/
        }

    }
}
