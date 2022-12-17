/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import java.awt.Component;
import java.awt.Point;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author siranjeev
 */
public class ProductDetailTableForm extends javax.swing.JPanel {

    protected XuiSession session = null;
    public static final String module = ProductDetailTableForm.class.getName();
    protected List<String> departmentListBidingCombo = FastList.newInstance();
    protected List<String> brandListBidingCombo = FastList.newInstance();
    protected List<String> productListBidingCombo = FastList.newInstance();
    protected List<String> facilityListBidingCombo = FastList.newInstance();
    protected DefaultComboBoxModel comboSupplierModel = null;
    protected List<String> supplierListBidingCombo = FastList.newInstance();
    protected boolean isLoading = false;
    static protected List<ProductInventoryPojo> cutDownproductsList = FastList.newInstance();
    protected List<ProductInventoryPojo> productPriceList = FastList.newInstance();

    protected ProductDataTreeLoader m_productsArray = null;
    ProductSelectionTreePanel treePanel = null;
    protected InteractiveTableModel tableModel = null;
    public static final String[] columnNames = {"Product Id", "Name", "Scan Code", "Normal Price", "List Price", "Avg Cost", "Quantity", ""};
    private static String[] field = {"intenalName", "defprice", "listprice", "avgcost", "prodId"};
    private static int[] width = {90, 350, 200, 120, 120, 120, 120, 2};
    private java.awt.Frame parentFrame = null;

    /**
     * Creates new form ProductDetailTableForm
     */
    public ProductDetailTableForm(java.awt.Frame parent, XuiSession xuisession) {
        parentFrame = parent;
        session = xuisession;
        initComponents();

        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        m_productsArray = new ProductDataTreeLoader(ControllerOptions.getSession().getProductCategory());
        DefaultComboBoxModel comboFacilityModel = new DefaultComboBoxModel();
        List<GenericValue> facilityList = PosProductHelper.getFacilityLists(session.getDelegator());
        for (GenericValue entry : facilityList) {
            String key = entry.getString("facilityId");
            String name = entry.getString("facilityName");
            comboFacilityModel.addElement(name);
            facilityListBidingCombo.add(key);
        }
        comboFacility.setModel(comboFacilityModel);
        String facilityId = (String) session.getAttribute("facilityId");
        if (facilityListBidingCombo.contains(facilityId)) {
            comboFacility.setSelectedIndex(facilityListBidingCombo.indexOf(facilityId));
        }

        DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
        m_productsArray.loadList();
        HashMap<String, Key> departmentMap = m_productsArray.getAllTopCategories();
        Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap);
        for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
            comboDepartmentModel.addElement(entryDept.getValue()._name);
            departmentListBidingCombo.add(entryDept.getValue()._id);
        }
        comboDepartment.setModel(comboDepartmentModel);

        Map<String, String> mapList = PosProductHelper.getSuppliers(session.getDelegator());
        comboSupplierModel = new DefaultComboBoxModel();
        for (Map.Entry<String, String> entry : mapList.entrySet()) {
            String val = entry.getValue().toString();
            comboSupplierModel.addElement(val);
            supplierListBidingCombo.add(entry.getKey());

        }
        comboSupplier.setModel(comboSupplierModel);

        tableModel = new InteractiveTableModel(columnNames);
        tableModel.addTableModelListener(new ProductDetailTableForm.InteractiveTableModelListener());
        //table = new JTable();
        jtable.setModel(tableModel);
        jtable.setSurrendersFocusOnKeystroke(true);
        if (!tableModel.hasEmptyRow()) {
            tableModel.addEmptyRow();
        }
        jtable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        setColumnWidth(width);
        TableColumn hidden = jtable.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

         treePanel = new ProductSelectionTreePanel(xuisession);
//                treePanel.setPreferredSize(new java.awt.Dimension(452, 350));

        javax.swing.GroupLayout panelTreeContainerLayout = new javax.swing.GroupLayout(panelTreeContainer);
        panelTreeContainer.setLayout(panelTreeContainerLayout);
        panelTreeContainerLayout.setHorizontalGroup(
            panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTreeContainerLayout.createSequentialGroup()
                .addComponent(treePanel)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        panelTreeContainerLayout.setVerticalGroup(
            panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTreeContainerLayout.createSequentialGroup()
                .addContainerGap(1, Short.MAX_VALUE)
                .addComponent(treePanel)
                .addGap(1, 1, 1))
        );
                
//        panelTreeContainer..addComponent(treePanel);
        treePanel.setVisible(true);
        panelTreeContainer.setVisible(true);;
        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
    }

    private ClassLoader getClassLoader() {
        ClassLoader cl = session.getClassLoader();
        if (cl == null) {
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable t) {
            }
            if (cl == null) {
//                Debug.log("No context classloader available; using class classloader", module);
                try {
                    cl = this.getClass().getClassLoader();
                } catch (Throwable t) {
                    //                  Debug.logError(t, module);
                }
            }
        }
        return cl;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        editInvoiceNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboSupplier = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        comboProduct = new javax.swing.JComboBox();
        editDefaultPrice = new javax.swing.JTextField();
        editListPrice = new javax.swing.JTextField();
        editAvgPrice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        editScanCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnApplyPrice = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboFacility = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        comboDepartment = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        comboBrand = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        editSearchStr = new javax.swing.JTextField();
        btnLoadPrice = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnProducts = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        panelTreeContainer = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(900, 706));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Invoice Id:");

        jLabel11.setText("Supplier:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(editInvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setPreferredSize(new java.awt.Dimension(452, 350));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 350));
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        jtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jtable.setMaximumSize(new java.awt.Dimension(9000, 9000));
        jtable.setPreferredSize(new java.awt.Dimension(300, 900));
        jtable.setRequestFocusEnabled(false);
        jtable.setRowHeight(22);
        jtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtableMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jtable);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        comboProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProductActionPerformed(evt);
            }
        });

        jLabel5.setText("Products");

        jLabel6.setText("Normal Price");

        jLabel7.setText("Default Price");

        jLabel8.setText("Avg, Cost");

        jLabel9.setText("Scan Code");

        btnApplyPrice.setText("Set");
        btnApplyPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyPriceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(comboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(editDefaultPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editListPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editAvgPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(editScanCode, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnApplyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editAvgPrice, editDefaultPrice, editListPrice, editScanCode});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editDefaultPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editListPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editAvgPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editScanCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApplyPrice))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Facility Name:");

        jLabel2.setText("Department Name:");

        comboDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDepartmentActionPerformed(evt);
            }
        });

        jLabel3.setText("Brand Name:");

        comboBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBrandActionPerformed(evt);
            }
        });

        jLabel4.setText("Search Str:");

        btnLoadPrice.setText("Load List");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(editSearchStr, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadPrice))
                    .addComponent(comboFacility, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(comboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editSearchStr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoadPrice)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnProducts.setText("Products");
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });

        jButton4.setText("jButton4");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProducts)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProducts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelTreeContainerLayout = new javax.swing.GroupLayout(panelTreeContainer);
        panelTreeContainer.setLayout(panelTreeContainerLayout);
        panelTreeContainerLayout.setHorizontalGroup(
            panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
        );
        panelTreeContainerLayout.setVerticalGroup(
            panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        jButton3.setText("Ok");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelTreeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTreeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public synchronized void comboDepartmentSelect() {
        Debug.logInfo("comboDepartmentSelect Select", module);

        String client = (String) comboDepartment.getSelectedItem();
        Debug.logInfo("comboDepartmentSelect selected item: " + client, module);
        if (UtilValidate.isNotEmpty(client)) {

            loadBrandItemDataModel();

        }
    }

    public void loadBrandItemDataModel() {
        try {
            DefaultComboBoxModel comboBrandModel = new DefaultComboBoxModel();
            brandListBidingCombo.clear();
            //add all 
            comboBrandModel.addElement(ProductDataTreeLoader.AllString);
            brandListBidingCombo.add(ProductDataTreeLoader.AllString);

            String departmentId = departmentListBidingCombo.get(comboDepartment.getSelectedIndex());
            String departmentName = comboDepartment.getSelectedItem().toString();

            HashMap<String, String> brandIdMap = m_productsArray.getBrandIdMap(departmentId, departmentName);
            for (Map.Entry<String, String> entryDept : brandIdMap.entrySet()) {
                String departId = entryDept.getKey();
                String departName = entryDept.getValue();
                comboBrandModel.addElement(departName);
                brandListBidingCombo.add(departId);
            }
            comboBrand.setModel(comboBrandModel);

        } catch (Exception e1) {
            Debug.logError(e1, module);
        }
    }

    public synchronized void comboBrandSelect() {

        Debug.logInfo("comboBrandSelect Select", module);

        String client = (String) comboBrand.getSelectedItem();
        if (UtilValidate.isNotEmpty(client)) {
            if (comboBrand.getSelectedIndex() != -1) {
            }
        }
    }

    private void comboDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDepartmentActionPerformed

        if (isLoading == true) {
            return;
        }

        String client = (String) comboDepartment.getSelectedItem();
        if (UtilValidate.isNotEmpty(client)) {
            try {
                isLoading = true;
                comboDepartmentSelect();
                loadBrandItemDataModel();
                loadProductPriceList();
                cutDownproductsList = getCutDownList(editSearchStr.getText());
                reloadItemDataModel(cutDownproductsList);
                reloadProductsComboData(cutDownproductsList);

            } finally {
                isLoading = false;
            }
        }

    }//GEN-LAST:event_comboDepartmentActionPerformed

    private void comboBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBrandActionPerformed
        if (isLoading == true) {
            return;
        }

        String client = (String) comboBrand.getSelectedItem();
        if (UtilValidate.isNotEmpty(client)) {

            try {
                isLoading = true;
                comboBrandSelect();
                loadProductPriceList();
                cutDownproductsList = getCutDownList(editSearchStr.getText());
                reloadItemDataModel(cutDownproductsList);
                reloadProductsComboData(cutDownproductsList);
            } finally {
                isLoading = false;
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_comboBrandActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        List quantityList = FastList.newInstance();
        for (ProductInventoryPojo entry : cutDownproductsList) {
            if (entry.productId.equals(productall) == false) {

                try {

                    if (entry.isPriceModiefied) {

                        //close all other list prices
                        PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_LISTPRICE,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), session.getDelegator());

                        //create new one
                        GenericValue listToStore = PosProductHelper.createProductPrice(
                                entry.productId, PosProductHelper.ProductPriceTypeId_LISTPRICE,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, (String) session.getAttribute("currency"),
                                UtilDateTime.nowTimestamp(), entry.price, session.getDelegator());

                        session.getDelegator().create(listToStore);
                        entry.isPriceModiefied = false;
                    }

                    if (entry.isDefaultPriceModified) {

                        //close all other list prices
                        PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), session.getDelegator());

                        //create new one
                        GenericValue listToStore = PosProductHelper.createProductPrice(
                                entry.productId, PosProductHelper.ProductPriceTypeId_DEFAULTPRICE,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, (String) session.getAttribute("currency"),
                                UtilDateTime.nowTimestamp(), entry.defaultPrice, session.getDelegator());

                        session.getDelegator().create(listToStore);
                        entry.isDefaultPriceModified = false;
                    }

                    if (entry.isPurchasePriceModified) {

                        //close all other list prices
                        PosProductHelper.closeProductPrice(entry.productId, PosProductHelper.ProductPriceTypeId_AVERAGECOST,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, UtilDateTime.nowTimestamp(), session.getDelegator());

                        //create new one
                        GenericValue listToStore = PosProductHelper.createProductPrice(
                                entry.productId, PosProductHelper.ProductPriceTypeId_AVERAGECOST,
                                PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, (String) session.getAttribute("currency"),
                                UtilDateTime.nowTimestamp(), entry.purchasePrice, session.getDelegator());

                        session.getDelegator().create(listToStore);
                        entry.isPurchasePriceModified = false;
                    }

                    if (entry.isEanValueModified) {
                        GenericValue giftEAN = PosProductHelper.getGoodIdentification(
                                entry.productId, PosProductHelper.GoodIdentificationTypeIdEAN, session.getDelegator());
                        if (giftEAN != null) {

                            boolean result = PosProductHelper.updateGoodIdentificationType(
                                    giftEAN, entry.eanValue, session.getDelegator());

                        } else {

                            boolean result = PosProductHelper.createGoodIdentificationType(
                                    entry.productId, PosProductHelper.GoodIdentificationTypeIdEAN,
                                    entry.eanValue, session.getDelegator());
                        }
                        entry.isEanValueModified = false;
                    }

                    if (entry.isQuantityOnHandModified) {
                        BigDecimal qty = entry.quantityOnHand.subtract(entry.minimumStock);
                        entry.quantity = BigDecimal.ZERO;
                        if (qty.equals(BigDecimal.ZERO) == false) {
                            entry.quantity = qty;
                            quantityList.add(entry);
                        }
                        entry.isQuantityOnHandModified = false;
                    }

                } catch (GenericEntityException e) {
                    // TODO Auto-generated catch block
                    Debug.logError(e, module);
                }
            }
        }

        if (saveInvoice(quantityList)) {
            Debug.logInfo("invoice saved", " Done");
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    public boolean saveInvoice(List invoiceListItems) {
        boolean result = false;

        String supplierName = (String) comboSupplier.getSelectedItem();
        String supplierPartyId = null;
        if (UtilValidate.isNotEmpty(supplierName)) {
            supplierPartyId = supplierListBidingCombo.get(comboSupplier.getSelectedIndex());
        }

        Delegator delegator = (Delegator) session.getDelegator();

        String ownerPartyId = XuiContainer.getSession().getCompanyPartyId();
        String currencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
        String origCurrencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
        String destinationFacilityId = facilityListBidingCombo.get(comboFacility.getSelectedIndex());
        String referenceNumber = editInvoiceNumber.getText();

        try {

            PosProductHelper.createPurchaseOrder(supplierPartyId, ownerPartyId, currencyUomId, origCurrencyUomId, destinationFacilityId, invoiceListItems, referenceNumber, delegator);
            result = true;
            /*			
             List<GenericValue> invoiceList = findInvoiceFromExternalInvoiceNumber(supplierPartyId, referenceNumber);//findOrderIdFromExternalInvoiceNumber(editInvoiceNo.getText());
             if(invoiceList.isEmpty() == false){
             GenericValue invoice = invoiceList.get(0);   
             BigDecimal notApplied = BigDecimal.TEN;
             String invoiceId = "";
             List<GenericValue> orderItemBillings = invoice.getRelated("OrderItemBilling");
             for(GenericValue billing : orderItemBillings){
             orderReadHelper = new OrderReadHelper(OrderReadHelper.getOrderHeader(delegator, billing.getString("orderId")));
             invoiceId = billing.getString("invoiceId");
             orderId = billing.getString("orderId");
             String msg = "Invoice Saved ";
             setInvoiceStatusMessage(msg, supplierPartyId, referenceNumber, orderId, invoiceId, null);						
             break;
             }
             }	
             */
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
        }

        return result;

    }

    private void jtableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableMouseEntered
    }//GEN-LAST:event_jtableMouseEntered

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        Point pnt = evt.getPoint();
        int row = jtable.rowAtPoint(pnt);
        int col = jtable.columnAtPoint(pnt);
        String prodId = (String) jtable.getModel().getValueAt(row, InteractiveTableModel.PROD_ID_INDEX);
        if (prodId != null) {
            comboProduct.setSelectedIndex(productListBidingCombo
                    .indexOf(prodId));
        }
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableMouseClicked
        Point pnt = evt.getPoint();
        int row = jtable.rowAtPoint(pnt);
        int col = jtable.columnAtPoint(pnt);
        String prodId = (String) jtable.getModel().getValueAt(row, InteractiveTableModel.PROD_ID_INDEX);
        if (prodId != null) {
            comboProduct.setSelectedIndex(productListBidingCombo
                    .indexOf(prodId));
        }
    }//GEN-LAST:event_jtableMouseClicked

    private void comboProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProductActionPerformed
        if (isLoading == true) {
            return;
        }

        String client = (String) comboProduct.getSelectedItem();
        if (UtilValidate.isNotEmpty(client)) {

            try {
                isLoading = true;
                String productId = productListBidingCombo.get(comboProduct.getSelectedIndex());
                loadProductDetail(productId);
            } finally {
                isLoading = false;
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_comboProductActionPerformed

    private void btnApplyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyPriceActionPerformed
        String client = (String) comboProduct.getSelectedItem();
        String productId = null;
        if (UtilValidate.isNotEmpty(client)) {
            productId = productListBidingCombo.get(comboProduct.getSelectedIndex());
        }

        if (productId != null) {

            if (productId.equals(productall)) {

                for (ProductInventoryPojo entry : cutDownproductsList) {

                    if (editListPrice.getText() != null && editListPrice.getText().isEmpty() == false) {

                        entry.price = new BigDecimal(editListPrice.getText());
                        entry.isPriceModiefied = true;
                    }

                    if (editDefaultPrice.getText() != null && editDefaultPrice.getText().isEmpty() == false) {

                        entry.defaultPrice = new BigDecimal(editDefaultPrice.getText());
                        entry.isDefaultPriceModified = true;
                    }

                    if (editAvgPrice.getText() != null && editAvgPrice.getText().isEmpty() == false) {
                        entry.purchasePrice = new BigDecimal(editAvgPrice.getText());
                        entry.isPurchasePriceModified = true;
                    }

                }
            } else {
                for (ProductInventoryPojo entry : cutDownproductsList) {


                    if (productId.equals(entry.productId)) {

                        if (editScanCode.getText() != null && editScanCode.getText().isEmpty() == false) {
//                            if (entry.eanValue.equals(editScanCode.getText()) == false){
                            GenericValue prod = PosProductHelper.getProductFromScanCode(editScanCode.getText(), PosProductHelper.GoodIdentificationTypeIdEAN, session.getDelegator());
                            if (prod == null) {
                                entry.eanValue = editScanCode.getText();
                                entry.isEanValueModified = true;
                            } else {
                                String prodId = prod.getString("productId");
                                if (prodId != null && prodId.equals(productId) == false) {
                                    GenericValue product = PosProductHelper.getProduct(prodId, session.getDelegator());
                                    OrderMaxOptionPane.showMessageDialog(null, "Scan code already exist!! " + "[" + product.getString("internalName") + "]");
                                } else {
                                    entry.eanValue = editScanCode.getText();
                                    entry.isEanValueModified = true;
                                }
                            }
                            //                          }
                        }

                        if (editListPrice.getText() != null && editListPrice.getText().isEmpty() == false) {

                            entry.price = new BigDecimal(editListPrice.getText());
                            entry.isPriceModiefied = true;
                        }

                        if (editDefaultPrice.getText() != null && editDefaultPrice.getText().isEmpty() == false) {

                            entry.defaultPrice = new BigDecimal(editDefaultPrice.getText());
                            entry.isDefaultPriceModified = true;
                        }

                        if (editAvgPrice.getText() != null && editAvgPrice.getText().isEmpty() == false) {
                            entry.purchasePrice = new BigDecimal(editAvgPrice.getText());
                            entry.isPurchasePriceModified = true;
                        }

                        break;
                    }
                }

            }

        }

        reloadItemDataModel(cutDownproductsList);

    }//GEN-LAST:event_btnApplyPriceActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        ProductDetailEditDialog dlg = new ProductDetailEditDialog(parentFrame, true, session);
        dlg.setVisible(true);
    }//GEN-LAST:event_btnProductsActionPerformed

    public void loadProductPriceList() {

        List<ProductInventoryPojo> oldProductPriceList = productPriceList;
        String facilityId = facilityListBidingCombo.get(comboFacility.getSelectedIndex());
        String facilityName = comboFacility.getSelectedItem().toString();

        String depId = departmentListBidingCombo.get(comboDepartment.getSelectedIndex());
        String depName = comboDepartment.getSelectedItem().toString();
        String branId = brandListBidingCombo.get(comboBrand.getSelectedIndex());
        String branName = comboBrand.getSelectedItem().toString();

        HashMap<String, String> prodMap = m_productsArray.getProductMap(depId, depName, branId, branName);
        productPriceList = FastList.newInstance();
        Map<String, String> treeMap = new TreeMap<String, String>(prodMap);
        for (Map.Entry<String, String> entryDept : treeMap.entrySet()) {
            String prodId = entryDept.getKey();
            String prodName = entryDept.getValue();
            boolean found = false;
            if (oldProductPriceList != null) {
                for (ProductInventoryPojo priceObj : oldProductPriceList) {
                    if (priceObj.productId.equals(prodId)) {
                        productPriceList.add(priceObj);
                        found = true;
                        break;
                    }
                }
            }

            if (found == false) {
                //              ProductInventoryPojo pojo = new ProductInventoryPojo();
                //              pojo.productId = prodId;
//                pojo.internalName = prodName;

                try {

                    //PosProductHelper.LoadPriceList(pojo, session.getDelegator());
                    ProductInventoryPojo pojo = PosProductHelper.getProductAndPriceDetails(prodId, session.getDelegator());
                    productPriceList.add(pojo); //.add(entryDept);                    

                    LocalDispatcher dispatcher = session.getDispatcher();
                    Map<String, Object> inventoryMap = PosProductHelper.getInventoryAvailableByFacility(prodId, facilityId, dispatcher);
                    BigDecimal availableToPromiseTotal = (BigDecimal) inventoryMap.get("availableToPromiseTotal");
                    BigDecimal quantityOnHandTotal = (BigDecimal) inventoryMap.get("quantityOnHandTotal");
                    if (availableToPromiseTotal != null && quantityOnHandTotal != null) {
                        pojo.quantityOnHand = quantityOnHandTotal;
                        pojo.minimumStock = quantityOnHandTotal;

                        pojo.availableToPromise = quantityOnHandTotal;

                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
//	            m_listModel.addElement(prodName);
//	        	productsListBinding.add(prodId);		            
        }

    }

    public void reloadItemDataModel(List<ProductInventoryPojo> cutdownList) {

        tableModel = new InteractiveTableModel(columnNames);
        jtable.setModel(tableModel);
        setColumnWidth(width);
        TableColumn hidden = jtable.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));
        jtable.setPreferredSize(new java.awt.Dimension(500, cutdownList.size() * jtable.getRowHeight()));
        tableModel.addRows(cutdownList);
        /*		XModel jmodel = tableEdit.createModel();
         tableEdit.setColumnWidth(jmodel);


         ListPriceTableEditTable.appendItemDataModel(jmodel, cutdownList);
	
         jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
         System.out.println("e...."+jtable.getSelectedRow());
         String prodId = tableEdit.getSelectedSku();
         comboProduct.setSelectedIndex(productListBidingCombo.indexOf(prodId));		     				
		        
         }
         });

         if( jtable.getRowCount() > 0 ){
         try {
         jtable.update();
         } catch (ArrayIndexOutOfBoundsException ex) {
         Debug.logError(ex, "Unable to repaint the Journal", module);
         }
         }                     
         */
    }
    static String productall = "AllProducts";

    public void loadProductDetail(String prodId) {

        if (prodId != null && cutDownproductsList != null) {
            if (prodId.equals(productall)) {
                editDefaultPrice.setText("");
                editListPrice.setText("");
                editAvgPrice.setText("");
                editScanCode.setText("");

            } else {
                for (ProductInventoryPojo entry : cutDownproductsList) {
                    if (prodId.equals(entry.productId)) {
                        editDefaultPrice.setText(entry.defaultPrice.toString());
                        editListPrice.setText(entry.price.toString());
                        editAvgPrice.setText(entry.purchasePrice.toString());
                        editScanCode.setText(entry.eanValue);
                        break;
                    }
                }

            }
        }
    }

    public List<ProductInventoryPojo> getCutDownList(String searchStr) {
        //     	Debug.logInfo("new search str: "+searchStr , module);


        List<ProductInventoryPojo> productsArray = FastList.newInstance();

        ProductInventoryPojo pojo = new ProductInventoryPojo();
        pojo.productId = productall;
        pojo.internalName = productall;
        productsArray.add(pojo);

        if (searchStr == null || searchStr.isEmpty()) {
            productsArray.addAll(productPriceList);
        } else {

            for (ProductInventoryPojo it : productPriceList) {

                ProductInventoryPojo obj = it;
                String val = obj.internalName;

                if (val.toUpperCase().contains(searchStr.toUpperCase())) {
                    productsArray.add(obj);
                }
                //	        	else if(brandName!=null && brandName.toUpperCase().contains(searchStr.toUpperCase())){
                //	        		productsArray.add(obj);        		
                //	        	}

            }
        }

        return productsArray;
    }

    public void reloadProductsComboData(List<ProductInventoryPojo> cutdownList) {


        DefaultComboBoxModel comboProductModel = new DefaultComboBoxModel();
        productListBidingCombo.clear();
        for (ProductInventoryPojo entry : cutdownList) {


            String key = entry.productId;
            String name = entry.internalName;
            comboProductModel.addElement(name);
            productListBidingCombo.add(key);
        }
        comboProduct.setModel(comboProductModel);
    }

    public void highlightLastRow(int row) {
        int lastrow = tableModel.getRowCount();
        if (row == lastrow - 1) {
            jtable.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else if(row == row + 1){
            jtable.setRowSelectionInterval(row + 1, row + 1);
        }
        else{
            jtable.setRowSelectionInterval(row, row);            
        }

        jtable.setColumnSelectionInterval(0, 0);
    }

    private void rowChanged(int row) {
        int lastrow = tableModel.getRowCount();
        int selrow = row;
        if (row == lastrow - 1) {
            selrow = lastrow - 1;
        } else {
            selrow = lastrow + 1;
        }


        String prodId = (String) jtable.getModel().getValueAt(selrow, InteractiveTableModel.PROD_ID_INDEX);
        if (prodId != null) {
            comboProduct.setSelectedIndex(productListBidingCombo
                    .indexOf(prodId));
        }
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {

        protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((ProductDetailTableForm.this.tableModel.getRowCount() - 1) == row
                        && !ProductDetailTableForm.this.tableModel.hasEmptyRow()) {
                    ProductDetailTableForm.this.tableModel.addEmptyRow();
                }

                highlightLastRow(row);
                //        rowChanged(row);
            }

            return c;
        }
    }

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                jtable.setColumnSelectionInterval(column + 1, column + 1);
                jtable.setRowSelectionInterval(row, row);
            }
        }
    }

    public void setColumnWidth(int[] columnWidth) {
        TableColumn column = null;
        for (int i = 0; i < columnWidth.length; i++) {
            column = jtable.getColumnModel().getColumn(i);
//            if (i == 2) {
            column.setPreferredWidth(columnWidth[i]); //third column is bigger
//            } else {
//                column.setPreferredWidth(50);
//            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApplyPrice;
    private javax.swing.JButton btnLoadPrice;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox comboBrand;
    private javax.swing.JComboBox comboDepartment;
    private javax.swing.JComboBox comboFacility;
    private javax.swing.JComboBox comboProduct;
    private javax.swing.JComboBox comboSupplier;
    private javax.swing.JTextField editAvgPrice;
    private javax.swing.JTextField editDefaultPrice;
    private javax.swing.JTextField editInvoiceNumber;
    private javax.swing.JTextField editListPrice;
    private javax.swing.JTextField editScanCode;
    private javax.swing.JTextField editSearchStr;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    private javax.swing.JPanel panelTreeContainer;
    // End of variables declaration//GEN-END:variables

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt ) {

        TreePath tp = null;
        tp = treePanel.getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {

//                clearFields();

                TreePath parentPath = tp.getParentPath();
                if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    try {
                        //get data from table
                        String productId = node._id;
                        GenericValue productEntity = node.loadDetails(productId, session.getDelegator());
   //                     loadProductDetail(productId);
                          //find department name from tree
                        TreeNode deptName = treePanel.GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                        if (deptName != null) {
                            Debug.logInfo("tree clicked dep -- " + deptName.getNodeName() + " node id " + deptName._id + " - " + deptName._name, module);
                            comboDepartment.getModel().setSelectedItem(deptName._name);
                        } else {
                            Debug.logInfo("tree clicked dep null", module);
                        }
           
                        comboBrand.getModel().setSelectedItem(productEntity.getString("brandName"));
                        //find brand name from tree
                        TreeNode brandName = treePanel.GetRecusevilyNodeType(node, tp, BrandTreeNode.BrandNodeName);
                        if (brandName != null) {
                            comboBrand.setSelectedIndex(brandListBidingCombo.indexOf(brandName._id));                            
                        } else {
                            Debug.logInfo("tree clicked brand null", module);
                        }

                        if (productId != null) {
                            comboProduct.setSelectedIndex(productListBidingCombo.indexOf(productId));
                            int row = tableModel.getRowNumber(productId);
                            highlightLastRow(row);
                        }
                                                
                    } catch (GenericEntityException ex) {
                        java.util.logging.Logger.getLogger(ProductSelectionTreePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (node.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
                    try {
                        String brandId = node._id;
                        GenericValue brandEntity = node.loadDetails(brandId, session.getDelegator());

                        TreeNode deptName = treePanel.GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                        if (deptName != null) {
                            comboDepartment.getModel().setSelectedItem(deptName._name);                        
                        } else {
                            Debug.logInfo("tree clicked dep null", module);
                        }   
                        
                        comboBrand.setSelectedIndex(brandListBidingCombo.indexOf(brandId));                            
                        highlightLastRow(0);                            
                    } catch (GenericEntityException ex) {
                    }
                } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                    try {
                        String departmentId = node._id;
                        GenericValue brandEntity = node.loadDetails(departmentId, session.getDelegator());

                        comboDepartment.getModel().setSelectedItem(brandEntity.getString("categoryName"));
                        comboBrand.setSelectedIndex(brandListBidingCombo.indexOf(ProductDataTreeLoader.AllString));                                                    
                        highlightLastRow(0); 
//                        comboProductDepName.getModel().setSelectedItem(editDepartmentName.getText());
                    } catch (GenericEntityException ex) {
                        Logger.getLogger(ProductDetailEditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                    String productId = node._id;

                }

            }
        }
    }

}
