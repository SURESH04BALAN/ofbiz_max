/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.purchaseorder;


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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import static javax.swing.Action.NAME;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import mvc.controller.LoadPurchaseOrderWorker;
import mvc.controller.LoadReceiveInventoryCompositeWorker;
import mvc.controller.OrderIdVerifyValidator;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.SavePurchaseOrderWorker;
import mvc.controller.ShipmentReceiptAction;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.PurchaseOrderTableModel;
import mvc.view.OrderCompositeOverviewPanel;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface.LookupType;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.Invoice;
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
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class PurchaseOrderController extends BaseMainScreen
        implements ItemListInterface<ShipmentReceiptComposite>,
        PartyIdInterface,
        OrderIdInterface,
        PaymentAmountInterface,
        OrderActionInterface {

    public static final String module = PurchaseOrderController.class.getName();
    public PurchaseOrderEnteryPanel panel = null;
    public PanelOrderTerms panelTerms = null;
    public final String caption = "Purchase Order";
    public PosScreen pos = null;
    protected String orderId = null;
    final ListAdapterListModel<OrderItemComposite> orderItemCompositeListModel = new ListAdapterListModel<OrderItemComposite>();
    ControllerOptions options = null;

    static public PurchaseOrderController runController(ControllerOptions options) {

        PurchaseOrderController controller = new PurchaseOrderController(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(PurchaseOrderController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(PurchaseOrderController.module, options.getDesktopPane());
        }
        return controller;
    }

    SimpleOrderButtonPanel buttonPanel = null;
    private OrderCompositeOverviewPanel orderCompositeOverviewPanel = null;
    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<InvoiceComposite> invoicesListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    final private ListAdapterListModel<Order> orderCompositeListModel = new ListAdapterListModel<Order>();

    OrderIdVerifyValidator oiValidator = null;
    OrderIdVerifyValidator oiValidator1 = null;
    /*ApproveOrderAction statusApprovedAction = null;
     PrintPurchaseInvoiceAction printPurchaseInvoiceAction = null;
     PrintPickSlipAction printPurchasePickSlipAction = null;
     ViewEditDeliverScheduleInfoAction viewEditDeliverScheduleInfoAction = null;
     ViewOrderHistoryAction viewOrderHistoryAction = null;
     CreateNewShipGroupAction createNewShipGroupAction = null;
     CreateAsNewOrderAction createAsNewOrderAction = null;
     PayPurchaseOrderAction payPurchaseOrderAction = null;
     CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
     CreateReturnOrderAction createReturnPurchaseOrderAction = null;
     QuickRefundEntireSalesOrderAction quickRefundEntireOrderAction = null;
     */
    PanelShippingGroup panelShippingGroup = null;
    PanelShippingItem panelShippingItem = null;
    PanelShippingOptions panelShippingOptions = null;

    OrderCopyToPopupButton copyToButton = null;
    OrderPrintPopupButton printButtonPopup = null;
    OrderActionPopupButton orderActionPopupButton = null;
    OrderStatusActionPopupButton orderStatusActionPopupButton;
    int oldProductStoreSelectionIndex = -1;

    protected PurchaseOrderController(ControllerOptions options) {
        super(options.getSession());
        this.options = options;
        this.orderId = options.getOrderId();
    }

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {

        width = 1000;
        height = 650;

        panel = new PurchaseOrderEnteryPanel(ControllerOptions.getSession(), options);
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
                        Debug.logInfo("billFromPartyId: " + validator.getField().getText(), module);
                        if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
                            String billFromPartyId = validator.getField().getText();
                            if (panel.getOrder() != null && UtilValidate.isNotEmpty(panel.getOrder().getPartyId()) && panel.getOrder().getPartyId().equals(billFromPartyId)) {
                                return;
                            } else {
                                String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();;
                                newOrder(billFromPartyId, billToPartyId);
                                //set order actions
                                setOrderToActions(panel.getOrder());

                                loadFinancialData(billFromPartyId);
                                loadOrderList(billFromPartyId);
                                setCaption(contFrame);
                            }
                        } else {
                            //clear dialog
                            panel.clearDialogFields();
                            invoiceCompositeListModel.clear();
                            buttonPanel.btnSaveOrder.setEnabled(true);
                            buttonPanel.btnCancelOrder.setEnabled(false);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
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

        oiValidator1 = new OrderIdVerifyValidator(panel.panelOrderIdPicker.textIdField, Order.ORDERTYPE_PURCHSEORDER, ControllerOptions.getSession());
        oiValidator1.addActionListener(orderIdTextChangeAction);

        panel.txtLineItemorderId.setInputVerifier(oiValidator);
        panel.panelOrderIdPicker.textIdField.setInputVerifier(oiValidator1);


        /*        generatePurchaseInvoiceAction = new GeneratePurchaseInvoiceAction(ShipmentReceiptAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         printPurchaseInvoiceAction = new PrintPurchaseInvoiceAction(PrintPurchaseInvoiceAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), invoicesListModel);
         printPurchasePickSlipAction = new PrintPickSlipAction(PrintPickSlipAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         viewEditDeliverScheduleInfoAction = new ViewEditDeliverScheduleInfoAction(ViewEditDeliverScheduleInfoAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         viewOrderHistoryAction = new ViewOrderHistoryAction(ViewOrderHistoryAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         createNewShipGroupAction = new CreateNewShipGroupAction(CreateNewShipGroupAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         createAsNewOrderAction = new CreateAsNewOrderAction(CreateAsNewOrderAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         payPurchaseOrderAction = new PayPurchaseOrderAction(PayPurchaseOrderAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), this, this, this);
         createReplacementPurchaseOrderAction = new CreateReplacementOrderAction(CreateReplacementOrderAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         createReturnPurchaseOrderAction = new CreateReturnOrderAction(CreateReturnOrderAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         quickRefundEntireOrderAction = new QuickRefundEntireSalesOrderAction(QuickRefundEntireSalesOrderAction.nameStr, ControllerOptions.getSession(), options.getDesktopPane(), orderListModel);
         */
        //set and handle all dialog product actions
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

        panel.productStoreCombo.jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                Debug.logInfo("itemStateChanged 1", module);
                if (event.getStateChange() == ItemEvent.SELECTED && panel.getOrder() != null) {
                    boolean allowChange = false; //
                    Debug.logInfo("itemStateChanged 2", module);
                    if (panel.productStoreCombo.comboBoxModel.getSelectedItem() != null) {
                        Debug.logInfo("itemStateChanged 3", module);
                        if (panel.getOrder().size() > 0) {
                            Debug.logInfo("itemStateChanged 4", module);
                            if (panel.getOrder().size() == 1) {
                                Debug.logInfo("itemStateChanged 5", module);
                                ShoppingCartItem shoppingCartItem = panel.getOrder().findCartItem(0);
                                if (UtilValidate.isEmpty(shoppingCartItem.getProductId())) {
                                    Debug.logInfo("itemStateChanged 6", module);
                                    String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
                                    String billFromPartyId = panel.panelPartyIdPicker.textIdField.getText();
                                    oldProductStoreSelectionIndex = panel.productStoreCombo.jComboBox.getSelectedIndex();
                                    //invoiceCompositeListModel.clear();
                                    newOrder(billFromPartyId, billToPartyId);
                                } else {
                                    allowChange = true;
                                    /*                Debug.logInfo("itemStateChanged 7", module);
                                     if (oldProductStoreSelectionIndex < 0) {
                                        
                                     Debug.logInfo("itemStateChanged 8", module);
                                     panel.productStoreCombo.jComboBox.setSelectedIndex(0);
                                     } else {
                                     Debug.logInfo("itemStateChanged 9", module);
                                     panel.productStoreCombo.jComboBox.setSelectedIndex(oldProductStoreSelectionIndex);
                                     }*/
                                    Debug.logInfo("itemStateChanged 10", module);
                                }
                                Debug.logInfo("itemStateChanged 11", module);
                            } else {
                                allowChange = true;
                                Debug.logInfo("itemStateChanged 12", module);
                                /*try {
                                    panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
                                    panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
                                } catch (Exception ex) {
                                    MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
                                    msg.show(panel);
                                    if (oldProductStoreSelectionIndex < 0) {
                                        panel.productStoreCombo.jComboBox.setSelectedIndex(0);
                                    } else {
                                        panel.productStoreCombo.jComboBox.setSelectedIndex(oldProductStoreSelectionIndex);
                                    }
                                }*/
                            }
                        } else {
                            allowChange = true;
                            Debug.logInfo("itemStateChanged 13", module);
                        //    panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
                        //    panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
                        }
                    } else {
                        Debug.logInfo("itemStateChanged 14", module);
                        oldProductStoreSelectionIndex = panel.productStoreCombo.jComboBox.getSelectedIndex();
                    }
                    Debug.logInfo("itemStateChanged 15", module);

                    if (allowChange) {
                        Debug.logInfo("allowChange itemStateChanged 12", module);
                        try {
                            panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
                            panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
                            oldProductStoreSelectionIndex = panel.productStoreCombo.jComboBox.getSelectedIndex();                            
                        } catch (Exception ex) {
                            MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
                            msg.show(panel);
                            if (oldProductStoreSelectionIndex < 0) {
                                panel.productStoreCombo.jComboBox.setSelectedIndex(0);
                            } else {
                                panel.productStoreCombo.jComboBox.setSelectedIndex(oldProductStoreSelectionIndex);
                            }
                        }
                    }
                    /*if (!"Okay".equalsIgnoreCase(jTextField.getText())) {
                     if (oldProductStoreSelectionIndex < 0) {
                     panel.productStoreCombo.jComboBox.setSelectedIndex(0);
                     } else {
                     panel.productStoreCombo.jComboBox.setSelectedIndex(oldProductStoreSelectionIndex);
                     }
                     } else {
                     oldProductStoreSelectionIndex = panel.productStoreCombo.jComboBox.getSelectedIndex();
                     }*/
                }
            }
        });

        /*
         panel.productStoreCombo.selectionModel.addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {

         if (!e.getValueIsAdjusting() && panel.getOrder() != null) {
         ListSelectionModel lsm = (ListSelectionModel) e.getSource();
         Debug.logInfo("valueChanged ", "module");
         if (panel.productStoreCombo.comboBoxModel.getSelectedItem() != null) {
         if (panel.getOrder().size() > 0) {
         if (panel.getOrder().size() == 1) {
         ShoppingCartItem shoppingCartItem = panel.getOrder().findCartItem(0);
         if (UtilValidate.isEmpty(shoppingCartItem.getProductId())) {
         String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
         String billFromPartyId = panel.panelPartyIdPicker.textIdField.getText();
         //invoiceCompositeListModel.clear();

         newOrder(billFromPartyId, billToPartyId);
         }
         } else {
         try {
         panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
         panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
         } catch (Exception ex) {
         MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
         msg.show(panel);
         }
         }
         } else {
         panel.getOrder().setFacilityId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getinventoryFacilityId());
         panel.getOrder().setProductStoreId(panel.productStoreCombo.comboBoxModel.getSelectedItem().getproductStoreId());
         }
         }
         }
         }
         });*/
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
        //PrintInventoryReceiptStickerAction val = new PrintInventoryReceiptStickerAction(PrintInventoryReceiptStickerAction.nameStr1, ControllerOptions.getSession(), options.getDesktopPane(), this);
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
                String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();
                String billFromPartyId = panel.panelPartyIdPicker.textIdField.getText();
                invoiceCompositeListModel.clear();

                newOrder(billFromPartyId, billToPartyId);
                //set order actions
                setOrderToActions(panel.getOrder());
                buttonPanel.btnSaveOrder.setEnabled(true);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(false);
                panel.reloadItemDataModel(orderItemCompositeListModel);
                setCaption(contFrame);
            }
        });
        setupProductActions();
        buttonPanel.btnSaveOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.panelPartyIdPicker.textIdField.getText() != null
                        && panel.panelPartyIdPicker.textIdField.getText().isEmpty() == false) {
                    panel.getDialogFields();
                    panelShippingItem.getDialogField();
                    panelShippingGroup.getDialogField();
                    Order order = panel.getOrder();
                    Debug.logInfo("order.getPartyId(): " + order.getBillFromVendorPartyId(), module);
                    if (UtilValidate.isNotEmpty(order.getBillFromVendorPartyId())) {
                        SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                        Debug.logInfo("after loadPersonsWorker.execute", NAME);

                        //update the dialog
                        panel.setDialogFields();
                        panelShippingGroup.clearDialogFields();
                        //set order actions    
                        setOrderToActions(panel.getOrder());
                        setCaption(contFrame);
                    } else {
                        OrderMaxOptionPane.showMessageDialog(null, "Cannot Save : Supplier id is empty.");
                    }
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "Cannot Save : Supplier id is empty.");
                }
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contFrame.okButtonPressed();
            }
        });

        //shipment receipt action
        //copyToButton.quickShipEntireOrderAction = new QuickShipEntireOrderAction(QuickShipEntireOrderAction.nameStr, ControllerOptions.getSession(), desktopPane, parentFrame, orderListModel);
        orderActionPopupButton.shipmentReceiptAction.addActionListener(new ActionListener() {
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
        /*
         statusApprovedAction = new ApproveOrderAction(orderListModel, ControllerOptions.getSession());
         //        buttonPanel.btnApproveOrder.setAction(statusApprovedAction);
         statusApprovedAction.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
         loadOrder(e.getActionCommand());
         //set order actions
         setOrderToActions(panel.getOrder());

         loadFinancialData(panel.panelPartyIdPicker.textIdField.getText());
         setCaption(contFrame);
         }

         });
         */
        panel.shippingTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    Debug.logInfo("tabeed checked: " + pane.getSelectedIndex(), module);
                    if (pane.getSelectedIndex() == 1) {
                        panelShippingItem.setOrder(panel.getOrder());
                    }
                }
            }
        });
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
                    }
                }
            }
        });

        Debug.logInfo("order Id: " + orderId, module);
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
    /*
     void loadExistingOrder(String orderId) {

     //                    if (panel.getOrder() != null && panel.getOrder().getOrderId().equals(orderId)) {
     //                        return;
     //                    } else {
     Order order = LoadPurchaseOrderWorker.loadOrder(orderId, ControllerOptions.getSession());
     Debug.logWarning("Order changed - partyIdTextFieldFocusLost", module);
     ShoppingCart shopingCart = LoadPurchaseOrderWorker.loadShopingCart(orderId, ControllerOptions.getSession());
     order.setShopingCart(shopingCart);

     //clear dialog
     panel.clearDialogFields();
     //set order
     panel.setOrder(order);

     LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, shopingCart, ControllerOptions.getSession());

     //update the dialog
     panel.setDialogFields();

     orderItemCompositeListModel.clear();
     orderItemCompositeListModel.addAll(panel.order.getOrderItemsList().getList());
     panel.reloadItemDataModel(orderItemCompositeListModel);  //                  }
     //add empty name
     addNewOrderItem();

     }
     */

    public void loadOrderList(String partyId) {

        /*  List<String> stausList = FastList.newInstance();

         orderCompositeListModel.clear();

         stausList.add("ORDER_CREATED");
         stausList.add("ORDER_HOLD");
         stausList.add("ORDER_PROCESSING");
         //        stausList.add("ORDER_COMPLETED");

         List<String> orderTypeId = FastList.newInstance();
         orderTypeId.add("PURCHASE_ORDER");

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
         /*                Order order = new Order(genValue.getString("orderTypeId"));
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
        /*
         orderCompositeListModel.add(order);
         }
         }
         */
    }

    public void newOrder(String billFromPartyId, String billToPartyId) {
        try {

            Account billFromAccount = PartyListSingleton.getAccount(billFromPartyId);
            Account billToAccount = PartyListSingleton.getAccount(billToPartyId);
            String productStoreId = panel.productStoreCombo.getSelectedItem().getproductStoreId();
            String facilityId = panel.productStoreCombo.getSelectedItem().getinventoryFacilityId();
            String currency = (String) ControllerOptions.getSession().getAttribute("currency");
            Order order = LoadPurchaseOrderWorker.newPurchaseOrder(ControllerOptions.getSession(), Order.ORDERTYPE_PURCHSEORDER,
                    billFromAccount, billToAccount, productStoreId, facilityId, currency);

            OrderItemComposite orderItem = LoadPurchaseOrderWorker.newOrderItem(ControllerOptions.getSession());
            order.getOrderItemsList().add(orderItem);

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
            Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeOrderItem(OrderItemComposite orderItem) {
        try {

            LoadPurchaseOrderWorker.removeOrderItem(panel.order, orderItem, ControllerOptions.getSession());

            orderItemCompositeListModel.clear();
            orderItemCompositeListModel.addAll(panel.order.getOrderItemsList().getList());

            panel.getOrderEntryTableModel().clearActionListener();
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            panel.reloadItemDataModel(orderItemCompositeListModel);  //                  }
            panel.repaint();

        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//set and handle all dialog product actions
    public void loadOrder(String orderId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        Order order = LoadPurchaseOrderWorker.loadOrder(orderId, ControllerOptions.getSession());

//        order.setShopingCart(shopingCart);
//        order.setOrderContactList(LoadPurchaseOrderWorker.loadOrderContactList(orderId, ControllerOptions.getSession()));
        //clear dialog
        panel.clearDialogFields();

        //set order
        panel.setOrder(order);
        Debug.logError("order.getDestinationFacilityId(): " + order.getFacilityId(), module);
        LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, order, ControllerOptions.getSession());

        Debug.logError("panel.order.getInvoiceIds().size(): " + panel.order.getInvoiceIds().size(), module);
        //load invoice composites
//        loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel);
        orderItemCompositeListModel.clear();
        orderItemCompositeListModel.addAll(order.getOrderItemsList().getList());

        invoicesListModel.clear();
        invoicesListModel.addAll(LoadInvoiceWorker.loadPurchaseOrderComposites(order.getInvoiceIds(), ControllerOptions.getSession()));
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

    String invoiceTypeId = "PURCHASE_INVOICE";

    public void loadFinancialData(String billFromPartyId) {
        /*
         List<InvoiceComposite> invList;
         try {
         final ClassLoader cl = this.getClassLoader();
         Thread.currentThread().setContextClassLoader(cl);

         invoiceCompositeListModel.clear();

         invList = LoadInvoiceWorker.loadOutstandingInvoices(billFromPartyId, invoiceTypeId, ControllerOptions.getSession());

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
         Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
    }

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
                    LookupActionListner listner = new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, prodoptions);

                    ActionListener selectChangeAction = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            processProductIdTextFieldChange(textField, event.getRow());
                            event.getTable().setValueAt(textField.getText(), event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex());
                            event.getTable().changeSelection(event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_INTERNALNAME_INDEX.getColumnIndex(), false, false);
                            event.getTable().requestFocus();
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
                Debug.logWarning("e.getSource(): " + e.getSource(), module);

                if (e.getSource() instanceof InteractiveRenderer
                        && e instanceof RowColumnActionEvent) {

//                    InteractiveRenderer render = (InteractiveRenderer) e.getSource();
//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    final RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {
                        Debug.logWarning("hasEmptyRow: " + hasEmptyRow(), module);
                        Debug.logWarning("panel.getTableModel().getRowCount() - 1): " + panel.getTableModel().getRowCount(), module);
                        Debug.logWarning("event.getRow(): " + event.getRow(), module);

                        if ((orderItemCompositeListModel.getSize() - 1) == event.getRow()
                                && !hasEmptyRow()) {

//                            final BigDecimal qty = (BigDecimal) panel.getTableModel().getValueAt(event.getRow(), PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX.getColumnIndex());
//                            final BigDecimal price = (BigDecimal) panel.getTableModel().getValueAt(event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX.getColumnIndex());
                            //                        handleTableRowChange(event.getTable(), event.getRow());
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
                /*try {                
                 if ((panel.getTableModel().getRowCount() - 1) == panel.getTable().getSelectedRow() && !panel.getTableModel().hasEmptyRow()) {
  
                 //handleTableRowChange(panel.getTable(), panel.getTable().getSelectedRow());
                 }
                 } catch (Exception ex) {
                 Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
            }

        }
        );

        tableDataChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof PurchaseOrderTableModel
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
                        if (event.getCol() == PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX.getColumnIndex()) {
                            if (UtilValidate.isNotEmpty(orderItem.getOrderItem()) && UtilValidate.isNotEmpty(orderItem.getShoppingCartItem())) {
                                orderItem.getShoppingCartItem().setIsModifiedPrice(true);
                                orderItem.getShoppingCartItem().setBasePrice(orderItem.getOrderItem().getunitPrice());
                                Debug.logInfo("PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                            }
                        } else if (event.getCol() == PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX.getColumnIndex()) {
                            if (UtilValidate.isNotEmpty(orderItem.getOrderItem()) && UtilValidate.isNotEmpty(orderItem.getShoppingCartItem())) {
                                orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), panel.getOrder().getShopingCart());
                                Debug.logInfo("PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                            }
                        }

                        //now sink the values back again..
                        orderItem.getOrderItem().setunitPrice(orderItem.getShoppingCartItem().getBasePrice());
                        orderItem.getOrderItem().setquantity(orderItem.getShoppingCartItem().getQuantity());

                        Debug.logInfo("After item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getorderId()
                                + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                                + "orderItem.getOrderItem().getquantityPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        panel.setDialogTotals();
                    } catch (Exception ex) {
                        if (orderItem != null) {
                            orderItem.getOrderItem().setproductId("");
                            orderItem.getOrderItem().setitemDescription("");
                            //orderItem.getOrderItem().getorderId()
                            //     + "order Seq Id: " + orderItem.getOrderItem().getorderItemSeqId()
                            //     + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getunitPrice().toString()
                            //     + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        }
                        MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
                        msg.show(panel);

//                        OrderMaxUtility.handleServiceReturn(module, null, OrderMaxUtility.ServiceReturnErrorStatusDisplay.LOGERROR)
                        Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };

        panel.getOrderEntryTableModel().clearActionListener();
        panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);

        panel.getTxtProdIdTableTextField()
                .getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "COLUMN_ENTERY_KEY");
        panel.getTxtProdIdTableTextField().getActionMap().put("COLUMN_ENTERY_KEY", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableCellAction(panel.getTxtProdIdTableTextField(), panel.getTable().getSelectedRow(), panel.getTable().getSelectedColumn());
            }
        }
        );

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
                LookupActionListner listner = new LookupActionListner(LookupType.ProductId, options);
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
            OrderItemComposite orderItem = LoadPurchaseOrderWorker.newOrderItem(ControllerOptions.getSession());
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
        Debug.logInfo("tableCellAction product: " + textField.getText() + "  selectd col " + panel.getTable().getSelectedColumn() + " col: " + col, module);
        if (/*col == PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex() &&*/!textField.getText().isEmpty()) {
            Debug.logInfo(" pass if tableCellAction product: " + textField.getText() + "  PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex() " + PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex() + " col: " + col, module);
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
            LoadPurchaseOrderWorker.addProduct(order, orderItem, prodId, partyId, ControllerOptions.getSession());
            panel.setDialogTotals();
            textField.postActionEvent(); //inform the editor

        } catch (Exception ex) {
            panel.setDialogTotals();
            textField.setBorder(red);
            Debug.logError(ex, module);
            //          OrderMaxOptionPane.showMessageDialog(this, ex.getMessage());//.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);            
        }
    }

    /*
     public OrderItemComposite addProduct(int row, String prodId) throws Exception {
     final ClassLoader cl = this.getClassLoader();
     Thread.currentThread().setContextClassLoader(cl);

     Order order = panel.getOrder();

     Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
     //        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
     ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(prodId, ControllerOptions.getSession());
     OrderItemComposite orderItem = panel.getTableModel().getRowData(row);
     orderItem.getOrderItem().setproductId(prodId);
     if (orderItem.getShoppingCartItem() == null) {
     int index = order.getShopingCart().addItemToEnd(
     orderItem.getOrderItem().getproductId(),
     orderItem.getOrderItem().getselectedAmount(),
     orderItem.getOrderItem().getquantity(),
     orderItem.getOrderItem().getunitPrice(),
     null, null, null,
     orderItem.getOrderItem().getorderItemTypeId(),
     null,
     ControllerOptions.getSession().getDispatcher(),
     true,
     true);

     ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
     orderItem.setShoppingCartItem(scItem);
     }

     Debug.logInfo("prodId: " + prodId, module);

     orderItem.setProductItemPrice(pip);

     SupplierProductList spl = LoadProductPriceWorker.getSupplierProduct(prodId, ControllerOptions.getSession());
     orderItem.setSupplierProductList(spl);

     order.addOrderItem(orderItem);
     Debug.logInfo("before update : " + orderItem.getOrderItem().getitemDescription(), module);

     orderItem.getOrderItem().setitemDescription(key._name);
     LoadPurchaseOrderWorker.calculateProductItemValues(orderItem);
     //sur        orderItem.setupOrderItemData(order.getPartyId());
     Debug.logInfo("addProductById : " + key.toString(), module);
     Debug.logInfo("After update : " + orderItem.getOrderItem().getitemDescription(), module);
     return orderItem;
     //
     }
     */
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

    /*
     public SupplierProductComposite getSupplierProductComposite oic, String productId, String partyId, String currencyUomId) {
     SupplierProductComposite supplierProductBillingAccountoductList().getSupplerProduct(
     partyId,
     currencyUomId);
     if (supplierProductComp != null) {
     return supplierProductComp;
     // orderItem.getOrderItem().setunitPrice(supplierProductComp.getSupplierProduct()
     //.getlastPrice());
     } else {
     int response = 0;
     if (desktopPane != null) {
     response = JOptionPane.showConfirmDialog(null,
     "Supplier Product doesn't exists. Do you want to create?", "Confirmation",
     JOptionPane.YES_NO_OPTION);
     } else {
     response = OrderMaxOptionPane.showConfirmDialog(desktopPane,
     "Supplier Product doesn't exists. Do you want to create?", "Confirmation",
     JOptionPane.YES_NO_OPTION);

     }
     if (response == JOptionPane.YES_OPTION) {

     SBillingAccountPanelsupplierProductPanel = new SuBillingAccountPanel;

     ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());

     SupplierProductComposite spComp = LoadSupplierProdBillingAccountduct(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
     spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
     spComp.getSupplierProduct().setpartyId(partyId);
     spComp.getSupplierProduct().setcurrencyUomId(currencyUomId);
     spComp.getSupplierProduct().setsupplierPrefOrderId("10_MAIN_SUPPL");
     spComp.getSupplierProduct().setcanDropShip("N");

     //                supplierProductPanel.setBounds(0, 0, 300, 300);
     //            productComposite.getSupplierProductList().add(spComp);
     //clear dialog
     supplierProductPanel.clearDialogFields();

     //set order
     supplierProductPanel.setProductComposite(productComposite);
     supplierProductPanel.setSupplierProductComp(spComp);
     //update the dialog
     supplierProductPanel.setDialogField();

     JOptionPane.showConfirmDialog(null,
     supplierProductPanel,
     "Supplier Product",
     JOptionPane.OK_CANCEL_OPTION,
     JOptionPane.PLAIN_MESSAGE);

     SupplierProductList spl;
     try {
     spl = LoadProductPriceWorker.getSupplierProduct(productId, ControllerOptions.getSession());
     oic.setSupplierProductList(spl);
     supplierProductComp = oic.getSupplierProductList().getSupplerProduct(
     partyId,
     currencyUomId);
     if (supplierProductComp != null) {
     return supplierProductComp;
     }
     } catch (GenericEntityException ex) {
     Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     }
     return supplierProductComp;
     }
     */
    /*public SupplierProductComposite getSupplierProduct(OrderItemComposite oic, String productId, String partyId, String currencyUomId) {
     SupplierProductComposite supplierProductComp = oic.getSupplierProductList().getSupplerProduct(
     partyId,
     currencyUomId);
     if (supplierProductComp != null) {
     return supplierProductComp;
     // orderItem.getOrderItem().setunitPrice(supplierProductComp.getSupplierProduct()
     //.getlastPrice());
     } else {
     int response = JOptionPane.NO_OPTION;

     if (desktopPane != null) {
     JOptionPane.showConfirmDialog(null,
     "Supplier Product doesn't exists. Do you want to create?", "Confirmation",
     JOptionPane.YES_NO_OPTION);
     } else {
     /*                OrderMaxOptionPane.showConfirmDialog(desktopPane,
     "Supplier Product doesn't exists. Do you want to create?", "Confirmation",
     JOptionPane.YES_NO_OPTION);

     }

     if (response == JOptionPane.YES_OPTION) {
     */
//                SupplierProductPanel supplierProductPanel = new SupplierProductPanel();

    /*                ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());

     SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(productComposite.getProduct().getproductId());
     spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
     spComp.getSupplierProduct().setpartyId(partyId);
     spComp.getSupplierProduct().setcurrencyUomId(currencyUomId);
     spComp.getSupplierProduct().setsupplierPrefOrderId("10_MAIN_SUPPL");
     spComp.getSupplierProduct().setcanDropShip("N");

     //                supplierProductPanel.setBounds(0, 0, 300, 300);
     //            productComposite.getSupplierProductList().add(spComp);
     //clear dialog
     supplierProductPanel.clearDialogFields();

     //set order
     supplierProductPanel.setProductComposite(productComposite);
     supplierProductPanel.setSupplierProductComp(spComp);
     //update the dialog
     supplierProductPanel.setDialogField();

     JOptionPane.showConfirmDialog(null,
     supplierProductPanel,
     "Supplier Product",
     JOptionPane.OK_CANCEL_OPTION,
     JOptionPane.PLAIN_MESSAGE);
     */
    /*          SupplierProductComposite val = LoadSupplierProductWorker.createSupplierProduct(productId, ControllerOptions.getSession());
     SupplierProductList spl;
     try {
     spl = LoadProductPriceWorker.getSupplierProduct(productId, ControllerOptions.getSession());
     oic.setSupplierProductList(spl);
     supplierProductComp = oic.getSupplierProductList().getSupplerProduct(
     partyId,
     currencyUomId);
     if (supplierProductComp != null) {
     return supplierProductComp;
     } else {

     }
     } catch (GenericEntityException ex) {
     Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     }
     return supplierProductComp;
     }*/
    /*
     public OrderItemComposite updateItem(JTable table, int row, String prodId, BigDecimal price, BigDecimal qty) throws Exception {
     final ClassLoader cl = this.getClassLoader();
     Thread.currentThread().setContextClassLoader(cl);

     Order order = panel.getOrder();

     Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
     SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
     OrderItemComposite orderItem = panel.getTableModel().getRowData(row);

     orderItem.getOrderItem().setunitPrice(price);
     orderItem.getOrderItem().setquantity(qty);
     Debug.logInfo("updateItem qty : " + qty.toString() + " price : " + price.toString(), module);
     orderItem.getShoppingCartItem().setBasePrice(price);
     orderItem.getShoppingCartItem().setQuantity(qty, ControllerOptions.getSession().getDispatcher(), order.getShopingCart());
     Debug.logInfo(" saved items reload qty : " + orderItem.getShoppingCartItem().getQuantity().toString()
     + " price : " + orderItem.getShoppingCartItem().getBasePrice().toString(), module);
     //orderItem.saleLineTotal = qty.multiply(price);
     //        LoadPurchaseOrderWorker.calculateProductItemValues(orderItem);

     return orderItem;
     }
     */
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
        if (panel != null && panel.order != null
                && panel.order.getOrderId() != null
                && panel.order.getOrderId().isEmpty() == false) {

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

            if (panel.statusItemComboModel != null) {
                String str = panel.statusItemComboModel.getSelectedItem().getdescription();
                orderToStringBuilder.append(" Status");
                orderToStringBuilder.append("[");
                orderToStringBuilder.append(str);
                orderToStringBuilder.append("]");
            }
        } else {
            orderToStringBuilder.append("[");
            orderToStringBuilder.append("New");
            orderToStringBuilder.append("] ");
            if (panel != null && panel.statusItemComboModel != null) {
                String str = panel.statusItemComboModel.getSelectedItem().getdescription();
                orderToStringBuilder.append(" Status");
                orderToStringBuilder.append("[");
                orderToStringBuilder.append(str);
                orderToStringBuilder.append("]");
            }
        }

        return orderToStringBuilder.toString();
    }

    ShipmentReceiptAction shipmentReceiptAction = null;
    //GeneratePurchaseInvoiceAction generatePurchaseInvoiceAction = null;

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
        return panel.panelOrderIdPicker.textIdField.getText();
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

    @Override
    public Order getOrder() {
        getDialogFields();
        return panel.getOrder();
    }
}
