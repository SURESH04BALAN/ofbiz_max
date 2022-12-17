package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.PartyRelationship;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class PartyRelationshipPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = PartyRelationshipPanel.class.getName();
    private PartyRelationship partyrelationship;
    private javax.swing.JLabel roleTypeIdFromLabel;
    private javax.swing.JTextField roleTypeIdFromTextField;
    private javax.swing.JLabel securityGroupIdLabel;
    private javax.swing.JTextField securityGroupIdTextField;
    private javax.swing.JLabel roleTypeIdToLabel;
    private javax.swing.JTextField roleTypeIdToTextField;
    private javax.swing.JLabel fromDateLabel;
    private javax.swing.JTextField fromDateTextField;
    private javax.swing.JLabel thruDateLabel;
    private javax.swing.JTextField thruDateTextField;
    private javax.swing.JLabel partyRelationshipTypeIdLabel;
    private javax.swing.JTextField partyRelationshipTypeIdTextField;
    private javax.swing.JLabel relationshipNameLabel;
    private javax.swing.JTextField relationshipNameTextField;
    private javax.swing.JLabel permissionsEnumIdLabel;
    private javax.swing.JTextField permissionsEnumIdTextField;
    private javax.swing.JLabel positionTitleLabel;
    private javax.swing.JTextField positionTitleTextField;
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JTextField commentsTextField;
    private javax.swing.JLabel statusIdLabel;
    private javax.swing.JTextField statusIdTextField;
    private javax.swing.JLabel priorityTypeIdLabel;
    private javax.swing.JTextField priorityTypeIdTextField;
    private javax.swing.JLabel partyIdToLabel;
    private javax.swing.JTextField partyIdToTextField;
    private javax.swing.JLabel partyIdFromLabel;
    private javax.swing.JTextField partyIdFromTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    private boolean isModified = false;

    public PartyRelationshipPanel(PartyRelationship val) {

        this.partyrelationship = val;

        initComponents();

    }

    public PartyRelationshipPanel() {

        initComponents();

    }

    private void initComponents() {

        roleTypeIdFromLabel = new javax.swing.JLabel();

        roleTypeIdFromTextField = new javax.swing.JTextField();

        roleTypeIdFromTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(roleTypeIdFromTextField);

        button.addActionListener(new LookupActionListner(roleTypeIdFromTextField, "roleTypeIdFromTextField"));

        securityGroupIdLabel = new javax.swing.JLabel();

        securityGroupIdTextField = new javax.swing.JTextField();

        securityGroupIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(securityGroupIdTextField);

        button.addActionListener(new LookupActionListner(securityGroupIdTextField, "securityGroupIdTextField"));

        roleTypeIdToLabel = new javax.swing.JLabel();

        roleTypeIdToTextField = new javax.swing.JTextField();

        roleTypeIdToTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(roleTypeIdToTextField);

        button.addActionListener(new LookupActionListner(roleTypeIdToTextField, "roleTypeIdToTextField"));

        fromDateLabel = new javax.swing.JLabel();

        fromDateTextField = new javax.swing.JTextField();

        fromDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        thruDateLabel = new javax.swing.JLabel();

        thruDateTextField = new javax.swing.JTextField();

        thruDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        partyRelationshipTypeIdLabel = new javax.swing.JLabel();

        partyRelationshipTypeIdTextField = new javax.swing.JTextField();

        partyRelationshipTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyRelationshipTypeIdTextField);

        button.addActionListener(new LookupActionListner(partyRelationshipTypeIdTextField, "partyRelationshipTypeIdTextField"));

        relationshipNameLabel = new javax.swing.JLabel();

        relationshipNameTextField = new javax.swing.JTextField();

        relationshipNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        permissionsEnumIdLabel = new javax.swing.JLabel();

        permissionsEnumIdTextField = new javax.swing.JTextField();

        permissionsEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(permissionsEnumIdTextField);

        button.addActionListener(new LookupActionListner(permissionsEnumIdTextField, "permissionsEnumIdTextField"));

        positionTitleLabel = new javax.swing.JLabel();

        positionTitleTextField = new javax.swing.JTextField();

        positionTitleTextField.getDocument().addDocumentListener(new TextChangeListner());

        commentsLabel = new javax.swing.JLabel();

        commentsTextField = new javax.swing.JTextField();

        commentsTextField.getDocument().addDocumentListener(new TextChangeListner());

        statusIdLabel = new javax.swing.JLabel();

        statusIdTextField = new javax.swing.JTextField();

        statusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(statusIdTextField);

        button.addActionListener(new LookupActionListner(statusIdTextField, "statusIdTextField"));

        priorityTypeIdLabel = new javax.swing.JLabel();

        priorityTypeIdTextField = new javax.swing.JTextField();

        priorityTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(priorityTypeIdTextField);

        button.addActionListener(new LookupActionListner(priorityTypeIdTextField, "priorityTypeIdTextField"));

        partyIdToLabel = new javax.swing.JLabel();

        partyIdToTextField = new javax.swing.JTextField();

        partyIdToTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyIdToTextField);

        button.addActionListener(new LookupActionListner(partyIdToTextField, "partyIdToTextField"));

        partyIdFromLabel = new javax.swing.JLabel();

        partyIdFromTextField = new javax.swing.JTextField();

        partyIdFromTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyIdFromTextField);

        button.addActionListener(new LookupActionListner(partyIdFromTextField, "partyIdFromTextField"));

        roleTypeIdFromLabel.setText("Role Type Id From:");

        securityGroupIdLabel.setText("Security Group Id:");

        roleTypeIdToLabel.setText("Role Type Id To:");

        fromDateLabel.setText("From Date:");

        thruDateLabel.setText("Thru Date:");

        partyRelationshipTypeIdLabel.setText("Party Relationship Type Id:");

        relationshipNameLabel.setText("Relationship Name:");

        permissionsEnumIdLabel.setText("Permissions Enum Id:");

        positionTitleLabel.setText("Position Title:");

        commentsLabel.setText("Comments:");

        statusIdLabel.setText("Status Id:");

        priorityTypeIdLabel.setText("Priority Type Id:");

        partyIdToLabel.setText("Party Id To:");

        partyIdFromLabel.setText("Party Id From:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(statusIdLabel)
                .add(permissionsEnumIdLabel)
                .add(roleTypeIdToLabel)
                .add(partyIdToLabel)
                .add(priorityTypeIdLabel)
                .add(roleTypeIdFromLabel)
                .add(partyRelationshipTypeIdLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(statusIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(permissionsEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(roleTypeIdToTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(partyIdToTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(priorityTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(roleTypeIdFromTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(partyRelationshipTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(securityGroupIdLabel)
                .add(partyIdFromLabel)
                .add(thruDateLabel)
                .add(fromDateLabel)
                .add(positionTitleLabel)
                .add(relationshipNameLabel)
                .add(commentsLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(securityGroupIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(partyIdFromTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(thruDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(fromDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(positionTitleTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(relationshipNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(statusIdLabel)
                .add(statusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(securityGroupIdLabel)
                .add(securityGroupIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(permissionsEnumIdLabel)
                .add(permissionsEnumIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(partyIdFromLabel)
                .add(partyIdFromTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(roleTypeIdToLabel)
                .add(roleTypeIdToTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(thruDateLabel)
                .add(thruDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(partyIdToLabel)
                .add(partyIdToTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(fromDateLabel)
                .add(fromDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(priorityTypeIdLabel)
                .add(priorityTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(positionTitleLabel)
                .add(positionTitleTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(roleTypeIdFromLabel)
                .add(roleTypeIdFromTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(relationshipNameLabel)
                .add(relationshipNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(partyRelationshipTypeIdLabel)
                .add(partyRelationshipTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(commentsLabel)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        relationshipNameTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getrelationshipName()));

        fromDateTextField.setText(OrderMaxUtility.getValidTimestamp(partyrelationship.getfromDate()));

        roleTypeIdFromTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getroleTypeIdFrom()));

        partyIdFromTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpartyIdFrom()));

        roleTypeIdToTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getroleTypeIdTo()));

        permissionsEnumIdTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpermissionsEnumId()));

        thruDateTextField.setText(OrderMaxUtility.getValidTimestamp(partyrelationship.getthruDate()));

        statusIdTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getstatusId()));

        positionTitleTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpositionTitle()));

        partyRelationshipTypeIdTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpartyRelationshipTypeId()));

        securityGroupIdTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getsecurityGroupId()));

        commentsTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getcomments()));

        priorityTypeIdTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpriorityTypeId()));

        partyIdToTextField.setText(OrderMaxUtility.getValidString(partyrelationship.getpartyIdTo()));

    }

    public void getUIFields() throws java.text.ParseException {

        partyrelationship.setrelationshipName(OrderMaxUtility.getValidString(relationshipNameTextField.getText()));

        partyrelationship.setfromDate(OrderMaxUtility.getValidTimestamp(fromDateTextField.getText()));

        partyrelationship.setroleTypeIdFrom(OrderMaxUtility.getValidString(roleTypeIdFromTextField.getText()));

        partyrelationship.setpartyIdFrom(OrderMaxUtility.getValidString(partyIdFromTextField.getText()));

        partyrelationship.setroleTypeIdTo(OrderMaxUtility.getValidString(roleTypeIdToTextField.getText()));

        partyrelationship.setpermissionsEnumId(OrderMaxUtility.getValidString(permissionsEnumIdTextField.getText()));

        partyrelationship.setthruDate(OrderMaxUtility.getValidTimestamp(thruDateTextField.getText()));

        partyrelationship.setstatusId(OrderMaxUtility.getValidString(statusIdTextField.getText()));

        partyrelationship.setpositionTitle(OrderMaxUtility.getValidString(positionTitleTextField.getText()));

        partyrelationship.setpartyRelationshipTypeId(OrderMaxUtility.getValidString(partyRelationshipTypeIdTextField.getText()));

        partyrelationship.setsecurityGroupId(OrderMaxUtility.getValidString(securityGroupIdTextField.getText()));

        partyrelationship.setcomments(OrderMaxUtility.getValidString(commentsTextField.getText()));

        partyrelationship.setpriorityTypeId(OrderMaxUtility.getValidString(priorityTypeIdTextField.getText()));

        partyrelationship.setpartyIdTo(OrderMaxUtility.getValidString(partyIdToTextField.getText()));

    }

    public static void createAndShowUI(PartyRelationship val) {

        try {

            PartyRelationshipPanel panel = new PartyRelationshipPanel(val);

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

        PartyRelationship newObj = null;

        if (baseVal != null) {

            newObj = new PartyRelationship(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new PartyRelationship();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return partyrelationship;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof PartyRelationship) {

            PartyRelationship newObj = (PartyRelationship) uiObject;

            partyrelationship = newObj;

            try {

                partyrelationship.setGenericValue();

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

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PartyRelationship", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),PartyRelationship.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PartyRelationshipPanel( ); 

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
