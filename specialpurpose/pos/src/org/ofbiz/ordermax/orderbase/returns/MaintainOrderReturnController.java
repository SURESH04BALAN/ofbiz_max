/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.LoadOrderReturnWorker;
import mvc.controller.LoadOrderWorker;
import mvc.controller.LoadSalesOrderWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.ProductIdVerifyValidator;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;

import mvc.model.table.PurchaseOrderTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentInvoiceController;
import org.ofbiz.ordermax.product.SetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class MaintainOrderReturnController extends BaseMainScreen
        implements SetProductIdInterface, OrderReturnActionInterface {

    public static final String module = MaintainOrderReturnController.class.getName();
    public OrderReturnHeaderPanel panel = null;
//    public OrderReturnItemsListPanel orderReturnItemPanel = null;
    OrderReturnItemListInterface orderReturnItemPanel = null;
    OrderReturnItemListInterface orderReturnItemViewPanel = null;

    OrderReturnCopyToPopupButton copyToButton = null;
//    OrderPrintPopupButton printButtonPopup = null;
//    OrderActionPopupButton orderActionPopupButton = null;
//    OrderStatusActionPopupButton orderStatusActionPopupButton;    
//    OrderReturnItemListInterface orderReturnItemPanel = null;
    //MaintainOrderReturnButtonPanel buttonPanel = null;
    final ListAdapterListModel<OrderHeader> orderListModel = new ListAdapterListModel<OrderHeader>();
    private String returnId = null;
    private String orderId = null;
    ControllerOptions options = null;
    Order order = null;
    ReturnStatusActionPopupButton returnStatusActionPopupButton;
    OrderReturnActionPopupButton orderReturnActionPopupButton;

    static public MaintainOrderReturnController runController(ControllerOptions options) {

        MaintainOrderReturnController controller = new MaintainOrderReturnController(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(MaintainOrderReturnController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(MaintainOrderReturnController.module, options.getDesktopPane());
        }
        return controller;
    }

    public String getCaption() {
        return "Order Returns";
    }

    protected MaintainOrderReturnController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
        if (options.getOrder() != null) {
            this.order = options.getOrder();
        } else if (options.getReturnId() != null) {
            this.returnId = options.getReturnId();
        }
    }

//    FindSupplierProductListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<SupplierProductComposite> invoiceCompositeListModel = new ListAdapterListModel<SupplierProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();
    private OrderReturnItemsListPanel orderReturnItemsListPanel = null;
    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new OrderReturnHeaderPanel(options);
        //options.put("PartyIdTextField", panel.panelFromPartyId.textIdField);
//        orderReturnItemPanel = new OrderReturnItemsListPanel();
        ControllerOptions tmpOptions = options.getCopy();
        tmpOptions.addDefaultPartyIdField(panel.panelFromPartyId.textIdField);
        orderReturnItemsListPanel = new OrderReturnItemsListPanel(tmpOptions);

        orderReturnItemPanel = orderReturnItemsListPanel;
        orderReturnItemViewPanel = new OrderReturnItemsViewListPanel();

//        panel.panelReturnItems.setLayout(new BorderLayout());
//        panel.panelReturnItems.add(BorderLayout.CENTER, orderReturnItemPanel.getPanel());
        setReturnPanelItems(orderReturnItemViewPanel);
//        buttonPanel = new MaintainOrderReturnButtonPanel();
//        panel.txtReturnId.addActionListener(new ProductIdLookupClickAction(this, parentFrame, desktopPane, ControllerOptions.getSession()));
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        //      OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        returnStatusActionPopupButton = new ReturnStatusActionPopupButton(panel.btnOrderReturn, this, ControllerOptions.getDesktopPane());
        copyToButton = new OrderReturnCopyToPopupButton(panel.btnCopyTo, this, ControllerOptions.getDesktopPane());
        orderReturnActionPopupButton = new OrderReturnActionPopupButton(panel.btnNewReturnOrderAction, this, options);

        //party selection button
        ActionListener newPartyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                Debug.logInfo("newPartyAction billFromPartyId: " + validator.getField().getText(), module);

                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                            //PartyIdVerifyValidator 
                        //        validator = (PartyIdVerifyValidator) e.getSource();
                        
                        /* if (UtilValidate.isEmpty(paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.getText())) {
                         paymentEntryHeaderPanel.panelPartyFromIdPicker.textIdField.setText(ControllerOptions.getSession().getCompanyPartyId());
                         }*/
                        if (orderId != null) {
                                //newPaymentToOrder(orderId);
                            //setInitialFocusField(paymentEntryHeaderPanel.txtPaymentAmount);
                             Debug.logInfo("panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId(): 1" + panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId(), module);                            
                            panel.panelFromPartyId.setEnabled(false);
                        } else if (returnId != null) {
                            
                            panel.panelFromPartyId.setEnabled(false);
                            panel.returnPickerEditPanel.setEnable(false);
                        } else{
                            String returnFromPartyId = validator.getField().getText();
                             Debug.logInfo("panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId(): " + panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId(), module);
                            if (panel.getReturnHeaderComposite()!= null) {
                                String partyId = panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId();
                                panel.getReturnHeaderComposite().getReturnHeader().setfromPartyId(returnFromPartyId);
                                loadBillingAccounts();                                                                
                            } 
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(MaintainOrderReturnController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(panel.panelFromPartyId.textIdField, ControllerOptions.getSession());
        piValidator1.addActionListener(newPartyAction);
        panel.panelFromPartyId.textIdField.setInputVerifier(piValidator1);

        //order selection button
        ActionListener productIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductIdVerifyValidator) {
                    ProductIdVerifyValidator validator = (ProductIdVerifyValidator) e.getSource();
                    String productId = validator.getField().getText();
                    Debug.logInfo("orderId: " + orderId, module);
                    loadOrderReturn(productId);
                }
            }
        };

        ActionListener orderReturnStatusChange = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrderReturn(e.getActionCommand());
            }
        };
        returnStatusActionPopupButton.acceptOrderReturnAction.addActionListener(orderReturnStatusChange);
        returnStatusActionPopupButton.cancelOrderReturnAction.addActionListener(orderReturnStatusChange);
        returnStatusActionPopupButton.completeOrderReturnAction.addActionListener(orderReturnStatusChange);
        returnStatusActionPopupButton.manualOrderReturnAction.addActionListener(orderReturnStatusChange);
        returnStatusActionPopupButton.receiveOrderReturnAction.addActionListener(orderReturnStatusChange);

        panel.btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        orderReturnActionPopupButton.newOrderReturnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.clearDialogFields();
                orderReturnItemPanel.clearDialogFields();
                orderReturnItemViewPanel.clearDialogFields();
                setNewReturnOrder();
            }
        });

        //orderReturnActionPopupButton
        panel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });

        panel.btnSaveOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    getDialogFields();
                    /*for(ReturnItemComposite returnItemComposite: orderItemCompositeListModel.getList()){
                     if(returnItemComposite.getReturnQty().compareTo(BigDecimal.ZERO) > 0){
                    
                     }
                     }*/
                    //LoadOrderReturnWorker.saveOrderReturnHeader(panel.getReturnHeaderComposite().getReturnHeader(), ControllerOptions.getSession());
                    LoadOrderReturnWorker.saveOrderReturnHeaderAndItems(panel.getReturnHeaderComposite(), ControllerOptions.getSession());
                    panel.setReturnHeaderComposite(panel.getReturnHeaderComposite());
                    setDialogFields();
                    setOrderReturnActions(panel.getReturnHeaderComposite());
//                loadOrders();
                } catch (Exception ex) {
                    Logger.getLogger(MaintainOrderReturnController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        ProductIdVerifyValidator prodValidator = new ProductIdVerifyValidator(panel.txtReturnId, ControllerOptions.getSession());
//        prodValidator.addActionListener(productIdTextChangeAction);
//        panel.txtReturnId.setInputVerifier(prodValidator);

        tableDataChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof PurchaseOrderTableModel
                        && e instanceof RowColumnActionEvent) {

                    try {
                        final RowColumnActionEvent event = (RowColumnActionEvent) e;
                        int row = event.getRow();
                        ReturnItemComposite orderItem = orderItemCompositeListModel.getElementAt(row);

                        Debug.logInfo("Before item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getorderId()
                                + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        Debug.logInfo("event.getCol(): " + event.getCol(), module);
                        if (event.getCol() == PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX.getColumnIndex()) {
                            orderItem.getShoppingCartItem().setIsModifiedPrice(true);
                            orderItem.getShoppingCartItem().setBasePrice(orderItem.getOrderItem().getunitPrice());

                            Debug.logInfo("PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        } else if (event.getCol() == PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX.getColumnIndex()) {
//                            orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), panel.getOrder().getShopingCart());
                            Debug.logInfo("PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        }

                        //now sink the values back again..
                        orderItem.getOrderItem().setunitPrice(orderItem.getShoppingCartItem().getBasePrice());
                        orderItem.getOrderItem().setquantity(orderItem.getShoppingCartItem().getQuantity());

                        Debug.logInfo("After item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getorderId()
                                + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                                + "orderItem.getOrderItem().getquantityPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
//                        panel.setDialogTotals();
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };

        orderReturnItemPanel.getOrderEntryTableModel().clearActionListener();
        orderReturnItemPanel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);

        if (orderReturnItemPanel.getFindBtn() != null) {
            orderReturnItemPanel.getFindBtn().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String orderId = orderReturnItemPanel.getOrderId();
                    String invoiceId = orderReturnItemPanel.getInvoiceId();
                    Debug.logInfo("invoiceId : " + invoiceId, module);
                    if (UtilValidate.isNotEmpty(orderId)) {
                        loadOrder(orderId);
                    } else if (UtilValidate.isNotEmpty(invoiceId)) {
                        loadInvoice(invoiceId);
                    }
                }
            });
        }

        panel.jTabbedPane1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 1) {
                        if ("RETURN_REQUESTED".equals(panel.getReturnHeaderComposite().getReturnHeader().getstatusId())) {
                            setReturnPanelItems(orderReturnItemPanel);

                            //final boolean isNeedsLoad = orderReturnItemPanel.getReturnHeaderComposite().getReturnHeader().getfromPartyId().equals(
                            //        panel.getReturnHeaderComposite().getReturnHeader().getfromPartyId());
                            //loadOrders();
                        } else {
                            setReturnPanelItems(orderReturnItemViewPanel);
                        }
                        System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                    }
                }
            }
        });
        
        if (order != null) {
            setNewReturnOrder(order);
            orderReturnItemsListPanel.panelOrderIdPicker.setEnabled(false);
            orderReturnItemsListPanel.panelOrderIdPicker.textIdField.setEnabled(false);
            orderReturnItemsListPanel.panelOrderIdPicker.btnHeaderPatryId.setEnabled(false);
            orderReturnItemsListPanel.btnFind.setEnabled(false);
            String orderId = order.getOrderId();
            orderReturnItemsListPanel.panelOrderIdPicker.textIdField.setText(orderId);
            if (UtilValidate.isNotEmpty(orderId)) {
                loadOrder(orderId);
            }
        } else if (returnId != null) {
            loadOrderReturn(returnId);
        } else {
            setNewReturnOrder();
        }
    }
    ActionListener tableDataChangeAction = null;

    protected void setReturnPanelItems(OrderReturnItemListInterface orderReturnPanel) {
        panel.panelReturnItems.removeAll();
        panel.panelReturnItems.setLayout(new BorderLayout());
        panel.panelReturnItems.add(BorderLayout.CENTER, orderReturnPanel.getPanel());
        panel.panelReturnItems.repaint();
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    protected void setSizeAndLocation(JDialog dlg) {
        int y = 10;
        int x = 50;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
        Debug.logError(" location set", module);
    }

    public void loadOrderReturn(String returnId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ReturnHeaderComposite returnHeaderComposite = LoadOrderReturnWorker.loadReturnHeaderComposite(returnId, ControllerOptions.getSession());
        if (returnHeaderComposite != null) {
            //clear dialog
            panel.clearDialogFields();

            //set order
            panel.setReturnHeaderComposite(returnHeaderComposite);
            setOrderReturnActions(returnHeaderComposite);
            loadBillingAccounts();
            //update the dialog
            setDialogFields();
            setReturnItems(returnHeaderComposite.getOrderReturnItemsList().getList());
        } else {
            OrderMaxOptionPane.showMessageDialog(null, "Product not found: " + returnId, "Load Product", JOptionPane.YES_NO_OPTION);
        }
    }
    final ListAdapterListModel<ReturnItemComposite> orderItemCompositeListModel = new ListAdapterListModel<ReturnItemComposite>();

    public void loadOrder(String orderId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        Map<String, Object> findOption = new HashMap<String, Object>();
        findOption.put("orderId", orderId);

        List<ReturnItemComposite> returnableItems = LoadOrderReturnWorker.getReturnableItems(findOption, ControllerOptions.getSession());
        setReturnItems(returnableItems);
    }

    public void loadInvoice(String invoiceId) {

        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            InvoiceComposite invoice = LoadInvoiceWorker.loadInvoice(invoiceId, ControllerOptions.getSession());
            Set<String> orderIdList = LoadOrderWorker.getInvoiceOrderIds(invoice.getInvoice().getGenericValueObj());
            for (String orderId : orderIdList) {
                Map<String, Object> findOption = new HashMap<String, Object>();
                findOption.put("orderId", orderId);
                Debug.logInfo("loadInvoice - orderId : " + orderId, module);
                List<ReturnItemComposite> returnableItems = LoadOrderReturnWorker.getReturnableItems(findOption, ControllerOptions.getSession());
                setReturnItems(returnableItems);
            }
        } catch (Exception ex) {
            Logger.getLogger(MaintainOrderReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//static public InvoiceComposite loadInvoice(String invoiceId, XuiSession ControllerOptions.getSession()) throws Exception {

    public void setReturnItems(List<ReturnItemComposite> returnableItems) {

        //clear dialog
        //orderReturnItemPanel.clearDialogFields();
        orderItemCompositeListModel.clear();
        if (returnableItems != null) {
            orderItemCompositeListModel.addAll(returnableItems/*order.getOrderItemsList().getList()*/);

            orderReturnItemPanel.setReturnHeaderComposite(panel.getReturnHeaderComposite());
            orderReturnItemPanel.getOrderEntryTableModel().clearActionListener();
            orderReturnItemPanel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            //load table
            orderReturnItemPanel.reloadItemDataModel(orderItemCompositeListModel);
            panel.getReturnHeaderComposite().setOrderReturnItemsList(orderItemCompositeListModel);
            //update the dialog
            orderReturnItemPanel.setDialogFields();

            orderReturnItemViewPanel.setReturnHeaderComposite(panel.getReturnHeaderComposite());
            orderReturnItemViewPanel.getOrderEntryTableModel().clearActionListener();
            orderReturnItemViewPanel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            //load table
            orderReturnItemViewPanel.reloadItemDataModel(orderItemCompositeListModel);

            //update the dialog
            orderReturnItemViewPanel.setDialogFields();
        }
    }

    @Override
    public void setProductId(String returnId) {
        loadOrderReturn(returnId);
    }

    @Override
    public String getClassName() {
        return module;
    }

    protected void setNewReturnOrder() {
        ReturnHeaderComposite rhc = null;

        if (options.isSalesReturn()) {
            rhc = LoadOrderReturnWorker.getNewSalesReturnHeaderComposite("CUSTOMER_RETURN");
            Debug.logInfo("sales return: ", module);
        } else {
            rhc = LoadOrderReturnWorker.getNewPurchaseReturnHeaderComposite("VENDOR_RETURN");
            Debug.logInfo("purchase return: ", module);
        }
        Debug.logInfo("rhc.getReturnHeader.getreturnHeaderTypeId(): " + rhc.getReturnHeader().getreturnHeaderTypeId(), module);
        panel.setReturnHeaderComposite(rhc);
        setOrderReturnActions(rhc);
        loadBillingAccounts();
        //update the dialog
        setDialogFields();
        rhc.setOrderReturnItemsList(new ListAdapterListModel<ReturnItemComposite>());
        setReturnItems(rhc.getOrderReturnItemsList().getList());
    }

    protected void setNewReturnOrder(Order order) {
        ReturnHeaderComposite rhc = null;

        rhc = LoadOrderReturnWorker.getNewReturnHeaderComposite(order);
        Debug.logInfo("rhc.getReturnHeader.getreturnHeaderTypeId(): " + rhc.getReturnHeader().getreturnHeaderTypeId(), module);
        panel.setReturnHeaderComposite(rhc);
        setOrderReturnActions(rhc);
        loadBillingAccounts();        
        //update the dialog
        setDialogFields();
        orderReturnItemsListPanel.panelOrderIdPicker.textIdField.setText(order.orderHeader.getOrderId());
        rhc.setOrderReturnItemsList(new ListAdapterListModel<ReturnItemComposite>());
        setReturnItems(rhc.getOrderReturnItemsList().getList());
    }

    void loadOrders() {

        Map<String, Object> svcCtx = panel.getOptions();
        List<GenericValue> orderList = LoadSalesOrderWorker.getOrderList(svcCtx, ControllerOptions.getSession());
        List<OrderHeader> orderArrayList = new ArrayList<OrderHeader>();
        if (orderList != null) {
            for (GenericValue genValue : orderList) {
                OrderHeader orderHeader = new OrderHeader();
                orderHeader.setOrderId(genValue.getString("orderId"));
                orderHeader.setOrderDate((java.sql.Timestamp) genValue.get("orderDate"));
//                orderHeader.setOrderName("Test");
                orderArrayList.add(orderHeader);
            }
        }
        orderListModel.addAll(orderArrayList);
//        orderReturnItemPanel.getOrderComboModel().setDataList(orderListModel.getList());

        orderReturnItemPanel.setReturnHeaderComposite(panel.getReturnHeaderComposite());

        JGenericComboBoxSelectionModel<OrderHeader> orderComboModel = new JGenericComboBoxSelectionModel<OrderHeader>(panel.panelNeedsInventoryReserve, orderArrayList, DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY);
    }

    public void setOrderReturnActions(ReturnHeaderComposite returnHeader) {

        invoiceCompositeListModel.clear();

        copyToButton.loadPopMenu(returnHeader);
        //printButtonPopup.loadPopMenu(order);
        //orderActionPopupButton.loadPopMenu(order);
        returnStatusActionPopupButton.loadPopMenu(returnHeader);
        orderReturnActionPopupButton.loadPopMenu(returnHeader);

        /* if (order != null && order.getOrderId() != null && order.getOrderId().isEmpty() == false) {

         if ("ORDER_CREATED".equals(order.getOrderStatusId())) {
         boolean isEnabled = true;
         panel.btnSaveOrder.setEnabled(isEnabled);
         }

         if ("ORDER_APPROVED".equals(order.getOrderStatusId())) {
         boolean isEnabled = true;
         panel.btnSaveOrder.setEnabled(isEnabled);

         }

         if ("ORDER_COMPLETED".equals(order.getOrderStatusId())
         || "ORDER_CANCELLED".equals(order.getOrderStatusId())) {
         boolean isEnabled = false;
         panel.btnSaveOrder.setEnabled(isEnabled);
         }

         }*/
    }

    public void loadBillingAccounts() {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);
            ReturnHeaderComposite returnHeaderComposite = panel.getReturnHeaderComposite();
            List<BillingAccount> billingAccountList = LoadBillingAccountWorker.getPartyBillingAccountList(
                    returnHeaderComposite.getReturnHeader().getcurrencyUomId(),
                    returnHeaderComposite.getReturnHeader().getfromPartyId(),
                    ControllerOptions.getSession().getDelegator());

            BillingAccount billingAccount = new BillingAccount();
            billingAccount.setdescription("<None>");
            billingAccount.setbillingAccountId(null);
            billingAccountList.add(billingAccount);
            panel.billingAccountComboBox.setDataList(billingAccountList);
            if(billingAccountList.size() > 0){
                panel.billingAccountComboBox.setSelectedItem(billingAccountList.get(0));
            }
            else{
                panel.billingAccountComboBox.setSelectedItem(billingAccount);
            }
            /*List<Map<String, Object>> billingAccountListMap = BillingAccountWorker.makePartyBillingAccountList(ControllerOptions.getSession().getUserLogin(),
             paymentEntryHeaderPanel.uomComboBox.getSelectedItem().getuomId(),
             paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText(),
             delegator, ControllerOptions.getSession().getDispatcher());

             for (Map<String, Object> list : billingAccountListMap) {
             for (Map.Entry<String, Object> entryDept : list.entrySet()) {
             if (entryDept.getEntityValue() != null) {
             Debug.logInfo("Key : " + entryDept.getEntityId() + " Value: " + entryDept.getEntityValue().toString(), module);
             }
             }
             }
             */
//                        if (Integer.parseInt(paymentEntryHeaderPanel.panelCustomerPartyIdFromPicker.textIdField.getText()) <= 0) {
           /* JOptionPane.showMessageDialog(null,
             "Error: Please enter number bigger than 0", "Error Message",
             JOptionPane.ERROR_MESSAGE);*/
        } catch (GeneralException ex) {
            Logger.getLogger(CustomerPaymentInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getDialogFields() {
        panel.getDialogFields();
//        panelShippingItem.getDialogField();
//        panelShippingGroup.getDialogField();
    }

    void setDialogFields() {
        panel.setDialogFields();
//        panelShippingItem.setDialogField();
        //       panelShippingGroup.setDialogField();
    }

    void clearDialogFields() {
        panel.clearDialogFields();
//        panelShippingItem.setDialogField();
        //       panelShippingGroup.setDialogField();
    }

    @Override
    public ReturnHeaderComposite getOrderReturn() {
        //getDialogFields();
        return panel.getReturnHeaderComposite();
    }
}
