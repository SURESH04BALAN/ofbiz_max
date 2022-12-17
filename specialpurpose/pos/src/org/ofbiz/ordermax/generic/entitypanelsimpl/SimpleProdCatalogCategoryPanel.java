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
import org.ofbiz.ordermax.base.components.BasePickerEditPanel;
import org.ofbiz.ordermax.base.components.CategoryPickerEditPanel;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProdCatalogCategory;
import org.ofbiz.ordermax.entity.ProdCatalogCategoryType;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.product.catalog.ProdCatalogSingleton;
import org.ofbiz.ordermax.product.catalog.ProdCatalogCategoryTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProdCatalogCategoryPanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProdCatalogCategory> listSelectionModel = null;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;
    private JGenericComboBoxSelectionModel<ProdCatalog> comboProductCatalog = null;
    private ReportCreatorSelectionInterface categoryPickerPanel = null;
    private JGenericComboBoxSelectionModel<ProdCatalogCategoryType> comboProdCatalogCategoryType = null;

    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProdCatalogCategoryPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProdCatalogCategory> list = new ArrayList<ProdCatalogCategory>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProdCatalogCategory>(list);
        panelHeader.setLayout(new BorderLayout());
        loadList();
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

        ControllerOptions categoryOptions = new ControllerOptions(options);
        try {
            categoryPickerPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.CategoryId, categoryOptions, panelCategoryId);//new CategoryPickerEditPanel(categoryOptions, panelCategoryId);

            comboProdCatalogCategoryType = new JGenericComboBoxSelectionModel<ProdCatalogCategoryType>(panelCatalogCategoryType, ProdCatalogCategoryTypeSingleton.getValueList());
            comboProductCatalog = new JGenericComboBoxSelectionModel<ProdCatalog>(panelProductCatalog, ProdCatalogSingleton.getValueList());

            fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
            thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

            fromDatePanel.txtDate.getDocument().addDocumentListener(dirty);
            thruDatePanel.txtDate.getDocument().addDocumentListener(dirty);
            txtSequenceNum.getDocument().addDocumentListener(dirty);

            comboProductCatalog.jComboBox.addActionListener(dirty);
            comboProdCatalogCategoryType.jComboBox.addActionListener(dirty);
        } catch (Exception ex) {
            Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
        ComponentBorder.loweredBevelBorder(panelHeader, "Prod Catalog Categories");

        newRecord();
    }

    ProdCatalogCategory currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProdCatalogCategory) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            try {
                fromDatePanel.setTimeStamp(currentRecord.getfromDate());
            } catch (Exception ex) {
                //    Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                thruDatePanel.setTimeStamp(currentRecord.getthruDate());
            } catch (Exception ex) {
                //       Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(currentRecord.getprodCatalogId())) {
                    comboProductCatalog.setSelectedItem(ProdCatalogSingleton.getProdCatalog(currentRecord.getprodCatalogId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                categoryPickerPanel.setEntityValue(currentRecord.getproductCategoryId()/*ProductCategorySingleton.getProductCategory(currentRecord.getproductCategoryId()).getProductCategoryId()*/);
            } catch (Exception ex) {
                Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            Debug.logInfo("currentRecord.getprodCatalogCategoryTypeId() " + currentRecord.getprodCatalogCategoryTypeId(), entityName);
            try {
                if (UtilValidate.isNotEmpty(currentRecord.getprodCatalogCategoryTypeId())) {
                    comboProdCatalogCategoryType.setSelectedItem(ProdCatalogCategoryTypeSingleton.getProdCatalogCategoryType(currentRecord.getprodCatalogCategoryTypeId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (UtilValidate.isNotEmpty(currentRecord.getsequenceNum())) {
                txtSequenceNum.setText(currentRecord.getsequenceNum().toString());
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

        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
        txtSequenceNum.setText("");
        categoryPickerPanel.setEntityValue("");
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            try {
                currentRecord.setfromDate(fromDatePanel.getTimeStamp());
            } catch (Exception ex) {
                Logger.getLogger(SimpleProdCatalogCategoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                currentRecord.setthruDate(thruDatePanel.getTimeStamp());
            } catch (Exception ex) {
                currentRecord.setthruDate(null);
            }

            if (UtilValidate.isNotEmpty(comboProductCatalog.getSelectedItem())) {
                currentRecord.setprodCatalogId(comboProductCatalog.getSelectedItem().getprodCatalogId());
            }
            if (UtilValidate.isNotEmpty(categoryPickerPanel.getEntityValue())) {
                currentRecord.setproductCategoryId((String)categoryPickerPanel.getEntityValue());//.getProductCategoryId());
            }

            if (UtilValidate.isNotEmpty(comboProdCatalogCategoryType.getSelectedItem())) {
                currentRecord.setprodCatalogCategoryTypeId(comboProdCatalogCategoryType.getSelectedItem().getprodCatalogCategoryTypeId());
            }

            if (UtilValidate.isNotEmpty(txtSequenceNum.getText())) {
                currentRecord.setsequenceNum(Long.parseLong(txtSequenceNum.getText()));
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
        jLabel5 = new javax.swing.JLabel();
        txtSequenceNum = new javax.swing.JTextField();
        panelProductCatalog = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelCategoryId = new javax.swing.JPanel();
        Store2 = new javax.swing.JLabel();
        panelCatalogCategoryType = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 250));

        Store.setText("Product Catalog:");

        jLabel3.setText("From Date:");

        jLabel4.setText("Thru Date:");

        jLabel5.setText("Sequence Num:");

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
            .addGap(0, 0, Short.MAX_VALUE)
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

        Store1.setText("Category ID:");

        javax.swing.GroupLayout panelCategoryIdLayout = new javax.swing.GroupLayout(panelCategoryId);
        panelCategoryId.setLayout(panelCategoryIdLayout);
        panelCategoryIdLayout.setHorizontalGroup(
            panelCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelCategoryIdLayout.setVerticalGroup(
            panelCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        Store2.setText("Product Catalog Category Type:");

        javax.swing.GroupLayout panelCatalogCategoryTypeLayout = new javax.swing.GroupLayout(panelCatalogCategoryType);
        panelCatalogCategoryType.setLayout(panelCatalogCategoryTypeLayout);
        panelCatalogCategoryTypeLayout.setHorizontalGroup(
            panelCatalogCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        panelCatalogCategoryTypeLayout.setVerticalGroup(
            panelCatalogCategoryTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProductCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSequenceNum, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCatalogCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store)
                    .addComponent(panelProductCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store1)
                    .addComponent(panelCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store2)
                    .addComponent(panelCatalogCategoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSequenceNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.add(panelDetail, java.awt.BorderLayout.PAGE_END);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JLabel Store2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelCatalogCategoryType;
    private javax.swing.JPanel panelCategoryId;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductCatalog;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JTextField txtSequenceNum;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProdCatalogCategory();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setprodCatalogId("");
            currentRecord.setproductCategoryId("");
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
        return UtilMisc.toList("prodCatalogId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(comboProdCatalogCategoryType.getSelectedItem())) {
            comboProdCatalogCategoryType.jComboBox.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(categoryPickerPanel.getEntityValue())) {
            //categoryPickerPanel.textIdField.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(comboProductCatalog.getSelectedItem())) {
            comboProductCatalog.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleProdCatalogCategoryPanel.class.getName());
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
        List<ProdCatalogCategory> list = new ArrayList<ProdCatalogCategory>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Debug.logInfo(" entityName: " + entityName, SimpleProdCatalogCategoryPanel.class.getName());
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProdCatalogCategory(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new ProdCatalogCategory(val);
        setDialogField();
    }
}
