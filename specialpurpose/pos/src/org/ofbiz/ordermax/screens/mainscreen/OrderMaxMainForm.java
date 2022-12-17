/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.mainscreen;

import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilationFailedException;
import com.openbravo.pos.config.PosConfigurationAction;
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.AppView;
//import com.openbravo.pos.scale.DeviceScale;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceAction;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity;
import org.ofbiz.ordermax.screens.action.ProductMaintainTreeAction;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelContainerPanel;
import org.ofbiz.ordermax.billingaccount.NewFindBillingAccountListController;
import org.ofbiz.ordermax.dataimportexport.ImportExportDataAction;
import org.ofbiz.ordermax.orderbase.orderviews.OrderViewDetailAction;
import org.ofbiz.ordermax.orderbase.returns.FindPurchaseOrderReturnListAction;
import org.ofbiz.ordermax.orderbase.returns.FindSalesOrderReturnListAction;
import org.ofbiz.ordermax.orderbase.returns.PurchaseOrderReturnAction;
import org.ofbiz.ordermax.orderbase.returns.SalesOrderReturnAction;
import org.ofbiz.ordermax.orderbase.shoppinglist.FindCustomerShoppingCartListAction;
import org.ofbiz.ordermax.party.FindCustomerPartyListAction;
import org.ofbiz.ordermax.party.FindSupplierPartyListAction;
import org.ofbiz.ordermax.price.ProductPriceMaintainTreeListAction;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMaintainAction;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMaintainDialog;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMemberMaintainAction;
import org.ofbiz.ordermax.screens.TableFTFEditDemo;
import org.ofbiz.ordermax.party.NewPartyAction;
import org.ofbiz.ordermax.payment.paymentgroup.NewPaymentGroupListController;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentGroupInvoiceController;
import org.ofbiz.ordermax.pdf.PdfFileViewerAction;
import org.ofbiz.ordermax.posterminal.PosTerminalDetailAction;
import org.ofbiz.ordermax.product.catalog.CategoryManagePanel;
import org.ofbiz.ordermax.product.catalog.ProductCatalogMaintainAction;
import org.ofbiz.ordermax.product.feature.ProductFeatureMaintainTreeController;
import org.ofbiz.ordermax.product.printlabel.ProductPrintLabelAction;
import org.ofbiz.ordermax.product.printshelflabel.ProductPrintShelfLabelAction;
import org.ofbiz.ordermax.productstore.ProductStoreSetupMenuAction;
import org.ofbiz.ordermax.report.reports.PosReportMainAction;
import org.ofbiz.ordermax.screens.action.ProductMaintainPosTreeAction;
import org.ofbiz.ordermax.screens.action.PurchaseOrderAction;
import org.ofbiz.ordermax.screens.action.SalesOrderAction;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class OrderMaxMainForm extends javax.swing.JFrame implements AppView {

    public static final String module = OrderMaxMainForm.class.getName();
    public final XuiSession session;
    ControllerOptions options = new ControllerOptions();

    static {
        try {
            Class.forName("org.ofbiz.ordermax.screens.action.ARSalesInvoiceAction");
//            Class.forName("org.ofbiz.ordermax.screens.action.ClientAction");
//            Class.forName("org.ofbiz.ordermax.screens.action.ProductAction");
            Class.forName("org.ofbiz.ordermax.screens.action.PurchaseOrderAction");
//            Class.forName("org.ofbiz.ordermax.screens.action.SalesInvoicePaymentAction");
            Class.forName("org.ofbiz.ordermax.screens.action.SalesOrderAction");
            Class.forName("org.ofbiz.ordermax.screens.action.SupplierClientAction");
            Class.forName("org.ofbiz.ordermax.screens.action.ScreenActionFactory");
            Class.forName("org.ofbiz.ordermax.screens.action.ListPriceLookupAction");
        } catch (ClassNotFoundException any) {
            any.printStackTrace();
        }
    }

    /**
     * Creates new form OrderMaxMainForm
     */
    public OrderMaxMainForm(XuiSession session) {

        initComponents();
        this.session = session;
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        createScreenAction();
        ComponentBorder.changeFont(menuBar, new java.awt.Font("Tahoma", 0, 13));
    }

    public OrderMaxMainForm() {
        initComponents();
        this.session = null;
        createScreenAction();

    }

    protected void createScreenAction() {
        /*
         File userDir = new File(System.getProperty("user.dir"));
         File[] files = userDir.listFiles();

         JMenu menu = new JMenu("Recent Files");
         JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
         //        JLabel label = new JLabel(" ", JLabel.CENTER);
         for (File f : files) {
         if (f.isFile() && !f.isHidden()) {
         ProductAction rf = new ProductAction(f, jLabel1);
         menu.add(new JMenuItem(rf.getAction()));
         toolBar.add(rf.getAction());
         }
         }
         //        JMenuBar menuBar = new JMenuBar();
         menuBar.add(menu);
         */

        menuBar.add(new WindowMenu((MDIDesktopPane) desktoppane));
        session.setDesktopPane(desktoppane);
        createUtilityMenuAction();
        createProductMenuAction();
        createCatalogCategoryMenuAction();
        createCustomerMenuAction();
        createSupplierMenuAction();
        createSalesPaymentMenuAction();
        createPurchasePaymentMenuAction();
        createSalesOrderMenuAction();
        createCustomerShoppingListMenuAction();
        createPurchaseOrderMenuAction();
        createSalesInvoiceMenuAction();
        createPurchaseInvoiceMenuAction();
        createInventoryMenuAction();
        createSupplierProductAction();
        createProductPricingAction();
        createAccountMenagerMenuAction();
        createUtilityMenu();
        createReportMenu();
        createPosDesignMenuAction();
        createProductStoreMenuAction();
//        mnuPurchaseOrderCreate
        //clientAction.setActionButtonItem(btnClient);
//        ScreenAction screenAction = new ARSalesInvoiceAction("Sales Order", session, desktoppane);
//screenAction = new ARSalesInvoiceAction("Sales Order", session, desktoppane);
//screenAction = new SalesInvoicePaymentAction("Sales Order", session, desktoppane);
//        ClientAction clientAction = new ClientAction("Product Setup", session, desktopPane);
//        clientAction.setActionMenuItem(productEnquireMenuItem);
//        clientAction.setActionButtonItem(btnProduct);
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel("status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
    }

    void createUtilityMenuAction() {
        ImportExportDataAction importDataAction = new ImportExportDataAction("Import Data From File", session, desktoppane);
        importDataAction.setActionMenuItem(mnuImportDataFromFile);
    }

    void createSupplierProductAction() {

        org.ofbiz.ordermax.product.supplierproduct.FindSupplierProductlListAction findSupplierProductlListAction
                = new org.ofbiz.ordermax.product.supplierproduct.FindSupplierProductlListAction("Supplier Product List", session, desktoppane);
//        findProductListAction.setActionMenuItem(productEnquireMenuItem);
        mnuSupplierProductGroup.add(findSupplierProductlListAction.createActionMenuItem());

        org.ofbiz.ordermax.product.supplierproduct.MaintainSupplierProductAction maintainSupplierProductAction
                = new org.ofbiz.ordermax.product.supplierproduct.MaintainSupplierProductAction("Product Pricing Maintain", session, desktoppane);
//        findProductListAction.setActionMenuItem(productEnquireMenuItem);
        mnuSupplierProductGroup.add(maintainSupplierProductAction.createActionMenuItem());
    }

    void createProductPricingAction() {

        org.ofbiz.ordermax.price.FindProductPriceListAction findProductPriceListAction
                = new org.ofbiz.ordermax.price.FindProductPriceListAction("Find Product Price", session, desktoppane);
//        findProductListAction.setActionMenuItem(productEnquireMenuItem);
        mnProductPricingGroup.add(findProductPriceListAction.createActionMenuItem());

        ProductPriceMaintainTreeListAction findSupplierProductlListAction
                = new ProductPriceMaintainTreeListAction("Product Bulk Pricing List", session, desktoppane);
//        findProductListAction.setActionMenuItem(productEnquireMenuItem);
        mnProductPricingGroup.add(findSupplierProductlListAction.createActionMenuItem());

    }

    void createProductMenuAction() {
        org.ofbiz.ordermax.product.FindProductListAction findProductListAction = new org.ofbiz.ordermax.product.FindProductListAction("Product Search", session, desktoppane);
        findProductListAction.setActionMenuItem(productEnquireMenuItem);
        mnuProducts.add(findProductListAction.createActionMenuItem());
        findProductListAction.setActionButtonItem(btnFindProducts);

        ProductPrintLabelAction productPrintLabelAction
                = new ProductPrintLabelAction("Print Packing Labels", session, desktoppane);
        mnuProducts.add(productPrintLabelAction.createActionMenuItem());
        productPrintLabelAction.setActionButtonItem(btnPrintLabel);
        btnPrintLabel.setText("Print Packing Labels");

        ProductPrintShelfLabelAction productPrintShelfLabelAction
                = new ProductPrintShelfLabelAction("Print Shelf Labels", session, desktoppane);
        mnuProducts.add(productPrintShelfLabelAction.createActionMenuItem());
        productPrintShelfLabelAction.setActionButtonItem(btnPrintProductShelfLabel);
        btnPrintProductShelfLabel.setText("Print Shelf Labels");
        
//        org.ofbiz.ordermax.product.ProductEnquireAction productEnquireAction = new org.ofbiz.ordermax.product.ProductEnquireAction(org.ofbiz.ordermax.product.ProductEnquireAction.nameStr, session, options, desktoppane);
//        productEnquireAction.setActionMenuItem(productMaintainMenuItem);
//        jMenu22.add(productEnquireAction.createActionMenuItem());
        org.ofbiz.ordermax.product.ProductMaintainAction productMaintainAction = new org.ofbiz.ordermax.product.ProductMaintainAction(org.ofbiz.ordermax.product.ProductMaintainAction.nameStr, session, desktoppane);
        productMaintainAction.setActionMenuItem(productMaintainMenuItem);
        mnuProducts.add(productMaintainAction.createActionMenuItem());

        ProductMaintainTreeAction proddAction = new ProductMaintainTreeAction(ProductMaintainTreeAction.nameStr, options, session, desktoppane);
        proddAction.setActionMenuItem(productEnquireMenuItem1);
        proddAction.setActionButtonItem(btnProduct);
        mnuProducts.add(proddAction.createActionMenuItem());

        org.ofbiz.ordermax.product.ProductMaintainPosAction productMaintainPosAction = new org.ofbiz.ordermax.product.ProductMaintainPosAction(org.ofbiz.ordermax.product.ProductMaintainAction.nameStr, session, desktoppane);
//        productMaintainPosAction.setActionMenuItem(productMaintainMenuItem);
        mnuProducts.add(productMaintainPosAction.createActionMenuItem());

        ProductMaintainPosTreeAction productMaintainPosTreeAction = new ProductMaintainPosTreeAction(ProductMaintainTreeAction.nameStr, options, session, desktoppane);
//        proddAction.setActionMenuItem(productEnquireMenuItem1);
//        proddAction.setActionButtonItem(btnProduct);
        mnuProducts.add(productMaintainPosTreeAction.createActionMenuItem());

        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductFeature");
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Feature", controllerOptions);
        JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        mnuProductFeature.add(mnuItem);

        controllerOptions = new ControllerOptions(options);
        ProductFeatureMaintainTreeController.addToMenu(options, mnuProductFeature);

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductFeatureGroup");
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction productFeatureGroup = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Feature Group", controllerOptions);
        mnuItem = productFeatureGroup.createActionMenuItem();
        mnuProductFeature.add(mnuItem);

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductFeatureGroupAppl");
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction productFeatureGroupAppl = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Feature Group Appl", controllerOptions);
        mnuItem = productFeatureGroupAppl.createActionMenuItem();
        mnuProductFeature.add(mnuItem);

        /*        ControllerOptions controllerOptions = new ControllerOptions(options);
         controllerOptions.put("EntityName", "Facility");
         ProductStoreConfigMenuAction productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Facility", controllerOptions);
         JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
         parentMenu.add(mnuItem);        
         */
    }

    void createCatalogCategoryMenuAction() {
        ProductCatalogMaintainAction productCatalogMaintainAction = new ProductCatalogMaintainAction(ProductCatalogMaintainAction.nameStr, session, desktoppane);
        productCatalogMaintainAction.setActionMenuItem(mnuProductCategoryMaintain);
        mnuCatalog.add(productCatalogMaintainAction.createActionMenuItem());

//        ControllerOptions options = new ControllerOptions();
//        PosProductCreateAction posProductCreateAction = new PosProductCreateAction(options);
//        mnuCatalogCategory.add(posProductCreateAction.createActionMenuItem());
//        ProductBulkMaintainTreeListAction productBulkMaintainTreeListAction = new ProductBulkMaintainTreeListAction(ProductBulkMaintainTreeListAction.nameStr, options);
//        mnuCatalogCategory.add(productBulkMaintainTreeListAction.createActionMenuItem());
    }

    void createCustomerMenuAction() {
        FindCustomerPartyListAction findPartyListAction = new FindCustomerPartyListAction("Customer List", session, desktoppane);
        mnuCustomers.add(findPartyListAction.createActionMenuItem());
        findPartyListAction.setActionButtonItem(btnFindCustomers);

        ControllerOptions options = new ControllerOptions();
        options.addPartyType("PERSON");
        options.addRoleType("CUSTOMER");
        options.addName("Create Person");
        NewPartyAction clientAction = new NewPartyAction(options);
        mnuCustomers.add(clientAction.createActionMenuItem());
        clientAction.setActionButtonItem(btnCustomer);

        ControllerOptions optionsGroup = new ControllerOptions();
        optionsGroup.addPartyType("PARTY_GROUP");
        optionsGroup.addRoleType("CUSTOMER");
        optionsGroup.addName("Create Company");
        NewPartyAction groupAction = new NewPartyAction(optionsGroup);
        mnuCustomers.add(groupAction.createActionMenuItem());
        clientAction.setActionButtonItem(btnCustomer);
//        clientAction.setActionMenuItem(customerEnquireMenuItem);
//        clientAction.setActionButtonItem(btnClient);
    }

    void createSupplierMenuAction() {

        FindSupplierPartyListAction findSupplierPartyListAction = new FindSupplierPartyListAction("Suppliers List", session, desktoppane);
        mnuSupplier.add(findSupplierPartyListAction.createActionMenuItem());
//        supplierAction.setActionMenuItem(supplierEnquireMenuItem);
        findSupplierPartyListAction.setActionButtonItem(btnFindSupplier);

//        SupplierClientAction supplierAction = new SupplierClientAction("Suppliers", session, desktoppane);
//        mnuSupplier.add(supplierAction.createActionMenuItem());
//        supplierAction.setActionMenuItem(supplierEnquireMenuItem);
//        supplierAction.setActionButtonItem(btnSupplier);
        ControllerOptions options = new ControllerOptions();
        options.addPartyType("PERSON");
        options.addRoleType("SUPPLIER");
        options.addName("Create Person");
        NewPartyAction clientAction = new NewPartyAction(options);
        mnuSupplier.add(clientAction.createActionMenuItem());
        clientAction.setActionButtonItem(btnSupplier);

        ControllerOptions optionsGroup = new ControllerOptions();
        optionsGroup.addPartyType("PARTY_GROUP");
        optionsGroup.addRoleType("SUPPLIER");
        optionsGroup.addName("Create Company");
        NewPartyAction groupAction = new NewPartyAction(optionsGroup);
        mnuSupplier.add(groupAction.createActionMenuItem());
        clientAction.setActionButtonItem(btnSupplier);

    }

    void createSalesPaymentMenuAction() {
        org.ofbiz.ordermax.payment.FindSalesPaymentListAction findSalesPaymentListAction = new org.ofbiz.ordermax.payment.FindSalesPaymentListAction("Customer Payment List", session, desktoppane);
        findSalesPaymentListAction.setActionMenuItem(mnuPaymentList);
        findSalesPaymentListAction.setActionButtonItem(btnFindSalesPayment);

        //      SavePaymentAction createPaymentAction = new SavePaymentAction("Create Payment", session, desktoppane, this);
        //      createPaymentAction.setActionMenuItem(mnuCreatePayment);
        org.ofbiz.ordermax.payment.sales.CustomerPaymentListAction customerPaymentListAction = new org.ofbiz.ordermax.payment.sales.CustomerPaymentListAction("Customer Payment", session, desktoppane);
        customerPaymentListAction.setActionMenuItem(mnuCreatePayment);
        customerPaymentListAction.setActionButtonItem(btnSalesPayment);

        CustomerPaymentGroupInvoiceController.addToMenu(options, mnuCustomerPayment);
//        customerPaymentGroupAction.setActionMenuItem(mnuCreatePaymentGroup);
//        customerPaymentGroupAction.setActionButtonItem(btnSalesPayment);        
    }

    void createPurchasePaymentMenuAction() {
        org.ofbiz.ordermax.payment.FindPurchasePaymentListAction findPurchasePaymentListAction = new org.ofbiz.ordermax.payment.FindPurchasePaymentListAction("Supplier Payment List", session, desktoppane);
        findPurchasePaymentListAction.setActionMenuItem(mnuPaymentList1);
        findPurchasePaymentListAction.setActionButtonItem(btnFindPurchasePayment);
//        SavePaymentAction createPaymentAction = new SavePaymentAction("Create Payment", session, desktoppane, this);
//        createPaymentAction.setActionMenuItem(mnuCreatePayment1);
        org.ofbiz.ordermax.payment.purchase.SupplierPaymentListAction supplierPaymentListAction = new org.ofbiz.ordermax.payment.purchase.SupplierPaymentListAction("Supplier Payment", session, desktoppane);
        supplierPaymentListAction.setActionMenuItem(mnuCreatePayment1);
        supplierPaymentListAction.setActionButtonItem(btnPurchasePayment);
    }

    void createCustomerShoppingListMenuAction() {
        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addName("Sales Order List");
        controllerOptions.addOrderType("SALES_ORDER");
        controllerOptions.addRoleTypeParent("CUSTOMER");
        controllerOptions.addParentPaymentTypeId("RECEIPT");

        FindCustomerShoppingCartListAction findCustomerShoppingCartListAction = new FindCustomerShoppingCartListAction("Purchase Order Return List", session, desktoppane);

        JMenuItem mnuItem = findCustomerShoppingCartListAction.createActionMenuItem();
        mnuShoppingList.add(mnuItem);

//        controllerOptions.put("EntityName", "ShoppingListItem");
        //       ProductStoreSetupMenuAction.ProductStoreConfigMenuAction productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Shopping List Item", controllerOptions);
//        productStoreConfigMenuAction.setActionMenuItem(mnuMaintainFrequentOrderItem);
//JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        //mnuCatalog.add(mnuItem);
        ControllerOptions options = controllerOptions.getCopy();
        options.put("EntityName", "ShoppingList");
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction shoppingListMnu = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Shopping List", options);
//        productStoreConfigMenuAction.setActionMenuItem(mnuMaintainFrequentOrderItem);        
        mnuItem = shoppingListMnu.createActionMenuItem();
        mnuShoppingList.add(mnuItem);

    }

    void createSalesOrderMenuAction() {
        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addName("Sales Order List");
        controllerOptions.addOrderType("SALES_ORDER");
        controllerOptions.addRoleTypeParent("CUSTOMER");
        controllerOptions.addParentPaymentTypeId("RECEIPT");
        org.ofbiz.ordermax.orderbase.FindSalesOrderListAction findSalesOrderListAction = new org.ofbiz.ordermax.orderbase.FindSalesOrderListAction(controllerOptions);
        findSalesOrderListAction.setActionMenuItem(mnuSalesOrderList);
        findSalesOrderListAction.setActionButtonItem(btnFindSalesOrder);

        SalesOrderAction salesAction = new SalesOrderAction("Sales Order", session, desktoppane);
        salesAction.setActionMenuItem(mnuSalesOrderCreate);
        salesAction.setActionButtonItem(btnSalesOrder);

        GeneratePurchaseInvoiceAction generateSalesInvoiceAction = new GeneratePurchaseInvoiceAction("Sales Invoice ", session, desktoppane);
        generateSalesInvoiceAction.setActionMenuItem(mnuGenerateInvoiceFromOrderList);
        generateSalesInvoiceAction.setActionButtonItem(btnSalesInvoice);

        FindSalesOrderReturnListAction findSalesOrderReturnListAction = new FindSalesOrderReturnListAction("Sales Order Return List", session, desktoppane);
        findSalesOrderReturnListAction.setActionMenuItem(mnuSalesOrderReturnList);
        findSalesOrderReturnListAction.setActionButtonItem(btnFindSalesReturn);

        SalesOrderReturnAction salesOrderReturnAction = new SalesOrderReturnAction("Sales Order Return", session, desktoppane);
        salesOrderReturnAction.setActionMenuItem(mnuReturnSalesOrder);
        salesOrderReturnAction.setActionButtonItem(btnSalesReturn);

        OrderViewDetailAction orderViewDetailAction = new OrderViewDetailAction(OrderViewDetailAction.nameStr, session, desktoppane);
        mnuOrders.add(orderViewDetailAction.createActionMenuItem());

        PdfFileViewerAction pdfFileViewerAction = new PdfFileViewerAction(PdfFileViewerAction.nameStr, session, desktoppane);
        mnuOrders.add(pdfFileViewerAction.createActionMenuItem());

        //        findCustomerShoppingCartListAction.setActionButtonItem(btnFindSalesReturn);
        //  SalesOrderReturnAction salesOrderReturnAction = new SalesOrderReturnAction("Sales Order Return", session, desktoppane);
        //  salesOrderReturnAction.setActionMenuItem(mnuReturnSalesOrder);
        //  salesOrderReturnAction.setActionButtonItem(btnSalesReturn);        
    }

    void createPurchaseOrderMenuAction() {

        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addName("Purchase Order List");
        controllerOptions.addParentPaymentTypeId("DISBURSEMENT");
        controllerOptions.addOrderType("PURCHASE_ORDER");
        controllerOptions.addRoleTypeParent("SUPPLIER");

        org.ofbiz.ordermax.orderbase.FindSalesOrderListAction findSalesOrderListAction = new org.ofbiz.ordermax.orderbase.FindSalesOrderListAction(controllerOptions);
        findSalesOrderListAction.setActionMenuItem(findPurchaseOrderMnuItem);
//        findSalesOrderListAction.setActionButtonItem(btnFindSalesOrder);

        org.ofbiz.ordermax.orderbase.FindPurchaseOrderListAction findPurchaseOrderListAction = new org.ofbiz.ordermax.orderbase.FindPurchaseOrderListAction("Find Purchase Order List by status", session, desktoppane);
        findPurchaseOrderListAction.setActionMenuItem(mnuPurchaseOrderList);
        findPurchaseOrderListAction.setActionButtonItem(btnFindPurchaseOrder);

        PurchaseOrderAction purchaserAction = new PurchaseOrderAction("Purchaser Order", session, desktoppane);
        purchaserAction.setActionMenuItem(mnuPurchaseOrderCreate);
        purchaserAction.setActionButtonItem(btnPurchaseOrder);

        GeneratePurchaseInvoiceAction generatePurchaseInvoiceAction = new GeneratePurchaseInvoiceAction("Generate Purchase Invoice ", session, desktoppane);
        generatePurchaseInvoiceAction.setActionMenuItem(mnuGenerateInvoiceFromOrderList);
        generatePurchaseInvoiceAction.setActionButtonItem(btnPurchaseInvoice);

        FindPurchaseOrderReturnListAction findOrderReturnListAction = new FindPurchaseOrderReturnListAction("Purchase Order Return List", session, desktoppane);
        findOrderReturnListAction.setActionMenuItem(mnuPurchaseOrderReturnList);
        findOrderReturnListAction.setActionButtonItem(btnReport);

        PurchaseOrderReturnAction purchaseOrderReturnAction = new PurchaseOrderReturnAction("Purchase Order Return", session, desktoppane);
        purchaseOrderReturnAction.setActionMenuItem(mnuPurchaseOrderReturnCreate);
        purchaseOrderReturnAction.setActionButtonItem(btnPurchaseReturn);
    }

    void createSalesInvoiceMenuAction() {
        org.ofbiz.ordermax.invoice.FindSalesInvoiceListAction findSalesInvoiceListAction = new org.ofbiz.ordermax.invoice.FindSalesInvoiceListAction("Invoice List", session, desktoppane);
        findSalesInvoiceListAction.setActionMenuItem(mnuFindInvoiceListItem);
        findSalesInvoiceListAction.setActionButtonItem(btnFindSalesInvoice);

        org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction editSalesInvoiceAction = new org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction("Sales Invoice", session, desktoppane);
        editSalesInvoiceAction.setActionMenuItem(invoiceCreateMenuItem);
        editSalesInvoiceAction.setActionButtonItem(btnSalesInvoice);
    }

    void createAccountMenagerMenuAction() {
        //FindBillingAccountListAction findSalesInvoiceListAction = new FindBillingAccountListAction("Billing Account List", session, desktoppane);
        // findSalesInvoiceListAction.setActionMenuItem(mnuBillingAccount);
        NewFindBillingAccountListController.addToMenu(options, mnuAccountManager);
        NewPaymentGroupListController.addToMenu(options, mnuAccountManager);
//        org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction editSalesInvoiceAction = new org.ofbiz.ordermax.invoice.salesinvoice.EditSalesInvoiceAction("Sales Invoice", session, desktoppane, this);
//        editSalesInvoiceAction.setActionMenuItem(invoiceCreateMenuItem);
    }

    void createPurchaseInvoiceMenuAction() {
        org.ofbiz.ordermax.invoice.FindPurchaseInvoiceListAction findPurchaseInvoiceListAction = new org.ofbiz.ordermax.invoice.FindPurchaseInvoiceListAction("Invoice List", session, desktoppane);
        findPurchaseInvoiceListAction.setActionMenuItem(mnuPurchaseInvoiceList);
        findPurchaseInvoiceListAction.setActionButtonItem(btnFindPurchaseInvoice);

        org.ofbiz.ordermax.invoice.purchaseinvoice.EditPurchaseInvoiceAction editPurchaseInvoiceAction = new org.ofbiz.ordermax.invoice.purchaseinvoice.EditPurchaseInvoiceAction("Edit Invoice", session, desktoppane);
        editPurchaseInvoiceAction.setActionMenuItem(mnuEditPurchaseInvoice);
        editPurchaseInvoiceAction.setActionButtonItem(btnPurchaseInvoice);
    }

    void createInventoryMenuAction() {

        org.ofbiz.ordermax.inventory.FindFacilitynventoryByProductController.FindFacilityInventoryByProductAction findFacilityInventory = new org.ofbiz.ordermax.inventory.FindFacilitynventoryByProductController.FindFacilityInventoryByProductAction("Inventory Item List", session, desktoppane);
        findFacilityInventory.setActionMenuItem(mnuInventory);

        org.ofbiz.ordermax.inventory.FindInventoryItemListAction findInventoryItemListAction = new org.ofbiz.ordermax.inventory.FindInventoryItemListAction("Inventory Item List", session, desktoppane);
        findInventoryItemListAction.setActionMenuItem(mnuInventoryItemList);

        org.ofbiz.ordermax.inventory.FindInventoryItemAndDetailListAction findInventoryItemAndDetailListAction = new org.ofbiz.ordermax.inventory.FindInventoryItemAndDetailListAction("Inventory Item List", session, desktoppane);
        findInventoryItemAndDetailListAction.setActionMenuItem(mnuInventoryDetailList);

        org.ofbiz.ordermax.inventory.ProductFacilityMaintainTreeController.ProductFacilityAction productFacility = new org.ofbiz.ordermax.inventory.ProductFacilityMaintainTreeController.ProductFacilityAction("Inventory Item List", session, desktoppane);
        mnuMasterInventory.add(productFacility.createActionMenuItem());
//        findFacilityInventory.setActionMenuItem(mnuInventory);        
    }

    void createPosDesignMenuAction() {

        org.ofbiz.ordermax.pospaneldesigner.PosPanelDesignAction findInventoryItemListAction = new org.ofbiz.ordermax.pospaneldesigner.PosPanelDesignAction("Pos Panel Design", session, desktoppane);
        findInventoryItemListAction.setActionMenuItem(jMenuItem6);

        PosTerminalDetailAction posTerminalDetailAction = new PosTerminalDetailAction("Pos Terminal Details", session, desktoppane);
        posTerminalDetailAction.setActionMenuItem(mnuPosTerminalConfig);

        PosConfigurationAction posConfigurationAction = new PosConfigurationAction("Pos Configuration", session, desktoppane);
        posConfigurationAction.setActionMenuItem(mnuPosCOnfiguration);

    }

    protected void createUtilityMenu() {

        AboutDialogAction aboutDialogAction = new AboutDialogAction("About", session, desktoppane);
        aboutDialogAction.setActionMenuItem(mnuItemAbout);
    }

    protected void createProductStoreMenuAction() {
        ControllerOptions controllerOptions = new ControllerOptions();
        // controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProdCatalog");
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Catalog", controllerOptions);
        JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        mnuCatalog.add(mnuItem);

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductCategory");
        productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Category", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        mnuCategories.add(mnuItem);

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProdCatalogCategory");
        productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Product Catalog Category", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        mnuCategories.add(mnuItem);

        ProductCategoryMaintainAction productCategoryMaintainAction = new ProductCategoryMaintainAction(ProductCategoryMaintainAction.nameStr, options);
        mnuCategories.add(productCategoryMaintainAction.createActionMenuItem());

        ProductCategoryMemberMaintainAction productCategoryMemberMaintainAction = new ProductCategoryMemberMaintainAction(ProductCategoryMemberMaintainAction.nameStr, session, desktoppane);
        mnuCategories.add(productCategoryMemberMaintainAction.createActionMenuItem());

        ProductStoreSetupMenuAction.addProductStoreMenus(controllerOptions, mnuProductStoreSetup);

        CategoryManagePanel.CategoryCopyMoveAction categoryCopyMoveAction = new CategoryManagePanel.CategoryCopyMoveAction();
        JMenuItem menuItem = categoryCopyMoveAction.createActionMenuItem();
        mnuCategories.add(menuItem);
        //    AboutDialogAction aboutDialogAction = new AboutDialogAction("About", session, desktoppane);
        //    aboutDialogAction.setActionMenuItem(mnuItemAbout);
    }

    protected void createReportMenu() {
        //    ReportMainAction aboutDialogAction = new ReportMainAction("Supplier Ageing Report", session, desktoppane, this);
        //    aboutDialogAction.setActionMenuItem(reportSupplierPaymentAgeingReport);

//        InventoryItemReceiptReportAction inventoryItemReceiptReportAction = new InventoryItemReceiptReportAction("Report Main", session, desktoppane, this);
        //      inventoryItemReceiptReportAction.setActionMenuItem(mnuItemInventoryReceipt); 
        org.ofbiz.ordermax.report.reports.ReportMainAction reportMainAction = new org.ofbiz.ordermax.report.reports.ReportMainAction("Report Main", session, desktoppane);
        reportMainAction.setActionMenuItem(mnuItemReport);
        reportMainAction.setActionButtonItem(btnReport);

        PosReportMainAction posReportMainAction = new PosReportMainAction("New Report Main", session, desktoppane);
        posReportMainAction.setActionMenuItem(mnuItemPosReport);
        //  org.ofbiz.ordermax.invoice.purchaseinvoice.EditPurchaseInvoiceAction editPurchaseInvoiceAction = new org.ofbiz.ordermax.invoice.purchaseinvoice.EditPurchaseInvoiceAction("Edit Invoice", session, desktoppane, this);
        //  editPurchaseInvoiceAction.setActionMenuItem(mnuItemReport);
        //  editPurchaseInvoiceAction.setActionButtonItem(btnReport);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        btnProduct = new javax.swing.JButton();
        btnCustomer = new javax.swing.JButton();
        btnSupplier = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jPanel3 = new javax.swing.JPanel();
        btnFindProducts = new javax.swing.JButton();
        btnFindCustomers = new javax.swing.JButton();
        btnFindSupplier = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jPanel2 = new javax.swing.JPanel();
        btnSalesOrder = new javax.swing.JButton();
        btnSalesInvoice = new javax.swing.JButton();
        btnSalesPayment = new javax.swing.JButton();
        btnSalesReturn = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jPanel4 = new javax.swing.JPanel();
        btnPurchaseOrder = new javax.swing.JButton();
        btnPurchaseInvoice = new javax.swing.JButton();
        btnPurchasePayment = new javax.swing.JButton();
        btnPurchaseReturn = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jPanel5 = new javax.swing.JPanel();
        btnFindSalesOrder = new javax.swing.JButton();
        btnFindSalesInvoice = new javax.swing.JButton();
        btnFindSalesPayment = new javax.swing.JButton();
        btnFindSalesReturn = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jPanel6 = new javax.swing.JPanel();
        btnFindPurchaseOrder = new javax.swing.JButton();
        btnFindPurchaseInvoice = new javax.swing.JButton();
        btnFindPurchasePayment = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        javax.swing.JDesktopPane desktopPane = new MDIDesktopPane();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnPrintLabel = new javax.swing.JButton();
        btnPrintProductShelfLabel = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuImportDataFromFile = new javax.swing.JMenuItem();
        mnuImportData = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        masterfilesMenu = new javax.swing.JMenu();
        masterCustomerMenu = new javax.swing.JMenu();
        mnuCustomers = new javax.swing.JMenu();
        masterProductsMenu = new javax.swing.JMenu();
        mnuCatalog = new javax.swing.JMenu();
        mnuCategories = new javax.swing.JMenu();
        mnuProducts = new javax.swing.JMenu();
        mnuProductFeature = new javax.swing.JMenu();
        mnProductPricingGroup = new javax.swing.JMenu();
        mnuSupplierProductGroup = new javax.swing.JMenu();
        jMenu21 = new javax.swing.JMenu();
        productEnquireMenuItem = new javax.swing.JMenuItem();
        productMaintainMenuItem = new javax.swing.JMenuItem();
        mnuProductCategoryMaintain = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        productEnquireMenuItem1 = new javax.swing.JMenuItem();
        masterSuppliersMenu = new javax.swing.JMenu();
        mnuSupplier = new javax.swing.JMenu();
        mnuProductStoreSetup = new javax.swing.JMenu();
        masterGeneralLedgMenu = new javax.swing.JMenu();
        mnuPosPanelDesign = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        mnuPosTerminalConfig = new javax.swing.JMenuItem();
        jMenu35 = new javax.swing.JMenu();
        mnuPosCOnfiguration = new javax.swing.JMenuItem();
        masterPayrollMenu = new javax.swing.JMenu();
        jMenu27 = new javax.swing.JMenu();
        employeeEnquireMenuItem = new javax.swing.JMenuItem();
        employeeMaintainMenuItem = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem55 = new javax.swing.JMenuItem();
        jMenu28 = new javax.swing.JMenu();
        cutMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenu29 = new javax.swing.JMenu();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenu30 = new javax.swing.JMenu();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu31 = new javax.swing.JMenu();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenu32 = new javax.swing.JMenu();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenuItem56 = new javax.swing.JMenuItem();
        jMenu33 = new javax.swing.JMenu();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenuItem53 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        masterGeneralLedgMenu4 = new javax.swing.JMenu();
        jMenu48 = new javax.swing.JMenu();
        cutMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem62 = new javax.swing.JMenuItem();
        jMenuItem63 = new javax.swing.JMenuItem();
        jMenu49 = new javax.swing.JMenu();
        jMenuItem64 = new javax.swing.JMenuItem();
        jMenuItem65 = new javax.swing.JMenuItem();
        jMenuItem66 = new javax.swing.JMenuItem();
        mnuAccountManager = new javax.swing.JMenu();
        transactionsMenu = new javax.swing.JMenu();
        masterCustomerMenu1 = new javax.swing.JMenu();
        jMenu46 = new javax.swing.JMenu();
        orderViewMenuItem2 = new javax.swing.JMenuItem();
        salesOrderCreateMenuItem2 = new javax.swing.JMenuItem();
        mnuSalesOrderList2 = new javax.swing.JMenuItem();
        jMenu39 = new javax.swing.JMenu();
        salesOrderCreateMenuItem1 = new javax.swing.JMenuItem();
        orderViewMenuItem1 = new javax.swing.JMenuItem();
        mnuSalesOrderList1 = new javax.swing.JMenuItem();
        mnuOrders = new javax.swing.JMenu();
        mnuSalesOrderList = new javax.swing.JMenuItem();
        mnuSalesOrderCreate = new javax.swing.JMenuItem();
        mnuReturnsMain = new javax.swing.JMenu();
        mnuSalesOrderReturnList = new javax.swing.JMenuItem();
        mnuReturnSalesOrder = new javax.swing.JMenuItem();
        mnuShoppingList = new javax.swing.JMenu();
        mnuReturnsMain1 = new javax.swing.JMenu();
        jMenu37 = new javax.swing.JMenu();
        mnuFindInvoiceListItem = new javax.swing.JMenuItem();
        invoiceEnquireMenuItem = new javax.swing.JMenuItem();
        invoiceCreateMenuItem = new javax.swing.JMenuItem();
        mnuCustomerPayment = new javax.swing.JMenu();
        mnuPaymentList = new javax.swing.JMenuItem();
        mnuCreatePayment = new javax.swing.JMenuItem();
        jMenu40 = new javax.swing.JMenu();
        masterProductsMenu1 = new javax.swing.JMenu();
        jMenu42 = new javax.swing.JMenu();
        copyMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem67 = new javax.swing.JMenuItem();
        jMenuItem68 = new javax.swing.JMenuItem();
        jMenuItem69 = new javax.swing.JMenuItem();
        jMenuItem70 = new javax.swing.JMenuItem();
        jMenuItem71 = new javax.swing.JMenuItem();
        jMenu43 = new javax.swing.JMenu();
        jMenu44 = new javax.swing.JMenu();
        jMenu45 = new javax.swing.JMenu();
        masterSuppliersMenu1 = new javax.swing.JMenu();
        jMenu108 = new javax.swing.JMenu();
        findPurchaseOrderMnuItem = new javax.swing.JMenuItem();
        mnuPurchaseOrderList = new javax.swing.JMenuItem();
        mnuPurchaseOrderEnquire = new javax.swing.JMenuItem();
        mnuPurchaseOrderCreate = new javax.swing.JMenuItem();
        mnuPurchaseOrderReturnList = new javax.swing.JMenuItem();
        mnuPurchaseOrderReturnCreate = new javax.swing.JMenuItem();
        jMenu109 = new javax.swing.JMenu();
        mnuPurchaseInvoiceList = new javax.swing.JMenuItem();
        mnuEditPurchaseInvoice = new javax.swing.JMenuItem();
        mnuGenerateInvoiceFromOrderList = new javax.swing.JMenuItem();
        orderViewMenuItem3 = new javax.swing.JMenuItem();
        jMenu47 = new javax.swing.JMenu();
        mnuPaymentList1 = new javax.swing.JMenuItem();
        mnuCreatePayment1 = new javax.swing.JMenuItem();
        masterPayrollMenu1 = new javax.swing.JMenu();
        jMenu51 = new javax.swing.JMenu();
        cutMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem87 = new javax.swing.JMenuItem();
        jMenuItem88 = new javax.swing.JMenuItem();
        jMenuItem89 = new javax.swing.JMenuItem();
        jMenuItem90 = new javax.swing.JMenuItem();
        jMenuItem91 = new javax.swing.JMenuItem();
        jMenu52 = new javax.swing.JMenu();
        cutMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem92 = new javax.swing.JMenuItem();
        jMenuItem93 = new javax.swing.JMenuItem();
        jMenu53 = new javax.swing.JMenu();
        jMenuItem94 = new javax.swing.JMenuItem();
        jMenuItem95 = new javax.swing.JMenuItem();
        jMenuItem96 = new javax.swing.JMenuItem();
        jMenu54 = new javax.swing.JMenu();
        jMenuItem97 = new javax.swing.JMenuItem();
        jMenuItem98 = new javax.swing.JMenuItem();
        jMenuItem99 = new javax.swing.JMenuItem();
        jMenu55 = new javax.swing.JMenu();
        jMenuItem100 = new javax.swing.JMenuItem();
        jMenuItem101 = new javax.swing.JMenuItem();
        jMenuItem102 = new javax.swing.JMenuItem();
        jMenu56 = new javax.swing.JMenu();
        jMenuItem103 = new javax.swing.JMenuItem();
        jMenuItem104 = new javax.swing.JMenuItem();
        jMenuItem105 = new javax.swing.JMenuItem();
        jMenuItem106 = new javax.swing.JMenuItem();
        jMenu57 = new javax.swing.JMenu();
        jMenuItem107 = new javax.swing.JMenuItem();
        jMenuItem108 = new javax.swing.JMenuItem();
        jMenuItem109 = new javax.swing.JMenuItem();
        masterGeneralLedgMenu1 = new javax.swing.JMenu();
        jMenu58 = new javax.swing.JMenu();
        cutMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem110 = new javax.swing.JMenuItem();
        jMenuItem111 = new javax.swing.JMenuItem();
        jMenu59 = new javax.swing.JMenu();
        jMenuItem112 = new javax.swing.JMenuItem();
        jMenuItem113 = new javax.swing.JMenuItem();
        jMenuItem114 = new javax.swing.JMenuItem();
        mnuMasterInventory = new javax.swing.JMenu();
        mnuInventory = new javax.swing.JMenuItem();
        mnuInventoryItemList = new javax.swing.JMenuItem();
        mnuInventoryDetailList = new javax.swing.JMenuItem();
        reportsMenu = new javax.swing.JMenu();
        mnuItemReport = new javax.swing.JMenuItem();
        mnuItemPosReport = new javax.swing.JMenuItem();
        masterCustomerMenu2 = new javax.swing.JMenu();
        jMenu60 = new javax.swing.JMenu();
        cutMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem115 = new javax.swing.JMenuItem();
        jMenuItem116 = new javax.swing.JMenuItem();
        jMenuItem117 = new javax.swing.JMenuItem();
        jMenuItem118 = new javax.swing.JMenuItem();
        jMenuItem119 = new javax.swing.JMenuItem();
        jMenu61 = new javax.swing.JMenu();
        jMenu62 = new javax.swing.JMenu();
        jMenu63 = new javax.swing.JMenu();
        jMenu64 = new javax.swing.JMenu();
        jMenu65 = new javax.swing.JMenu();
        masterProductsMenu2 = new javax.swing.JMenu();
        jMenu66 = new javax.swing.JMenu();
        copyMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem120 = new javax.swing.JMenuItem();
        jMenuItem121 = new javax.swing.JMenuItem();
        jMenuItem122 = new javax.swing.JMenuItem();
        jMenuItem123 = new javax.swing.JMenuItem();
        jMenuItem124 = new javax.swing.JMenuItem();
        jMenu67 = new javax.swing.JMenu();
        jMenu68 = new javax.swing.JMenu();
        jMenu69 = new javax.swing.JMenu();
        masterSuppliersMenu2 = new javax.swing.JMenu();
        jMenu70 = new javax.swing.JMenu();
        reportSupplierPaymentAgeingReport = new javax.swing.JMenuItem();
        mnuItemReportMain = new javax.swing.JMenuItem();
        mnuItemInventoryReceipt = new javax.swing.JMenuItem();
        jMenuItem127 = new javax.swing.JMenuItem();
        jMenuItem128 = new javax.swing.JMenuItem();
        jMenu71 = new javax.swing.JMenu();
        jMenuItem129 = new javax.swing.JMenuItem();
        jMenuItem130 = new javax.swing.JMenuItem();
        jMenu72 = new javax.swing.JMenu();
        jMenuItem131 = new javax.swing.JMenuItem();
        jMenuItem132 = new javax.swing.JMenuItem();
        jMenuItem133 = new javax.swing.JMenuItem();
        jMenu73 = new javax.swing.JMenu();
        jMenuItem134 = new javax.swing.JMenuItem();
        jMenuItem135 = new javax.swing.JMenuItem();
        jMenuItem136 = new javax.swing.JMenuItem();
        jMenu74 = new javax.swing.JMenu();
        jMenuItem137 = new javax.swing.JMenuItem();
        jMenuItem138 = new javax.swing.JMenuItem();
        jMenuItem139 = new javax.swing.JMenuItem();
        masterPayrollMenu2 = new javax.swing.JMenu();
        jMenu75 = new javax.swing.JMenu();
        cutMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem140 = new javax.swing.JMenuItem();
        jMenuItem141 = new javax.swing.JMenuItem();
        jMenuItem142 = new javax.swing.JMenuItem();
        jMenuItem143 = new javax.swing.JMenuItem();
        jMenuItem144 = new javax.swing.JMenuItem();
        jMenu76 = new javax.swing.JMenu();
        cutMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem145 = new javax.swing.JMenuItem();
        jMenuItem146 = new javax.swing.JMenuItem();
        jMenu77 = new javax.swing.JMenu();
        jMenuItem147 = new javax.swing.JMenuItem();
        jMenuItem148 = new javax.swing.JMenuItem();
        jMenuItem149 = new javax.swing.JMenuItem();
        jMenu78 = new javax.swing.JMenu();
        jMenuItem150 = new javax.swing.JMenuItem();
        jMenuItem151 = new javax.swing.JMenuItem();
        jMenuItem152 = new javax.swing.JMenuItem();
        jMenu79 = new javax.swing.JMenu();
        jMenuItem153 = new javax.swing.JMenuItem();
        jMenuItem154 = new javax.swing.JMenuItem();
        jMenuItem155 = new javax.swing.JMenuItem();
        jMenu80 = new javax.swing.JMenu();
        jMenuItem156 = new javax.swing.JMenuItem();
        jMenuItem157 = new javax.swing.JMenuItem();
        jMenuItem158 = new javax.swing.JMenuItem();
        jMenuItem159 = new javax.swing.JMenuItem();
        jMenu81 = new javax.swing.JMenu();
        jMenuItem160 = new javax.swing.JMenuItem();
        jMenuItem161 = new javax.swing.JMenuItem();
        jMenuItem162 = new javax.swing.JMenuItem();
        masterGeneralLedgMenu2 = new javax.swing.JMenu();
        jMenu82 = new javax.swing.JMenu();
        cutMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem163 = new javax.swing.JMenuItem();
        jMenuItem164 = new javax.swing.JMenuItem();
        jMenu83 = new javax.swing.JMenu();
        jMenuItem165 = new javax.swing.JMenuItem();
        jMenuItem166 = new javax.swing.JMenuItem();
        jMenuItem167 = new javax.swing.JMenuItem();
        periodendMenu = new javax.swing.JMenu();
        masterCustomerMenu3 = new javax.swing.JMenu();
        jMenu84 = new javax.swing.JMenu();
        cutMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem168 = new javax.swing.JMenuItem();
        jMenuItem169 = new javax.swing.JMenuItem();
        jMenuItem170 = new javax.swing.JMenuItem();
        jMenuItem171 = new javax.swing.JMenuItem();
        jMenuItem172 = new javax.swing.JMenuItem();
        jMenu85 = new javax.swing.JMenu();
        jMenu86 = new javax.swing.JMenu();
        jMenu87 = new javax.swing.JMenu();
        jMenu88 = new javax.swing.JMenu();
        jMenu89 = new javax.swing.JMenu();
        masterProductsMenu3 = new javax.swing.JMenu();
        jMenu90 = new javax.swing.JMenu();
        copyMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem173 = new javax.swing.JMenuItem();
        jMenuItem174 = new javax.swing.JMenuItem();
        jMenuItem175 = new javax.swing.JMenuItem();
        jMenuItem176 = new javax.swing.JMenuItem();
        jMenuItem177 = new javax.swing.JMenuItem();
        jMenu91 = new javax.swing.JMenu();
        jMenu92 = new javax.swing.JMenu();
        jMenu93 = new javax.swing.JMenu();
        masterSuppliersMenu3 = new javax.swing.JMenu();
        jMenu94 = new javax.swing.JMenu();
        cutMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem178 = new javax.swing.JMenuItem();
        jMenuItem179 = new javax.swing.JMenuItem();
        jMenuItem180 = new javax.swing.JMenuItem();
        jMenuItem181 = new javax.swing.JMenuItem();
        jMenu95 = new javax.swing.JMenu();
        jMenuItem182 = new javax.swing.JMenuItem();
        jMenuItem183 = new javax.swing.JMenuItem();
        jMenu96 = new javax.swing.JMenu();
        jMenuItem184 = new javax.swing.JMenuItem();
        jMenuItem185 = new javax.swing.JMenuItem();
        jMenuItem186 = new javax.swing.JMenuItem();
        jMenu97 = new javax.swing.JMenu();
        jMenuItem187 = new javax.swing.JMenuItem();
        jMenuItem188 = new javax.swing.JMenuItem();
        jMenuItem189 = new javax.swing.JMenuItem();
        jMenu98 = new javax.swing.JMenu();
        jMenuItem190 = new javax.swing.JMenuItem();
        jMenuItem191 = new javax.swing.JMenuItem();
        jMenuItem192 = new javax.swing.JMenuItem();
        masterPayrollMenu3 = new javax.swing.JMenu();
        jMenu99 = new javax.swing.JMenu();
        cutMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem193 = new javax.swing.JMenuItem();
        jMenuItem194 = new javax.swing.JMenuItem();
        jMenuItem195 = new javax.swing.JMenuItem();
        jMenuItem196 = new javax.swing.JMenuItem();
        jMenuItem197 = new javax.swing.JMenuItem();
        jMenu100 = new javax.swing.JMenu();
        cutMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem198 = new javax.swing.JMenuItem();
        jMenuItem199 = new javax.swing.JMenuItem();
        jMenu101 = new javax.swing.JMenu();
        jMenuItem200 = new javax.swing.JMenuItem();
        jMenuItem201 = new javax.swing.JMenuItem();
        jMenuItem202 = new javax.swing.JMenuItem();
        jMenu102 = new javax.swing.JMenu();
        jMenuItem203 = new javax.swing.JMenuItem();
        jMenuItem204 = new javax.swing.JMenuItem();
        jMenuItem205 = new javax.swing.JMenuItem();
        jMenu103 = new javax.swing.JMenu();
        jMenuItem206 = new javax.swing.JMenuItem();
        jMenuItem207 = new javax.swing.JMenuItem();
        jMenuItem208 = new javax.swing.JMenuItem();
        jMenu104 = new javax.swing.JMenu();
        jMenuItem209 = new javax.swing.JMenuItem();
        jMenuItem210 = new javax.swing.JMenuItem();
        jMenuItem211 = new javax.swing.JMenuItem();
        jMenuItem212 = new javax.swing.JMenuItem();
        jMenu105 = new javax.swing.JMenu();
        jMenuItem213 = new javax.swing.JMenuItem();
        jMenuItem214 = new javax.swing.JMenuItem();
        jMenuItem215 = new javax.swing.JMenuItem();
        masterGeneralLedgMenu3 = new javax.swing.JMenu();
        jMenu106 = new javax.swing.JMenu();
        cutMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem216 = new javax.swing.JMenuItem();
        jMenuItem217 = new javax.swing.JMenuItem();
        jMenu107 = new javax.swing.JMenu();
        jMenuItem218 = new javax.swing.JMenuItem();
        jMenuItem219 = new javax.swing.JMenuItem();
        jMenuItem220 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        mnuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 400));

        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(42, 32));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 34));

        jPanel1.setPreferredSize(new java.awt.Dimension(96, 34));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        btnProduct.setFocusable(false);
        btnProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct.setMaximumSize(new java.awt.Dimension(32, 32));
        btnProduct.setMinimumSize(new java.awt.Dimension(32, 32));
        btnProduct.setPreferredSize(new java.awt.Dimension(34, 34));
        btnProduct.setRequestFocusEnabled(false);
        btnProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnProduct);

        btnCustomer.setFocusable(false);
        btnCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCustomer.setMaximumSize(new java.awt.Dimension(32, 32));
        btnCustomer.setMinimumSize(new java.awt.Dimension(32, 32));
        btnCustomer.setPreferredSize(new java.awt.Dimension(34, 34));
        btnCustomer.setRequestFocusEnabled(false);
        btnCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnCustomer);

        btnSupplier.setFocusable(false);
        btnSupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSupplier.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSupplier.setMinimumSize(new java.awt.Dimension(32, 32));
        btnSupplier.setPreferredSize(new java.awt.Dimension(34, 34));
        btnSupplier.setRequestFocusEnabled(false);
        btnSupplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnSupplier);

        jToolBar1.add(jPanel1);
        jToolBar1.add(jSeparator6);

        jPanel3.setPreferredSize(new java.awt.Dimension(96, 34));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        btnFindProducts.setFocusable(false);
        btnFindProducts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindProducts.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindProducts.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindProducts.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindProducts.setRequestFocusEnabled(false);
        btnFindProducts.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnFindProducts);

        btnFindCustomers.setFocusable(false);
        btnFindCustomers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindCustomers.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindCustomers.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindCustomers.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindCustomers.setRequestFocusEnabled(false);
        btnFindCustomers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnFindCustomers);

        btnFindSupplier.setFocusable(false);
        btnFindSupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindSupplier.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindSupplier.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindSupplier.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindSupplier.setRequestFocusEnabled(false);
        btnFindSupplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnFindSupplier);

        jToolBar1.add(jPanel3);
        jToolBar1.add(jSeparator7);

        jPanel2.setEnabled(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(128, 32));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnSalesOrder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalesOrder.setFocusable(false);
        btnSalesOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalesOrder.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSalesOrder.setMinimumSize(new java.awt.Dimension(32, 32));
        btnSalesOrder.setPreferredSize(new java.awt.Dimension(34, 34));
        btnSalesOrder.setRequestFocusEnabled(false);
        btnSalesOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(btnSalesOrder);

        btnSalesInvoice.setFocusable(false);
        btnSalesInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalesInvoice.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSalesInvoice.setMinimumSize(new java.awt.Dimension(32, 32));
        btnSalesInvoice.setPreferredSize(new java.awt.Dimension(34, 34));
        btnSalesInvoice.setRequestFocusEnabled(false);
        btnSalesInvoice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(btnSalesInvoice);

        btnSalesPayment.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalesPayment.setFocusable(false);
        btnSalesPayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalesPayment.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSalesPayment.setMinimumSize(new java.awt.Dimension(32, 32));
        btnSalesPayment.setPreferredSize(new java.awt.Dimension(34, 34));
        btnSalesPayment.setRequestFocusEnabled(false);
        btnSalesPayment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(btnSalesPayment);

        btnSalesReturn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalesReturn.setFocusable(false);
        btnSalesReturn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalesReturn.setMaximumSize(new java.awt.Dimension(32, 32));
        btnSalesReturn.setMinimumSize(new java.awt.Dimension(32, 32));
        btnSalesReturn.setPreferredSize(new java.awt.Dimension(34, 34));
        btnSalesReturn.setRequestFocusEnabled(false);
        btnSalesReturn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(btnSalesReturn);

        jToolBar1.add(jPanel2);
        jToolBar1.add(jSeparator8);

        jPanel4.setEnabled(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(128, 32));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        btnPurchaseOrder.setFocusable(false);
        btnPurchaseOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPurchaseOrder.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPurchaseOrder.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPurchaseOrder.setPreferredSize(new java.awt.Dimension(34, 34));
        btnPurchaseOrder.setRequestFocusEnabled(false);
        btnPurchaseOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(btnPurchaseOrder);

        btnPurchaseInvoice.setFocusable(false);
        btnPurchaseInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPurchaseInvoice.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPurchaseInvoice.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPurchaseInvoice.setPreferredSize(new java.awt.Dimension(34, 34));
        btnPurchaseInvoice.setRequestFocusEnabled(false);
        btnPurchaseInvoice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(btnPurchaseInvoice);

        btnPurchasePayment.setFocusable(false);
        btnPurchasePayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPurchasePayment.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPurchasePayment.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPurchasePayment.setPreferredSize(new java.awt.Dimension(34, 34));
        btnPurchasePayment.setRequestFocusEnabled(false);
        btnPurchasePayment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(btnPurchasePayment);

        btnPurchaseReturn.setFocusable(false);
        btnPurchaseReturn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPurchaseReturn.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPurchaseReturn.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPurchaseReturn.setPreferredSize(new java.awt.Dimension(34, 34));
        btnPurchaseReturn.setRequestFocusEnabled(false);
        btnPurchaseReturn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(btnPurchaseReturn);

        jToolBar1.add(jPanel4);
        jToolBar1.add(jSeparator9);

        jPanel5.setEnabled(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(128, 32));
        jPanel5.setRequestFocusEnabled(false);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        btnFindSalesOrder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFindSalesOrder.setFocusable(false);
        btnFindSalesOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindSalesOrder.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindSalesOrder.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindSalesOrder.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindSalesOrder.setRequestFocusEnabled(false);
        btnFindSalesOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(btnFindSalesOrder);

        btnFindSalesInvoice.setFocusable(false);
        btnFindSalesInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindSalesInvoice.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindSalesInvoice.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindSalesInvoice.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindSalesInvoice.setRequestFocusEnabled(false);
        btnFindSalesInvoice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(btnFindSalesInvoice);

        btnFindSalesPayment.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFindSalesPayment.setFocusable(false);
        btnFindSalesPayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindSalesPayment.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindSalesPayment.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindSalesPayment.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindSalesPayment.setRequestFocusEnabled(false);
        btnFindSalesPayment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(btnFindSalesPayment);

        btnFindSalesReturn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFindSalesReturn.setFocusable(false);
        btnFindSalesReturn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindSalesReturn.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindSalesReturn.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindSalesReturn.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindSalesReturn.setRequestFocusEnabled(false);
        btnFindSalesReturn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(btnFindSalesReturn);

        jToolBar1.add(jPanel5);
        jToolBar1.add(jSeparator10);

        jPanel6.setEnabled(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(128, 32));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        btnFindPurchaseOrder.setFocusable(false);
        btnFindPurchaseOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindPurchaseOrder.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindPurchaseOrder.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindPurchaseOrder.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindPurchaseOrder.setRequestFocusEnabled(false);
        btnFindPurchaseOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(btnFindPurchaseOrder);

        btnFindPurchaseInvoice.setFocusable(false);
        btnFindPurchaseInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindPurchaseInvoice.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindPurchaseInvoice.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindPurchaseInvoice.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindPurchaseInvoice.setRequestFocusEnabled(false);
        btnFindPurchaseInvoice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(btnFindPurchaseInvoice);

        btnFindPurchasePayment.setFocusable(false);
        btnFindPurchasePayment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindPurchasePayment.setMaximumSize(new java.awt.Dimension(32, 32));
        btnFindPurchasePayment.setMinimumSize(new java.awt.Dimension(32, 32));
        btnFindPurchasePayment.setPreferredSize(new java.awt.Dimension(34, 34));
        btnFindPurchasePayment.setRequestFocusEnabled(false);
        btnFindPurchasePayment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(btnFindPurchasePayment);

        btnReport.setFocusable(false);
        btnReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReport.setMaximumSize(new java.awt.Dimension(32, 32));
        btnReport.setMinimumSize(new java.awt.Dimension(32, 32));
        btnReport.setPreferredSize(new java.awt.Dimension(34, 34));
        btnReport.setRequestFocusEnabled(false);
        btnReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(btnReport);

        jToolBar1.add(jPanel6);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        desktoppane = desktopPane;
        desktopPane.setBackground(new java.awt.Color(153, 153, 153));
        desktopPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        desktopPane.setMaximumSize(new java.awt.Dimension(2000, 2000));
        desktopPane.setMinimumSize(new java.awt.Dimension(1024, 900));

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnPrintLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPrintLabel.setText("Print Packing Labels");
        btnPrintLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintLabelActionPerformed(evt);
            }
        });

        btnPrintProductShelfLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPrintProductShelfLabel.setText("Print Shelf Labels");
        btnPrintProductShelfLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintProductShelfLabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrintProductShelfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(404, Short.MAX_VALUE))
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnPrintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnPrintProductShelfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(447, Short.MAX_VALUE))
        );
        desktopPane.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnPrintLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnPrintProductShelfLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        scrollPane.setViewportView(desktopPane);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        openMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        openMenuItem.setMnemonic('o');
        openMenuItem.setText("New Company");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveMenuItem.setMnemonic('o');
        saveMenuItem.setText("Open Company");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Close Company");
        saveAsMenuItem.setActionCommand("Close company");
        fileMenu.add(saveAsMenuItem);

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem1.setText("Setup and Delete Companies");
        fileMenu.add(jMenuItem1);
        fileMenu.add(jSeparator1);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem2.setText("Keystroke File Import");
        fileMenu.add(jMenuItem2);
        fileMenu.add(jSeparator2);

        jMenuItem3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem3.setText("Sign off User");
        fileMenu.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem4.setText("Change User");
        fileMenu.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem5.setText("Maintain User");
        fileMenu.add(jMenuItem5);
        fileMenu.add(jSeparator3);

        mnuImportDataFromFile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnuImportDataFromFile.setText("Restore Company Fron Archive");
        fileMenu.add(mnuImportDataFromFile);

        mnuImportData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnuImportData.setText("Import Data");
        mnuImportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuImportDataActionPerformed(evt);
            }
        });
        fileMenu.add(mnuImportData);
        fileMenu.add(jSeparator4);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        masterfilesMenu.setMnemonic('e');
        masterfilesMenu.setText("Masterfiles");
        masterfilesMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        masterCustomerMenu.setText("Customers");
        masterCustomerMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuCustomers.setText("Customers");
        mnuCustomers.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterCustomerMenu.add(mnuCustomers);

        masterfilesMenu.add(masterCustomerMenu);

        masterProductsMenu.setText("Catalog Manger");
        masterProductsMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuCatalog.setText("Catalog");
        mnuCatalog.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnuCatalog);

        mnuCategories.setText("Categories");
        mnuCategories.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnuCategories);

        mnuProducts.setText("Products");
        mnuProducts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnuProducts);

        mnuProductFeature.setText("Product Feature Setup");
        mnuProductFeature.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnuProductFeature);

        mnProductPricingGroup.setText("Product Pricing");
        mnProductPricingGroup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnProductPricingGroup);

        mnuSupplierProductGroup.setText("Supplier Product");
        mnuSupplierProductGroup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterProductsMenu.add(mnuSupplierProductGroup);

        jMenu21.setText("Old Product Menu");
        jMenu21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        productEnquireMenuItem.setMnemonic('y');
        productEnquireMenuItem.setText("Product List");
        jMenu21.add(productEnquireMenuItem);

        productMaintainMenuItem.setText("Products - Maintain");
        productMaintainMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productMaintainMenuItemActionPerformed(evt);
            }
        });
        jMenu21.add(productMaintainMenuItem);

        mnuProductCategoryMaintain.setText("Old Product");
        mnuProductCategoryMaintain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProductCategoryMaintainActionPerformed(evt);
            }
        });
        jMenu21.add(mnuProductCategoryMaintain);

        jMenuItem14.setText("Services - Maintain");
        jMenu21.add(jMenuItem14);

        jMenuItem15.setText("Services - Special Maintain");
        jMenu21.add(jMenuItem15);

        jMenuItem16.setText("Suppementary Barcodes");
        jMenu21.add(jMenuItem16);

        productEnquireMenuItem1.setMnemonic('y');
        productEnquireMenuItem1.setText("Old Product");
        jMenu21.add(productEnquireMenuItem1);

        masterProductsMenu.add(jMenu21);

        masterfilesMenu.add(masterProductsMenu);

        masterSuppliersMenu.setText("Suppliers");
        masterSuppliersMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuSupplier.setText("Suppliers");
        mnuSupplier.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterSuppliersMenu.add(mnuSupplier);

        masterfilesMenu.add(masterSuppliersMenu);

        mnuProductStoreSetup.setText("Product Store Setup");
        masterfilesMenu.add(mnuProductStoreSetup);

        masterGeneralLedgMenu.setText("Pos Configuration");
        masterGeneralLedgMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuPosPanelDesign.setText("Pos Panel Design");
        mnuPosPanelDesign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPosPanelDesignActionPerformed(evt);
            }
        });

        jMenuItem6.setText("Screen Design");
        mnuPosPanelDesign.add(jMenuItem6);

        masterGeneralLedgMenu.add(mnuPosPanelDesign);

        mnuPosTerminalConfig.setText("Pos Terminal Configuration");
        mnuPosTerminalConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPosTerminalConfigActionPerformed(evt);
            }
        });
        masterGeneralLedgMenu.add(mnuPosTerminalConfig);

        jMenu35.setText("General Ledger Sets");
        jMenu35.setEnabled(false);
        masterGeneralLedgMenu.add(jMenu35);

        mnuPosCOnfiguration.setText("Configuration");
        masterGeneralLedgMenu.add(mnuPosCOnfiguration);

        masterfilesMenu.add(masterGeneralLedgMenu);

        masterPayrollMenu.setText("Payroll");
        masterPayrollMenu.setEnabled(false);
        masterPayrollMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenu27.setText("Emplyoee Details");

        employeeEnquireMenuItem.setMnemonic('t');
        employeeEnquireMenuItem.setText("Enquire");
        jMenu27.add(employeeEnquireMenuItem);

        employeeMaintainMenuItem.setText("Maintain");
        jMenu27.add(employeeMaintainMenuItem);

        jMenuItem35.setText("Change Loc/Employee Code");
        jMenu27.add(jMenuItem35);

        jMenuItem36.setText("Delete");
        jMenu27.add(jMenuItem36);

        jMenuItem37.setText("Change Emp. Leave Table");
        jMenu27.add(jMenuItem37);

        jMenuItem55.setText("Consolidate Leave");
        jMenu27.add(jMenuItem55);

        masterPayrollMenu.add(jMenu27);

        jMenu28.setText("Employee Bank Accounts");

        cutMenuItem3.setMnemonic('t');
        cutMenuItem3.setText("Enquire");
        jMenu28.add(cutMenuItem3);

        jMenuItem38.setText("Maintain");
        jMenu28.add(jMenuItem38);

        jMenuItem39.setText("Delete");
        jMenu28.add(jMenuItem39);

        masterPayrollMenu.add(jMenu28);

        jMenu29.setText("Employee Standard Pay");

        jMenuItem40.setText("Enquire");
        jMenu29.add(jMenuItem40);

        jMenuItem41.setText("Maintain");
        jMenu29.add(jMenuItem41);

        jMenuItem42.setText("Delete");
        jMenu29.add(jMenuItem42);

        masterPayrollMenu.add(jMenu29);

        jMenu30.setText("Delivery Address");

        jMenuItem43.setText("Enquire");
        jMenu30.add(jMenuItem43);

        jMenuItem44.setText("Maintain");
        jMenu30.add(jMenuItem44);

        jMenuItem45.setText("Delete");
        jMenu30.add(jMenuItem45);

        masterPayrollMenu.add(jMenu30);

        jMenu31.setText("Other Account Details");

        jMenuItem46.setText("Delete");
        jMenu31.add(jMenuItem46);

        jMenuItem47.setText("Maintain");
        jMenu31.add(jMenuItem47);

        jMenuItem48.setText("Enquire");
        jMenu31.add(jMenuItem48);

        masterPayrollMenu.add(jMenu31);

        jMenu32.setText("New Employee");

        jMenuItem49.setText("Details");
        jMenu32.add(jMenuItem49);

        jMenuItem50.setText("Bank Accounts");
        jMenu32.add(jMenuItem50);

        jMenuItem51.setText("Other Accounts");
        jMenu32.add(jMenuItem51);

        jMenuItem56.setText("Standard Pays");
        jMenu32.add(jMenuItem56);

        masterPayrollMenu.add(jMenu32);

        jMenu33.setText("Payment Schedule");

        jMenuItem54.setText("Enquire");
        jMenu33.add(jMenuItem54);

        jMenuItem53.setText("Maintain");
        jMenu33.add(jMenuItem53);

        jMenuItem52.setText("Delete");
        jMenu33.add(jMenuItem52);

        masterPayrollMenu.add(jMenu33);

        masterfilesMenu.add(masterPayrollMenu);

        masterGeneralLedgMenu4.setText("General Ledger");
        masterGeneralLedgMenu4.setEnabled(false);
        masterGeneralLedgMenu4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jMenu48.setText("Accounts");

        cutMenuItem5.setMnemonic('t');
        cutMenuItem5.setText("Enquire");
        cutMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItem5ActionPerformed(evt);
            }
        });
        jMenu48.add(cutMenuItem5);

        jMenuItem62.setText("Maintain");
        jMenu48.add(jMenuItem62);

        jMenuItem63.setText("Delete");
        jMenu48.add(jMenuItem63);

        masterGeneralLedgMenu4.add(jMenu48);

        jMenu49.setText("General Ledger Sets");

        jMenuItem64.setText("Enquire");
        jMenu49.add(jMenuItem64);

        jMenuItem65.setText("Maintain");
        jMenu49.add(jMenuItem65);

        jMenuItem66.setText("Delete");
        jMenu49.add(jMenuItem66);

        masterGeneralLedgMenu4.add(jMenu49);

        masterfilesMenu.add(masterGeneralLedgMenu4);

        mnuAccountManager.setText("Accounting Manager");
        masterfilesMenu.add(mnuAccountManager);

        menuBar.add(masterfilesMenu);

        transactionsMenu.setMnemonic('h');
        transactionsMenu.setText("Transactions");
        transactionsMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        masterCustomerMenu1.setText("Customers");

        jMenu46.setText("Requests");
        jMenu46.setEnabled(false);

        orderViewMenuItem2.setMnemonic('t');
        orderViewMenuItem2.setText("Request Enquire");
        jMenu46.add(orderViewMenuItem2);

        salesOrderCreateMenuItem2.setText("Request Create");
        jMenu46.add(salesOrderCreateMenuItem2);

        mnuSalesOrderList2.setText("Request List");
        jMenu46.add(mnuSalesOrderList2);

        masterCustomerMenu1.add(jMenu46);

        jMenu39.setText("Quotes");
        jMenu39.setEnabled(false);

        salesOrderCreateMenuItem1.setText("Quote Create");
        jMenu39.add(salesOrderCreateMenuItem1);

        orderViewMenuItem1.setMnemonic('t');
        orderViewMenuItem1.setText("Quote Enquire");
        jMenu39.add(orderViewMenuItem1);

        mnuSalesOrderList1.setText("Quote List");
        jMenu39.add(mnuSalesOrderList1);

        masterCustomerMenu1.add(jMenu39);

        mnuOrders.setText("Orders");

        mnuSalesOrderList.setText("Order List");
        mnuOrders.add(mnuSalesOrderList);

        mnuSalesOrderCreate.setText("Order Create");
        mnuOrders.add(mnuSalesOrderCreate);

        masterCustomerMenu1.add(mnuOrders);

        mnuReturnsMain.setText("Retruns");

        mnuSalesOrderReturnList.setText("Order Return List");
        mnuReturnsMain.add(mnuSalesOrderReturnList);

        mnuReturnSalesOrder.setText("Order Return");
        mnuReturnsMain.add(mnuReturnSalesOrder);

        masterCustomerMenu1.add(mnuReturnsMain);

        mnuShoppingList.setText("Shopping List");
        masterCustomerMenu1.add(mnuShoppingList);

        mnuReturnsMain1.setText("Requirements");
        mnuReturnsMain1.setEnabled(false);
        masterCustomerMenu1.add(mnuReturnsMain1);

        jMenu37.setText("Invoice");

        mnuFindInvoiceListItem.setText("Find Invoice List");
        jMenu37.add(mnuFindInvoiceListItem);

        invoiceEnquireMenuItem.setMnemonic('t');
        invoiceEnquireMenuItem.setText("Invoice Enquire");
        jMenu37.add(invoiceEnquireMenuItem);

        invoiceCreateMenuItem.setText("Invoice Create");
        jMenu37.add(invoiceCreateMenuItem);

        masterCustomerMenu1.add(jMenu37);

        mnuCustomerPayment.setText("Payments");

        mnuPaymentList.setText("Payments List");
        mnuCustomerPayment.add(mnuPaymentList);

        mnuCreatePayment.setMnemonic('t');
        mnuCreatePayment.setText("Enter Payments");
        mnuCustomerPayment.add(mnuCreatePayment);

        masterCustomerMenu1.add(mnuCustomerPayment);

        jMenu40.setText("Standard Charges");
        jMenu40.setEnabled(false);
        masterCustomerMenu1.add(jMenu40);

        transactionsMenu.add(masterCustomerMenu1);

        masterProductsMenu1.setText("Products");

        jMenu42.setText("Products/Services");

        copyMenuItem1.setMnemonic('y');
        copyMenuItem1.setText("Products/Services - Enquire");
        jMenu42.add(copyMenuItem1);

        jMenuItem67.setText("Products - Maintain");
        jMenu42.add(jMenuItem67);

        jMenuItem68.setText("Products - Special Maintain");
        jMenu42.add(jMenuItem68);

        jMenuItem69.setText("Services - Maintain");
        jMenu42.add(jMenuItem69);

        jMenuItem70.setText("Services - Special Maintain");
        jMenu42.add(jMenuItem70);

        jMenuItem71.setText("Suppementary Barcodes");
        jMenu42.add(jMenuItem71);

        masterProductsMenu1.add(jMenu42);

        jMenu43.setText("Customer Pricing");
        masterProductsMenu1.add(jMenu43);

        jMenu44.setText("Supplier Pricing");
        masterProductsMenu1.add(jMenu44);

        jMenu45.setText("Product Groups");
        masterProductsMenu1.add(jMenu45);

        transactionsMenu.add(masterProductsMenu1);

        masterSuppliersMenu1.setText("Suppliers");

        jMenu108.setText("Order");

        findPurchaseOrderMnuItem.setText("Find Purchase Order");
        jMenu108.add(findPurchaseOrderMnuItem);

        mnuPurchaseOrderList.setText("Order List");
        jMenu108.add(mnuPurchaseOrderList);

        mnuPurchaseOrderEnquire.setMnemonic('t');
        mnuPurchaseOrderEnquire.setText("Order Enquire");
        mnuPurchaseOrderEnquire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPurchaseOrderEnquireActionPerformed(evt);
            }
        });
        jMenu108.add(mnuPurchaseOrderEnquire);

        mnuPurchaseOrderCreate.setText("Order Create");
        jMenu108.add(mnuPurchaseOrderCreate);

        mnuPurchaseOrderReturnList.setText("Order Return List");
        jMenu108.add(mnuPurchaseOrderReturnList);

        mnuPurchaseOrderReturnCreate.setText("Order Return");
        jMenu108.add(mnuPurchaseOrderReturnCreate);

        masterSuppliersMenu1.add(jMenu108);

        jMenu109.setText("Invoice");

        mnuPurchaseInvoiceList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        mnuPurchaseInvoiceList.setText("Invoice List");
        jMenu109.add(mnuPurchaseInvoiceList);

        mnuEditPurchaseInvoice.setText("Invoice Create");
        jMenu109.add(mnuEditPurchaseInvoice);

        mnuGenerateInvoiceFromOrderList.setText("Generate  Purchase invoice");
        jMenu109.add(mnuGenerateInvoiceFromOrderList);

        orderViewMenuItem3.setMnemonic('t');
        orderViewMenuItem3.setText("Invoice Enquire");
        orderViewMenuItem3.setEnabled(false);
        orderViewMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderViewMenuItem3ActionPerformed(evt);
            }
        });
        jMenu109.add(orderViewMenuItem3);

        masterSuppliersMenu1.add(jMenu109);

        jMenu47.setText("Payments");

        mnuPaymentList1.setText("Payments List");
        jMenu47.add(mnuPaymentList1);

        mnuCreatePayment1.setMnemonic('t');
        mnuCreatePayment1.setText("Enter Payments");
        jMenu47.add(mnuCreatePayment1);

        masterSuppliersMenu1.add(jMenu47);

        transactionsMenu.add(masterSuppliersMenu1);

        masterPayrollMenu1.setText("Payroll");
        masterPayrollMenu1.setEnabled(false);

        jMenu51.setText("Emplyoee Details");

        cutMenuItem7.setMnemonic('t');
        cutMenuItem7.setText("Enquire");
        jMenu51.add(cutMenuItem7);

        jMenuItem87.setText("Maintain");
        jMenu51.add(jMenuItem87);

        jMenuItem88.setText("Change Loc/Employee Code");
        jMenu51.add(jMenuItem88);

        jMenuItem89.setText("Delete");
        jMenu51.add(jMenuItem89);

        jMenuItem90.setText("Change Emp. Leave Table");
        jMenu51.add(jMenuItem90);

        jMenuItem91.setText("Consolidate Leave");
        jMenu51.add(jMenuItem91);

        masterPayrollMenu1.add(jMenu51);

        jMenu52.setText("Employee Bank Accounts");

        cutMenuItem8.setMnemonic('t');
        cutMenuItem8.setText("Enquire");
        jMenu52.add(cutMenuItem8);

        jMenuItem92.setText("Maintain");
        jMenu52.add(jMenuItem92);

        jMenuItem93.setText("Delete");
        jMenu52.add(jMenuItem93);

        masterPayrollMenu1.add(jMenu52);

        jMenu53.setText("Employee Standard Pay");

        jMenuItem94.setText("Enquire");
        jMenu53.add(jMenuItem94);

        jMenuItem95.setText("Maintain");
        jMenu53.add(jMenuItem95);

        jMenuItem96.setText("Delete");
        jMenu53.add(jMenuItem96);

        masterPayrollMenu1.add(jMenu53);

        jMenu54.setText("Delivery Address");

        jMenuItem97.setText("Enquire");
        jMenu54.add(jMenuItem97);

        jMenuItem98.setText("Maintain");
        jMenu54.add(jMenuItem98);

        jMenuItem99.setText("Delete");
        jMenu54.add(jMenuItem99);

        masterPayrollMenu1.add(jMenu54);

        jMenu55.setText("Other Account Details");

        jMenuItem100.setText("Delete");
        jMenu55.add(jMenuItem100);

        jMenuItem101.setText("Maintain");
        jMenu55.add(jMenuItem101);

        jMenuItem102.setText("Enquire");
        jMenu55.add(jMenuItem102);

        masterPayrollMenu1.add(jMenu55);

        jMenu56.setText("New Employee");

        jMenuItem103.setText("Details");
        jMenu56.add(jMenuItem103);

        jMenuItem104.setText("Bank Accounts");
        jMenu56.add(jMenuItem104);

        jMenuItem105.setText("Other Accounts");
        jMenu56.add(jMenuItem105);

        jMenuItem106.setText("Standard Pays");
        jMenu56.add(jMenuItem106);

        masterPayrollMenu1.add(jMenu56);

        jMenu57.setText("Payment Schedule");

        jMenuItem107.setText("Enquire");
        jMenu57.add(jMenuItem107);

        jMenuItem108.setText("Maintain");
        jMenu57.add(jMenuItem108);

        jMenuItem109.setText("Delete");
        jMenu57.add(jMenuItem109);

        masterPayrollMenu1.add(jMenu57);

        transactionsMenu.add(masterPayrollMenu1);

        masterGeneralLedgMenu1.setText("General Ledger");
        masterGeneralLedgMenu1.setEnabled(false);

        jMenu58.setText("Accounts");

        cutMenuItem9.setMnemonic('t');
        cutMenuItem9.setText("Enquire");
        jMenu58.add(cutMenuItem9);

        jMenuItem110.setText("Maintain");
        jMenu58.add(jMenuItem110);

        jMenuItem111.setText("Delete");
        jMenu58.add(jMenuItem111);

        masterGeneralLedgMenu1.add(jMenu58);

        jMenu59.setText("General Ledger Sets");

        jMenuItem112.setText("Enquire");
        jMenu59.add(jMenuItem112);

        jMenuItem113.setText("Maintain");
        jMenu59.add(jMenuItem113);

        jMenuItem114.setText("Delete");
        jMenu59.add(jMenuItem114);

        masterGeneralLedgMenu1.add(jMenu59);

        transactionsMenu.add(masterGeneralLedgMenu1);

        mnuMasterInventory.setText("Inventory");

        mnuInventory.setText("Inventory");
        mnuMasterInventory.add(mnuInventory);

        mnuInventoryItemList.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        mnuInventoryItemList.setText("Inventory Item List");
        mnuMasterInventory.add(mnuInventoryItemList);

        mnuInventoryDetailList.setText("Inventory Detail List");
        mnuMasterInventory.add(mnuInventoryDetailList);

        transactionsMenu.add(mnuMasterInventory);

        menuBar.add(transactionsMenu);

        reportsMenu.setText("Reports");
        reportsMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuItemReport.setText("jMenuItem7");
        reportsMenu.add(mnuItemReport);

        mnuItemPosReport.setText("jMenuItem7");
        reportsMenu.add(mnuItemPosReport);

        masterCustomerMenu2.setText("Customers");

        jMenu60.setText("Customers");

        cutMenuItem10.setMnemonic('t');
        cutMenuItem10.setText("Enquire");
        jMenu60.add(cutMenuItem10);

        jMenuItem115.setText("Maintain");
        jMenu60.add(jMenuItem115);

        jMenuItem116.setText("Special Maintaince");
        jMenu60.add(jMenuItem116);

        jMenuItem117.setText("Delete");
        jMenu60.add(jMenuItem117);

        jMenuItem118.setText("Contacts");
        jMenu60.add(jMenuItem118);

        jMenuItem119.setText("Create From Quotation");
        jMenu60.add(jMenuItem119);

        masterCustomerMenu2.add(jMenu60);

        jMenu61.setText("Special Pricing");
        masterCustomerMenu2.add(jMenu61);

        jMenu62.setText("Delivery Address");
        masterCustomerMenu2.add(jMenu62);

        jMenu63.setText("Sales Reps");
        masterCustomerMenu2.add(jMenu63);

        jMenu64.setText("Standard Charges");
        masterCustomerMenu2.add(jMenu64);

        jMenu65.setText("Exchange Rates");
        masterCustomerMenu2.add(jMenu65);

        reportsMenu.add(masterCustomerMenu2);

        masterProductsMenu2.setText("Products");

        jMenu66.setText("Products/Services");

        copyMenuItem2.setMnemonic('y');
        copyMenuItem2.setText("Products/Services - Enquire");
        jMenu66.add(copyMenuItem2);

        jMenuItem120.setText("Products - Maintain");
        jMenu66.add(jMenuItem120);

        jMenuItem121.setText("Products - Special Maintain");
        jMenu66.add(jMenuItem121);

        jMenuItem122.setText("Services - Maintain");
        jMenu66.add(jMenuItem122);

        jMenuItem123.setText("Services - Special Maintain");
        jMenu66.add(jMenuItem123);

        jMenuItem124.setText("Suppementary Barcodes");
        jMenu66.add(jMenuItem124);

        masterProductsMenu2.add(jMenu66);

        jMenu67.setText("Customer Pricing");
        masterProductsMenu2.add(jMenu67);

        jMenu68.setText("Supplier Pricing");
        masterProductsMenu2.add(jMenu68);

        jMenu69.setText("Product Groups");
        masterProductsMenu2.add(jMenu69);

        reportsMenu.add(masterProductsMenu2);

        masterSuppliersMenu2.setText("Suppliers");

        jMenu70.setText("Suppliers");

        reportSupplierPaymentAgeingReport.setMnemonic('t');
        reportSupplierPaymentAgeingReport.setText("Enquire");
        jMenu70.add(reportSupplierPaymentAgeingReport);

        mnuItemReportMain.setText("Maintain");
        jMenu70.add(mnuItemReportMain);

        mnuItemInventoryReceipt.setText("Special Maintaince");
        jMenu70.add(mnuItemInventoryReceipt);

        jMenuItem127.setText("Delete");
        jMenu70.add(jMenuItem127);

        jMenuItem128.setText("Contacts");
        jMenu70.add(jMenuItem128);

        masterSuppliersMenu2.add(jMenu70);

        jMenu71.setText("Pricing");

        jMenuItem129.setText("Maintain");
        jMenu71.add(jMenuItem129);

        jMenuItem130.setText("Delete");
        jMenu71.add(jMenuItem130);

        masterSuppliersMenu2.add(jMenu71);

        jMenu72.setText("Exchange Rates");

        jMenuItem131.setText("Enquire");
        jMenu72.add(jMenuItem131);

        jMenuItem132.setText("Maintain");
        jMenu72.add(jMenuItem132);

        jMenuItem133.setText("Delete");
        jMenu72.add(jMenuItem133);

        masterSuppliersMenu2.add(jMenu72);

        jMenu73.setText("Standard  Charges");

        jMenuItem134.setText("Enquire");
        jMenu73.add(jMenuItem134);

        jMenuItem135.setText("Maintain");
        jMenu73.add(jMenuItem135);

        jMenuItem136.setText("Delete");
        jMenu73.add(jMenuItem136);

        masterSuppliersMenu2.add(jMenu73);

        jMenu74.setText("Delivery Address");

        jMenuItem137.setText("Delete");
        jMenu74.add(jMenuItem137);

        jMenuItem138.setText("Maintain");
        jMenu74.add(jMenuItem138);

        jMenuItem139.setText("Enquire");
        jMenu74.add(jMenuItem139);

        masterSuppliersMenu2.add(jMenu74);

        reportsMenu.add(masterSuppliersMenu2);

        masterPayrollMenu2.setText("Payroll");

        jMenu75.setText("Emplyoee Details");

        cutMenuItem12.setMnemonic('t');
        cutMenuItem12.setText("Enquire");
        jMenu75.add(cutMenuItem12);

        jMenuItem140.setText("Maintain");
        jMenu75.add(jMenuItem140);

        jMenuItem141.setText("Change Loc/Employee Code");
        jMenu75.add(jMenuItem141);

        jMenuItem142.setText("Delete");
        jMenu75.add(jMenuItem142);

        jMenuItem143.setText("Change Emp. Leave Table");
        jMenu75.add(jMenuItem143);

        jMenuItem144.setText("Consolidate Leave");
        jMenu75.add(jMenuItem144);

        masterPayrollMenu2.add(jMenu75);

        jMenu76.setText("Employee Bank Accounts");

        cutMenuItem13.setMnemonic('t');
        cutMenuItem13.setText("Enquire");
        jMenu76.add(cutMenuItem13);

        jMenuItem145.setText("Maintain");
        jMenu76.add(jMenuItem145);

        jMenuItem146.setText("Delete");
        jMenu76.add(jMenuItem146);

        masterPayrollMenu2.add(jMenu76);

        jMenu77.setText("Employee Standard Pay");

        jMenuItem147.setText("Enquire");
        jMenu77.add(jMenuItem147);

        jMenuItem148.setText("Maintain");
        jMenu77.add(jMenuItem148);

        jMenuItem149.setText("Delete");
        jMenu77.add(jMenuItem149);

        masterPayrollMenu2.add(jMenu77);

        jMenu78.setText("Delivery Address");

        jMenuItem150.setText("Enquire");
        jMenu78.add(jMenuItem150);

        jMenuItem151.setText("Maintain");
        jMenu78.add(jMenuItem151);

        jMenuItem152.setText("Delete");
        jMenu78.add(jMenuItem152);

        masterPayrollMenu2.add(jMenu78);

        jMenu79.setText("Other Account Details");

        jMenuItem153.setText("Delete");
        jMenu79.add(jMenuItem153);

        jMenuItem154.setText("Maintain");
        jMenu79.add(jMenuItem154);

        jMenuItem155.setText("Enquire");
        jMenu79.add(jMenuItem155);

        masterPayrollMenu2.add(jMenu79);

        jMenu80.setText("New Employee");

        jMenuItem156.setText("Details");
        jMenu80.add(jMenuItem156);

        jMenuItem157.setText("Bank Accounts");
        jMenu80.add(jMenuItem157);

        jMenuItem158.setText("Other Accounts");
        jMenu80.add(jMenuItem158);

        jMenuItem159.setText("Standard Pays");
        jMenu80.add(jMenuItem159);

        masterPayrollMenu2.add(jMenu80);

        jMenu81.setText("Payment Schedule");

        jMenuItem160.setText("Enquire");
        jMenu81.add(jMenuItem160);

        jMenuItem161.setText("Maintain");
        jMenu81.add(jMenuItem161);

        jMenuItem162.setText("Delete");
        jMenu81.add(jMenuItem162);

        masterPayrollMenu2.add(jMenu81);

        reportsMenu.add(masterPayrollMenu2);

        masterGeneralLedgMenu2.setText("General Ledger");

        jMenu82.setText("Accounts");

        cutMenuItem14.setMnemonic('t');
        cutMenuItem14.setText("Enquire");
        jMenu82.add(cutMenuItem14);

        jMenuItem163.setText("Maintain");
        jMenu82.add(jMenuItem163);

        jMenuItem164.setText("Delete");
        jMenu82.add(jMenuItem164);

        masterGeneralLedgMenu2.add(jMenu82);

        jMenu83.setText("General Ledger Sets");

        jMenuItem165.setText("Enquire");
        jMenu83.add(jMenuItem165);

        jMenuItem166.setText("Maintain");
        jMenu83.add(jMenuItem166);

        jMenuItem167.setText("Delete");
        jMenu83.add(jMenuItem167);

        masterGeneralLedgMenu2.add(jMenu83);

        reportsMenu.add(masterGeneralLedgMenu2);

        menuBar.add(reportsMenu);

        periodendMenu.setText("Period Ends");
        periodendMenu.setEnabled(false);
        periodendMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        masterCustomerMenu3.setText("Customers");

        jMenu84.setText("Customers");

        cutMenuItem15.setMnemonic('t');
        cutMenuItem15.setText("Enquire");
        jMenu84.add(cutMenuItem15);

        jMenuItem168.setText("Maintain");
        jMenu84.add(jMenuItem168);

        jMenuItem169.setText("Special Maintaince");
        jMenu84.add(jMenuItem169);

        jMenuItem170.setText("Delete");
        jMenu84.add(jMenuItem170);

        jMenuItem171.setText("Contacts");
        jMenu84.add(jMenuItem171);

        jMenuItem172.setText("Create From Quotation");
        jMenu84.add(jMenuItem172);

        masterCustomerMenu3.add(jMenu84);

        jMenu85.setText("Special Pricing");
        masterCustomerMenu3.add(jMenu85);

        jMenu86.setText("Delivery Address");
        masterCustomerMenu3.add(jMenu86);

        jMenu87.setText("Sales Reps");
        masterCustomerMenu3.add(jMenu87);

        jMenu88.setText("Standard Charges");
        masterCustomerMenu3.add(jMenu88);

        jMenu89.setText("Exchange Rates");
        masterCustomerMenu3.add(jMenu89);

        periodendMenu.add(masterCustomerMenu3);

        masterProductsMenu3.setText("Products");

        jMenu90.setText("Products/Services");

        copyMenuItem3.setMnemonic('y');
        copyMenuItem3.setText("Products/Services - Enquire");
        jMenu90.add(copyMenuItem3);

        jMenuItem173.setText("Products - Maintain");
        jMenu90.add(jMenuItem173);

        jMenuItem174.setText("Products - Special Maintain");
        jMenu90.add(jMenuItem174);

        jMenuItem175.setText("Services - Maintain");
        jMenu90.add(jMenuItem175);

        jMenuItem176.setText("Services - Special Maintain");
        jMenu90.add(jMenuItem176);

        jMenuItem177.setText("Suppementary Barcodes");
        jMenu90.add(jMenuItem177);

        masterProductsMenu3.add(jMenu90);

        jMenu91.setText("Customer Pricing");
        masterProductsMenu3.add(jMenu91);

        jMenu92.setText("Supplier Pricing");
        masterProductsMenu3.add(jMenu92);

        jMenu93.setText("Product Groups");
        masterProductsMenu3.add(jMenu93);

        periodendMenu.add(masterProductsMenu3);

        masterSuppliersMenu3.setText("Suppliers");

        jMenu94.setText("Suppliers");

        cutMenuItem16.setMnemonic('t');
        cutMenuItem16.setText("Enquire");
        jMenu94.add(cutMenuItem16);

        jMenuItem178.setText("Maintain");
        jMenu94.add(jMenuItem178);

        jMenuItem179.setText("Special Maintaince");
        jMenu94.add(jMenuItem179);

        jMenuItem180.setText("Delete");
        jMenu94.add(jMenuItem180);

        jMenuItem181.setText("Contacts");
        jMenu94.add(jMenuItem181);

        masterSuppliersMenu3.add(jMenu94);

        jMenu95.setText("Pricing");

        jMenuItem182.setText("Maintain");
        jMenu95.add(jMenuItem182);

        jMenuItem183.setText("Delete");
        jMenu95.add(jMenuItem183);

        masterSuppliersMenu3.add(jMenu95);

        jMenu96.setText("Exchange Rates");

        jMenuItem184.setText("Enquire");
        jMenu96.add(jMenuItem184);

        jMenuItem185.setText("Maintain");
        jMenu96.add(jMenuItem185);

        jMenuItem186.setText("Delete");
        jMenu96.add(jMenuItem186);

        masterSuppliersMenu3.add(jMenu96);

        jMenu97.setText("Standard  Charges");

        jMenuItem187.setText("Enquire");
        jMenu97.add(jMenuItem187);

        jMenuItem188.setText("Maintain");
        jMenu97.add(jMenuItem188);

        jMenuItem189.setText("Delete");
        jMenu97.add(jMenuItem189);

        masterSuppliersMenu3.add(jMenu97);

        jMenu98.setText("Delivery Address");

        jMenuItem190.setText("Delete");
        jMenu98.add(jMenuItem190);

        jMenuItem191.setText("Maintain");
        jMenu98.add(jMenuItem191);

        jMenuItem192.setText("Enquire");
        jMenu98.add(jMenuItem192);

        masterSuppliersMenu3.add(jMenu98);

        periodendMenu.add(masterSuppliersMenu3);

        masterPayrollMenu3.setText("Payroll");

        jMenu99.setText("Emplyoee Details");

        cutMenuItem17.setMnemonic('t');
        cutMenuItem17.setText("Enquire");
        jMenu99.add(cutMenuItem17);

        jMenuItem193.setText("Maintain");
        jMenu99.add(jMenuItem193);

        jMenuItem194.setText("Change Loc/Employee Code");
        jMenu99.add(jMenuItem194);

        jMenuItem195.setText("Delete");
        jMenu99.add(jMenuItem195);

        jMenuItem196.setText("Change Emp. Leave Table");
        jMenu99.add(jMenuItem196);

        jMenuItem197.setText("Consolidate Leave");
        jMenu99.add(jMenuItem197);

        masterPayrollMenu3.add(jMenu99);

        jMenu100.setText("Employee Bank Accounts");

        cutMenuItem18.setMnemonic('t');
        cutMenuItem18.setText("Enquire");
        jMenu100.add(cutMenuItem18);

        jMenuItem198.setText("Maintain");
        jMenu100.add(jMenuItem198);

        jMenuItem199.setText("Delete");
        jMenu100.add(jMenuItem199);

        masterPayrollMenu3.add(jMenu100);

        jMenu101.setText("Employee Standard Pay");

        jMenuItem200.setText("Enquire");
        jMenu101.add(jMenuItem200);

        jMenuItem201.setText("Maintain");
        jMenu101.add(jMenuItem201);

        jMenuItem202.setText("Delete");
        jMenu101.add(jMenuItem202);

        masterPayrollMenu3.add(jMenu101);

        jMenu102.setText("Delivery Address");

        jMenuItem203.setText("Enquire");
        jMenu102.add(jMenuItem203);

        jMenuItem204.setText("Maintain");
        jMenu102.add(jMenuItem204);

        jMenuItem205.setText("Delete");
        jMenu102.add(jMenuItem205);

        masterPayrollMenu3.add(jMenu102);

        jMenu103.setText("Other Account Details");

        jMenuItem206.setText("Delete");
        jMenu103.add(jMenuItem206);

        jMenuItem207.setText("Maintain");
        jMenu103.add(jMenuItem207);

        jMenuItem208.setText("Enquire");
        jMenu103.add(jMenuItem208);

        masterPayrollMenu3.add(jMenu103);

        jMenu104.setText("New Employee");

        jMenuItem209.setText("Details");
        jMenu104.add(jMenuItem209);

        jMenuItem210.setText("Bank Accounts");
        jMenu104.add(jMenuItem210);

        jMenuItem211.setText("Other Accounts");
        jMenu104.add(jMenuItem211);

        jMenuItem212.setText("Standard Pays");
        jMenu104.add(jMenuItem212);

        masterPayrollMenu3.add(jMenu104);

        jMenu105.setText("Payment Schedule");

        jMenuItem213.setText("Enquire");
        jMenu105.add(jMenuItem213);

        jMenuItem214.setText("Maintain");
        jMenu105.add(jMenuItem214);

        jMenuItem215.setText("Delete");
        jMenu105.add(jMenuItem215);

        masterPayrollMenu3.add(jMenu105);

        periodendMenu.add(masterPayrollMenu3);

        masterGeneralLedgMenu3.setText("General Ledger");

        jMenu106.setText("Accounts");

        cutMenuItem19.setMnemonic('t');
        cutMenuItem19.setText("Enquire");
        jMenu106.add(cutMenuItem19);

        jMenuItem216.setText("Maintain");
        jMenu106.add(jMenuItem216);

        jMenuItem217.setText("Delete");
        jMenu106.add(jMenuItem217);

        masterGeneralLedgMenu3.add(jMenu106);

        jMenu107.setText("General Ledger Sets");

        jMenuItem218.setText("Enquire");
        jMenu107.add(jMenuItem218);

        jMenuItem219.setText("Maintain");
        jMenu107.add(jMenuItem219);

        jMenuItem220.setText("Delete");
        jMenu107.add(jMenuItem220);

        masterGeneralLedgMenu3.add(jMenu107);

        periodendMenu.add(masterGeneralLedgMenu3);

        menuBar.add(periodendMenu);

        jMenu3.setText("Tools");
        jMenu3.setEnabled(false);
        jMenu3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        menuBar.add(jMenu3);

        jMenu4.setText("Setup");
        jMenu4.setEnabled(false);
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        menuBar.add(jMenu4);

        jMenu6.setText("Help");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        mnuItemAbout.setText("jMenuItem7");
        jMenu6.add(mnuItemAbout);

        menuBar.add(jMenu6);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        //     PartyForm sd = new PartyForm();

//        sd.setSize(750, 600);
        //       addFrame(sd, "Customers Information");
        /*
         JInternalFrame internal;
         JButton button;

         internal = new JInternalFrame("Always Below",
         true,false,true,false);
         button = new JButton("Ok");
         internal.getContentPane().add(button,BorderLayout.CENTER);
         internal.setBounds(0,0,200,75);
         desktopPane.add(internal,
         new Integer(desktopPane.DEFAULT_LAYER.intValue()-1));

         internal = new JInternalFrame("Default Layer #1",
         true,false,true,true);
         button = new JButton("Ok");
         internal.getContentPane().add(button,BorderLayout.CENTER);
         internal.setBounds(25,25,200,75);
         desktopPane.add(internal,desktopPane.DEFAULT_LAYER);

         internal = new JInternalFrame("Default Layer #2",
         true,false,true,true);
         button = new JButton("Ok");
         internal.getContentPane().add(button,BorderLayout.CENTER);
         internal.setBounds(50,50,200,75);
         desktopPane.add(internal,desktopPane.DEFAULT_LAYER);

         internal = new JInternalFrame("Always Above",
         true,false,true,true);
         button = new JButton("Ok");
         internal.getContentPane().add(button,BorderLayout.CENTER);
         internal.setBounds(75,75,200,75);
         desktopPane.add(internal,
         new Integer(desktopPane.DEFAULT_LAYER.intValue()+1));

         setSize(300,300);
         this.show();        
         */
        TableFTFEditDemo.createAndShowGUI();
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        /*        javax.swing.JFrame frame = new javax.swing.JFrame("Client Editor");
         frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         frame.getContentPane().add(new ClientEditor(this.session));
         frame.setLocationRelativeTo(null);
         frame.pack();
         frame.setVisible(true);
         // TODO add your handling code here:
         */

    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void productMaintainMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productMaintainMenuItemActionPerformed
        // TODO add your handling code here:
//        ProductDetailEditDialog dlg = new ProductDetailEditDialog(this, true, this.session);
//        dlg.setLocationRelativeTo(null);
//        dlg.setVisible(true);

    }//GEN-LAST:event_productMaintainMenuItemActionPerformed

    private void orderViewMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderViewMenuItem3ActionPerformed
    }//GEN-LAST:event_orderViewMenuItem3ActionPerformed

    private void mnuProductCategoryMaintainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProductCategoryMaintainActionPerformed
    }//GEN-LAST:event_mnuProductCategoryMaintainActionPerformed


    private void mnuImportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuImportDataActionPerformed

        Delegator delegator = XuiContainer.getSession().getDelegator();
        JTextArea textArea = new JTextArea(10, 50);

        textArea.setText("All");
        textArea.setCaretPosition(0);
        String fileDir = "C:\\AuthLog\\entity\\pojo\\";
        int result = JOptionPane.showOptionDialog(this, new JScrollPane(textArea), "Please Enter Entity Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                OrderMaxViewEntity.addToIgnoreFieldList();
//                Delegator delegator = XuiContainer.getSession().getDelegator();
                String entName = delegator.getEntityGroupName("Party");
                Map<String, ModelEntity> map = delegator.getModelEntityMapByGroup(entName);
                if (textArea.getText().trim().contains("All")) {
                    Debug.logError("All Entity Start " + entName, module);
                    for (Map.Entry<String, ModelEntity> entryDept : map.entrySet()) {
                        textArea.setText(entryDept.getKey());
                        if ("ProductCategoryMemberAndPrice".equals(entryDept.getKey())) {
                            continue;
                        }
                        try {
                            List listVal = session.getDelegator().findList(entryDept.getKey(), null, null, null, null, false);
                            Debug.logError(entryDept.getKey() + " " + entryDept.getValue().getEntityName(), module);
                            if (listVal != null && listVal.size() > 0) {
                                GenericValue entVal = session.getDelegator().findList(entryDept.getKey(), null, null, null, null, false).get(0);
                                Debug.logError(entryDept.getKey() + " " + entryDept.getValue().getEntityName(), module);
//                            GenericEntity ent = GenericEntity.createGenericEntity(entryDept.getValue());
                                OrderMaxViewEntity.outputFieldMap(entVal, entryDept.getKey(), fileDir);
                                OrderMaxViewEntity.writeJavaDisplayClass(entVal, entryDept.getKey());
                            }
                        } catch (Exception e) {
                            Debug.logError(e, module);
                        }
                    }
                    Debug.logInfo("All Entity End: " + entName, module);
                } else {
                    if (map.containsKey(textArea.getText())) {
//                        GenericEntity ent = GenericEntity.createGenericEntity(map.get(textArea.getText()));
//                        OrderMaxViewEntity.outputFieldMap(ent, textArea.getText());
//                        OrderMaxViewEntity.writeJavaDisplayClass(ent, textArea.getText());

                        OrderMaxViewEntity.outputFieldMap(session.getDelegator().findList(textArea.getText(), null, null, null, null, false).get(0), textArea.getText(), fileDir);
                        OrderMaxViewEntity.writeJavaDisplayClass(session.getDelegator().findList(textArea.getText(), null, null, null, null, false).get(0), textArea.getText());
                    }
                }
                OrderMaxViewEntity.writeColumnIds();
            } catch (GenericEntityException ex) {
                Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
            }

//            outputFieldMap(session.getDelegator().findList("PartyGroup", null, null, null, null, false).get(0), "PartyGroup");
        }
    }//GEN-LAST:event_mnuImportDataActionPerformed

    private void mnuPurchaseOrderEnquireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPurchaseOrderEnquireActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuPurchaseOrderEnquireActionPerformed

    private void mnuPosPanelDesignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPosPanelDesignActionPerformed

    }//GEN-LAST:event_mnuPosPanelDesignActionPerformed

    private void cutMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItem5ActionPerformed
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(900, 750);
        ProductCategoryMaintainDialog dlg = new ProductCategoryMaintainDialog(f, true, XuiContainer.getSession());
//        dlg.setProdCatalogList(prodCatalogCategoryComboModel.dataListModel);
        dlg.setVisible(true);

        final TwoPanelContainerPanel chooser = new TwoPanelContainerPanel();
        JOptionPane pane = new JOptionPane(chooser, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
        final JInternalFrame dialog = pane.createInternalFrame(desktoppane, "Dialog Frame");
//        loadScreen(chooser);
        //      setSizeAndLocation(dialog);
        chooser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                returnStatus = chooser.getReturnStatus();
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    System.out.println("getReturnStatus : "
                            + chooser.getReturnStatus());
                }
                dialog.setVisible(false);
            }
        });

        dialog.setVisible(true);

    }//GEN-LAST:event_cutMenuItem5ActionPerformed

    private void mnuPosTerminalConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPosTerminalConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuPosTerminalConfigActionPerformed
    //DeviceScale m_Scale = null;
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        DynamicViewEntity prodView = new DynamicViewEntity();
        try {
            getClassLoader();
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            final File file = new File("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\screens\\mainscreen\\engine.groovy");
            GroovyShell shell = new GroovyShell();
            //final Object e = shell.evaluate(file);
            Object app = shell.evaluate(file);
            Engine engine = getEngine(app);
            /*
             Engine engine = (Engine) Proxy.newProxyInstance(this.getClass().getClassLoader(),
             new Class[]{Engine.class},
             new InvocationHandler() {
             @Override
             public Object invoke(Object proxy, Method method, Object[] args)
             throws Throwable {
             Method m = e.getClass().getMethod(method.getName());
             return m.invoke(e, args);
             }
             });*/

            engine.start();  // Start the engines!
            engine.stop();  // Stop at once!
        } catch (CompilationFailedException ex) {
            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
// call groovy expressions from Java code
//        Binding binding = new Binding();
//        binding.setVariable("foo", new Integer(2));
//        GroovyShell shell = new GroovyShell(binding);

//        Object value = shell.evaluate(groovyScript);
//  JasperReportFill.runReport();
        /*try {
         DeviceLoader.deviceScale.readWeight();
         } catch (ScaleException ex) {
         Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        /*    try {
         Collection<ContainerConfig.Container> list = ContainerConfig.getContainers("pos-containers.xml");
         for (ContainerConfig.Container it : list) {
         System.out.println(it.name);
         //                System.out.println(it.name);

         for (ContainerConfig.Container.Property key : it.properties.values()) {
         System.out.println("key:" + key.name + " Value= " + key.value);
         }
                
         ControllerOptions.getSession().getAppConfig().getM_propsconfig().putAll(it.properties);
                
         }
         } catch (ContainerException ex) {
         Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String strings[] = {
            "Aamchur-Dried Mango Powder 100Gm",
            "Max Spices Bay Leaves 5Kg",
            "Max Spices Black Lentil 25Kg"
        };
        for (int i = 0; i < strings.length; ++i) {
            String STRING = strings[i];
            setTextToImage(STRING, i);
        }
        /*      
         try {
         // public static void main(String[] args) throws Exception {

         for (int i = 0; i < strings.length; ++i) {
         final BufferedImage image = ImageIO.read(new File("C:\\josh\\ofbiz\\label.jpg"));
         String STRING = strings[i];
         int index = STRING.indexOf("Max Spices");
         if (index > -1) {
         STRING = STRING.substring("Max Spices".length()).trim();
         }
         Debug.logInfo(" substring index: " + index, module);
         //Graphics g = image.getGraphics();
         Graphics2D gO = image.createGraphics();
         gO.setColor(Color.BLACK);
         gO.setFont(new Font("SansSerif", Font.BOLD, 44));
         StringMetrics metrics = new StringMetrics(gO);

         double width = metrics.getWidth(STRING);
         double height = metrics.getHeight(STRING);
         double middle = (488 / 2) - (width / 2);
         double middleH = 510 + (100 - (height / 2));
         if (middle < 10) {
         int midpoint = STRING.length() / 2;

         int splitSpace = STRING.indexOf(" ", midpoint);
         String firstHalf = STRING.substring(0, splitSpace).trim();
         width = metrics.getWidth(firstHalf);
         middle = (488 / 2) - (width / 2);
         gO.drawString(firstHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());

         String secondHalf = STRING.substring(splitSpace);
         width = metrics.getWidth(secondHalf);
         middle = (488 / 2) - (width / 2);
         middleH += height * 1.2;
         gO.drawString(secondHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
         } else {                    
         gO.drawString(STRING, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
         }

         //    g.setColor(Color.BLACK);
         //  g.setFont(g.getFont().deriveFont(30f));
         //g.drawString("Hello World!", 530, 100);
         gO.dispose();

         ImageIO.write(image, "jpg", new File("C:\\josh\\ofbiz\\test" + i + ".jpg"));
         }
         } catch (IOException ex) {
         Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
         }
         */

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        System.out.println("Before printing paths..");

        String s = getClass().getName();
        int i = s.lastIndexOf(".");
        if (i > -1) {
            s = s.substring(i + 1);
        }
        s = s + ".class";
        System.out.println("name " + s);
        Object testPath = this.getClass().getResource(s);
        System.out.println(testPath);
        System.out.println("Path2: " + getClass().getResource(s).getPath());

        File f = new File("/com/openbravo/images/search.png");
        try {
            System.out.println(f.getCanonicalPath() + " " + f.exists());
            if (f.exists()) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search.png"));
                jButton1.setIcon(icon);
            } else {                
                String basePath = BaseHelper.getJarRootPath();
                f = new File(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/search.png"));
                System.out.println(f.getCanonicalPath() + " " + f.exists());
                if (f.exists()) {
                   // javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search.png"));
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(f.getCanonicalPath());                    
                    jButton1.setIcon(icon);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (getClass().getResource("/com/openbravo/images/search.png") != null) {
            System.out.println("Path2: " + getClass().getResource("/com/openbravo/images/search.png").getPath());
        }
        
        org.ofbiz.ordermax.report.reports.erp.NewSupplierPaymentReport rep = new org.ofbiz.ordermax.report.reports.erp.NewSupplierPaymentReport();
        rep.getReportCompiledFileName();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPrintLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintLabelActionPerformed

    private void btnPrintProductShelfLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintProductShelfLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintProductShelfLabelActionPerformed

    void setTextToImage(String STRING, int i) {
        try {
            // public static void main(String[] args) throws Exception {

            final BufferedImage image = ImageIO.read(new File("C:\\josh\\ofbiz\\label.jpg"));
//                String STRING = strings[i];
            int index = STRING.indexOf("Max Spices");
            if (index > -1) {
                STRING = STRING.substring("Max Spices".length()).trim();
            }
            Debug.logInfo(" substring index: " + index, module);
            //Graphics g = image.getGraphics();
            Graphics2D gO = image.createGraphics();
            gO.setColor(Color.BLACK);
            gO.setFont(new Font("SansSerif", Font.BOLD, 44));
            StringMetrics metrics = new StringMetrics(gO);

            double width = metrics.getWidth(STRING);
            double height = metrics.getHeight(STRING);
            double middle = (488 / 2) - (width / 2);
            double middleH = 510 + (100 - (height / 2));
            if (middle < 10) {
                int midpoint = STRING.length() / 2;

                int splitSpace = STRING.indexOf(" ", midpoint);
                String firstHalf = STRING.substring(0, splitSpace).trim();
                width = metrics.getWidth(firstHalf);
                middle = (488 / 2) - (width / 2);
                gO.drawString(firstHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());

                String secondHalf = STRING.substring(splitSpace);
                width = metrics.getWidth(secondHalf);
                middle = (488 / 2) - (width / 2);
                middleH += height * 1.2;
                gO.drawString(secondHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
            } else {
                gO.drawString(STRING, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
            }

            //    g.setColor(Color.BLACK);
            //  g.setFont(g.getFont().deriveFont(30f));
            //g.drawString("Hello World!", 530, 100);
            gO.dispose();

            ImageIO.write(image, "jpg", new File("C:\\josh\\ofbiz\\test" + i + ".jpg"));

        } catch (IOException ex) {
            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class StringMetrics {

        Font font;
        FontRenderContext context;

        public StringMetrics(Graphics2D g2) {

            font = g2.getFont();
            context = g2.getFontRenderContext();
        }

        Rectangle2D getBounds(String message) {

            return font.getStringBounds(message, context);
        }

        double getWidth(String message) {

            Rectangle2D bounds = getBounds(message);
            return bounds.getWidth();
        }

        double getHeight(String message) {

            Rectangle2D bounds = getBounds(message);
            return bounds.getHeight();
        }

    }

    private Engine getEngine(final Object app) {
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        return (Engine) Proxy.newProxyInstance(cl,
                new Class[]{Engine.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                        Method m = app.getClass().getMethod(method.getName());
                        return m.invoke(app, args);
                    }
                });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderMaxMainForm().setVisible(true);
            }
        });
    }

    private javax.swing.JDesktopPane desktoppane = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnFindCustomers;
    private javax.swing.JButton btnFindProducts;
    private javax.swing.JButton btnFindPurchaseInvoice;
    private javax.swing.JButton btnFindPurchaseOrder;
    private javax.swing.JButton btnFindPurchasePayment;
    private javax.swing.JButton btnFindSalesInvoice;
    private javax.swing.JButton btnFindSalesOrder;
    private javax.swing.JButton btnFindSalesPayment;
    private javax.swing.JButton btnFindSalesReturn;
    private javax.swing.JButton btnFindSupplier;
    private javax.swing.JButton btnPrintLabel;
    private javax.swing.JButton btnPrintProductShelfLabel;
    private javax.swing.JButton btnProduct;
    private javax.swing.JButton btnPurchaseInvoice;
    private javax.swing.JButton btnPurchaseOrder;
    private javax.swing.JButton btnPurchasePayment;
    private javax.swing.JButton btnPurchaseReturn;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSalesInvoice;
    private javax.swing.JButton btnSalesOrder;
    private javax.swing.JButton btnSalesPayment;
    private javax.swing.JButton btnSalesReturn;
    private javax.swing.JButton btnSupplier;
    private javax.swing.JMenuItem copyMenuItem1;
    private javax.swing.JMenuItem copyMenuItem2;
    private javax.swing.JMenuItem copyMenuItem3;
    private javax.swing.JMenuItem cutMenuItem10;
    private javax.swing.JMenuItem cutMenuItem12;
    private javax.swing.JMenuItem cutMenuItem13;
    private javax.swing.JMenuItem cutMenuItem14;
    private javax.swing.JMenuItem cutMenuItem15;
    private javax.swing.JMenuItem cutMenuItem16;
    private javax.swing.JMenuItem cutMenuItem17;
    private javax.swing.JMenuItem cutMenuItem18;
    private javax.swing.JMenuItem cutMenuItem19;
    private javax.swing.JMenuItem cutMenuItem3;
    private javax.swing.JMenuItem cutMenuItem5;
    private javax.swing.JMenuItem cutMenuItem7;
    private javax.swing.JMenuItem cutMenuItem8;
    private javax.swing.JMenuItem cutMenuItem9;
    private javax.swing.JMenuItem employeeEnquireMenuItem;
    private javax.swing.JMenuItem employeeMaintainMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem findPurchaseOrderMnuItem;
    private javax.swing.JMenuItem invoiceCreateMenuItem;
    private javax.swing.JMenuItem invoiceEnquireMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenu100;
    private javax.swing.JMenu jMenu101;
    private javax.swing.JMenu jMenu102;
    private javax.swing.JMenu jMenu103;
    private javax.swing.JMenu jMenu104;
    private javax.swing.JMenu jMenu105;
    private javax.swing.JMenu jMenu106;
    private javax.swing.JMenu jMenu107;
    private javax.swing.JMenu jMenu108;
    private javax.swing.JMenu jMenu109;
    private javax.swing.JMenu jMenu21;
    private javax.swing.JMenu jMenu27;
    private javax.swing.JMenu jMenu28;
    private javax.swing.JMenu jMenu29;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu30;
    private javax.swing.JMenu jMenu31;
    private javax.swing.JMenu jMenu32;
    private javax.swing.JMenu jMenu33;
    private javax.swing.JMenu jMenu35;
    private javax.swing.JMenu jMenu37;
    private javax.swing.JMenu jMenu39;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu40;
    private javax.swing.JMenu jMenu42;
    private javax.swing.JMenu jMenu43;
    private javax.swing.JMenu jMenu44;
    private javax.swing.JMenu jMenu45;
    private javax.swing.JMenu jMenu46;
    private javax.swing.JMenu jMenu47;
    private javax.swing.JMenu jMenu48;
    private javax.swing.JMenu jMenu49;
    private javax.swing.JMenu jMenu51;
    private javax.swing.JMenu jMenu52;
    private javax.swing.JMenu jMenu53;
    private javax.swing.JMenu jMenu54;
    private javax.swing.JMenu jMenu55;
    private javax.swing.JMenu jMenu56;
    private javax.swing.JMenu jMenu57;
    private javax.swing.JMenu jMenu58;
    private javax.swing.JMenu jMenu59;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu60;
    private javax.swing.JMenu jMenu61;
    private javax.swing.JMenu jMenu62;
    private javax.swing.JMenu jMenu63;
    private javax.swing.JMenu jMenu64;
    private javax.swing.JMenu jMenu65;
    private javax.swing.JMenu jMenu66;
    private javax.swing.JMenu jMenu67;
    private javax.swing.JMenu jMenu68;
    private javax.swing.JMenu jMenu69;
    private javax.swing.JMenu jMenu70;
    private javax.swing.JMenu jMenu71;
    private javax.swing.JMenu jMenu72;
    private javax.swing.JMenu jMenu73;
    private javax.swing.JMenu jMenu74;
    private javax.swing.JMenu jMenu75;
    private javax.swing.JMenu jMenu76;
    private javax.swing.JMenu jMenu77;
    private javax.swing.JMenu jMenu78;
    private javax.swing.JMenu jMenu79;
    private javax.swing.JMenu jMenu80;
    private javax.swing.JMenu jMenu81;
    private javax.swing.JMenu jMenu82;
    private javax.swing.JMenu jMenu83;
    private javax.swing.JMenu jMenu84;
    private javax.swing.JMenu jMenu85;
    private javax.swing.JMenu jMenu86;
    private javax.swing.JMenu jMenu87;
    private javax.swing.JMenu jMenu88;
    private javax.swing.JMenu jMenu89;
    private javax.swing.JMenu jMenu90;
    private javax.swing.JMenu jMenu91;
    private javax.swing.JMenu jMenu92;
    private javax.swing.JMenu jMenu93;
    private javax.swing.JMenu jMenu94;
    private javax.swing.JMenu jMenu95;
    private javax.swing.JMenu jMenu96;
    private javax.swing.JMenu jMenu97;
    private javax.swing.JMenu jMenu98;
    private javax.swing.JMenu jMenu99;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem100;
    private javax.swing.JMenuItem jMenuItem101;
    private javax.swing.JMenuItem jMenuItem102;
    private javax.swing.JMenuItem jMenuItem103;
    private javax.swing.JMenuItem jMenuItem104;
    private javax.swing.JMenuItem jMenuItem105;
    private javax.swing.JMenuItem jMenuItem106;
    private javax.swing.JMenuItem jMenuItem107;
    private javax.swing.JMenuItem jMenuItem108;
    private javax.swing.JMenuItem jMenuItem109;
    private javax.swing.JMenuItem jMenuItem110;
    private javax.swing.JMenuItem jMenuItem111;
    private javax.swing.JMenuItem jMenuItem112;
    private javax.swing.JMenuItem jMenuItem113;
    private javax.swing.JMenuItem jMenuItem114;
    private javax.swing.JMenuItem jMenuItem115;
    private javax.swing.JMenuItem jMenuItem116;
    private javax.swing.JMenuItem jMenuItem117;
    private javax.swing.JMenuItem jMenuItem118;
    private javax.swing.JMenuItem jMenuItem119;
    private javax.swing.JMenuItem jMenuItem120;
    private javax.swing.JMenuItem jMenuItem121;
    private javax.swing.JMenuItem jMenuItem122;
    private javax.swing.JMenuItem jMenuItem123;
    private javax.swing.JMenuItem jMenuItem124;
    private javax.swing.JMenuItem jMenuItem127;
    private javax.swing.JMenuItem jMenuItem128;
    private javax.swing.JMenuItem jMenuItem129;
    private javax.swing.JMenuItem jMenuItem130;
    private javax.swing.JMenuItem jMenuItem131;
    private javax.swing.JMenuItem jMenuItem132;
    private javax.swing.JMenuItem jMenuItem133;
    private javax.swing.JMenuItem jMenuItem134;
    private javax.swing.JMenuItem jMenuItem135;
    private javax.swing.JMenuItem jMenuItem136;
    private javax.swing.JMenuItem jMenuItem137;
    private javax.swing.JMenuItem jMenuItem138;
    private javax.swing.JMenuItem jMenuItem139;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem140;
    private javax.swing.JMenuItem jMenuItem141;
    private javax.swing.JMenuItem jMenuItem142;
    private javax.swing.JMenuItem jMenuItem143;
    private javax.swing.JMenuItem jMenuItem144;
    private javax.swing.JMenuItem jMenuItem145;
    private javax.swing.JMenuItem jMenuItem146;
    private javax.swing.JMenuItem jMenuItem147;
    private javax.swing.JMenuItem jMenuItem148;
    private javax.swing.JMenuItem jMenuItem149;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem150;
    private javax.swing.JMenuItem jMenuItem151;
    private javax.swing.JMenuItem jMenuItem152;
    private javax.swing.JMenuItem jMenuItem153;
    private javax.swing.JMenuItem jMenuItem154;
    private javax.swing.JMenuItem jMenuItem155;
    private javax.swing.JMenuItem jMenuItem156;
    private javax.swing.JMenuItem jMenuItem157;
    private javax.swing.JMenuItem jMenuItem158;
    private javax.swing.JMenuItem jMenuItem159;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem160;
    private javax.swing.JMenuItem jMenuItem161;
    private javax.swing.JMenuItem jMenuItem162;
    private javax.swing.JMenuItem jMenuItem163;
    private javax.swing.JMenuItem jMenuItem164;
    private javax.swing.JMenuItem jMenuItem165;
    private javax.swing.JMenuItem jMenuItem166;
    private javax.swing.JMenuItem jMenuItem167;
    private javax.swing.JMenuItem jMenuItem168;
    private javax.swing.JMenuItem jMenuItem169;
    private javax.swing.JMenuItem jMenuItem170;
    private javax.swing.JMenuItem jMenuItem171;
    private javax.swing.JMenuItem jMenuItem172;
    private javax.swing.JMenuItem jMenuItem173;
    private javax.swing.JMenuItem jMenuItem174;
    private javax.swing.JMenuItem jMenuItem175;
    private javax.swing.JMenuItem jMenuItem176;
    private javax.swing.JMenuItem jMenuItem177;
    private javax.swing.JMenuItem jMenuItem178;
    private javax.swing.JMenuItem jMenuItem179;
    private javax.swing.JMenuItem jMenuItem180;
    private javax.swing.JMenuItem jMenuItem181;
    private javax.swing.JMenuItem jMenuItem182;
    private javax.swing.JMenuItem jMenuItem183;
    private javax.swing.JMenuItem jMenuItem184;
    private javax.swing.JMenuItem jMenuItem185;
    private javax.swing.JMenuItem jMenuItem186;
    private javax.swing.JMenuItem jMenuItem187;
    private javax.swing.JMenuItem jMenuItem188;
    private javax.swing.JMenuItem jMenuItem189;
    private javax.swing.JMenuItem jMenuItem190;
    private javax.swing.JMenuItem jMenuItem191;
    private javax.swing.JMenuItem jMenuItem192;
    private javax.swing.JMenuItem jMenuItem193;
    private javax.swing.JMenuItem jMenuItem194;
    private javax.swing.JMenuItem jMenuItem195;
    private javax.swing.JMenuItem jMenuItem196;
    private javax.swing.JMenuItem jMenuItem197;
    private javax.swing.JMenuItem jMenuItem198;
    private javax.swing.JMenuItem jMenuItem199;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem200;
    private javax.swing.JMenuItem jMenuItem201;
    private javax.swing.JMenuItem jMenuItem202;
    private javax.swing.JMenuItem jMenuItem203;
    private javax.swing.JMenuItem jMenuItem204;
    private javax.swing.JMenuItem jMenuItem205;
    private javax.swing.JMenuItem jMenuItem206;
    private javax.swing.JMenuItem jMenuItem207;
    private javax.swing.JMenuItem jMenuItem208;
    private javax.swing.JMenuItem jMenuItem209;
    private javax.swing.JMenuItem jMenuItem210;
    private javax.swing.JMenuItem jMenuItem211;
    private javax.swing.JMenuItem jMenuItem212;
    private javax.swing.JMenuItem jMenuItem213;
    private javax.swing.JMenuItem jMenuItem214;
    private javax.swing.JMenuItem jMenuItem215;
    private javax.swing.JMenuItem jMenuItem216;
    private javax.swing.JMenuItem jMenuItem217;
    private javax.swing.JMenuItem jMenuItem218;
    private javax.swing.JMenuItem jMenuItem219;
    private javax.swing.JMenuItem jMenuItem220;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem62;
    private javax.swing.JMenuItem jMenuItem63;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem65;
    private javax.swing.JMenuItem jMenuItem66;
    private javax.swing.JMenuItem jMenuItem67;
    private javax.swing.JMenuItem jMenuItem68;
    private javax.swing.JMenuItem jMenuItem69;
    private javax.swing.JMenuItem jMenuItem70;
    private javax.swing.JMenuItem jMenuItem71;
    private javax.swing.JMenuItem jMenuItem87;
    private javax.swing.JMenuItem jMenuItem88;
    private javax.swing.JMenuItem jMenuItem89;
    private javax.swing.JMenuItem jMenuItem90;
    private javax.swing.JMenuItem jMenuItem91;
    private javax.swing.JMenuItem jMenuItem92;
    private javax.swing.JMenuItem jMenuItem93;
    private javax.swing.JMenuItem jMenuItem94;
    private javax.swing.JMenuItem jMenuItem95;
    private javax.swing.JMenuItem jMenuItem96;
    private javax.swing.JMenuItem jMenuItem97;
    private javax.swing.JMenuItem jMenuItem98;
    private javax.swing.JMenuItem jMenuItem99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu masterCustomerMenu;
    private javax.swing.JMenu masterCustomerMenu1;
    private javax.swing.JMenu masterCustomerMenu2;
    private javax.swing.JMenu masterCustomerMenu3;
    private javax.swing.JMenu masterGeneralLedgMenu;
    private javax.swing.JMenu masterGeneralLedgMenu1;
    private javax.swing.JMenu masterGeneralLedgMenu2;
    private javax.swing.JMenu masterGeneralLedgMenu3;
    private javax.swing.JMenu masterGeneralLedgMenu4;
    private javax.swing.JMenu masterPayrollMenu;
    private javax.swing.JMenu masterPayrollMenu1;
    private javax.swing.JMenu masterPayrollMenu2;
    private javax.swing.JMenu masterPayrollMenu3;
    private javax.swing.JMenu masterProductsMenu;
    private javax.swing.JMenu masterProductsMenu1;
    private javax.swing.JMenu masterProductsMenu2;
    private javax.swing.JMenu masterProductsMenu3;
    private javax.swing.JMenu masterSuppliersMenu;
    private javax.swing.JMenu masterSuppliersMenu1;
    private javax.swing.JMenu masterSuppliersMenu2;
    private javax.swing.JMenu masterSuppliersMenu3;
    private javax.swing.JMenu masterfilesMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnProductPricingGroup;
    private javax.swing.JMenu mnuAccountManager;
    private javax.swing.JMenu mnuCatalog;
    private javax.swing.JMenu mnuCategories;
    private javax.swing.JMenuItem mnuCreatePayment;
    private javax.swing.JMenuItem mnuCreatePayment1;
    private javax.swing.JMenu mnuCustomerPayment;
    private javax.swing.JMenu mnuCustomers;
    private javax.swing.JMenuItem mnuEditPurchaseInvoice;
    private javax.swing.JMenuItem mnuFindInvoiceListItem;
    private javax.swing.JMenuItem mnuGenerateInvoiceFromOrderList;
    private javax.swing.JMenuItem mnuImportData;
    private javax.swing.JMenuItem mnuImportDataFromFile;
    private javax.swing.JMenuItem mnuInventory;
    private javax.swing.JMenuItem mnuInventoryDetailList;
    private javax.swing.JMenuItem mnuInventoryItemList;
    private javax.swing.JMenuItem mnuItemAbout;
    private javax.swing.JMenuItem mnuItemInventoryReceipt;
    private javax.swing.JMenuItem mnuItemPosReport;
    private javax.swing.JMenuItem mnuItemReport;
    private javax.swing.JMenuItem mnuItemReportMain;
    private javax.swing.JMenu mnuMasterInventory;
    private javax.swing.JMenu mnuOrders;
    private javax.swing.JMenuItem mnuPaymentList;
    private javax.swing.JMenuItem mnuPaymentList1;
    private javax.swing.JMenuItem mnuPosCOnfiguration;
    private javax.swing.JMenu mnuPosPanelDesign;
    private javax.swing.JMenuItem mnuPosTerminalConfig;
    private javax.swing.JMenuItem mnuProductCategoryMaintain;
    private javax.swing.JMenu mnuProductFeature;
    private javax.swing.JMenu mnuProductStoreSetup;
    private javax.swing.JMenu mnuProducts;
    private javax.swing.JMenuItem mnuPurchaseInvoiceList;
    private javax.swing.JMenuItem mnuPurchaseOrderCreate;
    private javax.swing.JMenuItem mnuPurchaseOrderEnquire;
    private javax.swing.JMenuItem mnuPurchaseOrderList;
    private javax.swing.JMenuItem mnuPurchaseOrderReturnCreate;
    private javax.swing.JMenuItem mnuPurchaseOrderReturnList;
    private javax.swing.JMenuItem mnuReturnSalesOrder;
    private javax.swing.JMenu mnuReturnsMain;
    private javax.swing.JMenu mnuReturnsMain1;
    private javax.swing.JMenuItem mnuSalesOrderCreate;
    private javax.swing.JMenuItem mnuSalesOrderList;
    private javax.swing.JMenuItem mnuSalesOrderList1;
    private javax.swing.JMenuItem mnuSalesOrderList2;
    private javax.swing.JMenuItem mnuSalesOrderReturnList;
    private javax.swing.JMenu mnuShoppingList;
    private javax.swing.JMenu mnuSupplier;
    private javax.swing.JMenu mnuSupplierProductGroup;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem orderViewMenuItem1;
    private javax.swing.JMenuItem orderViewMenuItem2;
    private javax.swing.JMenuItem orderViewMenuItem3;
    private javax.swing.JMenu periodendMenu;
    private javax.swing.JMenuItem productEnquireMenuItem;
    private javax.swing.JMenuItem productEnquireMenuItem1;
    private javax.swing.JMenuItem productMaintainMenuItem;
    private javax.swing.JMenuItem reportSupplierPaymentAgeingReport;
    private javax.swing.JMenu reportsMenu;
    private javax.swing.JMenuItem salesOrderCreateMenuItem1;
    private javax.swing.JMenuItem salesOrderCreateMenuItem2;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JMenu transactionsMenu;
    // End of variables declaration//GEN-END:variables

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    @Override
    public void waitCursorBegin() {
        Debug.logInfo("waitCursorBegin", module);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    @Override
    public void waitCursorEnd() {
        Debug.logInfo("waitCursorEnd", module);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    //AppConfig config = null;

    @Override
    public AppProperties getProperties() {
        return ControllerOptions.getSession().getAppConfig();
    }
}
