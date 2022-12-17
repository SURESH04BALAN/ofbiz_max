/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product.catalog;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.product.catalog.CategoryProductManagePanel;
import org.ofbiz.ordermax.product.catalog.ProdCatalogMaintainPanel;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */

public class ProductCatalogMaintainAction extends ScreenAction {        
    public static final String nameStr = "Edit Catalog";
    final String iconPathStr = "";
    final String iconPathSmallStr = "";
     
    public ProductCatalogMaintainAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_CATEGORY_MAINTAIN_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public ProductCatalogMaintainAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_CATEGORY_MAINTAIN_ACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    

    public void actionPerformed(ActionEvent e) {
//        if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module) == false) {
//            org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen(null, session);
//                productCatalogMainScreen.loadScreenDialog();
//            productCatalogMainScreen.loadInternalFrameScreen(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module, desktopPane);
//        }
        
       ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addReturnHeaderType("VENDOR_RETURN");
        controllerOptions.addRoleType("SUPPLIER");
        
//        ProdCatalogMaintainPanel prodCatalogMaintainPanel = new ProdCatalogMaintainPanel();                
        ProductCatalogMaintainController partyMain = new ProductCatalogMaintainController(CategoryProductManagePanel.module, session);
        partyMain.loadSinglePanelInternalFrame(CategoryProductManagePanel.module, desktopPane);
    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class ClientActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ProductCatalogMaintainAction(name, session, desktopPane);
        }
  
               
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new ProductCatalogMaintainAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.PRODUCT_CATEGORY_MAINTAIN_ACTION, new ClientActionFactory());
    }    
    
}
