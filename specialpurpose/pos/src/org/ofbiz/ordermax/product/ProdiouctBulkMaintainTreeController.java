/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.price.*;
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
public class ProdiouctBulkMaintainTreeController extends BaseMainScreen {

    public static final String module = ProdiouctBulkMaintainTreeController.class.getName();
    //protected BaseMainPanelInterface productPriceEditTablePanel = null;
    protected List<String> brandMap = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();
    protected ProductPriceEditTablePanel productPriceEditTablePanel = null;
//    protected PriceTreeMap priceTreeMap = null;
//    CatalogSelectionPanel catalogTreeSelPanel;
    ProductSelectionPanelFrame catalogTreeSelPanel;
    javax.swing.JTabbedPane tabbedPanePrice = null;
    
    static public ProdiouctBulkMaintainTreeController runController(ControllerOptions options) {

        ProdiouctBulkMaintainTreeController controller = new ProdiouctBulkMaintainTreeController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ProdiouctBulkMaintainTreeController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ProductMaintainTreeController.module, options.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options;
    public ProdiouctBulkMaintainTreeController(ControllerOptions options) {
        super( ControllerOptions.getSession());
        this.options = options;
//        priceTreeMap = new PriceTreeMap(ControllerOptions.getSession());
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
        setCaption("Price Detail");

//        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
//        m_productsArray.loadList();
        tabbedPanePrice = new javax.swing.JTabbedPane();

        productPriceEditTablePanel = new ProductPriceEditTablePanel(ControllerOptions.getSession());
//        productPriceEditTablePanel = productPriceEditTablePanel;

        brandMap = FastList.newInstance();
        departmentMap = FastMap.newInstance();
        Map departmentValMap = FastMap.newInstance();
//        createTreePanel();
        catalogTreeSelPanel = new ProductSelectionPanelFrame(new ControllerOptions());
        /*        catalogTreeSelPanel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         TreeNode treeNode = catalogTreeSelPanel.productTreePanel.getSelectedTreeNode();
         if (treeNode != null) {
         if (treeNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
         TreeNode brandNode = catalogTreeSelPanel.productTreePanel.getSelectedParentTreeNode();
         if (brandNode != null) {
         Debug.logError(" Brand Node: " + brandNode._key, module);
         ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());

         //TreeNode deptNode = m_productsArray.findParentTreeNode(m_productsArray.getRootNode(),brandNode);
         TreeNode deptNode = catalogTreeSelPanel.productTreePanel.getParentNode(brandNode);
         if (deptNode != null) {
         Debug.logError(" Dept Node: " + deptNode._key, module);
         priceTreeMap.addAProductTreeNode(deptNode, brandNode, treeNode);
         }
         }
         } else if (treeNode.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
         TreeNode deptNode = catalogTreeSelPanel.productTreePanel.getSelectedParentTreeNode();
         if (deptNode != null) {
         priceTreeMap.addABrandTreeNode(deptNode, treeNode);
         }
         } else if (treeNode.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
         priceTreeMap.addDepartTreeNodes(treeNode);
         } else if (treeNode.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
         //treeNode.
         List<TreeNode> list = treePanel.getAllChildTreeNode();
         for (TreeNode childTreeNode : list) {
         priceTreeMap.addDepartTreeNodes(childTreeNode);
         }
         } else if (treeNode.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {
         //                        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();      
         //                        TreeNode deptNode = treePanel.getSelectedParentTreeNode();
         priceTreeMap.addCategoryTreeNodes(treeNode);
         }

         addToPriceEditTablePanel();
         }
         }
         });
         */
//        if (catalogTreeSelPanel.getSelectedCategory() != null) {
//                    String categoryId = catalogTreeSelPanel.getSelectedCategory().getproductCategoryId();
//            reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
//        }
/*        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) catalogTreeSelPanel.productTreePanel.getTreeDataList();
         HashMap<String, Key> departmentMap1 = m_productsArray.getAllDepartments();
         Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
         Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
         for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
         departmentValMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
         try {
         treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
         } catch (Exception e) {
         //                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
         //                Debug.logError(e, module);
         }
         }

         //        productPriceEditTablePanel.setDepartmentMap(departmentValMap);
         //sorted by id list
         for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
         departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
         }
         //         productPriceEditTablePanel.reloadItemDataModel(departmentList);

         ArrayList<Key> brandList = m_productsArray.getAllBrands();

         //sort
         Map<String, Key> brandTreeMap = new TreeMap<String, Key>();
         for (Key brand : brandList) {
         brandTreeMap.put(brand._name, brand);
         }

         brandList.clear();
         for (Map.Entry<String, Key> entryDept : brandTreeMap.entrySet()) {
         brandList.add(entryDept.getValue());
         }
         */
//        catalogTreeSelPanel.hideButtons(false);
//        f.getPanelSelecton().setLayout(new BorderLayout());
//        f.getPanelSelecton().add(BorderLayout.CENTER, catalogTreeSelPanel);

        /*        catalogTreeSelPanel.panelTree.setLayout(new BorderLayout());
         catalogTreeSelPanel.panelTree.add(BorderLayout.CENTER, treePanel.getContainerPanel());
         catalogTreeSelPanel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         if (catalogTreeSelPanel.getSelectedCategory() != null) {
         String categoryId = catalogTreeSelPanel.getSelectedCategory().getproductCategoryId();
         reloadTreePanel(categoryId);
         } else {

         }
         }
         });
         */
//        catalogTreeSelPanel.setSelectedCatalog(ProductDataTreeLoader.CatalogId);
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

//        OrderMaxUtility.addAPanelGrid(productPriceEditTablePanel, );
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), f.getPanelSelecton());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

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
                //productPriceEditTablePanel.loadItem();
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

//                priceTreeMap = new PriceTreeMap(ControllerOptions.getSession());
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
    /*  public void loadScreen() {

     ThreePanelContainerFrame f = new ThreePanelContainerFrame(module);
     f.setTitle("Product Edit");
     loadScreen(f);
     f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     f.setSize(1000, 700);
     f.setLocationRelativeTo(null);
     //        f.textField = panel.getPartyTextField();
     f.setVisible(true);
     f.toFront();
     }
     */

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
    /*
     @Override
     protected void createTreePanel() {
     //        treePanel 
     final ProductSelectionTreePanel treePanel1 = new ProductSelectionTreePanel(ControllerOptions.getSession());
     treePanel = treePanel1;

     treePanel1.getBtnAddSelected().addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
     TreeNode treeNode = treePanel1.getSelectedTreeNode();
     if (treeNode != null) {
     if (treeNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
     TreeNode brandNode = treePanel1.getSelectedParentTreeNode();
     if (brandNode != null) {
     Debug.logError(" Brand Node: " + brandNode._key, module);
     ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());

     //TreeNode deptNode = m_productsArray.findParentTreeNode(m_productsArray.getRootNode(),brandNode);
     TreeNode deptNode = treePanel1.getParentNode(brandNode);
     if (deptNode != null) {
     Debug.logError(" Dept Node: " + deptNode._key, module);
     priceTreeMap.addAProductTreeNode(deptNode, brandNode, treeNode);
     }
     }
     } else if (treeNode.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
     TreeNode deptNode = treePanel1.getSelectedParentTreeNode();
     if (deptNode != null) {
     priceTreeMap.addABrandTreeNode(deptNode, treeNode);
     }
     } else if (treeNode.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
     priceTreeMap.addDepartTreeNodes(treeNode);
     } else if (treeNode.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
     //treeNode.
     List<TreeNode> list = treePanel.getAllChildTreeNode();
     for (TreeNode childTreeNode : list) {
     priceTreeMap.addDepartTreeNodes(childTreeNode);
     }
     } else if (treeNode.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {
     //                        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();      
     //                        TreeNode deptNode = treePanel.getSelectedParentTreeNode();
     priceTreeMap.addCategoryTreeNodes(treeNode);
     }

     addToPriceEditTablePanel();
     }
     }
     });

     treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseClicked(java.awt.event.MouseEvent evt) {
     productTreeMouseClicked(evt);
     }
     });
     treePanel1.setPricePanelVisible(true);
     }
     */

    //refresh combos
    private ListAdapterListModel<ProductPriceBulkUpdate> selectedProductsPrices = new ListAdapterListModel<ProductPriceBulkUpdate>();

    protected void addToPriceEditTablePanel() {
        selectedProductsPrices.clear();
        for (Product prod : catalogTreeSelPanel.getModel().getList()) {
            selectedProductsPrices.add(new ProductPriceBulkUpdate(prod));
        }
        /*
         departmentMap = FastMap.newInstance();
         Map<String, String> brandMap = new HashMap<String, String>();
         Map<String, String> prodMap = new HashMap<String, String>();
         Map departmentValMap = FastMap.newInstance();

         //ProductListArray m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();
         HashMap<String, Key> departmentMap1 = priceTreeMap.getAllDepartments();
         Debug.logInfo(" departmentMap1 SIZE [" + departmentMap1.size(), module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
         Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
         for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
         departmentValMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
         HashMap<String, String> bmap = priceTreeMap.getBrandIdMap(entryDept.getValue()._id, entryDept.getValue()._name);
         brandMap.putAll(bmap);

         //            for (Map.Entry<String, String> entryBrand : bmap.entrySet()) {
         HashMap<String, String> pmap = priceTreeMap.getProductMap(
         entryDept.getValue()._id, entryDept.getValue()._name,
         AllString, AllString);
         prodMap.putAll(pmap);
         //           }

         }
         */
        loadProductPriceList(selectedProductsPrices);

//        productPriceEditTablePanel.setBrandMap(brandMap);
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
                Logger.getLogger(ProdiouctBulkMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
