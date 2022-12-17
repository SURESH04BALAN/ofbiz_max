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
import org.ofbiz.ordermax.entity.ProductFeature;
import org.ofbiz.ordermax.entity.ProductFeatureCategory;
import org.ofbiz.ordermax.entity.ProductFeatureType;
import org.ofbiz.ordermax.product.feature.ProductFeatureCategorySingleton;
import org.ofbiz.ordermax.product.feature.ProductFeatureTypeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProductFeaturePanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProductFeature> listSelectionModel = null;
    public  JGenericComboBoxSelectionModel<ProductFeatureType> productFeatureTypeModel = null;
    public  JGenericComboBoxSelectionModel<ProductFeatureCategory> productFeatureCategoryModel = null;
    
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProductFeaturePanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProductFeature> list = new ArrayList<ProductFeature>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProductFeature>(list);
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
 
        productFeatureTypeModel = new JGenericComboBoxSelectionModel<ProductFeatureType>(panelFetureTypeId, ProductFeatureTypeSingleton.getValueList());
        productFeatureCategoryModel = new JGenericComboBoxSelectionModel<ProductFeatureCategory>(panelFeatureCategoryId, ProductFeatureCategorySingleton.getValueList());
  
        txtProductFeatureId.getDocument().addDocumentListener(dirty);
        txtUomId.getDocument().addDocumentListener(dirty);
        txtDescription.getDocument().addDocumentListener(dirty);
        productFeatureTypeModel.jComboBox.addActionListener(dirty);
        productFeatureCategoryModel.jComboBox.addActionListener(dirty);
        
        currentRecord = new ProductFeature();
        setDialogField();

    }

    ProductFeature currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProductFeature) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            txtProductFeatureId.setText(currentRecord.getproductFeatureId());
            txtUomId.setText(currentRecord.getuomId());
            txtDescription.setText(currentRecord.getdescription());
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getproductFeatureTypeId())) {
                    productFeatureTypeModel.setSelectedItem(ProductFeatureTypeSingleton.getProductFeatureType(currentRecord.getproductFeatureTypeId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeaturePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getproductFeatureCategoryId())) {
                    productFeatureCategoryModel.setSelectedItem(ProductFeatureCategorySingleton.getProductFeatureCategory(currentRecord.getproductFeatureCategoryId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeaturePanel.class.getName()).log(Level.SEVERE, null, ex);
            }            
            dirty.setDirty(false);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        txtProductFeatureId.setText("");
        txtUomId.setText("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimpleProductFeaturePanel.class.getName());
            currentRecord.setproductFeatureId(txtProductFeatureId.getText());
            currentRecord.setuomId(txtUomId.getText());
            currentRecord.setdescription(txtDescription.getText());
            try {
                if (UtilValidate.isNotEmpty(productFeatureTypeModel.getSelectedItem())) {
                    currentRecord.setproductFeatureTypeId(productFeatureTypeModel.getSelectedItem().getproductFeatureTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeaturePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                if (UtilValidate.isNotEmpty(productFeatureCategoryModel.getSelectedItem())) {
                    currentRecord.setproductFeatureCategoryId(productFeatureCategoryModel.getSelectedItem().getproductFeatureCategoryId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductFeaturePanel.class.getName()).log(Level.SEVERE, null, ex);
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
        txtProductFeatureId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUomId = new javax.swing.JTextField();
        panelFetureTypeId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelOpenDate1 = new javax.swing.JPanel();
        txtDescription = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panelFeatureCategoryId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNumberSpecified = new javax.swing.JTextField();
        txtDefaultAmount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDefaultSequence = new javax.swing.JTextField();
        txtAbbrev = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtIdCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Product Feature Id:");

        jLabel2.setText("Product Feature Type Id:");

        jLabel3.setText("Uom Id:");

        javax.swing.GroupLayout panelFetureTypeIdLayout = new javax.swing.GroupLayout(panelFetureTypeId);
        panelFetureTypeId.setLayout(panelFetureTypeIdLayout);
        panelFetureTypeIdLayout.setHorizontalGroup(
            panelFetureTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFetureTypeIdLayout.setVerticalGroup(
            panelFetureTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel10.setText("Description:");

        javax.swing.GroupLayout panelOpenDate1Layout = new javax.swing.GroupLayout(panelOpenDate1);
        panelOpenDate1.setLayout(panelOpenDate1Layout);
        panelOpenDate1Layout.setHorizontalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate1Layout.setVerticalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel4.setText("Product Feature Category Id:");

        javax.swing.GroupLayout panelFeatureCategoryIdLayout = new javax.swing.GroupLayout(panelFeatureCategoryId);
        panelFeatureCategoryId.setLayout(panelFeatureCategoryIdLayout);
        panelFeatureCategoryIdLayout.setHorizontalGroup(
            panelFeatureCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFeatureCategoryIdLayout.setVerticalGroup(
            panelFeatureCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel5.setText("Number Specified:");

        jLabel6.setText("Default Amount:");

        jLabel7.setText("Default Sequence Num:");

        jLabel8.setText("Abbrev:");

        jLabel9.setText("Id Code:");

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
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUomId)
                    .addComponent(txtProductFeatureId)
                    .addComponent(panelFetureTypeId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFeatureCategoryId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNumberSpecified)
                    .addComponent(txtDefaultAmount)
                    .addComponent(txtDefaultSequence)
                    .addComponent(txtAbbrev)
                    .addComponent(txtIdCode))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProductFeatureId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelFetureTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelFeatureCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUomId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumberSpecified, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDefaultAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDefaultSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtAbbrev, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtIdCode, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFeatureCategoryId;
    private javax.swing.JPanel panelFetureTypeId;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel recordList;
    private javax.swing.JTextField txtAbbrev;
    private javax.swing.JTextField txtDefaultAmount;
    private javax.swing.JTextField txtDefaultSequence;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtIdCode;
    private javax.swing.JTextField txtNumberSpecified;
    public javax.swing.JTextField txtProductFeatureId;
    private javax.swing.JTextField txtUomId;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProductFeature();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setproductFeatureId("");
            currentRecord.setdescription(OrderMaxUtility.getValidString(txtDescription.getText()) + "(Copy)");
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
        return UtilMisc.toList("productFeatureId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(txtProductFeatureId.getText())) {
            txtProductFeatureId.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(txtDescription.getText())) {
            txtDescription.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(productFeatureTypeModel.getSelectedItem())) {
            productFeatureTypeModel.jComboBox.requestFocus();
            result = false;
        }else if (UtilValidate.isEmpty(productFeatureCategoryModel.getSelectedItem())) {
            productFeatureCategoryModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleProductFeaturePanel.class.getName());
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
        List<ProductFeature> list = new ArrayList<ProductFeature>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProductFeature(genVal));
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
        currentRecord = new ProductFeature(val);
        setDialogField();
    }
}
