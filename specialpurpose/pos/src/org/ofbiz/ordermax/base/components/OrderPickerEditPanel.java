/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.LookupActionListner;

/**
 *
 * @author siranjeev
 */
public class OrderPickerEditPanel extends javax.swing.JPanel implements ReportParameterSelectionInterface{

    static public String module = OrderPickerEditPanel.class.getName();
    static String editorId = "OrderIdTextField";
    public static String keyId = "orderId";

    /**
     * Creates new form DatePickerEditPanel
     */
    public OrderPickerEditPanel(ControllerOptions controllerOptions) {
        initComponents();
        controllerOptions.put(editorId, textIdField);
        controllerOptions.setDoubleClickCloseDialog();        
        btnHeaderPatryId.addActionListener(new LookupActionListner(LookupActionListnerInterface.LookupType.OrderId, controllerOptions));
    }

    public OrderPickerEditPanel(ControllerOptions controllerOptions, JPanel panel) {
        initComponents();
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, this);

        controllerOptions.put(editorId, textIdField);
        controllerOptions.setDoubleClickCloseDialog();
        btnHeaderPatryId.addActionListener(new LookupActionListner(LookupActionListnerInterface.LookupType.OrderId, controllerOptions));
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
        java.awt.GridBagConstraints gridBagConstraints;

        btnHeaderPatryId = new javax.swing.JButton();
        textIdField = new ObservingTextField();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 2, 0};
        layout.rowHeights = new int[] {0};
        setLayout(layout);

        btnHeaderPatryId.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\ORDER_small.png")); // NOI18N
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        add(btnHeaderPatryId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(textIdField, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHeaderPatryIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeaderPatryIdActionPerformed

    }//GEN-LAST:event_btnHeaderPatryIdActionPerformed

    @Override
    public EntityCondition getEntityCondition() {
        if (textIdField.getText().isEmpty() == false) {
            return EntityCondition.makeCondition(keyId, EntityOperator.EQUALS, textIdField.getText());
        } else {
            return null;
        }
    }

    @Override
    public String getEntityId() {
        return keyId;
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnHeaderPatryId;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables
}
