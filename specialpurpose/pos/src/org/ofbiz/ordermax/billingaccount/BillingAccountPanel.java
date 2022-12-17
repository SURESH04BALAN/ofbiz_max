/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadBillingAccountWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.BillingAccountRole;

/**
 *
 * @author BBS Auctions
 */
public class BillingAccountPanel extends javax.swing.JPanel {

    /**
     * Creates new form BillingAccountPanel
     */
    public BillingAccountPanel(ControllerOptions options) {
        initComponents();
        this.options = new ControllerOptions(options);
        billingAccountDetailPanel = new BillingAccountDetailPanel(options);
        billingAccountRolePanel = new BillingAccountRolePanel(options);
        invoiceListPanel = new InvoiceListPanel(options);
        orderSummaryListPanel = new OrderSummaryListPanel(options);
        paymentListPanel = new PaymentListPanel(options);
        billingAccountListPanel = new BillingAccountListPanel(options);
        tabbedPane.add("Billing Accounts", billingAccountListPanel);
        tabbedPane.add("Detail", billingAccountDetailPanel);
        tabbedPane.add("Roles", billingAccountRolePanel);
        tabbedPane.add("Invoices", invoiceListPanel);
        tabbedPane.add("Payements", paymentListPanel);
        tabbedPane.add("Orders", orderSummaryListPanel);

        billingAccountListPanel.tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) billingAccountListPanel.tablePanel.selectionModel;//listSelectionModel.selectionModel;
                Debug.logInfo("getRowCount(): " + " value changed", "module");
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    Debug.logInfo("getRowCount(): " + " value changed 1", "module");
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            Debug.logInfo("getRowCount(): " + " value changed 2", "module");
                            System.out.println(" " + i);
                            if (i < billingAccountListPanel.tablePanel.listModel.getSize()) {
                                Debug.logInfo("getRowCount(): " + " value changed 3", "module");
                                BillingAccount billingAccount = billingAccountListPanel.tablePanel.listModel.getElementAt(i);
                                if (billingAccount != null) {
                                    Debug.logInfo("getRowCount(): " + " value changed 4", "module");
                                    BillingAccountComposite composite = null;
                                    if (UtilValidate.isNotEmpty(billingAccount.getbillingAccountId())) {
                                        composite = LoadBillingAccountWorker.getBillingAccountComposite(billingAccount.getbillingAccountId(), ControllerOptions.getSession());
                                    } else {
                                        if (UtilValidate.isNotEmpty(account) && !account.getBillingAccountComposite().isEmpty()) {
                                            composite = account.getNewBillingAccountComposite();
                                        }
                                    }

                                    if (composite != null) {
                                        Debug.logInfo("getRowCount(): " + " value changed 5", "module");
                                        billingAccountComposite = composite;
                                        setBillingAccountComposite(billingAccountComposite);
                                        setDialogFields();
                                    }
                                }

                            }
                            //setGoodIdentification(i);
                            break;
                        }
                    }
                }
            }
        });

        billingAccountRolePanel.btnNewRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (billingAccountComposite != null) {

                    BillingAccountRole role = LoadBillingAccountWorker.newBillingAccountRole(billingAccountComposite.getPrimaryPartyId(), "");
                    billingAccountRolePanel.setBillingAccountRole(role);
                    billingAccountRolePanel.setDialogFields();
                }
            }
        });

        billingAccountRolePanel.btnSaveRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (billingAccountComposite != null && UtilValidate.isNotEmpty(billingAccountComposite.getBillingAccount().getbillingAccountId())) {
                    billingAccountRolePanel.getDialogFields();
                    billingAccountRolePanel.getBillingAccountRole().setbillingAccountId(billingAccountComposite.getBillingAccount().getbillingAccountId());
                    LoadBillingAccountWorker.saveBillingAccountRole(billingAccountRolePanel.getBillingAccountRole(), ControllerOptions.getSession());
                    billingAccountRolePanel.setDialogFields();
                }
            }
        });

        billingAccountDetailPanel.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (billingAccountComposite != null) {
                    billingAccountDetailPanel.getDialogFields();
                    LoadBillingAccountWorker.saveBillingAccount(billingAccountComposite, ControllerOptions.getSession());
                    billingAccountDetailPanel.setDialogFields();
                }
            }
        });
    }

    ControllerOptions options = null;
    InvoiceListPanel invoiceListPanel = null;
    public BillingAccountDetailPanel billingAccountDetailPanel = null;
    public BillingAccountRolePanel billingAccountRolePanel = null;
    public OrderSummaryListPanel orderSummaryListPanel = null;
    public PaymentListPanel paymentListPanel = null;
    public BillingAccountListPanel billingAccountListPanel = null;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());
        add(tabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    BillingAccountComposite billingAccountComposite = null;

    public void setBillingAccountComposite(BillingAccountComposite billingAccountComposite) {
        this.billingAccountComposite = billingAccountComposite;
        billingAccountDetailPanel.setBillingAccountComposite(billingAccountComposite);
        billingAccountRolePanel.setBillingAccountComposite(billingAccountComposite);
        invoiceListPanel.setBillingAccountComposite(billingAccountComposite);
        paymentListPanel.setBillingAccountComposite(billingAccountComposite);
    }

    Account account = null;
    ListAdapterListModel<BillingAccount> model = new ListAdapterListModel<BillingAccount>();

    public void setAccount(Account account) {
        this.account = account;
        model.clear();
        model.addAll(account.getBillingAccounts());
        billingAccountListPanel.setBillingAccountList(model);

    }

    public BillingAccountComposite getBillingAccountComposite() {
        return billingAccountComposite;
    }

    public void clearDialogFields() {
        billingAccountDetailPanel.clearDialogFields();
        billingAccountRolePanel.clearDialogFields();
        invoiceListPanel.clearDialogFields();
        orderSummaryListPanel.clearDialogFields();
        paymentListPanel.clearDialogFields();
    }

    public void setDialogFields() {
        /*      if (account != null) {
   
         model.addAll(account.getBillingAccounts());
         billingAccountListPanel.setBillingAccountList(model);
         }
         */
        billingAccountDetailPanel.setDialogFields();
        billingAccountRolePanel.setDialogFields();
        invoiceListPanel.setDialogFields();
        orderSummaryListPanel.setDialogFields();
        paymentListPanel.setDialogFields();

    }

    public void getDialogFields() {
        billingAccountDetailPanel.getDialogFields();
        billingAccountRolePanel.getDialogFields();
        invoiceListPanel.getDialogFields();
        orderSummaryListPanel.getDialogFields();
        paymentListPanel.getDialogFields();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
