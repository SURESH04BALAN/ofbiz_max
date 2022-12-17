/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.CatalogSelectionPanel;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.entity.GenericEntityException;
import javolution.util.FastList;
import javolution.util.FastMap;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.entity.ProductCategory;
import static org.ofbiz.ordermax.product.ProductCatalogMainScreen.module;
import static org.ofbiz.ordermax.product.TreeExpandedRestorer.SELECTION_BY_ROW;

/**
 *
 * @author siranjeev
 */
public class ProductCatalogBuildMainScreen extends BaseMainScreen {

    public static final String module = ProductCatalogBuildMainScreen.class.getName();
    static String PRODUCT_TAB_INDEX = "PRODUCT";
    static String CATALOG_CATEGORY_TAB_INDEX = "CATALOGCATEGORY";
    static String PRODUCT_CATEGORY_TAB_INDEX = "PRODUCTCATEGORY";
    static String PRODUCT_CATEGORY_LINK_TAB_INDEX = "PRODUCTCATEGORYLINK";
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    protected String visibleCardName = null;
    protected CustomProductPanel customProductPanel = null;
    protected DepartmentPanel departmentPanel = null;    
    protected BrandPanel brandPanel = null;    
    protected JPanel productCardPanel = null;
    protected ProdCatalogPanel prodCatalogPanel = null;
    protected ProductCategoryPanel productCategoryPanel = null;
    protected ProductCategoryLinkForm productCategoryLinkPanel = null;
    protected CategoryProductButtonPanel buttonPanel = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();
    protected List<String> brandMap = null;

    static public ProductCatalogBuildMainScreen runController(ControllerOptions options) {

        ProductCatalogBuildMainScreen controller = new ProductCatalogBuildMainScreen(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(ProductCatalogBuildMainScreen.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(ProductCatalogBuildMainScreen.module, options.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options = null;
    protected ProductCatalogBuildMainScreen( ControllerOptions options) {
        super( ControllerOptions.getSession());
        this.options = options;
    }

    @Override
    public void loadScreen(final ContainerPanelInterface f) {

        setCaption("Product Detail");
        buttonPanel = new CategoryProductButtonPanel();
        customProductPanel = new CustomProductPanel(ControllerOptions.getSession());
        createTreePanel();
        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
        m_productsArray.loadList();
        final CatalogSelectionPanel catalogTreeSelPanel = new CatalogSelectionPanel(options);

        catalogTreeSelPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (catalogTreeSelPanel.getSelectedCategory() != null) {
                    reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
                } else {

                }
            }
        });

        catalogTreeSelPanel.btnUpdateCatalog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prodCatalogPanel.newItem();
            }
        });


        catalogTreeSelPanel.btnUpdateCatalog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //prodCatalogPanel.newItem();
                comboPostalChanged(CATALOG_CATEGORY_TAB_INDEX);
            }
        });

//        f.getPanelSelecton().setSize(200, 700);
        OrderMaxUtility.addAPanelToBorder(treePanel.getContainerPanel(), catalogTreeSelPanel.panelTree);
        OrderMaxUtility.addAPanelToBorder(catalogTreeSelPanel, f.getPanelSelecton());

        productCardPanel = new JPanel(new CardLayout());

        customProductPanel.addChangeListener(this);

        customProductPanel.btnFixImagePath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //prodCatalogPanel.newItem();
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                System.out.println("Current relative path is: " + s);

//            localPath = "/images/categories" + "/" + field;
                ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();
                List<Key> list = m_productsArray.getAllProducts();
                for (Key key : list) {
                    TreeNode node = (TreeNode) key;
                    String prodId = node._id;
                    String validExt = "jpg";
                    boolean found = false;
                    boolean dirFound = false;
                    String dirPath = "/images/products/" + prodId + "/";
                    if (BaseHelper.isDirExists(dirPath)) {
                        dirFound = true;
                        String filePath = dirPath + "small.";
                        if (BaseHelper.isFileExists(filePath + "jpg")) {
                            found = true;
                        } else if (BaseHelper.isFileExists(filePath + "jpeg")) {
                            found = true;
                            validExt = "jpeg";
                        } else if (BaseHelper.isFileExists(filePath + "png")) {
                            found = true;
                            validExt = "png";

                        } else if (BaseHelper.isFileExists(filePath + "jpg&h=90&zc=1")) {
                            found = true;
                            validExt = "jpg";

                            File oldfile = new File(filePath + "jpg&h=90&zc=1");
                            File newfile = new File(filePath + "jpg");

                            System.out.println("old file " + oldfile.getName());
                            System.out.println("new file " + newfile.getName());
                            if (oldfile.renameTo(newfile)) {
                                System.out.println("Rename succesful");
                            } else {
                                System.out.println("Rename failed");
                            }
                        }
                    }
//            isNew = false;
                    try {
                        GenericValue product = node.loadDetails(prodId, ControllerOptions.getSession().getDelegator());
                        if (found == true) {
                            String pathFile = dirPath + "small." + validExt;
                            product.set("smallImageUrl", pathFile);
                            Debug.logInfo("pathFile: " + pathFile, module);
                            product.set("mediumImageUrl", dirPath + "small-60." + validExt);
                            product.set("largeImageUrl", dirPath + "large." + validExt);
                            product.set("originalImageUrl", dirPath + "original." + validExt);
                            product.set("detailImageUrl", dirPath + "large." + validExt);
                            Debug.logInfo("/images/products/" + prodId + "/original.jpg", module);
                        } else {
                            if (dirFound == false) {
                                product.set("smallImageUrl", null);
                                product.set("mediumImageUrl", null);
                                product.set("largeImageUrl", null);
                                product.set("originalImageUrl", null);
                                product.set("detailImageUrl", null);
                            }

                        }
//                        product.set("detailImageUrl", "/images/products/" + prodId + "/large-200.jpg");
                        ControllerOptions.getSession().getDelegator().store(product);

                    } catch (GenericEntityException ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
        });

        prodCatalogPanel = new ProdCatalogPanel(ControllerOptions.getSession());
        productCategoryPanel = new ProductCategoryPanel(ControllerOptions.getSession());
        productCategoryLinkPanel = new ProductCategoryLinkForm(ControllerOptions.getSession());
        //brandPanel = new BrandPanel(ControllerOptions.getSession());
        productCardPanel.add(prodCatalogPanel, CATALOG_CATEGORY_TAB_INDEX);
        productCardPanel.add(productCategoryPanel, PRODUCT_CATEGORY_TAB_INDEX);
        productCardPanel.add(customProductPanel, PRODUCT_TAB_INDEX);
        productCardPanel.add(productCategoryLinkPanel, PRODUCT_CATEGORY_LINK_TAB_INDEX);

        customProductPanel.btnReloadTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

//                    reloadTreePanel(categoryId);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        OrderMaxUtility.addAPanelToBorder(productCardPanel, f.getPanelDetail());
//        OrderMaxUtility.addAPanelToBorder(treePanel.getContainerPanel(), f.getPanelSelecton());
        OrderMaxUtility.addAPanelToBorder(buttonPanel, f.getPanelButton());

        comboPostalChanged(PRODUCT_CATEGORY_TAB_INDEX);

        brandMap = FastList.newInstance();
//        eventLoadBrand();

        departmentMap = FastMap.newInstance();
//        Map departmentValMap = FastMap.newInstance();

        HashMap<String, Key> departmentMap1 = m_productsArray.getAllTopCategories();
        Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
        Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
        for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
            departmentMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
            try {
                treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
            } catch (Exception e) {
//                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
//                Debug.logError(e, module);
            }
        }

        customProductPanel.m_productsArray = m_productsArray;
        customProductPanel.setDepartmentMap(departmentMap);

        //sorted by id list
        for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
            departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
        }

        ArrayList<Key> brandList = m_productsArray.getAllBrands();
        //sort
        Map<String, Key> brandTreeMap = new TreeMap<String, Key>();
        for (Key brand : brandList) {
            if (brand != null && brand._name != null && brand._name.isEmpty() == false) {
                brandTreeMap.put(brand._name, brand);
            } else {
                brand._name = brand._id;
                brandTreeMap.put(brand._name, brand);
                Debug.logInfo("brand name is empty. Band id: " + brand._id, module);
            }
        }

        brandList.clear();
        for (Map.Entry<String, Key> entryDept : brandTreeMap.entrySet()) {
            brandList.add(entryDept.getValue());
        }

        customProductPanel.setBrandList(brandList);
        ArrayList<Key> productList = m_productsArray.getListAll();
        for (Key prod : productList) {
            String productidd = prod._id;
            customProductPanel.addToProductCodeKeyMap(productidd);
        }
        buttonPanel.btnNewProduct.getAction().actionPerformed(null);
//        productCardPanel = new JPanel(new CardLayout());
//        customProductPanel = new CustomProductPanel(ControllerOptions.getSession());
    }

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (treePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(tmpCategoryId);
            m_productsArray.setProductLoaded(false);
            m_productsArray.setLazyLoad(false);                 
            m_productsArray.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
            treePanel.getTree().setModel(productTreeModel);
//            this.setProductListArray(m_productsArray);
//            ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
            //m_productsArray.loadList();

            brandMap = FastList.newInstance();
//        eventLoadBrand();

            departmentMap = FastMap.newInstance();
//          Map departmentValMap = FastMap.newInstance();

            HashMap<String, Key> departmentMap1 = m_productsArray.getAllTopCategories();
            Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
            Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
            for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
                departmentMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
                try {
                    treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
                } catch (Exception e) {
//                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
//                Debug.logError(e, module);
                }
            }

            customProductPanel.m_productsArray = m_productsArray;
            customProductPanel.setDepartmentMap(departmentMap);

            //sorted by id list
            for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
                departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
            }

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

            customProductPanel.setBrandList(brandList);
            ArrayList<Key> productList = m_productsArray.getListAll();
            for (Key prod : productList) {
                String productidd = prod._id;
                customProductPanel.addToProductCodeKeyMap(productidd);
            }

            //ProductListArray m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
            //m_productsArray.loadList();
//            OrderMaxUtility.addAPanelToBorder(treePanel.getContainerPanel(), catalogTreeSelPanel.panelTree);
//            f.getPanelSelecton().updateUI();
        }

    }

    protected boolean isNewProduct = false;

    @Override
    protected void createTreePanel() {
        final CatalogCategorySelectionTreePanel treePanel1 = new CatalogCategorySelectionTreePanel(ControllerOptions.getSession());

        CategoryButtonPanel panel = new CategoryButtonPanel();
        OrderMaxUtility.addAPanelToBorder(panel, treePanel1.panelSelection);
//        treePanel1.panelFind.setVisible(true);

        ActionListener newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    saveBeforeChange();

                    JTree jTree = treePanel.getTree();
                    TreePath tp = null;
                    tp = jTree.getSelectionPath();
                    if (tp != null) {
                        TreeNode node = (TreeNode) tp.getLastPathComponent();
                        if (node != null) {
                            node.tp = tp;
                            if (node.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {

                                comboPostalChanged(PRODUCT_CATEGORY_TAB_INDEX);

                                File filePath = BaseHelper.getImageFilePath("Category");

                                //                File filePath = new File(categoryData.path);//BaseHelper.getImageFilePath("original");
                                String brandId = node._id;
                                String categoryImageUrl = BaseHelper.CopyCategoryImageSetFileName("category", filePath, brandId, 0, 0);
                                String linkOneImageUrl = BaseHelper.CopyCategoryImageSetFileName("linkOne", filePath, brandId, 60, 60);
                                String linkTwoImageUrl = BaseHelper.CopyCategoryImageSetFileName("linkTwo", filePath, brandId, 48, 48);
                                productCategoryPanel.categoryImageUrlTextField.setText(categoryImageUrl);
                                productCategoryPanel.linkOneImageUrlTextField.setText(categoryImageUrl);
                                productCategoryPanel.linkTwoImageUrlTextField.setText(categoryImageUrl);

                            }
                        }

                    }
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        };
        panel.btnLoadCatImage.addActionListener(newAction);

        ActionListener newProductAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveBeforeChange();
                    isNewProduct = true;
                    customProductPanel.newItem();
                    comboPostalChanged(PRODUCT_TAB_INDEX);
                    setCaption("Product Detail - New");
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        };

        panel.btnNewProduct.addActionListener(newProductAction);
        buttonPanel.btnNewProduct.addActionListener(newProductAction);

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    buttonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    customProductPanel.saveItem();
//                    if (isNewProduct) {
                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(treePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
//                    reloadTreePanel(categoryId);
                    ter.restore(SELECTION_BY_ROW);
                    //                  }

                    isNewProduct = false;
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                } finally {
                    buttonPanel.setCursor(Cursor.getDefaultCursor());
                }
            }

        };

        panel.btnSaveProduct.addActionListener(newAction);
        buttonPanel.btnSaveProduct.addActionListener(newAction);

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Debug.logInfo("Delete product: ", module);
                    customProductPanel.deleteItem();
                    //                   reloadTreePanel(categoryId);
//                    treePanel.findFromId(customProductPanel.editProductId.getText());
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        };
        panel.btnDeleteProduct.addActionListener(newAction);
        buttonPanel.btnDeleteProduct.addActionListener(newAction);

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Debug.logInfo("reload product: ", module);
                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(treePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
                    saveBeforeChange();
//                    reloadTreePanel(categoryId);
                    ter.restore(SELECTION_BY_ROW);
//                    treePanel.findFromId(customProductPanel.editProductId.getText());
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        };
        panel.btnReloadTree.addActionListener(newAction);
        buttonPanel.btnReloadTree.addActionListener(newAction);
        
        panel.btnNewLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveBeforeChange();
                    productCategoryLinkPanel.newItem();
                    productCategoryLinkPanel.setParentItem(null);
                    comboPostalChanged(PRODUCT_CATEGORY_TAB_INDEX);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        panel.btnLinkProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveBeforeChange();
                    comboPostalChanged(PRODUCT_TAB_INDEX);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        panel.btnDeleteLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Debug.logInfo("reload product: ", module);
                    comboPostalChanged(PRODUCT_CATEGORY_LINK_TAB_INDEX);
                    productCategoryLinkPanel.deleteItem();

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        panel.btnSaveLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Debug.logInfo("reload product: ", module);
                    productCategoryLinkPanel.saveItem();
                    comboPostalChanged(PRODUCT_CATEGORY_LINK_TAB_INDEX);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        panel.btnShowLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    saveBeforeChange();

                    Debug.logInfo("reload product: ", module);
                    JTree jTree = treePanel.getTree();
                    TreePath tp = null;
                    tp = jTree.getSelectionPath();
                    if (tp != null) {
                        TreeNode node = (TreeNode) tp.getLastPathComponent();

                        TreePath parentPath = tp.getParentPath();
                        String categoryId = null;
                        if (parentPath != null) {
                            TreeNode currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
                            categoryId = currTreeParentNode._id;
                            Debug.logInfo(currTreeParentNode._id, module);
                        }

                        if (node != null && node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {

                            GenericValue catMember = PosProductHelper.getProductCategoryMember(node._id, categoryId, ControllerOptions.getSession().getDelegator());
                            Debug.logInfo("product id: " + node._id, module);
                            if (catMember != null) {

                                productCategoryLinkPanel.changeUIObject(productCategoryLinkPanel.createUIObject(catMember));
                                productCategoryLinkPanel.setUIFields();
                                productCategoryLinkPanel.setParentItem(catMember);
//                                                            Debug.logInfo("product id: " id, module);
                            } else {
                                Debug.logInfo("Category member not found", module);
                            }

                            //if (catMember == null) {
                            //    delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, prodPojo.brandId, delegator));
                            //}
                            comboPostalChanged(PRODUCT_CATEGORY_LINK_TAB_INDEX);
                        }

                    }

                } catch (Exception ex) {
                    Debug.logError(ex, module);

                }

            }
        });

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                productCategoryPanel.newItem();
                JTree jTree = treePanel.getTree();
                TreePath tp = null;
                tp = jTree.getSelectionPath();
                if (tp != null) {
                    TreeNode node = (TreeNode) tp.getLastPathComponent();
                    if (node != null) {
                        node.tp = tp;
                        if (node.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {

                            String productCategoryId = node._id;
                            productCategoryPanel.primaryParentCategoryIdTextField.setText(productCategoryId);
                            productCategoryPanel.productCategoryTypeIdTextField.setText("CATALOG_CATEGORY");
                        }
                    }
                }
                comboPostalChanged(PRODUCT_CATEGORY_TAB_INDEX);
            }
        };

        panel.btnNewCategory.addActionListener(newAction);
        buttonPanel.btnNewCategory.addActionListener(newAction);

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    productCategoryPanel.saveItem();
                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(treePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
//                    reloadTreePanel(categoryId);
                    ter.restore(SELECTION_BY_ROW);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        };

        panel.btnSaveCategory.addActionListener(newAction);
        buttonPanel.btnSaveCategory.addActionListener(newAction);

        newAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    JTree jTree = treePanel.getTree();
                    TreePath tp = null;
                    tp = jTree.getSelectionPath();
                    if (tp != null) {
                        TreeNode node = (TreeNode) tp.getLastPathComponent();
                        if (node != null) {
                            node.tp = tp;
                            if (node.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {

                                String productCategoryId = node._id;

                                GenericValue productCategoryEntity = PosProductHelper.getProductCategory(productCategoryId, ControllerOptions.getSession().getDelegator());
                                if (productCategoryEntity != null) {

                                    String primaryParentCategoryId = productCategoryEntity.getString("primaryParentCategoryId");

                                    GenericValue rollUp = PosProductHelper.getProductCategoryRollup(productCategoryId, primaryParentCategoryId, ControllerOptions.getSession().getDelegator());
                                    if (rollUp != null) {
                                        ControllerOptions.getSession().getDelegator().removeValue(rollUp);
                                    }
                                    ControllerOptions.getSession().getDelegator().removeValue(productCategoryEntity);
                                }

                            }

                        }
                    }

                } catch (GenericEntityException ex) {
                    Debug.logError(ex, module);
                }
            }
        };
        panel.btnDeleteCategory.addActionListener(newAction);
        buttonPanel.btnDeleteCategory.addActionListener(newAction);

        buttonPanel.okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hidePanel();
            }
        });
//        treePanel1.panelFind.add(panel,  java.awt.BorderLayout.CENTER);//.setVisible(false);        
        treePanel = treePanel1;
/*
        treePanel1.getBtnAddSelected()
                .addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                        if (prodNode != null) {
                            try {
//                                addItem(prodNode._id, treePanel1.getPrice(), treePanel1.getQuantity());
                                refreshScreen();

                            } catch (Exception ex) {
                                Logger.getLogger(OrderMaxMainForm.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
*/
        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Debug.logInfo("Current Selection: " + " mouseClicked", module);
                productTreeMouseClicked(evt);
            }
        });

        treePanel.getTree().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                if (treePanel.getTree().getLastSelectedPathComponent() != null) {
                    productTreeMouseClicked(null);
                }
            }
        });

        customProductPanel.btnRunReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                    if (prodNode != null) {
                        try {
                            ArrayList selList = new ArrayList<String>();
                            selList.add(prodNode._id);
                            customProductPanel.runReport(selList, ControllerOptions.getSession().getDelegator());

                        } catch (Exception ex) {
                            Logger.getLogger(OrderMaxMainForm.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

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
                if (node.getNodeName().equals(ProductCategoryTreeNode.ProductCategoryTreeNodeName)) {
                    try {
                        saveBeforeChange();
                        productCategoryPanel.setItem(node);
                        comboPostalChanged(PRODUCT_CATEGORY_TAB_INDEX);
                    } catch (Exception ex) {
                        Logger.getLogger(ProductCatalogBuildMainScreen.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    try {
                        saveBeforeChange();
//                        customProductPanel.setItem(node);
                        TreePath parentPath = tp.getParentPath();
                        if (parentPath != null) {
                            TreeNode currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
                            Debug.logInfo(currTreeParentNode._id, module);
                            if (currTreeParentNode instanceof ProductCategoryTreeNode) {
                                //GenericValue value = currTreeParentNode.loadDetails(currTreeParentNode._id, ControllerOptions.getSession().getDelegator());
                                if (currTreeParentNode._id != null) {
                                    int pos = currTreeParentNode._id.indexOf("_");

                                    if (pos > 0) {
                                        String depId = currTreeParentNode._id.substring(0, pos);
                                        if (departmentMap.containsKey(depId)) {
                                            ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();
                                            ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(depId, departmentMap.get(depId)));
                                            customProductPanel.setBrandList(brandList);
                                        }

                                        Debug.logInfo(currTreeParentNode._id.substring(0, pos), module);
                                        Debug.logInfo(currTreeParentNode._id.substring(pos + 1), module);
                                        customProductPanel.setParentCatId(currTreeParentNode._id.substring(0, pos));
                                        customProductPanel.setBrandCatId(currTreeParentNode._id);
                                    }
                                }
                            }
                        }

                        comboPostalChanged(PRODUCT_TAB_INDEX);
                    } catch (Exception ex) {
                        Logger.getLogger(ProductCatalogBuildMainScreen.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                    try {
                        saveBeforeChange();
                        departmentPanel.setItem(node);
                        comboPostalChanged(CATALOG_CATEGORY_TAB_INDEX);

                    } catch (Exception ex) {
                        Logger.getLogger(ProductCatalogBuildMainScreen.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                }
                node.tp = null;
            }
        }
    }

    public void comboPostalChanged(String desc) {
        if (productCardPanel != null) {
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            visibleCardName = desc;
            if (visibleCardName.equals(PRODUCT_TAB_INDEX)) {
//                baseMainPanelInterface = customProductPanel;
            } else if (visibleCardName.equals(CATALOG_CATEGORY_TAB_INDEX)) {
                baseMainPanelInterface = departmentPanel;
            } else if (visibleCardName.equals(PRODUCT_CATEGORY_TAB_INDEX)) {
                baseMainPanelInterface = brandPanel;
            }
        }

    }

    public void saveBeforeChange() throws Exception {

        String message = "Do you want to save?";
        if (customProductPanel.isModified) {
            int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                customProductPanel.saveItem();
            }
            customProductPanel.setIsModified(false);
        } else if (prodCatalogPanel.isModified()) {
            int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                prodCatalogPanel.saveItem();
            }
            prodCatalogPanel.setIsModified(false);
        } else if (productCategoryPanel.isModified()) {
            int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                productCategoryPanel.saveItem();
            }
            productCategoryPanel.setIsModified(false);

        } else if (productCategoryLinkPanel.isModified()) {
            int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                productCategoryLinkPanel.saveItem();
            }
            productCategoryLinkPanel.setIsModified(false);
        }

    }
    
    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }    
}
