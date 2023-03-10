/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;

/**
 *
 * @author BBS Auctions
 */
public class PanelProductId extends javax.swing.JPanel implements ProductTreeFindInterface {

    ProductPickerEditPanel panelProductIdPicker;

    /**
     * Creates new form PanelProductId
     */
    public PanelProductId(ControllerOptions controllerOptions) {
        initComponents();
        panelProductIdPicker = new ProductPickerEditPanel(controllerOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panelProductIdPicker);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProductId = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        panelProductId.setPreferredSize(new java.awt.Dimension(200, 24));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel2.setText("Product Id: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel2;
    public javax.swing.JPanel panelProductId;
    // End of variables declaration//GEN-END:variables

    @Override
    public List<String> getProductIds() {
        List<String> valList = new ArrayList<String>();
        valList.add(panelProductIdPicker.textIdField.getText());
        return valList;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    public String getName() {
        return "Product Id";
    }

   public void addActionListener(ActionListener listener) {
        panelProductIdPicker.addActionListener(listener);
    }    
    static class CreationFactoryClass implements ProductTreeFindPanelFactoryInterface {

        @Override
        public ProductTreeFindInterface createFind(ControllerOptions options) {
            return new PanelProductId(options);
        }
    }

//    static {
//        ProductTreeFindFactory.registerPanel("Product Id", new CreationFactoryClass());
//    }
}
