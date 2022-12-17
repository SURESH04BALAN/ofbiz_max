/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party.userlogin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadAccountWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.StringListCellRenderer;
import mvc.model.table.UserLoginTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.entity.UserLogin;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import org.ofbiz.ordermax.generic.GenericSavePanel;
import org.ofbiz.ordermax.generic.entitypanelsimpl.EntityPanelFactory;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class UserLoginMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = UserLoginMaintainPanel.class.getName();
    private ListAdapterListModel<UserLogin> personListModel = new ListAdapterListModel<UserLogin>();
    public GenericTableModelPanel<UserLogin, UserLoginTableModel> tablePanel = null;

//    private ListModelSelection<UserLogin> listModelSelection = new ListModelSelection<UserLogin>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    UserLogin userLogin = null;

    boolean isNew = false;
    boolean isModified = false;

    private DatePickerEditPanel thruDatePanel = null;
//    private JGenericComboBoxSelectionModel<UserLogin> comboProductCatalog = null;
    private JGenericComboBoxSelectionModel<String> comboEnabled = null;
    private JGenericComboBoxSelectionModel<String> comboRequirePassChange = null;

    public UserLoginMaintainPanel() {
        initComponents();
        /* ListCellRenderer<UserLogin> prodCatalogRenderer = new ProdCatalogListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
        tablePanel = new GenericTableModelPanel<UserLogin, UserLoginTableModel>(new UserLoginTableModel());
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, tablePanel);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        for (int i = 0; i < UserLoginTableModel.Columns.values().length; i++) {
            UserLoginTableModel.Columns[] columns = UserLoginTableModel.Columns.values();
            UserLoginTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        comboEnabled = new JGenericComboBoxSelectionModel<String>(panelEnabled, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));

        comboRequirePassChange = new JGenericComboBoxSelectionModel<String>(panelRequirePasswordChangr,
                YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));

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
                            clearDialogFields();
                            isNew = false;
                            isModified = false;
                            userLogin = tablePanel.listModel.getElementAt(i);
                            setDialogFields();
                            break;
                        }
                    }
                }
            }
        });

        /*panelHeader.add(BorderLayout.CENTER, scrollPane);
         catalogListSelectionModel.selectionModel.addListSelectionListener(new ListSelectionListener() {

         public void valueChanged(ListSelectionEvent e) {
         prodCatalog = catalogListSelectionModel.listModelSelection.getSelection();
         setDialogFields(prodCatalog);
         isNew = false;
         }
         });
         */
        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
        ComponentBorder.loweredBevelBorder(panelHeader, "User Logins");

    }

//    UserLogin prodCatalog = null;
    public void setDialogFields() {
//        buttonChangePassword.setEnabled(!isNew);
//        txtCurrentPassword.setEnabled(!isNew);

        panelPartyId.setText(userLogin.getpartyId());
        try {
            thruDatePanel.setTimeStamp(userLogin.getdisabledDateTime());
        } catch (Exception ex) {
//            Logger.getLogger(UserLoginMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            comboEnabled.setSelectedItem(YesNoConditionSelectSingleton.getString(userLogin.getenabled()));
        } catch (Exception ex) {
            //          Logger.getLogger(UserLoginMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            comboRequirePassChange.setSelectedItem(YesNoConditionSelectSingleton.getString(userLogin.getrequirePasswordChange()));
        } catch (Exception ex) {
//            Logger.getLogger(UserLoginMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(userLogin.getsuccessiveFailedLogins())) {
            txtFailedLogin.setText(userLogin.getsuccessiveFailedLogins().toString());
        }
        txtUserLogin.setText(userLogin.getuserLoginId());
        txtPasswordHint.setText(userLogin.getpasswordHint());
        //              columnValue = userLogin.getenabled();
        //            columnValue = userLogin.getcreatedStamp();
        //          columnValue = userLogin.getdisabledDateTime();
        //        columnValue = userLogin.gethasLoggedOut();
        //        columnValue = userLogin.getrequirePasswordChange();                
//                columnValue = userLogin.getsuccessiveFailedLogins();                
        txtExternalAuth.setText(userLogin.getexternalAuthId());

    }

    public void clearDialogFields() {
//        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
        txtExternalAuth.setText("");
        txtUserLogin.setText("");
        txtFailedLogin.setText("");
        txtPasswordHint.setText("");
        txtNewPassword.setText("");
        txtVerifyPassword.setText("");
    }

    public void getDialogFields() {

        try {
            userLogin.setdisabledDateTime(thruDatePanel.getTimeStamp());
        } catch (Exception ex) {
            userLogin.setdisabledDateTime(null);
        }

        if (UtilValidate.isNotEmpty(comboEnabled.getSelectedItem())) {
            userLogin.setenabled(comboEnabled.getSelectedItem());
        }

        if (UtilValidate.isNotEmpty(comboRequirePassChange.getSelectedItem())) {
            userLogin.setrequirePasswordChange(comboRequirePassChange.getSelectedItem());
        }

        if (UtilValidate.isNotEmpty(txtFailedLogin.getText())) {
            userLogin.setsuccessiveFailedLogins(Long.parseLong(txtFailedLogin.getText()));
        }

        userLogin.setpartyId(panelPartyId.getText());
        userLogin.setuserLoginId(txtUserLogin.getText());
        userLogin.setpasswordHint(txtPasswordHint.getText());
        userLogin.setcurrentPassword(txtNewPassword.getText());
    }

    public void setUserLoginList(ListAdapterListModel<UserLogin> orderListModel) {
        tablePanel.setListModel(orderListModel);
    }

    public UserLogin getProductStoreCatalog() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelRequirePasswordChangr = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        Store2 = new javax.swing.JLabel();
        Store3 = new javax.swing.JLabel();
        Store4 = new javax.swing.JLabel();
        panelEnabled = new javax.swing.JPanel();
        panelPartyId = new javax.swing.JTextField();
        txtUserLogin = new javax.swing.JTextField();
        txtFailedLogin = new javax.swing.JTextField();
        txtExternalAuth = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        Store6 = new javax.swing.JLabel();
        buttonChangePassword = new javax.swing.JButton();
        Store7 = new javax.swing.JLabel();
        txtVerifyPassword = new javax.swing.JTextField();
        Store5 = new javax.swing.JLabel();
        Store8 = new javax.swing.JLabel();
        txtPasswordHint = new javax.swing.JTextField();
        txtNewPassword = new javax.swing.JTextField();
        txtCurrentPassword = new javax.swing.JTextField();
        btnSecurity = new javax.swing.JButton();
        panelHeader = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 300));

        Store.setText("Party Id:");

        jLabel3.setText("Successive Failed Logins:");

        jLabel4.setText("Disabled Date Time:");

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        Store1.setText("Require Password Change:");

        javax.swing.GroupLayout panelRequirePasswordChangrLayout = new javax.swing.GroupLayout(panelRequirePasswordChangr);
        panelRequirePasswordChangr.setLayout(panelRequirePasswordChangrLayout);
        panelRequirePasswordChangrLayout.setHorizontalGroup(
            panelRequirePasswordChangrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelRequirePasswordChangrLayout.setVerticalGroup(
            panelRequirePasswordChangrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        Store2.setText("External Auth Id:");

        Store3.setText("User Login ID:");

        Store4.setText("Enabled:");

        panelEnabled.setEnabled(false);

        javax.swing.GroupLayout panelEnabledLayout = new javax.swing.GroupLayout(panelEnabled);
        panelEnabled.setLayout(panelEnabledLayout);
        panelEnabledLayout.setHorizontalGroup(
            panelEnabledLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelEnabledLayout.setVerticalGroup(
            panelEnabledLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelPartyId.setEditable(false);

        txtUserLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserLoginActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Set Password"));

        Store6.setText("New Password:");

        buttonChangePassword.setText("Change Password");
        buttonChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangePasswordActionPerformed(evt);
            }
        });

        Store7.setText("Verify Password:");

        Store5.setText("Current Password:");

        Store8.setText("Password Hint:");

        txtNewPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNewPassword)
                    .addComponent(buttonChangePassword)
                    .addComponent(txtVerifyPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(txtCurrentPassword)
                    .addComponent(txtPasswordHint))
                .addGap(158, 158, 158))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCurrentPassword, txtNewPassword, txtPasswordHint, txtVerifyPassword});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Store5)
                    .addComponent(txtCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store6)
                    .addComponent(txtNewPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Store7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store8)
                    .addComponent(txtPasswordHint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(buttonChangePassword)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSecurity.setText("Security Permission");
        btnSecurity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSecurityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addGap(6, 6, 6)
                        .addComponent(btnSecurity))
                    .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFailedLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExternalAuth)
                    .addComponent(panelRequirePasswordChangr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelEnabled, panelPartyId, panelRequirePasswordChangr, panelThruDate, txtExternalAuth, txtFailedLogin, txtUserLogin});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {newButton, saveButton});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Store)
                            .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Store3)
                            .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store4)
                            .addComponent(panelEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store1)
                            .addComponent(panelRequirePasswordChangr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Store2)
                            .addComponent(txtExternalAuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFailedLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveButton)
                            .addComponent(newButton)
                            .addComponent(btnSecurity))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(panelDetail, java.awt.BorderLayout.PAGE_END);

        panelHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Catalog Stores"));
        panelHeader.setPreferredSize(new java.awt.Dimension(489, 200));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 985, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields();

        try {
//            if (isNew) {
//            }

            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            LoadAccountWorker.saveUserLogin(userLogin, txtVerifyPassword.getText(), ControllerOptions.getSession());
            if (isNew) {
                tablePanel.listModel.add(userLogin);
            }
            isNew = false;
            //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
            //        saveAction.actionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(UserLoginMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        userLogin = new UserLogin();
        userLogin.setpartyId(panelPartyId.getText());
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void buttonChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangePasswordActionPerformed

        try {

            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);
            getDialogFields();
            LoadAccountWorker.updatePassword(userLogin, txtNewPassword.getText(), txtVerifyPassword.getText(), XuiContainer.getSession());

            //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
            //        saveAction.actionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(UserLoginMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_buttonChangePasswordActionPerformed

    private void txtUserLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserLoginActionPerformed

    private void txtNewPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewPasswordActionPerformed

    private void btnSecurityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSecurityActionPerformed

        if (userLogin != null && UtilValidate.isNotEmpty(userLogin.getuserLoginId())) {
            ControllerOptions tmpOptions = new ControllerOptions();
            tmpOptions.put("EntityName", "UserLoginSecurityGroup");
            tmpOptions.put("X", 200);
            tmpOptions.put("Y", 0);
       // final SimplePosTerminalPanel posTerminal = new SimplePosTerminalPanel(tmpOptions);
            //final SimpleFacilityPanel posTerminal = new SimpleFacilityPanel(tmpOptions);
            //final SimpleProductStorePanel posTerminal = new SimpleProductStorePanel(tmpOptions);
            GenericSaveInterface entityPanelInterface = EntityPanelFactory.createEntityPanel(tmpOptions);
            final GenericSavePanel viewer = new GenericSavePanel(tmpOptions);
            viewer.setGenericSaveInterface(entityPanelInterface);
            viewer.add(entityPanelInterface.getPanel(), BorderLayout.CENTER);
            tmpOptions.setSimpleScreenInterface(viewer);
            final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(tmpOptions);
            Debug.logInfo("userLogin.getuserLoginId(): " + userLogin.getuserLoginId(), module);
            entityPanelInterface.loadList(null);
            frame.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                if (viewer.getSelectedValue() != null) {
                                    /* //seleVal.put("Category", viewer.selectedCategory);
                                     // GenericValue genericValue = (GenericValue) genValMap.get("Category");
                                     productIdTextField.setText(viewer.getSelectedValue().getString(lookupInterface.getEntityId()));
                                     productIdTextField.requestFocusInWindow();
                                     lookupInterface.setGenericValue(viewer.getSelectedValue());
                                     //notify 
                                     ActionEvent event = new ActionEvent(this, 1, "categoryId", new Date().getTime(), 2);
                                     for (ActionListener listener : listeners) {
                                     listener.actionPerformed(event); // broadcast to all
                                     }
                                     listeners.clear();
                                     Debug.logInfo(" lookup brandCategory : " + viewer.getSelectedValue().getString(lookupInterface.getEntityId()), module);
                                     */
                                    /*
                                     panel.getOrder().addContactMech("BILLING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
                                     //                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                     panel.setDialogFields();
                                     //                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                     Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                     */
                                }
                                // terminalLogTablePanel.loadList();
                            }
                        }
                    });
        }
    }//GEN-LAST:event_btnSecurityActionPerformed
    protected ClassLoader getClassLoader() {
        //Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();
            //Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    //Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                    Debug.logError("class loader 5", module);
                }

                if (cl == null) {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JLabel Store2;
    private javax.swing.JLabel Store3;
    private javax.swing.JLabel Store4;
    private javax.swing.JLabel Store5;
    private javax.swing.JLabel Store6;
    private javax.swing.JLabel Store7;
    private javax.swing.JLabel Store8;
    private javax.swing.JButton btnSecurity;
    public javax.swing.JButton buttonChangePassword;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton newButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelEnabled;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTextField panelPartyId;
    private javax.swing.JPanel panelRequirePasswordChangr;
    private javax.swing.JPanel panelThruDate;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txtCurrentPassword;
    private javax.swing.JTextField txtExternalAuth;
    private javax.swing.JTextField txtFailedLogin;
    private javax.swing.JTextField txtNewPassword;
    private javax.swing.JTextField txtPasswordHint;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtVerifyPassword;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return null;
    }

    @Override
    public JButton getCancelButton() {
        return null;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
