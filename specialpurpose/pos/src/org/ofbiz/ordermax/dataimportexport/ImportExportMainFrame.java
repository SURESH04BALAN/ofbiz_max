/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport;

import mvc.view.*;
import mvc.controller.ClearListModelAction;
import mvc.controller.LoadCustomerAction;
import mvc.controller.SwingWorkerProgressModel;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import mvc.controller.LoadOrderAction;
import mvc.controller.LoadOutstandingPurchaseInvoiceAction;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadProductFromFileAction;
import mvc.controller.LoadProductPricesFromFileAction;
import mvc.controller.LoadSupplierAction;
import mvc.controller.LoadSupplierProductAction;
import org.ofbiz.ordermax.dataimportexport.loaders.BigFishProduct;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadBigFishProductFromFileAction;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadMaxProductFromFileAction;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadProductCompositeFromFileAction;
import mvc.controller.dataload.posdata.LoadPosSaleDataFromFileAction;
import mvc.controller.dataload.posdata.PosSalesData;
import mvc.controller.dataload.posdata.PosSalesLoadViewPanel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadGroceryAndScaleProductWorker;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadJoshProductWorker;
import org.ofbiz.pos.PosTransaction;

public class ImportExportMainFrame extends JInternalFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4353611743416911021L;

    private ListAdapterListModel<Person> personListModel = new ListAdapterListModel<Person>();
    private ListAdapterListModel<ProductComposite> productListModel = new ListAdapterListModel<ProductComposite>();
    private ListAdapterListModel<ProductComposite> productMaxListModel = new ListAdapterListModel<ProductComposite>();
    private ListAdapterListModel<BigFishProduct> bigFishproductListModel = new ListAdapterListModel<BigFishProduct>();
    // fix image
    private ListAdapterListModel<PosSalesData> fixImageDataListModel = new ListAdapterListModel<PosSalesData>();

    private ListAdapterListModel<PosSalesData> posSalesDataListModel = new ListAdapterListModel<PosSalesData>();
    private ListAdapterListModel<PosSalesData> rootyPosSalesDataListModel = new ListAdapterListModel<PosSalesData>();
    private ListAdapterListModel<PosSalesData> liverpoolPosSalesDataListModel = new ListAdapterListModel<PosSalesData>();
    private ListAdapterListModel<ProductComposite> productCompListModel = new ListAdapterListModel<ProductComposite>();
    private ListAdapterListModel<ProductPriceComposite> productPriceListModel = new ListAdapterListModel<ProductPriceComposite>();
    private ListAdapterListModel<Account> accountListModel = new ListAdapterListModel<Account>();
    private ListAdapterListModel<Account> supplierListModel = new ListAdapterListModel<Account>();
    private ListAdapterListModel<SupplierProductComposite> supplierProductListModel = new ListAdapterListModel<SupplierProductComposite>();
//    private ListAdapterListModel<ProductComposite> productCompositeListModel = new ListAdapterListModel<ProductComposite>();                

    private ListAdapterListModel<ShipmentReceiptComposite> ricListModel = new ListAdapterListModel<ShipmentReceiptComposite>();

    private ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

    private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);

    private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(
            progressBar);

    private ListAdapterListModel<Person> invoiceListModel = new ListAdapterListModel<Person>();

//    private ReceiveInventoryPanel overviewPanel = new ReceiveInventoryPanel();
//    private OrderOverviewPanel orderOverviewPanel = new OrderOverviewPanel();
//    private PartyLoadViewPanel partyLoadViewPanel = new PartyLoadViewPanel();
    private FixImageLoadViewPanel fixImageLoadViewPanel = new FixImageLoadViewPanel();
    private CustomerLoadViewPanel accountLoadViewPanel = new CustomerLoadViewPanel();
    private CustomerLoadViewPanel supplierLoadViewPanel = new CustomerLoadViewPanel();
    private ProductLoadViewPanel productLoadViewPanel = new ProductLoadViewPanel();
    private ProductLoadViewPanel productMaxLoadViewPanel = new ProductLoadViewPanel();
    private BigFishProductLoadViewPanel bigFishProductLoadViewPanel = new BigFishProductLoadViewPanel();
    private PosSalesLoadViewPanel posSalesLoadViewPanel = new PosSalesLoadViewPanel();
    private PosSalesLoadViewPanel rootyPosSalesLoadViewPanel = new PosSalesLoadViewPanel();
    private PosSalesLoadViewPanel liverpoolPosSalesLoadViewPanel = new PosSalesLoadViewPanel();

    private ProductLoadViewPanel productCompLoadViewPanel = new ProductLoadViewPanel();
    private SupplierProductLoadViewPanel supplierProductLoadViewPanel = new SupplierProductLoadViewPanel();
    private ProductPriceLoadViewPanel productPriceLoadViewPanel = new ProductPriceLoadViewPanel();
    private LoadGroceryAndScaleProductWorker.LoadGroceryAndScalAction groceryAndScaleAction = null;
        private LoadJoshProductWorker.LoadJoshProductAction loadJoshProductAction = null;
//    private InvoiceCompositeOverviewPanel invoiceCompositePanel = new InvoiceCompositeOverviewPanel();
    private LoadSpeedSimulationPanel loadPersonSpeedSimulationPanel = new LoadSpeedSimulationPanel();
//    private LoadSpeedSimulationPanel loadOrderSpeedSimulationPanel = new LoadSpeedSimulationPanel();

    private Component currentContent;
    XuiSession session = null;

    public ImportExportMainFrame(XuiSession delegator) {
        this.session = delegator;
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel parentPanel = new JPanel(new BorderLayout());
        //    JPanel rightPanel = new JPanel(new BorderLayout());

        progressBar.setStringPainted(true);
        javax.swing.JTabbedPane jTabbedPane = new javax.swing.JTabbedPane();

        parentPanel.add(loadPersonSpeedSimulationPanel, BorderLayout.NORTH);
        parentPanel.add(jTabbedPane, BorderLayout.CENTER);
        parentPanel.add(progressBar, BorderLayout.SOUTH);
//        this

        accountLoadViewPanel.setAccountList(accountListModel);
        supplierLoadViewPanel.setAccountList(supplierListModel);
        supplierLoadViewPanel.textEdit.setText("C:\\ordermax\\Real_Data\\cleaned_supplier.csv");
        productLoadViewPanel.setProductList(productListModel);
        productMaxLoadViewPanel.textEdit.setText("C:\\ordermax\\Real_Data\\SEVEN_HILLS_data_CUT_DOWN_VIRTUAL.csv");
        productMaxLoadViewPanel.setProductList(productMaxListModel);
        bigFishProductLoadViewPanel.setProductList(bigFishproductListModel);

        posSalesLoadViewPanel.setProductList(posSalesDataListModel);
        posSalesLoadViewPanel.textEdit.setText("C:\\backup\\Seven Hills Sales.csv");

        //fix image
        fixImageLoadViewPanel.setProductList(fixImageDataListModel);

        rootyPosSalesLoadViewPanel.setProductList(rootyPosSalesDataListModel);
        rootyPosSalesLoadViewPanel.textEdit.setText("C:\\backup\\Seven Hills Sales.csv");

        liverpoolPosSalesLoadViewPanel.setProductList(liverpoolPosSalesDataListModel);
        liverpoolPosSalesLoadViewPanel.textEdit.setText("C:\\backup\\Seven Hills Sales.csv");

        productCompLoadViewPanel.setProductList(productCompListModel);
//        partyLoadViewPanel.setPersonList(personListModel);
        supplierProductLoadViewPanel.setSupplierProductList(supplierProductListModel);
        productPriceLoadViewPanel.setProductPriceList(productPriceListModel);
        JPanel leftPanel = new JPanel(new BorderLayout());
        //      leftPanel.add(partyLoadViewPanel, BorderLayout.CENTER);

        JPanel accountPanel = new JPanel(new BorderLayout());
        accountPanel.add(accountLoadViewPanel, BorderLayout.CENTER);

        JPanel supplierPanel = new JPanel(new BorderLayout());
        supplierPanel.add(supplierLoadViewPanel, BorderLayout.CENTER);

        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(productLoadViewPanel, BorderLayout.CENTER);

        JPanel productMaxPanel = new JPanel(new BorderLayout());
        productMaxPanel.add(productMaxLoadViewPanel, BorderLayout.CENTER);

        //JPanel productCompPanel = new JPanel(new BorderLayout());
        //productCompPanel.add(productCompLoadViewPanel, BorderLayout.CENTER);
        JPanel supplierProductPanel = new JPanel(new BorderLayout());
        supplierProductPanel.add(supplierProductLoadViewPanel, BorderLayout.CENTER);

        JPanel productPricePanel = new JPanel(new BorderLayout());
        productPricePanel.add(productPriceLoadViewPanel, BorderLayout.CENTER);
        
        groceryAndScaleAction = new LoadGroceryAndScaleProductWorker.LoadGroceryAndScalAction(
                swingWorkerProgressModel,
                swingWorkerBasedComponentVisibility,
                loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel()
        );

        
        loadJoshProductAction = new LoadJoshProductWorker.LoadJoshProductAction(
                swingWorkerProgressModel,
                swingWorkerBasedComponentVisibility,
                loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel()
        );
                
        jTabbedPane.add(leftPanel, "Load Order");
        jTabbedPane.add(accountPanel, "Load Account");
        jTabbedPane.add(supplierPanel, "Load Supplier");
        jTabbedPane.add(productPanel, "Load Product");
        jTabbedPane.add(productMaxPanel, "Load Max Product");

        jTabbedPane.add(bigFishProductLoadViewPanel, "Load Big Fish Product");
        jTabbedPane.add(supplierProductPanel, "Load SupplierProduct");
        jTabbedPane.add(productPricePanel, "Load Product Price");
        jTabbedPane.add(productCompLoadViewPanel, "Load Product Summary File");
        jTabbedPane.add(posSalesLoadViewPanel, "Load Seven Hills Pos Sales Data File");
        jTabbedPane.add(rootyPosSalesLoadViewPanel, "Load Rooty Hills Pos Sales Data File");
        jTabbedPane.add(liverpoolPosSalesLoadViewPanel, "Load Liverpool Pos Sales Data File");
        jTabbedPane.add(groceryAndScaleAction.productCompLoadViewPanel, "Load Temporary Produts(Grocery and Scale)");
        jTabbedPane.add(loadJoshProductAction.productCompLoadViewPanel, "Load Josh Rockdale Products");        
        //fix image
        jTabbedPane.add(fixImageLoadViewPanel, "Fix Image files");

//        contentPane.add(leftPanel, BorderLayout.CENTER);
//        jTabbedPane.add(rightPanel, "Load Main");
        this.add(parentPanel);

        leftPanel.setSize(605, 660);
        setSize(1100, 660);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Swing MVC Implementation Example");
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        initMenu(jMenuBar);

//		setLocationRelativeTo(null);
    }

    private void initMenu(JMenuBar jMenuBar) {
        initFileMenu(jMenuBar);
        initOrderMenu(jMenuBar);
    }

    private void initFileMenu(JMenuBar jMenuBar) {
        JMenu fileMenu = new JMenu("Persons");
        jMenuBar.add(fileMenu);

        //load customers
        LoadCustomerAction loadPersonsAction = new LoadCustomerAction(accountLoadViewPanel.textEdit, accountListModel, session);
        loadPersonsAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadPersonsAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadPersonsAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        accountLoadViewPanel.jBtnLoad.addActionListener(loadPersonsAction);

        JMenuItem loadMenuItem = new JMenuItem(loadPersonsAction);
        fileMenu.add(loadMenuItem);

        //load suppliers
        LoadSupplierAction loadSupplierAction = new LoadSupplierAction(supplierLoadViewPanel.textEdit, supplierListModel, session);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadSupplierAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        supplierLoadViewPanel.jBtnLoad.addActionListener(loadSupplierAction);
        JMenuItem supplierMenuItem = new JMenuItem(loadSupplierAction);
        fileMenu.add(supplierMenuItem);

        //load supplier product
        LoadSupplierProductAction loadSupplierProductAction = new LoadSupplierProductAction(supplierProductLoadViewPanel.textEdit, supplierProductListModel, session);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadSupplierAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        supplierProductLoadViewPanel.jBtnLoad.addActionListener(loadSupplierProductAction);
        JMenuItem supplierProductMenuItem = new JMenuItem(loadSupplierProductAction);
        fileMenu.add(supplierProductMenuItem);

        //load suppliers
        LoadProductFromFileAction loadProductAction = new LoadProductFromFileAction(productLoadViewPanel.textEdit, productListModel, session);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadProductAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        productLoadViewPanel.jBtnLoad.addActionListener(loadProductAction);

        LoadMaxProductFromFileAction loadMaxProductAction = new LoadMaxProductFromFileAction(productMaxLoadViewPanel.textEdit, productMaxListModel, session);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadProductAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        productMaxLoadViewPanel.jBtnLoad.addActionListener(loadMaxProductAction);

        LoadProductCompositeFromFileAction loadProductCompAction = new LoadProductCompositeFromFileAction(productCompLoadViewPanel.textEdit, productCompListModel, session);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadProductAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        productCompLoadViewPanel.jBtnLoad.addActionListener(loadProductCompAction);

        LoadBigFishProductFromFileAction loadBigFishProductFromFileAction = new LoadBigFishProductFromFileAction(bigFishProductLoadViewPanel.textEdit, bigFishproductListModel, session);
        loadBigFishProductFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadBigFishProductFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadBigFishProductFromFileAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        bigFishProductLoadViewPanel.jBtnLoad.addActionListener(loadBigFishProductFromFileAction);

        LoadPosSaleDataFromFileAction loadPosSaleDataFromFileAction = new LoadPosSaleDataFromFileAction(posSalesLoadViewPanel.textEdit, posSalesDataListModel, session);
        loadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadPosSaleDataFromFileAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        posSalesLoadViewPanel.jBtnLoad.addActionListener(loadPosSaleDataFromFileAction);

        posSalesLoadViewPanel.jBtnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PosTransaction trans = PosTransaction.getCurrentTx(session);
                posSalesLoadViewPanel.printTotals(trans, null, false, posSalesDataListModel.getList(), "totals_seven_hills.txt");

            }
        });

        //rooty hill
        LoadPosSaleDataFromFileAction rootyLoadPosSaleDataFromFileAction = new LoadPosSaleDataFromFileAction(rootyPosSalesLoadViewPanel.textEdit, rootyPosSalesDataListModel, session);
        rootyLoadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        rootyLoadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        rootyLoadPosSaleDataFromFileAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        rootyPosSalesLoadViewPanel.jBtnLoad.addActionListener(rootyLoadPosSaleDataFromFileAction);

        rootyPosSalesLoadViewPanel.jBtnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PosTransaction trans = PosTransaction.getCurrentTx(session);
                rootyPosSalesLoadViewPanel.printTotals(trans, null, false, rootyPosSalesDataListModel.getList(), "totals_rooty_hills.txt");

            }
        });

        fixImageLoadViewPanel.jBtnLoadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fixImageLoadViewPanel.processFile();

            }
        });

        fixImageLoadViewPanel.jBtnLoadDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fixImageLoadViewPanel.processDir();

            }
        });

        fixImageLoadViewPanel.jBtnLoadOldOfBizFilesDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //fixImageLoadViewPanel.processOldOfbizFiles();
                fixImageLoadViewPanel.setNoImageAvaliable();

            }
        });

        //rooty hill
        LoadPosSaleDataFromFileAction liverpoolLoadPosSaleDataFromFileAction = new LoadPosSaleDataFromFileAction(posSalesLoadViewPanel.textEdit, liverpoolPosSalesDataListModel, session);
        liverpoolLoadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        liverpoolLoadPosSaleDataFromFileAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        liverpoolLoadPosSaleDataFromFileAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        liverpoolPosSalesLoadViewPanel.jBtnLoad.addActionListener(liverpoolLoadPosSaleDataFromFileAction);

        liverpoolPosSalesLoadViewPanel.jBtnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PosTransaction trans = PosTransaction.getCurrentTx(session);
                rootyPosSalesLoadViewPanel.printTotals(trans, null, false, liverpoolPosSalesDataListModel.getList(), "totals_liverpool.txt");

            }
        });
//                jTabbedPane.add(rootyPosSalesLoadViewPanel, "Load Rooty Hills Pos Sales Data File");
//        jTabbedPane.add(liverpoolPosSalesLoadViewPanel, "Load Liverpool Pos Sales Data File");

//        JMenuItem productMenuItem = new JMenuItem(loadProductAction);
//        fileMenu.add(productMenuItem);
        //load suppliers
        LoadProductPricesFromFileAction loadPriceAction = new LoadProductPricesFromFileAction(productPriceLoadViewPanel.textEdit, productPriceListModel, session);

        loadPriceAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);

        loadPriceAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);

        loadPriceAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        productPriceLoadViewPanel.jBtnLoad.addActionListener(loadPriceAction);
        JMenuItem priceMenuItem = new JMenuItem(loadPriceAction);

        fileMenu.add(priceMenuItem);

        ClearListModelAction clearPersonsModelAction = new ClearListModelAction(personListModel);
        JMenuItem clearPersonsMenuItem = new JMenuItem(clearPersonsModelAction);

        fileMenu.add(clearPersonsMenuItem);

    }

    private void initOrderMenu(JMenuBar jMenuBar) {
        JMenu fileMenu = new JMenu("Orders");
        jMenuBar.add(fileMenu);

        LoadOrderAction loadOrderAction = new LoadOrderAction(orderListModel, session);
        loadOrderAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadOrderAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadOrderAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        JMenuItem loadMenuItem = new JMenuItem(loadOrderAction);
        fileMenu.add(loadMenuItem);

        LoadOutstandingPurchaseInvoiceAction loadInvoiceAction = new LoadOutstandingPurchaseInvoiceAction(invoiceCompositeListModel, session);
        loadInvoiceAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadInvoiceAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadInvoiceAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        JMenuItem invoiceMenuItem = new JMenuItem(loadInvoiceAction);
        fileMenu.add(invoiceMenuItem);

        ClearListModelAction clearPersonsModelAction = new ClearListModelAction(personListModel);
        JMenuItem clearPersonsMenuItem = new JMenuItem(clearPersonsModelAction);
        fileMenu.add(clearPersonsMenuItem);

    }

    public void setContent(Component component) {
        Container contentPane = getContentPane();
        if (currentContent != null) {
            contentPane.remove(currentContent);
        }
        contentPane.add(component, BorderLayout.CENTER);
        currentContent = component;
        contentPane.doLayout();
        repaint();
    }

}
