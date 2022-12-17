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
import javax.swing.JButton;
import javax.swing.JPanel;
import mvc.controller.LoadProductWorker;
import mvc.controller.ProductIdMaintainVerifyValidator;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.components.CategoryPickerEditPanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;

/**
 *
 * @author BBS Auctions
 */
public class CreateProductIdPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    static public String module = CreateProductIdPanel.class.getName();
    public CategoryPickerEditPanel panelParentCategoryPicker;
    public CategoryPickerEditPanel panelBrandCategoryPicker;
    public ProductPickerEditPanel productPickerEditPanel;
    public ProductPickerEditPanel productVariantPickerEditPanel;
    public JGenericComboBoxSelectionModel<String> isVirtualComboBoxModel = null;
    public JGenericComboBoxSelectionModel<String> isVariantComboBoxModel = null;

    /**
     * Creates new form CreateProductIdPanel
     */
    public CreateProductIdPanel(ControllerOptions pOptions) {
        initComponents();

        ControllerOptions partyOptions = new ControllerOptions(pOptions);
        partyOptions.put("showData", true);
        panelParentCategoryPicker = new CategoryPickerEditPanel(partyOptions);
        panelDepartmentId.setLayout(new BorderLayout());
        panelDepartmentId.add(BorderLayout.CENTER, panelParentCategoryPicker);

        ControllerOptions productVariantOptions = new ControllerOptions(pOptions);
        productVariantOptions.put("virualOnly", "Y");
        productVariantPickerEditPanel = new ProductPickerEditPanel(productVariantOptions);
        panelVariantProductId.setLayout(new BorderLayout());
        panelVariantProductId.add(BorderLayout.CENTER, productVariantPickerEditPanel);
        
        if (pOptions.contains("treePath")) {
            treePath = (Map<Integer, TreeNode>) pOptions.get("treePath");
            if (treePath != null) {
                if (treePath.size() > 1) {
                    GenericValue genVal = PosProductHelper.getProductCategory(treePath.get(Integer.valueOf(1))._id, ControllerOptions.getSession().getDelegator());
                    panelParentCategoryPicker.setGenericValue(genVal);

                    int size = treePath.size();
                    TreeNode treeNode = treePath.get(Integer.valueOf(size - 1));
                    if (treeNode != null) {
                        if (treeNode instanceof ProductTreeNode) {
                            ProductTreeNode productTreeNode = (ProductTreeNode) treeNode;
                            productVariantPickerEditPanel.textIdField.setText(treeNode._id);
                        }
                    }
                }
            }
        }
        ControllerOptions brandOptions = new ControllerOptions(pOptions);
        brandOptions.put("showName", true);
        panelBrandCategoryPicker = new CategoryPickerEditPanel(brandOptions);
        panelBrandId.setLayout(new BorderLayout());
        panelBrandId.add(BorderLayout.CENTER, panelBrandCategoryPicker);



        //order selection button
        ActionListener productIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("1", module);
                if (e.getSource() instanceof ProductIdMaintainVerifyValidator) {
                    Debug.logInfo("2", module);
                    ProductIdMaintainVerifyValidator validator = (ProductIdMaintainVerifyValidator) e.getSource();
                    if (validator.isProductIdExists()) {
                        Debug.logInfo("3", module);
                        String productId = validator.getField().getText();
                        ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());
                        Debug.logInfo("productId : " + productId, module);
                        if (productComposite != null) {
                            Debug.logInfo("4", module);
                            txtName.setText(productComposite.getProduct().getproductName());
                            panelBrandCategoryPicker.textIdField.setText(productComposite.getProduct().getbrandName());
                        }
                    }
                }
            }
        };

        ProductIdMaintainVerifyValidator prodValidator = new ProductIdMaintainVerifyValidator(productVariantPickerEditPanel.textIdField, ControllerOptions.getSession());
        prodValidator.addActionListener(productIdTextChangeAction);
        productVariantPickerEditPanel.textIdField.setInputVerifier(prodValidator);

        ControllerOptions productOptions = new ControllerOptions(pOptions);
        productPickerEditPanel = new ProductPickerEditPanel(productOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, productPickerEditPanel);

        isVirtualComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVirtualProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        isVariantComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVariantProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));

        try {
            String str = YesNoConditionSelectSingleton.getString("N");
            isVirtualComboBoxModel.setSelectedItem(str);
            isVariantComboBoxModel.setSelectedItem(str);
            //isVariantComboBoxModel.jComboBox.setEnabled(false);
            productVariantPickerEditPanel.textIdField.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(CreateProductIdPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        isVirtualComboBoxModel.jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isVirtualComboBoxModel.getSelectedItem().equals(YesNoConditionSelectSingleton.Y)) {
                    panelVariantProdCont.setEnabled(false);
                    panelVariantProductId.setEnabled(false);
                    isVariantComboBoxModel.jComboBox.setEnabled(false);
                    productVariantPickerEditPanel.textIdField.setEnabled(false);
                } else {
                    panelVariantProdCont.setEnabled(true);
                    panelVariantProductId.setEnabled(true);
                    isVariantComboBoxModel.jComboBox.setEnabled(true);
                    productVariantPickerEditPanel.textIdField.setEnabled(true);
                }
            }
        });

        isVariantComboBoxModel.jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isVariantComboBoxModel.getSelectedItem().equals(YesNoConditionSelectSingleton.Y)) {
                    panelVariantProductId.setEnabled(true);
                    productVariantPickerEditPanel.textIdField.setEnabled(true);
                } else {
                    panelVariantProductId.setEnabled(false);
                    productVariantPickerEditPanel.textIdField.setEnabled(false);
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panelName = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panelBrandId = new javax.swing.JPanel();
        btnGenerate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        panelProductId = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        panelDepartmentId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelVirtualProd = new javax.swing.JPanel();
        panelVariantProdCont = new javax.swing.JPanel();
        panelVariantProd = new javax.swing.JPanel();
        panelVariantProductId = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setText("Name:");

        panelName.setMinimumSize(new java.awt.Dimension(200, 24));
        panelName.setPreferredSize(new java.awt.Dimension(200, 24));
        panelName.setLayout(new java.awt.BorderLayout());
        panelName.add(txtName, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Brand Id:");

        javax.swing.GroupLayout panelBrandIdLayout = new javax.swing.GroupLayout(panelBrandId);
        panelBrandId.setLayout(panelBrandIdLayout);
        panelBrandIdLayout.setHorizontalGroup(
            panelBrandIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelBrandIdLayout.setVerticalGroup(
            panelBrandIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        jLabel3.setText("Product Id:");

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        btnCancel.setText("Cancel");

        btnOk.setText("Ok");

        jLabel4.setText("Department Id:");

        panelDepartmentId.setMinimumSize(new java.awt.Dimension(200, 24));
        panelDepartmentId.setPreferredSize(new java.awt.Dimension(200, 24));

        javax.swing.GroupLayout panelDepartmentIdLayout = new javax.swing.GroupLayout(panelDepartmentId);
        panelDepartmentId.setLayout(panelDepartmentIdLayout);
        panelDepartmentIdLayout.setHorizontalGroup(
            panelDepartmentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelDepartmentIdLayout.setVerticalGroup(
            panelDepartmentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jLabel5.setText("Is Virtual:");

        javax.swing.GroupLayout panelVirtualProdLayout = new javax.swing.GroupLayout(panelVirtualProd);
        panelVirtualProd.setLayout(panelVirtualProdLayout);
        panelVirtualProdLayout.setHorizontalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelVirtualProdLayout.setVerticalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        panelVariantProdCont.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelVariantProdLayout = new javax.swing.GroupLayout(panelVariantProd);
        panelVariantProd.setLayout(panelVariantProdLayout);
        panelVariantProdLayout.setHorizontalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelVariantProdLayout.setVerticalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        panelVariantProdCont.add(panelVariantProd, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout panelVariantProductIdLayout = new javax.swing.GroupLayout(panelVariantProductId);
        panelVariantProductId.setLayout(panelVariantProductIdLayout);
        panelVariantProductIdLayout.setHorizontalGroup(
            panelVariantProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelVariantProductIdLayout.setVerticalGroup(
            panelVariantProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        panelVariantProdCont.add(panelVariantProductId, java.awt.BorderLayout.CENTER);

        jLabel6.setText("Is Variant:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelVirtualProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelVariantProdCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDepartmentId, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBrandId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(btnGenerate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(12, 12, 12))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnOk});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelBrandId, panelDepartmentId, panelName, panelProductId, panelVariantProdCont, panelVirtualProd});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(panelVirtualProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(panelVariantProdCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(panelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDepartmentId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelBrandId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel)
                            .addComponent(btnOk))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerate)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelBrandId, panelDepartmentId, panelName, panelProductId});

    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        boolean isVirtual = false;
        if (isVirtualComboBoxModel.getSelectedItem().equals(YesNoConditionSelectSingleton.Y)) {
            isVirtual = true;
        }
        String prodCode = generateProductCode(panelParentCategoryPicker.textIdField.getText(), panelBrandCategoryPicker.textIdField.getText(), txtName.getText(), isVirtual);
        productPickerEditPanel.textIdField.setText(prodCode);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel panelBrandId;
    private javax.swing.JPanel panelDepartmentId;
    private javax.swing.JPanel panelName;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelVariantProd;
    private javax.swing.JPanel panelVariantProdCont;
    private javax.swing.JPanel panelVariantProductId;
    private javax.swing.JPanel panelVirtualProd;
    public javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return this.btnOk;
    }

    @Override
    public JButton getCancelButton() {
        return this.btnCancel;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    String generateProductCode(String departmentId, String brandName, String name, boolean isVitual) {

        String productCode = departmentId;
        Debug.logInfo(" brandName : " + brandName, module);

        if (UtilValidate.isNotEmpty(departmentId) && UtilValidate.isNotEmpty(brandName) && UtilValidate.isNotEmpty(name)) {

            String n = name.toUpperCase().substring(0, 1);
            String b = brandName.toUpperCase().substring(0, 1);

            String format = String.format("%%0%dd", 4);
            String virtualString = isVitual ? "V" : "";

            Integer code = new Integer(departmentId);
            while (true) {
                String codeVal = String.format(format, code);
                productCode = n + b + codeVal + virtualString;
                Debug.logInfo(" productCode : " + productCode, module);
                GenericValue genVal = PosProductHelper.getProduct(productCode, ControllerOptions.getSession().getDelegator());
                if (genVal == null) {
                    break;
                }
                code += 1;
            }

        }
        return productCode;
    }

    Map<Integer, TreeNode> treePath;
    //public void setProductTreePath(Map<Integer, TreeNode> treePath) {
    //    this.treePath = treePath;
    //}
}
