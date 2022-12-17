/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindFacilityInventoryProductBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
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
import org.ofbiz.ordermax.facility.ViewFacilityInventoryByProduct;
import org.ofbiz.ordermax.invoice.purchaseinvoice.PurchaseInvoiceController;
import org.ofbiz.ordermax.invoice.salesinvoice.SalesInvoiceController;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.party.PartyMainScreen;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindFacilitynventoryByProductController extends BaseMainScreen {

    public static final String module = FindFacilitynventoryByProductController.class.getName();
    public PanelFindFacilityInventoryProductBean panel = null;
    public final String caption = "Order List";
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private boolean isSalesList = false;

    public String getCaption() {

        return "Inventory Item List";

    }

    public FindFacilitynventoryByProductController(boolean isSalesList, XuiSession sess) {
        super(sess);
        this.isSalesList = isSalesList;
    }

    public FindFacilitynventoryByProductController(ListAdapterListModel<Order> ordListModel, boolean isSalesList, XuiSession sess) {
        super(sess);
//        orderListModel.addAll(ordListModel.getList());
        this.isSalesList = isSalesList;
    }

    FindInventoryItemListButtonPanel buttonPanel = null;
    ControllerOptions controllerOptions = new ControllerOptions();
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<Map<String, Object>> invoiceCompositeListModel = new ListAdapterListModel<Map<String, Object>>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindFacilityInventoryProductBean(controllerOptions);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }

        panel.setInventoryItemList(invoiceCompositeListModel);

        buttonPanel = new FindInventoryItemListButtonPanel();
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
            @Override
            public void actionPerformed(ActionEvent e) {

                invoiceCompositeListModel.clear();

                try {

                    invoiceCompositeListModel.clear();
                    //List<EntityCondition> findOptionList = panel.getWhereClauseCond();

                    //List<InventoryItem> invList = LoadInventoryWorker.loadInventoryItem(findOptionList,ControllerOptions.getSession());
                   // ViewFacilityInventoryByProduct val = new ViewFacilityInventoryByProduct();
                    Map<String, Object> result = LoadInventoryWorker.loadInventory(panel.getValues());
                    for (Map.Entry<String, Object> entryDept : result.entrySet()) {

                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue(), module);

                    }

                    List<Map<String, Object>> invList = (List<Map<String, Object>>) result.get("inventoryByProduct");
                    invoiceCompositeListModel.addAll(invList);
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

    //menu actions
    static public class FindFacilityInventoryByProductAction extends ScreenAction {

        final String nameStr = "Show Inventory";
        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";

        public FindFacilityInventoryByProductAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
            super(ActionType.INVENTORY_ITEM_LIST_ACTION, session, desktopPane);
            this.setName(nameStr);
            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        public FindFacilityInventoryByProductAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
            super(ActionType.INVENTORY_ITEM_LIST_ACTION, name, session, desktopPane);
            this.setName(nameStr);
            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            FindFacilitynventoryByProductController findOrderListMain = new FindFacilitynventoryByProductController(null, false, session);
            findOrderListMain.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane);
        }

        @Override
        public Action getAction() {
            return this;
        }
    }
}
