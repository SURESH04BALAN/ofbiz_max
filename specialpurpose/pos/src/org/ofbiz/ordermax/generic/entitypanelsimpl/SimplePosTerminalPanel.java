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
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.PosTerminal;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimplePosTerminalPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<PosTerminal> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimplePosTerminalPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<PosTerminal> list = new ArrayList<PosTerminal>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<PosTerminal>(list);
        recordList.setLayout(new BorderLayout());
        loadList(options);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        recordList.setLayout(new BorderLayout());
        recordList.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(recordList, "List");
        ComponentBorder.loweredBevelBorder(jPanel1, "Detail");

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

        facilityModel = new JGenericComboBoxSelectionModel<Facility>(FacilitySingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, facilityModel.jComboBox);

        terminalIdTextField.getDocument().addDocumentListener(dirty);
        terminalNameTextField.getDocument().addDocumentListener(dirty);
        txtEntitySync.getDocument().addDocumentListener(dirty);
        facilityModel.jComboBox.addActionListener(dirty);

        newRecord();
    }

    PosTerminal currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (PosTerminal) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            terminalIdTextField.setText(currentRecord.getPosTerminalId());
            terminalNameTextField.setText(currentRecord.getTerminalName());
            txtEntitySync.setText(currentRecord.getPushEntitySyncId());
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getFacilityId())) {
                    facilityModel.setSelectedItem(FacilitySingleton.getFacility(currentRecord.getFacilityId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimplePosTerminalPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dirty.setDirty(false);
        }
//        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));
    }

    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        terminalIdTextField.setText("");
        terminalNameTextField.setText("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimplePosTerminalPanel.class.getName());
            currentRecord.setPosTerminalId(terminalIdTextField.getText());
            currentRecord.setTerminalName(terminalNameTextField.getText());
            currentRecord.setPushEntitySyncId(txtEntitySync.getText());
            try {
                if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
                    currentRecord.setFacilityId(facilityModel.getSelectedItem().getfacilityId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimplePosTerminalPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        recordList = new javax.swing.JPanel();
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

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

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
                .addContainerGap())
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel recordList;
    public javax.swing.JTextField terminalIdTextField;
    private javax.swing.JTextField terminalNameTextField;
    private javax.swing.JTextField txtEntitySync;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new PosTerminal();
        setDialogField();
    }
   public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setPosTerminalId("");
            currentRecord.setTerminalName(OrderMaxUtility.getValidString(terminalNameTextField.getText()) + "(Copy)");
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
        return UtilMisc.toList("posTerminalId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(terminalIdTextField.getText())) {
            terminalIdTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(terminalNameTextField.getText())) {
            terminalNameTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(facilityModel.getSelectedItem())) {
            facilityModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimplePosTerminalPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        List<PosTerminal> list = new ArrayList<PosTerminal>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new PosTerminal(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }
    
    public JPanel getPanel(){
        return this;
    }

    
    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new PosTerminal(val);
        setDialogField();
    }
}
