/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class ProductMaintainPosTreeAction extends ScreenAction {    
    static public final String nameStr = "Product Maintain Tree (Pos)";
    final String iconPathStr = "productIconsmall.png";
    final String iconPathSmallStr = "productIconsmall.png";
    ControllerOptions controllerOptions = null;        
    public ProductMaintainPosTreeAction(ControllerOptions controllerOptions, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCTACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.controllerOptions = controllerOptions;
        loadIcons();
    }
    
    public ProductMaintainPosTreeAction(String name, ControllerOptions controllerOptions, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCTACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
                this.controllerOptions = controllerOptions;
        loadIcons();
    }
    


    public void actionPerformed(ActionEvent e) {
        if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductMaintainTreeController.module) == false
                && BaseMainScreen.makeInternalFrameVisible(org.ofbiz.ordermax.product.ProductMaintainTreeController.module, desktopPane) == false) {
            org.ofbiz.ordermax.product.ProductMaintainTreeController.runController(controllerOptions) ;
                    //productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductMaintainTreeController(controllerOptions, session);
//                productCatalogMainScreen.loadScreenDialog();
            //productCatalogMainScreen.loadTwoPanelInternalFrame(org.ofbiz.ordermax.product.ProductMaintainTreeController.module, desktopPane);
        }

    }
    
    public Action getAction() {
        return this;
    }
/*
    static class ProductActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name,ControllerOptions controllerOptions, XuiSession session, JDesktopPane desktopPane) {
            return new ProductAction(name, controllerOptions, session, desktopPane, null);
        }
        @Override
        public ScreenAction createAction(String name, ControllerOptions controllerOptions, XuiSession session, JDesktopPane desktopPane, JFrame frame) {
            return new ProductAction(name, controllerOptions, session, desktopPane, frame);
        }
        
        @Override
        public ScreenAction createAction(ControllerOptions controllerOptions, XuiSession session, JDesktopPane desktopPane) {
             return new ProductAction(controllerOptions, session, desktopPane, null);
        }        
    }

    static {
        ScreenActionFactory.registerAction(ActionType.PRODUCTACTION, new ProductActionFactory());
    }   */
}
