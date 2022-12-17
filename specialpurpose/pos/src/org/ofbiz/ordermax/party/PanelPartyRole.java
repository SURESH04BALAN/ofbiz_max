/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

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
import mvc.model.table.PartyRoleTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelPartyRole extends javax.swing.JPanel {

    private JGenericComboBoxSelectionModel<RoleType> selectionComboModel = null;
    public GenericTableModelPanel<PartyRoleComposite, PartyRoleTableModel> tablePanel = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelPartyRole() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        selectionComboModel = new JGenericComboBoxSelectionModel<RoleType>(RoleTypeSingleton.getValueList());
        panelRoleType.setLayout(new BorderLayout());
        panelRoleType.add(BorderLayout.CENTER, selectionComboModel.jComboBox);

        tablePanel = new GenericTableModelPanel<PartyRoleComposite, PartyRoleTableModel>(new PartyRoleTableModel());
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

    PartyRoleComposite partyRoleComposite = null;

    public void setGoodIdentification(int index) {

        if (index < tablePanel.listModel.getSize()) {
            partyRoleComposite = (PartyRoleComposite) tablePanel.listModel.getElementAt(index);
            setDialogFields();
        }
    }

    public void setPartyGroupAccount(Account partyGroupAccount) {
        this.partyGroupAccount = partyGroupAccount;
        tablePanel.setListModel(partyGroupAccount.getPartyRolesList());
    }
    private Account partyGroupAccount = null;

    final public void setupEditOrderTable() {

        for (int i = 0; i < PartyRoleTableModel.Columns.values().length; i++) {
            PartyRoleTableModel.Columns[] columns = PartyRoleTableModel.Columns.values();
            PartyRoleTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
    }

    public Account getPartyGroupAccount() {
        return partyGroupAccount;
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
        txtParentTypeId.setText("");
    }

    public void setDialogFields() {

        if (partyRoleComposite != null) {
            RoleType roleType = null;
            try {
                roleType = RoleTypeSingleton.getRoleType(partyRoleComposite.getPartyRole().getroleTypeId());
                selectionComboModel.setSelectedItem(roleType);
                txtParentTypeId.setText(roleType.getparentTypeId());

            } catch (Exception ex) {
                Logger.getLogger(PartyRoleTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            txtParentTypeId.setText("");

        }

    }

    public void getDialogFields() {
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
        btnNewTelephone = new javax.swing.JButton();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        jLabel = new javax.swing.JLabel();
        panelRoleType = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtParentTypeId = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnSupplierRoles = new javax.swing.JButton();
        btnCustomerRoles = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 200));

        btnNewTelephone.setText("Add");
        btnNewTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTelephoneActionPerformed(evt);
            }
        });

        btnSaveTelephone.setText("Save");
        btnSaveTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTelephoneActionPerformed(evt);
            }
        });

        btnDeleteTelephone.setText("Delete");
        btnDeleteTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTelephoneActionPerformed(evt);
            }
        });

        jLabel.setText("Role Type:");

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

        jLabel4.setText("Parent Type Id:");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtParentTypeId)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtParentTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addContainerGap())
        );

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );

        jPanel1.add(panelIResultList);

        btnSupplierRoles.setText("Add Supplier Roles");
        btnSupplierRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierRolesActionPerformed(evt);
            }
        });

        btnCustomerRoles.setText("Add Customer Roles");
        btnCustomerRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerRolesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSupplierRoles)
                    .addComponent(btnCustomerRoles))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCustomerRoles, btnSupplierRoles});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSupplierRoles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCustomerRoles)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCustomerRoles, btnSupplierRoles});

        jPanel1.add(jPanel2);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
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

    }//GEN-LAST:event_btnNewTelephoneActionPerformed
    private void newGoodIdentification() {
        /*        goodIdentification = new GoodIdentification();
         goodIdentification.setproductId(productComposite.getProduct().getproductId());
         goodIdentification.setgoodIdentificationTypeId("EAN");
         */
    }

    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed
        /*getDialogField();
         Debug.logInfo("Save: " + goodIdentification.getproductId(), "module");
         if (goodIdentification != null
         && (goodIdentification.getproductId() != null && !goodIdentification.getproductId().isEmpty())) {
         listSelectionModel.dataListModel.add(goodIdentification);
         Debug.logInfo("Save valid : " + goodIdentification.getproductId(), "module");
         LoadProductWorker.saveGoodIdentification(listSelectionModel.dataListModel.getList(), GoodIdentificationTypeSingleton.getSingletonSession());
         }*/

        LoadAccountWorker.updatePartyRoles(partyGroupAccount, XuiContainer.getSession());
    }//GEN-LAST:event_btnSaveTelephoneActionPerformed

    private void btnDeleteTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTelephoneActionPerformed

        RoleType roletype = selectionComboModel.getSelectedItem();
        for (int i = 0; i < tablePanel.listModel.getSize(); ++i) {
            PartyRoleComposite partyRoleComposite = (PartyRoleComposite) tablePanel.listModel.getElementAt(i);
            if (partyRoleComposite.getPartyRole().getroleTypeId().equals(roletype.getroleTypeId())) {
                tablePanel.listModel.remove(partyRoleComposite);
                return;
            }
        }
    }//GEN-LAST:event_btnDeleteTelephoneActionPerformed

    private void btnSupplierRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierRolesActionPerformed

        List<PartyRole> list = LoadAccountWorker.createSupplierPartyRoles(partyGroupAccount.getParty().getpartyId());
        for (PartyRole prole : list) {
            PartyRoleComposite tmpPartyRoleComposite = new PartyRoleComposite();
            tmpPartyRoleComposite.setParty(partyGroupAccount);
            tmpPartyRoleComposite.setPartyRole(prole);
            tablePanel.listModel.add(tmpPartyRoleComposite);
        }
        LoadAccountWorker.createPartyRoles(partyGroupAccount, XuiContainer.getSession());
    }//GEN-LAST:event_btnSupplierRolesActionPerformed

    private void btnCustomerRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerRolesActionPerformed
        List<PartyRole> list = LoadAccountWorker.createCustomerPartyRoles(partyGroupAccount.getParty().getpartyId());
        for (PartyRole prole : list) {
            PartyRoleComposite tmpPartyRoleComposite = new PartyRoleComposite();
            tmpPartyRoleComposite.setParty(partyGroupAccount);
            tmpPartyRoleComposite.setPartyRole(prole);
            tablePanel.listModel.add(tmpPartyRoleComposite);
        }
        LoadAccountWorker.createPartyRoles(partyGroupAccount, XuiContainer.getSession());
    }//GEN-LAST:event_btnCustomerRolesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomerRoles;
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JButton btnSupplierRoles;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelRoleType;
    private javax.swing.JTextField txtParentTypeId;
    // End of variables declaration//GEN-END:variables
}
