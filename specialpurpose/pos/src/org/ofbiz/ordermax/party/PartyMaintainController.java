/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mvc.controller.LoadAccountWorker;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.PartyIdVerifyValidator;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PartyMaintainController extends BaseMainScreen {

    public static final String module = PartyMaintainController.class.getName();
//    public BaseMainPanelInterface panel = null;
    protected PartyGroupPanel partyGroupPanel = null;

//    PersonDialogForm personPanel = null;
    protected JPanel productCardPanel = null;
    RoleTypePanelExt roleTypePanel = null;    
    RoleType roleType = null;
    String visibleCardName = null;
    String partyId = null;
    boolean isCustomer = true;
    ControllerOptions options = null;

    static public PartyMaintainController runController(ControllerOptions options) {

        PartyMaintainController controller = new PartyMaintainController(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(PartyMaintainController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(PartyMaintainController.module, options.getDesktopPane());
        }
        return controller;
    }
/*
    public PartyMaintainController(String partyId, boolean isCustomer, XuiSession sess, ControllerOptions options) {
        super(sess);
        this.partyId = partyId;
//        this.isCustomer = isCustomer;
        this.options = options;
    }

    public PartyMaintainController(String roleTypeId, XuiSession sess, ControllerOptions options) {
        super(sess);
//        this.roleTypeId = roleTypeId;
        this.options = options;
    }
*/
    public PartyMaintainController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.roleType = options.getRoleType();
        this.partyId = options.getPartyId();
        this.options = options;
        if (options.contains("isSupplier")) {
            isCustomer = false;
        }
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 75;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
            frame.setLocation(x, y);
        }
    }

    
@Override
    public void loadScreen(ContainerPanelInterface f) {

        setCaption("Party Detail");

 
        productCardPanel = new JPanel(new CardLayout());
        partyGroupPanel = new PartyGroupPanel(ControllerOptions.getSession(), options);
        //      personPanel = new PersonDialogForm(parentFrame, ControllerOptions.getSession());
        roleTypePanel = new RoleTypePanelExt();

        productCardPanel.add(partyGroupPanel, PartyGroupTreeNode.PartyGroupNodeName);
//        productCardPanel.add(personPanel, PersonTreeNode.PersonNodeName);
        productCardPanel.add(roleTypePanel, RoleTypeTreeNode.RoleTypeRootName);

        PartyButtonPanel buttonPanel = new PartyButtonPanel();

        OrderMaxUtility.addAPanelToPanel(productCardPanel, f.getPanelDetail());
//        f.getPanelSelecton().setLayout(new BorderLayout());
//        f.getPanelSelecton().add(treePanel.getContainerPanel(), BorderLayout.CENTER);
//        OrderMaxUtility.addAPanelToPanel(treePanel.getContainerPanel(), );
        OrderMaxUtility.addAPanelToPanel(buttonPanel, partyGroupPanel.panelButton);

//        comboPostalChanged(PartyGroupTreeNode.PartyGroupNodeName);

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
//                    account = partyGroupPanel.getAccount();
                    getDialogFields();
                    LoadAccountWorker.saveAccount(partyGroupPanel.getAccount(), true, ControllerOptions.getSession());
                } catch (Exception ex) {
                    Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
                }
//                loadTree();
            }
        });


        buttonPanel.getBtnNewPartyGroup().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                createNewParty("PARTY_GROUP");
            }
        });
/*
        buttonPanel.getBtnNewPerson().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (partyGroupPanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
//sur                            partyGroupPanel.saveItem();
                        } catch (Exception e2) {
                            Debug.logError(e2, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                createNewParty("PERSON");
            }
        });
*/
//        if (options.isReadOnly()) {
        //order selection button
        ActionListener partyIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof PartyIdVerifyValidator) {
                    try {

                        PartyIdVerifyValidator validator = (PartyIdVerifyValidator) e.getSource();
                        Debug.logInfo("partyId: " + validator.getField().getText(), module);
                        if (UtilValidate.isNotEmpty(validator.getField()) && UtilValidate.isNotEmpty(validator.getField().getText())) {
                            String partyId = validator.getField().getText();
                            loadParty(partyId);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
                        Debug.logInfo("partyId test: 1", module);
                        if (partyGroupPanel.getAccount() != null && UtilValidate.isNotEmpty(partyGroupPanel.getAccount().getParty().getpartyId())) {
                            if (OrderMaxOptionPane.showConfirmDialog(null, "Party not found: " + partyId + ", Do you want to create new party?", "Load Party", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                PartyType type = partyGroupPanel.partyTypeComboModel.getSelectedItem();
                                if (type != null) {
                                    createNewParty(type.getpartyTypeId());
                                } else {
                                    OrderMaxOptionPane.showMessageDialog(null, "Please select a party type ", "Load Party", JOptionPane.YES_NO_OPTION);
                                }
                            }
                        } else {
                            Debug.logInfo("partyId test: 2", module);
                            PartyType type = partyGroupPanel.partyTypeComboModel.getSelectedItem();
                            if (type != null && partyGroupPanel.getAccount() == null) {
                                Debug.logInfo("partyId test: 3", module);
                                createNewParty(type.getpartyTypeId());
                            } else {
                                OrderMaxOptionPane.showMessageDialog(null, "Please select a party type ", "Load Party", JOptionPane.YES_NO_OPTION);
                            }
                        }
                    }
                }
            }
        };

        PartyIdVerifyValidator partyIdVerifyValidator = new PartyIdVerifyValidator(partyGroupPanel.panelPartyPickerId.textIdField, false, ControllerOptions.getSession());
        partyIdVerifyValidator.addActionListener(partyIdTextChangeAction);
        partyGroupPanel.panelPartyPickerId.textIdField.setInputVerifier(partyIdVerifyValidator);
      if (partyId != null) {
            try {
                loadParty(partyId);
            } catch (Exception ex) {
                Logger.getLogger(PartyMaintainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createNewParty("PARTY_GROUP");
        }
    }
    
    Account account = null;

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

 public void createNewParty(String partyTypeId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        try {
            Account account = LoadAccountWorker.createPartyComposite(partyTypeId);
            //clear dialog
            partyGroupPanel.clearDialogFields();

            //set order
  
             List<BillingAccountComposite> billingAccountComposites = new ArrayList<BillingAccountComposite>();
            
             BillingAccountComposite billingAccountComposite = LoadBillingAccountWorker.newBillingAccountComposite();
//            billingAccount.getBillingAccount()
            billingAccountComposites.add(billingAccountComposite);
            BillingAccountRole role = LoadBillingAccountWorker.newBillingAccountRole(partyId, "BILL_TO_CUSTOMER");
            billingAccountComposite.getBillingAccountRoleList().add(role);
            billingAccountComposite.setPrimaryPartyId(partyId);
            account.setBillingAccountComposite(billingAccountComposites);
                      partyGroupPanel.setAccount(account);
            //update the dialog
            setDialogFields();
        } catch (Exception ex) {
            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
            OrderMaxOptionPane.showMessageDialog(null, "Party not found: " + partyId, "Load Party", JOptionPane.YES_NO_OPTION);
        }
    }

    public void loadParty(String partyId) throws Exception {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
//        try {
        Account account = LoadAccountWorker.getAccount(partyId, XuiContainer.getSession());
        PartyContactMechCompositeList machList = LoadAccountWorker.getPartyContactMechList(partyId, XuiContainer.getSession());
        account.setPartyContactList(machList);

//        LoadBillingAccountWorker.getBillingAccountComposite(roleTypeId, ControllerOptions.getSession());
        List<BillingAccountComposite> billingAccounts = LoadBillingAccountWorker.getPartyBillingAccountComposites(partyId, XuiContainer.getSession());
        if (billingAccounts.isEmpty()) {
            BillingAccountComposite billingAccountComposite = LoadBillingAccountWorker.newBillingAccountComposite();
//            billingAccount.getBillingAccount()
            billingAccounts.add(billingAccountComposite);
            BillingAccountRole role = LoadBillingAccountWorker.newBillingAccountRole(partyId, "BILL_TO_CUSTOMER");
            billingAccountComposite.getBillingAccountRoleList().add(role);
            billingAccountComposite.setPrimaryPartyId(partyId);
        }
        account.setBillingAccountComposite(billingAccounts);

        //clear dialog
        partyGroupPanel.clearDialogFields();

        //set order
        partyGroupPanel.setAccount(account);

        //update the dialog
        setDialogFields();
//        } catch (Exception ex) {
//            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
//            OrderMaxOptionPane.showMessageDialog(null, "Party not found: " + partyId, "Load Party", JOptionPane.YES_NO_OPTION);
//        }
    }

    void getDialogFields() {
        try {
            partyGroupPanel.getDialogFields();
//        panelShippingItem.getDialogField();
//        panelShippingGroup.getDialogField();
        } catch (ParseException ex) {
            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setDialogFields() {
        try {
            partyGroupPanel.setDialogFields();
//        panelShippingItem.setDialogField();
            //       panelShippingGroup.setDialogField();
        } catch (ParseException ex) {
            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearDialogFields() {
        partyGroupPanel.clearDialogFields();

    }

    //@Override
    public Account getParty() {
        //getDialogFields();
        return partyGroupPanel.getAccount();
    }


    protected ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

}
