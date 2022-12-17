package org.ofbiz.ordermax.report.reports;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

public class PosReportMainAction extends ScreenAction {

    final String nameStr = "Pos Reports";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";

    public PosReportMainAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.INVENTORY_ITEM_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public PosReportMainAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
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

//        final InventoryItemReport report = new InventoryItemReport();
        //  ReportMainController findOrderListMain = new ReportMainController(session, PosTransaction.getCurrentTx(XuiContainer.getSession()));
        //   findOrderListMain.RunReportSelection(desktopPane);
        /*final com.openbravo.pos.reports.PanelReportBean panelReport = new com.openbravo.pos.reports.PanelReportBean();
        com.openbravo.pos.reports.JParamsDatesInterval paramdates = new com.openbravo.pos.reports.JParamsDatesInterval("invoiceDate");
        paramdates.setStartDate(com.openbravo.beans.DateUtils.getToday());
        panelReport.addQBFFilter(paramdates);
        com.openbravo.pos.reports.JParamsPartyId panel = new com.openbravo.pos.reports.JParamsPartyId();
        panelReport.addQBFFilter(panel);
        //panelReport.activate();
        //final SelectAddressPanel viewer = new SelectAddressPanel(options);
        //viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
        ControllerOptions options = new ControllerOptions();
        options.setSimpleScreenInterface(panelReport);
        panelReport.setReport("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jrxml");
        panelReport.init(null);
        try {
            panelReport.activate();
        } catch (BasicException ex) {
            Logger.getLogger(PosReportMainAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        //final com.openbravo.pos.reports.PanelReportBean panelReport = NewReportMainController.getReportPanel(name);
              ControllerOptions options = new ControllerOptions();
        //options.setSimpleScreenInterface(panelReport);
        //final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runControllerInternalFrame(options);
        final org.ofbiz.ordermax.report.reports.NewReportMainController frame1 = org.ofbiz.ordermax.report.reports.NewReportMainController.runControllerInternalFrame(options);
//panelReport.setVisible(true);
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new PosReportMainAction(name, session, desktopPane);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new PosReportMainAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }

}
