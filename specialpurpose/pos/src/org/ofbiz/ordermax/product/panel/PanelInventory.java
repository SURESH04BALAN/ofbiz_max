/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product.panel;

import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.invoice.FindInvoiceListPanel;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.RequirmentMethodEnumSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelInventory extends javax.swing.JPanel {
    private ListComboBoxModel<String> notAvaComboBoxModel = new ListComboBoxModel<String>();
    private ListComboBoxModel<String> requireInvComboBoxModel = new ListComboBoxModel<String>();
    private ListComboBoxModel<String> requireInvEnumComboBoxModel = new ListComboBoxModel<String>();    

    /**
     * Creates new form PanelInventory
     */
    public PanelInventory() {
        initComponents();
        ComponentBorder.loweredBevelBorder(panelInventory, "Inventory");
        notAvaComboBoxModel.setListModel(YesNoConditionSelectSingleton.getValueListModal());        
        CombosalesDiscWhenNotAvail.setModel(notAvaComboBoxModel);
        StringListCellRenderer stringRenderer = new StringListCellRenderer(false);
        CombosalesDiscWhenNotAvail.setRenderer(stringRenderer);
        try {
            CombosalesDiscWhenNotAvail.setSelectedItem(YesNoConditionSelectSingleton.getString("N"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        requireInvComboBoxModel.setListModel(YesNoConditionSelectSingleton.getValueListModal());        
        requireInventoryTextField.setModel(requireInvComboBoxModel);
        stringRenderer = new StringListCellRenderer(false);
        requireInventoryTextField.setRenderer(stringRenderer);
        try {
            requireInvComboBoxModel.setSelectedItem(YesNoConditionSelectSingleton.getString("Y"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
        requireInvEnumComboBoxModel.setListModel(RequirmentMethodEnumSingleton.getValueListModal());        
        requirementMethodEnumIdTextField.setModel(requireInvEnumComboBoxModel);
        stringRenderer = new StringListCellRenderer(false);
        requirementMethodEnumIdTextField.setRenderer(stringRenderer);
        try {
            requireInvEnumComboBoxModel.setSelectedItem(RequirmentMethodEnumSingleton.getString("PRODRQM_AUTO"));
        } catch (Exception ex) {
            Logger.getLogger(FindInvoiceListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
                
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

        panelInventory = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        inventoryMessageTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        CombosalesDiscWhenNotAvail = new javax.swing.JComboBox();
        requirementMethodEnumIdTextField = new javax.swing.JComboBox();
        requireInventoryTextField = new javax.swing.JComboBox();

        setLayout(new java.awt.GridLayout(1, 0));

        panelInventory.setPreferredSize(new java.awt.Dimension(1129, 300));

        jLabel7.setText("Disc. When Inv. Not Avail?");

        jLabel8.setText("Inventory Message");

        jLabel9.setText("Requirement Method Enum Id");

        jLabel10.setText("Require Inventory");

        jLabel14.setText("Lot Id");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CombosalesDiscWhenNotAvail.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        requirementMethodEnumIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        requireInventoryTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(27, 27, 27)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CombosalesDiscWhenNotAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(requireInventoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inventoryMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(requirementMethodEnumIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addComponent(CombosalesDiscWhenNotAvail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(requirementMethodEnumIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(requireInventoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(inventoryMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInventoryLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel14)))
                .addContainerGap())
        );

        panelInventoryLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {CombosalesDiscWhenNotAvail, inventoryMessageTextField, jComboBox5, requireInventoryTextField, requirementMethodEnumIdTextField});

        add(panelInventory);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CombosalesDiscWhenNotAvail;
    private javax.swing.JTextField inventoryMessageTextField;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelInventory;
    private javax.swing.JComboBox requireInventoryTextField;
    private javax.swing.JComboBox requirementMethodEnumIdTextField;
    // End of variables declaration//GEN-END:variables
}