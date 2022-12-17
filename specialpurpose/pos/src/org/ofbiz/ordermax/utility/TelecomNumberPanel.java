package org.ofbiz.ordermax.utility;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.TelecomNumber;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class TelecomNumberPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = TelecomNumberPanel.class.getName();

    private TelecomNumber telecomnumber;

    private javax.swing.JLabel contactMechIdLabel;

    private javax.swing.JTextField contactMechIdTextField;

    private javax.swing.JLabel areaCodeLabel;

    private javax.swing.JTextField areaCodeTextField;

    private javax.swing.JLabel contactNumberLabel;

    private javax.swing.JTextField contactNumberTextField;

    private javax.swing.JLabel countryCodeLabel;

    private javax.swing.JTextField countryCodeTextField;

    private javax.swing.JLabel askForNameLabel;

    private javax.swing.JTextField askForNameTextField;

    private JButton button;

    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }

    private boolean isModified = false;

    public TelecomNumberPanel(TelecomNumber val) {

        this.telecomnumber = val;

        initComponents();

    }

    public TelecomNumberPanel() {

        initComponents();

    }

    private void initComponents() {

        contactMechIdLabel = new javax.swing.JLabel();

        contactMechIdTextField = new javax.swing.JTextField();

        contactMechIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(contactMechIdTextField);

        button.addActionListener(new LookupActionListner(contactMechIdTextField, "contactMechIdTextField"));

        areaCodeLabel = new javax.swing.JLabel();

        areaCodeTextField = new javax.swing.JTextField();

        areaCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

        contactNumberLabel = new javax.swing.JLabel();

        contactNumberTextField = new javax.swing.JTextField();

        contactNumberTextField.getDocument().addDocumentListener(new TextChangeListner());

        countryCodeLabel = new javax.swing.JLabel();

        countryCodeTextField = new javax.swing.JTextField();

        countryCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

        askForNameLabel = new javax.swing.JLabel();

        askForNameTextField = new javax.swing.JTextField();

        askForNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        contactMechIdLabel.setText("Contact Mech Id:");

        areaCodeLabel.setText("Area Code:");

        contactNumberLabel.setText("Contact Number:");

        countryCodeLabel.setText("Country Code:");

        askForNameLabel.setText("Ask For Name:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(contactMechIdLabel)
                                .add(contactNumberLabel)
                                .add(areaCodeLabel)
                        )
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .add(contactNumberTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .add(areaCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        )
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(askForNameLabel)
                                .add(countryCodeLabel)
                        )
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(askForNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .add(countryCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        )
                        .addContainerGap()
                ));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(contactMechIdLabel)
                                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(askForNameLabel)
                                .add(askForNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(contactNumberLabel)
                                .add(contactNumberTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(countryCodeLabel)
                                .add(countryCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(areaCodeLabel)
                                .add(areaCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        )
                        .addContainerGap()
                ));

    }

    public void setUIFields() throws java.text.ParseException {

        countryCodeTextField.setText(OrderMaxUtility.getValidString(telecomnumber.getcountryCode()));

        areaCodeTextField.setText(OrderMaxUtility.getValidString(telecomnumber.getareaCode()));

        contactNumberTextField.setText(OrderMaxUtility.getValidString(telecomnumber.getcontactNumber()));

        contactMechIdTextField.setText(OrderMaxUtility.getValidString(telecomnumber.getcontactMechId()));

        askForNameTextField.setText(OrderMaxUtility.getValidString(telecomnumber.getaskForName()));

    }

    public void getUIFields() throws java.text.ParseException {

        telecomnumber.setcountryCode(OrderMaxUtility.getValidString(countryCodeTextField.getText()));

        telecomnumber.setareaCode(OrderMaxUtility.getValidString(areaCodeTextField.getText()));

        telecomnumber.setcontactNumber(OrderMaxUtility.getValidString(contactNumberTextField.getText()));

        telecomnumber.setcontactMechId(OrderMaxUtility.getValidString(contactMechIdTextField.getText()));

        telecomnumber.setaskForName(OrderMaxUtility.getValidString(askForNameTextField.getText()));

    }

    public static void createAndShowUI(TelecomNumber val) {

        try {

            TelecomNumberPanel panel = new TelecomNumberPanel(val);

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

        TelecomNumber newObj = null;

        if (baseVal != null) {

            newObj = new TelecomNumber(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new TelecomNumber();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return telecomnumber;

    }

    @Override

    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof TelecomNumber) {

            TelecomNumber newObj = (TelecomNumber) uiObject;

            telecomnumber = newObj;

            try {

                telecomnumber.setGenericValue();

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

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("TelecomNumber", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),TelecomNumber.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.TelecomNumberPanel( ); 

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
