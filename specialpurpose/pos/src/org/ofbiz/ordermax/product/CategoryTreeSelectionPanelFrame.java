/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.catalog.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadProductWorker;
import mvc.model.list.JListBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ProductListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseButtonPanel;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import static org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module;
import static org.ofbiz.ordermax.product.TreeExpandedRestorer.SELECTION_BY_ROW;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;

/**
 *
 * @author siranjeev
 */
public class CategoryTreeSelectionPanelFrame extends javax.swing.JPanel implements SimpleScreenInterface {

    /**
     * Creates new form CategoryProductManagePanel
     */
    public ProductSelectionTreePanel productSelectionTreePanel = null;
    CatalogSelectionPanel catalogTreeSelPanel = null;
//    private ProductPanelInterface productDetail = null;
    String currentTreeCatRootId = null;
    ControllerOptions options = null;

    //protected javax.swing.JDesktopPane desktopPane = null;
    public CategoryTreeSelectionPanelFrame(ControllerOptions options) {
        initComponents();

        productSelectionTreePanel = createTreePanel();
        this.options = options;

//        ProductCompositePanel panel = new ProductCompositePanel(options, desktopPane);
//        CustomProductPanel panel = new CustomProductPanel(ControllerOptions.getSession());
//        productDetail = panel;
//        panelProductTree.setLayout(new BorderLayout());
//        panelProductTree.add(BorderLayout.CENTER, panel);
        catalogTreeSelPanel = new CatalogSelectionPanel(options);
        catalogTreeSelPanel.hideButtons(false);
        panelProductTree.setLayout(new BorderLayout());
        panelProductTree.add(BorderLayout.CENTER, catalogTreeSelPanel);

        catalogTreeSelPanel.panelTree.setLayout(new BorderLayout());
        catalogTreeSelPanel.panelTree.add(BorderLayout.CENTER, productSelectionTreePanel);
        catalogTreeSelPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (catalogTreeSelPanel.getSelectedCategory() != null) {
//                    String categoryId = catalogTreeSelPanel.getSelectedCategory().getproductCategoryId();
                    reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());

                } else {

                }
            }
        });
        catalogTreeSelPanel.setSelectedCatalog(ControllerOptions.getSession().getProdCatalog().getprodCatalogId());
    }

    public ProductComposite getProductComposite() {
        return null;//productDetail.getProductComposite();
    }

    public void setProductComposite(ProductComposite productComposite) {
        //  productDetail.setProductComposite(productComposite);
    }

    public void clearDialogFields() {
        //    productDetail.clearDialogFields();
    }

    public void getDialogField() {
        //      productDetail.getDialogField();
    }

    public void setDialogField() {
//        productDetail.setDialogField();
    }

    private void getProductCategoryPanel(TreeNode node, TreeNode childNode) {

        final ProductCategoryPanel productCategoryPanel = new ProductCategoryPanel(ControllerOptions.getSession());
//                prodCatalogPanel.newItem();                
        final org.ofbiz.ordermax.base.PanelContainerDlg dialog = new org.ofbiz.ordermax.base.PanelContainerDlg(new javax.swing.JFrame(), true);

        dialog.getPanelDetail().setLayout(new BorderLayout());
        dialog.getPanelDetail().add(BorderLayout.CENTER, productCategoryPanel);

        BaseButtonPanel buttonPanel = new BaseButtonPanel();
        dialog.getPanelButton().setLayout(new BorderLayout());
        dialog.getPanelButton().add(BorderLayout.CENTER, buttonPanel);

        //new item
        if (childNode == null) {
            productCategoryPanel.newItem();
            String productCategoryId = node._id;
            productCategoryPanel.primaryParentCategoryIdTextField.setText(productCategoryId);
            productCategoryPanel.productCategoryTypeIdTextField.setText("CATALOG_CATEGORY");

        } else {
            productCategoryPanel.setItem(childNode);
        }

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    productCategoryPanel.saveItem();
                    String brandId = productCategoryPanel.productCategoryIdTextField.getText();
                    String categoryId = productCategoryPanel.primaryParentCategoryIdTextField.getText();
                    GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, ControllerOptions.getSession().getDelegator());

                    if (brandCategory != null) {

                        GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, categoryId, ControllerOptions.getSession().getDelegator());
                        if (rollUp == null) {
                            rollUp = PosProductHelper.createProductCategoryRollup(brandId, categoryId, ControllerOptions.getSession().getDelegator());
                            ControllerOptions.getSession().getDelegator().create(rollUp);
                        }
                    }

                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(productSelectionTreePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
//                    reloadTreePanel(categoryId);
                    ter.restore(SELECTION_BY_ROW);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dialog.okButtonPressed();

                    String brandId = productCategoryPanel.productCategoryIdTextField.getText();
                    String categoryId = productCategoryPanel.primaryParentCategoryIdTextField.getText();
                    GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, ControllerOptions.getSession().getDelegator());

                    if (brandCategory != null) {

                        GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, categoryId, ControllerOptions.getSession().getDelegator());
                        if (rollUp == null) {
                            rollUp = PosProductHelper.createProductCategoryRollup(brandId, categoryId, ControllerOptions.getSession().getDelegator());
                            ControllerOptions.getSession().getDelegator().create(rollUp);
                        }
                    }

                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(productSelectionTreePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
//                    reloadTreePanel(categoryId);
                    ter.restore(SELECTION_BY_ROW);

                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
        });

        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dialog.cancelButtonPressed();

            }
        });

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
            }
        });
        dialog.setVisible(true);

    }

    public void reloadTreeNode(TreeNode treeNode/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (productSelectionTreePanel != null) {

            try {
                productSelectionTreePanel.getTreeDataList().reloadTreeNode(treeNode);
            } catch (Exception ex) {
                Logger.getLogger(CategoryTreeSelectionPanelFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

//            productSelectionTreePanel.loadTree();
            ProductTreeModel productTreeModel = new ProductTreeModel(productSelectionTreePanel.getTreeDataList().getRootNode());
            productSelectionTreePanel.getTree().setModel(productTreeModel);
        }

    }

    public void addProductTreeNode(TreeNode treeNode, TreeNode product) {
        //        treePanel 
        if (productSelectionTreePanel != null) {

            try {
                productSelectionTreePanel.getTreeDataList().addProductTreeNode(treeNode, product);
            } catch (Exception ex) {
                Logger.getLogger(CategoryTreeSelectionPanelFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            ProductTreeModel productTreeModel = new ProductTreeModel(productSelectionTreePanel.getTreeDataList().getRootNode());
            productSelectionTreePanel.getTree().setModel(productTreeModel);
        }

    }

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (productSelectionTreePanel != null) {

            ProductDataTreeLoader catalogCategoryDataTree = new CatalogCategoryDataTree(tmpCategoryId);
            productSelectionTreePanel.setProductListArray(catalogCategoryDataTree);
            catalogCategoryDataTree.setProductLoaded(false);
            catalogCategoryDataTree.loadList();

//            productSelectionTreePanel.loadTree();
            ProductTreeModel productTreeModel = new ProductTreeModel(catalogCategoryDataTree.getRootNode());
            productSelectionTreePanel.getTree().setModel(productTreeModel);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        panelSelecton = new javax.swing.JPanel();
        panelProductTree = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        panelSelecton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setMinimumSize(new java.awt.Dimension(300, 100));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 200));
        panelSelecton.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelProductTreeLayout = new javax.swing.GroupLayout(panelProductTree);
        panelProductTree.setLayout(panelProductTreeLayout);
        panelProductTreeLayout.setHorizontalGroup(
            panelProductTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 739, Short.MAX_VALUE)
        );
        panelProductTreeLayout.setVerticalGroup(
            panelProductTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        panelSelecton.add(panelProductTree, java.awt.BorderLayout.CENTER);

        jPanel3.add(panelSelecton);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(743, 40));

        btnOk.setText("Ok");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(559, Short.MAX_VALUE)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    void copyItem(Product prodNode, TreeNode catNode) {
        try {
            GenericValue catMember = PosProductHelper.getProductCategoryMember(prodNode.getproductId(), catNode._id, ControllerOptions.getSession().getDelegator());
            if (catMember == null) {
                ControllerOptions.getSession().getDelegator().create(PosProductHelper.createProductCategoryMember(prodNode.getproductId(), catNode._id, ControllerOptions.getSession().getDelegator()));
            }

        } catch (Exception e2) {
            //Debug.logError(e2, module);
        }
    }

    void removeItem(TreeNode prodNode, TreeNode catNode) {
        try {
            GenericValue catMember = PosProductHelper.getProductCategoryMember(prodNode._id, catNode._id, ControllerOptions.getSession().getDelegator());
            if (catMember != null) {
                List itemList = new ArrayList<GenericValue>();
                itemList.add(catMember);
                ControllerOptions.getSession().getDelegator().removeAll(itemList);
            }
        } catch (Exception e2) {
            //Debug.logError(e2, module);
        }
    }
    public GenericValue selectedCategory = null;

    final protected ProductSelectionTreePanel createTreePanel() {
//        treePanel 
//        final PartySelectionTreePanel treePanel = new PartySelectionTreePanel(session);
        final ProductSelectionTreePanel treePanel = new ProductSelectionTreePanel(ControllerOptions.getSession());

/*        treePanel.getBtnAddSelected().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
//                        addItem(prodNode._id);
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
*/
        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                productTreeMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
//                int selRow = treePanel.getTree().getRowForLocation(e.getX(), e.getY());
                //              TreePath selPath = treePanel.getTree().getPathForLocation(e.getX(), e.getY());
//                if (selRow != -1) {
                if (e.getClickCount() == 1) {
                    //           mySingleClick(selRow, selPath);
                    TreeNode prodNode = treePanel.getSelectedTreeNode(ProductCategoryTreeNode.ProductCategoryTreeNodeName);
                    if (prodNode != null) {
                        String categoryId = prodNode._id;
                        selectedCategory = PosProductHelper.getProductCategory(categoryId, ControllerOptions.getSession().getDelegator());
                        Debug.logInfo("brandCategory : " + selectedCategory.getString("productCategoryId"), module);
                        /*ProductComposite comp = loadProduct(prodNode._id);
                         if (comp != null) {
                         getProductDetail().setIsEnable(true);
                         getProductDetail().setProductComposite(comp);
                         }*/
                    }
                } else if (e.getClickCount() == 2) {
                    TreeNode prodNode = treePanel.getSelectedTreeNode(ProductCategoryTreeNode.ProductCategoryTreeNodeName);
                    if (prodNode != null) {
                        String categoryId = prodNode._id;
                        selectedCategory = PosProductHelper.getProductCategory(categoryId, ControllerOptions.getSession().getDelegator());
                        Debug.logInfo("brandCategory : " + selectedCategory.getString("productCategoryId"), module);
                        /*ProductComposite comp = loadProduct(prodNode._id);
                         if (comp != null) {
                         getProductDetail().setIsEnable(true);
                         getProductDetail().setProductComposite(comp);
                         }*/
                    }
                }
            }
            //          }
        });
        return treePanel;
    }

    public void setSelectedCatalogId(String catalogId) {
        catalogTreeSelPanel.setSelectedCatalog(catalogId);
    }

    public void setSelecteCategoryId(String catagoryId) {
        catalogTreeSelPanel.setSelecteCategory(catagoryId);
    }

    public ProductComposite loadProduct(String productId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());
        if (productComposite != null) {
            //clear dialog
//            productDetail.clearDialogFields();

            //set order
//            productDetail.setProductComposite(productComposite);
            //update the dialog
//            productDetail.setDialogField();
        } else {
            OrderMaxOptionPane.showMessageDialog(null, "Product not found: " + productId, "Load Product", JOptionPane.YES_NO_OPTION);
        }
        return productComposite;
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

    private ClassLoader getClassLoader() {

        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelProductTree;
    private javax.swing.JPanel panelSelecton;
    // End of variables declaration//GEN-END:variables

    public ProductPanelInterface getProductDetail() {
        return null;//productDetail;
    }

    public void setProductDetail(ProductCompositePanel productDetail) {
        //this.productDetail = productDetail;
    }

    @Override
    public JButton getOkButton() {
        return this.btnOk;
    }

    @Override
    public JButton getCancelButton() {
        return this.btnCancel;
    }

    @Override
    public JPanel getPanel() {
        return this; //To change body of generated methods, choose Tools | Templates.
    }
}
