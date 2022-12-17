package org.ofbiz.ordermax.utility;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.CountryCapital;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class CountryCapitalPanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =CountryCapitalPanel.class.getName();

private CountryCapital countrycapital;

private javax.swing.JLabel countryCapitalLabel;

private javax.swing.JTextField countryCapitalTextField;

private javax.swing.JLabel countryCodeLabel;

private javax.swing.JTextField countryCodeTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public CountryCapitalPanel(CountryCapital  val ){

this.countrycapital= val;

initComponents();

}

public CountryCapitalPanel(){

initComponents();

}

private void initComponents() {

countryCapitalLabel = new javax.swing.JLabel();

countryCapitalTextField = new javax.swing.JTextField();

countryCapitalTextField.getDocument().addDocumentListener(new TextChangeListner());

countryCodeLabel = new javax.swing.JLabel();

countryCodeTextField = new javax.swing.JTextField();

countryCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

countryCapitalLabel.setText("Country Capital:");

countryCodeLabel.setText("Country Code:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryCapitalLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryCapitalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

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

.add(countryCapitalLabel)

.add(countryCapitalTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(countryCodeLabel)

.add(countryCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addContainerGap()

));

}

 

public void setUIFields() throws java.text.ParseException {

countryCodeTextField.setText( OrderMaxUtility.getValidString(countrycapital.getcountryCode()));

countryCapitalTextField.setText( OrderMaxUtility.getValidString(countrycapital.getcountryCapital()));

}

 

public void getUIFields() throws  java.text.ParseException {

countrycapital.setcountryCode( OrderMaxUtility.getValidString(countryCodeTextField.getText()));

countrycapital.setcountryCapital( OrderMaxUtility.getValidString(countryCapitalTextField.getText()));

}

public static void createAndShowUI(CountryCapital val){

try {

CountryCapitalPanel panel = new CountryCapitalPanel(val);

JFrame frame = new JFrame("Test Gui");

frame.getContentPane().add(panel);

panel.setUIFields();

frame.pack();

frame.setLocationRelativeTo(null);

frame.setVisible(true);

} catch (java.text.ParseException ex) {

    Debug.logError(ex,module);        

}

}

@Override

public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

CountryCapital newObj =null;

if (baseVal != null) {

 newObj = new CountryCapital(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new CountryCapital();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return countrycapital;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof CountryCapital) {

CountryCapital newObj = (CountryCapital) uiObject;

countrycapital = newObj;

try { 

countrycapital.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("CountryCapital", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),CountryCapital.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryCapitalPanel( ); 

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

