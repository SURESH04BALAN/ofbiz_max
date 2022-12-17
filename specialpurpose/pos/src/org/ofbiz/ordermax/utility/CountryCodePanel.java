package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.CountryCode;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class CountryCodePanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =CountryCodePanel.class.getName();

private CountryCode countrycode;

private javax.swing.JLabel countryAbbrLabel;

private javax.swing.JTextField countryAbbrTextField;

private javax.swing.JLabel countryCodeLabel;

private javax.swing.JTextField countryCodeTextField;

private javax.swing.JLabel countryNumberLabel;

private javax.swing.JTextField countryNumberTextField;

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

public CountryCodePanel(CountryCode  val ){

this.countrycode= val;

initComponents();

}

public CountryCodePanel(){

initComponents();

}

private void initComponents() {

countryAbbrLabel = new javax.swing.JLabel();

countryAbbrTextField = new javax.swing.JTextField();

countryAbbrTextField.getDocument().addDocumentListener(new TextChangeListner());

countryCodeLabel = new javax.swing.JLabel();

countryCodeTextField = new javax.swing.JTextField();

countryCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

countryNumberLabel = new javax.swing.JLabel();

countryNumberTextField = new javax.swing.JTextField();

countryNumberTextField.getDocument().addDocumentListener(new TextChangeListner());

countryNameLabel = new javax.swing.JLabel();

countryNameTextField = new javax.swing.JTextField();

countryNameTextField.getDocument().addDocumentListener(new TextChangeListner());

countryAbbrLabel.setText("Country Abbr:");

countryCodeLabel.setText("Country Code:");

countryNumberLabel.setText("Country Number:");

countryNameLabel.setText("Country Name:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryNameLabel)

.add(countryCodeLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(countryCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryAbbrLabel)

.add(countryNumberLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(countryAbbrTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(countryNumberTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

));

layoutPanel.setVerticalGroup(

            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

            .add(layoutPanel.createSequentialGroup()

                .addContainerGap()

                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(countryNameLabel)

.add(countryNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(countryAbbrLabel)

.add(countryAbbrTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(countryCodeLabel)

.add(countryCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(countryNumberLabel)

.add(countryNumberTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addContainerGap()

));

}

 

public void setUIFields() throws java.text.ParseException {

countryAbbrTextField.setText( OrderMaxUtility.getValidString(countrycode.getcountryAbbr()));

countryCodeTextField.setText( OrderMaxUtility.getValidString(countrycode.getcountryCode()));

countryNameTextField.setText( OrderMaxUtility.getValidString(countrycode.getcountryName()));

countryNumberTextField.setText( OrderMaxUtility.getValidString(countrycode.getcountryNumber()));

}

 

public void getUIFields() throws  java.text.ParseException {

countrycode.setcountryAbbr( OrderMaxUtility.getValidString(countryAbbrTextField.getText()));

countrycode.setcountryCode( OrderMaxUtility.getValidString(countryCodeTextField.getText()));

countrycode.setcountryName( OrderMaxUtility.getValidString(countryNameTextField.getText()));

countrycode.setcountryNumber( OrderMaxUtility.getValidString(countryNumberTextField.getText()));

}

public static void createAndShowUI(CountryCode val){

try {

CountryCodePanel panel = new CountryCodePanel(val);

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

CountryCode newObj =null;

if (baseVal != null) {

 newObj = new CountryCode(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new CountryCode();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return countrycode;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof CountryCode) {

CountryCode newObj = (CountryCode) uiObject;

countrycode = newObj;

try { 

countrycode.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("CountryCode", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),CountryCode.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryCodePanel( ); 

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

