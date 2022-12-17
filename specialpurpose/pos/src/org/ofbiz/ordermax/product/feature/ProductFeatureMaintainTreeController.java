/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.feature;

import org.ofbiz.ordermax.price.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ActionTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.ThreePanelContainerDlg;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductFeature;
import org.ofbiz.ordermax.entity.ProductFeatureAndAppl;
import org.ofbiz.ordermax.entity.ProductFeatureAppl;
import org.ofbiz.ordermax.entity.ProductFeatureApplType;
import org.ofbiz.ordermax.generic.EntityPersistanceFactory;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import static org.ofbiz.ordermax.payment.sales.CustomerPaymentGroupInvoiceController.caption;
import org.ofbiz.ordermax.product.ProductMaintainTreeController;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.ProductSelectionPanelFrame;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author administrator
 */
public class ProductFeatureMaintainTreeController extends BaseMainScreen {

    public static final String module = ProductFeatureMaintainTreeController.class.getName();
    protected List<String> brandMap = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();
    protected ProductFeatureEditTablePanel productFeatureEditTablePanel = null;
    ProductSelectionPanelFrame catalogTreeSelPanel;
    javax.swing.JTabbedPane tabbedPanePrice = null;

    static public ProductFeatureMaintainTreeController runController(ControllerOptions options) {

        ProductFeatureMaintainTreeController controller = new ProductFeatureMaintainTreeController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ProductFeatureMaintainTreeController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ProductMaintainTreeController.module, options.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options;

    public ProductFeatureMaintainTreeController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 200;
        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);

        }
        contFrame.setLocation(x, y);
    }

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        setCaption("Price Detail");

        tabbedPanePrice = new javax.swing.JTabbedPane();

        productFeatureEditTablePanel = new ProductFeatureEditTablePanel(ControllerOptions.getSession());
        catalogTreeSelPanel = new ProductSelectionPanelFrame(new ControllerOptions());
        catalogTreeSelPanel.setSelectVirtual(true);
        brandMap = FastList.newInstance();
        departmentMap = FastMap.newInstance();

        PriceButtonPanel buttonPanel = new PriceButtonPanel();
        f.getPanelDetail().setLayout(new BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, tabbedPanePrice);
        tabbedPanePrice.add("Product Selection", catalogTreeSelPanel);
        tabbedPanePrice.add("Product Feature", productFeatureEditTablePanel);
        tabbedPanePrice.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 1) {
                        if (catalogTreeSelPanel.isSelectionChanged()) {
                            addToPriceEditTablePanel();
                            catalogTreeSelPanel.setSelectionChanged(false);
                        }
                    }
                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        productFeatureEditTablePanel.btnLoadPrices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //productFeatureEditTablePanel.saveItem();
                    Map<String, Object> whereClauseMap = new HashMap<String, Object>();
                    featureList.clear();
                    //ProductFeatureType> productFeatureTypeModel = null;
                    //public JGenericComboBoxSelectionModel<ProductFeatureCategory> productFeatureCategoryModel
                    if (productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem() != null) {
                        whereClauseMap.put("productFeatureTypeId", productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem().getproductFeatureTypeId());
                    }

                    if (productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem() != null) {
                        whereClauseMap.put("productFeatureCategoryId", productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem().getproductFeatureCategoryId());
                    }

                    addToPriceEditTablePanel();

                    //whereClauseMap.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
                    List<GenericValue> sumValList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ProductFeature", whereClauseMap, "productFeatureId", ControllerOptions.getSession().getDelegator());
                    for (GenericValue val : sumValList) {
                        ProductFeature feaure = new ProductFeature(val);
                        featureList.put(feaure.getdescription(), feaure);
                    }
                    productFeatureEditTablePanel.setFeatureList(featureList);
                    productFeatureEditTablePanel.setupEditOrderTable();
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        productFeatureEditTablePanel.btnLoadNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //productFeatureEditTablePanel.saveItem();
                    Map<String, Object> whereClauseMap = new HashMap<String, Object>();
                    featureList.clear();
                    //ProductFeatureType> productFeatureTypeModel = null;
                    //public JGenericComboBoxSelectionModel<ProductFeatureCategory> productFeatureCategoryModel
                    if (productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem() != null) {
                        whereClauseMap.put("productFeatureTypeId", productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem().getproductFeatureTypeId());
                    }

                    if (productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem() != null) {
                        whereClauseMap.put("productFeatureCategoryId", productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem().getproductFeatureCategoryId());
                    }

                    addNewToPriceEditTablePanel();

                    //whereClauseMap.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
                    List<GenericValue> sumValList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ProductFeature", whereClauseMap, "productFeatureId", ControllerOptions.getSession().getDelegator());
                    for (GenericValue val : sumValList) {
                        ProductFeature feaure = new ProductFeature(val);
                        featureList.put(feaure.getdescription(), feaure);
                    }
                    productFeatureEditTablePanel.setFeatureList(featureList);
                    productFeatureEditTablePanel.setupEditOrderTable();
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ControllerOptions optionsVal = new ControllerOptions(options);
                    optionsVal.put("EntityName", "ProductFeatureApplBulk");
                    for (ProductFeatureApplBulkUpdate bulkUpdate : selectedProductsPrices.getList()) {
                        EntityPersistanceFactory.saveEntity(bulkUpdate, optionsVal);
                    }
//                    productFeatureEditTablePanel.saveItem();
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.getReloadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productFeatureEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productFeatureEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                addToPriceEditTablePanel();
                //productFeatureEditTablePanel.loadItem();
            }
        });

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (productFeatureEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productFeatureEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                f.okButtonPressed();

            }
        });

        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (productFeatureEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productFeatureEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                f.cancelButtonPressed();
            }
        });

        buttonPanel.getClearSelectionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (productFeatureEditTablePanel.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            productFeatureEditTablePanel.saveItem();
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                addToPriceEditTablePanel();
            }
        });

    }

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (treePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader m_productsArray = new CatalogCategoryDataTree(tmpCategoryId);
            m_productsArray.setProductLoaded(false);
            m_productsArray.setLazyLoad(true);
            m_productsArray.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(m_productsArray.getRootNode());
            treePanel.getTree().setModel(productTreeModel);
        }
    }

    public void loadScreenDialog() {

        ThreePanelContainerDlg f = new ThreePanelContainerDlg(new javax.swing.JFrame(), true);
        f.setTitle("Product Edit");
        loadScreen(f);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        f.setVisible(true);
    }

    @Override
    public void addItem(String id) throws Exception {
        productFeatureEditTablePanel.addItem(id);
    }

    @Override
    public void refreshScreen() {
        productFeatureEditTablePanel.refreshScreen();
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        panel.addItem(id, price, qty);
    }

    @Override
    protected void productTreeMouseClicked(java.awt.event.MouseEvent evt) {
        JTree jTree = treePanel.getTree();
        TreePath tp = null;
        tp = jTree.getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                node.tp = tp;
                node.tp = null;
            }
        }
    }

    //refresh combos
    private ListAdapterListModel<ProductFeatureApplBulkUpdate> selectedProductsPrices = new ListAdapterListModel<ProductFeatureApplBulkUpdate>();

    protected void addToPriceEditTablePanel() {
        selectedProductsPrices.clear();

        if (productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem() != null
                && productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem() != null) {
            for (Product prod : catalogTreeSelPanel.getModel().getList()) {
                //selectedProductsPrices.add(new ProductFeatureApplBulkUpdate(prod));
                selectedProductsPrices.addAll(loadProductFeatureList(prod,
                        productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem().getproductFeatureTypeId(),
                        productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem().getproductFeatureCategoryId()));
            }
            //loadProductFeatureList(selectedProductsPrices,
            //        productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem().getproductFeatureTypeId(),
            //        productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem().getproductFeatureCategoryId());

        }

        productFeatureEditTablePanel.setProductList(catalogTreeSelPanel.getModel());
        productFeatureEditTablePanel.setProductPriceList(selectedProductsPrices);
        productFeatureEditTablePanel.loadItem();
    }

    protected void addNewToPriceEditTablePanel() {
        selectedProductsPrices.clear();

        if (productFeatureEditTablePanel.productFeatureTypeModel.getSelectedItem() != null
                && productFeatureEditTablePanel.productFeatureCategoryModel.getSelectedItem() != null) {
            for (Product prod : catalogTreeSelPanel.getModel().getList()) {
                selectedProductsPrices.add(new ProductFeatureApplBulkUpdate(prod));
            }
        }

        productFeatureEditTablePanel.setProductList(catalogTreeSelPanel.getModel());
        productFeatureEditTablePanel.setProductPriceList(selectedProductsPrices);
        productFeatureEditTablePanel.loadItem();
    }

    public List<ProductFeatureApplBulkUpdate> loadProductFeatureList(Product product,
            String productFeatureTypeId, String productFeatureCategoryId) {
        List<ProductFeatureApplBulkUpdate> list = new ArrayList<ProductFeatureApplBulkUpdate>();
//        for (ProductFeatureApplBulkUpdate prodBulk : selectedProducts.getList()) {

        try {
            List<ProductFeatureAndAppl> productFeatureList = PosProductHelper.getProductFeatureAppl(product.getproductId(),
                    productFeatureTypeId, productFeatureCategoryId, ControllerOptions.getSession());
            if (!productFeatureList.isEmpty()) {
                for (ProductFeatureAndAppl app : productFeatureList) {
                    //ProductFeatureAndAppl app = productFeatureList.get(0);
                    ProductFeatureApplBulkUpdate prodBulk = new ProductFeatureApplBulkUpdate(product);
                    GenericValue val = app.getGenericValueObj().getRelatedOne("ProductFeature");
                    if (val != null) {
                        prodBulk.productFeature = new ProductFeature(val);
                    }
                    GenericValue val1 = app.getGenericValueObj().getRelatedOne("ProductFeatureAppl");
                    if (val1 != null) {
                        prodBulk.productFeatureAppl = new ProductFeatureAppl(val1);
                        prodBulk.oldPoductFeatureAppl = new ProductFeatureAppl(val1);
                    }
                    list.add(prodBulk);
                }
            } else {
                list.add(new ProductFeatureApplBulkUpdate(product));
            }
            //  prodBulk.setDefaultPrice(productPriceList);
            //  productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_LISTPRICE, ControllerOptions.getSession());
            //  prodBulk.setListPrice(productPriceList);
            //  productPriceList = PosProductHelper.getPriceList(prodBulk.getProduct().getproductId(), PosProductHelper.ProductPriceTypeId_AVERAGECOST, ControllerOptions.getSession());
            //  prodBulk.setAvgCost(productPriceList);
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //      }
        return list;
    }

    Map<String, ProductFeature> featureList = new HashMap<String, ProductFeature>();

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }

    static public class ProductPriceBulkEditTableModel extends ActionTableModel<ProductFeatureApplBulkUpdate> {

        /**
         *
         */
        private static final long serialVersionUID = 1547542546403627396L;
        Map<String, ProductFeature> modelFeatureList = null;

        public enum Columns {
//        public String[] m_colNames = {"Product Id", "Name", "Def Price", "List Price", "Avg Cost", "Supplier Id", "Supp Prod Id", "Last Price", "Scan Code", ""};
//        public Class[] m_colTypes = {String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class, String.class, BigDecimal.class, String.class, String.class};

            ProductId(0, 100, String.class, "Product Id", false),
            ProductName(1, 300, String.class, "Product Name", false),
            ProductFeatureId(2, 100, String.class, "Product Feature", true),
            ProductFeatureApplTypeId(3, 100, String.class, "Product Feature Appl Type", true),
            FromDate(4, 100, java.sql.Timestamp.class, "From Date", true),
            ThruDate(5, 100, java.sql.Timestamp.class, "Thru Date", true),
            SequenceNum(6, 100, BigDecimal.class, "Sequence Num", true),
            Amount(7, 100, BigDecimal.class, "Amount", true),
            RecurringAmount(8, 100, BigDecimal.class, "RecurringAmount", true);

//        TOPARTY(5, 100, String.class, "To Party", false),
//        EFFECTIVEDATE(6, 100, java.sql.Timestamp.class, "Effective Date", true),
//        AMOUNT(7, 100, BigDecimal.class, "Amount", false),
//        OUTSTANDINGAMOUNT(7, 100, BigDecimal.class, "Outstanding amount", false);
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

        public ProductPriceBulkEditTableModel(Map<String, ProductFeature> modelFeatureList) {
            this.modelFeatureList = modelFeatureList;
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

        public Object getValueAt(int rowIndex, int columnIndex) {

//            Debug.logInfo("getValueAt row: " + rowIndex + " column: " + col, module);
            if (rowIndex == 0) {
                Columns[] columns = Columns.values();
                Columns column = columns[columnIndex];
                if (column.equals(Columns.ProductId)) {
                    return "DEFAULT";
                }

                return new String();
            }

            Object columnValue = null;
            ProductFeatureApplBulkUpdate paymentComposite = (ProductFeatureApplBulkUpdate) listModel.getElementAt(rowIndex - 1);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            switch (column) {
                case ProductId:
                    columnValue = paymentComposite.getProduct().getproductId();
                    break;
                case ProductName:

                    columnValue = paymentComposite.getProduct().getproductName();

                    break;
                case ProductFeatureId:
                    if (paymentComposite.productFeature != null) {
                        columnValue = paymentComposite.productFeature.getdescription();
                    }
                    break;
                case ProductFeatureApplTypeId:
                    try {
                        if (UtilValidate.isNotEmpty(paymentComposite.productFeatureAppl.getProductFeatureApplTypeId())) {
                            ProductFeatureApplType applType = ProductFeatureApplTypeSingleton.getProductFeatureApplType(paymentComposite.productFeatureAppl.getProductFeatureApplTypeId());
                            columnValue = applType.getdescription();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //columnValue = paymentComposite.productFeatureAppl.getProductFeatureApplTypeId();
                    break;
                case FromDate:
                    columnValue = paymentComposite.productFeatureAppl.getFromDate();
                    break;
                case ThruDate:
                    columnValue = paymentComposite.productFeatureAppl.getThruDate();
                    break;
                case SequenceNum:
                    columnValue = paymentComposite.productFeatureAppl.getSequenceNum();
                    break;
                case Amount:
                    columnValue = paymentComposite.productFeatureAppl.getAmount();
                    break;
                case RecurringAmount:
                    columnValue = paymentComposite.productFeatureAppl.getRecurringAmount();
                    break;
                default:
                    break;
            }

            return columnValue;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {

            if (rowIndex == 0) {
                setAllRows(value, columnIndex);
                return;
            }
            Object columnValue = null;
//        OrderItemComposite orderItemComposite = (OrderItemComposite) listModel.getElementAt(rowIndex);
            ProductFeatureApplBulkUpdate paymentComposite = (ProductFeatureApplBulkUpdate) listModel.getElementAt(rowIndex - 1);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];

            switch (column) {
                case ProductFeatureId:
                    // paymentComposite.productFeatureAppl.setProductFeatureId((String) value);
                    //paymentComposite.productFeature.setdescription((String) value);
                    try {
                        ProductFeature productFeature = modelFeatureList.get((String) value);
                        if (productFeature != null) {
                            paymentComposite.productFeatureAppl.setProductFeatureId(productFeature.getproductFeatureId());
                            paymentComposite.productFeature = productFeature;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case ProductFeatureApplTypeId: {
                    try {
                        ProductFeatureApplType applType = ProductFeatureApplTypeSingleton.getReturnStatusFromDesc((String) value);
                        paymentComposite.productFeatureAppl.setProductFeatureApplTypeId(applType.getproductFeatureApplTypeId());
                    } catch (Exception ex) {
                        Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case FromDate:
                    paymentComposite.productFeatureAppl.setFromDate((java.sql.Timestamp) value);
                    break;
                case ThruDate:
                    paymentComposite.productFeatureAppl.setThruDate((java.sql.Timestamp) value);
                    break;
                case SequenceNum:
                    paymentComposite.productFeatureAppl.setSequenceNum((Long) value);
                    break;
                case Amount:
                    paymentComposite.productFeatureAppl.setAmount((BigDecimal) value);
                    break;
                case RecurringAmount:
                    paymentComposite.productFeatureAppl.setRecurringAmount((BigDecimal) value);

                default:
                    System.out.println("invalid index");
            }
//         record.updateOrderMax();
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void setAllRows(Object value, int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            for (int rowIndex = 0; rowIndex < listModel.getSize(); ++rowIndex) {
                ProductFeatureApplBulkUpdate paymentComposite = (ProductFeatureApplBulkUpdate) listModel.getElementAt(rowIndex);
                switch (column) {
                    case ProductFeatureId:
                        //paymentComposite.productFeatureAppl.setProductFeatureId((String) value);
                        try {
                            ProductFeature productFeature = modelFeatureList.get((String) value);
                            if (productFeature != null) {
                                paymentComposite.productFeatureAppl.setProductFeatureId(productFeature.getproductFeatureId());
                                paymentComposite.productFeature = productFeature;
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case ProductFeatureApplTypeId:
                        //paymentComposite.productFeatureAppl.setProductFeatureApplTypeId((String) value);
                        try {
                            ProductFeatureApplType applType = ProductFeatureApplTypeSingleton.getReturnStatusFromDesc((String) value);
                            paymentComposite.productFeatureAppl.setProductFeatureApplTypeId(applType.getproductFeatureApplTypeId());
                        } catch (Exception ex) {
                            Logger.getLogger(ProductFeatureMaintainTreeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case FromDate:
                        paymentComposite.productFeatureAppl.setFromDate((java.sql.Timestamp) value);
                        break;
                    case ThruDate:
                        paymentComposite.productFeatureAppl.setThruDate((java.sql.Timestamp) value);
                        break;
                    case SequenceNum:
                        paymentComposite.productFeatureAppl.setSequenceNum(((BigDecimal) value).longValue());
                        break;
                    case Amount:
                        paymentComposite.productFeatureAppl.setAmount((BigDecimal) value);
                        break;
                    case RecurringAmount:
                        paymentComposite.productFeatureAppl.setRecurringAmount((BigDecimal) value);

                    default:
                        System.out.println("invalid index");

                }
                fireTableCellUpdated(rowIndex + 1, columnIndex);
            }
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

        @Override
        final public int getRowCount() {
            return listModel.getSize() + 1;
        }
    }

    static public class MainMenuAction extends ScreenAction {

        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";
        ControllerOptions controllerOptions = null;

        public MainMenuAction(String name, ControllerOptions controllerOptions) {
            super(name);
            this.controllerOptions = controllerOptions;
            if (name == null) {
                this.setName(caption);
            }

            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductFeatureMaintainTreeController.runController(controllerOptions);

        }

        @Override
        public Action getAction() {
            return this;
        }
    }

    static public void addToMenu(ControllerOptions options, javax.swing.JMenu parentMenu) {

        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductFeature");
        MainMenuAction mainMenuItem = new MainMenuAction("Product Feature Bulk Update", controllerOptions);
        JMenuItem mnuItem = mainMenuItem.createActionMenuItem();
        parentMenu.add(mnuItem);
    }

    public class ProductFeatureApplBulkUpdate implements GenericSaveInterface {

        private ProductFeatureAppl productFeatureAppl = new ProductFeatureAppl();
        private ProductFeatureAppl oldPoductFeatureAppl = new ProductFeatureAppl();
        private ProductFeature productFeature = new ProductFeature();
        private Product product;

        public ProductFeatureApplBulkUpdate(Product product) {
            this.product = product;
            productFeatureAppl.setProductId(product.getproductId());
            oldPoductFeatureAppl.setProductId(product.getproductId());
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public ProductFeatureAppl getProductFeatureAppl() {
            return productFeatureAppl;
        }

        public void setProductFeatureAppl(ProductFeatureAppl productFeatureAppl) {
            this.productFeatureAppl = productFeatureAppl;
        }

        public ProductFeature getProductFeature() {
            return productFeature;
        }

        public void setProductFeature(ProductFeature productFeature) {
            this.productFeature = productFeature;
        }

        public ProductFeatureAppl getOldPoductFeatureAppl() {
            return oldPoductFeatureAppl;
        }

        public void setOldPoductFeatureAppl(ProductFeatureAppl oldPoductFeatureAppl) {
            this.oldPoductFeatureAppl = oldPoductFeatureAppl;
        }

        @Override
        public void newRecord() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void copyRecord() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<String> getKey() {
            return UtilMisc.toList("productId", "productFeatureId", "fromDate");
        }

        @Override
        public boolean hasChanged() {
            boolean result = false;
            if (!productFeatureAppl.getProductId().equals(oldPoductFeatureAppl.getProductId())
                    || !productFeatureAppl.getProductFeatureId().equals(oldPoductFeatureAppl.getProductFeatureId())
                    || !productFeatureAppl.getProductFeatureApplTypeId().equals(oldPoductFeatureAppl.getProductFeatureApplTypeId())
                    || !productFeatureAppl.getFromDate().equals(oldPoductFeatureAppl.getFromDate())
                    || !productFeatureAppl.getSequenceNum().equals(oldPoductFeatureAppl.getSequenceNum())) {

                result = true;
            }
            return result;
        }

        @Override
        public boolean isValidValues() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setGenericValue(GenericValue val) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void loadList(ControllerOptions options) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public JPanel getPanel() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void getDialogFields() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public GenericValue getGenericValueObj() {
            if (productFeatureAppl != null) {
                productFeatureAppl.getGenericValue();
                return productFeatureAppl.getGenericValueObj();
            }
            return null;
        }

        @Override
        public Map<String, Object> getValuesMap() {
            if (productFeatureAppl != null) {
                return productFeatureAppl.getValuesMap();
            }
            return null;
        }
    }

}
