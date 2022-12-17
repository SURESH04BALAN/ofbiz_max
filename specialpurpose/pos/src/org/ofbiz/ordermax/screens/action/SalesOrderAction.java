/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;

/**
 *
 * @author siranjeev
 */
public class SalesOrderAction extends ScreenAction {

    final String nameStr = "Sales Order";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    public SalesOrderAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SALESORDER, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public SalesOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SALESORDER, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    
    
    String orderId = null;
    public SalesOrderAction(String orderId, String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SALESORDER, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.orderId = orderId;
        loadIcons();

    }
    
    
    public void actionPerformed(ActionEvent e) {
    //    PurchaseOrderController purchaseOrder = new PurchaseOrderController(null, session);
    //    purchaseOrder.loadInternalFrameScreen(PurchaseOrderMainScreen.module, desktopPane, frame);
        
        try {
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addRoleType("CUSTOMER");
            controllerOptions.addOrderType("SALES_ORDER");
            controllerOptions.addRoleTypeParent("CUSTOMER");
            SalesOrderController.runController(controllerOptions);// salesOrderController = new SalesOrderController(orderId, controllerOptions, session);
            //salesOrderController.loadSinglePanelInternalFrame(salesOrderController.module, desktopPane);
        } catch (Exception ex) {
            Debug.logError(ex, name);
        }
                
    }

    public Action getAction() {
        return this;
    }
       static class SalesOrderActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new SalesOrderAction(name, session, desktopPane);
        }
 
        
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new SalesOrderAction(session, desktopPane);
        }            
    }

    static {
        ScreenActionFactory.registerAction(ActionType.SALESORDER, new SalesOrderActionFactory());
    }     
}
