/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
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
import org.ofbiz.ordermax.product.BrandPanel;
import org.ofbiz.ordermax.product.CustomProductPanel;
import org.ofbiz.ordermax.product.DepartmentPanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductDetailEditDialog extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    protected boolean isLoading = false;
    protected static XuiSession session = null;
    public static final String module = ProductDetailEditDialog.class.getName();
//    protected List<String> departmentListBidingCombo = FastList.newInstance();
    protected List<String> brandListBidingCombo = FastList.newInstance();
    protected List<String> productListBidingCombo = FastList.newInstance();
    protected List<String> facilityListBidingCombo = FastList.newInstance();
    protected List<String> supplierListBidingCombo = FastList.newInstance();
    protected List<Key> departmentList = FastList.newInstance();
    protected Hashtable<String, String> scanIdMap = new Hashtable<String, String>();
    protected KeyTableModel tableModel = null;
//    protected KeyTableModel brandTableModel = null;
    List<String> brandMap = null;
    Map<String, String> departmentMap = null;
    BigDecimal quantityOnHand = BigDecimal.ONE;

    class ProductCodeId {

        public String BrandName;
        public String ProductIdPrefix;
        public List<Integer> itemList;
    };
    protected Map<String, Map<String, ProductCodeId>> codeIdMap = FastMap.newInstance();
    static List<GenericValue> orderedproductPriceList = FastList.newInstance();
    protected ProductInventoryPojo curryProductPojo = new ProductInventoryPojo();
//    public static final String[] columnNames = {"Department Id", "Department Name", ""};
//    public static final String[] brandColumnNames = {"Brand Id", "Brand Name", ""};
    private static int[] width = {350, 350, 2};
    static boolean showTabsHeader = false;
    static String PRODUCT_TAB_INDEX = "PRODUCT";
    static String DEPARTMENT_TAB_INDEX = "DEPARTMENT";
    static String BRAND_TAB_INDEX = "BRAND";
    protected JPanel productCardPanel = null;
    String visibleCardName = null;
    ProductSelectionTreePanel treePanel = null;
    CustomProductPanel customProductPanel = null;
    DepartmentPanel departmentPanel = null;
    BrandPanel brandPanel = null;

    /**
     * Creates new form ProductDetailEditDialog
     */
    public ProductDetailEditDialog(java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        super(parent, modal);
        session = sessionVal;
        initComponents();

//        productCardPanel.setLayout(new CardLayout());
//        productCardPanel.add(jPanel8, DEPARTMENT_TAB_INDEX);
//        productCardPanel.add(jPanel7, BRAND_TAB_INDEX);
        //productCardPanel.add(jPanel3, PRODUCT_TAB_INDEX);        
        ProductDataTreeLoader m_productsArray = new ProductDataTreeLoader(ControllerOptions.getSession().getProductCategory());
        m_productsArray.loadList();

        customProductPanel = new CustomProductPanel(session);
        departmentPanel = new DepartmentPanel(m_productsArray.getRootNode(), session);
        brandPanel = new BrandPanel(session);

//        jPanel12.add(productCardPanel);
//        OrderMaxUtility.addAPanelToPanel(productCardPanel, productCardPanel);
        comboPostalChanged(DEPARTMENT_TAB_INDEX);

        /*
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

         Map<String, String> mapList = PosProductHelper.getSuppliers(session.getDelegator());
         DefaultComboBoxModel comboSupplierModel = new DefaultComboBoxModel();
         for (Map.Entry<String, String> entry : mapList.entrySet()) {
         String val = entry.getValue().toString();
         comboSupplierModel.addElement(val);
         supplierListBidingCombo.add(entry.getKey());
         }

         comboSupplier.setModel(comboSupplierModel);
         */
        brandMap = FastList.newInstance();
        eventLoadBrand();
//        customProductPanel.setBrandList(brandMap);
        /*      DefaultComboBoxModel comboBrandModel = new DefaultComboBoxModel();
         for (String entry : brandMap) {
         comboBrandModel.addElement(entry);
         }
         comboBrand.setModel(comboBrandModel);
         */
        departmentMap = FastMap.newInstance();

        Map departmentValMap = FastMap.newInstance();

        //      DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
        //      DefaultComboBoxModel comboDepartmentBrandModel = new DefaultComboBoxModel();
        HashMap<String, Key> departmentMap = m_productsArray.getAllTopCategories();
        Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap);
        Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
        for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
            departmentValMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
//            comboDepartmentBrandModel.addElement(entryDept.getValue()._name);
//            departmentListBidingCombo.add(entryDept.getValue()._id);
            try {
                treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
            } catch (Exception e) {
                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
                Debug.logError(e, module);

            }
        }
//        customProductPanel = new ProductPanel();
//        departmentPanel = new DepartmentPanel();

        customProductPanel.setDepartmentMap(departmentValMap);
        departmentPanel.setDepartmentMap(departmentValMap);

        //comboDepartment.setModel(comboDepartmentBrandModel);
        //comboProductDepName.setModel(comboDepartmentModel);
        //sorted by id list
        for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
            departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
        }
        departmentPanel.reloadItemDataModel(departmentList);


        ArrayList<Key> brandList = m_productsArray.getAllBrands();

        //sort
        Map<String, Key> brandTreeMap = new TreeMap<String, Key>();
        for (Key brand : brandList) {
            brandTreeMap.put(brand._name, brand);
        }

        brandList.clear();
        for (Map.Entry<String, Key> entryDept : brandTreeMap.entrySet()) {
            brandList.add(entryDept.getValue());
        }

        brandPanel.reloadBrandItemDataModel(brandList);

        ArrayList<Key> productList = m_productsArray.getListAll();
        for (Key prod : productList) {
            String productidd = prod._id;
            customProductPanel.addToProductCodeKeyMap(productidd);
        }

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });

        treePanel = new ProductSelectionTreePanel(session);
/*        treePanel.getBtnAddSelectedAll().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("selected All", module);
                List<TreeNode> prodNodeList = treePanel.getAllSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNodeList != null) {
                    for (TreeNode prodNode : prodNodeList) {
                        try {
//                            addItem(prodNode._id);
                        } catch (Exception ex) {
                            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
//                    refreshScreen();;
                }
            }
        });
*/


        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTreeMouseClicked(evt);
            }
        });

//        OrderMaxUtility.addAPanelToPanel(treePanel, panelTreeContainer);
        productCardPanel = panelDetail;//new JPanel(new CardLayout());
        productCardPanel.setLayout(new CardLayout());
        productCardPanel.add(departmentPanel, DEPARTMENT_TAB_INDEX);
        productCardPanel.add(brandPanel, BRAND_TAB_INDEX);
        productCardPanel.add(customProductPanel, PRODUCT_TAB_INDEX);

//        OrderMaxUtility.addAPanelToPanel(productCardPanel, panelDetail);
        OrderMaxUtility.addAPanelToPanel(treePanel, panelSelecton);


        this.pack();
    }

    public void eventLoadBrand() {
        // if (wasMouseClicked()) {
        BufferedReader CSVFile;

        try {
            CSVFile = new BufferedReader(new FileReader(
                    "C:\\AuthLog\\brand.csv"));

            String dataRow;
            try {
                // List departmentList = FastList.newInstance();
                dataRow = CSVFile.readLine();
                brandMap.clear();

                while (dataRow != null) {
                    String[] dataArray = dataRow.split(",");
                    // int index = 0;
                    for (String item : dataArray) {
                        brandMap.add(item);
                        // index++;;
                        System.out.print(item + ",");
                        break;
                    }

                    System.out.println(); // Print the data line.
                    dataRow = CSVFile.readLine(); // Read next line of data.
                }

                // Close the file once all data has been read.
                CSVFile.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Read the first line of data.

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void eventLoadDepartment() {
        // if (wasMouseClicked()) {
        BufferedReader CSVFile;

        try {
            CSVFile = new BufferedReader(new FileReader(
                    "C:\\AuthLog\\department.csv"));

            String dataRow;
            try {
                // List departmentList = FastList.newInstance();
                dataRow = CSVFile.readLine();
                departmentMap.clear();

                // The while checks to see if the data is null. If it is, we've
                // hit
                // the end of the file. If not, process the data.
                while (dataRow != null) {
                    String[] dataArray = dataRow.split(",");
                    // int index = 0;
                    if (dataArray.length > 1) {
                        // for (String item:dataArray) {
                        departmentMap.put(dataArray[0], dataArray[1]);
                        // index++;;
                        System.out.print(dataArray[0] + "," + dataArray[1]);

                    }

                    System.out.println(); // Print the data line.
                    dataRow = CSVFile.readLine(); // Read next line of data.
                }

                // Close the file once all data has been read.
                CSVFile.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // Read the first line of data.

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // }
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        btnNewDepartment1 = new javax.swing.JButton();
        btnBrandName1 = new javax.swing.JButton();
        btnSave1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnNewProduct1 = new javax.swing.JButton();
        cancelButton1 = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        panelDetail = new javax.swing.JPanel();
        panelSelecton = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(900, 600));
        setPreferredSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel3.setLayout(new java.awt.GridBagLayout());

        panelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelButton.setPreferredSize(new java.awt.Dimension(900, 60));
        panelButton.setLayout(new java.awt.GridBagLayout());

        btnNewDepartment1.setText("New Department");
        btnNewDepartment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewDepartment1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(btnNewDepartment1, gridBagConstraints);

        btnBrandName1.setText("New Brand");
        btnBrandName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrandName1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(btnBrandName1, gridBagConstraints);

        btnSave1.setText("Save");
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(btnSave1, gridBagConstraints);

        btnDelete1.setText("Delete");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(btnDelete1, gridBagConstraints);

        btnNewProduct1.setText("New Product");
        btnNewProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewProduct1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(btnNewProduct1, gridBagConstraints);

        cancelButton1.setText("Cancel");
        cancelButton1.setDefaultCapable(false);
        cancelButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(cancelButton1, gridBagConstraints);

        okButton.setText("OK");
        okButton.setDefaultCapable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelButton.add(okButton, gridBagConstraints);
        getRootPane().setDefaultButton(okButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(panelButton, gridBagConstraints);

        panelDetail.setPreferredSize(new java.awt.Dimension(700, 0));

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 700;
        gridBagConstraints.ipady = 397;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        jPanel3.add(panelDetail, gridBagConstraints);

        javax.swing.GroupLayout panelSelectonLayout = new javax.swing.GroupLayout(panelSelecton);
        panelSelecton.setLayout(panelSelectonLayout);
        panelSelectonLayout.setHorizontalGroup(
            panelSelectonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelSelectonLayout.setVerticalGroup(
            panelSelectonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 397;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(panelSelecton, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    public void comboPostalChanged(String desc) {
        if (productCardPanel != null) {
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            visibleCardName = desc;
        }
    }

    public String getVisibleCard() { //now we want to get the String identifier of the top card:
        String cardName = null;

        for (Component comp : productCardPanel.getComponents()) {
            if (comp.isVisible() == true) {
                JPanel card = (JPanel) comp;
                cardName = card.getName();
            }
        }
        return cardName;
    }

    void productTreeMouseClicked(java.awt.event.MouseEvent evt) {
/*
        Delegator delegator = (Delegator) session.getDelegator();
        String visiblePanelName = getVisibleCard();
        Debug.logInfo("visiblePanelName: " + visiblePanelName, module);
        if (visibleCardName == null) {
            return;
        }

        if (visibleCardName.equals(PRODUCT_TAB_INDEX)) {
            if (isValidFields() == false) {
                return;
            }

            // save the changes
            save();
            loadProductTree();

        } else if (visibleCardName.equals(BRAND_TAB_INDEX)) {
            try {

                String depId = getDepartmentCode();
                String depName = getDepartmentName();
                String brandName = editBrandName.getText();

                String brandId = depId + "_" + brandName.replaceAll("\\s", "");

                if (brandId != null && brandId.length() > 20) {
                    brandId = brandId.substring(0, 19);
                }
                editBrandId.setText(brandId);
                BrandTreeNode brandNode = new BrandTreeNode(brandId, brandName, false);

                GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, delegator);
                if (brandCategory == null) {
                    delegator.create(PosProductHelper.createProductCategory(brandId, depId, "CATALOG_CATEGORY", brandName, delegator));
                }

                GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, depId, delegator);
                if (rollUp == null) {
                    rollUp = PosProductHelper.createProductCategoryRollup(brandId, depId, delegator);
                    delegator.create(rollUp);
                    //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);
                }

                loadProductTree();
                //}

                //now add the node to tree



            } catch (GenericEntityException ex) {
                Logger.getLogger(ProductDetailEditDialog.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (visibleCardName.equals(DEPARTMENT_TAB_INDEX)) {
            String depId = editDepartmentId.getText();
            String depName = editDepartmentName.getText();

            GenericValue brandCategory = PosProductHelper.getProductCategory(depId, delegator);
            if (brandCategory == null) {
                try {
                    delegator.create(PosProductHelper.createProductCategory(depId, treePanel.getRootNode()._id, "CATALOG_CATEGORY", depName, delegator));
                } catch (GenericEntityException ex) {
                    Logger.getLogger(ProductDetailEditDialog.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            GenericValue rollUp = PosProductHelper.getProductCategoryRollup(depId, treePanel.getRootNode()._id, delegator);
            if (rollUp == null) {
                try {
                    rollUp = PosProductHelper.createProductCategoryRollup(depId, treePanel.getRootNode()._id, delegator);
                    delegator.create(rollUp);
                    //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);


                } catch (GenericEntityException ex) {
                    Logger.getLogger(ProductDetailEditDialog.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            loadProductTree();
        }
*/

        TreePath tp = null;
        tp = treePanel.getTree().getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {

//                clearBrandDialogFields();
//                clearDepartmentDialogFields();
//                customProductPanel.clearProductDialogFields();

                TreePath parentPath = tp.getParentPath();
//                if (parentPath != null) {
//                    currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
//                }

                //   currTreeNode = node;
                if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    /*
                     try {
                     //get data from table
                     String productId = node._id;
                     GenericValue productEntity = node.loadDetails(productId, session.getDelegator());

                     editProductId.setText(productId);
                     editQtyOnHand.setText(quantityOnHand.toString());
                     editProductName.setText(productEntity.getString("productName"));
                     editInternalName.setText(productEntity.getString("internalName"));
                     //TreeNode brandName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);

                     comboBrand.getModel().setSelectedItem(productEntity.getString("brandName"));
                     //find brand name from tree
                     TreeNode brandName = GetRecusevilyNodeType(node, tp, BrandTreeNode.BrandNodeName);
                     if (brandName != null) {
                     editBrandId.setText(brandName._id);
                     editBrandName.setText(brandName._name);

                     } else {
                     //Debug.logInfo("tree clicked brand null", module);
                     }

                     //find department name from tree
                     TreeNode deptName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                     if (deptName != null) {
                     //Debug.logInfo("tree clicked dep -- " + deptName.getNodeName() + " node id " + deptName._id + " - " + deptName._name, module);
                     comboProductDepName.getModel().setSelectedItem(deptName._name);
                     editDepartmentId.setText(deptName._id);
                     editDepartmentName.setText(deptName._name);

                     } else {
                     //Debug.logInfo("tree clicked dep null", module);
                     }

                     //get price details from db
                     Map<String, GenericValue> priceResult = PosProductHelper.getPriceListAndProductDetails(productId, session.getDelegator());
                     if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_LISTPRICE)) {
                     BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_LISTPRICE).getBigDecimal("price");
                     if (price != null) {
                     editListPrice.setText(price.toString());
                     }
                     }

                     if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE)) {
                     BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE).getBigDecimal("price");
                     if (price != null) {
                     editBasePrice.setText(price.toString());
                     }
                     }

                     if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_AVERAGECOST)) {
                     BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_AVERAGECOST).getBigDecimal("price");
                     if (price != null) {
                     editPurchasePrice.setText(price.toString());
                     }
                     }

                     if (priceResult.containsKey(PosProductHelper.SUPPLIERPRODUCT)) {
                     GenericValue supplierProduct = priceResult.get(PosProductHelper.SUPPLIERPRODUCT);
                     editSupplierCode.setText(supplierProduct.getString("supplierProductId"));
                     if (supplierListBidingCombo.contains(supplierProduct.getString("partyId"))) {
                     comboSupplier.setSelectedIndex(supplierListBidingCombo.indexOf(supplierProduct.getString("partyId")));
                     }
                     editSupplierLastPrice.setText(supplierProduct.getBigDecimal("lastPrice").toString());
                     }

                     if (priceResult.containsKey(PosProductHelper.GoodIdentificationTypeIdEAN)) {
                     editScanCode.setText(priceResult.get(PosProductHelper.GoodIdentificationTypeIdEAN).getString("idValue"));
                     //                            if (price != null) {
                     //                              editPurchasePrice.setText(price.toString());
                     //                         }
                     }
                     */
                    comboPostalChanged(PRODUCT_TAB_INDEX);
//                    } catch (GenericEntityException ex) {
//                        Logger.getLogger(ProductDetailEditDialog.class
//                                .getName()).log(Level.SEVERE, null, ex);
//                    }
                } else if (node.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
                    /*
                     try {
                     String brandId = node._id;
                     GenericValue brandEntity = node.loadDetails(brandId, session.getDelegator());
                     editBrandId.setText(brandEntity.getString("productCategoryId"));
                     editBrandName.setText(brandEntity.getString("categoryName"));

                     TreeNode deptName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
                     if (deptName != null) {
                     //Debug.logInfo("tree clicked dep -- " + deptName.getNodeName() + " node id " + deptName._id + " - " + deptName._name, module);
                     comboDepartment.getModel().setSelectedItem(deptName._name);
                     editDepartmentId.setText(deptName._id);
                     editDepartmentName.setText(deptName._name);

                     comboProductDepName.getModel().setSelectedItem(deptName._name);
                     } else {
                     //Debug.logInfo("tree clicked dep null", module);
                     }

                     comboPostalChanged(BRAND_TAB_INDEX);

                     } catch (GenericEntityException ex) {
                     Logger.getLogger(ProductDetailEditDialog.class
                     .getName()).log(Level.SEVERE, null, ex);
                     }
                     */
                    comboPostalChanged(BRAND_TAB_INDEX);
                } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                    /*try {
                     String departmentId = node._id;
                     GenericValue brandEntity = node.loadDetails(departmentId, session.getDelegator());

                     editDepartmentId.setText(brandEntity.getString("productCategoryId"));
                     editDepartmentName.setText(brandEntity.getString("categoryName"));
                     comboDepartment.getModel().setSelectedItem(editDepartmentName.getText());
                     comboProductDepName.getModel().setSelectedItem(editDepartmentName.getText());

                     } catch (GenericEntityException ex) {
                     Logger.getLogger(ProductDetailEditDialog.class
                     .getName()).log(Level.SEVERE, null, ex);
                     }
                     //                    findProduct(productId);
                     */
                    comboPostalChanged(DEPARTMENT_TAB_INDEX);
                } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                    String productId = node._id;

                }
            }
        }
    }

    private void btnNewDepartment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewDepartment1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewDepartment1ActionPerformed

    private void btnBrandName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrandName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBrandName1ActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void btnNewProduct1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewProduct1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewProduct1ActionPerformed

    private void cancelButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButton1ActionPerformed
    //get product details and update screen

    void findProduct(String productId) {
        try {
            //clear everything
//            customProductPanel.clearProductDialogFields();
            Delegator delegator = session.getDelegator();
            ProductInventoryPojo tempPojo = PosProductHelper.getProductAndPriceDetails(productId, delegator);
            if (tempPojo.productId != null) {
                curryProductPojo = tempPojo;
//sur                setDialogFields();
                //m_pos.getPromoStatusBar().displayMessage("Found product with ID: " + curryProductPojo.productName + "[" + curryProductPojo.productId + "]");
            } else {
                //	  OrderMaxOptionPane.showMessageDialog(null, "No product found");
                //m_pos.getPromoStatusBar().displayMessage("Unable to find product with ID: " + productId);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrandName1;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnNewDepartment1;
    private javax.swing.JButton btnNewProduct1;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton cancelButton1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelSelecton;
    // End of variables declaration//GEN-END:variables

    public JTextField getEditProductId() {
        return customProductPanel.getEditProductId();
    }
    private int returnStatus = RET_CANCEL;
    ProductTreeModel productTreeModel = null;
//    ProductTreeNode prodRootNose=null;

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    /*
     public void getDialogFields() {
     curryProductPojo.productId = editProductId.getText().toUpperCase();
     curryProductPojo.brandId = (String) comboBrand.getSelectedItem();
     curryProductPojo.brandName = (String) comboBrand.getSelectedItem();
     curryProductPojo.description = getDepartmentName().toUpperCase();

     curryProductPojo.quantityOnHand = quantityOnHand;
     curryProductPojo.productName = editProductName.getText().toUpperCase();
     curryProductPojo.internalName = editInternalName.getText().toUpperCase();
     curryProductPojo.brandName = (String) comboBrand.getSelectedItem();

     curryProductPojo.price = new BigDecimal(editListPrice.getText());
     curryProductPojo.defaultPrice = new BigDecimal(editBasePrice.getText());
     curryProductPojo.purchasePrice = new BigDecimal(editPurchasePrice.getText());

     curryProductPojo.currencyUomId = editCurrency.getText();
     curryProductPojo.supplierProductId = editSupplierCode.getText().toUpperCase();

     curryProductPojo.eanValue = editScanCode.getText().toUpperCase();

     curryProductPojo.categoryId = getDepartmentCode();
     curryProductPojo.categoryName = getDepartmentName();
     curryProductPojo.brandId = curryProductPojo.categoryId + "_" + ((String) comboBrand.getSelectedItem()).replaceAll("\\s", "");
     String client = (String) comboSupplier.getSelectedItem();

     if (UtilValidate.isNotEmpty(client)) {
     curryProductPojo.supplierPartyId = supplierListBidingCombo.get(comboSupplier.getSelectedIndex());
     }
     curryProductPojo.isPriceModiefied = true;
     curryProductPojo.isDefaultPriceModified = true;
     curryProductPojo.isPurchasePriceModified = true;

     }

     public void setDialogFields() {
     editProductId.setText(curryProductPojo.productId);
     editQtyOnHand.setText(quantityOnHand.toString());
     editProductName.setText(curryProductPojo.productName);
     editInternalName.setText(curryProductPojo.internalName);

     comboBrand.setSelectedItem(curryProductPojo.brandName);

     editListPrice.setText(curryProductPojo.price.toString());
     editBasePrice.setText(curryProductPojo.purchasePrice.toString());
     editCurrency.setText((String) session.getAttribute("currency"));
     editSupplierCode.setText(curryProductPojo.supplierProductId);
     editScanCode.setText(curryProductPojo.eanValue);

     if (supplierListBidingCombo.contains(curryProductPojo.supplierPartyId)) {
     comboSupplier.setSelectedIndex(supplierListBidingCombo.indexOf(curryProductPojo.supplierPartyId));
     }
     comboDepartment.setSelectedItem(curryProductPojo.description);

     }

     protected boolean save() {

     getDialogFields();
     String facilityId = (String) session.getAttribute("facilityId");
     if (facilityListBidingCombo.contains(facilityId)) {
     facilityId = facilityListBidingCombo.get(comboFacility.getSelectedIndex());

     }
     curryProductPojo = PosProductHelper.prepareProduct(curryProductPojo, facilityId);
     curryProductPojo = PosProductHelper.prepareProductPrice(curryProductPojo, facilityId);
     curryProductPojo.categoryId = getDepartmentCode();
     curryProductPojo.categoryName = getDepartmentName();
     curryProductPojo.brandId = curryProductPojo.categoryId + "_" + ((String) comboBrand.getSelectedItem()).replaceAll("\\s", "");

     if (curryProductPojo.brandId != null && curryProductPojo.brandId.length() > 20) {
     curryProductPojo.brandId = curryProductPojo.brandId.substring(0, 19);
     }

     return updateProduct(curryProductPojo);

     }

     public boolean isValidFields() {
     if (editProductId.getText().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Product Code is empty. Please enter valid code");
     editProductId.selectAll();
     return false;
     } else if (editProductName.getText().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Product Name is empty. Please enter description");
     editProductName.selectAll();
     return false;
     } else if (editInternalName.getText().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Product Internal Name is empty. Please enter internal name");
     editProductName.selectAll();
     return false;
     } else if (comboSupplier.getSelectedIndex() == -1) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Supplier Name is empty. Please enter supplier name");
     editProductName.selectAll();
     return false;
     } else if (editBasePrice.getText().trim().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Purchase price is empty. Please enter purchase price");
     editProductName.selectAll();
     return false;
     } else if (editListPrice.getText().trim().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Selling price is empty. Please enter selling price");
     editListPrice.selectAll();
     return false;
     } else if (editSupplierCode.getText().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null,
     "Supplier code is empty. Please enter supplier code");
     editProductName.selectAll();
     return false;
     } else if (editScanCode.getText().isEmpty()) {
     OrderMaxOptionPane.showMessageDialog(null, "Scan code is empty. Please enter scan code");
     editProductName.selectAll();
     return false;
     }

     //        m_pos.getPromoStatusBar().displayMessage("Save Product data is valid");

     return true;
     }
     */
    /*
     //add to product id map so we know its exisiting 
     //in database
     void addToProductCodeKeyMap(String productidd) {

     try {
     Integer code = new Integer(productidd.substring(2, 6));
     String n = productidd.substring(0, 1);
     String b = productidd.substring(1, 2);

     boolean result = false;
     if (codeIdMap.containsKey(n)) {
     //                //Debug.logInfo("trying brand contains: " + b, module);
     Map<String, ProductCodeId> map = codeIdMap.get(n);//  codeIdMap = FastMap.newInstance();
     if (map.containsKey(b)) {
     ProductCodeId id = map.get(b);
     for (Integer idNum : id.itemList) {
     if (idNum == code) {
     result = true;
     break;
     }
     }

     if (result == false) {
     id.itemList.add(code);
     Collections.sort(id.itemList);
     }
     } else {
     ProductCodeId id = new ProductCodeId();
     id.itemList = new ArrayList<Integer>();//map.get(b);
     id.itemList.add(code);
     id.ProductIdPrefix = b;
     id.BrandName = b;
     map.put(b, id);
     }
     } else {
     ProductCodeId id = new ProductCodeId();
     id.itemList = new ArrayList<Integer>();//map.get(b);
     id.itemList.add(code);
     id.ProductIdPrefix = b;
     id.BrandName = b;
     Map<String, ProductCodeId> map = FastMap.newInstance();
     map.put(b, id);
     codeIdMap.put(n, map);
     }
     } catch (Exception e) {
     //Debug.logError(e, module);
     }
     }

     public synchronized void eventGenerate() {
     if (editProductId != null) {

     String departmentId = (String) comboDepartment.getSelectedItem();
     String brandId = (String) comboBrand.getSelectedItem();
     String name = editInternalName.getText().trim();

     editProductId.setText(generateProductCode(departmentId, brandId, name));
     }
     }

     protected Integer getNextProductCode(String name, String brand, Integer code) {

     Integer first = code;
     if (codeIdMap.containsKey(name)) {
     Map<String, ProductCodeId> map = codeIdMap.get(name);// codeIdMap =
     // FastMap.newInstance();
     if (map.containsKey(brand)) {
     ProductCodeId id = map.get(brand);

     for (Integer idNum : id.itemList) {
     //Debug.logInfo("getNextProductCode: idNum: " + idNum.toString() + " first: " + first.toString(), module);
     if (first < idNum) {
     break;
     } else {
     first += 1;
     }
     }
     }
     }
     return first;
     }

     String generateProductCode(String departmentId, String brandId, String name) {

     String departmentCode = null;

     if (UtilValidate.isNotEmpty(departmentId)) {
     departmentCode = departmentListBidingCombo.get(comboDepartment
     .getSelectedIndex());
     }

     String productCode = departmentCode;

     if (departmentCode != null && brandId != null) {

     String n = name.toUpperCase().substring(0, 1);
     String b = brandId.toUpperCase().substring(0, 1);
     Integer code = getNextProductCode(n, b, new Integer(departmentCode));
     String format = String.format("%%0%dd", 4);
     String result = String.format(format, code);

     return n + b + result;
     }
     return productCode;
     }

     String getDepartmentCode() {

     String departmentCode = null;

     if (comboDepartment.getSelectedIndex() > -1) {
     departmentCode = departmentListBidingCombo.get(comboDepartment.getSelectedIndex());
     }

     return departmentCode;
     }

     String getDepartmentName() {

     String departmentName = null;

     if (comboDepartment.getSelectedItem() != null) {
     departmentName = comboDepartment.getSelectedItem().toString();
     }

     return departmentName;
     }

     public synchronized void eventCopyName() {

     editInternalName.setText(editProductName.getText());
     }

     public synchronized void eventCopyProductIdToSupplierProductId() {

     editSupplierCode.setText(editProductId.getText());

     }

     public synchronized void eventGenerateScanCode() {

     //Debug.logInfo("eventGenerateScanCode 1  ", module);
     String prodId = editProductId.getText();
     //Debug.logInfo("eventGenerateScanCode 2  ", module);
     String scanCode = generateScanCode(prodId);
     //Debug.logInfo("eventGenerateScanCode 4  ", module);

     //Debug.logInfo("eventGenerateScanCode 5  ", module);

     editScanCode.setText(scanCode);

     }

     public String generateScanCode(String productId) {

     String scanCode = "629" + getUniqueScanCode(productId);
     addScanProductToKeyMap(productId, scanCode);
     return scanCode;
     }

     String getUniqueScanCode(String prodId) {

     String scanCode = "";
     String alpha = "";
     String numaric = "";

     for (int i = 0; i < prodId.length(); ++i) {
     String code = prodId.substring(i, i + 1);
     String val = PosProductHelper.phoneDialAlphaToNumaric(code);
     scanCode += val;

     if (code == val) {
     numaric += code;
     } else {
     alpha += code;
     }
     }

     if (isScanIdExists(scanCode)) {
     //Debug.logInfo("isScanIdExists  " + scanCode, module);
     try {
     Integer intVal = new Integer(numaric) + 1;

     if (intVal < 1000) {
     prodId = alpha + "0" + intVal.toString();
     } else {
     prodId = alpha + intVal.toString();
     }
     //Debug.logInfo("prodId new:   " + prodId, module);
     scanCode = getUniqueScanCode(prodId);

     } catch (NumberFormatException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }


     }

     return scanCode;
     }

     public void addScanProductToKeyMap(String productidd, String scanId) {

     scanIdMap.put(productidd, scanId);
     }

     public String getScanIdFromProductId(String productidd) {

     if (scanIdMap.containsKey(productidd) == true) {
     return scanIdMap.get(productidd);
     } else {
     return null;
     }
     }

     public boolean isScanIdExists(String scanId) {

     Enumeration<String> enumKey = scanIdMap.keys();
     while (enumKey.hasMoreElements()) {
     String key = enumKey.nextElement();
     String val = scanIdMap.get(key);
     if (val.equals(scanId)) {
     return true;
     }
     }

     return false;
     }

     public boolean isScanProductIdExists(String productidd) {
     return scanIdMap.containsKey(productidd);
     }

     static public boolean createNewProductWithSupplier(ProductInventoryPojo prodPojo) {


     Delegator delegator = (Delegator) session.getDelegator();

     // check productId if null then skip creating inventory item
     // too.
     boolean productExists = PosProductHelper.checkProductExists(prodPojo.productId, delegator);
     if (productExists) {
     //            m_pos.getPromoStatusBar().displayMessage("Product data exists not saved....");
     return true;
     }

     Timestamp now = UtilDateTime.nowTimestamp();


     Map<String, GenericValue> toStore;
     try {

     toStore = PosProductHelper.decodeProductMap(prodPojo, now, delegator);

     if (toStore == null) {
     //Debug.logWarning("Faild to import product[" + prodPojo.productId + "] because data was bad.  Check preceding warnings for reason.", module);
     }

     GenericValue productGV = delegator.makeValue("Product", toStore.get("Product"));
     GenericValue productPriceGV = delegator.makeValue("ProductPrice", toStore.get("ProductPrice"));
     GenericValue productPriceListGV = delegator.makeValue("ProductPrice", toStore.get("ListPrice"));
     GenericValue productAverageCostGV = delegator.makeValue("ProductPrice", toStore.get("AverageCost"));
     GenericValue averageCostGV = delegator.makeValue("ProductAverageCost", toStore.get("ProductAverageCost"));


     GenericValue goodIdficationGV = null;
     if (toStore.containsKey("GoodIdentification")) {
     goodIdficationGV = delegator.makeValue("GoodIdentification", toStore.get("GoodIdentification"));
     }

     GenericValue goodIdficationGV1 = null;
     if (toStore.containsKey("GoodIdentification1")) {
     goodIdficationGV1 = delegator.makeValue("GoodIdentification", toStore.get("GoodIdentification1"));
     }

     GenericValue supplierItemGV = delegator.makeValue("SupplierProduct", toStore.get("SupplierProduct"));

     if (!ImportProductHelper.checkProductExists(productGV.getString("productId"), delegator)) {
     try {
     //product
     try {
     delegator.create(productGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[productGV] " + productGV, module);
     //Debug.logError(e2, module);
     }

     try {
     delegator.create(productPriceGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[productPriceGV] " + productPriceGV, module);
     // TODO Auto-generated catch block
     //Debug.logError(e2, module);
     }

     try {
     delegator.create(productAverageCostGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[productAverageCostGV] " + productAverageCostGV, module);
     // TODO Auto-generated catch block
     //Debug.logError(e2, module);
     }

     try {
     delegator.create(productPriceListGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[productPriceListGV] " + productPriceListGV, module);
     // TODO Auto-generated catch block
     //Debug.logError(e2, module);
     }

     try {
     delegator.create(averageCostGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[averageCostGV] " + averageCostGV, module);
     // TODO Auto-generated catch block
     //Debug.logError(e2, module);
     }

     if (goodIdficationGV != null) {
     try {
     delegator.create(goodIdficationGV);
     } catch (Exception e2) {
     //Debug.logInfo("Save product and inventory failed[goodIdficationGV] " + goodIdficationGV, module);
     // TODO Auto-generated catch block
     //Debug.logError(e2, module);
     }
     }

     if (goodIdficationGV1 != null) {

     try {
     delegator.create(goodIdficationGV1);
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[goodIdficationGV1] " + goodIdficationGV1, module);
     //Debug.logError(e2, module);
     }
     }

     try {
     delegator.create(supplierItemGV);
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[supplierProductGV] " + supplierItemGV, module);
     //Debug.logError(e2, module);
     }


     try {
     delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, "MS_CAT_PROMOTIONS", delegator));
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[createProductCategoryMember] " + supplierItemGV, module);
     //Debug.logError(e2, module);
     }

     try {
     //    					String categoryId = getDepartmentCode();
     String parentProductCategoryId = ProductDataTreeLoader.CategoryRootId;

     GenericValue departmentCategory = PosProductHelper.getProductCategory(prodPojo.categoryId, delegator);
     if (departmentCategory == null) {
     delegator.create(PosProductHelper.createProductCategory(prodPojo.categoryId, parentProductCategoryId, "CATALOG_CATEGORY", prodPojo.categoryName, delegator));
     }
     GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
     if (rollUp == null) {
     rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
     delegator.create(rollUp);
     }

     //    					String brandId = ((String) comboBrand.getSelectedItem()).substring(5);//getBrandCode();

     GenericValue brandCategory = PosProductHelper.getProductCategory(prodPojo.brandId, delegator);
     if (brandCategory == null) {
     delegator.create(PosProductHelper.createProductCategory(prodPojo.brandId, prodPojo.categoryId, "CATALOG_CATEGORY", prodPojo.brandName, delegator));
     }

     rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
     if (rollUp == null) {
     rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
     delegator.create(rollUp);
     //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);
     }

     GenericValue catMember = PosProductHelper.getProductCategoryMember(prodPojo.productId, prodPojo.brandId, delegator);
     if (catMember == null) {
     delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, prodPojo.brandId, delegator));
     }

     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[createProductCategoryMember] " + supplierItemGV, module);
     //Debug.logError(e2, module);
     }

     String contentId = "IMAGEMANAGEMENT_MAIN";// delegator.getNextSeqId("ProductContent");;
     try {
     delegator.create(PosProductHelper.createProductContent(prodPojo.productId, contentId, delegator));
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[createProductContent] " + supplierItemGV, module);
     //Debug.logError(e2, module);
     }


     String demensionId = delegator.getNextSeqId("ProductDimension");;
     try {
     delegator.create(PosProductHelper.createProductDimension(prodPojo.productId, demensionId, prodPojo.internalName, delegator));
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[createProductContent] " + supplierItemGV, module);
     //Debug.logError(e2, module);
     }


     } finally {
     //			            //Debug.logError(UtilProperties.getMessage(resource, 
     //			                    "ProductProductImportCannotStoreProduct", locale), module);
     //                    m_pos.getPromoStatusBar().displayMessage("Saved new product....");
     }

     //			    }
     } else {
     //Debug.logInfo("Product Exists nothing done - DELETE PRODUCT PLEASE!!!!!!!!!!!!!!!!!!! ", module);
     }
     } catch (GenericEntityException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
     } catch (Exception e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
     }

     return true;
     }

     static public boolean updateProduct(ProductInventoryPojo prodPojo) {

     // if the product exists then update
     if (PosProductHelper.isProductExists(prodPojo.productId, session.getDelegator())) {
     Delegator delegator = session.getDelegator();

     GenericValue valProd = PosProductHelper.getProduct(prodPojo.productId, delegator);
     if (valProd != null) {
     try {
     PosProductHelper.updateProduct(valProd, prodPojo, delegator);

     } catch (Exception e) {
     //Debug.logError(e, module);
     return false;
     }
     }

     //save price
     PosProductHelper.productPriceSave(prodPojo, prodPojo.currencyUomId, delegator);

     // supplier product
     GenericValue supplierVal = PosProductHelper.getSupplierProduct(
     prodPojo.productId, prodPojo.supplierPartyId, delegator);

     if (supplierVal == null) {

     boolean result = PosProductHelper.createSupplierProduct(prodPojo.productId,
     prodPojo.supplierPartyId, prodPojo.purchasePrice.toString(),
     prodPojo.supplierProductId, prodPojo.currencyUomId,
     delegator);
     if (!result) {
     return result;
     }

     }

     GenericValue giftEAN = PosProductHelper.getGoodIdentificationType(
     prodPojo.productId, PosProductHelper.GoodIdentificationTypeIdEAN, session.getDelegator());
     if (giftEAN != null) {

     boolean result = PosProductHelper.updateGoodIdentificationType(
     giftEAN, prodPojo.eanValue, delegator);
     if (!result) {
     return result;
     }

     } else {

     boolean result = PosProductHelper.createGoodIdentificationType(
     prodPojo.productId, PosProductHelper.GoodIdentificationTypeIdEAN,
     prodPojo.eanValue, delegator);
     if (!result) {
     return result;
     }

     }

     try {

     String promoCat = "MS_CAT_PROMOTIONS";
     GenericValue catMember = PosProductHelper.getProductCategoryMember(prodPojo.productId, promoCat, delegator);
     if (catMember == null) {
     delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, promoCat, delegator));
     }
     } catch (Exception e2) {
     // TODO Auto-generated catch block
     //Debug.logInfo("Save product and inventory failed[createProductCategoryMember] ", module);
     //Debug.logError(e2, module);
     }

     try {

     String parentProductCategoryId = ProductDataTreeLoader.CategoryRootId;

     GenericValue departmentCategory = PosProductHelper.getProductCategory(prodPojo.categoryId, delegator);
     if (departmentCategory == null) {
     delegator.create(PosProductHelper.createProductCategory(prodPojo.categoryId, parentProductCategoryId, "CATALOG_CATEGORY", prodPojo.categoryName, delegator));
     }
     GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
     if (rollUp == null) {
     rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
     delegator.create(rollUp);
     }

     GenericValue brandCategory = PosProductHelper.getProductCategory(prodPojo.brandId, delegator);
     if (brandCategory == null) {
     delegator.create(PosProductHelper.createProductCategory(prodPojo.brandId, prodPojo.categoryId, "CATALOG_CATEGORY", prodPojo.brandName, delegator));
     }

     rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
     if (rollUp == null) {
     rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
     delegator.create(rollUp);
     }


     try {
     GenericValue catMember = PosProductHelper.getProductCategoryMember(prodPojo.productId, prodPojo.brandId, delegator);
     if (catMember == null) {
     delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, prodPojo.brandId, delegator));
     }

     } catch (Exception e2) {
     //Debug.logError(e2, module);
     }


     } catch (Exception e2) {
     // TODO Auto-generated catch block						
     //Debug.logError(e2, module);
     }

     String contentId = "IMAGEMANAGEMENT_MAIN";// delegator.getNextSeqId("ProductContent");;
     try {
     delegator.create(PosProductHelper.createProductContent(prodPojo.productId, contentId, delegator));
     } catch (Exception e2) {
     //Debug.logError(e2, module);
     }


     String demensionId = delegator.getNextSeqId("ProductDimension");;
     try {
     delegator.create(PosProductHelper.createProductDimension(prodPojo.productId, demensionId, prodPojo.internalName, delegator));
     } catch (Exception e2) {
     //Debug.logError(e2, module);
     }

     //            m_pos.getPromoStatusBar().displayMessage("Updated Product data....");

     } else {
     createNewProductWithSupplier(prodPojo);
     }

     return true;

     }

     public List<ProductImportPojo> generateSpiceProductIds() {
     List<ProductImportPojo> list = FastList.newInstance();

     String productId = editProductId.getText();
     String departmentId = (String) comboDepartment.getSelectedItem();
     String brandId = (String) comboBrand.getSelectedItem();
     String name = editInternalName.getText().trim();

     ProductImportPojo currPojo1 = new ProductImportPojo();
     BigDecimal sellPrice = new BigDecimal(editListPrice.getText());
     BigDecimal purPrice = new BigDecimal(editBasePrice.getText());

     //1kg
     currPojo1.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo1.Item = editProductName.getText() + " 1KG";
     currPojo1.SellingPrice = editListPrice.getText();
     currPojo1.PurchasePrice = editBasePrice.getText();
     currPojo1.BarcodeItem = generateScanCode(currPojo1.ProductId);

     currPojo1.priceCurrencyUomId = editCurrency.getText();
     currPojo1.DEPARTMENT = (String) comboDepartment.getSelectedItem();
     currPojo1.Brand = (String) comboBrand.getSelectedItem();
     if (comboSupplier.getSelectedIndex() > 0) {
     currPojo1.supplierPartyId = supplierListBidingCombo.get(comboSupplier.getSelectedIndex());
     }
     addToProductCodeKeyMap(currPojo1.ProductId);
     list.add(currPojo1);

     //500gm
     ProductImportPojo currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " 500GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(2)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(2)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;

     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);


     //200gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " 200GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(5)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(5)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     //100gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " 100GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(10)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(10)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     //50gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " 50GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(20)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(20)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     return list;
     }

     public List<ProductImportPojo> generateSpicePowderProductIds() {
     List<ProductImportPojo> list = FastList.newInstance();

     String productId = editProductId.getText();
     String departmentId = (String) comboDepartment.getSelectedItem();
     String brandId = (String) comboBrand.getSelectedItem();
     String name = editInternalName.getText().trim();



     ProductImportPojo currPojo1 = new ProductImportPojo();
     BigDecimal sellPrice = new BigDecimal(editListPrice.getText());
     BigDecimal purPrice = new BigDecimal(editBasePrice.getText());

     //1kg
     currPojo1.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo1.Item = editProductName.getText() + " POWDER 1KG";
     currPojo1.SellingPrice = editListPrice.getText();
     currPojo1.PurchasePrice = editBasePrice.getText();
     currPojo1.BarcodeItem = generateScanCode(currPojo1.ProductId);

     currPojo1.priceCurrencyUomId = editCurrency.getText();
     currPojo1.DEPARTMENT = (String) comboDepartment.getSelectedItem();
     currPojo1.Brand = (String) comboBrand.getSelectedItem();
     if (comboSupplier.getSelectedIndex() > 0) {
     currPojo1.supplierPartyId = supplierListBidingCombo.get(comboSupplier.getSelectedIndex());
     }
     addToProductCodeKeyMap(currPojo1.ProductId);
     list.add(currPojo1);

     //500gm
     ProductImportPojo currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " POWDER 500GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(2)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(2)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;

     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);


     //200gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " POWDER 200GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(5)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(5)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     //100gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " POWDER 100GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(10)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(10)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     //50gm
     currPojo = new ProductImportPojo();
     currPojo.ProductId = generateProductCode(departmentId, brandId, name);
     currPojo.Item = editProductName.getText() + " POWDER 50GM";
     currPojo.SellingPrice = sellPrice.divide(new BigDecimal(20)).add(new BigDecimal(0.5)).toString();
     currPojo.PurchasePrice = purPrice.divide(new BigDecimal(20)).add(new BigDecimal(0.5)).toString();
     currPojo.BarcodeItem = generateScanCode(currPojo.ProductId);
     currPojo.priceCurrencyUomId = currPojo1.priceCurrencyUomId;
     currPojo.DEPARTMENT = currPojo1.DEPARTMENT;
     currPojo.Brand = currPojo1.Brand;
     currPojo.supplierPartyId = currPojo1.supplierPartyId;
     addToProductCodeKeyMap(currPojo.ProductId);
     list.add(currPojo);

     return list;
     }

     protected String getCSVLine(ProductImportPojo currPojo) {
     String line = new String();

     if (currPojo != null) {
     line = currPojo.ProductId + ",";
     line += currPojo.BarcodeItem + ",";
     line += currPojo.Item + ",";
     line += currPojo.Item + ",";
     line += currPojo.SellingPrice + ",";
     line += currPojo.PurchasePrice + ",";
     line += currPojo.SellingPrice + ",";
     line += currPojo.DEPARTMENT + ",";
     line += currPojo.Brand + ",";
     line += currPojo.productTypeId + ",";
     line += currPojo.priceCurrencyUomId + ",";

     line += currPojo.supplierPartyId + ",";

     //	    line += editSupplierCode.getText() + ",";	         	         
     line += currPojo.INV_ITEM_ID + ",";
     line += currPojo.facilityId + ",";
     line += currPojo.availableToPromise + ",";
     line += currPojo.onHand + ",";
     line += currPojo.minimumStock + ",";
     line += currPojo.reorderQuantity + ",";
     line += currPojo.daysToShip + ",";

     //	    XEdit editAvailableToPromise = null;	
     }

     return line;
     }

     public synchronized void eventGenerateChildIds() {

     try {
     //Debug.logInfo("eventGenerateChildIds", module);
     List<ProductImportPojo> list = generateSpiceProductIds();
     for (ProductImportPojo currPojo : list) {
     String line = getCSVLine(currPojo);
     //Debug.logInfo(line, module);
     }
     } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();


     }
     }
     */

    class InteractiveRenderer extends DefaultTableCellRenderer {

        protected int interactiveColumn;
        protected KeyTableModel model = null;
        protected JTable jtable = null;

        public InteractiveRenderer(int interactiveColumn, KeyTableModel tabModel, JTable table) {
            this.interactiveColumn = interactiveColumn;
            model = tabModel;
            jtable = table;
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((model.getRowCount() - 1) == row
                        && !model.hasEmptyRow()) {
                    model.addEmptyRow();
                }

                this.highlightLastRowInner(row);
                //        rowChanged(row);
            }

            return c;
        }

        public void highlightLastRowInner(int row) {
            int lastrow = model.getRowCount();
            if (row == lastrow - 1) {
                jtable.setRowSelectionInterval(lastrow - 1, lastrow - 1);
            } else {
                jtable.setRowSelectionInterval(row + 1, row + 1);
            }

            jtable.setColumnSelectionInterval(0, 0);
        }
    }
    /*
     public void highlightLastRow(int row) {
     int lastrow = tableModel.getRowCount();
     if (row == lastrow - 1) {
     tableDepartmentTable.setRowSelectionInterval(lastrow - 1, lastrow - 1);
     } else {
     tableDepartmentTable.setRowSelectionInterval(row + 1, row + 1);
     }

     tableDepartmentTable.setColumnSelectionInterval(0, 0);


     }
     */

    public class InteractiveTableModelListener implements TableModelListener {

        protected JTable jtable = null;

        public InteractiveTableModelListener(JTable table) {
            jtable = table;
        }

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
    /*
     public void setColumnWidth(int[] columnWidth) {
     TableColumn column = null;
     for (int i = 0; i < columnWidth.length; i++) {
     column = tableDepartmentTable.getColumnModel().getColumn(i);
     //            if (i == 2) {
     column.setPreferredWidth(columnWidth[i]); //third column is bigger
     //            } else {
     //                column.setPreferredWidth(50);
     //            }
     }
     }

     public void reloadItemDataModel(List<Key> cutdownList) {

     tableModel = new KeyTableModel(columnNames);
     tableModel.addTableModelListener(new ProductDetailEditDialog.InteractiveTableModelListener(tableDepartmentTable));
     tableDepartmentTable.setModel(tableModel);
     tableDepartmentTable.setSurrendersFocusOnKeystroke(true);
     //tableDepartmentTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));        
     setColumnWidth(width);
     TableColumn hidden = tableDepartmentTable.getColumnModel().getColumn(KeyTableModel.HIDDEN_INDEX);
     hidden.setMinWidth(2);
     hidden.setPreferredWidth(2);
     hidden.setMaxWidth(2);
     hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX, tableModel, tableDepartmentTable));
     tableDepartmentTable.setPreferredSize(new java.awt.Dimension(500, (cutdownList.size()) * tableDepartmentTable.getRowHeight()));
     tableModel.addRows(cutdownList);
     if (!tableModel.hasEmptyRow()) {
     tableModel.addEmptyRow();
     }
     }
     */
    /*
     public void reloadBrandItemDataModel(List<Key> brandList) {

     brandTableModel = new KeyTableModel(brandColumnNames);
     brandTableModel.addTableModelListener(new ProductDetailEditDialog.InteractiveTableModelListener(tableBrand));
     tableBrand.setModel(brandTableModel);
     tableBrand.setSurrendersFocusOnKeystroke(true);
     //tableDepartmentTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));        
     setColumnWidth(width);
     TableColumn hidden = tableBrand.getColumnModel().getColumn(KeyTableModel.HIDDEN_INDEX);
     hidden.setMinWidth(2);
     hidden.setPreferredWidth(2);
     hidden.setMaxWidth(2);
     hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX, brandTableModel, tableBrand));
     tableBrand.setPreferredSize(new java.awt.Dimension(500, (brandList.size()) * tableBrand.getRowHeight()));
     brandTableModel.addRows(brandList);
     if (!brandTableModel.hasEmptyRow()) {
     brandTableModel.addEmptyRow();
     }
     }

     public void clearProductDialogFields() {

     editProductId.setText("");
     editProductName.setText("");
     editInternalName.setText("");
     editListPrice.setText("");
     editPurchasePrice.setText("");
     editBasePrice.setText("");
     editCurrency.setText((String) session.getAttribute("currency"));
     editQtyOnHand.setText("1");
     editSupplierCode.setText("");
     editScanCode.setText("");
     editSupplierLastPrice.setText("");
     */
    // initialize the journal table header
/*        XModel jmodel = productDialogTable.createModel();
     if (jmodel != null) {
     productDialogTable.appendEmpty(jmodel);
     jtable.setModel(jmodel);
     productDialogTable.setColumnWidth(jmodel);
     }

     jtable.setSelectedRow(0);
     */
    /*  }

     public void clearBrandDialogFields() {
     editBrandId.setText("");
     editBrandName.setText("");
     }

     public void clearDepartmentDialogFields() {
     editDepartmentId.setText("");
     editDepartmentName.setText("");
     }
     */

    protected TreeNode GetRecusevilyNodeType(TreeNode parent, TreePath tp, String nodeType) {
        TreeNode child = null;
        if (parent.getNodeName().equals(nodeType)) {
            child = parent;
        }

        if (child == null) {

            TreePath parentPath = tp.getParentPath();
            if (parentPath != null) {
                TreeNode currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
                child = GetRecusevilyNodeType(currTreeParentNode, parentPath, nodeType);
            }
            //          }
        }
        return child;
    }

    void loadProductTree() {
        /*        TreePath path = jTree1.getSelectionPath();
         ProductDataTreeLoader m_productsArray = new ProductDataTreeLoader(session);
         m_productsArray.setProductLoaded(false);
         m_productsArray.loadList();
         //        prodRootNose= new ProductTreeNode("rootId", "Root Node", false, true, "facility");
         rootNode = m_productsArray.getRootNode();
         productTreeModel = new ProductTreeModel(rootNode, m_productsArray);
         jTree1.setModel(productTreeModel);

         departmentListBidingCombo.clear();
         DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
         DefaultComboBoxModel comboDepartmentBrandModel = new DefaultComboBoxModel();
         HashMap<String, Key> departmentMap = m_productsArray.getAllDepartments();
         Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap);
         Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
         for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
         comboDepartmentModel.addElement(entryDept.getValue()._name);
         comboDepartmentBrandModel.addElement(entryDept.getValue()._name);
         departmentListBidingCombo.add(entryDept.getValue()._id);
         treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());

         }
         comboDepartment.setModel(comboDepartmentBrandModel);
         comboProductDepName.setModel(comboDepartmentModel);
         jTree1.scrollPathToVisible(path);
         * */
    }

    public void expandToLast(JTree tree) {
        // expand to the last leaf from the root
/*        TreePath path = tree.getSelectionPath();
         DefaultMutableTreeNode root;
         root = (DefaultMutableTreeNode) tree.getModel().getRoot();
         tree.scrollPathToVisible(new TreePath(root.getLastLeaf().getPath()));
         */
    }
}
