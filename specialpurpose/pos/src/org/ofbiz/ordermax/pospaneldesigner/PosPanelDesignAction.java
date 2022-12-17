/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import org.ofbiz.ordermax.price.*;
import org.ofbiz.ordermax.product.supplierproduct.*;
import org.ofbiz.ordermax.inventory.*;
import org.ofbiz.ordermax.invoice.*;
import mvc.controller.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.KeyStroke;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.party.PartyMainScreen;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class PosPanelDesignAction extends ScreenAction {

    final String nameStr = "Pos Panel Design";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public PosPanelDesignAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.POS_PANEL_DESIGN_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public PosPanelDesignAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.POS_PANEL_DESIGN_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module) == false) {
//            org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen(null, session);
//                productCatalogMainScreen.loadScreenDialog();
//            productCatalogMainScreen.loadInternalFrameScreen(org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module, desktopPane);
//        }
//        FindSupplierProductListController findOrderListMain = new FindSupplierProductListController( null, false, session);
//        findOrderListMain.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane, frame);
//                PriceMainScreen priceMainScreen = new PriceMainScreen(frame, session);
//                priceMainScreen.loadScreen(PriceMainScreen.module);
        ControllerOptions controllerOptions = new ControllerOptions();
        PosPanelDesignerMainScreen.runController(controllerOptions);
                //PosPanelDesignerMainScreen purchaseOrder = new PosPanelDesignerMainScreen(controllerOptions);
//                purchaseOrder.pos = pos;
        //purchaseOrder.loadThreePanelFrame(PosPanelDesignerMainScreen.module);

    }

    @Override
    public Action getAction() {
        return this;
    }

    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new FindSupplierProductlListAction(name, session, desktopPane);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new FindSupplierProductlListAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }

}
