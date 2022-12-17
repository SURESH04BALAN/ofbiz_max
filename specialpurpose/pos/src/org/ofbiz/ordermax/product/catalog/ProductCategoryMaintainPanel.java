/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import org.ofbiz.ordermax.product.productloader.ProductCategoryDataTree;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.party.ProductCategoryInterface;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;
import org.ofbiz.ordermax.product.TreeExpandedRestorer;
import static org.ofbiz.ordermax.product.TreeExpandedRestorer.SELECTION_BY_ROW;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;

/**
 *
 * @author BBS Auctions
 */
public class ProductCategoryMaintainPanel extends javax.swing.JPanel implements ProductCategoryInterface, SimpleScreenInterface{
    final public static String module = ProductCategoryMaintainPanel.class.getName();  
    private ListAdapterListModel<ProductCategory> productCategoryListModel = new ListAdapterListModel<ProductCategory>();
//    private ListAdapterListModel<ProductCategory> productCategoryListModelTemp = new ListAdapterListModel<ProductCategory>();    
//    private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
//    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);
//    private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(progressBar);
//    private LoadSpeedSimulationPanel loadPersonSpeedSimulationPanel = new LoadSpeedSimulationPanel();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private ListModelSelection<ProductCategory> listModelSelection = new ListModelSelection<ProductCategory>();

    ProductSelectionTreePanel productTreePanel = null;

    XuiSession session = null;
    ProductCategoryMaintainDetailPanel productCategoryMaintainDetailPanel = null;

    /**
     * Creates new form ProdCatalogMaintainDialog
     */
    boolean isNew = true;
    boolean isModified = false;
    ProductCategory productCategory = new ProductCategory();

    public ProductCategoryMaintainPanel(XuiSession sess) {
        initComponents();
        this.session = sess;

//        progressBar.setStringPainted(true);
        productCategoryMaintainDetailPanel = new ProductCategoryMaintainDetailPanel(session);
        this.setLayout(new BorderLayout());
        this.add(productCategoryMaintainDetailPanel, BorderLayout.CENTER);

        //      contentPane.add(loadPersonSpeedSimulationPanel, BorderLayout.NORTH);
        //       contentPane.add(progressBar, BorderLayout.SOUTH);
        productTreePanel = createTreePanel();
        productTreePanel.setFindPanelVisible(false);

        productCategoryMaintainDetailPanel.panelTree.setLayout(new BorderLayout());
        productCategoryMaintainDetailPanel.panelTree.add(BorderLayout.CENTER, productTreePanel);

        //reload tree
        reloadTreePanel();

        Debug.log("productCategoryListModel size: " + productCategoryListModel.getSize());
        productCategoryMaintainDetailPanel.productCategoryList.setModel(productCategoryListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productCategoryMaintainDetailPanel.productCategoryList.setSelectionModel(selectionModel);
        listModelSelection.setListModels(productCategoryListModel, selectionModel);

        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                JList<ProductCategory> list = (JList<ProductCategory>) productCategoryMaintainDetailPanel.productCategoryList;
                productCategory = listModelSelection.getSelection();
                productCategoryMaintainDetailPanel.setDialogFields(productCategory);
                isNew = false;
            }
        });

        productCategoryMaintainDetailPanel.newButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isNew = true;
                isModified = false;
                ProductCategory newProductCategory = new ProductCategory();
                if (productCategory != null) {
                    newProductCategory.setPrimaryParentCategoryId(productCategory.getProductCategoryId());
                    newProductCategory.setProductCategoryTypeId(productCategory.getProductCategoryTypeId());
                }
                productCategoryMaintainDetailPanel.clearDialogFields();
                productCategoryMaintainDetailPanel.setDialogFields(newProductCategory);
                productCategory = newProductCategory;
            }
        });

        productCategoryMaintainDetailPanel.saveButton.addActionListener(
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        productCategoryMaintainDetailPanel.getDialogFields(productCategory);
                        /*                        if (isNew) {
                         productCategoryListModel.add(prodCatalog);
                         }
                         */
                        final SaveProductCategoryAction saveAction = new SaveProductCategoryAction(ProductCategoryMaintainPanel.this, session);
                        saveAction.actionPerformed(e);
                        //reload tree

//                        final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
//                        ter.save();
                        reloadTreePanel();
//                        ter.restore(SELECTION_BY_ROW);

                    }
                });
        productCategoryMaintainDetailPanel.okButton.addActionListener(
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        doClose(RET_OK);
                    }
                }
        );

        productCategoryMaintainDetailPanel.cancelButton.addActionListener(
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        doClose(RET_CANCEL);
                    }
                }
        );

        productCategoryMaintainDetailPanel.deleteButton.addActionListener(
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        final DeleteProductCategoryAction saveAction = new DeleteProductCategoryAction(ProductCategoryMaintainPanel.this, session);
                        saveAction.actionPerformed(e);
                        //reload tree

//                        final TreeExpandedRestorer ter = new TreeExpandedRestorer(productTreePanel.getTree(), SELECTION_BY_ROW);
//                        ter.save();
                        reloadTreePanel();
                    }
                }
        );
    }

    public void setProdCatalogList(ListAdapterListModel<ProductCategory> personListModel) {
//        productCategoryMaintainDetailPanel.setProdCatalogList(personListModel);

        productCategoryListModel = personListModel;
        productCategoryMaintainDetailPanel.productCategoryList.setModel(personListModel);
        listModelSelection.setListModels(personListModel, selectionModel);
        Debug.log("productCategoryListModel size: " + productCategoryListModel.getSize());
    }

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
//                TreePath selPath = treePanel.getTree().getPathForLocation(e.getX(), e.getY());
                Debug.logInfo("tree clicked", "module");
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {
                        TreeNode treeNode = treePanel.getSelectedTreeNode();
                        if (treeNode instanceof ProductCategoryTreeNode) {
                            ProductCategoryTreeNode productCategory = (ProductCategoryTreeNode) treeNode;
                            for (ProductCategory cat : productCategoryListModel.getList()) {
                                if (productCategory._id.equals(cat.getProductCategoryId())) {
                                    Debug.logInfo("productCategory._id: " + productCategory._id + "  cat.getCategoryData: "+cat.getCategoryData(), "module");
                                    listModelSelection.setSelection(cat);
                                    break;
                                }
                            }

                        } else {

                        }

                    }
                }
            }
        });

        return treePanel;
    }
//    ProductCategoryDataTree productCategoryDataTree = null;
    ProductTreeModel productTreeModel = null;

    final protected void reloadTreePanel() {
        //        treePanel 
        if (productTreePanel != null) {
//            if (productCategoryDataTree == null) {
            ProductCategoryDataTree productCategoryDataTree = new ProductCategoryDataTree(ControllerOptions.getSession().getProductCategory());
            productTreePanel.setProductListArray(productCategoryDataTree);
            //          }
            productCategoryDataTree.setProductLoaded(false);
            productCategoryDataTree.loadList();
            productTreeModel = new ProductTreeModel(productCategoryDataTree.getRootNode());
            productTreePanel.getTree().setModel(productTreeModel);
        }

        List<GenericValue> catalogList = PosProductHelper.getGenericValueLists("ProductCategory", null, null, session.getDelegator());
        for (GenericValue value : catalogList) {
            productCategoryListModel.add(new ProductCategory(value));
//            productCategoryListModelTemp.add(new ProductCategory(value));
        }
    }

    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        //notify 
        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }
//        setVisible(false);
//        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @Override
    public JButton getOkButton() {
        return productCategoryMaintainDetailPanel.okButton;
    }

    @Override
    public JButton getCancelButton() {
        return productCategoryMaintainDetailPanel.cancelButton;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
