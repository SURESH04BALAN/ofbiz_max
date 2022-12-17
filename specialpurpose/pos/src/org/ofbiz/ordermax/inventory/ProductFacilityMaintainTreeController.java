/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.price.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.ThreePanelContainerDlg;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductFacility;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.product.ProductMaintainTreeController;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.ProductSelectionPanelFrame;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author administrator
 */
public class ProductFacilityMaintainTreeController extends BaseMainScreen {

    public static final String module = ProductFacilityMaintainTreeController.class.getName();
    protected ProductFacilityEditTablePanel productPriceEditTablePanel = null;
    ProductSelectionPanelFrame catalogTreeSelPanel;
    javax.swing.JTabbedPane tabbedPanePrice = null;

    static public ProductFacilityMaintainTreeController runController(ControllerOptions options) {

        ProductFacilityMaintainTreeController controller = new ProductFacilityMaintainTreeController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ProductFacilityMaintainTreeController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ProductMaintainTreeController.module, options.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options;
    Facility currentfacility = null;

    public ProductFacilityMaintainTreeController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 200;
        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);

        }
        contFrame.setLocation(x, y);
    }

    public void loadScreen(final ContainerPanelInterface f) {
        setCaption("Product Facillty Detail");

        tabbedPanePrice = new javax.swing.JTabbedPane();

        productPriceEditTablePanel = new ProductFacilityEditTablePanel(ControllerOptions.getSession());
        catalogTreeSelPanel = new ProductSelectionPanelFrame(new ControllerOptions());

        PriceButtonPanel buttonPanel = new PriceButtonPanel();
        f.getPanelDetail().setLayout(new BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, tabbedPanePrice);
        tabbedPanePrice.add("Product Selection", catalogTreeSelPanel);
        tabbedPanePrice.add("Product Facility List", productPriceEditTablePanel);
        tabbedPanePrice.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 1) {
                        if (catalogTreeSelPanel.isSelectionChanged()) {
                            addToPriceEditTablePanel();
                            catalogTreeSelPanel.setSelectionChanged(false);
                        }
                    }
                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        productPriceEditTablePanel.facilityModel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) productPriceEditTablePanel.facilityModel.selectionModel;//listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    //currentfacility = productPriceEditTablePanel.facilityModel.getSelectedItem();                    
                }
            }
        });

        buttonPanel.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    productPriceEditTablePanel.saveItem();
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.getReloadButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (productPriceEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productPriceEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                addToPriceEditTablePanel();
            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (productPriceEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productPriceEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                f.okButtonPressed();

            }
        });

        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (productPriceEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productPriceEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                f.cancelButtonPressed();
            }
        });

        buttonPanel.getClearSelectionButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (productPriceEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productPriceEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                addToPriceEditTablePanel();
            }
        });

    }

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (treePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(tmpCategoryId);
            m_productsArray.setProductLoaded(false);
            m_productsArray.setLazyLoad(true);
            m_productsArray.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
            treePanel.getTree().setModel(productTreeModel);
        }
    }

    public void loadScreenDialog() {

        ThreePanelContainerDlg f = new ThreePanelContainerDlg(new javax.swing.JFrame(), true);
        f.setTitle("Product Edit");
        loadScreen(f);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        f.setVisible(true);
    }

    public void addItem(String id) throws Exception {
        productPriceEditTablePanel.addItem(id);
    }

    public void refreshScreen() {
        productPriceEditTablePanel.refreshScreen();
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        panel.addItem(id, price, qty);
    }

    @Override
    protected void productTreeMouseClicked(java.awt.event.MouseEvent evt) {
        JTree jTree = treePanel.getTree();
        TreePath tp = null;
        tp = jTree.getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                node.tp = tp;
                node.tp = null;
            }
        }
    }

    //refresh combos
    private ListAdapterListModel<ProductFacility> selectedProductsPrices = new ListAdapterListModel<ProductFacility>();

    protected void addToPriceEditTablePanel() {

        selectedProductsPrices.clear();
        if (currentfacility != productPriceEditTablePanel.facilityModel.getSelectedItem()) {
            currentfacility = productPriceEditTablePanel.facilityModel.getSelectedItem();
            for (Product prod : catalogTreeSelPanel.getModel().getList()) {
                List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ProductFacility", UtilMisc.toMap("productId", prod.getproductId(), "facilityId", currentfacility.getfacilityId()), "productId", ControllerOptions.getSession().getDelegator());
                if (resultList != null && !resultList.isEmpty()) {
                    for (GenericValue genVal : resultList) {
                        selectedProductsPrices.add(new ProductFacility(genVal));
                    }
                } else {
                    ProductFacility prodFac = new ProductFacility();
                    prodFac.setProductId(prod.getproductId());
                    prodFac.setFacilityId(currentfacility.getfacilityId());
                    selectedProductsPrices.add(prodFac);
                }
            }
        }

//        loadProductPriceList(selectedProductsPrices);
        productPriceEditTablePanel.setProductList(catalogTreeSelPanel.getModel());
        productPriceEditTablePanel.setProductPriceList(selectedProductsPrices);
        productPriceEditTablePanel.loadItem();
    }

    public void loadProductPriceList(ListAdapterListModel<ProductPriceBulkUpdate> selectedProducts) {

        for (ProductPriceBulkUpdate prodBulk : selectedProducts.getList()) {

            try {
//                    PosProductHelper.LoadPriceList(pojo, ControllerOptions.getSession().getDelegator());
                List<ProductPrice> productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_DEFAULTPRICE, ControllerOptions.getSession().getProductStoreGroupId(), ControllerOptions.getSession());

                prodBulk.setDefaultPrice(productPriceList);

                productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_LISTPRICE, ControllerOptions.getSession().getProductStoreGroupId(), ControllerOptions.getSession());
                prodBulk.setListPrice(productPriceList);

                productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_AVERAGECOST, ControllerOptions.getSession().getProductStoreGroupId(), ControllerOptions.getSession());
                prodBulk.setAvgCost(productPriceList);

            } catch (GenericEntityException ex) {
                Logger.getLogger(ProductFacilityMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

//menu actions
    static public class ProductFacilityAction extends ScreenAction {

        final String nameStr = "Product Facility Settings";
        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";

        public ProductFacilityAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
            super(ActionType.INVENTORY_ITEM_LIST_ACTION, session, desktopPane);
            this.setName(nameStr);
            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        public ProductFacilityAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
            super(ActionType.INVENTORY_ITEM_LIST_ACTION, name, session, desktopPane);
            this.setName(nameStr);
            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductFacilityMaintainTreeController findOrderListMain = new ProductFacilityMaintainTreeController(new ControllerOptions());
            findOrderListMain.loadTwoPanelInternalFrame(module, desktopPane);
        }

        @Override
        public Action getAction() {
            return this;
        }
    }
}
