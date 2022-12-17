/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ThreePanelContainerDlg;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.entity.ProductCategory;
import static org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.CATALOG_CATEGORY_TAB_INDEX;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.CatalogSelectionPanel;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;

/**
 *
 * @author siranjeev
 */
public class ProductCatalogMainScreen extends BaseMainScreen {

    public static final String module = ProductCatalogMainScreen.class.getName();
//    public BaseMainPanelInterface panel = null;
    static String PRODUCT_TAB_INDEX = "PRODUCT";
    static String DEPARTMENT_TAB_INDEX = "DEPARTMENT";
    private String BRAND_TAB_INDEX = "BRAND";
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    protected String visibleCardName = null;
    protected CustomProductPanel customProductPanel = null;
    protected DepartmentPanel departmentPanel = null;
    protected BrandPanel brandPanel = null;
    protected JPanel productCardPanel = null;
    protected List<String> brandMap = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();

    ControllerOptions options = null;

    static public ProductCatalogMainScreen runController(ControllerOptions options) {

        ProductCatalogMainScreen controller = new ProductCatalogMainScreen(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(ProductCatalogMainScreen.module, options.getDesktopPane(), null);
        } else {
            controller.loadThreePanelInternalFrame(ProductCatalogMainScreen.module, options.getDesktopPane());
        }
        return controller;
    }

    protected ProductCategory categoryId = ControllerOptions.getSession().getProductCategory();

    protected ProductCatalogMainScreen(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
    }

    public void loadScreen(final ContainerPanelInterface f) {
        setCaption("Product Detail");
       //createTreePanel();
       //ProductListArray m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
       //m_productsArray.loadList();
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
//                prodCatalogPanel.newItem();
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
        customProductPanel = new CustomProductPanel(ControllerOptions.getSession());
        departmentPanel = new DepartmentPanel(m_productsArray.getRootNode(), ControllerOptions.getSession());
        brandPanel = new BrandPanel(ControllerOptions.getSession());

        productCardPanel.add(departmentPanel, DEPARTMENT_TAB_INDEX);
        productCardPanel.add(brandPanel, BRAND_TAB_INDEX);
        productCardPanel.add(customProductPanel, PRODUCT_TAB_INDEX);
        customProductPanel.addChangeListener(this);

        brandMap = FastList.newInstance();
//        eventLoadBrand();

        departmentMap = FastMap.newInstance();
        Map departmentValMap = FastMap.newInstance();

        HashMap<String, Key> departmentMap1 = m_productsArray.getAllTopCategories();
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
        customProductPanel.m_productsArray = m_productsArray;
        customProductPanel.setDepartmentMap(departmentValMap);
        departmentPanel.setDepartmentMap(departmentValMap);
        brandPanel.setDepartmentMap(departmentValMap);

        //sorted by id list
        for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
            departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
        }
        departmentPanel.reloadItemDataModel(departmentList);

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

        brandPanel.reloadBrandItemDataModel(brandList);
        customProductPanel.setBrandList(brandList);
        ArrayList<Key> productList = m_productsArray.getListAll();
        for (Key prod : productList) {
            String productidd = prod._id;
            customProductPanel.addToProductCodeKeyMap(productidd);
        }

        customProductPanel.getButtonFind().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String scanCode = customProductPanel.getEditScanCode();
                    if (scanCode != null && scanCode.isEmpty() == false) {
                        GenericValue scanVal = PosProductHelper.getProductFromScanCode(scanCode, PosProductHelper.GoodIdentificationTypeIdEAN, ControllerOptions.getSession().getDelegator());

                        if (scanVal != null) {

                            String productId = scanVal.getString("productId");
                            String name = scanVal.getString("internalName");
                            String message = "Product with Scan Code[" + scanCode + "] is" + name + ". Do you want to display the product now?";
                            int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                try {
                                    treePanel.findFromId(productId);
                                    TreeNode prodNode = treePanel.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);

                                    if (prodNode != null) {
                                        try {
                                            TreeNode departNode = treePanel.getSelectedTreeNode(DepartmentTreeNode.DepartmentNodeName);
                                            ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                                            ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
                                            customProductPanel.setBrandList(brandList);

                                            baseMainPanelInterface.setItem(prodNode);
//                                            refreshScreen();
                                        } catch (Exception ex) {
                                            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }

                                } catch (Exception ex) {
                                    Debug.logError(ex, module);
                                }

                            } else if (reply == JOptionPane.CANCEL_OPTION) {
                                return;
                            }

                            //				}
                        } else {
                            String message = "Product with Scan Code[" + scanCode + "] doesn't exists.";
                            OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        }
                    }
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

//                loadProductTree();
            }
        });

        ProductButtonPanel buttonPanel = new ProductButtonPanel();

        OrderMaxUtility.addAPanelToBorder(productCardPanel, f.getPanelDetail());
        OrderMaxUtility.addAPanelToBorder(treePanel.getContainerPanel(), f.getPanelSelecton());
        OrderMaxUtility.addAPanelToBorder(buttonPanel, f.getPanelButton());

        comboPostalChanged(DEPARTMENT_TAB_INDEX);

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    baseMainPanelInterface.saveItem();
                    loadProductTree();
                    treePanel.findFromId(orderId);
                    customProductPanel.setIsModified(false);
                    departmentPanel.setIsModified(false);
                    brandPanel.setIsModified(false);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

//                loadProductTree();
            }
        });

        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.getBtnLoad().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                loadProductTree();
                treePanel.findFromId(orderId);
//                baseMainPanelInterface.refreshScreen();
                //orderId

                customProductPanel.setIsModified(false);
                departmentPanel.setIsModified(false);
                brandPanel.setIsModified(false);
            }
        });

        buttonPanel.getBtnNewProduct().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (baseMainPanelInterface.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            baseMainPanelInterface.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

//                comboPostalChanged(PRODUCT_TAB_INDEX);
                TreeNode departNode = treePanel.getSelectedTreeNode(DepartmentTreeNode.DepartmentNodeName);
//                TreeNode departNode = treePanel.getSelectedParentTreeNode();
                if (departNode != null) {
                    ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                    ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
                    customProductPanel.setBrandList(brandList);

                    comboPostalChanged(PRODUCT_TAB_INDEX);
                    baseMainPanelInterface.newItem();
                    baseMainPanelInterface.setParentItem(departNode);
                    TreeNode parentNode = treePanel.getSelectedTreeNode(BrandTreeNode.BrandNodeName);
                    if (parentNode != null) {
                        baseMainPanelInterface.setParentItem(parentNode);
                    }

                }

            }
        });

        buttonPanel.getBtnNewDepartment().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (baseMainPanelInterface.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            baseMainPanelInterface.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                comboPostalChanged(DEPARTMENT_TAB_INDEX);
                baseMainPanelInterface.newItem();
            }
        });

        buttonPanel.getBtnNewBrandName().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (baseMainPanelInterface.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            baseMainPanelInterface.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                TreeNode departNode = treePanel.getSelectedTreeNode(DepartmentTreeNode.DepartmentNodeName);
//                TreeNode departNode = treePanel.getSelectedParentTreeNode();
                if (departNode != null) {
                    ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                    ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
                    brandPanel.reloadBrandItemDataModel(brandList);
                    customProductPanel.setBrandList(brandList);

                    comboPostalChanged(BRAND_TAB_INDEX);
                    baseMainPanelInterface.newItem();
                    baseMainPanelInterface.setParentItem(departNode);
                }
            }
        });

    }

    public void loadScreenDialog() {

        ThreePanelContainerDlg f = new ThreePanelContainerDlg(new javax.swing.JFrame(), true);
        f.setTitle("Product Edit");
        loadScreen(f);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 700);
//        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        f.setVisible(true);

    }

    public void addItem(String id) throws Exception {
        baseMainPanelInterface.addItem(id);
    }

    public void refreshScreen() {
        baseMainPanelInterface.refreshScreen();
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
                if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception ex) {
                                Debug.logError(ex, module);
                            }

                        } else {
                            baseMainPanelInterface.setIsModified(false);
                        }
                    }
                    TreeNode departNode = treePanel.getSelectedTreeNode(DepartmentTreeNode.DepartmentNodeName);
                    ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                    ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
                    customProductPanel.setBrandList(brandList);
//                    customProductPanel.setItem(node);
                    comboPostalChanged(PRODUCT_TAB_INDEX);
                } else if (node.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception ex) {
                                Debug.logError(ex, module);
                            }

                        } else {
                            baseMainPanelInterface.setIsModified(false);
                        }
                    }

                    TreeNode departNode = treePanel.getSelectedParentTreeNode();
                    if (departNode != null) {
                        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                        ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
                        brandPanel.reloadBrandItemDataModel(brandList);
                        customProductPanel.setBrandList(brandList);
                        brandPanel.setItem(node);
                        comboPostalChanged(BRAND_TAB_INDEX);
                    }

                } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception ex) {
                                Debug.logError(ex, module);
                            }

                        } else {
                            baseMainPanelInterface.setIsModified(false);
                        }
                    }
                    departmentPanel.setItem(node);
                    comboPostalChanged(DEPARTMENT_TAB_INDEX);
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
            /*if (visibleCardName.equals(PRODUCT_TAB_INDEX)) {
                baseMainPanelInterface = customProductPanel;
            } else if (visibleCardName.equals(DEPARTMENT_TAB_INDEX)) {
                baseMainPanelInterface = departmentPanel;
            } else if (visibleCardName.equals(BRAND_TAB_INDEX)) {
                baseMainPanelInterface = brandPanel;
            }*/
        }
    }

    public void eventLoadBrand() {
        // if (wasMouseClicked()) {
        BufferedReader CSVFile;

        try {
            CSVFile = new BufferedReader(new FileReader(
                    "C:\\AuthLog\\brand.csv"));

            String dataRow;
            try {
                // List departmentList = FastList.newInstance();
                dataRow = CSVFile.readLine();
                brandMap.clear();

                while (dataRow != null) {
                    String[] dataArray = dataRow.split(",");
                    // int index = 0;
                    for (String item : dataArray) {
                        brandMap.add(item);
                        // index++;;
                        System.out.print(item + ",");
                        break;
                    }

                    System.out.println(); // Print the data line.
                    dataRow = CSVFile.readLine(); // Read next line of data.
                }

                // Close the file once all data has been read.
                CSVFile.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Read the first line of data.

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void eventLoadDepartment() {
        // if (wasMouseClicked()) {
        BufferedReader CSVFile;

        try {
            CSVFile = new BufferedReader(new FileReader(
                    "C:\\AuthLog\\department.csv"));

            String dataRow;
            try {
                // List departmentList = FastList.newInstance();
                dataRow = CSVFile.readLine();
                departmentMap.clear();

                // The while checks to see if the data is null. If it is, we've
                // hit
                // the end of the file. If not, process the data.
                while (dataRow != null) {
                    String[] dataArray = dataRow.split(",");
                    // int index = 0;
                    if (dataArray.length > 1) {
                        // for (String item:dataArray) {
                        departmentMap.put(dataArray[0], dataArray[1]);
                        // index++;;
                        System.out.print(dataArray[0] + "," + dataArray[1]);

                    }

                    System.out.println(); // Print the data line.
                    dataRow = CSVFile.readLine(); // Read next line of data.
                }

                // Close the file once all data has been read.
                CSVFile.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Read the first line of data.

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // }
    }

    protected void loadProductTree() {

        TreePath path = treePanel.getTree().getSelectionPath();

        treePanel.loadTree();
        brandMap = FastList.newInstance();
//        eventLoadBrand();

        /*      DefaultComboBoxModel comboBrandModel = new DefaultComboBoxModel();
         for (String entry : brandMap) {
         comboBrandModel.addElement(entry);
         }
         comboBrand.setModel(comboBrandModel);
         */
        departmentMap = FastMap.newInstance();

        Map departmentValMap = FastMap.newInstance();

        //      DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
        //      DefaultComboBoxModel comboDepartmentBrandModel = new DefaultComboBoxModel();
        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());

        HashMap<String, Key> departmentMap1 = m_productsArray.getAllTopCategories();
        Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
        Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
        for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
            departmentValMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
//            comboDepartmentBrandModel.addElement(entryDept.getValue()._name);
//            departmentListBidingCombo.add(entryDept.getValue()._id);
            try {
                treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
            } catch (Exception e) {
//                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
//                Debug.logError(e, module);
            }
        }
//        customProductPanel = new ProductPanel();
//        departmentPanel = new DepartmentPanel();

        customProductPanel.setDepartmentMap(departmentValMap);
        departmentPanel.setDepartmentMap(departmentValMap);
        brandPanel.setDepartmentMap(departmentValMap);

        //comboDepartment.setModel(comboDepartmentBrandModel);
        //comboProductDepName.setModel(comboDepartmentModel);
        //sorted by id list
        departmentList.clear();
        for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
            departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
        }
        departmentPanel.reloadItemDataModel(departmentList);

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

        brandPanel.reloadBrandItemDataModel(brandList);
        customProductPanel.setBrandList(brandList);
        ArrayList<Key> productList = m_productsArray.getListAll();
        for (Key prod : productList) {
            String productidd = prod._id;
            customProductPanel.addToProductCodeKeyMap(productidd);
        }

        treePanel.getTree().scrollPathToVisible(path);
    }

        protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (treePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader productCategoryDataTree = new CatalogCategoryDataTree(tmpCategoryId);
            productCategoryDataTree.setProductLoaded(false);
            productCategoryDataTree.setLazyLoad(true);
            productCategoryDataTree.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(productCategoryDataTree.getRootNode());
            treePanel.getTree().setModel(productTreeModel);
            ((ProductSelectionTreePanel)treePanel).setProductListArray(productCategoryDataTree);

        }
    }
/*        
    @Override
    protected void createTreePanel() {
        final ProductSelectionTreePanel treePanel1 = new CatalogCategorySelectionTreePanel(ControllerOptions.getSession());
        ProductDataTreeLoader m_productsArray  = new ProductDataTreeLoader(ControllerOptions.getSession());
        treePanel1.setProductListArray(m_productsArray);
//        CatalogCategoryDataTree();
//        treePanel1.loadTree();
        treePanel = treePanel1;



        treePanel1.getBtnAddSelected().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        addItem(prodNode._id, treePanel1.getPrice(), treePanel1.getQuantity());
                        refreshScreen();
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Debug.logInfo("Current Selection: " + " mouseClicked", module);
                productTreeMouseClicked(evt);
            }
        });
        //   treePanel.getTree().addTreeSelectionListener(this);

        treePanel.getTree().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                Debug.logInfo("Current Selection: " + " mouseClicked", module);
                if (treePanel.getTree().getLastSelectedPathComponent() != null) {
                    Debug.logInfo("Current Selection: " + treePanel.getTree().getLastSelectedPathComponent().toString(), module);

                }
            }
        });
    }
*/
        
    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
    boolean isNew = false;
    boolean isModified = false;
    String orderStatus;
    String orderId = null;
    String partyId = "";

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(OrderMaxUtility.ITEM_NEW)) {
            isNew = true;
            isModified = false;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_SAVED)) {
            isNew = false;
            isModified = false;
            orderId = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_MODIFIED)) {
            isModified = true;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_STATUS_CHANGED)) {
            orderId = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.PARTY_CHANGED)) {
            partyId = (String) event.getNewValue();
//            treePanel.setPartyId(partyId);
        }

        String caption = "Product - " + partyId + "[";
        if (isNew) {
            caption = caption.concat(" New ");
        } else {
            caption = caption.concat("Product Id: " + orderId);
        }

        if (orderStatus != null) {
            caption = caption.concat(", Order Status: " + orderStatus);
        }

        caption = caption.concat(" ]");

        if (isModified) {
            caption = caption.concat(" - Modified ");
        }

        setCaption(caption);
    }
}
