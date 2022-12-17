/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.supplierproduct;

import java.math.BigDecimal;
import java.text.ParseException;
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
import javax.swing.table.TableColumn;
import org.ofbiz.ordermax.dataimportexport.loaders.LoadSupplierProductFromFileWorker;
import mvc.controller.ProductIdVerifyValidator;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PartyListCellRenderer;
import mvc.model.list.StringListCellRenderer;
import mvc.model.list.SupplierPrefOrderListCellRenderer;
import mvc.model.list.UomListCellRenderer;
import mvc.model.table.SupplierProductCompositeTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.SupplierPrefOrder;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class SupplierProductPanel extends javax.swing.JPanel {

    public static final String module = SupplierProductPanel.class.getName();
    private SupplierProductCompositeTableModel supplierProductCompositeTableModel = new SupplierProductCompositeTableModel();
    private ListAdapterListModel<SupplierProductComposite> supplierProductListModel = new ListAdapterListModel<SupplierProductComposite>();

    public ListAdapterListModel<SupplierProductComposite> getSupplierProductListModel() {
        return supplierProductListModel;
    }

    public void setSupplierProductListModel(ListAdapterListModel<SupplierProductComposite> supplierProductListModel) {
        this.supplierProductListModel = supplierProductListModel;
    }

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

    private SetupSelectionModel<Account> supplierSelectionComboModel = null;
    private SetupSelectionModel<String> dropShipSelectionComboModel = null;
    private SetupSelectionModel<Uom> uomSelectionComboModel = null;
    private SetupSelectionModel<SupplierPrefOrder> supplierPerfSelectionComboModel = null;
    private SetupSelectionModel<Uom> currencyUomSelectionComboModel = null;
    private SetupListSelectionModel supplierListSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public SupplierProductPanel() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        supplierSelectionComboModel = new SetupSelectionModel<Account>(PartyListSingleton.getSupplierValueList(),
                partyIdTextField, new PartyListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE));

        uomSelectionComboModel = new SetupSelectionModel<Uom>(UomQuantitySingleton.getValueList(),
                quantityUomIdTextField, new UomListCellRenderer());

        currencyUomSelectionComboModel = new SetupSelectionModel<Uom>(UomCurrencySingleton.getValueList(),
                currencyUomIdTextField, new UomListCellRenderer());
        dropShipSelectionComboModel = new SetupSelectionModel<String>(YesNoConditionSelectSingleton.getValueList(),
                canDropShipTextField, new StringListCellRenderer(false));

        supplierPerfSelectionComboModel = new SetupSelectionModel<SupplierPrefOrder>(SupplierPrefOrderSingleton.getValueList(),
                supplierPrefOrderIdTextField, new SupplierPrefOrderListCellRenderer(false));

//        supplierListSelectionModel = new SetupListSelectionModel<GoodIdentification>(new GoodIdentificationListCellRenderer(false));
        jtableSupplierProduct.setModel(supplierProductCompositeTableModel);

//        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
//        listModelSelection.setListModels(supplierProductListModel, selectionModel);
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
        setupEditOrderTable();
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

    final public void setupEditOrderTable() {

        for (int i = 0; i < SupplierProductCompositeTableModel.Columns.values().length; i++) {
            SupplierProductCompositeTableModel.Columns[] columns = SupplierProductCompositeTableModel.Columns.values();
            SupplierProductCompositeTableModel.Columns column = columns[i];
            TableColumn col = jtableSupplierProduct.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            Debug.logInfo(column.getClassName().toString(), "module");
            if (column.getClassName().equals(java.math.BigDecimal.class)) {
                Debug.logError("BigDecimal format", "module");
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
            /*else if(SupplierProductCompositeTableModel.Columns.PRODUCTID == column){
             jtableSupplierProduct.setSurrendersFocusOnKeystroke(true);
             txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
             editor.setClickCountToStart(0);
             rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(rowColumnClickActionTableCellEditor);                    
             }
             else if(SupplierProductCompositeTableModel.Columns.SUPPLIERID == column){
             jtableSupplierProduct.setSurrendersFocusOnKeystroke(true);
             txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
             editor.setClickCountToStart(0);
             orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(orderRowColumnClickActionTableCellEditor);                   
             }   */
        }
    }

    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        if (productComposite != null) {
            supplierProductListModel.clear();
            supplierProductListModel.addAll(productComposite.getSupplierProductList().getList());

            if (supplierProductListModel.getSize() > 0) {
                setSupplierproduct(0);
            } else {
                clearDialogFields();
                supplierProductComp = createNewSupplierProductComposite();
                setDialogField();
            }
        }
    }

    public void clearDialogFields() {
        availableFromDateTextField.setText("");
        agreementIdTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
//        lastUpdatedTxStampTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedTxStamp()));
        minimumOrderQuantityTextField.setText("");//OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
        availableFromDateTextField.setText("");//OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
        shippingPriceTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
        supplierProductIdTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
        commentsTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getcomments()));
        lastPriceTextField.setText("");//OrderMaxUtility.getValidBigDecimal(supplierproduct.getlastPrice()));
        standardLeadTimeDaysTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getstandardLeadTimeDays()));
        supplierProductNameTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getsupplierProductName()));
        unitsIncludedTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getunitsIncluded()));
        supplierRatingTypeIdTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getsupplierRatingTypeId()));
        agreementItemSeqIdTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getagreementItemSeqId()));
        orderQtyIncrementsTextField.setText("");//OrderMaxUtility.getValidString(supplierproduct.getorderQtyIncrements()));        
    }
    private SupplierProductComposite supplierProductComp = null;

    public void setSupplierproduct(int index) {

        if (index < supplierProductListModel.getSize()) {
            supplierProductComp = (SupplierProductComposite) supplierProductListModel.getElementAt(index);

//            supplierproduct = supplierproductComp.getSupplierProduct();
            setDialogField();
        }
    }

    public void setDialogField() {

        supplierProductCompositeTableModel.setListModel(supplierProductListModel);
        if (supplierProductComp != null) {
            SupplierProduct supplierproduct = supplierProductComp.getSupplierProduct();
            try {
                if (UtilValidate.isNotEmpty(supplierproduct.getpartyId())) {
                    supplierSelectionComboModel.listModelSelection.setSelection(PartyListSingleton.getAccount(supplierproduct.getpartyId()));
                }
            } catch (Exception ex) {
                //              Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            agreementIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getagreementId()));
            minimumOrderQuantityTextField.setText(OrderMaxUtility.getValidBigDecimal(supplierproduct.getminimumOrderQuantity()));
            availableFromDateTextField.setText(OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableFromDate()));
            try {
                //        canDropShipTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getcanDropShip()));
                dropShipSelectionComboModel.listModelSelection.setSelection(YesNoConditionSelectSingleton.getString(OrderMaxUtility.getValidString(supplierproduct.getcanDropShip())));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //        createdTxStampTextField.setText(OrderMaxUtility.getValidTimestamp(supplierproduct.getcreatedTxStamp()));
                uomSelectionComboModel.listModelSelection.setSelection(UomQuantitySingleton.getUom(OrderMaxUtility.getValidString(supplierproduct.getquantityUomId())));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            shippingPriceTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getshippingPrice()));
            try {
                //        partyIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getpartyId()));
//        supplierPrefOrderIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getsupplierPrefOrderId()));
                currencyUomSelectionComboModel.listModelSelection.setSelection(UomCurrencySingleton.getUom(supplierproduct.getcurrencyUomId()));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                supplierPerfSelectionComboModel.listModelSelection.setSelection(SupplierPrefOrderSingleton.geSupplierPrefOrder(supplierproduct.getsupplierPrefOrderId()));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            productIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getproductId()));
            supplierProductIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getsupplierProductId()));
            commentsTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getcomments()));
            lastPriceTextField.setText(OrderMaxUtility.getValidBigDecimal(supplierproduct.getlastPrice()));
            standardLeadTimeDaysTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getstandardLeadTimeDays()));
            supplierProductNameTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getsupplierProductName()));
//        createdStampTextField.setText(OrderMaxUtility.getValidTimestamp(supplierproduct.getcreatedStamp()));
//su        currencyUomIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getcurrencyUomId()));
            unitsIncludedTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getunitsIncluded()));
            supplierRatingTypeIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getsupplierRatingTypeId()));
//        supplierCommissionPercTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getsupplierCommissionPerc()));
//        availableThruDateTextField.setText(OrderMaxUtility.getValidTimestamp(supplierproduct.getavailableThruDate()));
            agreementItemSeqIdTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getagreementItemSeqId()));
//        lastUpdatedStampTextField.setText(OrderMaxUtility.getValidTimestamp(supplierproduct.getlastUpdatedStamp()));
            orderQtyIncrementsTextField.setText(OrderMaxUtility.getValidString(supplierproduct.getorderQtyIncrements()));
        }
    }

    public void getDialogField() {

        if (supplierProductComp != null) {
            SupplierProduct supplierproduct = supplierProductComp.getSupplierProduct();
            supplierproduct.setagreementId(OrderMaxUtility.getValidString(agreementIdTextField.getText()));
            supplierproduct.setminimumOrderQuantity(OrderMaxUtility.getValidBigDecimal(minimumOrderQuantityTextField.getText()));

            try {
                supplierproduct.setpartyId(supplierSelectionComboModel.listModelSelection.getSelection().getParty().getpartyId());
                supplierProductComp.setAccount(PartyListSingleton.getAccount(supplierproduct.getpartyId()));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                supplierproduct.setavailableFromDate(OrderMaxUtility.getValidTimestamp(availableFromDateTextField.getText()));
            } catch (ParseException ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //        supplierproduct.setcanDropShip(OrderMaxUtility.getValidString(canDropShipTextField.getText()));
                supplierproduct.setcanDropShip(YesNoConditionSelectSingleton.getString(dropShipSelectionComboModel.listModelSelection.getSelection()));
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                supplierproduct.setsupplierPrefOrderId(supplierPerfSelectionComboModel.listModelSelection.getSelection().getsupplierPrefOrderId());

//                        SupplierPrefOrderSingleton.geSupplierPrefOrder().getsupplierPrefOrderId());
            } catch (Exception ex) {
//                Logger.getLogger(SupplierProductPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (UtilValidate.isNotEmpty(uomSelectionComboModel.listModelSelection.getSelection())) {
                supplierproduct.setquantityUomId(OrderMaxUtility.getValidString(uomSelectionComboModel.listModelSelection.getSelection().getuomId()));
            }
            supplierproduct.setshippingPrice(OrderMaxUtility.getValidBigDecimal(shippingPriceTextField.getText()));
            supplierproduct.setproductId(OrderMaxUtility.getValidString(supplierProductIdTextField.getText()));
            supplierproduct.setsupplierProductId(OrderMaxUtility.getValidString(supplierProductIdTextField.getText()));
            supplierproduct.setcomments(OrderMaxUtility.getValidString(commentsTextField.getText()));
            supplierproduct.setlastPrice(OrderMaxUtility.getValidBigDecimal(lastPriceTextField.getText()));
            supplierproduct.setstandardLeadTimeDays(OrderMaxUtility.getValidBigDecimal(standardLeadTimeDaysTextField.getText()));
            supplierproduct.setsupplierProductName(OrderMaxUtility.getValidString(supplierProductNameTextField.getText()));
            if (UtilValidate.isNotEmpty(currencyUomSelectionComboModel.listModelSelection.getSelection())) {
                supplierproduct.setcurrencyUomId(OrderMaxUtility.getValidString(currencyUomSelectionComboModel.listModelSelection.getSelection().getuomId()));
            }
            supplierproduct.setunitsIncluded(OrderMaxUtility.getValidBigDecimal(unitsIncludedTextField.getText()));
            supplierproduct.setsupplierRatingTypeId(OrderMaxUtility.getValidString(supplierRatingTypeIdTextField.getText()));
            supplierproduct.setagreementItemSeqId(OrderMaxUtility.getValidString(agreementItemSeqIdTextField.getText()));
            supplierproduct.setorderQtyIncrements(OrderMaxUtility.getValidBigDecimal(orderQtyIncrementsTextField.getText()));
        }
    }

    public SupplierProductComposite createNewSupplierProductComposite() {
        SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(productComposite.getProduct().getproductId());
        spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
        spComp.getSupplierProduct().setsupplierProductId(productComposite.getProduct().getproductId());
        return spComp;
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
        panelGoodIdentificationList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableSupplierProduct = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        availableFromDateTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        partyIdTextField = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        minimumOrderQuantityTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        supplierRatingTypeIdTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        standardLeadTimeDaysTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        orderQtyIncrementsTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        unitsIncludedTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        agreementIdTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        agreementItemSeqIdTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lastPriceTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        shippingPriceTextField = new javax.swing.JTextField();
        supplierProductNameTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        supplierProductIdTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        commentsTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        quantityUomIdTextField = new javax.swing.JComboBox();
        currencyUomIdTextField = new javax.swing.JComboBox();
        supplierPrefOrderIdTextField = new javax.swing.JComboBox();
        canDropShipTextField = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        productIdTextField = new javax.swing.JTextField();
        btnNewTelephone = new javax.swing.JButton();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        btnProductId = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(900, 700));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setPreferredSize(new java.awt.Dimension(855, 300));
        jPanel2.setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationList.setPreferredSize(new java.awt.Dimension(452, 200));
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

        jPanel2.add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);

        add(jPanel2);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1019, 350));

        jLabel2.setText("Available From Date:");

        jLabel1.setText("Supplier:");

        partyIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Min Order Qty:");

        jLabel4.setText("Currency Uom Id:");

        jLabel5.setText("Supplier Pref Order Id:");

        jLabel6.setText("Supplier Rating Type Id:");

        jLabel7.setText("Standard Lead Time Days:");

        jLabel8.setText("Order Qty Increments:");

        jLabel9.setText("Units Included:");

        jLabel10.setText("Quantity UomId:");

        jLabel11.setText("Agreement Id:");

        jLabel12.setText("Agreement Item Seq Id:");

        jLabel13.setText("Last Price:");

        jLabel14.setText("Shipping Price:");

        jLabel15.setText("Supplier Product Name:");

        jLabel16.setText("Supplier Product Id:");

        jLabel17.setText("Supplier can drop ship?:");

        jLabel18.setText("Comments:");

        quantityUomIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        currencyUomIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        supplierPrefOrderIdTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        canDropShipTextField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setText("Product Id:");

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

        btnProductId.setPreferredSize(new java.awt.Dimension(25, 25));

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currencyUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shippingPriceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(lastPriceTextField)
                    .addComponent(supplierProductNameTextField)
                    .addComponent(supplierProductIdTextField)
                    .addComponent(partyIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(productIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quantityUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(unitsIncludedTextField)
                            .addComponent(orderQtyIncrementsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(supplierRatingTypeIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(minimumOrderQuantityTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(agreementIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(standardLeadTimeDaysTextField)
                            .addComponent(availableFromDateTextField)
                            .addComponent(canDropShipTextField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(agreementItemSeqIdTextField))
                        .addContainerGap(53, Short.MAX_VALUE))
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supplierPrefOrderIdTextField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(commentsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {agreementIdTextField, agreementItemSeqIdTextField, availableFromDateTextField, standardLeadTimeDaysTextField, supplierPrefOrderIdTextField});

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {canDropShipTextField, minimumOrderQuantityTextField, orderQtyIncrementsTextField, quantityUomIdTextField, supplierRatingTypeIdTextField, unitsIncludedTextField});

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {currencyUomIdTextField, lastPriceTextField, partyIdTextField, productIdTextField, shippingPriceTextField, supplierProductIdTextField, supplierProductNameTextField});

        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(partyIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(minimumOrderQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(availableFromDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(productIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderQtyIncrementsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(standardLeadTimeDaysTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel8))
                    .addComponent(btnProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(supplierProductIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(unitsIncludedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(agreementIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(supplierProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(quantityUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(agreementItemSeqIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lastPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(supplierRatingTypeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(canDropShipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(shippingPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(supplierPrefOrderIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(currencyUomIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(commentsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Detail", panelGoodIdentificationDetail);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1025, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("History", jPanel1);

        add(jTabbedPane1);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Detail");
    }// </editor-fold>//GEN-END:initComponents

    public SupplierProductComposite getSupplierProductComp() {
        return supplierProductComp;
    }

    public void setSupplierProductComp(SupplierProductComposite supplierProductComp) {
        this.supplierProductComp = supplierProductComp;
    }

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        clearDialogFields();
        supplierProductComp = createNewSupplierProductComposite();
        setDialogField();
    }//GEN-LAST:event_btnNewTelephoneActionPerformed

    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed
        getDialogField();
        Debug.logInfo("btnSaveTelephoneActionPerformed: ", "iSelectedIndex");
        if (supplierProductComp != null
                && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getproductId())
                && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getpartyId())) {
            Debug.logInfo("btnSaveTelephoneActionPerformed productId: " + supplierProductComp.getSupplierProduct().getproductId()
                    + " PartyId: " + supplierProductComp.getSupplierProduct().getpartyId(), module);
            LoadSupplierProductFromFileWorker.saveSupplierProduct(supplierProductComp, GoodIdentificationTypeSingleton.getSingletonSession());
            supplierProductListModel.add(supplierProductComp);
            setDialogField();
            jtableSupplierProduct.repaint();
            Debug.logInfo("btnSaveTelephoneActionPerformed saved sucess: ", module);
        }

    }//GEN-LAST:event_btnSaveTelephoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agreementIdTextField;
    private javax.swing.JTextField agreementItemSeqIdTextField;
    private javax.swing.JTextField availableFromDateTextField;
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JToggleButton btnProductId;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JComboBox canDropShipTextField;
    private javax.swing.JTextField commentsTextField;
    private javax.swing.JComboBox currencyUomIdTextField;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jtableSupplierProduct;
    private javax.swing.JTextField lastPriceTextField;
    private javax.swing.JTextField minimumOrderQuantityTextField;
    private javax.swing.JTextField orderQtyIncrementsTextField;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JComboBox partyIdTextField;
    public javax.swing.JTextField productIdTextField;
    private javax.swing.JComboBox quantityUomIdTextField;
    private javax.swing.JTextField shippingPriceTextField;
    private javax.swing.JTextField standardLeadTimeDaysTextField;
    private javax.swing.JComboBox supplierPrefOrderIdTextField;
    private javax.swing.JTextField supplierProductIdTextField;
    private javax.swing.JTextField supplierProductNameTextField;
    private javax.swing.JTextField supplierRatingTypeIdTextField;
    private javax.swing.JTextField unitsIncludedTextField;
    // End of variables declaration//GEN-END:variables
}
