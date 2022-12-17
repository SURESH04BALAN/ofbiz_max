package org.ofbiz.ordermax.report;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.report.reports.InvoiceItemReport;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;
import org.ofbiz.pos.PosTransaction;

public class ReportMainAction extends ScreenAction {        
    final String nameStr = "Report Main";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
     
    public ReportMainAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.INVENTORY_ITEM_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public ReportMainAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.INVENTORY_ITEM_LIST_ACTION,name, session, desktopPane);
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
  //      ReportMainController findOrderListMain = new ReportMainController( null, false, session);
  //      findOrderListMain.loadSinglePanelInternalFrame(ReportMainController.module, desktopPane, frame);

//           ReportMainScreen.loadScreen(PosTransaction.getCurrentTx(XuiContainer.getSession()));
                InvoiceItemReport report = new InvoiceItemReport();
        ReportMainController findOrderListMain = new ReportMainController( session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
        findOrderListMain.loadSinglePanelInternalFrame(ReportMainController.module, desktopPane);
        
  
//            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductCatalogMainScreen.module) == false) {
//                org.ofbiz.ordermax.product.ProductCatalogMainScreen productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductCatalogMainScreen(null, pos.getSession());
//                productCatalogMainScreen.loadScreen(org.ofbiz.ordermax.product.ProductCatalogMainScreen.module);
//            }

        
    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ReportMainAction(name, session, desktopPane);
        }
  
               
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new ReportMainAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }    
    
}
