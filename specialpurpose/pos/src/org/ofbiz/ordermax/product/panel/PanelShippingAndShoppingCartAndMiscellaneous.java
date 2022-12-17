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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ShipmentBoxType;
import org.ofbiz.ordermax.entity.UomType;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.ShipmentBoxTypeSingleton;
import org.ofbiz.ordermax.product.UOMTypeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PanelShippingAndShoppingCartAndMiscellaneous extends javax.swing.JPanel {

    /*   final private ListAdapterListModel<UomType> uomTypeListModel = new ListAdapterListModel<UomType>();
     private ListComboBoxModel<UomType> uomTypeComboBoxModel = new ListComboBoxModel<UomType>();
     private ListSelectionModel selectionModel = new DefaultListSelectionModel();
     private ListModelSelection<UomType> listModelSelection = new ListModelSelection<UomType>();
     */
    private JGenericComboBoxSelectionModel<String> requireAmountComboBoxModel = null;//new ListComboBoxModel<String>();

    private JGenericComboBoxSelectionModel<String> allowOrderDecimalQuantity = null;
    private JGenericComboBoxSelectionModel<String> returnable = null;
    private JGenericComboBoxSelectionModel<String> includeInPromotions = null;

    private JGenericComboBoxSelectionModel<String> autoCreateKeywords = null;
    private JGenericComboBoxSelectionModel<String> inShippingBox = null;
    private JGenericComboBoxSelectionModel<ShipmentBoxType> defaultShipmentBoxType = null;
    private JGenericComboBoxSelectionModel<String> chargeShipping = null;
    private JGenericComboBoxSelectionModel<UomType> amountTypeComboModel = null;
    private JGenericComboBoxSelectionModel<String> ratingTypeComboModel = null;
    private JGenericComboBoxSelectionModel<String> taxableComboModel = null;
    public PanelShippingAndShoppingCartAndMiscellaneous() {
        initComponents();
        ComponentBorder.loweredBevelBorder(panelShipping, "Shipping");
        ComponentBorder.loweredBevelBorder(panelShoppingCart, "Shopping Cart");
        ComponentBorder.loweredBevelBorder(panelMiscellaneous, "Miscellaneous");

        allowOrderDecimalQuantity = new JGenericComboBoxSelectionModel<String>(panelAllowOrderDecimal, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));

        returnable = new JGenericComboBoxSelectionModel<String>(panelReturnable, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        includeInPromotions = new JGenericComboBoxSelectionModel<String>(panelIncludeInPromotion, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));

        autoCreateKeywords = new JGenericComboBoxSelectionModel<String>(panelKeyWoards, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        inShippingBox = new JGenericComboBoxSelectionModel<String>(panelInShippingBox, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        defaultShipmentBoxType = new JGenericComboBoxSelectionModel<ShipmentBoxType>(panelDefaultShipmentBox, ShipmentBoxTypeSingleton.getValueList());
        chargeShipping = new JGenericComboBoxSelectionModel<String>(panelChargeShipping, YesNoConditionSelectSingleton.getValueList(),
                new StringListCellRenderer(false));
        requireAmountComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelRequireAmount, YesNoConditionSelectSingleton.getValueList(),
                new StringListCellRenderer(false));
        ratingTypeComboModel = new JGenericComboBoxSelectionModel<String>(panelTaxable, YesNoConditionSelectSingleton.getValueList(),
                new StringListCellRenderer(false));
        amountTypeComboModel = new JGenericComboBoxSelectionModel<UomType>(panelAmountType, UOMTypeSingleton.getValueList());
taxableComboModel   = new JGenericComboBoxSelectionModel<String>(panelRatingTypeEnum, YesNoConditionSelectSingleton.getValueList(),
                new StringListCellRenderer(false));      
        /*
         uomTypeListModel.addAll(UOMTypeSingleton.getValueList());
         uomTypeComboBoxModel.setListModel(uomTypeListModel);
         amountUomTypeIdTextField.setModel(uomTypeComboBoxModel);
         selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         listModelSelection.setListModels(uomTypeListModel, selectionModel);
         uomTypeComboBoxModel.setListSelectionModel(selectionModel);
         UomTypeListCellRenderer uomTypeListCellRenderer = new UomTypeListCellRenderer(false);
         amountUomTypeIdTextField.setRenderer(uomTypeListCellRenderer);
         */
        /*       requireAmountComboBoxModel.setListModel(YesNoConditionSelectSingleton.getValueListModal());
         requireAmountTextField.setModel(requireAmountComboBoxModel);
         StringListCellRenderer stringRenderer = new StringListCellRenderer(false);
         requireAmountTextField.setRenderer(stringRenderer);
         try {
         requireAmountComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("Y"));
         } catch (Exception ex) {
         Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        ComponentBorder.loweredBevelBorder(panelRate, "Rate");

    }
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void clearDialogFields() {
        piecesIncludedTextField.setText("");
        productRatingTextField.setText("");
        allowOrderDecimalQuantity.setSelectedItem(YesNoConditionSelectSingleton.N);
        returnable.setSelectedItem(YesNoConditionSelectSingleton.N);
        includeInPromotions.setSelectedItem(YesNoConditionSelectSingleton.N);
        autoCreateKeywords.setSelectedItem(YesNoConditionSelectSingleton.N);
        inShippingBox.setSelectedItem(YesNoConditionSelectSingleton.N);
//        defaultShipmentBoxType.setSelectedItem(YesNoConditionSelectSingleton.N);
        chargeShipping.setSelectedItem(YesNoConditionSelectSingleton.N);

    }

    public void setDialogField() {
        if (UtilValidate.isNotEmpty(this.productComposite.getProduct().getpiecesIncluded())) {
            piecesIncludedTextField.setText(OrderMaxUtility.getValidString(this.productComposite.getProduct().getpiecesIncluded().toString()));
        }
        productRatingTextField.setText(OrderMaxUtility.getValidString(this.productComposite.getProduct().getproductRating()));
        allowOrderDecimalQuantity.setSelectedItem(this.productComposite.getProduct().getorderDecimalQuantity());
        try {
            returnable.setSelectedItem(YesNoConditionSelectSingleton.getString(this.productComposite.getProduct().getreturnable()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        includeInPromotions.setSelectedItem(this.productComposite.getProduct().getincludeInPromotions());

        autoCreateKeywords.setSelectedItem(this.productComposite.getProduct().getautoCreateKeywords());
        inShippingBox.setSelectedItem(this.productComposite.getProduct().getinShippingBox());
        try {
            defaultShipmentBoxType.setSelectedItem(ShipmentBoxTypeSingleton.getShipmentBoxType(this.productComposite.getProduct().getdefaultShipmentBoxTypeId()));
        } catch (Exception ex) {
//            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        chargeShipping.setSelectedItem(this.productComposite.getProduct().getchargeShipping());

    }

    public void getDialogField() {
        this.productComposite.getProduct().setpiecesIncluded(OrderMaxUtility.getValidLong(piecesIncludedTextField.getText()));
        this.productComposite.getProduct().setproductRating(OrderMaxUtility.getValidBigDecimal(productRatingTextField.getText()));
        try {
            this.productComposite.getProduct().setorderDecimalQuantity(YesNoConditionSelectSingleton.getKeyFromDisplayName(allowOrderDecimalQuantity.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.productComposite.getProduct().setreturnable(YesNoConditionSelectSingleton.getKeyFromDisplayName(returnable.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.productComposite.getProduct().setincludeInPromotions(YesNoConditionSelectSingleton.getKeyFromDisplayName(includeInPromotions.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.productComposite.getProduct().setautoCreateKeywords(YesNoConditionSelectSingleton.getKeyFromDisplayName(autoCreateKeywords.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.productComposite.getProduct().setinShippingBox(YesNoConditionSelectSingleton.getKeyFromDisplayName(inShippingBox.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (UtilValidate.isNotEmpty(defaultShipmentBoxType.getSelectedItem())) {
            this.productComposite.getProduct().setdefaultShipmentBoxTypeId(defaultShipmentBoxType.getSelectedItem().getshipmentBoxTypeId());
        }
        try {
            this.productComposite.getProduct().setchargeShipping(YesNoConditionSelectSingleton.getKeyFromDisplayName(chargeShipping.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(PanelShippingAndShoppingCartAndMiscellaneous.class.getName()).log(Level.SEVERE, null, ex);
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
        java.awt.GridBagConstraints gridBagConstraints;

        panelShipping = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        piecesIncludedTextField = new javax.swing.JTextField();
        panelChargeShipping = new javax.swing.JPanel();
        panelInShippingBox = new javax.swing.JPanel();
        panelDefaultShipmentBox = new javax.swing.JPanel();
        panelShoppingCart = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelAllowOrderDecimal = new javax.swing.JPanel();
        panelRequireAmount = new javax.swing.JPanel();
        panelAmountType = new javax.swing.JPanel();
        panelMiscellaneous = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        panelReturnable = new javax.swing.JPanel();
        panelTaxable = new javax.swing.JPanel();
        panelIncludeInPromotion = new javax.swing.JPanel();
        panelKeyWoards = new javax.swing.JPanel();
        panelRate = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        productRatingTextField = new javax.swing.JTextField();
        panelRatingTypeEnum = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(4395, 900));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        panelShipping.setPreferredSize(new java.awt.Dimension(4395, 90));

        jLabel23.setText("In Shipping Box ?");

        jLabel25.setText("Default Shipment Box Type Id");

        jLabel26.setText(" \tCharge Shipping");

        jLabel24.setText("Pieces Included");

        piecesIncludedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piecesIncludedTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChargeShippingLayout = new javax.swing.GroupLayout(panelChargeShipping);
        panelChargeShipping.setLayout(panelChargeShippingLayout);
        panelChargeShippingLayout.setHorizontalGroup(
            panelChargeShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelChargeShippingLayout.setVerticalGroup(
            panelChargeShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelInShippingBoxLayout = new javax.swing.GroupLayout(panelInShippingBox);
        panelInShippingBox.setLayout(panelInShippingBoxLayout);
        panelInShippingBoxLayout.setHorizontalGroup(
            panelInShippingBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelInShippingBoxLayout.setVerticalGroup(
            panelInShippingBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDefaultShipmentBoxLayout = new javax.swing.GroupLayout(panelDefaultShipmentBox);
        panelDefaultShipmentBox.setLayout(panelDefaultShipmentBoxLayout);
        panelDefaultShipmentBoxLayout.setHorizontalGroup(
            panelDefaultShipmentBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDefaultShipmentBoxLayout.setVerticalGroup(
            panelDefaultShipmentBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelShippingLayout = new javax.swing.GroupLayout(panelShipping);
        panelShipping.setLayout(panelShippingLayout);
        panelShippingLayout.setHorizontalGroup(
            panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShippingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDefaultShipmentBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(piecesIncludedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                .addGap(103, 103, 103)
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChargeShipping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelInShippingBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3752, Short.MAX_VALUE))
        );
        panelShippingLayout.setVerticalGroup(
            panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShippingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(piecesIncludedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23)
                    .addComponent(panelInShippingBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShippingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel25)
                        .addComponent(jLabel26)
                        .addComponent(panelChargeShipping, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelShippingLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(panelDefaultShipmentBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelShippingLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelChargeShipping, panelDefaultShipmentBox, panelInShippingBox});

        add(panelShipping);

        panelShoppingCart.setMaximumSize(new java.awt.Dimension(32767, 90));
        panelShoppingCart.setPreferredSize(new java.awt.Dimension(4381, 90));

        jLabel27.setText("Allow order decimal quantity");

        jLabel21.setText("Require Amount");

        jLabel22.setText("Amount Uom Type Id");

        javax.swing.GroupLayout panelAllowOrderDecimalLayout = new javax.swing.GroupLayout(panelAllowOrderDecimal);
        panelAllowOrderDecimal.setLayout(panelAllowOrderDecimalLayout);
        panelAllowOrderDecimalLayout.setHorizontalGroup(
            panelAllowOrderDecimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        panelAllowOrderDecimalLayout.setVerticalGroup(
            panelAllowOrderDecimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRequireAmountLayout = new javax.swing.GroupLayout(panelRequireAmount);
        panelRequireAmount.setLayout(panelRequireAmountLayout);
        panelRequireAmountLayout.setHorizontalGroup(
            panelRequireAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        panelRequireAmountLayout.setVerticalGroup(
            panelRequireAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelAmountTypeLayout = new javax.swing.GroupLayout(panelAmountType);
        panelAmountType.setLayout(panelAmountTypeLayout);
        panelAmountTypeLayout.setHorizontalGroup(
            panelAmountTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelAmountTypeLayout.setVerticalGroup(
            panelAmountTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelShoppingCartLayout = new javax.swing.GroupLayout(panelShoppingCart);
        panelShoppingCart.setLayout(panelShoppingCartLayout);
        panelShoppingCartLayout.setHorizontalGroup(
            panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShoppingCartLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelAllowOrderDecimal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAmountType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(106, 106, 106)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRequireAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3748, Short.MAX_VALUE))
        );
        panelShoppingCartLayout.setVerticalGroup(
            panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShoppingCartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRequireAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAllowOrderDecimal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShoppingCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(panelAmountType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(panelShoppingCart);

        panelMiscellaneous.setPreferredSize(new java.awt.Dimension(4395, 90));

        jLabel28.setText("Include In Promotions");

        jLabel29.setText(" \tTaxable");

        jLabel30.setText(" \tAuto Create Key words");

        jLabel31.setText("Returnable");

        javax.swing.GroupLayout panelReturnableLayout = new javax.swing.GroupLayout(panelReturnable);
        panelReturnable.setLayout(panelReturnableLayout);
        panelReturnableLayout.setHorizontalGroup(
            panelReturnableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelReturnableLayout.setVerticalGroup(
            panelReturnableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelTaxableLayout = new javax.swing.GroupLayout(panelTaxable);
        panelTaxable.setLayout(panelTaxableLayout);
        panelTaxableLayout.setHorizontalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelTaxableLayout.setVerticalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelIncludeInPromotionLayout = new javax.swing.GroupLayout(panelIncludeInPromotion);
        panelIncludeInPromotion.setLayout(panelIncludeInPromotionLayout);
        panelIncludeInPromotionLayout.setHorizontalGroup(
            panelIncludeInPromotionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelIncludeInPromotionLayout.setVerticalGroup(
            panelIncludeInPromotionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelKeyWoardsLayout = new javax.swing.GroupLayout(panelKeyWoards);
        panelKeyWoards.setLayout(panelKeyWoardsLayout);
        panelKeyWoardsLayout.setHorizontalGroup(
            panelKeyWoardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        panelKeyWoardsLayout.setVerticalGroup(
            panelKeyWoardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMiscellaneousLayout = new javax.swing.GroupLayout(panelMiscellaneous);
        panelMiscellaneous.setLayout(panelMiscellaneousLayout);
        panelMiscellaneousLayout.setHorizontalGroup(
            panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiscellaneousLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelMiscellaneousLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelReturnable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMiscellaneousLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelTaxable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(87, 87, 87)
                .addGroup(panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMiscellaneousLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel30))
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIncludeInPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelKeyWoards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3731, Short.MAX_VALUE))
        );

        panelMiscellaneousLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelIncludeInPromotion, panelKeyWoards, panelReturnable, panelTaxable});

        panelMiscellaneousLayout.setVerticalGroup(
            panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiscellaneousLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(panelReturnable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(panelIncludeInPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMiscellaneousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29)
                    .addComponent(panelTaxable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30)
                    .addComponent(panelKeyWoards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelMiscellaneousLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelIncludeInPromotion, panelKeyWoards, panelReturnable, panelTaxable});

        add(panelMiscellaneous);

        panelRate.setPreferredSize(new java.awt.Dimension(749, 90));

        jLabel15.setText("Rating Type Enum");

        jLabel20.setText("Rating");

        javax.swing.GroupLayout panelRatingTypeEnumLayout = new javax.swing.GroupLayout(panelRatingTypeEnum);
        panelRatingTypeEnum.setLayout(panelRatingTypeEnumLayout);
        panelRatingTypeEnumLayout.setHorizontalGroup(
            panelRatingTypeEnumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        panelRatingTypeEnumLayout.setVerticalGroup(
            panelRatingTypeEnumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelRateLayout = new javax.swing.GroupLayout(panelRate);
        panelRate.setLayout(panelRateLayout);
        panelRateLayout.setHorizontalGroup(
            panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRateLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRatingTypeEnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productRatingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3732, Short.MAX_VALUE))
        );
        panelRateLayout.setVerticalGroup(
            panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel20)
                        .addComponent(productRatingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelRatingTypeEnum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        add(panelRate);
    }// </editor-fold>//GEN-END:initComponents

    private void piecesIncludedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piecesIncludedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_piecesIncludedTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel panelAllowOrderDecimal;
    private javax.swing.JPanel panelAmountType;
    private javax.swing.JPanel panelChargeShipping;
    private javax.swing.JPanel panelDefaultShipmentBox;
    private javax.swing.JPanel panelInShippingBox;
    private javax.swing.JPanel panelIncludeInPromotion;
    private javax.swing.JPanel panelKeyWoards;
    private javax.swing.JPanel panelMiscellaneous;
    private javax.swing.JPanel panelRate;
    private javax.swing.JPanel panelRatingTypeEnum;
    private javax.swing.JPanel panelRequireAmount;
    private javax.swing.JPanel panelReturnable;
    private javax.swing.JPanel panelShipping;
    private javax.swing.JPanel panelShoppingCart;
    private javax.swing.JPanel panelTaxable;
    private javax.swing.JTextField piecesIncludedTextField;
    private javax.swing.JTextField productRatingTextField;
    // End of variables declaration//GEN-END:variables
}
