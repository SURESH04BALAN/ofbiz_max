/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.party.PartyMaintainController;
import org.ofbiz.ordermax.party.PartyMaintainControllerNew;

/**
 *
 * @author siranjeev
 */
public class SupplierClientAction extends ScreenAction {

    final String nameStr = "Suppliers";
    final String iconPathStr = "supplier_small.png";
    final String iconPathSmallStr = "supplier_small.png";

    public SupplierClientAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SUPPLIERCLIENTACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public SupplierClientAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.SUPPLIERCLIENTACTION, name, session, desktopPane);
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
        ControllerOptions options = new ControllerOptions();
        options.addRoleType("SUPPLIER");
        options.addRoleTypeParent("SUPPLIER");
        options.put("isSupplier", false);
        PartyMaintainControllerNew.runController(options);// partyMain = new PartyMaintainController(options);
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class SupplierClientFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new SupplierClientAction(name, session, desktopPane);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new SupplierClientAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.SUPPLIERCLIENTACTION, new SupplierClientFactory());
    }
}
