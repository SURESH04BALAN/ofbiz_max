/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import javolution.util.FastList;
import mvc.controller.LoadInventoryWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.InventoryItemAndDetail;
import org.ofbiz.ordermax.orderbase.OrderIdClickAction;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.product.ProductIdClickAction;
import org.ofbiz.ordermax.product.GetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindInventoryItemAndDetailListController extends BaseMainScreen
        implements GetProductIdInterface, OrderIdInterface {

    public static final String module = FindInventoryItemAndDetailListController.class.getName();
    public FindInventoryItemAndDetailListPanel panel = null;
    public final String caption = "Inventory Item And Detail List";
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private boolean isSalesList = false;

    public String getCaption() {

        return caption;

    }

    public FindInventoryItemAndDetailListController( boolean isSalesList, XuiSession sess) {
        super(sess);
        this.isSalesList = isSalesList;
    }

    public FindInventoryItemAndDetailListController(ListAdapterListModel<Order> ordListModel, boolean isSalesList,  XuiSession sess) {
        super(sess);
//        orderListModel.addAll(ordListModel.getList());
        this.isSalesList = isSalesList;
    }

    FindInventoryItemAndDetailListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<InventoryItemAndDetail> invoiceCompositeListModel = new ListAdapterListModel<InventoryItemAndDetail>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();
    ControllerOptions controllerOptions = new ControllerOptions();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindInventoryItemAndDetailListPanel(controllerOptions.getCopy());

        try {
            panel.invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE"));
        } catch (Exception ex) {
            Logger.getLogger(FindInventoryItemListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* if (isSalesList) {
         panel.cbSalesOrder.setSelected(true);
         panel.cbPurchaseOrder.setSelected(false);
         } else {
         panel.cbSalesOrder.setSelected(false);
         panel.cbPurchaseOrder.setSelected(true);

         }
         */

        panel.setReceiveInventoryList(invoiceCompositeListModel);

        buttonPanel = new FindInventoryItemAndDetailListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
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
            public void actionPerformed(ActionEvent e) {
//                if( panel.cbAll.isSelected()
//                orderStatusId
                List<String> stausList = FastList.newInstance();
                invoiceCompositeListModel.clear();
                List<InventoryItemAndDetail> invList;
                try {

                    invoiceCompositeListModel.clear();

                    //              Map<String, Object> findOptionList = panel.getFindOptionList();
//                    invList = LoadInventoryWorker.loadInventoryItemDetail(findOptionList,ControllerOptions.getSession());
                    List<EntityCondition> findOptionList = panel.getWhereClauseCond();
//                    Map<String, Object> findOptionList = panel.getFindOptionList();
                    invList = LoadInventoryWorker.loadInventoryItemDetail(findOptionList, ControllerOptions.getSession());

                    invoiceCompositeListModel.addAll(invList);

//                    Debug.logInfo("val.toString(): " + val.toString(), module);
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        //ControllerOptions options new ControllerOptions();
        ProductIdClickAction productIdClickAction = new ProductIdClickAction(this, controllerOptions.getCopy(), ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        OrderIdClickAction orderIdClickAction = new OrderIdClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        panel.getProductActionTableCellEditor().addActionListener(productIdClickAction);
        panel.getOrderActionTableCellEditor().addActionListener(orderIdClickAction);

        /*
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

         GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), delegator);
         if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
         PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, ControllerOptions.getSession());
         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
         } else {
         SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, ControllerOptions.getSession());
         salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane, null);
         }
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }
         }
         }
         };
         */
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
    public String getProductId() {
        return panel.getTxtProdIdTableTextField().getText();
    }

    @Override
    public String getOrderId() {
        return panel.getTxtOrderIdTableTextField().getText();
    }

    @Override
    public String getClassName() {
        return module;
    }

}
