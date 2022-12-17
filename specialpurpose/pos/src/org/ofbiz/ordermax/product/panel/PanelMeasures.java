/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.UomListCellRenderer;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.payment.UOMSingleton;
import org.ofbiz.ordermax.product.UomLenghtSingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;
import org.ofbiz.ordermax.product.UomWeightSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PanelMeasures extends javax.swing.JPanel {

    private JGenericComboBoxSelectionModel<Uom> widthUomModel = null;
    private JGenericComboBoxSelectionModel<Uom> heightUomModel = null;
    private JGenericComboBoxSelectionModel<Uom> depthUomModel = null;
    private JGenericComboBoxSelectionModel<Uom> diameterUomModel = null;
    private JGenericComboBoxSelectionModel<Uom> weightUomId = null;
    private JGenericComboBoxSelectionModel<Uom> quantityUomId = null;

    /**
     * Creates new form PanelMeasures
     */
    public PanelMeasures() {
        initComponents();
        ComponentBorder.loweredBevelBorder(panelMeasure, "Measure");
        /*
         uomLengthListModel.addAll(UomLenghtSingleton.getValueList());
         uomLengthComboBoxModel.setListModel(uomLengthListModel);
         heightUomIdTextField.setModel(uomLengthComboBoxModel);
         uomLengthSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         uomLengthListModelSelection.setListModels(uomLengthListModel, uomLengthSelectionModel);
         uomLengthComboBoxModel.setListSelectionModel(uomLengthSelectionModel);
         UomListCellRenderer uomLengthListCellRenderer = new UomListCellRenderer(true);
         heightUomIdTextField.setRenderer(uomLengthListCellRenderer);
         */

        widthUomModel = new JGenericComboBoxSelectionModel<Uom>(panelWidth,UomLenghtSingleton.getValueList());
        
        heightUomModel = new JGenericComboBoxSelectionModel<Uom>(panelHeight, UomLenghtSingleton.getValueList());
        depthUomModel = new JGenericComboBoxSelectionModel<Uom>(panelDepth , UomLenghtSingleton.getValueList());
        diameterUomModel = new JGenericComboBoxSelectionModel<Uom>(panelDiameter , UomLenghtSingleton.getValueList());
        weightUomId = new JGenericComboBoxSelectionModel<Uom>(panelWeight, UomWeightSingleton.getValueList());
        quantityUomId = new JGenericComboBoxSelectionModel<Uom>(panelQuantity, UomQuantitySingleton.getValueList());

    }
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void clearDialogFields() {
        quantityIncludedTextField.setText("");
        productWeightTextField.setText("");
        productDepthTextField.setText("");
        productHeightTextField.setText("");
        productDiameterTextField.setText("");
        productWidthTextField.setText("");
        shippingHeightTextField.setText("");
        shippingWidthTextField.setText("");
        shippingDepthTextField.setText("");
        weightTextField.setText("");

    }

    public void setDialogField() {
        Product product = productComposite.getProduct();

        quantityIncludedTextField.setText(OrderMaxUtility.getValidString(product.getquantityIncluded()));
        productWeightTextField.setText(OrderMaxUtility.getValidString(product.getproductWeight()));
        productDepthTextField.setText(OrderMaxUtility.getValidString(product.getproductDepth()));
        productHeightTextField.setText(OrderMaxUtility.getValidBigDecimal(product.getproductHeight()));
        productDiameterTextField.setText(OrderMaxUtility.getValidString(product.getproductDiameter()));
        productWidthTextField.setText(OrderMaxUtility.getValidString(product.getproductWidth()));
        try {
            widthUomModel.setSelectedItem(UomLenghtSingleton.getUom(product.getwidthUomId()));
        } catch (Exception ex) {
//            Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            depthUomModel.setSelectedItem(UomLenghtSingleton.getUom(product.getdepthUomId()));
        } catch (Exception ex) {
            //          Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            weightUomId.setSelectedItem(UomWeightSingleton.getUom(product.getweightUomId()));
        } catch (Exception ex) {
            //        Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            heightUomModel.setSelectedItem(UomLenghtSingleton.getUom(product.getheightUomId()));
        } catch (Exception ex) {
            //      Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            diameterUomModel.setSelectedItem(UomLenghtSingleton.getUom(product.getdiameterUomId()));
        } catch (Exception ex) {
            //    Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            quantityUomId.setSelectedItem(UomQuantitySingleton.getUom(product.getquantityUomId()));
        } catch (Exception ex) {
            //  Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }

        shippingHeightTextField.setText(OrderMaxUtility.getValidString(product.getshippingHeight()));
        shippingWidthTextField.setText(OrderMaxUtility.getValidString(product.getshippingWidth()));
        shippingDepthTextField.setText(OrderMaxUtility.getValidString(product.getshippingDepth()));
        weightTextField.setText(OrderMaxUtility.getValidString(product.getweight()));

    }

    public void getDialogField() {
        Product product = productComposite.getProduct();

        product.setquantityIncluded(OrderMaxUtility.getValidBigDecimal(quantityIncludedTextField.getText()));
        product.setproductWeight(OrderMaxUtility.getValidBigDecimal(productWeightTextField.getText()));
        product.setproductDepth(OrderMaxUtility.getValidBigDecimal(productDepthTextField.getText()));
        product.setproductHeight(OrderMaxUtility.getValidBigDecimal(productHeightTextField.getText()));
        product.setproductDiameter(OrderMaxUtility.getValidBigDecimal(productDiameterTextField.getText()));
        product.setproductWidth(OrderMaxUtility.getValidBigDecimal(productWidthTextField.getText()));
        try {
            product.setwidthUomId(widthUomModel.getSelectedItem().getuomId());
        } catch (Exception ex) {
            //           Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            product.setdepthUomId(depthUomModel.getSelectedItem().getuomId());

        } catch (Exception ex) {
            //           Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            product.setweightUomId(weightUomId.getSelectedItem().getuomId());
        } catch (Exception ex) {
            //           Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            product.setheightUomId(heightUomModel.getSelectedItem().getuomId());

        } catch (Exception ex) {
//            Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            product.setdiameterUomId(diameterUomModel.getSelectedItem().getuomId());

        } catch (Exception ex) {
            //           Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            product.setquantityUomId(quantityUomId.getSelectedItem().getuomId());
        } catch (Exception ex) {
            //           Logger.getLogger(PanelMeasures.class.getName()).log(Level.SEVERE, null, ex);
        }

        product.setshippingHeight(OrderMaxUtility.getValidBigDecimal(shippingHeightTextField.getText()));
        product.setshippingWidth(OrderMaxUtility.getValidBigDecimal(shippingWidthTextField.getText()));
        product.setshippingDepth(OrderMaxUtility.getValidBigDecimal(shippingDepthTextField.getText()));
        product.setweight(OrderMaxUtility.getValidBigDecimal(weightTextField.getText()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMeasure = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        productHeightTextField = new javax.swing.JTextField();
        productWidthTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        productDepthTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        productDiameterTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        productWeightTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        quantityIncludedTextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        shippingHeightTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        shippingWidthTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        shippingDepthTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        weightTextField = new javax.swing.JTextField();
        panelWeight = new javax.swing.JPanel();
        panelHeight = new javax.swing.JPanel();
        panelWidth = new javax.swing.JPanel();
        panelDepth = new javax.swing.JPanel();
        panelDiameter = new javax.swing.JPanel();
        panelQuantity = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        panelMeasure.setPreferredSize(new java.awt.Dimension(1129, 300));

        jLabel2.setText("Product Height");

        jLabel3.setText("Product Width");

        jLabel4.setText("Product Depth");

        jLabel5.setText("Product Diameter");

        productDiameterTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productDiameterTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("Product Weight");

        jLabel7.setText("Quantity Included");

        jLabel23.setText("Height Uom Id");

        jLabel25.setText("Width Uom Id");

        jLabel26.setText("Depth Uom Id");

        jLabel24.setText("Diameter Uom Id");

        jLabel27.setText("Weight Uom Id");

        jLabel28.setText("Quantity UomId");

        jLabel8.setText("Shipping Height");

        jLabel9.setText("Shipping Width");

        jLabel10.setText("Shipping Depth");

        jLabel11.setText("Shipping Weight");

        weightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelWeightLayout = new javax.swing.GroupLayout(panelWeight);
        panelWeight.setLayout(panelWeightLayout);
        panelWeightLayout.setHorizontalGroup(
            panelWeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelWeightLayout.setVerticalGroup(
            panelWeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelHeightLayout = new javax.swing.GroupLayout(panelHeight);
        panelHeight.setLayout(panelHeightLayout);
        panelHeightLayout.setHorizontalGroup(
            panelHeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelHeightLayout.setVerticalGroup(
            panelHeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelWidthLayout = new javax.swing.GroupLayout(panelWidth);
        panelWidth.setLayout(panelWidthLayout);
        panelWidthLayout.setHorizontalGroup(
            panelWidthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelWidthLayout.setVerticalGroup(
            panelWidthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDepthLayout = new javax.swing.GroupLayout(panelDepth);
        panelDepth.setLayout(panelDepthLayout);
        panelDepthLayout.setHorizontalGroup(
            panelDepthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelDepthLayout.setVerticalGroup(
            panelDepthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDiameterLayout = new javax.swing.GroupLayout(panelDiameter);
        panelDiameter.setLayout(panelDiameterLayout);
        panelDiameterLayout.setHorizontalGroup(
            panelDiameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelDiameterLayout.setVerticalGroup(
            panelDiameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelQuantityLayout = new javax.swing.GroupLayout(panelQuantity);
        panelQuantity.setLayout(panelQuantityLayout);
        panelQuantityLayout.setHorizontalGroup(
            panelQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        panelQuantityLayout.setVerticalGroup(
            panelQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMeasureLayout = new javax.swing.GroupLayout(panelMeasure);
        panelMeasure.setLayout(panelMeasureLayout);
        panelMeasureLayout.setHorizontalGroup(
            panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMeasureLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productHeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productWidthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productDepthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productDiameterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityIncludedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMeasureLayout.createSequentialGroup()
                        .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelMeasureLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(weightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelMeasureLayout.createSequentialGroup()
                                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(shippingHeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(shippingDepthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(shippingWidthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(173, Short.MAX_VALUE))
                    .addGroup(panelMeasureLayout.createSequentialGroup()
                        .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelDiameter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelMeasureLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelDepth, panelDiameter, panelHeight, panelQuantity, panelWeight, panelWidth});

        panelMeasureLayout.setVerticalGroup(
            panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMeasureLayout.createSequentialGroup()
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(productWeightTextField)
                    .addComponent(jLabel27)
                    .addComponent(jLabel11)
                    .addComponent(weightTextField)
                    .addComponent(panelWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(productHeightTextField)
                    .addComponent(jLabel23)
                    .addComponent(jLabel8)
                    .addComponent(shippingHeightTextField)
                    .addComponent(panelHeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(productWidthTextField)
                    .addComponent(jLabel25)
                    .addComponent(jLabel9)
                    .addComponent(shippingWidthTextField)
                    .addComponent(panelWidth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(productDepthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel10)
                    .addComponent(shippingDepthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(productDiameterTextField)
                    .addComponent(jLabel24)
                    .addComponent(panelDiameter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMeasureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(quantityIncludedTextField)
                    .addComponent(jLabel28)
                    .addComponent(panelQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelMeasureLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelDepth, panelDiameter, panelHeight, panelQuantity, panelWeight, panelWidth});

        add(panelMeasure);
    }// </editor-fold>//GEN-END:initComponents

    private void productDiameterTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productDiameterTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productDiameterTextFieldActionPerformed

    private void weightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelDepth;
    private javax.swing.JPanel panelDiameter;
    private javax.swing.JPanel panelHeight;
    private javax.swing.JPanel panelMeasure;
    private javax.swing.JPanel panelQuantity;
    private javax.swing.JPanel panelWeight;
    private javax.swing.JPanel panelWidth;
    private javax.swing.JTextField productDepthTextField;
    private javax.swing.JTextField productDiameterTextField;
    private javax.swing.JTextField productHeightTextField;
    private javax.swing.JTextField productWeightTextField;
    private javax.swing.JTextField productWidthTextField;
    private javax.swing.JTextField quantityIncludedTextField;
    private javax.swing.JTextField shippingDepthTextField;
    private javax.swing.JTextField shippingHeightTextField;
    private javax.swing.JTextField shippingWidthTextField;
    private javax.swing.JTextField weightTextField;
    // End of variables declaration//GEN-END:variables
}
