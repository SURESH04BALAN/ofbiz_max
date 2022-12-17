package org.ofbiz.ordermax.report.reports;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.TwoButtonContainerPanel;
import org.ofbiz.ordermax.report.ReportMainController;
import org.ofbiz.ordermax.report.reports.InventoryItemReport;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;
import org.ofbiz.pos.PosTransaction;

public class InventoryItemReceiptReportAction extends ScreenAction {

    final String nameStr = "SInventory Item Receipt Report";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public InventoryItemReceiptReportAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.INVENTORY_ITEM_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public InventoryItemReceiptReportAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.INVENTORY_ITEM_LIST_ACTION, name, session, desktopPane);
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
        final InventoryItemReceiptReportJasper report = new InventoryItemReceiptReportJasper();
        report.setShowSelectionPanel(true);
        ReportMainController findOrderListMain = new ReportMainController(session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
        findOrderListMain.RunReport(desktopPane);
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new InventoryItemReceiptReportAction(name, session, desktopPane);
        }

    

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new InventoryItemReceiptReportAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }

}
