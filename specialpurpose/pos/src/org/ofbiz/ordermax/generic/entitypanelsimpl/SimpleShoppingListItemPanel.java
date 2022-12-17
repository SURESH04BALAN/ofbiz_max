/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import org.ofbiz.ordermax.generic.GenericSaveInterface;
import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadOrderHeaderRolesOrderWorker;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ActionTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ShoppingList;
import org.ofbiz.ordermax.entity.ShoppingListItem;
import org.ofbiz.ordermax.generic.EntityPersistanceFactory;
import org.ofbiz.ordermax.generic.LoadGenericValues;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author BBS Auctions
 */
public class SimpleShoppingListItemPanel extends javax.swing.JPanel implements GenericSaveInterface {

    //private JGenericListBoxSelectionModel<ShoppingListItem> listSelectionModel = null;
    //public JGenericComboBoxSelectionModel<FacilityType> facilityModel = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;
    ProductPickerEditPanel panelProductIdPicker;
    public static final String module = SimpleShoppingListItemPanel.class.getName();
    private final JTextField txtBillingAccountIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;
    public GenericTableModelPanel<ShoppingListItem, ShoppingListItemTableModel> tablePanel = null;
    ListAdapterListModel<ShoppingListItem> listSelectionModel = new ListAdapterListModel<ShoppingListItem>();
    String shoppingListId = null;
    ShoppingList shoppingList = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleShoppingListItemPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ShoppingListItem> list = new ArrayList<ShoppingListItem>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        if (options.contains("shoppingListId")) {
            shoppingListId = (String) options.get("shoppingListId");
        }

        if (options.contains("shoppingList")) {
            shoppingList = (ShoppingList) options.get("shoppingList");
        }

        facilityIdTextField.setText(shoppingListId);
        /*        listSelectionModel = new JGenericListBoxSelectionModel<ShoppingListItem>(list);
         recordList.setLayout(new BorderLayout());
         loadList();
         JScrollPane scrollPane = new JScrollPane();
         scrollPane.setBounds(10, 11, 580, 200);

         scrollPane.setViewportView(listSelectionModel.jlistBox);
         recordList.setLayout(new BorderLayout());
         recordList.add(BorderLayout.CENTER, scrollPane);
         */
        tablePanel = new GenericTableModelPanel<ShoppingListItem, ShoppingListItemTableModel>(new ShoppingListItemTableModel());
        itemList.setLayout(new BorderLayout());
        itemList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        loadList(options);
        tablePanel.setListModel(listSelectionModel);

        ComponentBorder.loweredBevelBorder(itemList, "List");
        ComponentBorder.loweredBevelBorder(jPanel1, "Detail");

        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) tablePanel.jTable.getSelectionModel();;
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
        //facilityModel = new JGenericComboBoxSelectionModel<FacilityType>(FacilityTypeSingleton.getValueList());
        panelProductIdPicker = new ProductPickerEditPanel(options, txtProductId);

        facilityIdTextField.getDocument().addDocumentListener(dirty);
        txtQuantity.getDocument().addDocumentListener(dirty);
        txtModifiedPrice.getDocument().addDocumentListener(dirty);
        panelProductIdPicker.textIdField.addActionListener(dirty);

        newRecord();

    }

    ShoppingListItem currentRecord = null;

    public void setSelected(int index) {
        clearDialogFields();
        if (index < tablePanel.listModel.getSize()) {
            currentRecord = (ShoppingListItem) tablePanel.listModel.getElementAt(index);
            setDialogField();
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        facilityIdTextField.setText(shoppingListId);
        txtQuantity.setText("");
        panelProductIdPicker.textIdField.setText("");
        txtModifiedPrice.setText("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            currentRecord.setshoppingListId(facilityIdTextField.getText());
            if (UtilValidate.isNotEmpty(txtQuantity.getText())) {
                currentRecord.setquantity(new BigDecimal(txtQuantity.getText()));
            }
            if (UtilValidate.isNotEmpty(txtModifiedPrice.getText())) {
                currentRecord.setmodifiedPrice(new BigDecimal(txtModifiedPrice.getText()));
            }
            if (UtilValidate.isNotEmpty(panelProductIdPicker.textIdField.getText())) {
                currentRecord.setproductId(panelProductIdPicker.textIdField.getText());
            }

            /*
             if (currentRecord.getreservStart() != null) {
             txtReservStart.setText(currentRecord.getreservStart().toString());
             }
             if (currentRecord.getreservLength() != null) {
             txtReservLength.setText(currentRecord.getreservLength().toString());
             }
             if (currentRecord.getreservPersons() != null) {
             txtReservPersons.setText(currentRecord.getreservPersons().toString());
             }
             if (currentRecord.getquantityPurchased() != null) {
             txtQuantityPurchased.setText(currentRecord.getquantityPurchased().toString());
             }*/
            txtConfigId.setText(currentRecord.getconfigId());

        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            facilityIdTextField.setText(currentRecord.getshoppingListId());
            if (currentRecord.getquantity() != null) {
                txtQuantity.setText(currentRecord.getquantity().toString());
            }
            if (currentRecord.getmodifiedPrice() != null) {
                txtModifiedPrice.setText(currentRecord.getmodifiedPrice().toString());
            }
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getproductId())) {
                    panelProductIdPicker.textIdField.setText(currentRecord.getproductId());
                    // facilityModel.setSelectedItem(FacilityTypeSingleton.getFacilityType(currentRecord.getfacilityTypeId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleShoppingListItemPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (currentRecord.getreservStart() != null) {
                txtReservStart.setText(currentRecord.getreservStart().toString());
            }
            if (currentRecord.getreservLength() != null) {
                txtReservLength.setText(currentRecord.getreservLength().toString());
            }
            if (currentRecord.getreservPersons() != null) {
                txtReservPersons.setText(currentRecord.getreservPersons().toString());
            }
            if (currentRecord.getquantityPurchased() != null) {
                txtQuantityPurchased.setText(currentRecord.getquantityPurchased().toString());
            }
            txtConfigId.setText(currentRecord.getconfigId());

            dirty.setDirty(false);
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
        itemList = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        facilityIdTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        txtProductId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelOpenDate1 = new javax.swing.JPanel();
        txtModifiedPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtReservStart = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtReservLength = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtReservPersons = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtQuantityPurchased = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtConfigId = new javax.swing.JTextField();
        btnLoadOrderdItems = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout itemListLayout = new javax.swing.GroupLayout(itemList);
        itemList.setLayout(itemListLayout);
        itemListLayout.setHorizontalGroup(
            itemListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );
        itemListLayout.setVerticalGroup(
            itemListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );

        jPanel2.add(itemList, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Shopping List Id:");

        facilityIdTextField.setEnabled(false);

        jLabel2.setText("Product Id:");

        jLabel3.setText("Quantity:");

        javax.swing.GroupLayout txtProductIdLayout = new javax.swing.GroupLayout(txtProductId);
        txtProductId.setLayout(txtProductIdLayout);
        txtProductIdLayout.setHorizontalGroup(
            txtProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        txtProductIdLayout.setVerticalGroup(
            txtProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel10.setText("Modified Price:");

        javax.swing.GroupLayout panelOpenDate1Layout = new javax.swing.GroupLayout(panelOpenDate1);
        panelOpenDate1.setLayout(panelOpenDate1Layout);
        panelOpenDate1Layout.setHorizontalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtModifiedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate1Layout.setVerticalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtModifiedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Reserv Start:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Reserv Length:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Reserv Persons:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Quantity Purchased:");

        jLabel11.setText("Config Id:");

        btnLoadOrderdItems.setText("Load All Ordered Items");
        btnLoadOrderdItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadOrderdItemsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtQuantity)
                        .addComponent(facilityIdTextField)
                        .addComponent(txtProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtConfigId, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLoadOrderdItems, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtReservStart, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReservLength, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReservPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantityPurchased, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLoadOrderdItems, jLabel4, jLabel5, jLabel6, jLabel7});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(facilityIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtReservStart, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtReservLength, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtReservPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtQuantityPurchased, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtConfigId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadOrderdItems)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadOrderdItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadOrderdItemsActionPerformed
        if (shoppingList != null) {
            Map<String, Object> wClauseMap = new HashMap<String, Object>();
            wClauseMap.put("partyId", shoppingList.getpartyId());
            List<GenericValue> orders = LoadOrderHeaderRolesOrderWorker.getOrderDetails(wClauseMap, ControllerOptions.getSession().getDelegator());
            try {
                for (GenericValue order : orders) {
                    wClauseMap.clear();
                    wClauseMap.put("shoppingListId", shoppingList.getshoppingListId());
                    wClauseMap.put("shoppingListTypeId", shoppingList.getshoppingListTypeId());
                    wClauseMap.put("partyId", shoppingList.getpartyId());
                    wClauseMap.put("orderId", order.getString("orderId"));
                    EntityPersistanceFactory.GenericEntitySave.runEntityService("makeShoppingListFromOrder", wClauseMap);
                }
                loadList(options);
            } catch (Exception ex) {
                Logger.getLogger(SimpleShoppingListItemPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnLoadOrderdItemsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadOrderdItems;
    public javax.swing.JTextField facilityIdTextField;
    private javax.swing.JPanel itemList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JTextField txtConfigId;
    private javax.swing.JTextField txtModifiedPrice;
    private javax.swing.JPanel txtProductId;
    private javax.swing.JTextField txtQuantity;
    public javax.swing.JTextField txtQuantityPurchased;
    public javax.swing.JTextField txtReservLength;
    public javax.swing.JTextField txtReservPersons;
    public javax.swing.JTextField txtReservStart;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ShoppingListItem();
        currentRecord.setshoppingListId(shoppingListId);
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
//            currentRecord.setfacilityId("");
//            currentRecord.setfacilityName(OrderMaxUtility.getValidString(txtQuantity.getText()) + "(Copy)");
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
        return UtilMisc.toList("shoppingListId", "shoppingListItemSeqId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(facilityIdTextField.getText())) {
            facilityIdTextField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(txtQuantity.getText())) {
            txtQuantity.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(panelProductIdPicker.textIdField.getText())) {
            panelProductIdPicker.textIdField.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleShoppingListItemPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Shopping List Item",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        if (options != null && options.contains("shoppingListId")) {
            options.put("keys", UtilMisc.toMap("shoppingListId", (String) options.get("shoppingListId")));
        }
        
        List<ShoppingListItem> list = new LoadGenericValues<ShoppingListItem>().loadValues(options);
        //List<GenericValue> genList = loader.loadValues(options);

        /*List<ShoppingListItem> list = new ArrayList<ShoppingListItem>();
        for (GenericValue genVal : genList) {
            list.add(new ShoppingListItem(genVal));
        }*/
        Debug.logInfo(" entityName list: " + list.size(), SimpleShoppingListPanel.class.getName());
        listSelectionModel.setList(list);

        /* List<ShoppingListItem> list = new ArrayList<ShoppingListItem>();//PosTerminalSingleton.getValueList();
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
         list.add(new ShoppingListItem(genVal));
         }
         }
         listSelectionModel.setList(list);
         */
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new ShoppingListItem(val);
        setDialogField();
    }

    final public void setupEditOrderTable() {

        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);

        for (int i = 0; i < ShoppingListItemTableModel.Columns.values().length; i++) {
            ShoppingListItemTableModel.Columns[] columns = ShoppingListItemTableModel.Columns.values();
            ShoppingListItemTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            //TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (ShoppingListItemTableModel.Columns.PRODUCT_ID.toString().equals(column.toString())) {
                Debug.logError("col name: swwt" + column.toString(), "module");
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);
                Debug.logError("col name: swwt end" + column.toString(), "module");
            } else if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }

        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    static public class ShoppingListItemTableModel extends ActionTableModel<ShoppingListItem> {

        public enum Columns {

            SHOPPING_LIST_ID(0, 100, String.class, "SHOPPING LIST ID", true),
            SHOPPING_LIST_ITEM_SEQ_ID(1, 75, String.class, "SHOPPING LIST ITEM SEQ ID", false),
            PRODUCT_ID(2, 300, String.class, "PRODUCT ID", false),
            MODIFIED_PRICE(3, 100, BigDecimal.class, "MODIFIED PRICE", true),
            QUANTITY(4, 100, BigDecimal.class, "QUANTITY", true),
            RESERV_START(5, 100, java.sql.Timestamp.class, "RESERV START", true),
            RESERV_LENGTH(6, 100, BigDecimal.class, "RESERV LENGTH", false),
            RESERV_PERSONS(7, 100, String.class, "RESERV PERSONS", false),
            QUANTITY_PURCHASED(8, 100, String.class, "QUANTITY PURCHASED", true),
            CONFIG_ID(9, 100, String.class, "CONFIG ID", true);

            private int columnIndex;
            private int columnWidth;

            public String getHeaderString() {
                return headerString;
            }

            public void setHeaderString(String headerString) {
                this.headerString = headerString;
            }

            public Class getClassName() {
                return className;
            }

            public void setClassName(Class className) {
                this.className = className;
            }

            public boolean isIsEditable() {
                return isEditable;
            }

            public void setIsEditable(boolean isEditable) {
                this.isEditable = isEditable;
            }
            private String headerString;
            private Class className;
            private boolean isEditable;

            Columns(int index, int width, Class className, String header, boolean edit) {
                columnIndex = index;
                columnWidth = width;
                headerString = header;
                this.className = className;
                isEditable = edit;
            }

            public int getColumnIndex() {
                return columnIndex;
            }

            public int getColumnWidth() {
                return columnWidth;
            }
        }

        public ShoppingListItemTableModel() {
        }

        public int getRowCount() {
            return listModel.getSize();
        }

        public int getColumnCount() {
            return Columns.values().length;
        }

        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.isEditable;

        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object columnValue = null;
            ShoppingListItem shoppingListItem = listModel.getElementAt(rowIndex);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            switch (column) {
                case SHOPPING_LIST_ID:
                    columnValue = shoppingListItem.getshoppingListId();
                    break;
                case SHOPPING_LIST_ITEM_SEQ_ID:
                    columnValue = shoppingListItem.getshoppingListItemSeqId();
                    break;
                case PRODUCT_ID:
                    if (UtilValidate.isNotEmpty(shoppingListItem.getproductId())) {
                        try {
                            Product product = ProductSingleton.getProduct(shoppingListItem.getproductId());
                            if (product != null) {
                                columnValue = product.getproductName();
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(SimpleShoppingListItemPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        columnValue = null;
                    }
                    break;
                case QUANTITY:
                    columnValue = shoppingListItem.getquantity();
                    break;
                case RESERV_START:
                    columnValue = shoppingListItem.getreservStart();
                    break;
                case RESERV_LENGTH:
                    columnValue = shoppingListItem.getreservLength();
                    break;
                case RESERV_PERSONS:
                    columnValue = shoppingListItem.getreservPersons();
                    break;
                case QUANTITY_PURCHASED:
                    columnValue = shoppingListItem.getquantityPurchased();
                    break;
                case CONFIG_ID:
                    columnValue = shoppingListItem.getconfigId();
                    break;
                case MODIFIED_PRICE:
                    columnValue = shoppingListItem.getmodifiedPrice();
                    break;

                default:
                    break;
            }

            return columnValue;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.className;
        }

        @Override
        public String getColumnName(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.headerString;
        }

    }

}
