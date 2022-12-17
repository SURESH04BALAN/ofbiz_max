package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.CountryTeleCodeAndName;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class CountryTeleCodeAndNamePanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =CountryTeleCodeAndNamePanel.class.getName();

private CountryTeleCodeAndName countrytelecodeandname;

private javax.swing.JLabel countryCodeLabel;

private javax.swing.JTextField countryCodeTextField;

private javax.swing.JLabel teleCodeLabel;

private javax.swing.JTextField teleCodeTextField;

private javax.swing.JLabel countryNameLabel;

private javax.swing.JTextField countryNameTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public CountryTeleCodeAndNamePanel(CountryTeleCodeAndName  val ){

this.countrytelecodeandname= val;

initComponents();

}

public CountryTeleCodeAndNamePanel(){

initComponents();

}

private void initComponents() {

countryCodeLabel = new javax.swing.JLabel();

countryCodeTextField = new javax.swing.JTextField();

countryCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

teleCodeLabel = new javax.swing.JLabel();

teleCodeTextField = new javax.swing.JTextField();

teleCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

countryNameLabel = new javax.swing.JLabel();

countryNameTextField = new javax.swing.JTextField();

countryNameTextField.getDocument().addDocumentListener(new TextChangeListner());

countryCodeLabel.setText("Country Code:");

teleCodeLabel.setText("Tele Code:");

countryNameLabel.setText("Country Name:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(teleCodeLabel)

.add(countryNameLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(teleCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(countryNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryCodeLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

));

layoutPanel.setVerticalGroup(

            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

            .add(layoutPanel.createSequentialGroup()

                .addContainerGap()

                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(teleCodeLabel)

.add(teleCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(countryCodeLabel)

.add(countryCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(countryNameLabel)

.add(countryNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

)

.addContainerGap()

));

}

 

public void setUIFields() throws java.text.ParseException {

teleCodeTextField.setText( OrderMaxUtility.getValidString(countrytelecodeandname.getteleCode()));

countryCodeTextField.setText( OrderMaxUtility.getValidString(countrytelecodeandname.getcountryCode()));

countryNameTextField.setText( OrderMaxUtility.getValidString(countrytelecodeandname.getcountryName()));

}

 

public void getUIFields() throws  java.text.ParseException {

countrytelecodeandname.setteleCode( OrderMaxUtility.getValidString(teleCodeTextField.getText()));

countrytelecodeandname.setcountryCode( OrderMaxUtility.getValidString(countryCodeTextField.getText()));

countrytelecodeandname.setcountryName( OrderMaxUtility.getValidString(countryNameTextField.getText()));

}

public static void createAndShowUI(CountryTeleCodeAndName val){

try {

CountryTeleCodeAndNamePanel panel = new CountryTeleCodeAndNamePanel(val);

JFrame frame = new JFrame("Test Gui");

frame.getContentPane().add(panel);

panel.setUIFields();

frame.pack();

frame.setLocationRelativeTo(null);

frame.setVisible(true);

}  catch (java.text.ParseException ex) {

    Debug.logError(ex,module);        

}

}

@Override

public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

CountryTeleCodeAndName newObj =null;

if (baseVal != null) {

 newObj = new CountryTeleCodeAndName(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new CountryTeleCodeAndName();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return countrytelecodeandname;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof CountryTeleCodeAndName) {

CountryTeleCodeAndName newObj = (CountryTeleCodeAndName) uiObject;

countrytelecodeandname = newObj;

try { 

countrytelecodeandname.setGenericValue();

} catch (Exception ex) {

//Debug.logError (ex, module);

}

 }

 }

public JPanel getContainerPanel(){ return this; }

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("CountryTeleCodeAndName", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),CountryTeleCodeAndName.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryTeleCodeAndNamePanel( ); 

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

