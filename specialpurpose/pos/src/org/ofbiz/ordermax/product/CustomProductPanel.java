 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Hashtable;
import java.util.Map;
import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Locale;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.ImportProductHelper;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductImportPojo;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.screens.ProductDetailEditDialog;
import org.ofbiz.ordermax.utility.GenericValueComboBox;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.base.util.UtilMisc;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import mvc.controller.LoadSupplierProductWorker;
import mvc.controller.ProductIdMaintainVerifyValidator;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.components.CalculatePricePanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.composite.ProductContentCompositeList;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.report.reports.ReportBaseFactory;

/**
 *
 * @author siranjeev
 */
public class CustomProductPanel extends ProductPanel implements ProductPanelInterface {

    public static final String module = CustomProductPanel.class.getName();
    protected GenericValueComboBox facilityIdCombo = null;
    protected GenericValueComboBox supplierPartyIdCombo = null;
    //protected GenericValueComboBox weightUomIdCombo = null;
    protected ProductInventoryPojo curryProductPojo = new ProductInventoryPojo();
    protected Map<String, String> departmentValMap = new TreeMap<String, String>();
//    protected List<String> departmentListBidingCombo = FastList.newInstance();
    protected Map<String, Map<String, ProductCodeId>> codeIdMap = FastMap.newInstance();
    protected Hashtable<String, String> scanIdMap = new Hashtable<String, String>();
    BigDecimal quantityOnHand = BigDecimal.ONE;
    public ProductDataTreeLoader m_productsArray = null;
    private JGenericComboBoxSelectionModel<GoodIdentificationType> goodIdentificationTypeComboModel = null;
//    boolean isModified = false;

    public void setParentItem(Object val) {
        if (val != null && val instanceof TreeNode) {
            TreeNode deptNode = (TreeNode) val;
            Debug.logInfo("setParentItem node name: " + deptNode.getNodeName(), module);
            if (deptNode.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                Debug.logInfo("setParentItem node name: " + deptNode._id, module);
//                genericValueMapComboBox.setSelectedItemId(deptNode._id);
            }

            if (deptNode.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
                Debug.logInfo("setParentItem node name: " + deptNode._id, module);
//                genericValueMapBrandComboBox.setSelectedItemId(deptNode._id);
            }

        }

    }
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    Map<Integer, TreeNode> treePath;

    @Override
    public void setProductTreePath(Map<Integer, TreeNode> treePath) {
        this.treePath = treePath;
    }

    class ProductCodeId {

        public String BrandName;
        public String ProductIdPrefix;
        public List<Integer> itemList;
    };
    protected Delegator delegator = null;
    protected XuiSession session = null;
    protected CopyWebProductImagePanel copyWebProductImagePanel = null;
    private JGenericComboBoxSelectionModel<String> taxable = null;
    private JGenericComboBoxSelectionModel<Uom> weightUom = null;
    private JGenericComboBoxSelectionModel<Uom> lengthUom = null;
    public JGenericComboBoxSelectionModel<String> isVirtualComboBoxModel = null;
    public JGenericComboBoxSelectionModel<String> isVariantComboBoxModel = null;
    public JGenericComboBoxSelectionModel<Uom> currencyComboModel = null;
    public CalculatePricePanel editBasePrice = null;
    public CalculatePricePanel editListPrice = null;
    public ProductPickerEditPanel productVariantPickerEditPanel;

    private JGenericComboBoxSelectionModel<Uom> scaleUom = null;
    public JGenericComboBoxSelectionModel<String> isScaleComboBoxModel = null;

    /**
     * Creates new form CustomProductPanel
     */
    public CustomProductPanel(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;
        initComponents();
        taxable = new JGenericComboBoxSelectionModel<String>(panelTaxable, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        weightUom = new JGenericComboBoxSelectionModel<Uom>(panelWidth, UomWeightSingleton.getValueList());
        try {
            Uom uom = UomWeightSingleton.getUom("WT_kg");
            weightUom.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        scaleUom = new JGenericComboBoxSelectionModel<Uom>(panelScaleUom, UomWeightSingleton.getValueList());
        isScaleComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelScale, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        try {
            Uom uom = UomWeightSingleton.getUom("WT_kg");
            scaleUom.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        lengthUom = new JGenericComboBoxSelectionModel<Uom>(panelHeight, UomLenghtSingleton.getValueList());
        try {
            Uom uom = UomLenghtSingleton.getUom("LEN_mm");
            lengthUom.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControllerOptions partyOptions = new ControllerOptions();
        panelProductIdPicker = new ProductPickerEditPanel(partyOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panelProductIdPicker);
        editBasePrice = new CalculatePricePanel(editPurchasePrice);
        sellingPricePanel.setLayout(new BorderLayout());
        sellingPricePanel.add(BorderLayout.CENTER, editBasePrice);

        editListPrice = new CalculatePricePanel(editPurchasePrice);
        listPricePanel.setLayout(new BorderLayout());
        listPricePanel.add(BorderLayout.CENTER, editListPrice);

        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, delegator);
        facilityIdCombo = new GenericValueComboBox(comboFacility, genFacilityList, "Facility", "facilityId", "facilityName");
        comboFacility.setSelectedItem((String) session.getAttribute("facilityId"));
        List<GenericValue> genSupplierList = PosProductHelper.getSupplierList(delegator);
        supplierPartyIdCombo = new GenericValueComboBox(comboSupplier, genSupplierList, "Party", "partyId", "description");
        currencyComboModel = new JGenericComboBoxSelectionModel<Uom>(panelCurrency, UomCurrencySingleton.getValueList());
        try {
            currencyComboModel.setSelectedItem(UomCurrencySingleton.getUom((String) ControllerOptions.getSession().getAttribute("currency")));
        } catch (Exception ex) {
            Logger.getLogger(module).log(Level.SEVERE, null, ex);
        }
        isVirtualComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVirtualProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        isVariantComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVariantProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        goodIdentificationTypeComboModel = new JGenericComboBoxSelectionModel<GoodIdentificationType>(goodsidentificationPanel, GoodIdentificationTypeSingleton.getValueList());

        ControllerOptions productVariantOptions = new ControllerOptions();
        productVariantOptions.put("virualOnly", "Y");
        productVariantPickerEditPanel = new ProductPickerEditPanel(productVariantOptions);
        panelVariantProductId.setLayout(new BorderLayout());
        panelVariantProductId.add(BorderLayout.CENTER, productVariantPickerEditPanel);
        try {
            String str = YesNoConditionSelectSingleton.getString("N");
            isVirtualComboBoxModel.setSelectedItem(str);
            isVariantComboBoxModel.setSelectedItem(str);
            //isVariantComboBoxModel.jComboBox.setEnabled(false);
            //productVariantPickerEditPanel.textIdField.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(CreateProductIdPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProductIdMaintainVerifyValidator prodValidator = new ProductIdMaintainVerifyValidator(productVariantPickerEditPanel.textIdField, ControllerOptions.getSession());
        productVariantPickerEditPanel.textIdField.setInputVerifier(prodValidator);

        try {
            String str = YesNoConditionSelectSingleton.getString("N");
            isVirtualComboBoxModel.setSelectedItem(str);
            isVariantComboBoxModel.setSelectedItem(str);
            //isVariantComboBoxModel.jComboBox.setEnabled(false);
            productVariantPickerEditPanel.textIdField.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(CreateProductIdPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        isVirtualComboBoxModel.jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isVirtualComboBoxModel.getSelectedItem().equals(YesNoConditionSelectSingleton.Y)) {

                    panelVariantProductId.setEnabled(false);
                    isVariantComboBoxModel.jComboBox.setEnabled(false);
                    productVariantPickerEditPanel.textIdField.setEnabled(false);
                } else {
                    panelVariantProductId.setEnabled(true);
                    isVariantComboBoxModel.jComboBox.setEnabled(true);
                    productVariantPickerEditPanel.textIdField.setEnabled(true);
                }
            }
        });

        isVariantComboBoxModel.jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isVariantComboBoxModel.getSelectedItem().equals(YesNoConditionSelectSingleton.Y)) {
                    panelVariantProductId.setEnabled(true);
                    productVariantPickerEditPanel.textIdField.setEnabled(true);
                } else {
                    panelVariantProductId.setEnabled(false);
                    productVariantPickerEditPanel.textIdField.setEnabled(false);
                }
            }
        });

        //List<GenericValue> genUomList = PosProductHelper.getGenericValueLists("Uom", "uomTypeId", "WEIGHT_MEASURE", delegator);
        //weightUomIdCombo = new GenericValueComboBox(comoUomWeight, genUomList, "Uom", "uomId", "abbreviation");
        editProductName.getDocument().addDocumentListener(new TextChangeListner());
//        comboBrand.getDocument().addDocumentListener(new TextChangeListner());
        editProductName.getDocument().addDocumentListener(new TextChangeListner());
        editInternalName.getDocument().addDocumentListener(new TextChangeListner());
        editScanCode.getDocument().addDocumentListener(new TextChangeListner());

        panelProductIdPicker.textIdField.getDocument().addDocumentListener(new TextChangeListner());
//        comboDepartmentName = new javax.swing.JComboBox();
        editSupplierCode.getDocument().addDocumentListener(new TextChangeListner());
//        comboSupplier = new javax.swing.JComboBox();
        editSupplierLastPrice.getDocument().addDocumentListener(new TextChangeListner());
//        editBasePrice.getDocument().addDocumentListener(new TextChangeListner());
//        editListPrice.getDocument().addDocumentListener(new TextChangeListner());
//        editCurrency.getDocument().addDocumentListener(new TextChangeListner());
//        comboFacility = new javax.swing.JComboBox();
        editPurchasePrice.getDocument().addDocumentListener(new TextChangeListner());
//        editQtyOnHand.getDocument().addDocumentListener(new TextChangeListner());
        txtItemWeight.getDocument().addDocumentListener(new TextChangeListner());
        txtItemHeight.getDocument().addDocumentListener(new TextChangeListner());
        txtAreaDescription.getDocument().addDocumentListener(new TextChangeListner());
        txtShelfLife.getDocument().addDocumentListener(new TextChangeListner());
        //        genericValueMapComboBox = new GenericValueMapComboBox(comboDepartmentName, null);
//        genericValueMapBrandComboBox = new GenericValueMapComboBox(comboBrand, null);
        copyWebProductImagePanel = new CopyWebProductImagePanel(session);
        copyWebProductImagePanel.setSupplierList(genSupplierList);

        copyWebProductImagePanel.getBtnCopyProductDetail().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String productName = copyWebProductImagePanel.txtProductName.getText();
                    editProductName.setText(copyWebProductImagePanel.txtProductName.getText());
                    editInternalName.setText(copyWebProductImagePanel.txtProductName.getText());
                    isModified = true;
                    notifyListeners(OrderMaxUtility.ITEM_STATUS_CHANGED, productName, productName);

                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        copyWebProductImagePanel.getBtnCopyProductDetail1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String productName = copyWebProductImagePanel.txtProductName.getText();
                    String repString = copyWebProductImagePanel.txtStringToRemove.getText().trim();
                    if (repString.isEmpty() == false) {
                        int pos = productName.indexOf(copyWebProductImagePanel.txtStringToRemove.getText(), 0);
                        if (pos == 0) {
                            productName = productName.substring(repString.length()).trim();
                        }
                    }
                    String addString = copyWebProductImagePanel.txtStringToAdd.getText().trim();
                    if (addString.isEmpty() == false && productName.isEmpty() == false) {
                        productName = productName + addString;
                    }

                    File filePath = new File(copyWebProductImagePanel.selectedProductImagePath);//BaseHelper.getImageFilePath("original");
                    txtoriginalImageUrl.setText(BaseHelper.CopyImageSetFileName("original", filePath, panelProductIdPicker.textIdField.getText(), 0, 0));
                    txtmediumImageUrl.setText(BaseHelper.CopyImageSetFileName("small-60", filePath, panelProductIdPicker.textIdField.getText(), 60, 60));
                    txtsmallImageUrl.setText(BaseHelper.CopyImageSetFileName("small", filePath, panelProductIdPicker.textIdField.getText(), 48, 48));
                    txtlargeImageUrl.setText(BaseHelper.CopyImageSetFileName("large", filePath, panelProductIdPicker.textIdField.getText(), 240, 240));
                    txtdetailImageUrl.setText(BaseHelper.CopyImageSetFileName("large-200", filePath, panelProductIdPicker.textIdField.getText(), 200, 200));        // TODO add your handling code here:
                    showSelectandFileImage(txtoriginalImageUrl.getText());
                    isModified = true;
                    notifyListeners(OrderMaxUtility.ITEM_STATUS_CHANGED, productName, productName);

                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        copyWebProductImagePanel.getBtnCopyProductNew().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyDataFromWebPanel();
            }
        });

        copyWebProductImagePanel.btnUpdateBrand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    copyWebProductImagePanel.setDepartmentMap(departmentValMap);
                    // copyWebProductImagePanel.genericValueMapComboBox.setSelectedItemId(genericValueMapComboBox.getSelectedItemId());
                    copyWebProductImagePanel.setBrandList(brandMap);
                    //copyWebProductImagePanel.genericValueMapBrandComboBox.setSelectedItemId(genericValueMapBrandComboBox.getSelectedItemId());
                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        OrderMaxUtility.addAPanelToBorder(copyWebProductImagePanel, panelWebImage);

        List<String> list = ReportBaseFactory.getReportList(ReportBaseFactory.REP_GROUP_SUPPLIER, null);
        for (String str : list) {
            comboEntityName.addItem(str);
        }

        //panelWebImage.add();
    }
    Map<String, String> brandMap = null;

    void copyDataFromWebPanel() {
        try {
            if (isModified) {
                String message = "Do you want to save?";
                int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        saveItem();
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }

            newItem();

            supplierPartyIdCombo.setSelectedItemId(copyWebProductImagePanel.getSupplierId());
//            genericValueMapComboBox.setSelectedItemId(copyWebProductImagePanel.getDepartmentId());
//            genericValueMapBrandComboBox.setSelectedItemId(copyWebProductImagePanel.getBrandId());

            String productName = copyWebProductImagePanel.txtProductName.getText();
//            String productName = copyWebProductImagePanel.txtProductName.getText();
            String repString = copyWebProductImagePanel.txtStringToRemove.getText().trim();
            if (repString.isEmpty() == false) {
                int pos = productName.indexOf(copyWebProductImagePanel.txtStringToRemove.getText(), 0);
                if (pos == 0) {
                    productName = productName.substring(repString.length()).trim();
                }
            }
            String addString = copyWebProductImagePanel.txtStringToAdd.getText().trim();
            if (addString.isEmpty() == false && productName.isEmpty() == false) {
                productName = productName + addString;
            }

            editProductName.setText(productName);
            editInternalName.setText(productName);

            btnCreateProductIdActionPerformed(null);
            btnGenerateScanCodeActionPerformed(null);
            editSupplierCode.setText(panelProductIdPicker.textIdField.getText());
            addToProductCodeKeyMap(panelProductIdPicker.textIdField.getText());
            editSupplierLastPrice.setText(copyWebProductImagePanel.txtPurchasePrice.getText());
            editPurchasePrice.setText(copyWebProductImagePanel.txtPurchasePrice.getText());
            editBasePrice.setText(copyWebProductImagePanel.txtSellingPrice.getText());
            editListPrice.setText(copyWebProductImagePanel.txtSellingPrice.getText());

            File filePath = new File(copyWebProductImagePanel.selectedProductImagePath);//BaseHelper.getImageFilePath("original");
            txtoriginalImageUrl.setText(BaseHelper.CopyImageSetFileName("original", filePath, panelProductIdPicker.textIdField.getText(), 0, 0));
            txtmediumImageUrl.setText(BaseHelper.CopyImageSetFileName("small-60", filePath, panelProductIdPicker.textIdField.getText(), 60, 60));
            txtsmallImageUrl.setText(BaseHelper.CopyImageSetFileName("small", filePath, panelProductIdPicker.textIdField.getText(), 48, 48));
            txtlargeImageUrl.setText(BaseHelper.CopyImageSetFileName("large", filePath, panelProductIdPicker.textIdField.getText(), 240, 240));
            txtdetailImageUrl.setText(BaseHelper.CopyImageSetFileName("large-200", filePath, panelProductIdPicker.textIdField.getText(), 200, 200));        // TODO add your handling code here:
            showSelectandFileImage(txtoriginalImageUrl.getText());
            isModified = true;
            notifyListeners(OrderMaxUtility.ITEM_NEW, productName, productName);
        } catch (Exception ex) {
        }
//                loadProductTree();
    }

    public void setBrandList(List<Key> brandList) {

        brandMap = new HashMap<String, String>();
        for (Key entry : brandList) {
            brandMap.put(entry._id, entry._name);
        }
//        genericValueMapBrandComboBox = new GenericValueMapComboBox(comboBrand, brandMap);
//        copyWebProductImagePanel.setBrandList(brandMap);
    }
    //GenericValueMapComboBox genericValueMapComboBox = null;

    public void setDepartmentMap(Map depValMap) {
        departmentValMap = depValMap;
        /*        DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
         for (Map.Entry<String, String> entryDept : departmentValMap.entrySet()) {

         comboDepartmentModel.addElement(entryDept.getEntityValue());
         departmentListBidingCombo.add(entryDept.getEntityId());
         }
         comboDepartmentName.setModel(comboDepartmentModel);
         */
        //genericValueMapComboBox = new GenericValueMapComboBox(comboDepartmentName, depValMap);
        //genericValueMapComboBox.loadCombo();
//.        copyWebProductImagePanel.setDepartmentMap(depValMap);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        editInternalName = new javax.swing.JTextField();
        btnGenerateScanCode = new javax.swing.JButton();
        btnInternalName = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnCreateProductId = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        buttonFind = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        panelProductId = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        txtBrandName = new javax.swing.JTextField();
        panelTaxable = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        txtItemHeight = new javax.swing.JFormattedTextField();
        panelHeight = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtItemWeight = new javax.swing.JFormattedTextField();
        panelWidth = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        panelVirtualProd = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        panelVariantProd = new javax.swing.JPanel();
        panelVariantProductId = new javax.swing.JPanel();
        editProductName = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        txtShelfLife = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        panelProductId1 = new javax.swing.JPanel();
        goodsidentificationPanel = new javax.swing.JPanel();
        editScanCode = new javax.swing.JTextField();
        panelTaxable1 = new javax.swing.JPanel();
        panelScale = new javax.swing.JPanel();
        panelScaleUom = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        editPurchasePrice = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblListPrice = new javax.swing.JLabel();
        comboFacility = new javax.swing.JComboBox();
        lblPurchasePrice = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblBasePrice = new javax.swing.JLabel();
        panelCurrency = new javax.swing.JPanel();
        sellingPricePanel = new javax.swing.JPanel();
        listPricePanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        editSupplierLastPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboSupplier = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        editSupplierCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnCopyProductIdToSupplier = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtsmallImageUrl = new javax.swing.JTextField();
        btnSmallIcon = new javax.swing.JButton();
        btnMediumIcon = new javax.swing.JButton();
        txtmediumImageUrl = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnLargeIcon = new javax.swing.JButton();
        txtlargeImageUrl = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnDetailIcon = new javax.swing.JButton();
        txtdetailImageUrl = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtoriginalImageUrl = new javax.swing.JTextField();
        btnOriginalIcon = new javax.swing.JButton();
        panelImage = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnFixImagePath = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClearImageStore = new javax.swing.JButton();
        cbScaleImage = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnReloadTree = new javax.swing.JButton();
        btnCopySelProducts = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtPackDate = new ObservingTextField();
        txtProductName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtUseByDate = new ObservingTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtBarCode = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtWeightUom = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtPrice = new javax.swing.JFormattedTextField();
        txtWeight = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtIngrident = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtLabelCount = new javax.swing.JTextField();
        panelWebImage = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        btnRunReport = new javax.swing.JButton();
        lblSupplierName = new javax.swing.JLabel();
        btnReload = new javax.swing.JButton();
        comboEntityName = new javax.swing.JComboBox();
        panelJasper = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(500, 400));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane2.setMinimumSize(new java.awt.Dimension(0, 0));

        editInternalName.setMaximumSize(new java.awt.Dimension(400, 40));
        editInternalName.setMinimumSize(new java.awt.Dimension(400, 24));
        editInternalName.setPreferredSize(new java.awt.Dimension(300, 26));
        editInternalName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editInternalNameActionPerformed(evt);
            }
        });

        btnGenerateScanCode.setText("Create Scan Code");
        btnGenerateScanCode.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGenerateScanCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateScanCodeActionPerformed(evt);
            }
        });

        btnInternalName.setText("Copy Internal Name");
        btnInternalName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInternalNameActionPerformed(evt);
            }
        });

        jLabel8.setText("Scan code:");

        btnCreateProductId.setText("Create Product Code");
        btnCreateProductId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateProductIdActionPerformed(evt);
            }
        });

        jLabel5.setText("Internal Name:");

        jLabel4.setText("Name:");

        jLabel6.setText("Product Code:");

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel29.setText("Weight:");

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel30.setText("Shelf Life:");

        buttonFind.setText("Find");
        buttonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFindActionPerformed(evt);
            }
        });

        jLabel18.setText("Comments:");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(400, 40));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(400, 24));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 26));

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        txtAreaDescription.setPreferredSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setViewportView(txtAreaDescription);

        panelProductId.setMaximumSize(new java.awt.Dimension(400, 40));
        panelProductId.setMinimumSize(new java.awt.Dimension(400, 24));
        panelProductId.setPreferredSize(new java.awt.Dimension(300, 26));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel34.setText("Brand Name:");

        txtBrandName.setMaximumSize(new java.awt.Dimension(400, 40));
        txtBrandName.setMinimumSize(new java.awt.Dimension(400, 24));
        txtBrandName.setPreferredSize(new java.awt.Dimension(300, 26));

        panelTaxable.setMaximumSize(new java.awt.Dimension(400, 40));
        panelTaxable.setMinimumSize(new java.awt.Dimension(400, 24));
        panelTaxable.setPreferredSize(new java.awt.Dimension(300, 26));

        javax.swing.GroupLayout panelTaxableLayout = new javax.swing.GroupLayout(panelTaxable);
        panelTaxable.setLayout(panelTaxableLayout);
        panelTaxableLayout.setHorizontalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelTaxableLayout.setVerticalGroup(
            panelTaxableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel16.setText("Taxable(GST):");

        jPanel13.setMaximumSize(new java.awt.Dimension(400, 40));
        jPanel13.setMinimumSize(new java.awt.Dimension(400, 24));
        jPanel13.setPreferredSize(new java.awt.Dimension(300, 26));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPanel20.setLayout(new java.awt.GridLayout(1, 0));

        txtItemHeight.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.###"))));
        txtItemHeight.setToolTipText("");
        jPanel20.add(txtItemHeight);

        javax.swing.GroupLayout panelHeightLayout = new javax.swing.GroupLayout(panelHeight);
        panelHeight.setLayout(panelHeightLayout);
        panelHeightLayout.setHorizontalGroup(
            panelHeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelHeightLayout.setVerticalGroup(
            panelHeightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel20.add(panelHeight);

        jPanel13.add(jPanel20);

        jPanel11.setMaximumSize(new java.awt.Dimension(400, 40));
        jPanel11.setMinimumSize(new java.awt.Dimension(400, 24));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 26));
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        txtItemWeight.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.###"))));
        jPanel4.add(txtItemWeight);

        javax.swing.GroupLayout panelWidthLayout = new javax.swing.GroupLayout(panelWidth);
        panelWidth.setLayout(panelWidthLayout);
        panelWidthLayout.setHorizontalGroup(
            panelWidthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelWidthLayout.setVerticalGroup(
            panelWidthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel4.add(panelWidth);

        jPanel11.add(jPanel4);

        jPanel27.setMaximumSize(new java.awt.Dimension(400, 40));
        jPanel27.setMinimumSize(new java.awt.Dimension(400, 24));
        jPanel27.setPreferredSize(new java.awt.Dimension(300, 26));
        jPanel27.setLayout(new java.awt.GridLayout(1, 0));

        jPanel29.setLayout(new java.awt.BorderLayout());

        jLabel35.setText("Virtual:");
        jPanel29.add(jLabel35, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout panelVirtualProdLayout = new javax.swing.GroupLayout(panelVirtualProd);
        panelVirtualProd.setLayout(panelVirtualProdLayout);
        panelVirtualProdLayout.setHorizontalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );
        panelVirtualProdLayout.setVerticalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel29.add(panelVirtualProd, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel29);

        jPanel31.setLayout(new java.awt.BorderLayout());

        jLabel36.setText("Variant:");
        jPanel31.add(jLabel36, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout panelVariantProdLayout = new javax.swing.GroupLayout(panelVariantProd);
        panelVariantProd.setLayout(panelVariantProdLayout);
        panelVariantProdLayout.setHorizontalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );
        panelVariantProdLayout.setVerticalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel31.add(panelVariantProd, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel31);

        javax.swing.GroupLayout panelVariantProductIdLayout = new javax.swing.GroupLayout(panelVariantProductId);
        panelVariantProductId.setLayout(panelVariantProductIdLayout);
        panelVariantProductIdLayout.setHorizontalGroup(
            panelVariantProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );
        panelVariantProductIdLayout.setVerticalGroup(
            panelVariantProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel27.add(panelVariantProductId);

        editProductName.setMaximumSize(new java.awt.Dimension(400, 40));
        editProductName.setMinimumSize(new java.awt.Dimension(400, 24));
        editProductName.setPreferredSize(new java.awt.Dimension(300, 26));

        jPanel33.setMaximumSize(new java.awt.Dimension(400, 40));
        jPanel33.setMinimumSize(new java.awt.Dimension(400, 24));
        jPanel33.setPreferredSize(new java.awt.Dimension(300, 26));
        jPanel33.setLayout(new java.awt.GridLayout(1, 0));

        jPanel35.setLayout(new java.awt.GridLayout(1, 0));

        txtShelfLife.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtShelfLife.setText("730");
        txtShelfLife.setToolTipText("");
        jPanel35.add(txtShelfLife);

        jLabel38.setText("Days");
        jPanel35.add(jLabel38);

        jPanel33.add(jPanel35);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel37.setText("Height:");

        panelProductId1.setMaximumSize(new java.awt.Dimension(400, 40));
        panelProductId1.setMinimumSize(new java.awt.Dimension(400, 24));
        panelProductId1.setPreferredSize(new java.awt.Dimension(300, 26));
        panelProductId1.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout goodsidentificationPanelLayout = new javax.swing.GroupLayout(goodsidentificationPanel);
        goodsidentificationPanel.setLayout(goodsidentificationPanelLayout);
        goodsidentificationPanelLayout.setHorizontalGroup(
            goodsidentificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        goodsidentificationPanelLayout.setVerticalGroup(
            goodsidentificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelProductId1.add(goodsidentificationPanel);

        editScanCode.setMaximumSize(new java.awt.Dimension(400, 40));
        editScanCode.setMinimumSize(new java.awt.Dimension(400, 24));
        editScanCode.setPreferredSize(new java.awt.Dimension(300, 26));
        panelProductId1.add(editScanCode);

        panelTaxable1.setMaximumSize(new java.awt.Dimension(400, 40));
        panelTaxable1.setMinimumSize(new java.awt.Dimension(400, 24));
        panelTaxable1.setPreferredSize(new java.awt.Dimension(300, 26));
        panelTaxable1.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout panelScaleLayout = new javax.swing.GroupLayout(panelScale);
        panelScale.setLayout(panelScaleLayout);
        panelScaleLayout.setHorizontalGroup(
            panelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelScaleLayout.setVerticalGroup(
            panelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelTaxable1.add(panelScale);

        javax.swing.GroupLayout panelScaleUomLayout = new javax.swing.GroupLayout(panelScaleUom);
        panelScaleUom.setLayout(panelScaleUomLayout);
        panelScaleUomLayout.setHorizontalGroup(
            panelScaleUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelScaleUomLayout.setVerticalGroup(
            panelScaleUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelTaxable1.add(panelScaleUom);

        jLabel17.setText("Scale:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelTaxable, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelProductId, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(editProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editInternalName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBrandName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelProductId1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTaxable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(btnGenerateScanCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonFind))
                            .addComponent(btnInternalName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreateProductId)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editInternalName, editProductName, jPanel11, jPanel13, jPanel27, jPanel33, jScrollPane1, panelProductId, panelProductId1, panelTaxable, txtBrandName});

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCreateProductId, btnGenerateScanCode, btnInternalName});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(btnCreateProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(editInternalName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInternalName))
                .addGap(3, 3, 3)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(txtBrandName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductId1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGenerateScanCode)
                        .addComponent(buttonFind)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(12, 12, 12)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTaxable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTaxable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(142, 142, 142)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editProductName, jPanel11, jPanel13, jPanel27, jPanel33, panelProductId, panelProductId1, panelTaxable, txtBrandName});

        jTabbedPane2.addTab("Details", jPanel16);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Selling Prices"));

        editPurchasePrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPurchasePriceActionPerformed(evt);
            }
        });

        jLabel10.setText("Facility Name:");

        lblListPrice.setText("List Price:");

        comboFacility.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFacilityActionPerformed(evt);
            }
        });

        lblPurchasePrice.setText("Purchase Price(no tax):");

        jLabel19.setText("Currency:");

        lblBasePrice.setText("Selling Price:");

        javax.swing.GroupLayout panelCurrencyLayout = new javax.swing.GroupLayout(panelCurrency);
        panelCurrency.setLayout(panelCurrencyLayout);
        panelCurrencyLayout.setHorizontalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );
        panelCurrencyLayout.setVerticalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sellingPricePanelLayout = new javax.swing.GroupLayout(sellingPricePanel);
        sellingPricePanel.setLayout(sellingPricePanelLayout);
        sellingPricePanelLayout.setHorizontalGroup(
            sellingPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );
        sellingPricePanelLayout.setVerticalGroup(
            sellingPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout listPricePanelLayout = new javax.swing.GroupLayout(listPricePanel);
        listPricePanel.setLayout(listPricePanelLayout);
        listPricePanelLayout.setHorizontalGroup(
            listPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );
        listPricePanelLayout.setVerticalGroup(
            listPricePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBasePrice, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblListPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPurchasePrice, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboFacility, 0, 323, Short.MAX_VALUE)
                    .addComponent(editPurchasePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(sellingPricePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listPricePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(451, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboFacility, editPurchasePrice, listPricePanel, panelCurrency, sellingPricePanel});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPurchasePrice)
                    .addComponent(editPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBasePrice)
                    .addComponent(sellingPricePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblListPrice)
                    .addComponent(listPricePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboFacility, editPurchasePrice, panelCurrency});

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Details"));

        jLabel7.setText("Supplier Product Code:");

        jLabel1.setText("Supplier Last Price:");

        jLabel9.setText("Supplier:");

        btnCopyProductIdToSupplier.setText("Copy Product");
        btnCopyProductIdToSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyProductIdToSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(editSupplierCode, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboSupplier, javax.swing.GroupLayout.Alignment.LEADING, 0, 326, Short.MAX_VALUE)
                    .addComponent(editSupplierLastPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCopyProductIdToSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(423, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(comboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editSupplierCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCopyProductIdToSupplier)))
                .addGap(7, 7, 7)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(editSupplierLastPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel7, jPanel8});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Prices", jPanel10);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Images"));

        jLabel11.setText("Small:");

        txtsmallImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsmallImageUrlFocusGained(evt);
            }
        });

        btnSmallIcon.setText("jButton1");
        btnSmallIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIconActionPerformed(evt);
            }
        });

        btnMediumIcon.setText("jButton1");
        btnMediumIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMediumIconActionPerformed(evt);
            }
        });

        txtmediumImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtmediumImageUrlFocusGained(evt);
            }
        });

        jLabel12.setText("Medium:");

        btnLargeIcon.setText("jButton1");
        btnLargeIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLargeIconActionPerformed(evt);
            }
        });

        txtlargeImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtlargeImageUrlFocusGained(evt);
            }
        });

        jLabel13.setText("Large:");

        btnDetailIcon.setText("jButton1");
        btnDetailIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailIconActionPerformed(evt);
            }
        });

        txtdetailImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtdetailImageUrlFocusGained(evt);
            }
        });

        jLabel14.setText("Detail:");

        jLabel15.setText("Original:");

        txtoriginalImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtoriginalImageUrlFocusGained(evt);
            }
        });

        btnOriginalIcon.setText("jButton1");
        btnOriginalIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIconActionPerformed(evt);
            }
        });

        panelImage.setLayout(new java.awt.BorderLayout());
        panelImage.add(lblImage, java.awt.BorderLayout.CENTER);

        btnFixImagePath.setText("Fix Image Path");

        btnDelete.setText("Delete File");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClearImageStore.setText("Clear Image store");
        btnClearImageStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearImageStoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFixImagePath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearImageStore)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFixImagePath)
                    .addComponent(btnDelete)
                    .addComponent(btnClearImageStore))
                .addContainerGap())
        );

        panelImage.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        cbScaleImage.setSelected(true);
        cbScaleImage.setText("Scale Image");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsmallImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmediumImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtlargeImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdetailImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtoriginalImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSmallIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnLargeIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMediumIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnOriginalIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDetailIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbScaleImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtsmallImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmallIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtmediumImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMediumIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtlargeImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLargeIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtdetailImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetailIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtoriginalImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalIcon)
                    .addComponent(cbScaleImage))
                .addGap(18, 18, 18)
                .addComponent(panelImage, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));

        btnReloadTree.setText("reload Tree");

        btnCopySelProducts.setText("Copy All Selected Product");
        btnCopySelProducts.setEnabled(false);
        btnCopySelProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopySelProductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnReloadTree)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCopySelProducts)
                .addGap(0, 85, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloadTree)
                    .addComponent(btnCopySelProducts))
                .addContainerGap(528, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jTabbedPane2.addTab("Display Properties", jPanel1);

        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel21.setText("Product Name:");

        jLabel24.setText("Weight:");

        jLabel27.setText("Ingrident:");

        jLabel23.setText("Price:");

        jLabel26.setText("Use By Date:");

        jLabel25.setText("Packed Date:");

        jLabel22.setText("Bar Code:");

        jButton4.setText("Date");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Date");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Reload");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPrice.setToolTipText("");

        txtWeight.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));

        txtIngrident.setColumns(20);
        txtIngrident.setRows(5);
        jScrollPane2.setViewportView(txtIngrident);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBarCode)
                    .addComponent(txtPrice)
                    .addComponent(txtPackDate)
                    .addComponent(txtUseByDate)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtWeightUom, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(183, 183, 183))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtWeightUom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtPackDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtUseByDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton1))
                    .addComponent(jButton6))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jButton3.setText("Print Label");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel28.setText("Number of Labels to Print:");

        txtLabelCount.setText("1");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 81, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(txtLabelCount)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtLabelCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jButton3)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Print Product Lables", jPanel5);

        panelWebImage.setEnabled(false);

        javax.swing.GroupLayout panelWebImageLayout = new javax.swing.GroupLayout(panelWebImage);
        panelWebImage.setLayout(panelWebImageLayout);
        panelWebImageLayout.setHorizontalGroup(
            panelWebImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        panelWebImageLayout.setVerticalGroup(
            panelWebImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Web Products", panelWebImage);

        jPanel14.setLayout(new java.awt.BorderLayout());

        btnRunReport.setText("Run");
        btnRunReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunReportActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSupplierName)
                .addGap(32, 32, 32)
                .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnRunReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReload)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSupplierName)
                    .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRunReport)
                    .addComponent(btnReload))
                .addContainerGap())
        );

        jPanel14.add(jPanel15, java.awt.BorderLayout.PAGE_START);

        panelJasper.setBackground(new java.awt.Color(153, 255, 204));
        panelJasper.setPreferredSize(new java.awt.Dimension(680, 450));
        panelJasper.setLayout(new java.awt.GridLayout(1, 0));
        jPanel14.add(panelJasper, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Report", jPanel14);

        add(jTabbedPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public String getEditScanCode() {
        return editScanCode.getText();
    }

    public JButton getButtonFind() {
        return buttonFind;
    }

    private void btnCopyProductIdToSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyProductIdToSupplierActionPerformed
        editSupplierCode.setText(panelProductIdPicker.textIdField.getText());
//        genericValueMapBrandComboBox.checkItem();
    }//GEN-LAST:event_btnCopyProductIdToSupplierActionPerformed

    protected void showSelectandFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            lblImage.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(field));
            copyWebProductImagePanel.imageLabelSave.setIcon(BaseHelper.getImage(txtmediumImageUrl.getText()));
        } else {
            lblImage.setIcon(null);
            copyWebProductImagePanel.imageLabelSave.setIcon(null);

        }
    }

    protected void showLabelFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            jLabel31.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(field));
        } else {
            jLabel31.setIcon(null);
        }
    }

    private void btnSmallIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIconActionPerformed
        txtsmallImageUrl.setText(BaseHelper.selectandSetFileName("small", panelProductIdPicker.textIdField.getText(), 60, 60));
    }//GEN-LAST:event_btnSmallIconActionPerformed

    private void btnMediumIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMediumIconActionPerformed
        txtmediumImageUrl.setText(BaseHelper.selectandSetFileName("small-60", panelProductIdPicker.textIdField.getText(), 48, 48));
    }//GEN-LAST:event_btnMediumIconActionPerformed

    private void btnLargeIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLargeIconActionPerformed
        txtlargeImageUrl.setText(BaseHelper.selectandSetFileName("large", panelProductIdPicker.textIdField.getText(), 240, 240));
    }//GEN-LAST:event_btnLargeIconActionPerformed

    private void btnDetailIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailIconActionPerformed
        txtdetailImageUrl.setText(BaseHelper.selectandSetFileName("large-200", panelProductIdPicker.textIdField.getText(), 200, 200));        // TODO add your handling code here:
    }//GEN-LAST:event_btnDetailIconActionPerformed

    private void btnOriginalIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIconActionPerformed

        if (cbScaleImage.isSelected()) {
            File filePath = BaseHelper.getImageFilePath("original");

            txtoriginalImageUrl.setText(BaseHelper.CopyImageSetFileName("original", filePath, panelProductIdPicker.textIdField.getText(), 0, 0));
            txtmediumImageUrl.setText(BaseHelper.CopyImageSetFileName("small-60", filePath, panelProductIdPicker.textIdField.getText(), 60, 60));
            txtsmallImageUrl.setText(BaseHelper.CopyImageSetFileName("small", filePath, panelProductIdPicker.textIdField.getText(), 48, 48));
            txtlargeImageUrl.setText(BaseHelper.CopyImageSetFileName("large", filePath, panelProductIdPicker.textIdField.getText(), 240, 240));
            txtdetailImageUrl.setText(BaseHelper.CopyImageSetFileName("large-200", filePath, panelProductIdPicker.textIdField.getText(), 200, 200));        // TODO add your handling code here:
            showSelectandFileImage(txtoriginalImageUrl.getText());
//            btnSmallIconActionPerformed(evt);
//            btnMediumIconActionPerformed(evt);
//            btnLargeIconActionPerformed(evt);
//            btnDetailIconActionPerformed(evt);

        } else {
            txtoriginalImageUrl.setText(BaseHelper.selectandSetFileName("original", panelProductIdPicker.textIdField.getText(), 0, 0));
        }
    }//GEN-LAST:event_btnOriginalIconActionPerformed

    private void txtsmallImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsmallImageUrlFocusGained
        showSelectandFileImage(txtsmallImageUrl.getText());
    }//GEN-LAST:event_txtsmallImageUrlFocusGained

    private void txtmediumImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtmediumImageUrlFocusGained
        showSelectandFileImage(txtmediumImageUrl.getText());
    }//GEN-LAST:event_txtmediumImageUrlFocusGained

    private void txtlargeImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtlargeImageUrlFocusGained
        showSelectandFileImage(txtlargeImageUrl.getText());
    }//GEN-LAST:event_txtlargeImageUrlFocusGained

    private void txtdetailImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtdetailImageUrlFocusGained
        showSelectandFileImage(txtdetailImageUrl.getText());
    }//GEN-LAST:event_txtdetailImageUrlFocusGained

    private void txtoriginalImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtoriginalImageUrlFocusGained
        showSelectandFileImage(txtoriginalImageUrl.getText());
    }//GEN-LAST:event_txtoriginalImageUrlFocusGained

    private void editPurchasePriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPurchasePriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editPurchasePriceActionPerformed

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String productName = txtProductName.getText();
            String barCode = txtBarCode.getText();
          //  if (txtBarCode.getText().length() == 12) {
            //      barCode = "0" + barCode;
            //  }

            BigDecimal qty = new BigDecimal(txtWeight.getText());
            BigDecimal price = new BigDecimal(txtPrice.getText());
            String packedDate = txtPackDate.getText();;
            String useBy = txtUseByDate.getText();;
            String ingrifent = txtIngrident.getText();;
            String weightUom = txtWeightUom.getText();
            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            printPackingLabel(productName,
                    barCode, qty, price, packedDate,
                    useBy, ingrifent, weightUom, new BigDecimal(txtLabelCount.getText()).intValue(), pservice);
        } catch (Exception e) {
            Debug.logError(e, module);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtPackDate, locale);
        //previously Selectd date
        java.util.Date selectedDate = dp.parseDate(txtPackDate.getText());
        dp.setSelectedDate(selectedDate);
        dp.start(txtPackDate);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtUseByDate, locale);
        //previously Selectd date
        java.util.Date selectedDate = dp.parseDate(txtUseByDate.getText());
        dp.setSelectedDate(selectedDate);
        dp.start(txtUseByDate);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        setLabelPrintFields();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String fileDir = "/images/products/" + panelProductIdPicker.textIdField.getText() + "/";
        String message = "Do you want to Delete Directory " + fileDir + "?";
        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Delete", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                BaseHelper.deleteDirectoryContent(fileDir);
                BaseHelper.clearImageStore();
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearImageStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearImageStoreActionPerformed
        BaseHelper.clearImageStore();
    }//GEN-LAST:event_btnClearImageStoreActionPerformed

    private void btnCopySelProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopySelProductsActionPerformed

        for (int i = 0; i < copyWebProductImagePanel.jTable1.getModel().getRowCount(); ++i) {
            if ((Boolean) copyWebProductImagePanel.jTable1.getModel().getValueAt(i, 2) == true) {
                copyWebProductImagePanel.getRowData(i);
                copyDataFromWebPanel();
                if (isValidFields()) {
                    saveItem();
                } else {
                    OrderMaxOptionPane.showMessageDialog(null, "Not importing invalid data");
                    break;
                }

            }
        }
        /*
         // TODO add your handling code here:
         BigDecimal getVal = new BigDecimal("1.1");
         String str = taxable.getSelectedItem();

         if (txtGstPrice.getText() != null && txtGstPrice.getText().isEmpty() == false) {
         if (str != null && "Y".equals(str)) {
         BigDecimal val = new BigDecimal(txtGstPrice.getText());
         // divide bg1 with bg2 with 3 scale

         val = val.divide(getVal, 5, RoundingMode.CEILING);
         editBasePrice.setText(val.toString());
         editListPrice.setText(val.toString());
         } else {
         BigDecimal val = new BigDecimal(txtGstPrice.getText());
         // divide bg1 with bg2 with 3 scale
         editBasePrice.setText(val.toString());
         editListPrice.setText(val.toString());
         }
         }
         */
    }//GEN-LAST:event_btnCopySelProductsActionPerformed

    private void btnRunReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunReportActionPerformed
        //        }
    }//GEN-LAST:event_btnRunReportActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
    }//GEN-LAST:event_btnReloadActionPerformed

    private void comboEntityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEntityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEntityNameActionPerformed

    private void comboFacilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFacilityActionPerformed
        isModified = true;
    }//GEN-LAST:event_comboFacilityActionPerformed

    private void buttonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonFindActionPerformed

    private void btnCreateProductIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateProductIdActionPerformed
        /*        String departmentId = ((ComboKey) comboDepartmentName.getSelectedItem())._id;
         String brandName = ((ComboKey) comboBrand.getSelectedItem())._name;
         if ("All".equals(brandName) == true || "All".equals(departmentId) == true) {
         int reply = OrderMaxOptionPane.showConfirmDialog(null, "Brand or Department is invalid", "Error", JOptionPane.YES_NO_CANCEL_OPTION);
         panelProductIdPicker.textIdField.setText("");
         return;
         }

         String name = editInternalName.getText().trim();

         panelProductIdPicker.textIdField.setText(generateProductCode(departmentId, brandName, name));
         // TODO add your handling code here:
         */
        ControllerOptions option = new ControllerOptions();
        option.put("X", 350);
        option.put("Y", 100);
//                    treePath = (Map<Integer, TreeNode>) pOptions.get("treePath");
        option.put("treePath", treePath);
        final CreateProductIdPanel viewer = new CreateProductIdPanel(option);
        //viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
        option.setSimpleScreenInterface(viewer);
        final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(option);
        frame.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                            panelProductIdPicker.textIdField.setText((String) viewer.productPickerEditPanel.getEntityValue());
                            editProductName.setText(viewer.panelBrandCategoryPicker.getEntityValue() + " " + viewer.txtName.getText());
                            editInternalName.setText((String) viewer.productPickerEditPanel.getEntityValue());
                            txtBrandName.setText((String) viewer.panelBrandCategoryPicker.getEntityValue());

                            try {
                                Debug.logInfo("viewer.isVariantComboBoxModel.getSelectedItem()  " + viewer.isVariantComboBoxModel.getSelectedItem(), module);
                                Debug.logInfo("YesNoConditionSelectSingleton.getKeyFromDisplayName(viewer.isVariantComboBoxModel.getSelectedItem())  " + YesNoConditionSelectSingleton.getKeyFromDisplayName(viewer.isVariantComboBoxModel.getSelectedItem()), module);
                                isVariantComboBoxModel.setSelectedItem(viewer.isVariantComboBoxModel.getSelectedItem());
                                productVariantPickerEditPanel.textIdField.setText((String) viewer.productVariantPickerEditPanel.getEntityValue());
                            } catch (Exception ex) {
                                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {
                                isVirtualComboBoxModel.setSelectedItem(viewer.isVirtualComboBoxModel.getSelectedItem());
                                /* if (viewer.getSelectedPostalContact() != null) {
                                 panel.getOrder().addContactMech("BILLING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
                                 //                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                 panel.setDialogFields();
                                 //                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                 Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                 }*/
                            } catch (Exception ex) {
                                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
    }//GEN-LAST:event_btnCreateProductIdActionPerformed

    private void btnInternalNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInternalNameActionPerformed
        editInternalName.setText(editProductName.getText());
    }//GEN-LAST:event_btnInternalNameActionPerformed

    private void btnGenerateScanCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateScanCodeActionPerformed
        Debug.logInfo("eventGenerateScanCode 1  ", module);
        String prodId = panelProductIdPicker.textIdField.getText();
        Debug.logInfo("eventGenerateScanCode 2  ", module);
        String scanCode = generateScanCode(prodId);
        Debug.logInfo("barContent 1  " + prodId, module);
//            EAN13CodeBuilder eAN13CodeBuilder = new EAN13CodeBuilder(prodId);       
//           String scanCode = eAN13CodeBuilder.getCode();
        Debug.logInfo("barContent 2  " + prodId, module);

        Debug.logInfo("eventGenerateScanCode 4  ", module);

        Debug.logInfo("eventGenerateScanCode 5  ", module);

        editScanCode.setText(scanCode);
    }//GEN-LAST:event_btnGenerateScanCodeActionPerformed

    private void editInternalNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editInternalNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editInternalNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
        String barCode = txtBarCode.getText();
        if (txtBarCode.getText().length() == 12) {
            barCode = "0" + barCode;
        }
        org.ofbiz.ordermax.product.CustomProductPanel.printLabelTest1(pservice, barCode);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
        String barCode = txtBarCode.getText();
        if (txtBarCode.getText().length() == 12) {
            barCode = "0" + barCode;
        }
        org.ofbiz.ordermax.product.CustomProductPanel.printLabelTest(pservice, barCode);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    public static boolean printLabelTest(PrintService printService, String label) {

        if (printService == null || label == null) {
            System.err.println("[Print Label] print service or label is invalid.");
            return false;
        }
        String czas = new SimpleDateFormat("d MMMMM yyyy'r.' HH:mm s's.'").format(new Date());
        String command
                = "^XA\n\r^MNM\n\r^FO050,50\n\r^B8N,100,Y,N\n\r^FD1234567\n\r^FS\n\r^PQ3\n\r^XZ";

        byte[] data;
        data = command.getBytes(StandardCharsets.US_ASCII);
        Doc doc = new SimpleDoc(data, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);

        boolean result = false;
        try {
            printService.createPrintJob().print(doc, null);
            result = true;
        } catch (PrintException e) {
            e.printStackTrace();
        }
        return result;
    }

    //test print

    public static boolean printLabelTest1(PrintService printService, String label) {
        if (printService == null || label == null) {
            System.err.println("[Print Label] print service or label is invalid.");
            return false;
        }
        String czas = new SimpleDateFormat("d MMMMM yyyy'r.' HH:mm s's.'").format(new Date());
        String command
                = "N\n"
                + "A50,50,0,2,2,2,N,\"" + label + "\"\n"
                + "B50,100,0,1,2,2,170,B,\"" + label + "\"\n"
                + "A50,310,0,3,1,1,N,\"" + czas + "\"\n"
                + "P1\n";

        byte[] data;
        data = command.getBytes(StandardCharsets.US_ASCII);
        Doc doc = new SimpleDoc(data, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);

        boolean result = false;
        try {
            printService.createPrintJob().print(doc, null);
            result = true;
        } catch (PrintException e) {
            e.printStackTrace();
        }
        return result;
    }

    static public void printPackingLabel(String productName,
            String barCode, BigDecimal qty, BigDecimal price, String packedDate,
            String useBy, String ingrident, String weightUom, int numberofLabels, PrintService pservice) {
        try {

            DocPrintJob job = pservice.createPrintJob();
            String priceStr = "";
            if (price.equals(BigDecimal.ZERO) == false) {
                priceStr = price.setScale(2).toPlainString();//price.toString();
            }

            String weightStr = "";
            if (qty.equals(BigDecimal.ZERO) == false) {
                weightStr = qty.setScale(0).toPlainString();;
            }

            String commands  //= ;"^XA\n\r^MNM\n\r^FO050,50\n\r^B8N,100,Y,N\n\r^FD1234567\n\r^FS\n\r^PQ3\n\r^XZ";
                   /* = "                   ^XA"
                     +"^FO100,100^BY3"
                     +"^BE,N,150,N,N"
                     +"^FD" + barCode + "^FS"
                     +"^XZ";*/

                    = "^FX Delete Format^FS"
                    + "^XA ^EF^FS "
                    + "^XZ ^FX Save Format^FS"
                    + "^XA^DFFORMAT^FS"
                    + "^LH0,0 ^BY2,3,100 ^FO30,0^CFD50,40^FN1^FS "
                    + "^FO270,120,^BY3,^BE,N,10,N,N^FN2^FA20^FS"
                    + "^FO10,265^CFB0,20^FN3^FA50^FS"
                    + "^FO10,300^CFD0,20^FN4^FA50^FS"
                    + "^FO10,325^CFD0,20^FN5^FA50^FS"
                    + "^FX Item Details^FS"
                    + "^FO20, 60^CFD0,15^FN7^FA50^FS"
                    + "^FO115, 60^CFB0,20^FN13^FA50^FS"
                    + "^FO20,100^CFD0,15^FN8^FA50^FS"
                    + "^FO115,100^CFD0,15^FN14^FA50^FS"
                    + "^FO20,135^CFD0,15^FN9^FA50^FS"
                    + "^FO115,135^CFD0,15^FN15^FA50^FS"
                    + "^FO20,165^CFD0,15^FN10^FA50^FS"
                    + "^FO20,200^CFD0,15^FN16^FA50^FS"
                    + "^FO270,60^CFD0,15^FN11^FA50^FS"
                    + "^FO270,90^CFD0,15^FN18^FA50^FS"
                    + "^FO570,160^CFD0,15^FN17^FA50^FS"
                    + "^FO570,90^CFD0,15^FN19^FA50^FS"
                    + "^FO570,120^CFD0,15^FN20^FA50^FS"
                    + "^FO570,150^CFD0,15^FN21^FA50^FS"
                    + "^FO570,180^CFD0,15^FN22^FA50^FS"
                    + "^FO570,210^CFD0,15^FN23^FA50^FS"
                    + "^FN19^FDPacked By:^FS"
                    + "^FN20^FDMax Spices Pty Ltd^FS"
                    + "^FN21^FDUnit 3, 5 Stoddart Road^FS"
                    + "^FN22^FDProspect, NSW 2146 ^FS"
                    + "^FN23^FDph: 9631 5011^FS"
                    /*                   
                    
                     "^FX Delete Format^FS"
                     + "^XA"
                     + "^EF^FS"
                     + "^XZ"
                     + "^FX Save Format^FS"
                     + "^XA^DFFORMAT^FS"
                     + "^LH30,30"
                     + "^BY2,3,100"
                     + "^FO10,20^CFD40,40^FN1^FS"
                     + "^FO60,110,^BY3,^BE,N,150,N,N^FN2^FA20^FS"
                     + "^FO10,265^CFB0,20^FN3^FA50^FS"
                     + "^FO10,300^CFD0,20^FN4^FA50^FS"
                     + "^FO10,325^CFD0,20^FN5^FA50^FS"
                     + "^FX Item Details^FS"
                    
                     + "^FO490, 100^CFD0,15^FN6^FA50^FS"
                     + "^FO490, 140^CFD0,15^FN7^FA50^FS"
                     + "^FO490, 180^CFD0,15^FN8^FA50^FS"
                     + "^FO490,220^CFD0,15^FN9^FA50^FS"
                     + "^FO490,260^CFD0,15^FN10^FA50^FS"
                     + "^FO490,300^CFD0,15^FN11^FA50^FS"
                    
                     + "^FO630, 100^CFB0,20^FN12^FA50^FS"
                     + "^FO630, 140^CFB0,20^FN13^FA50^FS"
                     + "^FO630, 180^CFD0,15^FN14^FA50^FS"
                     + "^FO630,220^CFD0,15^FN15^FA50^FS"
                     + "^FO630,260^CFD0,15^FN16^FA50^FS"
                     + "^FO520,300^CFD0,15^FN17^FA50^FS"
                     */
                    + "^XZ"
                    + "^FXSHIPPING LABEL^FS"
                    + "^XA"
                    + "^LL990"
                    + "^LH10,5"
                    //+ "^FO10,10^GB775,355,4^FS" //outer box
                    + "^FO10,95^GB775,4,4^FS" //horizontal line

                    //+ "^FO500,95^GB4,355,4^FS" //vertical line
                    + "^XFFORMAT^FS"
                    + "^FN1^FD" + productName + "^FS"
                    + "^FN2^FD" + barCode + "^FS"
                    + "^FN3^FDMax Spices Pty Ltd^FS"
                    + "^FN4^FDUnit 3, 5 Stoddart Road^FS"
                    + "^FN5^FDProspect, NSW 2146   ph: 9631 5011^FS"
                    + "^FN6^FDPrice:^FS"
                    + "^FN7^FDWeight:^FS"
                    + "^FN8^FDPacked:^FS"
                    + "^FN9^FDUse By:^FS"
                    + "^FN10^FDIngrident:^FS"
                    + "^FN11^FD^FS"
                    + "^FN12^FD$" + priceStr + "^FS"
                    + "^FN13^FD" + weightStr + " " + weightUom + "^FS"
                    + "^FN14^FD" + packedDate + "^FS"
                    + "^FN15^FD" + useBy + "^FS"
                    + "^FN16^FD^FS"
                    + "^FN17^FD" + ingrident + "^FS"
                    + "^PQ" + numberofLabels
                    + "^XZ";

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);

        } catch (PrintException ex) {
            Debug.logError(ex, module);
        }
    }

    static public void printTransperentShelfLabel(String productName1, BigDecimal price1, 
            String barCode1, String productName2, BigDecimal price2, String barCode2, int numberofLabels, PrintService pservice) {
        try {

            DocPrintJob job = pservice.createPrintJob();
            String priceStr1 = "";
            if (price1.equals(BigDecimal.ZERO) == false) {
                priceStr1 = price1.setScale(2).toPlainString();//price.toString();
            }

            String priceStr2 = "";
          
            if (priceStr2.equals(BigDecimal.ZERO) == false) {
                priceStr2 = price2.setScale(2).toPlainString();;
            }

            String commands

                    = "    \n"
                    + "^FX Delete Format^FS\n"
                    + "^XA ^EF^FS \n"
                    + "^XZ ^FX Save Format^FS\n"
                    + "^XA^DFFORMAT^FS\n"
                    + "^LH20,0 ^BY2,3,100^FO10,10^CFD50,40^FN1^FS \n"
                    + "^LH20,0 ^BY2,3,100^FO10,70^CFD50,40^FN2^FS\n"
                    + "^LH20,0 ^BY2,3,100^FO10,150^CFD50,40^FN3^FS \n"
                    + "^LH20,0 ^BY2,3,100^FO10,200^CFD50,40^FN4^FS\n"
                    + "^FO500,40,^BY3,3,50,^BE,N,10,N,N^FN5^FA20^FS\n"
                    + "^FO500,180,^BY3,3,50,^BE,N,10,N,N^FN6^FA20^FS\n"
                    
                    + "^XZ^FXSHIPPING LABEL^FS\n"
                    + "^XA^LL660^LH0,0^FO10,125^GB775,4,4^FS\n"
                    + "^XFFORMAT^FS^FN1^FD" + productName1 + "^FS\n"
                    + "^FN2^FDPrice: $" + priceStr1 + "^FS\n"
                    + "^FN5^FD"+barCode1+"^FS\n"
                    + "^XFFORMAT^FS^FN3^FD" + productName2 + "^FS\n"
                    + "^FN4^FDPrice: $" + priceStr2 + "^FS\n"
                    + "^FN6^FD"+barCode2+"^FS\n"                    
                    + "^PQ1^XZ\n"
                    + "";

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);

            System.out.println(commands);
        } catch (PrintException ex) {
            Debug.logError(ex, module);
        }
    }

    static public void printTransperentPackingLabel(String productName,
            String barCode, BigDecimal qty, BigDecimal price, String packedDate,
            String useBy, String ingrident, String weightUom, String country, int numberofLabels, PrintService pservice) {
        try {

            DocPrintJob job = pservice.createPrintJob();
            String priceStr = "";
            if (price.equals(BigDecimal.ZERO) == false) {
                priceStr = price.setScale(2).toPlainString();//price.toString();
            }

            String weightStr = "";
            if (qty.equals(BigDecimal.ZERO) == false) {
                weightStr = qty.setScale(0).toPlainString();;
            }

            String commands  //= ;"^XA\n\r^MNM\n\r^FO050,50\n\r^B8N,100,Y,N\n\r^FD1234567\n\r^FS\n\r^PQ3\n\r^XZ";
                   /* = "                   ^XA"
                     +"^FO100,100^BY3"
                     +"^BE,N,150,N,N"
                     +"^FD" + barCode + "^FS"
                     +"^XZ";*/

                    = "^FX Delete Format^FS"
                    + "^XA ^EF^FS "
                    + "^XZ ^FX Save Format^FS"
                    + "^XA^DFFORMAT^FS"
                    + "^LH0,0 ^BY2,3,100 ^FO30,0^CFD50,40^FN1^FS "
                    + "^FO290,130,^BY2,^BE,N,8,N,N^FN2^FA20^FS"
                    + "^FO10,265^CFB0,20^FN3^FA50^FS"
                    + "^FO10,300^CFD0,20^FN4^FA50^FS"
                    + "^FO10,325^CFD0,20^FN5^FA50^FS"
                    + "^FX Item Details^FS"
                    + "^FO20, 60^CFD0,15^FN7^FA50^FS"
                    + "^FO115, 60^CFB0,20^FN13^FA50^FS"
                    + "^FO20,100^CFD0,15^FN8^FA50^FS"
                    + "^FO115,100^CFD0,15^FN14^FA50^FS"
                    + "^FO20,135^CFD0,15^FN9^FA50^FS"
                    + "^FO115,135^CFD0,15^FN15^FA50^FS"
                    + "^FO20,165^CFD0,15^FN10^FA50^FS"
                    + "^FO20,200^CFD0,15^FN17^FA50^FS"
                    + "^FO20,200^CFD0,15^FN16^FA50^FS"
                    + "^FO270,60^CFD0,15^FN11^FA50^FS"
                    + "^FO270,90^CFD0,15^FN18^FA50^FS"
                    + "^FO570,90^CFD0,15^FN19^FA50^FS"
                    + "^FO570,120^CFD0,15^FN20^FA50^FS"
                    + "^FO570,150^CFD0,15^FN21^FA50^FS"
                    + "^FO570,180^CFD0,15^FN22^FA50^FS"
                    + "^FO570,210^CFD0,15^FN23^FA50^FS"
                    + "^FN19^FDPacked By:^FS"
                    + "^FN20^FDMax Spices Pty Ltd^FS"
                    + "^FN21^FDUnit 3, 5 Stoddart Road^FS"
                    + "^FN22^FDProspect, NSW 2146 ^FS"
                    + "^FN23^FDph: 9631 5011^FS"
                    /*                   
                    
                     "^FX Delete Format^FS"
                     + "^XA"
                     + "^EF^FS"
                     + "^XZ"
                     + "^FX Save Format^FS"
                     + "^XA^DFFORMAT^FS"
                     + "^LH30,30"
                     + "^BY2,3,100"
                     + "^FO10,20^CFD40,40^FN1^FS"
                     + "^FO60,110,^BY3,^BE,N,150,N,N^FN2^FA20^FS"
                     + "^FO10,265^CFB0,20^FN3^FA50^FS"
                     + "^FO10,300^CFD0,20^FN4^FA50^FS"
                     + "^FO10,325^CFD0,20^FN5^FA50^FS"
                     + "^FX Item Details^FS"
                    
                     + "^FO490, 100^CFD0,15^FN6^FA50^FS"
                     + "^FO490, 140^CFD0,15^FN7^FA50^FS"
                     + "^FO490, 180^CFD0,15^FN8^FA50^FS"
                     + "^FO490,220^CFD0,15^FN9^FA50^FS"
                     + "^FO490,260^CFD0,15^FN10^FA50^FS"
                     + "^FO490,300^CFD0,15^FN11^FA50^FS"
                    
                     + "^FO630, 100^CFB0,20^FN12^FA50^FS"
                     + "^FO630, 140^CFB0,20^FN13^FA50^FS"
                     + "^FO630, 180^CFD0,15^FN14^FA50^FS"
                     + "^FO630,220^CFD0,15^FN15^FA50^FS"
                     + "^FO630,260^CFD0,15^FN16^FA50^FS"
                     + "^FO520,300^CFD0,15^FN17^FA50^FS"
                     */
                    /*
                    
                     " ^FX Delete Format^FS "
                     +" ^XA"
                     +" ^EF^FS"
                     +" ^XZ"
                     +" ^FX Save Format^FS"
                     +" ^XA^DFFORMAT^FS"
                     +" ^LH0,0"
                     +" ^BY2,3,100"
                     +" ^FO10,10^CFD50,40^FN1^FS"
                     +" ^FO270,120,^BY3,^BE,N,10,N,N^FN2^FA20^FS"
		    
                     +"^FO10,265^CFB0,20^FN3^FA50^FS"
                     +"^FO10,300^CFD0,20^FN4^FA50^FS"
                     +"^FO10,325^CFD0,20^FN5^FA50^FS"
                    
                     +"^FX Item Details^FS"                    

                     +"^FO10, 120^CFD0,15^FN7^FA50^FS"
                     +"^FO10, 160^CFD0,15^FN8^FA50^FS"
                     +"^FO10,200^CFD0,15^FN9^FA50^FS"

                     +"^FO590,120^CFD0,15^FN10^FA50^FS"
                     +"^FO590,200^CFD0,15^FN11^FA50^FS"


                     +"^FO115, 120^CFB0,20^FN13^FA50^FS"
                     +"^FO115, 160^CFD0,15^FN14^FA50^FS"
                     +"^FO115,200^CFD0,15^FN15^FA50^FS"
                     +"^FO670,120^CFD0,15^FN16^FA50^FS"
                     +"^FO590,160^CFD0,15^FN17^FA50^FS"
                     +"^FO590,240^CFD0,15^FN18^FA50^FS"
                     */
                    /*                  
                     "^FX Delete Format^FS"
                     + "^XA"
                     + "^EF^FS"
                     + "^XZ"
                     + "^FX Save Format^FS"
                     + "^XA^DFFORMAT^FS"
                     + "^LH0,0"
                     + "^BY2,3,100"
                     + "^FO010,20^CFD40,40^FN1^FS"
                     + "^FO60,110,^BY3,^BE,N,150,N,N^FN2^FA20^FS"
                     + "^FO10,265^CFB0,20^FN3^FA50^FS"
                     + "^FO10,300^CFD0,20^FN4^FA50^FS"
                     + "^FO10,325^CFD0,20^FN5^FA50^FS"
                    
                     + "^FX Item Details^FS"                    
                     + "^FO490, 100^CFD0,15^FN6^FA50^FS"
                     + "^FO490, 140^CFD0,15^FN7^FA50^FS"
                     + "^FO490, 180^CFD0,15^FN8^FA50^FS"
                     + "^FO490,220^CFD0,15^FN9^FA50^FS"
                     + "^FO490,260^CFD0,15^FN10^FA50^FS"
                     + "^FO490,300^CFD0,15^FN11^FA50^FS"
                    
                     + "^FO630, 100^CFB0,20^FN12^FA50^FS"
                     + "^FO630, 140^CFB0,20^FN13^FA50^FS"
                     + "^FO630, 180^CFD0,15^FN14^FA50^FS"
                     + "^FO630,220^CFD0,15^FN15^FA50^FS"
                     + "^FO630,260^CFD0,15^FN16^FA50^FS"
                     + "^FO520,300^CFD0,15^FN17^FA50^FS"
                     */
                    + "^XZ"
                    + "^FXSHIPPING LABEL^FS"
                    + "^XA"
                    + "^LL660"
                    + "^LH0,0"
                    //+ "^FO10,10^GB775,355,4^FS" //outer box
                    + "^FO10,40^GB775,4,4^FS" //horizontal line

                    //+ "^FO500,95^GB4,355,4^FS" //vertical line
                    + "^XFFORMAT^FS"
                    + "^FN1^FD" + productName + "^FS"
                    + "^FN2^FD" + barCode + "^FS"
                    + "^FN7^FDWeight:^FS"
                    + "^FN8^FDPacked:^FS"
                    + "^FN9^FDUse By:^FS"
                    + "^FN10^FDIngrident:^FS"
                    + "^FN11^FDOrigin:^FS"
                    + "^FN13^FD" + weightStr + " " + weightUom + "^FS"
                    + "^FN14^FD" + packedDate + "^FS"
                    + "^FN15^FD" + useBy + "^FS"
                    //         + "^FN16^FD^FS"
                    + "^FN17^FD" + ingrident + "^FS"
                    + "^FN18^FD" + country + "^FS"
                    + "^PQ" + numberofLabels
                    + "^XZ";

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);

            System.out.println(commands);
        } catch (PrintException ex) {
            Debug.logError(ex, module);
        }
    }

    static public void printPackingLabel_3x3(String productName,
            String barCode, BigDecimal qty, BigDecimal price, String packedDate,
            String useBy, String ingrident, String weightUom, int numberofLabels, PrintService pservice) {
        try {

            //PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            System.out.println("Printing to default printer printPackingLabel_3x3: " + pservice.getName());
            DocPrintJob job = pservice.createPrintJob();
            String priceStr = "";
            if (price.equals(BigDecimal.ZERO) == false) {
                priceStr = price.setScale(2).toPlainString();//price.toString();
            }

            String weightStr = "";
            if (qty.equals(BigDecimal.ZERO) == false) {
                weightStr = qty.setScale(0).toPlainString();;
            }

            String commands
                    = /*"^FX Delete Format^FS"
                     + "^XA\n"
                     + "^PR1\n"
                     + "^FO10,100\n"
                     + "^GB70,70,70,,3^FS\n"
                     + "^FO20,100\n"
                     + "^GB70,70,70,,3^FS\n"
                     + "^FO30,100\n"
                     + "^GB70,70,70,,3^FS\n"
                     + "^FO40,100\n"
                     + "^GB70,70,70,,3^FS\n"
                     + "^FO107,110^\n"
                     + "C\n"
                     + "F0,70,93\n"
                     + "^FR^FDREVERSE^FS\n"
                     + "^XZ";*/ /*"^FX Delete Format^FS" //^FX is comment start
                     + "^XA"
                     + "^EF^FS"
                     + "^XZ"
                    
                     + */ "^FX Save Format^FS"
                    /*             + "^XA^DFFORMAT^FS"
                     + "^LH30,30"
                     + "^BY2,3,100"
                     + "^FO10,20^CFD40,40^FN1^FS"
                     + "^FO10,110^BE^FN2^FA20^FS"
                     + "^FO10,260^CFB0,20^FN3^FA50^FS"
                     + "^FO10,295^CFD0,20^FN4^FA50^FS"
                     + "^FO10,320^CFD0,20^FN5^FA50^FS"
                     + "^FX Item Details^FS"
                     + "^FO490, 20^CFD0,15^FN6^FA50^FS"
                     + "^FO490, 70^CFD0,15^FN7^FA50^FS"
                     + "^FO490, 120^CFD0,15^FN8^FA50^FS"
                     + "^FO490,170^CFD0,15^FN9^FA50^FS"
                     + "^FO490,220^CFD0,15^FN10^FA50^FS"
                     + "^FO490,270^CFD0,15^FN11^FA50^FS"
                     + "^FO630, 20^CFB0,20^FN12^FA50^FS"
                     + "^FO630, 70^CFB0,20^FN13^FA50^FS"
                     + "^FO630, 120^CFD0,15^FN14^FA50^FS"
                     + "^FO630,170^CFD0,15^FN15^FA50^FS"
                     + "^FO630,220^CFD0,15^FN16^FA50^FS"
                     + "^FO520,270^CFD0,15^FN17^FA50^FS"
                     + "^XZ"
                     */
                    + "^FXSHIPPING LABEL^FS"
                    + "^XA"
                    + "^LH20,20"
                    + "^FO10,10^GB775,355,4^FS"
                    + "^FO10,110^GB490,4,4^FS"
                    + "^FO500,10^GB4,355,4^FS"
                    + "^XFFORMAT^FS"
                    + "^FN1^FD" + productName + "^FS"
                    + "^FN2^FD" + barCode + "^FS"
                    + "^FN3^FDMax Spices Pty Ltd^FS"
                    + "^FN4^FDUnit 3, 5 Stoddart Road^FS"
                    + "^FN5^FDProspect, NSW 2146   ph: 9631 5011^FS"
                    + "^FN6^FDPrice:^FS"
                    + "^FN7^FDWeight:^FS"
                    + "^FN8^FDPacked:^FS"
                    + "^FN9^FDUse By:^FS"
                    + "^FN10^FDIngrident:^FS"
                    + "^FN11^FD^FS"
                    + "^FN12^FD$" + priceStr + "^FS"
                    + "^FN13^FD" + weightStr + " " + weightUom + "^FS"
                    + "^FN14^FD" + packedDate + "^FS"
                    + "^FN15^FD" + useBy + "^FS"
                    + "^FN16^FD^FS"
                    + "^FN17^FD" + ingrident + "^FS"
                    + "^PQ" + numberofLabels
                    + "^XZ";

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);

        } catch (PrintException ex) {
            Debug.logError(ex, module);
        }
    }

    /*
     static public void printPackingLabel(String productName,
     String barCode, BigDecimal qty, BigDecimal price, String packedDate,
     String useBy, String ingrident, String weightUom, int numberofLabels, PrintService pservice) {
     try {

     //            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
     System.out.println("Printing to default printer: " + pservice.getName());
     DocPrintJob job = pservice.createPrintJob();
     String priceStr = "";
     if (price.equals(BigDecimal.ZERO) == false) {
     priceStr = price.setScale(2).toPlainString();//price.toString();
     }

     String weightStr = "";
     if (qty.equals(BigDecimal.ZERO) == false) {
     weightStr = qty.setScale(0).toPlainString();;
     }
     //                 +"^POI"
     String commands
     = "^FX Delete Format^FS"
     + "^XA"
     + "^EF^FS"
     + "^XZ"
                    
     + "^FX Save Format^FS"
     + "^XA^DFFORMAT^FS"
     + "^LL660"                    
     + "^LH20,-10"
     + "^BY2,3,110"
     + "^FO20,40^CFD40,40^FN1^FS"
     + "^FO50,120^BE^FN2^FA20^FS"
     + "^FO30,260^CFB0,20^FN3^FA50^FS"
     + "^FO30,295^CFD0,20^FN4^FA50^FS"
     + "^FO30,320^CFD0,20^FN5^FA50^FS"
                    
     + "^FX Item Details^FS"
     + "^FO510, 40^CFD0,15^FN6^FA50^FS"
     + "^FO510, 90^CFD0,15^FN7^FA50^FS"
     + "^FO510, 140^CFD0,15^FN8^FA50^FS"
     + "^FO510,190^CFD0,15^FN9^FA50^FS"
     + "^FO510,240^CFD0,15^FN10^FA50^FS"
     + "^FO510,290^CFD0,15^FN11^FA50^FS"
                    
     + "^FO630, 40^CFB0,20^FN12^FA50^FS"
     + "^FO630, 90^CFB0,20^FN13^FA50^FS"
     + "^FO630, 140^CFD0,15^FN14^FA50^FS"
     + "^FO630,190^CFD0,15^FN15^FA50^FS"
     + "^FO630,240^CFD0,15^FN16^FA50^FS"
     + "^FO520,290^CFD0,15^FN17^FA50^FS"
     + "^XZ"

     + "^FXSHIPPING LABEL^FS"
     + "^XA"
   

     + "^LH20,-10"
     + "^FO10,10^GB775,355,4^FS"
     + "^FO10,110^GB490,4,4^FS"
     + "^FO500,10^GB4,355,4^FS"
     + "^XFFORMAT^FS"
     + "^FN1^FD" + productName + "^FS"
     + "^FN2^FD" + barCode + "^FS"
     + "^FN3^FDMax Spices Pty Ltd^FS"
     + "^FN4^FDUnit 3, 5 Stoddart Road^FS"
     + "^FN5^FDProspect, NSW 2146   ph: 9631 5011^FS"
     + "^FN6^FDPrice:^FS"
     + "^FN7^FDWeight:^FS"
     + "^FN8^FDPacked:^FS"
     + "^FN9^FDUse By:^FS"
     + "^FN10^FDIngrident:^FS"
     + "^FN11^FD^FS"
     + "^FN12^FD$" + priceStr + "^FS"
     + "^FN13^FD" + weightStr + " " + weightUom + "^FS"
     + "^FN14^FD" + packedDate + "^FS"
     + "^FN15^FD" + useBy + "^FS"
     + "^FN16^FD^FS"
     + "^FN17^FD" + ingrident + "^FS"
     + "^PQ" + numberofLabels
     + "^XZ";

     DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
     Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
     job.print(doc, null);

     } catch (PrintException ex) {
     Debug.logError(ex, module);
     }
     }
     */
    /*  
     static public void printPackingLabel(String productName,
     String barCode, BigDecimal qty, BigDecimal price, String packedDate,
     String useBy, String ingrident, String weightUom, int numberofLabels, PrintService pservice) {
     try {

     //            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
     System.out.println("Printing to default printer: " + pservice.getName());
     DocPrintJob job = pservice.createPrintJob();
     String priceStr = "";
     if (price.equals(BigDecimal.ZERO) == false) {
     priceStr = price.setScale(2).toPlainString();//price.toString();
     }

     String weightStr = "";
     if (qty.equals(BigDecimal.ZERO) == false) {
     weightStr = qty.setScale(0).toPlainString();;
     }

     String commands
     = "^FX Delete Format^FS"
     + "^XA"
     + "^EF^FS"
     + "^XZ"
                    
     + "^FX Save Format^FS"
     + "^XA^DFFORMAT^FS"
     + "^LH40,40"
     + "^BY2,3,100"
     + "^FO10,20^CFD40,40^FN1^FS"
     + "^FO30,110^BE^FN2^FA20^FS"
     + "^FO10,260^CFB0,20^FN3^FA50^FS"
     + "^FO10,295^CFD0,20^FN4^FA50^FS"
     + "^FO10,320^CFD0,20^FN5^FA50^FS"
                    
     + "^FX Item Details^FS"
     + "^FO490, 20^CFD0,15^FN6^FA50^FS"
     + "^FO490, 70^CFD0,15^FN7^FA50^FS"
     + "^FO490, 120^CFD0,15^FN8^FA50^FS"
     + "^FO490,170^CFD0,15^FN9^FA50^FS"
     + "^FO490,220^CFD0,15^FN10^FA50^FS"
     + "^FO490,270^CFD0,15^FN11^FA50^FS"
     + "^FO630, 20^CFB0,20^FN12^FA50^FS"
     + "^FO630, 70^CFB0,20^FN13^FA50^FS"
     + "^FO630, 120^CFD0,15^FN14^FA50^FS"
     + "^FO630,170^CFD0,15^FN15^FA50^FS"
     + "^FO630,220^CFD0,15^FN16^FA50^FS"
     + "^FO520,270^CFD0,15^FN17^FA50^FS"
     + "^XZ"
     + "^FXSHIPPING LABEL^FS"
     + "^XA"
     + "^LL990"
     + "^LH40,40"
     + "^FO10,10^GB775,355,4^FS"
     + "^FO10,110^GB490,4,4^FS"
     + "^FO500,10^GB4,355,4^FS"
     + "^XFFORMAT^FS"
     + "^FN1^FD" + productName + "^FS"
     + "^FN2^FD" + barCode + "^FS"
     + "^FN3^FDMax Spices Pty Ltd^FS"
     + "^FN4^FDUnit 3, 5 Stoddart Road^FS"
     + "^FN5^FDProspect, NSW 2146   ph: 9631 5011^FS"
     + "^FN6^FDPrice:^FS"
     + "^FN7^FDWeight:^FS"
     + "^FN8^FDPacked:^FS"
     + "^FN9^FDUse By:^FS"
     + "^FN10^FDIngrident:^FS"
     + "^FN11^FD^FS"
     + "^FN12^FD$" + priceStr + "^FS"
     + "^FN13^FD" + weightStr + " " + weightUom + "^FS"
     + "^FN14^FD" + packedDate + "^FS"
     + "^FN15^FD" + useBy + "^FS"
     + "^FN16^FD^FS"
     + "^FN17^FD" + ingrident + "^FS"
     + "^PQ" + numberofLabels
     + "^XZ";

     DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
     Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
     job.print(doc, null);

     } catch (PrintException ex) {
     Debug.logError(ex, module);
     }
     }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearImageStore;
    private javax.swing.JButton btnCopyProductIdToSupplier;
    private javax.swing.JButton btnCopySelProducts;
    private javax.swing.JButton btnCreateProductId;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetailIcon;
    public javax.swing.JButton btnFixImagePath;
    private javax.swing.JButton btnGenerateScanCode;
    private javax.swing.JButton btnInternalName;
    private javax.swing.JButton btnLargeIcon;
    private javax.swing.JButton btnMediumIcon;
    private javax.swing.JButton btnOriginalIcon;
    private javax.swing.JButton btnReload;
    public javax.swing.JButton btnReloadTree;
    public javax.swing.JButton btnRunReport;
    private javax.swing.JButton btnSmallIcon;
    private javax.swing.JButton buttonFind;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbScaleImage;
    private javax.swing.JComboBox comboEntityName;
    private javax.swing.JComboBox comboFacility;
    private javax.swing.JComboBox comboSupplier;
    private javax.swing.JTextField editInternalName;
    private javax.swing.JTextField editProductName;
    private javax.swing.JTextField editPurchasePrice;
    private javax.swing.JTextField editScanCode;
    private javax.swing.JTextField editSupplierCode;
    private javax.swing.JTextField editSupplierLastPrice;
    private javax.swing.JPanel goodsidentificationPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblBasePrice;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblListPrice;
    private javax.swing.JLabel lblPurchasePrice;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JPanel listPricePanel;
    private javax.swing.JPanel panelCurrency;
    private javax.swing.JPanel panelHeight;
    private javax.swing.JPanel panelImage;
    private javax.swing.JPanel panelJasper;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelProductId1;
    private javax.swing.JPanel panelScale;
    private javax.swing.JPanel panelScaleUom;
    private javax.swing.JPanel panelTaxable;
    private javax.swing.JPanel panelTaxable1;
    private javax.swing.JPanel panelVariantProd;
    private javax.swing.JPanel panelVariantProductId;
    private javax.swing.JPanel panelVirtualProd;
    private javax.swing.JPanel panelWebImage;
    private javax.swing.JPanel panelWidth;
    private javax.swing.JPanel sellingPricePanel;
    private javax.swing.JTextArea txtAreaDescription;
    private javax.swing.JTextField txtBarCode;
    private javax.swing.JTextField txtBrandName;
    private javax.swing.JTextArea txtIngrident;
    private javax.swing.JFormattedTextField txtItemHeight;
    private javax.swing.JFormattedTextField txtItemWeight;
    private javax.swing.JTextField txtLabelCount;
    private javax.swing.JTextField txtPackDate;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JFormattedTextField txtShelfLife;
    private javax.swing.JTextField txtUseByDate;
    private javax.swing.JFormattedTextField txtWeight;
    private javax.swing.JTextField txtWeightUom;
    private javax.swing.JTextField txtdetailImageUrl;
    private javax.swing.JTextField txtlargeImageUrl;
    private javax.swing.JTextField txtmediumImageUrl;
    private javax.swing.JTextField txtoriginalImageUrl;
    private javax.swing.JTextField txtsmallImageUrl;
    // End of variables declaration//GEN-END:variables
private boolean isEnable = true;

    @Override
    public boolean isIsEnable() {
        return isEnable;
    }

    @Override
    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
        this.repaint();
    }

    private ProductComposite productComposite = null;

    public ProductPickerEditPanel panelProductIdPicker = null;

    @Override
    public ProductComposite getProductComposite() {
        return productComposite;
    }

    @Override
    public javax.swing.JTextField getProductIdTextField() {
        return panelProductIdPicker.textIdField;
    }

    @Override
    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        Debug.logInfo("setProductComposite: before " + this.productComposite.getProduct().getisVariant(), module);
        if ("Y".equals(this.productComposite.getProduct().getisVariant())) {

            if (this.productComposite.getProductAssocList() == null || this.productComposite.getProductAssocList().isEmpty()) {
                Debug.logInfo("setProductComposite: enter", module);
                ArrayList<ProductAssoc> list = LoadProductWorker.loadProductAssocToList(productComposite.getProduct().getproductId(), XuiContainer.getSession());
                this.productComposite.setProductAssocToList(list);
                Debug.logInfo("setProductComposite: " + list.size(), module);
            }
        }
        setDialogField();
        //setItem(productComposite.getProduct().getGenericValueObj());
    }

    @Override
    public void clearDialogFields() {
        try {
//            throw new Exception("clearDialogFields");
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        panelProductIdPicker.textIdField.setText("");
        editProductName.setText("");
        editInternalName.setText("");
        editListPrice.setText("");
        editPurchasePrice.setText("");
        editBasePrice.setText("");
//        editCurrency.setText((String) session.getAttribute("currency"));
//        editQtyOnHand.setText("1");
        editSupplierCode.setText("");
        editScanCode.setText("");
        editSupplierLastPrice.setText("");
        //cbTaxable.setSelected(false);
        txtItemWeight.setText("");
        txtItemHeight.setText("");
        txtAreaDescription.setText("");
        txtShelfLife.setText("");
        txtsmallImageUrl.setText("");
        txtmediumImageUrl.setText("");
        txtlargeImageUrl.setText("");
        txtdetailImageUrl.setText("");
        txtoriginalImageUrl.setText("");
        lblImage.setIcon(null);
        jLabel31.setIcon(null);
        String str;
        try {
            str = YesNoConditionSelectSingleton.getString("N");
            isScaleComboBoxModel.setSelectedItem(str);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public JTextField getEditProductId() {
        return panelProductIdPicker.textIdField;
    }

    public void refreshScreen() {
        setDialogField();
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void newItem() {
//        clearDepartmentDialogFields();
        clearDialogFields();
        jTabbedPane2.setSelectedIndex(0);
        isModified = false;
    }

    public void saveItem() {
        if (isValidFields()) {
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            getDialogField();
            String facilityId = facilityIdCombo.getSelectedItemId();
            curryProductPojo = PosProductHelper.prepareProduct(curryProductPojo, facilityId);
            curryProductPojo = PosProductHelper.prepareProductPrice(curryProductPojo, facilityId);
            curryProductPojo.categoryId = getDepartmentCode();
            curryProductPojo.categoryName = getDepartmentName();
//            curryProductPojo.brandId = curryProductPojo.categoryId + "_" + (((ComboKey) comboBrand.getSelectedItem())._name).replaceAll("\\s", "");

            if (curryProductPojo.brandId != null && curryProductPojo.brandId.length() > 20) {
                curryProductPojo.brandId = curryProductPojo.brandId.substring(0, 19);
            }

            updateProduct(curryProductPojo, delegator);
            isModified = false;
            notifyListeners(OrderMaxUtility.ITEM_SAVED, curryProductPojo.internalName, curryProductPojo.internalName);
        }
    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //protected GenericValueMapComboBox genericValueMapBrandComboBox;

    @Override
    public void setDialogField() {

        /*panelProductIdPicker.textIdFi
         eld.setText(curryProductPojo.productId);
         //        editQtyOnHand.setText(quantityOnHand.toString());
         editProductName.setText(curryProductPojo.productName);
         editInternalName.setText(curryProductPojo.internalName);

         //        comboBrand.setSelectedItem(curryProductPojo.brandName);

         editListPrice.setText(curryProductPojo.price.toString());
         editBasePrice.setText(curryProductPojo.purchasePrice.toString());
         editCurrency.setText((String) session.getAttribute("currency"));
         editSupplierCode.setText(curryProductPojo.supplierProductId);
         editScanCode.setText(curryProductPojo.eanValue);
         if (curryProductPojo.weight != null) {
         txtItemWeight.setText(curryProductPojo.weight.toString());
         }
         if (curryProductPojo.height != null) {
         txtItemHeight.setText(curryProductPojo.height.toString());
         }
         txtAreaDescription.setText(curryProductPojo.longDescription);
         if (txtAreaDescription.getText() == null || txtAreaDescription.getText().trim().isEmpty()) {
         txtAreaDescription.setText(editProductName.getText());
         }
         supplierPartyIdCombo.setSelectedItemId(curryProductPojo.supplierPartyId);
         //        .setSelectedItemId();
         //        if (supplierListBidingCombo.contains(curryProductPojo.supplierPartyId)) {
         //            comboSupplier.setSelectedIndex(supplierListBidingCombo.indexOf(curryProductPojo.supplierPartyId));
         //        }
         //        comboDepartmentName.setSelectedItem(curryProductPojo.description);
         txtsmallImageUrl.setText(curryProductPojo.smallImageUrl);
         txtmediumImageUrl.setText(curryProductPojo.mediumImageUrl);
         txtlargeImageUrl.setText(curryProductPojo.largeImageUrl);
         txtdetailImageUrl.setText(curryProductPojo.detailImageUrl);
         txtoriginalImageUrl.setText(curryProductPojo.originalImageUrl);
         showSelectandFileImage(txtoriginalImageUrl.getText());
         cbTaxable.setSelected(curryProductPojo.taxable.equals("Y"));// = String.valueOf());
         */
        try {
//            throw new Exception("set Dialog Fields");
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        isModified = false;
        //           TreeNode node = (TreeNode) val;
        //get data from table
//            String productId = node._id;
        Product productEntity = productComposite.getProduct();//.getGenericValueObj();
        panelProductIdPicker.textIdField.setEnabled(true);
        btnCreateProductId.setEnabled(true);
        if (productEntity != null) {
            String productId = productEntity.getproductId();
            if (UtilValidate.isNotEmpty(productId) && productEntity.getGenericValueObj() != null) {
                panelProductIdPicker.textIdField.setEnabled(false);
                btnCreateProductId.setEnabled(false);
                productVariantPickerEditPanel.textIdField.setEnabled(false);
            }
            panelProductIdPicker.textIdField.setText(productId);
            txtBrandName.setText(productEntity.getbrandName());
            editProductName.setText(productEntity.getproductName());
            editInternalName.setText(productEntity.getinternalName());

            if (UtilValidate.isNotEmpty(productEntity.getsmallImageUrl())) {
                txtsmallImageUrl.setText(productEntity.getsmallImageUrl());
                txtmediumImageUrl.setText(productEntity.getmediumImageUrl());
                txtlargeImageUrl.setText(productEntity.getlargeImageUrl());
                txtdetailImageUrl.setText(productEntity.getdetailImageUrl());
                txtoriginalImageUrl.setText(productEntity.getoriginalImageUrl());
            } else {
                if (productComposite.getProductContentCompositeList() == null) {
                    ProductContentCompositeList list = LoadProductWorker.loadProductContent(productComposite.getProduct().getproductId(), XuiContainer.getSession());
                    productComposite.setProductContentCompositeList(list);
                }
                ProductContentCompositeList list = productComposite.getProductContentCompositeList();
                for (ProductContentComposite composite : list.getList()) {
                    if ("SMALL_IMAGE_URL".equals(composite.getProductContent().getproductContentTypeId())) {
                        txtsmallImageUrl.setText(composite.getDataResource().getobjectInfo());
                    }
                    if ("LARGE_IMAGE_URL".equals(composite.getProductContent().getproductContentTypeId())) {

                        txtmediumImageUrl.setText(composite.getDataResource().getobjectInfo());
                        txtlargeImageUrl.setText(composite.getDataResource().getobjectInfo());
                    }
                    if ("DETAIL_IMAGE_URL".equals(composite.getProductContent().getproductContentTypeId())) {

                        txtdetailImageUrl.setText(composite.getDataResource().getobjectInfo());
                        txtoriginalImageUrl.setText(composite.getDataResource().getobjectInfo());
                    }
                }
            }

            /*if (productEntity.gettaxable() != null) {
             cbTaxable.setSelected("Y".equals(productEntity.gettaxable()));
             } else {
             cbTaxable.setSelected(false);
             }*/
            String str;
            try {
                if (UtilValidate.isNotEmpty(productEntity.getisVirtual())) {
                    str = YesNoConditionSelectSingleton.getString(productEntity.getisVirtual());
                    isVirtualComboBoxModel.setSelectedItem(str);
                }
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(productEntity.getisVariant())) {
                    str = YesNoConditionSelectSingleton.getString(productEntity.getisVariant());
                    isVariantComboBoxModel.setSelectedItem(str);
                }
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(productEntity.gettaxable())) {
                    str = YesNoConditionSelectSingleton.getString(productEntity.gettaxable());
                    taxable.setSelectedItem(str);
                }
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (UtilValidate.isNotEmpty(productEntity.getamountRequired())) {
                    str = YesNoConditionSelectSingleton.getString(productEntity.getamountRequired());
                    isScaleComboBoxModel.setSelectedItem(str);
                }

            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(productEntity.getamountUomId())) {
                    Uom uom = UomWeightSingleton.getUom(productEntity.getamountUomId());
                    scaleUom.setSelectedItem(uom);
                }
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            showLabelFileImage(productEntity.getoriginalImageUrl());
            if (UtilValidate.isNotEmpty(productEntity.getweight())) {
                Debug.logInfo("productEntity.getweight(): " + productEntity.getweight(), module);
                txtItemWeight.setValue(productEntity.getweight());

                try {
                    if (UtilValidate.isNotEmpty(productEntity.getweightUomId())) {
                        Uom uom = UomWeightSingleton.getUom(productEntity.getweightUomId());
                        weightUom.setSelectedItem(uom);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                txtItemWeight.setText("0");
            }
            if (UtilValidate.isNotEmpty(productEntity.getproductHeight())) {
                txtItemHeight.setText(productEntity.getproductHeight().toString());
                try {
                    if (UtilValidate.isNotEmpty(productEntity.getheightUomId())) {
                        Uom uom = UomLenghtSingleton.getUom(productEntity.getheightUomId());

                        lengthUom.setSelectedItem(uom);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            txtAreaDescription.setText(productEntity.getcomments());
            if (txtAreaDescription.getText() == null || txtAreaDescription.getText().trim().isEmpty()) {
                //          txtAreaDescription.setText(editProductName.getText());
            }

            if (productEntity.getpiecesIncluded() != null) {
                txtShelfLife.setValue(productEntity.getpiecesIncluded());
            } else {
                txtShelfLife.setValue(BigDecimal.valueOf(730));
            }

            /*
             //TreeNode brandName = GetRecusevilyNodeType(node, tp, DepartmentTreeNode.DepartmentNodeName);
             //            genericValueMapBrandComboBox.setSelectedItemId(productEntity.getString("brandName());//.setSelectedItem(productEntity.getString("brandName());
             //find brand name from tree
             TreeNode brandName = OrderMaxUtility.GetRecusevilyNodeType(node, node.tp, BrandTreeNode.BrandNodeName);
             if (brandName != null) {
             Debug.logInfo("tree clicked dep null: " + brandName._name, module);
             //editBrandId.setText(brandName._id);
             //comboBrand.setSelectedIndex(WIDTH).setSelectedItem(brandName._name);
             genericValueMapBrandComboBox.setSelectedItemId(brandName._id);
             //                copyWebProductImagePanel.setBrandId(brandName._id);

             } else {
             Debug.logInfo("tree clicked Brand null ", module);
             }

             //find department name from tree
             TreeNode deptName = OrderMaxUtility.GetRecusevilyNodeType(node, node.tp, DepartmentTreeNode.DepartmentNodeName);
             if (deptName != null) {
             //Debug.logInfo("tree clicked dep -- " + deptName.getNodeName() + " node id " + deptName._id + " - " + deptName._name, module);
             comboDepartmentName.getModel().setSelectedItem(deptName._name);
             //                copyWebProductImagePanel.setDepartmentId(deptName._name);

             } else {
             //Debug.logInfo("tree clicked dep null", module);
             }
             */
            //get price details from db
            //SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(productId, facilityIdCombo.getSelectedItemId(), session);
//            Map<String, Object> priceResult = PosProductHelper.getPriceListAndProductDetails(productId, delegator);
            //          if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_LISTPRICE)) {
            //             BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_LISTPRICE).getBigDecimal("price");
            //             if (price != null) {
            //editListPrice.setText(data.getListPrice().toString());
            //           }
            //     }
//            if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE)) {
//                BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE).getBigDecimal("price");
//                if (price != null) {
            //editBasePrice.setText(data.getDefaultPrice().toString());
//                }
//            }
//            if (priceResult.containsKey(PosProductHelper.ProductPriceTypeId_AVERAGECOST)) {
//                BigDecimal price = priceResult.get(PosProductHelper.ProductPriceTypeId_AVERAGECOST).getBigDecimal("price");
//                if (price != null) {
            //editPurchasePrice.setText(data.getAvgCost().toString());
//                }
//            }
            //if (priceResult.containsKey(PosProductHelper.SUPPLIERPRODUCT)) {
//                GenericValue supplierProduct = priceResult.get(PosProductHelper.SUPPLIERPRODUCT);
//            editSupplierCode.setText(data.getSupplierProductId());
//            supplierPartyIdCombo.setSelectedItemId(data.getSupplierId());
//            editSupplierLastPrice.setText(data.getLastPrice().toString());
            //}
//            if (priceResult.containsKey(PosProductHelper.GoodIdentificationTypeIdEAN)) {
//            editScanCode.setText(data.getScanCode());
            //          }
            showSelectandFileImage(txtoriginalImageUrl.getText());
            notifyListeners(OrderMaxUtility.ITEM_STATUS_CHANGED, editInternalName.getText(), editInternalName.getText());
//        } catch (GenericEntityException ex) {
//            Logger.getLogger(ProductDetailEditDialog.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
            Debug.logInfo("editProductName: " + editProductName.getText(), module);
            setLabelPrintFields();
            Debug.logInfo("txtProductName: " + txtProductName.getText(), module);
            isModified = false;
        }

        if (productComposite.getProductAssocToList() != null && !productComposite.getProductAssocToList().isEmpty()) {
            ProductAssoc productAssoc = productComposite.getProductAssocToList().get(0);
            productVariantPickerEditPanel.textIdField.setText(productAssoc.getproductId());
        }
        if (productComposite.getProductAssocToList() != null) {
            Debug.logInfo("productComposite.getProductAssocList(): " + productComposite.getProductAssocToList().size(), module);
        }
        setScanCode(productComposite);
        setProductPriceComposite(productComposite);
        setSupplierProductComposite(productComposite);

    }

    GoodIdentification goodIdentification = null;

    public void setScanCode(ProductComposite productComposite) {
        goodIdentification = null;
        if (productComposite.getGoodIdentificationList().getSize() > 0) {
            goodIdentification = (GoodIdentification) productComposite.getGoodIdentificationList().getElementAt(0);
        }

        if (goodIdentification != null) {
            try {
                GoodIdentificationType typeId = GoodIdentificationTypeSingleton.getGoodIdentificationType(goodIdentification.getgoodIdentificationTypeId());
                goodIdentificationTypeComboModel.setSelectedItem(typeId);

            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            editScanCode.setText(goodIdentification.getidValue());
        } else {
            editScanCode.setText("");
        }
    }

    public void setProductPriceComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        if (productComposite != null) {
            if (productComposite.getProductItemPrice().getListPrice() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getListPrice().getCurrentPrice();
                if (value != null) {
                    editListPrice.setText(value.getProductPrice().getprice().toString());
                }
            }

            if (productComposite.getProductItemPrice().getDefaultPrice() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getDefaultPrice().getCurrentPrice();
                if (value != null) {
                    editBasePrice.setText(value.getProductPrice().getprice().toString());
                }

            }

            if (productComposite.getProductItemPrice().getAverageCost() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getAverageCost().getCurrentPrice();
                if (value != null) {
                    editPurchasePrice.setText(value.getProductPrice().getprice().toString());
                }
            }
        }
    }

    public void setSupplierProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        if (productComposite != null) {

            SupplierProductComposite supplierProductComposite = productComposite.getSupplierProductList().getCurrentSupplierProduct();
            if (supplierProductComposite != null && supplierProductComposite.getSupplierProduct().getlastPrice() != null) {
                editSupplierCode.setText(supplierProductComposite.getSupplierProduct().getsupplierProductId());
                supplierPartyIdCombo.setSelectedItemId(supplierProductComposite.getSupplierProduct().getpartyId());
                editSupplierLastPrice.setText(supplierProductComposite.getSupplierProduct().getlastPrice().toString());
            }
        }
    }

    void setLabelPrintFields() {
        txtProductName.setText(editProductName.getText());
        txtBarCode.setText(editScanCode.getText());
        if (UtilValidate.isNotEmpty(editBasePrice.getText())) {
            txtPrice.setValue(new BigDecimal(editBasePrice.getText()));
        }
        if (UtilValidate.isNotEmpty(txtItemWeight.getText())) {
            txtWeight.setValue(new BigDecimal(txtItemWeight.getText()));
        }
        if (weightUom.getSelectedItem() != null) {
            txtWeightUom.setText(weightUom.getSelectedItem().getabbreviation());
        }
        txtIngrident.setText(txtAreaDescription.getText());

        try {
            txtShelfLife.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        BigDecimal millis = BigDecimal.ZERO;
        try {
            millis = BigDecimal.valueOf(((Long) txtShelfLife.getValue()).longValue());
        } catch (Exception e) {
            millis = BigDecimal.valueOf(((Double) txtShelfLife.getValue()).doubleValue());
        }

//        millis = millis.multiply(BigDecimal.valueOf(365));
//        if (txtPackDate.getText() == null || txtPackDate.getText().isEmpty()) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(cal.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());        

        millis = millis.multiply(BigDecimal.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
//                    BigDecimal.valueOf(86400000).multiply(millis);
        setStartAndEndDate(new java.util.Date(cal.getTimeInMillis()), new java.util.Date(cal.getTimeInMillis() + millis.longValue()));
//        }

    }

    public void setStartAndEndDate(java.util.Date startDate, java.util.Date endDate) {
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtPackDate, locale);
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(startDate);                           // set cal to date

        txtPackDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        txtUseByDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));

    }

    @Override
    public void getDialogField() {
//        genericValueMapBrandComboBox.checkItem();
        Product productEntity = productComposite.getProduct();//.getGenericValueObj();
        productEntity.setproductId(panelProductIdPicker.textIdField.getText().toUpperCase());
//        productEntity.brandId = genericValueMapBrandComboBox.getSelectedItemId();
//        productEntity.brandName = ((ComboKey) comboBrand.getSelectedItem())._name;
        productEntity.setbrandName(txtBrandName.getText());
        //productEntity.setdescription(editInternalName.getText().toUpperCase());

//su        productEntity.quantityOnHand = quantityOnHand;
        productEntity.setproductName(editProductName.getText().toUpperCase());
        productEntity.setinternalName(editInternalName.getText().toUpperCase());

//su        productEntity.price = new BigDecimal(editListPrice.getText());
//su        productEntity.defaultPrice = new BigDecimal(editBasePrice.getText());
//su        productEntity.purchasePrice = new BigDecimal(editPurchasePrice.getText());
//su        productEntity.currencyUomId = editCurrency.getText();
//su        productEntity.supplierProductId = editSupplierCode.getText().toUpperCase();
//su        productEntity.eanValue = editScanCode.getText().toUpperCase();
//su        productEntity.categoryId = getDepartmentCode();
//su        productEntity.categoryName = getDepartmentName();
//su        productEntity.brandId = productEntity.categoryId + "_" + ((String) productEntity.brandName).replaceAll("\\s", "");
//        String client = (String) comboSupplier.getSelectedItem();
        //      if (UtilValidate.isNotEmpty(client)) {
//su        productEntity.supplierPartyId = supplierPartyIdCombo.getSelectedItemId();
        //       }
        productEntity.setsmallImageUrl(txtsmallImageUrl.getText());
        productEntity.setmediumImageUrl(txtmediumImageUrl.getText());
        productEntity.setlargeImageUrl(txtlargeImageUrl.getText());
        productEntity.setdetailImageUrl(txtdetailImageUrl.getText());
        productEntity.setoriginalImageUrl(txtoriginalImageUrl.getText());
        productEntity.setcomments(txtAreaDescription.getText());
        if (taxable.getSelectedItem() != null) {

            String str;
            try {
                str = YesNoConditionSelectSingleton.getKeyFromDisplayName(taxable.getSelectedItem());
                productEntity.settaxable(str);//productEntity.getBoolean("taxable"));                
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {

            String str = YesNoConditionSelectSingleton.getKeyFromDisplayName(isScaleComboBoxModel.getSelectedItem());
            productEntity.setamountRequired(str);

        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(scaleUom.getSelectedItem())) {
                productEntity.setamountUomId(scaleUom.getSelectedItem().getuomId());
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (txtItemWeight.getText() != null && txtItemWeight.getText().isEmpty() == false) {
            productEntity.setweight(new BigDecimal(txtItemWeight.getText()));
//            productEntity.weightUomId = weightUomIdCombo.getSelectedItemId();
        }

        try {
            String str = YesNoConditionSelectSingleton.getKeyFromDisplayName(isVirtualComboBoxModel.getSelectedItem());
            productEntity.setisVirtual(str);//productEntity.getBoolean("taxable"));                
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String str = YesNoConditionSelectSingleton.getKeyFromDisplayName(isVariantComboBoxModel.getSelectedItem());
            productEntity.setisVariant(str);//productEntity.getBoolean("taxable"));     
            Debug.logInfo("isvariant: " + str, module);
            Debug.logInfo("isVariantComboBoxModel.getSelectedItem(): " + isVariantComboBoxModel.getSelectedItem(), module);
            if ("Y".equals(str)) {
                ProductAssoc productAssoc = null;
                if (productComposite.getProductAssocToList() != null) {
                    for (ProductAssoc productAssoc1 : productComposite.getProductAssocToList()) {
                        if ("PRODUCT_VARIANT".equals(productAssoc1.getproductAssocTypeId())) {
                            productAssoc = productAssoc1;
                            break;
                        }
                    }
                }

                if (productAssoc == null) {
                    if (productComposite.getProductAssocToList() == null) {
                        ArrayList<ProductAssoc> variantProductList = new ArrayList<ProductAssoc>();
                        productComposite.setProductAssocToList(variantProductList);
                    }
                    productAssoc = LoadProductWorker.createProductAssoc((String) productVariantPickerEditPanel.getEntityValue(), "PRODUCT_VARIANT");
                    productAssoc.setproductIdTo(productEntity.getproductId());
                    productComposite.getProductAssocToList().add(productAssoc);
                } else {
                    productAssoc.setproductIdTo(productEntity.getproductId());
                    productAssoc.setproductId((String) productVariantPickerEditPanel.getEntityValue());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productEntity.setweightUomId(weightUom.getSelectedItem().getuomId());//productEntity.getBoolean("taxable"));                
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            productEntity.setheightUomId(lengthUom.getSelectedItem().getuomId());//productEntity.getBoolean("taxable"));                
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        productEntity.setpiecesIncluded(new Long(txtShelfLife.getText()));
        getScanCode(productComposite);

        getSupplierProduct(productComposite);
        getProductPrice(productComposite);

        //productEntity.height = new BigDecimal(txtItemHeight.getText());
        //su       productEntity.isPriceModiefied = true;
//su        productEntity.isDefaultPriceModified = true;
//su        productEntity.isPurchasePriceModified = true;
    }

    public void getScanCode(ProductComposite productComposite) {
        if (goodIdentification != null) {
            try {
                org.ofbiz.ordermax.entity.GoodIdentificationType goodid = goodIdentificationTypeComboModel.getSelectedItem();
                if (goodid != null) {
                    goodIdentification.setgoodIdentificationTypeId(goodid.getgoodIdentificationTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            goodIdentification.setidValue(editScanCode.getText());
            goodIdentification.setproductId(productComposite.getProduct().getproductId());
        } else {
            if (goodIdentificationTypeComboModel.getSelectedItem() != null) {
                String typeId = goodIdentificationTypeComboModel.getSelectedItem().getgoodIdentificationTypeId();
                for (int i = 0; i < productComposite.getGoodIdentificationList().getSize(); ++i) {
                    GoodIdentification item = productComposite.getGoodIdentificationList().getList().get(i);
                    if (item.getgoodIdentificationTypeId().equals(typeId)) {
                        goodIdentification = item;
                        break;
                    }
                }

                if (goodIdentification == null) {
                    goodIdentification = LoadProductWorker.createNewGoodsIdentification(productComposite.getProduct().getproductId(), typeId);
                    productComposite.getGoodIdentificationList().add(goodIdentification);
                }
                goodIdentification.setidValue(editScanCode.getText());
            }
        }
    }

    public void getSupplierProduct(ProductComposite productComposite) {

        if (productComposite != null) {

            SupplierProductComposite supplierProductComposite = productComposite.getSupplierProductList().getCurrentSupplierProduct();
            if (supplierProductComposite != null/* && supplierProductComposite.getSupplierProduct().getlastPrice() != null*/) {
                supplierProductComposite.getSupplierProduct().setproductId(productComposite.getProduct().getproductId());
                supplierProductComposite.getSupplierProduct().setsupplierProductId(editSupplierCode.getText());
                supplierProductComposite.getSupplierProduct().setpartyId(supplierPartyIdCombo.getSelectedItemId());
                try {
                    supplierProductComposite.getSupplierProduct().setlastPrice(new BigDecimal(editSupplierLastPrice.getText()));
                } catch (Exception e) {
                    supplierProductComposite.getSupplierProduct().setlastPrice(BigDecimal.ZERO);
                }
                supplierProductComposite.getSupplierProduct().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
            } else {
                supplierProductComposite = LoadSupplierProductWorker.createSupplierProduct(productComposite.getProduct().getproductId(), supplierPartyIdCombo.getSelectedItemId(), ControllerOptions.getSession());
                try {
                    supplierProductComposite.getSupplierProduct().setlastPrice(new BigDecimal(editSupplierLastPrice.getText()));
                } catch (Exception e) {
                    supplierProductComposite.getSupplierProduct().setlastPrice(BigDecimal.ZERO);
                }
                supplierProductComposite.getSupplierProduct().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                productComposite.getSupplierProductList().add(supplierProductComposite);
            }
        }
    }

    //su        productEntity.price = new BigDecimal(editListPrice.getText());
//su        productEntity.defaultPrice = new BigDecimal(editBasePrice.getText());
//su        productEntity.purchasePrice = new BigDecimal(editPurchasePrice.getText());
//su        productEntity.currencyUomId = editCurrency.getText();
    public void getProductPrice(ProductComposite productComposite) {

        if (productComposite != null) {
            Debug.logInfo("getProductPrice: " + 1, module);
            if (productComposite.getProductItemPrice().getListPrice() != null) {
                Debug.logInfo("getProductPrice: " + 2, module);
                ProductPriceComposite value = productComposite.getProductItemPrice().getListPrice().getCurrentPrice();
                if (value != null) {
                    Debug.logInfo("getProductPrice: " + 3, module);
                    value.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                    Debug.logInfo("before  value.getProductPrice().setprice : " + editListPrice.getText(), module);
                    value.getProductPrice().setprice(new BigDecimal(editListPrice.getText()));
                    Debug.logInfo("value.getProductPrice().setprice: " + value.getProductPrice().getprice(), module);
                    value.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                } else {

                    ProductPriceComposite productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), "LIST_PRICE", ControllerOptions.getSession());
                    productPriceComposite.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                    productPriceComposite.getProductPrice().setprice(new BigDecimal(editListPrice.getText()));
                    productPriceComposite.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                    try {
                        productComposite.getProductItemPrice().addProductPrice(productPriceComposite);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            if (productComposite.getProductItemPrice().getDefaultPrice() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getDefaultPrice().getCurrentPrice();
                if (value != null) {
                    value.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                    value.getProductPrice().setprice(new BigDecimal(editBasePrice.getText()));
                    value.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                } else {

                    ProductPriceComposite productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), "DEFAULT_PRICE", ControllerOptions.getSession());
                    productPriceComposite.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                    productPriceComposite.getProductPrice().setprice(new BigDecimal(editBasePrice.getText()));
                    productPriceComposite.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                    try {
                        productComposite.getProductItemPrice().addProductPrice(productPriceComposite);
                    } catch (Exception ex) {
                        Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            if (productComposite.getProductItemPrice().getAverageCost() != null) {
                ProductPriceComposite value = productComposite.getProductItemPrice().getAverageCost().getCurrentPrice();
                if (value != null) {
                    value.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                    value.getProductPrice().setprice(new BigDecimal(editPurchasePrice.getText()));
                    value.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                } else {
                    if (UtilValidate.isNotEmpty(editPurchasePrice.getText())) {
                        ProductPriceComposite productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), "AVERAGE_COST", ControllerOptions.getSession());
                        productPriceComposite.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                        productPriceComposite.getProductPrice().setprice(new BigDecimal(editPurchasePrice.getText()));
                        productPriceComposite.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                        try {
                            productComposite.getProductItemPrice().addProductPrice(productPriceComposite);
                        } catch (Exception ex) {
                            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    public boolean isValidFields() {
        if (panelProductIdPicker.textIdField.getText().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Product Code is empty. Please enter valid code");
            panelProductIdPicker.textIdField.selectAll();
            return false;
        } else if (editProductName.getText().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Product Name is empty. Please enter description");
            editProductName.selectAll();
            return false;
        } else if (editInternalName.getText().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Product Internal Name is empty. Please enter internal name");
            editProductName.selectAll();
            return false;
        } else if (comboSupplier.getSelectedIndex() == -1) {
            OrderMaxOptionPane.showMessageDialog(null, "Supplier Name is empty. Please enter supplier name");
            editProductName.selectAll();
            return false;
        } else if (editBasePrice.getText().trim().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Purchase price is empty. Please enter purchase price");
            editProductName.selectAll();
            return false;
        } else if (editListPrice.getText().trim().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Selling price is empty. Please enter selling price");
//            editListPrice.selectAll();
            return false;
        } else if (editSupplierCode.getText().isEmpty()) {
            OrderMaxOptionPane.showMessageDialog(null, "Supplier code is empty. Please enter supplier code");
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

    String generateProductCode(String departmentId, String brandName, String name) {

        String departmentCode = null;

        if (UtilValidate.isNotEmpty(departmentId)) {
//            departmentCode = genericValueMapComboBox.getSelectedItemId();//.get(comboDepartmentName.getSelectedIndex());
        }

        String productCode = departmentCode;
        Debug.logInfo(" brandName : " + brandName, module);

        if (departmentCode != null && brandName != null) {

            String n = name.toUpperCase().substring(0, 1);
            String b = brandName.toUpperCase().substring(0, 1);
            Integer code = getNextProductCode(n, b, new Integer(departmentCode));
            String format = String.format("%%0%dd", 4);
            String result = String.format(format, code);

            return n + b + result;
        }
        return productCode;
    }

    String getDepartmentCode() {

        String departmentCode = null;

//        if (comboDepartmentName.getSelectedIndex() > -1) {
//            departmentCode = genericValueMapComboBox.getSelectedItemId();//.get(comboDepartmentName.getSelectedIndex());
//        }
        return departmentCode;
    }

    String getDepartmentName() {

        String departmentName = null;

//        if (comboDepartmentName.getSelectedItem() != null) {
//            departmentName = comboDepartmentName.getSelectedItem().toString();
//        }
        return departmentName;
    }

    public synchronized void eventCopyName() {

        editInternalName.setText(editProductName.getText());
    }

    public synchronized void eventCopyProductIdToSupplierProductId() {

        editSupplierCode.setText(panelProductIdPicker.textIdField.getText());

    }

    public synchronized void eventGenerateScanCode() {

        //Debug.logInfo("eventGenerateScanCode 1  ", module);
        String prodId = panelProductIdPicker.textIdField.getText();
        //Debug.logInfo("eventGenerateScanCode 2  ", module);
        String scanCode = generateScanCode(prodId);
        //Debug.logInfo("eventGenerateScanCode 4  ", module);

        //Debug.logInfo("eventGenerateScanCode 5  ", module);
        editScanCode.setText(scanCode);

    }

    public String generateScanCode(String productId) {

        String scanCode = getUniqueScanCode(productId);
        int length = scanCode.length();
        String strSeq = org.apache.commons.lang.StringUtils.leftPad(scanCode, 12, '0');
//        String strSeq = scanCode.
//        String zeros = String.format("%12s", scanCode);
        //int checksum = getCheckSum(strSeq);
        String barContent = strSeq;//new StringBuilder(strSeq).append(checksum).toString();
        Debug.logInfo("barContent 1  " + barContent, module);
        addScanProductToKeyMap(productId, barContent);
        return barContent;
    }

    private static int getCheckSum(CharSequence s) {
        int length = s.length();
        int sum = 0;
        for (int i = length - 1; i >= 0; i -= 2) {

            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                // System.out.println("wrong format");
            }
            sum += digit;
        }
        sum *= 3;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                // System.out.println("wrong format");
            }
            sum += digit;
        }
        int chkdig = 0;
        if (sum % 10 != 0) {
            chkdig = 10 - sum % 10;
        }

        return chkdig;
    }

    String getUniqueScanCode(String prodId) {

        String scanCode = "";
        String alpha = "";
        String numaric = "";

        for (int i = 0; i < prodId.length(); ++i) {
            String code = prodId.substring(i, i + 1);
            String val = PosProductHelper.phoneDialAlphaToNumaric(code);
            scanCode += val;

            if (code.equals(val)) {
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

    static public boolean createNewProductWithSupplier(ProductInventoryPojo prodPojo, Delegator delegator) {

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
                    /*
                     try {
                     delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, ProductDataTreeLoader.CategoryPromotionId, delegator));
                     } catch (Exception e2) {
                     // TODO Auto-generated catch block
                     //Debug.logInfo("Save product and inventory failed[createProductCategoryMember] " + supplierItemGV, module);
                     //Debug.logError(e2, module);
                     }
                     */
                    try {
                        //    					String categoryId = getDepartmentCode();
                        String parentProductCategoryId = ControllerOptions.getSession().getProductCategory().getProductCategoryId();

                        GenericValue departmentCategory = PosProductHelper.getProductCategory(prodPojo.categoryId, delegator);
                        if (departmentCategory != null) {
//                            delegator.create(PosProductHelper.createProductCategory(prodPojo.categoryId, parentProductCategoryId, "CATALOG_CATEGORY", prodPojo.categoryName, delegator));
//                        }
                            GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
                            if (rollUp == null) {
                                rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
                                delegator.create(rollUp);
                            }
                        }
                        //    					String brandId = ((String) comboBrand.getSelectedItem()).substring(5);//getBrandCode();

                        GenericValue brandCategory = PosProductHelper.getProductCategory(prodPojo.brandId, delegator);
                        if (brandCategory != null) {
//                            delegator.create(PosProductHelper.createProductCategory(prodPojo.brandId, prodPojo.categoryId, "CATALOG_CATEGORY", prodPojo.brandName, delegator));
//                        }

                            GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
                            if (rollUp == null) {
                                rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
                                delegator.create(rollUp);
                                //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);
                            }
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

    static public boolean updateProduct(ProductInventoryPojo prodPojo, Delegator delegator) {

        // if the product exists then update
        if (PosProductHelper.isProductExists(prodPojo.productId, delegator)) {

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

            GenericValue giftEAN = PosProductHelper.getGoodIdentification(
                    prodPojo.productId, PosProductHelper.GoodIdentificationTypeIdEAN, delegator);
            if (giftEAN != null) {
                Debug.logInfo("Update : ean: " + prodPojo.eanValue, module);
                boolean result = PosProductHelper.updateGoodIdentificationType(
                        giftEAN, prodPojo.eanValue, delegator);
                if (!result) {
                    return result;
                }

            } else {
                Debug.logInfo("new : ean: " + prodPojo.eanValue, module);
                boolean result = PosProductHelper.createGoodIdentificationType(
                        prodPojo.productId, PosProductHelper.GoodIdentificationTypeIdEAN,
                        prodPojo.eanValue, delegator);
                if (!result) {
                    return result;
                }

            }
            /*
             try {

             String promoCat = ProductDataTreeLoader.CategoryPromotionId;
             GenericValue catMember = PosProductHelper.getProductCategoryMember(prodPojo.productId, promoCat, delegator);
             if (catMember == null) {
             delegator.create(PosProductHelper.createProductCategoryMember(prodPojo.productId, promoCat, delegator));
             }
             } catch (Exception e2) {
             // TODO Auto-generated catch block
             //Debug.logInfo("Save product and inventory failed[createProductCategoryMember] ", module);
             //Debug.logError(e2, module);
             }*/

            try {

                String parentProductCategoryId = ControllerOptions.getSession().getProductCategory().getProductCategoryId();

                GenericValue departmentCategory = PosProductHelper.getProductCategory(prodPojo.categoryId, delegator);
                if (departmentCategory != null) {
//                    delegator.create(PosProductHelper.createProductCategory(prodPojo.categoryId, parentProductCategoryId, "CATALOG_CATEGORY", prodPojo.categoryName, delegator));

                    GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
                    if (rollUp == null) {
                        rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.categoryId, parentProductCategoryId, delegator);
                        delegator.create(rollUp);
                    }
                }

                GenericValue brandCategory = PosProductHelper.getProductCategory(prodPojo.brandId, delegator);
                if (brandCategory != null) {
                    //                  delegator.create(PosProductHelper.createProductCategory(prodPojo.brandId, prodPojo.categoryId, "CATALOG_CATEGORY", prodPojo.brandName, delegator));

                    GenericValue rollUp = PosProductHelper.getProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
                    if (rollUp == null) {
                        rollUp = PosProductHelper.createProductCategoryRollup(prodPojo.brandId, prodPojo.categoryId, delegator);
                        delegator.create(rollUp);
                    }
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
            createNewProductWithSupplier(prodPojo, delegator);
        }

        return true;

    }

    public List<ProductImportPojo> generateSpiceProductIds() {
        List<ProductImportPojo> list = FastList.newInstance();

        String productId = panelProductIdPicker.textIdField.getText();
        String departmentId = "";//(String) comboDepartmentName.getSelectedItem();
        String brandName = "";//((ComboKey) comboBrand.getSelectedItem())._name;
        String name = editInternalName.getText().trim();

        ProductImportPojo currPojo1 = new ProductImportPojo();
        BigDecimal sellPrice = new BigDecimal(editListPrice.getText());
        BigDecimal purPrice = new BigDecimal(editBasePrice.getText());

        //1kg
        currPojo1.ProductId = generateProductCode(departmentId, brandName, name);
        currPojo1.Item = editProductName.getText() + " 1KG";
        currPojo1.SellingPrice = editListPrice.getText();
        currPojo1.PurchasePrice = editBasePrice.getText();
        currPojo1.BarcodeItem = generateScanCode(currPojo1.ProductId);

//        currPojo1.priceCurrencyUomId = editCurrency.getText();
//        currPojo1.DEPARTMENT = (String) comboDepartmentName.getSelectedItem();
//        currPojo1.Brand = ((ComboKey) comboBrand.getSelectedItem())._name;
        if (comboSupplier.getSelectedIndex() > 0) {
            currPojo1.supplierPartyId = supplierPartyIdCombo.getSelectedItemId();//.get(comboSupplier.getSelectedIndex());
        }
        addToProductCodeKeyMap(currPojo1.ProductId);
        list.add(currPojo1);

        //500gm
        ProductImportPojo currPojo = new ProductImportPojo();
        currPojo.ProductId = generateProductCode(departmentId, brandName, name);
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
        currPojo.ProductId = generateProductCode(departmentId, brandName, name);
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
        currPojo.ProductId = generateProductCode(departmentId, brandName, name);
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
        currPojo.ProductId = generateProductCode(departmentId, brandName, name);
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

        String productId = panelProductIdPicker.textIdField.getText();
        String departmentId = "";//(String) comboDepartmentName.getSelectedItem();
        String brandId = "";//((ComboKey) comboBrand.getSelectedItem())._name;
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

//        currPojo1.priceCurrencyUomId = editCurrency.getText();
//        currPojo1.DEPARTMENT = (String) comboDepartmentName.getSelectedItem();
//        currPojo1.Brand = ((ComboKey) comboBrand.getSelectedItem())._name;
        if (comboSupplier.getSelectedIndex() > 0) {
            currPojo1.supplierPartyId = supplierPartyIdCombo.getSelectedItemId();//.get(comboSupplier.getSelectedIndex());
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

    //add to product id map so we know its exisiting 
    //in database
    public void addToProductCodeKeyMap(String productidd) {

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

    private Locale getLocale(String loc) {
        if (loc != null && loc.length() > 0) {
            return new Locale(loc);
        } else {
            return Locale.getDefault();
        }

    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
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

    public void setParentCatId(String id) {
//        genericValueMapComboBox.setSelectedItemId(id);
    }

    public void setBrandCatId(String id) {

        //      genericValueMapBrandComboBox.setSelectedItemId(id);
    }

    public void deleteItem() {
        try {
            String prodId = panelProductIdPicker.textIdField.getText();
            GenericValue prodValue = PosProductHelper.getGenericValueByKey("Product", UtilMisc.toMap("productId", prodId), delegator);

            //average cost
            List<GenericValue> valList = PosProductHelper.getGenericValueLists("ProductAverageCost", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            valList = PosProductHelper.getGenericValueLists("GoodIdentification", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            valList = PosProductHelper.getGenericValueLists("ProductCategoryMember", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            valList = PosProductHelper.getGenericValueLists("ProductContent", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            valList = PosProductHelper.getGenericValueLists("ProductPrice", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            valList = PosProductHelper.getGenericValueLists("SupplierProduct", "productId", prodId, delegator);
            for (GenericValue val : valList) {
                delegator.removeValue(val);
            }

            delegator.removeValue(prodValue);
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDetailEditDialog.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runReport(ArrayList<String> productList, Delegator delegator) {
        /*      Map<String, Object> whereClauseMap = new HashMap<String, Object>();
         for (String val : productList) {

         whereClauseMap.put("productId", val);
         }

         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("ReportTitle", "Address Report");
         parameters.put("DataFile", "CustomBeanFactory.java - Bean Collection");
         parameters.put(EntityJasperReport.DelegatorName, delegator);
         parameters.put(EntityJasperReport.WhereClauseMap, whereClauseMap);

         EntityJasperReport jasperReport = new InventoryItemReport();
         final JasperPrint jasperPrint = jasperReport.runReport(parameters);
         if (jasperPrint != null) {
         panelJasper.removeAll();
         JRViewer viewer = new JRViewer(jasperPrint);
         panelJasper.setLayout(new GridLayout(1, 1));
         panelJasper.add(viewer);
         panelJasper.invalidate();
         this.repaint();
         }
         */
    }

    @Override
    public boolean isNeedSavingPrices() {
        return true;
    }

}
