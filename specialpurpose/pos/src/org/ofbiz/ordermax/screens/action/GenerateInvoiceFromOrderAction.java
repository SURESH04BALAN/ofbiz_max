/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.party.PartyMainScreen;

/**
 *
 * @author siranjeev
 */

public class GenerateInvoiceFromOrderAction extends ScreenAction {        
    final String nameStr = "Customers";
    final String iconPathStr = "clients.png";
    final String iconPathSmallStr = "clients.png";
     
    public GenerateInvoiceFromOrderAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERATE_INVOICE_FROM_ORDERACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public GenerateInvoiceFromOrderAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERATE_INVOICE_FROM_ORDERACTION,name, session, desktopPane);
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

    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class ClientActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new GenerateInvoiceFromOrderAction(name, session, desktopPane);
        }
     
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new GenerateInvoiceFromOrderAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.GENERATE_INVOICE_FROM_ORDERACTION, new ClientActionFactory());
    }    
    
}
