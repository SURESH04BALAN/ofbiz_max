/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import org.ofbiz.ordermax.product.productContent.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductAssocToTableModel;
import mvc.model.table.ProductStoreCatalogTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.ProductAssocType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class ProductAssociationToPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = ProductAssociationToPanel.class.getName();
    private ListAdapterListModel<ProdCatalog> personListModel = new ListAdapterListModel<ProdCatalog>();
    public GenericTableModelPanel<ProductAssoc, ProductAssocToTableModel> tablePanel = null;

//    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    ProductAssoc productContentComposite = null;

    boolean isNew = false;
    boolean isModified = false;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;
    private DatePickerEditPanel purchaseFromDatePanel = null;
    private DatePickerEditPanel purchaseThruDatePanel = null;
    private ProductPickerEditPanel productPickerEditPanel;

    private JGenericComboBoxSelectionModel<ProductAssocType> comboProductAssocType = null;
    private JGenericComboBoxSelectionModel<Uom> comboUom = null;
    private JGenericComboBoxSelectionModel<RoleType> comboRoleType = null;
    ControllerOptions controllerOptions = new ControllerOptions();

    public ProductAssociationToPanel() {
        initComponents();
        /* ListCellRenderer<ProdCatalog> prodCatalogRenderer = new ProdCatalogListCellRenderer();
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
//        tabbedPaneProductContentDetail.add(panelImages, "Associations TO this Product from");
        //      tabbedPaneProductContentDetail.add(panelText, "Text");

        tablePanel = new GenericTableModelPanel<ProductAssoc, ProductAssocToTableModel>(new ProductAssocToTableModel());
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, tablePanel);

        fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        productPickerEditPanel = new ProductPickerEditPanel(controllerOptions, panelProductId);
//        contentMaintainPanel = new ContentMaintainPanel();
//        tabbedPaneProductContent.add("Content", contentMaintainPanel);
//        dataResourceMaintainPanel = new DataResourceMaintainPanel();
//        tabbedPaneProductContent.add("Data Resource", dataResourceMaintainPanel);
//        tabbedPaneProductContent.add(contentSet, "New Content");        

        
        for (int i = 0; i < ProductAssocToTableModel.Columns.values().length; i++) {
            ProductAssocToTableModel.Columns[] columns = ProductAssocToTableModel.Columns.values();
            ProductAssocToTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        comboProductAssocType = new JGenericComboBoxSelectionModel<ProductAssocType>(panelAssocisationType, ProductAssociationTypeSingleton.getValueList());

  //      comboUom = new JGenericComboBoxSelectionModel<Uom>(panelAssocisationType, UomTimeSingleton.getValueList()
        //              );
     //   comboRoleType = new JGenericComboBoxSelectionModel<RoleType>(panelUserRole, RoleTypeSingleton.getValueList()
        //            );
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
                            ProductAssoc value = tablePanel.listModel.getElementAt(i);
                            setDialogFields(value);

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
//        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
//        ComponentBorder.loweredBevelBorder(panelHeader, "Product Catalog Stores");
    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields(ProductAssoc productAssoc) {
        txtProductIdTo.setText(productAssoc.getproductIdTo());
        productPickerEditPanel.textIdField.setText(productAssoc.getproductId());
        try {
            comboProductAssocType.setSelectedItem(ProductAssociationTypeSingleton.getProductAssocType(productAssoc.getproductAssocTypeId()));
        } catch (Exception ex) {
            Logger.getLogger(ProductAssociationToPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            txtSeq.setText(productAssoc.getsequenceNum().toString());
        } catch (Exception ex) {
            //  Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
//            comboRoleType.setSelectedItem(RoleTypeSingleton.getRoleType(productAssoc.getuseRoleTypeId()));
        } catch (Exception ex) {
            //         Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            fromDatePanel.setTimeStamp(productAssoc.getfromDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            thruDatePanel.setTimeStamp(productAssoc.getthruDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
         if (UtilValidate.isNotEmpty(productAssoc.getuseCountLimit())) {
         txtReason.setText(productAssoc.getuseCountLimit().toString());
         }
        

         if (UtilValidate.isNotEmpty(productAssoc.getuseTime())) {
         txrInstruction.setText(productAssoc.getuseTime().toString());
         }

         if (UtilValidate.isNotEmpty(productAssoc.getsequenceNum())) {
         txtQuantity.setText(productAssoc.getsequenceNum().toString());
         }*/
    }

    public void clearDialogFields() {
        txtProductIdTo.setText("");
        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
        txtReason.setText("");
        txtQuantity.setText("");
        txrInstruction.setText("");
        txtReason.setText("");
        txtSeq.setText("");
        productPickerEditPanel.textIdField.setText("");
    }

    public void getDialogFields(ProductAssoc productAssoc) {
        /*    try {
         productAssoc.setfromDate(fromDatePanel.getTimeStamp());
         } catch (Exception ex) {
         Logger.getLogger(ProductAssociationMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         productAssoc.setthruDate(thruDatePanel.getTimeStamp());
         } catch (Exception ex) {
         productAssoc.setthruDate(null);
         }

         if (UtilValidate.isNotEmpty(comboProductContentType.getSelectedItem())) {
         productAssoc.setproductContentTypeId(comboProductContentType.getSelectedItem().getproductContentTypeId());
         }
         if (UtilValidate.isNotEmpty(comboUom.getSelectedItem())) {
         productAssoc.setuseTimeUomId(comboUom.getSelectedItem().getuomId());
         }
         if (UtilValidate.isNotEmpty(txtReason.getText())) {
         productAssoc.setsequenceNum(Long.parseLong(txtReason.getText()));
         }
         */
    }

    public void setproductAssocList(ListAdapterListModel<ProductAssoc> productAssocList) {
        tablePanel.setListModel(productAssocList);
        if(productAssocList.getSize() == 0){
        }
        
    }

    ListAdapterListModel<ProductAssoc> productAssocList = new ListAdapterListModel<ProductAssoc>();
    ;
    ProductComposite productComposite = null;

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        productAssocList.clear();
        clearDialogFields();
        if (productComposite != null) {
            if (productComposite.getProductAssocToList() == null) {
                ArrayList<ProductAssoc> list = LoadProductWorker.loadProductAssocToList(productComposite.getProduct().getproductId(), XuiContainer.getSession());
                productComposite.setProductAssocToList(list);
            }

            ArrayList<ProductAssoc> list = productComposite.getProductAssocToList();
            for (ProductAssoc composite : list) {
                productAssocList.add(composite);
            }
            setproductAssocList(productAssocList);

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

        jPanel2 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtReason = new javax.swing.JTextField();
        panelProductId = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelAssocisationType = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtProductIdTo = new javax.swing.JTextField();
        Store2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txrInstruction = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSeq = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelHeader.setMinimumSize(new java.awt.Dimension(0, 200));
        panelHeader.setPreferredSize(new java.awt.Dimension(489, 200));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(826, 300));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        panelDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 300));

        Store.setText("Product ID To:");

        jLabel3.setText("From Date:");

        jLabel4.setText("Thru Date:");

        jLabel5.setText("Reason:");

        txtReason.setMaximumSize(new java.awt.Dimension(100, 26));
        txtReason.setPreferredSize(new java.awt.Dimension(200, 26));

        panelProductId.setEnabled(false);
        panelProductId.setMaximumSize(new java.awt.Dimension(200, 26));
        panelProductId.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelFromDate.setMaximumSize(new java.awt.Dimension(200, 26));
        panelFromDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelThruDate.setMaximumSize(new java.awt.Dimension(200, 26));
        panelThruDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        Store1.setText("Association Type ID:");

        panelAssocisationType.setMaximumSize(new java.awt.Dimension(200, 26));
        panelAssocisationType.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelAssocisationTypeLayout = new javax.swing.GroupLayout(panelAssocisationType);
        panelAssocisationType.setLayout(panelAssocisationTypeLayout);
        panelAssocisationTypeLayout.setHorizontalGroup(
            panelAssocisationTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );
        panelAssocisationTypeLayout.setVerticalGroup(
            panelAssocisationTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

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

        jLabel6.setText("Product ID:");

        txtProductIdTo.setEnabled(false);
        txtProductIdTo.setMaximumSize(new java.awt.Dimension(200, 26));
        txtProductIdTo.setMinimumSize(new java.awt.Dimension(0, 0));
        txtProductIdTo.setPreferredSize(new java.awt.Dimension(200, 26));

        Store2.setText("Sequence Num:");

        jLabel9.setText("Instruction:");

        txrInstruction.setMaximumSize(new java.awt.Dimension(100, 26));
        txrInstruction.setPreferredSize(new java.awt.Dimension(200, 26));

        txtQuantity.setMaximumSize(new java.awt.Dimension(100, 26));
        txtQuantity.setPreferredSize(new java.awt.Dimension(200, 26));

        jLabel11.setText("Quantity:");

        txtSeq.setMaximumSize(new java.awt.Dimension(200, 26));
        txtSeq.setMinimumSize(new java.awt.Dimension(0, 0));
        txtSeq.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Store2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetailLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Store)
                                .addGap(18, 18, 18)
                                .addComponent(txtProductIdTo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDetailLayout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panelAssocisationType, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 22, Short.MAX_VALUE)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txrInstruction, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(txtReason, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))))
                .addContainerGap())
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelAssocisationType, panelFromDate, panelProductId, panelThruDate, txtProductIdTo, txtSeq});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Store)
                            .addComponent(txtProductIdTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store1)
                            .addComponent(panelAssocisationType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Store2)
                            .addComponent(txtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txrInstruction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(newButton)
                    .addComponent(deleteButton))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel1.add(panelDetail);

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields(productContentComposite);
        /*
         try {
         (new LoadProductCatalogWorker()).saveProdCatalogStore(productContentComposite, XuiContainer.getSession());
         if (isNew) {
         tablePanel.listModel.add(productContentComposite);
         }
         isNew = false;
         //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
         //        saveAction.actionPerformed(evt);
         } catch (Exception ex) {
         Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        productContentComposite = new ProductAssoc();
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JLabel Store2;
    public javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton newButton;
    private javax.swing.JPanel panelAssocisationType;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelThruDate;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txrInstruction;
    private javax.swing.JTextField txtProductIdTo;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtReason;
    private javax.swing.JTextField txtSeq;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return null;
    }

    @Override
    public JButton getCancelButton() {
        return null;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
