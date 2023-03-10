/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class CategoryPickerEditPanel extends BasePickerEditPanel {

    static public String module = CategoryPickerEditPanel.class.getName();
    public String keyId = "productCategoryId";

    /**
     * Creates new form DatePickerEditPanel
     */
    public CategoryPickerEditPanel(final ControllerOptions controllerOptions) {
        super(LookupType.CategoryId,controllerOptions);
        initComponents();
        controllerOptions.put(getEditorId(), textIdField);
        controllerOptions.put("EntityName", "ProductCategory");
        entityId = "productCategoryId";
        entityIdDescription = "description";            
        /*
        controllerOptions.setDoubleClickCloseDialog();
        
        LookupActionListner listner = new LookupActionListner(this, controllerOptions);
        btnHeaderPatryId.addActionListener(listner);
        listner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setCatGenericVale(genericValue);
            }
        });
        */
        setDirtyManger(textIdField);
    }
/*
    public CategoryPickerEditPanel(final ControllerOptions controllerOptions, JPanel panel) {
        super(controllerOptions, panel);
        initComponents();
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, this);

        controllerOptions.put(getEditorId(), textIdField);
        controllerOptions.put("EntityName", "ProductCategory");        
        controllerOptions.setDoubleClickCloseDialog();
        LookupActionListner listner = new LookupActionListner(this, controllerOptions);
        btnHeaderPatryId.addActionListener(listner);
        listner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setCatGenericVale(genericValue);
            }

        });
        setDirtyManger(textIdField);
    }*/

    public void setCatGenericValue(GenericValue genericValue) {
        if (genericValue != null) {
            if (controllerOptions.contains("showData")) {
                jLabel1.setText(genericValue.getString("productCategoryId"));
                textIdField.setText(genericValue.getString("categoryData"));
            } else if (controllerOptions.contains("showName")) {
                jLabel1.setText(genericValue.getString("productCategoryId"));
                textIdField.setText(genericValue.getString("description"));
            } else {
                textIdField.setText(genericValue.getString("productCategoryId"));
                jLabel1.setText(genericValue.getString("description"));
            }
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

        jPanel1 = new javax.swing.JPanel();
        btnHeaderPatryId = new javax.swing.JButton();
        textIdField = new ObservingTextField();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnHeaderPatryId.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\framework\\images\\webapp\\images\\icons\\famfamfam\\text_list_bullets.png")); // NOI18N
        btnHeaderPatryId.setMinimumSize(new java.awt.Dimension(24, 27));
        btnHeaderPatryId.setPreferredSize(new java.awt.Dimension(28, 28));
        btnHeaderPatryId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeaderPatryIdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel1.add(btnHeaderPatryId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textIdField, gridBagConstraints);

        add(jPanel1);

        jLabel1.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHeaderPatryIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeaderPatryIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHeaderPatryIdActionPerformed
/*
    @Override
    public String getEditorId() {
        return "textIdField";
    }

    @Override
    public String getEntityValue() {
        if (textIdField.getText().isEmpty() == false) {
            return textIdField.getText();
        } else {
            return null;
        }
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        String val = getEntityValue();
        if (val != null) {
            valueMap.put(getEntityId(), val);
        }
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHeaderPatryId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getKeyId() {
        return "productCategoryId";
    }

    @Override
    public LookupType getLookupType() {
        return LookupType.CategoryId;
    }
}
