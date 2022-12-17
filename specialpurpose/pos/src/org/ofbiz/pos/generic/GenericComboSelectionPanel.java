/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import java.util.List;
import java.util.Map;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;

/**
 *
 * @author siranjeev
 */
public class GenericComboSelectionPanel extends javax.swing.JPanel {

    /**
     * Creates new form GenericComboSelectionPanel
     */
    String selectId;
    String value;
    String eName;
    final org.ofbiz.guiapp.xui.XuiSession session = XuiContainer.getSession();
    private org.ofbiz.pos.generic.GenericValueComboBox selectIdCombo = null;

    public GenericComboSelectionPanel(String entityName, String selId, String defValue) {
        initComponents();
        selectId = selId;
        value = defValue;
        eName = entityName;
        lblSeleId.setText(selId);
        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists(eName, null, null, session.getDelegator());
        selectIdCombo = new org.ofbiz.pos.generic.GenericValueComboBox(comboSelText, genFacilityList, eName, selId, selId, true);
    }

    public GenericComboSelectionPanel(String entityName, String selId, String despField, String defValue) {
        initComponents();
        selectId = selId;
        value = defValue;
        eName = entityName;
        lblSeleId.setText(selId);
        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists(eName, null, null, session.getDelegator());
        selectIdCombo = new org.ofbiz.pos.generic.GenericValueComboBox(comboSelText, genFacilityList, eName, selId, despField, true);
    }

    public String getSelId() {
        return selectId;
    }

    public String getValue() {
        return value;
    }

    public boolean isAllSelected() {
        return selectIdCombo.isAllSelected();
    }

    public void setAllSelected() {
        selectIdCombo.setSelectedItemId(org.ofbiz.pos.generic.GenericValueComboBox.AllId);
    }

    public String getSelectedItemId() {
        return selectIdCombo.getSelectedItemId();
    }

    public void getSelection(Map<String, Object> resultMap) {
        if (isAllSelected() == false) {
            resultMap.put(getSelId(), getValue());
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

        lblSeleId = new javax.swing.JLabel();
        comboComperator = new javax.swing.JComboBox();
        comboSelText = new javax.swing.JComboBox();

        lblSeleId.setText("etewrtertewrterte");

        comboComperator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "=", "<=", ">=", "<>" }));

        comboSelText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSelTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblSeleId)
                .addGap(18, 18, 18)
                .addComponent(comboComperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboSelText, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSeleId)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboComperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboSelText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboSelTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSelTextActionPerformed
        value = comboSelText.getSelectedItem().toString();
    }//GEN-LAST:event_comboSelTextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboComperator;
    private javax.swing.JComboBox comboSelText;
    private javax.swing.JLabel lblSeleId;
    // End of variables declaration//GEN-END:variables
}
