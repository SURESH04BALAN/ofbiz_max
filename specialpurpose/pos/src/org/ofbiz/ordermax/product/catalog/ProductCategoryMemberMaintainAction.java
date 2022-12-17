/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import org.ofbiz.ordermax.orderbase.returns.*;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.payment.PaymentController;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryMemberMaintainAction extends ScreenAction {

    static public final String nameStr = "Maintain Product Hierarchy";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public ProductCategoryMemberMaintainAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ProductCategoryMemberMaintainAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ControllerOptions options = new ControllerOptions();
        CategoryProductManagePanel categoryProductManagePanel = new CategoryProductManagePanel(options);                
        SimpleFrameMainScreen partyMain = new SimpleFrameMainScreen(categoryProductManagePanel, CategoryProductManagePanel.module, session);
        partyMain.loadSinglePanelInternalFrame(CategoryProductManagePanel.module, desktopPane);
     //   partyMain.loadThreePanelInternalFrame(CategoryProductManagePanel.module, desktopPane, frame);

 
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
