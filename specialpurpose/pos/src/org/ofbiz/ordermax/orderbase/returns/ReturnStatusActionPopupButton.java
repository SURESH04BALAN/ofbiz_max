/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JToggleButton;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.StatusValidChangeToDetail;
import static org.ofbiz.ordermax.orderbase.PopupButtonMenu.getStatusValidChangeToDetail;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/**
 *
 * @author BBS Auctions
 */
public class ReturnStatusActionPopupButton extends ReturnPopupButtonMenu {

    public static final String module = ReturnStatusActionPopupButton.class.getName();
    public AcceptOrderReturnAction acceptOrderReturnAction = null;
    public ReceiveOrderReturnAction receiveOrderReturnAction = null;
    public CancelOrderReturnAction cancelOrderReturnAction = null;
    public CompleteOrderReturnAction completeOrderReturnAction = null;
    public ManualOrderReturnAction manualOrderReturnAction = null;
    private final Map<String, ScreenAction> orderReturnStatusActions = new HashMap<String, ScreenAction>();


    public ReturnStatusActionPopupButton(JToggleButton button, OrderReturnActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        super(button, orderActInterface, desktopPane);
        acceptOrderReturnAction = new AcceptOrderReturnAction(desktopPane, orderActInterface);
        cancelOrderReturnAction = new CancelOrderReturnAction(desktopPane, orderActInterface);
        completeOrderReturnAction = new CompleteOrderReturnAction(desktopPane, orderActInterface);
        receiveOrderReturnAction = new ReceiveOrderReturnAction(desktopPane, orderActInterface);
        manualOrderReturnAction = new ManualOrderReturnAction(desktopPane, orderActInterface);
        
        orderReturnStatusActions.put("RETURN_ACCEPTED", acceptOrderReturnAction);
        orderReturnStatusActions.put("RETURN_RECEIVED", receiveOrderReturnAction);
//        orderReturnStatusActions.put("RETURN_REQUESTED", cancelPaymentAction);
        orderReturnStatusActions.put("RETURN_CANCELLED", cancelOrderReturnAction);
        orderReturnStatusActions.put("RETURN_COMPLETED", completeOrderReturnAction);
        orderReturnStatusActions.put("RETURN_MAN_REFUND", manualOrderReturnAction);
    }

    public String getName() {
        return "Return Status Action";
    }
   

    @Override
    public void loadPopMenuAction(ReturnHeaderComposite orderReturn) {
        popupPanel.removeAll();
        
        String statusId = popuporderReturn.getReturnHeader().getstatusId();
        List<StatusValidChangeToDetail> statusValidChangeToDetails = getStatusValidChangeToDetail(statusId, XuiContainer.getSession());
        for (StatusValidChangeToDetail nextStatusDetail : statusValidChangeToDetails) {
            if (orderReturnStatusActions.containsKey(nextStatusDetail.getstatusIdTo())) {
                JToggleButton btnViewEditDeliver = orderReturnStatusActions.get(nextStatusDetail.getstatusIdTo()).createActionButtonItemToggle();
                popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);
            }

        }
    }

}
