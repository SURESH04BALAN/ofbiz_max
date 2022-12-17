/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.party.PartyTreeMaintainController;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class ClientAction extends ScreenAction {

    final String nameStr = "Customer Maintain";
    final String iconPathStr = "clients.png";
    final String iconPathSmallStr = "person_small.png";//clients.png";

    public ClientAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CLIENTACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ClientAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CLIENTACTION, name, session, desktopPane);
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
//        PartyMainScreen partyMain = new PartyMainScreen("CUSTOMER", null, session);
//        partyMain.loadThreePanelInternalFrame(PartyMainScreen.module, desktopPane, frame);
        if (BaseMainScreen.makeCurrentScreenVisible(PartyTreeMaintainController.module) == false
                && BaseMainScreen.makeInternalFrameVisible(PartyTreeMaintainController.module, desktopPane) == false) {
            ControllerOptions options = new ControllerOptions();
            options.addRoleType("CUSTOMER");   
            PartyTreeMaintainController.runController(options);
            //PartyTreeMaintainController partyMain = new PartyTreeMaintainController("CUSTOMER", session, options);
            //partyMain.loadThreePanelInternalFrame(PartyTreeMaintainController.module, desktopPane);
        }
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class ClientActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ClientAction(name, session, desktopPane);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new ClientAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.CLIENTACTION, new ClientActionFactory());
    }

}
