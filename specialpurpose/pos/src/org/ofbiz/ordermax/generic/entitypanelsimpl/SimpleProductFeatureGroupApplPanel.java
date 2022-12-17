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
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductFeature;
import org.ofbiz.ordermax.entity.ProductFeatureGroup;
import org.ofbiz.ordermax.entity.ProductFeatureGroupAppl;
import org.ofbiz.ordermax.facility.FacilityTypeSingleton;
import org.ofbiz.ordermax.product.feature.ProductFeatureGroupSingleton;
import org.ofbiz.ordermax.product.feature.ProductFeatureSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProductFeatureGroupApplPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProductFeatureGroupAppl> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<ProductFeatureGroup> featureGroupSelectionModel = null;
    public JGenericComboBoxSelectionModel<ProductFeature> featureSelectionModel = null;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;

    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProductFeatureGroupApplPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProductFeatureGroupAppl> list = new ArrayList<ProductFeatureGroupAppl>();//ProductFeatureGroupSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProductFeatureGroupAppl>(list);
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

        featureGroupSelectionModel = new JGenericComboBoxSelectionModel<ProductFeatureGroup>(ProductFeatureGroupSingleton.getValueList());
        panelFeatureGroup.setLayout(new BorderLayout());
        panelFeatureGroup.add(BorderLayout.CENTER, featureGroupSelectionModel.jComboBox);

        featureSelectionModel = new JGenericComboBoxSelectionModel<ProductFeature>(ProductFeatureSingleton.getValueList());
        panelFeature.setLayout(new BorderLayout());
        panelFeature.add(BorderLayout.CENTER, featureSelectionModel.jComboBox);

        fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        fromDatePanel.txtDate.getDocument().addDocumentListener(dirty);
        thruDatePanel.txtDate.getDocument().addDocumentListener(dirty);
        txtSequence.getDocument().addDocumentListener(dirty);
        featureGroupSelectionModel.jComboBox.addActionListener(dirty);
        featureSelectionModel.jComboBox.addActionListener(dirty);
        currentRecord = new ProductFeatureGroupAppl();
        setDialogField();

    }

    ProductFeatureGroupAppl currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProductFeatureGroupAppl) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            try {
                fromDatePanel.setTimeStamp(currentRecord.getFromDate());
            } catch (Exception ex) {
//                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                fromDatePanel.txtDate.setText("");
            }
            try {
                thruDatePanel.setTimeStamp(currentRecord.getThruDate());
            } catch (Exception ex) {
                // Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
                thruDatePanel.txtDate.setText("");
            }
            if (currentRecord.getSequenceNum() != null) {
                txtSequence.setText(currentRecord.getSequenceNum().toString());
            }
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getProductFeatureId())) {
                    featureSelectionModel.setSelectedItem(ProductFeatureSingleton.getProductFeature(currentRecord.getProductFeatureId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeatureGroupApplPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getProductFeatureGroupId())) {
                    featureGroupSelectionModel.setSelectedItem(ProductFeatureGroupSingleton.getProductFeatureGroup(currentRecord.getProductFeatureGroupId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeatureGroupApplPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dirty.setDirty(false);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {
        thruDatePanel.txtDate.setText("");
    }

    @Override
    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimpleProductFeatureGroupApplPanel.class.getName());
            try {
                currentRecord.setFromDate(fromDatePanel.getTimeStamp());
            } catch (Exception ex) {
                Logger.getLogger(SimpleUserLoginSecurityGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                currentRecord.setThruDate(thruDatePanel.getTimeStamp());
            } catch (Exception ex) {
                currentRecord.setThruDate(null);
            }

            if (UtilValidate.isNotEmpty(featureGroupSelectionModel.getSelectedItem())) {
                currentRecord.setProductFeatureGroupId(featureGroupSelectionModel.getSelectedItem().getproductFeatureGroupId());
            }
            //     if (UtilValidate.isNotEmpty(comboUserLogin.getSelectedItem())) {
            //        currentRecord.setuserLoginId(comboUserLogin.getSelectedItem().getuserLoginId());
            //   }
            try {
                if (UtilValidate.isNotEmpty(txtSequence.getText())) {
                    currentRecord.setSequenceNum(new Long(txtSequence.getText()));
                }
            } catch (Exception ex) {
                currentRecord.setSequenceNum(null);
            }

            try {
                if (UtilValidate.isNotEmpty(featureSelectionModel.getSelectedItem())) {
                    currentRecord.setProductFeatureId(featureSelectionModel.getSelectedItem().getproductFeatureId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeatureGroupApplPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelFeature = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelOpenDate1 = new javax.swing.JPanel();
        txtSequence = new javax.swing.JTextField();
        panelFromDate = new javax.swing.JPanel();
        panelFeatureGroup = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelThruDate = new javax.swing.JPanel();

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
            .addGap(0, 42, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Product Feature Group:");

        jLabel2.setText("From Date:");

        jLabel3.setText("Product Feature");

        javax.swing.GroupLayout panelFeatureLayout = new javax.swing.GroupLayout(panelFeature);
        panelFeature.setLayout(panelFeatureLayout);
        panelFeatureLayout.setHorizontalGroup(
            panelFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFeatureLayout.setVerticalGroup(
            panelFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel10.setText("Sequence Num:");

        javax.swing.GroupLayout panelOpenDate1Layout = new javax.swing.GroupLayout(panelOpenDate1);
        panelOpenDate1.setLayout(panelOpenDate1Layout);
        panelOpenDate1Layout.setHorizontalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate1Layout.setVerticalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFeatureGroupLayout = new javax.swing.GroupLayout(panelFeatureGroup);
        panelFeatureGroup.setLayout(panelFeatureGroupLayout);
        panelFeatureGroupLayout.setHorizontalGroup(
            panelFeatureGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFeatureGroupLayout.setVerticalGroup(
            panelFeatureGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel4.setText("Thru Date:");

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
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
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFeature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFeatureGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(panelFeatureGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelFeature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelFeature, panelFeatureGroup, panelFromDate, panelOpenDate1, panelThruDate});

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFeature;
    private javax.swing.JPanel panelFeatureGroup;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JPanel recordList;
    private javax.swing.JTextField txtSequence;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProductFeatureGroupAppl();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
//            currentRecord.setfacilityId("");
//            currentRecord.setfacilityName(OrderMaxUtility.getValidString(facilityNameTextField.getText()) + "(Copy)");
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
        return UtilMisc.toList("productFeatureGroupId", "productFeatureId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(fromDatePanel.txtDate.getText())) {
            fromDatePanel.txtDate.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(featureGroupSelectionModel.getSelectedItem())) {
            featureGroupSelectionModel.jComboBox.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(featureSelectionModel.getSelectedItem())) {
            featureSelectionModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleProductFeatureGroupApplPanel.class.getName());
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
        List<ProductFeatureGroupAppl> list = new ArrayList<ProductFeatureGroupAppl>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProductFeatureGroupAppl(genVal));
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
        currentRecord = new ProductFeatureGroupAppl(val);
        setDialogField();
    }
}
