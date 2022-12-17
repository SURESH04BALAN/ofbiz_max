/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import static java.awt.event.ItemEvent.SELECTED;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductCategory;

/**
 *
 * @author siranjeev
 */
public class CatalogSelectionPanel extends javax.swing.JPanel {

    public JGenericComboBoxSelectionModel<ProdCatalog> prodCatalogComboModel = null;
    private JGenericComboBoxSelectionModel<ProductCategory> prodCatalogCategoryComboModel = null;

    /**
     * Creates new form CatalogSelectionPanel
     */
    public CatalogSelectionPanel(ControllerOptions options) {
        initComponents();

        hideButtons(true);

//        ProdCatalogListCellRenderer catalogRenderer = new ProdCatalogListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        prodCatalogComboModel = new JGenericComboBoxSelectionModel<ProdCatalog>(ProdCatalogSingleton.getValueList());
        paneComboxCatalog.setLayout(new BorderLayout());
        paneComboxCatalog.add(BorderLayout.WEST, jLabel1);
        paneComboxCatalog.add(BorderLayout.EAST, btnUpdateCatalog);
        paneComboxCatalog.add(BorderLayout.CENTER, prodCatalogComboModel.jComboBox);

        prodCatalogComboModel.jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
//                ProdCatalog prodCatalog = seleProdCatalogComboModel.listModelSelection.getSelection();
                    int index = prodCatalogComboModel.jComboBox.getSelectedIndex();
                    Debug.log("index: " + index);
                    ProdCatalog prodCatalog = getSelectedCatalog();
                    if (prodCatalog != null) {
                        String selId = prodCatalog.getprodCatalogId();
                        loadCatalogCategories(selId);
                    }
                }
            }
        });

        try {
            prodCatalogComboModel.comboBoxModel.setSelectedItem(ProdCatalogSingleton.getProdCatalog(ControllerOptions.getSession().getProdCatalog().getprodCatalogId()));
        } catch (Exception ex) {
            Logger.getLogger(CatalogSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

//        ProductCategoryListCellRenderer pccRender = new ProductCategoryListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        prodCatalogCategoryComboModel = new JGenericComboBoxSelectionModel<ProductCategory>(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        panelCategory.setLayout(new BorderLayout());
        panelCategory.add(BorderLayout.WEST, jLabel2);
        panelCategory.add(BorderLayout.EAST, btnUpdateCategory);
        panelCategory.add(BorderLayout.CENTER, prodCatalogCategoryComboModel.jComboBox);

        prodCatalogCategoryComboModel.jComboBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                ProductCategory productCategory = getSelectedCategory();
                String selId = null;
                if (productCategory != null) {
                    selId = productCategory.getProductCategoryId();
                }

                ActionEvent event = new ActionEvent(this, 1, selId, new Date().getTime(), 2);
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
            }
        });

        ProductCategoryMaintainAction productCategoryMaintainAction = new ProductCategoryMaintainAction("Product Category", options);
        btnUpdateCategory.addActionListener(productCategoryMaintainAction);//.add(productCategoryMaintainAction.createActionMenuItem());

    }

    public void loadCatalogCategories(String selId) {

        if (prodCatalogCategoryComboModel != null) {

            prodCatalogCategoryComboModel.dataListModel.clear();
            List<GenericValue> categoryList = PosProductHelper.getGenericValueLists("ProdCatalogCategory", "prodCatalogId", selId, ControllerOptions.getSession().getDelegator());
            List<ProductCategory> valueList = new ArrayList<ProductCategory>();
            for (GenericValue value : categoryList) {
                try {
                    ProductCategory cat = ProductCategorySingleton.getProductCategory(value.getString("productCategoryId"));
                    valueList.add(cat);
//                    prodCatalogCategoryComboModel.dataListModel.add(cat);
                } catch (Exception ex) {
                    Logger.getLogger(CatalogSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            prodCatalogCategoryComboModel.setDataList(valueList);
            try {
                prodCatalogCategoryComboModel.comboBoxModel.setSelectedItem(ProductCategorySingleton.getProductCategory(ControllerOptions.getSession().getProductCategory().getProductCategoryId()));
            } catch (Exception ex) {
                //      Logger.getLogger(CatalogSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ProdCatalog getSelectedCatalog() {

        ProdCatalog prodCatalog = null;
        int index = prodCatalogComboModel.jComboBox.getSelectedIndex();
        if (index > -1) {
            prodCatalog = prodCatalogComboModel.comboBoxModel.getElementAt(index);
            String selId = prodCatalog.getprodCatalogId();
            loadCatalogCategories(selId);
        }
        return prodCatalog;
    }

    public ProductCategory getSelectedCategory() {

        ProductCategory prodCatalog = null;
        int index = prodCatalogCategoryComboModel.jComboBox.getSelectedIndex();
        if (index > -1) {
            prodCatalog = prodCatalogCategoryComboModel.comboBoxModel.getElementAt(index);
        }

        return prodCatalog;
    }

    public void setSelectedCatalog(String catalogId) {
        try {
            ProdCatalog prodCatalog = ProdCatalogSingleton.getProdCatalog(catalogId);
            if (prodCatalog != null) {
                prodCatalogComboModel.comboBoxModel.setSelectedItem(prodCatalog);
                loadCatalogCategories(prodCatalog.getprodCatalogId());
            }
        } catch (Exception ex) {
            Logger.getLogger(CatalogSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSelecteCategory(String catagoryId) {
        try {
            ProductCategory productCategory = ProductCategorySingleton.getProductCategory(catagoryId);
            if (productCategory != null) {
                prodCatalogCategoryComboModel.comboBoxModel.setSelectedItem(productCategory);
            }

        } catch (Exception ex) {
            Logger.getLogger(CatalogSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hideButtons(boolean val) {
        btnUpdateCatalog.setVisible(val);
        btnUpdateCatalog.setVisible(val);
        btnUpdateCategory.setVisible(val);
    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTree = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        paneComboxCatalog = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUpdateCatalog = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelCategory = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnUpdateCategory = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelTreeLayout = new javax.swing.GroupLayout(panelTree);
        panelTree.setLayout(panelTreeLayout);
        panelTreeLayout.setHorizontalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );
        panelTreeLayout.setVerticalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        add(panelTree, java.awt.BorderLayout.CENTER);

        jPanel1.setMinimumSize(new java.awt.Dimension(123, 60));
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 50));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel2.setLayout(new java.awt.GridLayout(0, 1));

        paneComboxCatalog.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Catalog:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setPreferredSize(new java.awt.Dimension(76, 16));
        paneComboxCatalog.add(jLabel1, java.awt.BorderLayout.WEST);

        btnUpdateCatalog.setText("Update");
        btnUpdateCatalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCatalogActionPerformed(evt);
            }
        });
        paneComboxCatalog.add(btnUpdateCatalog, java.awt.BorderLayout.EAST);

        jPanel2.add(paneComboxCatalog);

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(0, 1));

        panelCategory.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("     Category:");
        panelCategory.add(jLabel2, java.awt.BorderLayout.WEST);

        btnUpdateCategory.setText("Update");
        btnUpdateCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCategoryActionPerformed(evt);
            }
        });
        panelCategory.add(btnUpdateCategory, java.awt.BorderLayout.EAST);

        jPanel3.add(panelCategory);

        jPanel1.add(jPanel3);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateCatalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCatalogActionPerformed
        // TODO add your handling code here:
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(520, 550);
        ProdCatalogMaintainDialog dlg = new ProdCatalogMaintainDialog(f, true, ControllerOptions.getSession());
        dlg.setProdCatalogList(prodCatalogComboModel.dataListModel);
        dlg.setVisible(true);
    }//GEN-LAST:event_btnUpdateCatalogActionPerformed

    private void btnUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCategoryActionPerformed
        /*        JFrame f = new JFrame();
         f.setLayout(new BorderLayout());
         f.setSize(720, 750);
         ProductCategoryMaintainDialog dlg = new ProductCategoryMaintainDialog(f, true, session);
         //        dlg.setProdCatalogList(prodCatalogCategoryComboModel.dataListModel);
         dlg.setVisible(true);*/
    }//GEN-LAST:event_btnUpdateCategoryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnUpdateCatalog;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel paneComboxCatalog;
    private javax.swing.JPanel panelCategory;
    public javax.swing.JPanel panelTree;
    // End of variables declaration//GEN-END:variables
}
