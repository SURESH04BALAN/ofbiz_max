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
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ShoppingList;
import org.ofbiz.ordermax.entity.ShoppingListType;
import org.ofbiz.ordermax.generic.LoadGenericValues;
import org.ofbiz.ordermax.orderbase.shoppinglist.ShoppingListTypeSingleton;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.ordermax.productstore.ProductStoreSetupMenuAction;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleShoppingListPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ShoppingList> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<ShoppingListType> facilityModel = null;
    public JGenericComboBoxSelectionModel<ProductStore> productStoreCombo = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;
    PartyPickerEditPanel partyPickerEditPanel;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleShoppingListPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ShoppingList> list = new ArrayList<ShoppingList>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ShoppingList>(list);
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

        facilityModel = new JGenericComboBoxSelectionModel<ShoppingListType>(ShoppingListTypeSingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, facilityModel.jComboBox);

        partyPickerEditPanel = new PartyPickerEditPanel(options, panelPartyId);
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);
        productStoreCombo = new JGenericComboBoxSelectionModel<ProductStore>(ProductStoreSingleton.getValueList());
        panelProductStoreId.setLayout(new BorderLayout());
        panelProductStoreId.add(BorderLayout.CENTER, productStoreCombo.jComboBox);

        partyPickerEditPanel.textIdField.getDocument().addDocumentListener(dirty);
        facilityNameTextField.getDocument().addDocumentListener(dirty);
        txtEntitySync.getDocument().addDocumentListener(dirty);
        facilityModel.jComboBox.addActionListener(dirty);
        productStoreCombo.jComboBox.addActionListener(dirty);

        currentRecord = new ShoppingList();
        setDialogField();

    }

    ShoppingList currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ShoppingList) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            shoppingListIdTextField.setText(currentRecord.getshoppingListId());
            facilityNameTextField.setText(currentRecord.getlistName());
            txtEntitySync.setText(currentRecord.getdescription());
            partyPickerEditPanel.textIdField.setText(currentRecord.getpartyId());
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getshoppingListTypeId())) {
                    facilityModel.setSelectedItem(ShoppingListTypeSingleton.getShoppingListType(currentRecord.getshoppingListTypeId()));
                }
                if (UtilValidate.isNotEmpty(currentRecord.getproductStoreId())) {
                    productStoreCombo.setSelectedItem(ProductStoreSingleton.getProductStore(currentRecord.getproductStoreId()));
                }

            } catch (Exception ex) {
                Logger.getLogger(SimpleShoppingListPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            dirty.setDirty(false);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        shoppingListIdTextField.setText("");
        facilityNameTextField.setText("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            Debug.logInfo(" getDialogFields Print 1", SimpleShoppingListPanel.class.getName());
            currentRecord.setshoppingListId(shoppingListIdTextField.getText());
            currentRecord.setlistName(facilityNameTextField.getText());
            currentRecord.setdescription(txtEntitySync.getText());
            currentRecord.setpartyId(partyPickerEditPanel.textIdField.getText());
            try {
                if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
                    currentRecord.setshoppingListTypeId(facilityModel.getSelectedItem().getshoppingListTypeId());
                }
                if (UtilValidate.isNotEmpty(productStoreCombo.getSelectedItem() != null)) {
                    currentRecord.setproductStoreId(productStoreCombo.getSelectedItem().getproductStoreId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleShoppingListPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        shoppingListIdTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        facilityNameTextField = new javax.swing.JTextField();
        panelFacilityId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtEntitySync = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panelPartyId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelProductStoreId = new javax.swing.JPanel();
        btnShoppingListItem = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 89, Short.MAX_VALUE)
        );

        jPanel2.add(recordList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Shooping List Id:");

        shoppingListIdTextField.setEditable(false);

        jLabel2.setText("List Type:");

        jLabel3.setText("List Name:");

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel10.setText("Description:");

        jLabel4.setText("Party Id:");

        panelPartyId.setMaximumSize(new java.awt.Dimension(201, 22));

        javax.swing.GroupLayout panelPartyIdLayout = new javax.swing.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel5.setText("Product Store Id:");

        javax.swing.GroupLayout panelProductStoreIdLayout = new javax.swing.GroupLayout(panelProductStoreId);
        panelProductStoreId.setLayout(panelProductStoreIdLayout);
        panelProductStoreIdLayout.setHorizontalGroup(
            panelProductStoreIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelProductStoreIdLayout.setVerticalGroup(
            panelProductStoreIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        btnShoppingListItem.setText("Shopping List Items");
        btnShoppingListItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShoppingListItemActionPerformed(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shoppingListIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(facilityNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEntitySync, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProductStoreId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnShoppingListItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnShoppingListItem, facilityNameTextField, panelFacilityId, panelPartyId, panelProductStoreId, shoppingListIdTextField, txtEntitySync});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(shoppingListIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(facilityNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(panelProductStoreId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEntitySync, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnShoppingListItem)))
                .addGap(14, 14, 14))
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnShoppingListItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShoppingListItemActionPerformed
        ControllerOptions controllerOptions = options.getCopy();
        controllerOptions.put("EntityName", "ShoppingListItem");
        controllerOptions.put("shoppingListId", shoppingListIdTextField.getText());
        controllerOptions.put("shoppingList", currentRecord);       
        
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction.createAndShowPanel(controllerOptions);

        //productStoreConfigMenuAction = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Shopping List Item", controllerOptions);
        //productStoreConfigMenuAction.setActionMenuItem(mnuMaintainFrequentOrderItem);
    }//GEN-LAST:event_btnShoppingListItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShoppingListItem;
    private javax.swing.JTextField facilityNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelProductStoreId;
    private javax.swing.JPanel recordList;
    public javax.swing.JTextField shoppingListIdTextField;
    private javax.swing.JTextField txtEntitySync;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ShoppingList();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setshoppingListId("");
            currentRecord.setlistName(OrderMaxUtility.getValidString(facilityNameTextField.getText()) + "(Copy)");
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
        return UtilMisc.toList("shoppingListId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(partyPickerEditPanel.textIdField.getText())) {
            partyPickerEditPanel.textIdField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(facilityNameTextField.getText())) {
            facilityNameTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(facilityModel.getSelectedItem())) {
            facilityModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleShoppingListPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        if (options != null && options.contains("shoppingListId")) {
            options.put("keys", UtilMisc.toMap("shoppingListId", (String) options.get("shoppingListId")));
//            shoppingListId = (String) options.get("shoppingListId");
        }
        LoadGenericValues<ShoppingList> loader = new LoadGenericValues<ShoppingList>();
        List<ShoppingList> list = loader.loadValues(options);
        /*
         Debug.logInfo(" entityName: " + entityName, SimpleShoppingListPanel.class.getName());
         List<ShoppingList> list = new ArrayList<ShoppingList>();//PosTerminalSingleton.getValueList();
         String shoppingListId = null;
         if (options != null && options.contains("shoppingListId")) {
         shoppingListId = (String) options.get("shoppingListId");
         }
         if (UtilValidate.isNotEmpty(entityName)) {
         Delegator delegator = ControllerOptions.getSession().getDelegator();
         // this.setupTable();
         list.clear();
         List<GenericValue> genList = null;
         if (shoppingListId != null) {
         genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, UtilMisc.toMap("shoppingListId", shoppingListId), "shoppingListId", delegator);
         } else {
         genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, UtilMisc.toMap(), "shoppingListId", delegator);
         }

         for (GenericValue genVal : genList) {
         list.add(new ShoppingList(genVal));
         }
         }*/
        /*List<ShoppingList> list = new ArrayList<ShoppingList>();        
        for (GenericValue genVal : genList) {
            list.add(new ShoppingList(genVal));
        }
        Debug.logInfo(" entityName list: " + list.size(), SimpleShoppingListPanel.class.getName());*/
        listSelectionModel.setDataList(list);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new ShoppingList(val);
        setDialogField();
    }
}
