/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice.salesinvoice;

import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.*;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderPanelFocusTraversalPolicy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ComboKey;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.DatePickerEditPanel;

import org.ofbiz.ordermax.base.RXTable;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceItemCompositeList;

import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeComposite;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.invoice.InvoiceEntryTableModel;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.party.PartyContactMechHelper;
import org.ofbiz.ordermax.screens.UppercaseDocumentFilter;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.GenericValueComboBox;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.party.party.PartyHelper;

/**
 *
 * @author siranjeev
 */
public class SalesInvoiceEnteryPanel extends javax.swing.JPanel {

   
    protected XuiSession session = null;
    public static final String module = PurchaseInvoiceEnteryPanel.class.getName();
    private static final Border red = new LineBorder(Color.red);
    protected boolean isLoading = false;
    static protected List<ProductInventoryPojo> orderItemList = FastList.newInstance();

//    public static final String[] columnNames = {"Sel", "Product Code", "Description", "InvoiceComposite Qty", "Price", "On Hand Qty", "List Price", "Last Price", "", "GST", "Line Total", "Supplier Code"};
//    public Class[] colTypes = {Boolean.class, String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class, BigDecimal.class, BigDecimal.class, String.class};
    final static int decimalWidth = 70;
    private static int[] columnWidth = {10, 25, 200, decimalWidth, decimalWidth, decimalWidth, decimalWidth, decimalWidth, 2, decimalWidth, decimalWidth, 50,};
    private JFrame parentFrame = null;
    public InvoiceComposite order = null; //new Order(Order.ORDERTYPE_PURCHSEORDER);
    public GenericValueComboBox orderStatusCombo = null;
    private GenericValueComboBox facilityIdCombo = null;
    private GenericValueComboBox lineItemOrderStatusCombo = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    protected RXTable table;

    public RXTable getTable() {
        return table;
    }

    protected JScrollPane scroller;
    private InvoiceEntryTableModel orderEntryTableModel;

    public InvoiceEntryTableModel getOrderEntryTableModel() {
        return orderEntryTableModel;
    }

    public void setOrderEntryTableModel(InvoiceEntryTableModel orderEntryTableModel) {
        this.orderEntryTableModel = orderEntryTableModel;
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//            MaskFormatter dateMask = new MaskFormatter("####/##/##");
    private DatePickerEditPanel editOrderDate = null;
    private DatePickerEditPanel editEntryDate = null;
    private DatePickerEditPanel txtLineItemOrderDate = null;
    private DatePickerEditPanel txtLineItemEntryDate = null;
//    private ListPriceLookupAction listPriceLookupAction = null;

    /**
     * Creates new form OrderEnteryPanel
     */
    final public void initComponent1() {
//        try {
        //        orderEntryTableModel = new InvoiceEntryTableModel(columnNames, colTypes);
        orderEntryTableModel = new InvoiceEntryTableModel();
        orderEntryTableModel.addTableModelListener(new InteractiveTableModelListener());

        table = new RXTable() {
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setSelectAllForEdit(true);
        table.setModel(orderEntryTableModel);
        table.setSurrendersFocusOnKeystroke(true);
        table.setRowHeight(20);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        table.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        popup = new JPopupMenu();

        scroller = new javax.swing.JScrollPane(table);
        panelDetail.setLayout(new java.awt.GridLayout(0, 1));
        panelDetail.add(scroller);

        editOrderDate = new DatePickerEditPanel();
        editOrderDate.setSession(session);
        panelOrderDate.setLayout(new BorderLayout());
        panelOrderDate.add(BorderLayout.CENTER, editOrderDate);

        editEntryDate = new DatePickerEditPanel();
        editEntryDate.setSession(session);
        panelEntryDate.setLayout(new BorderLayout());
        panelEntryDate.add(BorderLayout.CENTER, editEntryDate);

        txtLineItemOrderDate = new DatePickerEditPanel();
        txtLineItemOrderDate.setSession(session);
        panelLineItemOrderDate.setLayout(new BorderLayout());
        panelLineItemOrderDate.add(BorderLayout.CENTER, txtLineItemOrderDate);

        txtLineItemEntryDate = new DatePickerEditPanel();
        txtLineItemEntryDate.setSession(session);
        panelLineItemEntryDate.setLayout(new BorderLayout());
        panelLineItemEntryDate.add(BorderLayout.CENTER, txtLineItemEntryDate);

        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) partyIdTextField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) orderIdTextField.getDocument()).setDocumentFilter(filter);

//            MaskFormatter dateMask1 = new MaskFormatter("####/##/##");
//            dateMask1.install(panelDeliveryDate);
        this.setFocusCycleRoot(true);
        Vector<Component> order = new Vector<Component>(7);
        order.add(partyIdTextField);
        order.add(editReference);
        order.add(editOrderDate);
        order.add(panelEntryDate);
        order.add(comboFacility);

        order.add(partyNameTextField);
        order.add(txtLineItemPartyId);
        order.add(txtLineItemReference);
        order.add(txtLineItemAreaCode);
        order.add(txtLineItemPhoneNo);

        order.add(txtLineItemOrderType);
        order.add(lcomboLineItemOrderStatus);
        order.add(txtLineItemCustomerPo);

        order.add(table);
        PurchaseOrderPanelFocusTraversalPolicy policy = new PurchaseOrderPanelFocusTraversalPolicy(order);
        this.setFocusTraversalPolicy(policy);
        
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelHeader, "Invoice Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelHistory, "Outstanding Invoices");                
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelSelecton, "Invoice Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelDetail, "Invoice Items");                
              
    }

    public boolean isOrderEditable() {

        return order.getInvoice().getstatusId().compareToIgnoreCase("ORDER_CREATED") == 0;
    }

    public void setLimitRight() {
        boolean isEnabled = isOrderEditable();
        btnSelectAll.setEnabled(isEnabled);
        btnUnSelectAll.setEnabled(isEnabled);
        btnDeleteSelectedItem.setEnabled(isEnabled);
        btnAddNewItem.setEnabled(isEnabled);
    }

    public void highlightLastRow(int row) {
        int lastrow = orderEntryTableModel.getRowCount();
        if (row == lastrow - 1) {
            table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            table.setRowSelectionInterval(row + 1, row + 1);
        }

        table.setColumnSelectionInterval(0, 0);
    }

    public void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int lastrow = getTableModel().getRowCount();
                if (rowIndex == lastrow - 1) {
                    table.scrollRectToVisible(table.getCellRect(rowIndex - 1, vColIndex, false));
                } else {
                    table.scrollRectToVisible(table.getCellRect(rowIndex + 1, vColIndex, false));
                }

            }
        });
    }

//    private JButton btnHeaderPatryId = null;
    private JButton btnItemPatryId = null;

    public JButton getBtnHeaderOrderId() {
        return btnHeaderOrderId;
    }

    public JButton getBtnItemOrderId() {
        return btnItemOrderId;
    }

    private JButton btnHeaderOrderId = null;
    private JButton btnItemOrderId = null;

    public SalesInvoiceEnteryPanel(final XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        initComponent1();

        List<GenericValue> genValList = PosProductHelper.getGenericValueLists("StatusItem", "statusTypeId", "INVOICE_STATUS", delegator);
        orderStatusCombo = new GenericValueComboBox(comboInvoiceStatus, genValList, "StatusItem", "statusId", "description");

        List<GenericValue> genFacilityList = PosProductHelper.getGenericValueLists("Facility", null, null, delegator);
        facilityIdCombo = new GenericValueComboBox(comboFacility, genFacilityList, "Facility", "facilityId", "facilityName");

//        List<GenericValue> genValList = PosProductHelper.getGenericValueLists("StatusItem", "statusTypeId", "ORDER_STATUS", delegator);
        lineItemOrderStatusCombo = new GenericValueComboBox(lcomboLineItemOrderStatus, genValList, "StatusItem", "statusId", "description");

        //prty id lookup
//        btnHeaderPatryId = new JButton("..");
        //      button.setPreferredSize(new Dimension(10, partyIdTextField.getBounds().height));
//        ComponentBorder cb = new ComponentBorder(btnHeaderPatryId);
//        cb.install(partyIdTextField);
//        btnHeaderPatryId.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField", "CUSTOMER", null));

        btnItemPatryId = new JButton("..");
        ComponentBorder cb = new ComponentBorder(btnItemPatryId);
        cb.install(txtLineItemPartyId);
//        btnItemPatryId.addActionListener(new LookupActionListner(txtLineItemPartyId, "partyIdTextField", "CUSTOMER", null));

        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);

        interactiveRenderer = new InteractiveRenderer(InvoiceEntryTableModel.Columns.HIDDEN_INDEX.getColumnIndex());

        jTabbedPane2.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 0) {
                        try {
                            editOrderDate.setTimeStamp(txtLineItemOrderDate.getTimeStamp());
                        } catch (Exception ex) {
                            Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            editEntryDate.setTimeStamp(txtLineItemEntryDate.getTimeStamp());
                        } catch (Exception ex) {
                            Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        editReference.setText(txtLineItemReference.getText());
                    } else if (pane.getSelectedIndex() == 1) {
                        try {
                            txtLineItemOrderDate.setTimeStamp(editOrderDate.getTimeStamp());
                        } catch (Exception ex) {
                            Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        txtLineItemEntryDate.setTimeStamp(editEntryDate.getTimeStamp());
                        txtLineItemReference.setText(editReference.getText());
                    }
                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

        JButton btnLineItemOrderId = new JButton("..");
        cb = new ComponentBorder(btnLineItemOrderId);
        cb.install(txtLineItemorderId);
//        btnLineItemOrderId.addActionListener(new LookupActionListner(txtLineItemorderId, "orderIdTextField", txtLineItemPartyId, "partyIdTextField", "CUSTOMER", parentFrame));

    }

    public JTextField getPartyTextField() {
        return partyIdTextField;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        panelOrderHeader = new javax.swing.JPanel();
        panelHistory = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        orderIdTextField = new javax.swing.JTextField();
        btnOrderLookup = new javax.swing.JToggleButton();
        jLabel15 = new javax.swing.JLabel();
        editReference = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        editOrderName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        panelOrderDate = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelEntryDate = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        comboInvoiceStatus = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        partyIdTextField = new javax.swing.JTextField();
        btnHeaderPatryId = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        edit30Days = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        editCurrOutstanding = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        comboFacility = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        editTotalBalance = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        editEnteredBy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        edit60Days = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        edit90Days = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        editBillingAddress = new javax.swing.JEditorPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        editDeliveryAddress = new javax.swing.JEditorPane();
        paneltable = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelSelecton = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtLineItemorderId = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        partyNameTextField = new javax.swing.JTextField();
        txtLineItemPartyId = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtLineItemReference = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtLineItemCustomerPo = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        txtLineItemAreaCode = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtLineItemPhoneNo = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        txtLineItemOrderType = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        panelLineItemEntryDate = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        panelLineItemOrderDate = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lcomboLineItemOrderStatus = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        txtInoviceToAddress = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        txtInoviceToAddress2 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtInvoiceContactName = new javax.swing.JTextField();
        txtInvoiceContactNumber = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        txtDeliveryAddress1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        txtDeliveryAddress2 = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        editDeliverContactName = new javax.swing.JTextField();
        txtDeliveryContactNumber = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        panelDetail = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnSelectAll = new javax.swing.JButton();
        btnAddNewItem = new javax.swing.JButton();
        btnDeleteSelectedItem = new javax.swing.JButton();
        btnUnSelectAll = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        txtLineItemSubTotal = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtLineItemGSTTotal = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtLineItemChargesTotal = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtLineItemTotalSales = new javax.swing.JTextField();
        panelSummary = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(900, 32767));
        setPreferredSize(new java.awt.Dimension(940, 605));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        panelOrderHeader.setAlignmentX(0.0F);
        panelOrderHeader.setFocusable(false);
        panelOrderHeader.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelOrderHeader.setMinimumSize(new java.awt.Dimension(770, 151));
        panelOrderHeader.setPreferredSize(new java.awt.Dimension(770, 210));
        panelOrderHeader.setLayout(new java.awt.BorderLayout());

        panelHistory.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "History", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        javax.swing.GroupLayout panelHistoryLayout = new javax.swing.GroupLayout(panelHistory);
        panelHistory.setLayout(panelHistoryLayout);
        panelHistoryLayout.setHorizontalGroup(
            panelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 919, Short.MAX_VALUE)
        );
        panelHistoryLayout.setVerticalGroup(
            panelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 289, Short.MAX_VALUE)
        );

        panelOrderHeader.add(panelHistory, java.awt.BorderLayout.CENTER);

        panelHeader.setPreferredSize(new java.awt.Dimension(1080, 256));
        panelHeader.setLayout(new java.awt.GridLayout(0, 4));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 216));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Customer Code:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Invoice Number:");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jPanel30.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel30.setLayout(new java.awt.BorderLayout());

        orderIdTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        orderIdTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        jPanel30.add(orderIdTextField, java.awt.BorderLayout.CENTER);

        btnOrderLookup.setIcon(new javax.swing.ImageIcon("C:\\AuthLog\\question image_16.png")); // NOI18N
        btnOrderLookup.setPreferredSize(new java.awt.Dimension(25, 25));
        jPanel30.add(btnOrderLookup, java.awt.BorderLayout.EAST);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Reference:");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editReference.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editReference.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Order Name:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editOrderName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editOrderName.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Order Date:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelOrderDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelOrderDateLayout = new javax.swing.GroupLayout(panelOrderDate);
        panelOrderDate.setLayout(panelOrderDateLayout);
        panelOrderDateLayout.setHorizontalGroup(
            panelOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOrderDateLayout.setVerticalGroup(
            panelOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Entry Date:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout panelEntryDateLayout = new javax.swing.GroupLayout(panelEntryDate);
        panelEntryDate.setLayout(panelEntryDateLayout);
        panelEntryDateLayout.setHorizontalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelEntryDateLayout.setVerticalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Invoice Status:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        comboInvoiceStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboInvoiceStatus.setMinimumSize(new java.awt.Dimension(0, 0));
        comboInvoiceStatus.setPreferredSize(new java.awt.Dimension(6, 21));

        jPanel4.setLayout(new java.awt.BorderLayout());

        partyIdTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        partyIdTextField.setMinimumSize(new java.awt.Dimension(6, 30));
        partyIdTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdTextFieldFocusLost(evt);
            }
        });
        jPanel4.add(partyIdTextField, java.awt.BorderLayout.CENTER);

        btnHeaderPatryId.setIcon(new javax.swing.ImageIcon("C:\\AuthLog\\question image_16.png")); // NOI18N
        btnHeaderPatryId.setPreferredSize(new java.awt.Dimension(25, 25));
        jPanel4.add(btnHeaderPatryId, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editOrderName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(editReference, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEntryDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboInvoiceStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editOrderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(panelOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(comboInvoiceStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );

        panelHeader.add(jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(270, 206));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("30 Days:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        edit30Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit30Days.setEnabled(false);
        edit30Days.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Current:");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editCurrOutstanding.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editCurrOutstanding.setEnabled(false);
        editCurrOutstanding.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Product Store Id:");
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        comboFacility.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboFacility.setMinimumSize(new java.awt.Dimension(0, 0));
        comboFacility.setPreferredSize(new java.awt.Dimension(6, 21));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Total:");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editTotalBalance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editTotalBalance.setEnabled(false);
        editTotalBalance.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Entered by:");
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editEnteredBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editEnteredBy.setEnabled(false);
        editEnteredBy.setMinimumSize(new java.awt.Dimension(0, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("60 Days:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        edit60Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit60Days.setEnabled(false);
        edit60Days.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("90 Days:");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        edit90Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit90Days.setEnabled(false);
        edit90Days.setMinimumSize(new java.awt.Dimension(6, 30));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editTotalBalance, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editCurrOutstanding, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edit30Days, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edit60Days, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(edit90Days, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboFacility, javax.swing.GroupLayout.Alignment.TRAILING, 0, 103, Short.MAX_VALUE)
                    .addComponent(editEnteredBy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editTotalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCurrOutstanding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edit30Days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit60Days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit90Days, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(comboFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(editEnteredBy, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        panelHeader.add(jPanel5);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Billing Details:"));
        jPanel15.setLayout(new java.awt.BorderLayout());

        editBillingAddress.setContentType("text/html"); // NOI18N
        editBillingAddress.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollPane3.setViewportView(editBillingAddress);

        jPanel15.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        panelHeader.add(jPanel15);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Delivery Details:"));
        jPanel2.setPreferredSize(new java.awt.Dimension(201, 183));
        jPanel2.setLayout(new java.awt.BorderLayout());

        editDeliveryAddress.setContentType("text/html"); // NOI18N
        editDeliveryAddress.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollPane4.setViewportView(editDeliveryAddress);

        jPanel2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        panelHeader.add(jPanel2);

        panelOrderHeader.add(panelHeader, java.awt.BorderLayout.PAGE_START);

        jTabbedPane2.addTab("Header", panelOrderHeader);

        paneltable.setMaximumSize(new java.awt.Dimension(800, 2147483647));
        paneltable.setMinimumSize(new java.awt.Dimension(770, 305));
        paneltable.setPreferredSize(new java.awt.Dimension(800, 350));
        paneltable.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(155);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(200, 297));

        panelSelecton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 170));
        panelSelecton.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setPreferredSize(new java.awt.Dimension(750, 216));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Supplier Name / #:");

        txtLineItemorderId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemorderId.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemorderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineItemorderIdActionPerformed(evt);
            }
        });
        txtLineItemorderId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineItemorderIdFocusLost(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("Invoice Number:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("/");

        partyNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        partyNameTextField.setMinimumSize(new java.awt.Dimension(6, 30));
        partyNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partyNameTextFieldActionPerformed(evt);
            }
        });
        partyNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyNameTextFieldFocusLost(evt);
            }
        });

        txtLineItemPartyId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemPartyId.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemPartyId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineItemPartyIdFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(partyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLineItemPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(txtLineItemorderId, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(partyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineItemPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(txtLineItemorderId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel34)))
                .addGap(4, 4, 4))
        );

        jPanel6.add(jPanel19);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Reference:");

        txtLineItemReference.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemReference.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setText("Purchase Order:");

        txtLineItemCustomerPo.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemCustomerPo.setPreferredSize(null);
        txtLineItemCustomerPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineItemCustomerPoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLineItemReference, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(txtLineItemCustomerPo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33)
            .addComponent(txtLineItemReference, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtLineItemCustomerPo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.add(jPanel20);

        txtLineItemAreaCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemAreaCode.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Phone Number:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("/");

        txtLineItemPhoneNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemPhoneNo.setMinimumSize(new java.awt.Dimension(6, 30));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLineItemAreaCode, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLineItemPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(txtLineItemAreaCode, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineItemPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanel6.add(jPanel21);

        txtLineItemOrderType.setEditable(false);
        txtLineItemOrderType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemOrderType.setMinimumSize(new java.awt.Dimension(6, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Invoice Type:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("Entry Date:");

        javax.swing.GroupLayout panelLineItemEntryDateLayout = new javax.swing.GroupLayout(panelLineItemEntryDate);
        panelLineItemEntryDate.setLayout(panelLineItemEntryDateLayout);
        panelLineItemEntryDateLayout.setHorizontalGroup(
            panelLineItemEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        panelLineItemEntryDateLayout.setVerticalGroup(
            panelLineItemEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLineItemOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(panelLineItemEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelLineItemEntryDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(txtLineItemOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel22);

        javax.swing.GroupLayout panelLineItemOrderDateLayout = new javax.swing.GroupLayout(panelLineItemOrderDate);
        panelLineItemOrderDate.setLayout(panelLineItemOrderDateLayout);
        panelLineItemOrderDateLayout.setHorizontalGroup(
            panelLineItemOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        panelLineItemOrderDateLayout.setVerticalGroup(
            panelLineItemOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Invoice Date:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Status:");

        lcomboLineItemOrderStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(lcomboLineItemOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(panelLineItemOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(lcomboLineItemOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelLineItemOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel23);

        panelSelecton.add(jPanel6, java.awt.BorderLayout.WEST);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel13.setLayout(new java.awt.GridLayout(0, 1));

        txtInoviceToAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInoviceToAddress.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInoviceToAddress.setPreferredSize(new java.awt.Dimension(40, 20));
        txtInoviceToAddress.setRequestFocusEnabled(false);
        txtInoviceToAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInoviceToAddressActionPerformed(evt);
            }
        });
        txtInoviceToAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInoviceToAddressFocusLost(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Invoice To Address:");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(1, 1, 1)
                .addComponent(txtInoviceToAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInoviceToAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel42)
        );

        jPanel13.add(jPanel24);

        txtInoviceToAddress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInoviceToAddress2.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInoviceToAddress2.setPreferredSize(new java.awt.Dimension(40, 25));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtInoviceToAddress2, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtInoviceToAddress2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.add(jPanel32);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("Invoice To Contact:");

        txtInvoiceContactName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInvoiceContactName.setPreferredSize(new java.awt.Dimension(40, 25));

        txtInvoiceContactNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInvoiceContactNumber.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInvoiceContactNumber.setPreferredSize(new java.awt.Dimension(40, 25));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addGap(1, 1, 1)
                .addComponent(txtInvoiceContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtInvoiceContactNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtInvoiceContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel26);

        txtDeliveryAddress1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryAddress1.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryAddress1.setPreferredSize(new java.awt.Dimension(40, 25));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("Deliver To Address:");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(4, 4, 4)
                .addComponent(txtDeliveryAddress1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDeliveryAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel44)
        );

        jPanel13.add(jPanel27);

        txtDeliveryAddress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryAddress2.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryAddress2.setPreferredSize(new java.awt.Dimension(40, 25));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDeliveryAddress2, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtDeliveryAddress2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.add(jPanel28);

        editDeliverContactName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editDeliverContactName.setMinimumSize(new java.awt.Dimension(6, 30));
        editDeliverContactName.setPreferredSize(new java.awt.Dimension(40, 25));

        txtDeliveryContactNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryContactNumber.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryContactNumber.setPreferredSize(new java.awt.Dimension(40, 25));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("Deliver To Contact:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(4, 4, 4)
                .addComponent(editDeliverContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDeliveryContactNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editDeliverContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDeliveryContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel45))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel29);

        panelSelecton.add(jPanel13, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panelSelecton);

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 925, Short.MAX_VALUE)
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(panelDetail);

        jPanel3.add(jSplitPane1);

        jPanel8.add(jPanel3, java.awt.BorderLayout.CENTER);

        paneltable.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setPreferredSize(new java.awt.Dimension(434, 100));

        btnSelectAll.setText("Select All");
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        btnAddNewItem.setText("Add New Item");
        btnAddNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewItemActionPerformed(evt);
            }
        });

        btnDeleteSelectedItem.setText("Delete Item");
        btnDeleteSelectedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedItemActionPerformed(evt);
            }
        });

        btnUnSelectAll.setText("Un Select All");
        btnUnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnSelectAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUnSelectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteSelectedItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddNewItem)
                .addContainerGap(261, Short.MAX_VALUE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNewItem, btnDeleteSelectedItem, btnUnSelectAll});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelectAll)
                    .addComponent(btnUnSelectAll)
                    .addComponent(btnDeleteSelectedItem)
                    .addComponent(btnAddNewItem))
                .addContainerGap())
        );

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Sub Total:");

        txtLineItemSubTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLineItemSubTotal.setMinimumSize(new java.awt.Dimension(6, 23));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setText("Tax:");

        txtLineItemGSTTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemGSTTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLineItemGSTTotal.setMinimumSize(new java.awt.Dimension(6, 23));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Charges:");

        txtLineItemChargesTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemChargesTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLineItemChargesTotal.setMinimumSize(new java.awt.Dimension(6, 23));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Totals:");

        txtLineItemTotalSales.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemTotalSales.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLineItemTotalSales.setMinimumSize(new java.awt.Dimension(6, 23));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLineItemSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineItemGSTTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineItemChargesTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineItemTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(txtLineItemSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(txtLineItemGSTTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(txtLineItemChargesTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(txtLineItemTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        paneltable.add(jPanel9, java.awt.BorderLayout.PAGE_END);

        jTabbedPane2.addTab("Items", paneltable);

        panelSummary.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelSummary.setMinimumSize(new java.awt.Dimension(770, 81));
        panelSummary.setPreferredSize(new java.awt.Dimension(770, 90));
        panelSummary.setLayout(new java.awt.GridLayout(1, 0));

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
        jScrollPane1.setViewportView(jTable1);

        panelSummary.add(jScrollPane1);

        jTabbedPane2.addTab("Roles", panelSummary);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(469, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Payments", jPanel17);

        add(jTabbedPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        JTable tree = table;
        InputMap inputMap = tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        KeyStroke[] keyStrokes = inputMap.allKeys();
        for (KeyStroke keyStroke : keyStrokes) {
            Object actionCommand = inputMap.get(keyStroke);
            System.out.println("keyStroke = " + keyStroke);
            System.out.println("actionCommand = " + actionCommand);
        }

        for (int i = 0; i < orderEntryTableModel.getRowCount(); ++i) {
            try {
                String prodId = (String) orderEntryTableModel.getValueAt(i, InvoiceEntryTableModel.Columns.QUANTITY.getColumnIndex());
                if (prodId != null && prodId.isEmpty() == false) {
                    orderEntryTableModel.setValueAt(true, i, InvoiceEntryTableModel.Columns.SEL.getColumnIndex());
                }
//                AudioRecord record = orderEntryTableModel.getRowData(i);
//          OrderItemCompositetemMax item = order.getOrderItem(record.getproductId());
//                if (item != null) {
//                    item.itemSelected = record.isSelected();
//                }
            } catch (Exception ex) {
                Debug.logError(ex,module);
            }
        }
    }//GEN-LAST:event_btnSelectAllActionPerformed


    private void btnUnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnSelectAllActionPerformed
        for (int i = 0; i < orderEntryTableModel.getRowCount(); ++i) {
            try {
                orderEntryTableModel.setValueAt(false, i, InvoiceEntryTableModel.Columns.SEL.getColumnIndex());
//                AudioRecord record = orderEntryTableModel.getOrderItemComposite//                OrderItemMax item = order.getOrderItem(record.getproductId());
//                if (item != null) {
//                    item.itemSelected = record.isSelected();
//                }
            } catch (Exception ex) {
                Debug.logError(ex,module);
            }
        }

    }//GEN-LAST:event_btnUnSelectAllActionPerformed

    private void btnDeleteSelectedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedItemActionPerformed

    }//GEN-LAST:event_btnDeleteSelectedItemActionPerformed

    private void btnAddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewItemActionPerformed
        /*        if (!orderEntryTableModel.hasEmptyRow()) {
         orderEntryTableModel.addEmptyRow();
         }
         */
//        reloadItemDataModel(getItemList());
    }//GEN-LAST:event_btnAddNewItemActionPerformed


    private void partyNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyNameTextFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_partyNameTextFieldFocusLost

    private void txtLineItemPartyIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineItemPartyIdFocusLost
    }//GEN-LAST:event_txtLineItemPartyIdFocusLost

    private void partyNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partyNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partyNameTextFieldActionPerformed

    private void txtLineItemorderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineItemorderIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemorderIdActionPerformed

    private void txtLineItemorderIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineItemorderIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemorderIdFocusLost

    private void txtInoviceToAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInoviceToAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInoviceToAddressActionPerformed

    private void txtInoviceToAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInoviceToAddressFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInoviceToAddressFocusLost

    private void txtLineItemCustomerPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineItemCustomerPoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemCustomerPoActionPerformed

    private void partyIdTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdTextFieldFocusLost

    }//GEN-LAST:event_partyIdTextFieldFocusLost

    public DefaultComboBoxModel getPurposeComboxModel(Account account, String mechType) {
        DefaultComboBoxModel comboDeliveryAddressModel = new DefaultComboBoxModel();
        List<PartyContactMechComposite> partyList = account.getPartyContactList().getPartyContactMechTypeList(mechType);
        for (PartyContactMechComposite pc : partyList) {

            PartyContactMechPurposeComposite pcm = pc.getPartyContactMechPurposeList().getPartyContactMechPurpose(pc.getContact().getContactMech().getcontactMechId());
            comboDeliveryAddressModel.addElement(new ComboKey(pcm.getContactMechPurposeType().getcontactMechPurposeTypeId(), pcm.getContactMechPurposeType().getdescription()));
        }

        return comboDeliveryAddressModel;
    }

    String addressHeader =  "      <table class=\"basic-table\" cellspacing=\"0\">\n"
            + "        <tbody>";
    String addressFooter =  "      </tbody></table>\n";

    final public void setDialogFields() {

        if (order.getBillToAccount() != null && order.getBillToAccount().getParty() != null) {

            partyIdTextField.setText(order.getBillToAccount().getParty().getParty().getpartyId());
            txtLineItemPartyId.setText(order.getBillToAccount().getParty().getParty().getpartyId());
            
            if ("PERSON".equals(order.getBillToAccount().getParty().getPartyTypeId())) {
//                    partyNameTextField.setText(order.getPartyName());
                partyNameTextField.setText(PartyHelper.getPartyName(order.getBillToAccount().getParty().getPerson().getGenericValueObj(), false));
            } else {
                partyNameTextField.setText(PartyHelper.getPartyName(order.getBillToAccount().getParty().getPartyGroup().getGenericValueObj(), false));
            }
            
            StringBuilder orderToStringBuilder = new StringBuilder();
            orderToStringBuilder.append(addressHeader);            
            if (order.getInvoiceContactList().getBillingLocationContact() != null) {
                Debug.logInfo("order.getBillToAccount():  try to  found", module);
             
                PostalAddress postalMech = order.getInvoiceContactList().getBillingLocationContact().getContact().getPostalAddress();

                String str = PartyContactMechHelper.getPartyHtmlString(order.getBillToAccount().getParty());
                orderToStringBuilder.append(str);
    
                txtInoviceToAddress.setText(OrderMaxUtility.getFormatedAddress1(postalMech.getGenericValueObj()));
                txtInoviceToAddress2.setText(OrderMaxUtility.getFormatedAddress2(postalMech.getGenericValueObj()));
            }

            if (order.getInvoiceContactList().getBillingLocationPhoneContact() != null) {
                TelecomNumber telecomMech = order.getInvoiceContactList().getBillingLocationPhoneContact().getContact().getTelecomNumber();
                if (telecomMech != null) {
                    txtLineItemAreaCode.setText(OrderMaxUtility.getCountryCodeAreaCode(telecomMech.getGenericValueObj()));
                    txtLineItemPhoneNo.setText(OrderMaxUtility.getContactNumber(telecomMech.getGenericValueObj()));
                    txtInvoiceContactNumber.setText(OrderMaxUtility.getAreadCodeContactNumber(telecomMech.getGenericValueObj()));
                    txtInvoiceContactName.setText(OrderMaxUtility.getContactName(telecomMech.getGenericValueObj()));
                }
            }
            orderToStringBuilder.append(PartyContactMechHelper.getInvoiceBillingDetails(order.getInvoiceContactList()));
            orderToStringBuilder.append(addressFooter);

            editBillingAddress.setText(orderToStringBuilder.toString()/*OrderMaxUtility.getFormatedAddress(postalMech)*/);


/*            if (order.getInvoiceContactList().getBillingLocationPhoneContact() != null) {
                GenericValue telecomMech = order.getInvoiceContactList().getBillingLocationPhoneContact().getContact().getTelecomNumber().getGenericValueObj();
                if (telecomMech != null) {
//                        editPhone.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                    txtLineItemAreaCode.setText(OrderMaxUtility.getCountryCodeAreaCode(telecomMech));
                    txtLineItemPhoneNo.setText(OrderMaxUtility.getContactNumber(telecomMech));
                    txtInvoiceContactNumber.setText(OrderMaxUtility.getAreadCodeContactNumber(telecomMech));
                    txtInvoiceContactName.setText(OrderMaxUtility.getContactName(telecomMech));

                }
            }
*/
            //shiping
            StringBuilder shippingDetailsToStringBuilder = new StringBuilder();            
            if (order.getInvoiceContactList().getShippingLocationContact() != null) {
                PostalAddress postalMech = order.getInvoiceContactList().getShippingLocationContact().getContact().getPostalAddress();

                String str = PartyContactMechHelper.getPartyHtmlString(order.getBillToAccount().getParty());
                shippingDetailsToStringBuilder.append(str);
                str = PartyContactMechHelper.getAddresHtmlString(postalMech, "World");
                shippingDetailsToStringBuilder.append(str);

                txtDeliveryAddress1.setText(OrderMaxUtility.getFormatedAddress1(postalMech.getGenericValueObj()));
                txtDeliveryAddress2.setText(OrderMaxUtility.getFormatedAddress2(postalMech.getGenericValueObj()));
            }

            if (order.getInvoiceContactList().getShippingLocationPhoneContact() != null) {
                TelecomNumber telecomMech = order.getInvoiceContactList().getShippingLocationPhoneContact().getContact().getTelecomNumber();
                if (telecomMech != null) {
//                editPhone.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                    String str = PartyContactMechHelper.getPhoneHtmlString(telecomMech,"tt");
                    shippingDetailsToStringBuilder.append(str);

                    txtDeliveryContactNumber.setText(OrderMaxUtility.getAreadCodeContactNumber(telecomMech.getGenericValueObj()));
                    editDeliverContactName.setText(OrderMaxUtility.getContactName(telecomMech.getGenericValueObj()));
//                        editEmail.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                }
            }
            editDeliveryAddress.setText(shippingDetailsToStringBuilder.toString());            
        }

        try {
            txtLineItemOrderType.setText(InvoiceTypeSingleton.getInvoiceType(order.getInvoice().getinvoiceTypeId()).getdescription());
        } catch (Exception ex) {
            Logger.getLogger(PurchaseInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            editOrderDate.setTimeStamp(order.getInvoice().getinvoiceDate());
        } catch (Exception ex) {
            Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            txtLineItemOrderDate.setTimeStamp(order.getInvoice().getinvoiceDate());
        } catch (Exception ex) {
            Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (order.getInvoice().getdueDate() != null) {
            try {
                editEntryDate.setTimeStamp(order.getInvoice().getdueDate());
            } catch (Exception ex) {
                Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                txtLineItemEntryDate.setTimeStamp(order.getInvoice().getdueDate());
            } catch (Exception ex) {
                Logger.getLogger(SalesInvoiceEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        editEnteredBy.setText(order.getInvoice().getCreatedBy());
        orderIdTextField.setText(order.getInvoice().getinvoiceId());
        txtLineItemorderId.setText(order.getInvoice().getinvoiceId());
        //       txtLineItemSubTotal.setText(order.getFinancials().getOrderTotal().toString());
//        txtLineItemTotalSales.setText(order.getFinancials().getOrderTotal().toString());
        editReference.setText(order.getInvoice().getreferenceNumber());
        txtLineItemReference.setText(order.getInvoice().getreferenceNumber());
        edit30Days.setText(order.getFinancials().getDays_30().toString());
        edit60Days.setText(order.getFinancials().getDays_60().toString());
        edit90Days.setText(order.getFinancials().getDays_90().toString());

        editTotalBalance.setText(order.getFinancials().getTotalOutstanding().toString());
        if (orderStatusCombo != null) {
            orderStatusCombo.setSelectedItemId(order.getInvoice().getstatusId());
        }

        if (lineItemOrderStatusCombo != null) {
            lineItemOrderStatusCombo.setSelectedItemId(order.getInvoice().getstatusId());
        }
        /*
         Debug.logInfo("setDialogFields Order Header get Facility Id: " + order.getdestinationFacilityId(), module);
         if (facilityIdCombo
         != null) {
         facilityIdCombo.setSelectedItemId(order.getDestinationFacilityId());
         }
         */
        setDialogTotals();
        setLimitRight();
    }

    public void setOrderPartyDetails(String newPartyId) {
    }

    public void getDialogFields() {
//        order.setPartyId(partyIdTextField.getText());
//        order.getInvoice().setCreatedBy(editEnteredBy.getText());
//        order.getInvoice().setEntryDate(editEntryDate.getTimeStamp());
//        order.getInvoice().setOrderDate(editOrderDate.getTimeStamp());

        if (orderStatusCombo != null) {
            order.getInvoice().setstatusId(orderStatusCombo.getSelectedItemId());
        }

//        if (facilityIdCombo != null) {
//            order.setDestinationFacilityId(facilityIdCombo.getSelectedItemId());
//        }
        order.getInvoice().setdescription(editReference.getText());
        order.getInvoice().setreferenceNumber(editReference.getText());

        /*
         InvoiceContactList ocl = new InvoiceContactList();
        
         PartyContactMechComposite pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(
         order.getBillToAccount().getParty(), "BILLING_LOCATION");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

         Debug.logInfo("BILLING_LOCATION 1 :", module);
         if (pc != null) {
         Debug.logInfo("BILLING_LOCATION 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
         ocl.add(createSupplierOrderContact(pc, "BILLING_LOCATION"));
         }

         pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(
         order.getBillToAccount().getParty(), "SHIPPING_LOCATION");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

         Debug.logInfo("SHIPPING_LOCATION 1 :", module);
         if (pc != null) {
         Debug.logInfo("SHIPPING_LOCATION 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
         ocl.add(createSupplierOrderContact(pc, "SHIPPING_LOCATION"));
         }

         pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(
         order.getBillToAccount().getParty(), "PHONE_BILLING");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

         Debug.logInfo("PHONE_BILLING 1 :", module);
         if (pc != null) {
         Debug.logInfo("PHONE_BILLING 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
         ocl.add(createSupplierOrderContact(pc, "PHONE_BILLING"));
         }

         pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(
         order.getBillToAccount().getParty(), "PHONE_SHIPPING");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

         Debug.logInfo("PHONE_SHIPPING 1 :", module);
         if (pc != null) {
         Debug.logInfo("PHONE_SHIPPING 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
         ocl.add(createSupplierOrderContact(pc, "PHONE_SHIPPING"));
         }

         pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(
         order.getBillToAccount().getParty(), "BILLING_EMAIL");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

         Debug.logInfo("BILLING_EMAIL 1 :", module);
         if (pc != null) {
         Debug.logInfo("BILLING_EMAIL 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
         ocl.add(createSupplierOrderContact(pc, "BILLING_EMAIL"));
         }

         order.setInvoiceContactList(ocl);
         */
    }

    public void clearDialogFields() {
        partyIdTextField.setText("");
//        panelDeliveryDate.setText("");
        editEnteredBy.setText("");
//        editOrderDate.setText("");
        orderIdTextField.setText("");
        txtLineItemSubTotal.setText("");
        txtLineItemTotalSales.setText("");
        editReference.setText("");
        edit30Days.setText("");
        edit60Days.setText("");
        edit90Days.setText("");
        editTotalBalance.setText("");
        partyNameTextField.setText("");
        txtDeliveryAddress1.setText("");
        txtDeliveryAddress2.setText("");
        txtInoviceToAddress.setText("");
        txtInoviceToAddress2.setText("");
        txtInvoiceContactName.setText("");
        txtInvoiceContactNumber.setText("");
        txtLineItemAreaCode.setText("");
        txtLineItemCustomerPo.setText("");
        txtLineItemOrderType.setText("");
        txtLineItemPartyId.setText("");
        txtLineItemPhoneNo.setText("");
        txtLineItemReference.setText("");
        txtLineItemorderId.setText("");

        editBillingAddress.setText("");
//        editPhone.setText("");
        editDeliveryAddress.setText("");
        editDeliverContactName.setText("");
        txtDeliveryContactNumber.setText("");
    }

    public void setDialogTotals() {
        /*
         Debug.logInfo("txtLineItemTotalSales : " + order.getGrandTotal().toString(), module);
         Debug.logInfo("txtLineItemGSTTotal : " + order.getTaxTotal().toString(), module);
         Debug.logInfo("getSubTotal() : " + order.getSubTotal().toString(), module);

         txtLineItemSubTotal.setText(order.getSubTotal().toString());
         txtLineItemTotalSales.setText(order.getGrandTotal().toString());
         txtLineItemGSTTotal.setText(order.getTaxTotal().toString());
         txtLineItemChargesTotal.setText(order.getTotalShipping().toString());
         */
    }

    final public void setupEditOrderTable() {

        orderEntryTableModel = new InvoiceEntryTableModel();
        orderEntryTableModel.addTableModelListener(new InteractiveTableModelListener());
        table.setSelectAllForEdit(true);
        table.setModel(orderEntryTableModel);
        table.setSurrendersFocusOnKeystroke(true);

        for (int i = 1; i < InvoiceEntryTableModel.Columns.values().length; i++) {
            InvoiceEntryTableModel.Columns[] columns = InvoiceEntryTableModel.Columns.values();
            InvoiceEntryTableModel.Columns column = columns[i];
            TableColumn col = table.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

        /*        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         TableColumnAdjuster tca = new TableColumnAdjuster(table);
         tca.adjustColumns();
         */
        /*
         if (i == InvoiceEntryTableModel.Columns.QUANTITY.getColumnIndex()) {
         setupProductIdEditColumn(column);

         } else if (i == InvoiceEntryTableModel.Columns.PRODUCT_ID.getColumnIndex()) {
         BigDecimalTableCellEditor cellEditor = new BigDecimalTableCellEditor();
         cellEditor.getTextField().setComponentPopupMenu(popup);
         column.setCellEditor(cellEditor);
         column.setCellRenderer(new DecimalFormatRenderer());

         } else if (i == InvoiceEntryTableModel.Columns.DESCRIPTION.getColumnIndex()) {
         BigDecimalTableCellEditor cellEditor = new BigDecimalTableCellEditor();
         cellEditor.getTextField().setComponentPopupMenu(popup);
         column.setCellEditor(cellEditor);
         column.setCellRenderer(new DecimalFormatRenderer());

         } else if (i == InvoiceEntryTableModel.Columns.TOTAL.getColumnIndex()) {
         //            setupLastPriceColumn(column);
         column.setCellRenderer(new DecimalFormatRenderer());
         } else if (i == InvoiceEntryTableModel.Columns.OVERRIDE_GL_ACCOUNT_ID.getColumnIndex()) {
         //            setupLastPriceColumn(column);
         column.setCellRenderer(new DecimalFormatRenderer());
         }

         }
         */
        TableColumn hidden = table.getColumnModel().getColumn(InvoiceEntryTableModel.Columns.HIDDEN_INDEX.getColumnIndex());
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);

        hidden.setCellRenderer(interactiveRenderer);

    }

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    void setupProductIdEditColumn(TableColumn column) {

        //set the cell editor
        column.setCellEditor(productTreeActionTableCellEditor);

//        textField.getActionMap().put("checkShiftTab", new AbstractAction() {
//            public void actionPerformed(ActionEvent e) {
//                tableCellAction(textField, table.getSelectedRow(), table.getSelectedColumn());
//            }
//        });
    }

    public int getLastEmptyRow() {
//        if (orderEntryTableModel.hasEmptyRow() == false) {
//            orderEntryTableModel.addEmptyRow();

//        }
        return orderEntryTableModel.getRowCount() - 1;
    }

    public void reloadItemDataModel(InvoiceItemCompositeList cutdownList) {
//        setupEditOrderTable();
        java.awt.Dimension dim = table.getPreferredSize();

        table.setPreferredSize(new java.awt.Dimension(dim.width, (cutdownList.getList().size() + 20) * table.getRowHeight()));
        List<InvoiceItemComposite> dataVector = new ArrayList<InvoiceItemComposite>();
        for (InvoiceItemComposite record : cutdownList.getList()) {
//            AudioRecord auRecord = AudioRecord.createRecord(record);
            Debug.logInfo("record.getOrderItem().getproductId(): " + record.getOrderItem().getproductId(), module);
            if(UtilValidate.isNotEmpty(record.getOrderItem().getproductId())){
            SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(record.getOrderItem().getproductId(), facilityIdCombo.getSelectedItemId(), session);
            }
            else{
                
            }

//            record.setOrderItemData(data);
//            auRecord.setupOrderItemData(order.getPartyId());
            dataVector.add(record);
        }
        if (dataVector.size() > 0) {
            orderEntryTableModel.setDataList(dataVector);
            orderEntryTableModel.fireTableDataChanged();
        }
    }

    public void updateRowData() {
        for (int i = 0; i < orderEntryTableModel.getRowCount(); ++i) {
            InvoiceItemComposite date;
            try {
                date = orderEntryTableModel.getRowData(i);
//                date.updateOrderMax();

            } catch (Exception ex) {
                Debug.logError(ex,module);            
            }

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

    public InvoiceComposite getOrder() {
        return order;
    }

    public void setOrder(InvoiceComposite order) {
        this.order = order;
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public JToggleButton getBtnHeaderPatryId() {
        return btnHeaderPatryId;
    }

    public JButton getBtnItemPatryId() {
        return btnItemPatryId;
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    public InvoiceEntryTableModel getTableModel() {
        return orderEntryTableModel;
    }

    private InteractiveRenderer interactiveRenderer = null;

    public InteractiveRenderer getInteractiveRenderer() {
        return interactiveRenderer;
    }

    JPopupMenu popup;

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < getTableModel().getColumnCount()) {
                    table.setColumnSelectionInterval(column + 1, column + 1);
                }
                table.setRowSelectionInterval(row, row);
//                setTotals();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewItem;
    public javax.swing.JButton btnDeleteSelectedItem;
    public javax.swing.JToggleButton btnHeaderPatryId;
    public javax.swing.JToggleButton btnOrderLookup;
    private javax.swing.JButton btnSelectAll;
    private javax.swing.JButton btnUnSelectAll;
    private javax.swing.JComboBox comboFacility;
    private javax.swing.JComboBox comboInvoiceStatus;
    public javax.swing.JTextField edit30Days;
    public javax.swing.JTextField edit60Days;
    public javax.swing.JTextField edit90Days;
    private javax.swing.JEditorPane editBillingAddress;
    public javax.swing.JTextField editCurrOutstanding;
    private javax.swing.JTextField editDeliverContactName;
    private javax.swing.JEditorPane editDeliveryAddress;
    private javax.swing.JTextField editEnteredBy;
    private javax.swing.JTextField editOrderName;
    private javax.swing.JTextField editReference;
    public javax.swing.JTextField editTotalBalance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox lcomboLineItemOrderStatus;
    public javax.swing.JTextField orderIdTextField;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelEntryDate;
    private javax.swing.JPanel panelHeader;
    public javax.swing.JPanel panelHistory;
    private javax.swing.JPanel panelLineItemEntryDate;
    private javax.swing.JPanel panelLineItemOrderDate;
    private javax.swing.JPanel panelOrderDate;
    private javax.swing.JPanel panelOrderHeader;
    private javax.swing.JPanel panelSelecton;
    private javax.swing.JPanel panelSummary;
    private javax.swing.JPanel paneltable;
    public javax.swing.JTextField partyIdTextField;
    private javax.swing.JTextField partyNameTextField;
    private javax.swing.JTextField txtDeliveryAddress1;
    private javax.swing.JTextField txtDeliveryAddress2;
    private javax.swing.JTextField txtDeliveryContactNumber;
    private javax.swing.JTextField txtInoviceToAddress;
    private javax.swing.JTextField txtInoviceToAddress2;
    private javax.swing.JTextField txtInvoiceContactName;
    private javax.swing.JTextField txtInvoiceContactNumber;
    private javax.swing.JTextField txtLineItemAreaCode;
    private javax.swing.JTextField txtLineItemChargesTotal;
    private javax.swing.JTextField txtLineItemCustomerPo;
    private javax.swing.JTextField txtLineItemGSTTotal;
    private javax.swing.JTextField txtLineItemOrderType;
    public javax.swing.JTextField txtLineItemPartyId;
    private javax.swing.JTextField txtLineItemPhoneNo;
    private javax.swing.JTextField txtLineItemReference;
    private javax.swing.JTextField txtLineItemSubTotal;
    private javax.swing.JTextField txtLineItemTotalSales;
    public javax.swing.JTextField txtLineItemorderId;
    // End of variables declaration//GEN-END:variables
}
