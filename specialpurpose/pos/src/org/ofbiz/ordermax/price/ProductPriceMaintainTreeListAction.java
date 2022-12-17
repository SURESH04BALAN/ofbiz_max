/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import org.ofbiz.ordermax.product.supplierproduct.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
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
public class ProductPriceMaintainTreeListAction extends ScreenAction {

    final String nameStr = "Maintain Product Price";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public ProductPriceMaintainTreeListAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_PRICING_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ProductPriceMaintainTreeListAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRODUCT_PRICING_LIST_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        PriceMaintainTreeController priceMainScreen = new PriceMaintainTreeController(new ControllerOptions());
        priceMainScreen.loadTwoPanelInternalFrame(PriceMaintainTreeController.module, desktopPane);
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
