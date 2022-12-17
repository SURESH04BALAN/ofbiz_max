/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;

/**
 *
 * @author siranjeev
 */
public class PurchaseOrderAction extends ScreenAction {

    final String nameStr = "Purchase Order";
    final String iconPathStr = "purchaseorder.png";
    final String iconPathSmallStr = "purchaseorder.png";

    public PurchaseOrderAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PURCHASEORDER, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public PurchaseOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PURCHASEORDER, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }


    String orderId = null;

    public PurchaseOrderAction(String orderId, String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PURCHASEORDER, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.orderId = orderId;
        loadIcons();
    }

    public void actionPerformed(ActionEvent e) {
//        PurchaseOrderMainScreen purchaseOrder = new PurchaseOrderMainScreen(null, session);
//        purchaseOrder.loadInternalFrameScreen(PurchaseOrderMainScreen.module, desktopPane, frame);
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addOrderType("PURCHASE_ORDER");
            controllerOptions.addRoleType("SUPPLIER");    
            controllerOptions.addRoleTypeParent("SUPPLIER");             
            controllerOptions.put("orderId", orderId);
             
        try {
            PurchaseOrderController.runController(controllerOptions);/* purchaseOrder = new PurchaseOrderController(orderId, controllerOptions, session);
            purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane);*/
        } catch (Exception ex) {
            Debug.logError(ex, name);
        }
        
    }

    public Action getAction() {
        return this;
    }

    static class PurchaseOrderActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new PurchaseOrderAction(name, session, desktopPane);
        }

   

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new PurchaseOrderAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.PURCHASEORDER, new PurchaseOrderActionFactory());
    }
}
