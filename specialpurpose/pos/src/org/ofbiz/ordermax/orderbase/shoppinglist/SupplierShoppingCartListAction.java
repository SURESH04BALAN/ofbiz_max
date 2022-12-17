/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

import org.ofbiz.ordermax.orderbase.returns.*;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.payment.PaymentController;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class SupplierShoppingCartListAction extends ScreenAction {

    final String nameStr = "Order Return";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public SupplierShoppingCartListAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public SupplierShoppingCartListAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, name, session, desktopPane);
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
        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addReturnHeaderType("VENDOR_RETURN");
        controllerOptions.addRoleType("SUPPLIER");
      
                        MaintainOrderReturnController.runController(controllerOptions);        
//        MaintainOrderReturnController paymentController = new MaintainOrderReturnController("", controllerOptions, session);
  //       paymentController.loadNonSizeableInternalFrameDialogScreen(PaymentController.module, desktopPane);

//        FindOrderReturnListController findPaymentListController = new FindOrderReturnListController( null, controllerOptions, session);
//        findPaymentListController.loadTwoPanelInternalFrame(FindOrderReturnListController.module, desktopPane, frame);
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new FindSalesInvoiceListAction(name, session, desktopPane);
        }

 

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new FindSalesInvoiceListAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_RETURN_LIST_ACTION, new LoadOrderListActionFactory());
    }

}
