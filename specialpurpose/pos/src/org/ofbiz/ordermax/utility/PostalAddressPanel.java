package org.ofbiz.ordermax.utility;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.PostalAddress;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class PostalAddressPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = PostalAddressPanel.class.getName();
    private PostalAddress postaladdress;
    private javax.swing.JLabel toNameLabel;
    private javax.swing.JTextField toNameTextField;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JLabel geoPointIdLabel;
    private javax.swing.JTextField geoPointIdTextField;
    private javax.swing.JLabel directionsLabel;
    private javax.swing.JTextField directionsTextField;
    private javax.swing.JLabel postalCodeGeoIdLabel;
    private javax.swing.JTextField postalCodeGeoIdTextField;
    private javax.swing.JLabel contactMechIdLabel;
    private javax.swing.JTextField contactMechIdTextField;
    private javax.swing.JLabel attnNameLabel;
    private javax.swing.JTextField attnNameTextField;
    private javax.swing.JLabel stateProvinceGeoIdLabel;
    private javax.swing.JTextField stateProvinceGeoIdTextField;
    private javax.swing.JLabel address2Label;
    private javax.swing.JTextField address2TextField;
    private javax.swing.JLabel address1Label;
    private javax.swing.JTextField address1TextField;
    private javax.swing.JLabel postalCodeLabel;
    private javax.swing.JTextField postalCodeTextField;
    private javax.swing.JLabel countryGeoIdLabel;
    private javax.swing.JTextField countryGeoIdTextField;
    private javax.swing.JLabel postalCodeExtLabel;
    private javax.swing.JTextField postalCodeExtTextField;
    private javax.swing.JLabel countyGeoIdLabel;
    private javax.swing.JTextField countyGeoIdTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {
            Debug.logInfo("isModified() isModified " + isModified, module);
        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;
            Debug.logInfo("setIsModified isModified " + isModified, module);
    }
    private boolean isModified = false;

    public PostalAddressPanel(PostalAddress val) {

        this.postaladdress = val;

        initComponents();

    }

    public PostalAddressPanel() {

        initComponents();

    }

    private void initComponents() {

        toNameLabel = new javax.swing.JLabel();

        toNameTextField = new javax.swing.JTextField();

        toNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        cityLabel = new javax.swing.JLabel();

        cityTextField = new javax.swing.JTextField();

        cityTextField.getDocument().addDocumentListener(new TextChangeListner());

        geoPointIdLabel = new javax.swing.JLabel();

        geoPointIdTextField = new javax.swing.JTextField();

        geoPointIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(geoPointIdTextField);

        button.addActionListener(new LookupActionListner(geoPointIdTextField, "geoPointIdTextField"));

        directionsLabel = new javax.swing.JLabel();

        directionsTextField = new javax.swing.JTextField();

        directionsTextField.getDocument().addDocumentListener(new TextChangeListner());

        postalCodeGeoIdLabel = new javax.swing.JLabel();

        postalCodeGeoIdTextField = new javax.swing.JTextField();

        postalCodeGeoIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(postalCodeGeoIdTextField);

        button.addActionListener(new LookupActionListner(postalCodeGeoIdTextField, "postalCodeGeoIdTextField"));

        contactMechIdLabel = new javax.swing.JLabel();

        contactMechIdTextField = new javax.swing.JTextField();

        contactMechIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(contactMechIdTextField);

        button.addActionListener(new LookupActionListner(contactMechIdTextField, "contactMechIdTextField"));

        attnNameLabel = new javax.swing.JLabel();

        attnNameTextField = new javax.swing.JTextField();

        attnNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        stateProvinceGeoIdLabel = new javax.swing.JLabel();

        stateProvinceGeoIdTextField = new javax.swing.JTextField();

        stateProvinceGeoIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(stateProvinceGeoIdTextField);

        button.addActionListener(new LookupActionListner(stateProvinceGeoIdTextField, "stateProvinceGeoIdTextField"));

        address2Label = new javax.swing.JLabel();

        address2TextField = new javax.swing.JTextField();

        address2TextField.getDocument().addDocumentListener(new TextChangeListner());

        address1Label = new javax.swing.JLabel();

        address1TextField = new javax.swing.JTextField();

        address1TextField.getDocument().addDocumentListener(new TextChangeListner());

        postalCodeLabel = new javax.swing.JLabel();

        postalCodeTextField = new javax.swing.JTextField();

        postalCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

        countryGeoIdLabel = new javax.swing.JLabel();

        countryGeoIdTextField = new javax.swing.JTextField();

        countryGeoIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(countryGeoIdTextField);

        button.addActionListener(new LookupActionListner(countryGeoIdTextField, "countryGeoIdTextField"));

        postalCodeExtLabel = new javax.swing.JLabel();

        postalCodeExtTextField = new javax.swing.JTextField();

        postalCodeExtTextField.getDocument().addDocumentListener(new TextChangeListner());

        countyGeoIdLabel = new javax.swing.JLabel();

        countyGeoIdTextField = new javax.swing.JTextField();

        countyGeoIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(countyGeoIdTextField);

        button.addActionListener(new LookupActionListner(countyGeoIdTextField, "countyGeoIdTextField"));

        toNameLabel.setText("To Name:");

        cityLabel.setText("City:");

        geoPointIdLabel.setText("Geo Point Id:");

        directionsLabel.setText("Directions:");

        postalCodeGeoIdLabel.setText("Postal Code Geo Id:");

        contactMechIdLabel.setText("Contact Mech Id:");

        attnNameLabel.setText("Attn Name:");

        stateProvinceGeoIdLabel.setText("State Province Geo Id:");

        address2Label.setText("Address 2:");

        address1Label.setText("Address 1:");

        postalCodeLabel.setText("Postal Code:");

        countryGeoIdLabel.setText("Country Geo Id:");

        postalCodeExtLabel.setText("Postal Code Ext:");

        countyGeoIdLabel.setText("County Geo Id:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(countryGeoIdLabel)
                .add(countyGeoIdLabel)
                .add(contactMechIdLabel)
                .add(postalCodeGeoIdLabel)
                .add(stateProvinceGeoIdLabel)
                .add(geoPointIdLabel)
                .add(directionsLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(countryGeoIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(countyGeoIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(postalCodeGeoIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(stateProvinceGeoIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(geoPointIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(directionsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(postalCodeLabel)
                .add(postalCodeExtLabel)
                .add(toNameLabel)
                .add(attnNameLabel)
                .add(address1Label)
                .add(address2Label)
                .add(cityLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(postalCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(postalCodeExtTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(toNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(attnNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(address1TextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(address2TextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(cityTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(countryGeoIdLabel)
                .add(countryGeoIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(postalCodeLabel)
                .add(postalCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(countyGeoIdLabel)
                .add(countyGeoIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(postalCodeExtLabel)
                .add(postalCodeExtTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(contactMechIdLabel)
                .add(contactMechIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(toNameLabel)
                .add(toNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(postalCodeGeoIdLabel)
                .add(postalCodeGeoIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(attnNameLabel)
                .add(attnNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(stateProvinceGeoIdLabel)
                .add(stateProvinceGeoIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(address1Label)
                .add(address1TextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(geoPointIdLabel)
                .add(geoPointIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(address2Label)
                .add(address2TextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(directionsLabel)
                .add(directionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(cityLabel)
                .add(cityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        cityTextField.setText(OrderMaxUtility.getValidString(postaladdress.getCity()));

        postalCodeTextField.setText(OrderMaxUtility.getValidString(postaladdress.getPostalCode()));

        toNameTextField.setText(OrderMaxUtility.getValidString(postaladdress.getToName()));

        postalCodeExtTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeExt()));

        stateProvinceGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getstateProvinceGeoId()));

        address2TextField.setText(OrderMaxUtility.getValidString(postaladdress.getAddress2()));

        countyGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountyGeoId()));

        attnNameTextField.setText(OrderMaxUtility.getValidString(postaladdress.getAttnName()));

        directionsTextField.setText(OrderMaxUtility.getValidString(postaladdress.getDirections()));

        postalCodeGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeGeoId()));

        contactMechIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getContactMechId()));

        address1TextField.setText(OrderMaxUtility.getValidString(postaladdress.getAddress1()));

        geoPointIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getgeoPointId()));

        countryGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountryGeoId()));

    }

    public void getUIFields() throws java.text.ParseException {

        postaladdress.setCity(OrderMaxUtility.getValidString(cityTextField.getText()));

        postaladdress.setpostalCode(OrderMaxUtility.getValidString(postalCodeTextField.getText()));

        postaladdress.setToName(OrderMaxUtility.getValidString(toNameTextField.getText()));

        postaladdress.setpostalCodeExt(OrderMaxUtility.getValidString(postalCodeExtTextField.getText()));

        postaladdress.setstateProvinceGeoId(OrderMaxUtility.getValidString(stateProvinceGeoIdTextField.getText()));

        postaladdress.setAddress2(OrderMaxUtility.getValidString(address2TextField.getText()));

        postaladdress.setcountyGeoId(OrderMaxUtility.getValidString(countyGeoIdTextField.getText()));

        postaladdress.setAttnName(OrderMaxUtility.getValidString(attnNameTextField.getText()));

        postaladdress.setDirections(OrderMaxUtility.getValidString(directionsTextField.getText()));

        postaladdress.setpostalCodeGeoId(OrderMaxUtility.getValidString(postalCodeGeoIdTextField.getText()));

        postaladdress.setContactMechId(OrderMaxUtility.getValidString(contactMechIdTextField.getText()));

        postaladdress.setAddress1(OrderMaxUtility.getValidString(address1TextField.getText()));

        postaladdress.setgeoPointId(OrderMaxUtility.getValidString(geoPointIdTextField.getText()));

        postaladdress.setcountryGeoId(OrderMaxUtility.getValidString(countryGeoIdTextField.getText()));

    }

    public static void createAndShowUI(PostalAddress val) {

        try {

            PostalAddressPanel panel = new PostalAddressPanel(val);

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

        PostalAddress newObj = null;

        if (baseVal != null) {

            newObj = new PostalAddress(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new PostalAddress();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return postaladdress;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof PostalAddress) {

            PostalAddress newObj = (PostalAddress) uiObject;

            postaladdress = newObj;

            try {

                postaladdress.setGenericValue();

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
            Debug.logInfo("postaladdress isModified " + isModified, module);
        }
    }
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PostalAddress", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),PostalAddress.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PostalAddressPanel( ); 

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
