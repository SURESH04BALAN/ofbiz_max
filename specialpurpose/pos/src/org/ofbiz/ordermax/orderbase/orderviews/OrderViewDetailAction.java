package org.ofbiz.ordermax.orderbase.orderviews;

import org.ofbiz.ordermax.product.catalog.*;
import mvc.controller.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.OrderSummaryView;
import org.ofbiz.ordermax.composite.PaymentApplicationCompositeList;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class OrderViewDetailAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
//    private ListAdapterListModel<ShipmentReceiptComposite> invoiceCompListModel;
    ItemListInterface<ShipmentReceiptComposite> itemListInterface = null;
    public static final String module = OrderViewDetailAction.class.getName();
    public static final String nameStr = "Order Summary";
    final String iconPathStr = "";
    final String iconPathSmallStr = "";

    private XuiSession session;

    public OrderViewDetailAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, name, session, desktopPane);

        this.session = session;
    }

    public OrderViewDetailAction(ItemListInterface<ShipmentReceiptComposite> itemListInterface, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, session);
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
            return nameStr;
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

    public void actionPerformed(ActionEvent e) {
        Debug.logInfo("orderStatusChanges : ", module);
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ControllerOptions options = session.getControllerOptions();
        options.addRoleType("BILL_TO_CUSTOMER");
        options.addOrderType("SALES_ORDER");
        options.addOrderStatusType("ORDER_COMPLETED");
        options.addRoleTypeParent("CUSTOMER");
        OrderDetailViewPanel viewer = new OrderDetailViewPanel(options);
        if (desktopPane != null) {
            /*org.ofbiz.order.order.OrderReadHelper orderReadHelper = new org.ofbiz.order.order.OrderReadHelper(session.getDelegator(), "VG11040");
             ListAdapterListModel<OrderSummaryView> listModel = new ListAdapterListModel<OrderSummaryView>();
             OrderSummaryView orderSummaryView = new OrderSummaryView();
             orderSummaryView.setNumber(orderReadHelper.getOrderId());
             orderSummaryView.setDetails(orderReadHelper.getOrderName());
             orderSummaryView.setType(orderReadHelper.getOrderTypeId());
             orderSummaryView.setAmount(orderReadHelper.getOrderGrandTotal());
             orderSummaryView.setReference(orderReadHelper.getOrderHeader().getString("externalId"));
             orderSummaryView.setDate(orderReadHelper.getOrderHeader().getTimestamp("orderDate"));
             listModel.add(orderSummaryView);
             Set<String> invoiceIds = LoadOrderWorker.getOrderInvoiceIds(orderReadHelper.getOrderHeader());
             for (String invoiceId : invoiceIds) {
             try {
             InvoiceComposite invoiceComp = LoadInvoiceWorker.loadInvoice(invoiceId, session);
             orderSummaryView = new OrderSummaryView();
             if (invoiceComp.getInvoice() != null) {
             orderSummaryView.setNumber(invoiceComp.getInvoice().getinvoiceId());
             orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
             orderSummaryView.setType(invoiceComp.getInvoice().getinvoiceTypeId());
             orderSummaryView.setReference(invoiceComp.getInvoice().getreferenceNumber());
             orderSummaryView.setDate(invoiceComp.getInvoice().getinvoiceDate());
             if (invoiceComp.getInvoice().getdueDate() != null) {
             orderSummaryView.setDueDate(invoiceComp.getInvoice().getdueDate());
             } else {
             orderSummaryView.setDueDate(invoiceComp.getInvoice().getinvoiceDate());
             }
             }
             orderSummaryView.setAmount(invoiceComp.getTotalAmount());
             listModel.add(orderSummaryView);

             PaymentApplicationCompositeList applicationList = LoadPaymentWorker.loadPaymentApplicationsForInvoice(invoiceId, session);
             for (PaymentApplication pa : applicationList.getList()) {
             orderSummaryView = new OrderSummaryView();
             if (invoiceComp.getInvoice() != null) {
             orderSummaryView.setNumber(pa.getpaymentApplicationId());
             orderSummaryView.setDetails(pa.getpaymentId());
             orderSummaryView.setType("Payment");
             orderSummaryView.setReference(pa.getpaymentId());
             orderSummaryView.setDate(pa.getcreatedStamp());
             orderSummaryView.setDueDate(null);
             }
             orderSummaryView.setAmount(pa.getamountApplied());
             listModel.add(orderSummaryView);
             }

             List<Map<String, Object>> paymentList = LoadInvoiceWorker.getInvoicePaymentList(invoiceComp.getInvoice().getGenericValueObj(), session);
             for (Map<String, Object> gv : paymentList) {

             if (invoiceComp.getInvoice() != null) {
             OrderSummaryView paidSummaryView = new OrderSummaryView();

             paidSummaryView.setNumber("");
             // orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
             paidSummaryView.setType("");
             paidSummaryView.setReference("Paid Amount");
             paidSummaryView.setAmount((BigDecimal) gv.get("paidAmount"));
             paidSummaryView.setDate(null);
             paidSummaryView.setDueDate(null);
             listModel.add(paidSummaryView);
             }
             if (invoiceComp.getInvoice() != null) {
             OrderSummaryView balanceSummaryView = new OrderSummaryView();
             balanceSummaryView.setNumber("");
             // orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
             balanceSummaryView.setType("");
             balanceSummaryView.setReference("Balance Amount");
             balanceSummaryView.setAmount((BigDecimal) gv.get("outstandingAmount"));
             balanceSummaryView.setDate(null);
             balanceSummaryView.setDueDate(null);
             listModel.add(balanceSummaryView);
             }
                     
             }
             } catch (Exception ex) {
             Logger.getLogger(OrderViewDetailAction.class.getName()).log(Level.SEVERE, null, ex);
             }

             }

             viewer.setListModal(listModel);*/
            SimpleFrameMainScreen partyMain = new SimpleFrameMainScreen(viewer, ProductCategoryMaintainPanel.module, session);
            partyMain.loadSinglePanelInternalFrame(ProductCategoryMaintainPanel.module, desktopPane);

            /*
             JOptionPane pane = new JOptionPane(viewer, JOptionPane.PLAIN_MESSAGE,
             JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

             final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Product Category");

             viewer.addActionListener(new ActionListener() {

             public void actionPerformed(ActionEvent e) {
             dialog.setVisible(false);
             }
             });

             //            JPanel panelJasper = twoPanelContainerPanel.getPanelDetail();
             //                            chooser.getPanelSelecton().setVisible(false);
             //            panelJasper.removeAll();
             //            panelJasper.setLayout(new GridLayout(1, 1));
             //            panelJasper.add(viewer);
             setSizeAndLocation(dialog);
             dialog.setVisible(true);
             */
        } else {
            TwoPanelNonSizableContainerDlg posSelectionDlg = new TwoPanelNonSizableContainerDlg(null, true);
            posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            posSelectionDlg.setSize(1000, 750);
            posSelectionDlg.setLocationRelativeTo(null);
            posSelectionDlg.setDividerLocation(0);
            JPanel panelJasper = posSelectionDlg.getPanelDetail();
            posSelectionDlg.getPanelSelecton().setVisible(false);
            panelJasper.removeAll();
            panelJasper.setLayout(new GridLayout(1, 1));
            panelJasper.add(viewer);
            setSizeAndLocation(posSelectionDlg);
            posSelectionDlg.setVisible(true);
            posSelectionDlg.toFront();
            posSelectionDlg.requestFocus();
        }
    }

    protected ClassLoader getClassLoader() {

        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }
}
