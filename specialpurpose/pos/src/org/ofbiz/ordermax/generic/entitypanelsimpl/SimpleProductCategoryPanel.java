/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import org.ofbiz.ordermax.generic.GenericSaveInterface;
import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.components.BasePickerEditPanel;
import org.ofbiz.ordermax.base.components.CategoryPickerEditPanel;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryType;
import org.ofbiz.ordermax.product.catalog.ProdutCategoryTypeSingleton;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProductCategoryPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProductCategory> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<ProductCategoryType> productCategoryTypeModel = null;
    private ReportCreatorSelectionInterface categoryPickerPanel = null;

    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProductCategoryPanel(ControllerOptions options) {
        try {
            initComponents();
            this.options = new ControllerOptions(options);
            options.put("DirtyManager", dirty);
            
            List<ProductCategory> list = new ArrayList<ProductCategory>();//PosTerminalSingleton.getValueList();
            if (options.contains("EntityName")) {
                entityName = (String) options.get("EntityName");
            }
            
            listSelectionModel = new JGenericListBoxSelectionModel<ProductCategory>(list);
            recordList.setLayout(new BorderLayout());
            loadList();
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(10, 11, 580, 200);
            
            scrollPane.setViewportView(listSelectionModel.jlistBox);
            recordList.setLayout(new BorderLayout());
            recordList.add(BorderLayout.CENTER, scrollPane);
            ComponentBorder.loweredBevelBorder(recordList, "List");
            ComponentBorder.loweredBevelBorder(jPanel3, "Detail");
            
            productCategoryTypeModel = new JGenericComboBoxSelectionModel<ProductCategoryType>(ProdutCategoryTypeSingleton.getValueList());
            panelProdutCategoryType.setLayout(new BorderLayout());
            panelProdutCategoryType.add(BorderLayout.CENTER, productCategoryTypeModel.jComboBox);
            
            listSelectionModel.jlistBox.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    ListSelectionModel lsm = (ListSelectionModel) listSelectionModel.selectionModel;
                    if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                System.out.println(" " + i);
                                setSelected(i);
                                break;
                            }
                        }
                    }
                }
            });
            
            ControllerOptions categoryOptions = new ControllerOptions(options);
            categoryPickerPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.CategoryId, categoryOptions, panelParentCategoryId);//new CategoryPickerEditPanel(categoryOptions, panelCategoryId);
            
            // categoryPickerPanel = new CategoryPickerEditPanel(categoryOptions, panelParentCategoryId);
            
            productCategoryId.getDocument().addDocumentListener(dirty);
            categoryName.getDocument().addDocumentListener(dirty);
            description.getDocument().addDocumentListener(dirty);
            //facilityModel.jComboBox.addActionListener(dirty);
            
            newRecord();
        } catch (Exception ex) {
            Logger.getLogger(SimpleProductCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ProductCategory currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProductCategory) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            productCategoryId.setText(currentRecord.getProductCategoryId());
//        productCategoryTypeId.setText(currentRecord.getproductCategoryTypeId());
//        primaryParentCategoryId.setText(currentRecord.getprimaryParentCategoryId());
            categoryName.setText(currentRecord.getCategoryName());
            description.setText(currentRecord.getDescription());
            longDescription.setText(currentRecord.getLongDescription());
            categoryImageUrl.setText(currentRecord.getCategoryImageUrl());
            linkOneImageUrl.setText(currentRecord.getLinkOneImageUrl());
            linkTwoImageUrl.setText(currentRecord.getLinkTwoImageUrl());
            detailScreen.setText(currentRecord.getDetailScreen());
            showInSelect.setText(currentRecord.getShowInSelect());
            if (currentRecord.getCategoryData() != null) {
                txtDataValue.setText(currentRecord.getCategoryData());
            }

            categoryPickerPanel.setEntityValue(currentRecord.getPrimaryParentCategoryId());
//            showSelectandFileImage(currentRecord.getCategoryImageUrl());

            //set selected combo
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getProductCategoryTypeId())) {
                    productCategoryTypeModel.setSelectedItem(ProdutCategoryTypeSingleton.getProductCategoryType(currentRecord.getProductCategoryTypeId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimplePosTerminalPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            enableDialogFields(UtilValidate.isNotEmpty(productCategoryId.getText()));

            dirty.setDirty(false);
        }
//        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));
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

    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
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

    public void getDialogFields() {

        if (currentRecord != null) {
            currentRecord.setProductCategoryId(productCategoryId.getText());
//        currentRecord.setproductCategoryTypeId(productCategoryTypeId.getText());
//        currentRecord.setprimaryParentCategoryId(primaryParentCategoryId.getText());
            currentRecord.setCategoryName(categoryName.getText());
            currentRecord.setDescription(description.getText());
            currentRecord.setLongDescription(longDescription.getText());
            currentRecord.setCategoryImageUrl(categoryImageUrl.getText());
            currentRecord.setLinkOneImageUrl(linkOneImageUrl.getText());
            currentRecord.setLinkTwoImageUrl(linkTwoImageUrl.getText());
            currentRecord.setDetailScreen(detailScreen.getText());
            currentRecord.setShowInSelect(showInSelect.getText());

            currentRecord.setCategoryData(txtDataValue.getText());

            currentRecord.setPrimaryParentCategoryId((String)categoryPickerPanel.getEntityValue());

            try {
                if (UtilValidate.isNotEmpty(productCategoryTypeModel.getSelectedItem())) {
                    currentRecord.setProductCategoryTypeId(productCategoryTypeModel.getSelectedItem().getproductCategoryTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 //       PartyGroup partygroup = partyGroupAccount.getPartyGroup();
        //       partygroup.setpartyId(OrderMaxUtility.getValidString(terminalIdTextField.getText()));

        //      partygroup.setgroupNameLocal(OrderMaxUtility.getValidString(terminalNameTextField.getText()));
//        partygroup.setnumEmployees(OrderMaxUtility.getValidString(numEmployeesTextField.getText()));
    }
    /*
     public static <T> T instanceOf(Class<T> clazz) throws Exception {
     return clazz.newInstance();
     }
    
     public static <T> Collection<T> getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
     Collection<T> objectList = new ArrayList<T>();
     Class<T> clazz 
     for (GenericValue genVal : genList) {
     objectList.add(new T(genVal));
     }
     return objectList;
     }    
     */

    protected void showSelectandFileImage(String field) {

        /* if (field != null && field.isEmpty() == false) {
         String file = ProductListArray.CategoryImagePath.concat(field);
         Debug.logInfo(file, module);
         lblImage.setIcon(BaseHelper.getImage(file));
         } else {
         lblImage.setIcon(null);
         //            copyWebProductImagePanel.imageLabelSave.setIcon(null);
         }*/
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
        recordList = new javax.swing.JPanel();
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
        btnLinkTwoImage = new javax.swing.JButton();
        btnCategoryIcon = new javax.swing.JButton();
        btnLinkOneImage = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        originalImageUrlTextField = new javax.swing.JTextField();
        btnOriginalImage = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtDataValue = new javax.swing.JTextField();
        panelParentCategoryId = new javax.swing.JPanel();
        panelProdutCategoryType = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(700, 260));
        jPanel3.setRequestFocusEnabled(false);

        jLabel1.setText("Product Category Id:");

        jLabel3.setText("Product Category Type Id:");

        jLabel4.setText("Primary Parent Category Id:");

        jLabel5.setText("Category Name:");

        jLabel6.setText("Description:");

        jLabel7.setText("Long Description:");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Category Image Url:");

        categoryImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                categoryImageUrlFocusGained(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("link One Image Url:");

        linkOneImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                linkOneImageUrlFocusGained(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("link Two Image Url:");

        linkTwoImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                linkTwoImageUrlFocusGained(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Detail Screen:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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

        javax.swing.GroupLayout panelParentCategoryIdLayout = new javax.swing.GroupLayout(panelParentCategoryId);
        panelParentCategoryId.setLayout(panelParentCategoryIdLayout);
        panelParentCategoryIdLayout.setHorizontalGroup(
            panelParentCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );
        panelParentCategoryIdLayout.setVerticalGroup(
            panelParentCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelProdutCategoryTypeLayout = new javax.swing.GroupLayout(panelProdutCategoryType);
        panelProdutCategoryType.setLayout(panelProdutCategoryTypeLayout);
        panelProdutCategoryTypeLayout.setHorizontalGroup(
            panelProdutCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );
        panelProdutCategoryTypeLayout.setVerticalGroup(
            panelProdutCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProdutCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataValue, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(longDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelParentCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(linkOneImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(linkTwoImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detailScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showInSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCategoryIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkOneImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkTwoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalImage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {categoryName, description, longDescription, panelParentCategoryId, panelProdutCategoryType, productCategoryId, txtDataValue});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {categoryImageUrl, detailScreen, linkOneImageUrl, linkTwoImageUrl, originalImageUrlTextField, showInSelect});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelParentCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(categoryImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCategoryIcon))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(productCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(linkOneImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkOneImage))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(categoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(linkTwoImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLinkTwoImage))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalImage))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(longDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(detailScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txtDataValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelProdutCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(showInSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void categoryImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_categoryImageUrlFocusGained
        showSelectandFileImage(categoryImageUrl.getText());
    }//GEN-LAST:event_categoryImageUrlFocusGained

    private void linkOneImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_linkOneImageUrlFocusGained
        showSelectandFileImage(linkOneImageUrl.getText());
    }//GEN-LAST:event_linkOneImageUrlFocusGained

    private void linkTwoImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_linkTwoImageUrlFocusGained
        showSelectandFileImage(linkTwoImageUrl.getText());
    }//GEN-LAST:event_linkTwoImageUrlFocusGained

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

      /*  if (cbScaleImage.isSelected()) {
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
        }*/
    }//GEN-LAST:event_btnOriginalImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategoryIcon;
    private javax.swing.JButton btnLinkOneImage;
    private javax.swing.JButton btnLinkTwoImage;
    private javax.swing.JButton btnOriginalImage;
    private javax.swing.JTextField categoryImageUrl;
    private javax.swing.JTextField categoryName;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField linkOneImageUrl;
    private javax.swing.JTextField linkTwoImageUrl;
    private javax.swing.JTextField longDescription;
    private javax.swing.JTextField originalImageUrlTextField;
    private javax.swing.JPanel panelParentCategoryId;
    private javax.swing.JPanel panelProdutCategoryType;
    private javax.swing.JTextField productCategoryId;
    private javax.swing.JPanel recordList;
    private javax.swing.JTextField showInSelect;
    private javax.swing.JTextField txtDataValue;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProductCategory();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setProductCategoryId("");
            currentRecord.setCategoryName(OrderMaxUtility.getValidString(categoryName.getText()) + "(Copy)");
            setDialogField();
            dirty.setDirty(true);
        }
    }

    @Override
    public Map<String, Object> getValuesMap() {
        if (currentRecord != null) {
            return currentRecord.getValuesMap();
        }
        return null;
    }

    @Override
    public GenericValue getGenericValueObj() {
        if (currentRecord != null) {
            currentRecord.getGenericValue();            
            return currentRecord.getGenericValueObj();
        }
        return null;
    }

    @Override
        public List<String> getKey() {
        return UtilMisc.toList("productCategoryId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(productCategoryId.getText())) {
            productCategoryId.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(categoryName.getText())) {
            categoryName.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(description.getText())) {
            description.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleProductCategoryPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }
    void loadList(){
        loadList(null);
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        List<ProductCategory> list = new ArrayList<ProductCategory>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProductCategory(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new ProductCategory(val);
        setDialogField();
    }
}
