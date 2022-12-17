/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.AccountCompositeTableModel;
import mvc.view.GenericTableModelPanel;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;

import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindPartyListPanel extends javax.swing.JPanel {

    private final JTextField txtPartyIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;
    public GenericTableModelPanel<Account, AccountCompositeTableModel> tablePanel = null;
    public JGenericComboBoxSelectionModel<PartyType> productIdComboModel = null;
    public JGenericComboBoxSelectionModel<RoleType> partyRoleComboModel = null;    
    ControllerOptions controllerOptions = null;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindPartyListPanel(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;
        
        tablePanel = new GenericTableModelPanel<Account, AccountCompositeTableModel>(new AccountCompositeTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
//        tablePartyList  = tablePanel.jTable;
        setupEditOrderTable();
        
        
        List<PartyType> findPartyList = PartyTypeSingleton.getValueList();
        PartyType partyType = new PartyType();
        partyType.setdescription("All");
        partyType.setpartyTypeId("ANY");
        findPartyList.add(0, partyType);

        productIdComboModel = new JGenericComboBoxSelectionModel<PartyType>(findPartyList);
        panelPartyTypeId.setLayout(new BorderLayout());
        panelPartyTypeId.add(BorderLayout.CENTER, productIdComboModel.jComboBox);
        productIdComboModel.comboBoxModel.setSelectedItem(partyType);
        
        List<RoleType> findPartyRoleList = RoleTypeSingleton.getValueList(this.controllerOptions.getRoleTypeParent());
        RoleType partyRole = new RoleType();
        partyRole.setdescription("All");
        partyRole.setroleTypeId("ANY");
        findPartyRoleList.add(0, partyRole);

        partyRoleComboModel  = new JGenericComboBoxSelectionModel<RoleType>(findPartyRoleList);
        panelPartyRole.setLayout(new BorderLayout());
        panelPartyRole.add(BorderLayout.CENTER, partyRoleComboModel.jComboBox);
        if(controllerOptions.getRoleType()!=null){
            partyRoleComboModel.comboBoxModel.setSelectedItem(controllerOptions.getRoleType());
        }
        
        if(controllerOptions.contains("partyId")){
            partyRoleComboModel.comboBoxModel.setSelectedItem(controllerOptions.getRoleType());
            txtPartyId.setText((String)controllerOptions.get("partyId"));
        }
        
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Account List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<Account> listModel) {
       tablePanel.setListModel(listModel);        
    }

    public Account getSelectedAccount() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtPartyIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < AccountCompositeTableModel.Columns.values().length; i++) {
            AccountCompositeTableModel.Columns[] columns = AccountCompositeTableModel.Columns.values();
            AccountCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (AccountCompositeTableModel.Columns.PARTYID == column) {
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
        }
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();
        boolean showAll = true;
        if (UtilValidate.isNotEmpty(txtPartyId.getText())) {
            findOptionList.put("partyId", txtPartyId.getText());
            showAll = false;
        }

        if (productIdComboModel.jComboBox.getSelectedItem() != null) {
            PartyType partyType = (PartyType) productIdComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getpartyTypeId())) {
                findOptionList.put("partyTypeId", partyType.getpartyTypeId());
                showAll = false;
            }
        }

        if (partyRoleComboModel.jComboBox.getSelectedItem() != null) {
            RoleType roleType = (RoleType) partyRoleComboModel.jComboBox.getSelectedItem();
            if (roleType != null && !"ANY".equals(roleType.getroleTypeId())) {
                findOptionList.put("roleTypeId", roleType.getroleTypeId());
                showAll = false;
            }
        }
        
        
        if (UtilValidate.isNotEmpty(txtFirstName.getText())) {
            findOptionList.put("firstName", txtFirstName.getText());
            showAll = false;
        }
        
        if (UtilValidate.isNotEmpty(txtLastName.getText())) {
            findOptionList.put("lastName", txtLastName.getText());
            showAll = false;
        }
        
        if (UtilValidate.isNotEmpty(txtGroupName.getText())) {
            findOptionList.put("groupName", txtGroupName.getText());
            showAll = false;
        }
        
        if (showAll == true) {
            findOptionList.put("showAll", "Y");
        }
        
        findOptionList.put("lookupFlag", "Y");                
        findOptionList.put("VIEW_INDEX", "0");
        findOptionList.put("VIEW_SIZE", "1000");        
        /*
         if (productNameComboModel.jComboBox.getSelectedItem() != null) {
         Account account = (Account) productNameComboModel.jComboBox.getSelectedItem();
         //            findOptionList.put("productId", account.getproductId());
         if (account.getParty().getpartyId()!= null && "All".equals(account.getParty().getpartyId()) == false) {
         findOptionList.put("partyId", account.getParty().getpartyId());
         }            
         //            findOptionList.put("internalName", account.getinternalName());
         }*/
        /*
         if(txtInternalName.getText()!=null && txtInternalName.getText().isEmpty()==false){
         findOptionList.put("internalName", txtInternalName.getText());            
         }        
         */
        return findOptionList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelLookupInvoice = new CollapsiblePanel();
        jLabel1 = new javax.swing.JLabel();
        cbProcessing = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        btnFind = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        panelPartyTypeId = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        cbCreated1 = new javax.swing.JCheckBox();
        cbCreated2 = new javax.swing.JCheckBox();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        cbCreated3 = new javax.swing.JCheckBox();
        txtPartyId = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtGroupName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelPartyRole = new javax.swing.JPanel();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.GridBagLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));
        panelLookupInvoice.setPreferredSize(new java.awt.Dimension(817, 200));
        java.awt.GridBagLayout panelLookupInvoiceLayout = new java.awt.GridBagLayout();
        panelLookupInvoiceLayout.columnWidths = new int[] {0, 3, 0, 3, 0, 3, 0, 3, 0};
        panelLookupInvoiceLayout.rowHeights = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Party Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLookupInvoice.add(jLabel1, gridBagConstraints);

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbProcessing, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Party Type Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel3, gridBagConstraints);

        btnFind.setText("Find");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(btnFind, gridBagConstraints);

        jComboBox1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox1, gridBagConstraints);

        panelPartyTypeId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPartyTypeIdLayout = new javax.swing.GroupLayout(panelPartyTypeId);
        panelPartyTypeId.setLayout(panelPartyTypeIdLayout);
        panelPartyTypeIdLayout.setHorizontalGroup(
            panelPartyTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPartyTypeIdLayout.setVerticalGroup(
            panelPartyTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 173;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelPartyTypeId, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Last name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel4, gridBagConstraints);

        jComboBox3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox3, gridBagConstraints);

        cbCreated1.setSelected(true);
        cbCreated1.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbCreated1, gridBagConstraints);

        cbCreated2.setSelected(true);
        cbCreated2.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbCreated2, gridBagConstraints);

        jComboBox4.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox4, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Group Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel5, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("First name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel6, gridBagConstraints);

        jComboBox5.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jComboBox5, gridBagConstraints);

        cbCreated3.setSelected(true);
        cbCreated3.setText("Ignore Case");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(cbCreated3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtPartyId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtFirstName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtLastName, gridBagConstraints);

        txtGroupName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGroupNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtGroupName, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Party Role:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel7, gridBagConstraints);

        panelPartyRole.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPartyRoleLayout = new javax.swing.GroupLayout(panelPartyRole);
        panelPartyRole.setLayout(panelPartyRoleLayout);
        panelPartyRoleLayout.setHorizontalGroup(
            panelPartyRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPartyRoleLayout.setVerticalGroup(
            panelPartyRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 173;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelPartyRole, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        add(panelLookupInvoice, gridBagConstraints);

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panelIResultList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void txtGroupNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGroupNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGroupNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbCreated1;
    public javax.swing.JCheckBox cbCreated2;
    public javax.swing.JCheckBox cbCreated3;
    public javax.swing.JCheckBox cbProcessing;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelPartyRole;
    private javax.swing.JPanel panelPartyTypeId;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGroupName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPartyId;
    // End of variables declaration//GEN-END:variables
}
