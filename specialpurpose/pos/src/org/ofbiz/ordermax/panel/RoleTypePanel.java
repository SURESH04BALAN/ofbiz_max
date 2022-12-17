package org.ofbiz.ordermax.panel;


import java.text.ParseException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class RoleTypePanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = RoleTypePanel.class.getName();
    private RoleType roletype;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JLabel roleTypeIdLabel;
    private javax.swing.JTextField roleTypeIdTextField;
    private javax.swing.JLabel hasTableLabel;
    private javax.swing.JTextField hasTableTextField;
    private javax.swing.JLabel parentTypeIdLabel;
    private javax.swing.JTextField parentTypeIdTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    private boolean isModified = false;

    public RoleTypePanel(RoleType val) {

        this.roletype = val;

        initComponents();

    }

    public RoleTypePanel() {

        initComponents();

    }

    private void initComponents() {

        descriptionLabel = new javax.swing.JLabel();

        descriptionTextField = new javax.swing.JTextField();

        descriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

        roleTypeIdLabel = new javax.swing.JLabel();

        roleTypeIdTextField = new javax.swing.JTextField();

        roleTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(roleTypeIdTextField);

        button.addActionListener(new LookupActionListner(roleTypeIdTextField, "roleTypeIdTextField"));

        hasTableLabel = new javax.swing.JLabel();

        hasTableTextField = new javax.swing.JTextField();

        hasTableTextField.getDocument().addDocumentListener(new TextChangeListner());

        parentTypeIdLabel = new javax.swing.JLabel();

        parentTypeIdTextField = new javax.swing.JTextField();

        parentTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(parentTypeIdTextField);

        button.addActionListener(new LookupActionListner(parentTypeIdTextField, "parentTypeIdTextField"));

        descriptionLabel.setText("Description:");

        roleTypeIdLabel.setText("Role Type Id:");

        hasTableLabel.setText("Has Table:");

        parentTypeIdLabel.setText("Parent Type Id:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(roleTypeIdLabel)
                .add(parentTypeIdLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(parentTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(hasTableLabel)
                .add(descriptionLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(hasTableTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(roleTypeIdLabel)
                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(hasTableLabel)
                .add(hasTableTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(parentTypeIdLabel)
                .add(parentTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(descriptionLabel)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        roleTypeIdTextField.setText(OrderMaxUtility.getValidString(roletype.getroleTypeId()));

        hasTableTextField.setText(OrderMaxUtility.getValidString(roletype.gethasTable()));

        descriptionTextField.setText(OrderMaxUtility.getValidString(roletype.getdescription()));

        parentTypeIdTextField.setText(OrderMaxUtility.getValidString(roletype.getparentTypeId()));

    }

    public void getUIFields() throws java.text.ParseException {

        roletype.setroleTypeId(OrderMaxUtility.getValidString(roleTypeIdTextField.getText()));

        roletype.sethasTable(OrderMaxUtility.getValidString(hasTableTextField.getText()));

        roletype.setdescription(OrderMaxUtility.getValidString(descriptionTextField.getText()));

        roletype.setparentTypeId(OrderMaxUtility.getValidString(parentTypeIdTextField.getText()));

    }

    public static void createAndShowUI(RoleType val) {

        try {

            RoleTypePanel panel = new RoleTypePanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (ParseException ex) {

            Debug.logError(ex, module);

        } 

    }

//    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        RoleType newObj = null;

        if (baseVal != null) {

            newObj = new RoleType(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new RoleType();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return roletype;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof RoleType) {

            RoleType newObj = (RoleType) uiObject;

            roletype = newObj;

            try {

                roletype.setGenericValue();

            } catch (Exception ex) {
//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    private class TextChangeListner implements DocumentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {

            warn(e);

        }

        public void removeUpdate(DocumentEvent e) {

            warn(e);

        }

        public void insertUpdate(DocumentEvent e) {

            warn(e);

        }

        void warn(DocumentEvent e) {

            isModified = true;

        }
    }
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("RoleType", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),RoleType.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.RoleTypePanel( ); 

 Object uiObj = panel.createUIObject(val);

 panel.changeUIObject(uiObj);

 panel.setUIFields();

 dlg.setChildPanelInterface(panel);

 OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

 dlg.setLocationRelativeTo(null);

 dlg.pack();

 dlg.setVisible(true);

 } catch (ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 } catch (java.text.ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 }

 */
