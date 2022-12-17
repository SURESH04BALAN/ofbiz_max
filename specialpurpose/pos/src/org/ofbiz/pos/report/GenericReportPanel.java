/*-
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ReadFilesFromFolder;
import org.ofbiz.party.party.PartyWorker;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.pos.generic.*;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class GenericReportPanel extends javax.swing.JPanel {

    static public String Name = "Generic Report";
    final org.ofbiz.guiapp.xui.XuiSession session = XuiContainer.getSession();
    private GenericValueComboBox supplierIdCombo = null;
    boolean isSupplierLoaded = false;
    private org.ofbiz.ordermax.utility.GenericValueComboBox facilityIdCombo = null;
    GenericValueTablePanel tablePanel = null;
    List<GenericComboSelectionPanel> filterList = new ArrayList<GenericComboSelectionPanel>();

    /**
     * Creates new form InventoryReportPanel
     */
    public GenericReportPanel() {
        initComponents();

        List<String> list = ReadFilesFromFolder.listFilesForFolder(ReadFilesFromFolder.folder);
        for (String str : list) {
            comboEntityName.addItem(str);
        }

        filterList.clear();
        GenericComboSelectionPanel genPanel = new GenericComboSelectionPanel("StatusItem", "statusId", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);

        genPanel = new GenericComboSelectionPanel("Product", "productId", "internalName", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);

        genPanel = new GenericComboSelectionPanel("OrderType", "orderTypeId", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);

        tablePanel = new GenericValueTablePanel(session, comboEntityName.getSelectedItem().toString());
        OrderMaxUtility.addAPanelToPanel(tablePanel, jPanel5);
    }

    @Override
    public String getName() {
        return Name;
    }
    public static final String module = GenericReportPanel.class.getName();

    public void setFacilityId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setFacilityId(String facilityId) {
        if (facilityIdCombo != null) {
            facilityIdCombo.setSelectedItemId(facilityId);
        }
    }
    String[][] reportQueryIdColumnName = {
        {"prodCode", "Product Code", "java.lang.String"},
        {"Product", "Product", "java.lang.String"},
        {"Qty", "Quantity", "java.math.BigDecimal"},
        {"UnitPrice", "Selling Price", "java.math.BigDecimal"},
        {"PurchasePrice", "Purchase Price", "java.math.BigDecimal"},
        {"TotalPrice", "Total Price", "java.math.BigDecimal"},
        {"SupplierName", "Supplier Name", "java.lang.String"
        }};
    protected PosTransaction mtrans = null;
    List<GenericValue> supplierList = null;

    public static GenericValue findPartyLatestTelecomNumber(String partyId, Delegator delegator) {
        return PartyWorker.findPartyLatestTelecomNumber(partyId, delegator);
    }
    /*
     public void runReport(PosScreen m_pos, PosTransaction m_trans) {
     try {
     mpos = m_pos;
     mtrans = m_trans;

     dataArray.clear();
     loadSuppliers();
     if (invoiceListItems == null) {


     final ClassLoader cl = this.getClassLoader(m_pos);
     Thread.currentThread().setContextClassLoader(cl);

     invoiceListItems = FastList.newInstance();
     //        String productId = null;
     Map<String, Object> svcRes = null;
     boolean beganTransaction = false;
     try {


     beganTransaction = TransactionUtil.begin();

     LocalDispatcher dispatcher = m_pos.getSession().getDispatcher();
     //            String productId = "X1500";
     //					String productStoreId = "9100";
     String facilityId = facilityIdCombo.getSelectedItemId();

     try {
     List<GenericValue> productList = ReportHelper.getGenericValueLists("Product", m_pos.getSession().getDelegator());
     //					GenericValue productStore = m_pos.getSession().getDelegator().findOne("ProductStore", UtilMisc.toMap("productStoreId", productStoreId), false);

     Iterator<GenericValue> it = productList.iterator();
     while (it.hasNext()) {
     GenericValue product = it.next();
     //				GenericValue product = pos.getSession().getDelegator().findOne("Product", UtilMisc.toMap("productId", productId), false);

     // Get ATP for the product
     Map<String, Object> getProductInventoryAvailableResult = null;
     try {
     Debug.logInfo("facilityId: " + facilityId, module);
     getProductInventoryAvailableResult = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", product.getString("productId"), "facilityId", facilityId));
     } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     Debug.logError(e, module);
     throw new Exception(e);
     }
     BigDecimal availableToPromise = (BigDecimal) getProductInventoryAvailableResult.get("availableToPromiseTotal");
     BigDecimal quantityOnHandTotal = (BigDecimal) getProductInventoryAvailableResult.get("quantityOnHandTotal");
     String available = (String) getProductInventoryAvailableResult.get("available");


     ReportData pojo = new ReportData();
     pojo.productId = product.getString("productId");
     pojo.productName = product.getString("internalName");
     pojo.quantityOnHandTotal = quantityOnHandTotal;
     pojo.availableToPromise = availableToPromise;
     pojo.available = available;

     String msg = "pojo.productId =" + product.getString("productId")
     + " pojo.productName =" + product.getString("internalName")
     + " pojo.quantityOnHandTotal =" + quantityOnHandTotal
     + " pojo.availableToPromise =" + availableToPromise
     + " pojo.available = " + available;
     Debug.logInfo(msg, module);

     List<GenericValue> inventoryList = product.getRelated("ProductPrice");
     List<GenericValue> listOrdered = EntityUtil.orderBy(inventoryList, UtilMisc.toList("createdStamp DESC"));
     GenericValue productPrice = null;
     if (listOrdered.size() > 0) {
     productPrice = listOrdered.get(0);
     pojo.price = productPrice.getBigDecimal("price");
     pojo.totalPrice = pojo.availableToPromise.multiply(pojo.price);
     }

     inventoryList = product.getRelated("SupplierProduct");
     listOrdered = EntityUtil.orderBy(inventoryList, UtilMisc.toList("createdStamp DESC"));
     GenericValue supplierProduct = null;
     if (listOrdered.size() > 0) {
     supplierProduct = listOrdered.get(0);
     pojo.supplierProductId = supplierProduct.getString("supplierProductId");
     pojo.supplierPartyId = supplierProduct.getString("partyId");
     pojo.supplierLastPrice = supplierProduct.getBigDecimal("lastPrice");
     }

     if (pojo.supplierPartyId != null) {

     GenericValue party = m_pos.getSession().getDelegator().findOne("PartyGroup", UtilMisc.toMap("partyId", pojo.supplierPartyId), false);
     Debug.logInfo(pojo.supplierPartyId, module);
     if (party != null) {
     pojo.supplierPartyDesc = party.getString("groupName");
     }
     }

     invoiceListItems.add(pojo);
     }

     } catch (GenericEntityException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }

     } catch (GenericServiceException e) {
     Debug.logError(e, module);
     //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());            
     } catch (GenericTransactionException e) {
     Debug.logError(e, module);
     try {
     TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
     } catch (GenericTransactionException e2) {
     Debug.logError(e2, "Unable to rollback transaction", module);
     OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
     }
     OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
     } finally {
     try {
     TransactionUtil.commit(beganTransaction);
     } catch (GenericTransactionException e) {
     Debug.logError(e, "Unable to commit transaction", module);
     OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
     }
     }
     Debug.logInfo("========== modifyProduct ", module);
     }


     String supplierId = supplierIdCombo.getSelectedItemId();//.getSelectedItem().toString();
     String comparator = comboComperator.getSelectedItem().toString().trim();
     String qty = editQty.getText();//.getSelectedItem().toString();

     Integer compQty = 0;
     boolean needCompare = qty.isEmpty() == false;
     if (!qty.isEmpty()) {
     compQty = new Integer(qty);
     }

     for (ReportData entry : invoiceListItems) {
     if (supplierId.equals("All") || entry.supplierPartyId.equals(supplierId)) {

     if (qty.isEmpty()) {

     addRowToTable(entry.productId, entry.productName, entry.availableToPromise, entry.price, entry.supplierLastPrice, entry.supplierPartyDesc);
     } else {
     if (isValidQty(comparator, compQty, entry.quantityOnHandTotal.intValue())) {
     addRowToTable(entry.productId, entry.productName, entry.availableToPromise, entry.price, entry.supplierLastPrice, entry.supplierPartyDesc);

     }
     }
     }
     }
     } catch (Exception ex) {
     Debug.logError(ex, "Unable to repaint the Journal", module);
     ex.printStackTrace();
     }

     MapValueTableDataModel model = new org.ofbiz.pos.generic.MapValueTableDataModel(reportQueryIdColumnName);
     jTable1.setModel(model);
     model.addMapRows(dataArray);

     return;
     }
     */

    public void runReport(PosTransaction m_trans) {
        if (tablePanel != null) {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            for (GenericComboSelectionPanel genPanel : filterList) {
                if (genPanel.isAllSelected() == false) {
                    whereClauseMap.put(genPanel.getSelId(), genPanel.getSelectedItemId());
                }
            }

            tablePanel.setGenericValueTableName(comboEntityName.getSelectedItem().toString(), whereClauseMap);
        }
    }

    public String GetSelectedSupplierId() {
        return supplierIdCombo.getSelectedItemId();
    }//.getSelectedItem().toString();

    public boolean isAllSelected() {
        return supplierIdCombo.getAllSelected();
    }
    /*
     public String GetComperator() {
     return comboComperator.getSelectedItem().toString().trim();
     }//.getSelectedItem().toString();

     public String GetQuantity() {
     return editQty.getText();
     }//.getSelectedItem().toString();
     * */
    protected List<Map<String, Object>> dataArray = new ArrayList<Map<String, Object>>();

    protected void addRowToTable(String productId, String productName, BigDecimal quantity, BigDecimal price, BigDecimal supplierLastPrice, String supplierPartyDesc) {
        Map<String, Object> line = new HashMap<String, Object>();

        line.put("prodCode", productId);
        line.put("Product", productName);
        line.put("Qty", quantity);
        line.put("UnitPrice", price);
        line.put("PurchasePrice", supplierLastPrice);
        line.put("TotalPrice", quantity.multiply(price));
        line.put("SupplierName", supplierPartyDesc);

        dataArray.add(line);
    }

    protected void appendEmpty() {
        addRowToTable("", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "");
    }
    protected static String equals = "=";
    protected static String greaterThanequal = ">=";
    protected static String lessThanequal = "<=";
    protected static String notequal = "<>";

    protected boolean isValidQty(String comparator, Integer compQty, Integer qty) {
        boolean result = false;
        Debug.logInfo("comparator : " + comparator + " compQty: " + compQty.toString() + " qty: " + qty.toString(), module);
        if (comparator.equals(equals)) {
            return qty.intValue() == compQty.intValue();
        } else if (comparator.equals(greaterThanequal)) {
            return qty.intValue() >= compQty.intValue();
        } else if (comparator.equals(lessThanequal)) {
            return qty.intValue() <= compQty.intValue();
        } else if (comparator.equals(notequal)) {
            return qty.intValue() != compQty.intValue();
        }
        return result;
    }

    protected ClassLoader getClassLoader(PosScreen m_pos) {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = m_pos.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    public void printReport(PosTransaction m_trans) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        lblSupplierName = new javax.swing.JLabel();
        btnReload = new javax.swing.JButton();
        comboEntityName = new javax.swing.JComboBox();
        panelFilter = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(680, 120));
        jPanel4.setLayout(new java.awt.BorderLayout());

        btnFind.setText("Run");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        lblSupplierName.setText("Report Object:");

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        comboEntityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEntityNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSupplierName)
                .addGap(32, 32, 32)
                .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFind)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReload)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSupplierName)
                        .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFind)
                        .addComponent(btnReload)))
                .addContainerGap())
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelFilter.setLayout(new java.awt.GridLayout(0, 2));
        jPanel4.add(panelFilter, java.awt.BorderLayout.CENTER);

        add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(153, 255, 204));
        jPanel5.setPreferredSize(new java.awt.Dimension(680, 450));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));
        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
//        if (mpos != null && mtrans != null) {
        runReport(mtrans);
//        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
//        if (mpos != null && mtrans != null) {
        runReport(mtrans);
//        }
    }//GEN-LAST:event_btnReloadActionPerformed

    private void comboEntityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEntityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEntityNameActionPerformed
    public Collection getBeanCollection(String entityName) throws Exception {
        Delegator delegator = session.getDelegator();
        List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);

        /*    
         Collection<CountryCode> objectList = new ArrayList<CountryCode>();
         for(GenericValue genVal : genList){
         objectList.add(new CountryCode(genVal));
         }
         return objectList; */
        Collection result = null;
        try {
            String classname = "org.ofbiz.ordermax.entity." + entityName;
            Debug.logInfo("classname: " + classname, module);
            String amethodname = "getObjectList";
            Class myTarget = Class.forName(classname);
            Object myinstance = myTarget.newInstance();
            Method myMethod;
            myMethod = myTarget.getDeclaredMethod(amethodname, List.class);
            if (myMethod != null) {
                result = (Collection) myMethod.invoke(myinstance, genList);
                if (result != null) {
                    Debug.logInfo("got valid result: " + classname, module);
                    if (result.size() > 0) {
                        Debug.logInfo("count got valid result of  " + result.size(), module);
                    }
                }
            } else {
                Debug.logInfo("method is not found amethodname: " + classname, module);
            }

        } catch (final ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (final SecurityException e) {
            throw new Exception(e.getMessage());
        } catch (final NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (InstantiationException ex) {
            Logger.getLogger(GenericReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox comboEntityName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JPanel panelFilter;
    // End of variables declaration//GEN-END:variables
//    public JTable getjTable1() {
//        return jTable1;
//    }
}
