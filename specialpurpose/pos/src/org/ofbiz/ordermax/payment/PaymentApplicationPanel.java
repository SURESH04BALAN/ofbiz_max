/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import javax.swing.table.*;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.PaymentApplicationTableModel;
import org.ofbiz.ordermax.base.InvoiceRolePartyPayment;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.PaymentApplication;

/**
 *
 * @author administrator
 */
public class PaymentApplicationPanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = PaymentApplicationPanel.class.getName();
    private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private java.awt.Frame parentFrame = null;

    private PaymentApplicationTableModel paymentApplicationTableModel = new PaymentApplicationTableModel();
    private ListAdapterListModel<PaymentApplication> invoiceCompositeListModel = new ListAdapterListModel<PaymentApplication>();

    /**
     * Creates new form PaymentPanel
     */
    public PaymentApplicationPanel() {
        initComponents();
        tablePaymentApplication.setModel(paymentApplicationTableModel);
        setupEditOrderTable();
    }

    public PaymentApplicationPanel(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        tablePaymentApplication.setModel(paymentApplicationTableModel);
        setupEditOrderTable();
        /*        Action delete = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         JTable table = (JTable) e.getSource();
         int modelRow = Integer.valueOf(e.getActionCommand());
         ((DefaultTableModel) table.getModel()).removeRow(modelRow);
         }
         };

         for (int i = 0; i < jTable1.getColumnModel().getColumnCount(); i++) {
         TableColumn column = jTable1.getColumnModel().getColumn(i);

         column.setCellEditor(new ButtonColumnCellEditor(jTable1, delete,  i));

         }
         */
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentDetail, "Payment Application");                               
    }

    public void setDialogFields() {

    }

    public void clearDialogFields() {
        tablePaymentApplication.removeAll();
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePaymentApplication.setSurrendersFocusOnKeystroke(true);
//        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
//        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
//        editor.setClickCountToStart(0);
//        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
//        tablePaymentApplication.getColumn("Payment Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < PaymentApplicationTableModel.Columns.values().length; i++) {

            PaymentApplicationTableModel.Columns[] columns = PaymentApplicationTableModel.Columns.values();
            PaymentApplicationTableModel.Columns column = columns[i];
            TableColumn col = tablePaymentApplication.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

    }

    public void setPaymentApplicationList(ListAdapterListModel<PaymentApplication> orderListModel) {

        paymentApplicationTableModel.setListModel(orderListModel);
    }

    private List<InvoiceRolePartyPayment> resultlist = new ArrayList<InvoiceRolePartyPayment>();

    public List<InvoiceRolePartyPayment> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<InvoiceRolePartyPayment> resultlist) {
        this.resultlist = resultlist;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPaymentDetail = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePaymentApplication = new javax.swing.JTable();

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

        add(panelPaymentDetail, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    public void loadItem() {
    }

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

    public void setPaymentAmount(BigDecimal val) {
        paymentAmt = val;
    }

    public BigDecimal getUnallocateAmount() {
        return BigDecimal.ZERO;
    }

    public static int SELECT_COL = 0;
    public static int DATE_COL = 1;
    public static int INVOICE_COL = 2;
    public static int REFERANCE_COL = 3;
    public static int INVOICE_AMT_COL = 4;
    public static int PAID_AMT_COL = 5;
    public static int BALANCE_AMT_COL = 6;
    public static int ALLOCATION_AMT_COL = 7;
    public static int BRANCH_COL = 8;

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
        return BigDecimal.ZERO;
    }

    public BigDecimal getUnallocatedAmount() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalAllocatedAndUnallocated() {
        return getAllocatedAmount().add(getUnallocatedAmount());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelPaymentDetail;
    private javax.swing.JTable tablePaymentApplication;
    // End of variables declaration//GEN-END:variables
}
