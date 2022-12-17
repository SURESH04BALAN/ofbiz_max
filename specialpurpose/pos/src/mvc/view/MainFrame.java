/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import mvc.controller.ClearListModelAction;
import mvc.controller.LoadCustomerAction;
import mvc.controller.SwingWorkerProgressModel;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.purchaseorder.inventory.ReceiveInventoryPanel;

public class MainFrame extends JInternalFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4353611743416911021L;

    private ListAdapterListModel<Person> personListModel = new ListAdapterListModel<Person>();
    private ListAdapterListModel<ShipmentReceiptComposite> ricListModel = new ListAdapterListModel<ShipmentReceiptComposite>();

    private ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

    private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);

    private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(
            progressBar);
    
    private ListAdapterListModel<Person> invoiceListModel = new ListAdapterListModel<Person>();

    private ReceiveInventoryPanel overviewPanel = new ReceiveInventoryPanel();
//    private OrderOverviewPanel orderOverviewPanel = new OrderOverviewPanel();
    private PartyLoadViewPanel partyLoadViewPanel = new PartyLoadViewPanel();
    private InvoiceCompositeOverviewPanel invoiceCompositePanel = new InvoiceCompositeOverviewPanel();
    private LoadSpeedSimulationPanel loadPersonSpeedSimulationPanel = new LoadSpeedSimulationPanel();
    private LoadSpeedSimulationPanel loadOrderSpeedSimulationPanel = new LoadSpeedSimulationPanel();

    private Component currentContent;
    XuiSession session = null;

    public MainFrame(XuiSession delegator) {
        this.session = delegator;
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        progressBar.setStringPainted(true);
//		overviewPanel.setPersonList(personListModel);
              /*  ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite("14893", session);    
         for(ShipmentReceiptComposite iter : ric.getShipmentReceiptCompositeList().getList()){
         ricListModel.add(iter);
         }                
         */
//		overviewPanel.setReceiveInventoryList(ricListModel);
        partyLoadViewPanel.setPersonList(personListModel);
        leftPanel.add(partyLoadViewPanel);
        setContent(leftPanel);
        leftPanel.add(loadPersonSpeedSimulationPanel, BorderLayout.NORTH);
        leftPanel.add(progressBar, BorderLayout.SOUTH);

        invoiceCompositePanel.setInvoiceCompositeList(invoiceCompositeListModel);
        rightPanel.add(invoiceCompositePanel);
        setContent(rightPanel);
        rightPanel.add(loadOrderSpeedSimulationPanel, BorderLayout.NORTH);
        rightPanel.add(progressBar, BorderLayout.SOUTH);

        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        initMenu(jMenuBar);
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.CENTER);

        leftPanel.setSize(605, 660);
        setSize(1100, 660);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Swing MVC Implementation Example");

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
        //LoadCustomerAction loadPersonsAction = new LoadCustomerAction(personListModel, session);
        //loadPersonsAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        //loadPersonsAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        //loadPersonsAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());

//        JMenuItem loadMenuItem = new JMenuItem(loadPersonsAction);
//        fileMenu.add(loadMenuItem);

        //load suppliers
/*        LoadSupplierAction loadSupplierAction = new LoadSupplierAction(personListModel, session);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadSupplierAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());

        JMenuItem supplierMenuItem = new JMenuItem(loadSupplierAction);
        fileMenu.add(supplierMenuItem);

        //load supplier product
        LoadSupplierProductAction loadSupplierProductAction = new LoadSupplierProductAction(personListModel, session);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadSupplierAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadSupplierAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());
        JMenuItem supplierProductMenuItem = new JMenuItem(loadSupplierProductAction);
        fileMenu.add(supplierProductMenuItem);


        //load suppliers
//        LoadProductFromFileAction loadProductAction = new LoadProductFromFileAction(personListModel, session);
//        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
//        loadProductAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
//        loadProductAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());

//        JMenuItem productMenuItem = new JMenuItem(loadProductAction);
//        fileMenu.add(productMenuItem);

        
        //load suppliers
        LoadProductPricesFromFileAction loadPriceAction = new LoadProductPricesFromFileAction(personListModel, session);
        loadPriceAction.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        loadPriceAction.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        loadPriceAction.setLoadSpeedModel(loadPersonSpeedSimulationPanel.getPersonsLoadSpeedModel());

        JMenuItem priceMenuItem = new JMenuItem(loadPriceAction);
        fileMenu.add(priceMenuItem);
                
        ClearListModelAction clearPersonsModelAction = new ClearListModelAction(personListModel);
        JMenuItem clearPersonsMenuItem = new JMenuItem(clearPersonsModelAction);
        fileMenu.add(clearPersonsMenuItem);
*/
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
