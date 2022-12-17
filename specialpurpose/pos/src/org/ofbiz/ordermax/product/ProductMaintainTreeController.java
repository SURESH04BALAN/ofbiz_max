/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import mvc.controller.ProductIdMaintainVerifyValidator;
import mvc.controller.ProductIdVerifyValidator;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadSupplierProductFromFileWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.price.ProductPricePanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductMaintainTreeController extends BaseMainScreen {

    public static final String module = ProductMaintainTreeController.class.getName();
    public ProductTreePanelFrame panel = null;
//    public ProductTreePanelFrame treePanelFrame=null;
    public final String caption = "Product Maintain";
    final ListAdapterListModel<ProductComposite> orderListModel = new ListAdapterListModel<ProductComposite>();
    private String productId = null;
    ControllerOptions options = null;

    static public ProductMaintainTreeController runController(ControllerOptions options) {

        ProductMaintainTreeController controller = new ProductMaintainTreeController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ProductMaintainTreeController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ProductMaintainTreeController.module, options.getDesktopPane());
        }
        return controller;
    }

    public String getCaption() {
        if (options.isReadOnly() == false) {
            String cap = isNew ? " [New]" : " []";
            return caption + cap;
        } else {
            return "Product Enquire (Read Only)";
        }
    }

    private ProductMaintainTreeController(ListAdapterListModel<ProductComposite> ordListModel, ControllerOptions options, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        this.options = options;
    }

    private ProductMaintainTreeController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
        if (UtilValidate.isNotEmpty(options.getProductId())) {
            this.productId = options.getProductId();
        }

    }

    ProductCompositeButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<ProductComposite> productCompositeListModel = new ListAdapterListModel<ProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }
    boolean isNew = true;

    @Override
    public void loadScreen(final ContainerPanelInterface contFrame) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new ProductTreePanelFrame(options);

//        panel.setReceiveInventoryList(productCompositeListModel);
        buttonPanel = new ProductCompositeButtonPanel();
        buttonPanel.btnNew.setEnabled(!options.isReadOnly());
        buttonPanel.btnSave.setEnabled(!options.isReadOnly());

//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, contFrame.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, contFrame.getPanelButton());

        if (options.isReadOnly()) {
            //order selection button
            ActionListener productIdTextChangeAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() instanceof ProductIdVerifyValidator) {
                        ProductIdVerifyValidator validator = (ProductIdVerifyValidator) e.getSource();
                        String productId = validator.getField().getText();
//                        Debug.logInfo("orderId: " + orderId, module);
                        loadProduct(productId);
                        //set order actions
                        if (panel.getProductComposite() != null) {
                            setProductToActions(panel.getProductComposite());
                            Debug.logInfo("panel.getOrder(): " + panel.getProductComposite().getProduct().getproductId(), module);
                        }
                        setCaption(contFrame);
                    }
                }
            };

            ProductIdVerifyValidator prodValidator = new ProductIdVerifyValidator(panel.getProductIdTextField(), true, ControllerOptions.getSession());
            prodValidator.addActionListener(productIdTextChangeAction);
            panel.getProductIdTextField().setInputVerifier(prodValidator);
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
                            if (panel.getProductComposite() != null && UtilValidate.isNotEmpty(productId)
                                    && !productId.equals(panel.getProductComposite().getProduct().getproductId()) && !isNew) {
                                createNewProduct(productId);
                            }

                            /*panel.clearDialogFields();
                             ProductComposite prodComp = LoadProductWorker.newProduct();
                             prodComp.getProduct().setproductId(productId);
                             panel.setProductComposite(prodComp);
                             panel.setDialogField();
                             */
                        }
                        setCaption(contFrame);
                    }
                }
            };

            ProductIdMaintainVerifyValidator prodValidator = new ProductIdMaintainVerifyValidator(panel.getProductIdTextField(), true, ControllerOptions.getSession());
            prodValidator.addActionListener(productIdTextChangeAction);
            panel.getProductIdTextField().setInputVerifier(prodValidator);
        }

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contFrame.okButtonPressed();
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                panel.getDialogField();
                /*panel.clearDialogFields();
                 panel.getProductDetail().setIsEnable(false);
                 ProductComposite prodComp = LoadProductWorker.newProduct();
                 panel.setProductComposite(prodComp);
                 panel.setDialogField();
                 isNew = true;*/
                createNewProduct(null);
                setCaption(contFrame);
            }
        });

        buttonPanel.btnNew.getActionListeners()[0].actionPerformed(null);

        buttonPanel.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ClassLoader cl = getClassLoader();
                Thread.currentThread().setContextClassLoader(cl);

                panel.getDialogField();
                String productId = panel.getProductComposite().getProduct().getproductId();
                org.ofbiz.ordermax.base.TreeNode node = panel.productSelectionTreePanel.getSelectedTreeNode(ProductCategoryTreeNode.ProductCategoryTreeNodeName);
                if (node == null) {
                    JOptionPane.showMessageDialog(null, "Please select default hierarchy", "Save Product", JOptionPane.YES_NO_OPTION);
                } else {
                    panel.getProductComposite().setCategoryTreeDef(node);
                    LoadProductWorker.saveProduct(panel.getProductComposite(), ControllerOptions.getSession());
                    if (isNew) {
                        panel.addProductTreeNode(node, new ProductTreeNode(productId, panel.getProductComposite().getProduct().getinternalName(), true, panel.getProductComposite().getProduct().getGenericValueObj()));
                        panel.productSelectionTreePanel.findFromId(productId);
                    }

                    if (panel.isNeedSavingPrices()) {
                        saveProductPrice(panel.getProductComposite());
                        saveSupplierProduct(panel.getProductComposite());
                    }

                    isNew = false;
                    setCaption(contFrame);
                    panel.getProductDetail().setIsEnable(true);
                }
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
         //                    invList = LoadInvoiceWorker.loadInvoices(findOptionList, session);

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
         PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, session);
         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
         }
         else{
         SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, session);
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

    public void createNewProduct(String productId) {
        //                panel.getDialogField();
        panel.clearDialogFields();
        panel.getProductDetail().setIsEnable(false);
        ProductComposite prodComp = LoadProductWorker.newProduct();
        prodComp.getProduct().setproductId(productId);
        if (panel.isNeedSavingPrices()) {

            try {
                ProductPriceComposite productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productId, "DEFAULT_PRICE", ControllerOptions.getSession());
                prodComp.getProductItemPrice().addProductPrice(productPriceComposite);
                productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productId, "LIST_PRICE", ControllerOptions.getSession());
                prodComp.getProductItemPrice().addProductPrice(productPriceComposite);
                productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productId, "AVERAGE_COST", ControllerOptions.getSession());
                prodComp.getProductItemPrice().addProductPrice(productPriceComposite);
            } catch (Exception ex) {
                Logger.getLogger(ProductMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
            }

            SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(prodComp.getProduct().getproductId());
            spComp.getSupplierProduct().setsupplierProductName(prodComp.getProduct().getinternalName());
            spComp.getSupplierProduct().setsupplierProductId(prodComp.getProduct().getproductId());
            prodComp.getSupplierProductList().add(spComp);

            GoodIdentification goodIdentification = LoadProductWorker.createNewGoodsIdentification("", "EAN");
            prodComp.getGoodIdentificationList().add(goodIdentification);
        }
        panel.setProductComposite(prodComp);
        panel.setDialogField();
        isNew = true;

    }

    public void loadProduct(String productId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());
        if (productComposite != null) {
            //clear dialog
            panel.clearDialogFields();
            panel.getProductDetail().setIsEnable(true);
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

    void saveSupplierProduct(ProductComposite productComposite) {
        if (productComposite != null) {

            Debug.logInfo("saveSupplierProduct: ", "module");
            SupplierProductComposite supplierProductComp = productComposite.getSupplierProductList().getCurrentSupplierProduct();
            if (supplierProductComp != null
                    && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getproductId())
                    && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getpartyId())) {
                Debug.logInfo("btnSaveTelephoneActionPerformed productId: " + supplierProductComp.getSupplierProduct().getproductId()
                        + " PartyId: " + supplierProductComp.getSupplierProduct().getpartyId(), module);
                LoadSupplierProductFromFileWorker.saveSupplierProduct(supplierProductComp, ControllerOptions.getSession());

                Debug.logInfo("saveSupplierProduct saved sucess: ", module);
            }
            Debug.logInfo("saveSupplierProduct: end", "module");
        }
    }

    void saveProductPrice(ProductComposite productComposite) {
        Debug.logInfo("ProductComposite: " + 1, "module");
        if (productComposite != null) {
            Debug.logInfo("ProductComposite: " + 1.5, "module");
            if (productComposite.getProductItemPrice().getListPrice() != null) {
                Debug.logInfo("ProductComposite: " + 1.9, "module");
                ProductPriceComposite value = productComposite.getProductItemPrice().getListPrice().getCurrentPrice();
                if (value != null) {
                    Debug.logInfo("ProductComposite: " + 2, "module");
                    saveProductPrice(value);
                }
            }

            if (productComposite.getProductItemPrice().getDefaultPrice() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getDefaultPrice().getCurrentPrice();
                if (value != null) {
                    saveProductPrice(value);
                }

            }

            if (productComposite.getProductItemPrice().getAverageCost() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getAverageCost().getCurrentPrice();
                if (value != null) {
                    saveProductPrice(value);
                }
            }
        }
    }

    void saveProductPrice(ProductPriceComposite productPriceComposite) {
        if (productPriceComposite != null
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPricePurposeId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPriceTypeId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductStoreGroupId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getcurrencyUomId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getfromDate())) {

            try {
                Debug.logInfo("product price: " + productPriceComposite.getProductPrice().getprice(), "module");
                LoadProductPriceWorker.saveProductPrice(productPriceComposite.getProductPrice(), ControllerOptions.getSession());
            } catch (Exception ex) {
                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Debug.logInfo("getproductId: " + productPriceComposite.getProductPrice().getproductId(), "module");
            Debug.logInfo("getproductPricePurposeId: " + productPriceComposite.getProductPrice().getproductPricePurposeId(), "module");
            Debug.logInfo("getproductPriceTypeId: " + productPriceComposite.getProductPrice().getproductPriceTypeId(), "module");
            Debug.logInfo("getproductStoreGroupId: " + productPriceComposite.getProductPrice().getproductStoreGroupId(), "module");
            Debug.logInfo("getcurrencyUomId: " + productPriceComposite.getProductPrice().getcurrencyUomId(), "module");
            Debug.logInfo("getfromDate: " + productPriceComposite.getProductPrice().getfromDate().toString(), "module");
        }
    }

    @Override
    public String getClassName() {
        return module;
    }

}
