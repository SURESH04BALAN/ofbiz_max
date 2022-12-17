package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.components.InvoicePickerEditPanel;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.report.ReportMainController;
import org.ofbiz.ordermax.report.reports.SalesInvoiceMaxSpicesReportJasper;
import org.ofbiz.ordermax.report.reports.SalesInvoiceReportJasper;
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
public class PrintSalesInvoiceAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<InvoiceComposite> invoiceCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Print Sales Invoice";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";
    private OrderActionInterface orderActionInterface;
 
    public PrintSalesInvoiceAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION,
                PrintSalesInvoiceAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public PrintSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<InvoiceComposite> invoiceCompListModel) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, name, session, desktopPane);
        this.invoiceCompListModel = invoiceCompListModel;
        this.session = session;
    }

    public PrintSalesInvoiceAction(ListAdapterListModel<InvoiceComposite> invoiceCompListModel, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, session);
        this.invoiceCompListModel = invoiceCompListModel;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderActionInterface != null) {
            Order order = orderActionInterface.getOrder();
            String invoiceTypeId = "";
            String partyId = "";
            if ("SALES_ORDER".equals(order.getOrderType())) {
                invoiceTypeId = "SALES_INVOICE";
                partyId = order.getOrderReadHelper().getBillToParty().getString("partyId");
            } else {
                Debug.logError("Order Type Not Handled: " + order.getOrderType(), module);
            }

            //final SalesInvoiceReportJasper report = new SalesInvoiceReportJasper();
            
            final SalesInvoiceMaxSpicesReportJasper report = new SalesInvoiceMaxSpicesReportJasper();
            report.setShowSelectionPanel(false);

            for (String invoiceId : order.getInvoiceIds()) {
                report.reportArgs.put("invoiceId", invoiceId);
                report.reportArgs.put("partyId", partyId);
                report.reportArgs.put("invoiceTypeId", invoiceTypeId);
                ReportMainController findOrderListMain = new ReportMainController( session, report);
                findOrderListMain.RunReport(desktopPane);
            }
        } else if (invoiceCompListModel.getSize() > 0) {
            final SalesInvoiceReportJasper report = new SalesInvoiceReportJasper();
            report.setShowSelectionPanel(false);
            for (final InvoiceComposite invComposite : invoiceCompListModel.getList()) {
                try {
//                    Map<String, Object> parameters = new HashMap<String, Object>();
//                    parameters.put("OrderFinancialData", orderFinancialData);

                    report.reportArgs.put("invoiceId", invComposite.getInvoice().getinvoiceId());
                    report.reportArgs.put("partyId", invComposite.getPartyTo().getParty().getpartyId());
                    report.reportArgs.put("invoiceTypeId", "SALES_INVOICE");

//                    report.reportArgs.put("OrderFinancialData", orderFinancialData);
                    ReportMainController findOrderListMain = new ReportMainController( session, PosTransaction.getCurrentTx(XuiContainer.getSession()), report);
                    findOrderListMain.RunReport(desktopPane);

                } catch (Exception ex) {
                    Logger.getLogger(PrintSalesInvoiceAction.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        /*
         if (invoiceCompListModel.getSize() > 0) {

         for (final InvoiceComposite invComposite : invoiceCompListModel.getList()) {

         try {

         //load financial data
         final ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

         //get financial data for this party
         String billToPartyId = invComposite.getPartyTo().getParty().getpartyId();
         List<InvoiceComposite> list = LoadInvoiceWorker.loadOutstandingInvoices(billToPartyId, "SALES_INVOICE", session);
         invoiceCompositeListModel.addAll(list);
         //                LoadInvoiceWorker loadOutstandingPurchaseInvoicerWorker = new LoadInvoiceWorker(billFromPartyId, invoiceCompositeListModel, session);
         //                loadOutstandingPurchaseInvoicerWorker.execute();
         //            SwingUtilities.invokeLater(new Runnable() {
         //                public void run() {
         OrderFinancialData orderFinancialData = new OrderFinancialData(invoiceCompositeListModel, OrderFinancialData.getCurrentDate());
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("OrderFinancialData", orderFinancialData);
         ThreePanelContainerInternalFrame internalFrame = new ThreePanelContainerInternalFrame(name);
         //                        internalFrame.setTitle(getCaption());
         internalFrame.setDividerLocation(0);
         //                        loadScreen(internalFrame);
         internalFrame.setSize(840, 700);
         desktopPane.add(internalFrame);

         parameters.put("ReportTitle", "Address Report");
         parameters.put("DataFile", "CustomBeanFactory.java - Bean Collection");
         parameters.put(EntityJasperReport.DelegatorName, session.getDelegator());
         parameters.put(EntityJasperReport.Session, session);
         parameters.put("invoiceId", invComposite.getInvoice().getinvoiceId());
         EntityJasperReport jasperReport = new InvoiceReportJasper();
         final JasperPrint jasperPrint = jasperReport.runReport(parameters);
         JPanel panelJasper = internalFrame.getPanelDetail();
         internalFrame.getPanelSelecton().setVisible(false);
         if (jasperPrint != null) {
         panelJasper.removeAll();
         JRViewer viewer = new JRViewer(jasperPrint);
         panelJasper.setLayout(new GridLayout(1, 1));
         panelJasper.add(viewer);
         }
         internalFrame.setVisible(true);
         internalFrame.toFront();
         internalFrame.requestFocus();
         } //          });
         catch (Exception ex) {
         Logger.getLogger(PrintSalesInvoiceAction.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
         }
         }
         } else {
         int selection = OrderMaxOptionPane.showConfirmDialog(
         null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
         }
         */
    }
}
