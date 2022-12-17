/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePicker;

/**
 *
 * @author siranjeev
 */
public class ProductMaintainTreeAction extends ScreenAction {    
    static public final String nameStr = "Product Tree Maintain";
    final String iconPathStr = "productIconsmall.png";
    final String iconPathSmallStr = "productIconsmall.png";
    ControllerOptions controllerOptions = null;        
    public ProductMaintainTreeAction(ControllerOptions controllerOptions, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCTACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.controllerOptions = controllerOptions;
        loadIcons();
    }
    
    public ProductMaintainTreeAction(String name, ControllerOptions controllerOptions, XuiSession session, javax.swing.JDesktopPane desktopPane) {
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
            controllerOptions.setShowFullProductScreen(true);
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
