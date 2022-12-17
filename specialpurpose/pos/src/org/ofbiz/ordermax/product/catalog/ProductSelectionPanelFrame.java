/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JListBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ProductListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseButtonPanel;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import static org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module;
import static org.ofbiz.ordermax.product.TreeExpandedRestorer.SELECTION_BY_ROW;

import org.ofbiz.ordermax.product.ProductCategoryPanel;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.product.TreeExpandedRestorer;

import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.product.tree.ProductTreeFindFactory;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ProductSelectionPanelFrame extends javax.swing.JPanel {

    /**
     * Creates new form CategoryProductManagePanel
     */
    XuiSession session;
    public ProductSelectionTreePanel productTreePanel = null;
    CatalogSelectionPanel catalogSelectionPanel;
    protected javax.swing.JDesktopPane desktopPane = null;
    protected boolean selectionChanged = false;
    protected boolean selectVirtual = false;

    public boolean isSelectVirtual() {
        return selectVirtual;
    }

    public void setSelectVirtual(boolean selectVirtual) {
        this.selectVirtual = selectVirtual;
    }

    public boolean isSelectionChanged() {
        return selectionChanged;
    }

    public void setSelectionChanged(boolean selectionChanged) {
        this.selectionChanged = selectionChanged;
    }
//    String catId = "MS_CAT_PROMOTIONS";
/*
     private class SetupListSelectionModel<E> {

     final private ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
     private JList<E> jListBox = new JList<E>(dataListModel);
     private ListSelectionModel selectionModel = new DefaultListSelectionModel();
     private ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

     private SetupListSelectionModel(ListCellRenderer<E> render) {

     jListBox.setSelectionModel(selectionModel);
     selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     listModelSelection.setListModels(dataListModel, selectionModel);
     jListBox.setCellRenderer(render);
     jListBox.setEnabled(true);

     //            jList.setCellRenderer(render);
     }
     }
     */
    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    public JListBoxSelectionModel<Product> productListSelectionModel = null;

    public ListAdapterListModel<Product> getModel() {
        return productListSelectionModel.dataListModel;
    }

    public ProdCatalog getSelectedCatalog() {
        return catalogSelectionPanel.getSelectedCatalog();
    }

    public ProductCategory getSelectedCategory() {
        return catalogSelectionPanel.getSelectedCategory();
    }
    ControllerOptions options = null;

    JGenericComboBoxSelectionModel<String> stringsComboBoxModel = null;

    public enum ProductSelectionType {

        All,
        VariantOnly,
        VirtualOnly,
        CategoriesOnly,
    }

    public ProductSelectionPanelFrame(ControllerOptions options) {
        initComponents();
        this.options = options;
        this.session = ControllerOptions.getSession();
        this.desktopPane = ControllerOptions.getDesktopPane();

        productTreePanel = createTreePanel(ControllerOptions.getSession().getProductCategory());
        // panelProductSelType 
        List<String> valList = new ArrayList<String>();
        valList.add("All Products");
        valList.add("Variant Products Only");
        valList.add("Virtual Products Only");
        valList.add("Categories Only");

        stringsComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelProductSelType, valList, "Variant Products Only");
        stringsComboBoxModel.jComboBox.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) stringsComboBoxModel.jComboBox.getSelectedItem();//get the selected item
                //int index = stringsComboBoxModel.jComboBox.getSelectedIndex();
                //productTreeFindInterface = new PanelSupplierProductId(new ControllerOptions());
//                productTreeFindInterface = ProductTreeFindFactory.createPanel(s, options);
//                panelSelection.removeAll();
//                panelSelection.setLayout(new BorderLayout());
//                panelSelection.add(BorderLayout.CENTER, productTreeFindInterface.getPanel());
            }
        });
        //jPanel1.setLayout(new BorderLayout());
        //jPanel1.add(BorderLayout.CENTER, productTreePanel);
//        DefaultListModel prevMovel = new DefaultListModel();
        //      jlistTreeSelect.setModel(prevMovel);
        productListSelectionModel = new JListBoxSelectionModel<Product>(new ProductListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(productListSelectionModel.jlistBox);
        panelJlist.setLayout(new BorderLayout());
        panelJlist.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelSelecton, "Product Soutrce");
        ComponentBorder.loweredBevelBorder(panelDetail, "Selection");

        productListSelectionModel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) productListSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setProductSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        catalogSelectionPanel = new CatalogSelectionPanel(options);

//        panelDetail.setLayout(new BorderLayout());
//        panelDetail.add(BorderLayout.CENTER, catalogSelectionPanel);
        catalogSelectionPanel.panelTree.setLayout(new BorderLayout());
        catalogSelectionPanel.panelTree.add(BorderLayout.CENTER, productTreePanel);
        /*        try {
         ProdCatalog prodCat = ProdCatalogSingleton.getProdCatalog(ProductDataTreeLoader.CatalogId);
         //        if (catalogSelectionPanel.prodCatalogComboModel.comboBoxModel.getSize() > 0) {
         catalogSelectionPanel.prodCatalogComboModel.comboBoxModel.setSelectedItem(prodCat);//.jComboBox.setSelectedIndex(0);
         //        }
         } catch (Exception ex) {
         Logger.getLogger(ProductSelectionPanelFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
         */

        /*
         catalogSelectionPanel.seleProdCatalogCategoryComboModel.selectionModel.addListSelectionListener(
         new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent listSelectionEvent) {
         ListSelectionModel lsm = (ListSelectionModel) productListSelectionModel.selectionModel;
         if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
         // Find out which indexes are selected.
         int minIndex = lsm.getMinSelectionIndex();
         int maxIndex = lsm.getMaxSelectionIndex();
         for (int i = minIndex; i <= maxIndex; i++) {
         if (lsm.isSelectedIndex(i)) {
         System.out.println(" " + i);

         String categoryId = catalogSelectionPanel.seleProdCatalogCategoryComboModel.listModelSelection.getSelection().getproductCategoryId();
         reloadTreePanel(categoryId);
         break;
         }
         }
         }
         }
         });
         */
        catalogSelectionPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (catalogSelectionPanel.getSelectedCategory() != null) {
//                    String categoryId = catalogSelectionPanel.getSelectedCategory().getproductCategoryId();
                    reloadTreePanel(catalogSelectionPanel.getSelectedCategory());
                } else {

                }
            }
        });
        /*
         catalogSelectionPanel.btnNewCategory.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {

         JTree jTree = productTreePanel.getTree();
         TreePath tp = null;
         tp = jTree.getSelectionPath();
         if (tp != null) {
         TreeNode node = (TreeNode) tp.getLastPathComponent();
         if (node != null) {

         if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
         node = productTreePanel.getParentNode(node);
         }

         if (node != null) {
         node.tp = tp;
         getProductCategoryPanel(node, null);//                            }
         }
         }
         }
         }

         });

         catalogSelectionPanel.btnSaveCategory.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         //                    prodCatalogPanel.saveItem();
         //                    catalogSelectionPanel.loadCatalogs();

         } catch (Exception ex) {
         Debug.logError(ex, module);
         }
         }
         });
         */
        /*
         catalogSelectionPanel.btnUpdateCategory.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         JTree jTree = productTreePanel.getTree();
         TreePath tp = null;
         tp = jTree.getSelectionPath();
         if (tp != null) {
         TreeNode node = (TreeNode) tp.getLastPathComponent();
         if (node != null) {

         if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
         node = productTreePanel.getParentNode(node);
         }

         if (node != null) {
         node.tp = tp;
         getProductCategoryPanel(null, node);//                            }
         }
         }
         }
                
         }
                
         });*/

        panelTreeSource.setLayout(new BorderLayout());
        panelTreeSource.add(BorderLayout.CENTER, catalogSelectionPanel);
        catalogSelectionPanel.setSelectedCatalog(ControllerOptions.getSession().getProdCatalog().getprodCatalogId());
//        catalogSelectionPanel.setSelecteCategory(ProductDataTreeLoader.categoryRootId);

    }

    ProductSelectionType getSelectedProductSelectionType() {
        ProductSelectionType val = ProductSelectionType.All;

        String s = (String) stringsComboBoxModel.jComboBox.getSelectedItem();//get the selected item     
        if ("Variant Products Only".equals(s)) {
            val = ProductSelectionType.VariantOnly;
        } else if ("Virtual Products Only".equals(s)) {
            val = ProductSelectionType.VirtualOnly;
        } else if ("Categories Only".equals(s)) {
            val = ProductSelectionType.CategoriesOnly;
        } else {
            val = ProductSelectionType.All;
        }

        return val;
    }

    void setProductSelection(int i) {

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
        jSplitPane1 = new javax.swing.JSplitPane();
        panelSelecton = new javax.swing.JPanel();
        panelTreeSource = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        panelJlist = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnAddAll = new javax.swing.JButton();
        btnRemoveAll = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        panelProductSelType = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setPreferredSize(new java.awt.Dimension(1000, 297));

        panelSelecton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setMinimumSize(new java.awt.Dimension(300, 100));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 200));
        panelSelecton.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelTreeSourceLayout = new javax.swing.GroupLayout(panelTreeSource);
        panelTreeSource.setLayout(panelTreeSourceLayout);
        panelTreeSourceLayout.setHorizontalGroup(
            panelTreeSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        panelTreeSourceLayout.setVerticalGroup(
            panelTreeSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        panelSelecton.add(panelTreeSource, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panelSelecton);

        panelDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDetail.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelJlistLayout = new javax.swing.GroupLayout(panelJlist);
        panelJlist.setLayout(panelJlistLayout);
        panelJlistLayout.setHorizontalGroup(
            panelJlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );
        panelJlistLayout.setVerticalGroup(
            panelJlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
        );

        panelDetail.add(panelJlist, java.awt.BorderLayout.CENTER);

        jPanel5.setPreferredSize(new java.awt.Dimension(432, 50));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnAddAll.setText("Add All");
        btnAddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAllActionPerformed(evt);
            }
        });

        btnRemoveAll.setText("Remove All");
        btnRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductSelTypeLayout = new javax.swing.GroupLayout(panelProductSelType);
        panelProductSelType.setLayout(panelProductSelTypeLayout);
        panelProductSelTypeLayout.setHorizontalGroup(
            panelProductSelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        panelProductSelTypeLayout.setVerticalGroup(
            panelProductSelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(panelProductSelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveAll)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdd, btnAddAll, btnRemove, btnRemoveAll});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRemove)
                        .addComponent(btnRemoveAll))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnAddAll))
                    .addComponent(panelProductSelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDetail.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setRightComponent(panelDetail);

        jPanel3.add(jSplitPane1);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        ProductSelectionType selType = getSelectedProductSelectionType();
        if (!selType.equals(ProductSelectionType.CategoriesOnly)) {
            TreeNode prodNode = productTreePanel.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);            
            if (prodNode != null) {
                try {
                    if (selType.equals(ProductSelectionType.All)) {
                        addItem(prodNode);
                    } else {
                        if (prodNode instanceof ProductTreeNode) {

                            ProductTreeNode prod = (ProductTreeNode) prodNode;
                            if (selType.compareTo(ProductSelectionType.VariantOnly) == 0 && prod.isVariant()) {
                                addItem(prodNode);
                            } else if (selType.compareTo(ProductSelectionType.VirtualOnly) == 0 && prod.isVirtual()) {
                                addItem(prodNode);
                            } else {
                                Debug.log("Not found: " + selType.toString() + " prod Type variant: " + prod.isVariant() + " " + " prod Type virtual: " + prod.isVirtual());
                            }
                        }
                    }
                    // addItem(prodNode);
                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            TreeNode prodNode = productTreePanel.getSelectedTreeNode(ProductCategoryTreeNode.ProductCategoryTreeNodeName);            
            if (prodNode != null) {
                
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAllActionPerformed

        ProductSelectionType selType = getSelectedProductSelectionType();
        List<TreeNode> selList = productTreePanel.getAllSelectedTreeNode(ProductTreeNode.ProdutNodeName);
        if (selList != null) {
            try {

                TreePath tp = productTreePanel.getTree().getSelectionPath();
                TreeNode catNode = null;
                if (tp != null) {
                    TreePath parentPath = tp.getParentPath();
                    if (parentPath != null) {
                        catNode = (TreeNode) parentPath.getLastPathComponent();
                    }
                }

                for (TreeNode prodNode : selList) {
                    if (catNode != null) {
                        prodNode.setParentCatId(catNode._id);
                    }

                    if (selType.equals(ProductSelectionType.All)) {
                        addItem(prodNode);
                    } else {
                        if (prodNode instanceof ProductTreeNode) {

                            ProductTreeNode prod = (ProductTreeNode) prodNode;
                            if (selType.equals(ProductSelectionType.VariantOnly) && prod.isVariant()) {
                                addItem(prodNode);
                            } else if (selType.equals(ProductSelectionType.VirtualOnly) && prod.isVirtual()) {
                                addItem(prodNode);
                            }
                        }
                    }

                }
                //notify 
                ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
            } catch (Exception ex) {
                Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAddAllActionPerformed

    private void btnRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllActionPerformed
//        DefaultListModel prevMovel = (DefaultListModel) jlistTreeSelect.getModel();        // TODO add your handling code here:
//        prevMovel.removeAllElements();
//        jlistTreeSelect.setModel(prevMovel);
        productListSelectionModel.dataListModel.clear();
        selectionChanged = true;
    }//GEN-LAST:event_btnRemoveAllActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed

        // Find out which indexes are selected.
        int minIndex = productListSelectionModel.selectionModel.getMinSelectionIndex();
        int maxIndex = productListSelectionModel.selectionModel.getMaxSelectionIndex();
        List<Product> list = new ArrayList<Product>();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (productListSelectionModel.selectionModel.isSelectedIndex(i)) {
                System.out.println(" " + i);
                list.add(productListSelectionModel.dataListModel.getElementAt(i));
            }
        }

        for (Product obj : list) {
            productListSelectionModel.dataListModel.remove(obj);
            selectionChanged = true;
        }

        if (productListSelectionModel.dataListModel.getSize() > minIndex) {
            productListSelectionModel.selectionModel.setLeadSelectionIndex(minIndex);
        } else {
            if (productListSelectionModel.dataListModel.getSize() != 0 && minIndex > 0) {
                productListSelectionModel.selectionModel.setLeadSelectionIndex(minIndex - 1);
            }
        }


    }//GEN-LAST:event_btnRemoveActionPerformed

    private void getProductCategoryPanel(TreeNode node, TreeNode childNode) {

        final ProductCategoryPanel productCategoryPanel = new ProductCategoryPanel(session);
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
                    GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, session.getDelegator());

                    if (brandCategory != null) {

                        GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, categoryId, session.getDelegator());
                        if (rollUp == null) {
                            rollUp = PosProductHelper.createProductCategoryRollup(brandId, categoryId, session.getDelegator());
                            session.getDelegator().create(rollUp);
                        }
                    }

                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
                    if (catalogSelectionPanel.getSelectedCategory() != null) {
//                        String comboCategoryId = catalogSelectionPanel.getSelectedCategory().getproductCategoryId();
                        reloadTreePanel(catalogSelectionPanel.getSelectedCategory());
                    }
//                    reloadTreePanel();
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
                    GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, session.getDelegator());

                    if (brandCategory != null) {

                        GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, categoryId, session.getDelegator());
                        if (rollUp == null) {
                            rollUp = PosProductHelper.createProductCategoryRollup(brandId, categoryId, session.getDelegator());
                            session.getDelegator().create(rollUp);
                        }
                    }

                    final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
                    ter.save();
//                    reloadTreePanel();
                    if (catalogSelectionPanel.getSelectedCategory() != null) {
//                        String comboCategoryId = catalogSelectionPanel.getSelectedCategory().getproductCategoryId();
                        reloadTreePanel(catalogSelectionPanel.getSelectedCategory());
                    }

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

    protected ProductSelectionTreePanel createTreePanel(ProductCategory id) {
//        treePanel 
//        final PartySelectionTreePanel treePanel = new PartySelectionTreePanel(session);
        final ProductSelectionTreePanel treePanel = new ProductSelectionTreePanel(session);

        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                productTreeMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
                int selRow = treePanel.getTree().getRowForLocation(e.getX(), e.getY());
                TreePath selPath = treePanel.getTree().getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {
                        //           mySingleClick(selRow, selPath);
                    } else if (e.getClickCount() == 2) {
                        TreeNode prodNode = treePanel.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                        try {
//                            panel.addItem(prodNode._id,BigDecimal.ONE,BigDecimal.ONE);
                            //         myDoubleClick(selRow, selPath);
/*                            int row = panel.getLastEmptyRow();
                             AudioRecord orderItem = panel.addProductById(prodNode._id, row);
                             OrderItemMax orderMaxItem = panel.addItem(orderItem.getproductId(), orderItem.getLastPrice(), BigDecimal.ONE);
                             orderItem.oim = orderMaxItem;

                             panel.getTable().changeSelection(row, OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                             panel.getLastEmptyRow();
                             */
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }
                    }
                }
            }
        });
        return treePanel;
    }

    protected void reloadTreePanel(ProductCategory tmpCategoryId) {
        //        treePanel 
        if (productTreePanel != null) {
            if (UtilValidate.isNotEmpty(tmpCategoryId)) {
                //                  createTreePanel();
                ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(tmpCategoryId);
                m_productsArray.setProductLoaded(false);
                m_productsArray.loadList();

                ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
                productTreePanel.getTree().setModel(productTreeModel);
                productTreePanel.setProductListArray(m_productsArray);
            } else {

                ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(tmpCategoryId);
                m_productsArray.setProductLoaded(false);
                m_productsArray.loadList();

                ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
                productTreePanel.getTree().setModel(productTreeModel);
                productTreePanel.setProductListArray(m_productsArray);

            }
        }
    }

    /*    final protected void reloadTreePanel() {
     //        treePanel 
     if (productTreePanel != null) {
     //                  createTreePanel();
     ProductDataTreeLoader m_productsArray = new ProductCategoryDataTree(session);
     m_productsArray.setProductLoaded(false);
     m_productsArray.loadList();

     ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
     productTreePanel.getTree().setModel(productTreeModel);
     }

     }
     */
    void addItem(TreeNode prodNode) {
        try {
            Debug.logInfo("add product node: " + prodNode._id, module);
            //        final DefaultListModel model = new DefaultListModel();
            Product prod = ProductSingleton.getProduct(prodNode._id);

            if (productListSelectionModel.dataListModel.indexOf(prod) > -1) {
                return;
            } else {
                productListSelectionModel.dataListModel.add(prod);
                selectionChanged = true;
            }
            /*
             DefaultListModel prevMovel = (DefaultListModel) jlistTreeSelect.getModel();
             for (int i = 0; i < prevMovel.getSize(); i++) {
             TreeNode node = (TreeNode) prevMovel.getElementAt(i);
             if (node._id.equalsIgnoreCase(prodNode._id)) {
             return;
             }
             }
            
             prevMovel.addElement(prodNode);
             */
        } catch (Exception ex) {
            Logger.getLogger(ProductSelectionPanelFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddAll;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveAll;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelJlist;
    private javax.swing.JPanel panelProductSelType;
    private javax.swing.JPanel panelSelecton;
    private javax.swing.JPanel panelTreeSource;
    // End of variables declaration//GEN-END:variables
}
