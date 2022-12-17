/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.invoice.FindInvoiceListPanel;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.InventoryLotIdMethodEnumSingleton;
import org.ofbiz.ordermax.product.RequirmentMethodEnumSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelVirtualProduct extends javax.swing.JPanel {

    //status type method
    private JGenericComboBoxSelectionModel<String> isVirtualComboBoxModel = null;
    private JGenericComboBoxSelectionModel<String> isVariantComboBoxModel = null;
    private JGenericComboBoxSelectionModel<String> vvmComboBoxModel = null;
    private JGenericComboBoxSelectionModel<ProductCategory> primaryCategoryComboBoxModel = null;

    private JGenericComboBoxSelectionModel<String> notAvaComboBoxModel = null;
    private JGenericComboBoxSelectionModel<String> requireInvComboBoxModel = null;
    private JGenericComboBoxSelectionModel<String> requireInvEnumComboBoxModel = null; //new ListComboBoxModel<String>();
    private JGenericComboBoxSelectionModel<String> lotIdEnumComboBoxModel = null;
    static final ProductCategory nullProductCategory = new ProductCategory();

    /**
     * Creates new form PanelVirtualProduct
     */
    public PanelVirtualProduct() {
        initComponents();
        ComponentBorder.loweredBevelBorder(panelVirtualProduct, "Virtual Product");
        //ComponentBorder.loweredBevelBorder(panelCategory, "Primary Category");

        isVirtualComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVirtualProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        isVariantComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVariantProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        vvmComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVariantMetod, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        primaryCategoryComboBoxModel = new JGenericComboBoxSelectionModel<ProductCategory>(panelPrimaryCat, ProductCategorySingleton.getValueList());
//        nullProductCategory.setproductCategoryId(null);
//        nullProductCategory.setdescription("None");
//        primaryCategoryComboBoxModel.addItem(nullProductCategory);

        /*        try {
         isVariantComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("N"));
         } catch (Exception ex) {
         Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        ComponentBorder.loweredBevelBorder(panelInventory, "Inventory");
        notAvaComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelDiscNotAvail, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        requireInvComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelRequireInv, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        requireInvEnumComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelReqMethEnum, RequirmentMethodEnumSingleton.getValueList(), new StringListCellRenderer(false));
        lotIdEnumComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelLotId, InventoryLotIdMethodEnumSingleton.getValueList(), new StringListCellRenderer(false));
    }
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void clearDialogFields() {
        try {
            isVirtualComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("N"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            isVariantComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("N"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            requireInvComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("Y"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            requireInvEnumComboBoxModel.setSelectedItem(RequirmentMethodEnumSingleton.getString("PRODRQM_AUTO"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            lotIdEnumComboBoxModel.setSelectedItem(InventoryLotIdMethodEnumSingleton.getString("Allowed"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            notAvaComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("N"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        inventoryMessageTextField.setText("");
        this.repaint();
    }

    public void setDialogField() {
        try {
            isVirtualComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString(productComposite.getProduct().getisVirtual()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            isVariantComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString(productComposite.getProduct().getisVariant()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Debug.logInfo("productComposite.getProduct().getprimaryProductCategoryId(): " + productComposite.getProduct().getprimaryProductCategoryId(), TOOL_TIP_TEXT_KEY);
            if (UtilValidate.isNotEmpty(productComposite.getProduct().getprimaryProductCategoryId())) {
                ProductCategory prod = ProductCategorySingleton.getProductCategory(productComposite.getProduct().getprimaryProductCategoryId());
                 Debug.logInfo("prod: " + prod.getProductCategoryId(), TOOL_TIP_TEXT_KEY);
                primaryCategoryComboBoxModel.setSelectedItem(ProductCategorySingleton.getProductCategory(productComposite.getProduct().getprimaryProductCategoryId()));
            } else {
                primaryCategoryComboBoxModel.setSelectedItem(nullProductCategory);
            }
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        vvmComboBoxModel = new ListComboBoxModel<String>();
//    primaryCategoryComboBoxModel = new ListComboBoxModel<String>();
        try {
            if (UtilValidate.isNotEmpty(productComposite.getProduct().getsalesDiscWhenNotAvail())) {
                notAvaComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString(productComposite.getProduct().getsalesDiscWhenNotAvail()));
            } else {
                notAvaComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.N);
            }
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(productComposite.getProduct().getrequireInventory())) {
                requireInvComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString(productComposite.getProduct().getrequireInventory()));
            } else {
                requireInvComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.N);
            }
        } catch (Exception ex) {
            //          Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            requireInvEnumComboBoxModel.setSelectedItem(RequirmentMethodEnumSingleton.getString(productComposite.getProduct().getrequirementMethodEnumId()));
        } catch (Exception ex) {
//            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            lotIdEnumComboBoxModel.setSelectedItem(InventoryLotIdMethodEnumSingleton.getString(productComposite.getProduct().getlotIdFilledIn()));
        } catch (Exception ex) {
//            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        inventoryMessageTextField.setText(productComposite.getProduct().getinventoryMessage());
        this.repaint();
    }

    public void getDialogField() {

        try {
            productComposite.getProduct().setisVirtual(YesNoConditionSelectSingleton.getKeyFromDisplayName(isVirtualComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelVirtualProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            productComposite.getProduct().setisVariant(YesNoConditionSelectSingleton.getKeyFromDisplayName(isVariantComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelVirtualProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (primaryCategoryComboBoxModel.getSelectedItem() != null) {
                productComposite.getProduct().setprimaryProductCategoryId(primaryCategoryComboBoxModel.getSelectedItem().getProductCategoryId());
            }
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        productComposite.getProduct().setisVariant(YesNoConditionSelectSingleton.getString(isVariantComboBoxModel.getSelectedItem()));

//        vvmComboBoxModel = new ListComboBoxModel<String>();
//    primaryCategoryComboBoxModel = new ListComboBoxModel<String>();
        try {
            productComposite.getProduct().setsalesDiscWhenNotAvail(YesNoConditionSelectSingleton.getKeyFromDisplayName(notAvaComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productComposite.getProduct().setrequireInventory(YesNoConditionSelectSingleton.getKeyFromDisplayName(requireInvComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productComposite.getProduct().setrequirementMethodEnumId(RequirmentMethodEnumSingleton.getInventoryTypeFromDesc(requireInvEnumComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productComposite.getProduct().setlotIdFilledIn(InventoryLotIdMethodEnumSingleton.getString(lotIdEnumComboBoxModel.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        productComposite.getProduct().setinventoryMessage(inventoryMessageTextField.getText());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProduct = new javax.swing.JPanel();
        panelVirtualProduct = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelVirtualProd = new javax.swing.JPanel();
        panelVariantProd = new javax.swing.JPanel();
        panelPrimaryCat = new javax.swing.JPanel();
        panelVariantMetod = new javax.swing.JPanel();
        panelInventory = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panelReqMethEnum = new javax.swing.JPanel();
        panelDiscNotAvail = new javax.swing.JPanel();
        panelRequireInv = new javax.swing.JPanel();
        panelLotId = new javax.swing.JPanel();
        inventoryMessageTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(1, 0));

        panelProduct.setPreferredSize(new java.awt.Dimension(749, 42));
        panelProduct.setLayout(new java.awt.BorderLayout());

        panelVirtualProduct.setPreferredSize(new java.awt.Dimension(823, 100));

        jLabel11.setText("Is VIRTUAL Product ?");

        jLabel13.setText("Virtual Variant Method");

        jLabel12.setText(" \tIs VARIANT Product ?");

        jLabel14.setText("Primary Category");

        javax.swing.GroupLayout panelVirtualProdLayout = new javax.swing.GroupLayout(panelVirtualProd);
        panelVirtualProd.setLayout(panelVirtualProdLayout);
        panelVirtualProdLayout.setHorizontalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelVirtualProdLayout.setVerticalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelVariantProdLayout = new javax.swing.GroupLayout(panelVariantProd);
        panelVariantProd.setLayout(panelVariantProdLayout);
        panelVariantProdLayout.setHorizontalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelVariantProdLayout.setVerticalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPrimaryCatLayout = new javax.swing.GroupLayout(panelPrimaryCat);
        panelPrimaryCat.setLayout(panelPrimaryCatLayout);
        panelPrimaryCatLayout.setHorizontalGroup(
            panelPrimaryCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelPrimaryCatLayout.setVerticalGroup(
            panelPrimaryCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelVariantMetodLayout = new javax.swing.GroupLayout(panelVariantMetod);
        panelVariantMetod.setLayout(panelVariantMetodLayout);
        panelVariantMetodLayout.setHorizontalGroup(
            panelVariantMetodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelVariantMetodLayout.setVerticalGroup(
            panelVariantMetodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelVirtualProductLayout = new javax.swing.GroupLayout(panelVirtualProduct);
        panelVirtualProduct.setLayout(panelVirtualProductLayout);
        panelVirtualProductLayout.setHorizontalGroup(
            panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVirtualProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelVirtualProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelVariantProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPrimaryCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelVariantMetod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        panelVirtualProductLayout.setVerticalGroup(
            panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVirtualProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelVirtualProductLayout.createSequentialGroup()
                        .addComponent(panelVirtualProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelVariantProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVirtualProductLayout.createSequentialGroup()
                        .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(panelPrimaryCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelVirtualProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addComponent(panelVariantMetod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(36, 36, 36))
        );

        panelProduct.add(panelVirtualProduct, java.awt.BorderLayout.PAGE_START);

        panelInventory.setPreferredSize(new java.awt.Dimension(150, 250));

        jLabel7.setText("Disc. When Inv. Not Avail?");

        jLabel8.setText("Inventory Message");

        jLabel9.setText("Requirement Method Enum Id");

        jLabel10.setText("Require Inventory");

        jLabel15.setText("Lot Id");

        javax.swing.GroupLayout panelReqMethEnumLayout = new javax.swing.GroupLayout(panelReqMethEnum);
        panelReqMethEnum.setLayout(panelReqMethEnumLayout);
        panelReqMethEnumLayout.setHorizontalGroup(
            panelReqMethEnumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );
        panelReqMethEnumLayout.setVerticalGroup(
            panelReqMethEnumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDiscNotAvailLayout = new javax.swing.GroupLayout(panelDiscNotAvail);
        panelDiscNotAvail.setLayout(panelDiscNotAvailLayout);
        panelDiscNotAvailLayout.setHorizontalGroup(
            panelDiscNotAvailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );
        panelDiscNotAvailLayout.setVerticalGroup(
            panelDiscNotAvailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRequireInvLayout = new javax.swing.GroupLayout(panelRequireInv);
        panelRequireInv.setLayout(panelRequireInvLayout);
        panelRequireInvLayout.setHorizontalGroup(
            panelRequireInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );
        panelRequireInvLayout.setVerticalGroup(
            panelRequireInvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLotIdLayout = new javax.swing.GroupLayout(panelLotId);
        panelLotId.setLayout(panelLotIdLayout);
        panelLotIdLayout.setHorizontalGroup(
            panelLotIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );
        panelLotIdLayout.setVerticalGroup(
            panelLotIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDiscNotAvail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReqMethEnum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRequireInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLotId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inventoryMessageTextField))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelDiscNotAvail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(panelReqMethEnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelRequireInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(inventoryMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(panelLotId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
        );

        panelProduct.add(panelInventory, java.awt.BorderLayout.CENTER);

        add(panelProduct);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inventoryMessageTextField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelDiscNotAvail;
    private javax.swing.JPanel panelInventory;
    private javax.swing.JPanel panelLotId;
    private javax.swing.JPanel panelPrimaryCat;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JPanel panelReqMethEnum;
    private javax.swing.JPanel panelRequireInv;
    private javax.swing.JPanel panelVariantMetod;
    private javax.swing.JPanel panelVariantProd;
    private javax.swing.JPanel panelVirtualProd;
    private javax.swing.JPanel panelVirtualProduct;
    // End of variables declaration//GEN-END:variables
}
