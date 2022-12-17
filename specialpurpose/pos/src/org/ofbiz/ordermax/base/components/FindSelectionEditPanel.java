/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;

/**
 *
 * @author siranjeev
 */
public class FindSelectionEditPanel extends javax.swing.JPanel implements ReportCreatorSelectionInterface {

    static public String module = FindSelectionEditPanel.class.getName();
    //public final static String ReturnEditorId = "selectionIdTextField";
    //public String keyId = "";
    protected FindSelectionEditOption editOption = new FindSelectionEditOption();
    public JGenericComboBoxSelectionModel<String> comboCondition;
    public boolean fieldTypeDecimal = false;

    /**
     * Creates new form DatePickerEditPanel
     */
/*    public FindSelectionEditPanel(ControllerOptions controllerOptions, String key) {
        initComponents();
        editOption.keyId = key;

        
        comboCondition = new JGenericComboBoxSelectionModel<String>(panelCondition, ConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        try {
            comboCondition.setSelectedItem(ConditionSelectSingleton.getString(ConditionSelectSingleton.EQUALS));
//        btnHeaderPatryId.addActionListener(new LookupActionListner(LookupActionListner.LookupType.ReturnId, controllerOptions));
        } catch (Exception ex) {
            Logger.getLogger(FindSelectionEditPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    public FindSelectionEditPanel(ControllerOptions controllerOptions, String key, String defCondition) {
        initComponents();
        editOption.keyId = key;

        //controllerOptions.put(ReturnEditorId, textIdField);
        comboCondition = new JGenericComboBoxSelectionModel<String>(panelCondition, ConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        try {
            comboCondition.setSelectedItem(ConditionSelectSingleton.getString(defCondition));
//        btnHeaderPatryId.addActionListener(new LookupActionListner(LookupActionListner.LookupType.ReturnId, controllerOptions));
        } catch (Exception ex) {
            Logger.getLogger(FindSelectionEditPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        textIdField = new javax.swing.JTextField();
        cbProcessing = new javax.swing.JCheckBox();
        panelCondition = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(374, 28));

        textIdField.setMaximumSize(new java.awt.Dimension(200, 2147483647));
        textIdField.setPreferredSize(new java.awt.Dimension(150, 22));

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");

        javax.swing.GroupLayout panelConditionLayout = new javax.swing.GroupLayout(panelCondition);
        panelCondition.setLayout(panelConditionLayout);
        panelConditionLayout.setHorizontalGroup(
            panelConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        panelConditionLayout.setVerticalGroup(
            panelConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(textIdField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProcessing))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textIdField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cbProcessing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbProcessing, panelCondition, textIdField});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox cbProcessing;
    private javax.swing.JPanel panelCondition;
    public javax.swing.JTextField textIdField;
    // End of variables declaration//GEN-END:variables

    @Override
    public EntityCondition getEntityCondition() {
        getDialogFields();

        if (editOption.value != null && editOption.op != null && textIdField.getText() != null) {
            try {
                String fieldName = editOption.keyId; //editOption.ignoreCase ? "upper(" + editOption.keyId + ")" : editOption.keyId;
                String fieldValue = textIdField.getText();//editOption.ignoreCase ? textIdField.getText().toUpperCase() : textIdField.getText();

                //EntityComparisonOperator op = ConditionSelectSingleton.getOperator(ConditionSelectSingleton.getKeyString(comboCondition.getSelectedItem()));
                //andExprs.add(new EntityExpr("lastName", true, EntityOperator.LIKE, "%"+lastName+"%", true))
                if (editOption.op == EntityOperator.LIKE) {
                    if (editOption.ignoreCase) {
                        return EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(fieldName), editOption.op, EntityFunction.UPPER("%" + fieldValue + "%"));
                    } else {
                        return EntityCondition.makeCondition(fieldName, editOption.op, "%" + fieldValue + "%");
                    }
                } else {
                    if (editOption.ignoreCase) {
                        return EntityCondition.makeCondition(EntityFunction.UPPER_FIELD(fieldName), editOption.op, EntityFunction.UPPER(fieldValue));
                    } else {
                        return EntityCondition.makeCondition(fieldName, editOption.op, fieldValue);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(FindSelectionEditPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    @Override
    public String getEntityId() {
        return editOption.keyId;
    }

    @Override
    public Object getEntityValue() {
        return editOption.value;
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        getDialogFields();

        String val = (String) getEntityValue();
        if (val != null) {
            if (fieldTypeDecimal) {
                valueMap.put(getEntityId(), new BigDecimal(val));
            } else {
                valueMap.put(getEntityId(), val);
            }
        }
    }

    public void getDialogFields() {
        try {
            editOption.op = ConditionSelectSingleton.getOperator(ConditionSelectSingleton.getKeyString(comboCondition.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindSelectionEditPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(textIdField.getText())) {
            editOption.value = textIdField.getText();
        } else {
            editOption.value = null;
        }

        editOption.ignoreCase = cbProcessing.isSelected();
    }

    public void setDialogFields() {
        try {
            editOption.op = ConditionSelectSingleton.getOperator(ConditionSelectSingleton.getKeyString(comboCondition.getSelectedItem()));
        } catch (Exception ex) {
            Logger.getLogger(FindSelectionEditPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(textIdField.getText())) {
            editOption.value = textIdField.getText();
        } else {
            editOption.value = null;
        }
        editOption.ignoreCase = cbProcessing.isSelected();
    }

    public FindSelectionEditOption getFindSelectionOptions() {
        getDialogFields();
        return editOption;
    }

    public boolean isValidOption() {
        return UtilValidate.isNotEmpty(textIdField.getText());
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void setEntityValue(java.lang.Object value) {
        textIdField.setText((String) value);
    }

    public static class FindSelectionEditOption {

        public String keyId;
        public EntityComparisonOperator op = null;
        public String value = null;
        public boolean ignoreCase = true;

        public FindSelectionEditOption() {

        }

    }

}