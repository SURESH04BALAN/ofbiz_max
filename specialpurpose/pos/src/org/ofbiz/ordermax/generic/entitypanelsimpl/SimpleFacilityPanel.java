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
import org.ofbiz.ordermax.entity.FacilityType;
import org.ofbiz.ordermax.facility.FacilityTypeSingleton;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleFacilityPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<Facility> listSelectionModel = null;
    public  JGenericComboBoxSelectionModel<FacilityType> facilityModel = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleFacilityPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<Facility> list = new ArrayList<Facility>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<Facility>(list);
        recordList.setLayout(new BorderLayout());
        loadList();
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

        facilityModel = new JGenericComboBoxSelectionModel<FacilityType>(FacilityTypeSingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, facilityModel.jComboBox);

        facilityIdTextField.getDocument().addDocumentListener(dirty);
        facilityNameTextField.getDocument().addDocumentListener(dirty);
        txtEntitySync.getDocument().addDocumentListener(dirty);
        facilityModel.jComboBox.addActionListener(dirty);

        currentRecord = new Facility();
        setDialogField();

    }

    Facility currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (Facility) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            facilityIdTextField.setText(currentRecord.getfacilityId());
            facilityNameTextField.setText(currentRecord.getfacilityName());
            txtEntitySync.setText(currentRecord.getdescription());
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getfacilityTypeId())) {
                    facilityModel.setSelectedItem(FacilityTypeSingleton.getFacilityType(currentRecord.getfacilityTypeId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleFacilityPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dirty.setDirty(false);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        facilityIdTextField.setText("");
        facilityNameTextField.setText("");
    }

    @Override
    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimpleFacilityPanel.class.getName());
            currentRecord.setfacilityId(facilityIdTextField.getText());
            currentRecord.setfacilityName(facilityNameTextField.getText());
            currentRecord.setdescription(txtEntitySync.getText());
            try {
                if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
                    currentRecord.setfacilityTypeId(facilityModel.getSelectedItem().getfacilityTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleFacilityPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        recordList = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        facilityIdTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        facilityNameTextField = new javax.swing.JTextField();
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
            .addGap(0, 95, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Facilityl Id:");

        jLabel2.setText("Facility Type:");

        jLabel3.setText("Facility Name:");

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

        jLabel10.setText("Description:");

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
                    .addComponent(facilityNameTextField)
                    .addComponent(facilityIdTextField)
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
                    .addComponent(facilityIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(facilityNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField facilityIdTextField;
    private javax.swing.JTextField facilityNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel recordList;
    private javax.swing.JTextField txtEntitySync;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new Facility();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setfacilityId("");
            currentRecord.setfacilityName(OrderMaxUtility.getValidString(facilityNameTextField.getText()) + "(Copy)");
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
        return UtilMisc.toList("facilityId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(facilityIdTextField.getText())) {
            facilityIdTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(facilityNameTextField.getText())) {
            facilityNameTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(facilityModel.getSelectedItem())) {
            facilityModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleFacilityPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    void loadList() {
        loadList(null);
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        List<Facility> list = new ArrayList<Facility>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new Facility(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new Facility(val);
        setDialogField();
    }
}
