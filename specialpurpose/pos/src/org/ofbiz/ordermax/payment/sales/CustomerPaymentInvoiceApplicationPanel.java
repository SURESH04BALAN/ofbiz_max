/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.sales;

import org.ofbiz.ordermax.payment.purchase.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import javax.swing.table.*;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.InvoiceCompositePaymentTableModel;
import org.ofbiz.ordermax.base.InvoiceRolePartyPayment;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.orderbase.InteractiveRenderer;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.payment.InvoicePaymentHelper;

/**
 *
 * @author administrator
 */
public class CustomerPaymentInvoiceApplicationPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = CustomerPaymentInvoiceApplicationPanel.class.getName();
    private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private java.awt.Frame parentFrame = null;

    private InvoiceCompositePaymentTableModel invoiceCompositePaymentTableModel = new InvoiceCompositePaymentTableModel();
//    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

    public InvoiceCompositePaymentTableModel getInvoiceCompositePaymentTableModel() {
        return invoiceCompositePaymentTableModel;
    }

    /**
     * Creates new form PaymentPanel
     */
    public CustomerPaymentInvoiceApplicationPanel() {
        initComponents();
        tablePaymentApplication.setModel(invoiceCompositePaymentTableModel);
        setupEditOrderTable();
    }

    public CustomerPaymentInvoiceApplicationPanel(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        tablePaymentApplication.setModel(invoiceCompositePaymentTableModel);
        setupEditOrderTable();
    }

    final public void setupEditOrderTable() {

        tablePaymentApplication.setSurrendersFocusOnKeystroke(true);
        for (int i = 0; i < InvoiceCompositePaymentTableModel.Columns.values().length; i++) {

            InvoiceCompositePaymentTableModel.Columns[] columns = InvoiceCompositePaymentTableModel.Columns.values();
            InvoiceCompositePaymentTableModel.Columns column = columns[i];
            TableColumn col = tablePaymentApplication.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

    }
    ListAdapterListModel<InvoiceComposite> invoiceCompositePaymentListModel = null;

    public void setPaymentApplicationList(ListAdapterListModel<InvoiceComposite> orderListModel) {

        invoiceCompositePaymentTableModel.setListModel(orderListModel);
        invoiceCompositePaymentListModel = orderListModel;
    }

    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    public JTextField getTxtProdIdTableTextField() {
        return null;//txtProdIdTableTextField;
    }

    public JTable getTable() {
        return null; //table;
    }

    public void setDialogTotals() {

    }
    void setDialogFields() {
    
    }
    public void clearDialogFields() {
        tablePaymentApplication.removeAll();
        cbShowZeroAmount.setSelected(false);
        txtPaymentAmount.setText("0");
        txtAllocatedAmount.setText("0");
        txtUnallocatedAmount.setText("0");
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return null;//productTreeActionTableCellEditor;
    }

    private InteractiveRenderer interactiveRenderer = null;

    public InteractiveRenderer getInteractiveRenderer() {
        return interactiveRenderer;
    }

    public InvoiceCompositePaymentTableModel getTableModel() {
        return invoiceCompositePaymentTableModel;
    }

    public void reloadItemDataModel(ListAdapterListModel<InvoiceComposite> invoiceList) {
        invoiceCompositePaymentTableModel.setListModel(invoiceList);
    }

    private List<InvoiceRolePartyPayment> resultlist = new ArrayList<InvoiceRolePartyPayment>();

    public List<InvoiceRolePartyPayment> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<InvoiceRolePartyPayment> resultlist) {
        this.resultlist = resultlist;
    }

    public void loadInvoiceDetails(String partyId, String roleTypeId, String invoiceTypeId) {
        ;
        try {
            List<Map<String, Object>> list = InvoicePaymentHelper.getPurchaseInvoices(partyId, roleTypeId, invoiceTypeId, delegator);
            resultlist = new ArrayList<InvoiceRolePartyPayment>();
            for (Map<String, Object> mapVal : list) {
                InvoiceRolePartyPayment val = new InvoiceRolePartyPayment(mapVal, delegator);
                resultlist.add(val);
            }
//            SimpleTableModel model = new SimpleTableModel(resultlist);
//            tablePaymentApplication.setModel(model);

        } catch (GenericEntityException e) {
            Debug.logError(e, "hhi");
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

        panelPaymentDetail = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePaymentApplication = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAutoAllocate = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();
        txtUnallocatedAmount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAllocatedAmount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSaveOrder = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnLoadInvoices = new javax.swing.JButton();
        cbShowZeroAmount = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        panelPaymentDetail.setLayout(new java.awt.BorderLayout());

        tablePaymentApplication.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "5", "9", "13"},
                {"2", "6", "10", "14"},
                {"3", "7", "11", "15"},
                {"4", "8", "12", null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablePaymentApplication);

        panelPaymentDetail.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(806, 60));

        btnAutoAllocate.setText("Auto Allocate");

        btnClearAll.setText("Clear All");

        txtUnallocatedAmount.setText("0");
        txtUnallocatedAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUnallocatedAmountFocusLost(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("UnAllocated:");

        txtAllocatedAmount.setText("0");
        txtAllocatedAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAllocatedAmountFocusLost(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Allocated:");

        txtPaymentAmount.setPreferredSize(new java.awt.Dimension(6, 25));
        txtPaymentAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaymentAmountFocusLost(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Payment Amount:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 497, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAllocatedAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtUnallocatedAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAutoAllocate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClearAll))
                    .addComponent(jLabel7))
                .addGap(20, 20, 20))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAutoAllocate, btnClearAll});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAllocatedAmount, txtPaymentAmount, txtUnallocatedAmount});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUnallocatedAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAllocatedAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAutoAllocate)
                    .addComponent(btnClearAll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAllocatedAmount, txtPaymentAmount, txtUnallocatedAmount});

        panelPaymentDetail.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        btnSaveOrder.setText("SAVE  PAYMENT");

        btnCancel.setText("CANCEL");
        btnCancel.setMinimumSize(new java.awt.Dimension(47, 15));
        btnCancel.setPreferredSize(new java.awt.Dimension(107, 20));

        btnOk.setText("OK");
        btnOk.setMinimumSize(new java.awt.Dimension(47, 15));
        btnOk.setPreferredSize(new java.awt.Dimension(107, 20));

        btnLoadInvoices.setText("Load Invoices");

        cbShowZeroAmount.setText("Show zero amounts");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSaveOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLoadInvoices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbShowZeroAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 426, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveOrder)
                        .addComponent(btnLoadInvoices)
                        .addComponent(cbShowZeroAmount)))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        panelPaymentDetail.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        add(panelPaymentDetail, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUnallocatedAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnallocatedAmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnallocatedAmountFocusLost

    private void txtAllocatedAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllocatedAmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAllocatedAmountFocusLost

    private void txtPaymentAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaymentAmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentAmountFocusLost

    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    @Override
    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    @Override
    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveItem() throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    @Override
    public void loadItem() {
        String roleTypeId = "BILL_TO_CUSTOMER";
        String invoiceTypeId = "PURCHASE_INVOICE";
        txtAllocatedAmount.setText("0");
        txtUnallocatedAmount.setText("0");
//        loadInvoiceDetails(partyIdTextField.getText(), roleTypeId, invoiceTypeId);

//        String partyId = "SOUTHPACIFIC";
//        String roleTypeId = "BILL_TO_CUSTOMER";
//        String invoiceTypeId = "PURCHASE_INVOICE";
    }

    @Override
    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsModified(boolean isModified) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    BigDecimal paymentAmt = BigDecimal.ZERO;

    public BigDecimal getUnallocateAmount() {
        return BigDecimal.ZERO;
    }

    public void setPaymentAmount(BigDecimal val) {
        Debug.logInfo("setPaymentAmount :" + val.toString(), module);
        txtPaymentAmount.setText(val.toString());
        paymentAmt = val;
    }

    public void setAllocatedAmount(BigDecimal val) {
        Debug.logInfo("setAllocatedAmount :" + val.toString(), module);
        txtAllocatedAmount.setText(val.toString());
    }

    public void setUnAllocatedAmount(BigDecimal val) {
        Debug.logInfo("setUnAllocatedAmount :" + val.toString(), module);
        txtUnallocatedAmount.setText(val.toString());
    }

    public BigDecimal getAmountFromText(String text) {
        BigDecimal amount = BigDecimal.ZERO;
        if (text != null && text.isEmpty() == false) {
            try {
                amount = new BigDecimal(text);
            } catch (Exception e) {
                amount = BigDecimal.ZERO;
            }
        }
        return amount;
    }

    public BigDecimal getAllocatedAmount() {
        return getAmountFromText(txtAllocatedAmount.getText());
    }

    public BigDecimal getUnallocatedAmount() {
        return getAmountFromText(txtUnallocatedAmount.getText());
    }

    public BigDecimal getTotalAllocatedAndUnallocated() {
        return getAllocatedAmount().add(getUnallocatedAmount());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAutoAllocate;
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnClearAll;
    public javax.swing.JButton btnLoadInvoices;
    public javax.swing.JButton btnOk;
    public javax.swing.JButton btnSaveOrder;
    public javax.swing.JCheckBox cbShowZeroAmount;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelPaymentDetail;
    private javax.swing.JTable tablePaymentApplication;
    private javax.swing.JTextField txtAllocatedAmount;
    public javax.swing.JTextField txtPaymentAmount;
    private javax.swing.JTextField txtUnallocatedAmount;
    // End of variables declaration//GEN-END:variables
}
