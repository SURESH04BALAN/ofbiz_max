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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;
import mvc.model.list.JListBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ProductListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseButtonPanel;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import static org.ofbiz.ordermax.product.ProductCatalogBuildMainScreen.module;
import org.ofbiz.ordermax.product.ProductCategoryPanel;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;
import org.ofbiz.ordermax.product.TreeExpandedRestorer;
import static org.ofbiz.ordermax.product.TreeExpandedRestorer.SELECTION_BY_ROW;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryMovePanelFrame extends javax.swing.JPanel {

    /**
     * Creates new form CategoryProductManagePanel
     */
    static private XuiSession session;
    ProductSelectionTreePanel productTreePanel = null;
    CatalogSelectionPanel catalogTreeSelPanel = null;
    protected javax.swing.JDesktopPane desktopPane = null;    
//    ProductCategoryPanel productCategoryPanel = null;
    String currentTreeCatRootId = null;
    
    public JListBoxSelectionModel<Product>  productListSelectionModel = null;
    public ListAdapterListModel<Product> getModel() {
        return productListSelectionModel.dataListModel;
    }
    ControllerOptions options = null;
    public ProductCategoryMovePanelFrame(ControllerOptions options) {
        initComponents();
        this.options = options;
        this.session = ControllerOptions.getSession();
        this.desktopPane = ControllerOptions.getDesktopPane();
        productTreePanel = createTreePanel();
//        DefaultListModel prevMovel = new DefaultListModel();
//        panelJlist.setModel(prevMovel);

        productListSelectionModel = new JListBoxSelectionModel<Product>(new ProductListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(productListSelectionModel.jlistBox);
        panelJlist.setLayout(new BorderLayout());
        panelJlist.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelDetailParent, "Product Destination");
        ComponentBorder.loweredBevelBorder(panelSelecton, "Selection");

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
//                            setProductSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        catalogTreeSelPanel = new CatalogSelectionPanel(options);
        catalogTreeSelPanel.hideButtons(false);
        panelDetail.setLayout(new BorderLayout());
        panelDetail.add(BorderLayout.CENTER, catalogTreeSelPanel);

        catalogTreeSelPanel.panelTree.setLayout(new BorderLayout());
        catalogTreeSelPanel.panelTree.add(BorderLayout.CENTER, productTreePanel);
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

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (productTreePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(ControllerOptions.getSession().getProductCategory());
            m_productsArray.setProductLoaded(false);
            m_productsArray.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
            productTreePanel.getTree().setModel(productTreeModel);
            productTreePanel.setProductListArray(m_productsArray);
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
        jSplitPane1 = new javax.swing.JSplitPane();
        panelSelecton = new javax.swing.JPanel();
        panelJlist = new javax.swing.JPanel();
        panelDetailParent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        btnMove = new javax.swing.JButton();
        btnMoveAll = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnRemoveAll = new javax.swing.JButton();
        panelDetail = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setPreferredSize(new java.awt.Dimension(1000, 297));

        panelSelecton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setMinimumSize(new java.awt.Dimension(300, 100));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 200));
        panelSelecton.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelJlistLayout = new javax.swing.GroupLayout(panelJlist);
        panelJlist.setLayout(panelJlistLayout);
        panelJlistLayout.setHorizontalGroup(
            panelJlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        panelJlistLayout.setVerticalGroup(
            panelJlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        panelSelecton.add(panelJlist, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panelSelecton);

        panelDetailParent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelDetailParent.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelButton.setPreferredSize(new java.awt.Dimension(432, 60));

        btnMove.setText("Move");
        btnMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveActionPerformed(evt);
            }
        });

        btnMoveAll.setText("Move All");
        btnMoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveAllActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnRemoveAll.setText("Remove All");
        btnRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMove, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveAll)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnMove, btnMoveAll});

        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMove)
                    .addComponent(btnMoveAll)
                    .addComponent(btnRemove)
                    .addComponent(btnRemoveAll))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.add(panelButton, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        jPanel1.add(panelDetail, java.awt.BorderLayout.CENTER);

        panelDetailParent.add(jPanel1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(panelDetailParent);

        jPanel3.add(jSplitPane1);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveActionPerformed
        JTree jTree = productTreePanel.getTree();
        TreePath tp = null;
        tp = jTree.getSelectionPath();
        TreeNode destNode = null;
        if (tp != null) {
            destNode = (TreeNode) tp.getLastPathComponent();
            if (destNode != null) {

                if (destNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    destNode = productTreePanel.getParentNode(destNode);
                }

                if (destNode != null) {
                    destNode.tp = tp;
                    //                            getProductCategoryPanel(node, null);//                            }
                }
            }
        }

        ListAdapterListModel<Product> prevMovel = getModel();        // TODO add your handling code here:
        int index = productListSelectionModel.jlistBox.getSelectedIndex();
        Product productNode = (Product) productListSelectionModel.jlistBox.getSelectedValue();
        if (productNode != null && destNode != null) {
            copyItem(productNode, destNode);

            prevMovel.remove((Product) productListSelectionModel.jlistBox.getSelectedValue());
            productListSelectionModel.jlistBox.setModel(prevMovel);
            if (prevMovel.getSize() > index) {
                productListSelectionModel.jlistBox.setSelectedIndex(index);
            } else {
                if (prevMovel.getSize() != 0) {
                    productListSelectionModel.jlistBox.setSelectedIndex(index - 1);
                }
            }
                        
            final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
            ter.save();
            if (catalogTreeSelPanel.getSelectedCategory() != null) {
                reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
            }

            ter.restore(SELECTION_BY_ROW);
        }
    }//GEN-LAST:event_btnMoveActionPerformed

    void copyItem(Product prodNode, TreeNode catNode) {
        try {
            GenericValue catMember = PosProductHelper.getProductCategoryMember(prodNode.getproductId(), catNode._id, session.getDelegator());
            if (catMember == null) {
                session.getDelegator().create(PosProductHelper.createProductCategoryMember(prodNode.getproductId(), catNode._id, session.getDelegator()));
            }

        } catch (Exception e2) {
            //Debug.logError(e2, module);
        }
    }

    void removeItem(TreeNode prodNode, TreeNode catNode) {
        try {
            GenericValue catMember = PosProductHelper.getProductCategoryMember(prodNode._id, catNode._id, session.getDelegator());
            if (catMember != null) {
                List itemList = new ArrayList<GenericValue>();
                itemList.add(catMember);
                session.getDelegator().removeAll(itemList);
            }

        } catch (Exception e2) {
            //Debug.logError(e2, module);
        }
    }


    private void btnMoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveAllActionPerformed
        TreeNode destNode = null;
        TreeNode selNode = productTreePanel.getSelectedTreeNode(ProductCategoryTreeNode.ProductCategoryTreeNodeName);//getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
        if (selNode != null) {
            destNode = selNode;
        }   
        
        
        ListAdapterListModel<Product> prevMovel = getModel();        // TODO add your handling code here:
        int index = productListSelectionModel.jlistBox.getSelectedIndex();
        Debug.logError("getSelectedIndex: " + index, module);
        Product productNode = productListSelectionModel.listModelSelection.getSelection();//.productListSelectionModel.selectionModel.getLeadSelectionIndex();.getSelectedValue();
        if(productNode==null){
            OrderMaxOptionPane.showMessageDialog(null, "Please select product. ", "Error", JOptionPane.YES_NO_OPTION);
            return;
        }
        
        if(destNode == null){
            OrderMaxOptionPane.showMessageDialog(null, "Please select destination category. ", "Error", JOptionPane.YES_NO_OPTION);
            return;            
        }
        
        if (productNode != null && destNode != null) {
            copyItem(productNode, destNode);
            prevMovel.remove((Product) productListSelectionModel.jlistBox.getSelectedValue());
            productListSelectionModel.jlistBox.setModel(prevMovel);
            if (prevMovel.getSize() > index) {
                productListSelectionModel.jlistBox.setSelectedIndex(index);
            } else {
                if (prevMovel.getSize() != 0) {
                    productListSelectionModel.jlistBox.setSelectedIndex(index - 1);
                }
            }

            final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
            ter.save();
//            String categoryId = catalogTreeSelPanel.genericCategoryIdCombo.getSelectedItemId();
//            reloadTreePanel(categoryId);
            if (catalogTreeSelPanel.getSelectedCategory() != null) {
                reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
            }

            ter.restore(SELECTION_BY_ROW);
        }
        
    }//GEN-LAST:event_btnMoveAllActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed

        JTree jTree = productTreePanel.getTree();
        TreePath tp = null;
        tp = jTree.getSelectionPath();
        TreeNode productNode = null;
        if (tp != null) {
            productNode = (TreeNode) tp.getLastPathComponent();
            if (productNode != null) {
                System.out.print(" productNode " + productNode._id);
                System.out.print(" productNode.getNodeName() " + productNode.getNodeName());
                System.out.print(" ProductTreeNode.ProdutNodeName      " + ProductTreeNode.ProdutNodeName);

                if (!productNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    System.out.print(" return: " + productNode.getNodeName().equals(ProductTreeNode.ProdutNodeName));
                    return;
                }

                if (productNode != null) {
                    productNode.tp = tp;
                    //                            getProductCategoryPanel(node, null);//                            }
                }
            }
        }

        TreeNode catNode = productTreePanel.getParentNode(productNode);
        TreePath parentPath = tp.getParentPath();
        if (parentPath != null) {
            catNode = (TreeNode) parentPath.getLastPathComponent();
        }

        if (productNode != null && catNode != null) {
            System.out.print(" productNode " + productNode._id + ",catNode: " + catNode._id);
            removeItem(productNode, catNode);

            final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
            ter.save();
            if (catalogTreeSelPanel.getSelectedCategory() != null) {
                reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
            }

            ter.restore(SELECTION_BY_ROW);

        } else {
            System.out.print(" productNode " + productNode._id + ",catNode: " + catNode._id);
        }

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllActionPerformed
        TreePath tp = null;
        tp = productTreePanel.getTree().getSelectionPath();
        if (tp != null) {
            TreeNode catNode = (TreeNode) tp.getLastPathComponent();
            if(catNode!=null){
          System.out.print( ",catNode: " + catNode._id);                
            List<TreeNode> selList = productTreePanel.getAllChildTreeNode();//getAllSelectedTreeNode(ProductTreeNode.ProdutNodeName);
            if (selList != null) {
                try {

                    for (TreeNode productNode : selList) {
                            System.out.print(" productNode " + productNode._id + ",catNode: " + catNode._id +"\n");                        
//                        TreeNode catNode = productTreePanel.getParentNode(productNode);
                        if (productNode != null && catNode != null) {
                            System.out.print(" productNode " + productNode._id + ",catNode: " + catNode._id);
                            removeItem(productNode, catNode);
                        } else {
                            System.out.print(" productNode " + productNode._id + ",catNode: " + catNode._id);
                        }

                    }

                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            }
        }
        
      
        final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
        ter.save();
        if (catalogTreeSelPanel.getSelectedCategory() != null) {
            reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
        }

        ter.restore(SELECTION_BY_ROW);

    }//GEN-LAST:event_btnRemoveAllActionPerformed

    final protected ProductSelectionTreePanel createTreePanel() {
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
                            Debug.logError(ex,module);
                        }
                    }
                }
            }
        });
        return treePanel;
    }
    
    public void setSelectedCatalogId(String catalogId){
            catalogTreeSelPanel.setSelectedCatalog(catalogId);
    }

    public void setSelecteCategoryId(String catagoryId){
        catalogTreeSelPanel.setSelecteCategory(catagoryId);               
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMove;
    private javax.swing.JButton btnMoveAll;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveAll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelDetailParent;
    private javax.swing.JPanel panelJlist;
    private javax.swing.JPanel panelSelecton;
    // End of variables declaration//GEN-END:variables
}
