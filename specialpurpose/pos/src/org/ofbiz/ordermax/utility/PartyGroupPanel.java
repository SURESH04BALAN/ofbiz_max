package org.ofbiz.ordermax.utility;


import java.text.ParseException;
import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.PartyGroup;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class PartyGroupPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = PartyGroupPanel.class.getName();
    private PartyGroup partygroup;
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JTextField commentsTextField;
    private javax.swing.JLabel officeSiteNameLabel;
    private javax.swing.JTextField officeSiteNameTextField;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JTextField groupNameTextField;
    private javax.swing.JLabel annualRevenueLabel;
    private javax.swing.JTextField annualRevenueTextField;
    private javax.swing.JLabel logoImageUrlLabel;
    private javax.swing.JTextField logoImageUrlTextField;
    private javax.swing.JLabel groupNameLocalLabel;
    private javax.swing.JTextField groupNameLocalTextField;
    private javax.swing.JLabel partyIdLabel;
    protected javax.swing.JTextField partyIdTextField;
    private javax.swing.JLabel tickerSymbolLabel;
    private javax.swing.JTextField tickerSymbolTextField;
    private javax.swing.JLabel numEmployeesLabel;
    private javax.swing.JTextField numEmployeesTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    private boolean isModified = false;

    public PartyGroupPanel(PartyGroup val) {

        this.partygroup = val;

        initComponents();

    }

    public PartyGroupPanel() {

        initComponents();

    }

    private void initComponents() {

        commentsLabel = new javax.swing.JLabel();

        commentsTextField = new javax.swing.JTextField();

        commentsTextField.getDocument().addDocumentListener(new TextChangeListner());

        officeSiteNameLabel = new javax.swing.JLabel();

        officeSiteNameTextField = new javax.swing.JTextField();

        officeSiteNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        groupNameLabel = new javax.swing.JLabel();

        groupNameTextField = new javax.swing.JTextField();

        groupNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        annualRevenueLabel = new javax.swing.JLabel();

        annualRevenueTextField = new javax.swing.JTextField();

        annualRevenueTextField.getDocument().addDocumentListener(new TextChangeListner());

        logoImageUrlLabel = new javax.swing.JLabel();

        logoImageUrlTextField = new javax.swing.JTextField();

        logoImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        groupNameLocalLabel = new javax.swing.JLabel();

        groupNameLocalTextField = new javax.swing.JTextField();

        groupNameLocalTextField.getDocument().addDocumentListener(new TextChangeListner());

        partyIdLabel = new javax.swing.JLabel();

        partyIdTextField = new javax.swing.JTextField();

        partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyIdTextField);

        button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"));

        tickerSymbolLabel = new javax.swing.JLabel();

        tickerSymbolTextField = new javax.swing.JTextField();

        tickerSymbolTextField.getDocument().addDocumentListener(new TextChangeListner());

        numEmployeesLabel = new javax.swing.JLabel();

        numEmployeesTextField = new javax.swing.JTextField();

        numEmployeesTextField.getDocument().addDocumentListener(new TextChangeListner());

        commentsLabel.setText("Comments:");

        officeSiteNameLabel.setText("Office Site Name:");

        groupNameLabel.setText("Group Name:");

        annualRevenueLabel.setText("Annual Revenue:");

        logoImageUrlLabel.setText("Logo Image Url:");

        groupNameLocalLabel.setText("Group Name Local:");

        partyIdLabel.setText("Party Id:");

        tickerSymbolLabel.setText("Ticker Symbol:");

        numEmployeesLabel.setText("Num Employees:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(partyIdLabel)
                .add(groupNameLocalLabel)
                .add(groupNameLabel)
                .add(tickerSymbolLabel)
                .add(officeSiteNameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(groupNameLocalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(groupNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(tickerSymbolTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(officeSiteNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(annualRevenueLabel)
                .add(numEmployeesLabel)
                .add(commentsLabel)
                .add(logoImageUrlLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(annualRevenueTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(numEmployeesTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(logoImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(partyIdLabel)
                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(annualRevenueLabel)
                .add(annualRevenueTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(groupNameLocalLabel)
                .add(groupNameLocalTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(numEmployeesLabel)
                .add(numEmployeesTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(groupNameLabel)
                .add(groupNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(commentsLabel)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(tickerSymbolLabel)
                .add(tickerSymbolTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(logoImageUrlLabel)
                .add(logoImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(officeSiteNameLabel)
                .add(officeSiteNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws ParseException, java.text.ParseException {

        partyIdTextField.setText(OrderMaxUtility.getValidString(partygroup.getpartyId()));

        officeSiteNameTextField.setText(OrderMaxUtility.getValidString(partygroup.getofficeSiteName()));

        annualRevenueTextField.setText(OrderMaxUtility.getValidString(partygroup.getannualRevenue()));

        groupNameTextField.setText(OrderMaxUtility.getValidString(partygroup.getgroupName()));

        commentsTextField.setText(OrderMaxUtility.getValidString(partygroup.getcomments()));

        groupNameLocalTextField.setText(OrderMaxUtility.getValidString(partygroup.getgroupNameLocal()));

        logoImageUrlTextField.setText(OrderMaxUtility.getValidString(partygroup.getlogoImageUrl()));

        tickerSymbolTextField.setText(OrderMaxUtility.getValidString(partygroup.gettickerSymbol()));

        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));

    }

    public void getUIFields() throws ParseException, java.text.ParseException {

        partygroup.setpartyId(OrderMaxUtility.getValidString(partyIdTextField.getText()));

        partygroup.setofficeSiteName(OrderMaxUtility.getValidString(officeSiteNameTextField.getText()));

        partygroup.setannualRevenue(OrderMaxUtility.getValidString(annualRevenueTextField.getText()));

        partygroup.setgroupName(OrderMaxUtility.getValidString(groupNameTextField.getText()));

        partygroup.setcomments(OrderMaxUtility.getValidString(commentsTextField.getText()));

        partygroup.setgroupNameLocal(OrderMaxUtility.getValidString(groupNameLocalTextField.getText()));

        partygroup.setlogoImageUrl(OrderMaxUtility.getValidString(logoImageUrlTextField.getText()));

        partygroup.settickerSymbol(OrderMaxUtility.getValidString(tickerSymbolTextField.getText()));

        partygroup.setnumEmployees(OrderMaxUtility.getValidString(numEmployeesTextField.getText()));

    }

    public static void createAndShowUI(PartyGroup val) {

        try {

            PartyGroupPanel panel = new PartyGroupPanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (ParseException ex) {

            Debug.logError(ex, module);

        } 

    }


    public GenericValueObjectInterface getUIObject() {

        return partygroup;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof PartyGroup) {

            PartyGroup newObj = (PartyGroup) uiObject;

            partygroup = newObj;

            try {

                partygroup.setGenericValue();

            } catch (Exception ex) {
//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    
    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        PartyGroup newObj = null;

        if (baseVal != null) {

            newObj = new PartyGroup(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new PartyGroup();

        }

        return newObj;

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

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PartyGroup", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),PartyGroup.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PartyGroupPanel( ); 

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
