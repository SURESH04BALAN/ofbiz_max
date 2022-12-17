/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTable;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.party.party.PartyWorker;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.pos.generic.*;
import static org.ofbiz.pos.report.AbstractReportPanel.module1;

/**
 *
 * @author siranjeev
 */
public class InventoryReportPanel extends AbstractReportPanel {

    static public String name = "Inventory Report";
    private GenericValueComboBox supplierIdCombo = null;
    boolean isSupplierLoaded = false;
    private org.ofbiz.ordermax.utility.GenericValueComboBox facilityIdCombo = null;

    /**
     * Creates new form InventoryReportPanel
     */
    public InventoryReportPanel() {
        initComponents();

        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, session.getDelegator());
        facilityIdCombo = new org.ofbiz.ordermax.utility.GenericValueComboBox(comboFacilityId, genFacilityList, "Facility", "facilityId", "facilityName");
        if (facilityIdCombo != null) {
            facilityIdCombo.setSelectedItemId((String) session.getAttribute("facilityId"));
        }
    }

    @Override
    public String getName() {
        return name;
    }
    List<ReportData> invoiceListItems = null;
    public static final String module = InventoryReportPanel.class.getName();

    private class ReportData {

        BigDecimal availableToPromise = null;
        BigDecimal quantityOnHandTotal = null;
        BigDecimal price = null;
        BigDecimal totalPrice = null;
        String available;
        String productId;
        String productName;
        String supplierProductId;
        String supplierPartyId;
        BigDecimal supplierLastPrice = null;
        String supplierPartyDesc;

        ReportData() {
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
    protected PosScreen mpos = null;
    protected PosTransaction mtrans = null;
    List<GenericValue> supplierList = null;

    protected void loadSuppliers() {
        if (isSupplierLoaded == false) {
            supplierList = ReportHelper.getSupplierList(ControllerOptions.getSession().getDelegator());
            supplierIdCombo = new GenericValueComboBox(jComboBox1, supplierList, "Party", "partyId", "description", true);
            isSupplierLoaded = true;
        }
    }

    public GenericValue getSupplierValue(String partyId) {
        GenericValue val = null;
        for (GenericValue supVal : supplierList) {
            if (supVal.getString("partyId").equals(partyId)) {
                return supVal;
            }
        }
        return val;
    }

    public static GenericValue findPartyLatestTelecomNumber(String partyId, Delegator delegator) {
        return PartyWorker.findPartyLatestTelecomNumber(partyId, delegator);
    }
    /*
     public void runReport(PosScreen  PosTransaction m_trans) {
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

    @Override
    public javax.swing.JPanel runReport() {
        

        try {
            
            mtrans = PosTransaction.getCurrentTx(session);
            if (!mtrans.isOpen()) {
                OrderMaxOptionPane.showMessageDialog(null, "dialog/error/terminalclosed");
                return this;
            }

            dataArray.clear();

            loadSuppliers();
            if (invoiceListItems == null) {

                final ClassLoader cl = mtrans.getSession().getClassLoader();
                Thread.currentThread().setContextClassLoader(cl);

                invoiceListItems = FastList.newInstance();
                //        String productId = null;
                Map<String, Object> svcRes = null;
                boolean beganTransaction = false;
                try {

                    beganTransaction = TransactionUtil.begin();

                    LocalDispatcher dispatcher = mtrans.getSession().getDispatcher();
                    //            String productId = "X1500";
                    //String productStoreId = ControllerOptions.getSession().getProductStore().getproductStoreId();
                    List<GenericValue> listGenvalues = new ArrayList<GenericValue>();

                    String facilityId = facilityIdCombo.getSelectedItemId();
                    for (ReportParameterSelectionInterface genPanel : filterList) {
                        if (genPanel instanceof ProductPickerEditPanel && UtilValidate.isNotEmpty(genPanel.getEntityValue())) {
                            GenericValue product = ControllerOptions.getSession().getDelegator().findOne("Product", UtilMisc.toMap("productId", genPanel.getEntityValue()), false);
                            listGenvalues.add(product);
                            break;
                        } else if ("facilityId".equals(genPanel.getEntityId())) {
                            facilityIdCombo.setSelectedItemId((String)genPanel.getEntityValue());
                        } else if ("partyId".equals(genPanel.getEntityId())) {
                            supplierIdCombo.setSelectedItemId((String)genPanel.getEntityValue());
                        }
                    }

                    if (listGenvalues.isEmpty()) {
                        //try {
                        List<GenericValue> productList = ReportHelper.getGenericValueLists("Product", ControllerOptions.getSession().getDelegator());
                        //GenericValue productStore = ControllerOptions.getSession().getDelegator().findOne("ProductStore", UtilMisc.toMap("productStoreId", productStoreId), false);
                        Iterator<GenericValue> it = productList.iterator();
                        while (it.hasNext()) {
                            GenericValue product = it.next();
                            String isVirtual = product.getString("isVirtual");
                            if (UtilValidate.isNotEmpty(isVirtual) && "Y".equals(isVirtual)) {
                                continue;
                            } else {
                                listGenvalues.add(product);
                            }
                        }
                    }

                    for (GenericValue product : listGenvalues) {
                    //String productId = "LP5654";
                        //GenericValue product = mtrans.getSession().getDelegator().findOne("Product", UtilMisc.toMap("productId", productId), false);

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

                            GenericValue party = mtrans.getSession().getDelegator().findOne("PartyGroup", UtilMisc.toMap("partyId", pojo.supplierPartyId), false);
                            Debug.logInfo(pojo.supplierPartyId, module);
                            if (party != null) {
                                pojo.supplierPartyDesc = party.getString("groupName");
                            }
                        }

                        invoiceListItems.add(pojo);
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
                        TransactionUtil.rollback();
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
            //        boolean needCompare = qty.isEmpty() == false;
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

        return this;
    }

    public String GetSelectedSupplierId() {
        return supplierIdCombo.getSelectedItemId();
    }//.getSelectedItem().toString();

    public boolean isAllSelected() {
        return supplierIdCombo.getAllSelected();
    }

    public String GetComperator() {
        return comboComperator.getSelectedItem().toString().trim();
    }//.getSelectedItem().toString();

    public String GetQuantity() {
        return editQty.getText();
    }//.getSelectedItem().toString();
    protected List<Map<String, Object>> dataArray = new ArrayList<Map<String, Object>>();

    protected void addRowToTable(String productId, String productName, BigDecimal quantity, BigDecimal price, BigDecimal supplierLastPrice, String supplierPartyDesc) {
        Map<String, Object> line = new HashMap<String, Object>();

        line.put("prodCode", productId);
        line.put("Product", productName);
        line.put("Qty", quantity);
        line.put("UnitPrice", price);
        line.put("PurchasePrice", supplierLastPrice);
        if (UtilValidate.isNotEmpty(quantity) && UtilValidate.isNotEmpty(price)) {
            line.put("TotalPrice", quantity.multiply(price));
        } else {
            line.put("TotalPrice", BigDecimal.ZERO);
        }
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
        btnFind = new javax.swing.JButton();
        lblSupplierName = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        lblQty = new javax.swing.JLabel();
        comboComperator = new javax.swing.JComboBox();
        editQty = new javax.swing.JTextField();
        lblQty1 = new javax.swing.JLabel();
        comboFacilityId = new javax.swing.JComboBox();
        btnReload = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(680, 120));

        btnFind.setText("Run");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        lblSupplierName.setText("Supplier Name:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblQty.setText("Quantity:");

        comboComperator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "=", "<=", ">=", "<>" }));

        lblQty1.setText("Facility Id:");

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSupplierName)
                    .addComponent(lblQty)
                    .addComponent(lblQty1))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(comboComperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editQty))
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReload))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(comboFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSupplierName)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblQty)
                            .addComponent(comboComperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFind)
                        .addComponent(btnReload)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQty1)
                    .addComponent(comboFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(153, 255, 204));
        jPanel5.setPreferredSize(new java.awt.Dimension(680, 450));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 2, 2));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1);

        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        if (mtrans != null) {
            runReport();
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        if (mtrans != null) {
            invoiceListItems = null;
            runReport();
        }

    }//GEN-LAST:event_btnReloadActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox comboComperator;
    private javax.swing.JComboBox comboFacilityId;
    private javax.swing.JTextField editQty;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblQty;
    private javax.swing.JLabel lblQty1;
    private javax.swing.JLabel lblSupplierName;
    // End of variables declaration//GEN-END:variables

    public JTable getjTable1() {
        return jTable1;
    }

    @Override
    public String identifier() {
        return getName();
    }

    @Override
    public JPanel getSelectionPanel() {
        ReportParameterSelectionPanel panelFilter = new ReportParameterSelectionPanel();
        filterList.clear();
        GridBagLayout layout = new GridBagLayout();
        panelFilter.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        ControllerOptions controllerOptions = new ControllerOptions();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 0, 0);  //top padding   
        gbc.weightx = 1;
        controllerOptions.put("endDateEnabled", false);
        int idx = 0;
        int idy = 0;
        JPanel panel = org.ofbiz.ordermax.report.ReportBaseMain.AddProductIdSelection(filterList, controllerOptions, "Product Id:");
        org.ofbiz.ordermax.report.ReportBaseMain.addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = org.ofbiz.ordermax.report.ReportBaseMain.AddPartyIdSelection(filterList, controllerOptions, "Supplier Id:");
        org.ofbiz.ordermax.report.ReportBaseMain.addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "facilityId", FacilitySingleton.getValueList(), null, null);
        org.ofbiz.ordermax.report.ReportBaseMain.addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, "Facility Id:");

        return panelFilter;

    }

}
