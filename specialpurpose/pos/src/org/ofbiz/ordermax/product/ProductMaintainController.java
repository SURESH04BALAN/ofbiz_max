/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.NAME;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import mvc.controller.LoadProductWorker;
import mvc.controller.ProductIdMaintainVerifyValidator;
import mvc.controller.ProductIdVerifyValidator;
import mvc.model.list.ListAdapterListModel;
import static mvc.model.table.InventoryItemDetailTableModel.Columns.orderId;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductMaintainController extends BaseMainScreen {

    public static final String module = FindInvoiceListController.class.getName();
    //public ProductCompositePanel panel = null;
    ProductPanelInterface panel = null;
    ProductPickerEditPanel panelProductIdPicker = null;
    public final String caption = "Product Maintain";
    final ListAdapterListModel<ProductComposite> orderListModel = new ListAdapterListModel<ProductComposite>();

    private String productId = null;
    ControllerOptions options = null;

    public String getCaption() {

        if (options.isReadOnly()) {

            return "Product Enquire (Read Only)";
        } else {
            return "Product Maintain";
        }

    }

    public ProductMaintainController(ControllerOptions options, XuiSession sess) {
        super(sess);
        this.options = options;
    }

    public ProductMaintainController(ListAdapterListModel<ProductComposite> ordListModel, ControllerOptions options, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        this.options = options;
    }

    public ProductMaintainController(String productId, ControllerOptions options, XuiSession sess) {
        super(sess);
        this.productId = productId;
        this.options = options;
    }

    ProductCompositeButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<ProductComposite> productCompositeListModel = new ListAdapterListModel<ProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
            contFrame.setLocation(x, y);
        }
    }

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
                        Debug.logInfo("loadScreen : ProductMaintainController", module);
        //panel = new ProductCompositePanel(options, ControllerOptions.getDesktopPane() );
        if (options.isShowFullProductScreen()) {
            ProductCompositePanel panelComp = new ProductCompositePanel(options, ControllerOptions.getDesktopPane());
            panelProductIdPicker = panelComp.panelProductIdPicker;
            panel = panelComp;
            OrderMaxUtility.addAPanelGrid(panelComp, contFrame.getPanelDetail());

        } else {
            CustomProductPanel panelNorm = new CustomProductPanel(ControllerOptions.getSession());
            panel = panelNorm;
            panelProductIdPicker = panelNorm.panelProductIdPicker;
            OrderMaxUtility.addAPanelGrid(panelNorm, contFrame.getPanelDetail());
 //           panelProductTree.setLayout(new BorderLayout());
            //           panelProductTree.add(BorderLayout.CENTER, panel);
        }

        try {
//            panel.invoiceTypeComboBoxModel.setSelectedItem(InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE"));
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

//        panel.setReceiveInventoryList(productCompositeListModel);
        buttonPanel = new ProductCompositeButtonPanel();
        buttonPanel.btnNew.setEnabled(!options.isReadOnly());
        buttonPanel.btnSave.setEnabled(!options.isReadOnly());
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());

        OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

        if (options.isReadOnly()) {
            //order selection button
            ActionListener productIdTextChangeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() instanceof ProductIdVerifyValidator) {
                        ProductIdVerifyValidator validator = (ProductIdVerifyValidator) e.getSource();
                        String productId = validator.getField().getText();
                        Debug.logInfo("productId: " + orderId, module);
                        loadProduct(productId);
                        //set order actions
                        if (panel.getProductComposite() != null) {
                            setProductToActions(panel.getProductComposite());
                            Debug.logInfo("panel.getProduct(): " + panel.getProductComposite().getProduct().getproductId(), module);
                        }
                        setCaption(contFrame);
                    }
                }
            };

            ProductIdVerifyValidator prodValidator = new ProductIdVerifyValidator(panelProductIdPicker.textIdField, ControllerOptions.getSession());
            prodValidator.addActionListener(productIdTextChangeAction);
            panelProductIdPicker.textIdField.setInputVerifier(prodValidator);
        } else {
            //order selection button
            ActionListener productIdTextChangeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() instanceof ProductIdMaintainVerifyValidator) {
                        ProductIdMaintainVerifyValidator validator = (ProductIdMaintainVerifyValidator) e.getSource();
                        if (validator.isProductIdExists()) {
                            String productId = validator.getField().getText();
                            loadProduct(productId);
                            //set order actions
                            if (panel.getProductComposite() != null) {
                                setProductToActions(panel.getProductComposite());
                            }
                        } else {
                            String productId = validator.getField().getText();
                            panel.clearDialogFields();
                            ProductComposite prodComp = LoadProductWorker.newProduct();
                            prodComp.getProduct().setproductId(productId);
                            panel.setProductComposite(prodComp);
                            panel.setDialogField();
                        }
                        setCaption(contFrame);
                    }
                }
            };

            ProductIdMaintainVerifyValidator prodValidator = new ProductIdMaintainVerifyValidator(panelProductIdPicker.textIdField, ControllerOptions.getSession());
            prodValidator.addActionListener(productIdTextChangeAction);
            panelProductIdPicker.textIdField.setInputVerifier(prodValidator);
        }

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("before loadPersonsWorker.execute", NAME);
                Debug.logInfo("after loadPersonsWorker.execute", NAME);
                contFrame.okButtonPressed();
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                panel.getDialogField();
                panel.clearDialogFields();
                ProductComposite prodComp = LoadProductWorker.newProduct();
                panel.setProductComposite(prodComp);
                panel.setDialogField();
                setCaption(contFrame);
            }
        });

        buttonPanel.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.getDialogField();
                LoadProductWorker.saveProduct(panel.getProductComposite(), ControllerOptions.getSession());
                setCaption(contFrame);
            }
        });

        /*
         panel.btnFind.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         List<String> stausList = FastList.newInstance();
         List<ProductComposite> invList;
         try {

         productCompositeListModel.clear();

         //                    Map<String, Object> findOptionList = panel.getFindOptionList();
         //                    invList = LoadInvoiceWorker.loadInvoices(findOptionList, ControllerOptions.getSession());

         //                    invoiceCompositeListModel.addAll(invList);

         //                    Debug.logInfo("val.toString(): " + val.toString(), module);
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
         Debug.logInfo("productId: " + invoiceId, module);

         GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), delegator);
         if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
         PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, ControllerOptions.getSession());
         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
         }
         else{
         SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, ControllerOptions.getSession());
         salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane, null);                            
         }
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }
         }
         }
         };

         panel.getProductTreeActionTableCellEditor().addActionListener(invoiceIdChangeAction);
         */
        if (productId != null) {
            loadProduct(productId);
            //set order actions
            setProductToActions(panel.getProductComposite());
            setCaption(contFrame);
        } else {
            /*            buttonPanel.btnSaveOrder.setEnabled(true);
             buttonPanel.btnApproveOrder.setEnabled(false);
             buttonPanel.btnCancelOrder.setEnabled(false);
             */
        }

    }

    void setCaption(final ContainerPanelInterface contFrame) {
        contFrame.setCaption(getCaption());
    }
//set and handle all dialog product actions

    public void loadProduct(String productId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());
        if (productComposite != null) {
            //clear dialog
            panel.clearDialogFields();

            //set order
            panel.setProductComposite(productComposite);

            //update the dialog
            panel.setDialogField();
        } else {
            JOptionPane.showMessageDialog(null, "Product not found: " + productId, "Load Product", JOptionPane.YES_NO_OPTION);
        }
    }

    public void setProductToActions(ProductComposite productComposite) {

        /*      orderListModel.clear();
         orderListModel.add(order);
         invoiceCompositeListModel.clear();
         copyToButton.loadPopMenu(order);

         if (order != null && order.getOrderHeader().getOrderId() != null && order.getOrderHeader().getOrderId().isEmpty() == false) {

         if ("ORDER_CREATED".equals(order.getOrderHeader().getStatusId())) {
         boolean isEnabled = true;
         buttonPanel.btnSaveOrder.setEnabled(isEnabled);
         buttonPanel.btnApproveOrder.setEnabled(isEnabled);
         buttonPanel.btnCancelOrder.setEnabled(isEnabled);
         }

         if ("ORDER_APPROVED".equals(order.getOrderHeader().getStatusId())) {
         boolean isEnabled = true;
         buttonPanel.btnSaveOrder.setEnabled(isEnabled);
         buttonPanel.btnApproveOrder.setEnabled(false);
         buttonPanel.btnCancelOrder.setEnabled(isEnabled);
         }

         if ("ORDER_COMPLETED".equals(order.getOrderHeader().getStatusId())
         || "ORDER_CANCELLED".equals(order.getOrderHeader().getStatusId())) {
         boolean isEnabled = false;
         buttonPanel.btnSaveOrder.setEnabled(isEnabled);
         buttonPanel.btnApproveOrder.setEnabled(isEnabled);
         buttonPanel.btnCancelOrder.setEnabled(isEnabled);
         }

         }
         */
    }

    @Override
    public String getClassName() {
        return module;
    }

}
