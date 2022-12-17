/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.salesorder;

import com.openbravo.data.gui.MdiMessageInf;
import com.openbravo.data.gui.MessageInf;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import mvc.controller.ApproveOrderAction;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import mvc.controller.LoadReceiveInventoryCompositeWorker;
import mvc.controller.LoadSalesOrderWorker;
import mvc.controller.OrderIdVerifyValidator;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.QuickShipEntireOrderAction;
import mvc.controller.SavePurchaseOrderWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.PurchaseOrderTableModel;
import mvc.model.table.SalesOrderTableModel;
import mvc.view.OrderCompositeOverviewPanel;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TwoPanelContainerPanel;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.Invoice;
import org.ofbiz.ordermax.invoice.salesinvoice.GenerateSalesInvoiceAction;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.OrderActionPopupButton;
import org.ofbiz.ordermax.orderbase.OrderCopyToPopupButton;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.orderbase.OrderPrintPopupButton;
import org.ofbiz.ordermax.orderbase.OrderStatusActionPopupButton;
import org.ofbiz.ordermax.orderbase.PanelOrderTerms;
import org.ofbiz.ordermax.orderbase.PanelShippingGroup;
import org.ofbiz.ordermax.orderbase.PanelShippingItem;
import org.ofbiz.ordermax.orderbase.PanelShippingOptions;
import org.ofbiz.ordermax.orderbase.PaymentAmountInterface;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.SimpleOrderButtonPanel;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.party.contacts.SelectAddressPanel;
import org.ofbiz.ordermax.product.FindProductListController;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderEntryKeyTableModel;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class SalesOrderController extends BaseMainScreen
        implements ItemListInterface<ShipmentReceiptComposite>,
        PartyIdInterface,
        OrderIdInterface,
        PaymentAmountInterface,
        OrderActionInterface {

    public static final String module = SalesOrderController.class.getName();
    public SalesOrderEnteryPanel1 panel = null;
    public final String caption = "Sales Order";
    public PosScreen pos = null;
    protected String orderId = null;
    final ListAdapterListModel<OrderItemComposite> orderItemCompositeListModel = new ListAdapterListModel<OrderItemComposite>();
    ControllerOptions options = null;

    static public SalesOrderController runController(ControllerOptions options) {

        SalesOrderController controller = new SalesOrderController(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(SalesOrderController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(SalesOrderController.module, options.getDesktopPane());
        }
        return controller;
    }

    SimpleOrderButtonPanel buttonPanel = null;
    private OrderCompositeOverviewPanel orderCompositeOverviewPanel = null;
    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<InvoiceComposite> invoicesListModel = new ListAdapterListModel<InvoiceComposite>();
    //final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    final private ListAdapterListModel<Order> orderCompositeListModel = new ListAdapterListModel<Order>();
    OrderCopyToPopupButton copyToButton = null;
    OrderPrintPopupButton printButtonPopup = null;
    OrderActionPopupButton orderActionPopupButton = null;
    OrderStatusActionPopupButton orderStatusActionPopupButton;

    OrderIdVerifyValidator oiValidator = null;
    OrderIdVerifyValidator oiValidator1 = null;
    ApproveOrderAction statusApprovedAction = null;
    PanelShippingGroup panelShippingGroup = null;
    PanelShippingItem panelShippingItem = null;
    PanelShippingOptions panelShippingOptions = null;
    public PanelOrderTerms panelTerms = null;

    protected SalesOrderController(ControllerOptions options) {
        super(options.getSession());
        this.options = options;
        this.orderId = options.getOrderId();
    }

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {

        width = 1000;
        height = 650;
        panel = new SalesOrderEnteryPanel1(ControllerOptions.getSession(), options);
        panel.setupEditOrderTable();
        panel.reloadItemDataModel(orderItemCompositeListModel);

        panel.setSize(width, height);
        Rectangle rect = panel.getBounds();
        rect.x = rect.x + 20;
        rect.y = rect.y + 20;
//        panel.setBounds(rect);

        orderCompositeOverviewPanel = new OrderCompositeOverviewPanel();
        orderCompositeOverviewPanel.setInvoiceCompositeList(orderCompositeListModel);

        panel.panelHistory.setLayout(new BorderLayout());
        panel.panelHistory.add(BorderLayout.CENTER, orderCompositeOverviewPanel);

        panelTerms = new PanelOrderTerms();
        panel.panelTerms.setLayout(new BorderLayout());
        panel.panelTerms.add(BorderLayout.CENTER, panelTerms);

        //party selection button
        ActionListener newPartyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                        PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                        Debug.logInfo("billToPartyId: " + validator.getField().getText(), module);
                        if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {

//                            Debug.logInfo("panel.getOrder().getPartyId(): " + panel.getOrder().getPartyId(), module);
                            String billToPartyId = validator.getField().getText();
                            if (panel.getOrder() != null && UtilValidate.isNotEmpty(panel.getOrder().getPartyId()) && panel.getOrder().getPartyId().equals(billToPartyId)) {
                                Debug.logInfo("billToPartyId old: " + validator.getField().getText(), module);
                                loadFinancialData(billToPartyId);
                                loadOrderList(billToPartyId);
                                return;
                            } else {
                                Debug.logInfo("billToPartyId new: " + validator.getField().getText(), module);
                                String billFromPartyId = ControllerOptions.getSession().getCompanyPartyId();
                                newOrder(billToPartyId, billFromPartyId);
                                //set order actions
                                setOrderToActions(panel.getOrder());

                                loadFinancialData(billToPartyId);
                                loadOrderList(billToPartyId);
                                setCaption(contFrame);
                            }
                        } else {
                            Debug.logInfo("billToPartyId something wrong: " + validator.getField().getText(), module);
                            //clear dialog
                            panel.clearDialogFields();
                            invoiceCompositeListModel.clear();
                            buttonPanel.btnSaveOrder.setEnabled(true);
                            buttonPanel.btnCancelOrder.setEnabled(false);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        PartyIdVerifyValidator piValidator = new PartyIdVerifyValidator(panel.txtLineItemPartyId, ControllerOptions.getSession());
        piValidator.addActionListener(newPartyAction);
        PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(panel.panelPartyIdPicker.textIdField, ControllerOptions.getSession());
        piValidator1.addActionListener(newPartyAction);
        panel.txtLineItemPartyId.setInputVerifier(piValidator);
        panel.panelPartyIdPicker.textIdField.setInputVerifier(piValidator1);

        //order selection button
        ActionListener orderIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof OrderIdVerifyValidator) {
                    OrderIdVerifyValidator validator = (OrderIdVerifyValidator) e.getSource();
                    String orderId = validator.getField().getText();
                    Debug.logInfo("orderId: " + orderId, module);
                    loadOrder(orderId);
                    //set order actions
                    setOrderToActions(panel.getOrder());
                    Debug.logInfo("panel.getOrder(): " + panel.getOrder().getOrderId(), module);

                    loadFinancialData(panel.panelPartyIdPicker.textIdField.getText());
                    loadOrderList(panel.panelPartyIdPicker.textIdField.getText());
                    setCaption(contFrame);
                }
            }
        };

        oiValidator = new OrderIdVerifyValidator(panel.txtLineItemorderId, Order.ORDERTYPE_PURCHSEORDER, ControllerOptions.getSession());
        oiValidator.addActionListener(orderIdTextChangeAction);

        oiValidator1 = new OrderIdVerifyValidator(panel.panelOrderIdPicker.textIdField, Order.ORDERTYPE_SALESORDER, ControllerOptions.getSession());
        oiValidator1.addActionListener(orderIdTextChangeAction);

        panel.txtLineItemorderId.setInputVerifier(oiValidator);
        panel.panelOrderIdPicker.textIdField.setInputVerifier(oiValidator1);

        //remove order item from tabld
        panel.addChangeListener(this);
        panel.btnDeleteSelectedItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.order.getOrderStatusId().equals("ORDER_COMPLETED") == false) {
                    List<OrderItemComposite> list = panel.getOrderEntryTableModel().getSelectedRows();
                    for (OrderItemComposite ar : list) {
                        if (ar.getOrderItem().getproductId() != null
                                && ar.getOrderItem().getproductId().isEmpty() == false) {

                            removeOrderItem(ar);
                            Debug.logInfo("Delete Selected : " + ar.getOrderItem().getproductId(), module);

                        }
                    }
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "Order Completed can't delete items");
                }
            }
        });

        panel.productStoreCombo.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    Debug.logInfo("valueChanged ", "module");
                    if (panel.productStoreCombo.comboBoxModel.getSelectedItem() != null) {
                        panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
                        panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
                    }
                }
            }
        });
        panel.btnBillingAddressChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UtilValidate.isNotEmpty(panel.getOrder())) {
                    ControllerOptions options = XuiSession.getControllerOptions();
                    options.addRoleType("BILL_TO_CUSTOMER");
                    options.addOrderType("SALES_ORDER");
                    options.addOrderStatusType("ORDER_COMPLETED");
                    options.addRoleTypeParent("CUSTOMER");
                    options.put("X", Integer.decode("300"));
                    options.put("partyContactPurposeTypeId", "BILLING_LOCATION");
                    if (panel.getOrder().getContactMech("BILLING_LOCATION") != null) {
                        Debug.logInfo("order.getBillFromAccount():  try to  found", module);
                        String postalAddressMechId = panel.getOrder().getContactMech("BILLING_LOCATION");
                        options.put("postalAddressMechId", postalAddressMechId);
                    }
                    final SelectAddressPanel viewer = new SelectAddressPanel(options);
                    viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
                    options.setSimpleScreenInterface(viewer);
                    final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(options);
                    frame.addActionListener(
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                        if (viewer.getSelectedPostalContact() != null) {
                                            panel.getOrder().addContactMech("BILLING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
//                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                            panel.setDialogFields();
//                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                            Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                        }
                                    }
                                }
                            });

                    Debug.logInfo("didn't wait", module);
                }
            }
        });

        panel.btnDeliveryAddressChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UtilValidate.isNotEmpty(panel.getOrder())) {
                    ControllerOptions options = XuiSession.getControllerOptions();
                    options.addRoleType("BILL_TO_CUSTOMER");
                    options.addOrderType("SALES_ORDER");
                    options.addOrderStatusType("ORDER_COMPLETED");
                    options.addRoleTypeParent("CUSTOMER");
                    options.put("X", Integer.decode("300"));
                    options.put("partyContactPurposeTypeId", "SHIPPING_LOCATION");
                    if (panel.getOrder().getContactMech("SHIPPING_LOCATION") != null) {
                        Debug.logInfo("order.getBillFromAccount():  try to  found", module);
                        String postalAddressMechId = panel.getOrder().getContactMech("SHIPPING_LOCATION");
                        options.put("postalAddressMechId", postalAddressMechId);
                    }
                    final SelectAddressPanel viewer = new SelectAddressPanel(options);
                    viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
                    options.setSimpleScreenInterface(viewer);
                    final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(options);
                    frame.addActionListener(
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                        if (viewer.getSelectedPostalContact() != null) {
                                            panel.getOrder().addContactMech("SHIPPING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                            panel.setDialogFields();
//                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                            Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                        }
                                    }
                                }
                            });

                    Debug.logInfo("didn't wait", module);
                }
            }
        });

        panel.btnAddBulkProduct.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.printf("The user's name is '%s'.\n", module);
                Map<String, Object> seleVal = new HashMap<String, Object>();
                final FindProductListController findOrderListMain = new FindProductListController(options, XuiContainer.getSession());
//            TwoPanelNonSizableContainerDlg dlg = findOrderListMain.loadNonSizeableTwoPanelDialogScreen(PartyMainScreen.module, null);
//            Debug.logError("getUserProductSelection: " + dlg.getReturnStatus(), module);
                if (ControllerOptions.getDesktopPane() != null) {
                    final TwoPanelContainerPanel dlg = findOrderListMain.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
                    dlg.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {

                            List<ProductComposite> arrayList = findOrderListMain.getSelectedProductListArray();
                            int row = 0;
                            if (orderItemCompositeListModel.getSize() > 0) {
                                row = orderItemCompositeListModel.getSize() - 1;
                            }
                            for (ProductComposite prodCom : arrayList) {
                                Debug.logInfo("prodCom.getProduct().getproductId(): " + prodCom.getProduct().getproductId(), module);;
                                // if (prodCom.isSelected()) {

                                if (orderItemCompositeListModel.getSize() <= row) {
                                    addNewOrderItem();
                                }
                                if (orderItemCompositeListModel.getSize() > row) {

                                    String prodId = prodCom.getProduct().getproductId();
                                    String partyId = panel.panelPartyIdPicker.textIdField.getText();
                                    OrderItemComposite orderItem = orderItemCompositeListModel.getElementAt(row);
                                    Order order = panel.getOrder();
                                    try {
                                        LoadSalesOrderWorker.addProduct(order, orderItem, prodId, partyId);
                                    } catch (Exception ex) {
                                        Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    panel.setDialogTotals();
                                    row++;
                                    panel.scrollToVisible(panel.getTable(), row, 1);
                                }
                            }
                            addNewOrderItem();
                            panel.getTable().setRowSelectionInterval(row - 1, row - 1);
                        }
                    });
                }
            }
        });

        buttonPanel = new SimpleOrderButtonPanel();

        copyToButton = new OrderCopyToPopupButton(buttonPanel.btnCopyTo, this, ControllerOptions.getDesktopPane());
        printButtonPopup = new OrderPrintPopupButton(buttonPanel.btnPrintInventorySticker, this, ControllerOptions.getDesktopPane());
        orderActionPopupButton = new OrderActionPopupButton(buttonPanel.btnOrderAction, this, ControllerOptions.getDesktopPane());
//        orderActionPopupButton = new OrderActionPopupButton(buttonPanel.btnOrderAction, this, desktopPane, parentFrame);
        orderStatusActionPopupButton = new OrderStatusActionPopupButton(buttonPanel.btnCancelOrder, this, ControllerOptions.getDesktopPane());
//        OrderMaxUtility.addAPanelGrid(panel, );
        contFrame.getPanelDetail().setLayout(new BorderLayout());
        contFrame.getPanelDetail().add(BorderLayout.CENTER, panel);

        panel.panelButton.setLayout(new BorderLayout());
        panel.panelButton.add(BorderLayout.CENTER, buttonPanel);
        panel.panelButton.setVisible(true);

        panelShippingGroup = new PanelShippingGroup(options);
        panelShippingItem = new PanelShippingItem();
        panelShippingOptions = new PanelShippingOptions();

        panel.panelShippingGroup.setLayout(new BorderLayout());
        panel.panelShippingGroup.add(BorderLayout.CENTER, panelShippingGroup);
        panel.panelShippingItem.setLayout(new BorderLayout());
        panel.panelShippingItem.add(BorderLayout.CENTER, panelShippingItem);
        panel.panelShippingOption.setLayout(new BorderLayout());
        panel.panelShippingOption.add(BorderLayout.CENTER, panelShippingOptions);

        //PrintInventoryReceiptStickerAction val = new PrintInventoryReceiptStickerAction(PrintInventoryReceiptStickerAction.nameStr, ControllerOptions.getSession(), desktopPane, parentFrame, this);
        //buttonPanel.btnPrintInventorySticker.addActionListener(val);
//        OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

        /*        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         cancelButtonPressed();
         }
         });
         */
        orderActionPopupButton.newOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String billToPartyId = panel.panelPartyIdPicker.textIdField.getText();
                String billFromPartyId = ControllerOptions.getSession().getCompanyPartyId();;
                if (UtilValidate.isNotEmpty(billToPartyId)) {
                    invoiceCompositeListModel.clear();
//                orderListModel.clear();
                    newOrder(billToPartyId, billFromPartyId);
                    //set order actions
                    setOrderToActions(panel.getOrder());
                    buttonPanel.btnSaveOrder.setEnabled(true);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                    buttonPanel.btnCancelOrder.setEnabled(false);
                    panel.reloadItemDataModel(orderItemCompositeListModel);
                    setCaption(contFrame);
                } else {
                    OrderMaxOptionPane.showMessageDialog(
                            null, "Please select a customer");

                }
            }
        });

        ActionListener saveOrderActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.panelPartyIdPicker.textIdField.getText() != null
                        && panel.panelPartyIdPicker.textIdField.getText().isEmpty() == false) {
                    getDialogFields();
                    Order order = panel.getOrder();
//                SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
                    Debug.logInfo("before loadPersonsWorker.execute", module);
//                saveOrderWorker.execute();
                    SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                    Debug.logInfo("after loadPersonsWorker.execute", module);

                    //update the dialog
                    panel.setDialogFields();
                    //set order actions    
                    setOrderToActions(panel.getOrder());
                    setCaption(contFrame);
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "Cannot save. Please select customer id.");
                }
            }
        };
        //set and handle all dialog product actions
        setupProductActions();
        orderActionPopupButton.saveOrderButton.addActionListener(saveOrderActionListener);
        buttonPanel.btnSaveOrder.addActionListener(saveOrderActionListener);

        //shipment receipt action
        //copyToButton.quickShipEntireOrderAction = new QuickShipEntireOrderAction(QuickShipEntireOrderAction.nameStr, ControllerOptions.getSession(), desktopPane, parentFrame, orderListModel);
        orderActionPopupButton.quickShipEntireOrderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //RELOAD AFTER shipping
                loadOrder(panel.getOrder().getOrderId());
                //set order actions
                setOrderToActions(panel.getOrder());
                loadFinancialData(panel.panelPartyIdPicker.textIdField.getText());
                setCaption(contFrame);
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contFrame.okButtonPressed();
            }
        });

        //statusApprovedAction = new ApprovePurchaseOrderAction(orderListModel, ControllerOptions.getSession());
        //buttonPanel.btnApproveOrder.setAction(statusApprovedAction);
        //change the status of order
        ActionListener orderStatusChange = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrder(e.getActionCommand());
                //set order actions
                setOrderToActions(panel.getOrder());

                loadFinancialData(panel.panelPartyIdPicker.textIdField.getText());
                setCaption(contFrame);

            }
        };
        orderStatusActionPopupButton.orderApproveAction.addActionListener(orderStatusChange);
        orderStatusActionPopupButton.cancelOrderAction.addActionListener(orderStatusChange);
        orderStatusActionPopupButton.holdOrderAction.addActionListener(orderStatusChange);

        panel.tabbedPaneOrderPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    Debug.logInfo("tabeed checked: " + pane.getSelectedIndex(), module);
                    if (pane.getSelectedIndex() == 4) {
                        panel.setOrderSummary();
                    } else if (pane.getSelectedIndex() == 5) {
                        panel.createScene();
                    }
                }
            }
        });

        if (orderId != null) {
            loadOrder(orderId);
            //set order actions
            setOrderToActions(panel.getOrder());

            loadFinancialData(panel.panelPartyIdPicker.textIdField.getText());
            loadOrderList(panel.panelPartyIdPicker.textIdField.getText());
            setCaption(contFrame);
        } else {
            setOrderToActions(panel.getOrder());
            buttonPanel.btnSaveOrder.setEnabled(true);
//            buttonPanel.btnApproveOrder.setEnabled(false);
            buttonPanel.btnCancelOrder.setEnabled(false);

        }
//        panel.setProductListArray((ProductListArray) treePanel.getTreeDataList());
        setInitialFocusField(panel.getPartyTextField());
    }

    ActionListener tableDataChangeAction = null;

    public void loadOrderList(String partyId) {
        /*
         List<String> stausList = FastList.newInstance();

         orderCompositeListModel.clear();

         stausList.add("ORDER_CREATED");
         stausList.add("ORDER_HOLD");
         stausList.add("ORDER_PROCESSING");
         //        stausList.add("ORDER_COMPLETED");

         List<String> orderTypeId = FastList.newInstance();
         orderTypeId.add("SALES_ORDER");

         //            orderTypeId.add("PURCHASE_ORDER");
         Map<String, Object> svcCtx = FastMap.newInstance();

         Locale locale = Locale.getDefault();

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

         List<GenericValue> orderList = LoadSalesOrderWorker.getOrderList(svcCtx, ControllerOptions.getSession());
         if (orderList != null) {
         for (GenericValue genValue : orderList) {
         Order order = LoadPurchaseOrderWorker.loadOrder(genValue.getString("orderId"), ControllerOptions.getSession()) ;                
         /*
         Order order = new Order(genValue.getString("orderTypeId"));
         OrderHeader orderHeader = new OrderHeader();
         order.setOrderHeader(orderHeader);
         orderHeader.setOrderId(genValue.getString("orderId"));
         orderHeader.setCurrencyUom(genValue.getString("currencyUom"));
         orderHeader.setOrderDate(genValue.getTimestamp("orderDate"));
         orderHeader.setOrderName(genValue.getString("orderName"));
         orderHeader.setStatusId(genValue.getString("statusId"));
         orderHeader.setRemainingSubTotal(genValue.getBigDecimal("remainingSubTotal"));
         orderHeader.setGrandTotal(genValue.getBigDecimal("grandTotal"));
         orderHeader.setOrderTypeId(genValue.getString("orderTypeId"));

         GenericValue orderGenVal = OrderReadHelper.getOrderHeader(ControllerOptions.getSession().getDelegator(), genValue.getString("orderId"));
         OrderRolesList orderRolesList = LoadPurchaseOrderWorker.getOrderRolesList(orderGenVal, ControllerOptions.getSession());
         order.setOrderRolesList(orderRolesList);
         */
        /*       orderCompositeListModel.add(order);
         }
         }*/
    }

    public void newOrder(String billToPartyId, String billFromPartyId) {
        try {
            Debug.logWarning("billToPartyId: " + billToPartyId + " billFromPartyId: " + billFromPartyId, module);
            Account billFromAccount = PartyListSingleton.getAccount(billFromPartyId);
            Account billToAccount = PartyListSingleton.getAccount(billToPartyId);
            String productStoreId = panel.productStoreCombo.getSelectedItem().getproductStoreId();
            String facilityId = panel.productStoreCombo.getSelectedItem().getinventoryFacilityId();
            String currency = (String) ControllerOptions.getSession().getAttribute("currency");
            String shipmentMethodTypeId = "NO_SHIPPING";

            Order order = LoadSalesOrderWorker.newSalesOrder(ControllerOptions.getSession(), Order.ORDERTYPE_SALESORDER,
                    billToAccount, billFromAccount, productStoreId, facilityId, currency, shipmentMethodTypeId);

            OrderItemComposite orderItem = LoadSalesOrderWorker.newOrderItem(ControllerOptions.getSession());
            order.getOrderItemsList().add(orderItem);
            Debug.logWarning("Party changed - partyIdTextFieldFocusLost", module);
            //clear dialog
            panel.clearDialogFields();
            //set order
            panel.setOrder(order);
            //add
            orderItemCompositeListModel.clear();
            orderItemCompositeListModel.addAll(order.getOrderItemsList().getList());

            //update the dialog
            panel.setDialogFields();

            //setup table
            panel.getOrderEntryTableModel().clearActionListener();
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            //load table
            panel.reloadItemDataModel(orderItemCompositeListModel);  //                  }

            oiValidator.setPartyId(billFromPartyId);
            oiValidator1.setPartyId(billFromPartyId);

            //set order actions
            setOrderToActions(panel.getOrder());

        } catch (Exception ex) {
            Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeOrderItem(OrderItemComposite orderItem) {
        try {

            LoadSalesOrderWorker.removeOrderItem(panel.order, orderItem, ControllerOptions.getSession());

            orderItemCompositeListModel.clear();
            orderItemCompositeListModel.addAll(panel.order.getOrderItemsList().getList());

            panel.getOrderEntryTableModel().clearActionListener();
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);

            //load table
            panel.reloadItemDataModel(orderItemCompositeListModel);
            panel.repaint();

        } catch (Exception ex) {
            Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//set and handle all dialog product actions
    public void loadOrder(String orderId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        Debug.logInfo("1", module);
        Order order = LoadSalesOrderWorker.loadOrder(orderId, ControllerOptions.getSession());
        Debug.logInfo("1.5", module);
//        order.getOrderContactList().outputToDebug();
        Debug.logInfo("2", module);//        order.setShopingCart(shopingCart);
//        order.setOrderContactList(LoadSalesOrderWorker.getOrderContactMechList(orderId, ControllerOptions.getSession()));
        Debug.logInfo("3", module);
        //      order.getOrderContactList().outputToDebug();
        //clear dialog
        panel.clearDialogFields();
        Debug.logInfo("4", module);
        //set order
        panel.setOrder(order);

        LoadSalesOrderWorker.setShoppingCartItemToItemComposite(order, order);
//        loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel);
        orderItemCompositeListModel.clear();
        orderItemCompositeListModel.addAll(order.getOrderItemsList().getList());

        //load invoice composites
//        loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel);
        invoicesListModel.clear();
        invoicesListModel.addAll(LoadInvoiceWorker.loadSalesOrderComposites(order.getInvoiceIds(), ControllerOptions.getSession()));
        //update the dialog
        panel.setDialogFields();

        panel.getOrderEntryTableModel().clearActionListener();
        panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
        //load table
        panel.reloadItemDataModel(orderItemCompositeListModel);
        //add empty name
        addNewOrderItem();
    }

    public void setOrderToActions(Order order) {

//        orderListModel.clear();
        //      orderListModel.add(order);
        invoiceCompositeListModel.clear();

        copyToButton.loadPopMenu(order);
        printButtonPopup.loadPopMenu(order);
        orderActionPopupButton.loadPopMenu(order);
        orderStatusActionPopupButton.loadPopMenu(order);
        panelTerms.setOrder(order);
        panelShippingGroup.setOrder(order);
        if (order != null && order.getOrderId() != null && order.getOrderId().isEmpty() == false) {

            if ("ORDER_CREATED".equals(order.getOrderStatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_APPROVED".equals(order.getOrderStatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("ORDER_COMPLETED".equals(order.getOrderStatusId())
                    || "ORDER_CANCELLED".equals(order.getOrderStatusId())) {
                boolean isEnabled = false;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

        }
    }
    String invoiceTypeId = "SALES_INVOICE";

    public void loadFinancialData(String billToPartyId) {

        /*
         final ClassLoader cl = getClassLoader();
         Thread.currentThread().setContextClassLoader(cl);
        
         List<InvoiceComposite> invList;
         try {

         invoiceCompositeListModel.clear();

         invList = LoadInvoiceWorker.loadOutstandingInvoices(billToPartyId, invoiceTypeId, ControllerOptions.getSession());

         invoiceCompositeListModel.addAll(invList);
         OrderFinancialData orderFinancialData = new OrderFinancialData(invoiceCompositeListModel, OrderFinancialData.getCurrentDate());
         BigDecimal val = orderFinancialData.getTotalOutstanding();
         panel.editTotalBalance.setText(val.toString());

         val = orderFinancialData.getZeroTo29DaysTotal();
         panel.editCurrOutstanding.setText(val.toString());

         val = orderFinancialData.get30To59DaysTotal();
         panel.edit30Days.setText(val.toString());

         val = orderFinancialData.get60To89DaysTotal();
         panel.edit60Days.setText(val.toString());

         val = orderFinancialData.getMoreThan90DaysTotal();
         panel.edit90Days.setText(val.toString());

         Debug.logInfo("val.toString(): " + val.toString(), module);
         } catch (Exception ex) {
         Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
    }
    
    LookupActionListner listner = null;// new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, prodoptions);
    public void setupProductActions() {

        //order product Id selection click
        //party selection button
        ActionListener productIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    final RowColumnActionEvent event = (RowColumnActionEvent) e;
                    final JTextField textField = panel.getTxtProdIdTableTextField();
//                        Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
//                    Map<String, Object> genVal = LookupActionListner.getUserProductSelection();
                    ControllerOptions prodoptions = new ControllerOptions(options);
                    prodoptions.setDoubleClickCloseDialog();
                    prodoptions.put(ProductPickerEditPanel.editorId, textField);
                    listner = new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, prodoptions);

                    ActionListener selectChangeAction = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            processProductIdTextFieldChange(textField, event.getRow());
                            event.getTable().setValueAt(textField.getText(), event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex());
                            event.getTable().changeSelection(event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_INTERNALNAME_INDEX.getColumnIndex(), false, false);
                            event.getTable().requestFocus();
                            //listner.removeAll();
                        }
                    };
                    listner.addActionListener(selectChangeAction);
                    Map<String, Object> genVal = listner.getUserProductSelection();
                }
            }
        };
        panel.getProductTreeActionTableCellEditor()
                .addActionListener(productIdChangeAction);

        //order product Id selection click
        //party selection button
        ActionListener interactiveCellRenderAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("interactiveCellRenderAction  hasEmptyRow: " + hasEmptyRow(), module);
                Debug.logInfo("e.getSource(): " + e.getSource(), module);

                if (e.getSource() instanceof InteractiveRenderer
                        && e instanceof RowColumnActionEvent) {

                    InteractiveRenderer render = (InteractiveRenderer) e.getSource();
//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    final RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {
                        Debug.logInfo("hasEmptyRow : " + hasEmptyRow(), module);

                        if ((orderItemCompositeListModel.getSize() - 1) == event.getRow()
                                && !hasEmptyRow()) {

                            addNewOrderItem();

                        }

                        panel.setDialogTotals();
                        panel.highlightLastRow(event.getRow());
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }
        };

        panel.getInteractiveRenderer().addActionListener(interactiveCellRenderAction);

        panel.getTable().addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (!hasEmptyRow()) {
                }

            }

            @Override
            public void focusLost(FocusEvent e
            ) {
            }

        }
        );

        tableDataChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof SalesOrderTableModel
                        && e instanceof RowColumnActionEvent) {
                    OrderItemComposite orderItem = null;
                    try {
                        final RowColumnActionEvent event = (RowColumnActionEvent) e;
                        int row = event.getRow();
                        orderItem = orderItemCompositeListModel.getElementAt(row);

                        Debug.logInfo("Before item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getorderId()
                                + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        Debug.logInfo("event.getCol(): " + event.getCol(), module);
                        if (event.getCol() == SalesOrderTableModel.Columns.ORDER_PRICE_INDEX.getColumnIndex()) {
                            if (orderItem.getShoppingCartItem() != null) {
                                orderItem.getShoppingCartItem().setIsModifiedPrice(true);
                                orderItem.getShoppingCartItem().setBasePrice(orderItem.getOrderItem().getunitPrice());
                                Debug.logInfo("SalesOrderTableModel.ORDER_PRICE_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                            }
                        } else if (event.getCol() == SalesOrderTableModel.Columns.ORDER_QTY_INDEX.getColumnIndex()) {
                            // try {
                            if (orderItem.getShoppingCartItem() != null) {
                                orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), panel.getOrder().getShopingCart());
                                Debug.logInfo("SalesOrderTableModel.ORDER_QTY_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                            }
                            //  } catch (CartItemModifyException ex) {
                            //     JOptionPane.showMessageDialog(null, ex.getMessage());
                            //  }
                        }

                        //now sink the values back again..
                        if (orderItem.getShoppingCartItem() != null) {
                            orderItem.getOrderItem().setunitPrice(orderItem.getShoppingCartItem().getBasePrice());
                            orderItem.getOrderItem().setquantity(orderItem.getShoppingCartItem().getQuantity());

                            Debug.logInfo("After item prodId: " + orderItem.getOrderItem().getproductId()
                                    + "order Id: " + orderItem.getOrderItem().getorderId()
                                    + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                                    + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                                    + "orderItem.getOrderItem().getquantityPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        }
                        panel.setDialogTotals();
                    } catch (Exception ex) {
                        if (orderItem != null) {
                            orderItem.getOrderItem().setproductId("");
                            orderItem.getOrderItem().setitemDescription("");
                            panel.order.removeOrderItem(orderItem);
                        }
                        MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
                        msg.show(panel);
                        Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            panel.order.removeCartItem(orderItem.getShoppingCartItem(), ControllerOptions.getSession().getDispatcher());
                                //orderItem.getShoppingCartItem()
                            //orderItem.getOrderItem().getorderId()
                            //     + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                            //     + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                            //     + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        } catch (CartItemModifyException ex1) {
                        //    MdiMessageInf msg1 = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
                        //    msg1.show(panel);
                        //    Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
        };

        panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);

        panel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "COLUMN_ENTERY_KEY");
        panel.getTxtProdIdTableTextField().getActionMap().put("COLUMN_ENTERY_KEY", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableCellAction(panel.getTxtProdIdTableTextField(), panel.getTable().getSelectedRow(), panel.getTable().getSelectedColumn());
            }
        });

        panel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "checkTab");
        panel.getTxtProdIdTableTextField().getActionMap().put("checkTab", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableCellAction(panel.getTxtProdIdTableTextField(), panel.getTable().getSelectedRow(), panel.getTable().getSelectedColumn());
            }
        });

        panel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "checkShiftTab");
        panel.getTxtProdIdTableTextField().getActionMap().put("checkShiftTab", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableCellAction(panel.getTxtProdIdTableTextField(), panel.getTable().getSelectedRow(), panel.getTable().getSelectedColumn());
            }
        });

        panel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "checkF2");
        panel.getTxtProdIdTableTextField().getActionMap().put("checkF2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                //tableCellAction(textField, table.getSelectedRow(), table.getSelectedColumn());
                LookupActionListner listner = new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, options);
                Map<String, Object> genVal = listner.getUserProductSelection();
                if (genVal != null) {
                    if (genVal.containsKey("Product")) {

                        if (panel != null) {

                            JTextField textField = panel.getTxtProdIdTableTextField();
                            if (textField != null) {
                                ProductComposite prod = (ProductComposite) genVal.get("Product");
                                textField.setText(prod.getProduct().getproductId());

                                RowColumnActionEvent event = (RowColumnActionEvent) e;

                                textField.setText(genVal.get("productId").toString());
                                Debug.logWarning("Order changed - product id text field: " + genVal.get("productId").toString(), module);
                                processProductIdTextFieldChange(textField, panel.getTable().getSelectedRow());
                                event.getTable().setValueAt(genVal.get("productId").toString(), panel.getTable().getSelectedRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex());
//                                    panel.getTable().setValueAt(genVal.get("productId").toString(), panel.getTable().getSelectedRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX);

                                event.getTable().changeSelection(panel.getTable().getSelectedRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_INTERNALNAME_INDEX.getColumnIndex(), false, false);
                                event.getTable().updateUI();
                                ((AbstractTableModel) event.getTable().getModel()).fireTableDataChanged();//fireTableRowsUpdated(event.getRow(), event.getRow());

//                                    panel.getTable().requestFocus();
                            }
                        } else {
//                        table.setValueAt(genVal.get("productId").toString(), row, column);
                        }
                    }
                }

            }
        });

        panel.getTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

    }

    public void addNewOrderItem() {

        Order order = panel.getOrder();//.getOrderItemsList().add(orderItem);
        if ("ORDER_COMPLETED".equals(order.getOrderStatusId()) == false) {
            OrderItemComposite orderItem = LoadSalesOrderWorker.newOrderItem(ControllerOptions.getSession());
            order.getOrderItemsList().add(orderItem);
            orderItemCompositeListModel.add(orderItem);
        }

    }

    public boolean hasEmptyRow() {
        if (orderItemCompositeListModel.getSize() <= 0) {
            return false;
        }

        OrderItemComposite OrderItemComposite = orderItemCompositeListModel.getElementAt(orderItemCompositeListModel.getSize() - 1);

        return UtilValidate.isEmpty(OrderItemComposite.getOrderItem().getproductId());

    }

    void setCaption(final ContainerPanelInterface contFrame) {

        contFrame.setCaption(getCaption());

    }

    void tableCellAction(final JTextField textField, int row, int col) {
        Debug.logInfo("tableCellAction product: " + textField.getText(), module);
        if (panel.getTable().getSelectedColumn() == OrderEntryKeyTableModel.ORDER_PROD_ID_INDEX && textField.getText().isEmpty() == false) {
            processProductIdTextFieldChange(textField, row);
            panel.getTable().setValueAt(textField.getText(), row, PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex());
            panel.getTable().changeSelection(row, PurchaseOrderTableModel.Columns.ORDER_PROD_INTERNALNAME_INDEX.getColumnIndex(), false, false);
            panel.getTable().requestFocus();
        }
    }

    private static final Border red = new LineBorder(Color.red);

    public void processProductIdTextFieldChange(final JTextField textField, int row) {
        try {
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            Debug.logInfo("selected product: " + textField.getText(), module);
            String prodId = textField.getText();
            String partyId = panel.panelPartyIdPicker.textIdField.getText();
            OrderItemComposite orderItem = orderItemCompositeListModel.getElementAt(row);
            Order order = panel.getOrder();
            LoadSalesOrderWorker.addProduct(order, orderItem, prodId, partyId);
            panel.setDialogTotals();
            textField.postActionEvent(); //inform the editor
        } catch (Exception ex) {
            panel.setDialogTotals();
            textField.setBorder(red);
            Debug.logError(ex, module);
            textField.setText("");
            OrderMaxOptionPane.showMessageDialog(panel, ex.getMessage());//.+(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);            
        }
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Java Technology Dive Log");
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(
                    new URL("http://i.imgur.com/6mbHZRU.png")));
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }

    public void setVisibleItems() {
    }

    public void setLimitRight() {
        boolean isEnabled = panel.isOrderEditable();
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCaption() {
        StringBuilder orderToStringBuilder = new StringBuilder();
        orderToStringBuilder.append(caption);
//        Debug.logInfo("panel != null" + UtilValidate.isNotEmpty(panel), module);
//        Debug.logInfo("panel.order != null" + UtilValidate.isNotEmpty(panel.order), module);        
//        Debug.logInfo("panel.order.getOrderId()" + UtilValidate.isNotEmpty(panel.order.getOrderId()), module);        

        if (panel != null && panel.order != null
                && UtilValidate.isNotEmpty(panel.order.getOrderId())) {

            orderToStringBuilder.append(" - Order Id[ ");
            orderToStringBuilder.append(panel.order.getOrderId());
//            if (panel.order.getOrderHeader().getOrderName() != null
//                    && panel.order.getOrderHeader().getOrderName().isEmpty() == false) {
//                orderToStringBuilder.append("(");
//                orderToStringBuilder.append(panel.order.getOrderHeader().getOrderName());
//                orderToStringBuilder.append(") ");
//            }
            orderToStringBuilder.append("]");
            Debug.logInfo("panel.order.getInvoiceIds().size(): " + panel.order.getInvoiceIds().size(), module);
            if (panel.order.getInvoiceIds().size() > 0) {
                orderToStringBuilder.append(" Invoice Id [");
                boolean first = true;
                for (String id : panel.order.getInvoiceIds()) {
                    if (first == false) {
                        orderToStringBuilder.append(",");
                    }
                    orderToStringBuilder.append(id);
                    first = false;

                }
                orderToStringBuilder.append("]");
            }

            /*sur            if (panel.statusItemComboModel != null) {
             String str = panel.statusItemComboModel.getSelectedItem().getdescription();
             orderToStringBuilder.append(" Status");
             orderToStringBuilder.append("[");
             orderToStringBuilder.append(str);
             orderToStringBuilder.append("]");
             }
             */
        } else {
            /*sur           orderToStringBuilder.append("[");
             orderToStringBuilder.append("New");
             orderToStringBuilder.append("] ");
             if (panel != null && panel.statusItemComboModel != null) {
             String str = panel.statusItemComboModel.getSelectedItem().getdescription();
             orderToStringBuilder.append(" Status");
             orderToStringBuilder.append("[");
             orderToStringBuilder.append(str);
             orderToStringBuilder.append("]");
             }*/
        }

        Debug.logInfo("orderToStringBuilder.toString() " + orderToStringBuilder.toString(), module);

        return orderToStringBuilder.toString();
    }

    QuickShipEntireOrderAction quickShipEntireOrderAction = null;
    GenerateSalesInvoiceAction generateSalesInvoiceAction = null;

    @Override
    public List<ShipmentReceiptComposite> getList() {
        ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(panel.getOrder(), ControllerOptions.getSession());
        return ric.getShipmentReceiptCompositeList().getList();
    }

    @Override
    public String getPartyId() {
        return panel.panelPartyIdPicker.textIdField.getText();
    }

    @Override
    public String getOrderId() {
        return panel.getOrder().getOrderId();
    }

    @Override
    public BigDecimal getPaymentAmount() {
        return new BigDecimal(panel.txtLineItemTotalSales.getText());

    }

    protected BigDecimal getTotalOutstanding() {
        BigDecimal value = new BigDecimal(0);
        for (InvoiceComposite ic : invoiceCompositeListModel.getList()) {
            value = value.add(ic.getOutstandingAmount());
            Debug.logInfo("value.toString(): " + value.toString(), module);

        }
        return value;
    }

    private void loadOrderComposites(Set<String> invoiceIds, ListAdapterListModel<InvoiceComposite> invoicesListModel) {

        invoicesListModel.clear();
        Map<String, String> valMap = new HashMap<String, String>();
        for (String invId : invoiceIds) {
            try {
                valMap.put("invoiceId", invId);
                GenericValue genVal = PosProductHelper.getGenericValueByKey("Invoice", valMap, ControllerOptions.getSession().getDelegator());
                InvoiceComposite comp = new InvoiceComposite();
                Invoice inv = new Invoice(genVal);
                comp.setInvoice(new Invoice(genVal));
                comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
                comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
                comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
                comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
                invoicesListModel.add(comp);

            } catch (Exception ex) {
                Logger.getLogger(PurchaseOrderController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void getDialogFields() {
        panel.getDialogFields();
        panelShippingItem.getDialogField();
        panelShippingGroup.getDialogField();
    }

    void setDialogFields() {
        panel.setDialogFields();
        panelShippingItem.setDialogField();
        panelShippingGroup.setDialogField();
    }

    @Override
    public Order getOrder() {
        getDialogFields();
        return panel.getOrder();
    }
}
