/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.purchaseorder;

import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import mvc.controller.LoadAccountWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.table.PurchaseOrderTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.RXTable;
import org.ofbiz.ordermax.base.components.OrderPickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor;

import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.generic.GenericPartySearchPanel;
import org.ofbiz.ordermax.generic.GenericValueDetailTableDialog;
import org.ofbiz.ordermax.orderbase.htmlsummarypages.OrderViewScreenlet;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderEnteryPanel1;
import org.ofbiz.ordermax.party.PartyContactMechHelper;
import org.ofbiz.ordermax.payment.UOMSingleton;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.ordermax.screens.action.GenericPriceLookupAction;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.GenericValueComboBox;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.party.party.PartyHelper;

/**
 *
 * @author siranjeev
 */
public class PurchaseOrderEnteryPanel extends javax.swing.JPanel {

    protected XuiSession session = null;
    public static final String module = PurchaseOrderEnteryPanel.class.getName();
    private static final Border red = new LineBorder(Color.red);
    protected boolean isLoading = false;
//    static protected List<ProductInventoryPojo> orderItemList = FastList.newInstance();

    private JFrame parentFrame = null;
    public Order order = null; //new Order(Order.ORDERTYPE_PURCHSEORDER);
//    public JGenericComboBoxSelectionModel<StatusItem> orderStatusCombo = null;
    public JGenericComboBoxSelectionModel<ProductStore> productStoreCombo = null;
//    public GenericValueComboBox productStoreCombo = null;
//        FacilityListCellRenderer pccRender = new FacilityListCellRenderer(true);
    //       List<Facility> list = FacilitySingleton.getValueList();
//        facilityComboModel = new JComboBoxSelectionModel<Facility>(list, pccRender);

    private GenericValueComboBox lineItemOrderStatusCombo = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private String shippConactMechId = "";
    private String billingAddressMechId = "";
    private String phoneConactMechId = "";
    private String emailConactMechId = "";
    public PartyPickerEditPanel panelPartyIdPicker;
    public PartyPickerEditPanel panelEnterdByIdPicker;
    public OrderPickerEditPanel panelOrderIdPicker;

    //private List<Map<String, Object>> partyMechList = null;
    protected RXTable table;

    public RXTable getTable() {
        return table;
    }

    protected JScrollPane scroller;
    private PurchaseOrderTableModel orderEntryTableModel;

    public PurchaseOrderTableModel getOrderEntryTableModel() {
        return orderEntryTableModel;
    }

    public void setOrderEntryTableModel(PurchaseOrderTableModel orderEntryTableModel) {
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

    private GenericPriceLookupAction averageCostLookupAction = null;
    private GenericPriceLookupAction boxPriceLookupAction = null;
    private GenericPriceLookupAction competitivePriceLookupAction = null;
    private GenericPriceLookupAction defaultPriceLookupAction = null;
    private GenericPriceLookupAction listPriceLookupAction = null;
    private GenericPriceLookupAction maximumPriceLookupAction = null;
    private GenericPriceLookupAction minimumOrderPriceLookupAction = null;
    private GenericPriceLookupAction minimumPriceLookupAction = null;
    private GenericPriceLookupAction promoPriceLookupAction = null;
    private GenericPriceLookupAction specialPromoPriceLookupAction = null;
    private GenericPriceLookupAction wholesalePriceLookupAction = null;
    private GenericPriceLookupAction productSupplierLookupAction = null;

    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
    public JGenericComboBoxSelectionModel<Uom> uomComboBox = null;

    /**
     * Creates new form OrderEnteryPanel
     */
    final public void initComponent1() {
//        try {
        //        orderEntryTableModel = new PurchaseOrderTableModel(columnNames, colTypes);
        orderEntryTableModel = new PurchaseOrderTableModel();
        orderEntryTableModel.addTableModelListener(new InteractiveTableModelListener());

        table = new RXTable();/* {
         public boolean getScrollableTracksViewportWidth() {
         return getPreferredSize().width < getParent().getWidth();
         }
         };*/

        Dimension dim = (Dimension) Toolkit.getDefaultToolkit().getScreenSize().clone();
        dim.width += 100;

        table.setPreferredScrollableViewportSize(dim);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectAllForEdit(true);
        table.setModel(orderEntryTableModel);
        table.setSurrendersFocusOnKeystroke(true);
        table.setRowHeight(26);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        Font font = table.getFont();
        font = font.deriveFont((float) (font.getSize2D() * 1.5));
        table.setFont(font);
        table.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        table.setModel(orderEntryTableModel);
//               btnHeaderPatryId.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField", "SUPPLIER", null));
        ControllerOptions partyOptions = new ControllerOptions(options);
        panelPartyIdPicker = new PartyPickerEditPanel(partyOptions);
        panelPartyCode.setLayout(new BorderLayout());
        panelPartyCode.add(BorderLayout.CENTER, panelPartyIdPicker);

        ControllerOptions enterdByOptions = new ControllerOptions(options);
        panelEnterdByIdPicker = new PartyPickerEditPanel(enterdByOptions);
        panelEnteredBy.setLayout(new BorderLayout());
        panelEnteredBy.add(BorderLayout.CENTER, panelEnterdByIdPicker);

        ControllerOptions currencyOptions = new ControllerOptions(options);
        panelEnterdByIdPicker = new PartyPickerEditPanel(enterdByOptions);
        panelEnteredBy.setLayout(new BorderLayout());
        panelEnteredBy.add(BorderLayout.CENTER, panelEnterdByIdPicker);

        ControllerOptions orderOptions = new ControllerOptions(options);
        orderOptions.put(panelPartyIdPicker.editorId, panelPartyIdPicker.textIdField);
        panelOrderIdPicker = new OrderPickerEditPanel(orderOptions);
        panelOrderId.setLayout(new BorderLayout());
        panelOrderId.add(BorderLayout.CENTER, panelOrderIdPicker);
        popup = new JPopupMenu();

        panelSelecton.setVisible(false);

        listPriceLookupAction = new GenericPriceLookupAction("List Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(listPriceLookupAction.createActionMenuItem());

        averageCostLookupAction = new GenericPriceLookupAction("Average Cost Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(averageCostLookupAction.createActionMenuItem());

        boxPriceLookupAction = new GenericPriceLookupAction("Box Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(boxPriceLookupAction.createActionMenuItem());

        competitivePriceLookupAction = new GenericPriceLookupAction("Competitive Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(competitivePriceLookupAction.createActionMenuItem());

        defaultPriceLookupAction = new GenericPriceLookupAction("Default Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(defaultPriceLookupAction.createActionMenuItem());

        maximumPriceLookupAction = new GenericPriceLookupAction("Maximum Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(maximumPriceLookupAction.createActionMenuItem());

        minimumOrderPriceLookupAction = new GenericPriceLookupAction("Minimum Order Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(minimumOrderPriceLookupAction.createActionMenuItem());

        minimumPriceLookupAction = new GenericPriceLookupAction("Minimum Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(minimumPriceLookupAction.createActionMenuItem());

        promoPriceLookupAction = new GenericPriceLookupAction("Promo Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(promoPriceLookupAction.createActionMenuItem());

        specialPromoPriceLookupAction = new GenericPriceLookupAction("Special Promo Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(specialPromoPriceLookupAction.createActionMenuItem());

        wholesalePriceLookupAction = new GenericPriceLookupAction("Wholesale Price Lookup", session, ProductPrice.ColumnNameId);
        popup.add(wholesalePriceLookupAction.createActionMenuItem());

        productSupplierLookupAction = new GenericPriceLookupAction("Supplier Product Lookup", session, SupplierProduct.ColumnNameId);
        popup.add(productSupplierLookupAction.createActionMenuItem());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());

                    if (!source.isRowSelected(row)) {
                        source.changeSelection(row, column, false, false);
                    }

                    try {
                        OrderItemComposite orderItem = getTableModel().getRowData(table.getSelectedRow());
                        if (orderItem != null) {
                            if (orderItem.getProductItemPrice() != null
                                    && orderItem.getProductItemPrice().getListPrice() != null) {
//                                listPriceLookupAction.setOrderItem(orderItem);
                                listPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getListPrice().getAllGenericValueElement());
                                averageCostLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getAverageCost().getAllGenericValueElement());
                                boxPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getBoxPrice().getAllGenericValueElement());
                                competitivePriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getCompetitivePrice().getAllGenericValueElement());
                                defaultPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getDefaultPrice().getAllGenericValueElement());

                                maximumPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getMaximumPrice().getAllGenericValueElement());
                                minimumOrderPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getMinimumOrderPrice().getAllGenericValueElement());
                                minimumPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getMinimumPrice().getAllGenericValueElement());
                                promoPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getPromoPrice().getAllGenericValueElement());
                                specialPromoPriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getSpecialPromoPrice().getAllGenericValueElement());
                                wholesalePriceLookupAction.setGenericValueDataList(orderItem.getProductItemPrice().getWholesalePrice().getAllGenericValueElement());
                                productSupplierLookupAction.setGenericValueDataList(orderItem.getSupplierProductList().getAllGenericValueElement());
                            }
                            popup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
        });

        scroller = new javax.swing.JScrollPane(table);
        panelDetailTable.setLayout(new java.awt.GridLayout(0, 1));
        panelDetailTable.add(scroller);

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

//            MaskFormatter dateMask1 = new MaskFormatter("####/##/##");
//            dateMask1.install(panelDeliveryDate);
        this.setFocusCycleRoot(true);
        Vector<Component> orderFieldVector = new Vector<Component>(8);
        orderFieldVector.add(panelPartyIdPicker.textIdField);
        orderFieldVector.add(editReference);
        orderFieldVector.add(editOrderName);
        orderFieldVector.add(editOrderDate);
        orderFieldVector.add(panelEntryDate);
        orderFieldVector.add(lcomboLineItemOrderStatus);
        orderFieldVector.add(panelFacilityId);
        orderFieldVector.add(checkBoxPrintConfirmation);
        PurchaseOrderPanelFocusTraversalPolicy policy = new PurchaseOrderPanelFocusTraversalPolicy(orderFieldVector);
        panelHeader.setFocusTraversalPolicy(policy);

        orderFieldVector = new Vector<Component>(8);
        orderFieldVector.add(partyNameTextField);
        orderFieldVector.add(txtLineItemPartyId);
        orderFieldVector.add(txtLineItemReference);
        orderFieldVector.add(txtLineItemAreaCode);
        orderFieldVector.add(txtLineItemPhoneNo);
        orderFieldVector.add(cbLineItemPrint);
        orderFieldVector.add(txtLineItemOrderType);
        orderFieldVector.add(txtLineItemCustomerPo);
        orderFieldVector.add(table);
        policy = new PurchaseOrderPanelFocusTraversalPolicy(orderFieldVector);
        jPanel3.setFocusTraversalPolicy(policy);

        //      } catch (ParseException ex) {
        //                                  Debug.logError(ex,module);
        //      }
        //panelHeader
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelHeader, "Order Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelHistory, "Outstanding Orders");
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelSelecton, "Order Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelDetail, "Order Items");

    }

    public boolean isOrderEditable() {

        return order.getOrderStatusId().compareToIgnoreCase("ORDER_CREATED") == 0;
    }

    public void setLimitRight() {
        boolean isEnabled = isOrderEditable();
        btnSelectAll.setEnabled(isEnabled);
        btnUnSelectAll.setEnabled(isEnabled);
        btnDeleteSelectedItem.setEnabled(isEnabled);
        btnAddNewItem.setEnabled(isEnabled);
    }

    public void setOrderSummary() {
// add a HTMLEditorKit to the editor pane
        HTMLEditorKit kit = new HTMLEditorKit();
        editBillingAddress2.setEditorKit(kit);
        // add some styles to the html

        URL url;
        try {
            url = new File("C:\\backup\\ofbiz-12.04.02\\themes\\flatgrey\\webapp\\flatgrey\\maincss.css").toURI().toURL();

            StyleSheet styleSheet = kit.getStyleSheet();
            styleSheet.importStyleSheet(url);
            OrderViewScreenlet orderViewScreenlet = new OrderViewScreenlet();
            Document doc = kit.createDefaultDocument();
            editBillingAddress2.setDocument(doc);
            editBillingAddress2.setText(orderViewScreenlet.getScreenletHtml(order));

        } catch (MalformedURLException ex) {
            Logger.getLogger(SalesOrderEnteryPanel1.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    ControllerOptions options = null;

    public PurchaseOrderEnteryPanel(final XuiSession session, ControllerOptions options) {
        delegator = session.getDelegator();
        this.session = session;
        this.options = options;

        initComponents();
        initComponent1();

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        statusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(findStatusItemList);
        panelOrderStatus.setLayout(new BorderLayout());
        panelOrderStatus.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);

        uomComboBox = new JGenericComboBoxSelectionModel<Uom>(panelCurrency, UOMSingleton.getValueList("CURRENCY_MEASURE"));

        productStoreCombo = new JGenericComboBoxSelectionModel<ProductStore>(ProductStoreSingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, productStoreCombo.jComboBox);
        try {
            productStoreCombo.comboBoxModel.setSelectedItem(ProductStoreSingleton.getProductStore(session.getProductStore().getproductStoreId()));
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        productStoreCombo.selectionModel.addListSelectionListener();
        List<GenericValue> genValList = PosProductHelper.getGenericValueLists("StatusItem", "statusTypeId", "ORDER_STATUS", delegator);
        lineItemOrderStatusCombo = new GenericValueComboBox(lcomboLineItemOrderStatus, genValList, "StatusItem", "statusId", "description");

        btnItemPatryId = new JButton("..");
        ComponentBorder cb = new ComponentBorder(btnItemPatryId);
        cb.install(txtLineItemPartyId);
        //btnItemPatryId.addActionListener(new LookupActionListner(txtLineItemPartyId, "partyIdTextField", "SUPPLIER", null));

        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);

        interactiveRenderer = new InteractiveRenderer(PurchaseOrderTableModel.Columns.HIDDEN_INDEX.getColumnIndex());

        tabbedPaneOrderPanel.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 0) {
//                        if (UtilValidate.isNotEmpty(txtLineItemOrderDate.getTimeStamp())) {
                        try {
                            editOrderDate.setTimeStamp(txtLineItemOrderDate.getTimeStamp());
                        } catch (Exception ex) {
                            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        }
                        //                       if (UtilValidate.isNotEmpty(txtLineItemEntryDate.getTimeStamp())) {
                        try {
                            editEntryDate.setTimeStamp(txtLineItemEntryDate.getTimeStamp());
                        } catch (Exception ex) {
                            //Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // }                   
                    } else if (pane.getSelectedIndex() == 1) {
                        // if (UtilValidate.isNotEmpty(editOrderDate.getTimeStamp())) {
                        try {
                            txtLineItemOrderDate.setTimeStamp(editOrderDate.getTimeStamp());
                        } catch (Exception ex) {
                            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //}
                        //if (UtilValidate.isNotEmpty(editEntryDate.getTimeStamp())) {
                        try {
                            txtLineItemEntryDate.setTimeStamp(editEntryDate.getTimeStamp());
                        } catch (Exception ex) {
//                                Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // }
                        if (UtilValidate.isNotEmpty(editReference.getText())) {
                            txtLineItemReference.setText(editReference.getText());
                        }
                    }
                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

        JButton btnLineItemOrderId = new JButton("..");
        cb = new ComponentBorder(btnLineItemOrderId);
        cb.install(txtLineItemorderId);
        //btnLineItemOrderId.addActionListener(new LookupActionListner(txtLineItemorderId, "orderIdTextField", txtLineItemPartyId, "partyIdTextField", "SUPPLIER", parentFrame));

    }

    public JTextField getPartyTextField() {
        return panelPartyIdPicker.textIdField;
    }

    public boolean isValidOrderHeader() {
        boolean result = true;

        if (result == true) {
            List<GenericValue> orderList = OrderEntryHelper.getOrderRoleList(panelPartyIdPicker.textIdField.getText(), editReference.getText(), delegator);
            String orderId = "";
            if (orderList.size() != 0) {
                result = false;
                for (GenericValue val : orderList) {
                    orderId = val.getString("orderId");
                    if (orderId.equals(panelOrderIdPicker.textIdField.getText())) {
                        result = true;
                        break;
                    }
                }
            }
            if (result == false) {
                OrderMaxOptionPane.showMessageDialog(null, "Reference or supplier invoice number exists[order id: " + orderId + "].");
            }

        }

        return result;
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

        tabbedPaneOrderPanel = new javax.swing.JTabbedPane();
        panelOrderHeader = new javax.swing.JPanel();
        panelHistory = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        editReference = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        editOrderName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        panelOrderDate = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelEntryDate = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelPartyCode = new javax.swing.JPanel();
        panelOrderId = new javax.swing.JPanel();
        panelOrderStatus = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelOrderStatus1 = new javax.swing.JPanel();
        panelFacilityId = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        edit30Days = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        editCurrOutstanding = new javax.swing.JTextField();
        editTotalBalance = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edit60Days = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        edit90Days = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        checkBoxPrintConfirmation = new javax.swing.JCheckBox();
        panelEnteredBy = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        panelCurrency = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        panelOrderByAdress = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        editBillingAddress = new javax.swing.JEditorPane();
        panelDeliveryToAddress = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        editDeliveryAddress = new javax.swing.JEditorPane();
        paneltable = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelSelecton = new CollapsiblePanel();
        panelLineItem = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtLineItemReference = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtLineItemCustomerPo = new javax.swing.JTextField();
        txtLineItemAreaCode = new javax.swing.JTextField();
        cbLineItemPrint = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtLineItemPhoneNo = new javax.swing.JTextField();
        panelLineItemEntryDate = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtLineItemOrderType = new javax.swing.JTextField();
        panelLineItemOrderDate = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lcomboLineItemOrderStatus = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        txtLineItemorderId = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        partyNameTextField = new javax.swing.JTextField();
        txtLineItemPartyId = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        txtInvoiceContactName = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtInoviceToAddress = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtInvoiceContactNumber = new javax.swing.JTextField();
        txtInoviceToAddress2 = new javax.swing.JTextField();
        txtDeliveryAddress2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtDeliveryAddress1 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        editDeliverContactName = new javax.swing.JTextField();
        txtDeliveryContactNumber = new javax.swing.JTextField();
        panelDetail = new javax.swing.JPanel();
        panelDetailTable = new javax.swing.JPanel();
        panelDetailTotals = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnSelectAll = new javax.swing.JButton();
        btnAddNewItem = new javax.swing.JButton();
        btnDeleteSelectedItem = new javax.swing.JButton();
        btnUnSelectAll = new javax.swing.JButton();
        btnAddNewItem1 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        txtLineItemSubTotal = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtLineItemGSTTotal = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtLineItemChargesTotal = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtLineItemTotalSales = new javax.swing.JTextField();
        panelTerms = new javax.swing.JPanel();
        panelShipping = new javax.swing.JPanel();
        shippingTabbedPane = new javax.swing.JTabbedPane();
        panelShippingGroup = new javax.swing.JPanel();
        panelShippingItem = new javax.swing.JPanel();
        panelShippingOption = new javax.swing.JPanel();
        panelSummary = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        editBillingAddress2 = new javax.swing.JEditorPane();
        panelButton = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(900, 1024));
        setPreferredSize(new java.awt.Dimension(940, 605));
        setLayout(new java.awt.BorderLayout());

        tabbedPaneOrderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tabbedPaneOrderPanel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
            .addGap(0, 245, Short.MAX_VALUE)
        );

        panelOrderHeader.add(panelHistory, java.awt.BorderLayout.CENTER);

        panelHeader.setMinimumSize(new java.awt.Dimension(900, 270));
        panelHeader.setPreferredSize(new java.awt.Dimension(1080, 270));
        panelHeader.setLayout(new javax.swing.BoxLayout(panelHeader, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.setMinimumSize(new java.awt.Dimension(250, 170));
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 306));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 2, 0};
        jPanel1Layout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Supplier Code:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Order Number:");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Reference:");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel15, gridBagConstraints);

        editReference.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editReference.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(editReference, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Order Name:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 33;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        editOrderName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editOrderName.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 167;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(editOrderName, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Order Date:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 38;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel16, gridBagConstraints);

        panelOrderDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelOrderDateLayout = new javax.swing.GroupLayout(panelOrderDate);
        panelOrderDate.setLayout(panelOrderDateLayout);
        panelOrderDateLayout.setHorizontalGroup(
            panelOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOrderDateLayout.setVerticalGroup(
            panelOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelOrderDate, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Entry Date:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

        panelEntryDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelEntryDateLayout = new javax.swing.GroupLayout(panelEntryDate);
        panelEntryDate.setLayout(panelEntryDateLayout);
        panelEntryDateLayout.setHorizontalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelEntryDateLayout.setVerticalGroup(
            panelEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelEntryDate, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Order Status:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        panelPartyCode.setMinimumSize(new java.awt.Dimension(173, 24));
        panelPartyCode.setPreferredSize(new java.awt.Dimension(0, 24));

        javax.swing.GroupLayout panelPartyCodeLayout = new javax.swing.GroupLayout(panelPartyCode);
        panelPartyCode.setLayout(panelPartyCodeLayout);
        panelPartyCodeLayout.setHorizontalGroup(
            panelPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPartyCodeLayout.setVerticalGroup(
            panelPartyCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelPartyCode, gridBagConstraints);

        javax.swing.GroupLayout panelOrderIdLayout = new javax.swing.GroupLayout(panelOrderId);
        panelOrderId.setLayout(panelOrderIdLayout);
        panelOrderIdLayout.setHorizontalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOrderIdLayout.setVerticalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelOrderId, gridBagConstraints);

        panelOrderStatus.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelOrderStatusLayout = new javax.swing.GroupLayout(panelOrderStatus);
        panelOrderStatus.setLayout(panelOrderStatusLayout);
        panelOrderStatusLayout.setHorizontalGroup(
            panelOrderStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOrderStatusLayout.setVerticalGroup(
            panelOrderStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelOrderStatus, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Choose Catalog:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 2, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Product Store Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        panelOrderStatus1.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelOrderStatus1Layout = new javax.swing.GroupLayout(panelOrderStatus1);
        panelOrderStatus1.setLayout(panelOrderStatus1Layout);
        panelOrderStatus1Layout.setHorizontalGroup(
            panelOrderStatus1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelOrderStatus1Layout.setVerticalGroup(
            panelOrderStatus1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelOrderStatus1, gridBagConstraints);

        panelFacilityId.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 1, 3);
        jPanel1.add(panelFacilityId, gridBagConstraints);

        panelHeader.add(jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setMinimumSize(new java.awt.Dimension(250, 250));
        jPanel5.setPreferredSize(new java.awt.Dimension(250, 250));
        jPanel5.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("30 Days:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel17);
        jLabel17.setBounds(4, 72, 120, 30);

        edit30Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit30Days.setEnabled(false);
        edit30Days.setMinimumSize(new java.awt.Dimension(6, 30));
        edit30Days.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(edit30Days);
        edit30Days.setBounds(132, 70, 110, 30);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel29.setText("Current:");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel29);
        jLabel29.setBounds(4, 39, 120, 30);

        editCurrOutstanding.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editCurrOutstanding.setEnabled(false);
        editCurrOutstanding.setMinimumSize(new java.awt.Dimension(6, 30));
        editCurrOutstanding.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(editCurrOutstanding);
        editCurrOutstanding.setBounds(132, 37, 110, 30);

        editTotalBalance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editTotalBalance.setEnabled(false);
        editTotalBalance.setMinimumSize(new java.awt.Dimension(6, 30));
        editTotalBalance.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(editTotalBalance);
        editTotalBalance.setBounds(132, 3, 110, 30);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Entered by:");
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel27);
        jLabel27.setBounds(4, 171, 120, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("60 Days:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel4);
        jLabel4.setBounds(4, 105, 120, 30);

        edit60Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit60Days.setEnabled(false);
        edit60Days.setMinimumSize(new java.awt.Dimension(6, 30));
        edit60Days.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(edit60Days);
        edit60Days.setBounds(132, 103, 110, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("90 Days:");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel12);
        jLabel12.setBounds(4, 138, 120, 30);

        edit90Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        edit90Days.setEnabled(false);
        edit90Days.setMinimumSize(new java.awt.Dimension(6, 30));
        edit90Days.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(edit90Days);
        edit90Days.setBounds(132, 136, 110, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Print Confirmation:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel11);
        jLabel11.setBounds(4, 204, 120, 30);

        checkBoxPrintConfirmation.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkBoxPrintConfirmation.setText("Yes");
        checkBoxPrintConfirmation.setMaximumSize(new java.awt.Dimension(100, 25));
        checkBoxPrintConfirmation.setMinimumSize(new java.awt.Dimension(6, 30));
        checkBoxPrintConfirmation.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel5.add(checkBoxPrintConfirmation);
        checkBoxPrintConfirmation.setBounds(132, 204, 110, 30);

        panelEnteredBy.setMinimumSize(new java.awt.Dimension(6, 30));
        panelEnteredBy.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout panelEnteredByLayout = new javax.swing.GroupLayout(panelEnteredBy);
        panelEnteredBy.setLayout(panelEnteredByLayout);
        panelEnteredByLayout.setHorizontalGroup(
            panelEnteredByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelEnteredByLayout.setVerticalGroup(
            panelEnteredByLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.add(panelEnteredBy);
        panelEnteredBy.setBounds(132, 171, 110, 30);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Currency:");
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel41);
        jLabel41.setBounds(4, 237, 120, 30);

        panelCurrency.setMinimumSize(new java.awt.Dimension(6, 30));
        panelCurrency.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout panelCurrencyLayout = new javax.swing.GroupLayout(panelCurrency);
        panelCurrency.setLayout(panelCurrencyLayout);
        panelCurrencyLayout.setHorizontalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelCurrencyLayout.setVerticalGroup(
            panelCurrencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.add(panelCurrency);
        panelCurrency.setBounds(132, 234, 110, 30);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("Total Outstanding:");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(4, 5, 120, 31);

        panelHeader.add(jPanel5);

        panelOrderByAdress.setMinimumSize(new java.awt.Dimension(200, 100));
        panelOrderByAdress.setPreferredSize(new java.awt.Dimension(200, 100));
        java.awt.GridBagLayout panelOrderByAdressLayout = new java.awt.GridBagLayout();
        panelOrderByAdressLayout.columnWidths = new int[] {0};
        panelOrderByAdressLayout.rowHeights = new int[] {0, 3, 0};
        panelOrderByAdress.setLayout(panelOrderByAdressLayout);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Bill From:");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelOrderByAdress.add(jLabel2, gridBagConstraints);

        editBillingAddress.setEditable(false);
        editBillingAddress.setContentType("text/html"); // NOI18N
        editBillingAddress.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        editBillingAddress.setText("<html>\n  <head>\n\n  </head>\n  <body>\n  </body>\n</html>\n\n");
        jScrollPane3.setViewportView(editBillingAddress);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelOrderByAdress.add(jScrollPane3, gridBagConstraints);

        panelHeader.add(panelOrderByAdress);

        panelDeliveryToAddress.setMinimumSize(new java.awt.Dimension(200, 42));
        panelDeliveryToAddress.setPreferredSize(new java.awt.Dimension(200, 306));
        java.awt.GridBagLayout panelDeliveryToAddressLayout = new java.awt.GridBagLayout();
        panelDeliveryToAddressLayout.columnWidths = new int[] {0};
        panelDeliveryToAddressLayout.rowHeights = new int[] {0, 3, 0};
        panelDeliveryToAddress.setLayout(panelDeliveryToAddressLayout);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Delivery From:");
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelDeliveryToAddress.add(jLabel6, gridBagConstraints);

        editDeliveryAddress.setEditable(false);
        editDeliveryAddress.setContentType("text/html"); // NOI18N
        editDeliveryAddress.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollPane4.setViewportView(editDeliveryAddress);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelDeliveryToAddress.add(jScrollPane4, gridBagConstraints);

        panelHeader.add(panelDeliveryToAddress);

        panelOrderHeader.add(panelHeader, java.awt.BorderLayout.PAGE_START);

        tabbedPaneOrderPanel.addTab("Order Header", panelOrderHeader);

        paneltable.setMaximumSize(new java.awt.Dimension(800, 2147483647));
        paneltable.setMinimumSize(new java.awt.Dimension(770, 305));
        paneltable.setPreferredSize(new java.awt.Dimension(800, 350));
        paneltable.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 3, 0, 3, 0};
        jPanel3Layout.rowHeights = new int[] {0, 2, 0};
        jPanel3.setLayout(jPanel3Layout);

        panelSelecton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 170));
        panelSelecton.setLayout(new java.awt.GridBagLayout());

        panelLineItem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelLineItem.setPreferredSize(new java.awt.Dimension(750, 216));
        java.awt.GridBagLayout jPanel6Layout = new java.awt.GridBagLayout();
        jPanel6Layout.columnWidths = new int[] {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0};
        jPanel6Layout.rowHeights = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0};
        panelLineItem.setLayout(jPanel6Layout);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Reference:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel33, gridBagConstraints);

        txtLineItemReference.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemReference.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemReference, gridBagConstraints);

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setText("Purchase Order #:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel37, gridBagConstraints);

        txtLineItemCustomerPo.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemCustomerPo.setPreferredSize(null);
        txtLineItemCustomerPo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineItemCustomerPoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemCustomerPo, gridBagConstraints);

        txtLineItemAreaCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemAreaCode.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemAreaCode, gridBagConstraints);

        cbLineItemPrint.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbLineItemPrint.setText("Print Confirmation:    ");
        cbLineItemPrint.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(cbLineItemPrint, gridBagConstraints);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Phone Number:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel31, gridBagConstraints);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("/");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        panelLineItem.add(jLabel35, gridBagConstraints);

        txtLineItemPhoneNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemPhoneNo.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemPhoneNo, gridBagConstraints);

        panelLineItemEntryDate.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout panelLineItemEntryDateLayout = new javax.swing.GroupLayout(panelLineItemEntryDate);
        panelLineItemEntryDate.setLayout(panelLineItemEntryDateLayout);
        panelLineItemEntryDateLayout.setHorizontalGroup(
            panelLineItemEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelLineItemEntryDateLayout.setVerticalGroup(
            panelLineItemEntryDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(panelLineItemEntryDate, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Order Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel28, gridBagConstraints);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("Entry Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel39, gridBagConstraints);

        txtLineItemOrderType.setEditable(false);
        txtLineItemOrderType.setBackground(new java.awt.Color(255, 255, 255));
        txtLineItemOrderType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemOrderType.setMinimumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemOrderType, gridBagConstraints);

        panelLineItemOrderDate.setBackground(new java.awt.Color(255, 255, 51));

        javax.swing.GroupLayout panelLineItemOrderDateLayout = new javax.swing.GroupLayout(panelLineItemOrderDate);
        panelLineItemOrderDate.setLayout(panelLineItemOrderDateLayout);
        panelLineItemOrderDateLayout.setHorizontalGroup(
            panelLineItemOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelLineItemOrderDateLayout.setVerticalGroup(
            panelLineItemOrderDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(panelLineItemOrderDate, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Ordere Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel32, gridBagConstraints);

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(jLabel36, gridBagConstraints);

        lcomboLineItemOrderStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(lcomboLineItemOrderStatus, gridBagConstraints);

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Supplier Name / #:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        panelLineItem.add(jLabel30, gridBagConstraints);

        txtLineItemorderId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemorderId.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemorderId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineItemorderIdFocusLost(evt);
            }
        });
        txtLineItemorderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineItemorderIdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 111;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLineItem.add(txtLineItemorderId, gridBagConstraints);

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("Order Number:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLineItem.add(jLabel38, gridBagConstraints);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("/");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        panelLineItem.add(jLabel34, gridBagConstraints);

        partyNameTextField.setEditable(false);
        partyNameTextField.setBackground(new java.awt.Color(255, 255, 255));
        partyNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        partyNameTextField.setMinimumSize(new java.awt.Dimension(6, 30));
        partyNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyNameTextFieldFocusLost(evt);
            }
        });
        partyNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partyNameTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLineItem.add(partyNameTextField, gridBagConstraints);

        txtLineItemPartyId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLineItemPartyId.setMinimumSize(new java.awt.Dimension(6, 30));
        txtLineItemPartyId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLineItemPartyIdFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        panelLineItem.add(txtLineItemPartyId, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        panelSelecton.add(panelLineItem, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        java.awt.GridBagLayout jPanel13Layout = new java.awt.GridBagLayout();
        jPanel13Layout.columnWidths = new int[] {0, 3, 0, 3, 0};
        jPanel13Layout.rowHeights = new int[] {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0};
        jPanel13.setLayout(jPanel13Layout);

        txtInvoiceContactName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInvoiceContactName.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel13.add(txtInvoiceContactName, gridBagConstraints);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("Invoice From Contact:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 0, 0);
        jPanel13.add(jLabel43, gridBagConstraints);

        txtInoviceToAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInoviceToAddress.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInoviceToAddress.setPreferredSize(new java.awt.Dimension(40, 20));
        txtInoviceToAddress.setRequestFocusEnabled(false);
        txtInoviceToAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInoviceToAddressFocusLost(evt);
            }
        });
        txtInoviceToAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInoviceToAddressActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel13.add(txtInoviceToAddress, gridBagConstraints);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Invoice From Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        jPanel13.add(jLabel42, gridBagConstraints);

        txtInvoiceContactNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInvoiceContactNumber.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInvoiceContactNumber.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel13.add(txtInvoiceContactNumber, gridBagConstraints);

        txtInoviceToAddress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtInoviceToAddress2.setMinimumSize(new java.awt.Dimension(6, 30));
        txtInoviceToAddress2.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel13.add(txtInoviceToAddress2, gridBagConstraints);

        txtDeliveryAddress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryAddress2.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryAddress2.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(txtDeliveryAddress2, gridBagConstraints);

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("Deliver From Address:");
        jLabel44.setMaximumSize(new java.awt.Dimension(121, 15));
        jLabel44.setMinimumSize(new java.awt.Dimension(121, 15));
        jLabel44.setPreferredSize(new java.awt.Dimension(121, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        jPanel13.add(jLabel44, gridBagConstraints);

        txtDeliveryAddress1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryAddress1.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryAddress1.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(txtDeliveryAddress1, gridBagConstraints);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("Deliver From Contact:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 14, 0, 0);
        jPanel13.add(jLabel45, gridBagConstraints);

        editDeliverContactName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editDeliverContactName.setMinimumSize(new java.awt.Dimension(6, 30));
        editDeliverContactName.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(editDeliverContactName, gridBagConstraints);

        txtDeliveryContactNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDeliveryContactNumber.setMinimumSize(new java.awt.Dimension(6, 30));
        txtDeliveryContactNumber.setPreferredSize(new java.awt.Dimension(40, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel13.add(txtDeliveryContactNumber, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        panelSelecton.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(panelSelecton, gridBagConstraints);

        panelDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelDetail.setPreferredSize(new java.awt.Dimension(1128, 380));
        panelDetail.setLayout(new java.awt.BorderLayout());

        panelDetailTable.setLayout(new java.awt.GridBagLayout());
        panelDetail.add(panelDetailTable, java.awt.BorderLayout.CENTER);

        panelDetailTotals.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelDetailTotals.setPreferredSize(new java.awt.Dimension(434, 100));

        btnSelectAll.setText("Select All");

        btnAddNewItem.setText("Add New Item");

        btnDeleteSelectedItem.setText("Delete Item");

        btnUnSelectAll.setText("Un Select All");

        btnAddNewItem1.setText("Add New Item");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddNewItem1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btnAddNewItem)
                    .addComponent(btnAddNewItem1))
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

        javax.swing.GroupLayout panelDetailTotalsLayout = new javax.swing.GroupLayout(panelDetailTotals);
        panelDetailTotals.setLayout(panelDetailTotalsLayout);
        panelDetailTotalsLayout.setHorizontalGroup(
            panelDetailTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailTotalsLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        panelDetailTotalsLayout.setVerticalGroup(
            panelDetailTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailTotalsLayout.createSequentialGroup()
                .addGroup(panelDetailTotalsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        panelDetail.add(panelDetailTotals, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(panelDetail, gridBagConstraints);

        paneltable.add(jPanel3, java.awt.BorderLayout.CENTER);

        tabbedPaneOrderPanel.addTab("Line Item", paneltable);

        javax.swing.GroupLayout panelTermsLayout = new javax.swing.GroupLayout(panelTerms);
        panelTerms.setLayout(panelTermsLayout);
        panelTermsLayout.setHorizontalGroup(
            panelTermsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 931, Short.MAX_VALUE)
        );
        panelTermsLayout.setVerticalGroup(
            panelTermsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
        );

        tabbedPaneOrderPanel.addTab("Trading Terms", panelTerms);

        panelShipping.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelShippingGroupLayout = new javax.swing.GroupLayout(panelShippingGroup);
        panelShippingGroup.setLayout(panelShippingGroupLayout);
        panelShippingGroupLayout.setHorizontalGroup(
            panelShippingGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 926, Short.MAX_VALUE)
        );
        panelShippingGroupLayout.setVerticalGroup(
            panelShippingGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        shippingTabbedPane.addTab("Shipping Group", panelShippingGroup);

        javax.swing.GroupLayout panelShippingItemLayout = new javax.swing.GroupLayout(panelShippingItem);
        panelShippingItem.setLayout(panelShippingItemLayout);
        panelShippingItemLayout.setHorizontalGroup(
            panelShippingItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 926, Short.MAX_VALUE)
        );
        panelShippingItemLayout.setVerticalGroup(
            panelShippingItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        shippingTabbedPane.addTab("Shipping Item", panelShippingItem);

        javax.swing.GroupLayout panelShippingOptionLayout = new javax.swing.GroupLayout(panelShippingOption);
        panelShippingOption.setLayout(panelShippingOptionLayout);
        panelShippingOptionLayout.setHorizontalGroup(
            panelShippingOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 926, Short.MAX_VALUE)
        );
        panelShippingOptionLayout.setVerticalGroup(
            panelShippingOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        shippingTabbedPane.addTab("Shipping Option", panelShippingOption);

        panelShipping.add(shippingTabbedPane, java.awt.BorderLayout.CENTER);
        shippingTabbedPane.getAccessibleContext().setAccessibleName("");

        tabbedPaneOrderPanel.addTab("Shipping ", panelShipping);

        panelSummary.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelSummary.setMinimumSize(new java.awt.Dimension(770, 81));
        panelSummary.setPreferredSize(new java.awt.Dimension(770, 90));
        panelSummary.setLayout(new java.awt.GridLayout(1, 0));

        editBillingAddress2.setEditable(false);
        editBillingAddress2.setContentType("text/html"); // NOI18N
        editBillingAddress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editBillingAddress2.setText("<html>\n  <head>\n\n  </head>\n  <body>\ntext\n  </body>\n</html>\n\n");
        jScrollPane10.setViewportView(editBillingAddress2);

        panelSummary.add(jScrollPane10);

        tabbedPaneOrderPanel.addTab("Order Summary", panelSummary);

        add(tabbedPaneOrderPanel, java.awt.BorderLayout.CENTER);

        panelButton.setPreferredSize(new java.awt.Dimension(940, 30));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(panelButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    protected List<OrderItemComposite> getItemList() {
        List<OrderItemComposite> list = new ArrayList<OrderItemComposite>();

        for (int i = 0; i < order.size(); i++) {
            list.add(order.getItem(i));
        }
        return list;
    }

    private void txtInoviceToAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInoviceToAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInoviceToAddressActionPerformed

    private void txtInoviceToAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInoviceToAddressFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInoviceToAddressFocusLost

    private void txtLineItemCustomerPoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineItemCustomerPoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemCustomerPoActionPerformed

    private void txtLineItemPartyIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineItemPartyIdFocusLost
        //        partyIdTextField.setText(txtLineItemPartyId.getText());
        //        notifyListeners(OrderMaxUtility.PARTY_CHANGED, "", txtLineItemPartyId.getText());
        //        Debug.logWarning("Party changed - txtLineItemPartyIdFocusLost", module);
    }//GEN-LAST:event_txtLineItemPartyIdFocusLost

    private void partyNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partyNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partyNameTextFieldActionPerformed

    private void partyNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyNameTextFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_partyNameTextFieldFocusLost

    private void txtLineItemorderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineItemorderIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemorderIdActionPerformed

    private void txtLineItemorderIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLineItemorderIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLineItemorderIdFocusLost

    String addressHeader = "      <table class=\"basic-table\" cellspacing=\"0\">\n"
            + "        <tbody>";
    String addressFooter = "      </tbody></table>\n";

    final public void setDialogFields() {
        setContactDetails(editBillingAddress, true);
        setContactDetails(editDeliveryAddress, false);

        Debug.logInfo("order.getBillFromAccount(): 1" + order.getBillFromAccount().getParty().getDisplayName(), module);

        if (order.getBillFromAccount() != null && order.getBillFromAccount().getParty() != null) {

            panelPartyIdPicker.textIdField.setText(order.getBillFromAccount().getParty().getParty().getpartyId());
            txtLineItemPartyId.setText(order.getBillFromAccount().getParty().getParty().getpartyId());
            if ("PERSON".equals(order.getBillFromAccount().getParty().getPartyTypeId())) {
//                    partyNameTextField.setText(order.getPartyName());
                partyNameTextField.setText(PartyHelper.getPartyName(order.getBillFromAccount().getParty().getPerson().getGenericValueObj(), false));
            } else {
                partyNameTextField.setText(PartyHelper.getPartyName(order.getBillFromAccount().getParty().getPartyGroup().getGenericValueObj(), false));
            }
//            order.getOrderContactList().outputToDebug();
            Debug.logInfo("order.getBillFromAccount(): try to find", module);
            if (order.getContactMech("BILLING_LOCATION") != null) {
                Debug.logInfo("order.getBillFromAccount():  try to  found", module);
                PostalAddress postalMech = LoadAccountWorker.getPostalAddress(order.getContactMech("BILLING_LOCATION"));
                txtInoviceToAddress.setText(OrderMaxUtility.getFormatedAddress1(postalMech.getGenericValueObj()));
                txtInoviceToAddress2.setText(OrderMaxUtility.getFormatedAddress2(postalMech.getGenericValueObj()));
            }

            if (order.getContactMech("PHONE_BILLING") != null) {
                TelecomNumber telecomNumber = LoadAccountWorker.getTelecomNumber(order.getContactMech("PHONE_BILLING"));
                GenericValue telecomMech = telecomNumber.getGenericValueObj();
                if (telecomMech != null) {
//                        editPhone.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                    txtLineItemAreaCode.setText(OrderMaxUtility.getCountryCodeAreaCode(telecomMech));
                    txtLineItemPhoneNo.setText(OrderMaxUtility.getContactNumber(telecomMech));
                    txtInvoiceContactNumber.setText(OrderMaxUtility.getAreadCodeContactNumber(telecomMech));
                    txtInvoiceContactName.setText(OrderMaxUtility.getContactName(telecomMech));
                }
            }

            if (order.getContactMech("SHIPPING_LOCATION") != null) {
                Debug.logInfo("order.getBillFromAccount():  try to  found", module);
                PostalAddress postalAddress = LoadAccountWorker.getPostalAddress(order.getContactMech("SHIPPING_LOCATION"));
                GenericValue postalMech = postalAddress.getGenericValueObj();
//                    editDeliveryAddress.setText(OrderMaxUtility.getFormatedAddress(postalMech));
                txtDeliveryAddress1.setText(OrderMaxUtility.getFormatedAddress1(postalMech));
                txtDeliveryAddress2.setText(OrderMaxUtility.getFormatedAddress2(postalMech));

            }

            if (order.getContactMech("PHONE_SHIPPING") != null) {
                TelecomNumber telecomNumber = LoadAccountWorker.getTelecomNumber(order.getContactMech("PHONE_SHIPPING"));
                GenericValue telecomMech = telecomNumber.getGenericValueObj();
                if (telecomMech != null) {

//                editPhone.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                    txtDeliveryContactNumber.setText(OrderMaxUtility.getAreadCodeContactNumber(telecomMech));
                    editDeliverContactName.setText(OrderMaxUtility.getContactName(telecomMech));
//                        editEmail.setText(OrderMaxUtility.getFormatedTelecom(telecomMech));
                }
            }

        }

        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            cbLineItemPrint.setSelected(true);
        }

        try {
            txtLineItemOrderType.setText(OrderTypeSingleton.getOrderType(order.getOrderType()).getdescription());
        } catch (Exception ex) {

        }
        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(order.getOrderStatusId()));
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (order.getCurrency() != null) {
            try {
                uomComboBox.setSelectedItem(UOMSingleton.getUom(order.getCurrency()));
            } catch (Exception ex) {
                Logger.getLogger(SalesOrderEnteryPanel1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        editOrderName.setText(order.getOrderName());
        try {
            editOrderDate.setTimeStamp(order.getOrderDate());
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            txtLineItemOrderDate.setTimeStamp(order.getOrderDate());
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        editReference.setText(order.getExternalId());

        panelEnterdByIdPicker.textIdField.setText(order.getCreatedBy());

        panelOrderIdPicker.textIdField.setText(order.getOrderId());
        txtLineItemorderId.setText(order.getOrderId());

        //       txtLineItemSubTotal.setText(order.getFinancials().getOrderTotal().toString());
//        txtLineItemTotalSales.setText(order.getFinancials().getOrderTotal().toString());
        editReference.setText(order.getExternalId());
        txtLineItemReference.setText(order.getExternalId());
        edit30Days.setText(order.getFinancials().getDays_30().toString());
        edit60Days.setText(order.getFinancials().getDays_60().toString());
        edit90Days.setText(order.getFinancials().getDays_90().toString());

        editTotalBalance.setText(order.getFinancials().getTotalOutstanding().toString());
        if (order.getDefaultShipAfterDate() != null) {
            try {
                editEntryDate.setTimeStamp(order.getDefaultShipAfterDate());
            } catch (Exception ex) {
                Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                txtLineItemEntryDate.setTimeStamp(order.getDefaultShipAfterDate());
            } catch (Exception ex) {
                Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (order.getCreatedBy() == null || order.getCreatedBy().isEmpty()) {
            order.setCreatedBy(session.getUserId());
        }

        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(order.getOrderStatusId()));
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (lineItemOrderStatusCombo != null) {
            lineItemOrderStatusCombo.setSelectedItemId(order.getOrderStatusId());
        }

        try {
            productStoreCombo.comboBoxModel.setSelectedItem(ProductStoreSingleton.getProductStore(order.getProductStoreId()));
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDialogTotals();
        setLimitRight();
    }

    void setContactDetails(JEditorPane txtBox, boolean billingAddress) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        orderToStringBuilder.append(addressHeader);
        String str = PartyContactMechHelper.getPartyHtmlString(order.getBillFromAccount().getParty());
        orderToStringBuilder.append(str);
        if (billingAddress) {
            orderToStringBuilder.append(PartyContactMechHelper.getOrderBillingDetails(order.getOrderContactMechIds()));
        } else {
            if (order.getContactMech("SHIPPING_LOCATION") != null) {
                orderToStringBuilder.append(PartyContactMechHelper.getOrderShippingDetails(order.getOrderContactMechIds()));
            } else {
                orderToStringBuilder.append(PartyContactMechHelper.getOrderBillingDetails(order.getOrderContactMechIds()));
            }
        }
        orderToStringBuilder.append(addressFooter);
//            Debug.logInfo(orderToStringBuilder.toString(), module);
        txtBox.setText(orderToStringBuilder.toString());
    }

    public void setOrderPartyDetails(String newPartyId) {
    }

    public void getDialogFields() {
//        order.setPartyId(partyIdTextField.getText());
        order.setCreatedBy(panelEnterdByIdPicker.textIdField.getText());
        try {
            order.setDefaultShipAfterDate(editEntryDate.getTimeStamp());
        } catch (Exception ex) {
            //Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            order.setOrderDate(editOrderDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderEnteryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        order.setOrderStatusId(statusItemComboModel.getSelectedItem().getstatusId());

        ProductStore productStore = (ProductStore) productStoreCombo.jComboBox.getSelectedItem();
        if (productStore != null) {
            order.setFacilityId(productStore.getinventoryFacilityId());
            order.setProductStoreId(productStore.getproductStoreId());
        }

        order.setOrderName(editReference.getText());
        order.setExternalId(editReference.getText());
        
        try {
            if (uomComboBox.getSelectedItem() != null) {
                order.setCurrency(session.getDispatcher(), ((Uom) uomComboBox.getSelectedItem()).getuomId());
            }
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }        
    }

    public void clearDialogFields() {
        panelPartyIdPicker.textIdField.setText("");
//        panelDeliveryDate.setText("");
        panelEnterdByIdPicker.textIdField.setText("");
//        editOrderDate.setText("");
        panelOrderIdPicker.textIdField.setText("");

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
//        editEmail.setText("");
        editDeliverContactName.setText("");
        txtDeliveryContactNumber.setText("");
    }

    public void setDialogTotals() {

        Debug.logInfo("txtLineItemTotalSales : " + order.getGrandTotal().toString(), module);
        Debug.logInfo("txtLineItemGSTTotal : " + order.getTaxTotal().toString(), module);
        Debug.logInfo("getSubTotal() : " + order.getSubTotal().toString(), module);
        DecimalFormat formater = new DecimalFormat("#0.00");
        txtLineItemSubTotal.setText(formater.format(order.getSubTotal()));
        txtLineItemTotalSales.setText(formater.format(order.getGrandTotal()));
        txtLineItemGSTTotal.setText(formater.format(order.getTaxTotal()));
        txtLineItemChargesTotal.setText(formater.format(order.getTotalShipping()));

    }

    public boolean saveInvoice(List invoiceListItems) throws GenericEntityException {
        boolean result = false;

        Delegator delegator = (Delegator) session.getDelegator();

        String ownerPartyId = XuiContainer.getSession().getCompanyPartyId();
        String supplierPartyId = panelPartyIdPicker.textIdField.getText();

        String currencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
        String origCurrencyUomId = UtilProperties.getPropertyValue("general", "currency.uom.id.default");
//        String destinationFacilityId = "mainwarehouse"; //facilityListBidingCombo.get(comboFacility.getSelectedIndex());
        String referenceNumber = editReference.getText();
        String destinationFacilityId = order.getFacilityId();

        PosProductHelper.createPurchaseOrder(supplierPartyId, ownerPartyId, currencyUomId, origCurrencyUomId, destinationFacilityId, invoiceListItems, referenceNumber, delegator);
        result = true;

        return result;
    }

    final public void setupEditOrderTable() {

        orderEntryTableModel = new PurchaseOrderTableModel();
        orderEntryTableModel.addTableModelListener(new InteractiveTableModelListener());
        table.setSelectAllForEdit(true);
        table.setModel(orderEntryTableModel);
        table.setSurrendersFocusOnKeystroke(true);

        int colCount = orderEntryTableModel.getColumnCount();
        PurchaseOrderTableModel.Columns[] columns = PurchaseOrderTableModel.Columns.values();

        for (int i = 0; i < colCount; i++) {

            TableColumn column = table.getColumnModel().getColumn(i);
            PurchaseOrderTableModel.Columns columnValues = columns[i];
            column.setPreferredWidth(columnValues.getColumnWidth());
            PurchaseOrderTableModel.Columns columnVal = columns[i];

            if (i == PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex()) {
                setupProductIdEditColumn(column);
            } else if (i == PurchaseOrderTableModel.Columns.ORDER_QTY_INDEX.getColumnIndex()) {
                BigDecimalTableCellEditor cellEditor = new BigDecimalTableCellEditor();
                cellEditor.getTextField().setComponentPopupMenu(popup);
                column.setCellEditor(cellEditor);
            } else if (i == PurchaseOrderTableModel.Columns.ORDER_PRICE_INDEX.getColumnIndex()) {
                BigDecimalTableCellEditor cellEditor = new BigDecimalTableCellEditor();
                cellEditor.getTextField().setComponentPopupMenu(popup);
                column.setCellEditor(cellEditor);
            } else if (i == PurchaseOrderTableModel.Columns.ORDER_LIST_PRICE_INDEX.getColumnIndex()) {
                setupListPriceColumn(column);
            } else if (i == PurchaseOrderTableModel.Columns.ORDER_LASTPRICE_INDEX.getColumnIndex()) {
                setupLastPriceColumn(column);
            }

            if (columnVal.getClassName().equals(BigDecimal.class)) {
                column.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
        }

        TableColumn hidden = table.getColumnModel().getColumn(PurchaseOrderTableModel.Columns.HIDDEN_INDEX.getColumnIndex());
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);

        hidden.setCellRenderer(interactiveRenderer);

        Font font = table.getFont();
        font = font.deriveFont((float) (font.getSize2D() * 1.2));
        table.setFont(font);

    }

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    void setupProductIdEditColumn(TableColumn column) {

        //set the cell editor
        column.setCellEditor(productTreeActionTableCellEditor);

    }

    void setupListPriceColumn(TableColumn column) {

        BigDecimalTableCellEditor editor = new BigDecimalTableCellEditor();
        column.setCellEditor(editor);
        column.setCellRenderer(new DecimalFormatRenderer());
        final JTextField textField = editor.getTextField();
        textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "checkF3");
        textField.getActionMap().put("checkF3", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    final GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(parentFrame, false, XuiContainer.getSession(), ProductPrice.ColumnNameId);
                    final GenericPartySearchPanel searchPanel = new GenericPartySearchPanel();
//                    dlg.setColumnWidths(Arrays.asList(150, 300, 150, 150));
                    dlg.setDoCellRendering(false);
                    dlg.setReadOnlyTable(true);
                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    OrderItemComposite orderItem = getTableModel().getRowData(table.getSelectedRow());
                    dlg.setVisible(true);
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });
    }

    void setupLastPriceColumn(TableColumn column) {

        BigDecimalTableCellEditor editor = new BigDecimalTableCellEditor();
        column.setCellEditor(editor);
        column.setCellRenderer(new DecimalFormatRenderer());

        final JTextField textField = editor.getTextField();
        textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "checkF3");
        textField.getActionMap().put("checkF3", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String[][] ColumnNameId = {
                        {"productId", "Product Id"},
                        {"partyId", "Party Id"},
                        {"lastPrice", "Last Price"},
                        {"supplierProductName", "Supplier Product Name"},
                        {"supplierProductId", "Supplier Product Id"},
                        {"availableFromDate", "Available From Date"},
                        {"availableThruDate", "Available Thru Date"},
                        {"standardLeadTimeDays", "Standard Lead Time Days"},
                        {"minimumOrderQuantity", "Minimum Order Quantity"},
                        {"orderQtyIncrements", "Order Qty Increments"},
                        {"unitsIncluded", "Units Included"},
                        {"quantityUomId", "Quantity Uom Id"},
                        {"shippingPrice", "Shipping Price"},
                        {"currencyUomId", "Currency Uom Id"},};

                    final GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(parentFrame, false, XuiContainer.getSession(), ColumnNameId);
                    final GenericPartySearchPanel searchPanel = new GenericPartySearchPanel();
//                    dlg.setColumnWidths(Arrays.asList(150, 300, 150, 150));
                    dlg.setDoCellRendering(false);
                    dlg.setReadOnlyTable(true);
//                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    OrderItemComposite orderItem = getTableModel().getRowData(table.getSelectedRow());
//                    SupplierProductAndListPriceData data = orderItem.getOrderItemData();//PosProductHelper.getOrderItemDetails(orderItem.getproductId(), productStoreCombo.getSelectedItemId(), session);

//                    dlg.setupOrderTableList(data.getSupplierProductList());
                    dlg.setVisible(true);

//                    dlg.setupOrderTableList(data.getDefaultPriceValueList());
//                    dlg.setVisible(true);
//                    dlg.setupOrderTableList(data.getAvgCostValueList());
//                    dlg.setVisible(true);
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });
    }

    public int getLastEmptyRow() {
        return orderEntryTableModel.getRowCount() - 1;
    }

    public void reloadItemDataModel(ListModel<OrderItemComposite> cutdownList) {
        java.awt.Dimension dim = table.getPreferredSize();
        table.setPreferredSize(new java.awt.Dimension(dim.width, (cutdownList.getSize() + 20) * table.getRowHeight()));
        orderEntryTableModel.setListModel(cutdownList);
    }

    public void updateRowData() {
        for (int i = 0; i < orderEntryTableModel.getRowCount(); ++i) {
            OrderItemComposite date;
            try {
                date = orderEntryTableModel.getRowData(i);
//                date.updateOrderMax();

            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
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

//    public javax.swing.JToggleButton getBtnHeaderPatryId() {
//        return btnHeaderPatryId;
//    }
    public JButton getBtnItemPatryId() {
        return btnItemPatryId;
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    public PurchaseOrderTableModel getTableModel() {
        return orderEntryTableModel;
    }

    private InteractiveRenderer interactiveRenderer = null;

    public InteractiveRenderer getInteractiveRenderer() {
        return interactiveRenderer;
    }

    JPopupMenu popup;

    public class InteractiveTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < getTableModel().getColumnCount()) {
                    table.setColumnSelectionInterval(column + 1, column + 1);
                }
                if (row < getTableModel().getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                }

//                setTotals();
            }
        }
    }

    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#0.##");

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = formatter.format(bdValue);//bdValue.setScale(scale, rounding);
            setHorizontalAlignment(JLabel.RIGHT);
// And pass it on to parent class
            return super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewItem;
    private javax.swing.JButton btnAddNewItem1;
    public javax.swing.JButton btnDeleteSelectedItem;
    private javax.swing.JButton btnSelectAll;
    private javax.swing.JButton btnUnSelectAll;
    private javax.swing.JCheckBox cbLineItemPrint;
    private javax.swing.JCheckBox checkBoxPrintConfirmation;
    public javax.swing.JTextField edit30Days;
    public javax.swing.JTextField edit60Days;
    public javax.swing.JTextField edit90Days;
    private javax.swing.JEditorPane editBillingAddress;
    private javax.swing.JEditorPane editBillingAddress2;
    public javax.swing.JTextField editCurrOutstanding;
    private javax.swing.JTextField editDeliverContactName;
    private javax.swing.JEditorPane editDeliveryAddress;
    private javax.swing.JTextField editOrderName;
    private javax.swing.JTextField editReference;
    public javax.swing.JTextField editTotalBalance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox lcomboLineItemOrderStatus;
    public javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelCurrency;
    private javax.swing.JPanel panelDeliveryToAddress;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelDetailTable;
    private javax.swing.JPanel panelDetailTotals;
    private javax.swing.JPanel panelEnteredBy;
    private javax.swing.JPanel panelEntryDate;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelHeader;
    public javax.swing.JPanel panelHistory;
    private javax.swing.JPanel panelLineItem;
    private javax.swing.JPanel panelLineItemEntryDate;
    private javax.swing.JPanel panelLineItemOrderDate;
    private javax.swing.JPanel panelOrderByAdress;
    private javax.swing.JPanel panelOrderDate;
    private javax.swing.JPanel panelOrderHeader;
    private javax.swing.JPanel panelOrderId;
    private javax.swing.JPanel panelOrderStatus;
    private javax.swing.JPanel panelOrderStatus1;
    private javax.swing.JPanel panelPartyCode;
    private javax.swing.JPanel panelSelecton;
    private javax.swing.JPanel panelShipping;
    public javax.swing.JPanel panelShippingGroup;
    public javax.swing.JPanel panelShippingItem;
    public javax.swing.JPanel panelShippingOption;
    private javax.swing.JPanel panelSummary;
    public javax.swing.JPanel panelTerms;
    private javax.swing.JPanel paneltable;
    private javax.swing.JTextField partyNameTextField;
    public javax.swing.JTabbedPane shippingTabbedPane;
    public javax.swing.JTabbedPane tabbedPaneOrderPanel;
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
    public javax.swing.JTextField txtLineItemTotalSales;
    public javax.swing.JTextField txtLineItemorderId;
    // End of variables declaration//GEN-END:variables
}
