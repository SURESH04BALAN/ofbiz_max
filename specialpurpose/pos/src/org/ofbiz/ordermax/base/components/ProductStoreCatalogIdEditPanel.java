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
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class ProductStoreCatalogIdEditPanel extends BasePickerEditPanel {

    static public String module = ProductStoreCatalogIdEditPanel.class.getName();
    public String keyId = "posTerminalId";
    /**
     * Creates new form DatePickerEditPanel
     */
    public ProductStoreCatalogIdEditPanel(final ControllerOptions controllerOptions) {
        super(LookupType.ProductStoreCatalogId,controllerOptions);
        initComponents();
        controllerOptions.put(getEditorId(), textIdField);
        controllerOptions.put("EntityName", "ProductStoreCatalog");
        entityId = "productStoreId";
        entityIdDescription = "prodCatalogId";  
        
        /*controllerOptions.setDoubleClickCloseDialog();
        LookupActionListner listner = new LookupActionListner(this, controllerOptions);
        btnHeaderPatryId.addActionListener(listner);
        listner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setCatGenericVale(genericValue);
            }
        });*/
    }
/*
    public ProductStoreCatalogIdEditPanel(final ControllerOptions controllerOptions, JPanel panel) {
        super(controllerOptions, panel);
        initComponents();
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, this);

        controllerOptions.put(getEditorId(), textIdField);
               controllerOptions.put("EntityName", "ProductStoreCatalog");
        controllerOptions.setDoubleClickCloseDialog();
        LookupActionListner listner = new LookupActionListner(this, controllerOptions);
        btnHeaderPatryId.addActionListener(listner);
        listner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setCatGenericVale(genericValue);
            }
        });
    }
*/
    public void setCatGenericVale(GenericValue genericValue) {
        if (genericValue != null) {
            if (controllerOptions.contains("showName")) {
                jLabel1.setText(genericValue.getString("prodCatalogId"));
                textIdField.setText(genericValue.getString("productStoreId"));
            } else {
                textIdField.setText(genericValue.getString("prodCatalogId"));
                jLabel1.setText(genericValue.getString("productStoreId"));
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

        jPanel1 = new javax.swing.JPanel();
        btnHeaderPatryId = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        textIdField = new ObservingTextField();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 2));

        jPanel1.setPreferredSize(new java.awt.Dimension(51, 24));
        jPanel1.setLayout(new java.awt.BorderLayout());

        btnHeaderPatryId.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\framework\\images\\webapp\\images\\icons\\famfamfam\\text_list_bullets.png")); // NOI18N
        btnHeaderPatryId.setMinimumSize(new java.awt.Dimension(24, 27));
        btnHeaderPatryId.setPreferredSize(new java.awt.Dimension(28, 28));
        btnHeaderPatryId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeaderPatryIdActionPerformed(evt);
            }
        });
        jPanel1.add(btnHeaderPatryId, java.awt.BorderLayout.EAST);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 270));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(textIdField, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1);

        jLabel1.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHeaderPatryIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeaderPatryIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHeaderPatryIdActionPerformed

  /*  @Override
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
    private javax.swing.JPanel jPanel3;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables
/*
    @Override
    public String getKeyId() {
        return "productStoreId";
    }
*/
    @Override
    public LookupType getLookupType() {
        return LookupType.ProductStoreCatalogId;
    }
}
