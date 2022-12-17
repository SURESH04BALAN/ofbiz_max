/*
 * Copyright (c) 2010, Oracle.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mvc.controller.LoadOrderHeaderRolesOrderWorker;
import mvc.controller.LoadPaymentWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.billingaccount.BillingAccountPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ContactList;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.entity.PartyIdentification;
import org.ofbiz.ordermax.entity.PartyIdentificationType;
import org.ofbiz.ordermax.screens.ClientEditor;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.entity.UserLogin;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import org.ofbiz.ordermax.generic.entitypanelsimpl.EntityPanelFactory;
import org.ofbiz.ordermax.invoice.FindInvoiceListPanel;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.invoice.PartyIdentificationTypeSingleton;
import org.ofbiz.ordermax.orderbase.FindOrderByPartyListPanel;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.orderviews.OrderDetailViewPanel;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.returns.FindOrderReturnListPanel;
import org.ofbiz.ordermax.orderbase.returns.LoadReturnWorker;
import org.ofbiz.ordermax.orderbase.returns.OrderReturnHeaderPanel;
import org.ofbiz.ordermax.party.userlogin.UserLoginMaintainPanel;
import org.ofbiz.ordermax.payment.FindPaymentListPanel;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.party.contact.ContactMechWorker;

/**
 * Form that allows editing of information about one client.
 *
 * @author Jiri Vagner, Jan Stola
 */
public class PartyGroupPanel extends javax.swing.JPanel /*implements GenericValuePanelInterfaceOrderMax, org.ofbiz.ordermax.base.BaseMainPanelInterface*/ {

    public static final String module = ClientEditor.class.getName();
    protected XuiSession session = null;
    protected GenericValue genericValue;
    protected PanelPartyRole partyRolePanel = null;
    protected PartyGroupDetailPanel partyGroupPanel = null;
    protected PersonPanel personPanel = null;
    protected UserLoginMaintainPanel userLoginMaintainPanel = null;
    protected OrderDetailViewPanel orderDetailViewPanel = null;
    protected FindInvoiceListPanel findInvoiceListPanel = null;
    protected FindOrderByPartyListPanel findOrderByPartyListPanel = null;
    protected FindOrderReturnListPanel findOrderReturnListPanel = null;
    protected FindPaymentListPanel findPaymentListPanel = null;
    public BillingAccountPanel billingAccountPanel = null;

    GenericValue genricValue = null;
//    java.awt.Frame parentFrame = null;
    protected PartyContactMechPanel contactMechPanelMain = null;
    List<Map<String, Object>> machList = null;

    Account account = null;
    //public JComboBoxSelectionModel<PartyType> partyTypeComboModel = null;
    public JGenericComboBoxSelectionModel<PartyType> partyTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
    public JGenericComboBoxSelectionModel<Uom> currencyComboModel = null;
    public JGenericComboBoxSelectionModel<PartyIdentificationType> partyIdentificationTypeModel = null;
//    private GenericSaveInterface genericUserPermissionInterface;
    public PartyPickerEditPanel panelPartyPickerId;
    ControllerOptions options = null;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        contactMechPanelMain.setPartyAccount(account);
        partyRolePanel.setPartyGroupAccount(account);
        billingAccountPanel.setAccount(account);
        orderDetailViewPanel.setAccount(account);
        findInvoiceListPanel.setAccount(account);
        findOrderByPartyListPanel.setAccount(account);
        findPaymentListPanel.setAccount(account);
        findOrderReturnListPanel.setAccount(account);        
       // genericUserPermissionInterface.loadList(UtilMisc.toMap("Account", account));
    }
    final private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    final private ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();
    final private ListAdapterListModel<ReturnHeaderComposite> returnHeaderCompositeListModel = new ListAdapterListModel<ReturnHeaderComposite>();
    final private ListAdapterListModel<PaymentComposite> paymentCompositeListModel = new ListAdapterListModel<PaymentComposite>();

    public PartyGroupPanel(XuiSession session, ControllerOptions options) {

        this.session = session;
        this.options = options;
        initComponents();
        panelPartyPickerId = new PartyPickerEditPanel(options);
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(BorderLayout.CENTER, panelPartyPickerId);
        panelPartyPickerId.textIdField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            ;
            public void focusLost(FocusEvent e) {
                if (!e.isTemporary()) {
                    String content = panelPartyPickerId.textIdField.getText();
                    partyGroupPanel.partyIdTextField.setText(content);

                }
            }
        });

        //partyTypeComboModel = new JComboBoxSelectionModel<PartyType>(PartyTypeSingleton.getValueList(), new PartyTypeListCellRenderer());
        partyTypeComboModel = new JGenericComboBoxSelectionModel<PartyType>(PartyTypeSingleton.getValueList());
        try {
            PartyType defaultSel = PartyTypeSingleton.getPartyType("PARTY_GROUP");
            partyTypeComboModel.setSelectedItem(defaultSel);

        } catch (Exception ex) {
            Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        statusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(StatusSingleton.getValueList("PARTY_STATUS"));
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);

        partyIdentificationTypeModel = new JGenericComboBoxSelectionModel<PartyIdentificationType>(PartyIdentificationTypeSingleton.getValueList());
        panelPartyIdentificationType.setLayout(new BorderLayout());
        panelPartyIdentificationType.add(BorderLayout.CENTER, partyIdentificationTypeModel.jComboBox);

        currencyComboModel = new JGenericComboBoxSelectionModel<Uom>(UomCurrencySingleton.getValueList());
        panelCurrency.setLayout(new BorderLayout());
        panelCurrency.add(BorderLayout.CENTER, currencyComboModel.jComboBox);

//        panelStatusId.setLayout(new BorderLayout());
//        panelStatusId.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);        
        panelPartyType.setLayout(new BorderLayout());
        panelPartyType.add(BorderLayout.CENTER, partyTypeComboModel.jComboBox);
        partyTypeComboModel.jComboBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartyType type = (PartyType) partyTypeComboModel.comboBoxModel.getSelectedItem();
//                Debug.logInfo("type.getpartyTypeId(): " + type.getpartyTypeId(), module);

                if (type != null && "PERSON".equals(type.getpartyTypeId())) {
                    mainPanel.removeAll();
                    mainPanel.setLayout(new BorderLayout());
                    if (account != null) {
                        personPanel.changeUIObject(account.getPerson());
                    }
                    try {
                        personPanel.setUIFields();
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mainPanel.add(BorderLayout.CENTER, personPanel);
                } else {
                    mainPanel.removeAll();
                    mainPanel.setLayout(new BorderLayout());
                    mainPanel.add(BorderLayout.CENTER, partyGroupPanel);
                    partyGroupPanel.setPartyGroupAccount(account);
                    try {
                        partyGroupPanel.setDialogFields();
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mainPanel.updateUI();
            }
        });

        partyGroupPanel = new PartyGroupDetailPanel();
        personPanel = new PersonPanel();
        //add party group as starting
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, partyGroupPanel);

        partyRolePanel = new PanelPartyRole();
        panelPartyRoles.setLayout(new BorderLayout());
        panelPartyRoles.add(partyRolePanel, BorderLayout.CENTER);

        userLoginMaintainPanel = new UserLoginMaintainPanel();
        panelUserLogin.setLayout(new BorderLayout());
        panelUserLogin.add(userLoginMaintainPanel, BorderLayout.CENTER);

        contactMechPanelMain = new PartyContactMechPanel(session);
        OrderMaxUtility.addAPanelToPanel(contactMechPanelMain, contactMainContainer);

        clientInfoPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 3) {
                    }

                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

        ControllerOptions billingOption = new ControllerOptions(options);
        billingOption.setPartyIdReadOnly(true);
        billingAccountPanel = new BillingAccountPanel(billingOption);
        panelBillingAccount.setLayout(new BorderLayout());
        panelBillingAccount.add(billingAccountPanel, BorderLayout.CENTER);

        ControllerOptions valOption = new ControllerOptions(options);
        valOption.setPartyIdReadOnly(true);
        if (options.getRoleTypeParent() != null && "SUPPLIER".equals(options.getRoleTypeParent())) {
            valOption.addRoleType("BILL_TO_CUSTOMER");
            valOption.addOrderType("PURCHASE_ORDER");
            valOption.addOrderStatusType("ORDER_COMPLETED");
            valOption.addRoleTypeParent("SUPPLIER");
        } else {
            valOption.addRoleType("BILL_TO_CUSTOMER");
            valOption.addOrderType("SALES_ORDER");
            valOption.addOrderStatusType("ORDER_COMPLETED");
            valOption.addRoleTypeParent("CUSTOMER");
        }

        orderDetailViewPanel = new OrderDetailViewPanel(valOption);
        panelSummary.setLayout(new BorderLayout());
        panelSummary.add(orderDetailViewPanel, BorderLayout.CENTER);

        ComponentBorder.doubleRaisedLoweredBevelBorder(jPanel3, "Party Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(mainPanel, "Party Details");

        ControllerOptions invOption = new ControllerOptions(options);
        invOption.setPartyIdReadOnly(true);
        findInvoiceListPanel = new FindInvoiceListPanel(invOption);
        panelInvoice.setLayout(new BorderLayout());
        panelInvoice.add(findInvoiceListPanel, BorderLayout.CENTER);
        findInvoiceListPanel.setReceiveInventoryList(invoiceCompositeListModel);
        findInvoiceListPanel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<EntityCondition> findOptionList = findInvoiceListPanel.getWhereClauseCond();
                LoadInvoiceWorker worker = new LoadInvoiceWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                findInvoiceListPanel.tablePanel.actionPerformed(worker);
            }
        });

        ControllerOptions orderOption = new ControllerOptions(options);
        orderOption.setPartyIdReadOnly(true);
        findOrderByPartyListPanel = new FindOrderByPartyListPanel(orderOption);
        panelOrders.setLayout(new BorderLayout());
        panelOrders.add(findOrderByPartyListPanel, BorderLayout.CENTER);
        findOrderByPartyListPanel.setReceiveInventoryList(orderListModel);
        findOrderByPartyListPanel.btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                orderListModel.clear();
                List<EntityCondition> findOptionList = findOrderByPartyListPanel.getFindOptionCondList();
                LoadOrderHeaderRolesOrderWorker worker = new LoadOrderHeaderRolesOrderWorker(orderListModel, ControllerOptions.getSession(), findOptionList);
                findOrderByPartyListPanel.tablePanel.actionPerformed(worker);
            }
        });

        ControllerOptions returnOption = new ControllerOptions(options);
        returnOption.setPartyIdReadOnly(true);
        findOrderReturnListPanel = new FindOrderReturnListPanel(returnOption);
        panelReturns.setLayout(new BorderLayout());
        panelReturns.add(findOrderReturnListPanel, BorderLayout.CENTER);
        findOrderReturnListPanel.setReturnListModel(returnHeaderCompositeListModel);
        findOrderReturnListPanel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ReturnHeaderComposite> invList;
                try {
                    returnHeaderCompositeListModel.clear();
                    List<EntityCondition> findOptionList = findOrderReturnListPanel.getWhereClauseCond();
                    LoadReturnWorker worker = new LoadReturnWorker(returnHeaderCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    findOrderReturnListPanel.tablePanel.actionPerformed(worker);
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        ControllerOptions paymentOption = new ControllerOptions(options);
        paymentOption.setPartyIdReadOnly(true);
        findPaymentListPanel = new FindPaymentListPanel(paymentOption);
        panelPayment.setLayout(new BorderLayout());
        panelPayment.add(findPaymentListPanel, BorderLayout.CENTER);
        findPaymentListPanel.setReceiveInventoryList(paymentCompositeListModel);
        findPaymentListPanel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                paymentCompositeListModel.clear();

                Map<String, Object> findOptionList = findPaymentListPanel.getFindOptionList();

                //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, session, findOptionList);
                LoadPaymentWorker worker = new LoadPaymentWorker(paymentCompositeListModel, ControllerOptions.getSession(), findOptionList);
                findPaymentListPanel.tablePanel.actionPerformed(worker);
            }
        });

//        ControllerOptions tmpOptions = new ControllerOptions( options);
//        tmpOptions.put("EntityName", "UserLoginSecurityGroup");
//        genericUserPermissionInterface = EntityPanelFactory.createEntityPanel(tmpOptions); 
//        panelSecurityPermission.setLayout(new BorderLayout());
//        panelSecurityPermission.add(genericUserPermissionInterface.getPanel(), BorderLayout.CENTER);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexButtonGroup = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        clientInfoPane = new javax.swing.JTabbedPane();
        parentMainPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelPartyId = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelStatusId = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelPartyType = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelCurrency = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelPartyIdentificationType = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panelPartyIdentificationValue = new javax.swing.JPanel();
        txtIdentification = new javax.swing.JTextField();
        contactMainContainer = new javax.swing.JPanel();
        panelPartyRoles = new javax.swing.JPanel();
        panelUserLogin = new javax.swing.JPanel();
        panelBillingAccount = new javax.swing.JPanel();
        panelTransactions = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelSummary = new javax.swing.JPanel();
        panelInvoice = new javax.swing.JPanel();
        panelOrders = new javax.swing.JPanel();
        panelReturns = new javax.swing.JPanel();
        panelPayment = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(900, 600));
        setLayout(new java.awt.BorderLayout());

        parentMainPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 977, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 562, Short.MAX_VALUE)
        );

        parentMainPanel.add(mainPanel, java.awt.BorderLayout.CENTER);

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanel3.setPreferredSize(new java.awt.Dimension(515, 90));
        jPanel3.setLayout(null);

        jLabel1.setText("Party Id:");
        jLabel1.setPreferredSize(new java.awt.Dimension(0, 24));
        jPanel3.add(jLabel1);
        jLabel1.setBounds(26, 20, 50, 24);

        panelPartyId.setMinimumSize(new java.awt.Dimension(200, 21));
        panelPartyId.setPreferredSize(new java.awt.Dimension(0, 24));

        org.jdesktop.layout.GroupLayout panelPartyIdLayout = new org.jdesktop.layout.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 24, Short.MAX_VALUE)
        );

        jPanel3.add(panelPartyId);
        panelPartyId.setBounds(80, 20, 200, 24);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Status:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel3.setPreferredSize(new java.awt.Dimension(0, 24));
        jPanel3.add(jLabel3);
        jLabel3.setBounds(310, 20, 100, 24);

        panelStatusId.setMinimumSize(new java.awt.Dimension(200, 21));
        panelStatusId.setPreferredSize(new java.awt.Dimension(200, 24));

        org.jdesktop.layout.GroupLayout panelStatusIdLayout = new org.jdesktop.layout.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 24, Short.MAX_VALUE)
        );

        jPanel3.add(panelStatusId);
        panelStatusId.setBounds(420, 20, 200, 24);

        jLabel4.setText("Party Type:");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(9, 55, 66, 16);
        jLabel4.getAccessibleContext().setAccessibleDescription("48");

        panelPartyType.setPreferredSize(new java.awt.Dimension(200, 24));

        org.jdesktop.layout.GroupLayout panelPartyTypeLayout = new org.jdesktop.layout.GroupLayout(panelPartyType);
        panelPartyType.setLayout(panelPartyTypeLayout);
        panelPartyTypeLayout.setHorizontalGroup(
            panelPartyTypeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );
        panelPartyTypeLayout.setVerticalGroup(
            panelPartyTypeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 24, Short.MAX_VALUE)
        );

        jPanel3.add(panelPartyType);
        panelPartyType.setBounds(80, 55, 200, 24);
        panelPartyType.getAccessibleContext().setAccessibleDescription("48");

        jLabel5.setText("Prefered Currency:");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(300, 55, 109, 16);
        jLabel5.getAccessibleContext().setAccessibleDescription("48");

        panelCurrency.setMinimumSize(new java.awt.Dimension(200, 21));
        panelCurrency.setPreferredSize(new java.awt.Dimension(200, 24));

        org.jdesktop.layout.GroupLayout panelCurrencyLayout = new org.jdesktop.layout.GroupLayout(panelCurrency);
        panelCurrency.setLayout(panelCurrencyLayout);
        panelCurrencyLayout.setHorizontalGroup(
            panelCurrencyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );
        panelCurrencyLayout.setVerticalGroup(
            panelCurrencyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 24, Short.MAX_VALUE)
        );

        jPanel3.add(panelCurrency);
        panelCurrency.setBounds(420, 55, 200, 24);
        panelCurrency.getAccessibleContext().setAccessibleDescription("48");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Id Type:");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel6.setPreferredSize(new java.awt.Dimension(0, 24));
        jPanel3.add(jLabel6);
        jLabel6.setBounds(670, 20, 78, 24);

        panelPartyIdentificationType.setMinimumSize(new java.awt.Dimension(200, 21));
        panelPartyIdentificationType.setPreferredSize(new java.awt.Dimension(0, 24));

        org.jdesktop.layout.GroupLayout panelPartyIdentificationTypeLayout = new org.jdesktop.layout.GroupLayout(panelPartyIdentificationType);
        panelPartyIdentificationType.setLayout(panelPartyIdentificationTypeLayout);
        panelPartyIdentificationTypeLayout.setHorizontalGroup(
            panelPartyIdentificationTypeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 200, Short.MAX_VALUE)
        );
        panelPartyIdentificationTypeLayout.setVerticalGroup(
            panelPartyIdentificationTypeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 24, Short.MAX_VALUE)
        );

        jPanel3.add(panelPartyIdentificationType);
        panelPartyIdentificationType.setBounds(760, 20, 200, 24);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Identification:");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(670, 55, 78, 16);
        jLabel7.getAccessibleContext().setAccessibleDescription("48");

        panelPartyIdentificationValue.setMinimumSize(new java.awt.Dimension(200, 21));
        panelPartyIdentificationValue.setLayout(new java.awt.BorderLayout());
        panelPartyIdentificationValue.add(txtIdentification, java.awt.BorderLayout.CENTER);
        txtIdentification.getAccessibleContext().setAccessibleDescription("48");

        jPanel3.add(panelPartyIdentificationValue);
        panelPartyIdentificationValue.setBounds(760, 55, 200, 22);

        parentMainPanel.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        clientInfoPane.addTab("Party Group", parentMainPanel);

        org.jdesktop.layout.GroupLayout contactMainContainerLayout = new org.jdesktop.layout.GroupLayout(contactMainContainer);
        contactMainContainer.setLayout(contactMainContainerLayout);
        contactMainContainerLayout.setHorizontalGroup(
            contactMainContainerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 977, Short.MAX_VALUE)
        );
        contactMainContainerLayout.setVerticalGroup(
            contactMainContainerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 652, Short.MAX_VALUE)
        );

        clientInfoPane.addTab("Contact Detail", contactMainContainer);

        org.jdesktop.layout.GroupLayout panelPartyRolesLayout = new org.jdesktop.layout.GroupLayout(panelPartyRoles);
        panelPartyRoles.setLayout(panelPartyRolesLayout);
        panelPartyRolesLayout.setHorizontalGroup(
            panelPartyRolesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 977, Short.MAX_VALUE)
        );
        panelPartyRolesLayout.setVerticalGroup(
            panelPartyRolesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 652, Short.MAX_VALUE)
        );

        clientInfoPane.addTab("Roles", panelPartyRoles);

        org.jdesktop.layout.GroupLayout panelUserLoginLayout = new org.jdesktop.layout.GroupLayout(panelUserLogin);
        panelUserLogin.setLayout(panelUserLoginLayout);
        panelUserLoginLayout.setHorizontalGroup(
            panelUserLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 977, Short.MAX_VALUE)
        );
        panelUserLoginLayout.setVerticalGroup(
            panelUserLoginLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 652, Short.MAX_VALUE)
        );

        clientInfoPane.addTab("User Login", panelUserLogin);

        org.jdesktop.layout.GroupLayout panelBillingAccountLayout = new org.jdesktop.layout.GroupLayout(panelBillingAccount);
        panelBillingAccount.setLayout(panelBillingAccountLayout);
        panelBillingAccountLayout.setHorizontalGroup(
            panelBillingAccountLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 977, Short.MAX_VALUE)
        );
        panelBillingAccountLayout.setVerticalGroup(
            panelBillingAccountLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 652, Short.MAX_VALUE)
        );

        clientInfoPane.addTab("Billing Account", panelBillingAccount);

        panelTransactions.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout panelSummaryLayout = new org.jdesktop.layout.GroupLayout(panelSummary);
        panelSummary.setLayout(panelSummaryLayout);
        panelSummaryLayout.setHorizontalGroup(
            panelSummaryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 972, Short.MAX_VALUE)
        );
        panelSummaryLayout.setVerticalGroup(
            panelSummaryLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 622, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Summary", panelSummary);

        org.jdesktop.layout.GroupLayout panelInvoiceLayout = new org.jdesktop.layout.GroupLayout(panelInvoice);
        panelInvoice.setLayout(panelInvoiceLayout);
        panelInvoiceLayout.setHorizontalGroup(
            panelInvoiceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 972, Short.MAX_VALUE)
        );
        panelInvoiceLayout.setVerticalGroup(
            panelInvoiceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 622, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Invoices", panelInvoice);

        org.jdesktop.layout.GroupLayout panelOrdersLayout = new org.jdesktop.layout.GroupLayout(panelOrders);
        panelOrders.setLayout(panelOrdersLayout);
        panelOrdersLayout.setHorizontalGroup(
            panelOrdersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 972, Short.MAX_VALUE)
        );
        panelOrdersLayout.setVerticalGroup(
            panelOrdersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 622, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Orders", panelOrders);

        org.jdesktop.layout.GroupLayout panelReturnsLayout = new org.jdesktop.layout.GroupLayout(panelReturns);
        panelReturns.setLayout(panelReturnsLayout);
        panelReturnsLayout.setHorizontalGroup(
            panelReturnsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 972, Short.MAX_VALUE)
        );
        panelReturnsLayout.setVerticalGroup(
            panelReturnsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 622, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Returns/Credit", panelReturns);

        org.jdesktop.layout.GroupLayout panelPaymentLayout = new org.jdesktop.layout.GroupLayout(panelPayment);
        panelPayment.setLayout(panelPaymentLayout);
        panelPaymentLayout.setHorizontalGroup(
            panelPaymentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 972, Short.MAX_VALUE)
        );
        panelPaymentLayout.setVerticalGroup(
            panelPaymentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 622, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Payments", panelPayment);

        panelTransactions.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        clientInfoPane.addTab("Transactions", panelTransactions);

        add(clientInfoPane, java.awt.BorderLayout.CENTER);

        panelButton.setMaximumSize(new java.awt.Dimension(32767, 60));
        panelButton.setMinimumSize(new java.awt.Dimension(0, 40));
        panelButton.setPreferredSize(new java.awt.Dimension(816, 40));
        panelButton.setVerifyInputWhenFocusTarget(false);

        org.jdesktop.layout.GroupLayout panelButtonLayout = new org.jdesktop.layout.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 982, Short.MAX_VALUE)
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 40, Short.MAX_VALUE)
        );

        add(panelButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
/*
     public void changeUIObject(GenericValueObjectInterface uiObject) {
     partyGroupPanel.changeUIObject(uiObject);
     }

     public GenericValueObjectInterface createUIObject(GenericValue baseVal) {
     genricValue = baseVal;
     return partyGroupPanel.createUIObject(baseVal);
     }

     public void getUIFields() throws java.text.ParseException {
     partyGroupPanel.getUIFields();
     }
     */
    String partyId = null;

    public void clearDialogFields() {
        /*try {
         throw new Exception("clearDialogFields ");
         } catch (Exception e) {
         Debug.logError(e, module);
         }*/
        txtIdentification.setText("");
        personPanel.clearUIFields();
        partyGroupPanel.clearDialogFields();

    }

    public void setDialogFields() throws java.text.ParseException {
        /*    try {
         throw new Exception("clearDialogFields ");
         } catch (Exception e) {
         Debug.logError(e, module);
         }*/
        panelPartyPickerId.textIdField.setText(account.getParty().getpartyId());
        if (account.getPartyIdentification() != null && UtilValidate.isNotEmpty(account.getPartyIdentification().getPartyIdentificationTypeId())) {
            try {
                PartyIdentificationType partyIdentificationType = PartyIdentificationTypeSingleton.getPartyIdentificationType(account.getPartyIdentification().getPartyIdentificationTypeId());
                partyIdentificationTypeModel.setSelectedItem(partyIdentificationType);
                txtIdentification.setText(account.getPartyIdentification().getIdValue());
//            String str = partyType.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
//            partyTypeComboModel.autoCompletion.setText(str);
            } catch (Exception ex) {
                Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            PartyType partyType = PartyTypeSingleton.getPartyType(account.getParty().getpartyTypeId());
            partyTypeComboModel.setSelectedItem(partyType);
//            String str = partyType.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
//            partyTypeComboModel.autoCompletion.setText(str);
        } catch (Exception ex) {
            Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (account.getParty().getpreferredCurrencyUomId() != null) {
                currencyComboModel.setSelectedItem(UomCurrencySingleton.getUom(account.getParty().getpreferredCurrencyUomId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(account.getParty().getstatusId())) {
                statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(account.getParty().getstatusId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ("PERSON".equals(account.getParty().getpartyTypeId())) {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            personPanel.changeUIObject(account.getPerson());
            personPanel.setUIFields();
            mainPanel.add(BorderLayout.CENTER, personPanel);
        } else {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(BorderLayout.CENTER, partyGroupPanel);
            partyGroupPanel.setPartyGroupAccount(account);
            partyGroupPanel.setDialogFields();
        }
        mainPanel.repaint();
        mainPanel.updateUI();

        contactMechPanelMain.setPartyContactMechCompositeListData(account.getPartyContactList());
        if (account.getParty().getpartyId() != null && account.getParty().getpartyId().isEmpty() == false) {
            partyId = account.getParty().getpartyId();
            machList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
//            contactMechPanelMain.setContactMechs(machList);
        }

        ListAdapterListModel<UserLogin> listModel = account.getUserLoginList();
        if (listModel.getSize() > 0) {
            userLoginMaintainPanel.setUserLoginList(listModel);
            UserLogin userLogin = listModel.getElementAt(0);
            userLoginMaintainPanel.setUserLogin(userLogin);
            userLoginMaintainPanel.setDialogFields();
        } else {
            UserLogin userLogin = new UserLogin();
            userLogin.setpartyId(account.getParty().getpartyId());
            userLoginMaintainPanel.setUserLogin(userLogin);
            userLoginMaintainPanel.setDialogFields();
        }
        billingAccountPanel.setDialogFields();
        /*
        
         try {
         account = LoadAccountWorker.getAccount(partyId, session);

         GenericValue genricValue = PosProductHelper.getParty(person.getpartyId(), delegator);

         List<GenericValue> genValList = genricValue.getRelated("PartyRole");
         if (genValList != null && genValList.size() > 0) {
         //            List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PartyRole", delegator);
         GenericValue partyRole = genValList.get(0);
         partyRolesTablePanel.setupOrderTableList(genValList);
         //        genericValue = PartyWorker.findPartyLatestTelecomNumber(person.getpartyId(), session.getDelegator());

         if (partyRole != null) {
         GenericValueObjectInterface uiObj = partyRolePanel.createUIObject(partyRole);
         partyRolePanel.changeUIObject(uiObj);
         partyRolePanel.setUIFields();
         }
         }
         //        delegator.getRelated(module, jPanel5, null, genricValue)
         partyGroupPanel.setUIFields();
         } catch (GenericEntityException ex) {
         Logger.getLogger(PartyGroupDialogForm.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
         Logger.getLogger(PartyGroupDialogForm.class.getName()).log(Level.SEVERE, null, ex);
         }

         }

    
         else {
         Debug.logInfo("Party Group, partyId: is null", module);
         //            machList = new ArrayList<Map<String, Object>>();
         //            Map<String, Object> maps = new HashMap<String, Object>();
         //            machList.add(maps);
         //            contactMechPanelMain.setContactMechs(machList);

         List<GenericValue> genValList = new ArrayList<GenericValue>();
         //                        List<GenericValue> genValList = PosProductHelper.getGenericValueLists("PartyRole", delegator);

         partyRolesTablePanel.setupOrderTableList(genValList);
         //        genericValue = PartyWorker.findPartyLatestTelecomNumber(person.getpartyId(), session.getDelegator());

         GenericValue partyRole = null;
         GenericValueObjectInterface uiObj = partyRolePanel.createUIObject(partyRole);
         partyRolePanel.changeUIObject(uiObj);
         partyRolePanel.setUIFields();

         partyGroupPanel.setUIFields();
         }
         */
    }

    public void getDialogFields() throws java.text.ParseException {

        if ("PERSON".equals(account.getParty().getpartyTypeId())) {
            personPanel.getUIFields();
        } else {
            partyGroupPanel.getUIFields();
        }
        account.setContactMechListToCreate(new ContactList());
        account.getParty().setpartyId(panelPartyPickerId.textIdField.getText());
        if (UtilValidate.isNotEmpty(txtIdentification.getText()) && partyIdentificationTypeModel.getSelectedItem() != null) {
            PartyIdentification partyIdentification = account.getPartyIdentification();
            if (partyIdentification == null) {
                partyIdentification = new PartyIdentification();
            }
            partyIdentification.setIdValue(txtIdentification.getText());

            account.getPartyIdentification().setPartyId(account.getParty().getpartyId());
            account.getPartyIdentification().setPartyIdentificationTypeId(partyIdentificationTypeModel.getSelectedItem().getpartyIdentificationTypeId());

        }
        try {
            if (statusItemComboModel.getSelectedItem() != null) {
                account.getParty().setstatusId(statusItemComboModel.getSelectedItem().getstatusId());
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (currencyComboModel.getSelectedItem() != null) {
                account.getParty().setpreferredCurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderReturnHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        contactMechPanelMain.get(account);
    }

    public JPanel getContainerPanel() {
        return this;
    }
    /*
     public void newItem() {

     try {
     //TelecomNumber telNumber = new TelecomNumber();
     GenericValueObjectInterface uiObject = this.createUIObject(null);
     changeUIObject(uiObject);
     setUIFields();
     contactMechPanelMain.createNewContactMech();
        

     }  catch (java.text.ParseException ex) {
     Logger.getLogger(PartyContactMechPanel.class  

     .getName()).log(Level.SEVERE, null, ex);
     }
     }

     public void saveItem() throws Exception {

     GenericValueObjectInterface partyGroup = getUIObject();
     GenericValue detailValue = partyGroup.getGenericValueObj();
     if (partyGroup.isGenericValueSet() == false) {
     detailValue = partyGroup.createNewGenericValueObj(session.getDelegator());
     }
     getUIFields();
     partyGroup.getGenericValue();
     Debug.logInfo("Save 1", module);
     if (OrderMaxUtility.createOrStorePartyGroup(detailValue, session.getDelegator())) {
     //                detailValue.create();
     Debug.logInfo("Save 2", module);
     PartyGroup person = (PartyGroup) partyGroupPanel.getUIObject();
     contactMechPanelMain.saveContactMech(person.getpartyId());
     }
     Debug.logInfo("Save 3", module);
        
     PartyHelper.createPartyRoles(roleTypeId, detailValue.getString("partyId"), session);
        
     }
     */

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public boolean isModified() {
        return isModified;

    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }
    private boolean isModified = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane clientInfoPane;
    private javax.swing.JPanel contactMainContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    public javax.swing.JPanel panelBillingAccount;
    public javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelCurrency;
    private javax.swing.JPanel panelInvoice;
    private javax.swing.JPanel panelOrders;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelPartyIdentificationType;
    private javax.swing.JPanel panelPartyIdentificationValue;
    private javax.swing.JPanel panelPartyRoles;
    private javax.swing.JPanel panelPartyType;
    private javax.swing.JPanel panelPayment;
    private javax.swing.JPanel panelReturns;
    private javax.swing.JPanel panelStatusId;
    private javax.swing.JPanel panelSummary;
    private javax.swing.JPanel panelTransactions;
    private javax.swing.JPanel panelUserLogin;
    private javax.swing.JPanel parentMainPanel;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JTextField txtIdentification;
    // End of variables declaration//GEN-END:variables
/*
     public void refreshScreen() {
     try {
     setUIFields();
        

     } catch (ParseException ex) {
     Logger.getLogger(PartyGroupPanel.class  

     .getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
    protected String roleTypeId = null;

    public void addItem(String id) throws Exception {
        roleTypeId = id;
    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     public void setItem(Object val) {

     GenericValue partyGroup = (GenericValue) val;
     GenericValueObjectInterface uiObj = this.createUIObject(partyGroup);
     this.changeUIObject(uiObj);
     try {
     this.setUIFields();
     }  catch (java.text.ParseException ex) {
     Debug.logError(ex, module);
     }
     }
     */

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
