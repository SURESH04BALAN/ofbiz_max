package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.Party;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class PartyPanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =PartyPanel.class.getName();

private Party party;

private javax.swing.JLabel preferredCurrencyUomIdLabel;

private javax.swing.JTextField preferredCurrencyUomIdTextField;

private javax.swing.JLabel statusIdLabel;

private javax.swing.JTextField statusIdTextField;

private javax.swing.JLabel descriptionLabel;

private javax.swing.JTextField descriptionTextField;

private javax.swing.JLabel createdDateLabel;

private javax.swing.JTextField createdDateTextField;

private javax.swing.JLabel partyIdLabel;

private javax.swing.JTextField partyIdTextField;

private javax.swing.JLabel isUnreadLabel;

private javax.swing.JTextField isUnreadTextField;

private javax.swing.JLabel createdByUserLoginLabel;

private javax.swing.JTextField createdByUserLoginTextField;

private javax.swing.JLabel dataSourceIdLabel;

private javax.swing.JTextField dataSourceIdTextField;

private javax.swing.JLabel partyTypeIdLabel;

private javax.swing.JTextField partyTypeIdTextField;

private javax.swing.JLabel externalIdLabel;

private javax.swing.JTextField externalIdTextField;

private javax.swing.JLabel lastModifiedDateLabel;

private javax.swing.JTextField lastModifiedDateTextField;

private javax.swing.JLabel lastModifiedByUserLoginLabel;

private javax.swing.JTextField lastModifiedByUserLoginTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public PartyPanel(Party  val ){

this.party= val;

initComponents();

}

public PartyPanel(){

initComponents();

}

private void initComponents() {

preferredCurrencyUomIdLabel = new javax.swing.JLabel();

preferredCurrencyUomIdTextField = new javax.swing.JTextField();

preferredCurrencyUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(preferredCurrencyUomIdTextField);

button.addActionListener(new LookupActionListner(preferredCurrencyUomIdTextField, "preferredCurrencyUomIdTextField"  ));

statusIdLabel = new javax.swing.JLabel();

statusIdTextField = new javax.swing.JTextField();

statusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(statusIdTextField);

button.addActionListener(new LookupActionListner(statusIdTextField, "statusIdTextField"  ));

descriptionLabel = new javax.swing.JLabel();

descriptionTextField = new javax.swing.JTextField();

descriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

createdDateLabel = new javax.swing.JLabel();

createdDateTextField = new javax.swing.JTextField();

createdDateTextField.getDocument().addDocumentListener(new TextChangeListner());

partyIdLabel = new javax.swing.JLabel();

partyIdTextField = new javax.swing.JTextField();

partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(partyIdTextField);

button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"  ));

isUnreadLabel = new javax.swing.JLabel();

isUnreadTextField = new javax.swing.JTextField();

isUnreadTextField.getDocument().addDocumentListener(new TextChangeListner());

createdByUserLoginLabel = new javax.swing.JLabel();

createdByUserLoginTextField = new javax.swing.JTextField();

createdByUserLoginTextField.getDocument().addDocumentListener(new TextChangeListner());

dataSourceIdLabel = new javax.swing.JLabel();

dataSourceIdTextField = new javax.swing.JTextField();

dataSourceIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(dataSourceIdTextField);

button.addActionListener(new LookupActionListner(dataSourceIdTextField, "dataSourceIdTextField"  ));

partyTypeIdLabel = new javax.swing.JLabel();

partyTypeIdTextField = new javax.swing.JTextField();

partyTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(partyTypeIdTextField);

button.addActionListener(new LookupActionListner(partyTypeIdTextField, "partyTypeIdTextField"  ));

externalIdLabel = new javax.swing.JLabel();

externalIdTextField = new javax.swing.JTextField();

externalIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(externalIdTextField);

button.addActionListener(new LookupActionListner(externalIdTextField, "externalIdTextField"  ));

lastModifiedDateLabel = new javax.swing.JLabel();

lastModifiedDateTextField = new javax.swing.JTextField();

lastModifiedDateTextField.getDocument().addDocumentListener(new TextChangeListner());

lastModifiedByUserLoginLabel = new javax.swing.JLabel();

lastModifiedByUserLoginTextField = new javax.swing.JTextField();

lastModifiedByUserLoginTextField.getDocument().addDocumentListener(new TextChangeListner());

preferredCurrencyUomIdLabel.setText("Preferred Currency Uom Id:");

statusIdLabel.setText("Status Id:");

descriptionLabel.setText("Description:");

createdDateLabel.setText("Created Date:");

partyIdLabel.setText("Party Id:");

isUnreadLabel.setText("Is Unread:");

createdByUserLoginLabel.setText("Created By User Login:");

dataSourceIdLabel.setText("Data Source Id:");

partyTypeIdLabel.setText("Party Type Id:");

externalIdLabel.setText("External Id:");

lastModifiedDateLabel.setText("Last Modified Date:");

lastModifiedByUserLoginLabel.setText("Last Modified By User Login:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(statusIdLabel)

.add(partyIdLabel)

.add(preferredCurrencyUomIdLabel)

.add(partyTypeIdLabel)

.add(externalIdLabel)

.add(dataSourceIdLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(statusIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(preferredCurrencyUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(partyTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(externalIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(dataSourceIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(lastModifiedByUserLoginLabel)

.add(descriptionLabel)

.add(lastModifiedDateLabel)

.add(createdDateLabel)

.add(isUnreadLabel)

.add(createdByUserLoginLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(lastModifiedByUserLoginTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(descriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(lastModifiedDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(createdDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(isUnreadTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(createdByUserLoginTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

));

layoutPanel.setVerticalGroup(

            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

            .add(layoutPanel.createSequentialGroup()

                .addContainerGap()

                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(statusIdLabel)

.add(statusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(lastModifiedByUserLoginLabel)

.add(lastModifiedByUserLoginTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(partyIdLabel)

.add(partyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(descriptionLabel)

.add(descriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(preferredCurrencyUomIdLabel)

.add(preferredCurrencyUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(lastModifiedDateLabel)

.add(lastModifiedDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(partyTypeIdLabel)

.add(partyTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(createdDateLabel)

.add(createdDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(externalIdLabel)

.add(externalIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(isUnreadLabel)

.add(isUnreadTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(dataSourceIdLabel)

.add(dataSourceIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(createdByUserLoginLabel)

.add(createdByUserLoginTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addContainerGap()

));

}

 

public void setUIFields() throws  java.text.ParseException {

partyIdTextField.setText( OrderMaxUtility.getValidString(party.getpartyId()));

lastModifiedByUserLoginTextField.setText( OrderMaxUtility.getValidString(party.getlastModifiedByUserLogin()));

preferredCurrencyUomIdTextField.setText( OrderMaxUtility.getValidString(party.getpreferredCurrencyUomId()));

externalIdTextField.setText( OrderMaxUtility.getValidString(party.getexternalId()));

isUnreadTextField.setText( OrderMaxUtility.getValidString(party.getisUnread()));

createdByUserLoginTextField.setText( OrderMaxUtility.getValidString(party.getcreatedByUserLogin()));

lastModifiedDateTextField.setText( OrderMaxUtility.getValidTimestamp(party.getlastModifiedDate()));

statusIdTextField.setText( OrderMaxUtility.getValidString(party.getstatusId()));

descriptionTextField.setText( OrderMaxUtility.getValidString(party.getdescription()));

dataSourceIdTextField.setText( OrderMaxUtility.getValidString(party.getdataSourceId()));

partyTypeIdTextField.setText( OrderMaxUtility.getValidString(party.getpartyTypeId()));

createdDateTextField.setText( OrderMaxUtility.getValidTimestamp(party.getcreatedDate()));

}

 

public void getUIFields() throws  java.text.ParseException {

party.setpartyId( OrderMaxUtility.getValidString(partyIdTextField.getText()));

party.setlastModifiedByUserLogin( OrderMaxUtility.getValidString(lastModifiedByUserLoginTextField.getText()));

party.setpreferredCurrencyUomId( OrderMaxUtility.getValidString(preferredCurrencyUomIdTextField.getText()));

party.setexternalId( OrderMaxUtility.getValidString(externalIdTextField.getText()));

party.setisUnread( OrderMaxUtility.getValidString(isUnreadTextField.getText()));

party.setcreatedByUserLogin( OrderMaxUtility.getValidString(createdByUserLoginTextField.getText()));

party.setlastModifiedDate( OrderMaxUtility.getValidTimestamp(lastModifiedDateTextField.getText()));

party.setstatusId( OrderMaxUtility.getValidString(statusIdTextField.getText()));

party.setdescription( OrderMaxUtility.getValidString(descriptionTextField.getText()));

party.setdataSourceId( OrderMaxUtility.getValidString(dataSourceIdTextField.getText()));

party.setpartyTypeId( OrderMaxUtility.getValidString(partyTypeIdTextField.getText()));

party.setcreatedDate( OrderMaxUtility.getValidTimestamp(createdDateTextField.getText()));

}

public static void createAndShowUI(Party val){

try {

PartyPanel panel = new PartyPanel(val);

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

Party newObj =null;

if (baseVal != null) {

 newObj = new Party(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new Party();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return party;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof Party) {

Party newObj = (Party) uiObject;

party = newObj;

try { 

party.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("Party", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),Party.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PartyPanel( ); 

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

