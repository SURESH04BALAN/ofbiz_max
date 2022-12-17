/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class PaymentPickerEditPanel extends javax.swing.JPanel {
    
    static public String module = PaymentPickerEditPanel.class.getName();
    static String editorId = "PaymentIdTextField";
    /**
     * Creates new form DatePickerEditPanel
     */
    public PaymentPickerEditPanel(ControllerOptions controllerOptions) {
        initComponents();
        controllerOptions.put(editorId, textIdField);
        controllerOptions.setDoubleClickCloseDialog();
        btnHeaderPatryId.addActionListener(new LookupActionListner(LookupActionListnerInterface.LookupType.PaymentId,controllerOptions));
    }
    protected XuiSession session = null;
    
    public XuiSession getSession() {
        return session;
    }
    
    public void setSession(XuiSession session) {
        this.session = session;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnHeaderPatryId = new javax.swing.JButton();
        textIdField = new ObservingTextField();

        btnHeaderPatryId.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\folder_open.jpg")); // NOI18N
        btnHeaderPatryId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeaderPatryIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(textIdField, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHeaderPatryId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(textIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnHeaderPatryId))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnHeaderPatryIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeaderPatryIdActionPerformed

    }//GEN-LAST:event_btnHeaderPatryIdActionPerformed
    

    
 
    
   
    
    private ClassLoader getClassLoader() {
        
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            
            if (cl == null) {
                try {
                    
                    cl = Thread.currentThread().getContextClassLoader();
                    
                } catch (Throwable t) {
                    
                }
                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHeaderPatryId;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables
}