/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindInventoryItemBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.NAME;
import mvc.controller.LoadInventoryWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.InventoryItem;
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
public class FindInventoryItemListController extends BaseMainScreen {

    public static final String module = FindInventoryItemListController.class.getName();
    public PanelFindInventoryItemBean panel = null;
    public final String caption = "Order List";
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private boolean isSalesList = false;

    public String getCaption() {

        return "Inventory Item List";

    }

    public FindInventoryItemListController(boolean isSalesList, XuiSession sess) {
        super(sess);
        this.isSalesList = isSalesList;
    }

    public FindInventoryItemListController(ListAdapterListModel<Order> ordListModel, boolean isSalesList, XuiSession sess) {
        super(sess);
//        orderListModel.addAll(ordListModel.getList());
        this.isSalesList = isSalesList;
    }

    FindInventoryItemListButtonPanel buttonPanel = null;
    ControllerOptions controllerOptions = new ControllerOptions();
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<InventoryItem> invoiceCompositeListModel = new ListAdapterListModel<InventoryItem>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindInventoryItemBean(controllerOptions);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        /* try {
         panel.invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE"));
         } catch (Exception ex) {
         Logger.getLogger(FindInventoryItemListPanel.class.getName()).log(Level.SEVERE, null, ex);
         }        
         if (isSalesList) {
         panel.cbSalesOrder.setSelected(true);
         panel.cbPurchaseOrder.setSelected(false);
         } else {
         panel.cbSalesOrder.setSelected(false);
         panel.cbPurchaseOrder.setSelected(true);

         }
         */

        panel.setInventoryItemList(invoiceCompositeListModel);

        buttonPanel = new FindInventoryItemListButtonPanel();
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
                //              panel.getDialogFields();
                //              Order order = panel.getOrder();
                //              SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
                Debug.logInfo("before loadPersonsWorker.execute", NAME);
//                saveOrderWorker.execute();
                //               SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                Debug.logInfo("after loadPersonsWorker.execute", NAME);

                //update the dialog
//                panel.setDialogFields();
                f.okButtonPressed();
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                invoiceCompositeListModel.clear();

                try {

                    invoiceCompositeListModel.clear();
                    //List<EntityCondition> findOptionList = panel.getWhereClauseCond();

//                    List<InventoryItem> invList = LoadInventoryWorker.loadInventoryItem(findOptionList,ControllerOptions.getSession());
                    invoiceCompositeListModel.addAll(LoadInventoryWorker.loadInventoryItem(panel.getEntityConditions(), ControllerOptions.getSession()));
                    panel.setVisibleFilter(false);                          
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                        String invoiceId = panel.getTxtProdIdTableTextField().getText();
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

        panel.getProductTreeActionTableCellEditor().addActionListener(invoiceIdChangeAction);

    }

    @Override
    public String getClassName() {
        return module;
    }

}
