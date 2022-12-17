/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product.panel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.StringListCellRenderer;
import mvc.model.list.UomTypeListCellRenderer;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.UomType;
import org.ofbiz.ordermax.invoice.FindInvoiceListPanel;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.UOMTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class PanelRateAndAmount extends javax.swing.JPanel {
    final private ListAdapterListModel<UomType> uomTypeListModel = new ListAdapterListModel<UomType>();    
    private ListComboBoxModel<UomType> uomTypeComboBoxModel = new ListComboBoxModel<UomType>();    
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();    
    private ListModelSelection<UomType> listModelSelection = new ListModelSelection<UomType>();  

    private ListComboBoxModel<String> requireAmountComboBoxModel = new ListComboBoxModel<String>();    
    
    /**
     * Creates new form PanelRateAndAmount
     */
    public PanelRateAndAmount() {
        initComponents();
        
        uomTypeListModel.addAll(UOMTypeSingleton.getValueList());        
        uomTypeComboBoxModel.setListModel(uomTypeListModel);
        amountUomTypeIdTextField.setModel(uomTypeComboBoxModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        listModelSelection.setListModels(uomTypeListModel, selectionModel);
        uomTypeComboBoxModel.setListSelectionModel(selectionModel);
        UomTypeListCellRenderer uomTypeListCellRenderer = new UomTypeListCellRenderer(false);
        amountUomTypeIdTextField.setRenderer(uomTypeListCellRenderer);
    
        
        requireAmountComboBoxModel.setListModel(YesNoConditionSelectSingleton.getValueListModal());        
        requireAmountTextField.setModel(requireAmountComboBoxModel);
        StringListCellRenderer stringRenderer = new StringListCellRenderer(false);
        requireAmountTextField.setRenderer(stringRenderer);
        try {
            requireAmountComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("Y"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ComponentBorder.loweredBevelBorder(panelRate, "Rate");
        ComponentBorder.loweredBevelBorder(panelAmount, "Amount");
    }
    private ProductComposite productComposite = null;
    public ProductComposite getProductComposite() {
        return productComposite;
    }
    
    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }
    
    public void clearDialogFields(){
    }
    
    public void setDialogField(){
    }
    public void getDialogField(){
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRate = new javax.swing.JPanel();
        ratingTypeEnumTextField = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        productRatingTextField = new javax.swing.JTextField();
        panelAmount = new javax.swing.JPanel();
        requireAmountTextField = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        amountUomTypeIdTextField = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());

        panelRate.setPreferredSize(new java.awt.Dimension(749, 60));

        ratingTypeEnumTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Rating Type Enum");

        jLabel20.setText("Rating");

        javax.swing.GroupLayout panelRateLayout = new javax.swing.GroupLayout(panelRate);
        panelRate.setLayout(panelRateLayout);
        panelRateLayout.setHorizontalGroup(
            panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRateLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(ratingTypeEnumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productRatingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        panelRateLayout.setVerticalGroup(
            panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ratingTypeEnumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel20)
                    .addComponent(productRatingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        add(panelRate, java.awt.BorderLayout.PAGE_START);

        panelAmount.setPreferredSize(new java.awt.Dimension(749, 42));

        requireAmountTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel21.setText("Require Amount");

        jLabel22.setText("Amount Uom Type Id");

        amountUomTypeIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelAmountLayout = new javax.swing.GroupLayout(panelAmount);
        panelAmount.setLayout(panelAmountLayout);
        panelAmountLayout.setHorizontalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAmountLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(requireAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(amountUomTypeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelAmountLayout.setVerticalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAmountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requireAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(amountUomTypeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(panelAmount, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox amountUomTypeIdTextField;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel panelAmount;
    private javax.swing.JPanel panelRate;
    private javax.swing.JTextField productRatingTextField;
    private javax.swing.JComboBox ratingTypeEnumTextField;
    private javax.swing.JComboBox requireAmountTextField;
    // End of variables declaration//GEN-END:variables
}
