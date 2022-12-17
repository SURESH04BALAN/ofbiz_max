/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */


public class ProductEnquireAction extends ScreenAction {        
    static public final String nameStr = "Product Enquire(Read Only)";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
       ControllerOptions controllerOptions = null;
    public ProductEnquireAction(XuiSession session, ControllerOptions controllerOptions, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_ENQUIRE_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.controllerOptions = controllerOptions;
        loadIcons();
    }    
    public ProductEnquireAction(String name, XuiSession session, ControllerOptions controllerOptions, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_ENQUIRE_ACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.controllerOptions = controllerOptions;
        loadIcons();
    }
    
   

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module) == false) {
//            org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen(null, session);
//                productCatalogMainScreen.loadScreenDialog();
//            productCatalogMainScreen.loadInternalFrameScreen(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module, desktopPane);
//        }
        ControllerOptions options = new ControllerOptions();
        options.setReadOnly(true);
        ProductMaintainController productMaintainController = new ProductMaintainController( options, session);
        productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, desktopPane);

//FindProductListController findProductListController = new FindProductListController( null, true, session);
//        findProductListController.loadTwoPanelInternalFrame(FindProductListController.module, desktopPane, frame);        
    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new FindProductListAction(name, session, desktopPane);
        }

               
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new FindProductListAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }    
    
}
