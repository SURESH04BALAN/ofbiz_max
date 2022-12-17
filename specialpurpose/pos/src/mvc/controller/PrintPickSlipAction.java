package mvc.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OnePanelSizableContainerDlg;
import org.ofbiz.ordermax.base.TwoPanelResizeableContainerInternalFrame;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.report.ReportMainController;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;
import org.ofbiz.ordermax.report.reports.InventoryItemReceiptReportJasper;
import org.ofbiz.ordermax.report.reports.PickingSlipReportJasper;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.pos.PosTransaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class PrintPickSlipAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = PrintPickSlipAction.class.getName();
    public static final String nameStr = "Print Pick Slip";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";
    private OrderActionInterface orderActionInterface;

    public PrintPickSlipAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.PRINT_PURCHASE_PICKSLIP_ACTION,
                PrintPickSlipAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public PrintPickSlipAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,  ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.PRINT_PURCHASE_PICKSLIP_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
    }

    public PrintPickSlipAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_PICKSLIP_ACTION, session);
        this.orderCompListModel = orderCompListModel;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Print Pick Slip";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {
            final PickingSlipReportJasper report = new PickingSlipReportJasper();
            report.setShowSelectionPanel(false);
            final Order order = orderActionInterface.getOrder();

            if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {
                Set<Order> orderIds = new HashSet<Order>();
                orderIds.add(order);
                report.reportArgs.put("orderHeader", orderIds);
                ReportMainController findOrderListMain = new ReportMainController(session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
                findOrderListMain.RunReport(desktopPane);
            }
        } else if (orderCompListModel.getSize() > 0) {
            final PickingSlipReportJasper report = new PickingSlipReportJasper();
            report.setShowSelectionPanel(false);
            for (final Order order : orderCompListModel.getList()) {

                if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {
                    Set<Order> orderIds = new HashSet<Order>();
                    orderIds.add(order);
                    report.reportArgs.put("orderHeader", orderIds);
                    break;
                }
            }
            ReportMainController findOrderListMain = new ReportMainController(session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
            findOrderListMain.RunReport(desktopPane);
        }
    }
}
