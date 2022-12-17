package mvc.controller;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.report.ReportMainController;
import org.ofbiz.ordermax.report.reports.InventoryItemReceiptReportJasper;
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
public class PrintInventoryReceiptStickerAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
//    private ListAdapterListModel<ShipmentReceiptComposite> invoiceCompListModel;
    ItemListInterface<ShipmentReceiptComposite> itemListInterface = null;
    public static final String module = PrintInventoryReceiptStickerAction.class.getName();
    public static final String nameStr1 = "Print Item Sticker";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public PrintInventoryReceiptStickerAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface) {
        super(ActionType.PRINT_PICKSLIP_ACTION,
                PrintInventoryReceiptStickerAction.nameStr1, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public PrintInventoryReceiptStickerAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ItemListInterface<ShipmentReceiptComposite> itemListInterface) {
        super(ActionType.PRINT_PICKSLIP_ACTION, name, session, desktopPane);
        this.itemListInterface = itemListInterface;
        this.session = session;
    }

    public PrintInventoryReceiptStickerAction(ItemListInterface<ShipmentReceiptComposite> itemListInterface, XuiSession session) {
        super(ActionType.PRINT_PICKSLIP_ACTION, session);
        this.itemListInterface = itemListInterface;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Print Invoice";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        frame.setSize(desktopPane.getBounds().width - 2 * x, desktopPane.getBounds().height - 2 * y);
        frame.setLocation(x, y);
    }

    protected void setSizeAndLocation(TwoPanelNonSizableContainerDlg dlg) {
        int y = 10;
        int x = 10;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {
            final Order order = orderActionInterface.getOrder();
            ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(order, session);

            for (final ShipmentReceiptComposite inventoryReceipt : ric.getShipmentReceiptCompositeList().getList()) {
                try {
                    final InventoryItemReceiptReportJasper report = new InventoryItemReceiptReportJasper();
                    report.setShowSelectionPanel(false);
                    report.addDataObject(report.reportArgs, inventoryReceipt);
                    ReportMainController findOrderListMain = new ReportMainController( session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
                    findOrderListMain.RunReport(desktopPane);
                } //          });
                catch (Exception ex) {
                    Debug.logError(ex, PrintInventoryReceiptStickerAction.class.getName());
                } finally {
                }
            }
        } else if (itemListInterface.getList().size() > 0) {

            for (final ShipmentReceiptComposite inventoryReceipt : itemListInterface.getList()) {

                try {
                    final InventoryItemReceiptReportJasper report = new InventoryItemReceiptReportJasper();
                    report.setShowSelectionPanel(false);
                    report.addDataObject(report.reportArgs, inventoryReceipt);
                    ReportMainController findOrderListMain = new ReportMainController(session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
                    findOrderListMain.RunReport(desktopPane);
                } //          });
                catch (Exception ex) {
                    Debug.logError(ex, PrintInventoryReceiptStickerAction.class.getName());
                } finally {
                }
            }

        } else {
            int selection = OrderMaxOptionPane.showConfirmDialog(
                    null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
