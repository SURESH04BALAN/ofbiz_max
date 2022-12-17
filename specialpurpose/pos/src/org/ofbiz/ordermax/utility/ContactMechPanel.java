package org.ofbiz.ordermax.utility;

import javax.swing.JFrame;

import javax.swing.JPanel;



import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.ContactMech;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ContactMechPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = ContactMechPanel.class.getName();
    private ContactMech contactmech;
    private javax.swing.JLabel infoStringLabel;
    private javax.swing.JTextField infoStringTextField;
    private javax.swing.JLabel contactMechIdLabel;
    private javax.swing.JTextField contactMechIdTextField;
    private javax.swing.JLabel contactMechTypeIdLabel;
    private javax.swing.JTextField contactMechTypeIdTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }
    private boolean isModified = false;

    public ContactMechPanel(ContactMech val) {

        this.contactmech = val;

        initComponents();

    }

    public ContactMechPanel() {

        initComponents();

    }

    private void initComponents() {

        infoStringLabel = new javax.swing.JLabel();

        infoStringTextField = new javax.swing.JTextField();
        infoStringTextField.getDocument().addDocumentListener(new TextChangeListner());

        contactMechIdLabel = new javax.swing.JLabel();

        contactMechIdTextField = new javax.swing.JTextField();
        contactMechIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(contactMechIdTextField);

        button.addActionListener(new LookupActionListner(contactMechIdTextField, "contactMechIdTextField"));

        contactMechTypeIdLabel = new javax.swing.JLabel();

        contactMechTypeIdTextField = new javax.swing.JTextField();

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(contactMechTypeIdTextField);

        button.addActionListener(new LookupActionListner(contactMechTypeIdTextField, "contactMechTypeIdTextField"));

        infoStringLabel.setText("Info String:");

        contactMechIdLabel.setText("Contact Mech Id:");

        contactMechTypeIdLabel.setText("Contact Mech Type Id:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(contactMechIdLabel)
                .add(contactMechTypeIdLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(contactMechTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(infoStringLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(infoStringTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(contactMechIdLabel)
                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(infoStringLabel)
                .add(infoStringTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(contactMechTypeIdLabel)
                .add(contactMechTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        infoStringTextField.setText(OrderMaxUtility.getValidString(contactmech.getinfoString()));

        contactMechIdTextField.setText(OrderMaxUtility.getValidString(contactmech.getcontactMechId()));

        contactMechTypeIdTextField.setText(OrderMaxUtility.getValidString(contactmech.getcontactMechTypeId()));

    }

    public void getUIFields() throws java.text.ParseException {

        contactmech.setinfoString(OrderMaxUtility.getValidString(infoStringTextField.getText()));

        contactmech.setcontactMechId(OrderMaxUtility.getValidString(contactMechIdTextField.getText()));

        contactmech.setcontactMechTypeId(OrderMaxUtility.getValidString(contactMechTypeIdTextField.getText()));

    }

    public static void createAndShowUI(ContactMech val) {

        try {

            ContactMechPanel panel = new ContactMechPanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        }  catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        ContactMech newObj = null;

        if (baseVal != null) {

            newObj = new ContactMech(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new ContactMech();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return contactmech;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof ContactMech) {

            ContactMech newObj = (ContactMech) uiObject;

            contactmech = newObj;

            try {

                contactmech.setGenericValue();

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
            Debug.logInfo("Text Change: " +  e.getDocument().toString(), module);

        }
    }
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("ContactMech", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),ContactMech.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.ContactMechPanel( ); 

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
