/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import org.ofbiz.ordermax.generic.GenericSaveInterface;
import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.SecurityGroup;
import org.ofbiz.ordermax.entity.UserLogin;
import org.ofbiz.ordermax.entity.UserLoginSecurityGroup;
import org.ofbiz.ordermax.party.userlogin.SecurityGroupSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author BBS Auctions
 */
public class SimpleUserLoginSecurityGroupPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<UserLoginSecurityGroup> listSelectionModel = null;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;
    private JGenericComboBoxSelectionModel<SecurityGroup> comboProductCatalog = null;
    //  private JGenericComboBoxSelectionModel<UserLogin> comboUserLogin = null;

    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleUserLoginSecurityGroupPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<UserLoginSecurityGroup> list = new ArrayList<UserLoginSecurityGroup>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<UserLoginSecurityGroup>(list);
        panelHeader.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, scrollPane);

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
                            setSelected(i);
                            break;
                        }
                    }
                }
            }
        });

        comboProductCatalog = new JGenericComboBoxSelectionModel<SecurityGroup>(panelProductCatalog, SecurityGroupSingleton.getValueList()
        );

//        comboUserLogin = new JGenericComboBoxSelectionModel<UserLogin>(panelStores);
        fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        fromDatePanel.txtDate.getDocument().addDocumentListener(dirty);
        thruDatePanel.txtDate.getDocument().addDocumentListener(dirty);
        txtLoginId.getDocument().addDocumentListener(dirty);
        // comboUserLogin.jComboBox.addActionListener(dirty);
        comboProductCatalog.jComboBox.addActionListener(dirty);

        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
        ComponentBorder.loweredBevelBorder(panelHeader, "User Security Permissions");
        loadList();
        newRecord();
    }

    UserLoginSecurityGroup currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (UserLoginSecurityGroup) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            try {
                fromDatePanel.setTimeStamp(currentRecord.getfromDate());
            } catch (Exception ex) {
//                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                fromDatePanel.txtDate.setText("");
            }
            try {
                thruDatePanel.setTimeStamp(currentRecord.getthruDate());
            } catch (Exception ex) {
                // Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                thruDatePanel.txtDate.setText("");
            }
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getgroupId())) {
                    comboProductCatalog.setSelectedItem(SecurityGroupSingleton.getSecurityGroup(currentRecord.getgroupId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getuserLoginId())) {
                    //comboUserLogin.setSelectedItem(logins.get(currentRecord.getuserLoginId()));
                    txtLoginId.setText(currentRecord.getuserLoginId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

//            if (UtilValidate.isNotEmpty(currentRecord.getsequenceNum())) {
//                txtSequenceNum.setText(currentRecord.getsequenceNum().toString());
//            }
            dirty.setDirty(false);
        }
//        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));
    }

    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {
        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
//        txtSequenceNum.setText("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            try {
                currentRecord.setfromDate(fromDatePanel.getTimeStamp());
            } catch (Exception ex) {
                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                currentRecord.setthruDate(thruDatePanel.getTimeStamp());
            } catch (Exception ex) {
                currentRecord.setthruDate(null);
            }

            if (UtilValidate.isNotEmpty(comboProductCatalog.getSelectedItem())) {
                currentRecord.setgroupId(comboProductCatalog.getSelectedItem().getgroupId());
            }
       //     if (UtilValidate.isNotEmpty(comboUserLogin.getSelectedItem())) {
            //        currentRecord.setuserLoginId(comboUserLogin.getSelectedItem().getuserLoginId());
            //   }
            if (UtilValidate.isNotEmpty(txtLoginId.getText())) {
                currentRecord.setuserLoginId(txtLoginId.getText());
            }
        }
    }
    /*
     public static <T> T instanceOf(Class<T> clazz) throws Exception {
     return clazz.newInstance();
     }
    
     public static <T> Collection<T> getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
     Collection<T> objectList = new ArrayList<T>();
     Class<T> clazz 
     for (GenericValue genVal : genList) {
     objectList.add(new T(genVal));
     }
     return objectList;
     }    
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelProductCatalog = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelStores = new javax.swing.JPanel();
        txtLoginId = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 829, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 250));

        Store.setText("Security Group");

        jLabel3.setText("From Date:");

        jLabel4.setText("Thru Date:");

        panelProductCatalog.setEnabled(false);

        javax.swing.GroupLayout panelProductCatalogLayout = new javax.swing.GroupLayout(panelProductCatalog);
        panelProductCatalog.setLayout(panelProductCatalogLayout);
        panelProductCatalogLayout.setHorizontalGroup(
            panelProductCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelProductCatalogLayout.setVerticalGroup(
            panelProductCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

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

        Store1.setText("Login Id");

        panelStores.setLayout(null);
        panelStores.add(txtLoginId);
        txtLoginId.setBounds(0, 0, 260, 22);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(panelStores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelProductCatalog, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(258, Short.MAX_VALUE))
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store)
                    .addComponent(panelProductCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Store1, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(panelStores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jPanel2.add(panelDetail, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductCatalog;
    private javax.swing.JPanel panelStores;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JTextField txtLoginId;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new UserLoginSecurityGroup();
        currentRecord.setuserLoginId(userLoginId);
        setDialogField();
    }

    @Override
    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setgroupId("");
//            currentRecord.setprodCatalogId("");
            setDialogField();
            dirty.setDirty(true);
        }
    }

    @Override
    public Map<String, Object> getValuesMap() {
        if (currentRecord != null) {
            return currentRecord.getValuesMap();
        }
        return null;
    }

    @Override
    public GenericValue getGenericValueObj() {
        if (currentRecord != null) {
            currentRecord.getGenericValue();            
            return currentRecord.getGenericValueObj();
        }
        return null;
    }

    @Override
    public List<String> getKey() {
        return UtilMisc.toList("userLoginId","groupId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(comboProductCatalog.getSelectedItem())) {
            comboProductCatalog.jComboBox.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(txtLoginId.getText())) {
            txtLoginId.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleUserLoginSecurityGroupPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    String partyId;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    Map<String, UserLogin> logins = new HashMap<String, UserLogin>();

    void loadList() {
        loadList(null);
    }
    String userLoginId = null;

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        if (options != null && options.contains("userLoginId")) {
            userLoginId = (String) options.get("userLoginId");
        }

        List<UserLoginSecurityGroup> list = new ArrayList<UserLoginSecurityGroup>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            List<GenericValue> genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, UtilMisc.toMap("userLoginId", userLoginId), "userLoginId", delegator);
            for (GenericValue genVal : genList) {
                list.add(new UserLoginSecurityGroup(genVal));
            }

        }

        listSelectionModel.setDataList(list);
        if (currentRecord != null) {
            currentRecord.setuserLoginId(userLoginId);
            txtLoginId.setText(userLoginId);
        }

        /*
         logins.clear();
         Delegator delegator = ControllerOptions.getSession().getDelegator();
         if(options!=null && options.containsKey("Account")){
         Account account = (Account) options.get("Account");
         partyId = account.getParty().getpartyId();
         }
        
         List<GenericValue> userLogins = PosProductHelper.getGenericValueListsWithSelection( "UserLogin", UtilMisc.toMap("partyId",partyId), "userLoginId", delegator);
         for(GenericValue val : userLogins){
         UserLogin userLogin = new UserLogin(val);
         logins.put(userLogin.getuserLoginId(), userLogin);            
         }
        
         comboUserLogin.setDataList(UtilMisc.toList(logins.values()));
         */
    }

    public JPanel getPanel() {
        return this;
    }
    
    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new UserLoginSecurityGroup(val);
        setDialogField();
    }     
}
