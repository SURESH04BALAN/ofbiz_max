package org.ofbiz.ordermax.panel;

import java.beans.PropertyChangeEvent;
import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class OrderHeaderAndRoleSummaryPanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =OrderHeaderAndRoleSummaryPanel.class.getName();

private OrderHeaderAndRoleSummary orderheaderandrolesummary;

private javax.swing.JLabel statusIdLabel;

private javax.swing.JTextField statusIdTextField;

private javax.swing.JLabel roleTypeIdLabel;

private javax.swing.JTextField roleTypeIdTextField;

private javax.swing.JLabel totalSubRemainingAmountLabel;

private javax.swing.JTextField totalSubRemainingAmountTextField;

private javax.swing.JLabel orderIdLabel;

private javax.swing.JTextField orderIdTextField;

private javax.swing.JLabel orderDateLabel;

private javax.swing.JTextField orderDateTextField;

private javax.swing.JLabel partyIdLabel;

private javax.swing.JTextField partyIdTextField;

private javax.swing.JLabel totalGrandAmountLabel;

private javax.swing.JTextField totalGrandAmountTextField;

private javax.swing.JLabel orderTypeIdLabel;

private javax.swing.JTextField orderTypeIdTextField;

private javax.swing.JLabel totalOrdersLabel;

private javax.swing.JTextField totalOrdersTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public OrderHeaderAndRoleSummaryPanel(OrderHeaderAndRoleSummary  val ){

this.orderheaderandrolesummary= val;

initComponents();

}

public OrderHeaderAndRoleSummaryPanel(){

initComponents();

}

private void initComponents() {

statusIdLabel = new javax.swing.JLabel();

statusIdTextField = new javax.swing.JTextField();

statusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(statusIdTextField);

button.addActionListener(new LookupActionListner(statusIdTextField, "statusIdTextField"  ));

roleTypeIdLabel = new javax.swing.JLabel();

roleTypeIdTextField = new javax.swing.JTextField();

roleTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(roleTypeIdTextField);

button.addActionListener(new LookupActionListner(roleTypeIdTextField, "roleTypeIdTextField"  ));

totalSubRemainingAmountLabel = new javax.swing.JLabel();

totalSubRemainingAmountTextField = new javax.swing.JTextField();

totalSubRemainingAmountTextField.getDocument().addDocumentListener(new TextChangeListner());

orderIdLabel = new javax.swing.JLabel();

orderIdTextField = new javax.swing.JTextField();

orderIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(orderIdTextField);

button.addActionListener(new LookupActionListner(orderIdTextField, "orderIdTextField"  ));

orderDateLabel = new javax.swing.JLabel();

orderDateTextField = new javax.swing.JTextField();

orderDateTextField.getDocument().addDocumentListener(new TextChangeListner());

partyIdLabel = new javax.swing.JLabel();

partyIdTextField = new javax.swing.JTextField();

partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(partyIdTextField);

button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"  ));

totalGrandAmountLabel = new javax.swing.JLabel();

totalGrandAmountTextField = new javax.swing.JTextField();

totalGrandAmountTextField.getDocument().addDocumentListener(new TextChangeListner());

orderTypeIdLabel = new javax.swing.JLabel();

orderTypeIdTextField = new javax.swing.JTextField();

orderTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(orderTypeIdTextField);

button.addActionListener(new LookupActionListner(orderTypeIdTextField, "orderTypeIdTextField"  ));

totalOrdersLabel = new javax.swing.JLabel();

totalOrdersTextField = new javax.swing.JTextField();

totalOrdersTextField.getDocument().addDocumentListener(new TextChangeListner());

statusIdLabel.setText("Status Id:");

roleTypeIdLabel.setText("Role Type Id:");

totalSubRemainingAmountLabel.setText("Total Sub Remaining Amount:");

orderIdLabel.setText("Order Id:");

orderDateLabel.setText("Order Date:");

partyIdLabel.setText("Party Id:");

totalGrandAmountLabel.setText("Total Grand Amount:");

orderTypeIdLabel.setText("Order Type Id:");

totalOrdersLabel.setText("Total Orders:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(statusIdLabel)

.add(roleTypeIdLabel)

.add(partyIdLabel)

.add(orderTypeIdLabel)

.add(orderIdLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(statusIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(orderTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(orderIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(orderDateLabel)

.add(totalOrdersLabel)

.add(totalGrandAmountLabel)

.add(totalSubRemainingAmountLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(orderDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(totalOrdersTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(totalGrandAmountTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(totalSubRemainingAmountTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

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

.add(orderDateLabel)

.add(orderDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(roleTypeIdLabel)

.add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(totalOrdersLabel)

.add(totalOrdersTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(partyIdLabel)

.add(partyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(totalGrandAmountLabel)

.add(totalGrandAmountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(orderTypeIdLabel)

.add(orderTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(totalSubRemainingAmountLabel)

.add(totalSubRemainingAmountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(orderIdLabel)

.add(orderIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

)

.addContainerGap()

));

}

 

public void setUIFields() throws java.text.ParseException {

partyIdTextField.setText( OrderMaxUtility.getValidString(orderheaderandrolesummary.getpartyId()));

roleTypeIdTextField.setText( OrderMaxUtility.getValidString(orderheaderandrolesummary.getroleTypeId()));

orderDateTextField.setText( OrderMaxUtility.getValidTimestamp(orderheaderandrolesummary.getorderDate()));

orderTypeIdTextField.setText( OrderMaxUtility.getValidString(orderheaderandrolesummary.getorderTypeId()));

orderIdTextField.setText( OrderMaxUtility.getValidString(orderheaderandrolesummary.getorderId()));

totalOrdersTextField.setText( OrderMaxUtility.getValidLong(orderheaderandrolesummary.gettotalOrders()));

statusIdTextField.setText( OrderMaxUtility.getValidString(orderheaderandrolesummary.getstatusId()));

totalSubRemainingAmountTextField.setText( OrderMaxUtility.getValidBigDecimal(orderheaderandrolesummary.gettotalSubRemainingAmount()));

totalGrandAmountTextField.setText( OrderMaxUtility.getValidBigDecimal(orderheaderandrolesummary.gettotalGrandAmount()));

}

 

public void getUIFields() throws java.text.ParseException {

orderheaderandrolesummary.setpartyId( OrderMaxUtility.getValidString(partyIdTextField.getText()));

orderheaderandrolesummary.setroleTypeId( OrderMaxUtility.getValidString(roleTypeIdTextField.getText()));

orderheaderandrolesummary.setorderDate( OrderMaxUtility.getValidTimestamp(orderDateTextField.getText()));

orderheaderandrolesummary.setorderTypeId( OrderMaxUtility.getValidString(orderTypeIdTextField.getText()));

orderheaderandrolesummary.setorderId( OrderMaxUtility.getValidString(orderIdTextField.getText()));

orderheaderandrolesummary.settotalOrders( OrderMaxUtility.getValidLong(totalOrdersTextField.getText()));

orderheaderandrolesummary.setstatusId( OrderMaxUtility.getValidString(statusIdTextField.getText()));

orderheaderandrolesummary.settotalSubRemainingAmount( OrderMaxUtility.getValidBigDecimal(totalSubRemainingAmountTextField.getText()));

orderheaderandrolesummary.settotalGrandAmount( OrderMaxUtility.getValidBigDecimal(totalGrandAmountTextField.getText()));

}

 

private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

 

private void notifyListeners(String property, String oldValue, String newValue) {

        for (PropertyChangeListener name : listener) {

            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));

        }

    }

 

    public void addChangeListener(PropertyChangeListener newListener) {

        listener.add(newListener);

    }

public static void createAndShowUI(OrderHeaderAndRoleSummary val){

try {

OrderHeaderAndRoleSummaryPanel panel = new OrderHeaderAndRoleSummaryPanel(val);

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

OrderHeaderAndRoleSummary newObj =null;

if (baseVal != null) {

 newObj = new OrderHeaderAndRoleSummary(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new OrderHeaderAndRoleSummary();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return orderheaderandrolesummary;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof OrderHeaderAndRoleSummary) {

OrderHeaderAndRoleSummary newObj = (OrderHeaderAndRoleSummary) uiObject;

orderheaderandrolesummary = newObj;

try { 

orderheaderandrolesummary.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("OrderHeaderAndRoleSummary", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),OrderHeaderAndRoleSummary.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.OrderHeaderAndRoleSummaryPanel( ); 

Object uiObj = panel.createUIObject(val);

panel.changeUIObject(uiObj);

panel.setUIFields();

dlg.setChildPanelInterface(panel);

OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

dlg.setLocationRelativeTo(null);

dlg.pack();

dlg.setVisible(true);

} catch (java.text.ParseException ex) {

Debug.logError(ex, module);

//Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

}

*/

