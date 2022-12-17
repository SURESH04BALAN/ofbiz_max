/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.ofbiz.ordermax.base.TreeSelectionInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.screens.HighlightTreeCellRenderer;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.product.product.ProductWorker;

/**
 *
 * @author siranjeev
 */
public class ProductSelectionTreePanel extends javax.swing.JPanel implements TreeSelectionInterface {

    static final String module = ProductSelectionTreePanel.class.getName();
    protected ProductDataTreeLoader productListArray = null;
    protected Delegator delegator = null;
    protected XuiSession session;
    private final HighlightTreeCellRenderer renderer = new HighlightTreeCellRenderer();
    protected TreeNode rootNode = null;
    protected ProductCategory categoryId = null;
    protected ProductTreeFindInterface productTreeFindInterface = null;
    ControllerOptions options = new ControllerOptions();
    protected LookupActionListner lookupActionListner = null;

    /**
     * Creates new form ProductSelectionTreePanel
     */
    public ProductSelectionTreePanel(XuiSession sessionVal/*, ProductDataTreeLoader prodListArray*/) {
        initComponents();

        this.session = sessionVal;
        this.delegator = sessionVal.getDelegator();
        setupPanel();

//        productTreeFindInterface = new PanelSupplierProductId(new ControllerOptions());
//        panelSelection.removeAll();
//        panelSelection.setLayout(new BorderLayout());
//        panelSelection.add(BorderLayout.CENTER, productTreeFindInterface.getPanel());
    }

    public ProductDataTreeLoader getTreeDataList() {
        return productListArray;
    }
    boolean pricePanelVisible = true;

    public void setPricePanelVisible(boolean val) {
        /* if (val) {
         jpanelPrice.setPreferredSize(new java.awt.Dimension(400, 104));
         panelFindContainer.setPreferredSize(new java.awt.Dimension(400, 200));
         } else {
         jpanelPrice.setPreferredSize(new java.awt.Dimension(400, 0));
         panelFindContainer.setPreferredSize(new java.awt.Dimension(400, 100));
         }*/
        pricePanelVisible = val;
    }

    public void setFindPanelVisible(boolean val) {
        if (val) {
            setPricePanelVisible(pricePanelVisible);
        } else {
            panelFindContainer.setPreferredSize(new java.awt.Dimension(400, 0));
        }
    }

    public void setProductListArray(ProductDataTreeLoader prodListArray) {
        this.productListArray = prodListArray;
        rootNode = productListArray.getRootNode();
    }

    public TreeNode getRootNode() {
        return rootNode;
    }
    ProductTreeModel productTreeModel = null;

    @Override
    public void loadTree() {

        if (productListArray != null) {
            rootNode = productListArray.getRootNode();
            productTreeModel = new ProductTreeModel(rootNode);
            tree.setModel(productTreeModel);
            for (int i = 0; i < 2; i++) {
                if (tree.getRowCount() > i) {
                    tree.expandRow(i);
                }
            }
        }
    }
    public JGenericComboBoxSelectionModel<String> stringsComboBoxModel = null;

    @Override
    final public void setupPanel() {
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        /*List<String> valList = new ArrayList<String>();
        HashMap<String, ProductTreeFindPanelFactoryInterface> values = ProductTreeFindFactory.getRegisteredTreeFindPanels();
        for (Entry<String, ProductTreeFindPanelFactoryInterface> value : values.entrySet()) {
            valList.add(value.getKey());
        }

        stringsComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelSelectionCombo, valList);
        stringsComboBoxModel.jComboBox.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) stringsComboBoxModel.jComboBox.getSelectedItem();//get the selected item
                //int index = stringsComboBoxModel.jComboBox.getSelectedIndex();
                //productTreeFindInterface = new PanelSupplierProductId(new ControllerOptions());
                productTreeFindInterface = ProductTreeFindFactory.createPanel(s, options);
                panelSelection.removeAll();
                panelSelection.setLayout(new BorderLayout());
                panelSelection.add(BorderLayout.CENTER, productTreeFindInterface.getPanel());

            }
        });
        
        */
        //jPanel2.setLayout(new BorderLayout());
        //jPanel2.add(BorderLayout.CENTER, productPriceTypeComboModel.jComboBox);
        //loadTree();

        /*editFindId.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) {
         if (rbDescription.isSelected()) {
         fireDocumentChangeEvent();
         }
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
         if (rbDescription.isSelected()) {
         fireDocumentChangeEvent();
         }

         }

         @Override
         public void changedUpdate(DocumentEvent e) {
         }
         });*/
        lookupActionListner = new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, options);
        lookupActionListner.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> listStr = lookupActionListner.getResultIds();

                //String tmpproductId = editFindId.getText();
                for (String val : listStr) {
                    final String productId = val;
                    if (!SwingUtilities.isEventDispatchThread()) {

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {

                                // save the changes
                                findProduct(productId);
                            }
                        });
                    } else {

                        // save the changes
                        findProduct(productId);
                    }
                }
                
                        Debug.logError("no thread true", "Module");                
            }
        });
        btnFindProduct.addActionListener(lookupActionListner);

        //CatalogProductTreeCellRenderer renderer = new CatalogProductTreeCellRenderer();
        //productTreeModel = new ProductTreeModel();
        tree.setCellRenderer(renderer);
//        renderer.setQ(editFindId.getText());
//        fireDocumentChangeEvent();
//        DocumentFilter filter = new UppercaseDocumentFilter();
//        ((AbstractDocument) editFindId.getDocument()).setDocumentFilter(filter);

        setPricePanelVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgFindSelection = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        panelFindContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnFindProduct = new javax.swing.JButton();
        panelSelection = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(230, 0));
        setPreferredSize(new java.awt.Dimension(230, 551));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeMouseClicked(evt);
            }
        });
        tree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                treeTreeWillExpand(evt);
            }
        });
        tree.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                treeVetoableChange(evt);
            }
        });
        jScrollPane3.setViewportView(tree);

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        panelFindContainer.setMinimumSize(new java.awt.Dimension(747, 100));
        panelFindContainer.setPreferredSize(new java.awt.Dimension(400, 100));
        panelFindContainer.setLayout(new java.awt.BorderLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(115, 24));
        jPanel3.setPreferredSize(new java.awt.Dimension(501, 100));

        jLabel1.setText("Find By:   ");

        jPanel2.setPreferredSize(new java.awt.Dimension(350, 24));
        jPanel2.setLayout(new java.awt.BorderLayout());

        btnFindProduct.setText("Find");
        jPanel2.add(btnFindProduct, java.awt.BorderLayout.EAST);

        panelSelection.setPreferredSize(new java.awt.Dimension(350, 70));

        javax.swing.GroupLayout panelSelectionLayout = new javax.swing.GroupLayout(panelSelection);
        panelSelection.setLayout(panelSelectionLayout);
        panelSelectionLayout.setHorizontalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );
        panelSelectionLayout.setVerticalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSelection, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(panelSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelFindContainer.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(panelFindContainer, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    String partyId = "";

    public void setPartyId(String id) {
        partyId = id;
    }

    public String getPartyId() {
        return partyId;
    }

    void clearFields() {
    }

    public void findFromId(String id) {
        findProduct(id);
    }

    void findProduct(String productId) {
        try {
            TreePath root = tree.getPathForRow(0);
            GenericValue prod = ProductTreeNode.loadProductDetail(productId);
            if (prod != null) {
                String productVarId = prod.getString("productId");
                String prodVarName = prod.getString("productName");

                Key productVar = new Key(productVarId, prodVarName);
                Debug.logInfo("searchNode product Id: " + productVar, module);
                TreeNode searchNode = (TreeNode) productListArray.getProductFromId(productVar);

//            collapseAll(tree, root);
                if (searchNode != null) {
                    if (!searchNode.toString().isEmpty()) {
                        Debug.logInfo("searchNode: " + searchNode._key, module);
                        renderer.setQ(searchNode.toString());
                        isExpanedFirst = false;
                        searchTree(tree, root, searchNode.toString());
                    }
                } else {
                    Debug.logInfo("searchNode not found: ", module);
                    GenericValue parentProduct = prod;

                    if ("Y".equals(prod.getString("isVariant"))) {
                        GenericValue parentProductVal = ProductWorker.getParentProduct(prod.getString("productId"), delegator);
                        if (parentProductVal != null) {
                            parentProduct = parentProductVal;
                        }
                    }
                    Debug.logInfo("parentProduct productId: " + parentProduct.getString("productId"), module);
                    List<GenericValue> categories = ProductWorker.getCurrentProductCategories(parentProduct);
                    Debug.logInfo("searchNode categories: " + categories.size(), module);
                    Map<String, List<GenericValue>> catTree = new HashMap<String, List<GenericValue>>();
                    for (GenericValue categorie : categories) {
                        Debug.logInfo("searchNode categorie: " + categorie, module);
                        //first load child products
                        List<GenericValue> values = new ArrayList<GenericValue>();
                        catTree.put(categorie.getString("productCategoryId"), values);
                        productListArray.getProductCategoryPath(categorie.getString("primaryParentCategoryId"), values);
                    }

                    for (Entry<String, List<GenericValue>> catValues : catTree.entrySet()) {
                        TreeNode parentNode = null;
                        for (GenericValue categorie : catValues.getValue()) {
                            Debug.logInfo("searchNode categorie: " + categorie, module);
                            parentNode = productListArray.findProductTreeNodes(productListArray.getRootNode(), categorie.getString("productCategoryId"), categorie.getString("categoryName"));
                            if (parentNode != null /*&& !parentNode._id.equals("Popular_Brands")*/) {
                                if (!parentNode.isChildrenLoaded()) {
                                    List<TreeNode> childs = productListArray.getChildTreeNodes(parentNode);
                                    parentNode.setChildrenNodes(childs);
                                    parentNode.setLoaded(true);
                                    parentNode.setHasChildrean(!childs.isEmpty());
                                    Debug.logInfo(" loaded  Key: " + catValues.getKey() + "  searchNode categorie: " + categorie, module);
                                }
                            }
                        }
                    }

                    //loaded the hierarchy 
                    //now loade the products
                    for (GenericValue categorie : categories) {
                        TreeNode parentNode = productListArray.findProductTreeNodes(productListArray.getRootNode(), categorie.getString("productCategoryId"), categorie.getString("categoryName"));
                        if (!parentNode.isChildrenLoaded()) {
                            List<TreeNode> childs = productListArray.getChildTreeNodes(parentNode);
                            parentNode.setChildrenNodes(childs);
                            parentNode.setLoaded(true);
                            parentNode.setHasChildrean(!childs.isEmpty());
                            //Debug.logInfo(" loaded  Key: " + catValues.getKey() + "  searchNode categorie: " + categorie, module);
                        }
                    }
                    searchNode = (TreeNode) productListArray.getProductFromId(productVar);

//            collapseAll(tree, root);
                    if (searchNode != null) {
                        if (!searchNode.toString().isEmpty()) {
                            Debug.logInfo("searchNode: " + searchNode._key, module);
                            renderer.setQ(searchNode.toString());
                            isExpanedFirst = false;
                            searchTree(tree, root, searchNode.toString());
                        }
                    }

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductSelectionTreePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void treeTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_treeTreeWillExpand
        // TODO add your handling code here:
        TreePath path = evt.getPath();
//        Debug.logInfo("treeTreeWillExpand path.getLastPathComponent(): " + path.getLastPathComponent().toString(), module);
        if (path.getLastPathComponent() instanceof TreeNode) {
            TreeNode node = (TreeNode) path.getLastPathComponent();
            Debug.logInfo("treeTreeWillExpand 1 isloaded): "
                    + path.getLastPathComponent().toString() + "node.isLoaded(): " + node.isLoaded() + " node.setHasChildrean: " + node.isHasChildrean(), module);

            if (node.isLoaded() == false) {
                List<TreeNode> childs = productListArray.getChildTreeNodes(node);
                node.setChildrenNodes(childs);
                node.setLoaded(true);
                node.setHasChildrean(!childs.isEmpty());
                Debug.logInfo("treeTreeWillExpand 1 just loaded): "
                        + path.getLastPathComponent().toString() + "node.isLoaded(): " + node.isLoaded() + " node.setHasChildrean: " + node.isHasChildrean()
                        + " count: " + childs.size(), module);

            }
        }
    }//GEN-LAST:event_treeTreeWillExpand

    private void treeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeMouseClicked

        TreePath tp = tree.getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {

                clearFields();

                TreePath parentPath = tp.getParentPath();
                if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    try {
                        //get data from table
                        String productId = node._id;
                        GenericValue productEntity = node.loadDetails(productId, delegator);

                        //find department name from tree
                        TreeNode deptName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                        if (deptName != null) {
                        } else {
                            Debug.logInfo("tree clicked dep null", module);
                        }
                    } catch (GenericEntityException ex) {
                        java.util.logging.Logger.getLogger(ProductSelectionTreePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } /*else if (node.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
                 try {
                 String brandId = node._id;
                 GenericValue brandEntity = node.loadDetails(brandId, delegator);

                 TreeNode deptName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                 if (deptName != null) {
                 } else {
                 Debug.logInfo("tree clicked dep null", module);
                 }
                 } catch (GenericEntityException ex) {
                 }
                 } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                 try {
                 String departmentId = node._id;
                 GenericValue brandEntity = node.loadDetails(departmentId, delegator);

                 } catch (GenericEntityException ex) {
                 //                        Logger.getLogger(ProductDetailEditDialog.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }*/ else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                    String productId = node._id;

                }

            }
        }
    }//GEN-LAST:event_treeMouseClicked

    private void treeVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_treeVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_treeVetoableChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgFindSelection;
    private javax.swing.JButton btnFindProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JPanel panelFindContainer;
    public javax.swing.JPanel panelSelection;
    protected javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables

    /*public JButton getBtnAddSelected() {
     return btnAddSelected;
     }

     public void setBtnAddSelected(JButton btnAddSelected) {
     this.btnAddSelected = btnAddSelected;
     }*/
    /*
     public JButton getBtnAddSelectedAll() {
     return btnAddSelectedAll;
     }

     public void setBtnAddSelectedAll(JButton btnAddSelectedAll) {
     this.btnAddSelectedAll = btnAddSelectedAll;
     }
     */

    /*    public JCheckBox getCbStockedOnly() {
     return cbStockedOnly;
     }

     public void setCbStockedOnly(JCheckBox cbStockedOnly) {
     this.cbStockedOnly = cbStockedOnly;
     }
     */
    public TreeNode GetRecusevilyNodeType(TreeNode parent, TreePath tp, String nodeType) {
        TreeNode child = null;
        if (parent.getNodeName().equals(nodeType)) {
            child = parent;
        }

        if (child == null) {
//            TreeNode node = (TreeNode) tp.getLastPathComponent();
//            if (node != null) {

            TreePath parentPath = tp.getParentPath();
            if (parentPath != null) {
                TreeNode currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
                child = GetRecusevilyNodeType(currTreeParentNode, parentPath, nodeType);
            }
            //          }
        }
        return child;
    }

    @Override
    public JTree getTree() {
        return tree;
    }

    public javax.swing.JPanel getContainerPanel() {
        return this;
    }

    private ClassLoader getClassLoader() {
        ClassLoader cl = session.getClassLoader();
        if (cl == null) {
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable t) {
            }
            if (cl == null) {
//                Debug.log("No context classloader available; using class classloader", module);
                try {
                    cl = this.getClass().getClassLoader();
                } catch (Throwable t) {
                    //                  Debug.logError(t, module);
                }
            }
        }
        return cl;
    }

    /*  public BigDecimal getPrice() {
     BigDecimal price = BigDecimal.ZERO;
     if (editPrice.getText() != null && editPrice.getText().isEmpty() == false) {

     price = new BigDecimal(editPrice.getText());

     }
     return price;
     }

     public BigDecimal getQuantity() {
     BigDecimal qty = BigDecimal.ZERO;
     if (editQuantity.getText() != null && editQuantity.getText().isEmpty() == false) {

     qty = new BigDecimal(editQuantity.getText());

     }
     return qty;
     }
     */
    /*    public ProductTreeNode getSelectedProductTreeNode() {
     ProductTreeNode selNode = null;
     TreePath tp = getTree().getSelectionPath();
     if (tp != null) {
     TreeNode node = (TreeNode) tp.getLastPathComponent();
     if (node != null && node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
     selNode = (ProductTreeNode) node;
     }
     }
     return selNode;
     }
     */
    @Override
    public TreeNode getSelectedParentTreeNode() {
        TreeNode selNode = null;

        TreePath tp = getTree().getSelectionPath();
        if (tp != null) {
            TreePath parentPath = tp.getParentPath();
            if (parentPath != null) {
                selNode = (TreeNode) parentPath.getLastPathComponent();
            }
        }
        return selNode;
    }

    public TreeNode getSelectedChildTreeNode() {
        TreeNode selNode = null;

        TreePath tp = getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                selNode = (TreeNode) tree.getModel().getChild(node, 0);
            }
        }
        return selNode;
    }

    @Override
    public List<TreeNode> getAllChildTreeNode() {
        List<TreeNode> selNodeList = new ArrayList<TreeNode>();
//        TreePath tp = getTree().getS.getSelectionPath();

        TreeNode selNode = null;

        TreePath tp = getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                int count = tree.getModel().getChildCount(node);
                for (int i = 0; i < count; ++i) {
                    selNode = (TreeNode) tree.getModel().getChild(node, i);
                    selNodeList.add(selNode);
                }
            }
        }

        return selNodeList;
    }

    public TreeNode getSelectedTreeNode() {
        TreeNode selNode = null;
        TreePath tp = getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                selNode = node;
            }
        }
        return selNode;
    }

    public List<TreeNode> getAllSelectedTreeNode(String typeName) {
        List<TreeNode> selNodeList = new ArrayList<TreeNode>();
//        TreePath tp = getTree().getS.getSelectionPath();

        TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
        if (node != null) {
            if (!node.isLoaded()) {
                productListArray.recursivelyBuildTreeNodes(node);
                //List<TreeNode> childs = productListArray.getChildTreeNodes(node);                
            }
            GetRecusevilyAllProductNode(node, selNodeList, typeName);
        }

        List<TreeNode> prodNodeList = new ArrayList<TreeNode>();
        for (TreeNode prodnode : selNodeList) {
            prodNodeList.add(prodnode);
        }

        return prodNodeList;
    }

    @Override
    public TreeNode getSelectedTreeNode(String typeName) {

        /*try {
         throw new Exception("getSelectedTreeNode");
         } catch (Exception e) {
         Debug.logError(e,module);
         }*/
        TreeNode selNode = null;
        TreePath tp = getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null && node.getNodeName().equals(typeName)) {
                selNode = node;
            }
        }

        if (selNode == null) {

            TreeSelectionModel model = tree.getSelectionModel();
            if (model.isSelectionEmpty()) // no selection  
            {
                Debug.logInfo("empty trees sel", module);
                return selNode;
            }

            TreePath path = model.getSelectionPath();
            // check nodes back up the path to the root node, bottom to top  
            TreePath parentPath = path.getParentPath();
            if (parentPath != null) {
                int n = parentPath.getPathCount();
                Debug.logInfo("parent count: " + n, module);
                TreeNode nextParent;
                for (int j = n - 1; j >= 0; j--) // check last first  
                {
                    nextParent = (TreeNode) parentPath.getPathComponent(j);
                    Debug.logInfo("node name: " + nextParent.getNodeName(), module);
//            String s = (String)nextParent.getUserObject();  
                    //System.out.println("nextParent[" + j + "] = " + s);  
                    if (nextParent.getNodeName().equals(typeName)) {
                        selNode = (TreeNode) nextParent;
                        Debug.logInfo("found node name: " + typeName, module);
                        break;
                    }
                }
            }
        }

        return selNode;
    }

    @Override
    public TreeNode getParentNode(TreeNode childNode) {
        TreeNode parentNode = null;
        Debug.logInfo("getParentNode: " + childNode.toString(), module);
        TreeSelectionModel model = tree.getSelectionModel();
        if (model.isSelectionEmpty()) {
            return parentNode;
        }
        Debug.logInfo("getParentNode: 1 ", module);
        TreePath path = model.getSelectionPath();
        //System.out.println(path.toString());  

        // check nodes back up the path to the root node, bottom to top  
        TreePath parentPath = path.getParentPath();
        int n = parentPath.getPathCount();
        TreeNode nextParent;
        Debug.logInfo("parentPath.getPathCount():  " + n, module);
        for (int j = n - 1; j >= 0; j--) // check last first  
        {
            nextParent = (TreeNode) parentPath.getPathComponent(j);

//            String s = (String)nextParent.getUserObject();  
            //System.out.println("nextParent[" + j + "] = " + s);  
            if (nextParent.equals(childNode)) {
                parentNode = (TreeNode) parentPath.getPathComponent(j - 1);
                break;
            }
        }
        return parentNode;
    }

    protected void GetRecusevilyAllProductNode(TreeNode parent, List<TreeNode> list, String nodeType) {
        Debug.logInfo("GetRecusevilyAllProductNode", module);
        if (parent != null) {

            if (parent.getNodeName().equals(nodeType)) {
                Debug.logInfo(parent._id, module);
                list.add(parent);
            }

            int count = parent.getChildCount();
            Debug.logInfo("Child Count: " + (new Integer(count)).toString(), module);
            for (int index = 0; index < count; ++index) {
                TreeNode child = (TreeNode) parent.getChild(index);
                GetRecusevilyAllProductNode(child, list, nodeType);
            }
        }
    }

    private void fireDocumentChangeEvent() {
        /*if (rbDescription.isSelected()) {
         String q = editFindId.getText();
         renderer.setQ(q);
         TreePath root = tree.getPathForRow(0);
         if (root != null) {
         //                collapseAll(tree, root);
         if (!q.isEmpty()) {
         isExpanedFirst = false;
         searchTree(tree, root, q);
         }
         }
         }
         tree.repaint();*/
    }
    static boolean isExpanedFirst = false;

    private static void searchTree(JTree tree, TreePath path, String q) {
        TreeNode node = (TreeNode) path.getLastPathComponent();
        if (node == null) {
            return;
        }
//        Debug.logInfo("searchTree node.toString(): " + node.toString(), module);
        if (node.toString() != null && node.toString().toUpperCase().contains(q.toUpperCase())) {
            tree.expandPath(path.getParentPath());
//            tree.setSelectionPath(path);
            if (isExpanedFirst == false) {
                tree.scrollPathToVisible(path);
                tree.setSelectionPath(path);
                isExpanedFirst = true;
            }
        }
        int childCount = node.getChildCount();
        if (node.isHasChildrean()) {
            // Enumeration e = node.children();
            //while (e.hasMoreElements()) {
            //    searchTree(tree, path.pathByAddingChild(e.nextElement()), q);
            //}
            for (int index = 0; index < childCount; index++) {
//            Enumeration e = node.children();
//            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) node.getChild(index);//.nextElement();

                searchTree(tree, path.pathByAddingChild(n), q);
            }
        }
    }

    private static void collapseAll(JTree tree, TreePath parent) {
        //Debug.logInfo("parent.getLastPathComponent().toString(): " + parent.getLastPathComponent().toString(), module);
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        int childCount = node.getChildCount();
        if (node.isHasChildrean() && childCount >= 0) {
            for (int index = 0; index < childCount; index++) {
//            Enumeration e = node.children();
//            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) node.getChild(index);//.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                collapseAll(tree, path);
            }
        }
        tree.collapsePath(parent);
    }

    static public class PanelProductDescription extends PanelProductId {

        public PanelProductDescription(ControllerOptions controllerOptions) {
            super(controllerOptions);
            this.jLabel2.setText("Description:");
        }
    }

    static class CreationFactoryClass implements ProductTreeFindPanelFactoryInterface {

        @Override
        public ProductTreeFindInterface createFind(ControllerOptions options) {
            return new PanelProductDescription(options);
        }
    }
}
