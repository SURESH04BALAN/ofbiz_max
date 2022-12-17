/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.BorderLayout;
import mvc.model.list.ProductCategoryListCellRenderer;
import java.awt.Color;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;

import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryType;
import org.ofbiz.ordermax.product.catalog.categorycontent.ProductCategoryContentMaintainPanel;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryMaintainDetailPanel extends javax.swing.JPanel {

    public static final String module = ProductCategoryMaintainDetailPanel.class.getName();
    private ListAdapterListModel<ProductCategory> personListModel = new ListAdapterListModel<ProductCategory>();
    private ListAdapterListModel<ProductCategory> parentProductCategoryListModel = new ListAdapterListModel<ProductCategory>();
    private ListComboBoxModel<ProductCategory> parentProductCategoryComboBoxModel = new ListComboBoxModel<ProductCategory>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private ListModelSelection<ProductCategory> listModelSelection = new ListModelSelection<ProductCategory>();

    private ListAdapterListModel<ProductCategoryType> productCategoryTypeListModel = new ListAdapterListModel<ProductCategoryType>();
    private ListComboBoxModel<ProductCategoryType> productCategoryTypeComboBoxModel = new ListComboBoxModel<ProductCategoryType>();
    private ListSelectionModel pctSelectionModel = new DefaultListSelectionModel();

//    private ListModelSelection<ProductCategory> listModelSelection = new ListModelSelection<ProductCategory>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    XuiSession session = null;
    ProductCategory noneProductCategory = new ProductCategory();
    ProductCategoryContentMaintainPanel panelCategoryContent = new ProductCategoryContentMaintainPanel();

    public ProductCategoryMaintainDetailPanel(XuiSession session) {
        initComponents();
        this.session = session;

        //lis item render for combo
        ListCellRenderer<ProductCategory> prodCatalogRenderer = new ProductCategoryListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        productCategoryList.setCellRenderer(prodCatalogRenderer);
        productCategoryList.setEnabled(true);
        productCategoryList.setSelectionBackground(Color.CYAN);

        //parent list get data
        List<GenericValue> catalogList = PosProductHelper.getGenericValueLists("ProductCategory", null, null, session.getDelegator());
        for (GenericValue value : catalogList) {
            parentProductCategoryListModel.add(new ProductCategory(value));
        }
        noneProductCategory.setProductCategoryId(null);
        noneProductCategory.setCategoryName("<None>");
        parentProductCategoryListModel.add(noneProductCategory);

        //set the model
        parentProductCategoryComboBoxModel.setListModel(parentProductCategoryListModel);
        parentProductCategoryComboBoxModel.setListSelectionModel(selectionModel);
        parentProductCategoryComboBoxModel.setSelectedItem(noneProductCategory);

        //render for combo
        ProductCategoryListCellRenderer personRenderer = new ProductCategoryListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
        comboPrimaryParentCategoryId.setRenderer(personRenderer);

        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //parent list get data
        List<GenericValue> categoryTypeList = PosProductHelper.getGenericValueLists("ProductCategoryType", null, null, session.getDelegator());
        for (GenericValue value : categoryTypeList) {
            productCategoryTypeListModel.add(new ProductCategoryType(value));
        }

        //set the model
        productCategoryTypeComboBoxModel.setListModel(productCategoryTypeListModel);
        productCategoryTypeComboBoxModel.setListSelectionModel(pctSelectionModel);

        //render for combo
        ProductCategoryTypeListCellRenderer pctRenderer = new ProductCategoryTypeListCellRenderer();
        comboProductCategoryTypeId.setRenderer(pctRenderer);
        panelContent.setLayout(new BorderLayout());
        panelContent.add(BorderLayout.CENTER, panelCategoryContent);
    }

//    ProductCategory productCategory = null;
    public void setDialogFields(ProductCategory productCategory) {

        productCategoryId.setText(productCategory.getProductCategoryId());
//        productCategoryTypeId.setText(productCategory.getproductCategoryTypeId());
//        primaryParentCategoryId.setText(productCategory.getprimaryParentCategoryId());
        categoryName.setText(productCategory.getCategoryName());
        description.setText(productCategory.getDescription());
        longDescription.setText(productCategory.getLongDescription());
        categoryImageUrl.setText(productCategory.getCategoryImageUrl());
        linkOneImageUrl.setText(productCategory.getLinkOneImageUrl());
        linkTwoImageUrl.setText(productCategory.getLinkTwoImageUrl());
        detailScreen.setText(productCategory.getDetailScreen());
        showInSelect.setText(productCategory.getShowInSelect());
        if (productCategory.getCategoryData() != null) {
            txtDataValue.setText(productCategory.getCategoryData());
        }
        showSelectandFileImage(productCategory.getCategoryImageUrl());
        //set selected combo
        if (productCategory.getPrimaryParentCategoryId() == null) {
            parentProductCategoryComboBoxModel.setSelectedItem(noneProductCategory);
        } else {
            for (ProductCategory cat : parentProductCategoryListModel.getList()) {
                if (productCategory.getPrimaryParentCategoryId().equals(cat.getProductCategoryId())) {
                    parentProductCategoryComboBoxModel.setSelectedItem(cat);
                    break;
                }
            }

        }

        //set selected combo
        if (productCategory.getProductCategoryTypeId() != null) {

            for (ProductCategoryType catType : productCategoryTypeListModel.getList()) {
                if (productCategory.getProductCategoryTypeId().equals(catType.getproductCategoryTypeId())) {
                    productCategoryTypeComboBoxModel.setSelectedItem(catType);
                    break;
                }
            }
        }
        enableDialogFields(UtilValidate.isNotEmpty(productCategoryId.getText()));
        panelCategoryContent.setProductCategory(productCategory);
    }

    public void clearDialogFields() {
        productCategoryId.setText("");
        productCategoryId.setText("");
//        productCategoryTypeId.setText("");
//        primaryParentCategoryId.setText("");
        categoryName.setText("");
        description.setText("");
        longDescription.setText("");
        categoryImageUrl.setText("");
        linkOneImageUrl.setText("");
        linkTwoImageUrl.setText("");
        detailScreen.setText("");
        showInSelect.setText("");
        txtDataValue.setText("");
        originalImageUrlTextField.setText("");
//      comboProductCategoryTypeId.setS
    }

    public void getDialogFields(ProductCategory productCategory) {
        productCategory.setProductCategoryId(productCategoryId.getText());
//        productCategory.setproductCategoryTypeId(productCategoryTypeId.getText());
//        productCategory.setprimaryParentCategoryId(primaryParentCategoryId.getText());
        productCategory.setCategoryName(categoryName.getText());
        productCategory.setDescription(description.getText());
        productCategory.setLongDescription(longDescription.getText());
        productCategory.setCategoryImageUrl(categoryImageUrl.getText());
        productCategory.setLinkOneImageUrl(linkOneImageUrl.getText());
        productCategory.setLinkTwoImageUrl(linkTwoImageUrl.getText());
        productCategory.setDetailScreen(detailScreen.getText());
        productCategory.setShowInSelect(showInSelect.getText());
        ProductCategory parentProductCategory = (ProductCategory) parentProductCategoryComboBoxModel.getSelectedItem();
        productCategory.setCategoryData(txtDataValue.getText());
        if (parentProductCategory != null) {
            productCategory.setPrimaryParentCategoryId(parentProductCategory.getProductCategoryId());
        }

        ProductCategoryType productCategoryType = (ProductCategoryType) productCategoryTypeComboBoxModel.getSelectedItem();
        if (productCategoryType != null) {
            productCategory.setProductCategoryTypeId(productCategoryType.getproductCategoryTypeId());
        }
    }

    void enableDialogFields(boolean enable) {
        btnCategoryIcon.setEnabled(enable);
        btnLinkOneImage.setEnabled(enable);
        btnLinkTwoImage.setEnabled(enable);
        btnOriginalImage.setEnabled(enable);

        categoryImageUrl.setEnabled(enable);
        linkOneImageUrl.setEnabled(enable);
        linkTwoImageUrl.setEnabled(enable);
        originalImageUrlTextField.setEnabled(enable);
    }

    public void setProdCatalogList(ListAdapterListModel<ProductCategory> personListModel, ListSelectionModel selectionModel) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        panelCategoryDetail = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        productCategoryId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        categoryName = new javax.swing.JTextField();
        description = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        longDescription = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        categoryImageUrl = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        linkOneImageUrl = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        linkTwoImageUrl = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        detailScreen = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        showInSelect = new javax.swing.JTextField();
        comboProductCategoryTypeId = new JComboBox<ProductCategoryType>(productCategoryTypeComboBoxModel);
        comboPrimaryParentCategoryId = new JComboBox<ProductCategory>(parentProductCategoryComboBoxModel);
        btnLinkTwoImage = new javax.swing.JButton();
        btnCategoryIcon = new javax.swing.JButton();
        btnLinkOneImage = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        originalImageUrlTextField = new javax.swing.JTextField();
        btnOriginalImage = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtDataValue = new javax.swing.JTextField();
        panelContent = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panelImage = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnFixImagePath = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClearImageStore = new javax.swing.JButton();
        cbScaleImage = new javax.swing.JCheckBox();
        tabbedTree = new javax.swing.JTabbedPane();
        panelTree = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productCategoryList = new JList<ProductCategory>(personListModel);

        setPreferredSize(new java.awt.Dimension(900, 590));
        setLayout(new java.awt.BorderLayout());

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, deleteButton, newButton, okButton, saveButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton)
                        .addComponent(newButton)
                        .addComponent(deleteButton))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(okButton)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelCategoryDetail.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(700, 260));
        jPanel3.setRequestFocusEnabled(false);

        jLabel1.setText("Product Category Id:");

        productCategoryId.setEnabled(false);

        jLabel3.setText("Product Category Type Id:");

        jLabel4.setText("Primary Parent Category Id:");

        jLabel5.setText("Category Name:");

        jLabel6.setText("Description:");

        jLabel7.setText("Long Description:");

        jLabel8.setText("Category Image Url:");

        categoryImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                categoryImageUrlFocusGained(evt);
            }
        });

        jLabel9.setText("link One Image Url:");

        linkOneImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                linkOneImageUrlFocusGained(evt);
            }
        });

        jLabel10.setText("link Two Image Url:");

        linkTwoImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                linkTwoImageUrlFocusGained(evt);
            }
        });

        jLabel11.setText("Detail Screen:");

        jLabel12.setText("Show In Select:");

        btnLinkTwoImage.setText("jButton1");
        btnLinkTwoImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinkTwoImageActionPerformed(evt);
            }
        });

        btnCategoryIcon.setText("jButton1");
        btnCategoryIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryIconActionPerformed(evt);
            }
        });

        btnLinkOneImage.setText("jButton1");
        btnLinkOneImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinkOneImageActionPerformed(evt);
            }
        });

        jLabel15.setText("Original:");

        originalImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originalImageUrlTextFieldFocusGained(evt);
            }
        });

        btnOriginalImage.setText("jButton1");
        btnOriginalImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalImageActionPerformed(evt);
            }
        });

        jLabel13.setText("Data Value:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(comboPrimaryParentCategoryId, 0, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(productCategoryId, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(categoryName, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(longDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDataValue, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboProductCategoryTypeId, javax.swing.GroupLayout.Alignment.LEADING, 0, 175, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(linkOneImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(linkTwoImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detailScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showInSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCategoryIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkOneImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkTwoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {categoryName, comboPrimaryParentCategoryId, comboProductCategoryTypeId, description, longDescription, productCategoryId});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {categoryImageUrl, detailScreen, linkOneImageUrl, linkTwoImageUrl, originalImageUrlTextField, showInSelect});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(comboPrimaryParentCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(categoryImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCategoryIcon))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(linkOneImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(categoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(linkTwoImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLinkTwoImage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOriginalImage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(longDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(detailScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtDataValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(comboProductCategoryTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(showInSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnLinkOneImage)))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Category Detail", jPanel3);

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 632, Short.MAX_VALUE)
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Category Content", panelContent);

        jPanel5.setLayout(new java.awt.BorderLayout());

        panelImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelImage.setLayout(new java.awt.BorderLayout());

        lblImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.add(lblImage, java.awt.BorderLayout.CENTER);

        btnFixImagePath.setText("Fix Image Path");

        btnDelete.setText("Delete File");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClearImageStore.setText("Clear Image store");
        btnClearImageStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearImageStoreActionPerformed(evt);
            }
        });

        cbScaleImage.setSelected(true);
        cbScaleImage.setText("Scale Image");
        cbScaleImage.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(cbScaleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFixImagePath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearImageStore))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFixImagePath)
                    .addComponent(btnDelete)
                    .addComponent(btnClearImageStore)
                    .addComponent(cbScaleImage))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelImage.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(panelImage, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Category Image", jPanel5);

        panelCategoryDetail.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        panelTree.setPreferredSize(new java.awt.Dimension(200, 0));

        javax.swing.GroupLayout panelTreeLayout = new javax.swing.GroupLayout(panelTree);
        panelTree.setLayout(panelTreeLayout);
        panelTreeLayout.setHorizontalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        panelTreeLayout.setVerticalGroup(
            panelTreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tabbedTree.addTab("Category Tree", panelTree);

        productCategoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(productCategoryList);

        tabbedTree.addTab("Category List", jScrollPane1);

        panelCategoryDetail.add(tabbedTree, java.awt.BorderLayout.WEST);

        jPanel2.add(panelCategoryDetail, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        //      doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
//        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void btnLinkTwoImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinkTwoImageActionPerformed
        linkTwoImageUrl.setText(BaseHelper.selectandSetCategoryFileName("linkTwo", productCategoryId.getText(), 240, 240));
    }//GEN-LAST:event_btnLinkTwoImageActionPerformed

    private void btnCategoryIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryIconActionPerformed
        categoryImageUrl.setText(BaseHelper.selectandSetCategoryFileName("category", productCategoryId.getText(), 60, 60));
    }//GEN-LAST:event_btnCategoryIconActionPerformed

    private void btnLinkOneImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinkOneImageActionPerformed
        linkOneImageUrl.setText(BaseHelper.selectandSetCategoryFileName("linkOne", productCategoryId.getText(), 48, 48));
    }//GEN-LAST:event_btnLinkOneImageActionPerformed

    private void originalImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originalImageUrlTextFieldFocusGained
        showSelectandFileImage(originalImageUrlTextField.getText());
    }//GEN-LAST:event_originalImageUrlTextFieldFocusGained

    private void btnOriginalImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalImageActionPerformed

        if (cbScaleImage.isSelected()) {
            File filePath = BaseHelper.getImageFilePath("category");

//            String categoryImageUrl = BaseHelper.CopyCategoryImageSetFileName("category", filePath, brandId, 0, 0);
//            String linkOneImageUrl = BaseHelper.CopyCategoryImageSetFileName("linkOne", filePath, brandId, 60, 60);
//            String linkTwoImageUrl = BaseHelper.CopyCategoryImageSetFileName("linkTwo", filePath, brandId, 48, 48);
            originalImageUrlTextField.setText(BaseHelper.CopyCategoryImageSetFileName("original", filePath, productCategoryId.getText(), 0, 0));
            categoryImageUrl.setText(BaseHelper.CopyCategoryImageSetFileName("category", filePath, productCategoryId.getText(), 60, 60));
            linkOneImageUrl.setText(BaseHelper.CopyCategoryImageSetFileName("linkOne", filePath, productCategoryId.getText(), 48, 48));
            linkTwoImageUrl.setText(BaseHelper.CopyCategoryImageSetFileName("linkTwo", filePath, productCategoryId.getText(), 240, 240));
            showSelectandFileImage(originalImageUrlTextField.getText());

        } else {
            originalImageUrlTextField.setText(BaseHelper.selectandSetFileName("original", productCategoryId.getText(), 0, 0));
        }
    }//GEN-LAST:event_btnOriginalImageActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String fileDir = "/images/categories/" + productCategoryId.getText() + "/";
        String message = "Do you want to Delete Directory " + fileDir + "?";
        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Delete", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                BaseHelper.deleteDirectoryContent(fileDir);
                BaseHelper.clearImageStore();
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearImageStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearImageStoreActionPerformed
        BaseHelper.clearImageStore();
    }//GEN-LAST:event_btnClearImageStoreActionPerformed

    private void categoryImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_categoryImageUrlFocusGained
        showSelectandFileImage(categoryImageUrl.getText());
    }//GEN-LAST:event_categoryImageUrlFocusGained

    private void linkOneImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_linkOneImageUrlFocusGained
        showSelectandFileImage(linkOneImageUrl.getText());
    }//GEN-LAST:event_linkOneImageUrlFocusGained

    private void linkTwoImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_linkTwoImageUrlFocusGained
        showSelectandFileImage(linkTwoImageUrl.getText());
    }//GEN-LAST:event_linkTwoImageUrlFocusGained

    protected void showSelectandFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            String file = ProductDataTreeLoader.CategoryImagePath.concat(field);
            Debug.logInfo(file, module);
            lblImage.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(file));
        } else {
            lblImage.setIcon(null);
//            copyWebProductImagePanel.imageLabelSave.setIcon(null);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategoryIcon;
    private javax.swing.JButton btnClearImageStore;
    private javax.swing.JButton btnDelete;
    public javax.swing.JButton btnFixImagePath;
    private javax.swing.JButton btnLinkOneImage;
    private javax.swing.JButton btnLinkTwoImage;
    private javax.swing.JButton btnOriginalImage;
    public javax.swing.JButton cancelButton;
    private javax.swing.JTextField categoryImageUrl;
    private javax.swing.JTextField categoryName;
    private javax.swing.JCheckBox cbScaleImage;
    private javax.swing.JComboBox comboPrimaryParentCategoryId;
    private javax.swing.JComboBox comboProductCategoryTypeId;
    public javax.swing.JButton deleteButton;
    private javax.swing.JTextField description;
    private javax.swing.JTextField detailScreen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTextField linkOneImageUrl;
    private javax.swing.JTextField linkTwoImageUrl;
    private javax.swing.JTextField longDescription;
    public javax.swing.JButton newButton;
    public javax.swing.JButton okButton;
    private javax.swing.JTextField originalImageUrlTextField;
    public javax.swing.JPanel panelCategoryDetail;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelImage;
    public javax.swing.JPanel panelTree;
    private javax.swing.JTextField productCategoryId;
    public javax.swing.JList productCategoryList;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField showInSelect;
    private javax.swing.JTabbedPane tabbedTree;
    private javax.swing.JTextField txtDataValue;
    // End of variables declaration//GEN-END:variables
}
