/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public abstract class BasePickerEditPanel extends javax.swing.JPanel implements LookupActionListnerInterface {

    static public String module = BasePickerEditPanel.class.getName();
    ControllerOptions controllerOptions;
    DirtyManager dirty = null;
    String editorId = "textIdField";
    public String originalEntityId = null;
    public String entityId = null;
    public String entityIdDescription = null;
    LookupType lookupType = null;
    /**
     * Creates new form DatePickerEditPanel
     */
    public BasePickerEditPanel(LookupType lookupType, ControllerOptions controllerOptions) {
        initComponents();
        this.lookupType = lookupType;
        this.controllerOptions = controllerOptions;

        if (controllerOptions.contains("editorId")) {
            editorId = (String) controllerOptions.get("editorId");
        }        
        
        if (controllerOptions.contains("DirtyManager")) {
            dirty = (DirtyManager) controllerOptions.get("DirtyManager");
        }

        createLookupAction();
    }

    protected void createLookupAction() {
        controllerOptions.put(editorId, textIdField);
        controllerOptions.setDoubleClickCloseDialog();

        LookupActionListner listner = new LookupActionListner(this, controllerOptions);
        btnLookup.addActionListener(listner);
        listner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setGenericValue(genericValue);
            }
        });
    }

    final void setDirtyManger(JTextField field) {
        if (dirty != null) {
            field.getDocument().addDocumentListener(dirty);
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

        jPanel1 = new javax.swing.JPanel();
        btnLookup = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        textIdField = new ObservingTextField();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setPreferredSize(new java.awt.Dimension(51, 24));
        jPanel1.setLayout(new java.awt.BorderLayout());

        btnLookup.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\framework\\images\\webapp\\images\\icons\\famfamfam\\text_list_bullets.png")); // NOI18N
        btnLookup.setMinimumSize(new java.awt.Dimension(24, 27));
        btnLookup.setPreferredSize(new java.awt.Dimension(28, 28));
        btnLookup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLookupActionPerformed(evt);
            }
        });
        jPanel1.add(btnLookup, java.awt.BorderLayout.EAST);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 270));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(textIdField, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1);

        jLabel1.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLookupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLookupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLookupActionPerformed

    @Override
    public EntityCondition getEntityCondition() {
        if (textIdField.getText().isEmpty() == false) {
            return EntityCondition.makeCondition(entityId, EntityOperator.EQUALS, textIdField.getText());
        } else {
            return null;
        }
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public Object getEntityValue() {
        if (textIdField.getText().isEmpty() == false) {
            return textIdField.getText();
        } else {
            return null;
        }
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        String val = (String) getEntityValue();
        if (val != null) {
            valueMap.put(getEntityId(), val);
        }
    }
    
    @Override
    public void setEntityValue(java.lang.Object value) {
        textIdField.setText((String)value);
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLookup;
    protected javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables

    protected GenericValue genericValue;

    @Override
    public void setGenericValue(GenericValue genericValue) {
        this.genericValue = genericValue;

        if (genericValue != null) {
            if (controllerOptions.contains("showName")) {
                jLabel1.setText(genericValue.getString(entityIdDescription));
                textIdField.setText(genericValue.getString(getKeyId()));
            } else {
                textIdField.setText(genericValue.getString(getKeyId()));
                jLabel1.setText(genericValue.getString(entityIdDescription));
            }
        }

    }

    @Override
    public GenericValue getGenericValue() {
        return genericValue;
    }

    @Override
    public String getEditorId() {
        return editorId;
    }

    public String getKeyId() {
        return entityId;
    }

    @Override
    abstract public LookupActionListnerInterface.LookupType getLookupType();
    
    @Override
    public Component getComponent() {
        return this;
    }    
}
