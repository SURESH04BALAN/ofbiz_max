/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.StringListCellRenderer;
import mvc.model.table.BillingAccountTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.SelectionTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class BillingAccountListPanel extends javax.swing.JPanel {

    private final JTextField txtBillingAccountIdTableTextField = new JTextField();
//    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

//    private BillingAccountTableModel paymentTableModel = new BillingAccountTableModel ();
    ListAdapterListModel invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
    public JComboBoxSelectionModel<String> accountIdCondComboModel = null;
    public JComboBoxSelectionModel<String> descCondComboModel = null;
    private DatePickerEditPanel fromDate = null;
    private DatePickerEditPanel thruDate = null;
    public GenericTableModelPanel<BillingAccount, BillingAccountTableModel> tablePanel = new GenericTableModelPanel<BillingAccount, BillingAccountTableModel>(new BillingAccountTableModel());

    ;    
//    public JComboBoxSelectionModel<String> accountIdCondComboModel = null;
//    public JComboBoxSelectionModel<String> descCondComboModel = null;
    
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public BillingAccountListPanel(ControllerOptions options) {
        initComponents();

//        tableReceiveInv.setModel(paymentTableModel);
        tablePanel = new GenericTableModelPanel<BillingAccount, BillingAccountTableModel>(new BillingAccountTableModel());
        panelInvoiceList.setLayout(new BorderLayout());
        panelInvoiceList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Billing Accounts");
//        btnFromPartyId.addActionListener(new LookupActionListner(txtFromPartyIdTextField, "partyIdTextField", "BILL_TO_CUSTOMER", null));
//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        

    }

    public void setBillingAccountList(ListAdapterListModel<BillingAccount> orderListModel) {
        tablePanel.setListModel(orderListModel);
        if (orderListModel.getSize() > 0) {
            tablePanel.jTable.setRowSelectionInterval(0, 0);
        }
    }

    public JTextField getTxtBillingAccountIdTableTextField() {
        return txtBillingAccountIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getRowColumnActionCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
//        txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
//        DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
//        editor.setClickCountToStart(0);
//        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
//        tableReceiveInv.getColumn("Payment Id").setCellEditor(productTreeActionTableCellEditor);
        for (int i = 0; i < BillingAccountTableModel.Columns.values().length; i++) {
            BillingAccountTableModel.Columns[] columns = BillingAccountTableModel.Columns.values();
            BillingAccountTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (BillingAccountTableModel.Columns.BILLINGACCOUNTID.toString().equals(column.toString())) {
                Debug.logError("col name: swwt" + column.toString(), "module");
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);
                Debug.logError("col name: swwt end" + column.toString(), "module");
            } else if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }

        }

    }

    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = bdValue.setScale(scale, rounding);
            setHorizontalAlignment(JLabel.RIGHT);
// And pass it on to parent class
            return super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Billing Accounts", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelInvoiceList.setLayout(new java.awt.GridLayout(1, 0));

        tableReceiveInv.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableReceiveInv);

        panelInvoiceList.add(jScrollPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JTable tableReceiveInv;
    // End of variables declaration//GEN-END:variables
}
