/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.payment.purchase;

import org.ofbiz.ordermax.invoice.*;
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

public class SupplierPaymentListAction extends ScreenAction {        
    final String nameStr = "Supplier Payment";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
     
    public SupplierPaymentListAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CUSTOMER_PAYMENT_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public SupplierPaymentListAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CUSTOMER_PAYMENT_ACTION,name, session, desktopPane);
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
//        SupplierPaymentInvoiceController paymentInvoiceController = new SupplierPaymentInvoiceController( null, session);
//        paymentInvoiceController.loadTwoPanelInternalFrame(FindPaymentListController.module, desktopPane);
        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addOrderType("PURCHASE_ORDER");
        controllerOptions.addRoleTypeParent("SUPPLIER");
        controllerOptions.addParentPaymentTypeId("DISBURSEMENT");
        SupplierPaymentInvoiceController.runController(controllerOptions);
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
        ScreenActionFactory.registerAction(ActionType.CUSTOMER_PAYMENT_ACTION, new LoadOrderListActionFactory());
    }    
    
}
