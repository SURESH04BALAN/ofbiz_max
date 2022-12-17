/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericListBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class SimpleProdCatalogPanel extends javax.swing.JPanel implements GenericSaveInterface {

    final public static String module = SimpleProdCatalogPanel.class.getName();
    private JGenericListBoxSelectionModel<ProdCatalog> listSelectionModel = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
   

    boolean isNew = false;
    boolean isModified = false;

    public SimpleProdCatalogPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProdCatalog> list = new ArrayList<ProdCatalog>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProdCatalog>(list);
        panelHeader.setLayout(new BorderLayout());
        loadList();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelHeader, "List");
        ComponentBorder.loweredBevelBorder(panelDetail, "Detail");

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

        currentRecord = new ProdCatalog();
        setDialogField();

    }

    ProdCatalog currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProdCatalog) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

//    ProdCatalog currentRecord = null;
    public void setDialogField() {
        if (currentRecord != null) {
            prodCatalogId.setText(currentRecord.getprodCatalogId());
            prodCatalogId.setText(currentRecord.getprodCatalogId());
            catalogName.setText(currentRecord.getcatalogName());
            useQuickAdd.setText(currentRecord.getuseQuickAdd());
            styleSheet.setText(currentRecord.getstyleSheet());
            headerLogo.setText(currentRecord.getheaderLogo());
            contentPathPrefix.setText(currentRecord.getcontentPathPrefix());
            templatePathPrefix.setText(currentRecord.gettemplatePathPrefix());
            viewAllowPermReqd.setText(currentRecord.getviewAllowPermReqd());
            purchaseAllowPermReqd.setText(currentRecord.getpurchaseAllowPermReqd());
        }
    }

    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {
        prodCatalogId.setText("");
        prodCatalogId.setText("");
        catalogName.setText("");
        useQuickAdd.setText("");
        styleSheet.setText("");
        headerLogo.setText("");
        contentPathPrefix.setText("");
        templatePathPrefix.setText("");
        viewAllowPermReqd.setText("");
        purchaseAllowPermReqd.setText("");
    }

    public void getDialogFields() {
        if (currentRecord != null) {
            currentRecord.setprodCatalogId(prodCatalogId.getText());
            currentRecord.setprodCatalogId(prodCatalogId.getText());
            currentRecord.setcatalogName(catalogName.getText());
            currentRecord.setuseQuickAdd(useQuickAdd.getText());
            currentRecord.setstyleSheet(styleSheet.getText());
            currentRecord.setheaderLogo(headerLogo.getText());
            currentRecord.setcontentPathPrefix(contentPathPrefix.getText());
            currentRecord.settemplatePathPrefix(templatePathPrefix.getText());
            currentRecord.setviewAllowPermReqd(viewAllowPermReqd.getText());
            currentRecord.setpurchaseAllowPermReqd(purchaseAllowPermReqd.getText());
        }
    }

    public void setProdCatalogList(ListAdapterListModel<ProdCatalog> personListModel, ListSelectionModel selectionModel) {
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
        jLabel1 = new javax.swing.JLabel();
        prodCatalogId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        catalogName = new javax.swing.JTextField();
        useQuickAdd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        styleSheet = new javax.swing.JTextField();
        headerLogo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        contentPathPrefix = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        templatePathPrefix = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        viewAllowPermReqd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        purchaseAllowPermReqd = new javax.swing.JTextField();
        panelHeader = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 205));

        jLabel1.setText("Prod Catalog Id:");

        jLabel3.setText("Catalog Name:");

        jLabel4.setText("Use Quick Add:");

        jLabel5.setText("Style Sheet:");

        jLabel6.setText("Header Logo:");

        jLabel7.setText("Content Path Prefix:");

        jLabel8.setText("Template Path Prefix:");

        jLabel9.setText("View Allow Perm Reqd:");

        jLabel10.setText("Purchase Allow Perm Reqd:");

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prodCatalogId, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(catalogName, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(useQuickAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(styleSheet, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contentPathPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(templatePathPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewAllowPermReqd, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(purchaseAllowPermReqd, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(prodCatalogId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(catalogName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(useQuickAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(styleSheet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(contentPathPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(templatePathPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(viewAllowPermReqd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(purchaseAllowPermReqd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(headerLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(panelDetail, java.awt.BorderLayout.PAGE_END);

        panelHeader.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Catalogs"));
        panelHeader.setPreferredSize(new java.awt.Dimension(489, 200));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField catalogName;
    private javax.swing.JTextField contentPathPrefix;
    private javax.swing.JTextField headerLogo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTextField prodCatalogId;
    private javax.swing.JTextField purchaseAllowPermReqd;
    private javax.swing.JTextField styleSheet;
    private javax.swing.JTextField templatePathPrefix;
    private javax.swing.JTextField useQuickAdd;
    private javax.swing.JTextField viewAllowPermReqd;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProdCatalog();
        setDialogField();
    }
   public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setprodCatalogId("");
            currentRecord.setcatalogName(OrderMaxUtility.getValidString(catalogName.getText()) + "(Copy)");
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
        if (UtilValidate.isEmpty(prodCatalogId.getText())) {
            prodCatalogId.requestFocus();
            result = false;
        } else if (UtilValidate.isEmpty(catalogName.getText())) {
            catalogName.requestFocus();
            result = false;
        } /*else if (UtilValidate.isEmpty(facilityModel.getSelectedItem())) {
            facilityModel.jComboBox.requestFocus();
            result = false;
        }*/
        Debug.logInfo(" isValidValues: " + result, SimplePosTerminalPanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }
    void loadList(){
        loadList(null);
    }
    
    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        List<ProdCatalog> list = new ArrayList<ProdCatalog>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProdCatalog(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }
    
    public JPanel getPanel(){
        return this;
    }

    public void setGenericValue(GenericValue val) {
        currentRecord = new ProdCatalog(val);
        setDialogField();
    }
}
