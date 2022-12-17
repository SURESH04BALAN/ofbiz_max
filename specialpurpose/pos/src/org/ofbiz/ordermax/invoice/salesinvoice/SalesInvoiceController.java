/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice.salesinvoice;

import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import mvc.controller.ApprovePurchaseInvoiceAction;
import mvc.controller.CreateAsNewOrderAction;
import mvc.controller.CreateNewShipGroupAction;
import mvc.controller.CreateReplacementOrderAction;
import mvc.controller.CreateReturnOrderAction;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceAction;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import mvc.controller.PartyIdVerifyValidator;
import mvc.controller.PayPurchaseOrderAction;
import mvc.controller.PrintPickSlipAction;
import mvc.controller.PrintSalesInvoiceAction;
import mvc.controller.QuickRefundEntireSalesOrderAction;
import mvc.controller.ShipmentReceiptAction;
import mvc.controller.ViewEditDeliverScheduleInfoAction;
import mvc.controller.ViewOrderHistoryAction;
import mvc.model.list.ListAdapterListModel;
import mvc.view.InvoiceCompositeOverviewPanel;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderEntryTableModel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.entity.Invoice;
import org.ofbiz.ordermax.invoice.InvoiceEntryTableModel;
import org.ofbiz.ordermax.invoice.InvoiceIdVerifyValidator;
import org.ofbiz.ordermax.orderbase.OrderFinancialData;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.SimpleOrderButtonPanel;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import org.ofbiz.ordermax.utility.OrderEntryKeyTableModel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.ProductSelectionPanel;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class SalesInvoiceController extends BaseMainScreen {

    public static final String module = PurchaseInvoiceController.class.getName();
    public SalesInvoiceEnteryPanel panel = null;
    public final String caption = "Sales Invoice";
    public PosScreen pos = null;
    String invoiceId = null;

    public SalesInvoiceController(XuiSession sess) {
        super(sess);

    }

    public SalesInvoiceController(String invoiceId, XuiSession sess) {
        super(sess);
        this.invoiceId = invoiceId;
    }

    SimpleOrderButtonPanel buttonPanel = null;
    private InvoiceCompositeOverviewPanel invoiceCompositePanel = null;
    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<InvoiceComposite> invoicesListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<InvoiceComposite> orderListModel = new ListAdapterListModel<InvoiceComposite>();

    CopyToPopupButton copyToButton = null;
    InvoiceIdVerifyValidator oiValidator = null;
    InvoiceIdVerifyValidator oiValidator1 = null;
    ApprovePurchaseInvoiceAction statusApprovedAction = null;
    PrintSalesInvoiceAction printPurchaseInvoiceAction = null;
    PrintPickSlipAction printPurchasePickSlipAction = null;
    ViewEditDeliverScheduleInfoAction viewEditDeliverScheduleInfoAction = null;
    ViewOrderHistoryAction viewOrderHistoryAction = null;
    CreateNewShipGroupAction createNewShipGroupAction = null;
    CreateAsNewOrderAction createAsNewOrderAction = null;
    PayPurchaseOrderAction payPurchaseOrderAction = null;
    CreateReplacementOrderAction createReplacementPurchaseOrderAction = null;
    CreateReturnOrderAction createReturnPurchaseOrderAction = null;
    QuickRefundEntireSalesOrderAction quickRefundEntireOrderAction = null;

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {

        width = 1000;
        height = 650;

        panel = new SalesInvoiceEnteryPanel(ControllerOptions.getSession());
        panel.setSize(width, height);
        Rectangle rect = panel.getBounds();
        rect.x = rect.x + 20;
        rect.y = rect.y + 20;
        panel.setBounds(rect);

        invoiceCompositePanel = new InvoiceCompositeOverviewPanel();
        invoiceCompositePanel.setInvoiceCompositeList(invoiceCompositeListModel);

        panel.panelHistory.setLayout(new BorderLayout());
        panel.panelHistory.add(BorderLayout.CENTER, invoiceCompositePanel);

        panel.setupEditOrderTable();

    //    panel.btnOrderLookup.addActionListener(new LookupActionListner(panel.orderIdTextField, "orderIdTextField", panel.partyIdTextField, "partyIdTextField", "BILL_FROM_VENDOR"));

        //party selection button
        ActionListener newPartyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                        PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                        Debug.logInfo("billFromPartyId: " + validator.getField().getText(), module);
                        if (validator.getField() != null && validator.getField().getText().isEmpty() == false) {
                            String billToPartyId = validator.getField().getText();
                            if (panel.getOrder() != null && panel.getOrder().getPartyId().equals(billToPartyId)) {
                                return;
                            } else {
                               // Debug.logInfo("panel.getOrder().getPartyId(): " + panel.getOrder().getPartyId(), module);
                                String billFromPartyId = ControllerOptions.getSession().getCompanyPartyId();;
                                newOrder(billToPartyId, billFromPartyId);
                                //set order actions
                                setOrderToActions(panel.getOrder());

                                loadFinancialData(billToPartyId);
                                setCaption(contFrame);
                            }
                        } else {
                            //clear dialog
                            panel.clearDialogFields();
                            orderListModel.clear();
//                            orderListModel.add(order);
                            invoiceCompositeListModel.clear();
                            buttonPanel.btnSaveOrder.setEnabled(true);
//                            buttonPanel.btnApproveOrder.setEnabled(false);
                            buttonPanel.btnCancelOrder.setEnabled(false);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        PartyIdVerifyValidator piValidator = new PartyIdVerifyValidator(panel.txtLineItemPartyId, ControllerOptions.getSession());
        piValidator.addActionListener(newPartyAction);
        PartyIdVerifyValidator piValidator1 = new PartyIdVerifyValidator(panel.partyIdTextField, ControllerOptions.getSession());
        piValidator1.addActionListener(newPartyAction);
        panel.txtLineItemPartyId.setInputVerifier(piValidator);
        panel.partyIdTextField.setInputVerifier(piValidator1);

        //order selection button
        ActionListener orderIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof InvoiceIdVerifyValidator) {
                    InvoiceIdVerifyValidator validator = (InvoiceIdVerifyValidator) e.getSource();
                    String invoiceId = validator.getField().getText();
                    Debug.logInfo("invoiceId: " + invoiceId, module);
                    loadInvoice(invoiceId);
                    //set order actions
                    setOrderToActions(panel.getOrder());
                    Debug.logInfo("panel.getOrder(): " + panel.getOrder().getInvoice().getinvoiceId(), module);

                    loadFinancialData(panel.partyIdTextField.getText());
                    setCaption(contFrame);
                }
            }
        };

        oiValidator = new InvoiceIdVerifyValidator(panel.txtLineItemorderId, ControllerOptions.getSession());
        oiValidator.addActionListener(orderIdTextChangeAction);

        oiValidator1 = new InvoiceIdVerifyValidator(panel.orderIdTextField, ControllerOptions.getSession());
        oiValidator1.addActionListener(orderIdTextChangeAction);
        panel.txtLineItemorderId.setInputVerifier(oiValidator);
        panel.orderIdTextField.setInputVerifier(oiValidator1);

        //set and handle all dialog product actions
        setupProductActions();

//        panel.newItem();
        //remove order item from tabld
        panel.addChangeListener(this);
        panel.btnDeleteSelectedItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.order.getInvoice().getstatusId().equals("ORDER_COMPLETED") == false) {
                    List<InvoiceItemComposite> list = panel.getOrderEntryTableModel().getSelectedRows();
                    for (InvoiceItemComposite ar : list) {
                        if (ar.getOrderItem().getproductId() != null
                                && ar.getOrderItem().getproductId().isEmpty() == false) {

                            removeOrderItem(ar);
                            Debug.logInfo("Delete Selected : " + ar.getOrderItem().getproductId(), module);

                        }
                    }
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "InvoiceComposite Completed can't delete items");
                }

            }
        });

        buttonPanel = new SimpleOrderButtonPanel();
        buttonPanel.btnOrderAction.setText("New Invoice");
        buttonPanel.btnSaveOrder.setText("Save Invoice");        
//        buttonPanel.btnApproveOrder.setText("Approve Invoice");        
        buttonPanel.btnCancelOrder.setText("Cancel Invoice");        

        copyToButton = new CopyToPopupButton(buttonPanel.btnCopyTo);
        OrderMaxUtility.addAPanelGrid(panel, contFrame.getPanelDetail());
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), contFrame.getPanelSelecton());
        OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

        /*        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         cancelButtonPressed();
         }
         });
         */
  /*      buttonPanel.btnOrderAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String billToPartyId = ControllerOptions.getSession().getCompanyPartyId();;
                newOrder(panel.partyIdTextField.getText(), billToPartyId);
                //set order actions
                setOrderToActions(panel.getOrder());
                invoiceCompositeListModel.clear();
                buttonPanel.btnSaveOrder.setEnabled(true);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(false);
                setCaption(contFrame);
            }
        });
*/
        buttonPanel.btnSaveOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.partyIdTextField.getText() != null
                        && panel.partyIdTextField.getText().isEmpty() == false) {
                    panel.getDialogFields();
                    InvoiceComposite order = panel.getOrder();
//                SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
                    Debug.logInfo("before loadPersonsWorker.execute", NAME);
//                saveOrderWorker.execute();
//                    SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                    Debug.logInfo("after loadPersonsWorker.execute", NAME);

                    //update the dialog
                    panel.setDialogFields();
                    //set order actions    
                    setOrderToActions(panel.getOrder());
                    setCaption(contFrame);
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "Cannot Save : Supplier id is empty.");
                }
            }
        });

        statusApprovedAction = new ApprovePurchaseInvoiceAction(orderListModel, ControllerOptions.getSession());
//        buttonPanel.btnApproveOrder.setAction(statusApprovedAction);
        statusApprovedAction.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadInvoice(e.getActionCommand());
                //set order actions
                setOrderToActions(panel.getOrder());
                loadFinancialData(panel.partyIdTextField.getText());
                setCaption(contFrame);
            }

        });

        if (invoiceId != null) {
            loadInvoice(invoiceId);
            //set order actions
            setOrderToActions(panel.getOrder());

            loadFinancialData(panel.partyIdTextField.getText());
            setCaption(contFrame);
        } else {
            buttonPanel.btnSaveOrder.setEnabled(true);
//            buttonPanel.btnApproveOrder.setEnabled(false);
            buttonPanel.btnCancelOrder.setEnabled(false);

        }
//        panel.setProductListArray((ProductListArray) treePanel.getTreeDataList());
        setInitialFocusField(panel.getPartyTextField());
    }

    ActionListener tableDataChangeAction = null;

    void loadExistingOrder(String invoiceId) {
        try {
            //                    if (panel.getOrder() != null && panel.getOrder().getInvoice().getinvoiceId().equals(invoiceId)) {
//                        return;
//                    } else {
            InvoiceComposite order = LoadInvoiceWorker.loadInvoice(invoiceId, ControllerOptions.getSession());
            Debug.logWarning("InvoiceComposite changed - partyIdTextFieldFocusLost", module);
//        ShoppingCart shopingCart = LoadPurchaseOrderWorker.loadShopingCart(invoiceId, ControllerOptions.getSession());
//        order.setShopingCart(shopingCart);
            
            //clear dialog
            panel.clearDialogFields();
            //set order
            panel.setOrder(order);
            
            //      LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, shopingCart);
            
            //update the dialog
            panel.setDialogFields();
            
            //setup table
            panel.setupEditOrderTable();
            
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            
            //load table
//                    panel.loadItemEditDataModel();  //
            //load table
            panel.reloadItemDataModel(order.getInvoiceItemCompositeList());  //                  }
            //add empty name
            addNewOrderItem();
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void newOrder(String billFromPartyId, String billToPartyId) {
        try {
            Account billFromAccount = PartyListSingleton.getAccount(billFromPartyId);
            Account billToAccount = PartyListSingleton.getAccount(billToPartyId);
            InvoiceComposite order = LoadInvoiceWorker.newInvoice(ControllerOptions.getSession(), "SALES_INVOICE", billFromAccount, billToAccount);

//sur            InvoiceItemComposite orderItem = LoadPurchaseOrderWorker.newOrderItem(ControllerOptions.getSession());
//            order.getOrderItemsList().add(orderItem);
            Debug.logWarning("Party changed - partyIdTextFieldFocusLost", module);
            //clear dialog
            panel.clearDialogFields();
            //set order
            panel.setOrder(order);

            //update the dialog
            panel.setDialogFields();

            //setup table
            panel.setupEditOrderTable();

            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            //load table
            panel.reloadItemDataModel(order.getInvoiceItemCompositeList());  //                  }

            oiValidator.setPartyId(billFromPartyId);
            oiValidator1.setPartyId(billFromPartyId);

            //set order actions
            setOrderToActions(panel.getOrder());

//                }
//            });
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeOrderItem(InvoiceItemComposite orderItem) {
        try {

            LoadInvoiceWorker.removeOrderItem(panel.order, orderItem, ControllerOptions.getSession());

            panel.reloadItemDataModel(panel.order.getInvoiceItemCompositeList());  //                  }
            panel.repaint();

        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//set and handle all dialog product actions
    public void loadInvoice(String invoiceId) {
        try {
            final ClassLoader cl = getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);
            Debug.logInfo(" loadInvoic invoiceId: " + invoiceId, module);            
            InvoiceComposite order = LoadInvoiceWorker.loadInvoice(invoiceId, ControllerOptions.getSession());
            
//sur        ShoppingCart shopingCart = LoadPurchaseOrderWorker.loadShopingCart(invoiceId, ControllerOptions.getSession());
//sur        order.setShopingCart(shopingCart);
            order.setInvoiceContactList(LoadInvoiceWorker.loadInvoiceContactList(invoiceId, ControllerOptions.getSession()));
            
            //clear dialog
            panel.clearDialogFields();
            
            //set order
            panel.setOrder(order);
            
//            LoadPurchaseOrderWorker.setShoppingCartItemToItemComposite(order, shopingCart);
            
            //load invoice composites
//        loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel);
            invoicesListModel.clear();
//            LoadInvoiceWorker.loadPurchaseOrderComposites(order.getInvoiceIds(), invoicesListModel, ControllerOptions.getSession());
            //update the dialog
            panel.setDialogFields();
            
            //setup table
            panel.setupEditOrderTable();
            
            panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);
            
            //load table
            panel.reloadItemDataModel(order.getInvoiceItemCompositeList());  //                  }
            
            //add empty name
            addNewOrderItem();
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrderToActions(InvoiceComposite order) {

        orderListModel.clear();
        orderListModel.add(order);
        invoiceCompositeListModel.clear();
        copyToButton.loadPopMenu(order);

        if (order != null && order.getInvoice().getinvoiceId() != null && order.getInvoice().getinvoiceId().isEmpty() == false) {

            if ("INVOICE_IN_PROCESS".equals(order.getInvoice().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnOrderAction.setEnabled(isEnabled);
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

            if ("INVOICE_APPROVED".equals(order.getInvoice().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnOrderAction.setEnabled(isEnabled);                
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }


            if ("INVOICE_READY".equals(order.getInvoice().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnOrderAction.setEnabled(isEnabled);                
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }
            
            if ("INVOICE_CANCELLED".equals(order.getInvoice().getstatusId())) {
                boolean isEnabled = true;
                buttonPanel.btnOrderAction.setEnabled(isEnabled);                
                buttonPanel.btnSaveOrder.setEnabled(false);
//                buttonPanel.btnApproveOrder.setEnabled(false);
                buttonPanel.btnCancelOrder.setEnabled(false);
            }

            
            if ("INVOICE_SENT".equals(order.getInvoice().getstatusId())
                    || "INVOICE_PAID".equals(order.getInvoice().getstatusId())
                    || "INVOICE_WRITEOFF".equals(order.getInvoice().getstatusId())) {
                boolean isEnabled = false;
                buttonPanel.btnSaveOrder.setEnabled(isEnabled);
//                buttonPanel.btnApproveOrder.setEnabled(isEnabled);
                buttonPanel.btnCancelOrder.setEnabled(isEnabled);
            }

        }
    }
    
    String invoiceTypeId = "PURCHASE_INVOICE";

    public void loadFinancialData(String billFromPartyId) {
        List<InvoiceComposite> invList;
        try {

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
            Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {

                        Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                        if (genVal != null) {
                            if (genVal.containsKey("productId")) {

                                if (panel != null) {

                                    JTextField textField = panel.getTxtProdIdTableTextField();
                                    if (textField != null) {
                                        textField.setText(genVal.get("productId").toString());
                                        Debug.logWarning("InvoiceComposite changed - product id text field: " + genVal.get("productId").toString(), module);
                                        processProductIdTextFieldChange(textField, event.getRow());
                                        event.getTable().setValueAt(genVal.get("productId").toString(), event.getRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                                        event.getTable().changeSelection(event.getRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                                    }
                                } else {
//                        table.setValueAt(genVal.get("productId").toString(), row, column);
                                }
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ProductTreeActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        panel.getProductTreeActionTableCellEditor().addActionListener(productIdChangeAction);

        //order product Id selection click
        //party selection button
        ActionListener interactiveCellRenderAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof InteractiveRenderer
                        && e instanceof RowColumnActionEvent) {

                    InteractiveRenderer render = (InteractiveRenderer) e.getSource();
//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    final RowColumnActionEvent event = (RowColumnActionEvent) e;
                    try {
                        if ((panel.getTableModel().getRowCount() - 1) == event.getRow()
                                && !panel.getTableModel().hasEmptyRow()) {

                            final BigDecimal qty = (BigDecimal) panel.getTableModel().getValueAt(event.getRow(), OrderEntryTableModel.ORDER_QTY_INDEX);
                            final BigDecimal price = (BigDecimal) panel.getTableModel().getValueAt(event.getRow(), OrderEntryTableModel.ORDER_PRICE_INDEX);
                            //                        handleTableRowChange(event.getTable(), event.getRow());
                            addNewOrderItem();

                        }

                        panel.setDialogTotals();
                        panel.highlightLastRow(event.getRow());
                    } catch (Exception ex) {
                        Debug.logError(ex,module);
                    }

                }
            }
        };

        panel.getInteractiveRenderer().addActionListener(interactiveCellRenderAction);

        panel.getTable().addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (!panel.getTableModel().hasEmptyRow()) {
                    final BigDecimal qty = (BigDecimal) panel.getTableModel().getValueAt(panel.getTable().getSelectedRow(), InvoiceEntryTableModel.Columns.QUANTITY.getColumnIndex());
                    final BigDecimal price = (BigDecimal) panel.getTableModel().getValueAt(panel.getTable().getSelectedRow(), InvoiceEntryTableModel.Columns.UNIT_PRICE.getColumnIndex());
                    if (qty.compareTo(BigDecimal.ZERO) != 0) {
                        addNewOrderItem();
                    }

                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                /*try {                
                 if ((panel.getTableModel().getRowCount() - 1) == panel.getTable().getSelectedRow() && !panel.getTableModel().hasEmptyRow()) {
  
                 //handleTableRowChange(panel.getTable(), panel.getTable().getSelectedRow());
                 }
                 } catch (Exception ex) {
                 Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
            }

        }
        );

        tableDataChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof OrderEntryTableModel
                        && e instanceof RowColumnActionEvent) {

                    try {
                        final RowColumnActionEvent event = (RowColumnActionEvent) e;
                        int row = event.getRow();
                        InvoiceItemComposite orderItem = panel.getTableModel().getRowData(row);

                        Debug.logInfo("Before item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getInvoiceId()
                                + "order Seq Id: " + orderItem.getOrderItem().getinvoiceItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getAmount().toString()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        Debug.logInfo("event.getCol(): " + event.getCol(), module);
                        if (event.getCol() == OrderEntryTableModel.ORDER_PRICE_INDEX) {
//                            orderItem.getShoppingCartItem().setIsModifiedPrice(true);
//                            orderItem.getShoppingCartItem().setBasePrice(orderItem.getOrderItem().getunitPrice());

                            Debug.logInfo("OrderEntryTableModel.ORDER_PRICE_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        } else if (event.getCol() == OrderEntryTableModel.ORDER_QTY_INDEX) {
//                            orderItem.getShoppingCartItem().setQuantity(orderItem.getOrderItem().getquantity(), ControllerOptions.getSession().getDispatcher(), panel.getOrder().getShopingCart());
                            Debug.logInfo("OrderEntryTableModel.ORDER_QTY_INDEX: " + orderItem.getOrderItem().getproductId(), module);
                        }

                        //now sink the values back again..
//                        orderItem.getOrderItem().setAmount(orderItem.getShoppingCartItem().getBasePrice());
  //                      orderItem.getOrderItem().setquantity(orderItem.getShoppingCartItem().getQuantity());

                        Debug.logInfo("After item prodId: " + orderItem.getOrderItem().getproductId()
                                + "order Id: " + orderItem.getOrderItem().getInvoiceId()
                                + "order Seq Id: " + orderItem.getOrderItem().getinvoiceItemSeqId()
                                + "orderItem.getOrderItem().getunitPrice(): " + orderItem.getOrderItem().getamount().toString()
                                + "orderItem.getOrderItem().getquantityPrice(): " + orderItem.getOrderItem().getquantity().toString(), module);
                        panel.setDialogTotals();
                    } catch (Exception ex) {
                        Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };

        panel.getOrderEntryTableModel().addActionListener(tableDataChangeAction);

        panel.getTxtProdIdTableTextField().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "COLUMN_ENTERY_KEY");
        panel.getTxtProdIdTableTextField().getActionMap().put("COLUMN_ENTERY_KEY", new AbstractAction() {
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
                try {

                    Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                    if (genVal != null) {
                        if (genVal.containsKey("productId")) {

                            if (panel != null) {

                                JTextField textField = panel.getTxtProdIdTableTextField();
                                if (textField != null) {
                                    textField.setText(genVal.get("productId").toString());
                                    Debug.logWarning("InvoiceComposite changed - product id text field: " + genVal.get("productId").toString(), module);
                                    processProductIdTextFieldChange(textField, panel.getTable().getSelectedRow());
                                    panel.getTable().setValueAt(genVal.get("productId").toString(), panel.getTable().getSelectedRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                                    panel.getTable().changeSelection(panel.getTable().getSelectedRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                                    panel.getTable().requestFocus();
                                }
                            } else {
//                        table.setValueAt(genVal.get("productId").toString(), row, column);
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ProductTreeActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        panel.getTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

    }

    public void addNewOrderItem() {

        InvoiceComposite order = panel.getOrder();//.getOrderItemsList().add(orderItem);
        if ("ORDER_COMPLETED".equals(order.getInvoice().getstatusId()) == false) {
            InvoiceItemComposite orderItem = LoadInvoiceWorker.newOrderItem(ControllerOptions.getSession());
            order.getInvoiceItemCompositeList().add(orderItem);
            panel.getTableModel().addEmptyRow(orderItem);
        }

    }

    public void handleTableRowChange(final JTable table, int row) throws Exception {

        final BigDecimal qty = (BigDecimal) panel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_QTY_INDEX);
        final BigDecimal price = (BigDecimal) panel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_PRICE_INDEX);

        if (qty.compareTo(BigDecimal.ZERO) != 0) {
            String prodId = (String) panel.getTableModel().getValueAt(row, OrderEntryTableModel.ORDER_PROD_ID_INDEX);
            updateItem(table, row, prodId, price, qty);
        }
    }

    void setCaption(final ContainerPanelInterface contFrame) {
        contFrame.setCaption(getCaption());
    }

    void tableCellAction(final JTextField textField, int row, int col) {
        Debug.logInfo("tableCellAction product: " + textField.getText(), module);
        if (panel.getTable().getSelectedColumn() == OrderEntryKeyTableModel.ORDER_PROD_ID_INDEX && textField.getText().isEmpty() == false) {
            processProductIdTextFieldChange(textField, row);
        }
    }

    private static final Border red = new LineBorder(Color.red);

    public void processProductIdTextFieldChange(final JTextField textField, int row) {
        try {
            Debug.logInfo("selected product: " + textField.getText(), module);

//            addProduct(row, prodId);
            String prodId = textField.getText();
//            String partyId = panel.panelPartyIdPicker.textIdField.getText();
//            OrderItemComposite orderItem = orderItemCompositeListModel.getElementAt(row);
//            Order order = panel.getOrder();
//            LoadSalesOrderWorker.addProduct(order, orderItem, prodId, partyId, ControllerOptions.getSession());            
            panel.setDialogTotals();
            textField.postActionEvent(); //inform the editor
            panel.getTableModel().fireTableRowsUpdated(row, row);
        } catch (Exception ex) {
            panel.setDialogTotals();
            textField.setBorder(red);
            Debug.logError(ex, module);
            //          OrderMaxOptionPane.showMessageDialog(this, ex.getMessage());//.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);            
        }
    }
/*
    public InvoiceItemComposite addProduct(int row, String prodId) throws Exception {
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        InvoiceComposite order = panel.getOrder();

        Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
//        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
        ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(prodId, ControllerOptions.getSession());
        InvoiceItemComposite orderItem = panel.getTableModel().getRowData(row);
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

        return orderItem;
//
    }*/

    public InvoiceItemComposite updateItem(JTable table, int row, String prodId, BigDecimal price, BigDecimal qty) throws Exception {
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        InvoiceComposite order = panel.getOrder();

       // Key key = ProductTreeArraySingleton.getInstance().getProductFromId(prodId);
//        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), ControllerOptions.getSession());
        InvoiceItemComposite orderItem = panel.getTableModel().getRowData(row);

        orderItem.getOrderItem().setamount(price);
        orderItem.getOrderItem().setquantity(qty);
        Debug.logInfo("updateItem qty : " + qty.toString() + " price : " + price.toString(), module);
        orderItem.getShoppingCartItem().setBasePrice(price);
//        orderItem.getShoppingCartItem().setQuantity(qty, ControllerOptions.getSession().getDispatcher(), order.getShopingCart());
        Debug.logInfo(" saved items reload qty : " + orderItem.getShoppingCartItem().getQuantity().toString()
                + " price : " + orderItem.getShoppingCartItem().getBasePrice().toString(), module);
        //orderItem.saleLineTotal = qty.multiply(price);
//        LoadPurchaseOrderWorker.calculateProductItemValues(orderItem);

        return orderItem;
    }

    @Override
    public void setVisibleItems() {
    }

    public void setLimitRight() {
        boolean isEnabled = panel.isOrderEditable();
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

    public String getCaption() {
        StringBuilder orderToStringBuilder = new StringBuilder();
        orderToStringBuilder.append(caption);
        if (panel != null && panel.order != null
                && panel.order.getInvoice().getinvoiceId() != null
                && panel.order.getInvoice().getinvoiceId().isEmpty() == false) {

            orderToStringBuilder.append(" - Invoice Id[ ");
            orderToStringBuilder.append(panel.order.getInvoice().getinvoiceId());
//            if (panel.order.getInvoice().getOrderName() != null
//                    && panel.order.getInvoice().getOrderName().isEmpty() == false) {
//                orderToStringBuilder.append("(");
//                orderToStringBuilder.append(panel.order.getInvoice().getOrderName());
//                orderToStringBuilder.append(") ");
//            }
            orderToStringBuilder.append("]");
/*
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
*/
            if (panel.orderStatusCombo != null) {
                String str = panel.orderStatusCombo.getSelectedValueDesc();
                orderToStringBuilder.append(" Status");
                orderToStringBuilder.append("[");
                orderToStringBuilder.append(str);
                orderToStringBuilder.append("]");
            }
        } else {
            orderToStringBuilder.append("[");
            orderToStringBuilder.append("New");
            orderToStringBuilder.append("] ");
            if (panel!=null && panel.orderStatusCombo != null) {
                String str = panel.orderStatusCombo.getSelectedValueDesc();
                orderToStringBuilder.append(" Status");
                orderToStringBuilder.append("[");
                orderToStringBuilder.append(str);
                orderToStringBuilder.append("]");
            }            
        }

        return orderToStringBuilder.toString();
    }

    ShipmentReceiptAction shipmentReceiptAction = null;
    GeneratePurchaseInvoiceAction generatePurchaseInvoiceAction = null;

    class CopyToPopupButton {

        private JToggleButton popupButton = null;
        private JPopupMenu popupMenu = null;

        private InvoiceComposite popupOrder;

        public CopyToPopupButton( JToggleButton button) {
            this.popupButton = button;
//            this.popupOrder = order;

//            loadPopMenu();
        }

        final public void loadPopMenu(final InvoiceComposite pOrder) {
            this.popupOrder = pOrder;
            Debug.logInfo("popupOrder.getOrder(): " + popupOrder.getInvoice().getinvoiceId(), module);
            final JPanel popupPanel = new JPanel();
            popupPanel.setBackground(new java.awt.Color(255, 255, 255));
            popupPanel.setLayout(new GridLayout(0, 1));
            if (popupOrder != null
                    && popupOrder.getInvoice().getinvoiceId() != null
                    && popupOrder.getInvoice().getinvoiceId().isEmpty() == false) {

                if ("ORDER_CREATED".equals(popupOrder.getInvoice().getstatusId())) {

                    JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
                    popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);

//                    JToggleButton btnInvVal = purchaseInvoiceAction.createActionButtonItemToggle();
//                    popupPanel.add(btnInvVal/*, BorderLayout.LINE_START*/);
                }

                if ("ORDER_APPROVED".equals(popupOrder.getInvoice().getstatusId())) {

                    JToggleButton btnVal = shipmentReceiptAction.createActionButtonItemToggle();
                    popupPanel.add(btnVal/*, BorderLayout.LINE_START*/);

                    JToggleButton btnViewEditDeliver = viewEditDeliverScheduleInfoAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createNewShipGroupAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = payPurchaseOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
                    popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);

                }

                if ("ORDER_COMPLETED".equals(popupOrder.getInvoice().getstatusId())) {
                    JToggleButton btnViewEditDeliver = quickRefundEntireOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createReturnPurchaseOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createAsNewOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = createReplacementPurchaseOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = viewOrderHistoryAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                    JToggleButton btnPrintVal = printPurchaseInvoiceAction.createActionButtonItemToggle();
                    popupPanel.add(btnPrintVal, BorderLayout.LINE_START);

                    JToggleButton btnPrintPickSlipVal = printPurchasePickSlipAction.createActionButtonItemToggle();
                    popupPanel.add(btnPrintPickSlipVal/*, BorderLayout.LINE_START*/);

                    btnViewEditDeliver = payPurchaseOrderAction.createActionButtonItemToggle();
                    popupPanel.add(btnViewEditDeliver/*, BorderLayout.LINE_START*/);

                }
            }

            popupMenu = new JPopupMenu();
            popupMenu.add(popupPanel);
            popupButton.setPreferredSize(new Dimension(popupMenu.getPreferredSize().width, popupButton.getPreferredSize().height));
            popupButton.setMaximumSize(new Dimension(popupMenu.getPreferredSize().width, popupButton.getPreferredSize().height));
            popupButton.removeAll();

            popupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (popupButton.isSelected()) {

                        if (popupOrder == null || popupOrder.getInvoice().getinvoiceId() == null || popupOrder.getInvoice().getinvoiceId().isEmpty() == true) {

                            int selection = OrderMaxOptionPane.showConfirmDialog(
                                    null, "InvoiceComposite is not created yet. Please save the order", "InvoiceComposite : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            popupButton.setSelected(false);
                            return;
                        }

                        popupMenu.pack();
                        Point pos = new Point();
                        // get the preferred size of the menu...
                        Dimension size = popupMenu.getPreferredSize();
                        // Adjust the x position so that the left side of the popup
                        // appears at the center of  the component
                        pos.x = 0;//(popupButton.getWidth() / 2);
                        // Adjust the y position so that the y postion (top corner)
                        // is positioned so that the bottom of the popup
                        // appears in the center
                        pos.y = /*(popupButton.getHeight() / 2)*/ 0 - size.height;
                        popupMenu.show(popupButton, pos.x, pos.y);
                    }
                }
            });

            popupMenu.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent pme) {
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                    Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                    Point componentLoc = popupButton.getLocationOnScreen();
                    mouseLoc.x -= componentLoc.x;
                    mouseLoc.y -= componentLoc.y;
                    //               if (!popupButton.contains(mouseLoc)) {
                    popupButton.setSelected(false);
                    Debug.logInfo("mouseLoc: " + mouseLoc, module);
                    //               }
                }
            });

        }
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
                Logger.getLogger(PurchaseInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
