/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindInvoiceBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.invoice.purchaseinvoice.PurchaseInvoiceController;
import org.ofbiz.ordermax.invoice.salesinvoice.SalesInvoiceController;

import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindInvoiceListController extends BaseMainScreen {

    public static final String module = FindInvoiceListController.class.getName();
    public PanelFindInvoiceBean panel = null;
    public final String caption = "Invoice List";
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    public String getCaption() {

        if (controllerOptions.isSalesInvoice()) {
            return "Sales Invoice List";
        } else {
            return "Purchase Invoice List";
        }

    }
    ControllerOptions controllerOptions = null;

    public FindInvoiceListController(JFrame pFrame, ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        this.controllerOptions = controllerOptions;
    }

    public FindInvoiceListController(ListAdapterListModel<Order> ordListModel, ControllerOptions controllerOptions, JFrame pFrame, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        this.controllerOptions = controllerOptions;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindInvoiceBean(controllerOptions);
        /*
         try {
         panel.invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE"));
         } catch (Exception ex) {
         Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
         }    */
        /* if (isSalesList) {
         panel.cbSalesOrder.setSelected(true);
         panel.cbPurchaseOrder.setSelected(false);
         } else {
         panel.cbSalesOrder.setSelected(false);
         panel.cbPurchaseOrder.setSelected(true);

         }
         */
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setInvoiceList(invoiceCompositeListModel);

        buttonPanel = new FindInvoiceListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        /*
         buttonPanel.btnDisplayOrder.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         String url = "https://localhost:8443/ordermgr/control/orderview?orderId=" + order.getOrderHeader().getOrderId();
         try {
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         } catch (IOException ex) {
         Logger.getLogger(ReceiveInventoryGenerateInventoryAction.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });
         */
        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });
buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_CANCEL);
            }
        });
        
        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                invoiceCompositeListModel.clear();
                ControllerOptions.waitCursorBegin();
                List<EntityCondition> findOptionList = panel.getFindOptionCondList();
//                findOptionList.put("userLogin", ControllerOptions.getSession().getUserLogin());
                for (EntityCondition entry : findOptionList) {
                    Debug.logError(entry.toString(), module);
                }

                //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                LoadInvoiceWorker worker = new LoadInvoiceWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                panel.tablePanel.actionPerformed(worker);
                ControllerOptions.waitCursorEnd();
                panel.setVisibleFilter(false);                
            }
        });

        ActionListener invoiceIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    RowColumnActionEvent event = (RowColumnActionEvent) e;

                    try {
                        String invoiceId = panel.getTxtInvoiceIdTableTextField().getText();
                        Debug.logInfo("orderId: " + invoiceId, module);

                        GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), ControllerOptions.getSession().getDelegator());
                        if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
                            PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, ControllerOptions.getSession());
                            purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, ControllerOptions.getDesktopPane());
                        } else {
                            SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, ControllerOptions.getSession());
                            salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, ControllerOptions.getDesktopPane());
                        }
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
        };

        panel.getInvoiceActionTableCellEditor().addActionListener(invoiceIdChangeAction);

//        ReceiveInventorySetReceiveAllAction allAction = new ReceiveInventorySetReceiveAllAction(invoiceCompositeListModel);
//        buttonPanel.btnReceiveAllInventory.addActionListener(allAction);
//        ReceiveInventoryResetAllAction allResetAction = new ReceiveInventoryResetAllAction(invoiceCompositeListModel);
//        buttonPanel.btnGenerateInvoice.addActionListener(allResetAction);
//        ReceiveInventoryGenerateInventoryAction generateInventoryAction = new ReceiveInventoryGenerateInventoryAction(invoiceCompositeListModel, ControllerOptions.getSession());
//        buttonPanel.btnGenerateInventory.addActionListener(generateInventoryAction);
//        LoadOrderListAction findBtnAction = new LoadOrderListAction(invoiceCompositeListModel, findOptionList, ControllerOptions.getSession());
//        panel.btnFind.addActionListener(findBtnAction);
    }

    @Override
    public String getClassName() {
        return module;
    }

    public InvoiceComposite getSelectedInvoice() {
        return panel.getSelectedInvoice();
    }

}
