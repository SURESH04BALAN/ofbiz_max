package org.ofbiz.ordermax.utility;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.PartyRole;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class PartyRolePanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = PartyRolePanel.class.getName();

    private PartyRole partyrole;

    private javax.swing.JLabel roleTypeIdLabel;

    private javax.swing.JTextField roleTypeIdTextField;

    private javax.swing.JLabel partyIdLabel;

    private javax.swing.JTextField partyIdTextField;

    private JButton button;

    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }

    private boolean isModified = false;

    public PartyRolePanel(PartyRole val) {

        this.partyrole = val;

        initComponents();

    }

    public PartyRolePanel() {
        initComponents();
    }

    private void initComponents() {

        roleTypeIdLabel = new javax.swing.JLabel();

        roleTypeIdTextField = new javax.swing.JTextField();

        roleTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(roleTypeIdTextField);

        button.addActionListener(new LookupActionListner(roleTypeIdTextField, "roleTypeIdTextField"));

        partyIdLabel = new javax.swing.JLabel();

        partyIdTextField = new javax.swing.JTextField();

        partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyIdTextField);

        button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"));

        roleTypeIdLabel.setText("Role Type Id:");

        partyIdLabel.setText("Party Id:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(roleTypeIdLabel)
                        )
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        )
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(partyIdLabel)
                        )
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        )
                        .addContainerGap()
                ));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(roleTypeIdLabel)
                                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(partyIdLabel)
                                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()
                ));

    }

    public void setUIFields() throws java.text.ParseException {

        partyIdTextField.setText(OrderMaxUtility.getValidString(partyrole.getpartyId()));

        roleTypeIdTextField.setText(OrderMaxUtility.getValidString(partyrole.getroleTypeId()));

    }

    public void getUIFields() throws java.text.ParseException {

        partyrole.setpartyId(OrderMaxUtility.getValidString(partyIdTextField.getText()));

        partyrole.setroleTypeId(OrderMaxUtility.getValidString(roleTypeIdTextField.getText()));

    }

    public static void createAndShowUI(PartyRole val) {

        try {

            PartyRolePanel panel = new PartyRolePanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override

    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        PartyRole newObj = null;

        if (baseVal != null) {

            newObj = new PartyRole(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new PartyRole();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return partyrole;

    }

    @Override

    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof PartyRole) {

            PartyRole newObj = (PartyRole) uiObject;

            partyrole = newObj;

            try {

                partyrole.setGenericValue();

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

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PartyRole", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),PartyRole.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PartyRolePanel( ); 

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
