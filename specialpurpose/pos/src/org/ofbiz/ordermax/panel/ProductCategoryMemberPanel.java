package org.ofbiz.ordermax.panel;

import java.beans.PropertyChangeEvent;
import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.ProductCategoryMember;

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

public class ProductCategoryMemberPanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =ProductCategoryMemberPanel.class.getName();

private ProductCategoryMember productcategorymember;

private javax.swing.JLabel commentsLabel;

private javax.swing.JTextField commentsTextField;

private javax.swing.JLabel productIdLabel;

private javax.swing.JTextField productIdTextField;

private javax.swing.JLabel fromDateLabel;

private javax.swing.JTextField fromDateTextField;

private javax.swing.JLabel thruDateLabel;

private javax.swing.JTextField thruDateTextField;

private javax.swing.JLabel sequenceNumLabel;

private javax.swing.JTextField sequenceNumTextField;

private javax.swing.JLabel quantityLabel;

private javax.swing.JTextField quantityTextField;

private javax.swing.JLabel productCategoryIdLabel;

private javax.swing.JTextField productCategoryIdTextField;

private JButton button;

private ComponentBorder cb;

public boolean isModified() {

return isModified;

}

public void setIsModified(boolean isModified) {

this.isModified = isModified;

}

private boolean isModified = false;

public ProductCategoryMemberPanel(ProductCategoryMember  val ){

this.productcategorymember= val;

initComponents();

}

public ProductCategoryMemberPanel(){

initComponents();

}

private void initComponents() {

commentsLabel = new javax.swing.JLabel();

commentsTextField = new javax.swing.JTextField();

commentsTextField.getDocument().addDocumentListener(new TextChangeListner());

productIdLabel = new javax.swing.JLabel();

productIdTextField = new javax.swing.JTextField();

productIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(productIdTextField);

button.addActionListener(new LookupActionListner(productIdTextField, "productIdTextField"  ));

fromDateLabel = new javax.swing.JLabel();

fromDateTextField = new javax.swing.JTextField();

fromDateTextField.getDocument().addDocumentListener(new TextChangeListner());

thruDateLabel = new javax.swing.JLabel();

thruDateTextField = new javax.swing.JTextField();

thruDateTextField.getDocument().addDocumentListener(new TextChangeListner());

sequenceNumLabel = new javax.swing.JLabel();

sequenceNumTextField = new javax.swing.JTextField();

sequenceNumTextField.getDocument().addDocumentListener(new TextChangeListner());

quantityLabel = new javax.swing.JLabel();

quantityTextField = new javax.swing.JTextField();

quantityTextField.getDocument().addDocumentListener(new TextChangeListner());

productCategoryIdLabel = new javax.swing.JLabel();

productCategoryIdTextField = new javax.swing.JTextField();

productCategoryIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(productCategoryIdTextField);

button.addActionListener(new LookupActionListner(productCategoryIdTextField, "productCategoryIdTextField"  ));

commentsLabel.setText("Comments:");

productIdLabel.setText("Product Id:");

fromDateLabel.setText("From Date:");

thruDateLabel.setText("Thru Date:");

sequenceNumLabel.setText("Sequence Num:");

quantityLabel.setText("Quantity:");

productCategoryIdLabel.setText("Product Category Id:");

org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

this.setLayout(layoutPanel);

layoutPanel.setHorizontalGroup(

layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(layoutPanel.createSequentialGroup()

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(productCategoryIdLabel)

.add(productIdLabel)

.add(thruDateLabel)

.add(fromDateLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(productCategoryIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(productIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(thruDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(fromDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(quantityLabel)

.add(commentsLabel)

.add(sequenceNumLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

.add(quantityTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(commentsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(sequenceNumTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

));

layoutPanel.setVerticalGroup(

            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

            .add(layoutPanel.createSequentialGroup()

                .addContainerGap()

                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(productCategoryIdLabel)

.add(productCategoryIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(quantityLabel)

.add(quantityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(productIdLabel)

.add(productIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(commentsLabel)

.add(commentsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(thruDateLabel)

.add(thruDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(sequenceNumLabel)

.add(sequenceNumTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(fromDateLabel)

.add(fromDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

)

.addContainerGap()

));

}

 

public void setUIFields() throws java.text.ParseException {

fromDateTextField.setText( OrderMaxUtility.getValidTimestamp(productcategorymember.getfromDate()));

quantityTextField.setText( OrderMaxUtility.getValidString(productcategorymember.getquantity()));

productIdTextField.setText( OrderMaxUtility.getValidString(productcategorymember.getproductId()));

productCategoryIdTextField.setText( OrderMaxUtility.getValidString(productcategorymember.getproductCategoryId()));

commentsTextField.setText( OrderMaxUtility.getValidString(productcategorymember.getcomments()));

thruDateTextField.setText( OrderMaxUtility.getValidTimestamp(productcategorymember.getthruDate()));

sequenceNumTextField.setText( OrderMaxUtility.getValidLong(productcategorymember.getsequenceNum()));

}

 

public void getUIFields() throws java.text.ParseException {

productcategorymember.setfromDate( OrderMaxUtility.getValidTimestamp(fromDateTextField.getText()));

productcategorymember.setquantity( OrderMaxUtility.getValidString(quantityTextField.getText()));

productcategorymember.setproductId( OrderMaxUtility.getValidString(productIdTextField.getText()));

productcategorymember.setproductCategoryId( OrderMaxUtility.getValidString(productCategoryIdTextField.getText()));

productcategorymember.setcomments( OrderMaxUtility.getValidString(commentsTextField.getText()));

productcategorymember.setthruDate( OrderMaxUtility.getValidTimestamp(thruDateTextField.getText()));

productcategorymember.setsequenceNum( OrderMaxUtility.getValidLong(sequenceNumTextField.getText()));

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

public static void createAndShowUI(ProductCategoryMember val){

try {

ProductCategoryMemberPanel panel = new ProductCategoryMemberPanel(val);

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

ProductCategoryMember newObj =null;

if (baseVal != null) {

 newObj = new ProductCategoryMember(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new ProductCategoryMember();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return productcategorymember;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof ProductCategoryMember) {

ProductCategoryMember newObj = (ProductCategoryMember) uiObject;

productcategorymember = newObj;

try { 

productcategorymember.setGenericValue();

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("ProductCategoryMember", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),ProductCategoryMember.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.ProductCategoryMemberPanel( ); 

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

