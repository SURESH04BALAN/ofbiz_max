package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.CountryTeleCode;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class CountryTeleCodePanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =CountryTeleCodePanel.class.getName();

private CountryTeleCode countrytelecode;

private javax.swing.JLabel countryCodeLabel;

private javax.swing.JTextField countryCodeTextField;

private javax.swing.JLabel teleCodeLabel;

private javax.swing.JTextField teleCodeTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public CountryTeleCodePanel(CountryTeleCode  val ){

this.countrytelecode= val;

initComponents();

}

public CountryTeleCodePanel(){

initComponents();

}

private void initComponents() {

countryCodeLabel = new javax.swing.JLabel();

countryCodeTextField = new javax.swing.JTextField();

countryCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

teleCodeLabel = new javax.swing.JLabel();

teleCodeTextField = new javax.swing.JTextField();

teleCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

countryCodeLabel.setText("Country Code:");

teleCodeLabel.setText("Tele Code:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(teleCodeLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(teleCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

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

.addContainerGap()

));

}

 

public void setUIFields() throws  java.text.ParseException {

teleCodeTextField.setText( OrderMaxUtility.getValidString(countrytelecode.getteleCode()));

countryCodeTextField.setText( OrderMaxUtility.getValidString(countrytelecode.getcountryCode()));

}

 

public void getUIFields() throws  java.text.ParseException {

countrytelecode.setteleCode( OrderMaxUtility.getValidString(teleCodeTextField.getText()));

countrytelecode.setcountryCode( OrderMaxUtility.getValidString(countryCodeTextField.getText()));

}

public static void createAndShowUI(CountryTeleCode val){

try {

CountryTeleCodePanel panel = new CountryTeleCodePanel(val);

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

CountryTeleCode newObj =null;

if (baseVal != null) {

 newObj = new CountryTeleCode(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new CountryTeleCode();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return countrytelecode;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof CountryTeleCode) {

CountryTeleCode newObj = (CountryTeleCode) uiObject;

countrytelecode = newObj;

try { 

countrytelecode.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("CountryTeleCode", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),CountryTeleCode.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryTeleCodePanel( ); 

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

