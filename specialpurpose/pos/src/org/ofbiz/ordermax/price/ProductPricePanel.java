/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadProductPriceWorker;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadSupplierProductFromFileWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.ProductPricePurposeListCellRenderer;
import mvc.model.list.ProductPriceTypeListCellRenderer;
import mvc.model.list.ProductStoreGroupListCellRenderer;
import mvc.model.list.StringListCellRenderer;
import mvc.model.list.UomListCellRenderer;
import mvc.model.table.ProductPriceTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductPricePurpose;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.ProductPricePurposeSingleton;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;
import org.ofbiz.ordermax.product.UomTimeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductPricePanel extends javax.swing.JPanel {

    private ProductPriceTableModel productPriceTableModel = new ProductPriceTableModel();
    private ListAdapterListModel<ProductPriceComposite> productPriceListModel = new ListAdapterListModel<ProductPriceComposite>();

    private class SetupSelectionModel<E> {

        final private ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
        private ListComboBoxModel<E> comboBoxModel = new ListComboBoxModel<E>();
        private ListSelectionModel selectionModel = new DefaultListSelectionModel();
        private ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

        private SetupSelectionModel(List<E> values, JComboBox comboBox, ListCellRenderer<E> render) {
            dataListModel.addAll(values);
            comboBoxModel.setListModel(dataListModel);
            selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listModelSelection.setListModels(dataListModel, selectionModel);
            comboBoxModel.setListSelectionModel(selectionModel);
            comboBox.setModel(comboBoxModel);
            comboBox.setRenderer(render);
        }
    }

    private class SetupListSelectionModel<E> {

        final private ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
        private JList<E> displayList = new JList<E>(dataListModel);
        private ListSelectionModel selectionModel = new DefaultListSelectionModel();
        private ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

        private SetupListSelectionModel(ListCellRenderer<E> render) {

            displayList.setSelectionModel(selectionModel);
            selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listModelSelection.setListModels(dataListModel, selectionModel);
            displayList.setCellRenderer(render);
            displayList.setEnabled(true);

//            jList.setCellRenderer(render);
        }
    }

    private SetupSelectionModel<ProductPriceType> supplierSelectionComboModel = null;
    private SetupSelectionModel<String> taxInPriceSelectionComboModel = null;
    private SetupSelectionModel<Uom> uomSelectionComboModel = null;
    private SetupSelectionModel<ProductStoreGroup> productStoreGroupComboModel = null;
    private SetupSelectionModel<Uom> currencyUomSelectionComboModel = null;
    private SetupSelectionModel<ProductPricePurpose> productPricePurposeSelectionComboModel = null;
    private SetupListSelectionModel supplierListSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public ProductPricePanel() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        supplierSelectionComboModel = new SetupSelectionModel<ProductPriceType>(ProductPriceTypeSingleton.getValueList(),
                productPriceTypeIdTextField, new ProductPriceTypeListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY));

        uomSelectionComboModel = new SetupSelectionModel<Uom>(UomTimeSingleton.getValueList(),
                termUomIdTextField, new UomListCellRenderer());

        currencyUomSelectionComboModel = new SetupSelectionModel<Uom>(UomCurrencySingleton.getValueList(),
                currencyUomIdTextField, new UomListCellRenderer());

        taxInPriceSelectionComboModel = new SetupSelectionModel<String>(YesNoConditionSelectSingleton.getValueList(),
                taxInPriceTextField, new StringListCellRenderer(false));

        productStoreGroupComboModel = new SetupSelectionModel<ProductStoreGroup>(ProductStoreGroupSingleton.getValueList(),
                productStoreGroupIdTextField, new ProductStoreGroupListCellRenderer(false));

        productPricePurposeSelectionComboModel = new SetupSelectionModel<ProductPricePurpose>(ProductPricePurposeSingleton.getValueList(),
                productPricePurposeIdTextField, new ProductPricePurposeListCellRenderer(false));

//        supplierListSelectionModel = new SetupListSelectionModel<GoodIdentification>(new GoodIdentificationListCellRenderer(false));
        jtableSupplierProduct.setModel(productPriceTableModel);

//        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
//        listModelSelection.setListModels(productPriceListModel, selectionModel);
        jtableSupplierProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        /*      
         JScrollPane scrollPane = new JScrollPane();
         scrollPane.setBounds(10, 11, 580, 200);

         scrollPane.setViewportView(listSelectionModel.displayList);
         panelGoodIdentificationList.setLayout(new BorderLayout());
         panelGoodIdentificationList.add(BorderLayout.CENTER, scrollPane);
         */
//        ComponentBorder.loweredBevelBorder(panelGoodIdentificationList, "List");
//        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");
        ListSelectionModel selectionModel = jtableSupplierProduct.getSelectionModel();

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleSelectionEvent(e);
            }
        });

    }

    protected void handleSelectionEvent(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        // e.getSource() returns an object like this
        // javax.swing.DefaultListSelectionModel 1052752867 ={11}
        // where 11 is the index of selected element when mouse button is released
        String strSource = e.getSource().toString();
        int start = strSource.indexOf("{") + 1,
                stop = strSource.length() - 1;
        Debug.logInfo("iSelectedIndex: " + strSource, "iSelectedIndex");
        try {
            int iSelectedIndex = Integer.parseInt(strSource.substring(start, stop));

            setSupplierproduct(iSelectedIndex);
            Debug.logInfo("iSelectedIndex: " + iSelectedIndex, "iSelectedIndex");
        } catch (Exception ex) {

        }
    }

    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        if (productComposite != null) {
            productPriceListModel.clear();
            productPriceListModel.addAll(productComposite.getProductItemPrice().getProductPriceList().getList());

            /*if (productPriceListModel.getSize() > 0) {
                setSupplierproduct(0);
            } else {
                clearDialogFields();
                productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
                setDialogField();
            }*/
        }
        
        newPriceRecord();
    }

    public void clearDialogFields() {
        taxAuthPartyIdTextField.setText("");
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(productPrice.getlastUpdatedTxStamp()));
        fromDateTextField.setText("");//OrderMaxUtility.getValidBigDecimal(productPrice.getminimumOrderQuantity()));
        taxAuthPartyIdTextField.setText("");//OrderMaxUtility.getValidTimestamp(productPrice.getavailableFromDate()));
        taxPercentageTextField.setText("");//OrderMaxUtility.getValidString(productPrice.getshippingPrice()));
        taxAuthorityCombinedTextField.setText("");//OrderMaxUtility.getValidString(productPrice.getsupplierProductId()));
        priceTextField.setText("");//OrderMaxUtility.getValidBigDecimal(productPrice.getlastPrice()));
        thruDateTextField.setText("");//OrderMaxUtility.getValidString(productPrice.getunitsIncluded()));
        taxAuthGeoIdTextField.setText("");//OrderMaxUtility.getValidString(productPrice.getorderQtyIncrements()));        
    }

    public void setSupplierproduct(int index) {

        if (index < productPriceListModel.getSize()) {
            productPriceComposite = (ProductPriceComposite) productPriceListModel.getElementAt(index);

//            productPrice = supplierproductComp.getProductPrice();
            setDialogField();
            btnSaveTelephone.setEnabled(false);
        }
    }

    public void setDialogField() {

        productPriceTableModel.setListModel(productPriceListModel);

        if (productPriceComposite != null) {
            ProductPrice productPrice = productPriceComposite.getProductPrice();
            try {
                supplierSelectionComboModel.listModelSelection.setSelection(ProductPriceTypeSingleton.getProductPriceType(productPrice.getproductPriceTypeId()));
            } catch (Exception ex) {
                //        Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            fromDateTextField.setText(OrderMaxUtility.getValidTimestamp(productPrice.getfromDate()));
            taxAuthPartyIdTextField.setText(OrderMaxUtility.getValidString(productPrice.gettaxAuthPartyId()));
            try {
                //        canDropShipTextField.setText(OrderMaxUtility.getValidString(productPrice.getcanDropShip()));
                productPricePurposeSelectionComboModel.listModelSelection.setSelection(ProductPricePurposeSingleton.getProductPricePurposeType(OrderMaxUtility.getValidString(productPrice.getproductPricePurposeId())));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                currencyUomSelectionComboModel.listModelSelection.setSelection(UomCurrencySingleton.getUom(OrderMaxUtility.getValidString(productPrice.getcurrencyUomId())));
            } catch (Exception ex) {
                //          Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            taxPercentageTextField.setText(OrderMaxUtility.getValidString(productPrice.gettaxPercentage()));
            try {
                uomSelectionComboModel.listModelSelection.setSelection(UomQuantitySingleton.getUom(productPrice.gettermUomId()));
            } catch (Exception ex) {
                //            Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                productStoreGroupComboModel.listModelSelection.setSelection(ProductStoreGroupSingleton.getProductStoreGroup(productPrice.getproductStoreGroupId()));
            } catch (Exception ex) {
                //              Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                taxInPriceSelectionComboModel.listModelSelection.setSelection(YesNoConditionSelectSingleton.getString(productPrice.gettaxInPrice()));
            } catch (Exception ex) {
//                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            taxAuthorityCombinedTextField.setText(OrderMaxUtility.getValidString(productPrice.gettaxAuthPartyId()));
            priceTextField.setText(OrderMaxUtility.getValidBigDecimal(productPrice.getprice()));
            thruDateTextField.setText(OrderMaxUtility.getValidTimestamp(productPrice.getthruDate()));
            taxAuthGeoIdTextField.setText(OrderMaxUtility.getValidString(productPrice.gettaxAuthGeoId()));

        }

    }

    public void getDialogField() {
        productPriceTableModel.setListModel(productPriceListModel);

        if (productPriceComposite != null) {
            ProductPrice productPrice = productPriceComposite.getProductPrice();
            try {
                productPrice.setproductPriceTypeId(supplierSelectionComboModel.listModelSelection.getSelection().getproductPriceTypeId());
                productPriceComposite.setProductPriceType(supplierSelectionComboModel.listModelSelection.getSelection());
            } catch (Exception ex) {
                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                productPrice.setfromDate(OrderMaxUtility.getValidTimestamp(fromDateTextField.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            productPrice.settaxAuthPartyId(OrderMaxUtility.getValidString(taxAuthPartyIdTextField.getText()));
            try {
                //        canDropShipTextField.setText(OrderMaxUtility.getValidString(productPrice.getcanDropShip()));
                productPrice.setproductPricePurposeId(productPricePurposeSelectionComboModel.listModelSelection.getSelection().getproductPricePurposeId());
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                productPrice.setcurrencyUomId(currencyUomSelectionComboModel.listModelSelection.getSelection().getuomId());
            } catch (Exception ex) {
                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            productPrice.settaxPercentage(OrderMaxUtility.getValidBigDecimal(taxPercentageTextField.getText()));
            try {
                productPrice.settermUomId(uomSelectionComboModel.listModelSelection.getSelection().getuomId());
            } catch (Exception ex) {
//                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                productPrice.setproductStoreGroupId(productStoreGroupComboModel.listModelSelection.getSelection().getproductStoreGroupId());
            } catch (Exception ex) {
                //              Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                productPrice.settaxInPrice(taxInPriceSelectionComboModel.listModelSelection.getSelection());
            } catch (Exception ex) {
//                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            productPrice.settaxAuthPartyId(taxAuthorityCombinedTextField.getText());
            productPrice.setprice(OrderMaxUtility.getValidBigDecimal(priceTextField.getText()));
            try {
                productPrice.setthruDate(OrderMaxUtility.getValidTimestamp(thruDateTextField.getText()));
            } catch (ParseException ex) {
                //              Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            productPrice.settaxAuthGeoId(taxAuthGeoIdTextField.getText());
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

        panelGoodIdentificationList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableSupplierProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnNewTelephone = new javax.swing.JButton();
        taxAuthPartyIdTextField = new javax.swing.JTextField();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        productPriceTypeIdTextField = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        fromDateTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        taxAuthGeoIdTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        thruDateTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        taxPercentageTextField = new javax.swing.JTextField();
        taxAuthorityCombinedTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        productStoreGroupIdTextField = new javax.swing.JComboBox();
        currencyUomIdTextField = new javax.swing.JComboBox();
        productPricePurposeIdTextField = new javax.swing.JComboBox();
        customPriceCalcServiceTextField = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        taxInPriceTextField = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        termUomIdTextField = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        panelGoodIdentificationList.setMinimumSize(new java.awt.Dimension(27, 200));
        panelGoodIdentificationList.setLayout(new java.awt.BorderLayout());

        jtableSupplierProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtableSupplierProduct);

        panelGoodIdentificationList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panelGoodIdentificationList);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setText("Tax Authority Party:");

        btnNewTelephone.setText("New");
        btnNewTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTelephoneActionPerformed(evt);
            }
        });

        btnSaveTelephone.setText("Save");
        btnSaveTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTelephoneActionPerformed(evt);
            }
        });

        btnDeleteTelephone.setText("Delete");

        jLabel1.setText("Price Type");

        productPriceTypeIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("From Date:");

        jLabel4.setText("Currency Uom Id:");

        jLabel5.setText("Purpose:");

        jLabel8.setText("Tax Auth Geo ID:");

        jLabel9.setText("Thru Date:");

        jLabel10.setText("Product Store Group Id:");

        jLabel11.setText("Tax Auth Combined Id");

        jLabel13.setText("Price:");

        jLabel14.setText("Tax Percentage:");

        jLabel17.setText("Custom Price Calc Service:");

        productStoreGroupIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        currencyUomIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productPricePurposeIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setText("Tax In Price:");

        taxInPriceTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setText("Term Uom Id:");

        termUomIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productPriceTypeIdTextField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productPricePurposeIdTextField, 0, 196, Short.MAX_VALUE)
                    .addComponent(currencyUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productStoreGroupIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(thruDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(priceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(taxPercentageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(taxAuthPartyIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(taxAuthGeoIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(taxInPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termUomIdTextField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taxAuthorityCombinedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(customPriceCalcServiceTextField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(443, 443, 443))
        );

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {currencyUomIdTextField, fromDateTextField, priceTextField, productPricePurposeIdTextField, productPriceTypeIdTextField, productStoreGroupIdTextField, thruDateTextField});

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {taxAuthGeoIdTextField, taxAuthPartyIdTextField, taxPercentageTextField});

        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(productPriceTypeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(productPricePurposeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(currencyUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(productStoreGroupIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(fromDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(thruDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(taxPercentageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(taxAuthPartyIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(taxAuthGeoIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(taxInPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(taxAuthorityCombinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(termUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(customPriceCalcServiceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addGap(105, 105, 105))
        );

        jTabbedPane1.addTab("Detail", panelGoodIdentificationDetail);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("History", jPanel1);

        jPanel2.add(jTabbedPane1);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
    ProductPriceComposite productPriceComposite = null;

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        newPriceRecord();
    }//GEN-LAST:event_btnNewTelephoneActionPerformed

    public void newPriceRecord() {
        clearDialogFields();
        productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), ControllerOptions.getSession());
        setDialogField();
        btnSaveTelephone.setEnabled(true);
    }
    
    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed
        getDialogField();
        Debug.logInfo("btnSaveTelephoneActionPerformed: ", "iSelectedIndex");
        if (productPriceComposite != null
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPricePurposeId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPriceTypeId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductStoreGroupId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getcurrencyUomId())
                && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getfromDate())) {

            try {
                LoadProductPriceWorker.saveProductPrice(productPriceComposite.getProductPrice(), ControllerOptions.getSession());

                productComposite.getProductItemPrice().addProductPrice(productPriceComposite);
                productPriceListModel.add(productPriceComposite);
                setDialogField();
                newPriceRecord();
                jtableSupplierProduct.repaint();
            } catch (Exception ex) {
                Logger.getLogger(ProductPricePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Debug.logInfo("getproductId: " + productPriceComposite.getProductPrice().getproductId(), "module");
            Debug.logInfo("getproductPricePurposeId: " + productPriceComposite.getProductPrice().getproductPricePurposeId(), "module");
            Debug.logInfo("getproductPriceTypeId: " + productPriceComposite.getProductPrice().getproductPriceTypeId(), "module");
            Debug.logInfo("getproductStoreGroupId: " + productPriceComposite.getProductPrice().getproductStoreGroupId(), "module");
            Debug.logInfo("getcurrencyUomId: " + productPriceComposite.getProductPrice().getcurrencyUomId(), "module");
            Debug.logInfo("getfromDate: " + productPriceComposite.getProductPrice().getfromDate().toString(), "module");
        }
    }//GEN-LAST:event_btnSaveTelephoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JComboBox currencyUomIdTextField;
    private javax.swing.JComboBox customPriceCalcServiceTextField;
    private javax.swing.JTextField fromDateTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jtableSupplierProduct;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JComboBox productPricePurposeIdTextField;
    private javax.swing.JComboBox productPriceTypeIdTextField;
    private javax.swing.JComboBox productStoreGroupIdTextField;
    private javax.swing.JTextField taxAuthGeoIdTextField;
    private javax.swing.JTextField taxAuthPartyIdTextField;
    private javax.swing.JTextField taxAuthorityCombinedTextField;
    private javax.swing.JComboBox taxInPriceTextField;
    private javax.swing.JTextField taxPercentageTextField;
    private javax.swing.JComboBox termUomIdTextField;
    private javax.swing.JTextField thruDateTextField;
    // End of variables declaration//GEN-END:variables
}
