/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.ThreePanelContainerDlg;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.product.ProductMaintainTreeController;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.ProductSelectionPanelFrame;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author administrator
 */
public class PriceMaintainTreeController extends BaseMainScreen {

    public static final String module = PriceMaintainTreeController.class.getName();
    protected List<String> brandMap = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();
    protected ProductPriceEditTablePanel productPriceEditTablePanel = null;
    ProductSelectionPanelFrame catalogTreeSelPanel;
    javax.swing.JTabbedPane tabbedPanePrice = null;

    static public PriceMaintainTreeController runController(ControllerOptions options) {

        PriceMaintainTreeController controller = new PriceMaintainTreeController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(PriceMaintainTreeController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ProductMaintainTreeController.module, options.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options;

    public PriceMaintainTreeController(ControllerOptions options) {
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

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        setCaption("Price Detail");

        tabbedPanePrice = new javax.swing.JTabbedPane();

        productPriceEditTablePanel = new ProductPriceEditTablePanel(ControllerOptions.getSession());
        catalogTreeSelPanel = new ProductSelectionPanelFrame(new ControllerOptions());
        brandMap = FastList.newInstance();
        departmentMap = FastMap.newInstance();

        PriceButtonPanel buttonPanel = new PriceButtonPanel();
        f.getPanelDetail().setLayout(new BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, tabbedPanePrice);
        tabbedPanePrice.add("Product Selection", catalogTreeSelPanel);
        tabbedPanePrice.add("Price List", productPriceEditTablePanel);
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

        productPriceEditTablePanel.btnLoadPrices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addToPriceEditTablePanel();          
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productPriceEditTablePanel.saveItem();
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.getReloadButton().addActionListener(new ActionListener() {
            @Override
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
                //productPriceEditTablePanel.loadItem();
            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
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
            @Override
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
            @Override
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

    @Override
    public void addItem(String id) throws Exception {
        productPriceEditTablePanel.addItem(id);
    }

    @Override
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
    private ListAdapterListModel<ProductPriceBulkUpdate> selectedProductsPrices = new ListAdapterListModel<ProductPriceBulkUpdate>();

    protected void addToPriceEditTablePanel() {

        selectedProductsPrices.clear();
        for (Product prod : catalogTreeSelPanel.getModel().getList()) {
            selectedProductsPrices.add(new ProductPriceBulkUpdate(prod));
        }

        loadProductPriceList(selectedProductsPrices);

        productPriceEditTablePanel.setProductList(catalogTreeSelPanel.getModel());
        productPriceEditTablePanel.setProductPriceList(selectedProductsPrices);
        productPriceEditTablePanel.loadItem();
    }

    public void loadProductPriceList(ListAdapterListModel<ProductPriceBulkUpdate> selectedProducts) {
        
        String productStoreGroupId = "_NA_";
        if (productPriceEditTablePanel.productStoreGroupModel.getSelectedItem() != null) {
            productStoreGroupId = productPriceEditTablePanel.productStoreGroupModel.getSelectedItem().getproductStoreGroupId();             
        }        
        
        for (ProductPriceBulkUpdate prodBulk : selectedProducts.getList()) {

            try {
//                    PosProductHelper.LoadPriceList(pojo, ControllerOptions.getSession().getDelegator());
                List<ProductPrice> productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_DEFAULTPRICE, productStoreGroupId, ControllerOptions.getSession());

                prodBulk.setDefaultPrice(productPriceList);

                productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_LISTPRICE, productStoreGroupId, ControllerOptions.getSession());
                prodBulk.setListPrice(productPriceList);

                productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_AVERAGECOST, productStoreGroupId, ControllerOptions.getSession());
                prodBulk.setAvgCost(productPriceList);

            } catch (GenericEntityException ex) {
                Logger.getLogger(PriceMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
