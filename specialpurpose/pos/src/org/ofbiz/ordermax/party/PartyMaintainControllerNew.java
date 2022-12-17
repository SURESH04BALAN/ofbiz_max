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
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PartyMaintainControllerNew extends BaseMainScreen {

    public static final String module = PartyMaintainControllerNew.class.getName();
//    public BaseMainPanelInterface panel = null;
    protected PartyGroupPanel partyGroupPanel = null;
//    PartyGroupPanel partyGroupPanel = null;
//    PersonDialogForm personPanel = null;
    protected RoleTypePanelExt roleTypePanel = null;
    protected JPanel productCardPanel = null;
    protected String roleTypeId = null;
    protected String visibleCardName = null;
    protected String partyId = null;
    protected boolean isMaintain = true;
    protected ControllerOptions options = null;
    protected PartyType partyType = null;

    static public PartyMaintainControllerNew runController(ControllerOptions options) {

        PartyMaintainControllerNew controller = new PartyMaintainControllerNew(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(PartyMaintainController.module, options.getDesktopPane(), null);
        } else {
            controller.loadSinglePanelInternalFrame(PartyMaintainController.module, options.getDesktopPane());
        }
        return controller;
    }

    protected PartyMaintainControllerNew(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
        this.partyId = options.getPartyId();
        this.partyType = options.getPartyType();

        if (options.getRoleType() != null) {
            this.roleTypeId = options.getRoleType().getroleTypeId();
        }

        if (options.contains("IsMaintain")) {
            this.isMaintain = true;
        }
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    public void loadScreen(ContainerPanelInterface f) {

        setCaption("Party Detail");

        productCardPanel = new JPanel(new CardLayout());
        partyGroupPanel = new PartyGroupPanel(ControllerOptions.getSession(), options);
        partyGroupPanel.partyTypeComboModel.jComboBox.setEnabled(false);
        //      personPanel = new PersonDialogForm(parentFrame, ControllerOptions.getSession());
        roleTypePanel = new RoleTypePanelExt();

        productCardPanel.add(partyGroupPanel, PartyGroupTreeNode.PartyGroupNodeName);
//        productCardPanel.add(personPanel, PersonTreeNode.PersonNodeName);
        productCardPanel.add(roleTypePanel, RoleTypeTreeNode.RoleTypeRootName);

        PartyButtonPanel buttonPanel = new PartyButtonPanel();
//        buttonPanel.btnNew.setEnabled(false);
//        buttonPanel.btnNew.setVisible(false);

        OrderMaxUtility.addAPanelToPanel(productCardPanel, f.getPanelDetail());
//        OrderMaxUtility.addAPanelToPanel(treePanel.getContainerPanel(), );
        OrderMaxUtility.addAPanelToPanel(buttonPanel, partyGroupPanel.panelButton);

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveParty();
            }
        });

        buttonPanel.getBtnNewPartyGroup().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createNewParty(partyType.getpartyTypeId());
            }
        });

        /*   buttonPanel.getBtnNewPerson().addActionListener(new ActionListener() {
         @Override
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
         });*/
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
                            //is already loaded??
                            if (UtilValidate.isNotEmpty(partyGroupPanel.getAccount())
                                    && UtilValidate.isNotEmpty(partyGroupPanel.getAccount().getParty().getpartyId())) {
                                if (!partyId.equals(partyGroupPanel.getAccount().getParty().getpartyId())) {
                                    GenericValue partyGen = org.ofbiz.ordermax.composite.PartyHelper.getParty(partyId, ControllerOptions.getSession().getDelegator());
                                    if (partyGen != null) {
                                        loadParty(partyId);
                                    } else {
                                        PartyType type = partyGroupPanel.partyTypeComboModel.getSelectedItem();
                                        if (type != null) {
                                            createNewParty(type.getpartyTypeId(), partyId);
                                        } else {
                                            OrderMaxOptionPane.showMessageDialog(null, "Please select a party type ", "Load Party", JOptionPane.YES_NO_OPTION);
                                        }
                                    }
                                }
                            }

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PartyMaintainControllerNew.class.getName()).log(Level.SEVERE, null, ex);
                        Debug.logInfo("partyId test: 1", module);
                        if (partyGroupPanel.getAccount() != null && UtilValidate.isNotEmpty(partyGroupPanel.getAccount().getParty().getpartyId())) {
                            if (OrderMaxOptionPane.showConfirmDialog(null, "Party not found: " + partyId + ", Do you want to create new party?", "Load Party", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                PartyType type = partyGroupPanel.partyTypeComboModel.getSelectedItem();
                                if (type != null) {
                                    createNewParty(type.getpartyTypeId(), partyId);

                                } else {
                                    OrderMaxOptionPane.showMessageDialog(null, "Please select a party type ", "Load Party", JOptionPane.YES_NO_OPTION);
                                }
                            }
                        } else {
                            Debug.logInfo("partyId test: 2", module);
                            PartyType type = partyGroupPanel.partyTypeComboModel.getSelectedItem();
                            if (type != null && partyGroupPanel.getAccount() == null) {
                                Debug.logInfo("partyId test: 3", module);
                                createNewParty(type.getpartyTypeId(), partyId);

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
                buttonPanel.btnNew.setEnabled(false);
                buttonPanel.btnNew.setVisible(false);
            } catch (Exception ex) {
                Logger.getLogger(PartyMaintainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createNewParty(partyType.getpartyTypeId());
        }
    }

    public void saveParty() {
        try {
            //account = partyGroupPanel.getAccount();
            getDialogFields();
            if (partyGroupPanel.getAccount().getPartyRolesList().getList().isEmpty()) {
           OrderMaxOptionPane.showMessageDialog(
                    null, "Party Role is empty. Please add party roles." + partyId, "Save Party", JOptionPane.YES_NO_OPTION);
            } else {
                LoadAccountWorker.saveAccount(partyGroupPanel.getAccount(), true, ControllerOptions.getSession());
                if (UtilValidate.isEmpty(partyId)) {
                    partyId = partyGroupPanel.getAccount().getParty().getpartyId();
                }
                loadParty(partyId);
            }
        } catch (Exception ex) {
            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addItem(String id) throws Exception {
        partyGroupPanel.addItem(id);
    }

    public void refreshScreen() {
//sur        partyGroupPanel.refreshScreen();
    }

    public void createNewParty(String partyTypeId) {
        createNewParty(partyTypeId, null);
    }

    public void createNewParty(String partyTypeId, String partyId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        try {
            Account account = LoadAccountWorker.createPartyComposite(partyTypeId);
            if (partyId != null) {
                account.getParty().setpartyId(partyId);
                List<PartyRole> list = null;
                if (options.getRoleTypeParent() != null && "SUPPLIER".equals(options.getRoleTypeParent())) {
                    list = LoadAccountWorker.createSupplierPartyRoles(partyId);
                } else {
                    list = LoadAccountWorker.createCustomerPartyRoles(partyId);
                }

                for (PartyRole prole : list) {
                    PartyRoleComposite tmpPartyRoleComposite = new PartyRoleComposite();
                    tmpPartyRoleComposite.setParty(account);
                    tmpPartyRoleComposite.setPartyRole(prole);
                    account.getPartyRolesList().add(tmpPartyRoleComposite);
                }
            }
            //clear dialog
            partyGroupPanel.clearDialogFields();

            //set order
            List<BillingAccountComposite> billingAccountComposites = new ArrayList<BillingAccountComposite>();

            BillingAccountComposite billingAccountComposite = LoadBillingAccountWorker.newBillingAccountComposite();
            billingAccountComposite.getBillingAccount().setaccountCurrencyUomId(account.getParty().getpreferredCurrencyUomId());
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
            Logger.getLogger(PartyMaintainControllerNew.class
                    .getName()).log(Level.SEVERE, null, ex);
            OrderMaxOptionPane.showMessageDialog(
                    null, "Party not found: " + partyId, "Load Party", JOptionPane.YES_NO_OPTION);
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
        account.getPartyContactList();
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
            Logger.getLogger(PartyMaintainControllerNew.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setDialogFields() {
        try {
            partyGroupPanel.setDialogFields();
//        panelShippingItem.setDialogField();
            //       panelShippingGroup.setDialogField();

        } catch (ParseException ex) {
            Logger.getLogger(PartyMaintainControllerNew.class
                    .getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
