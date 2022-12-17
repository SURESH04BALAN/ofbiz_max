/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductCatalogWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductStoreCatalogTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ProductStoreCatalogMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = ProductStoreCatalogMaintainPanel.class.getName();
    private ListAdapterListModel<ProdCatalog> personListModel = new ListAdapterListModel<ProdCatalog>();
    public GenericTableModelPanel<ProductStoreCatalog, ProductStoreCatalogTableModel> tablePanel = null;

//    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    ProductStoreCatalog productStoreCatalog = null;

    boolean isNew = false;
    boolean isModified = false;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;
    private JGenericComboBoxSelectionModel<ProdCatalog> comboProductCatalog = null;
    private JGenericComboBoxSelectionModel<ProductStore> comboProductStore = null;

    public ProductStoreCatalogMaintainPanel() {
        initComponents();
        /* ListCellRenderer<ProdCatalog> prodCatalogRenderer = new ProdCatalogListCellRenderer();
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
        tablePanel = new GenericTableModelPanel<ProductStoreCatalog, ProductStoreCatalogTableModel>(new ProductStoreCatalogTableModel());
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, tablePanel);
        fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        for (int i = 0; i < ProductStoreCatalogTableModel.Columns.values().length; i++) {
            ProductStoreCatalogTableModel.Columns[] columns = ProductStoreCatalogTableModel.Columns.values();
            ProductStoreCatalogTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        comboProductCatalog = new JGenericComboBoxSelectionModel<ProdCatalog>(panelProductCatalog, ProdCatalogSingleton.getValueList()
                );

        comboProductStore = new JGenericComboBoxSelectionModel<ProductStore>(panelStores, ProductStoreSingleton.getValueList()
                );

        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) tablePanel.selectionModel;//listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            clearDialogFields();
                            setDialogFields(tablePanel.listModel.getElementAt(i));
                            break;
                        }
                    }
                }
            }
        });

        /*panelHeader.add(BorderLayout.CENTER, scrollPane);
         catalogListSelectionModel.selectionModel.addListSelectionListener(new ListSelectionListener() {

         public void valueChanged(ListSelectionEvent e) {
         prodCatalog = catalogListSelectionModel.listModelSelection.getSelection();
         setDialogFields(prodCatalog);
         isNew = false;
         }
         });
         */
        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
        ComponentBorder.loweredBevelBorder(panelHeader, "Product Catalog Stores");

    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields(ProductStoreCatalog productStoreCatalog) {
        try {
            fromDatePanel.setTimeStamp(productStoreCatalog.getfromDate());
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            thruDatePanel.setTimeStamp(productStoreCatalog.getthruDate());
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            comboProductCatalog.setSelectedItem(ProdCatalogSingleton.getProdCatalog(productStoreCatalog.getprodCatalogId()));
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            comboProductStore.setSelectedItem(ProductStoreSingleton.getProductStore(productStoreCatalog.getproductStoreId()));
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(productStoreCatalog.getsequenceNum())) {
            txtSequenceNum.setText(productStoreCatalog.getsequenceNum().toString());
        }
    }

    public void clearDialogFields() {
        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
        txtSequenceNum.setText("");
    }

    public void getDialogFields(ProductStoreCatalog productStoreCatalog) {
        try {
            productStoreCatalog.setfromDate(fromDatePanel.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productStoreCatalog.setthruDate(thruDatePanel.getTimeStamp());
        } catch (Exception ex) {
            productStoreCatalog.setthruDate(null);
        }
        
        if (UtilValidate.isNotEmpty(comboProductCatalog.getSelectedItem())) {
            productStoreCatalog.setprodCatalogId(comboProductCatalog.getSelectedItem().getprodCatalogId());
        }
        if (UtilValidate.isNotEmpty(comboProductStore.getSelectedItem())) {
            productStoreCatalog.setproductStoreId(comboProductStore.getSelectedItem().getproductStoreId());
        }
        if (UtilValidate.isNotEmpty(txtSequenceNum.getText())) {
            productStoreCatalog.setsequenceNum(Long.parseLong(txtSequenceNum.getText()));
        }
    }

    public void setProductStoreCatalogList(ListAdapterListModel<ProductStoreCatalog> orderListModel) {
        tablePanel.setListModel(orderListModel);
    }

    public ProductStoreCatalog getProductStoreCatalog() {
        return productStoreCatalog;
    }

    public void setProductStoreCatalog(ProductStoreCatalog productStoreCatalog) {
        this.productStoreCatalog = productStoreCatalog;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSequenceNum = new javax.swing.JTextField();
        panelProductCatalog = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelStores = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 250));

        Store.setText("Product Catalog:");

        jLabel3.setText("From Date:");

        jLabel4.setText("Thru Date:");

        jLabel5.setText("Sequence Num:");

        panelProductCatalog.setEnabled(false);

        javax.swing.GroupLayout panelProductCatalogLayout = new javax.swing.GroupLayout(panelProductCatalog);
        panelProductCatalog.setLayout(panelProductCatalogLayout);
        panelProductCatalogLayout.setHorizontalGroup(
            panelProductCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelProductCatalogLayout.setVerticalGroup(
            panelProductCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        Store1.setText("Store:");

        javax.swing.GroupLayout panelStoresLayout = new javax.swing.GroupLayout(panelStores);
        panelStores.setLayout(panelStoresLayout);
        panelStoresLayout.setHorizontalGroup(
            panelStoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelStoresLayout.setVerticalGroup(
            panelStoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProductCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSequenceNum, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelStores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(163, Short.MAX_VALUE))
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelFromDate, panelProductCatalog, panelStores, panelThruDate, txtSequenceNum});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store)
                    .addComponent(panelProductCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store1)
                    .addComponent(panelStores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSequenceNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel2.add(panelDetail, java.awt.BorderLayout.PAGE_END);

        panelHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Catalog Stores"));
        panelHeader.setPreferredSize(new java.awt.Dimension(489, 200));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        panelButton.setPreferredSize(new java.awt.Dimension(664, 51));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(209, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, deleteButton, newButton, okButton, saveButton});

        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newButton)
                    .addComponent(deleteButton)
                    .addComponent(cancelButton)
                    .addComponent(okButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        add(panelButton, java.awt.BorderLayout.PAGE_END);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields(productStoreCatalog);

        try {
            (new LoadProductCatalogWorker()).saveProdCatalogStore(productStoreCatalog, XuiContainer.getSession());
            if (isNew) {
                tablePanel.listModel.add(productStoreCatalog);
            }
            isNew = false;
//        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
//        saveAction.actionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreCatalogMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        productStoreCatalog = new ProductStoreCatalog();
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        //      doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    public javax.swing.JButton cancelButton;
    public javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton newButton;
    public javax.swing.JButton okButton;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductCatalog;
    private javax.swing.JPanel panelStores;
    private javax.swing.JPanel panelThruDate;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txtSequenceNum;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return okButton;
    }

    @Override
    public JButton getCancelButton() {
        return cancelButton;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
