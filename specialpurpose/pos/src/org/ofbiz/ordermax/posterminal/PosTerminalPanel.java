/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.posterminal;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import mvc.model.table.PartyRoleTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.PosTerminal;
import org.ofbiz.ordermax.generic.GenericValueTablePanel;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.product.productstore.PosTerminalSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PosTerminalPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    private JGenericListBoxSelectionModel<PosTerminal> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    protected GenericValueTablePanel terminalLogTablePanel = null;
    protected GenericValueTablePanel terminalStateTablePanel = null;
    protected GenericValueTablePanel terminalInternTxTablePanel = null;

//    public GenericTableModelPanel<PartyRoleComposite, PartyRoleTableModel> tablePanel = null;
//    public GenericTableModelPanel<PartyRoleComposite, PartyRoleTableModel> tablePanel = null;

    /**
     * Creates new form PartyGroupDetailPanel
     */
    public PosTerminalPanel(ControllerOptions options) {
        initComponents();

        listSelectionModel = new JGenericListBoxSelectionModel<PosTerminal>(PosTerminalSingleton.getValueList());
        panelTerminalList.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        panelTerminalList.setLayout(new BorderLayout());
        panelTerminalList.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelTerminalList, "List");
        ComponentBorder.loweredBevelBorder(panelTerminalList, "Detail");

        listSelectionModel.jlistBox.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setPosTerminal(i);
                            break;
                        }
                    }
                }
            }
        });

        facilityModel = new JGenericComboBoxSelectionModel<Facility>(FacilitySingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, facilityModel.jComboBox);
        terminalStateTablePanel = new GenericValueTablePanel("PosTerminalState");
        terminalLogTablePanel  = new GenericValueTablePanel("PosTerminalLog");
        terminalInternTxTablePanel = new GenericValueTablePanel("PosTerminalInternTx");
        //tablePanel = new GenericTableModelPanel<PartyRoleComposite, PartyRoleTableModel>(new PartyRoleTableModel());
         panelTerminalState.setLayout(new BorderLayout());
         panelTerminalState.add(BorderLayout.CENTER, terminalStateTablePanel);

         panelTerminalLog.setLayout(new BorderLayout());
         panelTerminalLog.add(BorderLayout.CENTER, terminalLogTablePanel);

         
         panelTerminalInternTx.setLayout(new BorderLayout());
         panelTerminalInternTx.add(BorderLayout.CENTER, terminalInternTxTablePanel);
                 
//setupEditOrderTable();

         ComponentBorder.loweredBevelBorder(panelTerminalState, "List");
//         ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");
/*
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
         });*/
    }

    //private Account partyGroupAccount = null;
    public PosTerminal getPosTerminal() {
        return posTerminal;
    }

    public void setPosTerminal(PosTerminal posTerminal) {
        this.posTerminal = posTerminal;
    }
    PosTerminal posTerminal = null;

    public void setPosTerminal(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            posTerminal = (PosTerminal) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (posTerminal != null) {

            terminalIdTextField.setText(posTerminal.getPosTerminalId());
            terminalNameTextField.setText(posTerminal.getTerminalName());
            txtEntitySync.setText(posTerminal.getPushEntitySyncId());
            try {
                if (UtilValidate.isNotEmpty(posTerminal.getFacilityId())) {
                    facilityModel.setSelectedItem(FacilitySingleton.getFacility(posTerminal.getFacilityId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(PosTerminalPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));
    }

    public void clearDialogFields() {

        terminalIdTextField.setText("");
        terminalNameTextField.setText("");
    }

    public void getDialogFields() throws ParseException, java.text.ParseException {
 //       PartyGroup partygroup = partyGroupAccount.getPartyGroup();
        //       partygroup.setpartyId(OrderMaxUtility.getValidString(terminalIdTextField.getText()));

        //      partygroup.setgroupNameLocal(OrderMaxUtility.getValidString(terminalNameTextField.getText()));
//        partygroup.setnumEmployees(OrderMaxUtility.getValidString(numEmployeesTextField.getText()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        panelTerminalList = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        terminalIdTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        terminalNameTextField = new javax.swing.JTextField();
        panelFacilityId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelOpenDate1 = new javax.swing.JPanel();
        txtEntitySync = new javax.swing.JTextField();
        panelTerminalInternTx = new javax.swing.JPanel();
        panelTerminalLog = new javax.swing.JPanel();
        panelTerminalState = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnNewPostalAddress = new javax.swing.JButton();
        btnSavePostalAddress = new javax.swing.JButton();
        btnDeletePostalAddress = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelTerminalListLayout = new javax.swing.GroupLayout(panelTerminalList);
        panelTerminalList.setLayout(panelTerminalListLayout);
        panelTerminalListLayout.setHorizontalGroup(
            panelTerminalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        panelTerminalListLayout.setVerticalGroup(
            panelTerminalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(panelTerminalList, java.awt.BorderLayout.PAGE_START);

        jLabel1.setText("Terminal Id:");

        jLabel2.setText("Facility Id:");

        jLabel3.setText("Terminal Name:");

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel10.setText("Push Entity Sync Id:");

        javax.swing.GroupLayout panelOpenDate1Layout = new javax.swing.GroupLayout(panelOpenDate1);
        panelOpenDate1.setLayout(panelOpenDate1Layout);
        panelOpenDate1Layout.setHorizontalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEntitySync, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate1Layout.setVerticalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEntitySync, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(terminalNameTextField)
                    .addComponent(terminalIdTextField)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(terminalIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(terminalNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Detail", jPanel2);

        javax.swing.GroupLayout panelTerminalInternTxLayout = new javax.swing.GroupLayout(panelTerminalInternTx);
        panelTerminalInternTx.setLayout(panelTerminalInternTxLayout);
        panelTerminalInternTxLayout.setHorizontalGroup(
            panelTerminalInternTxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        panelTerminalInternTxLayout.setVerticalGroup(
            panelTerminalInternTxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Internal Txt", panelTerminalInternTx);

        javax.swing.GroupLayout panelTerminalLogLayout = new javax.swing.GroupLayout(panelTerminalLog);
        panelTerminalLog.setLayout(panelTerminalLogLayout);
        panelTerminalLogLayout.setHorizontalGroup(
            panelTerminalLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        panelTerminalLogLayout.setVerticalGroup(
            panelTerminalLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pos Terminal  Log", panelTerminalLog);

        javax.swing.GroupLayout panelTerminalStateLayout = new javax.swing.GroupLayout(panelTerminalState);
        panelTerminalState.setLayout(panelTerminalStateLayout);
        panelTerminalStateLayout.setHorizontalGroup(
            panelTerminalStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        panelTerminalStateLayout.setVerticalGroup(
            panelTerminalStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Terminal  State", panelTerminalState);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(417, 60));

        btnNewPostalAddress.setText("New");

        btnSavePostalAddress.setText("Save");

        btnDeletePostalAddress.setText("Delete");

        btnOk.setText("Ok");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNewPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeletePostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewPostalAddress)
                    .addComponent(btnDeletePostalAddress)
                    .addComponent(btnSavePostalAddress)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnDeletePostalAddress;
    public javax.swing.JButton btnNewPostalAddress;
    public javax.swing.JButton btnOk;
    public javax.swing.JButton btnSavePostalAddress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel panelTerminalInternTx;
    private javax.swing.JPanel panelTerminalList;
    private javax.swing.JPanel panelTerminalLog;
    private javax.swing.JPanel panelTerminalState;
    public javax.swing.JTextField terminalIdTextField;
    private javax.swing.JTextField terminalNameTextField;
    private javax.swing.JTextField txtEntitySync;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return btnOk;
    }

    @Override
    public JButton getCancelButton() {
        return btnCancel;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
