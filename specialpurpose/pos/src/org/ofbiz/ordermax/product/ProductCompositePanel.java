/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.ProductTypeListCellRenderer;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.ProductType;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategoryCopyPanelFrame;
import org.ofbiz.ordermax.product.panel.PanelGoodIdentification;
import org.ofbiz.ordermax.product.panel.PanelImages;
import org.ofbiz.ordermax.product.panel.PanelInventory;
import org.ofbiz.ordermax.product.panel.PanelMeasures;
import org.ofbiz.ordermax.product.panel.PanelRateAndAmount;
import org.ofbiz.ordermax.product.panel.PanelShippingAndShoppingCartAndMiscellaneous;
import org.ofbiz.ordermax.product.panel.PanelVirtualProduct;
import org.ofbiz.ordermax.price.ProductPricePanel;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMaintainDialog;
import org.ofbiz.ordermax.product.panel.PanelProductLabelPrint;
import org.ofbiz.ordermax.product.panel.ProductAssociationMaintainPanel;
import org.ofbiz.ordermax.product.productContent.ProductContentMaintainPanel;
import org.ofbiz.ordermax.product.supplierproduct.SupplierProductPanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductCompositePanel extends javax.swing.JPanel implements ProductPanelInterface {

    /**
     * Creates new form ProductCompositePanel
     */
    private PanelVirtualProduct panelVirtualProduct = null;
    private PanelInventory panelInventory = null;
    private PanelRateAndAmount panelRateAndAmount = null;
    private PanelMeasures panelMeasures = null;
    private PanelShippingAndShoppingCartAndMiscellaneous panelShippingAndShoppingCartAndMiscellaneous = null;
    private PanelImages panelImages = null;
    private PanelGoodIdentification goodIdentificationPanel = null;
    private org.ofbiz.ordermax.product.panel.ProductCategoryMemberPanel panelCategories = null;

    private ProductContentMaintainPanel productContentMaintainPanel = null;

    private static String strVirtualProduct = "Virtual Product";
    private static String strInventory = "Inventory";
    private static String strRateAndAmount = "Rate And Amount";
    private static String strMeasures = "Measures";
    private static String strShippingAndShoppingCartAndMiscellaneous = "Shipping, ShoppingCart And Miscellaneous";

    final private ListAdapterListModel<ProductType> productTypeListModel = new ListAdapterListModel<ProductType>();
    private ListComboBoxModel<ProductType> productTypeComboBoxModel = new ListComboBoxModel<ProductType>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private ListModelSelection<ProductType> listModelSelection = new ListModelSelection<ProductType>();
    private JGenericComboBoxSelectionModel<String> taxable = null;    
    private SupplierProductPanel supplierProductPanel = null;
    private ProductPricePanel productPricePanel = null;
    private ProductAssociationMaintainPanel productAssociationMaintainPanel = null;
    private ProductCategoryCopyPanelFrame panelCatagoryCopy = null;
    private PanelProductLabelPrint panelProductLabelPrint = null;
    public ProductPickerEditPanel panelProductIdPicker = null;
    ControllerOptions options = null;
    javax.swing.JDesktopPane desktopPane = null;
    private boolean isEnable = true;

    @Override
    public boolean isIsEnable() {
        return isEnable;
    }

    @Override
    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
        detailsTabbedPane.setEnabled(isEnable);
        supplierProductPanel.setEnabled(isEnable);
        productPricePanel.setEnabled(isEnable);
        productContentMaintainPanel.setEnabled(isEnable);
        panelProductLabelPrint.setEnabled(isEnable);
        panelCatagoryCopy.setEnabled(isEnable);
        panelVirtualProduct.setEnabled(isEnable);
        panelInventory.setEnabled(isEnable);
        panelRateAndAmount.setEnabled(isEnable);
        panelMeasures.setEnabled(isEnable);
        panelShippingAndShoppingCartAndMiscellaneous.setEnabled(isEnable);
        panelImages.setEnabled(isEnable);
        goodIdentificationPanel.setEnabled(isEnable);
        goodIdentificationPanel.setIsEnable(isEnable);                
        panelCategories.setEnabled(isEnable);
        this.repaint();
    }

    public ProductCompositePanel(ControllerOptions options, javax.swing.JDesktopPane desktopPane) {
        this.options = options;
        this.desktopPane = desktopPane;
        initComponents();

        productTypeListModel.addAll(ProductTypeSingleton.getValueList());
        productTypeComboBoxModel.setListModel(productTypeListModel);
        productTypeIdTextField.setModel(productTypeComboBoxModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModelSelection.setListModels(productTypeListModel, selectionModel);
        productTypeComboBoxModel.setListSelectionModel(selectionModel);

//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelProductHeader, "Product Header");
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelProductDetails, "Product Details");
        ProductTypeListCellRenderer productTypeListCellRenderer = new ProductTypeListCellRenderer(false);
        productTypeIdTextField.setRenderer(productTypeListCellRenderer);

        panelVirtualProduct = new PanelVirtualProduct();
        panelInventory = new PanelInventory();
        panelRateAndAmount = new PanelRateAndAmount();
        panelMeasures = new PanelMeasures();
        productContentMaintainPanel = new ProductContentMaintainPanel();

        panelShippingAndShoppingCartAndMiscellaneous = new PanelShippingAndShoppingCartAndMiscellaneous();
        goodIdentificationPanel = new PanelGoodIdentification();
        panelImages = new PanelImages();
        panelCategories = new org.ofbiz.ordermax.product.panel.ProductCategoryMemberPanel();

        panelCategories.getCategoryActionTableCellEditor().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (UtilValidate.isNotEmpty(panelCategories.txtCategoryIdTableTextField.getText())) {
                    JFrame f = new JFrame();
                    f.setLayout(new BorderLayout());
                    f.setSize(720, 750);
                    ProductCategoryMaintainDialog dlg = new ProductCategoryMaintainDialog(f, true, XuiContainer.getSession());
//        dlg.setProdCatalogList(prodCatalogCategoryComboModel.dataListModel);
                    dlg.setVisible(true);
                }
            }
        });
        taxable =new JGenericComboBoxSelectionModel<String>(panelTaxable, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
//        panelImageContainer.setLayout(new BorderLayout());
//        panelImageContainer.add(BorderLayout.CENTER, panelImages);
        detailsTabbedPane.add("Good Identification(Scan)", goodIdentificationPanel);
        detailsTabbedPane.add(strVirtualProduct, panelVirtualProduct);
//        detailsTabbedPane.add(strInventory, panelInventory);
//        detailsTabbedPane.add(strRateAndAmount, panelRateAndAmount);
        detailsTabbedPane.add(strMeasures, panelMeasures);
        detailsTabbedPane.add(strShippingAndShoppingCartAndMiscellaneous, panelShippingAndShoppingCartAndMiscellaneous);
        detailsTabbedPane.add("Images", panelImages);
        detailsTabbedPane.add("Categories", panelCategories);
        tabbedPaneProduct.add("Content", productContentMaintainPanel);

        supplierProductPanel = new SupplierProductPanel();
        tabbedPaneProduct.add("Supplier Product", supplierProductPanel);
        productPricePanel = new ProductPricePanel();
        tabbedPaneProduct.add("Product Price", productPricePanel);
        panelProductLabelPrint = new PanelProductLabelPrint();
        tabbedPaneProduct.add("Product Print Label", panelProductLabelPrint);
        ControllerOptions op = new ControllerOptions(options);
        panelCatagoryCopy = new ProductCategoryCopyPanelFrame(op);
        tabbedPaneProduct.add("Product Category", panelCatagoryCopy);
        
        productAssociationMaintainPanel = new ProductAssociationMaintainPanel ();
        tabbedPaneProduct.add("Product Associations", productAssociationMaintainPanel);
     
        ControllerOptions partyOptions = new ControllerOptions(options);
        panelProductIdPicker = new ProductPickerEditPanel(partyOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panelProductIdPicker);
    }

    String visibleCardName = null;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneProduct = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        panelWordingAndComment = new javax.swing.JPanel();
        panelProductDetails = new javax.swing.JPanel();
        detailsTabbedPane = new javax.swing.JTabbedPane();
        panelProductHeader = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        internalNameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        panelProductId = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        brandNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        productTypeIdTextField = new javax.swing.JComboBox();
        panelTaxable = new javax.swing.JPanel();
        commentsTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 405));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        panelHeader.setPreferredSize(new java.awt.Dimension(749, 42));
        panelHeader.setLayout(new java.awt.BorderLayout());

        panelWordingAndComment.setPreferredSize(new java.awt.Dimension(749, 150));
        panelWordingAndComment.setLayout(new java.awt.BorderLayout());

        panelProductDetails.setLayout(new java.awt.BorderLayout());
        panelProductDetails.add(detailsTabbedPane, java.awt.BorderLayout.CENTER);

        panelWordingAndComment.add(panelProductDetails, java.awt.BorderLayout.CENTER);

        panelProductHeader.setPreferredSize(new java.awt.Dimension(820, 115));
        panelProductHeader.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(150, 116));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        lblImage.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.add(lblImage);

        panelProductHeader.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Product Id:");

        jLabel3.setText("Internal Name:");

        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel5.setText("Product Name:");

        panelProductId.setPreferredSize(new java.awt.Dimension(210, 24));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(internalNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(panelProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(internalNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.add(jPanel3);

        jLabel4.setText("Brand Name:");

        jLabel6.setText("Taxable(GST):");

        brandNameTextField.setMaximumSize(new java.awt.Dimension(175, 45));

        jLabel2.setText("Product Type:");

        productTypeIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productTypeIdTextField.setMaximumSize(new java.awt.Dimension(175, 45));

        commentsTextField.setEnabled(false);
        commentsTextField.setMaximumSize(new java.awt.Dimension(175, 45));

        javax.swing.GroupLayout panelTaxableLayout = new javax.swing.GroupLayout(panelTaxable);
        panelTaxable.setLayout(panelTaxableLayout);
        panelTaxableLayout.setHorizontalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTaxableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(commentsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTaxableLayout.setVerticalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTaxableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(commentsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productTypeIdTextField, 0, 205, Short.MAX_VALUE)
                    .addComponent(brandNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(panelTaxable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel4, jLabel6});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(productTypeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(brandNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(panelTaxable, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel5.add(jPanel2);

        panelProductHeader.add(jPanel5, java.awt.BorderLayout.CENTER);

        panelWordingAndComment.add(panelProductHeader, java.awt.BorderLayout.PAGE_START);

        panelHeader.add(panelWordingAndComment, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelHeader);

        tabbedPaneProduct.addTab("Product Details", jPanel1);

        add(tabbedPaneProduct);
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed
    private ProductComposite productComposite = null;

    @Override
    public ProductComposite getProductComposite() {
        return productComposite;
    }
    @Override
    public javax.swing.JTextField getProductIdTextField() {
        return panelProductIdPicker.textIdField;
    }
    @Override
    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        panelVirtualProduct.setProductComposite(productComposite);
        panelInventory.setProductComposite(productComposite);
        panelRateAndAmount.setProductComposite(productComposite);
        panelMeasures.setProductComposite(productComposite);
        panelShippingAndShoppingCartAndMiscellaneous.setProductComposite(productComposite);
        goodIdentificationPanel.setProductComposite(productComposite);
        panelCategories.setProductComposite(productComposite);
        panelImages.setProductComposite(productComposite);
        supplierProductPanel.setProductComposite(productComposite);
        productPricePanel.setProductComposite(productComposite);
        productContentMaintainPanel.setProductComposite(productComposite);
        panelProductLabelPrint.setProductComposite(productComposite);
        panelCatagoryCopy.setSeletedProducts(productComposite.getProduct());
        productAssociationMaintainPanel.setProductComposite(productComposite);
        productPricePanel.newPriceRecord();
    }

    @Override
    public void clearDialogFields() {
        commentsTextField.setText("");
        brandNameTextField.setText("");
        internalNameTextField.setText("");
        panelProductIdPicker.textIdField.setText("");
        txtProductName.setText("");
        taxable.setSelectedItem(YesNoConditionSelectSingleton.N);
        panelVirtualProduct.clearDialogFields();
        panelInventory.clearDialogFields();
        panelRateAndAmount.clearDialogFields();
        panelMeasures.clearDialogFields();
        panelShippingAndShoppingCartAndMiscellaneous.clearDialogFields();
        goodIdentificationPanel.clearDialogFields();
        panelCategories.clearDialogFields();
        panelImages.clearDialogFields();
        panelProductLabelPrint.clearDialogFields();
    }

    @Override
    public void getDialogField() {

        productComposite.getProduct().setcomments(commentsTextField.getText());
        productComposite.getProduct().setbrandName(brandNameTextField.getText());
        productComposite.getProduct().setinternalName(internalNameTextField.getText());
        productComposite.getProduct().setproductName(txtProductName.getText());
        productComposite.getProduct().setproductId(panelProductIdPicker.textIdField.getText());
        productComposite.getProduct().setdescription(txtProductName.getText());
        try {
            productComposite.getProduct().setproductTypeId(listModelSelection.getSelection().getproductTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.productComposite.getProduct().settaxable(YesNoConditionSelectSingleton.getKeyFromDisplayName(taxable.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        panelVirtualProduct.getDialogField();
//        panelRateAndAmount.getDialogField();
        panelMeasures.getDialogField();
        panelShippingAndShoppingCartAndMiscellaneous.getDialogField();
        goodIdentificationPanel.getDialogField();
        panelCategories.getDialogField();
        panelImages.getDialogField();
        panelProductLabelPrint.getDialogField();
    }

    protected void showSelectandFileImage() {
        lblImage.setIcon(null);
        if (UtilValidate.isNotEmpty(productComposite.getProduct().getmediumImageUrl())) {
            ImageIcon icon = BaseHelper.getImage(productComposite.getProduct().getmediumImageUrl());
            if (UtilValidate.isNotEmpty(icon)) {
                lblImage.setIcon(icon);
            }
        } else if (UtilValidate.isNotEmpty(productComposite.getProduct().getsmallImageUrl())) {
//                copyWebProductImagePanel.imageLabelSave.setIcon(BaseHelper.getImage(smallImageUrlTextField.getText()));
            ImageIcon icon = BaseHelper.getImage(productComposite.getProduct().getsmallImageUrl());
            if (UtilValidate.isNotEmpty(icon)) {
                lblImage.setIcon(icon);
            }
        }
    }

    @Override
    public void setDialogField() {

        showSelectandFileImage(/*OrderMaxUtility.getValidString(productComposite.getProduct().getmediumImageUrl())*/);
        commentsTextField.setText(OrderMaxUtility.getValidString(productComposite.getProduct().getcomments()));
        brandNameTextField.setText(OrderMaxUtility.getValidString(productComposite.getProduct().getbrandName()));
        internalNameTextField.setText(OrderMaxUtility.getValidString(productComposite.getProduct().getinternalName()));
        panelProductIdPicker.textIdField.setText(OrderMaxUtility.getValidString(productComposite.getProduct().getproductId()));
        txtProductName.setText(OrderMaxUtility.getValidString(productComposite.getProduct().getproductName()));
        try {
            listModelSelection.setSelection(ProductTypeSingleton.getProductType(OrderMaxUtility.getValidString(productComposite.getProduct().getproductTypeId())));
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        panelVirtualProduct.setDialogField();
        panelInventory.setDialogField();
        panelRateAndAmount.setDialogField();
        panelMeasures.setDialogField();
        panelShippingAndShoppingCartAndMiscellaneous.setDialogField();
        goodIdentificationPanel.setDialogField();
        panelCategories.setDialogField();
        panelImages.setDialogField();
        supplierProductPanel.setDialogField();
        productPricePanel.setDialogField();
        panelProductLabelPrint.setDialogField();
        
        if (UtilValidate.isNotEmpty(this.productComposite.getProduct().gettaxable())) {
            taxable.setSelectedItem(this.productComposite.getProduct().gettaxable());
        } else {
            taxable.setSelectedItem(YesNoConditionSelectSingleton.N);
        }
    }
    
    Map<Integer,TreeNode>treePath;
    @Override
    public void setProductTreePath(Map<Integer, TreeNode> treePath) {
        this.treePath = treePath;
    }
    
    @Override
    public boolean isNeedSavingPrices(){
        return false;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brandNameTextField;
    private javax.swing.JTextField commentsTextField;
    private javax.swing.JTabbedPane detailsTabbedPane;
    private javax.swing.JTextField internalNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblImage;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductDetails;
    private javax.swing.JPanel panelProductHeader;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelTaxable;
    private javax.swing.JPanel panelWordingAndComment;
    private javax.swing.JComboBox productTypeIdTextField;
    private javax.swing.JTabbedPane tabbedPaneProduct;
    private javax.swing.JTextField txtProductName;
    // End of variables declaration//GEN-END:variables
}
