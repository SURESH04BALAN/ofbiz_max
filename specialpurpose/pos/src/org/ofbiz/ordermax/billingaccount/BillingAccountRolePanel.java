/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import org.ofbiz.ordermax.party.*;
import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadAccountWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.PartyRoleTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class BillingAccountRolePanel extends javax.swing.JPanel {

    private JGenericComboBoxSelectionModel<RoleType> selectionComboModel = null;
    public GenericTableModelPanel<BillingAccountRole, BillingAccountRoleTableModel> tablePanel = null;
    public PartyPickerEditPanel partyIdPicker = null;
    public DatePickerEditPanel fromDate = null;
    public DatePickerEditPanel thruDate = null;

    /**
     * Creates new form TelephonePanel
     */
    public BillingAccountRolePanel(ControllerOptions options) {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);
        ControllerOptions partyOptions = new ControllerOptions(options);
        partyIdPicker = new PartyPickerEditPanel(partyOptions);
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(BorderLayout.CENTER, partyIdPicker);

        selectionComboModel = new JGenericComboBoxSelectionModel<RoleType>(RoleTypeSingleton.getValueList());
        panelRoleType.setLayout(new BorderLayout());
        panelRoleType.add(BorderLayout.CENTER, selectionComboModel.jComboBox);

        fromDate = new DatePickerEditPanel();
        fromDate.setSession(ControllerOptions.getSession());
        panelFromDate.setLayout(new BorderLayout());
        panelFromDate.add(BorderLayout.CENTER, fromDate);

        thruDate = new DatePickerEditPanel();
        thruDate.setSession(ControllerOptions.getSession());
        panelThruDate.setLayout(new BorderLayout());
        panelThruDate.add(BorderLayout.CENTER, thruDate);

        tablePanel = new GenericTableModelPanel<BillingAccountRole, BillingAccountRoleTableModel>(new BillingAccountRoleTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        ComponentBorder.loweredBevelBorder(panelIResultList, "List");
        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");

        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) tablePanel.selectionModel;//listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setGoodIdentification(i);
                            break;
                        }
                    }
                }
            }
        });
    }
    BillingAccountComposite billingAccountComposite = null;
    BillingAccountRole billingAccountRole = null;
    //PartyRoleComposite partyRoleComposite = null;

    public BillingAccountRole getBillingAccountRole() {
        return billingAccountRole;
    }

    public void setBillingAccountRole(BillingAccountRole billingAccountRole) {
        this.billingAccountRole = billingAccountRole;
    }

    public void setGoodIdentification(int index) {

        if (index < tablePanel.listModel.getSize()) {
            billingAccountRole = tablePanel.listModel.getElementAt(index);
            setDialogFields();
        }
    }

    ListAdapterListModel<BillingAccountRole> billingAccountRoleListModel = new ListAdapterListModel<BillingAccountRole>();

    public void setBillingAccountComposite(BillingAccountComposite billingAccountComposite) {
        this.billingAccountComposite = billingAccountComposite;
        billingAccountRoleListModel.clear();
        billingAccountRoleListModel.addAll(billingAccountComposite.getBillingAccountRoleList());
        tablePanel.setListModel(billingAccountRoleListModel);
    }

    //private Account partyGroupAccount = null;
    final public void setupEditOrderTable() {

        for (int i = 0; i < BillingAccountRoleTableModel.Columns.values().length; i++) {
            BillingAccountRoleTableModel.Columns[] columns = BillingAccountRoleTableModel.Columns.values();
            BillingAccountRoleTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
    }


    /*
     public void setProductComposite(ProductComposite productComposite) {
     this.productComposite = productComposite;
     listSelectionModel.dataListModel.clear();
     clearDialogFields();
     if (productComposite != null) {
     listSelectionModel.dataListModel.addAll(productComposite.getGoodIdentificationList().getList());
     if (listSelectionModel.dataListModel.getSize() > 0) {
     listSelectionModel.listModelSelection.setSelection(listSelectionModel.dataListModel.getElementAt(0));
     } else {
     clearDialogFields();
     newGoodIdentification();
     setDialogField();
     }
     }

     }
     */
    public void clearDialogFields() {

        partyIdPicker.textIdField.setText("");
        fromDate.txtDate.setText("");
        thruDate.txtDate.setText("");
    }

    public void setDialogFields() {
        if (billingAccountRole != null) {

            try {
                if (UtilValidate.isNotEmpty(billingAccountRole.getroleTypeId())) {
                    RoleType roleType = RoleTypeSingleton.getRoleType(billingAccountRole.getroleTypeId());
                    selectionComboModel.setSelectedItem(roleType);
                }
                partyIdPicker.textIdField.setText(billingAccountRole.getpartyId());
                fromDate.setDate(billingAccountRole.getfromDate());
                thruDate.setDate(billingAccountRole.getthruDate());

            } catch (Exception ex) {
                Logger.getLogger(PartyRoleTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getDialogFields() {
        if (billingAccountRole != null) {
            try {
                if (selectionComboModel.getSelectedItem() != null) {
                    billingAccountRole.setroleTypeId(selectionComboModel.getSelectedItem().getroleTypeId());
                }
                // selectionComboModel.setSelectedItem(roleType);
                billingAccountRole.setpartyId(partyIdPicker.textIdField.getText());
                billingAccountRole.setfromDate(fromDate.getTimeStamp());
                billingAccountRole.setthruDate(thruDate.getTimeStamp());
            } catch (Exception ex) {
                Logger.getLogger(PartyRoleTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*      if (goodIdentification != null) {
         try {
         org.ofbiz.ordermax.entity.GoodIdentificationType goodid = (org.ofbiz.ordermax.entity.GoodIdentificationType) selectionComboModel.comboBoxModel.getSelectedItem();
         if (goodid != null) {

         goodIdentification.setgoodIdentificationTypeId(goodid.getgoodIdentificationTypeId());
         }
         } catch (Exception ex) {
         Logger.getLogger(PanelPartyRole.class.getName()).log(Level.SEVERE, null, ex);
         }

         goodIdentification.setidValue(txtRole.getText());
         }
         */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGoodIdentificationDetail = new javax.swing.JPanel();
        btnNewRole = new javax.swing.JButton();
        btnSaveRole = new javax.swing.JButton();
        btnDeleteRole = new javax.swing.JButton();
        jLabel = new javax.swing.JLabel();
        panelPartyId = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelRoleType = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelThruDate = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 200));

        btnNewRole.setText("New");
        btnNewRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewRoleActionPerformed(evt);
            }
        });

        btnSaveRole.setText("Save");
        btnSaveRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveRoleActionPerformed(evt);
            }
        });

        btnDeleteRole.setText("Delete");
        btnDeleteRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRoleActionPerformed(evt);
            }
        });

        jLabel.setText("Party Id:");

        javax.swing.GroupLayout panelPartyIdLayout = new javax.swing.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel1.setText("Role Type Id:");

        javax.swing.GroupLayout panelRoleTypeLayout = new javax.swing.GroupLayout(panelRoleType);
        panelRoleType.setLayout(panelRoleTypeLayout);
        panelRoleTypeLayout.setHorizontalGroup(
            panelRoleTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRoleTypeLayout.setVerticalGroup(
            panelRoleTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel2.setText("From Date:");

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel3.setText("Thru Date:");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(btnNewRole, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveRole, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteRole, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewRole)
                    .addComponent(btnDeleteRole)
                    .addComponent(btnSaveRole))
                .addContainerGap())
        );

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );

        jPanel1.add(panelIResultList, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewRoleActionPerformed

        /*  billingAccountRole = new BillingAccountRole();
                
         RoleType roletype = selectionComboModel.getSelectedItem();
         for (int i = 0; i < tablePanel.listModel.getSize(); ++i) {
         PartyRoleComposite partyRoleComposite = (PartyRoleComposite) tablePanel.listModel.getElementAt(i);
         if (partyRoleComposite.getPartyRole().getroleTypeId().equals(roletype.getroleTypeId())) {
         return;
         }
         }

         PartyRoleComposite partyRoleComposite = new PartyRoleComposite();
         partyRoleComposite.setParty(partyGroupAccount);
         PartyRole prole = new PartyRole();
         prole.setpartyId(partyGroupAccount.getParty().getpartyId());
         prole.setroleTypeId(roletype.getroleTypeId());
         partyRoleComposite.setPartyRole(prole);
         tablePanel.listModel.add(partyRoleComposite);

         LoadAccountWorker.createPartyRoles(partyGroupAccount, XuiContainer.getSession());
         */

    }//GEN-LAST:event_btnNewRoleActionPerformed


    private void btnSaveRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveRoleActionPerformed
        /*getDialogField();
         Debug.logInfo("Save: " + goodIdentification.getproductId(), "module");
         if (goodIdentification != null
         && (goodIdentification.getproductId() != null && !goodIdentification.getproductId().isEmpty())) {
         listSelectionModel.dataListModel.add(goodIdentification);
         Debug.logInfo("Save valid : " + goodIdentification.getproductId(), "module");
         LoadProductWorker.saveGoodIdentification(listSelectionModel.dataListModel.getList(), GoodIdentificationTypeSingleton.getSingletonSession());
         }*/

        //LoadAccountWorker.updatePartyRoles(partyGroupAccount, XuiContainer.getSession());
    }//GEN-LAST:event_btnSaveRoleActionPerformed

    private void btnDeleteRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRoleActionPerformed
        /*
         RoleType roletype = selectionComboModel.getSelectedItem();
         for (int i = 0; i < tablePanel.listModel.getSize(); ++i) {
         PartyRoleComposite partyRoleComposite = (PartyRoleComposite) tablePanel.listModel.getElementAt(i);
         if (partyRoleComposite.getPartyRole().getroleTypeId().equals(roletype.getroleTypeId())) {
         tablePanel.listModel.remove(partyRoleComposite);
         return;
         }
         }
         */
    }//GEN-LAST:event_btnDeleteRoleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteRole;
    public javax.swing.JButton btnNewRole;
    public javax.swing.JButton btnSaveRole;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelRoleType;
    private javax.swing.JPanel panelThruDate;
    // End of variables declaration//GEN-END:variables
}
