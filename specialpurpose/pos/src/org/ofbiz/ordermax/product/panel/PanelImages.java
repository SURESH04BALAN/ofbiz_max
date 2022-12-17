/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.io.File;
import javax.swing.JOptionPane;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import static org.ofbiz.guiapp.xui.XuiSession.module;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PanelImages extends javax.swing.JPanel {

    String productId;
//    protected CopyWebProductImagePanel copyWebProductImagePanel = null;

    /**
     * Creates new form PanelImages
     */
    public PanelImages() {
        initComponents();
    }
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void clearDialogFields() {
    }

    public void setDialogField() {
        
        Product product = productComposite.getProduct();
        productId = product.getproductId();
        mediumImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getmediumImageUrl()));
        smallImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getsmallImageUrl()));
        originalImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getoriginalImageUrl()));

        largeImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getlargeImageUrl()));
        detailImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getdetailImageUrl()));
        if(UtilValidate.isNotEmpty(detailImageUrlTextField.getText())){
            detailImageUrlTextField.requestFocus();
        }
        else if(UtilValidate.isNotEmpty(largeImageUrlTextField.getText())){
            largeImageUrlTextField.requestFocus();
        }
        else if(UtilValidate.isNotEmpty(originalImageUrlTextField.getText())){
            originalImageUrlTextField.requestFocus();
        } 
        else if(UtilValidate.isNotEmpty(mediumImageUrlTextField.getText())){
            mediumImageUrlTextField.requestFocus();
        }        
        else{
            smallImageUrlTextField.requestFocus();
        }          
        /*           
         reserv2ndPPPercTextField.setText(OrderMaxUtility.getValidString(product.getreserv2ndPPPerc()));
            
         defaultShipmentBoxTypeIdTextField.setText(OrderMaxUtility.getValidString(product.getdefaultShipmentBoxTypeId()));
            
         chargeShippingTextField.setText(OrderMaxUtility.getValidString(product.getchargeShipping()));
            
            
         configIdTextField.setText(OrderMaxUtility.getValidString(product.getconfigId()));
            
         requirementMethodEnumIdTextField.setText(OrderMaxUtility.getValidString(product.getrequirementMethodEnumId()));
            
         inShippingBoxTextField.setText(OrderMaxUtility.getValidString(product.getinShippingBox()));
            
         productRatingTextField.setText(OrderMaxUtility.getValidString(product.getproductRating()));
            
            
         productNameTextField.setText(OrderMaxUtility.getValidString(product.getproductName()));
            
         releaseDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getreleaseDate()));
            
         fixedAmountTextField.setText(OrderMaxUtility.getValidString(product.getfixedAmount()));
            
         priceDetailTextTextField.setText(OrderMaxUtility.getValidString(product.getpriceDetailText()));
            
         originGeoIdTextField.setText(OrderMaxUtility.getValidString(product.getoriginGeoId()));
            
         inventoryMessageTextField.setText(OrderMaxUtility.getValidString(product.getinventoryMessage()));
            
         isVirtualTextField.setText(OrderMaxUtility.getValidString(product.getisVirtual()));
            
            

            
         salesDiscWhenNotAvailTextField.setText(OrderMaxUtility.getValidString(product.getsalesDiscWhenNotAvail()));
            
         primaryProductCategoryIdTextField.setText(OrderMaxUtility.getValidString(product.getprimaryProductCategoryId()));
            
         billOfMaterialLevelTextField.setText(OrderMaxUtility.getValidString(product.getbillOfMaterialLevel()));
            
         salesDiscontinuationDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getsalesDiscontinuationDate()));
            
         supportDiscontinuationDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getsupportDiscontinuationDate()));
            
            
         taxableTextField.setText(OrderMaxUtility.getValidString(product.gettaxable()));
            
         createdDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getcreatedDate()));
            

            
         isVariantTextField.setText(OrderMaxUtility.getValidString(product.getisVariant()));
            

            
         introductionDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getintroductionDate()));
            

            
         createdByUserLoginTextField.setText(OrderMaxUtility.getValidString(product.getcreatedByUserLogin()));
            

            
         requireInventoryTextField.setText(OrderMaxUtility.getValidString(product.getrequireInventory()));
                        
         reservNthPPPercTextField.setText(OrderMaxUtility.getValidString(product.getreservNthPPPerc()));
            
         ratingTypeEnumTextField.setText(OrderMaxUtility.getValidString(product.getratingTypeEnum()));
            
         autoCreateKeywordsTextField.setText(OrderMaxUtility.getValidString(product.getautoCreateKeywords()));
            
         longDescriptionTextField.setText(OrderMaxUtility.getValidString(product.getlongDescription()));
            
            
         virtualVariantMethodEnumTextField.setText(OrderMaxUtility.getValidString(product.getvirtualVariantMethodEnum()));
            
            
         piecesIncludedTextField.setText(OrderMaxUtility.getValidString(product.getpiecesIncluded()));
            
         requireAmountTextField.setText(OrderMaxUtility.getValidString(product.getrequireAmount()));
            
         lastModifiedByUserLoginTextField.setText(OrderMaxUtility.getValidString(product.getlastModifiedByUserLogin()));
            

            
         includeInPromotionsTextField.setText(OrderMaxUtility.getValidString(product.getincludeInPromotions()));
            
         lastModifiedDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getlastModifiedDate()));
         orderDecimalQuantityTextField.setText(OrderMaxUtility.getValidString(product.getorderDecimalQuantity()));
            
         detailScreenTextField.setText(OrderMaxUtility.getValidString(product.getdetailScreen()));
            
         descriptionTextField.setText(OrderMaxUtility.getValidString(product.getdescription()));
            

            
         reservMaxPersonsTextField.setText(OrderMaxUtility.getValidString(product.getreservMaxPersons()));
            

            
         amountUomTypeIdTextField.setText(OrderMaxUtility.getValidString(product.getamountUomTypeId()));
            
         facilityIdTextField.setText(OrderMaxUtility.getValidString(product.getfacilityId()));
            
         returnableTextField.setText(OrderMaxUtility.getValidString(product.getreturnable()));
         */
    }

    public void getDialogField() {
        Product product = productComposite.getProduct();
        product.setmediumImageUrl(mediumImageUrlTextField.getText());
        product.setsmallImageUrl(smallImageUrlTextField.getText());
        product.setoriginalImageUrl(originalImageUrlTextField.getText());
        product.setlargeImageUrl(largeImageUrlTextField.getText());
        product.setdetailImageUrl(detailImageUrlTextField.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnFixImagePath = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClearImageStore = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        smallImageUrlTextField = new javax.swing.JTextField();
        btnSmallIcon = new javax.swing.JButton();
        btnMediumIcon = new javax.swing.JButton();
        mediumImageUrlTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnLargeIcon = new javax.swing.JButton();
        largeImageUrlTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnDetailIcon = new javax.swing.JButton();
        detailImageUrlTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        originalImageUrlTextField = new javax.swing.JTextField();
        btnOriginalIcon = new javax.swing.JButton();
        cbScaleImage = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        panelImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Image"));
        panelImage.setLayout(new java.awt.BorderLayout());

        lblImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.add(lblImage, java.awt.BorderLayout.CENTER);

        add(panelImage, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Images"));

        btnFixImagePath.setText("Fix Image Path");

        btnDelete.setText("Delete File");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClearImageStore.setText("Reload Images");
        btnClearImageStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearImageStoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFixImagePath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearImageStore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFixImagePath)
                    .addComponent(btnDelete)
                    .addComponent(btnClearImageStore))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setText("Small:");

        smallImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                smallImageUrlTextFieldFocusGained(evt);
            }
        });

        btnSmallIcon.setText("jButton1");
        btnSmallIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIconActionPerformed(evt);
            }
        });

        btnMediumIcon.setText("jButton1");
        btnMediumIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMediumIconActionPerformed(evt);
            }
        });

        mediumImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mediumImageUrlTextFieldFocusGained(evt);
            }
        });

        jLabel12.setText("Medium:");

        btnLargeIcon.setText("jButton1");
        btnLargeIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLargeIconActionPerformed(evt);
            }
        });

        largeImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                largeImageUrlTextFieldFocusGained(evt);
            }
        });

        jLabel13.setText("Large:");

        btnDetailIcon.setText("jButton1");
        btnDetailIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailIconActionPerformed(evt);
            }
        });

        detailImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detailImageUrlTextFieldFocusGained(evt);
            }
        });

        jLabel14.setText("Detail:");

        jLabel15.setText("Original:");

        originalImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originalImageUrlTextFieldFocusGained(evt);
            }
        });

        btnOriginalIcon.setText("jButton1");
        btnOriginalIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIconActionPerformed(evt);
            }
        });

        cbScaleImage.setSelected(true);
        cbScaleImage.setText("Scale Image");
        cbScaleImage.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(smallImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mediumImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(largeImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(detailImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSmallIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnLargeIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMediumIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnOriginalIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDetailIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbScaleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(smallImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmallIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(mediumImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMediumIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(largeImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLargeIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(detailImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetailIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbScaleImage)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void smallImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_smallImageUrlTextFieldFocusGained
        showSelectandFileImage(smallImageUrlTextField.getText());
    }//GEN-LAST:event_smallImageUrlTextFieldFocusGained

    private void btnSmallIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIconActionPerformed
        smallImageUrlTextField.setText(BaseHelper.selectandSetFileName("small", productId, 60, 60));
    }//GEN-LAST:event_btnSmallIconActionPerformed

    private void btnMediumIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMediumIconActionPerformed
        mediumImageUrlTextField.setText(BaseHelper.selectandSetFileName("small-60", productId, 48, 48));
    }//GEN-LAST:event_btnMediumIconActionPerformed

    private void mediumImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mediumImageUrlTextFieldFocusGained
        showSelectandFileImage(mediumImageUrlTextField.getText());
    }//GEN-LAST:event_mediumImageUrlTextFieldFocusGained

    private void btnLargeIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLargeIconActionPerformed
        largeImageUrlTextField.setText(BaseHelper.selectandSetFileName("large", productId, 240, 240));
    }//GEN-LAST:event_btnLargeIconActionPerformed

    private void largeImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_largeImageUrlTextFieldFocusGained
        showSelectandFileImage(largeImageUrlTextField.getText());
    }//GEN-LAST:event_largeImageUrlTextFieldFocusGained

    private void btnDetailIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailIconActionPerformed
        detailImageUrlTextField.setText(BaseHelper.selectandSetFileName("large-200", productId, 200, 200));        // TODO add your handling code here:
    }//GEN-LAST:event_btnDetailIconActionPerformed

    private void detailImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detailImageUrlTextFieldFocusGained
        showSelectandFileImage(detailImageUrlTextField.getText());
    }//GEN-LAST:event_detailImageUrlTextFieldFocusGained

    private void originalImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originalImageUrlTextFieldFocusGained
        showSelectandFileImage(originalImageUrlTextField.getText());
    }//GEN-LAST:event_originalImageUrlTextFieldFocusGained

    private void btnOriginalIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIconActionPerformed

        if (cbScaleImage.isSelected()) {
            File filePath = BaseHelper.getImageFilePath("original");

            originalImageUrlTextField.setText(BaseHelper.CopyImageSetFileName("original", filePath, productId, 0, 0));
            mediumImageUrlTextField.setText(BaseHelper.CopyImageSetFileName("small-60", filePath, productId, 60, 60));
            smallImageUrlTextField.setText(BaseHelper.CopyImageSetFileName("small", filePath, productId, 48, 48));
            largeImageUrlTextField.setText(BaseHelper.CopyImageSetFileName("large", filePath, productId, 240, 240));
            detailImageUrlTextField.setText(BaseHelper.CopyImageSetFileName("large-200", filePath, productId, 200, 200));        // TODO add your handling code here:
            showSelectandFileImage(originalImageUrlTextField.getText());

        } else {
            originalImageUrlTextField.setText(BaseHelper.selectandSetFileName("original", productId, 0, 0));
        }
    }//GEN-LAST:event_btnOriginalIconActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String fileDir = "/images/products/" + productId + "/";
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
    protected void showSelectandFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            lblImage.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(field));
 /*           if (UtilValidate.isNotEmpty(mediumImageUrlTextField.getText())) {
                ImageIcon icon = BaseHelper.getImage(mediumImageUrlTextField.getText());
                if (UtilValidate.isNotEmpty(icon)) {
//                    copyWebProductImagePanel.imageLabelSave.setIcon(icon);
                }
            } else if (UtilValidate.isNotEmpty(smallImageUrlTextField.getText())) {
//                copyWebProductImagePanel.imageLabelSave.setIcon(BaseHelper.getImage(smallImageUrlTextField.getText()));
                ImageIcon icon = BaseHelper.getImage(smallImageUrlTextField.getText());
                if (UtilValidate.isNotEmpty(icon)) {
//                    copyWebProductImagePanel.imageLabelSave.setIcon(icon);
                }
            }*/
        } else {
            lblImage.setIcon(null);
//            copyWebProductImagePanel.imageLabelSave.setIcon(null);
        }
    }

    private void btnClearImageStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearImageStoreActionPerformed
        BaseHelper.clearImageStore();
    }//GEN-LAST:event_btnClearImageStoreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearImageStore;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetailIcon;
    public javax.swing.JButton btnFixImagePath;
    private javax.swing.JButton btnLargeIcon;
    private javax.swing.JButton btnMediumIcon;
    private javax.swing.JButton btnOriginalIcon;
    private javax.swing.JButton btnSmallIcon;
    private javax.swing.JCheckBox cbScaleImage;
    private javax.swing.JTextField detailImageUrlTextField;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField largeImageUrlTextField;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTextField mediumImageUrlTextField;
    private javax.swing.JTextField originalImageUrlTextField;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTextField smallImageUrlTextField;
    // End of variables declaration//GEN-END:variables
}
