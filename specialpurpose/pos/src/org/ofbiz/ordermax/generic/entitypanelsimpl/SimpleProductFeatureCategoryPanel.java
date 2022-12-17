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
import org.ofbiz.ordermax.entity.ProductFeatureCategory;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProductFeatureCategoryPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProductFeatureCategory> listSelectionModel = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProductFeatureCategoryPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProductFeatureCategory> list = new ArrayList<ProductFeatureCategory>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProductFeatureCategory>(list);
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

     

        txtProductFeatureGroupId.getDocument().addDocumentListener(dirty);
        txtDescription.getDocument().addDocumentListener(dirty);
        txtParentCategoryId.getDocument().addDocumentListener(dirty);
        currentRecord = new ProductFeatureCategory();
        setDialogField();

    }

    ProductFeatureCategory currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProductFeatureCategory) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            txtProductFeatureGroupId.setText(currentRecord.getproductFeatureCategoryId());
            txtDescription.setText(currentRecord.getdescription());       
            txtParentCategoryId.setText(currentRecord.getparentCategoryId());             
            dirty.setDirty(false);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        txtProductFeatureGroupId.setText("");
        txtDescription.setText("");
    }

    @Override
    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimpleProductFeatureCategoryPanel.class.getName());
            currentRecord.setproductFeatureCategoryId(txtProductFeatureGroupId.getText());
            currentRecord.setdescription(txtDescription.getText());
            currentRecord.setparentCategoryId(txtParentCategoryId.getText());                
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
        txtProductFeatureGroupId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtParentCategoryId = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Product Feature Group Id:");

        jLabel3.setText("Description:");

        jLabel4.setText("Parent Category Id:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(txtProductFeatureGroupId)
                    .addComponent(txtParentCategoryId, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProductFeatureGroupId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtParentCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel recordList;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtParentCategoryId;
    public javax.swing.JTextField txtProductFeatureGroupId;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProductFeatureCategory();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setproductFeatureCategoryId("");
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
        return UtilMisc.toList("productFeatureCategoryId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(txtProductFeatureGroupId.getText())) {
            txtProductFeatureGroupId.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(txtDescription.getText())) {
            txtDescription.requestFocus();
            result = false;
        } 
        Debug.logInfo(" isValidValues: " + result, SimpleProductFeatureCategoryPanel.class.getName());
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
        List<ProductFeatureCategory> list = new ArrayList<ProductFeatureCategory>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProductFeatureCategory(genVal));
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
        currentRecord = new ProductFeatureCategory(val);
        setDialogField();
    }
}
