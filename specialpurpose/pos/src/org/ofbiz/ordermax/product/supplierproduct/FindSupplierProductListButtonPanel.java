/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.supplierproduct;

import org.ofbiz.ordermax.inventory.*;
import org.ofbiz.ordermax.invoice.*;
import org.ofbiz.ordermax.orderbase.purchaseorder.inventory.*;
import org.ofbiz.ordermax.orderbase.*;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 *
 * @author siranjeev
 */
public class FindSupplierProductListButtonPanel extends javax.swing.JPanel {

    
    /**
     * Creates new form ProductButtonPanel
     */
    public FindSupplierProductListButtonPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();

        okButton.setText("OK");
        okButton.setMinimumSize(new java.awt.Dimension(47, 15));
        okButton.setPreferredSize(new java.awt.Dimension(107, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(389, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

/*    public JButton getBtnDelete() {
        return btnDelete;
    }
*/

/*
    public JButton getBtnNewProduct() {
        return btnNewProduct;
    }

    public JButton getBtnSave() {
        return btnSave;
    }
*/
    public JButton getOkButton() {
        return okButton;
    }

    
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

/*    public JButton getBtnLoad() {
        return btnLoad;
    }
    */
}
