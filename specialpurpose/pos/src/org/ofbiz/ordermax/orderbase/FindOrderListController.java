/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.controller.LoadPurchaseOrderWorker;
import mvc.controller.LoadSalesOrderWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.JFontChooser;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderRolesList;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindOrderListController extends BaseMainScreen {

    public static final String module = FindOrderListController.class.getName();
    public FindOrderListPanel panel = null;
    public final String caption = "Order List";
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private boolean isSalesList = false;
    ControllerOptions controllerOptions = null;
    private Order order = null;

    public String getCaption() {

        if (controllerOptions.isSalesOrder()) {
            return "Sales Order List";
        } else {
            return "Purchase Order List";
        }

    }

    public FindOrderListController(ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        this.controllerOptions = controllerOptions;
    }

    public FindOrderListController(ListAdapterListModel<Order> ordListModel, ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        this.controllerOptions = controllerOptions;
    }
    public static JFontChooser jf = new JFontChooser();
    FindOrderListButtonPanel buttonPanel = null;

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindOrderListPanel(controllerOptions.getCopy());
        if (controllerOptions.isSalesOrder()) {
            panel.cbSalesOrder.setSelected(true);
            panel.cbPurchaseOrder.setSelected(false);
        } else {
            panel.cbSalesOrder.setSelected(false);
            panel.cbPurchaseOrder.setSelected(true);

        }
        /*        for (Order ord : orderListModel.getList()) {
         ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(ord, ControllerOptions.getSession());
         for (ShipmentReceiptComposite iter : ric.getShipmentReceiptCompositeList().getList()) {
         iter.setOrder(ord);
         orderListModel.add(iter);
         }
         }
         */
        panel.setReceiveInventoryList(orderListModel);
        panel.btnChangeFont.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jf.showDialog(panel);
                        Font font = jf.getSelectedFont();
                        /*                        OrderMaxOptionPane.showMessageDialog(panel, font == null ? "You canceled the dialog."
                         : "You have selected " + font.getName() + ", " + font.getSize()
                         + (font.isBold() ? ", Bold" : "") + (font.isItalic() ? ", Italic" : ""));
                         panel.setFont(font);
                         */
                        ComponentBorder.changeFont(panel, font);
                    }
                }
        );

        buttonPanel = new FindOrderListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        /*
         buttonPanel.btnDisplayOrder.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         String url = "https://localhost:8443/ordermgr/control/orderview?orderId=" + order.getOrderId();
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
            public void actionPerformed(ActionEvent e) {

                ControllerOptions.waitCursorBegin();
//                if( panel.cbAll.isSelected()
//                orderStatusId
                List<String> stausList = FastList.newInstance();
                orderListModel.clear();
                if (panel.cbApproved.isSelected() == true) {
                    stausList.add("ORDER_APPROVED");
                }
                if (panel.cbCanceled.isSelected() == true) {
                    stausList.add("ORDER_CANCELLED");
                }
                if (panel.cbCompleted.isSelected() == true) {
                    stausList.add("ORDER_COMPLETED");
                }
                if (panel.cbCreated.isSelected() == true) {
                    stausList.add("ORDER_CREATED");
                }
                if (panel.cbHeld.isSelected() == true) {
                    stausList.add("ORDER_HOLD");
                }
                if (panel.cbProcessing.isSelected() == true) {
                    stausList.add("ORDER_PROCESSING");
                }
                if (panel.cbRejected.isSelected() == true) {
                    stausList.add("ORDER_REJECTED");
                }

                List<String> orderTypeId = FastList.newInstance();
                if (panel.cbSalesOrder.isSelected() == true) {
                    orderTypeId.add("SALES_ORDER");
                }

                if (panel.cbPurchaseOrder.isSelected() == true) {
                    orderTypeId.add("PURCHASE_ORDER");
                }

                Map<String, Object> svcCtx = FastMap.newInstance();

                if (panel.cbPartiallyReceived.isSelected() == true) {
                    svcCtx.put("filterPartiallyReceivedPOs", "Y");
                }

                if (panel.cbOpenPastTheirETA.isSelected() == true) {
                    svcCtx.put("filterPOsOpenPastTheirETA", "Y");
                }

                if (panel.cbRejectedItems.isSelected() == true) {
                    svcCtx.put("filterPOsWithRejectedItems", "Y");
                }

                String filterInventoryProblems = "N";
                if (panel.cbInventoryProblem.isSelected() == true) {
                    filterInventoryProblems = "Y";
                }

                Locale locale = Locale.getDefault();
                String partyId = ControllerOptions.getSession().getCompanyPartyId();
                svcCtx.put("userLogin", ControllerOptions.getSession().getUserLogin());
                //svcCtx.put("orderId", order.getOrderId());
                svcCtx.put("roleTypeId", UtilMisc.toList("BILL_TO_CUSTOMER", "BILL_FROM_VENDOR"));
                svcCtx.put("partyId", partyId);
                svcCtx.put("locale", locale);
//                svcCtx.put("showAll", "Y");
                svcCtx.put("viewIndex", new Integer(1));
                svcCtx.put("viewSize", new Integer(1000));
                svcCtx.put("orderStatusId", stausList);
                svcCtx.put("orderTypeId", orderTypeId);
                svcCtx.put("filterInventoryProblems", filterInventoryProblems);
                List<GenericValue> orderList = LoadSalesOrderWorker.getOrderList(svcCtx, ControllerOptions.getSession());
                if (orderList != null) {
                    for (GenericValue genValue : orderList) {
                        //Order order = LoadPurchaseOrderWorker.loadShoppingCart(genValue.getString("orderId"), ControllerOptions.getSession());
                        //Locale locale = Locale.getDefault(); 
                        ShoppingCart shoppingCart = new ShoppingCart(ControllerOptions.getSession().getDelegator(), genValue.getString("productStoreId"), locale, genValue.getString("currencyUom"));
                        Order order = new Order(shoppingCart);
                        GenericValue orderGenVal = OrderReadHelper.getOrderHeader(ControllerOptions.getSession().getDelegator(), genValue.getString("orderId"));
//                         OrderHeader orderHeader = new OrderHeader(orderGenVal);
//                         order.setOrderHeader(orderHeader);
                        order.setOrderId(genValue.getString("orderId"));
                        //order.setCurrency(genValue.getString("currencyUom"));
                        order.setOrderDate(genValue.getTimestamp("orderDate"));
                        order.setOrderName(genValue.getString("orderName"));
                        order.setOrderStatusId(genValue.getString("statusId"));
//                         order.setRemainingSubTotal(genValue.getBigDecimal("remainingSubTotal"));
//                         order.setGrandTotal(genValue.getBigDecimal("grandTotal"));
                        order.setOrderType(genValue.getString("orderTypeId"));

                        //get order genericvalue
//                        GenericValue orderGenVal = OrderReadHelper.getOrderHeader(ControllerOptions.getSession().getDelegator(), genValue.getString("orderId"));
                        OrderRolesList orderRolesList = LoadPurchaseOrderWorker.getOrderRolesList(orderGenVal, ControllerOptions.getSession());
                        order.setBillFromAccount(orderRolesList.getBillFromParty());
                        order.setBillToAccount(orderRolesList.getBillToParty());
//                        order.setOrderRolesList(orderRolesList);
                        orderListModel.add(order);
                    }
                }
                ControllerOptions.waitCursorEnd();
            }

        });

        ActionListener productIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    try {
                        String orderId = panel.getTxtProdIdTableTextField().getText();
                        //Order order = getOrder(orderId);
                        ControllerOptions cOptions = controllerOptions.getCopy();
                        controllerOptions.addOrderId(orderId);
                        if (Order.ORDERTYPE_PURCHSEORDER.equals(order.getOrderType())) {
                            PurchaseOrderController.runController(cOptions);//purchaseOrder = new PurchaseOrderController( controllerOptions, ControllerOptions.getSession());
                            //purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane);
                        } else {

                            SalesOrderController.runController(cOptions);// salesOrderController = new SalesOrderController(orderId, cOptions, ControllerOptions.getSession());
                            //salesOrderController.loadSinglePanelInternalFrame(salesOrderController.module, ControllerOptions.getDesktopPane());

                        }
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
        };
        panel.getProductTreeActionTableCellEditor().addActionListener(productIdChangeAction);

        panel.tableReceiveInv.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    Debug.logInfo("valueChanged ", "module");
                    if (lsm.isSelectionEmpty()) {
                        System.out.println(" <none>");
                        order = null;
                    } else {
                        Debug.logInfo("valueChanged else", "module");
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                order = orderListModel.getElementAt(i);
                            }
                        }
                    }
                }
            }
        });

        panel.tableReceiveInv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    if (controllerOptions.isDoubleClickCloseDialog()) {
                        f.okButtonPressed();
                        setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);

                    } else {
                        if (order != null) {
                            String orderId = null;
                            String orderType = null;
                            TableModel model = panel.tableReceiveInv.getModel();
                            int row = panel.tableReceiveInv.rowAtPoint(e.getPoint());
                            int col = panel.tableReceiveInv.columnAtPoint(e.getPoint());
                            if (row >= 0 && col >= 0) {
                                orderId = model.getValueAt(row, OrderTableModel.Columns.ORDERNBR.getColumnIndex()).toString();
                                orderType = model.getValueAt(row, OrderTableModel.Columns.ORDERTYPE.getColumnIndex()).toString();

                            }

                            ControllerOptions cOptions = controllerOptions.getCopy();
                            if ("SALES_ORDER".equals(order.getOrderType())) {

                                cOptions.addRoleType("CUSTOMER");
                                cOptions.addOrderId(orderId);
                                SalesOrderController.runController(cOptions);// salesOrderController = new SalesOrderController(orderId, cOptions,  ControllerOptions.getSession());

                                //     f.okButtonPressed();
                            } else {
                                cOptions.addRoleType("SUPPLIER");
                                cOptions.put("orderId", orderId);
                                PurchaseOrderController.runController(cOptions);// purchaseOrderController = new PurchaseOrderController(orderId, cOptions, null, ControllerOptions.getSession());
//                        productMaintainController.loadNonSizeableTwoPanelDialogScreen(FindOrderByPartyListController.module, desktopPane, null);
//                        purchaseOrderController.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
//                                if (desktopPane == null) {
                                //                                  purchaseOrderController.loadNonSizeableTwoPanelDialogScreen(SalesOrderController.module, desktopPane, parentFrame);
                                //                            } else {
                                //                              purchaseOrderController.loadSinglePanelInternalFrame(SalesOrderController.module, desktopPane, null);
                                //                        }
                            }
                        }
                    }
                }
            }
        });

//        ReceiveInventorySetReceiveAllAction allAction = new ReceiveInventorySetReceiveAllAction(orderListModel);
//        buttonPanel.btnReceiveAllInventory.addActionListener(allAction);
//        ReceiveInventoryResetAllAction allResetAction = new ReceiveInventoryResetAllAction(orderListModel);
//        buttonPanel.btnGenerateInvoice.addActionListener(allResetAction);
//        ReceiveInventoryGenerateInventoryAction generateInventoryAction = new ReceiveInventoryGenerateInventoryAction(orderListModel, ControllerOptions.getSession());
//        buttonPanel.btnGenerateInventory.addActionListener(generateInventoryAction);
//        LoadOrderListAction findBtnAction = new LoadOrderListAction(orderListModel, findOptionList, ControllerOptions.getSession());
//        panel.btnFind.addActionListener(findBtnAction);
    }

    public Order getOrder(String orderId) {
        Order order = null;
        for (Order ord : orderListModel.getList()) {
            if (ord.getOrderId().equals(orderId)) {
                return ord;
            }
        }

        return order;
    }

    @Override
    public String getClassName() {
        return module;
    }

}
