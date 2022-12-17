/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import org.ofbiz.ordermax.party.*;
import org.ofbiz.ordermax.product.*;
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
public class FindOrderByPartyListButtonPanel extends javax.swing.JPanel {

    
    /**
     * Creates new form ProductButtonPanel
     */
    public FindOrderByPartyListButtonPanel() {
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

        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        btnOk.setText("OK");
        btnOk.setMinimumSize(new java.awt.Dimension(47, 15));
        btnOk.setPreferredSize(new java.awt.Dimension(107, 20));

        btnCancel.setText("Cancel");
        btnCancel.setMinimumSize(new java.awt.Dimension(47, 15));
        btnCancel.setPreferredSize(new java.awt.Dimension(107, 20));

        btnNew.setText("New");
        btnNew.setMinimumSize(new java.awt.Dimension(47, 15));
        btnNew.setPreferredSize(new java.awt.Dimension(107, 20));

        btnEdit.setText("Edit");
        btnEdit.setMinimumSize(new java.awt.Dimension(47, 15));
        btnEdit.setPreferredSize(new java.awt.Dimension(107, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    public JButton getOkButton() {
        return btnOk;
    }
*/
    
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnNew;
    public javax.swing.JButton btnOk;
    // End of variables declaration//GEN-END:variables

/*    public JButton getBtnLoad() {
        return btnLoad;
    }
    */
}
